package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoDoFirst<T> extends InternalMonoOperator<T, T> {
    final Runnable onFirst;

    MonoDoFirst(Mono<? extends T> mono, Runnable runnable) {
        super(mono);
        this.onFirst = runnable;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        this.onFirst.run();
        return coreSubscriber;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
