package reactor.core.publisher;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoToCompletableFuture<T> extends CompletableFuture<T> implements CoreSubscriber<T> {
    final boolean cancelSourceOnNext;
    final AtomicReference<Subscription> ref = new AtomicReference<>();

    MonoToCompletableFuture(boolean z) {
        this.cancelSourceOnNext = z;
    }

    @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.Future
    public boolean cancel(boolean z) {
        Subscription andSet;
        boolean zCancel = super.cancel(z);
        if (zCancel && (andSet = this.ref.getAndSet(null)) != null) {
            andSet.cancel();
        }
        return zCancel;
    }

    @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (Operators.validate(this.ref.getAndSet(subscription), subscription)) {
            subscription.request(Long.MAX_VALUE);
        } else {
            subscription.cancel();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        Subscription andSet = this.ref.getAndSet(null);
        if (andSet != null) {
            complete(t);
            if (this.cancelSourceOnNext) {
                andSet.cancel();
                return;
            }
            return;
        }
        Operators.onNextDropped(t, currentContext());
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (this.ref.getAndSet(null) != null) {
            completeExceptionally(th);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.ref.getAndSet(null) != null) {
            complete(null);
        }
    }

    @Override // reactor.core.CoreSubscriber
    public Context currentContext() {
        return Context.empty();
    }
}
