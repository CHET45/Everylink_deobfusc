package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxDefaultIfEmpty;

/* JADX INFO: loaded from: classes5.dex */
final class MonoDefaultIfEmpty<T> extends InternalMonoOperator<T, T> {
    final T defaultValue;

    MonoDefaultIfEmpty(Mono<? extends T> mono, T t) {
        super(mono);
        this.defaultValue = (T) Objects.requireNonNull(t, "defaultValue");
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new FluxDefaultIfEmpty.DefaultIfEmptySubscriber(coreSubscriber, this.defaultValue);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
