package com.github.houbb.heaven.util.common;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public final class ArgUtil {
    private ArgUtil() {
    }

    public static void notNull(Object obj, String str) {
        if (obj == null) {
            throw new IllegalArgumentException(str + " can not be null!");
        }
    }

    public static void notNull(Object obj, String str, String str2) {
        if (obj == null) {
            throw new IllegalArgumentException(String.format("%s %s", str, str2));
        }
    }

    public static void notEmpty(String str, String str2) {
        if (StringUtil.isEmpty(str)) {
            throw new IllegalArgumentException(str2 + " can not be null!");
        }
    }

    public static void equals(Object obj, Object obj2, String str) {
        if (ObjectUtil.isNotEquals(obj, obj2)) {
            throw new IllegalArgumentException(buildErrorMsg(obj, obj2, str));
        }
    }

    public static void assertEqualsLen(String str, int i, String str2) {
        if (isNotEqualsLen(str, i)) {
            throw new IllegalArgumentException(buildErrorMsg(Integer.valueOf(i), Integer.valueOf(str.length()), str2));
        }
    }

    public static boolean isEqualsLen(String str, int i) {
        return StringUtil.isEmpty(str) ? i == 0 : str.length() == i;
    }

    public static boolean isNotEqualsLen(String str, int i) {
        return !isEqualsLen(str, i);
    }

    public static boolean isFitMaxLen(String str, int i) {
        return StringUtil.isEmpty(str) ? i >= 0 : str.length() <= i;
    }

    public static boolean isNotFitMaxLen(String str, int i) {
        return !isFitMaxLen(str, i);
    }

    public static boolean isFitMinLen(String str, int i) {
        return StringUtil.isEmpty(str) ? i <= 0 : str.length() >= i;
    }

    public static boolean isNotFitMinLen(String str, int i) {
        return !isFitMinLen(str, i);
    }

    public static Boolean isNumber(String str) {
        if (ObjectUtil.isNotNull(str)) {
            try {
                new BigDecimal(str);
                return true;
            } catch (Exception unused) {
                return false;
            }
        }
        return true;
    }

    public static Boolean isNotNumber(String str) {
        return Boolean.valueOf(!isNumber(str).booleanValue());
    }

    public static Boolean isMatchesRegex(String str, String str2) {
        if (str != null) {
            return Boolean.valueOf(str.matches(str2));
        }
        return true;
    }

    public static Boolean isNotMatchesRegex(String str, String str2) {
        return Boolean.valueOf(!isMatchesRegex(str, str2).booleanValue());
    }

    private static String buildErrorMsg(Object obj, Object obj2, String str) {
        if (StringUtil.isEmpty(str)) {
            str = "与期望值不符合!";
        }
        return String.format("Except:<%s>, Real:<%s>, Msg:<%s>", obj, obj2, str);
    }

    public static void positive(int i, String str) {
        if (i <= 0) {
            throw new IllegalArgumentException(str + " must be > 0!");
        }
    }

    public static void notNegative(int i, String str) {
        if (i < 0) {
            throw new IllegalArgumentException(str + " must be >= 0!");
        }
    }

    public static void positive(long j, String str) {
        if (j <= 0) {
            throw new IllegalArgumentException(str + " must be > 0!");
        }
    }

    public static void notNegative(long j, String str) {
        if (j < 0) {
            throw new IllegalArgumentException(str + " must be >= 0!");
        }
    }

    public static void positive(double d, String str) {
        if (d < 0.0d) {
            throw new IllegalArgumentException(str + " must be > 0!");
        }
    }

    public static void notNegative(double d, String str) {
        if (d < 0.0d) {
            throw new IllegalArgumentException(str + " must be >= 0!");
        }
    }

    public static void assertTrue(boolean z, String str) {
        if (!z) {
            throw new IllegalArgumentException(str + " excepted true but is false!");
        }
    }

    public static void assertFalse(boolean z, String str) {
        if (z) {
            throw new IllegalArgumentException(str + " excepted false but is true!");
        }
    }

    public static void notEmpty(Object[] objArr, String str) {
        if (ArrayUtil.isEmpty(objArr)) {
            throw new IllegalArgumentException(str + " excepted is not empty!");
        }
        for (Object obj : objArr) {
            notNull(obj, str + " element ");
        }
    }

    public static void notEmpty(Collection<?> collection, String str) {
        if (CollectionUtil.isEmpty(collection)) {
            throw new IllegalArgumentException(str + " excepted is not empty!");
        }
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            notNull(it.next(), str + " element ");
        }
    }

    public static void notEmpty(Map map, String str) {
        if (MapUtil.isEmpty(map)) {
            throw new IllegalArgumentException(str + " excepted is not empty!");
        }
    }

    @Deprecated
    /* JADX INFO: renamed from: gt */
    public static void m531gt(long j, long j2) {
        m532gt("", j, j2);
    }

    /* JADX INFO: renamed from: gt */
    public static void m532gt(String str, long j, long j2) {
        if (j <= j2) {
            throw new IllegalArgumentException("[" + str + "] actual is <" + j + ">, expected is gt " + j2);
        }
    }

    public static void gte(String str, long j, long j2) {
        if (j < j2) {
            throw new IllegalArgumentException("[" + str + "] actual is <" + j + ">, expected is gte " + j2);
        }
    }

    /* JADX INFO: renamed from: lt */
    public static void m533lt(String str, long j, long j2) {
        if (j >= j2) {
            throw new IllegalArgumentException("[" + str + "] actual is <" + j + ">, expected is lt " + j2);
        }
    }

    public static void lte(String str, long j, long j2) {
        if (j > j2) {
            throw new IllegalArgumentException("[" + str + "] actual is <" + j + ">, expected is lte " + j2);
        }
    }
}
