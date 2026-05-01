package reactor.util.function;

import java.util.Objects;
import java.util.function.Function;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public class Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> extends Tuple7<T1, T2, T3, T4, T5, T6, T7> {
    private static final long serialVersionUID = -8746796646535446242L;

    /* JADX INFO: renamed from: t8 */
    @NonNull
    final T8 f2308t8;

    @Override // reactor.util.function.Tuple7, reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public int size() {
        return 8;
    }

    Tuple8(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        super(t1, t2, t3, t4, t5, t6, t7);
        this.f2308t8 = (T8) Objects.requireNonNull(t8, "t8");
    }

    public T8 getT8() {
        return this.f2308t8;
    }

    @Override // reactor.util.function.Tuple7, reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public <R> Tuple8<R, T2, T3, T4, T5, T6, T7, T8> mapT1(Function<T1, R> function) {
        return new Tuple8<>(function.apply(this.f2301t1), this.f2302t2, this.f2303t3, this.f2304t4, this.f2305t5, this.f2306t6, this.f2307t7, this.f2308t8);
    }

    @Override // reactor.util.function.Tuple7, reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public <R> Tuple8<T1, R, T3, T4, T5, T6, T7, T8> mapT2(Function<T2, R> function) {
        return new Tuple8<>(this.f2301t1, function.apply(this.f2302t2), this.f2303t3, this.f2304t4, this.f2305t5, this.f2306t6, this.f2307t7, this.f2308t8);
    }

    @Override // reactor.util.function.Tuple7, reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3
    public <R> Tuple8<T1, T2, R, T4, T5, T6, T7, T8> mapT3(Function<T3, R> function) {
        return new Tuple8<>(this.f2301t1, this.f2302t2, function.apply(this.f2303t3), this.f2304t4, this.f2305t5, this.f2306t6, this.f2307t7, this.f2308t8);
    }

    @Override // reactor.util.function.Tuple7, reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4
    public <R> Tuple8<T1, T2, T3, R, T5, T6, T7, T8> mapT4(Function<T4, R> function) {
        return new Tuple8<>(this.f2301t1, this.f2302t2, this.f2303t3, function.apply(this.f2304t4), this.f2305t5, this.f2306t6, this.f2307t7, this.f2308t8);
    }

    @Override // reactor.util.function.Tuple7, reactor.util.function.Tuple6, reactor.util.function.Tuple5
    public <R> Tuple8<T1, T2, T3, T4, R, T6, T7, T8> mapT5(Function<T5, R> function) {
        return new Tuple8<>(this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4, function.apply(this.f2305t5), this.f2306t6, this.f2307t7, this.f2308t8);
    }

    @Override // reactor.util.function.Tuple7, reactor.util.function.Tuple6
    public <R> Tuple8<T1, T2, T3, T4, T5, R, T7, T8> mapT6(Function<T6, R> function) {
        return new Tuple8<>(this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4, this.f2305t5, function.apply(this.f2306t6), this.f2307t7, this.f2308t8);
    }

    @Override // reactor.util.function.Tuple7
    public <R> Tuple8<T1, T2, T3, T4, T5, T6, R, T8> mapT7(Function<T7, R> function) {
        return new Tuple8<>(this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4, this.f2305t5, this.f2306t6, function.apply(this.f2307t7), this.f2308t8);
    }

    public <R> Tuple8<T1, T2, T3, T4, T5, T6, T7, R> mapT8(Function<T8, R> function) {
        return new Tuple8<>(this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4, this.f2305t5, this.f2306t6, this.f2307t7, function.apply(this.f2308t8));
    }

    @Override // reactor.util.function.Tuple7, reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    @Nullable
    public Object get(int i) {
        switch (i) {
            case 0:
                return this.f2301t1;
            case 1:
                return this.f2302t2;
            case 2:
                return this.f2303t3;
            case 3:
                return this.f2304t4;
            case 4:
                return this.f2305t5;
            case 5:
                return this.f2306t6;
            case 6:
                return this.f2307t7;
            case 7:
                return this.f2308t8;
            default:
                return null;
        }
    }

    @Override // reactor.util.function.Tuple7, reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public Object[] toArray() {
        return new Object[]{this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4, this.f2305t5, this.f2306t6, this.f2307t7, this.f2308t8};
    }

    @Override // reactor.util.function.Tuple7, reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof Tuple8) && super.equals(obj)) {
            return this.f2308t8.equals(((Tuple8) obj).f2308t8);
        }
        return false;
    }

    @Override // reactor.util.function.Tuple7, reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public int hashCode() {
        return (super.hashCode() * 31) + this.f2308t8.hashCode();
    }
}
