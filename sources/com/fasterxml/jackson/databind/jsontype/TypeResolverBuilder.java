package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import java.util.Collection;

/* JADX INFO: loaded from: classes3.dex */
public interface TypeResolverBuilder<T extends TypeResolverBuilder<T>> {
    TypeDeserializer buildTypeDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, Collection<NamedType> collection);

    TypeSerializer buildTypeSerializer(SerializationConfig serializationConfig, JavaType javaType, Collection<NamedType> collection);

    T defaultImpl(Class<?> cls);

    Class<?> getDefaultImpl();

    T inclusion(JsonTypeInfo.EnumC1421As enumC1421As);

    T init(JsonTypeInfo.EnumC1422Id enumC1422Id, TypeIdResolver typeIdResolver);

    T typeIdVisibility(boolean z);

    T typeProperty(String str);

    default T init(JsonTypeInfo.Value value, TypeIdResolver typeIdResolver) {
        return (T) init(value.getIdType(), typeIdResolver);
    }

    default T withDefaultImpl(Class<?> cls) {
        return (T) defaultImpl(cls);
    }

    default T withSettings(JsonTypeInfo.Value value) {
        throw new IllegalStateException("TypeResolveBuilder implementation " + getClass().getName() + " must implement `withSettings()`");
    }
}
