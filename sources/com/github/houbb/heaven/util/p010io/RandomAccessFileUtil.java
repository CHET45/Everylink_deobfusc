package com.github.houbb.heaven.util.p010io;

import com.github.houbb.heaven.constant.FileOptionConst;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes3.dex */
public final class RandomAccessFileUtil {
    private RandomAccessFileUtil() {
    }

    public static String getFileContent(String str, int i, int i2) {
        return getFileContent(str, i, i2, StandardCharsets.UTF_8);
    }

    public static String getFileContent(String str, int i, int i2, Charset charset) {
        int i3 = i2 - i;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(str, FileOptionConst.READ);
            try {
                MappedByteBuffer map = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, i, i3);
                byte[] bArr = new byte[i3];
                for (int i4 = 0; i4 < map.capacity(); i4++) {
                    bArr[i4] = map.get(i4);
                }
                String str2 = new String(bArr, charset);
                randomAccessFile.close();
                return str2;
            } finally {
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }
}
