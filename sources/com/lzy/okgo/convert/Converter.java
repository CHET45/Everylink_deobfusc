package com.lzy.okgo.convert;

import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public interface Converter<T> {
    T convertSuccess(Response response) throws Exception;
}
