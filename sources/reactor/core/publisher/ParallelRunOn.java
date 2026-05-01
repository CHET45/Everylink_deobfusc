package reactor.core.publisher;

import java.util.Queue;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPublishOn;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelRunOn<T> extends ParallelFlux<T> implements Scannable {
    final int prefetch;
    final Supplier<Queue<T>> queueSupplier;
    final Scheduler scheduler;
    final ParallelFlux<? extends T> source;

    ParallelRunOn(ParallelFlux<? extends T> parallelFlux, Scheduler scheduler, int i, Supplier<Queue<T>> supplier) {
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.scheduler = scheduler;
        this.prefetch = i;
        this.queueSupplier = supplier;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super T>[] coreSubscriberArr) {
        if (validate(coreSubscriberArr)) {
            int length = coreSubscriberArr.length;
            CoreSubscriber<? super Object>[] coreSubscriberArr2 = new CoreSubscriber[length];
            boolean z = coreSubscriberArr[0] instanceof Fuseable.ConditionalSubscriber;
            for (int i = 0; i < length; i++) {
                Scheduler.Worker workerCreateWorker = this.scheduler.createWorker();
                if (z) {
                    Fuseable.ConditionalSubscriber conditionalSubscriber = (Fuseable.ConditionalSubscriber) coreSubscriberArr[i];
                    Scheduler scheduler = this.scheduler;
                    int i2 = this.prefetch;
                    coreSubscriberArr2[i] = new FluxPublishOn.PublishOnConditionalSubscriber(conditionalSubscriber, scheduler, workerCreateWorker, true, i2, i2, this.queueSupplier);
                } else {
                    CoreSubscriber<? super T> coreSubscriber = coreSubscriberArr[i];
                    Scheduler scheduler2 = this.scheduler;
                    int i3 = this.prefetch;
                    coreSubscriberArr2[i] = new FluxPublishOn.PublishOnSubscriber(coreSubscriber, scheduler2, workerCreateWorker, true, i3, i3, this.queueSupplier);
                }
            }
            this.source.subscribe(coreSubscriberArr2);
        }
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.source.parallelism();
    }
}
