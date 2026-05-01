package com.tencent.cos.xml.transfer;

import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.AbortMultiUploadRequest;
import com.tencent.cos.xml.model.object.CompleteMultiUploadRequest;
import com.tencent.cos.xml.model.object.CompleteMultiUploadResult;
import com.tencent.cos.xml.model.object.InitMultipartUploadRequest;
import com.tencent.cos.xml.model.object.InitMultipartUploadResult;
import com.tencent.cos.xml.model.object.ListPartsRequest;
import com.tencent.cos.xml.model.object.ListPartsResult;
import com.tencent.cos.xml.model.object.ObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;
import com.tencent.cos.xml.model.object.UploadPartRequest;
import com.tencent.cos.xml.model.object.UploadPartResult;
import com.tencent.cos.xml.model.tag.ListParts;
import com.tencent.cos.xml.utils.SharePreferenceUtils;
import com.tencent.qcloud.core.http.HttpTaskMetrics;
import com.tencent.qcloud.core.logger.COSLogger;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class UploadService {
    private static final long SIZE_LIMIT = 2097152;
    private static String TAG = "UploadService";
    private AtomicLong ALREADY_SEND_DATA_LEN;
    private volatile int ERROR_EXIT_FLAG;
    private AtomicInteger UPLOAD_PART_COUNT;
    private String bucket;
    private CompleteMultiUploadRequest completeMultiUploadRequest;
    private String cosPath;
    private CosXmlProgressListener cosXmlProgressListener;
    private CosXmlSimpleService cosXmlService;
    private EncryptionType encryptionType;
    private long endTime;
    private long fileLength;
    private List<String> headers;
    private InitMultipartUploadRequest initMultipartUploadRequest;
    private boolean isNeedMd5;
    private boolean isSupportAccelerate;
    private ListPartsRequest listPartsRequest;
    private Exception mException;
    private byte[] objectSync;
    private OnGetHttpTaskMetrics onGetHttpTaskMetrics;
    private OnSignatureListener onSignatureListener;
    private OnUploadInfoListener onUploadInfoListener;
    private Map<Integer, SlicePartStruct> partStructMap;
    private PutObjectRequest putObjectRequest;
    ResumeData resumeData;
    private SharePreferenceUtils sharePreferenceUtils;
    private long sliceSize;
    private String srcPath;
    private long startTime;
    private String uploadId;
    private Map<UploadPartRequest, Long> uploadPartRequestLongMap;
    private UploadServiceResult uploadServiceResult;

    public enum EncryptionType {
        SSE,
        SSEC,
        SSEKMS,
        NONE
    }

    public interface OnGetHttpTaskMetrics {
        void onGetHttpMetrics(String str, HttpTaskMetrics httpTaskMetrics);
    }

    public interface OnSignatureListener {
        String onGetSign(CosXmlRequest cosXmlRequest);
    }

    public interface OnUploadInfoListener {
        void onInfo(ResumeData resumeData);
    }

    public static class ResumeData {
        public String bucket;
        public String cosPath;
        public String customerKeyForSSEC;
        public String customerKeyIdForSSEKMS;
        public String jsonContentForSSEKMS;
        public long sliceSize;
        public String srcPath;
        public String uploadId;
    }

    public UploadService(CosXmlSimpleService cosXmlSimpleService, ResumeData resumeData) {
        this.sliceSize = 1048576L;
        this.objectSync = new byte[0];
        this.startTime = -1L;
        this.endTime = -1L;
        this.headers = new ArrayList();
        this.isNeedMd5 = false;
        this.encryptionType = EncryptionType.NONE;
        this.isSupportAccelerate = false;
        this.cosXmlService = cosXmlSimpleService;
        init(resumeData);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public UploadService(com.tencent.cos.xml.CosXmlSimpleService r4, java.lang.String r5, java.lang.String r6, java.lang.String r7, long r8, android.content.Context r10) {
        /*
            r3 = this;
            r3.<init>()
            r0 = 1048576(0x100000, double:5.180654E-318)
            r3.sliceSize = r0
            r0 = 0
            byte[] r1 = new byte[r0]
            r3.objectSync = r1
            r1 = -1
            r3.startTime = r1
            r3.endTime = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r3.headers = r1
            r3.isNeedMd5 = r0
            com.tencent.cos.xml.transfer.UploadService$EncryptionType r1 = com.tencent.cos.xml.transfer.UploadService.EncryptionType.NONE
            r3.encryptionType = r1
            r3.isSupportAccelerate = r0
            if (r10 == 0) goto L3b
            android.content.Context r10 = r10.getApplicationContext()
            com.tencent.cos.xml.utils.SharePreferenceUtils r10 = com.tencent.cos.xml.utils.SharePreferenceUtils.instance(r10)
            r3.sharePreferenceUtils = r10
            java.lang.String r10 = r3.getKey(r4, r5, r6, r7, r8)
            if (r10 == 0) goto L3b
            com.tencent.cos.xml.utils.SharePreferenceUtils r0 = r3.sharePreferenceUtils
            java.lang.String r10 = r0.getValue(r10)
            goto L3c
        L3b:
            r10 = 0
        L3c:
            com.tencent.cos.xml.transfer.UploadService$ResumeData r0 = new com.tencent.cos.xml.transfer.UploadService$ResumeData
            r0.<init>()
            r0.bucket = r5
            r0.cosPath = r6
            r0.sliceSize = r8
            r0.srcPath = r7
            r0.uploadId = r10
            r3.cosXmlService = r4
            r3.init(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.cos.xml.transfer.UploadService.<init>(com.tencent.cos.xml.CosXmlSimpleService, java.lang.String, java.lang.String, java.lang.String, long, android.content.Context):void");
    }

    String getKey(CosXmlSimpleService cosXmlSimpleService, String str, String str2, String str3, long j) {
        File file = new File(str3);
        if (!file.exists()) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(cosXmlSimpleService != null ? cosXmlSimpleService.getAppid() : null).append(";").append(str).append(";").append(str2).append(";").append(str3).append(";").append(file.length()).append(";").append(file.lastModified()).append(";").append(j);
        return stringBuffer.toString();
    }

    void clearSharePreference() {
        SharePreferenceUtils sharePreferenceUtils = this.sharePreferenceUtils;
        if (sharePreferenceUtils != null) {
            sharePreferenceUtils.clear(getKey(this.cosXmlService, this.bucket, this.cosPath, this.srcPath, this.sliceSize));
        }
    }

    boolean updateSharePreference(String str) {
        SharePreferenceUtils sharePreferenceUtils = this.sharePreferenceUtils;
        if (sharePreferenceUtils != null) {
            return sharePreferenceUtils.updateValue(getKey(this.cosXmlService, this.bucket, this.cosPath, this.srcPath, this.sliceSize), str);
        }
        return false;
    }

    void init(ResumeData resumeData) {
        this.bucket = resumeData.bucket;
        this.cosPath = resumeData.cosPath;
        this.srcPath = resumeData.srcPath;
        this.sliceSize = resumeData.sliceSize;
        this.uploadId = resumeData.uploadId;
        this.UPLOAD_PART_COUNT = new AtomicInteger(0);
        this.ALREADY_SEND_DATA_LEN = new AtomicLong(0L);
        this.ERROR_EXIT_FLAG = 0;
        this.partStructMap = new LinkedHashMap();
        this.uploadPartRequestLongMap = new LinkedHashMap();
        this.resumeData = resumeData;
    }

    private void checkParameter() throws CosXmlClientException {
        if (this.srcPath != null) {
            File file = new File(this.srcPath);
            if (file.exists()) {
                this.fileLength = file.length();
                return;
            }
        }
        throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "srcPath :" + this.srcPath + " is invalid or is not exist");
    }

    public void setSign(long j, long j2) {
        this.startTime = j;
        this.endTime = j2;
    }

    private void setSignTime(CosXmlRequest cosXmlRequest) {
        if (cosXmlRequest != null) {
            long j = this.startTime;
            if (j > 0) {
                long j2 = this.endTime;
                if (j2 >= j) {
                    cosXmlRequest.setSign(j, j2);
                }
            }
        }
    }

    public void setOnSignatureListener(OnSignatureListener onSignatureListener) {
        this.onSignatureListener = onSignatureListener;
    }

    public void setOnGetHttpTaskMetrics(OnGetHttpTaskMetrics onGetHttpTaskMetrics) {
        this.onGetHttpTaskMetrics = onGetHttpTaskMetrics;
    }

    private void getHttpMetrics(CosXmlRequest cosXmlRequest, final String str) {
        if (this.onGetHttpTaskMetrics != null) {
            cosXmlRequest.attachMetrics(new HttpTaskMetrics() { // from class: com.tencent.cos.xml.transfer.UploadService.1
                @Override // com.tencent.qcloud.core.http.HttpTaskMetrics
                public void onDataReady() {
                    super.onDataReady();
                    UploadService.this.onGetHttpTaskMetrics.onGetHttpMetrics(str, this);
                }
            });
        }
    }

    public void setRequestHeaders(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        this.headers.add(str);
        this.headers.add(str2);
    }

    public void setNeedMd5(boolean z) {
        this.isNeedMd5 = z;
    }

    public void setCOSServerSideEncryptionType(EncryptionType encryptionType) {
        this.encryptionType = encryptionType;
    }

    public void isSupportAccelerate(boolean z) {
        this.isSupportAccelerate = z;
    }

    /* JADX INFO: renamed from: com.tencent.cos.xml.transfer.UploadService$6 */
    static /* synthetic */ class C44576 {

        /* JADX INFO: renamed from: $SwitchMap$com$tencent$cos$xml$transfer$UploadService$EncryptionType */
        static final /* synthetic */ int[] f1850x7f2b125a;

        static {
            int[] iArr = new int[EncryptionType.values().length];
            f1850x7f2b125a = iArr;
            try {
                iArr[EncryptionType.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1850x7f2b125a[EncryptionType.SSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1850x7f2b125a[EncryptionType.SSEC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1850x7f2b125a[EncryptionType.SSEKMS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void setEncryption(CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        if (cosXmlRequest == null) {
            return;
        }
        int i = C44576.f1850x7f2b125a[this.encryptionType.ordinal()];
        if (i == 2) {
            ((ObjectRequest) cosXmlRequest).setCOSServerSideEncryption();
        } else if (i == 3) {
            ((ObjectRequest) cosXmlRequest).setCOSServerSideEncryptionWithCustomerKey(this.resumeData.customerKeyForSSEC);
        } else {
            if (i != 4) {
                return;
            }
            ((ObjectRequest) cosXmlRequest).setCOSServerSideEncryptionWithKMS(this.resumeData.customerKeyIdForSSEKMS, this.resumeData.jsonContentForSSEKMS);
        }
    }

    public void setProgressListener(CosXmlProgressListener cosXmlProgressListener) {
        this.cosXmlProgressListener = cosXmlProgressListener;
    }

    public void setOnUploadInfoListener(OnUploadInfoListener onUploadInfoListener) {
        this.onUploadInfoListener = onUploadInfoListener;
    }

    private void setRequestHeaders(CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        if (cosXmlRequest != null) {
            int size = this.headers.size();
            for (int i = 0; i < size - 2; i += 2) {
                cosXmlRequest.setRequestHeaders(this.headers.get(i), this.headers.get(i + 1), false);
            }
        }
    }

    private void setSupportAccelerate(CosXmlRequest cosXmlRequest) {
        boolean z;
        if (cosXmlRequest == null || !(z = this.isSupportAccelerate)) {
            return;
        }
        cosXmlRequest.isSupportAccelerate(z);
    }

    public UploadServiceResult upload() throws CosXmlServiceException, CosXmlClientException {
        checkParameter();
        if (this.fileLength < 2097152) {
            return putObject(this.bucket, this.cosPath, this.srcPath);
        }
        return multiUploadParts();
    }

    public CosXmlResult resume(ResumeData resumeData) throws CosXmlServiceException, CosXmlClientException {
        init(resumeData);
        return upload();
    }

    public ResumeData pause() {
        this.ERROR_EXIT_FLAG = 2;
        ResumeData resumeData = new ResumeData();
        resumeData.bucket = this.bucket;
        resumeData.cosPath = this.cosPath;
        resumeData.sliceSize = this.sliceSize;
        resumeData.srcPath = this.srcPath;
        resumeData.uploadId = this.uploadId;
        resumeData.customerKeyForSSEC = this.resumeData.customerKeyForSSEC;
        resumeData.customerKeyIdForSSEKMS = this.resumeData.customerKeyIdForSSEKMS;
        resumeData.jsonContentForSSEKMS = this.resumeData.jsonContentForSSEKMS;
        return resumeData;
    }

    public void abort(CosXmlResultListener cosXmlResultListener) {
        this.ERROR_EXIT_FLAG = 3;
        abortMultiUpload(cosXmlResultListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clear() {
        this.putObjectRequest = null;
        this.initMultipartUploadRequest = null;
        this.listPartsRequest = null;
        this.completeMultiUploadRequest = null;
        this.partStructMap.clear();
        this.uploadPartRequestLongMap.clear();
    }

    private UploadServiceResult putObject(String str, String str2, String str3) throws CosXmlServiceException, CosXmlClientException {
        this.UPLOAD_PART_COUNT.set(1);
        PutObjectRequest putObjectRequest = new PutObjectRequest(str, str2, str3);
        this.putObjectRequest = putObjectRequest;
        putObjectRequest.setProgressListener(this.cosXmlProgressListener);
        OnSignatureListener onSignatureListener = this.onSignatureListener;
        if (onSignatureListener != null) {
            PutObjectRequest putObjectRequest2 = this.putObjectRequest;
            putObjectRequest2.setSign(onSignatureListener.onGetSign(putObjectRequest2));
        } else {
            setSignTime(this.putObjectRequest);
        }
        getHttpMetrics(this.putObjectRequest, "PutObjectRequest");
        setRequestHeaders(this.putObjectRequest);
        setSupportAccelerate(this.putObjectRequest);
        setEncryption(this.putObjectRequest);
        this.putObjectRequest.setNeedMD5(this.isNeedMd5);
        this.cosXmlService.putObjectAsync(this.putObjectRequest, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.UploadService.2
            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                synchronized (UploadService.this.objectSync) {
                    PutObjectResult putObjectResult = (PutObjectResult) cosXmlResult;
                    if (UploadService.this.uploadServiceResult == null) {
                        UploadService.this.uploadServiceResult = new UploadServiceResult();
                    }
                    UploadService.this.uploadServiceResult.httpCode = putObjectResult.httpCode;
                    UploadService.this.uploadServiceResult.httpMessage = putObjectResult.httpMessage;
                    UploadService.this.uploadServiceResult.headers = putObjectResult.headers;
                    UploadService.this.uploadServiceResult.eTag = putObjectResult.eTag;
                }
                UploadService.this.UPLOAD_PART_COUNT.decrementAndGet();
            }

            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                synchronized (UploadService.this.objectSync) {
                    if (cosXmlClientException != null) {
                        UploadService.this.mException = cosXmlClientException;
                    } else {
                        UploadService.this.mException = cosXmlServiceException;
                    }
                    UploadService.this.ERROR_EXIT_FLAG = 1;
                }
            }
        });
        while (this.UPLOAD_PART_COUNT.get() > 0 && this.ERROR_EXIT_FLAG == 0) {
        }
        if (this.ERROR_EXIT_FLAG > 0) {
            int i = this.ERROR_EXIT_FLAG;
            if (i == 1) {
                realCancel();
                Exception exc = this.mException;
                if (exc != null) {
                    if (exc instanceof CosXmlClientException) {
                        throw ((CosXmlClientException) exc);
                    }
                    if (exc instanceof CosXmlServiceException) {
                        throw ((CosXmlServiceException) exc);
                    }
                } else {
                    throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), "unknown exception");
                }
            } else {
                if (i == 2) {
                    realCancel();
                    clear();
                    throw new CosXmlClientException(ClientErrorCode.USER_CANCELLED.getCode(), "request is cancelled by manual pause");
                }
                if (i == 3) {
                    throw new CosXmlClientException(ClientErrorCode.USER_CANCELLED.getCode(), "request is cancelled by abort request");
                }
            }
        }
        this.uploadServiceResult.accessUrl = this.cosXmlService.getAccessUrl(this.putObjectRequest);
        return this.uploadServiceResult;
    }

    private UploadServiceResult multiUploadParts() throws CosXmlServiceException, CosXmlClientException {
        initSlicePart();
        if (this.uploadId != null) {
            updateSlicePart(listPart());
        } else {
            this.uploadId = initMultiUpload().initMultipartUpload.uploadId;
        }
        if (this.onUploadInfoListener != null) {
            ResumeData resumeData = new ResumeData();
            resumeData.bucket = this.bucket;
            resumeData.cosPath = this.cosPath;
            resumeData.sliceSize = this.sliceSize;
            resumeData.srcPath = this.srcPath;
            resumeData.uploadId = this.uploadId;
            resumeData.customerKeyForSSEC = this.resumeData.customerKeyForSSEC;
            resumeData.customerKeyIdForSSEKMS = this.resumeData.customerKeyIdForSSEKMS;
            resumeData.jsonContentForSSEKMS = this.resumeData.jsonContentForSSEKMS;
            this.onUploadInfoListener.onInfo(resumeData);
        }
        updateSharePreference(this.uploadId);
        Iterator<Map.Entry<Integer, SlicePartStruct>> it = this.partStructMap.entrySet().iterator();
        while (it.hasNext()) {
            final SlicePartStruct value = it.next().getValue();
            if (!value.isAlreadyUpload) {
                uploadPart(value.partNumber, value.offset, value.sliceSize, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.UploadService.3
                    @Override // com.tencent.cos.xml.listener.CosXmlResultListener
                    public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                        synchronized (UploadService.this.objectSync) {
                            value.eTag = ((UploadPartResult) cosXmlResult).eTag;
                            value.isAlreadyUpload = true;
                        }
                        UploadService.this.UPLOAD_PART_COUNT.decrementAndGet();
                    }

                    @Override // com.tencent.cos.xml.listener.CosXmlResultListener
                    public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                        synchronized (UploadService.this.objectSync) {
                            if (cosXmlClientException != null) {
                                UploadService.this.mException = cosXmlClientException;
                            } else {
                                UploadService.this.mException = cosXmlServiceException;
                            }
                            UploadService.this.ERROR_EXIT_FLAG = 1;
                        }
                    }
                });
            }
        }
        while (this.UPLOAD_PART_COUNT.get() > 0 && this.ERROR_EXIT_FLAG == 0) {
        }
        clearSharePreference();
        if (this.ERROR_EXIT_FLAG > 0) {
            int i = this.ERROR_EXIT_FLAG;
            if (i == 1) {
                realCancel();
                Exception exc = this.mException;
                if (exc != null) {
                    if (exc instanceof CosXmlClientException) {
                        throw ((CosXmlClientException) exc);
                    }
                    if (exc instanceof CosXmlServiceException) {
                        throw ((CosXmlServiceException) exc);
                    }
                } else {
                    throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), "unknown exception");
                }
            } else {
                if (i == 2) {
                    realCancel();
                    clear();
                    throw new CosXmlClientException(ClientErrorCode.USER_CANCELLED.getCode(), "request is cancelled by manual pause");
                }
                if (i == 3) {
                    throw new CosXmlClientException(ClientErrorCode.USER_CANCELLED.getCode(), "request is cancelled by abort request");
                }
            }
        }
        CompleteMultiUploadResult completeMultiUploadResultCompleteMultiUpload = completeMultiUpload();
        if (this.uploadServiceResult == null) {
            this.uploadServiceResult = new UploadServiceResult();
        }
        this.uploadServiceResult.httpCode = completeMultiUploadResultCompleteMultiUpload.httpCode;
        this.uploadServiceResult.httpMessage = completeMultiUploadResultCompleteMultiUpload.httpMessage;
        this.uploadServiceResult.headers = completeMultiUploadResultCompleteMultiUpload.headers;
        this.uploadServiceResult.eTag = completeMultiUploadResultCompleteMultiUpload.completeMultipartUpload.eTag;
        this.uploadServiceResult.accessUrl = this.cosXmlService.getAccessUrl(this.completeMultiUploadRequest);
        return this.uploadServiceResult;
    }

    private InitMultipartUploadResult initMultiUpload() throws CosXmlServiceException, CosXmlClientException {
        InitMultipartUploadRequest initMultipartUploadRequest = new InitMultipartUploadRequest(this.bucket, this.cosPath);
        this.initMultipartUploadRequest = initMultipartUploadRequest;
        OnSignatureListener onSignatureListener = this.onSignatureListener;
        if (onSignatureListener != null) {
            initMultipartUploadRequest.setSign(onSignatureListener.onGetSign(initMultipartUploadRequest));
        } else {
            setSignTime(initMultipartUploadRequest);
        }
        getHttpMetrics(this.initMultipartUploadRequest, "InitMultipartUploadRequest");
        setRequestHeaders(this.initMultipartUploadRequest);
        setSupportAccelerate(this.initMultipartUploadRequest);
        setEncryption(this.initMultipartUploadRequest);
        return this.cosXmlService.initMultipartUpload(this.initMultipartUploadRequest);
    }

    private ListPartsResult listPart() throws CosXmlServiceException, CosXmlClientException {
        ListPartsRequest listPartsRequest = new ListPartsRequest(this.bucket, this.cosPath, this.uploadId);
        this.listPartsRequest = listPartsRequest;
        OnSignatureListener onSignatureListener = this.onSignatureListener;
        if (onSignatureListener != null) {
            listPartsRequest.setSign(onSignatureListener.onGetSign(listPartsRequest));
        } else {
            setSignTime(listPartsRequest);
        }
        getHttpMetrics(this.listPartsRequest, "ListPartsRequest");
        setRequestHeaders(this.listPartsRequest);
        setSupportAccelerate(this.listPartsRequest);
        return this.cosXmlService.listParts(this.listPartsRequest);
    }

    private void uploadPart(int i, long j, long j2, CosXmlResultListener cosXmlResultListener) {
        final UploadPartRequest uploadPartRequest = new UploadPartRequest(this.bucket, this.cosPath, i, this.srcPath, j, j2, this.uploadId);
        this.uploadPartRequestLongMap.put(uploadPartRequest, 0L);
        uploadPartRequest.setNeedMD5(this.isNeedMd5);
        OnSignatureListener onSignatureListener = this.onSignatureListener;
        if (onSignatureListener != null) {
            uploadPartRequest.setSign(onSignatureListener.onGetSign(uploadPartRequest));
        } else {
            setSignTime(uploadPartRequest);
        }
        getHttpMetrics(uploadPartRequest, "UploadPartRequest");
        try {
            setRequestHeaders(uploadPartRequest);
            setSupportAccelerate(uploadPartRequest);
            setEncryption(uploadPartRequest);
            uploadPartRequest.setProgressListener(new CosXmlProgressListener() { // from class: com.tencent.cos.xml.transfer.UploadService.4
                @Override // com.tencent.qcloud.core.common.QCloudProgressListener
                public void onProgress(long j3, long j4) {
                    long jAddAndGet;
                    synchronized (UploadService.this.objectSync) {
                        try {
                            jAddAndGet = UploadService.this.ALREADY_SEND_DATA_LEN.addAndGet(j3 - ((Long) UploadService.this.uploadPartRequestLongMap.get(uploadPartRequest)).longValue());
                            UploadService.this.uploadPartRequestLongMap.put(uploadPartRequest, Long.valueOf(j3));
                        } catch (Exception e) {
                            if (UploadService.this.ERROR_EXIT_FLAG > 0) {
                                COSLogger.dProcess(UploadService.TAG, "upload file has been abort", e);
                            }
                        }
                        if (UploadService.this.cosXmlProgressListener != null) {
                            UploadService.this.cosXmlProgressListener.onProgress(jAddAndGet, UploadService.this.fileLength);
                        }
                    }
                }
            });
            this.cosXmlService.uploadPartAsync(uploadPartRequest, cosXmlResultListener);
        } catch (CosXmlClientException e) {
            cosXmlResultListener.onFail(this.putObjectRequest, e, null);
        }
    }

    private CompleteMultiUploadResult completeMultiUpload() throws CosXmlServiceException, CosXmlClientException {
        this.completeMultiUploadRequest = new CompleteMultiUploadRequest(this.bucket, this.cosPath, this.uploadId, null);
        Iterator<Map.Entry<Integer, SlicePartStruct>> it = this.partStructMap.entrySet().iterator();
        while (it.hasNext()) {
            SlicePartStruct value = it.next().getValue();
            this.completeMultiUploadRequest.setPartNumberAndETag(value.partNumber, value.eTag);
        }
        OnSignatureListener onSignatureListener = this.onSignatureListener;
        if (onSignatureListener != null) {
            CompleteMultiUploadRequest completeMultiUploadRequest = this.completeMultiUploadRequest;
            completeMultiUploadRequest.setSign(onSignatureListener.onGetSign(completeMultiUploadRequest));
        } else {
            setSignTime(this.completeMultiUploadRequest);
        }
        getHttpMetrics(this.completeMultiUploadRequest, "CompleteMultiUploadResult");
        setRequestHeaders(this.completeMultiUploadRequest);
        setSupportAccelerate(this.completeMultiUploadRequest);
        this.completeMultiUploadRequest.setNeedMD5(this.isNeedMd5);
        return this.cosXmlService.completeMultiUpload(this.completeMultiUploadRequest);
    }

    private void abortMultiUpload(final CosXmlResultListener cosXmlResultListener) {
        if (this.uploadId == null) {
            return;
        }
        AbortMultiUploadRequest abortMultiUploadRequest = new AbortMultiUploadRequest(this.bucket, this.cosPath, this.uploadId);
        OnSignatureListener onSignatureListener = this.onSignatureListener;
        if (onSignatureListener != null) {
            abortMultiUploadRequest.setSign(onSignatureListener.onGetSign(abortMultiUploadRequest));
        } else {
            setSignTime(abortMultiUploadRequest);
        }
        getHttpMetrics(abortMultiUploadRequest, "AbortMultiUploadRequest");
        try {
            setRequestHeaders(abortMultiUploadRequest);
            setSupportAccelerate(abortMultiUploadRequest);
            this.cosXmlService.abortMultiUploadAsync(abortMultiUploadRequest, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.UploadService.5
                @Override // com.tencent.cos.xml.listener.CosXmlResultListener
                public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                    cosXmlResultListener.onSuccess(cosXmlRequest, cosXmlResult);
                    UploadService.this.realCancel();
                    UploadService.this.clear();
                }

                @Override // com.tencent.cos.xml.listener.CosXmlResultListener
                public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                    cosXmlResultListener.onFail(cosXmlRequest, cosXmlClientException, cosXmlServiceException);
                    UploadService.this.realCancel();
                    UploadService.this.clear();
                }
            });
        } catch (CosXmlClientException e) {
            cosXmlResultListener.onFail(abortMultiUploadRequest, e, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void realCancel() {
        this.cosXmlService.cancel(this.putObjectRequest);
        this.cosXmlService.cancel(this.initMultipartUploadRequest);
        this.cosXmlService.cancel(this.listPartsRequest);
        this.cosXmlService.cancel(this.completeMultiUploadRequest);
        Map<UploadPartRequest, Long> map = this.uploadPartRequestLongMap;
        if (map != null) {
            Iterator<UploadPartRequest> it = map.keySet().iterator();
            while (it.hasNext()) {
                this.cosXmlService.cancel(it.next());
            }
        }
    }

    private void initSlicePart() throws CosXmlClientException {
        if (this.srcPath != null) {
            File file = new File(this.srcPath);
            if (!file.exists()) {
                throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "upload file does not exist");
            }
            this.fileLength = file.length();
        }
        long j = this.fileLength;
        if (j > 0) {
            long j2 = this.sliceSize;
            if (j2 > 0) {
                int i = (int) (j / j2);
                int i2 = 1;
                while (true) {
                    if (i2 < i) {
                        SlicePartStruct slicePartStruct = new SlicePartStruct();
                        slicePartStruct.isAlreadyUpload = false;
                        slicePartStruct.partNumber = i2;
                        slicePartStruct.offset = ((long) (i2 - 1)) * this.sliceSize;
                        slicePartStruct.sliceSize = this.sliceSize;
                        this.partStructMap.put(Integer.valueOf(i2), slicePartStruct);
                        i2++;
                    } else {
                        SlicePartStruct slicePartStruct2 = new SlicePartStruct();
                        slicePartStruct2.isAlreadyUpload = false;
                        slicePartStruct2.partNumber = i2;
                        slicePartStruct2.offset = ((long) (i2 - 1)) * this.sliceSize;
                        slicePartStruct2.sliceSize = this.fileLength - slicePartStruct2.offset;
                        this.partStructMap.put(Integer.valueOf(i2), slicePartStruct2);
                        this.UPLOAD_PART_COUNT.set(i2);
                        return;
                    }
                }
            }
        }
        throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "file size or slice size less than 0");
    }

    private void updateSlicePart(ListPartsResult listPartsResult) {
        List<ListParts.Part> list;
        if (listPartsResult == null || listPartsResult.listParts == null || (list = listPartsResult.listParts.parts) == null) {
            return;
        }
        for (ListParts.Part part : list) {
            if (this.partStructMap.containsKey(Integer.valueOf(part.partNumber))) {
                SlicePartStruct slicePartStruct = this.partStructMap.get(Integer.valueOf(part.partNumber));
                slicePartStruct.isAlreadyUpload = true;
                slicePartStruct.eTag = part.eTag;
                this.UPLOAD_PART_COUNT.decrementAndGet();
                this.ALREADY_SEND_DATA_LEN.addAndGet(Long.parseLong(part.size));
            }
        }
    }

    private static class SlicePartStruct {
        public String eTag;
        public boolean isAlreadyUpload;
        public long offset;
        public int partNumber;
        public long sliceSize;

        private SlicePartStruct() {
        }
    }

    public static class UploadServiceResult extends CosXmlResult {
        public String eTag;

        @Override // com.tencent.cos.xml.model.CosXmlResult
        public String printResult() {
            return super.printResult() + "\neTag:" + this.eTag + "\naccessUrl:" + this.accessUrl;
        }
    }

    void setUploadId(String str) {
        this.uploadId = str;
    }
}
