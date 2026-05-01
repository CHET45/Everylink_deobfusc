package reactor.util.concurrent;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BiPredicate;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class SpscLinkedArrayQueue<T> extends AbstractQueue<T> implements BiPredicate<T, T> {
    AtomicReferenceArray<Object> consumerArray;
    volatile long consumerIndex;
    final int mask;
    AtomicReferenceArray<Object> producerArray;
    volatile long producerIndex;
    static final AtomicLongFieldUpdater<SpscLinkedArrayQueue> PRODUCER_INDEX = AtomicLongFieldUpdater.newUpdater(SpscLinkedArrayQueue.class, "producerIndex");
    static final AtomicLongFieldUpdater<SpscLinkedArrayQueue> CONSUMER_INDEX = AtomicLongFieldUpdater.newUpdater(SpscLinkedArrayQueue.class, "consumerIndex");
    static final Object NEXT = new Object();

    SpscLinkedArrayQueue(int i) {
        int iCeilingNextPowerOfTwo = Queues.ceilingNextPowerOfTwo(Math.max(8, i));
        AtomicReferenceArray<Object> atomicReferenceArray = new AtomicReferenceArray<>(iCeilingNextPowerOfTwo + 1);
        this.consumerArray = atomicReferenceArray;
        this.producerArray = atomicReferenceArray;
        this.mask = iCeilingNextPowerOfTwo - 1;
    }

    @Override // java.util.Queue
    public boolean offer(T t) {
        Objects.requireNonNull(t);
        long j = this.producerIndex;
        AtomicReferenceArray<Object> atomicReferenceArray = this.producerArray;
        int i = this.mask;
        long j2 = 1 + j;
        if (atomicReferenceArray.get(((int) j2) & i) != null) {
            int i2 = ((int) j) & i;
            AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(i + 2);
            this.producerArray = atomicReferenceArray2;
            atomicReferenceArray2.lazySet(i2, t);
            atomicReferenceArray.lazySet(i + 1, atomicReferenceArray2);
            atomicReferenceArray.lazySet(i2, NEXT);
            PRODUCER_INDEX.lazySet(this, j2);
        } else {
            atomicReferenceArray.lazySet(((int) j) & i, t);
            PRODUCER_INDEX.lazySet(this, j2);
        }
        return true;
    }

    @Override // java.util.function.BiPredicate
    public boolean test(T t, T t2) {
        AtomicReferenceArray<Object> atomicReferenceArray = this.producerArray;
        long j = this.producerIndex;
        int i = this.mask;
        long j2 = 2 + j;
        if (atomicReferenceArray.get(((int) j2) & i) != null) {
            AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(i + 2);
            this.producerArray = atomicReferenceArray2;
            int i2 = ((int) j) & i;
            atomicReferenceArray2.lazySet(i2 + 1, t2);
            atomicReferenceArray2.lazySet(i2, t);
            atomicReferenceArray.lazySet(atomicReferenceArray.length() - 1, atomicReferenceArray2);
            atomicReferenceArray.lazySet(i2, NEXT);
            PRODUCER_INDEX.lazySet(this, j2);
        } else {
            int i3 = ((int) j) & i;
            atomicReferenceArray.lazySet(i3 + 1, t2);
            atomicReferenceArray.lazySet(i3, t);
            PRODUCER_INDEX.lazySet(this, j2);
        }
        return true;
    }

    @Override // java.util.Queue
    @Nullable
    public T poll() {
        long j = this.consumerIndex;
        AtomicReferenceArray<Object> atomicReferenceArray = this.consumerArray;
        int i = this.mask;
        int i2 = ((int) j) & i;
        T t = (T) atomicReferenceArray.get(i2);
        if (t == null) {
            return null;
        }
        if (t == NEXT) {
            int i3 = i + 1;
            AtomicReferenceArray<Object> atomicReferenceArray2 = (AtomicReferenceArray) atomicReferenceArray.get(i3);
            atomicReferenceArray.lazySet(i3, null);
            Object obj = atomicReferenceArray2.get(i2);
            this.consumerArray = atomicReferenceArray2;
            t = (T) obj;
            atomicReferenceArray = atomicReferenceArray2;
        }
        atomicReferenceArray.lazySet(i2, null);
        CONSUMER_INDEX.lazySet(this, j + 1);
        return t;
    }

    @Override // java.util.Queue
    @Nullable
    public T peek() {
        long j = this.consumerIndex;
        AtomicReferenceArray<Object> atomicReferenceArray = this.consumerArray;
        int i = this.mask;
        int i2 = ((int) j) & i;
        T t = (T) atomicReferenceArray.get(i2);
        if (t == null) {
            return null;
        }
        return t == NEXT ? (T) ((AtomicReferenceArray) atomicReferenceArray.get(i + 1)).get(i2) : t;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return this.producerIndex == this.consumerIndex;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        long j = this.consumerIndex;
        while (true) {
            long j2 = this.producerIndex;
            long j3 = this.consumerIndex;
            if (j == j3) {
                return (int) (j2 - j);
            }
            j = j3;
        }
    }

    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        while (poll() != null && !isEmpty()) {
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }
}
