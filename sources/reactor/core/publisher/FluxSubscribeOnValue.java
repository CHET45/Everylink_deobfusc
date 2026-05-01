package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxSubscribeOnValue<T> extends Flux<T> implements Fuseable, Scannable {
    final Scheduler scheduler;
    final T value;

    FluxSubscribeOnValue(@Nullable T t, Scheduler scheduler) {
        this.value = t;
        this.scheduler = (Scheduler) Objects.requireNonNull(scheduler, "scheduler");
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        T t = this.value;
        if (t == null) {
            ScheduledEmpty scheduledEmpty = new ScheduledEmpty(coreSubscriber);
            coreSubscriber.onSubscribe(scheduledEmpty);
            try {
                scheduledEmpty.setFuture(this.scheduler.schedule(scheduledEmpty));
                return;
            } catch (RejectedExecutionException e) {
                if (scheduledEmpty.future != OperatorDisposables.DISPOSED) {
                    coreSubscriber.onError(Operators.onRejectedExecution(e, coreSubscriber.currentContext()));
                    return;
                }
                return;
            }
        }
        coreSubscriber.onSubscribe(new ScheduledScalar(coreSubscriber, t, this.scheduler));
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    static final class ScheduledScalar<T> implements Fuseable.QueueSubscription<T>, InnerProducer<T>, Runnable {
        static final int COMPLETE = 3;
        static final int HAS_VALUE = 2;
        static final int NO_VALUE = 1;
        final CoreSubscriber<? super T> actual;
        int fusionState;
        volatile Disposable future;
        volatile int once;
        final Scheduler scheduler;
        final T value;
        static final AtomicIntegerFieldUpdater<ScheduledScalar> ONCE = AtomicIntegerFieldUpdater.newUpdater(ScheduledScalar.class, "once");
        static final AtomicReferenceFieldUpdater<ScheduledScalar, Disposable> FUTURE = AtomicReferenceFieldUpdater.newUpdater(ScheduledScalar.class, Disposable.class, "future");
        static final Disposable FINISHED = Disposables.disposed();

        ScheduledScalar(CoreSubscriber<? super T> coreSubscriber, T t, Scheduler scheduler) {
            this.actual = coreSubscriber;
            this.value = t;
            this.scheduler = scheduler;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.future == OperatorDisposables.DISPOSED);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.future == FINISHED);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return 1;
            }
            return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j) && ONCE.compareAndSet(this, 0, 1)) {
                try {
                    Disposable disposableSchedule = this.scheduler.schedule(this);
                    if (C0162xc40028dd.m5m(FUTURE, this, null, disposableSchedule) || this.future == FINISHED || this.future == OperatorDisposables.DISPOSED) {
                        return;
                    }
                    disposableSchedule.dispose();
                } catch (RejectedExecutionException e) {
                    if (this.future == FINISHED || this.future == OperatorDisposables.DISPOSED) {
                        return;
                    }
                    CoreSubscriber<? super T> coreSubscriber = this.actual;
                    coreSubscriber.onError(Operators.onRejectedExecution(e, this, null, this.value, coreSubscriber.currentContext()));
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Disposable andSet;
            ONCE.lazySet(this, 1);
            if (this.future != OperatorDisposables.DISPOSED) {
                Disposable disposable = this.future;
                Disposable disposable2 = FINISHED;
                if (disposable == disposable2 || (andSet = FUTURE.getAndSet(this, OperatorDisposables.DISPOSED)) == null || andSet == OperatorDisposables.DISPOSED || andSet == disposable2) {
                    return;
                }
                andSet.dispose();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (this.fusionState == 1) {
                    this.fusionState = 2;
                }
                this.actual.onNext(this.value);
                this.actual.onComplete();
            } finally {
                FUTURE.lazySet(this, FINISHED);
            }
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.fusionState = 1;
            return 2;
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            if (this.fusionState != 2) {
                return null;
            }
            this.fusionState = 3;
            return this.value;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.fusionState != 2;
        }

        @Override // java.util.Collection
        public int size() {
            return !isEmpty() ? 1 : 0;
        }

        @Override // java.util.Collection
        public void clear() {
            this.fusionState = 3;
        }
    }

    static final class ScheduledEmpty implements Fuseable.QueueSubscription<Void>, Runnable {
        final Subscriber<?> actual;
        volatile Disposable future;
        static final AtomicReferenceFieldUpdater<ScheduledEmpty, Disposable> FUTURE = AtomicReferenceFieldUpdater.newUpdater(ScheduledEmpty.class, Disposable.class, "future");
        static final Disposable FINISHED = Disposables.disposed();

        @Override // java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.Queue
        @Nullable
        public Void poll() {
            return null;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return i & 2;
        }

        @Override // java.util.Collection
        public int size() {
            return 0;
        }

        ScheduledEmpty(Subscriber<?> subscriber) {
            this.actual = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            Operators.validate(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Disposable disposable;
            Disposable andSet;
            Disposable disposable2 = this.future;
            if (disposable2 == OperatorDisposables.DISPOSED || disposable2 == (disposable = FINISHED) || (andSet = FUTURE.getAndSet(this, OperatorDisposables.DISPOSED)) == null || andSet == OperatorDisposables.DISPOSED || andSet == disposable) {
                return;
            }
            andSet.dispose();
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.actual.onComplete();
            } finally {
                FUTURE.lazySet(this, FINISHED);
            }
        }

        void setFuture(Disposable disposable) {
            Disposable disposable2;
            if (C0162xc40028dd.m5m(FUTURE, this, null, disposable) || (disposable2 = this.future) == FINISHED || disposable2 == OperatorDisposables.DISPOSED) {
                return;
            }
            disposable.dispose();
        }
    }
}
