package reactor.core.publisher;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Callable;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSingleCallable<T> extends Mono<T> implements Callable<T>, SourceProducer<T> {
    final Callable<? extends T> callable;

    @Nullable
    final T defaultValue;

    MonoSingleCallable(Callable<? extends T> callable) {
        this.callable = (Callable) Objects.requireNonNull(callable, "source");
        this.defaultValue = null;
    }

    MonoSingleCallable(Callable<? extends T> callable, T t) {
        this.callable = (Callable) Objects.requireNonNull(callable, "source");
        this.defaultValue = (T) Objects.requireNonNull(t, "defaultValue");
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Operators.MonoInnerProducerBase monoInnerProducerBase = new Operators.MonoInnerProducerBase(coreSubscriber);
        coreSubscriber.onSubscribe(monoInnerProducerBase);
        if (monoInnerProducerBase.isCancelled()) {
            return;
        }
        try {
            T tCall = this.callable.call();
            if (tCall == null && this.defaultValue == null) {
                coreSubscriber.onError(new NoSuchElementException("Source was empty"));
            } else if (tCall == null) {
                monoInnerProducerBase.complete(this.defaultValue);
            } else {
                monoInnerProducerBase.complete(tCall);
            }
        } catch (Throwable th) {
            coreSubscriber.onError(Operators.onOperatorError(th, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.Mono
    public T block() {
        return block(Duration.ZERO);
    }

    @Override // reactor.core.publisher.Mono
    public T block(Duration duration) {
        try {
            T tCall = this.callable.call();
            if (tCall == null && this.defaultValue == null) {
                throw new NoSuchElementException("Source was empty");
            }
            return tCall == null ? this.defaultValue : tCall;
        } catch (Throwable th) {
            throw Exceptions.propagate(th);
        }
    }

    @Override // java.util.concurrent.Callable
    public T call() throws Exception {
        T tCall = this.callable.call();
        if (tCall == null && this.defaultValue == null) {
            throw new NoSuchElementException("Source was empty");
        }
        return tCall == null ? this.defaultValue : tCall;
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
