package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxHide;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelLiftFuseable<I, O> extends ParallelFlux<O> implements Scannable, Fuseable {
    final Operators.LiftFunction<I, O> liftFunction;
    final ParallelFlux<I> source;

    ParallelLiftFuseable(ParallelFlux<I> parallelFlux, Operators.LiftFunction<I, O> liftFunction) {
        this.source = ParallelFlux.from((ParallelFlux) Objects.requireNonNull(parallelFlux, "source"));
        this.liftFunction = liftFunction;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.source.parallelism();
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
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.from(this.source).scanUnsafe(attr);
        }
        if (attr == Scannable.Attr.LIFTER) {
            return this.liftFunction.name;
        }
        return attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.Scannable
    public String stepName() {
        ParallelFlux<I> parallelFlux = this.source;
        if (parallelFlux instanceof Scannable) {
            return Scannable.from(parallelFlux).stepName();
        }
        return super.stepName();
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super O>[] coreSubscriberArr) {
        int iParallelism = parallelism();
        CoreSubscriber<? super I>[] coreSubscriberArr2 = new CoreSubscriber[iParallelism];
        for (int i = 0; i < iParallelism; i++) {
            CoreSubscriber<? super O> coreSubscriber = coreSubscriberArr[i];
            CoreSubscriber<? super I> suppressFuseableSubscriber = (CoreSubscriber) Objects.requireNonNull(this.liftFunction.lifter.apply(this.source, coreSubscriber), "Lifted subscriber MUST NOT be null");
            Objects.requireNonNull(suppressFuseableSubscriber, "Lifted subscriber MUST NOT be null");
            if ((coreSubscriber instanceof Fuseable.QueueSubscription) && !(suppressFuseableSubscriber instanceof Fuseable.QueueSubscription)) {
                suppressFuseableSubscriber = new FluxHide.SuppressFuseableSubscriber(suppressFuseableSubscriber);
            }
            coreSubscriberArr2[i] = suppressFuseableSubscriber;
        }
        this.source.subscribe(coreSubscriberArr2);
    }
}
