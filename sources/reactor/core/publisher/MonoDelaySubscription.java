package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Consumer;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxDelaySubscription;

/* JADX INFO: loaded from: classes5.dex */
final class MonoDelaySubscription<T, U> extends InternalMonoOperator<T, T> implements Consumer<FluxDelaySubscription.DelaySubscriptionOtherSubscriber<T, U>> {
    final Publisher<U> other;

    MonoDelaySubscription(Mono<? extends T> mono, Publisher<U> publisher) {
        super(mono);
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "other"));
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        this.other.subscribe(new FluxDelaySubscription.DelaySubscriptionOtherSubscriber(coreSubscriber, this));
        return null;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // java.util.function.Consumer
    public void accept(FluxDelaySubscription.DelaySubscriptionOtherSubscriber<T, U> delaySubscriptionOtherSubscriber) {
        this.source.subscribe((CoreSubscriber<? super Object>) new FluxDelaySubscription.DelaySubscriptionMainSubscriber(delaySubscriptionOtherSubscriber.actual, delaySubscriptionOtherSubscriber));
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
