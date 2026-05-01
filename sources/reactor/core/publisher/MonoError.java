package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoError<T> extends Mono<T> implements Fuseable.ScalarCallable, SourceProducer<T> {
    final Throwable error;

    MonoError(Throwable th) {
        this.error = (Throwable) Objects.requireNonNull(th, "error");
    }

    @Override // reactor.core.publisher.Mono
    public T block(Duration duration) {
        throw Exceptions.propagate(this.error);
    }

    @Override // reactor.core.publisher.Mono
    public T block() {
        throw Exceptions.propagate(this.error);
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Operators.error(coreSubscriber, this.error);
    }

    @Override // java.util.concurrent.Callable
    public Object call() throws Exception {
        Throwable th = this.error;
        if (th instanceof Exception) {
            throw ((Exception) th);
        }
        throw Exceptions.propagate(th);
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
