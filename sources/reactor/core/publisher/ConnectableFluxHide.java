package reactor.core.publisher;

import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ConnectableFluxHide<T> extends InternalConnectableFluxOperator<T, T> implements Scannable {
    @Override // reactor.core.publisher.InternalConnectableFluxOperator, reactor.core.publisher.OptimizableOperator
    public final CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return coreSubscriber;
    }

    ConnectableFluxHide(ConnectableFlux<T> connectableFlux) {
        super(connectableFlux);
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override // reactor.core.publisher.InternalConnectableFluxOperator, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    @Override // reactor.core.publisher.ConnectableFlux
    public void connect(Consumer<? super Disposable> consumer) {
        this.source.connect(consumer);
    }
}
