package reactor.core.scheduler;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;

/* JADX INFO: loaded from: classes5.dex */
final class ExecutorScheduler implements Scheduler, Scannable {
    final Executor executor;
    volatile boolean terminated;
    final boolean trampoline;

    interface WorkerDelete {
        void delete(ExecutorTrackedRunnable executorTrackedRunnable);
    }

    ExecutorScheduler(Executor executor, boolean z) {
        this.executor = executor;
        this.trampoline = z;
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedule(Runnable runnable) {
        if (this.terminated) {
            throw Exceptions.failWithRejected();
        }
        Objects.requireNonNull(runnable, "task");
        ExecutorPlainRunnable executorPlainRunnable = new ExecutorPlainRunnable(Schedulers.onSchedule(runnable));
        try {
            this.executor.execute(executorPlainRunnable);
            return executorPlainRunnable;
        } catch (Throwable th) {
            Executor executor = this.executor;
            if ((executor instanceof ExecutorService) && ((ExecutorService) executor).isShutdown()) {
                this.terminated = true;
            }
            Schedulers.handleError(th);
            throw Exceptions.failWithRejected(th);
        }
    }

    @Override // reactor.core.scheduler.Scheduler, reactor.core.Disposable
    public void dispose() {
        this.terminated = true;
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        return this.terminated;
    }

    @Override // reactor.core.scheduler.Scheduler
    public Scheduler.Worker createWorker() {
        return this.trampoline ? new ExecutorSchedulerTrampolineWorker(this.executor) : new ExecutorSchedulerWorker(this.executor);
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder("fromExecutor(").append(this.executor);
        if (this.trampoline) {
            sbAppend.append(",trampolining");
        }
        sbAppend.append(')');
        return sbAppend.toString();
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(isDisposed());
        }
        if (attr == Scannable.Attr.NAME) {
            return toString();
        }
        return null;
    }

    static final class ExecutorPlainRunnable extends AtomicBoolean implements Runnable, Disposable {
        private static final long serialVersionUID = 5116223460201378097L;
        final Runnable task;

        ExecutorPlainRunnable(Runnable runnable) {
            this.task = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (get()) {
                return;
            }
            try {
                this.task.run();
            } finally {
                try {
                } finally {
                }
            }
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return get();
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            set(true);
        }
    }

    static final class ExecutorTrackedRunnable extends AtomicBoolean implements Runnable, Disposable {
        private static final long serialVersionUID = 3503344795919906192L;
        final boolean callRemoveOnFinish;
        final WorkerDelete parent;
        final Runnable task;

        ExecutorTrackedRunnable(Runnable runnable, WorkerDelete workerDelete, boolean z) {
            this.task = runnable;
            this.parent = workerDelete;
            this.callRemoveOnFinish = z;
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x001e, code lost:
        
            if (r3.callRemoveOnFinish == false) goto L9;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:?, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:?, code lost:
        
            return;
         */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r3 = this;
                boolean r0 = r3.get()
                if (r0 != 0) goto L2e
                r0 = 1
                java.lang.Runnable r1 = r3.task     // Catch: java.lang.Throwable -> L18
                r1.run()     // Catch: java.lang.Throwable -> L18
                boolean r1 = r3.callRemoveOnFinish
                if (r1 == 0) goto L14
            L10:
                r3.dispose()
                goto L2e
            L14:
                r3.lazySet(r0)
                goto L2e
            L18:
                r1 = move-exception
                reactor.core.scheduler.Schedulers.handleError(r1)     // Catch: java.lang.Throwable -> L21
                boolean r1 = r3.callRemoveOnFinish
                if (r1 == 0) goto L14
                goto L10
            L21:
                r1 = move-exception
                boolean r2 = r3.callRemoveOnFinish
                if (r2 == 0) goto L2a
                r3.dispose()
                goto L2d
            L2a:
                r3.lazySet(r0)
            L2d:
                throw r1
            L2e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.scheduler.ExecutorScheduler.ExecutorTrackedRunnable.run():void");
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            if (compareAndSet(false, true)) {
                this.parent.delete(this);
            }
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return get();
        }
    }

    static final class ExecutorSchedulerWorker implements Scheduler.Worker, WorkerDelete, Scannable {
        final Executor executor;
        final Disposable.Composite tasks = Disposables.composite();
        private final boolean wrapSchedule;

        ExecutorSchedulerWorker(Executor executor) {
            this.executor = executor;
            this.wrapSchedule = !(executor instanceof Scheduler);
        }

