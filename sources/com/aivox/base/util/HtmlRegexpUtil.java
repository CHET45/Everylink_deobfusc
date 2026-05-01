package com.aivox.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class HtmlRegexpUtil {
    private static final String regxpForHtml = "<([^>]*)>";
    private static final String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\"";
    private static final String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>";

    public String replaceTag(String str) {
        if (!hasSpecialChars(str)) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(str.length());
        for (int i = 0; i <= str.length() - 1; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"') {
                stringBuffer.append("&quot;");
            } else if (cCharAt == '&') {
                stringBuffer.append("&amp;");
            } else if (cCharAt == '<') {
                stringBuffer.append("&lt;");
            } else if (cCharAt == '>') {
                stringBuffer.append("&gt;");
            } else {
                stringBuffer.append(cCharAt);
            }
        }
        return stringBuffer.toString();
    }

    public boolean hasSpecialChars(String str) {
        if (str == null || str.length() <= 0) {
            return false;
        }
        boolean z = false;
        for (int i = 0; i <= str.length() - 1; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"' || cCharAt == '&' || cCharAt == '<' || cCharAt == '>') {
                z = true;
            }
        }
        return z;
    }

    public static String filterHtml(String str) {
        Matcher matcher = Pattern.compile(regxpForHtml).matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        for (boolean zFind = matcher.find(); zFind; zFind = matcher.find()) {
            matcher.appendReplacement(stringBuffer, "");
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public static String fiterHtmlTag(String str, String str2) {
        Matcher matcher = Pattern.compile("<\\s*" + str2 + "\\s+([^>]*)\\s*>").matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        for (boolean zFind = matcher.find(); zFind; zFind = matcher.find()) {
            matcher.appendReplacement(stringBuffer, "");
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public static String replaceHtmlTag(String str, String str2, String str3, String str4, String str5) {
        Pattern patternCompile = Pattern.compile("<\\s*" + str2 + "\\s+([^>]*)\\s*>");
        Pattern patternCompile2 = Pattern.compile(str3 + "=\"([^\"]+)\"");
        Matcher matcher = patternCompile.matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        for (boolean zFind = matcher.find(); zFind; zFind = matcher.find()) {
            StringBuffer stringBuffer2 = new StringBuffer();
            Matcher matcher2 = patternCompile2.matcher(matcher.group(1));
            if (matcher2.find()) {
                matcher2.appendReplacement(stringBuffer2, str4 + matcher2.group(1) + str5);
            }
            matcher.appendReplacement(stringBuffer, stringBuffer2.toString());
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }
}
