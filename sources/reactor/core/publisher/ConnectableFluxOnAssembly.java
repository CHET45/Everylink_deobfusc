package reactor.core.publisher;

import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxOnAssembly;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ConnectableFluxOnAssembly<T> extends InternalConnectableFluxOperator<T, T> implements Fuseable, AssemblyOp, Scannable {
    final FluxOnAssembly.AssemblySnapshot stacktrace;

    ConnectableFluxOnAssembly(ConnectableFlux<T> connectableFlux, FluxOnAssembly.AssemblySnapshot assemblySnapshot) {
        super(connectableFlux);
        this.stacktrace = assemblySnapshot;
    }

    @Override // reactor.core.publisher.InternalConnectableFluxOperator, reactor.core.publisher.OptimizableOperator
    public final CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return FluxOnAssembly.wrapSubscriber(coreSubscriber, this.source, this, this.stacktrace);
    }

    @Override // reactor.core.publisher.ConnectableFlux
    public void connect(Consumer<? super Disposable> consumer) {
        this.source.connect(consumer);
    }

    @Override // reactor.core.publisher.InternalConnectableFluxOperator, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.ACTUAL_METADATA ? Boolean.valueOf(!this.stacktrace.isCheckpoint) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    @Override // reactor.core.Scannable
    public String stepName() {
        return this.stacktrace.operatorAssemblyInformation();
    }

    @Override // reactor.core.publisher.Flux
    public String toString() {
        return this.stacktrace.operatorAssemblyInformation();
    }
}
