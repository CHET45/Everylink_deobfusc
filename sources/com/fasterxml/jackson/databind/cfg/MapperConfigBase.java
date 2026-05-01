package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.ConfigFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MapperConfigBase<CFG extends ConfigFeature, T extends MapperConfigBase<CFG, T>> extends MapperConfig<T> implements Serializable {
    protected final ContextAttributes _attributes;
    protected final ConfigOverrides _configOverrides;
    protected final DatatypeFeatures _datatypeFeatures;
    protected final SimpleMixInResolver _mixIns;
    protected final PropertyName _rootName;
    protected final RootNameLookup _rootNames;
    protected final SubtypeResolver _subtypeResolver;
    protected final Class<?> _view;
    protected static final ConfigOverride EMPTY_OVERRIDE = ConfigOverride.empty();
    private static final long DEFAULT_MAPPER_FEATURES = MapperFeature.collectLongDefaults();
    private static final long AUTO_DETECT_MASK = (((MapperFeature.AUTO_DETECT_FIELDS.getLongMask() | MapperFeature.AUTO_DETECT_GETTERS.getLongMask()) | MapperFeature.AUTO_DETECT_IS_GETTERS.getLongMask()) | MapperFeature.AUTO_DETECT_SETTERS.getLongMask()) | MapperFeature.AUTO_DETECT_CREATORS.getLongMask();

    protected abstract T _with(DatatypeFeatures datatypeFeatures);

    protected abstract T _withBase(BaseSettings baseSettings);

    protected abstract T _withMapperFeatures(long j);

    public abstract T with(ContextAttributes contextAttributes);

    public abstract T with(SubtypeResolver subtypeResolver);

    public abstract T withRootName(PropertyName propertyName);

    public abstract T withView(Class<?> cls);

    protected MapperConfigBase(BaseSettings baseSettings, SubtypeResolver subtypeResolver, SimpleMixInResolver simpleMixInResolver, RootNameLookup rootNameLookup, ConfigOverrides configOverrides, DatatypeFeatures datatypeFeatures) {
        super(baseSettings, DEFAULT_MAPPER_FEATURES);
        this._mixIns = simpleMixInResolver;
        this._subtypeResolver = subtypeResolver;
        this._rootNames = rootNameLookup;
        this._rootName = null;
        this._view = null;
        this._attributes = ContextAttributes.getEmpty();
        this._configOverrides = configOverrides;
        this._datatypeFeatures = datatypeFeatures;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, SubtypeResolver subtypeResolver, SimpleMixInResolver simpleMixInResolver, RootNameLookup rootNameLookup, ConfigOverrides configOverrides) {
        super(mapperConfigBase, mapperConfigBase._base.copy());
        this._mixIns = simpleMixInResolver;
        this._subtypeResolver = subtypeResolver;
        this._rootNames = rootNameLookup;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = configOverrides;
        this._datatypeFeatures = mapperConfigBase._datatypeFeatures;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase) {
        super(mapperConfigBase);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
        this._datatypeFeatures = mapperConfigBase._datatypeFeatures;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, BaseSettings baseSettings) {
        super(mapperConfigBase, baseSettings);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
        this._datatypeFeatures = mapperConfigBase._datatypeFeatures;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, long j) {
        super(mapperConfigBase, j);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
        this._datatypeFeatures = mapperConfigBase._datatypeFeatures;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, SubtypeResolver subtypeResolver) {
        super(mapperConfigBase);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
        this._datatypeFeatures = mapperConfigBase._datatypeFeatures;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, PropertyName propertyName) {
        super(mapperConfigBase);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = propertyName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
        this._datatypeFeatures = mapperConfigBase._datatypeFeatures;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, Class<?> cls) {
        super(mapperConfigBase);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = cls;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
        this._datatypeFeatures = mapperConfigBase._datatypeFeatures;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, SimpleMixInResolver simpleMixInResolver) {
        super(mapperConfigBase);
        this._mixIns = simpleMixInResolver;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
        this._datatypeFeatures = mapperConfigBase._datatypeFeatures;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, ContextAttributes contextAttributes) {
        super(mapperConfigBase);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = contextAttributes;
        this._configOverrides = mapperConfigBase._configOverrides;
        this._datatypeFeatures = mapperConfigBase._datatypeFeatures;
    }

    protected MapperConfigBase(MapperConfigBase<CFG, T> mapperConfigBase, DatatypeFeatures datatypeFeatures) {
        super(mapperConfigBase);
        this._mixIns = mapperConfigBase._mixIns;
        this._subtypeResolver = mapperConfigBase._subtypeResolver;
        this._rootNames = mapperConfigBase._rootNames;
        this._rootName = mapperConfigBase._rootName;
        this._view = mapperConfigBase._view;
        this._attributes = mapperConfigBase._attributes;
        this._configOverrides = mapperConfigBase._configOverrides;
        this._datatypeFeatures = datatypeFeatures;
    }

    protected DatatypeFeatures _datatypeFeatures() {
        return this._datatypeFeatures;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final T with(MapperFeature... mapperFeatureArr) {
        long longMask = this._mapperFeatures;
        for (MapperFeature mapperFeature : mapperFeatureArr) {
            longMask |= mapperFeature.getLongMask();
        }
        return longMask == this._mapperFeatures ? this : (T) _withMapperFeatures(longMask);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final T without(MapperFeature... mapperFeatureArr) {
        long j = this._mapperFeatures;
        for (MapperFeature mapperFeature : mapperFeatureArr) {
            j &= ~mapperFeature.getLongMask();
        }
        return j == this._mapperFeatures ? this : (T) _withMapperFeatures(j);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final T with(MapperFeature mapperFeature, boolean z) {
        long longMask;
        if (z) {
            longMask = mapperFeature.getLongMask() | this._mapperFeatures;
        } else {
            longMask = (~mapperFeature.getLongMask()) & this._mapperFeatures;
        }
        return longMask == this._mapperFeatures ? this : (T) _withMapperFeatures(longMask);
    }

    public final T with(DatatypeFeature datatypeFeature) {
        return (T) _with(_datatypeFeatures().with(datatypeFeature));
    }

    public final T withFeatures(DatatypeFeature... datatypeFeatureArr) {
        return (T) _with(_datatypeFeatures().withFeatures(datatypeFeatureArr));
    }

    public final T without(DatatypeFeature datatypeFeature) {
        return (T) _with(_datatypeFeatures().without(datatypeFeature));
    }

    public final T withoutFeatures(DatatypeFeature... datatypeFeatureArr) {
        return (T) _with(_datatypeFeatures().withoutFeatures(datatypeFeatureArr));
    }

    public final T with(DatatypeFeature datatypeFeature, boolean z) {
        DatatypeFeatures datatypeFeatures_datatypeFeatures = _datatypeFeatures();
        return (T) _with(z ? datatypeFeatures_datatypeFeatures.with(datatypeFeature) : datatypeFeatures_datatypeFeatures.without(datatypeFeature));
    }

    public final T with(AnnotationIntrospector annotationIntrospector) {
        return (T) _withBase(this._base.withAnnotationIntrospector(annotationIntrospector));
    }

    public final T withAppendedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return (T) _withBase(this._base.withAppendedAnnotationIntrospector(annotationIntrospector));
    }

    public final T withInsertedAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        return (T) _withBase(this._base.withInsertedAnnotationIntrospector(annotationIntrospector));
    }

    public final T with(ClassIntrospector classIntrospector) {
        return (T) _withBase(this._base.withClassIntrospector(classIntrospector));
    }

    public T withAttributes(Map<?, ?> map) {
        return (T) with(getAttributes().withSharedAttributes(map));
    }

    public T withAttribute(Object obj, Object obj2) {
        return (T) with(getAttributes().withSharedAttribute(obj, obj2));
    }

    public T withoutAttribute(Object obj) {
        return (T) with(getAttributes().withoutSharedAttribute(obj));
    }

    public final T with(TypeFactory typeFactory) {
        return (T) _withBase(this._base.withTypeFactory(typeFactory));
    }

    public final T with(TypeResolverBuilder<?> typeResolverBuilder) {
        return (T) _withBase(this._base.withTypeResolverBuilder(typeResolverBuilder));
    }

    public final T with(PropertyNamingStrategy propertyNamingStrategy) {
        return (T) _withBase(this._base.withPropertyNamingStrategy(propertyNamingStrategy));
    }

    public final T with(AccessorNamingStrategy.Provider provider) {
        return (T) _withBase(this._base.withAccessorNaming(provider));
    }

    public final T with(HandlerInstantiator handlerInstantiator) {
        return (T) _withBase(this._base.withHandlerInstantiator(handlerInstantiator));
    }

    public T with(CacheProvider cacheProvider) {
        return (T) _withBase(this._base.with((CacheProvider) Objects.requireNonNull(cacheProvider)));
    }

    public final T with(Base64Variant base64Variant) {
        return (T) _withBase(this._base.with(base64Variant));
    }

    public T with(DateFormat dateFormat) {
        return (T) _withBase(this._base.withDateFormat(dateFormat));
    }

    public final T with(Locale locale) {
        return (T) _withBase(this._base.with(locale));
    }

    public final T with(TimeZone timeZone) {
        return (T) _withBase(this._base.with(timeZone));
    }

    public T withRootName(String str) {
        if (str == null) {
            return (T) withRootName((PropertyName) null);
        }
        return (T) withRootName(PropertyName.construct(str));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final DatatypeFeatures getDatatypeFeatures() {
        return this._datatypeFeatures;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final SubtypeResolver getSubtypeResolver() {
        return this._subtypeResolver;
    }

    @Deprecated
    public final String getRootName() {
        PropertyName propertyName = this._rootName;
        if (propertyName == null) {
            return null;
        }
        return propertyName.getSimpleName();
    }

    public final PropertyName getFullRootName() {
        return this._rootName;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final Class<?> getActiveView() {
        return this._view;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final ContextAttributes getAttributes() {
        return this._attributes;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final ConfigOverride getConfigOverride(Class<?> cls) {
        ConfigOverride configOverrideFindOverride = this._configOverrides.findOverride(cls);
        return configOverrideFindOverride == null ? EMPTY_OVERRIDE : configOverrideFindOverride;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final ConfigOverride findConfigOverride(Class<?> cls) {
        return this._configOverrides.findOverride(cls);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final JsonInclude.Value getDefaultPropertyInclusion() {
        return this._configOverrides.getDefaultInclusion();
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final JsonInclude.Value getDefaultPropertyInclusion(Class<?> cls) {
        JsonInclude.Value include = getConfigOverride(cls).getInclude();
        JsonInclude.Value defaultPropertyInclusion = getDefaultPropertyInclusion();
        return defaultPropertyInclusion == null ? include : defaultPropertyInclusion.withOverrides(include);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final JsonInclude.Value getDefaultInclusion(Class<?> cls, Class<?> cls2) {
        JsonInclude.Value includeAsProperty = getConfigOverride(cls2).getIncludeAsProperty();
        JsonInclude.Value defaultPropertyInclusion = getDefaultPropertyInclusion(cls);
        return defaultPropertyInclusion == null ? includeAsProperty : defaultPropertyInclusion.withOverrides(includeAsProperty);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final JsonFormat.Value getDefaultPropertyFormat(Class<?> cls) {
        return this._configOverrides.findFormatDefaults(cls);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final JsonIgnoreProperties.Value getDefaultPropertyIgnorals(Class<?> cls) {
        JsonIgnoreProperties.Value ignorals;
        ConfigOverride configOverrideFindOverride = this._configOverrides.findOverride(cls);
        if (configOverrideFindOverride == null || (ignorals = configOverrideFindOverride.getIgnorals()) == null) {
            return null;
        }
        return ignorals;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final JsonIgnoreProperties.Value getDefaultPropertyIgnorals(Class<?> cls, AnnotatedClass annotatedClass) {
        AnnotationIntrospector annotationIntrospector = getAnnotationIntrospector();
        return JsonIgnoreProperties.Value.merge(annotationIntrospector == null ? null : annotationIntrospector.findPropertyIgnoralByName(this, annotatedClass), getDefaultPropertyIgnorals(cls));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final JsonIncludeProperties.Value getDefaultPropertyInclusions(Class<?> cls, AnnotatedClass annotatedClass) {
        AnnotationIntrospector annotationIntrospector = getAnnotationIntrospector();
        if (annotationIntrospector == null) {
            return null;
        }
        return annotationIntrospector.findPropertyInclusionByName(this, annotatedClass);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final VisibilityChecker<?> getDefaultVisibilityChecker() {
        VisibilityChecker<?> defaultVisibility = this._configOverrides.getDefaultVisibility();
        long j = this._mapperFeatures;
        long j2 = AUTO_DETECT_MASK;
        if ((j & j2) == j2) {
            return defaultVisibility;
        }
        if (!isEnabled(MapperFeature.AUTO_DETECT_FIELDS)) {
            defaultVisibility = defaultVisibility.withFieldVisibility(JsonAutoDetect.Visibility.NONE);
        }
        if (!isEnabled(MapperFeature.AUTO_DETECT_GETTERS)) {
            defaultVisibility = defaultVisibility.withGetterVisibility(JsonAutoDetect.Visibility.NONE);
        }
        if (!isEnabled(MapperFeature.AUTO_DETECT_IS_GETTERS)) {
            defaultVisibility = defaultVisibility.withIsGetterVisibility(JsonAutoDetect.Visibility.NONE);
        }
        if (!isEnabled(MapperFeature.AUTO_DETECT_SETTERS)) {
            defaultVisibility = defaultVisibility.withSetterVisibility(JsonAutoDetect.Visibility.NONE);
        }
        return !isEnabled(MapperFeature.AUTO_DETECT_CREATORS) ? defaultVisibility.withCreatorVisibility(JsonAutoDetect.Visibility.NONE) : defaultVisibility;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final VisibilityChecker<?> getDefaultVisibilityChecker(Class<?> cls, AnnotatedClass annotatedClass) {
        VisibilityChecker<?> defaultVisibilityChecker;
        if (ClassUtil.isJDKClass(cls)) {
            defaultVisibilityChecker = VisibilityChecker.Std.allPublicInstance();
        } else {
            defaultVisibilityChecker = getDefaultVisibilityChecker();
            if (ClassUtil.isRecordType(cls) && isEnabled(MapperFeature.AUTO_DETECT_CREATORS)) {
                defaultVisibilityChecker = defaultVisibilityChecker.withCreatorVisibility(JsonAutoDetect.Visibility.DEFAULT);
            }
        }
        AnnotationIntrospector annotationIntrospector = getAnnotationIntrospector();
        if (annotationIntrospector != null) {
            defaultVisibilityChecker = annotationIntrospector.findAutoDetectVisibility(annotatedClass, defaultVisibilityChecker);
        }
        ConfigOverride configOverrideFindOverride = this._configOverrides.findOverride(cls);
        return configOverrideFindOverride != null ? defaultVisibilityChecker.withOverrides(configOverrideFindOverride.getVisibility()) : defaultVisibilityChecker;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public final JsonSetter.Value getDefaultSetterInfo() {
        return this._configOverrides.getDefaultSetterInfo();
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public Boolean getDefaultMergeable() {
        return this._configOverrides.getDefaultMergeable();
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public Boolean getDefaultMergeable(Class<?> cls) {
        Boolean mergeable;
        ConfigOverride configOverrideFindOverride = this._configOverrides.findOverride(cls);
        return (configOverrideFindOverride == null || (mergeable = configOverrideFindOverride.getMergeable()) == null) ? this._configOverrides.getDefaultMergeable() : mergeable;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public PropertyName findRootName(JavaType javaType) {
        PropertyName propertyName = this._rootName;
        return propertyName != null ? propertyName : this._rootNames.findRootName(javaType, this);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public PropertyName findRootName(Class<?> cls) {
        PropertyName propertyName = this._rootName;
        return propertyName != null ? propertyName : this._rootNames.findRootName(cls, this);
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector.MixInResolver
    public final Class<?> findMixInClassFor(Class<?> cls) {
        return this._mixIns.findMixInClassFor(cls);
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector.MixInResolver
    public ClassIntrospector.MixInResolver copy() {
        throw new UnsupportedOperationException();
    }

    public final int mixInCount() {
        return this._mixIns.localSize();
    }
}
