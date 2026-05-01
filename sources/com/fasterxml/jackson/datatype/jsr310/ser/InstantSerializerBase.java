package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* JADX INFO: loaded from: classes3.dex */
public abstract class InstantSerializerBase<T extends Temporal> extends JSR310FormattedSerializerBase<T> {
    private final DateTimeFormatter defaultFormat;
    private final ToLongFunction<T> getEpochMillis;
    private final ToLongFunction<T> getEpochSeconds;
    private final ToIntFunction<T> getNanoseconds;

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected abstract JSR310FormattedSerializerBase<?> withFormat(Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape);

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
    public /* bridge */ /* synthetic */ void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        super.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase, com.fasterxml.jackson.databind.ser.ContextualSerializer
    public /* bridge */ /* synthetic */ JsonSerializer createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        return super.createContextual(serializerProvider, beanProperty);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    @Deprecated
    public /* bridge */ /* synthetic */ JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return super.getSchema(serializerProvider, type);
    }

    protected InstantSerializerBase(Class<T> cls, ToLongFunction<T> toLongFunction, ToLongFunction<T> toLongFunction2, ToIntFunction<T> toIntFunction, DateTimeFormatter dateTimeFormatter) {
        super(cls, null);
        this.defaultFormat = dateTimeFormatter;
        this.getEpochMillis = toLongFunction;
        this.getEpochSeconds = toLongFunction2;
        this.getNanoseconds = toIntFunction;
    }

    protected InstantSerializerBase(InstantSerializerBase<T> instantSerializerBase, Boolean bool, DateTimeFormatter dateTimeFormatter) {
        this(instantSerializerBase, bool, instantSerializerBase._useNanoseconds, dateTimeFormatter);
    }

    protected InstantSerializerBase(InstantSerializerBase<T> instantSerializerBase, Boolean bool, Boolean bool2, DateTimeFormatter dateTimeFormatter) {
        this(instantSerializerBase, bool, bool2, dateTimeFormatter, instantSerializerBase._shape);
    }

    protected InstantSerializerBase(InstantSerializerBase<T> instantSerializerBase, Boolean bool, Boolean bool2, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        super(instantSerializerBase, bool, bool2, dateTimeFormatter, shape);
        this.defaultFormat = instantSerializerBase.defaultFormat;
        this.getEpochMillis = instantSerializerBase.getEpochMillis;
        this.getEpochSeconds = instantSerializerBase.getEpochSeconds;
        this.getNanoseconds = instantSerializerBase.getNanoseconds;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (useTimestamp(serializerProvider)) {
            if (useNanoseconds(serializerProvider)) {
                jsonGenerator.writeNumber(DecimalUtils.toBigDecimal(this.getEpochSeconds.applyAsLong(t), this.getNanoseconds.applyAsInt(t)));
                return;
            } else {
                jsonGenerator.writeNumber(this.getEpochMillis.applyAsLong(t));
                return;
            }
        }
        jsonGenerator.writeString(formatValue(t, serializerProvider));
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        if (useNanoseconds(jsonFormatVisitorWrapper.getProvider())) {
            JsonNumberFormatVisitor jsonNumberFormatVisitorExpectNumberFormat = jsonFormatVisitorWrapper.expectNumberFormat(javaType);
            if (jsonNumberFormatVisitorExpectNumberFormat != null) {
                jsonNumberFormatVisitorExpectNumberFormat.numberType(JsonParser.NumberType.BIG_DECIMAL);
                return;
            }
            return;
        }
        JsonIntegerFormatVisitor jsonIntegerFormatVisitorExpectIntegerFormat = jsonFormatVisitorWrapper.expectIntegerFormat(javaType);
        if (jsonIntegerFormatVisitorExpectIntegerFormat != null) {
            jsonIntegerFormatVisitorExpectIntegerFormat.numberType(JsonParser.NumberType.LONG);
            jsonIntegerFormatVisitorExpectIntegerFormat.format(JsonValueFormat.UTC_MILLISEC);
        }
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310SerializerBase
    protected JsonToken serializationShape(SerializerProvider serializerProvider) {
        if (useTimestamp(serializerProvider)) {
            if (useNanoseconds(serializerProvider)) {
                return JsonToken.VALUE_NUMBER_FLOAT;
            }
            return JsonToken.VALUE_NUMBER_INT;
        }
        return JsonToken.VALUE_STRING;
    }

    protected String formatValue(T t, SerializerProvider serializerProvider) {
        DateTimeFormatter dateTimeFormatterWithZone = this._formatter == null ? this.defaultFormat : this._formatter;
        if (dateTimeFormatterWithZone != null) {
            if (dateTimeFormatterWithZone.getZone() == null && serializerProvider.getConfig().hasExplicitTimeZone() && serializerProvider.isEnabled(SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE)) {
                dateTimeFormatterWithZone = dateTimeFormatterWithZone.withZone(serializerProvider.getTimeZone().toZoneId());
            }
            return dateTimeFormatterWithZone.format(t);
        }
        return t.toString();
    }
}
