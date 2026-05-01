package com.aivox.base.util;

import android.os.Environment;
import android.os.StatFs;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class SDCardUtil {
    private SDCardUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    public static long getSDCardAllSize() {
        if (!isSDCardEnable()) {
            return 0L;
        }
        StatFs statFs = new StatFs(getSDCardPath());
        return ((long) statFs.getAvailableBlocks()) * (((long) statFs.getAvailableBlocks()) - 4);
    }

    public static long getFreeBytes(String str) {
        String absolutePath;
        if (str.startsWith(getSDCardPath())) {
            absolutePath = getSDCardPath();
        } else {
            absolutePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs statFs = new StatFs(absolutePath);
        return ((long) statFs.getBlockSize()) * (((long) statFs.getAvailableBlocks()) - 4);
    }

    public static long getFreeKbs() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return (((long) statFs.getBlockSize()) * (((long) statFs.getAvailableBlocks()) - 4)) / 1024;
    }

    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }
}
