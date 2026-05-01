package com.aivox.base.img.imageloader;

import android.util.Log;
import com.aivox.base.util.BaseAppUtils;
import com.luck.picture.lib.config.FileSizeUnit;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/* JADX INFO: loaded from: classes.dex */
public class FileSizeUtil {
    public static final int SIZETYPE_B = 1;
    public static final int SIZETYPE_GB = 4;
    public static final int SIZETYPE_KB = 2;
    public static final int SIZETYPE_MB = 3;

    public static double getFileOrFilesSize(String str, int i) {
        long fileSize;
        File file = new File(str);
        try {
            if (file.isDirectory()) {
                fileSize = getFileSizes(file);
            } else {
                fileSize = getFileSize(file);
            }
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            Log.e("获取文件大小", "获取失败!");
            fileSize = 0;
        }
        return FormetFileSize(fileSize, i);
    }

    public static String getAutoFileOrFilesSize(String str) {
        long fileSize;
        File file = new File(str);
        try {
            if (file.isDirectory()) {
                fileSize = getFileSizes(file);
            } else {
                fileSize = getFileSize(file);
            }
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            Log.e("获取文件大小", "获取失败!");
            fileSize = 0;
        }
        return FormetFileSize(fileSize);
    }

    public static String getSize(File file) {
        long fileSize;
        try {
            if (file.isDirectory()) {
                fileSize = getFileSizes(file);
            } else {
                fileSize = getFileSize(file);
            }
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            Log.e("获取文件大小", "获取失败!");
            fileSize = 0;
        }
        return FormetFileSize(fileSize);
    }

    private static long getFileSize(File file) throws Exception {
        if (file.exists()) {
            return new FileInputStream(file).available();
        }
        file.createNewFile();
        Log.e("获取文件大小", "文件不存在!");
        return 0L;
    }

    private static long getFileSizes(File file) throws Exception {
        long fileSize;
        File[] fileArrListFiles = file.listFiles();
        long j = 0;
        for (int i = 0; i < fileArrListFiles.length; i++) {
            if (fileArrListFiles[i].isDirectory()) {
                fileSize = getFileSizes(fileArrListFiles[i]);
            } else {
                fileSize = getFileSize(fileArrListFiles[i]);
            }
            j += fileSize;
        }
        return j;
    }

    public static String FormetFileSize(long j) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (j == 0) {
            return "0B";
        }
        if (j < 1024) {
            return decimalFormat.format(j) + "B";
        }
        if (j < 1048576) {
            return decimalFormat.format(j / 1024.0d) + "KB";
        }
        if (j < FileSizeUnit.f822GB) {
            return decimalFormat.format(j / 1048576.0d) + "MB";
        }
        return decimalFormat.format(j / 1.073741824E9d) + "GB";
    }

    public static double FormetFileSize(long j, int i) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (i == 1) {
            return Double.valueOf(decimalFormat.format(j)).doubleValue();
        }
        if (i == 2) {
            return Double.valueOf(decimalFormat.format(j / 1024.0d)).doubleValue();
        }
        if (i == 3) {
            return Double.valueOf(decimalFormat.format(j / 1048576.0d)).doubleValue();
        }
        if (i != 4) {
            return 0.0d;
        }
        return Double.valueOf(decimalFormat.format(j / 1.073741824E9d)).doubleValue();
    }
}
