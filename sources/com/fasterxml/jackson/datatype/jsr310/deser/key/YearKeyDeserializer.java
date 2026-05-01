package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Year;

/* JADX INFO: loaded from: classes3.dex */
public class YearKeyDeserializer extends Jsr310KeyDeserializer {
    public static final YearKeyDeserializer INSTANCE = new YearKeyDeserializer();

    protected YearKeyDeserializer() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.key.Jsr310KeyDeserializer
    public Year deserialize(String str, DeserializationContext deserializationContext) throws IOException {
        try {
            return Year.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return (Year) _handleDateTimeException(deserializationContext, Year.class, new DateTimeException("Number format exception", e), str);
        } catch (DateTimeException e2) {
            return (Year) _handleDateTimeException(deserializationContext, Year.class, e2, str);
        }
    }
}
