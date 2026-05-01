package reactor.core.scheduler;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.SchedulerState;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class DelegateServiceScheduler implements Scheduler, SchedulerState.DisposeAwaiter<ScheduledExecutorService>, Scannable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final AtomicReferenceFieldUpdater<DelegateServiceScheduler, SchedulerState> STATE;
    static final ScheduledExecutorService TERMINATED;
    final String executorName;
    final ScheduledExecutorService original;

    @Nullable
    volatile SchedulerState<ScheduledExecutorService> state;

    static {
        ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        TERMINATED = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
        scheduledExecutorServiceNewSingleThreadScheduledExecutor.shutdownNow();
        STATE = AtomicReferenceFieldUpdater.newUpdater(DelegateServiceScheduler.class, SchedulerState.class, "state");
    }

    DelegateServiceScheduler(String str, ExecutorService executorService) {
        this.executorName = str;
        this.original = convert(executorService);
    }

    ScheduledExecutorService getOrCreate() {
        SchedulerState<ScheduledExecutorService> schedulerState = this.state;
        if (schedulerState == null) {
            init();
            schedulerState = this.state;
            if (schedulerState == null) {
                throw new IllegalStateException("executor is null after implicit start()");
            }
        }
        return schedulerState.currentResource;
    }

    @Override // reactor.core.scheduler.Scheduler
    public Scheduler.Worker createWorker() {
        return new ExecutorServiceWorker(getOrCreate());
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedule(Runnable runnable) {
        return Schedulers.directSchedule(getOrCreate(), runnable, null, 0L, TimeUnit.MILLISECONDS);
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        return Schedulers.directSchedule(getOrCreate(), runnable, null, j, timeUnit);
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedulePeriodically(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return Schedulers.directSchedulePeriodically(getOrCreate(), runnable, j, j2, timeUnit);
    }

    @Override // reactor.core.scheduler.Scheduler
    public void start() {
        C0162xc40028dd.m5m(STATE, this, null, SchedulerState.init(Schedulers.decorateExecutorService(this, this.original)));
    }

    @Override // reactor.core.scheduler.Scheduler
    public void init() {
        SchedulerState<ScheduledExecutorService> schedulerState = this.state;
        if (schedulerState != null) {
            if (schedulerState.currentResource == TERMINATED) {
                throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
            }
        } else if (!C0162xc40028dd.m5m(STATE, this, null, SchedulerState.init(Schedulers.decorateExecutorService(this, this.original))) && isDisposed()) {
            throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
        }
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        SchedulerState<ScheduledExecutorService> schedulerState = this.state;
        return schedulerState != null && schedulerState.currentResource == TERMINATED;
    }

    @Override // reactor.core.scheduler.SchedulerState.DisposeAwaiter
    public boolean await(ScheduledExecutorService scheduledExecutorService, long j, TimeUnit timeUnit) throws InterruptedException {
        return scheduledExecutorService.awaitTermination(j, timeUnit);
    }

    @Override // reactor.core.scheduler.Scheduler, reactor.core.Disposable
    public void dispose() {
        SchedulerState<ScheduledExecutorService> schedulerState = this.state;
        if (schedulerState != null && schedulerState.currentResource == TERMINATED) {
            schedulerState.initialResource.shutdownNow();
            return;
        }
        SchedulerState schedulerStateTransition = SchedulerState.transition(schedulerState == null ? null : schedulerState.currentResource, TERMINATED, this);
        C0162xc40028dd.m5m(STATE, this, schedulerState, schedulerStateTransition);
        if (schedulerStateTransition.initialResource != 0) {
            ((ScheduledExecutorService) schedulerStateTransition.initialResource).shutdownNow();
        }
    }

    @Override // reactor.core.scheduler.Scheduler
    public Mono<Void> disposeGracefully() {
        return Mono.defer(new Supplier() { // from class: reactor.core.scheduler.DelegateServiceScheduler$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m1977xa5e98dbc();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$disposeGracefully$0$reactor-core-scheduler-DelegateServiceScheduler */
    /* synthetic */ Mono m1977xa5e98dbc() {
        SchedulerState<ScheduledExecutorService> schedulerState = this.state;
        if (schedulerState != null && schedulerState.currentResource == TERMINATED) {
            return schedulerState.onDispose;
        }
        SchedulerState schedulerStateTransition = SchedulerState.transition(schedulerState == null ? null : schedulerState.currentResource, TERMINATED, this);
        C0162xc40028dd.m5m(STATE, this, schedulerState, schedulerStateTransition);
        if (schedulerStateTransition.initialResource != 0) {
            ((ScheduledExecutorService) schedulerStateTransition.initialResource).shutdown();
        }
        return schedulerStateTransition.onDispose;
    }

    static ScheduledExecutorService convert(ExecutorService executorService) {
        if (executorService instanceof ScheduledExecutorService) {
            return (ScheduledExecutorService) executorService;
        }
        return new UnsupportedScheduledExecutorService(executorService);
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(isDisposed());
        }
        if (attr == Scannable.Attr.NAME) {
            return toString();
        }
        SchedulerState<ScheduledExecutorService> schedulerState = this.state;
        if (schedulerState != null) {
            return Schedulers.scanExecutor(schedulerState.currentResource, attr);
        }
        return null;
    }

    public String toString() {
        return "fromExecutorService(" + this.executorName + ')';
    }

    static final class UnsupportedScheduledExecutorService implements ScheduledExecutorService, Supplier<ExecutorService> {
        final ExecutorService exec;

        UnsupportedScheduledExecutorService(ExecutorService executorService) {
            this.exec = executorService;
        }

        @Override // java.util.function.Supplier
        public ExecutorService get() {
            return this.exec;
        }

        @Override // java.util.concurrent.ExecutorService
        public void shutdown() {
            this.exec.shutdown();
        }

        @Override // java.util.concurrent.ExecutorService
        @NonNull
        public List<Runnable> shutdownNow() {
            return this.exec.shutdownNow();
        }

        @Override // java.util.concurrent.ExecutorService
        public boolean isShutdown() {
            return this.exec.isShutdown();
        }

        @Override // java.util.concurrent.ExecutorService
        public boolean isTerminated() {
            return this.exec.isTerminated();
        }

        @Override // java.util.concurrent.ExecutorService
        public boolean awaitTermination(long j, @NonNull TimeUnit timeUnit) throws InterruptedException {
            return this.exec.awaitTermination(j, timeUnit);
        }

        @Override // java.util.concurrent.ExecutorService
        @NonNull
        public <T> Future<T> submit(@NonNull Callable<T> callable) {
            return this.exec.submit(callable);
        }

        @Override // java.util.concurrent.ExecutorService
        @NonNull
        public <T> Future<T> submit(@NonNull Runnable runnable, T t) {
            return this.exec.submit(runnable, t);
        }

        @Override // java.util.concurrent.ExecutorService
        @NonNull
        public Future<?> submit(@NonNull Runnable runnable) {
            return this.exec.submit(runnable);
        }

        @Override // java.util.concurrent.ExecutorService
        @NonNull
        public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> collection) throws InterruptedException {
            return this.exec.invokeAll(collection);
        }

        @Override // java.util.concurrent.ExecutorService
        @NonNull
        public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> collection, long j, @NonNull TimeUnit timeUnit) throws InterruptedException {
            return this.exec.invokeAll(collection, j, timeUnit);
        }

        @Override // java.util.concurrent.ExecutorService
        @NonNull
        public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> collection) throws ExecutionException, InterruptedException {
            return (T) this.exec.invokeAny(collection);
        }

        @Override // java.util.concurrent.ExecutorService
        public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> collection, long j, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
            return (T) this.exec.invokeAny(collection, j, timeUnit);
        }

        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            this.exec.execute(runnable);
        }

        @Override // java.util.concurrent.ScheduledExecutorService
        @NonNull
        public ScheduledFuture<?> schedule(@NonNull Runnable runnable, long j, @NonNull TimeUnit timeUnit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        @Override // java.util.concurrent.ScheduledExecutorService
        @NonNull
        public <V> ScheduledFuture<V> schedule(@NonNull Callable<V> callable, long j, @NonNull TimeUnit timeUnit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        @Override // java.util.concurrent.ScheduledExecutorService
        @NonNull
        public ScheduledFuture<?> scheduleAtFixedRate(@NonNull Runnable runnable, long j, long j2, @NonNull TimeUnit timeUnit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        @Override // java.util.concurrent.ScheduledExecutorService
        @NonNull
        public ScheduledFuture<?> scheduleWithFixedDelay(@NonNull Runnable runnable, long j, long j2, @NonNull TimeUnit timeUnit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        public String toString() {
            return this.exec.toString();
        }
    }
}
