package reactor.core.publisher;

import androidx.core.app.NotificationCompat;
import com.azure.core.implementation.logging.LoggingKeys;
import com.microsoft.azure.storage.table.TableConstants;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.Metrics;
import reactor.util.function.Tuple2;

/* JADX INFO: loaded from: classes5.dex */
@Deprecated
final class FluxMetrics<T> extends InternalFluxOperator<T, T> {
    static final String METER_FLOW_DURATION = ".flow.duration";
    static final String METER_MALFORMED = ".malformed.source";
    static final String METER_ON_NEXT_DELAY = ".onNext.delay";
    static final String METER_REQUESTED = ".requested";
    static final String METER_SUBSCRIBED = ".subscribed";
    static final String REACTOR_DEFAULT_NAME = "reactor";
    final String name;
    final MeterRegistry registryCandidate;
    final Tags tags;
    static final Tags DEFAULT_TAGS_FLUX = Tags.of(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE, "Flux");
    static final Tags DEFAULT_TAGS_MONO = Tags.of(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE, "Mono");
    static final Tag TAG_ON_ERROR = Tag.of(NotificationCompat.CATEGORY_STATUS, "error");
    static final String TAG_KEY_EXCEPTION = "exception";
    static final Tags TAG_ON_COMPLETE = Tags.of(new String[]{NotificationCompat.CATEGORY_STATUS, "completed", TAG_KEY_EXCEPTION, ""});
    static final Tags TAG_ON_COMPLETE_EMPTY = Tags.of(new String[]{NotificationCompat.CATEGORY_STATUS, "completedEmpty", TAG_KEY_EXCEPTION, ""});
    static final Tags TAG_CANCEL = Tags.of(new String[]{NotificationCompat.CATEGORY_STATUS, LoggingKeys.CANCELLED_ERROR_TYPE, TAG_KEY_EXCEPTION, ""});
    static final Logger log = Loggers.getLogger((Class<?>) FluxMetrics.class);

