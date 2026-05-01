package com.jieli.jl_bt_ota.tool;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.impl.BluetoothOTAManager;
import com.jieli.jl_bt_ota.model.DataInfo;
import com.jieli.jl_bt_ota.model.OTAError;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class DataHandlerModify implements IDataHandler {

    /* JADX INFO: renamed from: b */
    private final BluetoothOTAManager f729b;

    /* JADX INFO: renamed from: c */
    private final RcspParser f730c;

    /* JADX INFO: renamed from: e */
    private final HandlerThread f732e;

    /* JADX INFO: renamed from: f */
    private final Handler f733f;

    /* JADX INFO: renamed from: g */
    private final Handler f734g;

    /* JADX INFO: renamed from: a */
    private final String f728a = "DataHandlerModify";

    /* JADX INFO: renamed from: d */
    private final DataInfoCache f731d = new DataInfoCache();

    private class TimeOutCheck implements Runnable {

        /* JADX INFO: renamed from: a */
        private final DataInfo f735a;

        public TimeOutCheck(DataInfo dataInfo) {
            this.f735a = dataInfo;
            dataInfo.setSendTime(System.currentTimeMillis());
        }

        @Override // java.lang.Runnable
        public void run() {
            BasePacket basePacket = this.f735a.getBasePacket();
            JL_Log.m852w(DataHandlerModify.this.f728a, "send data timeout  --> opCode : " + basePacket.getOpCode() + ", sn : " + basePacket.getOpCodeSn() + ", resend Count : " + this.f735a.getReSendCount());
            if (this.f735a.getReSendCount() < 3) {
                DataInfo dataInfo = this.f735a;
                dataInfo.setReSendCount(dataInfo.getReSendCount() + 1);
                DataHandlerModify.this.f731d.remove(this.f735a);
                DataHandlerModify.this.addSendData(this.f735a);
                return;
            }
            DataHandlerModify.this.f731d.remove(this.f735a);
            DataHandlerModify.this.f729b.removeCacheCommand(this.f735a.getDevice(), this.f735a.getBasePacket());
            DataHandlerModify.this.m775a(this.f735a, OTAError.buildError(ErrorCode.SUB_ERR_SEND_TIMEOUT));
        }
    }

    public DataHandlerModify(BluetoothOTAManager bluetoothOTAManager) {
        HandlerThread handlerThread = new HandlerThread("DataHandlerModify");
        this.f732e = handlerThread;
        this.f734g = new Handler(Looper.getMainLooper());
        this.f729b = bluetoothOTAManager;
        this.f730c = new RcspParser();
        handlerThread.start();
        this.f733f = new Handler(handlerThread.getLooper(), new Handler.Callback() { // from class: com.jieli.jl_bt_ota.tool.DataHandlerModify$$ExternalSyntheticLambda4
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return DataHandlerModify.m778a(message);
            }
        });
    }

    /* JADX INFO: renamed from: d */
    private int m786d(DataInfo dataInfo) {
        if (dataInfo == null) {
            return Integer.MAX_VALUE;
        }
        return dataInfo.getBasePacket().getOpCode() | (dataInfo.getBasePacket().getOpCodeSn() << 16);
    }

    @Override // com.jieli.jl_bt_ota.tool.IDataHandler
    public void addRecvData(final DataInfo dataInfo) {
        this.f733f.post(new Runnable() { // from class: com.jieli.jl_bt_ota.tool.DataHandlerModify$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m774a(dataInfo);
            }
        });
    }

    @Override // com.jieli.jl_bt_ota.tool.IDataHandler
    public void addSendData(final DataInfo dataInfo) {
        dataInfo.setSendTime(m772a());
        this.f733f.post(new Runnable() { // from class: com.jieli.jl_bt_ota.tool.DataHandlerModify$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m781b(dataInfo);
            }
        });
    }

    @Override // com.jieli.jl_bt_ota.tool.IDataHandler
    public void release() {
        this.f733f.removeCallbacksAndMessages(null);
        this.f730c.release();
        this.f731d.clear();
        if (this.f732e.isInterrupted()) {
            return;
        }
        this.f732e.quitSafely();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public /* synthetic */ void m781b(DataInfo dataInfo) {
        if (!m785c(dataInfo)) {
            m775a(dataInfo, OTAError.buildError(ErrorCode.SUB_ERR_SEND_FAILED));
            return;
        }
        if (dataInfo.getBasePacket().getHasResponse() == 1) {
            this.f731d.add(dataInfo);
            Handler handler = this.f733f;
            handler.sendMessageDelayed(handler.obtainMessage(m786d(dataInfo), new TimeOutCheck(dataInfo)), dataInfo.getTimeoutMs());
            return;
        }
        BasePacket basePacket = dataInfo.getBasePacket();
        int opCodeSn = basePacket.getOpCodeSn();
        basePacket.setOpCodeSn(256);
        CommandBase commandBaseConvert2Command = ParseHelper.convert2Command(basePacket, this.f729b.getCacheCommand(dataInfo.getDevice(), basePacket));
        if (commandBaseConvert2Command != null) {
            commandBaseConvert2Command.setOpCodeSn(opCodeSn);
        }
        m776a(dataInfo, commandBaseConvert2Command);
    }

    /* JADX INFO: renamed from: c */
    private boolean m785c(DataInfo dataInfo) {
        byte[] bArrPackSendBasePacket = ParseHelper.packSendBasePacket(dataInfo.getBasePacket());
        if (bArrPackSendBasePacket == null) {
            JL_Log.m848i(this.f728a, "send data :: pack data error.");
            return false;
        }
        int iM771a = m771a(dataInfo.getDevice());
        if (bArrPackSendBasePacket.length > iM771a) {
            JL_Log.m846e(this.f728a, "send data over communication mtu [" + iM771a + "] limit.");
            return false;
        }
        boolean zSendDataToDevice = false;
        for (int i = 0; i < 3 && !(zSendDataToDevice = this.f729b.sendDataToDevice(dataInfo.getDevice(), bArrPackSendBasePacket)); i++) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JL_Log.m848i(this.f728a, "send ret : " + zSendDataToDevice);
        return zSendDataToDevice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public static /* synthetic */ boolean m778a(Message message) {
        Object obj = message.obj;
        if (!(obj instanceof Runnable)) {
            return true;
        }
        ((Runnable) obj).run();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public /* synthetic */ void m774a(DataInfo dataInfo) {
        ArrayList<BasePacket> arrayListFindPacketData = this.f730c.findPacketData(m779b(dataInfo.getDevice()), dataInfo.getRecvData());
        if (arrayListFindPacketData != null && !arrayListFindPacketData.isEmpty()) {
            int size = arrayListFindPacketData.size();
            int i = 0;
            while (i < size) {
                BasePacket basePacket = arrayListFindPacketData.get(i);
                i++;
                BasePacket basePacket2 = basePacket;
                byte[] bArrPackSendBasePacket = ParseHelper.packSendBasePacket(basePacket2);
                if (basePacket2.getType() == 1) {
                    this.f729b.receiveDataFromDevice(dataInfo.getDevice(), bArrPackSendBasePacket);
                } else {
                    DataInfo dataInfo2 = this.f731d.getDataInfo(basePacket2);
                    if (dataInfo2 == null) {
                        JL_Log.m852w(this.f728a, "addRecvData : not found cache data info. " + basePacket2);
                    } else {
                        CommandBase commandBaseConvert2Command = ParseHelper.convert2Command(basePacket2, this.f729b.getCacheCommand(dataInfo.getDevice(), basePacket2));
                        if (commandBaseConvert2Command == null) {
                            BaseError baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_PARSE_DATA);
                            baseErrorBuildError.setOpCode(basePacket2.getOpCode());
                            m775a(dataInfo2, baseErrorBuildError);
                        } else {
                            this.f729b.receiveDataFromDevice(dataInfo.getDevice(), bArrPackSendBasePacket);
                        }
                        this.f731d.remove(dataInfo2);
                        this.f733f.removeMessages(m786d(dataInfo2));
                        m776a(dataInfo2, commandBaseConvert2Command);
                        this.f729b.removeCacheCommand(dataInfo.getDevice(), basePacket2);
                    }
                }
            }
            return;
        }
        JL_Log.m852w(this.f728a, "addRecvData : not found cmd. " + CHexConver.byte2HexStr(dataInfo.getRecvData()));
    }

    /* JADX INFO: renamed from: b */
    private int m779b(BluetoothDevice bluetoothDevice) {
        return this.f729b.getReceiveMtu(bluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public /* synthetic */ void m782b(DataInfo dataInfo, BaseError baseError) {
        if (dataInfo.getCallback() != null) {
            dataInfo.getCallback().onErrCode(baseError);
        }
        this.f729b.errorEventCallback(baseError);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b */
    public static /* synthetic */ void m783b(DataInfo dataInfo, CommandBase commandBase) {
        dataInfo.getCallback().onCommandResponse(commandBase);
    }

    /* JADX INFO: renamed from: a */
    private long m772a() {
        return System.currentTimeMillis();
    }

    /* JADX INFO: renamed from: a */
    private int m771a(BluetoothDevice bluetoothDevice) {
        return this.f729b.getCommunicationMtu(bluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m775a(final DataInfo dataInfo, final BaseError baseError) {
        if (dataInfo == null) {
            JL_Log.m844d(this.f728a, "callError : param is null");
            return;
        }
        if (dataInfo.getBasePacket() != null) {
            baseError.setOpCode(dataInfo.getBasePacket().getOpCode());
        }
        JL_Log.m852w(this.f728a, "callError : " + baseError);
        this.f734g.post(new Runnable() { // from class: com.jieli.jl_bt_ota.tool.DataHandlerModify$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m782b(dataInfo, baseError);
            }
        });
    }

    /* JADX INFO: renamed from: a */
    private void m776a(final DataInfo dataInfo, final CommandBase commandBase) {
        if (dataInfo != null && dataInfo.getCallback() != null && commandBase != null) {
            this.f734g.post(new Runnable() { // from class: com.jieli.jl_bt_ota.tool.DataHandlerModify$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DataHandlerModify.m783b(dataInfo, commandBase);
                }
            });
        } else {
            JL_Log.m844d(this.f728a, " callbackCmd : param is null. " + dataInfo);
        }
    }
}
