package com.aivox.common.ble;

import com.aivox.base.common.Constant;
import com.aivox.base.http.socket.ConnectStatus;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SaveLogHelper;
import com.aivox.common.ble.listener.AudioDataReceiverListener;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.model.AudioType;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.socket.DeviceProtocol;
import com.aivox.common.socket.WebSocketHandler;
import com.aivox.common.statemachine.RecordingStateMachine;
import com.aivox.common.util.PcmToWavUtil;
import com.aivox.common.util.PhoneStatusUtil;
import com.aivox.libOpus.utils.OpusUtils;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import kotlin.UByte;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class BleDataManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static BleDataManager mInstance;
    int BUFFER_LENGTH;
    int DEFAULT_AUDIO_SAMPLE_RATE;
    int DEFAULT_OPUS_CHANNEL;
    int OPUSMultiCount;
    private File audioFile;
    Thread decodeThread;
    long decoder;
    DataOutputStream dos;
    private AudioDataReceiverListener mAudioDataReceiverListener;
    private File mRecordFile;
    OpusUtils opusUtils;
    private BlockingQueue<short[]> shortQueue;
    int SEND_DATA_FRAME_LENGTH = 160;
    Constant.AudioStreamType streamType = Constant.AudioStreamType.NORMAL;
    private volatile boolean isOver = false;
    byte[] twoBytesDownPre = new byte[2];
    byte[] twoBytesUpPre = new byte[2];
    byte[] twoBytesMixPre = new byte[2];

    private BleDataManager() {
        this.DEFAULT_AUDIO_SAMPLE_RATE = 8000;
        this.DEFAULT_OPUS_CHANNEL = 2;
        this.BUFFER_LENGTH = 40;
        this.OPUSMultiCount = 8;
        if (this.decoder == 0) {
            if (BleBtService.getInstance().isGlass()) {
                this.DEFAULT_AUDIO_SAMPLE_RATE = 16000;
                this.OPUSMultiCount = 8;
                this.DEFAULT_OPUS_CHANNEL = 1;
                this.BUFFER_LENGTH = 40;
            } else if (DataHandle.getIns().isHasConnectedBle(true)) {
                this.DEFAULT_AUDIO_SAMPLE_RATE = 16000;
                this.OPUSMultiCount = 16;
                this.DEFAULT_OPUS_CHANNEL = 2;
                this.BUFFER_LENGTH = 80;
            } else {
                this.DEFAULT_AUDIO_SAMPLE_RATE = 8000;
                this.OPUSMultiCount = 8;
                this.DEFAULT_OPUS_CHANNEL = 2;
                this.BUFFER_LENGTH = 40;
            }
            OpusUtils instant = OpusUtils.INSTANCE.getInstant();
            this.opusUtils = instant;
            this.decoder = instant.createDecoder(this.DEFAULT_AUDIO_SAMPLE_RATE, this.DEFAULT_OPUS_CHANNEL);
            this.shortQueue = new LinkedBlockingDeque();
        }
    }

    public void setAudioDataReceiverListener(AudioDataReceiverListener audioDataReceiverListener) {
        this.mAudioDataReceiverListener = audioDataReceiverListener;
    }

    private void initDecodeThread() {
        Thread thread = new Thread(new Runnable() { // from class: com.aivox.common.ble.BleDataManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2442lambda$initDecodeThread$0$comaivoxcommonbleBleDataManager();
            }
        });
        this.decodeThread = thread;
        thread.start();
    }

    /* JADX INFO: renamed from: lambda$initDecodeThread$0$com-aivox-common-ble-BleDataManager, reason: not valid java name */
    /* synthetic */ void m2442lambda$initDecodeThread$0$comaivoxcommonbleBleDataManager() {
        while (!this.isOver) {
            try {
                if (!this.shortQueue.isEmpty()) {
                    dealWithDecoded(BaseStringUtil.shortArr2byteArr(this.shortQueue.take(), this.SEND_DATA_FRAME_LENGTH));
                }
            } catch (InterruptedException e) {
                LogUtil.m334d("decodeThread:" + e.getLocalizedMessage());
            }
        }
    }

    public static BleDataManager getInstance() {
        if (mInstance == null) {
            mInstance = new BleDataManager();
        }
        return mInstance;
    }

    public void createFile(String str, Constant.AudioStreamType audioStreamType) throws IOException {
        this.streamType = audioStreamType;
        this.audioFile = new File(str.replace(AudioType.WAV.getType(), AudioType.PCM.getType()));
        this.mRecordFile = new File(str);
        File file = this.audioFile;
        if (file != null) {
            if (file.exists()) {
                this.audioFile.delete();
            }
            LogUtil.m334d("createFile:" + this.audioFile.getPath() + "\t" + this.audioFile.getName() + "\t" + this.audioFile.createNewFile() + "\t" + this.streamType);
        }
        this.isOver = false;
        this.dos = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(this.audioFile.toPath(), new OpenOption[0])));
        initDecodeThread();
    }

    public void setOver(boolean z) {
        this.isOver = z;
    }

    public void save() {
        save(false);
    }

    public void save(boolean z) {
        this.shortQueue.clear();
        this.isOver = true;
        DataOutputStream dataOutputStream = this.dos;
        if (dataOutputStream != null) {
            try {
                dataOutputStream.close();
                if (z) {
                    if (this.audioFile.exists()) {
                        FileUtils.deleteFile(this.audioFile.getPath());
                    }
                    sendRecordStopMsg();
                } else {
                    this.mRecordFile.createNewFile();
                    PcmToWavUtil.convertPcmToWav(this.audioFile.getPath(), this.mRecordFile.getPath(), 16000, 1, 16, 640, new PcmToWavUtil.IPcmConvertCallback() { // from class: com.aivox.common.ble.BleDataManager.1
                        @Override // com.aivox.common.util.PcmToWavUtil.IPcmConvertCallback
                        public void onStart() {
                        }

                        @Override // com.aivox.common.util.PcmToWavUtil.IPcmConvertCallback
                        public void onError(String str) {
                            BleDataManager.this.sendRecordStopMsg();
                            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_ERROR, "PcmToWavUtil onError -> " + str);
                        }

                        @Override // com.aivox.common.util.PcmToWavUtil.IPcmConvertCallback
                        public void onComplete() {
                            BleDataManager.this.sendRecordStopMsg();
                            if (FileUtils.getFileSize(BleDataManager.this.mRecordFile.getPath()) <= FileUtils.getFileSize(BleDataManager.this.audioFile.getPath()) || FileUtils.getFileSize(BleDataManager.this.audioFile.getPath()) == -1) {
                                return;
                            }
                            FileUtils.deleteFile(BleDataManager.this.audioFile.getPath());
                        }
                    });
                }
            } catch (IOException unused) {
                sendRecordStopMsg();
            }
        }
    }

    public synchronized void writeData(byte[] bArr) {
        if (this.isOver) {
            return;
        }
        AudioDataReceiverListener audioDataReceiverListener = this.mAudioDataReceiverListener;
        if (audioDataReceiverListener != null) {
            audioDataReceiverListener.onDataWrite(bArr);
        }
        try {
            for (byte[] bArr2 : splitByteArray(bArr, this.BUFFER_LENGTH)) {
                short[] sArr = new short[(this.BUFFER_LENGTH * this.OPUSMultiCount) / 2];
                this.opusUtils.decode(this.decoder, bArr2, sArr);
                if (this.OPUSMultiCount == 16) {
                    for (short[] sArr2 : splitShortArray(sArr, this.SEND_DATA_FRAME_LENGTH)) {
                        this.shortQueue.put(sArr2);
                    }
                } else {
                    this.shortQueue.put(sArr);
                }
            }
        } catch (Exception e) {
            LogUtil.m336e("opusUtils.decode e:" + e.getLocalizedMessage());
        }
    }

    public void destroy() {
        this.isOver = true;
        this.shortQueue.clear();
        this.mAudioDataReceiverListener = null;
        Thread thread = this.decodeThread;
        if (thread != null) {
            thread.interrupt();
        }
        this.opusUtils.destroyDecoder(this.decoder);
        this.decoder = 0L;
        mInstance = null;
    }

    public static short[][] splitShortArray(short[] sArr, int i) {
        int iCeil = (int) Math.ceil(((double) sArr.length) / ((double) i));
        short[][] sArr2 = new short[iCeil][];
        for (int i2 = 0; i2 < iCeil; i2++) {
            int i3 = i2 * i;
            int iMin = Math.min(i3 + i, sArr.length) - i3;
            short[] sArr3 = new short[iMin];
            System.arraycopy(sArr, i3, sArr3, 0, iMin);
            sArr2[i2] = sArr3;
        }
        return sArr2;
    }

    public static byte[][] splitByteArray(byte[] bArr, int i) {
        int iCeil = (int) Math.ceil(((double) bArr.length) / ((double) i));
        byte[][] bArr2 = new byte[iCeil][];
        for (int i2 = 0; i2 < iCeil; i2++) {
            int i3 = i2 * i;
            int iMin = Math.min(i3 + i, bArr.length) - i3;
            byte[] bArr3 = new byte[iMin];
            System.arraycopy(bArr, i3, bArr3, 0, iMin);
            bArr2[i2] = bArr3;
        }
        return bArr2;
    }

    private byte[] calAverage(byte[] bArr, byte[] bArr2) {
        short s = (short) ((BaseStringUtil.toShortArrayLittle(bArr)[0] + BaseStringUtil.toShortArrayLittle(bArr2)[0]) / 2);
        return new byte[]{(byte) (s & 255), (byte) ((s >> 8) & 255)};
    }

    public byte[] resamplePCM(byte[] bArr) {
        short s;
        int length = bArr.length / 2;
        int i = length * 2;
        byte[] bArr2 = new byte[length * 4];
        short[] sArr = new short[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            sArr[i2] = (short) ((bArr[i3 + 1] << 8) | (bArr[i3] & UByte.MAX_VALUE));
        }
        for (int i4 = 0; i4 < i; i4++) {
            if (i4 % 2 == 0) {
                s = sArr[i4 / 2];
            } else {
                int i5 = i4 / 2;
                s = (short) ((sArr[i5] + sArr[Math.min(length - 1, i5 + 1)]) / 2);
            }
            int i6 = i4 * 2;
            bArr2[i6] = (byte) (s & 255);
            bArr2[i6 + 1] = (byte) ((s >> 8) & 255);
        }
        return bArr2;
    }

    private synchronized void dealWithDecoded(byte[] bArr) {
        int i;
        byte[] bArr2;
        int i2;
        int i3;
        int i4;
        double d;
        double d2;
        double dPow;
        double dPow2;
        if (BleBtService.getInstance().isGlass()) {
            GlassDataSource.getInstance().addData(bArr);
            writeToMixWAV(bArr);
            return;
        }
        int i5 = 2;
        byte[] bArr3 = new byte[2];
        byte[] bArr4 = new byte[2];
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        if (bArr != null) {
            int length = bArr.length;
            int i6 = 0;
            int i7 = 0;
            int i8 = 0;
            while (i7 < length) {
                byte b = bArr[i7];
                if (i8 < i5) {
                    i = i8 + 1;
                    bArr3[i8] = b;
                } else {
                    i = i8 + 1;
                    bArr4[i8 - 2] = b;
                }
                if (i == 4) {
                    if (this.streamType == Constant.AudioStreamType.SINGLE_MIX) {
                        double d3 = BaseStringUtil.toShortArrayLittle(bArr3)[i6];
                        i3 = i7;
                        double d4 = BaseStringUtil.toShortArrayLittle(bArr4)[i6];
                        if (d3 < 0.0d && d4 < 0.0d) {
                            dPow2 = (d3 + d4) - ((d3 * d4) / (-(Math.pow(2.0d, 15.0d) - 1.0d)));
                        } else {
                            dPow2 = (d3 + d4) - ((d3 * d4) / (Math.pow(2.0d, 15.0d) - 1.0d));
                        }
                        short s = (short) dPow2;
                        byte[] bArr5 = new byte[i5];
                        bArr5[0] = (byte) (s & 255);
                        bArr5[1] = (byte) ((s >> 8) & 255);
                        arrayList3.add(Byte.valueOf(bArr5[0]));
                        arrayList3.add(Byte.valueOf(bArr5[1]));
                    } else {
                        i3 = i7;
                        if (this.DEFAULT_AUDIO_SAMPLE_RATE == 8000) {
                            byte[] bArrCalAverage = calAverage(this.twoBytesDownPre, bArr3);
                            arrayList.add(Byte.valueOf(bArrCalAverage[0]));
                            arrayList.add(Byte.valueOf(bArrCalAverage[1]));
                            byte[] bArrCalAverage2 = calAverage(this.twoBytesUpPre, bArr4);
                            arrayList2.add(Byte.valueOf(bArrCalAverage2[0]));
                            arrayList2.add(Byte.valueOf(bArrCalAverage2[1]));
                        }
                        arrayList.add(Byte.valueOf(bArr3[0]));
                        arrayList.add(Byte.valueOf(bArr3[1]));
                        arrayList2.add(Byte.valueOf(bArr4[0]));
                        arrayList2.add(Byte.valueOf(bArr4[1]));
                        byte[] bArr6 = this.twoBytesDownPre;
                        bArr6[0] = bArr3[0];
                        bArr6[1] = bArr3[1];
                        byte[] bArr7 = this.twoBytesUpPre;
                        bArr7[0] = bArr4[0];
                        bArr7[1] = bArr4[1];
                        if (this.streamType == Constant.AudioStreamType.NORMAL) {
                            bArr2 = bArr3;
                            double d5 = BaseStringUtil.toShortArrayLittle(bArr3)[0];
                            double d6 = BaseStringUtil.toShortArrayLittle(bArr4)[0];
                            if (d5 < 0.0d && d6 < 0.0d) {
                                d = d5 + d6;
                                d2 = d5 * d6;
                                dPow = -(Math.pow(2.0d, 15.0d) - 1.0d);
                            } else {
                                d = d5 + d6;
                                d2 = d5 * d6;
                                dPow = Math.pow(2.0d, 15.0d) - 1.0d;
                            }
                            short s2 = (short) (d - (d2 / dPow));
                            i4 = 2;
                            byte[] bArr8 = {(byte) (s2 & 255), (byte) ((s2 >> 8) & 255)};
                            if (this.DEFAULT_AUDIO_SAMPLE_RATE == 8000) {
                                byte[] bArrCalAverage3 = calAverage(this.twoBytesMixPre, bArr8);
                                byte[] bArr9 = this.twoBytesMixPre;
                                i2 = 0;
                                bArr9[0] = bArr8[0];
                                bArr9[1] = bArr8[1];
                                writeToMixWAV(bArrCalAverage3);
                            } else {
                                i2 = 0;
                            }
                            writeToMixWAV(bArr8);
                        }
                        i = i2;
                    }
                    i4 = i5;
                    bArr2 = bArr3;
                    i2 = 0;
                    i = i2;
                } else {
                    bArr2 = bArr3;
                    i2 = i6;
                    i3 = i7;
                    i4 = i5;
                }
                i7 = i3 + 1;
                i5 = i4;
                i6 = i2;
                i8 = i;
                bArr3 = bArr2;
            }
        }
        if (this.streamType == Constant.AudioStreamType.LEFT) {
            byte[] byteArray = toByteArray(arrayList2);
            BleDataSourceUp.getInstance().writeUpData(byteArray);
            writeToMixWAV(byteArray);
        } else if (this.streamType == Constant.AudioStreamType.RIGHT) {
            byte[] byteArray2 = toByteArray(arrayList);
            BleDataSourceDown.getInstance().writeDownData(byteArray2);
            writeToMixWAV(byteArray2);
        } else if (this.streamType == Constant.AudioStreamType.SINGLE_MIX) {
            byte[] byteArray3 = toByteArray(arrayList3);
            BleDataSourceUp.getInstance().writeUpData(byteArray3);
            writeToMixWAV(byteArray3);
        } else {
            BleDataSourceDown.getInstance().writeDownData(toByteArray(arrayList));
            BleDataSourceUp.getInstance().writeUpData(toByteArray(arrayList2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRecordStopMsg() {
        if (WebSocketHandler.getInstance().getStatus() != ConnectStatus.Open) {
            EventBus.getDefault().post(new EventBean(DeviceProtocol.MSG_ID_WIFI_JSON.ACK_PHONE_RECORD_STOP));
        } else {
            while (RecordingStateMachine.get().getStageNow() == RecordingStateMachine.RecordingStateCode.RECORD_PAUSED) {
                RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.RECORD_STOP_ING, "RECORD_STOP_ING");
                RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.RECORD_SAVE_ING, "ACK_PHONE_RECORD_STOP");
            }
        }
        PhoneStatusUtil.getIns().unregisterAudioRecordingCallback();
    }

    public void sendRecordPauseMsg(Constant.AudioStreamType audioStreamType) {
        if (this.streamType == audioStreamType) {
            RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.RECORD_PAUSED, "ACK_PHONE_RECORD_PAUSE");
        }
    }

    public void writeToMixWAV(byte[] bArr) {
        try {
            DataOutputStream dataOutputStream = this.dos;
            if (dataOutputStream != null) {
                dataOutputStream.write(bArr);
            }
        } catch (IOException e) {
            LogUtil.m336e("writeToMixWAV:" + e.getLocalizedMessage());
        }
    }

    public static byte[] toByteArray(ArrayList<Byte> arrayList) {
        int size = arrayList.size();
        byte[] bArr = new byte[size];
        for (int i = 0; i < size; i++) {
            bArr[i] = arrayList.get(i).byteValue();
        }
        return bArr;
    }
}
