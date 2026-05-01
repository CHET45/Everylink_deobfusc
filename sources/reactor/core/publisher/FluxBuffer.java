package reactor.core.publisher;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxBuffer<T, C extends Collection<? super T>> extends InternalFluxOperator<T, C> {
    final Supplier<C> bufferSupplier;
    final int size;
    final int skip;

    FluxBuffer(Flux<? extends T> flux, int i, Supplier<C> supplier) {
        this(flux, i, i, supplier);
    }

    FluxBuffer(Flux<? extends T> flux, int i, int i2, Supplier<C> supplier) {
        super(flux);
        if (i <= 0) {
            throw new IllegalArgumentException("size > 0 required but it was " + i);
        }
        if (i2 <= 0) {
            throw new IllegalArgumentException("skip > 0 required but it was " + i2);
        }
        this.size = i;
        this.skip = i2;
        this.bufferSupplier = (Supplier) Objects.requireNonNull(supplier, "bufferSupplier");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super C> coreSubscriber) {
        int i = this.size;
        int i2 = this.skip;
        if (i == i2) {
            return new BufferExactSubscriber(coreSubscriber, this.size, this.bufferSupplier);
        }
        if (i2 > i) {
            return new BufferSkipSubscriber(coreSubscriber, this.size, this.skip, this.bufferSupplier);
        }
        return new BufferOverlappingSubscriber(coreSubscriber, this.size, this.skip, this.bufferSupplier);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class BufferExactSubscriber<T, C extends Collection<? super T>> implements InnerOperator<T, C> {
        final CoreSubscriber<? super C> actual;
        C buffer;
        final Supplier<C> bufferSupplier;
        boolean done;

        /* JADX INFO: renamed from: s */
        Subscription f2086s;
        final int size;

        BufferExactSubscriber(CoreSubscriber<? super C> coreSubscriber, int i, Supplier<C> supplier) {
            this.actual = coreSubscriber;
            this.size = i;
            this.bufferSupplier = supplier;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                this.f2086s.request(Operators.multiplyCap(j, this.size));
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2086s.cancel();
            Operators.onDiscardMultiple(this.buffer, this.actual.currentContext());
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2086s, subscription)) {
                this.f2086s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            C c = this.buffer;
            if (c == null) {
                try {
                    c = (C) Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer");
                    this.buffer = c;
                } catch (Throwable th) {
                    Context contextCurrentContext = this.actual.currentContext();
                    onError(Operators.onOperatorError(this.f2086s, th, t, contextCurrentContext));
                    Operators.onDiscard(t, contextCurrentContext);
                    return;
                }
            }
            if (c.add(t)) {
                if (c.size() == this.size) {
                    this.buffer = null;
                    this.actual.onNext(c);
                    return;
                }
                return;
            }
            Operators.onDiscard(t, this.actual.currentContext());
            this.f2086s.request(1L);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(th);
            Operators.onDiscardMultiple(this.buffer, this.actual.currentContext());
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            C c = this.buffer;
            if (c != null && !c.isEmpty()) {
                this.actual.onNext(c);
            }
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super C> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2086s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                C c = this.buffer;
                return Integer.valueOf(c != null ? c.size() : 0);
            }
            if (attr != Scannable.Attr.CAPACITY && attr != Scannable.Attr.PREFETCH) {
                return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            return Integer.valueOf(this.size);
        }
    }

    static final class BufferSkipSubscriber<T, C extends Collection<? super T>> implements InnerOperator<T, C> {
        static final AtomicIntegerFieldUpdater<BufferSkipSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(BufferSkipSubscriber.class, "wip");
        final CoreSubscriber<? super C> actual;
        C buffer;
        final Supplier<C> bufferSupplier;
        final Context ctx;
        boolean done;
        long index;

        /* JADX INFO: renamed from: s */
        Subscription f2088s;
        final int size;
        final int skip;
        volatile int wip;

        BufferSkipSubscriber(CoreSubscriber<? super C> coreSubscriber, int i, int i2, Supplier<C> supplier) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.size = i;
            this.skip = i2;
            this.bufferSupplier = supplier;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                    this.f2088s.request(Operators.addCap(Operators.multiplyCap(j, this.size), Operators.multiplyCap(this.skip - this.size, j - 1)));
                    return;
                }
                this.f2088s.request(Operators.multiplyCap(this.skip, j));
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2088s.cancel();
            Operators.onDiscardMultiple(this.buffer, this.ctx);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2088s, subscription)) {
                this.f2088s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            C c = this.buffer;
            long j = this.index;
            if (j % ((long) this.skip) == 0) {
                try {
                    c = (C) Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer");
                    this.buffer = c;
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2088s, th, t, this.ctx));
                    Operators.onDiscard(t, this.ctx);
                    return;
                }
            }
            if (c != null) {
                c.add(t);
                if (c.size() == this.size) {
                    this.buffer = null;
                    this.actual.onNext(c);
                }
            } else {
                Operators.onDiscard(t, this.ctx);
            }
            this.index = j + 1;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.ctx);
                return;
            }
            this.done = true;
            C c = this.buffer;
            this.buffer = null;
            this.actual.onError(th);
            Operators.onDiscardMultiple(c, this.ctx);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            C c = this.buffer;
            this.buffer = null;
            if (c != null) {
                this.actual.onNext(c);
            }
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super C> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2088s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CAPACITY) {
                return Integer.valueOf(this.size);
            }
            if (attr != Scannable.Attr.BUFFERED) {
                return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.size) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            C c = this.buffer;
            return Integer.valueOf(c != null ? c.size() : 0);
        }
    }

    static final class BufferOverlappingSubscriber<T, C extends Collection<? super T>> extends ArrayDeque<C> implements BooleanSupplier, InnerOperator<T, C> {
        static final AtomicIntegerFieldUpdater<BufferOverlappingSubscriber> ONCE = AtomicIntegerFieldUpdater.newUpdater(BufferOverlappingSubscriber.class, "once");
        static final AtomicLongFieldUpdater<BufferOverlappingSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BufferOverlappingSubscriber.class, "requested");
        final CoreSubscriber<? super C> actual;
        final Supplier<C> bufferSupplier;
        volatile boolean cancelled;
        boolean done;
        long index;
        volatile int once;
        long produced;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2087s;
        final int size;
        final int skip;

        BufferOverlappingSubscriber(CoreSubscriber<? super C> coreSubscriber, int i, int i2, Supplier<C> supplier) {
            this.actual = coreSubscriber;
            this.size = i;
            this.skip = i2;
            this.bufferSupplier = supplier;
        }

        @Override // java.util.function.BooleanSupplier
        public boolean getAsBoolean() {
            return this.cancelled;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j) && !DrainUtils.postCompleteRequest(j, this.actual, this, REQUESTED, this, this)) {
                if (this.once == 0 && ONCE.compareAndSet(this, 0, 1)) {
                    this.f2087s.request(Operators.addCap(this.size, Operators.multiplyCap(this.skip, j - 1)));
                } else {
                    this.f2087s.request(Operators.multiplyCap(this.skip, j));
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.f2087s.cancel();
            clear();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2087s, subscription)) {
                this.f2087s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long j = this.index;
            if (j % ((long) this.skip) == 0) {
                try {
                    offer((Collection) Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer"));
                } catch (Throwable th) {
                    Context contextCurrentContext = this.actual.currentContext();
                    onError(Operators.onOperatorError(this.f2087s, th, t, contextCurrentContext));
                    Operators.onDiscard(t, contextCurrentContext);
                    return;
                }
            }
            C cPeek = peek();
            if (cPeek != null && cPeek.size() + 1 == this.size) {
                poll();
                cPeek.add(t);
                this.actual.onNext(cPeek);
                this.produced++;
            }
            Iterator<C> it = iterator();
            while (it.hasNext()) {
                it.next().add(t);
            }
            this.index = j + 1;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            clear();
            this.actual.onError(th);
        }

        @Override // java.util.ArrayDeque, java.util.AbstractCollection, java.util.Collection
        public void clear() {
            Context contextCurrentContext = this.actual.currentContext();
            Iterator<C> it = iterator();
            while (it.hasNext()) {
                Operators.onDiscardMultiple(it.next(), contextCurrentContext);
            }
            super.clear();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            long j = this.produced;
            if (j != 0) {
                Operators.produced(REQUESTED, this, j);
            }
            DrainUtils.postComplete(this.actual, this, REQUESTED, this, this);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super C> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2087s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.CAPACITY) {
                return Integer.valueOf(size() * this.size);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(stream().mapToInt(new FluxBuffer$BufferOverlappingSubscriber$$ExternalSyntheticLambda0()).sum());
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            return attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
