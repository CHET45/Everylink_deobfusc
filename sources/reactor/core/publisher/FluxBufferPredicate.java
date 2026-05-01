package reactor.core.publisher;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxBufferPredicate<T, C extends Collection<? super T>> extends InternalFluxOperator<T, C> {
    final Supplier<C> bufferSupplier;
    final Mode mode;
    final Predicate<? super T> predicate;

    public enum Mode {
        UNTIL,
        UNTIL_CUT_BEFORE,
        WHILE
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return 1;
    }

    FluxBufferPredicate(Flux<? extends T> flux, Predicate<? super T> predicate, Supplier<C> supplier, Mode mode) {
        super(flux);
        this.predicate = (Predicate) Objects.requireNonNull(predicate, "predicate");
        this.bufferSupplier = (Supplier) Objects.requireNonNull(supplier, "bufferSupplier");
        this.mode = mode;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super C> coreSubscriber) {
        return new BufferPredicateSubscriber(coreSubscriber, (Collection) Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null initial buffer"), this.bufferSupplier, this.predicate, this.mode);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class BufferPredicateSubscriber<T, C extends Collection<? super T>> extends AbstractQueue<C> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, C>, BooleanSupplier {
        static final AtomicLongFieldUpdater<BufferPredicateSubscriber> REQUESTED_BUFFERS = AtomicLongFieldUpdater.newUpdater(BufferPredicateSubscriber.class, "requestedBuffers");
        static final AtomicLongFieldUpdater<BufferPredicateSubscriber> REQUESTED_FROM_SOURCE = AtomicLongFieldUpdater.newUpdater(BufferPredicateSubscriber.class, "requestedFromSource");

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<BufferPredicateSubscriber, Subscription> f2091S = AtomicReferenceFieldUpdater.newUpdater(BufferPredicateSubscriber.class, Subscription.class, "s");
        final CoreSubscriber<? super C> actual;

        @Nullable
        C buffer;
        final Supplier<C> bufferSupplier;
        boolean done;
        volatile boolean fastpath;
        final Mode mode;
        final Predicate<? super T> predicate;
        volatile long requestedBuffers;
        volatile long requestedFromSource;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2092s;

        BufferPredicateSubscriber(CoreSubscriber<? super C> coreSubscriber, C c, Supplier<C> supplier, Predicate<? super T> predicate, Mode mode) {
            this.actual = coreSubscriber;
            this.buffer = c;
            this.bufferSupplier = supplier;
            this.predicate = predicate;
            this.mode = mode;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                if (j == Long.MAX_VALUE) {
                    this.fastpath = true;
                    REQUESTED_BUFFERS.set(this, Long.MAX_VALUE);
                    REQUESTED_FROM_SOURCE.set(this, Long.MAX_VALUE);
                    this.f2092s.request(Long.MAX_VALUE);
                    return;
                }
                if (DrainUtils.postCompleteRequest(j, this.actual, this, REQUESTED_BUFFERS, this, this)) {
                    return;
                }
                Operators.addCap(REQUESTED_FROM_SOURCE, this, j);
                this.f2092s.request(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            synchronized (this) {
                C c = this.buffer;
                this.buffer = null;
                Operators.onDiscardMultiple(c, this.actual.currentContext());
            }
            cleanup();
            Operators.terminate(f2091S, this);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2091S, this, subscription)) {
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t)) {
                return;
            }
            this.f2092s.request(1L);
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return true;
            }
            try {
                boolean zTest = this.predicate.test(t);
                if (this.mode == Mode.UNTIL && zTest) {
                    if (cancelledWhileAdding(t)) {
                        return true;
                    }
                    onNextNewBuffer();
                } else if (this.mode == Mode.UNTIL_CUT_BEFORE && zTest) {
                    onNextNewBuffer();
                    if (cancelledWhileAdding(t)) {
                        return true;
                    }
                } else if (this.mode == Mode.WHILE && !zTest) {
                    onNextNewBuffer();
                } else if (cancelledWhileAdding(t)) {
                    return true;
                }
                if (this.fastpath) {
                    return true;
                }
                AtomicLongFieldUpdater<BufferPredicateSubscriber> atomicLongFieldUpdater = REQUESTED_FROM_SOURCE;
                return (((atomicLongFieldUpdater.decrementAndGet(this) > 0L ? 1 : (atomicLongFieldUpdater.decrementAndGet(this) == 0L ? 0 : -1)) == 0) && ((REQUESTED_BUFFERS.get(this) > 0L ? 1 : (REQUESTED_BUFFERS.get(this) == 0L ? 0 : -1)) > 0) && atomicLongFieldUpdater.compareAndSet(this, 0L, 1L)) ? false : true;
            } catch (Throwable th) {
                Context contextCurrentContext = this.actual.currentContext();
                onError(Operators.onOperatorError(this.f2092s, th, t, contextCurrentContext));
                Operators.onDiscard(t, contextCurrentContext);
                return true;
            }
        }

        boolean cancelledWhileAdding(T t) {
            synchronized (this) {
                C c = this.buffer;
                if (c != null && this.f2092s != Operators.cancelledSubscription()) {
                    c.add(t);
                    return false;
                }
                Operators.onDiscard(t, this.actual.currentContext());
                return true;
            }
        }

        @Nullable
        C triggerNewBuffer() {
            synchronized (this) {
                C c = this.buffer;
                if (c != null && this.f2092s != Operators.cancelledSubscription()) {
                    if (c.isEmpty()) {
                        return null;
                    }
                    try {
                        C c2 = (C) Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer");
                        synchronized (this) {
                            if (this.buffer == null) {
                                return null;
                            }
                            this.buffer = c2;
                            return c;
                        }
                    } catch (Throwable th) {
                        onError(Operators.onOperatorError(this.f2092s, th, this.actual.currentContext()));
                        return null;
                    }
                }
                return null;
            }
        }

        private void onNextNewBuffer() {
            Collection collectionTriggerNewBuffer = triggerNewBuffer();
            if (collectionTriggerNewBuffer != null) {
                if (this.fastpath) {
                    this.actual.onNext(collectionTriggerNewBuffer);
                } else if (REQUESTED_BUFFERS.getAndDecrement(this) > 0) {
                    this.actual.onNext(collectionTriggerNewBuffer);
                } else {
                    cancel();
                    this.actual.onError(Exceptions.failWithOverflow("Could not emit buffer due to lack of requests"));
                }
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super C> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            C c;
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            synchronized (this) {
                c = this.buffer;
                this.buffer = null;
            }
            cleanup();
            Operators.onDiscardMultiple(c, this.actual.currentContext());
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            cleanup();
            DrainUtils.postComplete(this.actual, this, REQUESTED_BUFFERS, this, this);
        }

        void cleanup() {
            Predicate<? super T> predicate = this.predicate;
            if (predicate instanceof Disposable) {
                ((Disposable) predicate).dispose();
            }
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2092s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(getAsBoolean());
            }
            if (attr != Scannable.Attr.CAPACITY) {
                return attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requestedBuffers) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            C c = this.buffer;
            return Integer.valueOf(c != null ? c.size() : 0);
        }

        @Override // java.util.function.BooleanSupplier
        public boolean getAsBoolean() {
            return this.f2092s == Operators.cancelledSubscription();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<C> iterator() {
            if (isEmpty()) {
                return Collections.emptyIterator();
            }
            return Collections.singleton(this.buffer).iterator();
        }

        @Override // java.util.Queue
        public boolean offer(C c) {
            throw new IllegalArgumentException();
        }

        @Override // java.util.Queue
        @Nullable
        public C poll() {
            C c = this.buffer;
            if (c == null || c.isEmpty()) {
                return null;
            }
            synchronized (this) {
                this.buffer = null;
            }
            return c;
        }

        @Override // java.util.Queue
        @Nullable
        public C peek() {
            return this.buffer;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            C c = this.buffer;
            return (c == null || c.isEmpty()) ? 0 : 1;
        }

        @Override // java.util.AbstractCollection
        public String toString() {
            return "FluxBufferPredicate";
        }
    }

    static class ChangedPredicate<T, K> implements Predicate<T>, Disposable {
        private BiPredicate<? super K, ? super K> keyComparator;
        private Function<? super T, ? extends K> keySelector;
        private K lastKey;

        ChangedPredicate(Function<? super T, ? extends K> function, BiPredicate<? super K, ? super K> biPredicate) {
            this.keySelector = function;
            this.keyComparator = biPredicate;
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            this.lastKey = null;
        }

        @Override // java.util.function.Predicate
        public boolean test(T t) {
            K kApply = this.keySelector.apply(t);
            K k = this.lastKey;
            if (k == null) {
                this.lastKey = kApply;
                return false;
            }
            boolean zTest = this.keyComparator.test(k, kApply);
            this.lastKey = kApply;
            return !zTest;
        }
    }
}
