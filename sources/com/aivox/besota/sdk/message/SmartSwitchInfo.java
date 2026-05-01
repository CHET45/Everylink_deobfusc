package com.aivox.besota.sdk.message;

import java.io.Serializable;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class SmartSwitchInfo implements Serializable {
    public static final int INVALID = -1;
    private int aac;
    private int latency;
    private int latency2;
    private int sbc;

    public SmartSwitchInfo() {
        this.latency = -1;
        this.latency2 = -1;
    }

    public SmartSwitchInfo(int i, int i2, int i3, int i4) {
        this.aac = i;
        this.sbc = i2;
        this.latency = i3;
        this.latency2 = i4;
    }

    public int getAac() {
        return this.aac;
    }

    public void setAac(int i) {
        this.aac = i;
    }

    public int getSbc() {
        return this.sbc;
    }

    public void setSbc(int i) {
        this.sbc = i;
    }

    public int getLatency() {
        return this.latency;
    }

    public void setLatency(int i) {
        this.latency = i;
    }

    public int getLatency2() {
        return this.latency2;
    }

    public void setLatency2(int i) {
        this.latency2 = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SmartSwitchInfo smartSwitchInfo = (SmartSwitchInfo) obj;
        return this.aac == smartSwitchInfo.aac && this.sbc == smartSwitchInfo.sbc && this.latency == smartSwitchInfo.latency && this.latency2 == smartSwitchInfo.latency2;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.aac), Integer.valueOf(this.sbc), Integer.valueOf(this.latency), Integer.valueOf(this.latency2));
    }

    public String toString() {
        return "SmartSwitchInfo{aac=" + this.aac + ", sbc=" + this.sbc + ", latency=" + this.latency + ", latency2=" + this.latency2 + '}';
    }
}
