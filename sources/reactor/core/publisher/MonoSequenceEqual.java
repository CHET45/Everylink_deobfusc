package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSequenceEqual<T> extends Mono<Boolean> implements SourceProducer<Boolean> {
    final BiPredicate<? super T, ? super T> comparer;
    final Publisher<? extends T> first;
    final int prefetch;
    final Publisher<? extends T> second;

    MonoSequenceEqual(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, BiPredicate<? super T, ? super T> biPredicate, int i) {
        this.first = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "first"));
        this.second = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher2, "second"));
        this.comparer = (BiPredicate) Objects.requireNonNull(biPredicate, "comparer");
        if (i < 1) {
            throw new IllegalArgumentException("Buffer size must be strictly positive: " + i);
        }
        this.prefetch = i;
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super Boolean> coreSubscriber) {
        EqualCoordinator equalCoordinator = new EqualCoordinator(coreSubscriber, this.prefetch, this.first, this.second, this.comparer);
        coreSubscriber.onSubscribe(equalCoordinator);
        equalCoordinator.subscribe();
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.prefetch) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class EqualCoordinator<T> implements InnerProducer<Boolean> {
        static final AtomicIntegerFieldUpdater<EqualCoordinator> ONCE = AtomicIntegerFieldUpdater.newUpdater(EqualCoordinator.class, "once");
        static final AtomicIntegerFieldUpdater<EqualCoordinator> WIP = AtomicIntegerFieldUpdater.newUpdater(EqualCoordinator.class, "wip");
        final CoreSubscriber<? super Boolean> actual;
        volatile boolean cancelled;
        final BiPredicate<? super T, ? super T> comparer;
        final Publisher<? extends T> first;
        final EqualSubscriber<T> firstSubscriber;
        volatile int once;
        final Publisher<? extends T> second;
        final EqualSubscriber<T> secondSubscriber;

        /* JADX INFO: renamed from: v1 */
        T f2267v1;

        /* JADX INFO: renamed from: v2 */
        T f2268v2;
        volatile int wip;

        EqualCoordinator(CoreSubscriber<? super Boolean> coreSubscriber, int i, Publisher<? extends T> publisher, Publisher<? extends T> publisher2, BiPredicate<? super T, ? super T> biPredicate) {
            this.actual = coreSubscriber;
            this.first = publisher;
            this.second = publisher2;
            this.comparer = biPredicate;
            this.firstSubscriber = new EqualSubscriber<>(this, i);
            this.secondSubscriber = new EqualSubscriber<>(this, i);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Boolean> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) new EqualSubscriber[]{this.firstSubscriber, this.secondSubscriber});
        }

        void subscribe() {
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.first.subscribe(this.firstSubscriber);
                this.second.subscribe(this.secondSubscriber);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j) && ONCE.compareAndSet(this, 0, 1)) {
                this.first.subscribe(this.firstSubscriber);
                this.second.subscribe(this.secondSubscriber);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelInner(this.firstSubscriber);
            cancelInner(this.secondSubscriber);
            if (WIP.getAndIncrement(this) == 0) {
                this.firstSubscriber.queue.clear();
                this.secondSubscriber.queue.clear();
            }
        }

        void cancel(EqualSubscriber<T> equalSubscriber, Queue<T> queue, EqualSubscriber<T> equalSubscriber2, Queue<T> queue2) {
            this.cancelled = true;
            cancelInner(equalSubscriber);
            queue.clear();
            cancelInner(equalSubscriber2);
            queue2.clear();
        }

        void cancelInner(EqualSubscriber<T> equalSubscriber) {
            Subscription andSet;
            if (equalSubscriber.subscription == Operators.cancelledSubscription() || (andSet = EqualSubscriber.f2269S.getAndSet(equalSubscriber, Operators.cancelledSubscription())) == null || andSet == Operators.cancelledSubscription()) {
                return;
            }
            andSet.cancel();
        }

        void drain() {
            Throwable th;
            Throwable th2;
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            EqualSubscriber<T> equalSubscriber = this.firstSubscriber;
            Queue<T> queue = equalSubscriber.queue;
            EqualSubscriber<T> equalSubscriber2 = this.secondSubscriber;
            Queue<T> queue2 = equalSubscriber2.queue;
            int iAddAndGet = 1;
            do {
                long j = 0;
                while (!this.cancelled) {
                    boolean z = equalSubscriber.done;
                    if (z && (th2 = equalSubscriber.error) != null) {
                        cancel(equalSubscriber, queue, equalSubscriber2, queue2);
                        this.actual.onError(th2);
                        return;
                    }
                    boolean z2 = equalSubscriber2.done;
                    if (z2 && (th = equalSubscriber2.error) != null) {
                        cancel(equalSubscriber, queue, equalSubscriber2, queue2);
                        this.actual.onError(th);
                        return;
                    }
                    if (this.f2267v1 == null) {
                        this.f2267v1 = queue.poll();
                    }
                    boolean z3 = this.f2267v1 == null;
                    if (this.f2268v2 == null) {
                        this.f2268v2 = queue2.poll();
                    }
                    T t = this.f2268v2;
                    boolean z4 = t == null;
                    if (z && z2 && z3 && z4) {
                        this.actual.onNext(true);
                        this.actual.onComplete();
                        return;
                    }
                    if (z && z2 && z3 != z4) {
                        cancel(equalSubscriber, queue, equalSubscriber2, queue2);
                        this.actual.onNext(false);
                        this.actual.onComplete();
                        return;
                    }
                    if (!z3 && !z4) {
                        try {
                            if (!this.comparer.test(this.f2267v1, t)) {
                                cancel(equalSubscriber, queue, equalSubscriber2, queue2);
                                this.actual.onNext(false);
                                this.actual.onComplete();
                                return;
                            } else {
                                j++;
                                this.f2267v1 = null;
                                this.f2268v2 = null;
                            }
                        } catch (Throwable th3) {
                            Exceptions.throwIfFatal(th3);
                            cancel(equalSubscriber, queue, equalSubscriber2, queue2);
                            CoreSubscriber<? super Boolean> coreSubscriber = this.actual;
                            coreSubscriber.onError(Operators.onOperatorError(th3, coreSubscriber.currentContext()));
                            return;
                        }
                    }
                    if (z3 || z4) {
                        if (j != 0) {
                            equalSubscriber.cachedSubscription.request(j);
                            equalSubscriber2.cachedSubscription.request(j);
                        }
                        iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                    }
                }
                queue.clear();
                queue2.clear();
                return;
            } while (iAddAndGet != 0);
        }
    }

    static final class EqualSubscriber<T> implements InnerConsumer<T> {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<EqualSubscriber, Subscription> f2269S = AtomicReferenceFieldUpdater.newUpdater(EqualSubscriber.class, Subscription.class, "subscription");
        Subscription cachedSubscription;
        volatile boolean done;
        Throwable error;
        final EqualCoordinator<T> parent;
        final int prefetch;
        final Queue<T> queue;
        volatile Subscription subscription;

        EqualSubscriber(EqualCoordinator<T> equalCoordinator, int i) {
            this.parent = equalCoordinator;
            this.prefetch = i;
            this.queue = (Queue) Queues.get(i).get();
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.actual.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.subscription == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.queue.size());
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2269S, this, subscription)) {
                this.cachedSubscription = subscription;
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.queue.offer(t)) {
                onError(Operators.onOperatorError(this.cachedSubscription, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), t, currentContext()));
            } else {
                this.parent.drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            this.parent.drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }
    }
}
