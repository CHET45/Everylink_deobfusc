package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxPublish<T> extends ConnectableFlux<T> implements Scannable {
    static final AtomicReferenceFieldUpdater<FluxPublish, PublishSubscriber> CONNECTION = AtomicReferenceFieldUpdater.newUpdater(FluxPublish.class, PublishSubscriber.class, "connection");
    volatile PublishSubscriber<T> connection;
    final int prefetch;
    final Supplier<? extends Queue<T>> queueSupplier;
    final boolean resetUponSourceTermination;
    final Flux<? extends T> source;

    FluxPublish(Flux<? extends T> flux, int i, Supplier<? extends Queue<T>> supplier, boolean z) {
        if (i <= 0) {
            throw new IllegalArgumentException("bufferSize > 0 required but it was " + i);
        }
        this.source = Flux.from((Publisher) Objects.requireNonNull(flux, "source"));
        this.prefetch = i;
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
        this.resetUponSourceTermination = z;
    }

    @Override // reactor.core.publisher.ConnectableFlux
    public void connect(Consumer<? super Disposable> consumer) {
        PublishSubscriber<T> publishSubscriber;
        while (true) {
            publishSubscriber = this.connection;
            if (publishSubscriber != null && !publishSubscriber.isTerminated()) {
                break;
            }
            PublishSubscriber<T> publishSubscriber2 = new PublishSubscriber<>(this.prefetch, this);
            if (C0162xc40028dd.m5m(CONNECTION, this, publishSubscriber, publishSubscriber2)) {
                publishSubscriber = publishSubscriber2;
                break;
            }
        }
        boolean zTryConnect = publishSubscriber.tryConnect();
        consumer.accept(publishSubscriber);
        if (zTryConnect) {
            this.source.subscribe((CoreSubscriber<? super Object>) publishSubscriber);
        }
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        PublishInner<T> publishInner = new PublishInner<>(coreSubscriber);
        coreSubscriber.onSubscribe(publishInner);
        while (!publishInner.isCancelled()) {
            PublishSubscriber<T> publishSubscriber = this.connection;
            if (publishSubscriber == null || (this.resetUponSourceTermination && publishSubscriber.isTerminated())) {
                PublishSubscriber<T> publishSubscriber2 = new PublishSubscriber<>(this.prefetch, this);
                if (C0162xc40028dd.m5m(CONNECTION, this, publishSubscriber, publishSubscriber2)) {
                    publishSubscriber = publishSubscriber2;
                } else {
                    continue;
                }
            }
            if (publishSubscriber.add(publishInner)) {
                if (publishInner.isCancelled()) {
                    publishSubscriber.remove(publishInner);
                } else {
                    publishInner.parent = publishSubscriber;
                }
                publishSubscriber.drainFromInner();
                return;
            }
            if (!this.resetUponSourceTermination) {
                if (publishSubscriber.error != null) {
                    publishInner.actual.onError(publishSubscriber.error);
                    return;
                } else {
                    publishInner.actual.onComplete();
                    return;
                }
            }
        }
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    static final class PublishSubscriber<T> implements InnerConsumer<T>, Disposable {
        static final long CANCELLED_FLAG = 2305843009213693952L;
        static final long CONNECTED_FLAG = 288230376151711744L;
        static final long FINALIZED_FLAG = Long.MIN_VALUE;
        static final long SUBSCRIPTION_SET_FLAG = 576460752303423488L;
        static final long TERMINATED_FLAG = 4611686018427387904L;
        static final long WORK_IN_PROGRESS_MASK = 4294967295L;
        boolean done;
        volatile Throwable error;
        final FluxPublish<T> parent;
        final int prefetch;
        Queue<T> queue;

        /* JADX INFO: renamed from: s */
        Subscription f2169s;
        int sourceMode;
        volatile long state;
        volatile PubSubInner<T>[] subscribers;
        static final AtomicReferenceFieldUpdater<PublishSubscriber, PubSubInner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(PublishSubscriber.class, PubSubInner[].class, "subscribers");
        static final AtomicLongFieldUpdater<PublishSubscriber> STATE = AtomicLongFieldUpdater.newUpdater(PublishSubscriber.class, "state");
        static final PubSubInner[] INIT = new PublishInner[0];
        static final PubSubInner[] CANCELLED = new PublishInner[0];
        static final PubSubInner[] TERMINATED = new PublishInner[0];
        static final AtomicReferenceFieldUpdater<PublishSubscriber, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(PublishSubscriber.class, Throwable.class, "error");

        static long addWork(long j) {
            return (j & WORK_IN_PROGRESS_MASK) == WORK_IN_PROGRESS_MASK ? (j & (-4294967296L)) | 1 : j + 1;
        }

        static boolean hasWorkInProgress(long j) {
            return (j & WORK_IN_PROGRESS_MASK) > 0;
        }

        static boolean isCancelled(long j) {
            return (j & 2305843009213693952L) == 2305843009213693952L;
        }

        static boolean isConnected(long j) {
            return (j & CONNECTED_FLAG) == CONNECTED_FLAG;
        }

        static boolean isFinalized(long j) {
            return (j & Long.MIN_VALUE) == Long.MIN_VALUE;
        }

        static boolean isSubscriptionSet(long j) {
            return (j & SUBSCRIPTION_SET_FLAG) == SUBSCRIPTION_SET_FLAG;
        }

        static boolean isTerminated(long j) {
            return (j & TERMINATED_FLAG) == TERMINATED_FLAG;
        }

        PublishSubscriber(int i, FluxPublish<T> fluxPublish) {
            this.prefetch = i;
            this.parent = fluxPublish;
            SUBSCRIBERS.lazySet(this, INIT);
        }

        boolean isTerminated() {
            return this.subscribers == TERMINATED;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2169s, subscription)) {
                this.f2169s = subscription;
                if (subscription instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription queueSubscription = (Fuseable.QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        long jMarkSubscriptionSetAndAddWork = markSubscriptionSetAndAddWork(this);
                        if (isCancelled(jMarkSubscriptionSetAndAddWork)) {
                            subscription.cancel();
                            return;
                        } else {
                            if (hasWorkInProgress(jMarkSubscriptionSetAndAddWork)) {
                                return;
                            }
                            drain(jMarkSubscriptionSetAndAddWork | 576460752303423489L);
                            return;
                        }
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        if (isCancelled(markSubscriptionSet(this))) {
                            subscription.cancel();
                            return;
                        } else {
                            subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
                            return;
                        }
                    }
                }
                this.queue = this.parent.queueSupplier.get();
                if (isCancelled(markSubscriptionSet(this))) {
                    subscription.cancel();
                } else {
                    subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(@Nullable T t) {
            if (this.done) {
                if (t != null) {
                    Operators.onNextDropped(t, currentContext());
                    return;
                }
                return;
            }
            if (this.sourceMode != 2 && !this.queue.offer(t)) {
                Throwable thOnOperatorError = Operators.onOperatorError(this.f2169s, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), t, currentContext());
                if (!Exceptions.addThrowable(ERROR, this, thOnOperatorError)) {
                    Operators.onErrorDroppedMulticast(thOnOperatorError, this.subscribers);
                    return;
                }
                this.done = true;
            }
            long jAddWork = addWork((PublishSubscriber<?>) this);
            if (isFinalized(jAddWork)) {
                clear();
            } else {
                if (isTerminated(jAddWork) || isCancelled(jAddWork) || hasWorkInProgress(jAddWork)) {
                    return;
                }
                drain(jAddWork + 1);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDroppedMulticast(th, this.subscribers);
                return;
            }
            if (!Exceptions.addThrowable(ERROR, this, th)) {
                Operators.onErrorDroppedMulticast(th, this.subscribers);
                return;
            }
            this.done = true;
            long jMarkTerminated = markTerminated(this);
            if (isTerminated(jMarkTerminated) || isCancelled(jMarkTerminated) || hasWorkInProgress(jMarkTerminated)) {
                return;
            }
            drain((jMarkTerminated | TERMINATED_FLAG) + 1);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            long jMarkTerminated = markTerminated(this);
            if (isTerminated(jMarkTerminated) || isCancelled(jMarkTerminated) || hasWorkInProgress(jMarkTerminated)) {
                return;
            }
            drain((jMarkTerminated | TERMINATED_FLAG) + 1);
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            if (SUBSCRIBERS.get(this) != TERMINATED && C0162xc40028dd.m5m(FluxPublish.CONNECTION, this.parent, this, null)) {
                long jMarkCancelled = markCancelled(this);
                if (isTerminated(jMarkCancelled) || isCancelled(jMarkCancelled) || hasWorkInProgress(jMarkCancelled)) {
                    return;
                }
                disconnectAction(jMarkCancelled);
            }
        }

        void clear() {
            if (this.sourceMode != 0) {
                this.queue.clear();
                return;
            }
            while (true) {
                T tPoll = this.queue.poll();
                if (tPoll == null) {
                    return;
                } else {
                    Operators.onDiscard(tPoll, currentContext());
                }
            }
        }

        void disconnectAction(long j) {
            if (isSubscriptionSet(j)) {
                this.f2169s.cancel();
                clear();
            }
            PubSubInner[] andSet = SUBSCRIBERS.getAndSet(this, CANCELLED);
            if (andSet.length > 0) {
                CancellationException cancellationException = new CancellationException("Disconnected");
                for (PubSubInner pubSubInner : andSet) {
                    pubSubInner.actual.onError(cancellationException);
                }
            }
        }

        boolean add(PublishInner<T> publishInner) {
            PubSubInner<T>[] pubSubInnerArr;
            PubSubInner[] pubSubInnerArr2;
            do {
                pubSubInnerArr = this.subscribers;
                if (pubSubInnerArr == TERMINATED) {
                    return false;
                }
                int length = pubSubInnerArr.length;
                pubSubInnerArr2 = new PubSubInner[length + 1];
                System.arraycopy(pubSubInnerArr, 0, pubSubInnerArr2, 0, length);
                pubSubInnerArr2[length] = publishInner;
            } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, pubSubInnerArr, pubSubInnerArr2));
            return true;
        }

        public void remove(PubSubInner<T> pubSubInner) {
            PubSubInner<T>[] pubSubInnerArr;
            PubSubInner[] pubSubInnerArr2;
            do {
                pubSubInnerArr = this.subscribers;
                if (pubSubInnerArr == TERMINATED || pubSubInnerArr == CANCELLED) {
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
                    pubSubInnerArr2 = CANCELLED;
                } else {
                    PubSubInner[] pubSubInnerArr3 = new PubSubInner[length - 1];
                    System.arraycopy(pubSubInnerArr, 0, pubSubInnerArr3, 0, i);
                    System.arraycopy(pubSubInnerArr, i + 1, pubSubInnerArr3, i, (length - i) - 1);
                    pubSubInnerArr2 = pubSubInnerArr3;
                }
            } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, pubSubInnerArr, pubSubInnerArr2));
        }

        PubSubInner<T>[] terminate() {
            return SUBSCRIBERS.getAndSet(this, TERMINATED);
        }

        boolean tryConnect() {
            return !isConnected(markConnected(this));
        }

        final void drainFromInner() {
            long jAddWorkIfSubscribed = addWorkIfSubscribed(this);
            if (isSubscriptionSet(jAddWorkIfSubscribed) && !hasWorkInProgress(jAddWorkIfSubscribed)) {
                drain(jAddWorkIfSubscribed + 1);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:100:0x011f A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:86:0x0123  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        final void drain(long r25) {
            /*
                Method dump skipped, instruction units count: 298
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.FluxPublish.PublishSubscriber.drain(long):void");
        }

        boolean checkTerminated(boolean z, boolean z2, @Nullable T t) {
            long j = this.state;
            if (isCancelled(j)) {
                Operators.onDiscard(t, currentContext());
                disconnectAction(j);
                return true;
            }
            int i = 0;
            if (z) {
                Throwable thTerminate = this.error;
                if (thTerminate != null && thTerminate != Exceptions.TERMINATED) {
                    if (this.parent.resetUponSourceTermination) {
                        C0162xc40028dd.m5m(FluxPublish.CONNECTION, this.parent, this, null);
                        thTerminate = Exceptions.terminate(ERROR, this);
                    }
                    this.queue.clear();
                    PubSubInner<T>[] pubSubInnerArrTerminate = terminate();
                    int length = pubSubInnerArrTerminate.length;
                    while (i < length) {
                        pubSubInnerArrTerminate[i].actual.onError(thTerminate);
                        i++;
                    }
                    return true;
                }
                if (z2) {
                    if (this.parent.resetUponSourceTermination) {
                        C0162xc40028dd.m5m(FluxPublish.CONNECTION, this.parent, this, null);
                    }
                    PubSubInner<T>[] pubSubInnerArrTerminate2 = terminate();
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

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers);
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return Operators.multiSubscribersContext(this.subscribers);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2169s;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.BUFFERED) {
                Queue<T> queue = this.queue;
                return Integer.valueOf(queue != null ? queue.size() : 0);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(isTerminated());
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2169s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            long j = this.state;
            return isTerminated(j) || isCancelled(j);
        }

        static void clearAndFinalize(PublishSubscriber<?> publishSubscriber) {
            long j;
            do {
                j = publishSubscriber.state;
                if (isFinalized(j)) {
                    publishSubscriber.clear();
                    return;
                } else if (isSubscriptionSet(j)) {
                    publishSubscriber.clear();
                }
            } while (!STATE.compareAndSet(publishSubscriber, j, ((-4294967296L) & j) | Long.MIN_VALUE));
        }

        static long addWork(PublishSubscriber<?> publishSubscriber) {
            long j;
            do {
                j = publishSubscriber.state;
            } while (!STATE.compareAndSet(publishSubscriber, j, addWork(j)));
            return j;
        }

        static long addWorkIfSubscribed(PublishSubscriber<?> publishSubscriber) {
            long j;
            do {
                j = publishSubscriber.state;
                if (!isSubscriptionSet(j)) {
                    return j;
                }
            } while (!STATE.compareAndSet(publishSubscriber, j, addWork(j)));
            return j;
        }

        static long markTerminated(PublishSubscriber<?> publishSubscriber) {
            long j;
            do {
                j = publishSubscriber.state;
                if (isCancelled(j) || isTerminated(j)) {
                    break;
                }
            } while (!STATE.compareAndSet(publishSubscriber, j, addWork(j) | TERMINATED_FLAG));
            return j;
        }

        static long markConnected(PublishSubscriber<?> publishSubscriber) {
            long j;
            do {
                j = publishSubscriber.state;
                if (isConnected(j)) {
                    return j;
                }
            } while (!STATE.compareAndSet(publishSubscriber, j, j | CONNECTED_FLAG));
            return j;
        }

        static long markSubscriptionSet(PublishSubscriber<?> publishSubscriber) {
            long j;
            do {
                j = publishSubscriber.state;
                if (isCancelled(j)) {
                    return j;
                }
            } while (!STATE.compareAndSet(publishSubscriber, j, j | SUBSCRIPTION_SET_FLAG));
            return j;
        }

        static long markSubscriptionSetAndAddWork(PublishSubscriber<?> publishSubscriber) {
            long j;
            do {
                j = publishSubscriber.state;
                if (isCancelled(j)) {
                    return j;
                }
            } while (!STATE.compareAndSet(publishSubscriber, j, addWork(j) | SUBSCRIPTION_SET_FLAG));
            return j;
        }

        static long markCancelled(PublishSubscriber<?> publishSubscriber) {
            long j;
            do {
                j = publishSubscriber.state;
                if (isCancelled(j)) {
                    return j;
                }
            } while (!STATE.compareAndSet(publishSubscriber, j, addWork(j) | 2305843009213693952L));
            return j;
        }

        static long markWorkDone(PublishSubscriber<?> publishSubscriber, long j) {
            long j2;
            long j3;
            do {
                j2 = publishSubscriber.state;
                if (j != j2) {
                    return j2;
                }
                j3 = j2 & (-4294967296L);
            } while (!STATE.compareAndSet(publishSubscriber, j2, j3));
            return j3;
        }
    }

    static abstract class PubSubInner<T> implements InnerProducer<T> {
        static final AtomicLongFieldUpdater<PubSubInner> REQUESTED = AtomicLongFieldUpdater.newUpdater(PubSubInner.class, "requested");
        final CoreSubscriber<? super T> actual;
        volatile long requested;

        abstract void drainParent();

        abstract void removeAndDrainParent();

        PubSubInner(CoreSubscriber<? super T> coreSubscriber) {
            this.actual = coreSubscriber;
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCapCancellable(REQUESTED, this, j);
                drainParent();
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            if (this.requested == Long.MIN_VALUE || REQUESTED.getAndSet(this, Long.MIN_VALUE) == Long.MIN_VALUE) {
                return;
            }
            removeAndDrainParent();
        }

        final boolean isCancelled() {
            return this.requested == Long.MIN_VALUE;
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isCancelled());
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(isCancelled() ? 0L : this.requested);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static final class PublishInner<T> extends PubSubInner<T> {
        PublishSubscriber<T> parent;

        PublishInner(CoreSubscriber<? super T> coreSubscriber) {
            super(coreSubscriber);
        }

        @Override // reactor.core.publisher.FluxPublish.PubSubInner
        void drainParent() {
            PublishSubscriber<T> publishSubscriber = this.parent;
            if (publishSubscriber != null) {
                publishSubscriber.drainFromInner();
            }
        }

        @Override // reactor.core.publisher.FluxPublish.PubSubInner
        void removeAndDrainParent() {
            PublishSubscriber<T> publishSubscriber = this.parent;
            if (publishSubscriber != null) {
                publishSubscriber.remove(this);
                publishSubscriber.drainFromInner();
            }
        }

        @Override // reactor.core.publisher.FluxPublish.PubSubInner, reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (attr != Scannable.Attr.TERMINATED) {
                return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            PublishSubscriber<T> publishSubscriber = this.parent;
            return Boolean.valueOf(publishSubscriber != null && publishSubscriber.isTerminated());
        }
    }
}
