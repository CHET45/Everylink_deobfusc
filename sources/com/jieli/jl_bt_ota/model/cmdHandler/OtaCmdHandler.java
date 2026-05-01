package com.jieli.jl_bt_ota.model.cmdHandler;

import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.base.CommonResponse;
import com.jieli.jl_bt_ota.model.command.EnterUpdateModeCmd;
import com.jieli.jl_bt_ota.model.command.ExitUpdateModeCmd;
import com.jieli.jl_bt_ota.model.command.FirmwareUpdateBlockCmd;
import com.jieli.jl_bt_ota.model.command.FirmwareUpdateStatusCmd;
import com.jieli.jl_bt_ota.model.command.GetUpdateFileOffsetCmd;
import com.jieli.jl_bt_ota.model.command.InquireUpdateCmd;
import com.jieli.jl_bt_ota.model.command.NotifyUpdateContentSizeCmd;
import com.jieli.jl_bt_ota.model.command.RebootDeviceCmd;
import com.jieli.jl_bt_ota.model.parameter.FirmwareUpdateBlockParam;
import com.jieli.jl_bt_ota.model.parameter.InquireUpdateParam;
import com.jieli.jl_bt_ota.model.parameter.NotifyUpdateContentSizeParam;
import com.jieli.jl_bt_ota.model.parameter.RebootDeviceParam;
import com.jieli.jl_bt_ota.model.response.EnterUpdateModeResponse;
import com.jieli.jl_bt_ota.model.response.ExitUpdateModeResponse;
import com.jieli.jl_bt_ota.model.response.FirmwareUpdateBlockResponse;
import com.jieli.jl_bt_ota.model.response.FirmwareUpdateStatusResponse;
import com.jieli.jl_bt_ota.model.response.InquireUpdateResponse;
import com.jieli.jl_bt_ota.model.response.RebootDeviceResponse;
import com.jieli.jl_bt_ota.model.response.UpdateFileOffsetResponse;
import com.jieli.jl_bt_ota.util.CHexConver;

