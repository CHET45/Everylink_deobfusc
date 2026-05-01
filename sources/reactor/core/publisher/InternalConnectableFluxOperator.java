package reactor.core.publisher;

import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
abstract class InternalConnectableFluxOperator<I, O> extends ConnectableFlux<O> implements Scannable, OptimizableOperator<O, I> {

    @Nullable
    final OptimizableOperator<?, I> optimizableOperator;
    final ConnectableFlux<I> source;

    @Nullable
    public abstract CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super O> coreSubscriber) throws Throwable;

    /* JADX WARN: Multi-variable type inference failed */
    public InternalConnectableFluxOperator(ConnectableFlux<I> connectableFlux) {
        this.source = connectableFlux;
        if (connectableFlux instanceof OptimizableOperator) {
            this.optimizableOperator = (OptimizableOperator) connectableFlux;
        } else {
            this.optimizableOperator = null;
        }
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public final void subscribe(CoreSubscriber<? super O> coreSubscriber) {
        OptimizableOperator optimizableOperator = this;
        while (true) {
            try {
                coreSubscriber = optimizableOperator.subscribeOrReturn(coreSubscriber);
                if (coreSubscriber == null) {
                    return;
                }
                OptimizableOperator optimizableOperatorNextOptimizableSource = optimizableOperator.nextOptimizableSource();
                if (optimizableOperatorNextOptimizableSource == null) {
                    CorePublisher corePublisherSource = optimizableOperator.source();
                    coreSubscriber = Operators.restoreContextOnSubscriberIfPublisherNonInternal(corePublisherSource, coreSubscriber);
                    corePublisherSource.subscribe((CoreSubscriber) coreSubscriber);
                    return;
                }
                optimizableOperator = optimizableOperatorNextOptimizableSource;
            } catch (Throwable th) {
                Operators.reportThrowInSubscribe(coreSubscriber, th);
                return;
            }
        }
    }

    @Override // reactor.core.publisher.OptimizableOperator
    public final CorePublisher<? extends I> source() {
        return this.source;
    }

    @Override // reactor.core.publisher.OptimizableOperator
    public final OptimizableOperator<?, ? extends I> nextOptimizableSource() {
        return this.optimizableOperator;
    }

    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.PARENT ? this.source : attr == InternalProducerAttr.INSTANCE ? true : null;
    }
}
