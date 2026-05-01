package com.tencent.aai.audio.utils;

import android.os.Environment;
import com.aivox.common.socket.DeviceProtocol;
import com.luck.picture.lib.config.PictureMimeType;
import com.tencent.aai.log.AAILogger;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class WavCache {
    public static String TAG = "WavCache";
    public static String parentDirName = "/";
    public static String wavName = "record.wav";

    /* JADX INFO: renamed from: com.tencent.aai.audio.utils.WavCache$a */
    public static class RunnableC2595a implements Runnable {

        /* JADX INFO: renamed from: a */
        public final /* synthetic */ short[] f928a;

        /* JADX INFO: renamed from: b */
        public final /* synthetic */ int f929b;

        /* JADX INFO: renamed from: c */
        public final /* synthetic */ int f930c;

        public RunnableC2595a(short[] sArr, int i, int i2) {
            this.f928a = sArr;
            this.f929b = i;
            this.f930c = i2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:53:0x00ae A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r0v10, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r0v13 */
        /* JADX WARN: Type inference failed for: r0v7 */
        /* JADX WARN: Type inference failed for: r2v11 */
        /* JADX WARN: Type inference failed for: r2v14 */
        /* JADX WARN: Type inference failed for: r2v17, types: [java.io.FileOutputStream] */
        /* JADX WARN: Type inference failed for: r2v22 */
        /* JADX WARN: Type inference failed for: r2v23 */
        /* JADX WARN: Type inference failed for: r2v24 */
        /* JADX WARN: Type inference failed for: r2v25 */
        /* JADX WARN: Type inference failed for: r2v26 */
        /* JADX WARN: Type inference failed for: r2v27 */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() throws java.lang.Throwable {
            /*
                r5 = this;
                java.io.File r0 = new java.io.File
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.io.File r2 = android.os.Environment.getExternalStorageDirectory()
                java.lang.String r2 = r2.toString()
                java.lang.StringBuilder r1 = r1.append(r2)
                java.lang.String r2 = com.tencent.aai.audio.utils.WavCache.parentDirName
                java.lang.StringBuilder r1 = r1.append(r2)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                boolean r1 = r0.exists()
                if (r1 != 0) goto L38
                boolean r1 = r0.mkdirs()
                if (r1 == 0) goto L31
                java.lang.String r1 = com.tencent.aai.audio.utils.WavCache.TAG
                java.lang.String r2 = "创建文件夹成功"
                goto L35
            L31:
                java.lang.String r1 = com.tencent.aai.audio.utils.WavCache.TAG
                java.lang.String r2 = "创建文件夹失败"
            L35:
                com.tencent.aai.log.AAILogger.info(r1, r2)
            L38:
                java.io.File r1 = new java.io.File
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                long r3 = java.lang.System.currentTimeMillis()
                java.lang.StringBuilder r2 = r2.append(r3)
                java.lang.String r3 = ".pcm"
                java.lang.StringBuilder r2 = r2.append(r3)
                java.lang.String r2 = r2.toString()
                r1.<init>(r0, r2)
                boolean r0 = r1.exists()
                if (r0 != 0) goto L71
                boolean r0 = r1.createNewFile()     // Catch: java.io.IOException -> L6d
                if (r0 == 0) goto L68
                java.lang.String r0 = com.tencent.aai.audio.utils.WavCache.TAG     // Catch: java.io.IOException -> L6d
                java.lang.String r2 = "创建文件成功"
            L64:
                com.tencent.aai.log.AAILogger.info(r0, r2)     // Catch: java.io.IOException -> L6d
                goto L71
            L68:
                java.lang.String r0 = com.tencent.aai.audio.utils.WavCache.TAG     // Catch: java.io.IOException -> L6d
                java.lang.String r2 = "创建文件失败"
                goto L64
            L6d:
                r0 = move-exception
                r0.printStackTrace()
            L71:
                r0 = 0
                java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L8d java.io.IOException -> L8f java.io.FileNotFoundException -> L9b
                r2.<init>(r1)     // Catch: java.lang.Throwable -> L8d java.io.IOException -> L8f java.io.FileNotFoundException -> L9b
                short[] r0 = r5.f928a     // Catch: java.io.IOException -> L89 java.io.FileNotFoundException -> L8b java.lang.Throwable -> La9
                byte[] r0 = com.tencent.aai.audio.utils.CharUtils.shortArray2ByteArray(r0)     // Catch: java.io.IOException -> L89 java.io.FileNotFoundException -> L8b java.lang.Throwable -> La9
                int r1 = r5.f929b     // Catch: java.io.IOException -> L89 java.io.FileNotFoundException -> L8b java.lang.Throwable -> La9
                int r1 = r1 * 2
                int r3 = r5.f930c     // Catch: java.io.IOException -> L89 java.io.FileNotFoundException -> L8b java.lang.Throwable -> La9
                int r3 = r3 * 2
                r2.write(r0, r1, r3)     // Catch: java.io.IOException -> L89 java.io.FileNotFoundException -> L8b java.lang.Throwable -> La9
                goto L97
            L89:
                r0 = move-exception
                goto L92
            L8b:
                r0 = move-exception
                goto L9e
            L8d:
                r1 = move-exception
                goto Lac
            L8f:
                r1 = move-exception
                r2 = r0
                r0 = r1
            L92:
                r0.printStackTrace()     // Catch: java.lang.Throwable -> La9
                if (r2 == 0) goto La8
            L97:
                r2.close()     // Catch: java.io.IOException -> La4
                goto La8
            L9b:
                r1 = move-exception
                r2 = r0
                r0 = r1
            L9e:
                r0.printStackTrace()     // Catch: java.lang.Throwable -> La9
                if (r2 == 0) goto La8
                goto L97
            La4:
                r0 = move-exception
                r0.printStackTrace()
            La8:
                return
            La9:
                r0 = move-exception
                r1 = r0
                r0 = r2
            Lac:
                if (r0 == 0) goto Lb6
                r0.close()     // Catch: java.io.IOException -> Lb2
                goto Lb6
            Lb2:
                r0 = move-exception
                r0.printStackTrace()
            Lb6:
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.aai.audio.utils.WavCache.RunnableC2595a.run():void");
        }
    }

    /* JADX INFO: renamed from: com.tencent.aai.audio.utils.WavCache$b */
    public static class RunnableC2596b implements Runnable {

        /* JADX INFO: renamed from: a */
        public final /* synthetic */ List f931a;

        public RunnableC2596b(List list) {
            this.f931a = list;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!PcmToWav.mergePCMFilesToWAVFile(this.f931a, FileUtils.getWavFileAbsolutePath("merge"))) {
                throw new IllegalStateException("mergePCMFilesToWAVFile fail");
            }
        }
    }

    private static void WriteWaveFileHeader(FileOutputStream fileOutputStream, long j, long j2, long j3, int i, long j4) throws IOException {
        fileOutputStream.write(new byte[]{DeviceProtocol.MSG_ID_WIFI.ACK_RECORD_STOP, 73, 70, 70, (byte) (j2 & 255), (byte) ((j2 >> 8) & 255), (byte) ((j2 >> 16) & 255), (byte) ((j2 >> 24) & 255), 87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, (byte) i, 0, (byte) (j3 & 255), (byte) ((j3 >> 8) & 255), (byte) ((j3 >> 16) & 255), (byte) ((j3 >> 24) & 255), (byte) (j4 & 255), (byte) ((j4 >> 8) & 255), (byte) ((j4 >> 16) & 255), (byte) ((j4 >> 24) & 255), 4, 0, 16, 0, 100, 97, 116, 97, (byte) (j & 255), (byte) ((j >> 8) & 255), (byte) ((j >> 16) & 255), (byte) ((j >> 24) & 255)}, 0, 44);
    }

    public static void cache(short[] sArr, int i, int i2) {
        new Thread(new RunnableC2595a(sArr, i, i2)).start();
    }

    public static void closeDataOutputStream(DataOutputStream dataOutputStream) {
        if (dataOutputStream != null) {
            try {
                dataOutputStream.close();
            } catch (Throwable th) {
                AAILogger.error("WavCache", "throwable----" + th.getMessage());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00d4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00a8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00bf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00de A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.FileOutputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void copyWaveFile() throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 231
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.aai.audio.utils.WavCache.copyWaveFile():void");
    }

    public static DataOutputStream creatPmcFile() {
        File file = new File(Environment.getExternalStorageDirectory().toString() + parentDirName);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file, "merge.pcm");
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            return new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file2)));
        } catch (FileNotFoundException e2) {
            AAILogger.error(TAG, e2.toString());
            e2.printStackTrace();
            return null;
        }
    }

    public static DataOutputStream creatPmcFileByPath(String str, String str2) {
        File file = new File(str);
        if (!file.exists()) {
            AAILogger.info("", file.mkdirs() ? "创建文件夹成功" : "创建文件夹失败");
        }
        File file2 = new File(file, str2);
        if (!file2.exists()) {
            try {
                AAILogger.info("", file2.createNewFile() ? "创建文件成功" : "创建文件失败");
            } catch (IOException e) {
                e.printStackTrace();
                AAILogger.error(TAG, e.toString());
            }
        }
        AAILogger.info("WavCache", "生成文件");
        try {
            return new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file2)));
        } catch (FileNotFoundException e2) {
            AAILogger.error(TAG, e2.toString());
            e2.printStackTrace();
            return null;
        }
    }

    public static void deleteAllFiles() {
        File[] fileArrListFiles;
        File file = new File(Environment.getExternalStorageDirectory().toString() + parentDirName);
        if (!file.isDirectory() || (fileArrListFiles = file.listFiles()) == null || fileArrListFiles.length <= 0) {
            return;
        }
        for (File file2 : fileArrListFiles) {
            file2.getName();
            file2.delete();
        }
    }

    public static void deletePcm() {
        File[] fileArrListFiles;
        File file = new File(Environment.getExternalStorageDirectory().toString() + parentDirName);
        if (!file.isDirectory() || (fileArrListFiles = file.listFiles()) == null || fileArrListFiles.length <= 0) {
            return;
        }
        for (File file2 : fileArrListFiles) {
            String name = file2.getName();
            if (name.toLowerCase().endsWith(".pcm") && !name.toLowerCase().equals("merge.pcm")) {
                file2.delete();
            }
        }
    }

    public static List<String> getPcmFiles() {
        File[] fileArrListFiles;
        ArrayList arrayList = new ArrayList();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + parentDirName);
        if (file.exists() && (fileArrListFiles = file.listFiles()) != null && fileArrListFiles.length > 0) {
            for (File file2 : fileArrListFiles) {
                arrayList.add(file2.getPath());
            }
        }
        return arrayList;
    }

    public static String getWavCacheFiles(String str) {
        File[] fileArrListFiles;
        try {
            File file = new File(Environment.getExternalStorageDirectory().toString() + parentDirName);
            if (!file.isDirectory() || (fileArrListFiles = file.listFiles()) == null || fileArrListFiles.length <= 0) {
                return null;
            }
            for (File file2 : fileArrListFiles) {
                if (file2.getName().toLowerCase().endsWith(str)) {
                    return file2.getPath();
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void makePCMFileToWAVFile() throws Throwable {
        String strPcmToWavFile;
        String str = Environment.getExternalStorageDirectory().toString() + parentDirName + "/merge.pcm";
        try {
            strPcmToWavFile = pcmToWavFile(str);
        } catch (IOException e) {
            e.printStackTrace();
            strPcmToWavFile = null;
        }
        if (strPcmToWavFile == null) {
            return;
        }
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
        AAILogger.info("PcmToWav", "makePCMFileToWAVFile  success!" + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date()));
    }

    public static void makePCMFileToWAVFile(String str, String str2) throws Throwable {
        String strPcmToWavFile;
        String str3 = str + "/" + str2;
        try {
            strPcmToWavFile = pcmToWavFile(str, str2);
        } catch (IOException e) {
            e.printStackTrace();
            strPcmToWavFile = null;
        }
        if (strPcmToWavFile == null) {
            return;
        }
        String str4 = str + "/" + str2.substring(0, str2.indexOf(".")) + PictureMimeType.WAV;
        ArrayList arrayList = new ArrayList();
        arrayList.add(strPcmToWavFile);
        PcmToWav.mergePCMFilesToWAVFile(arrayList, str4);
        File file = new File(str3);
        if (file.exists()) {
            file.delete();
        }
        AAILogger.info("PcmToWav", "makePCMFileToWAVFile  success!" + new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date()));
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x00cf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00d9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void mergeFiles() throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 227
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.aai.audio.utils.WavCache.mergeFiles():void");
    }

    public static void mergePCMFilesToWAVFile(List<String> list) {
        new Thread(new RunnableC2596b(list)).start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:104:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00b3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00d3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00dd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0099 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00a3 A[EXC_TOP_SPLITTER, PHI: r1 r2 r6
  0x00a3: PHI (r1v12 java.lang.String) = (r1v9 java.lang.String), (r1v10 java.lang.String), (r1v20 java.lang.String), (r1v20 java.lang.String) binds: [B:50:0x00a1, B:61:0x00bb, B:19:0x006f, B:16:0x006a] A[DONT_GENERATE, DONT_INLINE]
  0x00a3: PHI (r2v13 ??) = (r2v10 ??), (r2v11 ??), (r2v24 ?? I:??[int, short, byte, float]), (r2v16 ??) binds: [B:50:0x00a1, B:61:0x00bb, B:19:0x006f, B:16:0x006a] A[DONT_GENERATE, DONT_INLINE]
  0x00a3: PHI (r6v7 java.io.DataOutputStream) = 
  (r6v4 java.io.DataOutputStream)
  (r6v5 java.io.DataOutputStream)
  (r6v14 java.io.DataOutputStream)
  (r6v14 java.io.DataOutputStream)
 binds: [B:50:0x00a1, B:61:0x00bb, B:19:0x006f, B:16:0x006a] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v10, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.io.BufferedInputStream, java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String pcmToWavFile(java.lang.String r8) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.aai.audio.utils.WavCache.pcmToWavFile(java.lang.String):java.lang.String");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:104:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00b0 A[EXC_TOP_SPLITTER, PHI: r1 r2 r9
  0x00b0: PHI (r1v12 ??) = (r1v9 ??), (r1v10 ??), (r1v18 ??), (r1v18 ??) binds: [B:50:0x00ae, B:61:0x00c8, B:19:0x007c, B:16:0x0077] A[DONT_GENERATE, DONT_INLINE]
  0x00b0: PHI (r2v10 ??) = (r2v7 ??), (r2v8 ??), (r2v21 ?? I:??[int, short, byte, float]), (r2v13 ??) binds: [B:50:0x00ae, B:61:0x00c8, B:19:0x007c, B:16:0x0077] A[DONT_GENERATE, DONT_INLINE]
  0x00b0: PHI (r9v10 java.lang.String) = (r9v7 java.lang.String), (r9v8 java.lang.String), (r9v18 java.lang.String), (r9v18 java.lang.String) binds: [B:50:0x00ae, B:61:0x00c8, B:19:0x007c, B:16:0x0077] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00a6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00e1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00c0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12, types: [java.io.DataOutputStream] */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v18, types: [java.io.DataOutputStream] */
    /* JADX WARN: Type inference failed for: r1v19 */
    /* JADX WARN: Type inference failed for: r1v20 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.io.BufferedInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v4, types: [java.io.DataOutputStream] */
    /* JADX WARN: Type inference failed for: r9v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String pcmToWavFile(java.lang.String r8, java.lang.String r9) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 234
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.aai.audio.utils.WavCache.pcmToWavFile(java.lang.String, java.lang.String):java.lang.String");
    }

    public static void savePcmData(DataOutputStream dataOutputStream, short[] sArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            try {
                dataOutputStream.writeShort(sArr[i2]);
            } catch (Throwable th) {
                AAILogger.error("WavCache", "录音失败----" + th.getMessage());
                return;
            }
        }
    }
}
