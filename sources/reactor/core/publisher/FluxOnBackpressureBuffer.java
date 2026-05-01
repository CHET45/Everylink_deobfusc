package reactor.core.publisher;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxOnBackpressureBuffer<O> extends InternalFluxOperator<O, O> implements Fuseable {
    final int bufferSize;
    final Consumer<? super O> onOverflow;
    final boolean unbounded;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxOnBackpressureBuffer(Flux<? extends O> flux, int i, boolean z, @Nullable Consumer<? super O> consumer) {
        super(flux);
        if (i < 1) {
            throw new IllegalArgumentException("Buffer Size must be strictly positive");
        }
        this.bufferSize = i;
        this.unbounded = z;
        this.onOverflow = consumer;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super O> subscribeOrReturn(CoreSubscriber<? super O> coreSubscriber) {
        return new BackpressureBufferSubscriber(coreSubscriber, this.bufferSize, this.unbounded, this.onOverflow);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class BackpressureBufferSubscriber<T> implements Fuseable.QueueSubscription<T>, InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        final int capacityOrSkip;
        final Context ctx;
        volatile int discardGuard;
        volatile boolean done;
        volatile boolean enabledFusion;
        Throwable error;
        final Consumer<? super T> onOverflow;
        final Queue<T> queue;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2159s;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<BackpressureBufferSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(BackpressureBufferSubscriber.class, "wip");
        static final AtomicIntegerFieldUpdater<BackpressureBufferSubscriber> DISCARD_GUARD = AtomicIntegerFieldUpdater.newUpdater(BackpressureBufferSubscriber.class, "discardGuard");
        static final AtomicLongFieldUpdater<BackpressureBufferSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BackpressureBufferSubscriber.class, "requested");

        BackpressureBufferSubscriber(CoreSubscriber<? super T> coreSubscriber, int i, boolean z, @Nullable Consumer<? super T> consumer) {
            Queue<T> queue;
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.onOverflow = consumer;
            if (z) {
                queue = (Queue) Queues.unbounded(i).get();
            } else {
                queue = (Queue) Queues.get(i).get();
            }
            if (!z && Queues.capacity(queue) > i) {
                this.capacityOrSkip = i;
            } else {
                this.capacityOrSkip = Integer.MAX_VALUE;
            }
            this.queue = queue;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2159s;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done && this.queue.isEmpty());
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.queue.size());
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (attr == Scannable.Attr.DELAY_ERROR) {
                return true;
            }
            if (attr != Scannable.Attr.CAPACITY) {
                return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            int iCapacity = this.capacityOrSkip;
            if (iCapacity == Integer.MAX_VALUE) {
                iCapacity = Queues.capacity(this.queue);
            }
            return Integer.valueOf(iCapacity);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2159s, subscription)) {
                this.f2159s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            if (this.cancelled) {
                Operators.onDiscard(t, this.ctx);
            }
            if ((this.capacityOrSkip != Integer.MAX_VALUE && this.queue.size() >= this.capacityOrSkip) || !this.queue.offer(t)) {
                Throwable thOnOperatorError = Operators.onOperatorError(this.f2159s, Exceptions.failWithOverflow(), t, this.ctx);
                Consumer<? super T> consumer = this.onOverflow;
                if (consumer != null) {
                    try {
                        consumer.accept(t);
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        thOnOperatorError.initCause(th);
                    }
                }
                Operators.onDiscard(t, this.ctx);
                onError(thOnOperatorError);
                return;
            }
            drain(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.ctx);
                return;
            }
            this.error = th;
            this.done = true;
            drain(null);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            drain(null);
        }

        void drain(@Nullable T t) {
            if (WIP.getAndIncrement(this) != 0) {
                if (t == null || !this.cancelled) {
                    return;
                }
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            int iAddAndGet = 1;
            do {
                CoreSubscriber<? super T> coreSubscriber = this.actual;
                if (coreSubscriber != null) {
                    if (this.enabledFusion) {
                        drainFused(coreSubscriber);
                        return;
                    } else {
                        drainRegular(coreSubscriber);
                        return;
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        /* JADX WARN: Multi-variable type inference failed */
        void drainRegular(Subscriber<? super T> subscriber) {
            Queue<T> queue = this.queue;
            int iAddAndGet = 1;
            do {
                long j = this.requested;
                long j2 = 0;
                while (j != j2) {
                    boolean z = this.done;
                    T tPoll = queue.poll();
                    boolean z2 = tPoll == 0;
                    if (checkTerminated(z, z2, subscriber, tPoll)) {
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext(tPoll);
                    j2++;
                }
                if (j == j2 && checkTerminated(this.done, queue.isEmpty(), subscriber, null)) {
                    return;
                }
                if (j2 != 0 && j != Long.MAX_VALUE) {
                    REQUESTED.addAndGet(this, -j2);
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        void drainFused(Subscriber<? super T> subscriber) {
            int iAddAndGet = 1;
            while (!this.cancelled) {
                boolean z = this.done;
                subscriber.onNext(null);
                if (z) {
                    Throwable th = this.error;
                    if (th != null) {
                        subscriber.onError(th);
                        return;
                    } else {
                        subscriber.onComplete();
                        return;
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
            clear();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
                drain(null);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.f2159s.cancel();
            if (WIP.getAndIncrement(this) != 0 || this.enabledFusion) {
                return;
            }
            Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            return this.queue.poll();
        }

        @Override // java.util.Collection
        public int size() {
            return this.queue.size();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            if (DISCARD_GUARD.getAndIncrement(this) != 0) {
                return;
            }
            int iAddAndGet = 1;
            while (true) {
                Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
                int i = this.discardGuard;
                if (iAddAndGet == i) {
                    iAddAndGet = DISCARD_GUARD.addAndGet(this, -iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    iAddAndGet = i;
                }
            }
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.enabledFusion = true;
            return 2;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<? super T> subscriber, @Nullable T t) {
            if (this.cancelled) {
                this.f2159s.cancel();
                Operators.onDiscard(t, this.ctx);
                Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
                return true;
            }
            if (!z || !z2) {
                return false;
            }
            Throwable th = this.error;
            if (th != null) {
                subscriber.onError(th);
            } else {
                subscriber.onComplete();
            }
            return true;
        }
    }
}
