package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.parameter.InquireUpdateParam;
import com.jieli.jl_bt_ota.model.response.InquireUpdateResponse;

/* JADX INFO: loaded from: classes3.dex */
public class InquireUpdateCmd extends CommandWithParamAndResponse<InquireUpdateParam, InquireUpdateResponse> {
    public InquireUpdateCmd(InquireUpdateParam inquireUpdateParam) {
        super(Command.CMD_OTA_INQUIRE_DEVICE_IF_CAN_UPDATE, "InquireUpdateCmd", inquireUpdateParam);
    }
}
