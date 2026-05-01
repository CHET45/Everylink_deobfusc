package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxOnAssembly;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelFluxOnAssembly<T> extends ParallelFlux<T> implements Fuseable, AssemblyOp, Scannable {
    final ParallelFlux<T> source;
    final FluxOnAssembly.AssemblySnapshot stacktrace;

    ParallelFluxOnAssembly(ParallelFlux<T> parallelFlux, FluxOnAssembly.AssemblySnapshot assemblySnapshot) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.stacktrace = assemblySnapshot;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super T>[] coreSubscriberArr) {
        CoreSubscriber<? super T> onAssemblySubscriber;
        if (validate(coreSubscriberArr)) {
            int length = coreSubscriberArr.length;
            CoreSubscriber<? super T>[] coreSubscriberArr2 = new CoreSubscriber[length];
            for (int i = 0; i < length; i++) {
                CoreSubscriber<? super T> coreSubscriber = coreSubscriberArr[i];
                if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
                    onAssemblySubscriber = new FluxOnAssembly.OnAssemblyConditionalSubscriber<>((Fuseable.ConditionalSubscriber) coreSubscriber, this.stacktrace, this.source, this);
                } else {
                    onAssemblySubscriber = new FluxOnAssembly.OnAssemblySubscriber<>(coreSubscriber, this.stacktrace, this.source, this);
                }
                coreSubscriberArr2[i] = onAssemblySubscriber;
            }
            this.source.subscribe(coreSubscriberArr2);
        }
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.ACTUAL_METADATA ? Boolean.valueOf(!this.stacktrace.isCheckpoint) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.Scannable
    public String stepName() {
        return this.stacktrace.operatorAssemblyInformation();
    }

    @Override // reactor.core.publisher.ParallelFlux
    public String toString() {
        return this.stacktrace.operatorAssemblyInformation();
    }
}
