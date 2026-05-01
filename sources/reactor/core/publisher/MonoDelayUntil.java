package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoDelayUntil<T> extends Mono<T> implements Scannable, OptimizableOperator<T, T> {

    @Nullable
    final OptimizableOperator<?, T> optimizableOperator;
    Function<? super T, ? extends Publisher<?>>[] otherGenerators;
    final Mono<T> source;

    static boolean hasInner(int i) {
        return (i & 2) == 2;
    }

    static boolean hasRequest(int i) {
        return (i & 4) == 4;
    }

    static boolean hasSubscription(int i) {
        return (i & 1) == 1;
    }

    static boolean hasValue(int i) {
        return (i & 8) == 8;
    }

    static boolean isTerminated(int i) {
        return i == Integer.MIN_VALUE;
    }

    MonoDelayUntil(Mono<T> mono, Function<? super T, ? extends Publisher<?>> function) {
        MonoCacheInvalidateWhen monoCacheInvalidateWhen = (Mono<T>) ((Mono) Objects.requireNonNull(mono, "monoSource"));
        this.source = monoCacheInvalidateWhen;
        this.otherGenerators = new Function[]{(Function) Objects.requireNonNull(function, "triggerGenerator")};
        this.optimizableOperator = monoCacheInvalidateWhen instanceof OptimizableOperator ? monoCacheInvalidateWhen : null;
    }

    MonoDelayUntil(Mono<T> mono, Function<? super T, ? extends Publisher<?>>[] functionArr) {
        MonoCacheInvalidateWhen monoCacheInvalidateWhen = (Mono<T>) ((Mono) Objects.requireNonNull(mono, "monoSource"));
        this.source = monoCacheInvalidateWhen;
        this.otherGenerators = functionArr;
        if (monoCacheInvalidateWhen instanceof OptimizableOperator) {
            this.optimizableOperator = monoCacheInvalidateWhen;
        } else {
            this.optimizableOperator = null;
        }
    }

    MonoDelayUntil<T> copyWithNewTriggerGenerator(boolean z, Function<? super T, ? extends Publisher<?>> function) {
        Objects.requireNonNull(function, "triggerGenerator");
        Function<? super T, ? extends Publisher<?>>[] functionArr = this.otherGenerators;
        Function[] functionArr2 = new Function[functionArr.length + 1];
        System.arraycopy(functionArr, 0, functionArr2, 0, functionArr.length);
        functionArr2[functionArr.length] = function;
        return new MonoDelayUntil<>(this.source, functionArr2);
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        try {
            Operators.toFluxOrMono(this.source).subscribe((CoreSubscriber) subscribeOrReturn(coreSubscriber));
        } catch (Throwable th) {
            Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.OptimizableOperator
    public final CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) throws Throwable {
        DelayUntilCoordinator delayUntilCoordinator = new DelayUntilCoordinator(coreSubscriber, this.otherGenerators);
        coreSubscriber.onSubscribe(delayUntilCoordinator);
        return delayUntilCoordinator;
    }

    @Override // reactor.core.publisher.OptimizableOperator
    public final CorePublisher<? extends T> source() {
        return this.source;
    }

    @Override // reactor.core.publisher.OptimizableOperator
    public final OptimizableOperator<?, ? extends T> nextOptimizableSource() {
        return this.optimizableOperator;
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    static final class DelayUntilCoordinator<T> implements InnerOperator<T, T> {
        static final int HAS_INNER = 2;
        static final int HAS_REQUEST = 4;
        static final int HAS_SUBSCRIPTION = 1;
        static final int HAS_VALUE = 8;
        static final int TERMINATED = Integer.MIN_VALUE;
        final CoreSubscriber<? super T> actual;
        boolean done;
        volatile Throwable error;
        int index;
        final Function<? super T, ? extends Publisher<?>>[] otherGenerators;

        /* JADX INFO: renamed from: s */
        Subscription f2251s;
        volatile int state;
        DelayUntilTrigger<?> triggerSubscriber;
        T value;
        static final AtomicReferenceFieldUpdater<DelayUntilCoordinator, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(DelayUntilCoordinator.class, Throwable.class, "error");
        static final AtomicIntegerFieldUpdater<DelayUntilCoordinator> STATE = AtomicIntegerFieldUpdater.newUpdater(DelayUntilCoordinator.class, "state");

        DelayUntilCoordinator(CoreSubscriber<? super T> coreSubscriber, Function<? super T, ? extends Publisher<?>>[] functionArr) {
            this.actual = coreSubscriber;
            this.otherGenerators = functionArr;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2251s, subscription)) {
                this.f2251s = subscription;
                if (MonoDelayUntil.isTerminated(markHasSubscription())) {
                    subscription.cancel();
                } else {
                    subscription.request(Long.MAX_VALUE);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onDiscard(t, this.actual.currentContext());
            } else {
                this.value = t;
                subscribeNextTrigger();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            if (this.value == null) {
                this.actual.onError(th);
                return;
            }
            AtomicReferenceFieldUpdater<DelayUntilCoordinator, Throwable> atomicReferenceFieldUpdater = ERROR;
            if (!Exceptions.addThrowable(atomicReferenceFieldUpdater, this, th)) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            int iMarkTerminated = markTerminated();
            if (MonoDelayUntil.isTerminated(iMarkTerminated)) {
                return;
            }
            if (MonoDelayUntil.hasInner(iMarkTerminated)) {
                Operators.onDiscard(this.value, this.actual.currentContext());
                this.triggerSubscriber.cancel();
            }
            this.actual.onError(Exceptions.terminate(atomicReferenceFieldUpdater, this));
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (!this.done && this.value == null) {
                this.done = true;
                this.actual.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                int iMarkHasRequest = markHasRequest();
                if (MonoDelayUntil.isTerminated(iMarkHasRequest) || MonoDelayUntil.hasRequest(iMarkHasRequest) || !MonoDelayUntil.hasValue(iMarkHasRequest)) {
                    return;
                }
                this.done = true;
                CoreSubscriber<? super T> coreSubscriber = this.actual;
                coreSubscriber.onNext(this.value);
                coreSubscriber.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            int iMarkTerminated = markTerminated();
            if (MonoDelayUntil.isTerminated(iMarkTerminated)) {
                return;
            }
            Throwable thTerminate = Exceptions.terminate(ERROR, this);
            if (thTerminate != null) {
                Operators.onErrorDropped(thTerminate, this.actual.currentContext());
            }
            if (MonoDelayUntil.hasSubscription(iMarkTerminated)) {
                this.f2251s.cancel();
            }
            if (MonoDelayUntil.hasInner(iMarkTerminated)) {
                Operators.onDiscard(this.value, this.actual.currentContext());
                this.triggerSubscriber.cancel();
            }
        }

        void subscribeNextTrigger() {
            try {
                Publisher<?> publisherApply = this.otherGenerators[this.index].apply(this.value);
                Objects.requireNonNull(publisherApply, "mapper returned null value");
                DelayUntilTrigger<?> delayUntilTrigger = this.triggerSubscriber;
                if (delayUntilTrigger == null) {
                    delayUntilTrigger = new DelayUntilTrigger<>(this);
                    this.triggerSubscriber = delayUntilTrigger;
                }
                Operators.toFluxOrMono(publisherApply).subscribe((Subscriber) delayUntilTrigger);
            } catch (Throwable th) {
                onError(th);
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(MonoDelayUntil.isTerminated(this.state) && !this.done);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(MonoDelayUntil.isTerminated(this.state) && this.done);
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            DelayUntilTrigger<?> delayUntilTrigger = this.triggerSubscriber;
            return delayUntilTrigger == null ? Stream.empty() : Stream.of(delayUntilTrigger);
        }

        int markHasSubscription() {
            int i;
            do {
                i = this.state;
                if (MonoDelayUntil.isTerminated(i)) {
                    return Integer.MIN_VALUE;
                }
            } while (!STATE.compareAndSet(this, i, i | 1));
            return i;
        }

        int markHasRequest() {
            int i;
            do {
                i = this.state;
                if (MonoDelayUntil.isTerminated(i)) {
                    return Integer.MIN_VALUE;
                }
                if (MonoDelayUntil.hasRequest(i)) {
                    return i;
                }
            } while (!STATE.compareAndSet(this, i, MonoDelayUntil.hasValue(i) ? Integer.MIN_VALUE : i | 4));
            return i;
        }

        int markTerminated() {
            int i;
            do {
                i = this.state;
                if (MonoDelayUntil.isTerminated(i)) {
                    return Integer.MIN_VALUE;
                }
            } while (!STATE.compareAndSet(this, i, Integer.MIN_VALUE));
            return i;
        }

        void complete() {
            int i;
            do {
                i = this.state;
                if (MonoDelayUntil.isTerminated(i)) {
                    return;
                }
                if (MonoDelayUntil.hasRequest(i) && STATE.compareAndSet(this, i, Integer.MIN_VALUE)) {
                    CoreSubscriber<? super T> coreSubscriber = this.actual;
                    coreSubscriber.onNext(this.value);
                    coreSubscriber.onComplete();
                    return;
                }
            } while (!STATE.compareAndSet(this, i, i | 8));
        }
    }

    static final class DelayUntilTrigger<T> implements InnerConsumer<T> {
        boolean done;
        Throwable error;
        final DelayUntilCoordinator<?> parent;

        /* JADX INFO: renamed from: s */
        Subscription f2252s;

        DelayUntilTrigger(DelayUntilCoordinator<?> delayUntilCoordinator) {
            this.parent = delayUntilCoordinator;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(MonoDelayUntil.isTerminated(this.parent.state) && !this.done);
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.f2252s;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2252s, subscription)) {
                this.f2252s = subscription;
                if (MonoDelayUntil.isTerminated(markInnerActive())) {
                    subscription.cancel();
                    DelayUntilCoordinator<?> delayUntilCoordinator = this.parent;
                    Operators.onDiscard(delayUntilCoordinator.value, delayUntilCoordinator.currentContext());
                    return;
                }
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            Operators.onDiscard(t, this.parent.currentContext());
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.parent.currentContext());
                return;
            }
            DelayUntilCoordinator<?> delayUntilCoordinator = this.parent;
            this.done = true;
            delayUntilCoordinator.done = true;
            if (!Exceptions.addThrowable(DelayUntilCoordinator.ERROR, delayUntilCoordinator, th)) {
                Operators.onErrorDropped(th, delayUntilCoordinator.currentContext());
            } else {
                if (MonoDelayUntil.isTerminated(delayUntilCoordinator.markTerminated())) {
                    return;
                }
                Operators.onDiscard(delayUntilCoordinator.value, delayUntilCoordinator.currentContext());
                delayUntilCoordinator.f2251s.cancel();
                delayUntilCoordinator.actual.onError(Exceptions.terminate(DelayUntilCoordinator.ERROR, delayUntilCoordinator));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            DelayUntilCoordinator<?> delayUntilCoordinator = this.parent;
            int i = delayUntilCoordinator.index + 1;
            delayUntilCoordinator.index = i;
            if (i == delayUntilCoordinator.otherGenerators.length) {
                delayUntilCoordinator.complete();
            } else {
                if (MonoDelayUntil.isTerminated(markInnerInactive())) {
                    return;
                }
                this.done = false;
                this.f2252s = null;
                delayUntilCoordinator.subscribeNextTrigger();
            }
        }

        void cancel() {
            this.f2252s.cancel();
        }

        int markInnerActive() {
            int i;
            DelayUntilCoordinator<?> delayUntilCoordinator = this.parent;
            do {
                i = delayUntilCoordinator.state;
                if (MonoDelayUntil.isTerminated(i)) {
                    return Integer.MIN_VALUE;
                }
                if (MonoDelayUntil.hasInner(i)) {
                    return i;
                }
            } while (!DelayUntilCoordinator.STATE.compareAndSet(delayUntilCoordinator, i, i | 2));
            return i;
        }

        int markInnerInactive() {
            int i;
            DelayUntilCoordinator<?> delayUntilCoordinator = this.parent;
            do {
                i = delayUntilCoordinator.state;
                if (MonoDelayUntil.isTerminated(i)) {
                    return Integer.MIN_VALUE;
                }
                if (!MonoDelayUntil.hasInner(i)) {
                    return i;
                }
            } while (!DelayUntilCoordinator.STATE.compareAndSet(delayUntilCoordinator, i, i & (-3)));
            return i;
        }
    }
}
