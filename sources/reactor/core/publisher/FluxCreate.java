package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.FluxCreate;
import reactor.core.publisher.FluxSink;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

/* JADX INFO: loaded from: classes5.dex */
final class FluxCreate<T> extends Flux<T> implements SourceProducer<T> {
    final FluxSink.OverflowStrategy backpressure;
    final CreateMode createMode;
    final Consumer<? super FluxSink<T>> source;

    enum CreateMode {
        PUSH_ONLY,
        PUSH_PULL
    }

    FluxCreate(Consumer<? super FluxSink<T>> consumer, FluxSink.OverflowStrategy overflowStrategy, CreateMode createMode) {
        this.source = (Consumer) Objects.requireNonNull(consumer, "source");
        this.backpressure = (FluxSink.OverflowStrategy) Objects.requireNonNull(overflowStrategy, "backpressure");
        this.createMode = createMode;
    }

    /* JADX INFO: renamed from: reactor.core.publisher.FluxCreate$1 */
    static /* synthetic */ class C51461 {
        static final /* synthetic */ int[] $SwitchMap$reactor$core$publisher$FluxSink$OverflowStrategy;

        static {
            int[] iArr = new int[FluxSink.OverflowStrategy.values().length];
            $SwitchMap$reactor$core$publisher$FluxSink$OverflowStrategy = iArr;
            try {
                iArr[FluxSink.OverflowStrategy.IGNORE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$reactor$core$publisher$FluxSink$OverflowStrategy[FluxSink.OverflowStrategy.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$reactor$core$publisher$FluxSink$OverflowStrategy[FluxSink.OverflowStrategy.DROP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$reactor$core$publisher$FluxSink$OverflowStrategy[FluxSink.OverflowStrategy.LATEST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    static <T> BaseSink<T> createSink(CoreSubscriber<? super T> coreSubscriber, FluxSink.OverflowStrategy overflowStrategy) {
        int i = C51461.$SwitchMap$reactor$core$publisher$FluxSink$OverflowStrategy[overflowStrategy.ordinal()];
        if (i == 1) {
            return new IgnoreSink(coreSubscriber);
        }
        if (i == 2) {
            return new ErrorAsyncSink(coreSubscriber);
        }
        if (i == 3) {
            return new DropAsyncSink(coreSubscriber);
        }
        if (i == 4) {
            return new LatestAsyncSink(coreSubscriber);
        }
        return new BufferAsyncSink(coreSubscriber, Queues.SMALL_BUFFER_SIZE);
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        CoreSubscriber coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled = Operators.restoreContextOnSubscriberIfAutoCPEnabled(this, coreSubscriber);
        BaseSink baseSinkCreateSink = createSink(coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled, this.backpressure);
        coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled.onSubscribe(baseSinkCreateSink);
        try {
            this.source.accept(this.createMode == CreateMode.PUSH_PULL ? new SerializedFluxSink(baseSinkCreateSink) : baseSinkCreateSink);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            baseSinkCreateSink.error(Operators.onOperatorError(th, coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled.currentContext()));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
    }

    static final class SerializedFluxSink<T> implements FluxSink<T>, Scannable {
        static final AtomicReferenceFieldUpdater<SerializedFluxSink, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(SerializedFluxSink.class, Throwable.class, "error");
        static final AtomicIntegerFieldUpdater<SerializedFluxSink> WIP = AtomicIntegerFieldUpdater.newUpdater(SerializedFluxSink.class, "wip");
        volatile boolean done;
        volatile Throwable error;
        final Queue<T> mpscQueue = (Queue) Queues.unboundedMultiproducer().get();
        final BaseSink<T> sink;
        volatile int wip;

        SerializedFluxSink(BaseSink<T> baseSink) {
            this.sink = baseSink;
        }

        @Override // reactor.core.publisher.FluxSink
        @Deprecated
        public Context currentContext() {
            return this.sink.currentContext();
        }

        @Override // reactor.core.publisher.FluxSink
        public ContextView contextView() {
            return this.sink.contextView();
        }

        @Override // reactor.core.publisher.FluxSink
        public FluxSink<T> next(T t) {
            Objects.requireNonNull(t, "t is null in sink.next(t)");
            if (this.sink.isTerminated() || this.done) {
                Operators.onNextDropped(t, this.sink.currentContext());
                return this;
            }
            AtomicIntegerFieldUpdater<SerializedFluxSink> atomicIntegerFieldUpdater = WIP;
            if (atomicIntegerFieldUpdater.get(this) == 0 && atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                try {
                    this.sink.next(t);
                } catch (Throwable th) {
                    BaseSink<T> baseSink = this.sink;
                    Operators.onOperatorError(baseSink, th, t, baseSink.currentContext());
                }
                if (WIP.decrementAndGet(this) == 0) {
                    return this;
                }
            } else {
                this.mpscQueue.offer(t);
                if (atomicIntegerFieldUpdater.getAndIncrement(this) != 0) {
                    return this;
                }
            }
            drainLoop();
            return this;
        }

        @Override // reactor.core.publisher.FluxSink
        public void error(Throwable th) {
            Objects.requireNonNull(th, "t is null in sink.error(t)");
            if (this.sink.isTerminated() || this.done) {
                Operators.onOperatorError(th, this.sink.currentContext());
                return;
            }
            if (Exceptions.addThrowable(ERROR, this, th)) {
                this.done = true;
                drain();
            } else {
                Context contextCurrentContext = this.sink.currentContext();
                Operators.onDiscardQueueWithClear(this.mpscQueue, contextCurrentContext, null);
                Operators.onOperatorError(th, contextCurrentContext);
            }
        }

        @Override // reactor.core.publisher.FluxSink
        public void complete() {
            if (this.sink.isTerminated() || this.done) {
                return;
            }
            this.done = true;
            drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) == 0) {
                drainLoop();
            }
        }

        void drainLoop() {
            Context contextCurrentContext = this.sink.currentContext();
            BaseSink<T> baseSink = this.sink;
            Queue<T> queue = this.mpscQueue;
            while (true) {
                if (baseSink.isCancelled()) {
                    Operators.onDiscardQueueWithClear(queue, contextCurrentContext, null);
                    if (WIP.decrementAndGet(this) == 0) {
                        return;
                    }
                } else {
                    AtomicReferenceFieldUpdater<SerializedFluxSink, Throwable> atomicReferenceFieldUpdater = ERROR;
                    if (atomicReferenceFieldUpdater.get(this) != null) {
                        Operators.onDiscardQueueWithClear(queue, contextCurrentContext, null);
                        baseSink.error(Exceptions.terminate(atomicReferenceFieldUpdater, this));
                        return;
                    }
                    boolean z = this.done;
                    T tPoll = queue.poll();
                    boolean z2 = tPoll == null;
                    if (z && z2) {
                        baseSink.complete();
                        return;
                    } else if (!z2) {
                        try {
                            baseSink.next(tPoll);
                        } catch (Throwable th) {
                            Operators.onOperatorError(this.sink, th, tPoll, this.sink.currentContext());
                        }
                    } else if (WIP.decrementAndGet(this) == 0) {
                        return;
                    }
                }
            }
        }

        @Override // reactor.core.publisher.FluxSink
        public FluxSink<T> onRequest(LongConsumer longConsumer) {
            this.sink.onPushPullRequest(longConsumer);
            return this;
        }

        @Override // reactor.core.publisher.FluxSink
        public FluxSink<T> onCancel(Disposable disposable) {
            this.sink.onCancel(disposable);
            return this;
        }

        @Override // reactor.core.publisher.FluxSink
        public FluxSink<T> onDispose(Disposable disposable) {
            this.sink.onDispose(disposable);
            return this;
        }

        @Override // reactor.core.publisher.FluxSink
        public long requestedFromDownstream() {
            return this.sink.requestedFromDownstream();
        }

        @Override // reactor.core.publisher.FluxSink
        public boolean isCancelled() {
            return this.sink.isCancelled();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.mpscQueue.size());
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            return this.sink.scanUnsafe(attr);
        }

        public String toString() {
            return this.sink.toString();
        }
    }

    static class SerializeOnRequestSink<T> implements FluxSink<T>, Scannable {
        final BaseSink<T> baseSink;
        SerializedFluxSink<T> serializedSink;
        FluxSink<T> sink;

        SerializeOnRequestSink(BaseSink<T> baseSink) {
            this.baseSink = baseSink;
            this.sink = baseSink;
        }

        @Override // reactor.core.publisher.FluxSink
        @Deprecated
        public Context currentContext() {
            return this.sink.currentContext();
        }

        @Override // reactor.core.publisher.FluxSink
        public ContextView contextView() {
            return this.sink.contextView();
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            SerializedFluxSink<T> serializedFluxSink = this.serializedSink;
            return serializedFluxSink != null ? serializedFluxSink.scanUnsafe(attr) : this.baseSink.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.FluxSink
        public void complete() {
            this.sink.complete();
        }

        @Override // reactor.core.publisher.FluxSink
        public void error(Throwable th) {
            this.sink.error(th);
        }

        @Override // reactor.core.publisher.FluxSink
        public FluxSink<T> next(T t) {
            this.sink.next(t);
            SerializedFluxSink<T> serializedFluxSink = this.serializedSink;
            return serializedFluxSink == null ? this : serializedFluxSink;
        }

        @Override // reactor.core.publisher.FluxSink
        public long requestedFromDownstream() {
            return this.sink.requestedFromDownstream();
        }

        @Override // reactor.core.publisher.FluxSink
        public boolean isCancelled() {
            return this.sink.isCancelled();
        }

        @Override // reactor.core.publisher.FluxSink
        public FluxSink<T> onRequest(LongConsumer longConsumer) {
            if (this.serializedSink == null) {
                SerializedFluxSink<T> serializedFluxSink = new SerializedFluxSink<>(this.baseSink);
                this.serializedSink = serializedFluxSink;
                this.sink = serializedFluxSink;
            }
            return this.sink.onRequest(longConsumer);
        }

        @Override // reactor.core.publisher.FluxSink
        public FluxSink<T> onCancel(Disposable disposable) {
            this.sink.onCancel(disposable);
            return this.sink;
        }

        @Override // reactor.core.publisher.FluxSink
        public FluxSink<T> onDispose(Disposable disposable) {
            this.sink.onDispose(disposable);
            return this;
        }

        public String toString() {
            return this.baseSink.toString();
        }
    }

    static abstract class BaseSink<T> extends AtomicBoolean implements FluxSink<T>, InnerProducer<T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        volatile Disposable disposable;
        volatile LongConsumer requestConsumer;
        volatile long requested;
        static final Disposable TERMINATED = OperatorDisposables.DISPOSED;
        static final Disposable CANCELLED = Disposables.disposed();
        static final LongConsumer NOOP_CONSUMER = new LongConsumer() { // from class: reactor.core.publisher.FluxCreate$BaseSink$$ExternalSyntheticLambda0
            @Override // java.util.function.LongConsumer
            public final void accept(long j) {
                FluxCreate.BaseSink.lambda$static$0(j);
            }
        };
        static final AtomicReferenceFieldUpdater<BaseSink, Disposable> DISPOSABLE = AtomicReferenceFieldUpdater.newUpdater(BaseSink.class, Disposable.class, "disposable");
        static final AtomicLongFieldUpdater<BaseSink> REQUESTED = AtomicLongFieldUpdater.newUpdater(BaseSink.class, "requested");
        static final AtomicReferenceFieldUpdater<BaseSink, LongConsumer> REQUEST_CONSUMER = AtomicReferenceFieldUpdater.newUpdater(BaseSink.class, LongConsumer.class, "requestConsumer");

        static boolean hasRequestConsumer(long j) {
            return (j & Long.MIN_VALUE) == 0;
        }

        static /* synthetic */ void lambda$static$0(long j) {
        }

        void onCancel() {
        }

        void onRequestedFromDownstream() {
        }

        BaseSink(CoreSubscriber<? super T> coreSubscriber) {
            this.actual = coreSubscriber;
            this.ctx = coreSubscriber.currentContext();
            REQUESTED.lazySet(this, Long.MIN_VALUE);
        }

        @Override // reactor.core.publisher.FluxSink
        @Deprecated
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override // reactor.core.publisher.FluxSink
        public ContextView contextView() {
            return this.actual.currentContext();
        }

        @Override // reactor.core.publisher.FluxSink
        public void complete() {
            if (isTerminated()) {
                return;
            }
            try {
                this.actual.onComplete();
            } finally {
                disposeResource(false);
            }
        }

        @Override // reactor.core.publisher.FluxSink
        public void error(Throwable th) {
            if (isTerminated()) {
                Operators.onOperatorError(th, this.ctx);
                return;
            }
            try {
                this.actual.onError(th);
            } finally {
                disposeResource(false);
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            disposeResource(true);
            onCancel();
        }

        void disposeResource(boolean z) {
            Disposable disposable;
            Disposable andSet;
            Disposable disposable2 = z ? CANCELLED : TERMINATED;
            Disposable disposable3 = this.disposable;
            Disposable disposable4 = TERMINATED;
            if (disposable3 == disposable4 || disposable3 == (disposable = CANCELLED) || (andSet = DISPOSABLE.getAndSet(this, disposable2)) == null || andSet == disposable4 || andSet == disposable) {
                return;
            }
            if (z && (andSet instanceof SinkDisposable)) {
                ((SinkDisposable) andSet).cancel();
            }
            andSet.dispose();
        }

        @Override // reactor.core.publisher.FluxSink
        public long requestedFromDownstream() {
            return this.requested & Long.MAX_VALUE;
        }

        @Override // reactor.core.publisher.FluxSink
        public final boolean isCancelled() {
            return this.disposable == CANCELLED;
        }

        final boolean isTerminated() {
            return this.disposable == TERMINATED;
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (Operators.validate(j)) {
                if (hasRequestConsumer(addCap(this, j))) {
                    LongConsumer longConsumer = this.requestConsumer;
                    if (!isCancelled()) {
                        longConsumer.accept(j);
                    }
                }
                onRequestedFromDownstream();
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.FluxSink
        public FluxSink<T> onRequest(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer, "onRequest");
            onPushRequest(longConsumer);
            return this;
        }

        protected void onPushRequest(LongConsumer longConsumer) {
            if (!C0162xc40028dd.m5m(REQUEST_CONSUMER, this, null, NOOP_CONSUMER)) {
                throw new IllegalStateException("A consumer has already been assigned to consume requests");
            }
            longConsumer.accept(Long.MAX_VALUE);
        }

        protected void onPushPullRequest(LongConsumer longConsumer) {
            if (!C0162xc40028dd.m5m(REQUEST_CONSUMER, this, null, longConsumer)) {
                throw new IllegalStateException("A consumer has already been assigned to consume requests");
            }
            long jMarkRequestConsumerSet = markRequestConsumerSet(this);
            if (jMarkRequestConsumerSet > 0) {
                longConsumer.accept(jMarkRequestConsumerSet);
            }
        }

        @Override // reactor.core.publisher.FluxSink
        public final FluxSink<T> onCancel(Disposable disposable) {
            Objects.requireNonNull(disposable, "onCancel");
            if (!C0162xc40028dd.m5m(DISPOSABLE, this, null, new SinkDisposable(null, disposable))) {
                Disposable disposable2 = this.disposable;
                if (disposable2 == CANCELLED) {
                    disposable.dispose();
                } else if (disposable2 instanceof SinkDisposable) {
                    SinkDisposable sinkDisposable = (SinkDisposable) disposable2;
                    if (sinkDisposable.onCancel == null) {
                        sinkDisposable.onCancel = disposable;
                    } else {
                        disposable.dispose();
                    }
                }
            }
            return this;
        }

        @Override // reactor.core.publisher.FluxSink
        public final FluxSink<T> onDispose(Disposable disposable) {
            Objects.requireNonNull(disposable, "onDispose");
            if (!C0162xc40028dd.m5m(DISPOSABLE, this, null, new SinkDisposable(disposable, null))) {
                Disposable disposable2 = this.disposable;
                if (disposable2 == TERMINATED || disposable2 == CANCELLED) {
                    disposable.dispose();
                } else if (disposable2 instanceof SinkDisposable) {
                    SinkDisposable sinkDisposable = (SinkDisposable) disposable2;
                    if (sinkDisposable.disposable == null) {
                        sinkDisposable.disposable = disposable;
                    } else {
                        disposable.dispose();
                    }
                }
            }
            return this;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.disposable == TERMINATED);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.disposable == CANCELLED);
            }
            return attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(requestedFromDownstream()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
        }

        @Override // java.util.concurrent.atomic.AtomicBoolean
        public String toString() {
            return "FluxSink";
        }

        static <T> void produced(BaseSink<T> baseSink, long j) {
            long j2;
            long j3;
            do {
                j2 = baseSink.requested;
                j3 = j2 & Long.MAX_VALUE;
                if (j3 == 0 || j3 == Long.MAX_VALUE) {
                    return;
                }
            } while (!REQUESTED.compareAndSet(baseSink, j2, (Long.MIN_VALUE & j2) | Operators.subOrZero(j3, j)));
        }

        static <T> long addCap(BaseSink<T> baseSink, long j) {
            long j2;
            long j3;
            do {
                j2 = baseSink.requested;
                j3 = j2 & Long.MAX_VALUE;
                if (j3 == Long.MAX_VALUE) {
                    return j2;
                }
            } while (!REQUESTED.compareAndSet(baseSink, j2, Operators.addCap(j3, j) | (Long.MIN_VALUE & j2)));
            return j2;
        }

        static <T> long markRequestConsumerSet(BaseSink<T> baseSink) {
            long j;
            long j2;
            do {
                j = baseSink.requested;
                if (hasRequestConsumer(j)) {
                    return j;
                }
                j2 = j & Long.MAX_VALUE;
            } while (!REQUESTED.compareAndSet(baseSink, j, j2));
            return j2;
        }
    }

    static final class IgnoreSink<T> extends BaseSink<T> {
        IgnoreSink(CoreSubscriber<? super T> coreSubscriber) {
            super(coreSubscriber);
        }

        @Override // reactor.core.publisher.FluxSink
        public FluxSink<T> next(T t) {
            long j;
            long j2;
            if (isTerminated()) {
                Operators.onNextDropped(t, this.ctx);
                return this;
            }
            if (isCancelled()) {
                Operators.onDiscard(t, this.ctx);
                return this;
            }
            this.actual.onNext(t);
            do {
                j = this.requested;
                j2 = Long.MAX_VALUE & j;
                if (j2 == 0) {
                    break;
                }
            } while (!REQUESTED.compareAndSet(this, j, (Long.MIN_VALUE & j) | (j2 - 1)));
            return this;
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink, java.util.concurrent.atomic.AtomicBoolean
        public String toString() {
            return "FluxSink(" + FluxSink.OverflowStrategy.IGNORE + ")";
        }
    }

    static abstract class NoOverflowBaseAsyncSink<T> extends BaseSink<T> {
        abstract void onOverflow();

        NoOverflowBaseAsyncSink(CoreSubscriber<? super T> coreSubscriber) {
            super(coreSubscriber);
        }

        @Override // reactor.core.publisher.FluxSink
        public final FluxSink<T> next(T t) {
            if (isTerminated()) {
                Operators.onNextDropped(t, this.ctx);
                return this;
            }
            if (requestedFromDownstream() != 0) {
                this.actual.onNext(t);
                produced(this, 1L);
            } else {
                onOverflow();
                Operators.onDiscard(t, this.ctx);
            }
            return this;
        }
    }

    static final class DropAsyncSink<T> extends NoOverflowBaseAsyncSink<T> {
        @Override // reactor.core.publisher.FluxCreate.NoOverflowBaseAsyncSink
        void onOverflow() {
        }

        DropAsyncSink(CoreSubscriber<? super T> coreSubscriber) {
            super(coreSubscriber);
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink, java.util.concurrent.atomic.AtomicBoolean
        public String toString() {
            return "FluxSink(" + FluxSink.OverflowStrategy.DROP + ")";
        }
    }

    static final class ErrorAsyncSink<T> extends NoOverflowBaseAsyncSink<T> {
        ErrorAsyncSink(CoreSubscriber<? super T> coreSubscriber) {
            super(coreSubscriber);
        }

        @Override // reactor.core.publisher.FluxCreate.NoOverflowBaseAsyncSink
        void onOverflow() {
            error(Exceptions.failWithOverflow());
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink, java.util.concurrent.atomic.AtomicBoolean
        public String toString() {
            return "FluxSink(" + FluxSink.OverflowStrategy.ERROR + ")";
        }
    }

    static final class BufferAsyncSink<T> extends BaseSink<T> {
        static final AtomicIntegerFieldUpdater<BufferAsyncSink> WIP = AtomicIntegerFieldUpdater.newUpdater(BufferAsyncSink.class, "wip");
        volatile boolean done;
        Throwable error;
        final Queue<T> queue;
        volatile int wip;

        BufferAsyncSink(CoreSubscriber<? super T> coreSubscriber, int i) {
            super(coreSubscriber);
            this.queue = (Queue) Queues.unbounded(i).get();
        }

        @Override // reactor.core.publisher.FluxSink
        public FluxSink<T> next(T t) {
            this.queue.offer(t);
            drain();
            return this;
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink, reactor.core.publisher.FluxSink
        public void error(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink, reactor.core.publisher.FluxSink
        public void complete() {
            this.done = true;
            drain();
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink
        void onRequestedFromDownstream() {
            drain();
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink
        void onCancel() {
            drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            Queue<T> queue = this.queue;
            while (true) {
                long jRequestedFromDownstream = requestedFromDownstream();
                long j = 0;
                while (j != jRequestedFromDownstream) {
                    if (isCancelled()) {
                        Operators.onDiscardQueueWithClear(queue, this.ctx, null);
                        if (WIP.decrementAndGet(this) == 0) {
                            return;
                        }
                    } else {
                        boolean z = this.done;
                        T tPoll = queue.poll();
                        boolean z2 = tPoll == null;
                        if (z && z2) {
                            Throwable th = this.error;
                            if (th != null) {
                                super.error(th);
                                return;
                            } else {
                                super.complete();
                                return;
                            }
                        }
                        if (z2) {
                            break;
                        }
                        coreSubscriber.onNext(tPoll);
                        j++;
                    }
                }
                if (j == jRequestedFromDownstream) {
                    if (isCancelled()) {
                        Operators.onDiscardQueueWithClear(queue, this.ctx, null);
                        if (WIP.decrementAndGet(this) == 0) {
                            return;
                        }
                    } else {
                        boolean z3 = this.done;
                        boolean zIsEmpty = queue.isEmpty();
                        if (z3 && zIsEmpty) {
                            Throwable th2 = this.error;
                            if (th2 != null) {
                                super.error(th2);
                                return;
                            } else {
                                super.complete();
                                return;
                            }
                        }
                    }
                }
                if (j != 0) {
                    produced(this, j);
                }
                if (WIP.decrementAndGet(this) == 0) {
                    return;
                }
            }
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink, reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.queue.size());
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            return super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink, java.util.concurrent.atomic.AtomicBoolean
        public String toString() {
            return "FluxSink(" + FluxSink.OverflowStrategy.BUFFER + ")";
        }
    }

    static final class LatestAsyncSink<T> extends BaseSink<T> {
        static final AtomicIntegerFieldUpdater<LatestAsyncSink> WIP = AtomicIntegerFieldUpdater.newUpdater(LatestAsyncSink.class, "wip");
        volatile boolean done;
        Throwable error;
        final AtomicReference<T> queue;
        volatile int wip;

        LatestAsyncSink(CoreSubscriber<? super T> coreSubscriber) {
            super(coreSubscriber);
            this.queue = new AtomicReference<>();
        }

        @Override // reactor.core.publisher.FluxSink
        public FluxSink<T> next(T t) {
            Operators.onDiscard(this.queue.getAndSet(t), this.ctx);
            drain();
            return this;
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink, reactor.core.publisher.FluxSink
        public void error(Throwable th) {
            this.error = th;
            this.done = true;
            drain();
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink, reactor.core.publisher.FluxSink
        public void complete() {
            this.done = true;
            drain();
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink
        void onRequestedFromDownstream() {
            drain();
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink
        void onCancel() {
            drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            AtomicReference<T> atomicReference = this.queue;
            while (true) {
                long jRequestedFromDownstream = requestedFromDownstream();
                long j = 0;
                while (true) {
                    if (j == jRequestedFromDownstream) {
                        break;
                    }
                    if (isCancelled()) {
                        Operators.onDiscard(atomicReference.getAndSet(null), this.ctx);
                        if (WIP.decrementAndGet(this) == 0) {
                            return;
                        }
                    } else {
                        boolean z = this.done;
                        T andSet = atomicReference.getAndSet(null);
                        boolean z2 = andSet == null;
                        if (z && z2) {
                            Throwable th = this.error;
                            if (th != null) {
                                super.error(th);
                                return;
                            } else {
                                super.complete();
                                return;
                            }
                        }
                        if (z2) {
                            break;
                        }
                        coreSubscriber.onNext(andSet);
                        j++;
                    }
                }
                if (j == jRequestedFromDownstream) {
                    if (isCancelled()) {
                        Operators.onDiscard(atomicReference.getAndSet(null), this.ctx);
                        if (WIP.decrementAndGet(this) == 0) {
                            return;
                        }
                    } else {
                        boolean z3 = this.done;
                        boolean z4 = atomicReference.get() == null;
                        if (z3 && z4) {
                            Throwable th2 = this.error;
                            if (th2 != null) {
                                super.error(th2);
                                return;
                            } else {
                                super.complete();
                                return;
                            }
                        }
                    }
                }
                if (j != 0) {
                    produced(this, j);
                }
                if (WIP.decrementAndGet(this) == 0) {
                    return;
                }
            }
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink, reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.queue.get() == null ? 0 : 1);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            return super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.FluxCreate.BaseSink, java.util.concurrent.atomic.AtomicBoolean
        public String toString() {
            return "FluxSink(" + FluxSink.OverflowStrategy.LATEST + ")";
        }
    }

    static final class SinkDisposable implements Disposable {
        Disposable disposable;
        Disposable onCancel;

        SinkDisposable(@Nullable Disposable disposable, @Nullable Disposable disposable2) {
            this.disposable = disposable;
            this.onCancel = disposable2;
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            Disposable disposable = this.disposable;
            if (disposable != null) {
                disposable.dispose();
            }
        }

        public void cancel() {
            Disposable disposable = this.onCancel;
            if (disposable != null) {
                disposable.dispose();
            }
        }
    }
}
