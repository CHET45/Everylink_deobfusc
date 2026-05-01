package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.support.tuple.impl.Pair;
import com.github.houbb.heaven.util.lang.StringUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class PlaceholderUtil {
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";
    public static final String DEFAULT_VALUE_SEPARATOR = ":";

    private PlaceholderUtil() {
    }

    public static Pair<String, String> parsePlaceholder(String str) {
        String strTrim = StringUtil.trim(str);
        if (StringUtil.isEmpty(strTrim)) {
            return Pair.m528of((Object) null, (Object) null);
        }
        if (!strTrim.startsWith(DEFAULT_PLACEHOLDER_PREFIX) || !strTrim.endsWith(DEFAULT_PLACEHOLDER_SUFFIX)) {
            return Pair.m528of((Object) null, (Object) null);
        }
        String strSubstring = strTrim.substring(DEFAULT_PLACEHOLDER_PREFIX.length());
        String strSubstring2 = strSubstring.substring(0, strSubstring.length() - DEFAULT_PLACEHOLDER_SUFFIX.length());
        int iIndexOf = strSubstring2.indexOf(":");
        if (iIndexOf < 0) {
            return Pair.m528of(strSubstring2, (Object) null);
        }
        return Pair.m528of(strSubstring2.substring(0, iIndexOf), strSubstring2.substring(iIndexOf + 1));
    }

    public static void main(String[] strArr) {
        System.out.println(parsePlaceholder(null));
        System.out.println(parsePlaceholder("asdfsa"));
        System.out.println(parsePlaceholder("${name}"));
        System.out.println(parsePlaceholder("${name:ruo}"));
    }
}
