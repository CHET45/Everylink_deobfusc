package com.jieli.jl_bt_ota.model;

import com.jieli.jl_bt_ota.interfaces.rcsp.ICmdSnGenerator;

/* JADX INFO: loaded from: classes3.dex */
public class BluetoothOTAConfigure {
    public static final int PREFER_BLE = 0;
    public static final int PREFER_SPP = 1;
    private byte[] firmwareFileData;
    private String firmwareFilePath;
    private boolean isPriorityCallbackOtaFinish;
    private boolean isUseAuthDevice;

    @Deprecated
    private String scanFilterData;
    private ICmdSnGenerator snGenerator;
    private int priority = 0;
    private boolean isUseReconnect = false;
    private int bleIntervalMs = 500;
    private int timeoutMs = 3000;
    private boolean isUseJLServer = false;
    private int mtu = 20;
    private boolean isNeedChangeMtu = false;
    private int bleScanMode = 0;

    public static BluetoothOTAConfigure createDefault() {
        return new BluetoothOTAConfigure().setPriority(0).setBleIntervalMs(500).setTimeoutMs(3000).setUseAuthDevice(false).setUseReconnect(false).setMtu(20).setUseJLServer(false).setNeedChangeMtu(false);
    }

    public int getBleIntervalMs() {
        return this.bleIntervalMs;
    }

    public int getBleScanMode() {
        return this.bleScanMode;
    }

    public byte[] getFirmwareFileData() {
        return this.firmwareFileData;
    }

    public String getFirmwareFilePath() {
        return this.firmwareFilePath;
    }

    public int getMtu() {
        return this.mtu;
    }

    public int getPriority() {
        return this.priority;
    }

    @Deprecated
    public String getScanFilterData() {
        return this.scanFilterData;
    }

    public ICmdSnGenerator getSnGenerator() {
        return this.snGenerator;
    }

    public int getTimeoutMs() {
        return this.timeoutMs;
    }

    public boolean isNeedChangeMtu() {
        return this.isNeedChangeMtu;
    }

    public boolean isPriorityCallbackOtaFinish() {
        return this.isPriorityCallbackOtaFinish;
    }

    public boolean isUseAuthDevice() {
        return this.isUseAuthDevice;
    }

    public boolean isUseJLServer() {
        return this.isUseJLServer;
    }

    public boolean isUseReconnect() {
        return this.isUseReconnect;
    }

    public BluetoothOTAConfigure setBleIntervalMs(int i) {
        this.bleIntervalMs = i;
        return this;
    }

    public BluetoothOTAConfigure setBleScanMode(int i) {
        this.bleScanMode = i;
        return this;
    }

    public BluetoothOTAConfigure setFirmwareFileData(byte[] bArr) {
        this.firmwareFileData = bArr;
        return this;
    }

    public BluetoothOTAConfigure setFirmwareFilePath(String str) {
        this.firmwareFilePath = str;
        return this;
    }

    public BluetoothOTAConfigure setMtu(int i) {
        this.mtu = i;
        return this;
    }

    public BluetoothOTAConfigure setNeedChangeMtu(boolean z) {
        this.isNeedChangeMtu = z;
        return this;
    }

    public BluetoothOTAConfigure setPriority(int i) {
        this.priority = i;
        return this;
    }

    public BluetoothOTAConfigure setPriorityCallbackOtaFinish(boolean z) {
        this.isPriorityCallbackOtaFinish = z;
        return this;
    }

    @Deprecated
    public BluetoothOTAConfigure setScanFilterData(String str) {
        this.scanFilterData = str;
        return this;
    }

    public BluetoothOTAConfigure setSnGenerator(ICmdSnGenerator iCmdSnGenerator) {
        this.snGenerator = iCmdSnGenerator;
        return this;
    }

    public BluetoothOTAConfigure setTimeoutMs(int i) {
        if (i < 500) {
            i = 500;
        }
        this.timeoutMs = i;
        return this;
    }

    public BluetoothOTAConfigure setUseAuthDevice(boolean z) {
        this.isUseAuthDevice = z;
        return this;
    }

    public BluetoothOTAConfigure setUseJLServer(boolean z) {
        this.isUseJLServer = z;
        return this;
    }

    public BluetoothOTAConfigure setUseReconnect(boolean z) {
        this.isUseReconnect = z;
        return this;
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder("BluetoothOTAConfigure{priority=").append(this.priority).append(", isUseReconnect=").append(this.isUseReconnect).append(", isUseAuthDevice=").append(this.isUseAuthDevice).append(", bleIntervalMs=").append(this.bleIntervalMs).append(", timeoutMs=").append(this.timeoutMs).append(", isUseJLServer=").append(this.isUseJLServer).append(", firmwareFilePath='").append(this.firmwareFilePath).append("', scanFilterData='").append(this.scanFilterData).append("', mtu=").append(this.mtu).append(", isNeedChangeMtu=").append(this.isNeedChangeMtu).append(", bleScanMode=").append(this.bleScanMode).append(", firmwareFileData= ");
        byte[] bArr = this.firmwareFileData;
        return sbAppend.append(bArr == null ? 0 : bArr.length).append(", snGenerator=").append(this.snGenerator).append(", isPriorityCallbackOtaFinish=").append(this.isPriorityCallbackOtaFinish).append('}').toString();
    }
}
