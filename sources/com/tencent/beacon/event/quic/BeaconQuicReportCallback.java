package com.tencent.beacon.event.quic;

import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.RequestType;
import com.tencent.beacon.base.net.p020a.C2644e;
import com.tencent.beacon.base.net.p020a.InterfaceC2642c;
import com.tencent.beacon.base.net.p021b.C2669d;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.event.C2737e;
import com.tencent.beacon.event.EventBean;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p028d.C2710b;
import com.tencent.beacon.pack.C2755a;
import com.tencent.beacon.pack.ResponsePackageV2;
import com.tencent.beacon.pack.SocketResponsePackage;

/* JADX INFO: loaded from: classes4.dex */
public class BeaconQuicReportCallback implements QuicRequestCallback<BeaconQuicReportResult> {
    private static final String TAG = "[BeaconQuicReportCallback]";
    private final EventBean eventBean;
    private final C2737e eventManager;
    private final boolean isQuic;
    private final String logID;
    private final InterfaceC2642c<byte[], SocketResponsePackage> responseConverter = new C2644e();
    private final long startTime = C2694b.m1454c();

    public BeaconQuicReportCallback(C2737e c2737e, EventBean eventBean, String str) {
        this.eventManager = c2737e;
        this.eventBean = eventBean;
        this.logID = str;
        this.isQuic = C2710b.m1518d().m1544e() == 1;
    }

    private byte[] analyzeData(byte[] bArr) {
        if (!this.isQuic) {
            return C2694b.m1445a(bArr, 2);
        }
        return this.responseConverter.mo1161a(C2694b.m1445a(bArr, 2)).body;
    }

    private void onFailure(C2684d c2684d) {
        C2695c.m1463a(TAG, c2684d.toString(), new Object[0]);
        C2624j.m1031e().m1024a(c2684d.f1364b, c2684d.f1366d, c2684d.f1367e);
        this.eventManager.m1674a(this.eventBean, this.logID);
    }

    @Override // com.tencent.beacon.event.quic.QuicRequestCallback
    public void onResponse(BeaconQuicReportResult beaconQuicReportResult) {
        if (beaconQuicReportResult == null) {
            onFailure(new C2684d(RequestType.QUIC.name(), this.isQuic ? "472" : "475", -1, "response fail! result is null"));
            return;
        }
        boolean z = beaconQuicReportResult.getCode() == 0;
        C2695c.m1463a(TAG, "result=%s, eventName=%s , logID=%s", beaconQuicReportResult.toString(), this.eventBean.getEventCode(), this.logID);
        if (!z) {
            onFailure(new C2684d(RequestType.QUIC.name(), this.isQuic ? "472" : "475", beaconQuicReportResult.getCode(), C2695c.m1471c("response fail! result = %s", beaconQuicReportResult.toString())));
            return;
        }
        try {
            byte[] bArrAnalyzeData = analyzeData(beaconQuicReportResult.getResBuffer());
            ResponsePackageV2 responsePackageV2 = new ResponsePackageV2();
            responsePackageV2.readFrom(new C2755a(bArrAnalyzeData));
            C2669d.m1335a(this.startTime, responsePackageV2.serverTime, responsePackageV2.srcGatewayIp);
        } catch (Throwable th) {
            C2695c.m1465a(th);
            onFailure(new C2684d(RequestType.QUIC.name(), this.isQuic ? "473" : "476", beaconQuicReportResult.getCode(), th.getMessage(), th));
        }
    }
}
