package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxConcatMap<T, R> extends InternalFluxOperator<T, R> {
    final ErrorMode errorMode;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int prefetch;
    final Supplier<? extends Queue<T>> queueSupplier;

    enum ErrorMode {
        IMMEDIATE,
        BOUNDARY,
        END
    }

    interface FluxConcatMapSupport<I, T> extends InnerOperator<I, T> {
        void innerComplete();

        void innerError(Throwable th);

        void innerNext(T t);
    }

    static <T, R> CoreSubscriber<T> subscriber(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Publisher<? extends R>> function, Supplier<? extends Queue<T>> supplier, int i, ErrorMode errorMode) {
        int iOrdinal = errorMode.ordinal();
        if (iOrdinal == 1) {
            return new ConcatMapDelayed(coreSubscriber, function, supplier, i, false);
        }
        if (iOrdinal == 2) {
            return new ConcatMapDelayed(coreSubscriber, function, supplier, i, true);
        }
        return new ConcatMapImmediate(coreSubscriber, function, supplier, i);
    }

    FluxConcatMap(Flux<? extends T> flux, Function<? super T, ? extends Publisher<? extends R>> function, Supplier<? extends Queue<T>> supplier, int i, ErrorMode errorMode) {
        super(flux);
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.mapper = (Function) Objects.requireNonNull(function, "mapper");
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
        this.prefetch = i;
        this.errorMode = (ErrorMode) Objects.requireNonNull(errorMode, "errorMode");
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, coreSubscriber, this.mapper, false, true)) {
            return null;
        }
        return subscriber(coreSubscriber, this.mapper, this.queueSupplier, this.prefetch, this.errorMode);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ConcatMapImmediate<T, R> implements FluxConcatMapSupport<T, R> {
        volatile boolean active;
        final CoreSubscriber<? super R> actual;
        volatile boolean cancelled;
        int consumed;
        final Context ctx;
        volatile boolean done;
        volatile Throwable error;
        volatile int guard;
        final ConcatMapInner<R> inner = new ConcatMapInner<>(this);
        final int limit;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final int prefetch;
        volatile Queue<T> queue;
        final Supplier<? extends Queue<T>> queueSupplier;

        /* JADX INFO: renamed from: s */
        Subscription f2102s;
        int sourceMode;
        volatile int wip;
        static final AtomicReferenceFieldUpdater<ConcatMapImmediate, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(ConcatMapImmediate.class, Throwable.class, "error");
        static final AtomicIntegerFieldUpdater<ConcatMapImmediate> WIP = AtomicIntegerFieldUpdater.newUpdater(ConcatMapImmediate.class, "wip");
        static final AtomicIntegerFieldUpdater<ConcatMapImmediate> GUARD = AtomicIntegerFieldUpdater.newUpdater(ConcatMapImmediate.class, "guard");

        ConcatMapImmediate(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Publisher<? extends R>> function, Supplier<? extends Queue<T>> supplier, int i) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.mapper = function;
            this.queueSupplier = supplier;
            this.prefetch = i;
            this.limit = Operators.unboundedOrLimit(i);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2102s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done || this.error == Exceptions.TERMINATED);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.queue != null ? this.queue.size() : 0);
            }
            return attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2102s, subscription)) {
                this.f2102s = subscription;
                if (subscription instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription queueSubscription = (Fuseable.QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = 1;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = 2;
                        this.queue = queueSubscription;
                    } else {
                        this.queue = this.queueSupplier.get();
                    }
                } else {
                    this.queue = this.queueSupplier.get();
                }
                this.actual.onSubscribe(this);
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode == 2) {
                drain();
            } else if (!this.queue.offer(t)) {
                onError(Operators.onOperatorError(this.f2102s, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), t, this.ctx));
                Operators.onDiscard(t, this.ctx);
            } else {
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Throwable thTerminate;
            AtomicReferenceFieldUpdater<ConcatMapImmediate, Throwable> atomicReferenceFieldUpdater = ERROR;
            if (Exceptions.addThrowable(atomicReferenceFieldUpdater, this, th)) {
                this.inner.cancel();
                if (GUARD.getAndIncrement(this) != 0 || (thTerminate = Exceptions.terminate(atomicReferenceFieldUpdater, this)) == Exceptions.TERMINATED) {
                    return;
                }
                this.actual.onError(thTerminate);
                Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
                return;
            }
            Operators.onErrorDropped(th, this.ctx);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        @Override // reactor.core.publisher.FluxConcatMap.FluxConcatMapSupport
        public void innerNext(R r) {
            Throwable thTerminate;
            if (this.guard == 0) {
                AtomicIntegerFieldUpdater<ConcatMapImmediate> atomicIntegerFieldUpdater = GUARD;
                if (atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                    this.actual.onNext(r);
                    if (atomicIntegerFieldUpdater.compareAndSet(this, 1, 0) || (thTerminate = Exceptions.terminate(ERROR, this)) == Exceptions.TERMINATED) {
                        return;
                    }
                    this.actual.onError(thTerminate);
                }
            }
        }

        @Override // reactor.core.publisher.FluxConcatMap.FluxConcatMapSupport
        public void innerComplete() {
            this.active = false;
            drain();
        }

        @Override // reactor.core.publisher.FluxConcatMap.FluxConcatMapSupport
        public void innerError(Throwable th) {
            Throwable thTerminate;
            Throwable thOnNextInnerError = Operators.onNextInnerError(th, currentContext(), this.f2102s);
            if (thOnNextInnerError != null) {
                AtomicReferenceFieldUpdater<ConcatMapImmediate, Throwable> atomicReferenceFieldUpdater = ERROR;
                if (Exceptions.addThrowable(atomicReferenceFieldUpdater, this, thOnNextInnerError)) {
                    this.f2102s.cancel();
                    if (GUARD.getAndIncrement(this) != 0 || (thTerminate = Exceptions.terminate(atomicReferenceFieldUpdater, this)) == Exceptions.TERMINATED) {
                        return;
                    }
                    this.actual.onError(thTerminate);
                    Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
                    return;
                }
                Operators.onErrorDropped(thOnNextInnerError, this.ctx);
                return;
            }
            this.active = false;
            drain();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.inner.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.inner.cancel();
            this.f2102s.cancel();
            Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
        }

        void drain() {
            Publisher publisher;
            if (WIP.getAndIncrement(this) == 0) {
                while (!this.cancelled) {
                    if (!this.active) {
                        boolean z = this.done;
                        try {
                            T tPoll = this.queue.poll();
                            boolean z2 = tPoll == null;
                            if (z && z2) {
                                this.actual.onComplete();
                                return;
                            }
                            if (!z2) {
                                try {
                                    publisher = (Publisher) Objects.requireNonNull(this.mapper.apply(tPoll), "The mapper returned a null Publisher");
                                    if (this.sourceMode != 1) {
                                        int i = this.consumed + 1;
                                        if (i == this.limit) {
                                            this.consumed = 0;
                                            this.f2102s.request(i);
                                        } else {
                                            this.consumed = i;
                                        }
                                    }
                                } catch (Throwable th) {
                                    Operators.onDiscard(tPoll, this.ctx);
                                    if (Operators.onNextError(tPoll, th, this.ctx, this.f2102s) != null) {
                                        this.actual.onError(Operators.onOperatorError(this.f2102s, th, tPoll, this.ctx));
                                        return;
                                    }
                                }
                                if (publisher instanceof Callable) {
                                    try {
                                        Object objCall = ((Callable) publisher).call();
                                        if (objCall == null) {
                                            continue;
                                        } else if (this.inner.isUnbounded()) {
                                            if (this.guard == 0) {
                                                AtomicIntegerFieldUpdater<ConcatMapImmediate> atomicIntegerFieldUpdater = GUARD;
                                                if (atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                                                    this.actual.onNext(objCall);
                                                    if (!atomicIntegerFieldUpdater.compareAndSet(this, 1, 0)) {
                                                        Throwable thTerminate = Exceptions.terminate(ERROR, this);
                                                        if (thTerminate != Exceptions.TERMINATED) {
                                                            this.actual.onError(thTerminate);
                                                            return;
                                                        }
                                                        return;
                                                    }
                                                } else {
                                                    continue;
                                                }
                                            } else {
                                                continue;
                                            }
                                        } else {
                                            this.active = true;
                                            this.inner.set(new WeakScalarSubscription(objCall, this.inner));
                                        }
                                    } catch (Throwable th2) {
                                        if (Operators.onNextError(tPoll, th2, this.ctx, this.f2102s) != null) {
                                            this.actual.onError(Operators.onOperatorError(this.f2102s, th2, tPoll, this.ctx));
                                            Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
                                            return;
                                        }
                                    }
                                } else {
                                    this.active = true;
                                    Operators.toFluxOrMono(publisher).subscribe((Subscriber) this.inner);
                                }
                            }
                        } catch (Throwable th3) {
                            this.actual.onError(Operators.onOperatorError(this.f2102s, th3, this.ctx));
                            return;
                        }
                    }
                    if (WIP.decrementAndGet(this) == 0) {
                        return;
                    }
                }
            }
        }
    }

    static final class WeakScalarSubscription<T> implements Subscription {
        final CoreSubscriber<? super T> actual;
        boolean once;
        final T value;

        WeakScalarSubscription(T t, CoreSubscriber<? super T> coreSubscriber) {
            this.value = t;
            this.actual = coreSubscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (j <= 0 || this.once) {
                return;
            }
            this.once = true;
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            coreSubscriber.onNext(this.value);
            coreSubscriber.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Operators.onDiscard(this.value, this.actual.currentContext());
        }
    }

    static final class ConcatMapDelayed<T, R> implements FluxConcatMapSupport<T, R> {
        static final AtomicReferenceFieldUpdater<ConcatMapDelayed, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(ConcatMapDelayed.class, Throwable.class, "error");
        static final AtomicIntegerFieldUpdater<ConcatMapDelayed> WIP = AtomicIntegerFieldUpdater.newUpdater(ConcatMapDelayed.class, "wip");
        volatile boolean active;
        final CoreSubscriber<? super R> actual;
        volatile boolean cancelled;
        int consumed;
        volatile boolean done;
        volatile Throwable error;
        final ConcatMapInner<R> inner = new ConcatMapInner<>(this);
        final int limit;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final int prefetch;
        volatile Queue<T> queue;
        final Supplier<? extends Queue<T>> queueSupplier;

        /* JADX INFO: renamed from: s */
        Subscription f2101s;
        int sourceMode;
        final boolean veryEnd;
        volatile int wip;

        ConcatMapDelayed(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Publisher<? extends R>> function, Supplier<? extends Queue<T>> supplier, int i, boolean z) {
            this.actual = coreSubscriber;
            this.mapper = function;
            this.queueSupplier = supplier;
            this.prefetch = i;
            this.limit = Operators.unboundedOrLimit(i);
            this.veryEnd = z;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2101s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.queue != null ? this.queue.size() : 0);
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.DELAY_ERROR) {
                return true;
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2101s, subscription)) {
                this.f2101s = subscription;
                if (subscription instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription queueSubscription = (Fuseable.QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = 1;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = 2;
                        this.queue = queueSubscription;
                    } else {
                        this.queue = this.queueSupplier.get();
                    }
                } else {
                    this.queue = this.queueSupplier.get();
                }
                this.actual.onSubscribe(this);
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode == 2) {
                drain();
            } else {
                if (!this.queue.offer(t)) {
                    Context contextCurrentContext = this.actual.currentContext();
                    onError(Operators.onOperatorError(this.f2101s, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), t, contextCurrentContext));
                    Operators.onDiscard(t, contextCurrentContext);
                    return;
                }
                drain();
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

        @Override // reactor.core.publisher.FluxConcatMap.FluxConcatMapSupport
        public void innerNext(R r) {
            this.actual.onNext(r);
        }

        @Override // reactor.core.publisher.FluxConcatMap.FluxConcatMapSupport
        public void innerComplete() {
            this.active = false;
            drain();
        }

        @Override // reactor.core.publisher.FluxConcatMap.FluxConcatMapSupport
        public void innerError(Throwable th) {
            Throwable thOnNextInnerError = Operators.onNextInnerError(th, currentContext(), this.f2101s);
            if (thOnNextInnerError != null) {
                if (Exceptions.addThrowable(ERROR, this, thOnNextInnerError)) {
                    if (!this.veryEnd) {
                        this.f2101s.cancel();
                        this.done = true;
                    }
                    this.active = false;
                    drain();
                    return;
                }
                Operators.onErrorDropped(thOnNextInnerError, this.actual.currentContext());
                return;
            }
            this.active = false;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.inner.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.inner.cancel();
            this.f2101s.cancel();
            Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
        }

        void drain() {
            Publisher publisher;
            if (WIP.getAndIncrement(this) == 0) {
                Context contextCurrentContext = null;
                while (!this.cancelled) {
                    if (!this.active) {
                        boolean z = this.done;
                        if (z && !this.veryEnd && this.error != null) {
                            Throwable thTerminate = Exceptions.terminate(ERROR, this);
                            if (thTerminate != Exceptions.TERMINATED) {
                                this.actual.onError(thTerminate);
                                return;
                            }
                            return;
                        }
                        try {
                            T tPoll = this.queue.poll();
                            boolean z2 = tPoll == null;
                            if (z && z2) {
                                Throwable thTerminate2 = Exceptions.terminate(ERROR, this);
                                if (thTerminate2 != null && thTerminate2 != Exceptions.TERMINATED) {
                                    this.actual.onError(thTerminate2);
                                    return;
                                } else {
                                    this.actual.onComplete();
                                    return;
                                }
                            }
                            if (!z2) {
                                try {
                                    publisher = (Publisher) Objects.requireNonNull(this.mapper.apply(tPoll), "The mapper returned a null Publisher");
                                    if (this.sourceMode != 1) {
                                        int i = this.consumed + 1;
                                        if (i == this.limit) {
                                            this.consumed = 0;
                                            this.f2101s.request(i);
                                        } else {
                                            this.consumed = i;
                                        }
                                    }
                                } catch (Throwable th) {
                                    if (contextCurrentContext == null) {
                                        contextCurrentContext = this.actual.currentContext();
                                    }
                                    Operators.onDiscard(tPoll, contextCurrentContext);
                                    if (Operators.onNextError(tPoll, th, contextCurrentContext, this.f2101s) != null) {
                                        this.actual.onError(Operators.onOperatorError(this.f2101s, th, tPoll, contextCurrentContext));
                                        return;
                                    }
                                }
                                if (publisher instanceof Callable) {
                                    try {
                                        Object objCall = ((Callable) publisher).call();
                                        if (objCall == null) {
                                            continue;
                                        } else if (this.inner.isUnbounded()) {
                                            this.actual.onNext(objCall);
                                        } else {
                                            this.active = true;
                                            this.inner.set(new WeakScalarSubscription(objCall, this.inner));
                                        }
                                    } catch (Throwable th2) {
                                        if (contextCurrentContext == null) {
                                            contextCurrentContext = this.actual.currentContext();
                                        }
                                        Throwable thOnNextError = Operators.onNextError(tPoll, th2, contextCurrentContext);
                                        if (thOnNextError == null) {
                                            continue;
                                        } else if (!this.veryEnd || !Exceptions.addThrowable(ERROR, this, thOnNextError)) {
                                            this.actual.onError(Operators.onOperatorError(this.f2101s, thOnNextError, tPoll, contextCurrentContext));
                                            return;
                                        }
                                    }
                                } else {
                                    this.active = true;
                                    Operators.toFluxOrMono(publisher).subscribe((Subscriber) this.inner);
                                }
                            }
                        } catch (Throwable th3) {
                            CoreSubscriber<? super R> coreSubscriber = this.actual;
                            coreSubscriber.onError(Operators.onOperatorError(this.f2101s, th3, coreSubscriber.currentContext()));
                            return;
                        }
                    }
                    if (WIP.decrementAndGet(this) == 0) {
                        return;
                    }
                }
            }
        }
    }

    static final class ConcatMapInner<R> extends Operators.MultiSubscriptionSubscriber<R, R> {
        final FluxConcatMapSupport<?, R> parent;
        long produced;

        ConcatMapInner(FluxConcatMapSupport<?, R> fluxConcatMapSupport) {
            super(Operators.emptySubscriber());
            this.parent = fluxConcatMapSupport;
        }

        @Override // reactor.core.publisher.InnerOperator, reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.ACTUAL ? this.parent : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            this.produced++;
            this.parent.innerNext(r);
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            long j = this.produced;
            if (j != 0) {
                this.produced = 0L;
                produced(j);
            }
            this.parent.innerError(th);
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            long j = this.produced;
            if (j != 0) {
                this.produced = 0L;
                produced(j);
            }
            this.parent.innerComplete();
        }
    }
}
