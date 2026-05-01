package com.tencent.beacon.p015a.p017b;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.j */
/* JADX INFO: compiled from: AttaReport.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2624j extends AbstractC2622h {

    /* JADX INFO: renamed from: e */
    private static volatile C2624j f1049e;

    private C2624j() {
    }

    /* JADX INFO: renamed from: e */
    public static C2624j m1031e() {
        if (f1049e == null) {
            synchronized (C2624j.class) {
                if (f1049e == null) {
                    f1049e = new C2624j();
                }
            }
        }
        return f1049e;
    }

    @Override // com.tencent.beacon.p015a.p017b.AbstractC2622h
    /* JADX INFO: renamed from: b */
    String mo1027b() {
        return "00400014144";
    }

    @Override // com.tencent.beacon.p015a.p017b.AbstractC2622h
    /* JADX INFO: renamed from: c */
    String mo1028c() {
        return "6478159937";
    }
}
