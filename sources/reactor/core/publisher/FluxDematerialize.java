package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDematerialize<T> extends InternalFluxOperator<Signal<T>, T> {
    FluxDematerialize(Flux<Signal<T>> flux) {
        super(flux);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super Signal<T>> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new DematerializeSubscriber(coreSubscriber, false);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class DematerializeSubscriber<T> implements InnerOperator<Signal<T>, T> {
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        final boolean completeAfterOnNext;
        boolean done;

        /* JADX INFO: renamed from: s */
        Subscription f2111s;

        DematerializeSubscriber(CoreSubscriber<? super T> coreSubscriber, boolean z) {
            this.actual = coreSubscriber;
            this.completeAfterOnNext = z;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2111s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return 0;
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2111s, subscription)) {
                this.f2111s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Signal<T> signal) {
            if (this.done) {
                Operators.onNextDropped(signal, this.actual.currentContext());
                return;
            }
            if (signal.isOnComplete()) {
                this.f2111s.cancel();
                onComplete();
            } else if (signal.isOnError()) {
                this.f2111s.cancel();
                onError(signal.getThrowable());
            } else if (signal.isOnNext()) {
                this.actual.onNext(signal.get());
                if (this.completeAfterOnNext) {
                    onComplete();
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

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                this.f2111s.request(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.f2111s.cancel();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }
}
