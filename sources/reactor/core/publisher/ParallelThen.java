package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelThen extends Mono<Void> implements Scannable, Fuseable {
    final ParallelFlux<?> source;

    ParallelThen(ParallelFlux<?> parallelFlux) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super Void> coreSubscriber) {
        coreSubscriber.onSubscribe(new ThenMain(coreSubscriber, this.source));
    }

    static final class ThenMain implements InnerProducer<Void>, Fuseable, Fuseable.QueueSubscription<Void> {
        static final long CANCELLED_FLAG = Long.MIN_VALUE;
        static final long INNER_COMPLETED_MAX = 2147483647L;
        static final long REQUESTED_FLAG = 4611686018427387904L;
        static final AtomicLongFieldUpdater<ThenMain> STATE = AtomicLongFieldUpdater.newUpdater(ThenMain.class, "state");
        final CoreSubscriber<? super Void> actual;
        final ParallelFlux<?> source;
        volatile long state;
        final ThenInner[] subscribers;

        static int innersCompletedCount(long j) {
            return (int) (j & INNER_COMPLETED_MAX);
        }

        static boolean isCancelled(long j) {
            return (j & Long.MIN_VALUE) == Long.MIN_VALUE;
        }

        static boolean isRequestedOnce(long j) {
            return (j & REQUESTED_FLAG) == REQUESTED_FLAG;
        }

        @Override // java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.Queue
        public Void poll() {
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

        ThenMain(CoreSubscriber<? super Void> coreSubscriber, ParallelFlux<?> parallelFlux) {
            this.actual = coreSubscriber;
            this.source = parallelFlux;
            int iParallelism = parallelFlux.parallelism();
            ThenInner[] thenInnerArr = new ThenInner[iParallelism];
            for (int i = 0; i < iParallelism; i++) {
                thenInnerArr[i] = new ThenInner(this);
            }
            this.subscribers = thenInnerArr;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Void> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(innersCompletedCount(this.state) == this.source.parallelism());
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isCancelled(this.state) && innersCompletedCount(this.state) != this.source.parallelism());
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return 0;
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            long jMarkCancelled = markCancelled(this);
            if (isCancelled(jMarkCancelled) || !isRequestedOnce(jMarkCancelled)) {
                return;
            }
            for (ThenInner thenInner : this.subscribers) {
                thenInner.cancel();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (STATE.compareAndSet(this, 0L, REQUESTED_FLAG)) {
                this.source.subscribe(this.subscribers);
            }
        }

        void innerError(Throwable th, ThenInner thenInner) {
            long jMarkForceTerminated = markForceTerminated(this);
            int iParallelism = this.source.parallelism();
            if (isCancelled(jMarkForceTerminated) || innersCompletedCount(jMarkForceTerminated) == iParallelism) {
                return;
            }
            for (ThenInner thenInner2 : this.subscribers) {
                if (thenInner2 != thenInner) {
                    thenInner2.cancel();
                }
            }
            this.actual.onError(th);
        }

        void innerComplete() {
            long jMarkInnerCompleted = markInnerCompleted(this);
            int iParallelism = this.source.parallelism();
            int iInnersCompletedCount = innersCompletedCount(jMarkInnerCompleted);
            if (isCancelled(jMarkInnerCompleted) || iInnersCompletedCount == iParallelism || iInnersCompletedCount + 1 != iParallelism) {
                return;
            }
            this.actual.onComplete();
        }

        static long markForceTerminated(ThenMain thenMain) {
            long j;
            int iParallelism = thenMain.source.parallelism();
            do {
                j = thenMain.state;
                if (isCancelled(j) || innersCompletedCount(j) == iParallelism) {
                    break;
                }
            } while (!STATE.compareAndSet(thenMain, j, ((-2147483648L) & j) | Long.MIN_VALUE | ((long) iParallelism)));
            return j;
        }

        static long markCancelled(ThenMain thenMain) {
            long j;
            int iParallelism = thenMain.source.parallelism();
            do {
                j = thenMain.state;
                if (isCancelled(j) || innersCompletedCount(j) == iParallelism) {
                    break;
                }
            } while (!STATE.weakCompareAndSet(thenMain, j, j | Long.MIN_VALUE));
            return j;
        }

        static long markInnerCompleted(ThenMain thenMain) {
            long j;
            int iParallelism = thenMain.source.parallelism();
            do {
                j = thenMain.state;
                if (isCancelled(j) || innersCompletedCount(j) == iParallelism) {
                    break;
                }
            } while (!STATE.compareAndSet(thenMain, j, j + 1));
            return j;
        }
    }

    static final class ThenInner implements InnerConsumer<Object> {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<ThenInner, Subscription> f2291S = AtomicReferenceFieldUpdater.newUpdater(ThenInner.class, Subscription.class, "s");
        final ThenMain parent;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2292s;

        ThenInner(ThenMain thenMain) {
            this.parent = thenMain;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.actual.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2292s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.f2292s;
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
            if (Operators.setOnce(f2291S, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Operators.onDiscard(obj, this.parent.actual.currentContext());
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.innerError(th, this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.innerComplete();
        }

        void cancel() {
            Operators.terminate(f2291S, this);
        }
    }
}
