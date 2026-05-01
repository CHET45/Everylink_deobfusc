package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudProgressListener;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.util.QCloudHttpUtils;
import java.io.InputStream;

/* JADX INFO: loaded from: classes4.dex */
public class ResponseInputStreamConverter<T> extends ResponseBodyConverter<T> implements ProgressBody {
    private CountingInputStream countingInputStream;
    protected boolean isQuic = false;
    protected QCloudProgressListener progressListener;

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
        this.countingInputStream = new CountingInputStream(httpResponse.byteStream(), jContentLength, this.progressListener);
        return null;
    }

    @Override // com.tencent.qcloud.core.http.ProgressBody
    public long getBytesTransferred() {
        CountingInputStream countingInputStream = this.countingInputStream;
        if (countingInputStream != null) {
            return countingInputStream.getTotalTransferred();
        }
        return 0L;
    }

    public InputStream getInputStream() {
        return this.countingInputStream;
    }

    public long getBytesTotal() {
        return this.countingInputStream.getBytesTotal();
    }
}
