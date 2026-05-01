package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;

/* JADX INFO: loaded from: classes3.dex */
public class PropertyBuilder {
    private static final Object NO_DEFAULT_MARKER = Boolean.FALSE;
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final BeanDescription _beanDesc;
    protected final SerializationConfig _config;
    protected Object _defaultBean;
    protected final JsonInclude.Value _defaultInclusion;
    protected final boolean _useRealPropertyDefaults;

    public PropertyBuilder(SerializationConfig serializationConfig, BeanDescription beanDescription) {
        this._config = serializationConfig;
        this._beanDesc = beanDescription;
        JsonInclude.Value valueMerge = JsonInclude.Value.merge(beanDescription.findPropertyInclusion(JsonInclude.Value.empty()), serializationConfig.getDefaultPropertyInclusion(beanDescription.getBeanClass(), JsonInclude.Value.empty()));
        this._defaultInclusion = JsonInclude.Value.merge(serializationConfig.getDefaultPropertyInclusion(), valueMerge);
        this._useRealPropertyDefaults = valueMerge.getValueInclusion() == JsonInclude.Include.NON_DEFAULT;
        this._annotationIntrospector = serializationConfig.getAnnotationIntrospector();
    }

    public Annotations getClassAnnotations() {
        return this._beanDesc.getClassAnnotations();
    }

