package com.tencent.beacon.pack;

import com.github.houbb.heaven.constant.CharsetConst;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.tencent.beacon.pack.c */
/* JADX INFO: compiled from: PacketUtil.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2757c {

    /* JADX INFO: renamed from: a */
    private static HashMap<String, byte[]> f1754a;

    /* JADX INFO: renamed from: b */
    public final RequestPacket f1755b = new RequestPacket();

    /* JADX INFO: renamed from: c */
    public HashMap<String, byte[]> f1756c = new HashMap<>();

    /* JADX INFO: renamed from: d */
    public String f1757d = CharsetConst.GBK;

    /* JADX INFO: renamed from: e */
    C2755a f1758e = new C2755a();

    static {
        HashMap<String, byte[]> map = new HashMap<>();
        f1754a = map;
        map.put("", new byte[0]);
    }

    /* JADX INFO: renamed from: a */
    public void m1847a(int i) {
        this.f1755b.iRequestId = i;
    }

    /* JADX INFO: renamed from: b */
    public void m1851b(String str) {
        this.f1755b.sServantName = str;
    }

    /* JADX INFO: renamed from: a */
    public void m1848a(String str) {
        this.f1755b.sFuncName = str;
    }

    /* JADX INFO: renamed from: b */
    public <T> void m1852b(String str, T t) {
        if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        }
        if (t == null) {
            throw new IllegalArgumentException("put value can not is null");
        }
        if (t instanceof Set) {
            throw new IllegalArgumentException("can not support Set");
        }
        C2756b c2756b = new C2756b();
        c2756b.m1819a(this.f1757d);
        c2756b.m1828a(t, 0);
        this.f1756c.put(str, m1844a(c2756b.m1820a()));
    }

    /* JADX INFO: renamed from: a */
    public byte[] m1850a() {
        C2756b c2756b = new C2756b(0);
        c2756b.m1819a(this.f1757d);
        c2756b.m1831a((Map) this.f1756c, 0);
        RequestPacket requestPacket = this.f1755b;
        requestPacket.iVersion = (short) 3;
        requestPacket.sBuffer = m1844a(c2756b.m1820a());
        C2756b c2756b2 = new C2756b(0);
        c2756b2.m1819a(this.f1757d);
        this.f1755b.writeTo(c2756b2);
        byte[] bArrM1844a = m1844a(c2756b2.m1820a());
        int length = bArrM1844a.length + 4;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length);
        byteBufferAllocate.putInt(length).put(bArrM1844a).flip();
        return byteBufferAllocate.array();
    }

    /* JADX INFO: renamed from: b */
    private void m1845b() {
        C2755a c2755a = new C2755a(this.f1755b.sBuffer);
        c2755a.m1797a(this.f1757d);
        this.f1756c = c2755a.m1802a((Map) f1754a, 0, false);
    }

    /* JADX INFO: renamed from: a */
    public void m1849a(byte[] bArr) {
        if (bArr.length >= 4) {
            try {
                C2755a c2755a = new C2755a(bArr, 4);
                c2755a.m1797a(this.f1757d);
                this.f1755b.readFrom(c2755a);
                m1845b();
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("decode package must include size head");
    }

    /* JADX INFO: renamed from: a */
    public <T> T m1846a(String str, T t) throws Exception {
        if (!this.f1756c.containsKey(str)) {
            return null;
        }
        try {
            return (T) m1843a(this.f1756c.get(str), t);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /* JADX INFO: renamed from: a */
    private Object m1843a(byte[] bArr, Object obj) {
        this.f1758e.m1807a(bArr);
        this.f1758e.m1797a(this.f1757d);
        return this.f1758e.m1800a(obj, 0, true);
    }

    /* JADX INFO: renamed from: a */
    private byte[] m1844a(ByteBuffer byteBuffer) {
        int iPosition = byteBuffer.position();
        byte[] bArr = new byte[iPosition];
        System.arraycopy(byteBuffer.array(), 0, bArr, 0, iPosition);
        return bArr;
    }
}
