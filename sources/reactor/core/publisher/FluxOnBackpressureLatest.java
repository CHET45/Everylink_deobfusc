package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxOnBackpressureLatest<T> extends InternalFluxOperator<T, T> {
    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxOnBackpressureLatest(Flux<? extends T> flux) {
        super(flux);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new LatestSubscriber(coreSubscriber);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class LatestSubscriber<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        final Context ctx;
        volatile boolean done;
        Throwable error;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2163s;
        volatile T value;
        volatile int wip;
        static final AtomicLongFieldUpdater<LatestSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(LatestSubscriber.class, "requested");
        static final AtomicIntegerFieldUpdater<LatestSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(LatestSubscriber.class, "wip");
        static final AtomicReferenceFieldUpdater<LatestSubscriber, Object> VALUE = AtomicReferenceFieldUpdater.newUpdater(LatestSubscriber.class, Object.class, "value");

        LatestSubscriber(CoreSubscriber<? super T> coreSubscriber) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
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
            Object andSet;
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.f2163s.cancel();
            if (WIP.getAndIncrement(this) != 0 || (andSet = VALUE.getAndSet(this, null)) == null) {
                return;
            }
            Operators.onDiscard(andSet, this.ctx);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2163s, subscription)) {
                this.f2163s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            Object andSet = VALUE.getAndSet(this, t);
            if (andSet != null) {
                Operators.onDiscard(andSet, this.ctx);
            }
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
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
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            int iAddAndGet = 1;
            do {
                if (checkTerminated(this.done, this.value == null, coreSubscriber)) {
                    return;
                }
                long j = this.requested;
                long j2 = 0;
                while (j != j2) {
                    boolean z = this.done;
                    Object andSet = VALUE.getAndSet(this, null);
                    boolean z2 = andSet == null;
                    if (checkTerminated(z, z2, coreSubscriber)) {
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    coreSubscriber.onNext(andSet);
                    j2++;
                }
                if (j == j2) {
                    if (checkTerminated(this.done, this.value == null, coreSubscriber)) {
                        return;
                    }
                }
                if (j2 != 0 && j != Long.MAX_VALUE) {
                    Operators.produced(REQUESTED, this, j2);
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<? super T> subscriber) {
            if (this.cancelled) {
                Object andSet = VALUE.getAndSet(this, null);
                if (andSet != null) {
                    Operators.onDiscard(andSet, this.ctx);
                }
                return true;
            }
            if (!z) {
                return false;
            }
            Throwable th = this.error;
            if (th == null) {
                if (!z2) {
                    return false;
                }
                subscriber.onComplete();
                return true;
            }
            Object andSet2 = VALUE.getAndSet(this, null);
            if (andSet2 != null) {
                Operators.onDiscard(andSet2, this.ctx);
            }
            subscriber.onError(th);
            return true;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2163s;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.value != null ? 1 : 0);
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
