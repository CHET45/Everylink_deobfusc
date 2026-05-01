package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.retry.Retry;

/* JADX INFO: loaded from: classes5.dex */
final class MonoRetryWhen<T> extends InternalMonoOperator<T, T> {
    final Retry whenSourceFactory;

    MonoRetryWhen(Mono<? extends T> mono, Retry retry) {
        super(Mono.fromDirect(mono));
        this.whenSourceFactory = (Retry) Objects.requireNonNull(retry, "whenSourceFactory");
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        FluxRetryWhen.subscribe(coreSubscriber, this.whenSourceFactory, this.source);
        return null;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
