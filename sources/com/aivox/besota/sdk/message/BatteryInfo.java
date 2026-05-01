package com.aivox.besota.sdk.message;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class BatteryInfo implements Serializable {
    public static final int INVALID_LEVEL = -1;
    private boolean boxCharging;
    private int boxLevel;
    private boolean masterCharging;
    private int masterLevel;
    private int masterVoltage;
    private boolean slaveCharging;
    private int slaveLevel;
    private int slaveVoltage;

    public boolean isMasterCharging() {
        return this.masterCharging;
    }

    public void setMasterCharging(boolean z) {
        this.masterCharging = z;
    }

    public int getMasterLevel() {
        return this.masterLevel;
    }

    public void setMasterLevel(int i) {
        this.masterLevel = i;
    }

    public boolean isSlaveCharging() {
        return this.slaveCharging;
    }

    public void setSlaveCharging(boolean z) {
        this.slaveCharging = z;
    }

    public int getSlaveLevel() {
        return this.slaveLevel;
    }

    public void setSlaveLevel(int i) {
        this.slaveLevel = i;
    }

    public boolean isBoxCharging() {
        return this.boxCharging;
    }

    public void setBoxCharging(boolean z) {
        this.boxCharging = z;
    }

    public int getBoxLevel() {
        return this.boxLevel;
    }

    public void setBoxLevel(int i) {
        this.boxLevel = i;
    }

    public int getMasterVoltage() {
        return this.masterVoltage;
    }

    public void setMasterVoltage(int i) {
        this.masterVoltage = i;
    }

    public int getSlaveVoltage() {
        return this.slaveVoltage;
    }

    public void setSlaveVoltage(int i) {
        this.slaveVoltage = i;
    }

    public String toString() {
        return "BatteryInfo{\nmasterCharging=" + this.masterCharging + "\nmasterLevel=" + this.masterLevel + "\nslaveCharging=" + this.slaveCharging + "\nslaveLevel=" + this.slaveLevel + "\nboxCharging=" + this.boxCharging + "\nboxLevel=" + this.boxLevel + "\nmasterVoltage=" + this.masterVoltage + "\nslaveVoltage=" + this.slaveVoltage + '}';
    }
}
