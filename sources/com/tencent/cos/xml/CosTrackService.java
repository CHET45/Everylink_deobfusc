package com.tencent.cos.xml;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.common.VersionInfo;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.object.BasePutObjectRequest;
import com.tencent.cos.xml.model.object.GetObjectBytesRequest;
import com.tencent.cos.xml.model.object.GetObjectRequest;
import com.tencent.cos.xml.transfer.TransferTaskMetrics;
import com.tencent.cos.xml.utils.ThrowableUtils;
import com.tencent.qcloud.core.common.QCloudAuthenticationException;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpRequest;
import com.tencent.qcloud.core.http.HttpTask;
import com.tencent.qcloud.core.http.HttpTaskMetrics;
import com.tencent.qcloud.track.Constants;
import com.tencent.qcloud.track.QCloudTrackService;
import com.tencent.qcloud.track.cls.ClsLifecycleCredentialProvider;
import com.tencent.qcloud.track.service.BeaconTrackService;
import com.tencent.qcloud.track.service.ClsTrackService;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes4.dex */
public class CosTrackService {
    private static final String EVENT_CODE_NEW_TRANSFER = "qcloud_track_cos_sdk_transfer";
    private static final String EVENT_CODE_TRACK_COS_SDK = "qcloud_track_cos_sdk";
    private static final String EVENT_CODE_TRACK_COS_SDK_HTTP = "qcloud_track_cos_sdk_http";
    public static final String EVENT_CODE_TRACK_COS_SDK_SONAR = "qcloud_track_cos_sdk_sonar";
    public static final String EVENT_CODE_TRACK_COS_SDK_SONAR_FAILURE = "qcloud_track_cos_sdk_sonar_failure";
    private static final String EVENT_PARAMS_CLIENT = "Client";
    private static final String EVENT_PARAMS_FAILURE = "Failure";
    private static final String EVENT_PARAMS_SERVER = "Server";
    private static final String EVENT_PARAMS_SUCCESS = "Success";
    private static final boolean IS_DEBUG = false;
    private static final String SDK_NAME = "cos";
    private static final String TAG = "CosTrackService";
    private static CosTrackService instance;
    private String bridge;
    private boolean isCloseReport;
    private final CosTrackSonarService sonarService = new CosTrackSonarService();
    private final SonarHostsRandomQueue sonarHosts = new SonarHostsRandomQueue(3);

    private boolean isReport(CosXmlServiceException cosXmlServiceException) {
        return true;
    }

    public boolean isCloseReport() {
        return this.isCloseReport;
    }

    public CosTrackSonarService getSonarService() {
        return this.sonarService;
    }

    public SonarHostsRandomQueue getSonarHosts() {
        return this.sonarHosts;
    }

    private CosTrackService() {
    }

    public static void init(Context context, boolean z, String str) {
        synchronized (CosTrackService.class) {
            if (instance == null) {
                CosTrackService cosTrackService = new CosTrackService();
                instance = cosTrackService;
                cosTrackService.bridge = str;
                cosTrackService.isCloseReport = z;
                if (BeaconTrackService.isInclude()) {
                    BeaconTrackService beaconTrackService = new BeaconTrackService();
                    beaconTrackService.setContext(context);
                    beaconTrackService.init("0AND063SCYC5856Q");
                    QCloudTrackService.getInstance().addTrackService(EVENT_CODE_TRACK_COS_SDK, beaconTrackService);
                    QCloudTrackService.getInstance().addTrackService(EVENT_CODE_TRACK_COS_SDK_HTTP, beaconTrackService);
                    QCloudTrackService.getInstance().addTrackService(EVENT_CODE_TRACK_COS_SDK_SONAR, beaconTrackService);
                    QCloudTrackService.getInstance().addTrackService(EVENT_CODE_TRACK_COS_SDK_SONAR_FAILURE, beaconTrackService);
                    BeaconTrackService beaconTrackService2 = new BeaconTrackService();
                    beaconTrackService2.setContext(context);
                    beaconTrackService2.init("0AND05O9HW5YY29Z");
                    QCloudTrackService.getInstance().addTrackService(EVENT_CODE_NEW_TRANSFER, beaconTrackService2);
                }
                QCloudTrackService.getInstance().init(context);
                QCloudTrackService.getInstance().setDebug(false);
                QCloudTrackService.getInstance().setIsCloseReport(z);
                getInstance().reportSdkStart();
                getInstance().getSonarService().setContext(context);
                getInstance().getSonarService().periodicSonar();
            }
        }
    }

