package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
class MonoFilterWhen<T> extends InternalMonoOperator<T, T> {
    final Function<? super T, ? extends Publisher<Boolean>> asyncPredicate;

    MonoFilterWhen(Mono<T> mono, Function<? super T, ? extends Publisher<Boolean>> function) {
        super(mono);
        this.asyncPredicate = function;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new MonoFilterWhenMain(coreSubscriber, this.asyncPredicate);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class MonoFilterWhenMain<T> implements InnerOperator<T, T>, Fuseable, Fuseable.QueueSubscription<T> {
        static final AtomicReferenceFieldUpdater<MonoFilterWhenMain, FilterWhenInner> ASYNC_FILTER = AtomicReferenceFieldUpdater.newUpdater(MonoFilterWhenMain.class, FilterWhenInner.class, "asyncFilter");
        static final FilterWhenInner INNER_CANCELLED = new FilterWhenInner(null, false, null);
        static final FilterWhenInner INNER_TERMINATED = new FilterWhenInner(null, false, null);
        final CoreSubscriber<? super T> actual;
        volatile FilterWhenInner<T> asyncFilter;
        final Function<? super T, ? extends Publisher<Boolean>> asyncPredicate;
        boolean done;

        /* JADX INFO: renamed from: s */
        Subscription f2254s;

        @Override // java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.Queue
        public T poll() {
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

        MonoFilterWhenMain(CoreSubscriber<? super T> coreSubscriber, Function<? super T, ? extends Publisher<Boolean>> function) {
            this.actual = coreSubscriber;
            this.asyncPredicate = function;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2254s, subscription)) {
                this.f2254s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.done = true;
            try {
                Publisher publisher = (Publisher) Objects.requireNonNull(this.asyncPredicate.apply(t), "The asyncPredicate returned a null value");
                if (publisher instanceof Callable) {
                    try {
                        Boolean bool = (Boolean) ((Callable) publisher).call();
                        if (bool != null && bool.booleanValue()) {
                            this.actual.onNext(t);
                            this.actual.onComplete();
                            return;
                        } else {
                            Operators.onDiscard(t, this.actual.currentContext());
                            this.actual.onComplete();
                            return;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        Operators.onDiscard(t, this.actual.currentContext());
                        this.actual.onError(th);
                        return;
                    }
                }
                Operators.toFluxOrMono(publisher).subscribe((Subscriber) new FilterWhenInner(this, true ^ (publisher instanceof Mono), t));
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                Operators.onDiscard(t, this.actual.currentContext());
                this.actual.onError(th2);
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

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, currentContext());
            } else {
                this.done = true;
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2254s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2254s.cancel();
            FilterWhenInner<T> filterWhenInner = this.asyncFilter;
            FilterWhenInner<T> filterWhenInner2 = INNER_CANCELLED;
            if (filterWhenInner == filterWhenInner2 || filterWhenInner == INNER_TERMINATED || !C0162xc40028dd.m5m(ASYNC_FILTER, this, filterWhenInner, filterWhenInner2) || filterWhenInner == null) {
                return;
            }
            filterWhenInner.cancel();
        }

        public boolean trySetInner(FilterWhenInner<T> filterWhenInner) {
            if (this.asyncFilter == null && C0162xc40028dd.m5m(ASYNC_FILTER, this, null, filterWhenInner)) {
                return true;
            }
            Operators.onDiscard(filterWhenInner.value, currentContext());
            return false;
        }

        void innerResult(boolean z, FilterWhenInner<T> filterWhenInner) {
            if (this.asyncFilter == filterWhenInner && C0162xc40028dd.m5m(ASYNC_FILTER, this, filterWhenInner, INNER_TERMINATED)) {
                if (z) {
                    this.actual.onNext(filterWhenInner.value);
                    this.actual.onComplete();
                } else {
                    Operators.onDiscard(filterWhenInner.value, currentContext());
                    this.actual.onComplete();
                }
            }
        }

        void innerError(Throwable th, FilterWhenInner<T> filterWhenInner) {
            if (this.asyncFilter == filterWhenInner && C0162xc40028dd.m5m(ASYNC_FILTER, this, filterWhenInner, INNER_TERMINATED)) {
                Operators.onDiscard(filterWhenInner.value, currentContext());
                this.actual.onError(th);
            }
            Operators.onErrorDropped(th, currentContext());
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2254s;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return 0;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                FilterWhenInner<T> filterWhenInner = this.asyncFilter;
                if (this.done && (filterWhenInner == null || filterWhenInner == INNER_TERMINATED)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.asyncFilter == INNER_CANCELLED);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            FilterWhenInner<T> filterWhenInner = this.asyncFilter;
            return (filterWhenInner == null || filterWhenInner == INNER_CANCELLED || filterWhenInner == INNER_TERMINATED) ? Stream.empty() : Stream.of(filterWhenInner);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }

    static final class FilterWhenInner<T> implements InnerConsumer<Boolean> {
        final boolean cancelOnNext;
        boolean done;
        final MonoFilterWhenMain<T> parent;

        /* JADX INFO: renamed from: s */
        Subscription f2253s;
        final T value;

        FilterWhenInner(MonoFilterWhenMain<T> monoFilterWhenMain, boolean z, T t) {
            this.parent = monoFilterWhenMain;
            this.cancelOnNext = z;
            this.value = t;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2253s, subscription)) {
                this.f2253s = subscription;
                if (this.parent.trySetInner(this)) {
                    subscription.request(Long.MAX_VALUE);
                } else {
                    subscription.cancel();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Boolean bool) {
            if (this.done) {
                return;
            }
            this.done = true;
            if (this.cancelOnNext) {
                this.f2253s.cancel();
            }
            this.parent.innerResult(bool.booleanValue(), this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.parent.currentContext());
            } else {
                this.done = true;
                this.parent.innerError(th, this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.innerResult(false, this);
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2253s;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return 0;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.done ? 0L : 1L);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        void cancel() {
            this.f2253s.cancel();
        }
    }
}
