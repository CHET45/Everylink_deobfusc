package reactor.util.function;

import com.github.houbb.heaven.constant.CharConst;
import java.util.function.Function;

/* JADX INFO: loaded from: classes5.dex */
public abstract class Tuples implements Function {
    static final Tuples empty = new Tuples() { // from class: reactor.util.function.Tuples.1
        @Override // reactor.util.function.Tuples, java.util.function.Function
        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            return super.apply(obj);
        }
    };

    public static Tuple2 fromArray(Object[] objArr) {
        if (objArr == null || objArr.length < 2) {
            throw new IllegalArgumentException("null or too small array, need between 2 and 8 values");
        }
        switch (objArr.length) {
            case 2:
                return m1988of(objArr[0], objArr[1]);
            case 3:
                return m1989of(objArr[0], objArr[1], objArr[2]);
            case 4:
                return m1990of(objArr[0], objArr[1], objArr[2], objArr[3]);
            case 5:
                return m1991of(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4]);
            case 6:
                return m1992of(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 7:
                return m1993of(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6]);
            case 8:
                return m1994of(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7]);
            default:
                throw new IllegalArgumentException("too many arguments (" + objArr.length + "), need between 2 and 8 values");
        }
    }

    /* JADX INFO: renamed from: of */
    public static <T1, T2> Tuple2<T1, T2> m1988of(T1 t1, T2 t2) {
        return new Tuple2<>(t1, t2);
    }

    /* JADX INFO: renamed from: of */
    public static <T1, T2, T3> Tuple3<T1, T2, T3> m1989of(T1 t1, T2 t2, T3 t3) {
        return new Tuple3<>(t1, t2, t3);
    }

    /* JADX INFO: renamed from: of */
    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> m1990of(T1 t1, T2 t2, T3 t3, T4 t4) {
        return new Tuple4<>(t1, t2, t3, t4);
    }

    /* JADX INFO: renamed from: of */
    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> m1991of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return new Tuple5<>(t1, t2, t3, t4, t5);
    }

    /* JADX INFO: renamed from: of */
    public static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> m1992of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return new Tuple6<>(t1, t2, t3, t4, t5, t6);
    }

    /* JADX INFO: renamed from: of */
    public static <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> m1993of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        return new Tuple7<>(t1, t2, t3, t4, t5, t6, t7);
    }

    /* JADX INFO: renamed from: of */
    public static <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> m1994of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        return new Tuple8<>(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    public static Function<Object[], Tuple2> fnAny() {
        return empty;
    }

    public static <R> Function<Object[], R> fnAny(final Function<Tuple2, R> function) {
        return new Function() { // from class: reactor.util.function.Tuples$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return function.apply(Tuples.fnAny().apply((Object[]) obj));
            }
        };
    }

    public static <T1, T2> Function<Object[], Tuple2<T1, T2>> fn2() {
        return empty;
    }

    public static <T1, T2, T3> Function<Object[], Tuple3<T1, T2, T3>> fn3() {
        return empty;
    }

    public static <T1, T2, T3, R> Function<Object[], R> fn3(final Function<Tuple3<T1, T2, T3>, R> function) {
        return new Function() { // from class: reactor.util.function.Tuples$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return function.apply((Tuple3) Tuples.fn3().apply((Object[]) obj));
            }
        };
    }

    public static <T1, T2, T3, T4> Function<Object[], Tuple4<T1, T2, T3, T4>> fn4() {
        return empty;
    }

    public static <T1, T2, T3, T4, R> Function<Object[], R> fn4(final Function<Tuple4<T1, T2, T3, T4>, R> function) {
        return new Function() { // from class: reactor.util.function.Tuples$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return function.apply((Tuple4) Tuples.fn4().apply((Object[]) obj));
            }
        };
    }

    public static <T1, T2, T3, T4, T5> Function<Object[], Tuple5<T1, T2, T3, T4, T5>> fn5() {
        return empty;
    }

    public static <T1, T2, T3, T4, T5, R> Function<Object[], R> fn5(final Function<Tuple5<T1, T2, T3, T4, T5>, R> function) {
        return new Function() { // from class: reactor.util.function.Tuples$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return function.apply((Tuple5) Tuples.fn5().apply((Object[]) obj));
            }
        };
    }

    public static <T1, T2, T3, T4, T5, T6> Function<Object[], Tuple6<T1, T2, T3, T4, T5, T6>> fn6() {
        return empty;
    }

    public static <T1, T2, T3, T4, T5, T6, R> Function<Object[], R> fn6(final Function<Tuple6<T1, T2, T3, T4, T5, T6>, R> function) {
        return new Function() { // from class: reactor.util.function.Tuples$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return function.apply((Tuple6) Tuples.fn6().apply((Object[]) obj));
            }
        };
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Function<Object[], Tuple7<T1, T2, T3, T4, T5, T6, T7>> fn7() {
        return empty;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> Function<Object[], R> fn7(final Function<Tuple7<T1, T2, T3, T4, T5, T6, T7>, R> function) {
        return new Function() { // from class: reactor.util.function.Tuples$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return function.apply((Tuple7) Tuples.fn7().apply((Object[]) obj));
            }
        };
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Function<Object[], Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> fn8() {
        return empty;
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Function<Object[], R> fn8(final Function<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>, R> function) {
        return new Function() { // from class: reactor.util.function.Tuples$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return function.apply((Tuple8) Tuples.fn8().apply((Object[]) obj));
            }
        };
    }

    @Override // java.util.function.Function
    public Tuple2 apply(Object obj) {
        return fromArray((Object[]) obj);
    }

    static StringBuilder tupleStringRepresentation(Object... objArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (i != 0) {
                sb.append(CharConst.COMMA);
            }
            if (obj != null) {
                sb.append(obj);
            }
        }
        return sb;
    }

    Tuples() {
    }
}
