package com.tencent.qcloud.core.http;

import android.content.ContentResolver;
import android.net.Uri;
import android.text.TextUtils;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudProgressListener;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.util.OkhttpInternalUtils;
import com.tencent.qcloud.core.util.QCloudHttpUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import okio.Buffer;

/* JADX INFO: loaded from: classes4.dex */
public class ResponseFileConverter<T> extends ResponseBodyConverter<T> implements ProgressBody {
    private ContentResolver contentResolver;
    private Uri contentUri;
    private CountingSink countingSink;
    private String filePath;
    private InputStream inputStream;
    protected boolean isQuic = false;
    private long offset;
    protected QCloudProgressListener progressListener;

    public ResponseFileConverter(String str, long j) {
        this.filePath = str;
        this.offset = j;
    }

    public ResponseFileConverter(Uri uri, ContentResolver contentResolver, long j) {
        this.contentUri = uri;
        this.contentResolver = contentResolver;
        this.offset = j;
    }

    public ResponseFileConverter() {
    }

    @Override // com.tencent.qcloud.core.http.ProgressBody
    public void setProgressListener(QCloudProgressListener qCloudProgressListener) {
        this.progressListener = qCloudProgressListener;
    }

    public void enableQuic(boolean z) {
        this.isQuic = z;
    }

    public QCloudProgressListener getProgressListener() {
        return this.progressListener;
    }

    @Override // com.tencent.qcloud.core.http.ResponseBodyConverter
    public T convert(HttpResponse<T> httpResponse) throws QCloudServiceException, QCloudClientException {
        long jContentLength;
        if (this.isQuic) {
            return null;
        }
        HttpResponse.checkResponseSuccessful(httpResponse);
        long[] contentRange = QCloudHttpUtils.parseContentRange(httpResponse.header("Content-Range"));
        if (contentRange != null) {
            jContentLength = (contentRange[1] - contentRange[0]) + 1;
        } else {
            jContentLength = httpResponse.contentLength();
        }
        if (!TextUtils.isEmpty(this.filePath)) {
            return downloadToAbsolutePath(httpResponse, jContentLength);
        }
        if (this.contentUri != null) {
            return pipeToContentUri(httpResponse, jContentLength);
        }
        throw new QCloudClientException(new IllegalArgumentException("filePath or ContentUri are both null"));
    }

    private T pipeToContentUri(HttpResponse<T> httpResponse, long j) throws QCloudServiceException, QCloudClientException {
        OutputStream outputStream = getOutputStream();
        InputStream inputStreamByteStream = httpResponse.byteStream();
        byte[] bArr = new byte[8192];
        this.countingSink = new CountingSink(new Buffer(), j, this.progressListener);
        while (true) {
            try {
                try {
                    int i = inputStreamByteStream.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    outputStream.write(bArr, 0, i);
                    this.countingSink.writeBytesInternal(i);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new QCloudClientException("write local uri error for " + e.toString(), e);
                }
            } finally {
                if (outputStream != null) {
                    OkhttpInternalUtils.closeQuietly(outputStream);
                }
            }
        }
    }

