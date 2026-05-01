package reactor.core.publisher;

import java.util.Objects;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.observability.SignalListener;
import reactor.core.observability.SignalListenerFactory;
import reactor.core.publisher.FluxTap;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxTapFuseable<T, STATE> extends InternalFluxOperator<T, T> implements Fuseable {
    final STATE commonTapState;
    final SignalListenerFactory<T, STATE> tapFactory;

    FluxTapFuseable(Flux<? extends T> flux, SignalListenerFactory<T, STATE> signalListenerFactory) {
        super(flux);
        this.tapFactory = signalListenerFactory;
        this.commonTapState = signalListenerFactory.initializePublisherState(flux);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) throws Throwable {
        try {
            SignalListener<T> signalListenerCreateListener = this.tapFactory.createListener(this.source, coreSubscriber.currentContext().readOnly(), this.commonTapState);
            if (ContextPropagationSupport.isContextPropagationAvailable()) {
                Objects.requireNonNull(coreSubscriber);
                signalListenerCreateListener = ContextPropagation.contextRestoreForTap(signalListenerCreateListener, new FluxHandle$$ExternalSyntheticLambda0(coreSubscriber));
            }
            try {
                signalListenerCreateListener.doFirst();
                try {
                    Context contextAddToContext = signalListenerCreateListener.addToContext(coreSubscriber.currentContext());
                    if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
                        return new TapConditionalFuseableSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, signalListenerCreateListener, contextAddToContext);
                    }
                    return new TapFuseableSubscriber(coreSubscriber, signalListenerCreateListener, contextAddToContext);
                } catch (Throwable th) {
                    IllegalStateException illegalStateException = new IllegalStateException("Unable to augment tap Context at subscription via addToContext", th);
                    signalListenerCreateListener.handleListenerError(illegalStateException);
                    Operators.error(coreSubscriber, illegalStateException);
                    return null;
                }
            } catch (Throwable th2) {
                signalListenerCreateListener.handleListenerError(th2);
                Operators.error(coreSubscriber, th2);
                return null;
            }
        } catch (Throwable th3) {
            Operators.error(coreSubscriber, th3);
            return null;
        }
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static class TapFuseableSubscriber<T> extends FluxTap.TapSubscriber<T> implements Fuseable.QueueSubscription<T> {
        int mode;

        /* JADX INFO: renamed from: qs */
        Fuseable.QueueSubscription<T> f2219qs;

        TapFuseableSubscriber(CoreSubscriber<? super T> coreSubscriber, SignalListener<T> signalListener, Context context) {
            super(coreSubscriber, signalListener, context);
        }

        protected RuntimeException handleObserverErrorInPoll(Throwable th) {
            this.f2219qs.cancel();
            this.listener.handleListenerError(th);
            return Exceptions.propagate(th);
        }

        protected RuntimeException handleObserverErrorMultipleInPoll(Throwable th, RuntimeException runtimeException) {
            this.f2219qs.cancel();
            this.listener.handleListenerError(th);
            return Exceptions.multiple(th, runtimeException);
        }

        @Override // reactor.core.publisher.FluxTap.TapSubscriber, reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2218s, subscription)) {
                if (!(subscription instanceof Fuseable.QueueSubscription)) {
                    handleListenerErrorPreSubscription(new IllegalStateException("Fuseable subscriber but no QueueSubscription"), subscription);
                    return;
                }
                this.f2218s = subscription;
                this.f2219qs = (Fuseable.QueueSubscription) subscription;
                try {
                    this.listener.doOnSubscription();
                    this.actual.onSubscribe(this);
                } catch (Throwable th) {
                    handleListenerErrorPreSubscription(th, subscription);
                }
            }
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2219qs;
            if (queueSubscription == null) {
                this.mode = 0;
                return 0;
            }
            this.mode = queueSubscription.requestFusion(i);
            try {
                this.listener.doOnFusion(this.mode);
                return this.mode;
            } catch (Throwable th) {
                int i2 = this.mode;
                if (i2 == 2 || i2 == 0) {
                    handleListenerErrorAndTerminate(th);
                    return 0;
                }
                this.listener.handleListenerError(th);
                return this.mode;
            }
        }

        @Override // reactor.core.publisher.FluxTap.TapSubscriber, org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.mode == 2) {
                this.actual.onNext(null);
            } else {
                super.onNext(t);
            }
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            RuntimeException runtimeExceptionHandleObserverErrorMultipleInPoll;
            RuntimeException runtimeExceptionHandleObserverErrorInPoll;
            Fuseable.QueueSubscription<T> queueSubscription = this.f2219qs;
            if (queueSubscription == null) {
                return null;
            }
            try {
                T tPoll = queueSubscription.poll();
                if (tPoll == null && (this.done || this.mode == 1)) {
                    try {
                        this.listener.doOnComplete();
                        try {
                            this.listener.doFinally(SignalType.ON_COMPLETE);
                            return null;
                        } finally {
                        }
                    } finally {
                    }
                }
                if (tPoll != null) {
                    try {
                        this.listener.doOnNext(tPoll);
                    } catch (Throwable th) {
                        if (this.mode == 1) {
                            throw handleObserverErrorInPoll(th);
                        }
                        handleListenerErrorAndTerminate(th);
                        return null;
                    }
                }
                return tPoll;
            } catch (RuntimeException e) {
                try {
                    this.listener.doOnError(e);
                    try {
                        this.listener.doFinally(SignalType.ON_ERROR);
                        throw e;
                    } finally {
                    }
                } finally {
                }
            }
        }

        @Override // java.util.Collection
        public int size() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2219qs;
            if (queueSubscription == null) {
                return 0;
            }
            return queueSubscription.size();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2219qs;
            return queueSubscription == null || queueSubscription.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2219qs;
            if (queueSubscription != null) {
                queueSubscription.clear();
            }
        }
    }

    static final class TapConditionalFuseableSubscriber<T> extends TapFuseableSubscriber<T> implements Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super T> actualConditional;

        public TapConditionalFuseableSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, SignalListener<T> signalListener, Context context) {
            super(conditionalSubscriber, signalListener, context);
            this.actualConditional = conditionalSubscriber;
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (!this.actualConditional.tryOnNext(t)) {
                return false;
            }
            try {
                this.listener.doOnNext(t);
                return true;
            } catch (Throwable th) {
                handleListenerErrorAndTerminate(th);
                return true;
            }
        }
    }
}
