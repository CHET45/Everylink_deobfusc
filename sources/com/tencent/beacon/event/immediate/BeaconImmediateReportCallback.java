package com.tencent.beacon.event.immediate;

import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.RequestType;
import com.tencent.beacon.base.net.call.InterfaceC2678e;
import com.tencent.beacon.base.net.p021b.C2669d;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.event.C2737e;
import com.tencent.beacon.event.EventBean;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.pack.C2755a;
import com.tencent.beacon.pack.ResponsePackageV2;

/* JADX INFO: loaded from: classes4.dex */
public class BeaconImmediateReportCallback implements InterfaceC2678e<BeaconTransferResult> {

    /* JADX INFO: renamed from: a */
    private C2737e f1605a;

    /* JADX INFO: renamed from: b */
    private EventBean f1606b;

    /* JADX INFO: renamed from: c */
    private String f1607c;

    /* JADX INFO: renamed from: d */
    private long f1608d = C2694b.m1454c();

    public BeaconImmediateReportCallback(C2737e c2737e, EventBean eventBean, String str) {
        this.f1605a = c2737e;
        this.f1606b = eventBean;
        this.f1607c = str;
    }

    /* JADX INFO: renamed from: a */
    private void m1695a(C2684d c2684d) {
        C2695c.m1463a("[BeaconImmediateReportCallback]", c2684d.toString(), new Object[0]);
        C2624j.m1031e().m1024a(c2684d.f1364b, c2684d.f1366d, c2684d.f1367e);
        this.f1605a.m1674a(this.f1606b, this.f1607c);
    }

    public void onResponse(BeaconTransferResult beaconTransferResult) {
        if (beaconTransferResult == null) {
            m1695a(new C2684d(RequestType.LONG_CONNECTION.name(), "462", -1, "response fail! result is null"));
            return;
        }
        boolean z = beaconTransferResult.getCode() == 0 && beaconTransferResult.getBizCode() == 0;
        C2695c.m1463a("[BeaconImmediateReportCallback]", "result=%s, eventName=%s , logID=%s", beaconTransferResult.toString(), this.f1606b.getEventCode(), this.f1607c);
        if (!z) {
            m1695a(new C2684d(RequestType.LONG_CONNECTION.name(), "463", beaconTransferResult.getCode(), C2695c.m1471c("response fail! result = %s", beaconTransferResult.toString())));
            return;
        }
        byte[] bizBuffer = beaconTransferResult.getBizBuffer();
        ResponsePackageV2 responsePackageV2 = new ResponsePackageV2();
        try {
            responsePackageV2.readFrom(new C2755a(bizBuffer));
            C2669d.m1335a(this.f1608d, responsePackageV2.serverTime, responsePackageV2.srcGatewayIp);
        } catch (Throwable th) {
            C2695c.m1465a(th);
            m1695a(new C2684d(RequestType.LONG_CONNECTION.name(), "463", beaconTransferResult.getCode(), th.getMessage(), th));
        }
    }
}
