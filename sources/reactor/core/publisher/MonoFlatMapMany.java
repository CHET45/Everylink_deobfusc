package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoFlatMapMany<T, R> extends FluxFromMonoOperator<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> mapper;

    MonoFlatMapMany(Mono<? extends T> mono, Function<? super T, ? extends Publisher<? extends R>> function) {
        super(mono);
        this.mapper = function;
    }

    @Override // reactor.core.publisher.FluxFromMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, coreSubscriber, this.mapper, false, false)) {
            return null;
        }
        return new FlatMapManyMain(coreSubscriber, this.mapper);
    }

    @Override // reactor.core.publisher.FluxFromMonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class FlatMapManyMain<T, R> implements InnerOperator<T, R> {
        static final AtomicReferenceFieldUpdater<FlatMapManyMain, Subscription> INNER = AtomicReferenceFieldUpdater.newUpdater(FlatMapManyMain.class, Subscription.class, "inner");
        static final AtomicLongFieldUpdater<FlatMapManyMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(FlatMapManyMain.class, "requested");
        final CoreSubscriber<? super R> actual;
        boolean hasValue;
        volatile Subscription inner;
        Subscription main;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        volatile long requested;

        FlatMapManyMain(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Publisher<? extends R>> function) {
            this.actual = coreSubscriber;
            this.mapper = function;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.main : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.inner));
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            Subscription subscription = this.inner;
            if (subscription != null) {
                subscription.request(j);
                return;
            }
            if (Operators.validate(j)) {
                AtomicLongFieldUpdater<FlatMapManyMain> atomicLongFieldUpdater = REQUESTED;
                Operators.addCap(atomicLongFieldUpdater, this, j);
                Subscription subscription2 = this.inner;
                if (subscription2 != null) {
                    long andSet = atomicLongFieldUpdater.getAndSet(this, 0L);
                    if (andSet != 0) {
                        subscription2.request(andSet);
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.main.cancel();
            Operators.terminate(INNER, this);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.main, subscription)) {
                this.main = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        void onSubscribeInner(Subscription subscription) {
            if (Operators.setOnce(INNER, this, subscription)) {
                long andSet = REQUESTED.getAndSet(this, 0L);
                if (andSet != 0) {
                    subscription.request(andSet);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.hasValue = true;
            try {
                Publisher publisher = (Publisher) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Publisher.");
                if (publisher instanceof Callable) {
                    try {
                        Object objCall = ((Callable) publisher).call();
                        if (objCall == null) {
                            this.actual.onComplete();
                            return;
                        } else {
                            onSubscribeInner(Operators.scalarSubscription(this.actual, objCall));
                            return;
                        }
                    } catch (Throwable th) {
                        CoreSubscriber<? super R> coreSubscriber = this.actual;
                        coreSubscriber.onError(Operators.onOperatorError(this, th, t, coreSubscriber.currentContext()));
                        return;
                    }
                }
                Operators.toFluxOrMono(publisher).subscribe((Subscriber) new FlatMapManyInner(this, this.actual));
            } catch (Throwable th2) {
                CoreSubscriber<? super R> coreSubscriber2 = this.actual;
                coreSubscriber2.onError(Operators.onOperatorError(this, th2, t, coreSubscriber2.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.hasValue) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.hasValue) {
                return;
            }
            this.actual.onComplete();
        }
    }

    static final class FlatMapManyInner<R> implements InnerConsumer<R> {
        final CoreSubscriber<? super R> actual;
        final FlatMapManyMain<?, R> parent;

        FlatMapManyInner(FlatMapManyMain<?, R> flatMapManyMain, CoreSubscriber<? super R> coreSubscriber) {
            this.parent = flatMapManyMain;
            this.actual = coreSubscriber;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.parent.inner;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.parent.requested);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.parent.onSubscribeInner(subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            this.actual.onNext(r);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
        }
    }
}
