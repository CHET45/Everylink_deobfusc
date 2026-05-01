package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.command.GetDevMD5Cmd;
import com.jieli.jl_bt_ota.model.response.GetDevMD5Response;

/* JADX INFO: loaded from: classes3.dex */
public class GetDevMD5CmdHandler implements ICmdHandler {
    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    public CommandBase parseDataToCmd(BasePacket basePacket, CommandBase commandBase) {
        String str;
        if (basePacket == null || basePacket.getOpCode() != 212) {
            return null;
        }
        byte[] paramData = basePacket.getParamData();
        if (basePacket.getType() == 1) {
            return new GetDevMD5Cmd().setOpCodeSn(basePacket.getOpCodeSn());
        }
        if (paramData == null || paramData.length < 32) {
            str = "";
        } else {
            try {
                str = new String(paramData, 0, 32);
            } catch (Exception e) {
                e.printStackTrace();
                str = "";
            }
        }
        GetDevMD5Response getDevMD5Response = new GetDevMD5Response(str);
        getDevMD5Response.setRawData(paramData);
        GetDevMD5Cmd getDevMD5Cmd = commandBase instanceof GetDevMD5Cmd ? (GetDevMD5Cmd) commandBase : new GetDevMD5Cmd();
        getDevMD5Cmd.setOpCodeSn(basePacket.getOpCodeSn());
        getDevMD5Cmd.setResponse(getDevMD5Response);
        getDevMD5Cmd.setStatus(basePacket.getStatus());
        return getDevMD5Cmd;
    }
}
