package com.aivox.besota.sdk.connect;

import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.message.BaseMessage;
import com.aivox.besota.sdk.utils.DeviceProtocol;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface DeviceConnector {

    public interface ConnectionListener {
        void notifyWrite(int i);

        void onDataReceived(BaseMessage baseMessage);

        void onStatusChanged(HmDevice hmDevice, int i, DeviceProtocol deviceProtocol);
    }

    void connect(HmDevice hmDevice);

    void connect(HmDevice hmDevice, ConnectionListener connectionListener);

    void disconnect(HmDevice hmDevice);

    List<DeviceProtocol> getSupportedProtocols();

    boolean isProtocolSupported(DeviceProtocol deviceProtocol);

    void registerConnectionListener(ConnectionListener connectionListener);

    void unregisterConnectionListener(ConnectionListener connectionListener);
}
