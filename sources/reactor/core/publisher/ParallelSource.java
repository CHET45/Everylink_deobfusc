package reactor.core.publisher;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelSource<T> extends ParallelFlux<T> implements Scannable {
    final int parallelism;
    final int prefetch;
    final Supplier<Queue<T>> queueSupplier;
    final Publisher<? extends T> source;

    ParallelSource(Publisher<? extends T> publisher, int i, int i2, Supplier<Queue<T>> supplier) {
        if (i <= 0) {
            throw new IllegalArgumentException("parallelism > 0 required but it was " + i);
        }
        if (i2 <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i2);
        }
        this.source = Operators.toFluxOrMono(publisher);
        this.parallelism = i;
        this.prefetch = i2;
        this.queueSupplier = supplier;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.parallelism;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super T>[] coreSubscriberArr) {
        if (validate(coreSubscriberArr)) {
            this.source.subscribe(new ParallelSourceMain(coreSubscriberArr, this.prefetch, this.queueSupplier));
        }
    }

    static final class ParallelSourceMain<T> implements InnerConsumer<T> {
        volatile boolean cancelled;
        volatile boolean done;
        final long[] emissions;
        Throwable error;
        int index;
        final int limit;
        final int prefetch;
        int produced;
        Queue<T> queue;
        final Supplier<Queue<T>> queueSupplier;
        final AtomicLongArray requests;

        /* JADX INFO: renamed from: s */
        Subscription f2290s;
        int sourceMode;
        volatile int subscriberCount;
        final CoreSubscriber<? super T>[] subscribers;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ParallelSourceMain> WIP = AtomicIntegerFieldUpdater.newUpdater(ParallelSourceMain.class, "wip");
        static final AtomicIntegerFieldUpdater<ParallelSourceMain> SUBSCRIBER_COUNT = AtomicIntegerFieldUpdater.newUpdater(ParallelSourceMain.class, "subscriberCount");

        ParallelSourceMain(CoreSubscriber<? super T>[] coreSubscriberArr, int i, Supplier<Queue<T>> supplier) {
            this.subscribers = coreSubscriberArr;
            this.prefetch = i;
            this.queueSupplier = supplier;
            this.limit = Operators.unboundedOrLimit(i);
            this.requests = new AtomicLongArray(coreSubscriberArr.length);
            this.emissions = new long[coreSubscriberArr.length];
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2290s;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.BUFFERED) {
                Queue<T> queue = this.queue;
                return Integer.valueOf(queue != null ? queue.size() : 0);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers).map(new Function() { // from class: reactor.core.publisher.ParallelSource$ParallelSourceMain$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Scannable.from((CoreSubscriber) obj);
                }
            });
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.subscribers[0].currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2290s, subscription)) {
                this.f2290s = subscription;
                if (subscription instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription queueSubscription = (Fuseable.QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        setupSubscribers();
                        drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        setupSubscribers();
                        subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = this.queueSupplier.get();
                setupSubscribers();
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        void setupSubscribers() {
            int length = this.subscribers.length;
            int i = 0;
            while (i < length && !this.cancelled) {
                int i2 = i + 1;
                SUBSCRIBER_COUNT.lazySet(this, i2);
                this.subscribers[i].onSubscribe(new ParallelSourceInner(this, i, length));
                i = i2;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, currentContext());
            } else if (this.sourceMode == 0 && !this.queue.offer(t)) {
                onError(Operators.onOperatorError(this.f2290s, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), t, currentContext()));
            } else {
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, currentContext());
                return;
            }
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            drain();
        }

        void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.f2290s.cancel();
            if (WIP.getAndIncrement(this) == 0) {
                this.queue.clear();
            }
        }

        void drainAsync() {
            Throwable th;
            Queue<T> queue = this.queue;
            CoreSubscriber<? super T>[] coreSubscriberArr = this.subscribers;
            AtomicLongArray atomicLongArray = this.requests;
            long[] jArr = this.emissions;
            int length = jArr.length;
            int i = this.index;
            int i2 = this.produced;
            int iAddAndGet = 1;
            while (true) {
                int i3 = 0;
                int i4 = 0;
                while (!this.cancelled) {
                    boolean z = this.done;
                    if (z && (th = this.error) != null) {
                        queue.clear();
                        int length2 = coreSubscriberArr.length;
                        while (i3 < length2) {
                            coreSubscriberArr[i3].onError(th);
                            i3++;
                        }
                        return;
                    }
                    boolean zIsEmpty = queue.isEmpty();
                    if (z && zIsEmpty) {
                        int length3 = coreSubscriberArr.length;
                        while (i3 < length3) {
                            coreSubscriberArr[i3].onComplete();
                            i3++;
                        }
                        return;
                    }
                    if (!zIsEmpty) {
                        long j = atomicLongArray.get(i);
                        long j2 = jArr[i];
                        if (j != j2) {
                            try {
                                T tPoll = queue.poll();
                                if (tPoll != null) {
                                    coreSubscriberArr[i].onNext(tPoll);
                                    jArr[i] = j2 + 1;
                                    i2++;
                                    if (i2 == this.limit) {
                                        this.f2290s.request(i2);
                                        i2 = 0;
                                    }
                                    i4 = 0;
                                }
                            } catch (Throwable th2) {
                                Throwable thOnOperatorError = Operators.onOperatorError(this.f2290s, th2, coreSubscriberArr[i].currentContext());
                                int length4 = coreSubscriberArr.length;
                                while (i3 < length4) {
                                    coreSubscriberArr[i3].onError(thOnOperatorError);
                                    i3++;
                                }
                                return;
                            }
                        } else {
                            i4++;
                        }
                        i++;
                        if (i == length) {
                            i = 0;
                        }
                        if (i4 == length) {
                        }
                    }
                    int i5 = this.wip;
                    if (i5 == iAddAndGet) {
                        this.index = i;
                        this.produced = i2;
                        iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                        if (iAddAndGet == 0) {
                            return;
                        }
                    } else {
                        iAddAndGet = i5;
                    }
                }
                queue.clear();
                return;
            }
        }

        void drainSync() {
            Queue<T> queue = this.queue;
            CoreSubscriber<? super T>[] coreSubscriberArr = this.subscribers;
            AtomicLongArray atomicLongArray = this.requests;
            long[] jArr = this.emissions;
            int length = jArr.length;
            int i = this.index;
            int iAddAndGet = 1;
            while (true) {
                int i2 = 0;
                int i3 = 0;
                while (!this.cancelled) {
                    if (queue.isEmpty()) {
                        int length2 = coreSubscriberArr.length;
                        while (i2 < length2) {
                            coreSubscriberArr[i2].onComplete();
                            i2++;
                        }
                        return;
                    }
                    long j = atomicLongArray.get(i);
                    long j2 = jArr[i];
                    if (j != j2) {
                        try {
                            T tPoll = queue.poll();
                            if (tPoll == null) {
                                int length3 = coreSubscriberArr.length;
                                while (i2 < length3) {
                                    coreSubscriberArr[i2].onComplete();
                                    i2++;
                                }
                                return;
                            }
                            coreSubscriberArr[i].onNext(tPoll);
                            jArr[i] = j2 + 1;
                            i3 = 0;
                        } catch (Throwable th) {
                            Throwable thOnOperatorError = Operators.onOperatorError(this.f2290s, th, coreSubscriberArr[i].currentContext());
                            int length4 = coreSubscriberArr.length;
                            while (i2 < length4) {
                                coreSubscriberArr[i2].onError(thOnOperatorError);
                                i2++;
                            }
                            return;
                        }
                    } else {
                        i3++;
                    }
                    i++;
                    if (i == length) {
                        i = 0;
                    }
                    if (i3 == length) {
                        int i4 = this.wip;
                        if (i4 == iAddAndGet) {
                            this.index = i;
                            iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                            if (iAddAndGet == 0) {
                                return;
                            }
                        } else {
                            iAddAndGet = i4;
                        }
                    }
                }
                queue.clear();
                return;
            }
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            if (this.sourceMode == 1) {
                drainSync();
            } else {
                drainAsync();
            }
        }

        static final class ParallelSourceInner<T> implements InnerProducer<T> {
            final int index;
            final int length;
            final ParallelSourceMain<T> parent;

            ParallelSourceInner(ParallelSourceMain<T> parallelSourceMain, int i, int i2) {
                this.index = i;
                this.length = i2;
                this.parent = parallelSourceMain;
            }

            @Override // reactor.core.publisher.InnerProducer
            public CoreSubscriber<? super T> actual() {
                return this.parent.subscribers[this.index];
            }

            @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
            @Nullable
            public Object scanUnsafe(Scannable.Attr attr) {
                return attr == Scannable.Attr.PARENT ? this.parent : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }

            @Override // org.reactivestreams.Subscription
            public void request(long j) {
                long j2;
                if (Operators.validate(j)) {
                    AtomicLongArray atomicLongArray = this.parent.requests;
                    do {
                        j2 = atomicLongArray.get(this.index);
                        if (j2 == Long.MAX_VALUE) {
                            return;
                        }
                    } while (!atomicLongArray.compareAndSet(this.index, j2, Operators.addCap(j2, j)));
                    if (this.parent.subscriberCount == this.length) {
                        this.parent.drain();
                    }
                }
            }

            @Override // org.reactivestreams.Subscription
            public void cancel() {
                this.parent.cancel();
            }
        }
    }
}
