package reactor.core.publisher;

import android.Manifest;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;

/* JADX INFO: loaded from: classes5.dex */
final class FluxIterable<T> extends Flux<T> implements Fuseable, SourceProducer<T> {
    final Iterable<? extends T> iterable;

    @Nullable
    private final Runnable onClose = null;

    static <T> boolean checkFinite(Spliterator<? extends T> spliterator) {
        return spliterator.hasCharacteristics(64);
    }

    FluxIterable(Iterable<? extends T> iterable) {
        this.iterable = (Iterable) Objects.requireNonNull(iterable, "iterable");
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        try {
            Spliterator<? extends T> spliterator = this.iterable.spliterator();
            subscribe(coreSubscriber, spliterator, checkFinite(spliterator), this.onClose);
        } catch (Throwable th) {
            Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.BUFFERED) {
            Iterable<? extends T> iterable = this.iterable;
            if (iterable instanceof Collection) {
                return Integer.valueOf(((Collection) iterable).size());
            }
            if (iterable instanceof Tuple2) {
                return Integer.valueOf(((Tuple2) iterable).size());
            }
        }
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(attr);
    }

    static <T> void subscribe(CoreSubscriber<? super T> coreSubscriber, Spliterator<? extends T> spliterator, boolean z) {
        subscribe(coreSubscriber, spliterator, z, (Runnable) null);
    }

