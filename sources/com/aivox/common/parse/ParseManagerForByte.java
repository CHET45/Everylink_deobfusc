package com.aivox.common.parse;

import android.util.Base64;
import com.aivox.base.util.DataUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.DeviceInfo;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.UseHistory;
import com.clj.fastble.data.BleMsg;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import kotlin.UByte;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class ParseManagerForByte {
    private static ParseManagerForByte parseManager;
    int fileId;

    public static ParseManagerForByte getInstance() {
        if (parseManager == null) {
            parseManager = new ParseManagerForByte();
        }
        return parseManager;
    }

    public void parseBT(byte[] bArr) {
        LogUtil.m338i("收到bt数据：" + DataUtil.bytes2Hex(bArr) + ";" + bArr.length);
        if (bArr.length == 0) {
        }
        switch (bArr[1]) {
            case -127:
                LogUtil.m338i("result:" + ((int) bArr[3]));
                EventBus.getDefault().post(new EventBean(37));
                break;
            case -111:
                LogUtil.m338i("result:" + ((int) bArr[4]));
                EventBus.getDefault().post(new EventBean(33));
                break;
            case -95:
                parseUseHistory(bArr);
                EventBus.getDefault().post(new EventBean(36));
                break;
            case -79:
                DataHandle.getIns().setDeviceRecordState((bArr[4] & UByte.MAX_VALUE) == 1 ? 0 : 1);
                break;
            case -31:
                parseWifi(bArr);
                break;
            case 17:
                parseDeviceInfo(bArr);
                EventBus.getDefault().post(new EventBean(27));
                break;
            case 33:
                parseRecordList(bArr);
                EventBus.getDefault().post(new EventBean(28));
                break;
            case 49:
                parseRecordPlay(bArr);
                EventBus.getDefault().post(new EventBean(29));
                break;
            case 65:
                parseRecordPause(bArr);
                EventBus.getDefault().post(new EventBean(30));
                break;
            case BleMsg.MSG_SET_MTU_START /* 97 */:
                EventBus.getDefault().post(new EventBean(35));
                break;
            case 113:
                parseRecordsDelete(bArr);
                EventBus.getDefault().post(new EventBean(34));
                break;
        }
    }

    public void parseWIFI(byte[] bArr) {
        LogUtil.m338i("收到wifi数据：" + DataUtil.bytes2Hex(bArr) + ";" + bArr.length);
        if (bArr.length == 0) {
        }
        switch (bArr[1]) {
            case -127:
                LogUtil.m338i("result:" + ((int) bArr[3]));
                EventBus.getDefault().post(new EventBean(37));
                break;
            case -111:
                LogUtil.m338i("result:" + ((int) bArr[4]));
                EventBus.getDefault().post(new EventBean(33));
                break;
            case -95:
                parseUseHistory(bArr);
                EventBus.getDefault().post(new EventBean(36));
                break;
            case -80:
                parseRecordState(bArr);
                EventBus.getDefault().post(new EventBean(51));
                break;
            case -79:
                DataHandle.getIns().setDeviceRecordState((bArr[4] & UByte.MAX_VALUE) == 1 ? 0 : 1);
                break;
            case -62:
                EventBus.getDefault().post(new EventBean(52, parseThirdConnectState(bArr)));
                break;
            case -31:
                parseWifi(bArr);
                break;
            case 17:
                parseDeviceInfo(bArr);
                EventBus.getDefault().post(new EventBean(27));
                break;
            case 33:
                parseRecordList(bArr);
                EventBus.getDefault().post(new EventBean(28));
                break;
            case 49:
                parseRecordPlay(bArr);
                EventBus.getDefault().post(new EventBean(29));
                break;
            case 65:
                parseRecordPause(bArr);
                EventBus.getDefault().post(new EventBean(30));
                break;
            case 82:
                EventBus.getDefault().post(new EventBean(54, parseStopRecord(bArr)));
                break;
            case BleMsg.MSG_SET_MTU_START /* 97 */:
                EventBus.getDefault().post(new EventBean(35));
                break;
            case 113:
                parseRecordsDelete(bArr);
                EventBus.getDefault().post(new EventBean(34));
                break;
        }
    }

    private void parseRecordState(byte[] bArr) {
        Integer.parseInt(String.valueOf(bArr[3] & UByte.MAX_VALUE));
        Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 4));
        Integer.parseInt(String.valueOf(bArr[8] & UByte.MAX_VALUE));
        Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 9));
        DataHandle.getIns().setDeviceOnlineOrOffline(1);
    }

    private int parseThirdConnectState(byte[] bArr) {
        Integer.parseInt(String.valueOf(bArr[3] & UByte.MAX_VALUE));
        Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 4));
        Integer.parseInt(String.valueOf(bArr[8] & UByte.MAX_VALUE));
        Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 9));
        int i = Integer.parseInt(String.valueOf(bArr[13] & UByte.MAX_VALUE));
        LogUtil.m338i("连接状态：" + i + "  文件id：" + Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 14)));
        DataHandle.getIns().setDeviceRecordState(i != 1 ? 0 : 1);
        return i;
    }

    private void parseDeviceInfo(byte[] bArr) {
        LogUtil.m338i("-parseDeviceInfo-");
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceId(String.valueOf(bArr[2] & UByte.MAX_VALUE));
        Integer.parseInt(String.valueOf(bArr[3] & UByte.MAX_VALUE));
        deviceInfo.setUid(Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 4)));
        deviceInfo.setTotalStorage(DataUtil.getIntFromBytes(bArr, 4, 8));
        deviceInfo.setSurplusStorage(DataUtil.getIntFromBytes(bArr, 4, 12));
        deviceInfo.setDeviceName(getStrFromBytes(bArr, 20, 16));
        deviceInfo.setPower(String.valueOf(bArr[36] & UByte.MAX_VALUE));
        deviceInfo.setUniqueCode(getStrFromBytes(bArr, 40, 37));
        deviceInfo.setRecordStatus(String.valueOf(bArr[77] & UByte.MAX_VALUE));
        deviceInfo.setDuration(Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 78)));
        deviceInfo.setTranscribeType(String.valueOf(bArr[82] & UByte.MAX_VALUE));
        DataHandle.getIns().setDeviceRecordState(bArr[77] & UByte.MAX_VALUE);
        DataHandle.getIns().setDeviceInfo(deviceInfo);
    }

    private void parseRecordList(byte[] bArr) {
        int i = Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 3));
        AudioInfoBean audioInfoBean = new AudioInfoBean();
        DataHandle.getIns().setBtRecordList(audioInfoBean);
        LogUtil.m338i("数据长度data:" + Base64.encodeToString(bArr, 0));
        LogUtil.m338i("数据长度:" + i);
        LogUtil.m338i("文件名：" + audioInfoBean.getTitle());
    }

    private void parseRecordsDelete(byte[] bArr) {
        int i = Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 3));
        boolean z = (bArr[7] & UByte.MAX_VALUE) == 1;
        DataHandle.getIns().setRecordFileDeleteSuccess(z);
        ArrayList arrayList = new ArrayList();
        if (z) {
            return;
        }
        int i2 = (i - 1) / 30;
        for (int i3 = 0; i3 < i2; i3++) {
            arrayList.add(getStrFromBytes(bArr, 30, (i3 * 30) + 7));
        }
        DataHandle.getIns().getDelFailsFiles().clear();
        DataHandle.getIns().setDelFailsFiles(arrayList);
    }

    private void parseRecordPlay(byte[] bArr) {
        LogUtil.m338i("播放成功：");
    }

    private void parseRecordPause(byte[] bArr) {
        LogUtil.m338i("暂停播放成功：");
        DataHandle.getIns().setStopTime(getData(bArr, 4, 8));
        String intFromBytes = DataUtil.getIntFromBytes(bArr, 4, 8);
        LogUtil.m338i("暂停时间：" + intFromBytes + ";" + DateUtil.numberToTime(Integer.parseInt(intFromBytes)));
    }

    private int parseStartOrStopRecord(byte[] bArr) {
        Integer.parseInt(String.valueOf(bArr[3] & UByte.MAX_VALUE));
        Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 4));
        Integer.parseInt(String.valueOf(bArr[8] & UByte.MAX_VALUE));
        Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 9));
        Integer.parseInt(DataUtil.getIntFromBytes(bArr, 30, 13));
        Integer.parseInt(String.valueOf(bArr[43] & UByte.MAX_VALUE));
        Integer.parseInt(String.valueOf(bArr[44] & UByte.MAX_VALUE));
        Integer.parseInt(String.valueOf(bArr[45] & UByte.MAX_VALUE));
        Integer.parseInt(String.valueOf(bArr[46] & UByte.MAX_VALUE));
        int i = bArr[47] & UByte.MAX_VALUE;
        byte b = bArr[48];
        int i2 = Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 49));
        DataHandle.getIns().setDeviceRecordState(i == 1 ? 0 : 1);
        return i2;
    }

    private int parseStopRecord(byte[] bArr) {
        Integer.parseInt(String.valueOf(bArr[3] & UByte.MAX_VALUE));
        Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 4));
        Integer.parseInt(String.valueOf(bArr[8] & UByte.MAX_VALUE));
        Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 9));
        return Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 13));
    }

    private void parseUseHistory(byte[] bArr) {
        UseHistory useHistory = new UseHistory();
        useHistory.setUid(Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 4)));
        useHistory.setUseTime(Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 8)));
        useHistory.setStandbyTime(Integer.parseInt(DataUtil.getIntFromBytes(bArr, 4, 12)));
        useHistory.setIsLocalMeeting(bArr[16] & UByte.MAX_VALUE);
        DataHandle.getIns().setUseHistoryList(useHistory);
    }

    private void parseWifi(byte[] bArr) {
        EventBus.getDefault().post(new EventBean(41, bArr[4] & UByte.MAX_VALUE));
    }

    public static byte[] byteMerger(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public byte[] getData(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, i2, bArr2, 0, i);
        return bArr2;
    }

    public String getStrFromBytes(byte[] bArr, int i, int i2) {
        byte[] data = getData(bArr, i, i2);
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(DataUtil.asc2String(b & UByte.MAX_VALUE));
        }
        LogUtil.m338i("字符串:" + sb.toString());
        return sb.toString();
    }

    public byte[] getBytesFromStr(String str, int i) {
        LogUtil.m338i("filename:" + str + ";" + str.length());
        byte[] bArr = new byte[i];
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > i) {
            LogUtil.m336e("字符串溢出");
            return bArr;
        }
        System.arraycopy(bytes, 0, bArr, 0, bytes.length);
        return bArr;
    }

    public String getIntFromBytes(byte[] bArr, int i, int i2) {
        return String.valueOf(DataUtil.byteArrayToInt(getData(bArr, i, i2)));
    }
}
