package com.aivox.besota.sdk.message;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class AncSettings implements Serializable {
    public static final int ANC_SPORTS_OFF = 0;
    public static final int ANC_SPORTS_ON = 1;
    public static final int FILTER_AMBIENT_AWARE = 3;
    public static final int FILTER_ANC = 1;
    public static final int FILTER_OFF = 0;
    public static final int FILTER_TALK_THROUGH = 2;
    public static final int INVALID_VALUE = -1;
    private int ancSportsMode = 0;
    private int filter;
    private double gain;

    public void setFilter(int i) {
        this.filter = i;
    }

    public void setGain(double d) {
        this.gain = d;
    }

    public int getFilter() {
        return this.filter;
    }

    public double getGain() {
        return this.gain;
    }

    public int getAncSportsMode() {
        return this.ancSportsMode;
    }

    public void setAncSportsMode(int i) {
        this.ancSportsMode = i;
    }

    public String toString() {
        return "AncSettings{filter=" + this.filter + ", gain=" + this.gain + ", ancSportsMode=" + this.ancSportsMode + '}';
    }
}
