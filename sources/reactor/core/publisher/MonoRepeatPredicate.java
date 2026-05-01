package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxRepeatPredicate;

/* JADX INFO: loaded from: classes5.dex */
final class MonoRepeatPredicate<T> extends FluxFromMonoOperator<T, T> {
    final BooleanSupplier predicate;

    MonoRepeatPredicate(Mono<? extends T> mono, BooleanSupplier booleanSupplier) {
        super(mono);
        this.predicate = (BooleanSupplier) Objects.requireNonNull(booleanSupplier, "predicate");
    }

    @Override // reactor.core.publisher.FluxFromMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        FluxRepeatPredicate.RepeatPredicateSubscriber repeatPredicateSubscriber = new FluxRepeatPredicate.RepeatPredicateSubscriber(this.source, coreSubscriber, this.predicate);
        coreSubscriber.onSubscribe(repeatPredicateSubscriber);
        if (repeatPredicateSubscriber.isCancelled()) {
            return null;
        }
        repeatPredicateSubscriber.resubscribe();
        return null;
    }

    @Override // reactor.core.publisher.FluxFromMonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
