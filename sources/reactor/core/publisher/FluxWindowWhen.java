package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
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
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;

/* JADX INFO: loaded from: classes5.dex */
final class FluxWindowWhen<T, U, V> extends InternalFluxOperator<T, Flux<T>> {
    final Function<? super U, ? extends Publisher<V>> end;
    final Supplier<? extends Queue<T>> processorQueueSupplier;
    final Publisher<U> start;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxWindowWhen(Flux<? extends T> flux, Publisher<U> publisher, Function<? super U, ? extends Publisher<V>> function, Supplier<? extends Queue<T>> supplier) {
        super(flux);
        this.start = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "start"));
        this.end = (Function) Objects.requireNonNull(function, "end");
        this.processorQueueSupplier = (Supplier) Objects.requireNonNull(supplier, "processorQueueSupplier");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    @Nullable
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Flux<T>> coreSubscriber) {
        WindowWhenMainSubscriber windowWhenMainSubscriber = new WindowWhenMainSubscriber(coreSubscriber, this.start, this.end, this.processorQueueSupplier);
        coreSubscriber.onSubscribe(windowWhenMainSubscriber);
        if (windowWhenMainSubscriber.cancelled) {
            return null;
        }
        WindowWhenOpenSubscriber windowWhenOpenSubscriber = new WindowWhenOpenSubscriber(windowWhenMainSubscriber);
        if (!C0162xc40028dd.m5m(WindowWhenMainSubscriber.BOUNDARY, windowWhenMainSubscriber, null, windowWhenOpenSubscriber)) {
            return null;
        }
        WindowWhenMainSubscriber.OPEN_WINDOW_COUNT.incrementAndGet(windowWhenMainSubscriber);
        this.start.subscribe(windowWhenOpenSubscriber);
        return windowWhenMainSubscriber;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class WindowWhenMainSubscriber<T, U, V> extends QueueDrainSubscriber<T, Object, Flux<T>> {
        static final AtomicReferenceFieldUpdater<WindowWhenMainSubscriber, Disposable> BOUNDARY = AtomicReferenceFieldUpdater.newUpdater(WindowWhenMainSubscriber.class, Disposable.class, "boundary");
        static final AtomicLongFieldUpdater<WindowWhenMainSubscriber> OPEN_WINDOW_COUNT = AtomicLongFieldUpdater.newUpdater(WindowWhenMainSubscriber.class, "openWindowCount");
        volatile Disposable boundary;
        final Function<? super U, ? extends Publisher<V>> close;
        final Publisher<U> open;
        volatile long openWindowCount;
        final Supplier<? extends Queue<T>> processorQueueSupplier;
        final Disposable.Composite resources;

        /* JADX INFO: renamed from: s */
        Subscription f2239s;
        final List<Sinks.Many<T>> windows;

        WindowWhenMainSubscriber(CoreSubscriber<? super Flux<T>> coreSubscriber, Publisher<U> publisher, Function<? super U, ? extends Publisher<V>> function, Supplier<? extends Queue<T>> supplier) {
            super(coreSubscriber, (Queue) Queues.unboundedMultiproducer().get());
            this.open = publisher;
            this.close = function;
            this.processorQueueSupplier = supplier;
            this.resources = Disposables.composite();
            this.windows = new ArrayList();
            OPEN_WINDOW_COUNT.lazySet(this, 1L);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2239s, subscription)) {
                this.f2239s = subscription;
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            if (fastEnter()) {
                Iterator<Sinks.Many<T>> it = this.windows.iterator();
                while (it.hasNext()) {
                    it.next().emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
                }
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                this.queue.offer(t);
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.error = th;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            if (OPEN_WINDOW_COUNT.decrementAndGet(this) == 0) {
                this.resources.dispose();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            if (OPEN_WINDOW_COUNT.decrementAndGet(this) == 0) {
                this.resources.dispose();
            }
        }

        void error(Throwable th) {
            this.f2239s.cancel();
            this.resources.dispose();
            OperatorDisposables.dispose(BOUNDARY, this);
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            requested(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        void dispose() {
            this.resources.dispose();
            OperatorDisposables.dispose(BOUNDARY, this);
        }

        void drainLoop() {
            Queue<U> queue = this.queue;
            CoreSubscriber<? super V> coreSubscriber = this.actual;
            List<Sinks.Many<T>> list = this.windows;
            int iLeave = 1;
            while (true) {
                boolean z = this.done;
                U uPoll = queue.poll();
                boolean z2 = uPoll == null;
                if (z && z2) {
                    dispose();
                    Throwable th = this.error;
                    if (th != null) {
                        Iterator<Sinks.Many<T>> it = list.iterator();
                        while (it.hasNext()) {
                            it.next().emitError(Exceptions.wrapSource(th), Sinks.EmitFailureHandler.FAIL_FAST);
                        }
                        this.actual.onError(th);
                    } else {
                        Iterator<Sinks.Many<T>> it2 = list.iterator();
                        while (it2.hasNext()) {
                            it2.next().emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                        }
                        this.actual.onComplete();
                    }
                    list.clear();
                    return;
                }
                if (!z2) {
                    if (uPoll instanceof WindowOperation) {
                        WindowOperation windowOperation = (WindowOperation) uPoll;
                        if (windowOperation.f2237w != null) {
                            if (list.remove(windowOperation.f2237w)) {
                                windowOperation.f2237w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                                if (OPEN_WINDOW_COUNT.decrementAndGet(this) == 0) {
                                    dispose();
                                    return;
                                }
                            } else {
                                continue;
                            }
                        } else if (!this.cancelled) {
                            Sinks.Many<T> manyOnBackpressureBuffer = Sinks.unsafe().many().unicast().onBackpressureBuffer(this.processorQueueSupplier.get());
                            long jRequested = requested();
                            if (jRequested != 0) {
                                list.add(manyOnBackpressureBuffer);
                                coreSubscriber.onNext(manyOnBackpressureBuffer.asFlux());
                                if (jRequested != Long.MAX_VALUE) {
                                    produced(1L);
                                }
                                try {
                                    Publisher publisher = (Publisher) Objects.requireNonNull(this.close.apply(windowOperation.open), "The publisher supplied is null");
                                    WindowWhenCloseSubscriber windowWhenCloseSubscriber = new WindowWhenCloseSubscriber(this, manyOnBackpressureBuffer);
                                    if (this.resources.add(windowWhenCloseSubscriber)) {
                                        OPEN_WINDOW_COUNT.getAndIncrement(this);
                                        Operators.toFluxOrMono(publisher).subscribe(windowWhenCloseSubscriber);
                                    }
                                } catch (Throwable th2) {
                                    this.cancelled = true;
                                    coreSubscriber.onError(th2);
                                }
                            } else {
                                this.cancelled = true;
                                coreSubscriber.onError(Exceptions.failWithOverflow("Could not deliver new window due to lack of requests"));
                            }
                        }
                    } else {
                        Iterator<Sinks.Many<T>> it3 = list.iterator();
                        while (it3.hasNext()) {
                            it3.next().emitNext(uPoll, Sinks.EmitFailureHandler.FAIL_FAST);
                        }
                    }
                } else {
                    iLeave = leave(-iLeave);
                    if (iLeave == 0) {
                        return;
                    }
                }
            }
        }

        void open(U u) {
            this.queue.offer((U) new WindowOperation(null, u));
            if (enter()) {
                drainLoop();
            }
        }

        void close(WindowWhenCloseSubscriber<T, V> windowWhenCloseSubscriber) {
            this.resources.remove(windowWhenCloseSubscriber);
            this.queue.offer((U) new WindowOperation(windowWhenCloseSubscriber.f2238w, null));
            if (enter()) {
                drainLoop();
            }
        }

        @Override // reactor.core.publisher.QueueDrainSubscriber, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static final class WindowOperation<T, U> {
        final U open;

        /* JADX INFO: renamed from: w */
        final Sinks.Many<T> f2237w;

        WindowOperation(@Nullable Sinks.Many<T> many, @Nullable U u) {
            this.f2237w = many;
            this.open = u;
        }
    }

    static final class WindowWhenOpenSubscriber<T, U> implements Disposable, Subscriber<U> {
        static final AtomicReferenceFieldUpdater<WindowWhenOpenSubscriber, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(WindowWhenOpenSubscriber.class, Subscription.class, "subscription");
        boolean done;
        final WindowWhenMainSubscriber<T, U, ?> parent;
        volatile Subscription subscription;

        WindowWhenOpenSubscriber(WindowWhenMainSubscriber<T, U, ?> windowWhenMainSubscriber) {
            this.parent = windowWhenMainSubscriber;
        }

        @Override // org.reactivestreams.Subscriber
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
        public void onNext(U u) {
            if (this.done) {
                return;
            }
            this.parent.open(u);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.parent.actual.currentContext());
            } else {
                this.done = true;
                this.parent.error(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.onComplete();
        }
    }

    static final class WindowWhenCloseSubscriber<T, V> implements Disposable, Subscriber<V> {
        static final AtomicReferenceFieldUpdater<WindowWhenCloseSubscriber, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(WindowWhenCloseSubscriber.class, Subscription.class, "subscription");
        boolean done;
        final WindowWhenMainSubscriber<T, ?, V> parent;
        volatile Subscription subscription;

        /* JADX INFO: renamed from: w */
        final Sinks.Many<T> f2238w;

        WindowWhenCloseSubscriber(WindowWhenMainSubscriber<T, ?, V> windowWhenMainSubscriber, Sinks.Many<T> many) {
            this.parent = windowWhenMainSubscriber;
            this.f2238w = many;
        }

        @Override // org.reactivestreams.Subscriber
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
        public void onNext(V v) {
            if (this.done) {
                return;
            }
            this.done = true;
            dispose();
            this.parent.close(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.parent.actual.currentContext());
            } else {
                this.done = true;
                this.parent.error(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.close(this);
        }
    }
}
