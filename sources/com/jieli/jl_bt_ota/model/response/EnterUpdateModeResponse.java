package com.jieli.jl_bt_ota.model.response;

import com.jieli.jl_bt_ota.model.base.CommonResponse;

/* JADX INFO: loaded from: classes3.dex */
public class EnterUpdateModeResponse extends CommonResponse {
    private int canUpdateFlag;

    public EnterUpdateModeResponse(int i) {
        this.canUpdateFlag = i;
    }

    public int getCanUpdateFlag() {
        return this.canUpdateFlag;
    }

    @Override // com.jieli.jl_bt_ota.model.base.CommonResponse
    public String toString() {
        return "EnterUpdateModeResponse{rawData size =" + (getRawData() == null ? 0 : getRawData().length) + "\nxmOpCode=" + getXmOpCode() + "\ncanUpdateFlag=" + this.canUpdateFlag + '}';
    }
}
