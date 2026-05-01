package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoCallable<T> extends Mono<T> implements Callable<T>, Fuseable, SourceProducer<T> {
    final Callable<? extends T> callable;

    MonoCallable(Callable<? extends T> callable) {
        this.callable = (Callable) Objects.requireNonNull(callable, "callable");
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        coreSubscriber.onSubscribe(new MonoCallableSubscription(coreSubscriber, this.callable));
    }

    @Override // reactor.core.publisher.Mono
    @Nullable
    public T block() {
        return block(Duration.ZERO);
    }

    @Override // reactor.core.publisher.Mono
    @Nullable
    public T block(Duration duration) {
        try {
            return this.callable.call();
        } catch (Throwable th) {
            throw Exceptions.propagate(th);
        }
    }

    @Override // java.util.concurrent.Callable
    @Nullable
    public T call() throws Exception {
        return this.callable.call();
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static class MonoCallableSubscription<T> implements InnerProducer<T>, Fuseable, Fuseable.QueueSubscription<T> {
        static final AtomicIntegerFieldUpdater<MonoCallableSubscription> REQUESTED_ONCE = AtomicIntegerFieldUpdater.newUpdater(MonoCallableSubscription.class, "requestedOnce");
        final CoreSubscriber<? super T> actual;
        final Callable<? extends T> callable;
        volatile boolean cancelled;
        boolean done;
        volatile int requestedOnce;

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return i & 1;
        }

        MonoCallableSubscription(CoreSubscriber<? super T> coreSubscriber, Callable<? extends T> callable) {
            this.actual = coreSubscriber;
            this.callable = callable;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // java.util.Queue
        public T poll() {
            if (this.done) {
                return null;
            }
            this.done = true;
            try {
                return this.callable.call();
            } catch (Throwable th) {
                throw Exceptions.propagate(th);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.cancelled || this.requestedOnce == 1 || !REQUESTED_ONCE.compareAndSet(this, 0, 1)) {
                return;
            }
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            try {
                T tCall = this.callable.call();
                if (this.cancelled) {
                    Operators.onDiscard(tCall, coreSubscriber.currentContext());
                    return;
                }
                if (tCall != null) {
                    coreSubscriber.onNext(tCall);
                }
                coreSubscriber.onComplete();
            } catch (Exception e) {
                if (this.cancelled) {
                    Operators.onErrorDropped(e, coreSubscriber.currentContext());
                } else {
                    coreSubscriber.onError(e);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        @Override // java.util.Collection
        public int size() {
            return !this.done ? 1 : 0;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.done;
        }

        @Override // java.util.Collection
        public void clear() {
            this.done = true;
        }
    }
}
