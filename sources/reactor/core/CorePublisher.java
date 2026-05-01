package reactor.core;

import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes5.dex */
public interface CorePublisher<T> extends Publisher<T> {
    void subscribe(CoreSubscriber<? super T> coreSubscriber);
}
