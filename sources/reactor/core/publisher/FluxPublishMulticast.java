package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxPublishMulticast<T, R> extends InternalFluxOperator<T, R> implements Fuseable {
    final int prefetch;
    final Supplier<? extends Queue<T>> queueSupplier;
    final Function<? super Flux<T>, ? extends Publisher<? extends R>> transform;

    interface PublishMulticasterParent {
        void terminate();
    }

    FluxPublishMulticast(Flux<? extends T> flux, Function<? super Flux<T>, ? extends Publisher<? extends R>> function, int i, Supplier<? extends Queue<T>> supplier) {
        super(flux);
        if (i < 1) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.prefetch = i;
        this.transform = (Function) Objects.requireNonNull(function, "transform");
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        FluxPublishMulticaster fluxPublishMulticaster = new FluxPublishMulticaster(this.prefetch, this.queueSupplier, coreSubscriber.currentContext());
        CorePublisher fluxOrMono = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.transform.apply(fluxPublishMulticaster), "The transform returned a null Publisher"));
        if (fluxOrMono instanceof Fuseable) {
            fluxOrMono.subscribe((Subscriber) new CancelFuseableMulticaster(coreSubscriber, fluxPublishMulticaster));
        } else {
            fluxOrMono.subscribe((Subscriber) new CancelMulticaster(coreSubscriber, fluxPublishMulticaster));
        }
        return fluxPublishMulticaster;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class FluxPublishMulticaster<T> extends Flux<T> implements InnerConsumer<T>, PublishMulticasterParent {
        volatile boolean connected;
        final Context context;
        volatile boolean done;
        Throwable error;
        final int limit;
        final int prefetch;
        int produced;
        Queue<T> queue;
        final Supplier<? extends Queue<T>> queueSupplier;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2173s;
        int sourceMode;
        volatile PublishMulticastInner<T>[] subscribers;
        volatile int wip;

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<FluxPublishMulticaster, Subscription> f2172S = AtomicReferenceFieldUpdater.newUpdater(FluxPublishMulticaster.class, Subscription.class, "s");
        static final AtomicIntegerFieldUpdater<FluxPublishMulticaster> WIP = AtomicIntegerFieldUpdater.newUpdater(FluxPublishMulticaster.class, "wip");
        static final AtomicReferenceFieldUpdater<FluxPublishMulticaster, PublishMulticastInner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(FluxPublishMulticaster.class, PublishMulticastInner[].class, "subscribers");
        static final PublishMulticastInner[] EMPTY = new PublishMulticastInner[0];
        static final PublishMulticastInner[] TERMINATED = new PublishMulticastInner[0];

        FluxPublishMulticaster(int i, Supplier<? extends Queue<T>> supplier, Context context) {
            this.prefetch = i;
            this.limit = Operators.unboundedOrLimit(i);
            this.queueSupplier = supplier;
            SUBSCRIBERS.lazySet(this, EMPTY);
            this.context = context;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2173s;
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2173s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                Queue<T> queue = this.queue;
                return Integer.valueOf(queue != null ? queue.size() : 0);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers);
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.context;
        }

        @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
        public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
            PublishMulticastInner<T> publishMulticastInner = new PublishMulticastInner<>(this, coreSubscriber);
            coreSubscriber.onSubscribe(publishMulticastInner);
            if (add(publishMulticastInner)) {
                if (publishMulticastInner.requested == Long.MIN_VALUE) {
                    remove(publishMulticastInner);
                    return;
                } else {
                    drain();
                    return;
                }
            }
            Throwable th = this.error;
            if (th != null) {
                coreSubscriber.onError(th);
            } else {
                coreSubscriber.onComplete();
            }
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2172S, this, subscription)) {
                if (subscription instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription queueSubscription = (Fuseable.QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(3);
                    if (iRequestFusion == 1) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.connected = true;
                        drain();
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = iRequestFusion;
                        this.queue = queueSubscription;
                        this.connected = true;
                        subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = this.queueSupplier.get();
                this.connected = true;
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.context);
            } else if (this.sourceMode != 2 && !this.queue.offer(t)) {
                onError(Operators.onOperatorError(this.f2173s, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), t, this.context));
            } else {
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.context);
                return;
            }
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            if (this.sourceMode == 1) {
                drainSync();
            } else {
                drainAsync();
            }
        }

        void drainSync() {
            int iAddAndGet = 1;
            do {
                if (this.connected) {
                    if (this.f2173s == Operators.cancelledSubscription()) {
                        this.queue.clear();
                        return;
                    }
                    Queue<T> queue = this.queue;
                    PublishMulticastInner<T>[] publishMulticastInnerArr = this.subscribers;
                    int length = publishMulticastInnerArr.length;
                    if (length != 0) {
                        int i = 0;
                        long jMin = Long.MAX_VALUE;
                        for (PublishMulticastInner<T> publishMulticastInner : publishMulticastInnerArr) {
                            long j = publishMulticastInner.requested;
                            if (j != Long.MIN_VALUE) {
                                jMin = Math.min(jMin, j);
                            }
                        }
                        long j2 = 0;
                        while (j2 != jMin) {
                            if (this.f2173s == Operators.cancelledSubscription()) {
                                queue.clear();
                                return;
                            }
                            try {
                                T tPoll = queue.poll();
                                if (tPoll == null) {
                                    PublishMulticastInner[] andSet = SUBSCRIBERS.getAndSet(this, TERMINATED);
                                    int length2 = andSet.length;
                                    while (i < length2) {
                                        andSet[i].actual.onComplete();
                                        i++;
                                    }
                                    return;
                                }
                                for (PublishMulticastInner<T> publishMulticastInner2 : publishMulticastInnerArr) {
                                    publishMulticastInner2.actual.onNext(tPoll);
                                }
                                j2++;
                            } catch (Throwable th) {
                                this.error = Operators.onOperatorError(this.f2173s, th, this.context);
                                queue.clear();
                                PublishMulticastInner[] andSet2 = SUBSCRIBERS.getAndSet(this, TERMINATED);
                                int length3 = andSet2.length;
                                while (i < length3) {
                                    andSet2[i].actual.onError(th);
                                    i++;
                                }
                                return;
                            }
                        }
                        if (this.f2173s == Operators.cancelledSubscription()) {
                            queue.clear();
                            return;
                        }
                        if (queue.isEmpty()) {
                            PublishMulticastInner[] andSet3 = SUBSCRIBERS.getAndSet(this, TERMINATED);
                            int length4 = andSet3.length;
                            while (i < length4) {
                                andSet3[i].actual.onComplete();
                                i++;
                            }
                            return;
                        }
                        if (j2 != 0) {
                            while (i < length) {
                                publishMulticastInnerArr[i].produced(j2);
                                i++;
                            }
                        }
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        void drainAsync() {
            int i = this.produced;
            int iAddAndGet = 1;
            do {
                if (this.connected) {
                    if (this.f2173s == Operators.cancelledSubscription()) {
                        this.queue.clear();
                        return;
                    }
                    Queue<T> queue = this.queue;
                    PublishMulticastInner<T>[] publishMulticastInnerArr = this.subscribers;
                    int length = publishMulticastInnerArr.length;
                    if (length != 0) {
                        int i2 = 0;
                        long jMin = Long.MAX_VALUE;
                        for (PublishMulticastInner<T> publishMulticastInner : publishMulticastInnerArr) {
                            long j = publishMulticastInner.requested;
                            if (j != Long.MIN_VALUE) {
                                jMin = Math.min(jMin, j);
                            }
                        }
                        long j2 = 0;
                        while (j2 != jMin) {
                            if (this.f2173s == Operators.cancelledSubscription()) {
                                queue.clear();
                                return;
                            }
                            boolean z = this.done;
                            try {
                                T tPoll = queue.poll();
                                boolean z2 = tPoll == null;
                                if (z) {
                                    Throwable th = this.error;
                                    if (th != null) {
                                        queue.clear();
                                        PublishMulticastInner[] andSet = SUBSCRIBERS.getAndSet(this, TERMINATED);
                                        int length2 = andSet.length;
                                        while (i2 < length2) {
                                            andSet[i2].actual.onError(th);
                                            i2++;
                                        }
                                        return;
                                    }
                                    if (z2) {
                                        PublishMulticastInner[] andSet2 = SUBSCRIBERS.getAndSet(this, TERMINATED);
                                        int length3 = andSet2.length;
                                        while (i2 < length3) {
                                            andSet2[i2].actual.onComplete();
                                            i2++;
                                        }
                                        return;
                                    }
                                }
                                if (z2) {
                                    break;
                                }
                                for (PublishMulticastInner<T> publishMulticastInner2 : publishMulticastInnerArr) {
                                    publishMulticastInner2.actual.onNext(tPoll);
                                }
                                j2++;
                                i++;
                                if (i == this.limit) {
                                    this.f2173s.request(i);
                                    i = 0;
                                }
                            } catch (Throwable th2) {
                                queue.clear();
                                this.error = Operators.onOperatorError(this.f2173s, th2, this.context);
                                PublishMulticastInner[] andSet3 = SUBSCRIBERS.getAndSet(this, TERMINATED);
                                int length4 = andSet3.length;
                                while (i2 < length4) {
                                    andSet3[i2].actual.onError(th2);
                                    i2++;
                                }
                                return;
                            }
                        }
                        if (j2 == jMin) {
                            if (this.f2173s == Operators.cancelledSubscription()) {
                                queue.clear();
                                return;
                            }
                            if (this.done) {
                                Throwable th3 = this.error;
                                if (th3 != null) {
                                    queue.clear();
                                    PublishMulticastInner[] andSet4 = SUBSCRIBERS.getAndSet(this, TERMINATED);
                                    int length5 = andSet4.length;
                                    while (i2 < length5) {
                                        andSet4[i2].actual.onError(th3);
                                        i2++;
                                    }
                                    return;
                                }
                                if (queue.isEmpty()) {
                                    PublishMulticastInner[] andSet5 = SUBSCRIBERS.getAndSet(this, TERMINATED);
                                    int length6 = andSet5.length;
                                    while (i2 < length6) {
                                        andSet5[i2].actual.onComplete();
                                        i2++;
                                    }
                                    return;
                                }
                            }
                        }
                        if (j2 != 0) {
                            while (i2 < length) {
                                publishMulticastInnerArr[i2].produced(j2);
                                i2++;
                            }
                        }
                    }
                }
                this.produced = i;
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        boolean add(PublishMulticastInner<T> publishMulticastInner) {
            PublishMulticastInner<T>[] publishMulticastInnerArr;
            PublishMulticastInner[] publishMulticastInnerArr2;
            do {
                publishMulticastInnerArr = this.subscribers;
                if (publishMulticastInnerArr == TERMINATED) {
                    return false;
                }
                int length = publishMulticastInnerArr.length;
                publishMulticastInnerArr2 = new PublishMulticastInner[length + 1];
                System.arraycopy(publishMulticastInnerArr, 0, publishMulticastInnerArr2, 0, length);
                publishMulticastInnerArr2[length] = publishMulticastInner;
            } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, publishMulticastInnerArr, publishMulticastInnerArr2));
            return true;
        }

        void remove(PublishMulticastInner<T> publishMulticastInner) {
            PublishMulticastInner<T>[] publishMulticastInnerArr;
            PublishMulticastInner[] publishMulticastInnerArr2;
            do {
                publishMulticastInnerArr = this.subscribers;
                if (publishMulticastInnerArr == TERMINATED || publishMulticastInnerArr == EMPTY) {
                    return;
                }
                int length = publishMulticastInnerArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        i = -1;
                        break;
                    } else if (publishMulticastInnerArr[i] == publishMulticastInner) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    publishMulticastInnerArr2 = EMPTY;
                } else {
                    PublishMulticastInner[] publishMulticastInnerArr3 = new PublishMulticastInner[length - 1];
                    System.arraycopy(publishMulticastInnerArr, 0, publishMulticastInnerArr3, 0, i);
                    System.arraycopy(publishMulticastInnerArr, i + 1, publishMulticastInnerArr3, i, (length - i) - 1);
                    publishMulticastInnerArr2 = publishMulticastInnerArr3;
                }
            } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, publishMulticastInnerArr, publishMulticastInnerArr2));
        }

        @Override // reactor.core.publisher.FluxPublishMulticast.PublishMulticasterParent
        public void terminate() {
            Operators.terminate(f2172S, this);
            if (WIP.getAndIncrement(this) == 0 && this.connected) {
                this.queue.clear();
            }
        }
    }

    static final class PublishMulticastInner<T> implements InnerProducer<T> {
        static final AtomicLongFieldUpdater<PublishMulticastInner> REQUESTED = AtomicLongFieldUpdater.newUpdater(PublishMulticastInner.class, "requested");
        final CoreSubscriber<? super T> actual;
        final FluxPublishMulticaster<T> parent;
        volatile long requested;

        PublishMulticastInner(FluxPublishMulticaster<T> fluxPublishMulticaster, CoreSubscriber<? super T> coreSubscriber) {
            this.parent = fluxPublishMulticaster;
            this.actual = coreSubscriber;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(Math.max(0L, this.requested));
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(Long.MIN_VALUE == this.requested);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCapCancellable(REQUESTED, this, j);
                this.parent.drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (REQUESTED.getAndSet(this, Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.remove(this);
                this.parent.drain();
            }
        }

        void produced(long j) {
            Operators.producedCancellable(REQUESTED, this, j);
        }
    }

    static final class CancelMulticaster<T> implements InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final PublishMulticasterParent parent;

        /* JADX INFO: renamed from: s */
        Subscription f2171s;

        @Override // java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return false;
        }

        @Override // java.util.Queue
        @Nullable
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

        CancelMulticaster(CoreSubscriber<? super T> coreSubscriber, PublishMulticasterParent publishMulticasterParent) {
            this.actual = coreSubscriber;
            this.parent = publishMulticasterParent;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2171s;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2171s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2171s.cancel();
            this.parent.terminate();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2171s, subscription)) {
                this.f2171s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
            this.parent.terminate();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
            this.parent.terminate();
        }
    }

    static final class CancelFuseableMulticaster<T> implements InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final PublishMulticasterParent parent;

        /* JADX INFO: renamed from: s */
        Fuseable.QueueSubscription<T> f2170s;

        CancelFuseableMulticaster(CoreSubscriber<? super T> coreSubscriber, PublishMulticasterParent publishMulticasterParent) {
            this.actual = coreSubscriber;
            this.parent = publishMulticasterParent;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2170s;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2170s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2170s.cancel();
            this.parent.terminate();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2170s, subscription)) {
                this.f2170s = Operators.m1969as(subscription);
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
            this.parent.terminate();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
            this.parent.terminate();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return this.f2170s.requestFusion(i);
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            return this.f2170s.poll();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2170s.isEmpty();
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2170s.size();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2170s.clear();
        }
    }
}
