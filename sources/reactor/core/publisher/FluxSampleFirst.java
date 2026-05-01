package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
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
final class FluxSampleFirst<T, U> extends InternalFluxOperator<T, T> {
    final Function<? super T, ? extends Publisher<U>> throttler;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    FluxSampleFirst(Flux<? extends T> flux, Function<? super T, ? extends Publisher<U>> function) {
        super(flux);
        this.throttler = (Function) Objects.requireNonNull(function, "throttler");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        SampleFirstMain sampleFirstMain = new SampleFirstMain(coreSubscriber, this.throttler);
        coreSubscriber.onSubscribe(sampleFirstMain);
        return sampleFirstMain;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SampleFirstMain<T, U> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        volatile Throwable error;
        volatile boolean gate;
        volatile Subscription other;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2184s;
        final Function<? super T, ? extends Publisher<U>> throttler;
        volatile int wip;

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<SampleFirstMain, Subscription> f2183S = AtomicReferenceFieldUpdater.newUpdater(SampleFirstMain.class, Subscription.class, "s");
        static final AtomicReferenceFieldUpdater<SampleFirstMain, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(SampleFirstMain.class, Subscription.class, "other");
        static final AtomicLongFieldUpdater<SampleFirstMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(SampleFirstMain.class, "requested");
        static final AtomicIntegerFieldUpdater<SampleFirstMain> WIP = AtomicIntegerFieldUpdater.newUpdater(SampleFirstMain.class, "wip");
        static final AtomicReferenceFieldUpdater<SampleFirstMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(SampleFirstMain.class, Throwable.class, "error");

        SampleFirstMain(CoreSubscriber<? super T> coreSubscriber, Function<? super T, ? extends Publisher<U>> function) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.throttler = function;
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2184s == Operators.cancelledSubscription());
            }
            return attr == Scannable.Attr.PARENT ? this.f2184s : attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Operators.terminate(f2183S, this);
            Operators.terminate(OTHER, this);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2183S, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.gate) {
                this.gate = true;
                if (this.wip == 0) {
                    AtomicIntegerFieldUpdater<SampleFirstMain> atomicIntegerFieldUpdater = WIP;
                    if (atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                        this.actual.onNext(t);
                        if (atomicIntegerFieldUpdater.decrementAndGet(this) != 0) {
                            handleTermination();
                            return;
                        }
                        try {
                            Publisher publisher = (Publisher) Objects.requireNonNull(this.throttler.apply(t), "The throttler returned a null publisher");
                            SampleFirstOther sampleFirstOther = new SampleFirstOther(this);
                            if (Operators.replace(OTHER, this, sampleFirstOther)) {
                                Operators.toFluxOrMono(publisher).subscribe((Subscriber) sampleFirstOther);
                                return;
                            }
                            return;
                        } catch (Throwable th) {
                            Operators.terminate(f2183S, this);
                            error(Operators.onOperatorError(null, th, t, this.ctx));
                            return;
                        }
                    }
                    return;
                }
                return;
            }
            Operators.onDiscard(t, this.ctx);
        }

        void handleTermination() {
            Throwable thTerminate = Exceptions.terminate(ERROR, this);
            if (thTerminate != null && thTerminate != Exceptions.TERMINATED) {
                this.actual.onError(thTerminate);
            } else {
                this.actual.onComplete();
            }
        }

        void error(Throwable th) {
            if (Exceptions.addThrowable(ERROR, this, th)) {
                if (WIP.getAndIncrement(this) == 0) {
                    handleTermination();
                    return;
                }
                return;
            }
            Operators.onErrorDropped(th, this.ctx);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Operators.terminate(OTHER, this);
            error(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Operators.terminate(OTHER, this);
            if (WIP.getAndIncrement(this) == 0) {
                handleTermination();
            }
        }

        void otherNext() {
            this.gate = false;
        }

        void otherError(Throwable th) {
            Operators.terminate(f2183S, this);
            error(th);
        }
    }

    static final class SampleFirstOther<U> extends Operators.DeferredSubscription implements InnerConsumer<U> {
        final SampleFirstMain<?, U> main;

        SampleFirstOther(SampleFirstMain<?, U> sampleFirstMain) {
            this.main = sampleFirstMain;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override // reactor.core.publisher.Operators.DeferredSubscription, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
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
            cancel();
            this.main.otherNext();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.main.otherError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.main.otherNext();
        }
    }
}
