package com.github.houbb.nlp.common.util;

import com.github.houbb.heaven.util.lang.CharUtil;
import com.github.houbb.heaven.util.util.ArrayPrimitiveUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class CharUtils {
    private static final char[] CONNECTOR_CHARS = "+#&.-_'".toCharArray();

    private CharUtils() {
    }

    public static boolean isConsequent(char c) {
        return CharUtil.isChinese(c) || CharUtil.isDigitOrLetter(c) || CharUtil.isDigit(c) || isConnectorChars(c);
    }

    public static boolean isConnectorChars(char c) {
        return ArrayPrimitiveUtil.contains(CONNECTOR_CHARS, c);
    }

    public static boolean isLetterOrConnector(char c) {
        return c == '\'' || CharUtil.isEnglish(c);
    }
}
