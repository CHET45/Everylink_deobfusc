package com.github.houbb.heaven.util.p010io.big;

import com.azure.xml.implementation.aalto.util.CharsetNames;
import com.github.houbb.heaven.constant.FileOptionConst;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* JADX INFO: loaded from: classes3.dex */
public final class BigFileUtil {
    private BigFileUtil() {
    }

    public static void read(String str) {
        read(str, new BigFileStringHandlerPrint());
    }

    public static void read(String str, IBigFileStringHandler iBigFileStringHandler) {
        read(str, iBigFileStringHandler, "UTF-8");
    }

    public static void read(String str, IBigFileStringHandler iBigFileStringHandler, String str2) {
        read(str, iBigFileStringHandler, str2, 0L);
    }

    public static void read(String str, IBigFileStringHandler iBigFileStringHandler, String str2, long j) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(str), FileOptionConst.READ);
            randomAccessFile.seek(j);
            BigFileStringHandlerContext bigFileStringHandlerContext = new BigFileStringHandlerContext();
            bigFileStringHandlerContext.setFilePath(str);
            bigFileStringHandlerContext.setCharset(str2);
            int i = 0;
            while (true) {
                String line = randomAccessFile.readLine();
                if (line != null) {
                    String str3 = new String(line.getBytes(CharsetNames.CS_ISO_LATIN1), str2);
                    bigFileStringHandlerContext.setIndex(i);
                    bigFileStringHandlerContext.setLine(str3);
                    bigFileStringHandlerContext.setFilePointer(randomAccessFile.getFilePointer());
                    iBigFileStringHandler.handle(bigFileStringHandlerContext);
                    i++;
                } else {
                    randomAccessFile.close();
                    return;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void segFile(String str) {
        segFile(str, 100000);
    }

    public static void segFile(String str, int i) {
        segFile(str, i, "UTF-8");
    }

    public static void segFile(String str, int i, String str2) {
        segFile(str, i, str2, 0L);
    }

    public static void segFile(String str, int i, String str2, long j) {
        read(str, new BigFileStringHandlerSegment(i), str2, j);
    }
}
