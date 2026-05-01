package com.aivox.besota.sdk.message;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class InEarInfo implements Serializable {
    public static final int INVALID = -1;
    public static final int NOT_READY = 0;
    public static final int READY = 1;
    private int leftInEar;
    private int rightInEar;

    public int getLeftInEar() {
        return this.leftInEar;
    }

    public void setLeftInEar(int i) {
        this.leftInEar = i;
    }

    public int getRightInEar() {
        return this.rightInEar;
    }

    public void setRightInEar(int i) {
        this.rightInEar = i;
    }

    public String toString() {
        return "InEarInfo{leftInEar=" + this.leftInEar + ", rightInEar=" + this.rightInEar + '}';
    }
}
