package com.github.houbb.heaven.util.lang;

import java.lang.Character;

/* JADX INFO: loaded from: classes3.dex */
public final class CharUtil {
    public static boolean isAscii(char c) {
        return c <= 127;
    }

    public static boolean isChinese(char c) {
        return c >= 19968 && c <= 40869;
    }

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isEmpty(Character ch) {
        return ch == null;
    }

    public static boolean isEnglish(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }

    public static boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    public static char toFullWidth(char c) {
        if (c == ' ') {
            return (char) 12288;
        }
        return (c < '!' || c > '~') ? c : (char) (c + 65248);
    }

    public static char toHalfWidth(char c) {
        if (c == 12288) {
            return ' ';
        }
        return (c <= 65280 || c >= 65375) ? c : (char) (c - 65248);
    }

    private CharUtil() {
    }

    public static boolean isNotEmpty(Character ch) {
        return !isEmpty(ch);
    }

    public static String repeat(char c, int i) {
        return StringUtil.repeat(String.valueOf(c), i);
    }

    public static boolean isChinesePunctuation(char c) {
        return Character.UnicodeScript.of(c) == Character.UnicodeScript.HAN;
    }

    public static boolean isSpace(char c) {
        return Character.isSpaceChar(c) || 19 == c;
    }

    public static boolean isNotSpace(char c) {
        return !isSpace(c);
    }

    public static boolean isDigitOrLetter(char c) {
        return Character.isDigit(c) || Character.isLowerCase(c) || Character.isUpperCase(c);
    }

    public static boolean isEmilChar(char c) {
        return isDigitOrLetter(c) || '_' == c || '-' == c || c == '.' || c == '@';
    }

    public static boolean isWebSiteChar(char c) {
        return isDigitOrLetter(c) || '-' == c || '.' == c;
    }

    public static boolean isUrlChar(char c) {
        return isDigitOrLetter(c) || '-' == c || '.' == c;
    }

    public static boolean isNotDigit(char c) {
        return !isDigit(c);
    }

    public static boolean isNotNumber(char c) {
        return !isNumber(c);
    }

    public static boolean isNotAscii(char c) {
        return !isAscii(c);
    }
}
