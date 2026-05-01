package com.tencent.beacon.event.immediate;

/* JADX INFO: loaded from: classes4.dex */
public class BeaconTransferResult {

    /* JADX INFO: renamed from: a */
    private int f1612a;

    /* JADX INFO: renamed from: b */
    private int f1613b;

    /* JADX INFO: renamed from: c */
    private byte[] f1614c;

    /* JADX INFO: renamed from: d */
    private String f1615d;

    public byte[] getBizBuffer() {
        return this.f1614c;
    }

    public int getBizCode() {
        return this.f1613b;
    }

    public String getBizMsg() {
        return this.f1615d;
    }

    public int getCode() {
        return this.f1612a;
    }

    public void setBizBuffer(byte[] bArr) {
        this.f1614c = bArr;
    }

    public void setBizCode(int i) {
        this.f1613b = i;
    }

    public void setBizMsg(String str) {
        this.f1615d = str;
    }

    public void setCode(int i) {
        this.f1612a = i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("BeaconTransferResult{returnCode=");
        sb.append(this.f1612a);
        sb.append(", bizReturnCode=").append(this.f1613b);
        sb.append(", bizMsg='").append(this.f1615d).append("'}");
        return sb.toString();
    }
}
