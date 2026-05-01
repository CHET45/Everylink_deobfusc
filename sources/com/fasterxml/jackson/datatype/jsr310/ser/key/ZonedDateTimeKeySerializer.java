package com.fasterxml.jackson.datatype.jsr310.ser.key;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: classes3.dex */
public class ZonedDateTimeKeySerializer extends JsonSerializer<ZonedDateTime> {
    public static final ZonedDateTimeKeySerializer INSTANCE = new ZonedDateTimeKeySerializer();

    private ZonedDateTimeKeySerializer() {
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (serializerProvider.isEnabled(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)) {
            jsonGenerator.writeFieldName(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedDateTime));
            return;
        }
        if (useTimestamps(serializerProvider)) {
            if (useNanos(serializerProvider)) {
                jsonGenerator.writeFieldName(DecimalUtils.toBigDecimal(zonedDateTime.toEpochSecond(), zonedDateTime.getNano()).toString());
                return;
            } else {
                jsonGenerator.writeFieldName(String.valueOf(zonedDateTime.toInstant().toEpochMilli()));
                return;
            }
        }
        jsonGenerator.writeFieldName(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime));
    }

    private static boolean useNanos(SerializerProvider serializerProvider) {
        return serializerProvider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
    }

    private static boolean useTimestamps(SerializerProvider serializerProvider) {
        return serializerProvider.isEnabled(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    }
}
