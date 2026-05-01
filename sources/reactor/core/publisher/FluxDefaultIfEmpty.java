package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDefaultIfEmpty<T> extends InternalFluxOperator<T, T> {
    final T value;

    FluxDefaultIfEmpty(Flux<? extends T> flux, T t) {
        super(flux);
        this.value = (T) Objects.requireNonNull(t, "value");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new DefaultIfEmptySubscriber(coreSubscriber, this.value);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class DefaultIfEmptySubscriber<T> extends Operators.BaseFluxToMonoOperator<T, T> {
        static final AtomicReferenceFieldUpdater<DefaultIfEmptySubscriber, Object> FALLBACK_VALUE = AtomicReferenceFieldUpdater.newUpdater(DefaultIfEmptySubscriber.class, Object.class, "fallbackValue");
        boolean done;
        volatile T fallbackValue;
        boolean hasValue;

        DefaultIfEmptySubscriber(CoreSubscriber<? super T> coreSubscriber, T t) {
            super(coreSubscriber);
            FALLBACK_VALUE.lazySet(this, t);
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : super.scanUnsafe(attr);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, org.reactivestreams.Subscription
        public void request(long j) {
            if (!this.hasRequest) {
                this.hasRequest = true;
                int i = this.state;
                if (i != 1 && STATE.compareAndSet(this, i, i | 1) && i > 1) {
                    T t = this.fallbackValue;
                    if (t == null || !C0162xc40028dd.m5m(FALLBACK_VALUE, this, t, null)) {
                        return;
                    }
                    this.actual.onNext((Object) t);
                    this.actual.onComplete();
                    return;
                }
            }
            this.f2280s.request(j);
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            T t = this.fallbackValue;
            if (t == null || !C0162xc40028dd.m5m(FALLBACK_VALUE, this, t, null)) {
                return;
            }
            Operators.onDiscard(t, this.actual.currentContext());
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.hasValue) {
                this.hasValue = true;
                T t2 = this.fallbackValue;
                if (t2 != null && C0162xc40028dd.m5m(FALLBACK_VALUE, this, t2, null)) {
                    Operators.onDiscard(t2, this.actual.currentContext());
                }
            }
            this.actual.onNext((Object) t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            if (!this.hasValue) {
                completePossiblyEmpty();
            } else {
                this.actual.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            T t;
            if (this.done) {
                return;
            }
            this.done = true;
            if (!this.hasValue && (t = this.fallbackValue) != null && C0162xc40028dd.m5m(FALLBACK_VALUE, this, t, null)) {
                Operators.onDiscard(th, this.actual.currentContext());
            }
            this.actual.onError(th);
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator
        T accumulatedValue() {
            T t = this.fallbackValue;
            if (t == null || !C0162xc40028dd.m5m(FALLBACK_VALUE, this, t, null)) {
                return null;
            }
            return t;
        }
    }
}
