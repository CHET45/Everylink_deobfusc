package com.tencent.beacon.base.util;

import android.os.Build;
import com.tencent.beacon.p028d.C2710b;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.tencent.beacon.base.util.g */
/* JADX INFO: compiled from: RootUtil.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2699g {

    /* JADX INFO: renamed from: a */
    private static boolean f1399a;

    /* JADX INFO: renamed from: a */
    public static boolean m1480a() {
        ArrayList<String> arrayListM1484e = m1484e();
        if (arrayListM1484e == null || arrayListM1484e.size() <= 0) {
            C2695c.m1464a("[core] no response}", new Object[0]);
            return false;
        }
        for (String str : arrayListM1484e) {
            C2695c.m1464a(str, new Object[0]);
            if (str.contains("not found")) {
                return false;
            }
        }
        C2695c.m1464a("[core] sufile}", new Object[0]);
        return true;
    }

    /* JADX INFO: renamed from: b */
    public static boolean m1481b() {
        try {
            if (new File("/system/app/Superuser.apk").exists()) {
                C2695c.m1464a("[core] super_apk", new Object[0]);
                return true;
            }
        } catch (Exception e) {
            C2695c.m1465a(e);
        }
        return false;
    }

    /* JADX INFO: renamed from: c */
    public static boolean m1482c() {
        String str = Build.TAGS;
        if (str == null || !str.contains("test-keys")) {
            return false;
        }
        C2695c.m1464a("[core] test-keys", new Object[0]);
        return true;
    }

    /* JADX INFO: renamed from: d */
    public static boolean m1483d() {
        if (!C2710b.m1518d().m1561o()) {
            C2695c.m1468b("isRooted is Disable", new Object[0]);
            return false;
        }
        if (f1399a) {
            return true;
        }
        boolean z = m1482c() || m1481b() || m1480a();
        f1399a = z;
        return z;
    }

    /* JADX INFO: renamed from: e */
    private static ArrayList<String> m1484e() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            Runtime runtime = Runtime.getRuntime();
            String[] strArr = new String[3];
            strArr[0] = "/system/bin/sh";
            strArr[1] = "-c";
            strArr[2] = "type su";
            Process processExec = runtime.exec(strArr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream(), Charset.forName("UTF-8")));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                arrayList.add(line);
            }
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(processExec.getErrorStream(), Charset.forName("UTF-8")));
            while (true) {
                String line2 = bufferedReader2.readLine();
                if (line2 == null) {
                    return arrayList;
                }
                arrayList.add(line2);
            }
        } catch (Throwable th) {
            C2695c.m1465a(th);
            return null;
        }
    }
}
