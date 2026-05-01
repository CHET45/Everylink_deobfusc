package reactor.core.publisher;

import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxCombineLatest;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxCombineLatest<T, R> extends Flux<R> implements Fuseable, SourceProducer<R> {
    final Publisher<? extends T>[] array;
    final Function<Object[], R> combiner;
    final Iterable<? extends Publisher<? extends T>> iterable;
    final int prefetch;
    final Supplier<? extends Queue<SourceAndArray>> queueSupplier;

    FluxCombineLatest(Publisher<? extends T>[] publisherArr, Function<Object[], R> function, Supplier<? extends Queue<SourceAndArray>> supplier, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.array = (Publisher[]) Objects.requireNonNull(publisherArr, "array");
        this.iterable = null;
        this.combiner = (Function) Objects.requireNonNull(function, "combiner");
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
        this.prefetch = i;
    }

    FluxCombineLatest(Iterable<? extends Publisher<? extends T>> iterable, Function<Object[], R> function, Supplier<? extends Queue<SourceAndArray>> supplier, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.array = null;
        this.iterable = (Iterable) Objects.requireNonNull(iterable, "iterable");
        this.combiner = (Function) Objects.requireNonNull(function, "combiner");
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
        this.prefetch = i;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super R> coreSubscriber) {
        int length;
        Publisher<? extends T>[] publisherArr = this.array;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                Iterator it = (Iterator) Objects.requireNonNull(this.iterable.iterator(), "The iterator returned is null");
                length = 0;
                while (it.hasNext()) {
                    try {
                        try {
                            Publisher<? extends T> publisher = (Publisher) Objects.requireNonNull((Publisher) it.next(), "The Publisher returned by the iterator is null");
                            if (length == publisherArr.length) {
                                Publisher<? extends T>[] publisherArr2 = new Publisher[(length >> 2) + length];
                                System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
                                publisherArr = publisherArr2;
                            }
                            publisherArr[length] = publisher;
                            length++;
                        } catch (Throwable th) {
                            Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
                            return;
                        }
                    } catch (Throwable th2) {
                        Operators.error(coreSubscriber, Operators.onOperatorError(th2, coreSubscriber.currentContext()));
                        return;
                    }
                }
            } catch (Throwable th3) {
                Operators.error(coreSubscriber, Operators.onOperatorError(th3, coreSubscriber.currentContext()));
                return;
            }
        } else {
            length = publisherArr.length;
        }
        int i = length;
        if (i == 0) {
            Operators.complete(coreSubscriber);
            return;
        }
        if (i == 1) {
            Function function = new Function() { // from class: reactor.core.publisher.FluxCombineLatest$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m4284lambda$subscribe$0$reactorcorepublisherFluxCombineLatest(obj);
                }
            };
            if (publisherArr[0] instanceof Fuseable) {
                new FluxMapFuseable(from((Publisher) publisherArr[0]), function).subscribe((CoreSubscriber) coreSubscriber);
                return;
            } else if (!(coreSubscriber instanceof Fuseable.QueueSubscription)) {
                new FluxMap(from((Publisher) publisherArr[0]), function).subscribe((CoreSubscriber) coreSubscriber);
                return;
            }
        }
        Operators.toFluxOrMono(publisherArr);
        CombineLatestCoordinator combineLatestCoordinator = new CombineLatestCoordinator(coreSubscriber, this.combiner, i, this.queueSupplier.get(), this.prefetch);
        coreSubscriber.onSubscribe(combineLatestCoordinator);
        combineLatestCoordinator.subscribe(publisherArr, i);
    }

    /* JADX INFO: renamed from: lambda$subscribe$0$reactor-core-publisher-FluxCombineLatest, reason: not valid java name */
    /* synthetic */ Object m4284lambda$subscribe$0$reactorcorepublisherFluxCombineLatest(Object obj) {
        return this.combiner.apply(new Object[]{obj});
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.prefetch) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class CombineLatestCoordinator<T, R> implements Fuseable.QueueSubscription<R>, InnerProducer<R> {
        final CoreSubscriber<? super R> actual;
        volatile boolean cancelled;
        final Function<Object[], R> combiner;
        int completedSources;
        volatile boolean done;
        volatile Throwable error;
        final Object[] latest;
        int nonEmptySources;
        boolean outputFused;
        final Queue<SourceAndArray> queue;
        volatile long requested;
        final CombineLatestInner<T>[] subscribers;
        volatile int wip;
        static final AtomicLongFieldUpdater<CombineLatestCoordinator> REQUESTED = AtomicLongFieldUpdater.newUpdater(CombineLatestCoordinator.class, "requested");
        static final AtomicIntegerFieldUpdater<CombineLatestCoordinator> WIP = AtomicIntegerFieldUpdater.newUpdater(CombineLatestCoordinator.class, "wip");
        static final AtomicReferenceFieldUpdater<CombineLatestCoordinator, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(CombineLatestCoordinator.class, Throwable.class, "error");

        CombineLatestCoordinator(CoreSubscriber<? super R> coreSubscriber, Function<Object[], R> function, int i, Queue<SourceAndArray> queue, int i2) {
            this.actual = coreSubscriber;
            this.combiner = function;
            CombineLatestInner<T>[] combineLatestInnerArr = new CombineLatestInner[i];
            for (int i3 = 0; i3 < i; i3++) {
                combineLatestInnerArr[i3] = new CombineLatestInner<>(this, i3, i2);
            }
            this.subscribers = combineLatestInnerArr;
            this.latest = new Object[i];
            this.queue = queue;
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelAll();
            if (WIP.getAndIncrement(this) == 0) {
                clear();
            }
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers);
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : super.scanUnsafe(attr);
        }

        void subscribe(Publisher<? extends T>[] publisherArr, int i) {
            CombineLatestInner<T>[] combineLatestInnerArr = this.subscribers;
            for (int i2 = 0; i2 < i && !this.done && !this.cancelled; i2++) {
                publisherArr[i2].subscribe(combineLatestInnerArr[i2]);
            }
        }

        void innerValue(int i, T t) {
            boolean z;
            synchronized (this) {
                Object[] objArr = this.latest;
                int i2 = this.nonEmptySources;
                if (objArr[i] == null) {
                    i2++;
                    this.nonEmptySources = i2;
                }
                objArr[i] = t;
                if (objArr.length == i2) {
                    if (!this.queue.offer(new SourceAndArray(this.subscribers[i], (Object[]) objArr.clone()))) {
                        innerError(Operators.onOperatorError(this, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), this.actual.currentContext()));
                        return;
                    }
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    this.subscribers[i].requestOne();
                } else {
                    drain();
                }
            }
        }

        void innerComplete(int i) {
            int i2;
            synchronized (this) {
                Object[] objArr = this.latest;
                if (objArr[i] == null || (i2 = this.completedSources + 1) == objArr.length) {
                    this.done = true;
                    drain();
                } else {
                    this.completedSources = i2;
                }
            }
        }

        void innerError(Throwable th) {
            if (Exceptions.addThrowable(ERROR, this, th)) {
                this.done = true;
                drain();
            } else {
                discardQueue(this.queue);
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        void drainOutput() {
            CoreSubscriber<? super R> coreSubscriber = this.actual;
            Queue<SourceAndArray> queue = this.queue;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                Throwable th = this.error;
                if (th != null) {
                    discardQueue(queue);
                    coreSubscriber.onError(th);
                    return;
                }
                boolean z = this.done;
                boolean zIsEmpty = queue.isEmpty();
                if (!zIsEmpty) {
                    coreSubscriber.onNext(null);
                }
                if (z && zIsEmpty) {
                    coreSubscriber.onComplete();
                    return;
                } else {
                    iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
            discardQueue(queue);
        }

        void drainAsync() {
            Queue<SourceAndArray> queue = this.queue;
            int iAddAndGet = 1;
            do {
                long j = this.requested;
                long j2 = 0;
                while (j2 != j) {
                    boolean z = this.done;
                    SourceAndArray sourceAndArrayPoll = queue.poll();
                    boolean z2 = sourceAndArrayPoll == null;
                    if (checkTerminated(z, z2, queue)) {
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    try {
                        this.actual.onNext(Objects.requireNonNull(this.combiner.apply(sourceAndArrayPoll.array), "Combiner returned null"));
                        sourceAndArrayPoll.source.requestOne();
                        j2++;
                    } catch (Throwable th) {
                        Context contextCurrentContext = this.actual.currentContext();
                        Operators.onDiscardMultiple((Stream<?>) Stream.of(sourceAndArrayPoll.array), contextCurrentContext);
                        Throwable thOnOperatorError = Operators.onOperatorError(this, th, sourceAndArrayPoll.array, contextCurrentContext);
                        AtomicReferenceFieldUpdater<CombineLatestCoordinator, Throwable> atomicReferenceFieldUpdater = ERROR;
                        Exceptions.addThrowable(atomicReferenceFieldUpdater, this, thOnOperatorError);
                        this.actual.onError(Exceptions.terminate(atomicReferenceFieldUpdater, this));
                        return;
                    }
                }
                if (j2 == j && checkTerminated(this.done, queue.isEmpty(), queue)) {
                    return;
                }
                if (j2 != 0 && j != Long.MAX_VALUE) {
                    REQUESTED.addAndGet(this, -j2);
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            if (this.outputFused) {
                drainOutput();
            } else {
                drainAsync();
            }
        }

        boolean checkTerminated(boolean z, boolean z2, Queue<SourceAndArray> queue) {
            if (this.cancelled) {
                cancelAll();
                discardQueue(queue);
                return true;
            }
            if (!z) {
                return false;
            }
            Throwable thTerminate = Exceptions.terminate(ERROR, this);
            if (thTerminate != null && thTerminate != Exceptions.TERMINATED) {
                cancelAll();
                discardQueue(queue);
                this.actual.onError(thTerminate);
                return true;
            }
            if (!z2) {
                return false;
            }
            cancelAll();
            this.actual.onComplete();
            return true;
        }

        void cancelAll() {
            for (CombineLatestInner<T> combineLatestInner : this.subscribers) {
                combineLatestInner.cancel();
            }
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 4) != 0) {
                return 0;
            }
            int i2 = i & 2;
            this.outputFused = i2 != 0;
            return i2;
        }

        @Override // java.util.Queue
        @Nullable
        public R poll() {
            SourceAndArray sourceAndArrayPoll = this.queue.poll();
            if (sourceAndArrayPoll == null) {
                return null;
            }
            R rApply = this.combiner.apply(sourceAndArrayPoll.array);
            sourceAndArrayPoll.source.requestOne();
            return rApply;
        }

        private void discardQueue(Queue<SourceAndArray> queue) {
            Operators.onDiscardQueueWithClear(queue, this.actual.currentContext(), new Function() { // from class: reactor.core.publisher.FluxCombineLatest$CombineLatestCoordinator$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((FluxCombineLatest.SourceAndArray) obj).toStream();
                }
            });
        }

        @Override // java.util.Collection
        public void clear() {
            discardQueue(this.queue);
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // java.util.Collection
        public int size() {
            return this.queue.size();
        }
    }

    static final class CombineLatestInner<T> implements InnerConsumer<T> {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<CombineLatestInner, Subscription> f2096S = AtomicReferenceFieldUpdater.newUpdater(CombineLatestInner.class, Subscription.class, "s");
        final int index;
        final int limit;
        final CombineLatestCoordinator<T, ?> parent;
        final int prefetch;
        int produced;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2097s;

        CombineLatestInner(CombineLatestCoordinator<T, ?> combineLatestCoordinator, int i, int i2) {
            this.parent = combineLatestCoordinator;
            this.index = i;
            this.prefetch = i2;
            this.limit = Operators.unboundedOrLimit(i2);
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.actual.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2096S, this, subscription)) {
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.parent.innerValue(this.index, t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.innerError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.innerComplete(this.index);
        }

        public void cancel() {
            Operators.terminate(f2096S, this);
        }

        void requestOne() {
            int i = this.produced + 1;
            if (i == this.limit) {
                this.produced = 0;
                this.f2097s.request(i);
            } else {
                this.produced = i;
            }
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2097s;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2097s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class SourceAndArray {
        final Object[] array;
        final CombineLatestInner<?> source;

        SourceAndArray(CombineLatestInner<?> combineLatestInner, Object[] objArr) {
            this.source = combineLatestInner;
            this.array = objArr;
        }

        final Stream<?> toStream() {
            return Stream.of(this.array);
        }
    }
}
