package com.github.houbb.heaven.util.lang;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.util.codec.Base64;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassTypeUtil;
import com.github.houbb.heaven.util.lang.reflect.PrimitiveUtil;
import com.github.houbb.heaven.util.util.ArrayPrimitiveUtil;
import com.github.houbb.heaven.util.util.Optional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;

/* JADX INFO: loaded from: classes3.dex */
public final class NumUtil {
    public static final char[] HEX_CHARS = "1234567890abcdefABCDEF".toCharArray();
    private static final int[] DIGITS_INDEX = new int[128];

    private NumUtil() {
    }

    static {
        for (int i = 0; i < 64; i++) {
            DIGITS_INDEX[Base64.ALPHABET[i]] = i;
        }
    }

    public static int getMin(int i, int i2) {
        return Math.min(i, i2);
    }

    public static int getMax(int i, int i2) {
        return Math.max(i, i2);
    }

    public static Optional<Integer> toInteger(String str) {
        if (StringUtil.isEmpty(str)) {
            return Optional.empty();
        }
        try {
            return Optional.m536of(Integer.valueOf(str));
        } catch (NumberFormatException unused) {
            return Optional.empty();
        }
    }

    public static Integer toIntegerThrows(String str) {
        ArgUtil.notEmpty(str, TypedValues.Custom.S_STRING);
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public static int toInteger(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return i;
        }
    }

    public static Optional<Long> toLong(String str) {
        if (StringUtil.isEmpty(str)) {
            return Optional.empty();
        }
        try {
            return Optional.m536of(Long.valueOf(str));
        } catch (NumberFormatException unused) {
            return Optional.empty();
        }
    }

    public static Optional<Double> toDouble(String str) {
        if (StringUtil.isEmpty(str)) {
            return Optional.empty();
        }
        try {
            return Optional.m536of(Double.valueOf(str));
        } catch (NumberFormatException unused) {
            return Optional.empty();
        }
    }

    public static boolean isHex(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (ArrayPrimitiveUtil.indexOf(HEX_CHARS, c) < 0) {
                return false;
            }
        }
        return true;
    }

    public static Long parseLong(Object obj) {
        if (ObjectUtil.isNull(obj)) {
            return null;
        }
        Class<?> cls = obj.getClass();
        if ((obj instanceof Byte) || cls == Byte.TYPE) {
            return Long.valueOf(((Byte) obj).longValue());
        }
        if ((obj instanceof Short) || cls == Short.TYPE) {
            return Long.valueOf(((Short) obj).longValue());
        }
        if ((obj instanceof Integer) || cls == Integer.TYPE) {
            return Long.valueOf(((Integer) obj).longValue());
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof CharSequence) {
            return Long.valueOf(Long.parseLong(((CharSequence) obj).toString()));
        }
        if (obj instanceof BigInteger) {
            return Long.valueOf(((BigInteger) obj).longValue());
        }
        if (obj instanceof BigDecimal) {
            return Long.valueOf(((BigDecimal) obj).longValue());
        }
        throw new ClassCastException("Class cast exception for parse long with object: " + obj);
    }

    public static String getNumFormat(Number number, String str) {
        ArgUtil.notNull(number, "number");
        ArgUtil.notEmpty(str, "format");
        return new DecimalFormat(str).format(number);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T getFormatNum(String str, String str2, Class<T> cls) {
        ArgUtil.notNull(str, "number");
        ArgUtil.notEmpty(str2, "format");
        try {
            T t = (T) new DecimalFormat(str2).parse(str);
            if (BigDecimal.class == cls) {
                return (T) BigDecimal.valueOf(((Double) t).doubleValue());
            }
            if (BigInteger.class == cls) {
                return (T) BigInteger.valueOf(((Long) t).longValue());
            }
            if (Float.class != cls) {
                Class cls2 = Float.TYPE;
            }
            return t;
        } catch (ParseException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static Number getFormatNum(String str, String str2) {
        return (Number) getFormatNum(str, str2, Number.class);
    }

    public static Object getFormatNumCast(String str, String str2, Class cls) {
        ArgUtil.notEmpty(str, "numberStr");
        ArgUtil.notEmpty(str2, "format");
        ArgUtil.notNull(cls, "numberClazz");
        if (ClassTypeUtil.isPrimitive(cls)) {
            cls = PrimitiveUtil.getReferenceType(cls);
        }
        try {
            Number number = new DecimalFormat(str2).parse(str);
            if (Integer.class == cls) {
                return Integer.valueOf(number.intValue());
            }
            if (Long.class == cls) {
                return Long.valueOf(number.longValue());
            }
            if (Float.class == cls) {
                return Float.valueOf(number.floatValue());
            }
            if (Double.class == cls) {
                return Double.valueOf(number.doubleValue());
            }
            if (Short.class == cls) {
                return Short.valueOf(number.shortValue());
            }
            if (Byte.class == cls) {
                return Byte.valueOf(number.byteValue());
            }
            if (BigDecimal.class == cls) {
                return BigDecimal.valueOf(((Double) number).doubleValue());
            }
            return BigInteger.class == cls ? BigInteger.valueOf(((Long) number).longValue()) : number;
        } catch (ParseException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static BigInteger toBigInteger(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return bigDecimal.toBigInteger();
    }

    public static BigDecimal parseBigDecimal(BigInteger bigInteger) {
        if (bigInteger == null) {
            return null;
        }
        return new BigDecimal(bigInteger);
    }

    public static BigDecimal toBigDecimal(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        if (obj instanceof Long) {
            return BigDecimal.valueOf(((Long) obj).longValue());
        }
        if (obj instanceof Double) {
            return BigDecimal.valueOf(((Double) obj).doubleValue());
        }
        return new BigDecimal(String.valueOf(obj));
    }

    private static int getIndex(String str, int i) {
        return DIGITS_INDEX[str.charAt(i)];
    }

    public static long num64To10(String str) {
        long index = 0;
        long j = 1;
        for (int length = str.length() - 1; length >= 0; length--) {
            index += ((long) getIndex(str, length)) * j;
            j *= 64;
        }
        return index;
    }

    public static String num10To64(long j) {
        ArgUtil.notNegative(j, "number");
        if (j == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        while (j != 0) {
            sb.append(Base64.ALPHABET[(int) (j % 64)]);
            j /= 64;
        }
        return sb.reverse().toString();
    }

    public static String chainRatio(int i, int i2) {
        if (i <= 0) {
            return "--";
        }
        return new DecimalFormat("#0.00").format(((((double) (i2 - i)) * 1.0d) / (((double) i) * 1.0d)) * 100.0d);
    }
}
