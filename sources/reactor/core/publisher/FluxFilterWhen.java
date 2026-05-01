package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
class FluxFilterWhen<T> extends InternalFluxOperator<T, T> {
    final Function<? super T, ? extends Publisher<Boolean>> asyncPredicate;
    final int bufferSize;

    FluxFilterWhen(Flux<T> flux, Function<? super T, ? extends Publisher<Boolean>> function, int i) {
        super(flux);
        this.asyncPredicate = function;
        this.bufferSize = i;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new FluxFilterWhenSubscriber(coreSubscriber, this.asyncPredicate, this.bufferSize);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class FluxFilterWhenSubscriber<T> implements InnerOperator<T, T> {
        static final int STATE_FRESH = 0;
        static final int STATE_RESULT = 2;
        static final int STATE_RUNNING = 1;
        final CoreSubscriber<? super T> actual;
        final Function<? super T, ? extends Publisher<Boolean>> asyncPredicate;
        final int bufferSize;
        volatile boolean cancelled;
        int consumed;
        long consumerIndex;
        final Context ctx;
        volatile FilterWhenInner current;
        volatile boolean done;
        long emitted;
        volatile Throwable error;
        Boolean innerResult;
        long producerIndex;
        volatile long requested;
        volatile int state;
        final AtomicReferenceArray<T> toFilter;
        Subscription upstream;
        volatile int wip;
        static final AtomicReferenceFieldUpdater<FluxFilterWhenSubscriber, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(FluxFilterWhenSubscriber.class, Throwable.class, "error");
        static final AtomicLongFieldUpdater<FluxFilterWhenSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(FluxFilterWhenSubscriber.class, "requested");
        static final AtomicIntegerFieldUpdater<FluxFilterWhenSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(FluxFilterWhenSubscriber.class, "wip");
        static final AtomicReferenceFieldUpdater<FluxFilterWhenSubscriber, FilterWhenInner> CURRENT = AtomicReferenceFieldUpdater.newUpdater(FluxFilterWhenSubscriber.class, FilterWhenInner.class, "current");
        static final FilterWhenInner INNER_CANCELLED = new FilterWhenInner(null, false);

        FluxFilterWhenSubscriber(CoreSubscriber<? super T> coreSubscriber, Function<? super T, ? extends Publisher<Boolean>> function, int i) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.toFilter = new AtomicReferenceArray<>(Queues.ceilingNextPowerOfTwo(i));
            this.asyncPredicate = function;
            this.bufferSize = i;
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long j = this.producerIndex;
            this.toFilter.lazySet((this.toFilter.length() - 1) & ((int) j), t);
            this.producerIndex = j + 1;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            ERROR.set(this, th);
            this.done = true;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            drain();
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
            this.upstream.cancel();
            cancelInner();
            if (WIP.getAndIncrement(this) == 0) {
                clear();
            }
        }

        void cancelInner() {
            FilterWhenInner andSet;
            AtomicReferenceFieldUpdater<FluxFilterWhenSubscriber, FilterWhenInner> atomicReferenceFieldUpdater = CURRENT;
            FilterWhenInner filterWhenInner = atomicReferenceFieldUpdater.get(this);
            FilterWhenInner filterWhenInner2 = INNER_CANCELLED;
            if (filterWhenInner == filterWhenInner2 || (andSet = atomicReferenceFieldUpdater.getAndSet(this, filterWhenInner2)) == null || andSet == filterWhenInner2) {
                return;
            }
            andSet.cancel();
        }

        void clear() {
            int length = this.toFilter.length();
            for (int i = 0; i < length; i++) {
                Operators.onDiscard(this.toFilter.getAndSet(i, null), this.ctx);
            }
            this.innerResult = null;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.actual.onSubscribe(this);
                subscription.request(this.bufferSize);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:51:0x00dc  */
        /* JADX WARN: Removed duplicated region for block: B:52:0x00e6  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void drain() {
            /*
                Method dump skipped, instruction units count: 364
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.FluxFilterWhen.FluxFilterWhenSubscriber.drain():void");
        }

        void clearCurrent() {
            FilterWhenInner filterWhenInner = this.current;
            if (filterWhenInner != INNER_CANCELLED) {
                C0162xc40028dd.m5m(CURRENT, this, filterWhenInner, null);
            }
        }

        void innerResult(Boolean bool) {
            this.innerResult = bool;
            this.state = 2;
            clearCurrent();
            drain();
        }

        void innerError(Throwable th) {
            Exceptions.addThrowable(ERROR, this, th);
            this.state = 2;
            clearCurrent();
            drain();
        }

        void innerComplete() {
            this.state = 2;
            clearCurrent();
            drain();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.upstream;
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
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.CAPACITY) {
                return Integer.valueOf(this.toFilter.length());
            }
            if (attr == Scannable.Attr.LARGE_BUFFERED) {
                return Long.valueOf(this.producerIndex - this.consumerIndex);
            }
            if (attr != Scannable.Attr.BUFFERED) {
                return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.bufferSize) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            long j = this.producerIndex - this.consumerIndex;
            if (j <= 2147483647L) {
                return Integer.valueOf((int) j);
            }
            return Integer.MIN_VALUE;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            FilterWhenInner filterWhenInner = this.current;
            return filterWhenInner == null ? Stream.empty() : Stream.of(filterWhenInner);
        }
    }

    static final class FilterWhenInner implements InnerConsumer<Boolean> {
        static final AtomicReferenceFieldUpdater<FilterWhenInner, Subscription> SUB = AtomicReferenceFieldUpdater.newUpdater(FilterWhenInner.class, Subscription.class, "sub");
        final boolean cancelOnNext;
        boolean done;
        final FluxFilterWhenSubscriber<?> parent;
        volatile Subscription sub;

        FilterWhenInner(FluxFilterWhenSubscriber<?> fluxFilterWhenSubscriber, boolean z) {
            this.parent = fluxFilterWhenSubscriber;
            this.cancelOnNext = z;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(SUB, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Boolean bool) {
            if (this.done) {
                return;
            }
            if (this.cancelOnNext) {
                this.sub.cancel();
            }
            this.done = true;
            this.parent.innerResult(bool);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!this.done) {
                this.done = true;
                this.parent.innerError(th);
            } else {
                Operators.onErrorDropped(th, this.parent.currentContext());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.innerComplete();
        }

        void cancel() {
            Operators.terminate(SUB, this);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.sub;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.sub == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.done ? 0L : 1L);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }
}
