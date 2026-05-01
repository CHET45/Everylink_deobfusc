package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.github.houbb.heaven.constant.MethodConst;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes3.dex */
public class POJOPropertyBuilder extends BeanPropertyDefinition implements Comparable<POJOPropertyBuilder> {
    private static final AnnotationIntrospector.ReferenceProperty NOT_REFEFERENCE_PROP = AnnotationIntrospector.ReferenceProperty.managed("");
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final MapperConfig<?> _config;
    protected Linked<AnnotatedParameter> _ctorParameters;
    protected Linked<AnnotatedField> _fields;
    protected final boolean _forSerialization;
    protected Linked<AnnotatedMethod> _getters;
    protected final PropertyName _internalName;
    protected transient PropertyMetadata _metadata;
    protected final PropertyName _name;
    protected transient AnnotationIntrospector.ReferenceProperty _referenceInfo;
    protected Linked<AnnotatedMethod> _setters;

    private interface WithMember<T> {
        T withMember(AnnotatedMember annotatedMember);
    }

    public POJOPropertyBuilder(MapperConfig<?> mapperConfig, AnnotationIntrospector annotationIntrospector, boolean z, PropertyName propertyName) {
        this(mapperConfig, annotationIntrospector, z, propertyName, propertyName);
    }

    protected POJOPropertyBuilder(MapperConfig<?> mapperConfig, AnnotationIntrospector annotationIntrospector, boolean z, PropertyName propertyName, PropertyName propertyName2) {
        this._config = mapperConfig;
        this._annotationIntrospector = annotationIntrospector;
        this._internalName = propertyName;
        this._name = propertyName2;
        this._forSerialization = z;
    }

