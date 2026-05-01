package com.tencent.cos.xml;

import android.content.Context;
import android.text.TextUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.common.VersionInfo;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.BasePutObjectRequest;
import com.tencent.cos.xml.model.object.BasePutObjectResult;
import com.tencent.cos.xml.model.object.GetObjectBytesRequest;
import com.tencent.cos.xml.model.object.GetObjectBytesResult;
import com.tencent.cos.xml.model.object.GetObjectRequest;
import com.tencent.cos.xml.model.object.GetObjectResult;
import com.tencent.cos.xml.model.object.SaveLocalRequest;
import com.tencent.cos.xml.model.object.UploadPartRequest;
import com.tencent.cos.xml.model.object.UploadPartResult;
import com.tencent.cos.xml.model.object.UploadRequest;
import com.tencent.cos.xml.transfer.ResponseBytesConverter;
import com.tencent.cos.xml.transfer.ResponseFileBodySerializer;
import com.tencent.cos.xml.transfer.ResponseXmlS3BodySerializer;
import com.tencent.cos.xml.utils.StringUtils;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.QCloudCredentials;
import com.tencent.qcloud.core.auth.QCloudSelfSigner;
import com.tencent.qcloud.core.auth.QCloudSigner;
import com.tencent.qcloud.core.auth.ScopeLimitCredentialProvider;
import com.tencent.qcloud.core.auth.SignerFactory;
import com.tencent.qcloud.core.auth.StaticCredentialProvider;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudResultListener;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpResult;
import com.tencent.qcloud.core.http.HttpTask;
import com.tencent.qcloud.core.http.NetworkClient;
import com.tencent.qcloud.core.http.OkHttpClientImpl;
import com.tencent.qcloud.core.http.QCloudHttpClient;
import com.tencent.qcloud.core.http.QCloudHttpRequest;
import com.tencent.qcloud.core.http.QCloudHttpRetryHandler;
import com.tencent.qcloud.core.http.ResponseBodyConverter;
import com.tencent.qcloud.core.logger.COSLogger;
import com.tencent.qcloud.core.task.QCloudTask;
import com.tencent.qcloud.core.task.RetryStrategy;
import com.tencent.qcloud.core.task.TaskExecutors;
import com.tencent.qcloud.core.util.ContextHolder;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import okhttp3.HttpUrl;

/* JADX INFO: loaded from: classes4.dex */
public class CosXmlBaseService implements BaseCosXml {
    public static String BRIDGE = null;
    public static boolean IS_CLOSE_REPORT = false;
    private static final String TAG = "CosXmlBaseService";
    public static String appCachePath;
    protected volatile QCloudHttpClient client;
    protected CosXmlServiceConfig config;
    protected QCloudCredentialProvider credentialProvider;
    protected String requestDomain;
    protected QCloudSelfSigner selfSigner;
    protected String signerType;
    protected String tag;

    protected <T1 extends CosXmlRequest, T2 extends CosXmlResult> boolean buildHttpRequestBodyConverter(T1 t1, T2 t2, QCloudHttpRequest.Builder<T2> builder) {
        return false;
    }

    protected <T1 extends CosXmlRequest> void setCopySource(T1 t1) throws CosXmlClientException {
    }

    protected String signerTypeCompat(String str, CosXmlRequest cosXmlRequest) {
        return str;
    }

    public CosXmlBaseService(Context context, CosXmlServiceConfig cosXmlServiceConfig, QCloudCredentialProvider qCloudCredentialProvider) {
        this(context, cosXmlServiceConfig);
        this.credentialProvider = qCloudCredentialProvider;
    }

