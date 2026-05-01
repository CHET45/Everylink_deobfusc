package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxAutoConnect<T> extends Flux<T> implements Scannable {
    static final AtomicIntegerFieldUpdater<FluxAutoConnect> REMAINING = AtomicIntegerFieldUpdater.newUpdater(FluxAutoConnect.class, "remaining");
    final Consumer<? super Disposable> cancelSupport;
    volatile int remaining;
    final ConnectableFlux<? extends T> source;

    FluxAutoConnect(ConnectableFlux<? extends T> connectableFlux, int i, Consumer<? super Disposable> consumer) {
        if (i <= 0) {
            throw new IllegalArgumentException("n > required but it was " + i);
        }
        this.source = ConnectableFlux.from((ConnectableFlux) Objects.requireNonNull(connectableFlux, "source"));
        this.cancelSupport = (Consumer) Objects.requireNonNull(consumer, "cancelSupport");
        REMAINING.lazySet(this, i);
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        this.source.subscribe((CoreSubscriber<? super Object>) coreSubscriber);
        if (this.remaining <= 0 || REMAINING.decrementAndGet(this) != 0) {
            return;
        }
        this.source.connect(this.cancelSupport);
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.CAPACITY ? Integer.valueOf(this.remaining) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }
}