    public static void initCLs(Context context, String str, String str2) {
        if (!ClsTrackService.isInclude()) {
            throw new IllegalStateException("Please quote the cls library first: com.tencentcloudapi.cls:tencentcloud-cls-sdk-android:x.x.x");
        }
        ClsTrackService clsTrackService = new ClsTrackService();
        clsTrackService.init(context, str, str2);
        clsTrackService.setSecurityCredential("secretId", "secretKey");
        QCloudTrackService.getInstance().addTrackService(EVENT_CODE_TRACK_COS_SDK, clsTrackService);
    }

    public static void initCLs(Context context, String str, String str2, String str3, String str4) {
        if (!ClsTrackService.isInclude()) {
            throw new IllegalStateException("Please quote the cls library first: com.tencentcloudapi.cls:tencentcloud-cls-sdk-android:x.x.x");
        }
        ClsTrackService clsTrackService = new ClsTrackService();
        clsTrackService.init(context, str, str2);
        clsTrackService.setSecurityCredential(str3, str4);
        QCloudTrackService.getInstance().addTrackService(EVENT_CODE_TRACK_COS_SDK, clsTrackService);
    }

    public static void initCLs(Context context, String str, String str2, ClsLifecycleCredentialProvider clsLifecycleCredentialProvider) {
        if (!ClsTrackService.isInclude()) {
            throw new IllegalStateException("Please quote the cls library first: com.tencentcloudapi.cls:tencentcloud-cls-sdk-android:x.x.x");
        }
        ClsTrackService clsTrackService = new ClsTrackService();
        clsTrackService.init(context, str, str2);
        clsTrackService.setCredentialProvider(clsLifecycleCredentialProvider);
        QCloudTrackService.getInstance().addTrackService(EVENT_CODE_TRACK_COS_SDK, clsTrackService);
    }

    public static CosTrackService getInstance() {
        return instance;
    }

    public void reportSdkStart() {
        HashMap map = new HashMap();
        try {
            map.put("sdk_name", SDK_NAME);
            map.putAll(getCommonParams());
            QCloudTrackService.getInstance().reportSimpleData(Constants.SIMPLE_DATA_EVENT_CODE_START, map);
        } catch (Exception unused) {
        }
    }

    public void reportError(String str, Exception exc) {
        if (exc == null) {
            return;
        }
        HashMap map = new HashMap();
        try {
            Throwable rootCause = ThrowableUtils.getRootCause(exc);
            map.put("sdk_name", SDK_NAME);
            map.put("qcloud_error_source", str);
            map.put("qcloud_error_name", rootCause.getClass().getSimpleName());
            map.put("qcloud_error_message", rootCause.getMessage());
            map.putAll(getCommonParams());
            QCloudTrackService.getInstance().reportSimpleData(Constants.SIMPLE_DATA_EVENT_CODE_ERROR, map);
        } catch (Exception unused) {
        }
    }

    public void reportHttpMetrics(CosXmlRequest cosXmlRequest) {
        if (cosXmlRequest == null || cosXmlRequest.getMetrics() == null) {
            return;
        }
        if (cosXmlRequest.getMetrics().httpTaskFullTime() == 0.0d && cosXmlRequest.getMetrics().dnsLookupTookTime() == 0.0d && cosXmlRequest.getMetrics().connectTookTime() == 0.0d && cosXmlRequest.getMetrics().secureConnectTookTime() == 0.0d) {
            return;
        }
        try {
            Map<String, String> cosXmlRequestBaseParams = parseCosXmlRequestBaseParams(cosXmlRequest);
            cosXmlRequestBaseParams.putAll(parseHttpTaskMetricsBaseParams(cosXmlRequest.getMetrics(), getRequestName(cosXmlRequest)));
            cosXmlRequestBaseParams.putAll(getCommonParams());
            if (!TextUtils.isEmpty(cosXmlRequest.getClientTraceId())) {
                cosXmlRequestBaseParams.put("client_trace_id", cosXmlRequest.getClientTraceId());
            }
            QCloudTrackService.getInstance().report(EVENT_CODE_TRACK_COS_SDK_HTTP, cosXmlRequestBaseParams);
        } catch (Exception unused) {
        }
    }

