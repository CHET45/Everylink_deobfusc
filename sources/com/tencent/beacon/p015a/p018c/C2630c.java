package com.tencent.beacon.p015a.p018c;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.beacon.module.BeaconModule;
import com.tencent.beacon.module.ModuleName;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.a.c.c */
/* JADX INFO: compiled from: BeaconInfo.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2630c {

    /* JADX INFO: renamed from: a */
    private static volatile C2630c f1080a;

    /* JADX INFO: renamed from: b */
    private Context f1081b;

    /* JADX INFO: renamed from: c */
    private byte f1082c;

    /* JADX INFO: renamed from: d */
    private String f1083d;

    /* JADX INFO: renamed from: f */
    private String f1085f;

    /* JADX INFO: renamed from: g */
    private long f1086g;

    /* JADX INFO: renamed from: e */
    private String f1084e = "";

    /* JADX INFO: renamed from: h */
    private String f1087h = "";

    /* JADX INFO: renamed from: i */
    private final Map<String, String> f1088i = new HashMap();

    /* JADX INFO: renamed from: j */
    private String f1089j = "";

    /* JADX INFO: renamed from: k */
    private String f1090k = "";

    /* JADX INFO: renamed from: l */
    private String f1091l = "";

    /* JADX INFO: renamed from: m */
    private boolean f1092m = true;

    public C2630c() {
        this.f1082c = (byte) -1;
        this.f1083d = "";
        this.f1085f = "";
        this.f1082c = (byte) 1;
        this.f1083d = "beacon";
        this.f1085f = "unknown";
    }

    /* JADX INFO: renamed from: c */
    public static C2630c m1059c() {
        if (f1080a == null) {
            synchronized (C2630c.class) {
                if (f1080a == null) {
                    f1080a = new C2630c();
                }
            }
        }
        return f1080a;
    }

    /* JADX INFO: renamed from: a */
    public synchronized String m1061a() {
        return this.f1085f;
    }

    /* JADX INFO: renamed from: b */
    public void m1068b(String str) {
        this.f1085f = str;
    }

    /* JADX INFO: renamed from: d */
    public String m1070d() {
        return this.f1091l;
    }

    /* JADX INFO: renamed from: e */
    public String m1072e() {
        return this.f1087h;
    }

    /* JADX INFO: renamed from: f */
    public String m1073f() {
        return this.f1090k;
    }

    /* JADX INFO: renamed from: g */
    public synchronized byte m1074g() {
        return this.f1082c;
    }

    /* JADX INFO: renamed from: h */
    public synchronized String m1075h() {
        return this.f1083d;
    }

    /* JADX INFO: renamed from: i */
    public String m1076i() {
        return "4.2.86.12-external";
    }

    /* JADX INFO: renamed from: j */
    public synchronized long m1077j() {
        return this.f1086g;
    }

    /* JADX INFO: renamed from: k */
    public String m1078k() {
        return this.f1089j;
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1063a(long j) {
        this.f1086g = j;
    }

    /* JADX INFO: renamed from: b */
    public synchronized Context m1067b() {
        return this.f1081b;
    }

    /* JADX INFO: renamed from: d */
    public void m1071d(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.f1090k = str;
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1064a(Context context) {
        if (this.f1081b == null) {
            Context applicationContext = context.getApplicationContext();
            this.f1081b = applicationContext;
            if (applicationContext == null) {
                this.f1081b = context;
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public String m1062a(String str) {
        String str2 = this.f1088i.get(str);
        return str2 == null ? "" : str2;
    }

    /* JADX INFO: renamed from: c */
    public void m1069c(String str) {
        this.f1087h = str;
    }

    /* JADX INFO: renamed from: a */
    public void m1065a(String str, String str2) {
        this.f1088i.put(str, str2);
    }

    /* JADX INFO: renamed from: a */
    public BeaconModule m1060a(ModuleName moduleName) {
        return BeaconModule.f1707a.get(moduleName);
    }

    /* JADX INFO: renamed from: a */
    public void m1066a(boolean z) {
        this.f1092m = z;
    }
}
