package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoReduceSeed<T, R> extends MonoFromFluxOperator<T, R> implements Fuseable {
    final BiFunction<R, ? super T, R> accumulator;
    final Supplier<R> initialSupplier;

    MonoReduceSeed(Flux<? extends T> flux, Supplier<R> supplier, BiFunction<R, ? super T, R> biFunction) {
        super(flux);
        this.initialSupplier = (Supplier) Objects.requireNonNull(supplier, "initialSupplier");
        this.accumulator = (BiFunction) Objects.requireNonNull(biFunction, "accumulator");
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        return new ReduceSeedSubscriber(coreSubscriber, this.accumulator, Objects.requireNonNull(this.initialSupplier.get(), "The initial value supplied is null"));
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ReduceSeedSubscriber<T, R> extends Operators.BaseFluxToMonoOperator<T, R> {
        final BiFunction<R, ? super T, R> accumulator;
        boolean done;
        R seed;

        ReduceSeedSubscriber(CoreSubscriber<? super R> coreSubscriber, BiFunction<R, ? super T, R> biFunction, R r) {
            super(coreSubscriber);
            this.accumulator = biFunction;
            this.seed = r;
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(!this.done && this.seed == null);
            }
            return super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, org.reactivestreams.Subscription
        public void cancel() {
            R r;
            this.f2280s.cancel();
            synchronized (this) {
                r = this.seed;
                this.seed = null;
            }
            if (r == null) {
                return;
            }
            Operators.onDiscard(r, this.actual.currentContext());
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            try {
                synchronized (this) {
                    R r = this.seed;
                    if (r != null) {
                        this.seed = (R) Objects.requireNonNull(this.accumulator.apply(r, t), "The accumulator returned a null value");
                    } else {
                        Operators.onDiscard(t, this.actual.currentContext());
                    }
                }
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2280s, th, t, this.actual.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            R r;
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            synchronized (this) {
                r = this.seed;
                this.seed = null;
            }
            if (r == null) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                Operators.onDiscard(r, this.actual.currentContext());
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            completePossiblyEmpty();
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator
        R accumulatedValue() {
            R r;
            synchronized (this) {
                r = this.seed;
                this.seed = null;
            }
            return r;
        }
    }
}
