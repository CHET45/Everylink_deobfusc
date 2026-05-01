package reactor.util.context;

import com.lzy.okgo.cache.CacheHelper;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes5.dex */
final class Context0 implements CoreContext {
    static final Context0 INSTANCE = new Context0();

    @Override // reactor.util.context.Context
    public Context delete(Object obj) {
        return this;
    }

    @Override // reactor.util.context.ContextView
    public void forEach(BiConsumer<Object, Object> biConsumer) {
    }

    @Override // reactor.util.context.ContextView
    public boolean hasKey(Object obj) {
        return false;
    }

    @Override // reactor.util.context.CoreContext, reactor.util.context.ContextView
    public boolean isEmpty() {
        return true;
    }

    @Override // reactor.util.context.CoreContext
    public Context putAllInto(Context context) {
        return context;
    }

    @Override // reactor.util.context.ContextView
    public int size() {
        return 0;
    }

    @Override // reactor.util.context.CoreContext
    public void unsafePutAllInto(ContextN contextN) {
    }

    Context0() {
    }

    @Override // reactor.util.context.Context
    public Context put(Object obj, Object obj2) {
        Objects.requireNonNull(obj, CacheHelper.KEY);
        Objects.requireNonNull(obj2, "value");
        return new Context1(obj, obj2);
    }

    @Override // reactor.util.context.ContextView
    public <T> T get(Object obj) {
        throw new NoSuchElementException("Context is empty");
    }

    public String toString() {
        return "Context0{}";
    }

    @Override // reactor.util.context.ContextView
    public Stream<Map.Entry<Object, Object>> stream() {
        return Stream.empty();
    }
}
