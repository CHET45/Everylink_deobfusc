package com.tencent.cos.xml.transfer;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.tencent.cos.xml.CosTrackService;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.AbortMultiUploadRequest;
import com.tencent.cos.xml.model.object.CompleteMultiUploadRequest;
import com.tencent.cos.xml.model.object.CompleteMultiUploadResult;
import com.tencent.cos.xml.model.object.HeadObjectRequest;
import com.tencent.cos.xml.model.object.HeadObjectResult;
import com.tencent.cos.xml.model.object.InitMultipartUploadRequest;
import com.tencent.cos.xml.model.object.InitMultipartUploadResult;
import com.tencent.cos.xml.model.object.ListPartsRequest;
import com.tencent.cos.xml.model.object.ListPartsResult;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;
import com.tencent.cos.xml.model.object.UploadPartRequest;
import com.tencent.cos.xml.model.object.UploadPartResult;
import com.tencent.cos.xml.model.tag.CallbackResult;
import com.tencent.cos.xml.model.tag.InitiateMultipartUpload;
import com.tencent.cos.xml.model.tag.ListParts;
import com.tencent.cos.xml.model.tag.UrlUploadPolicy;
import com.tencent.cos.xml.model.tag.pic.PicUploadResult;
import com.tencent.cos.xml.utils.CloseUtil;
import com.tencent.cos.xml.utils.DigestUtils;
import com.tencent.qcloud.core.common.QCloudTaskStateListener;
import com.tencent.qcloud.core.http.HttpTaskMetrics;
import com.tencent.qcloud.core.logger.COSLogger;
import com.tencent.qcloud.core.util.ContextHolder;
import com.tencent.qcloud.core.util.QCloudStringUtils;
import com.tencent.qcloud.core.util.QCloudUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes4.dex */
public final class COSXMLUploadTask extends COSXMLTask {
    private static Executor executor = Executors.newSingleThreadExecutor();
    private AtomicLong ALREADY_SEND_DATA_LEN;
    private Object SYNC_UPLOAD_PART;
    private final String TAG;
    private AtomicInteger UPLOAD_PART_COUNT;
    private byte[] bytes;
    private String clientTraceId;
    private CompleteMultiUploadRequest completeMultiUploadRequest;
    private long fileLength;
    boolean forceSimpleUpload;
    private HeadObjectRequest headObjectRequest;
    HttpTaskMetrics httpTaskMetrics;
    private InitMultipartUploadRequest initMultipartUploadRequest;
    private InputStream inputStream;
    private boolean isSliceUpload;
    private ListPartsRequest listPartsRequest;
    protected long multiUploadSizeDivision;
    private MultiUploadsStateListener multiUploadsStateListenerHandler;
    private Map<Integer, SlicePartStruct> partStructMap;
    boolean priorityLow;
    private PutObjectRequest putObjectRequest;
    private AtomicBoolean sendingCompleteRequest;
    private long simpleAlreadySendDataLen;
    protected long sliceSize;
    String srcPath;
    private long startTime;
    private String uploadId;
    private Map<UploadPartRequest, Long> uploadPartRequestLongMap;
    private Uri uri;
    private URL url;
    private UrlUploadPolicy urlUploadPolicy;
    private WeightStrategy weightStrategy;

    private interface MultiUploadsStateListener {
        void onCompleted(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult);

        void onFailed(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException);

        void onInit();

        void onListParts();

        void onUploadParts();
    }

