package com.tencent.beacon.p015a.p017b;

import com.tencent.beacon.base.net.C2671c;
import com.tencent.beacon.base.net.HttpMethod;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.HttpRequestEntity;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import java.util.LinkedHashMap;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.d */
/* JADX INFO: compiled from: AbstractAttaReport.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2618d implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ String f1031a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ String f1032b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ Throwable f1033c;

    /* JADX INFO: renamed from: d */
    final /* synthetic */ boolean f1034d;

    /* JADX INFO: renamed from: e */
    final /* synthetic */ Callback f1035e;

    /* JADX INFO: renamed from: f */
    final /* synthetic */ AbstractC2622h f1036f;

    RunnableC2618d(AbstractC2622h abstractC2622h, String str, String str2, Throwable th, boolean z, Callback callback) {
        this.f1036f = abstractC2622h;
        this.f1031a = str;
        this.f1032b = str2;
        this.f1033c = th;
        this.f1034d = z;
        this.f1035e = callback;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.f1036f.f1047d) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(AbstractC2622h.f1044a);
            linkedHashMap.put("error_code", this.f1031a);
            linkedHashMap.put("error_msg", this.f1032b);
            linkedHashMap.put("error_stack_full", C2694b.m1435a(this.f1033c));
            linkedHashMap.put("_dc", String.valueOf(Math.random()));
            C2671c.m1351d().m1355a(HttpRequestEntity.builder().m1371b(this.f1034d ? "https://htrace.wetvinfo.com/kv" : "https://h.trace.qq.com/kv").m1368a("atta").m1369a(linkedHashMap).m1367a(HttpMethod.POST).m1370a()).m1384a(this.f1035e);
            C2695c.m1474d("[atta] upload a new error, errorCode: %s, message: %s, stack: %s", this.f1031a, this.f1032b, C2694b.m1435a(this.f1033c));
        }
    }
}
