package reactor.core.publisher;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class BlockingOptionalMonoSubscriber<T> extends CountDownLatch implements InnerConsumer<T>, Disposable {
    volatile boolean cancelled;
    final Context context;
    Throwable error;

    /* JADX INFO: renamed from: s */
    Subscription f2080s;
    T value;

    BlockingOptionalMonoSubscriber(Context context) {
        super(1);
        this.context = context;
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (this.value == null) {
            this.value = t;
            countDown();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (this.value == null) {
            this.error = th;
        }
        countDown();
    }

    @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription subscription) {
        this.f2080s = subscription;
        if (this.cancelled) {
            return;
        }
        subscription.request(Long.MAX_VALUE);
    }

    @Override // org.reactivestreams.Subscriber
    public final void onComplete() {
        countDown();
    }

    @Override // reactor.core.CoreSubscriber
    public Context currentContext() {
        return this.context;
    }

    @Override // reactor.core.Disposable
    public final void dispose() {
        this.cancelled = true;
        Subscription subscription = this.f2080s;
        if (subscription != null) {
            this.f2080s = null;
            subscription.cancel();
        }
    }

    final Optional<T> blockingGet() {
        if (Schedulers.isInNonBlockingThread()) {
            throw new IllegalStateException("blockOptional() is blocking, which is not supported in thread " + Thread.currentThread().getName());
        }
        if (getCount() != 0) {
            try {
                await();
            } catch (InterruptedException e) {
                dispose();
                RuntimeException runtimeExceptionPropagate = Exceptions.propagate(e);
                runtimeExceptionPropagate.addSuppressed(new Exception("#blockOptional() has been interrupted"));
                Thread.currentThread().interrupt();
                throw runtimeExceptionPropagate;
            }
        }
        Throwable th = this.error;
        if (th != null) {
            RuntimeException runtimeExceptionPropagate2 = Exceptions.propagate(th);
            runtimeExceptionPropagate2.addSuppressed(new Exception("#block terminated with an error"));
            throw runtimeExceptionPropagate2;
        }
        return Optional.ofNullable(this.value);
    }

    final Optional<T> blockingGet(long j, TimeUnit timeUnit) {
        if (Schedulers.isInNonBlockingThread()) {
            throw new IllegalStateException("blockOptional() is blocking, which is not supported in thread " + Thread.currentThread().getName());
        }
        if (getCount() != 0) {
            try {
                if (!await(j, timeUnit)) {
                    dispose();
                    String str = "Timeout on blocking read for " + j + " " + timeUnit;
                    throw new IllegalStateException(str, new TimeoutException(str));
                }
            } catch (InterruptedException e) {
                dispose();
                RuntimeException runtimeExceptionPropagate = Exceptions.propagate(e);
                runtimeExceptionPropagate.addSuppressed(new Exception("#blockOptional(timeout) has been interrupted"));
                Thread.currentThread().interrupt();
                throw runtimeExceptionPropagate;
            }
        }
        Throwable th = this.error;
        if (th != null) {
            RuntimeException runtimeExceptionPropagate2 = Exceptions.propagate(th);
            runtimeExceptionPropagate2.addSuppressed(new Exception("#block terminated with an error"));
            throw runtimeExceptionPropagate2;
        }
        return Optional.ofNullable(this.value);
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.TERMINATED) {
            return Boolean.valueOf(getCount() == 0);
        }
        if (attr == Scannable.Attr.PARENT) {
            return this.f2080s;
        }
        if (attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(this.cancelled);
        }
        if (attr == Scannable.Attr.ERROR) {
            return this.error;
        }
        if (attr == Scannable.Attr.PREFETCH) {
            return Integer.MAX_VALUE;
        }
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        return this.cancelled || getCount() == 0;
    }

    @Override // reactor.core.Scannable
    public String stepName() {
        return "blockOptional";
    }
}
