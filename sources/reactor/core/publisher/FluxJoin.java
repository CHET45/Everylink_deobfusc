package reactor.core.publisher;

import android.Manifest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.FluxGroupJoin;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;

/* JADX INFO: loaded from: classes5.dex */
final class FluxJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends InternalFluxOperator<TLeft, R> {
    final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
    final Publisher<? extends TRight> other;
    final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
    final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;

    FluxJoin(Flux<TLeft> flux, Publisher<? extends TRight> publisher, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
        super(flux);
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "other"));
        this.leftEnd = (Function) Objects.requireNonNull(function, "leftEnd");
        this.rightEnd = (Function) Objects.requireNonNull(function2, "rightEnd");
        this.resultSelector = (BiFunction) Objects.requireNonNull(biFunction, "resultSelector");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super TLeft> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        JoinSubscription joinSubscription = new JoinSubscription(coreSubscriber, this.leftEnd, this.rightEnd, this.resultSelector);
        coreSubscriber.onSubscribe(joinSubscription);
        FluxGroupJoin.LeftRightSubscriber leftRightSubscriber = new FluxGroupJoin.LeftRightSubscriber(joinSubscription, true);
        joinSubscription.cancellations.add(leftRightSubscriber);
        FluxGroupJoin.LeftRightSubscriber leftRightSubscriber2 = new FluxGroupJoin.LeftRightSubscriber(joinSubscription, false);
        joinSubscription.cancellations.add(leftRightSubscriber2);
        this.source.subscribe((CoreSubscriber<? super Object>) leftRightSubscriber);
        this.other.subscribe(leftRightSubscriber2);
        return null;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class JoinSubscription<TLeft, TRight, TLeftEnd, TRightEnd, R> implements FluxGroupJoin.JoinSupport<R> {
        volatile int active;
        final CoreSubscriber<? super R> actual;
        final Disposable.Composite cancellations = Disposables.composite();
        volatile Throwable error;
        final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
        int leftIndex;
        final Map<Integer, TLeft> lefts;
        final Queue<Object> queue;
        final BiPredicate<Object, Object> queueBiOffer;
        volatile long requested;
        final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
        final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
        int rightIndex;
        final Map<Integer, TRight> rights;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<JoinSubscription> WIP = AtomicIntegerFieldUpdater.newUpdater(JoinSubscription.class, "wip");
        static final AtomicIntegerFieldUpdater<JoinSubscription> ACTIVE = AtomicIntegerFieldUpdater.newUpdater(JoinSubscription.class, "active");
        static final AtomicLongFieldUpdater<JoinSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(JoinSubscription.class, "requested");
        static final AtomicReferenceFieldUpdater<JoinSubscription, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(JoinSubscription.class, Throwable.class, "error");
        static final Integer LEFT_VALUE = 1;
        static final Integer RIGHT_VALUE = 2;
        static final Integer LEFT_CLOSE = 3;
        static final Integer RIGHT_CLOSE = 4;

        JoinSubscription(CoreSubscriber<? super R> coreSubscriber, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super TRight, ? extends R> biFunction) {
            this.actual = coreSubscriber;
            Queue<Object> queue = (Queue) Queues.unboundedMultiproducer().get();
            this.queue = queue;
            this.queueBiOffer = (BiPredicate) queue;
            this.lefts = new LinkedHashMap();
            this.rights = new LinkedHashMap();
            this.leftEnd = function;
            this.rightEnd = function2;
            this.resultSelector = biFunction;
            ACTIVE.lazySet(this, 2);
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Scannable.from(this.cancellations).inners();
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancellations.isDisposed());
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.queue.size() / 2);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.active == 0);
            }
            return attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancellations.isDisposed()) {
                return;
            }
            this.cancellations.dispose();
            if (WIP.getAndIncrement(this) == 0) {
                this.queue.clear();
            }
        }

        void errorAll(Subscriber<?> subscriber) {
            Throwable thTerminate = Exceptions.terminate(ERROR, this);
            this.lefts.clear();
            this.rights.clear();
            subscriber.onError(thTerminate);
        }

        void drain() {
            int i;
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            Queue<Object> queue = this.queue;
            CoreSubscriber<? super R> coreSubscriber = this.actual;
            boolean z = true;
            int iAddAndGet = 1;
            while (!this.cancellations.isDisposed()) {
                if (this.error != null) {
                    queue.clear();
                    this.cancellations.dispose();
                    errorAll(coreSubscriber);
                    return;
                }
                boolean z2 = this.active == 0 ? z : false;
                Integer num = (Integer) queue.poll();
                boolean z3 = num == null ? z : false;
                if (z2 && z3) {
                    this.lefts.clear();
                    this.rights.clear();
                    this.cancellations.dispose();
                    coreSubscriber.onComplete();
                    return;
                }
                if (!z3) {
                    Object objPoll = queue.poll();
                    if (num == LEFT_VALUE) {
                        int i2 = this.leftIndex;
                        this.leftIndex = i2 + 1;
                        this.lefts.put(Integer.valueOf(i2), (TLeft) objPoll);
                        try {
                            Publisher publisher = (Publisher) Objects.requireNonNull(this.leftEnd.apply(objPoll), "The leftEnd returned a null Publisher");
                            FluxGroupJoin.LeftRightEndSubscriber leftRightEndSubscriber = new FluxGroupJoin.LeftRightEndSubscriber(this, z, i2);
                            this.cancellations.add(leftRightEndSubscriber);
                            Operators.toFluxOrMono(publisher).subscribe((Subscriber) leftRightEndSubscriber);
                            if (this.error != null) {
                                queue.clear();
                                this.cancellations.dispose();
                                errorAll(coreSubscriber);
                                return;
                            }
                            long j = this.requested;
                            i = iAddAndGet;
                            long j2 = 0;
                            for (TRight tright : this.rights.values()) {
                                try {
                                    Manifest manifest = (Object) Objects.requireNonNull(this.resultSelector.apply(objPoll, tright), "The resultSelector returned a null value");
                                    if (j2 != j) {
                                        coreSubscriber.onNext(manifest);
                                        j2++;
                                    } else {
                                        Exceptions.addThrowable(ERROR, this, Exceptions.failWithOverflow("Could not emit value due to lack of requests"));
                                        queue.clear();
                                        this.cancellations.dispose();
                                        errorAll(coreSubscriber);
                                        return;
                                    }
                                } catch (Throwable th) {
                                    Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, th, tright, this.actual.currentContext()));
                                    errorAll(coreSubscriber);
                                    return;
                                }
                            }
                            if (j2 != 0) {
                                Operators.produced(REQUESTED, this, j2);
                            }
                        } catch (Throwable th2) {
                            Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, th2, objPoll, this.actual.currentContext()));
                            errorAll(coreSubscriber);
                            return;
                        }
                    } else {
                        i = iAddAndGet;
                        if (num == RIGHT_VALUE) {
                            int i3 = this.rightIndex;
                            this.rightIndex = i3 + 1;
                            this.rights.put(Integer.valueOf(i3), (TRight) objPoll);
                            try {
                                Publisher publisher2 = (Publisher) Objects.requireNonNull(this.rightEnd.apply(objPoll), "The rightEnd returned a null Publisher");
                                FluxGroupJoin.LeftRightEndSubscriber leftRightEndSubscriber2 = new FluxGroupJoin.LeftRightEndSubscriber(this, false, i3);
                                this.cancellations.add(leftRightEndSubscriber2);
                                Operators.toFluxOrMono(publisher2).subscribe((Subscriber) leftRightEndSubscriber2);
                                if (this.error != null) {
                                    queue.clear();
                                    this.cancellations.dispose();
                                    errorAll(coreSubscriber);
                                    return;
                                }
                                long j3 = this.requested;
                                long j4 = 0;
                                for (TLeft tleft : this.lefts.values()) {
                                    try {
                                        Manifest manifest2 = (Object) Objects.requireNonNull(this.resultSelector.apply(tleft, objPoll), "The resultSelector returned a null value");
                                        if (j4 != j3) {
                                            coreSubscriber.onNext(manifest2);
                                            j4++;
                                        } else {
                                            Exceptions.addThrowable(ERROR, this, Exceptions.failWithOverflow("Could not emit value due to lack of requests"));
                                            queue.clear();
                                            this.cancellations.dispose();
                                            errorAll(coreSubscriber);
                                            return;
                                        }
                                    } catch (Throwable th3) {
                                        Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, th3, tleft, this.actual.currentContext()));
                                        errorAll(coreSubscriber);
                                        return;
                                    }
                                }
                                if (j4 != 0) {
                                    Operators.produced(REQUESTED, this, j4);
                                }
                            } catch (Throwable th4) {
                                Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, th4, objPoll, this.actual.currentContext()));
                                errorAll(coreSubscriber);
                                return;
                            }
                        } else if (num == LEFT_CLOSE) {
                            FluxGroupJoin.LeftRightEndSubscriber leftRightEndSubscriber3 = (FluxGroupJoin.LeftRightEndSubscriber) objPoll;
                            this.lefts.remove(Integer.valueOf(leftRightEndSubscriber3.index));
                            this.cancellations.remove(leftRightEndSubscriber3);
                        } else if (num == RIGHT_CLOSE) {
                            FluxGroupJoin.LeftRightEndSubscriber leftRightEndSubscriber4 = (FluxGroupJoin.LeftRightEndSubscriber) objPoll;
                            this.rights.remove(Integer.valueOf(leftRightEndSubscriber4.index));
                            this.cancellations.remove(leftRightEndSubscriber4);
                        }
                    }
                    iAddAndGet = i;
                    z = true;
                } else {
                    iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                }
            }
            queue.clear();
        }

        @Override // reactor.core.publisher.FluxGroupJoin.JoinSupport
        public void innerError(Throwable th) {
            if (Exceptions.addThrowable(ERROR, this, th)) {
                ACTIVE.decrementAndGet(this);
                drain();
            } else {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        @Override // reactor.core.publisher.FluxGroupJoin.JoinSupport
        public void innerComplete(FluxGroupJoin.LeftRightSubscriber leftRightSubscriber) {
            this.cancellations.remove(leftRightSubscriber);
            ACTIVE.decrementAndGet(this);
            drain();
        }

        @Override // reactor.core.publisher.FluxGroupJoin.JoinSupport
        public void innerValue(boolean z, Object obj) {
            this.queueBiOffer.test(z ? LEFT_VALUE : RIGHT_VALUE, obj);
            drain();
        }

        @Override // reactor.core.publisher.FluxGroupJoin.JoinSupport
        public void innerClose(boolean z, FluxGroupJoin.LeftRightEndSubscriber leftRightEndSubscriber) {
            this.queueBiOffer.test(z ? LEFT_CLOSE : RIGHT_CLOSE, leftRightEndSubscriber);
            drain();
        }

        @Override // reactor.core.publisher.FluxGroupJoin.JoinSupport
        public void innerCloseError(Throwable th) {
            if (Exceptions.addThrowable(ERROR, this, th)) {
                drain();
            } else {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }
    }
}
