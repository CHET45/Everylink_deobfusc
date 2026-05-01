package com.tencent.beacon.p015a.p017b;

import android.content.Context;
import com.aivox.base.util.SPUtil;
import com.tencent.beacon.base.net.C2671c;
import com.tencent.beacon.base.net.HttpMethod;
import com.tencent.beacon.base.net.call.HttpRequestEntity;
import com.tencent.beacon.p015a.p018c.C2630c;
import java.util.LinkedHashMap;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.g */
/* JADX INFO: compiled from: AbstractAttaReport.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2621g implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ String f1039a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ String f1040b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ String f1041c;

    /* JADX INFO: renamed from: d */
    final /* synthetic */ Context f1042d;

    /* JADX INFO: renamed from: e */
    final /* synthetic */ AbstractC2622h f1043e;

    RunnableC2621g(AbstractC2622h abstractC2622h, String str, String str2, String str3, Context context) {
        this.f1043e = abstractC2622h;
        this.f1039a = str;
        this.f1040b = str2;
        this.f1041c = str3;
        this.f1042d = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.f1043e.f1047d) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("attaid", this.f1043e.mo1027b());
            linkedHashMap.put(SPUtil.TOKEN, this.f1043e.mo1028c());
            linkedHashMap.put("platform", "Android");
            linkedHashMap.put("uin", "");
            linkedHashMap.put("model", "");
            linkedHashMap.put("os", "");
            linkedHashMap.put("error_stack_full", "");
            linkedHashMap.put("app_version", "");
            linkedHashMap.put("sdk_version", C2630c.m1059c().m1076i());
            linkedHashMap.put("product_id", this.f1039a);
            linkedHashMap.put("error_code", this.f1040b);
            linkedHashMap.put("error_msg", this.f1041c);
            linkedHashMap.put("package_name", this.f1042d.getPackageName());
            linkedHashMap.put("_dc", String.valueOf(Math.random()));
            C2671c.m1351d().m1355a(HttpRequestEntity.builder().m1371b("https://h.trace.qq.com/kv").m1368a("atta").m1369a(linkedHashMap).m1367a(HttpMethod.POST).m1370a()).m1384a(new C2620f(this));
        }
    }
}
