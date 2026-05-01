package com.tencent.cloud.stream.tts.core.utils;

import android.content.Context;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes4.dex */
public class AAILogger {
    private static File logFile;
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss.SSS");
    private static final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
    private static boolean enableDebug = true;
    private static boolean enableInfo = true;
    private static boolean enableWarn = true;
    private static boolean enableError = true;
    private static LoggerListener listener = null;
    private static boolean needLogFile = false;

    private AAILogger() {
    }

    public static void setNeedLogFile(boolean needLogFile2, Context applicationContext) {
        needLogFile = needLogFile2;
        if (needLogFile2) {
            logFile = getLogFile(applicationContext.getExternalFilesDir(null).getAbsolutePath() + File.separator + "TencentVoiceLog");
        }
    }

    private static File getLogFile(String localLogPath) {
        File file = new File(localLogPath);
        if (!file.exists() && !file.mkdirs()) {
            return null;
        }
        File file2 = new File(file.getPath() + File.separator + (dateFormat.format(new Date()) + ".log"));
        if (!file2.exists()) {
            try {
                if (!file2.createNewFile()) {
                    m1854e("AAILogger", "create log file failed");
                }
            } catch (Exception unused) {
                m1854e("AAILogger", "create log file failed");
            }
        }
        return file2;
    }

    public static void disableDebug() {
        enableDebug = false;
    }

    public static void enableDebug() {
        enableDebug = true;
        enableInfo = true;
        enableWarn = true;
        enableError = true;
    }

    public static void disableInfo() {
        enableInfo = false;
    }

    public static void enableInfo() {
        enableInfo = true;
        enableWarn = true;
        enableError = true;
    }

    public static void disableWarn() {
        enableWarn = false;
    }

    public static void enableWarn() {
        enableWarn = true;
        enableError = true;
    }

    public static void disableError() {
        enableError = false;
    }

    public static void enableError() {
        enableError = true;
    }

    public static void setLoggerListener(LoggerListener l) {
        listener = l;
    }

    private static void logOutput(String TAG, String message) {
        LoggerListener loggerListener = listener;
        if (loggerListener != null) {
            loggerListener.onLogInfo(TAG + message);
        }
    }

    /* JADX INFO: renamed from: d */
    public static void m1853d(String TAG, String message) {
        save2File(TAG, message, "[DEBUG]");
        if (enableDebug && enableInfo && enableWarn && enableError) {
            Log.d(TAG, message);
            logOutput(TAG, message);
        }
    }

    /* JADX INFO: renamed from: i */
    public static void m1855i(String TAG, String message) {
        save2File(TAG, message, "[INFO]");
        if (enableInfo && enableWarn && enableError) {
            Log.i(TAG, message);
            logOutput(TAG, message);
        }
    }

    /* JADX INFO: renamed from: w */
    public static void m1856w(String TAG, String message) {
        save2File(TAG, message, "[WARN]");
        if (enableWarn && enableError) {
            Log.w(TAG, message);
            logOutput(TAG, message);
        }
    }

    /* JADX INFO: renamed from: e */
    public static void m1854e(String TAG, String message) {
        save2File(TAG, message, "[ERROR]");
        if (enableError) {
            Log.e(TAG, message);
            logOutput(TAG, message);
        }
    }

    private static void save2File(String TAG, String message, String level) {
        if (logFile == null || !needLogFile) {
            return;
        }
        writeFile(logFile, dateFormat.format(new Date()) + level + "        " + TAG + "    " + message);
    }

    private static void writeFile(final File file, final String content) {
        mExecutorService.submit(new Runnable() { // from class: com.tencent.cloud.stream.tts.core.utils.AAILogger.1
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                PrintWriter printWriter;
                Throwable th;
                PrintWriter printWriter2 = null;
                PrintWriter printWriter3 = null;
                try {
                    try {
                        printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
                        try {
                            String str = content;
                            printWriter.println(str);
                            printWriter.flush();
                            printWriter.close();
                            printWriter2 = str;
                        } catch (Exception unused) {
                            printWriter3 = printWriter;
                            AAILogger.m1854e("AAILogger", "write log file failed");
                            printWriter2 = printWriter3;
                            if (printWriter3 != null) {
                                printWriter3.close();
                                printWriter2 = printWriter3;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (printWriter != null) {
                                printWriter.close();
                            }
                            throw th;
                        }
                    } catch (Exception unused2) {
                    }
                } catch (Throwable th3) {
                    printWriter = printWriter2;
                    th = th3;
                }
            }
        });
    }
}
