package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Predicate;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxTakeWhile<T> extends InternalFluxOperator<T, T> {
    final Predicate<? super T> predicate;

    FluxTakeWhile(Flux<? extends T> flux, Predicate<? super T> predicate) {
        super(flux);
        this.predicate = (Predicate) Objects.requireNonNull(predicate, "predicate");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new TakeWhileSubscriber(coreSubscriber, this.predicate);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class TakeWhileSubscriber<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        boolean done;
        final Predicate<? super T> predicate;

        /* JADX INFO: renamed from: s */
        Subscription f2217s;

        TakeWhileSubscriber(CoreSubscriber<? super T> coreSubscriber, Predicate<? super T> predicate) {
            this.actual = coreSubscriber;
            this.predicate = predicate;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2217s, subscription)) {
                this.f2217s = subscription;
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
                if (!this.predicate.test(t)) {
                    this.f2217s.cancel();
                    onComplete();
                } else {
                    this.actual.onNext(t);
                }
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2217s, th, t, this.actual.currentContext()));
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
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.PARENT ? this.f2217s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2217s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2217s.cancel();
        }
    }
}
