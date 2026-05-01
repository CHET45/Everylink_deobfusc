package reactor.core.publisher;

import com.aivox.base.common.Constant;
import java.util.Collection;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.FluxBufferTimeout;
import reactor.core.scheduler.Scheduler;
import reactor.util.Logger;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxBufferTimeout<T, C extends Collection<? super T>> extends InternalFluxOperator<T, C> {
    final int batchSize;
    final Supplier<C> bufferSupplier;
    final boolean fairBackpressure;
    final Logger logger;
    final Scheduler timer;
    final long timespan;
    final TimeUnit unit;

    FluxBufferTimeout(Flux<T> flux, int i, long j, TimeUnit timeUnit, Scheduler scheduler, Supplier<C> supplier, boolean z) {
        super(flux);
        if (j <= 0) {
            throw new IllegalArgumentException("Timeout period must be strictly positive");
        }
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize must be strictly positive");
        }
        this.timer = (Scheduler) Objects.requireNonNull(scheduler, "Timer");
        this.timespan = j;
        this.unit = (TimeUnit) Objects.requireNonNull(timeUnit, "unit");
        this.batchSize = i;
        this.bufferSupplier = (Supplier) Objects.requireNonNull(supplier, "bufferSupplier");
        this.fairBackpressure = z;
        this.logger = null;
    }

    FluxBufferTimeout(Flux<T> flux, int i, long j, TimeUnit timeUnit, Scheduler scheduler, Supplier<C> supplier, boolean z, Logger logger) {
        super(flux);
        if (j <= 0) {
            throw new IllegalArgumentException("Timeout period must be strictly positive");
        }
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize must be strictly positive");
        }
        this.timer = (Scheduler) Objects.requireNonNull(scheduler, "Timer");
        this.timespan = j;
        this.unit = (TimeUnit) Objects.requireNonNull(timeUnit, "unit");
        this.batchSize = i;
        this.bufferSupplier = (Supplier) Objects.requireNonNull(supplier, "bufferSupplier");
        this.fairBackpressure = z;
        this.logger = logger;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super C> coreSubscriber) {
        if (this.fairBackpressure) {
            return new BufferTimeoutWithBackpressureSubscriber(coreSubscriber, this.batchSize, this.timespan, this.unit, this.timer.createWorker(), this.bufferSupplier, null);
        }
        return new BufferTimeoutSubscriber(Operators.serialize(coreSubscriber), this.batchSize, this.timespan, this.unit, this.timer.createWorker(), this.bufferSupplier);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.timer : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
    }

    static final class BufferTimeoutWithBackpressureSubscriber<T, C extends Collection<? super T>> implements InnerOperator<T, C> {
        static final long CANCELLED_FLAG = Long.MIN_VALUE;
        static final long HAS_WORK_IN_PROGRESS_FLAG = 2305843009213693952L;
        static final long INDEX_MASK = 4294967295L;
        private static final int INDEX_SHIFT = 0;
        static final long REQUESTED_INDEX_MASK = 1152921500311879680L;
        private static final int REQUESTED_INDEX_SHIFT = 32;
        static final long TERMINATED_FLAG = 4611686018427387904L;
        static final long TIMEOUT_FLAG = 1152921504606846976L;
        private final CoreSubscriber<? super C> actual;
        private final int batchSize;
        private final Supplier<C> bufferSupplier;
        private final Disposable.Swap currentTimeoutTask = Disposables.swap();
        private boolean done;

        @Nullable
        private Throwable error;

        @Nullable
        private final Logger logger;
        private int outstanding;
        private final int prefetch;
        private final Queue<T> queue;
        private final int replenishMark;
        volatile long requested;
        private volatile long state;

        @Nullable
        private final StateLogger stateLogger;

        @Nullable
        private Subscription subscription;
        private final long timeSpan;
        private final Scheduler.Worker timer;
        private final TimeUnit unit;
        private static final AtomicLongFieldUpdater<BufferTimeoutWithBackpressureSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BufferTimeoutWithBackpressureSubscriber.class, "requested");
        private static final AtomicLongFieldUpdater<BufferTimeoutWithBackpressureSubscriber> STATE = AtomicLongFieldUpdater.newUpdater(BufferTimeoutWithBackpressureSubscriber.class, "state");

        private static long bitwiseIncrement(long j, long j2, long j3, int i) {
            int i2 = (int) j3;
            return (j & (~j2)) | (((((j & j2) >> i2) + ((long) i)) << i2) & j2);
        }

        private static long getIndex(long j) {
            return j & INDEX_MASK;
        }

        private static boolean hasWorkInProgress(long j) {
            return (j & 2305843009213693952L) == 2305843009213693952L;
        }

        private static boolean isCancelled(long j) {
            return (j & Long.MIN_VALUE) == Long.MIN_VALUE;
        }

        private static boolean isTerminated(long j) {
            return (j & TERMINATED_FLAG) == TERMINATED_FLAG;
        }

        private static boolean isTimedOut(long j) {
            return (j & 1152921504606846976L) == 1152921504606846976L;
        }

        private static long resetTimeout(long j) {
            return j & (-1152921504606846977L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static long setCancelled(long j) {
            return j | Long.MIN_VALUE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static long setTerminated(long j) {
            return j | TERMINATED_FLAG;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static long setTimedOut(long j) {
            return j | 1152921504606846976L;
        }

        private static long setWorkInProgress(long j) {
            return j | 2305843009213693952L;
        }

        public BufferTimeoutWithBackpressureSubscriber(CoreSubscriber<? super C> coreSubscriber, int i, long j, TimeUnit timeUnit, Scheduler.Worker worker, Supplier<C> supplier, @Nullable Logger logger) {
            this.actual = coreSubscriber;
            this.batchSize = i;
            this.timeSpan = j;
            this.unit = timeUnit;
            this.timer = worker;
            this.bufferSupplier = supplier;
            this.logger = logger;
            this.stateLogger = logger != null ? new StateLogger(logger) : null;
            int i2 = i << 2;
            this.prefetch = i2;
            this.replenishMark = i << 1;
            this.queue = (Queue) Queues.get(i2).get();
        }

        private static void trace(Logger logger, String str) {
            logger.trace(String.format("[%s][%s]", Long.valueOf(Thread.currentThread().getId()), str));
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.subscription, subscription)) {
                this.subscription = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super C> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            Logger logger = this.logger;
            if (logger != null) {
                trace(logger, "request " + j);
            }
            if (Operators.validate(j)) {
                long jAddCap = Operators.addCap(REQUESTED, this, j);
                if (jAddCap == Long.MAX_VALUE || hasWorkInProgress(forceAddWork(this, new Function() { // from class: reactor.core.publisher.FluxBufferTimeout$BufferTimeoutWithBackpressureSubscriber$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return Long.valueOf(FluxBufferTimeout.BufferTimeoutWithBackpressureSubscriber.incrementRequestIndex(((Long) obj).longValue()));
                    }
                }))) {
                    return;
                }
                drain(jAddCap == 0);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long jForceAddWork;
            Logger logger = this.logger;
            if (logger != null) {
                trace(logger, "onNext " + t);
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            boolean zOffer = this.queue.offer(t);
            if (!zOffer) {
                this.error = Operators.onOperatorError(this.subscription, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), t, this.actual.currentContext());
                Operators.onDiscard(t, this.actual.currentContext());
            }
            if (zOffer) {
                jForceAddWork = forceAddWork(this, new Function() { // from class: reactor.core.publisher.FluxBufferTimeout$BufferTimeoutWithBackpressureSubscriber$$ExternalSyntheticLambda5
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return Long.valueOf(FluxBufferTimeout.BufferTimeoutWithBackpressureSubscriber.incrementIndex(((Long) obj).longValue(), 1));
                    }
                });
                if (getIndex(jForceAddWork) == 0) {
                    try {
                        this.currentTimeoutTask.update(this.timer.schedule(new Runnable() { // from class: reactor.core.publisher.FluxBufferTimeout$BufferTimeoutWithBackpressureSubscriber$$ExternalSyntheticLambda6
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.bufferTimedOut();
                            }
                        }, this.timeSpan, this.unit));
                    } catch (RejectedExecutionException e) {
                        this.error = Operators.onRejectedExecution(e, this.subscription, null, t, this.actual.currentContext());
                        jForceAddWork = forceAddWork(this, new C5139xd7ef94c0());
                    }
                }
            } else {
                jForceAddWork = forceAddWork(this, new C5139xd7ef94c0());
            }
            if (hasWorkInProgress(jForceAddWork)) {
                return;
            }
            drain(false);
        }

        void bufferTimedOut() {
            Logger logger = this.logger;
            if (logger != null) {
                trace(logger, "timedOut");
            }
            if (this.done || hasWorkInProgress(forceAddWork(this, new Function() { // from class: reactor.core.publisher.FluxBufferTimeout$BufferTimeoutWithBackpressureSubscriber$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Long.valueOf(FluxBufferTimeout.BufferTimeoutWithBackpressureSubscriber.setTimedOut(((Long) obj).longValue()));
                }
            }))) {
                return;
            }
            drain(false);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Logger logger = this.logger;
            if (logger != null) {
                trace(logger, "onError " + th);
            }
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.error = th;
            if (hasWorkInProgress(forceAddWork(this, new C5139xd7ef94c0()))) {
                return;
            }
            drain(false);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Logger logger = this.logger;
            if (logger != null) {
                trace(logger, "onComplete");
            }
            if (this.done || hasWorkInProgress(forceAddWork(this, new C5139xd7ef94c0()))) {
                return;
            }
            drain(false);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Logger logger = this.logger;
            if (logger != null) {
                trace(logger, "cancel");
            }
            if (this.done || isCancelled(this.state)) {
                return;
            }
            Subscription subscription = this.subscription;
            if (subscription != null) {
                subscription.cancel();
            }
            if (hasWorkInProgress(forceAddWork(this, new Function() { // from class: reactor.core.publisher.FluxBufferTimeout$BufferTimeoutWithBackpressureSubscriber$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Long.valueOf(FluxBufferTimeout.BufferTimeoutWithBackpressureSubscriber.setCancelled(((Long) obj).longValue()));
                }
            }))) {
                return;
            }
            drain(false);
        }

        private void drain(boolean z) {
            int i;
            int i2;
            Logger logger = this.logger;
            if (logger != null) {
                trace(logger, "drain start");
            }
            boolean z2 = z;
            while (true) {
                long jResetTimeout = this.state;
                if (this.done || isCancelled(jResetTimeout)) {
                    Logger logger2 = this.logger;
                    if (logger2 != null) {
                        trace(logger2, "Discarding entire queue of " + this.queue.size());
                    }
                    Operators.onDiscardQueueWithClear(this.queue, currentContext(), null);
                    if (!hasWorkInProgress(tryClearWip(this, jResetTimeout))) {
                        return;
                    }
                } else {
                    long index = getIndex(jResetTimeout);
                    long jDecrementAndGet = this.requested;
                    boolean z3 = jDecrementAndGet > 0 && (z2 || isTimedOut(jResetTimeout) || isTerminated(jResetTimeout) || index >= ((long) this.batchSize));
                    Logger logger3 = this.logger;
                    if (logger3 != null) {
                        trace(logger3, "should flush: " + z3 + " currentRequest: " + jDecrementAndGet + " index: " + index + " isTerminated: " + isTerminated(jResetTimeout) + " isTimedOut: " + isTimedOut(jResetTimeout));
                    }
                    if (z3) {
                        this.currentTimeoutTask.update(null);
                        int i3 = 0;
                        do {
                            int iFlush = flush();
                            Logger logger4 = this.logger;
                            if (logger4 != null) {
                                trace(logger4, "flushed: " + iFlush);
                            }
                            if (iFlush == 0) {
                                break;
                            }
                            i3 += iFlush;
                            if (jDecrementAndGet != Long.MAX_VALUE) {
                                jDecrementAndGet = REQUESTED.decrementAndGet(this);
                            }
                        } while (jDecrementAndGet != 0);
                        i = i3;
                        z2 = false;
                    } else {
                        i = 0;
                    }
                    boolean zIsTerminated = isTerminated(jResetTimeout);
                    if (i > 0) {
                        this.outstanding -= i;
                    }
                    if (!zIsTerminated && jDecrementAndGet > 0 && (i2 = this.outstanding) < this.replenishMark) {
                        requestMore(this.prefetch - i2);
                    }
                    if (zIsTerminated && this.queue.isEmpty()) {
                        this.done = true;
                        Logger logger5 = this.logger;
                        if (logger5 != null) {
                            trace(logger5, "terminated! error: " + this.error + " queue size: " + this.queue.size());
                        }
                        if (this.error != null) {
                            Operators.onDiscardQueueWithClear(this.queue, currentContext(), null);
                            this.actual.onError(this.error);
                        } else if (this.queue.isEmpty()) {
                            this.actual.onComplete();
                        }
                    }
                    if (i > 0) {
                        final int i4 = -i;
                        forceUpdate(this, new Function() { // from class: reactor.core.publisher.FluxBufferTimeout$BufferTimeoutWithBackpressureSubscriber$$ExternalSyntheticLambda3
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return Long.valueOf(FluxBufferTimeout.BufferTimeoutWithBackpressureSubscriber.resetTimeout(FluxBufferTimeout.BufferTimeoutWithBackpressureSubscriber.incrementIndex(((Long) obj).longValue(), i4)));
                            }
                        });
                        jResetTimeout = resetTimeout(incrementIndex(jResetTimeout, i4));
                    }
                    if (!hasWorkInProgress(tryClearWip(this, jResetTimeout))) {
                        Logger logger6 = this.logger;
                        if (logger6 != null) {
                            trace(logger6, "drain done");
                            return;
                        }
                        return;
                    }
                    Logger logger7 = this.logger;
                    if (logger7 != null) {
                        trace(logger7, "drain repeat");
                    }
                }
            }
        }

        int flush() {
            T tPoll = this.queue.poll();
            int i = 0;
            if (tPoll == null) {
                return 0;
            }
            C c = this.bufferSupplier.get();
            do {
                c.add(tPoll);
                i++;
                if (i >= this.batchSize) {
                    break;
                }
                tPoll = this.queue.poll();
            } while (tPoll != null);
            this.actual.onNext(c);
            return i;
        }

        private void requestMore(int i) {
            Logger logger = this.logger;
            if (logger != null) {
                trace(logger, "requestMore " + i);
            }
            this.outstanding += i;
            ((Subscription) Objects.requireNonNull(this.subscription)).request(i);
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.subscription : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(isCancelled(this.state)) : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(isTerminated(this.state)) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.CAPACITY ? Integer.valueOf(this.prefetch) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(this.queue.size()) : attr == Scannable.Attr.RUN_ON ? this.timer : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static long incrementRequestIndex(long j) {
            return bitwiseIncrement(j, REQUESTED_INDEX_MASK, 32L, 1);
        }

        private static long incrementIndex(long j, int i) {
            return bitwiseIncrement(j, INDEX_MASK, 0L, i);
        }

        private static long forceAddWork(BufferTimeoutWithBackpressureSubscriber<?, ?> bufferTimeoutWithBackpressureSubscriber, Function<Long, Long> function) {
            long j;
            long jLongValue;
            do {
                j = ((BufferTimeoutWithBackpressureSubscriber) bufferTimeoutWithBackpressureSubscriber).state;
                jLongValue = function.apply(Long.valueOf(j)).longValue() | 2305843009213693952L;
            } while (!STATE.compareAndSet(bufferTimeoutWithBackpressureSubscriber, j, jLongValue));
            StateLogger stateLogger = ((BufferTimeoutWithBackpressureSubscriber) bufferTimeoutWithBackpressureSubscriber).stateLogger;
            if (stateLogger != null) {
                stateLogger.log(bufferTimeoutWithBackpressureSubscriber.toString(), "faw", j, jLongValue);
            }
            return j;
        }

        private static long forceUpdate(BufferTimeoutWithBackpressureSubscriber<?, ?> bufferTimeoutWithBackpressureSubscriber, Function<Long, Long> function) {
            long j;
            long jLongValue;
            do {
                j = ((BufferTimeoutWithBackpressureSubscriber) bufferTimeoutWithBackpressureSubscriber).state;
                jLongValue = function.apply(Long.valueOf(j)).longValue();
            } while (!STATE.compareAndSet(bufferTimeoutWithBackpressureSubscriber, j, jLongValue));
            StateLogger stateLogger = ((BufferTimeoutWithBackpressureSubscriber) bufferTimeoutWithBackpressureSubscriber).stateLogger;
            if (stateLogger != null) {
                stateLogger.log(bufferTimeoutWithBackpressureSubscriber.toString(), "fup", j, jLongValue);
            }
            return jLongValue;
        }

        private static <T, C extends Collection<? super T>> long tryClearWip(BufferTimeoutWithBackpressureSubscriber<T, C> bufferTimeoutWithBackpressureSubscriber, long j) {
            long j2;
            long j3;
            do {
                j2 = ((BufferTimeoutWithBackpressureSubscriber) bufferTimeoutWithBackpressureSubscriber).state;
                if (j != j2) {
                    return j2;
                }
                j3 = j2 & (-3458764509525573633L);
            } while (!STATE.compareAndSet(bufferTimeoutWithBackpressureSubscriber, j2, j3));
            StateLogger stateLogger = ((BufferTimeoutWithBackpressureSubscriber) bufferTimeoutWithBackpressureSubscriber).stateLogger;
            if (stateLogger != null) {
                stateLogger.log(bufferTimeoutWithBackpressureSubscriber.toString(), "wcl", j2, j3);
            }
            return j3;
        }
    }

    static final class BufferTimeoutSubscriber<T, C extends Collection<? super T>> implements InnerOperator<T, C> {
        static final int NOT_TERMINATED = 0;
        static final int TERMINATED_WITH_CANCEL = 3;
        static final int TERMINATED_WITH_ERROR = 2;
        static final int TERMINATED_WITH_SUCCESS = 1;
        final CoreSubscriber<? super C> actual;
        final int batchSize;
        final Supplier<C> bufferSupplier;
        volatile long outstanding;
        volatile long requested;
        protected Subscription subscription;
        final Scheduler.Worker timer;
        final long timespan;
        volatile Disposable timespanRegistration;
        final TimeUnit unit;
        volatile C values;
        static final AtomicIntegerFieldUpdater<BufferTimeoutSubscriber> TERMINATED = AtomicIntegerFieldUpdater.newUpdater(BufferTimeoutSubscriber.class, "terminated");
        static final AtomicLongFieldUpdater<BufferTimeoutSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BufferTimeoutSubscriber.class, "requested");
        static final AtomicLongFieldUpdater<BufferTimeoutSubscriber> OUTSTANDING = AtomicLongFieldUpdater.newUpdater(BufferTimeoutSubscriber.class, "outstanding");
        static final AtomicIntegerFieldUpdater<BufferTimeoutSubscriber> INDEX = AtomicIntegerFieldUpdater.newUpdater(BufferTimeoutSubscriber.class, Constant.KEY_INDEX);
        volatile int terminated = 0;
        volatile int index = 0;
        final Runnable flushTask = new Runnable() { // from class: reactor.core.publisher.FluxBufferTimeout$BufferTimeoutSubscriber$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m1960x169981fe();
            }
        };

        BufferTimeoutSubscriber(CoreSubscriber<? super C> coreSubscriber, int i, long j, TimeUnit timeUnit, Scheduler.Worker worker, Supplier<C> supplier) {
            this.actual = coreSubscriber;
            this.timespan = j;
            this.unit = timeUnit;
            this.timer = worker;
            this.batchSize = i;
            this.bufferSupplier = supplier;
        }

        /* JADX INFO: renamed from: lambda$new$0$reactor-core-publisher-FluxBufferTimeout$BufferTimeoutSubscriber */
        /* synthetic */ void m1960x169981fe() {
            int i;
            if (this.terminated == 0) {
                do {
                    i = this.index;
                    if (i == 0) {
                        return;
                    }
                } while (!INDEX.compareAndSet(this, i, 0));
                flushCallback(null);
            }
        }

        protected void doOnSubscribe() {
            this.values = this.bufferSupplier.get();
        }

        void nextCallback(T t) {
            synchronized (this) {
                if (OUTSTANDING.decrementAndGet(this) < 0) {
                    this.actual.onError(Exceptions.failWithOverflow("Unrequested element received"));
                    Context contextCurrentContext = this.actual.currentContext();
                    Operators.onDiscard(t, contextCurrentContext);
                    Operators.onDiscardMultiple(this.values, contextCurrentContext);
                    return;
                }
                C c = this.values;
                if (c == null) {
                    c = (C) Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer");
                    this.values = c;
                }
                c.add(t);
            }
        }

        void flushCallback(@Nullable T t) {
            C c;
            boolean z;
            synchronized (this) {
                c = this.values;
                if (c == null || c.isEmpty()) {
                    z = false;
                } else {
                    this.values = this.bufferSupplier.get();
                    z = true;
                }
            }
            if (z) {
                long j = this.requested;
                if (j != 0) {
                    if (j != Long.MAX_VALUE) {
                        long j2 = j;
                        while (!REQUESTED.compareAndSet(this, j2, j2 - 1)) {
                            j2 = this.requested;
                            if (j2 <= 0) {
                            }
                        }
                        this.actual.onNext(c);
                        return;
                    }
                    this.actual.onNext(c);
                    return;
                }
                cancel();
                this.actual.onError(Exceptions.failWithOverflow("Could not emit buffer due to lack of requests"));
                Operators.onDiscardMultiple(c, this.actual.currentContext());
            }
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.terminated == 3);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.terminated == 2 || this.terminated == 1);
            }
            return attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.CAPACITY ? Integer.valueOf(this.batchSize) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(this.batchSize - this.index) : attr == Scannable.Attr.RUN_ON ? this.timer : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            int i;
            int i2;
            boolean z;
            do {
                i = this.index;
                i2 = i + 1;
                z = i2 % this.batchSize == 0;
            } while (!INDEX.compareAndSet(this, i, z ? 0 : i2));
            if (i2 == 1) {
                try {
                    this.timespanRegistration = this.timer.schedule(this.flushTask, this.timespan, this.unit);
                } catch (RejectedExecutionException e) {
                    Context contextCurrentContext = this.actual.currentContext();
                    onError(Operators.onRejectedExecution(e, this.subscription, null, t, contextCurrentContext));
                    Operators.onDiscard(t, contextCurrentContext);
                    return;
                }
            }
            nextCallback(t);
            if (z) {
                if (this.timespanRegistration != null) {
                    this.timespanRegistration.dispose();
                    this.timespanRegistration = null;
                }
                flushCallback(t);
            }
        }

        void checkedComplete() {
            try {
                flushCallback(null);
            } finally {
                this.actual.onComplete();
            }
        }

        final boolean isCompleted() {
            return this.terminated == 1;
        }

        final boolean isFailed() {
            return this.terminated == 2;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
                if (this.terminated != 0) {
                    return;
                }
                if (this.batchSize == Integer.MAX_VALUE || j == Long.MAX_VALUE) {
                    requestMore(Long.MAX_VALUE);
                    return;
                }
                long jMultiplyCap = Operators.multiplyCap(this.requested, this.batchSize);
                if (jMultiplyCap > this.outstanding) {
                    requestMore(jMultiplyCap - this.outstanding);
                }
            }
        }

        final void requestMore(long j) {
            Subscription subscription = this.subscription;
            if (subscription != null) {
                Operators.addCap(OUTSTANDING, this, j);
                subscription.request(j);
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super C> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (TERMINATED.compareAndSet(this, 0, 1)) {
                this.timer.dispose();
                checkedComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (TERMINATED.compareAndSet(this, 0, 2)) {
                this.timer.dispose();
                Context contextCurrentContext = this.actual.currentContext();
                synchronized (this) {
                    C c = this.values;
                    if (c != null) {
                        Operators.onDiscardMultiple(c, contextCurrentContext);
                        c.clear();
                        this.values = null;
                    }
                }
                this.actual.onError(th);
            }
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.subscription, subscription)) {
                this.subscription = subscription;
                doOnSubscribe();
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (TERMINATED.compareAndSet(this, 0, 3)) {
                this.timer.dispose();
                Subscription subscription = this.subscription;
                if (subscription != null) {
                    this.subscription = null;
                    subscription.cancel();
                }
                C c = this.values;
                if (c != null) {
                    Operators.onDiscardMultiple(c, this.actual.currentContext());
                    c.clear();
                }
            }
        }
    }
}
