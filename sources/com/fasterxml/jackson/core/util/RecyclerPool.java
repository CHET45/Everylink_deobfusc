package com.fasterxml.jackson.core.util;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import com.fasterxml.jackson.core.util.RecyclerPool.WithPool;
import java.io.Serializable;
import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes3.dex */
public interface RecyclerPool<P extends WithPool<P>> extends Serializable {

    public interface WithPool<P extends WithPool<P>> {
        void releaseToPool();

        P withPool(RecyclerPool<P> recyclerPool);
    }

    P acquirePooled();

    default boolean clear() {
        return false;
    }

    default int pooledCount() {
        return -1;
    }

    void releasePooled(P p);

    default P acquireAndLinkPooled() {
        return (P) acquirePooled().withPool(this);
    }

    public static abstract class ThreadLocalPoolBase<P extends WithPool<P>> implements RecyclerPool<P> {
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public abstract P acquirePooled();

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public boolean clear() {
            return false;
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public int pooledCount() {
            return -1;
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public void releasePooled(P p) {
        }

        protected ThreadLocalPoolBase() {
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public P acquireAndLinkPooled() {
            return (P) acquirePooled();
        }
    }

    public static abstract class NonRecyclingPoolBase<P extends WithPool<P>> implements RecyclerPool<P> {
        private static final long serialVersionUID = 1;

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public abstract P acquirePooled();

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public boolean clear() {
            return true;
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public int pooledCount() {
            return 0;
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public void releasePooled(P p) {
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public P acquireAndLinkPooled() {
            return (P) acquirePooled();
        }
    }

    public static abstract class StatefulImplBase<P extends WithPool<P>> implements RecyclerPool<P> {
        public static final int SERIALIZATION_NON_SHARED = 1;
        public static final int SERIALIZATION_SHARED = -1;
        private static final long serialVersionUID = 1;
        protected final int _serialization;

        public abstract P createPooled();

        protected StatefulImplBase(int i) {
            this._serialization = i;
        }

        protected Optional<StatefulImplBase<P>> _resolveToShared(StatefulImplBase<P> statefulImplBase) {
            if (this._serialization == -1) {
                return Optional.of(statefulImplBase);
            }
            return Optional.empty();
        }
    }

    public static abstract class ConcurrentDequePoolBase<P extends WithPool<P>> extends StatefulImplBase<P> {
        private static final long serialVersionUID = 1;
        protected final transient Deque<P> pool;

        protected ConcurrentDequePoolBase(int i) {
            super(i);
            this.pool = new ConcurrentLinkedDeque();
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public P acquirePooled() {
            P pPollFirst = this.pool.pollFirst();
            return pPollFirst == null ? createPooled() : pPollFirst;
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public void releasePooled(P p) {
            this.pool.offerLast(p);
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public int pooledCount() {
            return this.pool.size();
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public boolean clear() {
            this.pool.clear();
            return true;
        }
    }

    @Deprecated
    public static abstract class LockFreePoolBase<P extends WithPool<P>> extends StatefulImplBase<P> {
        private static final long serialVersionUID = 1;
        private final transient AtomicReference<Node<P>> head;

        protected LockFreePoolBase(int i) {
            super(i);
            this.head = new AtomicReference<>();
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public P acquirePooled() {
            for (int i = 0; i < 3; i++) {
                Node<P> node = this.head.get();
                if (node == null) {
                    return createPooled();
                }
                if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m42m(this.head, node, node.next)) {
                    node.next = null;
                    return node.value;
                }
            }
            return createPooled();
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public void releasePooled(P p) {
            Node node = new Node(p);
            for (int i = 0; i < 3; i++) {
                node.next = this.head.get();
                if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m42m(this.head, node.next, node)) {
                    return;
                }
            }
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public int pooledCount() {
            int i = 0;
            for (Node node = this.head.get(); node != null; node = node.next) {
                i++;
            }
            return i;
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public boolean clear() {
            this.head.set(null);
            return true;
        }

        protected static class Node<P> {
            Node<P> next;
            final P value;

            Node(P p) {
                this.value = p;
            }
        }
    }

    public static abstract class BoundedPoolBase<P extends WithPool<P>> extends StatefulImplBase<P> {
        public static final int DEFAULT_CAPACITY = 100;
        private static final long serialVersionUID = 1;
        private final transient int capacity;
        private final transient ArrayBlockingQueue<P> pool;

        protected BoundedPoolBase(int i) {
            super(i);
            i = i <= 0 ? 100 : i;
            this.capacity = i;
            this.pool = new ArrayBlockingQueue<>(i);
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public P acquirePooled() {
            P pPoll = this.pool.poll();
            return pPoll == null ? createPooled() : pPoll;
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public void releasePooled(P p) {
            this.pool.offer(p);
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public int pooledCount() {
            return this.pool.size();
        }

        @Override // com.fasterxml.jackson.core.util.RecyclerPool
        public boolean clear() {
            this.pool.clear();
            return true;
        }

        public int capacity() {
            return this.capacity;
        }
    }
}
