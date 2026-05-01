package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes3.dex */
public class MonthDayDeserializer extends JSR310DateTimeDeserializerBase<MonthDay> {
    public static final MonthDayDeserializer INSTANCE = new MonthDayDeserializer();
    private static final long serialVersionUID = 1;

    public MonthDayDeserializer() {
        this(null);
    }

    public MonthDayDeserializer(DateTimeFormatter dateTimeFormatter) {
        super(MonthDay.class, dateTimeFormatter);
    }

    protected MonthDayDeserializer(MonthDayDeserializer monthDayDeserializer, Boolean bool) {
        super(monthDayDeserializer, bool);
    }

    protected MonthDayDeserializer(MonthDayDeserializer monthDayDeserializer, Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        super(monthDayDeserializer, bool, dateTimeFormatter, shape);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase, com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase
    public MonthDayDeserializer withLeniency(Boolean bool) {
        return new MonthDayDeserializer(this, bool);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    public JSR310DateTimeDeserializerBase<MonthDay> withDateFormat(DateTimeFormatter dateTimeFormatter) {
        return new MonthDayDeserializer(this, Boolean.valueOf(this._isLenient), dateTimeFormatter, this._shape);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public MonthDay deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.hasToken(JsonToken.VALUE_STRING)) {
            return _fromString(jsonParser, deserializationContext, jsonParser.getText());
        }
        if (jsonParser.isExpectedStartObjectToken()) {
            return _fromString(jsonParser, deserializationContext, deserializationContext.extractScalarFromObject(jsonParser, this, handledType()));
        }
        if (!jsonParser.isExpectedStartArrayToken()) {
            return jsonParser.hasToken(JsonToken.VALUE_EMBEDDED_OBJECT) ? (MonthDay) jsonParser.getEmbeddedObject() : (MonthDay) _handleUnexpectedToken(deserializationContext, jsonParser, JsonToken.VALUE_STRING, JsonToken.START_ARRAY);
        }
        JsonToken jsonTokenNextToken = jsonParser.nextToken();
        if (jsonTokenNextToken == JsonToken.END_ARRAY) {
            return null;
        }
        if ((jsonTokenNextToken == JsonToken.VALUE_STRING || jsonTokenNextToken == JsonToken.VALUE_EMBEDDED_OBJECT) && deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)) {
            MonthDay monthDayDeserialize = deserialize(jsonParser, deserializationContext);
            if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                handleMissingEndArrayForSingle(jsonParser, deserializationContext);
            }
            return monthDayDeserialize;
        }
        if (jsonTokenNextToken != JsonToken.VALUE_NUMBER_INT) {
            _reportWrongToken(deserializationContext, JsonToken.VALUE_NUMBER_INT, "month");
        }
        int intValue = jsonParser.getIntValue();
        int iNextIntValue = jsonParser.nextIntValue(-1);
        if (iNextIntValue == -1) {
            if (!jsonParser.hasToken(JsonToken.VALUE_NUMBER_INT)) {
                _reportWrongToken(deserializationContext, JsonToken.VALUE_NUMBER_INT, "day");
            }
            iNextIntValue = jsonParser.getIntValue();
        }
        if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            throw deserializationContext.wrongTokenException(jsonParser, handledType(), JsonToken.END_ARRAY, "Expected array to end");
        }
        return MonthDay.of(intValue, iNextIntValue);
    }

    protected MonthDay _fromString(JsonParser jsonParser, DeserializationContext deserializationContext, String str) throws IOException {
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return _fromEmptyString(jsonParser, deserializationContext, strTrim);
        }
        try {
            if (this._formatter == null) {
                return MonthDay.parse(strTrim);
            }
            return MonthDay.parse(strTrim, this._formatter);
        } catch (DateTimeException e) {
            return (MonthDay) _handleDateTimeException(deserializationContext, e, strTrim);
        }
    }
}
