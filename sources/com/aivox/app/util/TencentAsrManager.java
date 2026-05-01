package com.aivox.app.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.aivox.base.common.Constant;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.ThreadPoolManager;
import com.aivox.common.ble.BleDataManager;
import com.aivox.common.ble.GlassDataSource;
import com.aivox.common.model.AudioType;
import com.aivox.common.speech2text.BaseAudioDataSource;
import com.tencent.aai.AAIClient;
import com.tencent.aai.auth.LocalCredentialProvider;
import com.tencent.aai.exception.ClientException;
import com.tencent.aai.exception.ServerException;
import com.tencent.aai.listener.AudioRecognizeResultListener;
import com.tencent.aai.listener.AudioRecognizeStateListener;
import com.tencent.aai.model.AudioRecognizeConfiguration;
import com.tencent.aai.model.AudioRecognizeRequest;
import com.tencent.aai.model.AudioRecognizeResult;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class TencentAsrManager {
    private static final int APP_ID = 1302668744;
    private static final String SECRET_ID = "AKIDHQKtBt8eeT8tgE7CgndCYidKp5LLubAT";
    private static final String SECRET_KEY = "EExrVu65pHWUSAdsGPJwJPFqbz84khOC";
    private static final String TAG = "TencentAsrManager";
    private AAIClient aaiClient;
    private AudioRecognizeRequest audioRecognizeRequest;
    private Context context;
    private final AsrListener listener;
    private BaseAudioDataSource mRecorder;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private boolean isRecognizing = false;
    private final AudioRecognizeConfiguration config = new AudioRecognizeConfiguration.Builder().sliceTime(40).setSilentDetectTimeOut(false).audioFlowSilenceTimeOut(10000).minVolumeCallbackTime(80).build();
    private final AudioRecognizeResultListener innerResultListener = new C08421();
    private final AudioRecognizeStateListener innerStateListener = new C08432();

    public interface AsrListener {
        void onError(String str);

        void onSegmentSuccess(String str);

        void onSliceSuccess(String str);

        void onStateChanged(boolean z);
    }

    public boolean isRecognizing() {
        return this.isRecognizing;
    }

    public TencentAsrManager(Context context, AsrListener asrListener) {
        this.context = context;
        this.listener = asrListener;
        initClient();
    }

    private void initClient() {
        if (this.aaiClient == null) {
            this.aaiClient = new AAIClient(this.context, APP_ID, 0, SECRET_ID, new LocalCredentialProvider(SECRET_KEY));
        }
    }

    public void startRecognize(String str) {
        if (this.aaiClient == null) {
            initClient();
        }
        saveAudioFileLog();
        if (this.mRecorder == null) {
            this.mRecorder = GlassDataSource.getInstance();
        }
        if (this.audioRecognizeRequest == null) {
            this.audioRecognizeRequest = new AudioRecognizeRequest.Builder().pcmAudioDataSource(this.mRecorder).setEngineModelType(getEngineType(str)).setFilterDirty(0).setFilterModal(2).setFilterPunc(1).setConvert_num_mode(1).setVadSilenceTime(Constant.EVENT.BLE_GLASS_ASK_AI).setNeedvad(1).build();
        }
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.app.util.TencentAsrManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2388lambda$startRecognize$0$comaivoxapputilTencentAsrManager();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$startRecognize$0$com-aivox-app-util-TencentAsrManager, reason: not valid java name */
    /* synthetic */ void m2388lambda$startRecognize$0$comaivoxapputilTencentAsrManager() {
        if (this.aaiClient != null) {
            try {
                this.mRecorder.changePause(false, false);
                this.mRecorder.setRecognizing(true);
                this.mRecorder.startRecord(false);
                this.aaiClient.startAudioRecognize(this.audioRecognizeRequest, this.innerResultListener, this.innerStateListener, this.config);
                this.isRecognizing = true;
            } catch (Exception e) {
                postError("Start ASR failed: " + e.getMessage());
            }
        }
    }

    public void stopRecognize() {
        if (this.isRecognizing || this.aaiClient != null) {
            BaseAudioDataSource baseAudioDataSource = this.mRecorder;
            if (baseAudioDataSource != null) {
                baseAudioDataSource.setRecognizing(false);
                this.mRecorder.changePause(true, false);
                this.mRecorder.stopRecord();
            }
            AAIClient aAIClient = this.aaiClient;
            if (aAIClient != null) {
                aAIClient.stopAudioRecognize();
            }
            this.isRecognizing = false;
        }
    }

    public void release() {
        stopRecognize();
        AAIClient aAIClient = this.aaiClient;
        if (aAIClient != null) {
            aAIClient.release();
            this.aaiClient = null;
        }
        this.audioRecognizeRequest = null;
        this.context = null;
    }

    /* JADX INFO: renamed from: com.aivox.app.util.TencentAsrManager$1 */
    class C08421 implements AudioRecognizeResultListener {
        @Override // com.tencent.aai.listener.AudioRecognizeResultListener
        public void onSuccess(AudioRecognizeRequest audioRecognizeRequest, String str) {
        }

        C08421() {
        }

        @Override // com.tencent.aai.listener.AudioRecognizeResultListener
        public void onSliceSuccess(AudioRecognizeRequest audioRecognizeRequest, final AudioRecognizeResult audioRecognizeResult, int i) {
            if (TextUtils.isEmpty(audioRecognizeResult.getText())) {
                return;
            }
            TencentAsrManager.this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.TencentAsrManager$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2390lambda$onSliceSuccess$0$comaivoxapputilTencentAsrManager$1(audioRecognizeResult);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSliceSuccess$0$com-aivox-app-util-TencentAsrManager$1, reason: not valid java name */
        /* synthetic */ void m2390lambda$onSliceSuccess$0$comaivoxapputilTencentAsrManager$1(AudioRecognizeResult audioRecognizeResult) {
            if (TencentAsrManager.this.listener != null) {
                TencentAsrManager.this.listener.onSliceSuccess(audioRecognizeResult.getText());
            }
        }

        @Override // com.tencent.aai.listener.AudioRecognizeResultListener
        public void onSegmentSuccess(AudioRecognizeRequest audioRecognizeRequest, AudioRecognizeResult audioRecognizeResult, int i) {
            final String strTrim = audioRecognizeResult.getText().trim();
            if (TextUtils.isEmpty(strTrim)) {
                return;
            }
            TencentAsrManager.this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.TencentAsrManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2389lambda$onSegmentSuccess$1$comaivoxapputilTencentAsrManager$1(strTrim);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSegmentSuccess$1$com-aivox-app-util-TencentAsrManager$1, reason: not valid java name */
        /* synthetic */ void m2389lambda$onSegmentSuccess$1$comaivoxapputilTencentAsrManager$1(String str) {
            if (TencentAsrManager.this.listener != null) {
                TencentAsrManager.this.listener.onSegmentSuccess(str);
            }
        }

        @Override // com.tencent.aai.listener.AudioRecognizeResultListener
        public void onFailure(AudioRecognizeRequest audioRecognizeRequest, ClientException clientException, ServerException serverException, String str) {
            TencentAsrManager.this.postError("ASR Failure: " + str);
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.util.TencentAsrManager$2 */
    class C08432 implements AudioRecognizeStateListener {
        @Override // com.tencent.aai.listener.AudioRecognizeStateListener
        public void onNextAudioData(short[] sArr, int i) {
        }

        @Override // com.tencent.aai.listener.AudioRecognizeStateListener
        public void onSilentDetectTimeOut() {
        }

        @Override // com.tencent.aai.listener.AudioRecognizeStateListener
        public void onVoiceDb(float f) {
        }

        @Override // com.tencent.aai.listener.AudioRecognizeStateListener
        public void onVoiceVolume(AudioRecognizeRequest audioRecognizeRequest, int i) {
        }

        C08432() {
        }

        @Override // com.tencent.aai.listener.AudioRecognizeStateListener
        public void onStartRecord(AudioRecognizeRequest audioRecognizeRequest) {
            if (TencentAsrManager.this.mRecorder instanceof GlassDataSource) {
                ((GlassDataSource) TencentAsrManager.this.mRecorder).clearBuffer();
            }
            TencentAsrManager.this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.TencentAsrManager$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2391lambda$onStartRecord$0$comaivoxapputilTencentAsrManager$2();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onStartRecord$0$com-aivox-app-util-TencentAsrManager$2, reason: not valid java name */
        /* synthetic */ void m2391lambda$onStartRecord$0$comaivoxapputilTencentAsrManager$2() {
            if (TencentAsrManager.this.listener != null) {
                TencentAsrManager.this.listener.onStateChanged(true);
            }
        }

        @Override // com.tencent.aai.listener.AudioRecognizeStateListener
        public void onStopRecord(AudioRecognizeRequest audioRecognizeRequest) {
            TencentAsrManager.this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.TencentAsrManager$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2392lambda$onStopRecord$1$comaivoxapputilTencentAsrManager$2();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onStopRecord$1$com-aivox-app-util-TencentAsrManager$2, reason: not valid java name */
        /* synthetic */ void m2392lambda$onStopRecord$1$comaivoxapputilTencentAsrManager$2() {
            if (TencentAsrManager.this.listener != null) {
                TencentAsrManager.this.listener.onStateChanged(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postError(final String str) {
        this.mainHandler.post(new Runnable() { // from class: com.aivox.app.util.TencentAsrManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2387lambda$postError$1$comaivoxapputilTencentAsrManager(str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$postError$1$com-aivox-app-util-TencentAsrManager, reason: not valid java name */
    /* synthetic */ void m2387lambda$postError$1$comaivoxapputilTencentAsrManager(String str) {
        AsrListener asrListener = this.listener;
        if (asrListener != null) {
            asrListener.onError(str);
        }
    }

    private void saveAudioFileLog() {
        try {
            BleDataManager.getInstance().createFile(FileUtils.getAppPath(this.context, "glassAudio") + DateUtil.getDateFromTimestamp(Calendar.getInstance().getTimeInMillis(), DateUtil.YYYY_MMDD_HHMM_SS) + "." + AudioType.WAV.getType(), Constant.AudioStreamType.NORMAL);
        } catch (Exception unused) {
        }
    }

    private String getEngineType(String str) {
        if (str.contains("zh")) {
            return str.contains("CN") ? "16k_zh" : "16k_zh-TW";
        }
        if (str.startsWith("ja")) {
            return "16k_ja";
        }
        if (str.startsWith("ko")) {
            return "16k_ko";
        }
        if (str.startsWith("th")) {
            return "16k_th";
        }
        if (str.startsWith("vi")) {
            return "16k_vi";
        }
        return "16k_en";
    }
}
