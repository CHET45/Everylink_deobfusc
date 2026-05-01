package reactor.util.concurrent;

import java.util.concurrent.atomic.AtomicReferenceArray;

/* JADX INFO: compiled from: SpscArrayQueue.java */
/* JADX INFO: loaded from: classes5.dex */
class SpscArrayQueueCold<T> extends AtomicReferenceArray<T> {
    private static final long serialVersionUID = 8491797459632447132L;
    final int mask;

    public SpscArrayQueueCold(int i) {
        super(i);
        this.mask = i - 1;
    }
}
