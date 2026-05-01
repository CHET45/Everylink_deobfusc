package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPeek;
import reactor.core.publisher.FluxPeekFuseable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelLog<T> extends ParallelFlux<T> implements Scannable {
    final SignalPeek<T> log;
    final ParallelFlux<T> source;

    ParallelLog(ParallelFlux<T> parallelFlux, SignalPeek<T> signalPeek) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.log = signalPeek;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super T>[] coreSubscriberArr) {
        if (validate(coreSubscriberArr)) {
            int length = coreSubscriberArr.length;
            CoreSubscriber<? super T>[] coreSubscriberArr2 = new CoreSubscriber[length];
            boolean z = coreSubscriberArr[0] instanceof Fuseable.ConditionalSubscriber;
            for (int i = 0; i < length; i++) {
                if (z) {
                    coreSubscriberArr2[i] = new FluxPeekFuseable.PeekConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriberArr[i], this.log);
                } else {
                    coreSubscriberArr2[i] = new FluxPeek.PeekSubscriber(coreSubscriberArr[i], this.log);
                }
            }
            this.source.subscribe(coreSubscriberArr2);
        }
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }
}
