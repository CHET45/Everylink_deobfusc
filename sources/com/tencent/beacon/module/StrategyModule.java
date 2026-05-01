package com.tencent.beacon.module;

import android.content.Context;
import com.tencent.beacon.base.net.p021b.RunnableC2670e;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p028d.C2709a;
import com.tencent.beacon.p028d.C2710b;
import com.tencent.beacon.p028d.C2716h;
import com.tencent.beacon.p028d.RunnableC2717i;

/* JADX INFO: loaded from: classes4.dex */
public class StrategyModule implements BeaconModule {

    /* JADX INFO: renamed from: a */
    private static final Object f1739a = new Object();

    /* JADX INFO: renamed from: c */
    private RunnableC2717i f1741c;

    /* JADX INFO: renamed from: e */
    private boolean f1743e = false;

    /* JADX INFO: renamed from: d */
    private C2710b f1742d = C2710b.m1518d();

    /* JADX INFO: renamed from: b */
    private C2709a f1740b = C2709a.m1508a();

    public StrategyModule() {
        C2716h.m1581d().m1583a(this.f1740b);
        this.f1741c = new RunnableC2717i(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: d */
    public synchronized void m1781d() {
        if (!this.f1741c.m1605a()) {
            AbstractC2616b.m1001a().mo1011a(this.f1741c);
        }
    }

    /* JADX INFO: renamed from: c */
    public boolean m1785c() {
        boolean z;
        synchronized (f1739a) {
            z = this.f1743e;
        }
        return z;
    }

    @Override // com.tencent.beacon.module.BeaconModule
    /* JADX INFO: renamed from: a */
    public void mo1746a(Context context) {
        C2695c.m1464a("[module] strategy module > TRUE", new Object[0]);
        this.f1741c.m1608b();
        m1781d();
        RunnableC2670e.m1348a(context, new RunnableC2670e.a() { // from class: com.tencent.beacon.module.StrategyModule.1
            @Override // com.tencent.beacon.base.net.p021b.RunnableC2670e.a
            /* JADX INFO: renamed from: a */
            public void mo1090a() {
                synchronized (StrategyModule.this) {
                    if (!StrategyModule.this.m1785c() && !StrategyModule.this.f1741c.m1605a()) {
                        StrategyModule.this.m1781d();
                    }
                }
            }

            @Override // com.tencent.beacon.base.net.p021b.RunnableC2670e.a
            /* JADX INFO: renamed from: b */
            public void mo1091b() {
            }
        });
    }

    /* JADX INFO: renamed from: b */
    public C2710b m1784b() {
        return this.f1742d;
    }

    /* JADX INFO: renamed from: a */
    public void m1783a(boolean z) {
        synchronized (f1739a) {
            this.f1743e = z;
        }
    }

    /* JADX INFO: renamed from: a */
    public C2709a m1782a() {
        return this.f1740b;
    }
}
