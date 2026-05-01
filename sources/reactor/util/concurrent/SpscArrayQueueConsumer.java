package reactor.util.concurrent;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* JADX INFO: compiled from: SpscArrayQueue.java */
/* JADX INFO: loaded from: classes5.dex */
class SpscArrayQueueConsumer<T> extends SpscArrayQueueP2<T> {
    static final AtomicLongFieldUpdater<SpscArrayQueueConsumer> CONSUMER_INDEX = AtomicLongFieldUpdater.newUpdater(SpscArrayQueueConsumer.class, "consumerIndex");
    private static final long serialVersionUID = 4075549732218321659L;
    volatile long consumerIndex;

    SpscArrayQueueConsumer(int i) {
        super(i);
    }
}
