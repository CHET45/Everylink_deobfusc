package reactor.core.publisher;

import java.time.Duration;
import java.util.concurrent.Callable;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxOnAssembly;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoCallableOnAssembly<T> extends InternalMonoOperator<T, T> implements Callable<T>, AssemblyOp {
    final FluxOnAssembly.AssemblySnapshot stacktrace;

    MonoCallableOnAssembly(Mono<? extends T> mono, FluxOnAssembly.AssemblySnapshot assemblySnapshot) {
        super(mono);
        this.stacktrace = assemblySnapshot;
    }

    @Override // reactor.core.publisher.Mono
    @Nullable
    public T block() {
        return block(Duration.ZERO);
    }

    @Override // reactor.core.publisher.Mono
    @Nullable
    public T block(Duration duration) {
        try {
            return (T) ((Callable) this.source).call();
        } catch (Throwable th) {
            throw Exceptions.propagate(th);
        }
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new FluxOnAssembly.OnAssemblyConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.stacktrace, this.source, this);
        }
        return new FluxOnAssembly.OnAssemblySubscriber(coreSubscriber, this.stacktrace, this.source, this);
    }

    @Override // java.util.concurrent.Callable
    @Nullable
    public T call() throws Exception {
        return (T) ((Callable) this.source).call();
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
