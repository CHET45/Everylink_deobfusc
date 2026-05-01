package reactor.core.publisher;

import java.util.Comparator;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxMergeComparing;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelMergeOrdered<T> extends Flux<T> implements Scannable {
    final int prefetch;
    final ParallelFlux<? extends T> source;
    final Comparator<? super T> valueComparator;

    ParallelMergeOrdered(ParallelFlux<? extends T> parallelFlux, int i, Comparator<? super T> comparator) {
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.prefetch = i;
        this.valueComparator = comparator;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.prefetch) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        FluxMergeComparing.MergeOrderedMainProducer mergeOrderedMainProducer = new FluxMergeComparing.MergeOrderedMainProducer(coreSubscriber, this.valueComparator, this.prefetch, this.source.parallelism(), true, true);
        coreSubscriber.onSubscribe(mergeOrderedMainProducer);
        this.source.subscribe(mergeOrderedMainProducer.subscribers);
    }
}
