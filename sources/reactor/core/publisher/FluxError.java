package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxError<T> extends Flux<T> implements Fuseable.ScalarCallable, SourceProducer<T> {
    final Throwable error;

    FluxError(Throwable th) {
        this.error = (Throwable) Objects.requireNonNull(th);
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
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
