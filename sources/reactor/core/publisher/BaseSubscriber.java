package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;

/* JADX INFO: loaded from: classes5.dex */
public abstract class BaseSubscriber<T> implements CoreSubscriber<T>, Subscription, Disposable {

    /* JADX INFO: renamed from: S */
    static AtomicReferenceFieldUpdater<BaseSubscriber, Subscription> f2077S = AtomicReferenceFieldUpdater.newUpdater(BaseSubscriber.class, Subscription.class, "subscription");
    volatile Subscription subscription;

    protected void hookFinally(SignalType signalType) {
    }

    protected void hookOnCancel() {
    }

    protected void hookOnComplete() {
    }

    protected void hookOnNext(T t) {
    }

    protected Subscription upstream() {
        return this.subscription;
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        return this.subscription == Operators.cancelledSubscription();
    }

    @Override // reactor.core.Disposable
    public void dispose() {
        cancel();
    }

    protected void hookOnSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    protected void hookOnError(Throwable th) {
        throw Exceptions.errorCallbackNotImplemented(th);
    }

    @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription subscription) {
        if (Operators.setOnce(f2077S, this, subscription)) {
            try {
                hookOnSubscribe(subscription);
            } catch (Throwable th) {
                onError(Operators.onOperatorError(subscription, th, currentContext()));
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public final void onNext(T t) {
        Objects.requireNonNull(t, "onNext");
        try {
            hookOnNext(t);
        } catch (Throwable th) {
            onError(Operators.onOperatorError(this.subscription, th, t, currentContext()));
        }
    }

    @Override // org.reactivestreams.Subscriber
    public final void onError(Throwable th) {
        Objects.requireNonNull(th, "onError");
        if (f2077S.getAndSet(this, Operators.cancelledSubscription()) == Operators.cancelledSubscription()) {
            Operators.onErrorDropped(th, currentContext());
        } else {
            try {
                hookOnError(th);
            } finally {
                try {
                } finally {
                }
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public final void onComplete() {
        if (f2077S.getAndSet(this, Operators.cancelledSubscription()) != Operators.cancelledSubscription()) {
            try {
                hookOnComplete();
            } finally {
                try {
                } finally {
                }
            }
        }
    }

    @Override // org.reactivestreams.Subscription
    public final void request(long j) {
        Subscription subscription;
        if (!Operators.validate(j) || (subscription = this.subscription) == null) {
            return;
        }
        subscription.request(j);
    }

    public final void requestUnbounded() {
        request(Long.MAX_VALUE);
    }

    @Override // org.reactivestreams.Subscription
    public final void cancel() {
        if (Operators.terminate(f2077S, this)) {
            try {
                hookOnCancel();
            } finally {
                try {
                } finally {
                }
            }
        }
    }

    void safeHookFinally(SignalType signalType) {
        try {
            hookFinally(signalType);
        } catch (Throwable th) {
            Operators.onErrorDropped(th, currentContext());
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
