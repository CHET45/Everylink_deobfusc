package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/* JADX INFO: loaded from: classes3.dex */
public class LocalTimeSerializer extends JSR310FormattedSerializerBase<LocalTime> {
    public static final LocalTimeSerializer INSTANCE = new LocalTimeSerializer();
    private static final long serialVersionUID = 1;

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase, com.fasterxml.jackson.databind.ser.ContextualSerializer
    public /* bridge */ /* synthetic */ JsonSerializer createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        return super.createContextual(serializerProvider, beanProperty);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    @Deprecated
    public /* bridge */ /* synthetic */ JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return super.getSchema(serializerProvider, type);
    }

    protected LocalTimeSerializer() {
        this(null);
    }

    public LocalTimeSerializer(DateTimeFormatter dateTimeFormatter) {
        super(LocalTime.class, dateTimeFormatter);
    }

    protected LocalTimeSerializer(LocalTimeSerializer localTimeSerializer, Boolean bool, DateTimeFormatter dateTimeFormatter) {
        this(localTimeSerializer, bool, null, dateTimeFormatter);
    }

    protected LocalTimeSerializer(LocalTimeSerializer localTimeSerializer, Boolean bool, Boolean bool2, DateTimeFormatter dateTimeFormatter) {
        super(localTimeSerializer, bool, bool2, dateTimeFormatter, null);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected JSR310FormattedSerializerBase<LocalTime> withFormat(Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        return new LocalTimeSerializer(this, bool, dateTimeFormatter);
    }

    protected DateTimeFormatter _defaultFormatter() {
        return DateTimeFormatter.ISO_LOCAL_TIME;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (useTimestamp(serializerProvider)) {
            jsonGenerator.writeStartArray();
            _serializeAsArrayContents(localTime, jsonGenerator, serializerProvider);
            jsonGenerator.writeEndArray();
        } else {
            DateTimeFormatter dateTimeFormatter_defaultFormatter = this._formatter;
            if (dateTimeFormatter_defaultFormatter == null) {
                dateTimeFormatter_defaultFormatter = _defaultFormatter();
            }
            jsonGenerator.writeString(localTime.format(dateTimeFormatter_defaultFormatter));
        }
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310SerializerBase, com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        WritableTypeId writableTypeIdWriteTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(localTime, serializationShape(serializerProvider)));
        if (writableTypeIdWriteTypePrefix != null && writableTypeIdWriteTypePrefix.valueShape == JsonToken.START_ARRAY) {
            _serializeAsArrayContents(localTime, jsonGenerator, serializerProvider);
        } else {
            DateTimeFormatter dateTimeFormatter_defaultFormatter = this._formatter;
            if (dateTimeFormatter_defaultFormatter == null) {
                dateTimeFormatter_defaultFormatter = _defaultFormatter();
            }
            jsonGenerator.writeString(localTime.format(dateTimeFormatter_defaultFormatter));
        }
        typeSerializer.writeTypeSuffix(jsonGenerator, writableTypeIdWriteTypePrefix);
    }

    private final void _serializeAsArrayContents(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(localTime.getHour());
        jsonGenerator.writeNumber(localTime.getMinute());
        int second = localTime.getSecond();
        int nano = localTime.getNano();
        if (second > 0 || nano > 0) {
            jsonGenerator.writeNumber(second);
            if (nano > 0) {
                if (useNanoseconds(serializerProvider)) {
                    jsonGenerator.writeNumber(nano);
                } else {
                    jsonGenerator.writeNumber(localTime.get(ChronoField.MILLI_OF_SECOND));
                }
            }
        }
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310SerializerBase
    protected JsonToken serializationShape(SerializerProvider serializerProvider) {
        return useTimestamp(serializerProvider) ? JsonToken.START_ARRAY : JsonToken.VALUE_STRING;
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean bool, Boolean bool2) {
        return new LocalTimeSerializer(this, this._useTimestamp, bool2, this._formatter);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        if (useTimestamp(jsonFormatVisitorWrapper.getProvider())) {
            _acceptTimestampVisitor(jsonFormatVisitorWrapper, javaType);
            return;
        }
        JsonStringFormatVisitor jsonStringFormatVisitorExpectStringFormat = jsonFormatVisitorWrapper.expectStringFormat(javaType);
        if (jsonStringFormatVisitorExpectStringFormat != null) {
            jsonStringFormatVisitorExpectStringFormat.format(JsonValueFormat.TIME);
        }
    }
}
