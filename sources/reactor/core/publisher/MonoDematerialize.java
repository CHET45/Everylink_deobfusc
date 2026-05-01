package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxDematerialize;

/* JADX INFO: loaded from: classes5.dex */
final class MonoDematerialize<T> extends InternalMonoOperator<Signal<T>, T> {
    MonoDematerialize(Mono<Signal<T>> mono) {
        super(mono);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super Signal<T>> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new FluxDematerialize.DematerializeSubscriber(coreSubscriber, true);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
