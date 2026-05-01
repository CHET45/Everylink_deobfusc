package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import com.azure.core.implementation.logging.LoggingKeys;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxBufferPredicate;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxWindowPredicate<T> extends InternalFluxOperator<T, Flux<T>> implements Fuseable {
    final Supplier<? extends Queue<T>> groupQueueSupplier;
    final Supplier<? extends Queue<Flux<T>>> mainQueueSupplier;
    final FluxBufferPredicate.Mode mode;
    final Predicate<? super T> predicate;
    final int prefetch;

    FluxWindowPredicate(Flux<? extends T> flux, Supplier<? extends Queue<Flux<T>>> supplier, Supplier<? extends Queue<T>> supplier2, int i, Predicate<? super T> predicate, FluxBufferPredicate.Mode mode) {
        super(Flux.from(flux));
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.predicate = (Predicate) Objects.requireNonNull(predicate, "predicate");
        this.mainQueueSupplier = (Supplier) Objects.requireNonNull(supplier, "mainQueueSupplier");
        this.groupQueueSupplier = (Supplier) Objects.requireNonNull(supplier2, "groupQueueSupplier");
        this.mode = mode;
        this.prefetch = i;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Flux<T>> coreSubscriber) {
        return new WindowPredicateMain(coreSubscriber, this.mainQueueSupplier.get(), this.groupQueueSupplier, this.prefetch, this.predicate, this.mode);
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class WindowPredicateMain<T> implements Fuseable.QueueSubscription<Flux<T>>, InnerOperator<T, Flux<T>> {
        final CoreSubscriber<? super Flux<T>> actual;
        volatile int cancelled;
        final Context ctx;
        volatile boolean done;
        volatile Throwable error;
        final Supplier<? extends Queue<T>> groupQueueSupplier;
        final FluxBufferPredicate.Mode mode;
        volatile boolean outputFused;
        final Predicate<? super T> predicate;
        final int prefetch;
        final Queue<Flux<T>> queue;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2234s;
        WindowFlux<T> window;
        volatile int windowCount;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<WindowPredicateMain> WIP = AtomicIntegerFieldUpdater.newUpdater(WindowPredicateMain.class, "wip");
        static final AtomicLongFieldUpdater<WindowPredicateMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(WindowPredicateMain.class, "requested");
        static final AtomicReferenceFieldUpdater<WindowPredicateMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(WindowPredicateMain.class, Throwable.class, "error");
        static final AtomicIntegerFieldUpdater<WindowPredicateMain> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(WindowPredicateMain.class, LoggingKeys.CANCELLED_ERROR_TYPE);
        static final AtomicIntegerFieldUpdater<WindowPredicateMain> WINDOW_COUNT = AtomicIntegerFieldUpdater.newUpdater(WindowPredicateMain.class, "windowCount");

        WindowPredicateMain(CoreSubscriber<? super Flux<T>> coreSubscriber, Queue<Flux<T>> queue, Supplier<? extends Queue<T>> supplier, int i, Predicate<? super T> predicate, FluxBufferPredicate.Mode mode) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.queue = queue;
            this.groupQueueSupplier = supplier;
            this.prefetch = i;
            this.predicate = predicate;
            this.mode = mode;
            WINDOW_COUNT.lazySet(this, 2);
            initializeWindow();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2234s, subscription)) {
                this.f2234s = subscription;
                this.actual.onSubscribe(this);
                if (this.cancelled == 0) {
                    subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
                }
            }
        }

        void initializeWindow() {
            this.window = new WindowFlux<>(this.groupQueueSupplier.get(), this);
        }

        @Nullable
        WindowFlux<T> newWindowDeferred() {
            if (this.cancelled != 0) {
                return null;
            }
            WINDOW_COUNT.getAndIncrement(this);
            WindowFlux<T> windowFlux = new WindowFlux<>(this.groupQueueSupplier.get(), this);
            this.window = windowFlux;
            return windowFlux;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            WindowFlux<T> windowFlux = this.window;
            try {
                boolean zTest = this.predicate.test(t);
                if (handleDeferredWindow(windowFlux, t)) {
                    drain();
                    if (this.mode == FluxBufferPredicate.Mode.UNTIL && zTest) {
                        if (windowFlux.cancelled) {
                            Operators.onDiscard(t, this.ctx);
                            this.f2234s.request(1L);
                        } else {
                            windowFlux.onNext(t);
                        }
                        windowFlux.onComplete();
                        newWindowDeferred();
                        this.f2234s.request(1L);
                        return;
                    }
                    if (this.mode == FluxBufferPredicate.Mode.UNTIL_CUT_BEFORE && zTest) {
                        windowFlux.onComplete();
                        WindowFlux<T> windowFluxNewWindowDeferred = newWindowDeferred();
                        if (windowFluxNewWindowDeferred != null) {
                            windowFluxNewWindowDeferred.onNext(t);
                            handleDeferredWindow(windowFluxNewWindowDeferred, t);
                            drain();
                            return;
                        }
                        return;
                    }
                    if (this.mode == FluxBufferPredicate.Mode.WHILE && !zTest) {
                        windowFlux.onComplete();
                        newWindowDeferred();
                        Operators.onDiscard(t, this.ctx);
                        this.f2234s.request(1L);
                        return;
                    }
                    if (windowFlux.cancelled) {
                        Operators.onDiscard(t, this.ctx);
                        this.f2234s.request(1L);
                    } else {
                        windowFlux.onNext(t);
                    }
                }
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2234s, th, t, this.ctx));
            }
        }

        boolean handleDeferredWindow(@Nullable WindowFlux<T> windowFlux, T t) {
            if (windowFlux == null || !windowFlux.deferred) {
                return true;
            }
            windowFlux.deferred = false;
            if (this.queue.offer(windowFlux)) {
                return true;
            }
            onError(Operators.onOperatorError(this, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), t, this.ctx));
            return false;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (Exceptions.addThrowable(ERROR, this, th)) {
                this.done = true;
                cleanup();
                drain();
                return;
            }
            Operators.onErrorDropped(th, this.ctx);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            cleanup();
            WindowFlux<T> windowFlux = this.window;
            if (windowFlux != null) {
                windowFlux.onComplete();
            }
            this.window = null;
            this.done = true;
            WINDOW_COUNT.decrementAndGet(this);
            drain();
        }

        void cleanup() {
            Predicate<? super T> predicate = this.predicate;
            if (predicate instanceof Disposable) {
                ((Disposable) predicate).dispose();
            }
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2234s;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled == 1);
            }
            return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.prefetch) : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(this.queue.size()) : attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            WindowFlux<T> windowFlux = this.window;
            return windowFlux == null ? Stream.empty() : Stream.of(windowFlux);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
        }

        void signalAsyncError() {
            Throwable thTerminate = Exceptions.terminate(ERROR, this);
            this.windowCount = 0;
            WindowFlux<T> windowFlux = this.window;
            if (windowFlux != null) {
                windowFlux.onError(Exceptions.wrapSource(thTerminate));
            }
            this.actual.onError(thTerminate);
            this.window = null;
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
            AtomicIntegerFieldUpdater<WindowPredicateMain> atomicIntegerFieldUpdater = CANCELLED;
            if (atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                if (WINDOW_COUNT.decrementAndGet(this) == 0) {
                    this.f2234s.cancel();
                    cleanup();
                    return;
                }
                if (this.outputFused || WIP.getAndIncrement(this) != 0) {
                    return;
                }
                WindowFlux<T> windowFlux = this.window;
                while (true) {
                    Flux<T> fluxPoll = this.queue.poll();
                    if (fluxPoll == null) {
                        break;
                    } else {
                        ((WindowFlux) fluxPoll).cancel();
                    }
                }
                if (windowFlux != null && windowFlux.deferred) {
                    windowFlux.cancel();
                }
                if (WIP.decrementAndGet(this) == 0) {
                    if (!this.done && WINDOW_COUNT.get(this) == 0) {
                        this.f2234s.cancel();
                        cleanup();
                        return;
                    } else {
                        CANCELLED.set(this, 2);
                        return;
                    }
                }
                CANCELLED.set(this, 2);
                drainLoop();
                return;
            }
            if (atomicIntegerFieldUpdater.get(this) == 2 && WINDOW_COUNT.get(this) == 0) {
                this.f2234s.cancel();
                cleanup();
            }
        }

        void groupTerminated() {
            if (this.windowCount == 0) {
                return;
            }
            this.window = null;
            if (WINDOW_COUNT.decrementAndGet(this) == 0) {
                this.f2234s.cancel();
                cleanup();
            }
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            if (this.outputFused) {
                drainFused();
            } else {
                drainLoop();
            }
        }

        void drainFused() {
            CoreSubscriber<? super Flux<T>> coreSubscriber = this.actual;
            Queue<Flux<T>> queue = this.queue;
            int iAddAndGet = 1;
            while (this.cancelled == 0) {
                boolean z = this.done;
                coreSubscriber.onNext(null);
                if (z) {
                    if (this.error != null) {
                        signalAsyncError();
                        return;
                    } else {
                        coreSubscriber.onComplete();
                        return;
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
            queue.clear();
        }

        void drainLoop() {
            CoreSubscriber<? super Flux<T>> coreSubscriber = this.actual;
            Queue<Flux<T>> queue = this.queue;
            int iAddAndGet = 1;
            do {
                long j = this.requested;
                long j2 = 0;
                while (j2 != j) {
                    boolean z = this.done;
                    Flux<T> fluxPoll = queue.poll();
                    boolean z2 = fluxPoll == null;
                    if (checkTerminated(z, z2, coreSubscriber, queue)) {
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    coreSubscriber.onNext(fluxPoll);
                    j2++;
                }
                if (j2 == j && checkTerminated(this.done, queue.isEmpty(), coreSubscriber, queue)) {
                    return;
                }
                if (j2 != 0) {
                    this.f2234s.request(j2);
                    if (j != Long.MAX_VALUE) {
                        REQUESTED.addAndGet(this, -j2);
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, Queue<Flux<T>> queue) {
            if (this.cancelled != 0) {
                queue.clear();
                return true;
            }
            if (!z) {
                return false;
            }
            Throwable th = this.error;
            if (th != null && th != Exceptions.TERMINATED) {
                this.queue.clear();
                signalAsyncError();
                return true;
            }
            if (!z2) {
                return false;
            }
            subscriber.onComplete();
            return true;
        }

        @Override // java.util.Queue
        @Nullable
        public Flux<T> poll() {
            return this.queue.poll();
        }

        @Override // java.util.Collection
        public int size() {
            return this.queue.size();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.queue.clear();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }
    }

    static final class WindowFlux<T> extends Flux<T> implements Fuseable, Fuseable.QueueSubscription<T>, InnerOperator<T, T> {
        volatile CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        volatile Context ctx = Context.empty();
        boolean deferred = true;
        volatile boolean done;
        volatile boolean enableOperatorFusion;
        Throwable error;
        volatile int once;
        volatile WindowPredicateMain<T> parent;
        int produced;
        final Queue<T> queue;
        volatile long requested;
        volatile int wip;
        static final AtomicReferenceFieldUpdater<WindowFlux, WindowPredicateMain> PARENT = AtomicReferenceFieldUpdater.newUpdater(WindowFlux.class, WindowPredicateMain.class, "parent");
        static final AtomicReferenceFieldUpdater<WindowFlux, CoreSubscriber> ACTUAL = AtomicReferenceFieldUpdater.newUpdater(WindowFlux.class, CoreSubscriber.class, "actual");
        static final AtomicIntegerFieldUpdater<WindowFlux> ONCE = AtomicIntegerFieldUpdater.newUpdater(WindowFlux.class, "once");
        static final AtomicIntegerFieldUpdater<WindowFlux> WIP = AtomicIntegerFieldUpdater.newUpdater(WindowFlux.class, "wip");
        static final AtomicLongFieldUpdater<WindowFlux> REQUESTED = AtomicLongFieldUpdater.newUpdater(WindowFlux.class, "requested");

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
        }

        WindowFlux(Queue<T> queue, WindowPredicateMain<T> windowPredicateMain) {
            this.queue = queue;
            this.parent = windowPredicateMain;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        void propagateTerminate() {
            WindowPredicateMain<T> windowPredicateMain = this.parent;
            if (windowPredicateMain == null || !C0162xc40028dd.m5m(PARENT, this, windowPredicateMain, null)) {
                return;
            }
            windowPredicateMain.groupTerminated();
        }

        void drainRegular(Subscriber<? super T> subscriber) {
            Queue<T> queue = this.queue;
            int iAddAndGet = 1;
            do {
                long j = this.requested;
                long j2 = 0;
                while (j != j2) {
                    boolean z = this.done;
                    T tPoll = queue.poll();
                    boolean z2 = tPoll == null;
                    if (checkTerminated(z, z2, subscriber, queue)) {
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    subscriber.onNext(tPoll);
                    j2++;
                }
                if (j == j2 && checkTerminated(this.done, queue.isEmpty(), subscriber, queue)) {
                    return;
                }
                if (j2 != 0) {
                    WindowPredicateMain<T> windowPredicateMain = this.parent;
                    if (windowPredicateMain != null) {
                        windowPredicateMain.f2234s.request(j2);
                    }
                    if (j != Long.MAX_VALUE) {
                        REQUESTED.addAndGet(this, -j2);
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        void drainFused(Subscriber<? super T> subscriber) {
            Queue<T> queue = this.queue;
            int iAddAndGet = 1;
            while (!this.cancelled) {
                boolean z = this.done;
                subscriber.onNext(null);
                if (z) {
                    this.ctx = Context.empty();
                    this.actual = null;
                    Throwable th = this.error;
                    if (th != null) {
                        subscriber.onError(th);
                        return;
                    } else {
                        subscriber.onComplete();
                        return;
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
            Operators.onDiscardQueueWithClear(queue, this.ctx, null);
            this.ctx = Context.empty();
            this.actual = null;
        }

        void drain() {
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            if (coreSubscriber == null || WIP.getAndIncrement(this) != 0) {
                return;
            }
            if (this.enableOperatorFusion) {
                drainFused(coreSubscriber);
            } else {
                drainRegular(coreSubscriber);
            }
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, Queue<?> queue) {
            if (this.cancelled) {
                Operators.onDiscardQueueWithClear(queue, this.ctx, null);
                this.ctx = Context.empty();
                this.actual = null;
                return true;
            }
            if (!z || !z2) {
                return false;
            }
            Throwable th = this.error;
            this.ctx = Context.empty();
            this.actual = null;
            if (th != null) {
                subscriber.onError(th);
            } else {
                subscriber.onComplete();
            }
            return true;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            if (!this.queue.offer(t)) {
                onError(Operators.onOperatorError(this, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), t, this.ctx));
            } else if (!this.enableOperatorFusion) {
                drain();
            } else if (coreSubscriber != null) {
                coreSubscriber.onNext(null);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            propagateTerminate();
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            propagateTerminate();
            drain();
        }

        @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
        public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
            if (this.once == 0 && ONCE.compareAndSet(this, 0, 1)) {
                coreSubscriber.onSubscribe(this);
                ACTUAL.lazySet(this, coreSubscriber);
                this.ctx = coreSubscriber.currentContext();
                drain();
                return;
            }
            coreSubscriber.onError(new IllegalStateException("This processor allows only a single Subscriber"));
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
            WindowPredicateMain<T> windowPredicateMain = this.parent;
            if (windowPredicateMain != null && C0162xc40028dd.m5m(PARENT, this, windowPredicateMain, null)) {
                if (WindowPredicateMain.WINDOW_COUNT.decrementAndGet(windowPredicateMain) == 0) {
                    windowPredicateMain.cancel();
                } else {
                    windowPredicateMain.f2234s.request(1L);
                }
            }
            if (this.enableOperatorFusion || WIP.getAndIncrement(this) != 0) {
                return;
            }
            Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            T tPoll = this.queue.poll();
            if (tPoll != null) {
                this.produced++;
            } else {
                int i = this.produced;
                if (i != 0) {
                    this.produced = 0;
                    WindowPredicateMain<T> windowPredicateMain = this.parent;
                    if (windowPredicateMain != null) {
                        windowPredicateMain.f2234s.request(i);
                    }
                }
            }
            return tPoll;
        }

        @Override // java.util.Collection
        public int size() {
            return this.queue.size();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.enableOperatorFusion = true;
            return 2;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr != Scannable.Attr.BUFFERED) {
                return attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            Queue<T> queue = this.queue;
            return Integer.valueOf(queue == null ? 0 : queue.size());
        }
    }
}
