package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelMergeReduce<T> extends Mono<T> implements Scannable, Fuseable {
    final BiFunction<T, T, T> reducer;
    final ParallelFlux<? extends T> source;

    ParallelMergeReduce(ParallelFlux<? extends T> parallelFlux, BiFunction<T, T, T> biFunction) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.reducer = biFunction;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        ParallelFlux<? extends T> parallelFlux = this.source;
        coreSubscriber.onSubscribe(new MergeReduceMain(parallelFlux, coreSubscriber, parallelFlux.parallelism(), this.reducer));
    }

    static final class MergeReduceMain<T> implements InnerProducer<T>, Fuseable, Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        volatile SlotPair<T> current;
        volatile Throwable error;
        final BiFunction<T, T, T> reducer;
        volatile int remaining;
        final ParallelFlux<? extends T> source;
        final MergeReduceInner<T>[] subscribers;
        static final AtomicReferenceFieldUpdater<MergeReduceMain, SlotPair> CURRENT = AtomicReferenceFieldUpdater.newUpdater(MergeReduceMain.class, SlotPair.class, "current");
        static final AtomicIntegerFieldUpdater<MergeReduceMain> REMAINING = AtomicIntegerFieldUpdater.newUpdater(MergeReduceMain.class, "remaining");
        static final AtomicReferenceFieldUpdater<MergeReduceMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(MergeReduceMain.class, Throwable.class, "error");

        @Override // java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.Queue
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

        MergeReduceMain(ParallelFlux<? extends T> parallelFlux, CoreSubscriber<? super T> coreSubscriber, int i, BiFunction<T, T, T> biFunction) {
            this.actual = coreSubscriber;
            this.source = parallelFlux;
            MergeReduceInner<T>[] mergeReduceInnerArr = new MergeReduceInner[i];
            for (int i2 = 0; i2 < i; i2++) {
                mergeReduceInnerArr[i2] = new MergeReduceInner<>(this, biFunction);
            }
            this.subscribers = mergeReduceInnerArr;
            this.reducer = biFunction;
            REMAINING.lazySet(this, Integer.MIN_VALUE | i);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.remaining == 0);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.remaining == Integer.MIN_VALUE);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Nullable
        SlotPair<T> addValue(T t) {
            SlotPair<T> slotPair;
            int iTryAcquireSlot;
            while (true) {
                slotPair = this.current;
                if (slotPair == null) {
                    slotPair = new SlotPair<>();
                    if (!C0162xc40028dd.m5m(CURRENT, this, null, slotPair)) {
                        continue;
                    }
                }
                iTryAcquireSlot = slotPair.tryAcquireSlot();
                if (iTryAcquireSlot >= 0) {
                    break;
                }
                C0162xc40028dd.m5m(CURRENT, this, slotPair, null);
            }
            if (iTryAcquireSlot == 0) {
                slotPair.first = t;
            } else {
                slotPair.second = t;
            }
            if (!slotPair.releaseSlot()) {
                return null;
            }
            C0162xc40028dd.m5m(CURRENT, this, slotPair, null);
            return slotPair;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if ((REMAINING.getAndSet(this, Integer.MIN_VALUE) & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
                for (MergeReduceInner<T> mergeReduceInner : this.subscribers) {
                    mergeReduceInner.cancel();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            int i = this.remaining;
            if ((i & Integer.MIN_VALUE) == Integer.MIN_VALUE && REMAINING.compareAndSet(this, i, Integer.MAX_VALUE & i)) {
                this.source.subscribe(this.subscribers);
            }
        }

        void innerError(Throwable th) {
            if (C0162xc40028dd.m5m(ERROR, this, null, th)) {
                cancel();
                this.actual.onError(th);
            } else if (this.error != th) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        void innerComplete(@Nullable T t) {
            if (t != null) {
                while (true) {
                    SlotPair<T> slotPairAddValue = addValue(t);
                    if (slotPairAddValue == null) {
                        break;
                    }
                    try {
                        t = (T) Objects.requireNonNull(this.reducer.apply(slotPairAddValue.first, slotPairAddValue.second), "The reducer returned a null value");
                    } catch (Throwable th) {
                        innerError(Operators.onOperatorError(this, th, this.actual.currentContext()));
                        return;
                    }
                }
            }
            if (decrementAndGet(this) == 0) {
                SlotPair<T> slotPair = this.current;
                CURRENT.lazySet(this, null);
                if (slotPair != null) {
                    this.actual.onNext(slotPair.first);
                    this.actual.onComplete();
                } else {
                    this.actual.onComplete();
                }
            }
        }

        static <T> int decrementAndGet(MergeReduceMain<T> mergeReduceMain) {
            int i;
            int i2;
            do {
                i = mergeReduceMain.remaining;
                if (i == Integer.MIN_VALUE) {
                    return Integer.MIN_VALUE;
                }
                i2 = i - 1;
            } while (!REMAINING.compareAndSet(mergeReduceMain, i, i2));
            return i2;
        }
    }

    static final class MergeReduceInner<T> implements InnerConsumer<T> {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<MergeReduceInner, Subscription> f2284S = AtomicReferenceFieldUpdater.newUpdater(MergeReduceInner.class, Subscription.class, "s");
        boolean done;
        final MergeReduceMain<T> parent;
        final BiFunction<T, T, T> reducer;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2285s;
        T value;

        MergeReduceInner(MergeReduceMain<T> mergeReduceMain, BiFunction<T, T, T> biFunction) {
            this.parent = mergeReduceMain;
            this.reducer = biFunction;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.actual.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2285s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.f2285s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.value == null ? 0 : 1);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2284S, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, currentContext());
                return;
            }
            T t2 = this.value;
            if (t2 == null) {
                this.value = t;
                return;
            }
            try {
                this.value = (T) Objects.requireNonNull(this.reducer.apply(t2, t), "The reducer returned a null value");
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2285s, th, t, currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.parent.actual.currentContext());
            } else {
                this.done = true;
                this.parent.innerError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.innerComplete(this.value);
        }

        void cancel() {
            Operators.terminate(f2284S, this);
        }
    }

    static final class SlotPair<T> {
        static final AtomicIntegerFieldUpdater<SlotPair> ACQ = AtomicIntegerFieldUpdater.newUpdater(SlotPair.class, "acquireIndex");
        static final AtomicIntegerFieldUpdater<SlotPair> REL = AtomicIntegerFieldUpdater.newUpdater(SlotPair.class, "releaseIndex");
        volatile int acquireIndex;
        T first;
        volatile int releaseIndex;
        T second;

        SlotPair() {
        }

        int tryAcquireSlot() {
            int i;
            do {
                i = this.acquireIndex;
                if (i >= 2) {
                    return -1;
                }
            } while (!ACQ.compareAndSet(this, i, i + 1));
            return i;
        }

        boolean releaseSlot() {
            return REL.incrementAndGet(this) == 2;
        }
    }
}
