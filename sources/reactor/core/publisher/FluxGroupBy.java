package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import com.azure.core.implementation.logging.LoggingKeys;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxGroupBy<T, K, V> extends InternalFluxOperator<T, GroupedFlux<K, V>> implements Fuseable {
    final Supplier<? extends Queue<V>> groupQueueSupplier;
    final Function<? super T, ? extends K> keySelector;
    final Supplier<? extends Queue<GroupedFlux<K, V>>> mainQueueSupplier;
    final int prefetch;
    final Function<? super T, ? extends V> valueSelector;

    FluxGroupBy(Flux<? extends T> flux, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, Supplier<? extends Queue<GroupedFlux<K, V>>> supplier, Supplier<? extends Queue<V>> supplier2, int i) {
        super(flux);
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.keySelector = (Function) Objects.requireNonNull(function, "keySelector");
        this.valueSelector = (Function) Objects.requireNonNull(function2, "valueSelector");
        this.mainQueueSupplier = (Supplier) Objects.requireNonNull(supplier, "mainQueueSupplier");
        this.groupQueueSupplier = (Supplier) Objects.requireNonNull(supplier2, "groupQueueSupplier");
        this.prefetch = i;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super GroupedFlux<K, V>> coreSubscriber) {
        return new GroupByMain(coreSubscriber, this.mainQueueSupplier.get(), this.groupQueueSupplier, this.prefetch, this.keySelector, this.valueSelector);
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class GroupByMain<T, K, V> implements Fuseable.QueueSubscription<GroupedFlux<K, V>>, InnerOperator<T, GroupedFlux<K, V>> {
        final CoreSubscriber<? super GroupedFlux<K, V>> actual;
        volatile int cancelled;
        volatile boolean done;
        volatile boolean enableAsyncFusion;
        volatile Throwable error;
        volatile int groupCount;
        final Map<K, UnicastGroupedFlux<K, V>> groupMap = new ConcurrentHashMap();
        final Supplier<? extends Queue<V>> groupQueueSupplier;
        final Function<? super T, ? extends K> keySelector;
        final int prefetch;
        final Queue<GroupedFlux<K, V>> queue;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2134s;
        final Function<? super T, ? extends V> valueSelector;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<GroupByMain> WIP = AtomicIntegerFieldUpdater.newUpdater(GroupByMain.class, "wip");
        static final AtomicLongFieldUpdater<GroupByMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(GroupByMain.class, "requested");
        static final AtomicReferenceFieldUpdater<GroupByMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(GroupByMain.class, Throwable.class, "error");
        static final AtomicIntegerFieldUpdater<GroupByMain> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(GroupByMain.class, LoggingKeys.CANCELLED_ERROR_TYPE);
        static final AtomicIntegerFieldUpdater<GroupByMain> GROUP_COUNT = AtomicIntegerFieldUpdater.newUpdater(GroupByMain.class, "groupCount");

        GroupByMain(CoreSubscriber<? super GroupedFlux<K, V>> coreSubscriber, Queue<GroupedFlux<K, V>> queue, Supplier<? extends Queue<V>> supplier, int i, Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
            this.actual = coreSubscriber;
            this.queue = queue;
            this.groupQueueSupplier = supplier;
            this.prefetch = i;
            this.keySelector = function;
            this.valueSelector = function2;
            GROUP_COUNT.lazySet(this, 1);
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super GroupedFlux<K, V>> actual() {
            return this.actual;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2134s, subscription)) {
                this.f2134s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                Object objRequireNonNull = Objects.requireNonNull(this.keySelector.apply(t), "The keySelector returned a null value");
                Object objRequireNonNull2 = Objects.requireNonNull(this.valueSelector.apply(t), "The valueSelector returned a null value");
                UnicastGroupedFlux<K, V> unicastGroupedFlux = this.groupMap.get(objRequireNonNull);
                if (unicastGroupedFlux == 0) {
                    if (this.cancelled == 0) {
                        Queue<V> queue = this.groupQueueSupplier.get();
                        GROUP_COUNT.getAndIncrement(this);
                        UnicastGroupedFlux unicastGroupedFlux2 = new UnicastGroupedFlux(objRequireNonNull, queue, this, this.prefetch);
                        unicastGroupedFlux2.onNext(objRequireNonNull2);
                        this.groupMap.put((K) objRequireNonNull, (UnicastGroupedFlux<K, V>) unicastGroupedFlux2);
                        this.queue.offer(unicastGroupedFlux2);
                        drain();
                        return;
                    }
                    return;
                }
                unicastGroupedFlux.onNext(objRequireNonNull2);
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2134s, th, t, this.actual.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (Exceptions.addThrowable(ERROR, this, th)) {
                this.done = true;
                drain();
            } else {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            Iterator<UnicastGroupedFlux<K, V>> it = this.groupMap.values().iterator();
            while (it.hasNext()) {
                it.next().onComplete();
            }
            this.groupMap.clear();
            this.done = true;
            drain();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2134s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.queue.size());
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled == 1);
            }
            return attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return this.groupMap.values().stream();
        }

        void signalAsyncError() {
            Throwable thTerminate = Exceptions.terminate(ERROR, this);
            if (thTerminate == null) {
                thTerminate = new IllegalStateException("FluxGroupBy.signalAsyncError called without error set");
            }
            this.groupCount = 0;
            Iterator<UnicastGroupedFlux<K, V>> it = this.groupMap.values().iterator();
            while (it.hasNext()) {
                it.next().onError(thTerminate);
            }
            this.actual.onError(thTerminate);
            this.groupMap.clear();
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
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                if (GROUP_COUNT.decrementAndGet(this) == 0) {
                    this.f2134s.cancel();
                    return;
                }
                if (this.enableAsyncFusion || WIP.getAndIncrement(this) != 0) {
                    return;
                }
                while (true) {
                    GroupedFlux<K, V> groupedFluxPoll = this.queue.poll();
                    if (groupedFluxPoll == null) {
                        break;
                    } else {
                        ((UnicastGroupedFlux) groupedFluxPoll).cancel();
                    }
                }
                if (WIP.decrementAndGet(this) == 0) {
                    return;
                }
                drainLoop();
            }
        }

        void groupTerminated(K k) {
            if (this.groupCount == 0) {
                return;
            }
            this.groupMap.remove(k);
            int iDecrementAndGet = GROUP_COUNT.decrementAndGet(this);
            if (iDecrementAndGet == 0) {
                this.f2134s.cancel();
            } else if (iDecrementAndGet == 1) {
                this.f2134s.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            if (this.enableAsyncFusion) {
                drainFused();
            } else {
                drainLoop();
            }
        }

        void drainFused() {
            CoreSubscriber<? super GroupedFlux<K, V>> coreSubscriber = this.actual;
            Queue<GroupedFlux<K, V>> queue = this.queue;
            int iAddAndGet = 1;
            while (this.cancelled == 0) {
                boolean z = this.done;
                coreSubscriber.onNext(null);
                if (z) {
                    if (this.error != null) {
                        signalAsyncError();
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
            queue.clear();
        }

        void drainLoop() {
            CoreSubscriber<? super GroupedFlux<K, V>> coreSubscriber = this.actual;
            Queue<GroupedFlux<K, V>> queue = this.queue;
            int iAddAndGet = 1;
            do {
                long j = this.requested;
                long j2 = 0;
                while (j2 != j) {
                    boolean z = this.done;
                    GroupedFlux<K, V> groupedFluxPoll = queue.poll();
                    boolean z2 = groupedFluxPoll == null;
                    if (checkTerminated(z, z2, coreSubscriber, queue)) {
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    coreSubscriber.onNext(groupedFluxPoll);
                    j2++;
                }
                if (j2 == j && checkTerminated(this.done, queue.isEmpty(), coreSubscriber, queue)) {
                    return;
                }
                if (j2 != 0) {
                    this.f2134s.request(j2);
                    if (j != Long.MAX_VALUE) {
                        REQUESTED.addAndGet(this, -j2);
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, Queue<GroupedFlux<K, V>> queue) {
            if (!z) {
                return false;
            }
            Throwable th = this.error;
            if (th != null && th != Exceptions.TERMINATED) {
                queue.clear();
                signalAsyncError();
                return true;
            }
            if (!z2) {
                return false;
            }
            subscriber.onComplete();
            return true;
        }

        @Override // java.util.Queue
        @Nullable
        public GroupedFlux<K, V> poll() {
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
            this.queue.clear();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.enableAsyncFusion = true;
            return 2;
        }
    }

    static final class UnicastGroupedFlux<K, V> extends GroupedFlux<K, V> implements Fuseable, Fuseable.QueueSubscription<V>, InnerProducer<V> {
        volatile CoreSubscriber<? super V> actual;
        volatile boolean cancelled;
        final Context context;
        volatile boolean done;
        Throwable error;
        boolean isFirstRequest = true;
        final K key;
        final int limit;
        volatile int once;
        volatile boolean outputFused;
        volatile GroupByMain<?, K, V> parent;
        int produced;
        final Queue<V> queue;
        volatile long requested;
        volatile int wip;
        static final AtomicReferenceFieldUpdater<UnicastGroupedFlux, GroupByMain> PARENT = AtomicReferenceFieldUpdater.newUpdater(UnicastGroupedFlux.class, GroupByMain.class, "parent");
        static final AtomicReferenceFieldUpdater<UnicastGroupedFlux, CoreSubscriber> ACTUAL = AtomicReferenceFieldUpdater.newUpdater(UnicastGroupedFlux.class, CoreSubscriber.class, "actual");
        static final AtomicIntegerFieldUpdater<UnicastGroupedFlux> ONCE = AtomicIntegerFieldUpdater.newUpdater(UnicastGroupedFlux.class, "once");
        static final AtomicIntegerFieldUpdater<UnicastGroupedFlux> WIP = AtomicIntegerFieldUpdater.newUpdater(UnicastGroupedFlux.class, "wip");
        static final AtomicLongFieldUpdater<UnicastGroupedFlux> REQUESTED = AtomicLongFieldUpdater.newUpdater(UnicastGroupedFlux.class, "requested");

        @Override // reactor.core.publisher.GroupedFlux
        public K key() {
            return this.key;
        }

        UnicastGroupedFlux(K k, Queue<V> queue, GroupByMain<?, K, V> groupByMain, int i) {
            this.key = k;
            this.queue = queue;
            this.context = groupByMain.currentContext();
            this.parent = groupByMain;
            this.limit = Operators.unboundedOrLimit(i);
        }

        void doTerminate() {
            GroupByMain<?, K, V> groupByMain = this.parent;
            if (groupByMain == null || !C0162xc40028dd.m5m(PARENT, this, groupByMain, null)) {
                return;
            }
            groupByMain.groupTerminated(this.key);
        }

        void drainRegular(Subscriber<? super V> subscriber) {
            Queue<V> queue = this.queue;
            int iAddAndGet = 1;
            do {
                long j = this.requested;
                long j2 = 0;
                while (j != j2) {
                    boolean z = this.done;
                    V vPoll = queue.poll();
                    boolean z2 = vPoll == null;
                    if (checkTerminated(z, z2, subscriber, queue)) {
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext(vPoll);
                    j2++;
                }
                if (j == j2 && checkTerminated(this.done, queue.isEmpty(), subscriber, queue)) {
                    return;
                }
                if (j2 != 0) {
                    GroupByMain<?, K, V> groupByMain = this.parent;
                    if (groupByMain != null) {
                        if (this.isFirstRequest) {
                            this.isFirstRequest = false;
                            long j3 = j2 - 1;
                            if (j3 > 0) {
                                groupByMain.f2134s.request(j3);
                            }
                        } else {
                            groupByMain.f2134s.request(j2);
                        }
                    }
                    if (j != Long.MAX_VALUE) {
                        REQUESTED.addAndGet(this, -j2);
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        void drainFused(Subscriber<? super V> subscriber) {
            Queue<V> queue = this.queue;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                boolean z = this.done;
                subscriber.onNext(null);
                if (z) {
                    this.actual = null;
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
            queue.clear();
            this.actual = null;
        }

        void drain() {
            CoreSubscriber<? super V> coreSubscriber = this.actual;
            if (coreSubscriber == null || WIP.getAndIncrement(this) != 0) {
                return;
            }
            if (this.outputFused) {
                drainFused(coreSubscriber);
            } else {
                drainRegular(coreSubscriber);
            }
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, Queue<?> queue) {
            if (this.cancelled) {
                queue.clear();
                this.actual = null;
                return true;
            }
            if (!z || !z2) {
                return false;
            }
            Throwable th = this.error;
            this.actual = null;
            if (th != null) {
                subscriber.onError(th);
            } else {
                subscriber.onComplete();
            }
            return true;
        }

        public void onNext(V v) {
            CoreSubscriber<? super V> coreSubscriber = this.actual;
            if (!this.queue.offer(v)) {
                onError(Operators.onOperatorError(this, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), v, this.actual.currentContext()));
            } else if (!this.outputFused) {
                drain();
            } else if (coreSubscriber != null) {
                coreSubscriber.onNext(null);
            }
        }

        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            doTerminate();
            drain();
        }

        public void onComplete() {
            this.done = true;
            doTerminate();
            drain();
        }

        @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
        public void subscribe(CoreSubscriber<? super V> coreSubscriber) {
            if (this.once == 0 && ONCE.compareAndSet(this, 0, 1)) {
                coreSubscriber.onSubscribe(this);
                ACTUAL.lazySet(this, coreSubscriber);
                drain();
                return;
            }
            Operators.error(coreSubscriber, new IllegalStateException("GroupedFlux allows only one Subscriber"));
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
            doTerminate();
            if (this.outputFused || WIP.getAndIncrement(this) != 0) {
                return;
            }
            this.queue.clear();
        }

        @Override // java.util.Queue
        @Nullable
        public V poll() {
            V vPoll = this.queue.poll();
            if (vPoll != null) {
                this.produced++;
            } else {
                tryReplenish();
            }
            return vPoll;
        }

        void tryReplenish() {
            int i = this.produced;
            if (i != 0) {
                this.produced = 0;
                GroupByMain<?, K, V> groupByMain = this.parent;
                if (groupByMain != null) {
                    if (this.isFirstRequest) {
                        this.isFirstRequest = false;
                        int i2 = i - 1;
                        if (i2 > 0) {
                            groupByMain.f2134s.request(i2);
                            return;
                        }
                        return;
                    }
                    groupByMain.f2134s.request(i);
                }
            }
        }

        @Override // java.util.Collection
        public int size() {
            return this.queue.size();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            if (!this.queue.isEmpty()) {
                return false;
            }
            tryReplenish();
            return true;
        }

        @Override // java.util.Collection
        public void clear() {
            this.queue.clear();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr != Scannable.Attr.BUFFERED) {
                return attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            Queue<V> queue = this.queue;
            return Integer.valueOf(queue != null ? queue.size() : 0);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super V> actual() {
            return this.actual;
        }
    }
}
