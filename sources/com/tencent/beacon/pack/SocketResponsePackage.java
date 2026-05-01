package com.tencent.beacon.pack;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class SocketResponsePackage extends AbstractJceStruct {
    static byte[] cache_body;
    static Map<String, String> cache_header;
    public byte[] body;
    public Map<String, String> header;
    public String msg;
    public int statusCode;

    static {
        HashMap map = new HashMap();
        cache_header = map;
        map.put("", "");
        cache_body = new byte[]{0};
    }

    public SocketResponsePackage() {
        this.statusCode = 0;
        this.header = null;
        this.body = null;
        this.msg = "";
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void readFrom(C2755a c2755a) {
        this.statusCode = c2755a.m1796a(this.statusCode, 0, true);
        this.header = (Map) c2755a.m1800a(cache_header, 1, true);
        this.body = c2755a.m1810a(cache_body, 2, true);
        this.msg = c2755a.m1801a(3, false);
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void writeTo(C2756b c2756b) {
        c2756b.m1825a(this.statusCode, 0);
        c2756b.m1831a((Map) this.header, 1);
        c2756b.m1834a(this.body, 2);
        String str = this.msg;
        if (str != null) {
            c2756b.m1829a(str, 3);
        }
    }

    public SocketResponsePackage(int i, Map<String, String> map, byte[] bArr, String str) {
        this.statusCode = i;
        this.header = map;
        this.body = bArr;
        this.msg = str;
    }
}
