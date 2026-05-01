package reactor.core.publisher;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Spliterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxFlattenIterable<T, R> extends InternalFluxOperator<T, R> implements Fuseable {
    final Function<? super T, ? extends Iterable<? extends R>> mapper;
    final int prefetch;
    final Supplier<Queue<T>> queueSupplier;

    FluxFlattenIterable(Flux<? extends T> flux, Function<? super T, ? extends Iterable<? extends R>> function, int i, Supplier<Queue<T>> supplier) {
        super(flux);
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.mapper = (Function) Objects.requireNonNull(function, "mapper");
        this.prefetch = i;
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) throws Exception {
        if (this.source instanceof Callable) {
            Object objCall = ((Callable) this.source).call();
            if (objCall == null) {
                Operators.complete(coreSubscriber);
                return null;
            }
            try {
                Spliterator<? extends R> spliterator = this.mapper.apply(objCall).spliterator();
                FluxIterable.subscribe(coreSubscriber, spliterator, FluxIterable.checkFinite(spliterator));
                return null;
            } catch (Throwable th) {
                Context contextCurrentContext = coreSubscriber.currentContext();
                Throwable thOnNextError = Operators.onNextError(objCall, th, contextCurrentContext);
                Operators.onDiscard(objCall, contextCurrentContext);
                if (thOnNextError != null) {
                    Operators.error(coreSubscriber, thOnNextError);
                } else {
                    Operators.complete(coreSubscriber);
                }
                return null;
            }
        }
        return new FlattenIterableSubscriber(coreSubscriber, this.mapper, this.prefetch, this.queueSupplier);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class FlattenIterableSubscriber<T, R> implements InnerOperator<T, R>, Fuseable.QueueSubscription<R>, Consumer<R> {
        final CoreSubscriber<? super R> actual;
        volatile boolean cancelled;
        int consumed;

        @Nullable
        Spliterator<? extends R> current;
        boolean currentKnownToBeFinite;
        volatile boolean done;
        volatile Throwable error;
        int fusionMode;
        final int limit;
        final Function<? super T, ? extends Iterable<? extends R>> mapper;
        R nextElement;
        final int prefetch;
        Queue<T> queue;
        final Supplier<Queue<T>> queueSupplier;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2133s;
        boolean valueReady = false;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<FlattenIterableSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(FlattenIterableSubscriber.class, "wip");
        static final AtomicLongFieldUpdater<FlattenIterableSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(FlattenIterableSubscriber.class, "requested");
        static final AtomicReferenceFieldUpdater<FlattenIterableSubscriber, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(FlattenIterableSubscriber.class, Throwable.class, "error");

        FlattenIterableSubscriber(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Iterable<? extends R>> function, int i, Supplier<Queue<T>> supplier) {
            this.actual = coreSubscriber;
            this.mapper = function;
            this.prefetch = i;
            this.queueSupplier = supplier;
            this.limit = Operators.unboundedOrLimit(i);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2133s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr != Scannable.Attr.BUFFERED) {
                return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            Queue<T> queue = this.queue;
            return Integer.valueOf(queue != null ? queue.size() : 0);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2133s, subscription)) {
                this.f2133s = subscription;
                if (subscription instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription queueSubscription = (Fuseable.QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.actual.onSubscribe(this);
                        subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = this.queueSupplier.get();
                this.actual.onSubscribe(this);
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.fusionMode != 2 && !this.queue.offer(t)) {
                Context contextCurrentContext = this.actual.currentContext();
                onError(Operators.onOperatorError(this.f2133s, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), contextCurrentContext));
                Operators.onDiscard(t, contextCurrentContext);
                return;
            }
            drain(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (Exceptions.addThrowable(ERROR, this, th)) {
                this.done = true;
                drain(null);
            } else {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain(null);
        }

        @Override // java.util.function.Consumer
        public void accept(R r) {
            this.valueReady = true;
            this.nextElement = r;
        }

        boolean hasNext(Spliterator<? extends R> spliterator) {
            if (!this.valueReady) {
                spliterator.tryAdvance(this);
            }
            return this.valueReady;
        }

        R next(Spliterator<? extends R> spliterator) {
            if (!this.valueReady && !hasNext(spliterator)) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            R r = this.nextElement;
            this.nextElement = null;
            return r;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
                drain(null);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.f2133s.cancel();
            if (WIP.getAndIncrement(this) == 0) {
                Context contextCurrentContext = this.actual.currentContext();
                Operators.onDiscardQueueWithClear(this.queue, contextCurrentContext, null);
                Operators.onDiscard(this.nextElement, contextCurrentContext);
                Operators.onDiscardMultiple(this.current, this.currentKnownToBeFinite, contextCurrentContext);
            }
        }

        final void resetCurrent() {
            this.current = null;
            this.currentKnownToBeFinite = false;
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x0077  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0080  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void drainAsync() {
            /*
                Method dump skipped, instruction units count: 481
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.FluxFlattenIterable.FlattenIterableSubscriber.drainAsync():void");
        }

        void drainSync() {
            CoreSubscriber<? super R> coreSubscriber = this.actual;
            Spliterator<? extends R> spliterator = this.current;
            boolean zCheckFinite = this.currentKnownToBeFinite;
            int iAddAndGet = 1;
            while (true) {
                boolean z = false;
                if (spliterator == null) {
                    if (this.cancelled) {
                        Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                        return;
                    }
                    boolean z2 = this.done;
                    Queue<T> queue = this.queue;
                    try {
                        T tPoll = queue.poll();
                        boolean z3 = tPoll == null;
                        if (z2 && z3) {
                            coreSubscriber.onComplete();
                            return;
                        }
                        if (!z3) {
                            try {
                                spliterator = this.mapper.apply(tPoll).spliterator();
                                zCheckFinite = FluxIterable.checkFinite(spliterator);
                                if (zCheckFinite) {
                                    if (spliterator.estimateSize() == 0) {
                                        zCheckFinite = false;
                                        spliterator = null;
                                    }
                                } else if (!hasNext(spliterator)) {
                                    zCheckFinite = false;
                                    spliterator = null;
                                }
                            } catch (Throwable th) {
                                resetCurrent();
                                Context contextCurrentContext = this.actual.currentContext();
                                Throwable thOnNextError = Operators.onNextError(tPoll, th, contextCurrentContext, this.f2133s);
                                Operators.onDiscard(tPoll, contextCurrentContext);
                                if (thOnNextError != null) {
                                    coreSubscriber.onError(thOnNextError);
                                    return;
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        resetCurrent();
                        Operators.onDiscardQueueWithClear(queue, this.actual.currentContext(), null);
                        coreSubscriber.onError(th2);
                        return;
                    }
                }
                if (spliterator != null) {
                    long j = this.requested;
                    long j2 = 0;
                    while (true) {
                        if (j2 == j) {
                            break;
                        }
                        if (this.cancelled) {
                            resetCurrent();
                            Context contextCurrentContext2 = this.actual.currentContext();
                            Operators.onDiscardQueueWithClear(this.queue, contextCurrentContext2, null);
                            Operators.onDiscard(this.nextElement, contextCurrentContext2);
                            Operators.onDiscardMultiple(spliterator, zCheckFinite, contextCurrentContext2);
                            return;
                        }
                        try {
                            coreSubscriber.onNext((Object) Objects.requireNonNull(next(spliterator), "iterator returned null"));
                            if (this.cancelled) {
                                resetCurrent();
                                Context contextCurrentContext3 = this.actual.currentContext();
                                Operators.onDiscardQueueWithClear(this.queue, contextCurrentContext3, null);
                                Operators.onDiscard(this.nextElement, contextCurrentContext3);
                                Operators.onDiscardMultiple(spliterator, zCheckFinite, contextCurrentContext3);
                                return;
                            }
                            j2++;
                            try {
                                if (!hasNext(spliterator)) {
                                    resetCurrent();
                                    zCheckFinite = false;
                                    spliterator = null;
                                    break;
                                }
                            } catch (Throwable th3) {
                                resetCurrent();
                                coreSubscriber.onError(Operators.onOperatorError(this.f2133s, th3, this.actual.currentContext()));
                                return;
                            }
                        } catch (Throwable th4) {
                            resetCurrent();
                            coreSubscriber.onError(Operators.onOperatorError(this.f2133s, th4, this.actual.currentContext()));
                            return;
                        }
                    }
                    if (j2 == j) {
                        if (this.cancelled) {
                            resetCurrent();
                            Context contextCurrentContext4 = this.actual.currentContext();
                            Operators.onDiscardQueueWithClear(this.queue, contextCurrentContext4, null);
                            Operators.onDiscard(this.nextElement, contextCurrentContext4);
                            Operators.onDiscardMultiple(spliterator, zCheckFinite, contextCurrentContext4);
                            return;
                        }
                        boolean z4 = this.done;
                        if (this.queue.isEmpty() && spliterator == null) {
                            z = true;
                        }
                        if (z4 && z) {
                            resetCurrent();
                            coreSubscriber.onComplete();
                            return;
                        }
                    }
                    if (j2 != 0 && j != Long.MAX_VALUE) {
                        REQUESTED.addAndGet(this, -j2);
                    }
                    if (spliterator == null) {
                        continue;
                    }
                }
                this.current = spliterator;
                this.currentKnownToBeFinite = zCheckFinite;
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
        }

        void drain(@Nullable T t) {
            if (WIP.getAndIncrement(this) != 0) {
                if (t == null || !this.cancelled) {
                    return;
                }
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            if (this.fusionMode == 1) {
                drainSync();
            } else {
                drainAsync();
            }
        }

        @Override // java.util.Collection
        public void clear() {
            Context contextCurrentContext = this.actual.currentContext();
            Operators.onDiscard(this.nextElement, contextCurrentContext);
            Operators.onDiscardMultiple(this.current, this.currentKnownToBeFinite, contextCurrentContext);
            resetCurrent();
            Operators.onDiscardQueueWithClear(this.queue, contextCurrentContext, null);
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            if (this.current != null) {
                return !hasNext(r0);
            }
            return this.queue.isEmpty();
        }

        @Override // java.util.Queue
        @Nullable
        public R poll() {
            Spliterator<? extends R> spliterator = this.current;
            while (true) {
                Spliterator<? extends R> spliterator2 = null;
                if (spliterator == null) {
                    T tPoll = this.queue.poll();
                    if (tPoll == null) {
                        return null;
                    }
                    try {
                        spliterator2 = this.mapper.apply(tPoll).spliterator();
                        boolean zCheckFinite = FluxIterable.checkFinite(spliterator2);
                        if (hasNext(spliterator2)) {
                            this.current = spliterator2;
                            this.currentKnownToBeFinite = zCheckFinite;
                            spliterator = spliterator2;
                            break;
                        }
                        spliterator = spliterator2;
                    } catch (Throwable th) {
                        Operators.onDiscard(tPoll, this.actual.currentContext());
                        throw th;
                    }
                } else {
                    if (hasNext(spliterator)) {
                        break;
                    }
                    spliterator = spliterator2;
                }
            }
            R r = (R) Objects.requireNonNull(next(spliterator), "iterator returned null");
            if (!hasNext(spliterator)) {
                resetCurrent();
            }
            return r;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return ((i & 1) == 0 || this.fusionMode != 1) ? 0 : 1;
        }

        @Override // java.util.Collection
        public int size() {
            return this.queue.size();
        }
    }
}
