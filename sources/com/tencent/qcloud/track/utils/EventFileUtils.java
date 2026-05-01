package com.tencent.qcloud.track.utils;

import android.content.Context;
import android.util.Log;
import com.tencent.qcloud.track.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class EventFileUtils {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.io.OutputStreamWriter] */
    /* JADX WARN: Type inference failed for: r2v2 */
    public static void appendEventToFile(Context context, String str, Map<String, String> map) throws Throwable {
        OutputStreamWriter outputStreamWriter;
        IOException e;
        if (context == null) {
            return;
        }
        ?? file = new File(context.getExternalCacheDir() + File.separator + Constants.BEACON_EVENT_CACHE_FILE_NAME);
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
            return;
        }
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    return;
                }
            } catch (IOException e2) {
                Log.e("FileUtils", "Error creating new file", e2);
                return;
            }
        }
        if (file.length() / 1048576 > 10) {
            Log.e("FileUtils", "File size is larger than 10MB");
            return;
        }
        try {
            try {
                try {
                    outputStreamWriter = new OutputStreamWriter(new FileOutputStream((File) file, true));
                } catch (IOException e3) {
                    outputStreamWriter = null;
                    e = e3;
                } catch (Throwable th) {
                    file = 0;
                    th = th;
                    if (file != 0) {
                        try {
                            file.close();
                        } catch (IOException e4) {
                            Log.e("FileUtils", "Error closing OutputStreamWriter", e4);
                        }
                    }
                    throw th;
                }
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Event code: ").append(str).append("\n");
                    sb.append("Params: ").append("\n");
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                    }
                    sb.append("\n");
                    outputStreamWriter.write(sb.toString());
                    outputStreamWriter.flush();
                    outputStreamWriter.close();
                } catch (IOException e5) {
                    e = e5;
                    Log.e("FileUtils", "Error appending event to file", e);
                    if (outputStreamWriter != null) {
                        outputStreamWriter.close();
                    }
                }
            } catch (IOException e6) {
                Log.e("FileUtils", "Error closing OutputStreamWriter", e6);
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:5|70|6|67|7|(4:8|(3:10|(5:75|12|(1:14)|15|79)(3:73|16|(3:74|18|78)(1:77))|76)(1:72)|47|48)|19|(1:21)|65|22|26|(1:81)|47|48) */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0091, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0092, code lost:
    
        android.util.Log.e("FileUtils", "Error closing BufferedReader", r11);
     */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00cd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void readEventFromFile(android.content.Context r11, java.util.List<java.lang.String> r12, java.util.List<java.util.Map<java.lang.String, java.lang.String>> r13) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.track.utils.EventFileUtils.readEventFromFile(android.content.Context, java.util.List, java.util.List):void");
    }

    public static boolean eventFileExists(Context context) {
        if (context == null) {
            return false;
        }
        return new File(context.getExternalCacheDir() + File.separator + Constants.BEACON_EVENT_CACHE_FILE_NAME).exists();
    }
}
