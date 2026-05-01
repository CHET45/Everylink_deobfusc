package reactor.core.publisher;

import com.aivox.base.common.Constant;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxSampleTimeout<T, U> extends InternalFluxOperator<T, T> {
    final Supplier<Queue<Object>> queueSupplier;
    final Function<? super T, ? extends Publisher<U>> throttler;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxSampleTimeout(Flux<? extends T> flux, Function<? super T, ? extends Publisher<U>> function, Supplier<Queue<Object>> supplier) {
        super(flux);
        this.throttler = (Function) Objects.requireNonNull(function, "throttler");
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        SampleTimeoutMain sampleTimeoutMain = new SampleTimeoutMain(coreSubscriber, this.throttler, this.queueSupplier.get());
        coreSubscriber.onSubscribe(sampleTimeoutMain);
        return sampleTimeoutMain;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SampleTimeoutMain<T, U> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        final Context ctx;
        volatile boolean done;
        volatile Throwable error;
        volatile long index;
        volatile Subscription other;
        final Queue<SampleTimeoutOther<T, U>> queue;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2186s;
        final Function<? super T, ? extends Publisher<U>> throttler;
        volatile int wip;

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<SampleTimeoutMain, Subscription> f2185S = AtomicReferenceFieldUpdater.newUpdater(SampleTimeoutMain.class, Subscription.class, "s");
        static final AtomicReferenceFieldUpdater<SampleTimeoutMain, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(SampleTimeoutMain.class, Subscription.class, "other");
        static final AtomicLongFieldUpdater<SampleTimeoutMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(SampleTimeoutMain.class, "requested");
        static final AtomicIntegerFieldUpdater<SampleTimeoutMain> WIP = AtomicIntegerFieldUpdater.newUpdater(SampleTimeoutMain.class, "wip");
        static final AtomicReferenceFieldUpdater<SampleTimeoutMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(SampleTimeoutMain.class, Throwable.class, "error");
        static final AtomicLongFieldUpdater<SampleTimeoutMain> INDEX = AtomicLongFieldUpdater.newUpdater(SampleTimeoutMain.class, Constant.KEY_INDEX);

        SampleTimeoutMain(CoreSubscriber<? super T> coreSubscriber, Function<? super T, ? extends Publisher<U>> function, Queue<SampleTimeoutOther<T, U>> queue) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.throttler = function;
            this.queue = queue;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.PARENT ? this.f2186s : attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(this.queue.size()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            Operators.terminate(f2185S, this);
            Operators.terminate(OTHER, this);
            Operators.onDiscardQueueWithClear(this.queue, this.ctx, new FluxSampleTimeout$SampleTimeoutMain$$ExternalSyntheticLambda0());
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2185S, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            long jIncrementAndGet = INDEX.incrementAndGet(this);
            AtomicReferenceFieldUpdater<SampleTimeoutMain, Subscription> atomicReferenceFieldUpdater = OTHER;
            if (Operators.set(atomicReferenceFieldUpdater, this, Operators.emptySubscription())) {
                try {
                    Publisher publisher = (Publisher) Objects.requireNonNull(this.throttler.apply(t), "throttler returned a null publisher");
                    SampleTimeoutOther sampleTimeoutOther = new SampleTimeoutOther(this, t, jIncrementAndGet);
                    if (Operators.replace(atomicReferenceFieldUpdater, this, sampleTimeoutOther)) {
                        Operators.toFluxOrMono(publisher).subscribe((Subscriber) sampleTimeoutOther);
                    }
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2186s, th, t, this.ctx));
                }
            }
        }

        void error(Throwable th) {
            if (Exceptions.addThrowable(ERROR, this, th)) {
                this.done = true;
                drain();
            } else {
                Operators.onErrorDropped(th, this.ctx);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Operators.terminate(OTHER, this);
            error(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Subscription subscription = this.other;
            if (subscription instanceof SampleTimeoutOther) {
                SampleTimeoutOther sampleTimeoutOther = (SampleTimeoutOther) subscription;
                sampleTimeoutOther.cancel();
                sampleTimeoutOther.onComplete();
            }
            this.done = true;
            drain();
        }

        void otherNext(SampleTimeoutOther<T, U> sampleTimeoutOther) {
            this.queue.offer(sampleTimeoutOther);
            drain();
        }

        void otherError(long j, Throwable th) {
            if (j == this.index) {
                Operators.terminate(f2185S, this);
                error(th);
            } else {
                Operators.onErrorDropped(th, this.ctx);
            }
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            Queue<SampleTimeoutOther<T, U>> queue = this.queue;
            int iAddAndGet = 1;
            while (true) {
                boolean z = this.done;
                SampleTimeoutOther<T, U> sampleTimeoutOtherPoll = queue.poll();
                boolean z2 = sampleTimeoutOtherPoll == null;
                if (checkTerminated(z, z2, coreSubscriber, queue)) {
                    return;
                }
                if (z2) {
                    iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else if (sampleTimeoutOtherPoll.index == this.index) {
                    long j = this.requested;
                    if (j != 0) {
                        coreSubscriber.onNext(sampleTimeoutOtherPoll.value);
                        if (j != Long.MAX_VALUE) {
                            REQUESTED.decrementAndGet(this);
                        }
                    } else {
                        cancel();
                        Operators.onDiscardQueueWithClear(queue, this.ctx, new FluxSampleTimeout$SampleTimeoutMain$$ExternalSyntheticLambda0());
                        IllegalStateException illegalStateExceptionFailWithOverflow = Exceptions.failWithOverflow("Could not emit value due to lack of requests");
                        AtomicReferenceFieldUpdater<SampleTimeoutMain, Throwable> atomicReferenceFieldUpdater = ERROR;
                        Exceptions.addThrowable(atomicReferenceFieldUpdater, this, illegalStateExceptionFailWithOverflow);
                        coreSubscriber.onError(Exceptions.terminate(atomicReferenceFieldUpdater, this));
                        return;
                    }
                } else {
                    continue;
                }
            }
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, Queue<SampleTimeoutOther<T, U>> queue) {
            if (this.cancelled) {
                Operators.onDiscardQueueWithClear(queue, this.ctx, new FluxSampleTimeout$SampleTimeoutMain$$ExternalSyntheticLambda0());
                return true;
            }
            if (!z) {
                return false;
            }
            Throwable thTerminate = Exceptions.terminate(ERROR, this);
            if (thTerminate == null || thTerminate == Exceptions.TERMINATED) {
                if (!z2) {
                    return false;
                }
                subscriber.onComplete();
                return true;
            }
            cancel();
            Operators.onDiscardQueueWithClear(queue, this.ctx, new FluxSampleTimeout$SampleTimeoutMain$$ExternalSyntheticLambda0());
            subscriber.onError(thTerminate);
            return true;
        }
    }

    static final class SampleTimeoutOther<T, U> extends Operators.DeferredSubscription implements InnerConsumer<U> {
        static final AtomicIntegerFieldUpdater<SampleTimeoutOther> ONCE = AtomicIntegerFieldUpdater.newUpdater(SampleTimeoutOther.class, "once");
        final long index;
        final SampleTimeoutMain<T, U> main;
        volatile int once;
        final T value;

        SampleTimeoutOther(SampleTimeoutMain<T, U> sampleTimeoutMain, T t, long j) {
            this.main = sampleTimeoutMain;
            this.value = t;
            this.index = j;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override // reactor.core.publisher.Operators.DeferredSubscription, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.once == 1);
            }
            return attr == Scannable.Attr.ACTUAL ? this.main : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (set(subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(U u) {
            if (ONCE.compareAndSet(this, 0, 1)) {
                cancel();
                this.main.otherNext(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.main.otherError(this.index, th);
            } else {
                Operators.onErrorDropped(th, this.main.currentContext());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.main.otherNext(this);
            }
        }

        final Stream<T> toStream() {
            return Stream.of(this.value);
        }
    }
}
