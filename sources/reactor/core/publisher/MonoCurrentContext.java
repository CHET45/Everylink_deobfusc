package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
@Deprecated
final class MonoCurrentContext extends Mono<Context> implements Fuseable, Scannable {
    static final MonoCurrentContext INSTANCE = new MonoCurrentContext();

    MonoCurrentContext() {
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super Context> coreSubscriber) {
        coreSubscriber.onSubscribe(Operators.scalarSubscription(coreSubscriber, coreSubscriber.currentContext()));
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }
}
