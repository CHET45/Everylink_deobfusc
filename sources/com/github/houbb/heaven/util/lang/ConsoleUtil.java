package com.github.houbb.heaven.util.lang;

import com.azure.core.implementation.logging.DefaultLogger;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.DateUtil;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class ConsoleUtil {
    public static final String LINE = "--------------------------------------------------------";

    private ConsoleUtil() {
    }

    public static void info(String str, String str2, String str3, Object... objArr) {
        log(DefaultLogger.INFO, str, str2, buildString(str3, objArr), null);
    }

    public static void info(String str, Object... objArr) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        info(stackTraceElement.getClassName(), stackTraceElement.getMethodName(), str, objArr);
    }

    private static String buildString(String str, Object[] objArr) {
        for (int i = 0; i < objArr.length; i++) {
            str = str.replaceFirst("\\{}", "%s");
        }
        return String.format(str, objArr);
    }

    private static void log(String str, String str2, String str3, String str4, Throwable th) {
        String str5 = String.format("[%s] [%s] [%s] - %s", str, DateUtil.getCurrentDateTimeStr(), buildPrettyMethodName(str2, str3), str4);
        if (DefaultLogger.ERROR.equalsIgnoreCase(str)) {
            System.err.println(str5);
        } else {
            System.out.println(str5);
        }
        if (th != null) {
            th.printStackTrace(System.err);
        }
    }

    private static String buildPrettyMethodName(String str, String str2) {
        String[] strArrSplit = str.split("\\.");
        if (ArrayUtil.isEmpty(strArrSplit)) {
            return str2;
        }
        int length = strArrSplit.length;
        if (length == 1) {
            return str + "." + str2;
        }
        List listNewArrayList = Guavas.newArrayList(length);
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (i < i2) {
                listNewArrayList.add(String.valueOf(strArrSplit[i].charAt(0)));
                i++;
            } else {
                listNewArrayList.add(strArrSplit[i2]);
                return CollectionUtil.join(listNewArrayList, ".") + "." + str2;
            }
        }
    }
}
