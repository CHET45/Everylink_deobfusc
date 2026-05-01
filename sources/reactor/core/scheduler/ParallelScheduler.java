package reactor.core.scheduler;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.SchedulerState;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelScheduler implements Scheduler, Supplier<ScheduledExecutorService>, SchedulerState.DisposeAwaiter<ScheduledExecutorService[]>, Scannable {
    private static final AtomicReferenceFieldUpdater<ParallelScheduler, SchedulerState> STATE;
    static final ScheduledExecutorService TERMINATED;
    final ThreadFactory factory;

    /* JADX INFO: renamed from: n */
    final int f2300n;
    int roundRobin;
    volatile SchedulerState<ScheduledExecutorService[]> state;
    static final ScheduledExecutorService[] SHUTDOWN = new ScheduledExecutorService[0];
    static final AtomicLong COUNTER = new AtomicLong();

    static {
        ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        TERMINATED = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
        scheduledExecutorServiceNewSingleThreadScheduledExecutor.shutdownNow();
        STATE = AtomicReferenceFieldUpdater.newUpdater(ParallelScheduler.class, SchedulerState.class, "state");
    }

    ParallelScheduler(int i, ThreadFactory threadFactory) {
        if (i <= 0) {
            throw new IllegalArgumentException("n > 0 required but it was " + i);
        }
        this.f2300n = i;
        this.factory = threadFactory;
    }

    @Override // java.util.function.Supplier
    public ScheduledExecutorService get() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, this.factory);
        scheduledThreadPoolExecutor.setMaximumPoolSize(1);
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        return scheduledThreadPoolExecutor;
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        SchedulerState<ScheduledExecutorService[]> schedulerState = this.state;
        return schedulerState != null && schedulerState.currentResource == SHUTDOWN;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // reactor.core.scheduler.Scheduler
    public void init() {
        SchedulerState<ScheduledExecutorService[]> schedulerState = this.state;
        if (schedulerState != null) {
            if (schedulerState.currentResource == SHUTDOWN) {
                throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
            }
            return;
        }
        SchedulerState schedulerStateInit = SchedulerState.init(new ScheduledExecutorService[this.f2300n]);
        for (int i = 0; i < this.f2300n; i++) {
            ((ScheduledExecutorService[]) schedulerStateInit.currentResource)[i] = Schedulers.decorateExecutorService(this, get());
        }
        if (C0162xc40028dd.m5m(STATE, this, null, schedulerStateInit)) {
            return;
        }
        for (ScheduledExecutorService scheduledExecutorService : (ScheduledExecutorService[]) schedulerStateInit.currentResource) {
            scheduledExecutorService.shutdownNow();
        }
        if (isDisposed()) {
            throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // reactor.core.scheduler.Scheduler
    public void start() {
        SchedulerState<ScheduledExecutorService[]> schedulerState = this.state;
        if (schedulerState == null || schedulerState.currentResource == SHUTDOWN) {
            SchedulerState schedulerStateInit = SchedulerState.init(new ScheduledExecutorService[this.f2300n]);
            for (int i = 0; i < this.f2300n; i++) {
                ((ScheduledExecutorService[]) schedulerStateInit.currentResource)[i] = Schedulers.decorateExecutorService(this, get());
            }
            if (C0162xc40028dd.m5m(STATE, this, schedulerState, schedulerStateInit)) {
                return;
            }
            for (ScheduledExecutorService scheduledExecutorService : (ScheduledExecutorService[]) schedulerStateInit.currentResource) {
                scheduledExecutorService.shutdownNow();
            }
        }
    }

    @Override // reactor.core.scheduler.SchedulerState.DisposeAwaiter
    public boolean await(ScheduledExecutorService[] scheduledExecutorServiceArr, long j, TimeUnit timeUnit) throws InterruptedException {
        for (ScheduledExecutorService scheduledExecutorService : scheduledExecutorServiceArr) {
            if (!scheduledExecutorService.awaitTermination(j, timeUnit)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // reactor.core.scheduler.Scheduler, reactor.core.Disposable
    public void dispose() {
        SchedulerState<ScheduledExecutorService[]> schedulerState = this.state;
        int i = 0;
        if (schedulerState != null && schedulerState.currentResource == SHUTDOWN) {
            if (schedulerState.initialResource != null) {
                ScheduledExecutorService[] scheduledExecutorServiceArr = schedulerState.initialResource;
                int length = scheduledExecutorServiceArr.length;
                while (i < length) {
                    scheduledExecutorServiceArr[i].shutdownNow();
                    i++;
                }
                return;
            }
            return;
        }
        SchedulerState schedulerStateTransition = SchedulerState.transition(schedulerState == null ? null : schedulerState.currentResource, SHUTDOWN, this);
        C0162xc40028dd.m5m(STATE, this, schedulerState, schedulerStateTransition);
        if (schedulerStateTransition.initialResource != 0) {
            ScheduledExecutorService[] scheduledExecutorServiceArr2 = (ScheduledExecutorService[]) schedulerStateTransition.initialResource;
            int length2 = scheduledExecutorServiceArr2.length;
            while (i < length2) {
                scheduledExecutorServiceArr2[i].shutdownNow();
                i++;
            }
        }
    }

    @Override // reactor.core.scheduler.Scheduler
    public Mono<Void> disposeGracefully() {
        return Mono.defer(new Supplier() { // from class: reactor.core.scheduler.ParallelScheduler$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m1978x969c5b03();
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$disposeGracefully$0$reactor-core-scheduler-ParallelScheduler */
    /* synthetic */ Mono m1978x969c5b03() {
        SchedulerState<ScheduledExecutorService[]> schedulerState = this.state;
        if (schedulerState != null && schedulerState.currentResource == SHUTDOWN) {
            return schedulerState.onDispose;
        }
        SchedulerState schedulerStateTransition = SchedulerState.transition(schedulerState == null ? null : schedulerState.currentResource, SHUTDOWN, this);
        C0162xc40028dd.m5m(STATE, this, schedulerState, schedulerStateTransition);
        if (schedulerStateTransition.initialResource != 0) {
            for (ScheduledExecutorService scheduledExecutorService : (ScheduledExecutorService[]) schedulerStateTransition.initialResource) {
                scheduledExecutorService.shutdown();
            }
        }
        return schedulerStateTransition.onDispose;
    }

    ScheduledExecutorService pick() {
        SchedulerState<ScheduledExecutorService[]> schedulerState = this.state;
        if (schedulerState == null) {
            init();
            schedulerState = this.state;
            if (schedulerState == null) {
                throw new IllegalStateException("executors uninitialized after implicit init()");
            }
        }
        if (schedulerState.currentResource != SHUTDOWN) {
            int i = this.roundRobin;
            if (i == this.f2300n) {
                this.roundRobin = 1;
                i = 0;
            } else {
                this.roundRobin = i + 1;
            }
            return schedulerState.currentResource[i];
        }
        return TERMINATED;
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedule(Runnable runnable) {
        return Schedulers.directSchedule(pick(), runnable, null, 0L, TimeUnit.MILLISECONDS);
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        return Schedulers.directSchedule(pick(), runnable, null, j, timeUnit);
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedulePeriodically(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return Schedulers.directSchedulePeriodically(pick(), runnable, j, j2, timeUnit);
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder("parallel(").append(this.f2300n);
        if (this.factory instanceof ReactorThreadFactory) {
            sbAppend.append(",\"").append(((ReactorThreadFactory) this.factory).get()).append('\"');
        }
        sbAppend.append(')');
        return sbAppend.toString();
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(isDisposed());
        }
        if (attr == Scannable.Attr.CAPACITY || attr == Scannable.Attr.BUFFERED) {
            return Integer.valueOf(this.f2300n);
        }
        if (attr == Scannable.Attr.NAME) {
            return toString();
        }
        return null;
    }

    @Override // reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        return Stream.of((Object[]) this.state.currentResource).map(new Function() { // from class: reactor.core.scheduler.ParallelScheduler$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ParallelScheduler.lambda$inners$2((ScheduledExecutorService) obj);
            }
        });
    }

    static /* synthetic */ Scannable lambda$inners$2(final ScheduledExecutorService scheduledExecutorService) {
        return new Scannable() { // from class: reactor.core.scheduler.ParallelScheduler$$ExternalSyntheticLambda1
            @Override // reactor.core.Scannable
            public final Object scanUnsafe(Scannable.Attr attr) {
                return Schedulers.scanExecutor(scheduledExecutorService, attr);
            }
        };
    }

    @Override // reactor.core.scheduler.Scheduler
    public Scheduler.Worker createWorker() {
        return new ExecutorServiceWorker(pick());
    }
}
