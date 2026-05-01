package com.tencent.beacon.base.net.p020a;

import com.tencent.beacon.base.net.p020a.InterfaceC2642c;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p028d.C2716h;
import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.beacon.pack.C2757c;
import com.tencent.beacon.pack.RequestPackage;
import com.tencent.beacon.pack.ResponsePackage;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.a.a */
/* JADX INFO: compiled from: ByteConverterFactory.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2640a extends InterfaceC2642c.a<byte[], AbstractJceStruct> {

    /* JADX INFO: renamed from: a */
    private final a f1122a = new a();

    /* JADX INFO: renamed from: b */
    private final b f1123b = new b();

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.a.a$a */
    /* JADX INFO: compiled from: ByteConverterFactory.java */
    static final class a implements InterfaceC2642c<RequestPackage, byte[]> {
        a() {
        }

        /* JADX INFO: renamed from: b */
        private byte[] m1160b(RequestPackage requestPackage) {
            C2757c c2757c = new C2757c();
            c2757c.m1847a(1);
            c2757c.m1851b("test");
            c2757c.m1848a("test");
            c2757c.m1852b("detail", requestPackage);
            return c2757c.m1850a();
        }

        @Override // com.tencent.beacon.base.net.p020a.InterfaceC2642c
        /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public byte[] mo1161a(RequestPackage requestPackage) {
            if (requestPackage == null) {
                return null;
            }
            C2695c.m1463a("[BeaconNet]", "RequestPackage: " + requestPackage.toString(), new Object[0]);
            byte[] bArrM1159a = m1159a(m1160b(requestPackage));
            if (bArrM1159a != null) {
                C2695c.m1463a("[BeaconNet]", "request package after processing size: " + bArrM1159a.length, new Object[0]);
            }
            return bArrM1159a;
        }

        /* JADX INFO: renamed from: a */
        private byte[] m1159a(byte[] bArr) {
            C2716h c2716hM1581d = C2716h.m1581d();
            return c2716hM1581d != null ? C2694b.m1452b(bArr, 2, 3, c2716hM1581d.m1582a()) : bArr;
        }
    }

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.a.a$b */
    /* JADX INFO: compiled from: ByteConverterFactory.java */
    static final class b implements InterfaceC2642c<byte[], ResponsePackage> {
        b() {
        }

        /* JADX INFO: renamed from: b */
        private ResponsePackage m1162b(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            try {
                if (bArr.length <= 0) {
                    return null;
                }
                C2757c c2757c = new C2757c();
                c2757c.m1849a(bArr);
                return (ResponsePackage) c2757c.m1846a("detail", new ResponsePackage());
            } catch (Throwable unused) {
                return null;
            }
        }

        /* JADX INFO: renamed from: c */
        private byte[] m1163c(byte[] bArr) {
            return C2694b.m1446a(bArr, 2, 3, C2716h.m1581d().m1582a());
        }

        @Override // com.tencent.beacon.base.net.p020a.InterfaceC2642c
        /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public ResponsePackage mo1161a(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            return m1162b(m1163c(bArr));
        }
    }

    /* JADX INFO: renamed from: a */
    public static C2640a m1156a() {
        return new C2640a();
    }

    /* JADX INFO: renamed from: b */
    public InterfaceC2642c<byte[], ResponsePackage> m1157b() {
        return this.f1123b;
    }

    /* JADX INFO: renamed from: c */
    public InterfaceC2642c<RequestPackage, byte[]> m1158c() {
        return this.f1122a;
    }
}
