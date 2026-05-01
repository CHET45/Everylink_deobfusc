package reactor.core.publisher;

import java.util.Objects;
import org.reactivestreams.Publisher;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.MonoNext;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoFromPublisher<T> extends Mono<T> implements Scannable, OptimizableOperator<T, T> {

    @Nullable
    final OptimizableOperator<?, T> optimizableOperator;
    final Publisher<? extends T> source;

    @Override // reactor.core.publisher.OptimizableOperator
    public final CorePublisher<? extends T> source() {
        return this;
    }

    MonoFromPublisher(Publisher<? extends T> publisher) {
        this.source = (Publisher) Objects.requireNonNull(publisher, "publisher");
        if (publisher instanceof OptimizableOperator) {
            this.optimizableOperator = (OptimizableOperator) publisher;
        } else {
            this.optimizableOperator = null;
        }
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        try {
            CoreSubscriber<? super T> coreSubscriberSubscribeOrReturn = subscribeOrReturn(coreSubscriber);
            if (coreSubscriberSubscribeOrReturn == null) {
                return;
            }
            this.source.subscribe(Operators.restoreContextOnSubscriberIfPublisherNonInternal(this.source, coreSubscriberSubscribeOrReturn));
        } catch (Throwable th) {
            Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) throws Throwable {
        return new MonoNext.NextSubscriber(coreSubscriber);
    }

    @Override // reactor.core.publisher.OptimizableOperator
    public final OptimizableOperator<?, ? extends T> nextOptimizableSource() {
        return this.optimizableOperator;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return attr == InternalProducerAttr.INSTANCE ? true : null;
    }
}
