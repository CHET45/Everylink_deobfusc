package com.fasterxml.jackson.datatype.jsr310.util;

import com.github.houbb.heaven.constant.PunctuationConst;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes3.dex */
public class DurationUnitConverter {
    private static final Map<String, DurationSerialization> UNITS;
    final DurationSerialization serialization;

    protected static class DurationSerialization {
        final Function<Long, Duration> deserializer;
        final Function<Duration, Long> serializer;

        DurationSerialization(Function<Duration, Long> function, Function<Long, Duration> function2) {
            this.serializer = function;
            this.deserializer = function2;
        }

        static Function<Long, Duration> deserializer(final TemporalUnit temporalUnit) {
            return new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter$DurationSerialization$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Duration.of(((Long) obj).longValue(), temporalUnit);
                }
            };
        }
    }

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(ChronoUnit.NANOS.name(), new DurationSerialization(new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Long.valueOf(((Duration) obj).toNanos());
            }
        }, DurationSerialization.deserializer(ChronoUnit.NANOS)));
        linkedHashMap.put(ChronoUnit.MICROS.name(), new DurationSerialization(new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Long.valueOf(((Duration) obj).toNanos() / 1000);
            }
        }, DurationSerialization.deserializer(ChronoUnit.MICROS)));
        linkedHashMap.put(ChronoUnit.MILLIS.name(), new DurationSerialization(new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Long.valueOf(((Duration) obj).toMillis());
            }
        }, DurationSerialization.deserializer(ChronoUnit.MILLIS)));
        linkedHashMap.put(ChronoUnit.SECONDS.name(), new DurationSerialization(new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Long.valueOf(((Duration) obj).getSeconds());
            }
        }, DurationSerialization.deserializer(ChronoUnit.SECONDS)));
        linkedHashMap.put(ChronoUnit.MINUTES.name(), new DurationSerialization(new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Long.valueOf(((Duration) obj).toMinutes());
            }
        }, DurationSerialization.deserializer(ChronoUnit.MINUTES)));
        linkedHashMap.put(ChronoUnit.HOURS.name(), new DurationSerialization(new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Long.valueOf(((Duration) obj).toHours());
            }
        }, DurationSerialization.deserializer(ChronoUnit.HOURS)));
        linkedHashMap.put(ChronoUnit.HALF_DAYS.name(), new DurationSerialization(new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Long.valueOf(((Duration) obj).toHours() / 12);
            }
        }, DurationSerialization.deserializer(ChronoUnit.HALF_DAYS)));
        linkedHashMap.put(ChronoUnit.DAYS.name(), new DurationSerialization(new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Long.valueOf(((Duration) obj).toDays());
            }
        }, DurationSerialization.deserializer(ChronoUnit.DAYS)));
        UNITS = linkedHashMap;
    }

    DurationUnitConverter(DurationSerialization durationSerialization) {
        this.serialization = durationSerialization;
    }

    public Duration convert(long j) {
        return this.serialization.deserializer.apply(Long.valueOf(j));
    }

    public long convert(Duration duration) {
        return this.serialization.serializer.apply(duration).longValue();
    }

    public static String descForAllowed() {
        return PunctuationConst.DOUBLE_QUOTES + ((String) UNITS.keySet().stream().collect(Collectors.joining("\", \""))) + PunctuationConst.DOUBLE_QUOTES;
    }

    public static DurationUnitConverter from(String str) {
        DurationSerialization durationSerialization = UNITS.get(str);
        if (durationSerialization == null) {
            return null;
        }
        return new DurationUnitConverter(durationSerialization);
    }
}
