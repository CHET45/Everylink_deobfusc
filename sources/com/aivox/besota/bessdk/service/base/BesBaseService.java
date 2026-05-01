package com.aivox.besota.bessdk.service.base;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.MyAnimationUtil;
import com.aivox.besota.bessdk.BesSdkConstants;
import com.aivox.besota.bessdk.connect.BTService;
import com.aivox.besota.bessdk.connect.SppConnector;
import com.aivox.besota.bessdk.scan.BtHeleper;
import com.aivox.besota.bessdk.utils.ArrayUtil;
import com.aivox.besota.bessdk.utils.SPHelper;
import com.aivox.besota.sdk.connect.DeviceConnector;
import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.message.BaseMessage;
import com.aivox.besota.sdk.utils.DeviceProtocol;

/* JADX INFO: loaded from: classes.dex */
public class BesBaseService implements DeviceConnector.ConnectionListener, BesBaseConnectListener {
    BesServiceListener mBesServiceListener;
    public BesServiceConfig mConfig;
    public Context mContext;
    public DeviceConnector mDeviceConnector;
    public TOTAConnectCMD mTotaConnectCMD;
    public final String TAG = getClass().getSimpleName();
    public boolean totauccess = false;
    public boolean isConnect = false;
    public Handler mMsgHandler = new Handler() { // from class: com.aivox.besota.bessdk.service.base.BesBaseService.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            Log.i(BesBaseService.this.TAG, "mMsgHandler: ---------");
            int i = message.what;
            if (i == 1025) {
                BesBaseService besBaseService = BesBaseService.this;
                besBaseService.LOG(besBaseService.TAG, "handleMessage: ------BES_NOTI_MSG_TIMEOUT_TOTA_START");
                BesBaseService.this.totaConnectFail();
            } else {
                if (i != 1028) {
                    return;
                }
                BesBaseService besBaseService2 = BesBaseService.this;
                besBaseService2.LOG(besBaseService2.TAG, "handleMessage: ------MSG_TIME_OUT");
                BesBaseService.this.callBackErrorMessage(BesSdkConstants.MSG_TIME_OUT);
            }
        }
    };

    @Override // com.aivox.besota.sdk.connect.DeviceConnector.ConnectionListener
    public void notifyWrite(int i) {
    }

    @Override // com.aivox.besota.bessdk.service.base.BesBaseConnectListener
    public void notifyWrite(int i, int i2) {
    }

    public BesBaseService(BesServiceConfig besServiceConfig, BesServiceListener besServiceListener, Context context) {
        this.mContext = context;
        this.mConfig = besServiceConfig;
        if (besServiceConfig.getTotaConnect().booleanValue()) {
            this.mTotaConnectCMD = new TOTAConnectCMD();
            if (getDeviceConnectState(this.mConfig) == BesSdkConstants.BesConnectState.BES_CONNECT) {
                this.mTotaConnectCMD.setAes_key(BTService.getTotaAesKey(this.mContext, this.mConfig.getDeviceProtocol(), getCurDevice()));
            }
        } else {
            this.mTotaConnectCMD = null;
        }
        if (this.mContext == null || this.mConfig.getDeviceProtocol() == null || this.mConfig.getDevice() == null) {
            return;
        }
        this.mBesServiceListener = besServiceListener;
    }

    public BesSdkConstants.BesConnectState getDeviceConnectState(BesServiceConfig besServiceConfig) {
        return BTService.getDeviceConnectState(this.mContext, besServiceConfig);
    }

    private BluetoothDevice getCurDevice() {
        if (this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_USB) {
            return null;
        }
        HmDevice device = this.mConfig.getDevice();
        return BtHeleper.getBluetoothAdapter(this.mContext).getRemoteDevice(this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_BLE ? device.getBleAddress() : device.getDeviceMAC());
    }

    public void startConnect(BesServiceConfig besServiceConfig) {
        LOG(this.TAG, "startConnect: -------");
        this.mConfig = besServiceConfig;
        this.totauccess = false;
        if (besServiceConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_USB) {
            connect();
            return;
        }
        BesSdkConstants.BesConnectState deviceConnectState = getDeviceConnectState(this.mConfig);
        LOG(this.TAG, "startConnect: -------" + deviceConnectState);
        if (this.mTotaConnectCMD != null && this.mConfig.getTotaConnect().booleanValue() && deviceConnectState == BesSdkConstants.BesConnectState.BES_CONNECT) {
            LOG(this.TAG, "startConnect: ----TOTA---BES_CONNECT");
            BTService.refreshConnectListener(this.mContext, this.mConfig.getDeviceProtocol(), this, getCurDevice());
            this.mTotaConnectCMD.setAes_key(BTService.getTotaAesKey(this.mContext, this.mConfig.getDeviceProtocol(), getCurDevice()));
            totaConnectSucess();
            return;
        }
        if (deviceConnectState == BesSdkConstants.BesConnectState.BES_CONNECT) {
            BTService.refreshConnectListener(this.mContext, this.mConfig.getDeviceProtocol(), this, getCurDevice());
            this.isConnect = true;
            callBackStateChangedMessage(666, "");
            onStatusChanged(this.mConfig.getDevice(), 666, this.mConfig.getDeviceProtocol());
            return;
        }
        if (deviceConnectState == BesSdkConstants.BesConnectState.BES_CONNECT_TOTA) {
            LOG(this.TAG, "startConnect: -------DISCONNECT_First");
            BTService.disconnect(this.mConfig.getDevice());
            new Thread(new Runnable() { // from class: com.aivox.besota.bessdk.service.base.BesBaseService.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Thread.sleep(MyAnimationUtil.ANI_TIME_2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    BesBaseService.this.connect();
                }
            }).start();
            return;
        }
        connect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connect() {
        LogUtil.m335d(this.TAG, "connect_" + (this.mConfig.getDevice().getDeviceMAC() != null) + (this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_SPP));
        if (this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_SPP && this.mConfig.getDevice().getDeviceMAC() != null) {
            SppConnector sppConnector = SppConnector.getsConnector(this.mContext, this.mConfig);
            this.mDeviceConnector = sppConnector;
            sppConnector.connect(this.mConfig.getDevice(), this);
            return;
        }
        callBackErrorMessage(BesSdkConstants.MSG_PARAMETER_ERROR);
    }

    public void disconnected() {
        DeviceConnector deviceConnector = this.mDeviceConnector;
        if (deviceConnector != null) {
            deviceConnector.disconnect(this.mConfig.getDevice());
        }
    }

    @Override // com.aivox.besota.sdk.connect.DeviceConnector.ConnectionListener
    public void onStatusChanged(HmDevice hmDevice, int i, DeviceProtocol deviceProtocol) {
        LOG(this.TAG, "onStatusChanged---Base");
        if (i == 666) {
            this.isConnect = true;
        } else {
            this.isConnect = false;
        }
        if (this.mConfig.getTotaConnect().booleanValue() && !this.totauccess && i == 666) {
            sendData(this.mTotaConnectCMD.totaStartData());
            addTimeOutMsg(this.mMsgHandler, 5000L, 1025);
        }
        if (i == 444) {
            this.totauccess = false;
        }
    }

    @Override // com.aivox.besota.sdk.connect.DeviceConnector.ConnectionListener
    public void onDataReceived(BaseMessage baseMessage) {
        if (((byte[]) baseMessage.getMsgContent())[0] == 0 && ((byte[]) baseMessage.getMsgContent())[1] == 0) {
            return;
        }
        if (this.mConfig.getTotaConnect().booleanValue() && !this.totauccess) {
            int iReceiveData = this.mTotaConnectCMD.receiveData(this.mConfig.getUseTotaV2().booleanValue() ? this.mTotaConnectCMD.setTotaV2PacketData((byte[]) baseMessage.getMsgContent(), false) : (byte[]) baseMessage.getMsgContent());
            if (iReceiveData == 769) {
                removeTimeoutMsg(this.mMsgHandler, 1025);
                sendData(this.mTotaConnectCMD.totaConfirm());
            } else if (iReceiveData == 770) {
                totaConnectSucess();
            } else if (iReceiveData == 771) {
                this.totauccess = false;
                callBackTotaConnectState(false);
            }
        } else if (this.totauccess && ((Boolean) SPHelper.getPreference(this.mContext, BesSdkConstants.BES_TOTA_ENCRYPTION_KEY, true)).booleanValue()) {
            baseMessage.setMsgContent(this.mConfig.getUseTotaV2().booleanValue() ? this.mTotaConnectCMD.setTotaV2PacketData((byte[]) baseMessage.getMsgContent(), true) : this.mTotaConnectCMD.totaDecodeData((byte[]) baseMessage.getMsgContent()));
        }
        this.mMsgHandler.removeMessages(BesSdkConstants.MSG_TIME_OUT);
    }

    public void callBackTotaConnectState(boolean z) {
        BesServiceListener besServiceListener = this.mBesServiceListener;
        if (besServiceListener != null) {
            besServiceListener.onTotaConnectState(z, this.mConfig.getDevice());
        }
    }

    public void callBackErrorMessage(int i) {
        BesServiceListener besServiceListener = this.mBesServiceListener;
        if (besServiceListener != null) {
            besServiceListener.onErrorMessage(i, this.mConfig.getDevice());
        }
    }

    public void callBackStateChangedMessage(int i, String str) {
        BesServiceListener besServiceListener = this.mBesServiceListener;
        if (besServiceListener != null) {
            besServiceListener.onStateChangedMessage(i, str, this.mConfig.getDevice());
        }
    }

    public void callBackSuccessMessage(int i) {
        BesServiceListener besServiceListener = this.mBesServiceListener;
        if (besServiceListener != null) {
            besServiceListener.onSuccessMessage(i, this.mConfig.getDevice());
        }
    }

    public boolean sendData(byte[] bArr) {
        if (!this.isConnect) {
            return false;
        }
        LOG(this.TAG, "sendData: -----" + ArrayUtil.toHex(bArr));
        LOG(this.TAG, "sendData: -----" + bArr.length);
        boolean zBooleanValue = ((Boolean) SPHelper.getPreference(this.mContext, BesSdkConstants.BES_TOTA_ENCRYPTION_KEY, true)).booleanValue();
        Context context = this.mContext;
        DeviceProtocol deviceProtocol = this.mConfig.getDeviceProtocol();
        if (this.totauccess && zBooleanValue) {
            if (this.mConfig.getUseTotaV2().booleanValue()) {
                TOTAConnectCMD tOTAConnectCMD = this.mTotaConnectCMD;
                bArr = tOTAConnectCMD.getTotaV2Packet(tOTAConnectCMD.totaEncryptData(bArr));
            } else {
                bArr = this.mTotaConnectCMD.totaEncryptData(bArr);
            }
        } else if (this.mConfig.getUseTotaV2().booleanValue()) {
            bArr = this.mTotaConnectCMD.getTotaV2Packet(bArr);
        }
        return BTService.sendData(context, deviceProtocol, bArr, getCurDevice());
    }

    public boolean sendDataWithoutResponse(byte[] bArr) {
        if (!this.isConnect) {
            return false;
        }
        LOG(this.TAG, "sendDataWithoutResponse: ----" + ArrayUtil.toHex(bArr));
        LOG(this.TAG, "sendDataWithoutResponse: -----" + bArr.length);
        boolean zBooleanValue = ((Boolean) SPHelper.getPreference(this.mContext, BesSdkConstants.BES_TOTA_ENCRYPTION_KEY, true)).booleanValue();
        Context context = this.mContext;
        DeviceProtocol deviceProtocol = this.mConfig.getDeviceProtocol();
        if (this.totauccess && zBooleanValue) {
            if (this.mConfig.getUseTotaV2().booleanValue()) {
                TOTAConnectCMD tOTAConnectCMD = this.mTotaConnectCMD;
                bArr = tOTAConnectCMD.getTotaV2Packet(tOTAConnectCMD.totaEncryptData(bArr));
            } else {
                bArr = this.mTotaConnectCMD.totaEncryptData(bArr);
            }
        } else if (this.mConfig.getUseTotaV2().booleanValue()) {
            bArr = this.mTotaConnectCMD.getTotaV2Packet(bArr);
        }
        return BTService.sendDataWithoutResponse(context, deviceProtocol, bArr, getCurDevice());
    }

    public boolean sendData(byte[] bArr, long j) {
        addTimeOut(j);
        return sendData(bArr);
    }

    public void sendDataDelay(Handler handler, int i, long j) {
        handler.sendMessageDelayed(handler.obtainMessage(i), j);
    }

    public void addTimeOut(long j) {
        this.mMsgHandler.sendMessageDelayed(this.mMsgHandler.obtainMessage(BesSdkConstants.MSG_TIME_OUT), j);
    }

    public void addTimeOutMsg(Handler handler, long j, int i) {
        handler.sendMessageDelayed(handler.obtainMessage(i), j);
    }

    public void removeTimeoutMsg(Handler handler, int i) {
        handler.removeMessages(i);
    }

    public void LOG(String str, String str2) {
        if (this.mContext == null) {
            return;
        }
        LogUtil.m339i(str, "LOG: " + str2);
    }

    private void totaConnectSucess() {
        this.isConnect = true;
        this.totauccess = true;
        BTService.saveTotaAesKey(this.mContext, this.mConfig.getDeviceProtocol(), this.mTotaConnectCMD.getAes_key(), getCurDevice());
        callBackTotaConnectState(this.totauccess);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void totaConnectFail() {
        this.isConnect = false;
        this.totauccess = false;
        callBackTotaConnectState(false);
        disconnected();
    }
}
