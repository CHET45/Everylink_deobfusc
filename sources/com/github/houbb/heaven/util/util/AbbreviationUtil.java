package com.github.houbb.heaven.util.util;

import androidx.core.app.NotificationCompat;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.microsoft.azure.storage.core.C2391SR;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class AbbreviationUtil {
    private static final Map<String, String> MAP;

    private AbbreviationUtil() {
    }

    static {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        MAP = concurrentHashMap;
        concurrentHashMap.put("impl", "implements");
        concurrentHashMap.put(NotificationCompat.CATEGORY_MESSAGE, "message");
        concurrentHashMap.put(NotificationCompat.CATEGORY_ERROR, "error");
        concurrentHashMap.put("e", "exception");
        concurrentHashMap.put("ex", "exception");
        concurrentHashMap.put("doc", "document");
        concurrentHashMap.put("val", "value");
        concurrentHashMap.put("num", "number");
        concurrentHashMap.put("vo", "value object");
        concurrentHashMap.put("dto", "data transfer object");
        concurrentHashMap.put("gen", "generate");
        concurrentHashMap.put("dir", C2391SR.DIRECTORY);
        concurrentHashMap.put("init", "initialize");
        concurrentHashMap.put("cfg", "config");
        concurrentHashMap.put("arg", "argument");
        concurrentHashMap.put("args", "arguments");
    }

    public static void set(String str, String str2) {
        MAP.put(str, str2);
    }

    public static String get(String str) {
        return MAP.get(str);
    }

    public static String getOrDefault(String str, String str2) {
        String str3 = MAP.get(str);
        return StringUtil.isEmpty(str3) ? str2 : str3;
    }
}
