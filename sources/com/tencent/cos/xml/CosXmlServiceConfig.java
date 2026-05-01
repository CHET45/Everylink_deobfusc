package com.tencent.cos.xml;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.aivox.common.socket.WebSocketHandler;
import com.tencent.qcloud.core.http.NetworkClient;
import com.tencent.qcloud.core.http.QCloudHttpRetryHandler;
import com.tencent.qcloud.core.task.RetryStrategy;
import com.tencent.qcloud.core.task.TaskExecutors;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes4.dex */
public class CosXmlServiceConfig implements Parcelable {
    public static final String ACCELERATE_ENDPOINT_SUFFIX = "cos.accelerate";
    public static final String ACCELERATE_HOST_FORMAT = "${bucket}.cos.accelerate.myqcloud.com";
    public static final String CI_APPID_HOST_FORMAT = "${appid}.ci.${region}.myqcloud.com";
    public static final String CI_HOST_FORMAT = "${bucket}.ci.${region}.myqcloud.com";
    public static final String CI_REGION_HOST_FORMAT = "ci.${region}.myqcloud.com";
    public static final Parcelable.Creator<CosXmlServiceConfig> CREATOR = new Parcelable.Creator<CosXmlServiceConfig>() { // from class: com.tencent.cos.xml.CosXmlServiceConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CosXmlServiceConfig createFromParcel(Parcel parcel) {
            return new CosXmlServiceConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CosXmlServiceConfig[] newArray(int i) {
            return new CosXmlServiceConfig[i];
        }
    };
    public static final String DEFAULT_HOST_FORMAT = "${bucket}.cos.${region}.myqcloud.com";
    public static final String HTTPS_PROTOCOL = "https";
    public static final String HTTP_PROTOCOL = "http";
    public static final String PATH_STYLE_HOST_FORMAT = "cos.${region}.myqcloud.com";
    public static final String PIC_HOST_FORMAT = "${bucket}.pic.${region}.myqcloud.com";
    private final boolean accelerate;
    private final String appid;
    private final boolean bucketInPath;
    private final byte[] clientCertificateBytes;
    private final char[] clientCertificatePassword;
    private final Map<String, List<String>> commonHeaders;
    private final int connectionTimeout;
    private final NetworkClient customizeNetworkClient;
    private final boolean dnsCache;
    private final boolean domainSwitch;
    private final int downloadMaxThreadCount;
    private final String endpointSuffix;
    private final Executor executor;
    private final String host;
    private String hostFormat;
    private String hostHeaderFormat;
    private final boolean isDebuggable;
    private final boolean isQuic;
    private final RequestNetworkStrategy networkSwitchStrategy;
    private final Set<String> noSignHeaders;
    private final Executor observeExecutor;
    private final int port;
    private List<String> prefetchHosts;
    private final String protocol;
    private final QCloudHttpRetryHandler qCloudHttpRetryHandler;
    private final boolean redirectEnable;
    private final String region;
    private final RetryStrategy retryStrategy;
    private final boolean signInUrl;
    private final int socketTimeout;
    private final boolean transferThreadControl;
    private final int uploadMaxThreadCount;
    private final String userAgentExtended;
    private final boolean verifySSLEnable;

