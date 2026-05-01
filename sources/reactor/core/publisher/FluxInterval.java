package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxInterval extends Flux<Long> implements SourceProducer<Long> {
    final long initialDelay;
    final long period;
    final Scheduler timedScheduler;
    final TimeUnit unit;

    FluxInterval(long j, long j2, TimeUnit timeUnit, Scheduler scheduler) {
        if (j2 < 0) {
            throw new IllegalArgumentException("period >= 0 required but it was " + j2);
        }
        this.initialDelay = j;
        this.period = j2;
        this.unit = (TimeUnit) Objects.requireNonNull(timeUnit, "unit");
        this.timedScheduler = (Scheduler) Objects.requireNonNull(scheduler, "timedScheduler");
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super Long> coreSubscriber) {
        Scheduler.Worker workerCreateWorker = this.timedScheduler.createWorker();
        IntervalRunnable intervalRunnable = new IntervalRunnable(coreSubscriber, workerCreateWorker);
        coreSubscriber.onSubscribe(intervalRunnable);
        try {
            workerCreateWorker.schedulePeriodically(intervalRunnable, this.initialDelay, this.period, this.unit);
        } catch (RejectedExecutionException e) {
            if (intervalRunnable.cancelled) {
                return;
            }
            coreSubscriber.onError(Operators.onRejectedExecution(e, intervalRunnable, null, null, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.timedScheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
    }

    static final class IntervalRunnable implements Runnable, Subscription, InnerProducer<Long> {
        static final AtomicLongFieldUpdater<IntervalRunnable> REQUESTED = AtomicLongFieldUpdater.newUpdater(IntervalRunnable.class, "requested");
        final CoreSubscriber<? super Long> actual;
        volatile boolean cancelled;
        long count;
        volatile long requested;
        final Scheduler.Worker worker;

        IntervalRunnable(CoreSubscriber<? super Long> coreSubscriber, Scheduler.Worker worker) {
            this.actual = coreSubscriber;
            this.worker = worker;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Long> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.RUN_ON ? this.worker : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.cancelled) {
                return;
            }
            if (this.requested != 0) {
                CoreSubscriber<? super Long> coreSubscriber = this.actual;
                long j = this.count;
                this.count = 1 + j;
                coreSubscriber.onNext(Long.valueOf(j));
                if (this.requested != Long.MAX_VALUE) {
                    REQUESTED.decrementAndGet(this);
                    return;
                }
                return;
            }
            cancel();
            this.actual.onError(Exceptions.failWithOverflow("Could not emit tick " + this.count + " due to lack of requests (interval doesn't support small downstream requests that replenish slower than the ticks)"));
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.worker.dispose();
        }
    }
}
