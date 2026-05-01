package com.aivox.app.util.agent;

/* JADX INFO: loaded from: classes.dex */
public interface ChatCallback {
    void onError(Throwable th);

    void onSuccess(String str);
}
