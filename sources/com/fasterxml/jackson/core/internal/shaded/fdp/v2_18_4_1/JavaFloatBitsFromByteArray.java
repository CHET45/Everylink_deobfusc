package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes3.dex */
final class JavaFloatBitsFromByteArray extends AbstractJavaFloatingPointBitsFromByteArray {
    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromByteArray
    long nan() {
        return Float.floatToRawIntBits(Float.NaN);
    }

    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromByteArray
    long negativeInfinity() {
        return Float.floatToRawIntBits(Float.NEGATIVE_INFINITY);
    }

    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromByteArray
    long positiveInfinity() {
        return Float.floatToRawIntBits(Float.POSITIVE_INFINITY);
    }

    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromByteArray
    long valueOfFloatLiteral(byte[] bArr, int i, int i2, boolean z, long j, int i3, boolean z2, int i4) {
        float fTryDecFloatToFloatTruncated = FastFloatMath.tryDecFloatToFloatTruncated(z, j, i3, z2, i4);
        if (Float.isNaN(fTryDecFloatToFloatTruncated)) {
            fTryDecFloatToFloatTruncated = Float.parseFloat(new String(bArr, i, i2 - i, StandardCharsets.ISO_8859_1));
        }
        return Float.floatToRawIntBits(fTryDecFloatToFloatTruncated);
    }

    @Override // com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1.AbstractJavaFloatingPointBitsFromByteArray
    long valueOfHexLiteral(byte[] bArr, int i, int i2, boolean z, long j, int i3, boolean z2, int i4) {
        float fTryHexFloatToFloatTruncated = FastFloatMath.tryHexFloatToFloatTruncated(z, j, i3, z2, i4);
        if (Float.isNaN(fTryHexFloatToFloatTruncated)) {
            fTryHexFloatToFloatTruncated = Float.parseFloat(new String(bArr, i, i2 - i, StandardCharsets.ISO_8859_1));
        }
        return Float.floatToRawIntBits(fTryHexFloatToFloatTruncated);
    }
}
