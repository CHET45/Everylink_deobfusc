package com.tencent.beacon.base.net.call;

import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.NetException;

/* JADX INFO: loaded from: classes4.dex */
public interface Callback<T> {
    void onFailure(C2684d c2684d);

    void onResponse(T t) throws NetException;
}
