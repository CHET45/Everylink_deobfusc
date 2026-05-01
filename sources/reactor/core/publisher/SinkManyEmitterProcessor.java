package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPublish;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class SinkManyEmitterProcessor<T> extends Flux<T> implements InternalManySink<T>, Sinks.ManyWithUpstream<T>, CoreSubscriber<T>, Scannable, Disposable, ContextHolder {
    final boolean autoCancel;
    volatile boolean done;
    volatile Throwable error;
    final int prefetch;
    volatile Queue<T> queue;

    /* JADX INFO: renamed from: s */
    volatile Subscription f2296s;
    int sourceMode;
    volatile FluxPublish.PubSubInner<T>[] subscribers;
    volatile EmitterDisposable upstreamDisposable;
    volatile int wip;
    static final FluxPublish.PubSubInner[] EMPTY = new FluxPublish.PublishInner[0];

    /* JADX INFO: renamed from: S */
    static final AtomicReferenceFieldUpdater<SinkManyEmitterProcessor, Subscription> f2295S = AtomicReferenceFieldUpdater.newUpdater(SinkManyEmitterProcessor.class, Subscription.class, "s");
    static final AtomicReferenceFieldUpdater<SinkManyEmitterProcessor, FluxPublish.PubSubInner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(SinkManyEmitterProcessor.class, FluxPublish.PubSubInner[].class, "subscribers");
    static final AtomicReferenceFieldUpdater<SinkManyEmitterProcessor, EmitterDisposable> UPSTREAM_DISPOSABLE = AtomicReferenceFieldUpdater.newUpdater(SinkManyEmitterProcessor.class, EmitterDisposable.class, "upstreamDisposable");
    static final AtomicIntegerFieldUpdater<SinkManyEmitterProcessor> WIP = AtomicIntegerFieldUpdater.newUpdater(SinkManyEmitterProcessor.class, "wip");
    static final AtomicReferenceFieldUpdater<SinkManyEmitterProcessor, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(SinkManyEmitterProcessor.class, Throwable.class, "error");

    @Override // reactor.core.publisher.Sinks.Many
    public Flux<T> asFlux() {
        return this;
    }

    SinkManyEmitterProcessor(boolean z, int i) {
        if (i < 1) {
            throw new IllegalArgumentException("bufferSize must be strictly positive, was: " + i);
        }
        this.autoCancel = z;
        this.prefetch = i;
        SUBSCRIBERS.lazySet(this, EMPTY);
    }

    @Override // reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        return Stream.of((Object[]) this.subscribers);
    }

    @Override // reactor.core.publisher.ContextHolder
    public Context currentContext() {
        return Operators.multiSubscribersContext(this.subscribers);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDetached() {
        return this.f2296s == Operators.cancelledSubscription() && this.done && (this.error instanceof CancellationException);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean detach() {
        if (Operators.terminate(f2295S, this)) {
            this.done = true;
            CancellationException cancellationException = new CancellationException("the ManyWithUpstream sink had a Subscription to an upstream which has been manually cancelled");
            if (C0162xc40028dd.m5m(ERROR, this, null, cancellationException)) {
                Queue<T> queue = this.queue;
                if (queue != null) {
                    queue.clear();
                }
                for (FluxPublish.PubSubInner<T> pubSubInner : terminate()) {
                    pubSubInner.actual.onError(cancellationException);
                }
                return true;
            }
        }
        return false;
    }

    @Override // reactor.core.publisher.Sinks.ManyWithUpstream
    public Disposable subscribeTo(Publisher<? extends T> publisher) {
        EmitterDisposable emitterDisposable = new EmitterDisposable(this);
        if (C0162xc40028dd.m5m(UPSTREAM_DISPOSABLE, this, null, emitterDisposable)) {
            publisher.subscribe(this);
            return emitterDisposable;
        }
        throw new IllegalStateException("A Sinks.ManyWithUpstream must be subscribed to a source only once");
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Objects.requireNonNull(coreSubscriber, "subscribe");
        CoreSubscriber coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled = Operators.restoreContextOnSubscriberIfAutoCPEnabled(this, coreSubscriber);
        EmitterInner<T> emitterInner = new EmitterInner<>(coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled, this);
        coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled.onSubscribe(emitterInner);
        if (emitterInner.isCancelled()) {
            return;
        }
        if (add(emitterInner)) {
            if (emitterInner.isCancelled()) {
                remove(emitterInner);
            }
            drain();
        } else {
            Throwable th = this.error;
            if (th != null) {
                emitterInner.actual.onError(th);
            } else {
                emitterInner.actual.onComplete();
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        tryEmitComplete();
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitComplete() {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.done = true;
        drain();
        return Sinks.EmitResult.OK;
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        emitError(th, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitError(Throwable th) {
        Objects.requireNonNull(th, "tryEmitError must be invoked with a non-null Throwable");
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (Exceptions.addThrowable(ERROR, this, th)) {
            this.done = true;
            drain();
            return Sinks.EmitResult.OK;
        }
        return Sinks.EmitResult.FAIL_TERMINATED;
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (this.sourceMode == 2) {
            drain();
        } else {
            emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
        }
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitNext(T t) {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        Objects.requireNonNull(t, "tryEmitNext must be invoked with a non-null value");
        Queue<T> queue = this.queue;
        if (queue == null) {
            if (Operators.setOnce(f2295S, this, Operators.emptySubscription())) {
                queue = (Queue) Queues.get(this.prefetch).get();
                this.queue = queue;
            } else {
                while (!isCancelled()) {
                    queue = this.queue;
                    if (queue != null) {
                    }
                }
                return Sinks.EmitResult.FAIL_CANCELLED;
            }
        }
        if (!queue.offer(t)) {
            return this.subscribers == EMPTY ? Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER : Sinks.EmitResult.FAIL_OVERFLOW;
        }
        drain();
        return Sinks.EmitResult.OK;
    }

    @Override // reactor.core.publisher.Sinks.Many
    public int currentSubscriberCount() {
        return this.subscribers.length;
    }

    int getPending() {
        Queue<T> queue = this.queue;
        if (queue != null) {
            return queue.size();
        }
        return 0;
    }

    @Override // reactor.core.Disposable
    public void dispose() {
        onError(new CancellationException("Disposed"));
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        return isTerminated() || isCancelled();
    }

    @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (Operators.setOnce(f2295S, this, subscription)) {
            if (subscription instanceof Fuseable.QueueSubscription) {
                Fuseable.QueueSubscription queueSubscription = (Fuseable.QueueSubscription) subscription;
                int iRequestFusion = queueSubscription.requestFusion(3);
                if (iRequestFusion == 1) {
                    this.sourceMode = iRequestFusion;
                    this.queue = queueSubscription;
                    drain();
                    return;
                } else if (iRequestFusion == 2) {
                    this.sourceMode = iRequestFusion;
                    this.queue = queueSubscription;
                    subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
                    return;
                }
            }
            this.queue = (Queue) Queues.get(this.prefetch).get();
            subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
        }
    }

    @Nullable
    Throwable getError() {
        return this.error;
    }

    boolean isCancelled() {
        return Operators.cancelledSubscription() == this.f2296s;
    }

    public boolean isTerminated() {
        return this.done && getPending() == 0;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.f2296s : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(getPending()) : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(isCancelled()) : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(isTerminated()) : attr == Scannable.Attr.ERROR ? getError() : attr == Scannable.Attr.CAPACITY ? Integer.valueOf(getPrefetch()) : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x011d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:102:0x000c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void drain() {
        /*
            Method dump skipped, instruction units count: 286
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.SinkManyEmitterProcessor.drain():void");
    }

    FluxPublish.PubSubInner<T>[] terminate() {
        return SUBSCRIBERS.getAndSet(this, FluxPublish.PublishSubscriber.TERMINATED);
    }

    boolean checkTerminated(boolean z, boolean z2) {
        if (this.f2296s == Operators.cancelledSubscription()) {
            if (this.autoCancel) {
                terminate();
                Queue<T> queue = this.queue;
                if (queue != null) {
                    queue.clear();
                }
            }
            return true;
        }
        int i = 0;
        if (z) {
            Throwable th = this.error;
            if (th != null && th != Exceptions.TERMINATED) {
                Queue<T> queue2 = this.queue;
                if (queue2 != null) {
                    queue2.clear();
                }
                FluxPublish.PubSubInner<T>[] pubSubInnerArrTerminate = terminate();
                int length = pubSubInnerArrTerminate.length;
                while (i < length) {
                    pubSubInnerArrTerminate[i].actual.onError(th);
                    i++;
                }
                return true;
            }
            if (z2) {
                FluxPublish.PubSubInner<T>[] pubSubInnerArrTerminate2 = terminate();
                int length2 = pubSubInnerArrTerminate2.length;
                while (i < length2) {
                    pubSubInnerArrTerminate2[i].actual.onComplete();
                    i++;
                }
                return true;
            }
        }
        return false;
    }

    final boolean add(EmitterInner<T> emitterInner) {
        FluxPublish.PubSubInner<T>[] pubSubInnerArr;
        FluxPublish.PubSubInner[] pubSubInnerArr2;
        do {
            pubSubInnerArr = this.subscribers;
            if (pubSubInnerArr == FluxPublish.PublishSubscriber.TERMINATED) {
                return false;
            }
            int length = pubSubInnerArr.length;
            pubSubInnerArr2 = new FluxPublish.PubSubInner[length + 1];
            System.arraycopy(pubSubInnerArr, 0, pubSubInnerArr2, 0, length);
            pubSubInnerArr2[length] = emitterInner;
        } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, pubSubInnerArr, pubSubInnerArr2));
        return true;
    }

    final void remove(FluxPublish.PubSubInner<T> pubSubInner) {
        FluxPublish.PubSubInner<T>[] pubSubInnerArr;
        FluxPublish.PubSubInner[] pubSubInnerArr2;
        do {
            pubSubInnerArr = this.subscribers;
            if (pubSubInnerArr == FluxPublish.PublishSubscriber.TERMINATED || pubSubInnerArr == EMPTY) {
                return;
            }
            int length = pubSubInnerArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    i = -1;
                    break;
                } else if (pubSubInnerArr[i] == pubSubInner) {
                    break;
                } else {
                    i++;
                }
            }
            if (i < 0) {
                return;
            }
            if (length == 1) {
                pubSubInnerArr2 = EMPTY;
            } else {
                FluxPublish.PubSubInner[] pubSubInnerArr3 = new FluxPublish.PubSubInner[length - 1];
                System.arraycopy(pubSubInnerArr, 0, pubSubInnerArr3, 0, i);
                System.arraycopy(pubSubInnerArr, i + 1, pubSubInnerArr3, i, (length - i) - 1);
                pubSubInnerArr2 = pubSubInnerArr3;
            }
        } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, pubSubInnerArr, pubSubInnerArr2));
        if (this.autoCancel && pubSubInnerArr2 == EMPTY && Operators.terminate(f2295S, this) && WIP.getAndIncrement(this) == 0) {
            terminate();
            Queue<T> queue = this.queue;
            if (queue != null) {
                queue.clear();
            }
        }
    }

    static final class EmitterInner<T> extends FluxPublish.PubSubInner<T> {
        final SinkManyEmitterProcessor<T> parent;

        EmitterInner(CoreSubscriber<? super T> coreSubscriber, SinkManyEmitterProcessor<T> sinkManyEmitterProcessor) {
            super(coreSubscriber);
            this.parent = sinkManyEmitterProcessor;
        }

        @Override // reactor.core.publisher.FluxPublish.PubSubInner
        void drainParent() {
            this.parent.drain();
        }

        @Override // reactor.core.publisher.FluxPublish.PubSubInner
        void removeAndDrainParent() {
            this.parent.remove(this);
            this.parent.drain();
        }
    }

    static final class EmitterDisposable implements Disposable {

        @Nullable
        SinkManyEmitterProcessor<?> target;

        public EmitterDisposable(SinkManyEmitterProcessor<?> sinkManyEmitterProcessor) {
            this.target = sinkManyEmitterProcessor;
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            SinkManyEmitterProcessor<?> sinkManyEmitterProcessor = this.target;
            return sinkManyEmitterProcessor == null || sinkManyEmitterProcessor.isDetached();
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            SinkManyEmitterProcessor<?> sinkManyEmitterProcessor = this.target;
            if (sinkManyEmitterProcessor == null) {
                return;
            }
            if (sinkManyEmitterProcessor.detach() || sinkManyEmitterProcessor.isDetached()) {
                this.target = null;
            }
        }
    }
}
