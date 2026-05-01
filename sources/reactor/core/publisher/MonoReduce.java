package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiFunction;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoReduce<T> extends MonoFromFluxOperator<T, T> implements Fuseable {
    final BiFunction<T, T, T> aggregator;

    MonoReduce(Flux<? extends T> flux, BiFunction<T, T, T> biFunction) {
        super(flux);
        this.aggregator = (BiFunction) Objects.requireNonNull(biFunction, "aggregator");
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new ReduceSubscriber(coreSubscriber, this.aggregator);
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ReduceSubscriber<T> implements InnerOperator<T, T>, Fuseable, Fuseable.QueueSubscription<T> {
        static final Object CANCELLED = new Object();
        final CoreSubscriber<? super T> actual;
        T aggregate;
        final BiFunction<T, T, T> aggregator;
        boolean done;

        /* JADX INFO: renamed from: s */
        Subscription f2266s;

        @Override // java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.Queue
        public T poll() {
            return null;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return 0;
        }

        @Override // java.util.Collection
        public int size() {
            return 0;
        }

        ReduceSubscriber(CoreSubscriber<? super T> coreSubscriber, BiFunction<T, T, T> biFunction) {
            this.actual = coreSubscriber;
            this.aggregator = biFunction;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            boolean z = false;
            if (attr != Scannable.Attr.CANCELLED) {
                if (attr == Scannable.Attr.PREFETCH) {
                    return 0;
                }
                return attr == Scannable.Attr.PARENT ? this.f2266s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            if (!this.done && this.aggregate == CANCELLED) {
                z = true;
            }
            return Boolean.valueOf(z);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2266s, subscription)) {
                this.f2266s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            T t2 = this.aggregate;
            Object obj = CANCELLED;
            if (t2 == obj) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            if (t2 == null) {
                synchronized (this) {
                    if (this.aggregate == null) {
                        this.aggregate = t;
                        return;
                    } else {
                        Operators.onDiscard(t, this.actual.currentContext());
                        return;
                    }
                }
            }
            try {
                synchronized (this) {
                    if (this.aggregate != obj) {
                        this.aggregate = (T) Objects.requireNonNull(this.aggregator.apply(t2, t), "The aggregator returned a null value");
                    } else {
                        Operators.onDiscard(t, this.actual.currentContext());
                    }
                }
            } catch (Throwable th) {
                this.done = true;
                Context contextCurrentContext = this.actual.currentContext();
                synchronized (this) {
                    this.aggregate = null;
                    Operators.onDiscard(t, contextCurrentContext);
                    Operators.onDiscard(t2, contextCurrentContext);
                    CoreSubscriber<? super T> coreSubscriber = this.actual;
                    coreSubscriber.onError(Operators.onOperatorError(this.f2266s, th, t, coreSubscriber.currentContext()));
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            T t;
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            synchronized (this) {
                t = this.aggregate;
                this.aggregate = null;
            }
            if (t == CANCELLED) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            if (t != null) {
                Operators.onDiscard(t, this.actual.currentContext());
            }
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            T t;
            if (this.done) {
                return;
            }
            this.done = true;
            synchronized (this) {
                t = this.aggregate;
                this.aggregate = null;
            }
            if (t == CANCELLED) {
                return;
            }
            if (t == null) {
                this.actual.onComplete();
            } else {
                this.actual.onNext(t);
                this.actual.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            T t;
            T t2;
            this.f2266s.cancel();
            synchronized (this) {
                t = this.aggregate;
                t2 = (T) CANCELLED;
                this.aggregate = t2;
            }
            if (t == null || t == t2) {
                return;
            }
            Operators.onDiscard(t, this.actual.currentContext());
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2266s.request(Long.MAX_VALUE);
        }
    }
}
