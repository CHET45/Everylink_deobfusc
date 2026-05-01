package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.model.command.DataCmd;
import com.jieli.jl_bt_ota.model.command.DataHasResponseCmd;
import com.jieli.jl_bt_ota.model.parameter.DataParam;

/* JADX INFO: loaded from: classes3.dex */
public class DataCmdHandler implements ICmdHandler {
    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    public CommandBase parseDataToCmd(BasePacket basePacket, CommandBase commandBase) {
        if (basePacket == null || basePacket.getOpCode() != 1) {
            return null;
        }
        if (basePacket.getType() == 1) {
            return basePacket.getHasResponse() == 0 ? new DataCmd((DataParam) new DataParam(basePacket.getParamData()).setXmOpCode(basePacket.getXmOpCode())).setOpCodeSn(basePacket.getOpCodeSn()) : new DataHasResponseCmd((DataParam) new DataParam(basePacket.getParamData()).setXmOpCode(basePacket.getXmOpCode())).setOpCodeSn(basePacket.getOpCodeSn());
        }
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setRawData(basePacket.getParamData());
        commonResponse.setXmOpCode(basePacket.getXmOpCode());
        DataHasResponseCmd dataHasResponseCmd = commandBase instanceof DataHasResponseCmd ? (DataHasResponseCmd) commandBase : new DataHasResponseCmd(new DataParam(new byte[0]));
        dataHasResponseCmd.setOpCodeSn(basePacket.getOpCodeSn());
        dataHasResponseCmd.setStatus(basePacket.getStatus());
        dataHasResponseCmd.setResponse(commonResponse);
        return dataHasResponseCmd;
    }
}
