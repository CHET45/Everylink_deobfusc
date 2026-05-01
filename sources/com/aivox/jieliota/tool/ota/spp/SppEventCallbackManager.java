package com.aivox.jieliota.tool.ota.spp;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;
import com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback;
import com.aivox.jieliota.tool.ota.spp.interfaces.SppEventCallback;
import java.util.ArrayList;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class SppEventCallbackManager implements ISppEventCallback {
    private final ArrayList<SppEventCallback> mCallbacks = new ArrayList<>();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public void registerSppEventCallback(SppEventCallback sppEventCallback) {
        if (sppEventCallback == null || this.mCallbacks.contains(sppEventCallback)) {
            return;
        }
        this.mCallbacks.add(sppEventCallback);
    }

    public void unregisterSppEventCallback(SppEventCallback sppEventCallback) {
        if (sppEventCallback == null || this.mCallbacks.isEmpty()) {
            return;
        }
        this.mCallbacks.remove(sppEventCallback);
    }

    public void release() {
        this.mCallbacks.clear();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
    public void onAdapterChange(final boolean z) {
        callbackSppEvent(new SppEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.spp.SppEventCallbackManager.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.spp.SppEventCallbackManager.SppEventCallbackImpl
            public void onCallback(SppEventCallback sppEventCallback) {
                sppEventCallback.onAdapterChange(z);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
    public void onDiscoveryDeviceChange(final boolean z) {
        callbackSppEvent(new SppEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.spp.SppEventCallbackManager.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.spp.SppEventCallbackManager.SppEventCallbackImpl
            public void onCallback(SppEventCallback sppEventCallback) {
                sppEventCallback.onDiscoveryDeviceChange(z);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
    public void onDiscoveryDevice(final BluetoothDevice bluetoothDevice, final int i) {
        callbackSppEvent(new SppEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.spp.SppEventCallbackManager.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.spp.SppEventCallbackManager.SppEventCallbackImpl
            public void onCallback(SppEventCallback sppEventCallback) {
                sppEventCallback.onDiscoveryDevice(bluetoothDevice, i);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
    public void onSppConnection(final BluetoothDevice bluetoothDevice, final UUID uuid, final int i) {
        callbackSppEvent(new SppEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.spp.SppEventCallbackManager.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.spp.SppEventCallbackManager.SppEventCallbackImpl
            public void onCallback(SppEventCallback sppEventCallback) {
                sppEventCallback.onSppConnection(bluetoothDevice, uuid, i);
            }
        });
    }

    @Override // com.aivox.jieliota.tool.ota.spp.interfaces.ISppEventCallback
    public void onReceiveSppData(final BluetoothDevice bluetoothDevice, final UUID uuid, final byte[] bArr) {
        callbackSppEvent(new SppEventCallbackImpl() { // from class: com.aivox.jieliota.tool.ota.spp.SppEventCallbackManager.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.aivox.jieliota.tool.ota.spp.SppEventCallbackManager.SppEventCallbackImpl
            public void onCallback(SppEventCallback sppEventCallback) {
                sppEventCallback.onReceiveSppData(bluetoothDevice, uuid, bArr);
            }
        });
    }

    private void callbackSppEvent(SppEventCallbackImpl sppEventCallbackImpl) {
        if (sppEventCallbackImpl == null) {
            return;
        }
        OnSppEventRunnable onSppEventRunnable = new OnSppEventRunnable(sppEventCallbackImpl);
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            onSppEventRunnable.run();
        } else {
            this.mHandler.post(onSppEventRunnable);
        }
    }

    private static abstract class SppEventCallbackImpl {
        public abstract void onCallback(SppEventCallback sppEventCallback);

        private SppEventCallbackImpl() {
        }
    }

    private class OnSppEventRunnable implements Runnable {
        private final SppEventCallbackImpl mImpl;

        public OnSppEventRunnable(SppEventCallbackImpl sppEventCallbackImpl) {
            this.mImpl = sppEventCallbackImpl;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SppEventCallbackManager.this.mCallbacks.isEmpty() || this.mImpl == null) {
                return;
            }
            for (SppEventCallback sppEventCallback : new ArrayList(SppEventCallbackManager.this.mCallbacks)) {
                if (sppEventCallback != null) {
                    this.mImpl.onCallback(sppEventCallback);
                }
            }
        }
    }
}
