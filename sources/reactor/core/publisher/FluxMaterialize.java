package reactor.core.publisher;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.BooleanSupplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxMaterialize<T> extends InternalFluxOperator<T, Signal<T>> {
    FluxMaterialize(Flux<T> flux) {
        super(flux);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Signal<T>> coreSubscriber) {
        return new MaterializeSubscriber(coreSubscriber);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class MaterializeSubscriber<T> extends AbstractQueue<Signal<T>> implements InnerOperator<T, Signal<T>>, BooleanSupplier {
        static final AtomicLongFieldUpdater<MaterializeSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(MaterializeSubscriber.class, "requested");
        static final Signal empty = new ImmutableSignal(Context.empty(), SignalType.ON_NEXT, null, null, null);
        final CoreSubscriber<? super Signal<T>> actual;
        final Context cachedContext;
        volatile boolean cancelled;
        long produced;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2150s;
        Signal<T> terminalSignal;

        MaterializeSubscriber(CoreSubscriber<? super Signal<T>> coreSubscriber) {
            this.actual = coreSubscriber;
            this.cachedContext = coreSubscriber.currentContext();
        }

        @Override // reactor.core.publisher.InnerOperator, reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.cachedContext;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2150s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.terminalSignal != null);
            }
            if (attr != Scannable.Attr.ERROR) {
                return attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(getAsBoolean()) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(size()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            Signal<T> signal = this.terminalSignal;
            if (signal != null) {
                return signal.getThrowable();
            }
            return null;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Signal<T>> actual() {
            return this.actual;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2150s, subscription)) {
                this.f2150s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.terminalSignal != null) {
                Operators.onNextDropped(t, this.cachedContext);
            } else {
                this.produced++;
                this.actual.onNext(Signal.next(t, this.cachedContext));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.terminalSignal != null) {
                Operators.onErrorDropped(th, this.cachedContext);
                return;
            }
            this.terminalSignal = Signal.error(th, this.cachedContext);
            long j = this.produced;
            if (j != 0) {
                Operators.addCap(REQUESTED, this, -j);
            }
            DrainUtils.postComplete(this.actual, this, REQUESTED, this, this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.terminalSignal != null) {
                return;
            }
            this.terminalSignal = Signal.complete(this.cachedContext);
            long j = this.produced;
            if (j != 0) {
                Operators.addCap(REQUESTED, this, -j);
            }
            DrainUtils.postComplete(this.actual, this, REQUESTED, this, this);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (!Operators.validate(j) || DrainUtils.postCompleteRequest(j, this.actual, this, REQUESTED, this, this)) {
                return;
            }
            this.f2150s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.f2150s.cancel();
        }

        @Override // java.util.function.BooleanSupplier
        public boolean getAsBoolean() {
            return this.cancelled;
        }

        @Override // java.util.Queue
        public boolean offer(Signal<T> signal) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Queue
        @Nullable
        public Signal<T> poll() {
            Signal<T> signal;
            Signal<T> signal2 = this.terminalSignal;
            if (signal2 == null || signal2 == (signal = empty)) {
                return null;
            }
            this.terminalSignal = signal;
            return signal2;
        }

        @Override // java.util.Queue
        @Nullable
        public Signal<T> peek() {
            Signal<T> signal = empty;
            Signal<T> signal2 = this.terminalSignal;
            if (signal == signal2) {
                return null;
            }
            return signal2;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<Signal<T>> iterator() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            Signal<T> signal = this.terminalSignal;
            return (signal == null || signal == empty) ? 0 : 1;
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            return "MaterializeSubscriber";
        }
    }
}
