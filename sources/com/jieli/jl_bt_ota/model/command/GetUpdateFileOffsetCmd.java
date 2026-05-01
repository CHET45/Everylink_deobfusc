package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithResponse;
import com.jieli.jl_bt_ota.model.response.UpdateFileOffsetResponse;

/* JADX INFO: loaded from: classes3.dex */
public class GetUpdateFileOffsetCmd extends CommandWithResponse<UpdateFileOffsetResponse> {
    public GetUpdateFileOffsetCmd() {
        super(Command.CMD_OTA_GET_DEVICE_UPDATE_FILE_INFO_OFFSET, "GetUpdateFileOffsetCmd");
    }
}
