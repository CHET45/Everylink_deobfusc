package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.Spliterator;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxContextWriteRestoringThreadLocals;
import reactor.core.publisher.FluxContextWriteRestoringThreadLocalsFuseable;
import reactor.core.publisher.OnNextFailureStrategy;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
public abstract class Operators {
    static final Fuseable.ConditionalSubscriber<?> EMPTY_SUBSCRIBER = new Fuseable.ConditionalSubscriber<Object>() { // from class: reactor.core.publisher.Operators.1
        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            Operators.log.error("Unexpected call to Operators.emptySubscriber()", new IllegalStateException("onSubscribe should not be used"));
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            Operators.log.error("Unexpected call to Operators.emptySubscriber()", new IllegalStateException("onNext should not be used, got " + obj));
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(Object obj) {
            Operators.log.error("Unexpected call to Operators.emptySubscriber()", new IllegalStateException("tryOnNext should not be used, got " + obj));
            return false;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Operators.log.error("Unexpected call to Operators.emptySubscriber()", new IllegalStateException("onError should not be used", th));
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            Operators.log.error("Unexpected call to Operators.emptySubscriber()", new IllegalStateException("onComplete should not be used"));
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return Context.empty();
        }
    };
    static final Logger log = Loggers.getLogger((Class<?>) Operators.class);

    public static long addCap(long j, long j2) {
        long j3 = j + j2;
        if (j3 < 0) {
            return Long.MAX_VALUE;
        }
        return j3;
    }

    public static long subOrZero(long j, long j2) {
        long j3 = j - j2;
        if (j3 < 0) {
            return 0L;
        }
        return j3;
    }

    static int unboundedOrLimit(int i) {
        if (i == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return i - (i >> 2);
    }

    static long unboundedOrPrefetch(int i) {
        if (i == Integer.MAX_VALUE) {
            return Long.MAX_VALUE;
        }
        return i;
    }

    public static <T> long addCap(AtomicLongFieldUpdater<T> atomicLongFieldUpdater, T t, long j) {
        long j2;
        do {
            j2 = atomicLongFieldUpdater.get(t);
            if (j2 == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
        } while (!atomicLongFieldUpdater.compareAndSet(t, j2, addCap(j2, j)));
        return j2;
    }

    @Nullable
    /* JADX INFO: renamed from: as */
    public static <T> Fuseable.QueueSubscription<T> m1969as(Subscription subscription) {
        if (subscription instanceof Fuseable.QueueSubscription) {
            return (Fuseable.QueueSubscription) subscription;
        }
        return null;
    }

    public static Subscription cancelledSubscription() {
        return CancelledSubscription.INSTANCE;
    }

    public static void complete(Subscriber<?> subscriber) {
        subscriber.onSubscribe(EmptySubscription.INSTANCE);
        subscriber.onComplete();
    }

    public static <T> CoreSubscriber<T> drainSubscriber() {
        return DrainSubscriber.INSTANCE;
    }

    public static <T> CoreSubscriber<T> emptySubscriber() {
        return EMPTY_SUBSCRIBER;
    }

    public static Subscription emptySubscription() {
        return EmptySubscription.INSTANCE;
    }

    public static boolean canAppearAfterOnSubscribe(Subscription subscription) {
        return subscription == EmptySubscription.FROM_SUBSCRIBE_INSTANCE;
    }

    public static void error(Subscriber<?> subscriber, Throwable th) {
        subscriber.onSubscribe(EmptySubscription.INSTANCE);
        subscriber.onError(th);
    }

    public static void reportThrowInSubscribe(CoreSubscriber<?> coreSubscriber, Throwable th) {
        try {
            coreSubscriber.onSubscribe(EmptySubscription.FROM_SUBSCRIBE_INSTANCE);
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            th.addSuppressed(th2);
        }
        coreSubscriber.onError(onOperatorError(th, coreSubscriber.currentContext()));
    }

    public static <I, O> Function<? super Publisher<I>, ? extends Publisher<O>> lift(BiFunction<Scannable, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super I>> biFunction) {
        return LiftFunction.liftScannable(null, biFunction);
    }

    public static <O> Function<? super Publisher<O>, ? extends Publisher<O>> lift(Predicate<Scannable> predicate, BiFunction<Scannable, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super O>> biFunction) {
        return LiftFunction.liftScannable(predicate, biFunction);
    }

    public static <I, O> Function<? super Publisher<I>, ? extends Publisher<O>> liftPublisher(BiFunction<Publisher, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super I>> biFunction) {
        return LiftFunction.liftPublisher(null, biFunction);
    }

    public static <O> Function<? super Publisher<O>, ? extends Publisher<O>> liftPublisher(Predicate<Publisher> predicate, BiFunction<Publisher, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super O>> biFunction) {
        return LiftFunction.liftPublisher(predicate, biFunction);
    }

    public static long multiplyCap(long j, long j2) {
        long j3 = j * j2;
        if (((j | j2) >>> 31) == 0 || j3 / j == j2) {
            return j3;
        }
        return Long.MAX_VALUE;
    }

    static final <R> Function<Context, Context> discardLocalAdapter(final Class<R> cls, final Consumer<? super R> consumer) {
        Objects.requireNonNull(cls, "onDiscard must be based on a type");
        Objects.requireNonNull(consumer, "onDiscard must be provided a discardHook Consumer");
        final Consumer consumer2 = new Consumer() { // from class: reactor.core.publisher.Operators$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Operators.lambda$discardLocalAdapter$0(cls, consumer, obj);
            }
        };
        return new Function() { // from class: reactor.core.publisher.Operators$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Operators.lambda$discardLocalAdapter$1(consumer2, (Context) obj);
            }
        };
    }

    static /* synthetic */ void lambda$discardLocalAdapter$0(Class cls, Consumer consumer, Object obj) {
        if (cls.isInstance(obj)) {
            consumer.accept(cls.cast(obj));
        }
    }

    static /* synthetic */ Context lambda$discardLocalAdapter$1(Consumer consumer, Context context) {
        Consumer consumer2 = (Consumer) context.getOrDefault("reactor.onDiscard.local", null);
        if (consumer2 != null) {
            return context.put("reactor.onDiscard.local", consumer.andThen(consumer2));
        }
        return context.put("reactor.onDiscard.local", consumer);
    }

    public static final Context enableOnDiscard(@Nullable Context context, Consumer<?> consumer) {
        Objects.requireNonNull(consumer, "discardConsumer must be provided");
        if (context == null) {
            return Context.m1981of("reactor.onDiscard.local", consumer);
        }
        return context.put("reactor.onDiscard.local", consumer);
    }

    public static <T> void onDiscard(@Nullable T t, Context context) {
        Consumer consumer = (Consumer) context.getOrDefault("reactor.onDiscard.local", null);
        if (t == null || consumer == null) {
            return;
        }
        try {
            consumer.accept(t);
        } catch (Throwable th) {
            log.warn("Error in discard hook", th);
        }
    }

