package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.MonoSingle;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSingleMono<T> extends InternalMonoOperator<T, T> {
    MonoSingleMono(Mono<? extends T> mono) {
        super(mono);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new MonoSingle.SingleSubscriber(coreSubscriber, null, false);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
