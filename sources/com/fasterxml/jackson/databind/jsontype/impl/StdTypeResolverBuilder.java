package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.annotation.NoClass;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.util.Collection;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class StdTypeResolverBuilder implements TypeResolverBuilder<StdTypeResolverBuilder> {
    protected TypeIdResolver _customIdResolver;
    protected Class<?> _defaultImpl;
    protected JsonTypeInfo.EnumC1422Id _idType;
    protected JsonTypeInfo.EnumC1421As _includeAs;
    protected Boolean _requireTypeIdForSubtypes;
    protected boolean _typeIdVisible;
    protected String _typeProperty;

    protected boolean allowPrimitiveTypes(MapperConfig<?> mapperConfig, JavaType javaType) {
        return false;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public /* bridge */ /* synthetic */ TypeResolverBuilder defaultImpl(Class cls) {
        return defaultImpl((Class<?>) cls);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public /* bridge */ /* synthetic */ TypeResolverBuilder withDefaultImpl(Class cls) {
        return withDefaultImpl((Class<?>) cls);
    }

    public StdTypeResolverBuilder() {
        this._typeIdVisible = false;
    }

    protected StdTypeResolverBuilder(JsonTypeInfo.EnumC1422Id enumC1422Id, JsonTypeInfo.EnumC1421As enumC1421As, String str) {
        this._typeIdVisible = false;
        this._idType = enumC1422Id;
        this._includeAs = enumC1421As;
        this._typeProperty = _propName(str, enumC1422Id);
    }

    protected StdTypeResolverBuilder(StdTypeResolverBuilder stdTypeResolverBuilder, Class<?> cls) {
        this._typeIdVisible = false;
        this._idType = stdTypeResolverBuilder._idType;
        this._includeAs = stdTypeResolverBuilder._includeAs;
        this._typeProperty = stdTypeResolverBuilder._typeProperty;
        this._typeIdVisible = stdTypeResolverBuilder._typeIdVisible;
        this._customIdResolver = stdTypeResolverBuilder._customIdResolver;
        this._defaultImpl = cls;
        this._requireTypeIdForSubtypes = stdTypeResolverBuilder._requireTypeIdForSubtypes;
    }

    public StdTypeResolverBuilder(JsonTypeInfo.Value value) {
        this._typeIdVisible = false;
        if (value != null) {
            withSettings(value);
        }
    }

    public static StdTypeResolverBuilder noTypeInfoBuilder() {
        return new StdTypeResolverBuilder().withSettings(JsonTypeInfo.Value.construct(JsonTypeInfo.EnumC1422Id.NONE, null, null, null, false, null));
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public TypeSerializer buildTypeSerializer(SerializationConfig serializationConfig, JavaType javaType, Collection<NamedType> collection) {
        if (this._idType == JsonTypeInfo.EnumC1422Id.NONE) {
            return null;
        }
        if (javaType.isPrimitive() && !allowPrimitiveTypes(serializationConfig, javaType)) {
            return null;
        }
        if (this._idType == JsonTypeInfo.EnumC1422Id.DEDUCTION) {
            return AsDeductionTypeSerializer.instance();
        }
        TypeIdResolver typeIdResolverIdResolver = idResolver(serializationConfig, javaType, subTypeValidator(serializationConfig), collection, true, false);
        int i = C14561.$SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[this._includeAs.ordinal()];
        if (i == 1) {
            return new AsArrayTypeSerializer(typeIdResolverIdResolver, null);
        }
        if (i == 2) {
            return new AsPropertyTypeSerializer(typeIdResolverIdResolver, null, this._typeProperty);
        }
        if (i == 3) {
            return new AsWrapperTypeSerializer(typeIdResolverIdResolver, null);
        }
        if (i == 4) {
            return new AsExternalTypeSerializer(typeIdResolverIdResolver, null, this._typeProperty);
        }
        if (i == 5) {
            return new AsExistingPropertyTypeSerializer(typeIdResolverIdResolver, null, this._typeProperty);
        }
        throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + this._includeAs);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public TypeDeserializer buildTypeDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, Collection<NamedType> collection) {
        if (this._idType == JsonTypeInfo.EnumC1422Id.NONE) {
            return null;
        }
        if (javaType.isPrimitive() && !allowPrimitiveTypes(deserializationConfig, javaType)) {
            return null;
        }
        TypeIdResolver typeIdResolverIdResolver = idResolver(deserializationConfig, javaType, verifyBaseTypeValidity(deserializationConfig, javaType), collection, false, true);
        JavaType javaTypeDefineDefaultImpl = defineDefaultImpl(deserializationConfig, javaType);
        if (this._idType == JsonTypeInfo.EnumC1422Id.DEDUCTION) {
            return new AsDeductionTypeDeserializer(javaType, typeIdResolverIdResolver, javaTypeDefineDefaultImpl, deserializationConfig, collection);
        }
        int i = C14561.$SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[this._includeAs.ordinal()];
        if (i == 1) {
            return new AsArrayTypeDeserializer(javaType, typeIdResolverIdResolver, this._typeProperty, this._typeIdVisible, javaTypeDefineDefaultImpl);
        }
        if (i != 2) {
            if (i == 3) {
                return new AsWrapperTypeDeserializer(javaType, typeIdResolverIdResolver, this._typeProperty, this._typeIdVisible, javaTypeDefineDefaultImpl);
            }
            if (i == 4) {
                return new AsExternalTypeDeserializer(javaType, typeIdResolverIdResolver, this._typeProperty, this._typeIdVisible, javaTypeDefineDefaultImpl);
            }
            if (i != 5) {
                throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + this._includeAs);
            }
        }
        return new AsPropertyTypeDeserializer(javaType, typeIdResolverIdResolver, this._typeProperty, this._typeIdVisible, javaTypeDefineDefaultImpl, this._includeAs, _strictTypeIdHandling(deserializationConfig, javaType));
    }

    protected JavaType defineDefaultImpl(DeserializationConfig deserializationConfig, JavaType javaType) {
        Class<?> cls = this._defaultImpl;
        if (cls != null) {
            if (cls == Void.class || cls == NoClass.class) {
                return deserializationConfig.getTypeFactory().constructType(this._defaultImpl);
            }
            if (javaType.hasRawClass(cls)) {
                return javaType;
            }
            if (javaType.isTypeOrSuperTypeOf(this._defaultImpl)) {
                return deserializationConfig.getTypeFactory().constructSpecializedType(javaType, this._defaultImpl);
            }
            if (javaType.hasRawClass(this._defaultImpl)) {
                return javaType;
            }
        }
        if (!deserializationConfig.isEnabled(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL) || javaType.isAbstract()) {
            return null;
        }
        return javaType;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder init(JsonTypeInfo.EnumC1422Id enumC1422Id, TypeIdResolver typeIdResolver) {
        if (enumC1422Id == null) {
            throw new IllegalArgumentException("idType cannot be null");
        }
        this._idType = enumC1422Id;
        this._customIdResolver = typeIdResolver;
        this._typeProperty = _propName(null, enumC1422Id);
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder init(JsonTypeInfo.Value value, TypeIdResolver typeIdResolver) {
        this._customIdResolver = typeIdResolver;
        if (value != null) {
            withSettings(value);
        }
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder inclusion(JsonTypeInfo.EnumC1421As enumC1421As) {
        if (enumC1421As == null) {
            throw new IllegalArgumentException("includeAs cannot be null");
        }
        this._includeAs = enumC1421As;
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder typeProperty(String str) {
        this._typeProperty = _propName(str, this._idType);
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder defaultImpl(Class<?> cls) {
        this._defaultImpl = cls;
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder typeIdVisibility(boolean z) {
        this._typeIdVisible = z;
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder withDefaultImpl(Class<?> cls) {
        if (this._defaultImpl == cls) {
            return this;
        }
        ClassUtil.verifyMustOverride(StdTypeResolverBuilder.class, this, "withDefaultImpl");
        return new StdTypeResolverBuilder(this, cls);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder withSettings(JsonTypeInfo.Value value) {
        this._idType = (JsonTypeInfo.EnumC1422Id) Objects.requireNonNull(value.getIdType());
        this._includeAs = value.getInclusionType();
        this._typeProperty = _propName(value.getPropertyName(), this._idType);
        this._defaultImpl = value.getDefaultImpl();
        this._typeIdVisible = value.getIdVisible();
        this._requireTypeIdForSubtypes = value.getRequireTypeIdForSubtypes();
        return this;
    }

    protected String _propName(String str, JsonTypeInfo.EnumC1422Id enumC1422Id) {
        return (str == null || str.isEmpty()) ? enumC1422Id.getDefaultPropertyName() : str;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public Class<?> getDefaultImpl() {
        return this._defaultImpl;
    }

    public String getTypeProperty() {
        return this._typeProperty;
    }

    public boolean isTypeIdVisible() {
        return this._typeIdVisible;
    }

    protected TypeIdResolver idResolver(MapperConfig<?> mapperConfig, JavaType javaType, PolymorphicTypeValidator polymorphicTypeValidator, Collection<NamedType> collection, boolean z, boolean z2) {
        TypeIdResolver typeIdResolver = this._customIdResolver;
        if (typeIdResolver != null) {
            return typeIdResolver;
        }
        if (this._idType == null) {
            throw new IllegalStateException("Cannot build, 'init()' not yet called");
        }
        switch (C14561.$SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$Id[this._idType.ordinal()]) {
            case 1:
            case 2:
                return ClassNameIdResolver.construct(javaType, mapperConfig, polymorphicTypeValidator);
            case 3:
                return MinimalClassNameIdResolver.construct(javaType, mapperConfig, polymorphicTypeValidator);
            case 4:
                return SimpleNameIdResolver.construct(mapperConfig, javaType, collection, z, z2);
            case 5:
                return TypeNameIdResolver.construct(mapperConfig, javaType, collection, z, z2);
            case 6:
                return null;
            default:
                throw new IllegalStateException("Do not know how to construct standard type id resolver for idType: " + this._idType);
        }
    }

    /* JADX INFO: renamed from: com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder$1 */
    static /* synthetic */ class C14561 {
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As;
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$Id;

        static {
            int[] iArr = new int[JsonTypeInfo.EnumC1422Id.values().length];
            $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$Id = iArr;
            try {
                iArr[JsonTypeInfo.EnumC1422Id.DEDUCTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$Id[JsonTypeInfo.EnumC1422Id.CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$Id[JsonTypeInfo.EnumC1422Id.MINIMAL_CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$Id[JsonTypeInfo.EnumC1422Id.SIMPLE_NAME.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$Id[JsonTypeInfo.EnumC1422Id.NAME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$Id[JsonTypeInfo.EnumC1422Id.NONE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$Id[JsonTypeInfo.EnumC1422Id.CUSTOM.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            int[] iArr2 = new int[JsonTypeInfo.EnumC1421As.values().length];
            $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As = iArr2;
            try {
                iArr2[JsonTypeInfo.EnumC1421As.WRAPPER_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[JsonTypeInfo.EnumC1421As.PROPERTY.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[JsonTypeInfo.EnumC1421As.WRAPPER_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[JsonTypeInfo.EnumC1421As.EXTERNAL_PROPERTY.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonTypeInfo$As[JsonTypeInfo.EnumC1421As.EXISTING_PROPERTY.ordinal()] = 5;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    public PolymorphicTypeValidator subTypeValidator(MapperConfig<?> mapperConfig) {
        return mapperConfig.getPolymorphicTypeValidator();
    }

    protected PolymorphicTypeValidator verifyBaseTypeValidity(MapperConfig<?> mapperConfig, JavaType javaType) {
        PolymorphicTypeValidator polymorphicTypeValidatorSubTypeValidator = subTypeValidator(mapperConfig);
        if (this._idType == JsonTypeInfo.EnumC1422Id.CLASS || this._idType == JsonTypeInfo.EnumC1422Id.MINIMAL_CLASS) {
            PolymorphicTypeValidator.Validity validityValidateBaseType = polymorphicTypeValidatorSubTypeValidator.validateBaseType(mapperConfig, javaType);
            if (validityValidateBaseType == PolymorphicTypeValidator.Validity.DENIED) {
                return reportInvalidBaseType(mapperConfig, javaType, polymorphicTypeValidatorSubTypeValidator);
            }
            if (validityValidateBaseType == PolymorphicTypeValidator.Validity.ALLOWED) {
                return LaissezFaireSubTypeValidator.instance;
            }
        }
        return polymorphicTypeValidatorSubTypeValidator;
    }

    protected PolymorphicTypeValidator reportInvalidBaseType(MapperConfig<?> mapperConfig, JavaType javaType, PolymorphicTypeValidator polymorphicTypeValidator) {
        throw new IllegalArgumentException(String.format("Configured `PolymorphicTypeValidator` (of type %s) denied resolution of all subtypes of base type %s", ClassUtil.classNameOf(polymorphicTypeValidator), ClassUtil.classNameOf(javaType.getRawClass())));
    }

    protected boolean _strictTypeIdHandling(DeserializationConfig deserializationConfig, JavaType javaType) {
        if (this._requireTypeIdForSubtypes != null && javaType.isConcrete()) {
            return this._requireTypeIdForSubtypes.booleanValue();
        }
        if (deserializationConfig.isEnabled(MapperFeature.REQUIRE_TYPE_ID_FOR_SUBTYPES)) {
            return true;
        }
        return _hasTypeResolver(deserializationConfig, javaType);
    }

    protected boolean _hasTypeResolver(DeserializationConfig deserializationConfig, JavaType javaType) {
        return deserializationConfig.getAnnotationIntrospector().findPolymorphicTypeInfo(deserializationConfig, AnnotatedClassResolver.resolveWithoutSuperTypes(deserializationConfig, javaType.getRawClass())) != null;
    }
}
