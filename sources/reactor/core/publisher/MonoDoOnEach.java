package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoDoOnEach<T> extends InternalMonoOperator<T, T> {
    final Consumer<? super Signal<T>> onSignal;

    MonoDoOnEach(Mono<? extends T> mono, Consumer<? super Signal<T>> consumer) {
        super(mono);
        this.onSignal = (Consumer) Objects.requireNonNull(consumer, "onSignal");
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return FluxDoOnEach.createSubscriber(coreSubscriber, this.onSignal, false, true);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
