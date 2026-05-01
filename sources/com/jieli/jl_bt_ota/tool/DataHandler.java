package com.jieli.jl_bt_ota.tool;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.jieli.jl_bt_ota.constant.ErrorCode;
import com.jieli.jl_bt_ota.impl.BluetoothOTAManager;
import com.jieli.jl_bt_ota.interfaces.CommandCallback;
import com.jieli.jl_bt_ota.model.DataInfo;
import com.jieli.jl_bt_ota.model.OTAError;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import com.jieli.jl_bt_ota.model.base.CommandBase;
import com.jieli.jl_bt_ota.util.CHexConver;
import com.jieli.jl_bt_ota.util.JL_Log;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
public class DataHandler implements IDataHandler {

    /* JADX INFO: renamed from: e */
    private static final String f706e = "DataHandler";

    /* JADX INFO: renamed from: a */
    private final BluetoothOTAManager f707a;

    /* JADX INFO: renamed from: b */
    private final Handler f708b = new Handler(Looper.getMainLooper());

    /* JADX INFO: renamed from: c */
    private WorkThread f709c;

    /* JADX INFO: renamed from: d */
    private DataHandlerThread f710d;

    /* JADX INFO: Access modifiers changed from: private */
    class DataHandlerThread extends Thread {

        /* JADX INFO: renamed from: a */
        private volatile boolean f711a;

        /* JADX INFO: renamed from: b */
        private volatile boolean f712b;

        /* JADX INFO: renamed from: c */
        private ArrayList<BasePacket> f713c;

        /* JADX INFO: renamed from: d */
        private final LinkedBlockingQueue<DataInfo> f714d;

        /* JADX INFO: renamed from: e */
        private final List<DataInfo> f715e;

        /* JADX INFO: renamed from: f */
        private final List<DataInfo> f716f;

        /* JADX INFO: renamed from: g */
        private TimerThread f717g;

        public DataHandlerThread() {
            super("DataHandlerThread");
            this.f714d = new LinkedBlockingQueue<>();
            this.f715e = Collections.synchronizedList(new ArrayList());
            this.f716f = Collections.synchronizedList(new ArrayList());
        }

        /* JADX INFO: renamed from: c */
        private void m761c() {
            m749a();
            DataInfo dataInfoM763d = m763d();
            if (dataInfoM763d != null) {
                m766e(dataInfoM763d);
                return;
            }
            if (this.f716f.size() > 0) {
                m750a(500);
            } else if (this.f715e.size() > 0) {
                m750a(500);
            } else {
                m765e();
            }
        }

        /* JADX INFO: renamed from: d */
        private void m764d(DataInfo dataInfo) {
            final BasePacket basePacket = dataInfo.getBasePacket();
            if (basePacket == null) {
                return;
            }
            if (basePacket.getHasResponse() == 1) {
                this.f716f.remove(dataInfo);
            } else {
                this.f715e.remove(dataInfo);
            }
            final CommandCallback callback = dataInfo.getCallback();
            DataHandler.this.f708b.post(new Runnable() { // from class: com.jieli.jl_bt_ota.tool.DataHandler$DataHandlerThread$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m753a(basePacket, callback);
                }
            });
        }

