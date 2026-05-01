package com.tencent.beacon.event.p031c;

import com.tencent.beacon.base.net.p020a.InterfaceC2642c;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.event.EventBean;
import com.tencent.beacon.event.p029a.C2723b;

/* JADX INFO: renamed from: com.tencent.beacon.event.c.b */
/* JADX INFO: compiled from: EventConverterFactory.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2730b extends InterfaceC2642c.a<EventBean, C2723b> {

    /* JADX INFO: renamed from: a */
    private final b f1536a = new b();

    /* JADX INFO: renamed from: b */
    private final a f1537b = new a();

    /* JADX INFO: renamed from: com.tencent.beacon.event.c.b$a */
    /* JADX INFO: compiled from: EventConverterFactory.java */
    static final class a implements InterfaceC2642c<EventBean, C2723b> {
        a() {
        }

        @Override // com.tencent.beacon.base.net.p020a.InterfaceC2642c
        /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public C2723b mo1161a(EventBean eventBean) {
            C2723b c2723b = new C2723b();
            c2723b.f1523b = eventBean.getEventTime();
            c2723b.f1525d = eventBean.getAppKey();
            byte[] bArrM1444a = C2694b.m1444a(eventBean);
            c2723b.f1526e = bArrM1444a;
            if (bArrM1444a != null) {
                c2723b.f1524c = bArrM1444a.length;
            }
            return c2723b;
        }
    }

    /* JADX INFO: renamed from: com.tencent.beacon.event.c.b$b */
    /* JADX INFO: compiled from: EventConverterFactory.java */
    static final class b implements InterfaceC2642c<C2723b, EventBean> {
        b() {
        }

        @Override // com.tencent.beacon.base.net.p020a.InterfaceC2642c
        /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public EventBean mo1161a(C2723b c2723b) {
            Object objM1433a = C2694b.m1433a(c2723b.f1526e);
            if (objM1433a == null || !(objM1433a instanceof EventBean)) {
                return null;
            }
            EventBean eventBean = (EventBean) objM1433a;
            eventBean.setCid(c2723b.f1522a);
            return eventBean;
        }
    }

    private C2730b() {
    }

    /* JADX INFO: renamed from: a */
    public static C2730b m1629a() {
        return new C2730b();
    }

    /* JADX INFO: renamed from: b */
    public InterfaceC2642c<EventBean, C2723b> m1630b() {
        return this.f1537b;
    }

    /* JADX INFO: renamed from: c */
    public InterfaceC2642c<C2723b, EventBean> m1631c() {
        return this.f1536a;
    }
}
