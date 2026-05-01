package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxTake<T> extends InternalFluxOperator<T, T> {

    /* JADX INFO: renamed from: n */
    final long f2204n;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxTake(Flux<? extends T> flux, long j) {
        super(flux);
        if (j < 0) {
            throw new IllegalArgumentException("n >= 0 required but it was " + j);
        }
        this.f2204n = j;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new TakeConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.f2204n);
        }
        return new TakeSubscriber(coreSubscriber, this.f2204n);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class TakeSubscriber<T> implements InnerOperator<T, T> {
        static final AtomicIntegerFieldUpdater<TakeSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(TakeSubscriber.class, "wip");
        final CoreSubscriber<? super T> actual;
        boolean done;

        /* JADX INFO: renamed from: n */
        final long f2209n;
        long remaining;

        /* JADX INFO: renamed from: s */
        Subscription f2210s;
        volatile int wip;

        public TakeSubscriber(CoreSubscriber<? super T> coreSubscriber, long j) {
            this.actual = coreSubscriber;
            this.f2209n = j;
            this.remaining = j;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2210s, subscription)) {
                if (this.f2209n == 0) {
                    subscription.cancel();
                    this.done = true;
                    Operators.complete(this.actual);
                } else {
                    this.f2210s = subscription;
                    this.actual.onSubscribe(this);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long j = this.remaining;
            if (j == 0) {
                this.f2210s.cancel();
                onComplete();
                return;
            }
            long j2 = j - 1;
            this.remaining = j2;
            boolean z = j2 == 0;
            this.actual.onNext(t);
            if (z) {
                this.f2210s.cancel();
                onComplete();
            }
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

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.wip != 0 || !WIP.compareAndSet(this, 0, 1)) {
                this.f2210s.request(j);
            } else if (j >= this.f2209n) {
                this.f2210s.request(Long.MAX_VALUE);
            } else {
                this.f2210s.request(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2210s.cancel();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.PARENT ? this.f2210s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }

    static final class TakeConditionalSubscriber<T> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T> {
        static final AtomicIntegerFieldUpdater<TakeConditionalSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(TakeConditionalSubscriber.class, "wip");
        final Fuseable.ConditionalSubscriber<? super T> actual;
        boolean done;

        /* JADX INFO: renamed from: n */
        final long f2205n;
        long remaining;

        /* JADX INFO: renamed from: s */
        Subscription f2206s;
        volatile int wip;

        TakeConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, long j) {
            this.actual = conditionalSubscriber;
            this.f2205n = j;
            this.remaining = j;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2206s, subscription)) {
                if (this.f2205n == 0) {
                    subscription.cancel();
                    this.done = true;
                    Operators.complete(this.actual);
                } else {
                    this.f2206s = subscription;
                    this.actual.onSubscribe(this);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long j = this.remaining;
            if (j == 0) {
                this.f2206s.cancel();
                onComplete();
                return;
            }
            long j2 = j - 1;
            this.remaining = j2;
            boolean z = j2 == 0;
            this.actual.onNext(t);
            if (z) {
                this.f2206s.cancel();
                onComplete();
            }
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return true;
            }
            long j = this.remaining;
            if (j == 0) {
                this.f2206s.cancel();
                onComplete();
                return true;
            }
            long j2 = j - 1;
            this.remaining = j2;
            boolean z = j2 == 0;
            boolean zTryOnNext = this.actual.tryOnNext(t);
            if (z) {
                this.f2206s.cancel();
                onComplete();
            }
            return zTryOnNext;
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

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.wip != 0 || !WIP.compareAndSet(this, 0, 1)) {
                this.f2206s.request(j);
            } else if (j >= this.f2205n) {
                this.f2206s.request(Long.MAX_VALUE);
            } else {
                this.f2206s.request(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2206s.cancel();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.PARENT ? this.f2206s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }

    static final class TakeFuseableSubscriber<T> implements Fuseable.QueueSubscription<T>, InnerOperator<T, T> {
        static final AtomicIntegerFieldUpdater<TakeFuseableSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(TakeFuseableSubscriber.class, "wip");
        final CoreSubscriber<? super T> actual;
        boolean done;
        int inputMode;

        /* JADX INFO: renamed from: n */
        final long f2207n;

        /* JADX INFO: renamed from: qs */
        Fuseable.QueueSubscription<T> f2208qs;
        long remaining;
        volatile int wip;

        TakeFuseableSubscriber(CoreSubscriber<? super T> coreSubscriber, long j) {
            this.actual = coreSubscriber;
            this.f2207n = j;
            this.remaining = j;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2208qs, subscription)) {
                if (this.f2207n == 0) {
                    subscription.cancel();
                    this.done = true;
                    Operators.complete(this.actual);
                } else {
                    this.f2208qs = (Fuseable.QueueSubscription) subscription;
                    this.actual.onSubscribe(this);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.inputMode == 2) {
                this.actual.onNext(null);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long j = this.remaining;
            if (j == 0) {
                this.f2208qs.cancel();
                onComplete();
                return;
            }
            long j2 = j - 1;
            this.remaining = j2;
            boolean z = j2 == 0;
            this.actual.onNext(t);
            if (z) {
                this.f2208qs.cancel();
                onComplete();
            }
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

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.wip != 0 || !WIP.compareAndSet(this, 0, 1)) {
                this.f2208qs.request(j);
            } else if (j >= this.f2207n) {
                this.f2208qs.request(Long.MAX_VALUE);
            } else {
                this.f2208qs.request(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2208qs.cancel();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.PARENT ? this.f2208qs : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            int iRequestFusion = this.f2208qs.requestFusion(i);
            this.inputMode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            if (this.done) {
                return null;
            }
            long j = this.remaining;
            T tPoll = this.f2208qs.poll();
            if (j == 0) {
                this.done = true;
                if (this.inputMode == 2) {
                    this.f2208qs.cancel();
                    this.actual.onComplete();
                }
                return null;
            }
            if (tPoll != null) {
                long j2 = j - 1;
                this.remaining = j2;
                if (j2 == 0 && !this.done) {
                    this.done = true;
                    if (this.inputMode == 2) {
                        this.f2208qs.cancel();
                        this.actual.onComplete();
                    }
                }
            }
            return tPoll;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.remaining == 0 || this.f2208qs.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2208qs.clear();
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2208qs.size();
        }
    }
}
