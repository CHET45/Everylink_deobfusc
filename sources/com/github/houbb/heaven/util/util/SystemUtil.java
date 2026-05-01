package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.constant.SystemConst;

/* JADX INFO: loaded from: classes3.dex */
public final class SystemUtil {
    private SystemUtil() {
    }

    public static String getLineSeparator() {
        return getProperty(SystemConst.LINE_SEPARATOR);
    }

    public static String getProperty(String str) {
        return System.getProperty(str);
    }

    public static boolean isWindowsOs() {
        return System.getProperty(SystemConst.OS_NAME).toLowerCase().startsWith("win");
    }

    public static String getBaseDir(String str) {
        if (isWindowsOs()) {
            return "D:\\file\\" + str + "\\";
        }
        return "/app/file/" + str + "/";
    }

    public static String getTempDir() {
        return System.getProperty(SystemConst.IO_TMPDIR);
    }

    public static String getBaseDir() {
        if (isWindowsOs()) {
            return "D:\\file\\";
        }
        return "/app/file/";
    }

    public static String getCurrentWorkingDirectory() {
        return System.getProperty(SystemConst.USER_DIR);
    }

    public static String getClassPath() {
        return System.getProperty(SystemConst.CLASS_PATH);
    }
}
