package com.tencent.beacon.pack;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class SocketRequestPackage extends AbstractJceStruct {
    static byte[] cache_body;
    static Map<String, String> cache_header;
    public byte[] body;
    public Map<String, String> header;

    static {
        HashMap map = new HashMap();
        cache_header = map;
        map.put("", "");
        cache_body = new byte[]{0};
    }

    public SocketRequestPackage() {
        this.header = null;
        this.body = null;
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void readFrom(C2755a c2755a) {
        this.header = (Map) c2755a.m1800a(cache_header, 0, true);
        this.body = c2755a.m1810a(cache_body, 1, true);
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void writeTo(C2756b c2756b) {
        c2756b.m1831a((Map) this.header, 0);
        c2756b.m1834a(this.body, 1);
    }

    public SocketRequestPackage(Map<String, String> map, byte[] bArr) {
        this.header = map;
        this.body = bArr;
    }
}
