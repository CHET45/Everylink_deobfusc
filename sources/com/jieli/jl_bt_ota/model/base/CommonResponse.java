package com.jieli.jl_bt_ota.model.base;

/* JADX INFO: loaded from: classes3.dex */
public class CommonResponse extends BaseResponse {
    private int xmOpCode = -1;

    public int getXmOpCode() {
        return this.xmOpCode;
    }

    public void setXmOpCode(int i) {
        this.xmOpCode = i;
    }

    public String toString() {
        return "CommonResponse{xmOpCode=" + this.xmOpCode + '}';
    }
}
