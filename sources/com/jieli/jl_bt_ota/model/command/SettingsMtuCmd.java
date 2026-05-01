package com.jieli.jl_bt_ota.model.command;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.model.base.CommandWithParamAndResponse;
import com.jieli.jl_bt_ota.model.parameter.SettingsMtuParam;
import com.jieli.jl_bt_ota.model.response.SettingsMtuResponse;

/* JADX INFO: loaded from: classes3.dex */
public class SettingsMtuCmd extends CommandWithParamAndResponse<SettingsMtuParam, SettingsMtuResponse> {
    public SettingsMtuCmd(SettingsMtuParam settingsMtuParam) {
        super(Command.CMD_SETTINGS_COMMUNICATION_MTU, "SettingsMtuCmd", settingsMtuParam);
    }
}
