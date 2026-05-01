package reactor.core.publisher;

import org.reactivestreams.Publisher;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
interface SourceProducer<O> extends Scannable, Publisher<O> {
    @Override // reactor.core.Scannable
    @Nullable
    default Object scanUnsafe(Scannable.Attr attr) {
        return (attr == Scannable.Attr.PARENT || attr == Scannable.Attr.ACTUAL || attr != InternalProducerAttr.INSTANCE) ? null : true;
    }

    @Override // reactor.core.Scannable
    default String stepName() {
        return "source(" + getClass().getSimpleName() + ")";
    }
}
