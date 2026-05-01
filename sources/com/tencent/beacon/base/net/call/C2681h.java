package com.tencent.beacon.base.net.call;

import com.tencent.beacon.base.net.C2671c;
import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.NetException;
import com.tencent.beacon.base.net.RequestType;
import com.tencent.beacon.base.net.p021b.C2669d;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.pack.ResponsePackage;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.call.h */
/* JADX INFO: compiled from: JceCall.java */
/* JADX INFO: loaded from: classes4.dex */
class C2681h implements Callback<byte[]> {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ Callback f1359a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C2682i f1360b;

    C2681h(C2682i c2682i, Callback callback) {
        this.f1360b = c2682i;
        this.f1359a = callback;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(byte[] bArr) throws NetException {
        ResponsePackage responsePackageMo1161a;
        byte[] bArr2;
        C2695c.m1463a("[BeaconNet]", "raw response size: " + bArr.length, new Object[0]);
        if (this.f1360b.f1361a.getType() == RequestType.EVENT) {
            responsePackageMo1161a = C2671c.m1351d().f1328f.m1165b().mo1161a(bArr);
            if (responsePackageMo1161a == null) {
                throw new NetException("ResponsePackageV2 == null");
            }
            bArr2 = null;
        } else {
            responsePackageMo1161a = C2671c.m1351d().f1327e.m1157b().mo1161a(bArr);
            if (responsePackageMo1161a == null) {
                throw new NetException("responsePackage == null");
            }
            ResponsePackage responsePackage = responsePackageMo1161a;
            if (responsePackage.cmd != this.f1360b.f1361a.getResponseCmd()) {
                throw new NetException("responsePackage.cmd != requestEntity.responseCmd");
            }
            if (responsePackage.result != 0) {
                throw new NetException("responsePackage.result != OK(0)");
            }
            bArr2 = responsePackage.sBuffer;
            if (bArr2 == null || bArr2.length <= 0) {
                throw new NetException("responsePackage.buffer == null");
            }
        }
        C2669d.m1335a(this.f1360b.f1362b, responsePackageMo1161a.serverTime, responsePackageMo1161a.srcGatewayIp);
        Callback callback = this.f1359a;
        if (callback != null) {
            callback.onResponse(bArr2);
        }
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(C2684d c2684d) {
        Callback callback = this.f1359a;
        if (callback != null) {
            callback.onFailure(c2684d);
        }
    }
}
