package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxOnAssembly;

/* JADX INFO: loaded from: classes5.dex */
final class MonoOnAssembly<T> extends InternalMonoOperator<T, T> implements Fuseable, AssemblyOp {
    final FluxOnAssembly.AssemblySnapshot stacktrace;

    MonoOnAssembly(Mono<? extends T> mono, FluxOnAssembly.AssemblySnapshot assemblySnapshot) {
        super(mono);
        this.stacktrace = assemblySnapshot;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new FluxOnAssembly.OnAssemblyConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.stacktrace, this.source, this);
        }
        return new FluxOnAssembly.OnAssemblySubscriber(coreSubscriber, this.stacktrace, this.source, this);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.ACTUAL_METADATA ? Boolean.valueOf(!this.stacktrace.isCheckpoint) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    @Override // reactor.core.Scannable
    public String stepName() {
        return this.stacktrace.operatorAssemblyInformation();
    }

    @Override // reactor.core.publisher.Mono
    public String toString() {
        return this.stacktrace.operatorAssemblyInformation();
    }
}
