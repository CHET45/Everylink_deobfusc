package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.core.p008io.NumberInput;
import com.github.houbb.heaven.constant.CharConst;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.function.BiFunction;

/* JADX INFO: loaded from: classes3.dex */
public final class DecimalUtils {
    private static final BigDecimal ONE_BILLION = new BigDecimal(1000000000L);

    private DecimalUtils() {
    }

    public static String toDecimal(long j, int i) {
        StringBuilder sbAppend = new StringBuilder(20).append(j).append(CharConst.DOT);
        if (i != 0) {
            StringBuilder sb = new StringBuilder(9);
            sb.append(i);
            int length = 9 - sb.length();
            while (length > 0) {
                length--;
                sbAppend.append('0');
            }
            sbAppend.append((CharSequence) sb);
        } else {
            if (j == 0) {
                return "0.0";
            }
            sbAppend.append("000000000");
        }
        return sbAppend.toString();
    }

    public static BigDecimal toBigDecimal(long j, int i) {
        if (i != 0) {
            return NumberInput.parseBigDecimal(toDecimal(j, i), false);
        }
        if (j == 0) {
            return BigDecimal.ZERO.setScale(1);
        }
        return BigDecimal.valueOf(j).setScale(9);
    }

    @Deprecated
    public static int extractNanosecondDecimal(BigDecimal bigDecimal, long j) {
        return bigDecimal.subtract(BigDecimal.valueOf(j)).multiply(ONE_BILLION).intValue();
    }

    public static <T> T extractSecondsAndNanos(BigDecimal bigDecimal, BiFunction<Long, Integer, T> biFunction) {
        long j;
        BigDecimal bigDecimalScaleByPowerOfTen = bigDecimal.scaleByPowerOfTen(9);
        int i = 0;
        if (bigDecimalScaleByPowerOfTen.precision() - bigDecimalScaleByPowerOfTen.scale() > 0 && bigDecimal.scale() >= -63) {
            long jLongValue = bigDecimal.longValue();
            int iIntValue = bigDecimalScaleByPowerOfTen.subtract(BigDecimal.valueOf(jLongValue).scaleByPowerOfTen(9)).intValue();
            if (jLongValue < 0 && jLongValue > Instant.MIN.getEpochSecond()) {
                iIntValue = Math.abs(iIntValue);
            }
            j = jLongValue;
            i = iIntValue;
        } else {
            j = 0;
        }
        return biFunction.apply(Long.valueOf(j), Integer.valueOf(i));
    }
}
