package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class LocalDateTimeDeserializer extends JSR310DateTimeDeserializerBase<LocalDateTime> {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final LocalDateTimeDeserializer INSTANCE = new LocalDateTimeDeserializer();
    private static final long serialVersionUID = 1;
    protected final Boolean _readTimestampsAsNanosOverride;

    protected LocalDateTimeDeserializer() {
        this(DEFAULT_FORMATTER);
    }

    public LocalDateTimeDeserializer(DateTimeFormatter dateTimeFormatter) {
        super(LocalDateTime.class, dateTimeFormatter);
        this._readTimestampsAsNanosOverride = null;
    }

    protected LocalDateTimeDeserializer(LocalDateTimeDeserializer localDateTimeDeserializer, Boolean bool) {
        super(localDateTimeDeserializer, bool);
        this._readTimestampsAsNanosOverride = localDateTimeDeserializer._readTimestampsAsNanosOverride;
    }

    protected LocalDateTimeDeserializer(LocalDateTimeDeserializer localDateTimeDeserializer, Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape, Boolean bool2) {
        super(localDateTimeDeserializer, bool, dateTimeFormatter, shape);
        this._readTimestampsAsNanosOverride = bool2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    public JSR310DateTimeDeserializerBase<LocalDateTime> withDateFormat(DateTimeFormatter dateTimeFormatter) {
        return new LocalDateTimeDeserializer(this, Boolean.valueOf(this._isLenient), dateTimeFormatter, this._shape, this._readTimestampsAsNanosOverride);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase, com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase
    public LocalDateTimeDeserializer withLeniency(Boolean bool) {
        return new LocalDateTimeDeserializer(this, bool);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    protected JSR310DateTimeDeserializerBase<?> _withFormatOverrides(DeserializationContext deserializationContext, BeanProperty beanProperty, JsonFormat.Value value) {
        LocalDateTimeDeserializer localDateTimeDeserializer = (LocalDateTimeDeserializer) super._withFormatOverrides(deserializationContext, beanProperty, value);
        Boolean feature = value.getFeature(JsonFormat.Feature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
        return !Objects.equals(feature, localDateTimeDeserializer._readTimestampsAsNanosOverride) ? new LocalDateTimeDeserializer(localDateTimeDeserializer, Boolean.valueOf(localDateTimeDeserializer._isLenient), localDateTimeDeserializer._formatter, localDateTimeDeserializer._shape, feature) : localDateTimeDeserializer;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasTokenId(6)) {
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
            if ((jsonTokenNextToken == JsonToken.VALUE_STRING || jsonTokenNextToken == JsonToken.VALUE_EMBEDDED_OBJECT) && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                LocalDateTime localDateTimeDeserialize = deserialize(jsonParser, deserializationContext);
                if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    handleMissingEndArrayForSingle(jsonParser, deserializationContext);
                }
                return localDateTimeDeserialize;
            }
            if (jsonTokenNextToken == JsonToken.VALUE_NUMBER_INT) {
                int intValue = jsonParser.getIntValue();
                int iNextIntValue = jsonParser.nextIntValue(-1);
                int iNextIntValue2 = jsonParser.nextIntValue(-1);
                int iNextIntValue3 = jsonParser.nextIntValue(-1);
                int iNextIntValue4 = jsonParser.nextIntValue(-1);
                if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                    return LocalDateTime.of(intValue, iNextIntValue, iNextIntValue2, iNextIntValue3, iNextIntValue4);
                }
                int intValue2 = jsonParser.getIntValue();
                if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                    return LocalDateTime.of(intValue, iNextIntValue, iNextIntValue2, iNextIntValue3, iNextIntValue4, intValue2);
                }
                int intValue3 = jsonParser.getIntValue();
                if (intValue3 < 1000 && !shouldReadTimestampsAsNanoseconds(deserializationContext)) {
                    intValue3 *= 1000000;
                }
                int i = intValue3;
                if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    throw deserializationContext.wrongTokenException(jsonParser, handledType(), JsonToken.END_ARRAY, "Expected array to end");
                }
                return LocalDateTime.of(intValue, iNextIntValue, iNextIntValue2, iNextIntValue3, iNextIntValue4, intValue2, i);
            }
            deserializationContext.reportInputMismatch(handledType(), "Unexpected token (%s) within Array, expected VALUE_NUMBER_INT", jsonTokenNextToken);
        }
        if (jsonParser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
            return (LocalDateTime) jsonParser.getEmbeddedObject();
        }
        if (jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            _throwNoNumericTimestampNeedTimeZone(jsonParser, deserializationContext);
        }
        return (LocalDateTime) _handleUnexpectedToken(deserializationContext, jsonParser, "Expected array or string.", new Object[0]);
    }

    protected boolean shouldReadTimestampsAsNanoseconds(DeserializationContext deserializationContext) {
        Boolean bool = this._readTimestampsAsNanosOverride;
        return bool != null ? bool.booleanValue() : deserializationContext.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
    }

    protected LocalDateTime _fromString(JsonParser jsonParser, DeserializationContext deserializationContext, String str) throws IOException {
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return _fromEmptyString(jsonParser, deserializationContext, strTrim);
        }
        try {
            if (this._formatter == DEFAULT_FORMATTER && strTrim.length() > 10 && strTrim.charAt(10) == 'T' && strTrim.endsWith("Z")) {
                if (isLenient()) {
                    return LocalDateTime.parse(strTrim.substring(0, strTrim.length() - 1), this._formatter);
                }
                return (LocalDateTime) deserializationContext.handleWeirdStringValue(getValueType(deserializationContext).getRawClass(), strTrim, "Should not contain offset when 'strict' mode set for property or type (enable 'lenient' handling to allow)", new Object[0]);
            }
            return LocalDateTime.parse(strTrim, this._formatter);
        } catch (DateTimeException e) {
            return (LocalDateTime) _handleDateTimeException(deserializationContext, e, strTrim);
        }
    }
}
