package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxMap;

/* JADX INFO: loaded from: classes5.dex */
final class MonoMap<T, R> extends InternalMonoOperator<T, R> {
    final Function<? super T, ? extends R> mapper;

    MonoMap(Mono<? extends T> mono, Function<? super T, ? extends R> function) {
        super(mono);
        this.mapper = (Function) Objects.requireNonNull(function, "mapper");
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new FluxMap.MapConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.mapper);
        }
        return new FluxMap.MapSubscriber(coreSubscriber, this.mapper);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
