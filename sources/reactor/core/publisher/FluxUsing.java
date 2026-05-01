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

/* JADX INFO: loaded from: classes5.dex */
final class FluxUsing<T, S> extends Flux<T> implements Fuseable, SourceProducer<T> {
    final boolean eager;
    final Consumer<? super S> resourceCleanup;
    final Callable<S> resourceSupplier;
    final Function<? super S, ? extends Publisher<? extends T>> sourceFactory;

    FluxUsing(Callable<S> callable, Function<? super S, ? extends Publisher<? extends T>> function, Consumer<? super S> consumer, boolean z) {
        this.resourceSupplier = (Callable) Objects.requireNonNull(callable, "resourceSupplier");
        this.sourceFactory = (Function) Objects.requireNonNull(function, "sourceFactory");
        this.resourceCleanup = (Consumer) Objects.requireNonNull(consumer, "resourceCleanup");
        this.eager = z;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        try {
            S sCall = this.resourceSupplier.call();
            try {
                Publisher publisher = (Publisher) Objects.requireNonNull(this.sourceFactory.apply(sCall), "The sourceFactory returned a null value");
                if (publisher instanceof Fuseable) {
                    from(publisher).subscribe((CoreSubscriber) new UsingFuseableSubscriber(coreSubscriber, this.resourceCleanup, sCall, this.eager));
                } else if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
                    from(publisher).subscribe((CoreSubscriber) new UsingConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.resourceCleanup, sCall, this.eager));
                } else {
                    from(publisher).subscribe((CoreSubscriber) new UsingSubscriber(coreSubscriber, this.resourceCleanup, sCall, this.eager));
                }
            } catch (Throwable th) {
                Throwable thOnOperatorError = Operators.onOperatorError(th, coreSubscriber.currentContext());
                try {
                    this.resourceCleanup.accept(sCall);
                } catch (Throwable th2) {
                    thOnOperatorError = Exceptions.addSuppressed(th2, thOnOperatorError);
                }
                Operators.error(coreSubscriber, thOnOperatorError);
            }
        } catch (Throwable th3) {
            Operators.error(coreSubscriber, Operators.onOperatorError(th3, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class UsingSubscriber<T, S> implements InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        static final AtomicIntegerFieldUpdater<UsingSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(UsingSubscriber.class, "wip");
        final CoreSubscriber<? super T> actual;
        final boolean eager;
        final S resource;
        final Consumer<? super S> resourceCleanup;

        /* JADX INFO: renamed from: s */
        Subscription f2227s;
        volatile int wip;

        @Override // java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.Queue
        @Nullable
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

        UsingSubscriber(CoreSubscriber<? super T> coreSubscriber, Consumer<? super S> consumer, S s, boolean z) {
            this.actual = coreSubscriber;
            this.resourceCleanup = consumer;
            this.resource = s;
            this.eager = z;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.wip == 1);
            }
            return attr == Scannable.Attr.PARENT ? this.f2227s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2227s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (WIP.compareAndSet(this, 0, 1)) {
                this.f2227s.cancel();
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
            if (Operators.validate(this.f2227s, subscription)) {
                this.f2227s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
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
            cleanup();
        }
    }

    static final class UsingFuseableSubscriber<T, S> implements InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        static final AtomicIntegerFieldUpdater<UsingFuseableSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(UsingFuseableSubscriber.class, "wip");
        final CoreSubscriber<? super T> actual;
        final boolean eager;
        int mode;
        final S resource;
        final Consumer<? super S> resourceCleanup;

        /* JADX INFO: renamed from: s */
        Fuseable.QueueSubscription<T> f2226s;
        volatile int wip;

        UsingFuseableSubscriber(CoreSubscriber<? super T> coreSubscriber, Consumer<? super S> consumer, S s, boolean z) {
            this.actual = coreSubscriber;
            this.resourceCleanup = consumer;
            this.resource = s;
            this.eager = z;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.wip == 1);
            }
            return attr == Scannable.Attr.PARENT ? this.f2226s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2226s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (WIP.compareAndSet(this, 0, 1)) {
                this.f2226s.cancel();
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
            if (Operators.validate(this.f2226s, subscription)) {
                this.f2226s = (Fuseable.QueueSubscription) subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
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
            cleanup();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2226s.clear();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2226s.isEmpty();
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            T tPoll = this.f2226s.poll();
            if (tPoll == null && this.mode == 1 && WIP.compareAndSet(this, 0, 1)) {
                this.resourceCleanup.accept(this.resource);
            }
            return tPoll;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            int iRequestFusion = this.f2226s.requestFusion(i);
            this.mode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2226s.size();
        }
    }

    static final class UsingConditionalSubscriber<T, S> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        static final AtomicIntegerFieldUpdater<UsingConditionalSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(UsingConditionalSubscriber.class, "wip");
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final boolean eager;
        final S resource;
        final Consumer<? super S> resourceCleanup;

        /* JADX INFO: renamed from: s */
        Subscription f2225s;
        volatile int wip;

        @Override // java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.Queue
        @Nullable
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

        UsingConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, Consumer<? super S> consumer, S s, boolean z) {
            this.actual = conditionalSubscriber;
            this.resourceCleanup = consumer;
            this.resource = s;
            this.eager = z;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.wip == 1);
            }
            return attr == Scannable.Attr.PARENT ? this.f2225s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2225s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (WIP.compareAndSet(this, 0, 1)) {
                this.f2225s.cancel();
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
            if (Operators.validate(this.f2225s, subscription)) {
                this.f2225s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            return this.actual.tryOnNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
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
            if (this.eager && WIP.compareAndSet(this, 0, 1)) {
                try {
                    this.resourceCleanup.accept(this.resource);
                } catch (Throwable th) {
                    Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber = this.actual;
                    conditionalSubscriber.onError(Operators.onOperatorError(th, conditionalSubscriber.currentContext()));
                    return;
                }
            }
            this.actual.onComplete();
            if (this.eager || !WIP.compareAndSet(this, 0, 1)) {
                return;
            }
            cleanup();
        }
    }
}
