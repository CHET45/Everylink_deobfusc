package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxSourceMono<I> extends FluxFromMonoOperator<I, I> {
    @Override // reactor.core.publisher.FluxFromMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super I> coreSubscriber) {
        return coreSubscriber;
    }

    FluxSourceMono(Mono<? extends I> mono) {
        super(mono);
    }

    @Override // reactor.core.Scannable
    public String stepName() {
        if (this.source instanceof Scannable) {
            return "FluxFromMono(" + Scannable.from(this.source).stepName() + ")";
        }
        return "FluxFromMono(" + this.source.toString() + ")";
    }

    @Override // reactor.core.publisher.FluxFromMonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.from(this.source).scanUnsafe(attr) : super.scanUnsafe(attr);
    }
}
