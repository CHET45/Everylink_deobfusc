package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxSample<T, U> extends InternalFluxOperator<T, T> {
    final Publisher<U> other;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxSample(Flux<? extends T> flux, Publisher<U> publisher) {
        super(flux);
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "other"));
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        SampleMainSubscriber sampleMainSubscriber = new SampleMainSubscriber(Operators.serialize(coreSubscriber));
        coreSubscriber.onSubscribe(sampleMainSubscriber);
        this.other.subscribe(new SampleOther(sampleMainSubscriber));
        return sampleMainSubscriber;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SampleMainSubscriber<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        volatile Subscription main;
        volatile Subscription other;
        volatile long requested;
        volatile T value;
        static final AtomicReferenceFieldUpdater<SampleMainSubscriber, Object> VALUE = AtomicReferenceFieldUpdater.newUpdater(SampleMainSubscriber.class, Object.class, "value");
        static final AtomicReferenceFieldUpdater<SampleMainSubscriber, Subscription> MAIN = AtomicReferenceFieldUpdater.newUpdater(SampleMainSubscriber.class, Subscription.class, "main");
        static final AtomicReferenceFieldUpdater<SampleMainSubscriber, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(SampleMainSubscriber.class, Subscription.class, "other");
        static final AtomicLongFieldUpdater<SampleMainSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(SampleMainSubscriber.class, "requested");

        SampleMainSubscriber(CoreSubscriber<? super T> coreSubscriber) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.main;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.main == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.value == null ? 0 : 1);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
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
            subscription.request(Long.MAX_VALUE);
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

        void setOther(Subscription subscription) {
            if (!C0162xc40028dd.m5m(OTHER, this, null, subscription)) {
                subscription.cancel();
                if (this.other != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                    return;
                }
                return;
            }
            subscription.request(Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            cancelMain();
            cancelOther();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            Object andSet = VALUE.getAndSet(this, t);
            if (andSet != null) {
                Operators.onDiscard(andSet, this.ctx);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            cancelOther();
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            cancelOther();
            if (this.value != null) {
                this.actual.onNext(this.value);
            }
            this.actual.onComplete();
        }

        @Nullable
        T getAndNullValue() {
            return (T) VALUE.getAndSet(this, null);
        }

        void decrement() {
            REQUESTED.decrementAndGet(this);
        }
    }

    static final class SampleOther<T, U> implements InnerConsumer<U> {
        final SampleMainSubscriber<T> main;

        SampleOther(SampleMainSubscriber<T> sampleMainSubscriber) {
            this.main = sampleMainSubscriber;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.main.other;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.main.other == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
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

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.main.setOther(subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U u) {
            SampleMainSubscriber<T> sampleMainSubscriber = this.main;
            T andNullValue = sampleMainSubscriber.getAndNullValue();
            if (andNullValue != null) {
                if (sampleMainSubscriber.requested != 0) {
                    sampleMainSubscriber.actual.onNext(andNullValue);
                    if (sampleMainSubscriber.requested != Long.MAX_VALUE) {
                        sampleMainSubscriber.decrement();
                        return;
                    }
                    return;
                }
                sampleMainSubscriber.cancel();
                sampleMainSubscriber.actual.onError(Exceptions.failWithOverflow("Can't signal value due to lack of requests"));
                Operators.onDiscard(andNullValue, sampleMainSubscriber.ctx);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            SampleMainSubscriber<T> sampleMainSubscriber = this.main;
            sampleMainSubscriber.cancelMain();
            sampleMainSubscriber.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            SampleMainSubscriber<T> sampleMainSubscriber = this.main;
            sampleMainSubscriber.cancelMain();
            sampleMainSubscriber.actual.onComplete();
        }
    }
}
