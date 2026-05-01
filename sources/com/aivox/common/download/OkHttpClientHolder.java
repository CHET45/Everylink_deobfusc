package com.aivox.common.download;

import okhttp3.OkHttpClient;

/* JADX INFO: loaded from: classes.dex */
public enum OkHttpClientHolder {
    INSTANCE;

    private final OkHttpClient okHttpClientDownload = new OkHttpClient();

    OkHttpClientHolder() {
    }

    public OkHttpClient getOkHttpClientDownload() {
        return this.okHttpClientDownload;
    }
}
