package reactor.core;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
public interface CoreSubscriber<T> extends Subscriber<T> {
    @Override // org.reactivestreams.Subscriber
    void onSubscribe(Subscription subscription);

    default Context currentContext() {
        return Context.empty();
    }
}
