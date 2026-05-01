package com.lzy.okgo.request;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.utils.OkLogger;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/* JADX INFO: loaded from: classes4.dex */
public class ProgressRequestBody extends RequestBody {
    protected CountingSink countingSink;
    protected RequestBody delegate;
    protected Listener listener;

    public interface Listener {
        void onRequestProgress(long j, long j2, long j3);
    }

    public ProgressRequestBody(RequestBody requestBody) {
        this.delegate = requestBody;
    }

    public ProgressRequestBody(RequestBody requestBody, Listener listener) {
        this.delegate = requestBody;
        this.listener = listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        return this.delegate.contentType();
    }

    @Override // okhttp3.RequestBody
    public long contentLength() {
        try {
            return this.delegate.contentLength();
        } catch (IOException e) {
            OkLogger.m864e(e);
            return -1L;
        }
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        CountingSink countingSink = new CountingSink(bufferedSink);
        this.countingSink = countingSink;
        BufferedSink bufferedSinkBuffer = Okio.buffer(countingSink);
        this.delegate.writeTo(bufferedSinkBuffer);
        bufferedSinkBuffer.flush();
    }

    protected final class CountingSink extends ForwardingSink {
        private long bytesWritten;
        private long contentLength;
        private long lastRefreshUiTime;
        private long lastWriteBytes;

        public CountingSink(Sink sink) {
            super(sink);
            this.bytesWritten = 0L;
            this.contentLength = 0L;
        }

        @Override // okio.ForwardingSink, okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            super.write(buffer, j);
            if (this.contentLength <= 0) {
                this.contentLength = ProgressRequestBody.this.contentLength();
            }
            this.bytesWritten += j;
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.lastRefreshUiTime >= OkGo.REFRESH_TIME || this.bytesWritten == this.contentLength) {
                long j2 = (jCurrentTimeMillis - this.lastRefreshUiTime) / 1000;
                if (j2 == 0) {
                    j2++;
                }
                long j3 = (this.bytesWritten - this.lastWriteBytes) / j2;
                if (ProgressRequestBody.this.listener != null) {
                    ProgressRequestBody.this.listener.onRequestProgress(this.bytesWritten, this.contentLength, j3);
                }
                this.lastRefreshUiTime = System.currentTimeMillis();
                this.lastWriteBytes = this.bytesWritten;
            }
        }
    }
}
