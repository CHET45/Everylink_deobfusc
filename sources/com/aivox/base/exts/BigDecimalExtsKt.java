package com.aivox.base.exts;

import com.aivox.base.util.NumberUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BigDecimalExts.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a/\u0010\u0003\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\n\u001a/\u0010\u000b\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\n\u001a/\u0010\f\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\n\u001a/\u0010\r\u001a\u00020\u0002*\u00020\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\n¨\u0006\u000e"}, m1901d2 = {"isZero", "", "Ljava/math/BigDecimal;", "numDivide", "v", "", "precision", "", "mode", "Ljava/math/RoundingMode;", "(Ljava/math/BigDecimal;Ljava/lang/String;Ljava/lang/Integer;Ljava/math/RoundingMode;)Ljava/math/BigDecimal;", "numMinus", "numMultiply", "numPlus", "base_release"}, m1902k = 2, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class BigDecimalExtsKt {
    public static /* synthetic */ BigDecimal numPlus$default(BigDecimal bigDecimal, String str, Integer num, RoundingMode roundingMode, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        if ((i & 4) != 0) {
            roundingMode = RoundingMode.FLOOR;
        }
        return numPlus(bigDecimal, str, num, roundingMode);
    }

    public static final BigDecimal numPlus(BigDecimal bigDecimal, String str, Integer num, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(bigDecimal, "<this>");
        Intrinsics.checkNotNullParameter(mode, "mode");
        return NumberUtils.plus(bigDecimal.toString(), str, num, mode);
    }

    public static /* synthetic */ BigDecimal numMinus$default(BigDecimal bigDecimal, String str, Integer num, RoundingMode roundingMode, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        if ((i & 4) != 0) {
            roundingMode = RoundingMode.FLOOR;
        }
        return numMinus(bigDecimal, str, num, roundingMode);
    }

    public static final BigDecimal numMinus(BigDecimal bigDecimal, String str, Integer num, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(bigDecimal, "<this>");
        Intrinsics.checkNotNullParameter(mode, "mode");
        return NumberUtils.minus(bigDecimal.toString(), str, num, mode);
    }

    public static /* synthetic */ BigDecimal numMultiply$default(BigDecimal bigDecimal, String str, Integer num, RoundingMode roundingMode, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        if ((i & 4) != 0) {
            roundingMode = RoundingMode.FLOOR;
        }
        return numMultiply(bigDecimal, str, num, roundingMode);
    }

    public static final BigDecimal numMultiply(BigDecimal bigDecimal, String str, Integer num, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(bigDecimal, "<this>");
        Intrinsics.checkNotNullParameter(mode, "mode");
        return NumberUtils.multiply(bigDecimal.toString(), str, num, mode);
    }

    public static /* synthetic */ BigDecimal numDivide$default(BigDecimal bigDecimal, String str, Integer num, RoundingMode roundingMode, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        if ((i & 4) != 0) {
            roundingMode = RoundingMode.HALF_EVEN;
        }
        return numDivide(bigDecimal, str, num, roundingMode);
    }

    public static final BigDecimal numDivide(BigDecimal bigDecimal, String str, Integer num, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(bigDecimal, "<this>");
        Intrinsics.checkNotNullParameter(mode, "mode");
        return NumberUtils.divide(bigDecimal.toString(), str, num, mode);
    }

    public static final boolean isZero(BigDecimal bigDecimal) {
        Intrinsics.checkNotNullParameter(bigDecimal, "<this>");
        return bigDecimal.compareTo(BigDecimal.ZERO) == 0;
    }
}
