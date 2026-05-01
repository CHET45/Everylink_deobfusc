package reactor.core.publisher;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxSwitchMapNoPrefetch<T, R> extends InternalFluxOperator<T, R> {
    static long COMPLETED_MASK = 8;
    static long HAS_REQUEST_MASK = 4294967280L;
    static int HAS_REQUEST_OFFSET = 4;
    static int INDEX_OFFSET = 32;
    static long INNER_COMPLETED_MASK = 4;
    static long INNER_SUBSCRIBED_MASK = 2;
    static long INNER_WIP_MASK = 1;
    static int MAX_HAS_REQUEST = 268435455;
    static long TERMINATED = -1;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxSwitchMapNoPrefetch(Flux<? extends T> flux, Function<? super T, ? extends Publisher<? extends R>> function) {
        super(flux);
        this.mapper = (Function) Objects.requireNonNull(function, "mapper");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, coreSubscriber, this.mapper, false, false)) {
            return null;
        }
        return new SwitchMapMain(coreSubscriber, this.mapper);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SwitchMapMain<T, R> implements InnerOperator<T, R> {
        final CoreSubscriber<? super R> actual;
        boolean done;
        SwitchMapInner<T, R> inner;

        @Nullable
        final StateLogger logger;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2202s;
        volatile long state;
        volatile Throwable throwable;
        static final AtomicReferenceFieldUpdater<SwitchMapMain, Throwable> THROWABLE = AtomicReferenceFieldUpdater.newUpdater(SwitchMapMain.class, Throwable.class, "throwable");
        static final AtomicLongFieldUpdater<SwitchMapMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(SwitchMapMain.class, "requested");
        static final AtomicLongFieldUpdater<SwitchMapMain> STATE = AtomicLongFieldUpdater.newUpdater(SwitchMapMain.class, "state");

        SwitchMapMain(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Publisher<? extends R>> function) {
            this(coreSubscriber, function, null);
        }

        SwitchMapMain(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Publisher<? extends R>> function, @Nullable StateLogger stateLogger) {
            this.actual = coreSubscriber;
            this.mapper = function;
            this.logger = stateLogger;
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            long j = this.state;
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(!this.done && j == FluxSwitchMapNoPrefetch.TERMINATED);
            }
            return attr == Scannable.Attr.PARENT ? this.f2202s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.ERROR ? this.throwable : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.inner);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2202s, subscription)) {
                this.f2202s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            SwitchMapInner<T, R> switchMapInner = this.inner;
            if (switchMapInner == null) {
                SwitchMapInner<T, R> switchMapInner2 = new SwitchMapInner<>(this, this.actual, 0, this.logger);
                this.inner = switchMapInner2;
                subscribeInner(t, switchMapInner2, 0);
                return;
            }
            int i = switchMapInner.index + 1;
            SwitchMapInner<T, R> switchMapInner3 = new SwitchMapInner<>(this, this.actual, i, this.logger);
            this.inner = switchMapInner3;
            switchMapInner.nextInner = switchMapInner3;
            switchMapInner.nextElement = t;
            long jIncrementIndex = FluxSwitchMapNoPrefetch.incrementIndex(this);
            if (jIncrementIndex == FluxSwitchMapNoPrefetch.TERMINATED) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            if (FluxSwitchMapNoPrefetch.isInnerSubscribed(jIncrementIndex)) {
                switchMapInner.cancelFromParent();
                if (FluxSwitchMapNoPrefetch.isWip(jIncrementIndex)) {
                    return;
                }
                long j = switchMapInner.produced;
                if (j > 0) {
                    switchMapInner.produced = 0L;
                    if (this.requested != Long.MAX_VALUE) {
                        switchMapInner.requested = 0L;
                        REQUESTED.addAndGet(this, -j);
                    }
                }
                subscribeInner(t, switchMapInner3, i);
            }
        }

        void subscribeInner(T t, SwitchMapInner<T, R> switchMapInner, int i) {
            Context contextCurrentContext = this.actual.currentContext();
            while (switchMapInner.index != i) {
                Operators.onDiscard(t, contextCurrentContext);
                t = switchMapInner.nextElement;
                switchMapInner = switchMapInner.nextInner;
            }
            try {
                Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null publisher")).subscribe((Subscriber) switchMapInner);
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2202s, th, t, contextCurrentContext));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            AtomicReferenceFieldUpdater<SwitchMapMain, Throwable> atomicReferenceFieldUpdater = THROWABLE;
            if (!Exceptions.addThrowable(atomicReferenceFieldUpdater, this, th)) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            long terminated = FluxSwitchMapNoPrefetch.setTerminated(this);
            if (terminated == FluxSwitchMapNoPrefetch.TERMINATED) {
                return;
            }
            SwitchMapInner<T, R> switchMapInner = this.inner;
            if (switchMapInner != null && FluxSwitchMapNoPrefetch.isInnerSubscribed(terminated)) {
                switchMapInner.cancelFromParent();
            }
            this.actual.onError(Exceptions.terminate(atomicReferenceFieldUpdater, this));
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            long mainCompleted = FluxSwitchMapNoPrefetch.setMainCompleted(this);
            if (mainCompleted == FluxSwitchMapNoPrefetch.TERMINATED) {
                return;
            }
            if (this.inner == null || FluxSwitchMapNoPrefetch.hasInnerCompleted(mainCompleted)) {
                this.actual.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                long jAddRequest = FluxSwitchMapNoPrefetch.addRequest(this, Operators.addCap(REQUESTED, this, j));
                if (jAddRequest != FluxSwitchMapNoPrefetch.TERMINATED && FluxSwitchMapNoPrefetch.hasRequest(jAddRequest) == 1 && FluxSwitchMapNoPrefetch.isInnerSubscribed(jAddRequest) && !FluxSwitchMapNoPrefetch.hasInnerCompleted(jAddRequest)) {
                    SwitchMapInner<T, R> switchMapInner = this.inner;
                    if (switchMapInner.index == FluxSwitchMapNoPrefetch.index(jAddRequest)) {
                        switchMapInner.request(j);
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            long terminated = FluxSwitchMapNoPrefetch.setTerminated(this);
            if (terminated == FluxSwitchMapNoPrefetch.TERMINATED) {
                return;
            }
            SwitchMapInner<T, R> switchMapInner = this.inner;
            if (switchMapInner != null && FluxSwitchMapNoPrefetch.isInnerSubscribed(terminated) && !FluxSwitchMapNoPrefetch.hasInnerCompleted(terminated) && switchMapInner.index == FluxSwitchMapNoPrefetch.index(terminated)) {
                switchMapInner.cancelFromParent();
            }
            if (FluxSwitchMapNoPrefetch.hasMainCompleted(terminated)) {
                return;
            }
            this.f2202s.cancel();
            Throwable thTerminate = Exceptions.terminate(THROWABLE, this);
            if (thTerminate != null) {
                Operators.onErrorDropped(thTerminate, this.actual.currentContext());
            }
        }
    }

    static final class SwitchMapInner<T, R> implements InnerConsumer<R> {
        final CoreSubscriber<? super R> actual;
        boolean done;
        final int index;

        @Nullable
        final StateLogger logger;
        T nextElement;
        SwitchMapInner<T, R> nextInner;
        final SwitchMapMain<T, R> parent;
        long produced;
        long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2201s;

        SwitchMapInner(SwitchMapMain<T, R> switchMapMain, CoreSubscriber<? super R> coreSubscriber, int i, @Nullable StateLogger stateLogger) {
            this.parent = switchMapMain;
            this.actual = coreSubscriber;
            this.index = i;
            this.logger = stateLogger;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isCancelledByParent());
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2201s, subscription)) {
                this.f2201s = subscription;
                int i = this.index;
                SwitchMapMain<T, R> switchMapMain = this.parent;
                long innerSubscribed = FluxSwitchMapNoPrefetch.setInnerSubscribed(switchMapMain, i);
                if (innerSubscribed == FluxSwitchMapNoPrefetch.TERMINATED) {
                    subscription.cancel();
                    return;
                }
                int iIndex = FluxSwitchMapNoPrefetch.index(innerSubscribed);
                if (i != iIndex) {
                    subscription.cancel();
                    switchMapMain.subscribeInner(this.nextElement, this.nextInner, iIndex);
                } else if (FluxSwitchMapNoPrefetch.hasRequest(innerSubscribed) > 0) {
                    long j = switchMapMain.requested;
                    this.requested = j;
                    subscription.request(j);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            long j;
            boolean z;
            if (this.done) {
                Operators.onNextDropped(r, this.actual.currentContext());
                return;
            }
            SwitchMapMain<T, R> switchMapMain = this.parent;
            Subscription subscription = this.f2201s;
            int i = this.index;
            long jAddAndGet = this.requested;
            long wip = FluxSwitchMapNoPrefetch.setWip(switchMapMain, i);
            if (wip == FluxSwitchMapNoPrefetch.TERMINATED) {
                Operators.onDiscard(r, this.actual.currentContext());
                return;
            }
            if (FluxSwitchMapNoPrefetch.index(wip) != i) {
                Operators.onDiscard(r, this.actual.currentContext());
                return;
            }
            this.actual.onNext(r);
            int iHasRequest = FluxSwitchMapNoPrefetch.hasRequest(wip);
            if (jAddAndGet != Long.MAX_VALUE) {
                j = this.produced + 1;
                this.produced = j;
                if (iHasRequest > 1) {
                    long j2 = switchMapMain.requested;
                    long j3 = j2 - jAddAndGet;
                    if (j3 > 0) {
                        this.requested = j2;
                        if (j2 == Long.MAX_VALUE) {
                            this.produced = 0L;
                            subscription.request(Long.MAX_VALUE);
                            jAddAndGet = j2;
                            j = 0;
                        } else {
                            subscription.request(j3);
                            jAddAndGet = j2;
                        }
                    }
                }
                z = j == jAddAndGet;
                if (z) {
                    this.produced = 0L;
                    jAddAndGet = SwitchMapMain.REQUESTED.addAndGet(switchMapMain, -j);
                    this.requested = jAddAndGet;
                    boolean z2 = jAddAndGet == 0;
                    if (!z2) {
                        subscription.request(jAddAndGet);
                    }
                    z = z2;
                    j = 0;
                }
            } else {
                j = 0;
                z = false;
            }
            while (true) {
                long jUnsetWip = FluxSwitchMapNoPrefetch.unsetWip(switchMapMain, i, z, iHasRequest);
                if (jUnsetWip == FluxSwitchMapNoPrefetch.TERMINATED) {
                    return;
                }
                int iIndex = FluxSwitchMapNoPrefetch.index(jUnsetWip);
                if (i != iIndex) {
                    if (j > 0) {
                        this.produced = 0L;
                        this.requested = 0L;
                        SwitchMapMain.REQUESTED.addAndGet(switchMapMain, -j);
                    }
                    switchMapMain.subscribeInner(this.nextElement, this.nextInner, iIndex);
                    return;
                }
                int iHasRequest2 = FluxSwitchMapNoPrefetch.hasRequest(jUnsetWip);
                if (!z || iHasRequest >= iHasRequest2) {
                    return;
                }
                long j4 = switchMapMain.requested;
                long j5 = j4 - jAddAndGet;
                if (j5 > 0) {
                    this.requested = j4;
                    subscription.request(j4 == Long.MAX_VALUE ? Long.MAX_VALUE : j5);
                    iHasRequest = iHasRequest2;
                    jAddAndGet = j4;
                    z = false;
                } else {
                    iHasRequest = iHasRequest2;
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            SwitchMapMain<T, R> switchMapMain = this.parent;
            if (!Exceptions.addThrowable(SwitchMapMain.THROWABLE, switchMapMain, th)) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            long terminated = FluxSwitchMapNoPrefetch.setTerminated(switchMapMain);
            if (terminated == FluxSwitchMapNoPrefetch.TERMINATED) {
                return;
            }
            if (!FluxSwitchMapNoPrefetch.hasMainCompleted(terminated)) {
                switchMapMain.f2202s.cancel();
            }
            this.actual.onError(Exceptions.terminate(SwitchMapMain.THROWABLE, switchMapMain));
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            SwitchMapMain<T, R> switchMapMain = this.parent;
            int i = this.index;
            long wip = FluxSwitchMapNoPrefetch.setWip(switchMapMain, i);
            if (wip != FluxSwitchMapNoPrefetch.TERMINATED && FluxSwitchMapNoPrefetch.index(wip) == i) {
                long j = this.produced;
                if (j > 0) {
                    this.produced = 0L;
                    this.requested = 0L;
                    SwitchMapMain.REQUESTED.addAndGet(switchMapMain, -j);
                }
                if (FluxSwitchMapNoPrefetch.hasMainCompleted(wip)) {
                    this.actual.onComplete();
                    return;
                }
                long innerCompleted = FluxSwitchMapNoPrefetch.setInnerCompleted(switchMapMain);
                if (innerCompleted == FluxSwitchMapNoPrefetch.TERMINATED) {
                    return;
                }
                int iIndex = FluxSwitchMapNoPrefetch.index(innerCompleted);
                if (i != iIndex) {
                    switchMapMain.subscribeInner(this.nextElement, this.nextInner, iIndex);
                } else if (FluxSwitchMapNoPrefetch.hasMainCompleted(innerCompleted)) {
                    this.actual.onComplete();
                }
            }
        }

        void request(long j) {
            this.requested = Operators.addCap(this.requested, j);
            this.f2201s.request(j);
        }

        boolean isCancelledByParent() {
            long j = this.parent.state;
            return !(this.index == FluxSwitchMapNoPrefetch.index(j) || this.done) || (!this.parent.done && j == FluxSwitchMapNoPrefetch.TERMINATED);
        }

        void cancelFromParent() {
            this.f2201s.cancel();
        }

        public String toString() {
            return new StringJoiner(", ", SwitchMapInner.class.getSimpleName() + "[", "]").add("index=" + this.index).toString();
        }
    }

    static long setTerminated(SwitchMapMain<?, ?> switchMapMain) {
        long j;
        do {
            j = switchMapMain.state;
            long j2 = TERMINATED;
            if (j == j2) {
                return j2;
            }
        } while (!SwitchMapMain.STATE.compareAndSet(switchMapMain, j, TERMINATED));
        if (switchMapMain.logger != null) {
            switchMapMain.logger.log(switchMapMain.toString(), "std", j, TERMINATED);
        }
        return j;
    }

    static long setMainCompleted(SwitchMapMain<?, ?> switchMapMain) {
        long j;
        long j2;
        do {
            j = switchMapMain.state;
            long j3 = TERMINATED;
            if (j == j3) {
                return j3;
            }
            long j4 = COMPLETED_MASK;
            if ((j & j4) == j4) {
                return j;
            }
            j2 = j | j4;
        } while (!SwitchMapMain.STATE.compareAndSet(switchMapMain, j, j2));
        if (switchMapMain.logger != null) {
            switchMapMain.logger.log(switchMapMain.toString(), "smc", j, j2);
        }
        return j;
    }

    static long addRequest(SwitchMapMain<?, ?> switchMapMain, long j) {
        long j2;
        long jState;
        do {
            j2 = switchMapMain.state;
            long j3 = TERMINATED;
            if (j2 == j3) {
                return j3;
            }
            int iHasRequest = hasRequest(j2);
            if (iHasRequest == 0 && j > 0) {
                return j2;
            }
            jState = state(index(j2), isWip(j2), iHasRequest + 1, isInnerSubscribed(j2), hasMainCompleted(j2), hasInnerCompleted(j2));
        } while (!SwitchMapMain.STATE.compareAndSet(switchMapMain, j2, jState));
        if (switchMapMain.logger != null) {
            switchMapMain.logger.log(switchMapMain.toString(), "adr", j2, jState);
        }
        return jState;
    }

    static long incrementIndex(SwitchMapMain<?, ?> switchMapMain) {
        long j;
        long j2 = switchMapMain.state;
        long j3 = TERMINATED;
        if (j2 == j3) {
            return j3;
        }
        int iNextIndex = nextIndex(j2);
        do {
            long jState = state(iNextIndex, isWip(j2), hasRequest(j2), false, false, false);
            if (SwitchMapMain.STATE.compareAndSet(switchMapMain, j2, jState)) {
                if (switchMapMain.logger != null) {
                    switchMapMain.logger.log(switchMapMain.toString(), "ini", j2, jState);
                }
                return j2;
            }
            j2 = switchMapMain.state;
            j = TERMINATED;
        } while (j2 != j);
        return j;
    }

    static long setInnerSubscribed(SwitchMapMain<?, ?> switchMapMain, int i) {
        long j;
        long jState;
        do {
            j = switchMapMain.state;
            long j2 = TERMINATED;
            if (j == j2) {
                return j2;
            }
            if (i != index(j)) {
                return j;
            }
            jState = state(i, false, hasRequest(j), true, hasMainCompleted(j), false);
        } while (!SwitchMapMain.STATE.compareAndSet(switchMapMain, j, jState));
        if (switchMapMain.logger != null) {
            switchMapMain.logger.log(switchMapMain.toString(), "sns", j, jState);
        }
        return j;
    }

    static long setWip(SwitchMapMain<?, ?> switchMapMain, int i) {
        long j;
        long jState;
        do {
            j = switchMapMain.state;
            long j2 = TERMINATED;
            if (j == j2) {
                return j2;
            }
            if (i != index(j)) {
                return j;
            }
            jState = state(i, true, hasRequest(j), true, hasMainCompleted(j), false);
        } while (!SwitchMapMain.STATE.compareAndSet(switchMapMain, j, jState));
        if (switchMapMain.logger != null) {
            switchMapMain.logger.log(switchMapMain.toString(), "swp", j, jState);
        }
        return j;
    }

    static long unsetWip(SwitchMapMain<?, ?> switchMapMain, int i, boolean z, int i2) {
        long j;
        long jState;
        do {
            j = switchMapMain.state;
            long j2 = TERMINATED;
            if (j == j2) {
                return j2;
            }
            int iIndex = index(j);
            int iHasRequest = hasRequest(j);
            boolean z2 = i == iIndex;
            if (z && i2 < iHasRequest && z2) {
                return j;
            }
            jState = state(iIndex, false, (z && i2 == iHasRequest) ? 0 : iHasRequest, isInnerSubscribed(j), hasMainCompleted(j), false);
        } while (!SwitchMapMain.STATE.compareAndSet(switchMapMain, j, jState));
        if (switchMapMain.logger != null) {
            switchMapMain.logger.log(switchMapMain.toString(), "uwp", j, jState);
        }
        return j;
    }

    static long setInnerCompleted(SwitchMapMain<?, ?> switchMapMain) {
        long j;
        long jState;
        do {
            j = switchMapMain.state;
            long j2 = TERMINATED;
            if (j == j2) {
                return j2;
            }
            boolean zIsInnerSubscribed = isInnerSubscribed(j);
            jState = state(index(j), false, hasRequest(j), zIsInnerSubscribed, hasMainCompleted(j), zIsInnerSubscribed);
        } while (!SwitchMapMain.STATE.compareAndSet(switchMapMain, j, jState));
        if (switchMapMain.logger != null) {
            switchMapMain.logger.log(switchMapMain.toString(), "sic", j, jState);
        }
        return j;
    }

    static long state(int i, boolean z, int i2, boolean z2, boolean z3, boolean z4) {
        return (z ? INNER_WIP_MASK : 0L) | (((long) i) << INDEX_OFFSET) | (((long) Math.max(Math.min(i2, MAX_HAS_REQUEST), 0)) << HAS_REQUEST_OFFSET) | (z2 ? INNER_SUBSCRIBED_MASK : 0L) | (z3 ? COMPLETED_MASK : 0L) | (z4 ? INNER_COMPLETED_MASK : 0L);
    }

    static boolean isInnerSubscribed(long j) {
        long j2 = INNER_SUBSCRIBED_MASK;
        return (j & j2) == j2;
    }

    static boolean hasMainCompleted(long j) {
        long j2 = COMPLETED_MASK;
        return (j & j2) == j2;
    }

    static boolean hasInnerCompleted(long j) {
        long j2 = INNER_COMPLETED_MASK;
        return (j & j2) == j2;
    }

    static int hasRequest(long j) {
        return ((int) (j & HAS_REQUEST_MASK)) >> HAS_REQUEST_OFFSET;
    }

    static int index(long j) {
        return (int) (j >>> INDEX_OFFSET);
    }

    static int nextIndex(long j) {
        return ((int) (j >>> INDEX_OFFSET)) + 1;
    }

    static boolean isWip(long j) {
        long j2 = INNER_WIP_MASK;
        return (j & j2) == j2;
    }
}
