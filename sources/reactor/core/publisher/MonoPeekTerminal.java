package reactor.core.publisher;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoPeekTerminal<T> extends InternalMonoOperator<T, T> implements Fuseable {
    final BiConsumer<? super T, Throwable> onAfterTerminateCall;
    final Consumer<? super Throwable> onErrorCall;
    final Consumer<? super T> onSuccessCall;

    MonoPeekTerminal(Mono<? extends T> mono, @Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable BiConsumer<? super T, Throwable> biConsumer) {
        super(mono);
        this.onAfterTerminateCall = biConsumer;
        this.onSuccessCall = consumer;
        this.onErrorCall = consumer2;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new MonoTerminalPeekSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, (MonoPeekTerminal) this);
        }
        return new MonoTerminalPeekSubscriber(coreSubscriber, this);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class MonoTerminalPeekSubscriber<T> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final CoreSubscriber<? super T> actual;
        final Fuseable.ConditionalSubscriber<? super T> actualConditional;
        volatile boolean done;
        final MonoPeekTerminal<T> parent;

        @Nullable
        Fuseable.QueueSubscription<T> queueSubscription;

        /* JADX INFO: renamed from: s */
        Subscription f2262s;
        int sourceMode;
        boolean valued;

        MonoTerminalPeekSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, MonoPeekTerminal<T> monoPeekTerminal) {
            this.actualConditional = conditionalSubscriber;
            this.actual = conditionalSubscriber;
            this.parent = monoPeekTerminal;
        }

        MonoTerminalPeekSubscriber(CoreSubscriber<? super T> coreSubscriber, MonoPeekTerminal<T> monoPeekTerminal) {
            this.actual = coreSubscriber;
            this.actualConditional = null;
            this.parent = monoPeekTerminal;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.PARENT ? this.f2262s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2262s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2262s.cancel();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.f2262s = subscription;
            this.queueSubscription = Operators.m1969as(subscription);
            this.actual.onSubscribe(this);
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
            this.valued = true;
            if (this.parent.onSuccessCall != null) {
                try {
                    this.parent.onSuccessCall.accept(t);
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2262s, th, t, this.actual.currentContext()));
                    return;
                }
            }
            this.actual.onNext(t);
            if (this.parent.onAfterTerminateCall != null) {
                try {
                    this.parent.onAfterTerminateCall.accept(t, null);
                } catch (Throwable th2) {
                    Operators.onErrorDropped(Operators.onOperatorError(this.f2262s, th2, t, this.actual.currentContext()), this.actual.currentContext());
                }
            }
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return false;
            }
            if (this.actualConditional == null) {
                onNext(t);
                return false;
            }
            this.valued = true;
            if (this.parent.onSuccessCall != null) {
                try {
                    this.parent.onSuccessCall.accept(t);
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2262s, th, t, this.actual.currentContext()));
                    return false;
                }
            }
            boolean zTryOnNext = this.actualConditional.tryOnNext(t);
            if (this.parent.onAfterTerminateCall != null) {
                try {
                    this.parent.onAfterTerminateCall.accept(t, null);
                } catch (Throwable th2) {
                    Operators.onErrorDropped(Operators.onOperatorError(this.f2262s, th2, t, this.actual.currentContext()), this.actual.currentContext());
                }
            }
            return zTryOnNext;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            Consumer<? super Throwable> consumer = this.parent.onErrorCall;
            if (!this.valued && consumer != null) {
                try {
                    consumer.accept(th);
                } catch (Throwable th2) {
                    th = Operators.onOperatorError(null, th2, th, this.actual.currentContext());
                }
            }
            try {
                this.actual.onError(th);
            } catch (UnsupportedOperationException e) {
                if (consumer == null || (!Exceptions.isErrorCallbackNotImplemented(e) && e.getCause() != th)) {
                    throw e;
                }
            }
            if (this.valued || this.parent.onAfterTerminateCall == null) {
                return;
            }
            try {
                this.parent.onAfterTerminateCall.accept(null, th);
            } catch (Throwable th3) {
                Operators.onErrorDropped(Operators.onOperatorError(th3, this.actual.currentContext()), this.actual.currentContext());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            if (this.sourceMode == 0 && !this.valued && this.parent.onSuccessCall != null) {
                try {
                    this.parent.onSuccessCall.accept(null);
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2262s, th, this.actual.currentContext()));
                    return;
                }
            }
            this.done = true;
            this.actual.onComplete();
            if (this.sourceMode != 0 || this.valued || this.parent.onAfterTerminateCall == null) {
                return;
            }
            try {
                this.parent.onAfterTerminateCall.accept(null, null);
            } catch (Throwable th2) {
                Operators.onErrorDropped(Operators.onOperatorError(th2, this.actual.currentContext()), this.actual.currentContext());
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            boolean z = this.done;
            try {
                T tPoll = this.queueSubscription.poll();
                if (!this.valued && (tPoll != null || z || this.sourceMode == 1)) {
                    this.valued = true;
                    if (this.parent.onSuccessCall != null) {
                        try {
                            this.parent.onSuccessCall.accept(tPoll);
                        } catch (Throwable th) {
                            throw Exceptions.propagate(Operators.onOperatorError(this.f2262s, th, tPoll, this.actual.currentContext()));
                        }
                    }
                }
                return tPoll;
            } catch (Throwable th2) {
                if (this.parent.onErrorCall != null) {
                    try {
                        this.parent.onErrorCall.accept(th2);
                    } catch (Throwable th3) {
                        throw Exceptions.propagate(Operators.onOperatorError(null, th2, th3, this.actual.currentContext()));
                    }
                }
                throw th2;
            }
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            Fuseable.QueueSubscription<T> queueSubscription = this.queueSubscription;
            return queueSubscription == null || queueSubscription.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.queueSubscription.clear();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            int iRequestFusion = 0;
            if (this.queueSubscription != null && this.parent.onAfterTerminateCall == null && (i & 4) == 0) {
                iRequestFusion = this.queueSubscription.requestFusion(i);
            }
            this.sourceMode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Collection
        public int size() {
            Fuseable.QueueSubscription<T> queueSubscription = this.queueSubscription;
            if (queueSubscription == null) {
                return 0;
            }
            return queueSubscription.size();
        }
    }
}
