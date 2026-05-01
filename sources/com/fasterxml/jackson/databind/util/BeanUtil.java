package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.github.houbb.heaven.constant.MethodConst;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/* JADX INFO: loaded from: classes3.dex */
public class BeanUtil {
    @Deprecated
    public static String okNameForGetter(AnnotatedMethod annotatedMethod, boolean z) {
        String name = annotatedMethod.getName();
        String strOkNameForIsGetter = okNameForIsGetter(annotatedMethod, name, z);
        return strOkNameForIsGetter == null ? okNameForRegularGetter(annotatedMethod, name, z) : strOkNameForIsGetter;
    }

    @Deprecated
    public static String okNameForRegularGetter(AnnotatedMethod annotatedMethod, String str, boolean z) {
        if (!str.startsWith(MethodConst.GET_PREFIX)) {
            return null;
        }
        if ("getCallbacks".equals(str)) {
            if (isCglibGetCallbacks(annotatedMethod)) {
                return null;
            }
        } else if ("getMetaClass".equals(str) && isGroovyMetaClassGetter(annotatedMethod)) {
            return null;
        }
        if (z) {
            return stdManglePropertyName(str, 3);
        }
        return legacyManglePropertyName(str, 3);
    }

    @Deprecated
    public static String okNameForIsGetter(AnnotatedMethod annotatedMethod, String str, boolean z) {
        if (!str.startsWith(MethodConst.IS_PREFIX)) {
            return null;
        }
        Class<?> rawType = annotatedMethod.getRawType();
        if (rawType != Boolean.class && rawType != Boolean.TYPE) {
            return null;
        }
        if (z) {
            return stdManglePropertyName(str, 2);
        }
        return legacyManglePropertyName(str, 2);
    }

    @Deprecated
    public static String okNameForSetter(AnnotatedMethod annotatedMethod, boolean z) {
        return okNameForMutator(annotatedMethod, MethodConst.SET_PREFIX, z);
    }

    @Deprecated
    public static String okNameForMutator(AnnotatedMethod annotatedMethod, String str, boolean z) {
        String name = annotatedMethod.getName();
        if (!name.startsWith(str)) {
            return null;
        }
        if (z) {
            return stdManglePropertyName(name, str.length());
        }
        return legacyManglePropertyName(name, str.length());
    }

    public static Object getDefaultValue(JavaType javaType) {
        Class<?> rawClass = javaType.getRawClass();
        Class<?> clsPrimitiveType = ClassUtil.primitiveType(rawClass);
        if (clsPrimitiveType != null) {
            return ClassUtil.defaultValue(clsPrimitiveType);
        }
        if (javaType.isContainerType() || javaType.isReferenceType()) {
            return JsonInclude.Include.NON_EMPTY;
        }
        if (rawClass == String.class) {
            return "";
        }
        if (javaType.isTypeOrSubTypeOf(Date.class)) {
            return new Date(0L);
        }
        if (!javaType.isTypeOrSubTypeOf(Calendar.class)) {
            return null;
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(0L);
        return gregorianCalendar;
    }

    protected static boolean isCglibGetCallbacks(AnnotatedMethod annotatedMethod) {
        Class<?> rawType = annotatedMethod.getRawType();
        if (!rawType.isArray()) {
            return false;
        }
        String name = rawType.getComponentType().getName();
        if (name.contains(".cglib")) {
            return name.startsWith("net.sf.cglib") || name.startsWith("org.hibernate.repackage.cglib") || name.startsWith("org.springframework.cglib");
        }
        return false;
    }

    protected static boolean isGroovyMetaClassGetter(AnnotatedMethod annotatedMethod) {
        return annotatedMethod.getRawType().getName().startsWith("groovy.lang");
    }

    protected static String legacyManglePropertyName(String str, int i) {
        int length = str.length();
        if (length == i) {
            return null;
        }
        char cCharAt = str.charAt(i);
        char lowerCase = Character.toLowerCase(cCharAt);
        if (cCharAt == lowerCase) {
            return str.substring(i);
        }
        StringBuilder sb = new StringBuilder(length - i);
        sb.append(lowerCase);
        while (true) {
            i++;
            if (i >= length) {
                break;
            }
            char cCharAt2 = str.charAt(i);
            char lowerCase2 = Character.toLowerCase(cCharAt2);
            if (cCharAt2 == lowerCase2) {
                sb.append((CharSequence) str, i, length);
                break;
            }
            sb.append(lowerCase2);
        }
        return sb.toString();
    }

    public static String stdManglePropertyName(String str, int i) {
        int length = str.length();
        if (length == i) {
            return null;
        }
        char cCharAt = str.charAt(i);
        char lowerCase = Character.toLowerCase(cCharAt);
        if (cCharAt == lowerCase) {
            return str.substring(i);
        }
        int i2 = i + 1;
        if (i2 < length && Character.isUpperCase(str.charAt(i2))) {
            return str.substring(i);
        }
        StringBuilder sb = new StringBuilder(length - i);
        sb.append(lowerCase);
        sb.append((CharSequence) str, i2, length);
        return sb.toString();
    }

    public static String checkUnsupportedType(JavaType javaType) {
        String str;
        String str2;
        String name = javaType.getRawClass().getName();
        if (isJava8TimeClass(name)) {
            if (name.indexOf(46, 10) >= 0 || javaType.isTypeOrSubTypeOf(Throwable.class)) {
                return null;
            }
            str = "Java 8 date/time";
            str2 = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310";
        } else if (isJodaTimeClass(name)) {
            str = "Joda date/time";
            str2 = "com.fasterxml.jackson.datatype:jackson-datatype-joda";
        } else {
            if (!isJava8OptionalClass(name)) {
                return null;
            }
            str = "Java 8 optional";
            str2 = "com.fasterxml.jackson.datatype:jackson-datatype-jdk8";
        }
        return String.format("%s type %s not supported by default: add Module \"%s\" to enable handling", str, ClassUtil.getTypeDescription(javaType), str2);
    }

    public static boolean isJava8TimeClass(Class<?> cls) {
        return isJava8TimeClass(cls.getName());
    }

    private static boolean isJava8TimeClass(String str) {
        return str.startsWith("java.time.");
    }

    public static boolean isJava8OptionalClass(Class<?> cls) {
        return isJava8OptionalClass(cls.getName());
    }

    private static boolean isJava8OptionalClass(String str) {
        return str.startsWith("java.util.Optional");
    }

    public static boolean isJodaTimeClass(Class<?> cls) {
        return isJodaTimeClass(cls.getName());
    }

    private static boolean isJodaTimeClass(String str) {
        return str.startsWith("org.joda.time.");
    }
}
