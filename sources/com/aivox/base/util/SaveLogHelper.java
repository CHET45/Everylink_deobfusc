package com.aivox.base.util;

import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public class SaveLogHelper {
    public static final String LOG_TAG_BUTTON = "BUTTON CLICK: ";
    public static final String LOG_TAG_ERROR = "ERROR: ";
    public static final String LOG_TAG_LIFECYCLE = "LIFECYCLE: ";
    public static final String LOG_TAG_METHOD = "METHOD INVOKE: ";
    public static final String LOG_TAG_MSG = "MSG: ";
    private static final String TAG = "SaveLogHelper";
    private static volatile SaveLogHelper saveLogHelper;
    private final String fileName = "log.log";
    private ExecutorService mExecutor;
    private volatile FileOutputStream out;

    private SaveLogHelper() {
    }

    public static SaveLogHelper getIns() {
        if (saveLogHelper == null) {
            synchronized (SaveLogHelper.class) {
                if (saveLogHelper == null) {
                    saveLogHelper = new SaveLogHelper();
                }
            }
        }
        saveLogHelper.ensureExecutor();
        return saveLogHelper;
    }

    private void ensureExecutor() {
        ExecutorService executorService = this.mExecutor;
        if (executorService == null || executorService.isShutdown()) {
            this.mExecutor = Executors.newSingleThreadExecutor();
        }
    }

    public void init(final String str) {
        ensureExecutor();
        this.mExecutor.execute(new Runnable() { // from class: com.aivox.base.util.SaveLogHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2437lambda$init$0$comaivoxbaseutilSaveLogHelper(str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$init$0$com-aivox-base-util-SaveLogHelper, reason: not valid java name */
    /* synthetic */ void m2437lambda$init$0$comaivoxbaseutilSaveLogHelper(String str) {
        try {
            File file = new File(str, "log.log");
            if (!file.exists()) {
                file.createNewFile();
            }
            closeOutStream();
            this.out = new FileOutputStream(file, true);
        } catch (Exception e) {
            Log.e(TAG, "init", e);
        }
    }

    public void writeLog(final String str, final String str2, final boolean z) {
        ExecutorService executorService = this.mExecutor;
        if (executorService == null || executorService.isShutdown()) {
            return;
        }
        this.mExecutor.execute(new Runnable() { // from class: com.aivox.base.util.SaveLogHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2438lambda$writeLog$1$comaivoxbaseutilSaveLogHelper(str2, z, str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$writeLog$1$com-aivox-base-util-SaveLogHelper, reason: not valid java name */
    /* synthetic */ void m2438lambda$writeLog$1$comaivoxbaseutilSaveLogHelper(String str, boolean z, String str2) {
        String strSubstring;
        if (this.out == null) {
            return;
        }
        try {
            String curDate = DateUtil.getCurDate(DateUtil.YYYY_MM_DD_HH_MM_SS);
            String mD5String = MD5Utils.getMD5String(curDate + str);
            if (mD5String.length() >= 4) {
                strSubstring = mD5String.substring(mD5String.length() - 4);
            } else {
                strSubstring = "0000";
            }
            StringBuilder sb = new StringBuilder();
            if (z) {
                sb.append("\n");
            }
            sb.append("\n#Log#").append(curDate).append(" ").append(str2).append(str).append(" ").append(strSubstring);
            if (this.out == null || !this.out.getChannel().isOpen()) {
                return;
            }
            this.out.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            Log.e(TAG, "Write failed", e);
        } catch (Exception e2) {
            Log.e(TAG, "writeLog", e2);
        }
    }

    public void writeLog(String str, String str2) {
        writeLog(str, str2, false);
    }

    public void destroy() {
        ExecutorService executorService = this.mExecutor;
        if (executorService == null || executorService.isShutdown()) {
            return;
        }
        this.mExecutor.execute(new Runnable() { // from class: com.aivox.base.util.SaveLogHelper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.closeOutStream();
            }
        });
        this.mExecutor.shutdown();
        this.mExecutor = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeOutStream() {
        if (this.out != null) {
            try {
                try {
                    this.out.close();
                } catch (IOException e) {
                    Log.e(TAG, "closeOutStream", e);
                }
            } finally {
                this.out = null;
            }
        }
    }

    public String getFileName() {
        return "log.log";
    }
}
