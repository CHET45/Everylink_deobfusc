package reactor.core.publisher;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxPeekFuseable<T> extends InternalFluxOperator<T, T> implements Fuseable, SignalPeek<T> {
    final Runnable onAfterTerminateCall;
    final Runnable onCancelCall;
    final Runnable onCompleteCall;
    final Consumer<? super Throwable> onErrorCall;
    final Consumer<? super T> onNextCall;
    final LongConsumer onRequestCall;
    final Consumer<? super Subscription> onSubscribeCall;

    FluxPeekFuseable(Flux<? extends T> flux, @Nullable Consumer<? super Subscription> consumer, @Nullable Consumer<? super T> consumer2, @Nullable Consumer<? super Throwable> consumer3, @Nullable Runnable runnable, @Nullable Runnable runnable2, @Nullable LongConsumer longConsumer, @Nullable Runnable runnable3) {
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
            return new PeekFuseableConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this);
        }
        return new PeekFuseableSubscriber(coreSubscriber, this);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class PeekFuseableSubscriber<T> implements InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        volatile boolean done;
        final SignalPeek<T> parent;

        /* JADX INFO: renamed from: s */
        Fuseable.QueueSubscription<T> f2168s;
        int sourceMode;

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2168s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        PeekFuseableSubscriber(CoreSubscriber<? super T> coreSubscriber, SignalPeek<T> signalPeek) {
            this.actual = coreSubscriber;
            this.parent = signalPeek;
        }

        @Override // reactor.core.publisher.InnerOperator, reactor.core.CoreSubscriber
        public Context currentContext() {
            Context contextCurrentContext = this.actual.currentContext();
            Consumer<? super Context> consumerOnCurrentContextCall = this.parent.onCurrentContextCall();
            if (!contextCurrentContext.isEmpty() && consumerOnCurrentContextCall != null) {
                consumerOnCurrentContextCall.accept(contextCurrentContext);
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
            this.f2168s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Runnable runnableOnCancelCall = this.parent.onCancelCall();
            if (runnableOnCancelCall != null) {
                try {
                    runnableOnCancelCall.run();
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2168s, th, this.actual.currentContext()));
                    return;
                }
            }
            this.f2168s.cancel();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2168s, subscription)) {
                Consumer<? super Subscription> consumerOnSubscribeCall = this.parent.onSubscribeCall();
                if (consumerOnSubscribeCall != null) {
                    try {
                        consumerOnSubscribeCall.accept(subscription);
                    } catch (Throwable th) {
                        Operators.error(this.actual, Operators.onOperatorError(subscription, th, this.actual.currentContext()));
                        return;
                    }
                }
                this.f2168s = (Fuseable.QueueSubscription) subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            Consumer<? super T> consumerOnNextCall = this.parent.onNextCall();
            if (consumerOnNextCall != null) {
                try {
                    consumerOnNextCall.accept(t);
                } catch (Throwable th) {
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2168s);
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
                Exceptions.throwIfFatal(th);
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
            if (this.sourceMode == 2) {
                this.done = true;
                this.actual.onComplete();
                return;
            }
            Runnable runnableOnCompleteCall = this.parent.onCompleteCall();
            if (runnableOnCompleteCall != null) {
                try {
                    runnableOnCompleteCall.run();
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2168s, th, this.actual.currentContext()));
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

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            RuntimeException runtimeExceptionPropagate;
            boolean z = this.done;
            try {
                T tPoll = this.f2168s.poll();
                Consumer<? super T> consumerOnNextCall = this.parent.onNextCall();
                if (tPoll != null && consumerOnNextCall != null) {
                    try {
                        consumerOnNextCall.accept(tPoll);
                    } catch (Throwable th) {
                        Throwable thOnNextError = Operators.onNextError(tPoll, th, this.actual.currentContext(), this.f2168s);
                        if (thOnNextError == null) {
                            return poll();
                        }
                        throw Exceptions.propagate(thOnNextError);
                    }
                }
                if (tPoll == null && (z || this.sourceMode == 1)) {
                    Runnable runnableOnCompleteCall = this.parent.onCompleteCall();
                    if (runnableOnCompleteCall != null) {
                        runnableOnCompleteCall.run();
                    }
                    Runnable runnableOnAfterTerminateCall = this.parent.onAfterTerminateCall();
                    if (runnableOnAfterTerminateCall != null) {
                        runnableOnAfterTerminateCall.run();
                    }
                }
                return tPoll;
            } catch (Throwable th2) {
                Consumer<? super Throwable> consumerOnErrorCall = this.parent.onErrorCall();
                if (consumerOnErrorCall != null) {
                    try {
                        consumerOnErrorCall.accept(th2);
                    } finally {
                    }
                }
                Runnable runnableOnAfterTerminateCall2 = this.parent.onAfterTerminateCall();
                if (runnableOnAfterTerminateCall2 != null) {
                    try {
                        runnableOnAfterTerminateCall2.run();
                    } finally {
                    }
                }
                throw Exceptions.propagate(Operators.onOperatorError(this.f2168s, th2, this.actual.currentContext()));
            }
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2168s.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2168s.clear();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 4) != 0) {
                return 0;
            }
            int iRequestFusion = this.f2168s.requestFusion(i);
            this.sourceMode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2168s.size();
        }
    }

    static final class PeekFuseableConditionalSubscriber<T> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        volatile boolean done;
        final SignalPeek<T> parent;

        /* JADX INFO: renamed from: s */
        Fuseable.QueueSubscription<T> f2167s;
        int sourceMode;

        PeekFuseableConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, SignalPeek<T> signalPeek) {
            this.actual = conditionalSubscriber;
            this.parent = signalPeek;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            Context contextCurrentContext = this.actual.currentContext();
            Consumer<? super Context> consumerOnCurrentContextCall = this.parent.onCurrentContextCall();
            if (!contextCurrentContext.isEmpty() && consumerOnCurrentContextCall != null) {
                consumerOnCurrentContextCall.accept(contextCurrentContext);
            }
            return contextCurrentContext;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2167s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
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
            this.f2167s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Runnable runnableOnCancelCall = this.parent.onCancelCall();
            if (runnableOnCancelCall != null) {
                try {
                    runnableOnCancelCall.run();
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2167s, th, this.actual.currentContext()));
                    return;
                }
            }
            this.f2167s.cancel();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2167s, subscription)) {
                Consumer<? super Subscription> consumerOnSubscribeCall = this.parent.onSubscribeCall();
                if (consumerOnSubscribeCall != null) {
                    try {
                        consumerOnSubscribeCall.accept(subscription);
                    } catch (Throwable th) {
                        Operators.error(this.actual, Operators.onOperatorError(subscription, th, this.actual.currentContext()));
                        return;
                    }
                }
                this.f2167s = (Fuseable.QueueSubscription) subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            Consumer<? super T> consumerOnNextCall = this.parent.onNextCall();
            if (consumerOnNextCall != null) {
                try {
                    consumerOnNextCall.accept(t);
                } catch (Throwable th) {
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2167s);
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

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return false;
            }
            Consumer<? super T> consumerOnNextCall = this.parent.onNextCall();
            if (consumerOnNextCall != null) {
                try {
                    consumerOnNextCall.accept(t);
                } catch (Throwable th) {
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2167s);
                    if (thOnNextError == null) {
                        return false;
                    }
                    onError(thOnNextError);
                    return true;
                }
            }
            return this.actual.tryOnNext(t);
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
                Exceptions.throwIfFatal(th);
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
            if (this.sourceMode == 2) {
                this.done = true;
                this.actual.onComplete();
                return;
            }
            Runnable runnableOnCompleteCall = this.parent.onCompleteCall();
            if (runnableOnCompleteCall != null) {
                try {
                    runnableOnCompleteCall.run();
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2167s, th, this.actual.currentContext()));
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

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            RuntimeException runtimeExceptionPropagate;
            boolean z = this.done;
            try {
                T tPoll = this.f2167s.poll();
                Consumer<? super T> consumerOnNextCall = this.parent.onNextCall();
                if (tPoll != null && consumerOnNextCall != null) {
                    try {
                        consumerOnNextCall.accept(tPoll);
                    } catch (Throwable th) {
                        Throwable thOnNextError = Operators.onNextError(tPoll, th, this.actual.currentContext(), this.f2167s);
                        if (thOnNextError == null) {
                            return poll();
                        }
                        throw Exceptions.propagate(thOnNextError);
                    }
                }
                if (tPoll == null && (z || this.sourceMode == 1)) {
                    Runnable runnableOnCompleteCall = this.parent.onCompleteCall();
                    if (runnableOnCompleteCall != null) {
                        runnableOnCompleteCall.run();
                    }
                    Runnable runnableOnAfterTerminateCall = this.parent.onAfterTerminateCall();
                    if (runnableOnAfterTerminateCall != null) {
                        runnableOnAfterTerminateCall.run();
                    }
                }
                return tPoll;
            } catch (Throwable th2) {
                Consumer<? super Throwable> consumerOnErrorCall = this.parent.onErrorCall();
                if (consumerOnErrorCall != null) {
                    try {
                        consumerOnErrorCall.accept(th2);
                    } finally {
                    }
                }
                Runnable runnableOnAfterTerminateCall2 = this.parent.onAfterTerminateCall();
                if (runnableOnAfterTerminateCall2 != null) {
                    try {
                        runnableOnAfterTerminateCall2.run();
                    } finally {
                    }
                }
                throw Exceptions.propagate(Operators.onOperatorError(this.f2167s, th2, this.actual.currentContext()));
            }
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2167s.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2167s.clear();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 4) != 0) {
                return 0;
            }
            int iRequestFusion = this.f2167s.requestFusion(i);
            this.sourceMode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2167s.size();
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

    static final class PeekConditionalSubscriber<T> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        boolean done;
        final SignalPeek<T> parent;

        /* JADX INFO: renamed from: s */
        Subscription f2166s;

        PeekConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, SignalPeek<T> signalPeek) {
            this.actual = conditionalSubscriber;
            this.parent = signalPeek;
        }

        @Override // reactor.core.CoreSubscriber
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
            this.f2166s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Runnable runnableOnCancelCall = this.parent.onCancelCall();
            if (runnableOnCancelCall != null) {
                try {
                    runnableOnCancelCall.run();
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2166s, th, this.actual.currentContext()));
                    return;
                }
            }
            this.f2166s.cancel();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2166s, subscription)) {
                Consumer<? super Subscription> consumerOnSubscribeCall = this.parent.onSubscribeCall();
                if (consumerOnSubscribeCall != null) {
                    try {
                        consumerOnSubscribeCall.accept(subscription);
                    } catch (Throwable th) {
                        Operators.error(this.actual, Operators.onOperatorError(subscription, th, this.actual.currentContext()));
                        return;
                    }
                }
                this.f2166s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2166s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
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
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2166s);
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

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return false;
            }
            Consumer<? super T> consumerOnNextCall = this.parent.onNextCall();
            if (consumerOnNextCall != null) {
                try {
                    consumerOnNextCall.accept(t);
                } catch (Throwable th) {
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2166s);
                    if (thOnNextError == null) {
                        return false;
                    }
                    onError(thOnNextError);
                    return true;
                }
            }
            return this.actual.tryOnNext(t);
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
                Exceptions.throwIfFatal(th);
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
                    onError(Operators.onOperatorError(this.f2166s, th, this.actual.currentContext()));
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
}
