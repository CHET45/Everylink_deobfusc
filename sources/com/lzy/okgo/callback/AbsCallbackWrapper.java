package com.lzy.okgo.callback;

import okhttp3.Call;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class AbsCallbackWrapper<T> extends AbsCallback<T> {
    @Override // com.lzy.okgo.callback.AbsCallback
    public void onSuccess(T t, Call call, Response response) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lzy.okgo.convert.Converter
    public T convertSuccess(Response response) throws Exception {
        response.close();
        return response;
    }
}
