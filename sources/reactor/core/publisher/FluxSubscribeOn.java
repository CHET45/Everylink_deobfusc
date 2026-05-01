package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxSubscribeOn<T> extends InternalFluxOperator<T, T> {
    final boolean requestOnSeparateThread;
    final Scheduler scheduler;

    FluxSubscribeOn(Flux<? extends T> flux, Scheduler scheduler, boolean z) {
        super(flux);
        this.scheduler = (Scheduler) Objects.requireNonNull(scheduler, "scheduler");
        this.requestOnSeparateThread = z;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        Scheduler.Worker worker = (Scheduler.Worker) Objects.requireNonNull(this.scheduler.createWorker(), "The scheduler returned a null Function");
        SubscribeOnSubscriber subscribeOnSubscriber = new SubscribeOnSubscriber(this.source, coreSubscriber, worker, this.requestOnSeparateThread);
        coreSubscriber.onSubscribe(subscribeOnSubscriber);
        try {
            worker.schedule(subscribeOnSubscriber);
        } catch (RejectedExecutionException e) {
            if (subscribeOnSubscriber.f2197s != Operators.cancelledSubscription()) {
                coreSubscriber.onError(Operators.onRejectedExecution(e, subscribeOnSubscriber, null, null, coreSubscriber.currentContext()));
            }
        }
        return null;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
    }

    static final class SubscribeOnSubscriber<T> implements InnerOperator<T, T>, Runnable {
        final CoreSubscriber<? super T> actual;
        final boolean requestOnSeparateThread;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2197s;
        final CorePublisher<? extends T> source;
        volatile Thread thread;
        final Scheduler.Worker worker;

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<SubscribeOnSubscriber, Subscription> f2196S = AtomicReferenceFieldUpdater.newUpdater(SubscribeOnSubscriber.class, Subscription.class, "s");
        static final AtomicLongFieldUpdater<SubscribeOnSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(SubscribeOnSubscriber.class, "requested");
        static final AtomicReferenceFieldUpdater<SubscribeOnSubscriber, Thread> THREAD = AtomicReferenceFieldUpdater.newUpdater(SubscribeOnSubscriber.class, Thread.class, "thread");

        SubscribeOnSubscriber(CorePublisher<? extends T> corePublisher, CoreSubscriber<? super T> coreSubscriber, Scheduler.Worker worker, boolean z) {
            this.actual = coreSubscriber;
            this.worker = worker;
            this.source = corePublisher;
            this.requestOnSeparateThread = z;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2196S, this, subscription)) {
                long andSet = REQUESTED.getAndSet(this, 0L);
                if (andSet != 0) {
                    requestUpstream(andSet, subscription);
                }
            }
        }

        void requestUpstream(final long j, final Subscription subscription) {
            if (!this.requestOnSeparateThread || Thread.currentThread() == THREAD.get(this)) {
                subscription.request(j);
                return;
            }
            try {
                this.worker.schedule(new Runnable() { // from class: reactor.core.publisher.FluxSubscribeOn$SubscribeOnSubscriber$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        subscription.request(j);
                    }
                });
            } catch (RejectedExecutionException e) {
                if (!this.worker.isDisposed()) {
                    throw Operators.onRejectedExecution(e, this, null, null, this.actual.currentContext());
                }
            }
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
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
            this.worker.dispose();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                AtomicReferenceFieldUpdater<SubscribeOnSubscriber, Subscription> atomicReferenceFieldUpdater = f2196S;
                Subscription subscription = atomicReferenceFieldUpdater.get(this);
                if (subscription != null) {
                    requestUpstream(j, subscription);
                    return;
                }
                AtomicLongFieldUpdater<SubscribeOnSubscriber> atomicLongFieldUpdater = REQUESTED;
                Operators.addCap(atomicLongFieldUpdater, this, j);
                Subscription subscription2 = atomicReferenceFieldUpdater.get(this);
                if (subscription2 != null) {
                    long andSet = atomicLongFieldUpdater.getAndSet(this, 0L);
                    if (andSet != 0) {
                        requestUpstream(andSet, subscription2);
                    }
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            THREAD.lazySet(this, Thread.currentThread());
            this.source.subscribe((CoreSubscriber<? super Object>) this);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Subscription andSet;
            if (this.f2197s != Operators.cancelledSubscription() && (andSet = f2196S.getAndSet(this, Operators.cancelledSubscription())) != null && andSet != Operators.cancelledSubscription()) {
                andSet.cancel();
            }
            this.worker.dispose();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2197s;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2197s == Operators.cancelledSubscription());
            }
            return attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.RUN_ON ? this.worker : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }
}
