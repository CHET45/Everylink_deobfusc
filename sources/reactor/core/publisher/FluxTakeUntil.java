package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Predicate;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxTakeUntil<T> extends InternalFluxOperator<T, T> {
    final Predicate<? super T> predicate;

    FluxTakeUntil(Flux<? extends T> flux, Predicate<? super T> predicate) {
        super(flux);
        this.predicate = (Predicate) Objects.requireNonNull(predicate, "predicate");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new TakeUntilPredicateSubscriber(coreSubscriber, this.predicate);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class TakeUntilPredicateSubscriber<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        boolean done;
        final Predicate<? super T> predicate;

        /* JADX INFO: renamed from: s */
        Subscription f2216s;

        TakeUntilPredicateSubscriber(CoreSubscriber<? super T> coreSubscriber, Predicate<? super T> predicate) {
            this.actual = coreSubscriber;
            this.predicate = predicate;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2216s, subscription)) {
                this.f2216s = subscription;
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
                boolean zTest = this.predicate.test(t);
                this.actual.onNext(t);
                if (zTest) {
                    this.f2216s.cancel();
                    onComplete();
                }
            } catch (Throwable th) {
                this.actual.onNext(t);
                onError(Operators.onOperatorError(this.f2216s, th, t, this.actual.currentContext()));
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
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.PARENT ? this.f2216s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2216s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2216s.cancel();
        }
    }
}
