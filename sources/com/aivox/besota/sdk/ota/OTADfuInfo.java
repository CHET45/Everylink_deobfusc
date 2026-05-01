package com.aivox.besota.sdk.ota;

/* JADX INFO: loaded from: classes.dex */
public class OTADfuInfo {
    private int mBp;
    private String mVer;

    public OTADfuInfo(String str, int i) {
        this.mVer = str;
        this.mBp = i;
    }

    public String getVersion() {
        return this.mVer;
    }

    public int getBreakpoint() {
        return this.mBp;
    }

    public String toString() {
        return "OTADfuInfo{\nmVer='" + this.mVer + "'\nmBp=" + this.mBp + '}';
    }
}
