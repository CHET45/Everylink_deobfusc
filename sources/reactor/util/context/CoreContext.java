package reactor.util.context;

import java.util.Map;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes5.dex */
interface CoreContext extends Context {
    @Override // reactor.util.context.ContextView
    default boolean isEmpty() {
        return false;
    }

    Context putAllInto(Context context);

    void unsafePutAllInto(ContextN contextN);

    @Override // reactor.util.context.Context
    default Context putAll(ContextView contextView) {
        if (contextView.isEmpty()) {
            return this;
        }
        if (contextView instanceof CoreContext) {
            return ((CoreContext) contextView).putAllInto(this);
        }
        ContextN contextN = new ContextN(size() + contextView.size());
        unsafePutAllInto(contextN);
        ((Stream) contextView.stream().sequential()).forEach(contextN);
        return contextN.size() <= 5 ? Context.m1986of((Map<?, ?>) contextN) : contextN;
    }
}
