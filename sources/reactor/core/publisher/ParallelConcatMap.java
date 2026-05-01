package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxConcatMap;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelConcatMap<T, R> extends ParallelFlux<R> implements Scannable {
    final FluxConcatMap.ErrorMode errorMode;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int prefetch;
    final Supplier<? extends Queue<T>> queueSupplier;
    final ParallelFlux<T> source;

    ParallelConcatMap(ParallelFlux<T> parallelFlux, Function<? super T, ? extends Publisher<? extends R>> function, Supplier<? extends Queue<T>> supplier, int i, FluxConcatMap.ErrorMode errorMode) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.mapper = (Function) Objects.requireNonNull(function, "mapper");
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
        this.prefetch = i;
        this.errorMode = (FluxConcatMap.ErrorMode) Objects.requireNonNull(errorMode, "errorMode");
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (attr == Scannable.Attr.PREFETCH) {
            return Integer.valueOf(getPrefetch());
        }
        if (attr == Scannable.Attr.DELAY_ERROR) {
            return Boolean.valueOf(this.errorMode != FluxConcatMap.ErrorMode.IMMEDIATE);
        }
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
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
                coreSubscriberArr2[i] = FluxConcatMap.subscriber(coreSubscriberArr[i], this.mapper, this.queueSupplier, this.prefetch, this.errorMode);
            }
            this.source.subscribe(coreSubscriberArr2);
        }
    }
}
