package com.tencent.beacon.pack;

/* JADX INFO: loaded from: classes4.dex */
public final class RequestPackage extends AbstractJceStruct {
    static byte[] cache_sBuffer;
    public String appVersion;
    public String appkey;
    public int cmd;
    public byte encryType;
    public String model;
    public String osVersion;
    public byte platformId;
    public String reserved;
    public byte[] sBuffer;
    public String sdkId;
    public String sdkVersion;
    public byte zipType;

    public RequestPackage() {
        this.platformId = (byte) 0;
        this.appkey = "";
        this.appVersion = "";
        this.sdkId = "";
        this.sdkVersion = "";
        this.cmd = 0;
        this.sBuffer = null;
        this.encryType = (byte) 0;
        this.zipType = (byte) 0;
        this.model = "";
        this.osVersion = "";
        this.reserved = "";
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void readFrom(C2755a c2755a) {
        this.platformId = c2755a.m1793a(this.platformId, 0, true);
        this.appkey = c2755a.m1801a(1, true);
        this.appVersion = c2755a.m1801a(2, true);
        this.sdkId = c2755a.m1801a(3, true);
        this.sdkVersion = c2755a.m1801a(4, true);
        this.cmd = c2755a.m1796a(this.cmd, 5, true);
        if (cache_sBuffer == null) {
            cache_sBuffer = new byte[]{0};
        }
        this.sBuffer = c2755a.m1810a(cache_sBuffer, 6, true);
        this.encryType = c2755a.m1793a(this.encryType, 7, true);
        this.zipType = c2755a.m1793a(this.zipType, 8, true);
        this.model = c2755a.m1801a(9, false);
        this.osVersion = c2755a.m1801a(10, false);
        this.reserved = c2755a.m1801a(11, false);
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void writeTo(C2756b c2756b) {
        c2756b.m1821a(this.platformId, 0);
        c2756b.m1829a(this.appkey, 1);
        c2756b.m1829a(this.appVersion, 2);
        c2756b.m1829a(this.sdkId, 3);
        c2756b.m1829a(this.sdkVersion, 4);
        c2756b.m1825a(this.cmd, 5);
        c2756b.m1834a(this.sBuffer, 6);
        c2756b.m1821a(this.encryType, 7);
        c2756b.m1821a(this.zipType, 8);
        String str = this.model;
        if (str != null) {
            c2756b.m1829a(str, 9);
        }
        String str2 = this.osVersion;
        if (str2 != null) {
            c2756b.m1829a(str2, 10);
        }
        String str3 = this.reserved;
        if (str3 != null) {
            c2756b.m1829a(str3, 11);
        }
    }

    public RequestPackage(byte b, String str, String str2, String str3, String str4, int i, byte[] bArr, byte b2, byte b3, String str5, String str6, String str7) {
        this.platformId = b;
        this.appkey = str;
        this.appVersion = str2;
        this.sdkId = str3;
        this.sdkVersion = str4;
        this.cmd = i;
        this.sBuffer = bArr;
        this.encryType = b2;
        this.zipType = b3;
        this.model = str5;
        this.osVersion = str6;
        this.reserved = str7;
    }
}
