package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxHide;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class GroupedLiftFuseable<K, I, O> extends GroupedFlux<K, O> implements Scannable, Fuseable {
    final Operators.LiftFunction<I, O> liftFunction;
    final GroupedFlux<K, I> source;

    GroupedLiftFuseable(GroupedFlux<K, I> groupedFlux, Operators.LiftFunction<I, O> liftFunction) {
        this.source = (GroupedFlux) Objects.requireNonNull(groupedFlux, "source");
        this.liftFunction = liftFunction;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override // reactor.core.publisher.GroupedFlux
    public K key() {
        return this.source.key();
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
        GroupedFlux<K, I> groupedFlux = this.source;
        if (groupedFlux instanceof Scannable) {
            return Scannable.from(groupedFlux).stepName();
        }
        return super.stepName();
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super O> coreSubscriber) {
        CoreSubscriber<? super I> coreSubscriberApply = this.liftFunction.lifter.apply(this.source, coreSubscriber);
        Objects.requireNonNull(coreSubscriberApply, "Lifted subscriber MUST NOT be null");
        if ((coreSubscriber instanceof Fuseable.QueueSubscription) && !(coreSubscriberApply instanceof Fuseable.QueueSubscription)) {
            coreSubscriberApply = new FluxHide.SuppressFuseableSubscriber(coreSubscriberApply);
        }
        this.source.subscribe((CoreSubscriber) coreSubscriberApply);
    }
}