    public enum RequestNetworkStrategy {
        Aggressive,
        Conservative
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CosXmlServiceConfig(Builder builder) {
        this.hostFormat = DEFAULT_HOST_FORMAT;
        this.hostHeaderFormat = null;
        this.protocol = builder.protocol;
        this.userAgentExtended = builder.userAgentExtended;
        this.isDebuggable = builder.isDebuggable;
        this.appid = builder.appid;
        String str = builder.region;
        this.region = str;
        String str2 = builder.host;
        this.host = str2;
        this.port = builder.port;
        this.endpointSuffix = builder.endpointSuffix;
        this.bucketInPath = builder.bucketInPath;
        this.commonHeaders = builder.commonHeaders;
        this.noSignHeaders = builder.noSignHeaders;
        if (TextUtils.isEmpty(this.hostFormat) && TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("please set host or endpointSuffix or region !");
        }
        this.retryStrategy = builder.retryStrategy;
        this.qCloudHttpRetryHandler = builder.qCloudHttpRetryHandler;
        this.socketTimeout = builder.socketTimeout;
        this.connectionTimeout = builder.connectionTimeout;
        this.hostFormat = builder.hostFormat;
        this.hostHeaderFormat = builder.hostHeaderFormat;
        this.executor = builder.executor;
        this.observeExecutor = builder.observeExecutor;
        this.isQuic = builder.isQuic;
        this.networkSwitchStrategy = builder.networkSwitchStrategy;
        this.accelerate = builder.accelerate;
        this.dnsCache = builder.dnsCache;
        this.signInUrl = builder.signInUrl;
        this.transferThreadControl = builder.transferThreadControl;
        this.uploadMaxThreadCount = builder.uploadMaxThreadCount;
        this.downloadMaxThreadCount = builder.downloadMaxThreadCount;
        this.domainSwitch = builder.domainSwitch;
        this.verifySSLEnable = builder.verifySSLEnable;
        this.clientCertificateBytes = builder.clientCertificateBytes;
        this.clientCertificatePassword = builder.clientCertificatePassword;
        this.redirectEnable = builder.redirectEnable;
        this.customizeNetworkClient = builder.customizeNetworkClient;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getRegion() {
        return this.region;
    }

    public String getBucket(String str) {
        return getBucket(str, this.appid);
    }

    public String getBucket(String str, String str2) {
        return (str == null || str.endsWith(new StringBuilder("-").append(str2).toString()) || TextUtils.isEmpty(str2)) ? str : str + "-" + str2;
    }

    public Set<String> getNoSignHeaders() {
        return this.noSignHeaders;
    }

    public String getAppid() {
        return this.appid;
    }

    public String getRequestHost(String str, boolean z) {
        return getRequestHost((String) null, str, z);
    }

    public String getRequestHost(String str, String str2, boolean z) {
        return getRequestHost(str, str2, getHostFormat(z || this.accelerate, this.bucketInPath));
    }

    public String getRequestHost(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(this.host)) {
            return this.host;
        }
        if (TextUtils.isEmpty(str)) {
            str = this.region;
        }
        return getFormatHost(str3, str, getBucket(str2, this.appid));
    }

    public String getRequestHostByAppId(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(this.host)) {
            return this.host;
        }
        if (TextUtils.isEmpty(str)) {
            str = this.region;
        }
        return getFormatHostByAppId(str3, str, str2);
    }

    public String getRequestHost(String str, String str2) {
        if (!TextUtils.isEmpty(this.host)) {
            return this.host;
        }
        if (TextUtils.isEmpty(str)) {
            str = this.region;
        }
        return getFormatHost(str2, str);
    }

    public String getHeaderHost(String str, String str2) {
        String str3 = this.hostHeaderFormat;
        if (str3 != null) {
            return getFormatHost(str3, str, str2);
        }
        return "";
    }

    public boolean isSignInUrl() {
        return this.signInUrl;
    }

    @Deprecated
    public String getDefaultRequestHost(String str, String str2, String str3) {
        return getDefaultRequestHost(str, getBucket(str2, str3));
    }

    public String getDefaultRequestHost(String str, String str2) {
        return getFormatHost(DEFAULT_HOST_FORMAT, str, str2);
    }

    private String getFormatHost(String str, String str2, String str3) {
        if (str2 == null) {
            throw new IllegalArgumentException("please set request or config region !");
        }
        if (str3 == null) {
            throw new IllegalArgumentException("please set request bucket !");
        }
        return str.replace("${bucket}", str3).replace("${region}", str2);
    }

    private String getFormatHost(String str, String str2) {
        if (str2 == null) {
            throw new IllegalArgumentException("please set request or config region !");
        }
        return str.replace("${region}", str2);
    }

    private String getFormatHostByAppId(String str, String str2, String str3) {
        if (str2 == null) {
            throw new IllegalArgumentException("please set request or config region !");
        }
        if (str3 == null) {
            throw new IllegalArgumentException("please set request appid !");
        }
        return str.replace("${appid}", str3).replace("${region}", str2);
    }

    private String getHostFormat(boolean z, boolean z2) {
        String str;
        if (!TextUtils.isEmpty(this.hostFormat)) {
            return this.hostFormat;
        }
        if (z) {
            str = ACCELERATE_HOST_FORMAT;
        } else if (!z2) {
            str = DEFAULT_HOST_FORMAT;
        } else {
            str = PATH_STYLE_HOST_FORMAT;
        }
        String str2 = this.endpointSuffix;
        if (str2 == null) {
            return str;
        }
        String strConcat = this.bucketInPath ? str2 : "${bucket}.".concat(str2);
        return z ? strConcat.replace("cos.${region}", ACCELERATE_ENDPOINT_SUFFIX) : strConcat;
    }

    public int getPort() {
        return this.port;
    }

    @Deprecated
    public String getHost(String str, boolean z) {
        return getHost(str, null, z);
    }

