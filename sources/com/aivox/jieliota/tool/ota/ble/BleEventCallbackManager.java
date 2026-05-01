package com.aivox.jieliota.tool.ota.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.os.Handler;
import android.os.Looper;
import com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback;
import com.aivox.jieliota.tool.ota.ble.model.BleScanInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class BleEventCallbackManager extends BleEventCallback {
    private final ArrayList<BleEventCallback> mCallbacks = new ArrayList<>();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public void registerBleEventCallback(BleEventCallback bleEventCallback) {
        if (bleEventCallback == null || this.mCallbacks.contains(bleEventCallback)) {
            return;
        }
        this.mCallbacks.add(bleEventCallback);
    }

    public void unregisterBleEventCallback(BleEventCallback bleEventCallback) {
        if (bleEventCallback == null || this.mCallbacks.isEmpty()) {
            return;
        }
        this.mCallbacks.remove(bleEventCallback);
    }

    public void release() {
        this.mCallbacks.clear();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
    public void onAdapterChange(final boolean z) {
        callbackBleEvent(new BleEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.BleEventCallbackImpl
            public void onCallback(BleEventCallback bleEventCallback) {
                bleEventCallback.onAdapterChange(z);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
    public void onDiscoveryBleChange(final boolean z) {
        callbackBleEvent(new BleEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.BleEventCallbackImpl
            public void onCallback(BleEventCallback bleEventCallback) {
                bleEventCallback.onDiscoveryBleChange(z);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
    public void onDiscoveryBle(final BluetoothDevice bluetoothDevice, final BleScanInfo bleScanInfo) {
        callbackBleEvent(new BleEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.BleEventCallbackImpl
            public void onCallback(BleEventCallback bleEventCallback) {
                bleEventCallback.onDiscoveryBle(bluetoothDevice, bleScanInfo);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
    public void onBleConnection(final BluetoothDevice bluetoothDevice, final int i) {
        callbackBleEvent(new BleEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.BleEventCallbackImpl
            public void onCallback(BleEventCallback bleEventCallback) {
                bleEventCallback.onBleConnection(bluetoothDevice, i);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
    public void onBleServiceDiscovery(final BluetoothDevice bluetoothDevice, final int i, final List<BluetoothGattService> list) {
        callbackBleEvent(new BleEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.BleEventCallbackImpl
            public void onCallback(BleEventCallback bleEventCallback) {
                bleEventCallback.onBleServiceDiscovery(bluetoothDevice, i, list);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
    public void onBleNotificationStatus(final BluetoothDevice bluetoothDevice, final UUID uuid, final UUID uuid2, final int i) {
        callbackBleEvent(new BleEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.6
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.BleEventCallbackImpl
            public void onCallback(BleEventCallback bleEventCallback) {
                bleEventCallback.onBleNotificationStatus(bluetoothDevice, uuid, uuid2, i);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
    public void onBleDataBlockChanged(final BluetoothDevice bluetoothDevice, final int i, final int i2) {
        callbackBleEvent(new BleEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.7
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.BleEventCallbackImpl
            public void onCallback(BleEventCallback bleEventCallback) {
                bleEventCallback.onBleDataBlockChanged(bluetoothDevice, i, i2);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
    public void onBleDataNotification(final BluetoothDevice bluetoothDevice, final UUID uuid, final UUID uuid2, final byte[] bArr) {
        callbackBleEvent(new BleEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.8
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.BleEventCallbackImpl
            public void onCallback(BleEventCallback bleEventCallback) {
                bleEventCallback.onBleDataNotification(bluetoothDevice, uuid, uuid2, bArr);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
    public void onBleWriteStatus(final BluetoothDevice bluetoothDevice, final UUID uuid, final UUID uuid2, final byte[] bArr, final int i) {
        callbackBleEvent(new BleEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.9
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.BleEventCallbackImpl
            public void onCallback(BleEventCallback bleEventCallback) {
                bleEventCallback.onBleWriteStatus(bluetoothDevice, uuid, uuid2, bArr, i);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.ble.interfaces.BleEventCallback, com.aivox.jieliota.tool.ota.ble.interfaces.IBleEventCallback
    public void onConnectionUpdated(final BluetoothDevice bluetoothDevice, final int i, final int i2, final int i3, final int i4) {
        callbackBleEvent(new BleEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.10
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.ble.BleEventCallbackManager.BleEventCallbackImpl
            public void onCallback(BleEventCallback bleEventCallback) {
                bleEventCallback.onConnectionUpdated(bluetoothDevice, i, i2, i3, i4);
            }
        });
    }

    private void callbackBleEvent(BleEventCallbackImpl bleEventCallbackImpl) {
        if (bleEventCallbackImpl == null) {
            return;
        }
        OnBleEventRunnable onBleEventRunnable = new OnBleEventRunnable(bleEventCallbackImpl);
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            onBleEventRunnable.run();
        } else {
            this.mHandler.post(onBleEventRunnable);
        }
    }

    private static abstract class BleEventCallbackImpl {
        public abstract void onCallback(BleEventCallback bleEventCallback);

        private BleEventCallbackImpl() {
        }
    }

    private class OnBleEventRunnable implements Runnable {
        private final BleEventCallbackImpl mImpl;

        public OnBleEventRunnable(BleEventCallbackImpl bleEventCallbackImpl) {
            this.mImpl = bleEventCallbackImpl;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (BleEventCallbackManager.this.mCallbacks.isEmpty() || this.mImpl == null) {
                return;
            }
            for (BleEventCallback bleEventCallback : new ArrayList(BleEventCallbackManager.this.mCallbacks)) {
                if (bleEventCallback != null) {
                    this.mImpl.onCallback(bleEventCallback);
                }
            }
        }
    }
}