    private COSXMLUploadTask(CosXmlSimpleService cosXmlSimpleService, String str, String str2, String str3) {
        this.TAG = "UploadTask";
        this.isSliceUpload = false;
        this.SYNC_UPLOAD_PART = new Object();
        this.startTime = 0L;
        this.simpleAlreadySendDataLen = 0L;
        this.priorityLow = false;
        this.sendingCompleteRequest = new AtomicBoolean(false);
        this.weightStrategy = new WeightStrategy();
        this.multiUploadsStateListenerHandler = new MultiUploadsStateListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.1
            @Override // com.tencent.cos.xml.transfer.COSXMLUploadTask.MultiUploadsStateListener
            public void onInit() {
                COSXMLUploadTask cOSXMLUploadTask = COSXMLUploadTask.this;
                cOSXMLUploadTask.multiUploadPart(cOSXMLUploadTask.cosXmlService);
            }

            @Override // com.tencent.cos.xml.transfer.COSXMLUploadTask.MultiUploadsStateListener
            public void onListParts() {
                COSXMLUploadTask cOSXMLUploadTask = COSXMLUploadTask.this;
                cOSXMLUploadTask.multiUploadPart(cOSXMLUploadTask.cosXmlService);
            }

            @Override // com.tencent.cos.xml.transfer.COSXMLUploadTask.MultiUploadsStateListener
            public void onUploadParts() {
                COSXMLUploadTask cOSXMLUploadTask = COSXMLUploadTask.this;
                cOSXMLUploadTask.completeMultiUpload(cOSXMLUploadTask.cosXmlService);
            }

            @Override // com.tencent.cos.xml.transfer.COSXMLUploadTask.MultiUploadsStateListener
            public void onCompleted(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                COSXMLUploadTask.this.updateState(TransferState.COMPLETED, null, cosXmlResult, false);
            }

            @Override // com.tencent.cos.xml.transfer.COSXMLUploadTask.MultiUploadsStateListener
            public void onFailed(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                Exception exc = cosXmlClientException == null ? cosXmlServiceException : cosXmlClientException;
                COSXMLUploadTask.this.reportException(cosXmlRequest, cosXmlClientException, cosXmlServiceException);
                COSXMLUploadTask.this.updateState(TransferState.FAILED, exc, null, false);
            }
        };
        this.cosXmlService = cosXmlSimpleService;
        this.region = str;
        this.bucket = str2;
        this.cosPath = str3;
        this.httpTaskMetrics = new HttpTaskMetrics();
    }

    COSXMLUploadTask(CosXmlSimpleService cosXmlSimpleService, String str, String str2, String str3, String str4, String str5) {
        this(cosXmlSimpleService, str, str2, str3);
        this.srcPath = str4;
        this.uploadId = str5;
    }

    COSXMLUploadTask(CosXmlSimpleService cosXmlSimpleService, String str, String str2, String str3, Uri uri, String str4) {
        this(cosXmlSimpleService, str, str2, str3);
        this.uri = uri;
        this.uploadId = str4;
    }

    COSXMLUploadTask(CosXmlSimpleService cosXmlSimpleService, String str, String str2, String str3, URL url, UrlUploadPolicy urlUploadPolicy, String str4) {
        this(cosXmlSimpleService, str, str2, str3);
        this.url = url;
        this.urlUploadPolicy = urlUploadPolicy;
        this.uploadId = str4;
    }

    COSXMLUploadTask(CosXmlSimpleService cosXmlSimpleService, String str, String str2, String str3, URL url, String str4) {
        this(cosXmlSimpleService, str, str2, str3);
        this.url = url;
        this.uploadId = str4;
    }

    COSXMLUploadTask(CosXmlSimpleService cosXmlSimpleService, String str, String str2, String str3, byte[] bArr) {
        this(cosXmlSimpleService, str, str2, str3);
        this.bytes = bArr;
    }

    COSXMLUploadTask(CosXmlSimpleService cosXmlSimpleService, String str, String str2, String str3, InputStream inputStream) {
        this(cosXmlSimpleService, str, str2, str3);
        this.inputStream = inputStream;
    }

    COSXMLUploadTask(CosXmlSimpleService cosXmlSimpleService, PutObjectRequest putObjectRequest, String str) {
        this(cosXmlSimpleService, putObjectRequest.getRegion(), putObjectRequest.getBucket(), putObjectRequest.getPath(cosXmlSimpleService.getConfig()));
        this.uri = putObjectRequest.getUri();
        this.url = putObjectRequest.getUrl();
        this.urlUploadPolicy = putObjectRequest.getUrlUploadPolicy();
        this.srcPath = putObjectRequest.getSrcPath();
        this.bytes = putObjectRequest.getData();
        this.inputStream = putObjectRequest.getInputStream();
        this.queries = putObjectRequest.getQueryString();
        this.headers = putObjectRequest.getRequestHeaders();
        this.noSignHeaders = putObjectRequest.getNoSignHeaders();
        this.isNeedMd5 = putObjectRequest.isNeedMD5();
        this.networkType = putObjectRequest.getNetworkType();
        this.host = putObjectRequest.getHost();
        this.credentialProvider = putObjectRequest.getCredentialProvider();
        this.uploadId = str;
        this.priorityLow = putObjectRequest.isPriorityLow();
    }

    protected boolean checkParameter() {
        UrlUploadPolicy urlUploadPolicy;
        Context appContext;
        if (this.bytes == null && this.inputStream == null && this.srcPath == null && this.uri == null && this.url == null) {
            if (this.IS_EXIT.get()) {
                return false;
            }
            this.IS_EXIT.set(true);
            this.multiUploadsStateListenerHandler.onFailed(new PutObjectRequest(this.bucket, this.cosPath, ""), new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "source is is invalid: null"), null);
            return false;
        }
        if (this.srcPath != null) {
            File file = new File(this.srcPath);
            if (!file.exists() || file.isDirectory() || !file.canRead()) {
                if (this.IS_EXIT.get()) {
                    return false;
                }
                this.IS_EXIT.set(true);
                this.multiUploadsStateListenerHandler.onFailed(new PutObjectRequest(this.bucket, this.cosPath, this.srcPath), new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "srcPath is is invalid: " + this.srcPath), null);
                return false;
            }
            this.fileLength = file.length();
        }
        if (this.uri != null && (appContext = ContextHolder.getAppContext()) != null) {
            this.fileLength = QCloudUtils.getUriContentLength(this.uri, appContext.getContentResolver());
        }
        if (this.url != null && (urlUploadPolicy = this.urlUploadPolicy) != null) {
            if (urlUploadPolicy.getDownloadType() != UrlUploadPolicy.Type.NOTSUPPORT) {
                this.fileLength = this.urlUploadPolicy.getFileLength();
            } else {
                if (this.IS_EXIT.get()) {
                    return false;
                }
                monitor.sendStateMessage(this, TransferState.FAILED, new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "url not support download"), null, 1);
                this.IS_EXIT.set(true);
                return false;
            }
        }
        return true;
    }

    protected void upload() {
        if (checkParameter()) {
            this.startTime = System.nanoTime();
            this.clientTraceId = UUID.randomUUID().toString();
            startUpload();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchProgressChange(long j, long j2) {
        if (this.cosXmlProgressListener != null) {
            this.cosXmlProgressListener.onProgress(j, j2);
        }
        if (this.internalProgressListener != null) {
            this.internalProgressListener.onProgress(j, j2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchInitMultipleUpload(InitiateMultipartUpload initiateMultipartUpload) {
        if (this.initMultipleUploadListener != null) {
            this.initMultipleUploadListener.onSuccess(initiateMultipartUpload);
        }
        if (this.internalInitMultipleUploadListener != null) {
            this.internalInitMultipleUploadListener.onSuccess(initiateMultipartUpload);
        }
    }

    private void simpleUpload(CosXmlSimpleService cosXmlSimpleService) {
        if (this.bytes != null) {
            this.putObjectRequest = new PutObjectRequest(this.bucket, this.cosPath, this.bytes);
        } else if (this.inputStream != null) {
            this.putObjectRequest = new PutObjectRequest(this.bucket, this.cosPath, this.inputStream);
        } else if (this.uri != null) {
            this.putObjectRequest = new PutObjectRequest(this.bucket, this.cosPath, this.uri);
        } else if (this.url != null) {
            this.putObjectRequest = new PutObjectRequest(this.bucket, this.cosPath, this.url);
        } else {
            this.putObjectRequest = new PutObjectRequest(this.bucket, this.cosPath, this.srcPath);
        }
        this.putObjectRequest.setRegion(this.region);
        if (this.url != null) {
            this.putObjectRequest.setNeedMD5(false);
        } else {
            this.putObjectRequest.setNeedMD5(this.isNeedMd5);
        }
        this.putObjectRequest.setRequestHeaders(this.headers);
        this.putObjectRequest.addNoSignHeader(this.noSignHeaders);
        this.putObjectRequest.setNetworkType(this.networkType);
        this.putObjectRequest.setHost(this.host);
        this.putObjectRequest.setCredentialProvider(this.credentialProvider);
        if (this.onSignatureListener != null) {
            this.putObjectRequest.setSign(this.onSignatureListener.onGetSign(this.putObjectRequest));
        }
        getHttpMetrics(this.putObjectRequest, "PutObjectRequest");
        this.putObjectRequest.setTaskStateListener(new QCloudTaskStateListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.2
            @Override // com.tencent.qcloud.core.common.QCloudTaskStateListener
            public void onStateChanged(String str, int i) {
                if (COSXMLUploadTask.this.IS_EXIT.get()) {
                    return;
                }
                if (i == 2 || i == 3) {
                    COSXMLUploadTask.this.onUpdateInProgress();
                }
            }
        });
        this.putObjectRequest.setProgressListener(new CosXmlProgressListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.3
            @Override // com.tencent.qcloud.core.common.QCloudProgressListener
            public void onProgress(long j, long j2) {
                COSXMLUploadTask.this.simpleAlreadySendDataLen = j;
                COSXMLUploadTask.this.dispatchProgressChange(j, j2);
            }
        });
        if (this.priorityLow) {
            this.putObjectRequest.setPriorityLow();
        }
        this.putObjectRequest.setClientTraceId(this.clientTraceId);
        cosXmlSimpleService.internalPutObjectAsync(this.putObjectRequest, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.4
            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                if (cosXmlRequest == COSXMLUploadTask.this.putObjectRequest && !COSXMLUploadTask.this.IS_EXIT.get()) {
                    COSXMLUploadTask.this.IS_EXIT.set(true);
                    CosTrackService.getInstance().reportUploadTaskSuccess(cosXmlRequest, COSXMLUploadTask.this.getCosXmlServiceConfigTrackParams());
                    COSXMLUploadTask.this.updateState(TransferState.COMPLETED, null, cosXmlResult, false);
                }
            }

            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                if (cosXmlRequest == COSXMLUploadTask.this.putObjectRequest && !COSXMLUploadTask.this.IS_EXIT.get()) {
                    COSXMLUploadTask.this.IS_EXIT.set(true);
                    COSXMLUploadTask.this.multiUploadsStateListenerHandler.onFailed(cosXmlRequest, cosXmlClientException, cosXmlServiceException);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportException(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
        if (cosXmlRequest == null) {
            cosXmlRequest = buildCOSXMLTaskRequest();
        }
        if (cosXmlClientException != null) {
            CosTrackService.getInstance().reportUploadTaskClientException(cosXmlRequest, cosXmlClientException, getCosXmlServiceConfigTrackParams());
        }
        if (cosXmlServiceException != null) {
            CosTrackService.getInstance().reportUploadTaskServiceException(cosXmlRequest, cosXmlServiceException, getCosXmlServiceConfigTrackParams());
        }
    }

    private void multiUpload(CosXmlSimpleService cosXmlSimpleService) {
        initSlicePart(0L, this.fileLength, 1);
        if (!TextUtils.isEmpty(this.uploadId)) {
            listMultiUpload(cosXmlSimpleService);
        } else {
            initMultiUpload(cosXmlSimpleService);
        }
    }

    private void initMultiUpload(CosXmlSimpleService cosXmlSimpleService) {
        String mimeTypeFromExtension;
        InitMultipartUploadRequest initMultipartUploadRequest = new InitMultipartUploadRequest(this.bucket, this.cosPath);
        this.initMultipartUploadRequest = initMultipartUploadRequest;
        initMultipartUploadRequest.setRegion(this.region);
        this.initMultipartUploadRequest.setRequestHeaders(this.headers);
        this.initMultipartUploadRequest.addNoSignHeader(this.noSignHeaders);
        this.initMultipartUploadRequest.setNetworkType(this.networkType);
        this.initMultipartUploadRequest.setHost(this.host);
        this.initMultipartUploadRequest.setCredentialProvider(this.credentialProvider);
        if (getContentTypeByHeaders(this.headers) == null) {
            URL url = this.url;
            if (url != null) {
                mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url.toString()));
            } else {
                String str = this.srcPath;
                if (str != null) {
                    mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(QCloudStringUtils.getExtension(str));
                } else {
                    mimeTypeFromExtension = null;
                }
            }
            if (mimeTypeFromExtension != null) {
                try {
                    this.initMultipartUploadRequest.setRequestHeaders("Content-Type", mimeTypeFromExtension, false);
                } catch (CosXmlClientException unused) {
                }
            }
        }
        if (this.onSignatureListener != null) {
            this.initMultipartUploadRequest.setSign(this.onSignatureListener.onGetSign(this.initMultipartUploadRequest));
        }
        this.initMultipartUploadRequest.setClientTraceId(this.clientTraceId);
        this.httpTaskMetrics.setDomainName(this.initMultipartUploadRequest.getRequestHost(cosXmlSimpleService.getConfig()));
        getHttpMetrics(this.initMultipartUploadRequest, "InitMultipartUploadRequest");
        cosXmlSimpleService.initMultipartUploadAsync(this.initMultipartUploadRequest, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.5
            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                if (cosXmlRequest == COSXMLUploadTask.this.initMultipartUploadRequest && !COSXMLUploadTask.this.IS_EXIT.get()) {
                    COSXMLUploadTask.this.onUpdateInProgress();
                    InitiateMultipartUpload initiateMultipartUpload = ((InitMultipartUploadResult) cosXmlResult).initMultipartUpload;
                    COSXMLUploadTask.this.uploadId = initiateMultipartUpload.uploadId;
                    COSXMLUploadTask.this.multiUploadsStateListenerHandler.onInit();
                    COSXMLUploadTask.this.dispatchInitMultipleUpload(initiateMultipartUpload);
                }
            }

            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                if (cosXmlRequest == COSXMLUploadTask.this.initMultipartUploadRequest && !COSXMLUploadTask.this.IS_EXIT.get()) {
                    COSXMLUploadTask.this.IS_EXIT.set(true);
                    COSXMLUploadTask.this.multiUploadsStateListenerHandler.onFailed(cosXmlRequest, cosXmlClientException, cosXmlServiceException);
                }
            }
        });
    }

    public void listAllPartsAsync(CosXmlSimpleService cosXmlSimpleService, CosXmlResultListener cosXmlResultListener) {
        new RunnableC44426(new AtomicReference(null), cosXmlSimpleService, new ArrayList(), cosXmlResultListener).run();
    }

    /* JADX INFO: renamed from: com.tencent.cos.xml.transfer.COSXMLUploadTask$6 */
    class RunnableC44426 implements Runnable {
        final /* synthetic */ ArrayList val$allParts;
        final /* synthetic */ CosXmlResultListener val$cosXmlResultListener;
        final /* synthetic */ CosXmlSimpleService val$cosXmlService;
        final /* synthetic */ AtomicReference val$nextPartNumberMarker;

        RunnableC44426(AtomicReference atomicReference, CosXmlSimpleService cosXmlSimpleService, ArrayList arrayList, CosXmlResultListener cosXmlResultListener) {
            this.val$nextPartNumberMarker = atomicReference;
            this.val$cosXmlService = cosXmlSimpleService;
            this.val$allParts = arrayList;
            this.val$cosXmlResultListener = cosXmlResultListener;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.val$nextPartNumberMarker.get() != null && !((String) this.val$nextPartNumberMarker.get()).isEmpty()) {
                COSXMLUploadTask.this.listPartsRequest.setPartNumberMarker((String) this.val$nextPartNumberMarker.get());
            }
            this.val$cosXmlService.listPartsAsync(COSXMLUploadTask.this.listPartsRequest, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.6.1
                @Override // com.tencent.cos.xml.listener.CosXmlResultListener
                public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                    ListPartsResult listPartsResult = (ListPartsResult) cosXmlResult;
                    if (listPartsResult.listParts.parts != null && listPartsResult.listParts.parts.size() > 0) {
                        RunnableC44426.this.val$allParts.addAll(listPartsResult.listParts.parts);
                    }
                    RunnableC44426.this.val$nextPartNumberMarker.set(listPartsResult.listParts.nextPartNumberMarker);
                    if (RunnableC44426.this.val$nextPartNumberMarker.get() != null && !((String) RunnableC44426.this.val$nextPartNumberMarker.get()).isEmpty()) {
                        RunnableC44426.this.run();
                        return;
                    }
                    listPartsResult.listParts.parts = RunnableC44426.this.val$allParts;
                    RunnableC44426.this.val$cosXmlResultListener.onSuccess(cosXmlRequest, listPartsResult);
                }

                @Override // com.tencent.cos.xml.listener.CosXmlResultListener
                public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                    RunnableC44426.this.val$cosXmlResultListener.onFail(cosXmlRequest, cosXmlClientException, cosXmlServiceException);
                }
            });
        }
    }

    private void listMultiUpload(CosXmlSimpleService cosXmlSimpleService) {
        ListPartsRequest listPartsRequest = new ListPartsRequest(this.bucket, this.cosPath, this.uploadId);
        this.listPartsRequest = listPartsRequest;
        listPartsRequest.setRegion(this.region);
        this.listPartsRequest.setRequestHeaders(this.headers);
        this.listPartsRequest.addNoSignHeader(this.noSignHeaders);
        this.listPartsRequest.setNetworkType(this.networkType);
        this.listPartsRequest.setHost(this.host);
        this.listPartsRequest.setCredentialProvider(this.credentialProvider);
        if (this.onSignatureListener != null) {
            this.listPartsRequest.setSign(this.onSignatureListener.onGetSign(this.listPartsRequest));
        }
        this.httpTaskMetrics.setDomainName(this.listPartsRequest.getRequestHost(cosXmlSimpleService.getConfig()));
        getHttpMetrics(this.listPartsRequest, "ListPartsRequest");
        this.listPartsRequest.setTaskStateListener(new QCloudTaskStateListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.7
            @Override // com.tencent.qcloud.core.common.QCloudTaskStateListener
            public void onStateChanged(String str, int i) {
                if (COSXMLUploadTask.this.IS_EXIT.get()) {
                    return;
                }
                COSXMLUploadTask.this.onUpdateInProgress();
            }
        });
        this.listPartsRequest.setClientTraceId(this.clientTraceId);
        listAllPartsAsync(cosXmlSimpleService, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.8
            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onSuccess(CosXmlRequest cosXmlRequest, final CosXmlResult cosXmlResult) {
                if (cosXmlRequest == COSXMLUploadTask.this.listPartsRequest && !COSXMLUploadTask.this.IS_EXIT.get()) {
                    if (COSXMLUploadTask.this.url != null) {
                        COSXMLUploadTask.this.updateSlicePart((ListPartsResult) cosXmlResult);
                        COSXMLUploadTask.this.multiUploadsStateListenerHandler.onListParts();
                    } else {
                        COSXMLUploadTask.executor.execute(new Runnable() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.8.1
                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX WARN: Type inference failed for: r0v0 */
                            /* JADX WARN: Type inference failed for: r0v1, types: [java.io.Closeable] */
                            /* JADX WARN: Type inference failed for: r0v14, types: [java.io.Closeable, java.io.InputStream] */
                            /* JADX WARN: Type inference failed for: r0v16 */
                            /* JADX WARN: Type inference failed for: r0v17 */
                            /* JADX WARN: Type inference failed for: r0v18 */
                            /* JADX WARN: Type inference failed for: r0v19 */
                            /* JADX WARN: Type inference failed for: r0v20 */
                            /* JADX WARN: Type inference failed for: r0v21 */
                            /* JADX WARN: Type inference failed for: r0v22 */
                            /* JADX WARN: Type inference failed for: r0v23 */
                            /* JADX WARN: Type inference failed for: r0v3 */
                            /* JADX WARN: Type inference failed for: r0v4, types: [java.io.Closeable] */
                            /* JADX WARN: Type inference failed for: r0v6 */
                            @Override // java.lang.Runnable
                            public void run() {
                                boolean zVerifyUploadParts;
                                ?? OpenUploadFileStream = 0;
                                OpenUploadFileStream = 0;
                                try {
                                    try {
                                        OpenUploadFileStream = COSXMLUploadTask.this.openUploadFileStream();
                                        zVerifyUploadParts = COSXMLUploadTask.this.verifyUploadParts(((ListPartsResult) cosXmlResult).listParts, OpenUploadFileStream);
                                        OpenUploadFileStream = OpenUploadFileStream;
                                        if (OpenUploadFileStream != 0) {
                                            try {
                                                CloseUtil.closeQuietly(OpenUploadFileStream);
                                                OpenUploadFileStream = OpenUploadFileStream;
                                            } catch (CosXmlClientException e) {
                                                e.printStackTrace();
                                                OpenUploadFileStream = e;
                                            }
                                        }
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                        ?? r0 = OpenUploadFileStream;
                                        if (OpenUploadFileStream != 0) {
                                            try {
                                                CloseUtil.closeQuietly(OpenUploadFileStream);
                                                r0 = OpenUploadFileStream;
                                            } catch (CosXmlClientException e3) {
                                                e3.printStackTrace();
                                                r0 = e3;
                                            }
                                        }
                                        zVerifyUploadParts = false;
                                        OpenUploadFileStream = r0;
                                    }
                                    if (zVerifyUploadParts) {
                                        COSXMLUploadTask.this.updateSlicePart((ListPartsResult) cosXmlResult);
                                        COSXMLUploadTask.this.multiUploadsStateListenerHandler.onListParts();
                                    } else {
                                        COSXMLUploadTask.this.reTrans();
                                    }
                                } catch (Throwable th) {
                                    if (OpenUploadFileStream != 0) {
                                        try {
                                            CloseUtil.closeQuietly(OpenUploadFileStream);
                                        } catch (CosXmlClientException e4) {
                                            e4.printStackTrace();
                                        }
                                    }
                                    throw th;
                                }
                            }
                        });
                    }
                }
            }

            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onFail(final CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                if (cosXmlRequest == COSXMLUploadTask.this.listPartsRequest && !COSXMLUploadTask.this.IS_EXIT.get()) {
                    if (cosXmlServiceException != null && "NoSuchUpload".equals(cosXmlServiceException.getErrorCode())) {
                        COSXMLUploadTask.executor.execute(new Runnable() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.8.2
                            @Override // java.lang.Runnable
                            public void run() {
                                HeadObjectResult headObjectResultHeadObjectToCheckCRC64 = COSXMLUploadTask.this.headObjectToCheckCRC64();
                                if (headObjectResultHeadObjectToCheckCRC64 != null) {
                                    COSXMLUploadTask.this.onTransferComplete(cosXmlRequest, headObjectResultHeadObjectToCheckCRC64);
                                } else {
                                    if (COSXMLUploadTask.this.IS_EXIT.get()) {
                                        return;
                                    }
                                    COSXMLUploadTask.this.reTrans();
                                }
                            }
                        });
                    } else {
                        COSXMLUploadTask.this.IS_EXIT.set(true);
                        COSXMLUploadTask.this.multiUploadsStateListenerHandler.onFailed(cosXmlRequest, cosXmlClientException, cosXmlServiceException);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUpdateInProgress() {
        updateState(TransferState.IN_PROGRESS, null, null, false);
        if (this.waitTimeoutTimer != null) {
            this.waitTimeoutTimer.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public InputStream openUploadFileStream() throws IOException {
        if (this.srcPath != null) {
            return new FileInputStream(this.srcPath);
        }
        if (this.uri != null) {
            if (ContextHolder.getAppContext() == null) {
                throw new IOException("Open src file failed, Application context is null!");
            }
            return ContextHolder.getAppContext().getContentResolver().openInputStream(this.uri);
        }
        throw new IOException("There is no src file path or uri!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean verifyUploadParts(ListParts listParts, InputStream inputStream) throws IOException {
        List<ListParts.Part> list = listParts.parts;
        Collections.sort(list, new Comparator<ListParts.Part>() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.9
            @Override // java.util.Comparator
            public int compare(ListParts.Part part, ListParts.Part part2) {
                int iIntValue = Integer.valueOf(part.partNumber).intValue();
                int iIntValue2 = Integer.valueOf(part2.partNumber).intValue();
                if (iIntValue > iIntValue2) {
                    return 1;
                }
                return iIntValue < iIntValue2 ? -1 : 0;
            }
        });
        boolean zIsFixSliceSize = isFixSliceSize(list);
        boolean z = true;
        int i = 0;
        for (ListParts.Part part : list) {
            int i2 = Integer.parseInt(part.partNumber);
            z = z && i + 1 == i2;
            if (!zIsFixSliceSize && !z) {
                return true;
            }
            String cOSMd5 = DigestUtils.getCOSMd5(inputStream, ((long) ((i2 - i) - 1)) * this.sliceSize, Long.parseLong(part.size));
            if (!part.eTag.equals(cOSMd5)) {
                COSLogger.iProcess("UploadTask", "verify upload parts failed, part number " + part.partNumber + ", etag " + part.eTag + ", but local md5 is " + cOSMd5);
                return false;
            }
            i = i2;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HeadObjectResult headObjectToCheckCRC64() {
        try {
            HeadObjectRequest headObjectRequest = new HeadObjectRequest(this.bucket, this.cosPath);
            this.headObjectRequest = headObjectRequest;
            headObjectRequest.setCredentialProvider(this.credentialProvider);
            this.headObjectRequest.setClientTraceId(this.clientTraceId);
            HeadObjectResult headObjectResultHeadObject = this.cosXmlService.headObject(this.headObjectRequest);
            long bigIntFromString = DigestUtils.getBigIntFromString(getCRCValue(headObjectResultHeadObject));
            InputStream inputStreamOpenUploadFileStream = openUploadFileStream();
            long crc64 = DigestUtils.getCRC64(inputStreamOpenUploadFileStream);
            inputStreamOpenUploadFileStream.close();
            if (bigIntFromString == crc64) {
                return headObjectResultHeadObject;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getCRCValue(HeadObjectResult headObjectResult) {
        List<String> list = headObjectResult.headers.get(Headers.COS_HASH_CRC64_ECMA);
        if (list == null || list.size() != 1) {
            return null;
        }
        return list.get(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void multiUploadPart(CosXmlSimpleService cosXmlSimpleService) {
        boolean z;
        synchronized (this.partStructMap) {
            Iterator<Map.Entry<Integer, SlicePartStruct>> it = this.partStructMap.entrySet().iterator();
            z = true;
            while (it.hasNext()) {
                final SlicePartStruct value = it.next().getValue();
                if (!value.isAlreadyUpload && !this.IS_EXIT.get()) {
                    final UploadPartRequest uploadPartRequest = this.srcPath != null ? new UploadPartRequest(this.bucket, this.cosPath, value.partNumber, this.srcPath, value.offset, value.sliceSize, this.uploadId) : null;
                    if (this.uri != null) {
                        uploadPartRequest = new UploadPartRequest(this.bucket, this.cosPath, value.partNumber, this.uri, value.offset, value.sliceSize, this.uploadId);
                    }
                    if (this.url != null) {
                        uploadPartRequest = new UploadPartRequest(this.bucket, this.cosPath, value.partNumber, this.url, value.offset, value.sliceSize, this.uploadId);
                    }
                    if (this.priorityLow) {
                        uploadPartRequest.setPriorityLow();
                    }
                    uploadPartRequest.setRegion(this.region);
                    if (this.url == null) {
                        uploadPartRequest.setNeedMD5(this.isNeedMd5);
                    } else {
                        uploadPartRequest.setNeedMD5(false);
                    }
                    uploadPartRequest.setRequestHeaders(this.headers);
                    uploadPartRequest.addNoSignHeader(this.noSignHeaders);
                    uploadPartRequest.setNetworkType(this.networkType);
                    uploadPartRequest.setHost(this.host);
                    uploadPartRequest.setCredentialProvider(this.credentialProvider);
                    uploadPartRequest.setOnRequestWeightListener(new CosXmlRequest.OnRequestWeightListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.10
                        @Override // com.tencent.cos.xml.model.CosXmlRequest.OnRequestWeightListener
                        public int onWeight() {
                            return COSXMLUploadTask.this.weightStrategy.getWeight(COSXMLUploadTask.this.ALREADY_SEND_DATA_LEN.get());
                        }
                    });
                    if (this.onSignatureListener != null) {
                        uploadPartRequest.setSign(this.onSignatureListener.onGetSign(uploadPartRequest));
                    }
                    getHttpMetrics(uploadPartRequest, "UploadPartRequest");
                    this.uploadPartRequestLongMap.put(uploadPartRequest, 0L);
                    uploadPartRequest.setProgressListener(new CosXmlProgressListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.11
                        @Override // com.tencent.qcloud.core.common.QCloudProgressListener
                        public void onProgress(long j, long j2) {
                            if (COSXMLUploadTask.this.IS_EXIT.get()) {
                                return;
                            }
                            try {
                                long jAddAndGet = COSXMLUploadTask.this.ALREADY_SEND_DATA_LEN.addAndGet(j - ((Long) COSXMLUploadTask.this.uploadPartRequestLongMap.get(uploadPartRequest)).longValue());
                                COSXMLUploadTask.this.uploadPartRequestLongMap.put(uploadPartRequest, Long.valueOf(j));
                                COSXMLUploadTask cOSXMLUploadTask = COSXMLUploadTask.this;
                                cOSXMLUploadTask.dispatchProgressChange(jAddAndGet, cOSXMLUploadTask.fileLength);
                            } catch (Exception unused) {
                            }
                        }
                    });
                    uploadPartRequest.setClientTraceId(this.clientTraceId);
                    cosXmlSimpleService.uploadPartAsync(uploadPartRequest, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.12
                        @Override // com.tencent.cos.xml.listener.CosXmlResultListener
                        public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                            if (cosXmlRequest != uploadPartRequest) {
                                return;
                            }
                            COSXMLUploadTask.this.httpTaskMetrics.merge(cosXmlRequest.getMetrics());
                            if (COSXMLUploadTask.this.IS_EXIT.get()) {
                                return;
                            }
                            value.eTag = ((UploadPartResult) cosXmlResult).eTag;
                            value.isAlreadyUpload = true;
                            synchronized (COSXMLUploadTask.this.SYNC_UPLOAD_PART) {
                                COSXMLUploadTask.this.UPLOAD_PART_COUNT.decrementAndGet();
                                if (COSXMLUploadTask.this.UPLOAD_PART_COUNT.get() == 0) {
                                    COSXMLUploadTask.this.multiUploadsStateListenerHandler.onUploadParts();
                                }
                            }
                        }

                        @Override // com.tencent.cos.xml.listener.CosXmlResultListener
                        public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                            if (cosXmlRequest == uploadPartRequest && !COSXMLUploadTask.this.IS_EXIT.get()) {
                                COSXMLUploadTask.this.IS_EXIT.set(true);
                                COSXMLUploadTask.this.multiUploadsStateListenerHandler.onFailed(cosXmlRequest, cosXmlClientException, cosXmlServiceException);
                            }
                        }
                    });
                    z = false;
                }
            }
        }
        if (!z || this.IS_EXIT.get()) {
            return;
        }
        long j = this.fileLength;
        dispatchProgressChange(j, j);
        this.multiUploadsStateListenerHandler.onUploadParts();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void completeMultiUpload(CosXmlSimpleService cosXmlSimpleService) {
        this.sendingCompleteRequest.set(true);
        CompleteMultiUploadRequest completeMultiUploadRequest = new CompleteMultiUploadRequest(this.bucket, this.cosPath, this.uploadId, null);
        this.completeMultiUploadRequest = completeMultiUploadRequest;
        completeMultiUploadRequest.setRegion(this.region);
        Iterator<Map.Entry<Integer, SlicePartStruct>> it = this.partStructMap.entrySet().iterator();
        while (it.hasNext()) {
            SlicePartStruct value = it.next().getValue();
            this.completeMultiUploadRequest.setPartNumberAndETag(value.partNumber, value.eTag);
        }
        this.completeMultiUploadRequest.setNeedMD5(this.isNeedMd5);
        this.completeMultiUploadRequest.setRequestHeaders(getCustomCompleteHeaders(this.headers));
        this.completeMultiUploadRequest.addNoSignHeader(this.noSignHeaders);
        this.completeMultiUploadRequest.setNetworkType(this.networkType);
        this.completeMultiUploadRequest.setHost(this.host);
        this.completeMultiUploadRequest.setCredentialProvider(this.credentialProvider);
        if (this.onSignatureListener != null) {
            this.completeMultiUploadRequest.setSign(this.onSignatureListener.onGetSign(this.completeMultiUploadRequest));
        }
        this.completeMultiUploadRequest.setClientTraceId(this.clientTraceId);
        getHttpMetrics(this.completeMultiUploadRequest, "CompleteMultiUploadRequest");
        cosXmlSimpleService.completeMultiUploadAsync(this.completeMultiUploadRequest, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.13
            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                if (cosXmlRequest != COSXMLUploadTask.this.completeMultiUploadRequest) {
                    return;
                }
                COSXMLUploadTask.this.sendingCompleteRequest.set(false);
                COSXMLUploadTask.this.onTransferComplete(cosXmlRequest, cosXmlResult);
            }

            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onFail(final CosXmlRequest cosXmlRequest, final CosXmlClientException cosXmlClientException, final CosXmlServiceException cosXmlServiceException) {
                if (cosXmlRequest != COSXMLUploadTask.this.completeMultiUploadRequest) {
                    return;
                }
                if (cosXmlServiceException != null && "NoSuchUpload".equals(cosXmlServiceException.getErrorCode())) {
                    COSXMLUploadTask.executor.execute(new Runnable() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.13.1
                        @Override // java.lang.Runnable
                        public void run() {
                            HeadObjectResult headObjectResultHeadObjectToCheckCRC64 = COSXMLUploadTask.this.headObjectToCheckCRC64();
                            if (headObjectResultHeadObjectToCheckCRC64 != null) {
                                COSXMLUploadTask.this.onTransferComplete(cosXmlRequest, headObjectResultHeadObjectToCheckCRC64);
                            } else {
                                COSXMLUploadTask.this.encounterError(cosXmlRequest, cosXmlClientException, cosXmlServiceException);
                            }
                            COSXMLUploadTask.this.sendingCompleteRequest.set(false);
                        }
                    });
                } else {
                    COSXMLUploadTask.this.encounterError(cosXmlRequest, cosXmlClientException, cosXmlServiceException);
                    COSXMLUploadTask.this.sendingCompleteRequest.set(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTransferComplete(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
        cosXmlRequest.attachMetrics(this.httpTaskMetrics);
        if (this.IS_EXIT.get()) {
            return;
        }
        this.IS_EXIT.set(true);
        CosTrackService.getInstance().reportUploadTaskSuccess(cosXmlRequest, getCosXmlServiceConfigTrackParams());
        this.multiUploadsStateListenerHandler.onCompleted(cosXmlRequest, cosXmlResult);
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected void internalCompleted() {
        clear();
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected void internalFailed() {
        cancelAllRequest(this.cosXmlService, false);
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected void internalPause(boolean z) {
        CosXmlRequest cosXmlRequestBuildCOSXMLTaskRequest = buildCOSXMLTaskRequest();
        cosXmlRequestBuildCOSXMLTaskRequest.attachMetrics(this.httpTaskMetrics);
        CosTrackService.getInstance().reportUploadTaskSuccess(cosXmlRequestBuildCOSXMLTaskRequest, getCosXmlServiceConfigTrackParams());
        cancelAllRequest(this.cosXmlService, z);
    }

    public boolean pauseSafely() {
        if (this.sendingCompleteRequest.get()) {
            return false;
        }
        pause();
        return true;
    }

    public boolean pauseSafely(boolean z) {
        if (this.sendingCompleteRequest.get()) {
            return false;
        }
        pause(z);
        return true;
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected void internalCancel(boolean z) {
        cancelAllRequest(this.cosXmlService, z);
        if (this.isSliceUpload) {
            abortMultiUpload(this.cosXmlService);
        }
        clear();
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected void internalResume() {
        this.taskState = TransferState.WAITING;
        this.IS_EXIT.set(false);
        if (this.inputStream != null) {
            this.IS_EXIT.set(true);
            this.multiUploadsStateListenerHandler.onFailed(buildCOSXMLTaskRequest(), new CosXmlClientException(ClientErrorCode.SINK_SOURCE_NOT_FOUND.getCode(), "inputStream closed"), null);
        } else {
            upload();
        }
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected void encounterError(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
        if (this.IS_EXIT.get()) {
            return;
        }
        this.IS_EXIT.set(true);
        MultiUploadsStateListener multiUploadsStateListener = this.multiUploadsStateListenerHandler;
        if (cosXmlRequest == null) {
            cosXmlRequest = buildCOSXMLTaskRequest();
        }
        multiUploadsStateListener.onFailed(cosXmlRequest, cosXmlClientException, cosXmlServiceException);
    }

    void cancelAllRequest(CosXmlSimpleService cosXmlSimpleService, boolean z) {
        HeadObjectRequest headObjectRequest = this.headObjectRequest;
        if (headObjectRequest != null) {
            cosXmlSimpleService.cancel(headObjectRequest, z);
        }
        PutObjectRequest putObjectRequest = this.putObjectRequest;
        if (putObjectRequest != null) {
            cosXmlSimpleService.cancel(putObjectRequest, z);
        }
        InitMultipartUploadRequest initMultipartUploadRequest = this.initMultipartUploadRequest;
        if (initMultipartUploadRequest != null) {
            cosXmlSimpleService.cancel(initMultipartUploadRequest, z);
        }
        ListPartsRequest listPartsRequest = this.listPartsRequest;
        if (listPartsRequest != null) {
            cosXmlSimpleService.cancel(listPartsRequest, z);
        }
        Map<UploadPartRequest, Long> map = this.uploadPartRequestLongMap;
        if (map != null) {
            Iterator<UploadPartRequest> it = map.keySet().iterator();
            while (it.hasNext()) {
                cosXmlSimpleService.cancel(it.next(), z);
            }
        }
        CompleteMultiUploadRequest completeMultiUploadRequest = this.completeMultiUploadRequest;
        if (completeMultiUploadRequest != null) {
            cosXmlSimpleService.cancel(completeMultiUploadRequest, z);
        }
    }

    private void abortMultiUpload(CosXmlSimpleService cosXmlSimpleService) {
        if (this.uploadId == null) {
            return;
        }
        AbortMultiUploadRequest abortMultiUploadRequest = new AbortMultiUploadRequest(this.bucket, this.cosPath, this.uploadId);
        abortMultiUploadRequest.setRegion(this.region);
        abortMultiUploadRequest.setCredentialProvider(this.credentialProvider);
        if (this.onSignatureListener != null) {
            abortMultiUploadRequest.setSign(this.onSignatureListener.onGetSign(abortMultiUploadRequest));
        }
        abortMultiUploadRequest.setClientTraceId(this.clientTraceId);
        getHttpMetrics(abortMultiUploadRequest, "AbortMultiUploadRequest");
        cosXmlSimpleService.abortMultiUploadAsync(abortMultiUploadRequest, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.14
            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
            }

            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
            }
        });
    }

    private void clear() {
        Map<UploadPartRequest, Long> map = this.uploadPartRequestLongMap;
        if (map != null) {
            map.clear();
        }
        Map<Integer, SlicePartStruct> map2 = this.partStructMap;
        if (map2 != null) {
            map2.clear();
        }
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected CosXmlRequest buildCOSXMLTaskRequest() {
        return new COSXMLUploadTaskRequest(this.region, this.bucket, this.cosPath, this.srcPath, this.headers, this.queries);
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected CosXmlResult buildCOSXMLTaskResult(CosXmlResult cosXmlResult) {
        COSXMLUploadTaskResult cOSXMLUploadTaskResult = new COSXMLUploadTaskResult();
        if (cosXmlResult != null) {
            cOSXMLUploadTaskResult.httpCode = cosXmlResult.httpCode;
            cOSXMLUploadTaskResult.httpMessage = cosXmlResult.httpMessage;
            cOSXMLUploadTaskResult.headers = cosXmlResult.headers;
            cOSXMLUploadTaskResult.accessUrl = cosXmlResult.accessUrl;
        }
        if (cosXmlResult instanceof PutObjectResult) {
            PutObjectResult putObjectResult = (PutObjectResult) cosXmlResult;
            cOSXMLUploadTaskResult.eTag = putObjectResult.eTag;
            cOSXMLUploadTaskResult.picUploadResult = putObjectResult.picUploadResult();
            cOSXMLUploadTaskResult.callbackResult = putObjectResult.callbackResult;
        } else if (cosXmlResult instanceof CompleteMultiUploadResult) {
            CompleteMultiUploadResult completeMultiUploadResult = (CompleteMultiUploadResult) cosXmlResult;
            if (completeMultiUploadResult.completeMultipartUpload != null) {
                cOSXMLUploadTaskResult.eTag = completeMultiUploadResult.completeMultipartUpload.eTag;
                PicUploadResult picUploadResult = new PicUploadResult();
                picUploadResult.originalInfo = completeMultiUploadResult.completeMultipartUpload.getOriginInfo();
                picUploadResult.processResults = completeMultiUploadResult.completeMultipartUpload.processResults;
                cOSXMLUploadTaskResult.picUploadResult = picUploadResult;
                cOSXMLUploadTaskResult.callbackResult = completeMultiUploadResult.completeMultipartUpload.callbackResult;
            }
        } else if (cosXmlResult instanceof HeadObjectResult) {
            cOSXMLUploadTaskResult.eTag = ((HeadObjectResult) cosXmlResult).eTag;
        }
        return cOSXMLUploadTaskResult;
    }

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String str) {
        this.uploadId = str;
    }

    private void initSlicePart(long j, long j2, int i) {
        int i2 = (int) (j2 / this.sliceSize);
        int i3 = 0;
        while (true) {
            if (i3 >= i2) {
                break;
            }
            SlicePartStruct slicePartStruct = new SlicePartStruct();
            slicePartStruct.isAlreadyUpload = false;
            slicePartStruct.partNumber = i + i3;
            slicePartStruct.offset = (((long) i3) * this.sliceSize) + j;
            slicePartStruct.sliceSize = this.sliceSize;
            this.partStructMap.put(Integer.valueOf(slicePartStruct.partNumber), slicePartStruct);
            i3++;
        }
        if (j2 % this.sliceSize != 0) {
            SlicePartStruct slicePartStruct2 = new SlicePartStruct();
            slicePartStruct2.isAlreadyUpload = false;
            slicePartStruct2.partNumber = i + i2;
            slicePartStruct2.offset = (((long) i2) * this.sliceSize) + j;
            slicePartStruct2.sliceSize = (j + j2) - slicePartStruct2.offset;
            this.partStructMap.put(Integer.valueOf(slicePartStruct2.partNumber), slicePartStruct2);
            i2++;
        }
        this.UPLOAD_PART_COUNT.set((i + i2) - 1);
        this.IS_EXIT.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSlicePart(ListPartsResult listPartsResult) {
        List<ListParts.Part> list;
        if (listPartsResult == null || listPartsResult.listParts == null || (list = listPartsResult.listParts.parts) == null || list.size() <= 0) {
            return;
        }
        if (isFixSliceSize(list)) {
            for (ListParts.Part part : list) {
                if (this.partStructMap.containsKey(Integer.valueOf(part.partNumber))) {
                    SlicePartStruct slicePartStruct = this.partStructMap.get(Integer.valueOf(part.partNumber));
                    slicePartStruct.isAlreadyUpload = true;
                    slicePartStruct.eTag = part.eTag;
                    this.UPLOAD_PART_COUNT.decrementAndGet();
                    this.ALREADY_SEND_DATA_LEN.addAndGet(Long.parseLong(part.size));
                }
            }
            return;
        }
        Collections.sort(list, new Comparator<ListParts.Part>() { // from class: com.tencent.cos.xml.transfer.COSXMLUploadTask.15
            @Override // java.util.Comparator
            public int compare(ListParts.Part part2, ListParts.Part part3) {
                int iIntValue = Integer.valueOf(part2.partNumber).intValue();
                int iIntValue2 = Integer.valueOf(part3.partNumber).intValue();
                if (iIntValue > iIntValue2) {
                    return 1;
                }
                return iIntValue < iIntValue2 ? -1 : 0;
            }
        });
        int indexOfParts = getIndexOfParts(list);
        if (indexOfParts < 0) {
            return;
        }
        this.partStructMap.clear();
        long j = 0;
        int i = 0;
        while (i <= indexOfParts) {
            ListParts.Part part2 = list.get(i);
            SlicePartStruct slicePartStruct2 = new SlicePartStruct();
            i++;
            slicePartStruct2.partNumber = i;
            slicePartStruct2.offset = j;
            slicePartStruct2.sliceSize = Long.parseLong(part2.size);
            slicePartStruct2.eTag = part2.eTag;
            slicePartStruct2.isAlreadyUpload = true;
            j += slicePartStruct2.sliceSize;
            this.partStructMap.put(Integer.valueOf(i), slicePartStruct2);
        }
        this.ALREADY_SEND_DATA_LEN.addAndGet(j);
        initSlicePart(j, this.fileLength - j, indexOfParts + 2);
        for (int i2 = 0; i2 <= indexOfParts; i2++) {
            this.UPLOAD_PART_COUNT.decrementAndGet();
        }
    }

    private boolean isFixSliceSize(List<ListParts.Part> list) {
        for (ListParts.Part part : list) {
            if (this.partStructMap.containsKey(Integer.valueOf(part.partNumber)) && this.partStructMap.get(Integer.valueOf(part.partNumber)).sliceSize != Long.valueOf(part.size).longValue()) {
                return false;
            }
        }
        return true;
    }

    private int getIndexOfParts(List<ListParts.Part> list) {
        if (Integer.valueOf(list.get(0).partNumber).intValue() != 1) {
            return -1;
        }
        int size = list.size();
        int i = 0;
        int iIntValue = 1;
        for (int i2 = 1; i2 < size; i2++) {
            ListParts.Part part = list.get(i2);
            if (Integer.valueOf(part.partNumber).intValue() != iIntValue + 1) {
                break;
            }
            iIntValue = Integer.valueOf(part.partNumber).intValue();
            i = i2;
        }
        return i;
    }

    protected void startUpload() {
        if (this.bytes != null || this.inputStream != null) {
            simpleUpload(this.cosXmlService);
            return;
        }
        long j = this.fileLength;
        if (j < this.multiUploadSizeDivision || this.forceSimpleUpload) {
            simpleUpload(this.cosXmlService);
            return;
        }
        long j2 = this.sliceSize;
        if (Math.ceil(j / j2) > 10000.0d) {
            this.sliceSize = (long) Math.ceil(this.fileLength / 10000.0d);
        } else {
            this.sliceSize = j2;
        }
        this.isSliceUpload = true;
        this.UPLOAD_PART_COUNT = new AtomicInteger(0);
        this.ALREADY_SEND_DATA_LEN = new AtomicLong(0L);
        this.partStructMap = Collections.synchronizedMap(new LinkedHashMap());
        this.uploadPartRequestLongMap = new LinkedHashMap();
        multiUpload(this.cosXmlService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reTrans() {
        this.uploadId = null;
        startUpload();
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

    private static class WeightStrategy {
        private final long DEFAULT_WEIGHT_HIGH_SIZE;
        private final long DEFAULT_WEIGHT_NORMAL_SIZE;
        private long highSize;
        private long normalSize;

        private WeightStrategy() {
            this.DEFAULT_WEIGHT_NORMAL_SIZE = 83886080L;
            this.DEFAULT_WEIGHT_HIGH_SIZE = 157286400L;
            this.normalSize = 83886080L;
            this.highSize = 157286400L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getWeight(long j) {
            if (j > this.highSize) {
                return 2;
            }
            return j > this.normalSize ? 1 : 0;
        }
    }

    public boolean getSendingCompleteRequest() {
        return this.sendingCompleteRequest.get();
    }

    public static class COSXMLUploadTaskRequest extends PutObjectRequest {
        protected COSXMLUploadTaskRequest(String str, String str2, String str3, String str4, Map<String, List<String>> map, Map<String, String> map2) {
            super(str2, str3, str4);
            setRegion(str);
            setRequestHeaders(map);
            setQueryParameters(map2);
        }
    }

    public static class COSXMLUploadTaskResult extends CosXmlResult {
        public CallbackResult callbackResult;
        public String eTag;
        public PicUploadResult picUploadResult;

        protected COSXMLUploadTaskResult() {
        }
    }

    private Map<String, List<String>> getCustomCompleteHeaders(Map<String, List<String>> map) {
        if (map == null) {
            return new HashMap();
        }
        HashMap map2 = new HashMap(map);
        map2.remove("Content-Type");
        return map2;
    }

    private String getContentTypeByHeaders(Map<String, List<String>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        List<String> list = map.get("Content-Type");
        if (list == null || list.isEmpty()) {
            list = map.get("content-type");
        }
        if (list == null || list.isEmpty()) {
            list = map.get("Content-type");
        }
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
