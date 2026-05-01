package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Consumer;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDelaySubscription<T, U> extends InternalFluxOperator<T, T> implements Consumer<DelaySubscriptionOtherSubscriber<T, U>> {
    final Publisher<U> other;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxDelaySubscription(Flux<? extends T> flux, Publisher<U> publisher) {
        super(flux);
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "other"));
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        this.other.subscribe(new DelaySubscriptionOtherSubscriber(coreSubscriber, this));
        return null;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // java.util.function.Consumer
    public void accept(DelaySubscriptionOtherSubscriber<T, U> delaySubscriptionOtherSubscriber) {
        this.source.subscribe((CoreSubscriber<? super Object>) new DelaySubscriptionMainSubscriber(delaySubscriptionOtherSubscriber.actual, delaySubscriptionOtherSubscriber));
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class DelaySubscriptionOtherSubscriber<T, U> extends Operators.DeferredSubscription implements InnerOperator<U, T> {
        final CoreSubscriber<? super T> actual;
        boolean done;

        /* JADX INFO: renamed from: s */
        Subscription f2110s;
        final Consumer<DelaySubscriptionOtherSubscriber<T, U>> source;

        DelaySubscriptionOtherSubscriber(CoreSubscriber<? super T> coreSubscriber, Consumer<DelaySubscriptionOtherSubscriber<T, U>> consumer) {
            this.actual = coreSubscriber;
            this.source = consumer;
        }

        @Override // reactor.core.publisher.InnerOperator, reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override // reactor.core.publisher.Operators.DeferredSubscription, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2110s : attr == Scannable.Attr.ACTUAL ? this.actual : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.Operators.DeferredSubscription, org.reactivestreams.Subscription
        public void cancel() {
            this.f2110s.cancel();
            super.cancel();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2110s, subscription)) {
                this.f2110s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U u) {
            if (this.done) {
                return;
            }
            this.done = true;
            this.f2110s.cancel();
            this.source.accept(this);
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
            this.source.accept(this);
        }
    }

    static final class DelaySubscriptionMainSubscriber<T> implements InnerConsumer<T> {
        final CoreSubscriber<? super T> actual;
        final DelaySubscriptionOtherSubscriber<?, ?> arbiter;

        DelaySubscriptionMainSubscriber(CoreSubscriber<? super T> coreSubscriber, DelaySubscriptionOtherSubscriber<?, ?> delaySubscriptionOtherSubscriber) {
            this.actual = coreSubscriber;
            this.arbiter = delaySubscriptionOtherSubscriber;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.arbiter.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
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
    }
}
