package com.aivox.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class BreakUpSentence {
    private static final String SEPARATOR_REGEX = "[.?!。？！]";

    public static String[] splitSentence(String str) {
        Pattern patternCompile = Pattern.compile(SEPARATOR_REGEX);
        Matcher matcher = patternCompile.matcher(str);
        String[] strArrSplit = patternCompile.split(str);
        if (strArrSplit.length > 0) {
            for (int i = 0; i < strArrSplit.length; i++) {
                if (matcher.find()) {
                    strArrSplit[i] = strArrSplit[i] + matcher.group();
                }
            }
        }
        return strArrSplit;
    }
}
