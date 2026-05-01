package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class OffsetTimeDeserializer extends JSR310DateTimeDeserializerBase<OffsetTime> {
    public static final OffsetTimeDeserializer INSTANCE = new OffsetTimeDeserializer();
    private static final long serialVersionUID = 1;
    protected final Boolean _readTimestampsAsNanosOverride;

    protected OffsetTimeDeserializer() {
        this(DateTimeFormatter.ISO_OFFSET_TIME);
    }

    protected OffsetTimeDeserializer(DateTimeFormatter dateTimeFormatter) {
        super(OffsetTime.class, dateTimeFormatter);
        this._readTimestampsAsNanosOverride = null;
    }

    protected OffsetTimeDeserializer(OffsetTimeDeserializer offsetTimeDeserializer, Boolean bool) {
        super(offsetTimeDeserializer, bool);
        this._readTimestampsAsNanosOverride = offsetTimeDeserializer._readTimestampsAsNanosOverride;
    }

    protected OffsetTimeDeserializer(OffsetTimeDeserializer offsetTimeDeserializer, Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape, Boolean bool2) {
        super(offsetTimeDeserializer, bool, dateTimeFormatter, shape);
        this._readTimestampsAsNanosOverride = bool2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    public JSR310DateTimeDeserializerBase<OffsetTime> withDateFormat(DateTimeFormatter dateTimeFormatter) {
        return new OffsetTimeDeserializer(this, Boolean.valueOf(this._isLenient), dateTimeFormatter, this._shape, this._readTimestampsAsNanosOverride);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase, com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase
    public OffsetTimeDeserializer withLeniency(Boolean bool) {
        return new OffsetTimeDeserializer(this, bool);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    protected JSR310DateTimeDeserializerBase<?> _withFormatOverrides(DeserializationContext deserializationContext, BeanProperty beanProperty, JsonFormat.Value value) {
        OffsetTimeDeserializer offsetTimeDeserializer = (OffsetTimeDeserializer) super._withFormatOverrides(deserializationContext, beanProperty, value);
        Boolean feature = value.getFeature(JsonFormat.Feature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
        return !Objects.equals(feature, offsetTimeDeserializer._readTimestampsAsNanosOverride) ? new OffsetTimeDeserializer(offsetTimeDeserializer, Boolean.valueOf(offsetTimeDeserializer._isLenient), offsetTimeDeserializer._formatter, offsetTimeDeserializer._shape, feature) : offsetTimeDeserializer;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public OffsetTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        int i;
        if (jsonParser.hasToken(JsonToken.VALUE_STRING)) {
            return _fromString(jsonParser, deserializationContext, jsonParser.getText());
        }
        if (jsonParser.isExpectedStartObjectToken()) {
            return _fromString(jsonParser, deserializationContext, deserializationContext.extractScalarFromObject(jsonParser, this, handledType()));
        }
        if (!jsonParser.isExpectedStartArrayToken()) {
            if (jsonParser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
                return (OffsetTime) jsonParser.getEmbeddedObject();
            }
            if (jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
                _throwNoNumericTimestampNeedTimeZone(jsonParser, deserializationContext);
            }
            throw deserializationContext.wrongTokenException(jsonParser, handledType(), JsonToken.START_ARRAY, "Expected array or string.");
        }
        JsonToken jsonTokenNextToken = jsonParser.nextToken();
        if (jsonTokenNextToken != JsonToken.VALUE_NUMBER_INT) {
            if (jsonTokenNextToken == JsonToken.END_ARRAY) {
                return null;
            }
            if ((jsonTokenNextToken == JsonToken.VALUE_STRING || jsonTokenNextToken == JsonToken.VALUE_EMBEDDED_OBJECT) && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
                OffsetTime offsetTimeDeserialize = deserialize(jsonParser, deserializationContext);
                if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    handleMissingEndArrayForSingle(jsonParser, deserializationContext);
                }
                return offsetTimeDeserialize;
            }
            deserializationContext.reportInputMismatch(handledType(), "Unexpected token (%s) within Array, expected VALUE_NUMBER_INT", jsonTokenNextToken);
        }
        int intValue = jsonParser.getIntValue();
        int iNextIntValue = jsonParser.nextIntValue(-1);
        if (iNextIntValue == -1) {
            JsonToken currentToken = jsonParser.getCurrentToken();
            if (currentToken == JsonToken.END_ARRAY) {
                return null;
            }
            if (currentToken != JsonToken.VALUE_NUMBER_INT) {
                _reportWrongToken(deserializationContext, JsonToken.VALUE_NUMBER_INT, "minutes");
            }
            iNextIntValue = jsonParser.getIntValue();
        }
        int i2 = 0;
        if (jsonParser.nextToken() == JsonToken.VALUE_NUMBER_INT) {
            int intValue2 = jsonParser.getIntValue();
            if (jsonParser.nextToken() == JsonToken.VALUE_NUMBER_INT) {
                int intValue3 = jsonParser.getIntValue();
                if (intValue3 < 1000 && !shouldReadTimestampsAsNanoseconds(deserializationContext)) {
                    intValue3 *= 1000000;
                }
                i2 = intValue3;
                jsonParser.nextToken();
            }
            int i3 = i2;
            i2 = intValue2;
            i = i3;
        } else {
            i = 0;
        }
        if (jsonParser.getCurrentToken() == JsonToken.VALUE_STRING) {
            OffsetTime offsetTimeOf = OffsetTime.of(intValue, iNextIntValue, i2, i, ZoneOffset.of(jsonParser.getText()));
            if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                _reportWrongToken(deserializationContext, JsonToken.END_ARRAY, "timezone");
            }
            return offsetTimeOf;
        }
        throw deserializationContext.wrongTokenException(jsonParser, handledType(), JsonToken.VALUE_STRING, "Expected string for TimeZone after numeric values");
    }

    protected boolean shouldReadTimestampsAsNanoseconds(DeserializationContext deserializationContext) {
        Boolean bool = this._readTimestampsAsNanosOverride;
        return bool != null ? bool.booleanValue() : deserializationContext.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
    }

    protected OffsetTime _fromString(JsonParser jsonParser, DeserializationContext deserializationContext, String str) throws IOException {
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return _fromEmptyString(jsonParser, deserializationContext, strTrim);
        }
        try {
            return OffsetTime.parse(strTrim, this._formatter);
        } catch (DateTimeException e) {
            return (OffsetTime) _handleDateTimeException(deserializationContext, e, strTrim);
        }
    }
}
