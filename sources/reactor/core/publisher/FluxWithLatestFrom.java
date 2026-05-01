package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxWithLatestFrom<T, U, R> extends InternalFluxOperator<T, R> {
    final BiFunction<? super T, ? super U, ? extends R> combiner;
    final Publisher<? extends U> other;

    FluxWithLatestFrom(Flux<? extends T> flux, Publisher<? extends U> publisher, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        super(flux);
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "other"));
        this.combiner = (BiFunction) Objects.requireNonNull(biFunction, "combiner");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        WithLatestFromSubscriber withLatestFromSubscriber = new WithLatestFromSubscriber(Operators.serialize(coreSubscriber), this.combiner);
        this.other.subscribe(new WithLatestFromOtherSubscriber(withLatestFromSubscriber));
        return withLatestFromSubscriber;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class WithLatestFromSubscriber<T, U, R> implements InnerOperator<T, R> {
        static final AtomicReferenceFieldUpdater<WithLatestFromSubscriber, Subscription> MAIN = AtomicReferenceFieldUpdater.newUpdater(WithLatestFromSubscriber.class, Subscription.class, "main");
        static final AtomicReferenceFieldUpdater<WithLatestFromSubscriber, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(WithLatestFromSubscriber.class, Subscription.class, "other");
        final CoreSubscriber<? super R> actual;
        final BiFunction<? super T, ? super U, ? extends R> combiner;
        volatile Subscription main;
        volatile Subscription other;
        volatile U otherValue;

        WithLatestFromSubscriber(CoreSubscriber<? super R> coreSubscriber, BiFunction<? super T, ? super U, ? extends R> biFunction) {
            this.actual = coreSubscriber;
            this.combiner = biFunction;
        }

        void setOther(Subscription subscription) {
            if (C0162xc40028dd.m5m(OTHER, this, null, subscription)) {
                return;
            }
            subscription.cancel();
            if (this.other != Operators.cancelledSubscription()) {
                Operators.reportSubscriptionSet();
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.main == Operators.cancelledSubscription());
            }
            return attr == Scannable.Attr.PARENT ? this.main : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.main.request(j);
        }

        void cancelMain() {
            Subscription andSet;
            if (this.main == Operators.cancelledSubscription() || (andSet = MAIN.getAndSet(this, Operators.cancelledSubscription())) == null || andSet == Operators.cancelledSubscription()) {
                return;
            }
            andSet.cancel();
        }

        void cancelOther() {
            Subscription andSet;
            if (this.other == Operators.cancelledSubscription() || (andSet = OTHER.getAndSet(this, Operators.cancelledSubscription())) == null || andSet == Operators.cancelledSubscription()) {
                return;
            }
            andSet.cancel();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            cancelMain();
            cancelOther();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (!C0162xc40028dd.m5m(MAIN, this, null, subscription)) {
                subscription.cancel();
                if (this.main != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                    return;
                }
                return;
            }
            this.actual.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            U u = this.otherValue;
            if (u != null) {
                try {
                    this.actual.onNext(Objects.requireNonNull(this.combiner.apply(t, u), "The combiner returned a null value"));
                    return;
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this, th, t, this.actual.currentContext()));
                    return;
                }
            }
            this.main.request(1L);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.main == null && C0162xc40028dd.m5m(MAIN, this, null, Operators.cancelledSubscription())) {
                cancelOther();
                Operators.error(this.actual, th);
            } else {
                cancelOther();
                this.otherValue = null;
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            cancelOther();
            this.otherValue = null;
            this.actual.onComplete();
        }

        void otherError(Throwable th) {
            if (this.main == null && C0162xc40028dd.m5m(MAIN, this, null, Operators.cancelledSubscription())) {
                cancelMain();
                Operators.error(this.actual, th);
            } else {
                cancelMain();
                this.otherValue = null;
                this.actual.onError(th);
            }
        }

        void otherComplete() {
            if (this.otherValue == null) {
                if (this.main == null && C0162xc40028dd.m5m(MAIN, this, null, Operators.cancelledSubscription())) {
                    cancelMain();
                    Operators.complete(this.actual);
                } else {
                    cancelMain();
                    this.actual.onComplete();
                }
            }
        }
    }

    static final class WithLatestFromOtherSubscriber<U> implements InnerConsumer<U> {
        final WithLatestFromSubscriber<?, U, ?> main;

        WithLatestFromOtherSubscriber(WithLatestFromSubscriber<?, U, ?> withLatestFromSubscriber) {
            this.main = withLatestFromSubscriber;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.main.setOther(subscription);
            subscription.request(Long.MAX_VALUE);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U u) {
            this.main.otherValue = u;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.main.otherError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.main.otherComplete();
        }
    }
}
