package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes3.dex */
public class OffsetDateTimeKeyDeserializer extends Jsr310KeyDeserializer {
    public static final OffsetDateTimeKeyDeserializer INSTANCE = new OffsetDateTimeKeyDeserializer();

    private OffsetDateTimeKeyDeserializer() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.key.Jsr310KeyDeserializer
    public OffsetDateTime deserialize(String str, DeserializationContext deserializationContext) throws IOException {
        try {
            return OffsetDateTime.parse(str, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeException e) {
            return (OffsetDateTime) _handleDateTimeException(deserializationContext, OffsetDateTime.class, e, str);
        }
    }
}
