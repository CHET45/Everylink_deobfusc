package com.aivox.besota.sdk.message;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SealingInfo implements Serializable {
    public static final int INVALID = -1;
    public static final int NOT_READY = 0;
    public static final int READY = 1;
    private int leftSealing;
    private int rightSealing;

    public int getLeftSealing() {
        return this.leftSealing;
    }

    public void setLeftSealing(int i) {
        this.leftSealing = i;
    }

    public int getRightSealing() {
        return this.rightSealing;
    }

    public void setRightSealing(int i) {
        this.rightSealing = i;
    }

    public String toString() {
        return "SealingInfo{leftSealing=" + this.leftSealing + ", rightSealing=" + this.rightSealing + '}';
    }
}
