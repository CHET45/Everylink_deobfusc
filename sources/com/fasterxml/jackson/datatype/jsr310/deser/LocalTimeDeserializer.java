package com.fasterxml.jackson.datatype.jsr310.deser;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class LocalTimeDeserializer extends JSR310DateTimeDeserializerBase<LocalTime> {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;
    public static final LocalTimeDeserializer INSTANCE = new LocalTimeDeserializer();
    private static final long serialVersionUID = 1;
    protected final Boolean _readTimestampsAsNanosOverride;

    protected LocalTimeDeserializer() {
        this(DEFAULT_FORMATTER);
    }

    public LocalTimeDeserializer(DateTimeFormatter dateTimeFormatter) {
        super(LocalTime.class, dateTimeFormatter);
        this._readTimestampsAsNanosOverride = null;
    }

    protected LocalTimeDeserializer(LocalTimeDeserializer localTimeDeserializer, Boolean bool) {
        super(localTimeDeserializer, bool);
        this._readTimestampsAsNanosOverride = localTimeDeserializer._readTimestampsAsNanosOverride;
    }

    protected LocalTimeDeserializer(LocalTimeDeserializer localTimeDeserializer, Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape, Boolean bool2) {
        super(localTimeDeserializer, bool, dateTimeFormatter, shape);
        this._readTimestampsAsNanosOverride = bool2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    public JSR310DateTimeDeserializerBase<LocalTime> withDateFormat(DateTimeFormatter dateTimeFormatter) {
        return new LocalTimeDeserializer(this, Boolean.valueOf(this._isLenient), dateTimeFormatter, this._shape, this._readTimestampsAsNanosOverride);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase, com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase
    public LocalTimeDeserializer withLeniency(Boolean bool) {
        return new LocalTimeDeserializer(this, bool);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    protected JSR310DateTimeDeserializerBase<?> _withFormatOverrides(DeserializationContext deserializationContext, BeanProperty beanProperty, JsonFormat.Value value) {
        LocalTimeDeserializer localTimeDeserializer = (LocalTimeDeserializer) super._withFormatOverrides(deserializationContext, beanProperty, value);
        Boolean feature = value.getFeature(JsonFormat.Feature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
        return !Objects.equals(feature, localTimeDeserializer._readTimestampsAsNanosOverride) ? new LocalTimeDeserializer(localTimeDeserializer, Boolean.valueOf(localTimeDeserializer._isLenient), localTimeDeserializer._formatter, localTimeDeserializer._shape, feature) : localTimeDeserializer;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
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
                LocalTime localTimeDeserialize = deserialize(jsonParser, deserializationContext);
                if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    handleMissingEndArrayForSingle(jsonParser, deserializationContext);
                }
                return localTimeDeserialize;
            }
            if (jsonTokenNextToken == JsonToken.VALUE_NUMBER_INT) {
                int intValue = jsonParser.getIntValue();
                jsonParser.nextToken();
                int intValue2 = jsonParser.getIntValue();
                if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                    return LocalTime.of(intValue, intValue2);
                }
                int intValue3 = jsonParser.getIntValue();
                if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                    return LocalTime.of(intValue, intValue2, intValue3);
                }
                int intValue4 = jsonParser.getIntValue();
                if (intValue4 < 1000 && !shouldReadTimestampsAsNanoseconds(deserializationContext)) {
                    intValue4 *= 1000000;
                }
                if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    throw deserializationContext.wrongTokenException(jsonParser, handledType(), JsonToken.END_ARRAY, "Expected array to end");
                }
                return LocalTime.of(intValue, intValue2, intValue3, intValue4);
            }
            deserializationContext.reportInputMismatch(handledType(), "Unexpected token (%s) within Array, expected VALUE_NUMBER_INT", jsonTokenNextToken);
        }
        if (jsonParser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT)) {
            return (LocalTime) jsonParser.getEmbeddedObject();
        }
        if (jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
            _throwNoNumericTimestampNeedTimeZone(jsonParser, deserializationContext);
        }
        return (LocalTime) _handleUnexpectedToken(deserializationContext, jsonParser, "Expected array or string.", new Object[0]);
    }

    protected boolean shouldReadTimestampsAsNanoseconds(DeserializationContext deserializationContext) {
        Boolean bool = this._readTimestampsAsNanosOverride;
        return bool != null ? bool.booleanValue() : deserializationContext.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
    }

    protected LocalTime _fromString(JsonParser jsonParser, DeserializationContext deserializationContext, String str) throws IOException {
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return _fromEmptyString(jsonParser, deserializationContext, strTrim);
        }
        DateTimeFormatter dateTimeFormatter = this._formatter;
        try {
            if (dateTimeFormatter == DEFAULT_FORMATTER && strTrim.contains(ExifInterface.GPS_DIRECTION_TRUE)) {
                return LocalTime.parse(strTrim, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
            return LocalTime.parse(strTrim, dateTimeFormatter);
        } catch (DateTimeException e) {
            return (LocalTime) _handleDateTimeException(deserializationContext, e, strTrim);
        }
    }
}
