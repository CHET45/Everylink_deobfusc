package reactor.core.scheduler;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.core.Disposable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class PeriodicWorkerTask implements Runnable, Disposable, Callable<Void> {
    volatile Future<?> future;
    volatile Disposable.Composite parent;
    final Runnable task;
    Thread thread;
    static final Disposable.Composite DISPOSED = new EmptyCompositeDisposable();
    static final Future<Void> CANCELLED = new FutureTask(new Callable() { // from class: reactor.core.scheduler.PeriodicWorkerTask$$ExternalSyntheticLambda0
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return PeriodicWorkerTask.lambda$static$0();
        }
    });
    static final AtomicReferenceFieldUpdater<PeriodicWorkerTask, Future> FUTURE = AtomicReferenceFieldUpdater.newUpdater(PeriodicWorkerTask.class, Future.class, "future");
    static final AtomicReferenceFieldUpdater<PeriodicWorkerTask, Disposable.Composite> PARENT = AtomicReferenceFieldUpdater.newUpdater(PeriodicWorkerTask.class, Disposable.Composite.class, "parent");

    static /* synthetic */ Void lambda$static$0() throws Exception {
        return null;
    }

    PeriodicWorkerTask(Runnable runnable, Disposable.Composite composite) {
        this.task = runnable;
        PARENT.lazySet(this, composite);
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
        Disposable.Composite composite;
        Disposable.Composite composite2;
        while (true) {
            Future<?> future = this.future;
            Future<Void> future2 = CANCELLED;
            if (future == future2) {
                break;
            } else if (C0162xc40028dd.m5m(FUTURE, this, future, future2)) {
                if (future != null) {
                    future.cancel(this.thread != Thread.currentThread());
                }
            }
        }
        do {
            composite = this.parent;
            composite2 = DISPOSED;
            if (composite == composite2 || composite == null) {
                return;
            }
        } while (!C0162xc40028dd.m5m(PARENT, this, composite, composite2));
        composite.remove(this);
    }
}
