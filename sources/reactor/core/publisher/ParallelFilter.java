package reactor.core.publisher;

import java.util.function.Predicate;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFilter;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelFilter<T> extends ParallelFlux<T> implements Scannable {
    final Predicate<? super T> predicate;
    final ParallelFlux<T> source;

    ParallelFilter(ParallelFlux<T> parallelFlux, Predicate<? super T> predicate) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.predicate = predicate;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super T>[] coreSubscriberArr) {
        if (validate(coreSubscriberArr)) {
            int length = coreSubscriberArr.length;
            CoreSubscriber<? super T>[] coreSubscriberArr2 = new CoreSubscriber[length];
            boolean z = coreSubscriberArr[0] instanceof Fuseable.ConditionalSubscriber;
            for (int i = 0; i < length; i++) {
                if (z) {
                    coreSubscriberArr2[i] = new FluxFilter.FilterConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriberArr[i], this.predicate);
                } else {
                    coreSubscriberArr2[i] = new FluxFilter.FilterSubscriber(coreSubscriberArr[i], this.predicate);
                }
            }
            this.source.subscribe(coreSubscriberArr2);
        }
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.source.parallelism();
    }
}
