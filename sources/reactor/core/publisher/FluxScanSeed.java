package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxScanSeed<T, R> extends InternalFluxOperator<T, R> {
    final BiFunction<R, ? super T, R> accumulator;
    final Supplier<R> initialSupplier;

    FluxScanSeed(Flux<? extends T> flux, Supplier<R> supplier, BiFunction<R, ? super T, R> biFunction) {
        super(flux);
        this.accumulator = (BiFunction) Objects.requireNonNull(biFunction, "accumulator");
        this.initialSupplier = (Supplier) Objects.requireNonNull(supplier, "initialSupplier");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        ScanSeedCoordinator scanSeedCoordinator = new ScanSeedCoordinator(coreSubscriber, this.source, this.accumulator, this.initialSupplier);
        coreSubscriber.onSubscribe(scanSeedCoordinator);
        if (scanSeedCoordinator.isCancelled()) {
            return null;
        }
        scanSeedCoordinator.onComplete();
        return null;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ScanSeedCoordinator<T, R> extends Operators.MultiSubscriptionSubscriber<R, R> {
        static final AtomicIntegerFieldUpdater<ScanSeedCoordinator> WIP = AtomicIntegerFieldUpdater.newUpdater(ScanSeedCoordinator.class, "wip");
        final BiFunction<R, ? super T, R> accumulator;
        final Supplier<R> initialSupplier;
        long produced;
        private ScanSeedSubscriber<T, R> seedSubscriber;
        final Flux<? extends T> source;
        volatile int wip;

        ScanSeedCoordinator(CoreSubscriber<? super R> coreSubscriber, Flux<? extends T> flux, BiFunction<R, ? super T, R> biFunction, Supplier<R> supplier) {
            super(coreSubscriber);
            this.source = flux;
            this.accumulator = biFunction;
            this.initialSupplier = supplier;
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (WIP.getAndIncrement(this) == 0) {
                while (!isCancelled()) {
                    if (this.seedSubscriber != null && this.subscription == this.seedSubscriber) {
                        this.actual.onComplete();
                        return;
                    }
                    long j = this.produced;
                    if (j != 0) {
                        this.produced = 0L;
                        produced(j);
                    }
                    ScanSeedSubscriber<T, R> scanSeedSubscriber = this.seedSubscriber;
                    if (scanSeedSubscriber == null) {
                        try {
                            Object objRequireNonNull = Objects.requireNonNull(this.initialSupplier.get(), "The initial value supplied is null");
                            onSubscribe(Operators.scalarSubscription(this, objRequireNonNull));
                            this.seedSubscriber = new ScanSeedSubscriber<>(this, this.accumulator, objRequireNonNull);
                        } catch (Throwable th) {
                            onError(Operators.onOperatorError(th, this.actual.currentContext()));
                            return;
                        }
                    } else {
                        this.source.subscribe((CoreSubscriber<? super Object>) scanSeedSubscriber);
                    }
                    if (isCancelled() || WIP.decrementAndGet(this) == 0) {
                        return;
                    }
                }
            }
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            this.produced++;
            this.actual.onNext((Object) r);
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static final class ScanSeedSubscriber<T, R> implements InnerOperator<T, R> {
        final BiFunction<R, ? super T, R> accumulator;
        final CoreSubscriber<? super R> actual;
        boolean done;

        /* JADX INFO: renamed from: s */
        Subscription f2188s;
        R value;

        ScanSeedSubscriber(CoreSubscriber<? super R> coreSubscriber, BiFunction<R, ? super T, R> biFunction, R r) {
            this.actual = coreSubscriber;
            this.accumulator = biFunction;
            this.value = r;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2188s.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.value = null;
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.value = null;
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                R r = (Object) Objects.requireNonNull(this.accumulator.apply(this.value, t), "The accumulator returned a null value");
                this.actual.onNext(r);
                this.value = r;
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2188s, th, t, this.actual.currentContext()));
            }
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2188s, subscription)) {
                this.f2188s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2188s.request(j);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2188s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(attr);
        }
    }
}
