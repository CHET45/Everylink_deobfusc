package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDefer<T> extends Flux<T> implements SourceProducer<T> {
    final Supplier<? extends Publisher<? extends T>> supplier;

    FluxDefer(Supplier<? extends Publisher<? extends T>> supplier) {
        this.supplier = (Supplier) Objects.requireNonNull(supplier, "supplier");
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        try {
            from((Publisher) Objects.requireNonNull(this.supplier.get(), "The Publisher returned by the supplier is null")).subscribe((CoreSubscriber) coreSubscriber);
        } catch (Throwable th) {
            Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
