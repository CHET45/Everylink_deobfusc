package com.tencent.beacon.base.store;

import android.content.Context;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import com.github.houbb.heaven.constant.FileOptionConst;
import com.tencent.beacon.base.net.p021b.C2668c;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.base.util.C2700h;
import com.tencent.beacon.base.util.C2701i;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p015a.p018c.C2634g;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: renamed from: com.tencent.beacon.base.store.g */
/* JADX INFO: compiled from: PropertiesFile.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2692g {

    /* JADX INFO: renamed from: b */
    private FileChannel f1382b;

    /* JADX INFO: renamed from: d */
    private MappedByteBuffer f1384d;

    /* JADX INFO: renamed from: e */
    private long f1385e;

    /* JADX INFO: renamed from: f */
    private Runnable f1386f;

    /* JADX INFO: renamed from: g */
    private boolean f1387g;

    /* JADX INFO: renamed from: h */
    private boolean f1388h;

    /* JADX INFO: renamed from: a */
    private final Object f1381a = new Object();

    /* JADX INFO: renamed from: c */
    private JSONObject f1383c = new JSONObject();

    private C2692g(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, FileOptionConst.READ_WRITE);
        this.f1382b = randomAccessFile.getChannel();
        this.f1385e = randomAccessFile.length();
        C2695c.m1463a("[properties]", "file size: " + this.f1385e, new Object[0]);
        m1416e();
    }

    /* JADX INFO: renamed from: c */
    private Object m1411c(String str) {
        Object obj = null;
        try {
            synchronized (this.f1381a) {
                obj = this.f1383c.get(str);
            }
        } catch (Exception unused) {
            C2695c.m1463a("[properties]", "current jsonObject not exist key: " + str, new Object[0]);
        }
        return obj;
    }

    /* JADX INFO: renamed from: d */
    private Runnable m1413d() {
        if (this.f1386f == null) {
            this.f1386f = new RunnableC2690e(this);
        }
        return this.f1386f;
    }

    /* JADX INFO: renamed from: e */
    private void m1416e() throws IOException {
        if (this.f1385e <= 10) {
            this.f1388h = true;
            this.f1385e = 4L;
        }
        MappedByteBuffer map = this.f1382b.map(FileChannel.MapMode.READ_WRITE, 0L, this.f1385e);
        this.f1384d = map;
        map.rewind();
        if (this.f1388h) {
            this.f1384d.putInt(0, 1);
            m1403a(m1413d());
            return;
        }
        byte[] bArrM1404a = m1404a(this.f1384d);
        if (bArrM1404a == null) {
            return;
        }
        try {
            this.f1383c = new JSONObject(new String(m1409b(bArrM1404a), "ISO8859-1"));
        } catch (Exception e) {
            C2695c.m1463a("[properties]", "init error" + e.getMessage(), new Object[0]);
            C2624j.m1031e().m1024a("504", "[properties] init error! msg: " + e.getMessage() + ". file size: " + this.f1385e, e);
        }
        C2695c.m1463a("[properties]", "init json: " + this.f1383c.toString(), new Object[0]);
    }

    /* JADX INFO: renamed from: a */
    public static C2692g m1400a(Context context, String str) throws IOException {
        File file = new File(context.getFilesDir(), "beacon");
        if (!(!file.exists() ? file.mkdirs() : true)) {
            C2700h.m1485a("mkdir " + file.getName() + " exception!");
        }
        return new C2692g(new File(file, str + "V1"));
    }

    /* JADX INFO: renamed from: b */
    public static byte[] m1409b(byte[] bArr) {
        byte[] bArrM1406a = m1406a(bArr, "BEACONDEFAULTAES");
        if (bArrM1406a != null) {
            return bArrM1406a;
        }
        C2624j.m1031e().m1023a("517", "default aesKey unEncryption failed");
        byte[] bArrM1406a2 = m1406a(bArr, C2634g.m1115e().m1116a());
        return bArrM1406a2 != null ? bArrM1406a2 : m1406a(bArr, "");
    }

    /* JADX INFO: renamed from: c */
    private boolean m1412c() {
        if (!this.f1387g) {
            return false;
        }
        C2695c.m1463a("[properties]", "File is close!", new Object[0]);
        return true;
    }

    /* JADX INFO: renamed from: b */
    public synchronized void m1423b(String str, Object obj) {
        Object objM1411c;
        if (m1412c()) {
            return;
        }
        try {
            objM1411c = m1411c(str);
        } catch (Exception e) {
            C2624j.m1031e().m1024a("504", "[properties] JSON put error!", e);
            C2695c.m1468b("[properties] JSON put error!" + e.getMessage(), new Object[0]);
        }
        if (objM1411c == null || !objM1411c.equals(obj)) {
            if (obj instanceof String) {
                if (TextUtils.isEmpty((String) obj)) {
                    return;
                }
                if (!C2701i.m1489a((String) obj)) {
                    C2695c.m1468b("[properties] JSON put value not english ! !", new Object[0]);
                    return;
                }
            }
            synchronized (this.f1381a) {
                this.f1383c.put(str, obj);
            }
            m1403a(m1413d());
        }
    }

    /* JADX INFO: renamed from: a */
    private static byte[] m1406a(byte[] bArr, String str) {
        try {
            return C2668c.m1327a(3, str, bArr);
        } catch (Throwable th) {
            C2695c.m1465a(th);
            C2624j.m1031e().m1024a("513", "unEncrypt error: key=" + str, th);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    public static byte[] m1405a(byte[] bArr) throws Exception {
        return C2668c.m1330b(3, "BEACONDEFAULTAES", bArr);
    }

    /* JADX INFO: renamed from: a */
    private byte[] m1404a(ByteBuffer byteBuffer) {
        int i = byteBuffer.getInt(0);
        if (i <= 1 || i > 2097152 || byteBuffer.capacity() <= 10) {
            return null;
        }
        byteBuffer.position(10);
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr, 0, i);
        return bArr;
    }

    /* JADX INFO: renamed from: a */
    public synchronized <T> T m1417a(String str, T t) {
        if (m1412c()) {
            return t;
        }
        Object objM1411c = m1411c(str);
        if (objM1411c != null) {
            t = (T) objM1411c;
        }
        return t;
    }

    /* JADX INFO: renamed from: b */
    public synchronized <T> void m1424b(String str, Set<T> set) {
        if (m1412c()) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            Iterator<T> it = set.iterator();
            for (int i = 0; i < set.size(); i++) {
                if (it.hasNext()) {
                    jSONObject.put(String.valueOf(i), it.next());
                }
            }
            m1423b(str, jSONObject);
        } catch (JSONException e) {
            C2695c.m1465a(e);
            C2624j.m1031e().m1024a("504", "[properties] JSON put set error!", e);
        }
    }

    /* JADX INFO: renamed from: a */
    public synchronized <T> Set<T> m1418a(String str, Set<T> set) {
        JSONObject jSONObject;
        if (m1412c()) {
            return set;
        }
        try {
            HashSet hashSet = new HashSet();
            synchronized (this.f1381a) {
                jSONObject = this.f1383c.getJSONObject(str);
            }
            if (jSONObject != null) {
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    hashSet.add(jSONObject.get(itKeys.next()));
                }
            }
            if (!hashSet.isEmpty()) {
                set = hashSet;
            }
        } catch (JSONException e) {
            C2624j.m1031e().m1024a("504", "[properties] JSON getSet error!", e);
            C2695c.m1468b("[properties] JSON get error!" + e.getMessage(), new Object[0]);
        }
        return set;
    }

    /* JADX INFO: renamed from: b */
    public synchronized void m1422b(String str) {
        synchronized (this.f1381a) {
            this.f1383c.remove(str);
        }
        m1403a(m1413d());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1402a(long j) throws IOException {
        if (j <= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE) {
            this.f1384d.rewind();
            this.f1384d = this.f1382b.map(FileChannel.MapMode.READ_WRITE, 0L, j);
            return;
        }
        throw new IllegalArgumentException("file size to reach maximum!");
    }

    /* JADX INFO: renamed from: b */
    public Map<String, ?> m1421b() {
        synchronized (this.f1381a) {
            JSONObject jSONObject = this.f1383c;
            if (jSONObject == null) {
                return null;
            }
            Iterator<String> itKeys = jSONObject.keys();
            HashMap map = new HashMap();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                try {
                    map.put(next, this.f1383c.get(next));
                } catch (JSONException e) {
                    C2695c.m1465a(e);
                }
            }
            return map;
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1403a(Runnable runnable) {
        AbstractC2616b.m1001a().mo1011a(new RunnableC2691f(this, runnable));
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1419a() {
        this.f1383c = new JSONObject();
        m1403a(m1413d());
    }

    /* JADX INFO: renamed from: a */
    public boolean m1420a(String str) {
        synchronized (this.f1381a) {
            Iterator<String> itKeys = this.f1383c.keys();
            if (itKeys != null) {
                while (itKeys.hasNext()) {
                    if (str.equals(itKeys.next())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