    public CosXmlBaseService(Context context, CosXmlServiceConfig cosXmlServiceConfig) {
        this.tag = "CosXml";
        this.signerType = "CosXmlSigner";
        ContextHolder.setContext(context);
        COSLogger.enableLogcat(cosXmlServiceConfig.isDebuggable());
        COSLogger.enableLogFile(cosXmlServiceConfig.isDebuggable());
        CosTrackService.init(context.getApplicationContext(), IS_CLOSE_REPORT, BRIDGE);
        appCachePath = context.getApplicationContext().getFilesDir().getPath();
        TaskExecutors.initExecutor(cosXmlServiceConfig.getUploadMaxThreadCount(), cosXmlServiceConfig.getDownloadMaxThreadCount());
        setNetworkClient(cosXmlServiceConfig);
    }

    public QCloudCredentialProvider getCredentialProvider() {
        return this.credentialProvider;
    }

    public CosXmlBaseService(Context context, CosXmlServiceConfig cosXmlServiceConfig, QCloudSigner qCloudSigner) {
        this(context, cosXmlServiceConfig);
        this.credentialProvider = new StaticCredentialProvider(null);
        this.signerType = "UserCosXmlSigner";
        SignerFactory.registerSigner("UserCosXmlSigner", qCloudSigner);
    }

    public CosXmlBaseService(Context context, CosXmlServiceConfig cosXmlServiceConfig, QCloudSelfSigner qCloudSelfSigner) {
        this(context, cosXmlServiceConfig);
        this.selfSigner = qCloudSelfSigner;
    }

    private void init(QCloudHttpClient.Builder builder, CosXmlServiceConfig cosXmlServiceConfig) {
        builder.setConnectionTimeout(cosXmlServiceConfig.getConnectionTimeout()).setSocketTimeout(cosXmlServiceConfig.getSocketTimeout());
        RetryStrategy retryStrategy = cosXmlServiceConfig.getRetryStrategy();
        if (retryStrategy != null) {
            builder.setRetryStrategy(retryStrategy);
        }
        QCloudHttpRetryHandler qCloudHttpRetryHandler = cosXmlServiceConfig.getQCloudHttpRetryHandler();
        if (qCloudHttpRetryHandler != null) {
            builder.setQCloudHttpRetryHandler(qCloudHttpRetryHandler);
        }
        if (cosXmlServiceConfig.getCustomizeNetworkClient() != null) {
            builder.setNetworkClient(cosXmlServiceConfig.getCustomizeNetworkClient());
        } else if (cosXmlServiceConfig.isEnableQuic()) {
            try {
                builder.setNetworkClient((NetworkClient) Class.forName("com.tencent.qcloud.quic.QuicClientImpl").newInstance());
            } catch (Exception e) {
                IllegalStateException illegalStateException = new IllegalStateException(e.getMessage(), e);
                CosTrackService.getInstance().reportError(TAG, illegalStateException);
                throw illegalStateException;
            }
        } else {
            builder.setNetworkClient(new OkHttpClientImpl());
        }
        builder.dnsCache(cosXmlServiceConfig.isDnsCache());
        builder.addPrefetchHost(cosXmlServiceConfig.getEndpointSuffix());
        builder.setVerifySSLEnable(cosXmlServiceConfig.isVerifySSLEnable());
        builder.setClientCertificate(cosXmlServiceConfig.getClientCertificateBytes(), cosXmlServiceConfig.getClientCertificatePassword());
        builder.setRedirectEnable(cosXmlServiceConfig.isRedirectEnable());
    }

    public void setNetworkClient(CosXmlServiceConfig cosXmlServiceConfig) {
        QCloudHttpClient.Builder builder = new QCloudHttpClient.Builder();
        init(builder, cosXmlServiceConfig);
        this.client = builder.build();
        this.client.setNetworkClientType(builder);
        this.config = cosXmlServiceConfig;
        this.client.addVerifiedHost("*." + cosXmlServiceConfig.getEndpointSuffix());
        this.client.addVerifiedHost("*." + cosXmlServiceConfig.getEndpointSuffix(cosXmlServiceConfig.getRegion(), true));
    }

