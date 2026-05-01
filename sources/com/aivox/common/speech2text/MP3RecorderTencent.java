package com.aivox.common.speech2text;

import android.media.AudioFormat;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.AudioRecord;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import com.aivox.base.common.Constant;
import com.aivox.base.http.socket.ConnectStatus;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SaveLogHelper;
import com.aivox.common.model.AudioType;
import com.aivox.common.model.EventBean;
import com.aivox.common.socket.DeviceProtocol;
import com.aivox.common.socket.WebSocketHandler;
import com.aivox.common.statemachine.RecordingStateMachine;
import com.aivox.common.util.PCMFormat;
import com.aivox.common.util.PcmToWavUtil;
import com.aivox.common.util.PhoneStatusUtil;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.aai.exception.ClientException;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class MP3RecorderTencent extends BaseAudioDataSource {
    private static final int DEFAULT_AUDIO_SOURCE = 1;
    private static final int DEFAULT_CHANNEL_CONFIG = 16;
    private static final int DEFAULT_LAME_IN_CHANNEL = 1;
    private static final int DEFAULT_LAME_MP3_BIT_RATE = 16;
    private static final int DEFAULT_LAME_MP3_QUALITY = 2;
    private static final int DEFAULT_LAME_SAMPLING_RATE = 16000;
    private static final int DEFAULT_SAMPLING_RATE = 16000;
    public static final int ERROR_TYPE = 22;
    private static final int FRAME_COUNT = 160;
    private static final int MAX_VOLUME = 2000;
    public static final int RECORD_STOP_ERROR = 33;
    private static String TAG = "MP3RecorderTencent";
    private static final int mAudioFormat = 2;
    AudioPlaybackCaptureConfiguration audioPlaybackCaptureConfiguration;
    private Handler errorHandler;
    boolean isSendPcmRunning;
    private DataOutputStream mDataOutputStream;
    private int mMaxSize;
    private byte[] mPCMBuffer;
    private short[] mPCMBuffer_;
    private boolean mPause;
    private File mRecordFile;
    private File mRecordFilePCM;
    private boolean mSendError;
    private long startTime;
    private static final PCMFormat DEFAULT_AUDIO_FORMAT = PCMFormat.PCM_16BIT;
    private static int[] mSampleRates = {16000, 8000, 11025, 22050, 44100};
    private static int[] mAudioSourceArr = {0, 1};
    private static int[] mAudioFormataArr = {0, 1, 2, 3};
    private static int[] mChannelConfigArr = {16};
    private int audioSource = 1;
    private AudioRecord mAudioRecord = null;
    private boolean mIsRecording = false;
    private int mBufferSize = 640;
    private int mWaveSpeed = Constant.EVENT.BLE_CONNECTED;
    private boolean isRecognizing = false;
    private long waitTime = 78;
    private BlockingQueue<byte[]> mByteQueue = new LinkedBlockingDeque();
    private BlockingQueue<short[]> mShortQueue = new LinkedBlockingDeque();
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public int getMaxVolume() {
        return 2000;
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public boolean isSetSaveAudioRecordFiles() {
        return false;
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public void start() throws ClientException {
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public void stop() {
    }

    public MP3RecorderTencent(String str) {
        this.mRecordFile = new File(str);
        this.mRecordFilePCM = new File(str.replace(AudioType.WAV.getType(), AudioType.PCM.getType()));
    }

    public void setAudioSource(int i) {
        LogUtil.m338i("声源1：" + i);
        this.audioSource = i;
    }

    public int getAudioSource() {
        LogUtil.m338i("声源2：" + this.audioSource);
        return this.audioSource;
    }

    @Override // com.tencent.aai.audio.data.PcmAudioDataSource
    public synchronized int read(short[] sArr, int i) {
        try {
            System.arraycopy(this.mShortQueue.take(), 0, sArr, 0, sArr.length);
        } catch (Exception unused) {
            return 0;
        }
        return sArr.length;
    }

    public void recordStart(final boolean z) throws IOException {
        if (this.mIsRecording) {
            return;
        }
        this.mIsRecording = true;
        initAudioRecorder(z);
        try {
            this.mAudioRecord.startRecording();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e, "MP3Record start error");
            LogUtil.m336e("MP3Recorder.start.e:" + e.getLocalizedMessage());
        }
        if (this.mExecutor == null) {
            this.mExecutor = Executors.newSingleThreadExecutor();
        }
        this.mExecutor.submit(new Runnable() { // from class: com.aivox.common.speech2text.MP3RecorderTencent.1
            final boolean isError = false;

            @Override // java.lang.Runnable
            public void run() throws Throwable {
                int i;
                Process.setThreadPriority(-19);
                while (MP3RecorderTencent.this.mIsRecording) {
                    MP3RecorderTencent.this.startTime = System.currentTimeMillis();
                    if (!MP3RecorderTencent.this.isPause()) {
                        if (z) {
                            i = MP3RecorderTencent.this.mAudioRecord.read(MP3RecorderTencent.this.mPCMBuffer, 0, MP3RecorderTencent.this.mBufferSize);
                            if (MP3RecorderTencent.this.isRecognizing) {
                                try {
                                    MP3RecorderTencent.this.mByteQueue.put(MP3RecorderTencent.this.mPCMBuffer);
                                } catch (InterruptedException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        } else {
                            i = MP3RecorderTencent.this.mAudioRecord.read(MP3RecorderTencent.this.mPCMBuffer_, 0, MP3RecorderTencent.this.mBufferSize);
                            if (MP3RecorderTencent.this.isRecognizing) {
                                try {
                                    MP3RecorderTencent.this.mShortQueue.put(MP3RecorderTencent.this.mPCMBuffer_);
                                } catch (InterruptedException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            MP3RecorderTencent mP3RecorderTencent = MP3RecorderTencent.this;
                            mP3RecorderTencent.mPCMBuffer = BaseStringUtil.shortArr2byteArr(mP3RecorderTencent.mPCMBuffer_, MP3RecorderTencent.this.mPCMBuffer_.length);
                        }
                        MP3RecorderTencent.this.waitTime = System.currentTimeMillis() - MP3RecorderTencent.this.startTime;
                        if (i == -3 || i == -2) {
                            LogUtil.m336e("MP3Recorder.run:audioRecord.read result is ERROR_BAD_VALUE or ERROR_INVALID_OPERATION");
                            MP3RecorderTencent.this.sendErrorMessage("MP3Recorder.run:audioRecord.read result is ERROR_BAD_VALUE or ERROR_INVALID_OPERATION", false);
                        } else if (i > 0) {
                            if (MP3RecorderTencent.this.mDataOutputStream != null && MP3RecorderTencent.this.mRecordFilePCM != null) {
                                try {
                                    MP3RecorderTencent.this.mDataOutputStream.write(MP3RecorderTencent.this.mPCMBuffer);
                                } catch (Exception e4) {
                                    e4.getLocalizedMessage();
                                    LogUtil.m336e(e4.getLocalizedMessage());
                                    MP3RecorderTencent.this.sendErrorMessage(e4.getLocalizedMessage(), false);
                                }
                            }
                        } else {
                            MP3RecorderTencent.this.sendErrorMessage("readSize is 0", false);
                        }
                    }
                }
                if (MP3RecorderTencent.this.mDataOutputStream != null) {
                    try {
                        MP3RecorderTencent.this.mDataOutputStream.close();
                        MP3RecorderTencent.this.mRecordFile.createNewFile();
                        PcmToWavUtil.convertPcmToWav(MP3RecorderTencent.this.mRecordFilePCM.getPath(), MP3RecorderTencent.this.mRecordFile.getPath(), 16000, 1, 16, MP3RecorderTencent.this.mBufferSize, new PcmToWavUtil.IPcmConvertCallback() { // from class: com.aivox.common.speech2text.MP3RecorderTencent.1.1
                            @Override // com.aivox.common.util.PcmToWavUtil.IPcmConvertCallback
                            public void onStart() {
                            }

                            @Override // com.aivox.common.util.PcmToWavUtil.IPcmConvertCallback
                            public void onError(String str) {
                                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_ERROR, "PcmToWavUtil onError -> " + str);
                                MP3RecorderTencent.this.sendRecordStopMsg();
                            }

                            @Override // com.aivox.common.util.PcmToWavUtil.IPcmConvertCallback
                            public void onComplete() {
                                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "PcmToWavUtil onComplete -> PCMSize:" + FileUtils.getFileSize(MP3RecorderTencent.this.mRecordFilePCM.getPath()) + " wavPCMSize:" + FileUtils.getFileSize(MP3RecorderTencent.this.mRecordFile.getPath()));
                                if (FileUtils.getFileSize(MP3RecorderTencent.this.mRecordFile.getPath()) > FileUtils.getFileSize(MP3RecorderTencent.this.mRecordFilePCM.getPath()) && FileUtils.getFileSize(MP3RecorderTencent.this.mRecordFilePCM.getPath()) != -1) {
                                    FileUtils.deleteFile(MP3RecorderTencent.this.mRecordFilePCM.getPath());
                                }
                                MP3RecorderTencent.this.sendRecordStopMsg();
                            }
                        });
                    } catch (IOException e5) {
                        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_ERROR, "PcmToWavUtil IOException -> " + e5);
                        BaseAppUtils.printErrorMsg(e5);
                        MP3RecorderTencent.this.sendRecordStopMsg();
                        LogUtil.m336e("mDataOutputStream.close():" + e5.getLocalizedMessage());
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorMessage(String str, boolean z) {
        if (this.errorHandler == null || this.mSendError) {
            return;
        }
        this.mSendError = true;
        Message message = new Message();
        message.what = 22;
        message.obj = str;
        this.errorHandler.sendMessage(message);
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_ERROR, "mp3Recorder:ErrorHandler22 -> " + str);
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public boolean isRecording() {
        return this.mIsRecording;
    }

    private void initAudioRecorder(boolean z) throws IOException {
        PhoneStatusUtil.getIns().registerAudioRecordingCallback();
        this.mBufferSize = 640;
        PCMFormat pCMFormat = DEFAULT_AUDIO_FORMAT;
        int bytesPerFrame = pCMFormat.getBytesPerFrame();
        LogUtil.m338i("初始化buffersize1:" + this.mBufferSize + ";bytesPerFrame:" + bytesPerFrame + ";getMinBufferSize:" + AudioRecord.getMinBufferSize(16000, 16, pCMFormat.getAudioFormat()));
        int i = this.mBufferSize / bytesPerFrame;
        int i2 = i % 160;
        if (i2 != 0) {
            this.mBufferSize = (i + (160 - i2)) * bytesPerFrame;
        }
        LogUtil.m338i("初始化buffersize2:" + this.mBufferSize);
        if (this.audioPlaybackCaptureConfiguration != null && Build.VERSION.SDK_INT >= 29) {
            this.mAudioRecord = new AudioRecord.Builder().setAudioFormat(new AudioFormat.Builder().setEncoding(2).setSampleRate(16000).setChannelMask(16).build()).setBufferSizeInBytes(this.mBufferSize).setAudioSource(getAudioSource()).setAudioPlaybackCaptureConfig(this.audioPlaybackCaptureConfiguration).build();
        } else {
            this.mAudioRecord = new AudioRecord(getAudioSource(), 16000, 16, pCMFormat.getAudioFormat(), this.mBufferSize);
        }
        if (this.mAudioRecord.getState() == 0) {
            Log.i("tag", "audioRecord初始化失败");
            this.mAudioRecord = findAudioRecord();
        }
        int i3 = this.mBufferSize;
        this.mPCMBuffer = new byte[i3];
        this.mPCMBuffer_ = new short[i3];
        LogUtil.m338i("init_" + this.mBufferSize + PunctuationConst.UNDERLINE + this.mPCMBuffer.length + PunctuationConst.UNDERLINE + this.mPCMBuffer_.length);
        this.mAudioRecord.setPositionNotificationPeriod(160);
        File file = this.mRecordFilePCM;
        if (file != null) {
            if (file.exists()) {
                this.mRecordFilePCM.delete();
            }
            this.mRecordFilePCM.createNewFile();
            this.mDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(this.mRecordFilePCM)));
        }
    }

    public static byte[] intToByteArray(int i) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
    }

    public static byte[] shortToByteArray(short s) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(s).array();
    }

    public void setErrorHandler(Handler handler) {
        this.errorHandler = handler;
    }

    public int getWaveSpeed() {
        return this.mWaveSpeed;
    }

    public void setWaveSpeed(int i) {
        if (this.mWaveSpeed <= 0) {
            return;
        }
        this.mWaveSpeed = i;
    }

    public static void deleteFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            for (String str2 : file.list()) {
                deleteFile(str + File.separator + str2);
            }
            file.delete();
        }
    }

    public boolean isPause() {
        return this.mPause;
    }

    public void setPause(boolean z) {
        setPause(z, true);
    }

    public void setPause(boolean z, boolean z2) {
        LogUtil.m338i("--MP3Record2_setPause--" + z);
        this.mPause = z;
        if (z && z2) {
            sendRecordPauseMsg();
        }
    }

    private void sendRecordPauseMsg() {
        if (RecordingStateMachine.get().getStageNow().getCode().intValue() == 0) {
            return;
        }
        RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.RECORD_PAUSED, "ACK_PHONE_RECORD_PAUSE");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRecordStopMsg() {
        PhoneStatusUtil.getIns().unregisterAudioRecordingCallback();
        if (WebSocketHandler.getInstance().getStatus() != ConnectStatus.Open) {
            EventBus.getDefault().post(new EventBean(DeviceProtocol.MSG_ID_WIFI_JSON.ACK_PHONE_RECORD_STOP));
        } else {
            if (RecordingStateMachine.get().getStageNow().getCode().intValue() == 0) {
                return;
            }
            RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.RECORD_STOP_ING, "RECORD_STOP_ING");
            RecordingStateMachine.get().stateGo(RecordingStateMachine.RecordingStateCode.RECORD_SAVE_ING, "ACK_PHONE_RECORD_STOP");
        }
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void changePause(boolean z, boolean z2) {
        setPause(z, z2);
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void stopRecord() {
        LogUtil.m338i("--MP3Record2_stopRecord--");
        if (this.mIsRecording) {
            this.mIsRecording = false;
            AudioRecord audioRecord = this.mAudioRecord;
            if (audioRecord != null && audioRecord.getState() == 1) {
                this.mAudioRecord.release();
                this.mAudioRecord = null;
                Log.i("tag", "MP3Record2-录音停止");
            }
            ExecutorService executorService = this.mExecutor;
            if (executorService != null) {
                executorService.shutdownNow();
                this.mExecutor = null;
            }
        }
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void startRecord(boolean z) {
        try {
            recordStart(z);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public byte[] getData() {
        try {
            return this.mByteQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return this.mPCMBuffer;
        }
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public boolean isEmpty() {
        return this.mByteQueue.isEmpty();
    }

    @Override // com.aivox.common.speech2text.BaseAudioDataSource
    public void setRecognizing(boolean z) {
        LogUtil.m334d("LocalTransManager AliSpeech2Text setRecognizing" + z);
        this.isRecognizing = z;
    }

    public AudioRecord findAudioRecord() {
        int i;
        int[] iArr;
        int i2;
        int i3;
        int i4;
        int[] iArr2;
        int minBufferSize;
        for (int i5 : mSampleRates) {
            for (int i6 : mAudioFormataArr) {
                int[] iArr3 = mChannelConfigArr;
                int length = iArr3.length;
                int i7 = 0;
                while (i7 < length) {
                    int i8 = iArr3[i7];
                    int[] iArr4 = mAudioSourceArr;
                    int length2 = iArr4.length;
                    int i9 = 0;
                    while (i9 < length2) {
                        int i10 = iArr4[i9];
                        try {
                            minBufferSize = AudioRecord.getMinBufferSize(i5, i8, i6);
                        } catch (Exception e) {
                            e = e;
                            i = length2;
                            iArr = iArr4;
                            i2 = i8;
                            i3 = i7;
                            i4 = length;
                            iArr2 = iArr3;
                        }
                        if (minBufferSize > 0) {
                            i = length2;
                            iArr = iArr4;
                            int i11 = i8;
                            i3 = i7;
                            i4 = length;
                            iArr2 = iArr3;
                            try {
                                AudioRecord audioRecord = new AudioRecord(i10, i5, i11, i6, minBufferSize);
                                int state = audioRecord.getState();
                                LogUtil.m338i("state:" + state);
                                i2 = 1;
                                if (state == 1) {
                                    i2 = i11;
                                    LogUtil.m338i("rate:" + i5 + ";audioformat:" + i6 + ";channelConfig:" + i2 + ";mAudioSource:" + i10);
                                    return audioRecord;
                                }
                                try {
                                    i2 = i11;
                                    audioRecord.release();
                                } catch (Exception e2) {
                                    e = e2;
                                    BaseAppUtils.printErrorMsg(e);
                                    LogUtil.m338i("e:" + e.getLocalizedMessage());
                                }
                                e = e2;
                            } catch (Exception e3) {
                                e = e3;
                                i2 = i11;
                            }
                            BaseAppUtils.printErrorMsg(e);
                            LogUtil.m338i("e:" + e.getLocalizedMessage());
                        } else {
                            i = length2;
                            iArr = iArr4;
                            i2 = i8;
                            i3 = i7;
                            i4 = length;
                            iArr2 = iArr3;
                        }
                        i9++;
                        i8 = i2;
                        length2 = i;
                        iArr4 = iArr;
                        i7 = i3;
                        length = i4;
                        iArr3 = iArr2;
                    }
                    i7++;
                }
            }
        }
        return null;
    }
}
