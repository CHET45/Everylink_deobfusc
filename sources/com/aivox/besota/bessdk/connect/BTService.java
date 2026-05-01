package com.aivox.besota.bessdk.connect;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import com.aivox.besota.bessdk.BesSdkConstants;
import com.aivox.besota.bessdk.service.base.BesServiceConfig;
import com.aivox.besota.sdk.connect.DeviceConnector;
import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.utils.DeviceProtocol;

/* JADX INFO: loaded from: classes.dex */
public class BTService {
    protected static Object mOtaLock = new Object();
    public static byte[] curSendData = new byte[0];

    public static boolean sendData(Context context, DeviceProtocol deviceProtocol, byte[] bArr, BluetoothDevice bluetoothDevice) {
        if (context == null) {
            return false;
        }
        curSendData = bArr;
        synchronized (mOtaLock) {
            if (deviceProtocol != DeviceProtocol.PROTOCOL_SPP) {
                return false;
            }
            return SppConnector.getsConnector(context, null).write(bArr, bluetoothDevice);
        }
    }

    public static boolean sendDataWithoutResponse(Context context, DeviceProtocol deviceProtocol, byte[] bArr, BluetoothDevice bluetoothDevice) {
        if (context == null) {
            return false;
        }
        curSendData = bArr;
        synchronized (mOtaLock) {
            if (deviceProtocol != DeviceProtocol.PROTOCOL_SPP) {
                return false;
            }
            return SppConnector.getsConnector(context, null).write(bArr, bluetoothDevice);
        }
    }

    public static void refreshConnectListener(Context context, DeviceProtocol deviceProtocol, DeviceConnector.ConnectionListener connectionListener, BluetoothDevice bluetoothDevice) {
        synchronized (mOtaLock) {
            if (deviceProtocol == DeviceProtocol.PROTOCOL_SPP) {
                SppConnector.getsConnector(context, null).refreshConnectListener(connectionListener, bluetoothDevice);
            }
        }
    }

    public static void disconnect(HmDevice hmDevice) {
        synchronized (mOtaLock) {
            if (hmDevice.getPreferredProtocol() == DeviceProtocol.PROTOCOL_SPP) {
                SppConnector.getsConnector(null, null).disconnect(hmDevice);
            }
        }
    }

    public static BesSdkConstants.BesConnectState getDeviceConnectState(Context context, BesServiceConfig besServiceConfig) {
        synchronized (mOtaLock) {
            if (besServiceConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_SPP) {
                return SppConnector.getsConnector(context, null).getDeviceConnectState(besServiceConfig);
            }
            return BesSdkConstants.BesConnectState.BES_CONFIG_ERROR;
        }
    }

    public static byte[] getTotaAesKey(Context context, DeviceProtocol deviceProtocol, BluetoothDevice bluetoothDevice) {
        synchronized (mOtaLock) {
            if (deviceProtocol == DeviceProtocol.PROTOCOL_SPP) {
                return SppConnector.getsConnector(context, null).getTotaAesKey(bluetoothDevice);
            }
            return new byte[0];
        }
    }

    public static void saveTotaAesKey(Context context, DeviceProtocol deviceProtocol, byte[] bArr, BluetoothDevice bluetoothDevice) {
        synchronized (mOtaLock) {
            if (deviceProtocol == DeviceProtocol.PROTOCOL_SPP) {
                SppConnector.getsConnector(context, null).saveTotaAesKey(bArr, bluetoothDevice);
            }
        }
    }

    public static byte[] getCurSendData() {
        return curSendData;
    }
}
