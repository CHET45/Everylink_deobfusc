package reactor.core.publisher;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxOnBackpressureBufferStrategy<O> extends InternalFluxOperator<O, O> {
    final BufferOverflowStrategy bufferOverflowStrategy;
    final int bufferSize;
    final boolean delayError;
    final Consumer<? super O> onBufferOverflow;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxOnBackpressureBufferStrategy(Flux<? extends O> flux, int i, @Nullable Consumer<? super O> consumer, BufferOverflowStrategy bufferOverflowStrategy) {
        super(flux);
        boolean z = true;
        if (i < 1) {
            throw new IllegalArgumentException("Buffer Size must be strictly positive");
        }
        this.bufferSize = i;
        this.onBufferOverflow = consumer;
        this.bufferOverflowStrategy = bufferOverflowStrategy;
        if (consumer == null && bufferOverflowStrategy != BufferOverflowStrategy.ERROR) {
            z = false;
        }
        this.delayError = z;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super O> subscribeOrReturn(CoreSubscriber<? super O> coreSubscriber) {
        return new BackpressureBufferDropOldestSubscriber(coreSubscriber, this.bufferSize, this.delayError, this.onBufferOverflow, this.bufferOverflowStrategy);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class BackpressureBufferDropOldestSubscriber<T> extends ArrayDeque<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final int bufferSize;
        volatile boolean cancelled;
        final Context ctx;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        final Consumer<? super T> onOverflow;
        final BufferOverflowStrategy overflowStrategy;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2160s;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<BackpressureBufferDropOldestSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(BackpressureBufferDropOldestSubscriber.class, "wip");
        static final AtomicLongFieldUpdater<BackpressureBufferDropOldestSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BackpressureBufferDropOldestSubscriber.class, "requested");

        BackpressureBufferDropOldestSubscriber(CoreSubscriber<? super T> coreSubscriber, int i, boolean z, @Nullable Consumer<? super T> consumer, BufferOverflowStrategy bufferOverflowStrategy) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.delayError = z;
            this.onOverflow = consumer;
            this.overflowStrategy = bufferOverflowStrategy;
            this.bufferSize = i;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2160s;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done && isEmpty());
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
            return attr == Scannable.Attr.DELAY_ERROR ? Boolean.valueOf(this.delayError) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2160s, subscription)) {
                this.f2160s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            boolean z;
            boolean z2;
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            synchronized (this) {
                z = false;
                if (size() == this.bufferSize) {
                    int i = C51481.$SwitchMap$reactor$core$publisher$BufferOverflowStrategy[this.overflowStrategy.ordinal()];
                    z2 = true;
                    if (i == 1) {
                        T tPollFirst = pollFirst();
                        offer(t);
                        t = tPollFirst;
                    } else if (i != 2) {
                        z = true;
                    }
                    z = true;
                    z2 = false;
                } else {
                    offer(t);
                    z2 = false;
                }
            }
            if (z) {
                Consumer<? super T> consumer = this.onOverflow;
                if (consumer != null) {
                    try {
                        consumer.accept(t);
                    } catch (Throwable th) {
                        try {
                            onError(Operators.onOperatorError(this.f2160s, th, t, this.ctx));
                            return;
                        } finally {
                            Operators.onDiscard(t, this.ctx);
                        }
                    }
                }
            }
            if (z2) {
                onError(Operators.onOperatorError(this.f2160s, Exceptions.failWithOverflow(), t, this.ctx));
            }
            if (z2 || z) {
                return;
            }
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.ctx);
                return;
            }
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int iAddAndGet = 1;
            do {
                CoreSubscriber<? super T> coreSubscriber = this.actual;
                if (coreSubscriber != null) {
                    innerDrain(coreSubscriber);
                    return;
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        void innerDrain(Subscriber<? super T> subscriber) {
            boolean zIsEmpty;
            T tPoll;
            int iAddAndGet = 1;
            do {
                long j = this.requested;
                long j2 = 0;
                while (j != j2) {
                    boolean z = this.done;
                    synchronized (this) {
                        tPoll = poll();
                    }
                    boolean z2 = tPoll == null;
                    if (checkTerminated(z, z2, subscriber)) {
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext(tPoll);
                    j2++;
                }
                if (j == j2) {
                    synchronized (this) {
                        zIsEmpty = isEmpty();
                    }
                    if (checkTerminated(this.done, zIsEmpty, subscriber)) {
                        return;
                    }
                }
                if (j2 != 0 && j != Long.MAX_VALUE) {
                    Operators.produced(REQUESTED, this, j2);
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
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
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.f2160s.cancel();
            if (WIP.getAndIncrement(this) == 0) {
                synchronized (this) {
                    clear();
                }
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<? super T> subscriber) {
            if (this.cancelled) {
                this.f2160s.cancel();
                synchronized (this) {
                    clear();
                }
                return true;
            }
            if (!z) {
                return false;
            }
            if (this.delayError) {
                if (!z2) {
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
            Throwable th2 = this.error;
            if (th2 != null) {
                synchronized (this) {
                    clear();
                }
                subscriber.onError(th2);
                return true;
            }
            if (!z2) {
                return false;
            }
            subscriber.onComplete();
            return true;
        }

        @Override // java.util.ArrayDeque, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            Operators.onDiscardMultiple(this, this.ctx);
            super.clear();
        }
    }

    /* JADX INFO: renamed from: reactor.core.publisher.FluxOnBackpressureBufferStrategy$1 */
    static /* synthetic */ class C51481 {
        static final /* synthetic */ int[] $SwitchMap$reactor$core$publisher$BufferOverflowStrategy;

        static {
            int[] iArr = new int[BufferOverflowStrategy.values().length];
            $SwitchMap$reactor$core$publisher$BufferOverflowStrategy = iArr;
            try {
                iArr[BufferOverflowStrategy.DROP_OLDEST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$reactor$core$publisher$BufferOverflowStrategy[BufferOverflowStrategy.DROP_LATEST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$reactor$core$publisher$BufferOverflowStrategy[BufferOverflowStrategy.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}
