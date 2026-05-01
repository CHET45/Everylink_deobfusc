package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.model.parameter.NotifyUpdateContentSizeParam;

/* JADX INFO: loaded from: classes3.dex */
public class NotifyUpdateContentSizeCmd extends CommandWithParamAndResponse<NotifyUpdateContentSizeParam, CommonResponse> {
    public NotifyUpdateContentSizeCmd(NotifyUpdateContentSizeParam notifyUpdateContentSizeParam) {
        super(Command.CMD_OTA_NOTIFY_UPDATE_CONTENT_SIZE, "NotifyUpdateContentSizeCmd", notifyUpdateContentSizeParam);
    }
}
