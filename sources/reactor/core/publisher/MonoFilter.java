package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Predicate;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFilter;

/* JADX INFO: loaded from: classes5.dex */
final class MonoFilter<T> extends InternalMonoOperator<T, T> {
    final Predicate<? super T> predicate;

    MonoFilter(Mono<? extends T> mono, Predicate<? super T> predicate) {
        super(mono);
        this.predicate = (Predicate) Objects.requireNonNull(predicate, "predicate");
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new FluxFilter.FilterConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.predicate);
        }
        return new FluxFilter.FilterSubscriber(coreSubscriber, this.predicate);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
