package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes3.dex */
public class YearMonthDeserializer extends JSR310DateTimeDeserializerBase<YearMonth> {
    public static final YearMonthDeserializer INSTANCE = new YearMonthDeserializer();
    private static final long serialVersionUID = 1;

    public YearMonthDeserializer() {
        this(DateTimeFormatter.ofPattern("u-MM"));
    }

    public YearMonthDeserializer(DateTimeFormatter dateTimeFormatter) {
        super(YearMonth.class, dateTimeFormatter);
    }

    protected YearMonthDeserializer(YearMonthDeserializer yearMonthDeserializer, Boolean bool) {
        super(yearMonthDeserializer, bool);
    }

    public YearMonthDeserializer(YearMonthDeserializer yearMonthDeserializer, Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        super(yearMonthDeserializer, bool, dateTimeFormatter, shape);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    public JSR310DateTimeDeserializerBase<YearMonth> withDateFormat(DateTimeFormatter dateTimeFormatter) {
        return new YearMonthDeserializer(this, Boolean.valueOf(this._isLenient), dateTimeFormatter, this._shape);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase, com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase
    public YearMonthDeserializer withLeniency(Boolean bool) {
        return new YearMonthDeserializer(this, bool);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public YearMonth deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.VALUE_STRING)) {
            return _fromString(jsonParser, deserializationContext, jsonParser.getText());
        }
        if (jsonParser.isExpectedStartObjectToken()) {
            return _fromString(jsonParser, deserializationContext, deserializationContext.extractScalarFromObject(jsonParser, this, handledType()));
        }
        if (!jsonParser.isExpectedStartArrayToken()) {
            return jsonParser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT) ? (YearMonth) jsonParser.getEmbeddedObject() : (YearMonth) _handleUnexpectedToken(deserializationContext, jsonParser, JsonToken.VALUE_STRING, JsonToken.START_ARRAY);
        }
        JsonToken jsonTokenNextToken = jsonParser.nextToken();
        if (jsonTokenNextToken == JsonToken.END_ARRAY) {
            return null;
        }
        if ((jsonTokenNextToken == JsonToken.VALUE_STRING || jsonTokenNextToken == JsonToken.VALUE_EMBEDDED_OBJECT) && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            YearMonth yearMonthDeserialize = deserialize(jsonParser, deserializationContext);
            if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                handleMissingEndArrayForSingle(jsonParser, deserializationContext);
            }
            return yearMonthDeserialize;
        }
        if (jsonTokenNextToken != JsonToken.VALUE_NUMBER_INT) {
            _reportWrongToken(deserializationContext, JsonToken.VALUE_NUMBER_INT, "years");
        }
        int intValue = jsonParser.getIntValue();
        int iNextIntValue = jsonParser.nextIntValue(-1);
        if (iNextIntValue == -1) {
            if (!jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
                _reportWrongToken(deserializationContext, JsonToken.VALUE_NUMBER_INT, "months");
            }
            iNextIntValue = jsonParser.getIntValue();
        }
        if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            throw deserializationContext.wrongTokenException(jsonParser, handledType(), JsonToken.END_ARRAY, "Expected array to end");
        }
        return YearMonth.of(intValue, iNextIntValue);
    }

    protected YearMonth _fromString(JsonParser jsonParser, DeserializationContext deserializationContext, String str) throws IOException {
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return _fromEmptyString(jsonParser, deserializationContext, strTrim);
        }
        try {
            return YearMonth.parse(strTrim, this._formatter);
        } catch (DateTimeException e) {
            return (YearMonth) _handleDateTimeException(deserializationContext, e, strTrim);
        }
    }
}
