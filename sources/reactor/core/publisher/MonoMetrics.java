package reactor.core.publisher;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.Metrics;

/* JADX INFO: loaded from: classes5.dex */
@Deprecated
final class MonoMetrics<T> extends InternalMonoOperator<T, T> {
    final String name;
    final MeterRegistry registryCandidate;
    final Tags tags;

    MonoMetrics(Mono<? extends T> mono) {
        super(mono);
        this.name = FluxMetrics.resolveName(mono);
        this.tags = FluxMetrics.resolveTags(mono, FluxMetrics.DEFAULT_TAGS_MONO);
        this.registryCandidate = Metrics.MicrometerConfiguration.getRegistry();
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new MetricsSubscriber(coreSubscriber, this.registryCandidate, Clock.SYSTEM, this.name, this.tags);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static class MetricsSubscriber<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Clock clock;
        final Tags commonTags;
        boolean done;
        final MeterRegistry registry;

        /* JADX INFO: renamed from: s */
        Subscription f2259s;
        final String sequenceName;
        Timer.Sample subscribeToTerminateSample;

        MetricsSubscriber(CoreSubscriber<? super T> coreSubscriber, MeterRegistry meterRegistry, Clock clock, String str, Tags tags) {
            this.actual = coreSubscriber;
            this.clock = clock;
            this.sequenceName = str;
            this.commonTags = tags;
            this.registry = meterRegistry;
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            FluxMetrics.recordCancel(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
            this.f2259s.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            FluxMetrics.recordOnCompleteEmpty(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onError(Throwable th) {
            if (this.done) {
                FluxMetrics.recordMalformed(this.sequenceName, this.commonTags, this.registry);
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                this.done = true;
                FluxMetrics.recordOnError(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample, th);
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
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

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2259s, subscription)) {
                FluxMetrics.recordOnSubscribe(this.sequenceName, this.commonTags, this.registry);
                this.subscribeToTerminateSample = Timer.start(this.clock);
                this.f2259s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (Operators.validate(j)) {
                this.f2259s.request(j);
            }
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
