package reactor.core.publisher;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxOnBackpressureBufferTimeout<O> extends InternalFluxOperator<O, O> {
    private static final Logger LOGGER = Loggers.getLogger((Class<?>) FluxOnBackpressureBufferTimeout.class);
    final int bufferSize;
    final Consumer<? super O> onBufferEviction;
    final Duration ttl;
    final Scheduler ttlScheduler;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxOnBackpressureBufferTimeout(Flux<? extends O> flux, Duration duration, Scheduler scheduler, int i, Consumer<? super O> consumer) {
        super(flux);
        this.ttl = duration;
        this.ttlScheduler = scheduler;
        this.bufferSize = i;
        this.onBufferEviction = consumer;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super O> subscribeOrReturn(CoreSubscriber<? super O> coreSubscriber) {
        return new BackpressureBufferTimeoutSubscriber(coreSubscriber, this.ttl, this.ttlScheduler, this.bufferSize, this.onBufferEviction);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.ttlScheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
    }

    static final class BackpressureBufferTimeoutSubscriber<T> extends ArrayDeque<Object> implements InnerOperator<T, T>, Runnable {
        final CoreSubscriber<? super T> actual;
        final int bufferSizeDouble;
        volatile boolean cancelled;
        final Context ctx;
        volatile boolean done;
        Throwable error;
        final Consumer<? super T> onBufferEviction;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2161s;
        final Duration ttl;
        final Scheduler ttlScheduler;
        volatile int wip;
        final Scheduler.Worker worker;
        static final AtomicIntegerFieldUpdater<BackpressureBufferTimeoutSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(BackpressureBufferTimeoutSubscriber.class, "wip");
        static final AtomicLongFieldUpdater<BackpressureBufferTimeoutSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BackpressureBufferTimeoutSubscriber.class, "requested");

        BackpressureBufferTimeoutSubscriber(CoreSubscriber<? super T> coreSubscriber, Duration duration, Scheduler scheduler, int i, Consumer<? super T> consumer) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.onBufferEviction = (Consumer) Objects.requireNonNull(consumer, "buffer eviction callback must not be null");
            this.bufferSizeDouble = i << 1;
            this.ttl = duration;
            this.ttlScheduler = (Scheduler) Objects.requireNonNull(scheduler, "ttl Scheduler must not be null");
            this.worker = scheduler.createWorker();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2161s;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            boolean z = false;
            if (attr == Scannable.Attr.TERMINATED) {
                if (this.done && isEmpty()) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(size());
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (attr == Scannable.Attr.DELAY_ERROR) {
                return false;
            }
            if (attr == Scannable.Attr.RUN_ON) {
                return this.ttlScheduler;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.f2161s.cancel();
            this.worker.dispose();
            if (WIP.getAndIncrement(this) == 0) {
                clearQueue();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        void clearQueue() {
            Object objPoll;
            while (true) {
                synchronized (this) {
                    if (isEmpty()) {
                        return;
                    }
                    poll();
                    objPoll = poll();
                }
                evict(objPoll);
            }
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2161s, subscription)) {
                this.f2161s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            Object objPoll;
            synchronized (this) {
                if (size() == this.bufferSizeDouble) {
                    poll();
                    objPoll = poll();
                } else {
                    objPoll = null;
                }
                offer(Long.valueOf(this.ttlScheduler.now(TimeUnit.NANOSECONDS)));
                offer(t);
            }
            evict(objPoll);
            try {
                this.worker.schedule(this, this.ttl.toNanos(), TimeUnit.NANOSECONDS);
            } catch (RejectedExecutionException e) {
                this.done = true;
                this.error = Operators.onRejectedExecution(e, this, null, t, this.actual.currentContext());
            }
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            Object objPoll;
            while (!this.cancelled) {
                boolean z = this.done;
                synchronized (this) {
                    Long l = (Long) peek();
                    boolean z2 = l == null;
                    if (z2) {
                        objPoll = null;
                    } else {
                        if (l.longValue() > this.ttlScheduler.now(TimeUnit.NANOSECONDS) - this.ttl.toNanos()) {
                            return;
                        }
                        poll();
                        objPoll = poll();
                    }
                    evict(objPoll);
                    if (z2) {
                        if (z) {
                            drain();
                            return;
                        }
                        return;
                    }
                }
            }
        }

        void evict(@Nullable T t) {
            if (t != null) {
                try {
                    this.onBufferEviction.accept(t);
                } catch (Throwable th) {
                    if (FluxOnBackpressureBufferTimeout.LOGGER.isDebugEnabled()) {
                        FluxOnBackpressureBufferTimeout.LOGGER.debug("value [{}] couldn't be evicted due to a callback error. This error will be dropped: {}", t, th);
                    }
                    Operators.onErrorDropped(th, this.actual.currentContext());
                }
                Operators.onDiscard(t, this.actual.currentContext());
            }
        }

        void drain() {
            boolean zIsEmpty;
            Object objPoll;
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int iAddAndGet = 1;
            do {
                long j = this.requested;
                long j2 = 0;
                while (j2 != j) {
                    if (this.cancelled) {
                        clearQueue();
                        return;
                    }
                    boolean z = this.done;
                    synchronized (this) {
                        objPoll = poll() != null ? poll() : null;
                    }
                    boolean z2 = objPoll == null;
                    if (z && z2) {
                        Throwable th = this.error;
                        if (th != null) {
                            this.actual.onError(th);
                        } else {
                            this.actual.onComplete();
                        }
                        this.worker.dispose();
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    this.actual.onNext(objPoll);
                    j2++;
                }
                if (j2 == j) {
                    if (this.cancelled) {
                        clearQueue();
                        return;
                    }
                    boolean z3 = this.done;
                    synchronized (this) {
                        zIsEmpty = isEmpty();
                    }
                    if (z3 && zIsEmpty) {
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            this.actual.onError(th2);
                        } else {
                            this.actual.onComplete();
                        }
                        this.worker.dispose();
                        return;
                    }
                }
                if (j2 != 0 && j != Long.MAX_VALUE) {
                    REQUESTED.addAndGet(this, -j2);
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }
    }
}
