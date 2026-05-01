package com.tencent.beacon.base.util;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.beacon.base.net.p021b.C2654a;
import com.tencent.beacon.base.net.p021b.C2668c;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2632e;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.UByte;

/* JADX INFO: renamed from: com.tencent.beacon.base.util.b */
/* JADX INFO: compiled from: CoreUtils.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2694b {

    /* JADX INFO: renamed from: a */
    private static final Random f1390a = new Random();

    /* JADX INFO: renamed from: b */
    private static final AtomicInteger f1391b = new AtomicInteger(0);

    /* JADX INFO: renamed from: c */
    private static final SimpleDateFormat f1392c = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    /* JADX INFO: renamed from: a */
    public static boolean m1440a(long j, long j2) {
        SimpleDateFormat simpleDateFormat = f1392c;
        return simpleDateFormat.format(Long.valueOf(j)).equals(simpleDateFormat.format(Long.valueOf(j2)));
    }

    /* JADX INFO: renamed from: b */
    public static byte[] m1453b(byte[] bArr, int i, String str) {
        if (bArr == null || bArr.length <= 0 || i == -1) {
            return bArr;
        }
        try {
            return C2668c.m1327a(i, str, bArr);
        } catch (Throwable th) {
            C2695c.m1476e("data length: " + bArr.length + ",type:" + i + ",key: " + str + ",error: " + th.getMessage(), new Object[0]);
            C2624j.m1031e().m1024a("508", "data length: " + bArr.length + ",type:" + i + ",key: " + str, th);
            return null;
        }
    }

    /* JADX INFO: renamed from: c */
    public static long m1454c() {
        return new Date().getTime();
    }

    /* JADX INFO: renamed from: d */
    public static long m1457d() {
        return new Date().getTime() + C2630c.m1059c().m1077j();
    }

    /* JADX INFO: renamed from: e */
    public static String m1458e() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        } catch (Throwable th) {
            C2695c.m1465a(th);
            return "";
        }
    }

    /* JADX INFO: renamed from: a */
    public static byte[] m1444a(Object obj) {
        ObjectOutputStream objectOutputStream;
        if (!(obj instanceof Serializable)) {
            C2695c.m1468b("not serial obj ", new Object[0]);
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        } catch (Throwable th) {
            th = th;
            objectOutputStream = null;
        }
        try {
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            m1438a(objectOutputStream);
            m1438a(byteArrayOutputStream);
            return byteArray;
        } catch (Throwable th2) {
            th = th2;
            try {
                C2695c.m1465a(th);
                C2695c.m1468b(th.getMessage(), new Object[0]);
                C2624j.m1031e().m1024a("520", th.getMessage(), th);
                m1438a(objectOutputStream);
                m1438a(byteArrayOutputStream);
                return null;
            } catch (Throwable th3) {
                m1438a(objectOutputStream);
                m1438a(byteArrayOutputStream);
                throw th3;
            }
        }
    }

    /* JADX INFO: renamed from: c */
    public static String m1456c(String str) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("MD5").digest(str.getBytes(Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            for (byte b : bArrDigest) {
                int i = b & UByte.MAX_VALUE;
                if (i < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (Exception e) {
            C2695c.m1465a(e);
            return str;
        }
    }

    /* JADX INFO: renamed from: b */
    public static byte[] m1451b(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        C2695c.m1464a("zipType: %s len: %s", Integer.valueOf(i), Integer.valueOf(bArr.length));
        try {
            return C2654a.m1183a(i, bArr);
        } catch (Throwable th) {
            C2624j.m1031e().m1024a("521", "zipData length: " + bArr.length + ",type:" + i, th);
            C2695c.m1465a(th);
            C2695c.m1468b("zip err: %s", th.toString());
            return null;
        }
    }

    /* JADX INFO: renamed from: c */
    private static String m1455c(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("abcdef0123456789".charAt(random.nextInt(16)));
        }
        return stringBuffer.toString();
    }

    /* JADX INFO: renamed from: b */
    public static byte[] m1452b(byte[] bArr, int i, int i2, String str) {
        if (bArr == null) {
            return null;
        }
        try {
            return m1447a(m1451b(bArr, i), i2, str);
        } catch (Throwable th) {
            C2695c.m1465a(th);
            return null;
        }
    }

    /* JADX INFO: renamed from: b */
    public static String m1449b(String str) {
        String strM1456c = m1456c(str);
        if (strM1456c == null) {
            return strM1456c;
        }
        try {
            return strM1456c.substring(8, 24);
        } catch (Exception e) {
            C2695c.m1465a(e);
            return strM1456c;
        }
    }

    /* JADX INFO: renamed from: b */
    public static String m1448b() {
        String strM1072e;
        C2630c c2630cM1059c = C2630c.m1059c();
        if (c2630cM1059c == null) {
            strM1072e = "";
        } else {
            strM1072e = c2630cM1059c.m1072e();
        }
        return m1449b(strM1072e + PunctuationConst.UNDERLINE + C2632e.m1082l().m1093d() + PunctuationConst.UNDERLINE + m1454c() + PunctuationConst.UNDERLINE + (f1390a.nextInt(2147473647) + 1000));
    }

    /* JADX INFO: renamed from: a */
    public static Object m1433a(byte[] bArr) {
        Throwable th;
        ObjectInputStream objectInputStream;
        if (bArr == null) {
            return null;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
        } catch (Throwable th2) {
            th = th2;
            objectInputStream = null;
        }
        try {
            Object object = objectInputStream.readObject();
            m1438a(objectInputStream);
            m1438a(byteArrayInputStream);
            return object;
        } catch (Throwable th3) {
            th = th3;
            try {
                C2695c.m1465a(th);
                C2695c.m1468b(th.getMessage(), new Object[0]);
                C2624j.m1031e().m1024a("520", th.getMessage(), th);
                m1438a(objectInputStream);
                m1438a(byteArrayInputStream);
                return null;
            } catch (Throwable th4) {
                m1438a(objectInputStream);
                m1438a(byteArrayInputStream);
                throw th4;
            }
        }
    }

    /* JADX INFO: renamed from: b */
    public static boolean m1450b(int i) {
        return f1390a.nextInt(10000) < i;
    }

    /* JADX INFO: renamed from: a */
    public static byte[] m1447a(byte[] bArr, int i, String str) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        C2695c.m1463a("CoreUtils", "encry data length:%d type: %d", Integer.valueOf(bArr.length), Integer.valueOf(i));
        try {
            return C2668c.m1330b(i, str, bArr);
        } catch (Throwable th) {
            C2695c.m1465a(th);
            C2624j.m1031e().m1024a("507", "data length: " + bArr.length + ",type:" + i + ",key: " + str, th);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    public static byte[] m1445a(byte[] bArr, int i) {
        if (bArr == null || i == -1) {
            return bArr;
        }
        C2695c.m1464a("unzp: %s len: %s", Integer.valueOf(i), Integer.valueOf(bArr.length));
        try {
            return C2654a.m1185b(i, bArr);
        } catch (Throwable th) {
            C2624j.m1031e().m1024a("521", "unzipData length: " + bArr.length + ",type:" + i, th);
            C2695c.m1465a(th);
            C2695c.m1468b("unzip err msg: " + th.getMessage(), new Object[0]);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    public static byte[] m1446a(byte[] bArr, int i, int i2, String str) {
        try {
            return m1445a(m1453b(bArr, i2, str), i);
        } catch (Exception e) {
            C2695c.m1465a(e);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    public static ArrayList<String> m1436a(String[] strArr) {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        Process processExec;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            processExec = Runtime.getRuntime().exec(strArr);
            bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream(), Charset.forName("UTF-8")));
        } catch (Throwable th) {
            th = th;
            bufferedReader = null;
            bufferedReader2 = null;
        }
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                arrayList.add(line);
            } catch (Throwable th2) {
                th = th2;
                bufferedReader2 = null;
            }
            try {
                C2695c.m1465a(th);
                m1438a(bufferedReader);
                m1438a(bufferedReader2);
                return null;
            } catch (Throwable th3) {
                m1438a(bufferedReader);
                m1438a(bufferedReader2);
                throw th3;
            }
        }
        bufferedReader2 = new BufferedReader(new InputStreamReader(processExec.getErrorStream(), Charset.forName("UTF-8")));
        while (true) {
            try {
                String line2 = bufferedReader2.readLine();
                if (line2 != null) {
                    arrayList.add(line2);
                } else {
                    m1438a(bufferedReader);
                    m1438a(bufferedReader2);
                    return arrayList;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static HashSet<String> m1437a(ArrayList<String> arrayList) {
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        HashSet<String> hashSet = new HashSet<>(arrayList.size());
        hashSet.addAll(arrayList);
        return hashSet;
    }

    /* JADX INFO: renamed from: a */
    public static int m1431a(String str, int i, int i2, int i3) {
        int i4;
        if (str == null) {
            return i;
        }
        try {
            i4 = Integer.parseInt(str);
        } catch (Exception e) {
            C2695c.m1465a(e);
            i4 = i;
        }
        return (i4 < i2 || i4 > i3) ? i : i4;
    }

    /* JADX INFO: renamed from: a */
    public static long m1432a(String str, long j, long j2, long j3) {
        long j4;
        if (str == null) {
            return j;
        }
        try {
            j4 = Long.parseLong(str);
        } catch (Exception e) {
            C2695c.m1465a(e);
            j4 = j;
        }
        return (j4 < j2 || j4 > j3) ? j : j4;
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1443a(String str, boolean z) {
        if (str == null) {
            return z;
        }
        if (str.toLowerCase().equals("y")) {
            return true;
        }
        if (str.toLowerCase().equals("n")) {
            return false;
        }
        return z;
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1442a(String str) {
        return str.startsWith("rqd_");
    }

    /* JADX INFO: renamed from: a */
    public static String m1435a(Throwable th) {
        if (th == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        String strReplace = (th.getMessage() + "\n" + stringWriter.getBuffer().toString()).replace("\t", " ").replace("\n", " ").replace(PunctuationConst.DOLLAR, "-");
        return strReplace.length() > 10240 ? strReplace.substring(0, 10240) : strReplace;
    }

    /* JADX INFO: renamed from: a */
    public static void m1438a(Closeable... closeableArr) {
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    C2695c.m1465a(e);
                }
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1441a(SharedPreferences.Editor editor) {
        if (editor != null) {
            return true;
        }
        C2695c.m1468b("BeaconProperties editor is null!", new Object[0]);
        return false;
    }

    /* JADX INFO: renamed from: a */
    public static String m1434a() {
        return m1455c(16);
    }

    /* JADX INFO: renamed from: a */
    public static boolean m1439a(int i) {
        if (C2630c.m1059c().m1067b() == null) {
            return false;
        }
        String strM1093d = C2632e.m1082l().m1093d();
        return !TextUtils.isEmpty(strM1093d) && ((double) Math.abs(strM1093d.hashCode() % 10000)) < ((double) i) * 1.0d;
    }
}
