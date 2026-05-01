package com.tencent.beacon.base.net;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.d */
/* JADX INFO: compiled from: NetFailure.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2684d {

    /* JADX INFO: renamed from: a */
    public String f1363a;

    /* JADX INFO: renamed from: b */
    public String f1364b;

    /* JADX INFO: renamed from: c */
    public int f1365c;

    /* JADX INFO: renamed from: d */
    public String f1366d;

    /* JADX INFO: renamed from: e */
    public Throwable f1367e;

    public C2684d(String str, String str2, int i, String str3) {
        this.f1363a = str;
        this.f1364b = str2;
        this.f1365c = i;
        this.f1366d = str3;
    }

    public String toString() {
        return "NetFailure{requestType='" + this.f1363a + "', attaCode='" + this.f1364b + "', responseCode=" + this.f1365c + ", msg='" + this.f1366d + "', exception=" + this.f1367e + '}';
    }

    public C2684d(String str, String str2, int i, String str3, Throwable th) {
        this.f1363a = str;
        this.f1364b = str2;
        this.f1365c = i;
        this.f1366d = str3;
        this.f1367e = th;
    }
}
