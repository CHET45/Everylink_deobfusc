package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSourceFlux<I> extends MonoFromFluxOperator<I, I> {
    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super I> coreSubscriber) {
        return coreSubscriber;
    }

    MonoSourceFlux(Flux<? extends I> flux) {
        super(flux);
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.from(this.source).scanUnsafe(attr);
        }
        return super.scanUnsafe(attr);
    }
}
