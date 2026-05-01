package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneOffset;

/* JADX INFO: loaded from: classes3.dex */
public class ZoneOffsetKeyDeserializer extends Jsr310KeyDeserializer {
    public static final ZoneOffsetKeyDeserializer INSTANCE = new ZoneOffsetKeyDeserializer();

    private ZoneOffsetKeyDeserializer() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.key.Jsr310KeyDeserializer
    public ZoneOffset deserialize(String str, DeserializationContext deserializationContext) throws IOException {
        try {
            return ZoneOffset.of(str);
        } catch (DateTimeException e) {
            return (ZoneOffset) _handleDateTimeException(deserializationContext, ZoneOffset.class, e, str);
        }
    }
}