/* JADX INFO: loaded from: classes3.dex */
public class OtaCmdHandler implements ICmdHandler {
    @Override // com.jieli.jl_bt_ota.interfaces.command.ICmdHandler
    public CommandBase parseDataToCmd(BasePacket basePacket, CommandBase commandBase) {
        int i;
        int iBytesToInt;
        int iBytesToInt2;
        if (basePacket == null) {
            return null;
        }
        int opCode = basePacket.getOpCode();
        if (opCode == 225 || opCode == 226 || opCode == 227 || opCode == 228 || opCode == 229 || opCode == 230 || opCode == 231 || opCode == 232) {
            byte[] paramData = basePacket.getParamData();
            int iBytesToInt3 = 0;
            if (basePacket.getType() == 1) {
                switch (opCode) {
                    case Command.CMD_OTA_GET_DEVICE_UPDATE_FILE_INFO_OFFSET /* 225 */:
                        return new GetUpdateFileOffsetCmd().setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_OTA_INQUIRE_DEVICE_IF_CAN_UPDATE /* 226 */:
                        byte[] bArr = new byte[0];
                        if (paramData == null || paramData.length <= 0) {
                            paramData = bArr;
                        }
                        return new InquireUpdateCmd(new InquireUpdateParam(paramData)).setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_OTA_ENTER_UPDATE_MODE /* 227 */:
                        return new EnterUpdateModeCmd().setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_OTA_EXIT_UPDATE_MODE /* 228 */:
                        return new ExitUpdateModeCmd().setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_OTA_SEND_FIRMWARE_UPDATE_BLOCK /* 229 */:
                        if (paramData == null || paramData.length < 6) {
                            iBytesToInt = 0;
                        } else {
                            byte[] bArr2 = new byte[4];
                            System.arraycopy(paramData, 0, bArr2, 0, 4);
                            iBytesToInt3 = CHexConver.bytesToInt(bArr2);
                            iBytesToInt = CHexConver.bytesToInt(paramData[4], paramData[5]);
                        }
                        return new FirmwareUpdateBlockCmd(new FirmwareUpdateBlockParam(iBytesToInt3, iBytesToInt)).setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_OTA_GET_DEVICE_REFRESH_FIRMWARE_STATUS /* 230 */:
                        return new FirmwareUpdateStatusCmd().setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_REBOOT_DEVICE /* 231 */:
                        if (paramData != null && paramData.length > 0) {
                            iBytesToInt3 = CHexConver.byteToInt(paramData[0]);
                        }
                        return new RebootDeviceCmd(new RebootDeviceParam(iBytesToInt3)).setOpCodeSn(basePacket.getOpCodeSn());
                    case Command.CMD_OTA_NOTIFY_UPDATE_CONTENT_SIZE /* 232 */:
                        if (paramData == null || paramData.length < 4) {
                            iBytesToInt2 = 0;
                        } else {
                            byte[] bArr3 = new byte[4];
                            System.arraycopy(paramData, 0, bArr3, 0, 4);
                            iBytesToInt2 = CHexConver.bytesToInt(bArr3);
                            if (paramData.length >= 8) {
                                System.arraycopy(paramData, 4, bArr3, 0, 4);
                                iBytesToInt3 = CHexConver.bytesToInt(bArr3);
                            }
                        }
                        return new NotifyUpdateContentSizeCmd(new NotifyUpdateContentSizeParam(iBytesToInt2).setCurrentProgress(iBytesToInt3)).setOpCodeSn(basePacket.getOpCodeSn());
                }
            }
            int iByteToInt = -1;
            switch (opCode) {
                case Command.CMD_OTA_GET_DEVICE_UPDATE_FILE_INFO_OFFSET /* 225 */:
                    if (paramData == null || paramData.length < 6) {
                        i = 0;
                    } else {
                        byte[] bArr4 = new byte[4];
                        System.arraycopy(paramData, 0, bArr4, 0, 4);
                        int iBytesToInt4 = CHexConver.bytesToInt(bArr4);
                        iBytesToInt3 = CHexConver.bytesToInt(paramData[4], paramData[5]);
                        i = iBytesToInt4;
                    }
                    UpdateFileOffsetResponse updateFileOffsetResponse = new UpdateFileOffsetResponse();
                    updateFileOffsetResponse.setRawData(paramData);
                    updateFileOffsetResponse.setUpdateFileFlagOffset(i);
                    updateFileOffsetResponse.setUpdateFileFlagLen(iBytesToInt3);
                    GetUpdateFileOffsetCmd getUpdateFileOffsetCmd = commandBase instanceof GetUpdateFileOffsetCmd ? (GetUpdateFileOffsetCmd) commandBase : new GetUpdateFileOffsetCmd();
                    getUpdateFileOffsetCmd.setOpCodeSn(basePacket.getOpCodeSn());
                    getUpdateFileOffsetCmd.setStatus(basePacket.getStatus());
                    getUpdateFileOffsetCmd.setResponse(updateFileOffsetResponse);
                    return getUpdateFileOffsetCmd;
                case Command.CMD_OTA_INQUIRE_DEVICE_IF_CAN_UPDATE /* 226 */:
                    if (paramData != null && paramData.length > 0) {
                        iByteToInt = CHexConver.byteToInt(paramData[0]);
                    }
                    InquireUpdateResponse inquireUpdateResponse = new InquireUpdateResponse(iByteToInt);
                    inquireUpdateResponse.setRawData(paramData);
                    InquireUpdateCmd inquireUpdateCmd = commandBase instanceof InquireUpdateCmd ? (InquireUpdateCmd) commandBase : new InquireUpdateCmd(new InquireUpdateParam(new byte[0]));
                    inquireUpdateCmd.setOpCodeSn(basePacket.getOpCodeSn());
                    inquireUpdateCmd.setStatus(basePacket.getStatus());
                    inquireUpdateCmd.setResponse(inquireUpdateResponse);
                    return inquireUpdateCmd;
                case Command.CMD_OTA_ENTER_UPDATE_MODE /* 227 */:
                    if (paramData != null && paramData.length > 0) {
                        iByteToInt = CHexConver.byteToInt(paramData[0]);
                    }
                    EnterUpdateModeResponse enterUpdateModeResponse = new EnterUpdateModeResponse(iByteToInt);
                    enterUpdateModeResponse.setRawData(paramData);
                    EnterUpdateModeCmd enterUpdateModeCmd = commandBase instanceof EnterUpdateModeCmd ? (EnterUpdateModeCmd) commandBase : new EnterUpdateModeCmd();
                    enterUpdateModeCmd.setOpCodeSn(basePacket.getOpCodeSn());
                    enterUpdateModeCmd.setStatus(basePacket.getStatus());
                    enterUpdateModeCmd.setResponse(enterUpdateModeResponse);
                    return enterUpdateModeCmd;
                case Command.CMD_OTA_EXIT_UPDATE_MODE /* 228 */:
                    if (paramData != null && paramData.length > 0) {
                        iBytesToInt3 = CHexConver.byteToInt(paramData[0]);
                    }
                    ExitUpdateModeResponse exitUpdateModeResponse = new ExitUpdateModeResponse(iBytesToInt3);
                    exitUpdateModeResponse.setRawData(paramData);
                    ExitUpdateModeCmd exitUpdateModeCmd = commandBase instanceof ExitUpdateModeCmd ? (ExitUpdateModeCmd) commandBase : new ExitUpdateModeCmd();
                    exitUpdateModeCmd.setOpCodeSn(basePacket.getOpCodeSn());
                    exitUpdateModeCmd.setStatus(basePacket.getStatus());
                    exitUpdateModeCmd.setResponse(exitUpdateModeResponse);
                    return exitUpdateModeCmd;
                case Command.CMD_OTA_SEND_FIRMWARE_UPDATE_BLOCK /* 229 */:
                    byte[] bArr5 = new byte[0];
                    if (paramData != null && paramData.length > 0) {
                        bArr5 = paramData;
                    }
                    FirmwareUpdateBlockResponse firmwareUpdateBlockResponse = new FirmwareUpdateBlockResponse();
                    firmwareUpdateBlockResponse.setRawData(paramData);
                    firmwareUpdateBlockResponse.setFirmwareUpdateBlockData(bArr5);
                    FirmwareUpdateBlockCmd firmwareUpdateBlockCmd = commandBase instanceof FirmwareUpdateBlockCmd ? (FirmwareUpdateBlockCmd) commandBase : new FirmwareUpdateBlockCmd(new FirmwareUpdateBlockParam(0, 0));
                    firmwareUpdateBlockCmd.setOpCodeSn(basePacket.getOpCodeSn());
                    firmwareUpdateBlockCmd.setStatus(basePacket.getStatus());
                    firmwareUpdateBlockCmd.setResponse(firmwareUpdateBlockResponse);
                    return firmwareUpdateBlockCmd;
                case Command.CMD_OTA_GET_DEVICE_REFRESH_FIRMWARE_STATUS /* 230 */:
                    if (paramData != null && paramData.length > 0) {
                        iByteToInt = CHexConver.byteToInt(paramData[0]);
                    }
                    FirmwareUpdateStatusResponse firmwareUpdateStatusResponse = new FirmwareUpdateStatusResponse(iByteToInt);
                    firmwareUpdateStatusResponse.setRawData(paramData);
                    FirmwareUpdateStatusCmd firmwareUpdateStatusCmd = commandBase instanceof FirmwareUpdateStatusCmd ? (FirmwareUpdateStatusCmd) commandBase : new FirmwareUpdateStatusCmd();
                    firmwareUpdateStatusCmd.setOpCodeSn(basePacket.getOpCodeSn());
                    firmwareUpdateStatusCmd.setStatus(basePacket.getStatus());
                    firmwareUpdateStatusCmd.setResponse(firmwareUpdateStatusResponse);
                    return firmwareUpdateStatusCmd;
                case Command.CMD_REBOOT_DEVICE /* 231 */:
                    if (paramData != null && paramData.length > 0) {
                        iByteToInt = CHexConver.byteToInt(paramData[0]);
                    }
                    RebootDeviceResponse rebootDeviceResponse = new RebootDeviceResponse(iByteToInt);
                    rebootDeviceResponse.setRawData(paramData);
                    RebootDeviceCmd rebootDeviceCmd = commandBase instanceof RebootDeviceCmd ? (RebootDeviceCmd) commandBase : new RebootDeviceCmd(new RebootDeviceParam(0));
                    rebootDeviceCmd.setOpCodeSn(basePacket.getOpCodeSn());
                    rebootDeviceCmd.setStatus(basePacket.getStatus());
                    rebootDeviceCmd.setResponse(rebootDeviceResponse);
                    return rebootDeviceCmd;
                case Command.CMD_OTA_NOTIFY_UPDATE_CONTENT_SIZE /* 232 */:
                    CommonResponse commonResponse = new CommonResponse();
                    commonResponse.setRawData(paramData);
                    NotifyUpdateContentSizeCmd notifyUpdateContentSizeCmd = commandBase instanceof NotifyUpdateContentSizeCmd ? (NotifyUpdateContentSizeCmd) commandBase : new NotifyUpdateContentSizeCmd(new NotifyUpdateContentSizeParam(0));
                    notifyUpdateContentSizeCmd.setOpCodeSn(basePacket.getOpCodeSn());
                    notifyUpdateContentSizeCmd.setStatus(basePacket.getStatus());
                    notifyUpdateContentSizeCmd.setResponse(commonResponse);
                    return notifyUpdateContentSizeCmd;
            }
        }
        return null;
    }
}
