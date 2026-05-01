package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

import com.aivox.base.common.Constant;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.NavigableMap;

/* JADX INFO: loaded from: classes3.dex */
final class JavaBigDecimalFromCharArray extends AbstractBigDecimalParser {
    public BigDecimal parseBigDecimalString(char[] cArr, int i, int i2) {
        long j;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        long j2;
        int i8;
        long j3;
        int iTryToParseFourDigits;
        char[] cArr2 = cArr;
        int i9 = i;
        try {
            int iCheckBounds = checkBounds(cArr2.length, i9, i2);
            if (hasManyDigits(i2)) {
                return parseBigDecimalStringWithManyDigits(cArr, i, i2);
            }
            char cCharAt = charAt(cArr2, i9, iCheckBounds);
            boolean z = cCharAt == '-';
            if (z || cCharAt == '+') {
                i9++;
                cCharAt = charAt(cArr2, i9, iCheckBounds);
                if (cCharAt == 0) {
                    throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
                }
            }
            int i10 = i9;
            int i11 = -1;
            int i12 = i10;
            long j4 = 0;
            boolean z2 = false;
            while (i12 < iCheckBounds) {
                cCharAt = cArr2[i12];
                char c = (char) (cCharAt - '0');
                if (c >= '\n') {
                    if (cCharAt != '.') {
                        break;
                    }
                    z2 |= i11 >= 0;
                    int i13 = i12;
                    while (i13 < iCheckBounds - 4 && (iTryToParseFourDigits = FastDoubleSwar.tryToParseFourDigits(cArr2, i13 + 1)) >= 0) {
                        j4 = (j4 * 10000) + ((long) iTryToParseFourDigits);
                        i13 += 4;
                    }
                    int i14 = i12;
                    i12 = i13;
                    i11 = i14;
                } else {
                    j4 = (j4 * 10) + ((long) c);
                }
                i12++;
            }
            if (i11 < 0) {
                i4 = i12 - i10;
                i3 = i12;
                j = 0;
            } else {
                j = (i11 - i12) + 1;
                i3 = i11;
                i4 = (i12 - i10) - 1;
            }
            if ((cCharAt | ' ') == 101) {
                int i15 = i12 + 1;
                char cCharAt2 = charAt(cArr2, i15, iCheckBounds);
                boolean z3 = cCharAt2 == '-';
                if (z3 || cCharAt2 == '+') {
                    i15 = i12 + 2;
                    cCharAt2 = charAt(cArr2, i15, iCheckBounds);
                }
                char cCharAt3 = (char) (cCharAt2 - '0');
                z2 |= cCharAt3 >= '\n';
                long j5 = 0;
                while (true) {
                    if (j5 < 2147483647L) {
                        i5 = i10;
                        i6 = i3;
                        j5 = (j5 * 10) + ((long) cCharAt3);
                    } else {
                        i5 = i10;
                        i6 = i3;
                    }
                    j3 = j5;
                    i15++;
                    cCharAt3 = (char) (charAt(cArr2, i15, iCheckBounds) - '0');
                    if (cCharAt3 >= '\n') {
                        break;
                    }
                    cArr2 = cArr;
                    j5 = j3;
                    i10 = i5;
                    i3 = i6;
                }
                if (z3) {
                    j3 = -j3;
                }
                i8 = i15;
                j2 = j + j3;
                i7 = i12;
            } else {
                i5 = i10;
                i6 = i3;
                i7 = iCheckBounds;
                j2 = j;
                i8 = i12;
            }
            checkParsedBigDecimalBounds(z2 | (i4 == 0), i8, iCheckBounds, i4, j2);
            if (i4 < 19) {
                if (z) {
                    j4 = -j4;
                }
                return new BigDecimal(j4).scaleByPowerOfTen((int) j2);
            }
            return valueOfBigDecimalString(cArr, i5, i6, i6 + 1, i7, z, (int) j2);
        } catch (ArithmeticException e) {
            NumberFormatException numberFormatException = new NumberFormatException(AbstractNumberParser.VALUE_EXCEEDS_LIMITS);
            numberFormatException.initCause(e);
            throw numberFormatException;
        }
    }

