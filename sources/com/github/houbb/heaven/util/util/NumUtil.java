package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.util.codec.Base64;
import com.github.houbb.heaven.util.common.ArgUtil;
import java.text.DecimalFormat;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public final class NumUtil {
    private static final int[] DIGITS_INDEX = new int[128];

    private NumUtil() {
    }

    static {
        for (int i = 0; i < 64; i++) {
            DIGITS_INDEX[Base64.ALPHABET[i]] = i;
        }
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
