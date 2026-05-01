package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxLimitRequest<T> extends InternalFluxOperator<T, T> {
    final long cap;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return 0;
    }

    FluxLimitRequest(Flux<T> flux, long j) {
        super(flux);
        if (j < 0) {
            throw new IllegalArgumentException("cap >= 0 required but it was " + j);
        }
        this.cap = j;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    @Nullable
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (this.cap == 0) {
            Operators.complete(coreSubscriber);
            return null;
        }
        return new FluxLimitRequestSubscriber(coreSubscriber, this.cap);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.cap) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static class FluxLimitRequestSubscriber<T> implements InnerOperator<T, T> {
        static final AtomicLongFieldUpdater<FluxLimitRequestSubscriber> REQUEST_REMAINING = AtomicLongFieldUpdater.newUpdater(FluxLimitRequestSubscriber.class, "requestRemaining");
        final CoreSubscriber<? super T> actual;
        boolean done;
        Subscription parent;
        volatile long requestRemaining;
        long toProduce;

        FluxLimitRequestSubscriber(CoreSubscriber<? super T> coreSubscriber, long j) {
            this.actual = coreSubscriber;
            this.toProduce = j;
            this.requestRemaining = j;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long j = this.toProduce;
            if (j > 0) {
                long j2 = j - 1;
                this.toProduce = j2;
                this.actual.onNext(t);
                if (j2 == 0) {
                    this.done = true;
                    this.parent.cancel();
                    this.actual.onComplete();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, currentContext());
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

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.parent, subscription)) {
                this.parent = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            long j2;
            long j3;
            do {
                j2 = this.requestRemaining;
                j3 = j2 <= j ? j2 : j;
            } while (!REQUEST_REMAINING.compareAndSet(this, j2, j2 - j3));
            if (j3 != 0) {
                this.parent.request(j3);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.parent.cancel();
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.parent : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
