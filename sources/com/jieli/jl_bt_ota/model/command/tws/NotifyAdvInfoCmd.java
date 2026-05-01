package com.jieli.jl_bt_ota.model.command.tws;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithParam;
import com.jieli.jl_bt_ota.model.parameter.tws.NotifyAdvInfoParam;

/* JADX INFO: loaded from: classes3.dex */
public class NotifyAdvInfoCmd extends CommandWithParam<NotifyAdvInfoParam> {
    public NotifyAdvInfoCmd(NotifyAdvInfoParam notifyAdvInfoParam) {
        super(Command.CMD_ADV_DEVICE_NOTIFY, "NotifyAdvInfoCmd", notifyAdvInfoParam);
    }
}
