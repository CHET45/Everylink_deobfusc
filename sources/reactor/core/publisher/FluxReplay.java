package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxReplay<T> extends ConnectableFlux<T> implements Scannable, Fuseable, OptimizableOperator<T, T> {
    static final AtomicReferenceFieldUpdater<FluxReplay, ReplaySubscriber> CONNECTION = AtomicReferenceFieldUpdater.newUpdater(FluxReplay.class, ReplaySubscriber.class, "connection");
    volatile ReplaySubscriber<T> connection;
    final int history;

    @Nullable
    final OptimizableOperator<?, T> optimizableOperator;
    final Scheduler scheduler;
    final CorePublisher<T> source;
    final long ttl;

    interface ReplayBuffer<T> {
        void add(T t);

        int capacity();

        void clear(ReplaySubscription<T> replaySubscription);

        @Nullable
        Throwable getError();

        boolean isDone();

        boolean isEmpty(ReplaySubscription<T> replaySubscription);

        boolean isExpired();

        void onComplete();

        void onError(Throwable th);

        @Nullable
        T poll(ReplaySubscription<T> replaySubscription);

        void replay(ReplaySubscription<T> replaySubscription);

        int size();

        int size(ReplaySubscription<T> replaySubscription);
    }

    interface ReplaySubscription<T> extends Fuseable.QueueSubscription<T>, InnerProducer<T> {
        CoreSubscriber<? super T> actual();

        boolean enter();

        int fusionMode();

        int index();

        void index(int i);

        boolean isCancelled();

        int leave(int i);

        @Nullable
        Object node();

        void node(@Nullable Object obj);

        void produced(long j);

        void requestMore(int i);

        long requested();

        int tailIndex();

        void tailIndex(int i);
    }

    static final class SizeAndTimeBoundReplayBuffer<T> implements ReplayBuffer<T> {
        static final long NOT_DONE = Long.MIN_VALUE;
        volatile long done = Long.MIN_VALUE;
        Throwable error;
        volatile TimedNode<T> head;
        final int indexUpdateLimit;
        final int limit;
        final long maxAge;
        final Scheduler scheduler;
        int size;
        TimedNode<T> tail;

        static final class TimedNode<T> extends AtomicReference<TimedNode<T>> {
            final int index;
            final long time;
            final T value;

            TimedNode(int i, @Nullable T t, long j) {
                this.index = i;
                this.value = t;
                this.time = j;
            }

            @Override // java.util.concurrent.atomic.AtomicReference
            public String toString() {
                return "TimedNode{index=" + this.index + ", value=" + this.value + ", time=" + this.time + '}';
            }
        }

        SizeAndTimeBoundReplayBuffer(int i, long j, Scheduler scheduler) {
            this.limit = i;
            this.indexUpdateLimit = Operators.unboundedOrLimit(i);
            this.maxAge = j;
            this.scheduler = scheduler;
            TimedNode<T> timedNode = new TimedNode<>(-1, null, 0L);
            this.tail = timedNode;
            this.head = timedNode;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public boolean isExpired() {
            long j = this.done;
            return j != Long.MIN_VALUE && this.scheduler.now(TimeUnit.NANOSECONDS) - this.maxAge > j;
        }

        void replayNormal(ReplaySubscription<T> replaySubscription) {
            CoreSubscriber<? super T> coreSubscriberActual = replaySubscription.actual();
            int iLeave = 1;
            do {
                TimedNode<T> timedNode = (TimedNode) replaySubscription.node();
                if (timedNode == null) {
                    timedNode = this.head;
                    if (this.done == Long.MIN_VALUE) {
                        long jNow = this.scheduler.now(TimeUnit.NANOSECONDS) - this.maxAge;
                        TimedNode<T> timedNode2 = timedNode;
                        while (timedNode != null && timedNode.time <= jNow) {
                            timedNode2 = timedNode;
                            timedNode = timedNode.get();
                        }
                        timedNode = timedNode2;
                    }
                }
                long jRequested = replaySubscription.requested();
                long j = 0;
                while (j != jRequested) {
                    if (replaySubscription.isCancelled()) {
                        replaySubscription.node(null);
                        return;
                    }
                    boolean z = this.done != Long.MIN_VALUE;
                    TimedNode<T> timedNode3 = timedNode.get();
                    boolean z2 = timedNode3 == null;
                    if (z && z2) {
                        replaySubscription.node(null);
                        Throwable th = this.error;
                        if (th != null) {
                            coreSubscriberActual.onError(th);
                            return;
                        } else {
                            coreSubscriberActual.onComplete();
                            return;
                        }
                    }
                    if (z2) {
                        break;
                    }
                    coreSubscriberActual.onNext(timedNode3.value);
                    j++;
                    if ((timedNode3.index + 1) % this.indexUpdateLimit == 0) {
                        replaySubscription.requestMore(timedNode3.index + 1);
                    }
                    timedNode = timedNode3;
                }
                if (j == jRequested) {
                    if (replaySubscription.isCancelled()) {
                        replaySubscription.node(null);
                        return;
                    }
                    boolean z3 = this.done != Long.MIN_VALUE;
                    boolean z4 = timedNode.get() == null;
                    if (z3 && z4) {
                        replaySubscription.node(null);
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            coreSubscriberActual.onError(th2);
                            return;
                        } else {
                            coreSubscriberActual.onComplete();
                            return;
                        }
                    }
                }
                if (j != 0 && jRequested != Long.MAX_VALUE) {
                    replaySubscription.produced(j);
                }
                replaySubscription.node(timedNode);
                iLeave = replaySubscription.leave(iLeave);
            } while (iLeave != 0);
        }

        void replayFused(ReplaySubscription<T> replaySubscription) {
            CoreSubscriber<? super T> coreSubscriberActual = replaySubscription.actual();
            int iLeave = 1;
            while (!replaySubscription.isCancelled()) {
                boolean z = this.done != Long.MIN_VALUE;
                coreSubscriberActual.onNext(null);
                if (z) {
                    Throwable th = this.error;
                    if (th != null) {
                        coreSubscriberActual.onError(th);
                        return;
                    } else {
                        coreSubscriberActual.onComplete();
                        return;
                    }
                }
                iLeave = replaySubscription.leave(iLeave);
                if (iLeave == 0) {
                    return;
                }
            }
            replaySubscription.node(null);
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void onError(Throwable th) {
            this.done = this.scheduler.now(TimeUnit.NANOSECONDS);
            this.error = th;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        @Nullable
        public Throwable getError() {
            return this.error;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void onComplete() {
            this.done = this.scheduler.now(TimeUnit.NANOSECONDS);
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public boolean isDone() {
            return this.done != Long.MIN_VALUE;
        }

        TimedNode<T> latestHead(ReplaySubscription<T> replaySubscription) {
            long jNow = this.scheduler.now(TimeUnit.NANOSECONDS) - this.maxAge;
            TimedNode<T> timedNode = (TimedNode) replaySubscription.node();
            if (timedNode == null) {
                timedNode = this.head;
            }
            while (true) {
                TimedNode<T> timedNode2 = timedNode.get();
                if (timedNode2 == null || timedNode2.time > jNow) {
                    break;
                }
                timedNode = timedNode2;
            }
            return timedNode;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        @Nullable
        public T poll(ReplaySubscription<T> replaySubscription) {
            TimedNode<T> timedNode;
            TimedNode<T> timedNodeLatestHead = latestHead(replaySubscription);
            long jNow = this.scheduler.now(TimeUnit.NANOSECONDS) - this.maxAge;
            while (true) {
                timedNode = timedNodeLatestHead.get();
                if (timedNode == null) {
                    break;
                }
                if (timedNode.time > jNow) {
                    timedNodeLatestHead = timedNode;
                    break;
                }
                timedNodeLatestHead = timedNode;
            }
            if (timedNode == null) {
                if (timedNodeLatestHead.index == -1 || (timedNodeLatestHead.index + 1) % this.indexUpdateLimit != 0) {
                    return null;
                }
                replaySubscription.requestMore(timedNodeLatestHead.index + 1);
                return null;
            }
            replaySubscription.node(timedNode);
            if ((timedNode.index + 1) % this.indexUpdateLimit == 0) {
                replaySubscription.requestMore(timedNode.index + 1);
            }
            return timedNodeLatestHead.value;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void clear(ReplaySubscription<T> replaySubscription) {
            replaySubscription.node(null);
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public boolean isEmpty(ReplaySubscription<T> replaySubscription) {
            return latestHead(replaySubscription).get() == null;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public int size(ReplaySubscription<T> replaySubscription) {
            TimedNode<T> timedNodeLatestHead = latestHead(replaySubscription);
            int i = 0;
            while (true) {
                timedNodeLatestHead = timedNodeLatestHead.get();
                if (timedNodeLatestHead == null || i == Integer.MAX_VALUE) {
                    break;
                }
                i++;
            }
            return i;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public int size() {
            TimedNode<T> timedNode = this.head;
            int i = 0;
            while (true) {
                timedNode = timedNode.get();
                if (timedNode == null || i == Integer.MAX_VALUE) {
                    break;
                }
                i++;
            }
            return i;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public int capacity() {
            return this.limit;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void add(T t) {
            TimedNode<T> timedNode = this.tail;
            TimedNode<T> timedNode2 = new TimedNode<>(timedNode.index + 1, t, this.scheduler.now(TimeUnit.NANOSECONDS));
            timedNode.set(timedNode2);
            this.tail = timedNode2;
            int i = this.size;
            if (i == this.limit) {
                this.head = this.head.get();
            } else {
                this.size = i + 1;
            }
            long jNow = this.scheduler.now(TimeUnit.NANOSECONDS);
            long j = this.maxAge;
            long j2 = jNow - j;
            if (j == 0) {
                this.head = timedNode2;
                return;
            }
            TimedNode<T> timedNode3 = this.head;
            int i2 = 0;
            while (true) {
                TimedNode<T> timedNode4 = timedNode3.get();
                if (timedNode4 == null) {
                    return;
                }
                if (timedNode4.time > j2 || timedNode4 == timedNode2) {
                    break;
                }
                i2++;
                timedNode3 = timedNode4;
            }
            if (i2 != 0) {
                this.size -= i2;
                this.head = timedNode3;
            }
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void replay(ReplaySubscription<T> replaySubscription) {
            if (replaySubscription.enter()) {
                if (replaySubscription.fusionMode() == 0) {
                    replayNormal(replaySubscription);
                } else {
                    replayFused(replaySubscription);
                }
            }
        }
    }

    static final class UnboundedReplayBuffer<T> implements ReplayBuffer<T> {
        final int batchSize;
        volatile boolean done;
        Throwable error;
        final Object[] head;
        final int indexUpdateLimit;
        volatile int size;
        Object[] tail;
        int tailIndex;

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public int capacity() {
            return Integer.MAX_VALUE;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public boolean isExpired() {
            return false;
        }

        UnboundedReplayBuffer(int i) {
            this.batchSize = i;
            this.indexUpdateLimit = Operators.unboundedOrLimit(i);
            Object[] objArr = new Object[i + 1];
            this.tail = objArr;
            this.head = objArr;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        @Nullable
        public Throwable getError() {
            return this.error;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void add(T t) {
            int i = this.tailIndex;
            Object[] objArr = this.tail;
            if (i == objArr.length - 1) {
                Object[] objArr2 = new Object[objArr.length];
                objArr2[0] = t;
                this.tailIndex = 1;
                objArr[i] = objArr2;
                this.tail = objArr2;
            } else {
                objArr[i] = t;
                this.tailIndex = i + 1;
            }
            this.size++;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void onComplete() {
            this.done = true;
        }

        void replayNormal(ReplaySubscription<T> replaySubscription) {
            CoreSubscriber<? super T> coreSubscriberActual = replaySubscription.actual();
            int i = this.batchSize;
            int iLeave = 1;
            do {
                long jRequested = replaySubscription.requested();
                Object[] objArr = (Object[]) replaySubscription.node();
                if (objArr == null) {
                    objArr = this.head;
                }
                int iTailIndex = replaySubscription.tailIndex();
                int iIndex = replaySubscription.index();
                long j = 0;
                while (true) {
                    if (j == jRequested) {
                        break;
                    }
                    if (replaySubscription.isCancelled()) {
                        replaySubscription.node(null);
                        return;
                    }
                    boolean z = this.done;
                    boolean z2 = iIndex == this.size;
                    if (z && z2) {
                        replaySubscription.node(null);
                        Throwable th = this.error;
                        if (th != null) {
                            coreSubscriberActual.onError(th);
                            return;
                        } else {
                            coreSubscriberActual.onComplete();
                            return;
                        }
                    }
                    if (z2) {
                        break;
                    }
                    if (iTailIndex == i) {
                        objArr = (Object[]) objArr[iTailIndex];
                        iTailIndex = 0;
                    }
                    coreSubscriberActual.onNext(objArr[iTailIndex]);
                    j++;
                    iTailIndex++;
                    iIndex++;
                    if (iIndex % this.indexUpdateLimit == 0) {
                        replaySubscription.requestMore(iIndex);
                    }
                }
                if (j == jRequested) {
                    if (replaySubscription.isCancelled()) {
                        replaySubscription.node(null);
                        return;
                    }
                    boolean z3 = this.done;
                    boolean z4 = iIndex == this.size;
                    if (z3 && z4) {
                        replaySubscription.node(null);
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            coreSubscriberActual.onError(th2);
                            return;
                        } else {
                            coreSubscriberActual.onComplete();
                            return;
                        }
                    }
                }
                if (j != 0 && jRequested != Long.MAX_VALUE) {
                    replaySubscription.produced(j);
                }
                replaySubscription.index(iIndex);
                replaySubscription.tailIndex(iTailIndex);
                replaySubscription.node(objArr);
                iLeave = replaySubscription.leave(iLeave);
            } while (iLeave != 0);
        }

        void replayFused(ReplaySubscription<T> replaySubscription) {
            CoreSubscriber<? super T> coreSubscriberActual = replaySubscription.actual();
            int iLeave = 1;
            while (!replaySubscription.isCancelled()) {
                boolean z = this.done;
                coreSubscriberActual.onNext(null);
                if (z) {
                    Throwable th = this.error;
                    if (th != null) {
                        coreSubscriberActual.onError(th);
                        return;
                    } else {
                        coreSubscriberActual.onComplete();
                        return;
                    }
                }
                iLeave = replaySubscription.leave(iLeave);
                if (iLeave == 0) {
                    return;
                }
            }
            replaySubscription.node(null);
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void replay(ReplaySubscription<T> replaySubscription) {
            if (replaySubscription.enter()) {
                if (replaySubscription.fusionMode() == 0) {
                    replayNormal(replaySubscription);
                } else {
                    replayFused(replaySubscription);
                }
            }
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public boolean isDone() {
            return this.done;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        @Nullable
        public T poll(ReplaySubscription<T> replaySubscription) {
            int iIndex = replaySubscription.index();
            if (iIndex == this.size) {
                return null;
            }
            Object[] objArr = (Object[]) replaySubscription.node();
            if (objArr == null) {
                objArr = this.head;
                replaySubscription.node(objArr);
            }
            int iTailIndex = replaySubscription.tailIndex();
            if (iTailIndex == this.batchSize) {
                objArr = (Object[]) objArr[iTailIndex];
                replaySubscription.node(objArr);
                iTailIndex = 0;
            }
            T t = (T) objArr[iTailIndex];
            replaySubscription.tailIndex(iTailIndex + 1);
            int i = iIndex + 1;
            if (i % this.indexUpdateLimit == 0) {
                replaySubscription.requestMore(i);
            } else {
                replaySubscription.index(i);
            }
            return t;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void clear(ReplaySubscription<T> replaySubscription) {
            replaySubscription.node(null);
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public boolean isEmpty(ReplaySubscription<T> replaySubscription) {
            return replaySubscription.index() == this.size;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public int size(ReplaySubscription<T> replaySubscription) {
            return this.size - replaySubscription.index();
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public int size() {
            return this.size;
        }
    }

    static final class SizeBoundReplayBuffer<T> implements ReplayBuffer<T> {
        volatile boolean done;
        Throwable error;
        volatile Node<T> head;
        final int indexUpdateLimit;
        final int limit;
        int size;
        Node<T> tail;

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public boolean isExpired() {
            return false;
        }

        SizeBoundReplayBuffer(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Limit cannot be negative");
            }
            this.limit = i;
            this.indexUpdateLimit = Operators.unboundedOrLimit(i);
            Node<T> node = new Node<>(-1, null);
            this.tail = node;
            this.head = node;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public int capacity() {
            return this.limit;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void add(T t) {
            Node<T> node = this.tail;
            Node<T> node2 = new Node<>(node.index + 1, t);
            node.set(node2);
            this.tail = node2;
            int i = this.size;
            if (i == this.limit) {
                this.head = this.head.get();
            } else {
                this.size = i + 1;
            }
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void onComplete() {
            this.done = true;
        }

        void replayNormal(ReplaySubscription<T> replaySubscription) {
            CoreSubscriber<? super T> coreSubscriberActual = replaySubscription.actual();
            int iLeave = 1;
            do {
                long jRequested = replaySubscription.requested();
                Node<T> node = (Node) replaySubscription.node();
                if (node == null) {
                    node = this.head;
                }
                long j = 0;
                while (j != jRequested) {
                    if (replaySubscription.isCancelled()) {
                        replaySubscription.node(null);
                        return;
                    }
                    boolean z = this.done;
                    Node<T> node2 = node.get();
                    boolean z2 = node2 == null;
                    if (z && z2) {
                        replaySubscription.node(null);
                        Throwable th = this.error;
                        if (th != null) {
                            coreSubscriberActual.onError(th);
                            return;
                        } else {
                            coreSubscriberActual.onComplete();
                            return;
                        }
                    }
                    if (z2) {
                        break;
                    }
                    coreSubscriberActual.onNext(node2.value);
                    j++;
                    if ((node2.index + 1) % this.indexUpdateLimit == 0) {
                        replaySubscription.requestMore(node2.index + 1);
                    }
                    node = node2;
                }
                if (j == jRequested) {
                    if (replaySubscription.isCancelled()) {
                        replaySubscription.node(null);
                        return;
                    }
                    boolean z3 = this.done;
                    boolean z4 = node.get() == null;
                    if (z3 && z4) {
                        replaySubscription.node(null);
                        Throwable th2 = this.error;
                        if (th2 != null) {
                            coreSubscriberActual.onError(th2);
                            return;
                        } else {
                            coreSubscriberActual.onComplete();
                            return;
                        }
                    }
                }
                if (j != 0 && jRequested != Long.MAX_VALUE) {
                    replaySubscription.produced(j);
                }
                replaySubscription.node(node);
                iLeave = replaySubscription.leave(iLeave);
            } while (iLeave != 0);
        }

        void replayFused(ReplaySubscription<T> replaySubscription) {
            CoreSubscriber<? super T> coreSubscriberActual = replaySubscription.actual();
            int iLeave = 1;
            while (!replaySubscription.isCancelled()) {
                boolean z = this.done;
                coreSubscriberActual.onNext(null);
                if (z) {
                    Throwable th = this.error;
                    if (th != null) {
                        coreSubscriberActual.onError(th);
                        return;
                    } else {
                        coreSubscriberActual.onComplete();
                        return;
                    }
                }
                iLeave = replaySubscription.leave(iLeave);
                if (iLeave == 0) {
                    return;
                }
            }
            replaySubscription.node(null);
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void replay(ReplaySubscription<T> replaySubscription) {
            if (replaySubscription.enter()) {
                if (replaySubscription.fusionMode() == 0) {
                    replayNormal(replaySubscription);
                } else {
                    replayFused(replaySubscription);
                }
            }
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        @Nullable
        public Throwable getError() {
            return this.error;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public boolean isDone() {
            return this.done;
        }

        static final class Node<T> extends AtomicReference<Node<T>> {
            private static final long serialVersionUID = 3713592843205853725L;
            final int index;
            final T value;

            Node(int i, @Nullable T t) {
                this.index = i;
                this.value = t;
            }

            @Override // java.util.concurrent.atomic.AtomicReference
            public String toString() {
                return "Node(" + this.value + ")";
            }
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        @Nullable
        public T poll(ReplaySubscription<T> replaySubscription) {
            Node<T> node = (Node) replaySubscription.node();
            if (node == null) {
                node = this.head;
                replaySubscription.node(node);
            }
            Node<T> node2 = node.get();
            if (node2 == null) {
                return null;
            }
            replaySubscription.node(node2);
            if ((node2.index + 1) % this.indexUpdateLimit == 0) {
                replaySubscription.requestMore(node2.index + 1);
            }
            return node2.value;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public void clear(ReplaySubscription<T> replaySubscription) {
            replaySubscription.node(null);
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public boolean isEmpty(ReplaySubscription<T> replaySubscription) {
            Node<T> node = (Node) replaySubscription.node();
            if (node == null) {
                node = this.head;
                replaySubscription.node(node);
            }
            return node.get() == null;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public int size(ReplaySubscription<T> replaySubscription) {
            Node<T> node = (Node) replaySubscription.node();
            if (node == null) {
                node = this.head;
            }
            int i = 0;
            while (true) {
                node = node.get();
                if (node == null || i == Integer.MAX_VALUE) {
                    break;
                }
                i++;
            }
            return i;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplayBuffer
        public int size() {
            Node<T> node = this.head;
            int i = 0;
            while (true) {
                node = node.get();
                if (node == null || i == Integer.MAX_VALUE) {
                    break;
                }
                i++;
            }
            return i;
        }
    }

    FluxReplay(CorePublisher<T> corePublisher, int i, long j, @Nullable Scheduler scheduler) {
        this.source = Operators.toFluxOrMono((Publisher) Objects.requireNonNull(corePublisher, "source"));
        if (corePublisher instanceof OptimizableOperator) {
            this.optimizableOperator = (OptimizableOperator) corePublisher;
        } else {
            this.optimizableOperator = null;
        }
        if (i <= 0) {
            throw new IllegalArgumentException("History cannot be zero or negative : " + i);
        }
        this.history = i;
        if (scheduler != null && j < 0) {
            throw new IllegalArgumentException("TTL cannot be negative : " + j);
        }
        this.ttl = j;
        this.scheduler = scheduler;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.history;
    }

    ReplaySubscriber<T> newState() {
        if (this.scheduler != null) {
            return new ReplaySubscriber<>(new SizeAndTimeBoundReplayBuffer(this.history, this.ttl, this.scheduler), this, this.history);
        }
        if (this.history != Integer.MAX_VALUE) {
            return new ReplaySubscriber<>(new SizeBoundReplayBuffer(this.history), this, this.history);
        }
        return new ReplaySubscriber<>(new UnboundedReplayBuffer(Queues.SMALL_BUFFER_SIZE), this, Queues.SMALL_BUFFER_SIZE);
    }

    @Override // reactor.core.publisher.ConnectableFlux
    public void connect(Consumer<? super Disposable> consumer) {
        ReplaySubscriber<T> replaySubscriberNewState;
        do {
            replaySubscriberNewState = this.connection;
            if (replaySubscriberNewState != null) {
                break;
            } else {
                replaySubscriberNewState = newState();
            }
        } while (!C0162xc40028dd.m5m(CONNECTION, this, null, replaySubscriberNewState));
        boolean zTryConnect = replaySubscriberNewState.tryConnect();
        consumer.accept(replaySubscriberNewState);
        if (zTryConnect) {
            try {
                this.source.subscribe((CoreSubscriber) replaySubscriberNewState);
            } catch (Throwable th) {
                Operators.reportThrowInSubscribe(replaySubscriberNewState, th);
            }
        }
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        try {
            CoreSubscriber<? super T> coreSubscriberSubscribeOrReturn = subscribeOrReturn(coreSubscriber);
            if (coreSubscriberSubscribeOrReturn == null) {
                return;
            }
            this.source.subscribe((CoreSubscriber) coreSubscriberSubscribeOrReturn);
        } catch (Throwable th) {
            Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.OptimizableOperator
    public final CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) throws Throwable {
        ReplaySubscriber<T> replaySubscriber;
        boolean z;
        while (true) {
            replaySubscriber = this.connection;
            z = (this.scheduler == null || replaySubscriber == null || !replaySubscriber.buffer.isExpired()) ? false : true;
            if (replaySubscriber != null && !z) {
                break;
            }
            ReplaySubscriber<T> replaySubscriberNewState = newState();
            if (C0162xc40028dd.m5m(CONNECTION, this, replaySubscriber, replaySubscriberNewState)) {
                replaySubscriber = replaySubscriberNewState;
                break;
            }
        }
        ReplayInner<T> replayInner = new ReplayInner<>(coreSubscriber, replaySubscriber);
        coreSubscriber.onSubscribe(replayInner);
        replaySubscriber.add(replayInner);
        if (replayInner.isCancelled()) {
            replaySubscriber.remove(replayInner);
            return null;
        }
        replaySubscriber.buffer.replay(replayInner);
        if (z) {
            return replaySubscriber;
        }
        return null;
    }

    @Override // reactor.core.publisher.OptimizableOperator
    public final CorePublisher<? extends T> source() {
        return this.source;
    }

    @Override // reactor.core.publisher.OptimizableOperator
    public final OptimizableOperator<?, ? extends T> nextOptimizableSource() {
        return this.optimizableOperator;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    static final class ReplaySubscriber<T> implements InnerConsumer<T>, Disposable {
        static final long CONNECTED_FLAG = 1152921504606846976L;
        static final long DISPOSED_FLAG = Long.MIN_VALUE;
        static final long SUBSCRIBED_FLAG = 2305843009213693952L;
        static final long WORK_IN_PROGRESS_MAX_VALUE = 1152921504606846975L;
        final ReplayBuffer<T> buffer;
        final int limit;
        int nextPrefetchIndex;
        final FluxReplay<T> parent;
        final long prefetch;
        int produced;

        /* JADX INFO: renamed from: s */
        Subscription f2182s;
        volatile long state;
        volatile ReplaySubscription<T>[] subscribers = EMPTY;
        static final AtomicLongFieldUpdater<ReplaySubscriber> STATE = AtomicLongFieldUpdater.newUpdater(ReplaySubscriber.class, "state");
        static final ReplaySubscription[] EMPTY = new ReplaySubscription[0];
        static final ReplaySubscription[] TERMINATED = new ReplaySubscription[0];

        static boolean isConnected(long j) {
            return (j & 1152921504606846976L) == 1152921504606846976L;
        }

        static boolean isDisposed(long j) {
            return (j & Long.MIN_VALUE) == Long.MIN_VALUE;
        }

        static boolean isSubscribed(long j) {
            return (j & 2305843009213693952L) == 2305843009213693952L;
        }

        static boolean isWorkInProgress(long j) {
            return (j & WORK_IN_PROGRESS_MAX_VALUE) > 0;
        }

        ReplaySubscriber(ReplayBuffer<T> replayBuffer, FluxReplay<T> fluxReplay, int i) {
            this.buffer = replayBuffer;
            this.parent = fluxReplay;
            this.prefetch = Operators.unboundedOrPrefetch(i);
            int iUnboundedOrLimit = Operators.unboundedOrLimit(i);
            this.limit = iUnboundedOrLimit;
            this.nextPrefetchIndex = iUnboundedOrLimit;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (this.buffer.isDone()) {
                subscription.cancel();
                return;
            }
            if (Operators.validate(this.f2182s, subscription)) {
                this.f2182s = subscription;
                if (isDisposed(markSubscribed(this))) {
                    subscription.cancel();
                } else {
                    subscription.request(this.prefetch);
                }
            }
        }

        void manageRequest(long j) {
            Subscription subscription = this.f2182s;
            do {
                int i = this.nextPrefetchIndex;
                ReplaySubscription<T>[] replaySubscriptionArr = this.subscribers;
                if (replaySubscriptionArr.length <= 0) {
                    if (this.produced >= i) {
                        int i2 = this.limit;
                        this.nextPrefetchIndex = i + i2;
                        subscription.request(i2);
                    }
                } else {
                    for (ReplaySubscription<T> replaySubscription : replaySubscriptionArr) {
                        if (replaySubscription.index() < i) {
                            break;
                        }
                    }
                    int i22 = this.limit;
                    this.nextPrefetchIndex = i + i22;
                    subscription.request(i22);
                }
                j = markWorkDone(this, j);
                if (isDisposed(j)) {
                    return;
                }
            } while (isWorkInProgress(j));
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            ReplayBuffer<T> replayBuffer = this.buffer;
            if (replayBuffer.isDone()) {
                Operators.onNextDropped(t, currentContext());
                return;
            }
            this.produced++;
            replayBuffer.add(t);
            ReplaySubscription<T>[] replaySubscriptionArr = this.subscribers;
            if (replaySubscriptionArr.length == 0) {
                if (this.produced % this.limit == 0) {
                    long jMarkWorkAdded = markWorkAdded(this);
                    if (isDisposed(jMarkWorkAdded) || isWorkInProgress(jMarkWorkAdded)) {
                        return;
                    }
                    manageRequest(jMarkWorkAdded + 1);
                    return;
                }
                return;
            }
            for (ReplaySubscription<T> replaySubscription : replaySubscriptionArr) {
                replayBuffer.replay(replaySubscription);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            ReplayBuffer<T> replayBuffer = this.buffer;
            if (replayBuffer.isDone()) {
                Operators.onErrorDropped(th, currentContext());
                return;
            }
            replayBuffer.onError(th);
            for (ReplaySubscription<T> replaySubscription : terminate()) {
                replayBuffer.replay(replaySubscription);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            ReplayBuffer<T> replayBuffer = this.buffer;
            if (replayBuffer.isDone()) {
                return;
            }
            replayBuffer.onComplete();
            for (ReplaySubscription<T> replaySubscription : terminate()) {
                replayBuffer.replay(replaySubscription);
            }
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            C0162xc40028dd.m5m(FluxReplay.CONNECTION, this.parent, this, null);
            long jMarkDisposed = markDisposed(this);
            if (isDisposed(jMarkDisposed)) {
                return;
            }
            if (isSubscribed(jMarkDisposed)) {
                this.f2182s.cancel();
            }
            CancellationException cancellationException = new CancellationException("Disconnected");
            ReplayBuffer<T> replayBuffer = this.buffer;
            replayBuffer.onError(cancellationException);
            for (ReplaySubscription<T> replaySubscription : terminate()) {
                replayBuffer.replay(replaySubscription);
            }
        }

        boolean add(ReplayInner<T> replayInner) {
            ReplaySubscription<T>[] replaySubscriptionArr = this.subscribers;
            ReplaySubscription<T>[] replaySubscriptionArr2 = TERMINATED;
            if (replaySubscriptionArr == replaySubscriptionArr2) {
                return false;
            }
            synchronized (this) {
                ReplaySubscription<T>[] replaySubscriptionArr3 = this.subscribers;
                if (replaySubscriptionArr3 == replaySubscriptionArr2) {
                    return false;
                }
                int length = replaySubscriptionArr3.length;
                ReplayInner[] replayInnerArr = new ReplayInner[length + 1];
                System.arraycopy(replaySubscriptionArr3, 0, replayInnerArr, 0, length);
                replayInnerArr[length] = replayInner;
                this.subscribers = replayInnerArr;
                return true;
            }
        }

        void remove(ReplaySubscription<T> replaySubscription) {
            ReplaySubscription<T>[] replaySubscriptionArr;
            ReplaySubscription<T>[] replaySubscriptionArr2;
            ReplaySubscription<T>[] replaySubscriptionArr3 = this.subscribers;
            ReplaySubscription<T>[] replaySubscriptionArr4 = TERMINATED;
            if (replaySubscriptionArr3 == replaySubscriptionArr4 || replaySubscriptionArr3 == (replaySubscriptionArr = EMPTY)) {
                return;
            }
            synchronized (this) {
                ReplaySubscription<T>[] replaySubscriptionArr5 = this.subscribers;
                if (replaySubscriptionArr5 != replaySubscriptionArr4 && replaySubscriptionArr5 != replaySubscriptionArr) {
                    int length = replaySubscriptionArr5.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            i = -1;
                            break;
                        } else if (replaySubscriptionArr5[i] == replaySubscription) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (i < 0) {
                        return;
                    }
                    if (length == 1) {
                        replaySubscriptionArr2 = EMPTY;
                    } else {
                        ReplayInner[] replayInnerArr = new ReplayInner[length - 1];
                        System.arraycopy(replaySubscriptionArr5, 0, replayInnerArr, 0, i);
                        System.arraycopy(replaySubscriptionArr5, i + 1, replayInnerArr, i, (length - i) - 1);
                        replaySubscriptionArr2 = replayInnerArr;
                    }
                    this.subscribers = replaySubscriptionArr2;
                }
            }
        }

        ReplaySubscription<T>[] terminate() {
            ReplaySubscription<T>[] replaySubscriptionArr;
            ReplaySubscription<T>[] replaySubscriptionArr2 = this.subscribers;
            ReplaySubscription<T>[] replaySubscriptionArr3 = TERMINATED;
            if (replaySubscriptionArr2 == replaySubscriptionArr3) {
                return replaySubscriptionArr2;
            }
            synchronized (this) {
                replaySubscriptionArr = this.subscribers;
                if (replaySubscriptionArr != replaySubscriptionArr3) {
                    this.subscribers = replaySubscriptionArr3;
                }
            }
            return replaySubscriptionArr;
        }

        boolean isTerminated() {
            return this.subscribers == TERMINATED;
        }

        boolean tryConnect() {
            return markConnected(this);
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return Operators.multiSubscribersContext(this.subscribers);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2182s;
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (attr == Scannable.Attr.CAPACITY) {
                return Integer.valueOf(this.buffer.capacity());
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.buffer.getError();
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(this.buffer.size());
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(isTerminated());
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isDisposed());
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            return Stream.of((Object[]) this.subscribers);
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return isDisposed(this.state);
        }

        static boolean markConnected(ReplaySubscriber<?> replaySubscriber) {
            long j;
            do {
                j = replaySubscriber.state;
                if (isConnected(j)) {
                    return false;
                }
            } while (!STATE.compareAndSet(replaySubscriber, j, 1152921504606846976L | j));
            return true;
        }

        static long markSubscribed(ReplaySubscriber<?> replaySubscriber) {
            long j;
            do {
                j = replaySubscriber.state;
                if (isDisposed(j)) {
                    return j;
                }
            } while (!STATE.compareAndSet(replaySubscriber, j, j | 2305843009213693952L));
            return j;
        }

        static long markWorkAdded(ReplaySubscriber<?> replaySubscriber) {
            long j;
            do {
                j = replaySubscriber.state;
                if (isDisposed(j) || (j & WORK_IN_PROGRESS_MAX_VALUE) == WORK_IN_PROGRESS_MAX_VALUE) {
                    return j;
                }
            } while (!STATE.compareAndSet(replaySubscriber, j, j + 1));
            return j;
        }

        static long markWorkDone(ReplaySubscriber<?> replaySubscriber, long j) {
            long j2;
            long j3;
            do {
                j2 = replaySubscriber.state;
                if (j != j2) {
                    return j2;
                }
                j3 = j2 & (-1152921504606846976L);
            } while (!STATE.compareAndSet(replaySubscriber, j2, j3));
            return j3;
        }

        static long markDisposed(ReplaySubscriber<?> replaySubscriber) {
            long j;
            do {
                j = replaySubscriber.state;
                if (isDisposed(j)) {
                    return j;
                }
            } while (!STATE.compareAndSet(replaySubscriber, j, j | Long.MIN_VALUE));
            return j;
        }
    }

    static final class ReplayInner<T> implements ReplaySubscription<T> {
        final CoreSubscriber<? super T> actual;
        int fusionMode;
        int index;
        Object node;
        final ReplaySubscriber<T> parent;
        volatile long requested;
        int tailIndex;
        long totalRequested;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ReplayInner> WIP = AtomicIntegerFieldUpdater.newUpdater(ReplayInner.class, "wip");
        static final AtomicLongFieldUpdater<ReplayInner> REQUESTED = AtomicLongFieldUpdater.newUpdater(ReplayInner.class, "requested");

        ReplayInner(CoreSubscriber<? super T> coreSubscriber, ReplaySubscriber<T> replaySubscriber) {
            this.actual = coreSubscriber;
            this.parent = replaySubscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (!Operators.validate(j) || Operators.addCapCancellable(REQUESTED, this, j) == Long.MIN_VALUE) {
                return;
            }
            this.totalRequested = Operators.addCap(this.totalRequested, j);
            this.parent.buffer.replay(this);
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.parent.isTerminated());
            }
            if (attr == Scannable.Attr.BUFFERED) {
                return Integer.valueOf(size());
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isCancelled());
            }
            if (attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.valueOf(Math.max(0L, this.requested));
            }
            if (attr == Scannable.Attr.RUN_ON) {
                return this.parent.parent.scheduler;
            }
            return super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (REQUESTED.getAndSet(this, Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.remove(this);
                if (enter()) {
                    this.node = null;
                }
            }
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public long requested() {
            return this.requested;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public boolean isCancelled() {
            return this.requested == Long.MIN_VALUE;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription, reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if ((i & 2) == 0) {
                return 0;
            }
            this.fusionMode = 2;
            return 2;
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            return this.parent.buffer.poll(this);
        }

        @Override // java.util.Collection
        public void clear() {
            this.parent.buffer.clear(this);
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.parent.buffer.isEmpty(this);
        }

        @Override // java.util.Collection
        public int size() {
            return this.parent.buffer.size(this);
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public void node(@Nullable Object obj) {
            this.node = obj;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public int fusionMode() {
            return this.fusionMode;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        @Nullable
        public Object node() {
            return this.node;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public int index() {
            return this.index;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public void index(int i) {
            this.index = i;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public void requestMore(int i) {
            this.index = i;
            long jMarkWorkAdded = ReplaySubscriber.markWorkAdded(this.parent);
            if (ReplaySubscriber.isDisposed(jMarkWorkAdded) || ReplaySubscriber.isWorkInProgress(jMarkWorkAdded)) {
                return;
            }
            this.parent.manageRequest(jMarkWorkAdded + 1);
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public int tailIndex() {
            return this.tailIndex;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public void tailIndex(int i) {
            this.tailIndex = i;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public boolean enter() {
            return WIP.getAndIncrement(this) == 0;
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public int leave(int i) {
            return WIP.addAndGet(this, -i);
        }

        @Override // reactor.core.publisher.FluxReplay.ReplaySubscription
        public void produced(long j) {
            REQUESTED.addAndGet(this, -j);
        }
    }
}
