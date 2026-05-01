package reactor.core.publisher;

import io.micrometer.context.ContextSnapshot;
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
final class FluxTapRestoringThreadLocals<T, STATE> extends FluxOperator<T, T> {
    final STATE commonTapState;
    final SignalListenerFactory<T, STATE> tapFactory;

    FluxTapRestoringThreadLocals(Flux<? extends T> flux, SignalListenerFactory<T, STATE> signalListenerFactory) {
        super(flux);
        this.tapFactory = signalListenerFactory;
        this.commonTapState = signalListenerFactory.initializePublisherState(flux);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        try {
            SignalListener<T> signalListenerCreateListener = this.tapFactory.createListener(this.source, coreSubscriber.currentContext().readOnly(), this.commonTapState);
            try {
                signalListenerCreateListener.doFirst();
                try {
                    Context contextAddToContext = signalListenerCreateListener.addToContext(coreSubscriber.currentContext());
                    ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(contextAddToContext);
                    try {
                        this.source.subscribe((CoreSubscriber<? super Object>) new TapSubscriber(coreSubscriber, signalListenerCreateListener, contextAddToContext));
                        if (threadLocals != null) {
                            threadLocals.close();
                        }
                    } catch (Throwable th) {
                        if (threadLocals != null) {
                            try {
                                threadLocals.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    IllegalStateException illegalStateException = new IllegalStateException("Unable to augment tap Context at subscription via addToContext", th3);
                    signalListenerCreateListener.handleListenerError(illegalStateException);
                    Operators.error(coreSubscriber, illegalStateException);
                }
            } catch (Throwable th4) {
                signalListenerCreateListener.handleListenerError(th4);
                Operators.error(coreSubscriber, th4);
            }
        } catch (Throwable th5) {
            Operators.error(coreSubscriber, th5);
        }
    }

    @Override // reactor.core.publisher.FluxOperator, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        if (attr == InternalProducerAttr.INSTANCE) {
            return true;
        }
        return super.scanUnsafe(attr);
    }

    static class TapSubscriber<T> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Fuseable.ConditionalSubscriber<? super T> actualConditional;
        final Context context;
        boolean done;
        final SignalListener<T> listener;

        /* JADX INFO: renamed from: s */
        Subscription f2220s;

        TapSubscriber(CoreSubscriber<? super T> coreSubscriber, SignalListener<T> signalListener, Context context) {
            this.actual = coreSubscriber;
            this.listener = signalListener;
            this.context = context;
            if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
                this.actualConditional = (Fuseable.ConditionalSubscriber) coreSubscriber;
            } else {
                this.actualConditional = null;
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.context;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2220s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            if (attr == InternalProducerAttr.INSTANCE) {
                return true;
            }
            return super.scanUnsafe(attr);
        }

        protected void handleListenerErrorPreSubscription(Throwable th, Subscription subscription) {
            subscription.cancel();
            this.listener.handleListenerError(th);
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
            try {
                Operators.error(this.actual, th);
                if (threadLocals != null) {
                    threadLocals.close();
                }
            } catch (Throwable th2) {
                if (threadLocals != null) {
                    try {
                        threadLocals.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }

        protected void handleListenerErrorAndTerminate(Throwable th) {
            this.f2220s.cancel();
            this.listener.handleListenerError(th);
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
            try {
                this.actual.onError(th);
                if (threadLocals != null) {
                    threadLocals.close();
                }
            } catch (Throwable th2) {
                if (threadLocals != null) {
                    try {
                        threadLocals.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }

        protected void handleListenerErrorMultipleAndTerminate(Throwable th, Throwable th2) {
            this.f2220s.cancel();
            this.listener.handleListenerError(th);
            RuntimeException runtimeExceptionMultiple = Exceptions.multiple(th, th2);
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
            try {
                this.actual.onError(runtimeExceptionMultiple);
                if (threadLocals != null) {
                    threadLocals.close();
                }
            } catch (Throwable th3) {
                if (threadLocals != null) {
                    try {
                        threadLocals.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }

        protected void handleListenerErrorPostTermination(Throwable th) {
            this.listener.handleListenerError(th);
            Operators.onErrorDropped(th, this.actual.currentContext());
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2220s, subscription)) {
                this.f2220s = subscription;
                try {
                    this.listener.doOnSubscription();
                    ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
                    try {
                        this.actual.onSubscribe(this);
                        if (threadLocals != null) {
                            threadLocals.close();
                        }
                    } catch (Throwable th) {
                        if (threadLocals != null) {
                            try {
                                threadLocals.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    handleListenerErrorPreSubscription(th3, subscription);
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
                ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
                try {
                    this.actual.onNext(t);
                    if (threadLocals != null) {
                        threadLocals.close();
                    }
                } catch (Throwable th) {
                    if (threadLocals != null) {
                        try {
                            threadLocals.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                handleListenerErrorAndTerminate(th3);
            }
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
            try {
                Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber = this.actualConditional;
                if (conditionalSubscriber != null) {
                    if (!conditionalSubscriber.tryOnNext(t)) {
                        if (threadLocals == null) {
                            return false;
                        }
                        threadLocals.close();
                        return false;
                    }
                } else {
                    this.actual.onNext(t);
                }
                if (threadLocals != null) {
                    threadLocals.close();
                }
                try {
                    this.listener.doOnNext(t);
                    return true;
                } catch (Throwable th) {
                    handleListenerErrorAndTerminate(th);
                    return true;
                }
            } catch (Throwable th2) {
                if (threadLocals != null) {
                    try {
                        threadLocals.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                }
                throw th2;
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
                ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
                try {
                    this.actual.onError(th);
                    if (threadLocals != null) {
                        threadLocals.close();
                    }
                    try {
                        this.listener.doAfterError(th);
                        this.listener.doFinally(SignalType.ON_ERROR);
                    } catch (Throwable th2) {
                        handleListenerErrorPostTermination(th2);
                    }
                } catch (Throwable th3) {
                    if (threadLocals != null) {
                        try {
                            threadLocals.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                    }
                    throw th3;
                }
            } catch (Throwable th5) {
                handleListenerErrorMultipleAndTerminate(th5, th);
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
                ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
                try {
                    this.actual.onComplete();
                    if (threadLocals != null) {
                        threadLocals.close();
                    }
                    try {
                        this.listener.doAfterComplete();
                        this.listener.doFinally(SignalType.ON_COMPLETE);
                    } catch (Throwable th2) {
                        handleListenerErrorPostTermination(th2);
                    }
                } catch (Throwable th3) {
                    if (threadLocals != null) {
                        try {
                            threadLocals.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                    }
                    throw th3;
                }
            } catch (Throwable th5) {
                handleListenerErrorAndTerminate(th5);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.context);
            try {
                if (Operators.validate(j)) {
                    try {
                        this.listener.doOnRequest(j);
                        this.f2220s.request(j);
                    } catch (Throwable th) {
                        handleListenerErrorAndTerminate(th);
                        if (threadLocals != null) {
                            threadLocals.close();
                            return;
                        }
                        return;
                    }
                }
                if (threadLocals != null) {
                    threadLocals.close();
                }
            } catch (Throwable th2) {
                if (threadLocals != null) {
                    try {
                        threadLocals.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.context);
            try {
                try {
                    this.listener.doOnCancel();
                    try {
                        this.f2220s.cancel();
                        if (threadLocals != null) {
                            threadLocals.close();
                        }
                    } finally {
                        try {
                            this.listener.doFinally(SignalType.CANCEL);
                        } catch (Throwable th) {
                            handleListenerErrorAndTerminate(th);
                        }
                    }
                } catch (Throwable th2) {
                    if (threadLocals != null) {
                        try {
                            threadLocals.close();
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                        }
                    }
                    throw th2;
                }
            } catch (Throwable th4) {
                handleListenerErrorAndTerminate(th4);
                if (threadLocals != null) {
                    threadLocals.close();
                }
            }
        }
    }
}
