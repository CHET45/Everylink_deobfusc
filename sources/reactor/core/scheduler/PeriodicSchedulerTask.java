package reactor.core.scheduler;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.core.Disposable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class PeriodicSchedulerTask implements Runnable, Disposable, Callable<Void> {
    static final Future<Void> CANCELLED = new FutureTask(new Callable() { // from class: reactor.core.scheduler.PeriodicSchedulerTask$$ExternalSyntheticLambda0
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return PeriodicSchedulerTask.lambda$static$0();
        }
    });
    static final AtomicReferenceFieldUpdater<PeriodicSchedulerTask, Future> FUTURE = AtomicReferenceFieldUpdater.newUpdater(PeriodicSchedulerTask.class, Future.class, "future");
    volatile Future<?> future;
    final Runnable task;
    Thread thread;

    static /* synthetic */ Void lambda$static$0() throws Exception {
        return null;
    }

    PeriodicSchedulerTask(Runnable runnable) {
        this.task = runnable;
    }

    @Override // java.util.concurrent.Callable
    @Nullable
    public Void call() {
        this.thread = Thread.currentThread();
        try {
            this.task.run();
        } finally {
            try {
            } finally {
            }
        }
        return null;
    }

    @Override // java.lang.Runnable
    public void run() {
        call();
    }

    void setFuture(Future<?> future) {
        Future<?> future2;
        do {
            future2 = this.future;
            if (future2 == CANCELLED) {
                future.cancel(this.thread != Thread.currentThread());
                return;
            }
        } while (!C0162xc40028dd.m5m(FUTURE, this, future2, future));
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        return this.future == CANCELLED;
    }

    @Override // reactor.core.Disposable
    public void dispose() {
        Future<?> future;
        Future<Void> future2;
        do {
            future = this.future;
            future2 = CANCELLED;
            if (future == future2) {
                return;
            }
        } while (!C0162xc40028dd.m5m(FUTURE, this, future, future2));
        if (future != null) {
            future.cancel(this.thread != Thread.currentThread());
        }
    }
}
