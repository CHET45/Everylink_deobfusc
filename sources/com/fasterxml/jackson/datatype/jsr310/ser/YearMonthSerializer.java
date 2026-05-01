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
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes3.dex */
public class YearMonthSerializer extends JSR310FormattedSerializerBase<YearMonth> {
    public static final YearMonthSerializer INSTANCE = new YearMonthSerializer();
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

    protected YearMonthSerializer() {
        this(null);
    }

    public YearMonthSerializer(DateTimeFormatter dateTimeFormatter) {
        super(YearMonth.class, dateTimeFormatter);
    }

    private YearMonthSerializer(YearMonthSerializer yearMonthSerializer, Boolean bool, DateTimeFormatter dateTimeFormatter) {
        super(yearMonthSerializer, bool, dateTimeFormatter, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    public YearMonthSerializer withFormat(Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        return new YearMonthSerializer(this, bool, dateTimeFormatter);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(YearMonth yearMonth, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (useTimestamp(serializerProvider)) {
            jsonGenerator.writeStartArray();
            _serializeAsArrayContents(yearMonth, jsonGenerator, serializerProvider);
            jsonGenerator.writeEndArray();
            return;
        }
        jsonGenerator.writeString(this._formatter == null ? yearMonth.toString() : yearMonth.format(this._formatter));
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310SerializerBase, com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(YearMonth yearMonth, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        WritableTypeId writableTypeIdWriteTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(yearMonth, serializationShape(serializerProvider)));
        if (writableTypeIdWriteTypePrefix != null && writableTypeIdWriteTypePrefix.valueShape == JsonToken.START_ARRAY) {
            _serializeAsArrayContents(yearMonth, jsonGenerator, serializerProvider);
        } else {
            jsonGenerator.writeString(this._formatter == null ? yearMonth.toString() : yearMonth.format(this._formatter));
        }
        typeSerializer.writeTypeSuffix(jsonGenerator, writableTypeIdWriteTypePrefix);
    }

    protected void _serializeAsArrayContents(YearMonth yearMonth, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(yearMonth.getYear());
        jsonGenerator.writeNumber(yearMonth.getMonthValue());
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310FormattedSerializerBase
    protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        SerializerProvider provider = jsonFormatVisitorWrapper.getProvider();
        if (provider != null && useTimestamp(provider)) {
            super._acceptTimestampVisitor(jsonFormatVisitorWrapper, javaType);
            return;
        }
        JsonStringFormatVisitor jsonStringFormatVisitorExpectStringFormat = jsonFormatVisitorWrapper.expectStringFormat(javaType);
        if (jsonStringFormatVisitorExpectStringFormat != null) {
            jsonStringFormatVisitorExpectStringFormat.format(JsonValueFormat.DATE_TIME);
        }
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.ser.JSR310SerializerBase
    protected JsonToken serializationShape(SerializerProvider serializerProvider) {
        return useTimestamp(serializerProvider) ? JsonToken.START_ARRAY : JsonToken.VALUE_STRING;
    }
}
