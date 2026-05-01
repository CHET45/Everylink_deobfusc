package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Period;

/* JADX INFO: loaded from: classes3.dex */
public class PeriodKeyDeserializer extends Jsr310KeyDeserializer {
    public static final PeriodKeyDeserializer INSTANCE = new PeriodKeyDeserializer();

    private PeriodKeyDeserializer() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.key.Jsr310KeyDeserializer
    public Period deserialize(String str, DeserializationContext deserializationContext) throws IOException {
        try {
            return Period.parse(str);
        } catch (DateTimeException e) {
            return (Period) _handleDateTimeException(deserializationContext, Period.class, e, str);
        }
    }
}
