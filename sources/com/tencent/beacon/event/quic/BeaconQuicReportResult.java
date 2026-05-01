package com.tencent.beacon.event.quic;

import java.util.Arrays;

/* JADX INFO: loaded from: classes4.dex */
public class BeaconQuicReportResult {
    private int code;
    private byte[] resBuffer;
    private int resCode;
    private String resMsg;

    public BeaconQuicReportResult(int i, int i2, byte[] bArr, String str) {
        this.code = i;
        this.resCode = i2;
        this.resBuffer = bArr;
        this.resMsg = str;
    }

    public int getCode() {
        return this.code;
    }

    public byte[] getResBuffer() {
        return this.resBuffer;
    }

    public int getResCode() {
        return this.resCode;
    }

    public String getResMsg() {
        return this.resMsg;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public void setResBuffer(byte[] bArr) {
        this.resBuffer = bArr;
    }

    public void setResCode(int i) {
        this.resCode = i;
    }

    public void setResMsg(String str) {
        this.resMsg = str;
    }

    public String toString() {
        return "BeaconQuicReportResult{code=" + this.code + ", bizCode=" + this.resCode + ", bizBuffer=" + Arrays.toString(this.resBuffer) + ", bizMsg='" + this.resMsg + "'}";
    }
}
