package com.tencent.beacon.pack;

import com.tencent.beacon.base.util.C2695c;

/* JADX INFO: loaded from: classes4.dex */
public final class ResponsePackageV2 extends AbstractResponseCommon implements Cloneable {
    public int result = 0;
    public String msg = "";

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            C2695c.m1465a(e);
            return null;
        }
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void readFrom(C2755a c2755a) {
        this.result = c2755a.m1796a(this.result, 0, true);
        this.srcGatewayIp = c2755a.m1801a(1, true);
        this.serverTime = c2755a.m1798a(this.serverTime, 2, true);
        this.msg = c2755a.m1801a(3, true);
    }

    @Override // com.tencent.beacon.pack.AbstractJceStruct
    public void writeTo(C2756b c2756b) {
        c2756b.m1825a(this.result, 0);
        c2756b.m1829a(this.srcGatewayIp, 1);
        c2756b.m1826a(this.serverTime, 2);
        c2756b.m1829a(this.msg, 3);
    }
}
