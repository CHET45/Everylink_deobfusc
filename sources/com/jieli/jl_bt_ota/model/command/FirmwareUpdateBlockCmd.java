package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.parameter.FirmwareUpdateBlockParam;
import com.jieli.jl_bt_ota.model.response.FirmwareUpdateBlockResponse;

/* JADX INFO: loaded from: classes3.dex */
public class FirmwareUpdateBlockCmd extends CommandWithParamAndResponse<FirmwareUpdateBlockParam, FirmwareUpdateBlockResponse> {
    public FirmwareUpdateBlockCmd(FirmwareUpdateBlockParam firmwareUpdateBlockParam) {
        super(Command.CMD_OTA_SEND_FIRMWARE_UPDATE_BLOCK, "FirmwareUpdateBlockCmd", firmwareUpdateBlockParam);
    }
}
