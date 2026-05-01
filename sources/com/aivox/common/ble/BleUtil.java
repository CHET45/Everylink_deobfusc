package com.aivox.common.ble;

import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.common.GlassesCmd;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.Crc16Util;
import com.aivox.base.util.LogUtil;
import com.aivox.common.ble.model.BleAdvertisedData;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.UUID;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class BleUtil {
    private static final String TAG = "BleUtil";
    private static byte sequence;

    public static BleAdvertisedData parseAdertisedData(byte[] bArr) {
        byte b;
        ArrayList arrayList = new ArrayList();
        String str = null;
        if (bArr == null) {
            return new BleAdvertisedData(arrayList, null);
        }
        ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
        while (byteBufferOrder.remaining() > 2 && (b = byteBufferOrder.get()) != 0) {
            byte b2 = byteBufferOrder.get();
            if (b2 == 2 || b2 == 3) {
                while (b >= 2) {
                    arrayList.add(UUID.fromString(String.format("%08x-0000-1000-8000-00805f9b34fb", Short.valueOf(byteBufferOrder.getShort()))));
                    b = (byte) (b - 2);
                }
            } else if (b2 == 6 || b2 == 7) {
                while (b >= 16) {
                    arrayList.add(new UUID(byteBufferOrder.getLong(), byteBufferOrder.getLong()));
                    b = (byte) (b - 16);
                }
            } else if (b2 == 9) {
                byte[] bArr2 = new byte[b - 1];
                byteBufferOrder.get(bArr2);
                try {
                    str = new String(bArr2, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    BaseAppUtils.printErrorMsg(e);
                }
            } else {
                byteBufferOrder.position((byteBufferOrder.position() + b) - 1);
            }
        }
        return new BleAdvertisedData(arrayList, str);
    }

    public static byte[] getCmdBytes(GlassesCmd glassesCmd, boolean z, Object... objArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Byte.valueOf((byte) (glassesCmd.getCmd() & 255)));
        arrayList.add(Byte.valueOf((byte) ((glassesCmd.getCmd() >> 8) & 255)));
        if (z) {
            arrayList.add((byte) 2);
        } else {
            arrayList.add((byte) 1);
        }
        arrayList.add(Byte.valueOf(sequence));
        sequence = (byte) (sequence + 1);
        switch (C09651.$SwitchMap$com$aivox$base$common$GlassesCmd[glassesCmd.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                Object obj = objArr[0];
                if (obj instanceof Number) {
                    arrayList.add(Byte.valueOf(((Number) obj).byteValue()));
                }
                break;
            case 12:
                arrayList.add((byte) 1);
                break;
            case 13:
                Object obj2 = objArr[0];
                if (obj2 instanceof Number) {
                    arrayList.add(Byte.valueOf(((Number) obj2).byteValue()));
                }
                Object obj3 = objArr[1];
                if (obj3 instanceof Number) {
                    arrayList.add(Byte.valueOf(((Number) obj3).byteValue()));
                }
                break;
            case 14:
                arrayList.add((byte) 1);
                break;
            case 15:
                Object obj4 = objArr[0];
                if (obj4 instanceof String) {
                    for (byte b : ((String) obj4).getBytes(StandardCharsets.US_ASCII)) {
                        arrayList.add(Byte.valueOf(b));
                    }
                } else {
                    LogUtil.m337e(TAG, "SET_WIFI_SSID param is null");
                    return new byte[0];
                }
                break;
            case 16:
                Object obj5 = objArr[0];
                if (obj5 instanceof String) {
                    for (byte b2 : ((String) obj5).getBytes(StandardCharsets.US_ASCII)) {
                        arrayList.add(Byte.valueOf(b2));
                    }
                } else {
                    LogUtil.m337e(TAG, "SET_WIFI_PASSWORD param is null");
                    return new byte[0];
                }
                break;
            case 17:
                byte[] bArrTimeToBytes = timeToBytes();
                for (int i = 0; i <= 3; i++) {
                    arrayList.add(Byte.valueOf(bArrTimeToBytes[i]));
                }
                break;
            case 18:
                Object obj6 = objArr[0];
                if (obj6 instanceof String) {
                    for (byte b3 : ((String) obj6).getBytes(StandardCharsets.US_ASCII)) {
                        arrayList.add(Byte.valueOf(b3));
                    }
                } else {
                    LogUtil.m337e(TAG, "SET_WIFI_SSID param is null");
                    return new byte[0];
                }
                break;
            case 19:
                int iIntValue = ((Integer) objArr[0]).intValue();
                if (BaseGlobalConfig.isDebug()) {
                    LogUtil.m337e(TAG, "BLE 音频控制(0A02)：" + iIntValue);
                }
                arrayList.add(Byte.valueOf((byte) iIntValue));
                break;
            case 21:
                arrayList.add(Byte.valueOf(((Byte) objArr[0]).byteValue()));
                arrayList.add(Byte.valueOf(((Byte) objArr[1]).byteValue()));
                arrayList.add(Byte.valueOf(((Byte) objArr[2]).byteValue()));
                break;
            case 22:
                arrayList.add(Byte.valueOf(((Byte) objArr[0]).byteValue()));
                break;
            case 24:
                Object obj7 = objArr[0];
                if (obj7 instanceof Integer) {
                    arrayList.add(Byte.valueOf((byte) (((Integer) obj7).intValue() & 255)));
                    arrayList.add(Byte.valueOf((byte) ((((Integer) objArr[0]).intValue() >> 8) & 255)));
                }
                arrayList.add(Byte.valueOf(((Byte) objArr[1]).byteValue()));
                break;
            case 25:
                arrayList.add(Byte.valueOf(((Byte) objArr[0]).byteValue()));
                break;
        }
        int iMax = Math.max(arrayList.size() - 4, 0);
        if (iMax > 0) {
            arrayList.add(4, Byte.valueOf((byte) (iMax & 255)));
            arrayList.add(5, Byte.valueOf((byte) ((iMax >> 8) & 255)));
        } else {
            arrayList.add((byte) 0);
            arrayList.add((byte) 0);
        }
        Byte[] bArr = (Byte[]) arrayList.toArray(new Byte[0]);
        arrayList.add(0, (byte) -91);
        int length = bArr.length;
        arrayList.add(1, Byte.valueOf((byte) (length & 255)));
        arrayList.add(2, Byte.valueOf((byte) ((length >> 8) & 255)));
        int iCalculate = Crc16Util.calculate((Byte[]) arrayList.toArray(new Byte[0]));
        arrayList.add(Byte.valueOf((byte) (iCalculate & 255)));
        arrayList.add(Byte.valueOf((byte) ((iCalculate >> 8) & 255)));
        byte[] bArr2 = new byte[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            bArr2[i2] = ((Byte) arrayList.get(i2)).byteValue();
        }
        return bArr2;
    }

    /* JADX INFO: renamed from: com.aivox.common.ble.BleUtil$1 */
    static /* synthetic */ class C09651 {
        static final /* synthetic */ int[] $SwitchMap$com$aivox$base$common$GlassesCmd;

        static {
            int[] iArr = new int[GlassesCmd.values().length];
            $SwitchMap$com$aivox$base$common$GlassesCmd = iArr;
            try {
                iArr[GlassesCmd.SWITCH_NOISE_CONTROL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.SET_WEARING_DETECTION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.SET_GAME_MODE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.SET_EQ.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.APP_AI_MODE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.DEVICE_AI_MODE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.AI_MODE_VOICE_START.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.AI_MODE_VOICE_PROMPT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.VIDEO_PREVIEW_CONTROL.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.WIFI_AP_CONTROL.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.SET_ANTI_SHAKE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.SET_BUTTON_FUNCTION.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.FIND_DEVICE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.AI_MODE_EVENT.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.SET_WIFI_SSID.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.SET_WIFI_PASSWORD.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.SET_TIME.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.SET_BINDING_CODE.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.BLE_AUDIO_CONTROL.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.BLE_AUDIO_DATA.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.START_OTA.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.START_GET_FILE.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.FILE_DATA_UPLOAD.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.RETRIEVE_FILE_DATA.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.SET_DEVICE_CONTROL.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
        }
    }

    public static boolean validatePacket(byte[] bArr) {
        if (bArr == null || bArr.length < 5 || bArr[0] != -91) {
            return false;
        }
        if (bArr.length != (((bArr[2] & UByte.MAX_VALUE) << 8) | (bArr[1] & UByte.MAX_VALUE)) + 5) {
            return false;
        }
        Byte[] bArr2 = new Byte[bArr.length - 2];
        for (int i = 0; i < bArr.length - 2; i++) {
            bArr2[i] = Byte.valueOf(bArr[i]);
        }
        int iCalculate = Crc16Util.calculate(bArr2);
        boolean z = ((byte) (iCalculate & 255)) == bArr[bArr.length - 2];
        if (z) {
            return ((byte) ((iCalculate >> 8) & 255)) == bArr[bArr.length - 1];
        }
        return z;
    }

    private static byte[] timeToBytes() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt((int) ((jCurrentTimeMillis + ((long) TimeZone.getDefault().getOffset(jCurrentTimeMillis))) / 1000)).array();
    }
}
