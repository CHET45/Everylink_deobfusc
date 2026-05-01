package com.aivox.jieliota.tool.ota.spp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.SystemClock;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.jieliota.tool.config.ConfigHelper;
import com.aivox.jieliota.tool.ota.spp.ConnectionSppThread;
import com.aivox.jieliota.tool.ota.spp.ReceiveSppDataThread;
import com.aivox.jieliota.tool.ota.spp.SendSppDataThread;
import com.aivox.jieliota.tool.ota.spp.interfaces.OnWriteSppDataCallback;
import com.aivox.jieliota.tool.ota.spp.interfaces.SppEventCallback;
import com.aivox.jieliota.util.AppUtil;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.jieli.jl_bt_ota.impl.RcspAuth;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import com.jieli.jl_bt_ota.util.PreferencesHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class SppManager implements SendSppDataThread.ISppOp {
    private static final int BOND_DEV_TIMEOUT = 30000;
    private static final int CONNECT_DEV_TIMEOUT = 40000;
    private static final String KEY_SPP_UUID = "spp_uuid";
    private static final int MSG_CONNECT_SPP_TIMEOUT = 1024;
    private static final int MSG_CREATE_BOND_TIMEOUT = 1023;
    private static final int MSG_DISCOVERY_EDR_TIMEOUT = 1022;
    private static final String TAG = "SppManager";
    private static volatile SppManager instance;
    public final UUID customSppUUID;
    private volatile boolean isDeviceAuth;
    private final boolean isNeedAuth;
    private BluetoothReceiver mBluetoothReceiver;
    private volatile BluetoothDevice mBondingDevice;
    private final BluetoothAdapter mBtAdapter;
    private ConnectionSppThread mConnectSppThread;
    private volatile BluetoothDevice mConnectedSppDevice;
    private final Map<String, ReceiveSppDataThread> mConnectedSppMap;
    private volatile BluetoothDevice mConnectingSppDevice;
    private final Context mContext;
    private final List<BluetoothDevice> mDiscoveredEdrDevices;
    private DiscoveryReceiver mDiscoveryReceiver;
    private final Handler mHandler;
    private final RcspAuth.OnRcspAuthListener mOnRcspAuthListener;
    private final RcspAuth mRcspAuth;
    private long mScanTimeout;
    private SendSppDataThread mSendSppDataThread;
    private final SppEventCallbackManager mSppEventCallbackManager;
    private UUID mSppUUID;
    private final boolean useSppPrivateChannel;
    public static final UUID UUID_SPP = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_DEFAULT_CUSTOM = UUID.fromString("fe010000-1234-5678-abcd-00805f9b34fb");

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-jieliota-tool-ota-spp-SppManager, reason: not valid java name */
    /* synthetic */ boolean m2521lambda$new$0$comaivoxjieliotatoolotasppSppManager(Message message) {
        switch (message.what) {
            case MSG_DISCOVERY_EDR_TIMEOUT /* 1022 */:
                boolean zIsScanning = isScanning();
                JL_Log.m852w(TAG, "call MSG_DISCOVERY_EDR_TIMEOUT >> isScanning = " + zIsScanning);
                if (zIsScanning) {
                    stopDeviceScan();
                }
                break;
            case 1023:
                if (message.obj instanceof BluetoothDevice) {
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) message.obj;
                    Bundle data = message.getData();
                    UUID uuid = UUID_SPP;
                    if (data != null) {
                        uuid = (UUID) data.getSerializable(KEY_SPP_UUID);
                    }
                    JL_Log.m852w(TAG, "call MSG_CREATE_BOND_TIMEOUT >> device = " + printDeviceInfo(bluetoothDevice) + ", sppUUID = " + uuid);
                    if (isPaired(bluetoothDevice)) {
                        startConnectSppThread(bluetoothDevice, uuid);
                    } else {
                        handleSppConnection(bluetoothDevice, uuid, 0);
                    }
                }
                break;
            case 1024:
                if (message.obj instanceof BluetoothDevice) {
                    BluetoothDevice bluetoothDevice2 = (BluetoothDevice) message.obj;
                    Bundle data2 = message.getData();
                    UUID uuid2 = UUID_SPP;
                    if (data2 != null) {
                        uuid2 = (UUID) data2.getSerializable(KEY_SPP_UUID);
                    }
                    JL_Log.m852w(TAG, "call MSG_CONNECT_SPP_TIMEOUT >> device = " + printDeviceInfo(bluetoothDevice2) + ", sppUUID = " + uuid2);
                    if (!isSppSocketConnected(bluetoothDevice2, uuid2)) {
                        handleSppConnection(bluetoothDevice2, uuid2, 0);
                    }
                }
                break;
        }
        return true;
    }

    private SppManager(Context context) {
        this(context, false);
    }

    private SppManager(Context context, boolean z) {
        this.mDiscoveredEdrDevices = new ArrayList();
        this.mConnectedSppMap = new HashMap();
        this.useSppPrivateChannel = ConfigHelper.INSTANCE.getInstance().isUseMultiSppChannel();
        this.mHandler = new Handler(new Handler.Callback() { // from class: com.aivox.jieliota.tool.ota.spp.SppManager$$ExternalSyntheticLambda1
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return this.f$0.m2521lambda$new$0$comaivoxjieliotatoolotasppSppManager(message);
            }
        });
        RcspAuth.OnRcspAuthListener onRcspAuthListener = new RcspAuth.OnRcspAuthListener() { // from class: com.aivox.jieliota.tool.ota.spp.SppManager.4
            @Override // com.jieli.jl_bt_ota.impl.RcspAuth.OnRcspAuthListener
            public void onInitResult(boolean z2) {
                JL_Log.m846e(SppManager.TAG, "-onInitResult- " + z2);
            }

            @Override // com.jieli.jl_bt_ota.impl.RcspAuth.OnRcspAuthListener
            public void onAuthSuccess(BluetoothDevice bluetoothDevice) {
                JL_Log.m852w(SppManager.TAG, "-onAuthSuccess- >>> auth ok, handleSppConnection : " + SppManager.this.printDeviceInfo(bluetoothDevice));
                SppManager.this.isDeviceAuth = true;
                SppManager sppManager = SppManager.this;
                sppManager.handleSppConnection(bluetoothDevice, sppManager.mSppUUID, 2);
            }

            @Override // com.jieli.jl_bt_ota.impl.RcspAuth.OnRcspAuthListener
            public void onAuthFailed(BluetoothDevice bluetoothDevice, int i, String str) {
                JL_Log.m852w(SppManager.TAG, String.format(Locale.getDefault(), "-onAuthFailed- device : %s, code : %d, message : %s", SppManager.this.printDeviceInfo(bluetoothDevice), Integer.valueOf(i), str));
                SppManager.this.isDeviceAuth = false;
                SppManager sppManager = SppManager.this;
                sppManager.disconnectSpp(bluetoothDevice, sppManager.mSppUUID);
            }
        };
        this.mOnRcspAuthListener = onRcspAuthListener;
        this.mContext = context;
        if (CommonUtil.getMainContext() == null) {
            CommonUtil.setMainContext(context);
        }
        this.customSppUUID = UUID.fromString(PreferencesHelper.getSharedPreferences(context).getString(KEY_SPP_UUID, UUID_DEFAULT_CUSTOM.toString()));
        this.isNeedAuth = z;
        this.isDeviceAuth = !z;
        this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mSppEventCallbackManager = new SppEventCallbackManager();
        this.mRcspAuth = new RcspAuth(context, new RcspAuth.IRcspAuthOp() { // from class: com.aivox.jieliota.tool.ota.spp.SppManager$$ExternalSyntheticLambda2
            @Override // com.jieli.jl_bt_ota.impl.RcspAuth.IRcspAuthOp
            public final boolean sendAuthDataToDevice(BluetoothDevice bluetoothDevice, byte[] bArr) {
                return this.f$0.m2523lambda$new$2$comaivoxjieliotatoolotasppSppManager(bluetoothDevice, bArr);
            }
        }, onRcspAuthListener);
        registerBluetoothReceiver();
    }

    /* JADX INFO: renamed from: lambda$new$2$com-aivox-jieliota-tool-ota-spp-SppManager, reason: not valid java name */
    /* synthetic */ boolean m2523lambda$new$2$comaivoxjieliotatoolotasppSppManager(BluetoothDevice bluetoothDevice, byte[] bArr) {
        writeDataToSppAsync(bluetoothDevice, UUID_SPP, bArr, new OnWriteSppDataCallback() { // from class: com.aivox.jieliota.tool.ota.spp.SppManager$$ExternalSyntheticLambda0
            @Override // com.aivox.jieliota.tool.ota.spp.interfaces.OnWriteSppDataCallback
            public final void onSppResult(BluetoothDevice bluetoothDevice2, UUID uuid, boolean z, byte[] bArr2) {
                this.f$0.m2522lambda$new$1$comaivoxjieliotatoolotasppSppManager(bluetoothDevice2, uuid, z, bArr2);
            }
        });
        return true;
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-jieliota-tool-ota-spp-SppManager, reason: not valid java name */
    /* synthetic */ void m2522lambda$new$1$comaivoxjieliotatoolotasppSppManager(BluetoothDevice bluetoothDevice, UUID uuid, boolean z, byte[] bArr) {
        JL_Log.m848i(TAG, "-sendAuthDataToDevice- device = " + printDeviceInfo(bluetoothDevice) + ", result = " + z);
    }

    public static SppManager getInstance() {
        if (instance == null) {
            synchronized (SppManager.class) {
                if (instance == null) {
                    instance = new SppManager(BaseAppUtils.getContext());
                }
            }
        }
        return instance;
    }

    public void registerSppEventCallback(SppEventCallback sppEventCallback) {
        this.mSppEventCallbackManager.registerSppEventCallback(sppEventCallback);
    }

    public void unregisterSppEventCallback(SppEventCallback sppEventCallback) {
        this.mSppEventCallbackManager.unregisterSppEventCallback(sppEventCallback);
    }

    public void release() {
        unregisterDiscoveryReceiver();
        unregisterBluetoothReceiver();
        this.mDiscoveredEdrDevices.clear();
        stopConnectSppThread();
        if (this.mConnectedSppDevice != null) {
            disconnectSpp(this.mConnectedSppDevice, null);
        }
        clearConnectedSppMap();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mSppEventCallbackManager.release();
        this.mRcspAuth.removeListener(this.mOnRcspAuthListener);
        this.mRcspAuth.destroy();
        instance = null;
    }

    public boolean isScanning() {
        BluetoothAdapter bluetoothAdapter;
        return AppUtil.checkHasScanPermission(this.mContext) && (bluetoothAdapter = this.mBtAdapter) != null && bluetoothAdapter.isDiscovering();
    }

    public boolean startDeviceScan(long j) {
        if (this.mBtAdapter == null || !AppUtil.checkHasScanPermission(this.mContext)) {
            JL_Log.m846e(TAG, "this device is not supported bluetooth.");
            return false;
        }
        if (!BluetoothUtil.isBluetoothEnable()) {
            JL_Log.m846e(TAG, "Bluetooth is not enable.");
            return false;
        }
        if (isScanning()) {
            if (!this.mBtAdapter.cancelDiscovery()) {
                return false;
            }
            unregisterDiscoveryReceiver();
            int i = 0;
            while (this.mBtAdapter.isDiscovering()) {
                SystemClock.sleep(100L);
                i += 100;
                if (i > 2000) {
                    break;
                }
            }
            this.mDiscoveredEdrDevices.clear();
        }
        boolean zStartDiscovery = this.mBtAdapter.startDiscovery();
        JL_Log.m848i(TAG, "-startDiscovery- >>>>>> ret : " + zStartDiscovery);
        if (!zStartDiscovery) {
            return false;
        }
        if (j < 3000) {
            this.mScanTimeout = 3000L;
        } else {
            this.mScanTimeout = j;
        }
        registerDiscoverReceiver();
        this.mDiscoveredEdrDevices.clear();
        startScanTimeoutTask();
        this.mSppEventCallbackManager.onDiscoveryDeviceChange(true);
        syncSystemConnectedDevice();
        return true;
    }

    public boolean stopDeviceScan() {
        if (this.mBtAdapter == null || !AppUtil.checkHasScanPermission(this.mContext)) {
            JL_Log.m846e(TAG, "-stopDeviceScan- :: this device is not supported bluetooth.");
            return false;
        }
        if (!BluetoothUtil.isBluetoothEnable()) {
            JL_Log.m846e(TAG, "-stopDeviceScan- :: Bluetooth is not enable.");
            unregisterDiscoveryReceiver();
            return true;
        }
        if (!this.mBtAdapter.isDiscovering()) {
            return true;
        }
        if (!this.mBtAdapter.cancelDiscovery()) {
            return false;
        }
        stopScanTimeoutTask();
        return true;
    }

    public boolean isPaired(BluetoothDevice bluetoothDevice) {
        return AppUtil.checkHasConnectPermission(this.mContext) && bluetoothDevice != null && 12 == bluetoothDevice.getBondState();
    }

    public boolean isSppConnecting() {
        return this.mConnectingSppDevice != null;
    }

    public BluetoothDevice getConnectingSppDevice() {
        return this.mConnectingSppDevice;
    }

    public BluetoothDevice getConnectedSppDevice() {
        return this.mConnectedSppDevice;
    }

    public boolean isSppConnected() {
        return this.mConnectedSppDevice != null;
    }

    public boolean connectSpp(String str) {
        return connectSpp(BluetoothUtil.getRemoteDevice(str));
    }

    public boolean connectSpp(BluetoothDevice bluetoothDevice) {
        return connectSpp(bluetoothDevice, UUID_SPP);
    }

    public boolean connectSpp(BluetoothDevice bluetoothDevice, UUID uuid) {
        if (!AppUtil.checkHasConnectPermission(this.mContext)) {
            JL_Log.m852w(TAG, "-connectSpp- miss bluetooth permission.");
            return false;
        }
        if (bluetoothDevice == null || bluetoothDevice.getType() == 2) {
            JL_Log.m852w(TAG, "-connectSpp-  device is bad object. ");
            return false;
        }
        JL_Log.m848i(TAG, "-connectSpp- >> device : " + printDeviceInfo(bluetoothDevice) + ", sppUUID = " + uuid);
        if (this.mConnectingSppDevice != null) {
            JL_Log.m848i(TAG, "-connectSpp- >>  device is connecting. device :" + printDeviceInfo(this.mConnectedSppDevice));
            return false;
        }
        if (this.mConnectedSppDevice != null) {
            if (BluetoothUtil.deviceEquals(this.mConnectedSppDevice, bluetoothDevice)) {
                if (isSppSocketConnected(bluetoothDevice, uuid)) {
                    if (!this.isNeedAuth || isDevAuth(uuid)) {
                        handleSppConnection(bluetoothDevice, uuid, 2);
                        return true;
                    }
                    JL_Log.m848i(TAG, "-connectSpp- >>  device in process of certification. device :" + printDeviceInfo(bluetoothDevice));
                    return false;
                }
            } else if (disconnectSpp(this.mConnectedSppDevice, null)) {
                SystemClock.sleep(500L);
            }
        }
        setConnectingSppDevice(bluetoothDevice);
        setSppUUID(uuid);
        boolean zIsPaired = isPaired(bluetoothDevice);
        JL_Log.m848i(TAG, "-connectSpp- >> isPaired = " + zIsPaired);
        if (!zIsPaired) {
            boolean zCreateBond = BluetoothUtil.createBond(bluetoothDevice);
            JL_Log.m848i(TAG, "-connectSpp- >> createBond = " + zCreateBond);
            if (!zCreateBond) {
                handleSppConnection(bluetoothDevice, uuid, 0);
                return false;
            }
            startPairTimeoutTask(bluetoothDevice, uuid);
        } else if (bluetoothDevice.getUuids() == null || !BluetoothUtil.deviceHasProfile(bluetoothDevice, uuid)) {
            boolean zFetchUuidsWithSdp = bluetoothDevice.fetchUuidsWithSdp();
            JL_Log.m848i(TAG, "-connectSpp- >> fetchUuidsWithSdp = " + zFetchUuidsWithSdp);
            if (!zFetchUuidsWithSdp) {
                handleSppConnection(bluetoothDevice, uuid, 0);
                return false;
            }
        } else {
            startConnectSppThread(bluetoothDevice, uuid);
        }
        handleSppConnection(bluetoothDevice, uuid, 1);
        startConnectTimeoutTask(bluetoothDevice, uuid);
        return true;
    }

    public boolean disconnectSpp(BluetoothDevice bluetoothDevice, UUID uuid) {
        if (!AppUtil.checkHasConnectPermission(this.mContext)) {
            JL_Log.m852w(TAG, "-disconnectSpp- miss bluetooth permission.");
            return false;
        }
        if (!BluetoothUtil.deviceEquals(bluetoothDevice, this.mConnectedSppDevice)) {
            JL_Log.m846e(TAG, "-disconnectSpp- >> device is not connected. device = " + printDeviceInfo(bluetoothDevice) + ",\n ConnectedSppDevice = " + printDeviceInfo(this.mConnectedSppDevice));
            return false;
        }
        JL_Log.m848i(TAG, "-disconnectSpp- device = " + printDeviceInfo(bluetoothDevice) + ", sppUUID = " + uuid);
        if (uuid == null) {
            if (!isConnectedSocketMapEmpty(bluetoothDevice)) {
                Set<String> setKeySet = this.mConnectedSppMap.keySet();
                ArrayList arrayList = new ArrayList();
                String address = bluetoothDevice.getAddress();
                Iterator<String> it = setKeySet.iterator();
                while (it.hasNext()) {
                    String[] strArrSplit = it.next().split(PunctuationConst.UNDERLINE);
                    if (strArrSplit.length == 2 && address.equals(strArrSplit[0])) {
                        arrayList.add(UUID.fromString(strArrSplit[1]));
                    }
                }
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    disconnectSpp(bluetoothDevice, (UUID) it2.next());
                }
            }
        } else {
            ReceiveSppDataThread receiveSppDataThreadRemoveRecvSppDataThread = removeRecvSppDataThread(bluetoothDevice, uuid);
            if (receiveSppDataThreadRemoveRecvSppDataThread != null) {
                BluetoothSocket bluetoothSocket = receiveSppDataThreadRemoveRecvSppDataThread.getBluetoothSocket();
                if (bluetoothSocket != null) {
                    try {
                        bluetoothSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                receiveSppDataThreadRemoveRecvSppDataThread.stopThread();
            }
            handleSppConnection(bluetoothDevice, uuid, 0);
        }
        return true;
    }

    @Override // com.aivox.jieliota.tool.ota.spp.SendSppDataThread.ISppOp
    public synchronized boolean writeDataToSppDevice(BluetoothDevice bluetoothDevice, UUID uuid, byte[] bArr) throws IOException {
        if (!AppUtil.checkHasConnectPermission(this.mContext)) {
            JL_Log.m852w(TAG, "-writeDataToSppDevice- miss bluetooth permission.");
            return false;
        }
        if (bluetoothDevice == null || bArr == null) {
            JL_Log.m846e(TAG, "-writeDataToSppDevice- param is error.");
            return false;
        }
        if (!BluetoothUtil.deviceEquals(bluetoothDevice, this.mConnectedSppDevice)) {
            JL_Log.m846e(TAG, "-writeDataToSppDevice- device is error. device = " + printDeviceInfo(bluetoothDevice));
            return false;
        }
        ReceiveSppDataThread recvSppDataThread = getRecvSppDataThread(bluetoothDevice, uuid);
        if (recvSppDataThread == null) {
            JL_Log.m846e(TAG, "-writeDataToSppDevice- receiveSppDataThread is null. device = " + printDeviceInfo(bluetoothDevice) + ", sppUUID = " + uuid);
            return false;
        }
        BluetoothSocket bluetoothSocket = recvSppDataThread.getBluetoothSocket();
        if (bluetoothSocket != null && bluetoothSocket.isConnected() && bluetoothSocket.getOutputStream() != null) {
            bluetoothSocket.getOutputStream().write(bArr);
            JL_Log.m844d(TAG, "-writeDataToSppDevice- device = " + printDeviceInfo(bluetoothDevice) + ", sppUUID = " + uuid + "\n send ret = true, raw data = " + CHexConver.byte2HexStr(bArr));
            return true;
        }
        JL_Log.m846e(TAG, "-writeDataToSppDevice- spp socket is close.");
        return false;
    }

    public void writeDataToSppAsync(BluetoothDevice bluetoothDevice, UUID uuid, byte[] bArr, OnWriteSppDataCallback onWriteSppDataCallback) {
        addSendTask(bluetoothDevice, uuid, bArr, onWriteSppDataCallback);
    }

    public boolean checkDeviceIsAuth(BluetoothDevice bluetoothDevice, UUID uuid) {
        return BluetoothUtil.deviceEquals(bluetoothDevice, this.mConnectedSppDevice) && (!this.isNeedAuth || isDevAuth(uuid));
    }

    public boolean isSppSocketConnected(BluetoothDevice bluetoothDevice, UUID uuid) {
        BluetoothSocket bluetoothSocket;
        if (!AppUtil.checkHasConnectPermission(this.mContext)) {
            JL_Log.m852w(TAG, "-isSppSocketConnected- miss bluetooth permission.");
            return false;
        }
        ReceiveSppDataThread recvSppDataThread = getRecvSppDataThread(bluetoothDevice, uuid);
        return (recvSppDataThread == null || (bluetoothSocket = recvSppDataThread.getBluetoothSocket()) == null || !bluetoothSocket.isConnected()) ? false : true;
    }

    private void setConnectingSppDevice(BluetoothDevice bluetoothDevice) {
        this.mConnectingSppDevice = bluetoothDevice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setConnectedSppDevice(BluetoothDevice bluetoothDevice) {
        this.mConnectedSppDevice = bluetoothDevice;
        if (bluetoothDevice != null) {
            setConnectingSppDevice(null);
        } else {
            this.isDeviceAuth = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSppUUID(UUID uuid) {
        this.mSppUUID = uuid;
    }

    private boolean isDevAuth(UUID uuid) {
        if (UUID_SPP.equals(uuid)) {
            return this.isDeviceAuth;
        }
        return true;
    }

    private String getSocketUUID(BluetoothDevice bluetoothDevice, UUID uuid) {
        if (bluetoothDevice == null || uuid == null) {
            return null;
        }
        return bluetoothDevice.getAddress() + PunctuationConst.UNDERLINE + uuid;
    }

    private void addRecvSppDataThread(String str, ReceiveSppDataThread receiveSppDataThread) {
        if (str == null || receiveSppDataThread == null) {
            return;
        }
        this.mConnectedSppMap.put(str, receiveSppDataThread);
    }

    private ReceiveSppDataThread getRecvSppDataThread(BluetoothDevice bluetoothDevice, UUID uuid) {
        if (getSocketUUID(bluetoothDevice, uuid) == null) {
            return null;
        }
        return this.mConnectedSppMap.get(getSocketUUID(bluetoothDevice, uuid));
    }

    private ReceiveSppDataThread removeRecvSppDataThread(BluetoothDevice bluetoothDevice, UUID uuid) {
        if (getSocketUUID(bluetoothDevice, uuid) == null) {
            return null;
        }
        return this.mConnectedSppMap.remove(getSocketUUID(bluetoothDevice, uuid));
    }

    private boolean isConnectedSocketMapEmpty(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null || this.mConnectedSppMap.isEmpty()) {
            return true;
        }
        String address = bluetoothDevice.getAddress();
        Iterator<String> it = this.mConnectedSppMap.keySet().iterator();
        while (it.hasNext()) {
            String[] strArrSplit = it.next().split(PunctuationConst.UNDERLINE);
            if (strArrSplit.length == 2 && address.equals(strArrSplit[0])) {
                return false;
            }
        }
        return true;
    }

    private void clearConnectedSppMap() {
        if (this.mConnectedSppMap.isEmpty()) {
            return;
        }
        Iterator<String> it = this.mConnectedSppMap.keySet().iterator();
        while (it.hasNext()) {
            ReceiveSppDataThread receiveSppDataThread = this.mConnectedSppMap.get(it.next());
            if (receiveSppDataThread != null) {
                receiveSppDataThread.stopThread();
            }
        }
        this.mConnectedSppMap.clear();
    }

    private void syncSystemConnectedDevice() {
        List<BluetoothDevice> systemConnectedBtDeviceList;
        if (!AppUtil.checkHasConnectPermission(this.mContext) || (systemConnectedBtDeviceList = BluetoothUtil.getSystemConnectedBtDeviceList(this.mContext)) == null || systemConnectedBtDeviceList.isEmpty()) {
            return;
        }
        for (BluetoothDevice bluetoothDevice : systemConnectedBtDeviceList) {
            if (isSppDevice(bluetoothDevice) && !BluetoothUtil.deviceEquals(this.mConnectedSppDevice, bluetoothDevice) && !this.mDiscoveredEdrDevices.contains(bluetoothDevice)) {
                this.mDiscoveredEdrDevices.add(bluetoothDevice);
                this.mSppEventCallbackManager.onDiscoveryDevice(bluetoothDevice, 0);
            }
        }
    }

    private void startScanTimeoutTask() {
        if (this.mScanTimeout == 0) {
            return;
        }
        JL_Log.m844d(TAG, "-startScanTimeoutTask- mScanTimeout = " + this.mScanTimeout);
        this.mHandler.removeMessages(MSG_DISCOVERY_EDR_TIMEOUT);
        this.mHandler.sendEmptyMessageDelayed(MSG_DISCOVERY_EDR_TIMEOUT, this.mScanTimeout);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopScanTimeoutTask() {
        JL_Log.m844d(TAG, "-stopScanTimeoutTask-");
        this.mHandler.removeMessages(MSG_DISCOVERY_EDR_TIMEOUT);
    }

    private void startPairTimeoutTask(BluetoothDevice bluetoothDevice, UUID uuid) {
        JL_Log.m844d(TAG, "-startPairTimeoutTask- device = " + printDeviceInfo(bluetoothDevice) + ", sppUUID = " + uuid);
        this.mHandler.removeMessages(1023);
        this.mBondingDevice = bluetoothDevice;
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_SPP_UUID, uuid);
        Message messageObtainMessage = this.mHandler.obtainMessage(1023, bluetoothDevice);
        messageObtainMessage.setData(bundle);
        this.mHandler.sendMessageDelayed(messageObtainMessage, 30000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopPairTimeoutTask(BluetoothDevice bluetoothDevice) {
        JL_Log.m844d(TAG, "-stopPairTimeoutTask- device = " + printDeviceInfo(bluetoothDevice));
        if (BluetoothUtil.deviceEquals(bluetoothDevice, this.mBondingDevice)) {
            this.mHandler.removeMessages(1023);
            this.mBondingDevice = null;
        }
    }

    private void startConnectTimeoutTask(BluetoothDevice bluetoothDevice, UUID uuid) {
        JL_Log.m848i(TAG, "-startConnectTimeoutTask- device = " + printDeviceInfo(bluetoothDevice) + ", sppUUID = " + uuid);
        this.mHandler.removeMessages(1024);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_SPP_UUID, uuid);
        Message messageObtainMessage = this.mHandler.obtainMessage(1024, bluetoothDevice);
        messageObtainMessage.setData(bundle);
        this.mHandler.sendMessageDelayed(messageObtainMessage, 40000L);
    }

    private void stopConnectTimeoutTask() {
        JL_Log.m852w(TAG, "-stopConnectTimeoutTask-");
        this.mHandler.removeMessages(1024);
    }

    private void registerDiscoverReceiver() {
        if (this.mDiscoveryReceiver == null) {
            this.mDiscoveryReceiver = new DiscoveryReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
            intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
            intentFilter.addAction("android.bluetooth.device.action.FOUND");
            this.mContext.registerReceiver(this.mDiscoveryReceiver, intentFilter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterDiscoveryReceiver() {
        DiscoveryReceiver discoveryReceiver = this.mDiscoveryReceiver;
        if (discoveryReceiver != null) {
            this.mContext.unregisterReceiver(discoveryReceiver);
            this.mDiscoveryReceiver = null;
        }
    }

    private void registerBluetoothReceiver() {
        if (this.mBluetoothReceiver == null) {
            this.mBluetoothReceiver = new BluetoothReceiver();
            IntentFilter intentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.device.action.UUID");
            this.mContext.registerReceiver(this.mBluetoothReceiver, intentFilter);
        }
    }

    private void unregisterBluetoothReceiver() {
        BluetoothReceiver bluetoothReceiver = this.mBluetoothReceiver;
        if (bluetoothReceiver != null) {
            this.mContext.unregisterReceiver(bluetoothReceiver);
            this.mBluetoothReceiver = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String printDeviceInfo(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.printBtDeviceInfo(this.mContext, bluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startConnectSppThread(BluetoothDevice bluetoothDevice, UUID uuid) {
        JL_Log.m844d(TAG, "-startConnectSppThread- device = " + printDeviceInfo(bluetoothDevice) + ", sppUUID = " + uuid);
        if (this.mConnectSppThread == null) {
            ConnectionSppThread connectionSppThread = new ConnectionSppThread(this.mContext, bluetoothDevice, uuid, new ConnectionSppThread.OnConnectSppListener() { // from class: com.aivox.jieliota.tool.ota.spp.SppManager.1
                @Override // com.aivox.jieliota.tool.ota.spp.ConnectionSppThread.OnConnectSppListener
                public void onThreadStart(long j) {
                }

                @Override // com.aivox.jieliota.tool.ota.spp.ConnectionSppThread.OnConnectSppListener
                public void onThreadStop(long j, boolean z, BluetoothDevice bluetoothDevice2, UUID uuid2, BluetoothSocket bluetoothSocket) {
                    if (SppManager.this.mConnectSppThread != null && SppManager.this.mConnectSppThread.getId() == j) {
                        SppManager.this.mConnectSppThread = null;
                    }
                    if (z) {
                        SppManager.this.setConnectedSppDevice(bluetoothDevice2);
                        SppManager.this.startReceiveSppDataThread(bluetoothDevice2, uuid2, bluetoothSocket);
                        SppManager.this.startSendSppDataThread();
                        if (!SppManager.this.isNeedAuth || SppManager.this.checkDeviceIsAuth(bluetoothDevice2, uuid2)) {
                            SppManager.this.handleSppConnection(bluetoothDevice2, uuid2, 2);
                            return;
                        }
                        SppManager.this.mRcspAuth.stopAuth(bluetoothDevice2, false);
                        if (SppManager.this.mRcspAuth.startAuth(bluetoothDevice2)) {
                            SppManager.this.setSppUUID(uuid2);
                            return;
                        } else {
                            SppManager.this.disconnectSpp(bluetoothDevice2, uuid2);
                            return;
                        }
                    }
                    SppManager.this.handleSppConnection(bluetoothDevice2, uuid2, 0);
                }
            });
            this.mConnectSppThread = connectionSppThread;
            connectionSppThread.start();
        }
    }

    private void stopConnectSppThread() {
        ConnectionSppThread connectionSppThread = this.mConnectSppThread;
        if (connectionSppThread != null) {
            if (connectionSppThread.isAlive()) {
                this.mConnectSppThread.interrupt();
            }
            this.mConnectSppThread = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startReceiveSppDataThread(BluetoothDevice bluetoothDevice, UUID uuid, BluetoothSocket bluetoothSocket) {
        if (getRecvSppDataThread(bluetoothDevice, uuid) == null) {
            ReceiveSppDataThread receiveSppDataThread = new ReceiveSppDataThread(this.mContext, bluetoothDevice, uuid, bluetoothSocket, new ReceiveSppDataThread.OnRecvSppDataListener() { // from class: com.aivox.jieliota.tool.ota.spp.SppManager.2
                @Override // com.aivox.jieliota.tool.ota.spp.ReceiveSppDataThread.OnRecvSppDataListener
                public void onThreadStart(long j) {
                }

                @Override // com.aivox.jieliota.tool.ota.spp.ReceiveSppDataThread.OnRecvSppDataListener
                public void onRecvSppData(long j, BluetoothDevice bluetoothDevice2, UUID uuid2, byte[] bArr) {
                    JL_Log.m844d(SppManager.TAG, "-onRecvSppData- device = " + SppManager.this.printDeviceInfo(bluetoothDevice2) + ", sppUUID = " + uuid2 + ", \n raw data = " + CHexConver.byte2HexStr(bArr));
                    SppManager.this.mSppEventCallbackManager.onReceiveSppData(bluetoothDevice2, uuid2, bArr);
                    if (SppManager.this.checkDeviceIsAuth(bluetoothDevice2, uuid2)) {
                        return;
                    }
                    SppManager.this.mRcspAuth.handleAuthData(bluetoothDevice2, bArr);
                }

                @Override // com.aivox.jieliota.tool.ota.spp.ReceiveSppDataThread.OnRecvSppDataListener
                public void onThreadStop(long j, int i, BluetoothDevice bluetoothDevice2, UUID uuid2) {
                    if (i == 2) {
                        SppManager.this.disconnectSpp(bluetoothDevice2, uuid2);
                    }
                }
            });
            addRecvSppDataThread(getSocketUUID(bluetoothDevice, uuid), receiveSppDataThread);
            receiveSppDataThread.start();
        }
    }

    private void stopReceiveSppDataThread(BluetoothDevice bluetoothDevice, UUID uuid) {
        ReceiveSppDataThread receiveSppDataThreadRemoveRecvSppDataThread = removeRecvSppDataThread(bluetoothDevice, uuid);
        if (receiveSppDataThreadRemoveRecvSppDataThread != null) {
            receiveSppDataThreadRemoveRecvSppDataThread.stopThread();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSendSppDataThread() {
        if (this.mSendSppDataThread == null) {
            SendSppDataThread sendSppDataThread = new SendSppDataThread(this.mContext, this, new SendSppDataThread.OnSendDataListener() { // from class: com.aivox.jieliota.tool.ota.spp.SppManager.3
                @Override // com.aivox.jieliota.tool.ota.spp.SendSppDataThread.OnSendDataListener
                public void onThreadStart(long j) {
                }

                @Override // com.aivox.jieliota.tool.ota.spp.SendSppDataThread.OnSendDataListener
                public void onThreadStop(long j) {
                    if (SppManager.this.mSendSppDataThread == null || SppManager.this.mSendSppDataThread.getId() != j) {
                        return;
                    }
                    SppManager.this.mSendSppDataThread = null;
                }
            });
            this.mSendSppDataThread = sendSppDataThread;
            sendSppDataThread.start();
        }
    }

    private void stopSendSppDataThread() {
        SendSppDataThread sendSppDataThread = this.mSendSppDataThread;
        if (sendSppDataThread != null) {
            sendSppDataThread.stopThread();
        }
    }

    private void addSendTask(BluetoothDevice bluetoothDevice, UUID uuid, byte[] bArr, OnWriteSppDataCallback onWriteSppDataCallback) {
        SendSppDataThread sendSppDataThread = this.mSendSppDataThread;
        if (sendSppDataThread != null) {
            sendSppDataThread.addSendTask(new SendSppDataThread.SppSendTask(bluetoothDevice, uuid, bArr, onWriteSppDataCallback));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValidDevice(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.deviceEquals(this.mConnectingSppDevice, bluetoothDevice) || BluetoothUtil.deviceEquals(bluetoothDevice, this.mBondingDevice) || BluetoothUtil.deviceEquals(bluetoothDevice, this.mConnectedSppDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSppConnection(BluetoothDevice bluetoothDevice, UUID uuid, int i) {
        boolean zIsValidDevice = isValidDevice(bluetoothDevice);
        JL_Log.m848i(TAG, "-handleSppConnection- device = " + printDeviceInfo(bluetoothDevice) + ", isValidDevice = " + zIsValidDevice + ", sppUUID = " + uuid + ", status = " + i);
        if (zIsValidDevice) {
            if (i == 0 || i == 2) {
                if (BluetoothUtil.deviceEquals(bluetoothDevice, this.mConnectingSppDevice)) {
                    setConnectingSppDevice(null);
                }
                stopConnectTimeoutTask();
                if (i == 0) {
                    if (BluetoothUtil.deviceEquals(bluetoothDevice, this.mConnectedSppDevice)) {
                        stopReceiveSppDataThread(bluetoothDevice, uuid);
                        if (isConnectedSocketMapEmpty(bluetoothDevice)) {
                            stopSendSppDataThread();
                            setConnectedSppDevice(null);
                        }
                    }
                } else if (this.mConnectedSppDevice == null) {
                    setConnectedSppDevice(bluetoothDevice);
                }
            }
            this.mSppEventCallbackManager.onSppConnection(bluetoothDevice, uuid, i);
            if (this.useSppPrivateChannel) {
                if (i == 2) {
                    if (this.customSppUUID.equals(uuid)) {
                        return;
                    }
                    connectSpp(bluetoothDevice, this.customSppUUID);
                } else if (i == 0 && BluetoothUtil.deviceEquals(this.mConnectedSppDevice, bluetoothDevice)) {
                    disconnectSpp(this.mConnectedSppDevice, null);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSppDevice(BluetoothDevice bluetoothDevice) {
        return (!AppUtil.checkHasConnectPermission(this.mContext) || bluetoothDevice == null || bluetoothDevice.getType() == 2) ? false : true;
    }

    private class DiscoveryReceiver extends BroadcastReceiver {
        private DiscoveryReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action;
            if (intent == null || (action = intent.getAction()) == null) {
                return;
            }
            action.hashCode();
            switch (action) {
                case "android.bluetooth.adapter.action.DISCOVERY_FINISHED":
                    JL_Log.m844d(SppManager.TAG, "recv action : ACTION_DISCOVERY_FINISHED");
                    SppManager.this.stopScanTimeoutTask();
                    SppManager.this.unregisterDiscoveryReceiver();
                    SppManager.this.mSppEventCallbackManager.onDiscoveryDeviceChange(false);
                    break;
                case "android.bluetooth.adapter.action.DISCOVERY_STARTED":
                    JL_Log.m844d(SppManager.TAG, "recv action : ACTION_DISCOVERY_STARTED");
                    break;
                case "android.bluetooth.device.action.FOUND":
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    short shortExtra = intent.getShortExtra("android.bluetooth.device.extra.RSSI", (short) -1);
                    if (SppManager.this.isSppDevice(bluetoothDevice) && BluetoothUtil.isBluetoothEnable() && !SppManager.this.mDiscoveredEdrDevices.contains(bluetoothDevice)) {
                        SppManager.this.mDiscoveredEdrDevices.add(bluetoothDevice);
                        SppManager.this.mSppEventCallbackManager.onDiscoveryDevice(bluetoothDevice, shortExtra);
                        break;
                    }
                    break;
            }
        }
    }

    private class BluetoothReceiver extends BroadcastReceiver {
        private BluetoothReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action;
            int i;
            if (intent == null || (action = intent.getAction()) == null) {
                return;
            }
            action.hashCode();
            switch (action) {
                case "android.bluetooth.adapter.action.STATE_CHANGED":
                    int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", -1);
                    if (SppManager.this.mBtAdapter != null && intExtra == -1) {
                        intExtra = SppManager.this.mBtAdapter.getState();
                    }
                    if (intExtra != 10) {
                        if (intExtra == 12) {
                            SppManager.this.mSppEventCallbackManager.onAdapterChange(true);
                        }
                        break;
                    } else {
                        SppManager.this.mDiscoveredEdrDevices.clear();
                        SppManager.this.mSppEventCallbackManager.onDiscoveryDeviceChange(false);
                        SppManager sppManager = SppManager.this;
                        sppManager.disconnectSpp(sppManager.getConnectedSppDevice(), null);
                        SppManager.this.mSppEventCallbackManager.onAdapterChange(false);
                        break;
                    }
                    break;
                case "android.bluetooth.device.action.UUID":
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (bluetoothDevice != null) {
                        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("android.bluetooth.device.extra.UUID");
                        if (parcelableArrayExtra == null) {
                            JL_Log.m846e(SppManager.TAG, "recv action : ACTION_UUID >>> no uuids");
                        } else {
                            ParcelUuid[] parcelUuidArr = new ParcelUuid[parcelableArrayExtra.length];
                            for (i = 0; i < parcelableArrayExtra.length; i++) {
                                parcelUuidArr[i] = ParcelUuid.fromString(parcelableArrayExtra[i].toString());
                                JL_Log.m848i(SppManager.TAG, "recv action : ACTION_UUID >>> index = " + i + " uuid = " + parcelUuidArr[i]);
                            }
                        }
                        StringBuilder sb = new StringBuilder("recv action : ACTION_UUID >>> mConnectingSppDevice = ");
                        SppManager sppManager2 = SppManager.this;
                        JL_Log.m844d(SppManager.TAG, sb.append(sppManager2.printDeviceInfo(sppManager2.mConnectingSppDevice)).append(", device = ").append(SppManager.this.printDeviceInfo(bluetoothDevice)).toString());
                        if (BluetoothUtil.deviceEquals(SppManager.this.mConnectingSppDevice, bluetoothDevice)) {
                            SppManager sppManager3 = SppManager.this;
                            sppManager3.startConnectSppThread(bluetoothDevice, sppManager3.mSppUUID);
                        }
                        break;
                    }
                    break;
                case "android.bluetooth.device.action.BOND_STATE_CHANGED":
                    BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (bluetoothDevice2 != null && AppUtil.checkHasConnectPermission(context)) {
                        int bondState = bluetoothDevice2.getBondState();
                        boolean zIsValidDevice = SppManager.this.isValidDevice(bluetoothDevice2);
                        JL_Log.m848i(SppManager.TAG, "recv action : ACTION_BOND_STATE_CHANGED >>> device = " + SppManager.this.printDeviceInfo(bluetoothDevice2) + ", bond = " + bondState + ", isValidDevice = " + zIsValidDevice);
                        if ((bondState == 10 || bondState == 12) && zIsValidDevice) {
                            SppManager.this.stopPairTimeoutTask(bluetoothDevice2);
                            if (bondState == 12) {
                                SppManager sppManager4 = SppManager.this;
                                sppManager4.startConnectSppThread(bluetoothDevice2, sppManager4.mSppUUID);
                            } else {
                                SppManager sppManager5 = SppManager.this;
                                sppManager5.handleSppConnection(bluetoothDevice2, sppManager5.mSppUUID, 0);
                            }
                        }
                        break;
                    }
                    break;
            }
        }
    }
}
