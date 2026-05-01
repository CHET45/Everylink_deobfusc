package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.model.command.tws.NotifyAdvInfoCmd;
import com.jieli.jl_bt_ota.model.command.tws.RequestAdvOpCmd;
import com.jieli.jl_bt_ota.model.command.tws.SetDeviceNotifyAdvInfoCmd;
import com.jieli.jl_bt_ota.model.parameter.tws.NotifyAdvInfoParam;
import com.jieli.jl_bt_ota.model.parameter.tws.RequestAdvOpParam;
import com.jieli.jl_bt_ota.model.parameter.tws.SetDeviceNotifyAdvInfoParam;
import com.jieli.jl_bt_ota.model.response.SetDeviceNotifyAdvInfoResponse;
import com.jieli.jl_bt_ota.tool.ParseHelper;
import com.jieli.jl_bt_ota.util.CHexConver;

/* JADX INFO: loaded from: classes3.dex */
public class TwsCmdHandler implements ICmdHandler {
    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    public CommandBase parseDataToCmd(BasePacket basePacket, CommandBase commandBase) {
        if (basePacket == null) {
            return null;
        }
        int opCode = basePacket.getOpCode();
        if (opCode == 194 || opCode == 195 || opCode == 196) {
            byte[] paramData = basePacket.getParamData();
            int iByteToInt = 0;
            if (basePacket.getType() == 1) {
                switch (opCode) {
                    case Command.CMD_ADV_DEVICE_NOTIFY /* 194 */:
                        NotifyAdvInfoParam notifyAdvInfoParam = new NotifyAdvInfoParam();
                        ParseHelper.parseNotifyADVInfo(notifyAdvInfoParam, basePacket);
                        return new NotifyAdvInfoCmd(notifyAdvInfoParam).setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_ADV_NOTIFY_SETTINGS /* 195 */:
                        if (paramData != null && paramData.length > 0) {
                            iByteToInt = CHexConver.byteToInt(paramData[0]);
                        }
                        return new SetDeviceNotifyAdvInfoCmd(new SetDeviceNotifyAdvInfoParam(iByteToInt)).setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_ADV_DEV_REQUEST_OPERATION /* 196 */:
                        if (paramData != null && paramData.length > 0) {
                            iByteToInt = CHexConver.byteToInt(paramData[0]);
                        }
                        return new RequestAdvOpCmd(new RequestAdvOpParam(iByteToInt)).setOpCodeSn(basePacket.getOpCodeSn());
                }
            }
            if (opCode == 195) {
                SetDeviceNotifyAdvInfoResponse setDeviceNotifyAdvInfoResponse = new SetDeviceNotifyAdvInfoResponse((paramData == null || paramData.length <= 0) ? -1 : CHexConver.byteToInt(paramData[0]));
                setDeviceNotifyAdvInfoResponse.setRawData(paramData);
                SetDeviceNotifyAdvInfoCmd setDeviceNotifyAdvInfoCmd = commandBase instanceof SetDeviceNotifyAdvInfoCmd ? (SetDeviceNotifyAdvInfoCmd) commandBase : new SetDeviceNotifyAdvInfoCmd(new SetDeviceNotifyAdvInfoParam(0));
                setDeviceNotifyAdvInfoCmd.setOpCodeSn(basePacket.getOpCodeSn());
                setDeviceNotifyAdvInfoCmd.setStatus(basePacket.getStatus());
                setDeviceNotifyAdvInfoCmd.setResponse(setDeviceNotifyAdvInfoResponse);
                return setDeviceNotifyAdvInfoCmd;
            }
            if (opCode == 196) {
                CommonResponse commonResponse = new CommonResponse();
                commonResponse.setRawData(paramData);
                RequestAdvOpCmd requestAdvOpCmd = commandBase instanceof RequestAdvOpCmd ? (RequestAdvOpCmd) commandBase : new RequestAdvOpCmd(new RequestAdvOpParam(0));
                requestAdvOpCmd.setOpCodeSn(basePacket.getOpCodeSn());
                requestAdvOpCmd.setStatus(basePacket.getStatus());
                requestAdvOpCmd.setResponse(commonResponse);
                return requestAdvOpCmd;
            }
        }
        return null;
    }
}
