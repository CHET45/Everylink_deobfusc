package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxContextWrite;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoContextWrite<T> extends InternalMonoOperator<T, T> implements Fuseable {
    final Function<Context, Context> doOnContext;

    MonoContextWrite(Mono<? extends T> mono, Function<Context, Context> function) {
        super(mono);
        this.doOnContext = (Function) Objects.requireNonNull(function, "doOnContext");
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new FluxContextWrite.ContextWriteSubscriber(coreSubscriber, this.doOnContext.apply(coreSubscriber.currentContext()));
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
