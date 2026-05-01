package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxExpand<T> extends InternalFluxOperator<T, T> {
    final boolean breadthFirst;
    final int capacityHint;
    final Function<? super T, ? extends Publisher<? extends T>> expander;

    FluxExpand(Flux<T> flux, Function<? super T, ? extends Publisher<? extends T>> function, boolean z, int i) {
        super(flux);
        this.expander = function;
        this.breadthFirst = z;
        this.capacityHint = i;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (this.breadthFirst) {
            ExpandBreathSubscriber expandBreathSubscriber = new ExpandBreathSubscriber(coreSubscriber, this.expander, this.capacityHint);
            expandBreathSubscriber.queue.offer(this.source);
            coreSubscriber.onSubscribe(expandBreathSubscriber);
            expandBreathSubscriber.drainQueue();
            return null;
        }
        ExpandDepthSubscription expandDepthSubscription = new ExpandDepthSubscription(coreSubscriber, this.expander, this.capacityHint);
        expandDepthSubscription.source = this.source;
        coreSubscriber.onSubscribe(expandDepthSubscription);
        return null;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ExpandBreathSubscriber<T> extends Operators.MultiSubscriptionSubscriber<T, T> {
        static final AtomicIntegerFieldUpdater<ExpandBreathSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(ExpandBreathSubscriber.class, "wip");
        volatile boolean active;
        final Function<? super T, ? extends Publisher<? extends T>> expander;
        long produced;
        final Queue<Publisher<? extends T>> queue;
        volatile int wip;

        ExpandBreathSubscriber(CoreSubscriber<? super T> coreSubscriber, Function<? super T, ? extends Publisher<? extends T>> function, int i) {
            super(coreSubscriber);
            this.expander = function;
            this.queue = (Queue) Queues.unbounded(i).get();
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            set(subscription);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.produced++;
            this.actual.onNext((Object) t);
            try {
                this.queue.offer(Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.expander.apply(t), "The expander returned a null Publisher")));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                super.cancel();
                this.actual.onError(th);
                drainQueue();
            }
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            set(Operators.cancelledSubscription());
            super.cancel();
            this.actual.onError(th);
            drainQueue();
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            this.active = false;
            drainQueue();
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscription
        public void cancel() {
            super.cancel();
            drainQueue();
        }

        void drainQueue() {
            if (WIP.getAndIncrement(this) == 0) {
                do {
                    Queue<Publisher<? extends T>> queue = this.queue;
                    if (isCancelled()) {
                        queue.clear();
                    } else if (!this.active) {
                        if (queue.isEmpty()) {
                            set(Operators.cancelledSubscription());
                            super.cancel();
                            this.actual.onComplete();
                        } else {
                            Publisher<? extends T> publisherPoll = queue.poll();
                            long j = this.produced;
                            if (j != 0) {
                                this.produced = 0L;
                                produced(j);
                            }
                            this.active = true;
                            publisherPoll.subscribe(this);
                        }
                    }
                } while (WIP.decrementAndGet(this) != 0);
            }
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr != Scannable.Attr.BUFFERED) {
                return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
            }
            Queue<Publisher<? extends T>> queue = this.queue;
            return Integer.valueOf(queue != null ? queue.size() : 0);
        }
    }

    static final class ExpandDepthSubscription<T> implements InnerProducer<T> {
        volatile int active;
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        long consumed;
        volatile Object current;
        volatile Throwable error;
        final Function<? super T, ? extends Publisher<? extends T>> expander;
        volatile long requested;
        CorePublisher<? extends T> source;
        Deque<ExpandDepthSubscriber<T>> subscriptionStack;
        volatile int wip;
        static final AtomicReferenceFieldUpdater<ExpandDepthSubscription, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(ExpandDepthSubscription.class, Throwable.class, "error");
        static final AtomicIntegerFieldUpdater<ExpandDepthSubscription> ACTIVE = AtomicIntegerFieldUpdater.newUpdater(ExpandDepthSubscription.class, "active");
        static final AtomicLongFieldUpdater<ExpandDepthSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(ExpandDepthSubscription.class, "requested");
        static final AtomicReferenceFieldUpdater<ExpandDepthSubscription, Object> CURRENT = AtomicReferenceFieldUpdater.newUpdater(ExpandDepthSubscription.class, Object.class, "current");
        static final AtomicIntegerFieldUpdater<ExpandDepthSubscription> WIP = AtomicIntegerFieldUpdater.newUpdater(ExpandDepthSubscription.class, "wip");

        ExpandDepthSubscription(CoreSubscriber<? super T> coreSubscriber, Function<? super T, ? extends Publisher<? extends T>> function, int i) {
            this.actual = coreSubscriber;
            this.expander = function;
            this.subscriptionStack = new ArrayDeque(i);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j)) {
                Operators.addCap(REQUESTED, this, j);
                drainQueue();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            Deque<ExpandDepthSubscriber<T>> deque;
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            synchronized (this) {
                deque = this.subscriptionStack;
                this.subscriptionStack = null;
            }
            if (deque != null) {
                while (!deque.isEmpty()) {
                    deque.poll().dispose();
                }
            }
            Object andSet = CURRENT.getAndSet(this, this);
            if (andSet == this || andSet == null) {
                return;
            }
            ((ExpandDepthSubscriber) andSet).dispose();
        }

        @Nullable
        ExpandDepthSubscriber<T> pop() {
            ExpandDepthSubscriber<T> expandDepthSubscriberPollFirst;
            synchronized (this) {
                Deque<ExpandDepthSubscriber<T>> deque = this.subscriptionStack;
                expandDepthSubscriberPollFirst = deque != null ? deque.pollFirst() : null;
            }
            return expandDepthSubscriberPollFirst;
        }

        boolean push(ExpandDepthSubscriber<T> expandDepthSubscriber) {
            synchronized (this) {
                Deque<ExpandDepthSubscriber<T>> deque = this.subscriptionStack;
                if (deque == null) {
                    return false;
                }
                deque.offerFirst(expandDepthSubscriber);
                return true;
            }
        }

        boolean setCurrent(ExpandDepthSubscriber<T> expandDepthSubscriber) {
            AtomicReferenceFieldUpdater<ExpandDepthSubscription, Object> atomicReferenceFieldUpdater;
            Object obj;
            do {
                atomicReferenceFieldUpdater = CURRENT;
                obj = atomicReferenceFieldUpdater.get(this);
                if (obj == this) {
                    expandDepthSubscriber.dispose();
                    return false;
                }
            } while (!C0162xc40028dd.m5m(atomicReferenceFieldUpdater, this, obj, expandDepthSubscriber));
            return true;
        }

        /* JADX WARN: Code restructure failed: missing block: B:57:0x00d5, code lost:
        
            r12.source = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x00d7, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void drainQueue() {
            /*
                Method dump skipped, instruction units count: 216
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: reactor.core.publisher.FluxExpand.ExpandDepthSubscription.drainQueue():void");
        }

        void innerNext() {
            drainQueue();
        }

        void innerError(ExpandDepthSubscriber expandDepthSubscriber, Throwable th) {
            Exceptions.addThrowable(ERROR, this, th);
            expandDepthSubscriber.done = true;
            drainQueue();
        }

        void innerComplete(ExpandDepthSubscriber expandDepthSubscriber) {
            expandDepthSubscriber.done = true;
            drainQueue();
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.ERROR ? this.error : super.scanUnsafe(attr);
        }
    }

    static final class ExpandDepthSubscriber<T> implements InnerConsumer<T> {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<ExpandDepthSubscriber, Subscription> f2124S = AtomicReferenceFieldUpdater.newUpdater(ExpandDepthSubscriber.class, Subscription.class, "s");
        volatile boolean done;
        ExpandDepthSubscription<T> parent;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2125s;
        volatile T value;

        ExpandDepthSubscriber(ExpandDepthSubscription<T> expandDepthSubscription) {
            this.parent = expandDepthSubscription;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2124S, this, subscription)) {
                subscription.request(1L);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.f2125s != Operators.cancelledSubscription()) {
                this.value = t;
                this.parent.innerNext();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.f2125s != Operators.cancelledSubscription()) {
                this.parent.innerError(this, th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.f2125s != Operators.cancelledSubscription()) {
                this.parent.innerComplete(this);
            }
        }

        void requestOne() {
            this.f2125s.request(1L);
        }

        void dispose() {
            Operators.terminate(f2124S, this);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2125s;
            }
            if (attr == Scannable.Attr.ACTUAL) {
                return this.parent.actual;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.parent.actual().currentContext();
        }
    }
}
