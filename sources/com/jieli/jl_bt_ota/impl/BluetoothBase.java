package com.jieli.jl_bt_ota.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.interfaces.CommandCallback;
import com.jieli.jl_bt_ota.interfaces.IBluetoothCallback;
import com.jieli.jl_bt_ota.interfaces.IBluetoothManager;
import com.jieli.jl_bt_ota.interfaces.IUpgradeManager;
import com.jieli.jl_bt_ota.interfaces.rcsp.ICmdSnGenerator;
import com.jieli.jl_bt_ota.model.BluetoothOTAConfigure;
import com.jieli.jl_bt_ota.model.DataInfo;
import com.jieli.jl_bt_ota.model.OTAError;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.tool.BtEventCallbackHelper;
import com.jieli.jl_bt_ota.tool.CommandCache;
import com.jieli.jl_bt_ota.tool.DeviceStatusCache;
import com.jieli.jl_bt_ota.tool.IDataHandler;
import com.jieli.jl_bt_ota.tool.ParseHelper;
import com.jieli.jl_bt_ota.tool.SnGenerator;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import com.jieli.jl_bt_ota.util.CommonUtil;
import com.jieli.jl_bt_ota.util.JL_Log;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BluetoothBase implements IBluetoothManager, IUpgradeManager {

    /* JADX INFO: renamed from: a */
    private volatile BluetoothDevice f610a;

    /* JADX INFO: renamed from: b */
    private ICmdSnGenerator f611b;

    /* JADX INFO: renamed from: c */
    private BluetoothAdapterReceiver f612c;
    protected final Context context;

    /* JADX INFO: renamed from: d */
    private final boolean f613d;
    protected volatile IDataHandler dataHandler;
    protected final BluetoothAdapter mBluetoothAdapter;
    protected final BtEventCallbackHelper mBtEventCbHelper;
    protected final CommandCache mCommandCache;
    protected final DeviceStatusCache mDeviceStatusCache;
    protected String TAG = getClass().getSimpleName();
    protected BluetoothOTAConfigure mBluetoothOption = BluetoothOTAConfigure.createDefault();

    private class BluetoothAdapterReceiver extends BroadcastReceiver {
        private BluetoothAdapterReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            BluetoothAdapter bluetoothAdapter;
            if (intent == null || !"android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction()) || (bluetoothAdapter = BluetoothBase.this.mBluetoothAdapter) == null) {
                return;
            }
            int state = bluetoothAdapter.getState();
            JL_Log.m849i(BluetoothBase.this.TAG, "ACTION_STATE_CHANGED", "state : " + state);
            if (10 == state) {
                BluetoothBase bluetoothBase = BluetoothBase.this;
                bluetoothBase.mBtEventCbHelper.onAdapterStatus(false, bluetoothBase.f613d);
            } else if (12 == state) {
                BluetoothBase bluetoothBase2 = BluetoothBase.this;
                bluetoothBase2.mBtEventCbHelper.onAdapterStatus(true, bluetoothBase2.f613d);
            }
        }
    }

    public BluetoothBase(Context context) {
        this.context = (Context) CommonUtil.checkNotNull(context, "Context can not be null.");
        CommonUtil.setMainContext(context);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mDeviceStatusCache = new DeviceStatusCache();
        this.mCommandCache = new CommandCache();
        this.f613d = BluetoothUtil.hasBle(context);
        this.mBtEventCbHelper = new BtEventCallbackHelper();
        m601a();
    }

    /* JADX INFO: renamed from: b */
    private void m605b() {
        Context context;
        BluetoothAdapterReceiver bluetoothAdapterReceiver = this.f612c;
        if (bluetoothAdapterReceiver == null || (context = this.context) == null) {
            return;
        }
        context.unregisterReceiver(bluetoothAdapterReceiver);
        this.f612c = null;
    }

    public boolean checkDeviceIsCertify(BluetoothDevice bluetoothDevice) {
        return !this.mBluetoothOption.isUseAuthDevice() || this.mDeviceStatusCache.isAuthBtDevice(bluetoothDevice);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IUpgradeManager
    public void configure(BluetoothOTAConfigure bluetoothOTAConfigure) {
        this.mBluetoothOption = (BluetoothOTAConfigure) CommonUtil.checkNotNull(bluetoothOTAConfigure, "configure must not null.");
        JL_Log.m845d(this.TAG, "configure", "" + bluetoothOTAConfigure);
        this.f611b = bluetoothOTAConfigure.getSnGenerator();
    }

    public BluetoothOTAConfigure getBluetoothOption() {
        return this.mBluetoothOption;
    }

    public CommandBase getCacheCommand(BluetoothDevice bluetoothDevice, BasePacket basePacket) {
        if (basePacket == null) {
            return null;
        }
        return this.mCommandCache.getCommand(bluetoothDevice, basePacket.getOpCode(), basePacket.getOpCodeSn());
    }

    protected BluetoothDevice getConnectedBtDevice() {
        BluetoothDevice connectedDevice = getConnectedDevice();
        if (this.f610a == null) {
            this.f610a = connectedDevice;
        } else if (connectedDevice != null && !BluetoothUtil.deviceEquals(connectedDevice, this.f610a)) {
            this.f610a = connectedDevice;
        }
        return this.f610a;
    }

    public boolean isConnectedDevice() {
        return getConnectedBtDevice() != null;
    }

    protected void onA2dpStatus(BluetoothDevice bluetoothDevice, int i) {
        this.mBtEventCbHelper.onA2dpStatus(bluetoothDevice, i);
    }

    protected void onAdapterStatus(boolean z, boolean z2) {
        this.mBtEventCbHelper.onAdapterStatus(z, z2);
    }

    protected void onBleDataBlockChanged(BluetoothDevice bluetoothDevice, int i, int i2) {
        this.mBtEventCbHelper.onBleDataBlockChanged(bluetoothDevice, i, i2);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void onBtDeviceConnection(BluetoothDevice bluetoothDevice, int i) {
        this.mBtEventCbHelper.onBtDeviceConnection(bluetoothDevice, i);
    }

    protected void onConnection(BluetoothDevice bluetoothDevice, int i) {
        this.mBtEventCbHelper.onConnection(bluetoothDevice, i);
    }

    @Override // com.jieli.jl_bt_ota.interfaces.IBluetoothManager
    public void onError(BaseError baseError) {
        this.mBtEventCbHelper.onError(baseError);
    }

    protected void onHfpStatus(BluetoothDevice bluetoothDevice, int i) {
        this.mBtEventCbHelper.onHfpStatus(bluetoothDevice, i);
    }

    protected void onReceiveCommand(BluetoothDevice bluetoothDevice, CommandBase commandBase) {
        this.mBtEventCbHelper.onReceiveCommand(bluetoothDevice, commandBase);
    }

    protected String printBtDeviceInfo(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.printBtDeviceInfo(this.context, bluetoothDevice);
    }

    public boolean registerBluetoothCallback(IBluetoothCallback iBluetoothCallback) {
        return this.mBtEventCbHelper.addCallback(iBluetoothCallback);
    }

    public void release() {
        JL_Log.m849i(this.TAG, "release", ">>>>>>");
        m605b();
        this.mDeviceStatusCache.clear();
        this.mCommandCache.release();
        this.mBtEventCbHelper.release();
        ICmdSnGenerator iCmdSnGenerator = this.f611b;
        if (iCmdSnGenerator != null) {
            if (iCmdSnGenerator instanceof SnGenerator) {
                ((SnGenerator) iCmdSnGenerator).destroy();
            }
            this.f611b = null;
        }
    }

    public void removeCacheCommand(BluetoothDevice bluetoothDevice, BasePacket basePacket) {
        this.mCommandCache.removeCommandBase(bluetoothDevice, basePacket);
    }

    public void sendCommandAsync(CommandBase commandBase, CommandCallback commandCallback) {
        sendCommandAsync(getConnectedBtDevice(), commandBase, getBluetoothOption().getTimeoutMs(), commandCallback);
    }

    public void sendCommandResponse(CommandBase commandBase) {
        sendCommandResponse(getConnectedBtDevice(), commandBase);
    }

    protected void setConnectedBtDevice(BluetoothDevice bluetoothDevice) {
        this.f610a = bluetoothDevice;
    }

    public boolean unregisterBluetoothCallback(IBluetoothCallback iBluetoothCallback) {
        return this.mBtEventCbHelper.removeCallback(iBluetoothCallback);
    }

    /* JADX INFO: renamed from: a */
    private void m601a() {
        if (this.f612c != null || this.context == null) {
            return;
        }
        this.f612c = new BluetoothAdapterReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.context.registerReceiver(this.f612c, intentFilter);
    }

    public boolean isConnectedDevice(BluetoothDevice bluetoothDevice) {
        return BluetoothUtil.deviceEquals(getConnectedBtDevice(), bluetoothDevice);
    }

    public void sendCommandAsync(CommandBase commandBase, int i, CommandCallback commandCallback) {
        sendCommandAsync(getConnectedBtDevice(), commandBase, i, commandCallback);
    }

    public void sendCommandResponse(BluetoothDevice bluetoothDevice, CommandBase commandBase) {
        m602a(bluetoothDevice, commandBase, 0, getBluetoothOption().getTimeoutMs(), null);
    }

    public void sendCommandAsync(BluetoothDevice bluetoothDevice, CommandBase commandBase, int i, CommandCallback commandCallback) {
        m602a(bluetoothDevice, commandBase, 1, i, commandCallback);
    }

    /* JADX INFO: renamed from: a */
    private void m603a(BaseError baseError, CommandCallback commandCallback) {
        JL_Log.m853w(this.TAG, "cbCommandError", "callback : " + commandCallback + ", " + baseError);
        if (commandCallback != null) {
            commandCallback.onErrCode(baseError);
        } else {
            errorEventCallback(baseError);
        }
    }

    /* JADX INFO: renamed from: a */
    private void m602a(BluetoothDevice bluetoothDevice, CommandBase commandBase, int i, int i2, CommandCallback commandCallback) {
        if (bluetoothDevice == null) {
            bluetoothDevice = getConnectedBtDevice();
        }
        if (bluetoothDevice == null) {
            m603a(OTAError.buildError(ErrorCode.SUB_ERR_REMOTE_NOT_CONNECTED), commandCallback);
            return;
        }
        if (commandBase == null) {
            m603a(OTAError.buildError(4097), commandCallback);
            return;
        }
        if (!checkDeviceIsCertify(bluetoothDevice)) {
            m603a(OTAError.buildError(ErrorCode.SUB_ERR_AUTH_DEVICE), commandCallback);
            return;
        }
        if (this.dataHandler == null) {
            JL_Log.m845d(this.TAG, "sendCommandAsync", "No data processor.");
            m603a(OTAError.buildError(ErrorCode.SUB_ERR_REMOTE_NOT_CONNECTED, "No data processor."), commandCallback);
            return;
        }
        if (i == 1) {
            commandBase.setOpCodeSn(m600a(bluetoothDevice));
            this.mCommandCache.putCommandBase(bluetoothDevice, commandBase);
        }
        BasePacket basePacketConvert2BasePacket = ParseHelper.convert2BasePacket(commandBase, i);
        if (basePacketConvert2BasePacket == null) {
            m603a(OTAError.buildError(ErrorCode.SUB_ERR_PARSE_DATA), commandCallback);
            return;
        }
        DataInfo callback = new DataInfo().setType(0).setDevice(bluetoothDevice).setBasePacket(basePacketConvert2BasePacket).setTimeoutMs(i2).setCallback(commandCallback);
        JL_Log.m845d(this.TAG, "sendRcspCommand", "addSendData :: cmdType : " + i + ", " + callback);
        this.dataHandler.addSendData(callback);
    }

    /* JADX INFO: renamed from: a */
    private int m600a(BluetoothDevice bluetoothDevice) {
        if (this.f611b == null) {
            this.f611b = new SnGenerator();
        }
        return this.f611b.getRcspCmdSeq(bluetoothDevice);
    }
}
