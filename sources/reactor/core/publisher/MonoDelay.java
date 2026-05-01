package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoDelay extends Mono<Long> implements Scannable, SourceProducer<Long> {
    static final String CONTEXT_OPT_OUT_NOBACKPRESSURE = "reactor.core.publisher.MonoDelay.failOnBackpressure";
    final long delay;
    final Scheduler timedScheduler;
    final TimeUnit unit;

    MonoDelay(long j, TimeUnit timeUnit, Scheduler scheduler) {
        this.delay = j;
        this.unit = (TimeUnit) Objects.requireNonNull(timeUnit, "unit");
        this.timedScheduler = (Scheduler) Objects.requireNonNull(scheduler, "timedScheduler");
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super Long> coreSubscriber) {
        MonoDelayRunnable monoDelayRunnable = new MonoDelayRunnable(coreSubscriber, coreSubscriber.currentContext().getOrDefault(CONTEXT_OPT_OUT_NOBACKPRESSURE, false) == Boolean.TRUE);
        coreSubscriber.onSubscribe(monoDelayRunnable);
        try {
            monoDelayRunnable.setCancel(this.timedScheduler.schedule(monoDelayRunnable, this.delay, this.unit));
        } catch (RejectedExecutionException e) {
            if (MonoDelayRunnable.wasCancelled(monoDelayRunnable.state)) {
                return;
            }
            coreSubscriber.onError(Operators.onRejectedExecution(e, monoDelayRunnable, null, null, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.timedScheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
    }

    static final class MonoDelayRunnable implements Runnable, InnerProducer<Long> {
        static final byte FLAG_CANCELLED = 64;
        static final byte FLAG_CANCEL_SET = 1;
        static final byte FLAG_DELAY_DONE = 2;
        static final byte FLAG_PROPAGATED = 4;
        static final byte FLAG_REQUESTED = 32;
        static final byte FLAG_REQUESTED_EARLY = 16;
        static final AtomicIntegerFieldUpdater<MonoDelayRunnable> STATE = AtomicIntegerFieldUpdater.newUpdater(MonoDelayRunnable.class, "state");
        final CoreSubscriber<? super Long> actual;
        Disposable cancel;
        final boolean failOnBackpressure;
        volatile int state;

        static boolean wasCancelFutureSet(int i) {
            return (i & 1) == 1;
        }

        static boolean wasCancelled(int i) {
            return (i & 64) == 64;
        }

        static boolean wasDelayDone(int i) {
            return (i & 2) == 2;
        }

        static boolean wasPropagated(int i) {
            return (i & 4) == 4;
        }

        static boolean wasRequested(int i) {
            return (i & 32) == 32;
        }

        MonoDelayRunnable(CoreSubscriber<? super Long> coreSubscriber, boolean z) {
            this.actual = coreSubscriber;
            this.failOnBackpressure = z;
        }

        static int markCancelFutureSet(MonoDelayRunnable monoDelayRunnable) {
            int i;
            do {
                i = monoDelayRunnable.state;
                if (wasCancelled(i) || wasCancelFutureSet(i)) {
                    break;
                }
            } while (!STATE.compareAndSet(monoDelayRunnable, i, i | 1));
            return i;
        }

        static int markCancelled(MonoDelayRunnable monoDelayRunnable) {
            int i;
            do {
                i = monoDelayRunnable.state;
                if (wasCancelled(i) || wasPropagated(i)) {
                    break;
                }
            } while (!STATE.compareAndSet(monoDelayRunnable, i, i | 64));
            return i;
        }

        static int markDelayDone(MonoDelayRunnable monoDelayRunnable) {
            int i;
            do {
                i = monoDelayRunnable.state;
                if (wasCancelled(i) || wasDelayDone(i)) {
                    break;
                }
            } while (!STATE.compareAndSet(monoDelayRunnable, i, i | 2));
            return i;
        }

        static int markRequested(MonoDelayRunnable monoDelayRunnable) {
            int i;
            do {
                i = monoDelayRunnable.state;
                if (wasCancelled(i) || wasRequested(i)) {
                    break;
                }
            } while (!STATE.compareAndSet(monoDelayRunnable, i, (!wasDelayDone(i) ? 48 : 32) | i));
            return i;
        }

        static int markPropagated(MonoDelayRunnable monoDelayRunnable) {
            int i;
            do {
                i = monoDelayRunnable.state;
                if (wasCancelled(i)) {
                    return i;
                }
            } while (!STATE.compareAndSet(monoDelayRunnable, i, i | 4));
            return i;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Long> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(wasDelayDone(this.state) && wasRequested(this.state));
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(wasRequested(this.state) ? 1L : 0L);
            }
            return attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(wasCancelled(this.state)) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
        }

        void setCancel(Disposable disposable) {
            Disposable disposable2 = this.cancel;
            this.cancel = disposable;
            int iMarkCancelFutureSet = markCancelFutureSet(this);
            if (wasCancelFutureSet(iMarkCancelFutureSet)) {
                if (disposable2 != null) {
                    disposable2.dispose();
                }
            } else if (wasCancelled(iMarkCancelFutureSet)) {
                disposable.dispose();
            }
        }

        private void propagateDelay() {
            if (wasCancelled(markPropagated(this))) {
                return;
            }
            try {
                this.actual.onNext(0L);
                this.actual.onComplete();
            } catch (Throwable th) {
                CoreSubscriber<? super Long> coreSubscriber = this.actual;
                coreSubscriber.onError(Operators.onOperatorError(th, coreSubscriber.currentContext()));
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            int iMarkDelayDone = markDelayDone(this);
            if (wasCancelled(iMarkDelayDone) || wasDelayDone(iMarkDelayDone)) {
                return;
            }
            if (wasRequested(iMarkDelayDone)) {
                propagateDelay();
            } else if (this.failOnBackpressure) {
                this.actual.onError(Exceptions.failWithOverflow("Could not emit value due to lack of requests"));
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            int iMarkCancelled = markCancelled(this);
            if (wasCancelled(iMarkCancelled) || wasPropagated(iMarkCancelled) || !wasCancelFutureSet(iMarkCancelled)) {
                return;
            }
            this.cancel.dispose();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                int iMarkRequested = markRequested(this);
                if (wasCancelled(iMarkRequested) || wasRequested(iMarkRequested) || !wasDelayDone(iMarkRequested) || this.failOnBackpressure) {
                    return;
                }
                propagateDelay();
            }
        }
    }
}
