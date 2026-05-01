package com.tencent.cos.xml.transfer;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
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
import com.tencent.cos.xml.model.object.GetObjectRequest;
import com.tencent.cos.xml.model.object.HeadObjectRequest;
import com.tencent.cos.xml.utils.COSUtils;
import com.tencent.cos.xml.utils.CRC64Calculator;
import com.tencent.cos.xml.utils.DigestUtils;
import com.tencent.cos.xml.utils.FileUtils;
import com.tencent.qcloud.core.common.QCloudTaskStateListener;
import com.tencent.qcloud.core.logger.COSLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes4.dex */
public final class COSXMLDownloadTask extends COSXMLTask {
    private static final long CRC_CHECK_INTERVAL = 10485760;
    private static final int MAX_RETRY = 3;
    private static final String TAG = "COSXMLDownloadTask";
    private String clientTraceId;
    private boolean crc64CheckEnabled;
    private Map<Long, Long> crc64Map;
    private ExecutorService crcExecutor;
    private CountDownLatch crcLatch;
    private String eTag;
    private long fileOffset;
    private GetObjectRequest getObjectRequest;
    private long hasWriteDataLen;
    private HeadObjectRequest headObjectRequest;
    private long lastCrcCheckPosition;
    private String localSaveDirPath;
    private String localSaveFileName;
    private boolean objectKeySimplifyCheck;
    private long rangeEnd;
    private long rangeStart;
    private int retryCount;
    private String serverCrc64;
    private SharedPreferences sharedPreferences;
    private long startTime;

    COSXMLDownloadTask(Context context, CosXmlSimpleService cosXmlSimpleService, String str, String str2, String str3, String str4, String str5) {
        this.crc64CheckEnabled = true;
        this.crc64Map = new ConcurrentHashMap();
        this.lastCrcCheckPosition = 0L;
        this.retryCount = 0;
        this.crcExecutor = Executors.newSingleThreadExecutor();
        this.crcLatch = new CountDownLatch(1);
        this.rangeStart = 0L;
        this.rangeEnd = -1L;
        this.fileOffset = 0L;
        this.objectKeySimplifyCheck = true;
        this.hasWriteDataLen = 0L;
        this.startTime = 0L;
        this.region = str;
        this.bucket = str2;
        this.cosPath = str3;
        this.localSaveDirPath = str4;
        this.localSaveFileName = str5;
        this.cosXmlService = cosXmlSimpleService;
        if (context != null) {
            this.sharedPreferences = context.getSharedPreferences("COSXMLDOWNLOADTASK", 0);
        }
        this.clientTraceId = UUID.randomUUID().toString();
    }

    COSXMLDownloadTask(Context context, CosXmlSimpleService cosXmlSimpleService, GetObjectRequest getObjectRequest) {
        this(context, cosXmlSimpleService, getObjectRequest.getRegion(), getObjectRequest.getBucket(), getObjectRequest.getPath(cosXmlSimpleService.getConfig()), getObjectRequest.getSavePath(), getObjectRequest.getSaveFileName());
        this.queries = getObjectRequest.getQueryString();
        this.headers = getObjectRequest.getRequestHeaders();
        this.noSignHeaders = getObjectRequest.getNoSignHeaders();
        this.networkType = getObjectRequest.getNetworkType();
        this.host = getObjectRequest.getHost();
        this.credentialProvider = getObjectRequest.getCredentialProvider();
        this.objectKeySimplifyCheck = getObjectRequest.isObjectKeySimplifyCheck();
        this.isNeedMd5 = getObjectRequest.isNeedMD5();
        if (this.headers != null && this.headers.containsKey("Range")) {
            String str = this.headers.get("Range").get(0);
            int iIndexOf = str.indexOf(PunctuationConst.EQUAL);
            int iIndexOf2 = str.indexOf("-");
            this.rangeStart = Long.valueOf(str.substring(iIndexOf + 1, iIndexOf2)).longValue();
            String strSubstring = str.substring(iIndexOf2 + 1);
            if (!TextUtils.isEmpty(strSubstring)) {
                this.rangeEnd = Long.valueOf(strSubstring).longValue();
            }
            this.crc64CheckEnabled = false;
        }
        this.fileOffset = getObjectRequest.getFileOffset();
    }

