package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
class ParallelFluxRestoringThreadLocals<T> extends ParallelFlux<T> implements Scannable {
    private final ParallelFlux<? extends T> source;

    ParallelFluxRestoringThreadLocals(ParallelFlux<? extends T> parallelFlux) {
        this.source = parallelFlux;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super T>[] coreSubscriberArr) {
        this.source.subscribe(Operators.restoreContextOnSubscribers(this.source, coreSubscriberArr));
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }
}
