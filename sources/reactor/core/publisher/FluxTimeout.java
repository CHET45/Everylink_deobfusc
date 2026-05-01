package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import com.aivox.base.common.Constant;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxTimeout<T, U, V> extends InternalFluxOperator<T, T> {
    final Publisher<U> firstTimeout;
    final Function<? super T, ? extends Publisher<V>> itemTimeout;
    final Publisher<? extends T> other;
    final String timeoutDescription;

    enum CancelledIndexedCancellable implements IndexedCancellable {
        INSTANCE;

        @Override // reactor.core.publisher.FluxTimeout.IndexedCancellable
        public void cancel() {
        }

        @Override // reactor.core.publisher.FluxTimeout.IndexedCancellable
        public long index() {
            return Long.MAX_VALUE;
        }
    }

    interface IndexedCancellable {
        void cancel();

        long index();
    }

    FluxTimeout(Flux<? extends T> flux, Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function, String str) {
        super(flux);
        this.firstTimeout = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "firstTimeout"));
        this.itemTimeout = (Function) Objects.requireNonNull(function, "itemTimeout");
        this.other = null;
        this.timeoutDescription = addNameToTimeoutDescription(flux, (String) Objects.requireNonNull(str, "timeoutDescription is needed when no fallback"));
    }

    FluxTimeout(Flux<? extends T> flux, Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function, Publisher<? extends T> publisher2) {
        super(flux);
        this.firstTimeout = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "firstTimeout"));
        this.itemTimeout = (Function) Objects.requireNonNull(function, "itemTimeout");
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher2, "other"));
        this.timeoutDescription = null;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new TimeoutMainSubscriber(coreSubscriber, this.firstTimeout, this.itemTimeout, this.other, this.timeoutDescription);
    }

    @Nullable
    static String addNameToTimeoutDescription(Publisher<?> publisher, @Nullable String str) {
        if (str == null) {
            return null;
        }
        Scannable scannableFrom = Scannable.from(publisher);
        return scannableFrom.isScanAvailable() ? str + " in '" + scannableFrom.name() + PunctuationConst.SINGLE_QUOTES : str;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class TimeoutMainSubscriber<T, V> extends Operators.MultiSubscriptionSubscriber<T, T> {
        final Publisher<?> firstTimeout;
        volatile long index;
        final Function<? super T, ? extends Publisher<V>> itemTimeout;
        final Publisher<? extends T> other;

        /* JADX INFO: renamed from: s */
        Subscription f2222s;
        volatile IndexedCancellable timeout;
        final String timeoutDescription;
        static final AtomicReferenceFieldUpdater<TimeoutMainSubscriber, IndexedCancellable> TIMEOUT = AtomicReferenceFieldUpdater.newUpdater(TimeoutMainSubscriber.class, IndexedCancellable.class, "timeout");
        static final AtomicLongFieldUpdater<TimeoutMainSubscriber> INDEX = AtomicLongFieldUpdater.newUpdater(TimeoutMainSubscriber.class, Constant.KEY_INDEX);

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber
        protected boolean shouldCancelCurrent() {
            return true;
        }

        TimeoutMainSubscriber(CoreSubscriber<? super T> coreSubscriber, Publisher<?> publisher, Function<? super T, ? extends Publisher<V>> function, @Nullable Publisher<? extends T> publisher2, @Nullable String str) {
            super(Operators.serialize(coreSubscriber));
            this.itemTimeout = function;
            this.other = publisher2;
            this.timeoutDescription = str;
            this.firstTimeout = publisher;
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2222s, subscription)) {
                this.f2222s = subscription;
                set(subscription);
                TimeoutTimeoutSubscriber timeoutTimeoutSubscriber = new TimeoutTimeoutSubscriber(this, 0L);
                this.timeout = timeoutTimeoutSubscriber;
                this.actual.onSubscribe(this);
                this.firstTimeout.subscribe(timeoutTimeoutSubscriber);
            }
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.timeout.cancel();
            long j = this.index;
            if (j == Long.MIN_VALUE) {
                this.f2222s.cancel();
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long j2 = j + 1;
            if (!INDEX.compareAndSet(this, j, j2)) {
                this.f2222s.cancel();
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            this.actual.onNext((Object) t);
            producedOne();
            try {
                Publisher publisher = (Publisher) Objects.requireNonNull(this.itemTimeout.apply(t), "The itemTimeout returned a null Publisher");
                TimeoutTimeoutSubscriber timeoutTimeoutSubscriber = new TimeoutTimeoutSubscriber(this, j2);
                if (setTimeout(timeoutTimeoutSubscriber)) {
                    Operators.toFluxOrMono(publisher).subscribe((CoreSubscriber) timeoutTimeoutSubscriber);
                }
            } catch (Throwable th) {
                this.actual.onError(Operators.onOperatorError(this, th, t, this.actual.currentContext()));
            }
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            long j = this.index;
            if (j == Long.MIN_VALUE) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else if (!INDEX.compareAndSet(this, j, Long.MIN_VALUE)) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                cancelTimeout();
                this.actual.onError(th);
            }
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            long j = this.index;
            if (j != Long.MIN_VALUE && INDEX.compareAndSet(this, j, Long.MIN_VALUE)) {
                cancelTimeout();
                this.actual.onComplete();
            }
        }

        void cancelTimeout() {
            IndexedCancellable andSet;
            if (this.timeout == CancelledIndexedCancellable.INSTANCE || (andSet = TIMEOUT.getAndSet(this, CancelledIndexedCancellable.INSTANCE)) == null || andSet == CancelledIndexedCancellable.INSTANCE) {
                return;
            }
            andSet.cancel();
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscription
        public void cancel() {
            this.index = Long.MIN_VALUE;
            cancelTimeout();
            super.cancel();
        }

        boolean setTimeout(IndexedCancellable indexedCancellable) {
            IndexedCancellable indexedCancellable2;
            do {
                indexedCancellable2 = this.timeout;
                if (indexedCancellable2 == CancelledIndexedCancellable.INSTANCE) {
                    indexedCancellable.cancel();
                    return false;
                }
                if (indexedCancellable2 != null && indexedCancellable2.index() >= indexedCancellable.index()) {
                    indexedCancellable.cancel();
                    return false;
                }
            } while (!C0162xc40028dd.m5m(TIMEOUT, this, indexedCancellable2, indexedCancellable));
            if (indexedCancellable2 == null) {
                return true;
            }
            indexedCancellable2.cancel();
            return true;
        }

        void doTimeout(long j) {
            if (this.index == j && INDEX.compareAndSet(this, j, Long.MIN_VALUE)) {
                handleTimeout();
            }
        }

        void doError(long j, Throwable th) {
            if (this.index == j && INDEX.compareAndSet(this, j, Long.MIN_VALUE)) {
                super.cancel();
                this.actual.onError(th);
            }
        }

        void handleTimeout() {
            if (this.other == null) {
                super.cancel();
                this.actual.onError(new TimeoutException("Did not observe any item or terminal signal within " + this.timeoutDescription + " (and no fallback has been configured)"));
            } else {
                set(Operators.emptySubscription());
                this.other.subscribe(new TimeoutOtherSubscriber(this.actual, this));
            }
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static final class TimeoutOtherSubscriber<T> implements InnerConsumer<T> {
        final CoreSubscriber<? super T> actual;
        final Operators.MultiSubscriptionSubscriber<T, T> arbiter;

        TimeoutOtherSubscriber(CoreSubscriber<? super T> coreSubscriber, Operators.MultiSubscriptionSubscriber<T, T> multiSubscriptionSubscriber) {
            this.actual = coreSubscriber;
            this.arbiter = multiSubscriptionSubscriber;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.arbiter.set(subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class TimeoutTimeoutSubscriber implements InnerConsumer<Object>, IndexedCancellable {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<TimeoutTimeoutSubscriber, Subscription> f2223S = AtomicReferenceFieldUpdater.newUpdater(TimeoutTimeoutSubscriber.class, Subscription.class, "s");
        final long index;
        final TimeoutMainSubscriber<?, ?> main;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2224s;

        TimeoutTimeoutSubscriber(TimeoutMainSubscriber<?, ?> timeoutMainSubscriber, long j) {
            this.main = timeoutMainSubscriber;
            this.index = j;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (!C0162xc40028dd.m5m(f2223S, this, null, subscription)) {
                subscription.cancel();
                if (this.f2224s != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                    return;
                }
                return;
            }
            subscription.request(Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            this.f2224s.cancel();
            this.main.doTimeout(this.index);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.main.doError(this.index, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.main.doTimeout(this.index);
        }

        @Override // reactor.core.publisher.FluxTimeout.IndexedCancellable
        public void cancel() {
            Subscription andSet;
            if (this.f2224s == Operators.cancelledSubscription() || (andSet = f2223S.getAndSet(this, Operators.cancelledSubscription())) == null || andSet == Operators.cancelledSubscription()) {
                return;
            }
            andSet.cancel();
        }

        @Override // reactor.core.publisher.FluxTimeout.IndexedCancellable
        public long index() {
            return this.index;
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }
}
