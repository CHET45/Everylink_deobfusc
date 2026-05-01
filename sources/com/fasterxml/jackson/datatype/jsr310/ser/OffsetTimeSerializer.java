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
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/* JADX INFO: loaded from: classes3.dex */
public class OffsetTimeSerializer extends JSR310FormattedSerializerBase<OffsetTime> {
    public static final OffsetTimeSerializer INSTANCE = new OffsetTimeSerializer();
    private static final long serialVersionUID = 1;

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

    protected OffsetTimeSerializer() {
        super(OffsetTime.class);
    }

    protected OffsetTimeSerializer(OffsetTimeSerializer offsetTimeSerializer, Boolean bool, DateTimeFormatter dateTimeFormatter) {
        this(offsetTimeSerializer, bool, null, dateTimeFormatter);
    }

    protected OffsetTimeSerializer(OffsetTimeSerializer offsetTimeSerializer, Boolean bool, Boolean bool2, DateTimeFormatter dateTimeFormatter) {
        super(offsetTimeSerializer, bool, bool2, dateTimeFormatter, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    public OffsetTimeSerializer withFormat(Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        return new OffsetTimeSerializer(this, bool, dateTimeFormatter);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(OffsetTime offsetTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (useTimestamp(serializerProvider)) {
            jsonGenerator.writeStartArray();
            _serializeAsArrayContents(offsetTime, jsonGenerator, serializerProvider);
            jsonGenerator.writeEndArray();
            return;
        }
        jsonGenerator.writeString(this._formatter == null ? offsetTime.toString() : offsetTime.format(this._formatter));
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310SerializerBase, com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(OffsetTime offsetTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        WritableTypeId writableTypeIdWriteTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(offsetTime, serializationShape(serializerProvider)));
        if (writableTypeIdWriteTypePrefix != null && writableTypeIdWriteTypePrefix.valueShape == JsonToken.START_ARRAY) {
            _serializeAsArrayContents(offsetTime, jsonGenerator, serializerProvider);
        } else {
            jsonGenerator.writeString(this._formatter == null ? offsetTime.toString() : offsetTime.format(this._formatter));
        }
        typeSerializer.writeTypeSuffix(jsonGenerator, writableTypeIdWriteTypePrefix);
    }

    private final void _serializeAsArrayContents(OffsetTime offsetTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(offsetTime.getHour());
        jsonGenerator.writeNumber(offsetTime.getMinute());
        int second = offsetTime.getSecond();
        int nano = offsetTime.getNano();
        if (second > 0 || nano > 0) {
            jsonGenerator.writeNumber(second);
            if (nano > 0) {
                if (useNanoseconds(serializerProvider)) {
                    jsonGenerator.writeNumber(nano);
                } else {
                    jsonGenerator.writeNumber(offsetTime.get(ChronoField.MILLI_OF_SECOND));
                }
            }
        }
        jsonGenerator.writeString(offsetTime.getOffset().toString());
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310SerializerBase
    protected JsonToken serializationShape(SerializerProvider serializerProvider) {
        return useTimestamp(serializerProvider) ? JsonToken.START_ARRAY : JsonToken.VALUE_STRING;
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean bool, Boolean bool2) {
        return new OffsetTimeSerializer(this, this._useTimestamp, bool2, this._formatter);
    }
}
