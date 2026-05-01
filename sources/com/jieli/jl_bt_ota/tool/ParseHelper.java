package com.jieli.jl_bt_ota.tool;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import androidx.exifinterface.media.ExifInterface;
import com.jieli.jl_bt_ota.constant.Command;
import com.jieli.jl_bt_ota.interfaces.command.ICmdHandler;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.cmdHandler.RcspCmdHandler;
import com.jieli.jl_bt_ota.model.parameter.tws.NotifyAdvInfoParam;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes3.dex */
public class ParseHelper {

    /* JADX INFO: renamed from: a */
    private static final String f756a = "ParseHelper";

    /* JADX INFO: renamed from: b */
    private static final byte f757b = -2;

    /* JADX INFO: renamed from: c */
    private static final byte f758c = -36;

    /* JADX INFO: renamed from: d */
    private static final byte f759d = -70;

    /* JADX INFO: renamed from: e */
    private static final byte f760e = -17;

    /* JADX INFO: renamed from: g */
    private static byte[] f762g;

    /* JADX INFO: renamed from: f */
    private static final byte[] f761f = {-2, -36, -70};

    /* JADX INFO: renamed from: h */
    private static int f763h = 0;

    /* JADX INFO: renamed from: i */
    private static final char[] f764i = "0123456789ABCDEF".toCharArray();

    /* JADX INFO: renamed from: a */
    private static int m813a(byte[] bArr, int i, int i2) {
        int length = bArr.length;
        while (i < length) {
            if (bArr[i] == -2) {
                int i3 = length - i;
                byte[] bArr2 = f761f;
                if (i3 < bArr2.length) {
                    m815b(bArr, i, i3);
                    return -1;
                }
                int length2 = bArr2.length;
                byte[] bArr3 = new byte[length2];
                System.arraycopy(bArr, i, bArr3, 0, length2);
                if (!Arrays.equals(bArr3, bArr2)) {
                    continue;
                } else {
                    if (i3 <= bArr2.length + 4) {
                        m815b(bArr, i, i3);
                        return -1;
                    }
                    int length3 = bArr2.length + i;
                    byte[] bArr4 = new byte[2];
                    System.arraycopy(bArr, length3 + 2, bArr4, 0, 2);
                    int iBytesToInt = CHexConver.bytesToInt(bArr4[0], bArr4[1]);
                    if (iBytesToInt > i2 - 8) {
                        JL_Log.m847e(f756a, "findPacketData", CommonUtil.formatString("data length[%d] over MAX_RECEIVE_MTU[%d], cast away", Integer.valueOf(iBytesToInt), Integer.valueOf(i2)));
                    } else {
                        if (i3 <= bArr2.length + 4 + iBytesToInt) {
                            int i4 = length - length3;
                            byte[] bArr5 = new byte[i4];
                            System.arraycopy(bArr, length3, bArr5, 0, i4);
                            int iM813a = m813a(bArr5, 0, i2);
                            String str = f756a;
                            JL_Log.m849i(str, "findPacketData", "check left data, index = " + iM813a);
                            if (iM813a < bArr2.length) {
                                m815b(bArr, i, i3);
                                return -1;
                            }
                            int i5 = length3 + iM813a;
                            JL_Log.m853w(str, "findPacketData", "found headIndex = " + i5);
                            return i5;
                        }
                        if (bArr[length3 + 4 + iBytesToInt] == -17) {
                            return length3;
                        }
                    }
                    i = length3 - 1;
                }
            }
            i++;
        }
        return -1;
    }

    /* JADX INFO: renamed from: b */
    private static byte[] m816b(byte[] bArr) {
        int length = bArr.length;
        int i = f763h;
        if (i <= 0) {
            return (byte[]) bArr.clone();
        }
        byte[] bArr2 = new byte[i + length];
        System.arraycopy(f762g, 0, bArr2, 0, i);
        System.arraycopy(bArr, 0, bArr2, f763h, length);
        f763h = 0;
        return bArr2;
    }

