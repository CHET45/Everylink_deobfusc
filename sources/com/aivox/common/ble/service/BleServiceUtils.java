package com.aivox.common.ble.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.aivox.base.common.MyEnum;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DataUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.common.ble.listener.BLeBtConnectListener;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.DeviceBean;
import com.aivox.common.model.EventBean;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class BleServiceUtils {
    private static final String TAG = "BleServiceUtils";
    private static boolean mScanning;
    private final BroadcastReceiver UARTStatusChangeReceiver;
    private boolean bleServiceIsRunning;
    private final List<String> glassFilter;
    private BLeBtConnectListener listening;
    private final ScanCallback mScanCallback;

    private BleServiceUtils() {
        this.listening = null;
        this.glassFilter = new ArrayList();
        this.mScanCallback = new ScanCallback() { // from class: com.aivox.common.ble.service.BleServiceUtils.1
            @Override // android.bluetooth.le.ScanCallback
            public void onScanResult(int i, ScanResult scanResult) {
                super.onScanResult(i, scanResult);
                if (scanResult.getDevice().getName() == null || BleServiceUtils.this.getDeviceMode(scanResult) == null || BleServiceUtils.this.listening == null) {
                    return;
                }
                BleServiceUtils.this.listening.refreshDeviceList(scanResult.getDevice(), scanResult.getRssi(), BleServiceUtils.this.getDeviceMode(scanResult));
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onBatchScanResults(List<ScanResult> list) {
                super.onBatchScanResults(list);
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onScanFailed(int i) {
                super.onScanFailed(i);
                LogUtil.m339i(BleServiceUtils.TAG, "\nonScanFailed=" + i);
                BleBtService.getInstance().getBluetoothAdapter().getBluetoothLeScanner().stopScan(BleServiceUtils.this.mScanCallback);
            }
        };
        this.UARTStatusChangeReceiver = new BroadcastReceiver() { // from class: com.aivox.common.ble.service.BleServiceUtils.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(BleBtService.ACTION_GATT_CONNECTED) && BleServiceUtils.this.listening != null) {
                    LogUtil.m339i(BleServiceUtils.TAG, "ACTION_GATT_CONNECTED: " + BleBtService.getInstance().getBluetoothAdapter().getName());
                    BleServiceUtils.this.listening.connectSuccess();
                }
                if (action.equals(BleBtService.ACTION_GATT_DISCONNECTED)) {
                    BleBtService.getInstance().disconnect();
                    BleBtService.getInstance().close();
                    if (BleServiceUtils.this.listening != null) {
                        BleServiceUtils.this.listening.connectFailed(BleBtService.ACTION_GATT_DISCONNECTED);
                        LogUtil.m339i(BleServiceUtils.TAG, "ACTION_GATT_DISCONNECTED");
                        DataHandle.getIns().setHasConnectedBle(false);
                        EventBus.getDefault().post(new EventBean(301));
                    }
                }
                if (action.equals(BleBtService.ACTION_GATT_SERVICES_DISCOVERED)) {
                    BleBtService.getInstance().enableTXNotification();
                    if (BleServiceUtils.this.listening != null) {
                        LogUtil.m339i(BleServiceUtils.TAG, "ACTION_GATT_SERVICES_DISCOVERED");
                    }
                }
                if (action.equals(BleBtService.ACTION_DATA_AVAILABLE)) {
                    byte[] byteArrayExtra = intent.getByteArrayExtra(BleBtService.EXTRA_DATA);
                    if (BleServiceUtils.this.listening != null) {
                        LogUtil.m339i(BleServiceUtils.TAG, "ACTION_DATA_AVAILABLE" + DataUtil.bytes216String(byteArrayExtra));
                    } else {
                        LogUtil.m339i(BleServiceUtils.TAG, "========listening=null===========");
                    }
                }
                if (!action.equals(BleBtService.DEVICE_DOES_NOT_SUPPORT_UART) || BleServiceUtils.this.listening == null) {
                    return;
                }
                LogUtil.m339i(BleServiceUtils.TAG, "DEVICE_DOES_NOT_SUPPORT_UART");
            }
        };
    }

    private static final class MInstanceHolder {
        static final BleServiceUtils mInstance = new BleServiceUtils();

        private MInstanceHolder() {
        }
    }

    public static BleServiceUtils getInstance() {
        return MInstanceHolder.mInstance;
    }

    public void setListening(BLeBtConnectListener bLeBtConnectListener) {
        this.listening = bLeBtConnectListener;
    }

    public void cleanListening() {
        this.listening = null;
    }

    public void startService(Context context) {
        stopService(context);
        context.startService(new Intent(context, (Class<?>) BleBtService.class));
        this.bleServiceIsRunning = true;
        LocalBroadcastManager.getInstance(context).registerReceiver(this.UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
        LogUtil.m339i(TAG, "BLE 服务已开启");
    }

    public void stopService(Context context) {
        if (this.bleServiceIsRunning) {
            context.stopService(new Intent(context, (Class<?>) BleBtService.class));
            this.bleServiceIsRunning = false;
            LocalBroadcastManager.getInstance(context).unregisterReceiver(this.UARTStatusChangeReceiver);
            LogUtil.m339i(TAG, "BLE 服务已关闭");
        }
    }

    public void disconnect() {
        cleanListening();
        BleBtService.getInstance().disconnect();
    }

    public void openBle(Context context) {
        BleBtService.getInstance().openBle(context);
        BLeBtConnectListener bLeBtConnectListener = this.listening;
        if (bLeBtConnectListener != null) {
            bLeBtConnectListener.openBle();
        }
    }

    public void setRecording(boolean z) {
        BleBtService.getInstance().setRecording(z);
    }

    public void connectDevice(DeviceBean deviceBean) {
        BleBtService.getInstance().setGlass(this.glassFilter.contains(deviceBean.getAddress()));
        BleBtService.getInstance().connect(deviceBean);
    }

    public void scanLeDevice(boolean z) {
        if (z) {
            mScanning = true;
            LogUtil.m338i("scanLeDevice  " + (BleBtService.getInstance().getBluetoothAdapter() != null));
            if (BleBtService.getInstance().getBluetoothAdapter() != null) {
                startScan();
                return;
            }
            return;
        }
        if (mScanning) {
            try {
                stopScan();
            } catch (Exception e) {
                LogUtil.m338i("scanLeDevice  Exception : " + e);
            }
            mScanning = false;
        }
    }

    private void startScan() {
        BluetoothAdapter bluetoothAdapter = BleBtService.getInstance().getBluetoothAdapter();
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.startDiscovery();
            bluetoothAdapter.getBluetoothLeScanner().startScan(this.mScanCallback);
        }
    }

    public void stopScan() {
        if (mScanning) {
            BluetoothAdapter bluetoothAdapter = BleBtService.getInstance().getBluetoothAdapter();
            if (bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.getBluetoothLeScanner().stopScan(this.mScanCallback);
                bluetoothAdapter.cancelDiscovery();
            }
        }
    }

    private IntentFilter makeGattUpdateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BleBtService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BleBtService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BleBtService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BleBtService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BleBtService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }

    public boolean isConnect() {
        return BleBtService.getInstance().getConnectionState() == 2;
    }

    public boolean isBtOpen() {
        return BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    public MyEnum.BLE_DEVICE_MODEL getDeviceMode(ScanResult scanResult) {
        if (scanResult != null && scanResult.getScanRecord() != null) {
            byte[] bytes = scanResult.getScanRecord().getBytes();
            if (bytes.length < 14) {
                return null;
            }
            ArrayList arrayList = new ArrayList(Arrays.asList(MyEnum.BLE_DEVICE_MODEL.values()));
            for (int i = 0; i < arrayList.size(); i++) {
                byte[] bytes2 = ((MyEnum.BLE_DEVICE_MODEL) arrayList.get(i)).filterName.getBytes(StandardCharsets.UTF_8);
                if (bytes2.length == 8 && bytes[5] == bytes2[0] && bytes[6] == bytes2[1] && bytes[7] == bytes2[2] && bytes[14] == bytes2[3] && bytes[15] == bytes2[4] && bytes[16] == bytes2[5] && bytes[17] == bytes2[6] && bytes[18] == bytes2[7]) {
                    return (MyEnum.BLE_DEVICE_MODEL) arrayList.get(i);
                }
                if (bytes2.length == 3 && bytes[5] == bytes2[0] && bytes[6] == bytes2[1] && bytes[7] == bytes2[2]) {
                    if (!this.glassFilter.contains(scanResult.getDevice().getAddress())) {
                        this.glassFilter.add(scanResult.getDevice().getAddress());
                    }
                    LogUtil.m339i("Glasses", "眼镜蓝牙已发现:" + BaseStringUtil.getHexStringByteArray(bytes));
                    return (MyEnum.BLE_DEVICE_MODEL) arrayList.get(i);
                }
            }
        }
        return null;
    }
}
