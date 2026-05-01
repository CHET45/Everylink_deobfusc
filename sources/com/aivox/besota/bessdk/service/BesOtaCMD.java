package com.aivox.besota.bessdk.service;

import android.content.Context;
import android.util.Log;
import com.aivox.besota.bessdk.BesSdkConstants;
import com.aivox.besota.bessdk.utils.ArrayUtil;
import com.aivox.besota.bessdk.utils.ProfileUtils;
import com.aivox.besota.bessdk.utils.SPHelper;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class BesOtaCMD {
    static byte B_80 = -128;
    static byte B_81 = -127;
    static byte B_82 = -126;
    static byte B_83 = -125;
    static byte B_84 = -124;
    static byte B_85 = -123;
    static byte B_86 = -122;
    static byte B_87 = -121;
    static byte B_88 = -120;
    static byte B_8B = -117;
    static byte B_8C = -116;
    static byte B_8D = -115;
    static byte B_8E = -114;
    static byte B_8F = -113;
    static byte B_90 = -112;
    static byte B_91 = -111;
    static byte B_92 = -110;
    static byte B_93 = -109;
    static byte B_95 = -107;
    static byte B_96 = -106;
    static byte B_97 = -105;
    static byte B_98 = -104;
    static byte B_99 = -103;
    static byte B_9A = -102;
    static byte B_9B = -101;
    static byte B_9C = -100;
    static byte B_9D = -99;
    static byte B_9E = -98;
    byte[] beforeRandomCode;
    String deviceId;
    Context mContext;
    byte[] mOtaImageData;
    int USER_FLAG = 0;
    boolean isWithoutResponse = false;
    boolean isTotaConnect = false;
    boolean useTotaV2 = false;

    /* JADX INFO: renamed from: pl */
    int f198pl = 1;

    /* JADX INFO: renamed from: ll */
    int f197ll = 0;
    byte[] magicCode = {66, 69, 83, 84};
    byte[] emptyByte = new byte[0];
    int deviceType = 0;
    byte[] deviceTypeBytes = new byte[1];
    byte[] imageSideSelection = new byte[1];
    String curVersionLeft = "";
    String curVersionRight = "";
    String versionMsg = "";
    int mMtu = 512;
    String mSwVersion = "";
    String mHwVersion = "";
    int packetPayload = 0;
    int checkBytesLength = 16384;
    int totalPacketCount = 0;
    int onePercentBytes = 0;
    int curSendLength = 0;
    int curConfirmLength = 0;
    int beforeSendLength = 0;
    int curSendPacketLength = 0;
    int crcConfirmTimes = 0;
    boolean crcConfirm = false;
    String roleSwitchRandomID = "";
    boolean isSppConnect = false;
    byte[] totaOP = {0, -112};
    boolean isMultipleBin = false;
    byte binIdentifier = 0;
    int curBinCheckLength = 0;
    int curBinLength = 0;
    int curBinCount = 0;

    public int getReturnResult(int i, int i2) {
        return i < i2 ? BesOTAConstants.OTA_CMD_RETURN : i;
    }

    public void destoryVariable() {
        this.mOtaImageData = null;
        this.packetPayload = 0;
        this.totalPacketCount = 0;
        this.onePercentBytes = 0;
        this.curSendLength = 0;
        this.beforeSendLength = 0;
        this.curSendPacketLength = 0;
        this.crcConfirmTimes = 0;
        this.crcConfirm = false;
        this.roleSwitchRandomID = "";
        this.curConfirmLength = 0;
    }

    public void setOtaUser(int i, boolean z, boolean z2, boolean z3, boolean z4, String str) {
        this.USER_FLAG = i;
        this.isSppConnect = z;
        this.isWithoutResponse = z2;
        this.isTotaConnect = z3;
        this.useTotaV2 = z4;
        this.deviceId = str;
        this.curConfirmLength = 0;
        if (z) {
            this.checkBytesLength = 32768;
        }
        if (i == 1) {
            this.f197ll = 4;
        } else {
            this.f197ll = 0;
        }
    }

    public void setImageSideSelection(int i) {
        if (i == 0) {
            this.imageSideSelection[0] = 0;
            return;
        }
        if (i == 1) {
            this.imageSideSelection[0] = 1;
        } else if (i == 2) {
            this.imageSideSelection[0] = 16;
        } else if (i == 3) {
            this.imageSideSelection[0] = 17;
        }
    }

    public byte[] getCurrentVersionCMD() {
        byte[] bArr = {B_8E};
        byte[] bArrIntToBytesLittle = this.emptyByte;
        if (this.USER_FLAG == 1) {
            bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(this.magicCode.length);
        }
        byte[] bArrBytesSplic = ArrayUtil.bytesSplic(bArr, bArrIntToBytesLittle, this.magicCode, this.emptyByte);
        return this.isTotaConnect ? convertToTotaCMD(bArrBytesSplic) : bArrBytesSplic;
    }

    public int getCurrentDeviceType() {
        return this.deviceType;
    }

    public String getCurrentVersion() {
        int i = this.deviceType;
        if (i != 2352) {
            if (i == 2353) {
                return "TWS left:" + this.curVersionLeft;
            }
            return i == 2354 ? "TWS right:" + this.curVersionRight : "";
        }
        Log.i("TAG", "getCurrentVersion: -----" + this.curVersionLeft);
        String str = "stereo:" + this.curVersionLeft + "\n" + this.versionMsg;
        this.versionMsg = "";
        return str;
    }

    public String getRoleSwitchRandomID() {
        return this.roleSwitchRandomID;
    }

    public byte[] getOtaProtocolVersionCMD(boolean z) {
        byte[] bArr = {B_99};
        byte[] bArrIntToBytesLittle = this.emptyByte;
        byte[] bArr2 = new byte[4];
        if (this.USER_FLAG == 1) {
            bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(4);
        }
        bArr2[3] = z ? (byte) 1 : (byte) 0;
        byte[] bArrBytesSplic = ArrayUtil.bytesSplic(bArr, bArrIntToBytesLittle, this.emptyByte, bArr2);
        return this.isTotaConnect ? convertToTotaCMD(bArrBytesSplic) : bArrBytesSplic;
    }

    public byte[] getSetOtaUserCMD(int i) {
        byte[] bArr = {B_97};
        byte[] bArrIntToBytesLittle = this.emptyByte;
        if (this.USER_FLAG == 1) {
            bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(1);
        }
        byte b = (byte) i;
        if (i == 6) {
            this.isMultipleBin = true;
        }
        byte[] bArrBytesSplic = ArrayUtil.bytesSplic(bArr, bArrIntToBytesLittle, this.emptyByte, new byte[]{b});
        return this.isTotaConnect ? convertToTotaCMD(bArrBytesSplic) : bArrBytesSplic;
    }

    public byte[] getSetUpgrateTypeCMD(int i) {
        byte b;
        byte[] bArr = {B_9D};
        byte[] bArrIntToBytesLittle = this.emptyByte;
        if (this.USER_FLAG == 1) {
            bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(1);
        }
        if (i == 1) {
            b = 1;
        } else {
            b = 2;
            if (i != 2) {
                b = 0;
            }
        }
        byte[] bArrBytesSplic = ArrayUtil.bytesSplic(bArr, bArrIntToBytesLittle, this.emptyByte, new byte[]{b});
        return this.isTotaConnect ? convertToTotaCMD(bArrBytesSplic) : bArrBytesSplic;
    }

    public byte[] getROLESwitchRandomIDCMD() {
        byte[] bArr = {B_9B};
        byte[] bArrIntToBytesLittle = this.emptyByte;
        if (this.USER_FLAG == 1) {
            bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(0);
        }
        byte[] bArr2 = this.emptyByte;
        byte[] bArrBytesSplic = ArrayUtil.bytesSplic(bArr, bArrIntToBytesLittle, bArr2, bArr2);
        return this.isTotaConnect ? convertToTotaCMD(bArrBytesSplic) : bArrBytesSplic;
    }

    public byte[] getSelectSideCMD() {
        byte[] bArr = {B_90};
        byte[] bArrIntToBytesLittle = this.emptyByte;
        if (this.USER_FLAG == 1) {
            bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(1);
        }
        byte[] bArrBytesSplic = ArrayUtil.bytesSplic(bArr, bArrIntToBytesLittle, this.emptyByte, this.imageSideSelection);
        return this.isTotaConnect ? convertToTotaCMD(bArrBytesSplic) : bArrBytesSplic;
    }

    public byte[] getCheckBreakPointCMD(Context context) {
        this.mContext = context;
        byte[] bArr = {B_8C};
        byte[] bArrIntToBytesLittle = this.emptyByte;
        byte[] bytes = new byte[32];
        byte[] bArr2 = {1, 2, 3, 4};
        if (this.USER_FLAG == 1) {
            bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(this.magicCode.length + 40);
        }
        byte[] bArr3 = new byte[40];
        String str = (String) SPHelper.getPreference(this.mContext, BesOTAConstants.BES_OTA_RANDOM_CODE_LEFT + this.deviceId, "");
        if (str != null && str != "") {
            bytes = ArrayUtil.toBytes(str);
        }
        this.beforeRandomCode = bytes;
        byte[] bArr4 = new byte[36];
        System.arraycopy(bytes, 0, bArr4, 0, bytes.length);
        System.arraycopy(bArr2, 0, bArr4, bytes.length, 4);
        long jCrc32 = ArrayUtil.crc32(bArr4, 0, 36);
        System.arraycopy(bytes, 0, bArr3, 0, bytes.length);
        System.arraycopy(bArr2, 0, bArr3, bytes.length, 4);
        System.arraycopy(new byte[]{(byte) jCrc32, (byte) (jCrc32 >> 8), (byte) (jCrc32 >> 16), (byte) (jCrc32 >> 24)}, 0, bArr3, bytes.length + 4, 4);
        byte[] bArrBytesSplic = ArrayUtil.bytesSplic(bArr, bArrIntToBytesLittle, this.magicCode, bArr3);
        return this.isTotaConnect ? convertToTotaCMD(bArrBytesSplic) : bArrBytesSplic;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] getStartOTAPacketCMD(java.lang.String r20) {
        /*
            Method dump skipped, instruction units count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.besota.bessdk.service.BesOtaCMD.getStartOTAPacketCMD(java.lang.String):byte[]");
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:88:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] getOTAConfigureCMD(java.lang.String r24, android.content.Context r25) {
        /*
            Method dump skipped, instruction units count: 549
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.besota.bessdk.service.BesOtaCMD.getOTAConfigureCMD(java.lang.String, android.content.Context):byte[]");
    }

    public byte[] getDataPacketCMD(String str, boolean z) {
        if (this.mOtaImageData == null) {
            readlocalImageData(str, z);
        }
        byte[] bArrIntToBytesLittle = this.emptyByte;
        if (this.crcConfirm) {
            this.crcConfirm = false;
            byte[] bArr = {B_82};
            byte[] crc32OfSegment = getCrc32OfSegment();
            int length = this.magicCode.length + crc32OfSegment.length;
            if (this.USER_FLAG == 1) {
                bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(length);
            }
            byte[] bArrBytesSplic = ArrayUtil.bytesSplic(bArr, bArrIntToBytesLittle, this.magicCode, crc32OfSegment);
            if (this.isMultipleBin) {
                bArrBytesSplic = ArrayUtil.byteMerger(new byte[]{this.binIdentifier}, bArrBytesSplic);
            }
            return this.isTotaConnect ? convertToTotaCMD(bArrBytesSplic) : bArrBytesSplic;
        }
        int length2 = this.mOtaImageData.length;
        if (this.isMultipleBin) {
            length2 = this.curBinCheckLength;
        }
        if (this.beforeSendLength == length2 || this.curSendLength == length2) {
            byte[] bArr2 = {B_88};
            if (this.USER_FLAG == 1) {
                bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(0);
            }
            byte[] bArr3 = this.emptyByte;
            byte[] bArrBytesSplic2 = ArrayUtil.bytesSplic(bArr2, bArrIntToBytesLittle, bArr3, bArr3);
            if (this.isMultipleBin) {
                bArrBytesSplic2 = ArrayUtil.byteMerger(new byte[]{this.binIdentifier}, bArrBytesSplic2);
            }
            return this.isTotaConnect ? convertToTotaCMD(bArrBytesSplic2) : bArrBytesSplic2;
        }
        byte[] bArr4 = {B_85};
        byte[] dataPacket = getDataPacket();
        int length3 = dataPacket.length;
        if (this.USER_FLAG == 1) {
            bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(length3);
        }
        byte[] bArrBytesSplic3 = ArrayUtil.bytesSplic(bArr4, bArrIntToBytesLittle, this.emptyByte, dataPacket);
        if (this.isMultipleBin) {
            bArrBytesSplic3 = ArrayUtil.byteMerger(new byte[]{this.binIdentifier}, bArrBytesSplic3);
        }
        return this.isTotaConnect ? convertToTotaCMD(bArrBytesSplic3) : bArrBytesSplic3;
    }

    public void readlocalImageData(String str, boolean z) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            int iAvailable = fileInputStream.available();
            byte[] bArr = new byte[iAvailable];
            fileInputStream.read(bArr, 0, iAvailable);
            fileInputStream.close();
            if (this.isMultipleBin) {
                byte[] bArr2 = new byte[4];
                System.arraycopy(bArr, 0, bArr2, 0, 4);
                int iBytesToIntLittle = (ArrayUtil.bytesToIntLittle(bArr2) * 8) + 4;
                int i = iAvailable - iBytesToIntLittle;
                byte[] bArr3 = new byte[i];
                System.arraycopy(bArr, iBytesToIntLittle, bArr3, 0, i);
                bArr = bArr3;
            }
            this.mOtaImageData = bArr;
            int i2 = this.deviceType == 2352 ? 1 : 0;
            LOG("TAG", "readlocalImageData: -----" + this.mMtu);
            LOG("TAG", "readlocalImageData: -----" + iAvailable);
            LOG("TAG", "readlocalImageData: -----" + z);
            if (this.USER_FLAG == -1 && this.isSppConnect) {
                i2 = 1;
            }
            int iCalculateBLESinglePacketLen = (ProfileUtils.calculateBLESinglePacketLen(iAvailable, this.mMtu, z, i2) - this.f197ll) - (this.isMultipleBin ? 1 : 0);
            this.packetPayload = iCalculateBLESinglePacketLen;
            if (this.useTotaV2) {
                this.packetPayload = (((iCalculateBLESinglePacketLen - 20) / 16) * 15) + 16;
            }
            Log.i("TAG", "readlocalImageData packetPayload: -----" + this.packetPayload);
            int i3 = this.packetPayload;
            this.totalPacketCount = ((iAvailable + i3) - 1) / i3;
            this.onePercentBytes = ProfileUtils.calculateBLEOnePercentBytes(iAvailable, z, i2);
            this.curSendPacketLength = this.packetPayload;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public byte[] getDataPacket() {
        LOG("TAG", "getDataPacket 1------" + this.curSendPacketLength);
        LOG("TAG", "getDataPacket 1curSendLength------" + this.curSendLength);
        LOG("TAG", "getDataPacket 1packetPayload------" + this.packetPayload);
        LOG("TAG", "getDataPacket 1checkBytesLength------" + this.checkBytesLength);
        int length = this.mOtaImageData.length;
        int i = this.curSendLength;
        if (this.isMultipleBin) {
            length = this.curBinLength;
            i -= this.curBinCheckLength - length;
        }
        if (this.beforeSendLength > 0 && this.crcConfirmTimes == 0) {
            LOG("TAG", "getDataPacket 1checkBytesLength------0");
            this.crcConfirmTimes = this.beforeSendLength / this.checkBytesLength;
            this.curSendPacketLength = this.packetPayload;
        } else {
            int i2 = this.packetPayload;
            if (i + i2 >= length) {
                LOG("TAG", "getDataPacket 1checkBytesLength------1");
                this.curSendPacketLength = length - i;
                this.crcConfirm = true;
            } else if (i2 + i >= this.checkBytesLength * (this.crcConfirmTimes + 1)) {
                LOG("TAG", "getDataPacket 1checkBytesLength------2");
                this.curSendPacketLength = (this.checkBytesLength * (this.crcConfirmTimes + 1)) - i;
                this.crcConfirm = true;
            } else {
                LOG("TAG", "getDataPacket 1checkBytesLength------3");
                this.curSendPacketLength = this.packetPayload;
            }
        }
        if (this.curSendLength + this.curSendPacketLength == (this.crcConfirmTimes + 1) * this.checkBytesLength) {
            this.crcConfirm = true;
        }
        LOG("TAG", "getDataPacket 2------" + this.curSendPacketLength);
        Log.i("TAG", "getDataPacket: --------" + this.curSendPacketLength);
        byte[] bArr = new byte[this.curSendPacketLength];
        LOG("BesOtaCMD", "curSendLength--" + this.curSendLength);
        LOG("BesOtaCMD", "curSendPacketLength--" + this.curSendPacketLength);
        LOG("BesOtaCMD", "mOtaImageDataLength--" + this.mOtaImageData.length);
        System.arraycopy(this.mOtaImageData, this.curSendLength, bArr, 0, this.curSendPacketLength);
        return bArr;
    }

    public byte[] getCrc32OfSegment() {
        LOG("TAG", "getCrc32OfSegment: -----" + this.curSendLength);
        LOG("TAG", "getCrc32OfSegment: -----" + (this.checkBytesLength * this.crcConfirmTimes));
        Log.i("TAG", "getCrc32OfSegment: -----" + this.curSendLength);
        Log.i("TAG", "getCrc32OfSegment: -----" + (this.checkBytesLength * this.crcConfirmTimes));
        int i = this.checkBytesLength;
        int i2 = this.crcConfirmTimes;
        int i3 = i * i2;
        int i4 = this.curSendLength;
        if (this.isMultipleBin) {
            i3 = this.curConfirmLength;
            i4 -= this.curBinCheckLength - this.curBinLength;
        }
        int i5 = i4 - (i * i2);
        byte[] bArr = new byte[i5];
        System.arraycopy(this.mOtaImageData, i3, bArr, 0, i4 - (i * i2));
        return new byte[]{(byte) ArrayUtil.crc32(bArr, 0, i5), (byte) (r1 >> 8), (byte) (r1 >> 16), (byte) (r1 >> 24)};
    }

    public byte[] getImageOverwritingConfirmationPacketCMD() {
        byte[] bArr = {B_92};
        byte[] bArrIntToBytesLittle = this.emptyByte;
        if (this.USER_FLAG == 1) {
            bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(this.magicCode.length);
        }
        byte[] bArrBytesSplic = ArrayUtil.bytesSplic(bArr, bArrIntToBytesLittle, this.magicCode, this.emptyByte);
        return this.isTotaConnect ? convertToTotaCMD(bArrBytesSplic) : bArrBytesSplic;
    }

    public void notifySuccess() {
        this.curSendLength += this.curSendPacketLength;
        LOG("TAG", "notifySuccess: ---------" + this.curSendLength);
        LOG("TAG", "notifySuccess: ---------" + this.curConfirmLength);
        Log.i("TAG", "notifySuccess:---------" + this.curSendLength);
    }

    public int receiveData(byte[] bArr, Context context, int i, boolean z) {
        int i2;
        this.mContext = context;
        byte b = bArr[0];
        if (b == B_8F) {
            int length = this.f198pl + this.f197ll + this.magicCode.length;
            byte b2 = bArr[length];
            this.deviceTypeBytes = new byte[]{b2};
            if (b2 == 0) {
                Log.i("TAG", "decviceType: ---STEREO");
                this.deviceType = BesOTAConstants.DEVICE_TYPE_STEREO;
                if (this.USER_FLAG == 1) {
                    byte[] bArr2 = new byte[4];
                    System.arraycopy(bArr, this.f198pl, bArr2, 0, this.f197ll);
                    if (ArrayUtil.bytesToIntLittle(bArr2) > 15) {
                        int i3 = length + 9;
                        byte[] bArr3 = new byte[bArr.length - i3];
                        System.arraycopy(bArr, i3, bArr3, 0, bArr.length - i3);
                        this.versionMsg = ArrayUtil.toASCII(bArr3).replace("-", " ");
                    }
                }
            } else if (b2 == 1) {
                Log.i("TAG", "decviceType: ---TWS_CONNECT_LEFT");
                this.deviceType = BesOTAConstants.DEVICE_TYPE_TWS_CONNECT_LEFT;
            } else if (b2 == 2) {
                Log.i("TAG", "decviceType: ---TWS_CONNECT_RIGHT");
                this.deviceType = BesOTAConstants.DEVICE_TYPE_TWS_CONNECT_RIGHT;
            }
            byte[] bArr4 = new byte[4];
            System.arraycopy(bArr, length + 1, bArr4, 0, 4);
            this.curVersionLeft = ArrayUtil.toHex(bArr4).replace(PunctuationConst.COMMA, ".");
            byte[] bArr5 = new byte[4];
            System.arraycopy(bArr, length + 5, bArr5, 0, 4);
            this.curVersionRight = ArrayUtil.toHex(bArr5).replace(PunctuationConst.COMMA, ".");
            return getReturnResult(BesOTAConstants.OTA_CMD_GET_HW_INFO, i);
        }
        if (b == B_91) {
            destoryVariable();
            if (bArr[this.f198pl + this.f197ll] == 1) {
                return getReturnResult(BesOTAConstants.OTA_CMD_SELECT_SIDE_OK, i);
            }
            return getReturnResult(BesOTAConstants.OTA_CMD_SELECT_SIDE_ERROR, i);
        }
        if (b == B_8D) {
            int i4 = this.f198pl + this.f197ll;
            byte[] bArr6 = new byte[4];
            System.arraycopy(bArr, i4, bArr6, 0, 4);
            int iBytesToIntLittle = ArrayUtil.bytesToIntLittle(bArr6);
            this.curSendLength = iBytesToIntLittle;
            if (iBytesToIntLittle > 0) {
                this.beforeSendLength = iBytesToIntLittle;
                this.curConfirmLength = iBytesToIntLittle;
                this.mMtu = ((Integer) SPHelper.getPreference(this.mContext, BesOTAConstants.BES_OTA_CURRENT_MTU, Integer.valueOf(this.mMtu))).intValue();
                return getReturnResult(BesOTAConstants.OTA_CMD_BREAKPOINT_CHECK, i);
            }
            byte[] bArr7 = new byte[32];
            System.arraycopy(bArr, i4 + 4, bArr7, 0, 32);
            SPHelper.putPreference(this.mContext, BesOTAConstants.BES_OTA_RANDOM_CODE_LEFT + this.deviceId, ArrayUtil.toHex(bArr7));
            return getReturnResult(BesOTAConstants.OTA_CMD_BREAKPOINT_CHECK_80, i);
        }
        if (b == B_81) {
            int length2 = this.f198pl + this.f197ll + this.magicCode.length;
            byte[] bArr8 = new byte[2];
            System.arraycopy(bArr, length2, bArr8, 0, 2);
            this.mSwVersion = ArrayUtil.toHex(bArr8).replace(PunctuationConst.COMMA, ".");
            byte[] bArr9 = new byte[2];
            System.arraycopy(bArr, length2 + 2, bArr9, 0, 2);
            this.mHwVersion = ArrayUtil.toHex(bArr9).replace(PunctuationConst.COMMA, ".");
            LOG("TAG", "receiveData: --" + this.mSwVersion + "--" + this.mHwVersion);
            byte[] bArr10 = new byte[4];
            System.arraycopy(bArr, length2 + 4, bArr10, 0, 2);
            if (this.curSendLength <= 0 || !this.isMultipleBin) {
                this.mMtu = ArrayUtil.bytesToIntLittle(bArr10);
                LOG("TAG", "receiveData mMtu: --" + this.mMtu);
                SPHelper.putPreference(this.mContext, BesOTAConstants.BES_OTA_CURRENT_MTU, Integer.valueOf(this.mMtu));
            }
            return getReturnResult(BesOTAConstants.OTA_CMD_BREAKPOINT_CHECK, i);
        }
        if (b == B_87) {
            return bArr[this.f198pl + this.f197ll] == 1 ? BesOTAConstants.OTA_CMD_SEND_CONFIGURE_OK : getReturnResult(BesOTAConstants.OTA_CMD_SEND_CONFIGURE_ERROR, i);
        }
        if (b == B_83) {
            if (bArr[this.f198pl + this.f197ll] != 1) {
                return BesOTAConstants.OTA_CMD_CRC_CHECK_PACKAGE_ERROR;
            }
            this.crcConfirmTimes++;
            int i5 = this.curSendLength;
            this.curConfirmLength = i5;
            this.crcConfirm = false;
            byte[] bArr11 = this.mOtaImageData;
            int length3 = bArr11.length;
            if (this.isMultipleBin) {
                length3 = this.curBinCheckLength;
            }
            return (bArr11 == null || i5 != length3) ? BesOTAConstants.OTA_CMD_CRC_CHECK_PACKAGE_OK : BesOTAConstants.OTA_CMD_WHOLE_CRC_CHECK;
        }
        if (b == B_84) {
            return bArr[this.f198pl + this.f197ll] == 1 ? (!this.isMultipleBin || (i2 = this.curSendLength) == this.mOtaImageData.length) ? BesOTAConstants.OTA_CMD_WHOLE_CRC_CHECK_OK : i2 > 0 ? BesOTAConstants.OTA_CMD_BREAKPOINT_CHECK_80_NO_CHANGE : BesOTAConstants.OTA_CMD_BREAKPOINT_CHECK_80 : BesOTAConstants.OTA_CMD_WHOLE_CRC_CHECK_ERROR;
        }
        if (b == B_93) {
            if (bArr[this.f198pl + this.f197ll] == 1) {
                return getReturnResult(BesOTAConstants.OTA_CMD_IMAGE_OVER_CONFIRM, i);
            }
            return getReturnResult(BesOTAConstants.OTA_CMD_IMAGE_OVER_CONFIRM_ERROR, i);
        }
        if (b == B_95) {
            return BesOTAConstants.OTA_CMD_DISCONNECT;
        }
        if (b == B_96) {
            return BesOTAConstants.OTA_CMD_ROLESWITCH_COMPLETE;
        }
        if (b == B_9A) {
            this.USER_FLAG = 1;
            setOtaUser(1, this.isSppConnect, this.isWithoutResponse, this.isTotaConnect, this.useTotaV2, this.deviceId);
            return BesOTAConstants.OTA_CMD_GET_PROTOCOL_VERSION;
        }
        if (b == B_98) {
            return bArr[this.f198pl + this.f197ll] == 1 ? BesOTAConstants.OTA_CMD_SET_OAT_USER_OK : BesOTAConstants.OTA_CMD_SET_OAT_USER_ERROR;
        }
        if (b == B_9E) {
            byte b3 = bArr[this.f198pl + this.f197ll];
            if (b3 == 1) {
                return BesOTAConstants.OTA_CMD_SET_UPGRADE_TYPE_NORMAL;
            }
            if (b3 == 2) {
                return BesOTAConstants.OTA_CMD_SET_UPGRADE_TYPE_FAST;
            }
        } else {
            if (b == B_9C) {
                String strReplace = ArrayUtil.toHex(bArr).replace(PunctuationConst.COMMA, "");
                this.roleSwitchRandomID = strReplace.substring(strReplace.length() - 4);
                return BesOTAConstants.OTA_CMD_ROLESWITCH_GET_RANDOMID;
            }
            if (b == B_8B) {
                return z ? BesOTAConstants.OTA_CMD_SEND_OTA_DATA : BesOTAConstants.OTA_CMD_RETURN;
            }
            Log.i("TAG", "receiveData: error");
        }
        return 0;
    }

    public void crcConfirmError() {
        LOG("TAG", "crcConfirmError---" + this.curSendLength);
        LOG("TAG", "crcConfirmError---" + this.curConfirmLength);
        this.curSendLength = this.curConfirmLength;
        LOG("TAG", "crcConfirmError---" + this.curSendLength);
    }

    public String besOtaProgress() {
        byte[] bArr = this.mOtaImageData;
        if (bArr == null) {
            return "";
        }
        int length = bArr.length;
        if (length == 0) {
            return "0.00";
        }
        double d = this.curSendLength * 100;
        double d2 = length;
        if (d / 100.0d > d2) {
            d = d2;
        }
        return ArrayUtil.div(d, d2, 2) + "";
    }

    public void setCrcConfirmState(boolean z) {
        this.crcConfirm = z;
    }

    public byte[] convertToTotaCMD(byte[] bArr) {
        byte[] bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(bArr.length);
        byte[] bArrBytesSplic = ArrayUtil.bytesSplic(this.totaOP, new byte[]{bArrIntToBytesLittle[0]}, new byte[]{bArrIntToBytesLittle[1]}, bArr);
        Log.i("TAG", "convertToTotaCMD: -------" + ArrayUtil.toHex(bArrBytesSplic));
        return bArrBytesSplic;
    }

    public void LOG(String str, String str2) {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        String str3 = (String) SPHelper.getPreference(context, BesSdkConstants.BES_SAVE_LOG_NAME, "");
        if (((Boolean) SPHelper.getPreference(this.mContext, BesSdkConstants.BES_SAVE_LOG_KEY, true)).booleanValue()) {
            if (str3.equals(BesSdkConstants.BES_SAVE_LOG_OTA)) {
                Log.i(str, str2 + str3);
            } else if (str3.length() > 0) {
                Log.i(str, str2 + str3);
            }
        }
    }
}
