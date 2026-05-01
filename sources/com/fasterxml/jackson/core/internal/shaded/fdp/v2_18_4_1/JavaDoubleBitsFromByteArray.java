package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes3.dex */
final class JavaDoubleBitsFromByteArray extends AbstractJavaFloatingPointBitsFromByteArray {
    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromByteArray
    long nan() {
        return Double.doubleToRawLongBits(Double.NaN);
    }

    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromByteArray
    long negativeInfinity() {
        return Double.doubleToRawLongBits(Double.NEGATIVE_INFINITY);
    }

    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromByteArray
    long positiveInfinity() {
        return Double.doubleToRawLongBits(Double.POSITIVE_INFINITY);
    }

    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromByteArray
    long valueOfFloatLiteral(byte[] bArr, int i, int i2, boolean z, long j, int i3, boolean z2, int i4) {
        double dTryDecFloatToDoubleTruncated = FastDoubleMath.tryDecFloatToDoubleTruncated(z, j, i3, z2, i4);
        if (Double.isNaN(dTryDecFloatToDoubleTruncated)) {
            dTryDecFloatToDoubleTruncated = Double.parseDouble(new String(bArr, i, i2 - i, StandardCharsets.ISO_8859_1));
        }
        return Double.doubleToRawLongBits(dTryDecFloatToDoubleTruncated);
    }

    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromByteArray
    long valueOfHexLiteral(byte[] bArr, int i, int i2, boolean z, long j, int i3, boolean z2, int i4) {
        double dTryHexFloatToDoubleTruncated = FastDoubleMath.tryHexFloatToDoubleTruncated(z, j, i3, z2, i4);
        if (Double.isNaN(dTryHexFloatToDoubleTruncated)) {
            dTryHexFloatToDoubleTruncated = Double.parseDouble(new String(bArr, i, i2 - i, StandardCharsets.ISO_8859_1));
        }
        return Double.doubleToRawLongBits(dTryHexFloatToDoubleTruncated);
    }
}
