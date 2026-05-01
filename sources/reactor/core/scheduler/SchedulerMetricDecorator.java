package reactor.core.scheduler;

import com.github.houbb.heaven.constant.PunctuationConst;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import io.micrometer.core.instrument.search.Search;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.util.Metrics;

/* JADX INFO: loaded from: classes5.dex */
@Deprecated
final class SchedulerMetricDecorator implements BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService>, Disposable {
    static final String METRICS_DECORATOR_KEY = "reactor.metrics.decorator";
    static final String TAG_SCHEDULER_ID = "reactor.scheduler.id";
    final WeakHashMap<Scheduler, String> seenSchedulers = new WeakHashMap<>();
    final Map<String, AtomicInteger> schedulerDifferentiator = new HashMap();
    final WeakHashMap<Scheduler, AtomicInteger> executorDifferentiator = new WeakHashMap<>();
    final MeterRegistry registry = Metrics.MicrometerConfiguration.getRegistry();

    SchedulerMetricDecorator() {
    }

    @Override // java.util.function.BiFunction
    public synchronized ScheduledExecutorService apply(Scheduler scheduler, ScheduledExecutorService scheduledExecutorService) {
        String strComputeIfAbsent;
        final String str = (String) Scannable.from(scheduler).scanOrDefault(Scannable.Attr.NAME, scheduler.getClass().getName());
        strComputeIfAbsent = this.seenSchedulers.computeIfAbsent(scheduler, new Function() { // from class: reactor.core.scheduler.SchedulerMetricDecorator$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m4302lambda$apply$1$reactorcoreschedulerSchedulerMetricDecorator(str, (Scheduler) obj);
            }
        });
        return new C1MetricsRemovingScheduledExecutorService(scheduledExecutorService, strComputeIfAbsent + "-" + this.executorDifferentiator.computeIfAbsent(scheduler, new Function() { // from class: reactor.core.scheduler.SchedulerMetricDecorator$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SchedulerMetricDecorator.lambda$apply$2((Scheduler) obj);
            }
        }).getAndIncrement(), new Tag[]{Tag.of(TAG_SCHEDULER_ID, strComputeIfAbsent)});
    }

    /* JADX INFO: renamed from: lambda$apply$1$reactor-core-scheduler-SchedulerMetricDecorator, reason: not valid java name */
    /* synthetic */ String m4302lambda$apply$1$reactorcoreschedulerSchedulerMetricDecorator(String str, Scheduler scheduler) {
        int andIncrement = this.schedulerDifferentiator.computeIfAbsent(str, new Function() { // from class: reactor.core.scheduler.SchedulerMetricDecorator$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SchedulerMetricDecorator.lambda$apply$0((String) obj);
            }
        }).getAndIncrement();
        return andIncrement == 0 ? str : str + PunctuationConst.SHAPE + andIncrement;
    }

    static /* synthetic */ AtomicInteger lambda$apply$0(String str) {
        return new AtomicInteger(0);
    }

    static /* synthetic */ AtomicInteger lambda$apply$2(Scheduler scheduler) {
        return new AtomicInteger(0);
    }

    /* JADX INFO: renamed from: reactor.core.scheduler.SchedulerMetricDecorator$1MetricsRemovingScheduledExecutorService, reason: invalid class name */
    class C1MetricsRemovingScheduledExecutorService extends DelegatingScheduledExecutorService {
        final /* synthetic */ String val$executorId;
        final /* synthetic */ ScheduledExecutorService val$service;
        final /* synthetic */ Tag[] val$tags;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C1MetricsRemovingScheduledExecutorService(ScheduledExecutorService scheduledExecutorService, String str, Tag[] tagArr) {
            super(ExecutorServiceMetrics.monitor(SchedulerMetricDecorator.this.registry, scheduledExecutorService, str, tagArr));
            this.val$service = scheduledExecutorService;
            this.val$executorId = str;
            this.val$tags = tagArr;
        }

        @Override // reactor.core.scheduler.DelegatingScheduledExecutorService, java.util.concurrent.ExecutorService
        public List<Runnable> shutdownNow() {
            removeMetrics();
            return super.shutdownNow();
        }

        @Override // reactor.core.scheduler.DelegatingScheduledExecutorService, java.util.concurrent.ExecutorService
        public void shutdown() {
            removeMetrics();
            super.shutdown();
        }

        void removeMetrics() {
            Collection collectionMeters = Search.in(SchedulerMetricDecorator.this.registry).tag("name", this.val$executorId).meters();
            final MeterRegistry meterRegistry = SchedulerMetricDecorator.this.registry;
            Objects.requireNonNull(meterRegistry);
            collectionMeters.forEach(new Consumer() { // from class: reactor.core.scheduler.SchedulerMetricDecorator$1MetricsRemovingScheduledExecutorService$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    meterRegistry.remove((Meter) obj);
                }
            });
        }
    }

    @Override // reactor.core.Disposable
    public void dispose() {
        Collection collectionMeters = Search.in(this.registry).tagKeys(new String[]{TAG_SCHEDULER_ID}).meters();
        final MeterRegistry meterRegistry = this.registry;
        Objects.requireNonNull(meterRegistry);
        collectionMeters.forEach(new Consumer() { // from class: reactor.core.scheduler.SchedulerMetricDecorator$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                meterRegistry.remove((Meter) obj);
            }
        });
        this.seenSchedulers.clear();
        this.schedulerDifferentiator.clear();
        this.executorDifferentiator.clear();
    }
}
