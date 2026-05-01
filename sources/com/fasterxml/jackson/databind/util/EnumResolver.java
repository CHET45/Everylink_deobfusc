package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.EnumNamingStrategy;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class EnumResolver implements Serializable {
    private static final long serialVersionUID = 1;
    protected final Enum<?> _defaultValue;
    protected final Class<Enum<?>> _enumClass;
    protected final Enum<?>[] _enums;
    protected final HashMap<String, Enum<?>> _enumsById;
    protected final boolean _isFromIntValue;
    protected final boolean _isIgnoreCase;

    /* JADX WARN: Multi-variable type inference failed */
    protected static Class<Enum<?>> _enumClass(Class<?> cls) {
        return cls;
    }

    protected EnumResolver(Class<Enum<?>> cls, Enum<?>[] enumArr, HashMap<String, Enum<?>> map, Enum<?> r4, boolean z, boolean z2) {
        this._enumClass = cls;
        this._enums = enumArr;
        this._enumsById = map;
        this._defaultValue = r4;
        this._isIgnoreCase = z;
        this._isFromIntValue = z2;
    }

    public static EnumResolver constructFor(DeserializationConfig deserializationConfig, AnnotatedClass annotatedClass) {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        boolean zIsEnabled = deserializationConfig.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        Class<?> rawType = annotatedClass.getRawType();
        Class<Enum<?>> cls_enumClass = _enumClass(rawType);
        Enum<?>[] enumArr_enumConstants = _enumConstants(rawType);
        String[] strArrFindEnumValues = annotationIntrospector.findEnumValues(deserializationConfig, annotatedClass, enumArr_enumConstants, new String[enumArr_enumConstants.length]);
        String[][] strArr = new String[strArrFindEnumValues.length][];
        annotationIntrospector.findEnumAliases(deserializationConfig, annotatedClass, enumArr_enumConstants, strArr);
        HashMap map = new HashMap();
        int length = enumArr_enumConstants.length;
        for (int i = 0; i < length; i++) {
            Enum<?> r9 = enumArr_enumConstants[i];
            String strName = strArrFindEnumValues[i];
            if (strName == null) {
                strName = r9.name();
            }
            map.put(strName, r9);
            String[] strArr2 = strArr[i];
            if (strArr2 != null) {
                for (String str : strArr2) {
                    map.putIfAbsent(str, r9);
                }
            }
        }
        return new EnumResolver(cls_enumClass, enumArr_enumConstants, map, _enumDefault(annotationIntrospector, annotatedClass, enumArr_enumConstants), zIsEnabled, false);
    }

    @Deprecated
    public static EnumResolver constructFor(DeserializationConfig deserializationConfig, Class<?> cls) {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        boolean zIsEnabled = deserializationConfig.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        Class<Enum<?>> cls_enumClass = _enumClass(cls);
        Enum<?>[] enumArr_enumConstants = _enumConstants(cls);
        String[] strArrFindEnumValues = annotationIntrospector.findEnumValues(cls_enumClass, enumArr_enumConstants, new String[enumArr_enumConstants.length]);
        String[][] strArr = new String[strArrFindEnumValues.length][];
        annotationIntrospector.findEnumAliases(cls_enumClass, enumArr_enumConstants, strArr);
        HashMap map = new HashMap();
        int length = enumArr_enumConstants.length;
        for (int i = 0; i < length; i++) {
            Enum<?> r8 = enumArr_enumConstants[i];
            String strName = strArrFindEnumValues[i];
            if (strName == null) {
                strName = r8.name();
            }
            map.put(strName, r8);
            String[] strArr2 = strArr[i];
            if (strArr2 != null) {
                for (String str : strArr2) {
                    map.putIfAbsent(str, r8);
                }
            }
        }
        return new EnumResolver(cls_enumClass, enumArr_enumConstants, map, _enumDefault(annotationIntrospector, cls_enumClass), zIsEnabled, false);
    }

    public static EnumResolver constructUsingToString(DeserializationConfig deserializationConfig, AnnotatedClass annotatedClass) {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        boolean zIsEnabled = deserializationConfig.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        Class<?> rawType = annotatedClass.getRawType();
        Class<Enum<?>> cls_enumClass = _enumClass(rawType);
        Enum<?>[] enumArr_enumConstants = _enumConstants(rawType);
        String[] strArr = new String[enumArr_enumConstants.length];
        String[][] strArr2 = new String[enumArr_enumConstants.length][];
        if (annotationIntrospector != null) {
            annotationIntrospector.findEnumValues(deserializationConfig, annotatedClass, enumArr_enumConstants, strArr);
            annotationIntrospector.findEnumAliases(deserializationConfig, annotatedClass, enumArr_enumConstants, strArr2);
        }
        HashMap map = new HashMap();
        int length = enumArr_enumConstants.length;
        while (true) {
            length--;
            if (length >= 0) {
                Enum<?> r6 = enumArr_enumConstants[length];
                String string = strArr[length];
                if (string == null) {
                    string = r6.toString();
                }
                map.put(string, r6);
                String[] strArr3 = strArr2[length];
                if (strArr3 != null) {
                    for (String str : strArr3) {
                        map.putIfAbsent(str, r6);
                    }
                }
            } else {
                return new EnumResolver(cls_enumClass, enumArr_enumConstants, map, _enumDefault(annotationIntrospector, annotatedClass, enumArr_enumConstants), zIsEnabled, false);
            }
        }
    }

    @Deprecated
    public static EnumResolver constructUsingToString(DeserializationConfig deserializationConfig, Class<?> cls) {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        boolean zIsEnabled = deserializationConfig.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        Class<Enum<?>> cls_enumClass = _enumClass(cls);
        Enum<?>[] enumArr_enumConstants = _enumConstants(cls);
        HashMap map = new HashMap();
        String[][] strArr = new String[enumArr_enumConstants.length][];
        if (annotationIntrospector != null) {
            annotationIntrospector.findEnumAliases(cls_enumClass, enumArr_enumConstants, strArr);
        }
        int length = enumArr_enumConstants.length;
        while (true) {
            length--;
            if (length >= 0) {
                Enum<?> r1 = enumArr_enumConstants[length];
                map.put(r1.toString(), r1);
                String[] strArr2 = strArr[length];
                if (strArr2 != null) {
                    for (String str : strArr2) {
                        map.putIfAbsent(str, r1);
                    }
                }
            } else {
                return new EnumResolver(cls_enumClass, enumArr_enumConstants, map, _enumDefault(annotationIntrospector, cls_enumClass), zIsEnabled, false);
            }
        }
    }

    public static EnumResolver constructUsingIndex(DeserializationConfig deserializationConfig, AnnotatedClass annotatedClass) {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        boolean zIsEnabled = deserializationConfig.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        Class<?> rawType = annotatedClass.getRawType();
        Class<Enum<?>> cls_enumClass = _enumClass(rawType);
        Enum<?>[] enumArr_enumConstants = _enumConstants(rawType);
        HashMap map = new HashMap();
        int length = enumArr_enumConstants.length;
        while (true) {
            length--;
            if (length >= 0) {
                map.put(String.valueOf(length), enumArr_enumConstants[length]);
            } else {
                return new EnumResolver(cls_enumClass, enumArr_enumConstants, map, _enumDefault(annotationIntrospector, annotatedClass, enumArr_enumConstants), zIsEnabled, false);
            }
        }
    }

    @Deprecated
    public static EnumResolver constructUsingIndex(DeserializationConfig deserializationConfig, Class<Enum<?>> cls) {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        boolean zIsEnabled = deserializationConfig.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        Class<Enum<?>> cls_enumClass = _enumClass(cls);
        Enum<?>[] enumArr_enumConstants = _enumConstants(cls);
        HashMap map = new HashMap();
        int length = enumArr_enumConstants.length;
        while (true) {
            length--;
            if (length >= 0) {
                map.put(String.valueOf(length), enumArr_enumConstants[length]);
            } else {
                return new EnumResolver(cls_enumClass, enumArr_enumConstants, map, _enumDefault(annotationIntrospector, cls_enumClass), zIsEnabled, false);
            }
        }
    }

    @Deprecated
    public static EnumResolver constructUsingEnumNamingStrategy(DeserializationConfig deserializationConfig, Class<?> cls, EnumNamingStrategy enumNamingStrategy) {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        boolean zIsEnabled = deserializationConfig.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        Class<Enum<?>> cls_enumClass = _enumClass(cls);
        Enum<?>[] enumArr_enumConstants = _enumConstants(cls);
        HashMap map = new HashMap();
        String[] strArr = new String[enumArr_enumConstants.length];
        String[][] strArr2 = new String[enumArr_enumConstants.length][];
        if (annotationIntrospector != null) {
            annotationIntrospector.findEnumValues(cls_enumClass, enumArr_enumConstants, strArr);
            annotationIntrospector.findEnumAliases(cls_enumClass, enumArr_enumConstants, strArr2);
        }
        int length = enumArr_enumConstants.length;
        while (true) {
            length--;
            if (length >= 0) {
                Enum<?> r2 = enumArr_enumConstants[length];
                String strConvertEnumToExternalName = strArr[length];
                if (strConvertEnumToExternalName == null) {
                    strConvertEnumToExternalName = enumNamingStrategy.convertEnumToExternalName(r2.name());
                }
                map.put(strConvertEnumToExternalName, r2);
                String[] strArr3 = strArr2[length];
                if (strArr3 != null) {
                    for (String str : strArr3) {
                        map.putIfAbsent(str, r2);
                    }
                }
            } else {
                return new EnumResolver(cls_enumClass, enumArr_enumConstants, map, _enumDefault(annotationIntrospector, cls_enumClass), zIsEnabled, false);
            }
        }
    }

    public static EnumResolver constructUsingEnumNamingStrategy(DeserializationConfig deserializationConfig, AnnotatedClass annotatedClass, EnumNamingStrategy enumNamingStrategy) {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        boolean zIsEnabled = deserializationConfig.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        Class<?> rawType = annotatedClass.getRawType();
        Class<Enum<?>> cls_enumClass = _enumClass(rawType);
        Enum<?>[] enumArr_enumConstants = _enumConstants(rawType);
        String[] strArr = new String[enumArr_enumConstants.length];
        String[][] strArr2 = new String[enumArr_enumConstants.length][];
        if (annotationIntrospector != null) {
            annotationIntrospector.findEnumValues(deserializationConfig, annotatedClass, enumArr_enumConstants, strArr);
            annotationIntrospector.findEnumAliases(deserializationConfig, annotatedClass, enumArr_enumConstants, strArr2);
        }
        HashMap map = new HashMap();
        int length = enumArr_enumConstants.length;
        while (true) {
            length--;
            if (length >= 0) {
                Enum<?> r6 = enumArr_enumConstants[length];
                String strConvertEnumToExternalName = strArr[length];
                if (strConvertEnumToExternalName == null) {
                    strConvertEnumToExternalName = enumNamingStrategy.convertEnumToExternalName(r6.name());
                }
                map.put(strConvertEnumToExternalName, r6);
                String[] strArr3 = strArr2[length];
                if (strArr3 != null) {
                    for (String str : strArr3) {
                        map.putIfAbsent(str, r6);
                    }
                }
            } else {
                return new EnumResolver(cls_enumClass, enumArr_enumConstants, map, _enumDefault(annotationIntrospector, annotatedClass, enumArr_enumConstants), zIsEnabled, false);
            }
        }
    }

    @Deprecated
    public static EnumResolver constructUsingMethod(DeserializationConfig deserializationConfig, Class<?> cls, AnnotatedMember annotatedMember) {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        boolean zIsEnabled = deserializationConfig.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        Class<Enum<?>> cls_enumClass = _enumClass(cls);
        Enum<?>[] enumArr_enumConstants = _enumConstants(cls);
        HashMap map = new HashMap();
        int length = enumArr_enumConstants.length;
        while (true) {
            length--;
            if (length >= 0) {
                Enum<?> r10 = enumArr_enumConstants[length];
                try {
                    Object value = annotatedMember.getValue(r10);
                    if (value != null) {
                        map.put(value.toString(), r10);
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to access @JsonValue of Enum value " + r10 + ": " + e.getMessage());
                }
            } else {
                return new EnumResolver(cls_enumClass, enumArr_enumConstants, map, _enumDefault(annotationIntrospector, cls_enumClass), zIsEnabled, _isIntType(annotatedMember.getRawType()));
            }
        }
    }

    public static EnumResolver constructUsingMethod(DeserializationConfig deserializationConfig, AnnotatedClass annotatedClass, AnnotatedMember annotatedMember) {
        AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
        boolean zIsEnabled = deserializationConfig.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        Class<?> rawType = annotatedClass.getRawType();
        Class<Enum<?>> cls_enumClass = _enumClass(rawType);
        Enum<?>[] enumArr_enumConstants = _enumConstants(rawType);
        HashMap map = new HashMap();
        int length = enumArr_enumConstants.length;
        while (true) {
            length--;
            if (length >= 0) {
                Enum<?> r1 = enumArr_enumConstants[length];
                try {
                    Object value = annotatedMember.getValue(r1);
                    if (value != null) {
                        map.put(value.toString(), r1);
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to access @JsonValue of Enum value " + r1 + ": " + e.getMessage());
                }
            } else {
                return new EnumResolver(cls_enumClass, enumArr_enumConstants, map, _enumDefault(annotationIntrospector, annotatedClass, enumArr_enumConstants), zIsEnabled, _isIntType(annotatedMember.getRawType()));
            }
        }
    }

    public CompactStringObjectMap constructLookup() {
        return CompactStringObjectMap.construct(this._enumsById);
    }

    protected static Enum<?>[] _enumConstants(Class<?> cls) {
        Enum<?>[] enumConstants = _enumClass(cls).getEnumConstants();
        if (enumConstants != null) {
            return enumConstants;
        }
        throw new IllegalArgumentException("No enum constants for class " + cls.getName());
    }

    protected static Enum<?> _enumDefault(AnnotationIntrospector annotationIntrospector, AnnotatedClass annotatedClass, Enum<?>[] enumArr) {
        if (annotationIntrospector != null) {
            return annotationIntrospector.findDefaultEnumValue(annotatedClass, enumArr);
        }
        return null;
    }

    @Deprecated
    protected static Enum<?> _enumDefault(AnnotationIntrospector annotationIntrospector, Class<?> cls) {
        if (annotationIntrospector != null) {
            return annotationIntrospector.findDefaultEnumValue(_enumClass(cls));
        }
        return null;
    }

    protected static boolean _isIntType(Class<?> cls) {
        if (cls.isPrimitive()) {
            cls = ClassUtil.wrapperType(cls);
        }
        return cls == Long.class || cls == Integer.class || cls == Short.class || cls == Byte.class;
    }

    public Enum<?> findEnum(String str) {
        Enum<?> r0 = this._enumsById.get(str);
        return (r0 == null && this._isIgnoreCase) ? _findEnumCaseInsensitive(str) : r0;
    }

    protected Enum<?> _findEnumCaseInsensitive(String str) {
        for (Map.Entry<String, Enum<?>> entry : this._enumsById.entrySet()) {
            if (str.equalsIgnoreCase(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Enum<?> getEnum(int i) {
        if (i < 0) {
            return null;
        }
        Enum<?>[] enumArr = this._enums;
        if (i >= enumArr.length) {
            return null;
        }
        return enumArr[i];
    }

    public Enum<?> getDefaultValue() {
        return this._defaultValue;
    }

    public Enum<?>[] getRawEnums() {
        return this._enums;
    }

    public List<Enum<?>> getEnums() {
        ArrayList arrayList = new ArrayList(this._enums.length);
        for (Enum<?> r0 : this._enums) {
            arrayList.add(r0);
        }
        return arrayList;
    }

    public Collection<String> getEnumIds() {
        return this._enumsById.keySet();
    }

    public Class<Enum<?>> getEnumClass() {
        return this._enumClass;
    }

    public int lastValidIndex() {
        return this._enums.length - 1;
    }

    public boolean isFromIntValue() {
        return this._isFromIntValue;
    }
}