    protected BeanPropertyWriter buildWriter(SerializerProvider serializerProvider, BeanPropertyDefinition beanPropertyDefinition, JavaType javaType, JsonSerializer<?> jsonSerializer, TypeSerializer typeSerializer, TypeSerializer typeSerializer2, AnnotatedMember annotatedMember, boolean z) throws JsonMappingException {
        JavaType javaType2;
        Object arrayComparator;
        Object obj;
        Object defaultBean;
        boolean z2;
        Object obj2;
        try {
            JavaType javaTypeFindSerializationType = findSerializationType(annotatedMember, z, javaType);
            if (typeSerializer2 != null) {
                if (javaTypeFindSerializationType == null) {
                    javaTypeFindSerializationType = javaType;
                }
                if (javaTypeFindSerializationType.getContentType() == null) {
                    serializerProvider.reportBadPropertyDefinition(this._beanDesc, beanPropertyDefinition, "serialization type " + javaTypeFindSerializationType + " has no content", new Object[0]);
                }
                JavaType javaTypeWithContentTypeHandler = javaTypeFindSerializationType.withContentTypeHandler(typeSerializer2);
                javaTypeWithContentTypeHandler.getContentType();
                javaType2 = javaTypeWithContentTypeHandler;
            } else {
                javaType2 = javaTypeFindSerializationType;
            }
            JavaType javaType3 = javaType2 == null ? javaType : javaType2;
            AnnotatedMember accessor = beanPropertyDefinition.getAccessor();
            if (accessor == null) {
                return (BeanPropertyWriter) serializerProvider.reportBadPropertyDefinition(this._beanDesc, beanPropertyDefinition, "could not determine property type", new Object[0]);
            }
            JsonInclude.Value valueWithOverrides = this._config.getDefaultInclusion(javaType3.getRawClass(), accessor.getRawType(), this._defaultInclusion).withOverrides(beanPropertyDefinition.findInclusion());
            JsonInclude.Include valueInclusion = valueWithOverrides.getValueInclusion();
            if (valueInclusion == JsonInclude.Include.USE_DEFAULTS) {
                valueInclusion = JsonInclude.Include.ALWAYS;
            }
            int i = C14601.$SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[valueInclusion.ordinal()];
            Object defaultValue = null;
            if (i != 1) {
                if (i == 2) {
                    if (javaType3.isReferenceType()) {
                        obj = BeanPropertyWriter.MARKER_FOR_EMPTY;
                    }
                    z2 = true;
                    obj2 = defaultValue;
                } else if (i != 3) {
                    if (i == 4) {
                        arrayComparator = serializerProvider.includeFilterInstance(beanPropertyDefinition, valueWithOverrides.getValueFilter());
                    } else {
                        z = i == 5;
                        SerializationFeature serializationFeature = SerializationFeature.WRITE_EMPTY_JSON_ARRAYS;
                        if (javaType3.isContainerType() && !this._config.isEnabled(serializationFeature)) {
                            arrayComparator = BeanPropertyWriter.MARKER_FOR_EMPTY;
                        }
                        z2 = z;
                        obj2 = defaultValue;
                    }
                    obj2 = arrayComparator;
                    z2 = z;
                } else {
                    obj = BeanPropertyWriter.MARKER_FOR_EMPTY;
                }
                obj2 = obj;
                z2 = true;
            } else {
                if (this._useRealPropertyDefaults && (defaultBean = getDefaultBean()) != null) {
                    if (serializerProvider.isEnabled(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)) {
                        annotatedMember.fixAccess(this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
                    }
                    try {
                        defaultValue = annotatedMember.getValue(defaultBean);
                    } catch (Exception e) {
                        _throwWrapped(e, beanPropertyDefinition.getName(), defaultBean);
                    }
                } else {
                    defaultValue = BeanUtil.getDefaultValue(javaType3);
                    z = true;
                }
                if (defaultValue == null) {
                    if (!this._useRealPropertyDefaults) {
                        obj = BeanPropertyWriter.MARKER_FOR_EMPTY;
                        obj2 = obj;
                        z2 = true;
                    }
                    z2 = true;
                    obj2 = defaultValue;
                } else {
                    if (defaultValue.getClass().isArray()) {
                        arrayComparator = ArrayBuilders.getArrayComparator(defaultValue);
                        obj2 = arrayComparator;
                        z2 = z;
                    }
                    z2 = z;
                    obj2 = defaultValue;
                }
            }
            Class<?>[] clsArrFindViews = beanPropertyDefinition.findViews();
            if (clsArrFindViews == null) {
                clsArrFindViews = this._beanDesc.findDefaultViews();
            }
            BeanPropertyWriter beanPropertyWriter_constructPropertyWriter = _constructPropertyWriter(beanPropertyDefinition, annotatedMember, this._beanDesc.getClassAnnotations(), javaType, jsonSerializer, typeSerializer, javaType2, z2, obj2, clsArrFindViews);
            Object objFindNullSerializer = this._annotationIntrospector.findNullSerializer(annotatedMember);
            if (objFindNullSerializer != null) {
                beanPropertyWriter_constructPropertyWriter.assignNullSerializer(serializerProvider.serializerInstance(annotatedMember, objFindNullSerializer));
            }
            NameTransformer nameTransformerFindUnwrappingNameTransformer = this._annotationIntrospector.findUnwrappingNameTransformer(annotatedMember);
            return nameTransformerFindUnwrappingNameTransformer != null ? beanPropertyWriter_constructPropertyWriter.unwrappingWriter(nameTransformerFindUnwrappingNameTransformer) : beanPropertyWriter_constructPropertyWriter;
        } catch (JsonMappingException e2) {
            if (beanPropertyDefinition == null) {
                return (BeanPropertyWriter) serializerProvider.reportBadDefinition(javaType, ClassUtil.exceptionMessage(e2));
            }
            return (BeanPropertyWriter) serializerProvider.reportBadPropertyDefinition(this._beanDesc, beanPropertyDefinition, ClassUtil.exceptionMessage(e2), new Object[0]);
        }
    }

    /* JADX INFO: renamed from: com.fasterxml.jackson.databind.ser.PropertyBuilder$1 */
    static /* synthetic */ class C14601 {
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include;

        static {
            int[] iArr = new int[JsonInclude.Include.values().length];
            $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include = iArr;
            try {
                iArr[JsonInclude.Include.NON_DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude.Include.NON_ABSENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude.Include.NON_EMPTY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude.Include.CUSTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude.Include.NON_NULL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude.Include.ALWAYS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    protected BeanPropertyWriter _constructPropertyWriter(BeanPropertyDefinition beanPropertyDefinition, AnnotatedMember annotatedMember, Annotations annotations, JavaType javaType, JsonSerializer<?> jsonSerializer, TypeSerializer typeSerializer, JavaType javaType2, boolean z, Object obj, Class<?>[] clsArr) throws JsonMappingException {
        return new BeanPropertyWriter(beanPropertyDefinition, annotatedMember, annotations, javaType, jsonSerializer, typeSerializer, javaType2, z, obj, clsArr);
    }

    protected JavaType findSerializationType(Annotated annotated, boolean z, JavaType javaType) throws JsonMappingException {
        JavaType javaTypeRefineSerializationType = this._annotationIntrospector.refineSerializationType(this._config, annotated, javaType);
        if (javaTypeRefineSerializationType != javaType) {
            Class<?> rawClass = javaTypeRefineSerializationType.getRawClass();
            Class<?> rawClass2 = javaType.getRawClass();
            if (!rawClass.isAssignableFrom(rawClass2) && !rawClass2.isAssignableFrom(rawClass)) {
                throw new IllegalArgumentException("Illegal concrete-type annotation for method '" + annotated.getName() + "': class " + rawClass.getName() + " not a super-type of (declared) class " + rawClass2.getName());
            }
            javaType = javaTypeRefineSerializationType;
            z = true;
        }
        JsonSerialize.Typing typingFindSerializationTyping = this._annotationIntrospector.findSerializationTyping(annotated);
        if (typingFindSerializationTyping != null && typingFindSerializationTyping != JsonSerialize.Typing.DEFAULT_TYPING) {
            z = typingFindSerializationTyping == JsonSerialize.Typing.STATIC;
        }
        if (z) {
            return javaType.withStaticTyping();
        }
        return null;
    }

    protected Object getDefaultBean() {
        Object objInstantiateBean = this._defaultBean;
        if (objInstantiateBean == null) {
            objInstantiateBean = this._beanDesc.instantiateBean(this._config.canOverrideAccessModifiers());
            if (objInstantiateBean == null) {
                objInstantiateBean = NO_DEFAULT_MARKER;
            }
            this._defaultBean = objInstantiateBean;
        }
        if (objInstantiateBean == NO_DEFAULT_MARKER) {
            return null;
        }
        return this._defaultBean;
    }

    @Deprecated
    protected Object getPropertyDefaultValue(String str, AnnotatedMember annotatedMember, JavaType javaType) {
        Object defaultBean = getDefaultBean();
        if (defaultBean == null) {
            return getDefaultValue(javaType);
        }
        try {
            return annotatedMember.getValue(defaultBean);
        } catch (Exception e) {
            return _throwWrapped(e, str, defaultBean);
        }
    }

    @Deprecated
    protected Object getDefaultValue(JavaType javaType) {
        return BeanUtil.getDefaultValue(javaType);
    }

    protected Object _throwWrapped(Exception exc, String str, Object obj) {
        Throwable cause = exc;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        ClassUtil.throwIfError(cause);
        ClassUtil.throwIfRTE(cause);
        throw new IllegalArgumentException("Failed to get property '" + str + "' of default " + obj.getClass().getName() + " instance");
    }
}
