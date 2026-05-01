package com.tencent.aai.log;

/* JADX INFO: loaded from: classes4.dex */
public class AAILogger {
    private static boolean enableDebug = true;
    private static boolean enableError = true;
    private static boolean enableInfo = true;
    private static boolean enableWarn = true;
    private static LoggerListener listener;

    private AAILogger() {
    }

    public static void debug(String str, String str2) {
        if (enableDebug && enableInfo && enableWarn && enableError) {
            logOutput(str, str2);
        }
    }

    public static void disableDebug() {
        enableDebug = false;
    }

    public static void disableError() {
        enableError = false;
    }

    public static void disableInfo() {
        enableInfo = false;
    }

    public static void disableWarn() {
        enableWarn = false;
    }

    public static void enableDebug() {
        enableDebug = true;
        enableInfo = true;
        enableWarn = true;
        enableError = true;
    }

    public static void enableError() {
        enableError = true;
    }

    public static void enableInfo() {
        enableInfo = true;
        enableWarn = true;
        enableError = true;
    }

    public static void enableWarn() {
        enableWarn = true;
        enableError = true;
    }

    public static void error(String str, String str2) {
        if (enableError) {
            logOutput(str, str2);
        }
    }

    public static void info(String str, String str2) {
        if (enableInfo && enableWarn && enableError) {
            logOutput(str, str2);
        }
    }

    private static void logOutput(String str, String str2) {
        LoggerListener loggerListener = listener;
        if (loggerListener != null) {
            loggerListener.onLogInfo(str + str2);
        }
    }

    public static void setLoggerListener(LoggerListener loggerListener) {
        listener = loggerListener;
    }

    public static void warn(String str, String str2) {
        if (enableWarn && enableError) {
            logOutput(str, str2);
        }
    }
}
