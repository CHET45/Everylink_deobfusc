package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class StrictSubscriber<T> implements Scannable, CoreSubscriber<T>, Subscription {
    final Subscriber<? super T> actual;
    volatile boolean done;
    volatile Throwable error;
    volatile long requested;

    /* JADX INFO: renamed from: s */
    volatile Subscription f2299s;
    volatile int wip;

    /* JADX INFO: renamed from: S */
    static final AtomicReferenceFieldUpdater<StrictSubscriber, Subscription> f2298S = AtomicReferenceFieldUpdater.newUpdater(StrictSubscriber.class, Subscription.class, "s");
    static final AtomicLongFieldUpdater<StrictSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(StrictSubscriber.class, "requested");
    static final AtomicIntegerFieldUpdater<StrictSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(StrictSubscriber.class, "wip");
    static final AtomicReferenceFieldUpdater<StrictSubscriber, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(StrictSubscriber.class, Throwable.class, "error");

    StrictSubscriber(Subscriber<? super T> subscriber) {
        this.actual = subscriber;
    }

    @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (Operators.validate(this.f2299s, subscription)) {
            this.actual.onSubscribe(this);
            if (Operators.setOnce(f2298S, this, subscription)) {
                long andSet = REQUESTED.getAndSet(this, 0L);
                if (andSet != 0) {
                    subscription.request(andSet);
                    return;
                }
                return;
            }
            return;
        }
        onError(new IllegalStateException("§2.12 violated: onSubscribe must be called at most once"));
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        AtomicIntegerFieldUpdater<StrictSubscriber> atomicIntegerFieldUpdater = WIP;
        if (atomicIntegerFieldUpdater.get(this) == 0 && atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
            this.actual.onNext(t);
            if (atomicIntegerFieldUpdater.decrementAndGet(this) != 0) {
                Throwable thTerminate = Exceptions.terminate(ERROR, this);
                if (thTerminate != null) {
                    this.actual.onError(thTerminate);
                } else {
                    this.actual.onComplete();
                }
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        this.done = true;
        AtomicReferenceFieldUpdater<StrictSubscriber, Throwable> atomicReferenceFieldUpdater = ERROR;
        if (Exceptions.addThrowable(atomicReferenceFieldUpdater, this, th)) {
            if (WIP.getAndIncrement(this) == 0) {
                this.actual.onError(Exceptions.terminate(atomicReferenceFieldUpdater, this));
                return;
            }
            return;
        }
        Operators.onErrorDropped(th, Context.empty());
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        this.done = true;
        if (WIP.getAndIncrement(this) == 0) {
            Throwable thTerminate = Exceptions.terminate(ERROR, this);
            if (thTerminate != null) {
                this.actual.onError(thTerminate);
            } else {
                this.actual.onComplete();
            }
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        if (j <= 0) {
            cancel();
            onError(new IllegalArgumentException("§3.9 violated: positive request amount required but it was " + j));
            return;
        }
        Subscription subscription = this.f2299s;
        if (subscription != null) {
            subscription.request(j);
            return;
        }
        AtomicLongFieldUpdater<StrictSubscriber> atomicLongFieldUpdater = REQUESTED;
        Operators.addCap(atomicLongFieldUpdater, this, j);
        Subscription subscription2 = this.f2299s;
        if (subscription2 == null || atomicLongFieldUpdater.getAndSet(this, 0L) == 0) {
            return;
        }
        subscription2.request(j);
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        if (this.done) {
            return;
        }
        Operators.terminate(f2298S, this);
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PARENT) {
            return this.f2299s;
        }
        if (attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(this.f2299s == Operators.cancelledSubscription());
        }
        if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
            return Long.valueOf(this.requested);
        }
        if (attr == Scannable.Attr.ACTUAL) {
            return this.actual;
        }
        return null;
    }

    @Override // reactor.core.CoreSubscriber
    public Context currentContext() {
        return Context.empty();
    }
}
