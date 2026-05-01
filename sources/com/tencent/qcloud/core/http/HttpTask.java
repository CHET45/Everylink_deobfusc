package com.tencent.qcloud.core.http;

import bolts.CancellationTokenSource;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.QCloudCredentials;
import com.tencent.qcloud.core.auth.QCloudSelfSigner;
import com.tencent.qcloud.core.auth.QCloudSigner;
import com.tencent.qcloud.core.auth.ScopeLimitCredentialProvider;
import com.tencent.qcloud.core.common.DomainSwitchException;
import com.tencent.qcloud.core.common.QCloudAuthenticationException;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudDigistListener;
import com.tencent.qcloud.core.common.QCloudProgressListener;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpConstants;
import com.tencent.qcloud.core.task.QCloudTask;
import com.tencent.qcloud.core.task.TaskExecutors;
import com.tencent.qcloud.core.util.DomainSwitchUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/* JADX INFO: loaded from: classes4.dex */
public final class HttpTask<T> extends QCloudTask<HttpResult<T>> {
    private static AtomicInteger increments = new AtomicInteger(1);
    protected final QCloudCredentialProvider credentialProvider;
    private boolean hasSwitchedDomain;
    protected final HttpRequest<T> httpRequest;
    protected HttpResult<T> httpResult;
    private QCloudProgressListener mProgressListener;
    protected HttpTaskMetrics metrics;
    private NetworkProxy<T> networkProxy;

    HttpTask(HttpRequest<T> httpRequest, QCloudCredentialProvider qCloudCredentialProvider, NetworkClient networkClient) {
        super("HttpTask-" + httpRequest.tag() + "-" + increments.getAndIncrement(), httpRequest.tag());
        this.hasSwitchedDomain = false;
        this.mProgressListener = new QCloudProgressListener() { // from class: com.tencent.qcloud.core.http.HttpTask.1
            @Override // com.tencent.qcloud.core.common.QCloudProgressListener
            public void onProgress(long j, long j2) {
                HttpTask.this.onProgress(j, j2);
            }
        };
        this.httpRequest = httpRequest;
        this.credentialProvider = qCloudCredentialProvider;
        NetworkProxy<T> networkProxyWrapper = networkClient.getNetworkProxyWrapper();
        this.networkProxy = networkProxyWrapper;
        networkProxyWrapper.identifier = getIdentifier();
        this.networkProxy.mProgressListener = this.mProgressListener;
    }

    public HttpTask<T> scheduleOn(Executor executor) {
        scheduleOn(executor, 2);
        return this;
    }

    public HttpTask<T> scheduleOn(Executor executor, int i) {
        scheduleOn(executor, new CancellationTokenSource(), i);
        return this;
    }

    public HttpTask<T> schedule() {
        schedule(2);
        return this;
    }

    public HttpTask<T> schedule(int i) {
        if (this.httpRequest.getRequestBody() instanceof ProgressBody) {
            scheduleOn(TaskExecutors.UPLOAD_EXECUTOR, i);
        } else if (this.httpRequest.getResponseBodyConverter() instanceof ProgressBody) {
            scheduleOn(TaskExecutors.DOWNLOAD_EXECUTOR, i);
        } else {
            scheduleOn(TaskExecutors.COMMAND_EXECUTOR, i);
        }
        return this;
    }

    public boolean isSuccessful() {
        HttpResult<T> httpResult = this.httpResult;
        return httpResult != null && httpResult.isSuccessful();
    }

    @Override // com.tencent.qcloud.core.task.QCloudTask
    public HttpResult<T> getResult() {
        return this.httpResult;
    }

    public HttpTask<T> attachMetric(HttpTaskMetrics httpTaskMetrics) {
        this.metrics = httpTaskMetrics;
        return this;
    }

    public HttpTaskMetrics metrics() {
        return this.metrics;
    }

    public boolean isUploadTask() {
        if (this.httpRequest.getRequestBody() instanceof StreamingRequestBody) {
            return ((StreamingRequestBody) this.httpRequest.getRequestBody()).isLargeData();
        }
        return false;
    }

