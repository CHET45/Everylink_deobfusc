package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Predicate;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxFilter<T> extends InternalFluxOperator<T, T> {
    final Predicate<? super T> predicate;

    FluxFilter(Flux<? extends T> flux, Predicate<? super T> predicate) {
        super(flux);
        this.predicate = (Predicate) Objects.requireNonNull(predicate, "predicate");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new FilterConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.predicate);
        }
        return new FilterSubscriber(coreSubscriber, this.predicate);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class FilterSubscriber<T> implements InnerOperator<T, T>, Fuseable.ConditionalSubscriber<T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        boolean done;
        final Predicate<? super T> predicate;

        /* JADX INFO: renamed from: s */
        Subscription f2127s;

        FilterSubscriber(CoreSubscriber<? super T> coreSubscriber, Predicate<? super T> predicate) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.predicate = predicate;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2127s, subscription)) {
                this.f2127s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            try {
                if (this.predicate.test(t)) {
                    this.actual.onNext(t);
                } else {
                    Operators.onDiscard(t, this.ctx);
                    this.f2127s.request(1L);
                }
            } catch (Throwable th) {
                Throwable thOnNextError = Operators.onNextError(t, th, this.ctx, this.f2127s);
                if (thOnNextError != null) {
                    onError(thOnNextError);
                } else {
                    this.f2127s.request(1L);
                }
                Operators.onDiscard(t, this.ctx);
            }
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return false;
            }
            try {
                boolean zTest = this.predicate.test(t);
                if (zTest) {
                    this.actual.onNext(t);
                } else {
                    Operators.onDiscard(t, this.ctx);
                }
                return zTest;
            } catch (Throwable th) {
                Throwable thOnNextError = Operators.onNextError(t, th, this.ctx, this.f2127s);
                if (thOnNextError != null) {
                    onError(thOnNextError);
                }
                Operators.onDiscard(t, this.ctx);
                return false;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.ctx);
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
            return attr == Scannable.Attr.PARENT ? this.f2127s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2127s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2127s.cancel();
        }
    }

    static final class FilterConditionalSubscriber<T> implements InnerOperator<T, T>, Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final Context ctx;
        boolean done;
        final Predicate<? super T> predicate;

        /* JADX INFO: renamed from: s */
        Subscription f2126s;

        FilterConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, Predicate<? super T> predicate) {
            this.actual = conditionalSubscriber;
            this.ctx = conditionalSubscriber.currentContext();
            this.predicate = predicate;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2126s, subscription)) {
                this.f2126s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            try {
                if (this.predicate.test(t)) {
                    this.actual.onNext(t);
                } else {
                    this.f2126s.request(1L);
                    Operators.onDiscard(t, this.ctx);
                }
            } catch (Throwable th) {
                Throwable thOnNextError = Operators.onNextError(t, th, this.ctx, this.f2126s);
                if (thOnNextError != null) {
                    onError(thOnNextError);
                } else {
                    this.f2126s.request(1L);
                }
                Operators.onDiscard(t, this.ctx);
            }
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return false;
            }
            try {
                if (this.predicate.test(t)) {
                    return this.actual.tryOnNext(t);
                }
                Operators.onDiscard(t, this.ctx);
                return false;
            } catch (Throwable th) {
                Throwable thOnNextError = Operators.onNextError(t, th, this.ctx, this.f2126s);
                if (thOnNextError != null) {
                    onError(thOnNextError);
                }
                Operators.onDiscard(t, this.ctx);
                return false;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.ctx);
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
            return attr == Scannable.Attr.PARENT ? this.f2126s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2126s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2126s.cancel();
        }
    }
}
