package reactor.core.publisher;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxMapSignal<T, R> extends InternalFluxOperator<T, R> {
    final Supplier<? extends R> mapperComplete;
    final Function<? super Throwable, ? extends R> mapperError;
    final Function<? super T, ? extends R> mapperNext;

    FluxMapSignal(Flux<? extends T> flux, @Nullable Function<? super T, ? extends R> function, @Nullable Function<? super Throwable, ? extends R> function2, @Nullable Supplier<? extends R> supplier) {
        super(flux);
        if (function == null && function2 == null && supplier == null) {
            throw new IllegalArgumentException("Map Signal needs at least one valid mapper");
        }
        this.mapperNext = function;
        this.mapperError = function2;
        this.mapperComplete = supplier;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        return new FluxMapSignalSubscriber(coreSubscriber, this.mapperNext, this.mapperError, this.mapperComplete);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class FluxMapSignalSubscriber<T, R> extends AbstractQueue<R> implements InnerOperator<T, R>, BooleanSupplier {
        static final AtomicLongFieldUpdater<FluxMapSignalSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(FluxMapSignalSubscriber.class, "requested");
        final CoreSubscriber<? super R> actual;
        volatile boolean cancelled;
        boolean done;
        final Supplier<? extends R> mapperComplete;
        final Function<? super Throwable, ? extends R> mapperError;
        final Function<? super T, ? extends R> mapperNext;
        long produced;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2149s;
        R value;

        FluxMapSignalSubscriber(CoreSubscriber<? super R> coreSubscriber, @Nullable Function<? super T, ? extends R> function, @Nullable Function<? super Throwable, ? extends R> function2, @Nullable Supplier<? extends R> supplier) {
            this.actual = coreSubscriber;
            this.mapperNext = function;
            this.mapperError = function2;
            this.mapperComplete = supplier;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2149s, subscription)) {
                this.f2149s = subscription;
                this.actual.onSubscribe(this);
                if (this.mapperNext == null) {
                    subscription.request(Long.MAX_VALUE);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            Function<? super T, ? extends R> function = this.mapperNext;
            if (function == null) {
                return;
            }
            try {
                R rApply = function.apply(t);
                if (rApply == null) {
                    throw new NullPointerException("The mapper [" + this.mapperNext.getClass().getName() + "] returned a null value.");
                }
                this.produced++;
                this.actual.onNext(rApply);
            } catch (Throwable th) {
                this.done = true;
                CoreSubscriber<? super R> coreSubscriber = this.actual;
                coreSubscriber.onError(Operators.onOperatorError(this.f2149s, th, t, coreSubscriber.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            Function<? super Throwable, ? extends R> function = this.mapperError;
            if (function == null) {
                this.actual.onError(th);
                return;
            }
            try {
                R rApply = function.apply(th);
                if (rApply == null) {
                    throw new NullPointerException("The mapper [" + this.mapperError.getClass().getName() + "] returned a null value.");
                }
                this.value = rApply;
                long j = this.produced;
                if (j != 0) {
                    Operators.addCap(REQUESTED, this, -j);
                }
                DrainUtils.postComplete(this.actual, this, REQUESTED, this, this);
            } catch (Throwable th2) {
                this.done = true;
                CoreSubscriber<? super R> coreSubscriber = this.actual;
                coreSubscriber.onError(Operators.onOperatorError(this.f2149s, th2, th, coreSubscriber.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            Supplier<? extends R> supplier = this.mapperComplete;
            if (supplier == null) {
                this.actual.onComplete();
                return;
            }
            try {
                R r = supplier.get();
                if (r == null) {
                    throw new NullPointerException("The mapper [" + this.mapperComplete.getClass().getName() + "] returned a null value.");
                }
                this.value = r;
                long j = this.produced;
                if (j != 0) {
                    Operators.addCap(REQUESTED, this, -j);
                }
                DrainUtils.postComplete(this.actual, this, REQUESTED, this, this);
            } catch (Throwable th) {
                this.done = true;
                CoreSubscriber<? super R> coreSubscriber = this.actual;
                coreSubscriber.onError(Operators.onOperatorError(this.f2149s, th, coreSubscriber.currentContext()));
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (!Operators.validate(j) || DrainUtils.postCompleteRequest(j, this.actual, this, REQUESTED, this, this)) {
                return;
            }
            this.f2149s.request(j);
        }

        @Override // java.util.Queue
        public boolean offer(R r) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Queue
        @Nullable
        public R poll() {
            R r = this.value;
            if (r == null) {
                return null;
            }
            this.value = null;
            return r;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2149s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(getAsBoolean()) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(size()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // java.util.Queue
        @Nullable
        public R peek() {
            return this.value;
        }

        @Override // java.util.function.BooleanSupplier
        public boolean getAsBoolean() {
            return this.cancelled;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            this.f2149s.cancel();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<R> iterator() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.value == null ? 0 : 1;
        }
    }
}
