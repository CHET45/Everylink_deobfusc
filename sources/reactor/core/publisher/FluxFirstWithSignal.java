package reactor.core.publisher;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxFirstWithSignal<T> extends Flux<T> implements SourceProducer<T> {
    final Publisher<? extends T>[] array;
    final Iterable<? extends Publisher<? extends T>> iterable;

    @SafeVarargs
    FluxFirstWithSignal(Publisher<? extends T>... publisherArr) {
        this.array = (Publisher[]) Objects.requireNonNull(publisherArr, "array");
        this.iterable = null;
    }

    FluxFirstWithSignal(Iterable<? extends Publisher<? extends T>> iterable) {
        this.array = null;
        this.iterable = (Iterable) Objects.requireNonNull(iterable);
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        int length;
        Publisher<? extends T>[] publisherArr = this.array;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                Iterator it = (Iterator) Objects.requireNonNull(this.iterable.iterator(), "The iterator returned is null");
                length = 0;
                while (it.hasNext()) {
                    try {
                        try {
                            Publisher<? extends T> publisher = (Publisher) Objects.requireNonNull((Publisher) it.next(), "The Publisher returned by the iterator is null");
                            if (length == publisherArr.length) {
                                Publisher<? extends T>[] publisherArr2 = new Publisher[(length >> 2) + length];
                                System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
                                publisherArr = publisherArr2;
                            }
                            publisherArr[length] = publisher;
                            length++;
                        } catch (Throwable th) {
                            Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
                            return;
                        }
                    } catch (Throwable th2) {
                        Operators.error(coreSubscriber, Operators.onOperatorError(th2, coreSubscriber.currentContext()));
                        return;
                    }
                }
            } catch (Throwable th3) {
                Operators.error(coreSubscriber, Operators.onOperatorError(th3, coreSubscriber.currentContext()));
                return;
            }
        } else {
            length = publisherArr.length;
        }
        if (length == 0) {
            Operators.complete(coreSubscriber);
            return;
        }
        if (length == 1) {
            Publisher<? extends T> publisher2 = publisherArr[0];
            if (publisher2 == null) {
                Operators.error(coreSubscriber, new NullPointerException("The single source Publisher is null"));
                return;
            } else {
                Operators.toFluxOrMono(publisher2).subscribe((Subscriber) coreSubscriber);
                return;
            }
        }
        Operators.toFluxOrMono(publisherArr);
        new RaceCoordinator(length).subscribe(publisherArr, length, coreSubscriber);
    }

    @Nullable
    FluxFirstWithSignal<T> orAdditionalSource(Publisher<? extends T> publisher) {
        Publisher<? extends T>[] publisherArr = this.array;
        if (publisherArr == null) {
            return null;
        }
        int length = publisherArr.length;
        Publisher[] publisherArr2 = new Publisher[length + 1];
        System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
        publisherArr2[length] = publisher;
        return new FluxFirstWithSignal<>(publisherArr2);
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class RaceCoordinator<T> implements Subscription, Scannable {
        static final AtomicIntegerFieldUpdater<RaceCoordinator> WINNER = AtomicIntegerFieldUpdater.newUpdater(RaceCoordinator.class, "winner");
        volatile boolean cancelled;
        final FirstEmittingSubscriber<T>[] subscribers;
        volatile int winner = Integer.MIN_VALUE;

        RaceCoordinator(int i) {
            this.subscribers = new FirstEmittingSubscriber[i];
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            return null;
        }

        void subscribe(Publisher<? extends T>[] publisherArr, int i, CoreSubscriber<? super T> coreSubscriber) {
            FirstEmittingSubscriber<T>[] firstEmittingSubscriberArr = this.subscribers;
            for (int i2 = 0; i2 < i; i2++) {
                firstEmittingSubscriberArr[i2] = new FirstEmittingSubscriber<>(coreSubscriber, this, i2);
            }
            coreSubscriber.onSubscribe(this);
            for (int i3 = 0; i3 < i && !this.cancelled && this.winner == Integer.MIN_VALUE; i3++) {
                Publisher<? extends T> publisher = publisherArr[i3];
                if (publisher == null) {
                    if (WINNER.compareAndSet(this, Integer.MIN_VALUE, -1)) {
                        coreSubscriber.onError(new NullPointerException("The " + i3 + " th Publisher source is null"));
                        return;
                    }
                    return;
                }
                publisher.subscribe(firstEmittingSubscriberArr[i3]);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                int i = this.winner;
                if (i >= 0) {
                    this.subscribers[i].request(j);
                    return;
                }
                for (FirstEmittingSubscriber<T> firstEmittingSubscriber : this.subscribers) {
                    firstEmittingSubscriber.request(j);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            int i = this.winner;
            if (i >= 0) {
                this.subscribers[i].cancel();
                return;
            }
            for (FirstEmittingSubscriber<T> firstEmittingSubscriber : this.subscribers) {
                firstEmittingSubscriber.cancel();
            }
        }

        boolean tryWin(int i) {
            if (this.winner != Integer.MIN_VALUE || !WINNER.compareAndSet(this, Integer.MIN_VALUE, i)) {
                return false;
            }
            FirstEmittingSubscriber<T>[] firstEmittingSubscriberArr = this.subscribers;
            int length = firstEmittingSubscriberArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (i2 != i) {
                    firstEmittingSubscriberArr[i2].cancel();
                }
            }
            return true;
        }
    }

    static final class FirstEmittingSubscriber<T> extends Operators.DeferredSubscription implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final int index;
        final RaceCoordinator<T> parent;
        boolean won;

        FirstEmittingSubscriber(CoreSubscriber<? super T> coreSubscriber, RaceCoordinator<T> raceCoordinator, int i) {
            this.actual = coreSubscriber;
            this.parent = raceCoordinator;
            this.index = i;
        }

        @Override // reactor.core.publisher.Operators.DeferredSubscription, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2281s : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.parent.cancelled) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            set(subscription);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.won) {
                this.actual.onNext(t);
            } else if (this.parent.tryWin(this.index)) {
                this.won = true;
                this.actual.onNext(t);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.won) {
                this.actual.onError(th);
            } else if (this.parent.tryWin(this.index)) {
                this.won = true;
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.won) {
                this.actual.onComplete();
            } else if (this.parent.tryWin(this.index)) {
                this.won = true;
                this.actual.onComplete();
            }
        }
    }
}
