package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Predicate;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoCacheInvalidateIf<T> extends InternalMonoOperator<T, T> {
    static final State<?> EMPTY_STATE = new State<Object>() { // from class: reactor.core.publisher.MonoCacheInvalidateIf.1
        @Override // reactor.core.publisher.MonoCacheInvalidateIf.State
        public void clear() {
        }

        @Override // reactor.core.publisher.MonoCacheInvalidateIf.State
        @Nullable
        public Object get() {
            return null;
        }
    };
    static final AtomicReferenceFieldUpdater<MonoCacheInvalidateIf, State> STATE = AtomicReferenceFieldUpdater.newUpdater(MonoCacheInvalidateIf.class, State.class, "state");
    final Predicate<? super T> shouldInvalidatePredicate;
    volatile State<T> state;

    interface State<T> {
        void clear();

        @Nullable
        T get();
    }

    static final class ValueState<T> implements State<T> {

        @Nullable
        T value;

        ValueState(T t) {
            this.value = t;
        }

        @Override // reactor.core.publisher.MonoCacheInvalidateIf.State
        @Nullable
        public T get() {
            return this.value;
        }

        @Override // reactor.core.publisher.MonoCacheInvalidateIf.State
        public void clear() {
            this.value = null;
        }
    }

    MonoCacheInvalidateIf(Mono<T> mono, Predicate<? super T> predicate) {
        super(mono);
        this.shouldInvalidatePredicate = (Predicate) Objects.requireNonNull(predicate, "invalidationPredicate");
        this.state = (State<T>) EMPTY_STATE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0043, code lost:
    
        r8.onSubscribe(r0);
        r0.complete(r1.get());
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004d, code lost:
    
        return null;
     */
    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public reactor.core.CoreSubscriber<? super T> subscribeOrReturn(reactor.core.CoreSubscriber<? super T> r8) {
        /*
            r7 = this;
            reactor.core.publisher.MonoCacheInvalidateIf$CacheMonoSubscriber r0 = new reactor.core.publisher.MonoCacheInvalidateIf$CacheMonoSubscriber
            r0.<init>(r8)
        L5:
            reactor.core.publisher.MonoCacheInvalidateIf$State<T> r1 = r7.state
            reactor.core.publisher.MonoCacheInvalidateIf$State<?> r2 = reactor.core.publisher.MonoCacheInvalidateIf.EMPTY_STATE
            r3 = 0
            if (r1 == r2) goto L4e
            boolean r4 = r1 instanceof reactor.core.publisher.MonoCacheInvalidateIf.CoordinatorSubscriber
            if (r4 == 0) goto L11
            goto L4e
        L11:
            java.lang.Object r4 = r1.get()
            java.util.function.Predicate<? super T> r5 = r7.shouldInvalidatePredicate     // Catch: java.lang.Throwable -> L2d
            boolean r5 = r5.test(r4)     // Catch: java.lang.Throwable -> L2d
            if (r5 == 0) goto L43
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater<reactor.core.publisher.MonoCacheInvalidateIf, reactor.core.publisher.MonoCacheInvalidateIf$State> r5 = reactor.core.publisher.MonoCacheInvalidateIf.STATE     // Catch: java.lang.Throwable -> L2d
            boolean r2 = androidx.concurrent.futures.C0162xc40028dd.m5m(r5, r7, r1, r2)     // Catch: java.lang.Throwable -> L2d
            if (r2 == 0) goto L5
            reactor.util.context.Context r2 = r8.currentContext()     // Catch: java.lang.Throwable -> L2d
            reactor.core.publisher.Operators.onDiscard(r4, r2)     // Catch: java.lang.Throwable -> L2d
            goto L5
        L2d:
            r2 = move-exception
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater<reactor.core.publisher.MonoCacheInvalidateIf, reactor.core.publisher.MonoCacheInvalidateIf$State> r5 = reactor.core.publisher.MonoCacheInvalidateIf.STATE
            reactor.core.publisher.MonoCacheInvalidateIf$State<?> r6 = reactor.core.publisher.MonoCacheInvalidateIf.EMPTY_STATE
            boolean r5 = androidx.concurrent.futures.C0162xc40028dd.m5m(r5, r7, r1, r6)
            if (r5 == 0) goto L43
            reactor.util.context.Context r0 = r8.currentContext()
            reactor.core.publisher.Operators.onDiscard(r4, r0)
            reactor.core.publisher.Operators.error(r8, r2)
            return r3
        L43:
            r8.onSubscribe(r0)
            java.lang.Object r8 = r1.get()
            r0.complete(r8)
            return r3
        L4e:
            if (r1 != r2) goto L62
            reactor.core.publisher.MonoCacheInvalidateIf$CoordinatorSubscriber r1 = new reactor.core.publisher.MonoCacheInvalidateIf$CoordinatorSubscriber
            reactor.core.publisher.Mono<? extends I> r4 = r7.source
            r1.<init>(r7, r4)
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater<reactor.core.publisher.MonoCacheInvalidateIf, reactor.core.publisher.MonoCacheInvalidateIf$State> r4 = reactor.core.publisher.MonoCacheInvalidateIf.STATE
            boolean r2 = androidx.concurrent.futures.C0162xc40028dd.m5m(r4, r7, r2, r1)
            if (r2 != 0) goto L60
            goto L5
        L60:
            r2 = 1
            goto L65
        L62:
            reactor.core.publisher.MonoCacheInvalidateIf$CoordinatorSubscriber r1 = (reactor.core.publisher.MonoCacheInvalidateIf.CoordinatorSubscriber) r1
            r2 = 0
        L65:
            boolean r4 = r1.add(r0)
            if (r4 == 0) goto L5
            boolean r4 = r0.isCancelled()
            if (r4 == 0) goto L75
            r1.remove(r0)
            goto L7a
        L75:
            r0.coordinator = r1
            r8.onSubscribe(r0)
        L7a:
            if (r2 == 0) goto L7f
            r1.delayedSubscribe()
        L7f:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.MonoCacheInvalidateIf.subscribeOrReturn(reactor.core.CoreSubscriber):reactor.core.CoreSubscriber");
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class CoordinatorSubscriber<T> implements InnerConsumer<T>, State<T> {
        final MonoCacheInvalidateIf<T> main;
        final Mono<? extends T> source;
        volatile Subscription upstream;
        static final AtomicReferenceFieldUpdater<CoordinatorSubscriber, Subscription> UPSTREAM = AtomicReferenceFieldUpdater.newUpdater(CoordinatorSubscriber.class, Subscription.class, "upstream");
        static final AtomicReferenceFieldUpdater<CoordinatorSubscriber, CacheMonoSubscriber[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(CoordinatorSubscriber.class, CacheMonoSubscriber[].class, "subscribers");
        private static final CacheMonoSubscriber[] COORDINATOR_DONE = new CacheMonoSubscriber[0];
        private static final CacheMonoSubscriber[] COORDINATOR_INIT = new CacheMonoSubscriber[0];
        boolean done = false;
        volatile CacheMonoSubscriber<T>[] subscribers = COORDINATOR_INIT;

        @Override // reactor.core.publisher.MonoCacheInvalidateIf.State
        public void clear() {
        }

        CoordinatorSubscriber(MonoCacheInvalidateIf<T> monoCacheInvalidateIf, Mono<? extends T> mono) {
            this.main = monoCacheInvalidateIf;
            this.source = mono;
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
                        Subscription andSet = UPSTREAM.getAndSet(this, Operators.cancelledSubscription());
                        if (andSet != null) {
                            andSet.cancel();
                        }
                        C0162xc40028dd.m5m(MonoCacheInvalidateIf.STATE, this.main, this, MonoCacheInvalidateIf.EMPTY_STATE);
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

        void delayedSubscribe() {
            Subscription andSet = UPSTREAM.getAndSet(this, null);
            if (andSet != null && andSet != Operators.cancelledSubscription()) {
                andSet.cancel();
            }
            if (SUBSCRIBERS.get(this) != COORDINATOR_DONE) {
                this.source.subscribe((CoreSubscriber<? super Object>) this);
            }
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (C0162xc40028dd.m5m(UPSTREAM, this, null, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.main.state != this || this.done) {
                Operators.onNextDropped(t, currentContext());
                return;
            }
            this.done = true;
            if (C0162xc40028dd.m5m(MonoCacheInvalidateIf.STATE, this.main, this, new ValueState(t))) {
                for (CacheMonoSubscriber cacheMonoSubscriber : SUBSCRIBERS.getAndSet(this, COORDINATOR_DONE)) {
                    cacheMonoSubscriber.complete(t);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.main.state != this || this.done) {
                Operators.onErrorDropped(th, currentContext());
                return;
            }
            if (C0162xc40028dd.m5m(MonoCacheInvalidateIf.STATE, this.main, this, MonoCacheInvalidateIf.EMPTY_STATE)) {
                for (CacheMonoSubscriber cacheMonoSubscriber : SUBSCRIBERS.getAndSet(this, COORDINATOR_DONE)) {
                    cacheMonoSubscriber.onError(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                this.done = false;
                return;
            }
            if (C0162xc40028dd.m5m(MonoCacheInvalidateIf.STATE, this.main, this, MonoCacheInvalidateIf.EMPTY_STATE)) {
                for (CacheMonoSubscriber cacheMonoSubscriber : SUBSCRIBERS.getAndSet(this, COORDINATOR_DONE)) {
                    cacheMonoSubscriber.onError(new NoSuchElementException("cacheInvalidateWhen expects a value, source completed empty"));
                }
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
}
