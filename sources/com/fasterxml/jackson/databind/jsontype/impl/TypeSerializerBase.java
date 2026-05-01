package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class TypeSerializerBase extends TypeSerializer {
    protected final TypeIdResolver _idResolver;
    protected final BeanProperty _property;

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public String getPropertyName() {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public abstract JsonTypeInfo.EnumC1421As getTypeInclusion();

    protected void handleMissingId(Object obj) {
    }

    protected TypeSerializerBase(TypeIdResolver typeIdResolver, BeanProperty beanProperty) {
        this._idResolver = typeIdResolver;
        this._property = beanProperty;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public TypeIdResolver getTypeIdResolver() {
        return this._idResolver;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public WritableTypeId writeTypePrefix(JsonGenerator jsonGenerator, WritableTypeId writableTypeId) throws IOException {
        _generateTypeId(writableTypeId);
        if (writableTypeId.f479id == null) {
            return _writeTypePrefixForNull(jsonGenerator, writableTypeId);
        }
        return jsonGenerator.writeTypePrefix(writableTypeId);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public WritableTypeId writeTypeSuffix(JsonGenerator jsonGenerator, WritableTypeId writableTypeId) throws IOException {
        if (writableTypeId == null) {
            return _writeTypeSuffixfixForNull(jsonGenerator, writableTypeId);
        }
        return jsonGenerator.writeTypeSuffix(writableTypeId);
    }

    private WritableTypeId _writeTypePrefixForNull(JsonGenerator jsonGenerator, WritableTypeId writableTypeId) throws IOException {
        JsonToken jsonToken = writableTypeId.valueShape;
        writableTypeId.wrapperWritten = false;
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonGenerator.writeStartObject(writableTypeId.forValue);
        } else if (jsonToken == JsonToken.START_ARRAY) {
            jsonGenerator.writeStartArray(writableTypeId.forValue);
        }
        return writableTypeId;
    }

    private WritableTypeId _writeTypeSuffixfixForNull(JsonGenerator jsonGenerator, WritableTypeId writableTypeId) throws IOException {
        JsonToken jsonToken = writableTypeId.valueShape;
        if (jsonToken == JsonToken.START_OBJECT) {
            jsonGenerator.writeEndObject();
        } else if (jsonToken == JsonToken.START_ARRAY) {
            jsonGenerator.writeEndArray();
        }
        return writableTypeId;
    }

    protected void _generateTypeId(WritableTypeId writableTypeId) {
        String strIdFromValueAndType;
        if (writableTypeId.f479id == null) {
            Object obj = writableTypeId.forValue;
            Class<?> cls = writableTypeId.forValueType;
            if (cls == null) {
                strIdFromValueAndType = idFromValue(obj);
            } else {
                strIdFromValueAndType = idFromValueAndType(obj, cls);
            }
            writableTypeId.f479id = strIdFromValueAndType;
        }
    }

    protected String idFromValue(Object obj) {
        String strIdFromValue = this._idResolver.idFromValue(obj);
        if (strIdFromValue == null) {
            handleMissingId(obj);
        }
        return strIdFromValue;
    }

    protected String idFromValueAndType(Object obj, Class<?> cls) {
        String strIdFromValueAndType = this._idResolver.idFromValueAndType(obj, cls);
        if (strIdFromValueAndType == null) {
            handleMissingId(obj);
        }
        return strIdFromValueAndType;
    }
}
