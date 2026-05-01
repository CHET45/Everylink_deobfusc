package com.fasterxml.jackson.datatype.jsr310.ser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
abstract class JSR310FormattedSerializerBase<T> extends JSR310SerializerBase<T> implements ContextualSerializer {
    private static final long serialVersionUID = 1;
    protected final DateTimeFormatter _formatter;
    protected volatile transient JavaType _integerListType;
    protected final JsonFormat.Shape _shape;
    protected final Boolean _useNanoseconds;
    protected final Boolean _useTimestamp;

    /* JADX WARN: Multi-variable type inference failed */
    @Deprecated
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean bool) {
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected JSR310FormattedSerializerBase<?> withFeatures(Boolean bool, Boolean bool2) {
        return this;
    }

    protected abstract JSR310FormattedSerializerBase<?> withFormat(Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape);

    protected JSR310FormattedSerializerBase(Class<T> cls) {
        this(cls, null);
    }

    protected JSR310FormattedSerializerBase(Class<T> cls, DateTimeFormatter dateTimeFormatter) {
        super(cls);
        this._useTimestamp = null;
        this._useNanoseconds = null;
        this._shape = null;
        this._formatter = dateTimeFormatter;
    }

    protected JSR310FormattedSerializerBase(JSR310FormattedSerializerBase<?> jSR310FormattedSerializerBase, Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        this(jSR310FormattedSerializerBase, bool, null, dateTimeFormatter, shape);
    }

    protected JSR310FormattedSerializerBase(JSR310FormattedSerializerBase<?> jSR310FormattedSerializerBase, Boolean bool, Boolean bool2, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        super(jSR310FormattedSerializerBase.handledType());
        this._useTimestamp = bool;
        this._useNanoseconds = bool2;
        this._formatter = dateTimeFormatter;
        this._shape = shape;
    }

    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        Boolean bool;
        JsonFormat.Value valueFindFormatOverrides = findFormatOverrides(serializerProvider, beanProperty, handledType());
        if (valueFindFormatOverrides == null) {
            return this;
        }
        JsonFormat.Shape shape = valueFindFormatOverrides.getShape();
        if (shape == JsonFormat.Shape.ARRAY || shape.isNumeric()) {
            bool = Boolean.TRUE;
        } else {
            bool = shape == JsonFormat.Shape.STRING ? Boolean.FALSE : null;
        }
        DateTimeFormatter dateTimeFormatter_useDateTimeFormatter = this._formatter;
        if (valueFindFormatOverrides.hasPattern()) {
            dateTimeFormatter_useDateTimeFormatter = _useDateTimeFormatter(serializerProvider, valueFindFormatOverrides);
        }
        JSR310FormattedSerializerBase jSR310FormattedSerializerBaseWithFormat = (shape == this._shape && bool == this._useTimestamp && dateTimeFormatter_useDateTimeFormatter == this._formatter) ? this : withFormat(bool, dateTimeFormatter_useDateTimeFormatter, shape);
        Boolean feature = valueFindFormatOverrides.getFeature(JsonFormat.Feature.WRITE_DATES_WITH_ZONE_ID);
        Boolean feature2 = valueFindFormatOverrides.getFeature(JsonFormat.Feature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
        return (feature == null && feature2 == null) ? jSR310FormattedSerializerBaseWithFormat : jSR310FormattedSerializerBaseWithFormat.withFeatures(feature, feature2);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    @Deprecated
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return createSchemaNode(serializerProvider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) ? "array" : TypedValues.Custom.S_STRING, true);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        if (useTimestamp(jsonFormatVisitorWrapper.getProvider())) {
            _acceptTimestampVisitor(jsonFormatVisitorWrapper, javaType);
            return;
        }
        JsonStringFormatVisitor jsonStringFormatVisitorExpectStringFormat = jsonFormatVisitorWrapper.expectStringFormat(javaType);
        if (jsonStringFormatVisitorExpectStringFormat != null) {
            jsonStringFormatVisitorExpectStringFormat.format(JsonValueFormat.DATE_TIME);
        }
    }

    protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        JsonArrayFormatVisitor jsonArrayFormatVisitorExpectArrayFormat = jsonFormatVisitorWrapper.expectArrayFormat(_integerListType(jsonFormatVisitorWrapper.getProvider()));
        if (jsonArrayFormatVisitorExpectArrayFormat != null) {
            jsonArrayFormatVisitorExpectArrayFormat.itemsFormat(JsonFormatTypes.INTEGER);
        }
    }

    protected JavaType _integerListType(SerializerProvider serializerProvider) {
        JavaType javaType = this._integerListType;
        if (javaType != null) {
            return javaType;
        }
        CollectionType collectionTypeConstructCollectionType = serializerProvider.getTypeFactory().constructCollectionType(List.class, Integer.class);
        this._integerListType = collectionTypeConstructCollectionType;
        return collectionTypeConstructCollectionType;
    }

    protected SerializationFeature getTimestampsFeature() {
        return SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
    }

    protected boolean useTimestamp(SerializerProvider serializerProvider) {
        Boolean bool = this._useTimestamp;
        if (bool != null) {
            return bool.booleanValue();
        }
        JsonFormat.Shape shape = this._shape;
        if (shape != null) {
            if (shape == JsonFormat.Shape.STRING) {
                return false;
            }
            if (this._shape == JsonFormat.Shape.NUMBER_INT) {
                return true;
            }
        }
        return this._formatter == null && serializerProvider != null && serializerProvider.isEnabled(getTimestampsFeature());
    }

    protected boolean _useTimestampExplicitOnly(SerializerProvider serializerProvider) {
        Boolean bool = this._useTimestamp;
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    protected boolean useNanoseconds(SerializerProvider serializerProvider) {
        Boolean bool = this._useNanoseconds;
        if (bool != null) {
            return bool.booleanValue();
        }
        JsonFormat.Shape shape = this._shape;
        if (shape != null) {
            if (shape == JsonFormat.Shape.NUMBER_INT) {
                return false;
            }
            if (this._shape == JsonFormat.Shape.NUMBER_FLOAT) {
                return true;
            }
        }
        return serializerProvider != null && serializerProvider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
    }

    protected DateTimeFormatter _useDateTimeFormatter(SerializerProvider serializerProvider, JsonFormat.Value value) {
        DateTimeFormatter dateTimeFormatterOfPattern;
        String pattern = value.getPattern();
        Locale locale = value.hasLocale() ? value.getLocale() : serializerProvider.getLocale();
        if (locale == null) {
            dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern(pattern);
        } else {
            dateTimeFormatterOfPattern = DateTimeFormatter.ofPattern(pattern, locale);
        }
        return value.hasTimeZone() ? dateTimeFormatterOfPattern.withZone(value.getTimeZone().toZoneId()) : dateTimeFormatterOfPattern;
    }
}