    FluxMetrics(Flux<? extends T> flux) {
        super(flux);
        this.name = resolveName(flux);
        this.tags = resolveTags(flux, DEFAULT_TAGS_FLUX);
        this.registryCandidate = Metrics.MicrometerConfiguration.getRegistry();
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new MetricsSubscriber(coreSubscriber, this.registryCandidate, Clock.SYSTEM, this.name, this.tags);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static class MetricsSubscriber<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Clock clock;
        final Tags commonTags;
        boolean done;
        long lastNextEventNanos = -1;
        final Timer onNextIntervalTimer;
        final MeterRegistry registry;
        final DistributionSummary requestedCounter;

        /* JADX INFO: renamed from: s */
        Subscription f2154s;
        final String sequenceName;
        Timer.Sample subscribeToTerminateSample;

        MetricsSubscriber(CoreSubscriber<? super T> coreSubscriber, MeterRegistry meterRegistry, Clock clock, String str, Tags tags) {
            this.actual = coreSubscriber;
            this.clock = clock;
            this.sequenceName = str;
            this.commonTags = tags;
            this.registry = meterRegistry;
            this.onNextIntervalTimer = Timer.builder(str + FluxMetrics.METER_ON_NEXT_DELAY).tags(tags).description("Measures delays between onNext signals (or between onSubscribe and first onNext)").register(meterRegistry);
            if (!FluxMetrics.REACTOR_DEFAULT_NAME.equals(str)) {
                this.requestedCounter = DistributionSummary.builder(str + FluxMetrics.METER_REQUESTED).tags(tags).description("Counts the amount requested to a named Flux by all subscribers, until at least one requests an unbounded amount").register(meterRegistry);
            } else {
                this.requestedCounter = null;
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            FluxMetrics.recordCancel(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
            this.f2154s.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            if (this.onNextIntervalTimer.count() == 0) {
                FluxMetrics.recordOnCompleteEmpty(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
            } else {
                FluxMetrics.recordOnComplete(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
            }
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
                return;
            }
            long j = this.lastNextEventNanos;
            long jMonotonicTime = this.clock.monotonicTime();
            this.lastNextEventNanos = jMonotonicTime;
            this.onNextIntervalTimer.record(jMonotonicTime - j, TimeUnit.NANOSECONDS);
            this.actual.onNext(t);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2154s, subscription)) {
                FluxMetrics.recordOnSubscribe(this.sequenceName, this.commonTags, this.registry);
                this.subscribeToTerminateSample = Timer.start(this.clock);
                this.lastNextEventNanos = this.clock.monotonicTime();
                this.f2154s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (Operators.validate(j)) {
                DistributionSummary distributionSummary = this.requestedCounter;
                if (distributionSummary != null) {
                    distributionSummary.record(j);
                }
                this.f2154s.request(j);
            }
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static String resolveName(Publisher<?> publisher) {
        Scannable scannableFrom = Scannable.from(publisher);
        if (scannableFrom.isScanAvailable()) {
            String strName = scannableFrom.name();
            return scannableFrom.stepName().equals(strName) ? REACTOR_DEFAULT_NAME : strName;
        }
        log.warn("Attempting to activate metrics but the upstream is not Scannable. You might want to use `name()` (and optionally `tags()`) right before `metrics()`");
        return REACTOR_DEFAULT_NAME;
    }

    static Tags resolveTags(Publisher<?> publisher, Tags tags) {
        Scannable scannableFrom = Scannable.from(publisher);
        return scannableFrom.isScanAvailable() ? tags.and((List) scannableFrom.tags().map(new Function() { // from class: reactor.core.publisher.FluxMetrics$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Tuple2 tuple2 = (Tuple2) obj;
                return Tag.of((String) tuple2.getT1(), (String) tuple2.getT2());
            }
        }).collect(Collectors.toList())) : tags;
    }

    static void recordCancel(String str, Tags tags, MeterRegistry meterRegistry, Timer.Sample sample) {
        sample.stop(Timer.builder(str + METER_FLOW_DURATION).tags(tags.and(TAG_CANCEL)).description("Times the duration elapsed between a subscription and the cancellation of the sequence").register(meterRegistry));
    }

    static void recordMalformed(String str, Tags tags, MeterRegistry meterRegistry) {
        meterRegistry.counter(str + METER_MALFORMED, tags).increment();
    }

    static void recordOnError(String str, Tags tags, MeterRegistry meterRegistry, Timer.Sample sample, Throwable th) {
        sample.stop(Timer.builder(str + METER_FLOW_DURATION).tags(tags.and(new Tag[]{TAG_ON_ERROR})).tag(TAG_KEY_EXCEPTION, th.getClass().getName()).description("Times the duration elapsed between a subscription and the onError termination of the sequence, with the exception name as a tag.").register(meterRegistry));
    }

    static void recordOnComplete(String str, Tags tags, MeterRegistry meterRegistry, Timer.Sample sample) {
        sample.stop(Timer.builder(str + METER_FLOW_DURATION).tags(tags.and(TAG_ON_COMPLETE)).description("Times the duration elapsed between a subscription and the onComplete termination of a sequence that did emit some elements").register(meterRegistry));
    }

    static void recordOnCompleteEmpty(String str, Tags tags, MeterRegistry meterRegistry, Timer.Sample sample) {
        sample.stop(Timer.builder(str + METER_FLOW_DURATION).tags(tags.and(TAG_ON_COMPLETE_EMPTY)).description("Times the duration elapsed between a subscription and the onComplete termination of a sequence that didn't emit any element").register(meterRegistry));
    }

    static void recordOnSubscribe(String str, Tags tags, MeterRegistry meterRegistry) {
        Counter.builder(str + METER_SUBSCRIBED).tags(tags).description("Counts how many Reactor sequences have been subscribed to").register(meterRegistry).increment();
    }
}
