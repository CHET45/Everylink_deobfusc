package com.tencent.beacon.base.util;

/* JADX INFO: renamed from: com.tencent.beacon.base.util.f */
/* JADX INFO: compiled from: Pools.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2698f<T> extends C2697e<T> {

    /* JADX INFO: renamed from: c */
    private final Object f1398c;

    public C2698f(int i) {
        super(i);
        this.f1398c = new Object();
    }

    @Override // com.tencent.beacon.base.util.C2697e
    /* JADX INFO: renamed from: a */
    public T mo1478a() {
        T t;
        synchronized (this.f1398c) {
            t = (T) super.mo1478a();
        }
        return t;
    }

    @Override // com.tencent.beacon.base.util.C2697e
    /* JADX INFO: renamed from: a */
    public boolean mo1479a(T t) {
        boolean zMo1479a;
        synchronized (this.f1398c) {
            zMo1479a = super.mo1479a(t);
        }
        return zMo1479a;
    }
}
