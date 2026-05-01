package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.util.lang.StringUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class EmojiUtil {
    private EmojiUtil() {
    }

    public static String replaceEmoji(String str, String str2) {
        return StringUtil.isEmpty(str) ? str : str.replaceAll("[𐀀-\u10ffff\ud800-\udfff]", str2);
    }

    public static String replaceEmoji(String str) {
        return replaceEmoji(str, "");
    }
}
