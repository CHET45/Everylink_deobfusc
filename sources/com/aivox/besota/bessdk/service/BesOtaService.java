package com.aivox.besota.bessdk.service;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.aivox.besota.bessdk.BesSdkConstants;
import com.aivox.besota.bessdk.scan.BtHeleper;
import com.aivox.besota.bessdk.service.base.BesBaseService;
import com.aivox.besota.bessdk.service.base.BesServiceConfig;
import com.aivox.besota.bessdk.service.base.BesServiceListener;
import com.aivox.besota.bessdk.utils.ArrayUtil;
import com.aivox.besota.bessdk.utils.SPHelper;
import com.aivox.besota.sdk.connect.DeviceConnector;
import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.message.BaseMessage;
import com.aivox.besota.sdk.ota.OTADfuInfo;
import com.aivox.besota.sdk.ota.OTATask;
import com.aivox.besota.sdk.ota.RemoteOTAConfig;
import com.aivox.besota.sdk.utils.DeviceProtocol;
import com.aivox.besota.sdk.utils.OTAStatus;
import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class BesOtaService extends BesBaseService implements DeviceConnector.ConnectionListener, OTATask {
    int USER_FLAG;
    private Handler besOtaMsgHandler;
    int connectRetryTimes;
    int crcPackageRetryTimes;
    int curOtaResult;
    int curUpgateType;
    int curUser;
    boolean currentOrLegacy;
    int getCrcConfirmRetryTimes;
    int getProtocolRetryTimes;
    int getVersionRetryTimes;
    int imageSideSelection;
    boolean isWithoutResponse;
    private ScanCallback mCallback;
    BluetoothDevice mDevice;
    private BluetoothLeScanner mLeScanner;
    OTADfuInfo mOTADfuInfo;
    OTAStatus mOTAStatus;
    private BesOtaCMD mOtaCMD;
    protected Object mOtaLock;
    RemoteOTAConfig mRemoteOTAConfig;
    OTATask.StatusListener mStatusListener;
    int notifyRetryTimes;
    String progress;
    boolean roleSwitchDisconnect;
    String roleSwitchRandomID;
    private boolean scanSuccess;
    boolean sendPackageWithAck;
    int setUserRetryTimes;
    private Timer sppRoleSwitchTimer;
    int sppRoleSwitchTimes;
    long sppSendDataDelay;
    private TimerTask task;

    @Override // com.aivox.besota.sdk.ota.OTATask
    public boolean applyNewFirmware(int i) {
        return false;
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public void postTransferCleanup() {
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public void preTransferInit() {
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public void registerOTAStatusListener(OTATask.StatusListener statusListener) {
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public boolean startDataTransfer(OTADfuInfo oTADfuInfo, int i, boolean z, OTATask.StatusListener statusListener) {
        return false;
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public void unregisterOTAStatusListener(OTATask.StatusListener statusListener) {
    }

    public BesOtaService(BesServiceConfig besServiceConfig, BesServiceListener besServiceListener, Context context) {
        super(besServiceConfig, besServiceListener, context);
        this.mOtaLock = new Object();
        this.mOTAStatus = OTAStatus.STATUS_UNKNOWN;
        this.progress = "0.00";
        this.curOtaResult = 0;
        this.notifyRetryTimes = 0;
        this.connectRetryTimes = 0;
        this.getVersionRetryTimes = 0;
        this.getCrcConfirmRetryTimes = 0;
        this.getProtocolRetryTimes = 0;
        this.setUserRetryTimes = 0;
        this.sendPackageWithAck = false;
        this.crcPackageRetryTimes = 0;
        this.USER_FLAG = 1;
        this.currentOrLegacy = true;
        this.curUpgateType = 1;
        this.curUser = 1;
        this.sppSendDataDelay = 10L;
        this.imageSideSelection = 0;
        this.isWithoutResponse = false;
        this.roleSwitchRandomID = "";
        this.roleSwitchDisconnect = false;
        this.scanSuccess = false;
        this.mCallback = new ScanCallback() { // from class: com.aivox.besota.bessdk.service.BesOtaService.3
            @Override // android.bluetooth.le.ScanCallback
            public void onScanFailed(int i) {
                super.onScanFailed(i);
                BesOtaService besOtaService = BesOtaService.this;
                besOtaService.LOG(besOtaService.TAG, "onScanFailed: ---------fail");
                BesOtaService.this.scanToReConnected();
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onScanResult(int i, ScanResult scanResult) {
                BesOtaService.this.scanSuccess = true;
                String strBytesToHexString = ArrayUtil.bytesToHexString(scanResult.getScanRecord().getBytes());
                String strSubstring = "03ff";
                String[] strArrSplit = strBytesToHexString.split("03ff");
                if (strBytesToHexString.contains("03ff03ff")) {
                    String[] strArrSplit2 = strBytesToHexString.split("03ff03ff");
                    if (strArrSplit2.length > 1) {
                        String str = strArrSplit2[1];
                        int i2 = 0;
                        while (true) {
                            if (i2 >= str.length()) {
                                break;
                            }
                            int i3 = i2 + 1;
                            if (str.substring(i2, i3).equals("0")) {
                                i2 = i3;
                            } else {
                                String str2 = strArrSplit[strArrSplit.length - 1];
                                if (str2.length() < 4) {
                                    return;
                                } else {
                                    strSubstring = str2.substring(0, 4);
                                }
                            }
                        }
                    }
                } else {
                    if (strArrSplit.length <= 1) {
                        return;
                    }
                    String str3 = strArrSplit[strArrSplit.length - 1];
                    if (str3.length() < 4) {
                        return;
                    } else {
                        strSubstring = str3.substring(0, 4);
                    }
                }
                if (strSubstring.equals(BesOtaService.this.roleSwitchRandomID)) {
                    BesOtaService.this.mLeScanner.stopScan(BesOtaService.this.mCallback);
                    BesOtaService.this.mLeScanner = null;
                    BluetoothDevice device = scanResult.getDevice();
                    if (device != null) {
                        if (BesOtaService.this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_BLE) {
                            BesOtaService.this.mConfig.getDevice().setBleAddress(device.getAddress());
                        } else {
                            BesOtaService.this.mConfig.getDevice().setDeviceMAC(device.getAddress());
                        }
                        BesOtaService besOtaService = BesOtaService.this;
                        besOtaService.startConnect(besOtaService.mConfig);
                    }
                }
            }
        };
        this.besOtaMsgHandler = new Handler() { // from class: com.aivox.besota.bessdk.service.BesOtaService.4
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                BesOtaService besOtaService = BesOtaService.this;
                besOtaService.LOG(besOtaService.TAG, "handleMessage: -----" + message.what);
                int i = message.what;
                if (i == 2323) {
                    BesOtaService.this.disconnected();
                    return;
                }
                if (i == 2324) {
                    boolean zSendPacketData = BesOtaService.this.sendPacketData();
                    if (zSendPacketData && BesOtaService.this.curOtaResult > 0 && BesOtaService.this.mOtaCMD != null) {
                        BesOtaService.this.mOtaCMD.notifySuccess();
                        BesOtaService.this.besSendData();
                        return;
                    } else {
                        if (zSendPacketData || BesOtaService.this.curOtaResult <= 0 || BesOtaService.this.mOtaCMD == null) {
                            return;
                        }
                        BesOtaService.this.besSendData();
                        return;
                    }
                }
                switch (i) {
                    case BesOTAConstants.MSG_GET_VERSION_TIME_OUT /* 2384 */:
                        if (BesOtaService.this.getVersionRetryTimes > 2) {
                            BesOtaService.this.callBackErrorMessage(message.what);
                        } else if (BesOtaService.this.isConnect) {
                            BesOtaService.this.getVersionRetryTimes++;
                            BesOtaService.this.getCurrentVersion();
                        } else {
                            BesOtaService.this.getVersionRetryTimes = 0;
                        }
                        break;
                    case BesOTAConstants.MSG_GET_RANDOMID_TIME_OUT /* 2385 */:
                        BesOtaService.this.callBackStateChangedMessage(BesOTAConstants.MSG_GET_RANDOMID_TIME_OUT, "");
                        BesOtaService.this.sendSelectSideData();
                        break;
                    case BesOTAConstants.MSG_OTA_OVER_RECONNECT /* 2386 */:
                        if (BesOtaService.this.roleSwitchRandomID.length() > 0) {
                            BesOtaService.this.scanToReConnected();
                        } else {
                            BesOtaService besOtaService2 = BesOtaService.this;
                            besOtaService2.startConnect(besOtaService2.mConfig);
                        }
                        break;
                    case BesOTAConstants.MSG_GET_PROTOCOL_VERSION_TIME_OUT /* 2387 */:
                        if (BesOtaService.this.roleSwitchRandomID.length() > 0) {
                            BesOtaService.this.disconnected();
                        } else if (BesOtaService.this.USER_FLAG != 1) {
                            BesOtaService.this.sendUpgrateTypeData();
                        } else if (BesOtaService.this.getProtocolRetryTimes <= 2 && BesOtaService.this.isConnect) {
                            BesOtaService.this.getProtocolRetryTimes++;
                            BesOtaService.this.sendGetProtocolVersionData();
                        } else {
                            BesOtaService.this.getProtocolRetryTimes = 0;
                            BesOtaService.this.mOTAStatus = OTAStatus.STATUS_FAILED;
                            BesOtaService.this.callBackErrorMessage(BesOTAConstants.MSG_GET_PROTOCOL_VERSION_TIME_OUT);
                            BesOtaService.this.mOTAStatus = OTAStatus.STATUS_CANCELED;
                            BesOtaService.this.destoryVariable();
                        }
                        break;
                    case BesOTAConstants.MSG_GET_UPGRATE_TYPE_TIME_OUT /* 2388 */:
                        BesOtaService.this.callBackStateChangedMessage(BesOTAConstants.MSG_GET_UPGRATE_TYPE_TIME_OUT, "");
                        BesOtaService.this.sendGetROLESwitchRandomIDData();
                        break;
                    case BesOTAConstants.MSG_GET_SELECT_SIDE_TIME_OUT /* 2389 */:
                        BesOtaService besOtaService3 = BesOtaService.this;
                        besOtaService3.otaSendData(besOtaService3.mOtaCMD.getCheckBreakPointCMD(BesOtaService.this.mContext));
                        break;
                    case BesOTAConstants.MSG_SET_OTA_CONFIG_TIME_OUT /* 2390 */:
                        BesOtaService besOtaService4 = BesOtaService.this;
                        besOtaService4.otaSendData(besOtaService4.mOtaCMD.getOTAConfigureCMD(BesOtaService.this.mRemoteOTAConfig.getLocalPath(), BesOtaService.this.mContext));
                        break;
                    case BesOTAConstants.MSG_GET_CRC_CHECK_PACKAGE_TIME_OUT /* 2391 */:
                        if (BesOtaService.this.getCrcConfirmRetryTimes <= 1) {
                            BesOtaService.this.getCrcConfirmRetryTimes++;
                            if (BesOtaService.this.mOtaCMD != null) {
                                BesOtaService.this.mOtaCMD.setCrcConfirmState(true);
                                BesOtaService.this.besSendData();
                            }
                        } else {
                            BesOtaService.this.callBackErrorMessage(BesOTAConstants.OTA_CMD_CRC_CHECK_PACKAGE_ERROR);
                        }
                        break;
                    case BesOTAConstants.MSG_SET_USER_TIME_OUT /* 2392 */:
                        if (BesOtaService.this.setUserRetryTimes <= 1) {
                            BesOtaService.this.setUserRetryTimes++;
                            BesOtaService.this.sendSetUserData();
                        } else if (BesOtaService.this.USER_FLAG == 0) {
                            BesOtaService.this.getCurrentVersion();
                        } else {
                            BesOtaService.this.callBackErrorMessage(BesOTAConstants.OTA_CMD_SET_OAT_USER_ERROR);
                        }
                        break;
                    case BesOTAConstants.MSG_VERIFY_BTH_TIME_OUT /* 2393 */:
                        BesOtaService.this.sendUpgrateTypeData();
                        break;
                }
            }
        };
        this.sppRoleSwitchTimes = 0;
        LOG(this.TAG, "init");
        String bleAddress = this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_BLE ? this.mConfig.getDevice().getBleAddress() : this.mConfig.getDevice().getDeviceMAC();
        if (bleAddress != null && bleAddress.length() > 0) {
            this.mDevice = BtHeleper.getBluetoothAdapter(this.mContext).getRemoteDevice(bleAddress);
        }
        this.USER_FLAG = besServiceConfig.getUSER_FLAG().intValue();
        this.isWithoutResponse = besServiceConfig.getIsWithoutResponse().booleanValue();
        BesOtaCMD besOtaCMD = new BesOtaCMD();
        this.mOtaCMD = besOtaCMD;
        besOtaCMD.setOtaUser(this.USER_FLAG, besServiceConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_SPP, this.isWithoutResponse, this.mConfig.getTotaConnect().booleanValue(), besServiceConfig.getUseTotaV2().booleanValue(), bleAddress);
        boolean zBooleanValue = ((Boolean) SPHelper.getPreference(this.mContext, BesSdkConstants.BES_default_INTERVAL, true)).booleanValue();
        long j = Long.parseLong((String) SPHelper.getPreference(this.mContext, BesSdkConstants.BES_BLE_INTERVAL, "50"));
        Log.i(this.TAG, "BesOtaService: +++" + zBooleanValue);
        Log.i(this.TAG, "BesOtaService: +++" + j);
        long j2 = Long.parseLong((String) SPHelper.getPreference(this.mContext, BesSdkConstants.BES_SPP_INTERVAL, "30"));
        Log.i(this.TAG, "BesOtaService: +++" + j2);
        if (besServiceConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_SPP) {
            if (this.mConfig.getTotaConnect().booleanValue()) {
                this.sppSendDataDelay = 20L;
            } else {
                this.sppSendDataDelay = 15L;
            }
            if (this.USER_FLAG == 0) {
                this.sppSendDataDelay = 30L;
            }
            if (!zBooleanValue) {
                Log.i(this.TAG, "BesOtaService: 1111    " + j2);
                this.sppSendDataDelay = j2;
            }
        } else if (besServiceConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_USB) {
            this.sppSendDataDelay = 5L;
        } else {
            this.sppSendDataDelay = 50L;
            if (!zBooleanValue) {
                Log.i(this.TAG, "BesOtaService: 1111     " + j);
                this.sppSendDataDelay = j;
            }
        }
        if (this.USER_FLAG < 1) {
            this.sendPackageWithAck = besServiceConfig.getCurAckType().booleanValue();
        }
        if (this.USER_FLAG == -1) {
            int iIntValue = besServiceConfig.getImageSideSelection().intValue();
            this.imageSideSelection = iIntValue;
            this.mOtaCMD.setImageSideSelection(iIntValue != 4 ? iIntValue : 1);
        }
        this.curUser = besServiceConfig.getCurUser().intValue();
        this.curUpgateType = besServiceConfig.getCurUpgateType().intValue();
        LOG(this.TAG, "BesOtaService Protocol: --------" + besServiceConfig.getDeviceProtocol());
        startConnect(besServiceConfig);
    }

    @Override // com.aivox.besota.bessdk.service.base.BesBaseService, com.aivox.besota.sdk.connect.DeviceConnector.ConnectionListener
    public void onStatusChanged(HmDevice hmDevice, int i, DeviceProtocol deviceProtocol) {
        super.onStatusChanged(hmDevice, i, deviceProtocol);
        LOG(this.TAG, "onStatusChanged---OTAService");
        LOG(this.TAG, "onStatusChanged: ------" + i);
        Log.i(this.TAG, "onStatusChanged: --------mConfig.getTotaConnect()" + this.mConfig.getTotaConnect());
        if (i == 666 && !this.mConfig.getTotaConnect().booleanValue()) {
            this.roleSwitchDisconnect = false;
            this.connectRetryTimes = 0;
            LOG(this.TAG, "onStatusChanged: -----true");
            callBackStateChangedMessage(666, "");
            if (this.roleSwitchRandomID.length() > 0 && this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_SPP) {
                stopSppRoleSwitchTimer();
            }
            int i2 = this.USER_FLAG;
            if (i2 == 1) {
                sendGetProtocolVersionData();
                return;
            } else {
                if (i2 == 0) {
                    sendSetUserData();
                    return;
                }
                return;
            }
        }
        if (i != 666) {
            LOG(this.TAG, "onStatusChanged: -----false +" + this.mOTAStatus);
            if (this.mOTAStatus == OTAStatus.STATUS_CANCELED || this.mOTAStatus == OTAStatus.STATUS_FAILED) {
                return;
            }
            if (this.mOTAStatus != OTAStatus.STATUS_UNKNOWN) {
                if (this.mOTAStatus != OTAStatus.STATUS_SUCCEED && this.mOTAStatus != OTAStatus.STATUS_CANCELED && this.mOTAStatus != OTAStatus.STATUS_FAILED && this.roleSwitchRandomID.length() > 0) {
                    LOG(this.TAG, "onStatusChanged: -----------" + this.roleSwitchRandomID);
                    if (this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_BLE) {
                        scanToReConnected();
                        return;
                    } else {
                        if (this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_SPP || this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_GATT_BR_EDR) {
                            Log.i(this.TAG, "onStatusChanged: ---------mDeviceProtocol == DeviceProtocol.PROTOCOL_SPP");
                            startConnect(this.mConfig);
                            return;
                        }
                        return;
                    }
                }
                if (this.mOTAStatus == OTAStatus.STATUS_SUCCEED) {
                    sendDataDelay(this.besOtaMsgHandler, BesOTAConstants.MSG_OTA_OVER_RECONNECT, 15000L);
                    return;
                } else {
                    LOG(this.TAG, "onStatusChanged: ------------22222");
                    callBackStateChangedMessage(BesSdkConstants.BES_CONNECT_ERROR, "");
                    return;
                }
            }
            int i3 = this.connectRetryTimes + 1;
            this.connectRetryTimes = i3;
            if (i3 == 3) {
                callBackStateChangedMessage(BesSdkConstants.BES_CONNECT_ERROR, "");
            } else {
                startConnect(this.mConfig);
            }
        }
    }

    @Override // com.aivox.besota.bessdk.service.base.BesBaseService
    public void callBackTotaConnectState(boolean z) {
        super.callBackTotaConnectState(z);
        Log.i(this.TAG, "callBackTotaConnectState: ------" + z);
        if (z) {
            this.roleSwitchDisconnect = false;
            this.connectRetryTimes = 0;
            LOG(this.TAG, "onStatusChanged: -----true");
            callBackStateChangedMessage(666, "");
            if (this.roleSwitchRandomID.length() > 0 && this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_SPP) {
                stopSppRoleSwitchTimer();
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = this.USER_FLAG;
            if (i == 1) {
                sendGetProtocolVersionData();
            } else if (i == 0) {
                sendSetUserData();
            }
        }
    }

    @Override // com.aivox.besota.bessdk.service.base.BesBaseService, com.aivox.besota.sdk.connect.DeviceConnector.ConnectionListener
    public void onDataReceived(BaseMessage baseMessage) {
        super.onDataReceived(baseMessage);
        LOG(this.TAG, "onDataReceived : ----" + baseMessage.toString());
        if ((!this.mConfig.getTotaConnect().booleanValue() || this.totauccess) && this.mOtaCMD != null) {
            byte[] bArr = (byte[]) baseMessage.getMsgContent();
            LOG(this.TAG, "onDataReceived encode: ----" + ArrayUtil.toHex(bArr));
            if (this.sendPackageWithAck || bArr[0] != -117) {
                if (this.mConfig.getTotaConnect().booleanValue() && bArr.length > 4) {
                    byte[] bArr2 = new byte[bArr.length - 4];
                    System.arraycopy(bArr, 4, bArr2, 0, bArr.length - 4);
                    bArr = bArr2;
                }
                LOG(this.TAG, "onDataReceived: ----" + ArrayUtil.toHex(bArr));
                int iReceiveData = this.mOtaCMD.receiveData(bArr, this.mContext, this.curOtaResult, this.sendPackageWithAck);
                if (iReceiveData != 0) {
                    this.curOtaResult = iReceiveData;
                }
                LOG(this.TAG, "onDataReceived: -----" + this.curOtaResult);
                if (iReceiveData == 2307) {
                    removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_GET_VERSION_TIME_OUT);
                    this.getVersionRetryTimes = 0;
                    if (this.roleSwitchRandomID.length() > 0 && this.mOTAStatus != OTAStatus.STATUS_SUCCEED) {
                        startOta();
                        return;
                    } else {
                        callBackStateChangedMessage(iReceiveData, this.mOtaCMD.getCurrentVersion());
                        return;
                    }
                }
                if (iReceiveData == 2322) {
                    removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_VERIFY_BTH_TIME_OUT);
                    callBackStateChangedMessage(iReceiveData, "");
                    sendUpgrateTypeData();
                    return;
                }
                if (iReceiveData == 2305) {
                    removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_GET_PROTOCOL_VERSION_TIME_OUT);
                    this.getProtocolRetryTimes = 0;
                    callBackStateChangedMessage(iReceiveData, "");
                    sendSetUserData();
                    return;
                }
                if (iReceiveData == 2306) {
                    removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_SET_USER_TIME_OUT);
                    this.setUserRetryTimes = 0;
                    callBackStateChangedMessage(iReceiveData, "");
                    getCurrentVersion();
                    return;
                }
                if (iReceiveData == 2308) {
                    removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_GET_UPGRATE_TYPE_TIME_OUT);
                    callBackStateChangedMessage(iReceiveData, "");
                    sendGetROLESwitchRandomIDData();
                    return;
                }
                if (iReceiveData == 2309) {
                    removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_GET_UPGRATE_TYPE_TIME_OUT);
                    callBackStateChangedMessage(iReceiveData, "");
                    sendGetROLESwitchRandomIDData();
                    return;
                }
                if (iReceiveData == 2310) {
                    removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_GET_RANDOMID_TIME_OUT);
                    this.roleSwitchRandomID = this.mOtaCMD.getRoleSwitchRandomID();
                    LOG(this.TAG, "onDataReceived roleSwitchRandomID: -----" + this.roleSwitchRandomID);
                    callBackStateChangedMessage(iReceiveData, this.roleSwitchRandomID);
                    sendSelectSideData();
                    return;
                }
                if (iReceiveData == 2311) {
                    removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_GET_SELECT_SIDE_TIME_OUT);
                    callBackStateChangedMessage(iReceiveData, "");
                    otaSendData(this.mOtaCMD.getCheckBreakPointCMD(this.mContext));
                    return;
                }
                if (iReceiveData == 2312) {
                    if (((String) SPHelper.getPreference(this.mContext, BesOTAConstants.BES_OTA_IS_MULTIDEVICE_UPGRADE, "")).equals(BesOTAConstants.BES_OTA_IS_MULTIDEVICE_UPGRADE)) {
                        otaSendData(this.mOtaCMD.getStartOTAPacketCMD(this.mRemoteOTAConfig.getLocalPath()));
                        return;
                    } else {
                        callBackStateChangedMessage(iReceiveData, "");
                        return;
                    }
                }
                if (iReceiveData == 2313) {
                    otaSendData(this.mOtaCMD.getStartOTAPacketCMD(this.mRemoteOTAConfig.getLocalPath()));
                    return;
                }
                if (iReceiveData == 2320) {
                    callBackStateChangedMessage(iReceiveData, "");
                    if (this.curUser == 6) {
                        this.mOtaCMD.getStartOTAPacketCMD(this.mRemoteOTAConfig.getLocalPath());
                    }
                    addTimeOutMsg(this.besOtaMsgHandler, 3000L, BesOTAConstants.MSG_SET_OTA_CONFIG_TIME_OUT);
                    otaSendData(this.mOtaCMD.getOTAConfigureCMD(this.mRemoteOTAConfig.getLocalPath(), this.mContext));
                    return;
                }
                if (iReceiveData == 2324) {
                    this.mOtaCMD.notifySuccess();
                    besSendData();
                    return;
                }
                if (iReceiveData == 2321) {
                    removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_SET_OTA_CONFIG_TIME_OUT);
                    this.roleSwitchDisconnect = false;
                    this.curOtaResult = BesOTAConstants.OTA_CMD_SEND_OTA_DATA;
                    this.mOTAStatus = OTAStatus.STATUS_UPDATING;
                    callBackStateChangedMessage(iReceiveData, "");
                    besSendData();
                    return;
                }
                if (iReceiveData == 2326) {
                    removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_GET_CRC_CHECK_PACKAGE_TIME_OUT);
                    callBackStateChangedMessage(iReceiveData, "crc check package ok");
                    this.getCrcConfirmRetryTimes = 0;
                    this.crcPackageRetryTimes = 0;
                    this.curOtaResult = BesOTAConstants.OTA_CMD_SEND_OTA_DATA;
                    besSendData();
                    return;
                }
                if (iReceiveData == 2327) {
                    removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_GET_CRC_CHECK_PACKAGE_TIME_OUT);
                    this.mOTAStatus = OTAStatus.STATUS_VERIFYING;
                    besSendData();
                    return;
                }
                if (iReceiveData == 2323) {
                    callBackStateChangedMessage(iReceiveData, "");
                    if (this.roleSwitchRandomID.length() > 0) {
                        this.roleSwitchDisconnect = true;
                    }
                    if (this.mOTAStatus == OTAStatus.STATUS_SUCCEED) {
                        sendDataDelay(this.besOtaMsgHandler, BesOTAConstants.OTA_CMD_DISCONNECT, 3000L);
                        return;
                    }
                    return;
                }
                if (iReceiveData == 2337) {
                    callBackStateChangedMessage(iReceiveData, "");
                    startSppRoleSwitchTimer();
                    return;
                }
                if (iReceiveData == 2328) {
                    this.mOTAStatus = OTAStatus.STATUS_VERIFIED;
                    callBackStateChangedMessage(iReceiveData, "");
                    otaSendData(this.mOtaCMD.getImageOverwritingConfirmationPacketCMD());
                    return;
                }
                if (iReceiveData == 2329) {
                    LOG(this.TAG, "onDataReceived: -------ota success");
                    this.mOTAStatus = OTAStatus.STATUS_SUCCEED;
                    callBackSuccessMessage(iReceiveData);
                    SPHelper.removePreference(this.mContext, BesOTAConstants.BES_OTA_RANDOM_CODE_LEFT + (this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_BLE ? this.mConfig.getDevice().getBleAddress() : this.mConfig.getDevice().getDeviceMAC()));
                    return;
                }
                if (iReceiveData == 2371 && this.crcPackageRetryTimes < 3) {
                    removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_GET_CRC_CHECK_PACKAGE_TIME_OUT);
                    this.crcPackageRetryTimes++;
                    callBackStateChangedMessage(iReceiveData, "" + this.crcPackageRetryTimes);
                    this.mOtaCMD.crcConfirmError();
                    this.curOtaResult = BesOTAConstants.OTA_CMD_SEND_OTA_DATA;
                    besSendData();
                    return;
                }
                if (iReceiveData == 2304) {
                    LOG(this.TAG, "onDataReceived: OTA_CMD_RETURN------------OTA_CMD_RETURN");
                    return;
                }
                byte b = bArr[0];
                if (b == 0 && bArr[1] == 16 && bArr[2] == 16) {
                    return;
                }
                if (b == 99 && bArr[1] == 111 && bArr[2] == 110) {
                    return;
                }
                if (iReceiveData == 2368 || iReceiveData == 2369 || iReceiveData == 2370 || iReceiveData == 2371 || iReceiveData == 2372 || iReceiveData == 2373 || iReceiveData == 2374) {
                    if (iReceiveData == 2371) {
                        removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_GET_CRC_CHECK_PACKAGE_TIME_OUT);
                    } else if (iReceiveData == 2374) {
                        removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_SET_USER_TIME_OUT);
                        this.setUserRetryTimes = 0;
                    } else if (iReceiveData == 2370) {
                        removeTimeoutMsg(this.besOtaMsgHandler, BesOTAConstants.MSG_SET_OTA_CONFIG_TIME_OUT);
                    }
                    Log.i(this.TAG, "error onDataReceived: ----" + ArrayUtil.toHex(bArr));
                    OTAStatus oTAStatus = OTAStatus.STATUS_FAILED;
                    this.mOTAStatus = oTAStatus;
                    callBackOTAStatusChanged(oTAStatus);
                    callBackErrorMessage(iReceiveData);
                    destoryVariable();
                }
            }
        }
    }

    @Override // com.aivox.besota.bessdk.service.base.BesBaseService, com.aivox.besota.sdk.connect.DeviceConnector.ConnectionListener
    public void notifyWrite(int i) {
        super.notifyWrite(i);
        if (i == 667) {
            if (this.curOtaResult != 2324 || this.sendPackageWithAck) {
                return;
            }
            if (this.USER_FLAG < 1 || !this.isWithoutResponse) {
                new Thread(new Runnable() { // from class: com.aivox.besota.bessdk.service.BesOtaService.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            Thread.sleep(10L);
                            BesOtaService.this.notifyRetryTimes = 0;
                            BesOtaService.this.mOtaCMD.notifySuccess();
                            BesOtaService.this.sendPacketData();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                return;
            }
            return;
        }
        if (this.curOtaResult != 2324 || this.sendPackageWithAck) {
            return;
        }
        if (this.USER_FLAG < 1 || !this.isWithoutResponse) {
            int i2 = this.notifyRetryTimes + 1;
            this.notifyRetryTimes = i2;
            if (i2 == 3) {
                callBackErrorMessage(BesSdkConstants.BES_NOTIFY_ERROR);
            } else {
                sendPacketData();
            }
        }
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public void setOtaConfig(RemoteOTAConfig remoteOTAConfig) {
        LOG(this.TAG, "setOtaConfig: -----" + remoteOTAConfig.getLocalPath());
        if (!new File(remoteOTAConfig.getLocalPath()).exists()) {
            callBackErrorMessage(BesOTAConstants.OTA_START_OTA_ERROR);
            OTAStatus oTAStatus = OTAStatus.STATUS_FAILED;
            this.mOTAStatus = oTAStatus;
            callBackOTAStatusChanged(oTAStatus);
            return;
        }
        if (this.imageSideSelection == 4) {
            if (remoteOTAConfig.getWhatsNewContent("") == null) {
                return;
            }
            if (!new File(remoteOTAConfig.getWhatsNewContent("")).exists()) {
                callBackErrorMessage(BesOTAConstants.OTA_START_OTA_ERROR);
                OTAStatus oTAStatus2 = OTAStatus.STATUS_FAILED;
                this.mOTAStatus = oTAStatus2;
                callBackOTAStatusChanged(oTAStatus2);
                return;
            }
        }
        this.mRemoteOTAConfig = remoteOTAConfig;
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public HmDevice getDevice() {
        return this.mConfig.getDevice();
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public RemoteOTAConfig getOtaConfig() {
        return this.mRemoteOTAConfig;
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public OTAStatus getOTAStatus() {
        return this.mOTAStatus;
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public int getCurrentProgress() {
        return Float.valueOf(this.progress).intValue();
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public OTADfuInfo requestDFUInfo() {
        return this.mOTADfuInfo;
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public boolean startDataTransfer(OTADfuInfo oTADfuInfo, OTATask.StatusListener statusListener) {
        LOG(this.TAG, "startDataTransfer");
        if (this.curOtaResult == 2312) {
            otaSendData(this.mOtaCMD.getStartOTAPacketCMD(this.mRemoteOTAConfig.getLocalPath()));
            return true;
        }
        if (oTADfuInfo != null) {
            this.mOTADfuInfo = oTADfuInfo;
        }
        if (statusListener != null) {
            this.mStatusListener = statusListener;
        }
        if (!checkOtaState()) {
            callBackErrorMessage(BesOTAConstants.OTA_START_OTA_ERROR);
            OTAStatus oTAStatus = OTAStatus.STATUS_FAILED;
            this.mOTAStatus = oTAStatus;
            callBackOTAStatusChanged(oTAStatus);
            return false;
        }
        if (this.mOTADfuInfo.getBreakpoint() == 0) {
            SPHelper.removePreference(this.mContext, BesOTAConstants.BES_OTA_RANDOM_CODE_LEFT + (this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_BLE ? this.mConfig.getDevice().getBleAddress() : this.mConfig.getDevice().getDeviceMAC()));
        }
        if (this.curOtaResult == 2324 && this.mOTAStatus != OTAStatus.STATUS_PAUSED) {
            return false;
        }
        startOta();
        return true;
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public boolean stopDataTransfer() {
        LOG(this.TAG, "stopDataTransfer");
        OTAStatus oTAStatus = OTAStatus.STATUS_CANCELED;
        this.mOTAStatus = oTAStatus;
        callBackOTAStatusChanged(oTAStatus);
        BluetoothLeScanner bluetoothLeScanner = this.mLeScanner;
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(this.mCallback);
            this.mLeScanner = null;
        }
        destoryVariable();
        return true;
    }

    @Override // com.aivox.besota.sdk.ota.OTATask
    public boolean pausedDataTransfer() {
        this.mOTAStatus = OTAStatus.STATUS_PAUSED;
        return true;
    }

    private void callBackOTAStatusChanged(OTAStatus oTAStatus) {
        OTATask.StatusListener statusListener = this.mStatusListener;
        if (statusListener != null) {
            statusListener.onOTAStatusChanged(oTAStatus, this.mConfig.getDevice());
        }
    }

    private void callBackOTAProgressChanged(int i) {
        OTATask.StatusListener statusListener = this.mStatusListener;
        if (statusListener != null) {
            statusListener.onOTAProgressChanged(i, this.mConfig.getDevice());
        }
    }

    private boolean checkOtaState() {
        RemoteOTAConfig remoteOTAConfig = this.mRemoteOTAConfig;
        return (remoteOTAConfig == null || remoteOTAConfig.getLocalPath() == null || !new File(this.mRemoteOTAConfig.getLocalPath()).exists()) ? false : true;
    }

    public void destoryVariable() {
        LOG(this.TAG, "destoryVariable: --------------");
        BesOtaCMD besOtaCMD = this.mOtaCMD;
        if (besOtaCMD != null) {
            besOtaCMD.destoryVariable();
            this.mOtaCMD = null;
        }
        this.roleSwitchRandomID = "";
        this.mRemoteOTAConfig = null;
        this.mOTADfuInfo = null;
        this.mStatusListener = null;
        this.curOtaResult = 0;
    }

    public void getCurrentVersion() {
        if (this.mOtaCMD == null) {
            return;
        }
        LOG(this.TAG, "getCurrentVersion");
        this.mOTAStatus = OTAStatus.STATUS_STARTED;
        otaSendData(this.mOtaCMD.getCurrentVersionCMD());
        addTimeOutMsg(this.besOtaMsgHandler, 5000L, BesOTAConstants.MSG_GET_VERSION_TIME_OUT);
    }

    private void startOta() {
        LOG(this.TAG, "startOta: ------------");
        this.curOtaResult = 0;
        OTAStatus oTAStatus = OTAStatus.STATUS_STARTED;
        this.mOTAStatus = oTAStatus;
        callBackOTAStatusChanged(oTAStatus);
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendUpgrateTypeData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendGetProtocolVersionData() {
        LOG(this.TAG, "sendGetProtocolVersionData");
        otaSendData(this.mOtaCMD.getOtaProtocolVersionCMD(this.currentOrLegacy));
        addTimeOutMsg(this.besOtaMsgHandler, 3000L, BesOTAConstants.MSG_GET_PROTOCOL_VERSION_TIME_OUT);
        Log.i(this.TAG, "addTimeOutMsg MSG_GET_PROTOCOL_VERSION_TIME_OUT: ---------" + this.besOtaMsgHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSetUserData() {
        LOG(this.TAG, "sendSetUserData");
        otaSendData(this.mOtaCMD.getSetOtaUserCMD(this.curUser));
        addTimeOutMsg(this.besOtaMsgHandler, 5000L, BesOTAConstants.MSG_SET_USER_TIME_OUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendGetROLESwitchRandomIDData() {
        if (this.roleSwitchRandomID.length() > 0) {
            sendSelectSideData();
        } else {
            otaSendData(this.mOtaCMD.getROLESwitchRandomIDCMD());
            addTimeOutMsg(this.besOtaMsgHandler, 3000L, BesOTAConstants.MSG_GET_RANDOMID_TIME_OUT);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpgrateTypeData() {
        otaSendData(this.mOtaCMD.getSetUpgrateTypeCMD(this.curUpgateType));
        addTimeOutMsg(this.besOtaMsgHandler, 3000L, BesOTAConstants.MSG_GET_UPGRATE_TYPE_TIME_OUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSelectSideData() {
        otaSendData(this.mOtaCMD.getSelectSideCMD());
        addTimeOutMsg(this.besOtaMsgHandler, 3000L, BesOTAConstants.MSG_GET_SELECT_SIDE_TIME_OUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void besSendData() {
        if (this.mOTAStatus == OTAStatus.STATUS_PAUSED) {
            return;
        }
        if ((this.USER_FLAG == 1 && this.isWithoutResponse) || (this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_SPP && !this.sendPackageWithAck)) {
            sendPacketDataDelay(this.sppSendDataDelay);
        } else {
            sendPacketData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean sendPacketData() {
        LOG(this.TAG, "sendPacketData----" + this.roleSwitchDisconnect);
        if (this.roleSwitchDisconnect) {
            return false;
        }
        synchronized (this.mOtaLock) {
            RemoteOTAConfig remoteOTAConfig = this.mRemoteOTAConfig;
            if (remoteOTAConfig == null) {
                return false;
            }
            byte[] dataPacketCMD = this.mOtaCMD.getDataPacketCMD(remoteOTAConfig.getLocalPath(), this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_BLE || this.mConfig.getDeviceProtocol() == DeviceProtocol.PROTOCOL_GATT_BR_EDR);
            Log.i(this.TAG, "aaaaaaaa curOtaResult: -------" + this.curOtaResult);
            Log.i(this.TAG, "aaaaaaaa: ------- mConfig.getCurUser()" + this.mConfig.getCurUser());
            Log.i(this.TAG, "sendPacketData: ------" + ArrayUtil.toHex(dataPacketCMD));
            int i = 4;
            Log.i(this.TAG, "sendPacketData: ------" + ArrayUtil.toHex(new byte[]{dataPacketCMD[(this.mConfig.getTotaConnect().booleanValue() ? 4 : 0) + (this.curUser == 6 ? 1 : 0)]}));
            Log.i(this.TAG, "sendPacketData: ------" + (this.mConfig.getTotaConnect().booleanValue() ? 4 : 0));
            Log.i(this.TAG, "sendPacketData: ------" + (this.curUser == 6 ? 1 : 0));
            if (dataPacketCMD[(this.mConfig.getTotaConnect().booleanValue() ? 4 : 0) + (this.curUser == 6 ? 1 : 0)] != -123) {
                Log.i(this.TAG, "sendPacketData: -------curOtaResult = 0");
                this.curOtaResult = 0;
            }
            if (this.mConfig.getUSER_FLAG().intValue() == 1 && this.mConfig.getTotaConnect().booleanValue()) {
                if (dataPacketCMD[(this.mConfig.getTotaConnect().booleanValue() ? 4 : 0) + (this.curUser == 6 ? 1 : 0)] == -123) {
                    if (dataPacketCMD[(this.mConfig.getTotaConnect().booleanValue() ? 5 : 1) + (this.curUser == 6 ? 1 : 0)] == 0) {
                        if (dataPacketCMD[(this.mConfig.getTotaConnect().booleanValue() ? 6 : 2) + (this.curUser == 6 ? 1 : 0)] == 0) {
                            this.curOtaResult = 0;
                            return false;
                        }
                    }
                }
            }
            if (!this.mConfig.getTotaConnect().booleanValue()) {
                i = 0;
            }
            if (dataPacketCMD[i + (this.curUser == 6 ? 1 : 0)] == -126) {
                addTimeOutMsg(this.besOtaMsgHandler, 5000L, BesOTAConstants.MSG_GET_CRC_CHECK_PACKAGE_TIME_OUT);
            }
            this.progress = this.mOtaCMD.besOtaProgress();
            Log.i(this.TAG, "progress: --------" + this.progress);
            if (this.progress.length() > 0) {
                LOG(this.TAG, "sendPacketData: -----" + this.progress);
                callBackOTAProgressChanged(Float.valueOf(this.progress).intValue());
                callBackStateChangedMessage(BesOTAConstants.OTA_SEND_DATA_PROGRESS, this.progress);
            }
            if (this.curOtaResult != 0 && this.USER_FLAG == 1 && this.isWithoutResponse) {
                return otaSendDataWithoutResponse(dataPacketCMD);
            }
            return otaSendData(dataPacketCMD);
        }
    }

    private void sendPacketDataDelay(long j) {
        LOG(this.TAG, "sendPacketDataDelay----");
        sendDataDelay(this.besOtaMsgHandler, BesOTAConstants.OTA_CMD_SEND_OTA_DATA, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean otaSendData(byte[] bArr) {
        if (this.mOTAStatus == OTAStatus.STATUS_PAUSED) {
            return false;
        }
        return sendData(bArr);
    }

    private boolean otaSendDataWithoutResponse(byte[] bArr) {
        if (this.mOTAStatus == OTAStatus.STATUS_PAUSED) {
            return false;
        }
        return sendDataWithoutResponse(bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.aivox.besota.bessdk.service.BesOtaService$2] */
    public void scanToReConnected() {
        this.curOtaResult = 0;
        this.mOTAStatus = OTAStatus.STATUS_REBOOT;
        LOG(this.TAG, "scanToReConnected: -----------");
        BluetoothLeScanner bluetoothLeScanner = this.mLeScanner;
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(this.mCallback);
            this.mLeScanner = null;
        }
        this.scanSuccess = false;
        new Thread() { // from class: com.aivox.besota.bessdk.service.BesOtaService.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                super.run();
                try {
                    Thread.sleep(3000L);
                    BesOtaService besOtaService = BesOtaService.this;
                    besOtaService.LOG(besOtaService.TAG, "scanSuccess: --------" + BesOtaService.this.scanSuccess);
                    if (BesOtaService.this.scanSuccess) {
                        return;
                    }
                    BesOtaService besOtaService2 = BesOtaService.this;
                    besOtaService2.LOG(besOtaService2.TAG, "!scanSuccess: --------");
                    BesOtaService.this.scanToReConnected();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        BluetoothLeScanner bluetoothLeScanner2 = BtHeleper.getBluetoothAdapter(this.mContext).getBluetoothLeScanner();
        this.mLeScanner = bluetoothLeScanner2;
        bluetoothLeScanner2.startScan((List<ScanFilter>) null, new ScanSettings.Builder().setScanMode(0).build(), this.mCallback);
    }

    private void startSppRoleSwitchTimer() {
        LOG(this.TAG, "startSppRoleSwitchTimer----");
        stopSppRoleSwitchTimer();
        this.sppRoleSwitchTimes = 0;
        this.sppRoleSwitchTimer = new Timer();
        TimerTask timerTask = new TimerTask() { // from class: com.aivox.besota.bessdk.service.BesOtaService.5
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                BesOtaService.this.sppRoleSwitchTimes++;
                if (BesOtaService.this.sppRoleSwitchTimes > 5) {
                    BesOtaService.this.stopSppRoleSwitchTimer();
                    BesOtaService.this.mDeviceConnector.disconnect(BesOtaService.this.mConfig.getDevice());
                }
            }
        };
        this.task = timerTask;
        this.sppRoleSwitchTimer.schedule(timerTask, 0L, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopSppRoleSwitchTimer() {
        LOG(this.TAG, "stopSppRoleSwitchTimer----");
        Timer timer = this.sppRoleSwitchTimer;
        if (timer != null) {
            timer.cancel();
            this.sppRoleSwitchTimer = null;
        }
    }
}
