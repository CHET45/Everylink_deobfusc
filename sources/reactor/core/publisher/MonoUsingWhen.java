package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.FluxUsingWhen;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoUsingWhen<T, S> extends Mono<T> implements SourceProducer<T> {

    @Nullable
    final Function<? super S, ? extends Publisher<?>> asyncCancel;
    final Function<? super S, ? extends Publisher<?>> asyncComplete;
    final BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError;
    final Function<? super S, ? extends Mono<? extends T>> resourceClosure;
    final Publisher<S> resourceSupplier;

    MonoUsingWhen(Publisher<S> publisher, Function<? super S, ? extends Mono<? extends T>> function, Function<? super S, ? extends Publisher<?>> function2, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> biFunction, @Nullable Function<? super S, ? extends Publisher<?>> function3) {
        this.resourceSupplier = (Publisher) Objects.requireNonNull(publisher, "resourceSupplier");
        this.resourceClosure = (Function) Objects.requireNonNull(function, "resourceClosure");
        this.asyncComplete = (Function) Objects.requireNonNull(function2, "asyncComplete");
        this.asyncError = (BiFunction) Objects.requireNonNull(biFunction, "asyncError");
        this.asyncCancel = function3;
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Publisher<S> publisher = this.resourceSupplier;
        if (publisher instanceof Callable) {
            try {
                Object objCall = ((Callable) publisher).call();
                if (objCall == null) {
                    Operators.complete(coreSubscriber);
                } else {
                    Mono monoDeriveMonoFromResource = deriveMonoFromResource(objCall, this.resourceClosure);
                    fromDirect(monoDeriveMonoFromResource).subscribe((CoreSubscriber) prepareSubscriberForResource(objCall, coreSubscriber, this.asyncComplete, this.asyncError, this.asyncCancel, null));
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
    public static <RESOURCE, T> Mono<? extends T> deriveMonoFromResource(RESOURCE resource, Function<? super RESOURCE, ? extends Mono<? extends T>> function) {
        try {
            return (Mono) Objects.requireNonNull(function.apply(resource), "The resourceClosure function returned a null value");
        } catch (Throwable th) {
            return Mono.error(th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <RESOURCE, T> MonoUsingWhenSubscriber<? super T, RESOURCE> prepareSubscriberForResource(RESOURCE resource, CoreSubscriber<? super T> coreSubscriber, Function<? super RESOURCE, ? extends Publisher<?>> function, BiFunction<? super RESOURCE, ? super Throwable, ? extends Publisher<?>> biFunction, @Nullable Function<? super RESOURCE, ? extends Publisher<?>> function2, @Nullable Operators.DeferredSubscription deferredSubscription) {
        return new MonoUsingWhenSubscriber<>(coreSubscriber, resource, function, biFunction, function2, deferredSubscription);
    }

    static class ResourceSubscriber<S, T> extends Operators.DeferredSubscription implements InnerConsumer<S> {
        final CoreSubscriber<? super T> actual;

        @Nullable
        final Function<? super S, ? extends Publisher<?>> asyncCancel;
        final Function<? super S, ? extends Publisher<?>> asyncComplete;
        final BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> asyncError;
        final boolean isMonoSource;
        final Function<? super S, ? extends Mono<? extends T>> resourceClosure;
        boolean resourceProvided;
        Subscription resourceSubscription;

        ResourceSubscriber(CoreSubscriber<? super T> coreSubscriber, Function<? super S, ? extends Mono<? extends T>> function, Function<? super S, ? extends Publisher<?>> function2, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> biFunction, @Nullable Function<? super S, ? extends Publisher<?>> function3, boolean z) {
            this.actual = (CoreSubscriber) Objects.requireNonNull(coreSubscriber, "actual");
            this.resourceClosure = (Function) Objects.requireNonNull(function, "resourceClosure");
            this.asyncComplete = (Function) Objects.requireNonNull(function2, "asyncComplete");
            this.asyncError = (BiFunction) Objects.requireNonNull(biFunction, "asyncError");
            this.asyncCancel = function3;
            this.isMonoSource = z;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(S s) {
            if (this.resourceProvided) {
                Operators.onNextDropped(s, this.actual.currentContext());
                return;
            }
            this.resourceProvided = true;
            Mono.fromDirect(MonoUsingWhen.deriveMonoFromResource(s, this.resourceClosure)).subscribe((CoreSubscriber) MonoUsingWhen.prepareSubscriberForResource(s, this.actual, this.asyncComplete, this.asyncError, this.asyncCancel, this));
            if (this.isMonoSource) {
                return;
            }
            this.resourceSubscription.cancel();
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

    static class MonoUsingWhenSubscriber<T, S> extends FluxUsingWhen.UsingWhenSubscriber<T, S> {
        T value;

        MonoUsingWhenSubscriber(CoreSubscriber<? super T> coreSubscriber, S s, Function<? super S, ? extends Publisher<?>> function, BiFunction<? super S, ? super Throwable, ? extends Publisher<?>> biFunction, @Nullable Function<? super S, ? extends Publisher<?>> function2, @Nullable Operators.DeferredSubscription deferredSubscription) {
            super(coreSubscriber, s, function, biFunction, function2, deferredSubscription);
        }

        @Override // reactor.core.publisher.FluxUsingWhen.UsingWhenSubscriber, org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.value = t;
        }

        @Override // reactor.core.publisher.FluxUsingWhen.UsingWhenSubscriber, reactor.core.publisher.FluxUsingWhen.UsingWhenParent
        public void deferredComplete() {
            this.error = Exceptions.TERMINATED;
            if (this.value != null) {
                this.actual.onNext(this.value);
            }
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.FluxUsingWhen.UsingWhenSubscriber, reactor.core.publisher.FluxUsingWhen.UsingWhenParent
        public void deferredError(Throwable th) {
            Operators.onDiscard(this.value, this.actual.currentContext());
            this.error = th;
            this.actual.onError(th);
        }
    }
}
