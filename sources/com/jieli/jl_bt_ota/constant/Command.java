package com.jieli.jl_bt_ota.constant;

import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.cmdHandler.CustomCmdHandler;
import com.jieli.jl_bt_ota.model.cmdHandler.DataCmdHandler;
import com.jieli.jl_bt_ota.model.cmdHandler.DisconnectClassicBluetoothCmdHandler;
import com.jieli.jl_bt_ota.model.cmdHandler.GetDevMD5CmdHandler;
import com.jieli.jl_bt_ota.model.cmdHandler.GetTargetFeatureMapCmdHandler;
import com.jieli.jl_bt_ota.model.cmdHandler.GetTargetInfoCmdHandler;
import com.jieli.jl_bt_ota.model.cmdHandler.NotifyCommunicationWayCmdHandler;
import com.jieli.jl_bt_ota.model.cmdHandler.OtaCmdHandler;
import com.jieli.jl_bt_ota.model.cmdHandler.RcspCmdHandler;
import com.jieli.jl_bt_ota.model.cmdHandler.SettingsMtuCmdHandler;
import com.jieli.jl_bt_ota.model.cmdHandler.TwsCmdHandler;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class Command {
    public static final int CMD_ADV_DEVICE_NOTIFY = 194;
    public static final int CMD_ADV_DEV_REQUEST_OPERATION = 196;
    public static final int CMD_ADV_NOTIFY_SETTINGS = 195;
    public static final int CMD_CUSTOM = 240;
    public static final int CMD_DATA = 1;
    public static final int CMD_DISCONNECT_CLASSIC_BLUETOOTH = 6;
    public static final int CMD_EXTRA_CUSTOM = 255;
    public static final int CMD_GET_DEV_MD5 = 212;
    public static final int CMD_GET_TARGET_FEATURE_MAP = 2;
    public static final int CMD_GET_TARGET_INFO = 3;
    public static final int CMD_OTA_ENTER_UPDATE_MODE = 227;
    public static final int CMD_OTA_EXIT_UPDATE_MODE = 228;
    public static final int CMD_OTA_GET_DEVICE_REFRESH_FIRMWARE_STATUS = 230;
    public static final int CMD_OTA_GET_DEVICE_UPDATE_FILE_INFO_OFFSET = 225;
    public static final int CMD_OTA_INQUIRE_DEVICE_IF_CAN_UPDATE = 226;
    public static final int CMD_OTA_NOTIFY_UPDATE_CONTENT_SIZE = 232;
    public static final int CMD_OTA_SEND_FIRMWARE_UPDATE_BLOCK = 229;
    public static final int CMD_REBOOT_DEVICE = 231;
    public static final int CMD_SETTINGS_COMMUNICATION_MTU = 209;
    public static final int CMD_SWITCH_DEVICE_REQUEST = 11;

    /* JADX INFO: renamed from: a */
    private static Map<Integer, ICmdHandler> f609a;

    public static Map<Integer, ICmdHandler> getValidCommandList() {
        Map<Integer, ICmdHandler> map = f609a;
        if (map == null || map.size() == 0) {
            HashMap map2 = new HashMap();
            f609a = map2;
            map2.put(1, new DataCmdHandler());
            f609a.put(2, new GetTargetFeatureMapCmdHandler());
            f609a.put(3, new GetTargetInfoCmdHandler());
            f609a.put(6, new DisconnectClassicBluetoothCmdHandler());
            f609a.put(11, new NotifyCommunicationWayCmdHandler());
            f609a.put(Integer.valueOf(CMD_OTA_GET_DEVICE_UPDATE_FILE_INFO_OFFSET), new OtaCmdHandler());
            f609a.put(Integer.valueOf(CMD_OTA_INQUIRE_DEVICE_IF_CAN_UPDATE), new OtaCmdHandler());
            f609a.put(Integer.valueOf(CMD_OTA_ENTER_UPDATE_MODE), new OtaCmdHandler());
            f609a.put(Integer.valueOf(CMD_OTA_EXIT_UPDATE_MODE), new OtaCmdHandler());
            f609a.put(Integer.valueOf(CMD_OTA_SEND_FIRMWARE_UPDATE_BLOCK), new OtaCmdHandler());
            f609a.put(Integer.valueOf(CMD_OTA_GET_DEVICE_REFRESH_FIRMWARE_STATUS), new OtaCmdHandler());
            f609a.put(Integer.valueOf(CMD_REBOOT_DEVICE), new OtaCmdHandler());
            f609a.put(Integer.valueOf(CMD_OTA_NOTIFY_UPDATE_CONTENT_SIZE), new OtaCmdHandler());
            f609a.put(Integer.valueOf(CMD_SETTINGS_COMMUNICATION_MTU), new SettingsMtuCmdHandler());
            f609a.put(212, new GetDevMD5CmdHandler());
            f609a.put(Integer.valueOf(CMD_CUSTOM), new RcspCmdHandler());
            f609a.put(255, new CustomCmdHandler());
            f609a.put(Integer.valueOf(CMD_ADV_DEVICE_NOTIFY), new TwsCmdHandler());
            f609a.put(Integer.valueOf(CMD_ADV_NOTIFY_SETTINGS), new TwsCmdHandler());
            f609a.put(Integer.valueOf(CMD_ADV_DEV_REQUEST_OPERATION), new TwsCmdHandler());
        }
        return f609a;
    }

    public static boolean isValidCmd(int i) {
        return getValidCommandList().containsKey(Integer.valueOf(i));
    }
}
