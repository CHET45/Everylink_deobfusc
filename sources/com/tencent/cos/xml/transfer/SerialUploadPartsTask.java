package com.tencent.cos.xml.transfer;

import android.text.TextUtils;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.crypto.COSDirect;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.UploadPartRequest;
import com.tencent.cos.xml.transfer.COSUploadTask;
import com.tencent.qcloud.core.http.HttpTaskMetrics;
import java.util.Set;

/* JADX INFO: compiled from: UploadPartsTask.java */
/* JADX INFO: loaded from: classes4.dex */
class SerialUploadPartsTask extends BaseUploadPartsTask {
    private UploadPartRequest currentUploadPartRequest;
    private volatile HttpTaskMetrics httpTaskMetrics;
    private int mPartNumber;
    private long mStartPointer;

    public SerialUploadPartsTask(COSDirect cOSDirect, PutObjectRequest putObjectRequest, long j, long j2, int i, String str) {
        super(cOSDirect, putObjectRequest, j, j2, i, str);
    }

    @Override // com.tencent.cos.xml.transfer.BaseUploadPartsTask
    public Set<COSUploadTask.UploadPart> upload() throws CosXmlServiceException, CosXmlClientException {
        this.mStartPointer = this.mOffset;
        this.mPartNumber = this.mStartNumber;
        while (this.mStartPointer < this.mOffset + this.mSize) {
            long jMin = Math.min(this.mMaxPartSize, (this.mOffset + this.mSize) - this.mStartPointer);
            UploadPartRequest uploadRequest = getUploadRequest(this.mPartNumber, this.mStartPointer, jMin);
            this.currentUploadPartRequest = uploadRequest;
            uploadRequest.setProgressListener(new CosXmlProgressListener() { // from class: com.tencent.cos.xml.transfer.SerialUploadPartsTask.1
                @Override // com.tencent.qcloud.core.common.QCloudProgressListener
                public void onProgress(long j, long j2) {
                    SerialUploadPartsTask serialUploadPartsTask = SerialUploadPartsTask.this;
                    serialUploadPartsTask.notifyProgressChange(serialUploadPartsTask.mStartPointer + j, SerialUploadPartsTask.this.mOffset + SerialUploadPartsTask.this.mSize);
                }
            });
            String str = this.mCosDirect.uploadPart(this.currentUploadPartRequest).eTag;
            COSTransferTask.loggerInfo(COSUploadTask.TAG, this.taskId, "upload part %d, etag=%s", Integer.valueOf(this.mPartNumber), str);
            if (TextUtils.isEmpty(str)) {
                throw new CosXmlClientException(ClientErrorCode.ETAG_NOT_FOUND);
            }
            COSUploadTask.UploadPart uploadPart = new COSUploadTask.UploadPart(str, this.mPartNumber, this.mStartPointer, jMin);
            this.mStartPointer += jMin;
            this.mPartNumber++;
            this.uploadParts.add(uploadPart);
        }
        return this.uploadParts;
    }

    @Override // com.tencent.cos.xml.transfer.BaseUploadPartsTask
    public void cancel() {
        if (this.currentUploadPartRequest != null) {
            this.mCosDirect.cancel(this.currentUploadPartRequest);
        }
    }

    @Override // com.tencent.cos.xml.transfer.BaseUploadPartsTask
    public void cancel(boolean z) {
        if (this.currentUploadPartRequest != null) {
            this.mCosDirect.cancel(this.currentUploadPartRequest, z);
        }
    }
}
