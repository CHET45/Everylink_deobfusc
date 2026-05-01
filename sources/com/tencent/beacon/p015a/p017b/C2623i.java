package com.tencent.beacon.p015a.p017b;

/* JADX INFO: renamed from: com.tencent.beacon.a.b.i */
/* JADX INFO: compiled from: AttaAggregateReport.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2623i extends AbstractC2622h {

    /* JADX INFO: renamed from: e */
    private static volatile C2623i f1048e;

    private C2623i() {
    }

    /* JADX INFO: renamed from: e */
    public static C2623i m1030e() {
        if (f1048e == null) {
            synchronized (C2623i.class) {
                if (f1048e == null) {
                    f1048e = new C2623i();
                }
            }
        }
        return f1048e;
    }

    @Override // com.tencent.beacon.p015a.p017b.AbstractC2622h
    /* JADX INFO: renamed from: b */
    String mo1027b() {
        return "03300051017";
    }

    @Override // com.tencent.beacon.p015a.p017b.AbstractC2622h
    /* JADX INFO: renamed from: c */
    String mo1028c() {
        return "9462881773";
    }
}
