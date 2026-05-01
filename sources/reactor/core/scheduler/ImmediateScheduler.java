package reactor.core.scheduler;

import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;

/* JADX INFO: loaded from: classes5.dex */
final class ImmediateScheduler implements Scheduler, Scannable {
    static final Disposable FINISHED;
    private static final ImmediateScheduler INSTANCE;

    @Override // reactor.core.scheduler.Scheduler, reactor.core.Disposable
    public void dispose() {
    }

    static {
        ImmediateScheduler immediateScheduler = new ImmediateScheduler();
        INSTANCE = immediateScheduler;
        immediateScheduler.init();
        FINISHED = Disposables.disposed();
    }

    public static Scheduler instance() {
        return INSTANCE;
    }

    private ImmediateScheduler() {
    }

    @Override // reactor.core.scheduler.Scheduler
    public Disposable schedule(Runnable runnable) {
        runnable.run();
        return FINISHED;
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(isDisposed());
        }
        if (attr == Scannable.Attr.NAME) {
            return "immediate";
        }
        return null;
    }

    @Override // reactor.core.scheduler.Scheduler
    public Scheduler.Worker createWorker() {
        return new ImmediateSchedulerWorker();
    }

    static final class ImmediateSchedulerWorker implements Scheduler.Worker, Scannable {
        volatile boolean shutdown;

        ImmediateSchedulerWorker() {
        }

        @Override // reactor.core.scheduler.Scheduler.Worker
        public Disposable schedule(Runnable runnable) {
            if (this.shutdown) {
                throw Exceptions.failWithRejected();
            }
            runnable.run();
            return ImmediateScheduler.FINISHED;
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            this.shutdown = true;
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return this.shutdown;
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.shutdown);
            }
            if (attr == Scannable.Attr.NAME) {
                return "immediate.worker";
            }
            return null;
        }
    }
}
