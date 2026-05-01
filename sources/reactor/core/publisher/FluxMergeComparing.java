package reactor.core.publisher;

import com.azure.core.implementation.logging.LoggingKeys;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxMergeComparing<T> extends Flux<T> implements SourceProducer<T> {
    final boolean delayError;
    final int prefetch;
    final Publisher<? extends T>[] sources;
    final Comparator<? super T> valueComparator;
    final boolean waitForAllSources;

    @SafeVarargs
    FluxMergeComparing(int i, Comparator<? super T> comparator, boolean z, boolean z2, Publisher<? extends T>... publisherArr) {
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.sources = (Publisher[]) Objects.requireNonNull(publisherArr, "sources must be non-null");
        for (int i2 = 0; i2 < publisherArr.length; i2++) {
            if (publisherArr[i2] == null) {
                throw new NullPointerException("sources[" + i2 + "] is null");
            }
        }
        Operators.toFluxOrMono(this.sources);
        this.prefetch = i;
        this.valueComparator = comparator;
        this.delayError = z;
        this.waitForAllSources = z2;
    }

    FluxMergeComparing<T> mergeAdditionalSource(Publisher<? extends T> publisher, Comparator<? super T> comparator) {
        Publisher<? extends T>[] publisherArr = this.sources;
        int length = publisherArr.length;
        Publisher[] publisherArr2 = new Publisher[length + 1];
        System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
        publisherArr2[length] = publisher;
        if (!this.valueComparator.equals(comparator)) {
            return new FluxMergeComparing<>(this.prefetch, this.valueComparator.thenComparing((Comparator<? super Object>) comparator), this.delayError, this.waitForAllSources, publisherArr2);
        }
        return new FluxMergeComparing<>(this.prefetch, this.valueComparator, this.delayError, this.waitForAllSources, publisherArr2);
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr != Scannable.Attr.PARENT) {
            return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.prefetch) : attr == Scannable.Attr.DELAY_ERROR ? Boolean.valueOf(this.delayError) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
        Publisher<? extends T>[] publisherArr = this.sources;
        if (publisherArr.length > 0) {
            return publisherArr[0];
        }
        return null;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        MergeOrderedMainProducer mergeOrderedMainProducer = new MergeOrderedMainProducer(coreSubscriber, this.valueComparator, this.prefetch, this.sources.length, this.delayError, this.waitForAllSources);
        coreSubscriber.onSubscribe(mergeOrderedMainProducer);
        mergeOrderedMainProducer.subscribe(this.sources);
    }

    static final class MergeOrderedMainProducer<T> implements InnerProducer<T> {
        final CoreSubscriber<? super T> actual;
        volatile int cancelled;
        final Comparator<? super T> comparator;
        final boolean delayError;
        boolean done;
        volatile long emitted;
        volatile Throwable error;
        volatile long requested;
        final MergeOrderedInnerSubscriber<T>[] subscribers;
        final Object[] values;
        final boolean waitForAllSources;
        volatile int wip;
        static final Object DONE = new Object();
        static final AtomicReferenceFieldUpdater<MergeOrderedMainProducer, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(MergeOrderedMainProducer.class, Throwable.class, "error");
        static final AtomicIntegerFieldUpdater<MergeOrderedMainProducer> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(MergeOrderedMainProducer.class, LoggingKeys.CANCELLED_ERROR_TYPE);
        static final AtomicLongFieldUpdater<MergeOrderedMainProducer> REQUESTED = AtomicLongFieldUpdater.newUpdater(MergeOrderedMainProducer.class, "requested");
        static final AtomicLongFieldUpdater<MergeOrderedMainProducer> EMITTED = AtomicLongFieldUpdater.newUpdater(MergeOrderedMainProducer.class, "emitted");
        static final AtomicIntegerFieldUpdater<MergeOrderedMainProducer> WIP = AtomicIntegerFieldUpdater.newUpdater(MergeOrderedMainProducer.class, "wip");

        MergeOrderedMainProducer(CoreSubscriber<? super T> coreSubscriber, Comparator<? super T> comparator, int i, int i2, boolean z, boolean z2) {
            this.actual = coreSubscriber;
            this.comparator = comparator;
            this.delayError = z;
            this.waitForAllSources = z2;
            this.subscribers = new MergeOrderedInnerSubscriber[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                this.subscribers[i3] = new MergeOrderedInnerSubscriber<>(this, i);
            }
            this.values = new Object[i2];
        }

        void subscribe(Publisher<? extends T>[] publisherArr) {
            if (publisherArr.length != this.subscribers.length) {
                throw new IllegalArgumentException("must subscribe with " + this.subscribers.length + " sources");
            }
            for (int i = 0; i < publisherArr.length; i++) {
                Objects.requireNonNull(publisherArr[i], "subscribed with a null source: sources[" + i + "]");
                publisherArr[i].subscribe(this.subscribers[i]);
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            Operators.addCap(REQUESTED, this, j);
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                for (MergeOrderedInnerSubscriber<T> mergeOrderedInnerSubscriber : this.subscribers) {
                    mergeOrderedInnerSubscriber.cancel();
                }
                if (WIP.getAndIncrement(this) == 0) {
                    discardData();
                }
            }
        }

        void onInnerError(MergeOrderedInnerSubscriber<T> mergeOrderedInnerSubscriber, Throwable th) {
            Throwable thOnNextInnerError = Operators.onNextInnerError(th, actual().currentContext(), this);
            if (thOnNextInnerError != null) {
                if (Exceptions.addThrowable(ERROR, this, thOnNextInnerError)) {
                    if (!this.delayError) {
                        this.done = true;
                    }
                    mergeOrderedInnerSubscriber.done = true;
                    drain();
                    return;
                }
                mergeOrderedInnerSubscriber.done = true;
                Operators.onErrorDropped(thOnNextInnerError, this.actual.currentContext());
                return;
            }
            mergeOrderedInnerSubscriber.done = true;
            drain();
        }

        void drain() {
            Comparator<? super T> comparator;
            int i;
            Comparator<? super T> comparator2;
            Comparator<? super T> comparator3;
            int i2;
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            Comparator<? super T> comparator4 = this.comparator;
            MergeOrderedInnerSubscriber<T>[] mergeOrderedInnerSubscriberArr = this.subscribers;
            int length = mergeOrderedInnerSubscriberArr.length;
            Object[] objArr = this.values;
            long j = this.emitted;
            int iAddAndGet = 1;
            while (true) {
                long j2 = this.requested;
                while (true) {
                    boolean z = this.done;
                    if (this.cancelled != 0) {
                        Arrays.fill(objArr, (Object) null);
                        for (MergeOrderedInnerSubscriber<T> mergeOrderedInnerSubscriber : mergeOrderedInnerSubscriberArr) {
                            mergeOrderedInnerSubscriber.queue.clear();
                        }
                        return;
                    }
                    int i3 = 0;
                    int i4 = 0;
                    int i5 = 0;
                    while (i3 < length) {
                        Object obj = objArr[i3];
                        Comparator<? super T> comparator5 = comparator4;
                        Object obj2 = DONE;
                        if (obj == obj2) {
                            i5++;
                            i4++;
                            i2 = iAddAndGet;
                        } else {
                            if (obj == null) {
                                boolean z2 = mergeOrderedInnerSubscriberArr[i3].done;
                                i2 = iAddAndGet;
                                T tPoll = mergeOrderedInnerSubscriberArr[i3].queue.poll();
                                if (tPoll != null) {
                                    objArr[i3] = tPoll;
                                } else if (z2) {
                                    objArr[i3] = obj2;
                                    i5++;
                                }
                            } else {
                                i2 = iAddAndGet;
                            }
                            i4++;
                        }
                        i3++;
                        comparator4 = comparator5;
                        iAddAndGet = i2;
                    }
                    comparator = comparator4;
                    i = iAddAndGet;
                    if (checkTerminated(z || i5 == length, coreSubscriber)) {
                        return;
                    }
                    boolean z3 = this.waitForAllSources;
                    if ((z3 && i4 != length) || ((!z3 && (i4 == 0 || i4 == i5)) || j >= j2)) {
                        break;
                    }
                    int length2 = objArr.length;
                    int i6 = -1;
                    Object obj3 = null;
                    int i7 = 0;
                    int i8 = 0;
                    while (i7 < length2) {
                        Object obj4 = objArr[i7];
                        if (obj4 == DONE || obj4 == null) {
                            comparator3 = comparator;
                        } else {
                            if (obj3 != null) {
                                comparator3 = comparator;
                                try {
                                    if (comparator3.compare(obj3, obj4) > 0) {
                                    }
                                } catch (Throwable th) {
                                    Exceptions.addThrowable(ERROR, this, th);
                                    cancel();
                                    coreSubscriber.onError(Exceptions.terminate(ERROR, this));
                                    return;
                                }
                            } else {
                                comparator3 = comparator;
                            }
                            obj3 = obj4;
                            i6 = i8;
                        }
                        i8++;
                        i7++;
                        comparator = comparator3;
                    }
                    Comparator<? super T> comparator6 = comparator;
                    if (i6 >= 0) {
                        objArr[i6] = null;
                        coreSubscriber.onNext(obj3);
                        comparator2 = comparator6;
                        j++;
                        mergeOrderedInnerSubscriberArr[i6].request(1L);
                    } else {
                        comparator2 = comparator6;
                    }
                    iAddAndGet = i;
                    comparator4 = comparator2;
                }
                this.emitted = j;
                iAddAndGet = WIP.addAndGet(this, -i);
                if (iAddAndGet == 0) {
                    return;
                } else {
                    comparator4 = comparator;
                }
            }
        }

        boolean checkTerminated(boolean z, Subscriber<?> subscriber) {
            if (this.cancelled != 0) {
                discardData();
                return true;
            }
            if (!z) {
                return false;
            }
            if (this.delayError) {
                Throwable th = this.error;
                if (th != null && th != Exceptions.TERMINATED) {
                    subscriber.onError(Exceptions.terminate(ERROR, this));
                } else {
                    subscriber.onComplete();
                }
            } else {
                Throwable th2 = this.error;
                if (th2 != null && th2 != Exceptions.TERMINATED) {
                    Throwable thTerminate = Exceptions.terminate(ERROR, this);
                    cancel();
                    discardData();
                    subscriber.onError(thTerminate);
                } else {
                    subscriber.onComplete();
                }
            }
            return true;
        }

        private void discardData() {
            Context contextCurrentContext = actual().currentContext();
            for (Object obj : this.values) {
                if (obj != DONE) {
                    Operators.onDiscard(obj, contextCurrentContext);
                }
            }
            Arrays.fill(this.values, (Object) null);
            for (MergeOrderedInnerSubscriber<T> mergeOrderedInnerSubscriber : this.subscribers) {
                Operators.onDiscardQueueWithClear(mergeOrderedInnerSubscriber.queue, contextCurrentContext, null);
            }
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled > 0);
            }
            return attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.DELAY_ERROR ? Boolean.valueOf(this.delayError) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested - this.emitted) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static final class MergeOrderedInnerSubscriber<T> implements InnerOperator<T, T> {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<MergeOrderedInnerSubscriber, Subscription> f2151S = AtomicReferenceFieldUpdater.newUpdater(MergeOrderedInnerSubscriber.class, Subscription.class, "s");
        int consumed;
        volatile boolean done;
        final int limit;
        final MergeOrderedMainProducer<T> parent;
        final int prefetch;
        final Queue<T> queue;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2152s;

        MergeOrderedInnerSubscriber(MergeOrderedMainProducer<T> mergeOrderedMainProducer, int i) {
            this.parent = mergeOrderedMainProducer;
            this.prefetch = i;
            this.limit = i - (i >> 2);
            this.queue = (Queue) Queues.get(i).get();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2151S, this, subscription)) {
                subscription.request(this.prefetch);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.parent.done || this.done) {
                Operators.onNextDropped(t, actual().currentContext());
            } else {
                this.queue.offer(t);
                this.parent.drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.onInnerError(this, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            int i = this.consumed + 1;
            if (i == this.limit) {
                this.consumed = 0;
                Subscription subscription = this.f2152s;
                if (subscription != this) {
                    subscription.request(i);
                    return;
                }
                return;
            }
            this.consumed = i;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Subscription andSet = f2151S.getAndSet(this, this);
            if (andSet == null || andSet == this) {
                return;
            }
            andSet.cancel();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.parent.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.ACTUAL ? this.parent : attr == Scannable.Attr.PARENT ? this.f2152s : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.prefetch) : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(this.queue.size()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
