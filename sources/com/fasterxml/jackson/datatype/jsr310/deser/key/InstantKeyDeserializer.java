package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

/* JADX INFO: loaded from: classes3.dex */
public class InstantKeyDeserializer extends Jsr310KeyDeserializer {
    public static final InstantKeyDeserializer INSTANCE = new InstantKeyDeserializer();

    private InstantKeyDeserializer() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.key.Jsr310KeyDeserializer
    public Instant deserialize(String str, DeserializationContext deserializationContext) throws IOException {
        try {
            return (Instant) DateTimeFormatter.ISO_INSTANT.parse(str, new TemporalQuery() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.key.InstantKeyDeserializer$$ExternalSyntheticLambda0
                @Override // java.time.temporal.TemporalQuery
                public final Object queryFrom(TemporalAccessor temporalAccessor) {
                    return Instant.from(temporalAccessor);
                }
            });
        } catch (DateTimeException e) {
            return (Instant) _handleDateTimeException(deserializationContext, Instant.class, e, str);
        }
    }
}
