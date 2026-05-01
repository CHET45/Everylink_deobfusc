package com.fasterxml.jackson.databind.ser.std;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.EnumNamingStrategy;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.EnumNamingStrategyFactory;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.util.EnumValues;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
@JacksonStdImpl
public class EnumSerializer extends StdScalarSerializer<Enum<?>> implements ContextualSerializer {
    private static final long serialVersionUID = 1;
    protected final Boolean _serializeAsIndex;
    protected final EnumValues _values;
    protected final EnumValues _valuesByEnumNaming;
    protected final EnumValues _valuesByToString;

    @Deprecated
    public EnumSerializer(EnumValues enumValues, Boolean bool) {
        this(enumValues, bool, null, null);
    }

    @Deprecated
    public EnumSerializer(EnumValues enumValues, Boolean bool, EnumValues enumValues2) {
        this(enumValues, bool, enumValues2, null);
    }

    public EnumSerializer(EnumValues enumValues, Boolean bool, EnumValues enumValues2, EnumValues enumValues3) {
        super(enumValues.getEnumClass(), false);
        this._values = enumValues;
        this._serializeAsIndex = bool;
        this._valuesByEnumNaming = enumValues2;
        this._valuesByToString = enumValues3;
    }

    public static EnumSerializer construct(Class<?> cls, SerializationConfig serializationConfig, BeanDescription beanDescription, JsonFormat.Value value) {
        return new EnumSerializer(EnumValues.constructFromName(serializationConfig, beanDescription.getClassInfo()), _isShapeWrittenUsingIndex(cls, value, true, null), constructEnumNamingStrategyValues(serializationConfig, cls, beanDescription.getClassInfo()), EnumValues.constructFromToString(serializationConfig, beanDescription.getClassInfo()));
    }

    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        JsonFormat.Value valueFindFormatOverrides = findFormatOverrides(serializerProvider, beanProperty, handledType());
        if (valueFindFormatOverrides != null) {
            Boolean bool_isShapeWrittenUsingIndex = _isShapeWrittenUsingIndex(handledType(), valueFindFormatOverrides, false, this._serializeAsIndex);
            if (!Objects.equals(bool_isShapeWrittenUsingIndex, this._serializeAsIndex)) {
                return new EnumSerializer(this._values, bool_isShapeWrittenUsingIndex, this._valuesByEnumNaming, this._valuesByToString);
            }
        }
        return this;
    }

    public EnumValues getEnumValues() {
        return this._values;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public final void serialize(Enum<?> r2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        EnumValues enumValues = this._valuesByEnumNaming;
        if (enumValues != null) {
            jsonGenerator.writeString(enumValues.serializedValueFor(r2));
            return;
        }
        if (_serializeAsIndex(serializerProvider)) {
            jsonGenerator.writeNumber(r2.ordinal());
        } else if (serializerProvider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
            jsonGenerator.writeString(this._valuesByToString.serializedValueFor(r2));
        } else {
            jsonGenerator.writeString(this._values.serializedValueFor(r2));
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    @Deprecated
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        if (_serializeAsIndex(serializerProvider)) {
            return createSchemaNode(TypedValues.Custom.S_INT, true);
        }
        ObjectNode objectNodeCreateSchemaNode = createSchemaNode(TypedValues.Custom.S_STRING, true);
        if (type != null && serializerProvider.constructType(type).isEnumType()) {
            ArrayNode arrayNodePutArray = objectNodeCreateSchemaNode.putArray("enum");
            Iterator<SerializableString> it = this._values.values().iterator();
            while (it.hasNext()) {
                arrayNodePutArray.add(it.next().getValue());
            }
        }
        return objectNodeCreateSchemaNode;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        SerializerProvider provider = jsonFormatVisitorWrapper.getProvider();
        if (_serializeAsIndex(provider)) {
            visitIntFormat(jsonFormatVisitorWrapper, javaType, JsonParser.NumberType.INT);
            return;
        }
        JsonStringFormatVisitor jsonStringFormatVisitorExpectStringFormat = jsonFormatVisitorWrapper.expectStringFormat(javaType);
        if (jsonStringFormatVisitorExpectStringFormat != null) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            if (provider != null && provider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)) {
                Iterator<SerializableString> it = this._valuesByToString.values().iterator();
                while (it.hasNext()) {
                    linkedHashSet.add(it.next().getValue());
                }
            } else {
                Iterator<SerializableString> it2 = this._values.values().iterator();
                while (it2.hasNext()) {
                    linkedHashSet.add(it2.next().getValue());
                }
            }
            jsonStringFormatVisitorExpectStringFormat.enumTypes(linkedHashSet);
        }
    }

    protected final boolean _serializeAsIndex(SerializerProvider serializerProvider) {
        Boolean bool = this._serializeAsIndex;
        if (bool != null) {
            return bool.booleanValue();
        }
        return serializerProvider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX);
    }

    protected static Boolean _isShapeWrittenUsingIndex(Class<?> cls, JsonFormat.Value value, boolean z, Boolean bool) {
        JsonFormat.Shape shape = value == null ? null : value.getShape();
        if (shape == null || shape == JsonFormat.Shape.ANY || shape == JsonFormat.Shape.SCALAR) {
            return bool;
        }
        if (shape == JsonFormat.Shape.STRING || shape == JsonFormat.Shape.NATURAL) {
            return Boolean.FALSE;
        }
        if (shape.isNumeric() || shape == JsonFormat.Shape.ARRAY) {
            return Boolean.TRUE;
        }
        throw new IllegalArgumentException(String.format("Unsupported serialization shape (%s) for Enum %s, not supported as %s annotation", shape, cls.getName(), z ? "class" : "property"));
    }

    protected static EnumValues constructEnumNamingStrategyValues(SerializationConfig serializationConfig, Class<Enum<?>> cls, AnnotatedClass annotatedClass) {
        EnumNamingStrategy enumNamingStrategyCreateEnumNamingStrategyInstance = EnumNamingStrategyFactory.createEnumNamingStrategyInstance(serializationConfig.getAnnotationIntrospector().findEnumNamingStrategy(serializationConfig, annotatedClass), serializationConfig.canOverrideAccessModifiers());
        if (enumNamingStrategyCreateEnumNamingStrategyInstance == null) {
            return null;
        }
        return EnumValues.constructUsingEnumNamingStrategy(serializationConfig, annotatedClass, enumNamingStrategyCreateEnumNamingStrategyInstance);
    }
}
