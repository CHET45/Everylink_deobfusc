package reactor.core.publisher;

import java.util.function.LongConsumer;
import reactor.core.Disposable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

/* JADX INFO: loaded from: classes5.dex */
public interface MonoSink<T> {
    @Deprecated
    Context currentContext();

    void error(Throwable th);

    MonoSink<T> onCancel(Disposable disposable);

    MonoSink<T> onDispose(Disposable disposable);

    MonoSink<T> onRequest(LongConsumer longConsumer);

    void success();

    void success(@Nullable T t);

    default ContextView contextView() {
        return currentContext();
    }
}
