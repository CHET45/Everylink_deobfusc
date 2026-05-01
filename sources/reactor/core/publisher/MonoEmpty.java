package reactor.core.publisher;

import java.time.Duration;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoEmpty extends Mono<Object> implements Fuseable.ScalarCallable<Object>, SourceProducer<Object> {
    static final Publisher<Object> INSTANCE = new MonoEmpty();

    @Override // reactor.core.publisher.Mono
    @Nullable
    public Object block() {
        return null;
    }

    @Override // reactor.core.publisher.Mono
    @Nullable
    public Object block(Duration duration) {
        return null;
    }

    @Override // java.util.concurrent.Callable
    @Nullable
    public Object call() throws Exception {
        return null;
    }

    MonoEmpty() {
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super Object> coreSubscriber) {
        Operators.complete(coreSubscriber);
    }

    static <T> Mono<T> instance() {
        return (Mono) INSTANCE;
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
