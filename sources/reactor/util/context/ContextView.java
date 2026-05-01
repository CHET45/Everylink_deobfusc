package reactor.util.context;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public interface ContextView {
    <T> T get(Object obj);

    boolean hasKey(Object obj);

    int size();

    Stream<Map.Entry<Object, Object>> stream();

    default <T> T get(Class<T> cls) {
        T t = (T) get((Object) cls);
        if (cls.isInstance(t)) {
            return t;
        }
        throw new NoSuchElementException("Context does not contain a value of type " + cls.getName());
    }

    @Nullable
    default <T> T getOrDefault(Object obj, @Nullable T t) {
        return !hasKey(obj) ? t : (T) get(obj);
    }

    default <T> Optional<T> getOrEmpty(Object obj) {
        if (hasKey(obj)) {
            return Optional.of(get(obj));
        }
        return Optional.empty();
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    default void forEach(final BiConsumer<Object, Object> biConsumer) {
        stream().forEach(new Consumer() { // from class: reactor.util.context.ContextView$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Map.Entry entry = (Map.Entry) obj;
                biConsumer.accept(entry.getKey(), entry.getValue());
            }
        });
    }
}
