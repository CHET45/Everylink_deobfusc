package com.fasterxml.jackson.core.p008io;

import com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.JavaBigDecimalParser;
import java.math.BigDecimal;

/* JADX INFO: loaded from: classes3.dex */
public final class BigDecimalParser {
    static final int MAX_CHARS_TO_REPORT = 1000;
    private static final int SIZE_FOR_SWITCH_TO_FASTDOUBLEPARSER = 500;

    private BigDecimalParser() {
    }

    public static BigDecimal parse(String str) {
        try {
            if (str.length() < 500) {
                return new BigDecimal(str);
            }
            return JavaBigDecimalParser.parseBigDecimal(str);
        } catch (ArithmeticException | NumberFormatException e) {
            throw _parseFailure(e, str);
        }
    }

    public static BigDecimal parse(char[] cArr, int i, int i2) {
        try {
            if (i2 < 500) {
                return new BigDecimal(cArr, i, i2);
            }
            return JavaBigDecimalParser.parseBigDecimal(cArr, i, i2);
        } catch (ArithmeticException | NumberFormatException e) {
            throw _parseFailure(e, cArr, i, i2);
        }
    }

    public static BigDecimal parse(char[] cArr) {
        return parse(cArr, 0, cArr.length);
    }

    public static BigDecimal parseWithFastParser(String str) {
        try {
            return JavaBigDecimalParser.parseBigDecimal(str);
        } catch (ArithmeticException | NumberFormatException e) {
            throw _parseFailure(e, str);
        }
    }

    public static BigDecimal parseWithFastParser(char[] cArr, int i, int i2) {
        try {
            return JavaBigDecimalParser.parseBigDecimal(cArr, i, i2);
        } catch (ArithmeticException | NumberFormatException e) {
            throw _parseFailure(e, cArr, i, i2);
        }
    }

    private static NumberFormatException _parseFailure(Exception exc, String str) {
        String message = exc.getMessage();
        if (message == null) {
            message = "Not a valid number representation";
        }
        return new NumberFormatException(_generateExceptionMessage(_getValueDesc(str), message));
    }

    private static NumberFormatException _parseFailure(Exception exc, char[] cArr, int i, int i2) {
        String message = exc.getMessage();
        if (message == null) {
            message = "Not a valid number representation";
        }
        return new NumberFormatException(_generateExceptionMessage(_getValueDesc(cArr, i, i2), message));
    }

    private static String _getValueDesc(String str) {
        int length = str.length();
        if (length <= 1000) {
            return String.format("\"%s\"", str);
        }
        return String.format("\"%s\" (truncated to %d chars (from %d))", str.substring(0, 1000), 1000, Integer.valueOf(length));
    }

    private static String _getValueDesc(char[] cArr, int i, int i2) {
        if (i2 <= 1000) {
            return String.format("\"%s\"", new String(cArr, i, i2));
        }
        return String.format("\"%s\" (truncated to %d chars (from %d))", new String(cArr, i, 1000), 1000, Integer.valueOf(i2));
    }

    private static String _generateExceptionMessage(String str, String str2) {
        return String.format("Value %s can not be deserialized as `java.math.BigDecimal`, reason:  %s", str, str2);
    }
}
