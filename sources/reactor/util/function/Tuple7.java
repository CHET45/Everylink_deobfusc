package reactor.util.function;

import java.util.Objects;
import java.util.function.Function;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public class Tuple7<T1, T2, T3, T4, T5, T6, T7> extends Tuple6<T1, T2, T3, T4, T5, T6> {
    private static final long serialVersionUID = -8002391247456579281L;

    /* JADX INFO: renamed from: t7 */
    @NonNull
    final T7 f2307t7;

    @Override // reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public int size() {
        return 7;
    }

    Tuple7(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        super(t1, t2, t3, t4, t5, t6);
        this.f2307t7 = (T7) Objects.requireNonNull(t7, "t7");
    }

    public T7 getT7() {
        return this.f2307t7;
    }

    @Override // reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public <R> Tuple7<R, T2, T3, T4, T5, T6, T7> mapT1(Function<T1, R> function) {
        return new Tuple7<>(function.apply(this.f2301t1), this.f2302t2, this.f2303t3, this.f2304t4, this.f2305t5, this.f2306t6, this.f2307t7);
    }

    @Override // reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public <R> Tuple7<T1, R, T3, T4, T5, T6, T7> mapT2(Function<T2, R> function) {
        return new Tuple7<>(this.f2301t1, function.apply(this.f2302t2), this.f2303t3, this.f2304t4, this.f2305t5, this.f2306t6, this.f2307t7);
    }

    @Override // reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3
    public <R> Tuple7<T1, T2, R, T4, T5, T6, T7> mapT3(Function<T3, R> function) {
        return new Tuple7<>(this.f2301t1, this.f2302t2, function.apply(this.f2303t3), this.f2304t4, this.f2305t5, this.f2306t6, this.f2307t7);
    }

    @Override // reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4
    public <R> Tuple7<T1, T2, T3, R, T5, T6, T7> mapT4(Function<T4, R> function) {
        return new Tuple7<>(this.f2301t1, this.f2302t2, this.f2303t3, function.apply(this.f2304t4), this.f2305t5, this.f2306t6, this.f2307t7);
    }

    @Override // reactor.util.function.Tuple6, reactor.util.function.Tuple5
    public <R> Tuple7<T1, T2, T3, T4, R, T6, T7> mapT5(Function<T5, R> function) {
        return new Tuple7<>(this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4, function.apply(this.f2305t5), this.f2306t6, this.f2307t7);
    }

    @Override // reactor.util.function.Tuple6
    public <R> Tuple7<T1, T2, T3, T4, T5, R, T7> mapT6(Function<T6, R> function) {
        return new Tuple7<>(this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4, this.f2305t5, function.apply(this.f2306t6), this.f2307t7);
    }

    public <R> Tuple7<T1, T2, T3, T4, T5, T6, R> mapT7(Function<T7, R> function) {
        return new Tuple7<>(this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4, this.f2305t5, this.f2306t6, function.apply(this.f2307t7));
    }

    @Override // reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
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
            default:
                return null;
        }
    }

    @Override // reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public Object[] toArray() {
        return new Object[]{this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4, this.f2305t5, this.f2306t6, this.f2307t7};
    }

    @Override // reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof Tuple7) && super.equals(obj)) {
            return this.f2307t7.equals(((Tuple7) obj).f2307t7);
        }
        return false;
    }

    @Override // reactor.util.function.Tuple6, reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public int hashCode() {
        return (super.hashCode() * 31) + this.f2307t7.hashCode();
    }
}
