package com.lzy.okgo.adapter;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.BaseRequest;

/* JADX INFO: loaded from: classes4.dex */
public interface Call<T> {
    void cancel();

    Call<T> clone();

    Response<T> execute() throws Exception;

    void execute(AbsCallback<T> absCallback);

    BaseRequest getBaseRequest();

    boolean isCanceled();

    boolean isExecuted();
}
