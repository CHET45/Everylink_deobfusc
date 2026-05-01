package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxDistinct;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDistinctFuseable<T, K, C> extends InternalFluxOperator<T, T> implements Fuseable {
    final Consumer<C> cleanupCallback;
    final Supplier<C> collectionSupplier;
    final BiPredicate<C, K> distinctPredicate;
    final Function<? super T, ? extends K> keyExtractor;

    FluxDistinctFuseable(Flux<? extends T> flux, Function<? super T, ? extends K> function, Supplier<C> supplier, BiPredicate<C, K> biPredicate, Consumer<C> consumer) {
        super(flux);
        this.keyExtractor = (Function) Objects.requireNonNull(function, "keyExtractor");
        this.collectionSupplier = (Supplier) Objects.requireNonNull(supplier, "collectionSupplier");
        this.distinctPredicate = (BiPredicate) Objects.requireNonNull(biPredicate, "distinctPredicate");
        this.cleanupCallback = (Consumer) Objects.requireNonNull(consumer, "cleanupCallback");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new FluxDistinct.DistinctFuseableSubscriber(coreSubscriber, Objects.requireNonNull(this.collectionSupplier.get(), "The collectionSupplier returned a null collection"), this.keyExtractor, this.distinctPredicate, this.cleanupCallback);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
