package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: loaded from: classes3.dex */
public class ZonedDateTimeSerializer extends InstantSerializerBase<ZonedDateTime> {
    public static final ZonedDateTimeSerializer INSTANCE = new ZonedDateTimeSerializer();
    private static final long serialVersionUID = 1;
    protected final Boolean _writeZoneId;

    protected ZonedDateTimeSerializer() {
        this(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public ZonedDateTimeSerializer(DateTimeFormatter dateTimeFormatter) {
        super(ZonedDateTime.class, new ToLongFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer$$ExternalSyntheticLambda0
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((ZonedDateTime) obj).toInstant().toEpochMilli();
            }
        }, new ZonedDateTimeSerializer$$ExternalSyntheticLambda1(), new ToIntFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((ZonedDateTime) obj).getNano();
            }
        }, dateTimeFormatter);
        this._writeZoneId = null;
    }

    protected ZonedDateTimeSerializer(ZonedDateTimeSerializer zonedDateTimeSerializer, Boolean bool, DateTimeFormatter dateTimeFormatter, Boolean bool2) {
        this(zonedDateTimeSerializer, bool, zonedDateTimeSerializer._useNanoseconds, dateTimeFormatter, zonedDateTimeSerializer._shape, bool2);
    }

    @Deprecated
    protected ZonedDateTimeSerializer(ZonedDateTimeSerializer zonedDateTimeSerializer, Boolean bool, Boolean bool2, DateTimeFormatter dateTimeFormatter, Boolean bool3) {
        this(zonedDateTimeSerializer, bool, bool2, dateTimeFormatter, zonedDateTimeSerializer._shape, bool3);
    }

    protected ZonedDateTimeSerializer(ZonedDateTimeSerializer zonedDateTimeSerializer, Boolean bool, Boolean bool2, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape, Boolean bool3) {
        super(zonedDateTimeSerializer, bool, bool2, dateTimeFormatter, shape);
        this._writeZoneId = bool3;
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializerBase, com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected JSR310FormattedSerializerBase<?> withFormat(Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        return new ZonedDateTimeSerializer(this, bool, this._useNanoseconds, dateTimeFormatter, shape, this._writeZoneId);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    @Deprecated
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean bool) {
        return new ZonedDateTimeSerializer(this, this._useTimestamp, this._formatter, bool);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean bool, Boolean bool2) {
        return new ZonedDateTimeSerializer(this, this._useTimestamp, bool2, this._formatter, bool);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializerBase
    public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (!useTimestamp(serializerProvider) && ((this._formatter == null || this._shape != JsonFormat.Shape.STRING) && shouldWriteWithZoneId(serializerProvider))) {
            jsonGenerator.writeString(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedDateTime));
        } else {
            super.serialize(zonedDateTime, jsonGenerator, serializerProvider);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializerBase
    public String formatValue(ZonedDateTime zonedDateTime, SerializerProvider serializerProvider) {
        String value = super.formatValue(zonedDateTime, serializerProvider);
        return (this._formatter != null && this._shape == JsonFormat.Shape.STRING && Boolean.TRUE.equals(this._writeZoneId)) ? value + "[" + zonedDateTime.getZone().getId() + "]" : value;
    }

    public boolean shouldWriteWithZoneId(SerializerProvider serializerProvider) {
        Boolean bool = this._writeZoneId;
        return bool != null ? bool.booleanValue() : serializerProvider.isEnabled(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializerBase, com.fasterxml.jackson.datatype.jsr310.ser.JSR310SerializerBase
    protected JsonToken serializationShape(SerializerProvider serializerProvider) {
        if (!useTimestamp(serializerProvider) && shouldWriteWithZoneId(serializerProvider)) {
            return JsonToken.VALUE_STRING;
        }
        return super.serializationShape(serializerProvider);
    }
}