        @Override // reactor.core.scheduler.Scheduler.Worker
        public Disposable schedule(Runnable runnable) {
            Objects.requireNonNull(runnable, "task");
            if (this.wrapSchedule) {
                runnable = Schedulers.onSchedule(runnable);
            }
            ExecutorTrackedRunnable executorTrackedRunnable = new ExecutorTrackedRunnable(runnable, this, true);
            if (!this.tasks.add(executorTrackedRunnable)) {
                throw Exceptions.failWithRejected();
            }
            try {
                this.executor.execute(executorTrackedRunnable);
                return executorTrackedRunnable;
            } catch (Throwable th) {
                this.tasks.remove(executorTrackedRunnable);
                Schedulers.handleError(th);
                throw Exceptions.failWithRejected(th);
            }
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            this.tasks.dispose();
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return this.tasks.isDisposed();
        }

        @Override // reactor.core.scheduler.ExecutorScheduler.WorkerDelete
        public void delete(ExecutorTrackedRunnable executorTrackedRunnable) {
            this.tasks.remove(executorTrackedRunnable);
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isDisposed());
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.tasks.size());
            }
            if (attr != Scannable.Attr.PARENT) {
                if (attr == Scannable.Attr.NAME) {
                    return this.executor instanceof SingleWorkerScheduler ? this.executor + ".worker" : "fromExecutor(" + this.executor + ").worker";
                }
                return Schedulers.scanExecutor(this.executor, attr);
            }
            Executor executor = this.executor;
            if (executor instanceof Scannable) {
                return executor;
            }
            return null;
        }
    }

    static final class ExecutorSchedulerTrampolineWorker implements Scheduler.Worker, WorkerDelete, Runnable, Scannable {
        static final AtomicIntegerFieldUpdater<ExecutorSchedulerTrampolineWorker> WIP = AtomicIntegerFieldUpdater.newUpdater(ExecutorSchedulerTrampolineWorker.class, "wip");
        final Executor executor;
        final Queue<ExecutorTrackedRunnable> queue = new ConcurrentLinkedQueue();
        volatile boolean terminated;
        volatile int wip;
        private final boolean wrapSchedule;

        ExecutorSchedulerTrampolineWorker(Executor executor) {
            this.executor = executor;
            this.wrapSchedule = !(executor instanceof Scheduler);
        }

        @Override // reactor.core.scheduler.Scheduler.Worker
        public Disposable schedule(Runnable runnable) {
            Objects.requireNonNull(runnable, "task");
            if (this.terminated) {
                throw Exceptions.failWithRejected();
            }
            if (this.wrapSchedule) {
                runnable = Schedulers.onSchedule(runnable);
            }
            ExecutorTrackedRunnable executorTrackedRunnable = new ExecutorTrackedRunnable(runnable, this, false);
            synchronized (this) {
                if (this.terminated) {
                    throw Exceptions.failWithRejected();
                }
                this.queue.offer(executorTrackedRunnable);
            }
            if (WIP.getAndIncrement(this) == 0) {
                try {
                    this.executor.execute(this);
                } catch (Throwable th) {
                    executorTrackedRunnable.dispose();
                    Schedulers.handleError(th);
                    throw Exceptions.failWithRejected(th);
                }
            }
            return executorTrackedRunnable;
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            if (this.terminated) {
                return;
            }
            this.terminated = true;
            Queue<ExecutorTrackedRunnable> queue = this.queue;
            while (true) {
                ExecutorTrackedRunnable executorTrackedRunnablePoll = queue.poll();
                if (executorTrackedRunnablePoll == null || queue.isEmpty()) {
                    return;
                } else {
                    executorTrackedRunnablePoll.dispose();
                }
            }
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return this.terminated;
        }

        @Override // reactor.core.scheduler.ExecutorScheduler.WorkerDelete
        public void delete(ExecutorTrackedRunnable executorTrackedRunnable) {
            synchronized (this) {
                if (!this.terminated) {
                    this.queue.remove(executorTrackedRunnable);
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            int i;
            Queue<ExecutorTrackedRunnable> queue = this.queue;
            do {
                int i2 = this.wip;
                i = 0;
                while (i != i2) {
                    if (this.terminated) {
                        return;
                    }
                    ExecutorTrackedRunnable executorTrackedRunnablePoll = queue.poll();
                    if (executorTrackedRunnablePoll == null) {
                        break;
                    }
                    executorTrackedRunnablePoll.run();
                    i++;
                }
                if (i == i2 && this.terminated) {
                    return;
                }
            } while (WIP.addAndGet(this, -i) != 0);
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isDisposed());
            }
            if (attr == Scannable.Attr.PARENT) {
                Executor executor = this.executor;
                if (executor instanceof Scannable) {
                    return executor;
                }
                return null;
            }
            if (attr == Scannable.Attr.NAME) {
                return "fromExecutor(" + this.executor + ",trampolining).worker";
            }
            if (attr == Scannable.Attr.BUFFERED || attr == Scannable.Attr.LARGE_BUFFERED) {
                return Integer.valueOf(this.queue.size());
            }
            return Schedulers.scanExecutor(this.executor, attr);
        }
    }
}
