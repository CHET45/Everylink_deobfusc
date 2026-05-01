package com.lzy.okgo.callback;

import com.lzy.okgo.convert.StringConvert;
import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public abstract class StringCallback extends AbsCallback<String> {
    @Override // com.lzy.okgo.convert.Converter
    public String convertSuccess(Response response) throws Exception {
        String strConvertSuccess = StringConvert.create().convertSuccess(response);
        response.close();
        return strConvertSuccess;
    }
}
