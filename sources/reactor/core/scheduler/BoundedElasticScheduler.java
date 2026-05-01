package reactor.core.scheduler;

import androidx.concurrent.futures.C0162xc40028dd;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.BoundedElasticScheduler;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.SchedulerState;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class BoundedElasticScheduler implements Scheduler, SchedulerState.DisposeAwaiter<BoundedServices>, Scannable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int DEFAULT_TTL_SECONDS = 60;
    final Clock clock;
    final ThreadFactory factory;
    final int maxTaskQueuedPerThread;
    final int maxThreads;
    volatile SchedulerState<BoundedServices> state;
    final long ttlMillis;
    static final Logger LOGGER = Loggers.getLogger((Class<?>) BoundedElasticScheduler.class);
    static final AtomicLong COUNTER = new AtomicLong();
    static final AtomicReferenceFieldUpdater<BoundedElasticScheduler, SchedulerState> STATE = AtomicReferenceFieldUpdater.newUpdater(BoundedElasticScheduler.class, SchedulerState.class, "state");
    private static final SchedulerState<BoundedServices> INIT = SchedulerState.init(BoundedServices.SHUTDOWN);

    BoundedElasticScheduler(int i, int i2, ThreadFactory threadFactory, long j, Clock clock) {
        if (j <= 0) {
            throw new IllegalArgumentException("TTL must be strictly positive, was " + j + "ms");
        }
        if (i <= 0) {
            throw new IllegalArgumentException("maxThreads must be strictly positive, was " + i);
        }
        if (i2 <= 0) {
            throw new IllegalArgumentException("maxTaskQueuedPerThread must be strictly positive, was " + i2);
        }
        this.maxThreads = i;
        this.maxTaskQueuedPerThread = i2;
        this.factory = threadFactory;
        this.clock = (Clock) Objects.requireNonNull(clock, "A Clock must be provided");
        this.ttlMillis = j;
        STATE.lazySet(this, INIT);
    }

    BoundedElasticScheduler(int i, int i2, ThreadFactory threadFactory, int i3) {
        this(i, i2, threadFactory, ((long) i3) * 1000, Clock.tickSeconds(BoundedServices.ZONE_UTC));
    }

    BoundedScheduledExecutorService createBoundedExecutorService() {
        return new BoundedScheduledExecutorService(this.maxTaskQueuedPerThread, this.factory);
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        SchedulerState<BoundedServices> schedulerState = this.state;
        return schedulerState != INIT && schedulerState.currentResource == BoundedServices.SHUTDOWN;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // reactor.core.scheduler.Scheduler
    public void init() {
        SchedulerState<BoundedServices> schedulerState = this.state;
        SchedulerState<BoundedServices> schedulerState2 = INIT;
        if (schedulerState != schedulerState2) {
            if (schedulerState.currentResource == BoundedServices.SHUTDOWN) {
                throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
            }
            return;
        }
        SchedulerState schedulerStateInit = SchedulerState.init(new BoundedServices(this));
        if (C0162xc40028dd.m5m(STATE, this, schedulerState2, schedulerStateInit)) {
            try {
                ScheduledExecutorService scheduledExecutorService = ((BoundedServices) schedulerStateInit.currentResource).evictor;
                BoundedServices boundedServices = (BoundedServices) schedulerStateInit.currentResource;
                Objects.requireNonNull(boundedServices);
                BoundedElasticScheduler$$ExternalSyntheticLambda2 boundedElasticScheduler$$ExternalSyntheticLambda2 = new BoundedElasticScheduler$$ExternalSyntheticLambda2(boundedServices);
                long j = this.ttlMillis;
                scheduledExecutorService.scheduleAtFixedRate(boundedElasticScheduler$$ExternalSyntheticLambda2, j, j, TimeUnit.MILLISECONDS);
                return;
            } catch (RejectedExecutionException unused) {
                throw new IllegalStateException("Scheduler disposed during initialization");
            }
        }
        ((BoundedServices) schedulerStateInit.currentResource).evictor.shutdownNow();
        if (isDisposed()) {
            throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // reactor.core.scheduler.Scheduler
    public void start() {
        SchedulerState<BoundedServices> schedulerState = this.state;
        if (schedulerState.currentResource != BoundedServices.SHUTDOWN) {
            return;
        }
        SchedulerState schedulerStateInit = SchedulerState.init(new BoundedServices(this));
        if (C0162xc40028dd.m5m(STATE, this, schedulerState, schedulerStateInit)) {
            try {
                ScheduledExecutorService scheduledExecutorService = ((BoundedServices) schedulerStateInit.currentResource).evictor;
                BoundedServices boundedServices = (BoundedServices) schedulerStateInit.currentResource;
                Objects.requireNonNull(boundedServices);
                BoundedElasticScheduler$$ExternalSyntheticLambda2 boundedElasticScheduler$$ExternalSyntheticLambda2 = new BoundedElasticScheduler$$ExternalSyntheticLambda2(boundedServices);
                long j = this.ttlMillis;
                scheduledExecutorService.scheduleAtFixedRate(boundedElasticScheduler$$ExternalSyntheticLambda2, j, j, TimeUnit.MILLISECONDS);
                return;
            } catch (RejectedExecutionException unused) {
            }
        }
        ((BoundedServices) schedulerStateInit.currentResource).evictor.shutdownNow();
    }

    @Override // reactor.core.scheduler.SchedulerState.DisposeAwaiter
    public boolean await(BoundedServices boundedServices, long j, TimeUnit timeUnit) throws InterruptedException {
        if (!boundedServices.evictor.awaitTermination(j, timeUnit)) {
            return false;
        }
        for (BoundedState boundedState : boundedServices.busyStates.array) {
            if (!boundedState.executor.awaitTermination(j, timeUnit)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // reactor.core.scheduler.Scheduler, reactor.core.Disposable
    public void dispose() {
        SchedulerState<BoundedServices> schedulerState = this.state;
        int i = 0;
        if (schedulerState.currentResource == BoundedServices.SHUTDOWN) {
            if (schedulerState.initialResource != null) {
                schedulerState.initialResource.evictor.shutdownNow();
                BoundedState[] boundedStateArr = schedulerState.initialResource.busyStates.array;
                int length = boundedStateArr.length;
                while (i < length) {
                    boundedStateArr[i].shutdown(true);
                    i++;
                }
                return;
            }
            return;
        }
        BoundedState[] boundedStateArrDispose = schedulerState.currentResource.dispose();
        SchedulerState schedulerStateTransition = SchedulerState.transition(schedulerState.currentResource, BoundedServices.SHUTDOWN, this);
        C0162xc40028dd.m5m(STATE, this, schedulerState, schedulerStateTransition);
        ((BoundedServices) schedulerStateTransition.initialResource).evictor.shutdownNow();
        int length2 = boundedStateArrDispose.length;
        while (i < length2) {
            boundedStateArrDispose[i].shutdown(true);
            i++;
        }
    }

    @Override // reactor.core.scheduler.Scheduler
    public Mono<Void> disposeGracefully() {
        return Mono.defer(new Supplier() { // from class: reactor.core.scheduler.BoundedElasticScheduler$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m1976x2973bf72();
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$disposeGracefully$0$reactor-core-scheduler-BoundedElasticScheduler */
    /* synthetic */ Mono m1976x2973bf72() {
        SchedulerState<BoundedServices> schedulerState = this.state;
        if (schedulerState.currentResource == BoundedServices.SHUTDOWN) {
            return schedulerState.onDispose;
        }
        BoundedState[] boundedStateArrDispose = schedulerState.currentResource.dispose();
        SchedulerState schedulerStateTransition = SchedulerState.transition(schedulerState.currentResource, BoundedServices.SHUTDOWN, this);
        C0162xc40028dd.m5m(STATE, this, schedulerState, schedulerStateTransition);
        ((BoundedServices) schedulerStateTransition.initialResource).evictor.shutdown();
        for (BoundedState boundedState : boundedStateArrDispose) {
            boundedState.shutdown(false);
        }
        return schedulerStateTransition.onDispose;
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedule(Runnable runnable) {
        BoundedState boundedStatePick = this.state.currentResource.pick();
        try {
            return Schedulers.directSchedule(boundedStatePick.executor, runnable, boundedStatePick, 0L, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            boundedStatePick.dispose();
            throw e;
        }
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        BoundedState boundedStatePick = this.state.currentResource.pick();
        try {
            return Schedulers.directSchedule(boundedStatePick.executor, runnable, boundedStatePick, j, timeUnit);
        } catch (RejectedExecutionException e) {
            boundedStatePick.dispose();
            throw e;
        }
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedulePeriodically(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        BoundedState boundedStatePick = this.state.currentResource.pick();
        try {
            return Disposables.composite(Schedulers.directSchedulePeriodically(boundedStatePick.executor, runnable, j, j2, timeUnit), boundedStatePick);
        } catch (RejectedExecutionException e) {
            boundedStatePick.dispose();
            throw e;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("boundedElastic(");
        if (this.factory instanceof ReactorThreadFactory) {
            sb.append('\"').append(((ReactorThreadFactory) this.factory).get()).append("\",");
        }
        StringBuilder sbAppend = sb.append("maxThreads=").append(this.maxThreads).append(",maxTaskQueuedPerThread=");
        int i = this.maxTaskQueuedPerThread;
        sbAppend.append(i == Integer.MAX_VALUE ? "unbounded" : Integer.valueOf(i)).append(",ttl=");
        long j = this.ttlMillis;
        if (j < 1000) {
            sb.append(j).append("ms)");
        } else {
            sb.append(j / 1000).append("s)");
        }
        return sb.toString();
    }

    int estimateSize() {
        return this.state.currentResource.get();
    }

    int estimateBusy() {
        return this.state.currentResource.busyStates.array.length;
    }

    int estimateIdle() {
        return this.state.currentResource.idleQueue.size();
    }

    int estimateRemainingTaskCapacity() {
        BoundedState[] boundedStateArr = this.state.currentResource.busyStates.array;
        int i = this.maxTaskQueuedPerThread * this.maxThreads;
        for (BoundedState boundedState : boundedStateArr) {
            int iEstimateQueueSize = boundedState.estimateQueueSize();
            if (iEstimateQueueSize < 0) {
                return -1;
            }
            i -= iEstimateQueueSize;
        }
        return i;
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(isDisposed());
        }
        if (attr == Scannable.Attr.BUFFERED) {
            return Integer.valueOf(estimateSize());
        }
        if (attr == Scannable.Attr.CAPACITY) {
            return Integer.valueOf(this.maxThreads);
        }
        if (attr == Scannable.Attr.NAME) {
            return toString();
        }
        return null;
    }

    @Override // reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        BoundedServices boundedServices = this.state.currentResource;
        return Stream.concat(Stream.of((Object[]) boundedServices.busyStates.array), boundedServices.idleQueue.stream()).filter(new Predicate() { // from class: reactor.core.scheduler.BoundedElasticScheduler$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return BoundedElasticScheduler.lambda$inners$1((BoundedElasticScheduler.BoundedState) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$inners$1(BoundedState boundedState) {
        return (boundedState == null || boundedState == BoundedServices.CREATING) ? false : true;
    }

    @Override // reactor.core.scheduler.Scheduler
    public Scheduler.Worker createWorker() {
        BoundedState boundedStatePick = this.state.currentResource.pick();
        ExecutorServiceWorker executorServiceWorker = new ExecutorServiceWorker(boundedStatePick.executor);
        executorServiceWorker.disposables.add(boundedStatePick);
        return executorServiceWorker;
    }

    static final class BoundedServices extends AtomicInteger {
        static final AtomicReferenceFieldUpdater<BoundedServices, BusyStates> BUSY_STATES;
        static final BoundedState CREATING;
        static final AtomicLong EVICTOR_COUNTER;
        static final ThreadFactory EVICTOR_FACTORY;
        static final ScheduledExecutorService EVICTOR_SHUTDOWN;
        static final BoundedServices SHUTDOWN;
        static final BoundedServices SHUTTING_DOWN;
        volatile BusyStates busyStates;
        final Clock clock;
        final ScheduledExecutorService evictor;
        final Deque<BoundedState> idleQueue;
        final BoundedElasticScheduler parent;
        static final ZoneId ZONE_UTC = ZoneId.of("UTC");
        static final BusyStates ALL_IDLE = new BusyStates(new BoundedState[0], false);
        static final BusyStates ALL_SHUTDOWN = new BusyStates(new BoundedState[0], true);

        static final class BusyStates {
            final BoundedState[] array;
            final boolean shutdown;

            public BusyStates(BoundedState[] boundedStateArr, boolean z) {
                this.array = boundedStateArr;
                this.shutdown = z;
            }
        }

        static {
            ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            EVICTOR_SHUTDOWN = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
            scheduledExecutorServiceNewSingleThreadScheduledExecutor.shutdownNow();
            BoundedServices boundedServices = new BoundedServices();
            SHUTDOWN = boundedServices;
            BoundedServices boundedServices2 = new BoundedServices();
            SHUTTING_DOWN = boundedServices2;
            boundedServices.dispose();
            boundedServices2.dispose();
            ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor2 = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorServiceNewSingleThreadScheduledExecutor2.shutdownNow();
            BoundedState boundedState = new BoundedState(boundedServices, scheduledExecutorServiceNewSingleThreadScheduledExecutor2) { // from class: reactor.core.scheduler.BoundedElasticScheduler.BoundedServices.1
                @Override // reactor.core.scheduler.BoundedElasticScheduler.BoundedState
                public String toString() {
                    return "CREATING BoundedState";
                }
            };
            CREATING = boundedState;
            boundedState.markCount = -1;
            boundedState.idleSinceTimestamp = -1L;
            EVICTOR_COUNTER = new AtomicLong();
            EVICTOR_FACTORY = new ThreadFactory() { // from class: reactor.core.scheduler.BoundedElasticScheduler$BoundedServices$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.ThreadFactory
                public final Thread newThread(Runnable runnable) {
                    return BoundedElasticScheduler.BoundedServices.lambda$static$0(runnable);
                }
            };
            BUSY_STATES = AtomicReferenceFieldUpdater.newUpdater(BoundedServices.class, BusyStates.class, "busyStates");
        }

        static /* synthetic */ Thread lambda$static$0(Runnable runnable) {
            Thread thread = new Thread(runnable, "boundedElastic-evictor-" + EVICTOR_COUNTER.incrementAndGet());
            thread.setDaemon(true);
            return thread;
        }

        private BoundedServices() {
            this.parent = null;
            this.clock = Clock.fixed(Instant.EPOCH, ZONE_UTC);
            this.idleQueue = new ConcurrentLinkedDeque();
            this.busyStates = ALL_SHUTDOWN;
            this.evictor = EVICTOR_SHUTDOWN;
        }

        BoundedServices(BoundedElasticScheduler boundedElasticScheduler) {
            this.parent = boundedElasticScheduler;
            this.clock = boundedElasticScheduler.clock;
            this.idleQueue = new ConcurrentLinkedDeque();
            this.busyStates = ALL_IDLE;
            this.evictor = Executors.newSingleThreadScheduledExecutor(EVICTOR_FACTORY);
        }

        void eviction() {
            long jMillis = this.parent.clock.millis();
            for (BoundedState boundedState : new ArrayList(this.idleQueue)) {
                if (boundedState.tryEvict(jMillis, this.parent.ttlMillis)) {
                    this.idleQueue.remove(boundedState);
                    decrementAndGet();
                }
            }
        }

        boolean setBusy(BoundedState boundedState) {
            BusyStates busyStates;
            BoundedState[] boundedStateArr;
            do {
                busyStates = this.busyStates;
                if (busyStates.shutdown) {
                    return false;
                }
                int length = busyStates.array.length;
                boundedStateArr = new BoundedState[length + 1];
                System.arraycopy(busyStates.array, 0, boundedStateArr, 0, length);
                boundedStateArr[length] = boundedState;
            } while (!C0162xc40028dd.m5m(BUSY_STATES, this, busyStates, new BusyStates(boundedStateArr, false)));
            return true;
        }

        void setIdle(BoundedState boundedState) {
            BusyStates busyStates;
            BusyStates busyStates2;
            do {
                busyStates = this.busyStates;
                BoundedState[] boundedStateArr = this.busyStates.array;
                int length = boundedStateArr.length;
                if (length == 0 || busyStates.shutdown) {
                    return;
                }
                if (length == 1) {
                    busyStates2 = boundedStateArr[0] == boundedState ? ALL_IDLE : null;
                } else {
                    for (int i = 0; i < length; i++) {
                        if (boundedStateArr[i] == boundedState) {
                            BusyStates busyStates3 = new BusyStates(new BoundedState[length - 1], false);
                            System.arraycopy(boundedStateArr, 0, busyStates3.array, 0, i);
                            System.arraycopy(boundedStateArr, i + 1, busyStates3.array, i, (length - i) - 1);
                            busyStates2 = busyStates3;
                            break;
                        }
                    }
                }
                if (busyStates2 == null) {
                    return;
                }
            } while (!C0162xc40028dd.m5m(BUSY_STATES, this, busyStates, busyStates2));
            this.idleQueue.add(boundedState);
            if (!this.busyStates.shutdown) {
                return;
            }
            boundedState.shutdown(true);
            while (true) {
                BoundedState boundedStatePollLast = this.idleQueue.pollLast();
                if (boundedStatePollLast == null) {
                    return;
                } else {
                    boundedStatePollLast.shutdown(true);
                }
            }
        }

        BoundedState pick() {
            while (this.busyStates != ALL_SHUTDOWN) {
                int i = get();
                if (!this.idleQueue.isEmpty()) {
                    BoundedState boundedStatePollLast = this.idleQueue.pollLast();
                    if (boundedStatePollLast != null && boundedStatePollLast.markPicked()) {
                        if (setBusy(boundedStatePollLast)) {
                            return boundedStatePollLast;
                        }
                        boundedStatePollLast.shutdown(true);
                        return CREATING;
                    }
                } else if (i < this.parent.maxThreads) {
                    if (compareAndSet(i, i + 1)) {
                        BoundedElasticScheduler boundedElasticScheduler = this.parent;
                        BoundedState boundedState = new BoundedState(this, Schedulers.decorateExecutorService(boundedElasticScheduler, boundedElasticScheduler.createBoundedExecutorService()));
                        if (boundedState.markPicked()) {
                            if (setBusy(boundedState)) {
                                return boundedState;
                            }
                            boundedState.shutdown(true);
                            return CREATING;
                        }
                    } else {
                        continue;
                    }
                } else {
                    BoundedState boundedStateChoseOneBusy = choseOneBusy();
                    if (boundedStateChoseOneBusy != null && boundedStateChoseOneBusy.markPicked()) {
                        return boundedStateChoseOneBusy;
                    }
                }
            }
            return CREATING;
        }

        @Nullable
        private BoundedState choseOneBusy() {
            BoundedState[] boundedStateArr = this.busyStates.array;
            int length = boundedStateArr.length;
            if (length == 0) {
                return null;
            }
            if (length == 1) {
                return boundedStateArr[0];
            }
            BoundedState boundedState = boundedStateArr[0];
            int i = Integer.MAX_VALUE;
            for (BoundedState boundedState2 : boundedStateArr) {
                int i2 = boundedState2.markCount;
                if (i2 < i) {
                    boundedState = boundedState2;
                    i = i2;
                }
            }
            return boundedState;
        }

        public BoundedState[] dispose() {
            BusyStates busyStates;
            do {
                busyStates = this.busyStates;
                if (busyStates.shutdown) {
                    return busyStates.array;
                }
            } while (!C0162xc40028dd.m5m(BUSY_STATES, this, busyStates, new BusyStates(busyStates.array, true)));
            BoundedState[] boundedStateArr = busyStates.array;
            ArrayList arrayList = new ArrayList(this.idleQueue.size() + boundedStateArr.length);
            while (true) {
                BoundedState boundedStatePollLast = this.idleQueue.pollLast();
                if (boundedStatePollLast != null) {
                    arrayList.add(boundedStatePollLast);
                } else {
                    Collections.addAll(arrayList, boundedStateArr);
                    return (BoundedState[]) arrayList.toArray(new BoundedState[0]);
                }
            }
        }
    }

    static class BoundedState implements Disposable, Scannable {
        static final int EVICTED = -1;
        static final AtomicIntegerFieldUpdater<BoundedState> MARK_COUNT = AtomicIntegerFieldUpdater.newUpdater(BoundedState.class, "markCount");
        final ScheduledExecutorService executor;
        long idleSinceTimestamp = -1;
        volatile int markCount;
        final BoundedServices parent;

        BoundedState(BoundedServices boundedServices, ScheduledExecutorService scheduledExecutorService) {
            this.parent = boundedServices;
            this.executor = scheduledExecutorService;
        }

        int estimateQueueSize() {
            ScheduledExecutorService scheduledExecutorService = this.executor;
            if (scheduledExecutorService instanceof ScheduledThreadPoolExecutor) {
                return ((ScheduledThreadPoolExecutor) scheduledExecutorService).getQueue().size();
            }
            return -1;
        }

        boolean markPicked() {
            AtomicIntegerFieldUpdater<BoundedState> atomicIntegerFieldUpdater;
            int i;
            do {
                atomicIntegerFieldUpdater = MARK_COUNT;
                i = atomicIntegerFieldUpdater.get(this);
                if (i == -1) {
                    return false;
                }
            } while (!atomicIntegerFieldUpdater.compareAndSet(this, i, i + 1));
            return true;
        }

        boolean tryEvict(long j, long j2) {
            long j3 = this.idleSinceTimestamp;
            if (j3 < 0 || j - j3 < j2 || !MARK_COUNT.compareAndSet(this, 0, -1)) {
                return false;
            }
            this.executor.shutdownNow();
            return true;
        }

        void release() {
            int iDecrementAndGet = MARK_COUNT.decrementAndGet(this);
            if (iDecrementAndGet < 0) {
                return;
            }
            if (iDecrementAndGet == 0) {
                this.idleSinceTimestamp = this.parent.clock.millis();
                this.parent.setIdle(this);
            } else {
                this.idleSinceTimestamp = -1L;
            }
        }

        void shutdown(boolean z) {
            this.idleSinceTimestamp = -1L;
            MARK_COUNT.set(this, -1);
            if (z) {
                this.executor.shutdownNow();
            } else {
                this.executor.shutdown();
            }
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            release();
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return MARK_COUNT.get(this) <= 0;
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return Schedulers.scanExecutor(this.executor, attr);
        }

        public String toString() {
            return "BoundedState@" + System.identityHashCode(this) + "{ backing=" + MARK_COUNT.get(this) + ", idleSince=" + this.idleSinceTimestamp + ", executor=" + this.executor + '}';
        }
    }

    static final class BoundedScheduledExecutorService extends ScheduledThreadPoolExecutor implements Scannable {
        final int queueCapacity;

        BoundedScheduledExecutorService(int i, ThreadFactory threadFactory) {
            super(1, threadFactory);
            setMaximumPoolSize(1);
            setRemoveOnCancelPolicy(true);
            if (i < 1) {
                throw new IllegalArgumentException("was expecting a non-zero positive queue capacity");
            }
            this.queueCapacity = i;
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (Scannable.Attr.TERMINATED == attr) {
                return Boolean.valueOf(isTerminated());
            }
            if (Scannable.Attr.BUFFERED == attr) {
                return Integer.valueOf(getQueue().size());
            }
            if (Scannable.Attr.CAPACITY == attr) {
                return Integer.valueOf(this.queueCapacity);
            }
            return null;
        }

        @Override // java.util.concurrent.ThreadPoolExecutor
        public String toString() {
            int size = getQueue().size();
            long completedTaskCount = getCompletedTaskCount();
            String str = getActiveCount() > 0 ? "ACTIVE" : "IDLE";
            if (this.queueCapacity == Integer.MAX_VALUE) {
                return "BoundedScheduledExecutorService{" + str + ", queued=" + size + "/unbounded, completed=" + completedTaskCount + '}';
            }
            return "BoundedScheduledExecutorService{" + str + ", queued=" + size + "/" + this.queueCapacity + ", completed=" + completedTaskCount + '}';
        }

        void ensureQueueCapacity(int i) {
            int size;
            if (this.queueCapacity != Integer.MAX_VALUE && (size = super.getQueue().size() + i) > this.queueCapacity) {
                throw Exceptions.failWithRejected("Task capacity of bounded elastic scheduler reached while scheduling " + i + " tasks (" + size + "/" + this.queueCapacity + ")");
            }
        }

        @Override // java.util.concurrent.ScheduledThreadPoolExecutor, java.util.concurrent.ScheduledExecutorService
        public synchronized ScheduledFuture<?> schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            ensureQueueCapacity(1);
            return super.schedule(runnable, j, timeUnit);
        }

        @Override // java.util.concurrent.ScheduledThreadPoolExecutor, java.util.concurrent.ScheduledExecutorService
        public synchronized <V> ScheduledFuture<V> schedule(Callable<V> callable, long j, TimeUnit timeUnit) {
            ensureQueueCapacity(1);
            return super.schedule(callable, j, timeUnit);
        }

        @Override // java.util.concurrent.ScheduledThreadPoolExecutor, java.util.concurrent.ScheduledExecutorService
        public synchronized ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            ensureQueueCapacity(1);
            return super.scheduleAtFixedRate(runnable, j, j2, timeUnit);
        }

        @Override // java.util.concurrent.ScheduledThreadPoolExecutor, java.util.concurrent.ScheduledExecutorService
        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            ensureQueueCapacity(1);
            return super.scheduleWithFixedDelay(runnable, j, j2, timeUnit);
        }

        @Override // java.util.concurrent.ScheduledThreadPoolExecutor, java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.ExecutorService
        public void shutdown() {
            super.shutdown();
        }

        @Override // java.util.concurrent.ScheduledThreadPoolExecutor, java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.ExecutorService
        public List<Runnable> shutdownNow() {
            return super.shutdownNow();
        }

        @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.ExecutorService
        public boolean isShutdown() {
            return super.isShutdown();
        }

        @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.ExecutorService
        public boolean isTerminated() {
            return super.isTerminated();
        }

        @Override // java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.ExecutorService
        public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
            return super.awaitTermination(j, timeUnit);
        }

        @Override // java.util.concurrent.ScheduledThreadPoolExecutor, java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public synchronized <T> Future<T> submit(Callable<T> callable) {
            ensureQueueCapacity(1);
            return super.submit(callable);
        }

        @Override // java.util.concurrent.ScheduledThreadPoolExecutor, java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public synchronized <T> Future<T> submit(Runnable runnable, T t) {
            ensureQueueCapacity(1);
            return super.submit(runnable, t);
        }

        @Override // java.util.concurrent.ScheduledThreadPoolExecutor, java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public synchronized Future<?> submit(Runnable runnable) {
            ensureQueueCapacity(1);
            return super.submit(runnable);
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public synchronized <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
            ensureQueueCapacity(collection.size());
            return super.invokeAll(collection);
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public synchronized <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws InterruptedException {
            ensureQueueCapacity(collection.size());
            return super.invokeAll(collection, j, timeUnit);
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public synchronized <T> T invokeAny(Collection<? extends Callable<T>> collection) throws ExecutionException, InterruptedException {
            ensureQueueCapacity(collection.size());
            return (T) super.invokeAny(collection);
        }

        @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
        public synchronized <T> T invokeAny(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
            ensureQueueCapacity(collection.size());
            return (T) super.invokeAny(collection, j, timeUnit);
        }

        @Override // java.util.concurrent.ScheduledThreadPoolExecutor, java.util.concurrent.ThreadPoolExecutor, java.util.concurrent.Executor
        public synchronized void execute(Runnable runnable) {
            ensureQueueCapacity(1);
            super.submit(runnable);
        }
    }
}
