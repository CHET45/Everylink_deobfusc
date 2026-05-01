package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelReduceSeed<T, R> extends ParallelFlux<R> implements Scannable, Fuseable {
    final Supplier<R> initialSupplier;
    final BiFunction<R, ? super T, R> reducer;
    final ParallelFlux<? extends T> source;

    @Override // reactor.core.publisher.ParallelFlux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    ParallelReduceSeed(ParallelFlux<? extends T> parallelFlux, Supplier<R> supplier, BiFunction<R, ? super T, R> biFunction) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.initialSupplier = supplier;
        this.reducer = biFunction;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super R>[] coreSubscriberArr) {
        if (validate(coreSubscriberArr)) {
            int length = coreSubscriberArr.length;
            CoreSubscriber<? super Object>[] coreSubscriberArr2 = new CoreSubscriber[length];
            for (int i = 0; i < length; i++) {
                try {
                    coreSubscriberArr2[i] = new ParallelReduceSeedSubscriber(coreSubscriberArr[i], Objects.requireNonNull(this.initialSupplier.get(), "The initialSupplier returned a null value"), this.reducer);
                } catch (Throwable th) {
                    reportError(coreSubscriberArr, Operators.onOperatorError(th, coreSubscriberArr[i].currentContext()));
                    return;
                }
            }
            this.source.subscribe(coreSubscriberArr2);
        }
    }

    void reportError(Subscriber<?>[] subscriberArr, Throwable th) {
        for (Subscriber<?> subscriber : subscriberArr) {
            Operators.error(subscriber, th);
        }
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.source.parallelism();
    }

    static final class ParallelReduceSeedSubscriber<T, R> extends Operators.BaseFluxToMonoOperator<T, R> {
        R accumulator;
        boolean done;
        final BiFunction<R, ? super T, R> reducer;

        ParallelReduceSeedSubscriber(CoreSubscriber<? super R> coreSubscriber, R r, BiFunction<R, ? super T, R> biFunction) {
            super(coreSubscriber);
            this.accumulator = r;
            this.reducer = biFunction;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            synchronized (this) {
                try {
                    R r = this.accumulator;
                    if (r == null) {
                        return;
                    }
                    this.accumulator = (R) Objects.requireNonNull(this.reducer.apply(r, t), "The reducer returned a null value");
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2280s, th, t, this.actual.currentContext()));
                }
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
                r = this.accumulator;
                if (r != null) {
                    this.accumulator = null;
                }
            }
            if (r == null) {
                return;
            }
            Operators.onDiscard(r, currentContext());
            this.actual.onError(th);
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
                r = this.accumulator;
                if (r != null) {
                    this.accumulator = null;
                }
            }
            return r;
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, org.reactivestreams.Subscription
        public void cancel() {
            R r;
            this.f2280s.cancel();
            synchronized (this) {
                r = this.accumulator;
                if (r != null) {
                    this.accumulator = null;
                }
            }
            if (r == null) {
                return;
            }
            Operators.onDiscard(r, currentContext());
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(!this.done && this.accumulator == null);
            }
            return super.scanUnsafe(attr);
        }
    }
}
