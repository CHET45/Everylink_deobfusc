package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.support.tuple.impl.Pair;
import com.github.houbb.heaven.util.lang.StringUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class PropertyUtil {
    private PropertyUtil() {
    }

    public static Pair<String, String> getPropertyPair(String str) {
        if (StringUtil.isEmpty(str)) {
            return Pair.m528of((Object) null, (Object) null);
        }
        int iIndexOf = str.indexOf(PunctuationConst.EQUAL);
        if (iIndexOf < 0) {
            return Pair.m528of(str, (Object) null);
        }
        return Pair.m528of(str.substring(0, iIndexOf), str.substring(iIndexOf + 1));
    }

    public static void main(String[] strArr) {
        System.out.println(getPropertyPair(null));
        System.out.println(getPropertyPair("=123"));
        System.out.println(getPropertyPair("key=123"));
    }
}
