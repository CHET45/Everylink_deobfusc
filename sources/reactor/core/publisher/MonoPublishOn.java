package reactor.core.publisher;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoPublishOn<T> extends InternalMonoOperator<T, T> {
    final Scheduler scheduler;

    MonoPublishOn(Mono<? extends T> mono, Scheduler scheduler) {
        super(mono);
        this.scheduler = scheduler;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new PublishOnSubscriber(coreSubscriber, this.scheduler);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
    }

    static final class PublishOnSubscriber<T> implements InnerOperator<T, T>, Runnable {
        static final AtomicReferenceFieldUpdater<PublishOnSubscriber, Disposable> FUTURE = AtomicReferenceFieldUpdater.newUpdater(PublishOnSubscriber.class, Disposable.class, "future");
        static final AtomicReferenceFieldUpdater<PublishOnSubscriber, Object> VALUE = AtomicReferenceFieldUpdater.newUpdater(PublishOnSubscriber.class, Object.class, "value");
        final CoreSubscriber<? super T> actual;
        volatile Throwable error;
        volatile Disposable future;

        /* JADX INFO: renamed from: s */
        Subscription f2265s;
        final Scheduler scheduler;
        volatile T value;

        PublishOnSubscriber(CoreSubscriber<? super T> coreSubscriber, Scheduler scheduler) {
            this.actual = coreSubscriber;
            this.scheduler = scheduler;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.future == OperatorDisposables.DISPOSED);
            }
            return attr == Scannable.Attr.PARENT ? this.f2265s : attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2265s, subscription)) {
                this.f2265s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.value = t;
            trySchedule(this, null, t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            trySchedule(null, th, null);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.value == null) {
                trySchedule(null, null, null);
            }
        }

        void trySchedule(@Nullable Subscription subscription, @Nullable Throwable th, @Nullable Object obj) {
            if (this.future != null) {
                return;
            }
            try {
                this.future = this.scheduler.schedule(this);
            } catch (RejectedExecutionException e) {
                CoreSubscriber<? super T> coreSubscriber = this.actual;
                coreSubscriber.onError(Operators.onRejectedExecution(e, subscription, th, obj, coreSubscriber.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2265s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.future != OperatorDisposables.DISPOSED) {
                Disposable andSet = FUTURE.getAndSet(this, OperatorDisposables.DISPOSED);
                if (andSet != null && !OperatorDisposables.isDisposed(andSet)) {
                    andSet.dispose();
                }
                this.value = null;
            }
            this.f2265s.cancel();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (OperatorDisposables.isDisposed(this.future)) {
                return;
            }
            Object andSet = VALUE.getAndSet(this, null);
            if (andSet != null) {
                this.actual.onNext(andSet);
                this.actual.onComplete();
                return;
            }
            Throwable th = this.error;
            if (th != null) {
                this.actual.onError(th);
            } else {
                this.actual.onComplete();
            }
        }
    }
}
