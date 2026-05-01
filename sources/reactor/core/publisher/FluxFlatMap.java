package reactor.core.publisher;

import android.Manifest;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Predicate;
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
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.FluxHide;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxFlatMap<T, R> extends InternalFluxOperator<T, R> {
    final boolean delayError;
    final Supplier<? extends Queue<R>> innerQueueSupplier;
    final Supplier<? extends Queue<R>> mainQueueSupplier;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int maxConcurrency;
    final int prefetch;

    FluxFlatMap(Flux<? extends T> flux, Function<? super T, ? extends Publisher<? extends R>> function, boolean z, int i, Supplier<? extends Queue<R>> supplier, int i2, Supplier<? extends Queue<R>> supplier2) {
        super(flux);
        if (i2 <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i2);
        }
        if (i <= 0) {
            throw new IllegalArgumentException("maxConcurrency > 0 required but it was " + i);
        }
        this.mapper = (Function) Objects.requireNonNull(function, "mapper");
        this.delayError = z;
        this.prefetch = i2;
        this.maxConcurrency = i;
        this.mainQueueSupplier = (Supplier) Objects.requireNonNull(supplier, "mainQueueSupplier");
        this.innerQueueSupplier = (Supplier) Objects.requireNonNull(supplier2, "innerQueueSupplier");
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        if (trySubscribeScalarMap(this.source, coreSubscriber, this.mapper, false, true)) {
            return null;
        }
        return new FlatMapMain(coreSubscriber, this.mapper, this.delayError, this.maxConcurrency, this.mainQueueSupplier, this.prefetch, this.innerQueueSupplier);
    }

    static <T, R> boolean trySubscribeScalarMap(Publisher<? extends T> publisher, CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Publisher<? extends R>> function, boolean z, boolean z2) {
        Throwable thOnOperatorError;
        Throwable thOnOperatorError2;
        Throwable thOnOperatorError3;
        if (!(publisher instanceof Callable)) {
            return false;
        }
        try {
            Object objCall = ((Callable) publisher).call();
            Manifest manifest = (Object) objCall;
            if (manifest == null) {
                Operators.complete(coreSubscriber);
                return true;
            }
            try {
                Publisher publisher2 = (Publisher) Objects.requireNonNull(function.apply(manifest), "The mapper returned a null Publisher");
                if (publisher2 instanceof Callable) {
                    try {
                        Object objCall2 = ((Callable) publisher2).call();
                        if (objCall2 != null) {
                            coreSubscriber.onSubscribe(Operators.scalarSubscription(coreSubscriber, objCall2));
                        } else {
                            Operators.complete(coreSubscriber);
                        }
                    } catch (Throwable th) {
                        Context contextCurrentContext = coreSubscriber.currentContext();
                        if (z2) {
                            thOnOperatorError3 = Operators.onNextError(manifest, th, contextCurrentContext);
                        } else {
                            thOnOperatorError3 = Operators.onOperatorError(null, th, manifest, contextCurrentContext);
                        }
                        if (thOnOperatorError3 != null) {
                            Operators.error(coreSubscriber, thOnOperatorError3);
                        } else {
                            Operators.complete(coreSubscriber);
                        }
                        return true;
                    }
                } else {
                    CorePublisher fluxOrMono = Operators.toFluxOrMono(publisher2);
                    if (!z || (fluxOrMono instanceof Fuseable)) {
                        fluxOrMono.subscribe((Subscriber) coreSubscriber);
                    } else {
                        fluxOrMono.subscribe((Subscriber) new FluxHide.SuppressFuseableSubscriber(coreSubscriber));
                    }
                }
                return true;
            } catch (Throwable th2) {
                Context contextCurrentContext2 = coreSubscriber.currentContext();
                if (z2) {
                    thOnOperatorError2 = Operators.onNextError(manifest, th2, contextCurrentContext2);
                } else {
                    thOnOperatorError2 = Operators.onOperatorError(null, th2, manifest, contextCurrentContext2);
                }
                if (thOnOperatorError2 != null) {
                    Operators.error(coreSubscriber, thOnOperatorError2);
                } else {
                    Operators.complete(coreSubscriber);
                }
                return true;
            }
        } catch (Throwable th3) {
            Context contextCurrentContext3 = coreSubscriber.currentContext();
            if (z2) {
                thOnOperatorError = Operators.onNextError(null, th3, contextCurrentContext3);
            } else {
                thOnOperatorError = Operators.onOperatorError(th3, contextCurrentContext3);
            }
            if (thOnOperatorError != null) {
                Operators.error(coreSubscriber, thOnOperatorError);
            } else {
                Operators.complete(coreSubscriber);
            }
            return true;
        }
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class FlatMapMain<T, R> extends FlatMapTracker<FlatMapInner<R>> implements InnerOperator<T, R> {
        final CoreSubscriber<? super R> actual;
        volatile boolean cancelled;
        final boolean delayError;
        volatile boolean done;
        volatile Throwable error;
        final Supplier<? extends Queue<R>> innerQueueSupplier;
        int lastIndex;
        final int limit;
        final Supplier<? extends Queue<R>> mainQueueSupplier;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final int maxConcurrency;
        final int prefetch;
        int produced;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2132s;
        volatile Queue<R> scalarQueue;
        volatile int wip;
        static final AtomicReferenceFieldUpdater<FlatMapMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(FlatMapMain.class, Throwable.class, "error");
        static final AtomicLongFieldUpdater<FlatMapMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(FlatMapMain.class, "requested");
        static final AtomicIntegerFieldUpdater<FlatMapMain> WIP = AtomicIntegerFieldUpdater.newUpdater(FlatMapMain.class, "wip");
        static final FlatMapInner[] EMPTY = new FlatMapInner[0];
        static final FlatMapInner[] TERMINATED = new FlatMapInner[0];

        FlatMapMain(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Publisher<? extends R>> function, boolean z, int i, Supplier<? extends Queue<R>> supplier, int i2, Supplier<? extends Queue<R>> supplier2) {
            this.actual = coreSubscriber;
            this.mapper = function;
            this.delayError = z;
            this.maxConcurrency = i;
            this.mainQueueSupplier = supplier;
            this.prefetch = i2;
            this.innerQueueSupplier = supplier2;
            this.limit = Operators.unboundedOrLimit(i);
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.array).filter(new Predicate() { // from class: reactor.core.publisher.FluxFlatMap$FlatMapMain$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Objects.nonNull((FluxFlatMap.FlatMapInner) obj);
                }
            });
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2132s;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done && (this.scalarQueue == null || this.scalarQueue.isEmpty()));
            }
            if (attr == Scannable.Attr.DELAY_ERROR) {
                return Boolean.valueOf(this.delayError);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.maxConcurrency);
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.LARGE_BUFFERED) {
                return Long.valueOf((this.scalarQueue != null ? this.scalarQueue.size() : 0L) + ((long) this.size));
            }
            if (attr != Scannable.Attr.BUFFERED) {
                return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            long size = (this.scalarQueue != null ? this.scalarQueue.size() : 0L) + ((long) this.size);
            if (size <= 2147483647L) {
                return Integer.valueOf((int) size);
            }
            return Integer.MIN_VALUE;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // reactor.core.publisher.FlatMapTracker
        public FlatMapInner<R>[] empty() {
            return EMPTY;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // reactor.core.publisher.FlatMapTracker
        public FlatMapInner<R>[] terminated() {
            return TERMINATED;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // reactor.core.publisher.FlatMapTracker
        public FlatMapInner<R>[] newArray(int i) {
            return new FlatMapInner[i];
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // reactor.core.publisher.FlatMapTracker
        public void setIndex(FlatMapInner<R> flatMapInner, int i) {
            flatMapInner.index = i;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // reactor.core.publisher.FlatMapTracker
        public void unsubscribeEntry(FlatMapInner<R> flatMapInner) {
            flatMapInner.cancel();
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
            if (WIP.getAndIncrement(this) == 0) {
                Operators.onDiscardQueueWithClear(this.scalarQueue, this.actual.currentContext(), null);
                this.scalarQueue = null;
                this.f2132s.cancel();
                unsubscribe();
            }
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2132s, subscription)) {
                this.f2132s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Operators.unboundedOrPrefetch(this.maxConcurrency));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                Publisher publisher = (Publisher) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher");
                if (publisher instanceof Callable) {
                    try {
                        tryEmitScalar(((Callable) publisher).call());
                        return;
                    } catch (Throwable th) {
                        Context contextCurrentContext = this.actual.currentContext();
                        Throwable thOnNextError = Operators.onNextError(t, th, contextCurrentContext);
                        if (thOnNextError == null) {
                            tryEmitScalar(null);
                        } else if (!this.delayError || !Exceptions.addThrowable(ERROR, this, thOnNextError)) {
                            onError(Operators.onOperatorError(this.f2132s, thOnNextError, t, contextCurrentContext));
                        }
                        Operators.onDiscard(t, contextCurrentContext);
                        tryEmitScalar(null);
                        return;
                    }
                }
                FlatMapInner flatMapInner = new FlatMapInner(this, this.prefetch);
                if (add(flatMapInner)) {
                    Operators.toFluxOrMono(publisher).subscribe((Subscriber) flatMapInner);
                } else {
                    Operators.onDiscard(t, this.actual.currentContext());
                }
            } catch (Throwable th2) {
                Context contextCurrentContext2 = this.actual.currentContext();
                Throwable thOnNextError2 = Operators.onNextError(t, th2, contextCurrentContext2, this.f2132s);
                Operators.onDiscard(t, contextCurrentContext2);
                if (thOnNextError2 != null) {
                    onError(thOnNextError2);
                } else {
                    tryEmitScalar(null);
                }
            }
        }

        Queue<R> getOrCreateScalarQueue() {
            Queue<R> queue = this.scalarQueue;
            if (queue != null) {
                return queue;
            }
            Queue<R> queue2 = this.mainQueueSupplier.get();
            this.scalarQueue = queue2;
            return queue2;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else if (Exceptions.addThrowable(ERROR, this, th)) {
                this.done = true;
                drain(null);
            } else {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            drain(null);
        }

        void tryEmitScalar(@Nullable R r) {
            if (r == null) {
                if (this.maxConcurrency != Integer.MAX_VALUE) {
                    int i = this.produced + 1;
                    if (i == this.limit) {
                        this.produced = 0;
                        this.f2132s.request(i);
                        return;
                    } else {
                        this.produced = i;
                        return;
                    }
                }
                return;
            }
            if (this.wip == 0) {
                AtomicIntegerFieldUpdater<FlatMapMain> atomicIntegerFieldUpdater = WIP;
                if (atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                    long j = this.requested;
                    Queue<R> orCreateScalarQueue = this.scalarQueue;
                    if (j != 0 && (orCreateScalarQueue == null || orCreateScalarQueue.isEmpty())) {
                        this.actual.onNext(r);
                        if (j != Long.MAX_VALUE) {
                            REQUESTED.decrementAndGet(this);
                        }
                        if (this.maxConcurrency != Integer.MAX_VALUE) {
                            int i2 = this.produced + 1;
                            if (i2 == this.limit) {
                                this.produced = 0;
                                this.f2132s.request(i2);
                            } else {
                                this.produced = i2;
                            }
                        }
                    } else {
                        if (orCreateScalarQueue == null) {
                            orCreateScalarQueue = getOrCreateScalarQueue();
                        }
                        if (!orCreateScalarQueue.offer(r) && failOverflow(r, this.f2132s)) {
                            this.done = true;
                            drainLoop();
                            return;
                        }
                    }
                    if (atomicIntegerFieldUpdater.decrementAndGet(this) == 0) {
                        if (this.cancelled) {
                            Operators.onDiscard(r, this.actual.currentContext());
                            return;
                        }
                        return;
                    }
                    drainLoop();
                    return;
                }
            }
            if (!getOrCreateScalarQueue().offer(r) && failOverflow(r, this.f2132s)) {
                this.done = true;
            }
            drain(r);
        }

        void tryEmit(FlatMapInner<R> flatMapInner, R r) {
            if (this.wip == 0) {
                AtomicIntegerFieldUpdater<FlatMapMain> atomicIntegerFieldUpdater = WIP;
                if (atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                    long j = this.requested;
                    Queue<R> orCreateInnerQueue = flatMapInner.queue;
                    if (j != 0 && (orCreateInnerQueue == null || orCreateInnerQueue.isEmpty())) {
                        this.actual.onNext(r);
                        if (j != Long.MAX_VALUE) {
                            REQUESTED.decrementAndGet(this);
                        }
                        flatMapInner.request(1L);
                    } else {
                        if (orCreateInnerQueue == null) {
                            orCreateInnerQueue = getOrCreateInnerQueue(flatMapInner);
                        }
                        if (!orCreateInnerQueue.offer(r) && failOverflow(r, flatMapInner)) {
                            flatMapInner.done = true;
                            drainLoop();
                            return;
                        }
                    }
                    if (atomicIntegerFieldUpdater.decrementAndGet(this) == 0) {
                        if (this.cancelled) {
                            Operators.onDiscard(r, this.actual.currentContext());
                            return;
                        }
                        return;
                    }
                    drainLoop();
                    return;
                }
            }
            if (!getOrCreateInnerQueue(flatMapInner).offer(r) && failOverflow(r, flatMapInner)) {
                flatMapInner.done = true;
            }
            drain(r);
        }

        void drain(@Nullable R r) {
            if (WIP.getAndIncrement(this) != 0) {
                if (r == null || !this.cancelled) {
                    return;
                }
                Operators.onDiscard(r, this.actual.currentContext());
                return;
            }
            drainLoop();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:83:0x013a  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x0163  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void drainLoop() {
            /*
                Method dump skipped, instruction units count: 514
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.FluxFlatMap.FlatMapMain.drainLoop():void");
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, @Nullable R r) {
            if (this.cancelled) {
                Context contextCurrentContext = this.actual.currentContext();
                Operators.onDiscard(r, contextCurrentContext);
                Operators.onDiscardQueueWithClear(this.scalarQueue, contextCurrentContext, null);
                this.scalarQueue = null;
                this.f2132s.cancel();
                unsubscribe();
                return true;
            }
            if (this.delayError) {
                if (!z || !z2) {
                    return false;
                }
                Throwable th = this.error;
                if (th != null && th != Exceptions.TERMINATED) {
                    subscriber.onError(Exceptions.terminate(ERROR, this));
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
            if (!z) {
                return false;
            }
            Throwable th2 = this.error;
            if (th2 == null || th2 == Exceptions.TERMINATED) {
                if (!z2) {
                    return false;
                }
                subscriber.onComplete();
                return true;
            }
            Throwable thTerminate = Exceptions.terminate(ERROR, this);
            Context contextCurrentContext2 = this.actual.currentContext();
            Operators.onDiscard(r, contextCurrentContext2);
            Operators.onDiscardQueueWithClear(this.scalarQueue, contextCurrentContext2, null);
            this.scalarQueue = null;
            this.f2132s.cancel();
            unsubscribe();
            subscriber.onError(thTerminate);
            return true;
        }

        void innerError(FlatMapInner<R> flatMapInner, Throwable th) {
            Throwable thOnNextInnerError = Operators.onNextInnerError(th, currentContext(), this.f2132s);
            if (thOnNextInnerError != null) {
                if (Exceptions.addThrowable(ERROR, this, thOnNextInnerError)) {
                    if (!this.delayError) {
                        this.done = true;
                    }
                    flatMapInner.done = true;
                    drain(null);
                    return;
                }
                flatMapInner.done = true;
                Operators.onErrorDropped(thOnNextInnerError, this.actual.currentContext());
                return;
            }
            flatMapInner.done = true;
            drain(null);
        }

        boolean failOverflow(R r, Subscription subscription) {
            Throwable thOnOperatorError = Operators.onOperatorError(subscription, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), r, this.actual.currentContext());
            Operators.onDiscard(r, this.actual.currentContext());
            if (Exceptions.addThrowable(ERROR, this, thOnOperatorError)) {
                return true;
            }
            Operators.onErrorDropped(thOnOperatorError, this.actual.currentContext());
            return false;
        }

        void innerComplete(FlatMapInner<R> flatMapInner) {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            drainLoop();
        }

        Queue<R> getOrCreateInnerQueue(FlatMapInner<R> flatMapInner) {
            Queue<R> queue = flatMapInner.queue;
            if (queue != null) {
                return queue;
            }
            Queue<R> queue2 = this.innerQueueSupplier.get();
            flatMapInner.queue = queue2;
            return queue2;
        }
    }

    static final class FlatMapInner<R> implements InnerConsumer<R>, Subscription {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<FlatMapInner, Subscription> f2130S = AtomicReferenceFieldUpdater.newUpdater(FlatMapInner.class, Subscription.class, "s");
        volatile boolean done;
        int index;
        final int limit;
        final FlatMapMain<?, R> parent;
        final int prefetch;
        long produced;
        volatile Queue<R> queue;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2131s;
        int sourceMode;

        FlatMapInner(FlatMapMain<?, R> flatMapMain, int i) {
            this.parent = flatMapMain;
            this.prefetch = i;
            this.limit = Operators.unboundedOrLimit(i);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2130S, this, subscription)) {
                if (subscription instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription queueSubscription = (Fuseable.QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = 1;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.drain(null);
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = 2;
                        this.queue = queueSubscription;
                    }
                }
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            if (this.sourceMode == 2) {
                this.parent.drain(r);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(r, this.parent.currentContext());
            } else if (this.f2131s == Operators.cancelledSubscription()) {
                Operators.onDiscard(r, this.parent.currentContext());
            } else {
                this.parent.tryEmit(this, r);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.innerError(this, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.innerComplete(this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.sourceMode == 1) {
                return;
            }
            long j2 = this.produced + j;
            if (j2 >= this.limit) {
                this.produced = 0L;
                this.f2131s.request(j2);
            } else {
                this.produced = j2;
            }
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Operators.terminate(f2130S, this);
            Operators.onDiscardQueueWithClear(this.queue, this.parent.currentContext(), null);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2131s;
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
                return Boolean.valueOf(this.f2131s == Operators.cancelledSubscription());
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
    }
}
