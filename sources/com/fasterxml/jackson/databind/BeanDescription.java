package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.introspect.AnnotatedAndMetadata;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.introspect.PotentialCreators;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.Converter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BeanDescription {
    protected final JavaType _type;

    public abstract AnnotatedMember findAnyGetter();

    public abstract AnnotatedMember findAnySetterAccessor();

    public abstract List<BeanPropertyDefinition> findBackReferences();

    public String findClassDescription() {
        return null;
    }

    public abstract AnnotatedConstructor findDefaultConstructor();

    public abstract Class<?>[] findDefaultViews();

    public abstract Converter<Object, Object> findDeserializationConverter();

    public abstract JsonFormat.Value findExpectedFormat();

    public abstract Map<Object, AnnotatedMember> findInjectables();

    public AnnotatedMember findJsonKeyAccessor() {
        return null;
    }

    public abstract AnnotatedMember findJsonValueAccessor();

    public abstract AnnotatedMethod findMethod(String str, Class<?>[] clsArr);

    public abstract Class<?> findPOJOBuilder();

    public abstract JsonPOJOBuilder.Value findPOJOBuilderConfig();

    public abstract List<BeanPropertyDefinition> findProperties();

    public abstract JsonInclude.Value findPropertyInclusion(JsonInclude.Value value);

    public abstract Converter<Object, Object> findSerializationConverter();

    public abstract Annotations getClassAnnotations();

    public abstract AnnotatedClass getClassInfo();

    public abstract List<AnnotatedConstructor> getConstructors();

    public abstract List<AnnotatedAndMetadata<AnnotatedConstructor, JsonCreator.Mode>> getConstructorsWithMode();

    public abstract List<AnnotatedMethod> getFactoryMethods();

    public abstract List<AnnotatedAndMetadata<AnnotatedMethod, JsonCreator.Mode>> getFactoryMethodsWithMode();

    public abstract Set<String> getIgnoredPropertyNames();

    public abstract ObjectIdInfo getObjectIdInfo();

    public abstract PotentialCreators getPotentialCreators();

    public abstract boolean hasKnownClassAnnotations();

    public abstract Object instantiateBean(boolean z);

    protected BeanDescription(JavaType javaType) {
        this._type = javaType;
    }

    public JavaType getType() {
        return this._type;
    }

    public Class<?> getBeanClass() {
        return this._type.getRawClass();
    }

    public boolean isRecordType() {
        return this._type.isRecordType();
    }

    public boolean isNonStaticInnerClass() {
        return getClassInfo().isNonStaticInnerClass();
    }

    @Deprecated
    public JsonFormat.Value findExpectedFormat(JsonFormat.Value value) {
        JsonFormat.Value valueFindExpectedFormat = findExpectedFormat();
        return value == null ? valueFindExpectedFormat : valueFindExpectedFormat == null ? value : value.withOverrides(valueFindExpectedFormat);
    }
}
