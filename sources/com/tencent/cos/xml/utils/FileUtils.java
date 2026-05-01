package com.tencent.cos.xml.utils;

import android.util.Log;
import com.github.houbb.heaven.constant.FileOptionConst;
import com.tencent.cos.xml.CosXmlBaseService;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.qcloud.core.logger.COSLogger;
import com.tencent.qcloud.core.util.OkhttpInternalUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/* JADX INFO: loaded from: classes4.dex */
public class FileUtils {
    public static void saveInputStreamToTmpFile(InputStream inputStream, File file, long j, long j2) throws Throwable {
        FileOutputStream fileOutputStream;
        int i;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr = new byte[8192];
            long j3 = 0;
            if (j2 < 0) {
                j2 = Long.MAX_VALUE;
            }
            if (j > 0) {
                inputStream.skip(j);
            }
            while (j3 < j2 && (i = inputStream.read(bArr)) != -1) {
                long j4 = i;
                fileOutputStream.write(bArr, 0, (int) Math.min(j4, j2 - j3));
                j3 += j4;
            }
            fileOutputStream.flush();
            OkhttpInternalUtils.closeQuietly(fileOutputStream);
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                OkhttpInternalUtils.closeQuietly(fileOutputStream2);
            }
            throw th;
        }
    }

    public static String tempCache(InputStream inputStream) throws Throwable {
        FileOutputStream fileOutputStream = null;
        if (inputStream == null) {
            return null;
        }
        try {
            try {
                String str = CosXmlBaseService.appCachePath + File.separator + "temp.tmp";
                Log.d("UnitTest", str);
                File file = new File(str);
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[65536];
                    while (true) {
                        int i = inputStream.read(bArr, 0, 65536);
                        if (i > 0) {
                            fileOutputStream2.write(bArr, 0, i);
                        } else {
                            fileOutputStream2.flush();
                            CloseUtil.closeQuietly(fileOutputStream2);
                            CloseUtil.closeQuietly(inputStream);
                            return str;
                        }
                    }
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    throw new CosXmlClientException(ClientErrorCode.IO_ERROR.getCode(), e);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    CloseUtil.closeQuietly(fileOutputStream);
                    CloseUtil.closeQuietly(inputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e2) {
            e = e2;
        }
    }

    public static void intercept(String str, long j, long j2) throws IOException {
        if (j2 <= 0) {
            clearFile(str);
        }
        File file = new File(str);
        File file2 = new File(str.concat("." + System.currentTimeMillis() + ".temp"));
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        FileInputStream fileInputStream = new FileInputStream(file);
        if (j > 0 && fileInputStream.skip(j) != j) {
            throw new IOException("skip size is not equal to offset");
        }
        byte[] bArr = new byte[65536];
        long j3 = 65536;
        long jMin = Math.min(j3, j2);
        while (true) {
            int i = fileInputStream.read(bArr, 0, (int) jMin);
            if (i <= 0) {
                break;
            }
            fileOutputStream.write(bArr, 0, i);
            j2 -= (long) i;
            jMin = Math.min(j3, j2);
        }
        deleteFileIfExist(str);
        if (!file2.renameTo(file)) {
            throw new IOException("rename to " + str + "failed");
        }
    }

    public static boolean deleteFileIfExist(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static boolean clearFile(String str) throws IOException {
        if (deleteFileIfExist(str)) {
            return new File(str).createNewFile();
        }
        return false;
    }

    public static File[] listFile(File file) {
        if (file == null || !file.isDirectory()) {
            return null;
        }
        return file.listFiles();
    }

    public static boolean truncateFile(File file, long j) {
        if (file != null && file.exists()) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, FileOptionConst.READ_WRITE);
                try {
                    randomAccessFile.setLength(j);
                    randomAccessFile.close();
                    return true;
                } finally {
                }
            } catch (IOException e) {
                COSLogger.wProcess("FileUtils", "Truncate file failed: " + e.getMessage());
            }
        }
        return false;
    }
}
