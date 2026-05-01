package com.aivox.jieliota.tool.bluetooth;

import android.bluetooth.BluetoothDevice;
import androidx.core.app.NotificationCompat;
import com.aivox.jieliota.tool.ota.ble.model.BleScanInfo;
import java.util.UUID;
import kotlin.Metadata;

/* JADX INFO: compiled from: OnBTEventCallback.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\"\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0016J\"\u0010\r\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0016J\u001c\u0010\u000f\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u000bH\u0016J.\u0010\u0015\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000e\u001a\u00020\u000b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016¨\u0006\u001a"}, m1901d2 = {"Lcom/aivox/jieliota/tool/bluetooth/OnBTEventCallback;", "", "()V", "onAdapterChange", "", "bEnabled", "", "onBleMtuChange", "device", "Landroid/bluetooth/BluetoothDevice;", "mtu", "", NotificationCompat.CATEGORY_STATUS, "onDeviceConnection", "way", "onDiscovery", "bleScanMessage", "Lcom/aivox/jieliota/tool/ota/ble/model/BleScanInfo;", "onDiscoveryChange", "bStart", "scanType", "onReceiveData", "uuid", "Ljava/util/UUID;", "data", "", "jieliota_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public class OnBTEventCallback {
    public void onAdapterChange(boolean bEnabled) {
    }

    public void onBleMtuChange(BluetoothDevice device, int mtu, int status) {
    }

    public void onDeviceConnection(BluetoothDevice device, int way, int status) {
    }

    public void onDiscovery(BluetoothDevice device, BleScanInfo bleScanMessage) {
    }

    public void onDiscoveryChange(boolean bStart, int scanType) {
    }

    public void onReceiveData(BluetoothDevice device, int way, UUID uuid, byte[] data) {
    }
}
