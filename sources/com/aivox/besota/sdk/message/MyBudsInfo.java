package com.aivox.besota.sdk.message;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class MyBudsInfo implements Serializable {
    public static final int INVALID_STATUS = -1;
    public static final int OFF = 0;
    private int leftBeeping = -1;
    private int rightBeeping = -1;
    private int leftLighting = -1;
    private int rightLighting = -1;

    public int getLeftBeeping() {
        return this.leftBeeping;
    }

    public void setLeftBeeping(int i) {
        this.leftBeeping = i;
    }

    public int getRightBeeping() {
        return this.rightBeeping;
    }

    public void setRightBeeping(int i) {
        this.rightBeeping = i;
    }

    public int getLeftLighting() {
        return this.leftLighting;
    }

    public void setLeftLighting(int i) {
        this.leftLighting = i;
    }

    public int getRightLighting() {
        return this.rightLighting;
    }

    public void setRightLighting(int i) {
        this.rightLighting = i;
    }

    public String toString() {
        return "MyBudsInfo{leftBeeping=" + this.leftBeeping + ", rightBeeping=" + this.rightBeeping + ", leftLighting=" + this.leftLighting + ", rightLighting=" + this.rightLighting + '}';
    }
}
