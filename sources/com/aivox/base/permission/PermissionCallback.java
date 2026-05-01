package com.aivox.base.permission;

/* JADX INFO: loaded from: classes.dex */
public interface PermissionCallback {
    void granted(boolean z);

    void requestError(Throwable th);
}
