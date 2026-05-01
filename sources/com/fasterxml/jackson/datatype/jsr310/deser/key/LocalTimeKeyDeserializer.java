package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes3.dex */
public class LocalTimeKeyDeserializer extends Jsr310KeyDeserializer {
    public static final LocalTimeKeyDeserializer INSTANCE = new LocalTimeKeyDeserializer();

    private LocalTimeKeyDeserializer() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.key.Jsr310KeyDeserializer
    public LocalTime deserialize(String str, DeserializationContext deserializationContext) throws IOException {
        try {
            return LocalTime.parse(str, DateTimeFormatter.ISO_LOCAL_TIME);
        } catch (DateTimeException e) {
            return (LocalTime) _handleDateTimeException(deserializationContext, LocalTime.class, e, str);
        }
    }
}
