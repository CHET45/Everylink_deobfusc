package reactor.core.publisher;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxTimed<T> extends InternalFluxOperator<T, Timed<T>> {
    final Scheduler clock;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return 0;
    }

    FluxTimed(Flux<? extends T> flux, Scheduler scheduler) {
        super(flux);
        this.clock = scheduler;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Timed<T>> coreSubscriber) {
        return new TimedSubscriber(coreSubscriber, this.clock);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ImmutableTimed<T> implements Timed<T> {
        final T event;
        final long eventElapsedNanos;
        final long eventElapsedSinceSubscriptionNanos;
        final long eventTimestampEpochMillis;

        ImmutableTimed(long j, long j2, long j3, T t) {
            this.eventElapsedSinceSubscriptionNanos = j;
            this.eventElapsedNanos = j2;
            this.eventTimestampEpochMillis = j3;
            this.event = t;
        }

        @Override // reactor.core.publisher.Timed, java.util.function.Supplier
        public T get() {
            return this.event;
        }

        @Override // reactor.core.publisher.Timed
        public Duration elapsed() {
            return Duration.ofNanos(this.eventElapsedNanos);
        }

        @Override // reactor.core.publisher.Timed
        public Duration elapsedSinceSubscription() {
            return Duration.ofNanos(this.eventElapsedSinceSubscriptionNanos);
        }

        @Override // reactor.core.publisher.Timed
        public Instant timestamp() {
            return Instant.ofEpochMilli(this.eventTimestampEpochMillis);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ImmutableTimed immutableTimed = (ImmutableTimed) obj;
            return this.eventElapsedSinceSubscriptionNanos == immutableTimed.eventElapsedSinceSubscriptionNanos && this.eventElapsedNanos == immutableTimed.eventElapsedNanos && this.eventTimestampEpochMillis == immutableTimed.eventTimestampEpochMillis && this.event.equals(immutableTimed.event);
        }

        public int hashCode() {
            return Objects.hash(Long.valueOf(this.eventElapsedSinceSubscriptionNanos), Long.valueOf(this.eventElapsedNanos), Long.valueOf(this.eventTimestampEpochMillis), this.event);
        }

        public String toString() {
            return "Timed(" + this.event + "){eventElapsedNanos=" + this.eventElapsedNanos + ", eventElapsedSinceSubscriptionNanos=" + this.eventElapsedSinceSubscriptionNanos + ",  eventTimestampEpochMillis=" + this.eventTimestampEpochMillis + '}';
        }
    }

    static final class TimedSubscriber<T> implements InnerOperator<T, Timed<T>> {
        final CoreSubscriber<? super Timed<T>> actual;
        final Scheduler clock;
        boolean done;
        long lastEventNanos;

        /* JADX INFO: renamed from: s */
        Subscription f2221s;
        long subscriptionNanos;

        TimedSubscriber(CoreSubscriber<? super Timed<T>> coreSubscriber, Scheduler scheduler) {
            this.actual = coreSubscriber;
            this.clock = scheduler;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Timed<T>> actual() {
            return this.actual;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2221s, subscription)) {
                this.f2221s = subscription;
                long jNow = this.clock.now(TimeUnit.NANOSECONDS);
                this.subscriptionNanos = jNow;
                this.lastEventNanos = jNow;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, currentContext());
                return;
            }
            long jNow = this.clock.now(TimeUnit.NANOSECONDS);
            ImmutableTimed immutableTimed = new ImmutableTimed(jNow - this.subscriptionNanos, jNow - this.lastEventNanos, this.clock.now(TimeUnit.MILLISECONDS), t);
            this.lastEventNanos = jNow;
            this.actual.onNext(immutableTimed);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, currentContext());
            } else {
                this.done = true;
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                this.f2221s.request(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2221s.cancel();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
