package reactor.core.publisher;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxBufferBoundary<T, U, C extends Collection<? super T>> extends InternalFluxOperator<T, C> {
    final Supplier<C> bufferSupplier;
    final Publisher<U> other;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxBufferBoundary(Flux<? extends T> flux, Publisher<U> publisher, Supplier<C> supplier) {
        super(flux);
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "other"));
        this.bufferSupplier = (Supplier) Objects.requireNonNull(supplier, "bufferSupplier");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super C> coreSubscriber) {
        BufferBoundaryMain bufferBoundaryMain = new BufferBoundaryMain(this.source instanceof FluxInterval ? coreSubscriber : Operators.serialize(coreSubscriber), (Collection) Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer"), this.bufferSupplier);
        coreSubscriber.onSubscribe(bufferBoundaryMain);
        this.other.subscribe(bufferBoundaryMain.other);
        return bufferBoundaryMain;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class BufferBoundaryMain<T, U, C extends Collection<? super T>> implements InnerOperator<T, C> {
        final CoreSubscriber<? super C> actual;
        C buffer;
        final Supplier<C> bufferSupplier;
        final Context ctx;
        final BufferBoundaryOther<U> other = new BufferBoundaryOther<>(this);
        volatile long requested;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2090s;

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<BufferBoundaryMain, Subscription> f2089S = AtomicReferenceFieldUpdater.newUpdater(BufferBoundaryMain.class, Subscription.class, "s");
        static final AtomicLongFieldUpdater<BufferBoundaryMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(BufferBoundaryMain.class, "requested");

        BufferBoundaryMain(CoreSubscriber<? super C> coreSubscriber, C c, Supplier<C> supplier) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.buffer = c;
            this.bufferSupplier = supplier;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super C> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2090s;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2090s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.CAPACITY) {
                C c = this.buffer;
                return Integer.valueOf(c != null ? c.size() : 0);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            return attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Operators.terminate(f2089S, this);
            Operators.onDiscardMultiple(this.buffer, this.ctx);
            this.other.cancel();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2089S, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            synchronized (this) {
                C c = this.buffer;
                if (c != null) {
                    c.add(t);
                } else {
                    Operators.onNextDropped(t, this.ctx);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            C c;
            if (Operators.terminate(f2089S, this)) {
                synchronized (this) {
                    c = this.buffer;
                    this.buffer = null;
                }
                this.other.cancel();
                this.actual.onError(th);
                Operators.onDiscardMultiple(c, this.ctx);
                return;
            }
            Operators.onErrorDropped(th, this.ctx);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            C c;
            if (Operators.terminate(f2089S, this)) {
                synchronized (this) {
                    c = this.buffer;
                    this.buffer = null;
                }
                this.other.cancel();
                if (!c.isEmpty()) {
                    if (emit(c)) {
                        this.actual.onComplete();
                        return;
                    }
                    return;
                }
                this.actual.onComplete();
            }
        }

        void otherComplete() {
            C c;
            Subscription andSet = f2089S.getAndSet(this, Operators.cancelledSubscription());
            if (andSet != Operators.cancelledSubscription()) {
                synchronized (this) {
                    c = this.buffer;
                    this.buffer = null;
                }
                if (andSet != null) {
                    andSet.cancel();
                }
                if (c != null && !c.isEmpty()) {
                    if (emit(c)) {
                        this.actual.onComplete();
                        return;
                    }
                    return;
                }
                this.actual.onComplete();
            }
        }

        void otherError(Throwable th) {
            C c;
            Subscription andSet = f2089S.getAndSet(this, Operators.cancelledSubscription());
            if (andSet != Operators.cancelledSubscription()) {
                synchronized (this) {
                    c = this.buffer;
                    this.buffer = null;
                }
                if (andSet != null) {
                    andSet.cancel();
                }
                this.actual.onError(th);
                Operators.onDiscardMultiple(c, this.ctx);
                return;
            }
            Operators.onErrorDropped(th, this.ctx);
        }

        void otherNext() {
            C c;
            try {
                C c2 = (C) Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer");
                synchronized (this) {
                    c = this.buffer;
                    this.buffer = c2;
                }
                if (c == null || c.isEmpty()) {
                    return;
                }
                emit(c);
            } catch (Throwable th) {
                otherError(Operators.onOperatorError(this.other, th, this.ctx));
            }
        }

        boolean emit(C c) {
            long j = this.requested;
            if (j != 0) {
                this.actual.onNext(c);
                if (j == Long.MAX_VALUE) {
                    return true;
                }
                REQUESTED.decrementAndGet(this);
                return true;
            }
            this.actual.onError(Operators.onOperatorError(this, Exceptions.failWithOverflow(), c, this.ctx));
            Operators.onDiscardMultiple(c, this.ctx);
            return false;
        }
    }

    static final class BufferBoundaryOther<U> extends Operators.DeferredSubscription implements InnerConsumer<U> {
        final BufferBoundaryMain<?, U, ?> main;

        BufferBoundaryOther(BufferBoundaryMain<?, U, ?> bufferBoundaryMain) {
            this.main = bufferBoundaryMain;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (set(subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override // reactor.core.publisher.Operators.DeferredSubscription, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U u) {
            this.main.otherNext();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.main.otherError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.main.otherComplete();
        }
    }
}
