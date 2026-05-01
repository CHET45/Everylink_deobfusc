package reactor.core.scheduler;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.core.Disposable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class WorkerTask implements Runnable, Disposable, Callable<Void> {
    volatile Future<?> future;
    volatile Disposable.Composite parent;
    final Runnable task;
    volatile Thread thread;
    static final Disposable.Composite DISPOSED = new EmptyCompositeDisposable();
    static final Disposable.Composite DONE = new EmptyCompositeDisposable();
    static final Future<Void> FINISHED = new FutureTask(new Callable() { // from class: reactor.core.scheduler.WorkerTask$$ExternalSyntheticLambda0
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return WorkerTask.lambda$static$0();
        }
    });
    static final Future<Void> SYNC_CANCELLED = new FutureTask(new Callable() { // from class: reactor.core.scheduler.WorkerTask$$ExternalSyntheticLambda1
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return WorkerTask.lambda$static$1();
        }
    });
    static final Future<Void> ASYNC_CANCELLED = new FutureTask(new Callable() { // from class: reactor.core.scheduler.WorkerTask$$ExternalSyntheticLambda2
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return WorkerTask.lambda$static$2();
        }
    });
    static final AtomicReferenceFieldUpdater<WorkerTask, Future> FUTURE = AtomicReferenceFieldUpdater.newUpdater(WorkerTask.class, Future.class, "future");
    static final AtomicReferenceFieldUpdater<WorkerTask, Disposable.Composite> PARENT = AtomicReferenceFieldUpdater.newUpdater(WorkerTask.class, Disposable.Composite.class, "parent");
    static final AtomicReferenceFieldUpdater<WorkerTask, Thread> THREAD = AtomicReferenceFieldUpdater.newUpdater(WorkerTask.class, Thread.class, "thread");

    static /* synthetic */ Void lambda$static$0() throws Exception {
        return null;
    }

    static /* synthetic */ Void lambda$static$1() throws Exception {
        return null;
    }

    static /* synthetic */ Void lambda$static$2() throws Exception {
        return null;
    }

    WorkerTask(Runnable runnable, Disposable.Composite composite) {
        this.task = runnable;
        PARENT.lazySet(this, composite);
    }

    @Override // java.util.concurrent.Callable
    @Nullable
    public Void call() {
        Future<?> future;
        Future<Void> future2;
        Future<Void> future3;
        boolean zM5m;
        Future<?> future4;
        THREAD.lazySet(this, Thread.currentThread());
        try {
            this.task.run();
        } finally {
            try {
            } catch (Throwable th) {
                do {
                    if (future == future2) {
                        break;
                    }
                    if (future == future3) {
                        break;
                    }
                } while (!zM5m);
            }
        }
        THREAD.lazySet(this, null);
        Disposable.Composite composite = this.parent;
        if (composite != DISPOSED && C0162xc40028dd.m5m(PARENT, this, composite, DONE) && composite != null) {
            composite.remove(this);
        }
        do {
            future4 = this.future;
            if (future4 == SYNC_CANCELLED || future4 == ASYNC_CANCELLED) {
                break;
            }
        } while (!C0162xc40028dd.m5m(FUTURE, this, future4, FINISHED));
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
            if (future2 == FINISHED) {
                return;
            }
            if (future2 == SYNC_CANCELLED) {
                future.cancel(false);
                return;
            } else if (future2 == ASYNC_CANCELLED) {
                future.cancel(true);
                return;
            }
        } while (!C0162xc40028dd.m5m(FUTURE, this, future2, future));
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        Disposable.Composite composite = PARENT.get(this);
        return composite == DISPOSED || composite == DONE;
    }

    @Override // reactor.core.Disposable
    public void dispose() {
        Disposable.Composite composite;
        Disposable.Composite composite2;
        Future<Void> future;
        Future<Void> future2;
        while (true) {
            Future<?> future3 = this.future;
            if (future3 == FINISHED || future3 == (future = SYNC_CANCELLED) || future3 == (future2 = ASYNC_CANCELLED)) {
                break;
            }
            boolean z = this.thread != Thread.currentThread();
            AtomicReferenceFieldUpdater<WorkerTask, Future> atomicReferenceFieldUpdater = FUTURE;
            if (z) {
                future = future2;
            }
            if (C0162xc40028dd.m5m(atomicReferenceFieldUpdater, this, future3, future)) {
                if (future3 != null) {
                    future3.cancel(z);
                }
            }
        }
        do {
            composite = this.parent;
            if (composite == DONE || composite == (composite2 = DISPOSED) || composite == null) {
                return;
            }
        } while (!C0162xc40028dd.m5m(PARENT, this, composite, composite2));
        composite.remove(this);
    }
}
