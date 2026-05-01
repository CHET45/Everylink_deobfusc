package com.aivox.base.http.progress;

import okhttp3.RequestBody;

/* JADX INFO: loaded from: classes.dex */
public class ProgressHelper {
    public static ProgressRequestBody addProgressRequestListener(RequestBody requestBody, ProgressRequestListener progressRequestListener) {
        return new ProgressRequestBody(requestBody, progressRequestListener);
    }
}
