package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.p008io.NumberInput;
import com.fasterxml.jackson.core.util.JacksonFeatureSet;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public class InstantDeserializer<T extends Temporal> extends JSR310DateTimeDeserializerBase<T> {
    private static final boolean DEFAULT_ALWAYS_ALLOW_STRINGIFIED_DATE_TIMESTAMPS;
    private static final boolean DEFAULT_NORMALIZE_ZONE_ID;
    public static final InstantDeserializer<Instant> INSTANT;
    protected static final Pattern ISO8601_COLONLESS_OFFSET_REGEX;
    public static final InstantDeserializer<OffsetDateTime> OFFSET_DATE_TIME;
    public static final InstantDeserializer<ZonedDateTime> ZONED_DATE_TIME;
    private static final long serialVersionUID = 1;
    protected final Boolean _adjustToContextTZOverride;
    protected final boolean _alwaysAllowStringifiedDateTimestamps;
    protected final boolean _normalizeZoneId;
    protected final Boolean _readTimestampsAsNanosOverride;
    protected final BiFunction<T, ZoneId, T> adjust;
    protected final Function<FromIntegerArguments, T> fromMilliseconds;
    protected final Function<FromDecimalArguments, T> fromNanoseconds;
    protected final Function<TemporalAccessor, T> parsedToValue;
    protected final boolean replaceZeroOffsetAsZ;

    static /* synthetic */ Temporal lambda$new$6(Temporal temporal, ZoneId zoneId) {
        return temporal;
    }

    static {
        boolean zEnabledByDefault = JavaTimeFeature.NORMALIZE_DESERIALIZED_ZONE_ID.enabledByDefault();
        DEFAULT_NORMALIZE_ZONE_ID = zEnabledByDefault;
        boolean zEnabledByDefault2 = JavaTimeFeature.ALWAYS_ALLOW_STRINGIFIED_DATE_TIMESTAMPS.enabledByDefault();
        DEFAULT_ALWAYS_ALLOW_STRINGIFIED_DATE_TIMESTAMPS = zEnabledByDefault2;
        ISO8601_COLONLESS_OFFSET_REGEX = Pattern.compile("[+-][0-9]{4}(?=\\[|$)");
        INSTANT = new InstantDeserializer<>(Instant.class, DateTimeFormatter.ISO_INSTANT, new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Instant.from((TemporalAccessor) obj);
            }
        }, new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Instant.ofEpochMilli(((InstantDeserializer.FromIntegerArguments) obj).value);
            }
        }, new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Instant.ofEpochSecond(((InstantDeserializer.FromDecimalArguments) obj).integer, r1.fraction);
            }
        }, null, true, zEnabledByDefault, zEnabledByDefault2);
        OFFSET_DATE_TIME = new InstantDeserializer<>(OffsetDateTime.class, DateTimeFormatter.ISO_OFFSET_DATE_TIME, new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return OffsetDateTime.from((TemporalAccessor) obj);
            }
        }, new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                InstantDeserializer.FromIntegerArguments fromIntegerArguments = (InstantDeserializer.FromIntegerArguments) obj;
                return OffsetDateTime.ofInstant(Instant.ofEpochMilli(fromIntegerArguments.value), fromIntegerArguments.zoneId);
            }
        }, new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InstantDeserializer.decimalToOffsetDateTime((InstantDeserializer.FromDecimalArguments) obj);
            }
        }, new BiFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda12
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return InstantDeserializer.lambda$static$3((OffsetDateTime) obj, (ZoneId) obj2);
            }
        }, true, zEnabledByDefault, zEnabledByDefault2);
        ZONED_DATE_TIME = new InstantDeserializer<>(ZonedDateTime.class, DateTimeFormatter.ISO_ZONED_DATE_TIME, new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ZonedDateTime.from((TemporalAccessor) obj);
            }
        }, new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                InstantDeserializer.FromIntegerArguments fromIntegerArguments = (InstantDeserializer.FromIntegerArguments) obj;
                return ZonedDateTime.ofInstant(Instant.ofEpochMilli(fromIntegerArguments.value), fromIntegerArguments.zoneId);
            }
        }, new Function() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                InstantDeserializer.FromDecimalArguments fromDecimalArguments = (InstantDeserializer.FromDecimalArguments) obj;
                return ZonedDateTime.ofInstant(Instant.ofEpochSecond(fromDecimalArguments.integer, fromDecimalArguments.fraction), fromDecimalArguments.zoneId);
            }
        }, new BiFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda6
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((ZonedDateTime) obj).withZoneSameInstant((ZoneId) obj2);
            }
        }, false, zEnabledByDefault, zEnabledByDefault2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static OffsetDateTime decimalToOffsetDateTime(FromDecimalArguments fromDecimalArguments) {
        if (fromDecimalArguments.integer == OffsetDateTime.MIN.toEpochSecond() && fromDecimalArguments.fraction == OffsetDateTime.MIN.getNano()) {
            return OffsetDateTime.ofInstant(Instant.ofEpochSecond(OffsetDateTime.MIN.toEpochSecond(), OffsetDateTime.MIN.getNano()), OffsetDateTime.MIN.getOffset());
        }
        if (fromDecimalArguments.integer == OffsetDateTime.MAX.toEpochSecond() && fromDecimalArguments.fraction == OffsetDateTime.MAX.getNano()) {
            return OffsetDateTime.ofInstant(Instant.ofEpochSecond(OffsetDateTime.MAX.toEpochSecond(), OffsetDateTime.MAX.getNano()), OffsetDateTime.MAX.getOffset());
        }
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(fromDecimalArguments.integer, fromDecimalArguments.fraction), fromDecimalArguments.zoneId);
    }

    static /* synthetic */ OffsetDateTime lambda$static$3(OffsetDateTime offsetDateTime, ZoneId zoneId) {
        return (offsetDateTime.isEqual(OffsetDateTime.MIN) || offsetDateTime.isEqual(OffsetDateTime.MAX)) ? offsetDateTime : offsetDateTime.withOffsetSameInstant(zoneId.getRules().getOffset(offsetDateTime.toLocalDateTime()));
    }

    protected InstantDeserializer(Class<T> cls, DateTimeFormatter dateTimeFormatter, Function<TemporalAccessor, T> function, Function<FromIntegerArguments, T> function2, Function<FromDecimalArguments, T> function3, BiFunction<T, ZoneId, T> biFunction, boolean z, boolean z2, boolean z3) {
        super(cls, dateTimeFormatter);
        this.parsedToValue = function;
        this.fromMilliseconds = function2;
        this.fromNanoseconds = function3;
        this.adjust = biFunction == null ? new BiFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return InstantDeserializer.lambda$new$6((Temporal) obj, (ZoneId) obj2);
            }
        } : biFunction;
        this.replaceZeroOffsetAsZ = z;
        this._adjustToContextTZOverride = null;
        this._readTimestampsAsNanosOverride = null;
        this._normalizeZoneId = z2;
        this._alwaysAllowStringifiedDateTimestamps = z3;
    }

    @Deprecated
    protected InstantDeserializer(Class<T> cls, DateTimeFormatter dateTimeFormatter, Function<TemporalAccessor, T> function, Function<FromIntegerArguments, T> function2, Function<FromDecimalArguments, T> function3, BiFunction<T, ZoneId, T> biFunction, boolean z) {
        this(cls, dateTimeFormatter, function, function2, function3, biFunction, z, DEFAULT_NORMALIZE_ZONE_ID, DEFAULT_ALWAYS_ALLOW_STRINGIFIED_DATE_TIMESTAMPS);
    }

    protected InstantDeserializer(InstantDeserializer<T> instantDeserializer, DateTimeFormatter dateTimeFormatter) {
        super(instantDeserializer.handledType(), dateTimeFormatter);
        this.parsedToValue = instantDeserializer.parsedToValue;
        this.fromMilliseconds = instantDeserializer.fromMilliseconds;
        this.fromNanoseconds = instantDeserializer.fromNanoseconds;
        this.adjust = instantDeserializer.adjust;
        this.replaceZeroOffsetAsZ = this._formatter == DateTimeFormatter.ISO_INSTANT;
        this._adjustToContextTZOverride = instantDeserializer._adjustToContextTZOverride;
        this._readTimestampsAsNanosOverride = instantDeserializer._readTimestampsAsNanosOverride;
        this._normalizeZoneId = instantDeserializer._normalizeZoneId;
        this._alwaysAllowStringifiedDateTimestamps = instantDeserializer._alwaysAllowStringifiedDateTimestamps;
    }

    protected InstantDeserializer(InstantDeserializer<T> instantDeserializer, Boolean bool) {
        super(instantDeserializer.handledType(), instantDeserializer._formatter);
        this.parsedToValue = instantDeserializer.parsedToValue;
        this.fromMilliseconds = instantDeserializer.fromMilliseconds;
        this.fromNanoseconds = instantDeserializer.fromNanoseconds;
        this.adjust = instantDeserializer.adjust;
        this.replaceZeroOffsetAsZ = instantDeserializer.replaceZeroOffsetAsZ;
        this._adjustToContextTZOverride = bool;
        this._readTimestampsAsNanosOverride = instantDeserializer._readTimestampsAsNanosOverride;
        this._normalizeZoneId = instantDeserializer._normalizeZoneId;
        this._alwaysAllowStringifiedDateTimestamps = instantDeserializer._alwaysAllowStringifiedDateTimestamps;
    }

    protected InstantDeserializer(InstantDeserializer<T> instantDeserializer, DateTimeFormatter dateTimeFormatter, Boolean bool) {
        super(instantDeserializer.handledType(), dateTimeFormatter, bool);
        this.parsedToValue = instantDeserializer.parsedToValue;
        this.fromMilliseconds = instantDeserializer.fromMilliseconds;
        this.fromNanoseconds = instantDeserializer.fromNanoseconds;
        this.adjust = instantDeserializer.adjust;
        this.replaceZeroOffsetAsZ = this._formatter == DateTimeFormatter.ISO_INSTANT;
        this._adjustToContextTZOverride = instantDeserializer._adjustToContextTZOverride;
        this._readTimestampsAsNanosOverride = instantDeserializer._readTimestampsAsNanosOverride;
        this._normalizeZoneId = instantDeserializer._normalizeZoneId;
        this._alwaysAllowStringifiedDateTimestamps = instantDeserializer._alwaysAllowStringifiedDateTimestamps;
    }

    protected InstantDeserializer(InstantDeserializer<T> instantDeserializer, Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape, Boolean bool2, Boolean bool3) {
        super(instantDeserializer, bool, dateTimeFormatter, shape);
        this.parsedToValue = instantDeserializer.parsedToValue;
        this.fromMilliseconds = instantDeserializer.fromMilliseconds;
        this.fromNanoseconds = instantDeserializer.fromNanoseconds;
        this.adjust = instantDeserializer.adjust;
        this.replaceZeroOffsetAsZ = instantDeserializer.replaceZeroOffsetAsZ;
        this._adjustToContextTZOverride = bool2;
        this._readTimestampsAsNanosOverride = bool3;
        this._normalizeZoneId = instantDeserializer._normalizeZoneId;
        this._alwaysAllowStringifiedDateTimestamps = instantDeserializer._alwaysAllowStringifiedDateTimestamps;
    }

    protected InstantDeserializer(InstantDeserializer<T> instantDeserializer, JacksonFeatureSet<JavaTimeFeature> jacksonFeatureSet) {
        super(instantDeserializer.handledType(), instantDeserializer._formatter);
        this.parsedToValue = instantDeserializer.parsedToValue;
        this.fromMilliseconds = instantDeserializer.fromMilliseconds;
        this.fromNanoseconds = instantDeserializer.fromNanoseconds;
        this.adjust = instantDeserializer.adjust;
        this.replaceZeroOffsetAsZ = instantDeserializer.replaceZeroOffsetAsZ;
        this._adjustToContextTZOverride = instantDeserializer._adjustToContextTZOverride;
        this._readTimestampsAsNanosOverride = instantDeserializer._readTimestampsAsNanosOverride;
        this._normalizeZoneId = jacksonFeatureSet.isEnabled(JavaTimeFeature.NORMALIZE_DESERIALIZED_ZONE_ID);
        this._alwaysAllowStringifiedDateTimestamps = jacksonFeatureSet.isEnabled(JavaTimeFeature.ALWAYS_ALLOW_STRINGIFIED_DATE_TIMESTAMPS);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    public InstantDeserializer<T> withDateFormat(DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter == this._formatter ? this : new InstantDeserializer<>(this, dateTimeFormatter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase, com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase
    public InstantDeserializer<T> withLeniency(Boolean bool) {
        return new InstantDeserializer<>(this, this._formatter, bool);
    }

    public InstantDeserializer<T> withFeatures(JacksonFeatureSet<JavaTimeFeature> jacksonFeatureSet) {
        return (this._normalizeZoneId == jacksonFeatureSet.isEnabled(JavaTimeFeature.NORMALIZE_DESERIALIZED_ZONE_ID) && this._alwaysAllowStringifiedDateTimestamps == jacksonFeatureSet.isEnabled(JavaTimeFeature.ALWAYS_ALLOW_STRINGIFIED_DATE_TIMESTAMPS)) ? this : new InstantDeserializer<>(this, jacksonFeatureSet);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    protected JSR310DateTimeDeserializerBase<?> _withFormatOverrides(DeserializationContext deserializationContext, BeanProperty beanProperty, JsonFormat.Value value) {
        InstantDeserializer instantDeserializer = (InstantDeserializer) super._withFormatOverrides(deserializationContext, beanProperty, value);
        Boolean feature = value.getFeature(JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        Boolean feature2 = value.getFeature(JsonFormat.Feature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
        return (Objects.equals(feature, instantDeserializer._adjustToContextTZOverride) && Objects.equals(feature2, instantDeserializer._readTimestampsAsNanosOverride)) ? instantDeserializer : new InstantDeserializer(instantDeserializer, Boolean.valueOf(instantDeserializer._isLenient), instantDeserializer._formatter, instantDeserializer._shape, feature, feature2);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        int iCurrentTokenId = jsonParser.currentTokenId();
        if (iCurrentTokenId == 1) {
            return (T) _fromString(jsonParser, deserializationContext, deserializationContext.extractScalarFromObject(jsonParser, this, handledType()));
        }
        if (iCurrentTokenId == 3) {
            return _deserializeFromArray(jsonParser, deserializationContext);
        }
        if (iCurrentTokenId == 12) {
            return (T) jsonParser.getEmbeddedObject();
        }
        if (iCurrentTokenId == 6) {
            return (T) _fromString(jsonParser, deserializationContext, jsonParser.getText());
        }
        if (iCurrentTokenId != 7) {
            return iCurrentTokenId != 8 ? (T) _handleUnexpectedToken(deserializationContext, jsonParser, JsonToken.VALUE_STRING, JsonToken.VALUE_NUMBER_INT, JsonToken.VALUE_NUMBER_FLOAT) : (T) _fromDecimal(deserializationContext, jsonParser.getDecimalValue());
        }
        return (T) _fromLong(deserializationContext, jsonParser.getLongValue());
    }

    protected boolean shouldAdjustToContextTimezone(DeserializationContext deserializationContext) {
        Boolean bool = this._adjustToContextTZOverride;
        return bool != null ? bool.booleanValue() : deserializationContext.isEnabled(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    protected boolean shouldReadTimestampsAsNanoseconds(DeserializationContext deserializationContext) {
        Boolean bool = this._readTimestampsAsNanosOverride;
        return bool != null ? bool.booleanValue() : deserializationContext.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
    }

    protected int _countPeriods(String str, boolean z) {
        int i = 0;
        char cCharAt = str.charAt(0);
        int length = str.length();
        for (int i2 = (cCharAt == '-' || (cCharAt == '+' && z)) ? 1 : 0; i2 < length; i2++) {
            char cCharAt2 = str.charAt(i2);
            if (cCharAt2 < '0' || cCharAt2 > '9') {
                if (cCharAt2 != '.') {
                    return -1;
                }
                i++;
            }
        }
        return i;
    }

    protected T _fromString(JsonParser jsonParser, DeserializationContext deserializationContext, String str) throws IOException {
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return _fromEmptyString(jsonParser, deserializationContext, strTrim);
        }
        if (this._alwaysAllowStringifiedDateTimestamps || this._formatter == DateTimeFormatter.ISO_INSTANT || this._formatter == DateTimeFormatter.ISO_OFFSET_DATE_TIME || this._formatter == DateTimeFormatter.ISO_ZONED_DATE_TIME) {
            int i_countPeriods = _countPeriods(strTrim, jsonParser.isEnabled(JsonReadFeature.ALLOW_LEADING_PLUS_SIGN_FOR_NUMBERS.mappedFeature()));
            if (i_countPeriods >= 0) {
                try {
                    if (i_countPeriods == 0) {
                        return (T) _fromLong(deserializationContext, NumberInput.parseLong(strTrim));
                    }
                    if (i_countPeriods == 1) {
                        return (T) _fromDecimal(deserializationContext, NumberInput.parseBigDecimal(strTrim, false));
                    }
                } catch (NumberFormatException unused) {
                }
            }
            strTrim = replaceZeroOffsetAsZIfNecessary(strTrim);
        }
        if (this._formatter == DateTimeFormatter.ISO_OFFSET_DATE_TIME || this._formatter == DateTimeFormatter.ISO_ZONED_DATE_TIME) {
            strTrim = addInColonToOffsetIfMissing(strTrim);
        }
        try {
            T tApply = this.parsedToValue.apply(this._formatter.parse(strTrim));
            return shouldAdjustToContextTimezone(deserializationContext) ? this.adjust.apply(tApply, getZone(deserializationContext)) : tApply;
        } catch (DateTimeException e) {
            return (T) _handleDateTimeException(deserializationContext, e, strTrim);
        }
    }

    protected T _fromLong(DeserializationContext deserializationContext, long j) {
        if (shouldReadTimestampsAsNanoseconds(deserializationContext)) {
            return this.fromNanoseconds.apply(new FromDecimalArguments(j, 0, getZone(deserializationContext)));
        }
        return this.fromMilliseconds.apply(new FromIntegerArguments(j, getZone(deserializationContext)));
    }

    protected T _fromDecimal(final DeserializationContext deserializationContext, BigDecimal bigDecimal) {
        return this.fromNanoseconds.apply((FromDecimalArguments) DecimalUtils.extractSecondsAndNanos(bigDecimal, new BiFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer$$ExternalSyntheticLambda4
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.m527x3e50fb5a(deserializationContext, (Long) obj, (Integer) obj2);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$_fromDecimal$7$com-fasterxml-jackson-datatype-jsr310-deser-InstantDeserializer */
    /* synthetic */ FromDecimalArguments m527x3e50fb5a(DeserializationContext deserializationContext, Long l, Integer num) {
        return new FromDecimalArguments(l.longValue(), num.intValue(), getZone(deserializationContext));
    }

    private ZoneId getZone(DeserializationContext deserializationContext) {
        if (this._valueClass == Instant.class) {
            return null;
        }
        ZoneId zoneId = deserializationContext.getTimeZone().toZoneId();
        return this._normalizeZoneId ? zoneId.normalized() : zoneId;
    }

    private String replaceZeroOffsetAsZIfNecessary(String str) {
        return this.replaceZeroOffsetAsZ ? replaceZeroOffsetAsZ(str) : str;
    }

    private static String replaceZeroOffsetAsZ(String str) {
        int iLastIndexOf = str.lastIndexOf(43);
        if (iLastIndexOf < 0) {
            return str;
        }
        int i = iLastIndexOf + 1;
        int length = str.length() - i;
        return length != 2 ? length != 4 ? (length == 5 && str.regionMatches(i, "00:00", 0, length)) ? str.substring(0, iLastIndexOf) + 'Z' : str : str.regionMatches(i, "0000", 0, length) ? str.substring(0, iLastIndexOf) + 'Z' : str : str.regionMatches(i, "00", 0, length) ? str.substring(0, iLastIndexOf) + 'Z' : str;
    }

    private String addInColonToOffsetIfMissing(String str) {
        Matcher matcher = ISO8601_COLONLESS_OFFSET_REGEX.matcher(str);
        if (!matcher.find()) {
            return str;
        }
        StringBuilder sb = new StringBuilder(matcher.group(0));
        sb.insert(3, ":");
        return matcher.replaceFirst(sb.toString());
    }

    public static class FromIntegerArguments {
        public final long value;
        public final ZoneId zoneId;

        FromIntegerArguments(long j, ZoneId zoneId) {
            this.value = j;
            this.zoneId = zoneId;
        }
    }

    public static class FromDecimalArguments {
        public final int fraction;
        public final long integer;
        public final ZoneId zoneId;

        FromDecimalArguments(long j, int i, ZoneId zoneId) {
            this.integer = j;
            this.fraction = i;
            this.zoneId = zoneId;
        }
    }
}
