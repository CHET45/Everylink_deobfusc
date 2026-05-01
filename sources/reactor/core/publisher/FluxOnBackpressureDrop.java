package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxOnBackpressureDrop<T> extends InternalFluxOperator<T, T> {
    static final Consumer<Object> NOOP = new Consumer() { // from class: reactor.core.publisher.FluxOnBackpressureDrop$$ExternalSyntheticLambda0
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            FluxOnBackpressureDrop.lambda$static$0(obj);
        }
    };
    final Consumer<? super T> onDrop;

    static /* synthetic */ void lambda$static$0(Object obj) {
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxOnBackpressureDrop(Flux<? extends T> flux) {
        super(flux);
        this.onDrop = NOOP;
    }

    FluxOnBackpressureDrop(Flux<? extends T> flux, Consumer<? super T> consumer) {
        super(flux);
        this.onDrop = (Consumer) Objects.requireNonNull(consumer, "onDrop");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new DropSubscriber(coreSubscriber, this.onDrop);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class DropSubscriber<T> implements InnerOperator<T, T> {
        static final AtomicLongFieldUpdater<DropSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(DropSubscriber.class, "requested");
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        boolean done;
        final Consumer<? super T> onDrop;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2162s;

        DropSubscriber(CoreSubscriber<? super T> coreSubscriber, Consumer<? super T> consumer) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.onDrop = consumer;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2162s.cancel();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2162s, subscription)) {
                this.f2162s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                try {
                    this.onDrop.accept(t);
                } catch (Throwable th) {
                    Operators.onErrorDropped(th, this.ctx);
                }
                Operators.onDiscard(t, this.ctx);
                return;
            }
            long j = this.requested;
            if (j != 0) {
                this.actual.onNext(t);
                if (j != Long.MAX_VALUE) {
                    Operators.produced(REQUESTED, this, 1L);
                    return;
                }
                return;
            }
            try {
                this.onDrop.accept(t);
            } catch (Throwable th2) {
                onError(Operators.onOperatorError(this.f2162s, th2, t, this.ctx));
            }
            Operators.onDiscard(t, this.ctx);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                this.done = true;
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2162s;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
