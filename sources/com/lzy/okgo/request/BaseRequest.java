package com.lzy.okgo.request;

import android.text.TextUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.adapter.CacheCall;
import com.lzy.okgo.adapter.CallAdapter;
import com.lzy.okgo.adapter.DefaultCallAdapter;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.ProgressRequestBody;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseRequest<R extends BaseRequest> {
    protected String baseUrl;
    protected String cacheKey;
    protected CacheMode cacheMode;
    protected long cacheTime;
    protected long connectTimeout;
    private AbsCallback mCallback;
    private Converter mConverter;
    private Request mRequest;
    protected String method;
    protected long readTimeOut;
    protected int retryCount;
    protected Object tag;
    protected String url;
    protected long writeTimeOut;
    protected HttpParams params = new HttpParams();
    protected HttpHeaders headers = new HttpHeaders();
    protected List<Interceptor> interceptors = new ArrayList();

    public abstract Request generateRequest(RequestBody requestBody);

    public abstract RequestBody generateRequestBody();

    public BaseRequest(String str) {
        this.cacheTime = -1L;
        this.url = str;
        this.baseUrl = str;
        OkGo okGo = OkGo.getInstance();
        String acceptLanguage = HttpHeaders.getAcceptLanguage();
        if (!TextUtils.isEmpty(acceptLanguage)) {
            headers(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, acceptLanguage);
        }
        String userAgent = HttpHeaders.getUserAgent();
        if (!TextUtils.isEmpty(userAgent)) {
            headers("User-Agent", userAgent);
        }
        if (okGo.getCommonParams() != null) {
            this.params.put(okGo.getCommonParams());
        }
        if (okGo.getCommonHeaders() != null) {
            this.headers.put(okGo.getCommonHeaders());
        }
        if (okGo.getCacheMode() != null) {
            this.cacheMode = okGo.getCacheMode();
        }
        this.cacheTime = okGo.getCacheTime();
        this.retryCount = okGo.getRetryCount();
    }

    public R url(String str) {
        this.url = str;
        return this;
    }

    public R tag(Object obj) {
        this.tag = obj;
        return this;
    }

    public R readTimeOut(long j) {
        this.readTimeOut = j;
        return this;
    }

    public R writeTimeOut(long j) {
        this.writeTimeOut = j;
        return this;
    }

    public R connTimeOut(long j) {
        this.connectTimeout = j;
        return this;
    }

    public R cacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return this;
    }

    public R cacheKey(String str) {
        this.cacheKey = str;
        return this;
    }

    public R cacheTime(long j) {
        if (j <= -1) {
            j = -1;
        }
        this.cacheTime = j;
        return this;
    }

    public R headers(HttpHeaders httpHeaders) {
        this.headers.put(httpHeaders);
        return this;
    }

    public R headers(String str, String str2) {
        this.headers.put(str, str2);
        return this;
    }

    public R removeHeader(String str) {
        this.headers.remove(str);
        return this;
    }

    public R removeAllHeaders() {
        this.headers.clear();
        return this;
    }

    public R params(HttpParams httpParams) {
        this.params.put(httpParams);
        return this;
    }

    public R params(Map<String, String> map, boolean... zArr) {
        this.params.put(map, zArr);
        return this;
    }

    public R params(String str, String str2, boolean... zArr) {
        this.params.put(str, str2, zArr);
        return this;
    }

    public R params(String str, int i, boolean... zArr) {
        this.params.put(str, i, zArr);
        return this;
    }

    public R params(String str, float f, boolean... zArr) {
        this.params.put(str, f, zArr);
        return this;
    }

    public R params(String str, double d, boolean... zArr) {
        this.params.put(str, d, zArr);
        return this;
    }

    public R params(String str, long j, boolean... zArr) {
        this.params.put(str, j, zArr);
        return this;
    }

    public R params(String str, char c, boolean... zArr) {
        this.params.put(str, c, zArr);
        return this;
    }

    public R params(String str, boolean z, boolean... zArr) {
        this.params.put(str, z, zArr);
        return this;
    }

    public R addUrlParams(String str, List<String> list) {
        this.params.putUrlParams(str, list);
        return this;
    }

    public R removeParam(String str) {
        this.params.remove(str);
        return this;
    }

    public R removeAllParams() {
        this.params.clear();
        return this;
    }

    public R setCallback(AbsCallback absCallback) {
        this.mCallback = absCallback;
        return this;
    }

    public R addInterceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }

    public String getUrlParam(String str) {
        List<String> list = this.params.urlParamsMap.get(str);
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    public HttpParams.FileWrapper getFileParam(String str) {
        List<HttpParams.FileWrapper> list = this.params.fileParamsMap.get(str);
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0);
    }

    public HttpParams getParams() {
        return this.params;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public String getUrl() {
        return this.url;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public Object getTag() {
        return this.tag;
    }

    public CacheMode getCacheMode() {
        return this.cacheMode;
    }

    public void setCacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
    }

    public String getCacheKey() {
        return this.cacheKey;
    }

    public void setCacheKey(String str) {
        this.cacheKey = str;
    }

    public long getCacheTime() {
        return this.cacheTime;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public Request getRequest() {
        return this.mRequest;
    }

    public AbsCallback getCallback() {
        return this.mCallback;
    }

    public Converter getConverter() {
        return this.mConverter;
    }

    public String getMethod() {
        return this.method;
    }

    public RequestBody wrapRequestBody(RequestBody requestBody) {
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody);
        progressRequestBody.setListener(new ProgressRequestBody.Listener() { // from class: com.lzy.okgo.request.BaseRequest.1
            @Override // com.lzy.okgo.request.ProgressRequestBody.Listener
            public void onRequestProgress(final long j, final long j2, final long j3) {
                OkGo.getInstance().getDelivery().post(new Runnable() { // from class: com.lzy.okgo.request.BaseRequest.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (BaseRequest.this.mCallback != null) {
                            AbsCallback absCallback = BaseRequest.this.mCallback;
                            long j4 = j;
                            long j5 = j2;
                            absCallback.upProgress(j4, j5, (j4 * 1.0f) / j5, j3);
                        }
                    }
                });
            }
        });
        return progressRequestBody;
    }

    public Call generateCall(Request request) {
        this.mRequest = request;
        if (this.readTimeOut <= 0 && this.writeTimeOut <= 0 && this.connectTimeout <= 0 && this.interceptors.size() == 0) {
            return OkGo.getInstance().getOkHttpClient().newCall(request);
        }
        OkHttpClient.Builder builderNewBuilder = OkGo.getInstance().getOkHttpClient().newBuilder();
        long j = this.readTimeOut;
        if (j > 0) {
            builderNewBuilder.readTimeout(j, TimeUnit.MILLISECONDS);
        }
        long j2 = this.writeTimeOut;
        if (j2 > 0) {
            builderNewBuilder.writeTimeout(j2, TimeUnit.MILLISECONDS);
        }
        long j3 = this.connectTimeout;
        if (j3 > 0) {
            builderNewBuilder.connectTimeout(j3, TimeUnit.MILLISECONDS);
        }
        if (this.interceptors.size() > 0) {
            Iterator<Interceptor> it = this.interceptors.iterator();
            while (it.hasNext()) {
                builderNewBuilder.addInterceptor(it.next());
            }
        }
        return builderNewBuilder.build().newCall(request);
    }

    public Call getCall() {
        Request requestGenerateRequest = generateRequest(wrapRequestBody(generateRequestBody()));
        this.mRequest = requestGenerateRequest;
        return generateCall(requestGenerateRequest);
    }

    public <T> com.lzy.okgo.adapter.Call<T> getCall(Converter<T> converter) {
        this.mConverter = converter;
        return DefaultCallAdapter.create().adapt((com.lzy.okgo.adapter.Call) new CacheCall(this));
    }

    public <T, E> E getCall(Converter<T> converter, CallAdapter<E> callAdapter) {
        this.mConverter = converter;
        return callAdapter.adapt(getCall(converter));
    }

    public Response execute() throws IOException {
        return getCall().execute();
    }

    public <T> void execute(AbsCallback<T> absCallback) {
        this.mCallback = absCallback;
        this.mConverter = absCallback;
        new CacheCall(this).execute(absCallback);
    }
}
