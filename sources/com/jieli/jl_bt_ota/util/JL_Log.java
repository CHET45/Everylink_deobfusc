package com.jieli.jl_bt_ota.util;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: classes3.dex */
public class JL_Log {
    public static long FILE_SIZE_LIMIT = 314572800;
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_ERROR = 5;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_VERBOSE = 1;
    public static final int LEVEL_WARN = 4;
    public static int SAVE_LOG_LEVEL = 1;

    /* JADX INFO: renamed from: a */
    private static final String f785a = "ota:";

    /* JADX INFO: renamed from: b */
    private static final String f786b = "logcat";

    /* JADX INFO: renamed from: c */
    private static boolean f787c = true;

    /* JADX INFO: renamed from: d */
    private static boolean f788d = false;

    /* JADX INFO: renamed from: e */
    private static String f789e = null;

    /* JADX INFO: renamed from: f */
    private static final SimpleDateFormat f790f = new SimpleDateFormat("yyyyMMddHHmmss.SSS", Locale.getDefault());

    /* JADX INFO: renamed from: g */
    private static SaveLogFileThread f791g = null;

    /* JADX INFO: renamed from: h */
    private static Context f792h = null;

    /* JADX INFO: renamed from: i */
    private static ILogOutput f793i = null;
    public static boolean isTest = false;

    public interface ILogOutput {
        void output(String str);
    }

    private static class SaveLogFileThread extends Thread {

        /* JADX INFO: renamed from: a */
        private final LinkedBlockingQueue<byte[]> f794a;

        /* JADX INFO: renamed from: b */
        private final Context f795b;

        /* JADX INFO: renamed from: c */
        private volatile boolean f796c;

        /* JADX INFO: renamed from: d */
        private volatile boolean f797d;

        /* JADX INFO: renamed from: e */
        private long f798e;

        /* JADX INFO: renamed from: f */
        private FileOutputStream f799f;

        public SaveLogFileThread(Context context) {
            super("SaveLogFileThread");
            this.f794a = new LinkedBlockingQueue<>();
            this.f795b = context;
        }

