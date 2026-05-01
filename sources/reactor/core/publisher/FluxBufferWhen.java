package reactor.core.publisher;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxBufferWhen<T, OPEN, CLOSE, BUFFER extends Collection<? super T>> extends InternalFluxOperator<T, BUFFER> {
    final Supplier<BUFFER> bufferSupplier;
    final Function<? super OPEN, ? extends Publisher<CLOSE>> end;
    final Supplier<? extends Queue<BUFFER>> queueSupplier;
    final Publisher<OPEN> start;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxBufferWhen(Flux<? extends T> flux, Publisher<OPEN> publisher, Function<? super OPEN, ? extends Publisher<CLOSE>> function, Supplier<BUFFER> supplier, Supplier<? extends Queue<BUFFER>> supplier2) {
        super(flux);
        this.start = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "start"));
        this.end = (Function) Objects.requireNonNull(function, "end");
        this.bufferSupplier = (Supplier) Objects.requireNonNull(supplier, "bufferSupplier");
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier2, "queueSupplier");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super BUFFER> coreSubscriber) {
        BufferWhenMainSubscriber bufferWhenMainSubscriber = new BufferWhenMainSubscriber(coreSubscriber, this.bufferSupplier, this.queueSupplier, this.start, this.end);
        coreSubscriber.onSubscribe(bufferWhenMainSubscriber);
        BufferWhenOpenSubscriber bufferWhenOpenSubscriber = new BufferWhenOpenSubscriber(bufferWhenMainSubscriber);
        if (!bufferWhenMainSubscriber.subscribers.add(bufferWhenOpenSubscriber)) {
            return null;
        }
        this.start.subscribe(bufferWhenOpenSubscriber);
        return bufferWhenMainSubscriber;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class BufferWhenMainSubscriber<T, OPEN, CLOSE, BUFFER extends Collection<? super T>> implements InnerOperator<T, BUFFER> {
        final CoreSubscriber<? super BUFFER> actual;
        final Function<? super OPEN, ? extends Publisher<? extends CLOSE>> bufferClose;
        final Publisher<? extends OPEN> bufferOpen;
        final Supplier<BUFFER> bufferSupplier;
        volatile boolean cancelled;
        final Context ctx;
        volatile boolean done;
        long emitted;
        volatile Throwable errors;
        long index;
        final Queue<BUFFER> queue;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2094s;
        volatile int windows;
        static final AtomicLongFieldUpdater<BufferWhenMainSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BufferWhenMainSubscriber.class, "requested");

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<BufferWhenMainSubscriber, Subscription> f2093S = AtomicReferenceFieldUpdater.newUpdater(BufferWhenMainSubscriber.class, Subscription.class, "s");
        static final AtomicReferenceFieldUpdater<BufferWhenMainSubscriber, Throwable> ERRORS = AtomicReferenceFieldUpdater.newUpdater(BufferWhenMainSubscriber.class, Throwable.class, "errors");
        static final AtomicIntegerFieldUpdater<BufferWhenMainSubscriber> WINDOWS = AtomicIntegerFieldUpdater.newUpdater(BufferWhenMainSubscriber.class, "windows");
        LinkedHashMap<Long, BUFFER> buffers = new LinkedHashMap<>();
        final Disposable.Composite subscribers = Disposables.composite();

        BufferWhenMainSubscriber(CoreSubscriber<? super BUFFER> coreSubscriber, Supplier<BUFFER> supplier, Supplier<? extends Queue<BUFFER>> supplier2, Publisher<? extends OPEN> publisher, Function<? super OPEN, ? extends Publisher<? extends CLOSE>> function) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.bufferOpen = publisher;
            this.bufferClose = function;
            this.bufferSupplier = supplier;
            this.queue = supplier2.get();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2093S, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super BUFFER> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            synchronized (this) {
                LinkedHashMap<Long, BUFFER> linkedHashMap = this.buffers;
                if (linkedHashMap == null) {
                    return;
                }
                if (linkedHashMap.isEmpty()) {
                    Operators.onDiscard(t, this.ctx);
                    return;
                }
                Iterator<BUFFER> it = linkedHashMap.values().iterator();
                while (it.hasNext()) {
                    it.next().add(t);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            LinkedHashMap<Long, BUFFER> linkedHashMap;
            if (Exceptions.addThrowable(ERRORS, this, th)) {
                this.subscribers.dispose();
                synchronized (this) {
                    linkedHashMap = this.buffers;
                    this.buffers = null;
                }
                this.done = true;
                drain();
                if (linkedHashMap != null) {
                    Iterator<BUFFER> it = linkedHashMap.values().iterator();
                    while (it.hasNext()) {
                        Operators.onDiscardMultiple(it.next(), this.ctx);
                    }
                    return;
                }
                return;
            }
            Operators.onErrorDropped(th, this.ctx);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.subscribers.dispose();
            synchronized (this) {
                LinkedHashMap<Long, BUFFER> linkedHashMap = this.buffers;
                if (linkedHashMap == null) {
                    return;
                }
                Iterator<BUFFER> it = linkedHashMap.values().iterator();
                while (it.hasNext()) {
                    this.queue.offer(it.next());
                }
                this.buffers = null;
                this.done = true;
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            Operators.addCap(REQUESTED, this, j);
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            LinkedHashMap<Long, BUFFER> linkedHashMap;
            if (Operators.terminate(f2093S, this)) {
                this.cancelled = true;
                this.subscribers.dispose();
                synchronized (this) {
                    linkedHashMap = this.buffers;
                    this.buffers = null;
                }
                if (WINDOWS.getAndIncrement(this) == 0) {
                    Operators.onDiscardQueueWithClear(this.queue, this.ctx, new C5144xbccea62b());
                }
                if (linkedHashMap == null || linkedHashMap.isEmpty()) {
                    return;
                }
                Iterator<BUFFER> it = linkedHashMap.values().iterator();
                while (it.hasNext()) {
                    Operators.onDiscardMultiple(it.next(), this.ctx);
                }
            }
        }

        void drain() {
            if (WINDOWS.getAndIncrement(this) != 0) {
                return;
            }
            long j = this.emitted;
            CoreSubscriber<? super BUFFER> coreSubscriber = this.actual;
            Queue<BUFFER> queue = this.queue;
            int iAddAndGet = 1;
            do {
                long j2 = this.requested;
                while (j != j2) {
                    if (this.cancelled) {
                        Operators.onDiscardQueueWithClear(queue, this.ctx, new C5144xbccea62b());
                        return;
                    }
                    boolean z = this.done;
                    if (z && this.errors != null) {
                        Operators.onDiscardQueueWithClear(queue, this.ctx, new C5144xbccea62b());
                        coreSubscriber.onError(Exceptions.terminate(ERRORS, this));
                        return;
                    }
                    BUFFER bufferPoll = queue.poll();
                    boolean z2 = bufferPoll == null;
                    if (z && z2) {
                        coreSubscriber.onComplete();
                        return;
                    } else {
                        if (z2) {
                            break;
                        }
                        coreSubscriber.onNext(bufferPoll);
                        j++;
                    }
                }
                if (j == j2) {
                    if (this.cancelled) {
                        Operators.onDiscardQueueWithClear(queue, this.ctx, new C5144xbccea62b());
                        return;
                    }
                    if (this.done) {
                        if (this.errors != null) {
                            Operators.onDiscardQueueWithClear(queue, this.ctx, new C5144xbccea62b());
                            coreSubscriber.onError(Exceptions.terminate(ERRORS, this));
                            return;
                        } else if (queue.isEmpty()) {
                            coreSubscriber.onComplete();
                            return;
                        }
                    }
                }
                this.emitted = j;
                iAddAndGet = WINDOWS.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        void open(OPEN open) {
            try {
                Collection collection = (Collection) Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null Collection");
                Publisher publisher = (Publisher) Objects.requireNonNull(this.bufferClose.apply(open), "The bufferClose returned a null Publisher");
                long j = this.index;
                this.index = 1 + j;
                synchronized (this) {
                    LinkedHashMap<Long, BUFFER> linkedHashMap = this.buffers;
                    if (linkedHashMap == null) {
                        return;
                    }
                    linkedHashMap.put(Long.valueOf(j), (BUFFER) collection);
                    BufferWhenCloseSubscriber bufferWhenCloseSubscriber = new BufferWhenCloseSubscriber(this, j);
                    this.subscribers.add(bufferWhenCloseSubscriber);
                    Operators.toFluxOrMono(publisher).subscribe((Subscriber) bufferWhenCloseSubscriber);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                Operators.terminate(f2093S, this);
                if (Exceptions.addThrowable(ERRORS, this, th)) {
                    this.subscribers.dispose();
                    synchronized (this) {
                        LinkedHashMap<Long, BUFFER> linkedHashMap2 = this.buffers;
                        this.buffers = null;
                        this.done = true;
                        drain();
                        if (linkedHashMap2 != null) {
                            Iterator<BUFFER> it = linkedHashMap2.values().iterator();
                            while (it.hasNext()) {
                                Operators.onDiscardMultiple(it.next(), this.ctx);
                            }
                            return;
                        }
                        return;
                    }
                }
                Operators.onErrorDropped(th, this.ctx);
            }
        }

        void openComplete(BufferWhenOpenSubscriber<OPEN> bufferWhenOpenSubscriber) {
            this.subscribers.remove(bufferWhenOpenSubscriber);
            if (this.subscribers.size() == 0) {
                Operators.terminate(f2093S, this);
                this.done = true;
                drain();
            }
        }

        void close(BufferWhenCloseSubscriber<T, BUFFER> bufferWhenCloseSubscriber, long j) {
            boolean z;
            this.subscribers.remove(bufferWhenCloseSubscriber);
            if (this.subscribers.size() == 0) {
                Operators.terminate(f2093S, this);
                z = true;
            } else {
                z = false;
            }
            synchronized (this) {
                LinkedHashMap<Long, BUFFER> linkedHashMap = this.buffers;
                if (linkedHashMap == null) {
                    return;
                }
                this.queue.offer(linkedHashMap.remove(Long.valueOf(j)));
                if (z) {
                    this.done = true;
                }
                drain();
            }
        }

        void boundaryError(Disposable disposable, Throwable th) {
            LinkedHashMap<Long, BUFFER> linkedHashMap;
            Operators.terminate(f2093S, this);
            this.subscribers.remove(disposable);
            if (Exceptions.addThrowable(ERRORS, this, th)) {
                this.subscribers.dispose();
                synchronized (this) {
                    linkedHashMap = this.buffers;
                    this.buffers = null;
                }
                this.done = true;
                drain();
                if (linkedHashMap != null) {
                    Iterator<BUFFER> it = linkedHashMap.values().iterator();
                    while (it.hasNext()) {
                        Operators.onDiscardMultiple(it.next(), this.ctx);
                    }
                    return;
                }
                return;
            }
            Operators.onErrorDropped(th, this.ctx);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2094s;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.buffers.values().stream().mapToInt(new FluxBuffer$BufferOverlappingSubscriber$$ExternalSyntheticLambda0()).sum());
            }
            return attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.ERROR ? this.errors : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static final class BufferWhenOpenSubscriber<OPEN> implements Disposable, InnerConsumer<OPEN> {
        static final AtomicReferenceFieldUpdater<BufferWhenOpenSubscriber, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(BufferWhenOpenSubscriber.class, Subscription.class, "subscription");
        final BufferWhenMainSubscriber<?, OPEN, ?, ?> parent;
        volatile Subscription subscription;

        BufferWhenOpenSubscriber(BufferWhenMainSubscriber<?, OPEN, ?, ?> bufferWhenMainSubscriber) {
            this.parent = bufferWhenMainSubscriber;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(SUBSCRIPTION, this, subscription)) {
                this.subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            Operators.terminate(SUBSCRIPTION, this);
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return this.subscription == Operators.cancelledSubscription();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(OPEN open) {
            this.parent.open(open);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            SUBSCRIPTION.lazySet(this, Operators.cancelledSubscription());
            this.parent.boundaryError(this, th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            SUBSCRIPTION.lazySet(this, Operators.cancelledSubscription());
            this.parent.openComplete(this);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.MAX_VALUE;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isDisposed());
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class BufferWhenCloseSubscriber<T, BUFFER extends Collection<? super T>> implements Disposable, InnerConsumer<Object> {
        static final AtomicReferenceFieldUpdater<BufferWhenCloseSubscriber, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(BufferWhenCloseSubscriber.class, Subscription.class, "subscription");
        final long index;
        final BufferWhenMainSubscriber<T, ?, ?, BUFFER> parent;
        volatile Subscription subscription;

        BufferWhenCloseSubscriber(BufferWhenMainSubscriber<T, ?, ?, BUFFER> bufferWhenMainSubscriber, long j) {
            this.parent = bufferWhenMainSubscriber;
            this.index = j;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(SUBSCRIPTION, this, subscription)) {
                this.subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            Operators.terminate(SUBSCRIPTION, this);
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return this.subscription == Operators.cancelledSubscription();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Subscription subscription = this.subscription;
            if (subscription != Operators.cancelledSubscription()) {
                SUBSCRIPTION.lazySet(this, Operators.cancelledSubscription());
                subscription.cancel();
                this.parent.close(this, this.index);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.subscription != Operators.cancelledSubscription()) {
                SUBSCRIPTION.lazySet(this, Operators.cancelledSubscription());
                this.parent.boundaryError(this, th);
            } else {
                Operators.onErrorDropped(th, this.parent.ctx);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.subscription != Operators.cancelledSubscription()) {
                SUBSCRIPTION.lazySet(this, Operators.cancelledSubscription());
                this.parent.close(this, this.index);
            }
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.MAX_VALUE;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isDisposed());
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }
}
