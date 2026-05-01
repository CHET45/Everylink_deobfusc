package com.aivox.besota.sdk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import com.aivox.besota.bessdk.service.BesOTAConstants;
import com.aivox.besota.bessdk.utils.SPHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class FileUtils {
    private Context mContext;
    private static String mPath = Environment.getExternalStorageDirectory() + "/";
    public static String OTA_FILE_NAME = "ota.txt";
    public static String OTA_STATIC = "ota_static";

    public FileUtils(Context context) {
        this.mContext = context;
    }

    public String getFilesDir(String str, String str2) {
        String str3 = this.mContext.getExternalFilesDir("").getAbsolutePath() + "/" + str2 + "/";
        createFolder(str3);
        return str3 + str;
    }

    public void initBinFile() {
        File file = new File(this.mContext.getExternalFilesDir("").getAbsolutePath() + "/bin");
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    public String getOtaFilePath() {
        return this.mContext.getExternalFilesDir("").getAbsolutePath() + "/bin";
    }

    public String[] getOtaBinFileNames() {
        File[] fileArrListFiles = new File(this.mContext.getExternalFilesDir("").getAbsolutePath() + "/bin").listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length > 0) {
            String[] strArr = new String[fileArrListFiles.length];
            for (int i = 0; i < fileArrListFiles.length; i++) {
                strArr[i] = fileArrListFiles[i].getName();
            }
            return strArr;
        }
        return new String[0];
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0047 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x0040 -> B:35:0x0043). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void saveBytesToFile(java.lang.String r4, java.lang.String r5, byte[] r6) throws java.lang.Throwable {
        /*
            r3 = this;
            java.lang.String r4 = r3.getFilesDir(r4, r5)
            java.io.File r5 = new java.io.File
            r5.<init>(r4)
            r4 = 0
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L27 java.io.FileNotFoundException -> L33
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L27 java.io.FileNotFoundException -> L33
            r2 = 1
            r1.<init>(r5, r2)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L27 java.io.FileNotFoundException -> L33
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L23 java.io.IOException -> L27 java.io.FileNotFoundException -> L33
            r0.write(r6)     // Catch: java.io.IOException -> L1f java.io.FileNotFoundException -> L21 java.lang.Throwable -> L44
            r0.flush()     // Catch: java.io.IOException -> L1f java.io.FileNotFoundException -> L21 java.lang.Throwable -> L44
            r0.close()     // Catch: java.io.IOException -> L3f
            goto L43
        L1f:
            r4 = move-exception
            goto L2a
        L21:
            r4 = move-exception
            goto L36
        L23:
            r5 = move-exception
            r0 = r4
            r4 = r5
            goto L45
        L27:
            r5 = move-exception
            r0 = r4
            r4 = r5
        L2a:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L44
            if (r0 == 0) goto L43
            r0.close()     // Catch: java.io.IOException -> L3f
            goto L43
        L33:
            r5 = move-exception
            r0 = r4
            r4 = r5
        L36:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L44
            if (r0 == 0) goto L43
            r0.close()     // Catch: java.io.IOException -> L3f
            goto L43
        L3f:
            r4 = move-exception
            r4.printStackTrace()
        L43:
            return
        L44:
            r4 = move-exception
        L45:
            if (r0 == 0) goto L4f
            r0.close()     // Catch: java.io.IOException -> L4b
            goto L4f
        L4b:
            r5 = move-exception
            r5.printStackTrace()
        L4f:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.besota.sdk.utils.FileUtils.saveBytesToFile(java.lang.String, java.lang.String, byte[]):void");
    }

    public void createFolder(String str) {
        File file = new File(str);
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0043 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x003c -> B:35:0x003f). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void writebytetofile(java.lang.String r4, byte[] r5) throws java.lang.Throwable {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r4)
            r4 = 0
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L23 java.io.FileNotFoundException -> L2f
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L23 java.io.FileNotFoundException -> L2f
            r3 = 1
            r2.<init>(r0, r3)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L23 java.io.FileNotFoundException -> L2f
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L23 java.io.FileNotFoundException -> L2f
            r1.write(r5)     // Catch: java.io.IOException -> L1b java.io.FileNotFoundException -> L1d java.lang.Throwable -> L40
            r1.flush()     // Catch: java.io.IOException -> L1b java.io.FileNotFoundException -> L1d java.lang.Throwable -> L40
            r1.close()     // Catch: java.io.IOException -> L3b
            goto L3f
        L1b:
            r4 = move-exception
            goto L26
        L1d:
            r4 = move-exception
            goto L32
        L1f:
            r5 = move-exception
            r1 = r4
            r4 = r5
            goto L41
        L23:
            r5 = move-exception
            r1 = r4
            r4 = r5
        L26:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L40
            if (r1 == 0) goto L3f
            r1.close()     // Catch: java.io.IOException -> L3b
            goto L3f
        L2f:
            r5 = move-exception
            r1 = r4
            r4 = r5
        L32:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L40
            if (r1 == 0) goto L3f
            r1.close()     // Catch: java.io.IOException -> L3b
            goto L3f
        L3b:
            r4 = move-exception
            r4.printStackTrace()
        L3f:
            return
        L40:
            r4 = move-exception
        L41:
            if (r1 == 0) goto L4b
            r1.close()     // Catch: java.io.IOException -> L47
            goto L4b
        L47:
            r5 = move-exception
            r5.printStackTrace()
        L4b:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.besota.sdk.utils.FileUtils.writebytetofile(java.lang.String, byte[]):void");
    }

    public static void writeTOfileAndActiveClear(String str, String str2) {
        Log.i("TAG", "writeTOfileAndActiveClear: ------" + str2);
        String str3 = getFolderPath() + "Android/data/ActivityUtils.getPackageName()/files/";
        isExist(str3);
        String str4 = str3 + "OTA/";
        isExist(str4);
        File file = new File(str4 + str + ".txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            long jAvailable = fileInputStream.available();
            fileInputStream.close();
            if (jAvailable >= 80000000) {
                file.delete();
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write((str2 + "\n").getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTOFile(Object obj, String str, String str2, String str3) {
        Log.i("TAG", "writeTOFile: " + obj);
        String str4 = getFolderPath() + "Android/data/ActivityUtils.getPackageName()/files/";
        isExist(str4);
        String str5 = str4 + str + "/";
        isExist(str5);
        String str6 = str5 + str2 + "." + str3;
        File file = new File(str6);
        Log.i("TAG", "writeTOFile: path" + str6);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            long jAvailable = fileInputStream.available();
            fileInputStream.close();
            if (jAvailable >= 80000000) {
                file.delete();
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            if (obj.getClass() == String.class) {
                fileOutputStream.write((obj + "\n").getBytes());
                fileOutputStream.close();
            } else if (obj.getClass() == byte[].class) {
                fileOutputStream.write((byte[]) obj);
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFolderPath() {
        String str = mPath;
        isExist(str);
        return str + "/";
    }

    public static void isExist(String str) {
        File file = new File(str);
        if (file.exists()) {
            return;
        }
        synchronized (FileUtils.class) {
            file.mkdirs();
        }
    }

    public void HandleAnalyseFile(String str, String str2, String str3, String str4) {
        String filesDir = getFilesDir(str4, str2);
        File file = new File(filesDir);
        if (!file.exists()) {
            try {
                file.createNewFile();
                new FileOutputStream(filesDir, true).write("TIME,LEFT_PHONE_AGC, LEFT_PHONE_RSSI,LEFT_PHONE_MAX_RSSI,LEFT_PHONE_MIN_RSSI,LEFT_TWS_ARC,LEFT_TWS_RSSI,LEFT_TWS_MAX_RSSI,LEFT_TWS_MIN_RSSI,raw_rssi.ser,fa_idx_left,MIRROR_LEFT_PHONE_AGC,MIRROR_LEFT_PHONE_RSSI,MIRROR_LEFT_PHONE_MAX_RSSI,MIRROR_LEFT_PHONE_MIN_RSSI,RIGHT_PHONE_AGC,RIGHT_PHONE_RSSI,RIGHT_PHONE_MAX_RSSI,RIGHT_PHONE_MIN_RSSI,RIGHT_TWS_AGC,RIGHT_TWS_RSSI,RIGHT_TWS_MAX_RSSI,RIGHT_TWS_MIN_RSSI,peer_raw_rssi.ser,fa_idx_right,MIRROR_RIGHT_PHONE_AGC,MIRROR_RIGHT_PHONE_RSSI,MIRROR_RIGHT_PHONE_MAX_RSSI,MIRROR_RIGHT_PHONE_MIN_RSSI,CURRENT_ROLE\n".getBytes("gbk"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filesDir, true);
            fileOutputStream.write(str.getBytes("gbk"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void RssiextendAnalyseFile(String str, String str2, String str3, String str4) {
        String filesDir = getFilesDir(str4, str2);
        File file = new File(filesDir);
        if (!file.exists()) {
            try {
                file.createNewFile();
                new FileOutputStream(filesDir, true).write("TIME,LEFT_PHONE_AGC, LEFT_PHONE_RSSI,LEFT_PHONE_MAX_RSSI,LEFT_PHONE_MIN_RSSI,LEFT_TWS_ARC,LEFT_TWS_RSSI,LEFT_TWS_MAX_RSSI,LEFT_TWS_MIN_RSSI,raw_rssi.ser,RIGHT_PHONE_AGC,RIGHT_PHONE_RSSI,RIGHT_PHONE_MAX_RSSI,RIGHT_PHONE_MIN_RSSI,RIGHT_TWS_AGC,RIGHT_TWS_RSSI,RIGHT_TWS_MAX_RSSI,RIGHT_TWS_MIN_RSSI,peer_raw_rssi.ser,CRRENT_ROLE,extra_data \n".getBytes("gbk"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filesDir, true);
            fileOutputStream.write(str.getBytes("gbk"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public void HandleFileReport(String str, String str2, String str3, String str4) {
        String filesDir = getFilesDir(str4, str2);
        File file = new File(filesDir);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filesDir, true);
            Log.e("info = ", str);
            fileOutputStream.write(str.getBytes("gbk"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static void writeArrayListTOFile(ArrayList arrayList, String str, String str2, String str3) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        String str4 = getFolderPath() + "Android/data/ ActivityUtils.getPackageName()/files/";
        isExist(str4);
        String str5 = str4 + str + "/";
        isExist(str5);
        File file = new File(str5 + str2);
        ObjectOutputStream objectOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                try {
                    objectOutputStream.writeObject(arrayList);
                } catch (Exception e) {
                    e = e;
                    objectOutputStream2 = objectOutputStream;
                    e.printStackTrace();
                    objectOutputStream = objectOutputStream2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream = null;
        }
        if (objectOutputStream != null) {
            try {
                objectOutputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e5) {
                e5.printStackTrace();
            }
        }
    }

    public void writeFlashContentfiles(String str, String str2, String str3) {
        File file = new File(getFilesDir(str, str2));
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.available();
            fileInputStream.close();
        } catch (IOException e) {
            Log.e("ex", e.getMessage().toString());
        }
    }

    public static String cutLastSegmentOfPath(String str) {
        return str.substring(0, str.lastIndexOf("/"));
    }

    public static void fileToHex(Activity activity, int i) {
        if (((Boolean) SPHelper.getPreference(activity, BesOTAConstants.BES_KEY_USE_INTERNAL_FILE_ACCESS, false)).booleanValue()) {
            new Intent();
        }
    }
}
