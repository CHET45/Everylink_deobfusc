package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.common.QCloudProgressListener;
import java.io.IOException;
import okio.Buffer;
import okio.ForwardingSink;
import okio.Sink;

/* JADX INFO: loaded from: classes4.dex */
class CountingSink extends ForwardingSink {
    private long bytesTotal;
    private long bytesWritten;
    private long lastTimeBytesWritten;
    private QCloudProgressListener progressListener;
    private long recentReportBytes;

    public CountingSink(Sink sink, long j, QCloudProgressListener qCloudProgressListener) {
        super(sink);
        this.lastTimeBytesWritten = 0L;
        this.bytesWritten = 0L;
        this.recentReportBytes = 0L;
        this.bytesTotal = j;
        this.progressListener = qCloudProgressListener;
    }

    public CountingSink(Sink sink, long j, long j2, QCloudProgressListener qCloudProgressListener) {
        super(sink);
        this.bytesWritten = 0L;
        this.recentReportBytes = 0L;
        this.bytesTotal = j;
        this.lastTimeBytesWritten = j2;
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
        long j5 = this.lastTimeBytesWritten;
        qCloudProgressListener.onProgress(j + j5, j5 + this.bytesTotal);
    }

    void writeBytesInternal(long j) {
        this.bytesWritten += j;
        reportProgress();
    }

    long getTotalTransferred() {
        return this.bytesWritten + this.lastTimeBytesWritten;
    }

    long getBytesWritten() {
        return this.bytesWritten;
    }

    @Override // okio.ForwardingSink, okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        super.write(buffer, j);
        writeBytesInternal(j);
    }
}
