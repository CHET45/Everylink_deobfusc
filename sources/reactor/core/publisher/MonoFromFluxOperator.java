package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
abstract class MonoFromFluxOperator<I, O> extends Mono<O> implements Scannable, OptimizableOperator<O, I> {

    @Nullable
    final OptimizableOperator<?, I> optimizableOperator;
    protected final Flux<? extends I> source;

    @Nullable
    public abstract CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super O> coreSubscriber);

    /* JADX WARN: Multi-variable type inference failed */
    protected MonoFromFluxOperator(Flux<? extends I> flux) {
        this.source = (Flux) Objects.requireNonNull(flux);
        if (flux instanceof OptimizableOperator) {
            this.optimizableOperator = (OptimizableOperator) flux;
        } else {
            this.optimizableOperator = null;
        }
    }

    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PREFETCH) {
            return Integer.MAX_VALUE;
        }
        return attr == Scannable.Attr.PARENT ? this.source : attr == InternalProducerAttr.INSTANCE ? true : null;
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
                    coreSubscriber = Operators.restoreContextOnSubscriberIfPublisherNonInternal(optimizableOperator.source(), coreSubscriber);
                    optimizableOperator.source().subscribe((CoreSubscriber) coreSubscriber);
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
