package com.aivox.besota.bessdk.connect;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import com.aivox.base.util.LogUtil;
import com.aivox.besota.bessdk.BesSdkConstants;
import com.aivox.besota.bessdk.scan.BtHeleper;
import com.aivox.besota.bessdk.service.base.BesServiceConfig;
import com.aivox.besota.bessdk.utils.ArrayUtil;
import com.aivox.besota.sdk.connect.DeviceConnector;
import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.message.BaseMessage;
import com.aivox.besota.sdk.utils.DeviceProtocol;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class SppConnector implements DeviceConnector {
    private static List<BluetoothSocket> mBluetoothSockets;
    private static List<DeviceConnector.ConnectionListener> mConnectListeners;
    private static List<ConnectedRunnable> mConnectedRunnables;
    private static Context mContext;
    private static List<HmDevice> mHmDevices;
    private static List<BesServiceConfig> mServiceConfigs;
    private static List<byte[]> mTotaAesKeys;
    private static volatile SppConnector sConnector;
    private final String TAG = getClass().getSimpleName();
    private Object mListenerLock = new Object();

    public static SppConnector getsConnector(Context context, BesServiceConfig besServiceConfig) {
        if (sConnector == null) {
            synchronized (SppConnector.class) {
                if (sConnector == null) {
                    sConnector = new SppConnector();
                    mBluetoothSockets = new ArrayList();
                    mConnectListeners = new ArrayList();
                    mServiceConfigs = new ArrayList();
                    mHmDevices = new ArrayList();
                    mConnectedRunnables = new ArrayList();
                    mTotaAesKeys = new ArrayList();
                }
            }
        }
        if (context != null) {
            mContext = context;
        }
        if (besServiceConfig != null && besServiceConfig.getDevice() != null && sConnector.isNewDeviceWithConfig(besServiceConfig)) {
            mHmDevices.add(besServiceConfig.getDevice());
            mServiceConfigs.add(besServiceConfig);
            mConnectListeners.add(null);
            mBluetoothSockets.add(null);
            mConnectedRunnables.add(null);
            mTotaAesKeys.add(null);
        }
        if (besServiceConfig != null) {
            ArrayUtil.addObjectIndex(mServiceConfigs, besServiceConfig, sConnector.getCurIndex(sConnector.getCurDevice(besServiceConfig.getDevice())));
        }
        return sConnector;
    }

    public ArrayList<HmDevice> getCurConnectDevices() {
        ArrayList<HmDevice> arrayList = new ArrayList<>();
        for (int i = 0; i < mHmDevices.size(); i++) {
            BesServiceConfig besServiceConfig = mServiceConfigs.get(i);
            if (mConnectedRunnables.get(i) != null && besServiceConfig != null && (!besServiceConfig.getTotaConnect().booleanValue() || mTotaAesKeys.get(i) != null)) {
                arrayList.add(mHmDevices.get(i));
            }
        }
        return arrayList;
    }

    public BesSdkConstants.BesConnectState getDeviceConnectState(BesServiceConfig besServiceConfig) {
        if (besServiceConfig.getDevice() == null || besServiceConfig.getServiceUUID() == null) {
            return BesSdkConstants.BesConnectState.BES_CONFIG_ERROR;
        }
        BluetoothDevice curDevice = getCurDevice(besServiceConfig.getDevice());
        BesServiceConfig curServiceConfig = getCurServiceConfig(curDevice);
        if (!isNewDeviceWithConfig(besServiceConfig) && getCurBluetoothSocket(curDevice) != null && getCurConnectedRunnable(curDevice) != null && curServiceConfig != null && (!curServiceConfig.getTotaConnect().booleanValue() || getCurTotaAesKey(curDevice) != null)) {
            if (besServiceConfig.getServiceUUID().toString().equals(curServiceConfig.getServiceUUID().toString())) {
                return BesSdkConstants.BesConnectState.BES_CONNECT;
            }
            return curServiceConfig.getTotaConnect().booleanValue() ? BesSdkConstants.BesConnectState.BES_CONNECT_TOTA : BesSdkConstants.BesConnectState.BES_CONNECT_NOTOTA;
        }
        return BesSdkConstants.BesConnectState.BES_NO_CONNECT;
    }

    private boolean isNewDeviceWithConfig(BesServiceConfig besServiceConfig) {
        HmDevice device = besServiceConfig.getDevice();
        for (int i = 0; i < mHmDevices.size(); i++) {
            HmDevice hmDevice = mHmDevices.get(i);
            if (besServiceConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_SPP && device.getDeviceMAC().equals(hmDevice.getDeviceMAC())) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCurIndex(BluetoothDevice bluetoothDevice) {
        for (int i = 0; i < mHmDevices.size(); i++) {
            HmDevice hmDevice = mHmDevices.get(i);
            if ((hmDevice.getPreferredProtocol() == DeviceProtocol.PROTOCOL_BLE ? hmDevice.getBleAddress() : hmDevice.getDeviceMAC()).equals(bluetoothDevice.getAddress())) {
                return i;
            }
        }
        return 10000;
    }

    public void refreshConnectListener(DeviceConnector.ConnectionListener connectionListener, BluetoothDevice bluetoothDevice) {
        ArrayUtil.addObjectIndex(mConnectListeners, connectionListener, getCurIndex(bluetoothDevice));
    }

    public void saveTotaAesKey(byte[] bArr, BluetoothDevice bluetoothDevice) {
        ArrayUtil.addObjectIndex(mTotaAesKeys, bArr, getCurIndex(bluetoothDevice));
    }

    public byte[] getTotaAesKey(BluetoothDevice bluetoothDevice) {
        return getCurTotaAesKey(bluetoothDevice);
    }

    private byte[] getCurTotaAesKey(BluetoothDevice bluetoothDevice) {
        if (mTotaAesKeys.size() < getCurIndex(bluetoothDevice)) {
            return null;
        }
        return mTotaAesKeys.get(getCurIndex(bluetoothDevice));
    }

    private BluetoothDevice getCurDevice(HmDevice hmDevice) {
        return BtHeleper.getBluetoothAdapter(mContext).getRemoteDevice(hmDevice.getPreferredProtocol() == DeviceProtocol.PROTOCOL_BLE ? hmDevice.getBleAddress() : hmDevice.getDeviceMAC());
    }

    private HmDevice getCurHmDevice(BluetoothDevice bluetoothDevice) {
        if (mHmDevices.size() < getCurIndex(bluetoothDevice)) {
            return null;
        }
        return mHmDevices.get(getCurIndex(bluetoothDevice));
    }

    private ConnectedRunnable getCurConnectedRunnable(BluetoothDevice bluetoothDevice) {
        if (mConnectedRunnables.size() < getCurIndex(bluetoothDevice)) {
            return null;
        }
        return mConnectedRunnables.get(getCurIndex(bluetoothDevice));
    }

    private BluetoothSocket getCurBluetoothSocket(BluetoothDevice bluetoothDevice) {
        if (mBluetoothSockets.size() < getCurIndex(bluetoothDevice)) {
            return null;
        }
        return mBluetoothSockets.get(getCurIndex(bluetoothDevice));
    }

    private BesServiceConfig getCurServiceConfig(BluetoothDevice bluetoothDevice) {
        if (mServiceConfigs.size() < getCurIndex(bluetoothDevice)) {
            return null;
        }
        return mServiceConfigs.get(getCurIndex(bluetoothDevice));
    }

    private DeviceConnector.ConnectionListener getCurListener(BluetoothDevice bluetoothDevice) {
        if (mConnectListeners.size() < getCurIndex(bluetoothDevice)) {
            return null;
        }
        return mConnectListeners.get(getCurIndex(bluetoothDevice));
    }

    @Override // com.aivox.besota.sdk.connect.DeviceConnector
    public List<DeviceProtocol> getSupportedProtocols() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(DeviceProtocol.PROTOCOL_SPP);
        return arrayList;
    }

    @Override // com.aivox.besota.sdk.connect.DeviceConnector
    public boolean isProtocolSupported(DeviceProtocol deviceProtocol) {
        return deviceProtocol == DeviceProtocol.PROTOCOL_SPP;
    }

    @Override // com.aivox.besota.sdk.connect.DeviceConnector
    public void connect(HmDevice hmDevice) {
        if (hmDevice == null || mContext == null) {
            return;
        }
        startConnect(null, hmDevice);
    }

    @Override // com.aivox.besota.sdk.connect.DeviceConnector
    public void connect(HmDevice hmDevice, DeviceConnector.ConnectionListener connectionListener) {
        if (hmDevice == null || connectionListener == null || mContext == null) {
            return;
        }
        startConnect(connectionListener, hmDevice);
    }

    private void startConnect(DeviceConnector.ConnectionListener connectionListener, HmDevice hmDevice) {
        LOG(this.TAG, "startConnect: -----spp");
        BluetoothDevice remoteDevice = BtHeleper.getBluetoothAdapter(mContext).getRemoteDevice(hmDevice.getDeviceMAC());
        new Thread(new ConnectRunnable(remoteDevice, getCurServiceConfig(remoteDevice).getServiceUUID())).start();
        ArrayUtil.addObjectIndex(mConnectListeners, connectionListener, getCurIndex(remoteDevice));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onConnectionStateChanged(boolean z, BluetoothDevice bluetoothDevice) {
        DeviceConnector.ConnectionListener curListener = getCurListener(bluetoothDevice);
        if (curListener != null) {
            curListener.onStatusChanged(getCurHmDevice(bluetoothDevice), z ? 666 : BesSdkConstants.BES_CONNECT_ERROR, DeviceProtocol.PROTOCOL_SPP);
        }
        if (z) {
            return;
        }
        close(bluetoothDevice);
    }

    public boolean write(byte[] bArr, BluetoothDevice bluetoothDevice) {
        LOG(this.TAG, "spp write: -------" + bluetoothDevice.getAddress());
        LOG(this.TAG, "spp write: -------" + bArr.length);
        ConnectedRunnable curConnectedRunnable = getCurConnectedRunnable(bluetoothDevice);
        if (curConnectedRunnable == null) {
            return false;
        }
        LOG(this.TAG, "spp write: -------" + ArrayUtil.toHex(bArr));
        return curConnectedRunnable.write(bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReceive(byte[] bArr, BluetoothDevice bluetoothDevice) {
        LOG(this.TAG, "spp onReceive: -------" + bluetoothDevice.getAddress());
        LOG(this.TAG, "spp onReceive: -------" + bArr.length);
        LOG(this.TAG, "spp onReceive: ------" + ArrayUtil.toHex(bArr));
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setPush(true);
        baseMessage.setMsgContent(bArr);
        DeviceConnector.ConnectionListener curListener = getCurListener(bluetoothDevice);
        if (curListener != null) {
            curListener.onDataReceived(baseMessage);
        }
    }

    @Override // com.aivox.besota.sdk.connect.DeviceConnector
    public void disconnect(HmDevice hmDevice) {
        LOG(this.TAG, "disconnect------spp");
        close(getCurDevice(hmDevice));
    }

    @Override // com.aivox.besota.sdk.connect.DeviceConnector
    public void registerConnectionListener(DeviceConnector.ConnectionListener connectionListener) {
        synchronized (this.mListenerLock) {
            if (!mConnectListeners.contains(connectionListener)) {
                mConnectListeners.add(connectionListener);
            }
        }
    }

    @Override // com.aivox.besota.sdk.connect.DeviceConnector
    public void unregisterConnectionListener(DeviceConnector.ConnectionListener connectionListener) {
        if (mConnectListeners.contains(connectionListener)) {
            mConnectListeners.remove(connectionListener);
        }
    }

    public void close(BluetoothDevice bluetoothDevice) {
        try {
            BluetoothSocket curBluetoothSocket = getCurBluetoothSocket(bluetoothDevice);
            if (curBluetoothSocket != null) {
                curBluetoothSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayUtil.resetObjectAtIndex(mServiceConfigs, getCurIndex(bluetoothDevice));
        ArrayUtil.resetObjectAtIndex(mBluetoothSockets, getCurIndex(bluetoothDevice));
        ArrayUtil.resetObjectAtIndex(mConnectedRunnables, getCurIndex(bluetoothDevice));
    }

    private class ConnectRunnable implements Runnable {
        private BluetoothDevice mDevice;
        private UUID sUUID;

        public ConnectRunnable(BluetoothDevice bluetoothDevice, UUID uuid) {
            this.mDevice = bluetoothDevice;
            this.sUUID = uuid;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                SppConnector sppConnector = SppConnector.this;
                sppConnector.LOG(sppConnector.TAG, "run: ------" + this.sUUID);
                BluetoothSocket bluetoothSocketCreateInsecureRfcommSocketToServiceRecord = this.mDevice.createInsecureRfcommSocketToServiceRecord(this.sUUID);
                bluetoothSocketCreateInsecureRfcommSocketToServiceRecord.connect();
                ConnectedRunnable connectedRunnable = SppConnector.this.new ConnectedRunnable(bluetoothSocketCreateInsecureRfcommSocketToServiceRecord.getInputStream(), bluetoothSocketCreateInsecureRfcommSocketToServiceRecord.getOutputStream(), this.mDevice);
                ArrayUtil.addObjectIndex(SppConnector.mBluetoothSockets, bluetoothSocketCreateInsecureRfcommSocketToServiceRecord, SppConnector.this.getCurIndex(this.mDevice));
                ArrayUtil.addObjectIndex(SppConnector.mConnectedRunnables, connectedRunnable, SppConnector.this.getCurIndex(this.mDevice));
                SppConnector.this.onConnectionStateChanged(true, this.mDevice);
                new Thread(connectedRunnable).start();
            } catch (IOException e) {
                e.printStackTrace();
                SppConnector.this.onConnectionStateChanged(false, this.mDevice);
            }
        }
    }

    private class ConnectedRunnable implements Runnable {
        private BluetoothDevice mDevice;
        private InputStream mRead;
        private OutputStream mWrite;

        public ConnectedRunnable(InputStream inputStream, OutputStream outputStream, BluetoothDevice bluetoothDevice) {
            this.mRead = inputStream;
            this.mWrite = outputStream;
            this.mDevice = bluetoothDevice;
        }

        @Override // java.lang.Runnable
        public void run() {
            SppConnector sppConnector = SppConnector.this;
            sppConnector.LOG(sppConnector.TAG, "run: ------mConnectedRunnable" + hashCode());
            try {
                try {
                    byte[] bArr = new byte[1048576];
                    while (true) {
                        SppConnector.this.onReceive(ArrayUtil.extractBytes(bArr, 0, this.mRead.read(bArr)), this.mDevice);
                    }
                } catch (IOException e) {
                    SppConnector sppConnector2 = SppConnector.this;
                    sppConnector2.LOG(sppConnector2.TAG, "run: ------mConnectedRunnable ee" + e.toString());
                    e.printStackTrace();
                    SppConnector.this.onConnectionStateChanged(false, this.mDevice);
                    SppConnector sppConnector3 = SppConnector.this;
                    sppConnector3.LOG(sppConnector3.TAG, "run: ------mConnectedRunnable finally" + hashCode());
                    try {
                        InputStream inputStream = this.mRead;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                SppConnector sppConnector4 = SppConnector.this;
                sppConnector4.LOG(sppConnector4.TAG, "run: ------mConnectedRunnable finally" + hashCode());
                try {
                    InputStream inputStream2 = this.mRead;
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                throw th;
            }
        }

        public boolean write(byte[] bArr) {
            try {
                this.mWrite.write(bArr);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                SppConnector.this.onConnectionStateChanged(false, this.mDevice);
                return false;
            }
        }
    }

    public void LOG(String str, String str2) {
        LogUtil.m335d(str, str2);
    }
}
