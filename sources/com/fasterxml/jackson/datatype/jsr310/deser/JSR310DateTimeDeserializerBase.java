package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JSR310DateTimeDeserializerBase<T> extends JSR310DeserializerBase<T> implements ContextualDeserializer {
    protected final DateTimeFormatter _formatter;
    protected final JsonFormat.Shape _shape;

    protected abstract JSR310DateTimeDeserializerBase<T> withDateFormat(DateTimeFormatter dateTimeFormatter);

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase
    public abstract JSR310DateTimeDeserializerBase<T> withLeniency(Boolean bool);

    protected JSR310DateTimeDeserializerBase<T> withShape(JsonFormat.Shape shape) {
        return this;
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public /* bridge */ /* synthetic */ Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return super.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public /* bridge */ /* synthetic */ LogicalType logicalType() {
        return super.logicalType();
    }

    protected JSR310DateTimeDeserializerBase(Class<T> cls, DateTimeFormatter dateTimeFormatter) {
        super(cls);
        this._formatter = dateTimeFormatter;
        this._shape = null;
    }

    public JSR310DateTimeDeserializerBase(Class<T> cls, DateTimeFormatter dateTimeFormatter, Boolean bool) {
        super(cls, bool);
        this._formatter = dateTimeFormatter;
        this._shape = null;
    }

    protected JSR310DateTimeDeserializerBase(JSR310DateTimeDeserializerBase<T> jSR310DateTimeDeserializerBase, DateTimeFormatter dateTimeFormatter) {
        super(jSR310DateTimeDeserializerBase);
        this._formatter = dateTimeFormatter;
        this._shape = jSR310DateTimeDeserializerBase._shape;
    }

    protected JSR310DateTimeDeserializerBase(JSR310DateTimeDeserializerBase<T> jSR310DateTimeDeserializerBase, Boolean bool) {
        super(jSR310DateTimeDeserializerBase, bool);
        this._formatter = jSR310DateTimeDeserializerBase._formatter;
        this._shape = jSR310DateTimeDeserializerBase._shape;
    }

    protected JSR310DateTimeDeserializerBase(JSR310DateTimeDeserializerBase<T> jSR310DateTimeDeserializerBase, JsonFormat.Shape shape) {
        super(jSR310DateTimeDeserializerBase);
        this._formatter = jSR310DateTimeDeserializerBase._formatter;
        this._shape = shape;
    }

    protected JSR310DateTimeDeserializerBase(JSR310DateTimeDeserializerBase<T> jSR310DateTimeDeserializerBase, Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        super(jSR310DateTimeDeserializerBase, bool);
        this._formatter = dateTimeFormatter;
        this._shape = shape;
    }

    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        JsonFormat.Value valueFindFormatOverrides = findFormatOverrides(deserializationContext, beanProperty, handledType());
        return valueFindFormatOverrides == null ? this : _withFormatOverrides(deserializationContext, beanProperty, valueFindFormatOverrides);
    }

    protected JSR310DateTimeDeserializerBase<?> _withFormatOverrides(DeserializationContext deserializationContext, BeanProperty beanProperty, JsonFormat.Value value) {
        DateTimeFormatter formatter;
        Boolean lenient;
        JSR310DateTimeDeserializerBase<T> jSR310DateTimeDeserializerBaseWithLeniency = (!value.hasLenient() || (lenient = value.getLenient()) == null) ? this : withLeniency(lenient);
        if (value.hasPattern()) {
            String pattern = value.getPattern();
            Locale locale = value.hasLocale() ? value.getLocale() : deserializationContext.getLocale();
            DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder();
            if (acceptCaseInsensitiveValues(deserializationContext, value)) {
                dateTimeFormatterBuilder.parseCaseInsensitive();
            }
            dateTimeFormatterBuilder.appendPattern(pattern);
            if (locale == null) {
                formatter = dateTimeFormatterBuilder.toFormatter();
            } else {
                formatter = dateTimeFormatterBuilder.toFormatter(locale);
            }
            if (!jSR310DateTimeDeserializerBaseWithLeniency.isLenient()) {
                formatter = formatter.withResolverStyle(ResolverStyle.STRICT);
            }
            if (value.hasTimeZone()) {
                formatter = formatter.withZone(value.getTimeZone().toZoneId());
            }
            jSR310DateTimeDeserializerBaseWithLeniency = jSR310DateTimeDeserializerBaseWithLeniency.withDateFormat(formatter);
        }
        JsonFormat.Shape shape = value.getShape();
        return (shape == null || shape == this._shape) ? jSR310DateTimeDeserializerBaseWithLeniency : jSR310DateTimeDeserializerBaseWithLeniency.withShape(shape);
    }

    private boolean acceptCaseInsensitiveValues(DeserializationContext deserializationContext, JsonFormat.Value value) {
        Boolean feature = value.getFeature(JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_VALUES);
        if (feature == null) {
            feature = Boolean.valueOf(deserializationContext.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES));
        }
        return feature.booleanValue();
    }

    protected void _throwNoNumericTimestampNeedTimeZone(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        deserializationContext.reportInputMismatch(handledType(), "raw timestamp (%d) not allowed for `%s`: need additional information such as an offset or time-zone (see class Javadocs)", jsonParser.getNumberValue(), handledType().getName());
    }
}
