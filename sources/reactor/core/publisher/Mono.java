package reactor.core.publisher;

import com.microsoft.azure.storage.table.TableConstants;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.observability.SignalListener;
import reactor.core.observability.SignalListenerFactory;
import reactor.core.publisher.FluxHide;
import reactor.core.publisher.FluxOnAssembly;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.Logger;
import reactor.util.Metrics;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;
import reactor.util.context.ContextView;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuple4;
import reactor.util.function.Tuple5;
import reactor.util.function.Tuple6;
import reactor.util.function.Tuple7;
import reactor.util.function.Tuple8;
import reactor.util.function.Tuples;
import reactor.util.retry.Retry;

/* JADX INFO: loaded from: classes5.dex */
public abstract class Mono<T> implements CorePublisher<T> {
    static final BiPredicate EQUALS_BIPREDICATE = new BiPredicate() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda0
        @Override // java.util.function.BiPredicate
        public final boolean test(Object obj, Object obj2) {
            return obj.equals(obj2);
        }
    };

    static /* synthetic */ Logger lambda$log$25(Logger logger, String str) {
        return logger;
    }

    @Override // reactor.core.CorePublisher
    public abstract void subscribe(CoreSubscriber<? super T> coreSubscriber);

    public static <T> Mono<T> create(Consumer<MonoSink<T>> consumer) {
        return onAssembly(new MonoCreate(consumer));
    }

    public static <T> Mono<T> defer(Supplier<? extends Mono<? extends T>> supplier) {
        return onAssembly(new MonoDefer(supplier));
    }

    public static <T> Mono<T> deferContextual(Function<ContextView, ? extends Mono<? extends T>> function) {
        return onAssembly(new MonoDeferContextual(function));
    }

    public static Mono<Long> delay(Duration duration) {
        return delay(duration, Schedulers.parallel());
    }

    public static Mono<Long> delay(Duration duration, Scheduler scheduler) {
        return onAssembly(new MonoDelay(duration.toNanos(), TimeUnit.NANOSECONDS, scheduler));
    }

    public static <T> Mono<T> empty() {
        return MonoEmpty.instance();
    }

    public static <T> Mono<T> error(Throwable th) {
        return onAssembly(new MonoError(th));
    }

    public static <T> Mono<T> error(Supplier<? extends Throwable> supplier) {
        return onAssembly(new MonoErrorSupplied(supplier));
    }

    @SafeVarargs
    @Deprecated
    public static <T> Mono<T> first(Mono<? extends T>... monoArr) {
        return firstWithSignal(monoArr);
    }

    @Deprecated
    public static <T> Mono<T> first(Iterable<? extends Mono<? extends T>> iterable) {
        return firstWithSignal(iterable);
    }

    @SafeVarargs
    public static <T> Mono<T> firstWithSignal(Mono<? extends T>... monoArr) {
        return onAssembly(new MonoFirstWithSignal(monoArr));
    }

    public static <T> Mono<T> firstWithSignal(Iterable<? extends Mono<? extends T>> iterable) {
        return onAssembly(new MonoFirstWithSignal(iterable));
    }

    public static <T> Mono<T> firstWithValue(Iterable<? extends Mono<? extends T>> iterable) {
        return onAssembly(new MonoFirstWithValue(iterable));
    }

    @SafeVarargs
    public static <T> Mono<T> firstWithValue(Mono<? extends T> mono, Mono<? extends T>... monoArr) {
        MonoFirstWithValue<T> monoFirstWithValueFirstValuedAdditionalSources;
        return (!(mono instanceof MonoFirstWithValue) || (monoFirstWithValueFirstValuedAdditionalSources = ((MonoFirstWithValue) mono).firstValuedAdditionalSources(monoArr)) == null) ? onAssembly(new MonoFirstWithValue(mono, monoArr)) : monoFirstWithValueFirstValuedAdditionalSources;
    }

    public static <T> Mono<T> from(Publisher<? extends T> publisher) {
        boolean zShouldWrapPublisher = ContextPropagationSupport.shouldWrapPublisher(publisher);
        if ((publisher instanceof Mono) && !zShouldWrapPublisher) {
            return (Mono) publisher;
        }
        if ((publisher instanceof FluxSourceMono) || (publisher instanceof FluxSourceMonoFuseable)) {
            Mono<T> mono = (Mono<T>) ((FluxFromMonoOperator) publisher).source;
            return !ContextPropagationSupport.shouldWrapPublisher(mono) ? mono : wrap(mono, false);
        }
        return onAssembly(wrap(publisher, true));
    }

    public static <T> Mono<T> fromCallable(Callable<? extends T> callable) {
        return onAssembly(new MonoCallable(callable));
    }

    public static <T> Mono<T> fromCompletionStage(CompletionStage<? extends T> completionStage) {
        return onAssembly(new MonoCompletionStage(completionStage, false));
    }

    public static <T> Mono<T> fromCompletionStage(final Supplier<? extends CompletionStage<? extends T>> supplier) {
        return defer(new Supplier() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda23
            @Override // java.util.function.Supplier
            public final Object get() {
                return Mono.onAssembly(new MonoCompletionStage((CompletionStage) supplier.get(), false));
            }
        });
    }

    public static <I> Mono<I> fromDirect(Publisher<? extends I> publisher) {
        boolean zShouldWrapPublisher = ContextPropagationSupport.shouldWrapPublisher(publisher);
        if ((publisher instanceof Mono) && !zShouldWrapPublisher) {
            return (Mono) publisher;
        }
        if ((publisher instanceof FluxSourceMono) || (publisher instanceof FluxSourceMonoFuseable)) {
            Mono<? extends I> mono = ((FluxFromMonoOperator) publisher).source;
            return !ContextPropagationSupport.shouldWrapPublisher(mono) ? mono : wrap(mono, false);
        }
        return onAssembly(wrap(publisher, false));
    }

    public static <T> Mono<T> fromFuture(CompletableFuture<? extends T> completableFuture) {
        return fromFuture((CompletableFuture) completableFuture, false);
    }

    public static <T> Mono<T> fromFuture(CompletableFuture<? extends T> completableFuture, boolean z) {
        return onAssembly(new MonoCompletionStage(completableFuture, z));
    }

    public static <T> Mono<T> fromFuture(Supplier<? extends CompletableFuture<? extends T>> supplier) {
        return fromFuture((Supplier) supplier, false);
    }

    public static <T> Mono<T> fromFuture(final Supplier<? extends CompletableFuture<? extends T>> supplier, final boolean z) {
        return defer(new Supplier() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda33
            @Override // java.util.function.Supplier
            public final Object get() {
                return Mono.onAssembly(new MonoCompletionStage((CompletionStage) supplier.get(), z));
            }
        });
    }

    public static <T> Mono<T> fromRunnable(Runnable runnable) {
        return onAssembly(new MonoRunnable(runnable));
    }

    public static <T> Mono<T> fromSupplier(Supplier<? extends T> supplier) {
        return onAssembly(new MonoSupplier(supplier));
    }

    public static <T> Mono<T> ignoreElements(Publisher<T> publisher) {
        return onAssembly(new MonoIgnorePublisher(publisher));
    }

    public static <T> Mono<T> just(T t) {
        return onAssembly(new MonoJust(t));
    }

    public static <T> Mono<T> justOrEmpty(@Nullable Optional<? extends T> optional) {
        return (optional == null || !optional.isPresent()) ? empty() : just(optional.get());
    }

    public static <T> Mono<T> justOrEmpty(@Nullable T t) {
        return t != null ? just(t) : empty();
    }

    public static <T> Mono<T> never() {
        return MonoNever.instance();
    }

    public static <T> Mono<Boolean> sequenceEqual(Publisher<? extends T> publisher, Publisher<? extends T> publisher2) {
        return sequenceEqual(publisher, publisher2, equalsBiPredicate(), Queues.SMALL_BUFFER_SIZE);
    }

    public static <T> Mono<Boolean> sequenceEqual(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, BiPredicate<? super T, ? super T> biPredicate) {
        return sequenceEqual(publisher, publisher2, biPredicate, Queues.SMALL_BUFFER_SIZE);
    }

    public static <T> Mono<Boolean> sequenceEqual(Publisher<? extends T> publisher, Publisher<? extends T> publisher2, BiPredicate<? super T, ? super T> biPredicate, int i) {
        return onAssembly(new MonoSequenceEqual(publisher, publisher2, biPredicate, i));
    }

    public static <T, D> Mono<T> using(Callable<? extends D> callable, Function<? super D, ? extends Mono<? extends T>> function, Consumer<? super D> consumer, boolean z) {
        return onAssembly(new MonoUsing(callable, function, consumer, z));
    }

    public static <T, D> Mono<T> using(Callable<? extends D> callable, Function<? super D, ? extends Mono<? extends T>> function, Consumer<? super D> consumer) {
        return using(callable, function, consumer, true);
    }

    public static <T, D extends AutoCloseable> Mono<T> using(Callable<? extends D> callable, Function<? super D, ? extends Mono<? extends T>> function) {
        return using((Callable) callable, (Function) function, true);
    }

    public static <T, D extends AutoCloseable> Mono<T> using(Callable<? extends D> callable, Function<? super D, ? extends Mono<? extends T>> function, boolean z) {
        return using(callable, function, Exceptions.AUTO_CLOSE, z);
    }

    public static <T, D> Mono<T> usingWhen(Publisher<D> publisher, Function<? super D, ? extends Mono<? extends T>> function, final Function<? super D, ? extends Publisher<?>> function2) {
        return usingWhen(publisher, function, function2, new BiFunction() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda13
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Mono.lambda$usingWhen$2(function2, obj, (Throwable) obj2);
            }
        }, function2);
    }

    static /* synthetic */ Publisher lambda$usingWhen$2(Function function, Object obj, Throwable th) {
        return (Publisher) function.apply(obj);
    }

    public static <T, D> Mono<T> usingWhen(Publisher<D> publisher, Function<? super D, ? extends Mono<? extends T>> function, Function<? super D, ? extends Publisher<?>> function2, BiFunction<? super D, ? super Throwable, ? extends Publisher<?>> biFunction, Function<? super D, ? extends Publisher<?>> function3) {
        return onAssembly(new MonoUsingWhen(publisher, function, function2, biFunction, function3));
    }

    public static Mono<Void> when(Publisher<?>... publisherArr) {
        if (publisherArr.length == 0) {
            return empty();
        }
        if (publisherArr.length == 1) {
            return empty(publisherArr[0]);
        }
        return onAssembly(new MonoWhen(false, publisherArr));
    }

    public static Mono<Void> when(Iterable<? extends Publisher<?>> iterable) {
        return onAssembly(new MonoWhen(false, iterable));
    }

    public static Mono<Void> whenDelayError(Iterable<? extends Publisher<?>> iterable) {
        return onAssembly(new MonoWhen(true, iterable));
    }

    public static Mono<Void> whenDelayError(Publisher<?>... publisherArr) {
        if (publisherArr.length == 0) {
            return empty();
        }
        if (publisherArr.length == 1) {
            return empty(publisherArr[0]);
        }
        return onAssembly(new MonoWhen(true, publisherArr));
    }

    public static <T1, T2> Mono<Tuple2<T1, T2>> zip(Mono<? extends T1> mono, Mono<? extends T2> mono2) {
        return zip(mono, mono2, Flux.tuple2Function());
    }

    public static <T1, T2, O> Mono<O> zip(Mono<? extends T1> mono, Mono<? extends T2> mono2, BiFunction<? super T1, ? super T2, ? extends O> biFunction) {
        return onAssembly(new MonoZip(false, mono, mono2, biFunction));
    }

    public static <T1, T2, T3> Mono<Tuple3<T1, T2, T3>> zip(Mono<? extends T1> mono, Mono<? extends T2> mono2, Mono<? extends T3> mono3) {
        return onAssembly(new MonoZip(false, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2, mono3}));
    }

    public static <T1, T2, T3, T4> Mono<Tuple4<T1, T2, T3, T4>> zip(Mono<? extends T1> mono, Mono<? extends T2> mono2, Mono<? extends T3> mono3, Mono<? extends T4> mono4) {
        return onAssembly(new MonoZip(false, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2, mono3, mono4}));
    }

    public static <T1, T2, T3, T4, T5> Mono<Tuple5<T1, T2, T3, T4, T5>> zip(Mono<? extends T1> mono, Mono<? extends T2> mono2, Mono<? extends T3> mono3, Mono<? extends T4> mono4, Mono<? extends T5> mono5) {
        return onAssembly(new MonoZip(false, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2, mono3, mono4, mono5}));
    }

    public static <T1, T2, T3, T4, T5, T6> Mono<Tuple6<T1, T2, T3, T4, T5, T6>> zip(Mono<? extends T1> mono, Mono<? extends T2> mono2, Mono<? extends T3> mono3, Mono<? extends T4> mono4, Mono<? extends T5> mono5, Mono<? extends T6> mono6) {
        return onAssembly(new MonoZip(false, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda30
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2, mono3, mono4, mono5, mono6}));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Mono<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zip(Mono<? extends T1> mono, Mono<? extends T2> mono2, Mono<? extends T3> mono3, Mono<? extends T4> mono4, Mono<? extends T5> mono5, Mono<? extends T6> mono6, Mono<? extends T7> mono7) {
        return onAssembly(new MonoZip(false, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2, mono3, mono4, mono5, mono6, mono7}));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Mono<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zip(Mono<? extends T1> mono, Mono<? extends T2> mono2, Mono<? extends T3> mono3, Mono<? extends T4> mono4, Mono<? extends T5> mono5, Mono<? extends T6> mono6, Mono<? extends T7> mono7, Mono<? extends T8> mono8) {
        return onAssembly(new MonoZip(false, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2, mono3, mono4, mono5, mono6, mono7, mono8}));
    }

    public static <R> Mono<R> zip(Iterable<? extends Mono<?>> iterable, Function<? super Object[], ? extends R> function) {
        return onAssembly(new MonoZip(false, (Function) function, iterable));
    }

    public static <R> Mono<R> zip(final Function<? super Object[], ? extends R> function, Mono<?>... monoArr) {
        if (monoArr.length == 0) {
            return empty();
        }
        if (monoArr.length == 1) {
            return monoArr[0].map(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda27
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return function.apply(new Object[]{obj});
                }
            });
        }
        return onAssembly(new MonoZip(false, (Function) function, monoArr));
    }

    public static <T1, T2> Mono<Tuple2<T1, T2>> zipDelayError(Mono<? extends T1> mono, Mono<? extends T2> mono2) {
        return onAssembly(new MonoZip(true, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2}));
    }

    public static <T1, T2, T3> Mono<Tuple3<T1, T2, T3>> zipDelayError(Mono<? extends T1> mono, Mono<? extends T2> mono2, Mono<? extends T3> mono3) {
        return onAssembly(new MonoZip(true, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2, mono3}));
    }

    public static <T1, T2, T3, T4> Mono<Tuple4<T1, T2, T3, T4>> zipDelayError(Mono<? extends T1> mono, Mono<? extends T2> mono2, Mono<? extends T3> mono3, Mono<? extends T4> mono4) {
        return onAssembly(new MonoZip(true, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2, mono3, mono4}));
    }

    public static <T1, T2, T3, T4, T5> Mono<Tuple5<T1, T2, T3, T4, T5>> zipDelayError(Mono<? extends T1> mono, Mono<? extends T2> mono2, Mono<? extends T3> mono3, Mono<? extends T4> mono4, Mono<? extends T5> mono5) {
        return onAssembly(new MonoZip(true, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda38
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2, mono3, mono4, mono5}));
    }

    public static <T1, T2, T3, T4, T5, T6> Mono<Tuple6<T1, T2, T3, T4, T5, T6>> zipDelayError(Mono<? extends T1> mono, Mono<? extends T2> mono2, Mono<? extends T3> mono3, Mono<? extends T4> mono4, Mono<? extends T5> mono5, Mono<? extends T6> mono6) {
        return onAssembly(new MonoZip(true, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda35
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2, mono3, mono4, mono5, mono6}));
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Mono<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zipDelayError(Mono<? extends T1> mono, Mono<? extends T2> mono2, Mono<? extends T3> mono3, Mono<? extends T4> mono4, Mono<? extends T5> mono5, Mono<? extends T6> mono6, Mono<? extends T7> mono7) {
        return onAssembly(new MonoZip(true, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda42
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2, mono3, mono4, mono5, mono6, mono7}));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Mono<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zipDelayError(Mono<? extends T1> mono, Mono<? extends T2> mono2, Mono<? extends T3> mono3, Mono<? extends T4> mono4, Mono<? extends T5> mono5, Mono<? extends T6> mono6, Mono<? extends T7> mono7, Mono<? extends T8> mono8) {
        return onAssembly(new MonoZip(true, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.fromArray((Object[]) obj);
            }
        }, (Mono<?>[]) new Mono[]{mono, mono2, mono3, mono4, mono5, mono6, mono7, mono8}));
    }

    public static <R> Mono<R> zipDelayError(Iterable<? extends Mono<?>> iterable, Function<? super Object[], ? extends R> function) {
        return onAssembly(new MonoZip(true, (Function) function, iterable));
    }

    public static <R> Mono<R> zipDelayError(final Function<? super Object[], ? extends R> function, Mono<?>... monoArr) {
        if (monoArr.length == 0) {
            return empty();
        }
        if (monoArr.length == 1) {
            return monoArr[0].map(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda19
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return function.apply(new Object[]{obj});
                }
            });
        }
        return onAssembly(new MonoZip(true, (Function) function, monoArr));
    }

    /* JADX INFO: renamed from: as */
    public final <P> P m1966as(Function<? super Mono<T>, P> function) {
        return function.apply(this);
    }

    public final Mono<Void> and(Publisher<?> publisher) {
        Mono<Void> monoWhenAdditionalSource;
        return (!(this instanceof MonoWhen) || (monoWhenAdditionalSource = ((MonoWhen) this).whenAdditionalSource(publisher)) == null) ? when((Publisher<?>[]) new Publisher[]{this, publisher}) : monoWhenAdditionalSource;
    }

    @Nullable
    public T block() {
        BlockingMonoSubscriber blockingMonoSubscriber = new BlockingMonoSubscriber(ContextPropagationSupport.shouldPropagateContextToThreadLocals() ? ContextPropagation.contextCaptureToEmpty() : Context.empty());
        subscribe((Subscriber) blockingMonoSubscriber);
        return blockingMonoSubscriber.blockingGet();
    }

    @Nullable
    public T block(Duration duration) {
        BlockingMonoSubscriber blockingMonoSubscriber = new BlockingMonoSubscriber(ContextPropagationSupport.shouldPropagateContextToThreadLocals() ? ContextPropagation.contextCaptureToEmpty() : Context.empty());
        subscribe((Subscriber) blockingMonoSubscriber);
        return blockingMonoSubscriber.blockingGet(duration.toNanos(), TimeUnit.NANOSECONDS);
    }

    public Optional<T> blockOptional() {
        BlockingOptionalMonoSubscriber blockingOptionalMonoSubscriber = new BlockingOptionalMonoSubscriber(ContextPropagationSupport.shouldPropagateContextToThreadLocals() ? ContextPropagation.contextCaptureToEmpty() : Context.empty());
        subscribe((Subscriber) blockingOptionalMonoSubscriber);
        return blockingOptionalMonoSubscriber.blockingGet();
    }

    public Optional<T> blockOptional(Duration duration) {
        BlockingOptionalMonoSubscriber blockingOptionalMonoSubscriber = new BlockingOptionalMonoSubscriber(ContextPropagationSupport.shouldPropagateContextToThreadLocals() ? ContextPropagation.contextCaptureToEmpty() : Context.empty());
        subscribe((Subscriber) blockingOptionalMonoSubscriber);
        return blockingOptionalMonoSubscriber.blockingGet(duration.toNanos(), TimeUnit.NANOSECONDS);
    }

    public final <E> Mono<E> cast(final Class<E> cls) {
        Objects.requireNonNull(cls, "clazz");
        Objects.requireNonNull(cls);
        return (Mono<E>) map(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda25
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return cls.cast(obj);
            }
        });
    }

    public final Mono<T> cache() {
        return onAssembly(new MonoCacheTime(this));
    }

    public final Mono<T> cache(Duration duration) {
        return cache(duration, Schedulers.parallel());
    }

    public final Mono<T> cache(Duration duration, Scheduler scheduler) {
        return onAssembly(new MonoCacheTime(this, duration, scheduler));
    }

    public final Mono<T> cache(Function<? super T, Duration> function, Function<Throwable, Duration> function2, Supplier<Duration> supplier) {
        return cache(function, function2, supplier, Schedulers.parallel());
    }

    public final Mono<T> cache(Function<? super T, Duration> function, Function<Throwable, Duration> function2, Supplier<Duration> supplier, Scheduler scheduler) {
        return onAssembly(new MonoCacheTime(this, function, function2, supplier, scheduler));
    }

    public final Mono<T> cacheInvalidateIf(Predicate<? super T> predicate) {
        return onAssembly(new MonoCacheInvalidateIf(this, predicate));
    }

    public final Mono<T> cacheInvalidateWhen(Function<? super T, Mono<Void>> function) {
        return onAssembly(new MonoCacheInvalidateWhen(this, function, null));
    }

    public final Mono<T> cacheInvalidateWhen(Function<? super T, Mono<Void>> function, Consumer<? super T> consumer) {
        return onAssembly(new MonoCacheInvalidateWhen(this, function, consumer));
    }

    public final Mono<T> cancelOn(Scheduler scheduler) {
        return onAssembly(new MonoCancelOn(this, scheduler));
    }

    public final Mono<T> checkpoint() {
        return checkpoint(null, true);
    }

    public final Mono<T> checkpoint(String str) {
        return checkpoint((String) Objects.requireNonNull(str), false);
    }

    public final Mono<T> checkpoint(@Nullable String str, boolean z) {
        FluxOnAssembly.AssemblySnapshot checkpointHeavySnapshot;
        if (!z) {
            checkpointHeavySnapshot = new FluxOnAssembly.CheckpointLightSnapshot(str);
        } else {
            checkpointHeavySnapshot = new FluxOnAssembly.CheckpointHeavySnapshot(str, Traces.callSiteSupplierFactory.get());
        }
        return new MonoOnAssembly(this, checkpointHeavySnapshot);
    }

    public final Flux<T> concatWith(Publisher<? extends T> publisher) {
        return Flux.concat(this, publisher);
    }

    public final Mono<T> contextCapture() {
        if (!ContextPropagationSupport.isContextPropagationAvailable()) {
            return this;
        }
        if (ContextPropagationSupport.propagateContextToThreadLocals) {
            return onAssembly(new MonoContextWriteRestoringThreadLocals(this, ContextPropagation.contextCapture()));
        }
        return onAssembly(new MonoContextWrite(this, ContextPropagation.contextCapture()));
    }

    public final Mono<T> contextWrite(final ContextView contextView) {
        return contextWrite(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda40
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Context) obj).putAll(contextView);
            }
        });
    }

    public final Mono<T> contextWrite(Function<Context, Context> function) {
        if (ContextPropagationSupport.shouldPropagateContextToThreadLocals()) {
            return onAssembly(new MonoContextWriteRestoringThreadLocals(this, function));
        }
        return onAssembly(new MonoContextWrite(this, function));
    }

    private final Mono<T> contextWriteSkippingContextPropagation(final ContextView contextView) {
        return contextWriteSkippingContextPropagation(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda26
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Context) obj).putAll(contextView);
            }
        });
    }

    private final Mono<T> contextWriteSkippingContextPropagation(Function<Context, Context> function) {
        return onAssembly(new MonoContextWrite(this, function));
    }

    public final Mono<T> defaultIfEmpty(T t) {
        if (this instanceof Fuseable.ScalarCallable) {
            try {
                if (block() == null) {
                    return just(t);
                }
            } catch (Throwable unused) {
            }
            return this;
        }
        return onAssembly(new MonoDefaultIfEmpty(this, t));
    }

    public final Mono<T> delayElement(Duration duration) {
        return delayElement(duration, Schedulers.parallel());
    }

    public final Mono<T> delayElement(Duration duration, Scheduler scheduler) {
        return onAssembly(new MonoDelayElement(this, duration.toNanos(), TimeUnit.NANOSECONDS, scheduler));
    }

    public final Mono<T> delayUntil(Function<? super T, ? extends Publisher<?>> function) {
        Objects.requireNonNull(function, "triggerProvider required");
        if (this instanceof MonoDelayUntil) {
            return ((MonoDelayUntil) this).copyWithNewTriggerGenerator(false, function);
        }
        return onAssembly(new MonoDelayUntil(this, function));
    }

    public final Mono<T> delaySubscription(Duration duration) {
        return delaySubscription(duration, Schedulers.parallel());
    }

    public final Mono<T> delaySubscription(Duration duration, Scheduler scheduler) {
        return delaySubscription(delay(duration, scheduler));
    }

    public final <U> Mono<T> delaySubscription(Publisher<U> publisher) {
        return onAssembly(new MonoDelaySubscription(this, publisher));
    }

    public final <X> Mono<X> dematerialize() {
        return onAssembly(new MonoDematerialize(this));
    }

    public final Mono<T> doAfterTerminate(final Runnable runnable) {
        Objects.requireNonNull(runnable, "afterTerminate");
        return onAssembly(new MonoPeekTerminal(this, null, null, new BiConsumer() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda22
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                runnable.run();
            }
        }));
    }

    public final Mono<T> doFirst(Runnable runnable) {
        Objects.requireNonNull(runnable, "onFirst");
        if (this instanceof Fuseable) {
            return onAssembly(new MonoDoFirstFuseable(this, runnable));
        }
        return onAssembly(new MonoDoFirst(this, runnable));
    }

    public final Mono<T> doFinally(Consumer<SignalType> consumer) {
        Objects.requireNonNull(consumer, "onFinally");
        return onAssembly(new MonoDoFinally(this, consumer));
    }

    public final Mono<T> doOnCancel(Runnable runnable) {
        Objects.requireNonNull(runnable, "onCancel");
        return doOnSignal(this, null, null, null, runnable);
    }

    public final <R> Mono<T> doOnDiscard(Class<R> cls, Consumer<? super R> consumer) {
        return contextWriteSkippingContextPropagation(Operators.discardLocalAdapter(cls, consumer));
    }

    public final Mono<T> doOnNext(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "onNext");
        return doOnSignal(this, null, consumer, null, null);
    }

    public final Mono<T> doOnSuccess(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "onSuccess");
        return doOnTerminalSignal(this, consumer, null, null);
    }

    public final Mono<T> doOnEach(Consumer<? super Signal<T>> consumer) {
        Objects.requireNonNull(consumer, "signalConsumer");
        if (this instanceof Fuseable) {
            return onAssembly(new MonoDoOnEachFuseable(this, consumer));
        }
        return onAssembly(new MonoDoOnEach(this, consumer));
    }

    public final Mono<T> doOnError(Consumer<? super Throwable> consumer) {
        Objects.requireNonNull(consumer, "onError");
        return doOnTerminalSignal(this, null, consumer, null);
    }

    public final <E extends Throwable> Mono<T> doOnError(final Class<E> cls, final Consumer<? super E> consumer) {
        Objects.requireNonNull(cls, TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE);
        Objects.requireNonNull(consumer, "onError");
        return doOnTerminalSignal(this, null, new Consumer() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Mono.lambda$doOnError$21(cls, consumer, (Throwable) obj);
            }
        }, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$doOnError$21(Class cls, Consumer consumer, Throwable th) {
        if (cls.isInstance(th)) {
            consumer.accept(cls.cast(th));
        }
    }

    public final Mono<T> doOnError(final Predicate<? super Throwable> predicate, final Consumer<? super Throwable> consumer) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(consumer, "onError");
        return doOnTerminalSignal(this, null, new Consumer() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda41
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Mono.lambda$doOnError$22(predicate, consumer, (Throwable) obj);
            }
        }, null);
    }

    static /* synthetic */ void lambda$doOnError$22(Predicate predicate, Consumer consumer, Throwable th) {
        if (predicate.test(th)) {
            consumer.accept(th);
        }
    }

    public final Mono<T> doOnRequest(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer, "consumer");
        return doOnSignal(this, null, null, longConsumer, null);
    }

    public final Mono<T> doOnSubscribe(Consumer<? super Subscription> consumer) {
        Objects.requireNonNull(consumer, "onSubscribe");
        return doOnSignal(this, consumer, null, null, null);
    }

    public final Mono<T> doOnTerminate(final Runnable runnable) {
        Objects.requireNonNull(runnable, "onTerminate");
        return doOnTerminalSignal(this, new Consumer() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda44
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                runnable.run();
            }
        }, new Consumer() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                runnable.run();
            }
        }, null);
    }

    public final Mono<Tuple2<Long, T>> elapsed() {
        return elapsed(Schedulers.parallel());
    }

    public final Mono<Tuple2<Long, T>> elapsed(Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler");
        return onAssembly(new MonoElapsed(this, scheduler));
    }

    public final Flux<T> expandDeep(Function<? super T, ? extends Publisher<? extends T>> function, int i) {
        return Flux.onAssembly(new MonoExpand(this, function, false, i));
    }

    public final Flux<T> expandDeep(Function<? super T, ? extends Publisher<? extends T>> function) {
        return expandDeep(function, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> expand(Function<? super T, ? extends Publisher<? extends T>> function, int i) {
        return Flux.onAssembly(new MonoExpand(this, function, true, i));
    }

    public final Flux<T> expand(Function<? super T, ? extends Publisher<? extends T>> function) {
        return expand(function, Queues.SMALL_BUFFER_SIZE);
    }

    public final Mono<T> filter(Predicate<? super T> predicate) {
        if (this instanceof Fuseable) {
            return onAssembly(new MonoFilterFuseable(this, predicate));
        }
        return onAssembly(new MonoFilter(this, predicate));
    }

    public final Mono<T> filterWhen(Function<? super T, ? extends Publisher<Boolean>> function) {
        return onAssembly(new MonoFilterWhen(this, function));
    }

    public final <R> Mono<R> flatMap(Function<? super T, ? extends Mono<? extends R>> function) {
        return onAssembly(new MonoFlatMap(this, function));
    }

    public final <R> Flux<R> flatMapMany(Function<? super T, ? extends Publisher<? extends R>> function) {
        return Flux.onAssembly(new MonoFlatMapMany(this, function));
    }

    public final <R> Flux<R> flatMapMany(Function<? super T, ? extends Publisher<? extends R>> function, Function<? super Throwable, ? extends Publisher<? extends R>> function2, Supplier<? extends Publisher<? extends R>> supplier) {
        return flux().flatMap(function, function2, supplier);
    }

    public final <R> Flux<R> flatMapIterable(Function<? super T, ? extends Iterable<? extends R>> function) {
        return Flux.onAssembly(new MonoFlattenIterable(this, function, Integer.MAX_VALUE, Queues.one()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Flux<T> flux() {
        if ((this instanceof Callable) && !(this instanceof Fuseable.ScalarCallable)) {
            return Flux.onAssembly(new FluxCallable((Callable) this));
        }
        return Flux.from(this);
    }

    public final Mono<Boolean> hasElement() {
        return onAssembly(new MonoHasElement(this));
    }

    public final <R> Mono<R> handle(BiConsumer<? super T, SynchronousSink<R>> biConsumer) {
        if (this instanceof Fuseable) {
            return onAssembly(new MonoHandleFuseable(this, biConsumer));
        }
        return onAssembly(new MonoHandle(this, biConsumer));
    }

    public final Mono<T> hide() {
        return onAssembly(new MonoHide(this));
    }

    public final Mono<T> ignoreElement() {
        return onAssembly(new MonoIgnoreElement(this));
    }

    public final Mono<T> log() {
        return log(null, Level.INFO, new SignalType[0]);
    }

    public final Mono<T> log(@Nullable String str) {
        return log(str, Level.INFO, new SignalType[0]);
    }

    public final Mono<T> log(@Nullable String str, Level level, SignalType... signalTypeArr) {
        return log(str, level, false, signalTypeArr);
    }

    public final Mono<T> log(@Nullable String str, Level level, boolean z, SignalType... signalTypeArr) {
        SignalLogger signalLogger = new SignalLogger(this, str, level, z, signalTypeArr);
        if (this instanceof Fuseable) {
            return onAssembly(new MonoLogFuseable(this, signalLogger));
        }
        return onAssembly(new MonoLog(this, signalLogger));
    }

    public final Mono<T> log(Logger logger) {
        return log(logger, Level.INFO, false, new SignalType[0]);
    }

    public final Mono<T> log(final Logger logger, Level level, boolean z, SignalType... signalTypeArr) {
        SignalLogger signalLogger = new SignalLogger(this, "IGNORED", level, z, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda36
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mono.lambda$log$25(logger, (String) obj);
            }
        }, signalTypeArr);
        if (this instanceof Fuseable) {
            return onAssembly(new MonoLogFuseable(this, signalLogger));
        }
        return onAssembly(new MonoLog(this, signalLogger));
    }

    public final <R> Mono<R> map(Function<? super T, ? extends R> function) {
        if (this instanceof Fuseable) {
            return onAssembly(new MonoMapFuseable(this, function));
        }
        return onAssembly(new MonoMap(this, function));
    }

    public final <R> Mono<R> mapNotNull(final Function<? super T, ? extends R> function) {
        return handle(new BiConsumer() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda34
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Mono.lambda$mapNotNull$26(function, obj, (SynchronousSink) obj2);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$mapNotNull$26(Function function, Object obj, SynchronousSink synchronousSink) {
        Object objApply = function.apply(obj);
        if (objApply != null) {
            synchronousSink.next(objApply);
        }
    }

    public final Mono<Signal<T>> materialize() {
        return onAssembly(new MonoMaterialize(this));
    }

    public final Flux<T> mergeWith(Publisher<? extends T> publisher) {
        return Flux.merge(this, publisher);
    }

    @Deprecated
    public final Mono<T> metrics() {
        if (!Metrics.isInstrumentationAvailable()) {
            return this;
        }
        if (this instanceof Fuseable) {
            return onAssembly(new MonoMetricsFuseable(this));
        }
        return onAssembly(new MonoMetrics(this));
    }

    public final Mono<T> name(String str) {
        return MonoName.createOrAppend(this, str);
    }

    /* JADX INFO: renamed from: or */
    public final Mono<T> m1968or(Mono<? extends T> mono) {
        Mono<T> monoOrAdditionalSource;
        return (!(this instanceof MonoFirstWithSignal) || (monoOrAdditionalSource = ((MonoFirstWithSignal) this).orAdditionalSource(mono)) == null) ? firstWithSignal(this, mono) : monoOrAdditionalSource;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <U> Mono<U> ofType(final Class<U> cls) {
        Objects.requireNonNull(cls, "clazz");
        return (Mono<U>) filter(new Predicate() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda16
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cls.isAssignableFrom(obj.getClass());
            }
        }).cast(cls);
    }

    public final Mono<T> onErrorComplete() {
        return onAssembly(new MonoOnErrorReturn(this, null, null));
    }

    public final Mono<T> onErrorComplete(Class<? extends Throwable> cls) {
        Objects.requireNonNull(cls, "type must not be null");
        Objects.requireNonNull(cls);
        return onErrorComplete(new Mono$$ExternalSyntheticLambda32(cls));
    }

    public final Mono<T> onErrorComplete(Predicate<? super Throwable> predicate) {
        Objects.requireNonNull(predicate, "predicate must not be null");
        return onAssembly(new MonoOnErrorReturn(this, predicate, null));
    }

    public final Mono<T> onErrorContinue(BiConsumer<Throwable, Object> biConsumer) {
        return contextWriteSkippingContextPropagation(Context.m1981of(OnNextFailureStrategy.KEY_ON_NEXT_ERROR_STRATEGY, OnNextFailureStrategy.resume(biConsumer)));
    }

    public final <E extends Throwable> Mono<T> onErrorContinue(Class<E> cls, BiConsumer<Throwable, Object> biConsumer) {
        Objects.requireNonNull(cls);
        return onErrorContinue(new Mono$$ExternalSyntheticLambda32(cls), biConsumer);
    }

    public final <E extends Throwable> Mono<T> onErrorContinue(Predicate<E> predicate, BiConsumer<Throwable, Object> biConsumer) {
        return contextWriteSkippingContextPropagation(Context.m1981of(OnNextFailureStrategy.KEY_ON_NEXT_ERROR_STRATEGY, OnNextFailureStrategy.resumeIf(predicate, biConsumer)));
    }

    public final Mono<T> onErrorStop() {
        return contextWriteSkippingContextPropagation(Context.m1981of(OnNextFailureStrategy.KEY_ON_NEXT_ERROR_STRATEGY, OnNextFailureStrategy.stop()));
    }

    public final Mono<T> onErrorMap(Predicate<? super Throwable> predicate, final Function<? super Throwable, ? extends Throwable> function) {
        return onErrorResume(predicate, new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda31
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mono.error((Throwable) function.apply((Throwable) obj));
            }
        });
    }

    public final Mono<T> onErrorMap(final Function<? super Throwable, ? extends Throwable> function) {
        return onErrorResume(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda39
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mono.error((Throwable) function.apply((Throwable) obj));
            }
        });
    }

    public final <E extends Throwable> Mono<T> onErrorMap(Class<E> cls, Function<? super E, ? extends Throwable> function) {
        Objects.requireNonNull(cls);
        return onErrorMap(new Mono$$ExternalSyntheticLambda32(cls), function);
    }

    public final Mono<T> onErrorResume(Function<? super Throwable, ? extends Mono<? extends T>> function) {
        return onAssembly(new MonoOnErrorResume(this, function));
    }

    public final <E extends Throwable> Mono<T> onErrorResume(Class<E> cls, Function<? super E, ? extends Mono<? extends T>> function) {
        Objects.requireNonNull(cls, TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE);
        Objects.requireNonNull(cls);
        return onErrorResume(new Mono$$ExternalSyntheticLambda32(cls), function);
    }

    public final Mono<T> onErrorResume(final Predicate<? super Throwable> predicate, final Function<? super Throwable, ? extends Mono<? extends T>> function) {
        Objects.requireNonNull(predicate, "predicate");
        return onErrorResume(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda29
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mono.lambda$onErrorResume$30(predicate, function, (Throwable) obj);
            }
        });
    }

    static /* synthetic */ Mono lambda$onErrorResume$30(Predicate predicate, Function function, Throwable th) {
        return predicate.test(th) ? (Mono) function.apply(th) : error(th);
    }

    public final Mono<T> onErrorReturn(T t) {
        Objects.requireNonNull(t, "fallbackValue must not be null");
        return onAssembly(new MonoOnErrorReturn(this, null, t));
    }

    public final <E extends Throwable> Mono<T> onErrorReturn(Class<E> cls, T t) {
        Objects.requireNonNull(cls, "type must not be null");
        Objects.requireNonNull(t, "fallbackValue must not be null");
        Objects.requireNonNull(cls);
        return onErrorReturn(new Mono$$ExternalSyntheticLambda32(cls), t);
    }

    public final Mono<T> onErrorReturn(Predicate<? super Throwable> predicate, T t) {
        Objects.requireNonNull(predicate, "predicate must not be null");
        Objects.requireNonNull(t, "fallbackValue must not be null");
        return onAssembly(new MonoOnErrorReturn(this, predicate, t));
    }

    public final Mono<T> onTerminateDetach() {
        return new MonoDetach(this);
    }

    public final <R> Mono<R> publish(Function<? super Mono<T>, ? extends Mono<? extends R>> function) {
        return onAssembly(new MonoPublishMulticast(this, function));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Mono<T> publishOn(Scheduler scheduler) {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                try {
                    return onAssembly(new MonoSubscribeOnValue(block(), scheduler));
                } catch (Throwable unused) {
                }
            }
            return onAssembly(new MonoSubscribeOnCallable((Callable) this, scheduler));
        }
        return onAssembly(new MonoPublishOn(this, scheduler));
    }

    public final Flux<T> repeat() {
        return repeat(Flux.ALWAYS_BOOLEAN_SUPPLIER);
    }

    public final Flux<T> repeat(BooleanSupplier booleanSupplier) {
        return Flux.onAssembly(new MonoRepeatPredicate(this, booleanSupplier));
    }

    public final Flux<T> repeat(long j) {
        if (j == 0) {
            return flux();
        }
        return Flux.onAssembly(new MonoRepeat(this, j));
    }

    public final Flux<T> repeat(final long j, final BooleanSupplier booleanSupplier) {
        if (j < 0) {
            throw new IllegalArgumentException("numRepeat >= 0 required");
        }
        if (j == 0) {
            return flux();
        }
        return Flux.defer(new Supplier() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda28
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m4287lambda$repeat$31$reactorcorepublisherMono(booleanSupplier, j);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$repeat$31$reactor-core-publisher-Mono, reason: not valid java name */
    /* synthetic */ Publisher m4287lambda$repeat$31$reactorcorepublisherMono(BooleanSupplier booleanSupplier, long j) {
        return repeat(Flux.countingBooleanSupplier(booleanSupplier, j));
    }

    public final Flux<T> repeatWhen(Function<Flux<Long>, ? extends Publisher<?>> function) {
        return Flux.onAssembly(new MonoRepeatWhen(this, function));
    }

    public final Mono<T> repeatWhenEmpty(Function<Flux<Long>, ? extends Publisher<?>> function) {
        return repeatWhenEmpty(Integer.MAX_VALUE, function);
    }

    /* JADX INFO: renamed from: lambda$repeatWhenEmpty$34$reactor-core-publisher-Mono, reason: not valid java name */
    /* synthetic */ Mono m4288lambda$repeatWhenEmpty$34$reactorcorepublisherMono(final int i, final Function function) {
        return repeatWhen(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda37
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mono.lambda$repeatWhenEmpty$33(i, function, (Flux) obj);
            }
        }).next();
    }

    public final Mono<T> repeatWhenEmpty(final int i, final Function<Flux<Long>, ? extends Publisher<?>> function) {
        return defer(new Supplier() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m4288lambda$repeatWhenEmpty$34$reactorcorepublisherMono(i, function);
            }
        });
    }

    static /* synthetic */ Publisher lambda$repeatWhenEmpty$33(int i, Function function, Flux flux) {
        if (i == Integer.MAX_VALUE) {
            return (Publisher) function.apply(flux.index().map(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (Long) ((Tuple2) obj).getT1();
                }
            }));
        }
        return (Publisher) function.apply(flux.index().map(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (Long) ((Tuple2) obj).getT1();
            }
        }).take(i, false).concatWith(Flux.error((Supplier<? extends Throwable>) new Supplier() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                return Mono.lambda$repeatWhenEmpty$32();
            }
        })));
    }

    static /* synthetic */ Throwable lambda$repeatWhenEmpty$32() {
        return new IllegalStateException("Exceeded maximum number of repeats");
    }

    public final Mono<T> retry() {
        return retry(Long.MAX_VALUE);
    }

    public final Mono<T> retry(long j) {
        return onAssembly(new MonoRetry(this, j));
    }

    public final Mono<T> retryWhen(Retry retry) {
        return onAssembly(new MonoRetryWhen(this, retry));
    }

    public final Mono<T> share() {
        return this instanceof Fuseable.ScalarCallable ? this : ((this instanceof NextProcessor) && ((NextProcessor) this).isRefCounted) ? this : new NextProcessor(this, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Mono<T> single() {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                try {
                    T tCall = ((Fuseable.ScalarCallable) this).call();
                    if (tCall == null) {
                        return error(new NoSuchElementException("Source was a (constant) empty"));
                    }
                    return just(tCall);
                } catch (Exception e) {
                    return error(Exceptions.unwrap(e));
                }
            }
            return onAssembly(new MonoSingleCallable((Callable) this));
        }
        return onAssembly(new MonoSingleMono(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Mono<Optional<T>> singleOptional() {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                try {
                    return just(Optional.ofNullable(((Fuseable.ScalarCallable) this).call()));
                } catch (Exception e) {
                    return error(Exceptions.unwrap(e));
                }
            }
            return onAssembly(new MonoSingleOptionalCallable((Callable) this));
        }
        return onAssembly(new MonoSingleOptional(this));
    }

    public final Disposable subscribe() {
        if (this instanceof NextProcessor) {
            NextProcessor nextProcessor = (NextProcessor) this;
            if (nextProcessor.source != null && !nextProcessor.isRefCounted) {
                nextProcessor.subscribe((CoreSubscriber) new LambdaMonoSubscriber(null, null, null, null, null));
                nextProcessor.connect();
                return nextProcessor;
            }
        }
        return (Disposable) subscribeWith(new LambdaMonoSubscriber(null, null, null, null, null));
    }

    public final Disposable subscribe(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "consumer");
        return subscribe(consumer, null, null);
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, Consumer<? super Throwable> consumer2) {
        Objects.requireNonNull(consumer2, "errorConsumer");
        return subscribe(consumer, consumer2, null);
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable Runnable runnable) {
        return subscribe(consumer, consumer2, runnable, (Context) null);
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable Runnable runnable, @Nullable Consumer<? super Subscription> consumer3) {
        return (Disposable) subscribeWith(new LambdaMonoSubscriber(consumer, consumer2, runnable, consumer3, null));
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable Runnable runnable, @Nullable Context context) {
        return (Disposable) subscribeWith(new LambdaMonoSubscriber(consumer, consumer2, runnable, null, context));
    }

    @Override // org.reactivestreams.Publisher
    public final void subscribe(Subscriber<? super T> subscriber) {
        CorePublisher corePublisherOnLastAssembly = Operators.onLastAssembly(this);
        CoreSubscriber<? super T> coreSubscriber = Operators.toCoreSubscriber(subscriber);
        if ((coreSubscriber instanceof Fuseable.QueueSubscription) && this != corePublisherOnLastAssembly && (this instanceof Fuseable) && !(corePublisherOnLastAssembly instanceof Fuseable)) {
            coreSubscriber = new FluxHide.SuppressFuseableSubscriber(coreSubscriber);
        }
        try {
            if (corePublisherOnLastAssembly instanceof OptimizableOperator) {
                OptimizableOperator optimizableOperator = (OptimizableOperator) corePublisherOnLastAssembly;
                while (true) {
                    coreSubscriber = optimizableOperator.subscribeOrReturn(coreSubscriber);
                    if (coreSubscriber == null) {
                        return;
                    }
                    OptimizableOperator optimizableOperatorNextOptimizableSource = optimizableOperator.nextOptimizableSource();
                    if (optimizableOperatorNextOptimizableSource == null) {
                        corePublisherOnLastAssembly = optimizableOperator.source();
                        break;
                    }
                    optimizableOperator = optimizableOperatorNextOptimizableSource;
                }
            }
            coreSubscriber = Operators.restoreContextOnSubscriberIfPublisherNonInternal(corePublisherOnLastAssembly, coreSubscriber);
            corePublisherOnLastAssembly.subscribe((CoreSubscriber) coreSubscriber);
        } catch (Throwable th) {
            Operators.reportThrowInSubscribe(coreSubscriber, th);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Mono<T> subscribeOn(Scheduler scheduler) {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                try {
                    return onAssembly(new MonoSubscribeOnValue(block(), scheduler));
                } catch (Throwable unused) {
                }
            }
            return onAssembly(new MonoSubscribeOnCallable((Callable) this, scheduler));
        }
        return onAssembly(new MonoSubscribeOn(this, scheduler));
    }

    public final <E extends Subscriber<? super T>> E subscribeWith(E e) {
        subscribe(e);
        return e;
    }

    public final Mono<T> switchIfEmpty(Mono<? extends T> mono) {
        return onAssembly(new MonoSwitchIfEmpty(this, mono));
    }

    public final Mono<T> tag(String str, String str2) {
        return MonoName.createOrAppend(this, str, str2);
    }

    public final Mono<T> take(Duration duration) {
        return take(duration, Schedulers.parallel());
    }

    public final Mono<T> take(Duration duration, Scheduler scheduler) {
        return takeUntilOther(delay(duration, scheduler));
    }

    public final Mono<T> takeUntilOther(Publisher<?> publisher) {
        return onAssembly(new MonoTakeUntilOther(this, publisher));
    }

    public final Mono<T> tap(final Supplier<SignalListener<T>> supplier) {
        return tap(new SignalListenerFactory<T, Void>() { // from class: reactor.core.publisher.Mono.1
            @Override // reactor.core.observability.SignalListenerFactory
            public Void initializePublisherState(Publisher<? extends T> publisher) {
                return null;
            }

            @Override // reactor.core.observability.SignalListenerFactory
            public SignalListener<T> createListener(Publisher<? extends T> publisher, ContextView contextView, Void r3) {
                return (SignalListener) supplier.get();
            }
        });
    }

    public final Mono<T> tap(final Function<ContextView, SignalListener<T>> function) {
        return tap(new SignalListenerFactory<T, Void>() { // from class: reactor.core.publisher.Mono.2
            @Override // reactor.core.observability.SignalListenerFactory
            public Void initializePublisherState(Publisher<? extends T> publisher) {
                return null;
            }

            @Override // reactor.core.observability.SignalListenerFactory
            public SignalListener<T> createListener(Publisher<? extends T> publisher, ContextView contextView, Void r3) {
                return (SignalListener) function.apply(contextView);
            }
        });
    }

    public final Mono<T> tap(SignalListenerFactory<T, ?> signalListenerFactory) {
        if (ContextPropagationSupport.shouldPropagateContextToThreadLocals()) {
            return onAssembly(new MonoTapRestoringThreadLocals(this, signalListenerFactory));
        }
        if (this instanceof Fuseable) {
            return onAssembly(new MonoTapFuseable(this, signalListenerFactory));
        }
        return onAssembly(new MonoTap(this, signalListenerFactory));
    }

    public final Mono<Void> then() {
        return empty(this);
    }

    public final <V> Mono<V> then(Mono<V> mono) {
        if (this instanceof MonoIgnoreThen) {
            return ((MonoIgnoreThen) this).shift(mono);
        }
        return onAssembly(new MonoIgnoreThen(new Publisher[]{this}, mono));
    }

    public final <V> Mono<V> thenReturn(V v) {
        return then(just(v));
    }

    public final Mono<Void> thenEmpty(Publisher<Void> publisher) {
        return then(fromDirect(publisher));
    }

    public final <V> Flux<V> thenMany(Publisher<V> publisher) {
        return Flux.onAssembly(Flux.concat(ignoreElement(), publisher));
    }

    public final Mono<Timed<T>> timed() {
        return timed(Schedulers.parallel());
    }

    public final Mono<Timed<T>> timed(Scheduler scheduler) {
        return onAssembly(new MonoTimed(this, scheduler));
    }

    public final Mono<T> timeout(Duration duration) {
        return timeout(duration, Schedulers.parallel());
    }

    public final Mono<T> timeout(Duration duration, Mono<? extends T> mono) {
        return timeout(duration, mono, Schedulers.parallel());
    }

    public final Mono<T> timeout(Duration duration, Scheduler scheduler) {
        return timeout(duration, null, scheduler);
    }

    public final Mono<T> timeout(Duration duration, @Nullable Mono<? extends T> mono, Scheduler scheduler) {
        Mono<Long> monoOnErrorReturn = delay(duration, scheduler).onErrorReturn(0L);
        if (mono == null) {
            return onAssembly(new MonoTimeout(this, monoOnErrorReturn, duration.toMillis() + "ms"));
        }
        return onAssembly(new MonoTimeout(this, monoOnErrorReturn, mono));
    }

    public final <U> Mono<T> timeout(Publisher<U> publisher) {
        return onAssembly(new MonoTimeout(this, publisher, "first signal from a Publisher"));
    }

    public final <U> Mono<T> timeout(Publisher<U> publisher, Mono<? extends T> mono) {
        return onAssembly(new MonoTimeout(this, publisher, mono));
    }

    public final Mono<Tuple2<Long, T>> timestamp() {
        return timestamp(Schedulers.parallel());
    }

    public final Mono<Tuple2<Long, T>> timestamp(final Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler");
        return (Mono<Tuple2<Long, T>>) map(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda21
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.m1988of(Long.valueOf(scheduler.now(TimeUnit.MILLISECONDS)), obj);
            }
        });
    }

    public final CompletableFuture<T> toFuture() {
        return (CompletableFuture) subscribeWith(new MonoToCompletableFuture(false));
    }

    public final <V> Mono<V> transform(Function<? super Mono<T>, ? extends Publisher<V>> function) {
        if (Hooks.DETECT_CONTEXT_LOSS) {
            function = new ContextTrackingFunctionWrapper(function);
        }
        return onAssembly(from(function.apply(this)));
    }

    public final <V> Mono<V> transformDeferred(final Function<? super Mono<T>, ? extends Publisher<V>> function) {
        return defer(new Supplier() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda15
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m4289lambda$transformDeferred$36$reactorcorepublisherMono(function);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$transformDeferred$36$reactor-core-publisher-Mono, reason: not valid java name */
    /* synthetic */ Mono m4289lambda$transformDeferred$36$reactorcorepublisherMono(Function function) {
        if (Hooks.DETECT_CONTEXT_LOSS) {
            return from(new ContextTrackingFunctionWrapper(function).apply((Publisher) this));
        }
        return from((Publisher) function.apply(this));
    }

    public final <V> Mono<V> transformDeferredContextual(final BiFunction<? super Mono<T>, ? super ContextView, ? extends Publisher<V>> biFunction) {
        return deferContextual(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda20
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m1967xa1e3773d(biFunction, (ContextView) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$transformDeferredContextual$38$reactor-core-publisher-Mono */
    /* synthetic */ Mono m1967xa1e3773d(final BiFunction biFunction, final ContextView contextView) {
        if (Hooks.DETECT_CONTEXT_LOSS) {
            return wrap(new ContextTrackingFunctionWrapper(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda43
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Mono.lambda$transformDeferredContextual$37(biFunction, contextView, (Publisher) obj);
                }
            }, biFunction.toString()).apply((Publisher) this), true);
        }
        return from((Publisher) biFunction.apply(this, contextView));
    }

    static /* synthetic */ Publisher lambda$transformDeferredContextual$37(BiFunction biFunction, ContextView contextView, Publisher publisher) {
        return (Publisher) biFunction.apply(wrap(publisher, false), contextView);
    }

    public final <T2> Mono<Tuple2<T, T2>> zipWhen(Function<T, Mono<? extends T2>> function) {
        return (Mono<Tuple2<T, T2>>) zipWhen(function, new Flux$$ExternalSyntheticLambda2());
    }

    public final <T2, O> Mono<O> zipWhen(final Function<T, Mono<? extends T2>> function, final BiFunction<T, T2, O> biFunction) {
        Objects.requireNonNull(function, "rightGenerator function is mandatory to get the right-hand side Mono");
        Objects.requireNonNull(biFunction, "combinator function is mandatory to combine results from both Monos");
        return (Mono<O>) flatMap(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Mono) function.apply(obj)).map(new Function() { // from class: reactor.core.publisher.Mono$$ExternalSyntheticLambda24
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        return biFunction.apply(obj, obj2);
                    }
                });
            }
        });
    }

    public final <T2> Mono<Tuple2<T, T2>> zipWith(Mono<? extends T2> mono) {
        return (Mono<Tuple2<T, T2>>) zipWith(mono, Flux.tuple2Function());
    }

    public final <T2, O> Mono<O> zipWith(Mono<? extends T2> mono, BiFunction<? super T, ? super T2, ? extends O> biFunction) {
        Mono<O> monoZipAdditionalSource;
        return (!(this instanceof MonoZip) || (monoZipAdditionalSource = ((MonoZip) this).zipAdditionalSource(mono, biFunction)) == null) ? zip(this, mono, biFunction) : monoZipAdditionalSource;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static <T> Mono<T> onAssembly(Mono<T> mono) {
        Function<Publisher, Publisher> function = Hooks.onEachOperatorHook;
        if (function != null) {
            mono = (Mono) function.apply(mono);
        }
        return Hooks.GLOBAL_TRACE ? (Mono) Hooks.addAssemblyInfo(mono, new FluxOnAssembly.AssemblySnapshot(null, Traces.callSiteSupplierFactory.get())) : mono;
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    static <T> Mono<Void> empty(Publisher<T> publisher) {
        return ignoreElements(publisher);
    }

    static <T> Mono<T> doOnSignal(Mono<T> mono, @Nullable Consumer<? super Subscription> consumer, @Nullable Consumer<? super T> consumer2, @Nullable LongConsumer longConsumer, @Nullable Runnable runnable) {
        if (mono instanceof Fuseable) {
            return onAssembly(new MonoPeekFuseable(mono, consumer, consumer2, longConsumer, runnable));
        }
        return onAssembly(new MonoPeek(mono, consumer, consumer2, longConsumer, runnable));
    }

    static <T> Mono<T> doOnTerminalSignal(Mono<T> mono, @Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable BiConsumer<? super T, Throwable> biConsumer) {
        return onAssembly(new MonoPeekTerminal(mono, consumer, consumer2, biConsumer));
    }

    static <T> Mono<T> wrap(Publisher<T> publisher, boolean z) {
        Mono<T> monoSource;
        boolean zShouldWrapPublisher = ContextPropagationSupport.shouldWrapPublisher(publisher);
        if (publisher instanceof Mono) {
            if (!zShouldWrapPublisher) {
                return (Mono) publisher;
            }
            return ContextPropagation.monoRestoreThreadLocals((Mono) publisher);
        }
        if ((publisher instanceof FluxSourceMono) || (publisher instanceof FluxSourceMonoFuseable)) {
            Mono<T> mono = (Mono<T>) ((FluxFromMonoOperator) publisher).source;
            return !ContextPropagationSupport.shouldWrapPublisher(mono) ? mono : ContextPropagation.monoRestoreThreadLocals(mono);
        }
        boolean z2 = publisher instanceof Flux;
        if (z2 && (publisher instanceof Callable)) {
            return Flux.wrapToMono((Callable) publisher);
        }
        if (z) {
            if (z2) {
                monoSource = new MonoNext<>((Flux) publisher);
            } else {
                monoSource = new MonoFromPublisher<>(publisher);
            }
        } else if (z2 && (publisher instanceof Fuseable)) {
            monoSource = new MonoSourceFluxFuseable<>((Flux) publisher);
        } else if (z2) {
            monoSource = new MonoSourceFlux<>((Flux) publisher);
        } else if (publisher instanceof Fuseable) {
            monoSource = new MonoSourceFuseable<>(publisher);
        } else {
            monoSource = new MonoSource<>(publisher);
        }
        return zShouldWrapPublisher ? ContextPropagation.monoRestoreThreadLocals(monoSource) : monoSource;
    }

    static <T> BiPredicate<? super T, ? super T> equalsBiPredicate() {
        return EQUALS_BIPREDICATE;
    }
}
