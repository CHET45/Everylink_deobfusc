package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

import com.github.houbb.heaven.constant.CharConst;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractJavaFloatingPointBitsFromCharArray extends AbstractFloatValueParser {
    private static final boolean CONDITIONAL_COMPILATION_PARSE_EIGHT_HEX_DIGITS = true;

    abstract long nan();

    abstract long negativeInfinity();

    abstract long positiveInfinity();

    abstract long valueOfFloatLiteral(char[] cArr, int i, int i2, boolean z, long j, int i3, boolean z2, int i4);

    abstract long valueOfHexLiteral(char[] cArr, int i, int i2, boolean z, long j, int i3, boolean z2, int i4);

    AbstractJavaFloatingPointBitsFromCharArray() {
    }

    private static int skipWhitespace(char[] cArr, int i, int i2) {
        while (i < i2 && cArr[i] <= ' ') {
            i++;
        }
        return i;
    }

    private long parseDecFloatLiteral(char[] cArr, int i, int i2, int i3, boolean z, boolean z2) {
        char c;
        int i4;
        int i5;
        int i6;
        char cCharAt;
        int i7;
        int i8;
        long j;
        boolean z3;
        int i9;
        int iMin = Math.min(i3 - 4, 1073741824);
        int i10 = -1;
        int i11 = i;
        long j2 = 0;
        char c2 = 0;
        boolean z4 = false;
        while (true) {
            c = CharConst.DOT;
            if (i11 >= i3) {
                break;
            }
            c2 = cArr[i11];
            char c3 = (char) (c2 - '0');
            if (c3 >= '\n') {
                if (c2 != '.') {
                    break;
                }
                z4 |= i10 >= 0;
                int i12 = i11;
                while (i12 < iMin) {
                    int iTryToParseFourDigits = FastDoubleSwar.tryToParseFourDigits(cArr, i12 + 1);
                    if (iTryToParseFourDigits < 0) {
                        break;
                    }
                    j2 = (j2 * 10000) + ((long) iTryToParseFourDigits);
                    i12 += 4;
                }
                int i13 = i11;
                i11 = i12;
                i10 = i13;
            } else {
                j2 = (j2 * 10) + ((long) c3);
            }
            i11++;
        }
        if (i10 < 0) {
            i4 = i11 - i;
            i10 = i11;
            i5 = 0;
        } else {
            i4 = (i11 - i) - 1;
            i5 = (i10 - i11) + 1;
        }
        if ((c2 | ' ') == 101) {
            i6 = i11 + 1;
            char cCharAt2 = charAt(cArr, i6, i3);
            boolean z5 = cCharAt2 == '-';
            if (z5 || cCharAt2 == '+') {
                i6 = i11 + 2;
                cCharAt2 = charAt(cArr, i6, i3);
            }
            char c4 = (char) (cCharAt2 - '0');
            boolean z6 = z4 | (c4 >= '\n');
            int i14 = 0;
            while (true) {
                if (i14 < 1024) {
                    i14 = (i14 * 10) + c4;
                }
                i6++;
                cCharAt = charAt(cArr, i6, i3);
                char c5 = (char) (cCharAt - '0');
                if (c5 >= '\n') {
                    break;
                }
                c4 = c5;
            }
            if (z5) {
                i14 = -i14;
            }
            z4 = z6;
            int i15 = i14;
            i7 = i5 + i14;
            i8 = i15;
        } else {
            i6 = i11;
            cCharAt = c2;
            i7 = i5;
            i8 = 0;
        }
        if ((cCharAt | '\"') == 102) {
            i6++;
        }
        int iSkipWhitespace = skipWhitespace(cArr, i6, i3);
        if (z4 || iSkipWhitespace < i3 || (!z2 && i4 == 0)) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        if (i4 > 19) {
            int i16 = i;
            j = 0;
            int i17 = 0;
            while (i16 < i11) {
                char c6 = cArr[i16];
                if (c6 != c) {
                    if (Long.compareUnsigned(j, 1000000000000000000L) >= 0) {
                        break;
                    }
                    j = ((j * 10) + ((long) c6)) - 48;
                } else {
                    i17++;
                }
                i16++;
                c = CharConst.DOT;
            }
            i9 = (i10 - i16) + i17 + i8;
            z3 = i16 < i11;
        } else {
            j = j2;
            z3 = false;
            i9 = 0;
        }
        return valueOfFloatLiteral(cArr, i2, i3, z, j, i7, z3, i9);
    }

    public long parseFloatingPointLiteral(char[] cArr, int i, int i2) {
        int i3;
        int iCheckBounds = checkBounds(cArr.length, i, i2);
        int iSkipWhitespace = skipWhitespace(cArr, i, iCheckBounds);
        if (iSkipWhitespace == iCheckBounds) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        char cCharAt = cArr[iSkipWhitespace];
        boolean z = cCharAt == '-';
        if ((z || cCharAt == '+') && (cCharAt = charAt(cArr, (iSkipWhitespace = iSkipWhitespace + 1), iCheckBounds)) == 0) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        if (cCharAt >= 'I') {
            return parseNaNOrInfinity(cArr, iSkipWhitespace, iCheckBounds, z);
        }
        boolean z2 = cCharAt == '0';
        if (z2) {
            int i4 = iSkipWhitespace + 1;
            if ((charAt(cArr, i4, iCheckBounds) | ' ') == 120) {
                return parseHexFloatLiteral(cArr, iSkipWhitespace + 2, i, iCheckBounds, z);
            }
            i3 = i4;
        } else {
            i3 = iSkipWhitespace;
        }
        return parseDecFloatLiteral(cArr, i3, i, iCheckBounds, z, z2);
    }

    private long parseHexFloatLiteral(char[] cArr, int i, int i2, int i3, boolean z) {
        int i4;
        int iMin;
        boolean z2;
        int i5;
        int i6;
        long j;
        boolean z3;
        int i7;
        int i8 = -1;
        int i9 = i;
        long j2 = 0;
        char cCharAt = 0;
        boolean z4 = false;
        while (i9 < i3) {
            cCharAt = cArr[i9];
            int iLookupHex = lookupHex(cCharAt);
            if (iLookupHex < 0) {
                if (iLookupHex != -4) {
                    break;
                }
                z4 |= i8 >= 0;
                int i10 = i9;
                while (i10 < i3 - 8) {
                    long jTryToParseEightHexDigits = tryToParseEightHexDigits(cArr, i10 + 1);
                    if (jTryToParseEightHexDigits < 0) {
                        break;
                    }
                    j2 = (j2 << 32) + jTryToParseEightHexDigits;
                    i10 += 8;
                }
                int i11 = i9;
                i9 = i10;
                i8 = i11;
            } else {
                j2 = (j2 << 4) | ((long) iLookupHex);
            }
            i9++;
        }
        int i12 = 1024;
        if (i8 < 0) {
            i4 = i9 - i;
            i8 = i9;
            iMin = 0;
        } else {
            i4 = (i9 - i) - 1;
            iMin = Math.min((i8 - i9) + 1, 1024) * 4;
        }
        boolean z5 = (cCharAt | ' ') == 112;
        if (z5) {
            i5 = i9 + 1;
            char cCharAt2 = charAt(cArr, i5, i3);
            boolean z6 = cCharAt2 == '-';
            if (z6 || cCharAt2 == '+') {
                i5 = i9 + 2;
                cCharAt2 = charAt(cArr, i5, i3);
            }
            char c = (char) (cCharAt2 - '0');
            boolean z7 = z4 | (c >= '\n');
            int i13 = 0;
            while (true) {
                if (i13 < i12) {
                    i13 = (i13 * 10) + c;
                }
                z2 = true;
                i5++;
                cCharAt = charAt(cArr, i5, i3);
                char c2 = (char) (cCharAt - '0');
                if (c2 >= '\n') {
                    break;
                }
                c = c2;
                i12 = 1024;
            }
            if (z6) {
                i13 = -i13;
            }
            iMin += i13;
            i6 = i13;
            z4 = z7;
        } else {
            z2 = true;
            i5 = i9;
            i6 = 0;
        }
        char c3 = cCharAt;
        int i14 = iMin;
        if ((c3 | '\"') == 102) {
            i5++;
        }
        int iSkipWhitespace = skipWhitespace(cArr, i5, i3);
        if (z4 || iSkipWhitespace < i3 || i4 == 0 || !z5) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        if (i4 > 16) {
            iSkipWhitespace = i;
            long j3 = 0;
            int i15 = 0;
            while (iSkipWhitespace < i9) {
                int iLookupHex2 = lookupHex(cArr[iSkipWhitespace]);
                if (iLookupHex2 < 0) {
                    i15++;
                } else {
                    if (Long.compareUnsigned(j3, 1000000000000000000L) >= 0) {
                        break;
                    }
                    j3 = (j3 << 4) | ((long) iLookupHex2);
                }
                iSkipWhitespace++;
            }
            j = j3;
            z3 = iSkipWhitespace < i9 ? z2 : false;
            i7 = i15;
        } else {
            j = j2;
            z3 = false;
            i7 = 0;
        }
        return valueOfHexLiteral(cArr, i2, i3, z, j, i14, z3, (((i8 - iSkipWhitespace) + i7) * 4) + i6);
    }

    private long parseNaNOrInfinity(char[] cArr, int i, int i2, boolean z) {
        char c = cArr[i];
        if (c == 'N') {
            int i3 = i + 2;
            if (i3 < i2 && cArr[i + 1] == 'a' && cArr[i3] == 'N' && skipWhitespace(cArr, i + 3, i2) == i2) {
                return nan();
            }
        } else {
            int i4 = i + 7;
            if (i4 < i2 && c == 'I' && cArr[i + 1] == 'n' && cArr[i + 2] == 'f' && cArr[i + 3] == 'i' && cArr[i + 4] == 'n' && cArr[i + 5] == 'i' && cArr[i + 6] == 't' && cArr[i4] == 'y' && skipWhitespace(cArr, i + 8, i2) == i2) {
                return z ? negativeInfinity() : positiveInfinity();
            }
        }
        throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
    }

    private long tryToParseEightHexDigits(char[] cArr, int i) {
        return FastDoubleSwar.tryToParseEightHexDigits(cArr, i);
    }
}
