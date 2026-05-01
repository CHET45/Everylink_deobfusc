package reactor.core.publisher;

import reactor.util.context.Context;
import reactor.util.context.ContextView;

/* JADX INFO: loaded from: classes5.dex */
public interface SynchronousSink<T> {
    void complete();

    @Deprecated
    Context currentContext();

    void error(Throwable th);

    void next(T t);

    default ContextView contextView() {
        return currentContext();
    }
}
