package reactor.util.concurrent;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* JADX INFO: compiled from: SpscArrayQueue.java */
/* JADX INFO: loaded from: classes5.dex */
class SpscArrayQueueProducer<T> extends SpscArrayQueueP1<T> {
    static final AtomicLongFieldUpdater<SpscArrayQueueProducer> PRODUCER_INDEX = AtomicLongFieldUpdater.newUpdater(SpscArrayQueueProducer.class, "producerIndex");
    private static final long serialVersionUID = 1657408315616277653L;
    volatile long producerIndex;

    SpscArrayQueueProducer(int i) {
        super(i);
    }
}
