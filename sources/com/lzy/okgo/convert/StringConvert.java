package com.lzy.okgo.convert;

import okhttp3.Response;

/* JADX INFO: loaded from: classes4.dex */
public class StringConvert implements Converter<String> {
    public static StringConvert create() {
        return ConvertHolder.convert;
    }

    private static class ConvertHolder {
        private static StringConvert convert = new StringConvert();

        private ConvertHolder() {
        }
    }

    @Override // com.lzy.okgo.convert.Converter
    public String convertSuccess(Response response) throws Exception {
        return response.body().string();
    }
}
