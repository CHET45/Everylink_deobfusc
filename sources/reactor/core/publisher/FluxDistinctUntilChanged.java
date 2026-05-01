package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDistinctUntilChanged<T, K> extends InternalFluxOperator<T, T> {
    final BiPredicate<? super K, ? super K> keyComparator;
    final Function<? super T, K> keyExtractor;

    FluxDistinctUntilChanged(Flux<? extends T> flux, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
        super(flux);
        this.keyExtractor = (Function) Objects.requireNonNull(function, "keyExtractor");
        this.keyComparator = (BiPredicate) Objects.requireNonNull(biPredicate, "keyComparator");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new DistinctUntilChangedConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.keyExtractor, this.keyComparator);
        }
        return new DistinctUntilChangedSubscriber(coreSubscriber, this.keyExtractor, this.keyComparator);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class DistinctUntilChangedSubscriber<T, K> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        boolean done;
        final BiPredicate<? super K, ? super K> keyComparator;
        final Function<? super T, K> keyExtractor;

        @Nullable
        K lastKey;

        /* JADX INFO: renamed from: s */
        Subscription f2117s;

        DistinctUntilChangedSubscriber(CoreSubscriber<? super T> coreSubscriber, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.keyExtractor = function;
            this.keyComparator = biPredicate;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2117s, subscription)) {
                this.f2117s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t)) {
                return;
            }
            this.f2117s.request(1L);
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return true;
            }
            try {
                K k = (Object) Objects.requireNonNull(this.keyExtractor.apply(t), "The distinct extractor returned a null value.");
                K k2 = this.lastKey;
                if (k2 == null) {
                    this.lastKey = k;
                    this.actual.onNext(t);
                    return true;
                }
                try {
                    if (this.keyComparator.test(k2, k)) {
                        Operators.onDiscard(t, this.ctx);
                        return false;
                    }
                    this.lastKey = k;
                    this.actual.onNext(t);
                    return true;
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2117s, th, t, this.ctx));
                    Operators.onDiscard(t, this.ctx);
                    return true;
                }
            } catch (Throwable th2) {
                onError(Operators.onOperatorError(this.f2117s, th2, t, this.ctx));
                Operators.onDiscard(t, this.ctx);
                return true;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.ctx);
                return;
            }
            this.done = true;
            this.lastKey = null;
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.lastKey = null;
            this.actual.onComplete();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2117s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2117s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2117s.cancel();
            this.lastKey = null;
        }
    }

    static final class DistinctUntilChangedConditionalSubscriber<T, K> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final Context ctx;
        boolean done;
        final BiPredicate<? super K, ? super K> keyComparator;
        final Function<? super T, K> keyExtractor;

        @Nullable
        K lastKey;

        /* JADX INFO: renamed from: s */
        Subscription f2116s;

        DistinctUntilChangedConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, Function<? super T, K> function, BiPredicate<? super K, ? super K> biPredicate) {
            this.actual = conditionalSubscriber;
            this.ctx = conditionalSubscriber.currentContext();
            this.keyExtractor = function;
            this.keyComparator = biPredicate;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2116s, subscription)) {
                this.f2116s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t)) {
                return;
            }
            this.f2116s.request(1L);
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return true;
            }
            try {
                K k = (Object) Objects.requireNonNull(this.keyExtractor.apply(t), "The distinct extractor returned a null value.");
                K k2 = this.lastKey;
                if (k2 == null) {
                    this.lastKey = k;
                    return this.actual.tryOnNext(t);
                }
                try {
                    if (this.keyComparator.test(k2, k)) {
                        Operators.onDiscard(t, this.ctx);
                        return false;
                    }
                    this.lastKey = k;
                    return this.actual.tryOnNext(t);
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2116s, th, t, this.ctx));
                    Operators.onDiscard(t, this.ctx);
                    return true;
                }
            } catch (Throwable th2) {
                onError(Operators.onOperatorError(this.f2116s, th2, t, this.ctx));
                Operators.onDiscard(t, this.ctx);
                return true;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.ctx);
                return;
            }
            this.done = true;
            this.lastKey = null;
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.lastKey = null;
            this.actual.onComplete();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2116s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2116s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2116s.cancel();
            this.lastKey = null;
        }
    }
}
