package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxZip;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoZip<T, R> extends Mono<R> implements SourceProducer<R> {
    final boolean delayError;
    final Mono<?>[] sources;
    final Iterable<? extends Mono<?>> sourcesIterable;
    final Function<? super Object[], ? extends R> zipper;

    <U> MonoZip(boolean z, Mono<? extends T> mono, Mono<? extends U> mono2, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        this(z, new FluxZip.PairwiseZipper(new BiFunction[]{(BiFunction) Objects.requireNonNull(biFunction, "zipper2")}), (Mono<?>[]) new Mono[]{(Mono) Objects.requireNonNull(mono, "p1"), (Mono) Objects.requireNonNull(mono2, "p2")});
    }

    MonoZip(boolean z, Function<? super Object[], ? extends R> function, Mono<?>... monoArr) {
        this.delayError = z;
        this.zipper = (Function) Objects.requireNonNull(function, "zipper");
        this.sources = (Mono[]) Objects.requireNonNull(monoArr, "sources");
        this.sourcesIterable = null;
    }

    MonoZip(boolean z, Function<? super Object[], ? extends R> function, Iterable<? extends Mono<?>> iterable) {
        this.delayError = z;
        this.zipper = (Function) Objects.requireNonNull(function, "zipper");
        this.sources = null;
        this.sourcesIterable = (Iterable) Objects.requireNonNull(iterable, "sourcesIterable");
    }

    @Nullable
    Mono<R> zipAdditionalSource(Mono mono, BiFunction biFunction) {
        Mono<?>[] monoArr = this.sources;
        if (monoArr == null || !(this.zipper instanceof FluxZip.PairwiseZipper)) {
            return null;
        }
        int length = monoArr.length;
        Mono[] monoArr2 = new Mono[length + 1];
        System.arraycopy(monoArr, 0, monoArr2, 0, length);
        monoArr2[length] = mono;
        return new MonoZip(this.delayError, ((FluxZip.PairwiseZipper) this.zipper).then(biFunction), (Mono<?>[]) monoArr2);
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super R> coreSubscriber) {
        Mono<?>[] monoArr;
        int length;
        Mono<?>[] monoArr2 = this.sources;
        if (monoArr2 != null) {
            monoArr = monoArr2;
            length = monoArr2.length;
        } else {
            Mono<?>[] monoArr3 = new Mono[8];
            int i = 0;
            for (Mono<?> mono : this.sourcesIterable) {
                if (i == monoArr3.length) {
                    Mono<?>[] monoArr4 = new Mono[(i >> 2) + i];
                    System.arraycopy(monoArr3, 0, monoArr4, 0, i);
                    monoArr3 = monoArr4;
                }
                monoArr3[i] = mono;
                i++;
            }
            monoArr = monoArr3;
            length = i;
        }
        if (length == 0) {
            Operators.complete(coreSubscriber);
            return;
        }
        for (int i2 = 0; i2 < length; i2++) {
            monoArr[i2] = Mono.fromDirect(monoArr[i2]);
        }
        coreSubscriber.onSubscribe(new ZipCoordinator(monoArr, coreSubscriber, length, this.delayError, this.zipper));
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.DELAY_ERROR ? Boolean.valueOf(this.delayError) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ZipCoordinator<R> implements InnerProducer<R>, Fuseable, Fuseable.QueueSubscription<R> {
        static final long INTERRUPTED_FLAG = Long.MIN_VALUE;
        static final long MAX_SIGNALS_VALUE = 2147483647L;
        static final long REQUESTED_ONCE_FLAG = 4611686018427387904L;
        static final AtomicLongFieldUpdater<ZipCoordinator> STATE = AtomicLongFieldUpdater.newUpdater(ZipCoordinator.class, "state");
        final CoreSubscriber<? super R> actual;
        final boolean delayError;
        final Mono<?>[] sources;
        volatile long state;
        final ZipInner<R>[] subscribers;
        final Function<? super Object[], ? extends R> zipper;

        static int deliveredSignals(long j) {
            return (int) (j & MAX_SIGNALS_VALUE);
        }

        static boolean isInterrupted(long j) {
            return (j & Long.MIN_VALUE) == Long.MIN_VALUE;
        }

        static boolean isRequestedOnce(long j) {
            return (j & REQUESTED_ONCE_FLAG) == REQUESTED_ONCE_FLAG;
        }

        @Override // java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.Queue
        public R poll() {
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

        ZipCoordinator(Mono<?>[] monoArr, CoreSubscriber<? super R> coreSubscriber, int i, boolean z, Function<? super Object[], ? extends R> function) {
            this.sources = monoArr;
            this.actual = coreSubscriber;
            this.delayError = z;
            this.zipper = function;
            ZipInner<R>[] zipInnerArr = new ZipInner[i];
            this.subscribers = zipInnerArr;
            for (int i2 = 0; i2 < i; i2++) {
                zipInnerArr[i2] = new ZipInner<>(this);
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(deliveredSignals(this.state) == this.subscribers.length);
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.subscribers.length);
            }
            if (attr == Scannable.Attr.DELAY_ERROR) {
                return Boolean.valueOf(this.delayError);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                long j = this.state;
                return Boolean.valueOf(isInterrupted(j) && deliveredSignals(j) != this.subscribers.length);
            }
            return super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            long jMarkRequestedOnce = markRequestedOnce(this);
            if (isRequestedOnce(jMarkRequestedOnce) || isInterrupted(jMarkRequestedOnce)) {
                return;
            }
            Mono<?>[] monoArr = this.sources;
            ZipInner<R>[] zipInnerArr = this.subscribers;
            for (int i = 0; i < this.subscribers.length; i++) {
                monoArr[i].subscribe((CoreSubscriber<? super Object>) zipInnerArr[i]);
            }
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers);
        }

        boolean signal() {
            ZipInner<R>[] zipInnerArr = this.subscribers;
            int length = zipInnerArr.length;
            long jMarkDeliveredSignal = markDeliveredSignal(this);
            int iDeliveredSignals = deliveredSignals(jMarkDeliveredSignal);
            if (isInterrupted(jMarkDeliveredSignal) || iDeliveredSignals == length) {
                return false;
            }
            if (iDeliveredSignals + 1 != length) {
                return true;
            }
            Object[] objArr = new Object[length];
            boolean z = false;
            RuntimeException runtimeExceptionMultiple = null;
            Throwable th = null;
            for (int i = 0; i < zipInnerArr.length; i++) {
                ZipInner<R> zipInner = zipInnerArr[i];
                Object obj = zipInner.value;
                if (obj != null) {
                    objArr[i] = obj;
                } else {
                    Throwable th2 = zipInner.error;
                    if (th2 == null) {
                        z = true;
                    } else if (runtimeExceptionMultiple != null) {
                        runtimeExceptionMultiple.addSuppressed(th2);
                    } else if (th != null) {
                        runtimeExceptionMultiple = Exceptions.multiple(th, th2);
                    } else {
                        th = th2;
                    }
                }
            }
            if (runtimeExceptionMultiple != null) {
                this.actual.onError(runtimeExceptionMultiple);
            } else if (th != null) {
                this.actual.onError(th);
            } else if (z) {
                this.actual.onComplete();
            } else {
                try {
                    this.actual.onNext(Objects.requireNonNull(this.zipper.apply(objArr), "zipper produced a null value"));
                    this.actual.onComplete();
                } catch (Throwable th3) {
                    Operators.onDiscardMultiple(Arrays.asList(objArr), this.actual.currentContext());
                    CoreSubscriber<? super R> coreSubscriber = this.actual;
                    coreSubscriber.onError(Operators.onOperatorError(null, th3, objArr, coreSubscriber.currentContext()));
                    return true;
                }
            }
            return true;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            long jMarkInterrupted = markInterrupted(this);
            if (isInterrupted(jMarkInterrupted) || !isRequestedOnce(jMarkInterrupted) || deliveredSignals(jMarkInterrupted) == this.subscribers.length) {
                return;
            }
            Context contextCurrentContext = this.actual.currentContext();
            for (ZipInner<R> zipInner : this.subscribers) {
                if (zipInner.cancel()) {
                    Operators.onDiscard(zipInner.value, contextCurrentContext);
                }
            }
        }

        void cancelExcept(ZipInner<R> zipInner) {
            Context contextCurrentContext = this.actual.currentContext();
            for (ZipInner<R> zipInner2 : this.subscribers) {
                if (zipInner2 != zipInner && zipInner2.cancel()) {
                    Operators.onDiscard(zipInner2.value, contextCurrentContext);
                }
            }
        }

        static <T> long markRequestedOnce(ZipCoordinator<T> zipCoordinator) {
            long j;
            do {
                j = zipCoordinator.state;
                if (isInterrupted(j) || isRequestedOnce(j)) {
                    break;
                }
            } while (!STATE.compareAndSet(zipCoordinator, j, j | REQUESTED_ONCE_FLAG));
            return j;
        }

        static <T> long markDeliveredSignal(ZipCoordinator<T> zipCoordinator) {
            long j;
            int length = zipCoordinator.subscribers.length;
            do {
                j = zipCoordinator.state;
                if (isInterrupted(j) || length == deliveredSignals(j)) {
                    break;
                }
            } while (!STATE.compareAndSet(zipCoordinator, j, j + 1));
            return j;
        }

        static <T> long markForceTerminated(ZipCoordinator<T> zipCoordinator) {
            long j;
            int length = zipCoordinator.subscribers.length;
            do {
                j = zipCoordinator.state;
                if (isInterrupted(j) || length == deliveredSignals(j)) {
                    break;
                }
            } while (!STATE.compareAndSet(zipCoordinator, j, ((-2147483648L) & j) | ((long) length) | Long.MIN_VALUE));
            return j;
        }

        static <T> long markInterrupted(ZipCoordinator<T> zipCoordinator) {
            long j;
            int length = zipCoordinator.subscribers.length;
            do {
                j = zipCoordinator.state;
                if (isInterrupted(j) || length == deliveredSignals(j)) {
                    break;
                }
            } while (!STATE.compareAndSet(zipCoordinator, j, j | Long.MIN_VALUE));
            return j;
        }
    }

    static final class ZipInner<R> implements InnerConsumer<Object> {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<ZipInner, Subscription> f2278S = AtomicReferenceFieldUpdater.newUpdater(ZipInner.class, Subscription.class, "s");
        Throwable error;
        final ZipCoordinator<R> parent;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2279s;
        Object value;

        ZipInner(ZipCoordinator<R> zipCoordinator) {
            this.parent = zipCoordinator;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2279s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.f2279s;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.actual.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2278S, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.value == null) {
                this.value = obj;
                this.parent.signal();
                Subscription subscription = this.f2279s;
                if (subscription == Operators.cancelledSubscription() || !C0162xc40028dd.m5m(f2278S, this, subscription, Operators.cancelledSubscription())) {
                    Operators.onDiscard(obj, this.parent.actual.currentContext());
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.value != null) {
                Operators.onErrorDropped(th, this.parent.actual.currentContext());
                return;
            }
            this.error = th;
            if (this.parent.delayError) {
                if (this.parent.signal()) {
                    return;
                }
                Operators.onErrorDropped(th, this.parent.actual.currentContext());
            } else {
                if (ZipCoordinator.isInterrupted(ZipCoordinator.markForceTerminated(this.parent))) {
                    return;
                }
                this.parent.cancelExcept(this);
                this.parent.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.value != null) {
                return;
            }
            if (this.parent.delayError) {
                this.parent.signal();
            } else {
                if (ZipCoordinator.isInterrupted(ZipCoordinator.markForceTerminated(this.parent))) {
                    return;
                }
                this.parent.cancelExcept(this);
                this.parent.actual.onComplete();
            }
        }

        boolean cancel() {
            return !Operators.terminate(f2278S, this);
        }
    }
}
