package com.aivox.besota.bessdk.service.base;

import android.util.Log;
import com.aivox.besota.bessdk.BesSdkConstants;
import com.aivox.besota.bessdk.utils.ArrayUtil;
import com.aivox.besota.bessdk.utils.sha.Sha256;
import com.aivox.besota.bessdk.utils.sha.aes;
import com.aivox.besota.bessdk.utils.sha.besFunc;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.util.Random;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class TOTAConnectCMD {
    byte[] aes_key;
    byte[] hash_key_b;
    byte[] random_a;
    byte[] random_b;
    byte[] cashData = new byte[0];
    byte[] cashReceiveData = new byte[0];
    byte[] head = ArrayUtil.toBytes(ArrayUtil.str2HexStr("HEAD"));
    byte[] tail = ArrayUtil.toBytes(ArrayUtil.str2HexStr("TAIL"));
    byte[] dataLen = new byte[4];
    byte[] crc = new byte[4];

    public byte[] totaStartData() {
        int iNextInt = new Random().nextInt(112);
        int i = iNextInt + 16;
        this.random_b = new byte[i];
        new Random(new Random().nextInt(10000)).nextBytes(this.random_b);
        byte[] bArr = new byte[iNextInt + 21];
        byte[] bArrIntToBytesLittle = ArrayUtil.intToBytesLittle(iNextInt + 17);
        System.arraycopy(this.random_b, 0, bArr, 5, i);
        bArr[0] = 1;
        bArr[1] = 16;
        bArr[2] = bArrIntToBytesLittle[0];
        bArr[3] = bArrIntToBytesLittle[1];
        bArr[4] = (byte) i;
        return bArr;
    }

    public byte[] totaConfirm() {
        byte[] bArr = this.hash_key_b;
        byte[] bArr2 = new byte[bArr.length + 5];
        System.arraycopy(bArr, 0, bArr2, 5, bArr.length);
        bArr2[0] = 3;
        bArr2[1] = 16;
        bArr2[2] = 33;
        bArr2[3] = 0;
        bArr2[4] = 32;
        return bArr2;
    }

    public byte[] totaEncryptData(byte[] bArr) {
        return aes.encrypt(bArr, this.aes_key);
    }

    public byte[] totaDecodeData(byte[] bArr) {
        return aes.decrypt(bArr, this.aes_key);
    }

    public byte[] getTotaV2Packet(byte[] bArr) {
        return ArrayUtil.bytesSplic(ArrayUtil.byteMerger(this.head, ArrayUtil.intToBytesLittle(bArr.length)), bArr, new byte[]{(byte) ArrayUtil.crc32(bArr, 0, bArr.length), (byte) (r3 >> 8), (byte) (r3 >> 16), (byte) (r3 >> 24)}, this.tail);
    }

    public byte[] getTotaV2PacketData(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return bArr2;
        }
        byte[] bArr3 = new byte[2];
        System.arraycopy(ArrayUtil.intToBytesLittle(bArr2.length), 0, bArr3, 0, 2);
        return ArrayUtil.bytesSplic(bArr, bArr3, bArr2, new byte[0]);
    }

    public byte[][] getTotaV2LongPacket(byte[] bArr, int i) {
        int i2 = i - 16;
        byte[] bArr2 = totaEncryptData(bArr);
        int length = bArr2.length % i2 == 0 ? bArr2.length / i2 : (bArr2.length / i2) + 1;
        byte[][] bArr3 = new byte[length][];
        int i3 = 0;
        while (i3 < length) {
            int i4 = length - 1;
            int length2 = i3 == i4 ? bArr2.length - (i4 * i2) : i2;
            byte[] bArr4 = new byte[length2];
            System.arraycopy(bArr, i3 * i2, bArr4, 0, length2);
            bArr3[i3] = getTotaV2Packet(bArr4);
            i3++;
        }
        return bArr3;
    }

    public byte[] setTotaV2PacketData(byte[] bArr, boolean z) {
        Log.i("TAG", "setTotaV2PacketData: -----------" + ArrayUtil.toHex(bArr));
        if (!ArrayUtil.startsWith(bArr, this.head) || !ArrayUtil.endWith(bArr, this.tail)) {
            return new byte[]{0, 0, 0, 0, 0};
        }
        int length = this.head.length;
        byte[] bArr2 = this.dataLen;
        System.arraycopy(bArr, length, bArr2, 0, bArr2.length);
        int iBytesToIntLittle = ArrayUtil.bytesToIntLittle(this.dataLen);
        byte[] bArrByteMerger = new byte[iBytesToIntLittle];
        System.arraycopy(bArr, this.head.length + this.dataLen.length, bArrByteMerger, 0, iBytesToIntLittle);
        byte[] bArr3 = {(byte) ArrayUtil.crc32(bArrByteMerger, 0, iBytesToIntLittle), (byte) (r5 >> 8), (byte) (r5 >> 16), (byte) (r5 >> 24)};
        int length2 = this.head.length + this.dataLen.length + iBytesToIntLittle;
        byte[] bArr4 = this.crc;
        System.arraycopy(bArr, length2, bArr4, 0, bArr4.length);
        int i = 0;
        while (true) {
            byte[] bArr5 = this.crc;
            if (i < bArr5.length) {
                if (bArr3[i] != bArr5[i]) {
                    return new byte[]{1};
                }
                i++;
            } else {
                if (z) {
                    bArrByteMerger = totaDecodeData(bArrByteMerger);
                }
                byte[] bArr6 = this.cashData;
                if (bArr6.length > 0) {
                    bArrByteMerger = ArrayUtil.byteMerger(bArr6, bArrByteMerger);
                }
                byte[] bArr7 = new byte[4];
                System.arraycopy(bArrByteMerger, 2, bArr7, 0, 2);
                int iBytesToIntLittle2 = ArrayUtil.bytesToIntLittle(bArr7);
                Log.i("TAG", "cmdDataLenL:-----------" + iBytesToIntLittle2);
                if (iBytesToIntLittle2 + 4 > bArrByteMerger.length) {
                    this.cashData = bArrByteMerger;
                    return new byte[]{2};
                }
                this.cashData = new byte[0];
                return bArrByteMerger;
            }
        }
    }

    public int receiveData(byte[] bArr) {
        Log.i("TAG", "TOTA receiveData: ----------" + ArrayUtil.toHex(bArr));
        if (bArr.length < 2) {
            return 0;
        }
        byte b = bArr[0];
        if (b == 2 && (bArr[1] & UByte.MAX_VALUE) == 16) {
            int i = Integer.parseInt(ArrayUtil.toHex(new byte[]{bArr[3], bArr[2]}).replace(PunctuationConst.COMMA, ""), 16) - 1;
            byte[] bArr2 = new byte[i];
            this.random_a = bArr2;
            System.arraycopy(bArr, 5, bArr2, 0, i);
            this.hash_key_b = Sha256.getSHA256(new besFunc().func1(this.random_a, this.random_b));
            this.aes_key = new besFunc().func(this.hash_key_b);
            return BesSdkConstants.BES_TOTA_CONFIRM;
        }
        if (b == 0 && (bArr[1] & UByte.MAX_VALUE) == 16) {
            return (bArr.length > 15 && bArr[12] == 115 && bArr[13] == 117 && bArr[14] == 99) ? BesSdkConstants.BES_TOTA_SUCCESS : BesSdkConstants.BES_TOTA_ERROR;
        }
        return 0;
    }

    public void setAes_key(byte[] bArr) {
        this.aes_key = bArr;
    }

    public byte[] getAes_key() {
        return this.aes_key;
    }
}
