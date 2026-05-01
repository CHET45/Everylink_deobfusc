package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxConcatArray<T> extends Flux<T> implements SourceProducer<T> {
    final Publisher<? extends T>[] array;
    final boolean delayError;
    static final Object WORKING = new Object();
    static final Object DONE = new Object();

    interface SubscriptionAware {
        Subscription upstream();
    }

    @SafeVarargs
    FluxConcatArray(boolean z, Publisher<? extends T>... publisherArr) {
        this.array = (Publisher[]) Objects.requireNonNull(publisherArr, "array");
        this.delayError = z;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Publisher<? extends T>[] publisherArr = this.array;
        if (publisherArr.length == 0) {
            Operators.complete(coreSubscriber);
            return;
        }
        if (publisherArr.length == 1) {
            Publisher<? extends T> publisher = publisherArr[0];
            if (publisher == null) {
                Operators.error(coreSubscriber, new NullPointerException("The single source Publisher is null"));
                return;
            } else {
                Operators.toFluxOrMono(publisher).subscribe((Subscriber) coreSubscriber);
                return;
            }
        }
        if (this.delayError) {
            new ConcatArrayDelayErrorSubscriber(coreSubscriber, publisherArr).onComplete();
        } else {
            new ConcatArraySubscriber(coreSubscriber, publisherArr).onComplete();
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.DELAY_ERROR ? Boolean.valueOf(this.delayError) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    FluxConcatArray<T> concatAdditionalSourceLast(Publisher<? extends T> publisher) {
        Publisher<? extends T>[] publisherArr = this.array;
        int length = publisherArr.length;
        Publisher[] publisherArr2 = new Publisher[length + 1];
        System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
        publisherArr2[length] = publisher;
        return new FluxConcatArray<>(this.delayError, publisherArr2);
    }

    FluxConcatArray<T> concatAdditionalSourceFirst(Publisher<? extends T> publisher) {
        Publisher<? extends T>[] publisherArr = this.array;
        int length = publisherArr.length;
        Publisher[] publisherArr2 = new Publisher[length + 1];
        System.arraycopy(publisherArr, 0, publisherArr2, 1, length);
        publisherArr2[0] = publisher;
        return new FluxConcatArray<>(this.delayError, publisherArr2);
    }

    static final class ConcatArraySubscriber<T> extends ThreadLocal<Object> implements InnerOperator<T, T>, SubscriptionAware {
        static final AtomicLongFieldUpdater<ConcatArraySubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(ConcatArraySubscriber.class, "requested");
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        int index;
        long produced;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2099s;
        final Publisher<? extends T>[] sources;

        ConcatArraySubscriber(CoreSubscriber<? super T> coreSubscriber, Publisher<? extends T>[] publisherArr) {
            this.actual = coreSubscriber;
            this.sources = publisherArr;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (this.cancelled) {
                remove();
                subscription.cancel();
                return;
            }
            Subscription subscription2 = this.f2099s;
            this.f2099s = subscription;
            if (subscription2 == null) {
                this.actual.onSubscribe(this);
                return;
            }
            long jActivateAndGetRequested = FluxConcatArray.activateAndGetRequested(REQUESTED, this);
            if (jActivateAndGetRequested > 0) {
                subscription.request(jActivateAndGetRequested);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.produced++;
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            remove();
            this.actual.onError(th);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (get() == FluxConcatArray.WORKING) {
                set(FluxConcatArray.DONE);
                return;
            }
            Publisher<? extends T>[] publisherArr = this.sources;
            do {
                set(FluxConcatArray.WORKING);
                int i = this.index;
                if (i == publisherArr.length) {
                    remove();
                    if (this.cancelled) {
                        return;
                    }
                    this.actual.onComplete();
                    return;
                }
                Publisher<? extends T> publisher = publisherArr[i];
                if (publisher == null) {
                    remove();
                    if (this.cancelled) {
                        return;
                    }
                    this.actual.onError(new NullPointerException("Source Publisher at index " + i + " is null"));
                    return;
                }
                long j = this.produced;
                if (j != 0) {
                    this.produced = 0L;
                    FluxConcatArray.deactivateAndProduce(j, REQUESTED, this);
                }
                this.index = i + 1;
                if (this.cancelled) {
                    return;
                } else {
                    Operators.toFluxOrMono(publisher).subscribe((Subscriber) this);
                }
            } while (get() == FluxConcatArray.DONE);
            remove();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            Subscription subscriptionAddCapAndGetSubscription = FluxConcatArray.addCapAndGetSubscription(j, REQUESTED, this);
            if (subscriptionAddCapAndGetSubscription == null) {
                return;
            }
            subscriptionAddCapAndGetSubscription.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            remove();
            this.cancelled = true;
            if ((this.requested & Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.f2099s.cancel();
            }
        }

        @Override // reactor.core.publisher.FluxConcatArray.SubscriptionAware
        public Subscription upstream() {
            return this.f2099s;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == Scannable.Attr.PARENT ? this.f2099s : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : super.scanUnsafe(attr);
        }
    }

    static final class ConcatArrayDelayErrorSubscriber<T> extends ThreadLocal<Object> implements InnerOperator<T, T>, SubscriptionAware {
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        volatile Throwable error;
        int index;
        long produced;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2098s;
        final Publisher<? extends T>[] sources;
        static final AtomicLongFieldUpdater<ConcatArrayDelayErrorSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(ConcatArrayDelayErrorSubscriber.class, "requested");
        static final AtomicReferenceFieldUpdater<ConcatArrayDelayErrorSubscriber, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(ConcatArrayDelayErrorSubscriber.class, Throwable.class, "error");

        ConcatArrayDelayErrorSubscriber(CoreSubscriber<? super T> coreSubscriber, Publisher<? extends T>[] publisherArr) {
            this.actual = coreSubscriber;
            this.sources = publisherArr;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (this.cancelled) {
                remove();
                subscription.cancel();
                return;
            }
            Subscription subscription2 = this.f2098s;
            this.f2098s = subscription;
            if (subscription2 == null) {
                this.actual.onSubscribe(this);
                return;
            }
            long jActivateAndGetRequested = FluxConcatArray.activateAndGetRequested(REQUESTED, this);
            if (jActivateAndGetRequested > 0) {
                subscription.request(jActivateAndGetRequested);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.produced++;
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (!Exceptions.addThrowable(ERROR, this, th)) {
                remove();
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                onComplete();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (get() == FluxConcatArray.WORKING) {
                set(FluxConcatArray.DONE);
                return;
            }
            Publisher<? extends T>[] publisherArr = this.sources;
            do {
                set(FluxConcatArray.WORKING);
                int i = this.index;
                if (i == publisherArr.length) {
                    remove();
                    Throwable thTerminate = Exceptions.terminate(ERROR, this);
                    if (thTerminate == Exceptions.TERMINATED) {
                        return;
                    }
                    if (thTerminate != null) {
                        this.actual.onError(thTerminate);
                        return;
                    } else {
                        this.actual.onComplete();
                        return;
                    }
                }
                Publisher<? extends T> publisher = publisherArr[i];
                if (publisher == null) {
                    remove();
                    if (this.cancelled) {
                        return;
                    }
                    NullPointerException nullPointerException = new NullPointerException("Source Publisher at index " + i + " is null");
                    AtomicReferenceFieldUpdater<ConcatArrayDelayErrorSubscriber, Throwable> atomicReferenceFieldUpdater = ERROR;
                    if (!Exceptions.addThrowable(atomicReferenceFieldUpdater, this, nullPointerException)) {
                        Operators.onErrorDropped(nullPointerException, this.actual.currentContext());
                        return;
                    }
                    Throwable thTerminate2 = Exceptions.terminate(atomicReferenceFieldUpdater, this);
                    if (thTerminate2 == Exceptions.TERMINATED) {
                        return;
                    }
                    this.actual.onError(thTerminate2);
                    return;
                }
                long j = this.produced;
                if (j != 0) {
                    this.produced = 0L;
                    FluxConcatArray.deactivateAndProduce(j, REQUESTED, this);
                }
                this.index = i + 1;
                if (this.cancelled) {
                    return;
                } else {
                    Operators.toFluxOrMono(publisher).subscribe((Subscriber) this);
                }
            } while (get() == FluxConcatArray.DONE);
            remove();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            Subscription subscriptionAddCapAndGetSubscription = FluxConcatArray.addCapAndGetSubscription(j, REQUESTED, this);
            if (subscriptionAddCapAndGetSubscription == null) {
                return;
            }
            subscriptionAddCapAndGetSubscription.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            remove();
            this.cancelled = true;
            if ((this.requested & Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.f2098s.cancel();
            }
            Throwable thTerminate = Exceptions.terminate(ERROR, this);
            if (thTerminate != null) {
                Operators.onErrorDropped(thTerminate, this.actual.currentContext());
            }
        }

        @Override // reactor.core.publisher.FluxConcatArray.SubscriptionAware
        public Subscription upstream() {
            return this.f2098s;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.DELAY_ERROR) {
                return true;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.error == Exceptions.TERMINATED);
            }
            if (attr != Scannable.Attr.ERROR) {
                return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == Scannable.Attr.PARENT ? this.f2098s : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : super.scanUnsafe(attr);
            }
            if (this.error != Exceptions.TERMINATED) {
                return this.error;
            }
            return null;
        }
    }

    static <T> long activateAndGetRequested(AtomicLongFieldUpdater<T> atomicLongFieldUpdater, T t) {
        long j;
        long j2;
        do {
            j = atomicLongFieldUpdater.get(t);
            j2 = j & Long.MAX_VALUE;
        } while (!atomicLongFieldUpdater.compareAndSet(t, j, j2));
        return j2;
    }

    static <T> void deactivateAndProduce(long j, AtomicLongFieldUpdater<T> atomicLongFieldUpdater, T t) {
        long j2;
        do {
            j2 = atomicLongFieldUpdater.get(t);
        } while (!atomicLongFieldUpdater.compareAndSet(t, j2, j2 == Long.MAX_VALUE ? -1L : (j2 - j) | Long.MIN_VALUE));
    }

    /* JADX WARN: Incorrect types in method signature: <T::Lreactor/core/publisher/FluxConcatArray$SubscriptionAware;>(JLjava/util/concurrent/atomic/AtomicLongFieldUpdater<TT;>;TT;)Lorg/reactivestreams/Subscription; */
    @Nullable
    static Subscription addCapAndGetSubscription(long j, AtomicLongFieldUpdater atomicLongFieldUpdater, SubscriptionAware subscriptionAware) {
        long j2;
        Subscription subscriptionUpstream;
        long j3;
        long j4;
        do {
            j2 = atomicLongFieldUpdater.get(subscriptionAware);
            subscriptionUpstream = subscriptionAware.upstream();
            j3 = j2 & Long.MAX_VALUE;
            j4 = j2 & Long.MIN_VALUE;
            if (j3 == Long.MAX_VALUE) {
                if (j4 == Long.MIN_VALUE) {
                    return null;
                }
                return subscriptionUpstream;
            }
        } while (!atomicLongFieldUpdater.compareAndSet(subscriptionAware, j2, Operators.addCap(j3, j) | j4));
        if (j4 == Long.MIN_VALUE) {
            return null;
        }
        return subscriptionUpstream;
    }
}
