package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.Month;

/* JADX INFO: loaded from: classes3.dex */
public class OneBasedMonthSerializer extends JsonSerializer<Month> {
    private final JsonSerializer<Object> _defaultSerializer;

    public OneBasedMonthSerializer(JsonSerializer<?> jsonSerializer) {
        this._defaultSerializer = jsonSerializer;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(Month month, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (serializerProvider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX)) {
            jsonGenerator.writeNumber(month.ordinal() + 1);
        } else {
            this._defaultSerializer.serialize(month, jsonGenerator, serializerProvider);
        }
    }
}
