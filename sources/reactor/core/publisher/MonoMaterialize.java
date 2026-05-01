package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoMaterialize<T> extends InternalMonoOperator<T, Signal<T>> {
    MonoMaterialize(Mono<T> mono) {
        super(mono);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Signal<T>> coreSubscriber) {
        return new MaterializeSubscriber(coreSubscriber);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class MaterializeSubscriber<T> implements InnerOperator<T, Signal<T>> {
        final CoreSubscriber<? super Signal<T>> actual;
        boolean alreadyReceivedSignalFromSource;
        volatile boolean requested;

        /* JADX INFO: renamed from: s */
        Subscription f2258s;

        @Nullable
        volatile Signal<T> signalToReplayUponFirstRequest;

        MaterializeSubscriber(CoreSubscriber<? super Signal<T>> coreSubscriber) {
            this.actual = coreSubscriber;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Signal<T>> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2258s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2258s, subscription)) {
                this.f2258s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.alreadyReceivedSignalFromSource || !this.requested) {
                Operators.onNextDropped(t, currentContext());
                return;
            }
            this.alreadyReceivedSignalFromSource = true;
            this.actual.onNext(Signal.next(t, currentContext()));
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.alreadyReceivedSignalFromSource) {
                Operators.onErrorDropped(th, currentContext());
                return;
            }
            this.alreadyReceivedSignalFromSource = true;
            this.signalToReplayUponFirstRequest = Signal.error(th, currentContext());
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.alreadyReceivedSignalFromSource) {
                return;
            }
            this.alreadyReceivedSignalFromSource = true;
            this.signalToReplayUponFirstRequest = Signal.complete(currentContext());
            drain();
        }

        boolean drain() {
            Signal<T> signal = this.signalToReplayUponFirstRequest;
            if (signal == null || !this.requested) {
                return false;
            }
            this.actual.onNext(signal);
            this.actual.onComplete();
            this.signalToReplayUponFirstRequest = null;
            return true;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.requested || !Operators.validate(j)) {
                return;
            }
            this.requested = true;
            if (drain()) {
                return;
            }
            this.f2258s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2258s.cancel();
        }
    }
}