    public void reportRequestSuccess(CosXmlRequest cosXmlRequest, boolean z, Map<String, String> map) {
        HashMap map2 = new HashMap();
        map2.put("request_name", getRequestName(cosXmlRequest));
        map2.putAll(map);
        reportRequestSuccess(cosXmlRequest, map2, z);
    }

    public CosXmlClientException reportRequestClientException(CosXmlRequest cosXmlRequest, QCloudClientException qCloudClientException, boolean z, Map<String, String> map) {
        HashMap map2 = new HashMap();
        map2.put("request_name", getRequestName(cosXmlRequest));
        map2.putAll(map);
        return reportClientException(cosXmlRequest, qCloudClientException, map2, z);
    }

    public CosXmlServiceException reportRequestServiceException(CosXmlRequest cosXmlRequest, QCloudServiceException qCloudServiceException, boolean z, Map<String, String> map) {
        HashMap map2 = new HashMap();
        map2.put("request_name", getRequestName(cosXmlRequest));
        map2.putAll(map);
        return reportServiceException(cosXmlRequest, qCloudServiceException, map2, z);
    }

    public void reportUploadTaskSuccess(CosXmlRequest cosXmlRequest, Map<String, String> map) {
        HashMap map2 = new HashMap();
        map2.put("request_name", "UploadTask");
        map2.putAll(map);
        reportRequestSuccess(cosXmlRequest, (Map<String, String>) map2, false);
    }

    public void reportUploadTaskClientException(CosXmlRequest cosXmlRequest, QCloudClientException qCloudClientException, Map<String, String> map) {
        Map<String, String> mapCreateTransferExtra = createTransferExtra("UploadTask", cosXmlRequest);
        mapCreateTransferExtra.putAll(map);
        reportClientException(cosXmlRequest, qCloudClientException, mapCreateTransferExtra, false);
    }

    public void reportUploadTaskServiceException(CosXmlRequest cosXmlRequest, QCloudServiceException qCloudServiceException, Map<String, String> map) {
        Map<String, String> mapCreateTransferExtra = createTransferExtra("UploadTask", cosXmlRequest);
        mapCreateTransferExtra.putAll(map);
        reportServiceException(cosXmlRequest, qCloudServiceException, mapCreateTransferExtra, false);
    }

    public void reportDownloadTaskSuccess(CosXmlRequest cosXmlRequest, Map<String, String> map) {
        HashMap map2 = new HashMap();
        map2.put("request_name", "DownloadTask");
        map2.putAll(map);
        reportRequestSuccess(cosXmlRequest, (Map<String, String>) map2, false);
    }

    public void reportDownloadTaskClientException(CosXmlRequest cosXmlRequest, QCloudClientException qCloudClientException, Map<String, String> map) {
        Map<String, String> mapCreateTransferExtra = createTransferExtra("DownloadTask", cosXmlRequest);
        mapCreateTransferExtra.putAll(map);
        reportClientException(cosXmlRequest, qCloudClientException, mapCreateTransferExtra, false);
    }

    public void reportDownloadTaskServiceException(CosXmlRequest cosXmlRequest, QCloudServiceException qCloudServiceException, Map<String, String> map) {
        Map<String, String> mapCreateTransferExtra = createTransferExtra("DownloadTask", cosXmlRequest);
        mapCreateTransferExtra.putAll(map);
        reportServiceException(cosXmlRequest, qCloudServiceException, mapCreateTransferExtra, false);
    }

    public void reportCopyTaskSuccess(CosXmlRequest cosXmlRequest, Map<String, String> map) {
        HashMap map2 = new HashMap();
        map2.put("request_name", "CopyTask");
        map2.putAll(map);
        reportRequestSuccess(cosXmlRequest, (Map<String, String>) map2, false);
    }

    public void reportCopyTaskClientException(CosXmlRequest cosXmlRequest, CosXmlClientException cosXmlClientException, Map<String, String> map) {
        Map<String, String> mapCreateTransferExtra = createTransferExtra("CopyTask", cosXmlRequest);
        mapCreateTransferExtra.putAll(map);
        reportClientException(cosXmlRequest, cosXmlClientException, mapCreateTransferExtra, false);
    }

    public void reportCopyTaskServiceException(CosXmlRequest cosXmlRequest, CosXmlServiceException cosXmlServiceException, Map<String, String> map) {
        Map<String, String> mapCreateTransferExtra = createTransferExtra("CopyTask", cosXmlRequest);
        mapCreateTransferExtra.putAll(map);
        reportServiceException(cosXmlRequest, cosXmlServiceException, mapCreateTransferExtra, false);
    }

