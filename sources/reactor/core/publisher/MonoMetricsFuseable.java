package reactor.core.publisher;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.MonoMetrics;
import reactor.util.Metrics;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
@Deprecated
final class MonoMetricsFuseable<T> extends InternalMonoOperator<T, T> implements Fuseable {
    final String name;
    final MeterRegistry registryCandidate;
    final Tags tags;

    MonoMetricsFuseable(Mono<? extends T> mono) {
        super(mono);
        this.name = FluxMetrics.resolveName(mono);
        this.tags = FluxMetrics.resolveTags(mono, FluxMetrics.DEFAULT_TAGS_MONO);
        this.registryCandidate = Metrics.MicrometerConfiguration.getRegistry();
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new MetricsFuseableSubscriber(coreSubscriber, this.registryCandidate, Clock.SYSTEM, this.name, this.tags);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class MetricsFuseableSubscriber<T> extends MonoMetrics.MetricsSubscriber<T> implements Fuseable, Fuseable.QueueSubscription<T> {
        int mode;

        /* JADX INFO: renamed from: qs */
        @Nullable
        Fuseable.QueueSubscription<T> f2260qs;

        MetricsFuseableSubscriber(CoreSubscriber<? super T> coreSubscriber, MeterRegistry meterRegistry, Clock clock, String str, Tags tags) {
            super(coreSubscriber, meterRegistry, clock, str, tags);
        }

        @Override // java.util.Collection
        public void clear() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2260qs;
            if (queueSubscription != null) {
                queueSubscription.clear();
            }
        }

        @Override // reactor.core.publisher.MonoMetrics.MetricsSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.mode == 2) {
                if (!this.done) {
                    FluxMetrics.recordOnCompleteEmpty(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
                }
                this.actual.onComplete();
            } else {
                if (this.done) {
                    return;
                }
                this.done = true;
                FluxMetrics.recordOnCompleteEmpty(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
                this.actual.onComplete();
            }
        }

        @Override // reactor.core.publisher.MonoMetrics.MetricsSubscriber, org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.mode == 2) {
                this.actual.onNext(null);
                return;
            }
            if (this.done) {
                FluxMetrics.recordMalformed(this.sequenceName, this.commonTags, this.registry);
                Operators.onNextDropped(t, this.actual.currentContext());
            } else {
                this.done = true;
                FluxMetrics.recordOnComplete(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
                this.actual.onNext(t);
                this.actual.onComplete();
            }
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2260qs;
            return queueSubscription == null || queueSubscription.isEmpty();
        }

        @Override // reactor.core.publisher.MonoMetrics.MetricsSubscriber, reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2259s, subscription)) {
                FluxMetrics.recordOnSubscribe(this.sequenceName, this.commonTags, this.registry);
                this.subscribeToTerminateSample = Timer.start(this.clock);
                this.f2260qs = Operators.m1969as(subscription);
                this.f2259s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2260qs;
            if (queueSubscription == null) {
                return null;
            }
            try {
                T tPoll = queueSubscription.poll();
                if (!this.done) {
                    if (tPoll == null && this.mode == 1) {
                        FluxMetrics.recordOnCompleteEmpty(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
                    } else if (tPoll != null) {
                        FluxMetrics.recordOnComplete(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
                    }
                }
                this.done = true;
                return tPoll;
            } catch (Throwable th) {
                FluxMetrics.recordOnError(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample, th);
                throw th;
            }
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2260qs;
            if (queueSubscription == null) {
                return 0;
            }
            int iRequestFusion = queueSubscription.requestFusion(i);
            this.mode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Collection
        public int size() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2260qs;
            if (queueSubscription == null) {
                return 0;
            }
            return queueSubscription.size();
        }

        @Override // reactor.core.publisher.MonoMetrics.MetricsSubscriber, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
