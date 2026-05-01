package com.tencent.cos.xml.transfer;

import android.text.TextUtils;
import android.util.SparseArray;
import bolts.Task;
import bolts.TaskCompletionSource;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.crypto.COSDirect;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.UploadPartRequest;
import com.tencent.cos.xml.model.object.UploadPartResult;
import com.tencent.cos.xml.transfer.COSUploadTask;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: compiled from: UploadPartsTask.java */
/* JADX INFO: loaded from: classes4.dex */
class ParallelUploadPartsTask extends BaseUploadPartsTask {
    private AtomicLong mTotalProgress;
    private final Set<UploadPartRequest> runningRequestSet;
    private TaskCompletionSource<Set<COSUploadTask.UploadPart>> tcs;
    private SparseArray<Long> uploadPartProgress;

    ParallelUploadPartsTask(COSDirect cOSDirect, PutObjectRequest putObjectRequest, long j, long j2, int i, String str) {
        super(cOSDirect, putObjectRequest, j, j2, i, str);
        this.runningRequestSet = Collections.synchronizedSet(new HashSet());
        this.tcs = new TaskCompletionSource<>();
        this.uploadPartProgress = new SparseArray<>();
        this.mTotalProgress = new AtomicLong(0L);
    }

    @Override // com.tencent.cos.xml.transfer.BaseUploadPartsTask
    public Set<COSUploadTask.UploadPart> upload() throws Exception {
        ParallelUploadPartsTask parallelUploadPartsTask = this;
        Task<Set<COSUploadTask.UploadPart>> task = parallelUploadPartsTask.tcs.getTask();
        final int iCalculatePartNumber = parallelUploadPartsTask.calculatePartNumber(parallelUploadPartsTask.mSize, parallelUploadPartsTask.mMaxPartSize);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        int i = 0;
        while (i < iCalculatePartNumber) {
            final int i2 = parallelUploadPartsTask.mStartNumber + i;
            final long j = parallelUploadPartsTask.mOffset + (((long) i) * parallelUploadPartsTask.mMaxPartSize);
            final long jMin = Math.min(parallelUploadPartsTask.mMaxPartSize, (parallelUploadPartsTask.mOffset + parallelUploadPartsTask.mSize) - j);
            final UploadPartRequest uploadRequest = getUploadRequest(i2, j, jMin);
            uploadRequest.setProgressListener(new CosXmlProgressListener() { // from class: com.tencent.cos.xml.transfer.ParallelUploadPartsTask.1
                @Override // com.tencent.qcloud.core.common.QCloudProgressListener
                public void onProgress(long j2, long j3) {
                    ParallelUploadPartsTask.this.updateProgress(uploadRequest, j2);
                }
            });
            synchronized (parallelUploadPartsTask.runningRequestSet) {
                parallelUploadPartsTask.runningRequestSet.add(uploadRequest);
            }
            final AtomicInteger atomicInteger2 = atomicInteger;
            parallelUploadPartsTask.mCosDirect.uploadPartAsync(uploadRequest, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.ParallelUploadPartsTask.2
                @Override // com.tencent.cos.xml.listener.CosXmlResultListener
                public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                    synchronized (ParallelUploadPartsTask.this.runningRequestSet) {
                        ParallelUploadPartsTask.this.runningRequestSet.remove(uploadRequest);
                    }
                    UploadPartResult uploadPartResult = (UploadPartResult) cosXmlResult;
                    String str = uploadPartResult.eTag;
                    COSTransferTask.loggerInfo(COSUploadTask.TAG, ParallelUploadPartsTask.this.taskId, "upload part %d, etag=%s", Integer.valueOf(i2), str);
                    if (TextUtils.isEmpty(str)) {
                        ParallelUploadPartsTask.this.tcs.trySetError(new CosXmlClientException(ClientErrorCode.ETAG_NOT_FOUND));
                        ParallelUploadPartsTask.this.cancelAllUploadingRequests(false);
                    } else {
                        ParallelUploadPartsTask.this.uploadParts.add(new COSUploadTask.UploadPart(uploadPartResult.eTag, i2, j, jMin));
                        if (atomicInteger2.addAndGet(1) >= iCalculatePartNumber) {
                            ParallelUploadPartsTask.this.tcs.trySetResult(ParallelUploadPartsTask.this.uploadParts);
                        }
                    }
                }

                @Override // com.tencent.cos.xml.listener.CosXmlResultListener
                public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                    if (cosXmlClientException != null) {
                        ParallelUploadPartsTask.this.tcs.trySetError(cosXmlClientException);
                    } else if (cosXmlServiceException != null) {
                        ParallelUploadPartsTask.this.tcs.trySetError(cosXmlServiceException);
                    } else {
                        ParallelUploadPartsTask.this.tcs.trySetError(new CosXmlClientException(ClientErrorCode.UNKNOWN));
                    }
                    ParallelUploadPartsTask.this.cancelAllUploadingRequests(false);
                }
            });
            i++;
            parallelUploadPartsTask = this;
            atomicInteger = atomicInteger;
        }
        task.waitForCompletion();
        if (task.isFaulted()) {
            throw task.getError();
        }
        if (task.isCancelled()) {
            throw new CosXmlClientException(ClientErrorCode.USER_CANCELLED);
        }
        return task.getResult();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void updateProgress(UploadPartRequest uploadPartRequest, long j) {
        int partNumber = uploadPartRequest.getPartNumber();
        long jLongValue = j - this.uploadPartProgress.get(partNumber, 0L).longValue();
        this.uploadPartProgress.put(partNumber, Long.valueOf(j));
        notifyProgressChange(this.mOffset + this.mTotalProgress.addAndGet(jLongValue), this.mOffset + this.mSize);
    }

    private int calculatePartNumber(long j, long j2) {
        int i = (int) (j / j2);
        return j % j2 != 0 ? i + 1 : i;
    }

    @Override // com.tencent.cos.xml.transfer.BaseUploadPartsTask
    public void cancel() {
        this.tcs.trySetCancelled();
        cancelAllUploadingRequests(false);
    }

    @Override // com.tencent.cos.xml.transfer.BaseUploadPartsTask
    public void cancel(boolean z) {
        this.tcs.trySetCancelled();
        cancelAllUploadingRequests(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAllUploadingRequests(boolean z) {
        synchronized (this.runningRequestSet) {
            Iterator<UploadPartRequest> it = this.runningRequestSet.iterator();
            while (it.hasNext()) {
                this.mCosDirect.cancel(it.next(), z);
            }
        }
    }
}