    private void reportRequestSuccess(CosXmlRequest cosXmlRequest, Map<String, String> map, boolean z) {
        if (z) {
            return;
        }
        try {
            HttpTaskMetrics metrics = cosXmlRequest.getMetrics();
            Map<String, String> cosXmlRequestParams = parseCosXmlRequestParams(cosXmlRequest);
            cosXmlRequestParams.putAll(getCommonParams());
            if (map == null || !map.containsKey("request_name")) {
                cosXmlRequestParams.put("request_name", cosXmlRequest.getClass().getSimpleName());
            }
            cosXmlRequestParams.put("request_result", EVENT_PARAMS_SUCCESS);
            if (map != null) {
                cosXmlRequestParams.putAll(map);
            }
            cosXmlRequestParams.putAll(parseHttpTaskMetricsParams(metrics, cosXmlRequestParams.get("request_name")));
            if (!TextUtils.isEmpty(cosXmlRequest.getClientTraceId())) {
                cosXmlRequestParams.put("client_trace_id", cosXmlRequest.getClientTraceId());
            }
            QCloudTrackService.getInstance().report(EVENT_CODE_TRACK_COS_SDK, cosXmlRequestParams);
            this.sonarHosts.add(new SonarHost(cosXmlRequestParams.get("host"), cosXmlRequestParams.get("region"), cosXmlRequestParams.get("bucket")));
        } catch (Exception unused) {
        }
    }

    private CosXmlClientException reportClientException(CosXmlRequest cosXmlRequest, QCloudClientException qCloudClientException, Map<String, String> map, boolean z) {
        CosTrackSonarService cosTrackSonarService;
        ReturnClientException clientExceptionParams = getClientExceptionParams(qCloudClientException);
        if (!z) {
            try {
                if (isReport(clientExceptionParams.exception)) {
                    HttpTaskMetrics metrics = cosXmlRequest.getMetrics();
                    Map<String, String> cosXmlRequestParams = parseCosXmlRequestParams(cosXmlRequest);
                    cosXmlRequestParams.putAll(getCommonParams());
                    cosXmlRequestParams.putAll(clientExceptionParams.params);
                    if (map == null || !map.containsKey("request_name")) {
                        cosXmlRequestParams.put("request_name", cosXmlRequest.getClass().getSimpleName());
                    }
                    cosXmlRequestParams.put("request_result", EVENT_PARAMS_FAILURE);
                    if (map != null) {
                        cosXmlRequestParams.putAll(map);
                    }
                    cosXmlRequestParams.putAll(parseHttpTaskMetricsParams(metrics, cosXmlRequestParams.get("request_name")));
                    if (!TextUtils.isEmpty(cosXmlRequest.getClientTraceId())) {
                        cosXmlRequestParams.put("client_trace_id", cosXmlRequest.getClientTraceId());
                    }
                    QCloudTrackService.getInstance().report(EVENT_CODE_TRACK_COS_SDK, cosXmlRequestParams);
                    if ((clientExceptionParams.exception.errorCode == ClientErrorCode.POOR_NETWORK.getCode() || clientExceptionParams.exception.errorCode == ClientErrorCode.IO_ERROR.getCode()) && (cosTrackSonarService = this.sonarService) != null) {
                        cosTrackSonarService.failSonar(cosXmlRequestParams.get("host"), cosXmlRequestParams.get("region"), cosXmlRequestParams.get("bucket"), cosXmlRequest.getClientTraceId());
                    }
                }
            } catch (Exception unused) {
            }
        }
        return clientExceptionParams.exception;
    }

