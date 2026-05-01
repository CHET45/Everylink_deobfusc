package com.aivox.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.TranscribeConversationAdapter;
import com.aivox.app.databinding.ActivityEarPhoneConversationRecordingBinding;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.databinding.OnItemClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.permission.PermissionCallback;
import com.aivox.base.permission.PermissionUtils;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.router.action.RecordAction;
import com.aivox.base.util.AudioUtils;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.NetUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.SaveLogHelper;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.AudioType;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.TransKeyBean;
import com.aivox.common.model.Transcribe;
import com.aivox.common.parse.SendManager;
import com.aivox.common.speech2text.CommonTransManager;
import com.aivox.common.speech2text.ICommonTransCallback;
import com.aivox.common.speech2text.MP3RecorderTencent;
import com.aivox.common.translate.SeqTransModel;
import com.aivox.common.util.LanguageUtils;
import com.aivox.common.util.MicrosoftTtsManager;
import com.aivox.common.util.encrypt.SerAESUtil;
import com.aivox.common.view.LanguageSelectView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.antishake.AntiShake;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class EarPhoneConversationRecordingActivity extends BaseFragmentActivity implements ICommonTransCallback, OnViewClickListener {
    private AudioManager audioManager;
    private Bundle bundle;
    private volatile boolean clickedPause;
    private volatile boolean clickedPauseAndReceivedResult;
    private CountDownTimer countDownTimer;
    private int curIndex;
    private boolean curIsLeft;
    private String debugPath;
    private String filePath;
    private boolean hasTransDone;
    private boolean isLeftPressedWaiting;
    private boolean isRecordInit;
    private boolean isRecording;
    private boolean isRightPressedWaiting;
    private boolean isSpeeching;
    private long lastStartTime;
    private CommonTransManager leftTransManager;
    private ActivityEarPhoneConversationRecordingBinding mBinding;
    private MP3RecorderTencent mRecorder;
    private MP3RecorderTencent mRecorder2;
    private MicrosoftTtsManager mTtsManager;
    private int recordStatus;
    private CommonTransManager rightTransManager;
    private long sessionStartTime;
    private long startTime;
    private TranscribeConversationAdapter transcribeAdapter;
    private final List<Transcribe> transcribeList = new ArrayList();
    private boolean mFirst = true;
    private boolean isFirstStart = false;
    private boolean autoScroll = true;
    private int curSpeakPos = -1;
    private int mFrom = MyEnum.TRANSLATE_LANGUAGE.ZH.type;
    private int mTo = MyEnum.TRANSLATE_LANGUAGE.EN.type;
    private final CompositeDisposable mDis = new CompositeDisposable();
    private Transcribe mTranscribe = new Transcribe();
    private int lastED = 0;
    private final RecyclerView.OnScrollListener rcvOnScrollListener = new RecyclerView.OnScrollListener() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity.1
        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            super.onScrollStateChanged(recyclerView, i);
            if (i == 1) {
                LogUtil.m338i("--拖拽--");
                EarPhoneConversationRecordingActivity.this.startTimeCount();
            }
        }
    };
    private final MicrosoftTtsManager.ITextToSpeechListener mTextToSpeechListener = new C07512();

    static /* synthetic */ void lambda$getTransKeys$8(Throwable th) throws Exception {
    }

    @Override // com.aivox.common.speech2text.ICommonTransCallback
    public void onPrepare() {
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.EarPhoneConversationRecordingActivity$2 */
    class C07512 implements MicrosoftTtsManager.ITextToSpeechListener {
        C07512() {
        }

        @Override // com.aivox.common.util.MicrosoftTtsManager.ITextToSpeechListener
        public void onSpeechStart() {
            EarPhoneConversationRecordingActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$2$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m135x81c2d237();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSpeechStart$0$com-aivox-app-activity-EarPhoneConversationRecordingActivity$2 */
        /* synthetic */ void m135x81c2d237() {
            EarPhoneConversationRecordingActivity.this.isSpeeching = true;
            EarPhoneConversationRecordingActivity.this.transcribeAdapter.changePlayStatus(EarPhoneConversationRecordingActivity.this.curSpeakPos);
        }

        @Override // com.aivox.common.util.MicrosoftTtsManager.ITextToSpeechListener
        public void onSpeechFinish() {
            EarPhoneConversationRecordingActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m134x7fdca0c9();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSpeechFinish$1$com-aivox-app-activity-EarPhoneConversationRecordingActivity$2 */
        /* synthetic */ void m134x7fdca0c9() {
            EarPhoneConversationRecordingActivity.this.isSpeeching = false;
            EarPhoneConversationRecordingActivity.this.transcribeAdapter.changePlayStatus(-1);
            if (EarPhoneConversationRecordingActivity.this.isLeftPressedWaiting) {
                EarPhoneConversationRecordingActivity.this.startLeft();
            } else if (EarPhoneConversationRecordingActivity.this.isRightPressedWaiting) {
                EarPhoneConversationRecordingActivity.this.startRight();
            }
        }

        @Override // com.aivox.common.util.MicrosoftTtsManager.ITextToSpeechListener
        public void onSpeechError() {
            EarPhoneConversationRecordingActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m133x7b46b10f();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSpeechError$2$com-aivox-app-activity-EarPhoneConversationRecordingActivity$2 */
        /* synthetic */ void m133x7b46b10f() {
            EarPhoneConversationRecordingActivity.this.isSpeeching = false;
            EarPhoneConversationRecordingActivity.this.transcribeAdapter.changePlayStatus(-1);
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        MicrosoftTtsManager microsoftTtsManager = new MicrosoftTtsManager();
        this.mTtsManager = microsoftTtsManager;
        microsoftTtsManager.setListener(this.mTextToSpeechListener);
        SendManager.getInstance().sendSppData(Constant.CmdUpEnterSwitch);
        this.mBinding = (ActivityEarPhoneConversationRecordingBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_ear_phone_conversation_recording);
        getWindow().setFlags(128, 128);
        Bundle extras = getIntent().getExtras();
        this.bundle = extras;
        if (extras != null) {
            this.mFrom = extras.getInt("from", MyEnum.TRANSLATE_LANGUAGE.ZH.type);
            this.mTo = this.bundle.getInt("to", MyEnum.TRANSLATE_LANGUAGE.EN.type);
            this.mBinding.tvLangMySide.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mFrom).display);
            this.mBinding.tvLangOtherSide.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mTo).display);
        }
        DataHandle.getIns().setCurTransType(3);
        this.mBinding.setClickListener(this);
        this.mBinding.titleView.setOnBackListener(new View.OnClickListener() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m123x79c0313b(view2);
            }
        });
        this.mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        TranscribeConversationAdapter transcribeConversationAdapter = new TranscribeConversationAdapter(this);
        this.transcribeAdapter = transcribeConversationAdapter;
        transcribeConversationAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda13
            @Override // com.aivox.base.databinding.OnItemClickListener
            public final void onItemClick(View view2, int i) {
                this.f$0.m124xdfea0da(view2, i);
            }
        });
        this.mBinding.recyclerview.setAdapter(this.transcribeAdapter);
        this.isFirstStart = true;
        this.mBinding.recyclerview.addOnScrollListener(this.rcvOnScrollListener);
        toOpenTheRecordPermission();
        DataHandle.getIns().setTranscribeWsCanClosed(false);
        this.audioManager = (AudioManager) getSystemService("audio");
        requestAudioFocus();
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-EarPhoneConversationRecordingActivity */
    /* synthetic */ void m123x79c0313b(View view2) {
        doBack();
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-EarPhoneConversationRecordingActivity */
    /* synthetic */ void m124xdfea0da(View view2, int i) {
        if (this.isRecording) {
            return;
        }
        this.curSpeakPos = i;
        this.mTtsManager.stopAll();
        startTts(this.transcribeList.get(i).isConversationLeft() ? this.mTo : this.mFrom, this.transcribeList.get(i).getTranslate());
    }

    private void requestAudioFocus() {
        this.audioManager.requestAudioFocus(new AudioFocusRequest.Builder(1).build());
    }

    @Override // com.aivox.common.speech2text.ICommonTransCallback
    public void onProgress(final String str, final boolean z, final String str2, int i, int i2, boolean z2) {
        runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m128x24ea5b21(str2, z, str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onProgress$3$com-aivox-app-activity-EarPhoneConversationRecordingActivity */
    /* synthetic */ void m128x24ea5b21(String str, boolean z, String str2) {
        if (this.transcribeList.isEmpty()) {
            this.mTranscribe.setBeginAt(this.sessionStartTime + "");
            this.mTranscribe.setAudioId(str);
            this.mTranscribe.setEndAt(System.currentTimeMillis() + "");
            this.mTranscribe.setId(this.curIndex);
            this.mTranscribe.setType(z ? "1" : "0");
            this.mTranscribe.setVar(str2);
            if (z) {
                this.mTranscribe.setOnebest("");
                this.mTranscribe.setVar(str2);
            } else {
                this.mTranscribe.setOnebest(str2);
                this.mTranscribe.setVar("");
            }
            this.mTranscribe.setConversationLeft(this.curIsLeft);
            this.transcribeList.add(this.mTranscribe);
            this.isFirstStart = false;
        } else {
            int size = this.transcribeList.size() - 1;
            if (this.transcribeList.get(size).getId() == this.curIndex) {
                this.transcribeList.get(size).setEndAt(System.currentTimeMillis() + "");
                this.transcribeList.get(size).setType(z ? "1" : "0");
                if (z) {
                    this.transcribeList.get(size).setVar(str2);
                } else {
                    this.transcribeList.get(size).setOnebest(this.transcribeList.get(size).getOnebest() + str2);
                    this.transcribeList.get(size).setVar("");
                }
                this.mTranscribe.setAudioId(str);
            } else {
                Transcribe transcribe = new Transcribe();
                this.mTranscribe = transcribe;
                transcribe.setBeginAt(this.sessionStartTime + "");
                this.mTranscribe.setEndAt(System.currentTimeMillis() + "");
                this.mTranscribe.setAudioId(str);
                if (z) {
                    this.mTranscribe.setVar(str2);
                    this.mTranscribe.setOnebest("");
                } else {
                    this.mTranscribe.setOnebest(str2);
                    this.mTranscribe.setVar("");
                }
                this.mTranscribe.setId(this.curIndex);
                this.mTranscribe.setType(z ? "1" : "0");
                this.transcribeList.add(this.mTranscribe);
                this.mTranscribe.setConversationLeft(this.curIsLeft);
            }
            if (this.clickedPause) {
                this.clickedPauseAndReceivedResult = true;
            }
            try {
                SeqTransModel seqTransModel = SeqTransModel.getInstance();
                int i = this.lastED;
                List<Transcribe> list = this.transcribeList;
                int i2 = list.get(list.size() - 1).isConversationLeft() ? this.mFrom : this.mTo;
                List<Transcribe> list2 = this.transcribeList;
                int i3 = list2.get(list2.size() - 1).isConversationLeft() ? this.mTo : this.mFrom;
                int i4 = this.curIndex;
                StringBuilder sb = new StringBuilder();
                List<Transcribe> list3 = this.transcribeList;
                StringBuilder sbAppend = sb.append(list3.get(list3.size() - 1).getOnebest());
                List<Transcribe> list4 = this.transcribeList;
                seqTransModel.start(i, i2, i3, z, i4, sbAppend.append(list4.get(list4.size() - 1).getVar()).toString(), str, new SeqTransModel.ISeqTranCallback() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda2
                    @Override // com.aivox.common.translate.SeqTransModel.ISeqTranCallback
                    public final void onComplete(boolean z2, int i5, String str3, int i6, String str4) {
                        this.f$0.m127x90abeb82(z2, i5, str3, i6, str4);
                    }
                });
            } catch (Exception e) {
                this.clickedPause = false;
                this.clickedPauseAndReceivedResult = false;
                BaseAppUtils.printErrorMsg(e);
            }
        }
        refreshList();
    }

    /* JADX INFO: renamed from: lambda$onProgress$2$com-aivox-app-activity-EarPhoneConversationRecordingActivity */
    /* synthetic */ void m127x90abeb82(boolean z, int i, String str, int i2, String str2) {
        if (z) {
            this.hasTransDone = false;
        } else {
            this.hasTransDone = true;
            this.lastED = (int) (System.currentTimeMillis() - this.sessionStartTime);
        }
        for (int i3 = 0; i3 < this.transcribeList.size(); i3++) {
            if (i2 == this.transcribeList.get(i3).getId()) {
                this.transcribeList.get(i3).setTranslate(str);
            }
        }
        LogUtil.m337e("TTS", "onProgress: " + str);
        if (this.clickedPauseAndReceivedResult && !z) {
            startRead();
        }
        runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.refreshList();
            }
        });
    }

    @Override // com.aivox.common.speech2text.ICommonTransCallback
    public void onComplete(String str) {
        LogUtil.m337e("TTS", "onComplete: " + str);
        if (!this.clickedPauseAndReceivedResult && this.hasTransDone && BaseStringUtil.isNotEmpty(str)) {
            startRead();
        }
    }

    @Override // com.aivox.common.speech2text.ICommonTransCallback
    public void onError(String str, boolean z) {
        if (z) {
            runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.trans_error_40089));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.aivox.app.activity.EarPhoneConversationRecordingActivity$3] */
    public void startTimeCount() {
        this.countDownTimer = new CountDownTimer(5000L, 500L) { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity.3
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                EarPhoneConversationRecordingActivity.this.autoScroll = false;
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                EarPhoneConversationRecordingActivity.this.autoScroll = true;
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshList() {
        if (isActivityShow(EarPhoneConversationRecordingActivity.class)) {
            TranscribeConversationAdapter transcribeConversationAdapter = this.transcribeAdapter;
            if (transcribeConversationAdapter != null) {
                transcribeConversationAdapter.setmDate(this.transcribeList);
            }
            if (this.mBinding == null || this.transcribeAdapter.getItemCount() <= 0 || !this.autoScroll) {
                return;
            }
            this.mBinding.recyclerview.scrollToPosition(this.transcribeAdapter.getItemCount() - 1);
        }
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        if (!AntiShake.check(view2) && this.isFirstStart) {
            int id = view2.getId();
            if (id == this.mBinding.tvLangMySide.getId() || id == this.mBinding.tvLangOtherSide.getId()) {
                if (this.isRecording) {
                    return;
                }
                reSelectLang(id == this.mBinding.tvLangMySide.getId());
            } else if (id == this.mBinding.ivSwitchLang.getId()) {
                int i = this.mFrom;
                this.mFrom = this.mTo;
                this.mTo = i;
                this.mBinding.tvLangMySide.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mFrom).display);
                this.mBinding.tvLangOtherSide.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mTo).display);
                LanguageUtils.setDefaultLang(this.context, 3, this.mFrom, this.mTo);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLeft() {
        if (this.mFrom == this.mTo) {
            DialogUtils.showDialogWithDefBtn(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.same_lang_notice), null, null, true, true);
            return;
        }
        if (System.currentTimeMillis() - this.lastStartTime < 300) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.audio_export_frequent_operation));
            return;
        }
        if (this.isSpeeching) {
            this.isLeftPressedWaiting = true;
            this.isRightPressedWaiting = false;
            return;
        }
        requestAudioFocus();
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.sessionStartTime = jCurrentTimeMillis;
        this.lastStartTime = jCurrentTimeMillis;
        AudioUtils.getIns(this).openSco();
        this.clickedPauseAndReceivedResult = false;
        this.clickedPause = false;
        this.isRecording = true;
        refreshMicStatus(true, false);
        CommonTransManager commonTransManager = this.rightTransManager;
        if (commonTransManager != null && commonTransManager.getTranStatus() == MyEnum.TRANS_MODEL_STATUS.TRANSCRIBING.type) {
            this.rightTransManager.pauseAudio();
        }
        CommonTransManager commonTransManager2 = this.leftTransManager;
        if (commonTransManager2 != null) {
            if (commonTransManager2.getTranStatus() == MyEnum.TRANS_MODEL_STATUS.NOT_START.type) {
                this.leftTransManager.startRecording(this.mRecorder);
                this.leftTransManager.startTrans(false, this.mFrom, this.debugPath, this);
            } else {
                this.leftTransManager.resumeAudio();
            }
            SendManager.getInstance().sendSppData(Constant.CmdUpPlayStart);
        }
        new Handler().postDelayed(new Runnable() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m131x52c2c93c();
            }
        }, 10L);
    }

    /* JADX INFO: renamed from: lambda$startLeft$5$com-aivox-app-activity-EarPhoneConversationRecordingActivity */
    /* synthetic */ void m131x52c2c93c() {
        this.curIsLeft = true;
        this.curIndex++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRight() {
        if (this.mFrom == this.mTo) {
            DialogUtils.showDialogWithDefBtn(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.same_lang_notice), null, null, true, true);
            return;
        }
        if (System.currentTimeMillis() - this.lastStartTime < 300) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.audio_export_frequent_operation));
            return;
        }
        if (this.isSpeeching) {
            this.isLeftPressedWaiting = false;
            this.isRightPressedWaiting = true;
            return;
        }
        requestAudioFocus();
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.sessionStartTime = jCurrentTimeMillis;
        this.lastStartTime = jCurrentTimeMillis;
        AudioUtils.getIns(this).openSco();
        this.clickedPauseAndReceivedResult = false;
        this.clickedPause = false;
        this.isRecording = true;
        refreshMicStatus(false, true);
        CommonTransManager commonTransManager = this.leftTransManager;
        if (commonTransManager != null && commonTransManager.getTranStatus() == MyEnum.TRANS_MODEL_STATUS.TRANSCRIBING.type) {
            this.leftTransManager.pauseAudio();
        }
        CommonTransManager commonTransManager2 = this.rightTransManager;
        if (commonTransManager2 != null) {
            if (commonTransManager2.getTranStatus() == MyEnum.TRANS_MODEL_STATUS.NOT_START.type) {
                this.rightTransManager.startRecording(this.mRecorder2);
                this.rightTransManager.startTrans(false, this.mTo, this.debugPath, this);
            } else {
                this.rightTransManager.resumeAudio();
            }
            SendManager.getInstance().sendSppData(Constant.CmdUpPlayStart);
        }
        new Handler().postDelayed(new Runnable() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m132x44c71690();
            }
        }, 10L);
    }

    /* JADX INFO: renamed from: lambda$startRight$6$com-aivox-app-activity-EarPhoneConversationRecordingActivity */
    /* synthetic */ void m132x44c71690() {
        this.curIsLeft = false;
        this.curIndex++;
    }

    private void refreshMicStatus(boolean z, boolean z2) {
        if (z || z2) {
            this.isLeftPressedWaiting = false;
            this.isRightPressedWaiting = false;
            this.mBinding.ivWave.setBackgroundResource(C1034R.drawable.wave_anim);
            ((AnimationDrawable) this.mBinding.ivWave.getBackground()).start();
        } else {
            AnimationDrawable animationDrawable = (AnimationDrawable) this.mBinding.ivWave.getBackground();
            if (animationDrawable != null) {
                animationDrawable.stop();
            }
            this.mBinding.ivWave.setBackgroundResource(C1034R.drawable.ic_audio_wave_frame_0);
        }
        if (z) {
            this.mBinding.tvLeftRecording.setVisibility(0);
            this.mBinding.tvRightRecording.setVisibility(4);
        } else if (z2) {
            this.mBinding.tvLeftRecording.setVisibility(4);
            this.mBinding.tvRightRecording.setVisibility(0);
        } else {
            this.mBinding.tvLeftRecording.setVisibility(4);
            this.mBinding.tvRightRecording.setVisibility(4);
        }
        this.mBinding.tvSpeakHint.setVisibility((!this.transcribeList.isEmpty() || this.isRecording) ? 8 : 0);
        this.mBinding.tvSpeakHint.setVisibility((!this.transcribeList.isEmpty() || this.isRecording) ? 8 : 0);
    }

    private void pauseAll() {
        this.isLeftPressedWaiting = false;
        this.isRightPressedWaiting = false;
        if (this.isSpeeching) {
            return;
        }
        refreshMicStatus(false, false);
        this.lastStartTime = System.currentTimeMillis();
        SendManager.getInstance().sendSppData(Constant.CmdUpPlayEnd);
        CommonTransManager commonTransManager = this.leftTransManager;
        if (commonTransManager != null && commonTransManager.getTranStatus() == MyEnum.TRANS_MODEL_STATUS.TRANSCRIBING.type) {
            this.leftTransManager.pauseAudio();
        }
        CommonTransManager commonTransManager2 = this.rightTransManager;
        if (commonTransManager2 != null && commonTransManager2.getTranStatus() == MyEnum.TRANS_MODEL_STATUS.TRANSCRIBING.type) {
            this.rightTransManager.pauseAudio();
        }
        this.isRecording = false;
        this.clickedPause = true;
    }

    private void startRead() {
        if (!this.transcribeList.isEmpty() && !this.isRecording) {
            this.curSpeakPos = this.transcribeList.size() - 1;
            List<Transcribe> list = this.transcribeList;
            int i = list.get(list.size() + (-1)).isConversationLeft() ? this.mTo : this.mFrom;
            startTts(i, this.transcribeList.get(r1.size() - 1).getTranslate());
        }
        this.clickedPauseAndReceivedResult = false;
    }

    private void startTts(int i, String str) {
        LogUtil.m337e("TTS", "startTts: " + str);
        AudioUtils.getIns(this).closeSco();
        this.mTtsManager.speak(str, i, true);
    }

    private void stopAll() {
        CommonTransManager commonTransManager = this.leftTransManager;
        if (commonTransManager != null) {
            commonTransManager.stopAudio();
        }
        CommonTransManager commonTransManager2 = this.rightTransManager;
        if (commonTransManager2 != null) {
            commonTransManager2.stopAudio();
        }
    }

    private void startTrans() {
        LogUtil.m338i("recordStatus=" + this.recordStatus);
        if (this.recordStatus == 0) {
            this.startTime = DateUtil.getDateTimeNow();
        }
        setRecordStatus(1);
        if (!this.isRecordInit) {
            recordInit();
            this.isRecordInit = true;
        }
        LogUtil.m338i("ws_recordStatus:" + this.recordStatus);
        recordStart();
    }

    private void toOpenTheRecordPermission() {
        if (isDestroyed()) {
            return;
        }
        PermissionUtils.getIns(this, new C07564()).request("android.permission.RECORD_AUDIO");
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.EarPhoneConversationRecordingActivity$4 */
    class C07564 implements PermissionCallback {
        C07564() {
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void granted(boolean z) {
            if (z) {
                LogUtil.m338i("已获得录音权限");
                EarPhoneConversationRecordingActivity.this.getTransKeys();
            } else {
                DialogUtils.showDialogWithBtnIds(EarPhoneConversationRecordingActivity.this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.no_microphone_permissions), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$4$$ExternalSyntheticLambda0
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        this.f$0.m136xfb9ccdbf(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$4$$ExternalSyntheticLambda1
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        this.f$0.m137x79fdd19e(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, true, false, C0874R.string.cancel, C0874R.string.sure);
            }
        }

        /* JADX INFO: renamed from: lambda$granted$0$com-aivox-app-activity-EarPhoneConversationRecordingActivity$4 */
        /* synthetic */ void m136xfb9ccdbf(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            EarPhoneConversationRecordingActivity.this.finish();
        }

        /* JADX INFO: renamed from: lambda$granted$1$com-aivox-app-activity-EarPhoneConversationRecordingActivity$4 */
        /* synthetic */ void m137x79fdd19e(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            BaseAppUtils.openSettingView(EarPhoneConversationRecordingActivity.this);
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void requestError(Throwable th) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.no_microphone_permissions));
            BaseAppUtils.openSettingView(EarPhoneConversationRecordingActivity.this.context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getTransKeys() {
        int i = this.mFrom;
        if (i == 99) {
            i = 9;
        }
        int i2 = this.mTo;
        new AudioService(this).getTransKeys(i, i2 != 99 ? i2 : 9).subscribe(new Consumer() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m122x69570cc3((TransKeyBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                EarPhoneConversationRecordingActivity.lambda$getTransKeys$8((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getTransKeys$7$com-aivox-app-activity-EarPhoneConversationRecordingActivity */
    /* synthetic */ void m122x69570cc3(TransKeyBean transKeyBean) throws Exception {
        String strDecrypt = SerAESUtil.decrypt(transKeyBean.getFrom(), Constant.DECRYPT_KEY);
        String strDecrypt2 = SerAESUtil.decrypt(transKeyBean.getTo(), Constant.DECRYPT_KEY);
        if (BaseStringUtil.isNotEmpty(strDecrypt)) {
            JSONObject jSONObject = new JSONObject(strDecrypt);
            if (jSONObject.has("appKey")) {
                SPUtil.put(SPUtil.ALI_TRANS_KEY + this.mFrom, jSONObject.getString("appKey"));
                SPUtil.put(SPUtil.ALI_TRANS_TOKEN + this.mFrom, jSONObject.getString(SPUtil.TOKEN));
            } else {
                SPUtil.put(SPUtil.TENCENT_TRANS_SK, jSONObject.getString("secretKey"));
                SPUtil.put(SPUtil.TENCENT_TRANS_SI, jSONObject.getString("secretId"));
                SPUtil.put(SPUtil.TENCENT_TRANS_ID, jSONObject.getString("appId"));
            }
        }
        if (BaseStringUtil.isNotEmpty(strDecrypt2)) {
            JSONObject jSONObject2 = new JSONObject(strDecrypt2);
            if (jSONObject2.has("appKey")) {
                SPUtil.put(SPUtil.ALI_TRANS_KEY + this.mTo, jSONObject2.getString("appKey"));
                SPUtil.put(SPUtil.ALI_TRANS_TOKEN + this.mTo, jSONObject2.getString(SPUtil.TOKEN));
            } else {
                SPUtil.put(SPUtil.TENCENT_TRANS_SK, jSONObject2.getString("secretKey"));
                SPUtil.put(SPUtil.TENCENT_TRANS_SI, jSONObject2.getString("secretId"));
                SPUtil.put(SPUtil.TENCENT_TRANS_ID, jSONObject2.getString("appId"));
            }
        }
        startTrans();
    }

    private void recordInit() {
        LogUtil.m336e("-----第一次开始----->");
        this.filePath = FileUtils.getAudioFilePath(this);
        File file = new File(this.filePath);
        if (!file.exists() && !file.mkdirs()) {
            DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), getString(C0874R.string.create_file_fail), null, null, false, false, C0874R.string.sure, C0874R.string.sure);
            return;
        }
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_METHOD, "ConversationRecordingActivity:recordInit");
        this.filePath = FileUtils.getAudioFilePath(this) + DateUtil.getDateFromTimestamp(this.startTime, DateUtil.YYYY_MMDD_HHMM_SS) + "." + AudioType.WAV.getType();
        this.debugPath = FileUtils.getLogFilePath(this);
        this.leftTransManager = new CommonTransManager(this, false);
        this.rightTransManager = new CommonTransManager(this, false);
    }

    public void setRecordStatus(int i) {
        this.recordStatus = i;
        SPUtil.put(SPUtil.RECORD_STATE, Integer.valueOf(i));
    }

    public void recordStart() {
        LogUtil.m338i("mFirst=" + this.mFirst);
        try {
            this.mRecorder = new MP3RecorderTencent(this.filePath);
            this.mRecorder2 = new MP3RecorderTencent(this.filePath);
            if (this.mFirst) {
                this.mFirst = false;
            }
        } catch (Exception e) {
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_METHOD, "recordStartError" + e);
            LogUtil.m336e("录音异常：" + e.getLocalizedMessage());
            recordError();
            DialogUtils.showDialogWithDefBtnAndSingleListener(this, Integer.valueOf(C0874R.string.reminder), e.toString(), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda7
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m130xcf269070(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, false, false);
        }
    }

    /* JADX INFO: renamed from: lambda$recordStart$9$com-aivox-app-activity-EarPhoneConversationRecordingActivity */
    /* synthetic */ void m130xcf269070(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        doBack();
    }

    public void recordError() {
        LogUtil.m336e("--recordError--");
        setRecordStatus(0);
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void onEventMainThread(EventBean eventBean) {
        LogUtil.m338i("Recording:onEventMainThread" + eventBean.getFrom());
        int from = eventBean.getFrom();
        if (from == 46) {
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "networkReconnect");
            ToastUtil.showShort(Integer.valueOf(C0874R.string.weak_net_reconnect_recording_tip));
            return;
        }
        if (from == 59) {
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "networkDisconnect");
            if (NetUtil.isConnected(this)) {
                return;
            }
            ToastUtil.showShort(Integer.valueOf(C0874R.string.trans_error_40089));
            return;
        }
        if (from != 301 && from != 306) {
            switch (from) {
                case 309:
                    startLeft();
                    break;
                case 310:
                case 312:
                    pauseAll();
                    break;
                case 311:
                    startRight();
                    break;
            }
            return;
        }
        stopAll();
        DialogUtils.showDialogWithBtnIds(this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.earphone_break_while_conversation), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda10
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m125x73cbde17(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda11
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m126x80a4db6(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, true, false, C0874R.string.cancel, C0874R.string.sure);
    }

    /* JADX INFO: renamed from: lambda$onEventMainThread$10$com-aivox-app-activity-EarPhoneConversationRecordingActivity */
    /* synthetic */ void m125x73cbde17(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        doBack();
    }

    /* JADX INFO: renamed from: lambda$onEventMainThread$11$com-aivox-app-activity-EarPhoneConversationRecordingActivity */
    /* synthetic */ void m126x80a4db6(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        doBack();
        Bundle bundle = new Bundle();
        bundle.putInt("from", MyEnum.TRANSLATE_LANGUAGE.ZH.type);
        bundle.putInt("to", MyEnum.TRANSLATE_LANGUAGE.EN.type);
        ARouterUtils.startWithActivity(this, RecordAction.CONVERSATION_RECORD_ING, bundle);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        LogUtil.m338i("Recording_onBackPressed");
        doBack();
    }

    private void doBack() {
        LogUtil.m338i("Recording_doBack:" + this.recordStatus);
        setRecordStatus(3);
        stopAll();
        ARouterUtils.startWithActivity(this, MainAction.MAIN);
        finishAndRemoveTask();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.m338i("recording_onNewIntent");
        setIntent(intent);
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "ConversationRecordingActivity:onCreate", true);
        super.onCreate(bundle);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "ConversationRecordingActivity:onStart");
        super.onStart();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "ConversationRecordingActivity:onResume");
        super.onResume();
        LogUtil.m338i("Recording_onResume--" + this.bundle);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        LogUtil.m338i("RecordingAct2_onPause:");
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "ConversationRecordingActivity:onPause");
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "ConversationRecordingActivity:onStop");
        super.onStop();
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "ConversationRecordingActivity:onDestroy");
        SendManager.getInstance().sendSppData(Constant.CmdUpExitSwitch);
        super.onDestroy();
        SeqTransModel.getInstance().destroy();
        FileUtils.deleteFile(this.filePath);
        AudioUtils.getIns(this).closeSco();
        this.audioManager.abandonAudioFocus(null);
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        MicrosoftTtsManager microsoftTtsManager = this.mTtsManager;
        if (microsoftTtsManager != null) {
            microsoftTtsManager.releaseAll();
        }
        SPUtil.put(SPUtil.RECORD_STATE, 0);
        this.transcribeList.clear();
        this.mDis.clear();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        LogUtil.m338i("RecordingAct2_onSaveInstanceState:");
        super.onSaveInstanceState(bundle);
    }

    @Override // android.app.Activity
    public void onRestoreInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        LogUtil.m338i("RecordingAct2_onRestoreInstanceState:");
        super.onRestoreInstanceState(bundle, persistableBundle);
    }

    private void reSelectLang(final boolean z) {
        LanguageUtils.showLangSelectView(this.context, z, new LanguageSelectView.LangSelectListener() { // from class: com.aivox.app.activity.EarPhoneConversationRecordingActivity$$ExternalSyntheticLambda4
            @Override // com.aivox.common.view.LanguageSelectView.LangSelectListener
            public final void onLangSelected(MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
                this.f$0.m129x6703f6ce(z, translate_language, translate_language2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reSelectLang$12$com-aivox-app-activity-EarPhoneConversationRecordingActivity */
    /* synthetic */ void m129x6703f6ce(boolean z, MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
        if (z) {
            this.mFrom = translate_language.type;
        } else {
            this.mTo = translate_language.type;
        }
        this.mBinding.tvLangMySide.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mFrom).display);
        this.mBinding.tvLangOtherSide.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mTo).display);
        this.isRecordInit = false;
        getTransKeys();
        this.mTranscribe = new Transcribe();
        this.transcribeList.clear();
        refreshList();
        LanguageUtils.setDefaultLang(this.context, 3, this.mFrom, this.mTo);
    }
}
