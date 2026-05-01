package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes3.dex */
public class LocalDateKeyDeserializer extends Jsr310KeyDeserializer {
    public static final LocalDateKeyDeserializer INSTANCE = new LocalDateKeyDeserializer();

    private LocalDateKeyDeserializer() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.key.Jsr310KeyDeserializer
    public LocalDate deserialize(String str, DeserializationContext deserializationContext) throws IOException {
        try {
            return LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeException e) {
            return (LocalDate) _handleDateTimeException(deserializationContext, LocalDate.class, e, str);
        }
    }
}
