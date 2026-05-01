package com.tencent.beacon.pack;

/* JADX INFO: loaded from: classes4.dex */
public final class ResponsePackage extends AbstractResponseCommon {
    static byte[] cache_sBuffer;
    public int cmd;
    public String encryKey;
    public String encryPublicKey;
    public byte encryType;
    public byte result;
    public byte[] sBuffer;
    public byte zipType;

    public ResponsePackage() {
        this.result = (byte) 0;
        this.cmd = 0;
        this.sBuffer = null;
        this.encryType = (byte) 0;
        this.zipType = (byte) 0;
        this.encryKey = "";
        this.encryPublicKey = "";
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void readFrom(C2755a c2755a) {
        this.result = c2755a.m1793a(this.result, 0, true);
        this.cmd = c2755a.m1796a(this.cmd, 1, true);
        if (cache_sBuffer == null) {
            cache_sBuffer = new byte[]{0};
        }
        this.sBuffer = c2755a.m1810a(cache_sBuffer, 2, true);
        this.srcGatewayIp = c2755a.m1801a(3, true);
        this.encryType = c2755a.m1793a(this.encryType, 4, true);
        this.zipType = c2755a.m1793a(this.zipType, 5, true);
        this.serverTime = c2755a.m1798a(this.serverTime, 6, true);
        this.encryKey = c2755a.m1801a(7, false);
        this.encryPublicKey = c2755a.m1801a(8, false);
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void writeTo(C2756b c2756b) {
        c2756b.m1821a(this.result, 0);
        c2756b.m1825a(this.cmd, 1);
        c2756b.m1834a(this.sBuffer, 2);
        c2756b.m1829a(this.srcGatewayIp, 3);
        c2756b.m1821a(this.encryType, 4);
        c2756b.m1821a(this.zipType, 5);
        c2756b.m1826a(this.serverTime, 6);
        String str = this.encryKey;
        if (str != null) {
            c2756b.m1829a(str, 7);
        }
        String str2 = this.encryPublicKey;
        if (str2 != null) {
            c2756b.m1829a(str2, 8);
        }
    }

    public ResponsePackage(byte b, int i, byte[] bArr, String str, byte b2, byte b3, long j, String str2, String str3) {
        this.encryType = (byte) 0;
        this.zipType = (byte) 0;
        this.encryKey = "";
        this.encryPublicKey = "";
        this.result = b;
        this.cmd = i;
        this.sBuffer = bArr;
        this.srcGatewayIp = str;
        this.encryType = b2;
        this.zipType = b3;
        this.serverTime = j;
        this.encryKey = str2;
        this.encryPublicKey = str3;
    }
}
