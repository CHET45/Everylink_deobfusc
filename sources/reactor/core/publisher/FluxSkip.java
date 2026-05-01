package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxSkip<T> extends InternalFluxOperator<T, T> {

    /* JADX INFO: renamed from: n */
    final long f2189n;

    FluxSkip(Flux<? extends T> flux, long j) {
        super(flux);
        if (j < 0) {
            throw new IllegalArgumentException("n >= 0 required but it was " + j);
        }
        this.f2189n = j;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new SkipSubscriber(coreSubscriber, this.f2189n);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SkipSubscriber<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        long remaining;

        /* JADX INFO: renamed from: s */
        Subscription f2190s;

        SkipSubscriber(CoreSubscriber<? super T> coreSubscriber, long j) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.remaining = j;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2190s, subscription)) {
                this.f2190s = subscription;
                long j = this.remaining;
                this.actual.onSubscribe(this);
                subscription.request(j);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long j = this.remaining;
            if (j == 0) {
                this.actual.onNext(t);
            } else {
                Operators.onDiscard(t, this.ctx);
                this.remaining = j - 1;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2190s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2190s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2190s.cancel();
        }
    }
}
