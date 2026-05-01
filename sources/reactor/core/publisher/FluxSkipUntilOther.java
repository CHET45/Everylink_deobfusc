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
final class FluxSkipUntilOther<T, U> extends InternalFluxOperator<T, T> {
    final Publisher<U> other;

    FluxSkipUntilOther(Flux<? extends T> flux, Publisher<U> publisher) {
        super(flux);
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "other"));
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        SkipUntilMainSubscriber skipUntilMainSubscriber = new SkipUntilMainSubscriber(coreSubscriber);
        this.other.subscribe(new SkipUntilOtherSubscriber(skipUntilMainSubscriber));
        return skipUntilMainSubscriber;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SkipUntilOtherSubscriber<U> implements InnerConsumer<U> {
        final SkipUntilMainSubscriber<?> main;

        SkipUntilOtherSubscriber(SkipUntilMainSubscriber<?> skipUntilMainSubscriber) {
            this.main = skipUntilMainSubscriber;
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
            if (this.main.gate) {
                return;
            }
            SkipUntilMainSubscriber<?> skipUntilMainSubscriber = this.main;
            skipUntilMainSubscriber.other.cancel();
            skipUntilMainSubscriber.gate = true;
            skipUntilMainSubscriber.other = Operators.cancelledSubscription();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            SkipUntilMainSubscriber<?> skipUntilMainSubscriber = this.main;
            if (skipUntilMainSubscriber.gate) {
                Operators.onErrorDropped(th, this.main.currentContext());
            } else {
                skipUntilMainSubscriber.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            SkipUntilMainSubscriber<?> skipUntilMainSubscriber = this.main;
            if (skipUntilMainSubscriber.gate) {
                return;
            }
            skipUntilMainSubscriber.gate = true;
            skipUntilMainSubscriber.other = Operators.cancelledSubscription();
        }
    }

    static final class SkipUntilMainSubscriber<T> implements InnerOperator<T, T> {
        static final AtomicReferenceFieldUpdater<SkipUntilMainSubscriber, Subscription> MAIN = AtomicReferenceFieldUpdater.newUpdater(SkipUntilMainSubscriber.class, Subscription.class, "main");
        static final AtomicReferenceFieldUpdater<SkipUntilMainSubscriber, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(SkipUntilMainSubscriber.class, Subscription.class, "other");
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        volatile boolean gate;
        volatile Subscription main;
        volatile Subscription other;

        SkipUntilMainSubscriber(CoreSubscriber<? super T> coreSubscriber) {
            this.actual = Operators.serialize(coreSubscriber);
            this.ctx = coreSubscriber.currentContext();
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
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

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Operators.terminate(MAIN, this);
            Operators.terminate(OTHER, this);
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
            if (this.gate) {
                this.actual.onNext(t);
            } else {
                Operators.onDiscard(t, this.ctx);
                this.main.request(1L);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (C0162xc40028dd.m5m(MAIN, this, null, Operators.cancelledSubscription())) {
                Operators.error(this.actual, th);
            } else if (this.main == Operators.cancelledSubscription()) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                cancel();
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Operators.terminate(OTHER, this);
            this.actual.onComplete();
        }
    }
}
