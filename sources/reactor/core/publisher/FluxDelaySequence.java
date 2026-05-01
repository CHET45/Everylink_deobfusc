package reactor.core.publisher;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDelaySequence<T> extends InternalFluxOperator<T, T> {
    final Duration delay;
    final Scheduler scheduler;

    FluxDelaySequence(Flux<T> flux, Duration duration, Scheduler scheduler) {
        super(flux);
        this.delay = duration;
        this.scheduler = scheduler;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new DelaySubscriber(coreSubscriber, this.delay, this.scheduler.createWorker());
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
    }

    static final class DelaySubscriber<T> implements InnerOperator<T, T> {
        static final AtomicLongFieldUpdater<DelaySubscriber> DELAYED = AtomicLongFieldUpdater.newUpdater(DelaySubscriber.class, "delayed");
        final CoreSubscriber<? super T> actual;
        final long delay;
        volatile long delayed;
        volatile boolean done;

        /* JADX INFO: renamed from: s */
        Subscription f2107s;
        final TimeUnit timeUnit = TimeUnit.NANOSECONDS;

        /* JADX INFO: renamed from: w */
        final Scheduler.Worker f2108w;

        DelaySubscriber(CoreSubscriber<? super T> coreSubscriber, Duration duration, Scheduler.Worker worker) {
            this.actual = Operators.serialize(coreSubscriber);
            this.f2108w = worker;
            this.delay = duration.toNanos();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2107s, subscription)) {
                this.f2107s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(final T t) {
            if (this.done || this.delayed < 0) {
                Operators.onNextDropped(t, currentContext());
            } else {
                DELAYED.incrementAndGet(this);
                this.f2108w.schedule(new Runnable() { // from class: reactor.core.publisher.FluxDelaySequence$DelaySubscriber$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m1961x57c74b37(t);
                    }
                }, this.delay, this.timeUnit);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: delayedNext, reason: merged with bridge method [inline-methods] */
        public void m1961x57c74b37(T t) {
            DELAYED.decrementAndGet(this);
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, currentContext());
                return;
            }
            this.done = true;
            if (DELAYED.compareAndSet(this, 0L, -1L)) {
                this.actual.onError(th);
            } else {
                this.f2108w.schedule(new OnError(th), this.delay, this.timeUnit);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            if (DELAYED.compareAndSet(this, 0L, -1L)) {
                this.actual.onComplete();
            } else {
                this.f2108w.schedule(new OnComplete(), this.delay, this.timeUnit);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2107s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2107s.cancel();
            this.f2108w.dispose();
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2107s;
            }
            if (attr == Scannable.Attr.RUN_ON) {
                return this.f2108w;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2108w.isDisposed() && !this.done);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
        }

        final class OnError implements Runnable {

            /* JADX INFO: renamed from: t */
            private final Throwable f2109t;

            OnError(Throwable th) {
                this.f2109t = th;
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    DelaySubscriber.this.actual.onError(this.f2109t);
                } finally {
                    DelaySubscriber.this.f2108w.dispose();
                }
            }
        }

        final class OnComplete implements Runnable {
            OnComplete() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    DelaySubscriber.this.actual.onComplete();
                } finally {
                    DelaySubscriber.this.f2108w.dispose();
                }
            }
        }
    }
}
