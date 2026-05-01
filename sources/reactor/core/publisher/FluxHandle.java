package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiConsumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

/* JADX INFO: loaded from: classes5.dex */
final class FluxHandle<T, R> extends InternalFluxOperator<T, R> {
    final BiConsumer<? super T, SynchronousSink<R>> handler;

    FluxHandle(Flux<? extends T> flux, BiConsumer<? super T, SynchronousSink<R>> biConsumer) {
        super(flux);
        this.handler = (BiConsumer) Objects.requireNonNull(biConsumer, "handler");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        BiConsumer<? super T, SynchronousSink<R>> biConsumerContextRestoreForHandle;
        if (ContextPropagationSupport.shouldRestoreThreadLocalsInSomeOperators()) {
            BiConsumer<? super T, SynchronousSink<R>> biConsumer = this.handler;
            Objects.requireNonNull(coreSubscriber);
            biConsumerContextRestoreForHandle = ContextPropagation.contextRestoreForHandle(biConsumer, new FluxHandle$$ExternalSyntheticLambda0(coreSubscriber));
        } else {
            biConsumerContextRestoreForHandle = this.handler;
        }
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new HandleConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, biConsumerContextRestoreForHandle);
        }
        return new HandleSubscriber(coreSubscriber, biConsumerContextRestoreForHandle);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class HandleSubscriber<T, R> implements InnerOperator<T, R>, Fuseable.ConditionalSubscriber<T>, SynchronousSink<R> {
        final CoreSubscriber<? super R> actual;
        R data;
        boolean done;
        Throwable error;
        final BiConsumer<? super T, SynchronousSink<R>> handler;

        /* JADX INFO: renamed from: s */
        Subscription f2136s;
        boolean stop;

        HandleSubscriber(CoreSubscriber<? super R> coreSubscriber, BiConsumer<? super T, SynchronousSink<R>> biConsumer) {
            this.actual = coreSubscriber;
            this.handler = biConsumer;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2136s, subscription)) {
                this.f2136s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // reactor.core.publisher.InnerOperator, reactor.core.CoreSubscriber
        @Deprecated
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override // reactor.core.publisher.SynchronousSink
        public ContextView contextView() {
            return this.actual.currentContext();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                this.handler.accept(t, this);
                R r = this.data;
                this.data = null;
                if (r != null) {
                    this.actual.onNext(r);
                }
                if (!this.stop) {
                    if (r == null) {
                        this.f2136s.request(1L);
                        return;
                    }
                    return;
                }
                Throwable th = this.error;
                if (th != null) {
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2136s);
                    if (thOnNextError != null) {
                        onError(thOnNextError);
                        return;
                    } else {
                        reset();
                        this.f2136s.request(1L);
                        return;
                    }
                }
                this.f2136s.cancel();
                onComplete();
            } catch (Throwable th2) {
                Throwable thOnNextError2 = Operators.onNextError(t, th2, this.actual.currentContext(), this.f2136s);
                if (thOnNextError2 != null) {
                    onError(thOnNextError2);
                } else {
                    reset();
                    this.f2136s.request(1L);
                }
            }
        }

        private void reset() {
            this.done = false;
            this.stop = false;
            this.error = null;
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return false;
            }
            try {
                this.handler.accept(t, this);
                R r = this.data;
                this.data = null;
                if (r != null) {
                    this.actual.onNext(r);
                }
                if (!this.stop) {
                    return r != null;
                }
                Throwable th = this.error;
                if (th != null) {
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2136s);
                    if (thOnNextError != null) {
                        onError(thOnNextError);
                    } else {
                        reset();
                        return false;
                    }
                } else {
                    this.f2136s.cancel();
                    onComplete();
                }
                return true;
            } catch (Throwable th2) {
                Throwable thOnNextError2 = Operators.onNextError(t, th2, this.actual.currentContext(), this.f2136s);
                if (thOnNextError2 != null) {
                    onError(thOnNextError2);
                    return true;
                }
                reset();
                return false;
            }
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
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.SynchronousSink
        public void complete() {
            if (this.stop) {
                throw new IllegalStateException("Cannot complete after a complete or error");
            }
            this.stop = true;
        }

        @Override // reactor.core.publisher.SynchronousSink
        public void error(Throwable th) {
            if (this.stop) {
                throw new IllegalStateException("Cannot error after a complete or error");
            }
            this.error = (Throwable) Objects.requireNonNull(th, "error");
            this.stop = true;
        }

        @Override // reactor.core.publisher.SynchronousSink
        public void next(R r) {
            if (this.data != null) {
                throw new IllegalStateException("Cannot emit more than one data");
            }
            if (this.stop) {
                throw new IllegalStateException("Cannot emit after a complete or error");
            }
            this.data = (R) Objects.requireNonNull(r, "data");
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2136s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2136s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2136s.cancel();
        }
    }

    static final class HandleConditionalSubscriber<T, R> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, R>, SynchronousSink<R> {
        final Fuseable.ConditionalSubscriber<? super R> actual;
        R data;
        boolean done;
        Throwable error;
        final BiConsumer<? super T, SynchronousSink<R>> handler;

        /* JADX INFO: renamed from: s */
        Subscription f2135s;
        boolean stop;

        HandleConditionalSubscriber(Fuseable.ConditionalSubscriber<? super R> conditionalSubscriber, BiConsumer<? super T, SynchronousSink<R>> biConsumer) {
            this.actual = conditionalSubscriber;
            this.handler = biConsumer;
        }

        @Override // reactor.core.CoreSubscriber
        @Deprecated
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override // reactor.core.publisher.SynchronousSink
        public ContextView contextView() {
            return this.actual.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2135s, subscription)) {
                this.f2135s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                this.handler.accept(t, this);
                R r = this.data;
                this.data = null;
                if (r != null) {
                    this.actual.onNext(r);
                }
                if (!this.stop) {
                    if (r == null) {
                        this.f2135s.request(1L);
                        return;
                    }
                    return;
                }
                this.done = true;
                Throwable th = this.error;
                if (th != null) {
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2135s);
                    if (thOnNextError != null) {
                        this.actual.onError(thOnNextError);
                        return;
                    } else {
                        reset();
                        this.f2135s.request(1L);
                        return;
                    }
                }
                this.f2135s.cancel();
                this.actual.onComplete();
            } catch (Throwable th2) {
                Throwable thOnNextError2 = Operators.onNextError(t, th2, this.actual.currentContext(), this.f2135s);
                if (thOnNextError2 != null) {
                    onError(thOnNextError2);
                } else {
                    this.error = null;
                    this.f2135s.request(1L);
                }
            }
        }

        private void reset() {
            this.done = false;
            this.stop = false;
            this.error = null;
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return false;
            }
            try {
                this.handler.accept(t, this);
                R r = this.data;
                this.data = null;
                boolean zTryOnNext = r != null ? this.actual.tryOnNext(r) : false;
                if (!this.stop) {
                    return zTryOnNext;
                }
                this.done = true;
                Throwable th = this.error;
                if (th != null) {
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2135s);
                    if (thOnNextError != null) {
                        this.actual.onError(thOnNextError);
                    } else {
                        reset();
                        return false;
                    }
                } else {
                    this.f2135s.cancel();
                    this.actual.onComplete();
                }
                return true;
            } catch (Throwable th2) {
                Throwable thOnNextError2 = Operators.onNextError(t, th2, this.actual.currentContext(), this.f2135s);
                if (thOnNextError2 != null) {
                    onError(thOnNextError2);
                    return true;
                }
                reset();
                return false;
            }
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
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.SynchronousSink
        public void complete() {
            if (this.stop) {
                throw new IllegalStateException("Cannot complete after a complete or error");
            }
            this.stop = true;
        }

        @Override // reactor.core.publisher.SynchronousSink
        public void error(Throwable th) {
            if (this.stop) {
                throw new IllegalStateException("Cannot error after a complete or error");
            }
            this.error = (Throwable) Objects.requireNonNull(th, "error");
            this.stop = true;
        }

        @Override // reactor.core.publisher.SynchronousSink
        public void next(R r) {
            if (this.data != null) {
                throw new IllegalStateException("Cannot emit more than one data");
            }
            if (this.stop) {
                throw new IllegalStateException("Cannot emit after a complete or error");
            }
            this.data = (R) Objects.requireNonNull(r, "data");
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2135s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2135s.cancel();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2135s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
