package com.tencent.beacon.base.net.p020a;

import com.tencent.beacon.base.net.p020a.InterfaceC2642c;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p028d.C2710b;
import com.tencent.beacon.p028d.C2716h;
import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.beacon.pack.C2755a;
import com.tencent.beacon.pack.C2756b;
import com.tencent.beacon.pack.RequestPackageV2;
import com.tencent.beacon.pack.ResponsePackageV2;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.a.b */
/* JADX INFO: compiled from: ByteV2ConverterFactory.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2641b extends InterfaceC2642c.a<byte[], AbstractJceStruct> {

    /* JADX INFO: renamed from: a */
    private final a f1124a = new a();

    /* JADX INFO: renamed from: b */
    private final b f1125b = new b();

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.a.b$a */
    /* JADX INFO: compiled from: ByteV2ConverterFactory.java */
    static final class a implements InterfaceC2642c<RequestPackageV2, byte[]> {
        a() {
        }

        @Override // com.tencent.beacon.base.net.p020a.InterfaceC2642c
        /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public byte[] mo1161a(RequestPackageV2 requestPackageV2) {
            if (requestPackageV2 == null) {
                return null;
            }
            C2695c.m1463a("[BeaconNet]", "RequestPackageV2: " + requestPackageV2.toString(), new Object[0]);
            C2756b c2756b = new C2756b();
            requestPackageV2.writeTo(c2756b);
            byte[] bArrM1167a = m1167a(c2756b.m1842b());
            if (bArrM1167a != null) {
                C2695c.m1463a("[BeaconNet]", "request package after processing size: " + bArrM1167a.length, new Object[0]);
            }
            return bArrM1167a;
        }

        /* JADX INFO: renamed from: a */
        private byte[] m1167a(byte[] bArr) {
            int iM1534b = C2710b.m1518d().m1534b();
            C2716h c2716hM1581d = C2716h.m1581d();
            if (C2710b.m1518d().m1520A()) {
                return C2694b.m1452b(bArr, iM1534b, 3, c2716hM1581d.m1582a());
            }
            return C2694b.m1451b(bArr, iM1534b);
        }
    }

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.a.b$b */
    /* JADX INFO: compiled from: ByteV2ConverterFactory.java */
    static final class b implements InterfaceC2642c<byte[], ResponsePackageV2> {
        b() {
        }

        /* JADX INFO: renamed from: b */
        private byte[] m1168b(byte[] bArr) {
            return C2710b.m1518d().m1520A() ? C2694b.m1446a(bArr, 2, 3, C2716h.m1581d().m1582a()) : C2694b.m1445a(bArr, 2);
        }

        @Override // com.tencent.beacon.base.net.p020a.InterfaceC2642c
        /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
        public ResponsePackageV2 mo1161a(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            byte[] bArrM1168b = m1168b(bArr);
            ResponsePackageV2 responsePackageV2 = new ResponsePackageV2();
            responsePackageV2.readFrom(new C2755a(bArrM1168b));
            return responsePackageV2;
        }
    }

    /* JADX INFO: renamed from: a */
    public static C2641b m1164a() {
        return new C2641b();
    }

    /* JADX INFO: renamed from: b */
    public InterfaceC2642c<byte[], ResponsePackageV2> m1165b() {
        return this.f1125b;
    }

    /* JADX INFO: renamed from: c */
    public InterfaceC2642c<RequestPackageV2, byte[]> m1166c() {
        return this.f1124a;
    }
}
