package reactor.core.publisher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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
import reactor.core.Disposables;
import reactor.core.publisher.FluxConcatMap;
import reactor.core.publisher.FluxHide;
import reactor.core.publisher.FluxOnAssembly;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
public abstract class ParallelFlux<T> implements CorePublisher<T> {
    public int getPrefetch() {
        return -1;
    }

    public abstract int parallelism();

    public abstract void subscribe(CoreSubscriber<? super T>[] coreSubscriberArr);

    static <T> ParallelFlux<T> from(ParallelFlux<T> parallelFlux) {
        return ContextPropagationSupport.shouldWrapPublisher(parallelFlux) ? new ParallelFluxRestoringThreadLocals(parallelFlux) : parallelFlux;
    }

    public static <T> ParallelFlux<T> from(Publisher<? extends T> publisher) {
        return from(publisher, Schedulers.DEFAULT_POOL_SIZE, Queues.SMALL_BUFFER_SIZE, Queues.small());
    }

    public static <T> ParallelFlux<T> from(Publisher<? extends T> publisher, int i) {
        return from(publisher, i, Queues.SMALL_BUFFER_SIZE, Queues.small());
    }

    public static <T> ParallelFlux<T> from(Publisher<? extends T> publisher, int i, int i2, Supplier<Queue<T>> supplier) {
        Objects.requireNonNull(supplier, "queueSupplier");
        Objects.requireNonNull(publisher, "source");
        return onAssembly(new ParallelSource(publisher, i, i2, supplier));
    }

    @SafeVarargs
    public static <T> ParallelFlux<T> from(Publisher<T>... publisherArr) {
        return onAssembly(new ParallelArraySource(publisherArr));
    }

    /* JADX INFO: renamed from: as */
    public final <U> U m1973as(Function<? super ParallelFlux<T>, U> function) {
        return function.apply(this);
    }

    public final ParallelFlux<T> checkpoint() {
        return new ParallelFluxOnAssembly(this, new FluxOnAssembly.CheckpointHeavySnapshot(null, Traces.callSiteSupplierFactory.get()));
    }

    public final ParallelFlux<T> checkpoint(String str) {
        return new ParallelFluxOnAssembly(this, new FluxOnAssembly.CheckpointLightSnapshot(str));
    }

    public final ParallelFlux<T> checkpoint(String str, boolean z) {
        FluxOnAssembly.AssemblySnapshot checkpointHeavySnapshot;
        if (!z) {
            checkpointHeavySnapshot = new FluxOnAssembly.CheckpointLightSnapshot(str);
        } else {
            checkpointHeavySnapshot = new FluxOnAssembly.CheckpointHeavySnapshot(str, Traces.callSiteSupplierFactory.get());
        }
        return new ParallelFluxOnAssembly(this, checkpointHeavySnapshot);
    }

    public final <C> ParallelFlux<C> collect(Supplier<? extends C> supplier, BiConsumer<? super C, ? super T> biConsumer) {
        return onAssembly(new ParallelCollect(this, supplier, biConsumer));
    }

    public final Mono<List<T>> collectSortedList(Comparator<? super T> comparator) {
        return collectSortedList(comparator, 16);
    }

