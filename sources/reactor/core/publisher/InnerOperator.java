package reactor.core.publisher;

import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
interface InnerOperator<I, O> extends InnerConsumer<I>, InnerProducer<O> {
    @Override // reactor.core.CoreSubscriber
    default Context currentContext() {
        return actual().currentContext();
    }
}
