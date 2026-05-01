package com.lzy.okgo.model;

import okhttp3.Headers;

/* JADX INFO: loaded from: classes4.dex */
public final class Response<T> {
    private final T body;
    private final okhttp3.Response rawResponse;

    private Response(okhttp3.Response response, T t) {
        this.rawResponse = response;
        this.body = t;
    }

    public okhttp3.Response raw() {
        return this.rawResponse;
    }

    public int code() {
        return this.rawResponse.code();
    }

    public String message() {
        return this.rawResponse.message();
    }

    public Headers headers() {
        return this.rawResponse.headers();
    }

    public boolean isSuccessful() {
        return this.rawResponse.isSuccessful();
    }

    public T body() {
        return this.body;
    }

    public static <T> Response<T> success(T t, okhttp3.Response response) {
        if (response == null) {
            throw new NullPointerException("rawResponse == null");
        }
        if (!response.isSuccessful()) {
            throw new IllegalArgumentException("rawResponse must be successful response");
        }
        return new Response<>(response, t);
    }
}
