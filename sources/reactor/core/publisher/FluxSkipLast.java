package reactor.core.publisher;

import java.util.ArrayDeque;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxSkipLast<T> extends InternalFluxOperator<T, T> {

    /* JADX INFO: renamed from: n */
    final int f2191n;

    FluxSkipLast(Flux<? extends T> flux, int i) {
        super(flux);
        if (i < 0) {
            throw new IllegalArgumentException("n >= 0 required but it was " + i);
        }
        this.f2191n = i;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new SkipLastSubscriber(coreSubscriber, this.f2191n);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SkipLastSubscriber<T> extends ArrayDeque<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;

        /* JADX INFO: renamed from: n */
        final int f2192n;

        /* JADX INFO: renamed from: s */
        Subscription f2193s;

        SkipLastSubscriber(CoreSubscriber<? super T> coreSubscriber, int i) {
            this.actual = coreSubscriber;
            this.f2192n = i;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2193s, subscription)) {
                this.f2193s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(this.f2192n);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (size() == this.f2192n) {
                this.actual.onNext(pollFirst());
            }
            offerLast(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
            Operators.onDiscardQueueWithClear(this, this.actual.currentContext(), null);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
            Operators.onDiscardQueueWithClear(this, this.actual.currentContext(), null);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2193s : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.f2192n) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(size()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2193s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2193s.cancel();
            Operators.onDiscardQueueWithClear(this, this.actual.currentContext(), null);
        }
    }
}
