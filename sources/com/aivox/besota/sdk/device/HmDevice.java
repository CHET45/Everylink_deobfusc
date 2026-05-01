package com.aivox.besota.sdk.device;

import com.aivox.besota.sdk.message.BatteryInfo;
import com.aivox.besota.sdk.utils.AudioChannel;
import com.aivox.besota.sdk.utils.DeviceProtocol;
import com.aivox.besota.sdk.utils.DeviceRole;
import java.io.Serializable;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class HmDevice implements Serializable {
    private BatteryInfo batteryInfo;
    private String bleAddress;
    private AudioChannel channel;
    private String crc;
    private String deviceMAC;
    private String deviceMid;
    private String deviceName;
    private String devicePid;
    private String deviceUid;
    private String deviceVid;
    private String firmwareVer;
    private boolean isConnectable;
    private DeviceProtocol preferredProtocol;
    private DeviceRole role;
    private int rssi;
    private long scannedTimestamp;

    public void setDeviceVid(String str) {
        this.deviceVid = str;
    }

    public void setDevicePid(String str) {
        this.devicePid = str;
    }

    public void setDeviceMid(String str) {
        this.deviceMid = str;
    }

    public void setDeviceMAC(String str) {
        this.deviceMAC = str;
    }

    public void setBleAddress(String str) {
        this.bleAddress = str;
    }

    public void setRssi(int i) {
        this.rssi = i;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String str) {
        this.deviceName = str;
    }

    public void setBatteryInfo(BatteryInfo batteryInfo) {
        this.batteryInfo = batteryInfo;
    }

    public void setRole(DeviceRole deviceRole) {
        this.role = deviceRole;
    }

    public void setConnectable(boolean z) {
        this.isConnectable = z;
    }

    public void setScannedTimestamp(long j) {
        this.scannedTimestamp = j;
    }

    public void setCrc(String str) {
        this.crc = str;
    }

    public void setPreferredProtocol(DeviceProtocol deviceProtocol) {
        this.preferredProtocol = deviceProtocol;
    }

    public void setDeviceUid(String str) {
        this.deviceUid = str;
    }

    public String getDeviceVid() {
        return this.deviceVid;
    }

    public String getDevicePid() {
        return this.devicePid;
    }

    public String getDeviceMid() {
        return this.deviceMid;
    }

    public String getDeviceMAC() {
        return this.deviceMAC;
    }

    public String getBleAddress() {
        return this.bleAddress;
    }

    public int getRssi() {
        return this.rssi;
    }

    public BatteryInfo getBatteryInfo() {
        return this.batteryInfo;
    }

    public DeviceRole getRole() {
        return this.role;
    }

    public boolean isConnectable() {
        return this.isConnectable;
    }

    public long getScannedTimestamp() {
        return this.scannedTimestamp;
    }

    public String getCrc() {
        return this.crc;
    }

    public DeviceProtocol getPreferredProtocol() {
        return this.preferredProtocol;
    }

    public String getDeviceUid() {
        return this.deviceUid;
    }

    public String getFirmwareVer() {
        return this.firmwareVer;
    }

    public void setFirmwareVer(String str) {
        this.firmwareVer = str;
    }

    public AudioChannel getChannel() {
        return this.channel;
    }

    public void setChannel(AudioChannel audioChannel) {
        this.channel = audioChannel;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.deviceMAC, ((HmDevice) obj).deviceMAC);
    }

    public int hashCode() {
        return Objects.hash(this.deviceMAC);
    }

    public void setDevice(HmDevice hmDevice) {
        this.devicePid = hmDevice.devicePid;
        this.deviceVid = hmDevice.deviceVid;
        this.deviceMid = hmDevice.deviceMid;
        this.firmwareVer = hmDevice.firmwareVer;
        this.deviceMAC = hmDevice.deviceMAC;
        this.bleAddress = hmDevice.bleAddress;
        this.deviceName = hmDevice.deviceName;
        this.batteryInfo = hmDevice.batteryInfo;
        this.role = hmDevice.role;
        this.isConnectable = hmDevice.isConnectable;
        this.scannedTimestamp = hmDevice.scannedTimestamp;
        this.crc = hmDevice.crc;
        this.preferredProtocol = hmDevice.preferredProtocol;
        this.deviceUid = hmDevice.deviceUid;
        this.channel = hmDevice.channel;
    }

    public String toString() {
        return "HmDevice{\ndevicePid='" + this.devicePid + "'\ndeviceVid='" + this.deviceVid + "'\ndeviceMid='" + this.deviceMid + "'\nfirmwareVer='" + this.firmwareVer + "'\ndeviceMAC='" + this.deviceMAC + "'\ndeviceName='" + this.deviceName + "'\nbatteryInfo=" + this.batteryInfo + "\nrole=" + this.role + "\nisConnectable=" + this.isConnectable + "\nscannedTimestamp=" + this.scannedTimestamp + "\ncrc='" + this.crc + "'\npreferredProtocol=" + this.preferredProtocol + "\ndeviceUid='" + this.deviceUid + "'\nchannel=" + this.channel + '}';
    }
}
