package reactor.util.function;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public class Tuple2<T1, T2> implements Iterable<Object>, Serializable {
    private static final long serialVersionUID = -3518082018884860684L;

    /* JADX INFO: renamed from: t1 */
    @NonNull
    final T1 f2301t1;

    /* JADX INFO: renamed from: t2 */
    @NonNull
    final T2 f2302t2;

    public int size() {
        return 2;
    }

    Tuple2(T1 t1, T2 t2) {
        this.f2301t1 = (T1) Objects.requireNonNull(t1, "t1");
        this.f2302t2 = (T2) Objects.requireNonNull(t2, "t2");
    }

    public T1 getT1() {
        return this.f2301t1;
    }

    public T2 getT2() {
        return this.f2302t2;
    }

    public <R> Tuple2<R, T2> mapT1(Function<T1, R> function) {
        return new Tuple2<>(function.apply(this.f2301t1), this.f2302t2);
    }

    public <R> Tuple2<T1, R> mapT2(Function<T2, R> function) {
        return new Tuple2<>(this.f2301t1, function.apply(this.f2302t2));
    }

    @Nullable
    public Object get(int i) {
        if (i == 0) {
            return this.f2301t1;
        }
        if (i != 1) {
            return null;
        }
        return this.f2302t2;
    }

    public List<Object> toList() {
        return Arrays.asList(toArray());
    }

    public Object[] toArray() {
        return new Object[]{this.f2301t1, this.f2302t2};
    }

    @Override // java.lang.Iterable
    public Iterator<Object> iterator() {
        return Collections.unmodifiableList(toList()).iterator();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Tuple2 tuple2 = (Tuple2) obj;
        return this.f2301t1.equals(tuple2.f2301t1) && this.f2302t2.equals(tuple2.f2302t2);
    }

    public int hashCode() {
        return (((size() * 31) + this.f2301t1.hashCode()) * 31) + this.f2302t2.hashCode();
    }

    public final String toString() {
        return Tuples.tupleStringRepresentation(toArray()).insert(0, '[').append(']').toString();
    }
}
