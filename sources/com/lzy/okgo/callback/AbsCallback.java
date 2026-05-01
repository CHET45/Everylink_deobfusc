package com.lzy.okgo.callback;

import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.request.BaseRequest;
import okhttp3.Call;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbsCallback<T> implements Converter<T> {
    public void downloadProgress(long j, long j2, float f, long j3) {
    }

    public void onBefore(BaseRequest baseRequest) {
    }

    public void onCacheError(Call call, Exception exc) {
    }

    public void onCacheSuccess(T t, Call call) {
    }

    public void onError(Call call, Response response, Exception exc) {
    }

    public abstract void onSuccess(T t, Call call, Response response);

    public void parseError(Call call, Exception exc) {
    }

    public void upProgress(long j, long j2, float f, long j3) {
    }

    public void onAfter(T t, Exception exc) {
        if (exc != null) {
            exc.printStackTrace();
        }
    }
}
