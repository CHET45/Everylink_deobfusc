package com.tencent.beacon.event;

import android.os.Handler;
import android.text.TextUtils;
import com.aivox.base.util.MyAnimationUtil;
import com.tencent.beacon.base.net.C2671c;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.event.open.EventResult;
import com.tencent.beacon.event.p029a.C2722a;
import com.tencent.beacon.event.p031c.C2732d;
import com.tencent.beacon.p015a.p016a.C2612b;
import com.tencent.beacon.p015a.p016a.C2613c;
import com.tencent.beacon.p015a.p016a.InterfaceC2614d;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p028d.C2710b;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.event.e */
/* JADX INFO: compiled from: EventManager.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2737e implements InterfaceC2741i, InterfaceC2614d {

    /* JADX INFO: renamed from: b */
    private final C2722a f1578b;

    /* JADX INFO: renamed from: c */
    private final RunnableC2740h f1579c;

    /* JADX INFO: renamed from: d */
    private final RunnableC2740h f1580d;

    /* JADX INFO: renamed from: e */
    private long f1581e = MyAnimationUtil.ANI_TIME_2000;

    /* JADX INFO: renamed from: f */
    private long f1582f = 5000;

    /* JADX INFO: renamed from: a */
    private final Handler f1577a = AbstractC2616b.m1001a().mo1005a(3000);

    public C2737e() {
        C2722a c2722aM1615a = C2722a.m1615a();
        this.f1578b = c2722aM1615a;
        this.f1579c = new RunnableC2740h(2000, c2722aM1615a, true);
        this.f1580d = new RunnableC2740h(1000, c2722aM1615a, false);
        C2612b.m991a().m997a(11, this);
        C2612b.m991a().m997a(2, this);
    }

    @Override // com.tencent.beacon.event.InterfaceC2741i
    /* JADX INFO: renamed from: b */
    public void mo1679b(String str, EventBean eventBean) {
        C2695c.m1463a("[EventManager]", "eventName is %s, logID is %s", eventBean.getEventCode(), str);
        AbstractC2616b.m1001a().mo1011a(new RunnableC2724b(this, eventBean, str));
    }

    @Override // com.tencent.beacon.event.InterfaceC2741i
    /* JADX INFO: renamed from: c */
    public EventResult mo1680c(String str, EventBean eventBean) {
        boolean zMo1678a = mo1678a(new RunnableC2721a(this, eventBean));
        C2695c.m1461a("[EventModule]", 1, "event: %s. go in EventManager(%s). offer: %s", eventBean.getEventCode(), eventBean.getAppKey(), Boolean.valueOf(zMo1678a));
        if (!zMo1678a) {
            return EventResult.C2742a.m1744a(103);
        }
        if (TextUtils.isEmpty(str)) {
            str = "-1";
        }
        return EventResult.C2742a.m1745a(Long.parseLong(str));
    }

    @Override // com.tencent.beacon.event.InterfaceC2741i
    /* JADX INFO: renamed from: a */
    public void mo1675a(String str, EventBean eventBean) {
        C2695c.m1464a("[EventManager]", "eventName is %s, logID is %s", eventBean.getEventCode(), str);
        AbstractC2616b.m1001a().mo1011a(new RunnableC2728c(this, eventBean, str));
    }

    /* JADX INFO: renamed from: a */
    public void m1674a(EventBean eventBean, String str) {
        Map<String, String> eventValue = eventBean.getEventValue();
        eventValue.put("A156", "N");
        eventBean.setEventValue(eventValue);
        if (C2710b.m1518d().m1571y()) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(eventBean);
            C2671c.m1351d().m1361b(C2732d.m1633a((List<EventBean>) arrayList, true)).m1389a(new C2736d(this, eventBean, str), AbstractC2616b.m1004b());
            return;
        }
        mo1680c(str, eventBean);
    }

    @Override // com.tencent.beacon.event.InterfaceC2741i
    /* JADX INFO: renamed from: a */
    public void mo1676a(boolean z) {
        if (z) {
            this.f1580d.run();
            this.f1579c.run();
        } else {
            AbstractC2616b.m1001a().mo1011a(this.f1580d);
            AbstractC2616b.m1001a().mo1011a(this.f1579c);
        }
    }

    @Override // com.tencent.beacon.event.InterfaceC2741i
    /* JADX INFO: renamed from: a */
    public void mo1672a() {
        AbstractC2616b.m1001a().mo1007a(2000, 0L, this.f1581e, this.f1579c);
        AbstractC2616b.m1001a().mo1007a(1000, 0L, this.f1582f, this.f1580d);
    }

    @Override // com.tencent.beacon.event.InterfaceC2741i
    /* JADX INFO: renamed from: a */
    public void mo1677a(boolean z, int i) {
        AbstractC2616b.m1001a().mo1008a(2000, z, i);
        AbstractC2616b.m1001a().mo1008a(1000, z, i);
    }

    @Override // com.tencent.beacon.event.InterfaceC2741i
    /* JADX INFO: renamed from: a */
    public void mo1673a(int i) {
        int iM1557k;
        int iM1553i;
        if (C2710b.m1518d().m1564r()) {
            iM1557k = C2710b.m1518d().m1557k();
            iM1553i = C2710b.m1518d().m1553i();
        } else {
            iM1557k = 0;
            iM1553i = 0;
        }
        C2695c.m1464a(String.format("resumedPollingReport realtimeDelayTime: %s, normalDelayTime: %s", Integer.valueOf(iM1557k), Integer.valueOf(iM1553i)), new Object[0]);
        AbstractC2616b.m1001a().mo1006a(2000, iM1557k, i);
        AbstractC2616b.m1001a().mo1006a(1000, iM1553i, i);
    }

    @Override // com.tencent.beacon.event.InterfaceC2741i
    /* JADX INFO: renamed from: a */
    public boolean mo1678a(Runnable runnable) {
        return this.f1577a.post(runnable);
    }

    @Override // com.tencent.beacon.p015a.p016a.InterfaceC2614d
    /* JADX INFO: renamed from: a */
    public void mo1000a(C2613c c2613c) {
        int i = c2613c.f1020a;
        if (i == 2) {
            Map map = (Map) c2613c.f1021b.get("d_m");
            if (map != null) {
                if (this.f1581e == MyAnimationUtil.ANI_TIME_2000) {
                    this.f1581e = C2694b.m1432a((String) map.get("realtimePollingTime"), this.f1581e, 1000L, 10000L);
                }
                if (this.f1582f == 5000) {
                    this.f1582f = C2694b.m1432a((String) map.get("normalPollingTime"), this.f1582f, MyAnimationUtil.ANI_TIME_2000, 3600000L);
                }
            }
        } else if (i == 11) {
            Object obj = c2613c.f1021b.get("u_c_r_p");
            if (obj != null) {
                long jLongValue = ((Long) obj).longValue();
                if (jLongValue >= 1000 && jLongValue <= 10000) {
                    this.f1581e = jLongValue;
                }
            }
            Object obj2 = c2613c.f1021b.get("u_c_n_p");
            if (obj2 != null) {
                long jLongValue2 = ((Long) obj2).longValue();
                if (jLongValue2 >= MyAnimationUtil.ANI_TIME_2000 && jLongValue2 <= 3600000) {
                    this.f1582f = jLongValue2;
                }
            }
        }
        C2695c.m1463a("[EventManager]", "pollingTime maybe change, realtime: %s normal: %s", Long.valueOf(this.f1581e), Long.valueOf(this.f1582f));
    }
}
