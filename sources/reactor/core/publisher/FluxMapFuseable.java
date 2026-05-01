package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxMapFuseable<T, R> extends InternalFluxOperator<T, R> implements Fuseable {
    final Function<? super T, ? extends R> mapper;

    FluxMapFuseable(Flux<? extends T> flux, Function<? super T, ? extends R> function) {
        super(flux);
        this.mapper = (Function) Objects.requireNonNull(function, "mapper");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new MapFuseableConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.mapper);
        }
        return new MapFuseableSubscriber(coreSubscriber, this.mapper);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class MapFuseableSubscriber<T, R> implements InnerOperator<T, R>, Fuseable.QueueSubscription<R> {
        final CoreSubscriber<? super R> actual;
        boolean done;
        final Function<? super T, ? extends R> mapper;

        /* JADX INFO: renamed from: s */
        Fuseable.QueueSubscription<T> f2148s;
        int sourceMode;

        MapFuseableSubscriber(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends R> function) {
            this.actual = coreSubscriber;
            this.mapper = function;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2148s, subscription)) {
                this.f2148s = (Fuseable.QueueSubscription) subscription;
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
                R rApply = this.mapper.apply(t);
                if (rApply == null) {
                    throw new NullPointerException("The mapper [" + this.mapper.getClass().getName() + "] returned a null value.");
                }
                this.actual.onNext(rApply);
            } catch (Throwable th) {
                Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2148s);
                if (thOnNextError != null) {
                    onError(thOnNextError);
                } else {
                    this.f2148s.request(1L);
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
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2148s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2148s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2148s.cancel();
        }

        @Override // java.util.Queue
        @Nullable
        public R poll() {
            RuntimeException runtimeExceptionOnNextPollError;
            do {
                T tPoll = this.f2148s.poll();
                if (tPoll == null) {
                    return null;
                }
                try {
                    return (R) Objects.requireNonNull(this.mapper.apply(tPoll));
                } finally {
                }
            } while (runtimeExceptionOnNextPollError == null);
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2148s.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2148s.clear();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 4) != 0) {
                return 0;
            }
            int iRequestFusion = this.f2148s.requestFusion(i);
            this.sourceMode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2148s.size();
        }
    }

    static final class MapFuseableConditionalSubscriber<T, R> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, R>, Fuseable.QueueSubscription<R> {
        final Fuseable.ConditionalSubscriber<? super R> actual;
        boolean done;
        final Function<? super T, ? extends R> mapper;

        /* JADX INFO: renamed from: s */
        Fuseable.QueueSubscription<T> f2147s;
        int sourceMode;

        MapFuseableConditionalSubscriber(Fuseable.ConditionalSubscriber<? super R> conditionalSubscriber, Function<? super T, ? extends R> function) {
            this.actual = conditionalSubscriber;
            this.mapper = function;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2147s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2147s, subscription)) {
                this.f2147s = (Fuseable.QueueSubscription) subscription;
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
                R rApply = this.mapper.apply(t);
                if (rApply == null) {
                    throw new NullPointerException("The mapper [" + this.mapper.getClass().getName() + "] returned a null value.");
                }
                this.actual.onNext(rApply);
            } catch (Throwable th) {
                Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2147s);
                if (thOnNextError != null) {
                    onError(thOnNextError);
                } else {
                    this.f2147s.request(1L);
                }
            }
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return true;
            }
            try {
                R rApply = this.mapper.apply(t);
                if (rApply == null) {
                    throw new NullPointerException("The mapper [" + this.mapper.getClass().getName() + "] returned a null value.");
                }
                return this.actual.tryOnNext(rApply);
            } catch (Throwable th) {
                Throwable thOnNextError = Operators.onNextError(t, th, this.actual.currentContext(), this.f2147s);
                if (thOnNextError == null) {
                    return false;
                }
                onError(thOnNextError);
                return true;
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

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2147s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2147s.cancel();
        }

        @Override // java.util.Queue
        @Nullable
        public R poll() {
            RuntimeException runtimeExceptionOnNextPollError;
            do {
                T tPoll = this.f2147s.poll();
                if (tPoll == null) {
                    return null;
                }
                try {
                    return (R) Objects.requireNonNull(this.mapper.apply(tPoll));
                } finally {
                }
            } while (runtimeExceptionOnNextPollError == null);
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2147s.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2147s.clear();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 4) != 0) {
                return 0;
            }
            int iRequestFusion = this.f2147s.requestFusion(i);
            this.sourceMode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2147s.size();
        }
    }
}
