package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaUtilCollectionsDeserializers {
    private static final String PREFIX_JAVA_UTIL_ARRAYS = "java.util.Arrays$";
    private static final String PREFIX_JAVA_UTIL_COLLECTIONS = "java.util.Collections$";
    private static final String PREFIX_JAVA_UTIL_IMMUTABLE_COLL = "java.util.ImmutableCollections$";
    public static final int TYPE_AS_LIST = 11;
    private static final int TYPE_SINGLETON_LIST = 2;
    private static final int TYPE_SINGLETON_MAP = 3;
    private static final int TYPE_SINGLETON_SET = 1;
    private static final int TYPE_SYNC_COLLECTION = 8;
    private static final int TYPE_SYNC_LIST = 9;
    private static final int TYPE_SYNC_MAP = 10;
    private static final int TYPE_SYNC_SET = 7;
    private static final int TYPE_UNMODIFIABLE_LIST = 5;
    private static final int TYPE_UNMODIFIABLE_MAP = 6;
    private static final int TYPE_UNMODIFIABLE_SET = 4;

    /* JADX WARN: Removed duplicated region for block: B:34:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.fasterxml.jackson.databind.JsonDeserializer<?> findForCollection(com.fasterxml.jackson.databind.DeserializationContext r6, com.fasterxml.jackson.databind.JavaType r7) throws com.fasterxml.jackson.databind.JsonMappingException {
        /*
            Method dump skipped, instruction units count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.deser.impl.JavaUtilCollectionsDeserializers.findForCollection(com.fasterxml.jackson.databind.DeserializationContext, com.fasterxml.jackson.databind.JavaType):com.fasterxml.jackson.databind.JsonDeserializer");
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0061  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.fasterxml.jackson.databind.JsonDeserializer<?> findForMap(com.fasterxml.jackson.databind.DeserializationContext r4, com.fasterxml.jackson.databind.JavaType r5) throws com.fasterxml.jackson.databind.JsonMappingException {
        /*
            java.lang.Class r4 = r5.getRawClass()
            java.lang.String r4 = r4.getName()
            java.lang.String r0 = _findUtilCollectionsTypeName(r4)
            r1 = 6
            java.lang.String r2 = "Map"
            r3 = 0
            if (r0 == 0) goto L4e
            java.lang.String r4 = _findUnmodifiableTypeName(r0)
            if (r4 == 0) goto L25
            boolean r4 = r4.contains(r2)
            if (r4 == 0) goto L61
            java.lang.Class<java.util.Map> r4 = java.util.Map.class
            com.fasterxml.jackson.databind.deser.impl.JavaUtilCollectionsDeserializers$JavaUtilCollectionsConverter r4 = converter(r1, r5, r4)
            goto L62
        L25:
            java.lang.String r4 = _findSingletonTypeName(r0)
            if (r4 == 0) goto L39
            boolean r4 = r4.contains(r2)
            if (r4 == 0) goto L61
            r4 = 3
            java.lang.Class<java.util.Map> r0 = java.util.Map.class
            com.fasterxml.jackson.databind.deser.impl.JavaUtilCollectionsDeserializers$JavaUtilCollectionsConverter r4 = converter(r4, r5, r0)
            goto L62
        L39:
            java.lang.String r4 = _findSyncTypeName(r0)
            if (r4 == 0) goto L61
            boolean r4 = r4.contains(r2)
            if (r4 == 0) goto L61
            r4 = 10
            java.lang.Class<java.util.Map> r0 = java.util.Map.class
            com.fasterxml.jackson.databind.deser.impl.JavaUtilCollectionsDeserializers$JavaUtilCollectionsConverter r4 = converter(r4, r5, r0)
            goto L62
        L4e:
            java.lang.String r4 = _findUtilCollectionsImmutableTypeName(r4)
            if (r4 == 0) goto L61
            boolean r4 = r4.contains(r2)
            if (r4 == 0) goto L61
            java.lang.Class<java.util.Map> r4 = java.util.Map.class
            com.fasterxml.jackson.databind.deser.impl.JavaUtilCollectionsDeserializers$JavaUtilCollectionsConverter r4 = converter(r1, r5, r4)
            goto L62
        L61:
            r4 = r3
        L62:
            if (r4 != 0) goto L65
            goto L6a
        L65:
            com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer r3 = new com.fasterxml.jackson.databind.deser.std.StdDelegatingDeserializer
            r3.<init>(r4)
        L6a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.deser.impl.JavaUtilCollectionsDeserializers.findForMap(com.fasterxml.jackson.databind.DeserializationContext, com.fasterxml.jackson.databind.JavaType):com.fasterxml.jackson.databind.JsonDeserializer");
    }

    static JavaUtilCollectionsConverter converter(int i, JavaType javaType, Class<?> cls) {
        return new JavaUtilCollectionsConverter(i, javaType.findSuperType(cls));
    }

    private static String _findUtilArrayTypeName(String str) {
        if (str.startsWith(PREFIX_JAVA_UTIL_ARRAYS)) {
            return str.substring(PREFIX_JAVA_UTIL_ARRAYS.length());
        }
        return null;
    }

    private static String _findUtilCollectionsTypeName(String str) {
        if (str.startsWith(PREFIX_JAVA_UTIL_COLLECTIONS)) {
            return str.substring(PREFIX_JAVA_UTIL_COLLECTIONS.length());
        }
        return null;
    }

    private static String _findUtilCollectionsImmutableTypeName(String str) {
        if (str.startsWith(PREFIX_JAVA_UTIL_IMMUTABLE_COLL)) {
            return str.substring(PREFIX_JAVA_UTIL_IMMUTABLE_COLL.length());
        }
        return null;
    }

    private static String _findSingletonTypeName(String str) {
        if (str.startsWith("Singleton")) {
            return str.substring(9);
        }
        return null;
    }

    private static String _findSyncTypeName(String str) {
        if (str.startsWith("Synchronized")) {
            return str.substring(12);
        }
        return null;
    }

    private static String _findUnmodifiableTypeName(String str) {
        if (str.startsWith("Unmodifiable")) {
            return str.substring(12);
        }
        return null;
    }

    private static class JavaUtilCollectionsConverter implements Converter<Object, Object> {
        private final JavaType _inputType;
        private final int _kind;

        JavaUtilCollectionsConverter(int i, JavaType javaType) {
            this._inputType = javaType;
            this._kind = i;
        }

        @Override // com.fasterxml.jackson.databind.util.Converter
        public Object convert(Object obj) {
            if (obj == null) {
                return null;
            }
            switch (this._kind) {
                case 1:
                    Set set = (Set) obj;
                    _checkSingleton(set.size());
                    return Collections.singleton(set.iterator().next());
                case 2:
                    List list = (List) obj;
                    _checkSingleton(list.size());
                    return Collections.singletonList(list.get(0));
                case 3:
                    Map map = (Map) obj;
                    _checkSingleton(map.size());
                    Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
                    return Collections.singletonMap(entry.getKey(), entry.getValue());
                case 4:
                    return Collections.unmodifiableSet((Set) obj);
                case 5:
                    return Collections.unmodifiableList((List) obj);
                case 6:
                    return Collections.unmodifiableMap((Map) obj);
                case 7:
                    return Collections.synchronizedSet((Set) obj);
                case 8:
                    return Collections.synchronizedCollection((Collection) obj);
                case 9:
                    return Collections.synchronizedList((List) obj);
                case 10:
                    return Collections.synchronizedMap((Map) obj);
                default:
                    return obj;
            }
        }

        @Override // com.fasterxml.jackson.databind.util.Converter
        public JavaType getInputType(TypeFactory typeFactory) {
            return this._inputType;
        }

        @Override // com.fasterxml.jackson.databind.util.Converter
        public JavaType getOutputType(TypeFactory typeFactory) {
            return this._inputType;
        }

        private void _checkSingleton(int i) {
            if (i != 1) {
                throw new IllegalArgumentException("Can not deserialize Singleton container from " + i + " entries");
            }
        }
    }
}
