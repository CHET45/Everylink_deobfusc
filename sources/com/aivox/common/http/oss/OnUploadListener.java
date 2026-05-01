package com.aivox.common.http.oss;

/* JADX INFO: loaded from: classes.dex */
public interface OnUploadListener {
    void onFailure(int i);

    void onProgress(int i, long j, long j2, int i2);

    void onSuccess(int i, String str, String str2, long j);
}
