package com.tencent.beacon.base.util;

import android.text.TextUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2630c;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: renamed from: com.tencent.beacon.base.util.h */
/* JADX INFO: compiled from: StrictMode.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2700h {

    /* JADX INFO: renamed from: a */
    public static AtomicBoolean f1400a = new AtomicBoolean(false);

    /* JADX INFO: renamed from: a */
    public static void m1485a(String str) {
        C2695c.m1468b("[strict]  " + str, new Object[0]);
        if (f1400a.get()) {
            throw new IllegalStateException("[strict] " + str);
        }
    }

    /* JADX INFO: renamed from: a */
    public static void m1486a(Map map) {
        if (!f1400a.get() || map == null) {
            return;
        }
        for (Object obj : map.keySet()) {
            if (!(obj instanceof String)) {
                m1485a("Key必须为String类型!");
            }
            if (!(map.get(obj) instanceof String)) {
                m1485a("Value必须为String类型!");
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private static boolean m1487a() {
        return f1400a.get() || C2629b.m1051d(C2630c.m1059c().m1067b());
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1488a(String str, Object obj) {
        boolean zIsEmpty;
        if (obj instanceof String) {
            zIsEmpty = TextUtils.isEmpty((String) obj);
        } else {
            zIsEmpty = obj == null;
        }
        C2695c.m1464a(str + " " + (obj == null ? PunctuationConst.EQUAL : PunctuationConst.NOT) + "= null!", new Object[0]);
        if (zIsEmpty && m1487a()) {
            throw new NullPointerException(str + " == null!");
        }
        return zIsEmpty;
    }
}
