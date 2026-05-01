package com.aivox.common.ble.model;

/* JADX INFO: loaded from: classes.dex */
public class ExcelDeviceBean {
    private String bar_code;
    private int battery_charge;
    private String bind_password;
    private String first_time;
    private String made_Time;
    private int prescription_1;
    private int prescription_2;
    private int prescription_3;
    private String rtc;
    private int version;
    private int warning_times;

    public String getBar_code() {
        return this.bar_code;
    }

    public void setBar_code(String str) {
        this.bar_code = str;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int i) {
        this.version = i;
    }

    public String getMade_Time() {
        return this.made_Time;
    }

    public void setMade_Time(String str) {
        this.made_Time = str;
    }

    public String getFirst_time() {
        return this.first_time;
    }

    public void setFirst_time(String str) {
        this.first_time = str;
    }

    public String getBind_password() {
        return this.bind_password;
    }

    public void setBind_password(String str) {
        this.bind_password = str;
    }

    public int getWarning_times() {
        return this.warning_times;
    }

    public void setWarning_times(int i) {
        this.warning_times = i;
    }

    public String getRtc() {
        return this.rtc;
    }

    public void setRtc(String str) {
        this.rtc = str;
    }

    public int getPrescription_1() {
        return this.prescription_1;
    }

    public void setPrescription_1(int i) {
        this.prescription_1 = i;
    }

    public int getPrescription_2() {
        return this.prescription_2;
    }

    public void setPrescription_2(int i) {
        this.prescription_2 = i;
    }

    public int getPrescription_3() {
        return this.prescription_3;
    }

    public void setPrescription_3(int i) {
        this.prescription_3 = i;
    }

    public int getBattery_charge() {
        return this.battery_charge;
    }

    public void setBattery_charge(int i) {
        this.battery_charge = i;
    }
}
