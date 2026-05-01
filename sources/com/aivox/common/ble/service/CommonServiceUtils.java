package com.aivox.common.ble.service;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import com.aivox.base.util.DataUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.common.ble.listener.SppBtConnectListener;
import com.aivox.common.model.DataHandle;
import java.util.Set;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class CommonServiceUtils {
    public static UUID UUID1 = UUID.fromString("48534300-0000-0000-0000-0058494F4E47");
    public static UUID UUID2 = UUID.fromString("FE010000-1234-5678-ABCD-00805F9B34FB");
    public static UUID UUID3 = UUID.fromString("48534300-0000-2000-8000-0058494F4E47");
    private final String TAG;
    private final BroadcastReceiver mCommonReceiver;
    private boolean mSppServiceRunning;

    private CommonServiceUtils() {
        this.TAG = "CommonServiceUtils";
        this.mCommonReceiver = new BroadcastReceiver() { // from class: com.aivox.common.ble.service.CommonServiceUtils.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Removed duplicated region for block: B:23:0x0068  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onReceive(android.content.Context r7, android.content.Intent r8) {
                /*
                    Method dump skipped, instruction units count: 320
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.aivox.common.ble.service.CommonServiceUtils.C09711.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
    }

    private static final class MInstanceHolder {
        static final CommonServiceUtils mInstance = new CommonServiceUtils();

        private MInstanceHolder() {
        }
    }

    public static CommonServiceUtils getInstance() {
        return MInstanceHolder.mInstance;
    }

    public void setSppListening(SppBtConnectListener sppBtConnectListener) {
        SppBtService.getInstance().setListening(sppBtConnectListener);
    }

    public void removeListening() {
        SppBtService.getInstance().removeListening();
    }

    public void startSppService(Context context) {
        if (this.mSppServiceRunning) {
            return;
        }
        context.startService(new Intent(context, (Class<?>) SppBtService.class));
        this.mSppServiceRunning = true;
        LogUtil.m339i("CommonServiceUtils", "SPP 服务已开启");
        context.registerReceiver(this.mCommonReceiver, makeSppIntentFilter(), 2);
        LogUtil.m339i("CommonServiceUtils", "SPP 通用蓝牙广播监听已注册");
    }

    public Set<BluetoothDevice> getBondedDevices() {
        return SppBtService.getInstance().getBondedDevices();
    }

    public void connect(String str) {
        SppBtService.getInstance().connect(str);
    }

    public void stopService(Context context) {
        stopBleService(context);
        stopSppService(context);
    }

    public void startBleService(Context context) {
        BleServiceUtils.getInstance().startService(context);
        context.registerReceiver(this.mCommonReceiver, makeSppIntentFilter(), 2);
        LogUtil.m339i("CommonServiceUtils", "BLE 通用蓝牙广播监听已注册");
    }

    public void stopBleService(Context context) {
        BleServiceUtils.getInstance().cleanListening();
        BleServiceUtils.getInstance().stopService(context);
        try {
            context.unregisterReceiver(this.mCommonReceiver);
            LogUtil.m339i("CommonServiceUtils", "BLE 通用蓝牙广播监听已解除");
        } catch (Exception e) {
            LogUtil.m339i("CommonServiceUtils", "BLE 通用蓝牙广播监听未解除：" + e);
        }
    }

    public void stopSppService(Context context) {
        if (this.mSppServiceRunning) {
            context.stopService(new Intent(context, (Class<?>) SppBtService.class));
            this.mSppServiceRunning = false;
            LogUtil.m339i("CommonServiceUtils", "SPP 服务已关闭");
            try {
                context.unregisterReceiver(this.mCommonReceiver);
                LogUtil.m339i("CommonServiceUtils", "SPP 通用蓝牙广播监听已解除");
            } catch (Exception e) {
                LogUtil.m339i("CommonServiceUtils", "SPP 通用蓝牙广播监听未解除：" + e);
            }
        }
    }

    public void sendData(String str) {
        LogUtil.m339i("CommonServiceUtils", "发送数据：" + str);
        if (DataHandle.getIns().isHasConnectedBle(false)) {
            BleBtService.getInstance().writeRXCharacteristic(str.getBytes());
        } else {
            SppBtService.getInstance().send(str.getBytes());
        }
    }

    public void sendData(byte[] bArr) {
        if (!DataUtil.bytes2Hex(bArr).equals("a5060007000101000016f6")) {
            LogUtil.m339i("CommonServiceUtils", "发送数据：" + DataUtil.bytes2Hex(bArr));
        }
        if (DataHandle.getIns().isHasConnectedBle(false)) {
            BleBtService.getInstance().writeRXCharacteristic(bArr);
        } else {
            SppBtService.getInstance().send(bArr);
        }
    }

    public void disconnect() {
        if (DataHandle.getIns().isHasConnectedSpp()) {
            SppBtService.getInstance().disconnect(false);
        }
        if (DataHandle.getIns().isHasConnectedBle(false)) {
            BleBtService.getInstance().disconnect();
        }
    }

    public void disconnect(Context context) {
        if (this.mSppServiceRunning) {
            return;
        }
        SppBtService.getInstance().disconnect(false);
    }

    private IntentFilter makeSppIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        intentFilter.addAction("android.bluetooth.device.action.FOUND");
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        intentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
        return intentFilter;
    }

    public String getConnectedDeviceName() {
        if (DataHandle.getIns().isHasConnectedBle(false)) {
            return BleBtService.getInstance().getConnectedDeviceName();
        }
        return SppBtService.getInstance().getConnectedDeviceName();
    }

    public String getRealConnectedDeviceName() {
        if (DataHandle.getIns().isHasConnectedBle(false)) {
            return BleBtService.getInstance().getRealConnectedDeviceName();
        }
        return SppBtService.getInstance().getConnectedDeviceName();
    }

    public String getConnectedDeviceAddress() {
        if (DataHandle.getIns().isHasConnectedBle(false)) {
            return BleBtService.getInstance().getConnectedDeviceAddress();
        }
        return SppBtService.getInstance().getConnectedDeviceAddress();
    }

    public boolean isContainUUid(ParcelUuid[] parcelUuidArr, UUID uuid) {
        if (parcelUuidArr == null) {
            return false;
        }
        try {
            for (ParcelUuid parcelUuid : parcelUuidArr) {
                if (parcelUuid.getUuid().compareTo(uuid) == 0) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }
}
