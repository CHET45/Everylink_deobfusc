package reactor.core.publisher;

import java.util.function.Function;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxExpand;

/* JADX INFO: loaded from: classes5.dex */
final class MonoExpand<T> extends FluxFromMonoOperator<T, T> {
    final boolean breadthFirst;
    final int capacityHint;
    final Function<? super T, ? extends Publisher<? extends T>> expander;

    MonoExpand(Mono<T> mono, Function<? super T, ? extends Publisher<? extends T>> function, boolean z, int i) {
        super(mono);
        this.expander = function;
        this.breadthFirst = z;
        this.capacityHint = i;
    }

    @Override // reactor.core.publisher.FluxFromMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (this.breadthFirst) {
            FluxExpand.ExpandBreathSubscriber expandBreathSubscriber = new FluxExpand.ExpandBreathSubscriber(coreSubscriber, this.expander, this.capacityHint);
            expandBreathSubscriber.queue.offer(this.source);
            coreSubscriber.onSubscribe(expandBreathSubscriber);
            expandBreathSubscriber.drainQueue();
            return null;
        }
        FluxExpand.ExpandDepthSubscription expandDepthSubscription = new FluxExpand.ExpandDepthSubscription(coreSubscriber, this.expander, this.capacityHint);
        expandDepthSubscription.source = this.source;
        coreSubscriber.onSubscribe(expandDepthSubscription);
        return null;
    }

    @Override // reactor.core.publisher.FluxFromMonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
