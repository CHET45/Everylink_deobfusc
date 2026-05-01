package reactor.core.scheduler;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.SchedulerState;

/* JADX INFO: loaded from: classes5.dex */
final class SingleScheduler implements Scheduler, Supplier<ScheduledExecutorService>, Scannable, SchedulerState.DisposeAwaiter<ScheduledExecutorService> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final AtomicLong COUNTER = new AtomicLong();
    private static final SchedulerState<ScheduledExecutorService> INIT;
    private static final AtomicReferenceFieldUpdater<SingleScheduler, SchedulerState> STATE;
    static final ScheduledExecutorService TERMINATED;
    final ThreadFactory factory;
    volatile SchedulerState<ScheduledExecutorService> state;

    static {
        ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        TERMINATED = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
        scheduledExecutorServiceNewSingleThreadScheduledExecutor.shutdownNow();
        STATE = AtomicReferenceFieldUpdater.newUpdater(SingleScheduler.class, SchedulerState.class, "state");
        INIT = SchedulerState.init(scheduledExecutorServiceNewSingleThreadScheduledExecutor);
    }

    SingleScheduler(ThreadFactory threadFactory) {
        this.factory = threadFactory;
        STATE.lazySet(this, INIT);
    }

    @Override // java.util.function.Supplier
    public ScheduledExecutorService get() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1, this.factory);
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        scheduledThreadPoolExecutor.setMaximumPoolSize(1);
        return scheduledThreadPoolExecutor;
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        SchedulerState<ScheduledExecutorService> schedulerState = this.state;
        return schedulerState != INIT && schedulerState.currentResource == TERMINATED;
    }

    @Override // reactor.core.scheduler.Scheduler
    public void init() {
        SchedulerState<ScheduledExecutorService> schedulerState = this.state;
        SchedulerState<ScheduledExecutorService> schedulerState2 = INIT;
        if (schedulerState != schedulerState2) {
            if (schedulerState.currentResource == TERMINATED) {
                throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
            }
            return;
        }
        SchedulerState schedulerStateInit = SchedulerState.init(Schedulers.decorateExecutorService(this, get()));
        if (C0162xc40028dd.m5m(STATE, this, schedulerState2, schedulerStateInit)) {
            return;
        }
        ((ScheduledExecutorService) schedulerStateInit.currentResource).shutdownNow();
        if (isDisposed()) {
            throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
        }
    }

    @Override // reactor.core.scheduler.Scheduler
    public void start() {
        SchedulerState<ScheduledExecutorService> schedulerState = this.state;
        if (schedulerState.currentResource != TERMINATED) {
            return;
        }
        SchedulerState schedulerStateInit = SchedulerState.init(Schedulers.decorateExecutorService(this, get()));
        if (C0162xc40028dd.m5m(STATE, this, schedulerState, schedulerStateInit)) {
            return;
        }
        ((ScheduledExecutorService) schedulerStateInit.currentResource).shutdownNow();
    }

    @Override // reactor.core.scheduler.SchedulerState.DisposeAwaiter
    public boolean await(ScheduledExecutorService scheduledExecutorService, long j, TimeUnit timeUnit) throws InterruptedException {
        return scheduledExecutorService.awaitTermination(j, timeUnit);
    }

    @Override // reactor.core.scheduler.Scheduler, reactor.core.Disposable
    public void dispose() {
        SchedulerState<ScheduledExecutorService> schedulerState = this.state;
        ScheduledExecutorService scheduledExecutorService = schedulerState.currentResource;
        ScheduledExecutorService scheduledExecutorService2 = TERMINATED;
        if (scheduledExecutorService == scheduledExecutorService2) {
            schedulerState.initialResource.shutdownNow();
            return;
        }
        SchedulerState schedulerStateTransition = SchedulerState.transition(schedulerState.currentResource, scheduledExecutorService2, this);
        C0162xc40028dd.m5m(STATE, this, schedulerState, schedulerStateTransition);
        ((ScheduledExecutorService) schedulerStateTransition.initialResource).shutdownNow();
    }

    @Override // reactor.core.scheduler.Scheduler
    public Mono<Void> disposeGracefully() {
        return Mono.defer(new Supplier() { // from class: reactor.core.scheduler.SingleScheduler$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m1979x59e555a2();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$disposeGracefully$0$reactor-core-scheduler-SingleScheduler */
    /* synthetic */ Mono m1979x59e555a2() {
        SchedulerState<ScheduledExecutorService> schedulerState = this.state;
        ScheduledExecutorService scheduledExecutorService = schedulerState.currentResource;
        ScheduledExecutorService scheduledExecutorService2 = TERMINATED;
        if (scheduledExecutorService == scheduledExecutorService2) {
            return schedulerState.onDispose;
        }
        SchedulerState schedulerStateTransition = SchedulerState.transition(schedulerState.currentResource, scheduledExecutorService2, this);
        C0162xc40028dd.m5m(STATE, this, schedulerState, schedulerStateTransition);
        ((ScheduledExecutorService) schedulerStateTransition.initialResource).shutdown();
        return schedulerStateTransition.onDispose;
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedule(Runnable runnable) {
        return Schedulers.directSchedule(this.state.currentResource, runnable, null, 0L, TimeUnit.MILLISECONDS);
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        return Schedulers.directSchedule(this.state.currentResource, runnable, null, j, timeUnit);
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedulePeriodically(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return Schedulers.directSchedulePeriodically(this.state.currentResource, runnable, j, j2, timeUnit);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("single(");
        if (this.factory instanceof ReactorThreadFactory) {
            sb.append('\"').append(((ReactorThreadFactory) this.factory).get()).append('\"');
        }
        return sb.append(')').toString();
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(isDisposed());
        }
        if (attr == Scannable.Attr.NAME) {
            return toString();
        }
        if (attr == Scannable.Attr.CAPACITY || attr == Scannable.Attr.BUFFERED) {
            return 1;
        }
        return Schedulers.scanExecutor(this.state.currentResource, attr);
    }

    @Override // reactor.core.scheduler.Scheduler
    public Scheduler.Worker createWorker() {
        return new ExecutorServiceWorker(this.state.currentResource);
    }
}
