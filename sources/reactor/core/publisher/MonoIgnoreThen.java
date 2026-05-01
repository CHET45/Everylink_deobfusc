package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoIgnoreThen<T> extends Mono<T> implements Scannable {
    final Publisher<?>[] ignore;
    final Mono<T> last;

    MonoIgnoreThen(Publisher<?>[] publisherArr, Mono<T> mono) {
        this.ignore = (Publisher[]) Objects.requireNonNull(publisherArr, "ignore");
        this.last = (Mono) Objects.requireNonNull(mono, "last");
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        ThenIgnoreMain thenIgnoreMain = new ThenIgnoreMain(coreSubscriber, this.ignore, this.last);
        coreSubscriber.onSubscribe(thenIgnoreMain);
        thenIgnoreMain.subscribeNext();
    }

    <U> MonoIgnoreThen<U> shift(Mono<U> mono) {
        Objects.requireNonNull(mono, "newLast");
        Publisher<?>[] publisherArr = this.ignore;
        int length = publisherArr.length;
        Publisher[] publisherArr2 = new Publisher[length + 1];
        System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
        publisherArr2[length] = this.last;
        return new MonoIgnoreThen<>(publisherArr2, mono);
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    static final class ThenIgnoreMain<T> implements InnerOperator<T, T> {
        static final int CANCELLED = 128;
        static final int HAS_COMPLETION = 16;
        static final int HAS_REQUEST = 2;
        static final int HAS_SUBSCRIPTION = 4;
        static final int HAS_VALUE = 8;
        private static final AtomicIntegerFieldUpdater<ThenIgnoreMain> STATE = AtomicIntegerFieldUpdater.newUpdater(ThenIgnoreMain.class, "state");
        Subscription activeSubscription;
        final CoreSubscriber<? super T> actual;
        boolean done;
        final Publisher<?>[] ignoreMonos;
        int index;
        final Mono<T> lastMono;
        volatile int state;
        T value;

        static boolean hasRequest(int i) {
            return (i & 2) == 2;
        }

        static boolean hasSubscription(int i) {
            return (i & 4) == 4;
        }

        static boolean hasValue(int i) {
            return (i & 8) == 8;
        }

        static boolean isCancelled(int i) {
            return i == 128;
        }

        ThenIgnoreMain(CoreSubscriber<? super T> coreSubscriber, Publisher<?>[] publisherArr, Mono<T> mono) {
            this.actual = coreSubscriber;
            this.ignoreMonos = publisherArr;
            this.lastMono = mono;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.activeSubscription : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(isCancelled(this.state)) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.activeSubscription, subscription)) {
                this.activeSubscription = subscription;
                if (isCancelled(markHasSubscription())) {
                    subscription.cancel();
                } else {
                    subscription.request(Long.MAX_VALUE);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (hasSubscription(markCancelled())) {
                this.activeSubscription.cancel();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            int i;
            if (Operators.validate(j)) {
                do {
                    i = this.state;
                    if (isCancelled(i) || hasRequest(i)) {
                        return;
                    }
                } while (!STATE.compareAndSet(this, i, i | 2));
                if (hasValue(i)) {
                    CoreSubscriber<? super T> coreSubscriber = this.actual;
                    coreSubscriber.onNext(this.value);
                    coreSubscriber.onComplete();
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onDiscard(t, currentContext());
            } else if (this.index != this.ignoreMonos.length) {
                Operators.onDiscard(t, currentContext());
            } else {
                this.done = true;
                complete(t);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            if (this.index != this.ignoreMonos.length) {
                if (isCancelled(markUnsubscribed())) {
                    return;
                }
                this.activeSubscription = null;
                this.index++;
                subscribeNext();
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        /* JADX WARN: Multi-variable type inference failed */
        void subscribeNext() {
            Publisher<?>[] publisherArr = this.ignoreMonos;
            while (true) {
                int i = this.index;
                if (i == publisherArr.length) {
                    Mono<T> mono = this.lastMono;
                    if (mono instanceof Callable) {
                        if (isCancelled(this.state)) {
                            return;
                        }
                        try {
                            Object objCall = ((Callable) mono).call();
                            if (objCall != null) {
                                onNext(objCall);
                            }
                            onComplete();
                            return;
                        } catch (Throwable th) {
                            onError(Operators.onOperatorError(th, currentContext()));
                            return;
                        }
                    }
                    Operators.toFluxOrMono(mono).subscribe((CoreSubscriber) this);
                    return;
                }
                Publisher<?> publisher = publisherArr[i];
                if (publisher instanceof Callable) {
                    if (isCancelled(this.state)) {
                        return;
                    }
                    try {
                        Operators.onDiscard(((Callable) publisher).call(), currentContext());
                        this.index = i + 1;
                    } catch (Throwable th2) {
                        onError(Operators.onOperatorError(th2, currentContext()));
                        return;
                    }
                } else {
                    Operators.toFluxOrMono(publisher).subscribe((Subscriber) this);
                    return;
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, actual().currentContext());
            } else {
                this.done = true;
                this.actual.onError(th);
            }
        }

        final void complete(T t) {
            int i;
            do {
                i = this.state;
                if (isCancelled(i)) {
                    Operators.onDiscard(t, this.actual.currentContext());
                    return;
                } else {
                    if (hasRequest(i) && STATE.compareAndSet(this, i, i | 24)) {
                        CoreSubscriber<? super T> coreSubscriber = this.actual;
                        coreSubscriber.onNext(t);
                        coreSubscriber.onComplete();
                        return;
                    }
                    this.value = t;
                }
            } while (!STATE.compareAndSet(this, i, i | 24));
        }

        final int markHasSubscription() {
            int i;
            do {
                i = this.state;
                if (i == 128 || (i & 4) == 4) {
                    return i;
                }
            } while (!STATE.compareAndSet(this, i, i | 4));
            return i;
        }

        final int markUnsubscribed() {
            int i;
            do {
                i = this.state;
                if (isCancelled(i) || !hasSubscription(i)) {
                    return i;
                }
            } while (!STATE.compareAndSet(this, i, i & (-5)));
            return i;
        }

        final int markCancelled() {
            int i;
            do {
                i = this.state;
                if (i == 128) {
                    return i;
                }
            } while (!STATE.compareAndSet(this, i, 128));
            return i;
        }
    }
}
