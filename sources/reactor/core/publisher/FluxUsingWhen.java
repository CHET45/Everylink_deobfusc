package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxUsingWhen<T, S> extends Flux<T> implements SourceProducer<T> {

    @Nullable
    final Function<? super S, ? extends Publisher<?>> asyncCancel;
    final Function<? super S, ? extends Publisher<?>> asyncComplete;
    final BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError;
    final Function<? super S, ? extends Publisher<? extends T>> resourceClosure;
    final Publisher<S> resourceSupplier;

    private interface UsingWhenParent<T> extends InnerOperator<T, T> {
        void deferredComplete();

        void deferredError(Throwable th);
    }

    FluxUsingWhen(Publisher<S> publisher, Function<? super S, ? extends Publisher<? extends T>> function, Function<? super S, ? extends Publisher<?>> function2, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> biFunction, @Nullable Function<? super S, ? extends Publisher<?>> function3) {
        this.resourceSupplier = (Publisher) Objects.requireNonNull(publisher, "resourceSupplier");
        this.resourceClosure = (Function) Objects.requireNonNull(function, "resourceClosure");
        this.asyncComplete = (Function) Objects.requireNonNull(function2, "asyncComplete");
        this.asyncError = (BiFunction) Objects.requireNonNull(biFunction, "asyncError");
        this.asyncCancel = function3;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Publisher<S> publisher = this.resourceSupplier;
        if (publisher instanceof Callable) {
            try {
                Object objCall = ((Callable) publisher).call();
                if (objCall == null) {
                    Operators.complete(coreSubscriber);
                } else {
                    Publisher publisherDeriveFluxFromResource = deriveFluxFromResource(objCall, this.resourceClosure);
                    Operators.toFluxOrMono(publisherDeriveFluxFromResource).subscribe((Subscriber) prepareSubscriberForResource(objCall, coreSubscriber, this.asyncComplete, this.asyncError, this.asyncCancel, null));
                }
                return;
            } catch (Throwable th) {
                Operators.error(coreSubscriber, th);
                return;
            }
        }
        Operators.toFluxOrMono(publisher).subscribe((Subscriber) new ResourceSubscriber(coreSubscriber, this.resourceClosure, this.asyncComplete, this.asyncError, this.asyncCancel, this.resourceSupplier instanceof Mono));
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <RESOURCE, T> Publisher<? extends T> deriveFluxFromResource(RESOURCE resource, Function<? super RESOURCE, ? extends Publisher<? extends T>> function) {
        try {
            return (Publisher) Objects.requireNonNull(function.apply(resource), "The resourceClosure function returned a null value");
        } catch (Throwable th) {
            return Flux.error(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <RESOURCE, T> UsingWhenSubscriber<? super T, RESOURCE> prepareSubscriberForResource(RESOURCE resource, CoreSubscriber<? super T> coreSubscriber, Function<? super RESOURCE, ? extends Publisher<?>> function, BiFunction<? super RESOURCE, ? super Throwable, ? extends Publisher<?>> biFunction, @Nullable Function<? super RESOURCE, ? extends Publisher<?>> function2, @Nullable Operators.DeferredSubscription deferredSubscription) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new UsingWhenConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, resource, function, biFunction, function2, deferredSubscription);
        }
        return new UsingWhenSubscriber<>(coreSubscriber, resource, function, biFunction, function2, deferredSubscription);
    }

    static class ResourceSubscriber<S, T> extends Operators.DeferredSubscription implements InnerConsumer<S> {
        final CoreSubscriber<? super T> actual;

        @Nullable
        final Function<? super S, ? extends Publisher<?>> asyncCancel;
        final Function<? super S, ? extends Publisher<?>> asyncComplete;
        final BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError;
        final boolean isMonoSource;
        final Function<? super S, ? extends Publisher<? extends T>> resourceClosure;
        boolean resourceProvided;
        Subscription resourceSubscription;

        ResourceSubscriber(CoreSubscriber<? super T> coreSubscriber, Function<? super S, ? extends Publisher<? extends T>> function, Function<? super S, ? extends Publisher<?>> function2, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> biFunction, @Nullable Function<? super S, ? extends Publisher<?>> function3, boolean z) {
            this.actual = (CoreSubscriber) Objects.requireNonNull(coreSubscriber, "actual");
            this.resourceClosure = (Function) Objects.requireNonNull(function, "resourceClosure");
            this.asyncComplete = (Function) Objects.requireNonNull(function2, "asyncComplete");
            this.asyncError = (BiFunction) Objects.requireNonNull(biFunction, "asyncError");
            this.asyncCancel = function3;
            this.isMonoSource = z;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(S s) {
            if (this.resourceProvided) {
                Operators.onNextDropped(s, this.actual.currentContext());
                return;
            }
            this.resourceProvided = true;
            Operators.toFluxOrMono(FluxUsingWhen.deriveFluxFromResource(s, this.resourceClosure)).subscribe((Subscriber) FluxUsingWhen.prepareSubscriberForResource(s, this.actual, this.asyncComplete, this.asyncError, this.asyncCancel, this));
            if (this.isMonoSource) {
                return;
            }
            this.resourceSubscription.cancel();
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.resourceProvided) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.resourceProvided) {
                return;
            }
            this.actual.onComplete();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.resourceSubscription, subscription)) {
                this.resourceSubscription = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // reactor.core.publisher.Operators.DeferredSubscription, org.reactivestreams.Subscription
        public void cancel() {
            if (!this.resourceProvided) {
                this.resourceSubscription.cancel();
            }
            super.cancel();
        }

        @Override // reactor.core.publisher.Operators.DeferredSubscription, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.resourceSubscription;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.resourceProvided);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static class UsingWhenSubscriber<T, S> implements UsingWhenParent<T> {
        static final AtomicIntegerFieldUpdater<UsingWhenSubscriber> CALLBACK_APPLIED = AtomicIntegerFieldUpdater.newUpdater(UsingWhenSubscriber.class, "callbackApplied");
        final CoreSubscriber<? super T> actual;

        @Nullable
        final Operators.DeferredSubscription arbiter;

        @Nullable
        final Function<? super S, ? extends Publisher<?>> asyncCancel;
        final Function<? super S, ? extends Publisher<?>> asyncComplete;
        final BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError;
        volatile int callbackApplied;
        Throwable error;
        final S resource;

        /* JADX INFO: renamed from: s */
        Subscription f2228s;

        UsingWhenSubscriber(CoreSubscriber<? super T> coreSubscriber, S s, Function<? super S, ? extends Publisher<?>> function, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> biFunction, @Nullable Function<? super S, ? extends Publisher<?>> function2, @Nullable Operators.DeferredSubscription deferredSubscription) {
            this.actual = coreSubscriber;
            this.resource = s;
            this.asyncComplete = function;
            this.asyncError = biFunction;
            this.asyncCancel = function2;
            this.arbiter = deferredSubscription;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.error != null);
            }
            if (attr == Scannable.Attr.ERROR) {
                if (this.error == Exceptions.TERMINATED) {
                    return null;
                }
                return this.error;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.callbackApplied == 3);
            }
            return attr == Scannable.Attr.PARENT ? this.f2228s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                this.f2228s.request(j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (CALLBACK_APPLIED.compareAndSet(this, 0, 3)) {
                this.f2228s.cancel();
                try {
                    Function<? super S, ? extends Publisher<?>> function = this.asyncCancel;
                    if (function != null) {
                        Flux.from(function.apply(this.resource)).subscribe((CoreSubscriber) new CancelInner(this));
                    } else {
                        Flux.from(this.asyncComplete.apply(this.resource)).subscribe((CoreSubscriber) new CancelInner(this));
                    }
                } catch (Throwable th) {
                    Loggers.getLogger((Class<?>) FluxUsingWhen.class).warn("Error generating async resource cleanup during onCancel", th);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (CALLBACK_APPLIED.compareAndSet(this, 0, 2)) {
                try {
                    Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.asyncError.apply(this.resource, th), "The asyncError returned a null Publisher")).subscribe((Subscriber) new RollbackInner(this, th));
                } catch (Throwable th2) {
                    this.actual.onError(Exceptions.addSuppressed(Operators.onOperatorError(th2, this.actual.currentContext()), th));
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (CALLBACK_APPLIED.compareAndSet(this, 0, 1)) {
                try {
                    Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.asyncComplete.apply(this.resource), "The asyncComplete returned a null Publisher")).subscribe((Subscriber) new CommitInner(this));
                } catch (Throwable th) {
                    deferredError(Operators.onOperatorError(th, this.actual.currentContext()));
                }
            }
        }

        @Override // reactor.core.publisher.FluxUsingWhen.UsingWhenParent
        public void deferredComplete() {
            this.error = Exceptions.TERMINATED;
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.FluxUsingWhen.UsingWhenParent
        public void deferredError(Throwable th) {
            this.error = th;
            this.actual.onError(th);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2228s, subscription)) {
                this.f2228s = subscription;
                Operators.DeferredSubscription deferredSubscription = this.arbiter;
                if (deferredSubscription == null) {
                    this.actual.onSubscribe(this);
                } else {
                    deferredSubscription.set(this);
                }
            }
        }
    }

    static final class UsingWhenConditionalSubscriber<T, S> extends UsingWhenSubscriber<T, S> implements Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;

        UsingWhenConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, S s, Function<? super S, ? extends Publisher<?>> function, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> biFunction, @Nullable Function<? super S, ? extends Publisher<?>> function2, @Nullable Operators.DeferredSubscription deferredSubscription) {
            super(conditionalSubscriber, s, function, biFunction, function2, deferredSubscription);
            this.actual = conditionalSubscriber;
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            return this.actual.tryOnNext(t);
        }
    }

    static final class RollbackInner implements InnerConsumer<Object> {
        boolean done;
        final UsingWhenParent parent;
        final Throwable rollbackCause;

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
        }

        RollbackInner(UsingWhenParent usingWhenParent, Throwable th) {
            this.parent = usingWhenParent;
            this.rollbackCause = th;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            ((Subscription) Objects.requireNonNull(subscription, "Subscription cannot be null")).request(Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.done = true;
            this.parent.deferredError(Exceptions.addSuppressed(new RuntimeException("Async resource cleanup failed after onError", th), this.rollbackCause));
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.deferredError(this.rollbackCause);
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent.actual();
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.rollbackCause;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class CommitInner implements InnerConsumer<Object> {
        boolean done;
        final UsingWhenParent parent;

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
        }

        CommitInner(UsingWhenParent usingWhenParent) {
            this.parent = usingWhenParent;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            ((Subscription) Objects.requireNonNull(subscription, "Subscription cannot be null")).request(Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.done = true;
            this.parent.deferredError(new RuntimeException("Async resource cleanup failed after onComplete", Operators.onOperatorError(th, this.parent.currentContext())));
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.deferredComplete();
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent.actual();
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class CancelInner implements InnerConsumer<Object> {
        final UsingWhenParent parent;

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
        }

        CancelInner(UsingWhenParent usingWhenParent) {
            this.parent = usingWhenParent;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            ((Subscription) Objects.requireNonNull(subscription, "Subscription cannot be null")).request(Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Loggers.getLogger((Class<?>) FluxUsingWhen.class).warn("Async resource cleanup failed after cancel", th);
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent.actual();
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }
}
