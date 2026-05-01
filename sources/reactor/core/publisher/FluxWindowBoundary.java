package reactor.core.publisher;

import com.azure.core.implementation.logging.LoggingKeys;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxWindowBoundary<T, U> extends InternalFluxOperator<T, Flux<T>> {
    final Publisher<U> other;
    final Supplier<? extends Queue<T>> processorQueueSupplier;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxWindowBoundary(Flux<? extends T> flux, Publisher<U> publisher, Supplier<? extends Queue<T>> supplier) {
        super(flux);
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "other"));
        this.processorQueueSupplier = (Supplier) Objects.requireNonNull(supplier, "processorQueueSupplier");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    @Nullable
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Flux<T>> coreSubscriber) {
        Supplier<? extends Queue<T>> supplier = this.processorQueueSupplier;
        WindowBoundaryMain windowBoundaryMain = new WindowBoundaryMain(coreSubscriber, supplier, supplier.get());
        coreSubscriber.onSubscribe(windowBoundaryMain);
        if (!windowBoundaryMain.emit(windowBoundaryMain.window)) {
            return null;
        }
        this.other.subscribe(windowBoundaryMain.boundary);
        return windowBoundaryMain;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class WindowBoundaryMain<T, U> implements InnerOperator<T, Flux<T>>, Disposable {
        final CoreSubscriber<? super Flux<T>> actual;
        final WindowBoundaryOther<U> boundary;
        volatile int cancelled;
        boolean done;
        volatile Throwable error;
        final Supplier<? extends Queue<T>> processorQueueSupplier;
        final Queue<Object> queue;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2233s;
        Sinks.Many<T> window;
        volatile int windowCount;
        volatile int wip;

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<WindowBoundaryMain, Subscription> f2232S = AtomicReferenceFieldUpdater.newUpdater(WindowBoundaryMain.class, Subscription.class, "s");
        static final AtomicLongFieldUpdater<WindowBoundaryMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(WindowBoundaryMain.class, "requested");
        static final AtomicReferenceFieldUpdater<WindowBoundaryMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(WindowBoundaryMain.class, Throwable.class, "error");
        static final AtomicIntegerFieldUpdater<WindowBoundaryMain> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(WindowBoundaryMain.class, LoggingKeys.CANCELLED_ERROR_TYPE);
        static final AtomicIntegerFieldUpdater<WindowBoundaryMain> WINDOW_COUNT = AtomicIntegerFieldUpdater.newUpdater(WindowBoundaryMain.class, "windowCount");
        static final AtomicIntegerFieldUpdater<WindowBoundaryMain> WIP = AtomicIntegerFieldUpdater.newUpdater(WindowBoundaryMain.class, "wip");
        static final Object BOUNDARY_MARKER = new Object();
        static final Object DONE = new Object();

        WindowBoundaryMain(CoreSubscriber<? super Flux<T>> coreSubscriber, Supplier<? extends Queue<T>> supplier, Queue<T> queue) {
            this.actual = coreSubscriber;
            this.processorQueueSupplier = supplier;
            this.window = Sinks.unsafe().many().unicast().onBackpressureBuffer(queue, this);
            WINDOW_COUNT.lazySet(this, 2);
            this.boundary = new WindowBoundaryOther<>(this);
            this.queue = (Queue) Queues.unboundedMultiproducer().get();
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2233s;
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled == 1);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            return attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(this.queue.size()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) new Scannable[]{this.boundary, Scannable.from(this.window)});
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2232S, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            synchronized (this) {
                this.queue.offer(t);
            }
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.boundary.cancel();
            if (Exceptions.addThrowable(ERROR, this, th)) {
                drain();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.boundary.cancel();
            synchronized (this) {
                this.queue.offer(DONE);
            }
            drain();
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            if (WINDOW_COUNT.decrementAndGet(this) == 0) {
                cancelMain();
                this.boundary.cancel();
            }
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return this.cancelled == 1 || this.done;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
            }
        }

        void cancelMain() {
            Operators.terminate(f2232S, this);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                dispose();
            }
        }

        void boundaryNext() {
            synchronized (this) {
                this.queue.offer(BOUNDARY_MARKER);
            }
            if (this.cancelled != 0) {
                this.boundary.cancel();
            }
            drain();
        }

        void boundaryError(Throwable th) {
            cancelMain();
            if (Exceptions.addThrowable(ERROR, this, th)) {
                drain();
            } else {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        void boundaryComplete() {
            cancelMain();
            synchronized (this) {
                this.queue.offer(DONE);
            }
            drain();
        }

        /* JADX WARN: Multi-variable type inference failed */
        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber<? super Flux<T>> coreSubscriber = this.actual;
            Queue<Object> queue = this.queue;
            Sinks.Many<T> manyOnBackpressureBuffer = this.window;
            int iAddAndGet = 1;
            while (this.error == null) {
                Object objPoll = queue.poll();
                if (objPoll != null) {
                    if (objPoll == DONE) {
                        queue.clear();
                        manyOnBackpressureBuffer.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                        coreSubscriber.onComplete();
                        return;
                    }
                    Object obj = BOUNDARY_MARKER;
                    if (objPoll != obj) {
                        manyOnBackpressureBuffer.emitNext(objPoll, Sinks.EmitFailureHandler.FAIL_FAST);
                    }
                    if (objPoll == obj) {
                        manyOnBackpressureBuffer.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                        if (this.cancelled != 0) {
                            continue;
                        } else if (this.requested != 0) {
                            Queue<T> queue2 = this.processorQueueSupplier.get();
                            WINDOW_COUNT.getAndIncrement(this);
                            manyOnBackpressureBuffer = Sinks.unsafe().many().unicast().onBackpressureBuffer(queue2, this);
                            this.window = manyOnBackpressureBuffer;
                            coreSubscriber.onNext(manyOnBackpressureBuffer.asFlux());
                            if (this.requested != Long.MAX_VALUE) {
                                REQUESTED.decrementAndGet(this);
                            }
                        } else {
                            queue.clear();
                            cancelMain();
                            this.boundary.cancel();
                            coreSubscriber.onError(Exceptions.failWithOverflow("Could not create new window due to lack of requests"));
                            return;
                        }
                    } else {
                        continue;
                    }
                } else {
                    iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
            queue.clear();
            Throwable thTerminate = Exceptions.terminate(ERROR, this);
            if (thTerminate != Exceptions.TERMINATED) {
                manyOnBackpressureBuffer.emitError(Exceptions.wrapSource(thTerminate), Sinks.EmitFailureHandler.FAIL_FAST);
                coreSubscriber.onError(thTerminate);
            }
        }

        boolean emit(Sinks.Many<T> many) {
            long j = this.requested;
            if (j != 0) {
                this.actual.onNext(many.asFlux());
                if (j == Long.MAX_VALUE) {
                    return true;
                }
                REQUESTED.decrementAndGet(this);
                return true;
            }
            cancel();
            this.actual.onError(Exceptions.failWithOverflow("Could not emit buffer due to lack of requests"));
            return false;
        }
    }

    static final class WindowBoundaryOther<U> extends Operators.DeferredSubscription implements InnerConsumer<U> {
        final WindowBoundaryMain<?, U> main;

        WindowBoundaryOther(WindowBoundaryMain<?, U> windowBoundaryMain) {
            this.main = windowBoundaryMain;
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
            this.main.boundaryNext();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.main.boundaryError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.main.boundaryComplete();
        }
    }
}
