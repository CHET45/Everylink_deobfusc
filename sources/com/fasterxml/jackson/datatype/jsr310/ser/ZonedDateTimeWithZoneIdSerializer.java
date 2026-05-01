package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public class ZonedDateTimeWithZoneIdSerializer extends InstantSerializerBase<ZonedDateTime> {
    public static final ZonedDateTimeWithZoneIdSerializer INSTANCE = new ZonedDateTimeWithZoneIdSerializer();
    private static final long serialVersionUID = 1;

    protected ZonedDateTimeWithZoneIdSerializer() {
        super(ZonedDateTime.class, new ToLongFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeWithZoneIdSerializer$$ExternalSyntheticLambda0
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((ZonedDateTime) obj).toInstant().toEpochMilli();
            }
        }, new ZonedDateTimeSerializer$$ExternalSyntheticLambda1(), new ToIntFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeWithZoneIdSerializer$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((ZonedDateTime) obj).getNano();
            }
        }, (DateTimeFormatter) null);
    }

    protected ZonedDateTimeWithZoneIdSerializer(ZonedDateTimeWithZoneIdSerializer zonedDateTimeWithZoneIdSerializer, Boolean bool, DateTimeFormatter dateTimeFormatter) {
        this(zonedDateTimeWithZoneIdSerializer, bool, null, dateTimeFormatter);
    }

    protected ZonedDateTimeWithZoneIdSerializer(ZonedDateTimeWithZoneIdSerializer zonedDateTimeWithZoneIdSerializer, Boolean bool, Boolean bool2, DateTimeFormatter dateTimeFormatter) {
        super(zonedDateTimeWithZoneIdSerializer, bool, bool2, dateTimeFormatter);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializerBase, com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected JSR310FormattedSerializerBase<?> withFormat(Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        return new ZonedDateTimeWithZoneIdSerializer(this, bool, dateTimeFormatter);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean bool, Boolean bool2) {
        return new ZonedDateTimeWithZoneIdSerializer(this, this._useTimestamp, bool2, this._formatter);
    }
}
