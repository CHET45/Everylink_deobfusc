package reactor.core.publisher;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxFirstWithValue<T> extends Flux<T> implements SourceProducer<T> {
    final Publisher<? extends T>[] array;
    final Iterable<? extends Publisher<? extends T>> iterable;

    private FluxFirstWithValue(Publisher<? extends T>[] publisherArr) {
        this.array = (Publisher[]) Objects.requireNonNull(publisherArr, "array");
        this.iterable = null;
    }

    @SafeVarargs
    FluxFirstWithValue(Publisher<? extends T> publisher, Publisher<? extends T>... publisherArr) {
        Objects.requireNonNull(publisher, "first");
        Objects.requireNonNull(publisherArr, "others");
        Publisher<? extends T>[] publisherArr2 = new Publisher[publisherArr.length + 1];
        publisherArr2[0] = publisher;
        System.arraycopy(publisherArr, 0, publisherArr2, 1, publisherArr.length);
        this.array = publisherArr2;
        this.iterable = null;
    }

    FluxFirstWithValue(Iterable<? extends Publisher<? extends T>> iterable) {
        this.array = null;
        this.iterable = (Iterable) Objects.requireNonNull(iterable);
    }

    @SafeVarargs
    @Nullable
    final FluxFirstWithValue<T> firstValuedAdditionalSources(Publisher<? extends T>... publisherArr) {
        Objects.requireNonNull(publisherArr, "others");
        if (publisherArr.length == 0) {
            return this;
        }
        Publisher<? extends T>[] publisherArr2 = this.array;
        if (publisherArr2 == null) {
            return null;
        }
        int length = publisherArr2.length;
        int length2 = publisherArr.length;
        Publisher[] publisherArr3 = new Publisher[length + length2];
        System.arraycopy(publisherArr2, 0, publisherArr3, 0, length);
        System.arraycopy(publisherArr, 0, publisherArr3, length, length2);
        return new FluxFirstWithValue<>(publisherArr3);
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
            Flux fluxFrom = Flux.from(publisherArr[0]);
            if (fluxFrom == null) {
                Operators.error(coreSubscriber, new NullPointerException("The single source Publisher is null"));
                return;
            } else {
                fluxFrom.subscribe((Subscriber) coreSubscriber);
                return;
            }
        }
        new RaceValuesCoordinator(length).subscribe(publisherArr, length, coreSubscriber);
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class RaceValuesCoordinator<T> implements Subscription, Scannable {
        volatile boolean cancelled;
        final Throwable[] errorsOrCompleteEmpty;
        volatile int nbErrorsOrCompletedEmpty;
        final FirstValuesEmittingSubscriber<T>[] subscribers;
        volatile int winner = Integer.MIN_VALUE;
        static final AtomicIntegerFieldUpdater<RaceValuesCoordinator> WINNER = AtomicIntegerFieldUpdater.newUpdater(RaceValuesCoordinator.class, "winner");
        static final AtomicIntegerFieldUpdater<RaceValuesCoordinator> ERRORS_OR_COMPLETED_EMPTY = AtomicIntegerFieldUpdater.newUpdater(RaceValuesCoordinator.class, "nbErrorsOrCompletedEmpty");

        public RaceValuesCoordinator(int i) {
            this.subscribers = new FirstValuesEmittingSubscriber[i];
            this.errorsOrCompleteEmpty = new Throwable[i];
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers);
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            return null;
        }

        void subscribe(Publisher<? extends T>[] publisherArr, int i, CoreSubscriber<? super T> coreSubscriber) {
            for (int i2 = 0; i2 < i; i2++) {
                this.subscribers[i2] = new FirstValuesEmittingSubscriber<>(coreSubscriber, this, i2);
            }
            coreSubscriber.onSubscribe(this);
            Operators.toFluxOrMono(publisherArr);
            for (int i3 = 0; i3 < i && !this.cancelled && this.winner == Integer.MIN_VALUE; i3++) {
                Publisher<? extends T> publisher = publisherArr[i3];
                if (publisher == null) {
                    coreSubscriber.onError(new NullPointerException("The " + i3 + " th Publisher source is null"));
                    return;
                }
                publisher.subscribe(this.subscribers[i3]);
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
                for (FirstValuesEmittingSubscriber<T> firstValuesEmittingSubscriber : this.subscribers) {
                    firstValuesEmittingSubscriber.request(j);
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
            for (FirstValuesEmittingSubscriber<T> firstValuesEmittingSubscriber : this.subscribers) {
                firstValuesEmittingSubscriber.cancel();
            }
        }

        boolean tryWin(int i) {
            int i2 = 0;
            if (this.winner != Integer.MIN_VALUE || !WINNER.compareAndSet(this, Integer.MIN_VALUE, i)) {
                return false;
            }
            while (true) {
                FirstValuesEmittingSubscriber<T>[] firstValuesEmittingSubscriberArr = this.subscribers;
                if (i2 >= firstValuesEmittingSubscriberArr.length) {
                    return true;
                }
                if (i2 != i) {
                    firstValuesEmittingSubscriberArr[i2].cancel();
                    this.errorsOrCompleteEmpty[i2] = null;
                }
                i2++;
            }
        }
    }

    static final class FirstValuesEmittingSubscriber<T> extends Operators.DeferredSubscription implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final int index;
        final RaceValuesCoordinator<T> parent;
        boolean won;

        FirstValuesEmittingSubscriber(CoreSubscriber<? super T> coreSubscriber, RaceValuesCoordinator<T> raceValuesCoordinator, int i) {
            this.actual = coreSubscriber;
            this.parent = raceValuesCoordinator;
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
            } else {
                recordTerminalSignals(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.won) {
                this.actual.onComplete();
            } else {
                recordTerminalSignals(new NoSuchElementException("source at index " + this.index + " completed empty"));
            }
        }

        void recordTerminalSignals(Throwable th) {
            this.parent.errorsOrCompleteEmpty[this.index] = th;
            if (RaceValuesCoordinator.ERRORS_OR_COMPLETED_EMPTY.incrementAndGet(this.parent) == this.parent.subscribers.length) {
                NoSuchElementException noSuchElementException = new NoSuchElementException("All sources completed with error or without values");
                noSuchElementException.initCause(Exceptions.multiple(this.parent.errorsOrCompleteEmpty));
                this.actual.onError(noSuchElementException);
            }
        }
    }
}