    private CosXmlServiceException reportServiceException(CosXmlRequest cosXmlRequest, QCloudServiceException qCloudServiceException, Map<String, String> map, boolean z) {
        CosTrackSonarService cosTrackSonarService;
        ReturnServiceException serviceExceptionParams = getServiceExceptionParams(qCloudServiceException);
        if (!z) {
            try {
                if (isReport(serviceExceptionParams.exception)) {
                    Map<String, String> cosXmlRequestParams = parseCosXmlRequestParams(cosXmlRequest);
                    cosXmlRequestParams.putAll(getCommonParams());
                    cosXmlRequestParams.putAll(serviceExceptionParams.params);
                    if (map == null || !map.containsKey("request_name")) {
                        cosXmlRequestParams.put("request_name", cosXmlRequest.getClass().getSimpleName());
                    }
                    cosXmlRequestParams.put("request_result", EVENT_PARAMS_FAILURE);
                    if (map != null) {
                        cosXmlRequestParams.putAll(map);
                    }
                    cosXmlRequestParams.putAll(parseHttpTaskMetricsParams(cosXmlRequest.getMetrics(), cosXmlRequestParams.get("request_name")));
                    if (!TextUtils.isEmpty(cosXmlRequest.getClientTraceId())) {
                        cosXmlRequestParams.put("client_trace_id", cosXmlRequest.getClientTraceId());
                    }
                    QCloudTrackService.getInstance().report(EVENT_CODE_TRACK_COS_SDK, cosXmlRequestParams);
                    if (serviceExceptionParams.exception != null && (("RequestTimeout".equals(serviceExceptionParams.exception.getErrorCode()) || "UserNetworkTooSlow".equals(serviceExceptionParams.exception.getErrorCode())) && (cosTrackSonarService = this.sonarService) != null)) {
                        cosTrackSonarService.failSonar(cosXmlRequestParams.get("host"), cosXmlRequestParams.get("region"), cosXmlRequestParams.get("bucket"), cosXmlRequest.getClientTraceId());
                    }
                }
            } catch (Exception unused) {
            }
        }
        return serviceExceptionParams.exception;
    }

    private Map<String, String> parseHttpTaskMetricsBaseParams(HttpTaskMetrics httpTaskMetrics, String str) {
        HashMap map = new HashMap();
        if (httpTaskMetrics == null) {
            return map;
        }
        map.put("http_took_time", String.valueOf(httpTaskMetrics.httpTaskFullTime()));
        map.put("http_dns", String.valueOf(httpTaskMetrics.dnsLookupTookTime()));
        map.put("http_connect", String.valueOf(httpTaskMetrics.connectTookTime()));
        map.put("http_secure_connect", String.valueOf(httpTaskMetrics.secureConnectTookTime()));
        map.put("http_read_header", String.valueOf(httpTaskMetrics.readResponseHeaderTookTime()));
        map.put("http_read_body", String.valueOf(httpTaskMetrics.readResponseBodyTookTime()));
        map.put("http_write_header", String.valueOf(httpTaskMetrics.writeRequestHeaderTookTime()));
        map.put("http_write_body", String.valueOf(httpTaskMetrics.writeRequestBodyTookTime()));
        if ("UploadTask".equalsIgnoreCase(str) || "CopyTask".equalsIgnoreCase(str)) {
            map.put("http_size", String.valueOf(httpTaskMetrics.requestBodyByteCount()));
            map.put("http_speed", String.valueOf((httpTaskMetrics.requestBodyByteCount() / 1024.0d) / httpTaskMetrics.httpTaskFullTime()));
        } else if ("DownloadTask".equalsIgnoreCase(str)) {
            map.put("http_size", String.valueOf(httpTaskMetrics.responseBodyByteCount()));
            map.put("http_speed", String.valueOf((httpTaskMetrics.responseBodyByteCount() / 1024.0d) / httpTaskMetrics.httpTaskFullTime()));
        } else {
            map.put("http_size", String.valueOf(httpTaskMetrics.requestBodyByteCount() + httpTaskMetrics.responseBodyByteCount()));
            map.put("http_speed", String.valueOf(((httpTaskMetrics.requestBodyByteCount() + httpTaskMetrics.responseBodyByteCount()) / 1024.0d) / httpTaskMetrics.httpTaskFullTime()));
        }
        map.put("http_connect_ip", httpTaskMetrics.getConnectAddress() != null ? httpTaskMetrics.getConnectAddress().getHostAddress() : "null");
        map.put("http_dns_ips", httpTaskMetrics.getRemoteAddress() != null ? httpTaskMetrics.getRemoteAddress().toString() : "null");
        return map;
    }

    private Map<String, String> parseHttpTaskMetricsParams(HttpTaskMetrics httpTaskMetrics, String str) {
        HashMap map = new HashMap();
        if (httpTaskMetrics == null) {
            return map;
        }
        map.putAll(parseHttpTaskMetricsBaseParams(httpTaskMetrics, str));
        map.put("http_md5", String.valueOf(httpTaskMetrics.calculateMD5STookTime()));
        map.put("http_sign", String.valueOf(httpTaskMetrics.signRequestTookTime()));
        map.put("http_full_time", String.valueOf(httpTaskMetrics.fullTaskTookTime()));
        map.put("http_retry_times", String.valueOf(httpTaskMetrics.getRetryCount()));
        return map;
    }

