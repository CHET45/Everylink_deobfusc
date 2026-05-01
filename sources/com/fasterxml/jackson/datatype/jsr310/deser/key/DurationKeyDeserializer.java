package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Duration;

/* JADX INFO: loaded from: classes3.dex */
public class DurationKeyDeserializer extends Jsr310KeyDeserializer {
    public static final DurationKeyDeserializer INSTANCE = new DurationKeyDeserializer();

    private DurationKeyDeserializer() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.key.Jsr310KeyDeserializer
    public Duration deserialize(String str, DeserializationContext deserializationContext) throws IOException {
        try {
            return Duration.parse(str);
        } catch (DateTimeException e) {
            return (Duration) _handleDateTimeException(deserializationContext, Duration.class, e, str);
        }
    }
}
