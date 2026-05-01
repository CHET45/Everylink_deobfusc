package com.tencent.beacon.pack;

import com.github.houbb.heaven.constant.CharsetConst;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.pack.b */
/* JADX INFO: compiled from: JceOutputStream.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2756b {

    /* JADX INFO: renamed from: a */
    public static final byte[] f1750a = new byte[0];

    /* JADX INFO: renamed from: b */
    private static final char[] f1751b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* JADX INFO: renamed from: c */
    protected String f1752c;

    /* JADX INFO: renamed from: d */
    private ByteBuffer f1753d;

    public C2756b(int i) {
        this.f1752c = CharsetConst.GBK;
        this.f1753d = ByteBuffer.allocate(i);
    }

    /* JADX INFO: renamed from: a */
    public ByteBuffer m1820a() {
        return this.f1753d;
    }

    /* JADX INFO: renamed from: b */
    public byte[] m1842b() {
        byte[] bArr = new byte[this.f1753d.position()];
        System.arraycopy(this.f1753d.array(), 0, bArr, 0, this.f1753d.position());
        return bArr;
    }

    /* JADX INFO: renamed from: a */
    public void m1824a(int i) {
        if (this.f1753d.remaining() < i) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate((this.f1753d.capacity() + i) * 2);
            byteBufferAllocate.put(this.f1753d.array(), 0, this.f1753d.position());
            this.f1753d = byteBufferAllocate;
        }
    }

    /* JADX INFO: renamed from: b */
    public void m1841b(byte b, int i) {
        if (i < 15) {
            this.f1753d.put((byte) (b | (i << 4)));
        } else {
            if (i < 256) {
                this.f1753d.put((byte) (b | 240));
                this.f1753d.put((byte) i);
                return;
            }
            throw new RuntimeException("tag is too large: " + i);
        }
    }

    public C2756b() {
        this(128);
    }

    /* JADX INFO: renamed from: a */
    public void m1833a(boolean z, int i) {
        m1821a(z ? (byte) 1 : (byte) 0, i);
    }

    /* JADX INFO: renamed from: a */
    public void m1821a(byte b, int i) {
        m1824a(3);
        if (b == 0) {
            m1841b(AbstractJceStruct.ZERO_TAG, i);
        } else {
            m1841b((byte) 0, i);
            this.f1753d.put(b);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1832a(short s, int i) {
        m1824a(4);
        if (s >= -128 && s <= 127) {
            m1821a((byte) s, i);
        } else {
            m1841b((byte) 1, i);
            this.f1753d.putShort(s);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1825a(int i, int i2) {
        m1824a(6);
        if (i >= -32768 && i <= 32767) {
            m1832a((short) i, i2);
        } else {
            m1841b((byte) 2, i2);
            this.f1753d.putInt(i);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1826a(long j, int i) {
        m1824a(10);
        if (j >= -2147483648L && j <= 2147483647L) {
            m1825a((int) j, i);
        } else {
            m1841b((byte) 3, i);
            this.f1753d.putLong(j);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1823a(float f, int i) {
        m1824a(6);
        m1841b((byte) 4, i);
        this.f1753d.putFloat(f);
    }

    /* JADX INFO: renamed from: a */
    public void m1822a(double d, int i) {
        m1824a(10);
        m1841b((byte) 5, i);
        this.f1753d.putDouble(d);
    }

    /* JADX INFO: renamed from: a */
    public void m1829a(String str, int i) {
        byte[] bytes;
        try {
            bytes = str.getBytes(this.f1752c);
        } catch (UnsupportedEncodingException unused) {
            bytes = str.getBytes(Charset.forName("UTF-8"));
        }
        m1824a(bytes.length + 10);
        if (bytes.length > 255) {
            m1841b((byte) 7, i);
            this.f1753d.putInt(bytes.length);
            this.f1753d.put(bytes);
        } else {
            m1841b((byte) 6, i);
            this.f1753d.put((byte) bytes.length);
            this.f1753d.put(bytes);
        }
    }

    /* JADX INFO: renamed from: a */
    public <K, V> void m1831a(Map<K, V> map, int i) {
        m1824a(8);
        m1841b((byte) 8, i);
        m1825a(map == null ? 0 : map.size(), 0);
        if (map != null) {
            for (Map.Entry<K, V> entry : map.entrySet()) {
                m1828a(entry.getKey(), 0);
                m1828a(entry.getValue(), 1);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1840a(boolean[] zArr, int i) {
        m1824a(8);
        m1841b((byte) 9, i);
        m1825a(zArr.length, 0);
        for (boolean z : zArr) {
            m1833a(z, 0);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1834a(byte[] bArr, int i) {
        m1824a(bArr.length + 8);
        m1841b(AbstractJceStruct.SIMPLE_LIST, i);
        m1841b((byte) 0, 0);
        m1825a(bArr.length, 0);
        this.f1753d.put(bArr);
    }

    /* JADX INFO: renamed from: a */
    public void m1839a(short[] sArr, int i) {
        m1824a(8);
        m1841b((byte) 9, i);
        m1825a(sArr.length, 0);
        for (short s : sArr) {
            m1832a(s, 0);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1837a(int[] iArr, int i) {
        m1824a(8);
        m1841b((byte) 9, i);
        m1825a(iArr.length, 0);
        for (int i2 : iArr) {
            m1825a(i2, 0);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1838a(long[] jArr, int i) {
        m1824a(8);
        m1841b((byte) 9, i);
        m1825a(jArr.length, 0);
        for (long j : jArr) {
            m1826a(j, 0);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1836a(float[] fArr, int i) {
        m1824a(8);
        m1841b((byte) 9, i);
        m1825a(fArr.length, 0);
        for (float f : fArr) {
            m1823a(f, 0);
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1835a(double[] dArr, int i) {
        m1824a(8);
        m1841b((byte) 9, i);
        m1825a(dArr.length, 0);
        for (double d : dArr) {
            m1822a(d, 0);
        }
    }

    /* JADX INFO: renamed from: a */
    public <T> void m1830a(Collection<T> collection, int i) {
        m1824a(8);
        m1841b((byte) 9, i);
        m1825a(collection == null ? 0 : collection.size(), 0);
        if (collection != null) {
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                m1828a(it.next(), 0);
            }
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1827a(AbstractJceStruct abstractJceStruct, int i) {
        m1824a(2);
        m1841b((byte) 10, i);
        abstractJceStruct.writeTo(this);
        m1824a(2);
        m1841b(AbstractJceStruct.STRUCT_END, 0);
    }

    /* JADX INFO: renamed from: a */
    public void m1828a(Object obj, int i) {
        if (obj == null) {
            obj = "";
        }
        if (obj instanceof Byte) {
            m1821a(((Byte) obj).byteValue(), i);
            return;
        }
        if (obj instanceof Boolean) {
            m1833a(((Boolean) obj).booleanValue(), i);
            return;
        }
        if (obj instanceof Short) {
            m1832a(((Short) obj).shortValue(), i);
            return;
        }
        if (obj instanceof Integer) {
            m1825a(((Integer) obj).intValue(), i);
            return;
        }
        if (obj instanceof Long) {
            m1826a(((Long) obj).longValue(), i);
            return;
        }
        if (obj instanceof Float) {
            m1823a(((Float) obj).floatValue(), i);
            return;
        }
        if (obj instanceof Double) {
            m1822a(((Double) obj).doubleValue(), i);
            return;
        }
        if (obj instanceof String) {
            m1829a((String) obj, i);
            return;
        }
        if (obj instanceof Map) {
            m1831a((Map) obj, i);
            return;
        }
        if (obj instanceof List) {
            m1830a((Collection) obj, i);
            return;
        }
        if (obj instanceof AbstractJceStruct) {
            m1827a((AbstractJceStruct) obj, i);
            return;
        }
        if (obj instanceof byte[]) {
            m1834a((byte[]) obj, i);
            return;
        }
        if (obj instanceof boolean[]) {
            m1840a((boolean[]) obj, i);
            return;
        }
        if (obj instanceof short[]) {
            m1839a((short[]) obj, i);
            return;
        }
        if (obj instanceof int[]) {
            m1837a((int[]) obj, i);
            return;
        }
        if (obj instanceof long[]) {
            m1838a((long[]) obj, i);
            return;
        }
        if (obj instanceof float[]) {
            m1836a((float[]) obj, i);
            return;
        }
        if (obj instanceof double[]) {
            m1835a((double[]) obj, i);
        } else if (obj.getClass().isArray()) {
            m1818a((Object[]) obj, i);
        } else {
            if (obj instanceof Collection) {
                m1830a((Collection) obj, i);
                return;
            }
            throw new RuntimeException("write object error: unsupport type. " + obj.getClass());
        }
    }

    /* JADX INFO: renamed from: a */
    private void m1818a(Object[] objArr, int i) {
        m1824a(8);
        m1841b((byte) 9, i);
        m1825a(objArr.length, 0);
        for (Object obj : objArr) {
            m1828a(obj, 0);
        }
    }

    /* JADX INFO: renamed from: a */
    public int m1819a(String str) {
        this.f1752c = str;
        return 0;
    }
}
