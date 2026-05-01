package com.tencent.beacon.event;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.beacon.base.util.C2695c;
import java.util.Iterator;

/* JADX INFO: renamed from: com.tencent.beacon.event.f */
/* JADX INFO: compiled from: EventReportCallback.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2738f implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ long f1583a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C2739g f1584b;

    RunnableC2738f(C2739g c2739g, long j) {
        this.f1584b = c2739g;
        this.f1583a = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        StringBuilder sb = new StringBuilder();
        Iterator it = this.f1584b.f1585a.iterator();
        while (it.hasNext()) {
            sb.append((Long) it.next()).append(PunctuationConst.COMMA);
        }
        C2695c.m1461a(this.f1584b.f1586b, 4, "delete: %s", Boolean.valueOf(this.f1584b.f1591g.m1622a(this.f1584b.f1590f, sb.substring(0, sb.lastIndexOf(PunctuationConst.COMMA)))));
        this.f1584b.f1589e.m1692a(this.f1584b.f1585a);
        this.f1584b.f1589e.m1691a(this.f1583a);
    }
}
