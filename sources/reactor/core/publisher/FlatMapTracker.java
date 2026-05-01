package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/* JADX INFO: compiled from: FluxFlatMap.java */
/* JADX INFO: loaded from: classes5.dex */
abstract class FlatMapTracker<T> {
    long consumerIndex;
    long producerIndex;
    volatile int size;
    static final AtomicIntegerFieldUpdater<FlatMapTracker> SIZE = AtomicIntegerFieldUpdater.newUpdater(FlatMapTracker.class, "size");
    static final int[] FREE_EMPTY = new int[0];
    volatile T[] array = empty();
    int[] free = FREE_EMPTY;

    abstract T[] empty();

    abstract T[] newArray(int i);

    abstract void setIndex(T t, int i);

    abstract T[] terminated();

    abstract void unsubscribeEntry(T t);

    FlatMapTracker() {
    }

    final void unsubscribe() {
        T[] tArrTerminated = terminated();
        synchronized (this) {
            T[] tArr = this.array;
            if (tArr == tArrTerminated) {
                return;
            }
            SIZE.lazySet(this, 0);
            this.free = null;
            this.array = tArrTerminated;
            for (T t : tArr) {
                if (t != null) {
                    unsubscribeEntry(t);
                }
            }
        }
    }

    final T[] get() {
        return this.array;
    }

    final boolean add(T t) {
        if (this.array == terminated()) {
            return false;
        }
        synchronized (this) {
            T[] tArr = this.array;
            if (tArr == terminated()) {
                return false;
            }
            int iPollFree = pollFree();
            if (iPollFree < 0) {
                iPollFree = tArr.length;
                T[] tArrNewArray = iPollFree != 0 ? newArray(iPollFree << 1) : newArray(4);
                System.arraycopy(tArr, 0, tArrNewArray, 0, iPollFree);
                this.array = tArrNewArray;
                int length = tArrNewArray.length;
                int[] iArr = new int[length];
                int i = iPollFree + 1;
                for (int i2 = i; i2 < length; i2++) {
                    iArr[i2] = i2;
                }
                this.free = iArr;
                this.consumerIndex = i;
                this.producerIndex = length;
                tArr = tArrNewArray;
            }
            setIndex(t, iPollFree);
            AtomicIntegerFieldUpdater<FlatMapTracker> atomicIntegerFieldUpdater = SIZE;
            atomicIntegerFieldUpdater.lazySet(this, this.size);
            tArr[iPollFree] = t;
            atomicIntegerFieldUpdater.lazySet(this, this.size + 1);
            return true;
        }
    }

    final void remove(int i) {
        synchronized (this) {
            T[] tArr = this.array;
            if (tArr != terminated()) {
                tArr[i] = null;
                offerFree(i);
                SIZE.lazySet(this, this.size - 1);
            }
        }
    }

    int pollFree() {
        int[] iArr = this.free;
        int length = iArr.length - 1;
        long j = this.consumerIndex;
        if (this.producerIndex == j) {
            return -1;
        }
        int i = length & ((int) j);
        this.consumerIndex = j + 1;
        return iArr[i];
    }

    void offerFree(int i) {
        int[] iArr = this.free;
        int length = iArr.length - 1;
        long j = this.producerIndex;
        iArr[length & ((int) j)] = i;
        this.producerIndex = j + 1;
    }

    final boolean isEmpty() {
        return this.size == 0;
    }
}
