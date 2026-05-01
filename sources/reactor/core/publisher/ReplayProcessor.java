package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
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
@Deprecated
public final class ReplayProcessor<T> extends FluxProcessor<T, T> implements Fuseable, InternalManySink<T> {
    static final AtomicReferenceFieldUpdater<ReplayProcessor, FluxReplay.ReplaySubscription[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(ReplayProcessor.class, FluxReplay.ReplaySubscription[].class, "subscribers");
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

    @Override // reactor.core.publisher.FluxProcessor
    protected boolean isIdentityProcessor() {
        return true;
    }

    @Deprecated
    public static <T> ReplayProcessor<T> cacheLast() {
        return cacheLastOrDefault(null);
    }

    @Deprecated
    public static <T> ReplayProcessor<T> cacheLastOrDefault(@Nullable T t) {
        ReplayProcessor<T> replayProcessorCreate = create(1);
        if (t != null) {
            replayProcessorCreate.onNext(t);
        }
        return replayProcessorCreate;
    }

    @Deprecated
    public static <E> ReplayProcessor<E> create() {
        return create(Queues.SMALL_BUFFER_SIZE, true);
    }

    @Deprecated
    public static <E> ReplayProcessor<E> create(int i) {
        return create(i, false);
    }

    @Deprecated
    public static <E> ReplayProcessor<E> create(int i, boolean z) {
        FluxReplay.ReplayBuffer sizeBoundReplayBuffer;
        if (z) {
            sizeBoundReplayBuffer = new FluxReplay.UnboundedReplayBuffer(i);
        } else {
            sizeBoundReplayBuffer = new FluxReplay.SizeBoundReplayBuffer(i);
        }
        return new ReplayProcessor<>(sizeBoundReplayBuffer);
    }

    @Deprecated
    public static <T> ReplayProcessor<T> createTimeout(Duration duration) {
        return createTimeout(duration, Schedulers.parallel());
    }

    @Deprecated
    public static <T> ReplayProcessor<T> createTimeout(Duration duration, Scheduler scheduler) {
        return createSizeAndTimeout(Integer.MAX_VALUE, duration, scheduler);
    }

    @Deprecated
    public static <T> ReplayProcessor<T> createSizeAndTimeout(int i, Duration duration) {
        return createSizeAndTimeout(i, duration, Schedulers.parallel());
    }

    @Deprecated
    public static <T> ReplayProcessor<T> createSizeAndTimeout(int i, Duration duration, Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        if (i <= 0) {
            throw new IllegalArgumentException("size > 0 required but it was " + i);
        }
        return new ReplayProcessor<>(new FluxReplay.SizeAndTimeBoundReplayBuffer(i, duration.toNanos(), scheduler));
    }

    ReplayProcessor(FluxReplay.ReplayBuffer<T> replayBuffer) {
        this.buffer = replayBuffer;
        SUBSCRIBERS.lazySet(this, FluxReplay.ReplaySubscriber.EMPTY);
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Objects.requireNonNull(coreSubscriber, "subscribe");
        ReplayInner replayInner = new ReplayInner(coreSubscriber, this);
        coreSubscriber.onSubscribe(replayInner);
        if (add(replayInner) && replayInner.isCancelled()) {
            remove(replayInner);
        } else {
            this.buffer.replay(replayInner);
        }
    }

    @Override // reactor.core.publisher.FluxProcessor
    @Nullable
    public Throwable getError() {
        return this.buffer.getError();
    }

    @Override // reactor.core.publisher.FluxProcessor, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PARENT) {
            return this.subscription;
        }
        return attr == Scannable.Attr.CAPACITY ? Integer.valueOf(this.buffer.capacity()) : super.scanUnsafe(attr);
    }

    @Override // reactor.core.publisher.FluxProcessor, reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        return Stream.of((Object[]) this.subscribers);
    }

    @Override // reactor.core.publisher.FluxProcessor
    public long downstreamCount() {
        return this.subscribers.length;
    }

    @Override // reactor.core.publisher.FluxProcessor
    public boolean isTerminated() {
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

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (this.buffer.isDone()) {
            subscription.cancel();
        } else if (Operators.validate(this.subscription, subscription)) {
            this.subscription = subscription;
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // reactor.core.publisher.FluxProcessor, reactor.core.CoreSubscriber
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
        final ReplayProcessor<T> parent;
        volatile long requested;
        int tailIndex;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ReplayInner> WIP = AtomicIntegerFieldUpdater.newUpdater(ReplayInner.class, "wip");
        static final AtomicLongFieldUpdater<ReplayInner> REQUESTED = AtomicLongFieldUpdater.newUpdater(ReplayInner.class, "requested");

        ReplayInner(CoreSubscriber<? super T> coreSubscriber, ReplayProcessor<T> replayProcessor) {
            this.actual = coreSubscriber;
            this.parent = replayProcessor;
            this.buffer = replayProcessor.buffer;
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
