package reactor.core.publisher;

import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
abstract class InternalMonoOperator<I, O> extends MonoOperator<I, O> implements Scannable, OptimizableOperator<O, I> {

    @Nullable
    final OptimizableOperator<?, I> optimizableOperator;

    @Override // reactor.core.publisher.OptimizableOperator
    @Nullable
    public abstract CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super O> coreSubscriber) throws Throwable;

    /* JADX WARN: Multi-variable type inference failed */
    protected InternalMonoOperator(Mono<? extends I> mono) {
        super(mono);
        if (mono instanceof OptimizableOperator) {
            this.optimizableOperator = (OptimizableOperator) mono;
        } else {
            this.optimizableOperator = null;
        }
    }

    @Override // reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == InternalProducerAttr.INSTANCE) {
            return true;
        }
        return super.scanUnsafe(attr);
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
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
}
