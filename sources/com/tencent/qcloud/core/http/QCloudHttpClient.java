package com.tencent.qcloud.core.http;

import android.text.TextUtils;
import com.aivox.common.socket.WebSocketHandler;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.logger.COSLogger;
import com.tencent.qcloud.core.task.QCloudTask;
import com.tencent.qcloud.core.task.RetryStrategy;
import com.tencent.qcloud.core.task.TaskManager;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import okhttp3.Call;
import okhttp3.Dns;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;

/* JADX INFO: loaded from: classes4.dex */
public final class QCloudHttpClient {
    public static final String HTTP_LOG_TAG = "QCloudHttp";
    public static final String QUIC_LOG_TAG = "QCloudQuic";
    private static volatile QCloudHttpClient gDefault;
    private static OkHttpClient.Builder okHttpClientBuilder;
    private final ConnectionRepository connectionRepository;
    private boolean dnsCache;
    private final ArrayList<QCloudDnsFetch> dnsFetchs;
    private final Map<String, List<InetAddress>> dnsMap;
    private final HttpLogger httpLogger;
    private Dns mDns;
    private EventListener.Factory mEventListenerFactory;
    private HostnameVerifier mHostnameVerifier;
    private String networkClientType;
    private OkHttpClientImpl okhttpNetworkClient;
    private final TaskManager taskManager;
    private final Set<String> verifiedHost;
    private static Map<Integer, NetworkClient> networkClientMap = new ConcurrentHashMap(2);
    public static final Object okHttpClientBuilderLock = new Object();

    public interface QCloudDnsFetch {
        List<InetAddress> fetch(String str) throws UnknownHostException;
    }

    public static QCloudHttpClient getDefault() {
        if (gDefault == null) {
            synchronized (QCloudHttpClient.class) {
                if (gDefault == null) {
                    gDefault = new Builder().build();
                }
            }
        }
        return gDefault;
    }

    public void addVerifiedHost(String str) {
        if (str != null) {
            this.verifiedHost.add(str);
        }
    }

    public void addDnsRecord(String str, String[] strArr) throws UnknownHostException {
        if (strArr.length > 0) {
            ArrayList arrayList = new ArrayList(strArr.length);
            for (String str2 : strArr) {
                arrayList.add(InetAddress.getByName(str2));
            }
            this.dnsMap.put(str, arrayList);
        }
    }

    public void addDnsFetch(QCloudDnsFetch qCloudDnsFetch) {
        this.dnsFetchs.add(qCloudDnsFetch);
    }

