package reactor.core.publisher;

import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
abstract class InternalFluxOperator<I, O> extends FluxOperator<I, O> implements Scannable, OptimizableOperator<O, I> {

    @Nullable
    final OptimizableOperator<?, I> optimizableOperator;

    @Nullable
    public abstract CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super O> coreSubscriber) throws Throwable;

    /* JADX WARN: Multi-variable type inference failed */
    protected InternalFluxOperator(Flux<? extends I> flux) {
        super(flux);
        if (flux instanceof OptimizableOperator) {
            this.optimizableOperator = (OptimizableOperator) flux;
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

    @Override // reactor.core.publisher.FluxOperator, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PREFETCH) {
            return Integer.valueOf(getPrefetch());
        }
        if (attr == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (attr == InternalProducerAttr.INSTANCE) {
            return true;
        }
        return super.scanUnsafe(attr);
    }
}
