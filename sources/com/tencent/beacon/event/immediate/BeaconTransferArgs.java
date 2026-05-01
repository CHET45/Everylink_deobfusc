package com.tencent.beacon.event.immediate;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BeaconTransferArgs {

    /* JADX INFO: renamed from: a */
    private byte[] f1609a;

    /* JADX INFO: renamed from: b */
    private String f1610b = "";

    /* JADX INFO: renamed from: c */
    private String f1611c = "";

    public BeaconTransferArgs(byte[] bArr) {
        this.f1609a = bArr;
    }

    public String getAppkey() {
        return this.f1610b;
    }

    public abstract String getCommand();

    public byte[] getData() {
        return this.f1609a;
    }

    public String getEventCode() {
        return this.f1611c;
    }

    public void setAppkey(String str) {
        this.f1610b = str;
    }

    public abstract void setCommand(String str);

    public void setData(byte[] bArr) {
        this.f1609a = bArr;
    }

    public void setEventCode(String str) {
        this.f1611c = str;
    }
}
