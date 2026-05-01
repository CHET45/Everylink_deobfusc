package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.io.IOException;
import java.time.Month;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public class OneBasedMonthDeserializer extends DelegatingDeserializer {
    private static final Pattern HAS_ONE_OR_TWO_DIGITS = Pattern.compile("^\\d{1,2}$");
    private static final long serialVersionUID = 1;

    public OneBasedMonthDeserializer(JsonDeserializer<?> jsonDeserializer) {
        super(jsonDeserializer);
    }

    @Override // com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken jsonTokenCurrentToken = jsonParser.currentToken();
        Month month = (Month) getDelegatee().deserialize(jsonParser, deserializationContext);
        if (!_isNumericValue(jsonParser.getText(), jsonTokenCurrentToken)) {
            return month;
        }
        if (month == Month.JANUARY) {
            throw new InvalidFormatException(jsonParser, "Month.JANUARY value not allowed for 1-based Month.", month, (Class<?>) Month.class);
        }
        return month.minus(1L);
    }

    private boolean _isNumericValue(String str, JsonToken jsonToken) {
        return jsonToken == JsonToken.VALUE_NUMBER_INT || _isNumberAsString(str, jsonToken);
    }

    private boolean _isNumberAsString(String str, JsonToken jsonToken) {
        return jsonToken == JsonToken.VALUE_STRING && HAS_ONE_OR_TWO_DIGITS.matcher(str).matches();
    }

    @Override // com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer
    protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> jsonDeserializer) {
        return new OneBasedMonthDeserializer(jsonDeserializer);
    }
}
