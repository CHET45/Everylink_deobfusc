package com.aivox.common.ble.model;

/* JADX INFO: loaded from: classes.dex */
public class ExcelAlarmBean {
    private String alarm_information_1;
    private String alarm_information_2;
    private int charge_discharge_Num;
    private int index;
    private int prescription;
    private int remain_time;
    private int temperature;
    private int total_number;
    private int typeId;
    private int userId;
    private int verId;

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int i) {
        this.temperature = i;
    }

    public int getVerId() {
        return this.verId;
    }

    public void setVerId(int i) {
        this.verId = i;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public int getTypeId() {
        return this.typeId;
    }

    public void setTypeId(int i) {
        this.typeId = i;
    }

    public int getRemain_time() {
        return this.remain_time;
    }

    public void setRemain_time(int i) {
        this.remain_time = i;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int i) {
        this.userId = i;
    }

    public int getPrescription() {
        return this.prescription;
    }

    public void setPrescription(int i) {
        this.prescription = i;
    }

    public int getTotal_number() {
        return this.total_number;
    }

    public void setTotal_number(int i) {
        this.total_number = i;
    }

    public int getCharge_discharge_Num() {
        return this.charge_discharge_Num;
    }

    public void setCharge_discharge_Num(int i) {
        this.charge_discharge_Num = i;
    }

    public String getAlarm_information_1() {
        return this.alarm_information_1;
    }

    public void setAlarm_information_1(String str) {
        this.alarm_information_1 = str;
    }

    public String getAlarm_information_2() {
        return this.alarm_information_2;
    }

    public void setAlarm_information_2(String str) {
        this.alarm_information_2 = str;
    }
}
