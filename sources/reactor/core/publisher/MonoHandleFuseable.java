package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiConsumer;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxHandleFuseable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoHandleFuseable<T, R> extends InternalMonoOperator<T, R> implements Fuseable {
    final BiConsumer<? super T, SynchronousSink<R>> handler;

    MonoHandleFuseable(Mono<? extends T> mono, BiConsumer<? super T, SynchronousSink<R>> biConsumer) {
        super(mono);
        this.handler = (BiConsumer) Objects.requireNonNull(biConsumer, "handler");
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        BiConsumer<? super T, SynchronousSink<R>> biConsumerContextRestoreForHandle;
        if (ContextPropagationSupport.shouldRestoreThreadLocalsInSomeOperators()) {
            BiConsumer<? super T, SynchronousSink<R>> biConsumer = this.handler;
            Objects.requireNonNull(coreSubscriber);
            biConsumerContextRestoreForHandle = ContextPropagation.contextRestoreForHandle(biConsumer, new FluxHandle$$ExternalSyntheticLambda0(coreSubscriber));
        } else {
            biConsumerContextRestoreForHandle = this.handler;
        }
        return new FluxHandleFuseable.HandleFuseableSubscriber(coreSubscriber, biConsumerContextRestoreForHandle);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
