package reactor.core.publisher;

import java.util.Objects;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.observability.SignalListener;
import reactor.core.observability.SignalListenerFactory;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxTap<T, STATE> extends InternalFluxOperator<T, T> {
    final STATE commonTapState;
    final SignalListenerFactory<T, STATE> tapFactory;

    FluxTap(Flux<? extends T> flux, SignalListenerFactory<T, STATE> signalListenerFactory) {
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
                        return new TapConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, signalListenerCreateListener, contextAddToContext);
                    }
                    return new TapSubscriber(coreSubscriber, signalListenerCreateListener, contextAddToContext);
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

    static class TapSubscriber<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context context;
        boolean done;
        final SignalListener<T> listener;

        /* JADX INFO: renamed from: s */
        Subscription f2218s;

        TapSubscriber(CoreSubscriber<? super T> coreSubscriber, SignalListener<T> signalListener, Context context) {
            this.actual = coreSubscriber;
            this.listener = signalListener;
            this.context = context;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerOperator, reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.context;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2218s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        protected void handleListenerErrorPreSubscription(Throwable th, Subscription subscription) {
            subscription.cancel();
            this.listener.handleListenerError(th);
            Operators.error(this.actual, th);
        }

        protected void handleListenerErrorAndTerminate(Throwable th) {
            this.f2218s.cancel();
            this.listener.handleListenerError(th);
            this.actual.onError(th);
        }

        protected void handleListenerErrorMultipleAndTerminate(Throwable th, Throwable th2) {
            this.f2218s.cancel();
            this.listener.handleListenerError(th);
            this.actual.onError(Exceptions.multiple(th, th2));
        }

        protected void handleListenerErrorPostTermination(Throwable th) {
            this.listener.handleListenerError(th);
            Operators.onErrorDropped(th, this.actual.currentContext());
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2218s, subscription)) {
                this.f2218s = subscription;
                try {
                    this.listener.doOnSubscription();
                    this.actual.onSubscribe(this);
                } catch (Throwable th) {
                    handleListenerErrorPreSubscription(th, subscription);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                try {
                    this.listener.doOnMalformedOnNext(t);
                } finally {
                    try {
                    } finally {
                    }
                }
                return;
            }
            try {
                this.listener.doOnNext(t);
                this.actual.onNext(t);
            } catch (Throwable th) {
                handleListenerErrorAndTerminate(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                try {
                    this.listener.doOnMalformedOnError(th);
                } finally {
                    try {
                    } finally {
                    }
                }
                return;
            }
            this.done = true;
            try {
                this.listener.doOnError(th);
                this.actual.onError(th);
                try {
                    this.listener.doAfterError(th);
                    this.listener.doFinally(SignalType.ON_ERROR);
                } catch (Throwable th2) {
                    handleListenerErrorPostTermination(th2);
                }
            } catch (Throwable th3) {
                handleListenerErrorMultipleAndTerminate(th3, th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                try {
                    this.listener.doOnMalformedOnComplete();
                    return;
                } catch (Throwable th) {
                    handleListenerErrorPostTermination(th);
                    return;
                }
            }
            this.done = true;
            try {
                this.listener.doOnComplete();
                this.actual.onComplete();
                try {
                    this.listener.doAfterComplete();
                    this.listener.doFinally(SignalType.ON_COMPLETE);
                } catch (Throwable th2) {
                    handleListenerErrorPostTermination(th2);
                }
            } catch (Throwable th3) {
                handleListenerErrorAndTerminate(th3);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                try {
                    this.listener.doOnRequest(j);
                    this.f2218s.request(j);
                } catch (Throwable th) {
                    handleListenerErrorAndTerminate(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            try {
                this.listener.doOnCancel();
                try {
                    this.f2218s.cancel();
                    try {
                        this.listener.doFinally(SignalType.CANCEL);
                    } catch (Throwable th) {
                        handleListenerErrorAndTerminate(th);
                    }
                } catch (Throwable th2) {
                    try {
                        this.listener.doFinally(SignalType.CANCEL);
                    } catch (Throwable th3) {
                        handleListenerErrorAndTerminate(th3);
                    }
                    throw th2;
                }
            } catch (Throwable th4) {
                handleListenerErrorAndTerminate(th4);
            }
        }
    }

    static final class TapConditionalSubscriber<T> extends TapSubscriber<T> implements Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super T> actualConditional;

        public TapConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, SignalListener<T> signalListener, Context context) {
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
