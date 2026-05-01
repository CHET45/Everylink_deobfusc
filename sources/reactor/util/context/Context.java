package reactor.util.context;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public interface Context extends ContextView {
    Context delete(Object obj);

    Context put(Object obj, Object obj2);

    default ContextView readOnly() {
        return this;
    }

    static Context empty() {
        return Context0.INSTANCE;
    }

    /* JADX INFO: renamed from: of */
    static Context m1981of(Object obj, Object obj2) {
        return new Context1(obj, obj2);
    }

    /* JADX INFO: renamed from: of */
    static Context m1982of(Object obj, Object obj2, Object obj3, Object obj4) {
        return new Context2(obj, obj2, obj3, obj4);
    }

    /* JADX INFO: renamed from: of */
    static Context m1983of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        return new Context3(obj, obj2, obj3, obj4, obj5, obj6);
    }

    /* JADX INFO: renamed from: of */
    static Context m1984of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8) {
        return new Context4(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8);
    }

    /* JADX INFO: renamed from: of */
    static Context m1985of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10) {
        return new Context5(obj, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10);
    }

    /* JADX INFO: renamed from: of */
    static Context m1986of(Map<?, ?> map) {
        int size = ((Map) Objects.requireNonNull(map, "map")).size();
        if (size == 0) {
            return empty();
        }
        if (size <= 5) {
            Map.Entry[] entryArr = (Map.Entry[]) map.entrySet().toArray(new Map.Entry[size]);
            if (size == 1) {
                return new Context1(entryArr[0].getKey(), entryArr[0].getValue());
            }
            if (size == 2) {
                return new Context2(entryArr[0].getKey(), entryArr[0].getValue(), entryArr[1].getKey(), entryArr[1].getValue());
            }
            if (size == 3) {
                return new Context3(entryArr[0].getKey(), entryArr[0].getValue(), entryArr[1].getKey(), entryArr[1].getValue(), entryArr[2].getKey(), entryArr[2].getValue());
            }
            if (size == 4) {
                return new Context4(entryArr[0].getKey(), entryArr[0].getValue(), entryArr[1].getKey(), entryArr[1].getValue(), entryArr[2].getKey(), entryArr[2].getValue(), entryArr[3].getKey(), entryArr[3].getValue());
            }
            if (size == 5) {
                return new Context5(entryArr[0].getKey(), entryArr[0].getValue(), entryArr[1].getKey(), entryArr[1].getValue(), entryArr[2].getKey(), entryArr[2].getValue(), entryArr[3].getKey(), entryArr[3].getValue(), entryArr[4].getKey(), entryArr[4].getValue());
            }
        }
        map.forEach(new BiConsumer() { // from class: reactor.util.context.Context$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Context.lambda$of$0(obj, obj2);
            }
        });
        return new ContextN(map);
    }

    static /* synthetic */ void lambda$of$0(Object obj, Object obj2) {
        Objects.requireNonNull(obj, "null key found");
        if (obj2 == null) {
            throw new NullPointerException("null value for key " + obj);
        }
    }

    /* JADX INFO: renamed from: of */
    static Context m1987of(ContextView contextView) {
        Objects.requireNonNull(contextView, "contextView");
        if (contextView instanceof Context) {
            return (Context) contextView;
        }
        return empty().putAll(contextView);
    }

    default Context putNonNull(Object obj, @Nullable Object obj2) {
        return obj2 != null ? put(obj, obj2) : this;
    }

    default Context putAll(ContextView contextView) {
        if (contextView.isEmpty()) {
            return this;
        }
        if (contextView instanceof CoreContext) {
            return ((CoreContext) contextView).putAllInto(this);
        }
        ContextN contextN = new ContextN(size() + contextView.size());
        ((Stream) stream().sequential()).forEach(contextN);
        ((Stream) contextView.stream().sequential()).forEach(contextN);
        return contextN.size() <= 5 ? m1986of((Map<?, ?>) contextN) : contextN;
    }

    default Context putAllMap(Map<?, ?> map) {
        if (map.isEmpty()) {
            return this;
        }
        ContextN contextN = new ContextN(size() + map.size());
        forEach(contextN);
        map.forEach(contextN);
        return contextN.size() <= 5 ? m1986of((Map<?, ?>) contextN) : contextN;
    }

    @Deprecated
    default Context putAll(Context context) {
        return putAll(context.readOnly());
    }
}
