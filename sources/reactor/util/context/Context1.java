package reactor.util.context;

import com.lzy.okgo.cache.CacheHelper;
import java.util.AbstractMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes5.dex */
final class Context1 implements CoreContext {
    final Object key;
    final Object value;

    @Override // reactor.util.context.ContextView
    public int size() {
        return 1;
    }

    Context1(Object obj, Object obj2) {
        this.key = Objects.requireNonNull(obj, CacheHelper.KEY);
        this.value = Objects.requireNonNull(obj2, "value");
    }

    @Override // reactor.util.context.Context
    public Context put(Object obj, Object obj2) {
        Objects.requireNonNull(obj, CacheHelper.KEY);
        Objects.requireNonNull(obj2, "value");
        if (this.key.equals(obj)) {
            return new Context1(obj, obj2);
        }
        return new Context2(this.key, this.value, obj, obj2);
    }

    @Override // reactor.util.context.Context
    public Context delete(Object obj) {
        Objects.requireNonNull(obj, CacheHelper.KEY);
        return this.key.equals(obj) ? Context.empty() : this;
    }

    @Override // reactor.util.context.ContextView
    public boolean hasKey(Object obj) {
        return this.key.equals(obj);
    }

    @Override // reactor.util.context.ContextView
    public <T> T get(Object obj) {
        if (hasKey(obj)) {
            return (T) this.value;
        }
        throw new NoSuchElementException("Context does not contain key: " + obj);
    }

    @Override // reactor.util.context.ContextView
    public Stream<Map.Entry<Object, Object>> stream() {
        return Stream.of(new AbstractMap.SimpleImmutableEntry(this.key, this.value));
    }

    @Override // reactor.util.context.ContextView
    public void forEach(BiConsumer<Object, Object> biConsumer) {
        biConsumer.accept(this.key, this.value);
    }

    @Override // reactor.util.context.CoreContext
    public Context putAllInto(Context context) {
        return context.put(this.key, this.value);
    }

    @Override // reactor.util.context.CoreContext
    public void unsafePutAllInto(ContextN contextN) {
        contextN.accept(this.key, this.value);
    }

    public String toString() {
        return "Context1{" + this.key + '=' + this.value + '}';
    }
}
