package reactor.core.publisher;

import android.Manifest;
import java.util.Iterator;
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
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxGroupJoin<TLeft, TRight, TLeftEnd, TRightEnd, R> extends InternalFluxOperator<TLeft, R> {
    final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
    final Publisher<? extends TRight> other;
    final Supplier<? extends Queue<TRight>> processorQueueSupplier;
    final BiFunction<? super TLeft, ? super Flux<TRight>, ? extends R> resultSelector;
    final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;

    interface JoinSupport<T> extends InnerProducer<T> {
        void innerClose(boolean z, LeftRightEndSubscriber leftRightEndSubscriber);

        void innerCloseError(Throwable th);

        void innerComplete(LeftRightSubscriber leftRightSubscriber);

        void innerError(Throwable th);

        void innerValue(boolean z, Object obj);
    }

    FluxGroupJoin(Flux<TLeft> flux, Publisher<? extends TRight> publisher, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super Flux<TRight>, ? extends R> biFunction, Supplier<? extends Queue<Object>> supplier, Supplier<? extends Queue<TRight>> supplier2) {
        super(flux);
        this.other = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(publisher, "other"));
        this.leftEnd = (Function) Objects.requireNonNull(function, "leftEnd");
        this.rightEnd = (Function) Objects.requireNonNull(function2, "rightEnd");
        this.processorQueueSupplier = (Supplier) Objects.requireNonNull(supplier2, "processorQueueSupplier");
        this.resultSelector = (BiFunction) Objects.requireNonNull(biFunction, "resultSelector");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super TLeft> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        GroupJoinSubscription groupJoinSubscription = new GroupJoinSubscription(coreSubscriber, this.leftEnd, this.rightEnd, this.resultSelector, this.processorQueueSupplier);
        coreSubscriber.onSubscribe(groupJoinSubscription);
        LeftRightSubscriber leftRightSubscriber = new LeftRightSubscriber(groupJoinSubscription, true);
        groupJoinSubscription.cancellations.add(leftRightSubscriber);
        LeftRightSubscriber leftRightSubscriber2 = new LeftRightSubscriber(groupJoinSubscription, false);
        groupJoinSubscription.cancellations.add(leftRightSubscriber2);
        this.source.subscribe((CoreSubscriber<? super Object>) leftRightSubscriber);
        this.other.subscribe(leftRightSubscriber2);
        return null;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class GroupJoinSubscription<TLeft, TRight, TLeftEnd, TRightEnd, R> implements JoinSupport<R> {
        volatile int active;
        final CoreSubscriber<? super R> actual;
        final Disposable.Composite cancellations = Disposables.composite();
        volatile Throwable error;
        final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
        int leftIndex;
        final Map<Integer, Sinks.Many<TRight>> lefts;
        final Supplier<? extends Queue<TRight>> processorQueueSupplier;
        final Queue<Object> queue;
        final BiPredicate<Object, Object> queueBiOffer;
        volatile long requested;
        final BiFunction<? super TLeft, ? super Flux<TRight>, ? extends R> resultSelector;
        final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
        int rightIndex;
        final Map<Integer, TRight> rights;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<GroupJoinSubscription> WIP = AtomicIntegerFieldUpdater.newUpdater(GroupJoinSubscription.class, "wip");
        static final AtomicIntegerFieldUpdater<GroupJoinSubscription> ACTIVE = AtomicIntegerFieldUpdater.newUpdater(GroupJoinSubscription.class, "active");
        static final AtomicLongFieldUpdater<GroupJoinSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(GroupJoinSubscription.class, "requested");
        static final AtomicReferenceFieldUpdater<GroupJoinSubscription, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(GroupJoinSubscription.class, Throwable.class, "error");
        static final Integer LEFT_VALUE = 1;
        static final Integer RIGHT_VALUE = 2;
        static final Integer LEFT_CLOSE = 3;
        static final Integer RIGHT_CLOSE = 4;

        GroupJoinSubscription(CoreSubscriber<? super R> coreSubscriber, Function<? super TLeft, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super TLeft, ? super Flux<TRight>, ? extends R> biFunction, Supplier<? extends Queue<TRight>> supplier) {
            this.actual = coreSubscriber;
            this.processorQueueSupplier = supplier;
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
            return Stream.concat(this.lefts.values().stream().map(new Function() { // from class: reactor.core.publisher.FluxGroupJoin$GroupJoinSubscription$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Scannable.from((Sinks.Many) obj);
                }
            }), Scannable.from(this.cancellations).inners());
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
            return attr == Scannable.Attr.ERROR ? this.error : super.scanUnsafe(attr);
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
            Iterator<Sinks.Many<TRight>> it = this.lefts.values().iterator();
            while (it.hasNext()) {
                it.next().emitError(thTerminate, Sinks.EmitFailureHandler.FAIL_FAST);
            }
            this.lefts.clear();
            this.rights.clear();
            subscriber.onError(thTerminate);
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            Queue<Object> queue = this.queue;
            CoreSubscriber<? super R> coreSubscriber = this.actual;
            int iAddAndGet = 1;
            while (!this.cancellations.isDisposed()) {
                if (this.error != null) {
                    queue.clear();
                    this.cancellations.dispose();
                    errorAll(coreSubscriber);
                    return;
                }
                boolean z = this.active == 0;
                Integer num = (Integer) queue.poll();
                boolean z2 = num == null;
                if (z && z2) {
                    Iterator<Sinks.Many<TRight>> it = this.lefts.values().iterator();
                    while (it.hasNext()) {
                        it.next().emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                    }
                    this.lefts.clear();
                    this.rights.clear();
                    this.cancellations.dispose();
                    coreSubscriber.onComplete();
                    return;
                }
                if (!z2) {
                    Object objPoll = queue.poll();
                    if (num == LEFT_VALUE) {
                        Sinks.Many manyOnBackpressureBuffer = Sinks.unsafe().many().unicast().onBackpressureBuffer(this.processorQueueSupplier.get());
                        int i = this.leftIndex;
                        this.leftIndex = i + 1;
                        this.lefts.put(Integer.valueOf(i), (Sinks.Many<TRight>) manyOnBackpressureBuffer);
                        try {
                            Publisher publisher = (Publisher) Objects.requireNonNull(this.leftEnd.apply(objPoll), "The leftEnd returned a null Publisher");
                            LeftRightEndSubscriber leftRightEndSubscriber = new LeftRightEndSubscriber(this, true, i);
                            this.cancellations.add(leftRightEndSubscriber);
                            Operators.toFluxOrMono(publisher).subscribe((Subscriber) leftRightEndSubscriber);
                            if (this.error != null) {
                                this.cancellations.dispose();
                                queue.clear();
                                errorAll(coreSubscriber);
                                return;
                            }
                            try {
                                Manifest manifest = (Object) Objects.requireNonNull(this.resultSelector.apply(objPoll, manyOnBackpressureBuffer.asFlux()), "The resultSelector returned a null value");
                                if (this.requested != 0) {
                                    coreSubscriber.onNext(manifest);
                                    Operators.produced(REQUESTED, this, 1L);
                                    Iterator<TRight> it2 = this.rights.values().iterator();
                                    while (it2.hasNext()) {
                                        manyOnBackpressureBuffer.emitNext(it2.next(), Sinks.EmitFailureHandler.FAIL_FAST);
                                    }
                                } else {
                                    Exceptions.addThrowable(ERROR, this, Exceptions.failWithOverflow());
                                    errorAll(coreSubscriber);
                                    return;
                                }
                            } catch (Throwable th) {
                                Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, th, manyOnBackpressureBuffer, this.actual.currentContext()));
                                errorAll(coreSubscriber);
                                return;
                            }
                        } catch (Throwable th2) {
                            Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, th2, objPoll, this.actual.currentContext()));
                            errorAll(coreSubscriber);
                            return;
                        }
                    } else if (num == RIGHT_VALUE) {
                        int i2 = this.rightIndex;
                        this.rightIndex = i2 + 1;
                        this.rights.put(Integer.valueOf(i2), (TRight) objPoll);
                        try {
                            Publisher publisher2 = (Publisher) Objects.requireNonNull(this.rightEnd.apply(objPoll), "The rightEnd returned a null Publisher");
                            LeftRightEndSubscriber leftRightEndSubscriber2 = new LeftRightEndSubscriber(this, false, i2);
                            this.cancellations.add(leftRightEndSubscriber2);
                            Operators.toFluxOrMono(publisher2).subscribe((Subscriber) leftRightEndSubscriber2);
                            if (this.error != null) {
                                queue.clear();
                                this.cancellations.dispose();
                                errorAll(coreSubscriber);
                                return;
                            } else {
                                Iterator<Sinks.Many<TRight>> it3 = this.lefts.values().iterator();
                                while (it3.hasNext()) {
                                    it3.next().emitNext(objPoll, Sinks.EmitFailureHandler.FAIL_FAST);
                                }
                            }
                        } catch (Throwable th3) {
                            Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, th3, objPoll, this.actual.currentContext()));
                            errorAll(coreSubscriber);
                            return;
                        }
                    } else if (num == LEFT_CLOSE) {
                        LeftRightEndSubscriber leftRightEndSubscriber3 = (LeftRightEndSubscriber) objPoll;
                        Sinks.Many<TRight> manyRemove = this.lefts.remove(Integer.valueOf(leftRightEndSubscriber3.index));
                        this.cancellations.remove(leftRightEndSubscriber3);
                        if (manyRemove != null) {
                            manyRemove.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                        }
                    } else if (num == RIGHT_CLOSE) {
                        LeftRightEndSubscriber leftRightEndSubscriber4 = (LeftRightEndSubscriber) objPoll;
                        this.rights.remove(Integer.valueOf(leftRightEndSubscriber4.index));
                        this.cancellations.remove(leftRightEndSubscriber4);
                    }
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
        public void innerComplete(LeftRightSubscriber leftRightSubscriber) {
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
        public void innerClose(boolean z, LeftRightEndSubscriber leftRightEndSubscriber) {
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

    static final class LeftRightSubscriber implements InnerConsumer<Object>, Disposable {
        static final AtomicReferenceFieldUpdater<LeftRightSubscriber, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(LeftRightSubscriber.class, Subscription.class, "subscription");
        final boolean isLeft;
        final JoinSupport<?> parent;
        volatile Subscription subscription;

        LeftRightSubscriber(JoinSupport<?> joinSupport, boolean z) {
            this.parent = joinSupport;
            this.isLeft = z;
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            Operators.terminate(SUBSCRIPTION, this);
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.actual().currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isDisposed());
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return Operators.cancelledSubscription() == this.subscription;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(SUBSCRIPTION, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            this.parent.innerValue(this.isLeft, obj);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.innerError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.innerComplete(this);
        }
    }

    static final class LeftRightEndSubscriber implements InnerConsumer<Object>, Disposable {
        static final AtomicReferenceFieldUpdater<LeftRightEndSubscriber, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(LeftRightEndSubscriber.class, Subscription.class, "subscription");
        final int index;
        final boolean isLeft;
        final JoinSupport<?> parent;
        volatile Subscription subscription;

        LeftRightEndSubscriber(JoinSupport<?> joinSupport, boolean z, int i) {
            this.parent = joinSupport;
            this.isLeft = z;
            this.index = i;
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            Operators.terminate(SUBSCRIPTION, this);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isDisposed());
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return Operators.cancelledSubscription() == this.subscription;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(SUBSCRIPTION, this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (Operators.terminate(SUBSCRIPTION, this)) {
                this.parent.innerClose(this.isLeft, this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.parent.innerError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.parent.innerClose(this.isLeft, this);
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.actual().currentContext();
        }
    }
}
