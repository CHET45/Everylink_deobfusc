package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxArray;
import reactor.core.publisher.FluxFlatMap;

/* JADX INFO: loaded from: classes5.dex */
final class FluxMerge<T> extends Flux<T> implements SourceProducer<T> {
    final boolean delayError;
    final Supplier<? extends Queue<T>> innerQueueSupplier;
    final Supplier<? extends Queue<T>> mainQueueSupplier;
    final int maxConcurrency;
    final int prefetch;
    final Publisher<? extends T>[] sources;

    FluxMerge(Publisher<? extends T>[] publisherArr, boolean z, int i, Supplier<? extends Queue<T>> supplier, int i2, Supplier<? extends Queue<T>> supplier2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i2);
        }
        if (i <= 0) {
            throw new IllegalArgumentException("maxConcurrency > 0 required but it was " + i);
        }
        Publisher<? extends T>[] publisherArr2 = (Publisher[]) Objects.requireNonNull(publisherArr, "sources");
        this.sources = publisherArr2;
        Operators.toFluxOrMono(publisherArr2);
        this.delayError = z;
        this.maxConcurrency = i;
        this.prefetch = i2;
        this.mainQueueSupplier = (Supplier) Objects.requireNonNull(supplier, "mainQueueSupplier");
        this.innerQueueSupplier = (Supplier) Objects.requireNonNull(supplier2, "innerQueueSupplier");
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        FluxFlatMap.FlatMapMain flatMapMain = new FluxFlatMap.FlatMapMain(coreSubscriber, identityFunction(), this.delayError, this.maxConcurrency, this.mainQueueSupplier, this.prefetch, this.innerQueueSupplier);
        flatMapMain.onSubscribe(new FluxArray.ArraySubscription(flatMapMain, this.sources));
    }

    FluxMerge<T> mergeAdditionalSource(Publisher<? extends T> publisher, IntFunction<Supplier<? extends Queue<T>>> intFunction) {
        Supplier<? extends Queue<T>> supplierApply;
        Publisher<? extends T>[] publisherArr = this.sources;
        int length = publisherArr.length;
        Publisher[] publisherArr2 = new Publisher[length + 1];
        System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
        publisherArr2[length] = publisher;
        int i = this.maxConcurrency;
        if (i != Integer.MAX_VALUE) {
            i++;
            supplierApply = intFunction.apply(i);
        } else {
            supplierApply = this.mainQueueSupplier;
        }
        return new FluxMerge<>(publisherArr2, this.delayError, i, supplierApply, this.prefetch, this.innerQueueSupplier);
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.DELAY_ERROR ? Boolean.valueOf(this.delayError) : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.prefetch) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
