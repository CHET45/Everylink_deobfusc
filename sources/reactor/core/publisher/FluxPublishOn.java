package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Supplier;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxPublishOn<T> extends InternalFluxOperator<T, T> implements Fuseable {
    final boolean delayError;
    final int lowTide;
    final int prefetch;
    final Supplier<? extends Queue<T>> queueSupplier;
    final Scheduler scheduler;

    FluxPublishOn(Flux<? extends T> flux, Scheduler scheduler, boolean z, int i, int i2, Supplier<? extends Queue<T>> supplier) {
        super(flux);
        if (i <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + i);
        }
        this.scheduler = (Scheduler) Objects.requireNonNull(scheduler, "scheduler");
        this.delayError = z;
        this.prefetch = i;
        this.lowTide = i2;
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.prefetch;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        Scheduler.Worker worker = (Scheduler.Worker) Objects.requireNonNull(this.scheduler.createWorker(), "The scheduler returned a null worker");
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            this.source.subscribe((CoreSubscriber<? super Object>) new PublishOnConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.scheduler, worker, this.delayError, this.prefetch, this.lowTide, this.queueSupplier));
            return null;
        }
        return new PublishOnSubscriber(coreSubscriber, this.scheduler, worker, this.delayError, this.prefetch, this.lowTide, this.queueSupplier);
    }

    static final class PublishOnSubscriber<T> implements Fuseable.QueueSubscription<T>, Runnable, InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        final boolean delayError;
        volatile int discardGuard;
        volatile boolean done;
        Throwable error;
        final int limit;
        boolean outputFused;
        final int prefetch;
        long produced;
        Queue<T> queue;
        final Supplier<? extends Queue<T>> queueSupplier;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2175s;
        final Scheduler scheduler;
        int sourceMode;
        volatile int wip;
        final Scheduler.Worker worker;
        static final AtomicIntegerFieldUpdater<PublishOnSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(PublishOnSubscriber.class, "wip");
        static final AtomicIntegerFieldUpdater<PublishOnSubscriber> DISCARD_GUARD = AtomicIntegerFieldUpdater.newUpdater(PublishOnSubscriber.class, "discardGuard");
        static final AtomicLongFieldUpdater<PublishOnSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(PublishOnSubscriber.class, "requested");

        PublishOnSubscriber(CoreSubscriber<? super T> coreSubscriber, Scheduler scheduler, Scheduler.Worker worker, boolean z, int i, int i2, Supplier<? extends Queue<T>> supplier) {
            this.actual = coreSubscriber;
            this.worker = worker;
            this.scheduler = scheduler;
            this.delayError = z;
            this.prefetch = i;
            this.queueSupplier = supplier;
            this.limit = Operators.unboundedOrLimit(i, i2);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2175s, subscription)) {
                this.f2175s = subscription;
                if (subscription instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription queueSubscription = (Fuseable.QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = 1;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = 2;
                        this.queue = queueSubscription;
                        this.actual.onSubscribe(this);
                        subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = this.queueSupplier.get();
                this.actual.onSubscribe(this);
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode == 2) {
                trySchedule(this, null, null);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            if (this.cancelled) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            if (!this.queue.offer(t)) {
                Operators.onDiscard(t, this.actual.currentContext());
                this.error = Operators.onOperatorError(this.f2175s, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), t, this.actual.currentContext());
                this.done = true;
            }
            trySchedule(this, null, t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.error = th;
            this.done = true;
            trySchedule(null, th, null);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            trySchedule(null, null, null);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
                trySchedule(this, null, null);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.f2175s.cancel();
            this.worker.dispose();
            if (WIP.getAndIncrement(this) == 0) {
                if (this.sourceMode == 2) {
                    this.queue.clear();
                } else {
                    if (this.outputFused) {
                        return;
                    }
                    Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                }
            }
        }

        void trySchedule(@Nullable Subscription subscription, @Nullable Throwable th, @Nullable Object obj) {
            if (WIP.getAndIncrement(this) != 0) {
                if (this.cancelled) {
                    if (this.sourceMode == 2) {
                        this.queue.clear();
                        return;
                    } else {
                        Operators.onDiscard(obj, this.actual.currentContext());
                        return;
                    }
                }
                return;
            }
            try {
                this.worker.schedule(this);
            } catch (RejectedExecutionException e) {
                if (this.sourceMode == 2) {
                    this.queue.clear();
                } else if (this.outputFused) {
                    clear();
                } else {
                    Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                }
                if (this.cancelled) {
                    Operators.onErrorDropped(e, this.actual.currentContext());
                } else {
                    CoreSubscriber<? super T> coreSubscriber = this.actual;
                    coreSubscriber.onError(Operators.onRejectedExecution(e, subscription, th, obj, coreSubscriber.currentContext()));
                }
            }
        }

        void runSync() {
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            Queue<T> queue = this.queue;
            long j = this.produced;
            int iAddAndGet = 1;
            while (true) {
                long j2 = this.requested;
                while (j != j2) {
                    try {
                        T tPoll = queue.poll();
                        if (this.cancelled) {
                            Operators.onDiscard(tPoll, this.actual.currentContext());
                            Operators.onDiscardQueueWithClear(queue, this.actual.currentContext(), null);
                            return;
                        } else if (tPoll == null) {
                            doComplete(coreSubscriber);
                            return;
                        } else {
                            coreSubscriber.onNext(tPoll);
                            j++;
                        }
                    } catch (Throwable th) {
                        doError(coreSubscriber, Operators.onOperatorError(this.f2175s, th, this.actual.currentContext()));
                        return;
                    }
                }
                if (this.cancelled) {
                    Operators.onDiscardQueueWithClear(queue, this.actual.currentContext(), null);
                    return;
                }
                if (queue.isEmpty()) {
                    doComplete(coreSubscriber);
                    return;
                }
                int i = this.wip;
                if (iAddAndGet == i) {
                    this.produced = j;
                    iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    iAddAndGet = i;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        void runAsync() {
            Subscriber<?> subscriber = this.actual;
            Queue<T> queue = this.queue;
            long j = this.produced;
            int iAddAndGet = 1;
            while (true) {
                long jAddAndGet = this.requested;
                while (j != jAddAndGet) {
                    boolean z = this.done;
                    try {
                        T tPoll = queue.poll();
                        boolean z2 = tPoll == 0;
                        if (checkTerminated(z, z2, subscriber, tPoll)) {
                            return;
                        }
                        if (z2) {
                            break;
                        }
                        subscriber.onNext(tPoll);
                        j++;
                        if (j == this.limit) {
                            if (jAddAndGet != Long.MAX_VALUE) {
                                jAddAndGet = REQUESTED.addAndGet(this, -j);
                            }
                            this.f2175s.request(j);
                            j = 0;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.f2175s.cancel();
                        if (this.sourceMode == 2) {
                            this.queue.clear();
                        } else {
                            Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                        }
                        doError(subscriber, Operators.onOperatorError(th, this.actual.currentContext()));
                        return;
                    }
                }
                if (j == jAddAndGet && checkTerminated(this.done, queue.isEmpty(), subscriber, null)) {
                    return;
                }
                int i = this.wip;
                if (iAddAndGet == i) {
                    this.produced = j;
                    iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    iAddAndGet = i;
                }
            }
        }

        void runBackfused() {
            int iAddAndGet = 1;
            while (!this.cancelled) {
                boolean z = this.done;
                this.actual.onNext(null);
                if (z) {
                    Throwable th = this.error;
                    if (th != null) {
                        doError(this.actual, th);
                        return;
                    } else {
                        doComplete(this.actual);
                        return;
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
            clear();
        }

        void doComplete(Subscriber<?> subscriber) {
            subscriber.onComplete();
            this.worker.dispose();
        }

        void doError(Subscriber<?> subscriber, Throwable th) {
            try {
                subscriber.onError(th);
            } finally {
                this.worker.dispose();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.outputFused) {
                runBackfused();
            } else if (this.sourceMode == 1) {
                runSync();
            } else {
                runAsync();
            }
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, @Nullable T t) {
            if (this.cancelled) {
                Operators.onDiscard(t, this.actual.currentContext());
                if (this.sourceMode == 2) {
                    this.queue.clear();
                } else {
                    Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                }
                return true;
            }
            if (!z) {
                return false;
            }
            if (this.delayError) {
                if (!z2) {
                    return false;
                }
                Throwable th = this.error;
                if (th != null) {
                    doError(subscriber, th);
                } else {
                    doComplete(subscriber);
                }
                return true;
            }
            Throwable th2 = this.error;
            if (th2 == null) {
                if (!z2) {
                    return false;
                }
                doComplete(subscriber);
                return true;
            }
            Operators.onDiscard(t, this.actual.currentContext());
            if (this.sourceMode == 2) {
                this.queue.clear();
            } else {
                Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
            }
            doError(subscriber, th2);
            return true;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.f2175s;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr != Scannable.Attr.BUFFERED) {
                return attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.DELAY_ERROR ? Boolean.valueOf(this.delayError) : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.prefetch) : attr == Scannable.Attr.RUN_ON ? this.worker : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
            }
            Queue<T> queue = this.queue;
            return Integer.valueOf(queue != null ? queue.size() : 0);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // java.util.Collection
        public void clear() {
            if (DISCARD_GUARD.getAndIncrement(this) != 0) {
                return;
            }
            int iAddAndGet = 1;
            while (true) {
                Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                int i = this.discardGuard;
                if (iAddAndGet == i) {
                    iAddAndGet = DISCARD_GUARD.addAndGet(this, -iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    iAddAndGet = i;
                }
            }
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            T tPoll = this.queue.poll();
            if (tPoll != null && this.sourceMode != 1) {
                long j = this.produced + 1;
                if (j == this.limit) {
                    this.produced = 0L;
                    this.f2175s.request(j);
                } else {
                    this.produced = j;
                }
            }
            return tPoll;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        @Override // java.util.Collection
        public int size() {
            return this.queue.size();
        }
    }

    static final class PublishOnConditionalSubscriber<T> implements Fuseable.QueueSubscription<T>, Runnable, InnerOperator<T, T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        volatile boolean cancelled;
        long consumed;
        final boolean delayError;
        volatile int discardGuard;
        volatile boolean done;
        Throwable error;
        final int limit;
        boolean outputFused;
        final int prefetch;
        long produced;
        Queue<T> queue;
        final Supplier<? extends Queue<T>> queueSupplier;
        volatile long requested;

        /* JADX INFO: renamed from: s */
        Subscription f2174s;
        final Scheduler scheduler;
        int sourceMode;
        volatile int wip;
        final Scheduler.Worker worker;
        static final AtomicIntegerFieldUpdater<PublishOnConditionalSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(PublishOnConditionalSubscriber.class, "wip");
        static final AtomicIntegerFieldUpdater<PublishOnConditionalSubscriber> DISCARD_GUARD = AtomicIntegerFieldUpdater.newUpdater(PublishOnConditionalSubscriber.class, "discardGuard");
        static final AtomicLongFieldUpdater<PublishOnConditionalSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(PublishOnConditionalSubscriber.class, "requested");

        PublishOnConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, Scheduler scheduler, Scheduler.Worker worker, boolean z, int i, int i2, Supplier<? extends Queue<T>> supplier) {
            this.actual = conditionalSubscriber;
            this.worker = worker;
            this.scheduler = scheduler;
            this.delayError = z;
            this.prefetch = i;
            this.queueSupplier = supplier;
            this.limit = Operators.unboundedOrLimit(i, i2);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2174s, subscription)) {
                this.f2174s = subscription;
                if (subscription instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription queueSubscription = (Fuseable.QueueSubscription) subscription;
                    int iRequestFusion = queueSubscription.requestFusion(7);
                    if (iRequestFusion == 1) {
                        this.sourceMode = 1;
                        this.queue = queueSubscription;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        return;
                    }
                    if (iRequestFusion == 2) {
                        this.sourceMode = 2;
                        this.queue = queueSubscription;
                        this.actual.onSubscribe(this);
                        subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = this.queueSupplier.get();
                this.actual.onSubscribe(this);
                subscription.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode == 2) {
                trySchedule(this, null, null);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            if (this.cancelled) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            if (!this.queue.offer(t)) {
                Operators.onDiscard(t, this.actual.currentContext());
                this.error = Operators.onOperatorError(this.f2174s, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), t, this.actual.currentContext());
                this.done = true;
            }
            trySchedule(this, null, t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.error = th;
            this.done = true;
            trySchedule(null, th, null);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            trySchedule(null, null, null);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
                trySchedule(this, null, null);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.f2174s.cancel();
            this.worker.dispose();
            if (WIP.getAndIncrement(this) == 0) {
                if (this.sourceMode == 2) {
                    this.queue.clear();
                } else {
                    if (this.outputFused) {
                        return;
                    }
                    Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                }
            }
        }

        void trySchedule(@Nullable Subscription subscription, @Nullable Throwable th, @Nullable Object obj) {
            if (WIP.getAndIncrement(this) != 0) {
                if (this.cancelled) {
                    if (this.sourceMode == 2) {
                        this.queue.clear();
                        return;
                    } else {
                        Operators.onDiscard(obj, this.actual.currentContext());
                        return;
                    }
                }
                return;
            }
            try {
                this.worker.schedule(this);
            } catch (RejectedExecutionException e) {
                if (this.sourceMode == 2) {
                    this.queue.clear();
                } else if (this.outputFused) {
                    clear();
                } else {
                    Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                }
                if (this.cancelled) {
                    Operators.onErrorDropped(e, this.actual.currentContext());
                } else {
                    Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber = this.actual;
                    conditionalSubscriber.onError(Operators.onRejectedExecution(e, subscription, th, obj, conditionalSubscriber.currentContext()));
                }
            }
        }

        void runSync() {
            Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber = this.actual;
            Queue<T> queue = this.queue;
            long j = this.produced;
            int iAddAndGet = 1;
            while (true) {
                long j2 = this.requested;
                while (j != j2) {
                    try {
                        T tPoll = queue.poll();
                        if (this.cancelled) {
                            Operators.onDiscard(tPoll, this.actual.currentContext());
                            Operators.onDiscardQueueWithClear(queue, this.actual.currentContext(), null);
                            return;
                        } else if (tPoll == null) {
                            doComplete(conditionalSubscriber);
                            return;
                        } else if (conditionalSubscriber.tryOnNext(tPoll)) {
                            j++;
                        }
                    } catch (Throwable th) {
                        doError(conditionalSubscriber, Operators.onOperatorError(this.f2174s, th, this.actual.currentContext()));
                        return;
                    }
                }
                if (this.cancelled) {
                    Operators.onDiscardQueueWithClear(queue, this.actual.currentContext(), null);
                    return;
                }
                if (queue.isEmpty()) {
                    doComplete(conditionalSubscriber);
                    return;
                }
                int i = this.wip;
                if (iAddAndGet == i) {
                    this.produced = j;
                    iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    iAddAndGet = i;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        void runAsync() {
            Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber = this.actual;
            Queue<T> queue = this.queue;
            long j = this.produced;
            long j2 = this.consumed;
            int iAddAndGet = 1;
            while (true) {
                long j3 = this.requested;
                while (j != j3) {
                    boolean z = this.done;
                    try {
                        T tPoll = queue.poll();
                        boolean z2 = tPoll == 0;
                        if (checkTerminated(z, z2, conditionalSubscriber, tPoll)) {
                            return;
                        }
                        if (z2) {
                            break;
                        }
                        if (conditionalSubscriber.tryOnNext(tPoll)) {
                            j++;
                        }
                        j2++;
                        if (j2 == this.limit) {
                            this.f2174s.request(j2);
                            j2 = 0;
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        this.f2174s.cancel();
                        queue.clear();
                        doError(conditionalSubscriber, Operators.onOperatorError(th, this.actual.currentContext()));
                        return;
                    }
                }
                if (j == j3 && checkTerminated(this.done, queue.isEmpty(), conditionalSubscriber, null)) {
                    return;
                }
                int i = this.wip;
                if (iAddAndGet == i) {
                    this.produced = j;
                    this.consumed = j2;
                    iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    iAddAndGet = i;
                }
            }
        }

        void runBackfused() {
            int iAddAndGet = 1;
            while (!this.cancelled) {
                boolean z = this.done;
                this.actual.onNext(null);
                if (z) {
                    Throwable th = this.error;
                    if (th != null) {
                        doError(this.actual, th);
                        return;
                    } else {
                        doComplete(this.actual);
                        return;
                    }
                }
                iAddAndGet = WIP.addAndGet(this, -iAddAndGet);
                if (iAddAndGet == 0) {
                    return;
                }
            }
            clear();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.outputFused) {
                runBackfused();
            } else if (this.sourceMode == 1) {
                runSync();
            } else {
                runAsync();
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(this.requested);
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.f2174s;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled);
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr != Scannable.Attr.BUFFERED) {
                return attr == Scannable.Attr.ERROR ? this.error : attr == Scannable.Attr.DELAY_ERROR ? Boolean.valueOf(this.delayError) : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(this.prefetch) : attr == Scannable.Attr.RUN_ON ? this.worker : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
            }
            Queue<T> queue = this.queue;
            return Integer.valueOf(queue != null ? queue.size() : 0);
        }

        void doComplete(Subscriber<?> subscriber) {
            subscriber.onComplete();
            this.worker.dispose();
        }

        void doError(Subscriber<?> subscriber, Throwable th) {
            try {
                subscriber.onError(th);
            } finally {
                this.worker.dispose();
            }
        }

        boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, @Nullable T t) {
            if (this.cancelled) {
                Operators.onDiscard(t, this.actual.currentContext());
                if (this.sourceMode == 2) {
                    this.queue.clear();
                } else {
                    Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                }
                return true;
            }
            if (!z) {
                return false;
            }
            if (this.delayError) {
                if (!z2) {
                    return false;
                }
                Throwable th = this.error;
                if (th != null) {
                    doError(subscriber, th);
                } else {
                    doComplete(subscriber);
                }
                return true;
            }
            Throwable th2 = this.error;
            if (th2 == null) {
                if (!z2) {
                    return false;
                }
                doComplete(subscriber);
                return true;
            }
            Operators.onDiscard(t, this.actual.currentContext());
            if (this.sourceMode == 2) {
                this.queue.clear();
            } else {
                Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
            }
            doError(subscriber, th2);
            return true;
        }

        @Override // java.util.Collection
        public void clear() {
            if (DISCARD_GUARD.getAndIncrement(this) != 0) {
                return;
            }
            int iAddAndGet = 1;
            while (true) {
                Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                int i = this.discardGuard;
                if (iAddAndGet == i) {
                    iAddAndGet = DISCARD_GUARD.addAndGet(this, -iAddAndGet);
                    if (iAddAndGet == 0) {
                        return;
                    }
                } else {
                    iAddAndGet = i;
                }
            }
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            T tPoll = this.queue.poll();
            if (tPoll != null && this.sourceMode != 1) {
                long j = this.consumed + 1;
                if (j == this.limit) {
                    this.consumed = 0L;
                    this.f2174s.request(j);
                } else {
                    this.consumed = j;
                }
            }
            return tPoll;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        @Override // java.util.Collection
        public int size() {
            return this.queue.size();
        }
    }
}
