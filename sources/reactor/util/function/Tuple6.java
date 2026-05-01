package reactor.util.function;

import java.util.Objects;
import java.util.function.Function;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public class Tuple6<T1, T2, T3, T4, T5, T6> extends Tuple5<T1, T2, T3, T4, T5> {
    private static final long serialVersionUID = 770306356087176830L;

    /* JADX INFO: renamed from: t6 */
    @NonNull
    final T6 f2306t6;

    @Override // reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public int size() {
        return 6;
    }

    Tuple6(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        super(t1, t2, t3, t4, t5);
        this.f2306t6 = (T6) Objects.requireNonNull(t6, "t6");
    }

    public T6 getT6() {
        return this.f2306t6;
    }

    @Override // reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public <R> Tuple6<R, T2, T3, T4, T5, T6> mapT1(Function<T1, R> function) {
        return new Tuple6<>(function.apply(this.f2301t1), this.f2302t2, this.f2303t3, this.f2304t4, this.f2305t5, this.f2306t6);
    }

    @Override // reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public <R> Tuple6<T1, R, T3, T4, T5, T6> mapT2(Function<T2, R> function) {
        return new Tuple6<>(this.f2301t1, function.apply(this.f2302t2), this.f2303t3, this.f2304t4, this.f2305t5, this.f2306t6);
    }

    @Override // reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3
    public <R> Tuple6<T1, T2, R, T4, T5, T6> mapT3(Function<T3, R> function) {
        return new Tuple6<>(this.f2301t1, this.f2302t2, function.apply(this.f2303t3), this.f2304t4, this.f2305t5, this.f2306t6);
    }

    @Override // reactor.util.function.Tuple5, reactor.util.function.Tuple4
    public <R> Tuple6<T1, T2, T3, R, T5, T6> mapT4(Function<T4, R> function) {
        return new Tuple6<>(this.f2301t1, this.f2302t2, this.f2303t3, function.apply(this.f2304t4), this.f2305t5, this.f2306t6);
    }

    @Override // reactor.util.function.Tuple5
    public <R> Tuple6<T1, T2, T3, T4, R, T6> mapT5(Function<T5, R> function) {
        return new Tuple6<>(this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4, function.apply(this.f2305t5), this.f2306t6);
    }

    public <R> Tuple6<T1, T2, T3, T4, T5, R> mapT6(Function<T6, R> function) {
        return new Tuple6<>(this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4, this.f2305t5, function.apply(this.f2306t6));
    }

    @Override // reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
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
        if (i == 3) {
            return this.f2304t4;
        }
        if (i == 4) {
            return this.f2305t5;
        }
        if (i != 5) {
            return null;
        }
        return this.f2306t6;
    }

    @Override // reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public Object[] toArray() {
        return new Object[]{this.f2301t1, this.f2302t2, this.f2303t3, this.f2304t4, this.f2305t5, this.f2306t6};
    }

    @Override // reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof Tuple6) && super.equals(obj)) {
            return this.f2306t6.equals(((Tuple6) obj).f2306t6);
        }
        return false;
    }

    @Override // reactor.util.function.Tuple5, reactor.util.function.Tuple4, reactor.util.function.Tuple3, reactor.util.function.Tuple2
    public int hashCode() {
        return (super.hashCode() * 31) + this.f2306t6.hashCode();
    }
}
