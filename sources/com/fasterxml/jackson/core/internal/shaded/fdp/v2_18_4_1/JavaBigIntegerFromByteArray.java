package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

import com.aivox.base.common.Constant;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes3.dex */
class JavaBigIntegerFromByteArray extends AbstractBigIntegerParser {
    JavaBigIntegerFromByteArray() {
    }

    public BigInteger parseBigIntegerString(byte[] bArr, int i, int i2, int i3) throws NumberFormatException {
        int i4;
        try {
            int iCheckBounds = AbstractNumberParser.checkBounds(bArr.length, i, i2);
            byte b = bArr[i];
            boolean z = b == 45;
            if (z || b == 43) {
                i4 = i + 1;
                if (charAt(bArr, i4, iCheckBounds) == 0) {
                    throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
                }
            } else {
                i4 = i;
            }
            if (i3 == 10) {
                return parseDecDigits(bArr, i4, iCheckBounds, z);
            }
            if (i3 == 16) {
                return parseHexDigits(bArr, i4, iCheckBounds, z);
            }
            return new BigInteger(new String(bArr, i, i2, StandardCharsets.ISO_8859_1), i3);
        } catch (ArithmeticException e) {
            NumberFormatException numberFormatException = new NumberFormatException(AbstractNumberParser.VALUE_EXCEEDS_LIMITS);
            numberFormatException.initCause(e);
            throw numberFormatException;
        }
    }

    private BigInteger parseDecDigits(byte[] bArr, int i, int i2, boolean z) {
        int i3 = i2 - i;
        if (hasManyDigits(i3)) {
            return parseManyDecDigits(bArr, i, i2, z);
        }
        int i4 = (i3 & 7) + i;
        long jTryToParseUpTo7Digits = FastDoubleSwar.tryToParseUpTo7Digits(bArr, i, i4);
        boolean z2 = jTryToParseUpTo7Digits >= 0;
        while (i4 < i2) {
            int iTryToParseEightDigitsUtf8 = FastDoubleSwar.tryToParseEightDigitsUtf8(bArr, i4);
            z2 &= iTryToParseEightDigitsUtf8 >= 0;
            jTryToParseUpTo7Digits = (jTryToParseUpTo7Digits * 100000000) + ((long) iTryToParseEightDigitsUtf8);
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

    private BigInteger parseHexDigits(byte[] bArr, int i, int i2, boolean z) {
        int i3;
        boolean z2;
        int iSkipZeroes = skipZeroes(bArr, i, i2);
        int i4 = i2 - iSkipZeroes;
        if (i4 <= 0) {
            return BigInteger.ZERO;
        }
        checkHexBigIntegerBounds(i4);
        byte[] bArr2 = new byte[((i4 + 1) >> 1) + 1];
        if ((i4 & 1) != 0) {
            int i5 = iSkipZeroes + 1;
            int iLookupHex = lookupHex(bArr[iSkipZeroes]);
            bArr2[1] = (byte) iLookupHex;
            i3 = 2;
            z2 = iLookupHex < 0;
            iSkipZeroes = i5;
        } else {
            i3 = 1;
            z2 = false;
        }
        int i6 = ((i2 - iSkipZeroes) & 7) + iSkipZeroes;
        while (iSkipZeroes < i6) {
            byte b = bArr[iSkipZeroes];
            byte b2 = bArr[iSkipZeroes + 1];
            int iLookupHex2 = lookupHex(b);
            int iLookupHex3 = lookupHex(b2);
            int i7 = i3 + 1;
            bArr2[i3] = (byte) ((iLookupHex2 << 4) | iLookupHex3);
            z2 |= iLookupHex2 < 0 || iLookupHex3 < 0;
            iSkipZeroes += 2;
            i3 = i7;
        }
        while (iSkipZeroes < i2) {
            long jTryToParseEightHexDigits = FastDoubleSwar.tryToParseEightHexDigits(bArr, iSkipZeroes);
            FastDoubleSwar.writeIntBE(bArr2, i3, (int) jTryToParseEightHexDigits);
            z2 |= jTryToParseEightHexDigits < 0;
            iSkipZeroes += 8;
            i3 += 4;
        }
        if (z2) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        BigInteger bigInteger = new BigInteger(bArr2);
        return z ? bigInteger.negate() : bigInteger;
    }

    private BigInteger parseManyDecDigits(byte[] bArr, int i, int i2, boolean z) {
        int iSkipZeroes = skipZeroes(bArr, i, i2);
        checkDecBigIntegerBounds(i2 - iSkipZeroes);
        BigInteger digitsRecursive = ParseDigitsTaskByteArray.parseDigitsRecursive(bArr, iSkipZeroes, i2, FastIntegerMath.fillPowersOf10Floor16(iSkipZeroes, i2), Constant.EVENT.BLE_GLASS_ASK_AI);
        return z ? digitsRecursive.negate() : digitsRecursive;
    }

    private int skipZeroes(byte[] bArr, int i, int i2) {
        while (i < i2 - 8 && FastDoubleSwar.isEightZeroes(bArr, i)) {
            i += 8;
        }
        while (i < i2 && bArr[i] == 48) {
            i++;
        }
        return i;
    }
}
