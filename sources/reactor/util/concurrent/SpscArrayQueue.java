package reactor.util.concurrent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class SpscArrayQueue<T> extends SpscArrayQueueP3<T> implements Queue<T> {
    private static final long serialVersionUID = 494623116936946976L;

    SpscArrayQueue(int i) {
        super(Queues.ceilingNextPowerOfTwo(i));
    }

    @Override // java.util.Queue
    public boolean offer(T t) {
        Objects.requireNonNull(t, "e");
        long j = this.producerIndex;
        int i = ((int) j) & this.mask;
        if (get(i) != null) {
            return false;
        }
        lazySet(i, t);
        PRODUCER_INDEX.lazySet(this, j + 1);
        return true;
    }

    @Override // java.util.Queue
    @Nullable
    public T poll() {
        long j = this.consumerIndex;
        int i = ((int) j) & this.mask;
        T t = (T) get(i);
        if (t != null) {
            lazySet(i, null);
            CONSUMER_INDEX.lazySet(this, j + 1);
        }
        return t;
    }

    @Override // java.util.Queue
    @Nullable
    public T peek() {
        return (T) get(((int) this.consumerIndex) & this.mask);
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return this.producerIndex == this.consumerIndex;
    }

    @Override // java.util.Collection
    public void clear() {
        while (poll() != null && !isEmpty()) {
        }
    }

    @Override // java.util.Collection
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

    @Override // java.util.Collection
    public boolean contains(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public <R> R[] toArray(R[] rArr) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Queue, java.util.Collection
    public boolean add(T t) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Queue
    public T remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Queue
    public T element() {
        throw new UnsupportedOperationException();
    }
}
