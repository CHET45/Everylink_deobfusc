package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiFunction;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxScan<T> extends InternalFluxOperator<T, T> {
    final BiFunction<T, ? super T, T> accumulator;

    FluxScan(Flux<? extends T> flux, BiFunction<T, ? super T, T> biFunction) {
        super(flux);
        this.accumulator = (BiFunction) Objects.requireNonNull(biFunction, "accumulator");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new ScanSubscriber(coreSubscriber, this.accumulator);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ScanSubscriber<T> implements InnerOperator<T, T> {
        final BiFunction<T, ? super T, T> accumulator;
        final CoreSubscriber<? super T> actual;
        boolean done;

        /* JADX INFO: renamed from: s */
        Subscription f2187s;
        T value;

        ScanSubscriber(CoreSubscriber<? super T> coreSubscriber, BiFunction<T, ? super T, T> biFunction) {
            this.actual = coreSubscriber;
            this.accumulator = biFunction;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2187s, subscription)) {
                this.f2187s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            T t2 = this.value;
            if (t2 != null) {
                try {
                    t = (T) Objects.requireNonNull(this.accumulator.apply(t2, t), "The accumulator returned a null value");
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2187s, th, t, this.actual.currentContext()));
                    return;
                }
            }
            this.value = t;
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.value = null;
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.value = null;
            this.actual.onComplete();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2187s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.value != null ? 1 : 0);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2187s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2187s.cancel();
        }
    }
}
