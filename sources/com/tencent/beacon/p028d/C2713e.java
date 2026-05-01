package com.tencent.beacon.p028d;

import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.tencent.beacon.d.e */
/* JADX INFO: compiled from: ModuleStrategyBean.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2713e {

    /* JADX INFO: renamed from: a */
    private final int f1480a;

    /* JADX INFO: renamed from: b */
    public String f1481b = "";

    /* JADX INFO: renamed from: c */
    private boolean f1482c = false;

    /* JADX INFO: renamed from: d */
    private Map<String, String> f1483d = null;

    /* JADX INFO: renamed from: e */
    private Set<String> f1484e = null;

    /* JADX INFO: renamed from: f */
    private Set<String> f1485f = null;

    public C2713e(int i) {
        this.f1480a = i;
    }

    /* JADX INFO: renamed from: a */
    public void m1577a(boolean z) {
        this.f1482c = z;
    }

    /* JADX INFO: renamed from: b */
    public int m1578b() {
        return this.f1480a;
    }

    /* JADX INFO: renamed from: c */
    public boolean m1580c() {
        return this.f1482c;
    }

    /* JADX INFO: renamed from: a */
    public Map<String, String> m1574a() {
        return this.f1483d;
    }

    /* JADX INFO: renamed from: b */
    public void m1579b(Set<String> set) {
        this.f1485f = set;
    }

    /* JADX INFO: renamed from: a */
    public void m1575a(Map<String, String> map) {
        this.f1483d = map;
    }

    /* JADX INFO: renamed from: a */
    public void m1576a(Set<String> set) {
        this.f1484e = set;
    }
}
