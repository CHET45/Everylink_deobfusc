package com.lzy.okgo.adapter;

import android.graphics.Bitmap;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheManager;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.AbsCallbackWrapper;
import com.lzy.okgo.exception.OkGoException;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.utils.HeaderParser;
import com.lzy.okgo.utils.HttpUtils;
import java.io.IOException;
import java.net.SocketTimeoutException;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class CacheCall<T> implements Call<T> {
    private BaseRequest baseRequest;
    private CacheEntity<T> cacheEntity;
    private volatile boolean canceled;
    private int currentRetryCount;
    private boolean executed;
    private AbsCallback<T> mCallback;
    private okhttp3.Call rawCall;

    static /* synthetic */ int access$008(CacheCall cacheCall) {
        int i = cacheCall.currentRetryCount;
        cacheCall.currentRetryCount = i + 1;
        return i;
    }

    public CacheCall(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
    }

    @Override // com.lzy.okgo.adapter.Call
    public void execute(AbsCallback<T> absCallback) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already executed.");
            }
            this.executed = true;
        }
        this.mCallback = absCallback;
        if (absCallback == null) {
            this.mCallback = new AbsCallbackWrapper();
        }
        this.mCallback.onBefore(this.baseRequest);
        if (this.baseRequest.getCacheKey() == null) {
            BaseRequest baseRequest = this.baseRequest;
            baseRequest.setCacheKey(HttpUtils.createUrlFromParams(baseRequest.getBaseUrl(), this.baseRequest.getParams().urlParamsMap));
        }
        if (this.baseRequest.getCacheMode() == null) {
            this.baseRequest.setCacheMode(CacheMode.NO_CACHE);
        }
        final CacheMode cacheMode = this.baseRequest.getCacheMode();
        if (cacheMode != CacheMode.NO_CACHE) {
            CacheEntity<T> cacheEntity = (CacheEntity<T>) CacheManager.INSTANCE.get(this.baseRequest.getCacheKey());
            this.cacheEntity = cacheEntity;
            if (cacheEntity != null && cacheEntity.checkExpire(cacheMode, this.baseRequest.getCacheTime(), System.currentTimeMillis())) {
                this.cacheEntity.setExpire(true);
            }
            HeaderParser.addCacheHeaders(this.baseRequest, this.cacheEntity, cacheMode);
        }
        RequestBody requestBodyGenerateRequestBody = this.baseRequest.generateRequestBody();
        BaseRequest baseRequest2 = this.baseRequest;
        this.rawCall = this.baseRequest.generateCall(baseRequest2.generateRequest(baseRequest2.wrapRequestBody(requestBodyGenerateRequestBody)));
        if (cacheMode == CacheMode.IF_NONE_CACHE_REQUEST) {
            CacheEntity<T> cacheEntity2 = this.cacheEntity;
            if (cacheEntity2 != null && !cacheEntity2.isExpire()) {
                T data = this.cacheEntity.getData();
                HttpHeaders responseHeaders = this.cacheEntity.getResponseHeaders();
                if (data == null || responseHeaders == null) {
                    sendFailResultCallback(true, this.rawCall, null, OkGoException.INSTANCE("没有获取到缓存,或者缓存已经过期!"));
                } else {
                    sendSuccessResultCallback(true, data, this.rawCall, null);
                    return;
                }
            } else {
                sendFailResultCallback(true, this.rawCall, null, OkGoException.INSTANCE("没有获取到缓存,或者缓存已经过期!"));
            }
        } else if (cacheMode == CacheMode.FIRST_CACHE_THEN_REQUEST) {
            CacheEntity<T> cacheEntity3 = this.cacheEntity;
            if (cacheEntity3 != null && !cacheEntity3.isExpire()) {
                T data2 = this.cacheEntity.getData();
                HttpHeaders responseHeaders2 = this.cacheEntity.getResponseHeaders();
                if (data2 == null || responseHeaders2 == null) {
                    sendFailResultCallback(true, this.rawCall, null, OkGoException.INSTANCE("没有获取到缓存,或者缓存已经过期!"));
                } else {
                    sendSuccessResultCallback(true, data2, this.rawCall, null);
                }
            } else {
                sendFailResultCallback(true, this.rawCall, null, OkGoException.INSTANCE("没有获取到缓存,或者缓存已经过期!"));
            }
        }
        if (this.canceled) {
            this.rawCall.cancel();
        }
        this.currentRetryCount = 0;
        this.rawCall.enqueue(new Callback() { // from class: com.lzy.okgo.adapter.CacheCall.1
            @Override // okhttp3.Callback
            public void onFailure(okhttp3.Call call, IOException iOException) {
                if (!(iOException instanceof SocketTimeoutException) || CacheCall.this.currentRetryCount >= CacheCall.this.baseRequest.getRetryCount()) {
                    CacheCall.this.mCallback.parseError(call, iOException);
                    if (call.isCanceled()) {
                        return;
                    }
                    CacheCall.this.sendFailResultCallback(false, call, null, iOException);
                    return;
                }
                CacheCall.access$008(CacheCall.this);
                CacheCall.this.baseRequest.generateCall(call.request()).enqueue(this);
            }

            @Override // okhttp3.Callback
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                int iCode = response.code();
                if (iCode == 304 && cacheMode == CacheMode.DEFAULT) {
                    if (CacheCall.this.cacheEntity == null) {
                        CacheCall.this.sendFailResultCallback(true, call, response, OkGoException.INSTANCE("服务器响应码304，但是客户端没有缓存！"));
                        return;
                    }
                    Object data3 = CacheCall.this.cacheEntity.getData();
                    HttpHeaders responseHeaders3 = CacheCall.this.cacheEntity.getResponseHeaders();
                    if (data3 == null || responseHeaders3 == null) {
                        CacheCall.this.sendFailResultCallback(true, call, response, OkGoException.INSTANCE("没有获取到缓存,或者缓存已经过期!"));
                        return;
                    } else {
                        CacheCall.this.sendSuccessResultCallback(true, data3, call, response);
                        return;
                    }
                }
                if (iCode == 404 || iCode >= 500) {
                    CacheCall.this.sendFailResultCallback(false, call, response, OkGoException.INSTANCE("服务器数据异常!"));
                    return;
                }
                try {
                    Object objBody = CacheCall.this.parseResponse(response).body();
                    CacheCall.this.handleCache(response.headers(), objBody);
                    CacheCall.this.sendSuccessResultCallback(false, objBody, call, response);
                } catch (Exception e) {
                    CacheCall.this.sendFailResultCallback(false, call, response, e);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCache(Headers headers, T t) {
        if (this.baseRequest.getCacheMode() == CacheMode.NO_CACHE || (t instanceof Bitmap)) {
            return;
        }
        CacheEntity<T> cacheEntityCreateCacheEntity = HeaderParser.createCacheEntity(headers, t, this.baseRequest.getCacheMode(), this.baseRequest.getCacheKey());
        if (cacheEntityCreateCacheEntity == null) {
            CacheManager.INSTANCE.remove(this.baseRequest.getCacheKey());
        } else {
            CacheManager.INSTANCE.replace(this.baseRequest.getCacheKey(), cacheEntityCreateCacheEntity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendFailResultCallback(final boolean z, final okhttp3.Call call, final Response response, final Exception exc) {
        final CacheMode cacheMode = this.baseRequest.getCacheMode();
        OkGo.getInstance().getDelivery().post(new Runnable() { // from class: com.lzy.okgo.adapter.CacheCall.2
            @Override // java.lang.Runnable
            public void run() {
                if (z) {
                    CacheCall.this.mCallback.onCacheError(call, exc);
                    if (cacheMode == CacheMode.DEFAULT || cacheMode == CacheMode.REQUEST_FAILED_READ_CACHE) {
                        CacheCall.this.mCallback.onAfter(null, exc);
                        return;
                    }
                    return;
                }
                CacheCall.this.mCallback.onError(call, response, exc);
                if (cacheMode != CacheMode.REQUEST_FAILED_READ_CACHE) {
                    CacheCall.this.mCallback.onAfter(null, exc);
                }
            }
        });
        if (z || cacheMode != CacheMode.REQUEST_FAILED_READ_CACHE) {
            return;
        }
        CacheEntity<T> cacheEntity = this.cacheEntity;
        if (cacheEntity != null && !cacheEntity.isExpire()) {
            T data = this.cacheEntity.getData();
            HttpHeaders responseHeaders = this.cacheEntity.getResponseHeaders();
            if (data == null || responseHeaders == null) {
                sendFailResultCallback(true, call, response, OkGoException.INSTANCE("没有获取到缓存,或者缓存已经过期!"));
                return;
            } else {
                sendSuccessResultCallback(true, data, call, response);
                return;
            }
        }
        sendFailResultCallback(true, call, response, OkGoException.INSTANCE("没有获取到缓存,或者缓存已经过期!"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSuccessResultCallback(final boolean z, final T t, final okhttp3.Call call, final Response response) {
        final CacheMode cacheMode = this.baseRequest.getCacheMode();
        OkGo.getInstance().getDelivery().post(new Runnable() { // from class: com.lzy.okgo.adapter.CacheCall.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                if (z) {
                    CacheCall.this.mCallback.onCacheSuccess(t, call);
                    if (cacheMode == CacheMode.DEFAULT || cacheMode == CacheMode.REQUEST_FAILED_READ_CACHE || cacheMode == CacheMode.IF_NONE_CACHE_REQUEST) {
                        CacheCall.this.mCallback.onAfter(t, null);
                        return;
                    }
                    return;
                }
                CacheCall.this.mCallback.onSuccess(t, call, response);
                CacheCall.this.mCallback.onAfter(t, null);
            }
        });
    }

    @Override // com.lzy.okgo.adapter.Call
    public com.lzy.okgo.model.Response<T> execute() throws Exception {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already executed.");
            }
            this.executed = true;
        }
        okhttp3.Call call = this.baseRequest.getCall();
        if (this.canceled) {
            call.cancel();
        }
        return parseResponse(call.execute());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.lzy.okgo.model.Response<T> parseResponse(Response response) throws Exception {
        return com.lzy.okgo.model.Response.success(this.baseRequest.getConverter().convertSuccess(response), response);
    }

    @Override // com.lzy.okgo.adapter.Call
    public boolean isExecuted() {
        return this.executed;
    }

    @Override // com.lzy.okgo.adapter.Call
    public void cancel() {
        this.canceled = true;
        okhttp3.Call call = this.rawCall;
        if (call != null) {
            call.cancel();
        }
    }

    @Override // com.lzy.okgo.adapter.Call
    public boolean isCanceled() {
        return this.canceled;
    }

    @Override // com.lzy.okgo.adapter.Call
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Call<T> m2684clone() {
        return new CacheCall(this.baseRequest);
    }

    @Override // com.lzy.okgo.adapter.Call
    public BaseRequest getBaseRequest() {
        return this.baseRequest;
    }
}
