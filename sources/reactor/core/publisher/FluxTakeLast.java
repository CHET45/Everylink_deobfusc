package reactor.core.publisher;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.BooleanSupplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxTakeLast<T> extends InternalFluxOperator<T, T> {

    /* JADX INFO: renamed from: n */
    final int f2212n;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxTakeLast(Flux<? extends T> flux, int i) {
        super(flux);
        if (i < 0) {
            throw new IllegalArgumentException("n >= required but it was " + i);
        }
        this.f2212n = i;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (this.f2212n == 0) {
            return new TakeLastZeroSubscriber(coreSubscriber);
        }
        return new TakeLastManySubscriber(coreSubscriber, this.f2212n);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class TakeLastZeroSubscriber<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;

        /* JADX INFO: renamed from: s */
        Subscription f2215s;

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
        }

        TakeLastZeroSubscriber(CoreSubscriber<? super T> coreSubscriber) {
            this.actual = coreSubscriber;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2215s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2215s, subscription)) {
                this.f2215s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
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

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2215s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2215s.cancel();
        }
    }

    static final class TakeLastManySubscriber<T> extends ArrayDeque<T> implements BooleanSupplier, InnerOperator<T, T> {
        static final AtomicLongFieldUpdater<TakeLastManySubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(TakeLastManySubscriber.class, "requested");
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;

        /* JADX INFO: renamed from: n */
        final int f2213n;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2214s;

        TakeLastManySubscriber(CoreSubscriber<? super T> coreSubscriber, int i) {
            this.actual = coreSubscriber;
            this.f2213n = i;
        }

        @Override // java.util.function.BooleanSupplier
        public boolean getAsBoolean() {
            return this.cancelled;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                DrainUtils.postCompleteRequest(j, this.actual, this, REQUESTED, this, this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.f2214s.cancel();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2214s, subscription)) {
                this.f2214s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (size() == this.f2213n) {
                poll();
            }
            offer(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            DrainUtils.postComplete(this.actual, this, REQUESTED, this, this);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.PARENT ? this.f2214s : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(size()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }
}
