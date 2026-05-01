package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoDelayElement<T> extends InternalMonoOperator<T, T> {
    final long delay;
    final Scheduler timedScheduler;
    final TimeUnit unit;

    MonoDelayElement(Mono<? extends T> mono, long j, TimeUnit timeUnit, Scheduler scheduler) {
        super(mono);
        this.delay = j;
        this.unit = (TimeUnit) Objects.requireNonNull(timeUnit, "unit");
        this.timedScheduler = (Scheduler) Objects.requireNonNull(scheduler, "timedScheduler");
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new DelayElementSubscriber(coreSubscriber, this.timedScheduler, this.delay, this.unit);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.timedScheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
    }

    static final class DelayElementSubscriber<T> implements InnerOperator<T, T>, Fuseable, Fuseable.QueueSubscription<T>, Runnable {
        final CoreSubscriber<? super T> actual;
        final long delay;
        boolean done;

        /* JADX INFO: renamed from: s */
        Subscription f2250s;
        final Scheduler scheduler;
        volatile Disposable task;
        final TimeUnit unit;
        T value;
        static final Disposable CANCELLED = Disposables.disposed();
        static final Disposable TERMINATED = Disposables.disposed();
        static final AtomicReferenceFieldUpdater<DelayElementSubscriber, Disposable> TASK = AtomicReferenceFieldUpdater.newUpdater(DelayElementSubscriber.class, Disposable.class, "task");

        @Override // java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.Queue
        public T poll() {
            return null;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return 0;
        }

        @Override // java.util.Collection
        public int size() {
            return 0;
        }

        DelayElementSubscriber(CoreSubscriber<? super T> coreSubscriber, Scheduler scheduler, long j, TimeUnit timeUnit) {
            this.actual = coreSubscriber;
            this.scheduler = scheduler;
            this.delay = j;
            this.unit = timeUnit;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                Disposable disposable = this.task;
                if (!this.done || (disposable != TERMINATED && (disposable != null || this.value != null))) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.task == CANCELLED);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return 0;
            }
            return attr == Scannable.Attr.PARENT ? this.f2250s : attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2250s, subscription)) {
                this.f2250s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.value = t;
            try {
                if (this.task == CANCELLED) {
                    this.value = null;
                    Operators.onDiscard(t, this.actual.currentContext());
                    return;
                }
                Disposable disposableSchedule = this.scheduler.schedule(this, this.delay, this.unit);
                do {
                    Disposable disposable = this.task;
                    if (disposable == CANCELLED) {
                        disposableSchedule.dispose();
                        Operators.onDiscard(t, this.actual.currentContext());
                        return;
                    } else if (disposable == TERMINATED) {
                        return;
                    }
                } while (!C0162xc40028dd.m5m(TASK, this, null, disposableSchedule));
            } catch (RejectedExecutionException e) {
                this.value = null;
                Operators.onDiscard(t, this.actual.currentContext());
                CoreSubscriber<? super T> coreSubscriber = this.actual;
                coreSubscriber.onError(Operators.onRejectedExecution(e, this, null, t, coreSubscriber.currentContext()));
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Disposable disposable;
            do {
                disposable = this.task;
                if (disposable == CANCELLED) {
                    return;
                }
            } while (!C0162xc40028dd.m5m(TASK, this, disposable, TERMINATED));
            T t = this.value;
            this.value = null;
            this.actual.onNext(t);
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Disposable disposable;
            Disposable disposable2;
            do {
                disposable = this.task;
                disposable2 = CANCELLED;
                if (disposable == disposable2 || disposable == TERMINATED) {
                    return;
                }
            } while (!C0162xc40028dd.m5m(TASK, this, disposable, disposable2));
            if (disposable != null) {
                disposable.dispose();
                T t = this.value;
                this.value = null;
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            this.f2250s.cancel();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2250s.request(j);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                this.done = true;
                this.actual.onError(th);
            }
        }
    }
}
