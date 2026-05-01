package com.aivox.jieliota.tool.ota.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.jieliota.tool.config.ConfigHelper;
import com.aivox.jieliota.tool.ota.ble.SendBleDataThread;
import com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback;
import com.aivox.jieliota.tool.ota.ble.interfaces.OnWriteDataCallback;
import com.aivox.jieliota.tool.ota.ble.model.BleDevice;
import com.aivox.jieliota.tool.ota.ble.model.BleScanInfo;
import com.aivox.jieliota.util.AppUtil;
import com.aivox.jieliota.util.ReConnectHelper;
import com.jieli.jl_bt_ota.constant.BluetoothConstant;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class BleManager {
    private static final int CALLBACK_TIMEOUT = 6000;
    private static final int CONNECT_BLE_TIMEOUT = 40000;
    private static final int MAX_RETRY_CONNECT_COUNT = 1;
    private static final int MIN_CONNECT_TIME = 5000;
    private static final int MSG_BLE_DISCOVER_SERVICES_CALLBACK_TIMEOUT = 4117;
    private static final int MSG_CHANGE_BLE_MTU_TIMEOUT = 4116;
    private static final int MSG_CONNECT_BLE_TIMEOUT = 4113;
    private static final int MSG_NOTIFY_BLE_TIMEOUT = 4115;
    private static final int MSG_SCAN_BLE_TIMEOUT = 4112;
    private static final int MSG_SCAN_HID_DEVICE = 4114;
    private static final int RECONNECT_BLE_DELAY = 2000;
    private static final int SCAN_BLE_TIMEOUT = 12000;
    public static final int SEND_DATA_MAX_TIMEOUT = 8000;
    private static final String TAG = "BleManager";
    private static volatile BleManager instance;
    private volatile boolean isBleScanning;
    private BaseBtAdapterReceiver mAdapterReceiver;
    private final BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private volatile BluetoothDevice mConnectingBtDevice;
    private final Context mContext;
    private NotifyCharacteristicRunnable mNotifyCharacteristicRunnable;
    private final ReConnectHelper mReConnectHelper;
    private volatile BluetoothDevice mUsingDevice;
    public static final UUID BLE_UUID_SERVICE = BluetoothConstant.UUID_SERVICE;
    public static final UUID BLE_UUID_WRITE = BluetoothConstant.UUID_WRITE;
    public static final UUID BLE_UUID_NOTIFICATION = BluetoothConstant.UUID_NOTIFICATION;
    public static final UUID BLE_UUID_NOTIFICATION_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805F9B34FB");
    private final ConfigHelper configHelper = ConfigHelper.INSTANCE.getInstance();
    private final Map<String, BleDevice> mConnectedGattMap = new HashMap();
    private final List<BluetoothDevice> mDiscoveredBleDevices = new ArrayList();
    private final BleEventCallbackManager mCallbackManager = new BleEventCallbackManager();
    private int mRetryConnectCount = 0;
    private long startConnectTime = 0;
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.aivox.jieliota.tool.ota.ble.BleManager.1
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            List<BluetoothGattService> services;
            switch (message.what) {
                case BleManager.MSG_SCAN_BLE_TIMEOUT /* 4112 */:
                    if (BleManager.this.isBleScanning) {
                        BleManager.this.stopLeScan();
                    }
                    return false;
                case 4113:
                    if (message.obj instanceof BluetoothDevice) {
                        BluetoothDevice bluetoothDevice = (BluetoothDevice) message.obj;
                        if (BleManager.this.getConnectedBle(bluetoothDevice) == null) {
                            BleManager.this.handleBleConnection(bluetoothDevice, 0);
                        }
                        BleManager.this.setConnectingBtDevice(null);
                    }
                    return false;
                case 4114:
                    List<BluetoothDevice> systemConnectedBtDeviceList = BluetoothUtil.getSystemConnectedBtDeviceList(BleManager.this.mContext);
                    if (systemConnectedBtDeviceList != null && AppUtil.checkHasConnectPermission(BleManager.this.mContext)) {
                        for (BluetoothDevice bluetoothDevice2 : systemConnectedBtDeviceList) {
                            if (bluetoothDevice2.getType() != 1 && bluetoothDevice2.getBondState() == 12) {
                                BleManager.this.handleDiscoveryBle(bluetoothDevice2, null);
                            }
                        }
                    }
                    BleManager.this.mHandler.sendEmptyMessageDelayed(4114, 1000L);
                    return false;
                case 4115:
                    if (message.obj instanceof BluetoothDevice) {
                        BleManager.this.disconnectBleDevice((BluetoothDevice) message.obj);
                    }
                    return false;
                case BleManager.MSG_CHANGE_BLE_MTU_TIMEOUT /* 4116 */:
                    BluetoothDevice bluetoothDevice3 = (BluetoothDevice) message.obj;
                    BleDevice connectedBle = BleManager.this.getConnectedBle(bluetoothDevice3);
                    JL_Log.m848i(BleManager.TAG, "-MSG_CHANGE_BLE_MTU_TIMEOUT- request mtu timeout, device : " + BleManager.this.printDeviceInfo(bluetoothDevice3) + ", " + connectedBle);
                    if (connectedBle != null) {
                        BleManager.this.handleBleConnectedEvent(bluetoothDevice3);
                    } else {
                        BleManager.this.handleBleConnection(bluetoothDevice3, 0);
                    }
                    return false;
                case BleManager.MSG_BLE_DISCOVER_SERVICES_CALLBACK_TIMEOUT /* 4117 */:
                    if (message.obj instanceof BluetoothDevice) {
                        BluetoothDevice bluetoothDevice4 = (BluetoothDevice) message.obj;
                        if (BluetoothUtil.deviceEquals(bluetoothDevice4, BleManager.this.mUsingDevice)) {
                            BleDevice connectedBle2 = BleManager.this.getConnectedBle(bluetoothDevice4);
                            if (connectedBle2 == null || (services = connectedBle2.getGatt().getServices()) == null || services.size() <= 0) {
                                JL_Log.m844d(BleManager.TAG, "discover services timeout.");
                                BleManager.this.disconnectBleDevice(bluetoothDevice4);
                                BleManager.this.reconnectDevice(bluetoothDevice4.getAddress(), false);
                            } else {
                                BleManager.this.mBluetoothGattCallback.onServicesDiscovered(connectedBle2.getGatt(), 0);
                            }
                        }
                    }
                    return false;
                default:
                    return false;
            }
        }
    });
    private final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() { // from class: com.aivox.jieliota.tool.ota.ble.BleManager$$ExternalSyntheticLambda1
        @Override // android.bluetooth.BluetoothAdapter.LeScanCallback
        public final void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
            this.f$0.m2517lambda$new$1$comaivoxjieliotatoolotableBleManager(bluetoothDevice, i, bArr);
        }
    };
    private final ScanCallback mScanCallback = new ScanCallback() { // from class: com.aivox.jieliota.tool.ota.ble.BleManager.2
        @Override // android.bluetooth.le.ScanCallback
        public void onBatchScanResults(List<ScanResult> list) {
        }

        @Override // android.bluetooth.le.ScanCallback
        public void onScanResult(int i, ScanResult scanResult) {
            if (scanResult == null || scanResult.getScanRecord() == null) {
                return;
            }
            BleManager.this.filterDevice(scanResult.getDevice(), scanResult.getRssi(), scanResult.getScanRecord().getBytes(), scanResult.isConnectable());
        }

        @Override // android.bluetooth.le.ScanCallback
        public void onScanFailed(int i) {
            JL_Log.m844d(BleManager.TAG, "onScanFailed : " + i);
            BleManager.this.stopLeScan();
        }
    };
    private final BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() { // from class: com.aivox.jieliota.tool.ota.ble.BleManager.3
        public void onConnectionUpdated(BluetoothGatt bluetoothGatt, int i, int i2, int i3, int i4) {
            BluetoothDevice device;
            if (bluetoothGatt == null || !AppUtil.checkHasConnectPermission(BleManager.this.mContext) || (device = bluetoothGatt.getDevice()) == null) {
                return;
            }
            JL_Log.m846e(BleManager.TAG, "onConnectionUpdated >> device : " + BleManager.this.printDeviceInfo(device) + ", interval : " + i + ", latency : " + i2 + ", timeout : " + i3 + ", status : " + i4);
            BleManager.this.mCallbackManager.onConnectionUpdated(device, i, i2, i3, i4);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            BluetoothDevice device;
            if (bluetoothGatt == null || !AppUtil.checkHasConnectPermission(BleManager.this.mContext) || (device = bluetoothGatt.getDevice()) == null) {
                return;
            }
            JL_Log.m848i(BleManager.TAG, String.format(Locale.getDefault(), "onConnectionStateChange : device : %s, status = %d, newState = %d.", BleManager.this.printDeviceInfo(device), Integer.valueOf(i), Integer.valueOf(i2)));
            if (i2 == 0 || i2 == 3 || i2 == 2) {
                BleManager.this.stopConnectTimeout();
                BleManager.this.setConnectingBtDevice(null);
                if (i2 == 2) {
                    BleManager.this.mRetryConnectCount = 0;
                    boolean zDiscoverServices = bluetoothGatt.discoverServices();
                    JL_Log.m844d(BleManager.TAG, "onConnectionStateChange >> discoverServices : " + zDiscoverServices);
                    BleManager.this.putConnectedGattInMap(device.getAddress(), bluetoothGatt);
                    if (zDiscoverServices) {
                        BleManager.this.mHandler.removeMessages(BleManager.MSG_BLE_DISCOVER_SERVICES_CALLBACK_TIMEOUT);
                        BleManager.this.mHandler.sendMessageDelayed(BleManager.this.mHandler.obtainMessage(BleManager.MSG_BLE_DISCOVER_SERVICES_CALLBACK_TIMEOUT, device), 6000L);
                        return;
                    } else {
                        BleManager.this.disconnectBleDevice(device);
                        return;
                    }
                }
                BleManager.this.removeConnectedBle(device);
                AppUtil.refreshBleDeviceCache(BleManager.this.mContext, bluetoothGatt);
                bluetoothGatt.close();
                long jCurrentTimeMillis = System.currentTimeMillis() - BleManager.this.startConnectTime;
                JL_Log.m844d(BleManager.TAG, "onConnectionStateChange >> usedConnectTime = " + jCurrentTimeMillis + ", limit time = 5000");
                if (i == 133 && jCurrentTimeMillis < 5000) {
                    if (BleManager.this.mRetryConnectCount >= 1) {
                        BleManager.this.mRetryConnectCount = 0;
                    } else {
                        BleManager.access$1608(BleManager.this);
                        BleManager.this.connectBleDevice(device);
                        return;
                    }
                }
            }
            BleManager.this.handleBleConnection(device, i2);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            BluetoothDevice device;
            boolean z;
            if (bluetoothGatt == null || !AppUtil.checkHasConnectPermission(BleManager.this.mContext) || (device = bluetoothGatt.getDevice()) == null) {
                return;
            }
            BleManager.this.mHandler.removeMessages(BleManager.MSG_BLE_DISCOVER_SERVICES_CALLBACK_TIMEOUT);
            BleManager.this.mCallbackManager.onBleServiceDiscovery(device, i, bluetoothGatt.getServices());
            if (i == 0) {
                AppUtil.printBleGattServices(BleManager.this.mContext, device, bluetoothGatt, i);
                for (BluetoothGattService bluetoothGattService : bluetoothGatt.getServices()) {
                    if (BleManager.BLE_UUID_SERVICE.equals(bluetoothGattService.getUuid()) && bluetoothGattService.getCharacteristic(BleManager.BLE_UUID_WRITE) != null && bluetoothGattService.getCharacteristic(BleManager.BLE_UUID_NOTIFICATION) != null) {
                        JL_Log.m848i(BleManager.TAG, "start NotifyCharacteristicRunnable...");
                        BleManager.this.mNotifyCharacteristicRunnable = new NotifyCharacteristicRunnable(bluetoothGatt, BleManager.BLE_UUID_SERVICE, BleManager.BLE_UUID_NOTIFICATION);
                        BleManager.this.mHandler.post(BleManager.this.mNotifyCharacteristicRunnable);
                        z = true;
                        break;
                    }
                }
                z = false;
            } else {
                z = false;
            }
            JL_Log.m848i(BleManager.TAG, "onServicesDiscovered : " + z);
            if (z) {
                return;
            }
            BleManager.this.disconnectBleDevice(device);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            BluetoothDevice device;
            if (bluetoothGatt == null || !AppUtil.checkHasConnectPermission(BleManager.this.mContext) || (device = bluetoothGatt.getDevice()) == null || bluetoothGattCharacteristic == null) {
                return;
            }
            UUID uuid = bluetoothGattCharacteristic.getUuid();
            byte[] value = bluetoothGattCharacteristic.getValue();
            BluetoothGattService service = bluetoothGattCharacteristic.getService();
            UUID uuid2 = service != null ? service.getUuid() : null;
            JL_Log.m844d(BleManager.TAG, String.format(Locale.getDefault(), "onCharacteristicChanged : deice : %s, serviceUuid = %s, characteristicUuid = %s, \ndata : [%s]", BleManager.this.printDeviceInfo(device), uuid2, uuid, CHexConver.byte2HexStr(value)));
            BleManager.this.mCallbackManager.onBleDataNotification(device, uuid2, uuid, value);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            if (bluetoothGatt == null || bluetoothGatt.getDevice() == null || bluetoothGattCharacteristic == null || !AppUtil.checkHasConnectPermission(BleManager.this.mContext)) {
                return;
            }
            BluetoothDevice device = bluetoothGatt.getDevice();
            UUID uuid = bluetoothGattCharacteristic.getUuid();
            BluetoothGattService service = bluetoothGattCharacteristic.getService();
            UUID uuid2 = service != null ? service.getUuid() : null;
            byte[] value = bluetoothGattCharacteristic.getValue();
            JL_Log.m844d(BleManager.TAG, String.format(Locale.getDefault(), "onCharacteristicWrite : device : %s, serviceUuid = %s, characteristicUuid = %s, status = %d, \ndata : [%s]", BleManager.this.printDeviceInfo(device), uuid2, uuid, Integer.valueOf(i), CHexConver.byte2HexStr(value)));
            BleManager.this.wakeupSendThread(bluetoothGatt, uuid2, uuid, i, value);
            BleManager.this.mCallbackManager.onBleWriteStatus(device, uuid2, uuid, value, i);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            BluetoothDevice device;
            UUID uuid;
            UUID uuid2;
            if (bluetoothGatt == null || !AppUtil.checkHasConnectPermission(BleManager.this.mContext) || (device = bluetoothGatt.getDevice()) == null || bluetoothGattDescriptor == null) {
                return;
            }
            BluetoothGattCharacteristic characteristic = bluetoothGattDescriptor.getCharacteristic();
            if (characteristic != null) {
                uuid2 = characteristic.getUuid();
                BluetoothGattService service = characteristic.getService();
                uuid = service != null ? service.getUuid() : null;
            } else {
                uuid = null;
                uuid2 = null;
            }
            JL_Log.m848i(BleManager.TAG, String.format(Locale.getDefault(), "onDescriptorWrite : device : %s, serviceUuid = %s, characteristicUuid = %s, descriptor = %s, status = %d", BleManager.this.printDeviceInfo(device), uuid, uuid2, bluetoothGattDescriptor.getUuid(), Integer.valueOf(i)));
            BleManager.this.mCallbackManager.onBleNotificationStatus(device, uuid, uuid2, i);
            if (BleManager.this.mNotifyCharacteristicRunnable == null || !BluetoothUtil.deviceEquals(device, BleManager.this.mNotifyCharacteristicRunnable.getBleDevice()) || uuid == null || !uuid.equals(BleManager.this.mNotifyCharacteristicRunnable.getServiceUUID()) || uuid2 == null || !uuid2.equals(BleManager.this.mNotifyCharacteristicRunnable.getCharacteristicUUID()) || bluetoothGattDescriptor.getUuid() == null || !bluetoothGattDescriptor.getUuid().equals(BleManager.this.mNotifyCharacteristicRunnable.mDescriptorUUID)) {
                return;
            }
            if (i == 0) {
                BleManager.this.mNotifyCharacteristicRunnable = null;
                int bleRequestMtu = BleManager.this.configHelper.getBleRequestMtu();
                if (bleRequestMtu > 509) {
                    bleRequestMtu = 509;
                }
                BleManager.this.startChangeMtu(bluetoothGatt, bleRequestMtu);
                return;
            }
            int retryNum = BleManager.this.mNotifyCharacteristicRunnable.getRetryNum();
            if (retryNum >= 3) {
                BleManager.this.disconnectBleDevice(device);
            } else {
                BleManager.this.mNotifyCharacteristicRunnable.setRetryNum(retryNum + 1);
                BleManager.this.mHandler.postDelayed(BleManager.this.mNotifyCharacteristicRunnable, 100L);
            }
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
            BluetoothDevice device;
            if (bluetoothGatt == null || !AppUtil.checkHasConnectPermission(BleManager.this.mContext) || (device = bluetoothGatt.getDevice()) == null) {
                return;
            }
            JL_Log.m844d(BleManager.TAG, String.format(Locale.getDefault(), "onMtuChanged : device : %s, mtu = %d, status = %d", BleManager.this.printDeviceInfo(device), Integer.valueOf(i), Integer.valueOf(i2)));
            BleManager.this.mCallbackManager.onBleDataBlockChanged(device, i, i2);
            BleDevice connectedBle = BleManager.this.getConnectedBle(device);
            if (i2 == 0) {
                int i3 = i - 3;
                if (connectedBle == null || !BleManager.this.mHandler.hasMessages(BleManager.MSG_CHANGE_BLE_MTU_TIMEOUT)) {
                    return;
                }
                BleManager.this.stopChangeMtu();
                connectedBle.setMtu(i3);
                JL_Log.m848i(BleManager.TAG, "-onMtuChanged- handleBleConnectedEvent");
                BleManager.this.handleBleConnectedEvent(device);
            }
        }
    };

    static /* synthetic */ int access$1608(BleManager bleManager) {
        int i = bleManager.mRetryConnectCount;
        bleManager.mRetryConnectCount = i + 1;
        return i;
    }

    private BleManager(Context context) {
        this.mContext = (Context) CommonUtil.checkNotNull(context);
        if (CommonUtil.getMainContext() == null) {
            CommonUtil.setMainContext(context);
        }
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        if (defaultAdapter != null) {
            this.mBluetoothLeScanner = defaultAdapter.getBluetoothLeScanner();
        }
        this.mReConnectHelper = new ReConnectHelper(context, this);
        registerReceiver();
    }

    public static BleManager getInstance() {
        if (instance == null) {
            synchronized (BleManager.class) {
                if (instance == null) {
                    instance = new BleManager(BaseAppUtils.getContext());
                    JL_Log.m852w(TAG, "init BleManager.. " + instance);
                }
            }
        }
        return instance;
    }

    public static List<BluetoothDevice> getConnectedBleDeviceList(Context context) {
        BluetoothManager bluetoothManager;
        if (context == null || !AppUtil.checkHasConnectPermission(context) || (bluetoothManager = (BluetoothManager) context.getSystemService("bluetooth")) == null) {
            return null;
        }
        return bluetoothManager.getConnectedDevices(7);
    }

    public void destroy() {
        JL_Log.m852w(TAG, ">>>>>>>>>>>>>>destroy >>>>>>>>>>>>>>> ");
        unregisterReceiver();
        stopConnectTimeout();
        clearConnectedBleDevices();
        if (isBleScanning()) {
            stopLeScan();
        }
        isBleScanning(false);
        this.mDiscoveredBleDevices.clear();
        this.mReConnectHelper.release();
        this.mCallbackManager.release();
        this.mHandler.removeCallbacksAndMessages(null);
        instance = null;
    }

    public void registerBleEventCallback(BleEventCallback bleEventCallback) {
        this.mCallbackManager.registerBleEventCallback(bleEventCallback);
    }

    public void unregisterBleEventCallback(BleEventCallback bleEventCallback) {
        this.mCallbackManager.unregisterBleEventCallback(bleEventCallback);
    }

    public boolean isBluetoothEnable() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    public boolean isBleScanning() {
        return this.isBleScanning;
    }

    public boolean startLeScan(long j) {
        boolean zStartLeScan = false;
        if (this.mBluetoothAdapter != null && AppUtil.checkHasScanPermission(this.mContext) && isBluetoothEnable() && AppUtil.isHasLocationPermission(this.mContext)) {
            if (j <= 0) {
                j = 12000;
            }
            zStartLeScan = true;
            if (this.isBleScanning) {
                JL_Log.m848i(TAG, "scanning ble .....");
                BluetoothLeScanner bluetoothLeScanner = this.mBluetoothLeScanner;
                if (bluetoothLeScanner != null) {
                    bluetoothLeScanner.flushPendingScanResults(this.mScanCallback);
                }
                this.mDiscoveredBleDevices.clear();
                this.mHandler.removeMessages(MSG_SCAN_BLE_TIMEOUT);
                this.mHandler.sendEmptyMessageDelayed(MSG_SCAN_BLE_TIMEOUT, j);
                syncSystemBleDevice();
                return true;
            }
            if (this.mBluetoothLeScanner != null) {
                this.mBluetoothLeScanner.startScan((List<ScanFilter>) null, new ScanSettings.Builder().setScanMode(1).setMatchMode(1).build(), this.mScanCallback);
            } else {
                zStartLeScan = this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
            }
            JL_Log.m848i(TAG, "startLeScan : " + zStartLeScan + ", timeout = " + j);
            isBleScanning(zStartLeScan);
            if (zStartLeScan) {
                this.mDiscoveredBleDevices.clear();
                this.mHandler.removeMessages(MSG_SCAN_BLE_TIMEOUT);
                this.mHandler.sendEmptyMessageDelayed(MSG_SCAN_BLE_TIMEOUT, j);
                syncSystemBleDevice();
            }
        }
        return zStartLeScan;
    }

    public void stopLeScan() {
        if (this.mBluetoothAdapter != null && isBluetoothEnable() && AppUtil.checkHasScanPermission(this.mContext) && isBleScanning()) {
            try {
                BluetoothLeScanner bluetoothLeScanner = this.mBluetoothLeScanner;
                if (bluetoothLeScanner != null) {
                    bluetoothLeScanner.stopScan(this.mScanCallback);
                } else {
                    this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mHandler.removeMessages(MSG_SCAN_BLE_TIMEOUT);
            this.mHandler.removeMessages(4114);
            isBleScanning(false);
        }
    }

    public BluetoothDevice getConnectedBtDevice() {
        return this.mUsingDevice;
    }

    public BluetoothGatt getConnectedBtGatt(BluetoothDevice bluetoothDevice) {
        BleDevice connectedBle = getConnectedBle(bluetoothDevice);
        if (connectedBle == null) {
            return null;
        }
        return connectedBle.getGatt();
    }

    public BluetoothDevice getConnectedBLEDevice(String str) {
        if (!BluetoothAdapter.checkBluetoothAddress(str)) {
            return null;
        }
        List<BluetoothDevice> connectedDeviceList = getConnectedDeviceList();
        if (connectedDeviceList.isEmpty()) {
            return null;
        }
        for (BluetoothDevice bluetoothDevice : connectedDeviceList) {
            if (bluetoothDevice.getAddress().equals(str)) {
                return bluetoothDevice;
            }
        }
        return null;
    }

    public List<BluetoothDevice> getConnectedDeviceList() {
        if (this.mConnectedGattMap.isEmpty()) {
            return new ArrayList();
        }
        List<BleDevice> sortList = getSortList();
        ArrayList arrayList = new ArrayList();
        for (BleDevice bleDevice : sortList) {
            if (bleDevice != null && bleDevice.getGatt().getDevice() != null) {
                arrayList.add(bleDevice.getGatt().getDevice());
            }
        }
        return arrayList;
    }

    public void reconnectDevice(String str, boolean z) {
        String str2 = TAG;
        JL_Log.m844d(str2, "reconnectDevice : address = " + str + ", isUseAdv = " + z);
        JL_Log.m844d(str2, "reconnectDevice : ret = " + this.mReConnectHelper.putParam(new ReConnectHelper.ReconnectParam(str, z)));
    }

    public boolean isMatchReConnectDevice(String str, String str2) {
        return this.mReConnectHelper.isMatchAddress(str, str2);
    }

    public int getBleMtu(BluetoothDevice bluetoothDevice) {
        BleDevice connectedBle = getConnectedBle(bluetoothDevice);
        if (connectedBle == null) {
            return 0;
        }
        return connectedBle.getMtu();
    }

    public boolean isConnecting() {
        return this.mConnectingBtDevice != null;
    }

    public boolean isConnectingDevice(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.deviceEquals(this.mConnectingBtDevice, bluetoothDevice);
    }

    public boolean isConnectedDevice(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return false;
        }
        return isConnectedDevice(bluetoothDevice.getAddress());
    }

    public boolean isConnectedDevice(String str) {
        if (!BluetoothAdapter.checkBluetoothAddress(str)) {
            return false;
        }
        List<BluetoothDevice> connectedDeviceList = getConnectedDeviceList();
        if (connectedDeviceList.isEmpty()) {
            return false;
        }
        Iterator<BluetoothDevice> it = connectedDeviceList.iterator();
        while (it.hasNext()) {
            if (it.next().getAddress().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean connectBleDevice(BluetoothDevice bluetoothDevice) {
        BluetoothGatt bluetoothGattConnectGatt;
        if (bluetoothDevice != null && AppUtil.checkHasConnectPermission(this.mContext)) {
            if (this.mConnectingBtDevice != null) {
                JL_Log.m846e(TAG, "BleDevice is connecting, please wait.");
                return isConnectingDevice(bluetoothDevice);
            }
            if (isBleScanning()) {
                stopLeScan();
            }
            try {
                bluetoothGattConnectGatt = bluetoothDevice.connectGatt(this.mContext, false, this.mBluetoothGattCallback, 2);
            } catch (Exception e) {
                e.printStackTrace();
                bluetoothGattConnectGatt = null;
            }
            z = bluetoothGattConnectGatt != null;
            if (z) {
                setConnectingBtDevice(bluetoothDevice);
                handleBleConnection(bluetoothDevice, 1);
                startConnectTimeout(bluetoothDevice);
                JL_Log.m844d(TAG, "connect start...." + printDeviceInfo(bluetoothDevice));
            }
        }
        return z;
    }

    public void disconnectBleDevice(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null || !AppUtil.checkHasConnectPermission(this.mContext)) {
            return;
        }
        BleDevice bleDeviceRemoveConnectedBle = removeConnectedBle(bluetoothDevice);
        String str = TAG;
        JL_Log.m848i(str, "disconnectBleDevice : " + printDeviceInfo(bluetoothDevice) + ", " + bleDeviceRemoveConnectedBle);
        if (bleDeviceRemoveConnectedBle != null) {
            if (BluetoothUtil.isBluetoothEnable()) {
                bleDeviceRemoveConnectedBle.getGatt().disconnect();
                return;
            }
            return;
        }
        JL_Log.m848i(str, "disconnectBleDevice : It is not a connected device.");
    }

    public void writeDataByBleAsync(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, byte[] bArr, OnWriteDataCallback onWriteDataCallback) {
        addSendTask(bluetoothDevice, uuid, uuid2, bArr, onWriteDataCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isBleScanning(boolean z) {
        this.isBleScanning = z;
        this.mCallbackManager.onDiscoveryBleChange(z);
        if (this.isBleScanning && this.configHelper.isHidDevice()) {
            this.mHandler.sendEmptyMessage(4114);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BleDevice getConnectedBle(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return null;
        }
        return this.mConnectedGattMap.get(bluetoothDevice.getAddress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putConnectedGattInMap(String str, BluetoothGatt bluetoothGatt) {
        if (!BluetoothAdapter.checkBluetoothAddress(str) || bluetoothGatt == null) {
            return;
        }
        BleDevice bleDevice = new BleDevice(this.mContext, bluetoothGatt);
        bleDevice.setConnectedTime(System.currentTimeMillis());
        this.mConnectedGattMap.put(str, bleDevice);
        if (this.mUsingDevice == null) {
            this.mUsingDevice = bluetoothGatt.getDevice();
        }
        JL_Log.m848i(TAG, "putConnectedGattInMap >>>>>>>>>>>>> start");
        Iterator<String> it = this.mConnectedGattMap.keySet().iterator();
        while (it.hasNext()) {
            JL_Log.m844d(TAG, "putConnectedGattInMap >>>>>>>>>>>>> " + it.next());
        }
        JL_Log.m848i(TAG, "putConnectedGattInMap >>>>>>>>>>>>> end");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BleDevice removeConnectedBle(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return null;
        }
        return removeConnectedBle(bluetoothDevice.getAddress());
    }

    private BleDevice removeConnectedBle(String str) {
        if (!BluetoothAdapter.checkBluetoothAddress(str)) {
            return null;
        }
        BleDevice bleDeviceRemove = this.mConnectedGattMap.remove(str);
        if (bleDeviceRemove != null) {
            bleDeviceRemove.stopSendDataThread();
            if (this.mConnectedGattMap.isEmpty()) {
                setConnectedBtDevice(null);
            } else if (bleDeviceRemove.getGatt().getDevice() != null && BluetoothUtil.deviceEquals(bleDeviceRemove.getGatt().getDevice(), getConnectedBtDevice())) {
                setConnectedBtDevice(getSortList().get(0).getGatt().getDevice());
            }
        }
        return bleDeviceRemove;
    }

    private List<BleDevice> getSortList() {
        if (this.mConnectedGattMap.isEmpty()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(this.mConnectedGattMap.values());
        Collections.sort(arrayList, new Comparator() { // from class: com.aivox.jieliota.tool.ota.ble.BleManager$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return BleManager.lambda$getSortList$0((BleDevice) obj, (BleDevice) obj2);
            }
        });
        return arrayList;
    }

    static /* synthetic */ int lambda$getSortList$0(BleDevice bleDevice, BleDevice bleDevice2) {
        if (bleDevice == null && bleDevice2 == null) {
            return 0;
        }
        if (bleDevice == null) {
            return 1;
        }
        if (bleDevice2 == null) {
            return -1;
        }
        return Long.compare(bleDevice2.getConnectedTime(), bleDevice.getConnectedTime());
    }

    private void clearConnectedBleDevices() {
        if (AppUtil.checkHasConnectPermission(this.mContext) && !this.mConnectedGattMap.isEmpty()) {
            HashMap map = new HashMap(this.mConnectedGattMap);
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                BleDevice bleDevice = (BleDevice) map.get((String) it.next());
                if (bleDevice != null) {
                    bleDevice.getGatt().disconnect();
                    bleDevice.getGatt().close();
                }
            }
            this.mConnectedGattMap.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setConnectingBtDevice(BluetoothDevice bluetoothDevice) {
        this.mConnectingBtDevice = bluetoothDevice;
    }

    private void setConnectedBtDevice(BluetoothDevice bluetoothDevice) {
        this.mUsingDevice = bluetoothDevice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void filterDevice(BluetoothDevice bluetoothDevice, int i, byte[] bArr, boolean z) {
        if (!AppUtil.checkHasConnectPermission(this.mContext) || !isBluetoothEnable() || TextUtils.isEmpty(bluetoothDevice.getName()) || this.mDiscoveredBleDevices.contains(bluetoothDevice)) {
            return;
        }
        JL_Log.m844d(TAG, "notify device : " + printDeviceInfo(bluetoothDevice));
        this.mDiscoveredBleDevices.add(bluetoothDevice);
        handleDiscoveryBle(bluetoothDevice, new BleScanInfo().setRawData(bArr).setRssi(i).setEnableConnect(z));
    }

    private void startConnectTimeout(BluetoothDevice bluetoothDevice) {
        if (this.mHandler.hasMessages(4113)) {
            return;
        }
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(4113, bluetoothDevice), 40000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopConnectTimeout() {
        if (this.mHandler.hasMessages(4113)) {
            this.mHandler.removeMessages(4113);
        }
    }

    private void syncSystemBleDevice() {
        List<BluetoothDevice> connectedBleDeviceList = getConnectedBleDeviceList(this.mContext);
        if (connectedBleDeviceList == null || connectedBleDeviceList.isEmpty()) {
            return;
        }
        for (BluetoothDevice bluetoothDevice : connectedBleDeviceList) {
            if (!BluetoothUtil.deviceEquals(bluetoothDevice, this.mUsingDevice) && !this.mDiscoveredBleDevices.contains(bluetoothDevice)) {
                this.mDiscoveredBleDevices.add(bluetoothDevice);
                handleDiscoveryBle(bluetoothDevice, new BleScanInfo().setEnableConnect(true));
            }
        }
    }

    private void addSendTask(BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, byte[] bArr, OnWriteDataCallback onWriteDataCallback) {
        BleDevice connectedBle = getConnectedBle(bluetoothDevice);
        if ((connectedBle != null ? connectedBle.addSendTask(uuid, uuid2, bArr, onWriteDataCallback) : false) || onWriteDataCallback == null) {
            return;
        }
        onWriteDataCallback.onBleResult(bluetoothDevice, uuid, uuid2, false, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wakeupSendThread(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2, int i, byte[] bArr) {
        BleDevice connectedBle = getConnectedBle(bluetoothGatt.getDevice());
        if (connectedBle != null) {
            SendBleDataThread.BleSendTask bleSendTask = new SendBleDataThread.BleSendTask(bluetoothGatt, uuid, uuid2, bArr, null);
            bleSendTask.setStatus(i);
            connectedBle.wakeupSendThread(bleSendTask);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDiscoveryBle(BluetoothDevice bluetoothDevice, BleScanInfo bleScanInfo) {
        this.mCallbackManager.onDiscoveryBle(bluetoothDevice, bleScanInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBleConnection(BluetoothDevice bluetoothDevice, int i) {
        if (i == 0 || i == 2) {
            this.mHandler.removeMessages(4115);
            this.startConnectTime = 0L;
        } else if (i == 1) {
            this.startConnectTime = System.currentTimeMillis();
        }
        JL_Log.m848i(TAG, "handleBleConnection >> device : " + printDeviceInfo(bluetoothDevice) + ", status : " + i);
        this.mCallbackManager.onBleConnection(bluetoothDevice, i);
    }

    private void registerReceiver() {
        if (this.mAdapterReceiver == null) {
            this.mAdapterReceiver = new BaseBtAdapterReceiver();
            IntentFilter intentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
            this.mContext.registerReceiver(this.mAdapterReceiver, intentFilter);
        }
    }

    private void unregisterReceiver() {
        BaseBtAdapterReceiver baseBtAdapterReceiver = this.mAdapterReceiver;
        if (baseBtAdapterReceiver != null) {
            this.mContext.unregisterReceiver(baseBtAdapterReceiver);
            this.mAdapterReceiver = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean enableBLEDeviceNotification(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2) {
        if (bluetoothGatt == null || !AppUtil.checkHasConnectPermission(this.mContext)) {
            JL_Log.m852w(TAG, "Bluetooth gatt is null.");
            return false;
        }
        BluetoothGattService service = bluetoothGatt.getService(uuid);
        if (service == null) {
            JL_Log.m852w(TAG, "BluetoothGattService is null. uuid = " + uuid);
            return false;
        }
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(uuid2);
        if (characteristic == null) {
            JL_Log.m852w(TAG, "BluetoothGattCharacteristic is null. uuid = " + uuid2);
            return false;
        }
        boolean characteristicNotification = bluetoothGatt.setCharacteristicNotification(characteristic, true);
        if (characteristicNotification) {
            characteristicNotification = false;
            for (BluetoothGattDescriptor bluetoothGattDescriptor : characteristic.getDescriptors()) {
                if (BLE_UUID_NOTIFICATION_DESCRIPTOR.equals(bluetoothGattDescriptor.getUuid())) {
                    characteristicNotification = tryToWriteDescriptor(bluetoothGatt, bluetoothGattDescriptor, 0, false);
                    if (characteristicNotification) {
                        break;
                    }
                    JL_Log.m852w(TAG, "tryToWriteDescriptor failed....");
                }
            }
        } else {
            JL_Log.m852w(TAG, "setCharacteristicNotification is failed....");
        }
        JL_Log.m852w(TAG, "enableBLEDeviceNotification ret : " + characteristicNotification + ", serviceUUID : " + uuid + ", characteristicUUID : " + uuid2);
        return characteristicNotification;
    }

    private boolean tryToWriteDescriptor(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i, boolean z) {
        if (!AppUtil.checkHasConnectPermission(this.mContext)) {
            return false;
        }
        if (!z) {
            z = bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            String str = TAG;
            JL_Log.m848i(str, "..descriptor : .setValue  ret : " + z);
            if (z) {
                i = 0;
            } else {
                i++;
                if (i >= 3) {
                    return false;
                }
                JL_Log.m848i(str, "-tryToWriteDescriptor- : retryCount : " + i + ", isSkipSetValue :  false");
                SystemClock.sleep(50L);
                tryToWriteDescriptor(bluetoothGatt, bluetoothGattDescriptor, i, false);
            }
        }
        if (z) {
            z = bluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
            String str2 = TAG;
            JL_Log.m848i(str2, "..bluetoothGatt : .writeDescriptor  ret : " + z);
            if (!z) {
                int i2 = i + 1;
                if (i2 >= 3) {
                    return false;
                }
                JL_Log.m848i(str2, "-tryToWriteDescriptor- 2222 : retryCount : " + i2 + ", isSkipSetValue :  true");
                SystemClock.sleep(50L);
                tryToWriteDescriptor(bluetoothGatt, bluetoothGattDescriptor, i2, true);
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startChangeMtu(BluetoothGatt bluetoothGatt, int i) {
        if (bluetoothGatt == null || !AppUtil.checkHasConnectPermission(this.mContext)) {
            JL_Log.m852w(TAG, "-startChangeMtu- param is error.");
            return;
        }
        BluetoothDevice device = bluetoothGatt.getDevice();
        if (device == null) {
            JL_Log.m852w(TAG, "-startChangeMtu- device is null.");
            return;
        }
        if (this.mHandler.hasMessages(MSG_CHANGE_BLE_MTU_TIMEOUT)) {
            JL_Log.m852w(TAG, "-startChangeMtu- Adjusting the MTU for BLE");
            return;
        }
        boolean zRequestMtu = i > 20 ? bluetoothGatt.requestMtu(i + 3) : false;
        JL_Log.m844d(TAG, "-startChangeMtu- ret = " + zRequestMtu);
        if (zRequestMtu) {
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(MSG_CHANGE_BLE_MTU_TIMEOUT, device), 6000L);
        } else {
            handleBleConnectedEvent(device);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopChangeMtu() {
        this.mHandler.removeMessages(MSG_CHANGE_BLE_MTU_TIMEOUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBleConnectedEvent(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            JL_Log.m846e(TAG, "-handleBleConnectedEvent- device is null.");
            return;
        }
        stopChangeMtu();
        getConnectedBle(bluetoothDevice).startSendDataThread();
        handleBleConnection(bluetoothDevice, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String printDeviceInfo(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.printBtDeviceInfo(this.mContext, bluetoothDevice);
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-jieliota-tool-ota-ble-BleManager, reason: not valid java name */
    /* synthetic */ void m2517lambda$new$1$comaivoxjieliotatoolotableBleManager(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        filterDevice(bluetoothDevice, i, bArr, true);
    }

    private class BaseBtAdapterReceiver extends BroadcastReceiver {
        private BaseBtAdapterReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action;
            if (intent == null || (action = intent.getAction()) == null) {
            }
            action.hashCode();
            switch (action) {
                case "android.bluetooth.adapter.action.STATE_CHANGED":
                    int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", -1);
                    if (BleManager.this.mBluetoothAdapter != null && intExtra == -1) {
                        intExtra = BleManager.this.mBluetoothAdapter.getState();
                    }
                    if (intExtra == 10) {
                        BleManager.this.isBleScanning(false);
                        BleManager.this.mDiscoveredBleDevices.clear();
                        BleManager.this.mCallbackManager.onDiscoveryBleChange(false);
                        BleManager bleManager = BleManager.this;
                        bleManager.disconnectBleDevice(bleManager.getConnectedBtDevice());
                        BleManager.this.mCallbackManager.onAdapterChange(false);
                        break;
                    } else {
                        if (intExtra == 12) {
                            BleManager.this.mCallbackManager.onAdapterChange(true);
                        }
                        break;
                    }
                    break;
                case "android.bluetooth.device.action.ACL_CONNECTED":
                    JL_Log.m848i(BleManager.TAG, "BaseBtAdapterReceiver: ACTION_ACL_CONNECTED, device : " + BleManager.this.printDeviceInfo((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")));
                    break;
                case "android.bluetooth.device.action.ACL_DISCONNECTED":
                    JL_Log.m848i(BleManager.TAG, "BaseBtAdapterReceiver: ACTION_ACL_DISCONNECTED, device : " + BleManager.this.printDeviceInfo((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE")));
                    break;
            }
        }
    }

    private class NotifyCharacteristicRunnable implements Runnable {
        private final UUID mCharacteristicUUID;
        public final UUID mDescriptorUUID;
        private final BluetoothGatt mGatt;
        private final UUID mServiceUUID;
        private int retryNum;

        private NotifyCharacteristicRunnable(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2) {
            this.mDescriptorUUID = BleManager.BLE_UUID_NOTIFICATION_DESCRIPTOR;
            this.retryNum = 0;
            this.mGatt = bluetoothGatt;
            this.mServiceUUID = uuid;
            this.mCharacteristicUUID = uuid2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRetryNum(int i) {
            this.retryNum = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getRetryNum() {
            return this.retryNum;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public BluetoothDevice getBleDevice() {
            BluetoothGatt bluetoothGatt = this.mGatt;
            if (bluetoothGatt == null) {
                return null;
            }
            return bluetoothGatt.getDevice();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public UUID getServiceUUID() {
            return this.mServiceUUID;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public UUID getCharacteristicUUID() {
            return this.mCharacteristicUUID;
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean zEnableBLEDeviceNotification = BleManager.this.enableBLEDeviceNotification(this.mGatt, this.mServiceUUID, this.mCharacteristicUUID);
            JL_Log.m852w(BleManager.TAG, String.format(Locale.getDefault(), "enableBLEDeviceNotification ===> %s, service uuid = %s, characteristic uuid = %s", Boolean.valueOf(zEnableBLEDeviceNotification), this.mServiceUUID, this.mCharacteristicUUID));
            if (zEnableBLEDeviceNotification) {
                BleManager.this.mHandler.removeMessages(4115);
                BleManager.this.mHandler.sendMessageDelayed(BleManager.this.mHandler.obtainMessage(4115, this.mGatt.getDevice()), 6000L);
            } else {
                BluetoothGatt bluetoothGatt = this.mGatt;
                if (bluetoothGatt != null) {
                    BleManager.this.disconnectBleDevice(bluetoothGatt.getDevice());
                }
            }
        }
    }
}
