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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes3.dex */
public class LocalDateSerializer extends JSR310FormattedSerializerBase<LocalDate> {
    public static final LocalDateSerializer INSTANCE = new LocalDateSerializer();
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

    protected LocalDateSerializer() {
        super(LocalDate.class);
    }

    protected LocalDateSerializer(LocalDateSerializer localDateSerializer, Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        super(localDateSerializer, bool, dateTimeFormatter, shape);
    }

    public LocalDateSerializer(DateTimeFormatter dateTimeFormatter) {
        super(LocalDate.class, dateTimeFormatter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    public LocalDateSerializer withFormat(Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        return new LocalDateSerializer(this, bool, dateTimeFormatter, shape);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (useTimestamp(serializerProvider)) {
            if (this._shape == JsonFormat.Shape.NUMBER_INT) {
                jsonGenerator.writeNumber(localDate.toEpochDay());
                return;
            }
            jsonGenerator.writeStartArray();
            _serializeAsArrayContents(localDate, jsonGenerator, serializerProvider);
            jsonGenerator.writeEndArray();
            return;
        }
        jsonGenerator.writeString(this._formatter == null ? localDate.toString() : localDate.format(this._formatter));
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310SerializerBase, com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        WritableTypeId writableTypeIdWriteTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(localDate, serializationShape(serializerProvider)));
        JsonToken jsonToken = writableTypeIdWriteTypePrefix == null ? null : writableTypeIdWriteTypePrefix.valueShape;
        if (jsonToken == JsonToken.START_ARRAY) {
            _serializeAsArrayContents(localDate, jsonGenerator, serializerProvider);
        } else if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
            jsonGenerator.writeNumber(localDate.toEpochDay());
        } else {
            jsonGenerator.writeString(this._formatter == null ? localDate.toString() : localDate.format(this._formatter));
        }
        typeSerializer.writeTypeSuffix(jsonGenerator, writableTypeIdWriteTypePrefix);
    }

    protected void _serializeAsArrayContents(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(localDate.getYear());
        jsonGenerator.writeNumber(localDate.getMonthValue());
        jsonGenerator.writeNumber(localDate.getDayOfMonth());
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        SerializerProvider provider = jsonFormatVisitorWrapper.getProvider();
        if (provider != null && useTimestamp(provider)) {
            _acceptTimestampVisitor(jsonFormatVisitorWrapper, javaType);
            return;
        }
        JsonStringFormatVisitor jsonStringFormatVisitorExpectStringFormat = jsonFormatVisitorWrapper.expectStringFormat(javaType);
        if (jsonStringFormatVisitorExpectStringFormat != null) {
            jsonStringFormatVisitorExpectStringFormat.format(JsonValueFormat.DATE);
        }
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310SerializerBase
    protected JsonToken serializationShape(SerializerProvider serializerProvider) {
        if (useTimestamp(serializerProvider)) {
            if (this._shape == JsonFormat.Shape.NUMBER_INT) {
                return JsonToken.VALUE_NUMBER_INT;
            }
            return JsonToken.START_ARRAY;
        }
        return JsonToken.VALUE_STRING;
    }
}
