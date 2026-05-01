package reactor.util.context;

import io.micrometer.context.ContextAccessor;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public final class ReactorContextAccessor implements ContextAccessor<ContextView, Context> {
    public /* bridge */ /* synthetic */ void readValues(Object obj, Predicate predicate, Map map) {
        readValues((ContextView) obj, (Predicate<Object>) predicate, (Map<Object, Object>) map);
    }

    public /* bridge */ /* synthetic */ Object writeValues(Map map, Object obj) {
        return writeValues((Map<Object, Object>) map, (Context) obj);
    }

    public Class<? extends ContextView> readableType() {
        return ContextView.class;
    }

    public void readValues(ContextView contextView, final Predicate<Object> predicate, final Map<Object, Object> map) {
        contextView.forEach(new BiConsumer() { // from class: reactor.util.context.ReactorContextAccessor$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ReactorContextAccessor.lambda$readValues$0(predicate, map, obj, obj2);
            }
        });
    }

    static /* synthetic */ void lambda$readValues$0(Predicate predicate, Map map, Object obj, Object obj2) {
        if (predicate.test(obj)) {
            map.put(obj, obj2);
        }
    }

    @Nullable
    public <T> T readValue(ContextView contextView, Object obj) {
        return (T) contextView.getOrDefault(obj, null);
    }

    public Class<? extends Context> writeableType() {
        return Context.class;
    }

    public Context writeValues(Map<Object, Object> map, Context context) {
        return context.putAllMap(map);
    }
}
