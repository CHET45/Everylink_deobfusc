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
final class FluxSkipUntil<T> extends InternalFluxOperator<T, T> {
    final Predicate<? super T> predicate;

    FluxSkipUntil(Flux<? extends T> flux, Predicate<? super T> predicate) {
        super(flux);
        this.predicate = (Predicate) Objects.requireNonNull(predicate, "predicate");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new SkipUntilSubscriber(coreSubscriber, this.predicate);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SkipUntilSubscriber<T> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        boolean done;
        boolean doneSkipping;
        final Predicate<? super T> predicate;

        /* JADX INFO: renamed from: s */
        Subscription f2194s;

        SkipUntilSubscriber(CoreSubscriber<? super T> coreSubscriber, Predicate<? super T> predicate) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.predicate = predicate;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2194s, subscription)) {
                this.f2194s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            if (this.doneSkipping) {
                this.actual.onNext(t);
                return;
            }
            try {
                if (this.predicate.test(t)) {
                    this.doneSkipping = true;
                    this.actual.onNext(t);
                } else {
                    Operators.onDiscard(t, this.ctx);
                    this.f2194s.request(1L);
                }
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2194s, th, t, this.ctx));
            }
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return true;
            }
            if (this.doneSkipping) {
                this.actual.onNext(t);
                return true;
            }
            try {
                if (this.predicate.test(t)) {
                    this.doneSkipping = true;
                    this.actual.onNext(t);
                    return true;
                }
                Operators.onDiscard(t, this.ctx);
                return false;
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2194s, th, t, this.ctx));
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
            return attr == Scannable.Attr.PARENT ? this.f2194s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2194s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2194s.cancel();
        }
    }
}
