package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxErrorSupplied<T> extends Flux<T> implements Fuseable.ScalarCallable, SourceProducer<T> {
    final Supplier<? extends Throwable> errorSupplier;

    FluxErrorSupplied(Supplier<? extends Throwable> supplier) {
        this.errorSupplier = (Supplier) Objects.requireNonNull(supplier, "errorSupplier");
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Operators.error(coreSubscriber, (Throwable) Objects.requireNonNull(this.errorSupplier.get(), "errorSupplier produced a null Throwable"));
    }

    @Override // java.util.concurrent.Callable
    public Object call() throws Exception {
        Throwable th = (Throwable) Objects.requireNonNull(this.errorSupplier.get(), "errorSupplier produced a null Throwable");
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
