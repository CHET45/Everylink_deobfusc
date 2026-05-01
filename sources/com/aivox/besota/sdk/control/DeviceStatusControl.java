package com.aivox.besota.sdk.control;

import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.message.AncSettings;
import com.aivox.besota.sdk.message.GestureInfo;
import com.aivox.besota.sdk.message.MyBudsInfo;
import com.aivox.besota.sdk.message.SmartSwitchInfo;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface DeviceStatusControl extends BaseControl {
    void getA2DPConnectionStatus(HmDevice hmDevice, DeviceListener deviceListener);

    void getAncSetting(HmDevice hmDevice, DeviceListener deviceListener);

    void getAutoPlayPauseStatus(HmDevice hmDevice, DeviceListener deviceListener);

    void getAutoPowerOffStatus(HmDevice hmDevice, DeviceListener deviceListener);

    void getBatteryInfo(HmDevice hmDevice, DeviceListener deviceListener);

    void getDeviceInfo(DeviceListener deviceListener);

    void getFindMyBuds(HmDevice hmDevice, DeviceListener deviceListener);

    void getGestureStatus(HmDevice hmDevice, int i, DeviceListener deviceListener);

    void getInEarStatus(HmDevice hmDevice, DeviceListener deviceListener);

    void getMultiAIStatus(HmDevice hmDevice, DeviceListener deviceListener);

    void getRunningOTAInfo(HmDevice hmDevice, DeviceListener deviceListener);

    void getSealingStatus(HmDevice hmDevice, DeviceListener deviceListener);

    void getSmartSwitchStatus(HmDevice hmDevice, DeviceListener deviceListener);

    void getTwsConnectStatus(HmDevice hmDevice, DeviceListener deviceListener);

    void resetGestureStatus(HmDevice hmDevice, int i, DeviceListener deviceListener);

    void setAncSetting(HmDevice hmDevice, AncSettings ancSettings, boolean z, DeviceListener deviceListener);

    void setAutoPlayPauseStatus(HmDevice hmDevice, boolean z, DeviceListener deviceListener);

    void setAutoPowerOffStatus(HmDevice hmDevice, int i, DeviceListener deviceListener);

    void setDeviceName(HmDevice hmDevice, String str, DeviceListener deviceListener);

    void setFindMyBuds(HmDevice hmDevice, MyBudsInfo myBudsInfo, DeviceListener deviceListener);

    void setGestureStatus(HmDevice hmDevice, List<GestureInfo> list, DeviceListener deviceListener);

    void setMultiAIStatus(HmDevice hmDevice, int i, DeviceListener deviceListener);

    void setSmartSwitchStatus(HmDevice hmDevice, SmartSwitchInfo smartSwitchInfo, DeviceListener deviceListener);

    void syncCrcWithDevice(HmDevice hmDevice, String str, DeviceListener deviceListener);
}