    /* JADX INFO: renamed from: c */
    private static BasePacket m817c(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int i = 4;
        if (bArr.length < 4) {
            return null;
        }
        byte[] booleanArrayBig = CHexConver.getBooleanArrayBig(bArr[0]);
        int iByteToInt = CHexConver.byteToInt(bArr[1]);
        int iBytesToInt = CHexConver.bytesToInt(bArr, 2, 2);
        BasePacket basePacket = new BasePacket();
        int iByteToInt2 = CHexConver.byteToInt(booleanArrayBig[7]);
        int iByteToInt3 = CHexConver.byteToInt(booleanArrayBig[6]);
        basePacket.setType(iByteToInt2);
        basePacket.setHasResponse(iByteToInt3);
        basePacket.setOpCode(iByteToInt);
        basePacket.setParamLen(iBytesToInt);
        if (iBytesToInt > 0) {
            if (iByteToInt2 == 0) {
                basePacket.setStatus(CHexConver.byteToInt(bArr[4]));
                i = 5;
            }
            basePacket.setOpCodeSn(CHexConver.byteToInt(bArr[i]));
            int i2 = i + 1;
            if (iByteToInt == 1) {
                basePacket.setXmOpCode(CHexConver.byteToInt(bArr[i2]));
                i2 = i + 2;
            }
            int i3 = iBytesToInt - (i2 - 4);
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, i2, bArr2, 0, i3);
            basePacket.setParamData(bArr2);
            JL_Log.m845d(f756a, "parsePacketData", CommonUtil.formatString("packet type : %d, opCode : %d, sn :%d", Integer.valueOf(basePacket.getType()), Integer.valueOf(basePacket.getOpCode()), Integer.valueOf(basePacket.getOpCodeSn())));
        }
        return basePacket;
    }

    public static BasePacket convert2BasePacket(CommandBase commandBase, int i) {
        if (commandBase == null) {
            return null;
        }
        int i2 = 0;
        boolean z = i == 1;
        int type = commandBase.getType();
        if (z && (type == 2 || type == 3)) {
            i2 = 1;
        }
        BasePacket status = new BasePacket().setType(i).setHasResponse(i2).setOpCode(commandBase.getId()).setOpCodeSn(commandBase.getOpCodeSn()).setStatus(commandBase.getStatus());
        int length = z ? 1 : 2;
        if (commandBase.getParam() != null) {
            if (status.getOpCode() == 1) {
                status.setXmOpCode(commandBase.getParam().getXmOpCode());
                length++;
            }
            byte[] paramData = commandBase.getParam().getParamData();
            if (paramData != null && paramData.length > 0) {
                status.setParamData(paramData);
                length += paramData.length;
            }
        }
        status.setParamLen(length);
        return status;
    }

    public static CommandBase convert2Command(BasePacket basePacket, CommandBase commandBase) {
        if (basePacket == null) {
            return null;
        }
        CommandBase commandBaseM814a = m814a(basePacket, commandBase);
        return commandBaseM814a != null ? commandBaseM814a : new RcspCmdHandler().parseDataToCmd(basePacket, commandBase);
    }

    public static int convertVersionByString(String str) {
        if (!TextUtils.isEmpty(str)) {
            JL_Log.m849i(f756a, "convertVersionByString", "version = " + str);
            String[] strArrSplit = str.replace(ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "").replace("v", "").split("\\.");
            int length = strArrSplit.length;
            int[] iArr = new int[length];
            for (int i = 0; i < strArrSplit.length; i++) {
                String str2 = strArrSplit[i];
                if (TextUtils.isDigitsOnly(str2)) {
                    iArr[i] = Integer.parseInt(str2);
                }
            }
            if (length == 4) {
                byte[] booleanArray = CHexConver.getBooleanArray((byte) iArr[0]);
                byte[] booleanArray2 = CHexConver.getBooleanArray((byte) iArr[1]);
                byte[] bArr = new byte[8];
                System.arraycopy(booleanArray, 4, bArr, 0, 4);
                System.arraycopy(booleanArray2, 4, bArr, 4, 4);
                byte bM812a = (byte) m812a(bArr);
                byte[] booleanArray3 = CHexConver.getBooleanArray((byte) iArr[2]);
                byte[] booleanArray4 = CHexConver.getBooleanArray((byte) iArr[3]);
                byte[] bArr2 = new byte[8];
                System.arraycopy(booleanArray3, 4, bArr2, 0, 4);
                System.arraycopy(booleanArray4, 4, bArr2, 4, 4);
                byte bM812a2 = (byte) m812a(bArr2);
                JL_Log.m849i(f756a, "convertVersionByString", "versionCode : 0, heightValue : " + CHexConver.byte2HexStr(bArr) + ", lowValue : " + CHexConver.byte2HexStr(bArr2));
                return CHexConver.bytesToInt(bM812a, bM812a2);
            }
        }
        return 0;
    }

    public static ArrayList<BasePacket> findPacketData(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        return findPacketData(i, bArr);
    }

    public static String hexDataCovetToAddress(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr != null && bArr.length == 6) {
            for (int i = 0; i < bArr.length; i++) {
                char[] cArr = f764i;
                sb.append(cArr[(bArr[i] & UByte.MAX_VALUE) >> 4]);
                sb.append(cArr[bArr[i] & 15]);
                if (i != bArr.length - 1) {
                    sb.append(":");
                }
            }
        }
        return sb.toString();
    }

    public static byte[] packSendBasePacket(BasePacket basePacket) {
        int i;
        int i2;
        if (basePacket == null) {
            return null;
        }
        int paramLen = basePacket.getParamLen();
        int i3 = paramLen + 4;
        byte[] bArr = new byte[paramLen + 8];
        byte[] bArr2 = new byte[i3];
        byte[] bArr3 = new byte[2];
        if (basePacket.getType() == 1) {
            bArr3[0] = (byte) (bArr3[0] | 128);
        }
        if (basePacket.getHasResponse() == 1) {
            bArr3[0] = (byte) (bArr3[0] | 64);
        }
        bArr3[1] = (byte) basePacket.getOpCode();
        byte[] bArrInt2byte2 = CHexConver.int2byte2(paramLen);
        byte[] bArr4 = new byte[paramLen];
        byte[] bArr5 = {(byte) basePacket.getStatus()};
        byte[] bArr6 = {(byte) basePacket.getOpCodeSn()};
        if (basePacket.getType() == 1) {
            System.arraycopy(bArr6, 0, bArr4, 0, 1);
            if (basePacket.getOpCode() == 1) {
                System.arraycopy(new byte[]{(byte) basePacket.getXmOpCode()}, 0, bArr4, 1, 1);
                i = 2;
            } else {
                i = 1;
            }
            if (basePacket.getParamData() != null && basePacket.getParamData().length >= (i2 = paramLen - i)) {
                System.arraycopy(basePacket.getParamData(), 0, bArr4, i, i2);
                i += i2;
            }
        } else {
            System.arraycopy(bArr5, 0, bArr4, 0, 1);
            System.arraycopy(bArr6, 0, bArr4, 1, 1);
            if (basePacket.getOpCode() == 1) {
                System.arraycopy(new byte[]{(byte) basePacket.getXmOpCode()}, 0, bArr4, 2, 1);
                i = 3;
            } else {
                i = 2;
            }
            if (basePacket.getParamData() != null) {
                int i4 = paramLen - i;
                System.arraycopy(basePacket.getParamData(), 0, bArr4, i, i4);
                i += i4;
            }
        }
        if (i != paramLen) {
            JL_Log.m847e(f756a, "packSendBasePacket", "param data is error. index : " + i + ", paramLen : " + paramLen);
            return null;
        }
        System.arraycopy(bArr3, 0, bArr2, 0, 2);
        System.arraycopy(bArrInt2byte2, 0, bArr2, 2, 2);
        System.arraycopy(bArr4, 0, bArr2, 4, paramLen);
        System.arraycopy(new byte[]{-2, -36, -70}, 0, bArr, 0, 3);
        System.arraycopy(bArr2, 0, bArr, 3, i3);
        System.arraycopy(new byte[]{-17}, 0, bArr, paramLen + 7, 1);
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        byteBufferWrap.order(ByteOrder.BIG_ENDIAN);
        return byteBufferWrap.array();
    }

    public static void parseNotifyADVInfo(NotifyAdvInfoParam notifyAdvInfoParam, BasePacket basePacket) {
        byte[] paramData;
        if (basePacket == null || (paramData = basePacket.getParamData()) == null || paramData.length < 18) {
            return;
        }
        byte[] bArr = new byte[2];
        System.arraycopy(paramData, 0, bArr, 0, 2);
        int iBytesToInt = CHexConver.bytesToInt(bArr[0], bArr[1]);
        System.arraycopy(paramData, 2, bArr, 0, 2);
        int iBytesToInt2 = CHexConver.bytesToInt(bArr[0], bArr[1]);
        System.arraycopy(paramData, 4, bArr, 0, 2);
        int iBytesToInt3 = CHexConver.bytesToInt(bArr[0], bArr[1]);
        byte b = paramData[6];
        byte[] bArr2 = new byte[6];
        System.arraycopy(paramData, 7, bArr2, 0, 6);
        String strHexDataCovetToAddress = hexDataCovetToAddress(bArr2);
        int iByteToInt = CHexConver.byteToInt(paramData[13]);
        byte b2 = paramData[14];
        int i = (b2 >> 7) & 1;
        int i2 = b2 & ByteCompanionObject.MAX_VALUE;
        byte b3 = paramData[15];
        int i3 = (b3 >> 7) & 1;
        int i4 = b3 & ByteCompanionObject.MAX_VALUE;
        byte b4 = paramData[16];
        int i5 = (b4 >> 7) & 1;
        notifyAdvInfoParam.setVid(iBytesToInt).setUid(iBytesToInt2).setPid(iBytesToInt3).setDeviceType((b >> 4) & 255).setVersion(b & 15).setEdrAddr(strHexDataCovetToAddress).setAction(iByteToInt).setLeftCharging(i == 1).setLeftDeviceQuantity(i2).setRightCharging(i3 == 1).setRightDeviceQuantity(i4).setDeviceCharging(i5 == 1).setChargingBinQuantity(b4 & ByteCompanionObject.MAX_VALUE).setSeq(CHexConver.byteToInt(paramData[17]));
    }

    /* JADX WARN: Code restructure failed: missing block: B:123:0x031b, code lost:
    
        com.jieli.jl_bt_ota.util.JL_Log.m847e(com.jieli.jl_bt_ota.tool.ParseHelper.f756a, "parseTargetInfo", com.jieli.jl_bt_ota.util.CommonUtil.formatString("data length[%d] over paramDataLen[%d], cast away", java.lang.Integer.valueOf(r8), java.lang.Integer.valueOf(r3)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0332, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void parseTargetInfo(com.jieli.jl_bt_ota.model.response.TargetInfoResponse r16, com.jieli.jl_bt_ota.model.base.BasePacket r17) {
        /*
            Method dump skipped, instruction units count: 864
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.tool.ParseHelper.parseTargetInfo(com.jieli.jl_bt_ota.model.response.TargetInfoResponse, com.jieli.jl_bt_ota.model.base.BasePacket):void");
    }

    public static ArrayList<BasePacket> findPacketData(int i, byte[] bArr) {
        if (i == 0 || bArr == null || bArr.length == 0) {
            return null;
        }
        ArrayList<BasePacket> arrayList = new ArrayList<>();
        byte[] bArrM816b = m816b(bArr);
        int length = bArrM816b.length;
        JL_Log.m845d(f756a, "findPacketData", "data : " + CHexConver.byte2HexStr(bArrM816b));
        int i2 = 0;
        while (i2 < length) {
            int iM813a = m813a(bArrM816b, i2, i);
            if (iM813a < f761f.length) {
                JL_Log.m853w(f756a, "findPacketData", "not find head data : ");
                return arrayList;
            }
            JL_Log.m849i(f756a, "findPacketData", "prefixIndex = " + iM813a);
            int iBytesToInt = CHexConver.bytesToInt(bArrM816b, iM813a + 2, 2);
            int i3 = iBytesToInt + 4;
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArrM816b, iM813a, bArr2, 0, i3);
            BasePacket basePacketM817c = m817c(bArr2);
            if (basePacketM817c != null) {
                arrayList.add(basePacketM817c);
            }
            i2 = iM813a + 4 + iBytesToInt + 1;
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: b */
    private static void m815b(byte[] bArr, int i, int i2) {
        if (bArr == null || bArr.length <= 0 || i < 0 || i2 <= 0 || i + i2 > bArr.length) {
            return;
        }
        byte[] bArr2 = new byte[i2];
        f762g = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, i2);
        f763h = i2;
    }

    /* JADX INFO: renamed from: a */
    private static CommandBase m814a(BasePacket basePacket, CommandBase commandBase) {
        Map<Integer, ICmdHandler> validCommandList;
        ICmdHandler iCmdHandler;
        if (basePacket == null || (validCommandList = Command.getValidCommandList()) == null || (iCmdHandler = validCommandList.get(Integer.valueOf(basePacket.getOpCode()))) == null) {
            return null;
        }
        return iCmdHandler.parseDataToCmd(basePacket, commandBase);
    }

    /* JADX INFO: renamed from: a */
    private static int m812a(byte[] bArr) {
        if (bArr != null && bArr.length == 8) {
            StringBuilder sb = new StringBuilder();
            for (byte b : bArr) {
                sb.append(b & UByte.MAX_VALUE);
            }
            try {
                return Integer.valueOf(sb.toString(), 2).intValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
