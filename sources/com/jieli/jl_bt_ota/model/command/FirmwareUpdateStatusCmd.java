package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithResponse;
import com.jieli.jl_bt_ota.model.response.FirmwareUpdateStatusResponse;

/* JADX INFO: loaded from: classes3.dex */
public class FirmwareUpdateStatusCmd extends CommandWithResponse<FirmwareUpdateStatusResponse> {
    public FirmwareUpdateStatusCmd() {
        super(Command.CMD_OTA_GET_DEVICE_REFRESH_FIRMWARE_STATUS, "FirmwareUpdateStatusCmd");
    }
}
