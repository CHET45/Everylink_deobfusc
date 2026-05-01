package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.Function;
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

/* JADX INFO: loaded from: classes5.dex */
final class FluxRepeatWhen<T> extends InternalFluxOperator<T, T> {
    final Function<? super Flux<Long>, ? extends Publisher<?>> whenSourceFactory;

    FluxRepeatWhen(Flux<? extends T> flux, Function<? super Flux<Long>, ? extends Publisher<?>> function) {
        super(flux);
        this.whenSourceFactory = (Function) Objects.requireNonNull(function, "whenSourceFactory");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        RepeatWhenOtherSubscriber repeatWhenOtherSubscriber = new RepeatWhenOtherSubscriber();
        CoreSubscriber coreSubscriberSerialize = Operators.serialize(coreSubscriber);
        RepeatWhenMainSubscriber<?> repeatWhenMainSubscriber = new RepeatWhenMainSubscriber<>(coreSubscriberSerialize, repeatWhenOtherSubscriber.completionSignal, this.source);
        repeatWhenOtherSubscriber.main = repeatWhenMainSubscriber;
        coreSubscriberSerialize.onSubscribe(repeatWhenMainSubscriber);
        try {
            Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.whenSourceFactory.apply(repeatWhenOtherSubscriber), "The whenSourceFactory returned a null Publisher")).subscribe((Subscriber) repeatWhenOtherSubscriber);
            if (repeatWhenMainSubscriber.cancelled) {
                return null;
            }
            return repeatWhenMainSubscriber;
        } catch (Throwable th) {
            coreSubscriber.onError(Operators.onOperatorError(th, coreSubscriber.currentContext()));
            return null;
        }
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class RepeatWhenMainSubscriber<T> extends Operators.MultiSubscriptionSubscriber<T, T> {
        static final AtomicIntegerFieldUpdater<RepeatWhenMainSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(RepeatWhenMainSubscriber.class, "wip");
        Context context;
        final Operators.DeferredSubscription otherArbiter;
        long produced;
        final Sinks.Many<Long> signaller;
        final CorePublisher<? extends T> source;
        volatile int wip;

        RepeatWhenMainSubscriber(CoreSubscriber<? super T> coreSubscriber, Sinks.Many<Long> many, CorePublisher<? extends T> corePublisher) {
            super(coreSubscriber);
            this.signaller = many;
            this.source = corePublisher;
            this.otherArbiter = new Operators.DeferredSubscription();
            this.context = coreSubscriber.currentContext();
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

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext((Object) t);
            this.produced++;
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.otherArbiter.cancel();
            this.actual.onError(th);
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            long j = this.produced;
            if (j != 0) {
                this.produced = 0L;
                produced(j);
            }
            this.signaller.emitNext(Long.valueOf(j), Sinks.EmitFailureHandler.FAIL_FAST);
            this.otherArbiter.request(1L);
        }

        void setWhen(Subscription subscription) {
            this.otherArbiter.set(subscription);
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

    static final class RepeatWhenOtherSubscriber extends Flux<Long> implements InnerConsumer<Object>, OptimizableOperator<Long, Long> {
        final Sinks.Many<Long> completionSignal = Sinks.many().multicast().onBackpressureBuffer();
        RepeatWhenMainSubscriber<?> main;

        @Override // reactor.core.publisher.OptimizableOperator
        public OptimizableOperator<?, ? extends Long> nextOptimizableSource() {
            return null;
        }

        @Override // reactor.core.publisher.OptimizableOperator
        public CoreSubscriber<? super Long> subscribeOrReturn(CoreSubscriber<? super Long> coreSubscriber) {
            return coreSubscriber;
        }

        RepeatWhenOtherSubscriber() {
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
            this.main.setWhen(subscription);
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
        public void subscribe(CoreSubscriber<? super Long> coreSubscriber) {
            this.completionSignal.asFlux().subscribe(coreSubscriber);
        }

        @Override // reactor.core.publisher.OptimizableOperator
        public CorePublisher<? extends Long> source() {
            return this.completionSignal.asFlux();
        }
    }
}
