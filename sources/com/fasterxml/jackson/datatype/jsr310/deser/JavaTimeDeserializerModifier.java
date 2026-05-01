package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import java.time.Month;

/* JADX INFO: loaded from: classes3.dex */
public class JavaTimeDeserializerModifier extends BeanDeserializerModifier {
    private static final long serialVersionUID = 1;
    private final boolean _oneBaseMonths;

    public JavaTimeDeserializerModifier(boolean z) {
        this._oneBaseMonths = z;
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerModifier
    public JsonDeserializer<?> modifyEnumDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, BeanDescription beanDescription, JsonDeserializer<?> jsonDeserializer) {
        return (this._oneBaseMonths && javaType.hasRawClass(Month.class)) ? new OneBasedMonthDeserializer(jsonDeserializer) : jsonDeserializer;
    }
}