        public void addLog(byte[] bArr) {
            if (bArr != null) {
                try {
                    this.f794a.put(bArr);
                    m854a();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public synchronized void closeSaveFile() {
            this.f797d = false;
            m854a();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            FileOutputStream fileOutputStream;
            this.f797d = m855a(this.f795b);
            synchronized (this.f794a) {
                while (this.f797d) {
                    if (this.f794a.isEmpty()) {
                        this.f796c = true;
                        try {
                            this.f794a.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        this.f796c = false;
                        byte[] bArrPoll = this.f794a.poll();
                        if (bArrPoll != null && (fileOutputStream = this.f799f) != null) {
                            try {
                                fileOutputStream.write(bArrPoll);
                                this.f798e += (long) bArrPoll.length;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                            if (this.f798e >= JL_Log.FILE_SIZE_LIMIT) {
                                try {
                                    this.f799f.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                                this.f797d = m855a(this.f795b);
                            }
                        }
                    }
                }
            }
            this.f797d = false;
            this.f796c = false;
            this.f794a.clear();
            FileOutputStream fileOutputStream2 = this.f799f;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            SaveLogFileThread unused = JL_Log.f791g = null;
        }

        @Override // java.lang.Thread
        public synchronized void start() {
            this.f798e = 0L;
            this.f797d = this.f795b != null;
            super.start();
        }

        /* JADX INFO: renamed from: a */
        private void m854a() {
            if (this.f796c) {
                synchronized (this.f794a) {
                    this.f794a.notify();
                }
            }
        }

        /* JADX INFO: renamed from: a */
        private boolean m855a(Context context) {
            if (context == null) {
                return false;
            }
            if (TextUtils.isEmpty(JL_Log.f789e)) {
                String unused = JL_Log.f789e = JL_Log.m838b(context, "logcat");
            }
            try {
                this.f799f = new FileOutputStream(JL_Log.f789e + "/ota_log_app_" + JL_Log.m843d() + ".txt", true);
                this.f798e = 0L;
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static void addLogOutput(String str) {
        if (f788d) {
            if (f791g == null) {
                m836a(f792h);
                SystemClock.sleep(20L);
            }
            SaveLogFileThread saveLogFileThread = f791g;
            if (saveLogFileThread != null) {
                saveLogFileThread.addLog(str.getBytes());
            }
        }
    }

    /* JADX INFO: renamed from: c */
    private static void m842c(int i, String str, String str2) {
        String strM834a = m834a(m830a(i), str, str2);
        ILogOutput iLogOutput = f793i;
        if (iLogOutput != null) {
            iLogOutput.output(strM834a);
        } else {
            if (i < SAVE_LOG_LEVEL) {
                return;
            }
            addLogOutput(strM834a);
        }
    }

    /* JADX INFO: renamed from: d */
    public static void m844d(String str, String str2) {
        m835a(2, str, str2);
    }

    /* JADX INFO: renamed from: e */
    public static void m846e(String str, String str2) {
        m835a(5, str, str2);
    }

    public static boolean getSaveLogFile() {
        return f788d;
    }

    public static String getSaveLogPath(Context context) {
        return m838b(context, "logcat");
    }

    /* JADX INFO: renamed from: i */
    public static void m848i(String str, String str2) {
        m835a(3, str, str2);
    }

    public static boolean isIsLog() {
        return f787c;
    }

    public static void setIsSaveLogFile(Context context, boolean z) {
        f788d = z;
        if (z) {
            m836a(context);
        } else {
            m841c();
        }
    }

    public static void setIsTest(boolean z) {
        isTest = z;
    }

    public static void setLog(boolean z) {
        f787c = z;
    }

    public static void setLogOutput(ILogOutput iLogOutput) {
        f793i = iLogOutput;
    }

    /* JADX INFO: renamed from: v */
    public static void m850v(String str, String str2) {
        m835a(1, str, str2);
    }

    /* JADX INFO: renamed from: w */
    public static void m852w(String str, String str2) {
        m835a(4, str, str2);
    }

    /* JADX INFO: renamed from: b */
    private static String m839b(String str) {
        return f785a + str;
    }

    /* JADX INFO: renamed from: d */
    public static void m845d(String str, String str2, String str3) {
        m844d(str, m833a(str2, str3));
    }

    /* JADX INFO: renamed from: e */
    public static void m847e(String str, String str2, String str3) {
        m846e(str, m833a(str2, str3));
    }

    /* JADX INFO: renamed from: i */
    public static void m849i(String str, String str2, String str3) {
        m848i(str, m833a(str2, str3));
    }

    /* JADX INFO: renamed from: v */
    public static void m851v(String str, String str2, String str3) {
        m850v(str, m833a(str2, str3));
    }

    /* JADX INFO: renamed from: w */
    public static void m853w(String str, String str2, String str3) {
        m852w(str, m833a(str2, str3));
    }

    /* JADX INFO: renamed from: b */
    private static void m840b(int i, String str, String str2) {
        System.out.printf(Locale.ENGLISH, "%s\t%s\t%s%n", m830a(i), str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: d */
    public static String m843d() {
        return f790f.format(Calendar.getInstance().getTime());
    }

    /* JADX INFO: renamed from: a */
    private static String m834a(String str, String str2, String str3) {
        StringBuilder sbAppend = new StringBuilder().append(m843d()).append("   ").append(str).append("   ");
        if (str2 == null) {
            str2 = "null";
        }
        StringBuilder sbAppend2 = sbAppend.append(str2).append(" :  ");
        if (str3 == null) {
            str3 = "null";
        }
        return sbAppend2.append(str3).append("\n").toString();
    }

    /* JADX INFO: renamed from: a */
    private static String m833a(String str, String str2) {
        return String.format(Locale.ENGLISH, "[%s] >>> %s", str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static String m838b(Context context, String... strArr) {
        File externalFilesDir;
        if (context == null || strArr == null || strArr.length == 0 || (externalFilesDir = context.getExternalFilesDir(null)) == null || !externalFilesDir.exists()) {
            return null;
        }
        StringBuilder sb = new StringBuilder(externalFilesDir.getPath());
        int i = 0;
        if (sb.toString().endsWith("/")) {
            sb = new StringBuilder(sb.substring(0, sb.lastIndexOf("/")));
        }
        int length = strArr.length;
        while (true) {
            if (i >= length) {
                break;
            }
            sb.append("/").append(strArr[i]);
            File file = new File(sb.toString());
            if ((!file.exists() || file.isFile()) && !file.mkdir()) {
                m852w("jieli", "create dir failed. filePath = " + ((Object) sb));
                break;
            }
            i++;
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: a */
    private static String m830a(int i) {
        if (i == 1) {
            return "v";
        }
        if (i == 2) {
            return "d";
        }
        if (i == 3) {
            return "i";
        }
        if (i == 4) {
            return "w";
        }
        if (i != 5) {
            return "";
        }
        return "e";
    }

    /* JADX INFO: renamed from: c */
    private static void m841c() {
        SaveLogFileThread saveLogFileThread = f791g;
        if (saveLogFileThread != null) {
            saveLogFileThread.closeSaveFile();
            f791g = null;
        }
        f792h = null;
    }

    /* JADX INFO: renamed from: a */
    private static void m835a(int i, String str, String str2) {
        String strM839b = m839b(str);
        if (f787c) {
            if (isTest) {
                m840b(i, strM839b, str2);
            } else if (i == 1) {
                Log.v(strM839b, str2);
            } else if (i == 2) {
                Log.d(strM839b, str2);
            } else if (i == 3) {
                Log.i(strM839b, str2);
            } else if (i == 4) {
                Log.w(strM839b, str2);
            } else if (i != 5) {
                return;
            } else {
                Log.e(strM839b, str2);
            }
        }
        m842c(i, strM839b, str2);
    }

    /* JADX INFO: renamed from: a */
    private static void m836a(Context context) {
        SaveLogFileThread saveLogFileThread = f791g;
        if (saveLogFileThread == null || !saveLogFileThread.f797d) {
            if (f792h == null) {
                if (context == null) {
                    context = CommonUtil.getMainContext();
                }
                f792h = context;
            }
            SaveLogFileThread saveLogFileThread2 = new SaveLogFileThread(f792h);
            f791g = saveLogFileThread2;
            saveLogFileThread2.start();
        }
    }
}
