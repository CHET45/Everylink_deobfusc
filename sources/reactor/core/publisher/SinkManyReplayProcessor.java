package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxReplay;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class SinkManyReplayProcessor<T> extends Flux<T> implements InternalManySink<T>, CoreSubscriber<T>, ContextHolder, Disposable, Fuseable, Scannable {
    static final AtomicReferenceFieldUpdater<SinkManyReplayProcessor, FluxReplay.ReplaySubscription[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(SinkManyReplayProcessor.class, FluxReplay.ReplaySubscription[].class, "subscribers");
    final FluxReplay.ReplayBuffer<T> buffer;
    volatile FluxReplay.ReplaySubscription<T>[] subscribers;
    Subscription subscription;

    @Override // reactor.core.publisher.Sinks.Many
    public Flux<T> asFlux() {
        return this;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    static <T> SinkManyReplayProcessor<T> cacheLast() {
        return cacheLastOrDefault(null);
    }

    static <T> SinkManyReplayProcessor<T> cacheLastOrDefault(@Nullable T t) {
        SinkManyReplayProcessor<T> sinkManyReplayProcessorCreate = create(1);
        if (t != null) {
            sinkManyReplayProcessorCreate.onNext(t);
        }
        return sinkManyReplayProcessorCreate;
    }

    static <E> SinkManyReplayProcessor<E> create() {
        return create(Queues.SMALL_BUFFER_SIZE, true);
    }

    static <E> SinkManyReplayProcessor<E> create(int i) {
        return create(i, false);
    }

    static <E> SinkManyReplayProcessor<E> create(int i, boolean z) {
        FluxReplay.ReplayBuffer sizeBoundReplayBuffer;
        if (z) {
            sizeBoundReplayBuffer = new FluxReplay.UnboundedReplayBuffer(i);
        } else {
            sizeBoundReplayBuffer = new FluxReplay.SizeBoundReplayBuffer(i);
        }
        return new SinkManyReplayProcessor<>(sizeBoundReplayBuffer);
    }

    static <T> SinkManyReplayProcessor<T> createTimeout(Duration duration) {
        return createTimeout(duration, Schedulers.parallel());
    }

    static <T> SinkManyReplayProcessor<T> createTimeout(Duration duration, Scheduler scheduler) {
        return createSizeAndTimeout(Integer.MAX_VALUE, duration, scheduler);
    }

    static <T> SinkManyReplayProcessor<T> createSizeAndTimeout(int i, Duration duration) {
        return createSizeAndTimeout(i, duration, Schedulers.parallel());
    }

    static <T> SinkManyReplayProcessor<T> createSizeAndTimeout(int i, Duration duration, Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        if (i <= 0) {
            throw new IllegalArgumentException("size > 0 required but it was " + i);
        }
        return new SinkManyReplayProcessor<>(new FluxReplay.SizeAndTimeBoundReplayBuffer(i, duration.toNanos(), scheduler));
    }

    SinkManyReplayProcessor(FluxReplay.ReplayBuffer<T> replayBuffer) {
        this.buffer = replayBuffer;
        SUBSCRIBERS.lazySet(this, FluxReplay.ReplaySubscriber.EMPTY);
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Objects.requireNonNull(coreSubscriber, "subscribe");
        CoreSubscriber coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled = Operators.restoreContextOnSubscriberIfAutoCPEnabled(this, coreSubscriber);
        ReplayInner replayInner = new ReplayInner(coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled, this);
        coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled.onSubscribe(replayInner);
        if (add(replayInner) && replayInner.isCancelled()) {
            remove(replayInner);
        } else {
            this.buffer.replay(replayInner);
        }
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PARENT) {
            return this.subscription;
        }
        if (attr == Scannable.Attr.CAPACITY) {
            return Integer.valueOf(this.buffer.capacity());
        }
        if (attr == Scannable.Attr.TERMINATED) {
            return Boolean.valueOf(this.buffer.isDone());
        }
        if (attr == Scannable.Attr.ERROR) {
            return this.buffer.getError();
        }
        return null;
    }

    @Override // reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        return Stream.of((Object[]) this.subscribers);
    }

    @Override // reactor.core.Disposable
    public void dispose() {
        emitError(new CancellationException("Disposed"), Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        return this.buffer.isDone();
    }

    /* JADX WARN: Multi-variable type inference failed */
    boolean add(FluxReplay.ReplaySubscription<T> replaySubscription) {
        FluxReplay.ReplaySubscription<T>[] replaySubscriptionArr;
        ReplayInner[] replayInnerArr;
        do {
            replaySubscriptionArr = this.subscribers;
            if (replaySubscriptionArr == FluxReplay.ReplaySubscriber.TERMINATED) {
                return false;
            }
            int length = replaySubscriptionArr.length;
            replayInnerArr = new ReplayInner[length + 1];
            System.arraycopy(replaySubscriptionArr, 0, replayInnerArr, 0, length);
            replayInnerArr[length] = replaySubscription;
        } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, replaySubscriptionArr, replayInnerArr));
        return true;
    }

    void remove(FluxReplay.ReplaySubscription<T> replaySubscription) {
        FluxReplay.ReplaySubscription<T>[] replaySubscriptionArr;
        FluxReplay.ReplaySubscription[] replaySubscriptionArr2;
        do {
            replaySubscriptionArr = this.subscribers;
            if (replaySubscriptionArr == FluxReplay.ReplaySubscriber.TERMINATED || replaySubscriptionArr == FluxReplay.ReplaySubscriber.EMPTY) {
                return;
            }
            int length = replaySubscriptionArr.length;
            for (int i = 0; i < length; i++) {
                if (replaySubscriptionArr[i] == replaySubscription) {
                    if (length == 1) {
                        replaySubscriptionArr2 = FluxReplay.ReplaySubscriber.EMPTY;
                    } else {
                        ReplayInner[] replayInnerArr = new ReplayInner[length - 1];
                        System.arraycopy(replaySubscriptionArr, 0, replayInnerArr, 0, i);
                        System.arraycopy(replaySubscriptionArr, i + 1, replayInnerArr, i, (length - i) - 1);
                        replaySubscriptionArr2 = replayInnerArr;
                    }
                }
            }
            return;
        } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, replaySubscriptionArr, replaySubscriptionArr2));
    }

    @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (this.buffer.isDone()) {
            subscription.cancel();
        } else if (Operators.validate(this.subscription, subscription)) {
            this.subscription = subscription;
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // reactor.core.publisher.ContextHolder
    public Context currentContext() {
        return Operators.multiSubscribersContext(this.subscribers);
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        tryEmitComplete();
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitComplete() {
        FluxReplay.ReplayBuffer<T> replayBuffer = this.buffer;
        if (replayBuffer.isDone()) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        replayBuffer.onComplete();
        for (FluxReplay.ReplaySubscription<T> replaySubscription : SUBSCRIBERS.getAndSet(this, FluxReplay.ReplaySubscriber.TERMINATED)) {
            replayBuffer.replay(replaySubscription);
        }
        return Sinks.EmitResult.OK;
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        emitError(th, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitError(Throwable th) {
        FluxReplay.ReplayBuffer<T> replayBuffer = this.buffer;
        if (replayBuffer.isDone()) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        replayBuffer.onError(th);
        for (FluxReplay.ReplaySubscription<T> replaySubscription : SUBSCRIBERS.getAndSet(this, FluxReplay.ReplaySubscriber.TERMINATED)) {
            replayBuffer.replay(replaySubscription);
        }
        return Sinks.EmitResult.OK;
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitNext(T t) {
        FluxReplay.ReplayBuffer<T> replayBuffer = this.buffer;
        if (replayBuffer.isDone()) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        replayBuffer.add(t);
        for (FluxReplay.ReplaySubscription<T> replaySubscription : this.subscribers) {
            replayBuffer.replay(replaySubscription);
        }
        return Sinks.EmitResult.OK;
    }

    @Override // reactor.core.publisher.Sinks.Many
    public int currentSubscriberCount() {
        return this.subscribers.length;
    }

    static final class ReplayInner<T> implements FluxReplay.ReplaySubscription<T> {
        final CoreSubscriber<? super T> actual;
        final FluxReplay.ReplayBuffer<T> buffer;
        int fusionMode;
        int index;
        Object node;
        final SinkManyReplayProcessor<T> parent;
        volatile long requested;
        int tailIndex;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ReplayInner> WIP = AtomicIntegerFieldUpdater.newUpdater(ReplayInner.class, "wip");
        static final AtomicLongFieldUpdater<ReplayInner> REQUESTED = AtomicLongFieldUpdater.newUpdater(ReplayInner.class, "requested");

        ReplayInner(CoreSubscriber<? super T> coreSubscriber, SinkManyReplayProcessor<T> sinkManyReplayProcessor) {
            this.actual = coreSubscriber;
            this.parent = sinkManyReplayProcessor;
            this.buffer = sinkManyReplayProcessor.buffer;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public long requested() {
            return this.requested;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public boolean isCancelled() {
            return this.requested == Long.MIN_VALUE;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription, reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.fusionMode = 2;
            return 2;
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            return this.buffer.poll(this);
        }

        @Override // java.util.Collection
        public void clear() {
            this.buffer.clear(this);
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.buffer.isEmpty(this);
        }

        @Override // java.util.Collection
        public int size() {
            return this.buffer.size(this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                if (fusionMode() == 0) {
                    Operators.addCapCancellable(REQUESTED, this, j);
                }
                this.buffer.replay(this);
            }
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public void requestMore(int i) {
            this.index = i;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (REQUESTED.getAndSet(this, Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.remove(this);
                if (enter()) {
                    this.node = null;
                }
            }
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public void node(@Nullable Object obj) {
            this.node = obj;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public int fusionMode() {
            return this.fusionMode;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        @Nullable
        public Object node() {
            return this.node;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public int index() {
            return this.index;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public void index(int i) {
            this.index = i;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public int tailIndex() {
            return this.tailIndex;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public void tailIndex(int i) {
            this.tailIndex = i;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public boolean enter() {
            return WIP.getAndIncrement(this) == 0;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public int leave(int i) {
            return WIP.addAndGet(this, -i);
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public void produced(long j) {
            REQUESTED.addAndGet(this, -j);
        }
    }
}
