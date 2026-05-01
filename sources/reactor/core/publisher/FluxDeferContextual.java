package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDeferContextual<T> extends Flux<T> implements SourceProducer<T> {
    final Function<ContextView, ? extends Publisher<? extends T>> contextualPublisherFactory;

    FluxDeferContextual(Function<ContextView, ? extends Publisher<? extends T>> function) {
        this.contextualPublisherFactory = (Function) Objects.requireNonNull(function, "contextualPublisherFactory");
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Context contextCurrentContext = coreSubscriber.currentContext();
        try {
            Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.contextualPublisherFactory.apply(contextCurrentContext.readOnly()), "The Publisher returned by the contextualPublisherFactory is null")).subscribe((CoreSubscriber) coreSubscriber);
        } catch (Throwable th) {
            Operators.error(coreSubscriber, Operators.onOperatorError(th, contextCurrentContext));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
