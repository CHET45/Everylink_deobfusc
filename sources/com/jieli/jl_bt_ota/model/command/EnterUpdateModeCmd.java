package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithResponse;
import com.jieli.jl_bt_ota.model.response.EnterUpdateModeResponse;

/* JADX INFO: loaded from: classes3.dex */
public class EnterUpdateModeCmd extends CommandWithResponse<EnterUpdateModeResponse> {
    public EnterUpdateModeCmd() {
        super(Command.CMD_OTA_ENTER_UPDATE_MODE, "EnterUpdateModeCmd");
    }
}
