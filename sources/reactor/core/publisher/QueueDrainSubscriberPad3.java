package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* JADX INFO: compiled from: QueueDrainSubscriber.java */
/* JADX INFO: loaded from: classes5.dex */
class QueueDrainSubscriberPad3 extends QueueDrainSubscriberPad2 {
    static final AtomicLongFieldUpdater<QueueDrainSubscriberPad3> REQUESTED = AtomicLongFieldUpdater.newUpdater(QueueDrainSubscriberPad3.class, "requested");
    volatile long requested;

    QueueDrainSubscriberPad3() {
    }
}
