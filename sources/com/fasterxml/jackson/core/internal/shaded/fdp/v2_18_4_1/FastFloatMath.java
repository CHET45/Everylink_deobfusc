package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;
import kotlinx.coroutines.scheduling.WorkQueueKt;

/* JADX INFO: loaded from: classes3.dex */
class FastFloatMath {
    private static final int FLOAT_EXPONENT_BIAS = 127;
    private static final int FLOAT_MAX_EXPONENT_POWER_OF_TEN = 38;
    private static final int FLOAT_MAX_EXPONENT_POWER_OF_TWO = 127;
    private static final int FLOAT_MIN_EXPONENT_POWER_OF_TEN = -45;
    private static final int FLOAT_MIN_EXPONENT_POWER_OF_TWO = -126;
    private static final float[] FLOAT_POWER_OF_TEN = {1.0f, 10.0f, 100.0f, 1000.0f, 10000.0f, 100000.0f, 1000000.0f, 1.0E7f, 1.0E8f, 1.0E9f, 1.0E10f};
    private static final int FLOAT_SIGNIFICAND_WIDTH = 24;

    private FastFloatMath() {
    }

    static float tryDecFloatToFloatTruncated(boolean z, long j, int i, boolean z2, int i2) {
        if (j == 0) {
            return z ? -0.0f : 0.0f;
        }
        if (!z2) {
            if (FLOAT_MIN_EXPONENT_POWER_OF_TEN > i || i > 38) {
                return Float.NaN;
            }
            return tryDecToFloatWithFastAlgorithm(z, j, i);
        }
        if (FLOAT_MIN_EXPONENT_POWER_OF_TEN > i2 || i2 > 38) {
            return Float.NaN;
        }
        float fTryDecToFloatWithFastAlgorithm = tryDecToFloatWithFastAlgorithm(z, j, i2);
        float fTryDecToFloatWithFastAlgorithm2 = tryDecToFloatWithFastAlgorithm(z, j + 1, i2);
        if (Float.isNaN(fTryDecToFloatWithFastAlgorithm) || fTryDecToFloatWithFastAlgorithm2 != fTryDecToFloatWithFastAlgorithm) {
            return Float.NaN;
        }
        return fTryDecToFloatWithFastAlgorithm;
    }

    static float tryHexFloatToFloatTruncated(boolean z, long j, int i, boolean z2, int i2) {
        if (z2) {
            i = i2;
        }
        if (FLOAT_MIN_EXPONENT_POWER_OF_TWO > i || i > 127) {
            return Float.NaN;
        }
        float fFastScalb = fastScalb(j + (j < 0 ? 1.8446744E19f : 0.0f), i);
        return z ? -fFastScalb : fFastScalb;
    }

    static float tryDecToFloatWithFastAlgorithm(boolean z, long j, int i) {
        float f;
        if (-10 <= i && i <= 10 && Long.compareUnsigned(j, 16777215L) <= 0) {
            float f2 = j;
            if (i < 0) {
                f = f2 / FLOAT_POWER_OF_TEN[-i];
            } else {
                f = f2 * FLOAT_POWER_OF_TEN[i];
            }
            return z ? -f : f;
        }
        long j2 = FastDoubleMath.MANTISSA_64[i + PullToRefreshBase.SMOOTH_SCROLL_LONG_DURATION_MS];
        long j3 = ((((long) i) * 217706) >> 16) + 191;
        int iNumberOfLeadingZeros = Long.numberOfLeadingZeros(j);
        long jUnsignedMultiplyHigh = FastIntegerMath.unsignedMultiplyHigh(j << iNumberOfLeadingZeros, j2);
        long j4 = jUnsignedMultiplyHigh >>> 63;
        long j5 = jUnsignedMultiplyHigh >>> ((int) (38 + j4));
        int i2 = iNumberOfLeadingZeros + ((int) (j4 ^ 1));
        long j6 = jUnsignedMultiplyHigh & 274877906943L;
        if (j6 != 274877906943L) {
            if (j6 != 0 || (3 & j5) != 1) {
                long j7 = (j5 + 1) >>> 1;
                if (j7 >= 16777216) {
                    i2--;
                    j7 = 8388608;
                }
                long j8 = j7 & (-8388609);
                long j9 = j3 - ((long) i2);
                if (j9 >= 1 && j9 <= 254) {
                    return Float.intBitsToFloat((int) (j8 | (j9 << 23) | (z ? 2147483648L : 0L)));
                }
            }
        }
        return Float.NaN;
    }

    static float fastScalb(float f, int i) {
        return f * Float.intBitsToFloat((i + WorkQueueKt.MASK) << 23);
    }
}
