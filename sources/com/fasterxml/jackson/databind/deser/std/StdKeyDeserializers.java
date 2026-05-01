package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedAndMetadata;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes3.dex */
public class StdKeyDeserializers implements KeyDeserializers, Serializable {
    private static final long serialVersionUID = 1;

    public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver enumResolver) {
        return new StdKeyDeserializer.EnumKD(enumResolver, null);
    }

    public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver enumResolver, AnnotatedMethod annotatedMethod) {
        return new StdKeyDeserializer.EnumKD(enumResolver, annotatedMethod);
    }

    public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver enumResolver, EnumResolver enumResolver2, EnumResolver enumResolver3, EnumResolver enumResolver4) {
        return new StdKeyDeserializer.EnumKD(enumResolver, null, enumResolver2, enumResolver3, enumResolver4);
    }

    public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver enumResolver, AnnotatedMethod annotatedMethod, EnumResolver enumResolver2, EnumResolver enumResolver3, EnumResolver enumResolver4) {
        return new StdKeyDeserializer.EnumKD(enumResolver, annotatedMethod, enumResolver2, enumResolver3, enumResolver4);
    }

    public static KeyDeserializer constructDelegatingKeyDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, JsonDeserializer<?> jsonDeserializer) {
        return new StdKeyDeserializer.DelegatingKD(javaType.getRawClass(), jsonDeserializer);
    }

    public static KeyDeserializer findStringBasedKeyDeserializer(DeserializationConfig deserializationConfig, JavaType javaType) throws JsonMappingException {
        BeanDescription beanDescriptionIntrospectForCreation = deserializationConfig.introspectForCreation(javaType);
        AnnotatedAndMetadata<AnnotatedConstructor, JsonCreator.Mode> annotatedAndMetadata_findStringConstructor = _findStringConstructor(beanDescriptionIntrospectForCreation);
        if (annotatedAndMetadata_findStringConstructor != null && annotatedAndMetadata_findStringConstructor.metadata != null) {
            return _constructCreatorKeyDeserializer(deserializationConfig, (AnnotatedMember) annotatedAndMetadata_findStringConstructor.annotated);
        }
        List<AnnotatedAndMetadata<AnnotatedMethod, JsonCreator.Mode>> factoryMethodsWithMode = beanDescriptionIntrospectForCreation.getFactoryMethodsWithMode();
        factoryMethodsWithMode.removeIf(new Predicate() { // from class: com.fasterxml.jackson.databind.deser.std.StdKeyDeserializers$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return StdKeyDeserializers.lambda$findStringBasedKeyDeserializer$0((AnnotatedAndMetadata) obj);
            }
        });
        AnnotatedMethod annotatedMethod_findExplicitStringFactoryMethod = _findExplicitStringFactoryMethod(factoryMethodsWithMode);
        if (annotatedMethod_findExplicitStringFactoryMethod != null) {
            return _constructCreatorKeyDeserializer(deserializationConfig, annotatedMethod_findExplicitStringFactoryMethod);
        }
        if (annotatedAndMetadata_findStringConstructor != null) {
            return _constructCreatorKeyDeserializer(deserializationConfig, (AnnotatedMember) annotatedAndMetadata_findStringConstructor.annotated);
        }
        if (factoryMethodsWithMode.isEmpty()) {
            return null;
        }
        return _constructCreatorKeyDeserializer(deserializationConfig, (AnnotatedMember) factoryMethodsWithMode.get(0).annotated);
    }

    static /* synthetic */ boolean lambda$findStringBasedKeyDeserializer$0(AnnotatedAndMetadata annotatedAndMetadata) {
        return (((AnnotatedMethod) annotatedAndMetadata.annotated).getParameterCount() == 1 && ((AnnotatedMethod) annotatedAndMetadata.annotated).getRawParameterType(0) == String.class && annotatedAndMetadata.metadata != JsonCreator.Mode.PROPERTIES) ? false : true;
    }

    private static KeyDeserializer _constructCreatorKeyDeserializer(DeserializationConfig deserializationConfig, AnnotatedMember annotatedMember) {
        if (annotatedMember instanceof AnnotatedConstructor) {
            Constructor<?> annotated = ((AnnotatedConstructor) annotatedMember).getAnnotated();
            if (deserializationConfig.canOverrideAccessModifiers()) {
                ClassUtil.checkAndFixAccess(annotated, deserializationConfig.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
            }
            return new StdKeyDeserializer.StringCtorKeyDeserializer(annotated);
        }
        Method annotated2 = ((AnnotatedMethod) annotatedMember).getAnnotated();
        if (deserializationConfig.canOverrideAccessModifiers()) {
            ClassUtil.checkAndFixAccess(annotated2, deserializationConfig.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
        }
        return new StdKeyDeserializer.StringFactoryKeyDeserializer(annotated2);
    }

    private static AnnotatedAndMetadata<AnnotatedConstructor, JsonCreator.Mode> _findStringConstructor(BeanDescription beanDescription) {
        for (AnnotatedAndMetadata<AnnotatedConstructor, JsonCreator.Mode> annotatedAndMetadata : beanDescription.getConstructorsWithMode()) {
            AnnotatedConstructor annotatedConstructor = (AnnotatedConstructor) annotatedAndMetadata.annotated;
            if (annotatedConstructor.getParameterCount() == 1 && String.class == annotatedConstructor.getRawParameterType(0)) {
                return annotatedAndMetadata;
            }
        }
        return null;
    }

    private static AnnotatedMethod _findExplicitStringFactoryMethod(List<AnnotatedAndMetadata<AnnotatedMethod, JsonCreator.Mode>> list) throws JsonMappingException {
        AnnotatedMethod annotatedMethod = null;
        for (AnnotatedAndMetadata<AnnotatedMethod, JsonCreator.Mode> annotatedAndMetadata : list) {
            if (annotatedAndMetadata.metadata != null) {
                if (annotatedMethod == null) {
                    annotatedMethod = (AnnotatedMethod) annotatedAndMetadata.annotated;
                } else {
                    throw new IllegalArgumentException("Multiple suitable annotated Creator factory methods to be used as the Key deserializer for type " + ClassUtil.nameOf(((AnnotatedMethod) annotatedAndMetadata.annotated).getDeclaringClass()));
                }
            }
        }
        return annotatedMethod;
    }

    @Override // com.fasterxml.jackson.databind.deser.KeyDeserializers
    public KeyDeserializer findKeyDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
        Class<?> rawClass = javaType.getRawClass();
        if (rawClass.isPrimitive()) {
            rawClass = ClassUtil.wrapperType(rawClass);
        }
        return StdKeyDeserializer.forType(rawClass);
    }
}
