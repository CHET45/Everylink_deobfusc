package com.tencent.beacon.p015a.p017b;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.aivox.base.util.SPUtil;
import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2632e;
import com.tencent.beacon.p015a.p018c.C2634g;
import com.tencent.beacon.p028d.C2710b;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.h */
/* JADX INFO: compiled from: AbstractAttaReport.java */
/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractC2622h {

    /* JADX INFO: renamed from: a */
    private static final Map<String, String> f1044a = new LinkedHashMap();

    /* JADX INFO: renamed from: b */
    private boolean f1045b = false;

    /* JADX INFO: renamed from: c */
    private boolean f1046c = false;

    /* JADX INFO: renamed from: d */
    private final Object f1047d = new Object();

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public synchronized void m1020b(String str, String str2, Throwable th) {
        m1025a(str, str2, th, true, new C2619e(this));
    }

    /* JADX INFO: renamed from: e */
    private synchronized void m1021e() {
        if (this.f1045b) {
            return;
        }
        Map<String, String> map = f1044a;
        map.put("attaid", mo1027b());
        map.put(SPUtil.TOKEN, mo1028c());
        map.put("error_code", "");
        map.put("platform", "Android");
        map.put("uin", C2632e.m1082l().m1093d());
        map.put("model", Build.BOARD + " " + C2634g.m1115e().m1129h());
        map.put("os", C2632e.m1082l().m1108t());
        map.put("error_msg", "");
        map.put("error_stack_full", "");
        map.put("app_version", C2629b.m1042a());
        map.put("sdk_version", C2630c.m1059c().m1076i());
        map.put("product_id", C2630c.m1059c().m1072e());
        map.put("_dc", "");
        map.put("package_name", C2629b.m1047b());
        this.f1045b = true;
    }

    /* JADX INFO: renamed from: b */
    abstract String mo1027b();

    /* JADX INFO: renamed from: c */
    abstract String mo1028c();

    /* JADX INFO: renamed from: d */
    public boolean m1029d() {
        if (this.f1046c) {
            return true;
        }
        if (!C2710b.m1518d().m1566t()) {
            C2695c.m1468b("atta report closed by user.", new Object[0]);
            return false;
        }
        C2695c.m1468b("atta report.", new Object[0]);
        if (C2695c.m1467a()) {
            return false;
        }
        return C2694b.m1439a(100);
    }

    /* JADX INFO: renamed from: a */
    public void m1026a(boolean z) {
        this.f1046c = z;
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1023a(String str, String str2) {
        m1024a(str, str2, null);
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1024a(String str, String str2, Throwable th) {
        m1025a(str, str2, th, false, new C2617c(this, str, str2, th));
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1025a(String str, String str2, Throwable th, boolean z, Callback<BResponse> callback) {
        if (m1029d()) {
            if (!this.f1045b) {
                m1021e();
            }
            if (TextUtils.isEmpty(str)) {
                C2695c.m1468b("[atta] errorCode isn't valid value!", new Object[0]);
            } else {
                AbstractC2616b.m1001a().mo1011a(new RunnableC2618d(this, str, str2, th, z, callback));
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1022a(Context context, String str, String str2, String str3) {
        if (C2694b.m1450b(100)) {
            return;
        }
        if (TextUtils.isEmpty(str2)) {
            C2695c.m1468b("[atta] errorCode isn't valid value!", new Object[0]);
            return;
        }
        if (TextUtils.isEmpty(str)) {
            C2695c.m1468b("init beacon twice, but appkey is null!", new Object[0]);
        } else if (context == null) {
            C2695c.m1468b("init beacon twice, but context is null!", new Object[0]);
        } else {
            AbstractC2616b.m1001a().mo1011a(new RunnableC2621g(this, str, str2, str3, context));
        }
    }
}
