package com.tencent.beacon.base.util;

/* JADX INFO: renamed from: com.tencent.beacon.base.util.e */
/* JADX INFO: compiled from: Pools.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2697e<T> implements InterfaceC2696d<T> {

    /* JADX INFO: renamed from: a */
    private final Object[] f1396a;

    /* JADX INFO: renamed from: b */
    private int f1397b;

    public C2697e(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("The max pool size must be > 0");
        }
        this.f1396a = new Object[i];
    }

    /* JADX INFO: renamed from: b */
    private boolean m1477b(T t) {
        for (int i = 0; i < this.f1397b; i++) {
            if (this.f1396a[i] == t) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: a */
    public T mo1478a() {
        int i = this.f1397b;
        if (i <= 0) {
            return null;
        }
        int i2 = i - 1;
        Object[] objArr = this.f1396a;
        T t = (T) objArr[i2];
        objArr[i2] = null;
        this.f1397b = i2;
        return t;
    }

    /* JADX INFO: renamed from: a */
    public boolean mo1479a(T t) {
        if (!m1477b(t)) {
            int i = this.f1397b;
            Object[] objArr = this.f1396a;
            if (i >= objArr.length) {
                return false;
            }
            objArr[i] = t;
            this.f1397b = i + 1;
            return true;
        }
        throw new IllegalStateException("Already in the pool!");
    }
}
