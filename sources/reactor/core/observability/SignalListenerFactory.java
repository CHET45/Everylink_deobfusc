package reactor.core.observability;

import org.reactivestreams.Publisher;
import reactor.util.context.ContextView;

/* JADX INFO: loaded from: classes5.dex */
public interface SignalListenerFactory<T, STATE> {
    SignalListener<T> createListener(Publisher<? extends T> publisher, ContextView contextView, STATE state);

    STATE initializePublisherState(Publisher<? extends T> publisher);
}
