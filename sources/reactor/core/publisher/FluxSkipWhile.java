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
final class FluxSkipWhile<T> extends InternalFluxOperator<T, T> {
    final Predicate<? super T> predicate;

    FluxSkipWhile(Flux<? extends T> flux, Predicate<? super T> predicate) {
        super(flux);
        this.predicate = (Predicate) Objects.requireNonNull(predicate, "predicate");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new SkipWhileSubscriber(coreSubscriber, this.predicate);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SkipWhileSubscriber<T> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        boolean done;
        final Predicate<? super T> predicate;

        /* JADX INFO: renamed from: s */
        Subscription f2195s;
        boolean skipped;

        SkipWhileSubscriber(CoreSubscriber<? super T> coreSubscriber, Predicate<? super T> predicate) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.predicate = predicate;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2195s, subscription)) {
                this.f2195s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            if (this.skipped) {
                this.actual.onNext(t);
                return;
            }
            try {
                if (this.predicate.test(t)) {
                    Operators.onDiscard(t, this.ctx);
                    this.f2195s.request(1L);
                } else {
                    this.skipped = true;
                    this.actual.onNext(t);
                }
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2195s, th, t, this.ctx));
            }
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return true;
            }
            if (this.skipped) {
                this.actual.onNext(t);
                return true;
            }
            try {
                if (this.predicate.test(t)) {
                    Operators.onDiscard(t, this.ctx);
                    return false;
                }
                this.skipped = true;
                this.actual.onNext(t);
                return true;
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2195s, th, t, this.ctx));
                return true;
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
            return attr == Scannable.Attr.PARENT ? this.f2195s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2195s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2195s.cancel();
        }
    }
}
