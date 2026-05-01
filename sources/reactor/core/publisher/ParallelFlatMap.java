package reactor.core.publisher;

import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFlatMap;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelFlatMap<T, R> extends ParallelFlux<R> implements Scannable {
    final boolean delayError;
    final Supplier<? extends Queue<R>> innerQueueSupplier;
    final Supplier<? extends Queue<R>> mainQueueSupplier;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int maxConcurrency;
    final int prefetch;
    final ParallelFlux<T> source;

    ParallelFlatMap(ParallelFlux<T> parallelFlux, Function<? super T, ? extends Publisher<? extends R>> function, boolean z, int i, Supplier<? extends Queue<R>> supplier, int i2, Supplier<? extends Queue<R>> supplier2) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.mapper = function;
        this.delayError = z;
        this.maxConcurrency = i;
        this.mainQueueSupplier = supplier;
        this.prefetch = i2;
        this.innerQueueSupplier = supplier2;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.DELAY_ERROR ? Boolean.valueOf(this.delayError) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super R>[] coreSubscriberArr) {
        if (validate(coreSubscriberArr)) {
            int length = coreSubscriberArr.length;
            CoreSubscriber<? super T>[] coreSubscriberArr2 = new CoreSubscriber[length];
            for (int i = 0; i < length; i++) {
                coreSubscriberArr2[i] = new FluxFlatMap.FlatMapMain(coreSubscriberArr[i], this.mapper, this.delayError, this.maxConcurrency, this.mainQueueSupplier, this.prefetch, this.innerQueueSupplier);
            }
            this.source.subscribe(coreSubscriberArr2);
        }
    }
}
