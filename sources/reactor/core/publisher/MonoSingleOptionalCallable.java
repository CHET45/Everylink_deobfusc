package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSingleOptionalCallable<T> extends Mono<Optional<T>> implements Callable<Optional<T>>, SourceProducer<Optional<T>> {
    final Callable<? extends T> callable;

    MonoSingleOptionalCallable(Callable<? extends T> callable) {
        this.callable = (Callable) Objects.requireNonNull(callable, "source");
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super Optional<T>> coreSubscriber) {
        Operators.MonoInnerProducerBase monoInnerProducerBase = new Operators.MonoInnerProducerBase(coreSubscriber);
        coreSubscriber.onSubscribe(monoInnerProducerBase);
        if (monoInnerProducerBase.isCancelled()) {
            return;
        }
        try {
            monoInnerProducerBase.complete(Optional.ofNullable(this.callable.call()));
        } catch (Throwable th) {
            coreSubscriber.onError(Operators.onOperatorError(th, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.Mono
    public Optional<T> block() {
        return block(Duration.ZERO);
    }

    @Override // reactor.core.publisher.Mono
    public Optional<T> block(Duration duration) {
        try {
            return Optional.ofNullable(this.callable.call());
        } catch (Throwable th) {
            throw Exceptions.propagate(th);
        }
    }

    @Override // java.util.concurrent.Callable
    public Optional<T> call() throws Exception {
        return Optional.ofNullable(this.callable.call());
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