        /* JADX INFO: renamed from: e */
        private void m765e() {
            TimerThread timerThread = this.f717g;
            if (timerThread == null || !timerThread.f721b) {
                return;
            }
            JL_Log.m848i(DataHandler.f706e, "-stopTimer- >>> ");
            this.f717g.m770a();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: f */
        public void m767f() {
            if (this.f712b) {
                synchronized (this.f714d) {
                    if (this.f712b) {
                        JL_Log.m848i(DataHandler.f706e, "wakeUpThread:: notifyAll");
                        this.f714d.notifyAll();
                    }
                }
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            synchronized (this.f714d) {
                while (this.f711a) {
                    if (this.f714d.isEmpty()) {
                        this.f712b = true;
                        m761c();
                        JL_Log.m844d(DataHandler.f706e, "DataHandlerThread is waiting...");
                        try {
                            this.f714d.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        this.f712b = false;
                        m762c(this.f714d.poll());
                        m761c();
                    }
                }
            }
            JL_Log.m846e(DataHandler.f706e, "-DataHandlerThread- exit...");
            this.f715e.clear();
            this.f716f.clear();
            this.f714d.clear();
            this.f711a = false;
            m765e();
            DataHandler.this.f710d = null;
        }

        @Override // java.lang.Thread
        public synchronized void start() {
            this.f711a = true;
            super.start();
            JL_Log.m848i(DataHandler.f706e, "DataHandlerThread start....");
        }

        public void stopThread() {
            JL_Log.m852w(DataHandler.f706e, "-stopThread-");
            this.f711a = false;
            m767f();
        }

        public void tryToAddRecvData(DataInfo dataInfo) {
            JL_Log.m844d(DataHandler.f706e, "-tryToAddRecvData-  ret : " + m755a(dataInfo) + ",isWaiting = " + this.f712b);
        }

        public void tryToAddSendData(DataInfo dataInfo) {
            JL_Log.m844d(DataHandler.f706e, "-tryToAddSendData-  ret : " + m755a(dataInfo) + ",isWaiting = " + this.f712b);
        }

        /* JADX INFO: renamed from: b */
        private int m756b(BluetoothDevice bluetoothDevice) {
            return DataHandler.this.f707a.getReceiveMtu(bluetoothDevice);
        }

        /* JADX INFO: renamed from: a */
        private void m750a(int i) {
            TimerThread timerThread = this.f717g;
            if (timerThread != null) {
                if (timerThread.f721b) {
                    return;
                }
                this.f717g.f721b = true;
            } else {
                TimerThread timerThread2 = DataHandler.this.new TimerThread(i, new ThreadStateListener() { // from class: com.jieli.jl_bt_ota.tool.DataHandler.DataHandlerThread.1
                    @Override // com.jieli.jl_bt_ota.tool.DataHandler.ThreadStateListener
                    public void onFinish(long j) {
                        if (DataHandlerThread.this.f717g == null || DataHandlerThread.this.f717g.getId() != j) {
                            return;
                        }
                        DataHandlerThread.this.f717g = null;
                    }

                    @Override // com.jieli.jl_bt_ota.tool.DataHandler.ThreadStateListener
                    public void onStart(long j) {
                    }
                });
                this.f717g = timerThread2;
                timerThread2.start();
            }
        }

        /* JADX INFO: renamed from: b */
        private ArrayList<DataInfo> m757b() {
            if (this.f716f.size() <= 0) {
                return null;
            }
            ArrayList<DataInfo> arrayList = new ArrayList<>();
            for (DataInfo dataInfo : this.f716f) {
                if (dataInfo.isSend()) {
                    arrayList.add(dataInfo);
                }
            }
            return arrayList;
        }

        /* JADX INFO: renamed from: e */
        private void m766e(DataInfo dataInfo) {
            byte[] bArrPackSendBasePacket = ParseHelper.packSendBasePacket(dataInfo.getBasePacket());
            if (bArrPackSendBasePacket == null) {
                JL_Log.m848i(DataHandler.f706e, "send data :: pack data error.");
                m764d(dataInfo);
                return;
            }
            int iM746a = m746a(dataInfo.getDevice());
            JL_Log.m848i(DataHandler.f706e, "send data : [" + CHexConver.byte2HexStr(bArrPackSendBasePacket) + "], sendMtu = " + iM746a);
            if (bArrPackSendBasePacket.length > iM746a + 8) {
                JL_Log.m846e(DataHandler.f706e, "send data over communication mtu [" + iM746a + "] limit.");
                m764d(dataInfo);
                return;
            }
            boolean zSendDataToDevice = false;
            for (int i = 0; i < 3; i++) {
                if (DataHandler.this.f707a != null) {
                    zSendDataToDevice = DataHandler.this.f707a.sendDataToDevice(DataHandler.this.f707a.getConnectedDevice(), bArrPackSendBasePacket);
                }
                if (zSendDataToDevice) {
                    break;
                }
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            JL_Log.m848i(DataHandler.f706e, "send ret : " + zSendDataToDevice);
            if (!zSendDataToDevice) {
                m764d(dataInfo);
                return;
            }
            if (dataInfo.getBasePacket().getHasResponse() == 1) {
                dataInfo.setSend(true);
                dataInfo.setSendTime(Calendar.getInstance().getTimeInMillis());
            } else {
                final CommandCallback callback = dataInfo.getCallback();
                if (callback != null) {
                    DataHandler.this.f708b.post(new Runnable() { // from class: com.jieli.jl_bt_ota.tool.DataHandler$DataHandlerThread$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            callback.onCommandResponse(null);
                        }
                    });
                }
                this.f715e.remove(dataInfo);
            }
        }

        /* JADX INFO: renamed from: b */
        private void m759b(DataInfo dataInfo) {
            final CommandCallback callback = dataInfo.getCallback();
            DataHandler.this.f707a.removeCacheCommand(dataInfo.getDevice(), dataInfo.getBasePacket());
            DataHandler.this.f708b.post(new Runnable() { // from class: com.jieli.jl_bt_ota.tool.DataHandler$DataHandlerThread$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m751a(callback);
                }
            });
        }

        /* JADX INFO: renamed from: d */
        private DataInfo m763d() {
            int i = 0;
            if (this.f715e.size() > 0) {
                while (i < this.f715e.size()) {
                    DataInfo dataInfo = this.f715e.get(i);
                    if (!dataInfo.isSend()) {
                        return dataInfo;
                    }
                    i++;
                }
                return null;
            }
            if (this.f716f.size() <= 0) {
                return null;
            }
            while (i < this.f716f.size()) {
                DataInfo dataInfo2 = this.f716f.get(i);
                if (!dataInfo2.isSend()) {
                    return dataInfo2;
                }
                i++;
            }
            return null;
        }

        /* JADX INFO: renamed from: c */
        private void m762c(DataInfo dataInfo) {
            if (dataInfo != null) {
                if (dataInfo.getType() == 1) {
                    ArrayList<BasePacket> arrayListFindPacketData = ParseHelper.findPacketData(dataInfo.getDevice(), m756b(dataInfo.getDevice()), dataInfo.getRecvData());
                    if (arrayListFindPacketData != null) {
                        ArrayList<BasePacket> arrayList = this.f713c;
                        if (arrayList != null && arrayList.size() != 0) {
                            this.f713c.addAll(arrayListFindPacketData);
                        } else {
                            this.f713c = arrayListFindPacketData;
                        }
                        int size = arrayListFindPacketData.size();
                        int i = 0;
                        while (i < size) {
                            BasePacket basePacket = arrayListFindPacketData.get(i);
                            i++;
                            JL_Log.m844d(DataHandler.f706e, "-handlerQueue- opCode : " + basePacket.getOpCode());
                        }
                        m767f();
                        return;
                    }
                    JL_Log.m846e(DataHandler.f706e, "-handlerQueue- findPacketData not found. ");
                    return;
                }
                if (dataInfo.getBasePacket() != null) {
                    if (dataInfo.getBasePacket().getHasResponse() == 1) {
                        if (this.f716f.size() < 30) {
                            this.f716f.add(dataInfo);
                            return;
                        } else {
                            JL_Log.m848i(DataHandler.f706e, "-handlerQueue- haveResponseDataList is busy. ");
                            DataHandler.this.f707a.errorEventCallback(OTAError.buildError(ErrorCode.SUB_ERR_SYS_BUSY));
                            return;
                        }
                    }
                    if (this.f715e.size() < 60) {
                        this.f715e.add(dataInfo);
                    } else {
                        JL_Log.m848i(DataHandler.f706e, "-handlerQueue- noResponseDataList is busy. ");
                        DataHandler.this.f707a.errorEventCallback(OTAError.buildError(ErrorCode.SUB_ERR_SYS_BUSY));
                    }
                }
            }
        }

        /* JADX INFO: renamed from: a */
        private int m746a(BluetoothDevice bluetoothDevice) {
            return DataHandler.this.f707a.getCommunicationMtu(bluetoothDevice);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: a */
        public /* synthetic */ void m753a(BasePacket basePacket, CommandCallback commandCallback) {
            BaseError baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_SEND_FAILED);
            baseErrorBuildError.setOpCode(basePacket.getOpCode());
            if (commandCallback != null) {
                commandCallback.onErrCode(baseErrorBuildError);
            }
            DataHandler.this.f707a.errorEventCallback(baseErrorBuildError);
        }

        /* JADX INFO: renamed from: a */
        private boolean m755a(DataInfo dataInfo) {
            boolean zOffer;
            if (dataInfo != null) {
                try {
                    zOffer = this.f714d.offer(dataInfo, 3L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    zOffer = false;
                }
            } else {
                zOffer = false;
            }
            if (zOffer) {
                m767f();
            }
            return zOffer;
        }

        /* JADX INFO: renamed from: a */
        private void m749a() {
            ArrayList<BasePacket> arrayList = new ArrayList<>();
            ArrayList<BasePacket> arrayList2 = this.f713c;
            if (arrayList2 != null && arrayList2.size() > 0) {
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                ArrayList arrayList5 = new ArrayList(this.f713c);
                int size = arrayList5.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList5.get(i);
                    i++;
                    BasePacket basePacket = (BasePacket) obj;
                    byte[] bArrPackSendBasePacket = ParseHelper.packSendBasePacket(basePacket);
                    if (bArrPackSendBasePacket != null) {
                        if (DataHandler.this.f707a != null) {
                            DataHandler.this.f707a.receiveDataFromDevice(DataHandler.this.f707a.getConnectedDevice(), bArrPackSendBasePacket);
                        }
                        if (basePacket.getType() == 1) {
                            arrayList3.add(basePacket);
                        } else {
                            arrayList.add(basePacket);
                        }
                    } else {
                        arrayList4.add(basePacket);
                    }
                }
                if (!arrayList3.isEmpty()) {
                    this.f713c.removeAll(arrayList3);
                }
                if (arrayList4.size() > 0) {
                    this.f713c.removeAll(arrayList4);
                }
                m754a(arrayList);
                return;
            }
            m754a((ArrayList<BasePacket>) null);
        }

        /* JADX INFO: renamed from: a */
        private void m754a(ArrayList<BasePacket> arrayList) {
            ArrayList<DataInfo> arrayList2;
            int i;
            String str;
            ArrayList<DataInfo> arrayListM757b;
            ArrayList arrayList3;
            int i2;
            ArrayList<DataInfo> arrayList4;
            String str2;
            ArrayList<BasePacket> arrayList5 = arrayList;
            int size = this.f716f.size();
            String str3 = DataHandler.f706e;
            if (size > 0) {
                ArrayList<DataInfo> arrayListM757b2 = m757b();
                JL_Log.m852w(DataHandler.f706e, "-checkHaveResponseList- waitList size : " + (arrayListM757b2 == null ? 0 : arrayListM757b2.size()));
                if (arrayListM757b2 == null || arrayListM757b2.size() <= 0) {
                    return;
                }
                ArrayList arrayList6 = new ArrayList();
                ArrayList arrayList7 = new ArrayList();
                long timeInMillis = Calendar.getInstance().getTimeInMillis();
                String str4 = ", data : ";
                if (arrayList5 != null && arrayList.size() > 0) {
                    int size2 = arrayList.size();
                    int i3 = 0;
                    while (i3 < size2) {
                        int i4 = i3 + 1;
                        final BasePacket basePacket = arrayList5.get(i3);
                        JL_Log.m852w(str3, "-checkHaveResponseList- opCode : " + basePacket.getOpCode() + ", sn : " + basePacket.getOpCodeSn());
                        int size3 = arrayListM757b2.size();
                        int i5 = 0;
                        while (true) {
                            if (i5 >= size3) {
                                i2 = size2;
                                arrayList4 = arrayListM757b2;
                                str2 = str3;
                                break;
                            }
                            DataInfo dataInfo = arrayListM757b2.get(i5);
                            i5++;
                            final DataInfo dataInfo2 = dataInfo;
                            final BasePacket basePacket2 = dataInfo2.getBasePacket();
                            if (basePacket2 != null) {
                                JL_Log.m852w(str3, "-checkHaveResponseList- packet opCode : " + basePacket2.getOpCode() + ", packet sn : " + basePacket2.getOpCodeSn());
                            }
                            if (basePacket2 != null && basePacket2.getOpCode() == basePacket.getOpCode() && basePacket2.getOpCodeSn() == basePacket.getOpCodeSn()) {
                                JL_Log.m852w(str3, "-checkHaveResponseList- callback");
                                final CommandCallback callback = dataInfo2.getCallback();
                                arrayList4 = arrayListM757b2;
                                i2 = size2;
                                DataHandler.this.f708b.post(new Runnable() { // from class: com.jieli.jl_bt_ota.tool.DataHandler$DataHandlerThread$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        this.f$0.m752a(callback, basePacket, dataInfo2, basePacket2);
                                    }
                                });
                                arrayList6.add(basePacket);
                                arrayList7.add(dataInfo2);
                                str2 = str3;
                                str4 = str4;
                                break;
                            }
                            int i6 = size2;
                            String str5 = str4;
                            ArrayList<DataInfo> arrayList8 = arrayListM757b2;
                            if (dataInfo2.getTimeoutMs() < 500) {
                                dataInfo2.setTimeoutMs(500);
                            }
                            String str6 = str3;
                            if (timeInMillis - dataInfo2.getSendTime() > dataInfo2.getTimeoutMs()) {
                                int reSendCount = dataInfo2.getReSendCount();
                                str4 = str5;
                                JL_Log.m846e(str6, "wait for response timeout !!! reSend count : " + reSendCount + str4 + dataInfo2);
                                if (reSendCount >= 3) {
                                    JL_Log.m846e(str6, "retry count over time, callbackTimeOutError.");
                                    m759b(dataInfo2);
                                    arrayList6.add(basePacket);
                                    arrayList7.add(dataInfo2);
                                } else {
                                    dataInfo2.setReSendCount(reSendCount + 1);
                                    dataInfo2.setSend(false);
                                }
                            } else {
                                str4 = str5;
                            }
                            str3 = str6;
                            arrayListM757b2 = arrayList8;
                            size2 = i6;
                        }
                        arrayList5 = arrayList;
                        str3 = str2;
                        i3 = i4;
                        arrayListM757b2 = arrayList4;
                        size2 = i2;
                    }
                    arrayList2 = arrayListM757b2;
                    str = str3;
                    if (arrayList6.size() <= 0 || this.f713c == null) {
                        arrayList3 = arrayList;
                        i = 500;
                    } else {
                        arrayList3 = arrayList;
                        i = 500;
                        arrayList3.removeAll(arrayList6);
                        this.f713c.removeAll(arrayList6);
                    }
                    if (arrayList.size() > 0 && this.f713c != null) {
                        JL_Log.m846e(str, "-checkHaveResponseList- remove unused response.");
                        this.f713c.removeAll(arrayList3);
                    }
                    if (arrayList7.size() > 0) {
                        this.f716f.removeAll(arrayList7);
                        arrayList7.clear();
                        arrayListM757b = m757b();
                    }
                    if (arrayListM757b != null || arrayListM757b.size() <= 0) {
                        return;
                    }
                    int size4 = arrayListM757b.size();
                    int i7 = 0;
                    while (i7 < size4) {
                        DataInfo dataInfo3 = arrayListM757b.get(i7);
                        i7++;
                        DataInfo dataInfo4 = dataInfo3;
                        if (dataInfo4.getTimeoutMs() < i) {
                            dataInfo4.setTimeoutMs(i);
                        }
                        if (timeInMillis - dataInfo4.getSendTime() > dataInfo4.getTimeoutMs()) {
                            int reSendCount2 = dataInfo4.getReSendCount();
                            JL_Log.m846e(str, "wait for response timeout 222222 !!! reSend count : " + reSendCount2 + str4 + dataInfo4);
                            if (reSendCount2 >= 3) {
                                JL_Log.m846e(str, "retry count over time 222222, callbackTimeOutError.");
                                m759b(dataInfo4);
                                arrayList7.add(dataInfo4);
                            } else {
                                dataInfo4.setReSendCount(reSendCount2 + 1);
                                dataInfo4.setSend(false);
                            }
                        }
                    }
                    if (arrayList7.size() > 0) {
                        this.f716f.removeAll(arrayList7);
                        return;
                    }
                    return;
                }
                arrayList2 = arrayListM757b2;
                i = 500;
                str = DataHandler.f706e;
                arrayListM757b = arrayList2;
                if (arrayListM757b != null) {
                    return;
                } else {
                    return;
                }
            }
            if (arrayList5 == null || arrayList.size() <= 0 || this.f713c == null) {
                return;
            }
            JL_Log.m846e(DataHandler.f706e, "-checkHaveResponseList- 22222 remove unused response.");
            this.f713c.removeAll(arrayList5);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: a */
        public /* synthetic */ void m752a(CommandCallback commandCallback, BasePacket basePacket, DataInfo dataInfo, BasePacket basePacket2) {
            if (commandCallback != null) {
                CommandBase commandBaseConvert2Command = ParseHelper.convert2Command(basePacket, DataHandler.this.f707a.getCacheCommand(dataInfo.getDevice(), basePacket));
                if (commandBaseConvert2Command == null) {
                    commandCallback.onErrCode(OTAError.buildError(ErrorCode.SUB_ERR_PARSE_DATA));
                } else {
                    commandCallback.onCommandResponse(commandBaseConvert2Command);
                }
            }
            DataHandler.this.f707a.removeCacheCommand(dataInfo.getDevice(), basePacket2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: a */
        public /* synthetic */ void m751a(CommandCallback commandCallback) {
            BaseError baseErrorBuildError = OTAError.buildError(ErrorCode.SUB_ERR_SEND_TIMEOUT);
            if (commandCallback != null) {
                commandCallback.onErrCode(baseErrorBuildError);
            }
            DataHandler.this.f707a.errorEventCallback(baseErrorBuildError);
        }
    }

    public interface ThreadStateListener {
        void onFinish(long j);

        void onStart(long j);
    }

    private class TimerThread extends Thread {

        /* JADX INFO: renamed from: a */
        private final long f720a;

        /* JADX INFO: renamed from: b */
        private volatile boolean f721b;

        /* JADX INFO: renamed from: c */
        private final ThreadStateListener f722c;

        TimerThread(long j, ThreadStateListener threadStateListener) {
            super("TimerThread");
            this.f720a = j;
            this.f722c = threadStateListener;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (this.f721b) {
                try {
                    Thread.sleep(this.f720a);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (DataHandler.this.f710d == null) {
                    break;
                } else {
                    DataHandler.this.f710d.m767f();
                }
            }
            this.f721b = false;
            JL_Log.m852w(DataHandler.f706e, "TimerThread is end....name : " + getName());
            ThreadStateListener threadStateListener = this.f722c;
            if (threadStateListener != null) {
                threadStateListener.onFinish(getId());
            }
        }

        @Override // java.lang.Thread
        public synchronized void start() {
            this.f721b = true;
            super.start();
            JL_Log.m852w(DataHandler.f706e, "TimerThread is start....name : " + getName());
            ThreadStateListener threadStateListener = this.f722c;
            if (threadStateListener != null) {
                threadStateListener.onStart(getId());
            }
        }

        /* JADX INFO: renamed from: a */
        synchronized void m770a() {
            this.f721b = false;
        }
    }

    public class WorkThread extends HandlerThread implements Handler.Callback {

        /* JADX INFO: renamed from: c */
        private static final int f724c = 1;

        /* JADX INFO: renamed from: d */
        private static final int f725d = 2;

        /* JADX INFO: renamed from: a */
        private Handler f726a;

        public WorkThread(String str) {
            super(str, 10);
        }

        public Handler getWorkHandler() {
            if (this.f726a == null) {
                this.f726a = new Handler(getLooper(), this);
            }
            return this.f726a;
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                DataInfo dataInfo = (DataInfo) message.obj;
                if (DataHandler.this.f710d == null) {
                    return false;
                }
                DataHandler.this.f710d.tryToAddSendData(dataInfo);
                return false;
            }
            if (i != 2) {
                return false;
            }
            DataInfo dataInfo2 = (DataInfo) message.obj;
            if (DataHandler.this.f710d == null || dataInfo2 == null) {
                return false;
            }
            DataHandler.this.f710d.tryToAddRecvData(dataInfo2);
            return false;
        }

        @Override // android.os.HandlerThread
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            this.f726a = new Handler(getLooper(), this);
        }

        public void tryToAddRecvData(DataInfo dataInfo) {
            if (this.f726a == null) {
                this.f726a = new Handler(getLooper(), this);
            }
            Message messageObtainMessage = this.f726a.obtainMessage();
            messageObtainMessage.what = 2;
            messageObtainMessage.obj = dataInfo;
            this.f726a.sendMessage(messageObtainMessage);
        }

        public void tryToAddSendData(DataInfo dataInfo) {
            if (this.f726a == null) {
                this.f726a = new Handler(getLooper(), this);
            }
            Message messageObtainMessage = this.f726a.obtainMessage();
            messageObtainMessage.what = 1;
            messageObtainMessage.obj = dataInfo;
            this.f726a.sendMessage(messageObtainMessage);
        }
    }

    public DataHandler(BluetoothOTAManager bluetoothOTAManager) {
        this.f707a = bluetoothOTAManager;
        m740a();
    }

    /* JADX INFO: renamed from: d */
    private void m745d() {
        WorkThread workThread = this.f709c;
        if (workThread != null) {
            workThread.quitSafely();
            this.f709c = null;
        }
    }

    @Override // com.jieli.jl_bt_ota.tool.IDataHandler
    public void addRecvData(DataInfo dataInfo) {
        if (this.f709c == null) {
            m740a();
        }
        this.f709c.tryToAddRecvData(dataInfo);
    }

    @Override // com.jieli.jl_bt_ota.tool.IDataHandler
    public void addSendData(DataInfo dataInfo) {
        if (this.f709c == null) {
            m740a();
        }
        this.f709c.tryToAddSendData(dataInfo);
    }

    @Override // com.jieli.jl_bt_ota.tool.IDataHandler
    public void release() {
        JL_Log.m846e(f706e, "-release-");
        m744c();
    }

    /* JADX INFO: renamed from: b */
    private void m742b() {
        if (this.f709c == null) {
            this.f709c = new WorkThread("Work_Thread");
        }
        this.f709c.start();
    }

    /* JADX INFO: renamed from: c */
    private void m744c() {
        DataHandlerThread dataHandlerThread = this.f710d;
        if (dataHandlerThread != null) {
            dataHandlerThread.stopThread();
        }
        m745d();
    }

    /* JADX INFO: renamed from: a */
    private void m740a() {
        if (this.f710d == null) {
            DataHandlerThread dataHandlerThread = new DataHandlerThread();
            this.f710d = dataHandlerThread;
            dataHandlerThread.start();
            m742b();
        }
    }
}
