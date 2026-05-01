package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxMap<T, R> extends InternalFluxOperator<T, R> {
    final Function<? super T, ? extends R> mapper;

    FluxMap(Flux<? extends T> flux, Function<? super T, ? extends R> function) {
        super(flux);
        this.mapper = (Function) Objects.requireNonNull(function, "mapper");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new MapConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.mapper);
        }
        return new MapSubscriber(coreSubscriber, this.mapper);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class MapSubscriber<T, R> implements InnerOperator<T, R> {
        final CoreSubscriber<? super R> actual;
        boolean done;
        final Function<? super T, ? extends R> mapper;

        /* JADX INFO: renamed from: s */
        Subscription f2146s;

        MapSubscriber(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends R> function) {
            this.actual = coreSubscriber;
            this.mapper = function;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2146s, subscription)) {
                this.f2146s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                R rApply = this.mapper.apply(t);
                if (rApply == null) {
                    throw new NullPointerException("The mapper [" + this.mapper.getClass().getName() + "] returned a null value.");
                }
                this.actual.onNext(rApply);
            } catch (Throwable th) {
                Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2146s);
                if (thOnNextError != null) {
                    onError(thOnNextError);
                } else {
                    this.f2146s.request(1L);
                }
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

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2146s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2146s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2146s.cancel();
        }
    }

    static final class MapConditionalSubscriber<T, R> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, R> {
        final Fuseable.ConditionalSubscriber<? super R> actual;
        boolean done;
        final Function<? super T, ? extends R> mapper;

        /* JADX INFO: renamed from: s */
        Subscription f2145s;

        MapConditionalSubscriber(Fuseable.ConditionalSubscriber<? super R> conditionalSubscriber, Function<? super T, ? extends R> function) {
            this.actual = conditionalSubscriber;
            this.mapper = function;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2145s, subscription)) {
                this.f2145s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                R rApply = this.mapper.apply(t);
                if (rApply == null) {
                    throw new NullPointerException("The mapper [" + this.mapper.getClass().getName() + "] returned a null value.");
                }
                this.actual.onNext(rApply);
            } catch (Throwable th) {
                Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2145s);
                if (thOnNextError != null) {
                    onError(thOnNextError);
                } else {
                    this.f2145s.request(1L);
                }
            }
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return true;
            }
            try {
                R rApply = this.mapper.apply(t);
                if (rApply == null) {
                    throw new NullPointerException("The mapper [" + this.mapper.getClass().getName() + "] returned a null value.");
                }
                return this.actual.tryOnNext(rApply);
            } catch (Throwable th) {
                Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2145s);
                if (thOnNextError == null) {
                    return false;
                }
                this.done = true;
                this.actual.onError(thOnNextError);
                return true;
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

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2145s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2145s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2145s.cancel();
        }
    }
}
