package com.tencent.beacon.event.p031c;

import android.os.Build;
import android.text.TextUtils;
import com.azure.xml.implementation.aalto.util.XmlConsts;
import com.github.houbb.heaven.util.lang.BoolUtil;
import com.tencent.beacon.base.net.RequestType;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.base.net.p021b.C2667b;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.base.util.C2700h;
import com.tencent.beacon.event.EventBean;
import com.tencent.beacon.event.open.EventType;
import com.tencent.beacon.module.BeaconModule;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2632e;
import com.tencent.beacon.p015a.p018c.C2634g;
import com.tencent.beacon.p028d.C2710b;
import com.tencent.beacon.pack.EventRecordV2;
import com.tencent.beacon.pack.RequestPackageV2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

/* JADX INFO: renamed from: com.tencent.beacon.event.c.d */
/* JADX INFO: compiled from: EventUtils.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2732d {

    /* JADX INFO: renamed from: a */
    private static final Pattern f1539a = Pattern.compile("rqd_.*");

    /* JADX INFO: renamed from: b */
    private static final Pattern f1540b = Pattern.compile("A[0-9]{1,3}");

    /* JADX INFO: renamed from: c */
    private static final Random f1541c = new Random();

    /* JADX INFO: renamed from: a */
    public static String m1636a(String str) {
        if (str != null && str.trim().length() != 0) {
            if (m1647g(str.trim())) {
                String strTrim = str.trim();
                return strTrim.length() > 16 ? strTrim.substring(0, 16) : strTrim;
            }
            C2695c.m1476e("[core] channelID should be Numeric! channelID:" + str, new Object[0]);
        }
        return "unknown";
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1640a(int i) {
        return i != 0;
    }

    /* JADX INFO: renamed from: b */
    private static EventRecordV2 m1641b(EventBean eventBean) {
        if (eventBean == null) {
            return null;
        }
        EventRecordV2 eventRecordV2 = new EventRecordV2();
        eventRecordV2.appKey = eventBean.getAppKey();
        eventRecordV2.apn = eventBean.getApn() != null ? eventBean.getApn() : "";
        eventRecordV2.srcIp = eventBean.getSrcIp() != null ? eventBean.getSrcIp() : "";
        eventRecordV2.eventCode = eventBean.getEventCode();
        eventRecordV2.valueType = eventBean.getValueType();
        eventRecordV2.mapValue = eventBean.getEventValue();
        eventRecordV2.byteValue = eventBean.getByteValue();
        eventRecordV2.eventTime = eventBean.getEventTime();
        eventRecordV2.eventResult = eventBean.getEventResult();
        eventRecordV2.eventType = eventBean.getEventType();
        eventRecordV2.reserved = eventBean.getReserved();
        return eventRecordV2;
    }

    /* JADX INFO: renamed from: c */
    public static String m1643c(String str) {
        return (str == null || str.length() == 0) ? "" : str;
    }

    /* JADX INFO: renamed from: d */
    public static String m1644d(String str) {
        return (str == null || str.length() == 0) ? "DefaultPageID" : str.length() > 50 ? str.substring(0, 50) : str;
    }

    /* JADX INFO: renamed from: e */
    public static String m1645e(String str) {
        if (str == null || str.length() == 0) {
            return "10000";
        }
        String strTrim = str.replace('|', '_').trim();
        if (!m1647g(strTrim)) {
            C2695c.m1476e("[core] userID should be ASCII code in 32-126! userID:" + str, new Object[0]);
            return "10000";
        }
        if (strTrim.length() < 5) {
            C2695c.m1476e("[core] userID length should < 5!", new Object[0]);
        }
        return strTrim.length() > 128 ? strTrim.substring(0, 128) : strTrim;
    }

    /* JADX INFO: renamed from: f */
    public static BeaconModule m1646f(String str) throws Exception {
        return (BeaconModule) Class.forName(str).newInstance();
    }

    /* JADX INFO: renamed from: g */
    private static boolean m1647g(String str) {
        int length = str.length();
        boolean z = true;
        while (true) {
            length--;
            if (length < 0) {
                return z;
            }
            char cCharAt = str.charAt(length);
            if (cCharAt < ' ' || cCharAt >= 127) {
                z = false;
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static RequestPackageV2 m1634a(EventBean eventBean) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(eventBean);
        return m1635a(arrayList);
    }

    /* JADX INFO: renamed from: a */
    public static RequestPackageV2 m1635a(List<EventBean> list) {
        RequestPackageV2 requestPackageV2 = new RequestPackageV2();
        requestPackageV2.appVersion = C2629b.m1042a();
        requestPackageV2.common = m1637a();
        ArrayList<EventRecordV2> arrayList = new ArrayList<>();
        Iterator<EventBean> it = list.iterator();
        while (it.hasNext()) {
            EventRecordV2 eventRecordV2M1641b = m1641b(it.next());
            if (eventRecordV2M1641b != null) {
                arrayList.add(eventRecordV2M1641b);
            }
        }
        requestPackageV2.events = arrayList;
        C2630c c2630cM1059c = C2630c.m1059c();
        requestPackageV2.mainAppKey = c2630cM1059c.m1072e();
        requestPackageV2.model = C2634g.m1115e().m1129h();
        requestPackageV2.osVersion = C2632e.m1082l().m1108t();
        requestPackageV2.packageName = C2629b.m1047b();
        requestPackageV2.platformId = c2630cM1059c.m1074g();
        requestPackageV2.sdkId = c2630cM1059c.m1075h();
        requestPackageV2.sdkVersion = c2630cM1059c.m1076i();
        requestPackageV2.reserved = "";
        if (C2710b.m1518d().m1572z()) {
            m1638a(requestPackageV2);
        }
        return requestPackageV2;
    }

    /* JADX INFO: renamed from: b */
    public static String m1642b(String str) {
        if (TextUtils.isEmpty(str)) {
            if (C2700h.f1400a.get()) {
                C2624j.m1031e().m1023a("101", "eventCode == null");
                C2700h.m1485a("eventCode == null");
            }
            return "";
        }
        String strTrim = str.replace('|', '_').trim();
        if (m1647g(strTrim)) {
            if (strTrim.length() <= 128) {
                return strTrim;
            }
            String str2 = str + " length > 128.";
            C2624j.m1031e().m1023a("101", str2);
            C2700h.m1485a(str2);
            return strTrim.substring(0, 128);
        }
        String str3 = str + " is not ASCII";
        C2624j.m1031e().m1023a("101", str3);
        C2700h.m1485a(str3);
        return "";
    }

    /* JADX INFO: renamed from: a */
    private static void m1638a(RequestPackageV2 requestPackageV2) {
        Set<String> setM1523a = C2710b.m1518d().m1523a();
        if (setM1523a != null && !setM1523a.isEmpty()) {
            Iterator<String> it = setM1523a.iterator();
            while (it.hasNext()) {
                requestPackageV2.common.put(it.next(), "");
            }
        }
        Set<String> setM1539c = C2710b.m1518d().m1539c();
        if (setM1539c == null || setM1539c.isEmpty()) {
            return;
        }
        for (EventRecordV2 eventRecordV2 : requestPackageV2.events) {
            for (String str : eventRecordV2.mapValue.keySet()) {
                if (setM1539c.contains(str)) {
                    eventRecordV2.mapValue.put(str, "");
                }
            }
        }
    }

    /* JADX INFO: renamed from: a */
    private static Map<String, String> m1637a() {
        HashMap map = new HashMap();
        C2632e c2632eM1082l = C2632e.m1082l();
        C2630c c2630cM1059c = C2630c.m1059c();
        map.put("A31", "" + c2632eM1082l.m1104p());
        map.put("A67", C2629b.m1049c(c2630cM1059c.m1067b()));
        map.put("A76", C2629b.m1050d());
        map.put("A89", c2632eM1082l.m1089a(c2630cM1059c.m1067b()));
        map.put("A52", String.valueOf(c2632eM1082l.m1114z()));
        map.put("A58", c2632eM1082l.m1101m() ? BoolUtil.f541Y : "N");
        map.put("A12", c2632eM1082l.m1102n());
        map.put("A17", c2632eM1082l.m1110v());
        map.put("A159", c2632eM1082l.m1088F());
        map.put("A9", Build.BRAND);
        map.put("A158", c2632eM1082l.m1093d());
        C2634g c2634gM1115e = C2634g.m1115e();
        if (!TextUtils.isEmpty(c2634gM1115e.m1129h())) {
            map.put("A10", c2634gM1115e.m1129h());
        }
        if (!TextUtils.isEmpty(c2634gM1115e.m1118b())) {
            map.put("A2", "" + c2634gM1115e.m1118b());
        }
        if (!TextUtils.isEmpty(c2634gM1115e.m1122d())) {
            map.put("A4", c2634gM1115e.m1122d());
        }
        if (!TextUtils.isEmpty(c2634gM1115e.m1125f())) {
            map.put("A6", c2634gM1115e.m1125f());
        }
        if (!TextUtils.isEmpty(c2634gM1115e.m1116a())) {
            map.put("A7", c2634gM1115e.m1116a());
        }
        if (!TextUtils.isEmpty(c2634gM1115e.m1133j())) {
            map.put("A20", c2634gM1115e.m1133j());
        }
        if (!TextUtils.isEmpty(c2634gM1115e.m1135k())) {
            map.put("A69", c2634gM1115e.m1135k());
        }
        if (!TextUtils.isEmpty(c2634gM1115e.m1131i())) {
            map.put("A144", c2634gM1115e.m1131i());
        }
        return map;
    }

    /* JADX INFO: renamed from: a */
    public static int m1632a(EventType eventType) {
        int i = C2731c.f1538a[eventType.ordinal()];
        if (i == 3 || i == 4) {
            return 1;
        }
        if (i != 5) {
            return i != 6 ? 0 : 2;
        }
        return 3;
    }

    /* JADX INFO: renamed from: a */
    public static JceRequestEntity m1633a(List<EventBean> list, boolean z) {
        return JceRequestEntity.builder().m1373a(RequestType.EVENT).m1372a(z ? 2 : 1).m1381b(C2667b.m1319a(false)).m1376a(C2667b.m1319a(true), 8081).m1375a(C2630c.m1059c().m1072e()).m1377a(XmlConsts.XML_DECL_KW_VERSION, "v2").m1374a(m1635a(list)).m1379a();
    }

    /* JADX INFO: renamed from: a */
    public static void m1639a(String str, Map<String, String> map) {
        if (C2694b.m1439a(1) && !f1539a.matcher(str).matches()) {
            StringBuilder sb = new StringBuilder();
            for (String str2 : map.keySet()) {
                if (f1540b.matcher(str2).matches()) {
                    sb.append(str2);
                    sb.append(" ");
                }
            }
            if (sb.length() == 0) {
                return;
            }
            String str3 = String.format("use Axx for key, eventCode is %s, key is %s.", str, sb.toString());
            C2695c.m1468b(str3, new Object[0]);
            C2624j.m1031e().m1023a("106", str3);
        }
    }
}
