package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes3.dex */
public class OffsetTimeKeyDeserializer extends Jsr310KeyDeserializer {
    public static final OffsetTimeKeyDeserializer INSTANCE = new OffsetTimeKeyDeserializer();

    private OffsetTimeKeyDeserializer() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.key.Jsr310KeyDeserializer
    public OffsetTime deserialize(String str, DeserializationContext deserializationContext) throws IOException {
        try {
            return OffsetTime.parse(str, DateTimeFormatter.ISO_OFFSET_TIME);
        } catch (DateTimeException e) {
            return (OffsetTime) _handleDateTimeException(deserializationContext, OffsetTime.class, e, str);
        }
    }
}