    protected POJOPropertyBuilder(POJOPropertyBuilder pOJOPropertyBuilder, PropertyName propertyName) {
        this._config = pOJOPropertyBuilder._config;
        this._annotationIntrospector = pOJOPropertyBuilder._annotationIntrospector;
        this._internalName = pOJOPropertyBuilder._internalName;
        this._name = propertyName;
        this._fields = pOJOPropertyBuilder._fields;
        this._ctorParameters = pOJOPropertyBuilder._ctorParameters;
        this._getters = pOJOPropertyBuilder._getters;
        this._setters = pOJOPropertyBuilder._setters;
        this._forSerialization = pOJOPropertyBuilder._forSerialization;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public POJOPropertyBuilder withName(PropertyName propertyName) {
        return new POJOPropertyBuilder(this, propertyName);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public POJOPropertyBuilder withSimpleName(String str) {
        PropertyName propertyNameWithSimpleName = this._name.withSimpleName(str);
        return propertyNameWithSimpleName == this._name ? this : new POJOPropertyBuilder(this, propertyNameWithSimpleName);
    }

    @Override // java.lang.Comparable
    public int compareTo(POJOPropertyBuilder pOJOPropertyBuilder) {
        if (this._ctorParameters != null) {
            if (pOJOPropertyBuilder._ctorParameters == null) {
                return -1;
            }
        } else if (pOJOPropertyBuilder._ctorParameters != null) {
            return 1;
        }
        return getName().compareTo(pOJOPropertyBuilder.getName());
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition, com.fasterxml.jackson.databind.util.Named
    public String getName() {
        PropertyName propertyName = this._name;
        if (propertyName == null) {
            return null;
        }
        return propertyName.getSimpleName();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public PropertyName getFullName() {
        return this._name;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasName(PropertyName propertyName) {
        return this._name.equals(propertyName);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public String getInternalName() {
        return this._internalName.getSimpleName();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public PropertyName getWrapperName() {
        AnnotationIntrospector annotationIntrospector;
        AnnotatedMember primaryMember = getPrimaryMember();
        if (primaryMember == null || (annotationIntrospector = this._annotationIntrospector) == null) {
            return null;
        }
        return annotationIntrospector.findWrapperName(primaryMember);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean isExplicitlyIncluded() {
        return _anyExplicits(this._fields) || _anyExplicits(this._getters) || _anyExplicits(this._setters) || _anyExplicitNames(this._ctorParameters);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean isExplicitlyNamed() {
        return _anyExplicitNames(this._fields) || _anyExplicitNames(this._getters) || _anyExplicitNames(this._setters) || _anyExplicitNames(this._ctorParameters);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public PropertyMetadata getMetadata() {
        if (this._metadata == null) {
            AnnotatedMember primaryMemberUnchecked = getPrimaryMemberUnchecked();
            if (primaryMemberUnchecked == null) {
                this._metadata = PropertyMetadata.STD_REQUIRED_OR_OPTIONAL;
            } else {
                Boolean boolHasRequiredMarker = this._annotationIntrospector.hasRequiredMarker(primaryMemberUnchecked);
                String strFindPropertyDescription = this._annotationIntrospector.findPropertyDescription(primaryMemberUnchecked);
                Integer numFindPropertyIndex = this._annotationIntrospector.findPropertyIndex(primaryMemberUnchecked);
                String strFindPropertyDefaultValue = this._annotationIntrospector.findPropertyDefaultValue(primaryMemberUnchecked);
                if (boolHasRequiredMarker == null && numFindPropertyIndex == null && strFindPropertyDefaultValue == null) {
                    PropertyMetadata propertyMetadataWithDescription = PropertyMetadata.STD_REQUIRED_OR_OPTIONAL;
                    if (strFindPropertyDescription != null) {
                        propertyMetadataWithDescription = propertyMetadataWithDescription.withDescription(strFindPropertyDescription);
                    }
                    this._metadata = propertyMetadataWithDescription;
                } else {
                    this._metadata = PropertyMetadata.construct(boolHasRequiredMarker, strFindPropertyDescription, numFindPropertyIndex, strFindPropertyDefaultValue);
                }
                if (!this._forSerialization) {
                    this._metadata = _getSetterInfo(this._metadata, primaryMemberUnchecked);
                }
            }
        }
        return this._metadata;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0035 A[PHI: r1 r7
  0x0035: PHI (r1v3 boolean) = (r1v0 boolean), (r1v6 boolean) binds: [B:5:0x000b, B:14:0x002a] A[DONT_GENERATE, DONT_INLINE]
  0x0035: PHI (r7v5 com.fasterxml.jackson.databind.PropertyMetadata) = (r7v0 com.fasterxml.jackson.databind.PropertyMetadata), (r7v9 com.fasterxml.jackson.databind.PropertyMetadata) binds: [B:5:0x000b, B:14:0x002a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected com.fasterxml.jackson.databind.PropertyMetadata _getSetterInfo(com.fasterxml.jackson.databind.PropertyMetadata r7, com.fasterxml.jackson.databind.introspect.AnnotatedMember r8) {
        /*
            r6 = this;
            com.fasterxml.jackson.databind.introspect.AnnotatedMember r0 = r6.getAccessor()
            r1 = 1
            r2 = 0
            if (r8 == 0) goto L72
            com.fasterxml.jackson.databind.AnnotationIntrospector r3 = r6._annotationIntrospector
            r4 = 0
            if (r3 == 0) goto L35
            if (r0 == 0) goto L24
            java.lang.Boolean r3 = r3.findMergeInfo(r8)
            if (r3 == 0) goto L24
            boolean r1 = r3.booleanValue()
            if (r1 == 0) goto L23
            com.fasterxml.jackson.databind.PropertyMetadata$MergeInfo r1 = com.fasterxml.jackson.databind.PropertyMetadata.MergeInfo.createForPropertyOverride(r0)
            com.fasterxml.jackson.databind.PropertyMetadata r7 = r7.withMergeInfo(r1)
        L23:
            r1 = r4
        L24:
            com.fasterxml.jackson.databind.AnnotationIntrospector r3 = r6._annotationIntrospector
            com.fasterxml.jackson.annotation.JsonSetter$Value r3 = r3.findSetterInfo(r8)
            if (r3 == 0) goto L35
            com.fasterxml.jackson.annotation.Nulls r2 = r3.nonDefaultValueNulls()
            com.fasterxml.jackson.annotation.Nulls r3 = r3.nonDefaultContentNulls()
            goto L36
        L35:
            r3 = r2
        L36:
            if (r1 != 0) goto L3c
            if (r2 == 0) goto L3c
            if (r3 != 0) goto L73
        L3c:
            java.lang.Class r8 = r6._rawTypeOf(r8)
            com.fasterxml.jackson.databind.cfg.MapperConfig<?> r5 = r6._config
            com.fasterxml.jackson.databind.cfg.ConfigOverride r8 = r5.getConfigOverride(r8)
            com.fasterxml.jackson.annotation.JsonSetter$Value r5 = r8.getSetterInfo()
            if (r5 == 0) goto L58
            if (r2 != 0) goto L52
            com.fasterxml.jackson.annotation.Nulls r2 = r5.nonDefaultValueNulls()
        L52:
            if (r3 != 0) goto L58
            com.fasterxml.jackson.annotation.Nulls r3 = r5.nonDefaultContentNulls()
        L58:
            if (r1 == 0) goto L73
            if (r0 == 0) goto L73
            java.lang.Boolean r8 = r8.getMergeable()
            if (r8 == 0) goto L73
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L70
            com.fasterxml.jackson.databind.PropertyMetadata$MergeInfo r8 = com.fasterxml.jackson.databind.PropertyMetadata.MergeInfo.createForTypeOverride(r0)
            com.fasterxml.jackson.databind.PropertyMetadata r7 = r7.withMergeInfo(r8)
        L70:
            r1 = r4
            goto L73
        L72:
            r3 = r2
        L73:
            if (r1 != 0) goto L79
            if (r2 == 0) goto L79
            if (r3 != 0) goto La5
        L79:
            com.fasterxml.jackson.databind.cfg.MapperConfig<?> r8 = r6._config
            com.fasterxml.jackson.annotation.JsonSetter$Value r8 = r8.getDefaultSetterInfo()
            if (r2 != 0) goto L85
            com.fasterxml.jackson.annotation.Nulls r2 = r8.nonDefaultValueNulls()
        L85:
            if (r3 != 0) goto L8b
            com.fasterxml.jackson.annotation.Nulls r3 = r8.nonDefaultContentNulls()
        L8b:
            if (r1 == 0) goto La5
            com.fasterxml.jackson.databind.cfg.MapperConfig<?> r8 = r6._config
            java.lang.Boolean r8 = r8.getDefaultMergeable()
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            boolean r8 = r1.equals(r8)
            if (r8 == 0) goto La5
            if (r0 == 0) goto La5
            com.fasterxml.jackson.databind.PropertyMetadata$MergeInfo r8 = com.fasterxml.jackson.databind.PropertyMetadata.MergeInfo.createForDefaults(r0)
            com.fasterxml.jackson.databind.PropertyMetadata r7 = r7.withMergeInfo(r8)
        La5:
            if (r2 != 0) goto La9
            if (r3 == 0) goto Lad
        La9:
            com.fasterxml.jackson.databind.PropertyMetadata r7 = r7.withNulls(r2, r3)
        Lad:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder._getSetterInfo(com.fasterxml.jackson.databind.PropertyMetadata, com.fasterxml.jackson.databind.introspect.AnnotatedMember):com.fasterxml.jackson.databind.PropertyMetadata");
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public JavaType getPrimaryType() {
        if (this._forSerialization) {
            Annotated getter = getGetter();
            if (getter == null && (getter = getField()) == null) {
                return TypeFactory.unknownType();
            }
            return getter.getType();
        }
        Annotated constructorParameter = getConstructorParameter();
        if (constructorParameter == null) {
            AnnotatedMethod setter = getSetter();
            if (setter != null) {
                return setter.getParameterType(0);
            }
            constructorParameter = getField();
        }
        if (constructorParameter == null && (constructorParameter = getGetter()) == null) {
            return TypeFactory.unknownType();
        }
        return constructorParameter.getType();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public Class<?> getRawPrimaryType() {
        return getPrimaryType().getRawClass();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasGetter() {
        return this._getters != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasSetter() {
        return this._setters != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasField() {
        return this._fields != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasConstructorParameter() {
        return this._ctorParameters != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean couldDeserialize() {
        Linked<AnnotatedField> linked;
        return (this._ctorParameters == null && this._setters == null && ((linked = this._fields) == null || !_anyVisible(linked))) ? false : true;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean couldSerialize() {
        return (this._getters == null && this._fields == null) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0045  */
    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.fasterxml.jackson.databind.introspect.AnnotatedMethod getGetter() {
        /*
            r5 = this;
            com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder$Linked<com.fasterxml.jackson.databind.introspect.AnnotatedMethod> r0 = r5._getters
            if (r0 != 0) goto L6
            r0 = 0
            return r0
        L6:
            com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder$Linked<T> r1 = r0.next
            if (r1 != 0) goto Lf
            T r0 = r0.value
            com.fasterxml.jackson.databind.introspect.AnnotatedMethod r0 = (com.fasterxml.jackson.databind.introspect.AnnotatedMethod) r0
            return r0
        Lf:
            if (r1 == 0) goto L86
            T r2 = r0.value
            com.fasterxml.jackson.databind.introspect.AnnotatedMethod r2 = (com.fasterxml.jackson.databind.introspect.AnnotatedMethod) r2
            java.lang.Class r2 = r2.getDeclaringClass()
            T r3 = r1.value
            com.fasterxml.jackson.databind.introspect.AnnotatedMethod r3 = (com.fasterxml.jackson.databind.introspect.AnnotatedMethod) r3
            java.lang.Class r3 = r3.getDeclaringClass()
            if (r2 == r3) goto L31
            boolean r4 = r2.isAssignableFrom(r3)
            if (r4 == 0) goto L2a
            goto L45
        L2a:
            boolean r2 = r3.isAssignableFrom(r2)
            if (r2 == 0) goto L31
            goto L46
        L31:
            T r2 = r1.value
            com.fasterxml.jackson.databind.introspect.AnnotatedMethod r2 = (com.fasterxml.jackson.databind.introspect.AnnotatedMethod) r2
            int r2 = r5._getterPriority(r2)
            T r3 = r0.value
            com.fasterxml.jackson.databind.introspect.AnnotatedMethod r3 = (com.fasterxml.jackson.databind.introspect.AnnotatedMethod) r3
            int r3 = r5._getterPriority(r3)
            if (r2 == r3) goto L49
            if (r2 >= r3) goto L46
        L45:
            r0 = r1
        L46:
            com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder$Linked<T> r1 = r1.next
            goto Lf
        L49:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Conflicting getter definitions for property \""
            r3.<init>(r4)
            java.lang.String r4 = r5.getName()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = "\": "
            java.lang.StringBuilder r3 = r3.append(r4)
            T r0 = r0.value
            com.fasterxml.jackson.databind.introspect.AnnotatedMethod r0 = (com.fasterxml.jackson.databind.introspect.AnnotatedMethod) r0
            java.lang.String r0 = r0.getFullName()
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r3 = " vs "
            java.lang.StringBuilder r0 = r0.append(r3)
            T r1 = r1.value
            com.fasterxml.jackson.databind.introspect.AnnotatedMethod r1 = (com.fasterxml.jackson.databind.introspect.AnnotatedMethod) r1
            java.lang.String r1 = r1.getFullName()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            r2.<init>(r0)
            throw r2
        L86:
            com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder$Linked r1 = r0.withoutNext()
            r5._getters = r1
            T r0 = r0.value
            com.fasterxml.jackson.databind.introspect.AnnotatedMethod r0 = (com.fasterxml.jackson.databind.introspect.AnnotatedMethod) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.getGetter():com.fasterxml.jackson.databind.introspect.AnnotatedMethod");
    }

    protected AnnotatedMethod getGetterUnchecked() {
        Linked<AnnotatedMethod> linked = this._getters;
        if (linked == null) {
            return null;
        }
        return linked.value;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMethod getSetter() {
        Linked linked = this._setters;
        if (linked == null) {
            return null;
        }
        Linked linked2 = linked.next;
        if (linked2 == null) {
            return (AnnotatedMethod) linked.value;
        }
        while (linked2 != null) {
            AnnotatedMethod annotatedMethod_selectSetter = _selectSetter((AnnotatedMethod) linked.value, (AnnotatedMethod) linked2.value);
            if (annotatedMethod_selectSetter != linked.value) {
                if (annotatedMethod_selectSetter != linked2.value) {
                    return _selectSetterFromMultiple(linked, linked2);
                }
                linked = linked2;
            }
            linked2 = linked2.next;
        }
        this._setters = linked.withoutNext();
        return (AnnotatedMethod) linked.value;
    }

    protected AnnotatedMethod getSetterUnchecked() {
        Linked<AnnotatedMethod> linked = this._setters;
        if (linked == null) {
            return null;
        }
        return linked.value;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected AnnotatedMethod _selectSetterFromMultiple(Linked<AnnotatedMethod> linked, Linked<AnnotatedMethod> linked2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(linked.value);
        arrayList.add(linked2.value);
        Linked linked3 = linked2.next;
        Linked linked4 = linked;
        while (linked3 != null) {
            AnnotatedMethod annotatedMethod_selectSetter = _selectSetter((AnnotatedMethod) linked4.value, (AnnotatedMethod) linked3.value);
            if (annotatedMethod_selectSetter != linked4.value) {
                if (annotatedMethod_selectSetter == linked3.value) {
                    arrayList.clear();
                    linked4 = linked3;
                } else {
                    arrayList.add(linked3.value);
                }
            }
            linked3 = linked3.next;
            linked4 = linked4;
        }
        if (arrayList.isEmpty()) {
            this._setters = linked4.withoutNext();
            return (AnnotatedMethod) linked4.value;
        }
        throw new IllegalArgumentException(String.format("Conflicting setter definitions for property \"%s\": %s", getName(), (String) arrayList.stream().map(new Function() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AnnotatedMethod) obj).getFullName();
            }
        }).collect(Collectors.joining(" vs "))));
    }

    protected AnnotatedMethod _selectSetter(AnnotatedMethod annotatedMethod, AnnotatedMethod annotatedMethod2) {
        Class<?> declaringClass = annotatedMethod.getDeclaringClass();
        Class<?> declaringClass2 = annotatedMethod2.getDeclaringClass();
        if (declaringClass != declaringClass2) {
            if (declaringClass.isAssignableFrom(declaringClass2)) {
                return annotatedMethod2;
            }
            if (declaringClass2.isAssignableFrom(declaringClass)) {
                return annotatedMethod;
            }
        }
        int i_setterPriority = _setterPriority(annotatedMethod2);
        int i_setterPriority2 = _setterPriority(annotatedMethod);
        if (i_setterPriority != i_setterPriority2) {
            return i_setterPriority < i_setterPriority2 ? annotatedMethod2 : annotatedMethod;
        }
        AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
        if (annotationIntrospector == null) {
            return null;
        }
        return annotationIntrospector.resolveSetterConflict(this._config, annotatedMethod, annotatedMethod2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0038  */
    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.fasterxml.jackson.databind.introspect.AnnotatedField getField() {
        /*
            r6 = this;
            com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder$Linked<com.fasterxml.jackson.databind.introspect.AnnotatedField> r0 = r6._fields
            if (r0 != 0) goto L6
            r0 = 0
            return r0
        L6:
            T r0 = r0.value
            com.fasterxml.jackson.databind.introspect.AnnotatedField r0 = (com.fasterxml.jackson.databind.introspect.AnnotatedField) r0
            com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder$Linked<com.fasterxml.jackson.databind.introspect.AnnotatedField> r1 = r6._fields
            com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder$Linked<T> r1 = r1.next
        Le:
            if (r1 == 0) goto L71
            T r2 = r1.value
            com.fasterxml.jackson.databind.introspect.AnnotatedField r2 = (com.fasterxml.jackson.databind.introspect.AnnotatedField) r2
            java.lang.Class r3 = r0.getDeclaringClass()
            java.lang.Class r4 = r2.getDeclaringClass()
            if (r3 == r4) goto L2c
            boolean r5 = r3.isAssignableFrom(r4)
            if (r5 == 0) goto L25
            goto L38
        L25:
            boolean r3 = r4.isAssignableFrom(r3)
            if (r3 == 0) goto L2c
            goto L39
        L2c:
            boolean r3 = r0.isStatic()
            boolean r4 = r2.isStatic()
            if (r3 == r4) goto L3c
            if (r3 == 0) goto L39
        L38:
            r0 = r2
        L39:
            com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder$Linked<T> r1 = r1.next
            goto Le
        L3c:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Multiple fields representing property \""
            r3.<init>(r4)
            java.lang.String r4 = r6.getName()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = "\": "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r0 = r0.getFullName()
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r3 = " vs "
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r2 = r2.getFullName()
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L71:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.getField():com.fasterxml.jackson.databind.introspect.AnnotatedField");
    }

    protected AnnotatedField getFieldUnchecked() {
        Linked<AnnotatedField> linked = this._fields;
        if (linked == null) {
            return null;
        }
        return linked.value;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedParameter getConstructorParameter() {
        Linked linked = this._ctorParameters;
        if (linked == null) {
            return null;
        }
        while (!(((AnnotatedParameter) linked.value).getOwner() instanceof AnnotatedConstructor)) {
            linked = linked.next;
            if (linked == null) {
                return this._ctorParameters.value;
            }
        }
        return (AnnotatedParameter) linked.value;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public Iterator<AnnotatedParameter> getConstructorParameters() {
        if (this._ctorParameters == null) {
            return ClassUtil.emptyIterator();
        }
        return new MemberIterator(this._ctorParameters);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMember getPrimaryMember() {
        if (this._forSerialization) {
            return getAccessor();
        }
        AnnotatedMember mutator = getMutator();
        return mutator == null ? getAccessor() : mutator;
    }

    protected AnnotatedMember getPrimaryMemberUnchecked() {
        if (this._forSerialization) {
            Linked<AnnotatedMethod> linked = this._getters;
            if (linked != null) {
                return linked.value;
            }
            Linked<AnnotatedField> linked2 = this._fields;
            if (linked2 != null) {
                return linked2.value;
            }
            return null;
        }
        Linked<AnnotatedParameter> linked3 = this._ctorParameters;
        if (linked3 != null) {
            return linked3.value;
        }
        Linked<AnnotatedMethod> linked4 = this._setters;
        if (linked4 != null) {
            return linked4.value;
        }
        Linked<AnnotatedField> linked5 = this._fields;
        if (linked5 != null) {
            return linked5.value;
        }
        Linked<AnnotatedMethod> linked6 = this._getters;
        if (linked6 != null) {
            return linked6.value;
        }
        return null;
    }

    protected int _getterPriority(AnnotatedMethod annotatedMethod) {
        String name = annotatedMethod.getName();
        if (!name.startsWith(MethodConst.GET_PREFIX) || name.length() <= 3) {
            return (!name.startsWith(MethodConst.IS_PREFIX) || name.length() <= 2) ? 3 : 2;
        }
        return 1;
    }

    protected int _setterPriority(AnnotatedMethod annotatedMethod) {
        String name = annotatedMethod.getName();
        return (!name.startsWith(MethodConst.SET_PREFIX) || name.length() <= 3) ? 2 : 1;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public Class<?>[] findViews() {
        return (Class[]) fromMemberAnnotations(new WithMember<Class<?>[]>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.1
            @Override // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.WithMember
            public Class<?>[] withMember(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.findViews(annotatedMember);
            }
        });
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotationIntrospector.ReferenceProperty findReferenceType() {
        AnnotationIntrospector.ReferenceProperty referenceProperty = this._referenceInfo;
        if (referenceProperty != null) {
            if (referenceProperty == NOT_REFEFERENCE_PROP) {
                return null;
            }
            return referenceProperty;
        }
        AnnotationIntrospector.ReferenceProperty referenceProperty2 = (AnnotationIntrospector.ReferenceProperty) fromMemberAnnotations(new WithMember<AnnotationIntrospector.ReferenceProperty>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.WithMember
            public AnnotationIntrospector.ReferenceProperty withMember(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.findReferenceType(annotatedMember);
            }
        });
        this._referenceInfo = referenceProperty2 == null ? NOT_REFEFERENCE_PROP : referenceProperty2;
        return referenceProperty2;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean isTypeId() {
        Boolean bool = (Boolean) fromMemberAnnotations(new WithMember<Boolean>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.WithMember
            public Boolean withMember(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.isTypeId(annotatedMember);
            }
        });
        return bool != null && bool.booleanValue();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public ObjectIdInfo findObjectIdInfo() {
        return (ObjectIdInfo) fromMemberAnnotations(new WithMember<ObjectIdInfo>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.WithMember
            public ObjectIdInfo withMember(AnnotatedMember annotatedMember) {
                ObjectIdInfo objectIdInfoFindObjectIdInfo = POJOPropertyBuilder.this._annotationIntrospector.findObjectIdInfo(annotatedMember);
                return objectIdInfoFindObjectIdInfo != null ? POJOPropertyBuilder.this._annotationIntrospector.findObjectReferenceInfo(annotatedMember, objectIdInfoFindObjectIdInfo) : objectIdInfoFindObjectIdInfo;
            }
        });
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public JsonInclude.Value findInclusion() {
        AnnotatedMember accessor = getAccessor();
        AnnotationIntrospector annotationIntrospector = this._annotationIntrospector;
        JsonInclude.Value valueFindPropertyInclusion = annotationIntrospector == null ? null : annotationIntrospector.findPropertyInclusion(accessor);
        return valueFindPropertyInclusion == null ? JsonInclude.Value.empty() : valueFindPropertyInclusion;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public List<PropertyName> findAliases() {
        List<PropertyName> listFindPropertyAliases;
        AnnotatedMember primaryMember = getPrimaryMember();
        return (primaryMember == null || (listFindPropertyAliases = this._annotationIntrospector.findPropertyAliases(primaryMember)) == null) ? Collections.emptyList() : listFindPropertyAliases;
    }

    public JsonProperty.Access findAccess() {
        return (JsonProperty.Access) fromMemberAnnotationsExcept(new WithMember<JsonProperty.Access>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.WithMember
            public JsonProperty.Access withMember(AnnotatedMember annotatedMember) {
                return POJOPropertyBuilder.this._annotationIntrospector.findPropertyAccess(annotatedMember);
            }
        }, JsonProperty.Access.AUTO);
    }

    public void addField(AnnotatedField annotatedField, PropertyName propertyName, boolean z, boolean z2, boolean z3) {
        this._fields = new Linked<>(annotatedField, this._fields, propertyName, z, z2, z3);
    }

    public void addCtor(AnnotatedParameter annotatedParameter, PropertyName propertyName, boolean z, boolean z2, boolean z3) {
        this._ctorParameters = new Linked<>(annotatedParameter, this._ctorParameters, propertyName, z, z2, z3);
    }

    public void addGetter(AnnotatedMethod annotatedMethod, PropertyName propertyName, boolean z, boolean z2, boolean z3) {
        this._getters = new Linked<>(annotatedMethod, this._getters, propertyName, z, z2, z3);
    }

    public void addSetter(AnnotatedMethod annotatedMethod, PropertyName propertyName, boolean z, boolean z2, boolean z3) {
        this._setters = new Linked<>(annotatedMethod, this._setters, propertyName, z, z2, z3);
    }

    public void addAll(POJOPropertyBuilder pOJOPropertyBuilder) {
        this._fields = merge(this._fields, pOJOPropertyBuilder._fields);
        this._ctorParameters = merge(this._ctorParameters, pOJOPropertyBuilder._ctorParameters);
        this._getters = merge(this._getters, pOJOPropertyBuilder._getters);
        this._setters = merge(this._setters, pOJOPropertyBuilder._setters);
    }

    private static <T> Linked<T> merge(Linked<T> linked, Linked<T> linked2) {
        return linked == null ? linked2 : linked2 == null ? linked : linked.append(linked2);
    }

    public void removeIgnored() {
        this._fields = _removeIgnored(this._fields);
        this._getters = _removeIgnored(this._getters);
        this._setters = _removeIgnored(this._setters);
        this._ctorParameters = _removeIgnored(this._ctorParameters);
    }

    @Deprecated
    public JsonProperty.Access removeNonVisible(boolean z) {
        return removeNonVisible(z, null);
    }

    public JsonProperty.Access removeNonVisible(boolean z, POJOPropertiesCollector pOJOPropertiesCollector) {
        JsonProperty.Access accessFindAccess = findAccess();
        if (accessFindAccess == null) {
            accessFindAccess = JsonProperty.Access.AUTO;
        }
        int i = C14456.$SwitchMap$com$fasterxml$jackson$annotation$JsonProperty$Access[accessFindAccess.ordinal()];
        if (i == 1) {
            if (pOJOPropertiesCollector != null) {
                pOJOPropertiesCollector._collectIgnorals(getName());
                Iterator<PropertyName> it = findExplicitNames().iterator();
                while (it.hasNext()) {
                    pOJOPropertiesCollector._collectIgnorals(it.next().getSimpleName());
                }
            }
            this._setters = null;
            this._ctorParameters = null;
            if (!this._forSerialization) {
                this._fields = null;
            }
        } else if (i != 2) {
            if (i == 3) {
                this._getters = null;
                if (this._forSerialization) {
                    this._fields = null;
                }
            } else {
                this._getters = _removeNonVisible(this._getters);
                this._ctorParameters = _removeNonVisible(this._ctorParameters);
                if (!z || this._getters == null) {
                    this._fields = _removeNonVisible(this._fields);
                    this._setters = _removeNonVisible(this._setters);
                }
            }
        }
        return accessFindAccess;
    }

    /* JADX INFO: renamed from: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder$6 */
    static /* synthetic */ class C14456 {
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$annotation$JsonProperty$Access;

        static {
            int[] iArr = new int[JsonProperty.Access.values().length];
            $SwitchMap$com$fasterxml$jackson$annotation$JsonProperty$Access = iArr;
            try {
                iArr[JsonProperty.Access.READ_ONLY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonProperty$Access[JsonProperty.Access.READ_WRITE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonProperty$Access[JsonProperty.Access.WRITE_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonProperty$Access[JsonProperty.Access.AUTO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void removeConstructors() {
        this._ctorParameters = null;
    }

    public void removeFields() {
        this._fields = null;
    }

    public void trimByVisibility() {
        this._fields = _trimByVisibility(this._fields);
        this._getters = _trimByVisibility(this._getters);
        this._setters = _trimByVisibility(this._setters);
        this._ctorParameters = _trimByVisibility(this._ctorParameters);
    }

    public void mergeAnnotations(boolean z) {
        if (z) {
            Linked<AnnotatedMethod> linked = this._getters;
            if (linked != null) {
                this._getters = _applyAnnotations(this._getters, _mergeAnnotations(0, linked, this._fields, this._ctorParameters, this._setters));
                return;
            }
            Linked<AnnotatedField> linked2 = this._fields;
            if (linked2 != null) {
                this._fields = _applyAnnotations(this._fields, _mergeAnnotations(0, linked2, this._ctorParameters, this._setters));
                return;
            }
            return;
        }
        Linked<AnnotatedParameter> linked3 = this._ctorParameters;
        if (linked3 != null) {
            this._ctorParameters = _applyAnnotations(this._ctorParameters, _mergeAnnotations(0, linked3, this._setters, this._fields, this._getters));
            return;
        }
        Linked<AnnotatedMethod> linked4 = this._setters;
        if (linked4 != null) {
            this._setters = _applyAnnotations(this._setters, _mergeAnnotations(0, linked4, this._fields, this._getters));
            return;
        }
        Linked<AnnotatedField> linked5 = this._fields;
        if (linked5 != null) {
            this._fields = _applyAnnotations(this._fields, _mergeAnnotations(0, linked5, this._getters));
        }
    }

    private AnnotationMap _mergeAnnotations(int i, Linked<? extends AnnotatedMember>... linkedArr) {
        AnnotationMap annotationMap_getAllAnnotations = _getAllAnnotations(linkedArr[i]);
        do {
            i++;
            if (i >= linkedArr.length) {
                return annotationMap_getAllAnnotations;
            }
        } while (linkedArr[i] == null);
        return AnnotationMap.merge(annotationMap_getAllAnnotations, _mergeAnnotations(i, linkedArr));
    }

    private <T extends AnnotatedMember> AnnotationMap _getAllAnnotations(Linked<T> linked) {
        AnnotationMap allAnnotations = linked.value.getAllAnnotations();
        return linked.next != null ? AnnotationMap.merge(allAnnotations, _getAllAnnotations(linked.next)) : allAnnotations;
    }

    private <T extends AnnotatedMember> Linked<T> _applyAnnotations(Linked<T> linked, AnnotationMap annotationMap) {
        AnnotatedMember annotatedMember = (AnnotatedMember) linked.value.withAnnotations(annotationMap);
        Linked<T> linked2 = linked.next;
        Linked linkedWithNext = linked;
        if (linked2 != null) {
            linkedWithNext = linked.withNext(_applyAnnotations(linked.next, annotationMap));
        }
        return linkedWithNext.withValue(annotatedMember);
    }

    private <T> Linked<T> _removeIgnored(Linked<T> linked) {
        return linked == null ? linked : linked.withoutIgnored();
    }

    private <T> Linked<T> _removeNonVisible(Linked<T> linked) {
        return linked == null ? linked : linked.withoutNonVisible();
    }

    private <T> Linked<T> _trimByVisibility(Linked<T> linked) {
        return linked == null ? linked : linked.trimByVisibility();
    }

    private <T> boolean _anyExplicits(Linked<T> linked) {
        while (linked != null) {
            if (linked.name != null && linked.name.hasSimpleName()) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    private <T> boolean _anyExplicitNames(Linked<T> linked) {
        while (linked != null) {
            if (linked.name != null && linked.isNameExplicit) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    public boolean anyVisible() {
        return _anyVisible(this._fields) || _anyVisible(this._getters) || _anyVisible(this._setters) || _anyVisible(this._ctorParameters);
    }

    private <T> boolean _anyVisible(Linked<T> linked) {
        while (linked != null) {
            if (linked.isVisible) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    public boolean anyIgnorals() {
        return _anyIgnorals(this._fields) || _anyIgnorals(this._getters) || _anyIgnorals(this._setters) || _anyIgnorals(this._ctorParameters);
    }

    private <T> boolean _anyIgnorals(Linked<T> linked) {
        while (linked != null) {
            if (linked.isMarkedIgnored) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    public boolean anyExplicitsWithoutIgnoral() {
        return _anyExplicitsWithoutIgnoral(this._fields) || _anyExplicitsWithoutIgnoral(this._getters) || _anyExplicitsWithoutIgnoral(this._setters) || _anyExplicitNamesWithoutIgnoral(this._ctorParameters);
    }

    private <T> boolean _anyExplicitsWithoutIgnoral(Linked<T> linked) {
        while (linked != null) {
            if (!linked.isMarkedIgnored && linked.name != null && linked.name.hasSimpleName()) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    private <T> boolean _anyExplicitNamesWithoutIgnoral(Linked<T> linked) {
        while (linked != null) {
            if (!linked.isMarkedIgnored && linked.name != null && linked.isNameExplicit) {
                return true;
            }
            linked = linked.next;
        }
        return false;
    }

    public Set<PropertyName> findExplicitNames() {
        Set<PropertyName> set_findExplicitNames = _findExplicitNames(this._ctorParameters, _findExplicitNames(this._setters, _findExplicitNames(this._getters, _findExplicitNames(this._fields, null))));
        return set_findExplicitNames == null ? Collections.emptySet() : set_findExplicitNames;
    }

    public boolean hasExplicitName(PropertyName propertyName) {
        return _hasExplicitName(this._fields, propertyName) || _hasExplicitName(this._getters, propertyName) || _hasExplicitName(this._setters, propertyName) || _hasExplicitName(this._ctorParameters, propertyName);
    }

    private boolean _hasExplicitName(Linked<? extends AnnotatedMember> linked, PropertyName propertyName) {
        for (Linked linked2 = linked; linked2 != null; linked2 = linked2.next) {
            if (linked2.isNameExplicit && propertyName.equals(linked2.name)) {
                return true;
            }
        }
        return false;
    }

    public Collection<POJOPropertyBuilder> explode(Collection<PropertyName> collection) {
        HashMap map = new HashMap();
        _explode(collection, map, this._fields);
        _explode(collection, map, this._getters);
        _explode(collection, map, this._setters);
        _explode(collection, map, this._ctorParameters);
        return map.values();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r8v0 ??, still in use, count: 1, list:
          (r8v0 ?? I:java.lang.Object) from 0x0023: INVOKE (r11v0 ?? I:java.util.Map), (r7v0 ?? I:java.lang.Object), (r8v0 ?? I:java.lang.Object) INTERFACE call: java.util.Map.put(java.lang.Object, java.lang.Object):java.lang.Object A[MD:(K, V):V (c)] (LINE:1293)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    private void _explode(
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r8v0 ??, still in use, count: 1, list:
          (r8v0 ?? I:java.lang.Object) from 0x0023: INVOKE (r11v0 ?? I:java.util.Map), (r7v0 ?? I:java.lang.Object), (r8v0 ?? I:java.lang.Object) INTERFACE call: java.util.Map.put(java.lang.Object, java.lang.Object):java.lang.Object A[MD:(K, V):V (c)] (LINE:1293)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r10v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:236)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:224)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:169)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:407)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:337)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:303)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
        */

    private Set<PropertyName> _findExplicitNames(Linked<? extends AnnotatedMember> linked, Set<PropertyName> set) {
        for (Linked linked2 = linked; linked2 != null; linked2 = linked2.next) {
            if (linked2.isNameExplicit && linked2.name != null) {
                if (set == null) {
                    set = new HashSet<>();
                }
                set.add(linked2.name);
            }
        }
        return set;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[Property '");
        sb.append(this._name).append("'; ctors: ").append(this._ctorParameters).append(", field(s): ").append(this._fields).append(", getter(s): ").append(this._getters).append(", setter(s): ").append(this._setters);
        sb.append("]");
        return sb.toString();
    }

    protected <T> T fromMemberAnnotations(WithMember<T> withMember) {
        Linked<AnnotatedMethod> linked;
        Linked<AnnotatedField> linked2;
        if (this._annotationIntrospector == null) {
            return null;
        }
        if (this._forSerialization) {
            Linked<AnnotatedMethod> linked3 = this._getters;
            if (linked3 != null) {
                tWithMember = withMember.withMember(linked3.value);
            }
        } else {
            Linked<AnnotatedParameter> linked4 = this._ctorParameters;
            tWithMember = linked4 != null ? withMember.withMember(linked4.value) : null;
            if (tWithMember == null && (linked = this._setters) != null) {
                tWithMember = withMember.withMember(linked.value);
            }
        }
        return (tWithMember != null || (linked2 = this._fields) == null) ? tWithMember : withMember.withMember(linked2.value);
    }

    protected <T> T fromMemberAnnotationsExcept(WithMember<T> withMember, T t) {
        T tWithMember;
        T tWithMember2;
        T tWithMember3;
        T tWithMember4;
        T tWithMember5;
        T tWithMember6;
        T tWithMember7;
        T tWithMember8;
        if (this._annotationIntrospector == null) {
            return null;
        }
        if (this._forSerialization) {
            Linked<AnnotatedMethod> linked = this._getters;
            if (linked != null && (tWithMember8 = withMember.withMember(linked.value)) != null && tWithMember8 != t) {
                return tWithMember8;
            }
            Linked<AnnotatedField> linked2 = this._fields;
            if (linked2 != null && (tWithMember7 = withMember.withMember(linked2.value)) != null && tWithMember7 != t) {
                return tWithMember7;
            }
            Linked<AnnotatedParameter> linked3 = this._ctorParameters;
            if (linked3 != null && (tWithMember6 = withMember.withMember(linked3.value)) != null && tWithMember6 != t) {
                return tWithMember6;
            }
            Linked<AnnotatedMethod> linked4 = this._setters;
            if (linked4 == null || (tWithMember5 = withMember.withMember(linked4.value)) == null || tWithMember5 == t) {
                return null;
            }
            return tWithMember5;
        }
        Linked<AnnotatedParameter> linked5 = this._ctorParameters;
        if (linked5 != null && (tWithMember4 = withMember.withMember(linked5.value)) != null && tWithMember4 != t) {
            return tWithMember4;
        }
        Linked<AnnotatedMethod> linked6 = this._setters;
        if (linked6 != null && (tWithMember3 = withMember.withMember(linked6.value)) != null && tWithMember3 != t) {
            return tWithMember3;
        }
        Linked<AnnotatedField> linked7 = this._fields;
        if (linked7 != null && (tWithMember2 = withMember.withMember(linked7.value)) != null && tWithMember2 != t) {
            return tWithMember2;
        }
        Linked<AnnotatedMethod> linked8 = this._getters;
        if (linked8 == null || (tWithMember = withMember.withMember(linked8.value)) == null || tWithMember == t) {
            return null;
        }
        return tWithMember;
    }

    protected Class<?> _rawTypeOf(AnnotatedMember annotatedMember) {
        if (annotatedMember instanceof AnnotatedMethod) {
            AnnotatedMethod annotatedMethod = (AnnotatedMethod) annotatedMember;
            if (annotatedMethod.getParameterCount() > 0) {
                return annotatedMethod.getParameterType(0).getRawClass();
            }
        }
        return annotatedMember.getType().getRawClass();
    }

    protected static class MemberIterator<T extends AnnotatedMember> implements Iterator<T> {
        private Linked<T> next;

        public MemberIterator(Linked<T> linked) {
            this.next = linked;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.next != null;
        }

        @Override // java.util.Iterator
        public T next() {
            Linked<T> linked = this.next;
            if (linked == null) {
                throw new NoSuchElementException();
            }
            T t = linked.value;
            this.next = this.next.next;
            return t;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    protected static final class Linked<T> {
        public final boolean isMarkedIgnored;
        public final boolean isNameExplicit;
        public final boolean isVisible;
        public final PropertyName name;
        public final Linked<T> next;
        public final T value;

        public Linked(T t, Linked<T> linked, PropertyName propertyName, boolean z, boolean z2, boolean z3) {
            this.value = t;
            this.next = linked;
            PropertyName propertyName2 = (propertyName == null || propertyName.isEmpty()) ? null : propertyName;
            this.name = propertyName2;
            if (z) {
                if (propertyName2 == null) {
                    throw new IllegalArgumentException("Cannot pass true for 'explName' if name is null/empty");
                }
                if (!propertyName.hasSimpleName()) {
                    z = false;
                }
            }
            this.isNameExplicit = z;
            this.isVisible = z2;
            this.isMarkedIgnored = z3;
        }

        public Linked<T> withoutNext() {
            return this.next == null ? this : new Linked<>(this.value, null, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withValue(T t) {
            return t == this.value ? this : new Linked<>(t, this.next, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withNext(Linked<T> linked) {
            return linked == this.next ? this : new Linked<>(this.value, linked, this.name, this.isNameExplicit, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withoutIgnored() {
            Linked<T> linkedWithoutIgnored;
            if (this.isMarkedIgnored) {
                Linked<T> linked = this.next;
                if (linked == null) {
                    return null;
                }
                return linked.withoutIgnored();
            }
            Linked<T> linked2 = this.next;
            return (linked2 == null || (linkedWithoutIgnored = linked2.withoutIgnored()) == this.next) ? this : withNext(linkedWithoutIgnored);
        }

        public Linked<T> withoutNonVisible() {
            Linked<T> linked = this.next;
            Linked<T> linkedWithoutNonVisible = linked == null ? null : linked.withoutNonVisible();
            return this.isVisible ? withNext(linkedWithoutNonVisible) : linkedWithoutNonVisible;
        }

        protected Linked<T> append(Linked<T> linked) {
            Linked<T> linked2 = this.next;
            if (linked2 == null) {
                return withNext(linked);
            }
            return withNext(linked2.append(linked));
        }

        public Linked<T> trimByVisibility() {
            Linked<T> linked = this.next;
            if (linked == null) {
                return this;
            }
            Linked<T> linkedTrimByVisibility = linked.trimByVisibility();
            if (this.name != null) {
                if (linkedTrimByVisibility.name == null) {
                    return withNext(null);
                }
                return withNext(linkedTrimByVisibility);
            }
            if (linkedTrimByVisibility.name != null) {
                return linkedTrimByVisibility;
            }
            boolean z = this.isVisible;
            if (z == linkedTrimByVisibility.isVisible) {
                return withNext(linkedTrimByVisibility);
            }
            return z ? withNext(null) : linkedTrimByVisibility;
        }

        public String toString() {
            String str = String.format("%s[visible=%b,ignore=%b,explicitName=%b]", this.value.toString(), Boolean.valueOf(this.isVisible), Boolean.valueOf(this.isMarkedIgnored), Boolean.valueOf(this.isNameExplicit));
            return this.next != null ? str + ", " + this.next.toString() : str;
        }
    }
}