    static <T> void subscribe(CoreSubscriber<? super T> coreSubscriber, Spliterator<? extends T> spliterator, boolean z, @Nullable Runnable runnable) {
        if (spliterator == null) {
            Operators.error(coreSubscriber, new NullPointerException("The iterator is null"));
            return;
        }
        if (z) {
            try {
                if (spliterator.estimateSize() == 0) {
                    Operators.complete(coreSubscriber);
                    if (runnable != null) {
                        try {
                            runnable.run();
                            return;
                        } catch (Throwable th) {
                            Operators.onErrorDropped(th, coreSubscriber.currentContext());
                            return;
                        }
                    }
                    return;
                }
            } catch (Throwable th2) {
                Operators.error(coreSubscriber, Operators.onOperatorError(th2, coreSubscriber.currentContext()));
                if (runnable != null) {
                    try {
                        runnable.run();
                        return;
                    } catch (Throwable th3) {
                        Operators.onErrorDropped(th3, coreSubscriber.currentContext());
                        return;
                    }
                }
                return;
            }
        }
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            IterableSubscriptionConditional iterableSubscriptionConditional = new IterableSubscriptionConditional((Fuseable.ConditionalSubscriber) coreSubscriber, spliterator, z, runnable);
            try {
                if (!iterableSubscriptionConditional.hasNext()) {
                    Operators.complete(coreSubscriber);
                    iterableSubscriptionConditional.onCloseWithDropError();
                    return;
                } else {
                    coreSubscriber.onSubscribe(iterableSubscriptionConditional);
                    return;
                }
            } catch (Throwable th4) {
                Operators.error(coreSubscriber, th4);
                iterableSubscriptionConditional.onCloseWithDropError();
                return;
            }
        }
        IterableSubscription iterableSubscription = new IterableSubscription(coreSubscriber, spliterator, z, runnable);
        try {
            if (!iterableSubscription.hasNext()) {
                Operators.complete(coreSubscriber);
                iterableSubscription.onCloseWithDropError();
            } else {
                coreSubscriber.onSubscribe(iterableSubscription);
            }
        } catch (Throwable th5) {
            Operators.error(coreSubscriber, th5);
            iterableSubscription.onCloseWithDropError();
        }
    }

    static final class IterableSubscription<T> implements InnerProducer<T>, Fuseable.SynchronousSubscription<T>, Consumer<T> {
        static final AtomicLongFieldUpdater<IterableSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(IterableSubscription.class, "requested");
        static final int STATE_CALL_HAS_NEXT = 3;
        static final int STATE_HAS_NEXT_HAS_VALUE = 1;
        static final int STATE_HAS_NEXT_NO_VALUE = 0;
        static final int STATE_NO_NEXT = 2;
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        T current;
        Throwable hasNextFailure;
        final boolean knownToBeFinite;
        T nextElement;
        final Runnable onClose;
        volatile long requested;
        final Spliterator<? extends T> spliterator;
        int state;
        boolean valueReady;

        IterableSubscription(CoreSubscriber<? super T> coreSubscriber, Spliterator<? extends T> spliterator, boolean z, @Nullable Runnable runnable) {
            this.valueReady = false;
            this.actual = coreSubscriber;
            this.spliterator = spliterator;
            this.knownToBeFinite = z;
            this.onClose = runnable;
        }

        IterableSubscription(CoreSubscriber<? super T> coreSubscriber, Spliterator<? extends T> spliterator, boolean z) {
            this(coreSubscriber, spliterator, z, null);
        }

        @Override // java.util.function.Consumer
        public void accept(T t) {
            this.valueReady = true;
            this.nextElement = t;
        }

        boolean hasNext() {
            if (!this.valueReady) {
                this.spliterator.tryAdvance(this);
            }
            return this.valueReady;
        }

        T next() {
            if (!this.valueReady && !hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            T t = this.nextElement;
            this.nextElement = null;
            return t;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j) && Operators.addCap(REQUESTED, this, j) == 0) {
                if (j == Long.MAX_VALUE) {
                    fastPath();
                } else {
                    slowPath(j);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onCloseWithDropError() {
            Runnable runnable = this.onClose;
            if (runnable != null) {
                try {
                    runnable.run();
                } catch (Throwable th) {
                    Operators.onErrorDropped(th, this.actual.currentContext());
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:29:0x004c, code lost:
        
            r8 = reactor.core.publisher.FluxIterable.IterableSubscription.REQUESTED.addAndGet(r7, -r3);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void slowPath(long r8) {
            /*
                r7 = this;
                reactor.core.CoreSubscriber<? super T> r0 = r7.actual
                r1 = 0
            L4:
                r3 = r1
            L5:
                int r5 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
                if (r5 == 0) goto L46
                java.lang.Object r5 = r7.next()     // Catch: java.lang.Throwable -> L3e
                java.lang.String r6 = "The iterator returned a null value"
                java.lang.Object r5 = java.util.Objects.requireNonNull(r5, r6)     // Catch: java.lang.Throwable -> L3e
                boolean r6 = r7.cancelled
                if (r6 == 0) goto L18
                return
            L18:
                r0.onNext(r5)
                boolean r5 = r7.cancelled
                if (r5 == 0) goto L20
                return
            L20:
                boolean r5 = r7.hasNext()     // Catch: java.lang.Throwable -> L36
                boolean r6 = r7.cancelled
                if (r6 == 0) goto L29
                return
            L29:
                if (r5 != 0) goto L32
                r0.onComplete()
                r7.onCloseWithDropError()
                return
            L32:
                r5 = 1
                long r3 = r3 + r5
                goto L5
            L36:
                r8 = move-exception
                r0.onError(r8)
                r7.onCloseWithDropError()
                return
            L3e:
                r8 = move-exception
                r0.onError(r8)
                r7.onCloseWithDropError()
                return
            L46:
                long r8 = r7.requested
                int r5 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
                if (r5 != 0) goto L5
                java.util.concurrent.atomic.AtomicLongFieldUpdater<reactor.core.publisher.FluxIterable$IterableSubscription> r8 = reactor.core.publisher.FluxIterable.IterableSubscription.REQUESTED
                long r3 = -r3
                long r8 = r8.addAndGet(r7, r3)
                int r3 = (r8 > r1 ? 1 : (r8 == r1 ? 0 : -1))
                if (r3 != 0) goto L4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.FluxIterable.IterableSubscription.slowPath(long):void");
        }

        void fastPath() {
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            while (!this.cancelled) {
                try {
                    Manifest manifest = (Object) Objects.requireNonNull(next(), "The iterator returned a null value");
                    if (this.cancelled) {
                        return;
                    }
                    coreSubscriber.onNext(manifest);
                    if (this.cancelled) {
                        return;
                    }
                    try {
                        boolean zHasNext = hasNext();
                        if (this.cancelled) {
                            return;
                        }
                        if (!zHasNext) {
                            coreSubscriber.onComplete();
                            onCloseWithDropError();
                            return;
                        }
                    } catch (Exception e) {
                        coreSubscriber.onError(e);
                        onCloseWithDropError();
                        return;
                    }
                } catch (Exception e2) {
                    coreSubscriber.onError(e2);
                    onCloseWithDropError();
                    return;
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            onCloseWithDropError();
            this.cancelled = true;
            Operators.onDiscard(this.nextElement, this.actual.currentContext());
            Operators.onDiscardMultiple(this.spliterator, this.knownToBeFinite, this.actual.currentContext());
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.state == 2);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // java.util.Collection
        public void clear() {
            Operators.onDiscard(this.nextElement, this.actual.currentContext());
            Operators.onDiscardMultiple(this.spliterator, this.knownToBeFinite, this.actual.currentContext());
            this.state = 2;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            int i = this.state;
            if (i == 2) {
                return true;
            }
            if (this.cancelled && !this.knownToBeFinite) {
                return true;
            }
            if (i != 1 && i != 0) {
                try {
                    if (hasNext()) {
                        this.state = 0;
                        return false;
                    }
                    this.state = 2;
                    return true;
                } catch (Throwable th) {
                    this.state = 0;
                    this.hasNextFailure = th;
                }
            }
            return false;
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            T next;
            Throwable th = this.hasNextFailure;
            if (th != null) {
                this.state = 2;
                throw Exceptions.propagate(th);
            }
            if (!isEmpty()) {
                if (this.state == 0) {
                    next = next();
                } else {
                    next = this.current;
                    this.current = null;
                }
                this.state = 3;
                if (next != null) {
                    return next;
                }
                onCloseWithDropError();
                throw new NullPointerException("iterator returned a null value");
            }
            onCloseWithDropError();
            return null;
        }

        @Override // java.util.Collection
        public int size() {
            return this.state == 2 ? 0 : 1;
        }
    }

    static final class IterableSubscriptionConditional<T> implements InnerProducer<T>, Fuseable.SynchronousSubscription<T>, Consumer<T> {
        static final AtomicLongFieldUpdater<IterableSubscriptionConditional> REQUESTED = AtomicLongFieldUpdater.newUpdater(IterableSubscriptionConditional.class, "requested");
        static final int STATE_CALL_HAS_NEXT = 3;
        static final int STATE_HAS_NEXT_HAS_VALUE = 1;
        static final int STATE_HAS_NEXT_NO_VALUE = 0;
        static final int STATE_NO_NEXT = 2;
        final Fuseable.ConditionalSubscriber<? super T> actual;
        volatile boolean cancelled;
        T current;
        Throwable hasNextFailure;
        final boolean knownToBeFinite;
        T nextElement;
        final Runnable onClose;
        volatile long requested;
        final Spliterator<? extends T> spliterator;
        int state;
        boolean valueReady;

        IterableSubscriptionConditional(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, Spliterator<? extends T> spliterator, boolean z, @Nullable Runnable runnable) {
            this.valueReady = false;
            this.actual = conditionalSubscriber;
            this.spliterator = spliterator;
            this.knownToBeFinite = z;
            this.onClose = runnable;
        }

        IterableSubscriptionConditional(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, Spliterator<? extends T> spliterator, boolean z) {
            this(conditionalSubscriber, spliterator, z, null);
        }

        @Override // java.util.function.Consumer
        public void accept(T t) {
            this.valueReady = true;
            this.nextElement = t;
        }

        boolean hasNext() {
            if (!this.valueReady) {
                this.spliterator.tryAdvance(this);
            }
            return this.valueReady;
        }

        T next() {
            if (!this.valueReady && !hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            T t = this.nextElement;
            this.nextElement = null;
            return t;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j) && Operators.addCap(REQUESTED, this, j) == 0) {
                if (j == Long.MAX_VALUE) {
                    fastPath();
                } else {
                    slowPath(j);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onCloseWithDropError() {
            Runnable runnable = this.onClose;
            if (runnable != null) {
                try {
                    runnable.run();
                } catch (Throwable th) {
                    Operators.onErrorDropped(th, this.actual.currentContext());
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x004f, code lost:
        
            r9 = reactor.core.publisher.FluxIterable.IterableSubscriptionConditional.REQUESTED.addAndGet(r8, -r3);
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void slowPath(long r9) {
            /*
                r8 = this;
                reactor.core.Fuseable$ConditionalSubscriber<? super T> r0 = r8.actual
                r1 = 0
            L4:
                r3 = r1
            L5:
                int r5 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
                if (r5 == 0) goto L49
                java.lang.Object r5 = r8.next()     // Catch: java.lang.Throwable -> L41
                java.lang.String r6 = "The iterator returned a null value"
                java.lang.Object r5 = java.util.Objects.requireNonNull(r5, r6)     // Catch: java.lang.Throwable -> L41
                boolean r6 = r8.cancelled
                if (r6 == 0) goto L18
                return
            L18:
                boolean r5 = r0.tryOnNext(r5)
                boolean r6 = r8.cancelled
                if (r6 == 0) goto L21
                return
            L21:
                boolean r6 = r8.hasNext()     // Catch: java.lang.Throwable -> L39
                boolean r7 = r8.cancelled
                if (r7 == 0) goto L2a
                return
            L2a:
                if (r6 != 0) goto L33
                r0.onComplete()
                r8.onCloseWithDropError()
                return
            L33:
                if (r5 == 0) goto L5
                r5 = 1
                long r3 = r3 + r5
                goto L5
            L39:
                r9 = move-exception
                r0.onError(r9)
                r8.onCloseWithDropError()
                return
            L41:
                r9 = move-exception
                r0.onError(r9)
                r8.onCloseWithDropError()
                return
            L49:
                long r9 = r8.requested
                int r5 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
                if (r5 != 0) goto L5
                java.util.concurrent.atomic.AtomicLongFieldUpdater<reactor.core.publisher.FluxIterable$IterableSubscriptionConditional> r9 = reactor.core.publisher.FluxIterable.IterableSubscriptionConditional.REQUESTED
                long r3 = -r3
                long r9 = r9.addAndGet(r8, r3)
                int r3 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
                if (r3 != 0) goto L4
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.FluxIterable.IterableSubscriptionConditional.slowPath(long):void");
        }

        void fastPath() {
            Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber = this.actual;
            while (!this.cancelled) {
                try {
                    Manifest manifest = (Object) Objects.requireNonNull(next(), "The iterator returned a null value");
                    if (this.cancelled) {
                        return;
                    }
                    conditionalSubscriber.tryOnNext(manifest);
                    if (this.cancelled) {
                        return;
                    }
                    try {
                        boolean zHasNext = hasNext();
                        if (this.cancelled) {
                            return;
                        }
                        if (!zHasNext) {
                            conditionalSubscriber.onComplete();
                            onCloseWithDropError();
                            return;
                        }
                    } catch (Exception e) {
                        conditionalSubscriber.onError(e);
                        onCloseWithDropError();
                        return;
                    }
                } catch (Exception e2) {
                    conditionalSubscriber.onError(e2);
                    onCloseWithDropError();
                    return;
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            onCloseWithDropError();
            this.cancelled = true;
            Operators.onDiscard(this.nextElement, this.actual.currentContext());
            Operators.onDiscardMultiple(this.spliterator, this.knownToBeFinite, this.actual.currentContext());
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.state == 2);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // java.util.Collection
        public void clear() {
            Operators.onDiscard(this.nextElement, this.actual.currentContext());
            Operators.onDiscardMultiple(this.spliterator, this.knownToBeFinite, this.actual.currentContext());
            this.state = 2;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            int i = this.state;
            if (i == 2) {
                return true;
            }
            if (this.cancelled && !this.knownToBeFinite) {
                return true;
            }
            if (i != 1 && i != 0) {
                try {
                    if (hasNext()) {
                        this.state = 0;
                        return false;
                    }
                    this.state = 2;
                    return true;
                } catch (Throwable th) {
                    this.state = 0;
                    this.hasNextFailure = th;
                }
            }
            return false;
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            T next;
            Throwable th = this.hasNextFailure;
            if (th != null) {
                this.state = 2;
                throw Exceptions.propagate(th);
            }
            if (!isEmpty()) {
                if (this.state == 0) {
                    next = next();
                } else {
                    next = this.current;
                    this.current = null;
                }
                this.state = 3;
                return next;
            }
            onCloseWithDropError();
            return null;
        }

        @Override // java.util.Collection
        public int size() {
            return this.state == 2 ? 0 : 1;
        }
    }
}