    BigDecimal parseBigDecimalStringWithManyDigits(char[] cArr, int i, int i2) {
        int i3;
        int i4;
        long j;
        int i5;
        boolean z;
        int i6;
        boolean z2;
        long j2;
        int i7 = i;
        int i8 = i7 + i2;
        char cCharAt = charAt(cArr, i7, i8);
        boolean z3 = cCharAt == '-';
        if ((z3 || cCharAt == '+') && (cCharAt = charAt(cArr, (i7 = i7 + 1), i8)) == 0) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        int iMin = Math.min(i8 - 8, 1073741824);
        int i9 = i7;
        while (i9 < iMin && FastDoubleSwar.isEightZeroes(cArr, i9)) {
            i9 += 8;
        }
        while (i9 < i8 && cArr[i9] == '0') {
            i9++;
        }
        int i10 = i9;
        while (i10 < iMin && FastDoubleSwar.isEightDigits(cArr, i10)) {
            i10 += 8;
        }
        while (i10 < i8) {
            cCharAt = cArr[i10];
            if (!FastDoubleSwar.isDigit(cCharAt)) {
                break;
            }
            i10++;
        }
        if (cCharAt == '.') {
            int i11 = i10 + 1;
            while (i11 < iMin && FastDoubleSwar.isEightZeroes(cArr, i11)) {
                i11 += 8;
            }
            while (i11 < i8 && cArr[i11] == '0') {
                i11++;
            }
            int i12 = i11;
            while (i12 < iMin && FastDoubleSwar.isEightDigits(cArr, i12)) {
                i12 += 8;
            }
            while (i12 < i8) {
                cCharAt = cArr[i12];
                if (!FastDoubleSwar.isDigit(cCharAt)) {
                    break;
                }
                i12++;
            }
            i4 = i11;
            i3 = i10;
            i10 = i12;
        } else {
            i3 = -1;
            i4 = -1;
        }
        if (i3 < 0) {
            i5 = i10 - i9;
            i4 = i10;
            i3 = i4;
            j = 0;
        } else {
            j = (i3 - i10) + 1;
            i5 = i9 == i3 ? i10 - i4 : (i10 - i9) - 1;
        }
        if ((cCharAt | ' ') == 101) {
            int i13 = i10 + 1;
            char cCharAt2 = charAt(cArr, i13, i8);
            boolean z4 = cCharAt2 == '-';
            if (z4 || cCharAt2 == '+') {
                i13 = i10 + 2;
                cCharAt2 = charAt(cArr, i13, i8);
            }
            char cCharAt3 = (char) (cCharAt2 - '0');
            z2 = cCharAt3 >= '\n';
            long j3 = 0;
            while (true) {
                if (j3 < 2147483647L) {
                    z = z3;
                    j3 = (j3 * 10) + ((long) cCharAt3);
                } else {
                    z = z3;
                }
                j2 = j3;
                i13++;
                cCharAt3 = (char) (charAt(cArr, i13, i8) - '0');
                if (cCharAt3 >= '\n') {
                    break;
                }
                j3 = j2;
                z3 = z;
            }
            if (z4) {
                j2 = -j2;
            }
            j += j2;
            i6 = i13;
        } else {
            z = z3;
            i6 = i10;
            z2 = false;
            i10 = i8;
        }
        checkParsedBigDecimalBounds(z2 | (i7 == i3 && i3 == i10), i6, i8, i5, j);
        return valueOfBigDecimalString(cArr, i9, i3, i4, i10, z, (int) j);
    }

    BigDecimal valueOfBigDecimalString(char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5) {
        BigInteger bigIntegerNegate;
        BigInteger digitsIterative;
        int i6 = (i4 - i2) - 1;
        int i7 = i4 - i3;
        int i8 = i2 - i;
        NavigableMap<Integer, BigInteger> navigableMapCreatePowersOfTenFloor16Map = null;
        if (i8 <= 0) {
            bigIntegerNegate = BigInteger.ZERO;
        } else if (i8 > 400) {
            navigableMapCreatePowersOfTenFloor16Map = FastIntegerMath.createPowersOfTenFloor16Map();
            FastIntegerMath.fillPowersOfNFloor16Recursive(navigableMapCreatePowersOfTenFloor16Map, i, i2);
            bigIntegerNegate = ParseDigitsTaskCharArray.parseDigitsRecursive(cArr, i, i2, navigableMapCreatePowersOfTenFloor16Map, Constant.EVENT.BLE_GLASS_ASK_AI);
        } else {
            bigIntegerNegate = ParseDigitsTaskCharArray.parseDigitsIterative(cArr, i, i2);
        }
        if (i6 > 0) {
            if (i7 > 400) {
                if (navigableMapCreatePowersOfTenFloor16Map == null) {
                    navigableMapCreatePowersOfTenFloor16Map = FastIntegerMath.createPowersOfTenFloor16Map();
                }
                FastIntegerMath.fillPowersOfNFloor16Recursive(navigableMapCreatePowersOfTenFloor16Map, i3, i4);
                digitsIterative = ParseDigitsTaskCharArray.parseDigitsRecursive(cArr, i3, i4, navigableMapCreatePowersOfTenFloor16Map, Constant.EVENT.BLE_GLASS_ASK_AI);
            } else {
                digitsIterative = ParseDigitsTaskCharArray.parseDigitsIterative(cArr, i3, i4);
            }
            if (bigIntegerNegate.signum() != 0) {
                digitsIterative = FftMultiplier.multiply(bigIntegerNegate, FastIntegerMath.computePowerOfTen(navigableMapCreatePowersOfTenFloor16Map, i6)).add(digitsIterative);
            }
            bigIntegerNegate = digitsIterative;
        }
        if (z) {
            bigIntegerNegate = bigIntegerNegate.negate();
        }
        return new BigDecimal(bigIntegerNegate, -i5);
    }
}
