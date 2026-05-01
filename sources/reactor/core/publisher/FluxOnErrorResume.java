package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;

/* JADX INFO: loaded from: classes5.dex */
final class FluxOnErrorResume<T> extends InternalFluxOperator<T, T> {
    final Function<? super Throwable, ? extends Publisher<? extends T>> nextFactory;

    FluxOnErrorResume(Flux<? extends T> flux, Function<? super Throwable, ? extends Publisher<? extends T>> function) {
        super(flux);
        this.nextFactory = (Function) Objects.requireNonNull(function, "nextFactory");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new ResumeSubscriber(coreSubscriber, this.nextFactory);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ResumeSubscriber<T> extends Operators.MultiSubscriptionSubscriber<T, T> {
        final Function<? super Throwable, ? extends Publisher<? extends T>> nextFactory;
        boolean second;

        ResumeSubscriber(CoreSubscriber<? super T> coreSubscriber, Function<? super Throwable, ? extends Publisher<? extends T>> function) {
            super(coreSubscriber);
            this.nextFactory = function;
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (!this.second) {
                this.actual.onSubscribe(this);
            }
            set(subscription);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext((Object) t);
            if (this.second) {
                return;
            }
            producedOne();
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.second) {
                this.second = true;
                try {
                    Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.nextFactory.apply(th), "The nextFactory returned a null Publisher")).subscribe((Subscriber) this);
                    return;
                } catch (Throwable th2) {
                    this.actual.onError(Exceptions.addSuppressed(Operators.onOperatorError(th2, this.actual.currentContext()), th));
                    return;
                }
            }
            this.actual.onError(th);
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