    private Map<String, String> parseCosXmlRequestBaseParams(CosXmlRequest cosXmlRequest) {
        String protocol;
        HashMap map = new HashMap();
        if (cosXmlRequest == null) {
            return map;
        }
        map.put("bucket", cosXmlRequest.getBucket());
        map.put("region", cosXmlRequest.getRegion());
        if (cosXmlRequest.getHttpTask() != null && cosXmlRequest.getHttpTask().request() != null) {
            String strHeader = cosXmlRequest.getHttpTask().request().header("User-Agent");
            if (!TextUtils.isEmpty(strHeader) && strHeader.contains("android-quic-sdk")) {
                protocol = "quic";
            } else {
                protocol = cosXmlRequest.getHttpTask().request().url().getProtocol();
            }
            map.put("network_protocol", protocol);
            map.put("http_method", cosXmlRequest.getHttpTask().request().method());
        }
        String host = parseHost(cosXmlRequest);
        if (!TextUtils.isEmpty(host)) {
            map.put("host", host);
            try {
                Matcher matcher = Pattern.compile(".*\\.cos\\.(.*)\\.myqcloud.com").matcher(host);
                if (matcher.find()) {
                    String strGroup = matcher.group(1);
                    if (!TextUtils.isEmpty(strGroup)) {
                        map.put("region", strGroup);
                    }
                }
            } catch (Exception unused) {
            }
        }
        return map;
    }

    private Map<String, String> parseCosXmlRequestParams(CosXmlRequest cosXmlRequest) {
        HashMap map = new HashMap();
        if (cosXmlRequest == null) {
            return map;
        }
        map.putAll(parseCosXmlRequestBaseParams(cosXmlRequest));
        if (cosXmlRequest.getHttpTask() != null && cosXmlRequest.getHttpTask().request() != null) {
            String strHeader = cosXmlRequest.getHttpTask().request().header("User-Agent");
            if (!TextUtils.isEmpty(strHeader)) {
                map.put("user_agent", strHeader);
            }
        }
        return map;
    }

    private Map<String, String> getCommonParams() {
        HashMap map = new HashMap();
        map.put("sdk_version_name", VersionInfo.getVersionName());
        map.put("sdk_version_code", String.valueOf(VersionInfo.getVersionCode()));
        if (!TextUtils.isEmpty(this.bridge)) {
            map.put("sdk_bridge", this.bridge);
        }
        return map;
    }

    private String parseHost(CosXmlRequest cosXmlRequest) {
        HttpRequest httpRequestRequest;
        HttpTask httpTask = cosXmlRequest.getHttpTask();
        if (httpTask == null || (httpRequestRequest = httpTask.request()) == null) {
            return null;
        }
        return httpRequestRequest.host();
    }

    private static String getRequestName(CosXmlRequest cosXmlRequest) {
        if (cosXmlRequest instanceof BasePutObjectRequest) {
            return "UploadTask";
        }
        if ((cosXmlRequest instanceof GetObjectRequest) || (cosXmlRequest instanceof GetObjectBytesRequest)) {
            return "DownloadTask";
        }
        if ("CopyObjectRequest".equalsIgnoreCase(cosXmlRequest.getClass().getSimpleName())) {
            return "CopyTask";
        }
        return cosXmlRequest.getClass().getSimpleName();
    }

