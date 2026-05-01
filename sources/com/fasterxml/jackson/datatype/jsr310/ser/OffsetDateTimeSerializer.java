package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: loaded from: classes3.dex */
public class OffsetDateTimeSerializer extends InstantSerializerBase<OffsetDateTime> {
    public static final OffsetDateTimeSerializer INSTANCE = new OffsetDateTimeSerializer();
    private static final long serialVersionUID = 1;

    protected OffsetDateTimeSerializer() {
        super(OffsetDateTime.class, new ToLongFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer$$ExternalSyntheticLambda0
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((OffsetDateTime) obj).toInstant().toEpochMilli();
            }
        }, new ToLongFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer$$ExternalSyntheticLambda1
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((OffsetDateTime) obj).toEpochSecond();
            }
        }, new ToIntFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((OffsetDateTime) obj).getNano();
            }
        }, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    @Deprecated
    protected OffsetDateTimeSerializer(OffsetDateTimeSerializer offsetDateTimeSerializer, Boolean bool, DateTimeFormatter dateTimeFormatter) {
        this(offsetDateTimeSerializer, bool, offsetDateTimeSerializer._useNanoseconds, dateTimeFormatter);
    }

    protected OffsetDateTimeSerializer(OffsetDateTimeSerializer offsetDateTimeSerializer, Boolean bool, Boolean bool2, DateTimeFormatter dateTimeFormatter) {
        super(offsetDateTimeSerializer, bool, bool2, dateTimeFormatter);
    }

    public OffsetDateTimeSerializer(OffsetDateTimeSerializer offsetDateTimeSerializer, Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        super(offsetDateTimeSerializer, bool, offsetDateTimeSerializer._useNanoseconds, dateTimeFormatter, shape);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializerBase, com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected JSR310FormattedSerializerBase<?> withFormat(Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        return new OffsetDateTimeSerializer(this, bool, dateTimeFormatter, shape);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean bool, Boolean bool2) {
        return new OffsetDateTimeSerializer(this, this._useTimestamp, bool2, this._formatter);
    }
}
