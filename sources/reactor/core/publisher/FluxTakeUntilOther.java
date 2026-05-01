package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxTakeUntilOther<T, U> extends InternalFluxOperator<T, T> {
    final Publisher<U> other;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxTakeUntilOther(Flux<? extends T> flux, Publisher<U> publisher) {
        super(flux);
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "other"));
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        TakeUntilMainSubscriber takeUntilMainSubscriber = new TakeUntilMainSubscriber(coreSubscriber);
        this.other.subscribe(new TakeUntilOtherSubscriber(takeUntilMainSubscriber));
        return takeUntilMainSubscriber;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class TakeUntilOtherSubscriber<U> implements InnerConsumer<U> {
        final TakeUntilMainSubscriber<?> main;
        boolean once;

        TakeUntilOtherSubscriber(TakeUntilMainSubscriber<?> takeUntilMainSubscriber) {
            this.main = takeUntilMainSubscriber;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.main.other == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.main.other;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.main.setOther(subscription);
            subscription.request(Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U u) {
            if (this.once) {
                return;
            }
            this.once = true;
            this.main.cancelOther();
            this.main.cancelMainAndComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.once) {
                return;
            }
            this.once = true;
            this.main.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.once) {
                return;
            }
            this.once = true;
            this.main.cancelMainAndComplete();
        }
    }

    static final class TakeUntilMainSubscriber<T> implements InnerOperator<T, T> {
        static final AtomicReferenceFieldUpdater<TakeUntilMainSubscriber, Subscription> MAIN = AtomicReferenceFieldUpdater.newUpdater(TakeUntilMainSubscriber.class, Subscription.class, "main");
        static final AtomicReferenceFieldUpdater<TakeUntilMainSubscriber, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(TakeUntilMainSubscriber.class, Subscription.class, "other");
        final CoreSubscriber<? super T> actual;
        volatile Subscription main;
        volatile Subscription other;

        TakeUntilMainSubscriber(CoreSubscriber<? super T> coreSubscriber) {
            this.actual = Operators.serialize(coreSubscriber);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.main;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.main == Operators.cancelledSubscription());
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
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

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.main.request(j);
        }

        void cancelMainAndComplete() {
            if (this.main != Operators.cancelledSubscription()) {
                Subscription andSet = MAIN.getAndSet(this, Operators.cancelledSubscription());
                if (andSet != null && andSet != Operators.cancelledSubscription()) {
                    andSet.cancel();
                }
                if (andSet == null) {
                    Operators.complete(this.actual);
                } else {
                    this.actual.onComplete();
                }
            }
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
            Subscription andSet;
            if (this.main != Operators.cancelledSubscription() && (andSet = MAIN.getAndSet(this, Operators.cancelledSubscription())) != null && andSet != Operators.cancelledSubscription()) {
                andSet.cancel();
            }
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
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.main == null && C0162xc40028dd.m5m(MAIN, this, null, Operators.cancelledSubscription())) {
                Operators.error(this.actual, th);
            } else {
                cancelOther();
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.main == null && C0162xc40028dd.m5m(MAIN, this, null, Operators.cancelledSubscription())) {
                Operators.complete(this.actual);
            } else {
                cancelOther();
                this.actual.onComplete();
            }
        }
    }
}