    @Deprecated
    public String getHost(String str, String str2, boolean z) {
        return getHost(str, str2, this.appid, z);
    }

    @Deprecated
    public String getHost(String str, String str2, boolean z, boolean z2) {
        return getHost(str, str2, this.appid, z, z2);
    }

    @Deprecated
    public String getHost(String str, String str2, String str3, boolean z, boolean z2) {
        if (!z2 && !TextUtils.isEmpty(this.host)) {
            return this.host;
        }
        return (this.bucketInPath ? "" : "" + getBucket(str, str3) + ".") + getEndpointSuffix(str2, z);
    }

    @Deprecated
    public String getHost(String str, String str2, String str3, boolean z) {
        return getHost(str, str2, str3, z, false);
    }

    public boolean isDnsCache() {
        return this.dnsCache;
    }

    public Map<String, List<String>> getCommonHeaders() {
        return this.commonHeaders;
    }

    public boolean isTransferThreadControl() {
        return this.transferThreadControl;
    }

    public int getUploadMaxThreadCount() {
        return this.uploadMaxThreadCount;
    }

    public int getDownloadMaxThreadCount() {
        return this.downloadMaxThreadCount;
    }

    public boolean isDomainSwitch() {
        return this.domainSwitch;
    }

    public boolean isVerifySSLEnable() {
        return this.verifySSLEnable;
    }

    public byte[] getClientCertificateBytes() {
        return this.clientCertificateBytes;
    }

    public char[] getClientCertificatePassword() {
        return this.clientCertificatePassword;
    }

    public NetworkClient getCustomizeNetworkClient() {
        return this.customizeNetworkClient;
    }

    public boolean isRedirectEnable() {
        return this.redirectEnable;
    }

    @Deprecated
    public String getEndpointSuffix() {
        return getEndpointSuffix(this.region, false);
    }

