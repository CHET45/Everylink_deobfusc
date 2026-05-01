package com.aivox.base.util;

import com.blankj.utilcode.constant.RegexConstants;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class RegexUtil {
    public static boolean isFileName(String str) {
        return true;
    }

    private static boolean basicRegex(String str, String str2) {
        if (BaseStringUtil.isEmpty(str2)) {
            return false;
        }
        return Pattern.compile(str).matcher(str2).matches();
    }

    public static boolean isUserName(String str) {
        return basicRegex("^[A-Za-z0-9_]*$", str);
    }

    public static boolean isNickName(String str) {
        return basicRegex("^[A-Za-z0-9_\\u4e00-\\u9fa5]*$", str);
    }

    public static boolean isTrueName(String str) {
        return basicRegex("^[A-Za-z\\u4e00-\\u9fa5]+$", str);
    }

    public static boolean isPwd(String str) {
        return basicRegex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,18}$", str);
    }

    public static boolean isMobile(String str) {
        return basicRegex("^(1)(\\d{10})$", str);
    }

    public static boolean isTelephone(String str) {
        return basicRegex("^(\\d{3,4})?-?(\\d{7,8})$", str);
    }

    public static boolean isEmail(String str) {
        return basicRegex(RegexConstants.REGEX_EMAIL, str);
    }

    public static boolean isIdentityCard(String str) {
        if (str == null) {
            return false;
        }
        return Pattern.compile(RegexConstants.REGEX_ID_CARD15).matcher(str).matches() || Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$").matcher(str).matches();
    }

    public static boolean isPassWord(String str, int i, int i2) {
        if (str == null) {
            return false;
        }
        return Pattern.compile("^[^\\u4e00-\\u9fa5]{" + i + PunctuationConst.COMMA + i2 + "}$").matcher(str).matches();
    }

    public static boolean isLetter(String str) {
        return basicRegex("^[A-Za-z]+$", str);
    }

    public static boolean isChinese(String str) {
        return basicRegex(RegexConstants.REGEX_ZH, str);
    }

    public static boolean isNumber(String str) {
        return basicRegex("^[0-9]+$", str);
    }
}
