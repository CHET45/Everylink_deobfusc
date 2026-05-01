package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Predicate;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxOnErrorReturn<T> extends InternalFluxOperator<T, T> {

    @Nullable
    final T fallbackValue;

    @Nullable
    final Predicate<? super Throwable> resumableErrorPredicate;

    FluxOnErrorReturn(Flux<? extends T> flux, @Nullable Predicate<? super Throwable> predicate, @Nullable T t) {
        super(flux);
        this.resumableErrorPredicate = predicate;
        this.fallbackValue = t;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new ReturnSubscriber(coreSubscriber, this.resumableErrorPredicate, this.fallbackValue, false);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ReturnSubscriber<T> implements InnerOperator<T, T> {
        static final AtomicLongFieldUpdater<ReturnSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(ReturnSubscriber.class, "requested");
        private static final byte STATE_CANCELLED = -3;
        private static final byte STATE_PENDING_FALLBACK = -1;
        private static final byte STATE_TERMINATED = -2;
        final CoreSubscriber<? super T> actual;

        @Nullable
        final T fallbackValue;
        volatile long requested;

        @Nullable
        final Predicate<? super Throwable> resumableErrorPredicate;

        /* JADX INFO: renamed from: s */
        Subscription f2164s;
        final boolean trackRequestWhenFallbackDeferred;

        ReturnSubscriber(CoreSubscriber<? super T> coreSubscriber, @Nullable Predicate<? super Throwable> predicate, @Nullable T t, boolean z) {
            this.actual = coreSubscriber;
            this.resumableErrorPredicate = predicate;
            this.fallbackValue = t;
            this.trackRequestWhenFallbackDeferred = z;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2164s, subscription)) {
                this.f2164s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        void producedOne() {
            AtomicLongFieldUpdater<ReturnSubscriber> atomicLongFieldUpdater;
            long j;
            do {
                atomicLongFieldUpdater = REQUESTED;
                j = atomicLongFieldUpdater.get(this);
                if (j == Long.MAX_VALUE || j <= 0) {
                    return;
                }
            } while (!atomicLongFieldUpdater.compareAndSet(this, j, j - 1));
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (REQUESTED.getAndSet(this, -3L) != -3) {
                this.f2164s.cancel();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            AtomicLongFieldUpdater<ReturnSubscriber> atomicLongFieldUpdater;
            long j2;
            do {
                atomicLongFieldUpdater = REQUESTED;
                j2 = atomicLongFieldUpdater.get(this);
                if (j2 == Long.MAX_VALUE || j2 < -1) {
                    return;
                }
                if (j2 == -1) {
                    if (atomicLongFieldUpdater.compareAndSet(this, j2, -2L)) {
                        T t = this.fallbackValue;
                        if (t != null) {
                            this.actual.onNext(t);
                        }
                        this.actual.onComplete();
                        if (this.trackRequestWhenFallbackDeferred) {
                            this.f2164s.request(j);
                            this.f2164s.cancel();
                            return;
                        }
                        return;
                    }
                    return;
                }
            } while (!atomicLongFieldUpdater.compareAndSet(this, j2, Operators.addCap(j2, j)));
            this.f2164s.request(j);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            producedOne();
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.requested != -3) {
                REQUESTED.set(this, -2L);
                this.actual.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Predicate<? super Throwable> predicate = this.resumableErrorPredicate;
            if (predicate != null && !predicate.test(th)) {
                if (this.requested != -3) {
                    REQUESTED.set(this, -2L);
                    this.actual.onError(th);
                    return;
                }
                return;
            }
            if (this.fallbackValue == null) {
                if (this.requested != -3) {
                    REQUESTED.set(this, -2L);
                    this.actual.onComplete();
                    return;
                }
                return;
            }
            if (this.requested > 0) {
                REQUESTED.set(this, -2L);
                this.actual.onNext(this.fallbackValue);
                this.actual.onComplete();
                return;
            }
            AtomicLongFieldUpdater<ReturnSubscriber> atomicLongFieldUpdater = REQUESTED;
            if (atomicLongFieldUpdater.compareAndSet(this, 0L, -1L) || this.requested <= 0) {
                return;
            }
            atomicLongFieldUpdater.set(this, -2L);
            this.actual.onNext(this.fallbackValue);
            this.actual.onComplete();
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.f2164s;
            }
            long j = this.requested;
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(j == -3);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(j == -2);
            }
            if (attr != Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return super.scanUnsafe(attr);
            }
            if (j <= 0) {
                j = 0;
            }
            return Long.valueOf(j);
        }
    }
}