    private ReturnServiceException getServiceExceptionParams(QCloudServiceException qCloudServiceException) {
        String strValueOf;
        String strValueOf2;
        HashMap map = new HashMap();
        CosXmlServiceException cosXmlServiceExceptionConvertServerException = convertServerException(qCloudServiceException);
        if (!TextUtils.isEmpty(cosXmlServiceExceptionConvertServerException.getErrorCode()) && !cosXmlServiceExceptionConvertServerException.getErrorCode().equals("null")) {
            strValueOf = cosXmlServiceExceptionConvertServerException.getErrorCode();
        } else {
            strValueOf = String.valueOf(cosXmlServiceExceptionConvertServerException.getStatusCode());
        }
        map.put("error_code", strValueOf);
        if (!TextUtils.isEmpty(cosXmlServiceExceptionConvertServerException.getErrorMessage()) && !cosXmlServiceExceptionConvertServerException.getErrorMessage().equals("null")) {
            strValueOf2 = cosXmlServiceExceptionConvertServerException.getErrorMessage();
        } else {
            strValueOf2 = String.valueOf(cosXmlServiceExceptionConvertServerException.getHttpMessage());
        }
        map.put("error_message", strValueOf2);
        map.put("error_http_code", String.valueOf(cosXmlServiceExceptionConvertServerException.getStatusCode()));
        map.put("error_http_message", cosXmlServiceExceptionConvertServerException.getHttpMessage());
        map.put("error_service_name", cosXmlServiceExceptionConvertServerException.getServiceName());
        map.put("error_request_id", cosXmlServiceExceptionConvertServerException.getRequestId());
        map.put("error_type", "Server");
        return new ReturnServiceException(cosXmlServiceExceptionConvertServerException, map);
    }

    private static class ReturnServiceException {
        private final CosXmlServiceException exception;
        private final Map<String, String> params;

        public ReturnServiceException(CosXmlServiceException cosXmlServiceException, Map<String, String> map) {
            this.exception = cosXmlServiceException;
            this.params = map;
        }
    }

    private ReturnClientException getClientExceptionParams(QCloudClientException qCloudClientException) {
        HashMap map = new HashMap();
        CosXmlClientException cosXmlClientExceptionConvertClientException = convertClientException(qCloudClientException);
        Throwable rootCause = ThrowableUtils.getRootCause(cosXmlClientExceptionConvertClientException);
        map.put("error_code", String.valueOf(cosXmlClientExceptionConvertClientException.errorCode));
        map.put("error_name", rootCause.getClass().getSimpleName());
        map.put("error_message", rootCause.getMessage());
        map.put("error_type", EVENT_PARAMS_CLIENT);
        return new ReturnClientException(cosXmlClientExceptionConvertClientException, map);
    }

    private static class ReturnClientException {
        private final CosXmlClientException exception;
        private final Map<String, String> params;

        public ReturnClientException(CosXmlClientException cosXmlClientException, Map<String, String> map) {
            this.exception = cosXmlClientException;
            this.params = map;
        }
    }

    public CosXmlServiceException convertServerException(QCloudServiceException qCloudServiceException) {
        return qCloudServiceException instanceof CosXmlServiceException ? (CosXmlServiceException) qCloudServiceException : new CosXmlServiceException(qCloudServiceException);
    }

