package com.jieli.jl_bt_ota.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.github.houbb.heaven.constant.FileOptionConst;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.constant.JL_Constant;
import com.jieli.jl_bt_ota.constant.StateCode;
import com.jieli.jl_bt_ota.impl.RcspAuth;
import com.jieli.jl_bt_ota.interfaces.IActionCallback;
import com.jieli.jl_bt_ota.interfaces.IUpgradeCallback;
import com.jieli.jl_bt_ota.model.DataInfo;
import com.jieli.jl_bt_ota.model.FileOffset;
import com.jieli.jl_bt_ota.model.OTAError;
import com.jieli.jl_bt_ota.model.ReConnectDevMsg;
import com.jieli.jl_bt_ota.model.ReconnectParam;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.model.command.EnterUpdateModeCmd;
import com.jieli.jl_bt_ota.model.command.ExitUpdateModeCmd;
import com.jieli.jl_bt_ota.model.command.FirmwareUpdateBlockCmd;
import com.jieli.jl_bt_ota.model.command.NotifyUpdateContentSizeCmd;
import com.jieli.jl_bt_ota.model.command.SettingsMtuCmd;
import com.jieli.jl_bt_ota.model.parameter.FirmwareUpdateBlockParam;
import com.jieli.jl_bt_ota.model.parameter.FirmwareUpdateBlockResponseParam;
import com.jieli.jl_bt_ota.model.parameter.NotifyUpdateContentSizeParam;
import com.jieli.jl_bt_ota.model.parameter.SettingsMtuParam;
import com.jieli.jl_bt_ota.model.response.EnterUpdateModeResponse;
import com.jieli.jl_bt_ota.model.response.ExitUpdateModeResponse;
import com.jieli.jl_bt_ota.model.response.SettingsMtuResponse;
import com.jieli.jl_bt_ota.model.response.TargetInfoResponse;
import com.jieli.jl_bt_ota.thread.ReadFileThread;
import com.jieli.jl_bt_ota.tool.DataHandler;
import com.jieli.jl_bt_ota.tool.DataHandlerModify;
import com.jieli.jl_bt_ota.tool.DeviceReConnectManager;
import com.jieli.jl_bt_ota.tool.ParseHelper;
import com.jieli.jl_bt_ota.tool.RcspOTA;
import com.jieli.jl_bt_ota.tool.UpgradeCbHelper;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.FileUtil;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BluetoothOTAManager extends BluetoothBreProfiles implements RcspAuth.IRcspAuthOp {

    /* JADX INFO: renamed from: A */
    private static final int f635A = 4663;

    /* JADX INFO: renamed from: B */
    private static final int f636B = 4664;

    /* JADX INFO: renamed from: C */
    private static final int f637C = 4665;

    /* JADX INFO: renamed from: D */
    private static final int f638D = 4672;

    /* JADX INFO: renamed from: E */
    private static final int f639E = 4673;

    /* JADX INFO: renamed from: F */
    private static final int f640F = 4674;
    public static long FILE_CACHE_DATA_LIMIT = 2097152;
    public static boolean IS_SUPPORT_NEW_RECONNECT_WAY = true;
    public static boolean IS_USE_MODIFY_DATA_HANDLER = true;

    /* JADX INFO: renamed from: u */
    private static final long f641u = 6000;

    /* JADX INFO: renamed from: v */
    private static final long f642v = 1000;

    /* JADX INFO: renamed from: w */
    private static final long f643w = 5000;

    /* JADX INFO: renamed from: x */
    private static final int f644x = 4660;

    /* JADX INFO: renamed from: y */
    private static final int f645y = 4661;

    /* JADX INFO: renamed from: z */
    private static final int f646z = 4662;

    /* JADX INFO: renamed from: G */
    private final RcspOTA f647G;

    /* JADX INFO: renamed from: H */
    private final DeviceReConnectManager f648H;

    /* JADX INFO: renamed from: I */
    private final RcspAuth f649I;

    /* JADX INFO: renamed from: J */
    private final UpgradeCbHelper f650J;

    /* JADX INFO: renamed from: K */
    private ExecutorService f651K;

    /* JADX INFO: renamed from: L */
    private volatile boolean f652L;

    /* JADX INFO: renamed from: M */
    private volatile byte[] f653M;

    /* JADX INFO: renamed from: N */
    private volatile RandomAccessFile f654N;

    /* JADX INFO: renamed from: O */
    private long f655O;

    /* JADX INFO: renamed from: P */
    private long f656P;

    /* JADX INFO: renamed from: Q */
    private long f657Q;

    /* JADX INFO: renamed from: R */
    private int f658R;

    /* JADX INFO: renamed from: S */
    private int f659S;

    /* JADX INFO: renamed from: T */
    private ReconnectParam f660T;

    /* JADX INFO: renamed from: U */
    private final Handler f661U;

    /* JADX INFO: renamed from: V */
    private final RcspAuth.OnRcspAuthListener f662V;

    public BluetoothOTAManager(Context context) {
        super(context);
        this.f652L = false;
        this.f655O = 20000L;
        this.f656P = 0L;
        this.f657Q = 0L;
        this.f658R = 0;
        this.f659S = 0;
        this.f661U = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                int i = message.what;
                if (i == BluetoothOTAManager.f636B) {
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) message.obj;
                    JL_Log.m853w(BluetoothOTAManager.this.TAG, "MSG_WAIT_EDR_DISCONNECT", "sppDevice :" + BluetoothOTAManager.this.printBtDeviceInfo(bluetoothDevice));
                    BluetoothOTAManager.this.m695h(bluetoothDevice);
                } else if (i != BluetoothOTAManager.f637C) {
                    switch (i) {
                        case BluetoothOTAManager.f644x /* 4660 */:
                            BluetoothOTAManager.this.m660a("RECEIVE_CMD_TIMEOUT", OTAError.buildError(ErrorCode.SUB_ERR_WAITING_COMMAND_TIMEOUT));
                            break;
                        case BluetoothOTAManager.f645y /* 4661 */:
                            BluetoothDevice bluetoothDevice2 = (BluetoothDevice) message.obj;
                            JL_Log.m849i(BluetoothOTAManager.this.TAG, "MSG_CHANGE_BLE_MTU_TIMEOUT", "device : " + BluetoothOTAManager.this.printBtDeviceInfo(bluetoothDevice2));
                            if (!BluetoothOTAManager.this.m701i(bluetoothDevice2)) {
                                BluetoothOTAManager.this.m673c(bluetoothDevice2, 2);
                            } else {
                                BluetoothOTAManager.this.m666b(bluetoothDevice2, 0);
                            }
                            break;
                        case BluetoothOTAManager.f646z /* 4662 */:
                            boolean zIsOTA = BluetoothOTAManager.this.isOTA();
                            BluetoothDevice connectedBtDevice = BluetoothOTAManager.this.getConnectedBtDevice();
                            boolean zIsConnectedDevice = BluetoothOTAManager.this.isConnectedDevice(connectedBtDevice);
                            boolean zIsWaitingForUpdate = BluetoothOTAManager.this.f648H.isWaitingForUpdate();
                            ReconnectParam reconnectParam = BluetoothOTAManager.this.f660T;
                            BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                            JL_Log.m849i(bluetoothOTAManager.TAG, "MSG_OTA_RECONNECT_DEVICE", CommonUtil.formatString("device : %s, isOTA = %s, isWaitingForUpdate = %s, isConnectedDevice = %s\n %s", bluetoothOTAManager.printBtDeviceInfo(connectedBtDevice), Boolean.valueOf(zIsOTA), Boolean.valueOf(zIsWaitingForUpdate), Boolean.valueOf(zIsConnectedDevice), reconnectParam));
                            if (zIsOTA && zIsWaitingForUpdate) {
                                if (zIsConnectedDevice || reconnectParam == null) {
                                    JL_Log.m853w(BluetoothOTAManager.this.TAG, "MSG_OTA_RECONNECT_DEVICE", "Is the implementation of getConnectedBtDevice() incorrect?");
                                    BluetoothOTAManager.this.onBtDeviceConnection(connectedBtDevice, 1);
                                } else {
                                    BluetoothOTAManager bluetoothOTAManager2 = BluetoothOTAManager.this;
                                    bluetoothOTAManager2.m670b(bluetoothOTAManager2.f648H.getReconnectAddress(), reconnectParam.getFlag() == 1);
                                    if (BluetoothOTAManager.this.getBluetoothOption().isUseReconnect()) {
                                        BluetoothOTAManager.this.f661U.sendEmptyMessageDelayed(BluetoothOTAManager.f639E, DeviceReConnectManager.RECONNECT_TIMEOUT);
                                    } else {
                                        BluetoothOTAManager.this.f648H.startReconnectTask();
                                    }
                                    BluetoothOTAManager.this.m657a((ReconnectParam) null);
                                }
                            }
                            break;
                        default:
                            switch (i) {
                                case BluetoothOTAManager.f638D /* 4672 */:
                                    JL_Log.m845d(BluetoothOTAManager.this.TAG, "MSG_CALLBACK_OTA_FINISH", "---->");
                                    BluetoothOTAManager.this.m690g();
                                    break;
                                case BluetoothOTAManager.f639E /* 4673 */:
                                    BluetoothOTAManager.this.m660a("OTA_RECONNECT_DEVICE_TIMEOUT", OTAError.buildError(ErrorCode.SUB_ERR_RECONNECT_TIMEOUT));
                                    break;
                                case BluetoothOTAManager.f640F /* 4674 */:
                                    Object obj = message.obj;
                                    if (!(obj instanceof BluetoothDevice)) {
                                        return false;
                                    }
                                    BluetoothDevice bluetoothDevice3 = (BluetoothDevice) obj;
                                    JL_Log.m845d(BluetoothOTAManager.this.TAG, "MSG_DISCONNECT_DEVICE_TIMEOUT", "device : " + bluetoothDevice3);
                                    BluetoothOTAManager.this.onBtDeviceConnection(bluetoothDevice3, 0);
                                    break;
                                    break;
                            }
                            break;
                    }
                } else {
                    JL_Log.m853w(BluetoothOTAManager.this.TAG, "MSG_WAIT_DEVICE_DISCONNECT", "---->");
                    BluetoothOTAManager bluetoothOTAManager3 = BluetoothOTAManager.this;
                    bluetoothOTAManager3.m637a(bluetoothOTAManager3.getConnectedBtDevice(), BluetoothOTAManager.this.f660T);
                }
                return true;
            }
        });
        RcspAuth.OnRcspAuthListener onRcspAuthListener = new RcspAuth.OnRcspAuthListener() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.14
            @Override // com.jieli.jl_bt_ota.impl.RcspAuth.OnRcspAuthListener
            public void onAuthFailed(BluetoothDevice bluetoothDevice, int i, String str) {
                BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                JL_Log.m853w(bluetoothOTAManager.TAG, "onAuthFailed", CommonUtil.formatString("device : %s, code : %d, message : %s", bluetoothOTAManager.printBtDeviceInfo(bluetoothDevice), Integer.valueOf(i), str));
                BluetoothOTAManager.this.mDeviceStatusCache.updateDeviceIsAuth(bluetoothDevice, false);
                if (BluetoothOTAManager.this.f648H.isDeviceReconnecting()) {
                    BluetoothOTAManager.this.m673c(bluetoothDevice, 2);
                } else {
                    BluetoothOTAManager.this.m638a(bluetoothDevice, OTAError.buildError(ErrorCode.SUB_ERR_AUTH_DEVICE, i, str));
                }
            }

            @Override // com.jieli.jl_bt_ota.impl.RcspAuth.OnRcspAuthListener
            public void onAuthSuccess(BluetoothDevice bluetoothDevice) {
                BluetoothOTAManager.this.mDeviceStatusCache.updateDeviceIsAuth(bluetoothDevice, true);
                boolean zM701i = BluetoothOTAManager.this.m701i(bluetoothDevice);
                BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                JL_Log.m853w(bluetoothOTAManager.TAG, "onAuthSuccess", CommonUtil.formatString("device[%s] auth ok, isBleConnected = %s", bluetoothOTAManager.printBtDeviceInfo(bluetoothDevice), Boolean.valueOf(zM701i)));
                if (zM701i) {
                    BluetoothOTAManager.this.m709l(bluetoothDevice);
                } else {
                    BluetoothOTAManager.this.m666b(bluetoothDevice, 1);
                }
            }

            @Override // com.jieli.jl_bt_ota.impl.RcspAuth.OnRcspAuthListener
            public void onInitResult(boolean z) {
                JL_Log.m845d(BluetoothOTAManager.this.TAG, "onInitResult", "result : " + z);
            }
        };
        this.f662V = onRcspAuthListener;
        JL_Log.m849i(this.TAG, "init", String.format(Locale.ENGLISH, "Lib version name = %s(%d)", JL_Constant.getLibVersionName(), Integer.valueOf(JL_Constant.getLibVersionCode())));
        this.f647G = new RcspOTA(this);
        this.f648H = new DeviceReConnectManager(context, this);
        this.f649I = new RcspAuth(context, this, onRcspAuthListener);
        this.f650J = new UpgradeCbHelper();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: l */
    public void m709l(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return;
        }
        if (this.f661U.hasMessages(f645y)) {
            JL_Log.m853w(this.TAG, "startChangeMtu", "Adjusting the MTU for BLE");
            return;
        }
        boolean zM681d = (!this.mBluetoothOption.isNeedChangeMtu() || this.mBluetoothOption.getMtu() <= 20) ? false : m681d(bluetoothDevice, this.mBluetoothOption.getMtu());
        JL_Log.m845d(this.TAG, "startChangeMtu", "requestBleMtu : " + zM681d);
        if (!zM681d) {
            m666b(bluetoothDevice, 0);
        } else {
            Handler handler = this.f661U;
            handler.sendMessageDelayed(handler.obtainMessage(f645y, bluetoothDevice), f643w);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: m */
    public /* synthetic */ void m710m() {
        this.f650J.setUpgradeCallback(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: n */
    public /* synthetic */ void m712n() {
        m660a("startReadFileThread", OTAError.buildError(ErrorCode.SUB_ERR_FILE_NOT_FOUND));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: o */
    public void m714o() {
        if (this.f661U.hasMessages(f638D)) {
            this.f661U.removeMessages(f638D);
            this.f661U.sendEmptyMessage(f638D);
        }
    }

    /* JADX INFO: renamed from: p */
    private void m715p() {
        JL_Log.m853w(this.TAG, "releaseDataHandler", "---->");
        if (this.dataHandler != null) {
            this.dataHandler.release();
            this.dataHandler = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: q */
    public void m716q() {
        if (this.f648H.isWaitingForUpdate()) {
            this.f648H.setReConnectDevMsg(null);
            this.f648H.stopReconnectTask();
        }
    }

    /* JADX INFO: renamed from: r */
    private void m717r() {
        m676c(false);
        m720u();
        m718s();
        m680d(true);
        m657a((ReconnectParam) null);
        if (this.f653M != null) {
            this.f653M = null;
            System.gc();
        }
        if (this.f654N != null) {
            try {
                this.f654N.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.f654N = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: s */
    public void m718s() {
        this.f659S = 0;
        this.f658R = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: t */
    public void m719t() {
        if (!m671b("startReceiveCmdTimeout") && this.f655O > 0) {
            this.f661U.removeMessages(f644x);
            this.f661U.sendEmptyMessageDelayed(f644x, this.f655O);
        }
    }

    /* JADX INFO: renamed from: u */
    private void m720u() {
        this.f661U.removeMessages(f644x);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: v */
    public void m721v() {
        if (m671b("upgradeStep03")) {
            return;
        }
        this.f647G.enterUpdateMode(new IActionCallback<Integer>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.10
            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onError(BaseError baseError) {
                BluetoothOTAManager.this.m660a("upgradeStep03", baseError);
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onSuccess(Integer num) {
                JL_Log.m848i(BluetoothOTAManager.this.TAG, "Step3.请求进入升级模式, 结果码: " + num);
                if (num.intValue() == 0) {
                    BluetoothOTAManager.this.m719t();
                } else {
                    onError(OTAError.buildError(ErrorCode.SUB_ERR_OTA_FAILED, "Device is not allowed to enter the upgrade mode : " + num));
                }
            }
        });
    }

    /* JADX INFO: renamed from: w */
    private void m722w() {
        if (m671b("upgradeStep05")) {
            return;
        }
        this.f647G.queryUpdateResult(new IActionCallback<Integer>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.12
            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onError(BaseError baseError) {
                BluetoothOTAManager.this.m660a("upgradeStep05", baseError);
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onSuccess(Integer num) {
                BaseError baseErrorBuildError;
                JL_Log.m849i(BluetoothOTAManager.this.TAG, "upgradeStep05", "Step05.询问升级状态, 结果码: " + num);
                boolean z = false;
                if (num.intValue() == 0) {
                    BluetoothOTAManager.this.m676c(false);
                    if (BluetoothOTAManager.this.mBluetoothOption.isPriorityCallbackOtaFinish()) {
                        BluetoothOTAManager.this.m690g();
                    } else {
                        BluetoothOTAManager.this.f661U.removeMessages(BluetoothOTAManager.f638D);
                        BluetoothOTAManager.this.f661U.sendEmptyMessageDelayed(BluetoothOTAManager.f638D, 500L);
                    }
                    BluetoothOTAManager.this.f647G.rebootDevice(new IActionCallback<Boolean>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.12.1
                        @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                        public void onError(BaseError baseError) {
                        }

                        @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                        public void onSuccess(Boolean bool) {
                            BluetoothOTAManager.this.m714o();
                        }
                    });
                    return;
                }
                if (num.intValue() == 128) {
                    BluetoothOTAManager.this.resetTotalTime();
                    BluetoothOTAManager.this.m718s();
                    TargetInfoResponse deviceInfo = BluetoothOTAManager.this.getDeviceInfo();
                    if (deviceInfo != null && deviceInfo.isSupportDoubleBackup()) {
                        z = true;
                    }
                    JL_Log.m849i(BluetoothOTAManager.this.TAG, "upgradeStep05", "download resource finish. isSupportDoubleBackup : " + z);
                    if (z) {
                        BluetoothOTAManager.this.m721v();
                        return;
                    } else {
                        BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                        bluetoothOTAManager.m707k(bluetoothOTAManager.getConnectedBtDevice());
                        return;
                    }
                }
                switch (num.intValue()) {
                    case 1:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_CHECK_RECEIVED_DATA_FAILED);
                        break;
                    case 2:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_OTA_FAILED, "Device return update failed.");
                        break;
                    case 3:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_UPGRADE_KEY_NOT_MATCH);
                        break;
                    case 4:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_CHECK_UPGRADE_FILE);
                        break;
                    case 5:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_UPGRADE_TYPE_NOT_MATCH);
                        break;
                    case 6:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_UPGRADE_DATA_LEN);
                        break;
                    case 7:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_UPGRADE_FLASH_READ);
                        break;
                    case 8:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_UPGRADE_CMD_TIMEOUT);
                        break;
                    case 9:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_UPGRADE_SAME_FILE);
                        break;
                    default:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_UPGRADE_UNKNOWN, num.intValue(), "Device returned to an unknown code : " + num);
                        break;
                }
                onError(baseErrorBuildError);
            }
        });
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void cancelOTA() {
        m699i();
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void errorEventCallback(BaseError baseError) {
        this.mBtEventCbHelper.onError(baseError);
        m660a("errorEventCallback", baseError);
    }

    public int getCommunicationMtu(BluetoothDevice bluetoothDevice) {
        return this.mDeviceStatusCache.getMaxCommunicationMtu(bluetoothDevice);
    }

    public TargetInfoResponse getDeviceInfo(BluetoothDevice bluetoothDevice) {
        return this.mDeviceStatusCache.getDeviceInfo(bluetoothDevice);
    }

    public int getReceiveMtu(BluetoothDevice bluetoothDevice) {
        return this.mDeviceStatusCache.getMaxReceiveMtu(bluetoothDevice);
    }

    public long getTimeout_ms() {
        return this.f655O;
    }

    public long getTotalTime() {
        return this.f657Q;
    }

    public int getUpdateContentSize() {
        return this.f658R;
    }

    public boolean isOTA() {
        return this.f652L;
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase
    public void onA2dpStatus(BluetoothDevice bluetoothDevice, int i) {
        super.onA2dpStatus(bluetoothDevice, i);
        m636a(bluetoothDevice, 2, i);
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothDiscovery, com.jieli.jl_bt_ota.impl.BluetoothBase
    public void onAdapterStatus(boolean z, boolean z2) {
        super.onAdapterStatus(z, z2);
        if (z) {
            return;
        }
        m660a("onAdapterStatus", OTAError.buildError(4099));
        BluetoothDevice connectedBtDevice = getConnectedBtDevice();
        if (connectedBtDevice != null) {
            m673c(connectedBtDevice, 0);
        }
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase
    public void onBleDataBlockChanged(BluetoothDevice bluetoothDevice, int i, int i2) {
        super.onBleDataBlockChanged(bluetoothDevice, i, i2);
        JL_Log.m849i(this.TAG, "onBleDataBlockChanged", CommonUtil.formatString("device : %s, block : %d, status : %d", printBtDeviceInfo(bluetoothDevice), Integer.valueOf(i), Integer.valueOf(i2)));
        if (this.f661U.hasMessages(f645y)) {
            this.f661U.removeMessages(f645y);
            JL_Log.m849i(this.TAG, "onBleDataBlockChanged", "handleConnectedEvent >>>");
            m666b(bluetoothDevice, 0);
        }
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase, com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void onBtDeviceConnection(BluetoothDevice bluetoothDevice, int i) {
        super.onBtDeviceConnection(bluetoothDevice, i);
        JL_Log.m849i(this.TAG, "onBtDeviceConnection", "device : " + printBtDeviceInfo(bluetoothDevice) + ", " + StateCode.printConnectionState(i));
        if (i != 3) {
            this.f661U.removeMessages(f639E);
        }
        if (i != 1) {
            if (BluetoothUtil.deviceEquals(bluetoothDevice, getConnectedBtDevice())) {
                setConnectedBtDevice(null);
            }
            m673c(bluetoothDevice, i);
            return;
        }
        if (this.dataHandler == null) {
            this.dataHandler = IS_USE_MODIFY_DATA_HANDLER ? new DataHandlerModify(this) : new DataHandler(this);
        }
        if (checkDeviceIsCertify(bluetoothDevice)) {
            if (m701i(bluetoothDevice)) {
                m709l(bluetoothDevice);
                return;
            } else {
                m666b(bluetoothDevice, 1);
                return;
            }
        }
        this.f649I.stopAuth(bluetoothDevice, false);
        if (this.f649I.startAuth(bluetoothDevice)) {
            return;
        }
        m638a(bluetoothDevice, OTAError.buildError(ErrorCode.SUB_ERR_AUTH_DEVICE));
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006d  */
    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onConnection(android.bluetooth.BluetoothDevice r8, int r9) {
        /*
            Method dump skipped, instruction units count: 212
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.onConnection(android.bluetooth.BluetoothDevice, int):void");
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBase
    public void onHfpStatus(BluetoothDevice bluetoothDevice, int i) {
        super.onHfpStatus(bluetoothDevice, i);
        m636a(bluetoothDevice, 1, i);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
        if (bluetoothGatt == null) {
            return;
        }
        int i3 = i2 == 0 ? i - 3 : 20;
        JL_Log.m847e(this.TAG, "onMtuChanged", "bleMtu : " + i3);
        onBleDataBlockChanged(bluetoothGatt.getDevice(), i3, i2);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void onReceiveDeviceData(BluetoothDevice bluetoothDevice, byte[] bArr) {
        if (bluetoothDevice == null || bArr == null) {
            return;
        }
        JL_Log.m845d(this.TAG, "onReceiveDeviceData", "device : " + printBtDeviceInfo(bluetoothDevice) + ", recv data : " + CHexConver.byte2HexStr(bArr));
        if (!checkDeviceIsCertify(bluetoothDevice)) {
            JL_Log.m849i(this.TAG, "onReceiveDeviceData", "handleAuthData ");
            this.f649I.handleAuthData(bluetoothDevice, bArr);
        } else {
            if (this.dataHandler == null) {
                JL_Log.m849i(this.TAG, "onReceiveDeviceData", "No data processor.");
                return;
            }
            DataInfo recvData = new DataInfo().setType(1).setDevice(bluetoothDevice).setRecvData(bArr);
            this.dataHandler.addRecvData(recvData);
            JL_Log.m845d(this.TAG, "onReceiveDeviceData", "addRecvData : " + recvData);
        }
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void queryMandatoryUpdate(final IActionCallback<TargetInfoResponse> iActionCallback) {
        final BluetoothDevice connectedBtDevice = getConnectedBtDevice();
        if (connectedBtDevice == null) {
            JL_Log.m853w(this.TAG, "queryMandatoryUpdate", "Bluetooth device is disconnected.");
            if (iActionCallback != null) {
                iActionCallback.onError(OTAError.buildError(ErrorCode.SUB_ERR_REMOTE_NOT_CONNECTED));
                return;
            }
            return;
        }
        TargetInfoResponse deviceInfo = getDeviceInfo(connectedBtDevice);
        JL_Log.m849i(this.TAG, "queryMandatoryUpdate", "cache deviceInfo : " + deviceInfo);
        if (deviceInfo == null) {
            this.f647G.getDeviceInfo(new IActionCallback<TargetInfoResponse>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.2
                @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                public void onError(BaseError baseError) {
                    IActionCallback iActionCallback2 = iActionCallback;
                    if (iActionCallback2 != null) {
                        iActionCallback2.onError(baseError);
                    }
                }

                @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                public void onSuccess(TargetInfoResponse targetInfoResponse) {
                    BluetoothOTAManager.this.mDeviceStatusCache.updateDeviceTargetInfo(connectedBtDevice, targetInfoResponse);
                    BluetoothOTAManager.this.queryMandatoryUpdate(iActionCallback);
                }
            });
            return;
        }
        if (deviceInfo.isMandatoryUpgrade() || deviceInfo.getRequestOtaFlag() == 1) {
            if (iActionCallback != null) {
                iActionCallback.onSuccess(deviceInfo);
            }
            this.mBtEventCbHelper.onMandatoryUpgrade(connectedBtDevice);
        } else if (iActionCallback != null) {
            iActionCallback.onError(OTAError.buildError(0, "Device is connected."));
        }
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void receiveDataFromDevice(BluetoothDevice bluetoothDevice, byte[] bArr) {
        ArrayList<BasePacket> arrayListFindPacketData = ParseHelper.findPacketData(bluetoothDevice, getReceiveMtu(bluetoothDevice), bArr);
        if (arrayListFindPacketData == null || arrayListFindPacketData.isEmpty()) {
            JL_Log.m853w(this.TAG, "receiveDataFromDevice", "No ota command found.");
            return;
        }
        int size = arrayListFindPacketData.size();
        int i = 0;
        while (i < size) {
            BasePacket basePacket = arrayListFindPacketData.get(i);
            i++;
            BasePacket basePacket2 = basePacket;
            CommandBase commandBaseConvert2Command = ParseHelper.convert2Command(basePacket2, getCacheCommand(bluetoothDevice, basePacket2));
            if (commandBaseConvert2Command == null) {
                JL_Log.m853w(this.TAG, "receiveDataFromDevice", "Failed to parse command");
            } else {
                JL_Log.m845d(this.TAG, "receiveDataFromDevice", "" + commandBaseConvert2Command);
                if (basePacket2.getType() == 1) {
                    onReceiveCommand(bluetoothDevice, commandBaseConvert2Command);
                    m640a(bluetoothDevice, commandBaseConvert2Command, basePacket2.getHasResponse() == 1);
                } else {
                    m639a(bluetoothDevice, commandBaseConvert2Command);
                }
            }
        }
    }

    @Override // com.jieli.jl_bt_ota.impl.BluetoothBreProfiles, com.jieli.jl_bt_ota.impl.BluetoothDiscovery, com.jieli.jl_bt_ota.impl.BluetoothBase, com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void release() {
        super.release();
        cancelOTA();
        m717r();
        this.f648H.release();
        ExecutorService executorService = this.f651K;
        if (executorService != null && !executorService.isShutdown()) {
            this.f651K.shutdownNow();
            this.f651K = null;
        }
        this.f649I.removeListener(this.f662V);
        this.f649I.destroy();
        this.f650J.release();
        this.f661U.removeCallbacksAndMessages(null);
        m715p();
    }

    public void resetTotalTime() {
        this.f657Q = 0L;
    }

    @Override // com.jieli.jl_bt_ota.impl.RcspAuth.IRcspAuthOp
    public boolean sendAuthDataToDevice(BluetoothDevice bluetoothDevice, byte[] bArr) {
        return sendDataToDevice(bluetoothDevice, bArr);
    }

    public void setReconnectAddress(String str) {
        if (this.f648H.isWaitingForUpdate() && BluetoothAdapter.checkBluetoothAddress(str)) {
            this.f648H.setReconnectAddress(str);
        }
    }

    public void setTimeout_ms(long j) {
        this.f655O = j;
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void startOTA(IUpgradeCallback iUpgradeCallback) {
        BluetoothDevice connectedBtDevice = getConnectedBtDevice();
        if (connectedBtDevice == null) {
            JL_Log.m853w(this.TAG, "startOTA", "Bluetooth device is disconnected.");
            if (iUpgradeCallback != null) {
                iUpgradeCallback.onError(OTAError.buildError(ErrorCode.SUB_ERR_REMOTE_NOT_CONNECTED));
                return;
            }
            return;
        }
        if (isOTA()) {
            JL_Log.m853w(this.TAG, "startOTA", "OTA in progress.");
            if (iUpgradeCallback != null) {
                iUpgradeCallback.onError(OTAError.buildError(ErrorCode.SUB_ERR_OTA_IN_HANDLE));
                return;
            }
            return;
        }
        if (!getBluetoothOption().isUseAuthDevice()) {
            this.mDeviceStatusCache.updateDeviceIsAuth(connectedBtDevice, true);
        }
        m676c(true);
        this.f650J.setUpgradeCallback(iUpgradeCallback);
        if (FileUtil.checkFileExist(getBluetoothOption().getFirmwareFilePath())) {
            m641a(connectedBtDevice, getBluetoothOption().getFirmwareFilePath());
            return;
        }
        if (getBluetoothOption().getFirmwareFileData() == null || getBluetoothOption().getFirmwareFileData().length <= 0) {
            m660a("startOTA", OTAError.buildError(ErrorCode.SUB_ERR_DATA_NOT_FOUND));
            return;
        }
        this.f653M = getBluetoothOption().getFirmwareFileData();
        JL_Log.m845d(this.TAG, "startOTA", "data size = " + (this.f653M == null ? 0 : this.f653M.length));
        m687f();
        m711m(connectedBtDevice);
    }

    /* JADX INFO: renamed from: j */
    private boolean m704j() {
        return getConnectedBtDevice() != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: k */
    public /* synthetic */ void m706k() {
        this.f650J.setUpgradeCallback(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: m */
    public void m711m(final BluetoothDevice bluetoothDevice) {
        if (m671b("upgradePrepare")) {
            return;
        }
        if (getDeviceInfo(bluetoothDevice) == null) {
            this.f647G.getDeviceInfo(new IActionCallback<TargetInfoResponse>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.5
                @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                public void onError(BaseError baseError) {
                    BluetoothOTAManager.this.m660a("upgradePrepare", baseError);
                }

                @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                public void onSuccess(TargetInfoResponse targetInfoResponse) {
                    BluetoothOTAManager.this.mDeviceStatusCache.updateDeviceTargetInfo(bluetoothDevice, targetInfoResponse);
                    BluetoothOTAManager.this.m713n(bluetoothDevice);
                }
            });
        } else {
            m713n(bluetoothDevice);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: n */
    public void m713n(final BluetoothDevice bluetoothDevice) {
        if (m671b("upgradeStep01")) {
            return;
        }
        this.f647G.readUpgradeFileFlag(new IActionCallback<FileOffset>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.6
            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onError(BaseError baseError) {
                BluetoothOTAManager.this.m660a("upgradeStep01", baseError);
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onSuccess(FileOffset fileOffset) {
                JL_Log.m848i(BluetoothOTAManager.this.TAG, CommonUtil.formatString("Step01.获取升级文件信息的偏移地址, %s", fileOffset));
                BluetoothOTAManager.this.m635a(bluetoothDevice, 0.0f);
                BluetoothOTAManager.this.m667b(bluetoothDevice, fileOffset.getOffset(), fileOffset.getLen());
            }
        });
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public TargetInfoResponse getDeviceInfo() {
        return getDeviceInfo(getConnectedBtDevice());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: c */
    public void m676c(boolean z) {
        this.f652L = z;
    }

    /* JADX INFO: renamed from: d */
    private boolean m681d(BluetoothDevice bluetoothDevice, int i) {
        if (!CommonUtil.checkHasConnectPermission(this.context)) {
            JL_Log.m847e(this.TAG, "requestBleMtu", "Missing connect permission");
            return false;
        }
        BluetoothGatt connectedBluetoothGatt = getConnectedBluetoothGatt();
        if (connectedBluetoothGatt != null && BluetoothUtil.deviceEquals(connectedBluetoothGatt.getDevice(), bluetoothDevice)) {
            JL_Log.m847e(this.TAG, "requestBleMtu", "requestMtu is started.");
            if (connectedBluetoothGatt.requestMtu(i + 3)) {
                return true;
            }
            JL_Log.m847e(this.TAG, "requestBleMtu", "requestMtu failed. callback old mtu.");
            onBleDataBlockChanged(bluetoothDevice, 20, ErrorCode.SUB_ERR_CHANGE_BLE_MTU);
            return false;
        }
        JL_Log.m847e(this.TAG, "requestBleMtu", "Device is disconnected.");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: e */
    public void m682e() {
        JL_Log.m852w(this.TAG, "callbackCancelOTA : ");
        m717r();
        this.f650J.onCancelOTA();
        this.f661U.postDelayed(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m706k();
            }
        }, 100L);
    }

    /* JADX INFO: renamed from: f */
    private int m686f(BluetoothDevice bluetoothDevice) {
        int priority = this.mBluetoothOption.getPriority();
        TargetInfoResponse deviceInfo = getDeviceInfo(bluetoothDevice);
        if (deviceInfo != null && !deviceInfo.isSupportDoubleBackup()) {
            int singleBackupOtaWay = deviceInfo.getSingleBackupOtaWay();
            if (singleBackupOtaWay == 1) {
                return 0;
            }
            if (singleBackupOtaWay == 2) {
                return 1;
            }
            if (deviceInfo.getSdkType() >= 2) {
                return 0;
            }
        }
        return priority;
    }

    /* JADX INFO: renamed from: g */
    private void m691g(final BluetoothDevice bluetoothDevice) {
        JL_Log.m845d(this.TAG, "getDeviceInfoWithConnection", "---->");
        this.f647G.getDeviceInfo(new IActionCallback<TargetInfoResponse>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.4
            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onError(BaseError baseError) {
                BluetoothOTAManager.this.m638a(bluetoothDevice, baseError);
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onSuccess(TargetInfoResponse targetInfoResponse) {
                BluetoothOTAManager.this.mDeviceStatusCache.updateDeviceTargetInfo(bluetoothDevice, targetInfoResponse);
                if (targetInfoResponse.isSupportMD5()) {
                    BluetoothOTAManager.this.f647G.getMD5(new IActionCallback<String>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.4.1
                        @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                        public void onError(BaseError baseError) {
                            JL_Log.m849i(BluetoothOTAManager.this.TAG, "getDeviceInfoWithConnection#getDevMD5", "onError, " + baseError);
                        }

                        @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                        public void onSuccess(String str) {
                            JL_Log.m849i(BluetoothOTAManager.this.TAG, "getDeviceInfoWithConnection#getDevMD5", "onSuccess, MD5 : " + str);
                            C19964 c19964 = C19964.this;
                            BluetoothOTAManager.this.mDeviceStatusCache.updateDeviceMD5(bluetoothDevice, str);
                        }
                    });
                }
                if (targetInfoResponse.isMandatoryUpgrade()) {
                    JL_Log.m853w(BluetoothOTAManager.this.TAG, "getDeviceInfoWithConnection", "sdkType : " + targetInfoResponse.getSdkType());
                    BluetoothOTAManager.this.m677d(bluetoothDevice);
                    if (targetInfoResponse.getSdkType() >= 2) {
                        BluetoothGatt connectedBluetoothGatt = BluetoothOTAManager.this.getConnectedBluetoothGatt();
                        if (CommonUtil.checkHasConnectPermission(BluetoothOTAManager.this.context) && connectedBluetoothGatt != null) {
                            JL_Log.m853w(BluetoothOTAManager.this.TAG, "getDeviceInfoWithConnection", "requestConnectionPriority :: ret : " + connectedBluetoothGatt.requestConnectionPriority(1));
                        }
                    }
                } else {
                    BluetoothOTAManager.this.m716q();
                    if (BluetoothOTAManager.this.isOTA()) {
                        JL_Log.m845d(BluetoothOTAManager.this.TAG, "getDeviceInfoWithConnection", "The device is functioning properly. However, the cache status is currently being upgraded, so the upgrade call is complete.");
                        BluetoothOTAManager.this.m690g();
                    }
                }
                BluetoothOTAManager.this.m672c(bluetoothDevice);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: h */
    public void m695h(BluetoothDevice bluetoothDevice) {
        boolean zIsWaitingForUpdate = this.f648H.isWaitingForUpdate();
        JL_Log.m849i(this.TAG, "handleBrEdrDisconnect", "isWaitingForUpdate : " + zIsWaitingForUpdate);
        if (zIsWaitingForUpdate) {
            this.f661U.removeMessages(f636B);
            if (isConnectedDevice(bluetoothDevice)) {
                this.f661U.removeMessages(f640F);
                Handler handler = this.f661U;
                handler.sendMessageDelayed(handler.obtainMessage(f640F, bluetoothDevice), f641u);
                disconnectBluetoothDevice(bluetoothDevice);
                return;
            }
            m694h();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: i */
    public boolean m701i(BluetoothDevice bluetoothDevice) {
        return isConnectedDevice(bluetoothDevice) && this.mBluetoothOption.getPriority() == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: j */
    public /* synthetic */ void m703j(BluetoothDevice bluetoothDevice) {
        JL_Log.m849i(this.TAG, "startUpgradeReConnect", "removeBond >>> " + BluetoothUtil.removeBond(this.context, bluetoothDevice));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /* JADX INFO: renamed from: k */
    public void m707k(final BluetoothDevice bluetoothDevice) {
        if (m671b("readyToReconnectDevice")) {
            return;
        }
        int iM686f = m686f(bluetoothDevice);
        String strM632a = m632a(bluetoothDevice, iM686f);
        boolean z = IS_SUPPORT_NEW_RECONNECT_WAY;
        ReConnectDevMsg reConnectDevMsg = new ReConnectDevMsg(iM686f, strM632a);
        this.f648H.setReConnectDevMsg(reConnectDevMsg);
        JL_Log.m845d(this.TAG, "readyToReconnectDevice", "flag = " + (z ? 1 : 0) + ", " + reConnectDevMsg);
        m657a(new ReconnectParam(bluetoothDevice.getAddress(), iM686f, strM632a));
        this.f661U.removeMessages(f637C);
        this.f661U.sendEmptyMessageDelayed(f637C, f641u);
        this.f647G.changeCommunicationWay(iM686f, z ? 1 : 0, new IActionCallback<Integer>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.9
            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onError(BaseError baseError) {
                BluetoothOTAManager.this.m660a("readyToReconnectDevice", baseError);
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onSuccess(Integer num) {
                BluetoothOTAManager.this.f661U.removeMessages(BluetoothOTAManager.f637C);
                if (BluetoothOTAManager.this.f648H.isWaitingForUpdate()) {
                    BluetoothOTAManager.this.f648H.setReconnectUseADV(num.intValue() == 1);
                }
                if (BluetoothOTAManager.this.f660T != null) {
                    BluetoothOTAManager.this.f660T.setFlag(num.intValue());
                    BluetoothOTAManager bluetoothOTAManager = BluetoothOTAManager.this;
                    bluetoothOTAManager.m637a(bluetoothDevice, bluetoothOTAManager.f660T);
                }
            }
        });
    }

    /* JADX INFO: renamed from: b */
    private void m665b(final int i, final int i2, final IActionCallback<byte[]> iActionCallback) {
        ExecutorService executorService;
        if (i >= 0 && i2 >= 0) {
            if (this.f653M != null && this.f653M.length > 0) {
                byte[] bArr = new byte[i2];
                if (i + i2 > this.f653M.length) {
                    if (iActionCallback != null) {
                        iActionCallback.onError(OTAError.buildError(ErrorCode.SUB_ERR_OFFSET_OVER, CommonUtil.formatString("readBlockData :: Can not read file data by Buffer. offset = %d, len = %d, file data length = %d.", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.f653M.length))));
                        return;
                    }
                    return;
                } else {
                    System.arraycopy(this.f653M, i, bArr, 0, i2);
                    if (iActionCallback != null) {
                        iActionCallback.onSuccess(bArr);
                        return;
                    }
                    return;
                }
            }
            if (this.f654N != null && (executorService = this.f651K) != null && !executorService.isShutdown()) {
                this.f651K.execute(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m634a(i2, i, iActionCallback);
                    }
                });
                return;
            }
        }
        if (iActionCallback != null) {
            iActionCallback.onError(OTAError.buildError(4097, CommonUtil.formatString("readBlockData :: Can not read file data. offset = %d, len = %d.", Integer.valueOf(i), Integer.valueOf(i2))));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: c */
    public void m673c(BluetoothDevice bluetoothDevice, int i) {
        JL_Log.m849i(this.TAG, "notifyConnectionStatus", "device : " + printBtDeviceInfo(bluetoothDevice) + ", status : " + StateCode.printConnectionState(i));
        if (i != 3) {
            if (i == 1 || i == 4) {
                JL_Log.m849i(this.TAG, "notifyConnectionStatus", "handle connected event.");
            } else if (i == 2 || i == 0) {
                JL_Log.m853w(this.TAG, "notifyConnectionStatus", "handle disconnect event.");
                m715p();
                m720u();
                this.mDeviceStatusCache.removeDeviceStatus(bluetoothDevice);
                m714o();
            }
        }
        onConnection(bluetoothDevice, i);
    }

    /* JADX INFO: renamed from: i */
    private void m699i() {
        if (m671b("exitUpdateMode")) {
            return;
        }
        TargetInfoResponse deviceInfo = getDeviceInfo();
        if (deviceInfo == null || !deviceInfo.isSupportDoubleBackup()) {
            JL_Log.m849i(this.TAG, "exitUpdateMode", CommonUtil.formatString("device[%s] is single flash ota, so ota progress cannot be interrupted.", getConnectedBtDevice()));
        } else {
            m676c(false);
            this.f647G.exitUpdateMode(new IActionCallback<Integer>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.13
                @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                public void onError(BaseError baseError) {
                    BluetoothOTAManager.this.m676c(true);
                    BluetoothOTAManager.this.m660a("exitUpdateMode", baseError);
                }

                @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                public void onSuccess(Integer num) {
                    JL_Log.m845d(BluetoothOTAManager.this.TAG, "exitUpdateMode", "onSuccess >>> " + num);
                    if (num.intValue() == 0) {
                        BluetoothOTAManager.this.m682e();
                    } else {
                        onError(OTAError.buildError(ErrorCode.SUB_ERR_OTA_FAILED, num.intValue(), "Device return a bad code : " + num));
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: g */
    public void m690g() {
        JL_Log.m848i(this.TAG, "callbackStopOTA : ");
        m717r();
        this.f650J.onStopOTA();
        this.f661U.postDelayed(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m710m();
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: e */
    public void m683e(BluetoothDevice bluetoothDevice) {
        if (m671b("checkUpgradeEnvironment")) {
            return;
        }
        TargetInfoResponse deviceInfo = getDeviceInfo(bluetoothDevice);
        JL_Log.m845d(this.TAG, "checkUpgradeEnvironment", CommonUtil.formatString("device : %s, deviceInfo ：%s", printBtDeviceInfo(bluetoothDevice), deviceInfo));
        if (deviceInfo == null) {
            m660a("checkUpgradeEnvironment", OTAError.buildError(ErrorCode.SUB_ERR_REMOTE_NOT_CONNECTED));
            return;
        }
        if (deviceInfo.isSupportDoubleBackup()) {
            m716q();
            m721v();
        } else if (deviceInfo.isNeedBootLoader()) {
            m677d(bluetoothDevice);
            m719t();
        } else if (deviceInfo.isMandatoryUpgrade()) {
            m721v();
        } else {
            m707k(bluetoothDevice);
        }
    }

    /* JADX INFO: renamed from: f */
    private void m687f() {
        JL_Log.m848i(this.TAG, "callbackStartOTA : ");
        resetTotalTime();
        this.f650J.onStartOTA();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m657a(ReconnectParam reconnectParam) {
        this.f660T = reconnectParam;
    }

    /* JADX INFO: renamed from: h */
    private void m694h() {
        this.f661U.removeMessages(f646z);
        this.f661U.sendEmptyMessageDelayed(f646z, f642v);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: l */
    public /* synthetic */ void m708l() {
        this.f650J.setUpgradeCallback(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public /* synthetic */ void m634a(final int i, final int i2, final IActionCallback iActionCallback) {
        try {
            final byte[] bArr = new byte[i];
            this.f654N.seek(i2);
            final int i3 = this.f654N.read(bArr);
            if (i3 != i) {
                final long length = this.f654N.length();
                this.f661U.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BluetoothOTAManager.m654a(iActionCallback, i2, i, i3, length);
                    }
                });
            } else {
                this.f661U.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BluetoothOTAManager.m656a(iActionCallback, bArr, i3);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.f661U.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothOTAManager.m655a(iActionCallback, e);
                }
            });
        }
    }

    /* JADX INFO: renamed from: d */
    private void m680d(boolean z) {
        if (this.f656P > 0) {
            this.f657Q = CommonUtil.getCurrentTime() - this.f656P;
            if (z) {
                this.f656P = 0L;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: c */
    public void m672c(BluetoothDevice bluetoothDevice) {
        m673c(bluetoothDevice, 1);
        TargetInfoResponse deviceInfo = getDeviceInfo(bluetoothDevice);
        if (deviceInfo == null || isOTA()) {
            return;
        }
        if (deviceInfo.isMandatoryUpgrade() || deviceInfo.getRequestOtaFlag() == 1) {
            this.mBtEventCbHelper.onMandatoryUpgrade(bluetoothDevice);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: d */
    public void m677d(BluetoothDevice bluetoothDevice) {
        if (this.mDeviceStatusCache.getMaxCommunicationMtu(bluetoothDevice) < 530) {
            this.mDeviceStatusCache.updateDeviceMaxCommunicationMtu(bluetoothDevice, 530);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public static /* synthetic */ void m654a(IActionCallback iActionCallback, int i, int i2, int i3, long j) {
        if (iActionCallback != null) {
            iActionCallback.onError(OTAError.buildError(ErrorCode.SUB_ERR_OFFSET_OVER, CommonUtil.formatString("readBlockData :: Can not read file data by RandomAccessFile. offset = %d, len = %d, read data size = %d, file data length = %d.", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Long.valueOf(j))));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public static /* synthetic */ void m656a(IActionCallback iActionCallback, byte[] bArr, int i) {
        if (iActionCallback != null) {
            iActionCallback.onSuccess(Arrays.copyOfRange(bArr, 0, i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public static /* synthetic */ void m655a(IActionCallback iActionCallback, IOException iOException) {
        if (iActionCallback != null) {
            iActionCallback.onError(OTAError.buildError(ErrorCode.SUB_ERR_IO_EXCEPTION, "readBlockData :: failed. " + iOException.getMessage()));
        }
    }

    /* JADX INFO: renamed from: a */
    private void m641a(final BluetoothDevice bluetoothDevice, String str) {
        if (!m704j()) {
            m660a("startReadFileThread", OTAError.buildError(ErrorCode.SUB_ERR_REMOTE_NOT_CONNECTED));
            return;
        }
        m687f();
        final File file = new File(str);
        if (file.exists() && file.isFile()) {
            ExecutorService executorService = this.f651K;
            if (executorService == null || executorService.isShutdown()) {
                this.f651K = Executors.newSingleThreadExecutor();
            }
            if (file.length() <= FILE_CACHE_DATA_LIMIT) {
                this.f651K.execute(new ReadFileThread(str, new IActionCallback<byte[]>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.3
                    @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                    public void onError(BaseError baseError) {
                        BluetoothOTAManager.this.m660a("ReadFileThread", baseError);
                    }

                    @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                    public void onSuccess(byte[] bArr) {
                        JL_Log.m849i(BluetoothOTAManager.this.TAG, "ReadFileThread", "onSuccess, length = " + (bArr == null ? 0 : bArr.length));
                        BluetoothOTAManager.this.f653M = bArr;
                        BluetoothOTAManager.this.m711m(bluetoothDevice);
                    }
                }));
                return;
            } else {
                this.f651K.execute(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m659a(file, bluetoothDevice);
                    }
                });
                return;
            }
        }
        m660a("startReadFileThread", OTAError.buildError(ErrorCode.SUB_ERR_FILE_NOT_FOUND));
    }

    /* JADX INFO: renamed from: b */
    private float m663b(int i) {
        int i2 = this.f658R;
        if (i2 <= 0) {
            return 0.0f;
        }
        float f = (i * 100.0f) / i2;
        if (f >= 100.0f) {
            return 99.9f;
        }
        return f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m666b(BluetoothDevice bluetoothDevice, int i) {
        if (bluetoothDevice == null) {
            JL_Log.m847e(this.TAG, "handleConnectedEvent", "device is null.");
            return;
        }
        JL_Log.m845d(this.TAG, "handleConnectedEvent", CommonUtil.formatString("device : %s, way = %d", printBtDeviceInfo(bluetoothDevice), Integer.valueOf(i)));
        if (i == 0) {
            this.f661U.removeMessages(f645y);
        }
        setConnectedBtDevice(bluetoothDevice);
        m691g(bluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m670b(String str, boolean z) {
        if (m671b("callbackReconnectEvent")) {
            return;
        }
        JL_Log.m848i(this.TAG, "callbackReconnectEvent : " + str + ", " + z);
        this.f650J.onNeedReconnect(str, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public void m667b(final BluetoothDevice bluetoothDevice, int i, int i2) {
        if (m671b("upgradeStep02")) {
            return;
        }
        if (i2 < 0 || i < 0) {
            m660a("upgradeStep02", OTAError.buildError(4097, CommonUtil.formatString("upgradeStep02: offset = %d, len = %d", Integer.valueOf(i), Integer.valueOf(i2))));
        } else if (i == 0 && i2 == 0) {
            m642a(bluetoothDevice, new byte[]{CHexConver.intToByte(this.mBluetoothOption.getPriority())});
        } else {
            m665b(i, i2, new IActionCallback<byte[]>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.7
                @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                public void onError(BaseError baseError) {
                    BluetoothOTAManager.this.m660a("upgradeStep02", baseError);
                }

                @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
                public void onSuccess(byte[] bArr) {
                    BluetoothOTAManager.this.m642a(bluetoothDevice, bArr);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public /* synthetic */ void m659a(File file, BluetoothDevice bluetoothDevice) {
        try {
            this.f654N = new RandomAccessFile(file, FileOptionConst.READ);
            m711m(bluetoothDevice);
        } catch (Exception e) {
            e.printStackTrace();
            this.f661U.post(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m712n();
                }
            });
        }
    }

    /* JADX INFO: renamed from: a */
    private String m632a(BluetoothDevice bluetoothDevice, int i) {
        String address = bluetoothDevice.getAddress();
        TargetInfoResponse deviceInfo = getDeviceInfo(bluetoothDevice);
        if (deviceInfo != null && !deviceInfo.isSupportDoubleBackup()) {
            String edrAddr = i == 1 ? deviceInfo.getEdrAddr() : deviceInfo.getBleAddr();
            if (BluetoothAdapter.checkBluetoothAddress(edrAddr) && !edrAddr.equals(address)) {
                return edrAddr;
            }
        }
        return address;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m637a(final BluetoothDevice bluetoothDevice, ReconnectParam reconnectParam) {
        JL_Log.m845d(this.TAG, "startUpgradeReConnect", CommonUtil.formatString("device : %s, ReconnectParam = %s", printBtDeviceInfo(bluetoothDevice), reconnectParam));
        if (bluetoothDevice == null || reconnectParam == null) {
            return;
        }
        boolean zIsConnectedDevice = isConnectedDevice(bluetoothDevice);
        JL_Log.m849i(this.TAG, "startUpgradeReConnect", "isConnectedDevice = " + zIsConnectedDevice);
        if (!zIsConnectedDevice) {
            m694h();
            return;
        }
        boolean zM701i = m701i(bluetoothDevice);
        JL_Log.m849i(this.TAG, "startUpgradeReConnect", "isBLEConnected = " + zM701i);
        if (zM701i) {
            JL_Log.m845d(this.TAG, "startUpgradeReConnect", "Waiting for ble disconnect... ");
            m694h();
            return;
        }
        boolean z = isConnectedByProfile(bluetoothDevice) == 2;
        JL_Log.m845d(this.TAG, "startUpgradeReConnect", "isEDRConnected : " + z);
        if (!z) {
            JL_Log.m845d(this.TAG, "startUpgradeReConnect", "handleBrEdrDisconnect >>> ");
            m695h(bluetoothDevice);
            return;
        }
        this.f661U.removeMessages(f636B);
        Handler handler = this.f661U;
        handler.sendMessageDelayed(handler.obtainMessage(f636B, bluetoothDevice), f643w);
        boolean zDisconnectByProfiles = disconnectByProfiles(bluetoothDevice);
        JL_Log.m849i(this.TAG, "startUpgradeReConnect", "disconnectEdrRet : " + zDisconnectByProfiles);
        if (zDisconnectByProfiles) {
            return;
        }
        JL_Log.m849i(this.TAG, "startUpgradeReConnect", "disconnect edr failed.");
        this.f661U.removeMessages(f636B);
        this.f661U.postDelayed(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m703j(bluetoothDevice);
            }
        }, 300L);
        m695h(bluetoothDevice);
    }

    /* JADX INFO: renamed from: b */
    private boolean m671b(String str) {
        if (isOTA()) {
            return false;
        }
        JL_Log.m853w(this.TAG, str, "OTA process has exited.");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m635a(BluetoothDevice bluetoothDevice, float f) {
        if (m671b("callbackProgress")) {
            return;
        }
        TargetInfoResponse deviceInfo = getDeviceInfo(bluetoothDevice);
        m633a((deviceInfo == null || deviceInfo.isNeedBootLoader()) ? 0 : 1, f);
    }

    /* JADX INFO: renamed from: a */
    private void m633a(int i, float f) {
        if (m671b("callbackProgress")) {
            return;
        }
        JL_Log.m844d(this.TAG, "callbackProgress : type = " + i + ", progress = " + f);
        m680d(false);
        this.f650J.onProgress(i, f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m660a(String str, BaseError baseError) {
        if (m671b("callbackError") || baseError == null) {
            return;
        }
        JL_Log.m847e(this.TAG, "callbackError", CommonUtil.formatString("%s ----> %s", str, baseError));
        m717r();
        this.f650J.onError(baseError);
        this.f661U.postDelayed(new Runnable() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m708l();
            }
        }, 100L);
    }

    /* JADX INFO: renamed from: a */
    private void m636a(BluetoothDevice bluetoothDevice, int i, int i2) {
        if (bluetoothDevice == null || i2 != 0) {
            return;
        }
        boolean z = true;
        if (i != 1 ? !(i != 2 || isConnectedByHfp(bluetoothDevice) == 0) : isConnectedByA2dp(bluetoothDevice) != 0) {
            z = false;
        }
        boolean zHasMessages = this.f661U.hasMessages(f636B);
        JL_Log.m845d(this.TAG, "handleBrEdrProfileStatus", "isBrEdrDisconnect : " + z + ", isWaitingEdrDisconnectTask : " + zHasMessages);
        if (z && zHasMessages) {
            m695h(bluetoothDevice);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m642a(final BluetoothDevice bluetoothDevice, byte[] bArr) {
        this.f647G.inquiryDeviceCanOTA(bArr, new IActionCallback<Integer>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.8
            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onError(BaseError baseError) {
                BluetoothOTAManager.this.m660a("upgradeStep02", baseError);
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onSuccess(Integer num) {
                BaseError baseErrorBuildError;
                JL_Log.m848i(BluetoothOTAManager.this.TAG, CommonUtil.formatString("Step2.发送升级文件校验信息，确认是否可以升级, 结果: %d", num));
                if (num.intValue() == 0) {
                    BluetoothOTAManager.this.m683e(bluetoothDevice);
                    return;
                }
                switch (num.intValue()) {
                    case 1:
                        baseErrorBuildError = OTAError.buildError(16386);
                        break;
                    case 2:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_CHECK_UPGRADE_FILE, "Command E2, result = " + num);
                        break;
                    case 3:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_UPGRADE_FILE_VERSION_SAME);
                        break;
                    case 4:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_TWS_NOT_CONNECT);
                        break;
                    case 5:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_HEADSET_NOT_IN_CHARGING_BIN);
                        break;
                    case 6:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_OTA_IN_PROGRESS);
                        break;
                    case 7:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_DEVICE_IN_DOUBLE_CONNECTION);
                        break;
                    default:
                        baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_OTA_FAILED, num.intValue(), "upgradeStep2 :: Unknown error : " + num);
                        break;
                }
                onError(baseErrorBuildError);
            }
        });
    }

    /* JADX INFO: renamed from: a */
    private void m658a(final FirmwareUpdateBlockCmd firmwareUpdateBlockCmd, final int i, final int i2) {
        if (m671b("upgradeStep04")) {
            return;
        }
        m720u();
        if (i == 0 && i2 == 0) {
            JL_Log.m849i(this.TAG, "upgradeStep04", "read data over.");
            firmwareUpdateBlockCmd.setParam(null);
            firmwareUpdateBlockCmd.setStatus(0);
            sendCommandResponse(firmwareUpdateBlockCmd);
            m722w();
            return;
        }
        m665b(i, i2, new IActionCallback<byte[]>() { // from class: com.jieli.jl_bt_ota.impl.BluetoothOTAManager.11
            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onError(BaseError baseError) {
                BluetoothOTAManager.this.m660a("upgradeStep04", baseError);
            }

            @Override // com.jieli.jl_bt_ota.interfaces.IActionCallback
            public void onSuccess(byte[] bArr) {
                JL_Log.m849i(BluetoothOTAManager.this.TAG, "upgradeStep04", "read data, offset = " + i + ", length = " + i2 + ", data len = " + bArr.length);
                if (bArr.length <= 0) {
                    BluetoothOTAManager.this.m660a("upgradeStep04", OTAError.buildError(ErrorCode.SUB_ERR_OFFSET_OVER, "offset = " + i + ", length = " + i2));
                    return;
                }
                firmwareUpdateBlockCmd.setParam(new FirmwareUpdateBlockResponseParam(bArr));
                firmwareUpdateBlockCmd.setStatus(0);
                BluetoothOTAManager.this.sendCommandResponse(firmwareUpdateBlockCmd);
                BluetoothOTAManager.this.m719t();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m638a(BluetoothDevice bluetoothDevice, BaseError baseError) {
        m673c(bluetoothDevice, 2);
        m660a("callbackConnectFailed", baseError);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: a */
    private void m640a(BluetoothDevice bluetoothDevice, CommandBase commandBase, boolean z) {
        int id = commandBase.getId();
        if (id == 194) {
            boolean zIsOTA = isOTA();
            boolean zHasMessages = this.f661U.hasMessages(f635A);
            JL_Log.m845d(this.TAG, "Receive Command", "0xC2 : isOTA = " + zIsOTA + ", hasStopAdvNotify = " + zHasMessages);
            if (!zIsOTA || zHasMessages) {
                return;
            }
            this.f661U.sendEmptyMessageDelayed(f635A, 3000L);
            this.f647G.stopADVInfo(null);
            return;
        }
        if (id == 209) {
            SettingsMtuCmd settingsMtuCmd = (SettingsMtuCmd) commandBase;
            SettingsMtuParam settingsMtuParam = (SettingsMtuParam) settingsMtuCmd.getParam();
            if (settingsMtuParam == null) {
                JL_Log.m845d(this.TAG, "Receive Command", "0xD1 : command is error.");
                if (z) {
                    settingsMtuCmd.setStatus(1);
                    sendCommandResponse(settingsMtuCmd);
                    return;
                }
                return;
            }
            int mtu = settingsMtuParam.getMtu();
            int maxCommunicationMtu = this.mDeviceStatusCache.getMaxCommunicationMtu(bluetoothDevice);
            if (mtu >= 530) {
                this.mDeviceStatusCache.updateDeviceMaxCommunicationMtu(bluetoothDevice, mtu);
            } else {
                mtu = maxCommunicationMtu;
            }
            if (z) {
                settingsMtuParam.setMtu(mtu);
                settingsMtuCmd.setStatus(0);
                sendCommandResponse(settingsMtuCmd);
                return;
            }
            return;
        }
        if (id == 232) {
            NotifyUpdateContentSizeCmd notifyUpdateContentSizeCmd = (NotifyUpdateContentSizeCmd) commandBase;
            if (m671b("Receive E8 command ")) {
                notifyUpdateContentSizeCmd.setParam(null);
                notifyUpdateContentSizeCmd.setStatus(1);
                sendCommandResponse(notifyUpdateContentSizeCmd);
                return;
            }
            JL_Log.m853w(this.TAG, "Receive Command", "0xE8 : " + notifyUpdateContentSizeCmd);
            NotifyUpdateContentSizeParam notifyUpdateContentSizeParam = (NotifyUpdateContentSizeParam) notifyUpdateContentSizeCmd.getParam();
            if (notifyUpdateContentSizeParam == null) {
                JL_Log.m845d(this.TAG, "Receive Command", "0xE8 : command is error.");
                notifyUpdateContentSizeCmd.setStatus(1);
                sendCommandResponse(notifyUpdateContentSizeCmd);
                m660a("Receive E8 command", OTAError.buildError(ErrorCode.SUB_ERR_PARSE_DATA, "E8 command"));
                return;
            }
            int contentSize = notifyUpdateContentSizeParam.getContentSize();
            if (contentSize >= 0) {
                this.f656P = CommonUtil.getCurrentTime();
                int currentProgress = notifyUpdateContentSizeParam.getCurrentProgress();
                this.f659S = currentProgress;
                this.f658R = contentSize;
                m635a(bluetoothDevice, m663b(currentProgress));
                notifyUpdateContentSizeCmd.setStatus(0);
                notifyUpdateContentSizeCmd.setParam(null);
                sendCommandResponse(notifyUpdateContentSizeCmd);
                return;
            }
            JL_Log.m853w(this.TAG, "Receive Command", "0xE8 : length = " + contentSize);
            m660a("Receive E8 command", OTAError.buildError(4097, "Update content size is error. " + contentSize));
            return;
        }
        if (id == 228) {
            ExitUpdateModeCmd exitUpdateModeCmd = (ExitUpdateModeCmd) commandBase;
            boolean zIsOTA2 = isOTA();
            if (zIsOTA2) {
                m682e();
            }
            exitUpdateModeCmd.setResponse(new ExitUpdateModeResponse(!zIsOTA2 ? 1 : 0));
            exitUpdateModeCmd.setStatus(0);
            sendCommandResponse(exitUpdateModeCmd);
            return;
        }
        if (id != 229) {
            return;
        }
        m720u();
        FirmwareUpdateBlockCmd firmwareUpdateBlockCmd = (FirmwareUpdateBlockCmd) commandBase;
        if (m671b("Receive E5 command")) {
            firmwareUpdateBlockCmd.setParam(null);
            firmwareUpdateBlockCmd.setStatus(1);
            sendCommandResponse(firmwareUpdateBlockCmd);
            return;
        }
        FirmwareUpdateBlockParam firmwareUpdateBlockParam = (FirmwareUpdateBlockParam) firmwareUpdateBlockCmd.getParam();
        if (firmwareUpdateBlockParam == null) {
            JL_Log.m845d(this.TAG, "Receive Command", "0xE5 : command is error.");
            firmwareUpdateBlockCmd.setStatus(1);
            sendCommandResponse(firmwareUpdateBlockCmd);
            m660a("Receive E5 command", OTAError.buildError(ErrorCode.SUB_ERR_PARSE_DATA, "E5 command"));
            return;
        }
        int nextUpdateBlockOffsetAddr = firmwareUpdateBlockParam.getNextUpdateBlockOffsetAddr();
        int nextUpdateBlockLen = firmwareUpdateBlockParam.getNextUpdateBlockLen();
        if (this.f658R > 0) {
            int i = this.f659S + nextUpdateBlockLen;
            this.f659S = i;
            m635a(bluetoothDevice, m663b(i));
        }
        m658a(firmwareUpdateBlockCmd, nextUpdateBlockOffsetAddr, nextUpdateBlockLen);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: a */
    private void m639a(BluetoothDevice bluetoothDevice, CommandBase commandBase) {
        if (commandBase.getStatus() != 0) {
            return;
        }
        int id = commandBase.getId();
        if (id == 209) {
            SettingsMtuResponse settingsMtuResponse = (SettingsMtuResponse) ((SettingsMtuCmd) commandBase).getResponse();
            if (settingsMtuResponse != null) {
                this.mDeviceStatusCache.updateDeviceMaxCommunicationMtu(bluetoothDevice, settingsMtuResponse.getRealMtu());
                return;
            }
            return;
        }
        if (id != 227) {
            if (id != 231) {
                return;
            }
            JL_Log.m847e(this.TAG, "handleResponseCommand", "reboot >>> ");
            disconnectBluetoothDevice(bluetoothDevice);
            return;
        }
        EnterUpdateModeResponse enterUpdateModeResponse = (EnterUpdateModeResponse) ((EnterUpdateModeCmd) commandBase).getResponse();
        if (enterUpdateModeResponse == null || enterUpdateModeResponse.getCanUpdateFlag() != 0) {
            return;
        }
        m677d(bluetoothDevice);
    }
}
