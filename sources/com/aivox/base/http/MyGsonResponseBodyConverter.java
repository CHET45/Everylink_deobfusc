package com.aivox.base.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/* JADX INFO: loaded from: classes.dex */
final class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final TypeAdapter<T> adapter;
    private final Gson gson;

    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> typeAdapter) {
        this.gson = gson;
        this.adapter = typeAdapter;
    }

    @Override // retrofit2.Converter
    public T convert(ResponseBody responseBody) throws IOException {
        try {
            String strString = responseBody.string();
            MediaType mediaTypeContentType = responseBody.contentType();
            return this.adapter.read2(this.gson.newJsonReader(new InputStreamReader(new ByteArrayInputStream(strString.getBytes()), mediaTypeContentType != null ? mediaTypeContentType.charset(UTF_8) : UTF_8)));
        } finally {
            responseBody.close();
        }
    }
}