    private T downloadToAbsolutePath(HttpResponse<T> httpResponse, long j) throws Throwable {
        File file = new File(this.filePath);
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
            throw new QCloudClientException(new IOException("local file directory can not create."));
        }
        if (httpResponse.response.body() == null) {
            throw new QCloudServiceException("response body is empty !");
        }
        try {
            writeRandomAccessFile(file, httpResponse.byteStream(), j);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new QCloudClientException("write local file error for " + e.toString(), e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeRandomAccessFile(java.io.File r15, java.io.InputStream r16, long r17) throws java.lang.Throwable {
        /*
            r14 = this;
            r1 = r14
            r0 = r15
            r2 = r16
            if (r2 == 0) goto L88
            r3 = 0
            long r8 = r14.getBytesTransferred()     // Catch: java.lang.Throwable -> L75
            long r4 = r1.offset     // Catch: java.lang.Throwable -> L75
            long r4 = r4 + r8
            r6 = 0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 <= 0) goto L31
            java.io.RandomAccessFile r4 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L75
            java.lang.String r5 = "rws"
            r4.<init>(r15, r5)     // Catch: java.lang.Throwable -> L75
            long r5 = r1.offset     // Catch: java.lang.Throwable -> L2d
            long r5 = r5 + r8
            r4.seek(r5)     // Catch: java.lang.Throwable -> L2d
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L2d
            java.io.FileDescriptor r5 = r4.getFD()     // Catch: java.lang.Throwable -> L2d
            r0.<init>(r5)     // Catch: java.lang.Throwable -> L2d
            r11 = r0
            r12 = r4
            goto L38
        L2d:
            r0 = move-exception
            r11 = r3
            r12 = r4
            goto L78
        L31:
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L75
            r4.<init>(r15)     // Catch: java.lang.Throwable -> L75
            r12 = r3
            r11 = r4
        L38:
            java.io.BufferedOutputStream r13 = new java.io.BufferedOutputStream     // Catch: java.lang.Throwable -> L73
            r13.<init>(r11)     // Catch: java.lang.Throwable -> L73
            r0 = 1048576(0x100000, float:1.469368E-39)
            byte[] r0 = new byte[r0]     // Catch: java.lang.Throwable -> L70
            com.tencent.qcloud.core.http.CountingSink r3 = new com.tencent.qcloud.core.http.CountingSink     // Catch: java.lang.Throwable -> L70
            okio.Buffer r5 = new okio.Buffer     // Catch: java.lang.Throwable -> L70
            r5.<init>()     // Catch: java.lang.Throwable -> L70
            com.tencent.qcloud.core.common.QCloudProgressListener r10 = r1.progressListener     // Catch: java.lang.Throwable -> L70
            r4 = r3
            r6 = r17
            r4.<init>(r5, r6, r8, r10)     // Catch: java.lang.Throwable -> L70
            r1.countingSink = r3     // Catch: java.lang.Throwable -> L70
        L52:
            int r3 = r2.read(r0)     // Catch: java.lang.Throwable -> L70
            r4 = -1
            if (r3 == r4) goto L64
            r4 = 0
            r13.write(r0, r4, r3)     // Catch: java.lang.Throwable -> L70
            com.tencent.qcloud.core.http.CountingSink r4 = r1.countingSink     // Catch: java.lang.Throwable -> L70
            long r5 = (long) r3     // Catch: java.lang.Throwable -> L70
            r4.writeBytesInternal(r5)     // Catch: java.lang.Throwable -> L70
            goto L52
        L64:
            com.tencent.qcloud.core.util.OkhttpInternalUtils.closeQuietly(r13)
            com.tencent.qcloud.core.util.OkhttpInternalUtils.closeQuietly(r11)
            if (r12 == 0) goto L6f
            com.tencent.qcloud.core.util.OkhttpInternalUtils.closeQuietly(r12)
        L6f:
            return
        L70:
            r0 = move-exception
            r3 = r13
            goto L78
        L73:
            r0 = move-exception
            goto L78
        L75:
            r0 = move-exception
            r11 = r3
            r12 = r11
        L78:
            if (r3 == 0) goto L7d
            com.tencent.qcloud.core.util.OkhttpInternalUtils.closeQuietly(r3)
        L7d:
            if (r11 == 0) goto L82
            com.tencent.qcloud.core.util.OkhttpInternalUtils.closeQuietly(r11)
        L82:
            if (r12 == 0) goto L87
            com.tencent.qcloud.core.util.OkhttpInternalUtils.closeQuietly(r12)
        L87:
            throw r0
        L88:
            com.tencent.qcloud.core.common.QCloudClientException r0 = new com.tencent.qcloud.core.common.QCloudClientException
            java.io.IOException r2 = new java.io.IOException
            java.lang.String r3 = "response body stream is null"
            r2.<init>(r3)
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qcloud.core.http.ResponseFileConverter.writeRandomAccessFile(java.io.File, java.io.InputStream, long):void");
    }

    public OutputStream getOutputStream() throws QCloudClientException {
        if (!TextUtils.isEmpty(this.filePath)) {
            File file = new File(this.filePath);
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                throw new QCloudClientException(new IOException("local file directory can not create."));
            }
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new QCloudClientException(e);
            }
        }
        Uri uri = this.contentUri;
        if (uri != null) {
            try {
                return this.contentResolver.openOutputStream(uri);
            } catch (FileNotFoundException e2) {
                throw new QCloudClientException(e2);
            }
        }
        throw new QCloudClientException(new IllegalArgumentException("filePath or ContentUri are both null"));
    }

    @Override // com.tencent.qcloud.core.http.ProgressBody
    public long getBytesTransferred() {
        CountingSink countingSink = this.countingSink;
        if (countingSink != null) {
            return countingSink.getTotalTransferred();
        }
        return 0L;
    }

    public boolean isFilePathConverter() {
        return !TextUtils.isEmpty(this.filePath);
    }
}
