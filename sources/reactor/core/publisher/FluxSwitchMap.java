package reactor.core.publisher;

import android.Manifest;
import androidx.concurrent.futures.C0162xc40028dd;
import com.aivox.base.common.Constant;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxSwitchMap<T, R> extends InternalFluxOperator<T, R> {
    static final SwitchMapInner<Object> CANCELLED_INNER = new SwitchMapInner<>(null, 0, Long.MAX_VALUE);
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final int prefetch;
    final Supplier<? extends Queue<Object>> queueSupplier;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxSwitchMap(Flux<? extends T> flux, Function<? super T, ? extends Publisher<? extends R>> function, Supplier<? extends Queue<Object>> supplier, int i) {
        super(flux);
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.mapper = (Function) Objects.requireNonNull(function, "mapper");
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
        this.prefetch = i;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, coreSubscriber, this.mapper, false, false)) {
            return null;
        }
        return new SwitchMapMain(coreSubscriber, this.mapper, this.queueSupplier.get(), this.prefetch);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SwitchMapMain<T, R> implements InnerOperator<T, R> {
        volatile int active = 1;
        final CoreSubscriber<? super R> actual;
        volatile boolean cancelled;
        volatile boolean done;
        volatile Throwable error;
        volatile long index;
        volatile SwitchMapInner<R> inner;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        volatile int once;
        final int prefetch;
        final Queue<Object> queue;
        final BiPredicate<Object, Object> queueBiAtomic;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2200s;
        volatile int wip;
        static final AtomicReferenceFieldUpdater<SwitchMapMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(SwitchMapMain.class, Throwable.class, "error");
        static final AtomicIntegerFieldUpdater<SwitchMapMain> ONCE = AtomicIntegerFieldUpdater.newUpdater(SwitchMapMain.class, "once");
        static final AtomicLongFieldUpdater<SwitchMapMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(SwitchMapMain.class, "requested");
        static final AtomicIntegerFieldUpdater<SwitchMapMain> WIP = AtomicIntegerFieldUpdater.newUpdater(SwitchMapMain.class, "wip");
        static final AtomicReferenceFieldUpdater<SwitchMapMain, SwitchMapInner> INNER = AtomicReferenceFieldUpdater.newUpdater(SwitchMapMain.class, SwitchMapInner.class, "inner");
        static final AtomicLongFieldUpdater<SwitchMapMain> INDEX = AtomicLongFieldUpdater.newUpdater(SwitchMapMain.class, Constant.KEY_INDEX);
        static final AtomicIntegerFieldUpdater<SwitchMapMain> ACTIVE = AtomicIntegerFieldUpdater.newUpdater(SwitchMapMain.class, "active");

        SwitchMapMain(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Publisher<? extends R>> function, Queue<Object> queue, int i) {
            this.actual = coreSubscriber;
            this.mapper = function;
            this.queue = queue;
            this.prefetch = i;
            if (queue instanceof BiPredicate) {
                this.queueBiAtomic = (BiPredicate) queue;
            } else {
                this.queueBiAtomic = null;
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.PARENT ? this.f2200s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.prefetch) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(this.queue.size()) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.inner);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2200s, subscription)) {
                this.f2200s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long jIncrementAndGet = INDEX.incrementAndGet(this);
            SwitchMapInner<R> switchMapInner = this.inner;
            if (switchMapInner != null) {
                switchMapInner.deactivate();
                switchMapInner.cancel();
            }
            try {
                Publisher publisher = (Publisher) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null publisher");
                SwitchMapInner switchMapInner2 = new SwitchMapInner(this, this.prefetch, jIncrementAndGet);
                if (C0162xc40028dd.m5m(INNER, this, switchMapInner, switchMapInner2)) {
                    ACTIVE.getAndIncrement(this);
                    Operators.toFluxOrMono(publisher).subscribe((Subscriber) switchMapInner2);
                }
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2200s, th, t, this.actual.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            if (Exceptions.addThrowable(ERROR, this, th)) {
                if (ONCE.compareAndSet(this, 0, 1)) {
                    deactivate();
                }
                cancelInner();
                this.done = true;
                drain();
                return;
            }
            Operators.onErrorDropped(th, this.actual.currentContext());
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            if (ONCE.compareAndSet(this, 0, 1)) {
                deactivate();
            }
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
            if (WIP.getAndIncrement(this) == 0) {
                cancelAndCleanup(this.queue);
            }
        }

        void deactivate() {
            ACTIVE.decrementAndGet(this);
        }

        void cancelInner() {
            SwitchMapInner<Object> andSet = INNER.getAndSet(this, FluxSwitchMap.CANCELLED_INNER);
            if (andSet == null || andSet == FluxSwitchMap.CANCELLED_INNER) {
                return;
            }
            andSet.cancel();
            andSet.deactivate();
        }

        void cancelAndCleanup(Queue<?> queue) {
            this.f2200s.cancel();
            cancelInner();
            queue.clear();
        }

        void drain() {
            Manifest manifest;
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber<? super R> coreSubscriber = this.actual;
            Queue<?> queue = this.queue;
            int iAddAndGet = 1;
            do {
                long j = this.requested;
                long j2 = 0;
                while (true) {
                    if (j == j2) {
                        break;
                    }
                    boolean z = this.active == 0;
                    SwitchMapInner switchMapInner = (SwitchMapInner) queue.poll();
                    boolean z2 = switchMapInner == null;
                    if (checkTerminated(z, z2, coreSubscriber, queue)) {
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    do {
                        manifest = (Object) queue.poll();
                    } while (manifest == null);
                    int i = iAddAndGet;
                    if (this.index == switchMapInner.index) {
                        coreSubscriber.onNext(manifest);
                        switchMapInner.requestOne();
                        j2++;
                    }
                    iAddAndGet = i;
                }
                int i2 = iAddAndGet;
                if (j == j2) {
                    if (checkTerminated(this.active == 0, queue.isEmpty(), coreSubscriber, queue)) {
                        return;
                    }
                }
                if (j2 != 0 && j != Long.MAX_VALUE) {
                    REQUESTED.addAndGet(this, -j2);
                }
                iAddAndGet = WIP.addAndGet(this, -i2);
            } while (iAddAndGet != 0);
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, Queue<?> queue) {
            if (this.cancelled) {
                cancelAndCleanup(queue);
                return true;
            }
            if (!z) {
                return false;
            }
            Throwable thTerminate = Exceptions.terminate(ERROR, this);
            if (thTerminate != null && thTerminate != Exceptions.TERMINATED) {
                cancelAndCleanup(queue);
                subscriber.onError(thTerminate);
                return true;
            }
            if (!z2) {
                return false;
            }
            subscriber.onComplete();
            return true;
        }

        void innerNext(SwitchMapInner<R> switchMapInner, R r) {
            BiPredicate<Object, Object> biPredicate = this.queueBiAtomic;
            if (biPredicate != null) {
                biPredicate.test(switchMapInner, r);
            } else {
                this.queue.offer(switchMapInner);
                this.queue.offer(r);
            }
            drain();
        }

        void innerError(SwitchMapInner<R> switchMapInner, Throwable th) {
            if (Exceptions.addThrowable(ERROR, this, th)) {
                this.f2200s.cancel();
                if (ONCE.compareAndSet(this, 0, 1)) {
                    deactivate();
                }
                switchMapInner.deactivate();
                drain();
                return;
            }
            Operators.onErrorDropped(th, this.actual.currentContext());
        }

        void innerComplete(SwitchMapInner<R> switchMapInner) {
            switchMapInner.deactivate();
            drain();
        }
    }

    static final class SwitchMapInner<R> implements InnerConsumer<R>, Subscription {
        static final AtomicIntegerFieldUpdater<SwitchMapInner> ONCE = AtomicIntegerFieldUpdater.newUpdater(SwitchMapInner.class, "once");

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<SwitchMapInner, Subscription> f2198S = AtomicReferenceFieldUpdater.newUpdater(SwitchMapInner.class, Subscription.class, "s");
        final long index;
        final int limit;
        volatile int once;
        final SwitchMapMain<?, R> parent;
        final int prefetch;
        int produced;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2199s;

        SwitchMapInner(SwitchMapMain<?, R> switchMapMain, int i, long j) {
            this.parent = switchMapMain;
            this.prefetch = i;
            this.limit = Operators.unboundedOrLimit(i);
            this.index = j;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2199s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.f2199s;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            Subscription subscription2 = this.f2199s;
            if (subscription2 == Operators.cancelledSubscription()) {
                subscription.cancel();
            }
            if (subscription2 != null) {
                subscription.cancel();
                Operators.reportSubscriptionSet();
            } else if (C0162xc40028dd.m5m(f2198S, this, null, subscription)) {
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            } else if (this.f2199s != Operators.cancelledSubscription()) {
                subscription.cancel();
                Operators.reportSubscriptionSet();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            this.parent.innerNext(this, r);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.innerError(this, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.innerComplete(this);
        }

        void deactivate() {
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.parent.deactivate();
            }
        }

        void requestOne() {
            int i = this.produced + 1;
            if (i == this.limit) {
                this.produced = 0;
                this.f2199s.request(i);
            } else {
                this.produced = i;
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            long j2 = ((long) this.produced) + j;
            if (j2 >= this.limit) {
                this.produced = 0;
                this.f2199s.request(j2);
            } else {
                this.produced = (int) j2;
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Subscription andSet;
            if (this.f2199s == Operators.cancelledSubscription() || (andSet = f2198S.getAndSet(this, Operators.cancelledSubscription())) == null || andSet == Operators.cancelledSubscription()) {
                return;
            }
            andSet.cancel();
        }
    }
}
