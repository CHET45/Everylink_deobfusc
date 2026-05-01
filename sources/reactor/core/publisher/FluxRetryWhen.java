package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;
import reactor.util.retry.Retry;

/* JADX INFO: loaded from: classes5.dex */
final class FluxRetryWhen<T> extends InternalFluxOperator<T, T> {
    final Retry whenSourceFactory;

    FluxRetryWhen(Flux<? extends T> flux, Retry retry) {
        super(flux);
        this.whenSourceFactory = (Retry) Objects.requireNonNull(retry, "whenSourceFactory");
    }

    static <T> void subscribe(CoreSubscriber<? super T> coreSubscriber, Retry retry, CorePublisher<? extends T> corePublisher) {
        CorePublisher fluxOrMono = Operators.toFluxOrMono(corePublisher);
        RetryWhenOtherSubscriber retryWhenOtherSubscriber = new RetryWhenOtherSubscriber();
        CoreSubscriber coreSubscriberSerialize = Operators.serialize(coreSubscriber);
        RetryWhenMainSubscriber<?> retryWhenMainSubscriber = new RetryWhenMainSubscriber<>(coreSubscriberSerialize, retryWhenOtherSubscriber.completionSignal, fluxOrMono, retry.retryContext());
        retryWhenOtherSubscriber.main = retryWhenMainSubscriber;
        coreSubscriberSerialize.onSubscribe(retryWhenMainSubscriber);
        try {
            Operators.toFluxOrMono((Publisher) Objects.requireNonNull(retry.generateCompanion(retryWhenOtherSubscriber), "The whenSourceFactory returned a null Publisher")).subscribe((Subscriber) retryWhenOtherSubscriber);
            if (retryWhenMainSubscriber.cancelled) {
                return;
            }
            fluxOrMono.subscribe((CoreSubscriber) retryWhenMainSubscriber);
        } catch (Throwable th) {
            coreSubscriber.onError(Operators.onOperatorError(th, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        subscribe(coreSubscriber, this.whenSourceFactory, this.source);
        return null;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class RetryWhenMainSubscriber<T> extends Operators.MultiSubscriptionSubscriber<T, T> implements Retry.RetrySignal {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final AtomicIntegerFieldUpdater<RetryWhenMainSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(RetryWhenMainSubscriber.class, "wip");
        Context context;

        @Nullable
        Throwable lastFailure;
        final Operators.DeferredSubscription otherArbiter;
        long produced;
        final ContextView retryContext;
        final Sinks.Many<Retry.RetrySignal> signaller;
        final CorePublisher<? extends T> source;
        long subsequentFailureIndex;
        long totalFailureIndex;
        volatile int wip;

        RetryWhenMainSubscriber(CoreSubscriber<? super T> coreSubscriber, Sinks.Many<Retry.RetrySignal> many, CorePublisher<? extends T> corePublisher, ContextView contextView) {
            super(coreSubscriber);
            this.totalFailureIndex = 0L;
            this.subsequentFailureIndex = 0L;
            this.lastFailure = null;
            this.signaller = many;
            this.source = corePublisher;
            this.otherArbiter = new Operators.DeferredSubscription();
            this.context = coreSubscriber.currentContext();
            this.retryContext = contextView;
        }

        @Override // reactor.util.retry.Retry.RetrySignal
        public long totalRetries() {
            return this.totalFailureIndex - 1;
        }

        @Override // reactor.util.retry.Retry.RetrySignal
        public long totalRetriesInARow() {
            return this.subsequentFailureIndex - 1;
        }

        @Override // reactor.util.retry.Retry.RetrySignal
        public Throwable failure() {
            return this.lastFailure;
        }

        @Override // reactor.util.retry.Retry.RetrySignal
        public ContextView retryContextView() {
            return this.retryContext;
        }

        @Override // reactor.core.publisher.InnerOperator, reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.context;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) new Scannable[]{Scannable.from(this.signaller), this.otherArbiter});
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.otherArbiter.cancel();
            super.cancel();
        }

        void swap(Subscription subscription) {
            this.otherArbiter.set(subscription);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.subsequentFailureIndex = 0L;
            this.actual.onNext((Object) t);
            this.produced++;
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.totalFailureIndex++;
            this.subsequentFailureIndex++;
            this.lastFailure = th;
            long j = this.produced;
            if (j != 0) {
                this.produced = 0L;
                produced(j);
            }
            this.signaller.emitNext(this, Sinks.EmitFailureHandler.FAIL_FAST);
            this.otherArbiter.request(1L);
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            this.lastFailure = null;
            this.otherArbiter.cancel();
            this.actual.onComplete();
        }

        void resubscribe(Object obj) {
            if (WIP.getAndIncrement(this) == 0) {
                while (!this.cancelled) {
                    if (obj instanceof ContextView) {
                        this.context = this.context.putAll((ContextView) obj);
                    }
                    this.source.subscribe((CoreSubscriber<? super Object>) this);
                    if (WIP.decrementAndGet(this) == 0) {
                        return;
                    }
                }
            }
        }

        void whenError(Throwable th) {
            super.cancel();
            this.actual.onError(th);
        }

        void whenComplete() {
            super.cancel();
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static final class RetryWhenOtherSubscriber extends Flux<Retry.RetrySignal> implements InnerConsumer<Object>, OptimizableOperator<Retry.RetrySignal, Retry.RetrySignal> {
        final Sinks.Many<Retry.RetrySignal> completionSignal = Sinks.many().multicast().onBackpressureBuffer();
        RetryWhenMainSubscriber<?> main;

        @Override // reactor.core.publisher.OptimizableOperator
        public OptimizableOperator<?, ? extends Retry.RetrySignal> nextOptimizableSource() {
            return null;
        }

        @Override // reactor.core.publisher.OptimizableOperator
        public CoreSubscriber<? super Retry.RetrySignal> subscribeOrReturn(CoreSubscriber<? super Retry.RetrySignal> coreSubscriber) {
            return coreSubscriber;
        }

        RetryWhenOtherSubscriber() {
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.main.otherArbiter : attr == Scannable.Attr.ACTUAL ? this.main : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.main.swap(subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            this.main.resubscribe(obj);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.main.whenError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.main.whenComplete();
        }

        @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
        public void subscribe(CoreSubscriber<? super Retry.RetrySignal> coreSubscriber) {
            this.completionSignal.asFlux().subscribe(coreSubscriber);
        }

        @Override // reactor.core.publisher.OptimizableOperator
        public CorePublisher<? extends Retry.RetrySignal> source() {
            return this.completionSignal.asFlux();
        }
    }
}
