package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import com.aivox.app.util.MultiAiClient;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelMergeSequential<T> extends Flux<T> implements Scannable {
    final int prefetch;
    final Supplier<Queue<T>> queueSupplier;
    final ParallelFlux<? extends T> source;

    ParallelMergeSequential(ParallelFlux<? extends T> parallelFlux, int i, Supplier<Queue<T>> supplier) {
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.prefetch = i;
        this.queueSupplier = supplier;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        MergeSequentialMain mergeSequentialMain = new MergeSequentialMain(coreSubscriber, this.source.parallelism(), this.prefetch, this.queueSupplier);
        coreSubscriber.onSubscribe(mergeSequentialMain);
        this.source.subscribe(mergeSequentialMain.subscribers);
    }

    static final class MergeSequentialMain<T> implements InnerProducer<T> {
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        volatile int done;
        volatile Throwable error;
        final Supplier<Queue<T>> queueSupplier;
        volatile long requested;
        final MergeSequentialInner<T>[] subscribers;
        volatile int wip;
        static final AtomicReferenceFieldUpdater<MergeSequentialMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(MergeSequentialMain.class, Throwable.class, "error");
        static final AtomicIntegerFieldUpdater<MergeSequentialMain> WIP = AtomicIntegerFieldUpdater.newUpdater(MergeSequentialMain.class, "wip");
        static final AtomicLongFieldUpdater<MergeSequentialMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(MergeSequentialMain.class, "requested");
        static final AtomicIntegerFieldUpdater<MergeSequentialMain> DONE = AtomicIntegerFieldUpdater.newUpdater(MergeSequentialMain.class, MultiAiClient.EVENT_DONE);

        MergeSequentialMain(CoreSubscriber<? super T> coreSubscriber, int i, int i2, Supplier<Queue<T>> supplier) {
            this.actual = coreSubscriber;
            this.queueSupplier = supplier;
            MergeSequentialInner<T>[] mergeSequentialInnerArr = new MergeSequentialInner[i];
            for (int i3 = 0; i3 < i; i3++) {
                mergeSequentialInnerArr[i3] = new MergeSequentialInner<>(this, i2);
            }
            this.subscribers = mergeSequentialInnerArr;
            DONE.lazySet(this, i);
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done == 0);
            }
            return attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers);
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
            cancelAll();
            if (WIP.getAndIncrement(this) == 0) {
                cleanup();
            }
        }

        void cancelAll() {
            for (MergeSequentialInner<T> mergeSequentialInner : this.subscribers) {
                mergeSequentialInner.cancel();
            }
        }

        void cleanup() {
            for (MergeSequentialInner<T> mergeSequentialInner : this.subscribers) {
                mergeSequentialInner.queue = null;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0056  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void onNext(reactor.core.publisher.ParallelMergeSequential.MergeSequentialInner<T> r7, T r8) {
            /*
                r6 = this;
                int r0 = r6.wip
                java.lang.String r1 = "Queue is full: Reactive Streams source doesn't respect backpressure"
                if (r0 != 0) goto L56
                java.util.concurrent.atomic.AtomicIntegerFieldUpdater<reactor.core.publisher.ParallelMergeSequential$MergeSequentialMain> r0 = reactor.core.publisher.ParallelMergeSequential.MergeSequentialMain.WIP
                r2 = 0
                r3 = 1
                boolean r2 = r0.compareAndSet(r6, r2, r3)
                if (r2 == 0) goto L56
                long r2 = r6.requested
                r4 = 0
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 == 0) goto L31
                reactor.core.CoreSubscriber<? super T> r1 = r6.actual
                r1.onNext(r8)
                long r1 = r6.requested
                r3 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r8 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r8 == 0) goto L2d
                java.util.concurrent.atomic.AtomicLongFieldUpdater<reactor.core.publisher.ParallelMergeSequential$MergeSequentialMain> r8 = reactor.core.publisher.ParallelMergeSequential.MergeSequentialMain.REQUESTED
                r8.decrementAndGet(r6)
            L2d:
                r7.requestOne()
                goto L4f
            L31:
                java.util.function.Supplier<java.util.Queue<T>> r2 = r6.queueSupplier
                java.util.Queue r7 = r7.getQueue(r2)
                boolean r7 = r7.offer(r8)
                if (r7 != 0) goto L4f
                java.lang.IllegalStateException r7 = reactor.core.Exceptions.failWithOverflow(r1)
                reactor.core.CoreSubscriber<? super T> r0 = r6.actual
                reactor.util.context.Context r0 = r0.currentContext()
                java.lang.Throwable r7 = reactor.core.publisher.Operators.onOperatorError(r6, r7, r8, r0)
                r6.onError(r7)
                return
            L4f:
                int r7 = r0.decrementAndGet(r6)
                if (r7 != 0) goto L7d
                return
            L56:
                java.util.function.Supplier<java.util.Queue<T>> r0 = r6.queueSupplier
                java.util.Queue r7 = r7.getQueue(r0)
                boolean r7 = r7.offer(r8)
                if (r7 != 0) goto L74
                java.lang.IllegalStateException r7 = reactor.core.Exceptions.failWithOverflow(r1)
                reactor.core.CoreSubscriber<? super T> r0 = r6.actual
                reactor.util.context.Context r0 = r0.currentContext()
                java.lang.Throwable r7 = reactor.core.publisher.Operators.onOperatorError(r6, r7, r8, r0)
                r6.onError(r7)
                return
            L74:
                java.util.concurrent.atomic.AtomicIntegerFieldUpdater<reactor.core.publisher.ParallelMergeSequential$MergeSequentialMain> r7 = reactor.core.publisher.ParallelMergeSequential.MergeSequentialMain.WIP
                int r7 = r7.getAndIncrement(r6)
                if (r7 == 0) goto L7d
                return
            L7d:
                r6.drainLoop()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.ParallelMergeSequential.MergeSequentialMain.onNext(reactor.core.publisher.ParallelMergeSequential$MergeSequentialInner, java.lang.Object):void");
        }

        void onError(Throwable th) {
            if (C0162xc40028dd.m5m(ERROR, this, null, th)) {
                cancelAll();
                drain();
            } else if (this.error != th) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        void onComplete() {
            if (DONE.decrementAndGet(this) < 0) {
                return;
            }
            drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            drainLoop();
        }

        /* JADX WARN: Code restructure failed: missing block: B:29:0x004e, code lost:
        
            if (r12 == false) goto L84;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x0050, code lost:
        
            if (r15 == false) goto L85;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0052, code lost:
        
            r3.onComplete();
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0055, code lost:
        
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0056, code lost:
        
            if (r15 == false) goto L86;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void drainLoop() {
            /*
                r18 = this;
                r0 = r18
                reactor.core.publisher.ParallelMergeSequential$MergeSequentialInner<T>[] r1 = r0.subscribers
                int r2 = r1.length
                reactor.core.CoreSubscriber<? super T> r3 = r0.actual
                r5 = 1
            L8:
                long r6 = r0.requested
                r8 = 0
                r10 = r8
            Ld:
                int r12 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r12 == 0) goto L58
                boolean r12 = r0.cancelled
                if (r12 == 0) goto L19
                r18.cleanup()
                return
            L19:
                java.lang.Throwable r12 = r0.error
                if (r12 == 0) goto L24
                r18.cleanup()
                r3.onError(r12)
                return
            L24:
                int r12 = r0.done
                if (r12 != 0) goto L2a
                r12 = 1
                goto L2b
            L2a:
                r12 = 0
            L2b:
                r14 = 0
                r15 = 1
            L2d:
                if (r14 >= r2) goto L4e
                r4 = r1[r14]
                java.util.Queue<T> r13 = r4.queue
                if (r13 == 0) goto L4b
                java.lang.Object r13 = r13.poll()
                if (r13 == 0) goto L4b
                r3.onNext(r13)
                r4.requestOne()
                r16 = 1
                long r10 = r10 + r16
                int r4 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r4 != 0) goto L4a
                goto L58
            L4a:
                r15 = 0
            L4b:
                int r14 = r14 + 1
                goto L2d
            L4e:
                if (r12 == 0) goto L56
                if (r15 == 0) goto L56
                r3.onComplete()
                return
            L56:
                if (r15 == 0) goto Ld
            L58:
                int r4 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
                if (r4 != 0) goto L93
                boolean r4 = r0.cancelled
                if (r4 == 0) goto L64
                r18.cleanup()
                return
            L64:
                java.lang.Throwable r4 = r0.error
                if (r4 == 0) goto L6f
                r18.cleanup()
                r3.onError(r4)
                return
            L6f:
                int r4 = r0.done
                if (r4 != 0) goto L75
                r4 = 1
                goto L76
            L75:
                r4 = 0
            L76:
                r12 = 0
            L77:
                if (r12 >= r2) goto L8a
                r13 = r1[r12]
                java.util.Queue<T> r13 = r13.queue
                if (r13 == 0) goto L87
                boolean r13 = r13.isEmpty()
                if (r13 != 0) goto L87
                r13 = 0
                goto L8b
            L87:
                int r12 = r12 + 1
                goto L77
            L8a:
                r13 = 1
            L8b:
                if (r4 == 0) goto L93
                if (r13 == 0) goto L93
                r3.onComplete()
                return
            L93:
                int r4 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
                if (r4 == 0) goto La6
                r8 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r4 == 0) goto La6
                java.util.concurrent.atomic.AtomicLongFieldUpdater<reactor.core.publisher.ParallelMergeSequential$MergeSequentialMain> r4 = reactor.core.publisher.ParallelMergeSequential.MergeSequentialMain.REQUESTED
                long r6 = -r10
                r4.addAndGet(r0, r6)
            La6:
                int r4 = r0.wip
                if (r4 != r5) goto Lb4
                java.util.concurrent.atomic.AtomicIntegerFieldUpdater<reactor.core.publisher.ParallelMergeSequential$MergeSequentialMain> r4 = reactor.core.publisher.ParallelMergeSequential.MergeSequentialMain.WIP
                int r5 = -r5
                int r4 = r4.addAndGet(r0, r5)
                if (r4 != 0) goto Lb4
                return
            Lb4:
                r5 = r4
                goto L8
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.ParallelMergeSequential.MergeSequentialMain.drainLoop():void");
        }
    }

    static final class MergeSequentialInner<T> implements InnerConsumer<T> {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<MergeSequentialInner, Subscription> f2286S = AtomicReferenceFieldUpdater.newUpdater(MergeSequentialInner.class, Subscription.class, "s");
        volatile boolean done;
        final int limit;
        final MergeSequentialMain<T> parent;
        final int prefetch;
        long produced;
        volatile Queue<T> queue;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2287s;

        MergeSequentialInner(MergeSequentialMain<T> mergeSequentialMain, int i) {
            this.parent = mergeSequentialMain;
            this.prefetch = i;
            this.limit = Operators.unboundedOrLimit(i);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2287s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.f2287s;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.queue != null ? this.queue.size() : 0);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.actual.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2286S, this, subscription)) {
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.parent.onNext(this, t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.onComplete();
        }

        void requestOne() {
            long j = this.produced + 1;
            if (j == this.limit) {
                this.produced = 0L;
                this.f2287s.request(j);
            } else {
                this.produced = j;
            }
        }

        public void cancel() {
            Operators.terminate(f2286S, this);
        }

        Queue<T> getQueue(Supplier<Queue<T>> supplier) {
            Queue<T> queue = this.queue;
            if (queue != null) {
                return queue;
            }
            Queue<T> queue2 = supplier.get();
            this.queue = queue2;
            return queue2;
        }
    }
}