    private QCloudHttpClient(Builder builder) {
        this.networkClientType = OkHttpClientImpl.class.getName();
        this.dnsCache = true;
        this.mHostnameVerifier = new HostnameVerifier() { // from class: com.tencent.qcloud.core.http.QCloudHttpClient.1
            @Override // javax.net.ssl.HostnameVerifier
            public boolean verify(String str, SSLSession sSLSession) {
                if (QCloudHttpClient.this.verifiedHost.size() > 0) {
                    Iterator it = QCloudHttpClient.this.verifiedHost.iterator();
                    while (it.hasNext()) {
                        if (HttpsURLConnection.getDefaultHostnameVerifier().verify((String) it.next(), sSLSession)) {
                            return true;
                        }
                    }
                }
                return HttpsURLConnection.getDefaultHostnameVerifier().verify(str, sSLSession);
            }
        };
        this.mDns = new Dns() { // from class: com.tencent.qcloud.core.http.QCloudHttpClient.2
            @Override // okhttp3.Dns
            public List<InetAddress> lookup(String str) throws UnknownHostException {
                List<InetAddress> listFetch = QCloudHttpClient.this.dnsMap.containsKey(str) ? (List) QCloudHttpClient.this.dnsMap.get(str) : null;
                if (listFetch == null || listFetch.size() == 0) {
                    for (QCloudDnsFetch qCloudDnsFetch : QCloudHttpClient.this.dnsFetchs) {
                        if (qCloudDnsFetch != null) {
                            try {
                                listFetch = qCloudDnsFetch.fetch(str);
                                if (listFetch != null) {
                                    break;
                                }
                            } catch (UnknownHostException unused) {
                                continue;
                            }
                        }
                    }
                }
                if (listFetch == null || listFetch.size() == 0) {
                    try {
                        listFetch = Dns.SYSTEM.lookup(str);
                    } catch (UnknownHostException unused2) {
                        COSLogger.wNetwork(QCloudHttpClient.HTTP_LOG_TAG, "system dns failed, retry cache dns records.");
                    }
                }
                if ((listFetch == null || listFetch.size() == 0) && !QCloudHttpClient.this.dnsCache) {
                    throw new UnknownHostException("can not resolve host name " + str);
                }
                if (listFetch == null || listFetch.size() == 0) {
                    try {
                        listFetch = QCloudHttpClient.this.connectionRepository.getDnsRecord(str);
                    } catch (UnknownHostException unused3) {
                        COSLogger.wNetwork(QCloudHttpClient.HTTP_LOG_TAG, "Not found dns in cache records.");
                    }
                }
                if (listFetch != null && listFetch.size() > 0) {
                    ConnectionRepository.getInstance().insertDnsRecordCache(str, listFetch);
                    return listFetch;
                }
                throw new UnknownHostException(str);
            }
        };
        this.mEventListenerFactory = new EventListener.Factory() { // from class: com.tencent.qcloud.core.http.QCloudHttpClient.3
            @Override // okhttp3.EventListener.Factory
            public EventListener create(Call call) {
                return new CallMetricsListener(call);
            }
        };
        this.verifiedHost = new HashSet(5);
        this.dnsMap = new ConcurrentHashMap(3);
        this.dnsFetchs = new ArrayList<>(3);
        this.taskManager = TaskManager.getInstance();
        ConnectionRepository connectionRepository = ConnectionRepository.getInstance();
        this.connectionRepository = connectionRepository;
        HttpLogger httpLogger = new HttpLogger();
        this.httpLogger = httpLogger;
        OkHttpClientImpl okHttpClientImpl = new OkHttpClientImpl();
        this.okhttpNetworkClient = okHttpClientImpl;
        okHttpClientImpl.init(builder, hostnameVerifier(), this.mDns, httpLogger);
        NetworkClient okHttpClientImpl2 = builder.networkClient;
        okHttpClientImpl2 = okHttpClientImpl2 == null ? new OkHttpClientImpl() : okHttpClientImpl2;
        this.networkClientType = okHttpClientImpl2.getClass().getName();
        if (OkHttpClientImpl.class.getName().equals(this.networkClientType) && (okHttpClientImpl2 instanceof OkHttpClientImpl)) {
            if (this.okhttpNetworkClient == null) {
                OkHttpClientImpl okHttpClientImpl3 = (OkHttpClientImpl) okHttpClientImpl2;
                this.okhttpNetworkClient = okHttpClientImpl3;
                okHttpClientImpl3.init(builder, hostnameVerifier(), this.mDns, httpLogger);
            }
        } else {
            int iHashCode = this.networkClientType.hashCode();
            if (!networkClientMap.containsKey(Integer.valueOf(iHashCode))) {
                okHttpClientImpl2.init(builder, hostnameVerifier(), this.mDns, httpLogger);
                if (!"com.tencent.qcloud.quic.QuicClientImpl".equals(this.networkClientType)) {
                    okHttpClientImpl2.enableQCloudInterceptor();
                }
                networkClientMap.put(Integer.valueOf(iHashCode), okHttpClientImpl2);
            }
        }
        connectionRepository.addPrefetchHosts(builder.prefetchHost);
        connectionRepository.init();
    }

    public void setNetworkClientType(Builder builder) {
        OkHttpClientImpl okHttpClientImpl = new OkHttpClientImpl();
        this.okhttpNetworkClient = okHttpClientImpl;
        okHttpClientImpl.init(builder, hostnameVerifier(), this.mDns, this.httpLogger);
        NetworkClient networkClient = builder.networkClient;
        if (networkClient != null) {
            this.networkClientType = networkClient.getClass().getName();
            if (OkHttpClientImpl.class.getName().equals(this.networkClientType) && (networkClient instanceof OkHttpClientImpl)) {
                if (this.okhttpNetworkClient == null) {
                    OkHttpClientImpl okHttpClientImpl2 = (OkHttpClientImpl) networkClient;
                    this.okhttpNetworkClient = okHttpClientImpl2;
                    okHttpClientImpl2.init(builder, hostnameVerifier(), this.mDns, this.httpLogger);
                    return;
                }
                return;
            }
            int iHashCode = this.networkClientType.hashCode();
            if (networkClientMap.containsKey(Integer.valueOf(iHashCode))) {
                return;
            }
            networkClient.init(builder, hostnameVerifier(), this.mDns, this.httpLogger);
            if (!"com.tencent.qcloud.quic.QuicClientImpl".equals(this.networkClientType)) {
                networkClient.enableQCloudInterceptor();
            }
            networkClientMap.put(Integer.valueOf(iHashCode), networkClient);
        }
    }

    public List<HttpTask> getTasksByTag(String str) {
        ArrayList arrayList = new ArrayList();
        if (str == null) {
            return arrayList;
        }
        for (QCloudTask qCloudTask : this.taskManager.snapshot()) {
            if ((qCloudTask instanceof HttpTask) && str.equals(qCloudTask.getTag())) {
                arrayList.add((HttpTask) qCloudTask);
            }
        }
        return arrayList;
    }

