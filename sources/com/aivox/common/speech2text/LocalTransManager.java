package com.aivox.common.speech2text;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.SaveLogHelper;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.ble.BleDataSourceDown;
import com.aivox.common.ble.BleDataSourceUp;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.parse.WifiSendManagerForJson;
import com.aivox.common.socket.WebSocketHandler;
import com.github.houbb.opencc4j.util.ZhTwConverterUtil;
import com.tencent.aai.AAIClient;
import com.tencent.aai.auth.LocalCredentialProvider;
import com.tencent.aai.exception.ClientException;
import com.tencent.aai.exception.ServerException;
import com.tencent.aai.listener.AudioRecognizeResultListener;
import com.tencent.aai.listener.AudioRecognizeStateListener;
import com.tencent.aai.log.AAILogger;
import com.tencent.aai.model.AudioRecognizeConfiguration;
import com.tencent.aai.model.AudioRecognizeRequest;
import com.tencent.aai.model.AudioRecognizeResult;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class LocalTransManager {
    private static final int HANDLER_NOTIFY = 1001;
    private AAIClient aaiClient;
    private AudioRecognizeConfiguration audioRecognizeConfiguration;
    private AudioRecognizeRequest audioRecognizeRequest;
    private int curTime;
    private ICommonTransCallback iLocalTransCallback;
    private boolean isRecording;
    private boolean isUp;
    private final Activity mActivity;
    private TransTimeHandler mHandler;
    private BaseAudioDataSource mRecorder;
    private String modelType;
    private int pauseTotal;
    private long totalTime;
    private final String TAG = "LocalTransManager";
    private final AudioRecognizeResultListener audioRecognizeResultlistener = new AudioRecognizeResultListener() { // from class: com.aivox.common.speech2text.LocalTransManager.1
        @Override // com.tencent.aai.listener.AudioRecognizeResultListener
        public void onSliceSuccess(AudioRecognizeRequest audioRecognizeRequest, AudioRecognizeResult audioRecognizeResult, int i) {
            LogUtil.m335d("LocalTransManager", "onSliceSuccess: voiceId = " + audioRecognizeResult.getVoiceId() + " seq = " + i + " startTime = " + audioRecognizeResult.getStartTime() + " endTime = " + audioRecognizeResult.getEndTime());
            LogUtil.m335d("LocalTransManager", "onSliceSuccess: result = " + audioRecognizeResult.getText());
            String text = audioRecognizeResult.getText();
            if (BaseStringUtil.isNotEmpty(text)) {
                if (!LocalTransManager.this.modelType.equals(MyEnum.TENCENT_LANGUAGE_TYPE.ZH_T.name) && !LocalTransManager.this.modelType.equals(MyEnum.TENCENT_LANGUAGE_TYPE.YUE.name)) {
                    LocalTransManager.this.iLocalTransCallback.onProgress(text, true, audioRecognizeResult.getVoiceId() + i, audioRecognizeResult.getStartTime() + LocalTransManager.this.pauseTotal, audioRecognizeResult.getEndTime() + LocalTransManager.this.pauseTotal, LocalTransManager.this.isUp);
                } else {
                    LocalTransManager.this.iLocalTransCallback.onProgress(ZhTwConverterUtil.toTraditional(text), true, audioRecognizeResult.getVoiceId() + i, audioRecognizeResult.getStartTime() + LocalTransManager.this.pauseTotal, audioRecognizeResult.getEndTime() + LocalTransManager.this.pauseTotal, LocalTransManager.this.isUp);
                }
            }
        }

        @Override // com.tencent.aai.listener.AudioRecognizeResultListener
        public void onSegmentSuccess(AudioRecognizeRequest audioRecognizeRequest, AudioRecognizeResult audioRecognizeResult, int i) {
            LogUtil.m335d("LocalTransManager", "onSegmentSuccess: voiceId = " + audioRecognizeResult.getVoiceId() + " seq = " + i + " startTime =" + audioRecognizeResult.getStartTime() + " endTime = " + audioRecognizeResult.getEndTime());
            LogUtil.m335d("LocalTransManager", "onSegmentSuccess: result = " + audioRecognizeResult.getText());
            LocalTransManager.this.curTime = audioRecognizeResult.getEndTime();
            String strReplaceAll = audioRecognizeResult.getText().replaceAll("嗯。|啊。|哦。|呃。|嗯，|啊，|哦，|呃，|好好好|那个那个|Mm-hmm,|Mm-hmm.|yeah,yeah", "");
            if (BaseStringUtil.isNotEmpty(strReplaceAll)) {
                if (!LocalTransManager.this.modelType.equals(MyEnum.TENCENT_LANGUAGE_TYPE.ZH_T.name) && !LocalTransManager.this.modelType.equals(MyEnum.TENCENT_LANGUAGE_TYPE.YUE.name)) {
                    LocalTransManager.this.iLocalTransCallback.onProgress(strReplaceAll, false, audioRecognizeResult.getVoiceId() + i, audioRecognizeResult.getStartTime() + LocalTransManager.this.pauseTotal, audioRecognizeResult.getEndTime() + LocalTransManager.this.pauseTotal, LocalTransManager.this.isUp);
                } else {
                    LocalTransManager.this.iLocalTransCallback.onProgress(ZhTwConverterUtil.toTraditional(strReplaceAll), false, audioRecognizeResult.getVoiceId() + i, audioRecognizeResult.getStartTime() + LocalTransManager.this.pauseTotal, audioRecognizeResult.getEndTime() + LocalTransManager.this.pauseTotal, LocalTransManager.this.isUp);
                }
            }
        }

        @Override // com.tencent.aai.listener.AudioRecognizeResultListener
        public void onSuccess(AudioRecognizeRequest audioRecognizeRequest, String str) {
            LogUtil.m335d("LocalTransManager", "onSuccess: result = " + str);
            if (LocalTransManager.this.isRecording) {
                LocalTransManager.this.isRecording = false;
            }
            LocalTransManager.this.iLocalTransCallback.onComplete(str);
        }

        @Override // com.tencent.aai.listener.AudioRecognizeResultListener
        public void onFailure(AudioRecognizeRequest audioRecognizeRequest, ClientException clientException, ServerException serverException, String str) {
            LocalTransManager.this.iLocalTransCallback.onError(str, false);
            LocalTransManager.this.handleError(str);
            if (LocalTransManager.this.isRecording) {
                LocalTransManager.this.isRecording = false;
            }
            if (str != null) {
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_ERROR, "LocalTransManager:onFailure -> " + str);
                LogUtil.m335d("LocalTransManager", "onFailure response: " + str);
            }
            if (clientException != null) {
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_ERROR, "LocalTransManager:onClientFailure -> " + clientException.getMessage());
                LogUtil.m335d("LocalTransManager", "onFailure client: " + clientException);
            }
            if (serverException != null) {
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_ERROR, "LocalTransManager:onServerFailure -> " + serverException.getMessage());
                LogUtil.m335d("LocalTransManager", "onFailure server: " + serverException);
                ToastUtil.showLong(serverException.getMessage());
            }
        }
    };
    private final AudioRecognizeStateListener audioRecognizeStateListener = new AudioRecognizeStateListener() { // from class: com.aivox.common.speech2text.LocalTransManager.2
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

        @Override // com.tencent.aai.listener.AudioRecognizeStateListener
        public void onStartRecord(AudioRecognizeRequest audioRecognizeRequest) {
            LocalTransManager.this.isRecording = true;
            AAILogger.disableDebug();
            AAILogger.disableInfo();
            LogUtil.m335d("LocalTransManager", "onStartRecord");
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "tencent_trans:audioRecognizeStateListener:onStartRecord");
            WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsStartRecording());
        }

        @Override // com.tencent.aai.listener.AudioRecognizeStateListener
        public void onStopRecord(AudioRecognizeRequest audioRecognizeRequest) {
            LogUtil.m335d("LocalTransManager", "onStopRecord");
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "tencent_trans:audioRecognizeStateListener:onStopRecord");
            LocalTransManager.this.isRecording = false;
            WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsStopRecording());
        }
    };
    private final boolean isBleMode = false;

    private class TransTimeHandler extends Handler {
        private TransTimeHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (LocalTransManager.this.isRecording) {
                LocalTransManager.this.totalTime++;
            }
            if (LocalTransManager.this.totalTime < ((Integer) SPUtil.get(SPUtil.LEFT_CURRENCY_TIME, 0)).intValue()) {
                LocalTransManager.this.mHandler.sendEmptyMessageDelayed(1001, 1000L);
            } else {
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_ERROR, "tencent:mp3Recorder:timeOut");
                LocalTransManager.this.stopRecognize();
                EventBus.getDefault().post(new EventBean(76));
            }
            super.handleMessage(message);
        }
    }

    public LocalTransManager(Activity activity, boolean z) {
        this.mActivity = activity;
        if (z) {
            TransTimeHandler transTimeHandler = new TransTimeHandler();
            this.mHandler = transTimeHandler;
            transTimeHandler.removeMessages(1001);
            this.mHandler.sendEmptyMessage(1001);
        }
    }

    public LocalTransManager(Activity activity, boolean z, boolean z2) {
        this.mActivity = activity;
        this.isUp = z2;
        if (z) {
            TransTimeHandler transTimeHandler = new TransTimeHandler();
            this.mHandler = transTimeHandler;
            transTimeHandler.removeMessages(1001);
            this.mHandler.sendEmptyMessage(1001);
        }
    }

    public void start(boolean z, int i, String str, ICommonTransCallback iCommonTransCallback) {
        if (this.isRecording) {
            return;
        }
        this.modelType = MyEnum.TENCENT_LANGUAGE_TYPE.getTencentLanguageName(i);
        this.iLocalTransCallback = iCommonTransCallback;
        if (z) {
            return;
        }
        if (this.aaiClient == null) {
            this.aaiClient = new AAIClient(this.mActivity, Integer.parseInt((String) SPUtil.get(SPUtil.TENCENT_TRANS_ID, "0")), 0, (String) SPUtil.get(SPUtil.TENCENT_TRANS_SI, ""), new LocalCredentialProvider((String) SPUtil.get(SPUtil.TENCENT_TRANS_SK, "")));
        }
        this.audioRecognizeRequest = new AudioRecognizeRequest.Builder().pcmAudioDataSource(this.mRecorder).setEngineModelType(this.modelType).setFilterDirty(0).setFilterModal(0).setFilterPunc(0).setConvert_num_mode(0).setNeedvad(DataHandle.getIns().getCurTransType() == 3 ? 0 : 1).setVadSilenceTime(isLongVadMode() ? 2000 : Constant.EVENT.BLE_GLASS_ASK_AI).setWordInfo(1).build();
        this.audioRecognizeConfiguration = new AudioRecognizeConfiguration.Builder().setSilentDetectTimeOut(false).audioFlowSilenceTimeOut(5000).minVolumeCallbackTime(80).build();
        startRecognize();
    }

    private boolean isLongVadMode() {
        return BleBtService.getInstance().isGlass() || DataHandle.getIns().getCurTransType() == 101 || DataHandle.getIns().getCurTransType() == 102 || DataHandle.getIns().getCurTransType() == 103 || DataHandle.getIns().getCurTransType() == 104 || DataHandle.getIns().getCurTransType() == 105 || DataHandle.getIns().getCurTransType() == 106 || DataHandle.getIns().getCurTransType() == 107;
    }

    private void startRecognize() {
        new Thread(new Runnable() { // from class: com.aivox.common.speech2text.LocalTransManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m360xc3c52aad();
            }
        }).start();
    }

    /* JADX INFO: renamed from: lambda$startRecognize$2$com-aivox-common-speech2text-LocalTransManager */
    /* synthetic */ void m360xc3c52aad() {
        try {
            if (this.aaiClient != null) {
                SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_METHOD, "LocalTransManager:startRecognize");
                this.aaiClient.startAudioRecognize(this.audioRecognizeRequest, this.audioRecognizeResultlistener, this.audioRecognizeStateListener, this.audioRecognizeConfiguration);
                this.mRecorder.setRecognizing(true);
            }
        } catch (Exception e) {
            this.mActivity.runOnUiThread(new Runnable() { // from class: com.aivox.common.speech2text.LocalTransManager$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m359x89fa88ce(e);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$startRecognize$1$com-aivox-common-speech2text-LocalTransManager */
    /* synthetic */ void m359x89fa88ce(Exception exc) {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_ERROR, "转写开启失败 -> " + exc);
        DialogUtils.showDialogWithDefBtn(this.mActivity, Integer.valueOf(C0874R.string.reminder), "转写开启失败，请重试", null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.speech2text.LocalTransManager$$ExternalSyntheticLambda0
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m358x502fe6ef(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, true, false);
        BaseAppUtils.printErrorMsg(exc);
    }

    /* JADX INFO: renamed from: lambda$startRecognize$0$com-aivox-common-speech2text-LocalTransManager */
    /* synthetic */ void m358x502fe6ef(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        startRecognize();
    }

    public void startRecording(BaseAudioDataSource baseAudioDataSource) {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_METHOD, "LocalTransManager:startRecording");
        if (this.isBleMode) {
            if (this.isUp) {
                this.mRecorder = BleDataSourceUp.getInstance();
            } else {
                this.mRecorder = BleDataSourceDown.getInstance();
            }
        } else {
            this.mRecorder = baseAudioDataSource;
        }
        this.mRecorder.changePause(false, false);
        this.mRecorder.startRecord(false);
    }

    public void pauseAudio() {
        this.pauseTotal = this.curTime;
        AAIClient aAIClient = this.aaiClient;
        if (aAIClient != null) {
            aAIClient.stopAudioRecognize();
        }
        BaseAudioDataSource baseAudioDataSource = this.mRecorder;
        if (baseAudioDataSource != null) {
            baseAudioDataSource.changePause(true, true);
        }
    }

    public void resumeAudio() {
        if (this.isRecording || this.mRecorder == null) {
            return;
        }
        startRecognize();
        this.mRecorder.changePause(false, false);
    }

    public void stopAudio() {
        LogUtil.m335d("LocalTransManager", "tencent stopAudio" + hashCode());
        BaseAudioDataSource baseAudioDataSource = this.mRecorder;
        if (baseAudioDataSource != null) {
            baseAudioDataSource.stopRecord();
            this.mRecorder.changePause(true, false);
        }
        AAIClient aAIClient = this.aaiClient;
        if (aAIClient != null) {
            aAIClient.stopAudioRecognize();
            this.aaiClient.release();
        }
        TransTimeHandler transTimeHandler = this.mHandler;
        if (transTimeHandler != null) {
            transTimeHandler.removeCallbacksAndMessages(null);
        }
    }

    public void stopRecognize() {
        WebSocketHandler.getInstance().send(WifiSendManagerForJson.getInstance().wsCmdRemoveLimit());
        this.pauseTotal = this.curTime;
        this.mRecorder.setRecognizing(false);
        AAIClient aAIClient = this.aaiClient;
        if (aAIClient != null) {
            aAIClient.stopAudioRecognize();
        }
    }

    public boolean isRecording() {
        return this.aaiClient != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleError(final String str) {
        if (BaseStringUtil.isEmpty(str)) {
            return;
        }
        this.mActivity.runOnUiThread(new Runnable() { // from class: com.aivox.common.speech2text.LocalTransManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m357xc86a3532(str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$handleError$3$com-aivox-common-speech2text-LocalTransManager */
    /* synthetic */ void m357xc86a3532(String str) {
        if (str.contains("4001") || str.contains("4002") || str.contains("4003")) {
            ToastUtil.showLong(this.mActivity.getResources().getString(C0874R.string.trans_error_400123));
            return;
        }
        if (str.contains("4004") || str.contains("4005")) {
            ToastUtil.showLong(this.mActivity.getResources().getString(C0874R.string.trans_error_40045));
            return;
        }
        if (str.contains("4006")) {
            ToastUtil.showLong(this.mActivity.getResources().getString(C0874R.string.trans_error_4006));
            return;
        }
        if (str.contains("4007")) {
            ToastUtil.showLong(this.mActivity.getResources().getString(C0874R.string.trans_error_4007));
            return;
        }
        if (str.contains("4008") || str.contains("4009")) {
            ToastUtil.showLong(this.mActivity.getResources().getString(C0874R.string.trans_error_40089));
            this.iLocalTransCallback.onError(this.mActivity.getResources().getString(C0874R.string.trans_error_40089), true);
        } else if (str.contains("500")) {
            ToastUtil.showLong(this.mActivity.getResources().getString(C0874R.string.trans_error_500x));
        } else if (str.contains("6001")) {
            Activity activity = this.mActivity;
            DialogUtils.showDialogWithBtnIds(activity, activity.getResources().getString(C0874R.string.trans_error_6001_title), this.mActivity.getResources().getString(C0874R.string.trans_error_6001), null, null, false, true, C0874R.string.know_and_continue, 0);
        }
    }
}
