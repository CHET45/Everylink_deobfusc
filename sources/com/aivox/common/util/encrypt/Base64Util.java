package com.aivox.common.util.encrypt;

import android.util.Base64;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class Base64Util {
    public static String encodeWord(String str) throws UnsupportedEncodingException {
        return Base64.encodeToString(str.getBytes("utf-8"), 2);
    }

    public static String decodeWord(String str) throws UnsupportedEncodingException {
        return new String(Base64.decode(str, 2), "utf-8");
    }
}