    public <T> HttpTask<T> resolveRequest(HttpRequest<T> httpRequest) {
        return handleRequest(httpRequest, null, null);
    }

    public <T> HttpTask<T> resolveRequest(HttpRequest<T> httpRequest, String str) {
        return handleRequest(httpRequest, null, str);
    }

    public <T> HttpTask<T> resolveRequest(QCloudHttpRequest<T> qCloudHttpRequest, QCloudCredentialProvider qCloudCredentialProvider) {
        return handleRequest(qCloudHttpRequest, qCloudCredentialProvider, null);
    }

    public <T> HttpTask<T> resolveRequest(QCloudHttpRequest<T> qCloudHttpRequest, QCloudCredentialProvider qCloudCredentialProvider, String str) {
        return handleRequest(qCloudHttpRequest, qCloudCredentialProvider, str);
    }

    private HostnameVerifier hostnameVerifier() {
        return this.mHostnameVerifier;
    }

    private <T> HttpTask<T> handleRequest(HttpRequest<T> httpRequest, QCloudCredentialProvider qCloudCredentialProvider, String str) {
        if (TextUtils.isEmpty(str)) {
            str = this.networkClientType;
        }
        if (OkHttpClientImpl.class.getName().equals(str)) {
            return new HttpTask<>(httpRequest, qCloudCredentialProvider, this.okhttpNetworkClient);
        }
        return new HttpTask<>(httpRequest, qCloudCredentialProvider, networkClientMap.get(Integer.valueOf(str.hashCode())));
    }

    public static final class Builder {
        byte[] clientCertificateBytes;
        char[] clientCertificatePassword;
        OkHttpClient.Builder mBuilder;
        NetworkClient networkClient;
        QCloudHttpRetryHandler qCloudHttpRetryHandler;
        RetryStrategy retryStrategy;
        int connectionTimeout = WebSocketHandler.ACK_TIMEOUT_FOR_AUDIO_SAVE;
        int socketTimeout = 30000;
        List<String> prefetchHost = new LinkedList();
        boolean dnsCache = false;
        boolean verifySSLEnable = true;
        boolean redirectEnable = false;

        public Builder setConnectionTimeout(int i) {
            if (i < 3000) {
                throw new IllegalArgumentException("connection timeout must be larger than 3 seconds.");
            }
            this.connectionTimeout = i;
            return this;
        }

        public Builder setSocketTimeout(int i) {
            if (i < 3000) {
                throw new IllegalArgumentException("socket timeout must be larger than 3 seconds.");
            }
            this.socketTimeout = i;
            return this;
        }

        public Builder setRetryStrategy(RetryStrategy retryStrategy) {
            this.retryStrategy = retryStrategy;
            return this;
        }

        public Builder setQCloudHttpRetryHandler(QCloudHttpRetryHandler qCloudHttpRetryHandler) {
            this.qCloudHttpRetryHandler = qCloudHttpRetryHandler;
            return this;
        }

        public Builder setInheritBuilder(OkHttpClient.Builder builder) {
            this.mBuilder = builder;
            return this;
        }

        public Builder setNetworkClient(NetworkClient networkClient) {
            this.networkClient = networkClient;
            return this;
        }

        public Builder addPrefetchHost(String str) {
            this.prefetchHost.add(str);
            return this;
        }

        public Builder dnsCache(boolean z) {
            this.dnsCache = z;
            return this;
        }

        public Builder setVerifySSLEnable(boolean z) {
            this.verifySSLEnable = z;
            return this;
        }

        public Builder setClientCertificate(byte[] bArr, char[] cArr) {
            this.clientCertificateBytes = bArr;
            this.clientCertificatePassword = cArr;
            return this;
        }

        public Builder setRedirectEnable(boolean z) {
            this.redirectEnable = z;
            return this;
        }

        public QCloudHttpClient build() {
            if (this.retryStrategy == null) {
                this.retryStrategy = RetryStrategy.DEFAULT;
            }
            QCloudHttpRetryHandler qCloudHttpRetryHandler = this.qCloudHttpRetryHandler;
            if (qCloudHttpRetryHandler != null) {
                this.retryStrategy.setRetryHandler(qCloudHttpRetryHandler);
            }
            synchronized (QCloudHttpClient.okHttpClientBuilderLock) {
                if (this.mBuilder == null) {
                    if (QCloudHttpClient.okHttpClientBuilder == null) {
                        OkHttpClient.Builder unused = QCloudHttpClient.okHttpClientBuilder = new OkHttpClient.Builder();
                    }
                    this.mBuilder = QCloudHttpClient.okHttpClientBuilder;
                }
            }
            return new QCloudHttpClient(this);
        }
    }
}
