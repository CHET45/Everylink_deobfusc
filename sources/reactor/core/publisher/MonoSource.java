package reactor.core.publisher;

import java.util.Objects;
import org.reactivestreams.Publisher;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSource<I> extends Mono<I> implements Scannable, SourceProducer<I>, OptimizableOperator<I, I> {

    @Nullable
    final OptimizableOperator<?, I> optimizableOperator;
    final Publisher<? extends I> source;

    @Override // reactor.core.publisher.OptimizableOperator
    public final CorePublisher<? extends I> source() {
        return this;
    }

    @Override // reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super I> coreSubscriber) {
        return coreSubscriber;
    }

    MonoSource(Publisher<? extends I> publisher) {
        this.source = (Publisher) Objects.requireNonNull(publisher);
        if (publisher instanceof OptimizableOperator) {
            this.optimizableOperator = (OptimizableOperator) publisher;
        } else {
            this.optimizableOperator = null;
        }
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super I> coreSubscriber) {
        this.source.subscribe(coreSubscriber);
    }

    @Override // reactor.core.publisher.OptimizableOperator
    public final OptimizableOperator<?, ? extends I> nextOptimizableSource() {
        return this.optimizableOperator;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.from(this.source).scanUnsafe(attr);
        }
        return super.scanUnsafe(attr);
    }
}
