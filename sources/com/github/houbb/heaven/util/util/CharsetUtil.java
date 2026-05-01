package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.util.lang.StringUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public final class CharsetUtil {
    private static final Pattern UNICODE_PATTERN = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

    public static boolean isChinese(char c) {
        return c >= 19968 && c <= 40869;
    }

    private CharsetUtil() {
    }

    public static String unicodeToZh(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split("\\\\u");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < strArrSplit.length; i++) {
            String str2 = strArrSplit[i];
            sb.append((char) Integer.valueOf(str2.substring(0, 4), 16).intValue());
            if (str2.length() > 4) {
                sb.append(str2.substring(4));
            }
        }
        return sb.toString();
    }

    public static String zhToUnicode(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            sb.append("\\u").append(Integer.toString(c, 16));
        }
        return sb.toString();
    }

    public static boolean isContainsChinese(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllChinese(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!isChinese(c)) {
                return false;
            }
        }
        return true;
    }

    public static String unicodeToString(String str) {
        if (StringUtil.isEmptyTrim(str)) {
            return str;
        }
        Matcher matcher = UNICODE_PATTERN.matcher(str);
        while (matcher.find()) {
            str = str.replace(matcher.group(1), ((char) Integer.parseInt(matcher.group(2), 16)) + "");
        }
        return str;
    }
}
