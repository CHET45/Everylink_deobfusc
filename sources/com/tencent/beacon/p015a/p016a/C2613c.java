package com.tencent.beacon.p015a.p016a;

import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.a.a.c */
/* JADX INFO: compiled from: BusEvent.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2613c {

    /* JADX INFO: renamed from: a */
    public int f1020a;

    /* JADX INFO: renamed from: b */
    public Map<String, Object> f1021b;

    public C2613c(int i, Map<String, Object> map) {
        this.f1020a = i;
        this.f1021b = map;
    }

    public String toString() {
        return "BusEvent{channel=" + this.f1020a + ", params=" + this.f1021b + '}';
    }

    public C2613c(int i) {
        this.f1020a = i;
    }
}
