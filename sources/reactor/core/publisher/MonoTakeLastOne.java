package reactor.core.publisher;

import java.util.NoSuchElementException;
import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoTakeLastOne<T> extends MonoFromFluxOperator<T, T> implements Fuseable {
    final T defaultValue;

    MonoTakeLastOne(Flux<? extends T> flux) {
        super(flux);
        this.defaultValue = null;
    }

    MonoTakeLastOne(Flux<? extends T> flux, T t) {
        super(flux);
        this.defaultValue = (T) Objects.requireNonNull(t, "defaultValue");
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new TakeLastOneSubscriber(coreSubscriber, this.defaultValue, true);
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class TakeLastOneSubscriber<T> extends Operators.BaseFluxToMonoOperator<T, T> {
        static final Object CANCELLED = new Object();
        boolean done;
        final boolean mustEmit;
        T value;

        TakeLastOneSubscriber(CoreSubscriber<? super T> coreSubscriber, @Nullable T t, boolean z) {
            super(coreSubscriber);
            this.value = t;
            this.mustEmit = z;
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done && this.value == null);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.value == CANCELLED);
            }
            return super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            T t2;
            T t3 = this.value;
            Object obj = CANCELLED;
            if (t3 == obj) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            synchronized (this) {
                t2 = this.value;
                if (t2 != obj) {
                    this.value = t;
                }
            }
            if (t2 == obj) {
                Operators.onDiscard(t, this.actual.currentContext());
            } else {
                Operators.onDiscard(t2, this.actual.currentContext());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            T t;
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            synchronized (this) {
                t = this.value;
                this.value = null;
            }
            if (t == CANCELLED) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            if (t != null) {
                Operators.onDiscard(t, this.actual.currentContext());
            }
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            T t = this.value;
            if (t == CANCELLED) {
                return;
            }
            if (t == null) {
                if (this.mustEmit) {
                    this.actual.onError(Operators.onOperatorError(new NoSuchElementException("Flux#last() didn't observe any onNext signal"), this.actual.currentContext()));
                    return;
                } else {
                    this.actual.onComplete();
                    return;
                }
            }
            completePossiblyEmpty();
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, org.reactivestreams.Subscription
        public void cancel() {
            T t;
            this.f2280s.cancel();
            synchronized (this) {
                t = this.value;
                this.value = (T) CANCELLED;
            }
            if (t != null) {
                Operators.onDiscard(t, this.actual.currentContext());
            }
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator
        T accumulatedValue() {
            T t;
            synchronized (this) {
                t = this.value;
                this.value = null;
            }
            if (t == CANCELLED) {
                return null;
            }
            return t;
        }
    }
}
