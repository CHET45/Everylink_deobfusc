package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelGroup<T> extends Flux<GroupedFlux<Integer, T>> implements Scannable, Fuseable {
    final ParallelFlux<? extends T> source;

    ParallelGroup(ParallelFlux<? extends T> parallelFlux) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super GroupedFlux<Integer, T>> coreSubscriber) {
        int iParallelism = this.source.parallelism();
        ParallelInnerGroup[] parallelInnerGroupArr = new ParallelInnerGroup[iParallelism];
        for (int i = 0; i < iParallelism; i++) {
            parallelInnerGroupArr[i] = new ParallelInnerGroup(i);
        }
        FluxArray.subscribe(coreSubscriber, parallelInnerGroupArr);
        this.source.subscribe(parallelInnerGroupArr);
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    static final class ParallelInnerGroup<T> extends GroupedFlux<Integer, T> implements InnerOperator<T, T> {
        CoreSubscriber<? super T> actual;
        final int key;
        volatile int once;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2283s;
        static final AtomicIntegerFieldUpdater<ParallelInnerGroup> ONCE = AtomicIntegerFieldUpdater.newUpdater(ParallelInnerGroup.class, "once");

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<ParallelInnerGroup, Subscription> f2282S = AtomicReferenceFieldUpdater.newUpdater(ParallelInnerGroup.class, Subscription.class, "s");
        static final AtomicLongFieldUpdater<ParallelInnerGroup> REQUESTED = AtomicLongFieldUpdater.newUpdater(ParallelInnerGroup.class, "requested");

        ParallelInnerGroup(int i) {
            this.key = i;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // reactor.core.publisher.GroupedFlux
        public Integer key() {
            return Integer.valueOf(this.key);
        }

        @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
        public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.actual = coreSubscriber;
                coreSubscriber.onSubscribe(this);
            } else {
                Operators.error(coreSubscriber, new IllegalStateException("This ParallelGroup can be subscribed to at most once."));
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2283s;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2283s == Operators.cancelledSubscription());
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2282S, this, subscription)) {
                long andSet = REQUESTED.getAndSet(this, 0L);
                if (andSet != 0) {
                    subscription.request(andSet);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Subscription subscription = this.f2283s;
                if (subscription == null) {
                    AtomicLongFieldUpdater<ParallelInnerGroup> atomicLongFieldUpdater = REQUESTED;
                    Operators.addCap(atomicLongFieldUpdater, this, j);
                    Subscription subscription2 = this.f2283s;
                    if (subscription2 == null || atomicLongFieldUpdater.getAndSet(this, 0L) == 0) {
                        return;
                    }
                    subscription2.request(j);
                    return;
                }
                subscription.request(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Operators.terminate(f2282S, this);
        }
    }
}
