package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes3.dex */
public class LocalDateDeserializer extends JSR310DateTimeDeserializerBase<LocalDate> {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final LocalDateDeserializer INSTANCE = new LocalDateDeserializer();
    private static final long serialVersionUID = 1;

    protected LocalDateDeserializer() {
        this(DEFAULT_FORMATTER);
    }

    public LocalDateDeserializer(DateTimeFormatter dateTimeFormatter) {
        super(LocalDate.class, dateTimeFormatter);
    }

    public LocalDateDeserializer(LocalDateDeserializer localDateDeserializer, DateTimeFormatter dateTimeFormatter) {
        super(localDateDeserializer, dateTimeFormatter);
    }

    protected LocalDateDeserializer(LocalDateDeserializer localDateDeserializer, Boolean bool) {
        super(localDateDeserializer, bool);
    }

    protected LocalDateDeserializer(LocalDateDeserializer localDateDeserializer, JsonFormat.Shape shape) {
        super(localDateDeserializer, shape);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    public JSR310DateTimeDeserializerBase<LocalDate> withDateFormat(DateTimeFormatter dateTimeFormatter) {
        return new LocalDateDeserializer(this, dateTimeFormatter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase, com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase
    public LocalDateDeserializer withLeniency(Boolean bool) {
        return new LocalDateDeserializer(this, bool);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    public JSR310DateTimeDeserializerBase<LocalDate> withShape(JsonFormat.Shape shape) {
        return new LocalDateDeserializer(this, shape);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.VALUE_STRING)) {
            return _fromString(jsonParser, deserializationContext, jsonParser.getText());
        }
        if (jsonParser.isExpectedStartObjectToken()) {
            return _fromString(jsonParser, deserializationContext, deserializationContext.extractScalarFromObject(jsonParser, this, handledType()));
        }
        if (jsonParser.isExpectedStartArrayToken()) {
            JsonToken jsonTokenNextToken = jsonParser.nextToken();
            if (jsonTokenNextToken == JsonToken.END_ARRAY) {
                return null;
            }
            if (deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS) && (jsonTokenNextToken == JsonToken.VALUE_STRING || jsonTokenNextToken == JsonToken.VALUE_EMBEDDED_OBJECT)) {
                LocalDate localDateDeserialize = deserialize(jsonParser, deserializationContext);
                if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    handleMissingEndArrayForSingle(jsonParser, deserializationContext);
                }
                return localDateDeserialize;
            }
            if (jsonTokenNextToken == JsonToken.VALUE_NUMBER_INT) {
                int intValue = jsonParser.getIntValue();
                int iNextIntValue = jsonParser.nextIntValue(-1);
                int iNextIntValue2 = jsonParser.nextIntValue(-1);
                if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    throw deserializationContext.wrongTokenException(jsonParser, handledType(), JsonToken.END_ARRAY, "Expected array to end");
                }
                return LocalDate.of(intValue, iNextIntValue, iNextIntValue2);
            }
            deserializationContext.reportInputMismatch(handledType(), "Unexpected token (%s) within Array, expected VALUE_NUMBER_INT", jsonTokenNextToken);
        }
        if (jsonParser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
            return (LocalDate) jsonParser.getEmbeddedObject();
        }
        if (jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            _checkCoercionFail(deserializationContext, deserializationContext.findCoercionAction(logicalType(), this._valueClass, CoercionInputShape.Integer), handledType(), Long.valueOf(jsonParser.getLongValue()), "Integer value (" + jsonParser.getLongValue() + ")");
            if (this._shape == JsonFormat.Shape.NUMBER_INT || isLenient()) {
                return LocalDate.ofEpochDay(jsonParser.getLongValue());
            }
            return _failForNotLenient(jsonParser, deserializationContext, JsonToken.VALUE_STRING);
        }
        return (LocalDate) _handleUnexpectedToken(deserializationContext, jsonParser, "Expected array or string.", new Object[0]);
    }

    protected LocalDate _fromString(JsonParser jsonParser, DeserializationContext deserializationContext, String str) throws IOException {
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return _fromEmptyString(jsonParser, deserializationContext, strTrim);
        }
        try {
            DateTimeFormatter dateTimeFormatter = this._formatter;
            if (dateTimeFormatter == DEFAULT_FORMATTER && strTrim.length() > 10 && strTrim.charAt(10) == 'T') {
                if (isLenient()) {
                    if (strTrim.endsWith("Z")) {
                        return LocalDate.parse(strTrim.substring(0, strTrim.length() - 1), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    }
                    return LocalDate.parse(strTrim, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                }
                return (LocalDate) deserializationContext.handleWeirdStringValue(getValueType(deserializationContext).getRawClass(), strTrim, "Should not contain time component when 'strict' mode set for property or type (enable 'lenient' handling to allow)", new Object[0]);
            }
            return LocalDate.parse(strTrim, dateTimeFormatter);
        } catch (DateTimeException e) {
            return (LocalDate) _handleDateTimeException(deserializationContext, e, strTrim);
        }
    }
}
