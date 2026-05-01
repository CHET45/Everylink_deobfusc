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
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;
import com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes3.dex */
public class DurationSerializer extends JSR310FormattedSerializerBase<Duration> {
    public static final DurationSerializer INSTANCE = new DurationSerializer();
    private static final long serialVersionUID = 1;
    private DurationUnitConverter _durationUnitConverter;

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected DateTimeFormatter _useDateTimeFormatter(SerializerProvider serializerProvider, JsonFormat.Value value) {
        return null;
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
    public /* bridge */ /* synthetic */ void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        super.acceptJsonFormatVisitor(jsonFormatVisitorWrapper, javaType);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    @Deprecated
    public /* bridge */ /* synthetic */ JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return super.getSchema(serializerProvider, type);
    }

    protected DurationSerializer() {
        super(Duration.class);
    }

    protected DurationSerializer(DurationSerializer durationSerializer, Boolean bool, DateTimeFormatter dateTimeFormatter) {
        super(durationSerializer, bool, dateTimeFormatter, null);
    }

    protected DurationSerializer(DurationSerializer durationSerializer, Boolean bool, Boolean bool2, DateTimeFormatter dateTimeFormatter) {
        super(durationSerializer, bool, bool2, dateTimeFormatter, null);
    }

    protected DurationSerializer(DurationSerializer durationSerializer, DurationUnitConverter durationUnitConverter) {
        super(durationSerializer, durationSerializer._useTimestamp, durationSerializer._useNanoseconds, durationSerializer._formatter, durationSerializer._shape);
        this._durationUnitConverter = durationUnitConverter;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    public DurationSerializer withFormat(Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        return new DurationSerializer(this, bool, dateTimeFormatter);
    }

    protected DurationSerializer withConverter(DurationUnitConverter durationUnitConverter) {
        return new DurationSerializer(this, durationUnitConverter);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected SerializationFeature getTimestampsFeature() {
        return SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS;
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase, com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        DurationSerializer durationSerializer = (DurationSerializer) super.createContextual(serializerProvider, beanProperty);
        JsonFormat.Value valueFindFormatOverrides = findFormatOverrides(serializerProvider, beanProperty, handledType());
        if (valueFindFormatOverrides == null || !valueFindFormatOverrides.hasPattern()) {
            return durationSerializer;
        }
        String pattern = valueFindFormatOverrides.getPattern();
        DurationUnitConverter durationUnitConverterFrom = DurationUnitConverter.from(pattern);
        if (durationUnitConverterFrom == null) {
            serializerProvider.reportBadDefinition(handledType(), String.format("Bad 'pattern' definition (\"%s\") for `Duration`: expected one of [%s]", pattern, DurationUnitConverter.descForAllowed()));
        }
        return durationSerializer.withConverter(durationUnitConverterFrom);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(Duration duration, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (useTimestamp(serializerProvider)) {
            DurationUnitConverter durationUnitConverter = this._durationUnitConverter;
            if (durationUnitConverter != null) {
                jsonGenerator.writeNumber(durationUnitConverter.convert(duration));
                return;
            } else if (useNanoseconds(serializerProvider)) {
                jsonGenerator.writeNumber(_toNanos(duration));
                return;
            } else {
                jsonGenerator.writeNumber(duration.toMillis());
                return;
            }
        }
        jsonGenerator.writeString(duration.toString());
    }

    private BigDecimal _toNanos(Duration duration) {
        if (duration.isNegative()) {
            Duration durationAbs = duration.abs();
            return DecimalUtils.toBigDecimal(durationAbs.getSeconds(), durationAbs.getNano()).negate();
        }
        return DecimalUtils.toBigDecimal(duration.getSeconds(), duration.getNano());
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        JsonIntegerFormatVisitor jsonIntegerFormatVisitorExpectIntegerFormat = jsonFormatVisitorWrapper.expectIntegerFormat(javaType);
        if (jsonIntegerFormatVisitorExpectIntegerFormat != null) {
            jsonIntegerFormatVisitorExpectIntegerFormat.numberType(JsonParser.NumberType.LONG);
            SerializerProvider provider = jsonFormatVisitorWrapper.getProvider();
            if (provider == null || !useNanoseconds(provider)) {
                jsonIntegerFormatVisitorExpectIntegerFormat.format(JsonValueFormat.UTC_MILLISEC);
            }
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

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean bool, Boolean bool2) {
        return new DurationSerializer(this, this._useTimestamp, bool2, this._formatter);
    }
}
