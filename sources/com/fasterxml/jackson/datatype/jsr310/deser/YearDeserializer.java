package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadCapability;
import com.fasterxml.jackson.core.p008io.NumberInput;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Year;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes3.dex */
public class YearDeserializer extends JSR310DateTimeDeserializerBase<Year> {
    public static final YearDeserializer INSTANCE = new YearDeserializer();
    private static final long serialVersionUID = 1;

    public YearDeserializer() {
        this(null);
    }

    public YearDeserializer(DateTimeFormatter dateTimeFormatter) {
        super(Year.class, dateTimeFormatter);
    }

    protected YearDeserializer(YearDeserializer yearDeserializer, Boolean bool) {
        super(yearDeserializer, bool);
    }

    public YearDeserializer(YearDeserializer yearDeserializer, Boolean bool, DateTimeFormatter dateTimeFormatter, JsonFormat.Shape shape) {
        super(yearDeserializer, bool, dateTimeFormatter, shape);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase
    public JSR310DateTimeDeserializerBase<Year> withDateFormat(DateTimeFormatter dateTimeFormatter) {
        return new YearDeserializer(this, Boolean.valueOf(this._isLenient), dateTimeFormatter, this._shape);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase, com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase
    public YearDeserializer withLeniency(Boolean bool) {
        return new YearDeserializer(this, bool);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Year deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken jsonTokenCurrentToken = jsonParser.currentToken();
        if (jsonTokenCurrentToken == JsonToken.VALUE_STRING) {
            return _fromString(jsonParser, deserializationContext, jsonParser.getText());
        }
        if (jsonTokenCurrentToken == JsonToken.START_OBJECT) {
            return _fromString(jsonParser, deserializationContext, deserializationContext.extractScalarFromObject(jsonParser, this, handledType()));
        }
        if (jsonTokenCurrentToken == JsonToken.VALUE_NUMBER_INT) {
            return _fromNumber(deserializationContext, jsonParser.getIntValue());
        }
        if (jsonTokenCurrentToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
            return (Year) jsonParser.getEmbeddedObject();
        }
        return jsonParser.hasToken(JsonToken.START_ARRAY) ? _deserializeFromArray(jsonParser, deserializationContext) : (Year) _handleUnexpectedToken(deserializationContext, jsonParser, JsonToken.VALUE_STRING, JsonToken.VALUE_NUMBER_INT);
    }

    protected Year _fromString(JsonParser jsonParser, DeserializationContext deserializationContext, String str) throws IOException {
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return _fromEmptyString(jsonParser, deserializationContext, strTrim);
        }
        if (deserializationContext.isEnabled(StreamReadCapability.UNTYPED_SCALARS) && _isValidTimestampString(strTrim)) {
            return _fromNumber(deserializationContext, NumberInput.parseInt(strTrim));
        }
        try {
            if (this._formatter == null) {
                return Year.parse(strTrim);
            }
            return Year.parse(strTrim, this._formatter);
        } catch (DateTimeException e) {
            return (Year) _handleDateTimeException(deserializationContext, e, strTrim);
        }
    }

    protected Year _fromNumber(DeserializationContext deserializationContext, int i) {
        return Year.of(i);
    }
}
