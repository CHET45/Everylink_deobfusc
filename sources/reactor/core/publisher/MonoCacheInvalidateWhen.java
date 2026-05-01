package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.function.Function;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.MonoCacheInvalidateIf;
import reactor.core.publisher.Operators;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoCacheInvalidateWhen<T> extends InternalMonoOperator<T, T> {
    private static final Logger LOGGER = Loggers.getLogger((Class<?>) MonoCacheInvalidateWhen.class);
    static final AtomicReferenceFieldUpdater<MonoCacheInvalidateWhen, MonoCacheInvalidateIf.State> STATE = AtomicReferenceFieldUpdater.newUpdater(MonoCacheInvalidateWhen.class, MonoCacheInvalidateIf.State.class, "state");

    @Nullable
    final Consumer<? super T> invalidateHandler;
    final Function<? super T, Mono<Void>> invalidationTriggerGenerator;
    volatile MonoCacheInvalidateIf.State<T> state;

    MonoCacheInvalidateWhen(Mono<T> mono, Function<? super T, Mono<Void>> function, @Nullable Consumer<? super T> consumer) {
        super(mono);
        this.invalidationTriggerGenerator = (Function) Objects.requireNonNull(function, "invalidationTriggerGenerator");
        this.invalidateHandler = consumer;
        this.state = (MonoCacheInvalidateIf.State<T>) MonoCacheInvalidateIf.EMPTY_STATE;
    }

    boolean compareAndInvalidate(MonoCacheInvalidateIf.State<T> state) {
        if (!C0162xc40028dd.m5m(STATE, this, state, MonoCacheInvalidateIf.EMPTY_STATE)) {
            return false;
        }
        if (!(state instanceof MonoCacheInvalidateIf.ValueState)) {
            return true;
        }
        LOGGER.trace("invalidated {}", state.get());
        safeInvalidateHandler(state.get());
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    void invalidate() {
        MonoCacheInvalidateIf.State andSet = STATE.getAndSet(this, MonoCacheInvalidateIf.EMPTY_STATE);
        if (andSet instanceof MonoCacheInvalidateIf.ValueState) {
            LOGGER.trace("invalidated {}", andSet.get());
            safeInvalidateHandler(andSet.get());
        }
    }

    void safeInvalidateHandler(@Nullable final T t) {
        Consumer<? super T> consumer;
        if (t == null || (consumer = this.invalidateHandler) == null) {
            return;
        }
        try {
            consumer.accept(t);
        } catch (Throwable th) {
            LOGGER.warnOrDebug(new Logger.ChoiceOfMessageSupplier() { // from class: reactor.core.publisher.MonoCacheInvalidateWhen$$ExternalSyntheticLambda0
                @Override // reactor.util.Logger.ChoiceOfMessageSupplier
                public final String get(boolean z) {
                    return MonoCacheInvalidateWhen.lambda$safeInvalidateHandler$0(t, z);
                }
            }, th);
        }
    }

    static /* synthetic */ String lambda$safeInvalidateHandler$0(Object obj, boolean z) {
        return z ? "Failed to apply invalidate handler on value " + obj : "Failed to apply invalidate handler";
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        CoordinatorSubscriber<T> coordinatorSubscriber;
        boolean z;
        CacheMonoSubscriber<T> cacheMonoSubscriber = new CacheMonoSubscriber<>(coreSubscriber);
        coreSubscriber.onSubscribe(cacheMonoSubscriber);
        while (true) {
            MonoCacheInvalidateIf.State<T> state = this.state;
            if (state == MonoCacheInvalidateIf.EMPTY_STATE || (state instanceof CoordinatorSubscriber)) {
                if (state == MonoCacheInvalidateIf.EMPTY_STATE) {
                    coordinatorSubscriber = new CoordinatorSubscriber<>(this);
                    if (C0162xc40028dd.m5m(STATE, this, MonoCacheInvalidateIf.EMPTY_STATE, coordinatorSubscriber)) {
                        z = true;
                    } else {
                        continue;
                    }
                } else {
                    coordinatorSubscriber = (CoordinatorSubscriber) state;
                    z = false;
                }
                if (coordinatorSubscriber.add(cacheMonoSubscriber)) {
                    if (cacheMonoSubscriber.isCancelled()) {
                        coordinatorSubscriber.remove(cacheMonoSubscriber);
                    } else {
                        cacheMonoSubscriber.coordinator = coordinatorSubscriber;
                    }
                    if (z) {
                        this.source.subscribe((CoreSubscriber<? super Object>) coordinatorSubscriber);
                    }
                    return null;
                }
            } else {
                cacheMonoSubscriber.complete(state.get());
                return null;
            }
        }
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class CoordinatorSubscriber<T> implements InnerConsumer<T>, MonoCacheInvalidateIf.State<T> {
        final MonoCacheInvalidateWhen<T> main;
        volatile CacheMonoSubscriber<T>[] subscribers = COORDINATOR_INIT;
        Subscription subscription;
        static final AtomicReferenceFieldUpdater<CoordinatorSubscriber, CacheMonoSubscriber[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(CoordinatorSubscriber.class, CacheMonoSubscriber[].class, "subscribers");
        private static final CacheMonoSubscriber[] COORDINATOR_DONE = new CacheMonoSubscriber[0];
        private static final CacheMonoSubscriber[] COORDINATOR_INIT = new CacheMonoSubscriber[0];

        @Override // reactor.core.publisher.MonoCacheInvalidateIf.State
        public void clear() {
        }

        CoordinatorSubscriber(MonoCacheInvalidateWhen<T> monoCacheInvalidateWhen) {
            this.main = monoCacheInvalidateWhen;
        }

        @Override // reactor.core.publisher.MonoCacheInvalidateIf.State
        @Nullable
        public T get() {
            throw new UnsupportedOperationException("coordinator State#get shouldn't be used");
        }

        final boolean add(CacheMonoSubscriber<T> cacheMonoSubscriber) {
            CacheMonoSubscriber<T>[] cacheMonoSubscriberArr;
            CacheMonoSubscriber[] cacheMonoSubscriberArr2;
            do {
                cacheMonoSubscriberArr = this.subscribers;
                if (cacheMonoSubscriberArr == COORDINATOR_DONE) {
                    return false;
                }
                int length = cacheMonoSubscriberArr.length;
                cacheMonoSubscriberArr2 = new CacheMonoSubscriber[length + 1];
                System.arraycopy(cacheMonoSubscriberArr, 0, cacheMonoSubscriberArr2, 0, length);
                cacheMonoSubscriberArr2[length] = cacheMonoSubscriber;
            } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, cacheMonoSubscriberArr, cacheMonoSubscriberArr2));
            return true;
        }

        final void remove(CacheMonoSubscriber<T> cacheMonoSubscriber) {
            while (true) {
                CacheMonoSubscriber<T>[] cacheMonoSubscriberArr = this.subscribers;
                if (cacheMonoSubscriberArr == COORDINATOR_DONE || cacheMonoSubscriberArr == COORDINATOR_INIT) {
                    return;
                }
                int length = cacheMonoSubscriberArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        i = -1;
                        break;
                    } else if (cacheMonoSubscriberArr[i] == cacheMonoSubscriber) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    if (C0162xc40028dd.m5m(SUBSCRIBERS, this, cacheMonoSubscriberArr, COORDINATOR_DONE)) {
                        if (this.main.compareAndInvalidate(this)) {
                            this.subscription.cancel();
                            return;
                        }
                        return;
                    }
                } else {
                    CacheMonoSubscriber[] cacheMonoSubscriberArr2 = new CacheMonoSubscriber[length - 1];
                    System.arraycopy(cacheMonoSubscriberArr, 0, cacheMonoSubscriberArr2, 0, i);
                    System.arraycopy(cacheMonoSubscriberArr, i + 1, cacheMonoSubscriberArr2, i, (length - i) - 1);
                    if (C0162xc40028dd.m5m(SUBSCRIBERS, this, cacheMonoSubscriberArr, cacheMonoSubscriberArr2)) {
                        return;
                    }
                }
            }
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.subscription, subscription)) {
                this.subscription = subscription;
                subscription.request(Long.MAX_VALUE);
            }
        }

        boolean cacheLoadFailure(MonoCacheInvalidateIf.State<T> state, Throwable th) {
            if (!C0162xc40028dd.m5m(MonoCacheInvalidateWhen.STATE, this.main, state, MonoCacheInvalidateIf.EMPTY_STATE)) {
                return false;
            }
            for (CacheMonoSubscriber cacheMonoSubscriber : SUBSCRIBERS.getAndSet(this, COORDINATOR_DONE)) {
                cacheMonoSubscriber.onError(th);
            }
            return true;
        }

        void cacheLoad(T t) {
            MonoCacheInvalidateIf.ValueState valueState = new MonoCacheInvalidateIf.ValueState(t);
            if (C0162xc40028dd.m5m(MonoCacheInvalidateWhen.STATE, this.main, this, valueState)) {
                try {
                    Mono mono = (Mono) Objects.requireNonNull(this.main.invalidationTriggerGenerator.apply(t), "invalidationTriggerGenerator produced a null trigger");
                    for (CacheMonoSubscriber cacheMonoSubscriber : SUBSCRIBERS.getAndSet(this, COORDINATOR_DONE)) {
                        cacheMonoSubscriber.complete(t);
                    }
                    mono.subscribe((CoreSubscriber) new TriggerSubscriber(this.main));
                } catch (Throwable th) {
                    if (cacheLoadFailure(valueState, th)) {
                        this.main.safeInvalidateHandler(t);
                    }
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.main.state != this) {
                Operators.onNextDroppedMulticast(t, this.subscribers);
            } else {
                cacheLoad(t);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.main.state != this) {
                Operators.onErrorDroppedMulticast(th, this.subscribers);
            } else {
                cacheLoadFailure(this, th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.main.state == this) {
                cacheLoadFailure(this, new NoSuchElementException("cacheInvalidateWhen expects a value, source completed empty"));
            }
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return Operators.multiSubscribersContext(this.subscribers);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class CacheMonoSubscriber<T> extends Operators.MonoSubscriber<T, T> {
        CoordinatorSubscriber<T> coordinator;

        CacheMonoSubscriber(CoreSubscriber<? super T> coreSubscriber) {
            super(coreSubscriber);
        }

        @Override // reactor.core.publisher.Operators.MonoSubscriber, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            CoordinatorSubscriber<T> coordinatorSubscriber = this.coordinator;
            if (coordinatorSubscriber != null) {
                coordinatorSubscriber.remove(this);
            }
        }

        @Override // reactor.core.publisher.Operators.MonoSubscriber, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.coordinator.main : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static final class TriggerSubscriber implements InnerConsumer<Void> {
        final MonoCacheInvalidateWhen<?> main;

        @Override // org.reactivestreams.Subscriber
        public void onNext(Void r1) {
        }

        TriggerSubscriber(MonoCacheInvalidateWhen<?> monoCacheInvalidateWhen) {
            this.main = monoCacheInvalidateWhen;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            subscription.request(1L);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            MonoCacheInvalidateWhen.LOGGER.debug("Invalidation triggered by onError(" + th + ")");
            this.main.invalidate();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.main.invalidate();
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return Context.empty();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.main;
            }
            boolean z = true;
            if (attr != Scannable.Attr.TERMINATED) {
                return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == Scannable.Attr.PREFETCH ? 1 : null;
            }
            if (this.main.state != MonoCacheInvalidateIf.EMPTY_STATE && !(this.main.state instanceof CoordinatorSubscriber)) {
                z = false;
            }
            return Boolean.valueOf(z);
        }
    }
}