    @Deprecated
    public String getEndpointSuffix(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            str = getRegion();
        }
        String str2 = this.endpointSuffix;
        if (str2 == null && str != null) {
            str2 = "cos." + str + ".myqcloud.com";
        }
        String strSubstituteEndpointSuffix = substituteEndpointSuffix(str2, str);
        return (strSubstituteEndpointSuffix == null || !z) ? strSubstituteEndpointSuffix : strSubstituteEndpointSuffix.replace("cos." + str, ACCELERATE_ENDPOINT_SUFFIX);
    }

    private String substituteEndpointSuffix(String str, String str2) {
        return (TextUtils.isEmpty(str) || str2 == null) ? str : str.replace("${region}", str2);
    }

    public String getUrlPath(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (this.bucketInPath) {
            if (!str.endsWith("-" + this.appid) && !TextUtils.isEmpty(this.appid)) {
                str = str + "-" + this.appid;
            }
            sb.append("/").append(str);
        }
        if (str2 != null && !str2.startsWith("/")) {
            sb.append("/").append(str2);
        } else {
            sb.append(str2);
        }
        return sb.toString();
    }

    public boolean isDebuggable() {
        return this.isDebuggable;
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public RetryStrategy getRetryStrategy() {
        return this.retryStrategy;
    }

    public QCloudHttpRetryHandler getQCloudHttpRetryHandler() {
        return this.qCloudHttpRetryHandler;
    }

    public Executor getExecutor() {
        return this.executor;
    }

    public Executor getObserveExecutor() {
        return this.observeExecutor;
    }

    public boolean isEnableQuic() {
        return this.isQuic;
    }

    public RequestNetworkStrategy networkSwitchStrategy() {
        return this.networkSwitchStrategy;
    }

    public String getUserAgentExtended() {
        return this.userAgentExtended;
    }

    public Map<String, String> getTrackParams() {
        HashMap map = new HashMap();
        if (!TextUtils.isEmpty(this.host)) {
            map.put("cos_config_host", this.host);
        }
        map.put("cos_config_quic", String.valueOf(isEnableQuic()));
        if (networkSwitchStrategy() != null) {
            map.put("cos_config_network_switch_strategy", networkSwitchStrategy().toString());
        }
        return map;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.protocol);
        parcel.writeString(this.region);
        parcel.writeInt(this.isDebuggable ? 1 : 0);
    }

    private CosXmlServiceConfig(Parcel parcel) {
        this(new Builder().isHttps("https".equals(parcel.readString())).setRegion(parcel.readString()).setDebuggable(parcel.readInt() == 1));
    }

    public static final class Builder {
        private boolean accelerate;
        private String appid;
        private boolean bucketInPath;
        private byte[] clientCertificateBytes;
        private char[] clientCertificatePassword;
        private Map<String, List<String>> commonHeaders;
        private int connectionTimeout;
        private NetworkClient customizeNetworkClient;
        private boolean dnsCache;
        private boolean domainSwitch;
        private int downloadMaxThreadCount;
        private String endpointSuffix;
        private Executor executor;
        private String host;
        private String hostFormat;
        private String hostHeaderFormat;
        private boolean isDebuggable;
        private boolean isQuic;
        private RequestNetworkStrategy networkSwitchStrategy;
        private Set<String> noSignHeaders;
        private Executor observeExecutor;
        private int port;
        private String protocol;
        private QCloudHttpRetryHandler qCloudHttpRetryHandler;
        private boolean redirectEnable;
        private String region;
        private RetryStrategy retryStrategy;
        private boolean signInUrl;
        private int socketTimeout;
        private boolean transferThreadControl;
        private int uploadMaxThreadCount;
        private String userAgentExtended;
        private boolean verifySSLEnable;

        public Builder() {
            this.port = -1;
            this.connectionTimeout = WebSocketHandler.ACK_TIMEOUT_FOR_AUDIO_SAVE;
            this.socketTimeout = 30000;
            this.isQuic = false;
            this.dnsCache = true;
            this.commonHeaders = new HashMap();
            this.noSignHeaders = new HashSet();
            this.transferThreadControl = true;
            this.protocol = "https";
            this.isDebuggable = false;
            this.retryStrategy = RetryStrategy.DEFAULT;
            this.bucketInPath = false;
            this.uploadMaxThreadCount = TaskExecutors.UPLOAD_THREAD_COUNT;
            this.downloadMaxThreadCount = TaskExecutors.DOWNLOAD_THREAD_COUNT;
            this.domainSwitch = true;
            this.verifySSLEnable = true;
            this.clientCertificateBytes = null;
            this.clientCertificatePassword = null;
            this.redirectEnable = false;
            this.customizeNetworkClient = null;
        }

        public Builder(CosXmlServiceConfig cosXmlServiceConfig) {
            this.port = -1;
            this.connectionTimeout = WebSocketHandler.ACK_TIMEOUT_FOR_AUDIO_SAVE;
            this.socketTimeout = 30000;
            this.isQuic = false;
            this.dnsCache = true;
            this.commonHeaders = new HashMap();
            this.noSignHeaders = new HashSet();
            this.transferThreadControl = true;
            this.protocol = cosXmlServiceConfig.protocol;
            this.region = cosXmlServiceConfig.region;
            this.appid = cosXmlServiceConfig.appid;
            this.host = cosXmlServiceConfig.host;
            this.port = cosXmlServiceConfig.port;
            this.endpointSuffix = cosXmlServiceConfig.endpointSuffix;
            this.bucketInPath = cosXmlServiceConfig.bucketInPath;
            this.isDebuggable = cosXmlServiceConfig.isDebuggable;
            this.retryStrategy = cosXmlServiceConfig.retryStrategy;
            this.qCloudHttpRetryHandler = cosXmlServiceConfig.qCloudHttpRetryHandler;
            this.connectionTimeout = cosXmlServiceConfig.connectionTimeout;
            this.socketTimeout = cosXmlServiceConfig.socketTimeout;
            this.executor = cosXmlServiceConfig.executor;
            this.observeExecutor = cosXmlServiceConfig.observeExecutor;
            this.isQuic = cosXmlServiceConfig.isQuic;
            this.networkSwitchStrategy = cosXmlServiceConfig.networkSwitchStrategy;
            this.dnsCache = cosXmlServiceConfig.dnsCache;
            this.commonHeaders = cosXmlServiceConfig.commonHeaders;
            this.noSignHeaders = cosXmlServiceConfig.noSignHeaders;
            this.hostFormat = cosXmlServiceConfig.hostFormat;
            this.hostHeaderFormat = cosXmlServiceConfig.hostHeaderFormat;
            this.accelerate = cosXmlServiceConfig.accelerate;
            this.signInUrl = cosXmlServiceConfig.signInUrl;
            this.transferThreadControl = cosXmlServiceConfig.transferThreadControl;
            this.uploadMaxThreadCount = cosXmlServiceConfig.uploadMaxThreadCount;
            this.downloadMaxThreadCount = cosXmlServiceConfig.downloadMaxThreadCount;
            this.domainSwitch = cosXmlServiceConfig.domainSwitch;
            this.verifySSLEnable = cosXmlServiceConfig.verifySSLEnable;
            this.clientCertificateBytes = cosXmlServiceConfig.clientCertificateBytes;
            this.clientCertificatePassword = cosXmlServiceConfig.clientCertificatePassword;
            this.redirectEnable = cosXmlServiceConfig.redirectEnable;
            this.customizeNetworkClient = cosXmlServiceConfig.customizeNetworkClient;
        }

        public Builder setConnectionTimeout(int i) {
            this.connectionTimeout = i;
            return this;
        }

        public Builder setSocketTimeout(int i) {
            this.socketTimeout = i;
            return this;
        }

        public Builder setTransferThreadControl(boolean z) {
            this.transferThreadControl = z;
            return this;
        }

        public Builder setUploadMaxThreadCount(int i) {
            this.uploadMaxThreadCount = i;
            return this;
        }

        public Builder setDownloadMaxThreadCount(int i) {
            this.downloadMaxThreadCount = i;
            return this;
        }

        public Builder setDomainSwitch(boolean z) {
            this.domainSwitch = z;
            return this;
        }

        public Builder setVerifySSLEnable(boolean z) {
            this.verifySSLEnable = z;
            return this;
        }

        public Builder setClientCertificate(byte[] bArr, String str) {
            this.clientCertificateBytes = bArr;
            this.clientCertificatePassword = str.toCharArray();
            return this;
        }

        public Builder setRedirectEnable(boolean z) {
            this.redirectEnable = z;
            return this;
        }

        public Builder setCustomizeNetworkClient(NetworkClient networkClient) {
            this.customizeNetworkClient = networkClient;
            return this;
        }

        public Builder isHttps(boolean z) {
            if (z) {
                this.protocol = "https";
            } else {
                this.protocol = "http";
            }
            return this;
        }

        public Builder setHostFormat(String str) {
            this.hostFormat = str;
            return this;
        }

        @Deprecated
        public Builder setAppidAndRegion(String str, String str2) {
            this.appid = str;
            this.region = str2;
            return this;
        }

        public Builder setRegion(String str) {
            this.region = str;
            return this;
        }

        @Deprecated
        public Builder setEndpointSuffix(String str) {
            this.endpointSuffix = str;
            return this;
        }

        public Builder setHost(String str) {
            this.host = str;
            return this;
        }

        public Builder setHost(Uri uri) {
            this.host = uri.getHost();
            if (uri.getPort() != -1) {
                this.port = uri.getPort();
            }
            this.protocol = uri.getScheme();
            return this;
        }

        public Builder setPort(int i) {
            this.port = i;
            return this;
        }

        public Builder setDebuggable(boolean z) {
            this.isDebuggable = z;
            return this;
        }

        public Builder setSignInUrl(boolean z) {
            this.signInUrl = z;
            return this;
        }

        public Builder setRetryStrategy(RetryStrategy retryStrategy) {
            this.retryStrategy = retryStrategy;
            return this;
        }

        public Builder setRetryHandler(QCloudHttpRetryHandler qCloudHttpRetryHandler) {
            this.qCloudHttpRetryHandler = qCloudHttpRetryHandler;
            return this;
        }

        @Deprecated
        public Builder setBucketInPath(boolean z) {
            this.bucketInPath = z;
            return this;
        }

        public Builder setPathStyle(boolean z) {
            this.bucketInPath = z;
            return this;
        }

        public Builder setExecutor(Executor executor) {
            this.executor = executor;
            return this;
        }

        public Builder setObserveExecutor(Executor executor) {
            this.observeExecutor = executor;
            return this;
        }

        public Builder enableQuic(boolean z) {
            this.isQuic = z;
            return this;
        }

        public Builder setNetworkSwitchStrategy(RequestNetworkStrategy requestNetworkStrategy) {
            this.networkSwitchStrategy = requestNetworkStrategy;
            return this;
        }

        public Builder setUserAgentExtended(String str) {
            this.userAgentExtended = str;
            return this;
        }

        public Builder dnsCache(boolean z) {
            this.dnsCache = z;
            return this;
        }

        public Builder setAccelerate(boolean z) {
            this.accelerate = z;
            return this;
        }

        public CosXmlServiceConfig builder() {
            return new CosXmlServiceConfig(this);
        }

        public Builder addHeader(String str, String str2) {
            List<String> linkedList = this.commonHeaders.get(str);
            if (linkedList == null) {
                linkedList = new LinkedList<>();
            }
            linkedList.add(str2);
            this.commonHeaders.put(str, linkedList);
            return this;
        }

        public Builder addNoSignHeaders(String str) {
            this.noSignHeaders.add(str);
            return this;
        }
    }
}
