package com.jieli.jl_bt_ota.model.response;

import com.jieli.jl_bt_ota.model.base.CommonResponse;

/* JADX INFO: loaded from: classes3.dex */
public class RebootDeviceResponse extends CommonResponse {
    private int result;

    public RebootDeviceResponse(int i) {
        this.result = i;
    }

    public int getResult() {
        return this.result;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommonResponse
    public String toString() {
        return "RebootDeviceResponse{rawData size =" + (getRawData() == null ? 0 : getRawData().length) + "\nxmOpCode=" + getXmOpCode() + "\nresult=" + this.result + '}';
    }
}
