package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.Consumer;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoUsing<T, S> extends Mono<T> implements Fuseable, SourceProducer<T> {
    final boolean eager;
    final Consumer<? super S> resourceCleanup;
    final Callable<S> resourceSupplier;
    final Function<? super S, ? extends Mono<? extends T>> sourceFactory;

    MonoUsing(Callable<S> callable, Function<? super S, ? extends Mono<? extends T>> function, Consumer<? super S> consumer, boolean z) {
        this.resourceSupplier = (Callable) Objects.requireNonNull(callable, "resourceSupplier");
        this.sourceFactory = (Function) Objects.requireNonNull(function, "sourceFactory");
        this.resourceCleanup = (Consumer) Objects.requireNonNull(consumer, "resourceCleanup");
        this.eager = z;
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        try {
            S sCall = this.resourceSupplier.call();
            try {
                Mono monoFromDirect = Mono.fromDirect((Publisher) Objects.requireNonNull(this.sourceFactory.apply(sCall), "The sourceFactory returned a null value"));
                if (monoFromDirect instanceof Fuseable) {
                    monoFromDirect.subscribe((CoreSubscriber) new MonoUsingSubscriber(coreSubscriber, this.resourceCleanup, sCall, this.eager, true));
                } else {
                    monoFromDirect.subscribe((CoreSubscriber) new MonoUsingSubscriber(coreSubscriber, this.resourceCleanup, sCall, this.eager, false));
                }
            } catch (Throwable th) {
                th = th;
                try {
                    this.resourceCleanup.accept(sCall);
                } catch (Throwable th2) {
                    th = Exceptions.addSuppressed(th2, Operators.onOperatorError(th, coreSubscriber.currentContext()));
                }
                Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
            }
        } catch (Throwable th3) {
            Operators.error(coreSubscriber, Operators.onOperatorError(th3, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class MonoUsingSubscriber<T, S> implements InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        static final AtomicIntegerFieldUpdater<MonoUsingSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(MonoUsingSubscriber.class, "wip");
        final CoreSubscriber<? super T> actual;
        final boolean allowFusion;
        final boolean eager;
        int mode;

        /* JADX INFO: renamed from: qs */
        @Nullable
        Fuseable.QueueSubscription<T> f2274qs;
        final S resource;
        final Consumer<? super S> resourceCleanup;

        /* JADX INFO: renamed from: s */
        Subscription f2275s;
        boolean valued;
        volatile int wip;

        MonoUsingSubscriber(CoreSubscriber<? super T> coreSubscriber, Consumer<? super S> consumer, S s, boolean z, boolean z2) {
            this.actual = coreSubscriber;
            this.resourceCleanup = consumer;
            this.resource = s;
            this.eager = z;
            this.allowFusion = z2;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.wip == 1);
            }
            return attr == Scannable.Attr.PARENT ? this.f2275s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2275s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (WIP.compareAndSet(this, 0, 1)) {
                this.f2275s.cancel();
                cleanup();
            }
        }

        void cleanup() {
            try {
                this.resourceCleanup.accept(this.resource);
            } catch (Throwable th) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2275s, subscription)) {
                this.f2275s = subscription;
                if (subscription instanceof Fuseable.QueueSubscription) {
                    this.f2274qs = (Fuseable.QueueSubscription) subscription;
                }
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.mode == 2) {
                this.actual.onNext(null);
                return;
            }
            this.valued = true;
            if (this.eager && WIP.compareAndSet(this, 0, 1)) {
                try {
                    this.resourceCleanup.accept(this.resource);
                } catch (Throwable th) {
                    Context contextCurrentContext = this.actual.currentContext();
                    this.actual.onError(Operators.onOperatorError(th, contextCurrentContext));
                    Operators.onDiscard(t, contextCurrentContext);
                    return;
                }
            }
            this.actual.onNext(t);
            this.actual.onComplete();
            if (this.eager || !WIP.compareAndSet(this, 0, 1)) {
                return;
            }
            try {
                this.resourceCleanup.accept(this.resource);
            } catch (Throwable th2) {
                Operators.onErrorDropped(th2, this.actual.currentContext());
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.valued && this.mode != 2) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            if (this.eager && WIP.compareAndSet(this, 0, 1)) {
                try {
                    this.resourceCleanup.accept(this.resource);
                } catch (Throwable th2) {
                    th = Exceptions.addSuppressed(Operators.onOperatorError(th2, this.actual.currentContext()), th);
                }
            }
            this.actual.onError(th);
            if (this.eager || !WIP.compareAndSet(this, 0, 1)) {
                return;
            }
            cleanup();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.valued || this.mode == 2) {
                if (this.eager && WIP.compareAndSet(this, 0, 1)) {
                    try {
                        this.resourceCleanup.accept(this.resource);
                    } catch (Throwable th) {
                        CoreSubscriber<? super T> coreSubscriber = this.actual;
                        coreSubscriber.onError(Operators.onOperatorError(th, coreSubscriber.currentContext()));
                        return;
                    }
                }
                this.actual.onComplete();
                if (this.eager || !WIP.compareAndSet(this, 0, 1)) {
                    return;
                }
                try {
                    this.resourceCleanup.accept(this.resource);
                } catch (Throwable th2) {
                    Operators.onErrorDropped(th2, this.actual.currentContext());
                }
            }
        }

        @Override // java.util.Collection
        public void clear() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2274qs;
            if (queueSubscription != null) {
                queueSubscription.clear();
            }
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2274qs;
            return queueSubscription == null || queueSubscription.isEmpty();
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            Fuseable.QueueSubscription<T> queueSubscription;
            if (this.mode == 0 || (queueSubscription = this.f2274qs) == null) {
                return null;
            }
            T tPoll = queueSubscription.poll();
            if (tPoll != null) {
                this.valued = true;
                if (this.eager && WIP.compareAndSet(this, 0, 1)) {
                    try {
                        this.resourceCleanup.accept(this.resource);
                    } catch (Throwable th) {
                        Operators.onDiscard(tPoll, this.actual.currentContext());
                        throw th;
                    }
                }
            } else if (this.mode == 1 && !this.eager && WIP.compareAndSet(this, 0, 1)) {
                try {
                    this.resourceCleanup.accept(this.resource);
                } catch (Throwable th2) {
                    if (!this.valued) {
                        throw th2;
                    }
                    Operators.onErrorDropped(th2, this.actual.currentContext());
                }
            }
            return tPoll;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2274qs;
            if (queueSubscription == null) {
                this.mode = 0;
                return 0;
            }
            int iRequestFusion = queueSubscription.requestFusion(i);
            this.mode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Collection
        public int size() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2274qs;
            if (queueSubscription == null) {
                return 0;
            }
            return queueSubscription.size();
        }
    }
}
