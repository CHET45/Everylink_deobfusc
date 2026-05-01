package com.tencent.beacon.p015a.p016a;

import android.util.SparseArray;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p015a.p017b.C2624j;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: renamed from: com.tencent.beacon.a.a.b */
/* JADX INFO: compiled from: BeaconBus.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2612b {

    /* JADX INFO: renamed from: a */
    private static volatile C2612b f1014a;

    /* JADX INFO: renamed from: c */
    private final Object f1016c = new Object();

    /* JADX INFO: renamed from: f */
    private AtomicBoolean f1019f = new AtomicBoolean(false);

    /* JADX INFO: renamed from: b */
    private final SparseArray<List<InterfaceC2614d>> f1015b = new SparseArray<>();

    /* JADX INFO: renamed from: d */
    private final SparseArray<List<C2613c>> f1017d = new SparseArray<>();

    /* JADX INFO: renamed from: e */
    private final SparseArray<Object> f1018e = new SparseArray<>();

    private C2612b() {
    }

    /* JADX INFO: renamed from: a */
    public static C2612b m991a() {
        if (f1014a == null) {
            synchronized (C2612b.class) {
                if (f1014a == null) {
                    f1014a = new C2612b();
                }
            }
        }
        return f1014a;
    }

    /* JADX INFO: renamed from: c */
    private void m994c(C2613c c2613c) {
        m995d(c2613c);
        synchronized (m992b(c2613c.f1020a)) {
            List<InterfaceC2614d> listM993c = m993c(c2613c.f1020a);
            if (listM993c == null) {
                return;
            }
            Iterator<InterfaceC2614d> it = listM993c.iterator();
            while (it.hasNext()) {
                try {
                    it.next().mo1000a(c2613c);
                } catch (Throwable th) {
                    C2695c.m1465a(th);
                    if (this.f1019f.compareAndSet(false, true)) {
                        C2624j.m1031e().m1024a("512", "dispatchEvent error", th);
                    }
                }
            }
        }
    }

    /* JADX INFO: renamed from: d */
    private void m995d(C2613c c2613c) {
    }

    /* JADX INFO: renamed from: b */
    public void m999b(C2613c c2613c) {
        synchronized (m992b(c2613c.f1020a)) {
            C2613c c2613c2 = new C2613c(c2613c.f1020a, c2613c.f1021b);
            List<C2613c> arrayList = this.f1017d.get(c2613c2.f1020a);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.f1017d.put(c2613c2.f1020a, arrayList);
            }
            arrayList.add(c2613c2);
            m994c(c2613c);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m997a(int i, InterfaceC2614d interfaceC2614d) {
        synchronized (m992b(i)) {
            List<InterfaceC2614d> arrayList = this.f1015b.get(i);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.f1015b.put(i, arrayList);
            }
            if (arrayList.contains(interfaceC2614d)) {
                return;
            }
            arrayList.add(interfaceC2614d);
            List<C2613c> list = this.f1017d.get(i);
            if (list != null) {
                Iterator<C2613c> it = list.iterator();
                while (it.hasNext()) {
                    try {
                        interfaceC2614d.mo1000a(it.next());
                    } catch (Throwable th) {
                        C2695c.m1465a(th);
                    }
                }
                if (i == 6 || i == 12) {
                    m996a(i);
                }
            }
        }
    }

    /* JADX INFO: renamed from: b */
    private Object m992b(int i) {
        Object obj;
        synchronized (this.f1016c) {
            obj = this.f1018e.get(i);
            if (obj == null) {
                obj = new Object();
                this.f1018e.put(i, obj);
            }
        }
        return obj;
    }

    /* JADX INFO: renamed from: c */
    private List<InterfaceC2614d> m993c(int i) {
        List<InterfaceC2614d> list = this.f1015b.get(i);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    /* JADX INFO: renamed from: a */
    public void m998a(C2613c c2613c) {
        AbstractC2616b.m1001a().mo1011a(new RunnableC2611a(this, c2613c));
    }

    /* JADX INFO: renamed from: a */
    public void m996a(int i) {
        synchronized (m992b(i)) {
            this.f1017d.remove(i);
        }
    }
}
