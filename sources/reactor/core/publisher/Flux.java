package reactor.core.publisher;

import com.microsoft.azure.storage.table.TableConstants;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collector;
import java.util.stream.Stream;
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
import reactor.core.publisher.FluxBufferPredicate;
import reactor.core.publisher.FluxCombineLatest;
import reactor.core.publisher.FluxConcatMap;
import reactor.core.publisher.FluxCreate;
import reactor.core.publisher.FluxHide;
import reactor.core.publisher.FluxOnAssembly;
import reactor.core.publisher.FluxSink;
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
public abstract class Flux<T> implements CorePublisher<T> {
    static final BiFunction TUPLE2_BIFUNCTION = new Flux$$ExternalSyntheticLambda2();
    static final Supplier LIST_SUPPLIER = new Supplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda3
        @Override // java.util.function.Supplier
        public final Object get() {
            return Flux.m4276$r8$lambda$3QDcPx_H9ZbYCstET9Nk_h1Fps();
        }
    };
    static final Supplier SET_SUPPLIER = new Supplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda4
        @Override // java.util.function.Supplier
        public final Object get() {
            return Flux.$r8$lambda$i0xaqPRVvSLVFa8Q5zyijLb7w_A();
        }
    };
    static final BooleanSupplier ALWAYS_BOOLEAN_SUPPLIER = new BooleanSupplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda5
        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            return Flux.lambda$static$45();
        }
    };
    static final BiPredicate OBJECT_EQUAL = new BiPredicate() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda6
        @Override // java.util.function.BiPredicate
        public final boolean test(Object obj, Object obj2) {
            return obj.equals(obj2);
        }
    };
    static final Function IDENTITY_FUNCTION = Function.identity();

    /* JADX INFO: renamed from: $r8$lambda$3QDcPx_H9ZbYCstET9N-k_h1Fps, reason: not valid java name */
    public static /* synthetic */ ArrayList m4276$r8$lambda$3QDcPx_H9ZbYCstET9Nk_h1Fps() {
        return new ArrayList();
    }

    public static /* synthetic */ HashSet $r8$lambda$i0xaqPRVvSLVFa8Q5zyijLb7w_A() {
        return new HashSet();
    }

    static /* synthetic */ Stream lambda$fromStream$3(Stream stream) {
        return stream;
    }

    static /* synthetic */ Logger lambda$log$24(Logger logger, String str) {
        return logger;
    }

    static /* synthetic */ Object lambda$reduce$31(Object obj) {
        return obj;
    }

    static /* synthetic */ Object lambda$scan$34(Object obj) {
        return obj;
    }

    static /* synthetic */ boolean lambda$static$45() {
        return true;
    }

    static /* synthetic */ Publisher lambda$timeout$35(Mono mono, Object obj) {
        return mono;
    }

    public int getPrefetch() {
        return -1;
    }

    @Override // reactor.core.CorePublisher
    public abstract void subscribe(CoreSubscriber<? super T> coreSubscriber);

    @SafeVarargs
    public static <T, V> Flux<V> combineLatest(Function<Object[], V> function, Publisher<? extends T>... publisherArr) {
        return combineLatest(function, Queues.XS_BUFFER_SIZE, publisherArr);
    }

    @SafeVarargs
    public static <T, V> Flux<V> combineLatest(final Function<Object[], V> function, int i, Publisher<? extends T>... publisherArr) {
        if (publisherArr.length == 0) {
            return empty();
        }
        if (publisherArr.length == 1) {
            Publisher<? extends T> publisher = publisherArr[0];
            if (publisher instanceof Fuseable) {
                return onAssembly(new FluxMapFuseable(from(publisher), new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda35
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return function.apply(new Object[]{obj});
                    }
                }));
            }
            return onAssembly(new FluxMap(from(publisher), new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda36
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return function.apply(new Object[]{obj});
                }
            }));
        }
        return onAssembly(new FluxCombineLatest(publisherArr, function, (Supplier<? extends Queue<FluxCombineLatest.SourceAndArray>>) Queues.get(i), i));
    }

    public static <T1, T2, V> Flux<V> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, final BiFunction<? super T1, ? super T2, ? extends V> biFunction) {
        return combineLatest(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda25
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Object[] objArr = (Object[]) obj;
                return biFunction.apply(objArr[0], objArr[1]);
            }
        }, publisher, publisher2);
    }

    public static <T1, T2, T3, V> Flux<V> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Function<Object[], V> function) {
        return combineLatest(function, publisher, publisher2, publisher3);
    }

    public static <T1, T2, T3, T4, V> Flux<V> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Function<Object[], V> function) {
        return combineLatest(function, publisher, publisher2, publisher3, publisher4);
    }

    public static <T1, T2, T3, T4, T5, V> Flux<V> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Function<Object[], V> function) {
        return combineLatest(function, publisher, publisher2, publisher3, publisher4, publisher5);
    }

    public static <T1, T2, T3, T4, T5, T6, V> Flux<V> combineLatest(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Publisher<? extends T6> publisher6, Function<Object[], V> function) {
        return combineLatest(function, publisher, publisher2, publisher3, publisher4, publisher5, publisher6);
    }

    public static <T, V> Flux<V> combineLatest(Iterable<? extends Publisher<? extends T>> iterable, Function<Object[], V> function) {
        return combineLatest(iterable, Queues.XS_BUFFER_SIZE, function);
    }

    public static <T, V> Flux<V> combineLatest(Iterable<? extends Publisher<? extends T>> iterable, int i, Function<Object[], V> function) {
        return onAssembly(new FluxCombineLatest(iterable, function, (Supplier<? extends Queue<FluxCombineLatest.SourceAndArray>>) Queues.get(i), i));
    }

    public static <T> Flux<T> concat(Iterable<? extends Publisher<? extends T>> iterable) {
        return onAssembly(new FluxConcatIterable(iterable));
    }

    @SafeVarargs
    public final Flux<T> concatWithValues(T... tArr) {
        return concatWith(fromArray(tArr));
    }

    public static <T> Flux<T> concat(Publisher<? extends Publisher<? extends T>> publisher) {
        return concat(publisher, Queues.XS_BUFFER_SIZE);
    }

    public static <T> Flux<T> concat(Publisher<? extends Publisher<? extends T>> publisher, int i) {
        return from(publisher).concatMap(identityFunction(), i);
    }

    @SafeVarargs
    public static <T> Flux<T> concat(Publisher<? extends T>... publisherArr) {
        return onAssembly(new FluxConcatArray(false, publisherArr));
    }

    public static <T> Flux<T> concatDelayError(Publisher<? extends Publisher<? extends T>> publisher) {
        return concatDelayError(publisher, Queues.XS_BUFFER_SIZE);
    }

    public static <T> Flux<T> concatDelayError(Publisher<? extends Publisher<? extends T>> publisher, int i) {
        return from(publisher).concatMapDelayError(identityFunction(), i);
    }

    public static <T> Flux<T> concatDelayError(Publisher<? extends Publisher<? extends T>> publisher, boolean z, int i) {
        return from(publisher).concatMapDelayError(identityFunction(), z, i);
    }

    @SafeVarargs
    public static <T> Flux<T> concatDelayError(Publisher<? extends T>... publisherArr) {
        return onAssembly(new FluxConcatArray(true, publisherArr));
    }

    public static <T> Flux<T> create(Consumer<? super FluxSink<T>> consumer) {
        return create(consumer, FluxSink.OverflowStrategy.BUFFER);
    }

    public static <T> Flux<T> create(Consumer<? super FluxSink<T>> consumer, FluxSink.OverflowStrategy overflowStrategy) {
        return onAssembly(new FluxCreate(consumer, overflowStrategy, FluxCreate.CreateMode.PUSH_PULL));
    }

    public static <T> Flux<T> push(Consumer<? super FluxSink<T>> consumer) {
        return push(consumer, FluxSink.OverflowStrategy.BUFFER);
    }

    public static <T> Flux<T> push(Consumer<? super FluxSink<T>> consumer, FluxSink.OverflowStrategy overflowStrategy) {
        return onAssembly(new FluxCreate(consumer, overflowStrategy, FluxCreate.CreateMode.PUSH_ONLY));
    }

    public static <T> Flux<T> defer(Supplier<? extends Publisher<T>> supplier) {
        return onAssembly(new FluxDefer(supplier));
    }

    public static <T> Flux<T> deferContextual(Function<ContextView, ? extends Publisher<T>> function) {
        return onAssembly(new FluxDeferContextual(function));
    }

    public static <T> Flux<T> empty() {
        return FluxEmpty.instance();
    }

    public static <T> Flux<T> error(Throwable th) {
        return error(th, false);
    }

    public static <T> Flux<T> error(Supplier<? extends Throwable> supplier) {
        return onAssembly(new FluxErrorSupplied(supplier));
    }

    public static <O> Flux<O> error(Throwable th, boolean z) {
        if (z) {
            return onAssembly(new FluxErrorOnRequest(th));
        }
        return onAssembly(new FluxError(th));
    }

    @SafeVarargs
    @Deprecated
    public static <I> Flux<I> first(Publisher<? extends I>... publisherArr) {
        return firstWithSignal(publisherArr);
    }

    @Deprecated
    public static <I> Flux<I> first(Iterable<? extends Publisher<? extends I>> iterable) {
        return firstWithSignal(iterable);
    }

    @SafeVarargs
    public static <I> Flux<I> firstWithSignal(Publisher<? extends I>... publisherArr) {
        return onAssembly(new FluxFirstWithSignal(publisherArr));
    }

    public static <I> Flux<I> firstWithSignal(Iterable<? extends Publisher<? extends I>> iterable) {
        return onAssembly(new FluxFirstWithSignal(iterable));
    }

    public static <I> Flux<I> firstWithValue(Iterable<? extends Publisher<? extends I>> iterable) {
        return onAssembly(new FluxFirstWithValue(iterable));
    }

    @SafeVarargs
    public static <I> Flux<I> firstWithValue(Publisher<? extends I> publisher, Publisher<? extends I>... publisherArr) {
        FluxFirstWithValue<T> fluxFirstWithValueFirstValuedAdditionalSources;
        return (!(publisher instanceof FluxFirstWithValue) || (fluxFirstWithValueFirstValuedAdditionalSources = ((FluxFirstWithValue) publisher).firstValuedAdditionalSources(publisherArr)) == null) ? onAssembly(new FluxFirstWithValue(publisher, publisherArr)) : fluxFirstWithValueFirstValuedAdditionalSources;
    }

    public static <T> Flux<T> from(Publisher<? extends T> publisher) {
        if ((publisher instanceof Flux) && !ContextPropagationSupport.shouldWrapPublisher(publisher)) {
            return (Flux) publisher;
        }
        return onAssembly(wrap(publisher));
    }

    public static <T> Flux<T> fromArray(T[] tArr) {
        if (tArr.length == 0) {
            return empty();
        }
        if (tArr.length == 1) {
            return just(tArr[0]);
        }
        return onAssembly(new FluxArray(tArr));
    }

    public static <T> Flux<T> fromIterable(Iterable<? extends T> iterable) {
        return onAssembly(new FluxIterable(iterable));
    }

    public static <T> Flux<T> fromStream(final Stream<? extends T> stream) {
        Objects.requireNonNull(stream, "Stream s must be provided");
        return onAssembly(new FluxStream(new Supplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda37
            @Override // java.util.function.Supplier
            public final Object get() {
                return Flux.lambda$fromStream$3(stream);
            }
        }));
    }

    public static <T> Flux<T> fromStream(Supplier<Stream<? extends T>> supplier) {
        return onAssembly(new FluxStream(supplier));
    }

    public static <T> Flux<T> generate(Consumer<SynchronousSink<T>> consumer) {
        Objects.requireNonNull(consumer, "generator");
        return onAssembly(new FluxGenerate(consumer));
    }

    public static <T, S> Flux<T> generate(Callable<S> callable, BiFunction<S, SynchronousSink<T>, S> biFunction) {
        return onAssembly(new FluxGenerate(callable, biFunction));
    }

    public static <T, S> Flux<T> generate(Callable<S> callable, BiFunction<S, SynchronousSink<T>, S> biFunction, Consumer<? super S> consumer) {
        return onAssembly(new FluxGenerate(callable, biFunction, consumer));
    }

    public static Flux<Long> interval(Duration duration) {
        return interval(duration, Schedulers.parallel());
    }

    public static Flux<Long> interval(Duration duration, Duration duration2) {
        return interval(duration, duration2, Schedulers.parallel());
    }

    public static Flux<Long> interval(Duration duration, Scheduler scheduler) {
        return interval(duration, duration, scheduler);
    }

    public static Flux<Long> interval(Duration duration, Duration duration2, Scheduler scheduler) {
        return onAssembly(new FluxInterval(duration.toNanos(), duration2.toNanos(), TimeUnit.NANOSECONDS, scheduler));
    }

    @SafeVarargs
    public static <T> Flux<T> just(T... tArr) {
        return fromArray(tArr);
    }

    public static <T> Flux<T> just(T t) {
        return onAssembly(new FluxJust(t));
    }

    public static <T> Flux<T> merge(Publisher<? extends Publisher<? extends T>> publisher) {
        return merge(publisher, Queues.SMALL_BUFFER_SIZE, Queues.XS_BUFFER_SIZE);
    }

    public static <T> Flux<T> merge(Publisher<? extends Publisher<? extends T>> publisher, int i) {
        return merge(publisher, i, Queues.XS_BUFFER_SIZE);
    }

    public static <T> Flux<T> merge(Publisher<? extends Publisher<? extends T>> publisher, int i, int i2) {
        return onAssembly(new FluxFlatMap(from(publisher), identityFunction(), false, i, Queues.get(i), i2, Queues.get(i2)));
    }

    public static <I> Flux<I> merge(Iterable<? extends Publisher<? extends I>> iterable) {
        return merge(fromIterable(iterable));
    }

    @SafeVarargs
    public static <I> Flux<I> merge(Publisher<? extends I>... publisherArr) {
        return merge(Queues.XS_BUFFER_SIZE, publisherArr);
    }

    @SafeVarargs
    public static <I> Flux<I> merge(int i, Publisher<? extends I>... publisherArr) {
        return merge(i, false, (Publisher[]) publisherArr);
    }

    @SafeVarargs
    public static <I> Flux<I> mergeDelayError(int i, Publisher<? extends I>... publisherArr) {
        return merge(i, true, (Publisher[]) publisherArr);
    }

    @SafeVarargs
    public static <I extends Comparable<? super I>> Flux<I> mergePriority(Publisher<? extends I>... publisherArr) {
        return mergePriority(Queues.SMALL_BUFFER_SIZE, Comparator.naturalOrder(), publisherArr);
    }

    @SafeVarargs
    public static <T> Flux<T> mergePriority(Comparator<? super T> comparator, Publisher<? extends T>... publisherArr) {
        return mergePriority(Queues.SMALL_BUFFER_SIZE, comparator, publisherArr);
    }

    @SafeVarargs
    public static <T> Flux<T> mergePriority(int i, Comparator<? super T> comparator, Publisher<? extends T>... publisherArr) {
        if (publisherArr.length == 0) {
            return empty();
        }
        if (publisherArr.length == 1) {
            return from(publisherArr[0]);
        }
        return onAssembly(new FluxMergeComparing(i, comparator, false, false, publisherArr));
    }

    @SafeVarargs
    public static <T> Flux<T> mergePriorityDelayError(int i, Comparator<? super T> comparator, Publisher<? extends T>... publisherArr) {
        if (publisherArr.length == 0) {
            return empty();
        }
        if (publisherArr.length == 1) {
            return from(publisherArr[0]);
        }
        return onAssembly(new FluxMergeComparing(i, comparator, true, false, publisherArr));
    }

    @SafeVarargs
    public static <I extends Comparable<? super I>> Flux<I> mergeComparing(Publisher<? extends I>... publisherArr) {
        return mergeComparing(Queues.SMALL_BUFFER_SIZE, Comparator.naturalOrder(), publisherArr);
    }

    @SafeVarargs
    public static <T> Flux<T> mergeComparing(Comparator<? super T> comparator, Publisher<? extends T>... publisherArr) {
        return mergeComparing(Queues.SMALL_BUFFER_SIZE, comparator, publisherArr);
    }

    @SafeVarargs
    public static <T> Flux<T> mergeComparing(int i, Comparator<? super T> comparator, Publisher<? extends T>... publisherArr) {
        if (publisherArr.length == 0) {
            return empty();
        }
        if (publisherArr.length == 1) {
            return from(publisherArr[0]);
        }
        return onAssembly(new FluxMergeComparing(i, comparator, false, true, publisherArr));
    }

    @SafeVarargs
    public static <T> Flux<T> mergeComparingDelayError(int i, Comparator<? super T> comparator, Publisher<? extends T>... publisherArr) {
        if (publisherArr.length == 0) {
            return empty();
        }
        if (publisherArr.length == 1) {
            return from(publisherArr[0]);
        }
        return onAssembly(new FluxMergeComparing(i, comparator, true, true, publisherArr));
    }

    @SafeVarargs
    @Deprecated
    public static <I extends Comparable<? super I>> Flux<I> mergeOrdered(Publisher<? extends I>... publisherArr) {
        return mergeOrdered(Queues.SMALL_BUFFER_SIZE, Comparator.naturalOrder(), publisherArr);
    }

    @SafeVarargs
    @Deprecated
    public static <T> Flux<T> mergeOrdered(Comparator<? super T> comparator, Publisher<? extends T>... publisherArr) {
        return mergeOrdered(Queues.SMALL_BUFFER_SIZE, comparator, publisherArr);
    }

    @SafeVarargs
    @Deprecated
    public static <T> Flux<T> mergeOrdered(int i, Comparator<? super T> comparator, Publisher<? extends T>... publisherArr) {
        if (publisherArr.length == 0) {
            return empty();
        }
        if (publisherArr.length == 1) {
            return from(publisherArr[0]);
        }
        return onAssembly(new FluxMergeComparing(i, comparator, true, true, publisherArr));
    }

    public static <T> Flux<T> mergeSequential(Publisher<? extends Publisher<? extends T>> publisher) {
        return mergeSequential((Publisher) publisher, false, Queues.SMALL_BUFFER_SIZE, Queues.XS_BUFFER_SIZE);
    }

    public static <T> Flux<T> mergeSequential(Publisher<? extends Publisher<? extends T>> publisher, int i, int i2) {
        return mergeSequential((Publisher) publisher, false, i, i2);
    }

    public static <T> Flux<T> mergeSequentialDelayError(Publisher<? extends Publisher<? extends T>> publisher, int i, int i2) {
        return mergeSequential((Publisher) publisher, true, i, i2);
    }

    @SafeVarargs
    public static <I> Flux<I> mergeSequential(Publisher<? extends I>... publisherArr) {
        return mergeSequential(Queues.XS_BUFFER_SIZE, false, (Publisher[]) publisherArr);
    }

    @SafeVarargs
    public static <I> Flux<I> mergeSequential(int i, Publisher<? extends I>... publisherArr) {
        return mergeSequential(i, false, (Publisher[]) publisherArr);
    }

    @SafeVarargs
    public static <I> Flux<I> mergeSequentialDelayError(int i, Publisher<? extends I>... publisherArr) {
        return mergeSequential(i, true, (Publisher[]) publisherArr);
    }

    public static <I> Flux<I> mergeSequential(Iterable<? extends Publisher<? extends I>> iterable) {
        return mergeSequential((Iterable) iterable, false, Queues.SMALL_BUFFER_SIZE, Queues.XS_BUFFER_SIZE);
    }

    public static <I> Flux<I> mergeSequential(Iterable<? extends Publisher<? extends I>> iterable, int i, int i2) {
        return mergeSequential((Iterable) iterable, false, i, i2);
    }

    public static <I> Flux<I> mergeSequentialDelayError(Iterable<? extends Publisher<? extends I>> iterable, int i, int i2) {
        return mergeSequential((Iterable) iterable, true, i, i2);
    }

    public static <T> Flux<T> never() {
        return FluxNever.instance();
    }

    public static Flux<Integer> range(int i, int i2) {
        if (i2 == 1) {
            return just(Integer.valueOf(i));
        }
        if (i2 == 0) {
            return empty();
        }
        return onAssembly(new FluxRange(i, i2));
    }

    public static <T> Flux<T> switchOnNext(Publisher<? extends Publisher<? extends T>> publisher) {
        return onAssembly(new FluxSwitchMapNoPrefetch(from(publisher), identityFunction()));
    }

    @Deprecated
    public static <T> Flux<T> switchOnNext(Publisher<? extends Publisher<? extends T>> publisher, int i) {
        if (i == 0) {
            return onAssembly(new FluxSwitchMapNoPrefetch(from(publisher), identityFunction()));
        }
        return onAssembly(new FluxSwitchMap(from(publisher), identityFunction(), Queues.unbounded(i), i));
    }

    public static <T, D> Flux<T> using(Callable<? extends D> callable, Function<? super D, ? extends Publisher<? extends T>> function, Consumer<? super D> consumer) {
        return using(callable, function, consumer, true);
    }

    public static <T, D> Flux<T> using(Callable<? extends D> callable, Function<? super D, ? extends Publisher<? extends T>> function, Consumer<? super D> consumer, boolean z) {
        return onAssembly(new FluxUsing(callable, function, consumer, z));
    }

    public static <T, D extends AutoCloseable> Flux<T> using(Callable<? extends D> callable, Function<? super D, ? extends Publisher<? extends T>> function) {
        return using((Callable) callable, (Function) function, true);
    }

    public static <T, D extends AutoCloseable> Flux<T> using(Callable<? extends D> callable, Function<? super D, ? extends Publisher<? extends T>> function, boolean z) {
        return using(callable, function, Exceptions.AUTO_CLOSE, z);
    }

    static /* synthetic */ Publisher lambda$usingWhen$4(Function function, Object obj, Throwable th) {
        return (Publisher) function.apply(obj);
    }

    public static <T, D> Flux<T> usingWhen(Publisher<D> publisher, Function<? super D, ? extends Publisher<? extends T>> function, final Function<? super D, ? extends Publisher<?>> function2) {
        return usingWhen(publisher, function, function2, new BiFunction() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda40
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Flux.lambda$usingWhen$4(function2, obj, (Throwable) obj2);
            }
        }, function2);
    }

    public static <T, D> Flux<T> usingWhen(Publisher<D> publisher, Function<? super D, ? extends Publisher<? extends T>> function, Function<? super D, ? extends Publisher<?>> function2, BiFunction<? super D, ? super Throwable, ? extends Publisher<?>> biFunction, Function<? super D, ? extends Publisher<?>> function3) {
        return onAssembly(new FluxUsingWhen(publisher, function, function2, biFunction, function3));
    }

    public static <T1, T2, O> Flux<O> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, BiFunction<? super T1, ? super T2, ? extends O> biFunction) {
        return onAssembly(new FluxZip(publisher, publisher2, biFunction, Queues.m1980xs(), Queues.XS_BUFFER_SIZE));
    }

    public static <T1, T2> Flux<Tuple2<T1, T2>> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2) {
        return zip(publisher, publisher2, tuple2Function());
    }

    public static <T1, T2, T3> Flux<Tuple3<T1, T2, T3>> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3) {
        return zip(Tuples.fn3(), publisher, publisher2, publisher3);
    }

    public static <T1, T2, T3, T4> Flux<Tuple4<T1, T2, T3, T4>> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4) {
        return zip(Tuples.fn4(), publisher, publisher2, publisher3, publisher4);
    }

    public static <T1, T2, T3, T4, T5> Flux<Tuple5<T1, T2, T3, T4, T5>> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5) {
        return zip(Tuples.fn5(), publisher, publisher2, publisher3, publisher4, publisher5);
    }

    public static <T1, T2, T3, T4, T5, T6> Flux<Tuple6<T1, T2, T3, T4, T5, T6>> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Publisher<? extends T6> publisher6) {
        return zip(Tuples.fn6(), publisher, publisher2, publisher3, publisher4, publisher5, publisher6);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Flux<Tuple7<T1, T2, T3, T4, T5, T6, T7>> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Publisher<? extends T6> publisher6, Publisher<? extends T7> publisher7) {
        return zip(Tuples.fn7(), publisher, publisher2, publisher3, publisher4, publisher5, publisher6, publisher7);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Flux<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> zip(Publisher<? extends T1> publisher, Publisher<? extends T2> publisher2, Publisher<? extends T3> publisher3, Publisher<? extends T4> publisher4, Publisher<? extends T5> publisher5, Publisher<? extends T6> publisher6, Publisher<? extends T7> publisher7, Publisher<? extends T8> publisher8) {
        return zip(Tuples.fn8(), publisher, publisher2, publisher3, publisher4, publisher5, publisher6, publisher7, publisher8);
    }

    public static <O> Flux<O> zip(Iterable<? extends Publisher<?>> iterable, Function<? super Object[], ? extends O> function) {
        return zip(iterable, Queues.XS_BUFFER_SIZE, function);
    }

    public static <O> Flux<O> zip(Iterable<? extends Publisher<?>> iterable, int i, Function<? super Object[], ? extends O> function) {
        return onAssembly(new FluxZip(iterable, function, Queues.get(i), i));
    }

    @SafeVarargs
    public static <I, O> Flux<O> zip(Function<? super Object[], ? extends O> function, Publisher<? extends I>... publisherArr) {
        return zip(function, Queues.XS_BUFFER_SIZE, publisherArr);
    }

    @SafeVarargs
    public static <I, O> Flux<O> zip(final Function<? super Object[], ? extends O> function, int i, Publisher<? extends I>... publisherArr) {
        if (publisherArr.length == 0) {
            return empty();
        }
        if (publisherArr.length == 1) {
            Publisher<? extends I> publisher = publisherArr[0];
            if (publisher instanceof Fuseable) {
                return onAssembly(new FluxMapFuseable(from(publisher), new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda29
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return function.apply(new Object[]{obj});
                    }
                }));
            }
            return onAssembly(new FluxMap(from(publisher), new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda30
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return function.apply(new Object[]{obj});
                }
            }));
        }
        return onAssembly(new FluxZip(publisherArr, function, Queues.get(i), i));
    }

    public static <TUPLE extends Tuple2, V> Flux<V> zip(Publisher<? extends Publisher<?>> publisher, final Function<? super TUPLE, ? extends V> function) {
        return onAssembly(new FluxBuffer(from(publisher), Integer.MAX_VALUE, listSupplier()).flatMap(new Function<List<? extends Publisher<?>>, Publisher<V>>() { // from class: reactor.core.publisher.Flux.1
            @Override // java.util.function.Function
            public Publisher<V> apply(List<? extends Publisher<?>> list) {
                return Flux.zip(Tuples.fnAny(function), (Publisher[]) list.toArray(new Publisher[list.size()]));
            }
        }));
    }

    public final Mono<Boolean> all(Predicate<? super T> predicate) {
        return Mono.onAssembly(new MonoAll(this, predicate));
    }

    public final Mono<Boolean> any(Predicate<? super T> predicate) {
        return Mono.onAssembly(new MonoAny(this, predicate));
    }

    /* JADX INFO: renamed from: as */
    public final <P> P m1957as(Function<? super Flux<T>, P> function) {
        return function.apply(this);
    }

    @Nullable
    public final T blockFirst() {
        BlockingFirstSubscriber blockingFirstSubscriber = new BlockingFirstSubscriber(ContextPropagationSupport.shouldPropagateContextToThreadLocals() ? ContextPropagation.contextCaptureToEmpty() : Context.empty());
        subscribe((Subscriber) blockingFirstSubscriber);
        return blockingFirstSubscriber.blockingGet();
    }

    @Nullable
    public final T blockFirst(Duration duration) {
        BlockingFirstSubscriber blockingFirstSubscriber = new BlockingFirstSubscriber(ContextPropagationSupport.shouldPropagateContextToThreadLocals() ? ContextPropagation.contextCaptureToEmpty() : Context.empty());
        subscribe((Subscriber) blockingFirstSubscriber);
        return blockingFirstSubscriber.blockingGet(duration.toNanos(), TimeUnit.NANOSECONDS);
    }

    @Nullable
    public final T blockLast() {
        BlockingLastSubscriber blockingLastSubscriber = new BlockingLastSubscriber(ContextPropagationSupport.shouldPropagateContextToThreadLocals() ? ContextPropagation.contextCaptureToEmpty() : Context.empty());
        subscribe((Subscriber) blockingLastSubscriber);
        return blockingLastSubscriber.blockingGet();
    }

    @Nullable
    public final T blockLast(Duration duration) {
        BlockingLastSubscriber blockingLastSubscriber = new BlockingLastSubscriber(ContextPropagationSupport.shouldPropagateContextToThreadLocals() ? ContextPropagation.contextCaptureToEmpty() : Context.empty());
        subscribe((Subscriber) blockingLastSubscriber);
        return blockingLastSubscriber.blockingGet(duration.toNanos(), TimeUnit.NANOSECONDS);
    }

    public final Flux<List<T>> buffer() {
        return buffer(Integer.MAX_VALUE);
    }

    public final Flux<List<T>> buffer(int i) {
        return (Flux<List<T>>) buffer(i, listSupplier());
    }

    public final <C extends Collection<? super T>> Flux<C> buffer(int i, Supplier<C> supplier) {
        return onAssembly(new FluxBuffer(this, i, supplier));
    }

    public final Flux<List<T>> buffer(int i, int i2) {
        return (Flux<List<T>>) buffer(i, i2, listSupplier());
    }

    public final <C extends Collection<? super T>> Flux<C> buffer(int i, int i2, Supplier<C> supplier) {
        return onAssembly(new FluxBuffer(this, i, i2, supplier));
    }

    public final Flux<List<T>> buffer(Publisher<?> publisher) {
        return (Flux<List<T>>) buffer(publisher, listSupplier());
    }

    public final <C extends Collection<? super T>> Flux<C> buffer(Publisher<?> publisher, Supplier<C> supplier) {
        return onAssembly(new FluxBufferBoundary(this, publisher, supplier));
    }

    public final Flux<List<T>> buffer(Duration duration) {
        return buffer(duration, Schedulers.parallel());
    }

    public final Flux<List<T>> buffer(Duration duration, Duration duration2) {
        return buffer(duration, duration2, Schedulers.parallel());
    }

    public final Flux<List<T>> buffer(Duration duration, Scheduler scheduler) {
        return buffer(interval(duration, scheduler));
    }

    public final Flux<List<T>> buffer(final Duration duration, Duration duration2, final Scheduler scheduler) {
        if (duration.equals(duration2)) {
            return buffer(duration, scheduler);
        }
        return bufferWhen(interval(Duration.ZERO, duration2, scheduler), new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mono.delay(duration, scheduler);
            }
        });
    }

    public final Flux<List<T>> bufferTimeout(int i, Duration duration) {
        return (Flux<List<T>>) bufferTimeout(i, duration, listSupplier());
    }

    public final <C extends Collection<? super T>> Flux<C> bufferTimeout(int i, Duration duration, Supplier<C> supplier) {
        return bufferTimeout(i, duration, Schedulers.parallel(), supplier);
    }

    public final Flux<List<T>> bufferTimeout(int i, Duration duration, Scheduler scheduler) {
        return (Flux<List<T>>) bufferTimeout(i, duration, scheduler, listSupplier());
    }

    public final <C extends Collection<? super T>> Flux<C> bufferTimeout(int i, Duration duration, Scheduler scheduler, Supplier<C> supplier) {
        return onAssembly(new FluxBufferTimeout(this, i, duration.toNanos(), TimeUnit.NANOSECONDS, scheduler, supplier, false));
    }

    public final Flux<List<T>> bufferTimeout(int i, Duration duration, boolean z) {
        return (Flux<List<T>>) bufferTimeout(i, duration, Schedulers.parallel(), listSupplier(), z);
    }

    public final Flux<List<T>> bufferTimeout(int i, Duration duration, Scheduler scheduler, boolean z) {
        return (Flux<List<T>>) bufferTimeout(i, duration, scheduler, listSupplier(), z);
    }

    public final <C extends Collection<? super T>> Flux<C> bufferTimeout(int i, Duration duration, Supplier<C> supplier, boolean z) {
        return bufferTimeout(i, duration, Schedulers.parallel(), supplier, z);
    }

    public final <C extends Collection<? super T>> Flux<C> bufferTimeout(int i, Duration duration, Scheduler scheduler, Supplier<C> supplier, boolean z) {
        return onAssembly(new FluxBufferTimeout(this, i, duration.toNanos(), TimeUnit.NANOSECONDS, scheduler, supplier, z));
    }

    public final Flux<List<T>> bufferUntil(Predicate<? super T> predicate) {
        return onAssembly(new FluxBufferPredicate(this, predicate, listSupplier(), FluxBufferPredicate.Mode.UNTIL));
    }

    public final Flux<List<T>> bufferUntil(Predicate<? super T> predicate, boolean z) {
        FluxBufferPredicate.Mode mode;
        Supplier supplierListSupplier = listSupplier();
        if (z) {
            mode = FluxBufferPredicate.Mode.UNTIL_CUT_BEFORE;
        } else {
            mode = FluxBufferPredicate.Mode.UNTIL;
        }
        return onAssembly(new FluxBufferPredicate(this, predicate, supplierListSupplier, mode));
    }

    public final Flux<List<T>> bufferUntilChanged() {
        return bufferUntilChanged(identityFunction());
    }

    public final <V> Flux<List<T>> bufferUntilChanged(Function<? super T, ? extends V> function) {
        return bufferUntilChanged(function, equalPredicate());
    }

    public final <V> Flux<List<T>> bufferUntilChanged(final Function<? super T, ? extends V> function, final BiPredicate<? super V, ? super V> biPredicate) {
        return defer(new Supplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda15
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m4279lambda$bufferUntilChanged$8$reactorcorepublisherFlux(function, biPredicate);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$bufferUntilChanged$8$reactor-core-publisher-Flux, reason: not valid java name */
    /* synthetic */ Publisher m4279lambda$bufferUntilChanged$8$reactorcorepublisherFlux(Function function, BiPredicate biPredicate) {
        return bufferUntil(new FluxBufferPredicate.ChangedPredicate(function, biPredicate), true);
    }

    public final Flux<List<T>> bufferWhile(Predicate<? super T> predicate) {
        return onAssembly(new FluxBufferPredicate(this, predicate, listSupplier(), FluxBufferPredicate.Mode.WHILE));
    }

    public final <U, V> Flux<List<T>> bufferWhen(Publisher<U> publisher, Function<? super U, ? extends Publisher<V>> function) {
        return (Flux<List<T>>) bufferWhen(publisher, function, listSupplier());
    }

    public final <U, V, C extends Collection<? super T>> Flux<C> bufferWhen(Publisher<U> publisher, Function<? super U, ? extends Publisher<V>> function, Supplier<C> supplier) {
        return onAssembly(new FluxBufferWhen(this, publisher, function, supplier, Queues.unbounded(Queues.XS_BUFFER_SIZE)));
    }

    public final Flux<T> cache() {
        return cache(Integer.MAX_VALUE);
    }

    public final Flux<T> cache(int i) {
        return replay(i).autoConnect();
    }

    public final Flux<T> cache(Duration duration) {
        return cache(duration, Schedulers.parallel());
    }

    public final Flux<T> cache(Duration duration, Scheduler scheduler) {
        return cache(Integer.MAX_VALUE, duration, scheduler);
    }

    public final Flux<T> cache(int i, Duration duration) {
        return cache(i, duration, Schedulers.parallel());
    }

    public final Flux<T> cache(int i, Duration duration, Scheduler scheduler) {
        return replay(i, duration, scheduler).autoConnect();
    }

    public final <E> Flux<E> cast(final Class<E> cls) {
        Objects.requireNonNull(cls, "clazz");
        Objects.requireNonNull(cls);
        return (Flux<E>) map(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return cls.cast(obj);
            }
        });
    }

    public final Flux<T> cancelOn(Scheduler scheduler) {
        return onAssembly(new FluxCancelOn(this, scheduler));
    }

    public final Flux<T> checkpoint() {
        return checkpoint(null, true);
    }

    public final Flux<T> checkpoint(String str) {
        return checkpoint((String) Objects.requireNonNull(str), false);
    }

    public final Flux<T> checkpoint(@Nullable String str, boolean z) {
        FluxOnAssembly.AssemblySnapshot checkpointHeavySnapshot;
        if (!z) {
            checkpointHeavySnapshot = new FluxOnAssembly.CheckpointLightSnapshot(str);
        } else {
            checkpointHeavySnapshot = new FluxOnAssembly.CheckpointHeavySnapshot(str, Traces.callSiteSupplierFactory.get());
        }
        return new FluxOnAssembly(this, checkpointHeavySnapshot);
    }

    public final <E> Mono<E> collect(Supplier<E> supplier, BiConsumer<E, ? super T> biConsumer) {
        return Mono.onAssembly(new MonoCollect(this, supplier, biConsumer));
    }

    public final <R, A> Mono<R> collect(Collector<? super T, A, ? extends R> collector) {
        return Mono.onAssembly(new MonoStreamCollector(this, collector));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Mono<List<T>> collectList() {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                try {
                    final T tCall = ((Fuseable.ScalarCallable) this).call();
                    return Mono.onAssembly(new MonoCallable(new Callable() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda27
                        @Override // java.util.concurrent.Callable
                        public final Object call() {
                            return Flux.lambda$collectList$9(tCall);
                        }
                    }));
                } catch (Exception e) {
                    return Mono.error(Exceptions.unwrap(e));
                }
            }
            final Callable callable = (Callable) this;
            return Mono.onAssembly(new MonoCallable(new Callable() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda28
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return Flux.lambda$collectList$10(callable);
                }
            }));
        }
        return Mono.onAssembly(new MonoCollectList(this));
    }

    static /* synthetic */ List lambda$collectList$9(Object obj) throws Exception {
        List list = (List) listSupplier().get();
        if (obj != null) {
            list.add(obj);
        }
        return list;
    }

    static /* synthetic */ List lambda$collectList$10(Callable callable) throws Exception {
        List list = (List) listSupplier().get();
        Object objCall = callable.call();
        if (objCall != null) {
            list.add(objCall);
        }
        return list;
    }

    public final <K> Mono<Map<K, T>> collectMap(Function<? super T, ? extends K> function) {
        return (Mono<Map<K, T>>) collectMap(function, identityFunction());
    }

    static /* synthetic */ Map lambda$collectMap$11() {
        return new HashMap();
    }

    public final <K, V> Mono<Map<K, V>> collectMap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return collectMap(function, function2, new Supplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda53
            @Override // java.util.function.Supplier
            public final Object get() {
                return Flux.lambda$collectMap$11();
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <K, V> Mono<Map<K, V>> collectMap(final Function<? super T, ? extends K> function, final Function<? super T, ? extends V> function2, Supplier<Map<K, V>> supplier) {
        Objects.requireNonNull(function, "Key extractor is null");
        Objects.requireNonNull(function2, "Value extractor is null");
        Objects.requireNonNull(supplier, "Map supplier is null");
        return (Mono<Map<K, V>>) collect(supplier, new BiConsumer() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda38
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((Map) obj).put(function.apply(obj2), function2.apply(obj2));
            }
        });
    }

    public final <K> Mono<Map<K, Collection<T>>> collectMultimap(Function<? super T, ? extends K> function) {
        return (Mono<Map<K, Collection<T>>>) collectMultimap(function, identityFunction());
    }

    static /* synthetic */ Map lambda$collectMultimap$13() {
        return new HashMap();
    }

    public final <K, V> Mono<Map<K, Collection<V>>> collectMultimap(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return collectMultimap(function, function2, new Supplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda56
            @Override // java.util.function.Supplier
            public final Object get() {
                return Flux.lambda$collectMultimap$13();
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <K, V> Mono<Map<K, Collection<V>>> collectMultimap(final Function<? super T, ? extends K> function, final Function<? super T, ? extends V> function2, Supplier<Map<K, Collection<V>>> supplier) {
        Objects.requireNonNull(function, "Key extractor is null");
        Objects.requireNonNull(function2, "Value extractor is null");
        Objects.requireNonNull(supplier, "Map supplier is null");
        return (Mono<Map<K, Collection<V>>>) collect(supplier, new BiConsumer() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda42
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((Collection) ((Map) obj).computeIfAbsent(function.apply(obj2), new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda48
                    @Override // java.util.function.Function
                    public final Object apply(Object obj3) {
                        return Flux.lambda$collectMultimap$14(obj3);
                    }
                })).add(function2.apply(obj2));
            }
        });
    }

    static /* synthetic */ Collection lambda$collectMultimap$14(Object obj) {
        return new ArrayList();
    }

    public final Mono<List<T>> collectSortedList() {
        return collectSortedList(null);
    }

    public final Mono<List<T>> collectSortedList(@Nullable final Comparator<? super T> comparator) {
        return collectList().doOnNext(new Consumer() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((List) obj).sort(comparator);
            }
        });
    }

    public final <V> Flux<V> concatMap(Function<? super T, ? extends Publisher<? extends V>> function) {
        return onAssembly(new FluxConcatMapNoPrefetch(this, function, FluxConcatMap.ErrorMode.IMMEDIATE));
    }

    public final <V> Flux<V> concatMap(Function<? super T, ? extends Publisher<? extends V>> function, int i) {
        if (i == 0) {
            return onAssembly(new FluxConcatMapNoPrefetch(this, function, FluxConcatMap.ErrorMode.IMMEDIATE));
        }
        return onAssembly(new FluxConcatMap(this, function, Queues.get(i), i, FluxConcatMap.ErrorMode.IMMEDIATE));
    }

    public final <V> Flux<V> concatMapDelayError(Function<? super T, ? extends Publisher<? extends V>> function) {
        return concatMapDelayError(function, 0);
    }

    public final <V> Flux<V> concatMapDelayError(Function<? super T, ? extends Publisher<? extends V>> function, int i) {
        return concatMapDelayError(function, true, i);
    }

    public final <V> Flux<V> concatMapDelayError(Function<? super T, ? extends Publisher<? extends V>> function, boolean z, int i) {
        FluxConcatMap.ErrorMode errorMode = z ? FluxConcatMap.ErrorMode.END : FluxConcatMap.ErrorMode.BOUNDARY;
        if (i == 0) {
            return onAssembly(new FluxConcatMapNoPrefetch(this, function, errorMode));
        }
        return onAssembly(new FluxConcatMap(this, function, Queues.get(i), i, errorMode));
    }

    public final <R> Flux<R> concatMapIterable(Function<? super T, ? extends Iterable<? extends R>> function) {
        return concatMapIterable(function, Queues.XS_BUFFER_SIZE);
    }

    public final <R> Flux<R> concatMapIterable(Function<? super T, ? extends Iterable<? extends R>> function, int i) {
        return onAssembly(new FluxFlattenIterable(this, function, i, Queues.get(i)));
    }

    public final Flux<T> concatWith(Publisher<? extends T> publisher) {
        return this instanceof FluxConcatArray ? ((FluxConcatArray) this).concatAdditionalSourceLast(publisher) : concat(this, publisher);
    }

    public final Flux<T> contextCapture() {
        if (!ContextPropagationSupport.isContextPropagationAvailable()) {
            return this;
        }
        if (ContextPropagationSupport.propagateContextToThreadLocals) {
            return onAssembly(new FluxContextWriteRestoringThreadLocals(this, ContextPropagation.contextCapture()));
        }
        return onAssembly(new FluxContextWrite(this, ContextPropagation.contextCapture()));
    }

    public final Flux<T> contextWrite(final ContextView contextView) {
        return contextWrite(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Context) obj).putAll(contextView);
            }
        });
    }

    public final Flux<T> contextWrite(Function<Context, Context> function) {
        if (ContextPropagationSupport.shouldPropagateContextToThreadLocals()) {
            return onAssembly(new FluxContextWriteRestoringThreadLocals(this, function));
        }
        return onAssembly(new FluxContextWrite(this, function));
    }

    private final Flux<T> contextWriteSkippingContextPropagation(final ContextView contextView) {
        return contextWriteSkippingContextPropagation(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Context) obj).putAll(contextView);
            }
        });
    }

    private final Flux<T> contextWriteSkippingContextPropagation(Function<Context, Context> function) {
        return onAssembly(new FluxContextWrite(this, function));
    }

    public final Mono<Long> count() {
        return Mono.onAssembly(new MonoCount(this));
    }

    public final Flux<T> defaultIfEmpty(T t) {
        return onAssembly(new FluxDefaultIfEmpty(this, t));
    }

    public final Flux<T> delayElements(Duration duration) {
        return delayElements(duration, Schedulers.parallel());
    }

    public final Flux<T> delayElements(final Duration duration, final Scheduler scheduler) {
        return delayUntil(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda54
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mono.delay(duration, scheduler);
            }
        });
    }

    public final Flux<T> delaySequence(Duration duration) {
        return delaySequence(duration, Schedulers.parallel());
    }

    public final Flux<T> delaySequence(Duration duration, Scheduler scheduler) {
        return onAssembly(new FluxDelaySequence(this, duration, scheduler));
    }

    public final Flux<T> delayUntil(final Function<? super T, ? extends Publisher<?>> function) {
        return (Flux<T>) concatMap(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mono.just(obj).delayUntil(function);
            }
        });
    }

    public final Flux<T> delaySubscription(Duration duration) {
        return delaySubscription(duration, Schedulers.parallel());
    }

    public final Flux<T> delaySubscription(Duration duration, Scheduler scheduler) {
        return delaySubscription(Mono.delay(duration, scheduler));
    }

    public final <U> Flux<T> delaySubscription(Publisher<U> publisher) {
        return onAssembly(new FluxDelaySubscription(this, publisher));
    }

    public final <X> Flux<X> dematerialize() {
        return onAssembly(new FluxDematerialize(this));
    }

    public final Flux<T> distinct() {
        return distinct(identityFunction());
    }

    public final <V> Flux<T> distinct(Function<? super T, ? extends V> function) {
        return distinct(function, hashSetSupplier());
    }

    public final <V, C extends Collection<? super V>> Flux<T> distinct(Function<? super T, ? extends V> function, Supplier<C> supplier) {
        return distinct(function, supplier, new BiPredicate() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda32
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return ((Collection) obj).add(obj2);
            }
        }, new Consumer() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda34
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Collection) obj).clear();
            }
        });
    }

    public final <V, C> Flux<T> distinct(Function<? super T, ? extends V> function, Supplier<C> supplier, BiPredicate<C, V> biPredicate, Consumer<C> consumer) {
        if (this instanceof Fuseable) {
            return onAssembly(new FluxDistinctFuseable(this, function, supplier, biPredicate, consumer));
        }
        return onAssembly(new FluxDistinct(this, function, supplier, biPredicate, consumer));
    }

    public final Flux<T> distinctUntilChanged() {
        return distinctUntilChanged(identityFunction());
    }

    public final <V> Flux<T> distinctUntilChanged(Function<? super T, ? extends V> function) {
        return distinctUntilChanged(function, equalPredicate());
    }

    public final <V> Flux<T> distinctUntilChanged(Function<? super T, ? extends V> function, BiPredicate<? super V, ? super V> biPredicate) {
        return onAssembly(new FluxDistinctUntilChanged(this, function, biPredicate));
    }

    public final Flux<T> doAfterTerminate(Runnable runnable) {
        Objects.requireNonNull(runnable, "afterTerminate");
        return doOnSignal(this, null, null, null, null, runnable, null, null);
    }

    public final Flux<T> doOnCancel(Runnable runnable) {
        Objects.requireNonNull(runnable, "onCancel");
        return doOnSignal(this, null, null, null, null, null, null, runnable);
    }

    public final Flux<T> doOnComplete(Runnable runnable) {
        Objects.requireNonNull(runnable, "onComplete");
        return doOnSignal(this, null, null, null, runnable, null, null, null);
    }

    public final <R> Flux<T> doOnDiscard(Class<R> cls, Consumer<? super R> consumer) {
        return contextWriteSkippingContextPropagation(Operators.discardLocalAdapter(cls, consumer));
    }

    public final Flux<T> doOnEach(Consumer<? super Signal<T>> consumer) {
        if (this instanceof Fuseable) {
            return onAssembly(new FluxDoOnEachFuseable(this, consumer));
        }
        return onAssembly(new FluxDoOnEach(this, consumer));
    }

    public final Flux<T> doOnError(Consumer<? super Throwable> consumer) {
        Objects.requireNonNull(consumer, "onError");
        return doOnSignal(this, null, null, consumer, null, null, null, null);
    }

    public final <E extends Throwable> Flux<T> doOnError(Class<E> cls, Consumer<? super E> consumer) {
        Objects.requireNonNull(cls, TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE);
        Objects.requireNonNull(cls);
        return doOnError(new Flux$$ExternalSyntheticLambda1(cls), consumer);
    }

    public final Flux<T> doOnError(final Predicate<? super Throwable> predicate, final Consumer<? super Throwable> consumer) {
        Objects.requireNonNull(predicate, "predicate");
        return doOnError(new Consumer() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda39
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Flux.lambda$doOnError$21(predicate, consumer, (Throwable) obj);
            }
        });
    }

    static /* synthetic */ void lambda$doOnError$21(Predicate predicate, Consumer consumer, Throwable th) {
        if (predicate.test(th)) {
            consumer.accept(th);
        }
    }

    public final Flux<T> doOnNext(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "onNext");
        return doOnSignal(this, null, consumer, null, null, null, null, null);
    }

    public final Flux<T> doOnRequest(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer, "consumer");
        return doOnSignal(this, null, null, null, null, null, longConsumer, null);
    }

    public final Flux<T> doOnSubscribe(Consumer<? super Subscription> consumer) {
        Objects.requireNonNull(consumer, "onSubscribe");
        return doOnSignal(this, consumer, null, null, null, null, null, null);
    }

    public final Flux<T> doOnTerminate(final Runnable runnable) {
        Objects.requireNonNull(runnable, "onTerminate");
        return doOnSignal(this, null, null, new Consumer() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                runnable.run();
            }
        }, runnable, null, null, null);
    }

    public final Flux<T> doFirst(Runnable runnable) {
        Objects.requireNonNull(runnable, "onFirst");
        if (this instanceof Fuseable) {
            return onAssembly(new FluxDoFirstFuseable(this, runnable));
        }
        return onAssembly(new FluxDoFirst(this, runnable));
    }

    public final Flux<T> doFinally(Consumer<SignalType> consumer) {
        Objects.requireNonNull(consumer, "onFinally");
        return onAssembly(new FluxDoFinally(this, consumer));
    }

    public final Flux<Tuple2<Long, T>> elapsed() {
        return elapsed(Schedulers.parallel());
    }

    public final Flux<Tuple2<Long, T>> elapsed(Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler");
        return onAssembly(new FluxElapsed(this, scheduler));
    }

    public final Mono<T> elementAt(int i) {
        return Mono.onAssembly(new MonoElementAt(this, i));
    }

    public final Mono<T> elementAt(int i, T t) {
        return Mono.onAssembly(new MonoElementAt(this, i, t));
    }

    public final Flux<T> expandDeep(Function<? super T, ? extends Publisher<? extends T>> function, int i) {
        return onAssembly(new FluxExpand(this, function, false, i));
    }

    public final Flux<T> expandDeep(Function<? super T, ? extends Publisher<? extends T>> function) {
        return expandDeep(function, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> expand(Function<? super T, ? extends Publisher<? extends T>> function, int i) {
        return onAssembly(new FluxExpand(this, function, true, i));
    }

    public final Flux<T> expand(Function<? super T, ? extends Publisher<? extends T>> function) {
        return expand(function, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> filter(Predicate<? super T> predicate) {
        if (this instanceof Fuseable) {
            return onAssembly(new FluxFilterFuseable(this, predicate));
        }
        return onAssembly(new FluxFilter(this, predicate));
    }

    public final Flux<T> filterWhen(Function<? super T, ? extends Publisher<Boolean>> function) {
        return filterWhen(function, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> filterWhen(Function<? super T, ? extends Publisher<Boolean>> function, int i) {
        return onAssembly(new FluxFilterWhen(this, function, i));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <R> Flux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function) {
        return (Flux<R>) flatMap(function, Queues.SMALL_BUFFER_SIZE, Queues.XS_BUFFER_SIZE);
    }

    public final <V> Flux<V> flatMap(Function<? super T, ? extends Publisher<? extends V>> function, int i) {
        return flatMap(function, i, Queues.XS_BUFFER_SIZE);
    }

    public final <V> Flux<V> flatMap(Function<? super T, ? extends Publisher<? extends V>> function, int i, int i2) {
        return flatMap(function, false, i, i2);
    }

    public final <V> Flux<V> flatMapDelayError(Function<? super T, ? extends Publisher<? extends V>> function, int i, int i2) {
        return flatMap(function, true, i, i2);
    }

    public final <R> Flux<R> flatMap(@Nullable Function<? super T, ? extends Publisher<? extends R>> function, @Nullable Function<? super Throwable, ? extends Publisher<? extends R>> function2, @Nullable Supplier<? extends Publisher<? extends R>> supplier) {
        return onAssembly(new FluxFlatMap(new FluxMapSignal(this, function, function2, supplier), identityFunction(), false, Queues.XS_BUFFER_SIZE, Queues.m1980xs(), Queues.XS_BUFFER_SIZE, Queues.m1980xs()));
    }

    public final <R> Flux<R> flatMapIterable(Function<? super T, ? extends Iterable<? extends R>> function) {
        return flatMapIterable(function, Queues.SMALL_BUFFER_SIZE);
    }

    public final <R> Flux<R> flatMapIterable(Function<? super T, ? extends Iterable<? extends R>> function, int i) {
        return onAssembly(new FluxFlattenIterable(this, function, i, Queues.get(i)));
    }

    public final <R> Flux<R> flatMapSequential(Function<? super T, ? extends Publisher<? extends R>> function) {
        return flatMapSequential(function, Queues.SMALL_BUFFER_SIZE);
    }

    public final <R> Flux<R> flatMapSequential(Function<? super T, ? extends Publisher<? extends R>> function, int i) {
        return flatMapSequential(function, i, Queues.XS_BUFFER_SIZE);
    }

    public final <R> Flux<R> flatMapSequential(Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2) {
        return flatMapSequential(function, false, i, i2);
    }

    public final <R> Flux<R> flatMapSequentialDelayError(Function<? super T, ? extends Publisher<? extends R>> function, int i, int i2) {
        return flatMapSequential(function, true, i, i2);
    }

    public final <K> Flux<GroupedFlux<K, T>> groupBy(Function<? super T, ? extends K> function) {
        return (Flux<GroupedFlux<K, T>>) groupBy(function, identityFunction());
    }

    public final <K> Flux<GroupedFlux<K, T>> groupBy(Function<? super T, ? extends K> function, int i) {
        return (Flux<GroupedFlux<K, T>>) groupBy(function, identityFunction(), i);
    }

    public final <K, V> Flux<GroupedFlux<K, V>> groupBy(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return groupBy(function, function2, Queues.SMALL_BUFFER_SIZE);
    }

    public final <K, V> Flux<GroupedFlux<K, V>> groupBy(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, int i) {
        return onAssembly(new FluxGroupBy(this, function, function2, Queues.unbounded(i), Queues.unbounded(i), i));
    }

    public final <TRight, TLeftEnd, TRightEnd, R> Flux<R> groupJoin(Publisher<? extends TRight> publisher, Function<? super T, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super T, ? super Flux<TRight>, ? extends R> biFunction) {
        return onAssembly(new FluxGroupJoin(this, publisher, function, function2, biFunction, Queues.unbounded(Queues.XS_BUFFER_SIZE), Queues.unbounded(Queues.XS_BUFFER_SIZE)));
    }

    public final <R> Flux<R> handle(BiConsumer<? super T, SynchronousSink<R>> biConsumer) {
        if (this instanceof Fuseable) {
            return onAssembly(new FluxHandleFuseable(this, biConsumer));
        }
        return onAssembly(new FluxHandle(this, biConsumer));
    }

    public final Mono<Boolean> hasElement(final T t) {
        Objects.requireNonNull(t, "value");
        return any(new Predicate() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.equals(t, obj);
            }
        });
    }

    public final Mono<Boolean> hasElements() {
        return Mono.onAssembly(new MonoHasElements(this));
    }

    public Flux<T> hide() {
        return new FluxHide(this);
    }

    public final Flux<Tuple2<Long, T>> index() {
        return (Flux<Tuple2<Long, T>>) index(tuple2Function());
    }

    public final <I> Flux<I> index(BiFunction<? super Long, ? super T, ? extends I> biFunction) {
        if (this instanceof Fuseable) {
            return onAssembly(new FluxIndexFuseable(this, biFunction));
        }
        return onAssembly(new FluxIndex(this, biFunction));
    }

    public final Mono<T> ignoreElements() {
        return Mono.onAssembly(new MonoIgnoreElements(this));
    }

    public final <TRight, TLeftEnd, TRightEnd, R> Flux<R> join(Publisher<? extends TRight> publisher, Function<? super T, ? extends Publisher<TLeftEnd>> function, Function<? super TRight, ? extends Publisher<TRightEnd>> function2, BiFunction<? super T, ? super TRight, ? extends R> biFunction) {
        return onAssembly(new FluxJoin(this, publisher, function, function2, biFunction));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Mono<T> last() {
        if (this instanceof Callable) {
            Mono monoWrapToMono = wrapToMono((Callable) this);
            if (monoWrapToMono == Mono.empty()) {
                return Mono.onAssembly(new MonoError(new NoSuchElementException("Flux#last() didn't observe any onNext signal from Callable flux")));
            }
            return Mono.onAssembly(monoWrapToMono);
        }
        return Mono.onAssembly(new MonoTakeLastOne(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Mono<T> last(T t) {
        if (this instanceof Callable) {
            Callable callable = (Callable) this;
            if (callable instanceof Fuseable.ScalarCallable) {
                try {
                    T tCall = ((Fuseable.ScalarCallable) callable).call();
                    if (tCall == null) {
                        return Mono.just(t);
                    }
                    return Mono.just(tCall);
                } catch (Exception e) {
                    return Mono.error(Exceptions.unwrap(e));
                }
            }
            Mono.onAssembly(new MonoCallable(callable));
        }
        return Mono.onAssembly(new MonoTakeLastOne(this, t));
    }

    public final Flux<T> limitRate(int i) {
        return onAssembly(publishOn(Schedulers.immediate(), i));
    }

    public final Flux<T> limitRate(int i, int i2) {
        return onAssembly(publishOn(Schedulers.immediate(), true, i, i2));
    }

    @Deprecated
    public final Flux<T> limitRequest(long j) {
        return take(j, true);
    }

    public final Flux<T> log() {
        return log(null, Level.INFO, new SignalType[0]);
    }

    public final Flux<T> log(String str) {
        return log(str, Level.INFO, new SignalType[0]);
    }

    public final Flux<T> log(@Nullable String str, Level level, SignalType... signalTypeArr) {
        return log(str, level, false, signalTypeArr);
    }

    public final Flux<T> log(@Nullable String str, Level level, boolean z, SignalType... signalTypeArr) {
        SignalLogger signalLogger = new SignalLogger(this, str, level, z, signalTypeArr);
        if (this instanceof Fuseable) {
            return onAssembly(new FluxLogFuseable(this, signalLogger));
        }
        return onAssembly(new FluxLog(this, signalLogger));
    }

    public final Flux<T> log(Logger logger) {
        return log(logger, Level.INFO, false, new SignalType[0]);
    }

    public final Flux<T> log(final Logger logger, Level level, boolean z, SignalType... signalTypeArr) {
        SignalLogger signalLogger = new SignalLogger(this, "IGNORED", level, z, new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Flux.lambda$log$24(logger, (String) obj);
            }
        }, signalTypeArr);
        if (this instanceof Fuseable) {
            return onAssembly(new FluxLogFuseable(this, signalLogger));
        }
        return onAssembly(new FluxLog(this, signalLogger));
    }

    public final <V> Flux<V> map(Function<? super T, ? extends V> function) {
        if (this instanceof Fuseable) {
            return onAssembly(new FluxMapFuseable(this, function));
        }
        return onAssembly(new FluxMap(this, function));
    }

    public final <V> Flux<V> mapNotNull(final Function<? super T, ? extends V> function) {
        return (Flux<V>) handle(new BiConsumer() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda47
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Flux.lambda$mapNotNull$25(function, obj, (SynchronousSink) obj2);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$mapNotNull$25(Function function, Object obj, SynchronousSink synchronousSink) {
        Object objApply = function.apply(obj);
        if (objApply != null) {
            synchronousSink.next(objApply);
        }
    }

    public final Flux<Signal<T>> materialize() {
        return onAssembly(new FluxMaterialize(this));
    }

    @Deprecated
    public final Flux<T> mergeOrderedWith(Publisher<? extends T> publisher, Comparator<? super T> comparator) {
        return this instanceof FluxMergeComparing ? ((FluxMergeComparing) this).mergeAdditionalSource(publisher, comparator) : mergeOrdered(comparator, this, publisher);
    }

    public final Flux<T> mergeComparingWith(Publisher<? extends T> publisher, Comparator<? super T> comparator) {
        return this instanceof FluxMergeComparing ? ((FluxMergeComparing) this).mergeAdditionalSource(publisher, comparator) : mergeComparing(comparator, this, publisher);
    }

    public final Flux<T> mergeWith(Publisher<? extends T> publisher) {
        return this instanceof FluxMerge ? ((FluxMerge) this).mergeAdditionalSource(publisher, new IntFunction() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda22
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return Queues.get(i);
            }
        }) : merge(this, publisher);
    }

    @Deprecated
    public final Flux<T> metrics() {
        if (!Metrics.isInstrumentationAvailable()) {
            return this;
        }
        if (this instanceof Fuseable) {
            return onAssembly(new FluxMetricsFuseable(this));
        }
        return onAssembly(new FluxMetrics(this));
    }

    public final Flux<T> name(String str) {
        return FluxName.createOrAppend(this, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Mono<T> next() {
        if (this instanceof Callable) {
            return Mono.onAssembly(wrapToMono((Callable) this));
        }
        return Mono.onAssembly(new MonoNext(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <U> Flux<U> ofType(final Class<U> cls) {
        Objects.requireNonNull(cls, "clazz");
        return (Flux<U>) filter(new Predicate() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cls.isAssignableFrom(obj.getClass());
            }
        }).cast(cls);
    }

    public final Flux<T> onBackpressureBuffer() {
        return onAssembly(new FluxOnBackpressureBuffer(this, Queues.SMALL_BUFFER_SIZE, true, null));
    }

    public final Flux<T> onBackpressureBuffer(int i) {
        return onAssembly(new FluxOnBackpressureBuffer(this, i, false, null));
    }

    public final Flux<T> onBackpressureBuffer(int i, Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "onOverflow");
        return onAssembly(new FluxOnBackpressureBuffer(this, i, false, consumer));
    }

    public final Flux<T> onBackpressureBuffer(int i, BufferOverflowStrategy bufferOverflowStrategy) {
        Objects.requireNonNull(bufferOverflowStrategy, "bufferOverflowStrategy");
        return onAssembly(new FluxOnBackpressureBufferStrategy(this, i, null, bufferOverflowStrategy));
    }

    public final Flux<T> onBackpressureBuffer(int i, Consumer<? super T> consumer, BufferOverflowStrategy bufferOverflowStrategy) {
        Objects.requireNonNull(consumer, "onBufferOverflow");
        Objects.requireNonNull(bufferOverflowStrategy, "bufferOverflowStrategy");
        return onAssembly(new FluxOnBackpressureBufferStrategy(this, i, consumer, bufferOverflowStrategy));
    }

    public final Flux<T> onBackpressureBuffer(Duration duration, int i, Consumer<? super T> consumer) {
        return onBackpressureBuffer(duration, i, consumer, Schedulers.parallel());
    }

    public final Flux<T> onBackpressureBuffer(Duration duration, int i, Consumer<? super T> consumer, Scheduler scheduler) {
        Objects.requireNonNull(duration, "ttl");
        Objects.requireNonNull(consumer, "onBufferEviction");
        return onAssembly(new FluxOnBackpressureBufferTimeout(this, duration, scheduler, i, consumer));
    }

    public final Flux<T> onBackpressureDrop() {
        return onAssembly(new FluxOnBackpressureDrop(this));
    }

    public final Flux<T> onBackpressureDrop(Consumer<? super T> consumer) {
        return onAssembly(new FluxOnBackpressureDrop(this, consumer));
    }

    static /* synthetic */ void lambda$onBackpressureError$27(Object obj) {
        throw Exceptions.failWithOverflow();
    }

    public final Flux<T> onBackpressureError() {
        return onBackpressureDrop(new Consumer() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda23
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Flux.lambda$onBackpressureError$27(obj);
            }
        });
    }

    public final Flux<T> onBackpressureLatest() {
        return onAssembly(new FluxOnBackpressureLatest(this));
    }

    public final Flux<T> onErrorComplete() {
        return onAssembly(new FluxOnErrorReturn(this, null, null));
    }

    public final Flux<T> onErrorComplete(Class<? extends Throwable> cls) {
        Objects.requireNonNull(cls, "type must not be null");
        Objects.requireNonNull(cls);
        return onErrorComplete(new Flux$$ExternalSyntheticLambda1(cls));
    }

    public final Flux<T> onErrorComplete(Predicate<? super Throwable> predicate) {
        Objects.requireNonNull(predicate, "predicate must not be null");
        return onAssembly(new FluxOnErrorReturn(this, predicate, null));
    }

    public final Flux<T> onErrorContinue(BiConsumer<Throwable, Object> biConsumer) {
        return contextWriteSkippingContextPropagation(Context.m1981of(OnNextFailureStrategy.KEY_ON_NEXT_ERROR_STRATEGY, OnNextFailureStrategy.resume(biConsumer)));
    }

    public final <E extends Throwable> Flux<T> onErrorContinue(Class<E> cls, BiConsumer<Throwable, Object> biConsumer) {
        Objects.requireNonNull(cls);
        return onErrorContinue(new Flux$$ExternalSyntheticLambda1(cls), biConsumer);
    }

    public final <E extends Throwable> Flux<T> onErrorContinue(Predicate<E> predicate, BiConsumer<Throwable, Object> biConsumer) {
        return contextWriteSkippingContextPropagation(Context.m1981of(OnNextFailureStrategy.KEY_ON_NEXT_ERROR_STRATEGY, OnNextFailureStrategy.resumeIf(predicate, biConsumer)));
    }

    public final Flux<T> onErrorStop() {
        return contextWriteSkippingContextPropagation(Context.m1981of(OnNextFailureStrategy.KEY_ON_NEXT_ERROR_STRATEGY, OnNextFailureStrategy.stop()));
    }

    public final Flux<T> onErrorMap(final Function<? super Throwable, ? extends Throwable> function) {
        return onErrorResume(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda31
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mono.error((Throwable) function.apply((Throwable) obj));
            }
        });
    }

    public final <E extends Throwable> Flux<T> onErrorMap(Class<E> cls, Function<? super E, ? extends Throwable> function) {
        Objects.requireNonNull(cls);
        return onErrorMap(new Flux$$ExternalSyntheticLambda1(cls), function);
    }

    public final Flux<T> onErrorMap(Predicate<? super Throwable> predicate, final Function<? super Throwable, ? extends Throwable> function) {
        return onErrorResume(predicate, new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda43
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mono.error((Throwable) function.apply((Throwable) obj));
            }
        });
    }

    public final Flux<T> onErrorResume(Function<? super Throwable, ? extends Publisher<? extends T>> function) {
        return onAssembly(new FluxOnErrorResume(this, function));
    }

    public final <E extends Throwable> Flux<T> onErrorResume(Class<E> cls, Function<? super E, ? extends Publisher<? extends T>> function) {
        Objects.requireNonNull(cls, TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE);
        Objects.requireNonNull(cls);
        return onErrorResume(new Flux$$ExternalSyntheticLambda1(cls), function);
    }

    public final Flux<T> onErrorResume(final Predicate<? super Throwable> predicate, final Function<? super Throwable, ? extends Publisher<? extends T>> function) {
        Objects.requireNonNull(predicate, "predicate");
        return onErrorResume(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda26
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Flux.lambda$onErrorResume$30(predicate, function, (Throwable) obj);
            }
        });
    }

    static /* synthetic */ Publisher lambda$onErrorResume$30(Predicate predicate, Function function, Throwable th) {
        return predicate.test(th) ? (Publisher) function.apply(th) : error(th);
    }

    public final Flux<T> onErrorReturn(T t) {
        Objects.requireNonNull(t, "fallbackValue must not be null");
        return onAssembly(new FluxOnErrorReturn(this, null, t));
    }

    public final <E extends Throwable> Flux<T> onErrorReturn(Class<E> cls, T t) {
        Objects.requireNonNull(cls, "type must not be null");
        Objects.requireNonNull(cls);
        return onErrorReturn(new Flux$$ExternalSyntheticLambda1(cls), t);
    }

    public final Flux<T> onErrorReturn(Predicate<? super Throwable> predicate, T t) {
        Objects.requireNonNull(predicate, "predicate must not be null");
        Objects.requireNonNull(t, "fallbackValue must not be null");
        return onAssembly(new FluxOnErrorReturn(this, predicate, t));
    }

    public final Flux<T> onTerminateDetach() {
        return new FluxDetach(this);
    }

    /* JADX INFO: renamed from: or */
    public final Flux<T> m1959or(Publisher<? extends T> publisher) {
        FluxFirstWithSignal<T> fluxFirstWithSignalOrAdditionalSource;
        return (!(this instanceof FluxFirstWithSignal) || (fluxFirstWithSignalOrAdditionalSource = ((FluxFirstWithSignal) this).orAdditionalSource(publisher)) == null) ? firstWithSignal(this, publisher) : fluxFirstWithSignalOrAdditionalSource;
    }

    public final ParallelFlux<T> parallel() {
        return parallel(Schedulers.DEFAULT_POOL_SIZE);
    }

    public final ParallelFlux<T> parallel(int i) {
        return parallel(i, Queues.SMALL_BUFFER_SIZE);
    }

    public final ParallelFlux<T> parallel(int i, int i2) {
        return ParallelFlux.from(this, i, i2, Queues.get(i2));
    }

    public final ConnectableFlux<T> publish() {
        return publish(Queues.SMALL_BUFFER_SIZE);
    }

    public final ConnectableFlux<T> publish(int i) {
        return onAssembly((ConnectableFlux) new FluxPublish(this, i, Queues.get(i), true));
    }

    public final <R> Flux<R> publish(Function<? super Flux<T>, ? extends Publisher<? extends R>> function) {
        return publish(function, Queues.SMALL_BUFFER_SIZE);
    }

    public final <R> Flux<R> publish(Function<? super Flux<T>, ? extends Publisher<? extends R>> function, int i) {
        return onAssembly(new FluxPublishMulticast(this, function, i, Queues.get(i)));
    }

    @Deprecated
    public final Mono<T> publishNext() {
        return shareNext();
    }

    public final Flux<T> publishOn(Scheduler scheduler) {
        return publishOn(scheduler, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> publishOn(Scheduler scheduler, int i) {
        return publishOn(scheduler, true, i);
    }

    public final Flux<T> publishOn(Scheduler scheduler, boolean z, int i) {
        return publishOn(scheduler, z, i, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    final Flux<T> publishOn(Scheduler scheduler, boolean z, int i, int i2) {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                try {
                    return onAssembly(new FluxSubscribeOnValue(((Fuseable.ScalarCallable) this).call(), scheduler));
                } catch (Exception unused) {
                }
            }
            return onAssembly(new FluxSubscribeOnCallable((Callable) this, scheduler));
        }
        return onAssembly(new FluxPublishOn(this, scheduler, z, i, i2, Queues.get(i)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Mono<T> reduce(BiFunction<T, T, T> biFunction) {
        if (this instanceof Callable) {
            return Mono.onAssembly(wrapToMono((Callable) this));
        }
        return Mono.onAssembly(new MonoReduce(this, biFunction));
    }

    public final <A> Mono<A> reduce(final A a, BiFunction<A, ? super T, A> biFunction) {
        return reduceWith(new Supplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda45
            @Override // java.util.function.Supplier
            public final Object get() {
                return Flux.lambda$reduce$31(a);
            }
        }, biFunction);
    }

    public final <A> Mono<A> reduceWith(Supplier<A> supplier, BiFunction<A, ? super T, A> biFunction) {
        return Mono.onAssembly(new MonoReduceSeed(this, supplier, biFunction));
    }

    public final Flux<T> repeat() {
        return repeat(ALWAYS_BOOLEAN_SUPPLIER);
    }

    public final Flux<T> repeat(BooleanSupplier booleanSupplier) {
        return onAssembly(new FluxRepeatPredicate(this, booleanSupplier));
    }

    public final Flux<T> repeat(long j) {
        return j == 0 ? this : onAssembly(new FluxRepeat(this, j));
    }

    /* JADX INFO: renamed from: lambda$repeat$32$reactor-core-publisher-Flux, reason: not valid java name */
    /* synthetic */ Publisher m4280lambda$repeat$32$reactorcorepublisherFlux(BooleanSupplier booleanSupplier, long j) {
        return repeat(countingBooleanSupplier(booleanSupplier, j));
    }

    public final Flux<T> repeat(final long j, final BooleanSupplier booleanSupplier) {
        if (j >= 0) {
            return j == 0 ? this : defer(new Supplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda14
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m4280lambda$repeat$32$reactorcorepublisherFlux(booleanSupplier, j);
                }
            });
        }
        throw new IllegalArgumentException("numRepeat >= 0 required");
    }

    public final Flux<T> repeatWhen(Function<Flux<Long>, ? extends Publisher<?>> function) {
        return onAssembly(new FluxRepeatWhen(this, function));
    }

    public final ConnectableFlux<T> replay() {
        return replay(Integer.MAX_VALUE);
    }

    public final ConnectableFlux<T> replay(int i) {
        if (i == 0) {
            return onAssembly((ConnectableFlux) new FluxPublish(this, Queues.SMALL_BUFFER_SIZE, Queues.get(Queues.SMALL_BUFFER_SIZE), false));
        }
        return onAssembly((ConnectableFlux) new FluxReplay(this, i, 0L, null));
    }

    public final ConnectableFlux<T> replay(Duration duration) {
        return replay(Integer.MAX_VALUE, duration);
    }

    public final ConnectableFlux<T> replay(int i, Duration duration) {
        return replay(i, duration, Schedulers.parallel());
    }

    public final ConnectableFlux<T> replay(Duration duration, Scheduler scheduler) {
        return replay(Integer.MAX_VALUE, duration, scheduler);
    }

    public final ConnectableFlux<T> replay(int i, Duration duration, Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "timer");
        if (i == 0) {
            return onAssembly((ConnectableFlux) new FluxPublish(this, Queues.SMALL_BUFFER_SIZE, Queues.get(Queues.SMALL_BUFFER_SIZE), true));
        }
        return onAssembly((ConnectableFlux) new FluxReplay(this, i, duration.toNanos(), scheduler));
    }

    public final Flux<T> retry() {
        return retry(Long.MAX_VALUE);
    }

    public final Flux<T> retry(long j) {
        return onAssembly(new FluxRetry(this, j));
    }

    public final Flux<T> retryWhen(Retry retry) {
        return onAssembly(new FluxRetryWhen(this, retry));
    }

    public final Flux<T> sample(Duration duration) {
        return sample(interval(duration));
    }

    public final <U> Flux<T> sample(Publisher<U> publisher) {
        return onAssembly(new FluxSample(this, publisher));
    }

    public final Flux<T> sampleFirst(final Duration duration) {
        return sampleFirst(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda51
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mono.delay(duration);
            }
        });
    }

    public final <U> Flux<T> sampleFirst(Function<? super T, ? extends Publisher<U>> function) {
        return onAssembly(new FluxSampleFirst(this, function));
    }

    public final <U> Flux<T> sampleTimeout(Function<? super T, ? extends Publisher<U>> function) {
        return sampleTimeout(function, Queues.XS_BUFFER_SIZE);
    }

    public final <U> Flux<T> sampleTimeout(Function<? super T, ? extends Publisher<U>> function, int i) {
        return onAssembly(new FluxSampleTimeout(this, function, Queues.get(i)));
    }

    public final Flux<T> scan(BiFunction<T, T, T> biFunction) {
        return onAssembly(new FluxScan(this, biFunction));
    }

    public final <A> Flux<A> scan(final A a, BiFunction<A, ? super T, A> biFunction) {
        Objects.requireNonNull(a, "seed");
        return scanWith(new Supplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda44
            @Override // java.util.function.Supplier
            public final Object get() {
                return Flux.lambda$scan$34(a);
            }
        }, biFunction);
    }

    public final <A> Flux<A> scanWith(Supplier<A> supplier, BiFunction<A, ? super T, A> biFunction) {
        return onAssembly(new FluxScanSeed(this, supplier, biFunction));
    }

    public final Flux<T> share() {
        return onAssembly(new FluxRefCount(new FluxPublish(this, Queues.SMALL_BUFFER_SIZE, Queues.small(), true), 1));
    }

    public final Mono<T> shareNext() {
        return Mono.onAssembly(new NextProcessor(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Mono<T> single() {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                try {
                    T tCall = ((Fuseable.ScalarCallable) this).call();
                    if (tCall == null) {
                        return Mono.error(new NoSuchElementException("Source was a (constant) empty"));
                    }
                    return Mono.just(tCall);
                } catch (Exception e) {
                    return Mono.error(Exceptions.unwrap(e));
                }
            }
            return Mono.onAssembly(new MonoSingleCallable((Callable) this));
        }
        return Mono.onAssembly(new MonoSingle(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Mono<T> single(T t) {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                try {
                    T tCall = ((Fuseable.ScalarCallable) this).call();
                    if (tCall == null) {
                        return Mono.just(t);
                    }
                    return Mono.just(tCall);
                } catch (Exception e) {
                    return Mono.error(Exceptions.unwrap(e));
                }
            }
            return Mono.onAssembly(new MonoSingleCallable((Callable) this, t));
        }
        return Mono.onAssembly(new MonoSingle(this, t, false));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Mono<T> singleOrEmpty() {
        if (this instanceof Callable) {
            return Mono.onAssembly(wrapToMono((Callable) this));
        }
        return Mono.onAssembly(new MonoSingle(this, null, true));
    }

    public final Flux<T> skip(long j) {
        return j == 0 ? this : onAssembly(new FluxSkip(this, j));
    }

    public final Flux<T> skip(Duration duration) {
        return skip(duration, Schedulers.parallel());
    }

    public final Flux<T> skip(Duration duration, Scheduler scheduler) {
        return !duration.isZero() ? skipUntilOther(Mono.delay(duration, scheduler)) : this;
    }

    public final Flux<T> skipLast(int i) {
        return i == 0 ? this : onAssembly(new FluxSkipLast(this, i));
    }

    public final Flux<T> skipUntil(Predicate<? super T> predicate) {
        return onAssembly(new FluxSkipUntil(this, predicate));
    }

    public final Flux<T> skipUntilOther(Publisher<?> publisher) {
        return onAssembly(new FluxSkipUntilOther(this, publisher));
    }

    public final Flux<T> skipWhile(Predicate<? super T> predicate) {
        return onAssembly(new FluxSkipWhile(this, predicate));
    }

    public final Flux<T> sort() {
        return (Flux<T>) collectSortedList().flatMapIterable(identityFunction());
    }

    public final Flux<T> sort(Comparator<? super T> comparator) {
        return (Flux<T>) collectSortedList(comparator).flatMapIterable(identityFunction());
    }

    public final Flux<T> startWith(Iterable<? extends T> iterable) {
        return startWith(fromIterable(iterable));
    }

    @SafeVarargs
    public final Flux<T> startWith(T... tArr) {
        return startWith(just((Object[]) tArr));
    }

    public final Flux<T> startWith(Publisher<? extends T> publisher) {
        return this instanceof FluxConcatArray ? ((FluxConcatArray) this).concatAdditionalSourceFirst(publisher) : concat(publisher, this);
    }

    public final Disposable subscribe() {
        return subscribe(null, null, null);
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

    @Deprecated
    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable Runnable runnable, @Nullable Consumer<? super Subscription> consumer3) {
        return (Disposable) subscribeWith(new LambdaSubscriber(consumer, consumer2, runnable, consumer3, null));
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable Runnable runnable, @Nullable Context context) {
        return (Disposable) subscribeWith(new LambdaSubscriber(consumer, consumer2, runnable, null, context));
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

    public final Flux<T> subscribeOn(Scheduler scheduler) {
        return subscribeOn(scheduler, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Flux<T> subscribeOn(Scheduler scheduler, boolean z) {
        if (this instanceof Callable) {
            if (this instanceof Fuseable.ScalarCallable) {
                try {
                    return onAssembly(new FluxSubscribeOnValue(((Fuseable.ScalarCallable) this).call(), scheduler));
                } catch (Exception unused) {
                }
            }
            return onAssembly(new FluxSubscribeOnCallable((Callable) this, scheduler));
        }
        return onAssembly(new FluxSubscribeOn(this, scheduler, z));
    }

    public final <E extends Subscriber<? super T>> E subscribeWith(E e) {
        subscribe(e);
        return e;
    }

    public final <V> Flux<V> switchOnFirst(BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends V>> biFunction) {
        return switchOnFirst(biFunction, true);
    }

    public final <V> Flux<V> switchOnFirst(BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends V>> biFunction, boolean z) {
        return onAssembly(new FluxSwitchOnFirst(this, biFunction, z));
    }

    public final Flux<T> switchIfEmpty(Publisher<? extends T> publisher) {
        return onAssembly(new FluxSwitchIfEmpty(this, publisher));
    }

    public final <V> Flux<V> switchMap(Function<? super T, Publisher<? extends V>> function) {
        return onAssembly(new FluxSwitchMapNoPrefetch(this, function));
    }

    @Deprecated
    public final <V> Flux<V> switchMap(Function<? super T, Publisher<? extends V>> function, int i) {
        if (i == 0) {
            return onAssembly(new FluxSwitchMapNoPrefetch(this, function));
        }
        return onAssembly(new FluxSwitchMap(this, function, Queues.unbounded(i), i));
    }

    public final Flux<T> tag(String str, String str2) {
        return FluxName.createOrAppend(this, str, str2);
    }

    public final Flux<T> take(long j) {
        return take(j, true);
    }

    public final Flux<T> take(long j, boolean z) {
        if (z) {
            return onAssembly(new FluxLimitRequest(this, j));
        }
        if (this instanceof Fuseable) {
            return onAssembly(new FluxTakeFuseable(this, j));
        }
        return onAssembly(new FluxTake(this, j));
    }

    public final Flux<T> take(Duration duration) {
        return take(duration, Schedulers.parallel());
    }

    public final Flux<T> take(Duration duration, Scheduler scheduler) {
        if (!duration.isZero()) {
            return takeUntilOther(Mono.delay(duration, scheduler));
        }
        return take(0L, false);
    }

    public final Flux<T> takeLast(int i) {
        if (i == 1) {
            return onAssembly(new FluxTakeLastOne(this));
        }
        return onAssembly(new FluxTakeLast(this, i));
    }

    public final Flux<T> takeUntil(Predicate<? super T> predicate) {
        return onAssembly(new FluxTakeUntil(this, predicate));
    }

    public final Flux<T> takeUntilOther(Publisher<?> publisher) {
        return onAssembly(new FluxTakeUntilOther(this, publisher));
    }

    public final Flux<T> takeWhile(Predicate<? super T> predicate) {
        return onAssembly(new FluxTakeWhile(this, predicate));
    }

    public final Flux<T> tap(final Supplier<SignalListener<T>> supplier) {
        return tap(new SignalListenerFactory<T, Void>() { // from class: reactor.core.publisher.Flux.2
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

    public final Flux<T> tap(final Function<ContextView, SignalListener<T>> function) {
        return tap(new SignalListenerFactory<T, Void>() { // from class: reactor.core.publisher.Flux.3
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

    public final Flux<T> tap(SignalListenerFactory<T, ?> signalListenerFactory) {
        if (ContextPropagationSupport.shouldPropagateContextToThreadLocals()) {
            return onAssembly(new FluxTapRestoringThreadLocals(this, signalListenerFactory));
        }
        if (this instanceof Fuseable) {
            return onAssembly(new FluxTapFuseable(this, signalListenerFactory));
        }
        return onAssembly(new FluxTap(this, signalListenerFactory));
    }

    public final Mono<Void> then() {
        return Mono.onAssembly(new MonoIgnoreElements(this));
    }

    public final <V> Mono<V> then(Mono<V> mono) {
        return Mono.onAssembly(new MonoIgnoreThen(new Publisher[]{this}, mono));
    }

    public final Mono<Void> thenEmpty(Publisher<Void> publisher) {
        return then(Mono.fromDirect(publisher));
    }

    public final <V> Flux<V> thenMany(Publisher<V> publisher) {
        return concat(ignoreElements(), publisher);
    }

    public final Flux<Timed<T>> timed() {
        return timed(Schedulers.parallel());
    }

    public final Flux<Timed<T>> timed(Scheduler scheduler) {
        return onAssembly(new FluxTimed(this, scheduler));
    }

    public final Flux<T> timeout(Duration duration) {
        return timeout(duration, (Publisher) null, Schedulers.parallel());
    }

    public final Flux<T> timeout(Duration duration, @Nullable Publisher<? extends T> publisher) {
        return timeout(duration, publisher, Schedulers.parallel());
    }

    public final Flux<T> timeout(Duration duration, Scheduler scheduler) {
        return timeout(duration, (Publisher) null, scheduler);
    }

    public final Flux<T> timeout(Duration duration, @Nullable Publisher<? extends T> publisher, Scheduler scheduler) {
        final Mono<Long> monoOnErrorReturn = Mono.delay(duration, scheduler).onErrorReturn(0L);
        Function<? super T, ? extends Publisher<V>> function = new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda52
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Flux.lambda$timeout$35(monoOnErrorReturn, obj);
            }
        };
        if (publisher == null) {
            return timeout(monoOnErrorReturn, function, duration.toMillis() + "ms");
        }
        return timeout(monoOnErrorReturn, function, publisher);
    }

    public final <U> Flux<T> timeout(Publisher<U> publisher) {
        return timeout(publisher, new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda50
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Flux.never();
            }
        });
    }

    public final <U, V> Flux<T> timeout(Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function) {
        return timeout(publisher, function, "first signal from a Publisher");
    }

    private <U, V> Flux<T> timeout(Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function, String str) {
        return onAssembly(new FluxTimeout(this, publisher, function, str));
    }

    public final <U, V> Flux<T> timeout(Publisher<U> publisher, Function<? super T, ? extends Publisher<V>> function, Publisher<? extends T> publisher2) {
        return onAssembly(new FluxTimeout(this, publisher, function, publisher2));
    }

    public final Flux<Tuple2<Long, T>> timestamp() {
        return timestamp(Schedulers.parallel());
    }

    public final Flux<Tuple2<Long, T>> timestamp(final Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler");
        return (Flux<Tuple2<Long, T>>) map(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Tuples.m1988of(Long.valueOf(scheduler.now(TimeUnit.MILLISECONDS)), obj);
            }
        });
    }

    public final Iterable<T> toIterable() {
        return toIterable(Queues.SMALL_BUFFER_SIZE);
    }

    public final Iterable<T> toIterable(int i) {
        return toIterable(i, null);
    }

    public final Iterable<T> toIterable(int i, @Nullable final Supplier<Queue<T>> supplier) {
        Supplier supplier2;
        if (supplier == null) {
            supplier2 = Queues.get(i);
        } else {
            supplier2 = new Supplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda46
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Hooks.wrapQueue((Queue) supplier.get());
                }
            };
        }
        return new BlockingIterable(this, i, supplier2, ContextPropagationSupport.shouldPropagateContextToThreadLocals() ? new Flux$$ExternalSyntheticLambda20() : new Flux$$ExternalSyntheticLambda21());
    }

    public final Stream<T> toStream() {
        return toStream(Queues.SMALL_BUFFER_SIZE);
    }

    public final Stream<T> toStream(int i) {
        return new BlockingIterable(this, i, Queues.get(i), ContextPropagationSupport.shouldPropagateContextToThreadLocals() ? new Flux$$ExternalSyntheticLambda20() : new Flux$$ExternalSyntheticLambda21()).stream();
    }

    public final <V> Flux<V> transform(Function<? super Flux<T>, ? extends Publisher<V>> function) {
        if (Hooks.DETECT_CONTEXT_LOSS) {
            function = new ContextTrackingFunctionWrapper(function);
        }
        return onAssembly(from(function.apply(this)));
    }

    public final <V> Flux<V> transformDeferred(final Function<? super Flux<T>, ? extends Publisher<V>> function) {
        return defer(new Supplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda55
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m4281lambda$transformDeferred$39$reactorcorepublisherFlux(function);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$transformDeferred$39$reactor-core-publisher-Flux, reason: not valid java name */
    /* synthetic */ Publisher m4281lambda$transformDeferred$39$reactorcorepublisherFlux(Function function) {
        if (Hooks.DETECT_CONTEXT_LOSS) {
            return new ContextTrackingFunctionWrapper(function).apply((Publisher) this);
        }
        return (Publisher) function.apply(this);
    }

    public final <V> Flux<V> transformDeferredContextual(final BiFunction<? super Flux<T>, ? super ContextView, ? extends Publisher<V>> biFunction) {
        return deferContextual(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m1958x815daa5b(biFunction, (ContextView) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$transformDeferredContextual$41$reactor-core-publisher-Flux */
    /* synthetic */ Publisher m1958x815daa5b(final BiFunction biFunction, final ContextView contextView) {
        if (Hooks.DETECT_CONTEXT_LOSS) {
            return new ContextTrackingFunctionWrapper(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda33
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Flux.lambda$transformDeferredContextual$40(biFunction, contextView, (Publisher) obj);
                }
            }, biFunction.toString()).apply((Publisher) this);
        }
        return (Publisher) biFunction.apply(this, contextView);
    }

    static /* synthetic */ Publisher lambda$transformDeferredContextual$40(BiFunction biFunction, ContextView contextView, Publisher publisher) {
        return (Publisher) biFunction.apply(wrap(publisher), contextView);
    }

    public final Flux<Flux<T>> window(int i) {
        return onAssembly(new FluxWindow(this, i, Queues.get(i)));
    }

    public final Flux<Flux<T>> window(int i, int i2) {
        return onAssembly(new FluxWindow(this, i, i2, Queues.unbounded(Queues.XS_BUFFER_SIZE), Queues.unbounded(Queues.XS_BUFFER_SIZE)));
    }

    public final Flux<Flux<T>> window(Publisher<?> publisher) {
        return onAssembly(new FluxWindowBoundary(this, publisher, Queues.unbounded(Queues.XS_BUFFER_SIZE)));
    }

    public final Flux<Flux<T>> window(Duration duration) {
        return window(duration, Schedulers.parallel());
    }

    public final Flux<Flux<T>> window(Duration duration, Duration duration2) {
        return window(duration, duration2, Schedulers.parallel());
    }

    public final Flux<Flux<T>> window(Duration duration, Scheduler scheduler) {
        return window(interval(duration, scheduler));
    }

    public final Flux<Flux<T>> window(final Duration duration, Duration duration2, final Scheduler scheduler) {
        if (duration2.equals(duration)) {
            return window(duration);
        }
        return windowWhen(interval(Duration.ZERO, duration2, scheduler), new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda24
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mono.delay(duration, scheduler);
            }
        });
    }

    public final Flux<Flux<T>> windowTimeout(int i, Duration duration) {
        return windowTimeout(i, duration, Schedulers.parallel());
    }

    public final Flux<Flux<T>> windowTimeout(int i, Duration duration, boolean z) {
        return windowTimeout(i, duration, Schedulers.parallel(), z);
    }

    public final Flux<Flux<T>> windowTimeout(int i, Duration duration, Scheduler scheduler) {
        return windowTimeout(i, duration, scheduler, false);
    }

    public final Flux<Flux<T>> windowTimeout(int i, Duration duration, Scheduler scheduler, boolean z) {
        return onAssembly(new FluxWindowTimeout(this, i, duration.toNanos(), TimeUnit.NANOSECONDS, scheduler, z));
    }

    public final Flux<Flux<T>> windowUntil(Predicate<T> predicate) {
        return windowUntil(predicate, false);
    }

    public final Flux<Flux<T>> windowUntil(Predicate<T> predicate, boolean z) {
        return windowUntil(predicate, z, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<Flux<T>> windowUntil(Predicate<T> predicate, boolean z, int i) {
        return onAssembly(new FluxWindowPredicate(this, Queues.unbounded(i), Queues.unbounded(i), i, predicate, z ? FluxBufferPredicate.Mode.UNTIL_CUT_BEFORE : FluxBufferPredicate.Mode.UNTIL));
    }

    public final Flux<Flux<T>> windowUntilChanged() {
        return windowUntilChanged(identityFunction());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <V> Flux<Flux<T>> windowUntilChanged(Function<? super T, ? super V> function) {
        return windowUntilChanged(function, equalPredicate());
    }

    /* JADX INFO: renamed from: lambda$windowUntilChanged$43$reactor-core-publisher-Flux, reason: not valid java name */
    /* synthetic */ Publisher m4282lambda$windowUntilChanged$43$reactorcorepublisherFlux(Function function, BiPredicate biPredicate) {
        return windowUntil(new FluxBufferPredicate.ChangedPredicate(function, biPredicate), true);
    }

    public final <V> Flux<Flux<T>> windowUntilChanged(final Function<? super T, ? extends V> function, final BiPredicate<? super V, ? super V> biPredicate) {
        return defer(new Supplier() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda49
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m4282lambda$windowUntilChanged$43$reactorcorepublisherFlux(function, biPredicate);
            }
        });
    }

    public final Flux<Flux<T>> windowWhile(Predicate<T> predicate) {
        return windowWhile(predicate, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<Flux<T>> windowWhile(Predicate<T> predicate, int i) {
        return onAssembly(new FluxWindowPredicate(this, Queues.unbounded(i), Queues.unbounded(i), i, predicate, FluxBufferPredicate.Mode.WHILE));
    }

    public final <U, V> Flux<Flux<T>> windowWhen(Publisher<U> publisher, Function<? super U, ? extends Publisher<V>> function) {
        return onAssembly(new FluxWindowWhen(this, publisher, function, Queues.unbounded(Queues.XS_BUFFER_SIZE)));
    }

    public final <U, R> Flux<R> withLatestFrom(Publisher<? extends U> publisher, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        return onAssembly(new FluxWithLatestFrom(this, publisher, biFunction));
    }

    public final <T2> Flux<Tuple2<T, T2>> zipWith(Publisher<? extends T2> publisher) {
        return (Flux<Tuple2<T, T2>>) zipWith(publisher, tuple2Function());
    }

    public final <T2, V> Flux<V> zipWith(Publisher<? extends T2> publisher, BiFunction<? super T, ? super T2, ? extends V> biFunction) {
        FluxZip fluxZipZipAdditionalSource;
        return (!(this instanceof FluxZip) || (fluxZipZipAdditionalSource = ((FluxZip) this).zipAdditionalSource(publisher, biFunction)) == null) ? zip(this, publisher, biFunction) : fluxZipZipAdditionalSource;
    }

    public final <T2, V> Flux<V> zipWith(Publisher<? extends T2> publisher, int i, final BiFunction<? super T, ? super T2, ? extends V> biFunction) {
        return zip(new Function() { // from class: reactor.core.publisher.Flux$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Object[] objArr = (Object[]) obj;
                return biFunction.apply(objArr[0], objArr[1]);
            }
        }, i, this, publisher);
    }

    public final <T2> Flux<Tuple2<T, T2>> zipWith(Publisher<? extends T2> publisher, int i) {
        return (Flux<Tuple2<T, T2>>) zipWith(publisher, i, tuple2Function());
    }

    public final <T2> Flux<Tuple2<T, T2>> zipWithIterable(Iterable<? extends T2> iterable) {
        return (Flux<Tuple2<T, T2>>) zipWithIterable(iterable, tuple2Function());
    }

    public final <T2, V> Flux<V> zipWithIterable(Iterable<? extends T2> iterable, BiFunction<? super T, ? super T2, ? extends V> biFunction) {
        return onAssembly(new FluxZipIterable(this, iterable, biFunction));
    }

    protected static <T> Flux<T> onAssembly(Flux<T> flux) {
        Function<Publisher, Publisher> function = Hooks.onEachOperatorHook;
        if (function != null) {
            flux = (Flux) function.apply(flux);
        }
        return Hooks.GLOBAL_TRACE ? (Flux) Hooks.addAssemblyInfo(flux, new FluxOnAssembly.AssemblySnapshot(null, Traces.callSiteSupplierFactory.get())) : flux;
    }

    protected static <T> ConnectableFlux<T> onAssembly(ConnectableFlux<T> connectableFlux) {
        Function<Publisher, Publisher> function = Hooks.onEachOperatorHook;
        if (function != null) {
            connectableFlux = (ConnectableFlux) function.apply(connectableFlux);
        }
        return Hooks.GLOBAL_TRACE ? (ConnectableFlux) Hooks.addAssemblyInfo(connectableFlux, new FluxOnAssembly.AssemblySnapshot(null, Traces.callSiteSupplierFactory.get())) : connectableFlux;
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    final <V> Flux<V> flatMap(Function<? super T, ? extends Publisher<? extends V>> function, boolean z, int i, int i2) {
        return onAssembly(new FluxFlatMap(this, function, z, i, Queues.get(i), i2, Queues.get(i2)));
    }

    final <R> Flux<R> flatMapSequential(Function<? super T, ? extends Publisher<? extends R>> function, boolean z, int i, int i2) {
        FluxConcatMap.ErrorMode errorMode;
        if (z) {
            errorMode = FluxConcatMap.ErrorMode.END;
        } else {
            errorMode = FluxConcatMap.ErrorMode.IMMEDIATE;
        }
        return onAssembly(new FluxMergeSequential(this, function, i, i2, errorMode));
    }

    static <T> Flux<T> doOnSignal(Flux<T> flux, @Nullable Consumer<? super Subscription> consumer, @Nullable Consumer<? super T> consumer2, @Nullable Consumer<? super Throwable> consumer3, @Nullable Runnable runnable, @Nullable Runnable runnable2, @Nullable LongConsumer longConsumer, @Nullable Runnable runnable3) {
        if (flux instanceof Fuseable) {
            return onAssembly(new FluxPeekFuseable(flux, consumer, consumer2, consumer3, runnable, runnable2, longConsumer, runnable3));
        }
        return onAssembly(new FluxPeek(flux, consumer, consumer2, consumer3, runnable, runnable2, longConsumer, runnable3));
    }

    static <T> Mono<T> wrapToMono(Callable<T> callable) {
        if (callable instanceof Fuseable.ScalarCallable) {
            try {
                T tCall = ((Fuseable.ScalarCallable) callable).call();
                if (tCall == null) {
                    return MonoEmpty.instance();
                }
                return new MonoJust(tCall);
            } catch (Exception e) {
                return new MonoError(Exceptions.unwrap(e));
            }
        }
        return new MonoCallable(callable);
    }

    @SafeVarargs
    static <I> Flux<I> merge(int i, boolean z, Publisher<? extends I>... publisherArr) {
        if (publisherArr.length == 0) {
            return empty();
        }
        if (publisherArr.length == 1) {
            return from(publisherArr[0]);
        }
        return onAssembly(new FluxMerge(publisherArr, z, publisherArr.length, Queues.get(publisherArr.length), i, Queues.get(i)));
    }

    @SafeVarargs
    static <I> Flux<I> mergeSequential(int i, boolean z, Publisher<? extends I>... publisherArr) {
        if (publisherArr.length == 0) {
            return empty();
        }
        if (publisherArr.length == 1) {
            return from(publisherArr[0]);
        }
        return onAssembly(new FluxMergeSequential(new FluxArray(publisherArr), identityFunction(), publisherArr.length, i, z ? FluxConcatMap.ErrorMode.END : FluxConcatMap.ErrorMode.IMMEDIATE));
    }

    static <T> Flux<T> mergeSequential(Publisher<? extends Publisher<? extends T>> publisher, boolean z, int i, int i2) {
        FluxConcatMap.ErrorMode errorMode;
        Flux fluxFrom = from(publisher);
        Function functionIdentityFunction = identityFunction();
        if (z) {
            errorMode = FluxConcatMap.ErrorMode.END;
        } else {
            errorMode = FluxConcatMap.ErrorMode.IMMEDIATE;
        }
        return onAssembly(new FluxMergeSequential(fluxFrom, functionIdentityFunction, i, i2, errorMode));
    }

    static <I> Flux<I> mergeSequential(Iterable<? extends Publisher<? extends I>> iterable, boolean z, int i, int i2) {
        return onAssembly(new FluxMergeSequential(new FluxIterable(iterable), identityFunction(), i, i2, z ? FluxConcatMap.ErrorMode.END : FluxConcatMap.ErrorMode.IMMEDIATE));
    }

    static BooleanSupplier countingBooleanSupplier(final BooleanSupplier booleanSupplier, final long j) {
        return j <= 0 ? booleanSupplier : new BooleanSupplier() { // from class: reactor.core.publisher.Flux.4

            /* JADX INFO: renamed from: n */
            long f2084n;

            @Override // java.util.function.BooleanSupplier
            public boolean getAsBoolean() {
                long j2 = this.f2084n;
                this.f2084n = 1 + j2;
                return j2 < j && booleanSupplier.getAsBoolean();
            }
        };
    }

    static <O> Predicate<O> countingPredicate(final Predicate<O> predicate, final long j) {
        return j == 0 ? predicate : new Predicate<O>() { // from class: reactor.core.publisher.Flux.5

            /* JADX INFO: renamed from: n */
            long f2085n;

            @Override // java.util.function.Predicate
            public boolean test(O o) {
                long j2 = this.f2085n;
                this.f2085n = 1 + j2;
                return j2 < j && predicate.test(o);
            }
        };
    }

    static <O> Supplier<Set<O>> hashSetSupplier() {
        return SET_SUPPLIER;
    }

    static <O> Supplier<List<O>> listSupplier() {
        return LIST_SUPPLIER;
    }

    static <U, V> BiPredicate<U, V> equalPredicate() {
        return OBJECT_EQUAL;
    }

    static <T> Function<T, T> identityFunction() {
        return IDENTITY_FUNCTION;
    }

    static <A, B> BiFunction<A, B, Tuple2<A, B>> tuple2Function() {
        return TUPLE2_BIFUNCTION;
    }

    static <I> Flux<I> wrap(Publisher<? extends I> publisher) {
        Flux<I> fluxSource;
        boolean zShouldWrapPublisher = ContextPropagationSupport.shouldWrapPublisher(publisher);
        if (publisher instanceof Flux) {
            if (!zShouldWrapPublisher) {
                return (Flux) publisher;
            }
            return ContextPropagation.fluxRestoreThreadLocals((Flux) publisher, publisher instanceof Fuseable);
        }
        if (publisher instanceof Fuseable.ScalarCallable) {
            try {
                T tCall = ((Fuseable.ScalarCallable) publisher).call();
                if (tCall != null) {
                    return new FluxJust(tCall);
                }
                return FluxEmpty.instance();
            } catch (Exception e) {
                return new FluxError(Exceptions.unwrap(e));
            }
        }
        boolean z = publisher instanceof Fuseable;
        if (publisher instanceof Mono) {
            if (z) {
                fluxSource = new FluxSourceMonoFuseable<>((Mono) publisher);
            } else {
                fluxSource = new FluxSourceMono<>((Mono) publisher);
            }
        } else if (z) {
            fluxSource = new FluxSourceFuseable<>(publisher);
        } else {
            fluxSource = new FluxSource<>(publisher);
        }
        return zShouldWrapPublisher ? ContextPropagation.fluxRestoreThreadLocals(fluxSource, z) : fluxSource;
    }
}
