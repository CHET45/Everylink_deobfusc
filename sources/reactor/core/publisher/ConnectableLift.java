package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ConnectableLift<I, O> extends InternalConnectableFluxOperator<I, O> implements Scannable {
    final Operators.LiftFunction<I, O> liftFunction;

    ConnectableLift(ConnectableFlux<I> connectableFlux, Operators.LiftFunction<I, O> liftFunction) {
        super((ConnectableFlux) Objects.requireNonNull(connectableFlux, "source"));
        this.liftFunction = liftFunction;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override // reactor.core.publisher.ConnectableFlux
    public void connect(Consumer<? super Disposable> consumer) {
        this.source.connect(consumer);
    }

    @Override // reactor.core.publisher.InternalConnectableFluxOperator, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.source.getPrefetch()) : attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.RUN_STYLE ? Scannable.from(this.source).scanUnsafe(attr) : attr == Scannable.Attr.LIFTER ? this.liftFunction.name : super.scanUnsafe(attr);
    }

    @Override // reactor.core.Scannable
    public String stepName() {
        if (this.source instanceof Scannable) {
            return Scannable.from(this.source).stepName();
        }
        return super.stepName();
    }

    @Override // reactor.core.publisher.InternalConnectableFluxOperator, reactor.core.publisher.OptimizableOperator
    public final CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super O> coreSubscriber) {
        CoreSubscriber<? super I> coreSubscriberApply = this.liftFunction.lifter.apply(this.source, coreSubscriber);
        Objects.requireNonNull(coreSubscriberApply, "Lifted subscriber MUST NOT be null");
        return coreSubscriberApply;
    }
}
