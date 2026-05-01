package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithResponse;
import com.jieli.jl_bt_ota.model.response.ExitUpdateModeResponse;

/* JADX INFO: loaded from: classes3.dex */
public class ExitUpdateModeCmd extends CommandWithResponse<ExitUpdateModeResponse> {
    public ExitUpdateModeCmd() {
        super(Command.CMD_OTA_EXIT_UPDATE_MODE, "ExitUpdateModeCmd");
    }
}
