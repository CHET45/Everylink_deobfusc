package reactor.core.publisher;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPeekFuseable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxPeek<T> extends InternalFluxOperator<T, T> implements SignalPeek<T> {
    final Runnable onAfterTerminateCall;
    final Runnable onCancelCall;
    final Runnable onCompleteCall;
    final Consumer<? super Throwable> onErrorCall;
    final Consumer<? super T> onNextCall;
    final LongConsumer onRequestCall;
    final Consumer<? super Subscription> onSubscribeCall;

    FluxPeek(Flux<? extends T> flux, @Nullable Consumer<? super Subscription> consumer, @Nullable Consumer<? super T> consumer2, @Nullable Consumer<? super Throwable> consumer3, @Nullable Runnable runnable, @Nullable Runnable runnable2, @Nullable LongConsumer longConsumer, @Nullable Runnable runnable3) {
        super(flux);
        this.onSubscribeCall = consumer;
        this.onNextCall = consumer2;
        this.onErrorCall = consumer3;
        this.onCompleteCall = runnable;
        this.onAfterTerminateCall = runnable2;
        this.onRequestCall = longConsumer;
        this.onCancelCall = runnable3;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new FluxPeekFuseable.PeekConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this);
        }
        return new PeekSubscriber(coreSubscriber, this);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class PeekSubscriber<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        boolean done;
        final SignalPeek<T> parent;

        /* JADX INFO: renamed from: s */
        Subscription f2165s;

        PeekSubscriber(CoreSubscriber<? super T> coreSubscriber, SignalPeek<T> signalPeek) {
            this.actual = coreSubscriber;
            this.parent = signalPeek;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2165s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerOperator, reactor.core.CoreSubscriber
        public Context currentContext() {
            Context contextCurrentContext = this.actual.currentContext();
            if (!contextCurrentContext.isEmpty() && this.parent.onCurrentContextCall() != null) {
                this.parent.onCurrentContextCall().accept(contextCurrentContext);
            }
            return contextCurrentContext;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            LongConsumer longConsumerOnRequestCall = this.parent.onRequestCall();
            if (longConsumerOnRequestCall != null) {
                try {
                    longConsumerOnRequestCall.accept(j);
                } catch (Throwable th) {
                    Operators.onOperatorError(th, this.actual.currentContext());
                }
            }
            this.f2165s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Runnable runnableOnCancelCall = this.parent.onCancelCall();
            if (runnableOnCancelCall != null) {
                try {
                    runnableOnCancelCall.run();
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2165s, th, this.actual.currentContext()));
                    return;
                }
            }
            this.f2165s.cancel();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2165s, subscription)) {
                Consumer<? super Subscription> consumerOnSubscribeCall = this.parent.onSubscribeCall();
                if (consumerOnSubscribeCall != null) {
                    try {
                        consumerOnSubscribeCall.accept(subscription);
                    } catch (Throwable th) {
                        Operators.error(this.actual, Operators.onOperatorError(subscription, th, this.actual.currentContext()));
                        return;
                    }
                }
                this.f2165s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            Consumer<? super T> consumerOnNextCall = this.parent.onNextCall();
            if (consumerOnNextCall != null) {
                try {
                    consumerOnNextCall.accept(t);
                } catch (Throwable th) {
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2165s);
                    if (thOnNextError == null) {
                        request(1L);
                        return;
                    } else {
                        onError(thOnNextError);
                        return;
                    }
                }
            }
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            Consumer<? super Throwable> consumerOnErrorCall = this.parent.onErrorCall();
            if (consumerOnErrorCall != null) {
                try {
                    consumerOnErrorCall.accept(th);
                } catch (Throwable th2) {
                    th = Operators.onOperatorError(null, th2, th, this.actual.currentContext());
                }
            }
            try {
                this.actual.onError(th);
            } catch (UnsupportedOperationException e) {
                if (consumerOnErrorCall == null || (!Exceptions.isErrorCallbackNotImplemented(e) && e.getCause() != th)) {
                    throw e;
                }
            }
            Runnable runnableOnAfterTerminateCall = this.parent.onAfterTerminateCall();
            if (runnableOnAfterTerminateCall != null) {
                try {
                    runnableOnAfterTerminateCall.run();
                } catch (Throwable th3) {
                    FluxPeek.afterErrorWithFailure(this.parent, th3, th, this.actual.currentContext());
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            Runnable runnableOnCompleteCall = this.parent.onCompleteCall();
            if (runnableOnCompleteCall != null) {
                try {
                    runnableOnCompleteCall.run();
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2165s, th, this.actual.currentContext()));
                    return;
                }
            }
            this.done = true;
            this.actual.onComplete();
            Runnable runnableOnAfterTerminateCall = this.parent.onAfterTerminateCall();
            if (runnableOnAfterTerminateCall != null) {
                try {
                    runnableOnAfterTerminateCall.run();
                } catch (Throwable th2) {
                    FluxPeek.afterCompleteWithFailure(this.parent, th2, this.actual.currentContext());
                }
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Consumer<? super Subscription> onSubscribeCall() {
        return this.onSubscribeCall;
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Consumer<? super T> onNextCall() {
        return this.onNextCall;
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Consumer<? super Throwable> onErrorCall() {
        return this.onErrorCall;
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Runnable onCompleteCall() {
        return this.onCompleteCall;
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Runnable onAfterTerminateCall() {
        return this.onAfterTerminateCall;
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public LongConsumer onRequestCall() {
        return this.onRequestCall;
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Runnable onCancelCall() {
        return this.onCancelCall;
    }

    static <T> void afterCompleteWithFailure(SignalPeek<T> signalPeek, Throwable th, Context context) {
        Exceptions.throwIfFatal(th);
        Operators.onErrorDropped(Operators.onOperatorError(th, context), context);
    }

    static <T> void afterErrorWithFailure(SignalPeek<T> signalPeek, Throwable th, Throwable th2, Context context) {
        Exceptions.throwIfFatal(th);
        Operators.onErrorDropped(Operators.onOperatorError(null, th, th2, context), context);
    }
}
