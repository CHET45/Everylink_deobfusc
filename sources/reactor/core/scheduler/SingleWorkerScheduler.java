package reactor.core.scheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.scheduler.ExecutorScheduler;
import reactor.core.scheduler.Scheduler;

/* JADX INFO: loaded from: classes5.dex */
final class SingleWorkerScheduler implements Scheduler, Executor, Scannable {
    final Scheduler.Worker main;

    SingleWorkerScheduler(Scheduler scheduler) {
        this.main = scheduler.createWorker();
    }

    @Override // reactor.core.scheduler.Scheduler, reactor.core.Disposable
    public void dispose() {
        this.main.dispose();
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedule(Runnable runnable) {
        return this.main.schedule(runnable);
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        return this.main.schedule(runnable, j, timeUnit);
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedulePeriodically(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        return this.main.schedulePeriodically(runnable, j, j2, timeUnit);
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        this.main.schedule(runnable);
    }

    @Override // reactor.core.scheduler.Scheduler
    public Scheduler.Worker createWorker() {
        return new ExecutorScheduler.ExecutorSchedulerWorker(this);
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        return this.main.isDisposed();
    }

    public String toString() {
        Scannable scannableFrom = Scannable.from(this.main);
        if (scannableFrom.isScanAvailable()) {
            return "singleWorker(" + scannableFrom.scanUnsafe(Scannable.Attr.NAME) + ")";
        }
        return "singleWorker(" + this.main.toString() + ")";
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(isDisposed());
        }
        return attr == Scannable.Attr.PARENT ? this.main : attr == Scannable.Attr.NAME ? toString() : Scannable.from(this.main).scanUnsafe(attr);
    }
}
