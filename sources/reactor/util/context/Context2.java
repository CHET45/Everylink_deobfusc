package reactor.util.context;

import com.lzy.okgo.cache.CacheHelper;
import java.util.AbstractMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes5.dex */
final class Context2 implements CoreContext {
    final Object key1;
    final Object key2;
    final Object value1;
    final Object value2;

    @Override // reactor.util.context.ContextView
    public int size() {
        return 2;
    }

    Context2(Object obj, Object obj2, Object obj3, Object obj4) {
        if (Objects.requireNonNull(obj, "key1").equals(obj3)) {
            throw new IllegalArgumentException("Key #1 (" + obj + ") is duplicated");
        }
        this.key1 = obj;
        this.value1 = Objects.requireNonNull(obj2, "value1");
        this.key2 = Objects.requireNonNull(obj3, "key2");
        this.value2 = Objects.requireNonNull(obj4, "value2");
    }

    @Override // reactor.util.context.Context
    public Context put(Object obj, Object obj2) {
        Objects.requireNonNull(obj, CacheHelper.KEY);
        Objects.requireNonNull(obj2, "value");
        if (this.key1.equals(obj)) {
            return new Context2(obj, obj2, this.key2, this.value2);
        }
        if (this.key2.equals(obj)) {
            return new Context2(this.key1, this.value1, obj, obj2);
        }
        return new Context3(this.key1, this.value1, this.key2, this.value2, obj, obj2);
    }

    @Override // reactor.util.context.Context
    public Context delete(Object obj) {
        Objects.requireNonNull(obj, CacheHelper.KEY);
        if (this.key1.equals(obj)) {
            return new Context1(this.key2, this.value2);
        }
        return this.key2.equals(obj) ? new Context1(this.key1, this.value1) : this;
    }

    @Override // reactor.util.context.ContextView
    public boolean hasKey(Object obj) {
        return this.key1.equals(obj) || this.key2.equals(obj);
    }

    @Override // reactor.util.context.ContextView
    public <T> T get(Object obj) {
        if (this.key1.equals(obj)) {
            return (T) this.value1;
        }
        if (this.key2.equals(obj)) {
            return (T) this.value2;
        }
        throw new NoSuchElementException("Context does not contain key: " + obj);
    }

    @Override // reactor.util.context.ContextView
    public Stream<Map.Entry<Object, Object>> stream() {
        return Stream.of((Object[]) new Map.Entry[]{new AbstractMap.SimpleImmutableEntry(this.key1, this.value1), new AbstractMap.SimpleImmutableEntry(this.key2, this.value2)});
    }

    @Override // reactor.util.context.ContextView
    public void forEach(BiConsumer<Object, Object> biConsumer) {
        biConsumer.accept(this.key1, this.value1);
        biConsumer.accept(this.key2, this.value2);
    }

    @Override // reactor.util.context.CoreContext
    public Context putAllInto(Context context) {
        return context.put(this.key1, this.value1).put(this.key2, this.value2);
    }

    @Override // reactor.util.context.CoreContext
    public void unsafePutAllInto(ContextN contextN) {
        contextN.accept(this.key1, this.value1);
        contextN.accept(this.key2, this.value2);
    }

    public String toString() {
        return "Context2{" + this.key1 + '=' + this.value1 + ", " + this.key2 + '=' + this.value2 + '}';
    }
}
