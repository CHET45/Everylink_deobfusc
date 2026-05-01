package com.tencent.beacon.base.net.adapter;

import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.HttpRequestEntity;
import com.tencent.beacon.base.net.call.JceRequestEntity;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractNetAdapter {
    protected static final int CONNECT_TIMEOUT = 30000;
    protected static final int MAX_CONTENT_LENGTH = 65535;
    protected static final int READ_TIMEOUT = 10000;

    public abstract void request(HttpRequestEntity httpRequestEntity, Callback<BResponse> callback);

    public abstract void request(JceRequestEntity jceRequestEntity, Callback<byte[]> callback);
}