    public void addCustomerDNS(String str, String[] strArr) throws CosXmlClientException {
        try {
            this.client.addDnsRecord(str, strArr);
        } catch (UnknownHostException e) {
            throw new CosXmlClientException(ClientErrorCode.POOR_NETWORK.getCode(), e);
        }
    }

    public void addCustomerDNSFetch(QCloudHttpClient.QCloudDnsFetch qCloudDnsFetch) {
        this.client.addDnsFetch(qCloudDnsFetch);
    }

    @Deprecated
    public void addVerifiedHost(String str) {
        this.client.addVerifiedHost(str);
    }

    public void setDomain(String str) {
        this.requestDomain = str;
    }

    protected String getRequestHost(CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        if (!TextUtils.isEmpty(cosXmlRequest.getHost())) {
            return cosXmlRequest.getHost();
        }
        if (!TextUtils.isEmpty(this.requestDomain)) {
            return this.requestDomain;
        }
        return cosXmlRequest.getRequestHost(this.config);
    }

    protected <T1 extends CosXmlRequest, T2 extends CosXmlResult> QCloudHttpRequest buildHttpRequest(T1 t1, T2 t2, boolean z, String str) throws CosXmlClientException {
        QCloudHttpRequest.Builder<T2> builderTag = new QCloudHttpRequest.Builder().method(t1.getMethod()).userAgent(getUserAgent(str)).tag((Object) this.tag);
        builderTag.addNoSignHeaderKeys(this.config.getNoSignHeaders());
        builderTag.addNoSignHeaderKeys(t1.getNoSignHeaders());
        builderTag.addNoSignParamKeys(t1.getNoSignParams());
        String requestURL = t1.getRequestURL();
        String requestHost = getRequestHost(t1);
        if (TextUtils.isEmpty(t1.getHost())) {
            if (this.config.networkSwitchStrategy() == CosXmlServiceConfig.RequestNetworkStrategy.Aggressive) {
                if (z) {
                    requestHost = this.config.getDefaultRequestHost(t1.getRegion(), t1.getBucket());
                }
            } else if (this.config.networkSwitchStrategy() == CosXmlServiceConfig.RequestNetworkStrategy.Conservative && !z) {
                requestHost = this.config.getDefaultRequestHost(t1.getRegion(), t1.getBucket());
            }
        }
        if (requestURL != null) {
            try {
                builderTag.url(new URL(requestURL));
            } catch (MalformedURLException e) {
                throw new CosXmlClientException(ClientErrorCode.BAD_REQUEST.getCode(), e);
            }
        } else {
            t1.checkParameters();
            builderTag.scheme(this.config.getProtocol()).host(requestHost).path(t1.getPath(this.config));
            if (this.config.getPort() != -1) {
                builderTag.port(this.config.getPort());
            }
            builderTag.query(t1.getQueryString());
            if (t1.getQueryEncodedString() != null) {
                builderTag.encodedQuery(t1.getQueryEncodedString());
            }
        }
        setCopySource(t1);
        HashSet<String> hashSet = new HashSet();
        hashSet.addAll(this.config.getCommonHeaders().keySet());
        hashSet.addAll(t1.getRequestHeaders().keySet());
        HashMap map = new HashMap();
        for (String str2 : hashSet) {
            List<String> list = t1.getRequestHeaders().get(str2);
            if (list == null) {
                list = this.config.getCommonHeaders().get(str2);
            }
            if (list != null) {
                map.put(str2, list);
            }
        }
        if (!map.containsKey("Host")) {
            LinkedList linkedList = new LinkedList();
            linkedList.add(requestHost);
            map.put("Host", linkedList);
        }
        if (t1.headersHasUnsafeNonAscii()) {
            builderTag.addHeadersUnsafeNonAscii(map);
        } else {
            builderTag.addHeaders(map);
        }
        if (t1.isNeedMD5()) {
            builderTag.contentMD5();
        }
        builderTag.setKeyTime(t1.getKeyTime());
        if (this.credentialProvider != null || t1.getCredentialProvider() != null) {
            builderTag.signer(signerTypeCompat(this.signerType, t1), t1.getSignSourceProvider());
        } else {
            builderTag.signer(null, null);
        }
        QCloudSelfSigner qCloudSelfSigner = this.selfSigner;
        if (qCloudSelfSigner != null) {
            builderTag.selfSigner(qCloudSelfSigner);
        }
        builderTag.credentialScope(t1.getSTSCredentialScope(this.config));
        if (t1.getRequestBody() != null) {
            builderTag.body(t1.getRequestBody());
        }
        if (t1 instanceof SaveLocalRequest) {
            SaveLocalRequest saveLocalRequest = (SaveLocalRequest) t1;
            if (!TextUtils.isEmpty(saveLocalRequest.getSaveLocalPath())) {
                builderTag.converter((ResponseBodyConverter<T2>) new ResponseFileBodySerializer((GetObjectResult) t2, saveLocalRequest.getSaveLocalPath(), saveLocalRequest.getSaveLocalOffset()));
            } else if (saveLocalRequest.getSaveLocalUri() != null) {
                builderTag.converter((ResponseBodyConverter<T2>) new ResponseFileBodySerializer((GetObjectResult) t2, saveLocalRequest.getSaveLocalUri(), ContextHolder.getAppContext().getContentResolver(), saveLocalRequest.getSaveLocalOffset()));
            }
        } else if (t1 instanceof GetObjectBytesRequest) {
            builderTag.converter((ResponseBodyConverter<T2>) new ResponseBytesConverter((GetObjectBytesResult) t2));
        } else if (!buildHttpRequestBodyConverter(t1, t2, builderTag)) {
            builderTag.converter((ResponseBodyConverter<T2>) new ResponseXmlS3BodySerializer(t2));
        }
        builderTag.signInUrl(t1.isSignInUrl() || this.config.isSignInUrl());
        return builderTag.build();
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <T1 extends com.tencent.cos.xml.model.CosXmlRequest, T2 extends com.tencent.cos.xml.model.CosXmlResult> com.tencent.qcloud.core.http.HttpTask buildHttpTask(T1 r4, T2 r5, boolean r6) throws com.tencent.cos.xml.exception.CosXmlClientException {
        /*
            Method dump skipped, instruction units count: 236
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.cos.xml.CosXmlBaseService.buildHttpTask(com.tencent.cos.xml.model.CosXmlRequest, com.tencent.cos.xml.model.CosXmlResult, boolean):com.tencent.qcloud.core.http.HttpTask");
    }

    protected <T1 extends CosXmlRequest, T2 extends CosXmlResult> T2 execute(T1 t1, T2 t2) throws CosXmlServiceException, CosXmlClientException {
        return (T2) execute(t1, t2, false);
    }

    protected <T1 extends CosXmlRequest, T2 extends CosXmlResult> T2 execute(T1 t1, T2 t2, boolean z) throws CosXmlServiceException, CosXmlClientException {
        return (T2) execute(t1, t2, z, false);
    }

    protected <T1 extends CosXmlRequest, T2 extends CosXmlResult> T2 execute(T1 t1, T2 t2, boolean z, boolean z2) throws CosXmlServiceException, CosXmlClientException {
        try {
            HttpTask httpTaskBuildHttpTask = buildHttpTask(t1, t2, z2);
            setProgressListener(t1, httpTaskBuildHttpTask, false);
            HttpResult httpResultExecuteNow = httpTaskBuildHttpTask.executeNow();
            CosTrackService.getInstance().reportRequestSuccess(t1, z, this.config.getTrackParams());
            CosTrackService.getInstance().reportHttpMetrics(t1);
            logRequestMetrics(t1);
            if (httpResultExecuteNow != null) {
                return (T2) httpResultExecuteNow.content();
            }
            return null;
        } catch (QCloudClientException e) {
            CosTrackService.getInstance().reportHttpMetrics(t1);
            CosXmlClientException cosXmlClientExceptionConvertClientException = CosTrackService.getInstance().convertClientException(e);
            if (this.config.networkSwitchStrategy() != null && !z2 && (cosXmlClientExceptionConvertClientException.errorCode == ClientErrorCode.POOR_NETWORK.getCode() || cosXmlClientExceptionConvertClientException.errorCode == ClientErrorCode.IO_ERROR.getCode())) {
                return (T2) execute(t1, t2, z, true);
            }
            throw CosTrackService.getInstance().reportRequestClientException(t1, e, z, this.config.getTrackParams());
        } catch (QCloudServiceException e2) {
            CosTrackService.getInstance().reportHttpMetrics(t1);
            CosXmlServiceException cosXmlServiceExceptionConvertServerException = CosTrackService.getInstance().convertServerException(e2);
            if (this.config.networkSwitchStrategy() != null && !z2 && ("RequestTimeout".equals(cosXmlServiceExceptionConvertServerException.getErrorCode()) || "UserNetworkTooSlow".equals(cosXmlServiceExceptionConvertServerException.getErrorCode()))) {
                return (T2) execute(t1, t2, z, true);
            }
            throw CosTrackService.getInstance().reportRequestServiceException(t1, e2, z, this.config.getTrackParams());
        }
    }

    protected <T1 extends CosXmlRequest, T2 extends CosXmlResult> void schedule(T1 t1, T2 t2, CosXmlResultListener cosXmlResultListener) {
        schedule(t1, t2, cosXmlResultListener, false);
    }

    protected <T1 extends CosXmlRequest, T2 extends CosXmlResult> void schedule(T1 t1, T2 t2, CosXmlResultListener cosXmlResultListener, boolean z) {
        schedule(t1, t2, cosXmlResultListener, z, false);
    }

    protected <T1 extends CosXmlRequest, T2 extends CosXmlResult> void schedule(final T1 t1, final T2 t2, final CosXmlResultListener cosXmlResultListener, final boolean z, final boolean z2) {
        Object obj = new QCloudResultListener<HttpResult<T2>>() { // from class: com.tencent.cos.xml.CosXmlBaseService.1
            @Override // com.tencent.qcloud.core.common.QCloudResultListener
            public void onSuccess(HttpResult<T2> httpResult) {
                CosTrackService.getInstance().reportRequestSuccess(t1, z, CosXmlBaseService.this.config.getTrackParams());
                CosTrackService.getInstance().reportHttpMetrics(t1);
                CosXmlBaseService.this.logRequestMetrics(t1);
                cosXmlResultListener.onSuccess(t1, (CosXmlResult) httpResult.content());
            }

            @Override // com.tencent.qcloud.core.common.QCloudResultListener
            public void onFailure(QCloudClientException qCloudClientException, QCloudServiceException qCloudServiceException) {
                CosTrackService.getInstance().reportHttpMetrics(t1);
                CosXmlBaseService.this.logRequestMetrics(t1);
                if (qCloudClientException != null) {
                    CosXmlClientException cosXmlClientExceptionConvertClientException = CosTrackService.getInstance().convertClientException(qCloudClientException);
                    if (CosXmlBaseService.this.config.networkSwitchStrategy() != null && !z2 && (cosXmlClientExceptionConvertClientException.errorCode == ClientErrorCode.POOR_NETWORK.getCode() || cosXmlClientExceptionConvertClientException.errorCode == ClientErrorCode.IO_ERROR.getCode())) {
                        CosXmlBaseService.this.schedule(t1, t2, cosXmlResultListener, z, true);
                    }
                    cosXmlResultListener.onFail(t1, CosTrackService.getInstance().reportRequestClientException(t1, qCloudClientException, z, CosXmlBaseService.this.config.getTrackParams()), null);
                    return;
                }
                if (qCloudServiceException != null) {
                    CosXmlServiceException cosXmlServiceExceptionConvertServerException = CosTrackService.getInstance().convertServerException(qCloudServiceException);
                    if (CosXmlBaseService.this.config.networkSwitchStrategy() != null && !z2 && ("RequestTimeout".equals(cosXmlServiceExceptionConvertServerException.getErrorCode()) || "UserNetworkTooSlow".equals(cosXmlServiceExceptionConvertServerException.getErrorCode()))) {
                        CosXmlBaseService.this.schedule(t1, t2, cosXmlResultListener, z, true);
                    }
                    cosXmlResultListener.onFail(t1, null, CosTrackService.getInstance().reportRequestServiceException(t1, qCloudServiceException, z, CosXmlBaseService.this.config.getTrackParams()));
                }
            }
        };
        try {
            HttpTask httpTaskBuildHttpTask = buildHttpTask(t1, t2, z2);
            setProgressListener(t1, httpTaskBuildHttpTask, true);
            Executor executor = this.config.getExecutor();
            Executor observeExecutor = this.config.getObserveExecutor();
            if (observeExecutor != null) {
                httpTaskBuildHttpTask.observeOn(observeExecutor);
            }
            httpTaskBuildHttpTask.addResultListener(obj);
            if (executor != null) {
                httpTaskBuildHttpTask.scheduleOn(executor);
            } else if (t1 instanceof UploadRequest) {
                httpTaskBuildHttpTask.scheduleOn(TaskExecutors.UPLOAD_EXECUTOR, t1.getPriority());
            } else {
                httpTaskBuildHttpTask.schedule();
            }
        } catch (QCloudClientException e) {
            CosXmlClientException cosXmlClientExceptionConvertClientException = CosTrackService.getInstance().convertClientException(e);
            if (this.config.networkSwitchStrategy() != null && !z2 && (cosXmlClientExceptionConvertClientException.errorCode == ClientErrorCode.POOR_NETWORK.getCode() || cosXmlClientExceptionConvertClientException.errorCode == ClientErrorCode.IO_ERROR.getCode())) {
                schedule(t1, t2, cosXmlResultListener, z, true);
            }
            cosXmlResultListener.onFail(t1, CosTrackService.getInstance().reportRequestClientException(t1, e, z, this.config.getTrackParams()), null);
        }
    }

    protected <T1 extends CosXmlRequest, T2 extends CosXmlResult> void setProgressListener(final T1 t1, HttpTask<T2> httpTask, boolean z) {
        if (t1 instanceof BasePutObjectRequest) {
            httpTask.addProgressListener(((BasePutObjectRequest) t1).getProgressListener());
            return;
        }
        if (t1 instanceof UploadPartRequest) {
            httpTask.addProgressListener(((UploadPartRequest) t1).getProgressListener());
            if (z) {
                httpTask.setOnRequestWeightListener(new QCloudTask.OnRequestWeightListener() { // from class: com.tencent.cos.xml.CosXmlBaseService.2
                    @Override // com.tencent.qcloud.core.task.QCloudTask.OnRequestWeightListener
                    public int onWeight() {
                        return t1.getWeight();
                    }
                });
                return;
            }
            return;
        }
        if (t1 instanceof GetObjectRequest) {
            httpTask.addProgressListener(((GetObjectRequest) t1).getProgressListener());
        }
    }

    public String getAccessUrl(CosXmlRequest cosXmlRequest) {
        String requestHost;
        String requestURL = cosXmlRequest.getRequestURL();
        if (requestURL != null) {
            int iIndexOf = requestURL.indexOf(PunctuationConst.QUESTION_MARK);
            return iIndexOf > 0 ? requestURL.substring(0, iIndexOf) : requestURL;
        }
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme(this.config.getProtocol());
        try {
            requestHost = getRequestHost(cosXmlRequest);
        } catch (CosXmlClientException e) {
            CosTrackService.getInstance().reportError(TAG, e);
            e.printStackTrace();
            requestHost = null;
        }
        builder.host(requestHost);
        String path = cosXmlRequest.getPath(this.config);
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (path.length() > 0) {
            builder.addPathSegments(path);
        }
        return builder.build().toString();
    }

    public String getPresignedURL(CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        QCloudCredentials credentials;
        try {
            if (cosXmlRequest.getCredentialProvider() != null) {
                credentials = cosXmlRequest.getCredentialProvider().getCredentials();
            } else {
                QCloudCredentialProvider qCloudCredentialProvider = this.credentialProvider;
                if (qCloudCredentialProvider instanceof ScopeLimitCredentialProvider) {
                    credentials = ((ScopeLimitCredentialProvider) qCloudCredentialProvider).getCredentials(cosXmlRequest.getSTSCredentialScope(this.config));
                } else {
                    credentials = qCloudCredentialProvider.getCredentials();
                }
            }
            QCloudSigner signer = SignerFactory.getSigner(signerTypeCompat(this.signerType, cosXmlRequest));
            QCloudHttpRequest qCloudHttpRequestBuildHttpRequest = buildHttpRequest(cosXmlRequest, null, false, null);
            signer.sign(qCloudHttpRequestBuildHttpRequest, credentials);
            String strHeader = qCloudHttpRequestBuildHttpRequest.header("Authorization");
            String strHeader2 = qCloudHttpRequestBuildHttpRequest.header(Headers.SECURITY_TOKEN);
            if (!TextUtils.isEmpty(strHeader2)) {
                strHeader = strHeader + "&x-cos-security-token=" + strHeader2;
            }
            String accessUrl = getAccessUrl(cosXmlRequest);
            String strFlat = StringUtils.flat(cosXmlRequest.getQueryString());
            return TextUtils.isEmpty(strFlat) ? accessUrl + PunctuationConst.QUESTION_MARK + strHeader : accessUrl + PunctuationConst.QUESTION_MARK + strFlat + PunctuationConst.AND + strHeader;
        } catch (QCloudClientException e) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_CREDENTIALS.getCode(), e);
        }
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public GetObjectResult getObject(GetObjectRequest getObjectRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetObjectResult) execute(getObjectRequest, new GetObjectResult());
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public void getObjectAsync(GetObjectRequest getObjectRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getObjectRequest, new GetObjectResult(), cosXmlResultListener);
    }

    public void internalGetObjectAsync(GetObjectRequest getObjectRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getObjectRequest, new GetObjectResult(), cosXmlResultListener, true);
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public byte[] getObject(String str, String str2) throws CosXmlServiceException, CosXmlClientException {
        GetObjectBytesResult getObjectBytesResult = (GetObjectBytesResult) execute(new GetObjectBytesRequest(str, str2), new GetObjectBytesResult());
        return getObjectBytesResult != null ? getObjectBytesResult.data : new byte[0];
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public byte[] getObject(GetObjectBytesRequest getObjectBytesRequest) throws CosXmlServiceException, CosXmlClientException {
        GetObjectBytesResult getObjectBytesResult = (GetObjectBytesResult) execute(getObjectBytesRequest, new GetObjectBytesResult());
        return getObjectBytesResult != null ? getObjectBytesResult.data : new byte[0];
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public BasePutObjectResult basePutObject(BasePutObjectRequest basePutObjectRequest) throws CosXmlServiceException, CosXmlClientException {
        BasePutObjectResult basePutObjectResult = new BasePutObjectResult();
        basePutObjectResult.accessUrl = getAccessUrl(basePutObjectRequest);
        return (BasePutObjectResult) execute(basePutObjectRequest, basePutObjectResult);
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public void basePutObjectAsync(BasePutObjectRequest basePutObjectRequest, CosXmlResultListener cosXmlResultListener) {
        BasePutObjectResult basePutObjectResult = new BasePutObjectResult();
        basePutObjectResult.accessUrl = getAccessUrl(basePutObjectRequest);
        schedule(basePutObjectRequest, basePutObjectResult, cosXmlResultListener);
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UploadPartResult) execute(uploadPartRequest, new UploadPartResult());
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public void uploadPartAsync(UploadPartRequest uploadPartRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(uploadPartRequest, new UploadPartResult(), cosXmlResultListener);
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public String getObjectUrl(String str, String str2, String str3) {
        BasePutObjectRequest basePutObjectRequest = new BasePutObjectRequest(str, str3, "");
        basePutObjectRequest.setRegion(str2);
        return getAccessUrl(basePutObjectRequest);
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public <T1 extends CosXmlRequest, T2 extends CosXmlResult> T2 commonInterface(T1 t1, Class<T2> cls) throws CosXmlServiceException, CosXmlClientException {
        try {
            return (T2) execute(t1, cls.newInstance());
        } catch (IllegalAccessException e) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "Failed to create result instance", e);
        } catch (InstantiationException e2) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "Failed to create result instance", e2);
        }
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public <T1 extends CosXmlRequest, T2 extends CosXmlResult> void commonInterfaceAsync(T1 t1, Class<T2> cls, CosXmlResultListener cosXmlResultListener) {
        try {
            schedule(t1, cls.newInstance(), cosXmlResultListener);
        } catch (IllegalAccessException e) {
            cosXmlResultListener.onFail(t1, new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "Failed to create result instance", e), null);
        } catch (InstantiationException e2) {
            cosXmlResultListener.onFail(t1, new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "Failed to create result instance", e2), null);
        }
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public void cancel(CosXmlRequest cosXmlRequest) {
        if (cosXmlRequest == null || cosXmlRequest.getHttpTask() == null) {
            return;
        }
        cosXmlRequest.getHttpTask().cancel();
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public void cancel(CosXmlRequest cosXmlRequest, boolean z) {
        if (cosXmlRequest == null || cosXmlRequest.getHttpTask() == null) {
            return;
        }
        cosXmlRequest.getHttpTask().cancel(z);
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public void cancelAll() {
        Iterator<HttpTask> it = this.client.getTasksByTag(this.tag).iterator();
        while (it.hasNext()) {
            it.next().cancel();
        }
    }

    @Override // com.tencent.cos.xml.BaseCosXml
    public void release() {
        cancelAll();
    }

    @Deprecated
    public String getAppid() {
        return this.config.getAppid();
    }

    @Deprecated
    public String getRegion() {
        return this.config.getRegion();
    }

    @Deprecated
    public String getRegion(CosXmlRequest cosXmlRequest) {
        return cosXmlRequest.getRegion() == null ? this.config.getRegion() : cosXmlRequest.getRegion();
    }

    public CosXmlServiceConfig getConfig() {
        return this.config;
    }

    public String getUserAgent(String str) {
        String userAgent;
        CosXmlServiceConfig cosXmlServiceConfig = this.config;
        if (cosXmlServiceConfig == null) {
            return VersionInfo.getUserAgent();
        }
        if (!cosXmlServiceConfig.isEnableQuic() || OkHttpClientImpl.class.getName().equals(str)) {
            userAgent = VersionInfo.getUserAgent();
        } else if ("com.tencent.qcloud.quic.QuicClientImpl".equals(str)) {
            userAgent = VersionInfo.getQuicUserAgent();
        } else {
            userAgent = VersionInfo.getQuicUserAgent();
        }
        return TextUtils.isEmpty(this.config.getUserAgentExtended()) ? userAgent : userAgent + "-" + this.config.getUserAgentExtended();
    }

    public File[] getLogFiles(int i) {
        return COSLogger.getLogFiles(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logRequestMetrics(CosXmlRequest cosXmlRequest) {
        if (cosXmlRequest.getMetrics() != null) {
            COSLogger.iNetwork(QCloudHttpClient.HTTP_LOG_TAG, cosXmlRequest.getMetrics().toString());
        }
    }
}
