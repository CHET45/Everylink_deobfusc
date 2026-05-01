package com.github.houbb.heaven.util.p010io.ext.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.constant.FileOptionConst;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.p010io.ext.IFiles;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class RandomAccessFiles implements IFiles {
    private final RandomAccessFile file;

    private long getMin(long j, long j2) {
        return j <= j2 ? j : j2;
    }

    public RandomAccessFiles(String str) {
        try {
            this.file = new RandomAccessFile(str, FileOptionConst.READ_WRITE);
        } catch (FileNotFoundException e) {
            throw new CommonRuntimeException(e);
        }
    }

    @Override // com.github.houbb.heaven.util.p010io.ext.IFiles
    public byte[] read(long j, long j2) {
        try {
            ArgUtil.notNegative(j, "startIndex not allow negative!");
            ArgUtil.assertTrue(j2 >= j, "endIndex >= startIndex is expected!");
            int min = (int) (getMin(this.file.length(), j2) - j);
            byte[] bArr = new byte[min];
            this.file.readFully(bArr, (int) j, min);
            return bArr;
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }

    @Override // com.github.houbb.heaven.util.p010io.ext.IFiles
    public String read(long j, long j2, String str) {
        try {
            return new String(read(j, j2), str);
        } catch (UnsupportedEncodingException e) {
            throw new CommonRuntimeException(e);
        }
    }

    @Override // com.github.houbb.heaven.util.p010io.ext.IFiles
    public void write(long j, byte[] bArr) {
        ArgUtil.notNegative(j, "startIndex not allow negative!");
        try {
            long length = this.file.length();
            long min = getMin(j, length);
            byte[] bArr2 = read(min, length);
            this.file.seek(min);
            this.file.write(bArr);
            this.file.write(bArr2);
        } catch (IOException e) {
            throw new CommonRuntimeException(e);
        }
    }
}