    public boolean isDownloadTask() {
        return this.httpRequest.getResponseBodyConverter() instanceof ProgressBody;
    }

    public boolean isResponseFilePathConverter() {
        return (this.httpRequest.getResponseBodyConverter() instanceof ResponseFileConverter) && ((ResponseFileConverter) this.httpRequest.getResponseBodyConverter()).isFilePathConverter();
    }

    public HttpRequest<T> request() {
        return this.httpRequest;
    }

    public long getTransferBodySize() {
        ProgressBody progressBody;
        if (this.httpRequest.getRequestBody() instanceof ProgressBody) {
            progressBody = (ProgressBody) this.httpRequest.getRequestBody();
        } else {
            progressBody = this.httpRequest.getResponseBodyConverter() instanceof ProgressBody ? (ProgressBody) this.httpRequest.getResponseBodyConverter() : null;
        }
        if (progressBody != null) {
            return progressBody.getBytesTransferred();
        }
        return 0L;
    }

    public double getAverageStreamingSpeed(long j) {
        ProgressBody progressBody;
        if (this.httpRequest.getRequestBody() instanceof ProgressBody) {
            progressBody = (ProgressBody) this.httpRequest.getRequestBody();
        } else {
            progressBody = this.httpRequest.getResponseBodyConverter() instanceof ProgressBody ? (ProgressBody) this.httpRequest.getResponseBodyConverter() : null;
        }
        if (progressBody != null) {
            return (progressBody.getBytesTransferred() / 1024.0d) / (j / 1000.0d);
        }
        return 0.0d;
    }

    public boolean isSelfSigner() {
        return this.httpRequest.getQCloudSelfSigner() != null;
    }

    public boolean hasSwitchedDomain() {
        return this.hasSwitchedDomain;
    }

    public QCloudHttpRequest retrySignRequest() throws QCloudClientException {
        this.httpRequest.removeHeader("Authorization");
        this.httpRequest.addOrReplaceHeader(HttpConstants.Header.COS_SDK_RETRY, String.valueOf(true));
        QCloudSigner qCloudSigner = this.httpRequest.getQCloudSigner();
        if (qCloudSigner != null) {
            this.metrics.onSignRequestStart();
            signRequest(qCloudSigner, (QCloudHttpRequest) this.httpRequest);
            this.metrics.onSignRequestEnd();
        }
        QCloudSelfSigner qCloudSelfSigner = this.httpRequest.getQCloudSelfSigner();
        if (qCloudSelfSigner != null) {
            this.metrics.onSignRequestStart();
            qCloudSelfSigner.sign((QCloudHttpRequest) this.httpRequest);
            this.metrics.onSignRequestEnd();
        }
        return (QCloudHttpRequest) this.httpRequest;
    }

    public QCloudHttpRequest newRangeSignRequest(String str) throws QCloudClientException {
        this.httpRequest.removeHeader("Authorization");
        this.httpRequest.addOrReplaceHeader("Range", str);
        QCloudSigner qCloudSigner = this.httpRequest.getQCloudSigner();
        if (qCloudSigner != null) {
            this.metrics.onSignRequestStart();
            signRequest(qCloudSigner, (QCloudHttpRequest) this.httpRequest);
            this.metrics.onSignRequestEnd();
        }
        QCloudSelfSigner qCloudSelfSigner = this.httpRequest.getQCloudSelfSigner();
        if (qCloudSelfSigner != null) {
            this.metrics.onSignRequestStart();
            qCloudSelfSigner.sign((QCloudHttpRequest) this.httpRequest);
            this.metrics.onSignRequestEnd();
        }
        return (QCloudHttpRequest) this.httpRequest;
    }

    @Override // com.tencent.qcloud.core.task.QCloudTask
    public void cancel() {
        this.networkProxy.cancel();
        super.cancel();
    }

