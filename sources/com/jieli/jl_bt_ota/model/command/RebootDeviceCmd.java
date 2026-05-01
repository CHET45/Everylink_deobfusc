package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.parameter.RebootDeviceParam;
import com.jieli.jl_bt_ota.model.response.RebootDeviceResponse;

/* JADX INFO: loaded from: classes3.dex */
public class RebootDeviceCmd extends CommandWithParamAndResponse<RebootDeviceParam, RebootDeviceResponse> {
    public RebootDeviceCmd(RebootDeviceParam rebootDeviceParam) {
        super(Command.CMD_REBOOT_DEVICE, "RebootDeviceCmd", rebootDeviceParam);
    }
}
