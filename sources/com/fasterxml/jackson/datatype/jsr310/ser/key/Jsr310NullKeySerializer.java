package com.fasterxml.jackson.datatype.jsr310.ser.key;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public class Jsr310NullKeySerializer extends JsonSerializer<Object> {
    public static final String NULL_KEY = "";

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(Object obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (obj != null) {
            throw JsonMappingException.from(jsonGenerator, "Jsr310NullKeySerializer is only for serializing null values.");
        }
        jsonGenerator.writeFieldName("");
    }
}
