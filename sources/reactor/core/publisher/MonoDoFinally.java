package reactor.core.publisher;

import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoDoFinally<T> extends InternalMonoOperator<T, T> {
    final Consumer<SignalType> onFinally;

    MonoDoFinally(Mono<? extends T> mono, Consumer<SignalType> consumer) {
        super(mono);
        this.onFinally = consumer;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return FluxDoFinally.createSubscriber(coreSubscriber, this.onFinally);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
