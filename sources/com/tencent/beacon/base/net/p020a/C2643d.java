package com.tencent.beacon.base.net.p020a;

import android.text.TextUtils;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.p028d.C2716h;
import com.tencent.beacon.pack.SocketRequestPackage;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.a.d */
/* JADX INFO: compiled from: SocketRequestConverter.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2643d implements InterfaceC2642c<JceRequestEntity, SocketRequestPackage> {
    /* JADX INFO: renamed from: b */
    private Map<String, String> m1169b(JceRequestEntity jceRequestEntity) {
        Map<String, String> header = jceRequestEntity.getHeader();
        if (!header.containsKey("sid")) {
            String strM1589e = C2716h.m1581d().m1589e();
            if (!TextUtils.isEmpty(strM1589e)) {
                header.put("sid", strM1589e);
            }
        }
        return header;
    }

    @Override // com.tencent.beacon.base.net.p020a.InterfaceC2642c
    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public SocketRequestPackage mo1161a(JceRequestEntity jceRequestEntity) {
        return new SocketRequestPackage(m1169b(jceRequestEntity), jceRequestEntity.getContent());
    }
}
