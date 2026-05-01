package com.fasterxml.jackson.core.internal.shaded.fdp.v2_18_4_1;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractBigIntegerParser extends AbstractNumberParser {
    private static final int MAX_DECIMAL_DIGITS = 646456993;
    private static final int MAX_HEX_DIGITS = 536870912;
    static final int RECURSION_THRESHOLD = 400;

    protected static boolean hasManyDigits(int i) {
        return i > 18;
    }

    AbstractBigIntegerParser() {
    }

    protected static void checkHexBigIntegerBounds(int i) {
        if (i > MAX_HEX_DIGITS) {
            throw new NumberFormatException(AbstractNumberParser.VALUE_EXCEEDS_LIMITS);
        }
    }

    protected static void checkDecBigIntegerBounds(int i) {
        if (i > MAX_DECIMAL_DIGITS) {
            throw new NumberFormatException(AbstractNumberParser.VALUE_EXCEEDS_LIMITS);
        }
    }
}
