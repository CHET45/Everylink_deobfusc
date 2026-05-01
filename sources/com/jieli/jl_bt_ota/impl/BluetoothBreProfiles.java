package com.jieli.jl_bt_ota.impl;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.text.TextUtils;
import com.jieli.jl_bt_ota.constant.BluetoothConstant;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BluetoothBreProfiles extends BluetoothDiscovery {

    /* JADX INFO: renamed from: q */
    private BluetoothHeadset f615q;

    /* JADX INFO: renamed from: r */
    private BluetoothA2dp f616r;

    /* JADX INFO: renamed from: s */
    private BluetoothHandFreeReceiver f617s;

    /* JADX INFO: renamed from: t */
    private final BluetoothProfile.ServiceListener f618t;

    private class BluetoothHandFreeReceiver extends BroadcastReceiver {
        private BluetoothHandFreeReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int i;
            if (intent != null) {
                String action = intent.getAction();
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (TextUtils.isEmpty(action) || bluetoothDevice == null) {
                    return;
                }
                String str = (String) Objects.requireNonNull(action);
                str.hashCode();
                switch (str) {
                    case "android.bluetooth.device.action.UUID":
                        Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("android.bluetooth.device.extra.UUID");
                        if (parcelableArrayExtra == null) {
                            JL_Log.m849i(BluetoothBreProfiles.this.TAG, "ACTION_UUID", "No uuids.");
                            break;
                        } else {
                            ParcelUuid[] parcelUuidArr = new ParcelUuid[parcelableArrayExtra.length];
                            for (i = 0; i < parcelableArrayExtra.length; i++) {
                                parcelUuidArr[i] = ParcelUuid.fromString(parcelableArrayExtra[i].toString());
                                JL_Log.m845d(BluetoothBreProfiles.this.TAG, "ACTION_UUID", "uuid : " + parcelUuidArr[i].toString());
                            }
                            break;
                        }
                        break;
                    case "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED":
                        try {
                            int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                            JL_Log.m849i(BluetoothBreProfiles.this.TAG, "HFP#ACTION_CONNECTION_STATE_CHANGED", "device : " + BluetoothBreProfiles.this.printBtDeviceInfo(bluetoothDevice) + ", state : " + intExtra);
                            BluetoothBreProfiles.this.onHfpStatus(bluetoothDevice, intExtra);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                        break;
                    case "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED":
                        try {
                            int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                            JL_Log.m849i(BluetoothBreProfiles.this.TAG, "A2DP#ACTION_CONNECTION_STATE_CHANGED", "device : " + BluetoothBreProfiles.this.printBtDeviceInfo(bluetoothDevice) + ", state : " + intExtra2);
                            BluetoothBreProfiles.this.onA2dpStatus(bluetoothDevice, intExtra2);
                            break;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                        break;
                }
            }
        }
    }

    public BluetoothBreProfiles(Context context) {
        super(context);
        this.f618t = new BluetoothProfile.ServiceListener() { // from class: com.jieli.jl_bt_ota.impl.BluetoothBreProfiles.1
            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                JL_Log.m849i(BluetoothBreProfiles.this.TAG, "onServiceConnected", "profile : " + i);
                if (2 == i) {
                    BluetoothBreProfiles.this.f616r = (BluetoothA2dp) bluetoothProfile;
                } else if (1 == i) {
                    BluetoothBreProfiles.this.f615q = (BluetoothHeadset) bluetoothProfile;
                }
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceDisconnected(int i) {
                JL_Log.m849i(BluetoothBreProfiles.this.TAG, "onServiceDisconnected", "profile : " + i);
                if (2 == i) {
                    BluetoothBreProfiles.this.f616r = null;
                } else if (1 == i) {
                    BluetoothBreProfiles.this.f615q = null;
                }
            }
        };
        m609a(context);
        m608a();
    }

    /* JADX INFO: renamed from: b */
    private boolean m612b(String str, BluetoothDevice bluetoothDevice) {
        if (m613c(str, bluetoothDevice)) {
            return true;
        }
        if (this.f615q != null) {
            return false;
        }
        JL_Log.m853w(this.TAG, str, "No hfp manager.");
        return true;
    }

    /* JADX INFO: renamed from: c */
    private boolean m613c(String str, BluetoothDevice bluetoothDevice) {
        if (!CommonUtil.checkHasConnectPermission(this.context)) {
            JL_Log.m853w(this.TAG, str, "Missing connect permissions.");
            return true;
        }
        if (bluetoothDevice == null) {
            JL_Log.m853w(this.TAG, str, "Device is null.");
            return true;
        }
        if (BluetoothUtil.isBluetoothEnable()) {
            return false;
        }
        JL_Log.m853w(this.TAG, str, "Bluetooth is off.");
        return true;
    }

    public boolean deviceHasA2dp(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.deviceHasProfile(this.context, bluetoothDevice, BluetoothConstant.UUID_A2DP);
    }

    public boolean deviceHasHfp(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.deviceHasProfile(this.context, bluetoothDevice, BluetoothConstant.UUID_HFP);
    }

    public boolean disconnectByProfiles(BluetoothDevice bluetoothDevice) {
        boolean zDisconnectFromHfp = false;
        if (m613c("disconnectByProfiles", bluetoothDevice)) {
            return false;
        }
        JL_Log.m845d(this.TAG, "disconnectByProfiles", "device : " + printBtDeviceInfo(bluetoothDevice));
        if (bluetoothDevice.getType() != 2) {
            int iIsConnectedByA2dp = isConnectedByA2dp(bluetoothDevice);
            if (iIsConnectedByA2dp == 2) {
                zDisconnectFromHfp = disconnectFromA2dp(bluetoothDevice);
                JL_Log.m849i(this.TAG, "disconnectByProfiles", "disconnectFromA2dp : " + zDisconnectFromHfp);
            }
            int iIsConnectedByHfp = isConnectedByHfp(bluetoothDevice);
            if (iIsConnectedByHfp == 2) {
                zDisconnectFromHfp = disconnectFromHfp(bluetoothDevice);
                JL_Log.m849i(this.TAG, "disconnectByProfiles", "disconnectFromHfp : " + zDisconnectFromHfp);
            }
            if (iIsConnectedByA2dp == 0 && iIsConnectedByHfp == 0) {
                JL_Log.m845d(this.TAG, "disconnectByProfiles", "Classic Bluetooth is disconnected.");
                return true;
            }
        }
        return zDisconnectFromHfp;
    }

    public boolean disconnectFromA2dp(BluetoothDevice bluetoothDevice) {
        if (m610a("disconnectFromA2dp", bluetoothDevice)) {
            return false;
        }
        int iIsConnectedByA2dp = isConnectedByA2dp(bluetoothDevice);
        JL_Log.m845d(this.TAG, "disconnectFromA2dp", "deviceA2dpStatus : " + iIsConnectedByA2dp);
        if (iIsConnectedByA2dp == 0) {
            JL_Log.m845d(this.TAG, "disconnectFromA2dp", "A2dp is disconnected.");
            return true;
        }
        boolean zDisconnectDeviceA2dp = iIsConnectedByA2dp == 2 ? BluetoothUtil.disconnectDeviceA2dp(this.context, this.f616r, bluetoothDevice) : false;
        JL_Log.m845d(this.TAG, "disconnectFromA2dp", " -------------> " + zDisconnectDeviceA2dp);
        return zDisconnectDeviceA2dp;
    }

    public boolean disconnectFromHfp(BluetoothDevice bluetoothDevice) {
        if (m612b("disconnectFromHfp", bluetoothDevice)) {
            return false;
        }
        int iIsConnectedByHfp = isConnectedByHfp(bluetoothDevice);
        JL_Log.m845d(this.TAG, "disconnectFromHfp", "deviceHfpStatus : " + iIsConnectedByHfp);
        if (iIsConnectedByHfp == 0) {
            JL_Log.m849i(this.TAG, "disconnectFromHfp", "Hfp is disconnected");
            return true;
        }
        boolean zDisconnectDeviceHfp = iIsConnectedByHfp == 2 ? BluetoothUtil.disconnectDeviceHfp(this.context, this.f615q, bluetoothDevice) : false;
        JL_Log.m849i(this.TAG, "disconnectFromHfp", " ----> " + zDisconnectDeviceHfp);
        return zDisconnectDeviceHfp;
    }

    protected BluetoothHeadset getBluetoothHfp() {
        return this.f615q;
    }

    public List<BluetoothDevice> getDevicesConnectedByProfile() {
        if (!CommonUtil.checkHasConnectPermission(this.context)) {
            return null;
        }
        BluetoothHeadset bluetoothHeadset = this.f615q;
        List<BluetoothDevice> connectedDevices = bluetoothHeadset != null ? bluetoothHeadset.getConnectedDevices() : null;
        ArrayList arrayList = connectedDevices != null ? new ArrayList(connectedDevices) : null;
        BluetoothA2dp bluetoothA2dp = this.f616r;
        if (bluetoothA2dp != null) {
            connectedDevices = bluetoothA2dp.getConnectedDevices();
        }
        if (connectedDevices != null) {
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            arrayList.addAll(connectedDevices);
        }
        return arrayList;
    }

    protected BluetoothA2dp getmBluetoothA2dp() {
        return this.f616r;
    }

    public int isConnectedByA2dp(BluetoothDevice bluetoothDevice) {
        if (m610a("isConnectedByA2dp", bluetoothDevice)) {
            return 0;
        }
        List<BluetoothDevice> connectedDevices = this.f616r.getConnectedDevices();
        if (connectedDevices != null) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            while (it.hasNext()) {
                if (it.next().getAddress().equals(bluetoothDevice.getAddress())) {
                    JL_Log.m845d(this.TAG, "isConnectedByA2dp", " ----> Connected");
                    return 2;
                }
            }
        }
        int connectionState = this.f616r.getConnectionState(bluetoothDevice);
        JL_Log.m845d(this.TAG, "isConnectedByA2dp", "state : " + connectionState);
        return connectionState;
    }

    public int isConnectedByHfp(BluetoothDevice bluetoothDevice) {
        if (m612b("isConnectedByHfp", bluetoothDevice)) {
            return 0;
        }
        List<BluetoothDevice> connectedDevices = this.f615q.getConnectedDevices();
        if (connectedDevices != null) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            while (it.hasNext()) {
                if (it.next().getAddress().equals(bluetoothDevice.getAddress())) {
                    JL_Log.m845d(this.TAG, "isConnectedByHfp", "Hfp service is connected.");
                    return 2;
                }
            }
        }
        int connectionState = this.f615q.getConnectionState(bluetoothDevice);
        JL_Log.m845d(this.TAG, "isConnectedByHfp", "state : " + connectionState);
        return connectionState;
    }

    public int isConnectedByProfile(BluetoothDevice bluetoothDevice) {
        if (m613c("isConnectedByProfile", bluetoothDevice)) {
            return 0;
        }
        BluetoothHeadset bluetoothHeadset = this.f615q;
        if (bluetoothHeadset == null || this.f616r == null) {
            JL_Log.m853w(this.TAG, "isConnectedByProfile", "No hfp manager or a2dp manager.");
            return 0;
        }
        List<BluetoothDevice> connectedDevices = bluetoothHeadset.getConnectedDevices();
        if (connectedDevices != null) {
            Iterator<BluetoothDevice> it = connectedDevices.iterator();
            while (it.hasNext()) {
                if (it.next().getAddress().equals(bluetoothDevice.getAddress())) {
                    JL_Log.m845d(this.TAG, "isConnectedByProfile", "Hfp service is connected.");
                    return 2;
                }
            }
        }
        List<BluetoothDevice> connectedDevices2 = this.f616r.getConnectedDevices();
        if (connectedDevices2 != null) {
            Iterator<BluetoothDevice> it2 = connectedDevices2.iterator();
            while (it2.hasNext()) {
                if (it2.next().getAddress().equals(bluetoothDevice.getAddress())) {
                    JL_Log.m845d(this.TAG, "isConnectedByProfile", "A2dp service is connected.");
                    return 2;
                }
            }
        }
        return 0;
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothDiscovery, com.jieli.jl_bt_ota.impl.BluetoothBase, com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void release() {
        BluetoothAdapter bluetoothAdapter;
        BluetoothAdapter bluetoothAdapter2;
        super.release();
        m611b();
        BluetoothA2dp bluetoothA2dp = this.f616r;
        if (bluetoothA2dp != null && (bluetoothAdapter2 = this.mBluetoothAdapter) != null) {
            bluetoothAdapter2.closeProfileProxy(2, bluetoothA2dp);
            this.f616r = null;
        }
        BluetoothHeadset bluetoothHeadset = this.f615q;
        if (bluetoothHeadset == null || (bluetoothAdapter = this.mBluetoothAdapter) == null) {
            return;
        }
        bluetoothAdapter.closeProfileProxy(1, bluetoothHeadset);
        this.f615q = null;
    }

    /* JADX INFO: renamed from: a */
    private void m609a(Context context) {
        if (context == null) {
            return;
        }
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            JL_Log.m847e(this.TAG, "init", "The device does not support Bluetooth function.");
            return;
        }
        if (this.f616r == null) {
            try {
                if (!bluetoothAdapter.getProfileProxy(context, this.f618t, 2)) {
                    JL_Log.m847e(this.TAG, "init", "Failed to obtain a2dp manager.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.f615q == null) {
            try {
                if (this.mBluetoothAdapter.getProfileProxy(context, this.f618t, 1)) {
                    return;
                }
                JL_Log.m847e(this.TAG, "init", "Failed to obtain hfp manager.");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: renamed from: b */
    private void m611b() {
        Context context;
        BluetoothHandFreeReceiver bluetoothHandFreeReceiver = this.f617s;
        if (bluetoothHandFreeReceiver == null || (context = this.context) == null) {
            return;
        }
        context.unregisterReceiver(bluetoothHandFreeReceiver);
        this.f617s = null;
    }

    protected boolean disconnectFromA2dp(String str) {
        return disconnectFromA2dp(BluetoothUtil.getRemoteDevice(str));
    }

    public boolean disconnectFromHfp(String str) {
        return disconnectFromHfp(BluetoothUtil.getRemoteDevice(str));
    }

    /* JADX INFO: renamed from: a */
    private boolean m610a(String str, BluetoothDevice bluetoothDevice) {
        if (m613c(str, bluetoothDevice)) {
            return true;
        }
        if (this.f616r != null) {
            return false;
        }
        JL_Log.m853w(this.TAG, str, "No a2dp manager.");
        return true;
    }

    /* JADX INFO: renamed from: a */
    private void m608a() {
        if (this.f617s != null || this.context == null) {
            return;
        }
        this.f617s = new BluetoothHandFreeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.device.action.UUID");
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
        this.context.registerReceiver(this.f617s, intentFilter);
    }
}
