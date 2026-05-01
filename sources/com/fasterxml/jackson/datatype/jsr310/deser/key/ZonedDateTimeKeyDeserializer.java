package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZonedDateTime;

/* JADX INFO: loaded from: classes3.dex */
public class ZonedDateTimeKeyDeserializer extends Jsr310KeyDeserializer {
    public static final ZonedDateTimeKeyDeserializer INSTANCE = new ZonedDateTimeKeyDeserializer();

    private ZonedDateTimeKeyDeserializer() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.key.Jsr310KeyDeserializer
    public ZonedDateTime deserialize(String str, DeserializationContext deserializationContext) throws IOException {
        try {
            return ZonedDateTime.parse(str);
        } catch (DateTimeException e) {
            return (ZonedDateTime) _handleDateTimeException(deserializationContext, ZonedDateTime.class, e, str);
        }
    }
}
