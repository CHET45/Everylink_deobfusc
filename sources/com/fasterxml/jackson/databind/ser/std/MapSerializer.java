package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.IgnorePropertiesUtil;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes3.dex */
@JacksonStdImpl
public class MapSerializer extends ContainerSerializer<Map<?, ?>> implements ContextualSerializer {
    private static final long serialVersionUID = 1;
    protected PropertySerializerMap _dynamicValueSerializers;
    protected final Object _filterId;
    protected final Set<String> _ignoredEntries;
    protected final Set<String> _includedEntries;
    protected final IgnorePropertiesUtil.Checker _inclusionChecker;
    protected JsonSerializer<Object> _keySerializer;
    protected final JavaType _keyType;
    protected final BeanProperty _property;
    protected final boolean _sortKeys;
    protected final boolean _suppressNulls;
    protected final Object _suppressableValue;
    protected JsonSerializer<Object> _valueSerializer;
    protected final JavaType _valueType;
    protected final boolean _valueTypeIsStatic;
    protected final TypeSerializer _valueTypeSerializer;
    protected static final JavaType UNSPECIFIED_TYPE = TypeFactory.unknownType();
    public static final Object MARKER_FOR_EMPTY = JsonInclude.Include.NON_EMPTY;

    protected MapSerializer(Set<String> set, Set<String> set2, JavaType javaType, JavaType javaType2, boolean z, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2) {
        super(Map.class, false);
        set = (set == null || set.isEmpty()) ? null : set;
        this._ignoredEntries = set;
        this._includedEntries = set2;
        this._keyType = javaType;
        this._valueType = javaType2;
        this._valueTypeIsStatic = z;
        this._valueTypeSerializer = typeSerializer;
        this._keySerializer = jsonSerializer;
        this._valueSerializer = jsonSerializer2;
        this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
        this._property = null;
        this._filterId = null;
        this._sortKeys = false;
        this._suppressableValue = null;
        this._suppressNulls = false;
        this._inclusionChecker = IgnorePropertiesUtil.buildCheckerIfNeeded(set, set2);
    }

    @Deprecated
    protected MapSerializer(Set<String> set, JavaType javaType, JavaType javaType2, boolean z, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2) {
        this(set, null, javaType, javaType2, z, typeSerializer, jsonSerializer, jsonSerializer2);
    }

    protected MapSerializer(MapSerializer mapSerializer, BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2, Set<String> set, Set<String> set2) {
        super(Map.class, false);
        set = (set == null || set.isEmpty()) ? null : set;
        this._ignoredEntries = set;
        this._includedEntries = set2;
        this._keyType = mapSerializer._keyType;
        this._valueType = mapSerializer._valueType;
        this._valueTypeIsStatic = mapSerializer._valueTypeIsStatic;
        this._valueTypeSerializer = mapSerializer._valueTypeSerializer;
        this._keySerializer = jsonSerializer;
        this._valueSerializer = jsonSerializer2;
        this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
        this._property = beanProperty;
        this._filterId = mapSerializer._filterId;
        this._sortKeys = mapSerializer._sortKeys;
        this._suppressableValue = mapSerializer._suppressableValue;
        this._suppressNulls = mapSerializer._suppressNulls;
        this._inclusionChecker = IgnorePropertiesUtil.buildCheckerIfNeeded(set, set2);
    }

    @Deprecated
    protected MapSerializer(MapSerializer mapSerializer, BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2, Set<String> set) {
        this(mapSerializer, beanProperty, jsonSerializer, jsonSerializer2, set, null);
    }

    protected MapSerializer(MapSerializer mapSerializer, TypeSerializer typeSerializer, Object obj, boolean z) {
        super(Map.class, false);
        this._ignoredEntries = mapSerializer._ignoredEntries;
        this._includedEntries = mapSerializer._includedEntries;
        this._keyType = mapSerializer._keyType;
        this._valueType = mapSerializer._valueType;
        this._valueTypeIsStatic = mapSerializer._valueTypeIsStatic;
        this._valueTypeSerializer = typeSerializer;
        this._keySerializer = mapSerializer._keySerializer;
        this._valueSerializer = mapSerializer._valueSerializer;
        this._dynamicValueSerializers = mapSerializer._dynamicValueSerializers;
        this._property = mapSerializer._property;
        this._filterId = mapSerializer._filterId;
        this._sortKeys = mapSerializer._sortKeys;
        this._suppressableValue = obj;
        this._suppressNulls = z;
        this._inclusionChecker = mapSerializer._inclusionChecker;
    }

