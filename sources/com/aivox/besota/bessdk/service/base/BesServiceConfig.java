package com.aivox.besota.bessdk.service.base;

import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.utils.DeviceProtocol;
import java.io.Serializable;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class BesServiceConfig implements Serializable {
    private UUID characteristicsUUID;
    private UUID descriptorUUID;
    private HmDevice device;
    private DeviceProtocol deviceProtocol;
    private UUID serviceUUID;
    private Boolean totaConnect = false;
    private Boolean useTotaV2 = false;
    private Integer USER_FLAG = 0;
    private Integer curUser = 1;
    private Integer curUpgateType = 1;
    private Boolean curAckType = false;
    private Integer imageSideSelection = 0;
    private Boolean isWithoutResponse = false;

    public void setDeviceProtocol(DeviceProtocol deviceProtocol) {
        this.deviceProtocol = deviceProtocol;
    }

    public void setServiceUUID(UUID uuid) {
        this.serviceUUID = uuid;
    }

    public void setCharacteristicsUUID(UUID uuid) {
        this.characteristicsUUID = uuid;
    }

    public void setDescriptorUUID(UUID uuid) {
        this.descriptorUUID = uuid;
    }

    public void setDevice(HmDevice hmDevice) {
        this.device = hmDevice;
    }

    public void setTotaConnect(Boolean bool) {
        this.totaConnect = bool;
    }

    public void setUseTotaV2(Boolean bool) {
        this.useTotaV2 = bool;
    }

    public void setUSER_FLAG(Integer num) {
        this.USER_FLAG = num;
    }

    public void setCurUser(Integer num) {
        this.curUser = num;
    }

    public void setCurUpgateType(Integer num) {
        this.curUpgateType = num;
    }

    public void setCurAckType(Boolean bool) {
        this.curAckType = bool;
    }

    public void setImageSideSelection(Integer num) {
        this.imageSideSelection = num;
    }

    public void setIsWithoutResponse(Boolean bool) {
        this.isWithoutResponse = bool;
    }

    public DeviceProtocol getDeviceProtocol() {
        return this.deviceProtocol;
    }

    public UUID getServiceUUID() {
        return this.serviceUUID;
    }

    public UUID getCharacteristicsUUID() {
        return this.characteristicsUUID;
    }

    public UUID getDescriptorUUID() {
        return this.descriptorUUID;
    }

    public HmDevice getDevice() {
        return this.device;
    }

    public Boolean getTotaConnect() {
        return this.totaConnect;
    }

    public Boolean getUseTotaV2() {
        return this.useTotaV2;
    }

    public Integer getUSER_FLAG() {
        return this.USER_FLAG;
    }

    public Integer getCurUser() {
        return this.curUser;
    }

    public Integer getCurUpgateType() {
        return this.curUpgateType;
    }

    public Boolean getCurAckType() {
        return this.curAckType;
    }

    public Integer getImageSideSelection() {
        return this.imageSideSelection;
    }

    public Boolean getIsWithoutResponse() {
        return this.isWithoutResponse;
    }
}