    public CosXmlClientException convertClientException(QCloudClientException qCloudClientException) {
        CosXmlClientException cosXmlClientException;
        if (qCloudClientException instanceof CosXmlClientException) {
            return (CosXmlClientException) qCloudClientException;
        }
        Throwable rootCause = ThrowableUtils.getRootCause(qCloudClientException);
        if (rootCause instanceof IllegalArgumentException) {
            cosXmlClientException = new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), qCloudClientException);
        } else if (rootCause instanceof QCloudAuthenticationException) {
            cosXmlClientException = new CosXmlClientException(ClientErrorCode.INVALID_CREDENTIALS.getCode(), qCloudClientException);
        } else if (rootCause instanceof IOException) {
            cosXmlClientException = new CosXmlClientException(ClientErrorCode.IO_ERROR.getCode(), qCloudClientException);
        } else {
            cosXmlClientException = new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), qCloudClientException);
        }
        return cosXmlClientException;
    }

    private boolean isReport(CosXmlClientException cosXmlClientException) {
        return !(cosXmlClientException.getMessage() != null && cosXmlClientException.getMessage().toLowerCase(Locale.ROOT).contains("canceled"));
    }

    public static class SonarHostsRandomQueue {
        private final int maxSize;
        private long sonarHostsAddTimestamp;
        private final List<SonarHost> list = new LinkedList();
        private final Random random = new Random();

        public SonarHostsRandomQueue(int i) {
            this.maxSize = i;
        }

        public void add(SonarHost sonarHost) {
            if (this.list.size() >= this.maxSize) {
                this.list.remove(0);
            }
            this.list.add(sonarHost);
            this.sonarHostsAddTimestamp = System.currentTimeMillis();
        }

        public long getSonarHostsAddTimestamp() {
            return this.sonarHostsAddTimestamp;
        }

        public SonarHost get() {
            if (this.list.isEmpty()) {
                return null;
            }
            return this.list.get(this.random.nextInt(this.list.size()));
        }
    }

    public static class SonarHost {
        private final String bucket;
        private final String host;
        private final String region;

        public SonarHost(String str, String str2, String str3) {
            this.host = str;
            this.region = str2;
            this.bucket = str3;
        }

        public String getHost() {
            return this.host;
        }

        public String getRegion() {
            return this.region;
        }

        public String getBucket() {
            return this.bucket;
        }
    }

    private Map<String, String> createTransferExtra(String str, CosXmlRequest cosXmlRequest) {
        HashMap map = new HashMap();
        map.put("request_name", str);
        map.put("error_node", cosXmlRequest != null ? cosXmlRequest.getClass().getSimpleName() : "null");
        return map;
    }

    public void reportTransferSuccess(CosXmlRequest cosXmlRequest, TransferTaskMetrics transferTaskMetrics, boolean z) {
        reportTransferTask(cosXmlRequest, transferTaskMetrics, z, true, null);
    }

    public void reportTransferClientException(CosXmlRequest cosXmlRequest, TransferTaskMetrics transferTaskMetrics, CosXmlClientException cosXmlClientException, boolean z) {
        if (isReport(cosXmlClientException)) {
            reportTransferTask(cosXmlRequest, transferTaskMetrics, z, false, parseClientExceptionParams(cosXmlClientException));
        }
    }

    public void reportTransferServiceException(CosXmlRequest cosXmlRequest, TransferTaskMetrics transferTaskMetrics, CosXmlServiceException cosXmlServiceException, boolean z) {
        if (isReport(cosXmlServiceException)) {
            reportTransferTask(cosXmlRequest, transferTaskMetrics, z, false, parseServiceExceptionParams(cosXmlServiceException));
        }
    }

    private void reportTransferTask(CosXmlRequest cosXmlRequest, TransferTaskMetrics transferTaskMetrics, boolean z, boolean z2, Map<String, String> map) {
        try {
            Map<String, String> cosXmlRequestParams = parseCosXmlRequestParams(cosXmlRequest);
            cosXmlRequestParams.put("request_name", cosXmlRequest.getClass().getSimpleName());
            cosXmlRequestParams.putAll(getCommonParams());
            cosXmlRequestParams.putAll(parseSimplePerfParams(transferTaskMetrics));
            cosXmlRequestParams.put("encrypted", String.valueOf(z));
            cosXmlRequestParams.put("request_result", z2 ? EVENT_PARAMS_SUCCESS : EVENT_PARAMS_FAILURE);
            if (map != null) {
                cosXmlRequestParams.putAll(map);
            }
            QCloudTrackService.getInstance().report(EVENT_CODE_NEW_TRANSFER, cosXmlRequestParams);
        } catch (Exception unused) {
        }
    }

    private Map<String, String> parseSimplePerfParams(TransferTaskMetrics transferTaskMetrics) {
        HashMap map = new HashMap();
        if (transferTaskMetrics == null) {
            return map;
        }
        map.put("transfer_size", String.valueOf(transferTaskMetrics.getSize()));
        map.put("ip", transferTaskMetrics.getConnectAddress() != null ? transferTaskMetrics.getConnectAddress().getHostAddress() : "");
        map.put("took_time", String.valueOf(transferTaskMetrics.getTookTime()));
        map.put("wait_took_time", String.valueOf(transferTaskMetrics.getWaitTookTime()));
        map.put("first_progress_took_time", String.valueOf(transferTaskMetrics.getFirstProgressTookTime()));
        return map;
    }

    private Map<String, String> parseClientExceptionParams(CosXmlClientException cosXmlClientException) {
        HashMap map = new HashMap();
        map.put("error_message", cosXmlClientException.getMessage());
        map.put("error_code", String.valueOf(cosXmlClientException.errorCode));
        map.put("error_type", EVENT_PARAMS_CLIENT);
        return map;
    }

    private Map<String, String> parseServiceExceptionParams(CosXmlServiceException cosXmlServiceException) {
        HashMap map = new HashMap();
        map.put("error_message", cosXmlServiceException.getErrorMessage());
        map.put("error_code", cosXmlServiceException.getErrorCode());
        map.put("request_id", cosXmlServiceException.getRequestId());
        map.put("error_type", "Server");
        return map;
    }
}
