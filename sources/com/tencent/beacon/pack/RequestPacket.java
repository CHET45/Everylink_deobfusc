package com.tencent.beacon.pack;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class RequestPacket extends AbstractJceStruct {
    static Map<String, String> cache_context;
    static byte[] cache_sBuffer;
    public Map<String, String> context;
    public byte[] sBuffer;
    public Map<String, String> status;
    public short iVersion = 3;
    public byte cPacketType = 0;
    public int iMessageType = 0;
    public int iRequestId = 0;
    public String sServantName = null;
    public String sFuncName = null;
    public int iTimeout = 0;

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void readFrom(C2755a c2755a) {
        try {
            this.iVersion = c2755a.m1804a(this.iVersion, 1, true);
            this.cPacketType = c2755a.m1793a(this.cPacketType, 2, true);
            this.iMessageType = c2755a.m1796a(this.iMessageType, 3, true);
            this.iRequestId = c2755a.m1796a(this.iRequestId, 4, true);
            this.sServantName = c2755a.m1801a(5, true);
            this.sFuncName = c2755a.m1801a(6, true);
            if (cache_sBuffer == null) {
                cache_sBuffer = new byte[]{0};
            }
            this.sBuffer = c2755a.m1810a(cache_sBuffer, 7, true);
            this.iTimeout = c2755a.m1796a(this.iTimeout, 8, true);
            if (cache_context == null) {
                HashMap map = new HashMap();
                cache_context = map;
                map.put("", "");
            }
            this.context = (Map) c2755a.m1800a(cache_context, 9, true);
            if (cache_context == null) {
                HashMap map2 = new HashMap();
                cache_context = map2;
                map2.put("", "");
            }
            this.status = (Map) c2755a.m1800a(cache_context, 10, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void writeTo(C2756b c2756b) {
        c2756b.m1832a(this.iVersion, 1);
        c2756b.m1821a(this.cPacketType, 2);
        c2756b.m1825a(this.iMessageType, 3);
        c2756b.m1825a(this.iRequestId, 4);
        c2756b.m1829a(this.sServantName, 5);
        c2756b.m1829a(this.sFuncName, 6);
        c2756b.m1834a(this.sBuffer, 7);
        c2756b.m1825a(this.iTimeout, 8);
        c2756b.m1831a((Map) this.context, 9);
        c2756b.m1831a((Map) this.status, 10);
    }
}
