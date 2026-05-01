package com.tencent.beacon.pack;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public final class EventRecordV2 extends AbstractJceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static byte[] cache_byteValue;
    static Map<String, String> cache_mapValue;
    static int cache_valueType;
    public String appKey = "";
    public String apn = "";
    public String srcIp = "";
    public String eventCode = "";
    public int valueType = 0;
    public Map<String, String> mapValue = null;
    public byte[] byteValue = null;
    public long eventTime = 0;
    public boolean eventResult = true;
    public int eventType = 0;
    public String reserved = "";

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void readFrom(C2755a c2755a) {
        this.appKey = c2755a.m1801a(0, false);
        this.apn = c2755a.m1801a(1, false);
        this.srcIp = c2755a.m1801a(2, false);
        this.eventCode = c2755a.m1801a(3, true);
        this.valueType = c2755a.m1796a(this.valueType, 4, false);
        this.mapValue = (Map) c2755a.m1800a(cache_mapValue, 5, false);
        this.byteValue = c2755a.m1810a(cache_byteValue, 6, false);
        this.eventTime = c2755a.m1798a(this.eventTime, 7, true);
        this.eventResult = c2755a.m1809a(this.eventResult, 8, false);
        this.eventType = c2755a.m1796a(this.eventType, 9, false);
        this.reserved = c2755a.m1801a(10, false);
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void writeTo(C2756b c2756b) {
        String str = this.appKey;
        if (str != null) {
            c2756b.m1829a(str, 0);
        }
        String str2 = this.apn;
        if (str2 != null) {
            c2756b.m1829a(str2, 1);
        }
        String str3 = this.srcIp;
        if (str3 != null) {
            c2756b.m1829a(str3, 2);
        }
        c2756b.m1829a(this.eventCode, 3);
        c2756b.m1825a(this.valueType, 4);
        Map<String, String> map = this.mapValue;
        if (map != null) {
            c2756b.m1831a((Map) map, 5);
        }
        byte[] bArr = this.byteValue;
        if (bArr != null) {
            c2756b.m1834a(bArr, 6);
        }
        c2756b.m1826a(this.eventTime, 7);
        c2756b.m1833a(this.eventResult, 8);
        c2756b.m1825a(this.eventType, 9);
        String str4 = this.reserved;
        if (str4 != null) {
            c2756b.m1829a(str4, 10);
        }
    }

    static {
        HashMap map = new HashMap();
        cache_mapValue = map;
        map.put("", "");
        cache_byteValue = new byte[]{0};
    }
}
