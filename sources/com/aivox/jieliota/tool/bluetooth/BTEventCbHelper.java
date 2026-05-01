package com.aivox.jieliota.tool.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import com.aivox.jieliota.tool.ota.ble.model.BleScanInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BTEventCbHelper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\nH\u0002J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0016J\"\u0010\u000e\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\"\u0010\u0014\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u001c\u0010\u0016\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u0012H\u0016J.\u0010\u001c\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0015\u001a\u00020\u00122\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\u000e\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\u0001J\u0006\u0010#\u001a\u00020\bJ\u000e\u0010$\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\u0001R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, m1901d2 = {"Lcom/aivox/jieliota/tool/bluetooth/BTEventCbHelper;", "Lcom/aivox/jieliota/tool/bluetooth/OnBTEventCallback;", "()V", "callbacks", "", "uiHandler", "Landroid/os/Handler;", "callbackEvent", "", "impl", "Lcom/aivox/jieliota/tool/bluetooth/CallbackImpl;", "onAdapterChange", "bEnabled", "", "onBleMtuChange", "device", "Landroid/bluetooth/BluetoothDevice;", "mtu", "", NotificationCompat.CATEGORY_STATUS, "onDeviceConnection", "way", "onDiscovery", "bleScanMessage", "Lcom/aivox/jieliota/tool/ota/ble/model/BleScanInfo;", "onDiscoveryChange", "bStart", "scanType", "onReceiveData", "uuid", "Ljava/util/UUID;", "data", "", "registerCallback", "callback", "release", "unregisterCallback", "jieliota_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class BTEventCbHelper extends OnBTEventCallback {
    private final List<OnBTEventCallback> callbacks = new ArrayList();
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public final void registerCallback(OnBTEventCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (this.callbacks.contains(callback)) {
            return;
        }
        this.callbacks.add(callback);
    }

    public final void unregisterCallback(OnBTEventCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        if (this.callbacks.isEmpty()) {
            return;
        }
        this.callbacks.remove(callback);
    }

    public final void release() {
        this.callbacks.clear();
        this.uiHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.aivox.jieliota.tool.bluetooth.OnBTEventCallback
    public void onAdapterChange(final boolean bEnabled) {
        callbackEvent(new CallbackImpl<OnBTEventCallback>() { // from class: com.aivox.jieliota.tool.bluetooth.BTEventCbHelper.onAdapterChange.1
            @Override // com.aivox.jieliota.tool.bluetooth.CallbackImpl
            public void onCallback(OnBTEventCallback callback) {
                Intrinsics.checkNotNullParameter(callback, "callback");
                callback.onAdapterChange(bEnabled);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.bluetooth.OnBTEventCallback
    public void onDiscoveryChange(final boolean bStart, final int scanType) {
        callbackEvent(new CallbackImpl<OnBTEventCallback>() { // from class: com.aivox.jieliota.tool.bluetooth.BTEventCbHelper.onDiscoveryChange.1
            @Override // com.aivox.jieliota.tool.bluetooth.CallbackImpl
            public void onCallback(OnBTEventCallback callback) {
                Intrinsics.checkNotNullParameter(callback, "callback");
                callback.onDiscoveryChange(bStart, scanType);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.bluetooth.OnBTEventCallback
    public void onDiscovery(final BluetoothDevice device, final BleScanInfo bleScanMessage) {
        callbackEvent(new CallbackImpl<OnBTEventCallback>() { // from class: com.aivox.jieliota.tool.bluetooth.BTEventCbHelper.onDiscovery.1
            @Override // com.aivox.jieliota.tool.bluetooth.CallbackImpl
            public void onCallback(OnBTEventCallback callback) {
                Intrinsics.checkNotNullParameter(callback, "callback");
                callback.onDiscovery(device, bleScanMessage);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.bluetooth.OnBTEventCallback
    public void onDeviceConnection(final BluetoothDevice device, final int way, final int status) {
        callbackEvent(new CallbackImpl<OnBTEventCallback>() { // from class: com.aivox.jieliota.tool.bluetooth.BTEventCbHelper.onDeviceConnection.1
            @Override // com.aivox.jieliota.tool.bluetooth.CallbackImpl
            public void onCallback(OnBTEventCallback callback) {
                Intrinsics.checkNotNullParameter(callback, "callback");
                callback.onDeviceConnection(device, way, status);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.bluetooth.OnBTEventCallback
    public void onReceiveData(final BluetoothDevice device, final int way, final UUID uuid, final byte[] data) {
        callbackEvent(new CallbackImpl<OnBTEventCallback>() { // from class: com.aivox.jieliota.tool.bluetooth.BTEventCbHelper.onReceiveData.1
            @Override // com.aivox.jieliota.tool.bluetooth.CallbackImpl
            public void onCallback(OnBTEventCallback callback) {
                Intrinsics.checkNotNullParameter(callback, "callback");
                callback.onReceiveData(device, way, uuid, data);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.bluetooth.OnBTEventCallback
    public void onBleMtuChange(final BluetoothDevice device, final int mtu, final int status) {
        callbackEvent(new CallbackImpl<OnBTEventCallback>() { // from class: com.aivox.jieliota.tool.bluetooth.BTEventCbHelper.onBleMtuChange.1
            @Override // com.aivox.jieliota.tool.bluetooth.CallbackImpl
            public void onCallback(OnBTEventCallback callback) {
                Intrinsics.checkNotNullParameter(callback, "callback");
                callback.onBleMtuChange(device, mtu, status);
            }
        });
    }

    private final void callbackEvent(CallbackImpl<OnBTEventCallback> impl) {
        if (impl == null) {
            return;
        }
        CallbackRunnable callbackRunnable = new CallbackRunnable(this.callbacks, impl);
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            callbackRunnable.run();
        } else {
            this.uiHandler.post(callbackRunnable);
        }
    }
}
