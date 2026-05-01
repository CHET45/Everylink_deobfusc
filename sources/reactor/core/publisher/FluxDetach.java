package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDetach<T> extends InternalFluxOperator<T, T> {
    FluxDetach(Flux<? extends T> flux) {
        super(flux);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new DetachSubscriber(coreSubscriber);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class DetachSubscriber<T> implements InnerOperator<T, T> {
        CoreSubscriber<? super T> actual;

        /* JADX INFO: renamed from: s */
        Subscription f2112s;

        DetachSubscriber(CoreSubscriber<? super T> coreSubscriber) {
            this.actual = coreSubscriber;
        }

        @Override // reactor.core.publisher.InnerOperator, reactor.core.CoreSubscriber
        public Context currentContext() {
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            return coreSubscriber == null ? Context.empty() : coreSubscriber.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2112s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.actual == null);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.actual == null && this.f2112s == null);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2112s, subscription)) {
                this.f2112s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            if (coreSubscriber != null) {
                coreSubscriber.onNext(t);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            if (coreSubscriber != null) {
                this.actual = null;
                this.f2112s = null;
                coreSubscriber.onError(th);
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            if (coreSubscriber != null) {
                this.actual = null;
                this.f2112s = null;
                coreSubscriber.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            Subscription subscription = this.f2112s;
            if (subscription != null) {
                subscription.request(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Subscription subscription = this.f2112s;
            if (subscription != null) {
                this.actual = null;
                this.f2112s = null;
                subscription.cancel();
            }
        }
    }
}