    protected MapSerializer(MapSerializer mapSerializer, Object obj, boolean z) {
        super(Map.class, false);
        this._ignoredEntries = mapSerializer._ignoredEntries;
        this._includedEntries = mapSerializer._includedEntries;
        this._keyType = mapSerializer._keyType;
        this._valueType = mapSerializer._valueType;
        this._valueTypeIsStatic = mapSerializer._valueTypeIsStatic;
        this._valueTypeSerializer = mapSerializer._valueTypeSerializer;
        this._keySerializer = mapSerializer._keySerializer;
        this._valueSerializer = mapSerializer._valueSerializer;
        this._dynamicValueSerializers = PropertySerializerMap.emptyForProperties();
        this._property = mapSerializer._property;
        this._filterId = obj;
        this._sortKeys = z;
        this._suppressableValue = mapSerializer._suppressableValue;
        this._suppressNulls = mapSerializer._suppressNulls;
        this._inclusionChecker = mapSerializer._inclusionChecker;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public MapSerializer _withValueTypeSerializer(TypeSerializer typeSerializer) {
        if (this._valueTypeSerializer == typeSerializer) {
            return this;
        }
        _ensureOverride("_withValueTypeSerializer");
        return new MapSerializer(this, typeSerializer, this._suppressableValue, this._suppressNulls);
    }

    public MapSerializer withResolved(BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2, Set<String> set, Set<String> set2, boolean z) {
        _ensureOverride("withResolved");
        MapSerializer mapSerializer = new MapSerializer(this, beanProperty, jsonSerializer, jsonSerializer2, set, set2);
        return z != mapSerializer._sortKeys ? new MapSerializer(mapSerializer, this._filterId, z) : mapSerializer;
    }

    public MapSerializer withResolved(BeanProperty beanProperty, JsonSerializer<?> jsonSerializer, JsonSerializer<?> jsonSerializer2, Set<String> set, boolean z) {
        return withResolved(beanProperty, jsonSerializer, jsonSerializer2, set, null, z);
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public MapSerializer withFilterId(Object obj) {
        if (this._filterId == obj) {
            return this;
        }
        _ensureOverride("withFilterId");
        return new MapSerializer(this, obj, this._sortKeys);
    }

    public MapSerializer withContentInclusion(Object obj, boolean z) {
        if (obj == this._suppressableValue && z == this._suppressNulls) {
            return this;
        }
        _ensureOverride("withContentInclusion");
        return new MapSerializer(this, this._valueTypeSerializer, obj, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.fasterxml.jackson.databind.ser.std.MapSerializer construct(java.util.Set<java.lang.String> r12, java.util.Set<java.lang.String> r13, com.fasterxml.jackson.databind.JavaType r14, boolean r15, com.fasterxml.jackson.databind.jsontype.TypeSerializer r16, com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r17, com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r18, java.lang.Object r19) {
        /*
            r0 = r14
            r1 = r19
            if (r0 != 0) goto La
            com.fasterxml.jackson.databind.JavaType r0 = com.fasterxml.jackson.databind.ser.std.MapSerializer.UNSPECIFIED_TYPE
            r6 = r0
            r7 = r6
            goto L21
        La:
            com.fasterxml.jackson.databind.JavaType r2 = r14.getKeyType()
            java.lang.Class<java.util.Properties> r3 = java.util.Properties.class
            boolean r3 = r14.hasRawClass(r3)
            if (r3 == 0) goto L1b
            com.fasterxml.jackson.databind.JavaType r0 = com.fasterxml.jackson.databind.type.TypeFactory.unknownType()
            goto L1f
        L1b:
            com.fasterxml.jackson.databind.JavaType r0 = r14.getContentType()
        L1f:
            r7 = r0
            r6 = r2
        L21:
            r0 = 0
            if (r15 != 0) goto L2e
            if (r7 == 0) goto L36
            boolean r2 = r7.isFinal()
            if (r2 == 0) goto L36
            r0 = 1
            goto L36
        L2e:
            java.lang.Class r2 = r7.getRawClass()
            java.lang.Class<java.lang.Object> r3 = java.lang.Object.class
            if (r2 != r3) goto L38
        L36:
            r8 = r0
            goto L39
        L38:
            r8 = r15
        L39:
            com.fasterxml.jackson.databind.ser.std.MapSerializer r0 = new com.fasterxml.jackson.databind.ser.std.MapSerializer
            r3 = r0
            r4 = r12
            r5 = r13
            r9 = r16
            r10 = r17
            r11 = r18
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11)
            if (r1 == 0) goto L4d
            com.fasterxml.jackson.databind.ser.std.MapSerializer r0 = r0.withFilterId(r1)
        L4d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ser.std.MapSerializer.construct(java.util.Set, java.util.Set, com.fasterxml.jackson.databind.JavaType, boolean, com.fasterxml.jackson.databind.jsontype.TypeSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.JsonSerializer, java.lang.Object):com.fasterxml.jackson.databind.ser.std.MapSerializer");
    }

    public static MapSerializer construct(Set<String> set, JavaType javaType, boolean z, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer, JsonSerializer<Object> jsonSerializer2, Object obj) {
        return construct(set, null, javaType, z, typeSerializer, jsonSerializer, jsonSerializer2, obj);
    }

    protected void _ensureOverride(String str) {
        ClassUtil.verifyMustOverride(MapSerializer.class, this, str);
    }

    @Deprecated
    protected void _ensureOverride() {
        _ensureOverride("N/A");
    }

    @Deprecated
    protected MapSerializer(MapSerializer mapSerializer, TypeSerializer typeSerializer, Object obj) {
        this(mapSerializer, typeSerializer, obj, false);
    }

    @Deprecated
    public MapSerializer withContentInclusion(Object obj) {
        return new MapSerializer(this, this._valueTypeSerializer, obj, this._suppressNulls);
    }

    @Deprecated
    public static MapSerializer construct(String[] strArr, JavaType javaType, boolean z, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer, JsonSerializer<Object> jsonSerializer2, Object obj) {
        return construct(ArrayBuilders.arrayToSet(strArr), javaType, z, typeSerializer, jsonSerializer, jsonSerializer2, obj);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        JsonSerializer<?> jsonSerializerSerializerInstance;
        JsonSerializer<Object> jsonSerializerSerializerInstance2;
        JsonSerializer<?> jsonSerializerHandleSecondaryContextualization;
        Set<String> set;
        Set<String> set2;
        boolean zEquals;
        JsonInclude.Include contentInclusion;
        Object objFindFilterId;
        Boolean feature;
        AnnotationIntrospector annotationIntrospector = serializerProvider.getAnnotationIntrospector();
        Object defaultValue = null;
        AnnotatedMember member = beanProperty == null ? null : beanProperty.getMember();
        if (_neitherNull(member, annotationIntrospector)) {
            Object objFindKeySerializer = annotationIntrospector.findKeySerializer(member);
            jsonSerializerSerializerInstance = objFindKeySerializer != null ? serializerProvider.serializerInstance(member, objFindKeySerializer) : null;
            Object objFindContentSerializer = annotationIntrospector.findContentSerializer(member);
            jsonSerializerSerializerInstance2 = objFindContentSerializer != null ? serializerProvider.serializerInstance(member, objFindContentSerializer) : null;
        } else {
            jsonSerializerSerializerInstance = null;
            jsonSerializerSerializerInstance2 = null;
        }
        if (jsonSerializerSerializerInstance2 == null) {
            jsonSerializerSerializerInstance2 = this._valueSerializer;
        }
        JsonSerializer<?> jsonSerializerFindContextualConvertingSerializer = findContextualConvertingSerializer(serializerProvider, beanProperty, jsonSerializerSerializerInstance2);
        if (jsonSerializerFindContextualConvertingSerializer == null && this._valueTypeIsStatic && !this._valueType.isJavaLangObject()) {
            jsonSerializerFindContextualConvertingSerializer = serializerProvider.findContentValueSerializer(this._valueType, beanProperty);
        }
        JsonSerializer<?> jsonSerializer = jsonSerializerFindContextualConvertingSerializer;
        if (jsonSerializerSerializerInstance == null) {
            jsonSerializerSerializerInstance = this._keySerializer;
        }
        if (jsonSerializerSerializerInstance == null) {
            jsonSerializerHandleSecondaryContextualization = serializerProvider.findKeySerializer(this._keyType, beanProperty);
        } else {
            jsonSerializerHandleSecondaryContextualization = serializerProvider.handleSecondaryContextualization(jsonSerializerSerializerInstance, beanProperty);
        }
        JsonSerializer<?> jsonSerializer2 = jsonSerializerHandleSecondaryContextualization;
        Set<String> hashSet = this._ignoredEntries;
        Set<String> hashSet2 = this._includedEntries;
        boolean zIncludeFilterSuppressNulls = false;
        if (_neitherNull(member, annotationIntrospector)) {
            SerializationConfig config = serializerProvider.getConfig();
            Set<String> setFindIgnoredForSerialization = annotationIntrospector.findPropertyIgnoralByName(config, member).findIgnoredForSerialization();
            if (_nonEmpty(setFindIgnoredForSerialization)) {
                hashSet = hashSet == null ? new HashSet<>() : new HashSet(hashSet);
                Iterator<String> it = setFindIgnoredForSerialization.iterator();
                while (it.hasNext()) {
                    hashSet.add(it.next());
                }
            }
            Set<String> included = annotationIntrospector.findPropertyInclusionByName(config, member).getIncluded();
            if (included != null) {
                hashSet2 = hashSet2 == null ? new HashSet<>() : new HashSet(hashSet2);
                Iterator<String> it2 = included.iterator();
                while (it2.hasNext()) {
                    hashSet2.add(it2.next());
                }
            }
            zEquals = Boolean.TRUE.equals(annotationIntrospector.findSerializationSortAlphabetically(member));
            set = hashSet;
            set2 = hashSet2;
        } else {
            set = hashSet;
            set2 = hashSet2;
            zEquals = false;
        }
        JsonFormat.Value valueFindFormatOverrides = findFormatOverrides(serializerProvider, beanProperty, Map.class);
        MapSerializer mapSerializerWithResolved = withResolved(beanProperty, jsonSerializer2, jsonSerializer, set, set2, (valueFindFormatOverrides == null || (feature = valueFindFormatOverrides.getFeature(JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES)) == null) ? zEquals : feature.booleanValue());
        if (member != null && (objFindFilterId = annotationIntrospector.findFilterId(member)) != null) {
            mapSerializerWithResolved = mapSerializerWithResolved.withFilterId(objFindFilterId);
        }
        JsonInclude.Value valueFindIncludeOverrides = findIncludeOverrides(serializerProvider, beanProperty, Map.class);
        if (valueFindIncludeOverrides == null || (contentInclusion = valueFindIncludeOverrides.getContentInclusion()) == JsonInclude.Include.USE_DEFAULTS) {
            return mapSerializerWithResolved;
        }
        int i = C14651.$SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[contentInclusion.ordinal()];
        if (i == 1) {
            defaultValue = BeanUtil.getDefaultValue(this._valueType);
            if (defaultValue != null && defaultValue.getClass().isArray()) {
                defaultValue = ArrayBuilders.getArrayComparator(defaultValue);
            }
        } else if (i != 2) {
            if (i == 3) {
                defaultValue = MARKER_FOR_EMPTY;
            } else {
                if (i == 4) {
                    defaultValue = serializerProvider.includeFilterInstance(null, valueFindIncludeOverrides.getContentFilter());
                    if (defaultValue != null) {
                        zIncludeFilterSuppressNulls = serializerProvider.includeFilterSuppressNulls(defaultValue);
                    }
                } else if (i == 5) {
                }
                return mapSerializerWithResolved.withContentInclusion(defaultValue, zIncludeFilterSuppressNulls);
            }
        } else if (this._valueType.isReferenceType()) {
            defaultValue = MARKER_FOR_EMPTY;
        }
        zIncludeFilterSuppressNulls = true;
        return mapSerializerWithResolved.withContentInclusion(defaultValue, zIncludeFilterSuppressNulls);
    }

    /* JADX INFO: renamed from: com.fasterxml.jackson.databind.ser.std.MapSerializer$1 */
    static /* synthetic */ class C14651 {
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

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JavaType getContentType() {
        return this._valueType;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JsonSerializer<?> getContentSerializer() {
        return this._valueSerializer;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public boolean isEmpty(SerializerProvider serializerProvider, Map<?, ?> map) {
        JsonSerializer<Object> jsonSerializer_findSerializer;
        if (map.isEmpty()) {
            return true;
        }
        Object obj = this._suppressableValue;
        if (obj == null && !this._suppressNulls) {
            return false;
        }
        JsonSerializer<Object> jsonSerializer = this._valueSerializer;
        boolean z = MARKER_FOR_EMPTY == obj;
        if (jsonSerializer != null) {
            for (Object obj2 : map.values()) {
                if (obj2 == null) {
                    if (!this._suppressNulls) {
                        return false;
                    }
                } else if (z) {
                    if (!jsonSerializer.isEmpty(serializerProvider, obj2)) {
                        return false;
                    }
                } else if (obj == null || !obj.equals(map)) {
                    return false;
                }
            }
            return true;
        }
        for (Object obj3 : map.values()) {
            if (obj3 == null) {
                if (!this._suppressNulls) {
                    return false;
                }
            } else {
                try {
                    jsonSerializer_findSerializer = _findSerializer(serializerProvider, obj3);
                } catch (DatabindException unused) {
                }
                if (z) {
                    if (!jsonSerializer_findSerializer.isEmpty(serializerProvider, obj3)) {
                        return false;
                    }
                } else if (obj == null || !obj.equals(map)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public boolean hasSingleElement(Map<?, ?> map) {
        return map.size() == 1;
    }

    public JsonSerializer<?> getKeySerializer() {
        return this._keySerializer;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject(map);
        serializeWithoutTypeInfo(map, jsonGenerator, serializerProvider);
        jsonGenerator.writeEndObject();
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        jsonGenerator.assignCurrentValue(map);
        WritableTypeId writableTypeIdWriteTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(map, JsonToken.START_OBJECT));
        serializeWithoutTypeInfo(map, jsonGenerator, serializerProvider);
        typeSerializer.writeTypeSuffix(jsonGenerator, writableTypeIdWriteTypePrefix);
    }

    public void serializeWithoutTypeInfo(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        PropertyFilter propertyFilterFindPropertyFilter;
        if (map.isEmpty()) {
            return;
        }
        if (this._sortKeys || serializerProvider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
            map = _orderEntries(map, jsonGenerator, serializerProvider);
        }
        Map<?, ?> map2 = map;
        Object obj = this._filterId;
        if (obj != null && (propertyFilterFindPropertyFilter = findPropertyFilter(serializerProvider, obj, map2)) != null) {
            serializeFilteredFields(map2, jsonGenerator, serializerProvider, propertyFilterFindPropertyFilter, this._suppressableValue);
            return;
        }
        Object obj2 = this._suppressableValue;
        if (obj2 != null || this._suppressNulls) {
            serializeOptionalFields(map2, jsonGenerator, serializerProvider, obj2);
            return;
        }
        JsonSerializer<Object> jsonSerializer = this._valueSerializer;
        if (jsonSerializer != null) {
            serializeFieldsUsing(map2, jsonGenerator, serializerProvider, jsonSerializer);
        } else {
            serializeFields(map2, jsonGenerator, serializerProvider);
        }
    }

    public void serializeFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Object obj = null;
        if (this._valueTypeSerializer != null) {
            serializeTypedFields(map, jsonGenerator, serializerProvider, null);
            return;
        }
        JsonSerializer<Object> jsonSerializer = this._keySerializer;
        try {
            Object key = null;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                try {
                    Object value = entry.getValue();
                    key = entry.getKey();
                    if (key == null) {
                        serializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, jsonGenerator, serializerProvider);
                    } else {
                        IgnorePropertiesUtil.Checker checker = this._inclusionChecker;
                        if (checker == null || !checker.shouldIgnore(key)) {
                            jsonSerializer.serialize(key, jsonGenerator, serializerProvider);
                        }
                    }
                    if (value == null) {
                        serializerProvider.defaultSerializeNull(jsonGenerator);
                    } else {
                        JsonSerializer<Object> jsonSerializer_findSerializer = this._valueSerializer;
                        if (jsonSerializer_findSerializer == null) {
                            jsonSerializer_findSerializer = _findSerializer(serializerProvider, value);
                        }
                        jsonSerializer_findSerializer.serialize(value, jsonGenerator, serializerProvider);
                    }
                } catch (Exception e) {
                    e = e;
                    obj = key;
                    wrapAndThrow(serializerProvider, e, map, String.valueOf(obj));
                    return;
                }
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0069 A[EXC_TOP_SPLITTER, PHI: r5
  0x0069: PHI (r5v3 com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object>) = 
  (r5v2 com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object>)
  (r5v5 com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object>)
  (r5v5 com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object>)
  (r5v5 com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object>)
 binds: [B:27:0x004a, B:33:0x005d, B:35:0x0060, B:37:0x0066] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void serializeOptionalFields(java.util.Map<?, ?> r8, com.fasterxml.jackson.core.JsonGenerator r9, com.fasterxml.jackson.databind.SerializerProvider r10, java.lang.Object r11) throws java.io.IOException {
        /*
            r7 = this;
            com.fasterxml.jackson.databind.jsontype.TypeSerializer r0 = r7._valueTypeSerializer
            if (r0 == 0) goto L8
            r7.serializeTypedFields(r8, r9, r10, r11)
            return
        L8:
            java.lang.Object r0 = com.fasterxml.jackson.databind.ser.std.MapSerializer.MARKER_FOR_EMPTY
            if (r0 != r11) goto Le
            r0 = 1
            goto Lf
        Le:
            r0 = 0
        Lf:
            java.util.Set r1 = r8.entrySet()
            java.util.Iterator r1 = r1.iterator()
        L17:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L79
            java.lang.Object r2 = r1.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getKey()
            if (r3 != 0) goto L32
            com.fasterxml.jackson.databind.JavaType r4 = r7._keyType
            com.fasterxml.jackson.databind.BeanProperty r5 = r7._property
            com.fasterxml.jackson.databind.JsonSerializer r4 = r10.findNullKeySerializer(r4, r5)
            goto L3f
        L32:
            com.fasterxml.jackson.databind.util.IgnorePropertiesUtil$Checker r4 = r7._inclusionChecker
            if (r4 == 0) goto L3d
            boolean r4 = r4.shouldIgnore(r3)
            if (r4 == 0) goto L3d
            goto L17
        L3d:
            com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r4 = r7._keySerializer
        L3f:
            java.lang.Object r2 = r2.getValue()
            if (r2 != 0) goto L4f
            boolean r5 = r7._suppressNulls
            if (r5 == 0) goto L4a
            goto L17
        L4a:
            com.fasterxml.jackson.databind.JsonSerializer r5 = r10.getDefaultNullValueSerializer()
            goto L69
        L4f:
            com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r5 = r7._valueSerializer
            if (r5 != 0) goto L57
            com.fasterxml.jackson.databind.JsonSerializer r5 = r7._findSerializer(r10, r2)
        L57:
            if (r0 == 0) goto L60
            boolean r6 = r5.isEmpty(r10, r2)
            if (r6 == 0) goto L69
            goto L17
        L60:
            if (r11 == 0) goto L69
            boolean r6 = r11.equals(r2)
            if (r6 == 0) goto L69
            goto L17
        L69:
            r4.serialize(r3, r9, r10)     // Catch: java.lang.Exception -> L70
            r5.serialize(r2, r9, r10)     // Catch: java.lang.Exception -> L70
            goto L17
        L70:
            r2 = move-exception
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r7.wrapAndThrow(r10, r2, r8, r3)
            goto L17
        L79:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ser.std.MapSerializer.serializeOptionalFields(java.util.Map, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider, java.lang.Object):void");
    }

    public void serializeFieldsUsing(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, JsonSerializer<Object> jsonSerializer) throws IOException {
        JsonSerializer<Object> jsonSerializer2 = this._keySerializer;
        TypeSerializer typeSerializer = this._valueTypeSerializer;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            IgnorePropertiesUtil.Checker checker = this._inclusionChecker;
            if (checker == null || !checker.shouldIgnore(key)) {
                if (key == null) {
                    serializerProvider.findNullKeySerializer(this._keyType, this._property).serialize(null, jsonGenerator, serializerProvider);
                } else {
                    jsonSerializer2.serialize(key, jsonGenerator, serializerProvider);
                }
                Object value = entry.getValue();
                if (value == null) {
                    serializerProvider.defaultSerializeNull(jsonGenerator);
                } else if (typeSerializer == null) {
                    try {
                        jsonSerializer.serialize(value, jsonGenerator, serializerProvider);
                    } catch (Exception e) {
                        wrapAndThrow(serializerProvider, e, map, String.valueOf(key));
                    }
                } else {
                    jsonSerializer.serializeWithType(value, jsonGenerator, serializerProvider, typeSerializer);
                }
            }
        }
    }

    public void serializeFilteredFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, PropertyFilter propertyFilter, Object obj) throws IOException {
        JsonSerializer<Object> jsonSerializerFindNullKeySerializer;
        JsonSerializer<Object> defaultNullValueSerializer;
        MapProperty mapProperty = new MapProperty(this._valueTypeSerializer, this._property);
        boolean z = MARKER_FOR_EMPTY == obj;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            IgnorePropertiesUtil.Checker checker = this._inclusionChecker;
            if (checker == null || !checker.shouldIgnore(key)) {
                if (key == null) {
                    jsonSerializerFindNullKeySerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
                } else {
                    jsonSerializerFindNullKeySerializer = this._keySerializer;
                }
                Object value = entry.getValue();
                if (value == null) {
                    if (!this._suppressNulls) {
                        defaultNullValueSerializer = serializerProvider.getDefaultNullValueSerializer();
                        mapProperty.reset(key, value, jsonSerializerFindNullKeySerializer, defaultNullValueSerializer);
                        try {
                            propertyFilter.serializeAsField(map, jsonGenerator, serializerProvider, mapProperty);
                        } catch (Exception e) {
                            wrapAndThrow(serializerProvider, e, map, String.valueOf(key));
                        }
                    }
                } else {
                    defaultNullValueSerializer = this._valueSerializer;
                    if (defaultNullValueSerializer == null) {
                        defaultNullValueSerializer = _findSerializer(serializerProvider, value);
                    }
                    if (z) {
                        if (!defaultNullValueSerializer.isEmpty(serializerProvider, value)) {
                            mapProperty.reset(key, value, jsonSerializerFindNullKeySerializer, defaultNullValueSerializer);
                            propertyFilter.serializeAsField(map, jsonGenerator, serializerProvider, mapProperty);
                        }
                    } else if (obj == null || !obj.equals(value)) {
                        mapProperty.reset(key, value, jsonSerializerFindNullKeySerializer, defaultNullValueSerializer);
                        propertyFilter.serializeAsField(map, jsonGenerator, serializerProvider, mapProperty);
                    }
                }
            }
        }
    }

    public void serializeTypedFields(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, Object obj) throws IOException {
        JsonSerializer<Object> jsonSerializerFindNullKeySerializer;
        JsonSerializer<Object> defaultNullValueSerializer;
        boolean z = MARKER_FOR_EMPTY == obj;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            if (key == null) {
                jsonSerializerFindNullKeySerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
            } else {
                IgnorePropertiesUtil.Checker checker = this._inclusionChecker;
                if (checker == null || !checker.shouldIgnore(key)) {
                    jsonSerializerFindNullKeySerializer = this._keySerializer;
                }
            }
            Object value = entry.getValue();
            if (value == null) {
                if (!this._suppressNulls) {
                    defaultNullValueSerializer = serializerProvider.getDefaultNullValueSerializer();
                    jsonSerializerFindNullKeySerializer.serialize(key, jsonGenerator, serializerProvider);
                    try {
                        defaultNullValueSerializer.serializeWithType(value, jsonGenerator, serializerProvider, this._valueTypeSerializer);
                    } catch (Exception e) {
                        wrapAndThrow(serializerProvider, e, map, String.valueOf(key));
                    }
                }
            } else {
                defaultNullValueSerializer = this._valueSerializer;
                if (defaultNullValueSerializer == null) {
                    defaultNullValueSerializer = _findSerializer(serializerProvider, value);
                }
                if (z) {
                    if (!defaultNullValueSerializer.isEmpty(serializerProvider, value)) {
                        jsonSerializerFindNullKeySerializer.serialize(key, jsonGenerator, serializerProvider);
                        defaultNullValueSerializer.serializeWithType(value, jsonGenerator, serializerProvider, this._valueTypeSerializer);
                    }
                } else if (obj == null || !obj.equals(value)) {
                    jsonSerializerFindNullKeySerializer.serialize(key, jsonGenerator, serializerProvider);
                    defaultNullValueSerializer.serializeWithType(value, jsonGenerator, serializerProvider, this._valueTypeSerializer);
                }
            }
        }
    }

    public void serializeFilteredAnyProperties(SerializerProvider serializerProvider, JsonGenerator jsonGenerator, Object obj, Map<?, ?> map, PropertyFilter propertyFilter, Object obj2) throws IOException {
        JsonSerializer<Object> jsonSerializerFindNullKeySerializer;
        JsonSerializer<Object> defaultNullValueSerializer;
        MapProperty mapProperty = new MapProperty(this._valueTypeSerializer, this._property);
        boolean z = MARKER_FOR_EMPTY == obj2;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            IgnorePropertiesUtil.Checker checker = this._inclusionChecker;
            if (checker == null || !checker.shouldIgnore(key)) {
                if (key == null) {
                    jsonSerializerFindNullKeySerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
                } else {
                    jsonSerializerFindNullKeySerializer = this._keySerializer;
                }
                Object value = entry.getValue();
                if (value == null) {
                    if (!this._suppressNulls) {
                        defaultNullValueSerializer = serializerProvider.getDefaultNullValueSerializer();
                        mapProperty.reset(key, value, jsonSerializerFindNullKeySerializer, defaultNullValueSerializer);
                        try {
                            propertyFilter.serializeAsField(obj, jsonGenerator, serializerProvider, mapProperty);
                        } catch (Exception e) {
                            wrapAndThrow(serializerProvider, e, map, String.valueOf(key));
                        }
                    }
                } else {
                    defaultNullValueSerializer = this._valueSerializer;
                    if (defaultNullValueSerializer == null) {
                        defaultNullValueSerializer = _findSerializer(serializerProvider, value);
                    }
                    if (z) {
                        if (!defaultNullValueSerializer.isEmpty(serializerProvider, value)) {
                            mapProperty.reset(key, value, jsonSerializerFindNullKeySerializer, defaultNullValueSerializer);
                            propertyFilter.serializeAsField(obj, jsonGenerator, serializerProvider, mapProperty);
                        }
                    } else if (obj2 == null || !obj2.equals(value)) {
                        mapProperty.reset(key, value, jsonSerializerFindNullKeySerializer, defaultNullValueSerializer);
                        propertyFilter.serializeAsField(obj, jsonGenerator, serializerProvider, mapProperty);
                    }
                }
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    @Deprecated
    public JsonNode getSchema(SerializerProvider serializerProvider, Type type) {
        return createSchemaNode("object", true);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        JsonMapFormatVisitor jsonMapFormatVisitorExpectMapFormat = jsonFormatVisitorWrapper.expectMapFormat(javaType);
        if (jsonMapFormatVisitorExpectMapFormat != null) {
            jsonMapFormatVisitorExpectMapFormat.keyFormat(this._keySerializer, this._keyType);
            JsonSerializer<Object> jsonSerializer_findAndAddDynamic = this._valueSerializer;
            if (jsonSerializer_findAndAddDynamic == null) {
                jsonSerializer_findAndAddDynamic = _findAndAddDynamic(this._dynamicValueSerializers, this._valueType, jsonFormatVisitorWrapper.getProvider());
            }
            jsonMapFormatVisitorExpectMapFormat.valueFormat(jsonSerializer_findAndAddDynamic, this._valueType);
        }
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, Class<?> cls, SerializerProvider serializerProvider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult serializerAndMapResultFindAndAddSecondarySerializer = propertySerializerMap.findAndAddSecondarySerializer(cls, serializerProvider, this._property);
        if (propertySerializerMap != serializerAndMapResultFindAndAddSecondarySerializer.map) {
            this._dynamicValueSerializers = serializerAndMapResultFindAndAddSecondarySerializer.map;
        }
        return serializerAndMapResultFindAndAddSecondarySerializer.serializer;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap propertySerializerMap, JavaType javaType, SerializerProvider serializerProvider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult serializerAndMapResultFindAndAddSecondarySerializer = propertySerializerMap.findAndAddSecondarySerializer(javaType, serializerProvider, this._property);
        if (propertySerializerMap != serializerAndMapResultFindAndAddSecondarySerializer.map) {
            this._dynamicValueSerializers = serializerAndMapResultFindAndAddSecondarySerializer.map;
        }
        return serializerAndMapResultFindAndAddSecondarySerializer.serializer;
    }

    protected Map<?, ?> _orderEntries(Map<?, ?> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (map instanceof SortedMap) {
            return map;
        }
        if (_hasNullKey(map)) {
            TreeMap treeMap = new TreeMap();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Object key = entry.getKey();
                if (key == null) {
                    _writeNullKeyedEntry(jsonGenerator, serializerProvider, entry.getValue());
                } else {
                    treeMap.put(key, entry.getValue());
                }
            }
            return treeMap;
        }
        return new TreeMap(map);
    }

    protected boolean _hasNullKey(Map<?, ?> map) {
        return (map instanceof HashMap) && map.containsKey(null);
    }

    protected void _writeNullKeyedEntry(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, Object obj) throws IOException {
        JsonSerializer<Object> jsonSerializer_findSerializer;
        JsonSerializer<Object> jsonSerializerFindNullKeySerializer = serializerProvider.findNullKeySerializer(this._keyType, this._property);
        if (obj == null) {
            if (this._suppressNulls) {
                return;
            } else {
                jsonSerializer_findSerializer = serializerProvider.getDefaultNullValueSerializer();
            }
        } else {
            jsonSerializer_findSerializer = this._valueSerializer;
            if (jsonSerializer_findSerializer == null) {
                jsonSerializer_findSerializer = _findSerializer(serializerProvider, obj);
            }
            Object obj2 = this._suppressableValue;
            if (obj2 == MARKER_FOR_EMPTY) {
                if (jsonSerializer_findSerializer.isEmpty(serializerProvider, obj)) {
                    return;
                }
            } else if (obj2 != null && obj2.equals(obj)) {
                return;
            }
        }
        try {
            jsonSerializerFindNullKeySerializer.serialize(null, jsonGenerator, serializerProvider);
            jsonSerializer_findSerializer.serialize(obj, jsonGenerator, serializerProvider);
        } catch (Exception e) {
            wrapAndThrow(serializerProvider, e, obj, "");
        }
    }

    private final JsonSerializer<Object> _findSerializer(SerializerProvider serializerProvider, Object obj) throws JsonMappingException {
        Class<?> cls = obj.getClass();
        JsonSerializer<Object> jsonSerializerSerializerFor = this._dynamicValueSerializers.serializerFor(cls);
        if (jsonSerializerSerializerFor != null) {
            return jsonSerializerSerializerFor;
        }
        if (this._valueType.hasGenericTypes()) {
            return _findAndAddDynamic(this._dynamicValueSerializers, serializerProvider.constructSpecializedType(this._valueType, cls), serializerProvider);
        }
        return _findAndAddDynamic(this._dynamicValueSerializers, cls, serializerProvider);
    }
}
