package com.github.houbb.heaven.util.util.regex;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes3.dex */
public final class RegexUtil {
    private static final String[] SPECIAL_CHARS = {"\\", PunctuationConst.DOLLAR, "(", ")", "*", Marker.ANY_NON_NULL_MARKER, ".", "[", "]", PunctuationConst.QUESTION_MARK, PunctuationConst.CARET, "{", PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX, PunctuationConst.f489OR};
    private static final Pattern PUNCTUATION_PATTERN = Pattern.compile("\\p{P}");
    private static final Pattern LETTER_PATTERN = Pattern.compile("\\p{L}");
    private static final Pattern MARKABLE_PATTERN = Pattern.compile("\\p{M}");
    private static final Pattern DELIMITER_PATTERN = Pattern.compile("\\p{Z}");
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("\\p{S}");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\p{N}");
    private static final Pattern OTHER_CHARS_PATTERN = Pattern.compile("\\p{C}");
    private static final Pattern EMAIL_ENGLISH_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    private static final Pattern EMAIL_CHINESE_PATTERN = Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\\\d{8}$");
    private static final Pattern URL_PATTERN = Pattern.compile("^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\/])+(\\\\?{0,1}(([A-Za-z0-9-~]+\\\\={0,1})([A-Za-z0-9-~]*)\\\\&{0,1})*)$");
    private static final Pattern WEB_SITE_PATTERN = Pattern.compile("^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$");
    private static final Pattern EMOJI_PATTERN = Pattern.compile("(?:[\\uD83C\\uDF00-\\uD83D\\uDDFF]|[\\uD83E\\uDD00-\\uD83E\\uDDFF]|[\\uD83D\\uDE00-\\uD83D\\uDE4F]|[\\uD83D\\uDE80-\\uD83D\\uDEFF]|[\\u2600-\\u26FF]\\uFE0F?|[\\u2700-\\u27BF]\\uFE0F?|\\u24C2\\uFE0F?|[\\uD83C\\uDDE6-\\uD83C\\uDDFF]{1,2}|[\\uD83C\\uDD70\\uD83C\\uDD71\\uD83C\\uDD7E\\uD83C\\uDD7F\\uD83C\\uDD8E\\uD83C\\uDD91-\\uD83C\\uDD9A]\\uFE0F?|[\\u0023\\u002A\\u0030-\\u0039]\\uFE0F?\\u20E3|[\\u2194-\\u2199\\u21A9-\\u21AA]\\uFE0F?|[\\u2B05-\\u2B07\\u2B1B\\u2B1C\\u2B50\\u2B55]\\uFE0F?|[\\u2934\\u2935]\\uFE0F?|[\\u3030\\u303D]\\uFE0F?|[\\u3297\\u3299]\\uFE0F?|[\\uD83C\\uDE01\\uD83C\\uDE02\\uD83C\\uDE1A\\uD83C\\uDE2F\\uD83C\\uDE32-\\uD83C\\uDE3A\\uD83C\\uDE50\\uD83C\\uDE51]\\uFE0F?|[\\u203C\\u2049]\\uFE0F?|[\\u25AA\\u25AB\\u25B6\\u25C0\\u25FB-\\u25FE]\\uFE0F?|[\\u00A9\\u00AE]\\uFE0F?|[\\u2122\\u2139]\\uFE0F?|\\uD83C\\uDC04\\uFE0F?|\\uD83C\\uDCCF\\uFE0F?|[\\u231A\\u231B\\u2328\\u23CF\\u23E9-\\u23F3\\u23F8-\\u23FA]\\uFE0F?)");
    private static final Pattern IP_PATTERN = Pattern.compile("^\\d{1,3}(.\\d{1,3}){3}$");

    private RegexUtil() {
    }

    public static boolean isIp(String str) {
        if (StringUtil.isEmptyTrim(str)) {
            return false;
        }
        return IP_PATTERN.matcher(str).matches();
    }

    public static String escapeWord(String str) {
        if (StringUtil.isNotBlank(str)) {
            for (String str2 : SPECIAL_CHARS) {
                if (str.contains(str2)) {
                    str = str.replace(str2, "\\" + str2);
                }
            }
        }
        return str;
    }

    public static boolean isEmoji(String str) {
        return EMOJI_PATTERN.matcher(str).find();
    }

    public static boolean isPunctuation(String str) {
        return isPatternMatch(str, PUNCTUATION_PATTERN);
    }

    public static boolean isMarkable(String str) {
        return isPatternMatch(str, MARKABLE_PATTERN);
    }

    public static boolean isSymbol(String str) {
        return isPatternMatch(str, SYMBOL_PATTERN);
    }

    public static boolean isOtherChars(String str) {
        return isPatternMatch(str, OTHER_CHARS_PATTERN);
    }

    public static boolean isNumber(String str) {
        return isPatternMatch(str, NUMBER_PATTERN);
    }

    public static boolean isEmail(String str) {
        return isPatternMatch(str, EMAIL_ENGLISH_PATTERN);
    }

    public static boolean isUrl(String str) {
        return isPatternMatch(str, URL_PATTERN);
    }

    public static boolean isWebSite(String str) {
        return isPatternMatch(str, WEB_SITE_PATTERN);
    }

    private static boolean isPatternMatch(String str, Pattern pattern) {
        return pattern.matcher(str).find();
    }

    public static boolean match(Pattern pattern, String str) {
        return pattern.matcher(str).find();
    }

    public static boolean match(String str, String str2) {
        return match(Pattern.compile(str), str2);
    }

    public static boolean hasMatch(List<String> list, String str) {
        if (CollectionUtil.isEmpty(list)) {
            return false;
        }
        Pattern patternCompile = Pattern.compile(str);
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (match(patternCompile, it.next())) {
                return true;
            }
        }
        return false;
    }
}
