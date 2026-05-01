package com.jieli.jl_bt_ota.model.command.tws;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.model.parameter.tws.RequestAdvOpParam;

/* JADX INFO: loaded from: classes3.dex */
public class RequestAdvOpCmd extends CommandWithParamAndResponse<RequestAdvOpParam, CommonResponse> {
    public RequestAdvOpCmd(RequestAdvOpParam requestAdvOpParam) {
        super(Command.CMD_ADV_DEV_REQUEST_OPERATION, "RequestAdvOpCmd", requestAdvOpParam);
    }
}
