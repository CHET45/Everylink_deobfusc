package com.tencent.beacon.pack;

import com.github.houbb.heaven.constant.CharsetConst;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.UByte;

/* JADX INFO: renamed from: com.tencent.beacon.pack.a */
/* JADX INFO: compiled from: JceInputStream.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2755a {

    /* JADX INFO: renamed from: a */
    protected String f1746a = CharsetConst.GBK;

    /* JADX INFO: renamed from: b */
    private ByteBuffer f1747b;

    /* JADX INFO: renamed from: com.tencent.beacon.pack.a$a */
    /* JADX INFO: compiled from: JceInputStream.java */
    public static class a {

        /* JADX INFO: renamed from: a */
        public byte f1748a;

        /* JADX INFO: renamed from: b */
        public int f1749b;
    }

    public C2755a() {
    }

    /* JADX INFO: renamed from: a */
    public static int m1786a(a aVar, ByteBuffer byteBuffer) {
        byte b = byteBuffer.get();
        aVar.f1748a = (byte) (b & 15);
        aVar.f1749b = (b & 240) >> 4;
        if (aVar.f1749b != 15) {
            return 1;
        }
        aVar.f1749b = byteBuffer.get() & UByte.MAX_VALUE;
        return 2;
    }

    /* JADX INFO: renamed from: b */
    private int m1789b(a aVar) {
        return m1786a(aVar, this.f1747b.duplicate());
    }

    /* JADX INFO: renamed from: b */
    private void m1791b(int i) {
        ByteBuffer byteBuffer = this.f1747b;
        byteBuffer.position(byteBuffer.position() + i);
    }

    public C2755a(byte[] bArr) {
        this.f1747b = ByteBuffer.wrap(bArr);
    }

    /* JADX INFO: renamed from: b */
    private void m1790b() {
        a aVar = new a();
        m1806a(aVar);
        m1788a(aVar.f1748a);
    }

    public C2755a(byte[] bArr, int i) {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        this.f1747b = byteBufferWrap;
        byteBufferWrap.position(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: b */
    private <T> T[] m1792b(T t, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m1806a(aVar);
        if (aVar.f1748a == 9) {
            int iM1796a = m1796a(0, 0, true);
            if (iM1796a >= 0) {
                T[] tArr = (T[]) ((Object[]) Array.newInstance(t.getClass(), iM1796a));
                for (int i2 = 0; i2 < iM1796a; i2++) {
                    tArr[i2] = m1800a((Object) t, 0, true);
                }
                return tArr;
            }
            throw new RuntimeException("size invalid: " + iM1796a);
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public void m1806a(a aVar) {
        m1786a(aVar, this.f1747b);
    }

    /* JADX INFO: renamed from: a */
    public void m1807a(byte[] bArr) {
        this.f1747b = ByteBuffer.wrap(bArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0017, code lost:
    
        if (r6 != r1.f1749b) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0019, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:?, code lost:
    
        return false;
     */
    /* JADX INFO: renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean m1808a(int r6) {
        /*
            r5 = this;
            r0 = 0
            com.tencent.beacon.pack.a$a r1 = new com.tencent.beacon.pack.a$a     // Catch: java.lang.RuntimeException -> L24 java.nio.BufferUnderflowException -> L29
            r1.<init>()     // Catch: java.lang.RuntimeException -> L24 java.nio.BufferUnderflowException -> L29
        L6:
            int r2 = r5.m1789b(r1)     // Catch: java.lang.RuntimeException -> L24 java.nio.BufferUnderflowException -> L29
            byte r3 = r1.f1748a     // Catch: java.lang.RuntimeException -> L24 java.nio.BufferUnderflowException -> L29
            r4 = 11
            if (r3 != r4) goto L11
            return r0
        L11:
            int r3 = r1.f1749b     // Catch: java.lang.RuntimeException -> L24 java.nio.BufferUnderflowException -> L29
            if (r6 > r3) goto L1b
            int r1 = r1.f1749b     // Catch: java.lang.RuntimeException -> L24 java.nio.BufferUnderflowException -> L29
            if (r6 != r1) goto L1a
            r0 = 1
        L1a:
            return r0
        L1b:
            r5.m1791b(r2)     // Catch: java.lang.RuntimeException -> L24 java.nio.BufferUnderflowException -> L29
            byte r2 = r1.f1748a     // Catch: java.lang.RuntimeException -> L24 java.nio.BufferUnderflowException -> L29
            r5.m1788a(r2)     // Catch: java.lang.RuntimeException -> L24 java.nio.BufferUnderflowException -> L29
            goto L6
        L24:
            r6 = move-exception
            com.tencent.beacon.base.util.C2695c.m1465a(r6)
            goto L2d
        L29:
            r6 = move-exception
            com.tencent.beacon.base.util.C2695c.m1465a(r6)
        L2d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.pack.C2755a.m1808a(int):boolean");
    }

    /* JADX INFO: renamed from: a */
    public void m1805a() {
        a aVar = new a();
        do {
            m1806a(aVar);
            m1788a(aVar.f1748a);
        } while (aVar.f1748a != 11);
    }

    /* JADX INFO: renamed from: a */
    private void m1788a(byte b) {
        int i = 0;
        switch (b) {
            case 0:
                m1791b(1);
                return;
            case 1:
                m1791b(2);
                return;
            case 2:
                m1791b(4);
                return;
            case 3:
                m1791b(8);
                return;
            case 4:
                m1791b(4);
                return;
            case 5:
                m1791b(8);
                return;
            case 6:
                int i2 = this.f1747b.get();
                if (i2 < 0) {
                    i2 += 256;
                }
                m1791b(i2);
                return;
            case 7:
                m1791b(this.f1747b.getInt());
                return;
            case 8:
                int iM1796a = m1796a(0, 0, true);
                while (i < iM1796a * 2) {
                    m1790b();
                    i++;
                }
                return;
            case 9:
                int iM1796a2 = m1796a(0, 0, true);
                while (i < iM1796a2) {
                    m1790b();
                    i++;
                }
                return;
            case 10:
                m1805a();
                return;
            case 11:
            case 12:
                return;
            case 13:
                a aVar = new a();
                m1806a(aVar);
                if (aVar.f1748a == 0) {
                    m1791b(m1796a(0, 0, true));
                    return;
                }
                throw new RuntimeException("skipField with invalid type, type value: " + ((int) b) + ", " + ((int) aVar.f1748a));
            default:
                throw new RuntimeException("invalid type.");
        }
    }

    /* JADX INFO: renamed from: a */
    public boolean m1809a(boolean z, int i, boolean z2) {
        return m1793a((byte) 0, i, z2) != 0;
    }

    /* JADX INFO: renamed from: a */
    public byte m1793a(byte b, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return b;
        }
        a aVar = new a();
        m1806a(aVar);
        byte b2 = aVar.f1748a;
        if (b2 == 0) {
            return this.f1747b.get();
        }
        if (b2 == 12) {
            return (byte) 0;
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public short m1804a(short s, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return s;
        }
        a aVar = new a();
        m1806a(aVar);
        byte b = aVar.f1748a;
        if (b == 0) {
            return this.f1747b.get();
        }
        if (b == 1) {
            return this.f1747b.getShort();
        }
        if (b == 12) {
            return (short) 0;
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public int m1796a(int i, int i2, boolean z) {
        if (!m1808a(i2)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return i;
        }
        a aVar = new a();
        m1806a(aVar);
        byte b = aVar.f1748a;
        if (b == 0) {
            return this.f1747b.get();
        }
        if (b == 1) {
            return this.f1747b.getShort();
        }
        if (b == 2) {
            return this.f1747b.getInt();
        }
        if (b == 12) {
            return 0;
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public long m1798a(long j, int i, boolean z) {
        int i2;
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return j;
        }
        a aVar = new a();
        m1806a(aVar);
        byte b = aVar.f1748a;
        if (b == 12) {
            return 0L;
        }
        if (b == 0) {
            i2 = this.f1747b.get();
        } else if (b == 1) {
            i2 = this.f1747b.getShort();
        } else {
            if (b != 2) {
                if (b != 3) {
                    throw new RuntimeException("type mismatch.");
                }
                return this.f1747b.getLong();
            }
            i2 = this.f1747b.getInt();
        }
        return i2;
    }

    /* JADX INFO: renamed from: a */
    public float m1795a(float f, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return f;
        }
        a aVar = new a();
        m1806a(aVar);
        byte b = aVar.f1748a;
        if (b == 4) {
            return this.f1747b.getFloat();
        }
        if (b == 12) {
            return 0.0f;
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public double m1794a(double d, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return d;
        }
        a aVar = new a();
        m1806a(aVar);
        byte b = aVar.f1748a;
        if (b == 4) {
            return this.f1747b.getFloat();
        }
        if (b == 5) {
            return this.f1747b.getDouble();
        }
        if (b == 12) {
            return 0.0d;
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public boolean[] m1817a(boolean[] zArr, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m1806a(aVar);
        if (aVar.f1748a == 9) {
            int iM1796a = m1796a(0, 0, true);
            if (iM1796a >= 0) {
                boolean[] zArr2 = new boolean[iM1796a];
                for (int i2 = 0; i2 < iM1796a; i2++) {
                    zArr2[i2] = m1809a(zArr2[0], 0, true);
                }
                return zArr2;
            }
            throw new RuntimeException("size invalid: " + iM1796a);
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public byte[] m1810a(byte[] bArr, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m1806a(aVar);
        byte b = aVar.f1748a;
        if (b == 9) {
            int iM1796a = m1796a(0, 0, true);
            if (iM1796a >= 0 && iM1796a <= this.f1747b.capacity()) {
                byte[] bArr2 = new byte[iM1796a];
                for (int i2 = 0; i2 < iM1796a; i2++) {
                    bArr2[i2] = m1793a(bArr2[0], 0, true);
                }
                return bArr2;
            }
            throw new RuntimeException("size invalid: " + iM1796a);
        }
        if (b == 13) {
            a aVar2 = new a();
            m1806a(aVar2);
            if (aVar2.f1748a == 0) {
                int iM1796a2 = m1796a(0, 0, true);
                if (iM1796a2 >= 0 && iM1796a2 <= this.f1747b.capacity()) {
                    byte[] bArr3 = new byte[iM1796a2];
                    this.f1747b.get(bArr3);
                    return bArr3;
                }
                throw new RuntimeException("invalid size, tag: " + i + ", type: " + ((int) aVar.f1748a) + ", " + ((int) aVar2.f1748a) + ", size: " + iM1796a2);
            }
            throw new RuntimeException("type mismatch, tag: " + i + ", type: " + ((int) aVar.f1748a) + ", " + ((int) aVar2.f1748a));
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public short[] m1816a(short[] sArr, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m1806a(aVar);
        if (aVar.f1748a == 9) {
            int iM1796a = m1796a(0, 0, true);
            if (iM1796a >= 0) {
                short[] sArr2 = new short[iM1796a];
                for (int i2 = 0; i2 < iM1796a; i2++) {
                    sArr2[i2] = m1804a(sArr2[0], 0, true);
                }
                return sArr2;
            }
            throw new RuntimeException("size invalid: " + iM1796a);
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public int[] m1813a(int[] iArr, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m1806a(aVar);
        if (aVar.f1748a == 9) {
            int iM1796a = m1796a(0, 0, true);
            if (iM1796a >= 0) {
                int[] iArr2 = new int[iM1796a];
                for (int i2 = 0; i2 < iM1796a; i2++) {
                    iArr2[i2] = m1796a(iArr2[0], 0, true);
                }
                return iArr2;
            }
            throw new RuntimeException("size invalid: " + iM1796a);
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public long[] m1814a(long[] jArr, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m1806a(aVar);
        if (aVar.f1748a == 9) {
            int iM1796a = m1796a(0, 0, true);
            if (iM1796a >= 0) {
                long[] jArr2 = new long[iM1796a];
                for (int i2 = 0; i2 < iM1796a; i2++) {
                    jArr2[i2] = m1798a(jArr2[0], 0, true);
                }
                return jArr2;
            }
            throw new RuntimeException("size invalid: " + iM1796a);
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public float[] m1812a(float[] fArr, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m1806a(aVar);
        if (aVar.f1748a == 9) {
            int iM1796a = m1796a(0, 0, true);
            if (iM1796a >= 0) {
                float[] fArr2 = new float[iM1796a];
                for (int i2 = 0; i2 < iM1796a; i2++) {
                    fArr2[i2] = m1795a(fArr2[0], 0, true);
                }
                return fArr2;
            }
            throw new RuntimeException("size invalid: " + iM1796a);
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public double[] m1811a(double[] dArr, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m1806a(aVar);
        if (aVar.f1748a == 9) {
            int iM1796a = m1796a(0, 0, true);
            if (iM1796a >= 0) {
                double[] dArr2 = new double[iM1796a];
                for (int i2 = 0; i2 < iM1796a; i2++) {
                    dArr2[i2] = m1794a(dArr2[0], 0, true);
                }
                return dArr2;
            }
            throw new RuntimeException("size invalid: " + iM1796a);
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public AbstractJceStruct m1799a(AbstractJceStruct abstractJceStruct, int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        try {
            AbstractJceStruct abstractJceStruct2 = (AbstractJceStruct) abstractJceStruct.getClass().newInstance();
            a aVar = new a();
            m1806a(aVar);
            if (aVar.f1748a == 10) {
                abstractJceStruct2.readFrom(this);
                m1805a();
                return abstractJceStruct2;
            }
            throw new RuntimeException("type mismatch.");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: a */
    public <T> Object m1800a(T t, int i, boolean z) {
        if (t instanceof Byte) {
            return Byte.valueOf(m1793a((byte) 0, i, z));
        }
        if (t instanceof Boolean) {
            return Boolean.valueOf(m1809a(false, i, z));
        }
        if (t instanceof Short) {
            return Short.valueOf(m1804a((short) 0, i, z));
        }
        if (t instanceof Integer) {
            return Integer.valueOf(m1796a(0, i, z));
        }
        if (t instanceof Long) {
            return Long.valueOf(m1798a(0L, i, z));
        }
        if (t instanceof Float) {
            return Float.valueOf(m1795a(0.0f, i, z));
        }
        if (t instanceof Double) {
            return Double.valueOf(m1794a(0.0d, i, z));
        }
        if (t instanceof String) {
            return m1801a(i, z);
        }
        if (t instanceof Map) {
            return m1802a((Map) t, i, z);
        }
        if (t instanceof List) {
            return m1803a((List) t, i, z);
        }
        if (t instanceof AbstractJceStruct) {
            return m1799a((AbstractJceStruct) t, i, z);
        }
        if (t.getClass().isArray()) {
            if (!(t instanceof byte[]) && !(t instanceof Byte[])) {
                if (t instanceof boolean[]) {
                    return m1817a((boolean[]) null, i, z);
                }
                if (t instanceof short[]) {
                    return m1816a((short[]) null, i, z);
                }
                if (t instanceof int[]) {
                    return m1813a((int[]) null, i, z);
                }
                if (t instanceof long[]) {
                    return m1814a((long[]) null, i, z);
                }
                if (t instanceof float[]) {
                    return m1812a((float[]) null, i, z);
                }
                if (t instanceof double[]) {
                    return m1811a((double[]) null, i, z);
                }
                return m1815a((Object[]) t, i, z);
            }
            return m1810a((byte[]) null, i, z);
        }
        throw new RuntimeException("read object error: unsupport type.");
    }

    /* JADX INFO: renamed from: a */
    public String m1801a(int i, boolean z) {
        if (!m1808a(i)) {
            if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return null;
        }
        a aVar = new a();
        m1806a(aVar);
        byte b = aVar.f1748a;
        if (b == 6) {
            int i2 = this.f1747b.get();
            if (i2 < 0) {
                i2 += 256;
            }
            byte[] bArr = new byte[i2];
            this.f1747b.get(bArr);
            try {
                return new String(bArr, this.f1746a);
            } catch (UnsupportedEncodingException unused) {
                return new String(bArr, Charset.forName("UTF-8"));
            }
        }
        if (b == 7) {
            int i3 = this.f1747b.getInt();
            if (i3 <= 104857600 && i3 >= 0 && i3 <= this.f1747b.capacity()) {
                byte[] bArr2 = new byte[i3];
                this.f1747b.get(bArr2);
                try {
                    return new String(bArr2, this.f1746a);
                } catch (UnsupportedEncodingException unused2) {
                    return new String(bArr2, Charset.forName("UTF-8"));
                }
            }
            throw new RuntimeException("String too long: " + i3);
        }
        throw new RuntimeException("type mismatch.");
    }

    /* JADX INFO: renamed from: a */
    public <K, V> HashMap<K, V> m1802a(Map<K, V> map, int i, boolean z) {
        return (HashMap) m1787a(new HashMap(), map, i, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: a */
    private <K, V> Map<K, V> m1787a(Map<K, V> map, Map<K, V> map2, int i, boolean z) {
        if (map2 != null && !map2.isEmpty()) {
            Map.Entry<K, V> next = map2.entrySet().iterator().next();
            K key = next.getKey();
            V value = next.getValue();
            if (m1808a(i)) {
                a aVar = new a();
                m1806a(aVar);
                if (aVar.f1748a == 8) {
                    int iM1796a = m1796a(0, 0, true);
                    if (iM1796a < 0) {
                        throw new RuntimeException("size invalid: " + iM1796a);
                    }
                    for (int i2 = 0; i2 < iM1796a; i2++) {
                        map.put(m1800a(key, 0, true), m1800a(value, 1, true));
                    }
                } else {
                    throw new RuntimeException("type mismatch.");
                }
            } else if (z) {
                throw new RuntimeException("require field not exist.");
            }
            return map;
        }
        return new HashMap();
    }

    /* JADX INFO: renamed from: a */
    public <T> T[] m1815a(T[] tArr, int i, boolean z) {
        if (tArr != null && tArr.length != 0) {
            return (T[]) m1792b(tArr[0], i, z);
        }
        throw new RuntimeException("unable to get type of key and value.");
    }

    /* JADX INFO: renamed from: a */
    public <T> List<T> m1803a(List<T> list, int i, boolean z) {
        if (list != null && !list.isEmpty()) {
            Object[] objArrM1792b = m1792b(list.get(0), i, z);
            if (objArrM1792b == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (Object obj : objArrM1792b) {
                arrayList.add(obj);
            }
            return arrayList;
        }
        return new ArrayList();
    }

    /* JADX INFO: renamed from: a */
    public int m1797a(String str) {
        this.f1746a = str;
        return 0;
    }
}
