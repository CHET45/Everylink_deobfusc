package com.tencent.qcloud.core.http;

import android.content.ContentResolver;
import android.net.Uri;
import com.tencent.qcloud.core.common.QCloudDigistListener;
import com.tencent.qcloud.core.common.QCloudProgressListener;
import com.tencent.qcloud.core.logger.COSLogger;
import com.tencent.qcloud.core.util.Base64Utils;
import com.tencent.qcloud.core.util.OkhttpInternalUtils;
import com.tencent.qcloud.core.util.QCloudUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/* JADX INFO: loaded from: classes4.dex */
public class StreamingRequestBody extends RequestBody implements ProgressBody, QCloudDigistListener {
    protected byte[] bytes;
    protected ContentResolver contentResolver;
    protected String contentType;
    protected CountingSink countingSink;
    protected File file;
    protected QCloudProgressListener progressListener;
    protected InputStream stream;
    protected Uri uri;
    protected URL url;
    protected long offset = 0;
    protected long requiredLength = -1;
    protected long contentRawLength = -1;
    private boolean deleteFileWhenComplete = false;

    @Override // com.tencent.qcloud.core.http.ProgressBody
    public void setProgressListener(QCloudProgressListener qCloudProgressListener) {
        this.progressListener = qCloudProgressListener;
    }

    public QCloudProgressListener getProgressListener() {
        return this.progressListener;
    }

    protected StreamingRequestBody() {
    }

    @Override // com.tencent.qcloud.core.http.ProgressBody
    public long getBytesTransferred() {
        CountingSink countingSink = this.countingSink;
        if (countingSink != null) {
            return countingSink.getTotalTransferred();
        }
        return 0L;
    }

    static StreamingRequestBody file(File file, String str) {
        return file(file, str, 0L, Long.MAX_VALUE);
    }

    static StreamingRequestBody file(File file, String str, long j, long j2) {
        StreamingRequestBody streamingRequestBody = new StreamingRequestBody();
        streamingRequestBody.file = file;
        streamingRequestBody.contentType = str;
        if (j < 0) {
            j = 0;
        }
        streamingRequestBody.offset = j;
        streamingRequestBody.requiredLength = j2;
        return streamingRequestBody;
    }

    static StreamingRequestBody bytes(byte[] bArr, String str, long j, long j2) {
        StreamingRequestBody streamingRequestBody = new StreamingRequestBody();
        streamingRequestBody.bytes = bArr;
        streamingRequestBody.contentType = str;
        if (j < 0) {
            j = 0;
        }
        streamingRequestBody.offset = j;
        streamingRequestBody.requiredLength = j2;
        return streamingRequestBody;
    }

    static StreamingRequestBody steam(InputStream inputStream, File file, String str, long j, long j2) {
        StreamingRequestBody streamingRequestBody = new StreamingRequestBody();
        streamingRequestBody.stream = inputStream;
        streamingRequestBody.contentType = str;
        streamingRequestBody.file = file;
        if (j < 0) {
            j = 0;
        }
        streamingRequestBody.offset = j;
        streamingRequestBody.requiredLength = j2;
        streamingRequestBody.deleteFileWhenComplete = true;
        return streamingRequestBody;
    }

    static StreamingRequestBody url(URL url, String str, long j, long j2) {
        StreamingRequestBody streamingRequestBody = new StreamingRequestBody();
        streamingRequestBody.url = url;
        streamingRequestBody.contentType = str;
        if (j < 0) {
            j = 0;
        }
        streamingRequestBody.offset = j;
        streamingRequestBody.requiredLength = j2;
        return streamingRequestBody;
    }

    static StreamingRequestBody uri(Uri uri, ContentResolver contentResolver, String str, long j, long j2) {
        StreamingRequestBody streamingRequestBody = new StreamingRequestBody();
        streamingRequestBody.uri = uri;
        streamingRequestBody.contentResolver = contentResolver;
        streamingRequestBody.contentType = str;
        if (j < 0) {
            j = 0;
        }
        streamingRequestBody.offset = j;
        streamingRequestBody.requiredLength = j2;
        return streamingRequestBody;
    }

    boolean isLargeData() {
        return (this.file == null && this.uri == null && this.stream == null) ? false : true;
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        String str = this.contentType;
        if (str != null) {
            return MediaType.parse(str);
        }
        return null;
    }

    @Override // okhttp3.RequestBody
    public long contentLength() throws IOException {
        long jMin;
        long contentRawLength = getContentRawLength();
        if (contentRawLength <= 0) {
            jMin = Math.max(this.requiredLength, -1L);
        } else {
            long j = this.requiredLength;
            if (j <= 0) {
                jMin = Math.max(contentRawLength - this.offset, -1L);
            } else {
                jMin = Math.min(contentRawLength - this.offset, j);
            }
        }
        if (jMin < 0) {
            return -1L;
        }
        return jMin;
    }

    protected long getContentRawLength() throws IOException {
        if (this.contentRawLength < 0) {
            if (this.stream != null) {
                this.contentRawLength = r0.available();
            } else {
                File file = this.file;
                if (file != null) {
                    this.contentRawLength = file.length();
                } else {
                    if (this.bytes != null) {
                        this.contentRawLength = r0.length;
                    } else {
                        Uri uri = this.uri;
                        if (uri != null) {
                            this.contentRawLength = QCloudUtils.getUriContentLength(uri, this.contentResolver);
                        }
                    }
                }
            }
        }
        return this.contentRawLength;
    }

