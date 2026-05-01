package reactor.util.function;

import java.util.Objects;
import java.util.function.Function;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public class Tuple4<T1, T2, T3, T4> extends Tuple3<T1, T2, T3> {
    private static final long serialVersionUID = -4898704078143033129L;

    /* JADX INFO: renamed from: t4 */
    @NonNull
    final T4 f2304t4;

    @Override // reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public int size() {
        return 4;
    }

    Tuple4(T1 t1, T2 t2, T3 t3, T4 t4) {
        super(t1, t2, t3);
        this.f2304t4 = (T4) Objects.requireNonNull(t4, "t4");
    }

    public T4 getT4() {
        return this.f2304t4;
    }

    @Override // reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public <R> Tuple4<R, T2, T3, T4> mapT1(Function<T1, R> function) {
        return new Tuple4<>(function.apply(this.f2301t1), this.f2302t2, this.f2303t3, this.f2304t4);
    }

    @Override // reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public <R> Tuple4<T1, R, T3, T4> mapT2(Function<T2, R> function) {
        return new Tuple4<>(this.f2301t1, function.apply(this.f2302t2), this.f2303t3, this.f2304t4);
    }

    @Override // reactor.util.function.Tuple3
    public <R> Tuple4<T1, T2, R, T4> mapT3(Function<T3, R> function) {
        return new Tuple4<>(this.f2301t1, this.f2302t2, function.apply(this.f2303t3), this.f2304t4);
    }

    public <R> Tuple4<T1, T2, T3, R> mapT4(Function<T4, R> function) {
        return new Tuple4<>(this.f2301t1, this.f2302t2, this.f2303t3, function.apply(this.f2304t4));
    }

    @Override // reactor.util.function.Tuple3, reactor.util.function.Tuple2
    @Nullable
    public Object get(int i) {
        if (i == 0) {
            return this.f2301t1;
        }
        if (i == 1) {
            return this.f2302t2;
        }
        if (i == 2) {
            return this.f2303t3;
        }
        if (i != 3) {
            return null;
        }
        return this.f2304t4;
    }

    @Override // reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public Object[] toArray() {
        return new Object[]{this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4};
    }

    @Override // reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof Tuple4) && super.equals(obj)) {
            return this.f2304t4.equals(((Tuple4) obj).f2304t4);
        }
        return false;
    }

    @Override // reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public int hashCode() {
        return (super.hashCode() * 31) + this.f2304t4.hashCode();
    }
}
