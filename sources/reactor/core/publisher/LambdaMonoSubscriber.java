package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class LambdaMonoSubscriber<T> implements InnerConsumer<T>, Disposable {

    /* JADX INFO: renamed from: S */
    static final AtomicReferenceFieldUpdater<LambdaMonoSubscriber, Subscription> f2246S = AtomicReferenceFieldUpdater.newUpdater(LambdaMonoSubscriber.class, Subscription.class, "subscription");
    final Runnable completeConsumer;
    final Consumer<? super T> consumer;
    final Consumer<? super Throwable> errorConsumer;
    final Context initialContext;
    volatile Subscription subscription;
    final Consumer<? super Subscription> subscriptionConsumer;

    LambdaMonoSubscriber(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable Runnable runnable, @Nullable Consumer<? super Subscription> consumer3, @Nullable Context context) {
        this.consumer = consumer;
        this.errorConsumer = consumer2;
        this.completeConsumer = runnable;
        this.subscriptionConsumer = consumer3;
        this.initialContext = context == null ? Context.empty() : context;
    }

    LambdaMonoSubscriber(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable Runnable runnable, @Nullable Consumer<? super Subscription> consumer3) {
        this(consumer, consumer2, runnable, consumer3, null);
    }

    @Override // reactor.core.CoreSubscriber
    public Context currentContext() {
        return this.initialContext;
    }

    @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription subscription) {
        if (Operators.validate(this.subscription, subscription)) {
            this.subscription = subscription;
            Consumer<? super Subscription> consumer = this.subscriptionConsumer;
            if (consumer != null) {
                try {
                    consumer.accept(subscription);
                    return;
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    subscription.cancel();
                    onError(th);
                    return;
                }
            }
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public final void onComplete() {
        Runnable runnable;
        if (f2246S.getAndSet(this, Operators.cancelledSubscription()) == Operators.cancelledSubscription() || (runnable = this.completeConsumer) == null) {
            return;
        }
        try {
            runnable.run();
        } catch (Throwable th) {
            Operators.onErrorDropped(th, this.initialContext);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public final void onError(Throwable th) {
        if (f2246S.getAndSet(this, Operators.cancelledSubscription()) == Operators.cancelledSubscription()) {
            Operators.onErrorDropped(th, this.initialContext);
        } else {
            doError(th);
        }
    }

    void doError(Throwable th) {
        Consumer<? super Throwable> consumer = this.errorConsumer;
        if (consumer != null) {
            consumer.accept(th);
        } else {
            Operators.onErrorDropped(Exceptions.errorCallbackNotImplemented(th), this.initialContext);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public final void onNext(T t) {
        Subscription andSet = f2246S.getAndSet(this, Operators.cancelledSubscription());
        if (andSet == Operators.cancelledSubscription()) {
            Operators.onNextDropped(t, this.initialContext);
            return;
        }
        Consumer<? super T> consumer = this.consumer;
        if (consumer != null) {
            try {
                consumer.accept(t);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                andSet.cancel();
                doError(th);
            }
        }
        Runnable runnable = this.completeConsumer;
        if (runnable != null) {
            try {
                runnable.run();
            } catch (Throwable th2) {
                Operators.onErrorDropped(th2, this.initialContext);
            }
        }
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PARENT) {
            return this.subscription;
        }
        if (attr == Scannable.Attr.PREFETCH) {
            return Integer.MAX_VALUE;
        }
        if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(isDisposed());
        }
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        return this.subscription == Operators.cancelledSubscription();
    }

    @Override // reactor.core.Disposable
    public void dispose() {
        Subscription andSet = f2246S.getAndSet(this, Operators.cancelledSubscription());
        if (andSet == null || andSet == Operators.cancelledSubscription()) {
            return;
        }
        andSet.cancel();
    }
}
