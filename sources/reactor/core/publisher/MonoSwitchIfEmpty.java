package reactor.core.publisher;

import java.util.Objects;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxSwitchIfEmpty;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSwitchIfEmpty<T> extends InternalMonoOperator<T, T> {
    final Mono<? extends T> other;

    MonoSwitchIfEmpty(Mono<? extends T> mono, Mono<? extends T> mono2) {
        super(mono);
        this.other = fromDirect((Publisher) Objects.requireNonNull(mono2, "other"));
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        FluxSwitchIfEmpty.SwitchIfEmptySubscriber switchIfEmptySubscriber = new FluxSwitchIfEmpty.SwitchIfEmptySubscriber(coreSubscriber, this.other);
        coreSubscriber.onSubscribe(switchIfEmptySubscriber);
        return switchIfEmptySubscriber;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
