package com.aivox.base.model;

import android.bluetooth.BluetoothDevice;
import android.view.MotionEvent;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BaseEventBean {

    /* JADX INFO: renamed from: a */
    int f184a;

    /* JADX INFO: renamed from: b */
    int f185b;

    /* JADX INFO: renamed from: c */
    int f186c;
    BluetoothDevice device;
    int from;
    Identity identity;
    boolean isTrue;
    List<Transcribe> list;
    MotionEvent motionEvent;

    /* JADX INFO: renamed from: s1 */
    String f187s1;
    byte[] startTime;
    Transcribe transcribe;

    public BaseEventBean(int i, List<Transcribe> list) {
        this.from = i;
        this.list = list;
    }

    public BaseEventBean(int i, int i2, String str) {
        this.from = i;
        this.f184a = i2;
        this.f187s1 = str;
    }

    public BaseEventBean(int i, Transcribe transcribe) {
        this.from = i;
        this.transcribe = transcribe;
    }

    public BaseEventBean(int i, BluetoothDevice bluetoothDevice) {
        this.from = i;
        this.device = bluetoothDevice;
    }

    public BaseEventBean(int i, boolean z) {
        this.from = i;
        this.isTrue = z;
    }

    public BaseEventBean(int i, String str, byte[] bArr) {
        this.from = i;
        this.f187s1 = str;
        this.startTime = bArr;
    }

    public BaseEventBean(int i, String str) {
        this.from = i;
        this.f187s1 = str;
    }

    public BaseEventBean(int i, int i2, int i3, int i4) {
        this.from = i;
        this.f184a = i2;
        this.f185b = i3;
        this.f186c = i4;
    }

    public BaseEventBean(int i, int i2, int i3) {
        this.from = i;
        this.f184a = i2;
        this.f185b = i3;
    }

    public BaseEventBean(int i, int i2) {
        this.from = i;
        this.f184a = i2;
    }

    public BaseEventBean(int i, MotionEvent motionEvent) {
        this.from = i;
        this.motionEvent = motionEvent;
    }

    public BaseEventBean(int i, Identity identity) {
        this.from = i;
        this.identity = identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Identity getIdentity() {
        return this.identity;
    }

    public boolean isTrue() {
        return this.isTrue;
    }

    public void setTrue(boolean z) {
        this.isTrue = z;
    }

    public BaseEventBean(int i) {
        this.from = i;
    }

    public byte[] getStartTime() {
        return this.startTime;
    }

    public void setStartTime(byte[] bArr) {
        this.startTime = bArr;
    }

    public String getS1() {
        return this.f187s1;
    }

    public void setS1(String str) {
        this.f187s1 = str;
    }

    public int getC() {
        return this.f186c;
    }

    public void setC(int i) {
        this.f186c = i;
    }

    public void setA(int i) {
        this.f184a = i;
    }

    public int getA() {
        return this.f184a;
    }

    public int getFrom() {
        return this.from;
    }

    public void setFrom(int i) {
        this.from = i;
    }

    public int getB() {
        return this.f185b;
    }

    public void setB(int i) {
        this.f185b = i;
    }

    public MotionEvent getMotionEvent() {
        return this.motionEvent;
    }

    public void setMotionEvent(MotionEvent motionEvent) {
        this.motionEvent = motionEvent;
    }

    public BluetoothDevice getDevice() {
        return this.device;
    }

    public void setDevice(BluetoothDevice bluetoothDevice) {
        this.device = bluetoothDevice;
    }

    public Transcribe getTranscribe() {
        return this.transcribe;
    }

    public void setTranscribe(Transcribe transcribe) {
        this.transcribe = transcribe;
    }

    public List<Transcribe> getList() {
        return this.list;
    }

    public void setList(List<Transcribe> list) {
        this.list = list;
    }
}
