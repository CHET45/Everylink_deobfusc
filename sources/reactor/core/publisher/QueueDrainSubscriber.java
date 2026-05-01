package reactor.core.publisher;

import java.util.Queue;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
abstract class QueueDrainSubscriber<T, U, V> extends QueueDrainSubscriberPad4 implements InnerOperator<T, V> {
    final CoreSubscriber<? super V> actual;
    volatile boolean cancelled;
    volatile boolean done;
    Throwable error;
    final Queue<U> queue;

    public boolean accept(Subscriber<? super V> subscriber, U u) {
        return false;
    }

    QueueDrainSubscriber(CoreSubscriber<? super V> coreSubscriber, Queue<U> queue) {
        this.actual = coreSubscriber;
        this.queue = queue;
    }

    @Override // reactor.core.publisher.InnerProducer
    public CoreSubscriber<? super V> actual() {
        return this.actual;
    }

    public final boolean cancelled() {
        return this.cancelled;
    }

    public final boolean done() {
        return this.done;
    }

    public final boolean enter() {
        return this.wip.getAndIncrement() == 0;
    }

    public final boolean fastEnter() {
        return this.wip.get() == 0 && this.wip.compareAndSet(0, 1);
    }

    protected final void fastPathEmitMax(U u, boolean z, Disposable disposable) {
        CoreSubscriber<? super V> coreSubscriber = this.actual;
        Queue<U> queue = this.queue;
        if (this.wip.get() == 0 && this.wip.compareAndSet(0, 1)) {
            long j = this.requested;
            if (j != 0) {
                if (accept(coreSubscriber, u) && j != Long.MAX_VALUE) {
                    produced(1L);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                disposable.dispose();
                coreSubscriber.onError(Exceptions.failWithOverflow("Could not emit buffer due to lack of requests"));
                return;
            }
        } else {
            queue.offer(u);
            if (!enter()) {
                return;
            }
        }
        drainMaxLoop(queue, coreSubscriber, z, disposable, this);
    }

    protected final void fastPathOrderedEmitMax(U u, boolean z, Disposable disposable) {
        CoreSubscriber<? super V> coreSubscriber = this.actual;
        Queue<U> queue = this.queue;
        if (this.wip.get() == 0 && this.wip.compareAndSet(0, 1)) {
            long j = this.requested;
            if (j != 0) {
                if (queue.isEmpty()) {
                    if (accept(coreSubscriber, u) && j != Long.MAX_VALUE) {
                        produced(1L);
                    }
                    if (leave(-1) == 0) {
                        return;
                    }
                } else {
                    queue.offer(u);
                }
            } else {
                this.cancelled = true;
                disposable.dispose();
                coreSubscriber.onError(Exceptions.failWithOverflow("Could not emit buffer due to lack of requests"));
                return;
            }
        } else {
            queue.offer(u);
            if (!enter()) {
                return;
            }
        }
        drainMaxLoop(queue, coreSubscriber, z, disposable, this);
    }

    public final Throwable error() {
        return this.error;
    }

    public final int leave(int i) {
        return this.wip.addAndGet(i);
    }

    public final long requested() {
        return this.requested;
    }

    public final long produced(long j) {
        return REQUESTED.addAndGet(this, -j);
    }

    public final void requested(long j) {
        if (Operators.validate(j)) {
            Operators.addCap(REQUESTED, this, j);
        }
    }

    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.ERROR ? this.error : super.scanUnsafe(attr);
    }

    static <Q, S> void drainMaxLoop(Queue<Q> queue, Subscriber<? super S> subscriber, boolean z, Disposable disposable, QueueDrainSubscriber<?, Q, S> queueDrainSubscriber) {
        int iLeave = 1;
        while (true) {
            boolean zDone = queueDrainSubscriber.done();
            Q qPoll = queue.poll();
            boolean z2 = qPoll == null;
            if (checkTerminated(zDone, z2, subscriber, z, queue, queueDrainSubscriber)) {
                if (disposable != null) {
                    disposable.dispose();
                    return;
                }
                return;
            } else if (!z2) {
                long jRequested = queueDrainSubscriber.requested();
                if (jRequested != 0) {
                    if (queueDrainSubscriber.accept(subscriber, qPoll) && jRequested != Long.MAX_VALUE) {
                        queueDrainSubscriber.produced(1L);
                    }
                } else {
                    queue.clear();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    subscriber.onError(Exceptions.failWithOverflow("Could not emit value due to lack of requests."));
                    return;
                }
            } else {
                iLeave = queueDrainSubscriber.leave(-iLeave);
                if (iLeave == 0) {
                    return;
                }
            }
        }
    }

    static <Q, S> boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, boolean z3, Queue<?> queue, QueueDrainSubscriber<?, Q, S> queueDrainSubscriber) {
        if (queueDrainSubscriber.cancelled()) {
            queue.clear();
            return true;
        }
        if (!z) {
            return false;
        }
        if (z3) {
            if (!z2) {
                return false;
            }
            Throwable thError = queueDrainSubscriber.error();
            if (thError != null) {
                subscriber.onError(thError);
            } else {
                subscriber.onComplete();
            }
            return true;
        }
        Throwable thError2 = queueDrainSubscriber.error();
        if (thError2 != null) {
            queue.clear();
            subscriber.onError(thError2);
            return true;
        }
        if (!z2) {
            return false;
        }
        subscriber.onComplete();
        return true;
    }
}
