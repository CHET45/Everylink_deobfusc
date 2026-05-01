package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxWindowTimeout<T> extends InternalFluxOperator<T, Flux<T>> {
    final boolean fairBackpressure;
    final int maxSize;
    final Scheduler timer;
    final long timespan;
    final TimeUnit unit;

    FluxWindowTimeout(Flux<T> flux, int i, long j, TimeUnit timeUnit, Scheduler scheduler, boolean z) {
        super(Flux.from(flux));
        if (j <= 0) {
            throw new IllegalArgumentException("Timeout period must be strictly positive");
        }
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize must be strictly positive");
        }
        this.fairBackpressure = z;
        this.timer = (Scheduler) Objects.requireNonNull(scheduler, "Timer");
        this.timespan = j;
        this.unit = (TimeUnit) Objects.requireNonNull(timeUnit, "unit");
        this.maxSize = i;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Flux<T>> coreSubscriber) {
        if (this.fairBackpressure) {
            return new WindowTimeoutWithBackpressureSubscriber(coreSubscriber, this.maxSize, this.timespan, this.unit, this.timer, null);
        }
        return new WindowTimeoutSubscriber(coreSubscriber, this.maxSize, this.timespan, this.unit, this.timer);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.RUN_ON) {
            return this.timer;
        }
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return super.scanUnsafe(attr);
    }

    static final class WindowTimeoutWithBackpressureSubscriber<T> implements InnerOperator<T, Flux<T>> {
        static final long ACTIVE_WINDOW_INDEX_MASK = 1099510579200L;
        static final int ACTIVE_WINDOW_INDEX_SHIFT = 20;
        static final long CANCELLED_FLAG = Long.MIN_VALUE;
        static final long HAS_UNSENT_WINDOW = 2305843009213693952L;
        static final long HAS_WORK_IN_PROGRESS = 1152921504606846976L;
        static final long NEXT_WINDOW_INDEX_MASK = 1048575;
        static final long REQUEST_INDEX_MASK = 1152920405095219200L;
        static final int REQUEST_INDEX_SHIFT = 40;
        static final long TERMINATED_FLAG = 4611686018427387904L;
        final CoreSubscriber<? super Flux<T>> actual;
        boolean done;
        Throwable error;
        final int limit;

        @Nullable
        final StateLogger logger;
        final int maxSize;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2236s;
        final Scheduler scheduler;
        volatile long state;
        final long timespan;
        final TimeUnit unit;
        InnerWindow<T> window;
        final Scheduler.Worker worker;
        static final AtomicLongFieldUpdater<WindowTimeoutWithBackpressureSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(WindowTimeoutWithBackpressureSubscriber.class, "requested");
        static final AtomicLongFieldUpdater<WindowTimeoutWithBackpressureSubscriber> STATE = AtomicLongFieldUpdater.newUpdater(WindowTimeoutWithBackpressureSubscriber.class, "state");

        static int activeWindowIndex(long j) {
            return (int) ((j & ACTIVE_WINDOW_INDEX_MASK) >> 20);
        }

        static boolean hasUnsentWindow(long j) {
            return (j & 2305843009213693952L) == 2305843009213693952L;
        }

        static boolean hasWorkInProgress(long j) {
            return (j & 1152921504606846976L) == 1152921504606846976L;
        }

        static long incrementActiveWindowIndex(long j) {
            return ((((j & ACTIVE_WINDOW_INDEX_MASK) >> 20) + 1) << 20) & ACTIVE_WINDOW_INDEX_MASK;
        }

        static long incrementNextWindowIndex(long j) {
            return ((j & NEXT_WINDOW_INDEX_MASK) + 1) & NEXT_WINDOW_INDEX_MASK;
        }

        static long incrementRequestIndex(long j) {
            return ((((j & REQUEST_INDEX_MASK) >> 40) + 1) << 40) & REQUEST_INDEX_MASK;
        }

        static boolean isCancelled(long j) {
            return (j & Long.MIN_VALUE) == Long.MIN_VALUE;
        }

        static boolean isTerminated(long j) {
            return (j & TERMINATED_FLAG) == TERMINATED_FLAG;
        }

        static int nextWindowIndex(long j) {
            return (int) (j & NEXT_WINDOW_INDEX_MASK);
        }

        WindowTimeoutWithBackpressureSubscriber(CoreSubscriber<? super Flux<T>> coreSubscriber, int i, long j, TimeUnit timeUnit, Scheduler scheduler, @Nullable StateLogger stateLogger) {
            this.actual = coreSubscriber;
            this.timespan = j;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.maxSize = i;
            this.limit = Operators.unboundedOrLimit(i);
            this.worker = scheduler.createWorker();
            this.logger = stateLogger;
            STATE.lazySet(this, 1L);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2236s, subscription)) {
                this.f2236s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            while (!isCancelled(this.state)) {
                if (this.window.sendNext(t)) {
                    return;
                }
            }
            Operators.onDiscard(t, this.actual.currentContext());
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.error = th;
            this.done = true;
            long jMarkTerminated = markTerminated(this);
            if (isCancelled(jMarkTerminated) || isTerminated(jMarkTerminated)) {
                return;
            }
            InnerWindow<T> innerWindow = this.window;
            if (innerWindow != null) {
                innerWindow.sendError(Exceptions.wrapSource(th));
                if (hasUnsentWindow(jMarkTerminated)) {
                    return;
                }
            }
            if (hasWorkInProgress(jMarkTerminated)) {
                return;
            }
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            long jMarkTerminated = markTerminated(this);
            if (isCancelled(jMarkTerminated) || isTerminated(jMarkTerminated)) {
                return;
            }
            InnerWindow<T> innerWindow = this.window;
            if (innerWindow != null) {
                innerWindow.sendComplete();
                if (hasUnsentWindow(jMarkTerminated)) {
                    return;
                }
            }
            if (hasWorkInProgress(jMarkTerminated)) {
                return;
            }
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.addCap(REQUESTED, this, j) == Long.MAX_VALUE) {
                return;
            }
            while (true) {
                long j2 = this.state;
                if (isCancelled(j2)) {
                    return;
                }
                if (hasWorkInProgress(j2)) {
                    long jIncrementRequestIndex = ((-1152920405095219201L) & j2) | incrementRequestIndex(j2);
                    if (STATE.compareAndSet(this, j2, jIncrementRequestIndex)) {
                        StateLogger stateLogger = this.logger;
                        if (stateLogger != null) {
                            stateLogger.log(toString(), "mre", j2, jIncrementRequestIndex);
                            return;
                        }
                        return;
                    }
                } else {
                    if (!hasUnsentWindow(j2) && (isTerminated(j2) || activeWindowIndex(j2) == nextWindowIndex(j2))) {
                        return;
                    }
                    long j3 = ((-2305843009213693953L) & j2) | 1152921504606846976L;
                    if (STATE.compareAndSet(this, j2, j3)) {
                        StateLogger stateLogger2 = this.logger;
                        if (stateLogger2 != null) {
                            stateLogger2.log(toString(), "mre", j2, j3);
                        }
                        drain(j2, j3);
                        return;
                    }
                }
            }
        }

        void tryCreateNextWindow(int i) {
            long j;
            boolean zHasWorkInProgress;
            long jIncrementNextWindowIndex;
            do {
                j = this.state;
                if (isCancelled(j) || nextWindowIndex(j) != i) {
                    return;
                }
                zHasWorkInProgress = hasWorkInProgress(j);
                if (!zHasWorkInProgress && isTerminated(j) && !hasUnsentWindow(j)) {
                    return;
                } else {
                    jIncrementNextWindowIndex = ((-1048576) & j) | incrementNextWindowIndex(j) | 1152921504606846976L;
                }
            } while (!STATE.compareAndSet(this, j, jIncrementNextWindowIndex));
            if (zHasWorkInProgress) {
                return;
            }
            drain(j, jIncrementNextWindowIndex);
        }

        void drain(long j, long j2) {
            long j3;
            long jIncrementActiveWindowIndex;
            long jCommitWork;
            long jIncrementActiveWindowIndex2;
            long jReceived;
            long j4 = j;
            long jMarkWorkDone = j2;
            while (true) {
                long jDecrementAndGet = this.requested;
                StateLogger stateLogger = this.logger;
                if (stateLogger != null) {
                    stateLogger.log(toString(), "dr" + jDecrementAndGet, j4, jMarkWorkDone);
                }
                boolean zHasUnsentWindow = hasUnsentWindow(j4);
                int iActiveWindowIndex = activeWindowIndex(jMarkWorkDone);
                int iNextWindowIndex = nextWindowIndex(jMarkWorkDone);
                if (iActiveWindowIndex != iNextWindowIndex || zHasUnsentWindow) {
                    if (jDecrementAndGet <= 0) {
                        long j5 = jMarkWorkDone;
                        if (jDecrementAndGet == 0 && !zHasUnsentWindow) {
                            InnerWindow<T> innerWindow = new InnerWindow<>(this.maxSize, this, iNextWindowIndex, true, this.logger);
                            InnerWindow<T> innerWindow2 = this.window;
                            this.window = innerWindow;
                            long jCommitWork2 = commitWork(this, j5, true);
                            jIncrementActiveWindowIndex2 = ((jCommitWork2 & (-2305844108724273153L)) ^ (j5 == jCommitWork2 ? 1152921504606846976L : 0L)) | incrementActiveWindowIndex(jCommitWork2) | 2305843009213693952L;
                            jCommitWork = jCommitWork2 | 2305843009213693952L;
                            if (isCancelled(jIncrementActiveWindowIndex2)) {
                                innerWindow2.sendCancel();
                                innerWindow.sendCancel();
                                innerWindow.cancel();
                                return;
                            }
                            try {
                                innerWindow.scheduleTimeout();
                                long jReceived2 = InnerWindow.received(innerWindow2.sendComplete());
                                if (jReceived2 > 0) {
                                    this.f2236s.request(jReceived2);
                                }
                                if (!hasWorkInProgress(jIncrementActiveWindowIndex2)) {
                                    return;
                                }
                                j4 = jCommitWork;
                                jMarkWorkDone = jIncrementActiveWindowIndex2;
                            } catch (Exception e) {
                                if (hasWorkInProgress(jIncrementActiveWindowIndex2)) {
                                    CoreSubscriber<? super Flux<T>> coreSubscriber = this.actual;
                                    coreSubscriber.onError(Operators.onOperatorError(this.f2236s, e, coreSubscriber.currentContext()));
                                    return;
                                } else {
                                    onError(Operators.onOperatorError(this.f2236s, e, this.actual.currentContext()));
                                    return;
                                }
                            }
                        } else {
                            long jMarkWorkDone2 = markWorkDone(this, j5);
                            long j6 = 1152921504606846976L | jMarkWorkDone2;
                            if (isCancelled(jMarkWorkDone2)) {
                                InnerWindow<T> innerWindow3 = this.window;
                                if (InnerWindow.isSent(innerWindow3.sendCancel())) {
                                    return;
                                }
                                innerWindow3.cancel();
                                return;
                            }
                            if (isTerminated(jMarkWorkDone2) && !hasUnsentWindow(jMarkWorkDone2)) {
                                Throwable th = this.error;
                                if (th != null) {
                                    this.actual.onError(th);
                                    return;
                                } else {
                                    this.actual.onComplete();
                                    return;
                                }
                            }
                            if (!hasWorkInProgress(jMarkWorkDone2)) {
                                return;
                            }
                            jMarkWorkDone = jMarkWorkDone2;
                            j4 = j6;
                        }
                    } else if (zHasUnsentWindow) {
                        InnerWindow<T> innerWindow4 = this.window;
                        this.actual.onNext(innerWindow4);
                        if (jDecrementAndGet != Long.MAX_VALUE) {
                            jDecrementAndGet = REQUESTED.decrementAndGet(this);
                            StateLogger stateLogger2 = this.logger;
                            if (stateLogger2 != null) {
                                stateLogger2.log(toString(), "dec", jDecrementAndGet, jDecrementAndGet);
                            }
                        }
                        long j7 = jDecrementAndGet;
                        long jSendSent = innerWindow4.sendSent();
                        if (isTerminated(jMarkWorkDone)) {
                            Throwable th2 = this.error;
                            if (th2 != null) {
                                if (!isTerminated(jSendSent)) {
                                    innerWindow4.sendError(th2);
                                }
                                this.actual.onError(th2);
                                return;
                            } else {
                                if (!isTerminated(jSendSent)) {
                                    innerWindow4.sendComplete();
                                }
                                this.actual.onComplete();
                                return;
                            }
                        }
                        if (iNextWindowIndex > iActiveWindowIndex && (InnerWindow.isTimeout(jSendSent) || InnerWindow.isTerminated(jSendSent))) {
                            boolean z = j7 == 0;
                            long j8 = jMarkWorkDone;
                            InnerWindow<T> innerWindow5 = new InnerWindow<>(this.maxSize, this, iNextWindowIndex, z, this.logger);
                            this.window = innerWindow5;
                            if (!z) {
                                this.actual.onNext(innerWindow5);
                                if (j7 != Long.MAX_VALUE) {
                                    REQUESTED.decrementAndGet(this);
                                }
                            }
                            long jCommitWork3 = commitWork(this, j8, z);
                            jIncrementActiveWindowIndex = ((jCommitWork3 & (-2305844108724273153L)) ^ (j8 == jCommitWork3 ? 1152921504606846976L : 0L)) | incrementActiveWindowIndex(jCommitWork3) | (z ? 2305843009213693952L : 0L);
                            j3 = (jCommitWork3 & (-2305843009213693953L)) | (z ? 2305843009213693952L : 0L);
                            if (isCancelled(jIncrementActiveWindowIndex)) {
                                innerWindow5.sendCancel();
                                if (z) {
                                    innerWindow5.cancel();
                                    return;
                                }
                                return;
                            }
                            if (isTerminated(jIncrementActiveWindowIndex) && !z) {
                                Throwable th3 = this.error;
                                if (th3 != null) {
                                    innerWindow5.sendError(th3);
                                    this.actual.onError(th3);
                                    return;
                                } else {
                                    innerWindow5.sendComplete();
                                    this.actual.onComplete();
                                    return;
                                }
                            }
                            try {
                                innerWindow5.scheduleTimeout();
                                long jReceived3 = InnerWindow.received(jSendSent);
                                if (jReceived3 > 0) {
                                    this.f2236s.request(jReceived3);
                                }
                                if (!hasWorkInProgress(jIncrementActiveWindowIndex)) {
                                    return;
                                }
                            } catch (Exception e2) {
                                if (hasWorkInProgress(jIncrementActiveWindowIndex)) {
                                    CoreSubscriber<? super Flux<T>> coreSubscriber2 = this.actual;
                                    coreSubscriber2.onError(Operators.onOperatorError(this.f2236s, e2, coreSubscriber2.currentContext()));
                                    return;
                                } else {
                                    onError(Operators.onOperatorError(this.f2236s, e2, this.actual.currentContext()));
                                    return;
                                }
                            }
                        } else {
                            long j9 = jMarkWorkDone;
                            long jCommitSent = commitSent(this, j9);
                            long j10 = jCommitSent & (-2305843009213693953L);
                            long j11 = j10 ^ (j9 == jCommitSent ? 1152921504606846976L : 0L);
                            if (isCancelled(j11)) {
                                return;
                            }
                            if (isTerminated(j11)) {
                                Throwable th4 = this.error;
                                if (th4 != null) {
                                    this.actual.onError(th4);
                                    return;
                                } else {
                                    this.actual.onComplete();
                                    return;
                                }
                            }
                            if (!hasWorkInProgress(j11)) {
                                return;
                            }
                            j3 = j10;
                            jIncrementActiveWindowIndex = j11;
                        }
                        j4 = j3;
                        jMarkWorkDone = jIncrementActiveWindowIndex;
                    } else {
                        long j12 = jMarkWorkDone;
                        InnerWindow<T> innerWindow6 = new InnerWindow<>(this.maxSize, this, iNextWindowIndex, false, this.logger);
                        InnerWindow<T> innerWindow7 = this.window;
                        this.window = innerWindow6;
                        this.actual.onNext(innerWindow6);
                        if (jDecrementAndGet != Long.MAX_VALUE) {
                            REQUESTED.decrementAndGet(this);
                        }
                        jCommitWork = commitWork(this, j12, false);
                        jIncrementActiveWindowIndex2 = ((jCommitWork & (-2305844108724273153L)) ^ (j12 == jCommitWork ? 1152921504606846976L : 0L)) | incrementActiveWindowIndex(jCommitWork);
                        if (isCancelled(jIncrementActiveWindowIndex2)) {
                            innerWindow7.sendCancel();
                            innerWindow6.sendCancel();
                            return;
                        }
                        if (isTerminated(jIncrementActiveWindowIndex2)) {
                            Throwable th5 = this.error;
                            if (th5 != null) {
                                innerWindow7.sendError(th5);
                                innerWindow6.sendError(th5);
                                this.actual.onError(th5);
                                return;
                            } else {
                                innerWindow7.sendComplete();
                                innerWindow6.sendComplete();
                                this.actual.onComplete();
                                return;
                            }
                        }
                        try {
                            innerWindow6.scheduleTimeout();
                            if (innerWindow7 == null) {
                                jReceived = this.maxSize;
                            } else {
                                jReceived = InnerWindow.received(innerWindow7.sendComplete());
                            }
                            if (jReceived > 0) {
                                this.f2236s.request(jReceived);
                            }
                            if (!hasWorkInProgress(jIncrementActiveWindowIndex2)) {
                                return;
                            }
                            j4 = jCommitWork;
                            jMarkWorkDone = jIncrementActiveWindowIndex2;
                        } catch (Exception e3) {
                            if (hasWorkInProgress(jIncrementActiveWindowIndex2)) {
                                CoreSubscriber<? super Flux<T>> coreSubscriber3 = this.actual;
                                coreSubscriber3.onError(Operators.onOperatorError(this.f2236s, e3, coreSubscriber3.currentContext()));
                                return;
                            } else {
                                onError(Operators.onOperatorError(this.f2236s, e3, this.actual.currentContext()));
                                return;
                            }
                        }
                    }
                } else {
                    jMarkWorkDone = markWorkDone(this, jMarkWorkDone);
                    j4 = jMarkWorkDone | 1152921504606846976L;
                    if (isCancelled(jMarkWorkDone)) {
                        return;
                    }
                    if (isTerminated(jMarkWorkDone)) {
                        Throwable th6 = this.error;
                        if (th6 != null) {
                            this.actual.onError(th6);
                            return;
                        } else {
                            this.actual.onComplete();
                            return;
                        }
                    }
                    if (!hasWorkInProgress(jMarkWorkDone)) {
                        return;
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            long jMarkCancelled = markCancelled(this);
            if ((hasWorkInProgress(jMarkCancelled) || !isTerminated(jMarkCancelled) || hasUnsentWindow(jMarkCancelled)) && !isCancelled(jMarkCancelled)) {
                this.f2236s.cancel();
                InnerWindow<T> innerWindow = this.window;
                if (innerWindow == null || InnerWindow.isSent(innerWindow.sendCancel()) || hasWorkInProgress(jMarkCancelled)) {
                    return;
                }
                innerWindow.cancel();
            }
        }

        Disposable schedule(Runnable runnable, long j) {
            if (this.unit.toNanos(this.timespan) - (this.scheduler.now(TimeUnit.NANOSECONDS) - j) > 0) {
                return this.worker.schedule(runnable, this.timespan, this.unit);
            }
            runnable.run();
            return InnerWindow.DISPOSED;
        }

        long now() {
            return this.scheduler.now(TimeUnit.NANOSECONDS);
        }

        static <T> long markTerminated(WindowTimeoutWithBackpressureSubscriber<T> windowTimeoutWithBackpressureSubscriber) {
            long j;
            while (true) {
                j = windowTimeoutWithBackpressureSubscriber.state;
                if (isTerminated(j) || isCancelled(j)) {
                    break;
                }
                long j2 = j | TERMINATED_FLAG;
                if (STATE.compareAndSet(windowTimeoutWithBackpressureSubscriber, j, j2)) {
                    StateLogger stateLogger = windowTimeoutWithBackpressureSubscriber.logger;
                    if (stateLogger != null) {
                        stateLogger.log(windowTimeoutWithBackpressureSubscriber.toString(), "mtd", j, j2);
                    }
                }
            }
            return j;
        }

        static <T> long markCancelled(WindowTimeoutWithBackpressureSubscriber<T> windowTimeoutWithBackpressureSubscriber) {
            long j;
            do {
                j = windowTimeoutWithBackpressureSubscriber.state;
                if ((!hasWorkInProgress(j) && isTerminated(j) && !hasUnsentWindow(j)) || isCancelled(j)) {
                    return j;
                }
            } while (!STATE.compareAndSet(windowTimeoutWithBackpressureSubscriber, j, j | Long.MIN_VALUE));
            return j;
        }

        static <T> long markWorkDone(WindowTimeoutWithBackpressureSubscriber<T> windowTimeoutWithBackpressureSubscriber, long j) {
            long j2;
            long j3;
            do {
                j2 = windowTimeoutWithBackpressureSubscriber.state;
                if (j != j2) {
                    StateLogger stateLogger = windowTimeoutWithBackpressureSubscriber.logger;
                    if (stateLogger != null) {
                        stateLogger.log(windowTimeoutWithBackpressureSubscriber.toString(), "fwd", j2, j2);
                    }
                    return j2;
                }
                j3 = j2 ^ 1152921504606846976L;
            } while (!STATE.compareAndSet(windowTimeoutWithBackpressureSubscriber, j2, j3));
            StateLogger stateLogger2 = windowTimeoutWithBackpressureSubscriber.logger;
            if (stateLogger2 != null) {
                stateLogger2.log(windowTimeoutWithBackpressureSubscriber.toString(), "mwd", j2, j3);
            }
            return j3;
        }

        static <T> long commitSent(WindowTimeoutWithBackpressureSubscriber<T> windowTimeoutWithBackpressureSubscriber, long j) {
            long j2;
            long j3;
            do {
                j2 = windowTimeoutWithBackpressureSubscriber.state;
                j3 = ((-2305843009213693953L) & j2) ^ (j == j2 ? 1152921504606846976L : 0L);
            } while (!STATE.compareAndSet(windowTimeoutWithBackpressureSubscriber, j2, j3));
            StateLogger stateLogger = windowTimeoutWithBackpressureSubscriber.logger;
            if (stateLogger != null) {
                stateLogger.log(windowTimeoutWithBackpressureSubscriber.toString(), "cts", j2, j3);
            }
            return j2;
        }

        static <T> long commitWork(WindowTimeoutWithBackpressureSubscriber<T> windowTimeoutWithBackpressureSubscriber, long j, boolean z) {
            long j2;
            long jIncrementActiveWindowIndex;
            do {
                j2 = windowTimeoutWithBackpressureSubscriber.state;
                jIncrementActiveWindowIndex = (((-2305844108724273153L) & j2) ^ (j == j2 ? 1152921504606846976L : 0L)) | incrementActiveWindowIndex(j2) | (z ? 2305843009213693952L : 0L);
            } while (!STATE.compareAndSet(windowTimeoutWithBackpressureSubscriber, j2, jIncrementActiveWindowIndex));
            StateLogger stateLogger = windowTimeoutWithBackpressureSubscriber.logger;
            if (stateLogger != null) {
                stateLogger.log(windowTimeoutWithBackpressureSubscriber.toString(), "ctw", j2, jIncrementActiveWindowIndex);
            }
            return j2;
        }
    }

    static final class InnerWindow<T> extends Flux<T> implements InnerProducer<T>, Runnable {
        static final long CANCELLED_STATE = 1152921504606846976L;
        static final long FINALIZED_STATE = Long.MIN_VALUE;
        static final long HAS_SUBSCRIBER_SET_STATE = 72057594037927936L;
        static final long HAS_SUBSCRIBER_STATE = 144115188075855872L;
        static final long HAS_VALUES_STATE = 288230376151711744L;
        static final long PARENT_CANCELLED_STATE = 2305843009213693952L;
        static final long RECEIVED_MASK = 36028797002186752L;
        static final long RECEIVED_SHIFT_BITS = 24;
        static final long TERMINATED_STATE = 4611686018427387904L;
        static final long TIMEOUT_STATE = 576460752303423488L;
        static final long UNSENT_STATE = 36028797018963968L;
        static final long WORK_IN_PROGRESS_MAX = 16777215;
        CoreSubscriber<? super T> actual;
        final long createTime;
        Throwable error;
        final int index;

        @Nullable
        final StateLogger logger;
        final int max;
        final WindowTimeoutWithBackpressureSubscriber<T> parent;
        final Queue<T> queue;
        volatile long requested;
        volatile long state;
        volatile Disposable timer;
        static final Disposable DISPOSED = Disposables.disposed();
        static final AtomicLongFieldUpdater<InnerWindow> REQUESTED = AtomicLongFieldUpdater.newUpdater(InnerWindow.class, "requested");
        static final AtomicLongFieldUpdater<InnerWindow> STATE = AtomicLongFieldUpdater.newUpdater(InnerWindow.class, "state");
        static final AtomicReferenceFieldUpdater<InnerWindow, Disposable> TIMER = AtomicReferenceFieldUpdater.newUpdater(InnerWindow.class, Disposable.class, "timer");
        int received = 0;
        int produced = 0;

        static boolean hasSubscribedOnce(long j) {
            return (j & HAS_SUBSCRIBER_STATE) == HAS_SUBSCRIBER_STATE;
        }

        static boolean hasSubscriberSet(long j) {
            return (j & HAS_SUBSCRIBER_SET_STATE) == HAS_SUBSCRIBER_SET_STATE;
        }

        static boolean hasValues(long j) {
            return (j & HAS_VALUES_STATE) == HAS_VALUES_STATE;
        }

        static boolean hasWorkInProgress(long j) {
            return (j & WORK_IN_PROGRESS_MAX) > 0;
        }

        static long incrementReceived(long j) {
            return ((j >> RECEIVED_SHIFT_BITS) + 1) << RECEIVED_SHIFT_BITS;
        }

        static long incrementWork(long j) {
            if (j == WORK_IN_PROGRESS_MAX) {
                return 1L;
            }
            return 1 + j;
        }

        static boolean isCancelled(long j) {
            return (j & 1152921504606846976L) == 1152921504606846976L;
        }

        static boolean isCancelledByParent(long j) {
            return (j & 2305843009213693952L) == 2305843009213693952L;
        }

        static boolean isFinalized(long j) {
            return (j & Long.MIN_VALUE) == Long.MIN_VALUE;
        }

        static boolean isSent(long j) {
            return (j & UNSENT_STATE) == 0;
        }

        static boolean isTerminated(long j) {
            return (j & TERMINATED_STATE) == TERMINATED_STATE;
        }

        static boolean isTimeout(long j) {
            return (j & TIMEOUT_STATE) == TIMEOUT_STATE;
        }

        static long received(long j) {
            return (j & RECEIVED_MASK) >> RECEIVED_SHIFT_BITS;
        }

        InnerWindow(int i, WindowTimeoutWithBackpressureSubscriber<T> windowTimeoutWithBackpressureSubscriber, int i2, boolean z, @Nullable StateLogger stateLogger) {
            this.max = i;
            this.parent = windowTimeoutWithBackpressureSubscriber;
            this.queue = (Queue) Queues.get(i).get();
            this.index = i2;
            this.logger = stateLogger;
            if (z) {
                STATE.lazySet(this, UNSENT_STATE);
                if (stateLogger != null) {
                    stateLogger.log(toString(), "mct", 0L, UNSENT_STATE);
                }
            } else if (stateLogger != null) {
                stateLogger.log(toString(), "mct", 0, 0);
            }
            this.createTime = windowTimeoutWithBackpressureSubscriber.now();
        }

        @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
        public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
            if (hasSubscribedOnce(markSubscribedOnce(this))) {
                Operators.error(coreSubscriber, new IllegalStateException("Only one subscriber allowed"));
                return;
            }
            this.actual = coreSubscriber;
            coreSubscriber.onSubscribe(this);
            long jMarkSubscriberSet = markSubscriberSet(this);
            if (isFinalized(jMarkSubscriberSet) || hasWorkInProgress(jMarkSubscriberSet) || hasValues(jMarkSubscriberSet) || !isTerminated(jMarkSubscriberSet)) {
                return;
            }
            Throwable th = this.error;
            if (th != null) {
                coreSubscriber.onError(th);
            } else {
                coreSubscriber.onComplete();
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            Operators.addCap(REQUESTED, this, j);
            long jMarkHasRequest = markHasRequest(this);
            if (hasWorkInProgress(jMarkHasRequest) || isCancelled(jMarkHasRequest) || isFinalized(jMarkHasRequest) || !hasValues(jMarkHasRequest)) {
                return;
            }
            drain((jMarkHasRequest | HAS_SUBSCRIBER_SET_STATE) + 1);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            long jMarkCancelled = markCancelled(this);
            if (isCancelled(jMarkCancelled) || isFinalized(jMarkCancelled) || hasWorkInProgress(jMarkCancelled)) {
                return;
            }
            clearAndFinalize();
        }

        long sendCancel() {
            long j;
            long jIncrementWork;
            long j2;
            Disposable andSet;
            do {
                j = this.state;
                if (isCancelledByParent(j)) {
                    return j;
                }
                long j3 = ((-16777216) & j) | 6917529027641081856L;
                if (hasSubscriberSet(j)) {
                    jIncrementWork = hasValues(j) ? incrementWork(WORK_IN_PROGRESS_MAX & j) : Long.MIN_VALUE;
                } else {
                    jIncrementWork = 0;
                }
                j2 = j3 | jIncrementWork;
            } while (!STATE.compareAndSet(this, j, j2));
            if (isFinalized(j)) {
                return j;
            }
            if (!isTimeout(j) && (andSet = TIMER.getAndSet(this, DISPOSED)) != null) {
                andSet.dispose();
            }
            if (!hasSubscriberSet(j) || hasWorkInProgress(j)) {
                return j;
            }
            if (isCancelled(j)) {
                clearAndFinalize();
                return j;
            }
            if (hasValues(j)) {
                drain(j2);
            } else {
                this.actual.onComplete();
            }
            return j;
        }

        boolean sendNext(T t) {
            long jMarkHasValues;
            long j;
            int i = this.received + 1;
            if (i > this.max) {
                return false;
            }
            this.received = i;
            this.queue.offer(t);
            if (i == this.max) {
                jMarkHasValues = markHasValuesAndTerminated(this);
                j = (4899916394579099648L | jMarkHasValues) + 1;
                if (!isTimeout(jMarkHasValues)) {
                    Disposable andSet = TIMER.getAndSet(this, DISPOSED);
                    if (andSet != null) {
                        andSet.dispose();
                    }
                    if (!isCancelledByParent(jMarkHasValues)) {
                        this.parent.tryCreateNextWindow(this.index);
                    }
                }
            } else {
                jMarkHasValues = markHasValues(this);
                j = (HAS_VALUES_STATE | jMarkHasValues) + 1;
            }
            if (isFinalized(jMarkHasValues)) {
                if (isCancelledByParent(jMarkHasValues)) {
                    clearQueue();
                    return true;
                }
                if (isCancelled(jMarkHasValues)) {
                    clearQueue();
                    this.parent.f2236s.request(1L);
                    return true;
                }
                if (this.queue.poll() == t) {
                    return false;
                }
                this.parent.f2236s.request(1L);
                return true;
            }
            if (isTimeout(jMarkHasValues) && isTerminated(jMarkHasValues)) {
                this.parent.f2236s.request(1L);
            }
            if (!hasSubscriberSet(jMarkHasValues) || hasWorkInProgress(jMarkHasValues)) {
                return true;
            }
            if (isCancelled(jMarkHasValues)) {
                clearAndFinalize();
                return true;
            }
            drain(j);
            return true;
        }

        long sendComplete() {
            Disposable andSet;
            long jMarkTerminated = markTerminated(this);
            if (!isFinalized(jMarkTerminated) && !isTerminated(jMarkTerminated)) {
                if (!isTimeout(jMarkTerminated) && (andSet = TIMER.getAndSet(this, DISPOSED)) != null) {
                    andSet.dispose();
                }
                if (!hasSubscriberSet(jMarkTerminated) || hasWorkInProgress(jMarkTerminated)) {
                    return jMarkTerminated;
                }
                if (isCancelled(jMarkTerminated)) {
                    clearAndFinalize();
                    return jMarkTerminated;
                }
                if (hasValues(jMarkTerminated)) {
                    drain((TERMINATED_STATE | jMarkTerminated) + 1);
                } else {
                    this.actual.onComplete();
                }
            }
            return jMarkTerminated;
        }

        long sendError(Throwable th) {
            Disposable andSet;
            this.error = th;
            long jMarkTerminated = markTerminated(this);
            if (!isFinalized(jMarkTerminated) && !isTerminated(jMarkTerminated)) {
                if (!isTimeout(jMarkTerminated) && (andSet = TIMER.getAndSet(this, DISPOSED)) != null) {
                    andSet.dispose();
                }
                if (!hasSubscriberSet(jMarkTerminated) || hasWorkInProgress(jMarkTerminated)) {
                    return jMarkTerminated;
                }
                if (isCancelled(jMarkTerminated)) {
                    clearAndFinalize();
                    return jMarkTerminated;
                }
                if (hasValues(jMarkTerminated)) {
                    drain((TERMINATED_STATE | jMarkTerminated) + 1);
                } else {
                    this.actual.onError(th);
                }
            }
            return jMarkTerminated;
        }

        long sendSent() {
            long jMarkSent = markSent(this);
            if (isFinalized(jMarkSent) || !((isTerminated(jMarkSent) || isTimeout(jMarkSent)) && hasSubscriberSet(jMarkSent) && !hasWorkInProgress(jMarkSent))) {
                return jMarkSent;
            }
            if (isCancelled(jMarkSent)) {
                clearAndFinalize();
                return jMarkSent;
            }
            if (hasValues(jMarkSent)) {
                drain(((UNSENT_STATE ^ jMarkSent) | TERMINATED_STATE) + 1);
            } else {
                this.actual.onComplete();
            }
            return jMarkSent;
        }

        void drain(long j) {
            Queue<T> queue = this.queue;
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            do {
                long j2 = this.requested;
                int i = 0;
                boolean z = false;
                while (true) {
                    if (i >= j2) {
                        break;
                    }
                    T tPoll = queue.poll();
                    boolean z2 = tPoll == null;
                    if (checkTerminated(this.produced + i, coreSubscriber, tPoll)) {
                        return;
                    }
                    if (z2) {
                        z = z2;
                        break;
                    } else {
                        coreSubscriber.onNext(tPoll);
                        i++;
                        z = z2;
                    }
                }
                int i2 = this.produced + i;
                this.produced = i2;
                if (checkTerminated(i2, coreSubscriber, null)) {
                    return;
                }
                if (i != 0 && j2 != Long.MAX_VALUE) {
                    REQUESTED.addAndGet(this, -i);
                }
                j = markWorkDone(this, j, !z);
                if (isCancelled(j)) {
                    clearAndFinalize();
                    return;
                }
            } while (hasWorkInProgress(j));
        }

        boolean checkTerminated(int i, CoreSubscriber<? super T> coreSubscriber, @Nullable T t) {
            long j = this.state;
            if (isCancelled(j)) {
                if (t != null) {
                    Operators.onDiscard(t, coreSubscriber.currentContext());
                }
                clearAndFinalize();
                return true;
            }
            if (t != null || received(j) > i || !isTerminated(j) || !markFinalized(j)) {
                return false;
            }
            Throwable th = this.error;
            if (th != null) {
                coreSubscriber.onError(th);
            } else {
                coreSubscriber.onComplete();
            }
            return true;
        }

        void scheduleTimeout() {
            Disposable disposableSchedule = this.parent.schedule(this, this.createTime);
            if (C0162xc40028dd.m5m(TIMER, this, null, disposableSchedule)) {
                return;
            }
            disposableSchedule.dispose();
        }

        @Override // java.lang.Runnable
        public void run() {
            long jMarkTimeout = markTimeout(this);
            if (isTerminated(jMarkTimeout) || isCancelledByParent(jMarkTimeout)) {
                return;
            }
            this.parent.tryCreateNextWindow(this.index);
        }

        void clearAndFinalize() {
            long j;
            do {
                j = this.state;
                clearQueue();
                if (isFinalized(j)) {
                    return;
                }
            } while (!STATE.compareAndSet(this, j, (hasValues(j) ? HAS_VALUES_STATE : 0L) ^ ((Long.MIN_VALUE | j) & (-16777216))));
        }

        boolean markFinalized(long j) {
            return STATE.compareAndSet(this, j, ((Long.MIN_VALUE | j) & (-16777216)) ^ (hasValues(j) ? HAS_VALUES_STATE : 0L));
        }

        void clearQueue() {
            Queue<T> queue = this.queue;
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            Context contextCurrentContext = coreSubscriber != null ? coreSubscriber.currentContext() : this.parent.currentContext();
            while (true) {
                T tPoll = queue.poll();
                if (tPoll == null) {
                    return;
                } else {
                    Operators.onDiscard(tPoll, contextCurrentContext);
                }
            }
        }

        static <T> long markSent(InnerWindow<T> innerWindow) {
            long j;
            long j2;
            do {
                j = innerWindow.state;
                if (isCancelled(j)) {
                    return j;
                }
                long j3 = UNSENT_STATE ^ j;
                long jIncrementWork = 0;
                if (isTimeout(j) || isTerminated(j)) {
                    if (hasSubscriberSet(j)) {
                        jIncrementWork = hasValues(j) ? incrementWork(WORK_IN_PROGRESS_MAX & j) : Long.MIN_VALUE;
                    }
                    jIncrementWork |= TERMINATED_STATE;
                }
                j2 = j3 | jIncrementWork;
            } while (!STATE.compareAndSet(innerWindow, j, j2));
            StateLogger stateLogger = innerWindow.logger;
            if (stateLogger != null) {
                stateLogger.log(innerWindow.toString(), "mst", j, j2);
            }
            return j;
        }

        static <T> long markTimeout(InnerWindow<T> innerWindow) {
            long j;
            long j2;
            do {
                j = innerWindow.state;
                if (isTerminated(j)) {
                    return j;
                }
                j2 = j | TIMEOUT_STATE;
            } while (!STATE.compareAndSet(innerWindow, j, j2));
            StateLogger stateLogger = innerWindow.logger;
            if (stateLogger != null) {
                stateLogger.log(innerWindow.toString(), "mtt", j, j2);
            }
            return j;
        }

        static <T> long markCancelled(InnerWindow<T> innerWindow) {
            long j;
            while (true) {
                j = innerWindow.state;
                if (isCancelled(j) || isFinalized(j)) {
                    break;
                }
                long jIncrementWork = ((-16777216) & j) | 1224979098644774912L | incrementWork(WORK_IN_PROGRESS_MAX & j);
                if (STATE.compareAndSet(innerWindow, j, jIncrementWork)) {
                    StateLogger stateLogger = innerWindow.logger;
                    if (stateLogger != null) {
                        stateLogger.log(innerWindow.toString(), "mcd", j, jIncrementWork);
                    }
                }
            }
            return j;
        }

        static <T> long markHasValues(InnerWindow<T> innerWindow) {
            long j;
            long jIncrementReceived;
            long jIncrementWork;
            long j2;
            do {
                j = innerWindow.state;
                if (isFinalized(j)) {
                    StateLogger stateLogger = innerWindow.logger;
                    if (stateLogger != null) {
                        stateLogger.log(innerWindow.toString(), "fhv", j, j);
                    }
                    return j;
                }
                if (hasSubscriberSet(j)) {
                    jIncrementReceived = incrementReceived(RECEIVED_MASK & j) | HAS_VALUES_STATE | ((-36028797018963968L) & j);
                    jIncrementWork = incrementWork(j & WORK_IN_PROGRESS_MAX);
                } else {
                    jIncrementReceived = incrementReceived(RECEIVED_MASK & j) | HAS_VALUES_STATE | ((-36028797018963968L) & j);
                    jIncrementWork = hasWorkInProgress(j) ? incrementWork(j & WORK_IN_PROGRESS_MAX) : 0L;
                }
                j2 = jIncrementWork | jIncrementReceived;
            } while (!STATE.compareAndSet(innerWindow, j, j2));
            StateLogger stateLogger2 = innerWindow.logger;
            if (stateLogger2 != null) {
                stateLogger2.log(innerWindow.toString(), "mhv", j, j2);
            }
            return j;
        }

        static <T> long markHasValuesAndTerminated(InnerWindow<T> innerWindow) {
            long j;
            long jIncrementReceived;
            long jIncrementWork;
            long j2;
            do {
                j = innerWindow.state;
                if (isFinalized(j)) {
                    StateLogger stateLogger = innerWindow.logger;
                    if (stateLogger != null) {
                        stateLogger.log(innerWindow.toString(), "fht", j, j);
                    }
                    return j;
                }
                long j3 = (-36028797018963968L) & j;
                if (hasSubscriberSet(j)) {
                    jIncrementReceived = j3 | 4899916394579099648L | incrementReceived(RECEIVED_MASK & j);
                    jIncrementWork = incrementWork(j & WORK_IN_PROGRESS_MAX);
                } else {
                    jIncrementReceived = j3 | 4899916394579099648L | incrementReceived(RECEIVED_MASK & j);
                    jIncrementWork = hasWorkInProgress(j) ? incrementWork(j & WORK_IN_PROGRESS_MAX) : 0L;
                }
                j2 = jIncrementReceived | jIncrementWork;
            } while (!STATE.compareAndSet(innerWindow, j, j2));
            StateLogger stateLogger2 = innerWindow.logger;
            if (stateLogger2 != null) {
                stateLogger2.log(innerWindow.toString(), "hvt", j, j2);
            }
            return j;
        }

        static <T> long markHasRequest(InnerWindow<T> innerWindow) {
            long j;
            while (true) {
                j = innerWindow.state;
                if (isCancelled(j) || isFinalized(j)) {
                    break;
                }
                long jIncrementWork = ((-16777216) & j) | HAS_SUBSCRIBER_SET_STATE | (hasValues(j) ? incrementWork(WORK_IN_PROGRESS_MAX & j) : 0L);
                if (STATE.compareAndSet(innerWindow, j, jIncrementWork)) {
                    StateLogger stateLogger = innerWindow.logger;
                    if (stateLogger != null) {
                        stateLogger.log(innerWindow.toString(), "mhr", j, jIncrementWork);
                    }
                }
            }
            return j;
        }

        static <T> long markTerminated(InnerWindow<T> innerWindow) {
            long j;
            long jIncrementWork;
            while (true) {
                j = innerWindow.state;
                if (isFinalized(j) || isTerminated(j)) {
                    break;
                }
                long j2 = ((-16777216) & j) | TERMINATED_STATE;
                if (hasSubscriberSet(j)) {
                    jIncrementWork = hasValues(j) ? incrementWork(WORK_IN_PROGRESS_MAX & j) : Long.MIN_VALUE;
                } else {
                    jIncrementWork = 0;
                }
                long j3 = j2 | jIncrementWork;
                if (STATE.compareAndSet(innerWindow, j, j3)) {
                    StateLogger stateLogger = innerWindow.logger;
                    if (stateLogger != null) {
                        stateLogger.log(innerWindow.toString(), "mtd", j, j3);
                    }
                }
            }
            return j;
        }

        static <T> long markSubscribedOnce(InnerWindow<T> innerWindow) {
            long j;
            long j2;
            do {
                j = innerWindow.state;
                if (hasSubscribedOnce(j)) {
                    return j;
                }
                j2 = j | HAS_SUBSCRIBER_STATE;
            } while (!STATE.compareAndSet(innerWindow, j, j2));
            StateLogger stateLogger = innerWindow.logger;
            if (stateLogger != null) {
                stateLogger.log(innerWindow.toString(), "mso", j, j2);
            }
            return j;
        }

        static <T> long markSubscriberSet(InnerWindow<T> innerWindow) {
            long j;
            while (true) {
                j = innerWindow.state;
                if (isFinalized(j) || hasWorkInProgress(j)) {
                    break;
                }
                long j2 = HAS_SUBSCRIBER_SET_STATE | j | ((!isTerminated(j) || hasValues(j)) ? 0L : Long.MIN_VALUE);
                if (STATE.compareAndSet(innerWindow, j, j2)) {
                    StateLogger stateLogger = innerWindow.logger;
                    if (stateLogger != null) {
                        stateLogger.log(innerWindow.toString(), "mss", j, j2);
                    }
                }
            }
            return j;
        }

        static <T> long markWorkDone(InnerWindow<T> innerWindow, long j, boolean z) {
            long j2 = innerWindow.state;
            if (j != j2) {
                return j2;
            }
            long j3 = ((z ? 0L : HAS_VALUES_STATE) ^ j2) & (-16777216);
            if (STATE.compareAndSet(innerWindow, j2, j3)) {
                StateLogger stateLogger = innerWindow.logger;
                if (stateLogger != null) {
                    stateLogger.log(innerWindow.toString(), "mwd", j2, j3);
                }
                return j3;
            }
            return innerWindow.state;
        }

        @Override // reactor.core.publisher.Flux
        public String toString() {
            return super.toString() + " " + this.index;
        }
    }

    static final class WindowTimeoutSubscriber<T> implements InnerOperator<T, Flux<T>> {
        final CoreSubscriber<? super Flux<T>> actual;
        volatile boolean cancelled;
        int count;
        volatile boolean done;
        Throwable error;
        final int maxSize;
        long producerIndex;
        final Queue<Object> queue = (Queue) Queues.unboundedMultiproducer().get();
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2235s;
        final Scheduler scheduler;
        volatile boolean terminated;
        volatile Disposable timer;
        final long timespan;
        final TimeUnit unit;
        Sinks.Many<T> window;
        volatile int wip;
        final Scheduler.Worker worker;
        static final AtomicLongFieldUpdater<WindowTimeoutSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(WindowTimeoutSubscriber.class, "requested");
        static final AtomicIntegerFieldUpdater<WindowTimeoutSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(WindowTimeoutSubscriber.class, "wip");
        static final AtomicReferenceFieldUpdater<WindowTimeoutSubscriber, Disposable> TIMER = AtomicReferenceFieldUpdater.newUpdater(WindowTimeoutSubscriber.class, Disposable.class, "timer");

        WindowTimeoutSubscriber(CoreSubscriber<? super Flux<T>> coreSubscriber, int i, long j, TimeUnit timeUnit, Scheduler scheduler) {
            this.actual = coreSubscriber;
            this.timespan = j;
            this.unit = timeUnit;
            this.scheduler = scheduler;
            this.maxSize = i;
            this.worker = scheduler.createWorker();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            Sinks.Many<T> many = this.window;
            return many == null ? Stream.empty() : Stream.of(Scannable.from(many));
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2235s;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.CAPACITY) {
                return Integer.valueOf(this.maxSize);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.queue.size());
            }
            if (attr == Scannable.Attr.RUN_ON) {
                return this.worker;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2235s, subscription)) {
                this.f2235s = subscription;
                CoreSubscriber<? super Flux<T>> coreSubscriber = this.actual;
                coreSubscriber.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                Sinks.Many<T> manyOnBackpressureBuffer = Sinks.unsafe().many().unicast().onBackpressureBuffer();
                this.window = manyOnBackpressureBuffer;
                long j = this.requested;
                if (j != 0) {
                    coreSubscriber.onNext(manyOnBackpressureBuffer.asFlux());
                    if (j != Long.MAX_VALUE) {
                        REQUESTED.decrementAndGet(this);
                    }
                    if (OperatorDisposables.replace(TIMER, this, newPeriod())) {
                        subscription.request(Long.MAX_VALUE);
                        return;
                    }
                    return;
                }
                coreSubscriber.onError(Operators.onOperatorError(subscription, Exceptions.failWithOverflow(), this.actual.currentContext()));
            }
        }

        Disposable newPeriod() {
            try {
                Scheduler.Worker worker = this.worker;
                ConsumerIndexHolder consumerIndexHolder = new ConsumerIndexHolder(this.producerIndex, this);
                long j = this.timespan;
                return worker.schedulePeriodically(consumerIndexHolder, j, j, this.unit);
            } catch (Exception e) {
                CoreSubscriber<? super Flux<T>> coreSubscriber = this.actual;
                coreSubscriber.onError(Operators.onRejectedExecution(e, this.f2235s, null, null, coreSubscriber.currentContext()));
                return Disposables.disposed();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.terminated) {
                return;
            }
            AtomicIntegerFieldUpdater<WindowTimeoutSubscriber> atomicIntegerFieldUpdater = WIP;
            if (atomicIntegerFieldUpdater.get(this) == 0 && atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                Sinks.Many<T> many = this.window;
                many.emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
                int i = this.count + 1;
                if (i >= this.maxSize) {
                    this.producerIndex++;
                    this.count = 0;
                    many.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                    long j = this.requested;
                    if (j != 0) {
                        Sinks.Many<T> manyOnBackpressureBuffer = Sinks.unsafe().many().unicast().onBackpressureBuffer();
                        this.window = manyOnBackpressureBuffer;
                        this.actual.onNext(manyOnBackpressureBuffer.asFlux());
                        if (j != Long.MAX_VALUE) {
                            REQUESTED.decrementAndGet(this);
                        }
                        Disposable disposable = this.timer;
                        disposable.dispose();
                        Disposable disposableNewPeriod = newPeriod();
                        if (!C0162xc40028dd.m5m(TIMER, this, disposable, disposableNewPeriod)) {
                            disposableNewPeriod.dispose();
                        }
                    } else {
                        this.window = null;
                        this.actual.onError(Operators.onOperatorError(this.f2235s, Exceptions.failWithOverflow(), t, this.actual.currentContext()));
                        this.timer.dispose();
                        this.worker.dispose();
                        return;
                    }
                } else {
                    this.count = i;
                }
                if (atomicIntegerFieldUpdater.decrementAndGet(this) == 0) {
                    return;
                }
            } else {
                this.queue.offer(t);
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            this.actual.onError(th);
            this.timer.dispose();
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            this.actual.onComplete();
            this.timer.dispose();
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        void drainLoop() {
            Queue<Object> queue = this.queue;
            CoreSubscriber<? super Flux<T>> coreSubscriber = this.actual;
            Sinks.Many<T> manyOnBackpressureBuffer = this.window;
            int iAddAndGet = 1;
            while (!this.terminated) {
                boolean z = this.done;
                Object objPoll = queue.poll();
                boolean z2 = objPoll == null;
                boolean z3 = objPoll instanceof ConsumerIndexHolder;
                if (z && (z2 || z3)) {
                    this.window = null;
                    queue.clear();
                    Throwable th = this.error;
                    if (th != null) {
                        manyOnBackpressureBuffer.emitError(th, Sinks.EmitFailureHandler.FAIL_FAST);
                    } else {
                        manyOnBackpressureBuffer.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                    }
                    this.timer.dispose();
                    this.worker.dispose();
                    return;
                }
                if (z2) {
                    iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else if (z3) {
                    manyOnBackpressureBuffer.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                    this.count = 0;
                    manyOnBackpressureBuffer = Sinks.unsafe().many().unicast().onBackpressureBuffer();
                    this.window = manyOnBackpressureBuffer;
                    long j = this.requested;
                    if (j != 0) {
                        coreSubscriber.onNext(manyOnBackpressureBuffer.asFlux());
                        if (j != Long.MAX_VALUE) {
                            REQUESTED.decrementAndGet(this);
                        }
                    } else {
                        this.window = null;
                        this.queue.clear();
                        coreSubscriber.onError(Operators.onOperatorError(this.f2235s, Exceptions.failWithOverflow(), this.actual.currentContext()));
                        this.timer.dispose();
                        this.worker.dispose();
                        return;
                    }
                } else {
                    manyOnBackpressureBuffer.emitNext(objPoll, Sinks.EmitFailureHandler.FAIL_FAST);
                    int i = this.count + 1;
                    if (i >= this.maxSize) {
                        this.producerIndex++;
                        this.count = 0;
                        manyOnBackpressureBuffer.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                        long j2 = this.requested;
                        if (j2 != 0) {
                            manyOnBackpressureBuffer = Sinks.unsafe().many().unicast().onBackpressureBuffer();
                            this.window = manyOnBackpressureBuffer;
                            this.actual.onNext(manyOnBackpressureBuffer.asFlux());
                            if (j2 != Long.MAX_VALUE) {
                                REQUESTED.decrementAndGet(this);
                            }
                            Disposable disposable = this.timer;
                            disposable.dispose();
                            Disposable disposableNewPeriod = newPeriod();
                            if (!C0162xc40028dd.m5m(TIMER, this, disposable, disposableNewPeriod)) {
                                disposableNewPeriod.dispose();
                            }
                        } else {
                            this.window = null;
                            coreSubscriber.onError(Operators.onOperatorError(this.f2235s, Exceptions.failWithOverflow(), objPoll, this.actual.currentContext()));
                            this.timer.dispose();
                            this.worker.dispose();
                            return;
                        }
                    } else {
                        this.count = i;
                    }
                }
            }
            this.f2235s.cancel();
            queue.clear();
            this.timer.dispose();
            this.worker.dispose();
        }

        boolean enter() {
            return WIP.getAndIncrement(this) == 0;
        }

        static final class ConsumerIndexHolder implements Runnable {
            final long index;
            final WindowTimeoutSubscriber<?> parent;

            ConsumerIndexHolder(long j, WindowTimeoutSubscriber<?> windowTimeoutSubscriber) {
                this.index = j;
                this.parent = windowTimeoutSubscriber;
            }

            @Override // java.lang.Runnable
            public void run() {
                WindowTimeoutSubscriber<?> windowTimeoutSubscriber = this.parent;
                if (!windowTimeoutSubscriber.cancelled) {
                    windowTimeoutSubscriber.queue.offer(this);
                } else {
                    windowTimeoutSubscriber.terminated = true;
                    windowTimeoutSubscriber.timer.dispose();
                    windowTimeoutSubscriber.worker.dispose();
                }
                if (windowTimeoutSubscriber.enter()) {
                    windowTimeoutSubscriber.drainLoop();
                }
            }
        }
    }
}
