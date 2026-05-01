package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDoOnEach<T> extends InternalFluxOperator<T, T> {
    final Consumer<? super Signal<T>> onSignal;

    FluxDoOnEach(Flux<? extends T> flux, Consumer<? super Signal<T>> consumer) {
        super(flux);
        this.onSignal = (Consumer) Objects.requireNonNull(consumer, "onSignal");
    }

    static <T> DoOnEachSubscriber<T> createSubscriber(CoreSubscriber<? super T> coreSubscriber, Consumer<? super Signal<T>> consumer, boolean z, boolean z2) {
        if (z) {
            if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
                return new DoOnEachFuseableConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, consumer, z2);
            }
            return new DoOnEachFuseableSubscriber(coreSubscriber, consumer, z2);
        }
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new DoOnEachConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, consumer, z2);
        }
        return new DoOnEachSubscriber<>(coreSubscriber, consumer, z2);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return createSubscriber(coreSubscriber, this.onSignal, false, false);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static class DoOnEachSubscriber<T> implements InnerOperator<T, T>, Signal<T> {
        static final short STATE_DONE = 3;
        static final short STATE_FLUX_START = 0;
        static final short STATE_MONO_START = 1;
        static final short STATE_SKIP_HANDLER = 2;
        final CoreSubscriber<? super T> actual;
        final Context cachedContext;
        final Consumer<? super Signal<T>> onSignal;

        /* JADX INFO: renamed from: qs */
        @Nullable
        Fuseable.QueueSubscription<T> f2119qs;

        /* JADX INFO: renamed from: s */
        Subscription f2120s;
        short state;

        /* JADX INFO: renamed from: t */
        T f2121t;

        @Override // reactor.core.publisher.Signal
        @Nullable
        public Subscription getSubscription() {
            return null;
        }

        @Override // reactor.core.publisher.Signal
        @Nullable
        public Throwable getThrowable() {
            return null;
        }

        DoOnEachSubscriber(CoreSubscriber<? super T> coreSubscriber, Consumer<? super Signal<T>> consumer, boolean z) {
            this.actual = coreSubscriber;
            this.cachedContext = coreSubscriber.currentContext();
            this.onSignal = consumer;
            this.state = z ? (short) 1 : (short) 0;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2120s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2120s.cancel();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2120s, subscription)) {
                this.f2120s = subscription;
                this.f2119qs = Operators.m1969as(subscription);
                this.actual.onSubscribe(this);
            }
        }

        @Override // reactor.core.publisher.InnerOperator, reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.cachedContext;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2120s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.state == 3);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.state == 3) {
                Operators.onNextDropped(t, this.cachedContext);
                return;
            }
            try {
                this.f2121t = t;
                this.onSignal.accept(this);
                if (this.state == 1) {
                    this.state = (short) 2;
                    try {
                        this.onSignal.accept(Signal.complete(this.cachedContext));
                    } catch (Throwable th) {
                        this.state = (short) 1;
                        onError(Operators.onOperatorError(this.f2120s, th, this.cachedContext));
                        return;
                    }
                }
                this.actual.onNext(t);
            } catch (Throwable th2) {
                onError(Operators.onOperatorError(this.f2120s, th2, t, this.cachedContext));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            short s = this.state;
            if (s == 3) {
                Operators.onErrorDropped(th, this.cachedContext);
                return;
            }
            boolean z = s < 2;
            this.state = (short) 3;
            if (z) {
                try {
                    this.onSignal.accept(Signal.error(th, this.cachedContext));
                } catch (Throwable th2) {
                    th = Operators.onOperatorError(null, th2, th, this.cachedContext);
                }
            }
            try {
                this.actual.onError(th);
            } catch (UnsupportedOperationException e) {
                if (!Exceptions.isErrorCallbackNotImplemented(e) && e.getCause() != th) {
                    throw e;
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            short s = this.state;
            if (s == 3) {
                return;
            }
            this.state = (short) 3;
            if (s < 2) {
                try {
                    this.onSignal.accept(Signal.complete(this.cachedContext));
                } catch (Throwable th) {
                    this.state = s;
                    onError(Operators.onOperatorError(this.f2120s, th, this.cachedContext));
                    return;
                }
            }
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.Signal, java.util.function.Supplier
        @Nullable
        public T get() {
            return this.f2121t;
        }

        @Override // reactor.core.publisher.Signal
        public ContextView getContextView() {
            return this.cachedContext;
        }

        @Override // reactor.core.publisher.Signal
        public SignalType getType() {
            return SignalType.ON_NEXT;
        }

        public String toString() {
            return "doOnEach_onNext(" + this.f2121t + ")";
        }
    }

    static class DoOnEachFuseableSubscriber<T> extends DoOnEachSubscriber<T> implements Fuseable, Fuseable.QueueSubscription<T> {
        int fusionMode;

        DoOnEachFuseableSubscriber(CoreSubscriber<? super T> coreSubscriber, Consumer<? super Signal<T>> consumer, boolean z) {
            super(coreSubscriber, consumer, z);
        }

        @Override // reactor.core.publisher.FluxDoOnEach.DoOnEachSubscriber, org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.fusionMode == 2) {
                this.actual.onNext(null);
            } else {
                super.onNext(t);
            }
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            int iRequestFusion;
            Fuseable.QueueSubscription<T> queueSubscription = this.f2119qs;
            if (queueSubscription != null && (i & 4) == 0 && ((iRequestFusion = queueSubscription.requestFusion(i)) == 1 || iRequestFusion == 2)) {
                this.fusionMode = iRequestFusion;
                return iRequestFusion;
            }
            this.fusionMode = 0;
            return 0;
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2119qs.clear();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2119qs == null || this.f2119qs.isEmpty();
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            if (this.f2119qs == null) {
                return null;
            }
            T tPoll = this.f2119qs.poll();
            if (tPoll == null && this.fusionMode == 1) {
                this.state = (short) 3;
                this.onSignal.accept(Signal.complete(this.cachedContext));
            } else if (tPoll != null) {
                this.f2121t = tPoll;
                this.onSignal.accept(this);
            }
            return tPoll;
        }

        @Override // java.util.Collection
        public int size() {
            if (this.f2119qs == null) {
                return 0;
            }
            return this.f2119qs.size();
        }
    }

    static final class DoOnEachConditionalSubscriber<T> extends DoOnEachSubscriber<T> implements Fuseable.ConditionalSubscriber<T> {
        DoOnEachConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, Consumer<? super Signal<T>> consumer, boolean z) {
            super(conditionalSubscriber, consumer, z);
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            boolean zTryOnNext = ((Fuseable.ConditionalSubscriber) this.actual).tryOnNext(t);
            if (zTryOnNext) {
                this.f2121t = t;
                this.onSignal.accept(this);
            }
            return zTryOnNext;
        }
    }

    static final class DoOnEachFuseableConditionalSubscriber<T> extends DoOnEachFuseableSubscriber<T> implements Fuseable.ConditionalSubscriber<T> {
        DoOnEachFuseableConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, Consumer<? super Signal<T>> consumer, boolean z) {
            super(conditionalSubscriber, consumer, z);
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            boolean zTryOnNext = ((Fuseable.ConditionalSubscriber) this.actual).tryOnNext(t);
            if (zTryOnNext) {
                this.f2121t = t;
                this.onSignal.accept(this);
            }
            return zTryOnNext;
        }
    }
}
