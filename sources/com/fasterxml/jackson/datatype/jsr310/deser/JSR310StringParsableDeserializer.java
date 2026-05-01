package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;

/* JADX INFO: loaded from: classes3.dex */
public class JSR310StringParsableDeserializer extends JSR310DeserializerBase<Object> implements ContextualDeserializer {
    protected static final int TYPE_PERIOD = 1;
    protected static final int TYPE_ZONE_ID = 2;
    protected static final int TYPE_ZONE_OFFSET = 3;
    private static final long serialVersionUID = 1;
    protected final int _typeSelector;
    public static final JsonDeserializer<Period> PERIOD = createDeserializer(Period.class, 1);
    public static final JsonDeserializer<ZoneId> ZONE_ID = createDeserializer(ZoneId.class, 2);
    public static final JsonDeserializer<ZoneOffset> ZONE_OFFSET = createDeserializer(ZoneOffset.class, 3);

    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public /* bridge */ /* synthetic */ LogicalType logicalType() {
        return super.logicalType();
    }

    protected JSR310StringParsableDeserializer(Class<?> cls, int i) {
        super(cls);
        this._typeSelector = i;
    }

    protected JSR310StringParsableDeserializer(JSR310StringParsableDeserializer jSR310StringParsableDeserializer, Boolean bool) {
        super(jSR310StringParsableDeserializer, bool);
        this._typeSelector = jSR310StringParsableDeserializer._typeSelector;
    }

    protected static <T> JsonDeserializer<T> createDeserializer(Class<T> cls, int i) {
        return new JSR310StringParsableDeserializer((Class<?>) cls, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase
    public JSR310DeserializerBase<Object> withLeniency(Boolean bool) {
        return this._isLenient == (Boolean.FALSE.equals(bool) ^ true) ? this : new JSR310StringParsableDeserializer(this, bool);
    }

    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        Boolean lenient;
        JsonFormat.Value valueFindFormatOverrides = findFormatOverrides(deserializationContext, beanProperty, handledType());
        return (valueFindFormatOverrides == null || !valueFindFormatOverrides.hasLenient() || (lenient = valueFindFormatOverrides.getLenient()) == null) ? this : withLeniency(lenient);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.VALUE_STRING)) {
            return _fromString(jsonParser, deserializationContext, jsonParser.getText());
        }
        if (jsonParser.isExpectedStartObjectToken()) {
            return _fromString(jsonParser, deserializationContext, deserializationContext.extractScalarFromObject(jsonParser, this, handledType()));
        }
        if (jsonParser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
            return jsonParser.getEmbeddedObject();
        }
        if (jsonParser.isExpectedStartArrayToken()) {
            return _deserializeFromArray(jsonParser, deserializationContext);
        }
        throw deserializationContext.wrongTokenException(jsonParser, handledType(), JsonToken.VALUE_STRING, (String) null);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken != null && currentToken.isScalarValue()) {
            return deserialize(jsonParser, deserializationContext);
        }
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

    protected Object _fromString(JsonParser jsonParser, DeserializationContext deserializationContext, String str) throws IOException {
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            CoercionAction coercionActionFindCoercionAction = deserializationContext.findCoercionAction(logicalType(), this._valueClass, CoercionInputShape.EmptyString);
            if (coercionActionFindCoercionAction == CoercionAction.Fail) {
                deserializationContext.reportInputMismatch(this, "Cannot coerce empty String (\"\") to %s (but could if enabling coercion using `CoercionConfig`)", _coercedTypeDesc());
            }
            if (!isLenient()) {
                return _failForNotLenient(jsonParser, deserializationContext, JsonToken.VALUE_STRING);
            }
            if (coercionActionFindCoercionAction == CoercionAction.AsEmpty) {
                return getEmptyValue(deserializationContext);
            }
            return null;
        }
        try {
            int i = this._typeSelector;
            if (i == 1) {
                return Period.parse(strTrim);
            }
            if (i == 2) {
                return ZoneId.of(strTrim);
            }
            if (i == 3) {
                return ZoneOffset.of(strTrim);
            }
            VersionUtil.throwInternal();
            return null;
        } catch (DateTimeException e) {
            return _handleDateTimeException(deserializationContext, e, strTrim);
        }
    }
}
