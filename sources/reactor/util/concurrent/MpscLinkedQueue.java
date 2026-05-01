package reactor.util.concurrent;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiPredicate;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MpscLinkedQueue<E> extends AbstractQueue<E> implements BiPredicate<E, E> {
    private volatile LinkedQueueNode<E> consumerNode;
    private volatile LinkedQueueNode<E> producerNode;
    private static final AtomicReferenceFieldUpdater<MpscLinkedQueue, LinkedQueueNode> PRODUCER_NODE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(MpscLinkedQueue.class, LinkedQueueNode.class, "producerNode");
    private static final AtomicReferenceFieldUpdater<MpscLinkedQueue, LinkedQueueNode> CONSUMER_NODE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(MpscLinkedQueue.class, LinkedQueueNode.class, "consumerNode");

    public MpscLinkedQueue() {
        LinkedQueueNode linkedQueueNode = new LinkedQueueNode();
        CONSUMER_NODE_UPDATER.lazySet(this, linkedQueueNode);
        PRODUCER_NODE_UPDATER.getAndSet(this, linkedQueueNode);
    }

    @Override // java.util.Queue
    public final boolean offer(E e) {
        Objects.requireNonNull(e, "The offered value 'e' must be non-null");
        LinkedQueueNode<E> linkedQueueNode = new LinkedQueueNode<>(e);
        PRODUCER_NODE_UPDATER.getAndSet(this, linkedQueueNode).soNext(linkedQueueNode);
        return true;
    }

    @Override // java.util.function.BiPredicate
    public boolean test(E e, E e2) {
        Objects.requireNonNull(e, "The offered value 'e1' must be non-null");
        Objects.requireNonNull(e2, "The offered value 'e2' must be non-null");
        LinkedQueueNode<E> linkedQueueNode = new LinkedQueueNode<>(e);
        LinkedQueueNode<E> linkedQueueNode2 = new LinkedQueueNode<>(e2);
        LinkedQueueNode andSet = PRODUCER_NODE_UPDATER.getAndSet(this, linkedQueueNode2);
        linkedQueueNode.soNext(linkedQueueNode2);
        andSet.soNext(linkedQueueNode);
        return true;
    }

    @Override // java.util.Queue
    @Nullable
    public E poll() {
        LinkedQueueNode<E> linkedQueueNodeLvNext;
        LinkedQueueNode<E> linkedQueueNode = this.consumerNode;
        LinkedQueueNode<E> linkedQueueNodeLvNext2 = linkedQueueNode.lvNext();
        if (linkedQueueNodeLvNext2 != null) {
            E andNullValue = linkedQueueNodeLvNext2.getAndNullValue();
            linkedQueueNode.soNext(linkedQueueNode);
            CONSUMER_NODE_UPDATER.lazySet(this, linkedQueueNodeLvNext2);
            return andNullValue;
        }
        if (linkedQueueNode == this.producerNode) {
            return null;
        }
        do {
            linkedQueueNodeLvNext = linkedQueueNode.lvNext();
        } while (linkedQueueNodeLvNext == null);
        E andNullValue2 = linkedQueueNodeLvNext.getAndNullValue();
        linkedQueueNode.soNext(linkedQueueNode);
        CONSUMER_NODE_UPDATER.lazySet(this, linkedQueueNodeLvNext);
        return andNullValue2;
    }

    @Override // java.util.Queue
    @Nullable
    public E peek() {
        LinkedQueueNode<E> linkedQueueNodeLvNext;
        LinkedQueueNode<E> linkedQueueNode = this.consumerNode;
        LinkedQueueNode<E> linkedQueueNodeLvNext2 = linkedQueueNode.lvNext();
        if (linkedQueueNodeLvNext2 != null) {
            return linkedQueueNodeLvNext2.lpValue();
        }
        if (linkedQueueNode == this.producerNode) {
            return null;
        }
        do {
            linkedQueueNodeLvNext = linkedQueueNode.lvNext();
        } while (linkedQueueNodeLvNext == null);
        return linkedQueueNodeLvNext.lpValue();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        while (poll() != null && !isEmpty()) {
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        LinkedQueueNode<E> linkedQueueNode = this.consumerNode;
        LinkedQueueNode<E> linkedQueueNode2 = this.producerNode;
        int i = 0;
        while (linkedQueueNode != linkedQueueNode2 && linkedQueueNode != null && i < Integer.MAX_VALUE) {
            LinkedQueueNode<E> linkedQueueNodeLvNext = linkedQueueNode.lvNext();
            if (linkedQueueNodeLvNext == linkedQueueNode) {
                return i;
            }
            i++;
            linkedQueueNode = linkedQueueNodeLvNext;
        }
        return i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return this.consumerNode == this.producerNode;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    static final class LinkedQueueNode<E> {
        private static final AtomicReferenceFieldUpdater<LinkedQueueNode, LinkedQueueNode> NEXT_UPDATER = AtomicReferenceFieldUpdater.newUpdater(LinkedQueueNode.class, LinkedQueueNode.class, "next");
        private volatile LinkedQueueNode<E> next;
        private E value;

        LinkedQueueNode() {
            this(null);
        }

        LinkedQueueNode(@Nullable E e) {
            spValue(e);
        }

        @Nullable
        public E getAndNullValue() {
            E eLpValue = lpValue();
            spValue(null);
            return eLpValue;
        }

        @Nullable
        public E lpValue() {
            return this.value;
        }

        public void spValue(@Nullable E e) {
            this.value = e;
        }

        public void soNext(@Nullable LinkedQueueNode<E> linkedQueueNode) {
            NEXT_UPDATER.lazySet(this, linkedQueueNode);
        }

        @Nullable
        public LinkedQueueNode<E> lvNext() {
            return this.next;
        }
    }
}
