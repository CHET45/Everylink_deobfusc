package com.aivox.besota.sdk.scan;

import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.utils.DeviceProtocol;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public interface ScanManager {

    public interface ScanListener {
        void onDeviceOffline(HmDevice hmDevice);

        void onScanFailed(String str);

        void onScanResult(HmDevice hmDevice);
    }

    Collection<HmDevice> getDeviceList();

    boolean isScanInProgress(DeviceProtocol deviceProtocol);

    void startScan(Collection<DeviceProtocol> collection, ScanListener scanListener, ScanFilter scanFilter);

    void startScan(Collection<DeviceProtocol> collection, ScanListener scanListener, ScanFilter scanFilter, BleBroadCastParser bleBroadCastParser);

    void stopAllScan();

    void stopScan(Collection<DeviceProtocol> collection);
}
