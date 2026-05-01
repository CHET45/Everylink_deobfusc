package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoFlatMap<T, R> extends InternalMonoOperator<T, R> implements Fuseable {
    final Function<? super T, ? extends Mono<? extends R>> mapper;

    MonoFlatMap(Mono<? extends T> mono, Function<? super T, ? extends Mono<? extends R>> function) {
        super(mono);
        this.mapper = (Function) Objects.requireNonNull(function, "mapper");
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, coreSubscriber, this.mapper, true, false)) {
            return null;
        }
        return new FlatMapMain(coreSubscriber, this.mapper);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class FlatMapMain<T, R> implements InnerOperator<T, R>, Fuseable, Fuseable.QueueSubscription<R> {
        static final AtomicReferenceFieldUpdater<FlatMapMain, FlatMapInner> SECOND = AtomicReferenceFieldUpdater.newUpdater(FlatMapMain.class, FlatMapInner.class, "second");
        final CoreSubscriber<? super R> actual;
        boolean done;
        final Function<? super T, ? extends Mono<? extends R>> mapper;

        /* JADX INFO: renamed from: s */
        Subscription f2256s;
        volatile FlatMapInner<R> second;

        @Override // java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.Queue
        public R poll() {
            return null;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return 0;
        }

        @Override // java.util.Collection
        public int size() {
            return 0;
        }

        FlatMapMain(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Mono<? extends R>> function) {
            this.actual = coreSubscriber;
            this.mapper = function;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.second);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2256s;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return 0;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.second == FlatMapInner.CANCELLED);
            }
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2256s, subscription)) {
                this.f2256s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            try {
                Publisher publisher = (Mono) Objects.requireNonNull(this.mapper.apply(t), "The mapper returned a null Mono");
                if (publisher instanceof Callable) {
                    try {
                        Object objCall = ((Callable) publisher).call();
                        if (objCall == null) {
                            this.actual.onComplete();
                            return;
                        } else {
                            this.actual.onNext(objCall);
                            this.actual.onComplete();
                            return;
                        }
                    } catch (Throwable th) {
                        this.actual.onError(Operators.onOperatorError(this.f2256s, th, t, this.actual.currentContext()));
                        return;
                    }
                }
                try {
                    Mono.fromDirect(publisher).subscribe((CoreSubscriber) new FlatMapInner(this));
                } catch (Throwable th2) {
                    CoreSubscriber<? super R> coreSubscriber = this.actual;
                    coreSubscriber.onError(Operators.onOperatorError(this, th2, t, coreSubscriber.currentContext()));
                }
            } catch (Throwable th3) {
                CoreSubscriber<? super R> coreSubscriber2 = this.actual;
                coreSubscriber2.onError(Operators.onOperatorError(this.f2256s, th3, t, coreSubscriber2.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                this.done = true;
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2256s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2256s.cancel();
            FlatMapInner<R> flatMapInner = this.second;
            if (flatMapInner == FlatMapInner.CANCELLED || !C0162xc40028dd.m5m(SECOND, this, flatMapInner, FlatMapInner.CANCELLED) || flatMapInner == null) {
                return;
            }
            flatMapInner.f2255s.cancel();
        }

        boolean setSecond(FlatMapInner<R> flatMapInner) {
            return this.second == null && C0162xc40028dd.m5m(SECOND, this, null, flatMapInner);
        }

        void secondError(Throwable th) {
            this.actual.onError(th);
        }

        void secondComplete(R r) {
            this.actual.onNext(r);
            this.actual.onComplete();
        }

        void secondComplete() {
            this.actual.onComplete();
        }
    }

    static final class FlatMapInner<R> implements InnerConsumer<R> {
        static final FlatMapInner<?> CANCELLED = new FlatMapInner<>(null);
        boolean done;
        final FlatMapMain<?, R> parent;

        /* JADX INFO: renamed from: s */
        Subscription f2255s;

        FlatMapInner(FlatMapMain<?, R> flatMapMain) {
            this.parent = flatMapMain;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2255s;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2255s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2255s, subscription)) {
                this.f2255s = subscription;
                if (this.parent.setSecond(this)) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.cancel();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(R r) {
            if (this.done) {
                Operators.onNextDropped(r, this.parent.currentContext());
            } else {
                this.done = true;
                this.parent.secondComplete(r);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.parent.currentContext());
            } else {
                this.done = true;
                this.parent.secondError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.secondComplete();
        }
    }
}
