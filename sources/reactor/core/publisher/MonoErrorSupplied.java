package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoErrorSupplied<T> extends Mono<T> implements Fuseable.ScalarCallable<T>, SourceProducer<T> {
    final Supplier<? extends Throwable> errorSupplier;

    MonoErrorSupplied(Supplier<? extends Throwable> supplier) {
        this.errorSupplier = (Supplier) Objects.requireNonNull(supplier, "errorSupplier");
    }

    @Override // reactor.core.publisher.Mono
    public T block(Duration duration) {
        throw Exceptions.propagate((Throwable) Objects.requireNonNull(this.errorSupplier.get(), "the errorSupplier returned null"));
    }

    @Override // reactor.core.publisher.Mono
    public T block() {
        throw Exceptions.propagate((Throwable) Objects.requireNonNull(this.errorSupplier.get(), "the errorSupplier returned null"));
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Operators.error(coreSubscriber, (Throwable) Objects.requireNonNull(this.errorSupplier.get(), "the errorSupplier returned null"));
    }

    @Override // java.util.concurrent.Callable
    public T call() throws Exception {
        Throwable th = (Throwable) Objects.requireNonNull(this.errorSupplier.get(), "the errorSupplier returned null");
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