    public final Mono<List<T>> collectSortedList(final Comparator<? super T> comparator, int i) {
        final int iParallelism = (i / parallelism()) + 1;
        return reduce(new Supplier() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return ParallelFlux.lambda$collectSortedList$0(iParallelism);
            }
        }, new BiFunction() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda8
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ParallelFlux.lambda$collectSortedList$1((List) obj, obj2);
            }
        }).map(new Function() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ParallelFlux.lambda$collectSortedList$2(comparator, (List) obj);
            }
        }).reduce(new BiFunction() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda10
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ParallelFlux.sortedMerger((List) obj, (List) obj2, comparator);
            }
        });
    }

    static /* synthetic */ List lambda$collectSortedList$0(int i) {
        return new ArrayList(i);
    }

    static /* synthetic */ List lambda$collectSortedList$1(List list, Object obj) {
        list.add(obj);
        return list;
    }

    static /* synthetic */ List lambda$collectSortedList$2(Comparator comparator, List list) {
        list.sort(comparator);
        return list;
    }

    public final <R> ParallelFlux<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> function) {
        return concatMap(function, 2, FluxConcatMap.ErrorMode.IMMEDIATE);
    }

    public final <R> ParallelFlux<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> function, int i) {
        return concatMap(function, i, FluxConcatMap.ErrorMode.IMMEDIATE);
    }

    public final <R> ParallelFlux<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> function) {
        return concatMap(function, 2, FluxConcatMap.ErrorMode.END);
    }

    public final ParallelFlux<T> doAfterTerminate(Runnable runnable) {
        Objects.requireNonNull(runnable, "afterTerminate");
        return doOnSignal(this, null, null, null, null, runnable, null, null, null);
    }

    public final ParallelFlux<T> doOnCancel(Runnable runnable) {
        Objects.requireNonNull(runnable, "onCancel");
        return doOnSignal(this, null, null, null, null, null, null, null, runnable);
    }

    public final ParallelFlux<T> doOnComplete(Runnable runnable) {
        Objects.requireNonNull(runnable, "onComplete");
        return doOnSignal(this, null, null, null, runnable, null, null, null, null);
    }

    public final ParallelFlux<T> doOnEach(final Consumer<? super Signal<T>> consumer) {
        Objects.requireNonNull(consumer, "signalConsumer");
        return onAssembly(new ParallelDoOnEach(this, new BiConsumer() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                consumer.accept(Signal.next(obj2, (Context) obj));
            }
        }, new BiConsumer() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                consumer.accept(Signal.error((Throwable) obj2, (Context) obj));
            }
        }, new Consumer() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                consumer.accept(Signal.complete((Context) obj));
            }
        }));
    }

    public final ParallelFlux<T> doOnError(Consumer<? super Throwable> consumer) {
        Objects.requireNonNull(consumer, "onError");
        return doOnSignal(this, null, null, consumer, null, null, null, null, null);
    }

    public final ParallelFlux<T> doOnSubscribe(Consumer<? super Subscription> consumer) {
        Objects.requireNonNull(consumer, "onSubscribe");
        return doOnSignal(this, null, null, null, null, null, consumer, null, null);
    }

    public final ParallelFlux<T> doOnNext(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "onNext");
        return doOnSignal(this, consumer, null, null, null, null, null, null, null);
    }

    public final ParallelFlux<T> doOnRequest(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer, "onRequest");
        return doOnSignal(this, null, null, null, null, null, null, longConsumer, null);
    }

    public final ParallelFlux<T> doOnTerminate(final Runnable runnable) {
        Objects.requireNonNull(runnable, "onTerminate");
        return doOnSignal(this, null, null, new Consumer() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                runnable.run();
            }
        }, runnable, null, null, null, null);
    }

    public final ParallelFlux<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        return onAssembly(new ParallelFilter(this, predicate));
    }

    public final <R> ParallelFlux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function) {
        return flatMap(function, false, Integer.MAX_VALUE, Queues.SMALL_BUFFER_SIZE);
    }

    public final <R> ParallelFlux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function, boolean z) {
        return flatMap(function, z, Integer.MAX_VALUE, Queues.SMALL_BUFFER_SIZE);
    }

    public final <R> ParallelFlux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function, boolean z, int i) {
        return flatMap(function, z, i, Queues.SMALL_BUFFER_SIZE);
    }

    public final <R> ParallelFlux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> function, boolean z, int i, int i2) {
        return onAssembly(new ParallelFlatMap(this, function, z, i, Queues.get(i), i2, Queues.get(i2)));
    }

    public final Flux<GroupedFlux<Integer, T>> groups() {
        return Flux.onAssembly(new ParallelGroup(this));
    }

    public final ParallelFlux<T> hide() {
        return new ParallelFluxHide(this);
    }

    public final ParallelFlux<T> log() {
        return log(null, Level.INFO, new SignalType[0]);
    }

    public final ParallelFlux<T> log(@Nullable String str) {
        return log(str, Level.INFO, new SignalType[0]);
    }

    public final ParallelFlux<T> log(@Nullable String str, Level level, SignalType... signalTypeArr) {
        return log(str, level, false, signalTypeArr);
    }

    public final ParallelFlux<T> log(@Nullable String str, Level level, boolean z, SignalType... signalTypeArr) {
        return onAssembly(new ParallelLog(this, new SignalLogger(this, str, level, z, signalTypeArr)));
    }

    public final <U> ParallelFlux<U> map(Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function, "mapper");
        return onAssembly(new ParallelMap(this, function));
    }

    public final ParallelFlux<T> name(String str) {
        return ParallelFluxName.createOrAppend(this, str);
    }

    public final Flux<T> ordered(Comparator<? super T> comparator) {
        return ordered(comparator, Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> ordered(Comparator<? super T> comparator, int i) {
        return new ParallelMergeOrdered(this, i, comparator);
    }

    public final Mono<T> reduce(BiFunction<T, T, T> biFunction) {
        Objects.requireNonNull(biFunction, "reducer");
        return Mono.onAssembly(new ParallelMergeReduce(this, biFunction));
    }

    public final <R> ParallelFlux<R> reduce(Supplier<R> supplier, BiFunction<R, ? super T, R> biFunction) {
        Objects.requireNonNull(supplier, "initialSupplier");
        Objects.requireNonNull(biFunction, "reducer");
        return onAssembly(new ParallelReduceSeed(this, supplier, biFunction));
    }

    public final ParallelFlux<T> runOn(Scheduler scheduler) {
        return runOn(scheduler, Queues.SMALL_BUFFER_SIZE);
    }

    public final ParallelFlux<T> runOn(Scheduler scheduler, int i) {
        Objects.requireNonNull(scheduler, "scheduler");
        return onAssembly(new ParallelRunOn(this, scheduler, i, Queues.get(i)));
    }

    public final Flux<T> sequential() {
        return sequential(Queues.SMALL_BUFFER_SIZE);
    }

    public final Flux<T> sequential(int i) {
        return Flux.onAssembly(new ParallelMergeSequential(this, i, Queues.get(i)));
    }

    public final Flux<T> sorted(Comparator<? super T> comparator) {
        return sorted(comparator, 16);
    }

    public final Flux<T> sorted(final Comparator<? super T> comparator, int i) {
        final int iParallelism = (i / parallelism()) + 1;
        return Flux.onAssembly(new ParallelMergeSort(reduce(new Supplier() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                return ParallelFlux.lambda$sorted$8(iParallelism);
            }
        }, new BiFunction() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ParallelFlux.lambda$sorted$9((List) obj, obj2);
            }
        }).map(new Function() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ParallelFlux.lambda$sorted$10(comparator, (List) obj);
            }
        }), comparator));
    }

    static /* synthetic */ List lambda$sorted$8(int i) {
        return new ArrayList(i);
    }

    static /* synthetic */ List lambda$sorted$9(List list, Object obj) {
        list.add(obj);
        return list;
    }

    static /* synthetic */ List lambda$sorted$10(Comparator comparator, List list) {
        list.sort(comparator);
        return list;
    }

    public final Disposable subscribe() {
        return subscribe(null, null, null);
    }

    public final Disposable subscribe(Consumer<? super T> consumer) {
        return subscribe(consumer, null, null);
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, Consumer<? super Throwable> consumer2) {
        return subscribe(consumer, consumer2, null);
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable Runnable runnable) {
        return subscribe(consumer, consumer2, runnable, null, null);
    }

    @Override // reactor.core.CorePublisher
    public final void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        sequential().subscribe((CoreSubscriber) Operators.toCoreSubscriber(new FluxHide.SuppressFuseableSubscriber(Operators.toCoreSubscriber(coreSubscriber))));
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable Runnable runnable, @Nullable Consumer<? super Subscription> consumer3) {
        return subscribe(consumer, consumer2, runnable, consumer3, null);
    }

    public final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable Runnable runnable, @Nullable Context context) {
        return subscribe(consumer, consumer2, runnable, null, context);
    }

    final Disposable subscribe(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> consumer2, @Nullable Runnable runnable, @Nullable Consumer<? super Subscription> consumer3, @Nullable Context context) {
        CorePublisher corePublisherOnLastAssembly = Operators.onLastAssembly(this);
        if (corePublisherOnLastAssembly instanceof ParallelFlux) {
            int iParallelism = parallelism();
            LambdaSubscriber[] lambdaSubscriberArr = new LambdaSubscriber[iParallelism];
            for (int i = 0; i < iParallelism; i++) {
                lambdaSubscriberArr[i] = new LambdaSubscriber(consumer, consumer2, runnable, consumer3, context);
            }
            ((ParallelFlux) corePublisherOnLastAssembly).subscribe(lambdaSubscriberArr);
            return Disposables.composite(lambdaSubscriberArr);
        }
        LambdaSubscriber lambdaSubscriber = new LambdaSubscriber(consumer, consumer2, runnable, consumer3, context);
        corePublisherOnLastAssembly.subscribe((CoreSubscriber) Operators.toCoreSubscriber(new FluxHide.SuppressFuseableSubscriber(lambdaSubscriber)));
        return lambdaSubscriber;
    }

    @Override // org.reactivestreams.Publisher
    public final void subscribe(Subscriber<? super T> subscriber) {
        Operators.onLastAssembly(sequential()).subscribe((CoreSubscriber) Operators.toCoreSubscriber(new FluxHide.SuppressFuseableSubscriber(Operators.toCoreSubscriber(subscriber))));
    }

    public final ParallelFlux<T> tag(String str, String str2) {
        return ParallelFluxName.createOrAppend(this, str, str2);
    }

    public final Mono<Void> then() {
        return Mono.onAssembly(new ParallelThen(this));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <U> ParallelFlux<U> transform(Function<? super ParallelFlux<T>, ParallelFlux<U>> function) {
        return onAssembly((ParallelFlux) m1973as(function));
    }

    public final <U> ParallelFlux<U> transformGroups(final Function<? super GroupedFlux<Integer, T>, ? extends Publisher<? extends U>> function) {
        if (getPrefetch() > -1) {
            Flux<GroupedFlux<Integer, T>> fluxGroups = groups();
            Objects.requireNonNull(function);
            return from(fluxGroups.flatMap(new Function() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (Publisher) function.apply((GroupedFlux) obj);
                }
            }), parallelism(), getPrefetch(), Queues.small());
        }
        Flux<GroupedFlux<Integer, T>> fluxGroups2 = groups();
        Objects.requireNonNull(function);
        return from(fluxGroups2.flatMap(new Function() { // from class: reactor.core.publisher.ParallelFlux$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (Publisher) function.apply((GroupedFlux) obj);
            }
        }), parallelism());
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    protected final boolean validate(Subscriber<?>[] subscriberArr) {
        int iParallelism = parallelism();
        if (subscriberArr.length == iParallelism) {
            return true;
        }
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("parallelism = " + iParallelism + ", subscribers = " + subscriberArr.length);
        for (Subscriber<?> subscriber : subscriberArr) {
            Operators.error(subscriber, illegalArgumentException);
        }
        return false;
    }

    final <R> ParallelFlux<R> concatMap(Function<? super T, ? extends Publisher<? extends R>> function, int i, FluxConcatMap.ErrorMode errorMode) {
        return onAssembly(new ParallelConcatMap(this, function, Queues.get(i), i, errorMode));
    }

    final <R> ParallelFlux<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> function, boolean z, int i) {
        return concatMap(function, i, z ? FluxConcatMap.ErrorMode.END : FluxConcatMap.ErrorMode.BOUNDARY);
    }

    final <R> ParallelFlux<R> concatMapDelayError(Function<? super T, ? extends Publisher<? extends R>> function, int i) {
        return concatMap(function, i, FluxConcatMap.ErrorMode.END);
    }

    protected static <T> ParallelFlux<T> onAssembly(ParallelFlux<T> parallelFlux) {
        Function<Publisher, Publisher> function = Hooks.onEachOperatorHook;
        if (function != null) {
            parallelFlux = (ParallelFlux) function.apply(parallelFlux);
        }
        return Hooks.GLOBAL_TRACE ? (ParallelFlux) Hooks.addAssemblyInfo(parallelFlux, new FluxOnAssembly.AssemblySnapshot(null, Traces.callSiteSupplierFactory.get())) : parallelFlux;
    }

    static <T> ParallelFlux<T> doOnSignal(ParallelFlux<T> parallelFlux, @Nullable Consumer<? super T> consumer, @Nullable Consumer<? super T> consumer2, @Nullable Consumer<? super Throwable> consumer3, @Nullable Runnable runnable, @Nullable Runnable runnable2, @Nullable Consumer<? super Subscription> consumer4, @Nullable LongConsumer longConsumer, @Nullable Runnable runnable3) {
        return onAssembly(new ParallelPeek(parallelFlux, consumer, consumer2, consumer3, runnable, runnable2, consumer4, longConsumer, runnable3));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0037, code lost:
    
        r3 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <T> java.util.List<T> sortedMerger(java.util.List<T> r5, java.util.List<T> r6, java.util.Comparator<? super T> r7) {
        /*
            int r0 = r5.size()
            int r1 = r6.size()
            int r0 = r0 + r1
            if (r0 != 0) goto L11
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            return r5
        L11:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>(r0)
            java.util.Iterator r5 = r5.iterator()
            java.util.Iterator r6 = r6.iterator()
            boolean r0 = r5.hasNext()
            r2 = 0
            if (r0 == 0) goto L2a
            java.lang.Object r0 = r5.next()
            goto L2b
        L2a:
            r0 = r2
        L2b:
            boolean r3 = r6.hasNext()
            if (r3 == 0) goto L36
            java.lang.Object r3 = r6.next()
            goto L37
        L36:
            r3 = r2
        L37:
            if (r0 == 0) goto L5f
            if (r3 == 0) goto L5f
            int r4 = r7.compare(r0, r3)
            if (r4 >= 0) goto L51
            r1.add(r0)
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L4f
            java.lang.Object r0 = r5.next()
            goto L37
        L4f:
            r0 = r2
            goto L37
        L51:
            r1.add(r3)
            boolean r3 = r6.hasNext()
            if (r3 == 0) goto L36
            java.lang.Object r3 = r6.next()
            goto L37
        L5f:
            if (r0 == 0) goto L72
            r1.add(r0)
        L64:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L85
            java.lang.Object r6 = r5.next()
            r1.add(r6)
            goto L64
        L72:
            if (r3 == 0) goto L85
            r1.add(r3)
        L77:
            boolean r5 = r6.hasNext()
            if (r5 == 0) goto L85
            java.lang.Object r5 = r6.next()
            r1.add(r5)
            goto L77
        L85:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.ParallelFlux.sortedMerger(java.util.List, java.util.List, java.util.Comparator):java.util.List");
    }
}
