package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
interface InnerProducer<O> extends Scannable, Subscription {
    CoreSubscriber<? super O> actual();

    @Override // reactor.core.Scannable
    @Nullable
    default Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.ACTUAL) {
            return actual();
        }
        return attr == InternalProducerAttr.INSTANCE ? true : null;
    }
}
