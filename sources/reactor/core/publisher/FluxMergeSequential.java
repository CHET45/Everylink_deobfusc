package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxConcatMap;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxMergeSequential<T, R> extends InternalFluxOperator<T, R> {
    final FluxConcatMap.ErrorMode errorMode;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int maxConcurrency;
    final int prefetch;
    final Supplier<Queue<MergeSequentialInner<R>>> queueSupplier;

    FluxMergeSequential(Flux<? extends T> flux, Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, FluxConcatMap.ErrorMode errorMode) {
        this(flux, function, i, i2, errorMode, Queues.get(Math.max(i2, i)));
    }

    FluxMergeSequential(Flux<? extends T> flux, Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, FluxConcatMap.ErrorMode errorMode, Supplier<Queue<MergeSequentialInner<R>>> supplier) {
        super(flux);
        if (i2 <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i2);
        }
        if (i <= 0) {
            throw new IllegalArgumentException("maxConcurrency > 0 required but it was " + i);
        }
        this.mapper = (Function) Objects.requireNonNull(function, "mapper");
        this.maxConcurrency = i;
        this.prefetch = i2;
        this.errorMode = errorMode;
        this.queueSupplier = supplier;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, coreSubscriber, this.mapper, false, false)) {
            return null;
        }
        return new MergeSequentialMain(coreSubscriber, this.mapper, this.maxConcurrency, this.prefetch, this.errorMode, this.queueSupplier);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class MergeSequentialMain<T, R> implements InnerOperator<T, R> {
        final CoreSubscriber<? super R> actual;
        volatile boolean cancelled;
        MergeSequentialInner<R> current;
        volatile boolean done;
        volatile Throwable error;
        final FluxConcatMap.ErrorMode errorMode;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final int maxConcurrency;
        final int prefetch;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2153s;
        final Queue<MergeSequentialInner<R>> subscribers;
        volatile int wip;
        static final AtomicReferenceFieldUpdater<MergeSequentialMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(MergeSequentialMain.class, Throwable.class, "error");
        static final AtomicIntegerFieldUpdater<MergeSequentialMain> WIP = AtomicIntegerFieldUpdater.newUpdater(MergeSequentialMain.class, "wip");
        static final AtomicLongFieldUpdater<MergeSequentialMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(MergeSequentialMain.class, "requested");

        MergeSequentialMain(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2, FluxConcatMap.ErrorMode errorMode, Supplier<Queue<MergeSequentialInner<R>>> supplier) {
            this.actual = coreSubscriber;
            this.mapper = function;
            this.maxConcurrency = i;
            this.prefetch = i2;
            this.errorMode = errorMode;
            this.subscribers = supplier.get();
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers.peek());
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2153s;
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done && this.subscribers.isEmpty());
            }
            if (attr == Scannable.Attr.DELAY_ERROR) {
                return Boolean.valueOf(this.errorMode != FluxConcatMap.ErrorMode.IMMEDIATE);
            }
            return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.maxConcurrency) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(this.subscribers.size()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2153s, subscription)) {
                this.f2153s = subscription;
                this.actual.onSubscribe(this);
                int i = this.maxConcurrency;
                subscription.request(i == Integer.MAX_VALUE ? Long.MAX_VALUE : i);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            try {
                CorePublisher fluxOrMono = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.mapper.apply(t), "publisher"));
                MergeSequentialInner<R> mergeSequentialInner = new MergeSequentialInner<>(this, this.prefetch);
                if (this.cancelled) {
                    return;
                }
                if (!this.subscribers.offer(mergeSequentialInner)) {
                    int size = this.subscribers.size();
                    mergeSequentialInner.cancel();
                    drainAndCancel();
                    onError(Operators.onOperatorError(this.f2153s, new IllegalStateException("Too many subscribers for fluxMergeSequential on item: " + t + "; subscribers: " + size), t, this.actual.currentContext()));
                    return;
                }
                if (this.cancelled) {
                    return;
                }
                fluxOrMono.subscribe((Subscriber) mergeSequentialInner);
                if (this.cancelled) {
                    mergeSequentialInner.cancel();
                    drainAndCancel();
                }
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2153s, th, t, this.actual.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (Exceptions.addThrowable(ERROR, this, th)) {
                this.done = true;
                drain();
            } else {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.f2153s.cancel();
            drainAndCancel();
        }

        void drainAndCancel() {
            if (WIP.getAndIncrement(this) == 0) {
                do {
                    cancelAll();
                } while (WIP.decrementAndGet(this) != 0);
            }
        }

        void cancelAll() {
            MergeSequentialInner<R> mergeSequentialInner = this.current;
            if (mergeSequentialInner != null) {
                mergeSequentialInner.cancel();
            }
            while (true) {
                MergeSequentialInner<R> mergeSequentialInnerPoll = this.subscribers.poll();
                if (mergeSequentialInnerPoll == null) {
                    return;
                } else {
                    mergeSequentialInnerPoll.cancel();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
                drain();
            }
        }

        void innerNext(MergeSequentialInner<R> mergeSequentialInner, R r) {
            if (mergeSequentialInner.queue().offer(r)) {
                drain();
            } else {
                mergeSequentialInner.cancel();
                onError(Operators.onOperatorError(null, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), r, this.actual.currentContext()));
            }
        }

        void innerError(MergeSequentialInner<R> mergeSequentialInner, Throwable th) {
            Throwable thOnNextInnerError = Operators.onNextInnerError(th, currentContext(), this.f2153s);
            if (thOnNextInnerError != null) {
                if (Exceptions.addThrowable(ERROR, this, thOnNextInnerError)) {
                    mergeSequentialInner.setDone();
                    if (this.errorMode != FluxConcatMap.ErrorMode.END) {
                        this.f2153s.cancel();
                    }
                    drain();
                    return;
                }
                Operators.onErrorDropped(thOnNextInnerError, this.actual.currentContext());
                return;
            }
            mergeSequentialInner.setDone();
            drain();
        }

        void innerComplete(MergeSequentialInner<R> mergeSequentialInner) {
            mergeSequentialInner.setDone();
            drain();
        }

        void drain() {
            MergeSequentialInner<R> mergeSequentialInnerPoll;
            int i;
            boolean z;
            long j;
            long j2;
            Queue<R> queue;
            Throwable th;
            Throwable th2;
            Throwable th3;
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            MergeSequentialInner<R> mergeSequentialInner = this.current;
            CoreSubscriber<? super R> coreSubscriber = this.actual;
            FluxConcatMap.ErrorMode errorMode = this.errorMode;
            int iAddAndGet = 1;
            while (true) {
                long j3 = this.requested;
                if (mergeSequentialInner != null) {
                    mergeSequentialInnerPoll = mergeSequentialInner;
                } else {
                    if (errorMode != FluxConcatMap.ErrorMode.END && (th3 = this.error) != null) {
                        cancelAll();
                        coreSubscriber.onError(th3);
                        return;
                    }
                    boolean z2 = this.done;
                    mergeSequentialInnerPoll = this.subscribers.poll();
                    if (z2 && mergeSequentialInnerPoll == null) {
                        Throwable th4 = this.error;
                        if (th4 != null) {
                            coreSubscriber.onError(th4);
                            return;
                        } else {
                            coreSubscriber.onComplete();
                            return;
                        }
                    }
                    if (mergeSequentialInnerPoll != null) {
                        this.current = mergeSequentialInnerPoll;
                    }
                }
                if (mergeSequentialInnerPoll == null || (queue = mergeSequentialInnerPoll.queue()) == null) {
                    i = iAddAndGet;
                    z = false;
                    j = 0;
                    j2 = 0;
                } else {
                    j2 = 0;
                    while (true) {
                        i = iAddAndGet;
                        if (j2 == j3) {
                            break;
                        }
                        if (this.cancelled) {
                            cancelAll();
                            return;
                        }
                        if (errorMode == FluxConcatMap.ErrorMode.IMMEDIATE && (th2 = this.error) != null) {
                            this.current = null;
                            mergeSequentialInnerPoll.cancel();
                            cancelAll();
                            coreSubscriber.onError(th2);
                            return;
                        }
                        boolean zIsDone = mergeSequentialInnerPoll.isDone();
                        try {
                            R rPoll = queue.poll();
                            boolean z3 = rPoll == null;
                            if (zIsDone && z3) {
                                this.current = null;
                                this.f2153s.request(1L);
                                mergeSequentialInnerPoll = null;
                                z = true;
                                break;
                            }
                            if (z3) {
                                break;
                            }
                            coreSubscriber.onNext(rPoll);
                            j2++;
                            mergeSequentialInnerPoll.requestOne();
                            iAddAndGet = i;
                        } catch (Throwable th5) {
                            this.current = null;
                            mergeSequentialInnerPoll.cancel();
                            Throwable thOnOperatorError = Operators.onOperatorError(th5, this.actual.currentContext());
                            cancelAll();
                            coreSubscriber.onError(thOnOperatorError);
                            return;
                        }
                    }
                    z = false;
                    if (j2 == j3) {
                        if (this.cancelled) {
                            cancelAll();
                            return;
                        }
                        if (errorMode == FluxConcatMap.ErrorMode.IMMEDIATE && (th = this.error) != null) {
                            this.current = null;
                            mergeSequentialInnerPoll.cancel();
                            cancelAll();
                            coreSubscriber.onError(th);
                            return;
                        }
                        boolean zIsDone2 = mergeSequentialInnerPoll.isDone();
                        boolean zIsEmpty = queue.isEmpty();
                        if (zIsDone2 && zIsEmpty) {
                            this.current = null;
                            this.f2153s.request(1L);
                            mergeSequentialInnerPoll = null;
                            z = true;
                        }
                    }
                    j = 0;
                }
                if (j2 != j && j3 != Long.MAX_VALUE) {
                    REQUESTED.addAndGet(this, -j2);
                }
                if (z) {
                    mergeSequentialInner = mergeSequentialInnerPoll;
                    iAddAndGet = i;
                } else {
                    iAddAndGet = WIP.addAndGet(this, -i);
                    if (iAddAndGet == 0) {
                        return;
                    } else {
                        mergeSequentialInner = mergeSequentialInnerPoll;
                    }
                }
            }
        }
    }

    static final class MergeSequentialInner<R> implements InnerConsumer<R> {
        static final AtomicReferenceFieldUpdater<MergeSequentialInner, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(MergeSequentialInner.class, Subscription.class, "subscription");
        volatile boolean done;
        int fusionMode;
        final int limit;
        final MergeSequentialMain<?, R> parent;
        final int prefetch;
        long produced;
        volatile Queue<R> queue;
        volatile Subscription subscription;

        MergeSequentialInner(MergeSequentialMain<?, R> mergeSequentialMain, int i) {
            this.parent = mergeSequentialMain;
            this.prefetch = i;
            this.limit = Operators.unboundedOrLimit(i);
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                if (!this.done || (this.queue != null && !this.queue.isEmpty())) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.subscription == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.queue != null ? this.queue.size() : 0);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(SUBSCRIPTION, this, subscription)) {
                if (subscription instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription queueSubscription = (Fuseable.QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.innerComplete(this);
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.fusionMode = iRequestFusion;
                        this.queue = queueSubscription;
                        subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = (Queue) Queues.get(this.prefetch).get();
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            if (this.fusionMode == 0) {
                this.parent.innerNext(this, r);
            } else {
                this.parent.drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.innerError(this, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.innerComplete(this);
        }

        void requestOne() {
            if (this.fusionMode != 1) {
                long j = this.produced + 1;
                if (j == this.limit) {
                    this.produced = 0L;
                    this.subscription.request(j);
                } else {
                    this.produced = j;
                }
            }
        }

        void cancel() {
            Operators.set(SUBSCRIPTION, this, Operators.cancelledSubscription());
        }

        boolean isDone() {
            return this.done;
        }

        void setDone() {
            this.done = true;
        }

        Queue<R> queue() {
            return this.queue;
        }
    }
}
