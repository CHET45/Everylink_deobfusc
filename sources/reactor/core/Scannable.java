package reactor.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterators;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Scannable {
    public static final Pattern OPERATOR_NAME_UNRELATED_WORDS_PATTERN = Pattern.compile("Parallel|Flux|Mono|Publisher|Subscriber|Fuseable|Operator|Conditional");

    static /* synthetic */ LinkedHashMap $r8$lambda$9jZrLfmN7qos7GZMvkzgNgs9WjU() {
        return new LinkedHashMap();
    }

    static /* synthetic */ String lambda$tagsDeduplicated$2(String str, String str2) {
        return str2;
    }

    default boolean isScanAvailable() {
        return true;
    }

    @Nullable
    Object scanUnsafe(Attr attr);

    public static class Attr<T> {
        final T defaultValue;
        final Function<Object, ? extends T> safeConverter;
        public static final Attr<Scannable> ACTUAL = new Attr<>(null, new Scannable$Attr$$ExternalSyntheticLambda0());
        public static final Attr<Boolean> ACTUAL_METADATA = new Attr<>(false);
        public static final Attr<Integer> BUFFERED = new Attr<>(0);
        public static final Attr<Integer> CAPACITY = new Attr<>(0);
        public static final Attr<Boolean> CANCELLED = new Attr<>(false);
        public static final Attr<Boolean> DELAY_ERROR = new Attr<>(false);
        public static final Attr<Throwable> ERROR = new Attr<>(null);
        public static final Attr<Long> LARGE_BUFFERED = new Attr<>(null);
        public static final Attr<String> NAME = new Attr<>(null);
        public static final Attr<Scannable> PARENT = new Attr<>(null, new Scannable$Attr$$ExternalSyntheticLambda0());
        public static final Attr<Scannable> RUN_ON = new Attr<>(null, new Scannable$Attr$$ExternalSyntheticLambda0());
        public static final Attr<Integer> PREFETCH = new Attr<>(0);
        public static final Attr<Long> REQUESTED_FROM_DOWNSTREAM = new Attr<>(0L);
        public static final Attr<Boolean> TERMINATED = new Attr<>(false);
        public static final Attr<Stream<Tuple2<String, String>>> TAGS = new Attr<>(null);
        public static final Attr<RunStyle> RUN_STYLE = new Attr<>(RunStyle.UNKNOWN);
        public static final Attr<String> LIFTER = new Attr<>(null);
        static final Scannable UNAVAILABLE_SCAN = new Scannable() { // from class: reactor.core.Scannable.Attr.1
            @Override // reactor.core.Scannable
            public boolean isScanAvailable() {
                return false;
            }

            @Override // reactor.core.Scannable
            public Object scanUnsafe(Attr attr) {
                return null;
            }

            public String toString() {
                return "UNAVAILABLE_SCAN";
            }

            @Override // reactor.core.Scannable
            public String stepName() {
                return "UNAVAILABLE_SCAN";
            }
        };
        static final Scannable NULL_SCAN = new Scannable() { // from class: reactor.core.Scannable.Attr.2
            @Override // reactor.core.Scannable
            public boolean isScanAvailable() {
                return false;
            }

            @Override // reactor.core.Scannable
            public Object scanUnsafe(Attr attr) {
                return null;
            }

            public String toString() {
                return "NULL_SCAN";
            }

            @Override // reactor.core.Scannable
            public String stepName() {
                return "NULL_SCAN";
            }
        };

        public enum RunStyle {
            UNKNOWN,
            ASYNC,
            SYNC
        }

        @Nullable
        public T defaultValue() {
            return this.defaultValue;
        }

        boolean isConversionSafe() {
            return this.safeConverter != null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Nullable
        T tryConvert(@Nullable Object obj) {
            if (obj == 0) {
                return null;
            }
            Function<Object, ? extends T> function = this.safeConverter;
            return function == null ? obj : function.apply(obj);
        }

        protected Attr(@Nullable T t) {
            this(t, null);
        }

        protected Attr(@Nullable T t, @Nullable Function<Object, ? extends T> function) {
            this.defaultValue = t;
            this.safeConverter = function;
        }

        static Stream<? extends Scannable> recurse(Scannable scannable, Attr<Scannable> attr) {
            Scannable scannableFrom = Scannable.from(scannable.scan(attr));
            if (!scannableFrom.isScanAvailable()) {
                return Stream.empty();
            }
            return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<Scannable>(attr) { // from class: reactor.core.Scannable.Attr.3

                /* JADX INFO: renamed from: c */
                Scannable f2076c;
                final /* synthetic */ Attr val$key;

                {
                    this.val$key = attr;
                    this.f2076c = this.val$s;
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    Scannable scannable2 = this.f2076c;
                    return scannable2 != null && scannable2.isScanAvailable();
                }

                @Override // java.util.Iterator
                public Scannable next() {
                    Scannable scannable2 = this.f2076c;
                    this.f2076c = Scannable.from(scannable2.scan(this.val$key));
                    return scannable2;
                }
            }, 0), false);
        }
    }

    static Scannable from(@Nullable Object obj) {
        if (obj == null) {
            return Attr.NULL_SCAN;
        }
        if (obj instanceof Scannable) {
            return (Scannable) obj;
        }
        return Attr.UNAVAILABLE_SCAN;
    }

    default Stream<? extends Scannable> actuals() {
        return Attr.recurse(this, Attr.ACTUAL);
    }

    default Stream<? extends Scannable> inners() {
        return Stream.empty();
    }

    default String name() {
        String str = (String) scan(Attr.NAME);
        return str != null ? str : (String) parents().map(new Function() { // from class: reactor.core.Scannable$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Scannable.lambda$name$0((Scannable) obj);
            }
        }).filter(new Predicate() { // from class: reactor.core.Scannable$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((String) obj);
            }
        }).findFirst().orElseGet(new Supplier() { // from class: reactor.core.Scannable$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.stepName();
            }
        });
    }

    static /* synthetic */ String lambda$name$0(Scannable scannable) {
        return (String) scannable.scan(Attr.NAME);
    }

    default String stepName() {
        String name = getClass().getName();
        int iIndexOf = name.indexOf(36);
        if (iIndexOf != -1) {
            name = name.substring(0, iIndexOf);
        }
        int iLastIndexOf = name.lastIndexOf(46);
        if (iLastIndexOf != -1) {
            name = name.substring(iLastIndexOf + 1);
        }
        String strReplaceAll = OPERATOR_NAME_UNRELATED_WORDS_PATTERN.matcher(name).replaceAll("");
        return !strReplaceAll.isEmpty() ? strReplaceAll.substring(0, 1).toLowerCase() + strReplaceAll.substring(1) : strReplaceAll;
    }

    default Stream<String> steps() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll((Collection) parents().collect(Collectors.toList()));
        Collections.reverse(arrayList);
        arrayList.add(this);
        arrayList.addAll((Collection) actuals().collect(Collectors.toList()));
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        int i = 0;
        while (i < arrayList.size()) {
            Scannable scannable = (Scannable) arrayList.get(i);
            Scannable scannable2 = i < arrayList.size() + (-1) ? (Scannable) arrayList.get(i + 1) : null;
            if (scannable2 != null && ((Boolean) scannable2.scan(Attr.ACTUAL_METADATA)).booleanValue()) {
                arrayList2.add(scannable2.stepName());
                i++;
            } else {
                arrayList2.add(scannable.stepName());
            }
            i++;
        }
        return arrayList2.stream();
    }

    default Stream<? extends Scannable> parents() {
        return Attr.recurse(this, Attr.PARENT);
    }

    @Nullable
    default <T> T scan(Attr<T> attr) {
        T tTryConvert = attr.tryConvert(scanUnsafe(attr));
        return tTryConvert == null ? attr.defaultValue() : tTryConvert;
    }

    default <T> T scanOrDefault(Attr<T> attr, T t) {
        T tTryConvert = attr.tryConvert(scanUnsafe(attr));
        return tTryConvert == null ? (T) Objects.requireNonNull(t, "defaultValue") : tTryConvert;
    }

    default Stream<Tuple2<String, String>> tags() {
        LinkedList linkedList = new LinkedList();
        for (Scannable scannable = this; scannable != null && scannable.isScanAvailable(); scannable = (Scannable) scannable.scan(Attr.PARENT)) {
            linkedList.add(0, scannable);
        }
        return linkedList.stream().flatMap(new Function() { // from class: reactor.core.Scannable$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Scannable.lambda$tags$1((Scannable) obj);
            }
        });
    }

    static /* synthetic */ Stream lambda$tags$1(Scannable scannable) {
        return (Stream) scannable.scanOrDefault(Attr.TAGS, Stream.empty());
    }

    @Deprecated
    default Map<String, String> tagsDeduplicated() {
        return (Map) tags().collect(Collectors.toMap(new Function() { // from class: reactor.core.Scannable$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (String) ((Tuple2) obj).getT1();
            }
        }, new Function() { // from class: reactor.core.Scannable$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (String) ((Tuple2) obj).getT2();
            }
        }, new BinaryOperator() { // from class: reactor.core.Scannable$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Scannable.lambda$tagsDeduplicated$2((String) obj, (String) obj2);
            }
        }, new Supplier() { // from class: reactor.core.Scannable$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return Scannable.$r8$lambda$9jZrLfmN7qos7GZMvkzgNgs9WjU();
            }
        }));
    }
}
