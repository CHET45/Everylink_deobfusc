package reactor.core.publisher;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxZip<T, R> extends Flux<R> implements SourceProducer<R> {
    final int prefetch;
    final Supplier<? extends Queue<T>> queueSupplier;
    final Publisher<? extends T>[] sources;
    final Iterable<? extends Publisher<? extends T>> sourcesIterable;
    final Function<? super Object[], ? extends R> zipper;

    <U> FluxZip(Publisher<? extends T> publisher, Publisher<? extends U> publisher2, BiFunction<? super T, ? super U, ? extends R> biFunction, Supplier<? extends Queue<T>> supplier, int i) {
        this(new Publisher[]{(Publisher) Objects.requireNonNull(publisher, "p1"), (Publisher) Objects.requireNonNull(publisher2, "p2")}, new PairwiseZipper(new BiFunction[]{(BiFunction) Objects.requireNonNull(biFunction, "zipper2")}), supplier, i);
    }

    FluxZip(Publisher<? extends T>[] publisherArr, Function<? super Object[], ? extends R> function, Supplier<? extends Queue<T>> supplier, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.sources = (Publisher[]) Objects.requireNonNull(publisherArr, "sources");
        if (publisherArr.length == 0) {
            throw new IllegalArgumentException("at least one source is required");
        }
        this.sourcesIterable = null;
        this.zipper = (Function) Objects.requireNonNull(function, "zipper");
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
        this.prefetch = i;
    }

    FluxZip(Iterable<? extends Publisher<? extends T>> iterable, Function<? super Object[], ? extends R> function, Supplier<? extends Queue<T>> supplier, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.sources = null;
        this.sourcesIterable = (Iterable) Objects.requireNonNull(iterable, "sourcesIterable");
        this.zipper = (Function) Objects.requireNonNull(function, "zipper");
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
        this.prefetch = i;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    @Nullable
    FluxZip<T, R> zipAdditionalSource(Publisher publisher, BiFunction biFunction) {
        Publisher<? extends T>[] publisherArr = this.sources;
        if (publisherArr == null || !(this.zipper instanceof PairwiseZipper)) {
            return null;
        }
        int length = publisherArr.length;
        Publisher[] publisherArr2 = new Publisher[length + 1];
        System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
        publisherArr2[length] = publisher;
        return new FluxZip<>(publisherArr2, ((PairwiseZipper) this.zipper).then(biFunction), this.queueSupplier, this.prefetch);
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super R> coreSubscriber) {
        Publisher<? extends T>[] publisherArr = this.sources;
        try {
            if (publisherArr != null) {
                handleArrayMode(coreSubscriber, publisherArr);
            } else {
                handleIterableMode(coreSubscriber, this.sourcesIterable);
            }
        } catch (Throwable th) {
            Operators.reportThrowInSubscribe(coreSubscriber, th);
        }
    }

    void handleIterableMode(CoreSubscriber<? super R> coreSubscriber, Iterable<? extends Publisher<? extends T>> iterable) {
        Object[] objArr = new Object[8];
        Publisher<? extends T>[] publisherArr = new Publisher[8];
        int i = 0;
        int i2 = 0;
        for (Publisher<? extends T> publisher : iterable) {
            if (publisher == null) {
                Operators.error(coreSubscriber, Operators.onOperatorError(new NullPointerException("The sourcesIterable returned a null Publisher"), coreSubscriber.currentContext()));
                return;
            }
            if (publisher instanceof Callable) {
                try {
                    Object objCall = ((Callable) publisher).call();
                    if (objCall == null) {
                        Operators.complete(coreSubscriber);
                        return;
                    }
                    if (i == objArr.length) {
                        int i3 = (i >> 1) + i;
                        Object[] objArr2 = new Object[i3];
                        System.arraycopy(objArr, 0, objArr2, 0, i);
                        Publisher<? extends T>[] publisherArr2 = new Publisher[i3];
                        System.arraycopy(publisherArr, 0, publisherArr2, 0, i);
                        publisherArr = publisherArr2;
                        objArr = objArr2;
                    }
                    objArr[i] = objCall;
                    i2++;
                } catch (Throwable th) {
                    Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
                    return;
                }
            } else {
                if (i == publisherArr.length) {
                    int i4 = (i >> 1) + i;
                    Object[] objArr3 = new Object[i4];
                    System.arraycopy(objArr, 0, objArr3, 0, i);
                    Publisher<? extends T>[] publisherArr3 = new Publisher[i4];
                    System.arraycopy(publisherArr, 0, publisherArr3, 0, i);
                    publisherArr = publisherArr3;
                    objArr = objArr3;
                }
                publisherArr[i] = publisher;
            }
            i++;
        }
        if (i == 0) {
            Operators.complete(coreSubscriber);
        } else {
            handleBoth(coreSubscriber, publisherArr, i < objArr.length ? Arrays.copyOfRange(objArr, 0, i, objArr.getClass()) : objArr, i, i2);
        }
    }

    void handleArrayMode(CoreSubscriber<? super R> coreSubscriber, Publisher<? extends T>[] publisherArr) {
        int length = publisherArr.length;
        Object[] objArr = null;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            Publisher<? extends T> publisher = publisherArr[i2];
            if (publisher == null) {
                Operators.error(coreSubscriber, new NullPointerException("The sources contained a null Publisher"));
                return;
            }
            if (publisher instanceof Callable) {
                try {
                    Object objCall = ((Callable) publisher).call();
                    if (objCall == null) {
                        Operators.complete(coreSubscriber);
                        return;
                    }
                    if (objArr == null) {
                        objArr = new Object[length];
                    }
                    objArr[i2] = objCall;
                    i++;
                } catch (Throwable th) {
                    Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
                    return;
                }
            }
        }
        handleBoth(coreSubscriber, publisherArr, objArr, length, i);
    }

    void handleBoth(CoreSubscriber<? super R> coreSubscriber, Publisher<? extends T>[] publisherArr, @Nullable Object[] objArr, int i, int i2) {
        Operators.toFluxOrMono(publisherArr);
        if (i2 == 0 || objArr == null) {
            ZipCoordinator zipCoordinator = new ZipCoordinator(coreSubscriber, this.zipper, i, this.queueSupplier, this.prefetch);
            coreSubscriber.onSubscribe(zipCoordinator);
            zipCoordinator.subscribe(publisherArr, i);
        } else {
            if (i != i2) {
                ZipSingleCoordinator zipSingleCoordinator = new ZipSingleCoordinator(coreSubscriber, objArr, i, i2, this.zipper);
                coreSubscriber.onSubscribe(zipSingleCoordinator);
                zipSingleCoordinator.subscribe(i, publisherArr);
                return;
            }
            coreSubscriber.onSubscribe(new ZipScalarCoordinator(coreSubscriber, this.zipper, objArr));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.prefetch) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ZipScalarCoordinator<R> implements InnerProducer<R>, Fuseable, Fuseable.QueueSubscription<R> {
        static final AtomicIntegerFieldUpdater<ZipScalarCoordinator> STATE = AtomicIntegerFieldUpdater.newUpdater(ZipScalarCoordinator.class, "state");
        final CoreSubscriber<? super R> actual;
        boolean cancelled;
        boolean done;
        final Object[] scalars;
        volatile int state;
        final Function<? super Object[], ? extends R> zipper;

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return i & 1;
        }

        ZipScalarCoordinator(CoreSubscriber<? super R> coreSubscriber, Function<? super Object[], ? extends R> function, Object[] objArr) {
            this.actual = coreSubscriber;
            this.zipper = function;
            this.scalars = objArr;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.BUFFERED ? Integer.valueOf(this.scalars.length) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.done) {
                return;
            }
            this.done = true;
            if (this.state == 0 && STATE.compareAndSet(this, 0, 1)) {
                try {
                    this.actual.onNext(Objects.requireNonNull(this.zipper.apply(this.scalars), "The zipper returned a null value"));
                    this.actual.onComplete();
                } catch (Throwable th) {
                    CoreSubscriber<? super R> coreSubscriber = this.actual;
                    coreSubscriber.onError(Operators.onOperatorError(th, coreSubscriber.currentContext()));
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            if (this.state == 0) {
                if (STATE.compareAndSet(this, 0, 2)) {
                    Context contextCurrentContext = this.actual.currentContext();
                    for (Object obj : this.scalars) {
                        Operators.onDiscard(obj, contextCurrentContext);
                    }
                }
            }
        }

        @Override // java.util.Queue
        public R poll() {
            if (this.done) {
                return null;
            }
            this.done = true;
            return (R) Objects.requireNonNull(this.zipper.apply(this.scalars), "The zipper returned a null value");
        }

        @Override // java.util.Collection
        public int size() {
            return !this.done ? 1 : 0;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.done;
        }

        @Override // java.util.Collection
        public void clear() {
            if (this.done || this.cancelled) {
                return;
            }
            this.cancelled = true;
            Context contextCurrentContext = this.actual.currentContext();
            for (Object obj : this.scalars) {
                Operators.onDiscard(obj, contextCurrentContext);
            }
        }
    }

    static final class ZipSingleCoordinator<T, R> extends Operators.MonoSubscriber<R, R> {
        static final AtomicIntegerFieldUpdater<ZipSingleCoordinator> WIP = AtomicIntegerFieldUpdater.newUpdater(ZipSingleCoordinator.class, "wip");
        final Object[] scalars;
        final ZipSingleSubscriber<T>[] subscribers;
        volatile int wip;
        final Function<? super Object[], ? extends R> zipper;

        ZipSingleCoordinator(CoreSubscriber<? super R> coreSubscriber, Object[] objArr, int i, int i2, Function<? super Object[], ? extends R> function) {
            super(coreSubscriber);
            this.zipper = function;
            this.scalars = objArr;
            ZipSingleSubscriber<T>[] zipSingleSubscriberArr = new ZipSingleSubscriber[i];
            for (int i3 = 0; i3 < i; i3++) {
                if (objArr[i3] == null) {
                    zipSingleSubscriberArr[i3] = new ZipSingleSubscriber<>(this, i3);
                }
            }
            this.subscribers = zipSingleSubscriberArr;
            WIP.lazySet(this, i - i2);
        }

        void subscribe(int i, Publisher<? extends T>[] publisherArr) {
            ZipSingleSubscriber<T>[] zipSingleSubscriberArr = this.subscribers;
            for (int i2 = 0; i2 < i && this.wip > 0 && !isCancelled(); i2++) {
                ZipSingleSubscriber<T> zipSingleSubscriber = zipSingleSubscriberArr[i2];
                if (zipSingleSubscriber != null) {
                    try {
                        publisherArr[i2].subscribe(zipSingleSubscriber);
                    } catch (Throwable th) {
                        Operators.reportThrowInSubscribe(zipSingleSubscriber, th);
                    }
                }
            }
        }

        void next(T t, int i) {
            Object[] objArr = this.scalars;
            objArr[i] = t;
            int iDecrementAndGet = WIP.decrementAndGet(this);
            if (iDecrementAndGet != 0) {
                if (iDecrementAndGet < 0) {
                    Operators.onDiscard(t, this.actual.currentContext());
                }
            } else {
                try {
                    complete(Objects.requireNonNull(this.zipper.apply(objArr), "The zipper returned a null value"));
                } catch (Throwable th) {
                    this.actual.onError(Operators.onOperatorError(this, th, t, this.actual.currentContext()));
                }
            }
        }

        void error(Throwable th, int i) {
            if (WIP.getAndSet(this, 0) > 0) {
                cancelAll();
                this.actual.onError(th);
            } else {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        void complete(int i) {
            if (WIP.getAndSet(this, 0) > 0) {
                cancelAll();
                this.actual.onComplete();
            }
        }

        @Override // reactor.core.publisher.Operators.MonoSubscriber, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            if (WIP.getAndSet(this, 0) > 0) {
                cancelAll();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // reactor.core.publisher.Operators.MonoSubscriber
        protected void discard(R r) {
            if (r != 0) {
                if (r instanceof Iterable) {
                    Operators.onDiscardMultiple((Iterator<?>) ((Iterable) r).iterator(), true, this.actual.currentContext());
                } else if (r.getClass().isArray()) {
                    Operators.onDiscardMultiple(Arrays.asList((Object[]) r), this.actual.currentContext());
                } else {
                    Operators.onDiscard(r, this.actual.currentContext());
                }
            }
        }

        @Override // reactor.core.publisher.Operators.MonoSubscriber, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            z = false;
            boolean z = false;
            if (attr != Scannable.Attr.TERMINATED) {
                if (attr == Scannable.Attr.BUFFERED) {
                    return Integer.valueOf(this.wip > 0 ? this.scalars.length : 0);
                }
                return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            if (this.wip == 0 && !isCancelled()) {
                z = true;
            }
            return Boolean.valueOf(z);
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers);
        }

        void cancelAll() {
            for (ZipSingleSubscriber<T> zipSingleSubscriber : this.subscribers) {
                if (zipSingleSubscriber != null) {
                    zipSingleSubscriber.dispose();
                }
            }
            Operators.onDiscardMultiple(Arrays.asList(this.scalars), this.actual.currentContext());
        }
    }

    static final class ZipSingleSubscriber<T> implements InnerConsumer<T>, Disposable {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<ZipSingleSubscriber, Subscription> f2242S = AtomicReferenceFieldUpdater.newUpdater(ZipSingleSubscriber.class, Subscription.class, "s");
        boolean done;
        boolean hasFirstValue;
        final int index;
        final ZipSingleCoordinator<T, ?> parent;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2243s;

        ZipSingleSubscriber(ZipSingleCoordinator<T, ?> zipSingleCoordinator, int i) {
            this.parent = zipSingleCoordinator;
            this.index = i;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2243s;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done || this.hasFirstValue);
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2243s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.parent.scalars[this.index] != null ? 1 : 0);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2242S, this, subscription)) {
                this.f2243s = subscription;
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.parent.currentContext());
            }
            if (this.hasFirstValue) {
                Operators.onDiscard(t, this.parent.currentContext());
                return;
            }
            this.hasFirstValue = true;
            Operators.terminate(f2242S, this);
            this.parent.next(t, this.index);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.hasFirstValue || this.done) {
                Operators.onErrorDropped(th, this.parent.currentContext());
            } else {
                this.done = true;
                this.parent.error(th, this.index);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.hasFirstValue || this.done) {
                return;
            }
            this.done = true;
            this.parent.complete(this.index);
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            Operators.terminate(f2242S, this);
        }
    }

    static final class ZipCoordinator<T, R> implements InnerProducer<R> {
        final CoreSubscriber<? super R> actual;
        volatile boolean cancelled;
        final Object[] current;
        volatile Throwable error;
        volatile long requested;
        final ZipInner<T>[] subscribers;
        volatile int wip;
        final Function<? super Object[], ? extends R> zipper;
        static final AtomicIntegerFieldUpdater<ZipCoordinator> WIP = AtomicIntegerFieldUpdater.newUpdater(ZipCoordinator.class, "wip");
        static final AtomicLongFieldUpdater<ZipCoordinator> REQUESTED = AtomicLongFieldUpdater.newUpdater(ZipCoordinator.class, "requested");
        static final AtomicReferenceFieldUpdater<ZipCoordinator, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(ZipCoordinator.class, Throwable.class, "error");

        ZipCoordinator(CoreSubscriber<? super R> coreSubscriber, Function<? super Object[], ? extends R> function, int i, Supplier<? extends Queue<T>> supplier, int i2) {
            this.actual = coreSubscriber;
            this.zipper = function;
            ZipInner<T>[] zipInnerArr = new ZipInner[i];
            for (int i3 = 0; i3 < i; i3++) {
                zipInnerArr[i3] = new ZipInner<>(this, i2, i3, supplier);
            }
            this.current = new Object[i];
            this.subscribers = zipInnerArr;
        }

        void subscribe(Publisher<? extends T>[] publisherArr, int i) {
            ZipInner<T>[] zipInnerArr = this.subscribers;
            for (int i2 = 0; i2 < i && !this.cancelled && this.error == null; i2++) {
                ZipInner<T> zipInner = zipInnerArr[i2];
                try {
                    publisherArr[i2].subscribe(zipInner);
                } catch (Throwable th) {
                    Operators.reportThrowInSubscribe(zipInner, th);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
                drain(null, null);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            cancelAll();
            if (WIP.getAndIncrement(this) == 0) {
                discardAll(1);
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers);
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        void error(Throwable th, int i) {
            if (Exceptions.addThrowable(ERROR, this, th)) {
                drain(null, null);
            } else {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }

        void cancelAll() {
            for (ZipInner<T> zipInner : this.subscribers) {
                zipInner.cancel();
            }
        }

        void discardAll(int i) {
            Context contextCurrentContext = this.actual.currentContext();
            Object[] objArr = this.current;
            Operators.onDiscardMultiple(Arrays.asList(objArr), contextCurrentContext);
            Arrays.fill(objArr, (Object) null);
            while (true) {
                for (ZipInner<T> zipInner : this.subscribers) {
                    Queue<T> queue = zipInner.queue;
                    int i2 = zipInner.sourceMode;
                    if (queue != null) {
                        if (i2 == 2) {
                            queue.clear();
                        } else {
                            Operators.onDiscardQueueWithClear(queue, contextCurrentContext, null);
                        }
                    }
                }
                int i3 = this.wip;
                if (i != i3) {
                    i = i3;
                } else if (WIP.compareAndSet(this, i, Integer.MIN_VALUE)) {
                    return;
                } else {
                    i = this.wip;
                }
            }
        }

        void drain(@Nullable ZipInner<T> zipInner, @Nullable Object obj) {
            int iAddWork = addWork(this);
            if (iAddWork != 0) {
                if (zipInner != null) {
                    if (zipInner.sourceMode == 2 && iAddWork == Integer.MIN_VALUE) {
                        zipInner.queue.clear();
                        return;
                    } else {
                        if (obj == null || !this.cancelled) {
                            return;
                        }
                        Operators.onDiscard(obj, this.actual.currentContext());
                        return;
                    }
                }
                return;
            }
            CoreSubscriber<? super R> coreSubscriber = this.actual;
            ZipInner<T>[] zipInnerArr = this.subscribers;
            int length = zipInnerArr.length;
            Object[] objArr = this.current;
            int iAddAndGet = 1;
            do {
                long j = this.requested;
                long j2 = 0;
                while (j != j2) {
                    if (this.cancelled) {
                        discardAll(iAddAndGet);
                        return;
                    }
                    if (this.error != null) {
                        cancelAll();
                        discardAll(iAddAndGet);
                        coreSubscriber.onError(Exceptions.terminate(ERROR, this));
                        return;
                    }
                    boolean z = false;
                    for (int i = 0; i < length; i++) {
                        ZipInner<T> zipInner2 = zipInnerArr[i];
                        if (objArr[i] == null) {
                            try {
                                boolean z2 = zipInner2.done;
                                Queue<T> queue = zipInner2.queue;
                                T tPoll = queue != null ? queue.poll() : null;
                                boolean z3 = tPoll == null;
                                if (z2 && z3) {
                                    cancelAll();
                                    discardAll(iAddAndGet);
                                    coreSubscriber.onComplete();
                                    return;
                                } else if (z3) {
                                    z = true;
                                } else {
                                    objArr[i] = tPoll;
                                }
                            } catch (Throwable th) {
                                Throwable thOnOperatorError = Operators.onOperatorError(th, this.actual.currentContext());
                                cancelAll();
                                discardAll(iAddAndGet);
                                AtomicReferenceFieldUpdater<ZipCoordinator, Throwable> atomicReferenceFieldUpdater = ERROR;
                                Exceptions.addThrowable(atomicReferenceFieldUpdater, this, thOnOperatorError);
                                coreSubscriber.onError(Exceptions.terminate(atomicReferenceFieldUpdater, this));
                                return;
                            }
                        }
                    }
                    if (z) {
                        break;
                    }
                    try {
                        coreSubscriber.onNext((Object) Objects.requireNonNull(this.zipper.apply(objArr.clone()), "The zipper returned a null value"));
                        j2++;
                        Arrays.fill(objArr, (Object) null);
                    } catch (Throwable th2) {
                        Throwable thOnOperatorError2 = Operators.onOperatorError(null, th2, objArr.clone(), this.actual.currentContext());
                        cancelAll();
                        discardAll(iAddAndGet);
                        AtomicReferenceFieldUpdater<ZipCoordinator, Throwable> atomicReferenceFieldUpdater2 = ERROR;
                        Exceptions.addThrowable(atomicReferenceFieldUpdater2, this, thOnOperatorError2);
                        coreSubscriber.onError(Exceptions.terminate(atomicReferenceFieldUpdater2, this));
                        return;
                    }
                }
                if (j == j2) {
                    if (this.cancelled) {
                        return;
                    }
                    if (this.error != null) {
                        cancelAll();
                        discardAll(iAddAndGet);
                        coreSubscriber.onError(Exceptions.terminate(ERROR, this));
                        return;
                    }
                    for (int i2 = 0; i2 < length; i2++) {
                        ZipInner<T> zipInner3 = zipInnerArr[i2];
                        if (objArr[i2] == null) {
                            try {
                                boolean z4 = zipInner3.done;
                                Queue<T> queue2 = zipInner3.queue;
                                T tPoll2 = queue2 != null ? queue2.poll() : null;
                                boolean z5 = tPoll2 == null;
                                if (z4 && z5) {
                                    cancelAll();
                                    discardAll(iAddAndGet);
                                    coreSubscriber.onComplete();
                                    return;
                                } else if (!z5) {
                                    objArr[i2] = tPoll2;
                                }
                            } catch (Throwable th3) {
                                Throwable thOnOperatorError3 = Operators.onOperatorError(null, th3, objArr, this.actual.currentContext());
                                cancelAll();
                                discardAll(iAddAndGet);
                                AtomicReferenceFieldUpdater<ZipCoordinator, Throwable> atomicReferenceFieldUpdater3 = ERROR;
                                Exceptions.addThrowable(atomicReferenceFieldUpdater3, this, thOnOperatorError3);
                                coreSubscriber.onError(Exceptions.terminate(atomicReferenceFieldUpdater3, this));
                                return;
                            }
                        }
                    }
                }
                if (j2 != 0) {
                    for (ZipInner<T> zipInner4 : zipInnerArr) {
                        zipInner4.request(j2);
                    }
                    if (j != Long.MAX_VALUE) {
                        REQUESTED.addAndGet(this, -j2);
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
            } while (iAddAndGet != 0);
        }

        static int addWork(ZipCoordinator<?, ?> zipCoordinator) {
            int i;
            do {
                i = zipCoordinator.wip;
                if (i == Integer.MIN_VALUE) {
                    return Integer.MIN_VALUE;
                }
            } while (!WIP.compareAndSet(zipCoordinator, i, i + 1));
            return i;
        }
    }

    static final class ZipInner<T> implements InnerConsumer<T> {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<ZipInner, Subscription> f2240S = AtomicReferenceFieldUpdater.newUpdater(ZipInner.class, Subscription.class, "s");
        volatile boolean done;
        final int index;
        final int limit;
        final ZipCoordinator<T, ?> parent;
        final int prefetch;
        long produced;
        volatile Queue<T> queue;
        final Supplier<? extends Queue<T>> queueSupplier;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2241s;
        int sourceMode;

        ZipInner(ZipCoordinator<T, ?> zipCoordinator, int i, int i2, Supplier<? extends Queue<T>> supplier) {
            this.parent = zipCoordinator;
            this.prefetch = i;
            this.index = i2;
            this.queueSupplier = supplier;
            this.limit = Operators.unboundedOrLimit(i);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2240S, this, subscription)) {
                if (subscription instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription queueSubscription = (Fuseable.QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = 1;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.parent.drain(this, null);
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = 2;
                        this.queue = queueSubscription;
                    } else {
                        this.queue = this.queueSupplier.get();
                    }
                } else {
                    this.queue = this.queueSupplier.get();
                }
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
                this.parent.drain(this, null);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode != 2 && !this.queue.offer(t)) {
                Operators.onDiscard(t, currentContext());
                onError(Operators.onOperatorError(this.f2241s, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), currentContext()));
            } else {
                this.parent.drain(this, t);
            }
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.actual.currentContext();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, currentContext());
            } else {
                this.done = true;
                this.parent.error(th, this.index);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            this.parent.drain(this, null);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2241s;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2241s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.queue != null ? this.queue.size() : 0);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done && this.f2241s != Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.prefetch);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        void cancel() {
            Operators.terminate(f2240S, this);
        }

        void request(long j) {
            if (this.sourceMode != 1) {
                long j2 = this.produced + j;
                if (j2 >= this.limit) {
                    this.produced = 0L;
                    this.f2241s.request(j2);
                } else {
                    this.produced = j2;
                }
            }
        }
    }

    static final class PairwiseZipper<R> implements Function<Object[], R> {
        final BiFunction[] zippers;

        PairwiseZipper(BiFunction[] biFunctionArr) {
            this.zippers = biFunctionArr;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.function.Function
        public R apply(Object[] objArr) {
            int i = 1;
            T t = (R) this.zippers[0].apply(objArr[0], objArr[1]);
            while (true) {
                BiFunction[] biFunctionArr = this.zippers;
                if (i >= biFunctionArr.length) {
                    return (R) t;
                }
                BiFunction biFunction = biFunctionArr[i];
                i++;
                t = (R) biFunction.apply(t, objArr[i]);
            }
        }

        public PairwiseZipper then(BiFunction biFunction) {
            BiFunction[] biFunctionArr = this.zippers;
            int length = biFunctionArr.length;
            BiFunction[] biFunctionArr2 = new BiFunction[length + 1];
            System.arraycopy(biFunctionArr, 0, biFunctionArr2, 0, length);
            biFunctionArr2[length] = biFunction;
            return new PairwiseZipper(biFunctionArr2);
        }
    }
}
