package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

import com.aivox.base.common.Constant;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes3.dex */
class JavaBigIntegerFromCharArray extends AbstractBigIntegerParser {
    JavaBigIntegerFromCharArray() {
    }

    public BigInteger parseBigIntegerString(char[] cArr, int i, int i2, int i3) throws NumberFormatException {
        int i4;
        try {
            int iCheckBounds = AbstractNumberParser.checkBounds(cArr.length, i, i2);
            char c = cArr[i];
            boolean z = c == '-';
            if (z || c == '+') {
                i4 = i + 1;
                if (charAt(cArr, i4, iCheckBounds) == 0) {
                    throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
                }
            } else {
                i4 = i;
            }
            if (i3 == 10) {
                return parseDecDigits(cArr, i4, iCheckBounds, z);
            }
            if (i3 == 16) {
                return parseHexDigits(cArr, i4, iCheckBounds, z);
            }
            return new BigInteger(new String(cArr, i, i2), i3);
        } catch (ArithmeticException e) {
            NumberFormatException numberFormatException = new NumberFormatException(AbstractNumberParser.VALUE_EXCEEDS_LIMITS);
            numberFormatException.initCause(e);
            throw numberFormatException;
        }
    }

    private BigInteger parseDecDigits(char[] cArr, int i, int i2, boolean z) {
        int i3 = i2 - i;
        if (hasManyDigits(i3)) {
            return parseManyDecDigits(cArr, i, i2, z);
        }
        int i4 = (i3 & 7) + i;
        long jTryToParseUpTo7Digits = FastDoubleSwar.tryToParseUpTo7Digits(cArr, i, i4);
        boolean z2 = jTryToParseUpTo7Digits >= 0;
        while (i4 < i2) {
            int iTryToParseEightDigits = FastDoubleSwar.tryToParseEightDigits(cArr, i4);
            z2 &= iTryToParseEightDigits >= 0;
            jTryToParseUpTo7Digits = (jTryToParseUpTo7Digits * 100000000) + ((long) iTryToParseEightDigits);
            i4 += 8;
        }
        if (!z2) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        if (z) {
            jTryToParseUpTo7Digits = -jTryToParseUpTo7Digits;
        }
        return BigInteger.valueOf(jTryToParseUpTo7Digits);
    }

    private BigInteger parseHexDigits(char[] cArr, int i, int i2, boolean z) {
        int i3;
        boolean z2;
        int iSkipZeroes = skipZeroes(cArr, i, i2);
        int i4 = i2 - iSkipZeroes;
        if (i4 <= 0) {
            return BigInteger.ZERO;
        }
        checkHexBigIntegerBounds(i4);
        byte[] bArr = new byte[((i4 + 1) >> 1) + 1];
        if ((i4 & 1) != 0) {
            int i5 = iSkipZeroes + 1;
            int iLookupHex = lookupHex(cArr[iSkipZeroes]);
            bArr[1] = (byte) iLookupHex;
            i3 = 2;
            z2 = iLookupHex < 0;
            iSkipZeroes = i5;
        } else {
            i3 = 1;
            z2 = false;
        }
        int i6 = ((i2 - iSkipZeroes) & 7) + iSkipZeroes;
        while (iSkipZeroes < i6) {
            char c = cArr[iSkipZeroes];
            char c2 = cArr[iSkipZeroes + 1];
            int iLookupHex2 = lookupHex(c);
            int iLookupHex3 = lookupHex(c2);
            int i7 = i3 + 1;
            bArr[i3] = (byte) ((iLookupHex2 << 4) | iLookupHex3);
            z2 |= iLookupHex2 < 0 || iLookupHex3 < 0;
            iSkipZeroes += 2;
            i3 = i7;
        }
        while (iSkipZeroes < i2) {
            long jTryToParseEightHexDigits = FastDoubleSwar.tryToParseEightHexDigits(cArr, iSkipZeroes);
            FastDoubleSwar.writeIntBE(bArr, i3, (int) jTryToParseEightHexDigits);
            z2 |= jTryToParseEightHexDigits < 0;
            iSkipZeroes += 8;
            i3 += 4;
        }
        if (z2) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        BigInteger bigInteger = new BigInteger(bArr);
        return z ? bigInteger.negate() : bigInteger;
    }

    private BigInteger parseManyDecDigits(char[] cArr, int i, int i2, boolean z) {
        int iSkipZeroes = skipZeroes(cArr, i, i2);
        checkDecBigIntegerBounds(i2 - iSkipZeroes);
        BigInteger digitsRecursive = ParseDigitsTaskCharArray.parseDigitsRecursive(cArr, iSkipZeroes, i2, FastIntegerMath.fillPowersOf10Floor16(iSkipZeroes, i2), Constant.EVENT.BLE_GLASS_ASK_AI);
        return z ? digitsRecursive.negate() : digitsRecursive;
    }

    private int skipZeroes(char[] cArr, int i, int i2) {
        while (i < i2 - 8 && FastDoubleSwar.isEightZeroes(cArr, i)) {
            i += 8;
        }
        while (i < i2 && cArr[i] == '0') {
            i++;
        }
        return i;
    }
}