    public static <T> void onDiscardQueueWithClear(@Nullable Queue<T> queue, Context context, @Nullable Function<T, Stream<?>> function) {
        if (queue == null) {
            return;
        }
        final Consumer consumer = (Consumer) context.getOrDefault("reactor.onDiscard.local", null);
        if (consumer == null) {
            queue.clear();
            return;
        }
        while (true) {
            try {
                T tPoll = queue.poll();
                if (tPoll == null) {
                    return;
                }
                if (function != null) {
                    try {
                        function.apply(tPoll).forEach(new Consumer() { // from class: reactor.core.publisher.Operators$$ExternalSyntheticLambda6
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Operators.lambda$onDiscardQueueWithClear$2(consumer, obj);
                            }
                        });
                    } catch (Throwable th) {
                        log.warn("Error while extracting items to discard from queue element, continuing with next queue element", th);
                    }
                } else {
                    try {
                        consumer.accept(tPoll);
                    } catch (Throwable th2) {
                        log.warn("Error while discarding a queue element, continuing with next queue element", th2);
                    }
                }
            } catch (Throwable th3) {
                log.warn("Cannot further apply discard hook while discarding and clearing a queue", th3);
                return;
            }
        }
    }

    static /* synthetic */ void lambda$onDiscardQueueWithClear$2(Consumer consumer, Object obj) {
        try {
            consumer.accept(obj);
        } catch (Throwable th) {
            log.warn("Error while discarding item extracted from a queue element, continuing with next item", th);
        }
    }

    public static void onDiscardMultiple(Stream<?> stream, Context context) {
        final Consumer consumer = (Consumer) context.getOrDefault("reactor.onDiscard.local", null);
        if (consumer != null) {
            try {
                stream.filter(new Predicate() { // from class: reactor.core.publisher.Operators$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return Objects.nonNull(obj);
                    }
                }).forEach(new Consumer() { // from class: reactor.core.publisher.Operators$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        Operators.lambda$onDiscardMultiple$3(consumer, obj);
                    }
                });
            } catch (Throwable th) {
                log.warn("Error while discarding stream, stopping", th);
            }
        }
    }

    static /* synthetic */ void lambda$onDiscardMultiple$3(Consumer consumer, Object obj) {
        try {
            consumer.accept(obj);
        } catch (Throwable th) {
            log.warn("Error while discarding a stream element, continuing with next element", th);
        }
    }

    public static void onDiscardMultiple(@Nullable Collection<?> collection, Context context) {
        Consumer consumer;
        if (collection == null || (consumer = (Consumer) context.getOrDefault("reactor.onDiscard.local", null)) == null) {
            return;
        }
        try {
            if (collection.isEmpty()) {
                return;
            }
            for (Object obj : collection) {
                if (obj != null) {
                    try {
                        consumer.accept(obj);
                    } catch (Throwable th) {
                        log.warn("Error while discarding element from a Collection, continuing with next element", th);
                    }
                }
            }
        } catch (Throwable th2) {
            log.warn("Error while discarding collection, stopping", th2);
        }
    }

    public static void onDiscardMultiple(@Nullable Iterator<?> it, boolean z, Context context) {
        final Consumer consumer;
        if (it == null || !z || (consumer = (Consumer) context.getOrDefault("reactor.onDiscard.local", null)) == null) {
            return;
        }
        try {
            it.forEachRemaining(new Consumer() { // from class: reactor.core.publisher.Operators$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Operators.lambda$onDiscardMultiple$4(consumer, obj);
                }
            });
        } catch (Throwable th) {
            log.warn("Error while discarding Iterator, stopping", th);
        }
    }

    static /* synthetic */ void lambda$onDiscardMultiple$4(Consumer consumer, Object obj) {
        if (obj != null) {
            try {
                consumer.accept(obj);
            } catch (Throwable th) {
                log.warn("Error while discarding element from an Iterator, continuing with next element", th);
            }
        }
    }

    public static void onDiscardMultiple(@Nullable Spliterator<?> spliterator, boolean z, Context context) {
        final Consumer consumer;
        if (spliterator == null || !z || (consumer = (Consumer) context.getOrDefault("reactor.onDiscard.local", null)) == null) {
            return;
        }
        try {
            spliterator.forEachRemaining(new Consumer() { // from class: reactor.core.publisher.Operators$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Operators.lambda$onDiscardMultiple$5(consumer, obj);
                }
            });
        } catch (Throwable th) {
            log.warn("Error while discarding Spliterator, stopping", th);
        }
    }

    static /* synthetic */ void lambda$onDiscardMultiple$5(Consumer consumer, Object obj) {
        if (obj != null) {
            try {
                consumer.accept(obj);
            } catch (Throwable th) {
                log.warn("Error while discarding element from an Spliterator, continuing with next element", th);
            }
        }
    }

    public static void onErrorDropped(Throwable th, Context context) {
        Consumer<? super Throwable> consumer = (Consumer) context.getOrDefault("reactor.onErrorDropped.local", null);
        if (consumer == null) {
            consumer = Hooks.onErrorDroppedHook;
        }
        if (consumer == null) {
            log.error("Operator called default onErrorDropped", th);
        } else {
            consumer.accept(th);
        }
    }

    public static <T> void onNextDropped(T t, Context context) {
        Objects.requireNonNull(t, "onNext");
        Objects.requireNonNull(context, "context");
        Consumer<Object> consumer = (Consumer) context.getOrDefault("reactor.onNextDropped.local", null);
        if (consumer == null) {
            consumer = Hooks.onNextDroppedHook;
        }
        if (consumer != null) {
            consumer.accept(t);
            return;
        }
        Logger logger = log;
        if (logger.isDebugEnabled()) {
            logger.debug("onNextDropped: " + t);
        }
    }

    public static Throwable onOperatorError(Throwable th, Context context) {
        return onOperatorError(null, th, context);
    }

    public static Throwable onOperatorError(@Nullable Subscription subscription, Throwable th, Context context) {
        return onOperatorError(subscription, th, null, context);
    }

    public static Throwable onOperatorError(@Nullable Subscription subscription, Throwable th, @Nullable Object obj, Context context) {
        Exceptions.throwIfFatal(th);
        if (subscription != null) {
            subscription.cancel();
        }
        Throwable thUnwrap = Exceptions.unwrap(th);
        BiFunction<? super Throwable, Object, ? extends Throwable> biFunction = (BiFunction) context.getOrDefault("reactor.onOperatorError.local", null);
        if (biFunction == null) {
            biFunction = Hooks.onOperatorErrorHook;
        }
        if (biFunction == null) {
            return (obj == null || obj == thUnwrap || !(obj instanceof Throwable)) ? thUnwrap : Exceptions.addSuppressed(thUnwrap, (Throwable) obj);
        }
        return biFunction.apply(th, obj);
    }

    public static RuntimeException onRejectedExecution(Throwable th, Context context) {
        return onRejectedExecution(th, null, null, null, context);
    }

    static final OnNextFailureStrategy onNextErrorStrategy(Context context) {
        OnNextFailureStrategy lambdaOnNextErrorStrategy = null;
        BiFunction biFunction = (BiFunction) context.getOrDefault(OnNextFailureStrategy.KEY_ON_NEXT_ERROR_STRATEGY, null);
        if (biFunction instanceof OnNextFailureStrategy) {
            lambdaOnNextErrorStrategy = (OnNextFailureStrategy) biFunction;
        } else if (biFunction != null) {
            lambdaOnNextErrorStrategy = new OnNextFailureStrategy.LambdaOnNextErrorStrategy(biFunction);
        }
        if (lambdaOnNextErrorStrategy == null) {
            lambdaOnNextErrorStrategy = Hooks.onNextErrorHook;
        }
        return lambdaOnNextErrorStrategy == null ? OnNextFailureStrategy.STOP : lambdaOnNextErrorStrategy;
    }

    public static final BiFunction<? super Throwable, Object, ? extends Throwable> onNextErrorFunction(Context context) {
        return onNextErrorStrategy(context);
    }

    @Nullable
    public static <T> Throwable onNextError(@Nullable T t, Throwable th, Context context, Subscription subscription) {
        Throwable thUnwrapOnNextError = unwrapOnNextError(th);
        OnNextFailureStrategy onNextFailureStrategyOnNextErrorStrategy = onNextErrorStrategy(context);
        if (onNextFailureStrategyOnNextErrorStrategy.test(thUnwrapOnNextError, (Object) t)) {
            Throwable thProcess = onNextFailureStrategyOnNextErrorStrategy.process(thUnwrapOnNextError, t, context);
            if (thProcess != null) {
                subscription.cancel();
            }
            return thProcess;
        }
        return onOperatorError(subscription, thUnwrapOnNextError, t, context);
    }

    @Nullable
    public static <T> Throwable onNextError(@Nullable T t, Throwable th, Context context) {
        Throwable thUnwrapOnNextError = unwrapOnNextError(th);
        OnNextFailureStrategy onNextFailureStrategyOnNextErrorStrategy = onNextErrorStrategy(context);
        if (onNextFailureStrategyOnNextErrorStrategy.test(thUnwrapOnNextError, (Object) t)) {
            return onNextFailureStrategyOnNextErrorStrategy.process(thUnwrapOnNextError, t, context);
        }
        return onOperatorError(null, thUnwrapOnNextError, t, context);
    }

    public static <T> Throwable onNextInnerError(Throwable th, Context context, @Nullable Subscription subscription) {
        Throwable thUnwrapOnNextError = unwrapOnNextError(th);
        OnNextFailureStrategy onNextFailureStrategyOnNextErrorStrategy = onNextErrorStrategy(context);
        if (onNextFailureStrategyOnNextErrorStrategy.test(thUnwrapOnNextError, (Object) null) && (thUnwrapOnNextError = onNextFailureStrategyOnNextErrorStrategy.process(thUnwrapOnNextError, null, context)) != null && subscription != null) {
            subscription.cancel();
        }
        return thUnwrapOnNextError;
    }

    @Nullable
    public static <T> RuntimeException onNextPollError(@Nullable T t, Throwable th, Context context) {
        Throwable thUnwrapOnNextError = unwrapOnNextError(th);
        OnNextFailureStrategy onNextFailureStrategyOnNextErrorStrategy = onNextErrorStrategy(context);
        if (onNextFailureStrategyOnNextErrorStrategy.test(thUnwrapOnNextError, (Object) t)) {
            Throwable thProcess = onNextFailureStrategyOnNextErrorStrategy.process(thUnwrapOnNextError, t, context);
            if (thProcess != null) {
                return Exceptions.propagate(thProcess);
            }
            return null;
        }
        return Exceptions.propagate(onOperatorError(null, thUnwrapOnNextError, t, context));
    }

    public static <T> CorePublisher<T> onLastAssembly(CorePublisher<T> corePublisher) {
        Function<Publisher, Publisher> function = Hooks.onLastOperatorHook;
        if (function == null) {
            return corePublisher;
        }
        Publisher publisher = (Publisher) Objects.requireNonNull(function.apply(corePublisher), "LastOperator hook returned null");
        if (publisher instanceof CorePublisher) {
            return (CorePublisher) publisher;
        }
        return new CorePublisherAdapter(publisher);
    }

    public static <T> CorePublisher<T> toFluxOrMono(Publisher<T> publisher) {
        if (publisher instanceof Mono) {
            return Mono.fromDirect(publisher);
        }
        return Flux.from(publisher);
    }

    public static <T> void toFluxOrMono(Publisher<? extends T>[] publisherArr) {
        for (int i = 0; i < publisherArr.length; i++) {
            Publisher<? extends T> publisher = publisherArr[i];
            if (publisher != null) {
                publisherArr[i] = toFluxOrMono(publisher);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> CoreSubscriber<T> restoreContextOnSubscriberIfPublisherNonInternal(Publisher<?> publisher, CoreSubscriber<T> coreSubscriber) {
        return ContextPropagationSupport.shouldWrapPublisher(publisher) ? restoreContextOnSubscriber(publisher, coreSubscriber) : coreSubscriber;
    }

    static <T> CoreSubscriber<? super T> restoreContextOnSubscriberIfAutoCPEnabled(Publisher<?> publisher, CoreSubscriber<? super T> coreSubscriber) {
        return ContextPropagationSupport.shouldPropagateContextToThreadLocals() ? restoreContextOnSubscriber(publisher, coreSubscriber) : coreSubscriber;
    }

    static <T> CoreSubscriber<T> restoreContextOnSubscriber(Publisher<?> publisher, CoreSubscriber<T> coreSubscriber) {
        if (publisher instanceof Fuseable) {
            return new FluxContextWriteRestoringThreadLocalsFuseable.FuseableContextWriteRestoringThreadLocalsSubscriber(coreSubscriber, coreSubscriber.currentContext());
        }
        return new FluxContextWriteRestoringThreadLocals.ContextWriteRestoringThreadLocalsSubscriber(coreSubscriber, coreSubscriber.currentContext());
    }

    static <T> CoreSubscriber<? super T>[] restoreContextOnSubscribers(Publisher<?> publisher, CoreSubscriber<? super T>[] coreSubscriberArr) {
        CoreSubscriber<? super T>[] coreSubscriberArr2 = new CoreSubscriber[coreSubscriberArr.length];
        for (int i = 0; i < coreSubscriberArr.length; i++) {
            coreSubscriberArr2[i] = restoreContextOnSubscriber(publisher, coreSubscriberArr[i]);
        }
        return coreSubscriberArr2;
    }

    private static Throwable unwrapOnNextError(Throwable th) {
        return Exceptions.isBubbling(th) ? th : Exceptions.unwrap(th);
    }

    public static RuntimeException onRejectedExecution(Throwable th, @Nullable Subscription subscription, @Nullable Throwable th2, @Nullable Object obj, Context context) {
        if (context.hasKey("reactor.onRejectedExecution.local")) {
            context = context.put("reactor.onOperatorError.local", context.get("reactor.onRejectedExecution.local"));
        }
        RejectedExecutionException rejectedExecutionExceptionFailWithRejected = Exceptions.failWithRejected(th);
        if (th2 != null) {
            rejectedExecutionExceptionFailWithRejected.addSuppressed(th2);
        }
        if (obj != null) {
            return Exceptions.propagate(onOperatorError(subscription, rejectedExecutionExceptionFailWithRejected, obj, context));
        }
        return Exceptions.propagate(onOperatorError(subscription, rejectedExecutionExceptionFailWithRejected, context));
    }

    public static <T> long produced(AtomicLongFieldUpdater<T> atomicLongFieldUpdater, T t, long j) {
        long j2;
        long jSubOrZero;
        do {
            j2 = atomicLongFieldUpdater.get(t);
            if (j2 == 0 || j2 == Long.MAX_VALUE) {
                return j2;
            }
            jSubOrZero = subOrZero(j2, j);
        } while (!atomicLongFieldUpdater.compareAndSet(t, j2, jSubOrZero));
        return jSubOrZero;
    }

    public static <F> boolean replace(AtomicReferenceFieldUpdater<F, Subscription> atomicReferenceFieldUpdater, F f, Subscription subscription) {
        Subscription subscription2;
        do {
            subscription2 = atomicReferenceFieldUpdater.get(f);
            if (subscription2 == CancelledSubscription.INSTANCE) {
                subscription.cancel();
                return false;
            }
        } while (!C0162xc40028dd.m5m(atomicReferenceFieldUpdater, f, subscription2, subscription));
        return true;
    }

    public static void reportBadRequest(long j) {
        Logger logger = log;
        if (logger.isDebugEnabled()) {
            logger.debug("Negative request", Exceptions.nullOrNegativeRequestException(j));
        }
    }

    public static void reportMoreProduced() {
        Logger logger = log;
        if (logger.isDebugEnabled()) {
            logger.debug("More data produced than requested", Exceptions.failWithOverflow());
        }
    }

    public static void reportSubscriptionSet() {
        Logger logger = log;
        if (logger.isDebugEnabled()) {
            logger.debug("Duplicate Subscription has been detected", Exceptions.duplicateOnSubscribeException());
        }
    }

    public static <T> Subscription scalarSubscription(CoreSubscriber<? super T> coreSubscriber, T t) {
        return new ScalarSubscription(coreSubscriber, t);
    }

    public static <T> Subscription scalarSubscription(CoreSubscriber<? super T> coreSubscriber, T t, String str) {
        return new ScalarSubscription(coreSubscriber, t, str);
    }

    public static <T> CoreSubscriber<T> serialize(CoreSubscriber<? super T> coreSubscriber) {
        return new SerializedSubscriber(coreSubscriber);
    }

    public static <F> boolean set(AtomicReferenceFieldUpdater<F, Subscription> atomicReferenceFieldUpdater, F f, Subscription subscription) {
        Subscription subscription2;
        do {
            subscription2 = atomicReferenceFieldUpdater.get(f);
            if (subscription2 == CancelledSubscription.INSTANCE) {
                subscription.cancel();
                return false;
            }
        } while (!C0162xc40028dd.m5m(atomicReferenceFieldUpdater, f, subscription2, subscription));
        if (subscription2 == null) {
            return true;
        }
        subscription2.cancel();
        return true;
    }

    public static <F> boolean setOnce(AtomicReferenceFieldUpdater<F, Subscription> atomicReferenceFieldUpdater, F f, Subscription subscription) {
        Objects.requireNonNull(subscription, "subscription");
        Subscription subscription2 = atomicReferenceFieldUpdater.get(f);
        if (subscription2 == CancelledSubscription.INSTANCE) {
            subscription.cancel();
            return false;
        }
        if (subscription2 != null) {
            subscription.cancel();
            reportSubscriptionSet();
            return false;
        }
        if (C0162xc40028dd.m5m(atomicReferenceFieldUpdater, f, null, subscription)) {
            return true;
        }
        if (atomicReferenceFieldUpdater.get(f) == CancelledSubscription.INSTANCE) {
            subscription.cancel();
            return false;
        }
        subscription.cancel();
        reportSubscriptionSet();
        return false;
    }

    public static <F> boolean terminate(AtomicReferenceFieldUpdater<F, Subscription> atomicReferenceFieldUpdater, F f) {
        Subscription andSet;
        if (atomicReferenceFieldUpdater.get(f) == CancelledSubscription.INSTANCE || (andSet = atomicReferenceFieldUpdater.getAndSet(f, CancelledSubscription.INSTANCE)) == null || andSet == CancelledSubscription.INSTANCE) {
            return false;
        }
        andSet.cancel();
        return true;
    }

    public static boolean validate(@Nullable Subscription subscription, Subscription subscription2) {
        Objects.requireNonNull(subscription2, "Subscription cannot be null");
        if (subscription == null) {
            return true;
        }
        subscription2.cancel();
        return false;
    }

    public static boolean validate(long j) {
        if (j > 0) {
            return true;
        }
        reportBadRequest(j);
        return false;
    }

    public static <T> CoreSubscriber<? super T> toCoreSubscriber(Subscriber<? super T> subscriber) {
        Objects.requireNonNull(subscriber, "actual");
        if (subscriber instanceof CoreSubscriber) {
            return (CoreSubscriber) subscriber;
        }
        return new StrictSubscriber(subscriber);
    }

    public static <T> Fuseable.ConditionalSubscriber<? super T> toConditionalSubscriber(CoreSubscriber<? super T> coreSubscriber) {
        Objects.requireNonNull(coreSubscriber, "actual");
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return (Fuseable.ConditionalSubscriber) coreSubscriber;
        }
        return new ConditionalSubscriberAdapter(coreSubscriber);
    }

    static Context multiSubscribersContext(InnerProducer<?>[] innerProducerArr) {
        if (innerProducerArr.length > 0) {
            return innerProducerArr[0].actual().currentContext();
        }
        return Context.empty();
    }

    static <T> long addCapCancellable(AtomicLongFieldUpdater<T> atomicLongFieldUpdater, T t, long j) {
        long j2;
        do {
            j2 = atomicLongFieldUpdater.get(t);
            if (j2 == Long.MIN_VALUE || j2 == Long.MAX_VALUE) {
                break;
            }
        } while (!atomicLongFieldUpdater.compareAndSet(t, j2, addCap(j2, j)));
        return j2;
    }

    static void onErrorDroppedMulticast(Throwable th, InnerProducer<?>[] innerProducerArr) {
        onErrorDropped(th, multiSubscribersContext(innerProducerArr));
    }

    static <T> void onNextDroppedMulticast(T t, InnerProducer<?>[] innerProducerArr) {
        onNextDropped(t, multiSubscribersContext(innerProducerArr));
    }

    static <T> long producedCancellable(AtomicLongFieldUpdater<T> atomicLongFieldUpdater, T t, long j) {
        long j2;
        long j3;
        do {
            j2 = atomicLongFieldUpdater.get(t);
            if (j2 == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            if (j2 == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            long j4 = j2 - j;
            if (j4 < 0) {
                reportBadRequest(j4);
                j3 = 0;
            } else {
                j3 = j4;
            }
        } while (!atomicLongFieldUpdater.compareAndSet(t, j2, j3));
        return j3;
    }

    static int unboundedOrLimit(int i, int i2) {
        if (i2 <= 0) {
            return i;
        }
        if (i2 >= i) {
            return unboundedOrLimit(i);
        }
        if (i == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return i2;
    }

    Operators() {
    }

    static final class CorePublisherAdapter<T> implements CorePublisher<T> {
        final Publisher<T> publisher;

        CorePublisherAdapter(Publisher<T> publisher) {
            this.publisher = publisher;
        }

        @Override // reactor.core.CorePublisher
        public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
            this.publisher.subscribe(coreSubscriber);
        }

        @Override // org.reactivestreams.Publisher
        public void subscribe(Subscriber<? super T> subscriber) {
            this.publisher.subscribe(subscriber);
        }
    }

    static final class CancelledSubscription implements Subscription, Scannable {
        static final CancelledSubscription INSTANCE = new CancelledSubscription();

        @Override // org.reactivestreams.Subscription
        public void cancel() {
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
        }

        CancelledSubscription() {
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.CANCELLED ? true : null;
        }

        @Override // reactor.core.Scannable
        public String stepName() {
            return "cancelledSubscription";
        }
    }

    static final class EmptySubscription implements Fuseable.QueueSubscription<Object>, Scannable {
        static final EmptySubscription INSTANCE = new EmptySubscription();
        static final EmptySubscription FROM_SUBSCRIBE_INSTANCE = new EmptySubscription();

        @Override // org.reactivestreams.Subscription
        public void cancel() {
        }

        @Override // java.util.Collection
        public void clear() {
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return true;
        }

        @Override // java.util.Queue
        @Nullable
        public Object poll() {
            return null;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return 0;
        }

        @Override // java.util.Collection
        public int size() {
            return 0;
        }

        EmptySubscription() {
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? true : null;
        }

        @Override // reactor.core.Scannable
        public String stepName() {
            return "emptySubscription";
        }
    }

    public static class DeferredSubscription implements Subscription, Scannable {
        static final AtomicLongFieldUpdater<DeferredSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(DeferredSubscription.class, "requested");
        static final int STATE_CANCELLED = -2;
        static final int STATE_SUBSCRIBED = -1;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2281s;

        protected boolean isCancelled() {
            return this.requested == -2;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            long andSet = REQUESTED.getAndSet(this, -2L);
            if (andSet != -2 && andSet == -1) {
                this.f2281s.cancel();
            }
        }

        protected void terminate() {
            REQUESTED.getAndSet(this, -2L);
        }

        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            long j = this.requested;
            if (attr == Scannable.Attr.PARENT) {
                return this.f2281s;
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                if (j < 0) {
                    j = 0;
                }
                return Long.valueOf(j);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isCancelled());
            }
            return null;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            long j2 = this.requested;
            if (j2 > -1) {
                long j3 = j2;
                while (j3 != Long.MAX_VALUE) {
                    if (REQUESTED.compareAndSet(this, j3, Operators.addCap(j3, j))) {
                        return;
                    }
                    j3 = this.requested;
                    if (j3 < 0) {
                        j2 = j3;
                    }
                }
                return;
            }
            if (j2 == -2) {
                return;
            }
            this.f2281s.request(j);
        }

        public final boolean set(Subscription subscription) {
            Objects.requireNonNull(subscription, "s");
            long j = this.requested;
            Subscription subscription2 = this.f2281s;
            if (j == -2) {
                subscription.cancel();
                return false;
            }
            if (subscription2 != null) {
                subscription.cancel();
                Operators.reportSubscriptionSet();
                return false;
            }
            long j2 = 0;
            while (true) {
                long j3 = this.requested;
                if (j3 == -2 || j3 == -1) {
                    break;
                }
                this.f2281s = subscription;
                long j4 = j3 - j2;
                if (j4 > 0) {
                    subscription.request(j4);
                }
                long j5 = j2 + j4;
                if (REQUESTED.compareAndSet(this, j3, -1L)) {
                    return true;
                }
                j2 = j5;
            }
            subscription.cancel();
            return false;
        }
    }

    public static class MonoSubscriber<I, O> implements InnerOperator<I, O>, Fuseable, Fuseable.QueueSubscription<O> {
        static final int CANCELLED = 4;
        static final int HAS_REQUEST_HAS_VALUE = 3;
        static final int HAS_REQUEST_NO_VALUE = 2;
        static final int NO_REQUEST_HAS_VALUE = 1;
        static final int NO_REQUEST_NO_VALUE = 0;
        static final AtomicIntegerFieldUpdater<MonoSubscriber> STATE = AtomicIntegerFieldUpdater.newUpdater(MonoSubscriber.class, "state");
        protected final CoreSubscriber<? super O> actual;
        volatile int state;

        @Nullable
        protected O value;

        @Override // java.util.Collection
        public final boolean isEmpty() {
            return true;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
        }

        @Override // java.util.Queue
        @Nullable
        public final O poll() {
            return null;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return 0;
        }

        public MonoSubscriber(CoreSubscriber<? super O> coreSubscriber) {
            this.actual = coreSubscriber;
        }

        public void cancel() {
            O o = this.value;
            this.value = null;
            STATE.set(this, 4);
            discard(o);
        }

        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isCancelled());
            }
            if (attr != Scannable.Attr.TERMINATED) {
                if (attr == Scannable.Attr.PREFETCH) {
                    return Integer.MAX_VALUE;
                }
                return super.scanUnsafe(attr);
            }
            boolean z = true;
            if (this.state != 3 && this.state != 1) {
                z = false;
            }
            return Boolean.valueOf(z);
        }

        @Override // java.util.Collection
        public final void clear() {
            this.value = null;
        }

        public final void complete(@Nullable O o) {
            while (true) {
                int i = this.state;
                if ((i & (-3)) != 0) {
                    this.value = null;
                    discard(o);
                    return;
                } else {
                    if (i == 2 && STATE.compareAndSet(this, 2, 3)) {
                        this.value = null;
                        CoreSubscriber<? super O> coreSubscriber = this.actual;
                        coreSubscriber.onNext(o);
                        coreSubscriber.onComplete();
                        return;
                    }
                    setValue(o);
                    if (i == 0 && STATE.compareAndSet(this, 0, 1)) {
                        return;
                    }
                }
            }
        }

        protected void discard(@Nullable O o) {
            Operators.onDiscard(o, this.actual.currentContext());
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super O> actual() {
            return this.actual;
        }

        public final boolean isCancelled() {
            return this.state == 4;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.reactivestreams.Subscriber
        public void onNext(I i) {
            setValue(i);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                do {
                    int i = this.state;
                    if (i == 4 || (i & (-2)) != 0) {
                        return;
                    }
                    if (i == 1 && STATE.compareAndSet(this, 1, 3)) {
                        O o = this.value;
                        if (o != null) {
                            this.value = null;
                            CoreSubscriber<? super O> coreSubscriber = this.actual;
                            coreSubscriber.onNext(o);
                            coreSubscriber.onComplete();
                            return;
                        }
                        return;
                    }
                } while (!STATE.compareAndSet(this, 0, 2));
            }
        }

        public void setValue(@Nullable O o) {
            if (STATE.get(this) == 4) {
                discard(o);
            } else {
                this.value = o;
            }
        }

        @Override // java.util.Collection
        public int size() {
            return !isEmpty() ? 1 : 0;
        }
    }

    static abstract class BaseFluxToMonoOperator<I, O> implements InnerOperator<I, O>, Fuseable, Fuseable.QueueSubscription<I> {
        static final AtomicIntegerFieldUpdater<BaseFluxToMonoOperator> STATE = AtomicIntegerFieldUpdater.newUpdater(BaseFluxToMonoOperator.class, "state");
        final CoreSubscriber<? super O> actual;
        boolean hasRequest;

        /* JADX INFO: renamed from: s */
        Subscription f2280s;
        volatile int state;

        @Nullable
        abstract O accumulatedValue();

        @Override // java.util.Collection
        public final void clear() {
        }

        @Override // java.util.Collection
        public final boolean isEmpty() {
            return true;
        }

        @Override // java.util.Queue
        public final I poll() {
            return null;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public final int requestFusion(int i) {
            return 0;
        }

        @Override // java.util.Collection
        public final int size() {
            return 0;
        }

        BaseFluxToMonoOperator(CoreSubscriber<? super O> coreSubscriber) {
            this.actual = coreSubscriber;
        }

        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PREFETCH) {
                return 0;
            }
            return attr == Scannable.Attr.PARENT ? this.f2280s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super O> actual() {
            return this.actual;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2280s, subscription)) {
                this.f2280s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        public void request(long j) {
            if (this.hasRequest) {
                return;
            }
            this.hasRequest = true;
            int i = this.state;
            if ((i & 1) != 1 && STATE.compareAndSet(this, i, i | 1)) {
                if (i == 0) {
                    this.f2280s.request(Long.MAX_VALUE);
                    return;
                }
                O oAccumulatedValue = accumulatedValue();
                if (oAccumulatedValue == null) {
                    return;
                }
                this.actual.onNext(oAccumulatedValue);
                this.actual.onComplete();
            }
        }

        public void cancel() {
            this.f2280s.cancel();
        }

        final void completePossiblyEmpty() {
            O oAccumulatedValue;
            if (this.hasRequest) {
                O oAccumulatedValue2 = accumulatedValue();
                if (oAccumulatedValue2 == null) {
                    return;
                }
                this.actual.onNext(oAccumulatedValue2);
                this.actual.onComplete();
                return;
            }
            if ((this.state == 0 && STATE.compareAndSet(this, 0, 2)) || (oAccumulatedValue = accumulatedValue()) == null) {
                return;
            }
            this.actual.onNext(oAccumulatedValue);
            this.actual.onComplete();
        }
    }

    static abstract class MultiSubscriptionSubscriber<I, O> implements InnerOperator<I, O> {
        final CoreSubscriber<? super O> actual;
        volatile boolean cancelled;
        volatile long missedProduced;
        volatile long missedRequested;
        volatile Subscription missedSubscription;
        long requested;
        Subscription subscription;
        protected boolean unbounded;
        volatile int wip;
        static final AtomicReferenceFieldUpdater<MultiSubscriptionSubscriber, Subscription> MISSED_SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(MultiSubscriptionSubscriber.class, Subscription.class, "missedSubscription");
        static final AtomicLongFieldUpdater<MultiSubscriptionSubscriber> MISSED_REQUESTED = AtomicLongFieldUpdater.newUpdater(MultiSubscriptionSubscriber.class, "missedRequested");
        static final AtomicLongFieldUpdater<MultiSubscriptionSubscriber> MISSED_PRODUCED = AtomicLongFieldUpdater.newUpdater(MultiSubscriptionSubscriber.class, "missedProduced");
        static final AtomicIntegerFieldUpdater<MultiSubscriptionSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(MultiSubscriptionSubscriber.class, "wip");

        protected boolean shouldCancelCurrent() {
            return false;
        }

        public MultiSubscriptionSubscriber(CoreSubscriber<? super O> coreSubscriber) {
            this.actual = coreSubscriber;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super O> actual() {
            return this.actual;
        }

        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            drain();
        }

        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.missedSubscription != null ? this.missedSubscription : this.subscription;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isCancelled());
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(Operators.addCap(this.requested, this.missedRequested));
            }
            return super.scanUnsafe(attr);
        }

        public final boolean isUnbounded() {
            return this.unbounded;
        }

        final boolean isCancelled() {
            return this.cancelled;
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            set(subscription);
        }

        public final void produced(long j) {
            if (this.unbounded) {
                return;
            }
            if (this.wip == 0) {
                AtomicIntegerFieldUpdater<MultiSubscriptionSubscriber> atomicIntegerFieldUpdater = WIP;
                if (atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                    long j2 = this.requested;
                    if (j2 != Long.MAX_VALUE) {
                        long j3 = j2 - j;
                        if (j3 < 0) {
                            Operators.reportMoreProduced();
                            j3 = 0;
                        }
                        this.requested = j3;
                    } else {
                        this.unbounded = true;
                    }
                    if (atomicIntegerFieldUpdater.decrementAndGet(this) == 0) {
                        return;
                    }
                    drainLoop();
                    return;
                }
            }
            Operators.addCap(MISSED_PRODUCED, this, j);
            drain();
        }

        final void producedOne() {
            if (this.unbounded) {
                return;
            }
            if (this.wip == 0) {
                AtomicIntegerFieldUpdater<MultiSubscriptionSubscriber> atomicIntegerFieldUpdater = WIP;
                if (atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                    long j = this.requested;
                    if (j != Long.MAX_VALUE) {
                        long j2 = j - 1;
                        if (j2 < 0) {
                            Operators.reportMoreProduced();
                            j2 = 0;
                        }
                        this.requested = j2;
                    } else {
                        this.unbounded = true;
                    }
                    if (atomicIntegerFieldUpdater.decrementAndGet(this) == 0) {
                        return;
                    }
                    drainLoop();
                    return;
                }
            }
            Operators.addCap(MISSED_PRODUCED, this, 1L);
            drain();
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (!Operators.validate(j) || this.unbounded) {
                return;
            }
            if (this.wip == 0) {
                AtomicIntegerFieldUpdater<MultiSubscriptionSubscriber> atomicIntegerFieldUpdater = WIP;
                if (atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                    long j2 = this.requested;
                    if (j2 != Long.MAX_VALUE) {
                        long jAddCap = Operators.addCap(j2, j);
                        this.requested = jAddCap;
                        if (jAddCap == Long.MAX_VALUE) {
                            this.unbounded = true;
                        }
                    }
                    Subscription subscription = this.subscription;
                    if (atomicIntegerFieldUpdater.decrementAndGet(this) != 0) {
                        drainLoop();
                    }
                    if (subscription != null) {
                        subscription.request(j);
                        return;
                    }
                    return;
                }
            }
            Operators.addCap(MISSED_REQUESTED, this, j);
            drain();
        }

        public final void set(Subscription subscription) {
            if (this.cancelled) {
                subscription.cancel();
                return;
            }
            Objects.requireNonNull(subscription);
            if (this.wip == 0) {
                AtomicIntegerFieldUpdater<MultiSubscriptionSubscriber> atomicIntegerFieldUpdater = WIP;
                if (atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                    Subscription subscription2 = this.subscription;
                    if (subscription2 != null && shouldCancelCurrent()) {
                        subscription2.cancel();
                    }
                    this.subscription = subscription;
                    long j = this.requested;
                    if (atomicIntegerFieldUpdater.decrementAndGet(this) != 0) {
                        drainLoop();
                    }
                    if (j != 0) {
                        subscription.request(j);
                        return;
                    }
                    return;
                }
            }
            Subscription andSet = MISSED_SUBSCRIPTION.getAndSet(this, subscription);
            if (andSet != null && shouldCancelCurrent()) {
                andSet.cancel();
            }
            drain();
        }

        final void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            drainLoop();
        }

        final void drainLoop() {
            int iAddAndGet = 1;
            long j = 0;
            long jAddCap = 0;
            long j2 = 0;
            Subscription subscription = null;
            while (true) {
                Subscription andSet = this.missedSubscription;
                if (andSet != null) {
                    andSet = MISSED_SUBSCRIPTION.getAndSet(this, null);
                }
                long andSet2 = this.missedRequested;
                if (andSet2 != j) {
                    andSet2 = MISSED_REQUESTED.getAndSet(this, j);
                }
                long andSet3 = this.missedProduced;
                if (andSet3 != j) {
                    andSet3 = MISSED_PRODUCED.getAndSet(this, j);
                }
                Subscription subscription2 = this.subscription;
                if (this.cancelled) {
                    if (subscription2 != null) {
                        subscription2.cancel();
                        this.subscription = null;
                    }
                    if (andSet != null) {
                        andSet.cancel();
                    }
                } else {
                    long jAddCap2 = this.requested;
                    if (jAddCap2 != Long.MAX_VALUE) {
                        jAddCap2 = Operators.addCap(jAddCap2, andSet2);
                        if (jAddCap2 != Long.MAX_VALUE) {
                            jAddCap2 -= andSet3;
                            if (jAddCap2 < 0) {
                                Operators.reportMoreProduced();
                                jAddCap2 = 0;
                            }
                        }
                        this.requested = jAddCap2;
                    }
                    if (andSet != null) {
                        if (subscription2 != null && shouldCancelCurrent()) {
                            subscription2.cancel();
                        }
                        this.subscription = andSet;
                        if (jAddCap2 != 0) {
                            jAddCap = Operators.addCap(jAddCap, jAddCap2 - j2);
                            subscription = andSet;
                        }
                    } else if (andSet2 != 0 && subscription2 != null) {
                        jAddCap = Operators.addCap(jAddCap, andSet2);
                        j2 += andSet2;
                        subscription = subscription2;
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                if (iAddAndGet == 0) {
                    break;
                } else {
                    j = 0;
                }
            }
            if (jAddCap != 0) {
                subscription.request(jAddCap);
            }
        }
    }

    static final class ScalarSubscription<T> implements Fuseable.SynchronousSubscription<T>, InnerProducer<T> {
        static final AtomicIntegerFieldUpdater<ScalarSubscription> ONCE = AtomicIntegerFieldUpdater.newUpdater(ScalarSubscription.class, "once");
        final CoreSubscriber<? super T> actual;
        volatile int once;

        @Nullable
        final String stepName;
        final T value;

        @Override // reactor.core.Fuseable.SynchronousSubscription, reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return (i & 1) != 0 ? 1 : 0;
        }

        ScalarSubscription(CoreSubscriber<? super T> coreSubscriber, T t) {
            this(coreSubscriber, t, null);
        }

        ScalarSubscription(CoreSubscriber<? super T> coreSubscriber, T t, String str) {
            this.value = (T) Objects.requireNonNull(t, "value");
            this.actual = (CoreSubscriber) Objects.requireNonNull(coreSubscriber, "actual");
            this.stepName = str;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.once == 0) {
                Operators.onDiscard(this.value, this.actual.currentContext());
            }
            ONCE.lazySet(this, 2);
        }

        @Override // java.util.Collection
        public void clear() {
            if (this.once == 0) {
                Operators.onDiscard(this.value, this.actual.currentContext());
            }
            ONCE.lazySet(this, 1);
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.once != 0;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            if (this.once != 0) {
                return null;
            }
            ONCE.lazySet(this, 1);
            return this.value;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.once == 1);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.once == 2);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j) && ONCE.compareAndSet(this, 0, 1)) {
                CoreSubscriber<? super T> coreSubscriber = this.actual;
                coreSubscriber.onNext(this.value);
                if (this.once != 2) {
                    coreSubscriber.onComplete();
                }
            }
        }

        @Override // java.util.Collection
        public int size() {
            return !isEmpty() ? 1 : 0;
        }

        @Override // reactor.core.Scannable
        public String stepName() {
            String str = this.stepName;
            return str != null ? str : "scalarSubscription(" + this.value + ")";
        }
    }

    static final class DrainSubscriber<T> implements CoreSubscriber<T> {
        static final DrainSubscriber INSTANCE = new DrainSubscriber();

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
        }

        DrainSubscriber() {
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            subscription.request(Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            Operators.onErrorDropped(Exceptions.errorCallbackNotImplemented(th), Context.empty());
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return Context.empty();
        }
    }

    static final class ConditionalSubscriberAdapter<T> implements Fuseable.ConditionalSubscriber<T> {
        final CoreSubscriber<T> delegate;

        ConditionalSubscriberAdapter(CoreSubscriber<T> coreSubscriber) {
            this.delegate = coreSubscriber;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.delegate.currentContext();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.delegate.onSubscribe(subscription);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.delegate.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.delegate.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.delegate.onComplete();
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            this.delegate.onNext(t);
            return true;
        }
    }

    static final class LiftFunction<I, O> implements Function<Publisher<I>, Publisher<O>> {
        final Predicate<Publisher> filter;
        final BiFunction<Publisher, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super I>> lifter;
        final String name;

        static final <I, O> LiftFunction<I, O> liftScannable(@Nullable final Predicate<Scannable> predicate, final BiFunction<Scannable, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super I>> biFunction) {
            Objects.requireNonNull(biFunction, "lifter");
            return new LiftFunction<>(predicate != null ? new Predicate() { // from class: reactor.core.publisher.Operators$LiftFunction$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return predicate.test(Scannable.from((Publisher) obj));
                }
            } : null, new BiFunction() { // from class: reactor.core.publisher.Operators$LiftFunction$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    Publisher publisher = (Publisher) obj;
                    return Operators.restoreContextOnSubscriberIfPublisherNonInternal(publisher, (CoreSubscriber) biFunction.apply(Scannable.from(publisher), Operators.restoreContextOnSubscriberIfAutoCPEnabled(publisher, (CoreSubscriber) obj2)));
                }
            }, biFunction.toString());
        }

        static final <I, O> LiftFunction<I, O> liftPublisher(@Nullable Predicate<Publisher> predicate, final BiFunction<Publisher, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super I>> biFunction) {
            Objects.requireNonNull(biFunction, "lifter");
            return new LiftFunction<>(predicate, new BiFunction() { // from class: reactor.core.publisher.Operators$LiftFunction$$ExternalSyntheticLambda2
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    Publisher publisher = (Publisher) obj;
                    return Operators.restoreContextOnSubscriberIfPublisherNonInternal(publisher, (CoreSubscriber) biFunction.apply(publisher, Operators.restoreContextOnSubscriberIfAutoCPEnabled(publisher, (CoreSubscriber) obj2)));
                }
            }, biFunction.toString());
        }

        private LiftFunction(@Nullable Predicate<Publisher> predicate, BiFunction<Publisher, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super I>> biFunction, String str) {
            this.filter = predicate;
            this.lifter = (BiFunction) Objects.requireNonNull(biFunction, "lifter");
            this.name = (String) Objects.requireNonNull(str, "name");
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.function.Function
        public Publisher<O> apply(Publisher<I> publisher) {
            Predicate<Publisher> predicate = this.filter;
            if (predicate != null && !predicate.test(publisher)) {
                return publisher;
            }
            if (publisher instanceof Fuseable) {
                if (publisher instanceof Mono) {
                    return new MonoLiftFuseable(publisher, this);
                }
                if (publisher instanceof ParallelFlux) {
                    return new ParallelLiftFuseable((ParallelFlux) publisher, this);
                }
                if (publisher instanceof ConnectableFlux) {
                    return new ConnectableLiftFuseable((ConnectableFlux) publisher, this);
                }
                if (publisher instanceof GroupedFlux) {
                    return new GroupedLiftFuseable((GroupedFlux) publisher, this);
                }
                return new FluxLiftFuseable(publisher, this);
            }
            if (publisher instanceof Mono) {
                return new MonoLift(publisher, this);
            }
            if (publisher instanceof ParallelFlux) {
                return new ParallelLift((ParallelFlux) publisher, this);
            }
            if (publisher instanceof ConnectableFlux) {
                return new ConnectableLift((ConnectableFlux) publisher, this);
            }
            if (publisher instanceof GroupedFlux) {
                return new GroupedLift((GroupedFlux) publisher, this);
            }
            return new FluxLift(publisher, this);
        }
    }

    static class MonoInnerProducerBase<O> implements InnerProducer<O> {
        private static final int CANCELLED = 128;
        private static final int HAS_COMPLETED = 4;
        private static final int HAS_REQUEST = 2;
        private static final int HAS_VALUE = 1;
        private static final AtomicIntegerFieldUpdater<MonoInnerProducerBase> STATE = AtomicIntegerFieldUpdater.newUpdater(MonoInnerProducerBase.class, "state");
        private final CoreSubscriber<? super O> actual;
        private volatile int state;
        private O value;

        private static boolean hasCompleted(int i) {
            return (i & 4) == 4;
        }

        private static boolean hasRequest(int i) {
            return (i & 2) == 2;
        }

        private static boolean hasValue(int i) {
            return (i & 1) == 1;
        }

        private static boolean isCancelled(int i) {
            return i == 128;
        }

        protected void doOnCancel() {
        }

        protected void doOnComplete(O o) {
        }

        protected void doOnRequest(long j) {
        }

        public MonoInnerProducerBase(CoreSubscriber<? super O> coreSubscriber) {
            this.actual = coreSubscriber;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isCancelled());
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(hasCompleted(this.state));
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            return super.scanUnsafe(attr);
        }

        public final void complete(O o) {
            int i;
            do {
                i = this.state;
                if (isCancelled(i)) {
                    discard(o);
                    return;
                }
                if (hasRequest(i) && STATE.compareAndSet(this, i, i | 5)) {
                    this.value = null;
                    doOnComplete(o);
                    this.actual.onNext(o);
                    this.actual.onComplete();
                    return;
                }
                this.value = o;
            } while (!STATE.compareAndSet(this, i, i | 5));
        }

        public final void complete() {
            while (true) {
                int i = this.state;
                if (isCancelled(i)) {
                    return;
                }
                if (STATE.compareAndSet(this, i, i | 4)) {
                    if (hasValue(i) && hasRequest(i)) {
                        O o = this.value;
                        this.value = null;
                        doOnComplete(o);
                        this.actual.onNext(o);
                        this.actual.onComplete();
                        return;
                    }
                    if (!hasValue(i)) {
                        this.actual.onComplete();
                        return;
                    } else if (!hasRequest(i)) {
                        return;
                    }
                }
            }
        }

        protected final void discard(@Nullable O o) {
            Operators.onDiscard(o, this.actual.currentContext());
        }

        protected final void discardTheValue() {
            discard(this.value);
            this.value = null;
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super O> actual() {
            return this.actual;
        }

        public final boolean isCancelled() {
            return this.state == 128;
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
                doOnRequest(j);
                if (hasValue(i) && hasCompleted(i)) {
                    O o = this.value;
                    this.value = null;
                    doOnComplete(o);
                    this.actual.onNext(o);
                    this.actual.onComplete();
                }
            }
        }

        protected final void setValue(@Nullable O o) {
            int i;
            this.value = o;
            do {
                i = this.state;
                if (isCancelled(i)) {
                    discardTheValue();
                    return;
                }
            } while (!STATE.compareAndSet(this, i, i | 1));
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            int andSet = STATE.getAndSet(this, 128);
            if (isCancelled(andSet)) {
                return;
            }
            doOnCancel();
            if (!hasValue(andSet) || (andSet & 6) == 6) {
                return;
            }
            discardTheValue();
        }
    }
}
