package com.aivox.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class EmojiFilter {
    public static String filterEmoji(String str) {
        if (str == null || str.trim().isEmpty()) {
            return str;
        }
        Matcher matcher = Pattern.compile("[^\\u0000-\\uFFFF]|\\p{So}|\\p{Cn}", 66).matcher(str);
        return matcher.find() ? matcher.replaceAll("") : str;
    }
}
