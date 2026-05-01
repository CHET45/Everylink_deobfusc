package com.tencent.beacon.event;

import com.tencent.beacon.base.net.C2671c;
import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.event.p029a.C2722a;
import com.tencent.beacon.module.EventModule;
import com.tencent.beacon.module.ModuleName;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p015a.p018c.C2630c;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: renamed from: com.tencent.beacon.event.g */
/* JADX INFO: compiled from: EventReportCallback.java */
/* JADX INFO: loaded from: classes4.dex */
final class C2739g implements Callback<byte[]> {

    /* JADX INFO: renamed from: a */
    private final Set<Long> f1585a;

    /* JADX INFO: renamed from: b */
    private final String f1586b;

    /* JADX INFO: renamed from: c */
    private final String f1587c;

    /* JADX INFO: renamed from: d */
    private long f1588d = C2694b.m1454c();

    /* JADX INFO: renamed from: e */
    private RunnableC2740h f1589e;

    /* JADX INFO: renamed from: f */
    private String f1590f;

    /* JADX INFO: renamed from: g */
    private C2722a f1591g;

    C2739g(RunnableC2740h runnableC2740h, String str, C2722a c2722a, Set<Long> set, String str2) {
        this.f1589e = runnableC2740h;
        this.f1590f = str;
        this.f1591g = c2722a;
        this.f1585a = new HashSet(set);
        this.f1586b = "[EventReport(" + str + ")]";
        this.f1587c = str2;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(C2684d c2684d) {
        C2695c.m1461a(this.f1586b, 3, "send failure reason: %s. LogID: %s.", c2684d.toString(), this.f1587c);
        this.f1589e.m1692a(this.f1585a);
        if (c2684d.f1364b.equals("406")) {
            this.f1589e.m1690a();
            AbstractC2616b.m1001a().mo1011a(this.f1589e);
        }
        C2671c.m1351d().m1363e();
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(byte[] bArr) {
        C2695c.m1461a(this.f1586b, 3, "report success! offer task: %s! ", Boolean.valueOf(((EventModule) C2630c.m1059c().m1060a(ModuleName.EVENT)).m1764d().mo1678a(new RunnableC2738f(this, C2694b.m1454c() - this.f1588d))));
        if (this.f1585a.size() >= this.f1589e.m1693b()) {
            AbstractC2616b.m1001a().mo1011a(this.f1589e);
        }
        this.f1589e.m1694c();
        C2671c.m1351d().m1356a(0);
    }
}
