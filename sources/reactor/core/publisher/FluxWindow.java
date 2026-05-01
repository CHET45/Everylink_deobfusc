package reactor.core.publisher;

import com.azure.core.implementation.logging.LoggingKeys;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.Scannable$Attr$$ExternalSyntheticLambda0;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxWindow<T> extends InternalFluxOperator<T, Flux<T>> {
    final Supplier<? extends Queue<Sinks.Many<T>>> overflowQueueSupplier;
    final Supplier<? extends Queue<T>> processorQueueSupplier;
    final int size;
    final int skip;

    FluxWindow(Flux<? extends T> flux, int i, Supplier<? extends Queue<T>> supplier) {
        super(flux);
        if (i <= 0) {
            throw new IllegalArgumentException("size > 0 required but it was " + i);
        }
        this.size = i;
        this.skip = i;
        this.processorQueueSupplier = (Supplier) Objects.requireNonNull(supplier, "processorQueueSupplier");
        this.overflowQueueSupplier = null;
    }

    FluxWindow(Flux<? extends T> flux, int i, int i2, Supplier<? extends Queue<T>> supplier, Supplier<? extends Queue<Sinks.Many<T>>> supplier2) {
        super(flux);
        if (i <= 0) {
            throw new IllegalArgumentException("size > 0 required but it was " + i);
        }
        if (i2 <= 0) {
            throw new IllegalArgumentException("skip > 0 required but it was " + i2);
        }
        this.size = i;
        this.skip = i2;
        this.processorQueueSupplier = (Supplier) Objects.requireNonNull(supplier, "processorQueueSupplier");
        this.overflowQueueSupplier = (Supplier) Objects.requireNonNull(supplier2, "overflowQueueSupplier");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Flux<T>> coreSubscriber) {
        int i = this.skip;
        int i2 = this.size;
        if (i == i2) {
            return new WindowExactSubscriber(coreSubscriber, this.size, this.processorQueueSupplier);
        }
        if (i > i2) {
            return new WindowSkipSubscriber(coreSubscriber, this.size, this.skip, this.processorQueueSupplier);
        }
        return new WindowOverlapSubscriber(coreSubscriber, this.size, this.skip, this.processorQueueSupplier, this.overflowQueueSupplier.get());
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class WindowExactSubscriber<T> implements Disposable, InnerOperator<T, Flux<T>> {
        static final AtomicIntegerFieldUpdater<WindowExactSubscriber> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(WindowExactSubscriber.class, LoggingKeys.CANCELLED_ERROR_TYPE);
        static final AtomicIntegerFieldUpdater<WindowExactSubscriber> WINDOW_COUNT = AtomicIntegerFieldUpdater.newUpdater(WindowExactSubscriber.class, "windowCount");
        final CoreSubscriber<? super Flux<T>> actual;
        volatile int cancelled;
        boolean done;
        int index;
        final Supplier<? extends Queue<T>> processorQueueSupplier;

        /* JADX INFO: renamed from: s */
        Subscription f2229s;
        final int size;
        Sinks.Many<T> window;
        volatile int windowCount;

        WindowExactSubscriber(CoreSubscriber<? super Flux<T>> coreSubscriber, int i, Supplier<? extends Queue<T>> supplier) {
            this.actual = coreSubscriber;
            this.size = i;
            this.processorQueueSupplier = supplier;
            WINDOW_COUNT.lazySet(this, 1);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2229s, subscription)) {
                this.f2229s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            int i = this.index;
            Sinks.Many<T> manyOnBackpressureBuffer = this.window;
            if (this.cancelled == 0 && i == 0) {
                WINDOW_COUNT.getAndIncrement(this);
                manyOnBackpressureBuffer = Sinks.unsafe().many().unicast().onBackpressureBuffer(this.processorQueueSupplier.get(), this);
                this.window = manyOnBackpressureBuffer;
                this.actual.onNext(manyOnBackpressureBuffer.asFlux());
            }
            int i2 = i + 1;
            manyOnBackpressureBuffer.emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
            if (i2 == this.size) {
                this.index = 0;
                this.window = null;
                manyOnBackpressureBuffer.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                return;
            }
            this.index = i2;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            Sinks.Many<T> many = this.window;
            if (many != null) {
                this.window = null;
                many.emitError(Exceptions.wrapSource(th), Sinks.EmitFailureHandler.FAIL_FAST);
            }
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            Sinks.Many<T> many = this.window;
            if (many != null) {
                this.window = null;
                many.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
            }
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                this.f2229s.request(Operators.multiplyCap(this.size, j));
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                dispose();
            }
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            if (WINDOW_COUNT.decrementAndGet(this) == 0) {
                this.f2229s.cancel();
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return this.cancelled == 1 || this.done;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2229s;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled == 1);
            }
            return attr == Scannable.Attr.CAPACITY ? Integer.valueOf(this.size) : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.window));
        }
    }

    static final class WindowSkipSubscriber<T> implements Disposable, InnerOperator<T, Flux<T>> {
        final CoreSubscriber<? super Flux<T>> actual;
        volatile int cancelled;
        final Context ctx;
        boolean done;
        volatile int firstRequest;
        int index;
        final Supplier<? extends Queue<T>> processorQueueSupplier;

        /* JADX INFO: renamed from: s */
        Subscription f2231s;
        final int size;
        final int skip;
        Sinks.Many<T> window;
        volatile int windowCount;
        static final AtomicIntegerFieldUpdater<WindowSkipSubscriber> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(WindowSkipSubscriber.class, LoggingKeys.CANCELLED_ERROR_TYPE);
        static final AtomicIntegerFieldUpdater<WindowSkipSubscriber> WINDOW_COUNT = AtomicIntegerFieldUpdater.newUpdater(WindowSkipSubscriber.class, "windowCount");
        static final AtomicIntegerFieldUpdater<WindowSkipSubscriber> FIRST_REQUEST = AtomicIntegerFieldUpdater.newUpdater(WindowSkipSubscriber.class, "firstRequest");

        WindowSkipSubscriber(CoreSubscriber<? super Flux<T>> coreSubscriber, int i, int i2, Supplier<? extends Queue<T>> supplier) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.size = i;
            this.skip = i2;
            this.processorQueueSupplier = supplier;
            WINDOW_COUNT.lazySet(this, 1);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2231s, subscription)) {
                this.f2231s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            int i = this.index;
            Sinks.Many<T> manyOnBackpressureBuffer = this.window;
            if (i == 0) {
                WINDOW_COUNT.getAndIncrement(this);
                manyOnBackpressureBuffer = Sinks.unsafe().many().unicast().onBackpressureBuffer(this.processorQueueSupplier.get(), this);
                this.window = manyOnBackpressureBuffer;
                this.actual.onNext(manyOnBackpressureBuffer.asFlux());
            }
            int i2 = i + 1;
            if (manyOnBackpressureBuffer != null) {
                manyOnBackpressureBuffer.emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
            } else {
                Operators.onDiscard(t, this.ctx);
            }
            if (i2 == this.size) {
                this.window = null;
                if (manyOnBackpressureBuffer != null) {
                    manyOnBackpressureBuffer.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                }
            }
            if (i2 == this.skip) {
                this.index = 0;
            } else {
                this.index = i2;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.ctx);
                return;
            }
            this.done = true;
            Sinks.Many<T> many = this.window;
            if (many != null) {
                this.window = null;
                many.emitError(Exceptions.wrapSource(th), Sinks.EmitFailureHandler.FAIL_FAST);
            }
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            Sinks.Many<T> many = this.window;
            if (many != null) {
                this.window = null;
                many.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
            }
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                if (this.firstRequest == 0 && FIRST_REQUEST.compareAndSet(this, 0, 1)) {
                    this.f2231s.request(Operators.addCap(Operators.multiplyCap(this.size, j), Operators.multiplyCap(this.skip - this.size, j - 1)));
                } else {
                    this.f2231s.request(Operators.multiplyCap(this.skip, j));
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                dispose();
            }
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return this.cancelled == 1 || this.done;
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            if (WINDOW_COUNT.decrementAndGet(this) == 0) {
                this.f2231s.cancel();
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2231s;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled == 1);
            }
            return attr == Scannable.Attr.CAPACITY ? Integer.valueOf(this.size) : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.window));
        }
    }

    static final class WindowOverlapSubscriber<T> extends ArrayDeque<Sinks.Many<T>> implements Disposable, InnerOperator<T, Flux<T>> {
        final CoreSubscriber<? super Flux<T>> actual;
        volatile int cancelled;
        volatile boolean done;
        Throwable error;
        volatile int firstRequest;
        int index;
        final Supplier<? extends Queue<T>> processorQueueSupplier;
        int produced;
        final Queue<Sinks.Many<T>> queue;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2230s;
        final int size;
        final int skip;
        volatile int windowCount;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<WindowOverlapSubscriber> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(WindowOverlapSubscriber.class, LoggingKeys.CANCELLED_ERROR_TYPE);
        static final AtomicIntegerFieldUpdater<WindowOverlapSubscriber> WINDOW_COUNT = AtomicIntegerFieldUpdater.newUpdater(WindowOverlapSubscriber.class, "windowCount");
        static final AtomicIntegerFieldUpdater<WindowOverlapSubscriber> FIRST_REQUEST = AtomicIntegerFieldUpdater.newUpdater(WindowOverlapSubscriber.class, "firstRequest");
        static final AtomicLongFieldUpdater<WindowOverlapSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(WindowOverlapSubscriber.class, "requested");
        static final AtomicIntegerFieldUpdater<WindowOverlapSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(WindowOverlapSubscriber.class, "wip");

        WindowOverlapSubscriber(CoreSubscriber<? super Flux<T>> coreSubscriber, int i, int i2, Supplier<? extends Queue<T>> supplier, Queue<Sinks.Many<T>> queue) {
            this.actual = coreSubscriber;
            this.size = i;
            this.skip = i2;
            this.processorQueueSupplier = supplier;
            WINDOW_COUNT.lazySet(this, 1);
            this.queue = queue;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2230s, subscription)) {
                this.f2230s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            int i = this.index;
            if (i == 0 && this.cancelled == 0) {
                WINDOW_COUNT.getAndIncrement(this);
                Sinks.Many<T> manyOnBackpressureBuffer = Sinks.unsafe().many().unicast().onBackpressureBuffer(this.processorQueueSupplier.get(), this);
                offer(manyOnBackpressureBuffer);
                this.queue.offer(manyOnBackpressureBuffer);
                drain();
            }
            int i2 = i + 1;
            Iterator<Sinks.Many<T>> it = iterator();
            while (it.hasNext()) {
                it.next().emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
            }
            int i3 = this.produced + 1;
            if (i3 == this.size) {
                this.produced = i3 - this.skip;
                Sinks.Many<T> manyPoll = poll();
                if (manyPoll != null) {
                    manyPoll.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                }
            } else {
                this.produced = i3;
            }
            if (i2 == this.skip) {
                this.index = 0;
            } else {
                this.index = i2;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            Iterator<Sinks.Many<T>> it = iterator();
            while (it.hasNext()) {
                it.next().emitError(Exceptions.wrapSource(th), Sinks.EmitFailureHandler.FAIL_FAST);
            }
            clear();
            this.error = th;
            drain();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            Iterator<Sinks.Many<T>> it = iterator();
            while (it.hasNext()) {
                it.next().emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
            }
            clear();
            drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber<? super Flux<T>> coreSubscriber = this.actual;
            Queue<Sinks.Many<T>> queue = this.queue;
            int iAddAndGet = 1;
            do {
                long j = this.requested;
                long j2 = 0;
                while (j2 != j) {
                    boolean z = this.done;
                    Sinks.Many<T> manyPoll = queue.poll();
                    boolean z2 = manyPoll == null;
                    if (checkTerminated(z, z2, coreSubscriber, queue)) {
                        return;
                    }
                    if (z2) {
                        break;
                    }
                    coreSubscriber.onNext(manyPoll.asFlux());
                    j2++;
                }
                if (j2 == j && checkTerminated(this.done, queue.isEmpty(), coreSubscriber, queue)) {
                    return;
                }
                if (j2 != 0 && j != Long.MAX_VALUE) {
                    REQUESTED.addAndGet(this, -j2);
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, Queue<?> queue) {
            if (this.cancelled == 1) {
                queue.clear();
                return true;
            }
            if (!z) {
                return false;
            }
            Throwable th = this.error;
            if (th != null) {
                queue.clear();
                subscriber.onError(th);
                return true;
            }
            if (!z2) {
                return false;
            }
            subscriber.onComplete();
            return true;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
                if (this.firstRequest == 0 && FIRST_REQUEST.compareAndSet(this, 0, 1)) {
                    this.f2230s.request(Operators.addCap(this.size, Operators.multiplyCap(this.skip, j - 1)));
                } else {
                    this.f2230s.request(Operators.multiplyCap(this.skip, j));
                }
                drain();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                dispose();
            }
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            if (WINDOW_COUNT.decrementAndGet(this) == 0) {
                this.f2230s.cancel();
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return this.cancelled == 1 || this.done;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2230s;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled == 1);
            }
            if (attr == Scannable.Attr.CAPACITY) {
                return Integer.valueOf(this.size);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.LARGE_BUFFERED) {
                return Long.valueOf(((long) this.queue.size()) + ((long) size()));
            }
            if (attr != Scannable.Attr.BUFFERED) {
                return attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            long size = ((long) this.queue.size()) + ((long) size());
            if (size < 2147483647L) {
                return Integer.valueOf((int) size);
            }
            return Integer.MIN_VALUE;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(toArray()).map(new Scannable$Attr$$ExternalSyntheticLambda0());
        }
    }
}
