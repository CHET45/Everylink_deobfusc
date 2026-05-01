package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelMergeSort<T> extends Flux<T> implements Scannable {
    final Comparator<? super T> comparator;
    final ParallelFlux<List<T>> source;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    ParallelMergeSort(ParallelFlux<List<T>> parallelFlux, Comparator<? super T> comparator) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.comparator = comparator;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        MergeSortMain mergeSortMain = new MergeSortMain(coreSubscriber, this.source.parallelism(), this.comparator);
        coreSubscriber.onSubscribe(mergeSortMain);
        this.source.subscribe(mergeSortMain.subscribers);
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    static final class MergeSortMain<T> implements InnerProducer<T> {
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        final Comparator<? super T> comparator;
        volatile Throwable error;
        final int[] indexes;
        final List<T>[] lists;
        volatile int remaining;
        volatile long requested;
        final MergeSortInner<T>[] subscribers;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<MergeSortMain> WIP = AtomicIntegerFieldUpdater.newUpdater(MergeSortMain.class, "wip");
        static final AtomicLongFieldUpdater<MergeSortMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(MergeSortMain.class, "requested");
        static final AtomicIntegerFieldUpdater<MergeSortMain> REMAINING = AtomicIntegerFieldUpdater.newUpdater(MergeSortMain.class, "remaining");
        static final AtomicReferenceFieldUpdater<MergeSortMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(MergeSortMain.class, Throwable.class, "error");

        MergeSortMain(CoreSubscriber<? super T> coreSubscriber, int i, Comparator<? super T> comparator) {
            this.comparator = comparator;
            this.actual = coreSubscriber;
            MergeSortInner<T>[] mergeSortInnerArr = new MergeSortInner[i];
            for (int i2 = 0; i2 < i; i2++) {
                mergeSortInnerArr[i2] = new MergeSortInner<>(this, i2);
            }
            this.subscribers = mergeSortInnerArr;
            this.lists = new List[i];
            this.indexes = new int[i];
            REMAINING.lazySet(this, i);
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(this.subscribers.length - this.remaining) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
                if (this.remaining == 0) {
                    drain();
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelAll();
            if (WIP.getAndIncrement(this) == 0) {
                Arrays.fill(this.lists, (Object) null);
            }
        }

        void cancelAll() {
            for (MergeSortInner<T> mergeSortInner : this.subscribers) {
                mergeSortInner.cancel();
            }
        }

        void innerNext(List<T> list, int i) {
            this.lists[i] = list;
            if (REMAINING.decrementAndGet(this) == 0) {
                drain();
            }
        }

        void innerError(Throwable th) {
            if (C0162xc40028dd.m5m(ERROR, this, null, th)) {
                cancelAll();
                drain();
            } else if (this.error != th) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x0073, code lost:
        
            if (r11 != r7) goto L49;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x0077, code lost:
        
            if (r16.cancelled == false) goto L38;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x0079, code lost:
        
            java.util.Arrays.fill(r2, (java.lang.Object) null);
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x007d, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x007e, code lost:
        
            r10 = r16.error;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x0081, code lost:
        
            if (r10 == null) goto L42;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x0083, code lost:
        
            cancelAll();
            java.util.Arrays.fill(r2, (java.lang.Object) null);
            r1.onError(r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x008c, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x008d, code lost:
        
            if (r14 >= r4) goto L65;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x0097, code lost:
        
            if (r3[r14] == r2[r14].size()) goto L46;
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x009a, code lost:
        
            r14 = r14 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x009d, code lost:
        
            java.util.Arrays.fill(r2, (java.lang.Object) null);
            r1.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x00a4, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x00a9, code lost:
        
            if (r11 == 0) goto L54;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x00b2, code lost:
        
            if (r7 == Long.MAX_VALUE) goto L54;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x00b4, code lost:
        
            reactor.core.publisher.ParallelMergeSort.MergeSortMain.REQUESTED.addAndGet(r16, -r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00ba, code lost:
        
            r5 = r16.wip;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x00bc, code lost:
        
            if (r5 != r6) goto L67;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x00be, code lost:
        
            r5 = reactor.core.publisher.ParallelMergeSort.MergeSortMain.WIP.addAndGet(r16, -r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:0x00c5, code lost:
        
            if (r5 != 0) goto L68;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x00c7, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void drain() {
            /*
                Method dump skipped, instruction units count: 203
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.ParallelMergeSort.MergeSortMain.drain():void");
        }
    }

    static final class MergeSortInner<T> implements InnerConsumer<List<T>> {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<MergeSortInner, Subscription> f2288S = AtomicReferenceFieldUpdater.newUpdater(MergeSortInner.class, Subscription.class, "s");
        final int index;
        final MergeSortMain<T> parent;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2289s;

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        MergeSortInner(MergeSortMain<T> mergeSortMain, int i) {
            this.parent = mergeSortMain;
            this.index = i;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.actual.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2289s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.f2289s;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
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
            if (Operators.setOnce(f2288S, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(List<T> list) {
            this.parent.innerNext(list, this.index);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.innerError(th);
        }

        void cancel() {
            Operators.terminate(f2288S, this);
        }
    }
}
