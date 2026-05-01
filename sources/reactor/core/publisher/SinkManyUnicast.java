package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import com.microsoft.azure.storage.core.C2391SR;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class SinkManyUnicast<T> extends Flux<T> implements InternalManySink<T>, Disposable, Fuseable.QueueSubscription<T>, Fuseable {
    volatile CoreSubscriber<? super T> actual;
    volatile boolean cancelled;
    volatile int discardGuard;
    volatile boolean done;
    Throwable error;
    boolean hasDownstream;
    volatile Disposable onTerminate;
    volatile int once;
    boolean outputFused;
    final Queue<T> queue;
    volatile long requested;
    volatile boolean subscriptionDelivered;
    volatile int wip;
    static final AtomicReferenceFieldUpdater<SinkManyUnicast, Disposable> ON_TERMINATE = AtomicReferenceFieldUpdater.newUpdater(SinkManyUnicast.class, Disposable.class, "onTerminate");
    static final AtomicIntegerFieldUpdater<SinkManyUnicast> ONCE = AtomicIntegerFieldUpdater.newUpdater(SinkManyUnicast.class, "once");
    static final AtomicIntegerFieldUpdater<SinkManyUnicast> WIP = AtomicIntegerFieldUpdater.newUpdater(SinkManyUnicast.class, "wip");
    static final AtomicIntegerFieldUpdater<SinkManyUnicast> DISCARD_GUARD = AtomicIntegerFieldUpdater.newUpdater(SinkManyUnicast.class, "discardGuard");
    static final AtomicLongFieldUpdater<SinkManyUnicast> REQUESTED = AtomicLongFieldUpdater.newUpdater(SinkManyUnicast.class, "requested");

    @Override // reactor.core.publisher.Sinks.Many
    public Flux<T> asFlux() {
        return this;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    static <E> SinkManyUnicast<E> create() {
        return new SinkManyUnicast<>((Queue) Queues.unbounded().get());
    }

    static <E> SinkManyUnicast<E> create(Queue<E> queue) {
        return new SinkManyUnicast<>(Hooks.wrapQueue(queue));
    }

    static <E> SinkManyUnicast<E> create(Queue<E> queue, Disposable disposable) {
        return new SinkManyUnicast<>(Hooks.wrapQueue(queue), disposable);
    }

    SinkManyUnicast(Queue<T> queue) {
        this.queue = (Queue) Objects.requireNonNull(queue, C2391SR.QUEUE);
        this.onTerminate = null;
    }

    SinkManyUnicast(Queue<T> queue, Disposable disposable) {
        this.queue = (Queue) Objects.requireNonNull(queue, C2391SR.QUEUE);
        this.onTerminate = (Disposable) Objects.requireNonNull(disposable, "onTerminate");
    }

    @Override // reactor.core.Scannable
    public Stream<Scannable> inners() {
        return this.hasDownstream ? Stream.of(Scannable.from(this.actual)) : Stream.empty();
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (Scannable.Attr.ACTUAL == attr) {
            return this.actual;
        }
        if (Scannable.Attr.BUFFERED == attr) {
            return Integer.valueOf(this.queue.size());
        }
        if (Scannable.Attr.CAPACITY == attr) {
            return Integer.valueOf(Queues.capacity(this.queue));
        }
        if (Scannable.Attr.PREFETCH == attr) {
            return Integer.MAX_VALUE;
        }
        return Scannable.Attr.CANCELLED == attr ? Boolean.valueOf(this.cancelled) : Scannable.Attr.TERMINATED == attr ? Boolean.valueOf(this.done) : Scannable.Attr.ERROR == attr ? this.error : InternalProducerAttr.INSTANCE == attr ? true : null;
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitComplete() {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (this.cancelled) {
            return Sinks.EmitResult.FAIL_CANCELLED;
        }
        this.done = true;
        doTerminate();
        drain(null);
        return Sinks.EmitResult.OK;
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitError(Throwable th) {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (this.cancelled) {
            return Sinks.EmitResult.FAIL_CANCELLED;
        }
        this.error = th;
        this.done = true;
        doTerminate();
        drain(null);
        return Sinks.EmitResult.OK;
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitNext(T t) {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (this.cancelled) {
            return Sinks.EmitResult.FAIL_CANCELLED;
        }
        if (!this.queue.offer(t)) {
            return this.once > 0 ? Sinks.EmitResult.FAIL_OVERFLOW : Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
        }
        drain(t);
        return Sinks.EmitResult.OK;
    }

    @Override // reactor.core.publisher.Sinks.Many
    public int currentSubscriberCount() {
        return this.hasDownstream ? 1 : 0;
    }

    void doTerminate() {
        Disposable disposable = this.onTerminate;
        if (disposable == null || !C0162xc40028dd.m5m(ON_TERMINATE, this, disposable, null)) {
            return;
        }
        disposable.dispose();
    }

    void drainRegular(CoreSubscriber<? super T> coreSubscriber) {
        Queue<T> queue = this.queue;
        int iAddAndGet = 1;
        do {
            long j = this.requested;
            long j2 = 0;
            while (j != j2) {
                boolean z = this.done;
                T tPoll = queue.poll();
                boolean z2 = tPoll == null;
                if (checkTerminated(z, z2, coreSubscriber, queue, tPoll)) {
                    return;
                }
                if (z2) {
                    break;
                }
                coreSubscriber.onNext(tPoll);
                j2++;
            }
            if (j == j2 && checkTerminated(this.done, queue.isEmpty(), coreSubscriber, queue, null)) {
                return;
            }
            if (j2 != 0 && j != Long.MAX_VALUE) {
                REQUESTED.addAndGet(this, -j2);
            }
            iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
        } while (iAddAndGet != 0);
    }

    void drainFused(CoreSubscriber<? super T> coreSubscriber) {
        int iAddAndGet = 1;
        while (!this.cancelled) {
            boolean z = this.done;
            coreSubscriber.onNext(null);
            if (z) {
                this.hasDownstream = false;
                Throwable th = this.error;
                if (th != null) {
                    coreSubscriber.onError(th);
                    return;
                } else {
                    coreSubscriber.onComplete();
                    return;
                }
            }
            iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            }
        }
        clear();
        this.hasDownstream = false;
    }

    void drain(@Nullable T t) {
        if (WIP.getAndIncrement(this) != 0) {
            if (t != null) {
                if (this.cancelled) {
                    Operators.onDiscard(t, this.actual.currentContext());
                    return;
                } else {
                    if (this.done) {
                        Operators.onNextDropped(t, currentContext());
                        return;
                    }
                    return;
                }
            }
            return;
        }
        int iAddAndGet = 1;
        while (!this.subscriptionDelivered) {
            iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            if (iAddAndGet == 0) {
                return;
            }
        }
        CoreSubscriber<? super T> coreSubscriber = this.actual;
        if (this.outputFused) {
            drainFused(coreSubscriber);
        } else {
            drainRegular(coreSubscriber);
        }
    }

    boolean checkTerminated(boolean z, boolean z2, CoreSubscriber<? super T> coreSubscriber, Queue<T> queue, @Nullable T t) {
        if (this.cancelled) {
            Operators.onDiscard(t, coreSubscriber.currentContext());
            Operators.onDiscardQueueWithClear(queue, coreSubscriber.currentContext(), null);
            this.hasDownstream = false;
            return true;
        }
        if (!z || !z2) {
            return false;
        }
        Throwable th = this.error;
        this.hasDownstream = false;
        if (th != null) {
            coreSubscriber.onError(th);
        } else {
            coreSubscriber.onComplete();
        }
        return true;
    }

    @Override // reactor.core.publisher.ContextHolder
    public Context currentContext() {
        CoreSubscriber<? super T> coreSubscriber = this.actual;
        return coreSubscriber != null ? coreSubscriber.currentContext() : Context.empty();
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Objects.requireNonNull(coreSubscriber, "subscribe");
        CoreSubscriber<? super T> coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled = Operators.restoreContextOnSubscriberIfAutoCPEnabled(this, coreSubscriber);
        if (this.once == 0 && ONCE.compareAndSet(this, 0, 1)) {
            this.hasDownstream = true;
            this.actual = coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled;
            coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled.onSubscribe(this);
            this.subscriptionDelivered = true;
            if (this.cancelled) {
                this.hasDownstream = false;
                return;
            } else {
                drain(null);
                return;
            }
        }
        Operators.error(coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled, new IllegalStateException("Sinks.many().unicast() sinks only allow a single Subscriber"));
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
        doTerminate();
        if (WIP.getAndIncrement(this) == 0) {
            if (!this.outputFused) {
                Operators.onDiscardQueueWithClear(this.queue, currentContext(), null);
            }
            this.hasDownstream = false;
        }
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
            Operators.onDiscardQueueWithClear(this.queue, currentContext(), null);
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
        this.outputFused = true;
        return 2;
    }

    @Override // reactor.core.Disposable
    public void dispose() {
        emitError(new CancellationException("Disposed"), Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        return this.cancelled || this.done;
    }
}
