package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.IgnorePropertiesUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class BeanDeserializerBuilder {
    protected SettableAnyProperty _anySetter;
    protected HashMap<String, SettableBeanProperty> _backRefProperties;
    protected final BeanDescription _beanDesc;
    protected AnnotatedMethod _buildMethod;
    protected JsonPOJOBuilder.Value _builderConfig;
    protected final DeserializationConfig _config;
    protected final DeserializationContext _context;
    protected HashSet<String> _ignorableProps;
    protected boolean _ignoreAllUnknown;
    protected HashSet<String> _includableProps;
    protected List<ValueInjector> _injectables;
    protected ObjectIdReader _objectIdReader;
    protected final Map<String, SettableBeanProperty> _properties;
    protected SettableBeanProperty[] _propsBasedCreatorParams;
    protected ValueInstantiator _valueInstantiator;

    public BeanDeserializerBuilder(BeanDescription beanDescription, DeserializationContext deserializationContext) {
        this._properties = new LinkedHashMap();
        this._beanDesc = beanDescription;
        this._context = deserializationContext;
        this._config = deserializationContext.getConfig();
    }

    protected BeanDeserializerBuilder(BeanDeserializerBuilder beanDeserializerBuilder) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this._properties = linkedHashMap;
        this._beanDesc = beanDeserializerBuilder._beanDesc;
        this._context = beanDeserializerBuilder._context;
        this._config = beanDeserializerBuilder._config;
        linkedHashMap.putAll(beanDeserializerBuilder._properties);
        this._injectables = _copy(beanDeserializerBuilder._injectables);
        this._backRefProperties = _copy(beanDeserializerBuilder._backRefProperties);
        this._ignorableProps = beanDeserializerBuilder._ignorableProps;
        this._includableProps = beanDeserializerBuilder._includableProps;
        this._valueInstantiator = beanDeserializerBuilder._valueInstantiator;
        this._propsBasedCreatorParams = beanDeserializerBuilder._propsBasedCreatorParams;
        this._objectIdReader = beanDeserializerBuilder._objectIdReader;
        this._anySetter = beanDeserializerBuilder._anySetter;
        this._ignoreAllUnknown = beanDeserializerBuilder._ignoreAllUnknown;
        this._buildMethod = beanDeserializerBuilder._buildMethod;
        this._builderConfig = beanDeserializerBuilder._builderConfig;
    }

    private static HashMap<String, SettableBeanProperty> _copy(HashMap<String, SettableBeanProperty> map) {
        if (map == null) {
            return null;
        }
        return new HashMap<>(map);
    }

    private static <T> List<T> _copy(List<T> list) {
        if (list == null) {
            return null;
        }
        return new ArrayList(list);
    }

    public void addOrReplaceProperty(SettableBeanProperty settableBeanProperty, boolean z) {
        SettableBeanProperty[] settableBeanPropertyArr;
        SettableBeanProperty settableBeanPropertyPut = this._properties.put(settableBeanProperty.getName(), settableBeanProperty);
        if (settableBeanPropertyPut == null || (settableBeanPropertyArr = this._propsBasedCreatorParams) == null) {
            return;
        }
        int length = settableBeanPropertyArr.length;
        for (int i = 0; i < length; i++) {
            SettableBeanProperty[] settableBeanPropertyArr2 = this._propsBasedCreatorParams;
            if (settableBeanPropertyArr2[i] == settableBeanPropertyPut) {
                settableBeanPropertyArr2[i] = settableBeanProperty;
            }
        }
    }

    public void addProperty(SettableBeanProperty settableBeanProperty) {
        SettableBeanProperty settableBeanPropertyPut = this._properties.put(settableBeanProperty.getName(), settableBeanProperty);
        if (settableBeanPropertyPut != null && settableBeanPropertyPut != settableBeanProperty) {
            throw new IllegalArgumentException("Duplicate property '" + settableBeanProperty.getName() + "' for " + this._beanDesc.getType());
        }
    }

    public void addBackReferenceProperty(String str, SettableBeanProperty settableBeanProperty) throws DatabindException {
        if (this._backRefProperties == null) {
            this._backRefProperties = new HashMap<>(4);
        }
        if (this._config.canOverrideAccessModifiers()) {
            try {
                settableBeanProperty.fixAccess(this._config);
            } catch (IllegalArgumentException e) {
                _handleBadAccess(e);
            }
        }
        this._backRefProperties.put(str, settableBeanProperty);
    }

    public void addInjectable(PropertyName propertyName, JavaType javaType, Annotations annotations, AnnotatedMember annotatedMember, Object obj) throws DatabindException {
        if (this._injectables == null) {
            this._injectables = new ArrayList();
        }
        if (this._config.canOverrideAccessModifiers()) {
            try {
                annotatedMember.fixAccess(this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
            } catch (IllegalArgumentException e) {
                _handleBadAccess(e);
            }
        }
        this._injectables.add(new ValueInjector(propertyName, javaType, annotatedMember, obj));
    }

    public void addIgnorable(String str) {
        if (this._ignorableProps == null) {
            this._ignorableProps = new HashSet<>();
        }
        this._ignorableProps.add(str);
    }

    public void addIncludable(String str) {
        if (this._includableProps == null) {
            this._includableProps = new HashSet<>();
        }
        this._includableProps.add(str);
    }

    public void addCreatorProperty(SettableBeanProperty settableBeanProperty) {
        addProperty(settableBeanProperty);
    }

    public void setAnySetter(SettableAnyProperty settableAnyProperty) {
        if (this._anySetter != null && settableAnyProperty != null) {
            throw new IllegalStateException("_anySetter already set to non-null");
        }
        this._anySetter = settableAnyProperty;
    }

    public void setIgnoreUnknownProperties(boolean z) {
        this._ignoreAllUnknown = z;
    }

    public void setValueInstantiator(ValueInstantiator valueInstantiator) {
        this._valueInstantiator = valueInstantiator;
        this._propsBasedCreatorParams = valueInstantiator.getFromObjectArguments(this._config);
    }

    public void setObjectIdReader(ObjectIdReader objectIdReader) {
        this._objectIdReader = objectIdReader;
    }

    public void setPOJOBuilder(AnnotatedMethod annotatedMethod, JsonPOJOBuilder.Value value) {
        this._buildMethod = annotatedMethod;
        this._builderConfig = value;
    }

    public Iterator<SettableBeanProperty> getProperties() {
        return this._properties.values().iterator();
    }

    public SettableBeanProperty findProperty(PropertyName propertyName) {
        return this._properties.get(propertyName.getSimpleName());
    }

    public boolean hasProperty(PropertyName propertyName) {
        return findProperty(propertyName) != null;
    }

    public SettableBeanProperty removeProperty(PropertyName propertyName) {
        return this._properties.remove(propertyName.getSimpleName());
    }

    public SettableAnyProperty getAnySetter() {
        return this._anySetter;
    }

    public ValueInstantiator getValueInstantiator() {
        return this._valueInstantiator;
    }

    public List<ValueInjector> getInjectables() {
        return this._injectables;
    }

    public ObjectIdReader getObjectIdReader() {
        return this._objectIdReader;
    }

    public AnnotatedMethod getBuildMethod() {
        return this._buildMethod;
    }

    public JsonPOJOBuilder.Value getBuilderConfig() {
        return this._builderConfig;
    }

    public boolean hasIgnorable(String str) {
        return IgnorePropertiesUtil.shouldIgnore(str, this._ignorableProps, this._includableProps);
    }

    public JsonDeserializer<?> build() throws DatabindException {
        Collection<SettableBeanProperty> collectionValues = this._properties.values();
        _fixAccess(collectionValues);
        BeanPropertyMap beanPropertyMapConstruct = BeanPropertyMap.construct(this._config, collectionValues, _collectAliases(collectionValues), _findCaseInsensitivity());
        beanPropertyMapConstruct.assignIndexes();
        boolean zIsEnabled = this._config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
        boolean z = !zIsEnabled;
        if (zIsEnabled) {
            Iterator<SettableBeanProperty> it = collectionValues.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (it.next().hasViews()) {
                    z = true;
                    break;
                }
            }
        }
        boolean z2 = z;
        if (this._objectIdReader != null) {
            beanPropertyMapConstruct = beanPropertyMapConstruct.withProperty(new ObjectIdValueProperty(this._objectIdReader, PropertyMetadata.STD_REQUIRED));
        }
        return new BeanDeserializer(this, this._beanDesc, beanPropertyMapConstruct, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, this._includableProps, z2);
    }

    public AbstractDeserializer buildAbstract() {
        return new AbstractDeserializer(this, this._beanDesc, this._backRefProperties, this._properties);
    }

    public JsonDeserializer<?> buildBuilderBased(JavaType javaType, String str) throws DatabindException {
        AnnotatedMethod annotatedMethod = this._buildMethod;
        if (annotatedMethod == null) {
            if (!str.isEmpty()) {
                this._context.reportBadDefinition(this._beanDesc.getType(), String.format("Builder class %s does not have build method (name: '%s')", ClassUtil.getTypeDescription(this._beanDesc.getType()), str));
            }
        } else {
            Class<?> rawReturnType = annotatedMethod.getRawReturnType();
            Class<?> rawClass = javaType.getRawClass();
            if (rawReturnType != rawClass && !rawReturnType.isAssignableFrom(rawClass) && !rawClass.isAssignableFrom(rawReturnType)) {
                this._context.reportBadDefinition(this._beanDesc.getType(), String.format("Build method `%s` has wrong return type (%s), not compatible with POJO type (%s)", this._buildMethod.getFullName(), ClassUtil.getClassDescription(rawReturnType), ClassUtil.getTypeDescription(javaType)));
            }
        }
        Collection<SettableBeanProperty> collectionValues = this._properties.values();
        _fixAccess(collectionValues);
        BeanPropertyMap beanPropertyMapConstruct = BeanPropertyMap.construct(this._config, collectionValues, _collectAliases(collectionValues), _findCaseInsensitivity());
        beanPropertyMapConstruct.assignIndexes();
        boolean zIsEnabled = this._config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
        boolean z = !zIsEnabled;
        if (zIsEnabled) {
            Iterator<SettableBeanProperty> it = collectionValues.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (it.next().hasViews()) {
                    z = true;
                    break;
                }
            }
        }
        if (this._objectIdReader != null) {
            beanPropertyMapConstruct = beanPropertyMapConstruct.withProperty(new ObjectIdValueProperty(this._objectIdReader, PropertyMetadata.STD_REQUIRED));
        }
        return createBuilderBasedDeserializer(javaType, beanPropertyMapConstruct, z);
    }

    protected JsonDeserializer<?> createBuilderBasedDeserializer(JavaType javaType, BeanPropertyMap beanPropertyMap, boolean z) {
        return new BuilderBasedDeserializer(this, this._beanDesc, javaType, beanPropertyMap, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, this._includableProps, z);
    }

    protected void _fixAccess(Collection<SettableBeanProperty> collection) throws DatabindException {
        if (this._config.canOverrideAccessModifiers()) {
            Iterator<SettableBeanProperty> it = collection.iterator();
            while (it.hasNext()) {
                try {
                    it.next().fixAccess(this._config);
                } catch (IllegalArgumentException e) {
                    _handleBadAccess(e);
                }
            }
        }
        SettableAnyProperty settableAnyProperty = this._anySetter;
        if (settableAnyProperty != null) {
            try {
                settableAnyProperty.fixAccess(this._config);
            } catch (IllegalArgumentException e2) {
                _handleBadAccess(e2);
            }
        }
        AnnotatedMethod annotatedMethod = this._buildMethod;
        if (annotatedMethod != null) {
            try {
                annotatedMethod.fixAccess(this._config.isEnabled(MapperFeature.OVERRIDE_PUBLIC_ACCESS_MODIFIERS));
            } catch (IllegalArgumentException e3) {
                _handleBadAccess(e3);
            }
        }
    }

    protected Map<String, List<PropertyName>> _collectAliases(Collection<SettableBeanProperty> collection) {
        HashMap map = null;
        for (SettableBeanProperty settableBeanProperty : collection) {
            List<PropertyName> listFindAliases = settableBeanProperty.findAliases(this._config);
            if (listFindAliases != null && !listFindAliases.isEmpty()) {
                if (map == null) {
                    map = new HashMap();
                }
                map.put(settableBeanProperty.getName(), listFindAliases);
            }
        }
        return map == null ? Collections.emptyMap() : map;
    }

    protected boolean _findCaseInsensitivity() {
        Boolean feature = this._beanDesc.findExpectedFormat().getFeature(JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        if (feature == null) {
            return this._config.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        }
        return feature.booleanValue();
    }

    protected void _handleBadAccess(IllegalArgumentException illegalArgumentException) throws DatabindException {
        try {
            this._context.reportBadTypeDefinition(this._beanDesc, illegalArgumentException.getMessage(), new Object[0]);
        } catch (DatabindException e) {
            if (e.getCause() == null) {
                e.initCause(illegalArgumentException);
            }
            throw e;
        }
    }
}
