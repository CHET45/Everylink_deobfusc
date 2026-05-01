package com.aivox.common.speech2text;

/* JADX INFO: loaded from: classes.dex */
public interface ICommonTransCallback {
    void onComplete(String str);

    void onError(String str, boolean z);

    void onPrepare();

    void onProgress(String str, boolean z, String str2, int i, int i2, boolean z2);
}
