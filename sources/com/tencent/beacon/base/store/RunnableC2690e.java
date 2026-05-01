package com.tencent.beacon.base.store;

import com.tencent.beacon.p015a.p017b.C2624j;

/* JADX INFO: renamed from: com.tencent.beacon.base.store.e */
/* JADX INFO: compiled from: PropertiesFile.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2690e implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ C2692g f1378a;

    RunnableC2690e(C2692g c2692g) {
        this.f1378a = c2692g;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            synchronized (this.f1378a.f1381a) {
                byte[] bArrM1405a = C2692g.m1405a(this.f1378a.f1383c.toString().getBytes("ISO8859-1"));
                if (bArrM1405a == null) {
                    return;
                }
                if (bArrM1405a.length + 10 > this.f1378a.f1385e) {
                    this.f1378a.f1385e = bArrM1405a.length + 10;
                    C2692g c2692g = this.f1378a;
                    c2692g.m1402a(c2692g.f1385e);
                }
                this.f1378a.f1384d.putInt(0, bArrM1405a.length);
                this.f1378a.f1384d.position(10);
                this.f1378a.f1384d.put(bArrM1405a);
                this.f1378a.f1384d.force();
            }
        } catch (Exception e) {
            C2624j.m1031e().m1024a("504", "[properties] write to file error!", e);
        }
    }
}
