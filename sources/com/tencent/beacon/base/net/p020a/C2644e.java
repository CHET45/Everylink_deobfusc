package com.tencent.beacon.base.net.p020a;

import com.tencent.beacon.base.net.p021b.C2669d;
import com.tencent.beacon.pack.C2755a;
import com.tencent.beacon.pack.SocketResponsePackage;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.a.e */
/* JADX INFO: compiled from: SocketResponseConverter.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2644e implements InterfaceC2642c<byte[], SocketResponsePackage> {
    @Override // com.tencent.beacon.base.net.p020a.InterfaceC2642c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public SocketResponsePackage mo1161a(byte[] bArr) {
        SocketResponsePackage socketResponsePackage = new SocketResponsePackage();
        socketResponsePackage.readFrom(new C2755a(bArr));
        C2669d.m1343c(socketResponsePackage.header);
        return socketResponsePackage;
    }
}
