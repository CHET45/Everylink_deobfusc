package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

import kotlin.UByte;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractJavaFloatingPointBitsFromByteArray extends AbstractFloatValueParser {
    abstract long nan();

    abstract long negativeInfinity();

    abstract long positiveInfinity();

    abstract long valueOfFloatLiteral(byte[] bArr, int i, int i2, boolean z, long j, int i3, boolean z2, int i4);

    abstract long valueOfHexLiteral(byte[] bArr, int i, int i2, boolean z, long j, int i3, boolean z2, int i4);

    AbstractJavaFloatingPointBitsFromByteArray() {
    }

    private static int skipWhitespace(byte[] bArr, int i, int i2) {
        while (i < i2 && (bArr[i] & UByte.MAX_VALUE) <= 32) {
            i++;
        }
        return i;
    }

    private long parseDecFloatLiteral(byte[] bArr, int i, int i2, int i3, boolean z, boolean z2) {
        byte b;
        int i4;
        int i5;
        int i6;
        int i7;
        long j;
        boolean z3;
        int i8;
        byte bCharAt;
        int iTryToParseFourDigits;
        int i9 = -1;
        int i10 = i;
        long j2 = 0;
        byte b2 = 0;
        boolean z4 = false;
        while (true) {
            b = 46;
            if (i10 >= i3) {
                break;
            }
            b2 = bArr[i10];
            char c = (char) (b2 - 48);
            if (c >= '\n') {
                if (b2 != 46) {
                    break;
                }
                z4 |= i9 >= 0;
                int i11 = i10;
                while (i11 < i3 - 4 && (iTryToParseFourDigits = FastDoubleSwar.tryToParseFourDigits(bArr, i11 + 1)) >= 0) {
                    j2 = (j2 * 10000) + ((long) iTryToParseFourDigits);
                    i11 += 4;
                }
                int i12 = i10;
                i10 = i11;
                i9 = i12;
            } else {
                j2 = (j2 * 10) + ((long) c);
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
        if ((b2 | 32) == 101) {
            i6 = i10 + 1;
            byte bCharAt2 = charAt(bArr, i6, i3);
            boolean z5 = bCharAt2 == 45;
            if (z5 || bCharAt2 == 43) {
                i6 = i10 + 2;
                bCharAt2 = charAt(bArr, i6, i3);
            }
            char c2 = (char) (bCharAt2 - 48);
            boolean z6 = z4 | (c2 >= '\n');
            int i13 = 0;
            while (true) {
                if (i13 < 1024) {
                    i13 = (i13 * 10) + c2;
                }
                i6++;
                bCharAt = charAt(bArr, i6, i3);
                char c3 = (char) (bCharAt - 48);
                if (c3 >= '\n') {
                    break;
                }
                c2 = c3;
            }
            if (z5) {
                i13 = -i13;
            }
            i5 += i13;
            z4 = z6;
            int i14 = i13;
            b2 = bCharAt;
            i7 = i14;
        } else {
            i6 = i10;
            i7 = 0;
        }
        if ((b2 | 34) == 102) {
            i6++;
        }
        int iSkipWhitespace = skipWhitespace(bArr, i6, i3);
        if (z4 || iSkipWhitespace < i3 || (!z2 && i4 == 0)) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        if (i4 > 19) {
            int i15 = i;
            int i16 = 0;
            long j3 = 0;
            while (i15 < i10) {
                byte b3 = bArr[i15];
                if (b3 != b) {
                    if (Long.compareUnsigned(j3, 1000000000000000000L) >= 0) {
                        break;
                    }
                    j3 = ((j3 * 10) + ((long) b3)) - 48;
                } else {
                    i16++;
                }
                i15++;
                b = 46;
            }
            i8 = (i9 - i15) + i16 + i7;
            j = j3;
            z3 = i15 < i10;
        } else {
            j = j2;
            z3 = false;
            i8 = 0;
        }
        return valueOfFloatLiteral(bArr, i2, i3, z, j, i5, z3, i8);
    }

    public long parseFloatingPointLiteral(byte[] bArr, int i, int i2) {
        int i3;
        int iCheckBounds = checkBounds(bArr.length, i, i2);
        int iSkipWhitespace = skipWhitespace(bArr, i, iCheckBounds);
        if (iSkipWhitespace == iCheckBounds) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        byte bCharAt = bArr[iSkipWhitespace];
        boolean z = bCharAt == 45;
        if ((z || bCharAt == 43) && (bCharAt = charAt(bArr, (iSkipWhitespace = iSkipWhitespace + 1), iCheckBounds)) == 0) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        if (bCharAt >= 73) {
            return parseNaNOrInfinity(bArr, iSkipWhitespace, iCheckBounds, z);
        }
        boolean z2 = bCharAt == 48;
        if (z2) {
            int i4 = iSkipWhitespace + 1;
            if ((charAt(bArr, i4, iCheckBounds) | 32) == 120) {
                return parseHexFloatingPointLiteral(bArr, iSkipWhitespace + 2, i, iCheckBounds, z);
            }
            i3 = i4;
        } else {
            i3 = iSkipWhitespace;
        }
        return parseDecFloatLiteral(bArr, i3, i, iCheckBounds, z, z2);
    }

    private long parseHexFloatingPointLiteral(byte[] bArr, int i, int i2, int i3, boolean z) {
        int i4;
        int i5;
        int iMin;
        int i6;
        int i7;
        int i8;
        boolean z2;
        byte bCharAt;
        int i9 = -1;
        int i10 = i;
        long j = 0;
        byte b = 0;
        int i11 = 0;
        while (true) {
            if (i10 >= i3) {
                break;
            }
            b = bArr[i10];
            int iLookupHex = lookupHex(b);
            if (iLookupHex < 0) {
                if (iLookupHex != -4) {
                    break;
                }
                i11 |= i9 < 0 ? 0 : 1;
                i9 = i10;
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
        boolean z3 = (b | 32) == 112;
        if (z3) {
            i6 = i10 + 1;
            byte bCharAt2 = charAt(bArr, i6, i3);
            boolean z4 = bCharAt2 == 45;
            if (z4 || bCharAt2 == 43) {
                i6 = i10 + 2;
                bCharAt2 = charAt(bArr, i6, i3);
            }
            char c = (char) (bCharAt2 - 48);
            int i12 = i11 | (c >= '\n' ? 1 : 0);
            int i13 = 0;
            while (true) {
                if (i13 < 1024) {
                    i13 = (i13 * 10) + c;
                }
                i6 += i4;
                bCharAt = charAt(bArr, i6, i3);
                char c2 = (char) (bCharAt - 48);
                if (c2 >= '\n') {
                    break;
                }
                c = c2;
                i4 = 1;
            }
            if (z4) {
                i13 = -i13;
            }
            iMin += i13;
            b = bCharAt;
            i7 = i13;
            i11 = i12;
        } else {
            i6 = i10;
            i7 = 0;
        }
        if ((b | 34) == 102) {
            i6++;
        }
        int iSkipWhitespace = skipWhitespace(bArr, i6, i3);
        if (i11 != 0 || iSkipWhitespace < i3 || i5 == 0 || !z3) {
            throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
        }
        if (i5 > 16) {
            int i14 = i;
            int i15 = 0;
            j = 0;
            while (i14 < i10) {
                int iLookupHex2 = lookupHex(bArr[i14]);
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
        return valueOfHexLiteral(bArr, i2, i3, z, j, iMin, z2, (((i9 - iSkipWhitespace) + i8) * 4) + i7);
    }

    private long parseNaNOrInfinity(byte[] bArr, int i, int i2, boolean z) {
        if (bArr[i] == 78) {
            int i3 = i + 2;
            if (i3 < i2 && bArr[i + 1] == 97 && bArr[i3] == 78 && skipWhitespace(bArr, i + 3, i2) == i2) {
                return nan();
            }
        } else if (i + 7 < i2 && FastDoubleSwar.readLongLE(bArr, i) == 8751735898823355977L && skipWhitespace(bArr, i + 8, i2) == i2) {
            return z ? negativeInfinity() : positiveInfinity();
        }
        throw new NumberFormatException(AbstractNumberParser.SYNTAX_ERROR);
    }
}
