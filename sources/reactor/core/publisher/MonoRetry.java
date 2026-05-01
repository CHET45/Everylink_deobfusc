package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxRetry;

/* JADX INFO: loaded from: classes5.dex */
final class MonoRetry<T> extends InternalMonoOperator<T, T> {
    final long times;

    MonoRetry(Mono<? extends T> mono, long j) {
        super(mono);
        if (j < 0) {
            throw new IllegalArgumentException("times >= 0 required");
        }
        this.times = j;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        FluxRetry.RetrySubscriber retrySubscriber = new FluxRetry.RetrySubscriber(this.source, coreSubscriber, this.times);
        coreSubscriber.onSubscribe(retrySubscriber);
        if (retrySubscriber.isCancelled()) {
            return null;
        }
        retrySubscriber.resubscribe();
        return null;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
