package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.common.QCloudProgressListener;
import com.tencent.qcloud.core.logger.COSLogger;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes4.dex */
class CountingInputStream extends FilterInputStream {
    private long bytesTotal;
    private long bytesWritten;
    private long mark;
    private QCloudProgressListener progressListener;
    private long recentReportBytes;

    public CountingInputStream(InputStream inputStream, long j, QCloudProgressListener qCloudProgressListener) {
        super(inputStream);
        this.bytesWritten = 0L;
        this.recentReportBytes = 0L;
        this.mark = -1L;
        this.bytesTotal = j;
        this.progressListener = qCloudProgressListener;
    }

    private void reportProgress() {
        QCloudProgressListener qCloudProgressListener = this.progressListener;
        if (qCloudProgressListener == null) {
            return;
        }
        long j = this.bytesWritten;
        long j2 = j - this.recentReportBytes;
        if (j2 <= 51200) {
            long j3 = j2 * 10;
            long j4 = this.bytesTotal;
            if (j3 <= j4 && j != j4) {
                return;
            }
        }
        this.recentReportBytes = j;
        qCloudProgressListener.onProgress(j, this.bytesTotal);
    }

    void readBytesInternal(long j) {
        this.bytesWritten += j;
        reportProgress();
    }

    long getTotalTransferred() {
        return this.bytesWritten;
    }

    long getBytesTotal() {
        return this.bytesTotal;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = super.read(bArr, i, i2);
        if (i3 > 0) {
            readBytesInternal(i3);
        }
        return i3;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws IOException {
        long jSkip = super.skip(j);
        readBytesInternal(jSkip);
        return jSkip;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int i) {
        super.mark(i);
        this.mark = this.bytesWritten;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        if (!this.in.markSupported()) {
            throw new IOException("Mark not supported");
        }
        if (this.mark == -1) {
            throw new IOException("Mark not set");
        }
        this.in.reset();
        this.bytesWritten = this.mark;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        COSLogger.iProcess("Test", "CountingInputStream is closed");
    }
}
