package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxDetach;

/* JADX INFO: loaded from: classes5.dex */
final class MonoDetach<T> extends InternalMonoOperator<T, T> {
    MonoDetach(Mono<? extends T> mono) {
        super(mono);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new FluxDetach.DetachSubscriber(coreSubscriber);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
