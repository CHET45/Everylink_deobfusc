package com.aivox.common.parse;

import com.aivox.base.util.DataUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.socket.DeviceProtocol;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SendManager {
    private static String enterpriseid;
    private static String identity;
    private static SendManager sendManager;

    public static SendManager getInstance() {
        if (sendManager == null) {
            sendManager = new SendManager();
        }
        return sendManager;
    }

    public void sendSppData(String str) {
        CommonServiceUtils.getInstance().sendData(str);
    }

    public byte[] cmdDeviceInfo(byte b, String str) {
        byte[] bArr = {DeviceProtocol.START, 16, b, 4, 0, 0, 0, 0};
        Arrays.fill(bArr, (byte) 0);
        LogUtil.m338i("准备发送数据：" + DataUtil.bytes2Hex(bArr));
        return bArr;
    }

    public byte[] cmdRecordList(byte b, String str) {
        byte[] bArr = {DeviceProtocol.START, 32, b, 4, 0, 0, 0, 0};
        Arrays.fill(bArr, (byte) 0);
        LogUtil.m338i("准备发送数据：" + DataUtil.bytes2Hex(bArr));
        return bArr;
    }

    public byte[] cmdRecordPlay(byte b, String str, String str2, byte[] bArr) {
        byte[] bArr2 = new byte[42];
        Arrays.fill(bArr2, (byte) 0);
        bArr2[0] = DeviceProtocol.START;
        bArr2[1] = 48;
        bArr2[2] = b;
        bArr2[3] = 34;
        byte[] bytesFromStr = ParseManagerForByte.getInstance().getBytesFromStr(str2, 30);
        for (int i = 0; i < bytesFromStr.length; i++) {
            bArr2[i + 8] = bytesFromStr[i];
        }
        LogUtil.m338i("开始播放时间：" + DataUtil.getIntFromBytes(bArr, 4, 0));
        bArr2[38] = bArr[0];
        bArr2[39] = bArr[1];
        bArr2[40] = bArr[2];
        bArr2[41] = bArr[3];
        LogUtil.m338i("准备发送数据：" + DataUtil.bytes2Hex(bArr2));
        return bArr2;
    }

    public byte[] cmdRecordPause(byte b, String str, String str2) {
        byte[] bArr = new byte[38];
        Arrays.fill(bArr, (byte) 0);
        bArr[0] = DeviceProtocol.START;
        bArr[1] = 64;
        bArr[2] = b;
        bArr[3] = 30;
        byte[] bytesFromStr = ParseManagerForByte.getInstance().getBytesFromStr(str2, 30);
        for (int i = 0; i < bytesFromStr.length; i++) {
            bArr[i + 8] = bytesFromStr[i];
        }
        LogUtil.m338i("准备发送数据：" + DataUtil.bytes2Hex(bArr));
        return bArr;
    }

    public byte[] cmdRecordStartOrStop(byte b, String str, boolean z) {
        byte[] bArr = {DeviceProtocol.START, 80, b, 5, (byte) (!z ? 1 : 0), 0, 0, 0, 0};
        Arrays.fill(bArr, (byte) 0);
        LogUtil.m338i("准备发送数据：" + DataUtil.bytes2Hex(bArr));
        return bArr;
    }

    public byte[] cmdUpload(byte b, int i, String str) {
        byte[] bArr = {DeviceProtocol.START, 96, b, 5, bArrIntToByteArray[0], bArrIntToByteArray[1], bArrIntToByteArray[2], bArrIntToByteArray[3], (byte) Integer.parseInt(str)};
        Arrays.fill(bArr, (byte) 0);
        byte[] bArrIntToByteArray = DataUtil.intToByteArray(i);
        LogUtil.m338i("准备发送数据：" + DataUtil.bytes2Hex(bArr));
        return bArr;
    }

    public byte[] cmdDelete(byte b, List<String> list) {
        byte[] bArr = new byte[1024];
        Arrays.fill(bArr, (byte) 0);
        bArr[0] = DeviceProtocol.START;
        bArr[1] = 112;
        bArr[2] = b;
        int size = list.size() * 30;
        byte[] bArr2 = new byte[size];
        byte[] bArrIntToByteArray = DataUtil.intToByteArray(size);
        bArr[3] = bArrIntToByteArray[0];
        bArr[4] = bArrIntToByteArray[1];
        bArr[5] = bArrIntToByteArray[2];
        bArr[6] = bArrIntToByteArray[3];
        for (int i = 0; i < list.size(); i++) {
            byte[] bytesFromStr = ParseManagerForByte.getInstance().getBytesFromStr(list.get(i), 30);
            for (int i2 = 0; i2 < bytesFromStr.length; i2++) {
                bArr[i2 + 7 + (i * 30)] = bytesFromStr[i2];
            }
        }
        LogUtil.m338i("准备发送数据：" + Arrays.toString(ParseManagerForByte.getInstance().getData(bArr, size + 7, 0)));
        return bArr;
    }

    public byte[] cmdVolume(byte b, int i) {
        byte[] bArr = {DeviceProtocol.START, -128, b, 1, (byte) i};
        Arrays.fill(bArr, (byte) 0);
        LogUtil.m338i("准备发送数据：" + DataUtil.bytes2Hex(bArr));
        return bArr;
    }

    public byte[] cmdRTC(byte b, int i) {
        byte[] bArr = {DeviceProtocol.START, -112, b, 4, bArrIntToByteArray[0], bArrIntToByteArray[1], bArrIntToByteArray[2], bArrIntToByteArray[3]};
        Arrays.fill(bArr, (byte) 0);
        LogUtil.m338i("timestamp:" + i + ";" + DateUtil.getDateFromTimestamp(((long) i) * 1000, DateUtil.YYYY_MM_DD_HH_MM_SS));
        byte[] bArrIntToByteArray = DataUtil.intToByteArray(i);
        LogUtil.m338i("准备发送数据：" + DataUtil.bytes2Hex(bArr));
        return bArr;
    }

    public byte[] cmdUseHistroy(byte b, int i, int i2, int i3) {
        byte[] bArr = {DeviceProtocol.START, -96, b, 1, bArrIntToByteArray[0], bArrIntToByteArray[1], bArrIntToByteArray[2], bArrIntToByteArray[3], bArrIntToByteArray[0], bArrIntToByteArray[1], bArrIntToByteArray[2], bArrIntToByteArray[3], bArrIntToByteArray[0], bArrIntToByteArray[1], bArrIntToByteArray[2], bArrIntToByteArray[3]};
        Arrays.fill(bArr, (byte) 0);
        byte[] bArrIntToByteArray = DataUtil.intToByteArray(i);
        byte[] bArrIntToByteArray2 = DataUtil.intToByteArray(i2);
        byte[] bArrIntToByteArray3 = DataUtil.intToByteArray(i3);
        LogUtil.m338i("准备发送数据：" + DataUtil.bytes2Hex(bArr));
        return bArr;
    }

    public byte[] cmdWifi(byte b, String str, String str2, int i) {
        byte[] bArr = new byte[73];
        Arrays.fill(bArr, (byte) 0);
        bArr[0] = DeviceProtocol.START;
        bArr[1] = -32;
        bArr[2] = b;
        bArr[3] = 69;
        byte[] bytesFromStr = ParseManagerForByte.getInstance().getBytesFromStr(str, 30);
        byte[] bytesFromStr2 = ParseManagerForByte.getInstance().getBytesFromStr(str2, 30);
        if (str.length() > 0) {
            System.arraycopy(bytesFromStr, 0, bArr, 4, 30);
        }
        if (str2.length() > 0) {
            System.arraycopy(bytesFromStr2, 0, bArr, 34, 30);
        }
        byte[] bArrIntToByteArray = DataUtil.intToByteArray(i);
        bArr[64] = bArrIntToByteArray[0];
        bArr[65] = bArrIntToByteArray[1];
        bArr[66] = bArrIntToByteArray[2];
        bArr[67] = bArrIntToByteArray[3];
        bArr[68] = (byte) Integer.parseInt(identity);
        byte[] bArrIntToByteArray2 = DataUtil.intToByteArray(Integer.parseInt(enterpriseid));
        bArr[69] = bArrIntToByteArray2[0];
        bArr[70] = bArrIntToByteArray2[1];
        bArr[71] = bArrIntToByteArray2[2];
        bArr[72] = bArrIntToByteArray2[3];
        LogUtil.m338i("cmdWifi:" + DataUtil.bytes2Hex(bArr));
        return bArr;
    }

    public byte[] wifiConnectSuccess(int i) {
        byte[] bArr = new byte[49];
        Arrays.fill(bArr, (byte) 0);
        bArr[0] = DeviceProtocol.START;
        bArr[1] = -80;
        bArr[2] = 1;
        bArr[3] = 45;
        bArr[4] = 1;
        System.arraycopy(DataUtil.getBytesFromStr("12345", 40), 0, bArr, 5, 40);
        byte[] bArrIntToByteArray = DataUtil.intToByteArray(i);
        bArr[45] = bArrIntToByteArray[0];
        bArr[46] = bArrIntToByteArray[1];
        bArr[47] = bArrIntToByteArray[2];
        bArr[48] = bArrIntToByteArray[3];
        LogUtil.m338i("Device2Cloud:" + DataUtil.bytes2Hex(bArr));
        return bArr;
    }
}
