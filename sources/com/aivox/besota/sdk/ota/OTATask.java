package com.aivox.besota.sdk.ota;

import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.utils.OTAStatus;

/* JADX INFO: loaded from: classes.dex */
public interface OTATask {

    public interface StatusListener {
        void onOTAProgressChanged(int i, HmDevice hmDevice);

        void onOTAStatusChanged(OTAStatus oTAStatus, HmDevice hmDevice);
    }

    boolean applyNewFirmware(int i);

    int getCurrentProgress();

    HmDevice getDevice();

    OTAStatus getOTAStatus();

    RemoteOTAConfig getOtaConfig();

    boolean pausedDataTransfer();

    void postTransferCleanup();

    void preTransferInit();

    void registerOTAStatusListener(StatusListener statusListener);

    OTADfuInfo requestDFUInfo();

    void setOtaConfig(RemoteOTAConfig remoteOTAConfig);

    boolean startDataTransfer(OTADfuInfo oTADfuInfo, int i, boolean z, StatusListener statusListener);

    boolean startDataTransfer(OTADfuInfo oTADfuInfo, StatusListener statusListener);

    boolean stopDataTransfer();

    void unregisterOTAStatusListener(StatusListener statusListener);
}
