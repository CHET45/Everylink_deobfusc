package com.github.houbb.heaven.util.p010io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* JADX INFO: loaded from: classes3.dex */
public class ZipUtil {
    public static void extractWar(String str, String str2) {
        try {
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdirs();
            }
            ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Paths.get(str, new String[0]), new OpenOption[0]));
            try {
                for (ZipEntry nextEntry = zipInputStream.getNextEntry(); nextEntry != null; nextEntry = zipInputStream.getNextEntry()) {
                    String str3 = str2 + File.separator + nextEntry.getName();
                    if (!nextEntry.isDirectory()) {
                        extractFile(zipInputStream, str3);
                    } else {
                        new File(str3).mkdir();
                    }
                    zipInputStream.closeEntry();
                }
                zipInputStream.close();
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void extractFile(ZipInputStream zipInputStream, String str) {
        try {
            FileUtil.createFile(str);
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = zipInputStream.read(bArr);
                    if (i > 0) {
                        fileOutputStream.write(bArr, 0, i);
                    } else {
                        fileOutputStream.close();
                        return;
                    }
                }
            } finally {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
