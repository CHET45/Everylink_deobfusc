package com.aivox.common.model;

import android.bluetooth.BluetoothDevice;
import android.view.MotionEvent;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class EventBean<T> {

    /* JADX INFO: renamed from: a */
    int f236a;
    AudioInfoBean audioInfoBean;

    /* JADX INFO: renamed from: b */
    int f237b;

    /* JADX INFO: renamed from: c */
    int f238c;

    /* JADX INFO: renamed from: d */
    long f239d;
    BluetoothDevice device;
    int from;
    Identity identity;
    boolean isTrue;
    List<Transcribe> list;
    MotionEvent motionEvent;

    /* JADX INFO: renamed from: s1 */
    String f240s1;

    /* JADX INFO: renamed from: s2 */
    String f241s2;
    byte[] startTime;

    /* JADX INFO: renamed from: t */
    T f242t;
    Transcribe transcribe;

    public EventBean(int i, T t) {
        this.from = i;
        this.f242t = t;
    }

    public EventBean(int i, List<Transcribe> list) {
        this.from = i;
        this.list = list;
    }

    public EventBean(int i, int i2, String str) {
        this.from = i;
        this.f236a = i2;
        this.f240s1 = str;
    }

    public EventBean(int i, Transcribe transcribe) {
        this.from = i;
        this.transcribe = transcribe;
    }

    public EventBean(int i, BluetoothDevice bluetoothDevice) {
        this.from = i;
        this.device = bluetoothDevice;
    }

    public EventBean(int i, boolean z) {
        this.from = i;
        this.isTrue = z;
    }

    public EventBean(int i, String str, byte[] bArr) {
        this.from = i;
        this.f240s1 = str;
        this.startTime = bArr;
    }

    public EventBean(int i, String str) {
        this.from = i;
        this.f240s1 = str;
    }

    public EventBean(int i, int i2, int i3, int i4) {
        this.from = i;
        this.f236a = i2;
        this.f237b = i3;
        this.f238c = i4;
    }

    public EventBean(int i, int i2, int i3) {
        this.from = i;
        this.f236a = i2;
        this.f237b = i3;
    }

    public EventBean(int i, int i2) {
        this.from = i;
        this.f236a = i2;
    }

    public EventBean(int i, MotionEvent motionEvent) {
        this.from = i;
        this.motionEvent = motionEvent;
    }

    public EventBean(int i, Identity identity) {
        this.from = i;
        this.identity = identity;
    }

    public EventBean(int i, int i2, long j) {
        this.from = i;
        this.f239d = j;
        this.f236a = i2;
    }

    public EventBean(int i, AudioInfoBean audioInfoBean) {
        this.from = i;
        this.audioInfoBean = audioInfoBean;
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

    public EventBean(int i) {
        this.from = i;
    }

    public byte[] getStartTime() {
        return this.startTime;
    }

    public void setStartTime(byte[] bArr) {
        this.startTime = bArr;
    }

    public String getS1() {
        return this.f240s1;
    }

    public void setS1(String str) {
        this.f240s1 = str;
    }

    public String getS2() {
        return this.f241s2;
    }

    public void setS2(String str) {
        this.f241s2 = str;
    }

    public int getC() {
        return this.f238c;
    }

    public void setC(int i) {
        this.f238c = i;
    }

    public void setA(int i) {
        this.f236a = i;
    }

    public int getA() {
        return this.f236a;
    }

    public int getFrom() {
        return this.from;
    }

    public void setFrom(int i) {
        this.from = i;
    }

    public int getB() {
        return this.f237b;
    }

    public void setB(int i) {
        this.f237b = i;
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

    public T getT() {
        return this.f242t;
    }

    public void setT(T t) {
        this.f242t = t;
    }

    public long getD() {
        return this.f239d;
    }

    public void setD(long j) {
        this.f239d = j;
    }

    public AudioInfoBean getAudioInfo() {
        return this.audioInfoBean;
    }

    public void setAudioInfo(AudioInfoBean audioInfoBean) {
        this.audioInfoBean = audioInfoBean;
    }
}
