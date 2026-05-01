package com.tencent.beacon.base.net.adapter;

import com.tencent.beacon.base.net.p021b.C2669d;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p028d.C2710b;
import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.beacon.pack.C2756b;
import com.tencent.beacon.pack.RequestPackageV2;
import com.tencent.beacon.pack.SocketRequestPackage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.adapter.g */
/* JADX INFO: compiled from: QuicAdapter.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2651g {

    /* JADX INFO: renamed from: a */
    private static C2710b f1134a;

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.adapter.g$a */
    /* JADX INFO: compiled from: QuicAdapter.java */
    private static final class a {

        /* JADX INFO: renamed from: a */
        static final C2651g f1135a = new C2651g();
    }

    /* JADX INFO: renamed from: a */
    public static C2651g m1173a() {
        return a.f1135a;
    }

    private C2651g() {
        f1134a = C2710b.m1518d();
    }

    /* JADX INFO: renamed from: a */
    public byte[] m1175a(RequestPackageV2 requestPackageV2) {
        try {
            if (f1134a.m1544e() != 1) {
                if (f1134a.m1544e() == 2) {
                    return C2694b.m1451b(requestPackageV2.toByteArray(), 2);
                }
                return null;
            }
            SocketRequestPackage socketRequestPackage = new SocketRequestPackage(C2669d.m1345e(), requestPackageV2.toByteArray());
            C2756b c2756b = new C2756b();
            socketRequestPackage.writeTo(c2756b);
            byte[] bArrM1842b = c2756b.m1842b();
            return C2694b.m1451b(m1174a(bArrM1842b, bArrM1842b.length), 2);
        } catch (Throwable th) {
            String str = String.format("QuicAdapter quic generate data error: %s", th.getMessage());
            C2695c.m1468b(str, new Object[0]);
            C2695c.m1465a(th);
            C2624j.m1031e().m1023a(f1134a.m1544e() == 1 ? "471" : "474", str);
            return null;
        }
    }

    /* JADX INFO: renamed from: a */
    private byte[] m1174a(byte[] bArr, int i) {
        int i2 = i + 4;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i2);
        byteBufferAllocate.order(ByteOrder.BIG_ENDIAN);
        byteBufferAllocate.putShort((short) (i2 & 65535));
        byteBufferAllocate.put(bArr);
        byteBufferAllocate.put(AbstractJceStruct.SIMPLE_LIST);
        byteBufferAllocate.put((byte) 10);
        return byteBufferAllocate.array();
    }
}
