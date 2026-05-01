package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxTake;

/* JADX INFO: loaded from: classes5.dex */
final class FluxTakeFuseable<T> extends InternalFluxOperator<T, T> implements Fuseable {

    /* JADX INFO: renamed from: n */
    final long f2211n;

    FluxTakeFuseable(Flux<? extends T> flux, long j) {
        super(flux);
        if (j < 0) {
            throw new IllegalArgumentException("n >= 0 required but it was " + j);
        }
        this.f2211n = j;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new FluxTake.TakeFuseableSubscriber(coreSubscriber, this.f2211n);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
