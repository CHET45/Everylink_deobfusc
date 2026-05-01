package com.tencent.beacon.base.net.p021b;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.azure.xml.implementation.aalto.util.XmlConsts;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.lzy.okgo.model.HttpHeaders;
import com.microsoft.azure.storage.Constants;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.base.util.C2700h;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2632e;
import com.tencent.beacon.p015a.p018c.C2634g;
import com.tencent.beacon.p028d.C2710b;
import com.tencent.beacon.p028d.C2716h;
import com.tencent.beacon.pack.RequestPackage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.d */
/* JADX INFO: compiled from: NetUtils.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2669d {

    /* JADX INFO: renamed from: a */
    private static final Map<String, String> f1317a;

    /* JADX INFO: renamed from: b */
    private static final Map<String, String> f1318b;

    static {
        HashMap map = new HashMap(8);
        f1317a = map;
        HashMap map2 = new HashMap(7);
        f1318b = map2;
        map.put("wup_version", "3.0");
        map.put("TYPE_COMPRESS", String.valueOf(2));
        map.put("encr_type", "rsapost");
        map.put("Content-Type", "jce");
        map.put("zip_type", String.valueOf(C2710b.m1518d().m1534b()));
        map2.putAll(map);
    }

    /* JADX INFO: renamed from: a */
    public static void m1336a(Map<String, List<String>> map) {
        List<String> list;
        if (map == null) {
            return;
        }
        try {
            if (map.containsKey("sid") && (list = map.get("sid")) != null) {
                m1342c(String.valueOf(list.get(0)));
            }
            if (map.containsKey(HttpHeaders.HEAD_KEY_SET_COOKIE)) {
                for (String str : map.get(HttpHeaders.HEAD_KEY_SET_COOKIE)) {
                    if (str.contains("tgw_l7_route")) {
                        m1340b(str.substring(0, str.indexOf(59)));
                    }
                }
            }
        } catch (Throwable th) {
            String str2 = "parse http header fail message: " + th.getMessage();
            C2695c.m1468b(str2, new Object[0]);
            C2624j.m1031e().m1023a("457", str2);
        }
    }

    /* JADX INFO: renamed from: b */
    private static void m1340b(String str) {
        if (str == null) {
            return;
        }
        C2695c.m1464a("[BeaconNet]", "update ias cookie: %s", str);
        C2716h.m1581d().m1588c(str);
    }

    /* JADX INFO: renamed from: c */
    public static void m1343c(Map<String, String> map) {
        if (map == null) {
            return;
        }
        try {
            if (map.containsKey("sid")) {
                m1342c(String.valueOf(map.get("sid")));
            }
        } catch (Throwable th) {
            String str = "parse socket header fail message: " + th.getMessage();
            C2695c.m1468b(str, new Object[0]);
            C2624j.m1031e().m1023a("407", str);
        }
    }

    /* JADX INFO: renamed from: d */
    public static boolean m1344d() {
        NetworkInfo networkInfoM1338b = m1338b();
        return networkInfoM1338b != null && networkInfoM1338b.isConnected();
    }

    /* JADX INFO: renamed from: e */
    public static synchronized Map<String, String> m1345e() {
        Map<String, String> map;
        map = f1318b;
        map.put("is_quic", Constants.TRUE);
        map.put(XmlConsts.XML_DECL_KW_VERSION, "v2");
        map.put("zip_type", String.valueOf(2));
        return map;
    }

    /* JADX INFO: renamed from: f */
    private static void m1346f() {
        C2716h c2716hM1581d = C2716h.m1581d();
        if (c2716hM1581d == null) {
            return;
        }
        String strM1585b = c2716hM1581d.m1585b();
        if (strM1585b != null) {
            f1317a.put(HttpHeaders.HEAD_KEY_COOKIE, strM1585b);
        } else {
            C2695c.m1464a("no iasCookie in http header!", new Object[0]);
            C2624j.m1031e().m1023a("519", "no iasCookie in http header!");
        }
    }

    /* JADX INFO: renamed from: b */
    public static NetworkInfo m1338b() {
        if (!C2710b.m1518d().m1552h()) {
            return null;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) C2630c.m1059c().m1067b().getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            return connectivityManager.getActiveNetworkInfo();
        } catch (Throwable th) {
            C2695c.m1465a(th);
            return null;
        }
    }

    /* JADX INFO: renamed from: c */
    private static void m1342c(String str) {
        if (str == null) {
            return;
        }
        C2695c.m1463a("[BeaconNet]", "update strategy sid: %s", str);
        C2716h.m1581d().m1586b(str);
    }

    /* JADX INFO: renamed from: b */
    public static String m1339b(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.trim().length() > 0 && m1337a(key)) {
                String strTrim = key.trim();
                sb.append(PunctuationConst.AND);
                sb.append(strTrim.replace(PunctuationConst.f489OR, "%7C").replace(PunctuationConst.AND, "%26").replace(PunctuationConst.EQUAL, "%3D"));
                sb.append(PunctuationConst.EQUAL);
                String value = entry.getValue();
                if (value != null) {
                    sb.append(value.trim().replace('\n', ' ').replace(XmlConsts.CHAR_CR, ' ').replace(PunctuationConst.f489OR, "%7C").replace(PunctuationConst.AND, "%26").replace(PunctuationConst.EQUAL, "%3D"));
                }
            } else {
                C2695c.m1476e("[core] '%s' should be ASCII code in 32-126!", key);
            }
        }
        return sb.length() > 0 ? sb.substring(1) : "";
    }

    /* JADX INFO: renamed from: c */
    public static String m1341c() {
        NetworkInfo networkInfoM1338b = m1338b();
        return networkInfoM1338b == null ? "unknown" : networkInfoM1338b.getType() == 1 ? "wifi" : "";
    }

    /* JADX INFO: renamed from: a */
    public static RequestPackage m1332a(int i, byte[] bArr, Map<String, String> map, String str) {
        C2630c c2630cM1059c = C2630c.m1059c();
        C2632e c2632eM1082l = C2632e.m1082l();
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.model = C2634g.m1115e().m1129h();
        requestPackage.osVersion = c2632eM1082l.m1108t();
        requestPackage.platformId = c2630cM1059c.m1074g();
        requestPackage.appkey = str;
        requestPackage.appVersion = C2629b.m1042a();
        requestPackage.sdkId = c2630cM1059c.m1075h();
        requestPackage.sdkVersion = c2630cM1059c.m1076i();
        requestPackage.cmd = i;
        requestPackage.encryType = (byte) 3;
        requestPackage.zipType = (byte) 2;
        requestPackage.sBuffer = bArr;
        requestPackage.reserved = m1339b(map);
        return requestPackage;
    }

    /* JADX INFO: renamed from: a */
    public static Map<String, String> m1334a(String str, Map<String, String> map) {
        int i;
        if (map == null) {
            return null;
        }
        HashMap map2 = new HashMap();
        int i2 = 0;
        int length = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey() == null) {
                C2695c.m1468b("BeaconEvent key can't be null!!!", new Object[i2]);
                i = i2;
            } else {
                String strValueOf = String.valueOf(entry.getKey());
                int length2 = strValueOf.trim().length();
                if (length2 > 0 && m1337a(strValueOf)) {
                    String strTrim = strValueOf.trim();
                    if (length2 > 64) {
                        strTrim = strTrim.substring(i2, 64);
                        String str2 = "[event] eventName: " + str + ", key: " + strTrim + " should be less than 64!";
                        C2624j.m1031e().m1023a("102", str2);
                        C2700h.m1485a(str2);
                    }
                    String strReplace = strTrim.replace(PunctuationConst.f489OR, "%7C").replace(PunctuationConst.AND, "%26").replace(PunctuationConst.EQUAL, "%3D");
                    String strTrim2 = entry.getValue() == null ? "" : String.valueOf(entry.getValue()).trim();
                    if (strTrim2.length() > 20480) {
                        String str3 = "[event] eventName: " + str + ", key: " + strReplace + "'s value > 20K.";
                        C2624j.m1031e().m1023a("103", str3);
                        C2700h.m1485a(str3);
                        i = 0;
                        strTrim2 = strTrim2.substring(0, 20480);
                    } else {
                        i = 0;
                    }
                    String strReplace2 = strTrim2.replace('\n', ' ').replace(XmlConsts.CHAR_CR, ' ').replace(PunctuationConst.f489OR, "%7C").replace(PunctuationConst.AND, "%26").replace(PunctuationConst.EQUAL, "%3D");
                    map2.put(strReplace, strReplace2);
                    length += strReplace.length() + strReplace2.length();
                } else {
                    i = i2;
                    C2695c.m1476e("[core] '%s' should be ASCII code in 32-126!", strValueOf);
                    C2624j.m1031e().m1023a("102", "[event] eventName: " + str + ", key: " + strValueOf + " should be ASCII code in 32-126!");
                }
            }
            i2 = i;
        }
        if (length <= 46080) {
            return map2;
        }
        String str4 = "[event] eventName: " + str + " params > 45K";
        C2624j.m1031e().m1023a("104", str4);
        C2700h.m1485a(str4);
        return null;
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1337a(String str) {
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
    public static synchronized Map<String, String> m1333a() {
        Map<String, String> map;
        C2716h c2716hM1581d = C2716h.m1581d();
        if (TextUtils.isEmpty(c2716hM1581d.m1590f())) {
            C2695c.m1464a("defHeader: strategyHolder.getRsaEncryKey() is null, recreate it", new Object[0]);
            c2716hM1581d.m1591g();
        }
        map = f1317a;
        map.put("bea_key", c2716hM1581d.m1590f());
        if (C2710b.m1518d().m1559m() == 1) {
            map.put("bea_key_id", "1");
        }
        if (!C2710b.m1518d().m1520A()) {
            m1346f();
        }
        return map;
    }

    /* JADX INFO: renamed from: a */
    public static void m1335a(long j, long j2, String str) {
        C2695c.m1463a("[BeaconNet]", "fixBeaconInfo, serverTime: " + j2 + ",ip: " + str, new Object[0]);
        C2630c c2630cM1059c = C2630c.m1059c();
        c2630cM1059c.m1068b(str);
        c2630cM1059c.m1063a(j2 - ((j + C2694b.m1454c()) / 2));
    }
}
