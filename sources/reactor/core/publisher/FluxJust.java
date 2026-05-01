package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxJust<T> extends Flux<T> implements Fuseable.ScalarCallable<T>, Fuseable, SourceProducer<T> {
    final T value;

    FluxJust(T t) {
        this.value = (T) Objects.requireNonNull(t, "value");
    }

    @Override // java.util.concurrent.Callable
    public T call() throws Exception {
        return this.value;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        coreSubscriber.onSubscribe(Operators.scalarSubscription(coreSubscriber, this.value, "just"));
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.BUFFERED) {
            return 1;
        }
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
