package reactor.util.context;

import com.lzy.okgo.cache.CacheHelper;
import java.util.AbstractMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes5.dex */
final class Context4 implements CoreContext {
    final Object key1;
    final Object key2;
    final Object key3;
    final Object key4;
    final Object value1;
    final Object value2;
    final Object value3;
    final Object value4;

    @Override // reactor.util.context.ContextView
    public int size() {
        return 4;
    }

    static void checkKeys(Object... objArr) {
        int length = objArr.length;
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (i >= i2) {
                if (length != 0) {
                    Objects.requireNonNull(objArr[i2], CacheHelper.KEY + length);
                    return;
                }
                return;
            }
            Object obj = objArr[i];
            i++;
            Object objRequireNonNull = Objects.requireNonNull(obj, CacheHelper.KEY + i);
            for (int i3 = i; i3 < length; i3++) {
                if (objRequireNonNull.equals(objArr[i3])) {
                    throw new IllegalArgumentException("Key #" + i + " (" + objRequireNonNull + ") is duplicated");
                }
            }
        }
    }

    Context4(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8) {
        checkKeys(obj, obj3, obj5, obj7);
        this.key1 = Objects.requireNonNull(obj, "key1");
        this.value1 = Objects.requireNonNull(obj2, "value1");
        this.key2 = Objects.requireNonNull(obj3, "key2");
        this.value2 = Objects.requireNonNull(obj4, "value2");
        this.key3 = Objects.requireNonNull(obj5, "key3");
        this.value3 = Objects.requireNonNull(obj6, "value3");
        this.key4 = Objects.requireNonNull(obj7, "key4");
        this.value4 = Objects.requireNonNull(obj8, "value4");
    }

    @Override // reactor.util.context.Context
    public Context put(Object obj, Object obj2) {
        Objects.requireNonNull(obj, CacheHelper.KEY);
        Objects.requireNonNull(obj2, "value");
        if (this.key1.equals(obj)) {
            return new Context4(obj, obj2, this.key2, this.value2, this.key3, this.value3, this.key4, this.value4);
        }
        if (this.key2.equals(obj)) {
            return new Context4(this.key1, this.value1, obj, obj2, this.key3, this.value3, this.key4, this.value4);
        }
        if (this.key3.equals(obj)) {
            return new Context4(this.key1, this.value1, this.key2, this.value2, obj, obj2, this.key4, this.value4);
        }
        if (this.key4.equals(obj)) {
            return new Context4(this.key1, this.value1, this.key2, this.value2, this.key3, this.value3, obj, obj2);
        }
        return new Context5(this.key1, this.value1, this.key2, this.value2, this.key3, this.value3, this.key4, this.value4, obj, obj2);
    }

    @Override // reactor.util.context.Context
    public Context delete(Object obj) {
        Objects.requireNonNull(obj, CacheHelper.KEY);
        if (this.key1.equals(obj)) {
            return new Context3(this.key2, this.value2, this.key3, this.value3, this.key4, this.value4);
        }
        if (this.key2.equals(obj)) {
            return new Context3(this.key1, this.value1, this.key3, this.value3, this.key4, this.value4);
        }
        if (this.key3.equals(obj)) {
            return new Context3(this.key1, this.value1, this.key2, this.value2, this.key4, this.value4);
        }
        return this.key4.equals(obj) ? new Context3(this.key1, this.value1, this.key2, this.value2, this.key3, this.value3) : this;
    }

    @Override // reactor.util.context.ContextView
    public boolean hasKey(Object obj) {
        return this.key1.equals(obj) || this.key2.equals(obj) || this.key3.equals(obj) || this.key4.equals(obj);
    }

    @Override // reactor.util.context.ContextView
    public <T> T get(Object obj) {
        if (this.key1.equals(obj)) {
            return (T) this.value1;
        }
        if (this.key2.equals(obj)) {
            return (T) this.value2;
        }
        if (this.key3.equals(obj)) {
            return (T) this.value3;
        }
        if (this.key4.equals(obj)) {
            return (T) this.value4;
        }
        throw new NoSuchElementException("Context does not contain key: " + obj);
    }

    @Override // reactor.util.context.ContextView
    public Stream<Map.Entry<Object, Object>> stream() {
        return Stream.of((Object[]) new Map.Entry[]{new AbstractMap.SimpleImmutableEntry(this.key1, this.value1), new AbstractMap.SimpleImmutableEntry(this.key2, this.value2), new AbstractMap.SimpleImmutableEntry(this.key3, this.value3), new AbstractMap.SimpleImmutableEntry(this.key4, this.value4)});
    }

    @Override // reactor.util.context.ContextView
    public void forEach(BiConsumer<Object, Object> biConsumer) {
        biConsumer.accept(this.key1, this.value1);
        biConsumer.accept(this.key2, this.value2);
        biConsumer.accept(this.key3, this.value3);
        biConsumer.accept(this.key4, this.value4);
    }

    @Override // reactor.util.context.CoreContext
    public Context putAllInto(Context context) {
        return context.put(this.key1, this.value1).put(this.key2, this.value2).put(this.key3, this.value3).put(this.key4, this.value4);
    }

    @Override // reactor.util.context.CoreContext
    public void unsafePutAllInto(ContextN contextN) {
        contextN.accept(this.key1, this.value1);
        contextN.accept(this.key2, this.value2);
        contextN.accept(this.key3, this.value3);
        contextN.accept(this.key4, this.value4);
    }

    public String toString() {
        return "Context4{" + this.key1 + '=' + this.value1 + ", " + this.key2 + '=' + this.value2 + ", " + this.key3 + '=' + this.value3 + ", " + this.key4 + '=' + this.value4 + '}';
    }
}
