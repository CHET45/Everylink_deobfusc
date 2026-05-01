package com.jieli.jl_bt_ota.model.parameter.tws;

import com.jieli.jl_bt_ota.model.base.BaseParameter;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;

/* JADX INFO: loaded from: classes3.dex */
public class NotifyAdvInfoParam extends BaseParameter {
    private int action;
    private int chargingBinQuantity;
    private int deviceType;
    private String edrAddr;
    private boolean isDeviceCharging;
    private boolean isLeftCharging;
    private boolean isRightCharging;
    private int leftDeviceQuantity;
    private int pid;
    private int rightDeviceQuantity;
    private int seq;
    private boolean showDialog;
    private int uid;
    private int version;
    private int vid;

    public int getAction() {
        return this.action;
    }

    public int getChargingBinQuantity() {
        return this.chargingBinQuantity;
    }

    public int getDeviceType() {
        return this.deviceType;
    }

    public String getEdrAddr() {
        return this.edrAddr;
    }

    public int getLeftDeviceQuantity() {
        return this.leftDeviceQuantity;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter, com.jieli.jl_bt_ota.interfaces.command.IParamBase
    public byte[] getParamData() {
        byte[] bArr = new byte[18];
        byte[] bArrInt2byte2 = CHexConver.int2byte2(this.vid);
        System.arraycopy(bArrInt2byte2, 0, bArr, 0, bArrInt2byte2.length);
        int length = bArrInt2byte2.length;
        byte[] bArrInt2byte22 = CHexConver.int2byte2(this.uid);
        System.arraycopy(bArrInt2byte22, 0, bArr, length, bArrInt2byte22.length);
        int length2 = length + bArrInt2byte22.length;
        byte[] bArrInt2byte23 = CHexConver.int2byte2(this.pid);
        System.arraycopy(bArrInt2byte23, 0, bArr, length2, bArrInt2byte23.length);
        int length3 = length2 + bArrInt2byte23.length;
        bArr[length3] = (byte) ((this.deviceType << 4) | (this.version & 15));
        int i = length3 + 1;
        byte[] bArrAddressCovertToByteArray = BluetoothUtil.addressCovertToByteArray(this.edrAddr);
        if (bArrAddressCovertToByteArray != null && bArrAddressCovertToByteArray.length == 6) {
            System.arraycopy(bArrAddressCovertToByteArray, 0, bArr, i, bArrAddressCovertToByteArray.length);
            i = length3 + 7;
        }
        bArr[i] = CHexConver.intToByte(this.action);
        bArr[i + 1] = this.isLeftCharging ? (byte) -128 : (byte) 0;
        bArr[i + 2] = this.isRightCharging ? (byte) -128 : (byte) 0;
        bArr[i + 3] = this.isDeviceCharging ? (byte) -128 : (byte) 0;
        bArr[i + 4] = CHexConver.intToByte(this.seq);
        return bArr;
    }

    public int getPid() {
        return this.pid;
    }

    public int getRightDeviceQuantity() {
        return this.rightDeviceQuantity;
    }

    public int getSeq() {
        return this.seq;
    }

    public int getUid() {
        return this.uid;
    }

    public int getVersion() {
        return this.version;
    }

    public int getVid() {
        return this.vid;
    }

    public boolean isDeviceCharging() {
        return this.isDeviceCharging;
    }

    public boolean isLeftCharging() {
        return this.isLeftCharging;
    }

    public boolean isRightCharging() {
        return this.isRightCharging;
    }

    public boolean isShowDialog() {
        return this.showDialog;
    }

    public NotifyAdvInfoParam setAction(int i) {
        this.action = i;
        return this;
    }

    public NotifyAdvInfoParam setChargingBinQuantity(int i) {
        this.chargingBinQuantity = i;
        return this;
    }

    public NotifyAdvInfoParam setDeviceCharging(boolean z) {
        this.isDeviceCharging = z;
        return this;
    }

    public NotifyAdvInfoParam setDeviceType(int i) {
        this.deviceType = i;
        return this;
    }

    public NotifyAdvInfoParam setEdrAddr(String str) {
        this.edrAddr = str;
        return this;
    }

    public NotifyAdvInfoParam setLeftCharging(boolean z) {
        this.isLeftCharging = z;
        return this;
    }

    public NotifyAdvInfoParam setLeftDeviceQuantity(int i) {
        this.leftDeviceQuantity = i;
        return this;
    }

    public NotifyAdvInfoParam setPid(int i) {
        this.pid = i;
        return this;
    }

    public NotifyAdvInfoParam setRightCharging(boolean z) {
        this.isRightCharging = z;
        return this;
    }

    public NotifyAdvInfoParam setRightDeviceQuantity(int i) {
        this.rightDeviceQuantity = i;
        return this;
    }

    public NotifyAdvInfoParam setSeq(int i) {
        this.seq = i;
        return this;
    }

    public NotifyAdvInfoParam setShowDialog(boolean z) {
        this.showDialog = z;
        return this;
    }

    public NotifyAdvInfoParam setUid(int i) {
        this.uid = i;
        return this;
    }

    public NotifyAdvInfoParam setVersion(int i) {
        this.version = i;
        return this;
    }

    public NotifyAdvInfoParam setVid(int i) {
        this.vid = i;
        return this;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter
    public String toString() {
        return "NotifyAdvInfoParam{pid=" + this.pid + ", vid=" + this.vid + ", uid=" + this.uid + ", chipType=" + this.deviceType + ", version=" + this.version + ", showDialog=" + this.showDialog + ", edrAddr='" + this.edrAddr + "', seq=" + this.seq + ", action=" + this.action + ", leftDeviceQuantity=" + this.leftDeviceQuantity + ", isLeftCharging=" + this.isLeftCharging + ", rightDeviceQuantity=" + this.rightDeviceQuantity + ", isRightCharging=" + this.isRightCharging + ", chargingBinQuantity=" + this.chargingBinQuantity + ", isDeviceCharging=" + this.isDeviceCharging + "} " + super.toString();
    }
}
