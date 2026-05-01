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
final class FluxHandleFuseable<T, R> extends InternalFluxOperator<T, R> implements Fuseable {
    final BiConsumer<? super T, SynchronousSink<R>> handler;

    FluxHandleFuseable(Flux<? extends T> flux, BiConsumer<? super T, SynchronousSink<R>> biConsumer) {
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
            return new HandleFuseableConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, biConsumerContextRestoreForHandle);
        }
        return new HandleFuseableSubscriber(coreSubscriber, biConsumerContextRestoreForHandle);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class HandleFuseableSubscriber<T, R> implements InnerOperator<T, R>, Fuseable.ConditionalSubscriber<T>, Fuseable.QueueSubscription<R>, SynchronousSink<R> {
        final CoreSubscriber<? super R> actual;
        R data;
        boolean done;
        Throwable error;
        final BiConsumer<? super T, SynchronousSink<R>> handler;

        /* JADX INFO: renamed from: s */
        Fuseable.QueueSubscription<T> f2138s;
        int sourceMode;
        boolean stop;

        HandleFuseableSubscriber(CoreSubscriber<? super R> coreSubscriber, BiConsumer<? super T, SynchronousSink<R>> biConsumer) {
            this.actual = coreSubscriber;
            this.handler = biConsumer;
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

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return true;
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
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2138s);
                    if (thOnNextError != null) {
                        this.done = true;
                        this.actual.onError(thOnNextError);
                    } else {
                        reset();
                        return false;
                    }
                } else {
                    this.done = true;
                    this.f2138s.cancel();
                    this.actual.onComplete();
                }
                return true;
            } catch (Throwable th2) {
                Throwable thOnNextError2 = Operators.onNextError(t, th2, this.actual.currentContext(), this.f2138s);
                if (thOnNextError2 != null) {
                    onError(thOnNextError2);
                    return true;
                }
                reset();
                return false;
            }
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2138s, subscription)) {
                this.f2138s = (Fuseable.QueueSubscription) subscription;
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
            try {
                this.handler.accept(t, this);
                R r = this.data;
                this.data = null;
                if (r != null) {
                    this.actual.onNext(r);
                }
                if (!this.stop) {
                    if (r == null) {
                        this.f2138s.request(1L);
                        return;
                    }
                    return;
                }
                Throwable th = this.error;
                if (th != null) {
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2138s);
                    if (thOnNextError != null) {
                        this.done = true;
                        this.actual.onError(thOnNextError);
                        return;
                    } else {
                        reset();
                        this.f2138s.request(1L);
                        return;
                    }
                }
                this.done = true;
                this.f2138s.cancel();
                this.actual.onComplete();
            } catch (Throwable th2) {
                Throwable thOnNextError2 = Operators.onNextError(t, th2, this.actual.currentContext(), this.f2138s);
                if (thOnNextError2 != null) {
                    onError(thOnNextError2);
                } else {
                    this.f2138s.request(1L);
                }
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

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2138s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2138s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2138s.cancel();
        }

        /* JADX WARN: Code restructure failed: missing block: B:32:0x0060, code lost:
        
            if (r4 == 0) goto L61;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x0066, code lost:
        
            return null;
         */
        @Override // java.util.Queue
        @reactor.util.annotation.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public R poll() {
            /*
                r9 = this;
                int r0 = r9.sourceMode
                r1 = 2
                r2 = 1
                r3 = 0
                if (r0 != r1) goto L67
                boolean r0 = r9.done
                if (r0 == 0) goto Lc
                return r3
            Lc:
                r0 = 0
            Le:
                r4 = r0
            Lf:
                reactor.core.Fuseable$QueueSubscription<T> r6 = r9.f2138s
                java.lang.Object r6 = r6.poll()
                if (r6 == 0) goto L5e
                java.util.function.BiConsumer<? super T, reactor.core.publisher.SynchronousSink<R>> r7 = r9.handler     // Catch: java.lang.Throwable -> L4c
                r7.accept(r6, r9)     // Catch: java.lang.Throwable -> L4c
                R r7 = r9.data
                r9.data = r3
                boolean r8 = r9.stop
                if (r8 == 0) goto L45
                java.lang.Throwable r0 = r9.error
                if (r0 == 0) goto L38
                reactor.core.CoreSubscriber<? super R> r1 = r9.actual
                reactor.util.context.Context r1 = r1.currentContext()
                java.lang.RuntimeException r0 = reactor.core.publisher.Operators.onNextPollError(r6, r0, r1)
                if (r0 != 0) goto L35
                goto L44
            L35:
                r9.done = r2
                throw r0
            L38:
                r9.done = r2
                reactor.core.Fuseable$QueueSubscription<T> r0 = r9.f2138s
                r0.cancel()
                reactor.core.CoreSubscriber<? super R> r0 = r9.actual
                r0.onComplete()
            L44:
                return r7
            L45:
                if (r7 == 0) goto L48
                return r7
            L48:
                r6 = 1
                long r4 = r4 + r6
                goto Lf
            L4c:
                r7 = move-exception
                reactor.core.CoreSubscriber<? super R> r8 = r9.actual
                reactor.util.context.Context r8 = r8.currentContext()
                java.lang.RuntimeException r6 = reactor.core.publisher.Operators.onNextPollError(r6, r7, r8)
                if (r6 != 0) goto L5d
                r9.reset()
                goto Lf
            L5d:
                throw r6
            L5e:
                int r6 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
                if (r6 == 0) goto L66
                r9.request(r4)
                goto Le
            L66:
                return r3
            L67:
                reactor.core.Fuseable$QueueSubscription<T> r0 = r9.f2138s
                java.lang.Object r0 = r0.poll()
                if (r0 == 0) goto Lab
                java.util.function.BiConsumer<? super T, reactor.core.publisher.SynchronousSink<R>> r1 = r9.handler     // Catch: java.lang.Throwable -> L99
                r1.accept(r0, r9)     // Catch: java.lang.Throwable -> L99
                R r1 = r9.data
                r9.data = r3
                boolean r4 = r9.stop
                if (r4 == 0) goto L96
                java.lang.Throwable r4 = r9.error
                if (r4 == 0) goto L93
                reactor.core.CoreSubscriber<? super R> r1 = r9.actual
                reactor.util.context.Context r1 = r1.currentContext()
                java.lang.RuntimeException r0 = reactor.core.publisher.Operators.onNextPollError(r0, r4, r1)
                if (r0 != 0) goto L90
                r9.reset()
                goto L67
            L90:
                r9.done = r2
                throw r0
            L93:
                r9.done = r2
                return r1
            L96:
                if (r1 == 0) goto L67
                return r1
            L99:
                r1 = move-exception
                reactor.core.CoreSubscriber<? super R> r4 = r9.actual
                reactor.util.context.Context r4 = r4.currentContext()
                java.lang.RuntimeException r0 = reactor.core.publisher.Operators.onNextPollError(r0, r1, r4)
                if (r0 != 0) goto Laa
                r9.reset()
                goto L67
            Laa:
                throw r0
            Lab:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.FluxHandleFuseable.HandleFuseableSubscriber.poll():java.lang.Object");
        }

        private void reset() {
            this.done = false;
            this.stop = false;
            this.error = null;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2138s.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2138s.clear();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 4) != 0) {
                return 0;
            }
            int iRequestFusion = this.f2138s.requestFusion(i);
            this.sourceMode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2138s.size();
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
    }

    static final class HandleFuseableConditionalSubscriber<T, R> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, R>, Fuseable.QueueSubscription<R>, SynchronousSink<R> {
        final Fuseable.ConditionalSubscriber<? super R> actual;
        R data;
        boolean done;
        Throwable error;
        final BiConsumer<? super T, SynchronousSink<R>> handler;

        /* JADX INFO: renamed from: s */
        Fuseable.QueueSubscription<T> f2137s;
        int sourceMode;
        boolean stop;

        HandleFuseableConditionalSubscriber(Fuseable.ConditionalSubscriber<? super R> conditionalSubscriber, BiConsumer<? super T, SynchronousSink<R>> biConsumer) {
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
            if (Operators.validate(this.f2137s, subscription)) {
                this.f2137s = (Fuseable.QueueSubscription) subscription;
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
            try {
                this.handler.accept(t, this);
                R r = this.data;
                this.data = null;
                if (r != null) {
                    this.actual.onNext(r);
                }
                if (!this.stop) {
                    if (r == null) {
                        this.f2137s.request(1L);
                        return;
                    }
                    return;
                }
                Throwable th = this.error;
                if (th != null) {
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2137s);
                    if (thOnNextError != null) {
                        this.done = true;
                        this.actual.onError(thOnNextError);
                        return;
                    } else {
                        reset();
                        this.f2137s.request(1L);
                        return;
                    }
                }
                this.done = true;
                this.f2137s.cancel();
                this.actual.onComplete();
            } catch (Throwable th2) {
                Throwable thOnNextError2 = Operators.onNextError(t, th2, this.actual.currentContext(), this.f2137s);
                if (thOnNextError2 != null) {
                    onError(thOnNextError2);
                } else {
                    reset();
                    this.f2137s.request(1L);
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
                return true;
            }
            try {
                this.handler.accept(t, this);
                R r = this.data;
                this.data = null;
                boolean zTryOnNext = r != null ? this.actual.tryOnNext(r) : false;
                if (!this.stop) {
                    return zTryOnNext;
                }
                Throwable th = this.error;
                if (th != null) {
                    Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2137s);
                    if (thOnNextError != null) {
                        this.done = true;
                        this.actual.onError(thOnNextError);
                    } else {
                        reset();
                        return false;
                    }
                } else {
                    this.done = true;
                    this.f2137s.cancel();
                    this.actual.onComplete();
                }
                return true;
            } catch (Throwable unused) {
                Throwable thOnNextError2 = Operators.onNextError(t, this.error, this.actual.currentContext(), this.f2137s);
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
            return attr == Scannable.Attr.PARENT ? this.f2137s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2137s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2137s.cancel();
        }

        /* JADX WARN: Code restructure failed: missing block: B:32:0x0069, code lost:
        
            if (r4 == 0) goto L59;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x006f, code lost:
        
            return null;
         */
        @Override // java.util.Queue
        @reactor.util.annotation.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public R poll() {
            /*
                r10 = this;
                int r0 = r10.sourceMode
                r1 = 2
                r2 = 1
                r3 = 0
                if (r0 != r1) goto L70
                boolean r0 = r10.done
                if (r0 == 0) goto Lc
                return r3
            Lc:
                r0 = 0
            Le:
                r4 = r0
            Lf:
                reactor.core.Fuseable$QueueSubscription<T> r6 = r10.f2137s
                java.lang.Object r6 = r6.poll()
                if (r6 == 0) goto L67
                java.util.function.BiConsumer<? super T, reactor.core.publisher.SynchronousSink<R>> r7 = r10.handler     // Catch: java.lang.Throwable -> L55
                r7.accept(r6, r10)     // Catch: java.lang.Throwable -> L55
                R r7 = r10.data
                r10.data = r3
                boolean r8 = r10.stop
                if (r8 == 0) goto L4e
                java.lang.Throwable r8 = r10.error
                if (r8 == 0) goto L41
                reactor.core.Fuseable$ConditionalSubscriber<? super R> r7 = r10.actual
                reactor.util.context.Context r7 = r7.currentContext()
                reactor.core.Fuseable$QueueSubscription<T> r9 = r10.f2137s
                java.lang.Throwable r6 = reactor.core.publisher.Operators.onNextError(r6, r8, r7, r9)
                if (r6 != 0) goto L3a
                r10.reset()
                goto Lf
            L3a:
                r10.done = r2
                java.lang.RuntimeException r0 = reactor.core.Exceptions.propagate(r6)
                throw r0
            L41:
                r10.done = r2
                reactor.core.Fuseable$QueueSubscription<T> r0 = r10.f2137s
                r0.cancel()
                reactor.core.Fuseable$ConditionalSubscriber<? super R> r0 = r10.actual
                r0.onComplete()
                return r7
            L4e:
                if (r7 == 0) goto L51
                return r7
            L51:
                r6 = 1
                long r4 = r4 + r6
                goto Lf
            L55:
                r7 = move-exception
                reactor.core.Fuseable$ConditionalSubscriber<? super R> r8 = r10.actual
                reactor.util.context.Context r8 = r8.currentContext()
                java.lang.RuntimeException r6 = reactor.core.publisher.Operators.onNextPollError(r6, r7, r8)
                if (r6 != 0) goto L66
                r10.reset()
                goto Lf
            L66:
                throw r6
            L67:
                int r6 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
                if (r6 == 0) goto L6f
                r10.request(r4)
                goto Le
            L6f:
                return r3
            L70:
                reactor.core.Fuseable$QueueSubscription<T> r0 = r10.f2137s
                java.lang.Object r0 = r0.poll()
                if (r0 == 0) goto Lb2
                java.util.function.BiConsumer<? super T, reactor.core.publisher.SynchronousSink<R>> r1 = r10.handler     // Catch: java.lang.Throwable -> La0
                r1.accept(r0, r10)     // Catch: java.lang.Throwable -> La0
                R r1 = r10.data
                r10.data = r3
                boolean r4 = r10.stop
                if (r4 == 0) goto L9d
                r10.done = r2
                java.lang.Throwable r4 = r10.error
                if (r4 == 0) goto L9c
                reactor.core.Fuseable$ConditionalSubscriber<? super R> r1 = r10.actual
                reactor.util.context.Context r1 = r1.currentContext()
                java.lang.RuntimeException r0 = reactor.core.publisher.Operators.onNextPollError(r0, r4, r1)
                if (r0 != 0) goto L9b
                r10.reset()
                goto L70
            L9b:
                throw r0
            L9c:
                return r1
            L9d:
                if (r1 == 0) goto L70
                return r1
            La0:
                r1 = move-exception
                reactor.core.Fuseable$ConditionalSubscriber<? super R> r4 = r10.actual
                reactor.util.context.Context r4 = r4.currentContext()
                java.lang.RuntimeException r0 = reactor.core.publisher.Operators.onNextPollError(r0, r1, r4)
                if (r0 != 0) goto Lb1
                r10.reset()
                goto L70
            Lb1:
                throw r0
            Lb2:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.FluxHandleFuseable.HandleFuseableConditionalSubscriber.poll():java.lang.Object");
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2137s.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2137s.clear();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 4) != 0) {
                return 0;
            }
            int iRequestFusion = this.f2137s.requestFusion(i);
            this.sourceMode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2137s.size();
        }
    }
}
