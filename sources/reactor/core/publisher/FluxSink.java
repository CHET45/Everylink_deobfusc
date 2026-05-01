package reactor.core.publisher;

import java.util.function.LongConsumer;
import reactor.core.Disposable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

/* JADX INFO: loaded from: classes5.dex */
public interface FluxSink<T> {

    public enum OverflowStrategy {
        IGNORE,
        ERROR,
        DROP,
        LATEST,
        BUFFER
    }

    void complete();

    @Deprecated
    Context currentContext();

    void error(Throwable th);

    boolean isCancelled();

    FluxSink<T> next(T t);

    FluxSink<T> onCancel(Disposable disposable);

    FluxSink<T> onDispose(Disposable disposable);

    FluxSink<T> onRequest(LongConsumer longConsumer);

    long requestedFromDownstream();

    default ContextView contextView() {
        return currentContext();
    }
}