    /* JADX WARN: Finally extract failed */
    public InputStream getStream() throws IOException {
        InputStream fileInputStream;
        if (this.bytes != null) {
            fileInputStream = new ByteArrayInputStream(this.bytes);
        } else {
            InputStream inputStream = this.stream;
            if (inputStream != null) {
                try {
                    saveInputStreamToTmpFile(inputStream, this.file);
                    InputStream inputStream2 = this.stream;
                    if (inputStream2 != null) {
                        OkhttpInternalUtils.closeQuietly(inputStream2);
                    }
                    this.stream = null;
                    this.offset = 0L;
                    fileInputStream = new FileInputStream(this.file);
                } catch (Throwable th) {
                    InputStream inputStream3 = this.stream;
                    if (inputStream3 != null) {
                        OkhttpInternalUtils.closeQuietly(inputStream3);
                    }
                    this.stream = null;
                    this.offset = 0L;
                    throw th;
                }
            } else if (this.file != null) {
                fileInputStream = new FileInputStream(this.file);
            } else {
                URL url = this.url;
                if (url != null) {
                    URLConnection uRLConnectionOpenConnection = url.openConnection();
                    if (this.offset > 0) {
                        uRLConnectionOpenConnection.setRequestProperty("Range", "bytes=" + this.offset + "-" + this.offset + this.requiredLength);
                    }
                    fileInputStream = this.url.openStream();
                } else {
                    Uri uri = this.uri;
                    fileInputStream = uri != null ? this.contentResolver.openInputStream(uri) : null;
                }
            }
        }
        if (this.url == null && fileInputStream != null) {
            long j = this.offset;
            if (j > 0) {
                long jSkip = fileInputStream.skip(j);
                if (jSkip < this.offset) {
                    COSLogger.wNetwork(QCloudHttpClient.HTTP_LOG_TAG, "skip  %d is small than offset %d", Long.valueOf(jSkip), Long.valueOf(this.offset));
                }
            }
        }
        return fileInputStream;
    }

    protected void saveInputStreamToTmpFile(InputStream inputStream, File file) throws Throwable {
        FileOutputStream fileOutputStream;
        int i;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr = new byte[8192];
            long jContentLength = contentLength();
            long j = 0;
            if (jContentLength < 0) {
                jContentLength = Long.MAX_VALUE;
            }
            long j2 = this.offset;
            if (j2 > 0) {
                inputStream.skip(j2);
            }
            while (j < jContentLength && (i = inputStream.read(bArr)) != -1) {
                long j3 = i;
                fileOutputStream.write(bArr, 0, (int) Math.min(j3, jContentLength - j));
                j += j3;
            }
            fileOutputStream.flush();
            OkhttpInternalUtils.closeQuietly(fileOutputStream);
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                OkhttpInternalUtils.closeQuietly(fileOutputStream2);
            }
            throw th;
        }
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws Throwable {
        BufferedSource bufferedSource;
        InputStream inputStream = null;
        bufferedSourceBuffer = null;
        BufferedSource bufferedSourceBuffer = null;
        try {
            InputStream stream = getStream();
            if (stream != null) {
                try {
                    bufferedSourceBuffer = Okio.buffer(Okio.source(stream));
                    long jContentLength = contentLength();
                    CountingSink countingSink = new CountingSink(bufferedSink, jContentLength, this.progressListener);
                    this.countingSink = countingSink;
                    BufferedSink bufferedSinkBuffer = Okio.buffer(countingSink);
                    if (jContentLength > 0) {
                        bufferedSinkBuffer.write(bufferedSourceBuffer, jContentLength);
                    } else {
                        bufferedSinkBuffer.writeAll(bufferedSourceBuffer);
                    }
                    bufferedSinkBuffer.flush();
                } catch (Throwable th) {
                    th = th;
                    bufferedSource = bufferedSourceBuffer;
                    inputStream = stream;
                    if (inputStream != null) {
                        OkhttpInternalUtils.closeQuietly(inputStream);
                    }
                    if (bufferedSource != null) {
                        OkhttpInternalUtils.closeQuietly(bufferedSource);
                    }
                    throw th;
                }
            }
            if (stream != null) {
                OkhttpInternalUtils.closeQuietly(stream);
            }
            if (bufferedSourceBuffer != null) {
                OkhttpInternalUtils.closeQuietly(bufferedSourceBuffer);
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedSource = null;
        }
    }

    @Override // com.tencent.qcloud.core.common.QCloudDigistListener
    public String onGetMd5() throws IOException {
        try {
            try {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    byte[] bArr = this.bytes;
                    if (bArr != null) {
                        messageDigest.update(bArr, (int) this.offset, (int) contentLength());
                        return Base64Utils.encode(messageDigest.digest());
                    }
                    InputStream stream = getStream();
                    byte[] bArr2 = new byte[8192];
                    long jContentLength = contentLength();
                    while (jContentLength > 0) {
                        int i = stream.read(bArr2, 0, ((long) 8192) > jContentLength ? (int) jContentLength : 8192);
                        if (i == -1) {
                            break;
                        }
                        messageDigest.update(bArr2, 0, i);
                        jContentLength -= (long) i;
                    }
                    String strEncode = Base64Utils.encode(messageDigest.digest());
                    if (stream != null) {
                        OkhttpInternalUtils.closeQuietly(stream);
                    }
                    return strEncode;
                } catch (IOException e) {
                    throw e;
                }
            } catch (NoSuchAlgorithmException e2) {
                throw new IOException("unSupport Md5 algorithm", e2);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                OkhttpInternalUtils.closeQuietly(null);
            }
            throw th;
        }
    }

    public void release() {
        File file;
        if (!this.deleteFileWhenComplete || (file = this.file) == null) {
            return;
        }
        file.delete();
    }
}
