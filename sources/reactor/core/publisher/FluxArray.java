package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxArray<T> extends Flux<T> implements Fuseable, SourceProducer<T> {
    final T[] array;

    @SafeVarargs
    public FluxArray(T... tArr) {
        this.array = (T[]) ((Object[]) Objects.requireNonNull(tArr, "array"));
    }

    public static <T> void subscribe(CoreSubscriber<? super T> coreSubscriber, T[] tArr) {
        if (tArr.length == 0) {
            Operators.complete(coreSubscriber);
        } else if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            coreSubscriber.onSubscribe(new ArrayConditionalSubscription((Fuseable.ConditionalSubscriber) coreSubscriber, tArr));
        } else {
            coreSubscriber.onSubscribe(new ArraySubscription(coreSubscriber, tArr));
        }
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        subscribe(coreSubscriber, this.array);
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.BUFFERED ? Integer.valueOf(this.array.length) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ArraySubscription<T> implements InnerProducer<T>, Fuseable.SynchronousSubscription<T> {
        static final AtomicLongFieldUpdater<ArraySubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(ArraySubscription.class, "requested");
        final CoreSubscriber<? super T> actual;
        final T[] array;
        volatile boolean cancelled;
        int index;
        volatile long requested;

        ArraySubscription(CoreSubscriber<? super T> coreSubscriber, T[] tArr) {
            this.actual = coreSubscriber;
            this.array = tArr;
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

        void slowPath(long j) {
            T[] tArr = this.array;
            int length = tArr.length;
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            int i = this.index;
            do {
                int i2 = 0;
                while (!this.cancelled) {
                    while (i != length && i2 != j) {
                        T t = tArr[i];
                        if (t == null) {
                            coreSubscriber.onError(new NullPointerException("The " + i + "th array element was null"));
                            return;
                        }
                        coreSubscriber.onNext(t);
                        if (this.cancelled) {
                            return;
                        }
                        i++;
                        i2++;
                    }
                    if (i == length) {
                        coreSubscriber.onComplete();
                        return;
                    }
                    j = this.requested;
                    if (j == i2) {
                        this.index = i;
                        j = REQUESTED.addAndGet(this, -i2);
                    }
                }
                return;
            } while (j != 0);
        }

        void fastPath() {
            T[] tArr = this.array;
            int length = tArr.length;
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            for (int i = this.index; i != length; i++) {
                if (this.cancelled) {
                    return;
                }
                T t = tArr[i];
                if (t == null) {
                    coreSubscriber.onError(new NullPointerException("The " + i + "th array element was null"));
                    return;
                }
                coreSubscriber.onNext(t);
            }
            if (this.cancelled) {
                return;
            }
            coreSubscriber.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            int i = this.index;
            T[] tArr = this.array;
            if (i == tArr.length) {
                return null;
            }
            T t = tArr[i];
            Objects.requireNonNull(t);
            this.index = i + 1;
            return t;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.index == this.array.length;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // java.util.Collection
        public void clear() {
            this.index = this.array.length;
        }

        @Override // java.util.Collection
        public int size() {
            return this.array.length - this.index;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(isEmpty()) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(size()) : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : super.scanUnsafe(attr);
        }
    }

    static final class ArrayConditionalSubscription<T> implements InnerProducer<T>, Fuseable.SynchronousSubscription<T> {
        static final AtomicLongFieldUpdater<ArrayConditionalSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(ArrayConditionalSubscription.class, "requested");
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final T[] array;
        volatile boolean cancelled;
        int index;
        volatile long requested;

        ArrayConditionalSubscription(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, T[] tArr) {
            this.actual = conditionalSubscriber;
            this.array = tArr;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
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

        void slowPath(long j) {
            T[] tArr = this.array;
            int length = tArr.length;
            Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber = this.actual;
            int i = this.index;
            do {
                int i2 = 0;
                while (!this.cancelled) {
                    while (i != length && i2 != j) {
                        T t = tArr[i];
                        if (t == null) {
                            conditionalSubscriber.onError(new NullPointerException("The " + i + "th array element was null"));
                            return;
                        }
                        boolean zTryOnNext = conditionalSubscriber.tryOnNext(t);
                        if (this.cancelled) {
                            return;
                        }
                        i++;
                        if (zTryOnNext) {
                            i2++;
                        }
                    }
                    if (i == length) {
                        conditionalSubscriber.onComplete();
                        return;
                    }
                    j = this.requested;
                    if (j == i2) {
                        this.index = i;
                        j = REQUESTED.addAndGet(this, -i2);
                    }
                }
                return;
            } while (j != 0);
        }

        void fastPath() {
            T[] tArr = this.array;
            int length = tArr.length;
            Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber = this.actual;
            for (int i = this.index; i != length; i++) {
                if (this.cancelled) {
                    return;
                }
                T t = tArr[i];
                if (t == null) {
                    conditionalSubscriber.onError(new NullPointerException("The " + i + "th array element was null"));
                    return;
                }
                conditionalSubscriber.onNext(t);
            }
            if (this.cancelled) {
                return;
            }
            conditionalSubscriber.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(isEmpty()) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(size()) : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : super.scanUnsafe(attr);
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            int i = this.index;
            T[] tArr = this.array;
            if (i == tArr.length) {
                return null;
            }
            T t = (T) Objects.requireNonNull(tArr[i], "Array returned null value");
            this.index = i + 1;
            return t;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.index == this.array.length;
        }

        @Override // java.util.Collection
        public void clear() {
            this.index = this.array.length;
        }

        @Override // java.util.Collection
        public int size() {
            return this.array.length - this.index;
        }
    }
}
