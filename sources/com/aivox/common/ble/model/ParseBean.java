package com.aivox.common.ble.model;

/* JADX INFO: loaded from: classes.dex */
public class ParseBean {
    private int alarm_status;
    private int battery_power;
    private int current_Mode;
    private int current_value;
    private int dosage;
    private boolean isStart;
    private int minutes_remaining;
    private int prescription;
    private int resistance_value;
    private int seconds_remaining;
    private int temperature;
    private int type_ID;
    private int ver_ID;
    private int voltage_values;

    public int getVer_ID() {
        return this.ver_ID;
    }

    public void setVer_ID(int i) {
        this.ver_ID = i;
    }

    public int getType_ID() {
        return this.type_ID;
    }

    public void setType_ID(int i) {
        this.type_ID = i;
    }

    public int getCurrent_Mode() {
        return this.current_Mode;
    }

    public void setCurrent_Mode(int i) {
        this.current_Mode = i;
    }

    public boolean isStart() {
        return this.isStart;
    }

    public void setStart(boolean z) {
        this.isStart = z;
    }

    public int getPrescription() {
        return this.prescription;
    }

    public void setPrescription(int i) {
        this.prescription = i;
    }

    public int getDosage() {
        return this.dosage;
    }

    public void setDosage(int i) {
        this.dosage = i;
    }

    public int getMinutes_remaining() {
        return this.minutes_remaining;
    }

    public void setMinutes_remaining(int i) {
        this.minutes_remaining = i;
    }

    public int getSeconds_remaining() {
        return this.seconds_remaining;
    }

    public void setSeconds_remaining(int i) {
        this.seconds_remaining = i;
    }

    public int getAlarm_status() {
        return this.alarm_status;
    }

    public void setAlarm_status(int i) {
        this.alarm_status = i;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int i) {
        this.temperature = i;
    }

    public int getBattery_power() {
        return this.battery_power;
    }

    public void setBattery_power(int i) {
        this.battery_power = i;
    }

    public int getVoltage_values() {
        return this.voltage_values;
    }

    public void setVoltage_values(int i) {
        this.voltage_values = i;
    }

    public int getCurrent_value() {
        return this.current_value;
    }

    public void setCurrent_value(int i) {
        this.current_value = i;
    }

    public int getResistance_value() {
        return this.resistance_value;
    }

    public void setResistance_value(int i) {
        this.resistance_value = i;
    }

    public String toString() {
        return "ParseBean [ver_ID=" + this.ver_ID + ", type_ID=" + this.type_ID + ", current_Mode=" + this.current_Mode + ", isStart=" + this.isStart + ", prescription=" + this.prescription + ", dosage=" + this.dosage + ", minutes_remaining=" + this.minutes_remaining + ", seconds_remaining=" + this.seconds_remaining + ", alarm_status=" + this.alarm_status + ", temperature=" + this.temperature + ", battery_power=" + this.battery_power + ", voltage_values=" + this.voltage_values + ", current_value=" + this.current_value + ", resistance_value=" + this.resistance_value + "]";
    }
}
