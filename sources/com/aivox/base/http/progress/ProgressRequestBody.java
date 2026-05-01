package com.aivox.base.http.progress;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/* JADX INFO: loaded from: classes.dex */
public class ProgressRequestBody extends RequestBody {
    private BufferedSink bufferedSink;
    private final ProgressRequestListener progressListener;
    private final RequestBody requestBody;

    public ProgressRequestBody(RequestBody requestBody, ProgressRequestListener progressRequestListener) {
        this.requestBody = requestBody;
        this.progressListener = progressRequestListener;
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        return this.requestBody.contentType();
    }

    @Override // okhttp3.RequestBody
    public long contentLength() throws IOException {
        return this.requestBody.contentLength();
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        if (this.bufferedSink == null) {
            this.bufferedSink = Okio.buffer(sink(bufferedSink));
        }
        this.requestBody.writeTo(this.bufferedSink);
        this.bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) { // from class: com.aivox.base.http.progress.ProgressRequestBody.1
            long bytesWritten = 0;
            long contentLength = 0;

            @Override // okio.ForwardingSink, okio.Sink
            public void write(Buffer buffer, long j) throws IOException {
                super.write(buffer, j);
                if (this.contentLength == 0) {
                    this.contentLength = ProgressRequestBody.this.contentLength();
                }
                this.bytesWritten += j;
                ProgressRequestListener progressRequestListener = ProgressRequestBody.this.progressListener;
                long j2 = this.bytesWritten;
                long j3 = this.contentLength;
                progressRequestListener.onRequestProgress(j2, j3, j2 == j3);
            }
        };
    }
}
