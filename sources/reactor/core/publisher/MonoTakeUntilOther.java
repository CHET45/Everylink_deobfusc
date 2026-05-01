package reactor.core.publisher;

import java.util.Objects;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxTakeUntilOther;

/* JADX INFO: loaded from: classes5.dex */
final class MonoTakeUntilOther<T, U> extends InternalMonoOperator<T, T> {
    private final Publisher<U> other;

    MonoTakeUntilOther(Mono<? extends T> mono, Publisher<U> publisher) {
        super(mono);
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "other"));
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        FluxTakeUntilOther.TakeUntilMainSubscriber takeUntilMainSubscriber = new FluxTakeUntilOther.TakeUntilMainSubscriber(coreSubscriber);
        this.other.subscribe(new FluxTakeUntilOther.TakeUntilOtherSubscriber(takeUntilMainSubscriber));
        return takeUntilMainSubscriber;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
