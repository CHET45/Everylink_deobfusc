package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

/* JADX INFO: loaded from: classes3.dex */
public class JavaDoubleParser {
    private static final JavaDoubleBitsFromByteArray BYTE_ARRAY_PARSER = new JavaDoubleBitsFromByteArray();
    private static final JavaDoubleBitsFromCharArray CHAR_ARRAY_PARSER = new JavaDoubleBitsFromCharArray();
    private static final JavaDoubleBitsFromCharSequence CHAR_SEQUENCE_PARSER = new JavaDoubleBitsFromCharSequence();

    private JavaDoubleParser() {
    }

    public static double parseDouble(CharSequence charSequence) throws NumberFormatException {
        return parseDouble(charSequence, 0, charSequence.length());
    }

    public static double parseDouble(CharSequence charSequence, int i, int i2) throws NumberFormatException {
        return Double.longBitsToDouble(CHAR_SEQUENCE_PARSER.parseFloatingPointLiteral(charSequence, i, i2));
    }

    public static double parseDouble(byte[] bArr) throws NumberFormatException {
        return parseDouble(bArr, 0, bArr.length);
    }

    public static double parseDouble(byte[] bArr, int i, int i2) throws NumberFormatException {
        return Double.longBitsToDouble(BYTE_ARRAY_PARSER.parseFloatingPointLiteral(bArr, i, i2));
    }

    public static double parseDouble(char[] cArr) throws NumberFormatException {
        return parseDouble(cArr, 0, cArr.length);
    }

    public static double parseDouble(char[] cArr, int i, int i2) throws NumberFormatException {
        return Double.longBitsToDouble(CHAR_ARRAY_PARSER.parseFloatingPointLiteral(cArr, i, i2));
    }
}
