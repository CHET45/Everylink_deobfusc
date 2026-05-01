package com.jieli.jl_bt_ota.model.parameter.tws;

import com.jieli.jl_bt_ota.model.base.BaseParameter;

/* JADX INFO: loaded from: classes3.dex */
public class SetDeviceNotifyAdvInfoParam extends BaseParameter {

    /* JADX INFO: renamed from: op */
    private int f698op;

    public SetDeviceNotifyAdvInfoParam(int i) {
        this.f698op = i;
    }

    public int getOp() {
        return this.f698op;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter, com.jieli.jl_bt_ota.interfaces.command.IParamBase
    public byte[] getParamData() {
        return new byte[]{(byte) this.f698op};
    }

    public SetDeviceNotifyAdvInfoParam setOp(int i) {
        this.f698op = i;
        return this;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter
    public String toString() {
        return "SetDeviceNotifyAdvInfoParam{op=" + this.f698op + "} " + super.toString();
    }
}