    @Override // com.tencent.qcloud.core.task.QCloudTask
    public void cancel(boolean z) {
        this.networkProxy.cancel();
        super.cancel(z);
    }

    private boolean isCompleteMultipartRequest(HttpRequest httpRequest) {
        Set<String> setKeySet = httpRequest.queries.keySet();
        return setKeySet != null && setKeySet.size() == 1 && setKeySet.contains("uploadId");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.qcloud.core.task.QCloudTask
    public HttpResult<T> execute() throws QCloudServiceException, QCloudClientException {
        HttpResult<T> httpResult;
        if (this.metrics == null) {
            this.metrics = new HttpTaskMetrics();
        }
        this.networkProxy.metrics = this.metrics;
        this.metrics.onTaskStart();
        if (this.httpRequest.shouldCalculateContentMD5()) {
            this.metrics.onCalculateMD5Start();
            calculateContentMD5();
            this.metrics.onCalculateMD5End();
        }
        if (this.httpRequest.getRequestBody() instanceof ReactiveBody) {
            try {
                ((ReactiveBody) this.httpRequest.getRequestBody()).prepare();
            } catch (IOException e) {
                throw new QCloudClientException(e);
            }
        }
        QCloudSigner qCloudSigner = this.httpRequest.getQCloudSigner();
        if (qCloudSigner != null) {
            this.metrics.onSignRequestStart();
            signRequest(qCloudSigner, (QCloudHttpRequest) this.httpRequest);
            this.metrics.onSignRequestEnd();
        }
        QCloudSelfSigner qCloudSelfSigner = this.httpRequest.getQCloudSelfSigner();
        if (qCloudSelfSigner != null) {
            this.metrics.onSignRequestStart();
            qCloudSelfSigner.sign((QCloudHttpRequest) this.httpRequest);
            this.metrics.onSignRequestEnd();
        }
        if (this.httpRequest.getRequestBody() instanceof ProgressBody) {
            ((ProgressBody) this.httpRequest.getRequestBody()).setProgressListener(this.mProgressListener);
        }
        try {
            try {
                this.metrics.onHttpTaskStart();
                this.httpResult = this.networkProxy.executeHttpRequest(this.httpRequest);
                this.metrics.onHttpTaskEnd();
                httpResult = this.httpResult;
            } catch (QCloudClientException e2) {
                if (!(e2.getCause() instanceof DomainSwitchException) || !isDomainSwitch() || qCloudSigner == null || qCloudSelfSigner != null) {
                    this.metrics.onHttpTaskEnd();
                    throw e2;
                }
                this.hasSwitchedDomain = true;
                String string = this.httpRequest.url.toString();
                if (DomainSwitchUtils.isMyqcloudCosUrl(this.httpRequest.url.getHost())) {
                    string = this.httpRequest.url.toString().replace(DomainSwitchUtils.DOMAIN_MYQCLOUD, DomainSwitchUtils.DOMAIN_TENCENTCOS);
                } else if (DomainSwitchUtils.isMyqcloudCiUrl(this.httpRequest.url.getHost())) {
                    string = this.httpRequest.url.toString().replace(DomainSwitchUtils.DOMAIN_MYQCLOUD, DomainSwitchUtils.DOMAIN_TENCENTCI);
                }
                this.httpRequest.setUrl(string);
                try {
                    this.httpRequest.addOrReplaceHeader("Host", new URL(string).getHost());
                } catch (MalformedURLException unused) {
                }
                this.httpRequest.addOrReplaceHeader(HttpConstants.Header.COS_SDK_RETRY, String.valueOf(true));
                this.metrics.onSignRequestStart();
                signRequest(qCloudSigner, (QCloudHttpRequest) this.httpRequest);
                this.metrics.onSignRequestEnd();
                this.metrics.onHttpTaskStart();
                this.httpResult = this.networkProxy.executeHttpRequest(this.httpRequest);
                this.metrics.onHttpTaskEnd();
                httpResult = this.httpResult;
                if (this.httpRequest.getRequestBody() instanceof ReactiveBody) {
                    try {
                        ((ReactiveBody) this.httpRequest.getRequestBody()).end(this.httpResult);
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                if (this.httpRequest.getRequestBody() instanceof StreamingRequestBody) {
                }
            } catch (QCloudServiceException e4) {
                if (!isClockSkewedError(e4)) {
                    this.metrics.onHttpTaskEnd();
                    throw e4;
                }
                this.metrics.setClockSkewedRetry(true);
                if (qCloudSigner != null) {
                    this.metrics.onSignRequestStart();
                    signRequest(qCloudSigner, (QCloudHttpRequest) this.httpRequest);
                    this.metrics.onSignRequestEnd();
                }
                this.metrics.onHttpTaskStart();
                this.httpResult = this.networkProxy.executeHttpRequest(this.httpRequest);
                this.metrics.onHttpTaskEnd();
                httpResult = this.httpResult;
                if (this.httpRequest.getRequestBody() instanceof ReactiveBody) {
                    try {
                        ((ReactiveBody) this.httpRequest.getRequestBody()).end(this.httpResult);
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                if (this.httpRequest.getRequestBody() instanceof StreamingRequestBody) {
                }
            }
            return httpResult;
        } finally {
            if (this.httpRequest.getRequestBody() instanceof ReactiveBody) {
                try {
                    ((ReactiveBody) this.httpRequest.getRequestBody()).end(this.httpResult);
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            if (this.httpRequest.getRequestBody() instanceof StreamingRequestBody) {
                ((StreamingRequestBody) this.httpRequest.getRequestBody()).release();
            }
            this.metrics.onTaskEnd();
        }
    }

    private boolean isClockSkewedError(QCloudServiceException qCloudServiceException) {
        return QCloudServiceException.ERR0R_REQUEST_IS_EXPIRED.equals(qCloudServiceException.getErrorCode()) || QCloudServiceException.ERR0R_REQUEST_TIME_TOO_SKEWED.equals(qCloudServiceException.getErrorCode());
    }

    private void signRequest(QCloudSigner qCloudSigner, QCloudHttpRequest qCloudHttpRequest) throws QCloudClientException {
        QCloudCredentials credentials;
        QCloudCredentialProvider qCloudCredentialProvider = this.credentialProvider;
        if (qCloudCredentialProvider == null) {
            throw new QCloudClientException(new QCloudAuthenticationException("no credentials provider"));
        }
        if (qCloudCredentialProvider instanceof ScopeLimitCredentialProvider) {
            credentials = ((ScopeLimitCredentialProvider) qCloudCredentialProvider).getCredentials(qCloudHttpRequest.getCredentialScope());
        } else {
            credentials = qCloudCredentialProvider.getCredentials();
        }
        qCloudSigner.sign(qCloudHttpRequest, credentials);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void calculateContentMD5() throws QCloudClientException {
        RequestBody requestBody = this.httpRequest.getRequestBody();
        if (requestBody == 0) {
            throw new QCloudClientException(new IllegalArgumentException("get md5 canceled, request body is null."));
        }
        if (requestBody instanceof QCloudDigistListener) {
            try {
                if (this.httpRequest.getRequestBody() instanceof MultipartStreamRequestBody) {
                    ((MultipartStreamRequestBody) this.httpRequest.getRequestBody()).addMd5();
                } else {
                    this.httpRequest.addHeader("Content-MD5", ((QCloudDigistListener) requestBody).onGetMd5());
                }
                return;
            } catch (IOException e) {
                throw new QCloudClientException("calculate md5 error: " + e.getMessage(), e);
            }
        }
        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
            this.httpRequest.addHeader("Content-MD5", buffer.md5().base64());
            buffer.close();
        } catch (IOException e2) {
            throw new QCloudClientException("calculate md5 error" + e2.getMessage(), e2);
        }
    }

    public void convertResponse(Response response) throws QCloudServiceException, QCloudClientException {
        this.httpResult = this.networkProxy.convertResponse(this.httpRequest, response);
    }
}
