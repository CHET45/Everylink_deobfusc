package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

import com.github.houbb.heaven.constant.CharConst;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractJavaFloatingPointBitsFromCharSequence extends AbstractFloatValueParser {
    abstract long nan();

    abstract long negativeInfinity();

    abstract long positiveInfinity();

    abstract long valueOfFloatLiteral(CharSequence charSequence, int i, int i2, boolean z, long j, int i3, boolean z2, int i4);

    abstract long valueOfHexLiteral(CharSequence charSequence, int i, int i2, boolean z, long j, int i3, boolean z2, int i4);

    AbstractJavaFloatingPointBitsFromCharSequence() {
    }

    private static int skipWhitespace(CharSequence charSequence, int i, int i2) {
        while (i < i2 && charSequence.charAt(i) <= ' ') {
            i++;
        }
        return i;
    }

    private long parseDecFloatLiteral(CharSequence charSequence, int i, int i2, int i3, boolean z, boolean z2) {
        char c;
        int i4;
        int i5;
        int i6;
        int i7;
        long j;
        boolean z3;
        int i8;
        char cCharAt;
        int i9 = -1;
        int i10 = i;
        long j2 = 0;
        char cCharAt2 = 0;
        boolean z4 = false;
        while (true) {
            c = CharConst.DOT;
            if (i10 >= i3) {
                break;
            }
            cCharAt2 = charSequence.charAt(i10);
            char c2 = (char) (cCharAt2 - '0');
            if (c2 >= '\n') {
                if (cCharAt2 != '.') {
                    break;
                }
                z4 |= i9 >= 0;
                i9 = i10;
            } else {
                j2 = (j2 * 10) + ((long) c2);
            }
            i10++;
        }
        if (i9 < 0) {
            i4 = i10 - i;
            i9 = i10;
            i5 = 0;
        } else {
            i4 = (i10 - i) - 1;
            i5 = (i9 - i10) + 1;
        }
        if ((cCharAt2 | ' ') == 101) {
            i6 = i10 + 1;
            char cCharAt3 = charAt(charSequence, i6, i3);
            boolean z5 = cCharAt3 == '-';
            if (z5 || cCharAt3 == '+') {
                i6 = i10 + 2;
                cCharAt3 = charAt(charSequence, i6, i3);
            }
            char c3 = (char) (cCharAt3 - '0');
            boolean z6 = z4 | (c3 >= '\n');
            int i11 = 0;
            while (true) {
                if (i11 < 1024) {
                    i11 = (i11 * 10) + c3;
                }
                i6++;
                cCharAt = charAt(charSequence, i6, i3);
                char c4 = (char) (cCharAt - '0');
                if (c4 >= '\n') {
                    break;
                }
                c3 = c4;
            }
            if (z5) {
                i11 = -i11;
            }
            i5 += i11;
            z4 = z6;
            int i12 = i11;
            cCharAt2 = cCharAt;
            i7 = i12;
        } else {
            i6 = i10;
            i7 = 0;
        }
        if ((cCharAt2 | '\"') == 102) {
            i6++;
        }
        int iSkipWhitespace = skipWhitespace(charSequence, i6, i3);
        if (z4 || iSkipWhitespace < i3 || (!z2 && i4 == 0)) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        if (i4 > 19) {
            int i13 = i;
            int i14 = 0;
            long j3 = 0;
            while (i13 < i10) {
                char cCharAt4 = charSequence.charAt(i13);
                if (cCharAt4 != c) {
                    if (Long.compareUnsigned(j3, 1000000000000000000L) >= 0) {
                        break;
                    }
                    j3 = ((j3 * 10) + ((long) cCharAt4)) - 48;
                } else {
                    i14++;
                }
                i13++;
                c = CharConst.DOT;
            }
            i8 = (i9 - i13) + i14 + i7;
            j = j3;
            z3 = i13 < i10;
        } else {
            j = j2;
            z3 = false;
            i8 = 0;
        }
        return valueOfFloatLiteral(charSequence, i2, i3, z, j, i5, z3, i8);
    }

    public final long parseFloatingPointLiteral(CharSequence charSequence, int i, int i2) {
        int i3;
        int iCheckBounds = checkBounds(charSequence.length(), i, i2);
        int iSkipWhitespace = skipWhitespace(charSequence, i, iCheckBounds);
        if (iSkipWhitespace == iCheckBounds) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        char cCharAt = charSequence.charAt(iSkipWhitespace);
        boolean z = cCharAt == '-';
        if ((z || cCharAt == '+') && (cCharAt = charAt(charSequence, (iSkipWhitespace = iSkipWhitespace + 1), iCheckBounds)) == 0) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        if (cCharAt >= 'I') {
            return parseNaNOrInfinity(charSequence, iSkipWhitespace, iCheckBounds, z);
        }
        boolean z2 = cCharAt == '0';
        if (z2) {
            int i4 = iSkipWhitespace + 1;
            if ((charAt(charSequence, i4, iCheckBounds) | ' ') == 120) {
                return parseHexFloatLiteral(charSequence, iSkipWhitespace + 2, i, iCheckBounds, z);
            }
            i3 = i4;
        } else {
            i3 = iSkipWhitespace;
        }
        return parseDecFloatLiteral(charSequence, i3, i, iCheckBounds, z, z2);
    }

    private long parseHexFloatLiteral(CharSequence charSequence, int i, int i2, int i3, boolean z) {
        int i4;
        int i5;
        int iMin;
        int i6;
        int i7;
        int i8;
        boolean z2;
        char cCharAt;
        int i9 = -1;
        int i10 = i;
        long j = 0;
        char cCharAt2 = 0;
        boolean z3 = false;
        while (true) {
            i4 = 1;
            if (i10 >= i3) {
                break;
            }
            cCharAt2 = charSequence.charAt(i10);
            int iLookupHex = lookupHex(cCharAt2);
            if (iLookupHex < 0) {
                if (iLookupHex != -4) {
                    break;
                }
                z3 |= i9 >= 0;
                int i11 = i10;
                while (i11 < i3 - 8) {
                    long jTryToParseEightHexDigits = FastDoubleSwar.tryToParseEightHexDigits(charSequence, i11 + 1);
                    if (jTryToParseEightHexDigits < 0) {
                        break;
                    }
                    j = (j << 32) + jTryToParseEightHexDigits;
                    i11 += 8;
                }
                int i12 = i10;
                i10 = i11;
                i9 = i12;
            } else {
                j = (j << 4) | ((long) iLookupHex);
            }
            i10++;
        }
        if (i9 < 0) {
            i5 = i10 - i;
            i9 = i10;
            iMin = 0;
        } else {
            i5 = (i10 - i) - 1;
            iMin = Math.min((i9 - i10) + 1, 1024) * 4;
        }
        boolean z4 = (cCharAt2 | ' ') == 112;
        if (z4) {
            i6 = i10 + 1;
            char cCharAt3 = charAt(charSequence, i6, i3);
            boolean z5 = cCharAt3 == '-';
            if (z5 || cCharAt3 == '+') {
                i6 = i10 + 2;
                cCharAt3 = charAt(charSequence, i6, i3);
            }
            char c = (char) (cCharAt3 - '0');
            boolean z6 = z3 | (c >= '\n');
            int i13 = 0;
            while (true) {
                if (i13 < 1024) {
                    i13 = (i13 * 10) + c;
                }
                i6 += i4;
                cCharAt = charAt(charSequence, i6, i3);
                char c2 = (char) (cCharAt - '0');
                if (c2 >= '\n') {
                    break;
                }
                c = c2;
                i4 = 1;
            }
            if (z5) {
                i13 = -i13;
            }
            iMin += i13;
            cCharAt2 = cCharAt;
            i7 = i13;
            z3 = z6;
        } else {
            i6 = i10;
            i7 = 0;
        }
        if ((cCharAt2 | '\"') == 102) {
            i6++;
        }
        int iSkipWhitespace = skipWhitespace(charSequence, i6, i3);
        if (z3 || iSkipWhitespace < i3 || i5 == 0 || !z4) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        if (i5 > 16) {
            int i14 = i;
            int i15 = 0;
            j = 0;
            while (i14 < i10) {
                int iLookupHex2 = lookupHex(charSequence.charAt(i14));
                if (iLookupHex2 < 0) {
                    i15++;
                } else {
                    if (Long.compareUnsigned(j, 1000000000000000000L) >= 0) {
                        break;
                    }
                    j = (j << 4) | ((long) iLookupHex2);
                }
                i14++;
            }
            z2 = i14 < i10;
            int i16 = i15;
            iSkipWhitespace = i14;
            i8 = i16;
        } else {
            i8 = 0;
            z2 = false;
        }
        return valueOfHexLiteral(charSequence, i2, i3, z, j, iMin, z2, (((i9 - iSkipWhitespace) + i8) * 4) + i7);
    }

    private long parseNaNOrInfinity(CharSequence charSequence, int i, int i2, boolean z) {
        if (charSequence.charAt(i) == 'N') {
            int i3 = i + 2;
            if (i3 < i2 && charSequence.charAt(i + 1) == 'a' && charSequence.charAt(i3) == 'N' && skipWhitespace(charSequence, i + 3, i2) == i2) {
                return nan();
            }
        } else {
            int i4 = i + 7;
            if (i4 < i2 && charSequence.charAt(i) == 'I' && charSequence.charAt(i + 1) == 'n' && charSequence.charAt(i + 2) == 'f' && charSequence.charAt(i + 3) == 'i' && charSequence.charAt(i + 4) == 'n' && charSequence.charAt(i + 5) == 'i' && charSequence.charAt(i + 6) == 't' && charSequence.charAt(i4) == 'y' && skipWhitespace(charSequence, i + 8, i2) == i2) {
                return z ? negativeInfinity() : positiveInfinity();
            }
        }
        throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
    }
}
