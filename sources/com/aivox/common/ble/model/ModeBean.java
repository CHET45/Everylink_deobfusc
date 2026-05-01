package com.aivox.common.ble.model;

/* JADX INFO: loaded from: classes.dex */
public class ModeBean {
    private int mode_1;
    private int mode_2;
    private int mode_3;

    public interface ModeBeanListen {
        void setAlarmData(ExcelAlarmBean excelAlarmBean);

        void setAlarmNumber(int i);

        void setDeviceBean(ExcelDeviceBean excelDeviceBean);

        void setIndexDeviceBean(int i);

        void setModeBean(ModeBean modeBean);

        void setRCT(byte[] bArr);

        void setUseData(ExcelUserBean excelUserBean);

        void setUseNumber(int i);
    }

    public int getMode_1() {
        return this.mode_1;
    }

    public void setMode_1(int i) {
        this.mode_1 = i;
    }

    public int getMode_2() {
        return this.mode_2;
    }

    public void setMode_2(int i) {
        this.mode_2 = i;
    }

    public int getMode_3() {
        return this.mode_3;
    }

    public void setMode_3(int i) {
        this.mode_3 = i;
    }
}
