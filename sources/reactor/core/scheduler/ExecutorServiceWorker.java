package reactor.core.scheduler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;

/* JADX INFO: loaded from: classes5.dex */
final class ExecutorServiceWorker implements Scheduler.Worker, Disposable, Scannable {
    final Disposable.Composite disposables = Disposables.composite();
    final ScheduledExecutorService exec;

    ExecutorServiceWorker(ScheduledExecutorService scheduledExecutorService) {
        this.exec = scheduledExecutorService;
    }

    @Override // reactor.core.scheduler.Scheduler.Worker
    public Disposable schedule(Runnable runnable) {
        return Schedulers.workerSchedule(this.exec, this.disposables, runnable, 0L, TimeUnit.MILLISECONDS);
    }

    @Override // reactor.core.scheduler.Scheduler.Worker
    public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        return Schedulers.workerSchedule(this.exec, this.disposables, runnable, j, timeUnit);
    }

    @Override // reactor.core.scheduler.Scheduler.Worker
    public Disposable schedulePeriodically(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return Schedulers.workerSchedulePeriodically(this.exec, this.disposables, runnable, j, j2, timeUnit);
    }

    @Override // reactor.core.Disposable
    public void dispose() {
        this.disposables.dispose();
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        return this.disposables.isDisposed();
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.BUFFERED) {
            return Integer.valueOf(this.disposables.size());
        }
        if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(isDisposed());
        }
        return attr == Scannable.Attr.NAME ? "ExecutorServiceWorker" : Schedulers.scanExecutor(this.exec, attr);
    }
}
