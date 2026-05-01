package reactor.core.publisher;

import java.util.concurrent.Callable;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxOnAssembly;

/* JADX INFO: loaded from: classes5.dex */
final class FluxCallableOnAssembly<T> extends InternalFluxOperator<T, T> implements Fuseable, Callable<T>, AssemblyOp {
    final FluxOnAssembly.AssemblySnapshot stacktrace;

    FluxCallableOnAssembly(Flux<? extends T> flux, FluxOnAssembly.AssemblySnapshot assemblySnapshot) {
        super(flux);
        this.stacktrace = assemblySnapshot;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return FluxOnAssembly.wrapSubscriber(coreSubscriber, this.source, this, this.stacktrace);
    }

    @Override // java.util.concurrent.Callable
    public T call() throws Exception {
        return (T) ((Callable) this.source).call();
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.ACTUAL_METADATA ? Boolean.valueOf(!this.stacktrace.isCheckpoint) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
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
