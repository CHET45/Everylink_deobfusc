package com.fasterxml.jackson.datatype.jsr310.deser.key;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import java.io.IOException;
import java.time.DateTimeException;

/* JADX INFO: loaded from: classes3.dex */
abstract class Jsr310KeyDeserializer extends KeyDeserializer {
    protected abstract Object deserialize(String str, DeserializationContext deserializationContext) throws IOException;

    Jsr310KeyDeserializer() {
    }

    @Override // com.fasterxml.jackson.databind.KeyDeserializer
    public final Object deserializeKey(String str, DeserializationContext deserializationContext) throws IOException {
        if ("".equals(str)) {
            return null;
        }
        return deserialize(str, deserializationContext);
    }

    protected <T> T _handleDateTimeException(DeserializationContext deserializationContext, Class<?> cls, DateTimeException dateTimeException, String str) throws IOException {
        try {
            return (T) deserializationContext.handleWeirdKey(cls, str, "Failed to deserialize %s: (%s) %s", cls.getName(), dateTimeException.getClass().getName(), dateTimeException.getMessage());
        } catch (JsonMappingException e) {
            e.initCause(dateTimeException);
            throw e;
        } catch (IOException e2) {
            if (e2.getCause() == null) {
                e2.initCause(dateTimeException);
            }
            throw JsonMappingException.fromUnexpectedIOE(e2);
        }
    }
}
