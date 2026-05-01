package com.github.houbb.opencc4j.util;

import com.github.houbb.heaven.util.lang.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class InnerCharUtils {
    public static List<String> toCharList(String str) {
        int i;
        if (StringUtil.isEmpty(str)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (Character.isHighSurrogate(cCharAt) && (i = i2 + 1) < length) {
                char cCharAt2 = str.charAt(i);
                if (Character.isLowSurrogate(cCharAt2)) {
                    arrayList.add(new String(new char[]{cCharAt, cCharAt2}));
                    i2 += 2;
                }
            }
            arrayList.add(Character.toString(cCharAt));
            i2++;
        }
        return arrayList;
    }

    public static boolean isChineseForSingle(String str) {
        if (str == null || str.isEmpty() || str.length() > 2) {
            return false;
        }
        int iCodePointAt = str.codePointAt(0);
        if (str.length() != Character.charCount(iCodePointAt)) {
            return false;
        }
        return (iCodePointAt >= 19968 && iCodePointAt <= 40959) || (iCodePointAt >= 13312 && iCodePointAt <= 19903) || (iCodePointAt >= 131072 && iCodePointAt <= 173791);
    }
}
