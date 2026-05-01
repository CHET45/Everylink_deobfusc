package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoRunnable<T> extends Mono<T> implements Callable<Void>, SourceProducer<T> {
    final Runnable run;

    MonoRunnable(Runnable runnable) {
        this.run = (Runnable) Objects.requireNonNull(runnable, "run");
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        MonoRunnableEagerSubscription monoRunnableEagerSubscription = new MonoRunnableEagerSubscription();
        coreSubscriber.onSubscribe(monoRunnableEagerSubscription);
        if (monoRunnableEagerSubscription.isCancelled()) {
            return;
        }
        try {
            this.run.run();
            coreSubscriber.onComplete();
        } catch (Throwable th) {
            coreSubscriber.onError(Operators.onOperatorError(th, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.Mono
    @Nullable
    public T block(Duration duration) {
        this.run.run();
        return null;
    }

    @Override // reactor.core.publisher.Mono
    @Nullable
    public T block() {
        this.run.run();
        return null;
    }

    @Override // java.util.concurrent.Callable
    @Nullable
    public Void call() throws Exception {
        this.run.run();
        return null;
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class MonoRunnableEagerSubscription extends AtomicBoolean implements Subscription {
        @Override // org.reactivestreams.Subscription
        public void request(long j) {
        }

        MonoRunnableEagerSubscription() {
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            set(true);
        }

        public boolean isCancelled() {
            return get();
        }
    }
}
