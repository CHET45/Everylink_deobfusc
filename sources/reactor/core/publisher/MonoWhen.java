package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoWhen extends Mono<Void> implements SourceProducer<Void> {
    final boolean delayError;
    final Publisher<?>[] sources;
    final Iterable<? extends Publisher<?>> sourcesIterable;

    MonoWhen(boolean z, Publisher<?>... publisherArr) {
        this.delayError = z;
        this.sources = (Publisher[]) Objects.requireNonNull(publisherArr, "sources");
        this.sourcesIterable = null;
    }

    MonoWhen(boolean z, Iterable<? extends Publisher<?>> iterable) {
        this.delayError = z;
        this.sources = null;
        this.sourcesIterable = (Iterable) Objects.requireNonNull(iterable, "sourcesIterable");
    }

    @Nullable
    Mono<Void> whenAdditionalSource(Publisher<?> publisher) {
        Publisher<?>[] publisherArr = this.sources;
        if (publisherArr == null) {
            return null;
        }
        int length = publisherArr.length;
        Publisher[] publisherArr2 = new Publisher[length + 1];
        System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
        publisherArr2[length] = publisher;
        return new MonoWhen(this.delayError, (Publisher<?>[]) publisherArr2);
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super Void> coreSubscriber) {
        int length;
        Publisher<?>[] publisherArr = this.sources;
        if (publisherArr != null) {
            length = publisherArr.length;
        } else {
            publisherArr = new Publisher[8];
            int i = 0;
            for (Publisher<?> publisher : this.sourcesIterable) {
                if (i == publisherArr.length) {
                    Publisher<?>[] publisherArr2 = new Publisher[(i >> 2) + i];
                    System.arraycopy(publisherArr, 0, publisherArr2, 0, i);
                    publisherArr = publisherArr2;
                }
                publisherArr[i] = publisher;
                i++;
            }
            length = i;
        }
        if (length == 0) {
            Operators.complete(coreSubscriber);
        } else {
            Operators.toFluxOrMono(publisherArr);
            coreSubscriber.onSubscribe(new WhenCoordinator(publisherArr, coreSubscriber, length, this.delayError));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.DELAY_ERROR ? Boolean.valueOf(this.delayError) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class WhenCoordinator implements InnerProducer<Void>, Fuseable, Fuseable.QueueSubscription<Void> {
        static final long INTERRUPTED_FLAG = Long.MIN_VALUE;
        static final long MAX_SIGNALS_VALUE = 2147483647L;
        static final long REQUESTED_ONCE_FLAG = 4611686018427387904L;
        static final AtomicLongFieldUpdater<WhenCoordinator> STATE = AtomicLongFieldUpdater.newUpdater(WhenCoordinator.class, "state");
        final CoreSubscriber<? super Void> actual;
        final boolean delayError;
        final Publisher<?>[] sources;
        volatile long state;
        final WhenInner[] subscribers;

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
        public Void poll() {
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

        WhenCoordinator(Publisher<?>[] publisherArr, CoreSubscriber<? super Void> coreSubscriber, int i, boolean z) {
            this.sources = publisherArr;
            this.actual = coreSubscriber;
            this.delayError = z;
            this.subscribers = new WhenInner[i];
            for (int i2 = 0; i2 < i; i2++) {
                this.subscribers[i2] = new WhenInner(this);
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Void> actual() {
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

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers);
        }

        boolean signal() {
            WhenInner[] whenInnerArr = this.subscribers;
            int length = whenInnerArr.length;
            long jMarkDeliveredSignal = markDeliveredSignal(this);
            int iDeliveredSignals = deliveredSignals(jMarkDeliveredSignal);
            if (isInterrupted(jMarkDeliveredSignal) || iDeliveredSignals == length) {
                return false;
            }
            if (iDeliveredSignals + 1 != length) {
                return true;
            }
            RuntimeException runtimeExceptionMultiple = null;
            Throwable th = null;
            for (WhenInner whenInner : whenInnerArr) {
                Throwable th2 = whenInner.error;
                if (th2 != null) {
                    if (runtimeExceptionMultiple != null) {
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
            } else {
                this.actual.onComplete();
            }
            return true;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            long jMarkRequestedOnce = markRequestedOnce(this);
            if (isRequestedOnce(jMarkRequestedOnce) || isInterrupted(jMarkRequestedOnce)) {
                return;
            }
            Publisher<?>[] publisherArr = this.sources;
            WhenInner[] whenInnerArr = this.subscribers;
            for (int i = 0; i < this.subscribers.length; i++) {
                publisherArr[i].subscribe(whenInnerArr[i]);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            long jMarkInterrupted = markInterrupted(this);
            if (isInterrupted(jMarkInterrupted) || !isRequestedOnce(jMarkInterrupted)) {
                return;
            }
            int iDeliveredSignals = deliveredSignals(jMarkInterrupted);
            WhenInner[] whenInnerArr = this.subscribers;
            if (iDeliveredSignals == whenInnerArr.length) {
                return;
            }
            for (WhenInner whenInner : whenInnerArr) {
                whenInner.cancel();
            }
        }

        void cancelExcept(WhenInner whenInner) {
            for (WhenInner whenInner2 : this.subscribers) {
                if (whenInner2 != whenInner) {
                    whenInner2.cancel();
                }
            }
        }

        static long markRequestedOnce(WhenCoordinator whenCoordinator) {
            long j;
            do {
                j = whenCoordinator.state;
                if (isInterrupted(j) || isRequestedOnce(j)) {
                    break;
                }
            } while (!STATE.compareAndSet(whenCoordinator, j, j | REQUESTED_ONCE_FLAG));
            return j;
        }

        static long markDeliveredSignal(WhenCoordinator whenCoordinator) {
            long j;
            int length = whenCoordinator.subscribers.length;
            do {
                j = whenCoordinator.state;
                if (isInterrupted(j) || length == deliveredSignals(j)) {
                    break;
                }
            } while (!STATE.compareAndSet(whenCoordinator, j, j + 1));
            return j;
        }

        static long markForceTerminated(WhenCoordinator whenCoordinator) {
            long j;
            int length = whenCoordinator.subscribers.length;
            do {
                j = whenCoordinator.state;
                if (isInterrupted(j) || length == deliveredSignals(j)) {
                    break;
                }
            } while (!STATE.compareAndSet(whenCoordinator, j, ((-2147483648L) & j) | ((long) length) | Long.MIN_VALUE));
            return j;
        }

        static long markInterrupted(WhenCoordinator whenCoordinator) {
            long j;
            int length = whenCoordinator.subscribers.length;
            do {
                j = whenCoordinator.state;
                if (isInterrupted(j) || length == deliveredSignals(j)) {
                    break;
                }
            } while (!STATE.compareAndSet(whenCoordinator, j, j | Long.MIN_VALUE));
            return j;
        }
    }

    static final class WhenInner implements InnerConsumer<Object> {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<WhenInner, Subscription> f2276S = AtomicReferenceFieldUpdater.newUpdater(WhenInner.class, Subscription.class, "s");
        Throwable error;
        final WhenCoordinator parent;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2277s;

        WhenInner(WhenCoordinator whenCoordinator) {
            this.parent = whenCoordinator;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2277s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.f2277s;
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
            if (Operators.setOnce(f2276S, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Operators.onDiscard(obj, currentContext());
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            if (this.parent.delayError) {
                if (this.parent.signal()) {
                    return;
                }
                Operators.onErrorDropped(th, this.parent.actual.currentContext());
            } else {
                if (WhenCoordinator.isInterrupted(WhenCoordinator.markForceTerminated(this.parent))) {
                    return;
                }
                this.parent.cancelExcept(this);
                this.parent.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.signal();
        }

        void cancel() {
            Operators.terminate(f2276S, this);
        }
    }
}
