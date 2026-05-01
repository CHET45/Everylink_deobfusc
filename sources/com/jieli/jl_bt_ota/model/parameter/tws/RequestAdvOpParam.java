package com.jieli.jl_bt_ota.model.parameter.tws;

import com.jieli.jl_bt_ota.model.base.BaseParameter;
import com.jieli.jl_bt_ota.util.CHexConver;

/* JADX INFO: loaded from: classes3.dex */
public class RequestAdvOpParam extends BaseParameter {

    /* JADX INFO: renamed from: op */
    private int f697op;

    public RequestAdvOpParam(int i) {
        this.f697op = i;
    }

    public int getOp() {
        return this.f697op;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter, com.jieli.jl_bt_ota.interfaces.command.IParamBase
    public byte[] getParamData() {
        return new byte[CHexConver.intToByte(this.f697op)];
    }

    public RequestAdvOpParam setOp(int i) {
        this.f697op = i;
        return this;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter
    public String toString() {
        return "RequestAdvOpParam{op=" + this.f697op + "} " + super.toString();
    }
}
