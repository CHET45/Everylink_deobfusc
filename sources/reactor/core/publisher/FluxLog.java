package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPeek;
import reactor.core.publisher.FluxPeekFuseable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxLog<T> extends InternalFluxOperator<T, T> {
    final SignalPeek<T> log;

    FluxLog(Flux<? extends T> flux, SignalPeek<T> signalPeek) {
        super(flux);
        this.log = signalPeek;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new FluxPeekFuseable.PeekConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.log);
        }
        return new FluxPeek.PeekSubscriber(coreSubscriber, this.log);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
