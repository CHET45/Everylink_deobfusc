package reactor.core.publisher;

import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxNever extends Flux<Object> implements SourceProducer<Object> {
    static final Publisher<Object> INSTANCE = new FluxNever();

    FluxNever() {
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super Object> coreSubscriber) {
        coreSubscriber.onSubscribe(Operators.emptySubscription());
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static <T> Flux<T> instance() {
        return (Flux) INSTANCE;
    }
}
