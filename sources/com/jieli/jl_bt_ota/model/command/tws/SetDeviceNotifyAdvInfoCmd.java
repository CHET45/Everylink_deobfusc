package com.jieli.jl_bt_ota.model.command.tws;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.parameter.tws.SetDeviceNotifyAdvInfoParam;
import com.jieli.jl_bt_ota.model.response.SetDeviceNotifyAdvInfoResponse;

/* JADX INFO: loaded from: classes3.dex */
public class SetDeviceNotifyAdvInfoCmd extends CommandWithParamAndResponse<SetDeviceNotifyAdvInfoParam, SetDeviceNotifyAdvInfoResponse> {
    public SetDeviceNotifyAdvInfoCmd(SetDeviceNotifyAdvInfoParam setDeviceNotifyAdvInfoParam) {
        super(Command.CMD_ADV_NOTIFY_SETTINGS, "SetDeviceNotifyAdvInfoCmd", setDeviceNotifyAdvInfoParam);
    }
}
