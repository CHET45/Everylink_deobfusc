package reactor.util.function;

import java.util.Objects;
import java.util.function.Function;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public class Tuple3<T1, T2, T3> extends Tuple2<T1, T2> {
    private static final long serialVersionUID = -4430274211524723033L;

    /* JADX INFO: renamed from: t3 */
    @NonNull
    final T3 f2303t3;

    @Override // reactor.util.function.Tuple2
    public int size() {
        return 3;
    }

    Tuple3(T1 t1, T2 t2, T3 t3) {
        super(t1, t2);
        this.f2303t3 = (T3) Objects.requireNonNull(t3, "t3");
    }

    public T3 getT3() {
        return this.f2303t3;
    }

    @Override // reactor.util.function.Tuple2
    public <R> Tuple3<R, T2, T3> mapT1(Function<T1, R> function) {
        return new Tuple3<>(function.apply(this.f2301t1), this.f2302t2, this.f2303t3);
    }

    @Override // reactor.util.function.Tuple2
    public <R> Tuple3<T1, R, T3> mapT2(Function<T2, R> function) {
        return new Tuple3<>(this.f2301t1, function.apply(this.f2302t2), this.f2303t3);
    }

    public <R> Tuple3<T1, T2, R> mapT3(Function<T3, R> function) {
        return new Tuple3<>(this.f2301t1, this.f2302t2, function.apply(this.f2303t3));
    }

    @Override // reactor.util.function.Tuple2
    @Nullable
    public Object get(int i) {
        if (i == 0) {
            return this.f2301t1;
        }
        if (i == 1) {
            return this.f2302t2;
        }
        if (i != 2) {
            return null;
        }
        return this.f2303t3;
    }

    @Override // reactor.util.function.Tuple2
    public Object[] toArray() {
        return new Object[]{this.f2301t1, this.f2302t2, this.f2303t3};
    }

    @Override // reactor.util.function.Tuple2
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof Tuple3) && super.equals(obj)) {
            return this.f2303t3.equals(((Tuple3) obj).f2303t3);
        }
        return false;
    }

    @Override // reactor.util.function.Tuple2
    public int hashCode() {
        return (super.hashCode() * 31) + this.f2303t3.hashCode();
    }
}
