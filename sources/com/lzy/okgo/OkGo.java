package com.lzy.okgo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.CookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.DeleteRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.HeadRequest;
import com.lzy.okgo.request.OptionsRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;
import com.lzy.okgo.utils.OkLogger;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.X509TrustManager;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/* JADX INFO: loaded from: classes4.dex */
public class OkGo {
    public static final int DEFAULT_MILLISECONDS = 60000;
    public static int REFRESH_TIME = 100;
    private static Application context;
    private CookieJarImpl cookieJar;
    private CacheMode mCacheMode;
    private long mCacheTime;
    private HttpHeaders mCommonHeaders;
    private HttpParams mCommonParams;
    private Handler mDelivery;
    private int mRetryCount;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder okHttpClientBuilder;

    private OkGo() {
        this.mRetryCount = 3;
        this.mCacheTime = -1L;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        this.okHttpClientBuilder = builder;
        builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);
        this.okHttpClientBuilder.connectTimeout(60000L, TimeUnit.MILLISECONDS);
        this.okHttpClientBuilder.readTimeout(60000L, TimeUnit.MILLISECONDS);
        this.okHttpClientBuilder.writeTimeout(60000L, TimeUnit.MILLISECONDS);
        this.mDelivery = new Handler(Looper.getMainLooper());
    }

    public static OkGo getInstance() {
        return OkGoHolder.holder;
    }

    private static class OkGoHolder {
        private static OkGo holder = new OkGo();

        private OkGoHolder() {
        }
    }

    public static void init(Application application) {
        context = application;
    }

    public static Context getContext() {
        Application application = context;
        if (application != null) {
            return application;
        }
        throw new IllegalStateException("请先在全局Application中调用 OkGo.init() 初始化！");
    }

    public Handler getDelivery() {
        return this.mDelivery;
    }

    public OkHttpClient getOkHttpClient() {
        if (this.okHttpClient == null) {
            this.okHttpClient = this.okHttpClientBuilder.build();
        }
        return this.okHttpClient;
    }

    public OkHttpClient.Builder getOkHttpClientBuilder() {
        return this.okHttpClientBuilder;
    }

    public static GetRequest get(String str) {
        return new GetRequest(str);
    }

    public static PostRequest post(String str) {
        return new PostRequest(str);
    }

    public static PutRequest put(String str) {
        return new PutRequest(str);
    }

    public static HeadRequest head(String str) {
        return new HeadRequest(str);
    }

    public static DeleteRequest delete(String str) {
        return new DeleteRequest(str);
    }

    public static OptionsRequest options(String str) {
        return new OptionsRequest(str);
    }

    public OkGo debug(String str) {
        debug(str, Level.INFO, true);
        return this;
    }

    public OkGo debug(String str, Level level, boolean z) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(str);
        httpLoggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        httpLoggingInterceptor.setColorLevel(level);
        this.okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        OkLogger.debug(z);
        return this;
    }

    public OkGo setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.okHttpClientBuilder.hostnameVerifier(hostnameVerifier);
        return this;
    }

    public OkGo setCertificates(InputStream... inputStreamArr) {
        setCertificates((InputStream) null, (String) null, inputStreamArr);
        return this;
    }

    public OkGo setCertificates(X509TrustManager x509TrustManager) {
        setCertificates((InputStream) null, (String) null, x509TrustManager);
        return this;
    }

    public OkGo setCertificates(InputStream inputStream, String str, InputStream... inputStreamArr) {
        HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.getSslSocketFactory(null, inputStream, str, inputStreamArr);
        this.okHttpClientBuilder.sslSocketFactory(sslSocketFactory.sSLSocketFactory, sslSocketFactory.trustManager);
        return this;
    }

    public OkGo setCertificates(InputStream inputStream, String str, X509TrustManager x509TrustManager) {
        HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.getSslSocketFactory(x509TrustManager, inputStream, str, null);
        this.okHttpClientBuilder.sslSocketFactory(sslSocketFactory.sSLSocketFactory, sslSocketFactory.trustManager);
        return this;
    }

    public OkGo setCookieStore(CookieStore cookieStore) {
        CookieJarImpl cookieJarImpl = new CookieJarImpl(cookieStore);
        this.cookieJar = cookieJarImpl;
        this.okHttpClientBuilder.cookieJar(cookieJarImpl);
        return this;
    }

    public CookieJarImpl getCookieJar() {
        return this.cookieJar;
    }

    public OkGo setReadTimeOut(long j) {
        this.okHttpClientBuilder.readTimeout(j, TimeUnit.MILLISECONDS);
        return this;
    }

    public OkGo setWriteTimeOut(long j) {
        this.okHttpClientBuilder.writeTimeout(j, TimeUnit.MILLISECONDS);
        return this;
    }

    public OkGo setConnectTimeout(long j) {
        this.okHttpClientBuilder.connectTimeout(j, TimeUnit.MILLISECONDS);
        return this;
    }

    public OkGo setRetryCount(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("retryCount must > 0");
        }
        this.mRetryCount = i;
        return this;
    }

    public int getRetryCount() {
        return this.mRetryCount;
    }

    public OkGo setCacheMode(CacheMode cacheMode) {
        this.mCacheMode = cacheMode;
        return this;
    }

    public CacheMode getCacheMode() {
        return this.mCacheMode;
    }

    public OkGo setCacheTime(long j) {
        if (j <= -1) {
            j = -1;
        }
        this.mCacheTime = j;
        return this;
    }

    public long getCacheTime() {
        return this.mCacheTime;
    }

    public HttpParams getCommonParams() {
        return this.mCommonParams;
    }

    public OkGo addCommonParams(HttpParams httpParams) {
        if (this.mCommonParams == null) {
            this.mCommonParams = new HttpParams();
        }
        this.mCommonParams.put(httpParams);
        return this;
    }

    public HttpHeaders getCommonHeaders() {
        return this.mCommonHeaders;
    }

    public OkGo addCommonHeaders(HttpHeaders httpHeaders) {
        if (this.mCommonHeaders == null) {
            this.mCommonHeaders = new HttpHeaders();
        }
        this.mCommonHeaders.put(httpHeaders);
        return this;
    }

    public OkGo addInterceptor(Interceptor interceptor) {
        this.okHttpClientBuilder.addInterceptor(interceptor);
        return this;
    }

    public void cancelTag(Object obj) {
        for (Call call : getOkHttpClient().dispatcher().queuedCalls()) {
            if (obj.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call2 : getOkHttpClient().dispatcher().runningCalls()) {
            if (obj.equals(call2.request().tag())) {
                call2.cancel();
            }
        }
    }

    public void cancelAll() {
        Iterator<Call> it = getOkHttpClient().dispatcher().queuedCalls().iterator();
        while (it.hasNext()) {
            it.next().cancel();
        }
        Iterator<Call> it2 = getOkHttpClient().dispatcher().runningCalls().iterator();
        while (it2.hasNext()) {
            it2.next().cancel();
        }
    }
}
