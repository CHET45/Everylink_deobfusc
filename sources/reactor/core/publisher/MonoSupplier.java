package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSupplier<T> extends Mono<T> implements Callable<T>, Fuseable, SourceProducer<T> {
    final Supplier<? extends T> supplier;

    MonoSupplier(Supplier<? extends T> supplier) {
        this.supplier = (Supplier) Objects.requireNonNull(supplier, "callable");
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        coreSubscriber.onSubscribe(new MonoSupplierSubscription(coreSubscriber, this.supplier));
    }

    @Override // reactor.core.publisher.Mono
    @Nullable
    public T block(Duration duration) {
        return this.supplier.get();
    }

    @Override // reactor.core.publisher.Mono
    @Nullable
    public T block() {
        return block(Duration.ZERO);
    }

    @Override // java.util.concurrent.Callable
    @Nullable
    public T call() throws Exception {
        return this.supplier.get();
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static class MonoSupplierSubscription<T> implements InnerProducer<T>, Fuseable, Fuseable.QueueSubscription<T> {
        static final AtomicIntegerFieldUpdater<MonoSupplierSubscription> REQUESTED_ONCE = AtomicIntegerFieldUpdater.newUpdater(MonoSupplierSubscription.class, "requestedOnce");
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        boolean done;
        volatile int requestedOnce;
        final Supplier<? extends T> supplier;

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return i & 1;
        }

        MonoSupplierSubscription(CoreSubscriber<? super T> coreSubscriber, Supplier<? extends T> supplier) {
            this.actual = coreSubscriber;
            this.supplier = supplier;
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
            return this.supplier.get();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.cancelled || this.requestedOnce == 1 || !REQUESTED_ONCE.compareAndSet(this, 0, 1)) {
                return;
            }
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            try {
                T t = this.supplier.get();
                if (this.cancelled) {
                    Operators.onDiscard(t, coreSubscriber.currentContext());
                    return;
                }
                if (t != null) {
                    coreSubscriber.onNext(t);
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
