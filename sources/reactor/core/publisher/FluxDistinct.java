package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDistinct<T, K, C> extends InternalFluxOperator<T, T> {
    final Consumer<C> cleanupCallback;
    final Supplier<C> collectionSupplier;
    final BiPredicate<C, K> distinctPredicate;
    final Function<? super T, ? extends K> keyExtractor;

    FluxDistinct(Flux<? extends T> flux, Function<? super T, ? extends K> function, Supplier<C> supplier, BiPredicate<C, K> biPredicate, Consumer<C> consumer) {
        super(flux);
        this.keyExtractor = (Function) Objects.requireNonNull(function, "keyExtractor");
        this.collectionSupplier = (Supplier) Objects.requireNonNull(supplier, "collectionSupplier");
        this.distinctPredicate = (BiPredicate) Objects.requireNonNull(biPredicate, "distinctPredicate");
        this.cleanupCallback = (Consumer) Objects.requireNonNull(consumer, "cleanupCallback");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        Object objRequireNonNull = Objects.requireNonNull(this.collectionSupplier.get(), "The collectionSupplier returned a null collection");
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new DistinctConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, objRequireNonNull, this.keyExtractor, this.distinctPredicate, this.cleanupCallback);
        }
        return new DistinctSubscriber(coreSubscriber, objRequireNonNull, this.keyExtractor, this.distinctPredicate, this.cleanupCallback);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class DistinctSubscriber<T, K, C> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Consumer<C> cleanupCallback;
        final C collection;
        final Context ctx;
        final BiPredicate<C, K> distinctPredicate;
        boolean done;
        final Function<? super T, ? extends K> keyExtractor;

        /* JADX INFO: renamed from: s */
        Subscription f2115s;

        DistinctSubscriber(CoreSubscriber<? super T> coreSubscriber, C c, Function<? super T, ? extends K> function, BiPredicate<C, K> biPredicate, Consumer<C> consumer) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.collection = c;
            this.keyExtractor = function;
            this.distinctPredicate = biPredicate;
            this.cleanupCallback = consumer;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2115s, subscription)) {
                this.f2115s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t)) {
                return;
            }
            this.f2115s.request(1L);
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return true;
            }
            try {
                try {
                    if (this.distinctPredicate.test(this.collection, (K) Objects.requireNonNull(this.keyExtractor.apply(t), "The distinct extractor returned a null value."))) {
                        this.actual.onNext(t);
                        return true;
                    }
                    Operators.onDiscard(t, this.ctx);
                    return false;
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2115s, th, t, this.ctx));
                    Operators.onDiscard(t, this.ctx);
                    return true;
                }
            } catch (Throwable th2) {
                onError(Operators.onOperatorError(this.f2115s, th2, t, this.ctx));
                Operators.onDiscard(t, this.ctx);
                return true;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.ctx);
                return;
            }
            this.done = true;
            this.cleanupCallback.accept(this.collection);
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.cleanupCallback.accept(this.collection);
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2115s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2115s.cancel();
            C c = this.collection;
            if (c != null) {
                this.cleanupCallback.accept(c);
            }
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2115s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static final class DistinctConditionalSubscriber<T, K, C> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final Consumer<C> cleanupCallback;
        final C collection;
        final Context ctx;
        final BiPredicate<C, K> distinctPredicate;
        boolean done;
        final Function<? super T, ? extends K> keyExtractor;

        /* JADX INFO: renamed from: s */
        Subscription f2113s;

        DistinctConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, C c, Function<? super T, ? extends K> function, BiPredicate<C, K> biPredicate, Consumer<C> consumer) {
            this.actual = conditionalSubscriber;
            this.ctx = conditionalSubscriber.currentContext();
            this.collection = c;
            this.keyExtractor = function;
            this.distinctPredicate = biPredicate;
            this.cleanupCallback = consumer;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2113s, subscription)) {
                this.f2113s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            try {
                try {
                    if (this.distinctPredicate.test(this.collection, (K) Objects.requireNonNull(this.keyExtractor.apply(t), "The distinct extractor returned a null value."))) {
                        this.actual.onNext(t);
                    } else {
                        Operators.onDiscard(t, this.ctx);
                        this.f2113s.request(1L);
                    }
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2113s, th, t, this.ctx));
                    Operators.onDiscard(t, this.ctx);
                }
            } catch (Throwable th2) {
                onError(Operators.onOperatorError(this.f2113s, th2, t, this.ctx));
                Operators.onDiscard(t, this.ctx);
            }
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return true;
            }
            try {
                try {
                    if (this.distinctPredicate.test(this.collection, (K) Objects.requireNonNull(this.keyExtractor.apply(t), "The distinct extractor returned a null value."))) {
                        return this.actual.tryOnNext(t);
                    }
                    Operators.onDiscard(t, this.ctx);
                    return false;
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2113s, th, t, this.ctx));
                    Operators.onDiscard(t, this.ctx);
                    return true;
                }
            } catch (Throwable th2) {
                onError(Operators.onOperatorError(this.f2113s, th2, t, this.ctx));
                Operators.onDiscard(t, this.ctx);
                return true;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.ctx);
                return;
            }
            this.done = true;
            this.cleanupCallback.accept(this.collection);
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.cleanupCallback.accept(this.collection);
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2113s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2113s.cancel();
            C c = this.collection;
            if (c != null) {
                this.cleanupCallback.accept(c);
            }
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2113s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static final class DistinctFuseableSubscriber<T, K, C> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Consumer<C> cleanupCallback;
        final C collection;
        final Context ctx;
        final BiPredicate<C, K> distinctPredicate;
        boolean done;
        final Function<? super T, ? extends K> keyExtractor;

        /* JADX INFO: renamed from: qs */
        Fuseable.QueueSubscription<T> f2114qs;
        int sourceMode;

        DistinctFuseableSubscriber(CoreSubscriber<? super T> coreSubscriber, C c, Function<? super T, ? extends K> function, BiPredicate<C, K> biPredicate, Consumer<C> consumer) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            this.collection = c;
            this.keyExtractor = function;
            this.distinctPredicate = biPredicate;
            this.cleanupCallback = consumer;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2114qs, subscription)) {
                this.f2114qs = (Fuseable.QueueSubscription) subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (tryOnNext(t)) {
                return;
            }
            this.f2114qs.request(1L);
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
                return true;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return true;
            }
            try {
                try {
                    if (this.distinctPredicate.test(this.collection, (K) Objects.requireNonNull(this.keyExtractor.apply(t), "The distinct extractor returned a null value."))) {
                        this.actual.onNext(t);
                        return true;
                    }
                    Operators.onDiscard(t, this.ctx);
                    return false;
                } catch (Throwable th) {
                    onError(Operators.onOperatorError(this.f2114qs, th, t, this.ctx));
                    Operators.onDiscard(t, this.ctx);
                    return true;
                }
            } catch (Throwable th2) {
                onError(Operators.onOperatorError(this.f2114qs, th2, t, this.ctx));
                Operators.onDiscard(t, this.ctx);
                return true;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.ctx);
                return;
            }
            this.done = true;
            this.cleanupCallback.accept(this.collection);
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.cleanupCallback.accept(this.collection);
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2114qs.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2114qs.cancel();
            C c = this.collection;
            if (c != null) {
                this.cleanupCallback.accept(c);
            }
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            int iRequestFusion = this.f2114qs.requestFusion(i);
            this.sourceMode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2114qs : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        /* JADX WARN: Code restructure failed: missing block: B:11:0x002a, code lost:
        
            if (r4 == 0) goto L13;
         */
        /* JADX WARN: Code restructure failed: missing block: B:12:0x002c, code lost:
        
            request(r4);
         */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x002f, code lost:
        
            return r6;
         */
        @Override // java.util.Queue
        @reactor.util.annotation.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public T poll() {
            /*
                r10 = this;
                int r0 = r10.sourceMode
                r1 = 2
                java.lang.String r2 = "The keyExtractor returned a null collection"
                r3 = 0
                if (r0 != r1) goto L40
                r0 = 0
                r4 = r0
            Lb:
                reactor.core.Fuseable$QueueSubscription<T> r6 = r10.f2114qs
                java.lang.Object r6 = r6.poll()
                if (r6 != 0) goto L14
                return r3
            L14:
                java.util.function.Function<? super T, ? extends K> r7 = r10.keyExtractor     // Catch: java.lang.Throwable -> L39
                java.lang.Object r7 = r7.apply(r6)     // Catch: java.lang.Throwable -> L39
                java.lang.Object r7 = java.util.Objects.requireNonNull(r7, r2)     // Catch: java.lang.Throwable -> L39
                java.util.function.BiPredicate<C, K> r8 = r10.distinctPredicate     // Catch: java.lang.Throwable -> L39
                C r9 = r10.collection     // Catch: java.lang.Throwable -> L39
                boolean r7 = r8.test(r9, r7)     // Catch: java.lang.Throwable -> L39
                if (r7 == 0) goto L30
                int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
                if (r0 == 0) goto L2f
                r10.request(r4)     // Catch: java.lang.Throwable -> L39
            L2f:
                return r6
            L30:
                reactor.util.context.Context r7 = r10.ctx     // Catch: java.lang.Throwable -> L39
                reactor.core.publisher.Operators.onDiscard(r6, r7)     // Catch: java.lang.Throwable -> L39
                r6 = 1
                long r4 = r4 + r6
                goto Lb
            L39:
                r0 = move-exception
                reactor.util.context.Context r1 = r10.ctx
                reactor.core.publisher.Operators.onDiscard(r6, r1)
                throw r0
            L40:
                reactor.core.Fuseable$QueueSubscription<T> r0 = r10.f2114qs
                java.lang.Object r0 = r0.poll()
                if (r0 != 0) goto L49
                return r3
            L49:
                java.util.function.Function<? super T, ? extends K> r1 = r10.keyExtractor     // Catch: java.lang.Throwable -> L64
                java.lang.Object r1 = r1.apply(r0)     // Catch: java.lang.Throwable -> L64
                java.lang.Object r1 = java.util.Objects.requireNonNull(r1, r2)     // Catch: java.lang.Throwable -> L64
                java.util.function.BiPredicate<C, K> r4 = r10.distinctPredicate     // Catch: java.lang.Throwable -> L64
                C r5 = r10.collection     // Catch: java.lang.Throwable -> L64
                boolean r1 = r4.test(r5, r1)     // Catch: java.lang.Throwable -> L64
                if (r1 == 0) goto L5e
                return r0
            L5e:
                reactor.util.context.Context r1 = r10.ctx     // Catch: java.lang.Throwable -> L64
                reactor.core.publisher.Operators.onDiscard(r0, r1)     // Catch: java.lang.Throwable -> L64
                goto L40
            L64:
                r1 = move-exception
                reactor.util.context.Context r2 = r10.ctx
                reactor.core.publisher.Operators.onDiscard(r0, r2)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.FluxDistinct.DistinctFuseableSubscriber.poll():java.lang.Object");
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2114qs.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2114qs.clear();
            this.cleanupCallback.accept(this.collection);
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2114qs.size();
        }
    }
}
