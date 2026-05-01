package reactor.core.publisher;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSubscribeOn<T> extends InternalMonoOperator<T, T> {
    final Scheduler scheduler;

    MonoSubscribeOn(Mono<? extends T> mono, Scheduler scheduler) {
        super(mono);
        this.scheduler = scheduler;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        Scheduler.Worker workerCreateWorker = this.scheduler.createWorker();
        SubscribeOnSubscriber subscribeOnSubscriber = new SubscribeOnSubscriber(this.source, coreSubscriber, workerCreateWorker);
        coreSubscriber.onSubscribe(subscribeOnSubscriber);
        try {
            workerCreateWorker.schedule(subscribeOnSubscriber);
        } catch (RejectedExecutionException e) {
            if (subscribeOnSubscriber.f2273s != Operators.cancelledSubscription()) {
                coreSubscriber.onError(Operators.onRejectedExecution(e, subscribeOnSubscriber, null, null, coreSubscriber.currentContext()));
            }
        }
        return null;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
    }

    static final class SubscribeOnSubscriber<T> implements InnerOperator<T, T>, Runnable {
        final CoreSubscriber<? super T> actual;
        final Publisher<? extends T> parent;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2273s;
        volatile Thread thread;
        final Scheduler.Worker worker;

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<SubscribeOnSubscriber, Subscription> f2272S = AtomicReferenceFieldUpdater.newUpdater(SubscribeOnSubscriber.class, Subscription.class, "s");
        static final AtomicLongFieldUpdater<SubscribeOnSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(SubscribeOnSubscriber.class, "requested");
        static final AtomicReferenceFieldUpdater<SubscribeOnSubscriber, Thread> THREAD = AtomicReferenceFieldUpdater.newUpdater(SubscribeOnSubscriber.class, Thread.class, "thread");

        SubscribeOnSubscriber(Publisher<? extends T> publisher, CoreSubscriber<? super T> coreSubscriber, Scheduler.Worker worker) {
            this.actual = coreSubscriber;
            this.parent = publisher;
            this.worker = worker;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2273s == Operators.cancelledSubscription());
            }
            return attr == Scannable.Attr.PARENT ? this.f2273s : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.RUN_ON ? this.worker : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
        }

        @Override // java.lang.Runnable
        public void run() {
            THREAD.lazySet(this, Thread.currentThread());
            this.parent.subscribe(this);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2272S, this, subscription)) {
                long andSet = REQUESTED.getAndSet(this, 0L);
                if (andSet != 0) {
                    trySchedule(andSet, subscription);
                }
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            try {
                this.actual.onError(th);
            } finally {
                this.worker.dispose();
                THREAD.lazySet(this, null);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
            this.worker.dispose();
            THREAD.lazySet(this, null);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Subscription subscription = this.f2273s;
                if (subscription != null) {
                    trySchedule(j, subscription);
                    return;
                }
                AtomicLongFieldUpdater<SubscribeOnSubscriber> atomicLongFieldUpdater = REQUESTED;
                Operators.addCap(atomicLongFieldUpdater, this, j);
                Subscription subscription2 = this.f2273s;
                if (subscription2 == null || atomicLongFieldUpdater.getAndSet(this, 0L) == 0) {
                    return;
                }
                trySchedule(j, subscription2);
            }
        }

        void trySchedule(final long j, final Subscription subscription) {
            if (Thread.currentThread() == THREAD.get(this)) {
                subscription.request(j);
                return;
            }
            try {
                this.worker.schedule(new Runnable() { // from class: reactor.core.publisher.MonoSubscribeOn$SubscribeOnSubscriber$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        subscription.request(j);
                    }
                });
            } catch (RejectedExecutionException e) {
                if (this.worker.isDisposed()) {
                    return;
                }
                CoreSubscriber<? super T> coreSubscriber = this.actual;
                coreSubscriber.onError(Operators.onRejectedExecution(e, this, null, null, coreSubscriber.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Operators.terminate(f2272S, this);
            this.worker.dispose();
        }
    }
}
