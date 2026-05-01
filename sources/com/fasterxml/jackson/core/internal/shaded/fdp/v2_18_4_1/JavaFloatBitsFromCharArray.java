package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

/* JADX INFO: loaded from: classes3.dex */
final class JavaFloatBitsFromCharArray extends AbstractJavaFloatingPointBitsFromCharArray {
    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromCharArray
    long nan() {
        return Float.floatToRawIntBits(Float.NaN);
    }

    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromCharArray
    long negativeInfinity() {
        return Float.floatToRawIntBits(Float.NEGATIVE_INFINITY);
    }

    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromCharArray
    long positiveInfinity() {
        return Float.floatToRawIntBits(Float.POSITIVE_INFINITY);
    }

    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromCharArray
    long valueOfFloatLiteral(char[] cArr, int i, int i2, boolean z, long j, int i3, boolean z2, int i4) {
        return Float.isNaN(FastFloatMath.tryDecFloatToFloatTruncated(z, j, i3, z2, i4)) ? Float.floatToRawIntBits(Float.parseFloat(new String(cArr, i, i2 - i))) : Float.floatToRawIntBits(r4);
    }

    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromCharArray
    long valueOfHexLiteral(char[] cArr, int i, int i2, boolean z, long j, int i3, boolean z2, int i4) {
        float fTryHexFloatToFloatTruncated = FastFloatMath.tryHexFloatToFloatTruncated(z, j, i3, z2, i4);
        if (Float.isNaN(fTryHexFloatToFloatTruncated)) {
            fTryHexFloatToFloatTruncated = Float.parseFloat(new String(cArr, i, i2 - i));
        }
        return Float.floatToRawIntBits(fTryHexFloatToFloatTruncated);
    }
}
