package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import java.time.Month;

/* JADX INFO: loaded from: classes3.dex */
public class JavaTimeSerializerModifier extends BeanSerializerModifier {
    private static final long serialVersionUID = 1;
    private final boolean _oneBaseMonths;

    public JavaTimeSerializerModifier(boolean z) {
        this._oneBaseMonths = z;
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanSerializerModifier
    public JsonSerializer<?> modifyEnumSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription, JsonSerializer<?> jsonSerializer) {
        return (this._oneBaseMonths && javaType.hasRawClass(Month.class)) ? new OneBasedMonthSerializer(jsonSerializer) : jsonSerializer;
    }
}
