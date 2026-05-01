package reactor.core.publisher;

import java.util.Objects;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;

/* JADX INFO: loaded from: classes5.dex */
final class FluxSwitchIfEmpty<T> extends InternalFluxOperator<T, T> {
    final Publisher<? extends T> other;

    FluxSwitchIfEmpty(Flux<? extends T> flux, Publisher<? extends T> publisher) {
        super(flux);
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "other"));
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        SwitchIfEmptySubscriber switchIfEmptySubscriber = new SwitchIfEmptySubscriber(coreSubscriber, this.other);
        coreSubscriber.onSubscribe(switchIfEmptySubscriber);
        return switchIfEmptySubscriber;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SwitchIfEmptySubscriber<T> extends Operators.MultiSubscriptionSubscriber<T, T> {
        boolean once;
        final Publisher<? extends T> other;

        SwitchIfEmptySubscriber(CoreSubscriber<? super T> coreSubscriber, Publisher<? extends T> publisher) {
            super(coreSubscriber);
            this.other = publisher;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.once) {
                this.once = true;
            }
            this.actual.onNext((Object) t);
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.once) {
                this.once = true;
                this.other.subscribe(this);
            } else {
                this.actual.onComplete();
            }
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