    private boolean checkParameter() {
        if (!this.objectKeySimplifyCheck) {
            return true;
        }
        String canonicalPath = this.cosPath;
        try {
            canonicalPath = new File("/" + this.cosPath).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!"/".equals(canonicalPath)) {
            return true;
        }
        updateState(TransferState.FAILED, new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "The key in the getobject is illegal"), null, false);
        return false;
    }

    protected void download() {
        if (checkParameter()) {
            this.startTime = System.nanoTime();
            this.clientTraceId = UUID.randomUUID().toString();
            run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void realDownload(long j, long j2, long j3) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(this.bucket, this.cosPath, this.localSaveDirPath, this.localSaveFileName);
        this.getObjectRequest = getObjectRequest;
        getObjectRequest.setRegion(this.region);
        this.getObjectRequest.setFileOffset(j3);
        this.getObjectRequest.setQueryParameters(this.queries);
        this.getObjectRequest.setRequestHeaders(this.headers);
        this.getObjectRequest.setObjectKeySimplifyCheck(this.objectKeySimplifyCheck);
        this.getObjectRequest.addNoSignHeader(this.noSignHeaders);
        this.getObjectRequest.setNetworkType(this.networkType);
        this.getObjectRequest.setHost(this.host);
        this.getObjectRequest.setCredentialProvider(this.credentialProvider);
        if (j2 > 0 || j > 0) {
            this.getObjectRequest.setRange(j, j2);
        }
        if (this.onSignatureListener != null) {
            this.getObjectRequest.setSign(this.onSignatureListener.onGetSign(this.getObjectRequest));
        }
        getHttpMetrics(this.getObjectRequest, "GetObjectRequest");
        this.getObjectRequest.setProgressListener(new CosXmlProgressListener() { // from class: com.tencent.cos.xml.transfer.COSXMLDownloadTask.1
            @Override // com.tencent.qcloud.core.common.QCloudProgressListener
            public void onProgress(long j4, long j5) {
                long j6 = COSXMLDownloadTask.this.hasWriteDataLen + j4;
                if (COSXMLDownloadTask.this.crc64CheckEnabled && (j6 - COSXMLDownloadTask.this.lastCrcCheckPosition >= COSXMLDownloadTask.CRC_CHECK_INTERVAL || (j4 == j5 && j6 > COSXMLDownloadTask.this.lastCrcCheckPosition))) {
                    COSLogger.dProcess(COSXMLDownloadTask.TAG, "calculateFileCrc start: " + COSXMLDownloadTask.this.lastCrcCheckPosition + " end: " + j6);
                    long j7 = COSXMLDownloadTask.this.lastCrcCheckPosition;
                    COSXMLDownloadTask.this.lastCrcCheckPosition = j6;
                    COSXMLDownloadTask.this.calculateFileCrc(j7, j6, j4 == j5);
                }
                if (COSXMLDownloadTask.this.cosXmlProgressListener != null) {
                    COSXMLDownloadTask.this.cosXmlProgressListener.onProgress(j6, COSXMLDownloadTask.this.hasWriteDataLen + j5);
                }
            }
        });
        this.getObjectRequest.setClientTraceId(this.clientTraceId);
        this.cosXmlService.internalGetObjectAsync(this.getObjectRequest, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.COSXMLDownloadTask.2
            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                if (cosXmlRequest != COSXMLDownloadTask.this.getObjectRequest) {
                    return;
                }
                if (COSXMLDownloadTask.this.crc64CheckEnabled) {
                    COSXMLDownloadTask.this.serverCrc64 = cosXmlResult.getHeader(Headers.COS_HASH_CRC64_ECMA);
                    COSLogger.dProcess(COSXMLDownloadTask.TAG, "serverCrc64: " + COSXMLDownloadTask.this.serverCrc64);
                    try {
                        COSXMLDownloadTask.this.crcLatch.await();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                        COSXMLDownloadTask.this.handleCrcCheckFailure();
                        return;
                    }
                }
                if (!COSXMLDownloadTask.this.crc64CheckEnabled || COSXMLDownloadTask.this.validateCrc64()) {
                    CosTrackService.getInstance().reportDownloadTaskSuccess(COSXMLDownloadTask.this.getObjectRequest, COSXMLDownloadTask.this.getCosXmlServiceConfigTrackParams());
                    if (COSXMLDownloadTask.this.IS_EXIT.get()) {
                        return;
                    }
                    COSXMLDownloadTask.this.IS_EXIT.set(true);
                    COSXMLDownloadTask.this.updateState(TransferState.COMPLETED, null, cosXmlResult, false);
                    return;
                }
                COSXMLDownloadTask.this.handleCrcCheckFailure();
            }

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                Exception exc;
                if (cosXmlRequest != COSXMLDownloadTask.this.getObjectRequest) {
                    return;
                }
                if (cosXmlClientException == null || COSXMLDownloadTask.this.taskState == TransferState.PAUSED || COSXMLDownloadTask.this.taskState == TransferState.CANCELED) {
                    cosXmlClientException = null;
                } else {
                    CosTrackService.getInstance().reportDownloadTaskClientException(cosXmlRequest, cosXmlClientException, COSXMLDownloadTask.this.getCosXmlServiceConfigTrackParams());
                }
                if (cosXmlServiceException == null || COSXMLDownloadTask.this.taskState == TransferState.PAUSED || COSXMLDownloadTask.this.taskState == TransferState.CANCELED) {
                    exc = cosXmlClientException;
                } else {
                    CosTrackService.getInstance().reportDownloadTaskServiceException(cosXmlRequest, cosXmlServiceException, COSXMLDownloadTask.this.getCosXmlServiceConfigTrackParams());
                    exc = cosXmlServiceException;
                }
                if (COSXMLDownloadTask.this.IS_EXIT.get()) {
                    return;
                }
                COSXMLDownloadTask.this.IS_EXIT.set(true);
                COSXMLDownloadTask.this.updateState(TransferState.FAILED, exc, null, false);
            }
        });
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected CosXmlRequest buildCOSXMLTaskRequest() {
        return new COSXMLDownloadTaskRequest(this.region, this.bucket, this.cosPath, this.localSaveDirPath, this.localSaveFileName, this.headers, this.queries);
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected CosXmlResult buildCOSXMLTaskResult(CosXmlResult cosXmlResult) {
        COSXMLDownloadTaskResult cOSXMLDownloadTaskResult = new COSXMLDownloadTaskResult();
        if (cosXmlResult != null) {
            cOSXMLDownloadTaskResult.httpCode = cosXmlResult.httpCode;
            cOSXMLDownloadTaskResult.httpMessage = cosXmlResult.httpMessage;
            cOSXMLDownloadTaskResult.headers = cosXmlResult.headers;
            cOSXMLDownloadTaskResult.eTag = this.eTag;
            cOSXMLDownloadTaskResult.accessUrl = cosXmlResult.accessUrl;
        }
        return cOSXMLDownloadTaskResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getKey() {
        StringBuffer stringBuffer = new StringBuffer("download_");
        stringBuffer.append(this.region).append(PunctuationConst.UNDERLINE).append(this.bucket).append(PunctuationConst.UNDERLINE).append(this.cosPath).append(PunctuationConst.UNDERLINE).append(this.rangeStart).append(PunctuationConst.UNDERLINE).append(this.rangeEnd).append(PunctuationConst.UNDERLINE).append(this.fileOffset).append(PunctuationConst.UNDERLINE).append(this.localSaveDirPath).append(PunctuationConst.UNDERLINE).append(this.localSaveFileName).append(PunctuationConst.UNDERLINE).append(this.eTag);
        try {
            return DigestUtils.getSha1(stringBuffer.toString());
        } catch (CosXmlClientException unused) {
            return stringBuffer.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized String hasExisted() {
        SharedPreferences sharedPreferences = this.sharedPreferences;
        if (sharedPreferences == null) {
            return null;
        }
        return sharedPreferences.getString(getKey(), null);
    }

    private synchronized void clear() {
        SharedPreferences sharedPreferences = this.sharedPreferences;
        if (sharedPreferences != null) {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.remove(getKey());
            editorEdit.remove(getKey() + "_crc");
            editorEdit.commit();
        }
    }

    private void cancelAllRequest(boolean z) {
        this.cosXmlService.cancel(this.headObjectRequest, z);
        this.cosXmlService.cancel(this.getObjectRequest, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void save(String str) {
        SharedPreferences sharedPreferences = this.sharedPreferences;
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(getKey(), str).commit();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calculateFileCrc(final long j, final long j2, final boolean z) {
        this.crcExecutor.execute(new Runnable() { // from class: com.tencent.cos.xml.transfer.COSXMLDownloadTask$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m1858xe3cd50a6(j, j2, z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$calculateFileCrc$0$com-tencent-cos-xml-transfer-COSXMLDownloadTask */
    /* synthetic */ void m1858xe3cd50a6(long j, long j2, boolean z) {
        try {
            long crc64 = CRC64Calculator.getCRC64(new FileInputStream(new File(getDownloadPath())), j, j2 - j);
            this.crc64Map.put(Long.valueOf(j), Long.valueOf(crc64));
            synchronized (this) {
                SharedPreferences sharedPreferences = this.sharedPreferences;
                if (sharedPreferences != null) {
                    SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                    editorEdit.putString(getKey() + "_crc", serializeCrcMap());
                    editorEdit.apply();
                }
            }
            COSLogger.dProcess(TAG, "calculateFileCrc finish start: " + j + " end: " + j2 + " crcValue= " + crc64);
            if (z) {
                this.crcLatch.countDown();
            }
        } catch (IOException e) {
            COSLogger.wProcess(TAG, "CRC64 calculation error: " + e.getMessage());
            handleCrcCheckFailure();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean validateCrc64() {
        if (TextUtils.isEmpty(this.serverCrc64)) {
            return true;
        }
        try {
            String strMergeCrcValues = mergeCrcValues();
            COSLogger.dProcess(TAG, "mergedCrc= " + strMergeCrcValues);
            return strMergeCrcValues.equals(this.serverCrc64);
        } catch (Exception e) {
            COSLogger.wProcess(TAG, "MergeCRC64 validation error: " + e.getMessage());
            return false;
        }
    }

    private String mergeCrcValues() {
        long jLongValue;
        Long[] lArr = (Long[]) this.crc64Map.keySet().toArray(new Long[0]);
        Arrays.sort(lArr);
        COSLogger.dProcess(TAG, "crc64Map sortedKeys= " + Arrays.toString(lArr) + " lastCrcCheckPosition= " + this.lastCrcCheckPosition);
        long jLongValue2 = new BigInteger(Long.toUnsignedString(this.crc64Map.get(lArr[0]).longValue(), 10).trim()).longValue();
        for (int i = 1; i < lArr.length; i++) {
            long jLongValue3 = lArr[i].longValue();
            if (i == lArr.length - 1) {
                jLongValue = this.lastCrcCheckPosition;
            } else {
                jLongValue = lArr[i + 1].longValue();
            }
            jLongValue2 = CRC64Calculator.combine(jLongValue2, new BigInteger(Long.toUnsignedString(this.crc64Map.get(Long.valueOf(jLongValue3)).longValue(), 10).trim()).longValue(), jLongValue - jLongValue3);
        }
        return Long.toUnsignedString(jLongValue2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCrcCheckFailure() {
        COSLogger.wProcess(TAG, "CRC64 check failed:" + this.retryCount);
        int i = this.retryCount;
        this.retryCount = i + 1;
        if (i < 3) {
            this.crcExecutor.shutdownNow();
            if (!FileUtils.deleteFileIfExist(getDownloadPath())) {
                updateState(TransferState.FAILED, new CosXmlClientException(ClientErrorCode.IO_ERROR.getCode(), "CRC64 check failed"), null, false);
                return;
            }
            clear();
            this.crc64Map.clear();
            this.lastCrcCheckPosition = 0L;
            this.IS_EXIT.set(false);
            this.crcExecutor = Executors.newSingleThreadExecutor();
            this.crcLatch = new CountDownLatch(1);
            download();
            return;
        }
        updateState(TransferState.FAILED, new CosXmlClientException(ClientErrorCode.IO_ERROR.getCode(), "CRC64 check failed"), null, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean validatePartialCrc(File file, long j) throws IOException {
        long j2 = 0;
        if (j <= 0) {
            return true;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        while (j2 < j) {
            try {
                long jMin = Math.min(CRC_CHECK_INTERVAL + j2, j);
                long crc64 = CRC64Calculator.getCRC64(fileInputStream, j2, jMin - j2);
                Long l = this.crc64Map.get(Long.valueOf(j2));
                if (l == null || l.longValue() != crc64) {
                    fileInputStream.close();
                    return false;
                }
                j2 = jMin;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        fileInputStream.close();
        return true;
    }

    private String serializeCrcMap() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Long, Long> entry : this.crc64Map.entrySet()) {
            if (sb.length() > 0) {
                sb.append(PunctuationConst.COMMA);
            }
            sb.append(entry.getKey()).append(":").append(entry.getValue());
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deserializeCrcMap(String str) {
        this.crc64Map.clear();
        if (TextUtils.isEmpty(str)) {
            return;
        }
        for (String str2 : str.split(PunctuationConst.COMMA)) {
            String[] strArrSplit = str2.split(":");
            if (strArrSplit.length == 2) {
                try {
                    this.crc64Map.put(Long.valueOf(Long.parseLong(strArrSplit[0])), Long.valueOf(Long.parseLong(strArrSplit[1])));
                } catch (NumberFormatException unused) {
                    COSLogger.wProcess(TAG, "Failed to parse CRC64 entry: " + str2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long crc64MapLastKey() {
        long jLongValue = 0;
        for (Long l : this.crc64Map.keySet()) {
            if (l.longValue() > jLongValue) {
                jLongValue = l.longValue();
            }
        }
        return jLongValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getDownloadPath() {
        String str;
        String str2 = this.localSaveDirPath;
        if (str2 == null) {
            return null;
        }
        if (!str2.endsWith("/")) {
            str = this.localSaveDirPath + "/";
        } else {
            str = this.localSaveDirPath;
        }
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (this.localSaveFileName != null) {
            return str + this.localSaveFileName;
        }
        if (this.cosPath == null) {
            return str;
        }
        int iLastIndexOf = this.cosPath.lastIndexOf("/");
        if (iLastIndexOf >= 0) {
            return str + this.cosPath.substring(iLastIndexOf + 1);
        }
        return str + this.cosPath;
    }

    protected void run() {
        HeadObjectRequest headObjectRequest = new HeadObjectRequest(this.bucket, this.cosPath);
        this.headObjectRequest = headObjectRequest;
        headObjectRequest.setRequestHeaders(this.headers);
        this.headObjectRequest.addNoSignHeader(this.noSignHeaders);
        this.headObjectRequest.setNetworkType(this.networkType);
        this.headObjectRequest.setHost(this.host);
        this.headObjectRequest.setCredentialProvider(this.credentialProvider);
        this.headObjectRequest.setQueryParameters(this.queries);
        this.headObjectRequest.setRegion(this.region);
        final String downloadPath = getDownloadPath();
        if (this.onSignatureListener != null) {
            this.headObjectRequest.setSign(this.onSignatureListener.onGetSign(this.headObjectRequest));
        }
        getHttpMetrics(this.headObjectRequest, "HeadObjectRequest");
        this.headObjectRequest.setTaskStateListener(new QCloudTaskStateListener() { // from class: com.tencent.cos.xml.transfer.COSXMLDownloadTask.3
            @Override // com.tencent.qcloud.core.common.QCloudTaskStateListener
            public void onStateChanged(String str, int i) {
                if (COSXMLDownloadTask.this.IS_EXIT.get() || i == 1) {
                    return;
                }
                COSXMLDownloadTask.this.updateState(TransferState.IN_PROGRESS, null, null, false);
            }
        });
        this.headObjectRequest.setClientTraceId(this.clientTraceId);
        this.cosXmlService.headObjectAsync(this.headObjectRequest, new CosXmlResultListener() { // from class: com.tencent.cos.xml.transfer.COSXMLDownloadTask.4
            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                if (cosXmlRequest == COSXMLDownloadTask.this.headObjectRequest && !COSXMLDownloadTask.this.IS_EXIT.get()) {
                    List<String> list = cosXmlResult.headers.get("ETag");
                    if (list != null && list.size() > 0) {
                        COSXMLDownloadTask.this.eTag = list.get(0);
                    }
                    String strHasExisted = COSXMLDownloadTask.this.hasExisted();
                    if (strHasExisted != null) {
                        File file = new File(strHasExisted);
                        if (file.exists()) {
                            String string = COSXMLDownloadTask.this.sharedPreferences.getString(COSXMLDownloadTask.this.getKey() + "_crc", null);
                            COSXMLDownloadTask.this.crc64CheckEnabled = !TextUtils.isEmpty(string);
                            long length = file.length();
                            if (string != null && COSXMLDownloadTask.this.crc64CheckEnabled) {
                                COSXMLDownloadTask.this.deserializeCrcMap(string);
                                long jCrc64MapLastKey = COSXMLDownloadTask.this.crc64Map.isEmpty() ? 0L : COSXMLDownloadTask.this.crc64MapLastKey();
                                if (length > jCrc64MapLastKey) {
                                    COSLogger.dProcess(COSXMLDownloadTask.TAG, "Local file is longer than CRC records, will truncate to last valid position");
                                    if (!FileUtils.truncateFile(file, jCrc64MapLastKey)) {
                                        COSLogger.dProcess(COSXMLDownloadTask.TAG, "File truncate failed, will handle as failure");
                                        COSXMLDownloadTask.this.handleCrcCheckFailure();
                                        return;
                                    }
                                    try {
                                        if (!COSXMLDownloadTask.this.validatePartialCrc(file, jCrc64MapLastKey)) {
                                            COSLogger.dProcess(COSXMLDownloadTask.TAG, "CRC check failed for existing file");
                                            COSXMLDownloadTask.this.handleCrcCheckFailure();
                                            return;
                                        }
                                    } catch (IOException e) {
                                        COSLogger.wProcess(COSXMLDownloadTask.TAG, "CRC validation error: " + e.getMessage());
                                        COSXMLDownloadTask.this.handleCrcCheckFailure();
                                        return;
                                    }
                                }
                                if (length < jCrc64MapLastKey) {
                                    COSLogger.dProcess(COSXMLDownloadTask.TAG, "Local file is shorter than CRC records");
                                    COSXMLDownloadTask.this.handleCrcCheckFailure();
                                    return;
                                }
                                try {
                                    if (COSXMLDownloadTask.this.validatePartialCrc(file, jCrc64MapLastKey)) {
                                        List<String> list2 = cosXmlResult.headers.get("Content-Length");
                                        if (list2 != null && list2.size() > 0 && Long.valueOf(list2.get(0)).longValue() == length) {
                                            if (COSXMLDownloadTask.this.cosXmlProgressListener != null) {
                                                COSXMLDownloadTask.this.cosXmlProgressListener.onProgress(length, length);
                                            }
                                            COSXMLDownloadTask.this.IS_EXIT.set(true);
                                            COSXMLDownloadTask.this.updateState(TransferState.COMPLETED, null, cosXmlResult, false);
                                            return;
                                        }
                                        COSXMLDownloadTask cOSXMLDownloadTask = COSXMLDownloadTask.this;
                                        cOSXMLDownloadTask.hasWriteDataLen = jCrc64MapLastKey - cOSXMLDownloadTask.fileOffset;
                                        COSXMLDownloadTask cOSXMLDownloadTask2 = COSXMLDownloadTask.this;
                                        cOSXMLDownloadTask2.realDownload(cOSXMLDownloadTask2.rangeStart + COSXMLDownloadTask.this.hasWriteDataLen, COSXMLDownloadTask.this.rangeEnd, COSXMLDownloadTask.this.fileOffset + COSXMLDownloadTask.this.hasWriteDataLen);
                                        return;
                                    }
                                    COSLogger.dProcess(COSXMLDownloadTask.TAG, "CRC check failed for existing file");
                                    COSXMLDownloadTask.this.handleCrcCheckFailure();
                                    return;
                                } catch (IOException e2) {
                                    COSLogger.wProcess(COSXMLDownloadTask.TAG, "CRC validation error: " + e2.getMessage());
                                    COSXMLDownloadTask.this.handleCrcCheckFailure();
                                    return;
                                }
                            }
                            List<String> list3 = cosXmlResult.headers.get("Content-Length");
                            if (list3 != null && list3.size() > 0 && Long.valueOf(list3.get(0)).longValue() == length) {
                                if (COSXMLDownloadTask.this.cosXmlProgressListener != null) {
                                    COSXMLDownloadTask.this.cosXmlProgressListener.onProgress(length, length);
                                }
                                COSXMLDownloadTask.this.IS_EXIT.set(true);
                                COSXMLDownloadTask.this.updateState(TransferState.COMPLETED, null, cosXmlResult, false);
                                return;
                            }
                            COSXMLDownloadTask cOSXMLDownloadTask3 = COSXMLDownloadTask.this;
                            cOSXMLDownloadTask3.hasWriteDataLen = length - cOSXMLDownloadTask3.fileOffset;
                            COSXMLDownloadTask cOSXMLDownloadTask4 = COSXMLDownloadTask.this;
                            cOSXMLDownloadTask4.realDownload(COSXMLDownloadTask.this.hasWriteDataLen + cOSXMLDownloadTask4.rangeStart, COSXMLDownloadTask.this.rangeEnd, COSXMLDownloadTask.this.hasWriteDataLen + COSXMLDownloadTask.this.fileOffset);
                            return;
                        }
                    }
                    FileUtils.deleteFileIfExist(downloadPath);
                    COSXMLDownloadTask cOSXMLDownloadTask5 = COSXMLDownloadTask.this;
                    cOSXMLDownloadTask5.save(cOSXMLDownloadTask5.getDownloadPath());
                    COSXMLDownloadTask.this.hasWriteDataLen = 0L;
                    COSXMLDownloadTask cOSXMLDownloadTask6 = COSXMLDownloadTask.this;
                    cOSXMLDownloadTask6.realDownload(cOSXMLDownloadTask6.rangeStart, COSXMLDownloadTask.this.rangeEnd, COSXMLDownloadTask.this.fileOffset);
                }
            }

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            @Override // com.tencent.cos.xml.listener.CosXmlResultListener
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
                if (cosXmlRequest != COSXMLDownloadTask.this.headObjectRequest) {
                    return;
                }
                Exception exc = cosXmlClientException;
                if (COSXMLDownloadTask.this.IS_EXIT.get()) {
                    return;
                }
                if (cosXmlClientException == null) {
                    exc = cosXmlServiceException;
                }
                exc.printStackTrace();
                COSXMLDownloadTask.this.updateState(TransferState.FAILED, exc, null, false);
                COSLogger.iProcess(COSXMLDownloadTask.TAG, "head " + COSXMLDownloadTask.this.cosPath + "failed !, exception is " + exc.getMessage(), exc);
            }
        });
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected void internalCompleted() {
        clear();
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected void internalFailed() {
        cancelAllRequest(false);
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected void internalPause(boolean z) {
        if (this.getObjectRequest != null) {
            CosTrackService.getInstance().reportDownloadTaskSuccess(this.getObjectRequest, getCosXmlServiceConfigTrackParams());
        }
        cancelAllRequest(z);
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected void internalCancel(boolean z) {
        cancelAllRequest(z);
        clear();
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected void internalResume() {
        this.taskState = TransferState.WAITING;
        this.IS_EXIT.set(false);
        download();
    }

    @Override // com.tencent.cos.xml.transfer.COSXMLTask
    protected void encounterError(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException) {
        if (this.IS_EXIT.get()) {
            return;
        }
        this.IS_EXIT.set(true);
        updateState(TransferState.FAILED, COSUtils.mergeException(cosXmlClientException, cosXmlServiceException), null, false);
    }

    public static class COSXMLDownloadTaskRequest extends GetObjectRequest {
        protected COSXMLDownloadTaskRequest(String str, String str2, String str3, String str4, String str5, Map<String, List<String>> map, Map<String, String> map2) {
            super(str2, str3, str4, str5);
            setRegion(str);
            setRequestHeaders(map);
            setQueryParameters(map2);
        }
    }

    public static class COSXMLDownloadTaskResult extends CosXmlResult {
        public String eTag;

        protected COSXMLDownloadTaskResult() {
        }
    }
}
