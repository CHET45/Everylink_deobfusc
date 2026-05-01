package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDoFirstFuseable<T> extends InternalFluxOperator<T, T> implements Fuseable {
    final Runnable onFirst;

    FluxDoFirstFuseable(Flux<? extends T> flux, Runnable runnable) {
        super(flux);
        this.onFirst = runnable;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        this.onFirst.run();
        return coreSubscriber;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
