package com.tencent.beacon.p026c;

import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.module.ModuleName;
import com.tencent.beacon.module.StatModule;
import com.tencent.beacon.p015a.p018c.C2630c;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.c.a */
/* JADX INFO: compiled from: FragmentTime.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2702a {

    /* JADX INFO: renamed from: a */
    private static Map<String, Long> f1401a = new HashMap();

    /* JADX INFO: renamed from: a */
    public static void m1490a(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        f1401a.put(str, Long.valueOf(jCurrentTimeMillis));
        C2695c.m1464a("[page] onPageIn cost time: %d", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
    }

    /* JADX INFO: renamed from: b */
    public static void m1491b(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        Map<String, Long> map = f1401a;
        if (map == null) {
            C2695c.m1476e("[page] please call 'onPageIn' first!", new Object[0]);
            return;
        }
        Long l = map.get(str);
        if (l == null) {
            C2695c.m1476e("[page] please call 'onPageIn' first!", new Object[0]);
            return;
        }
        ((StatModule) C2630c.m1059c().m1060a(ModuleName.STAT)).m1774a(str, jCurrentTimeMillis - l.longValue(), jCurrentTimeMillis);
        f1401a.remove(str);
        C2695c.m1464a("[page] onPageOut cost time: %d", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
    }
}
