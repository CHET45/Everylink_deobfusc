package com.aivox.besota.bessdk.utils;

import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;

/* JADX INFO: loaded from: classes.dex */
public class ProfileUtils {
    private static final int BLE_SINGLE_PACKE_MAC_BYTES = 256;
    private static final int BLE_SINGLE_PACKE_MAC_BYTES_MAX_STEREO = 660;
    private static final int BLE_SINGLE_PACKE_MAC_BYTES_MAX_TWS = 512;
    private static final int SPP_SINGLE_CRC_BYTE_COUNTS = 4096;
    private static final int SPP_SINGLE_PACKE_BYTES = 256;

    public static int calculateBLESinglePacketLen(int i, int i2, boolean z, int i3) {
        if (i != 0 && i < i2 - 1) {
            return i;
        }
        if (z) {
            return i2 > 509 ? TypedValues.PositionType.TYPE_CURVE_FIT : i2 - 1;
        }
        if (i3 == 1) {
            int i4 = i2 - 1;
            return i4 > BLE_SINGLE_PACKE_MAC_BYTES_MAX_STEREO ? BLE_SINGLE_PACKE_MAC_BYTES_MAX_STEREO : i4;
        }
        int i5 = i2 - 1;
        if (i5 > 512) {
            return 512;
        }
        return i5;
    }

    public static int calculateSppSinglePacketLen(int i) {
        if (i >= 256) {
            i = 256;
        }
        Log.e("BES", "calculateSppSinglePacketLen = " + i);
        return i;
    }

    public static int calculateSppTotalPacketCount(int i) {
        return (i + 255) / 256;
    }

    public static int calculateSppTotalCrcCount(int i) {
        return (i + 4095) / 4096;
    }

    public static int calculateBLETotalPacketCount(int i, int i2, boolean z, int i3) {
        int iCalculateBLESinglePacketLen = ((calculateBLESinglePacketLen(i, i2, z, i3) + i) - 1) / calculateBLESinglePacketLen(i, i2, z, i3);
        Log.e("BES", "imageSize = " + i + " mtu = " + i2 + " totalCount = " + iCalculateBLESinglePacketLen);
        return iCalculateBLESinglePacketLen;
    }

    public static int calculateBLEOnePercentBytes(int i, boolean z, int i2) {
        int i3 = i / 100;
        if (i < 256) {
            i3 = i;
        } else {
            int i4 = i3 < 256 ? 256 - i3 : 256 - (i3 % 256);
            if (i4 != 0) {
                i3 += i4;
            }
        }
        if (i3 < 4096) {
            i3 = 4096;
        }
        Log.e("BES", "imageSize = " + i + " onepercentBytes = " + i3 + " crc total Count " + (((i + i3) - 1) / i3));
        return i3;
    }
}
