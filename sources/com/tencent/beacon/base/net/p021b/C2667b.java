package com.tencent.beacon.base.net.p021b;

import android.text.TextUtils;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.base.util.C2700h;
import com.tencent.beacon.p028d.C2710b;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.b.b */
/* JADX INFO: compiled from: Constant.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2667b {

    /* JADX INFO: renamed from: a */
    public static String f1310a = "https://otheve.beacon.qq.com/analytics/v2_upload";

    /* JADX INFO: renamed from: b */
    public static String f1311b = "https://othstr.beacon.qq.com/analytics/v2_upload";

    /* JADX INFO: renamed from: c */
    public static String f1312c = "oth.eve.mdt.qq.com";

    /* JADX INFO: renamed from: d */
    public static String f1313d = "oth.str.mdt.qq.com";

    /* JADX INFO: renamed from: e */
    public static String f1314e = "https://mqqeve.beacon.qq.com/analytics/v2_upload";

    /* JADX INFO: renamed from: f */
    public static String f1315f = "";

    /* JADX INFO: renamed from: g */
    private static boolean f1316g = false;

    /* JADX INFO: renamed from: a */
    public static String m1318a(String str) {
        if (str == null || "".equals(str)) {
            return "";
        }
        String str2 = str.contains("https") ? "https://" : "http://";
        int iIndexOf = str.indexOf(str2);
        if (iIndexOf == -1) {
            return str;
        }
        String strSubstring = str.substring(iIndexOf + str2.length(), str.indexOf("/", str2.length()));
        int iIndexOf2 = strSubstring.indexOf(":");
        return iIndexOf2 != -1 ? strSubstring.substring(0, iIndexOf2) : strSubstring;
    }

    /* JADX INFO: renamed from: b */
    public static void m1322b(String str) {
        if (TextUtils.isEmpty(str) || f1316g) {
            return;
        }
        f1312c = str;
    }

    /* JADX INFO: renamed from: c */
    public static void m1324c(String str) {
        if (TextUtils.isEmpty(str) || f1316g) {
            return;
        }
        f1310a = str;
    }

    /* JADX INFO: renamed from: d */
    public static void m1325d(String str) {
        if (TextUtils.isEmpty(str) || f1316g) {
            return;
        }
        f1313d = str;
    }

    /* JADX INFO: renamed from: e */
    public static void m1326e(String str) {
        if (TextUtils.isEmpty(str) || f1316g) {
            return;
        }
        f1311b = str;
    }

    /* JADX INFO: renamed from: b */
    public static String m1321b(boolean z) {
        return z ? f1313d : f1311b;
    }

    /* JADX INFO: renamed from: b */
    public static void m1323b(String str, String str2) {
        Pattern patternCompile = Pattern.compile("((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}");
        Matcher matcher = patternCompile.matcher(str);
        Matcher matcher2 = patternCompile.matcher(str2);
        if (matcher.matches() && matcher2.matches()) {
            f1313d = str;
            f1311b = f1311b.replace("othstr.beacon.qq.com", str);
            f1312c = str2;
            String strReplace = f1310a.replace("otheve.beacon.qq.com", str2);
            f1310a = strReplace;
            C2695c.m1464a("[event url] ip modified by api, socketStrategyHost: %s, httpsStrategyUrl: %s, socketLogHost: %s ,httpsLogUrl: %s", f1313d, f1311b, f1312c, strReplace);
            return;
        }
        C2700h.m1485a("[event url] set report ip is not valid IP address!");
    }

    /* JADX INFO: renamed from: a */
    public static String m1319a(boolean z) {
        return z ? f1312c : f1310a;
    }

    /* JADX INFO: renamed from: a */
    public static String m1317a() {
        int iM1544e = C2710b.m1518d().m1544e();
        if (iM1544e != 1) {
            return iM1544e != 2 ? "" : f1314e;
        }
        return "https://" + f1315f + ":8082";
    }

    /* JADX INFO: renamed from: a */
    public static void m1320a(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            f1313d = str;
            f1311b = f1311b.replace("othstr.beacon.qq.com", str);
            f1316g = true;
        }
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        f1312c = str2;
        f1310a = f1310a.replace("otheve.beacon.qq.com", str2);
        f1316g = true;
    }
}
