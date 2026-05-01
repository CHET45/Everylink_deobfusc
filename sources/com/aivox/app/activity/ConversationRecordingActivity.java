package com.aivox.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.TranscribeConversationAdapter;
import com.aivox.app.adapter.TranscribeConversationTextAdapter;
import com.aivox.app.databinding.ActivityConversationRecordingBinding;
import com.aivox.app.view.ConversationRecordIconView;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.databinding.OnItemClickListener;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.permission.PermissionCallback;
import com.aivox.base.permission.PermissionUtils;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.RecordAction;
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
import com.aivox.base.util.SpanUtils;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.AudioType;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.TransKeyBean;
import com.aivox.common.model.Transcribe;
import com.aivox.common.p003db.entity.ConversationEntity;
import com.aivox.common.p003db.maneger.ConversationDbManager;
import com.aivox.common.speech2text.CommonTransManager;
import com.aivox.common.speech2text.ICommonTransCallback;
import com.aivox.common.speech2text.MP3RecorderTencent;
import com.aivox.common.translate.SeqTransModel;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.LanguageUtils;
import com.aivox.common.util.MicrosoftTtsManager;
import com.aivox.common.util.VibrateHelp;
import com.aivox.common.util.encrypt.SerAESUtil;
import com.aivox.common.view.LanguageSelectView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.antishake.AntiShake;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ConversationRecordingActivity extends BaseFragmentActivity implements OnViewClickListener {
    private static final int MAX_SEC = 30;
    private Bundle bundle;
    SpannableString content;
    private CountDownTimer countDownTimer;
    private int curIndex;
    private boolean curIsLeft;
    private String debugPath;
    private long downTime;
    private String filePath;
    private boolean hasTransDone;
    private boolean isFaceMode;
    private boolean isLeftSpeaking;
    private boolean isNoNet;
    private boolean isRecordBtnPressed;
    private boolean isRecordInit;
    private boolean isRecording;
    private boolean isRightSpeaking;
    private int leftTime;
    private CommonTransManager leftTransManager;
    private boolean mAutoSpeakEnable;
    private ActivityConversationRecordingBinding mBinding;
    private TranscribeConversationTextAdapter mBtmAdapter;
    private int mCurCount;
    private int mCurSpeakingIndex;
    private RecordingHandler mHandler;
    private int mLastCount;
    private ConversationDbManager mManager;
    private int mMaxCount;
    private MP3RecorderTencent mRecorder;
    private MP3RecorderTencent mRecorder2;
    private TranscribeConversationTextAdapter mTopAdapter;
    private MicrosoftTtsManager mTtsManager;
    private String mUid;
    int offset;
    private int recordStatus;
    private CommonTransManager rightTransManager;
    private long sessionStartTime;
    private boolean showBreakNoti;
    SpanUtils spanUtils;
    private long startTime;
    private TranscribeConversationAdapter transcribeAdapter;
    private final List<Transcribe> transcribeList = new ArrayList();
    private int mMySideLang = MyEnum.TRANSLATE_LANGUAGE.ZH.type;
    private int mOtherSideLang = MyEnum.TRANSLATE_LANGUAGE.EN.type;
    private final CompositeDisposable mDis = new CompositeDisposable();
    private boolean mFirst = true;
    private boolean autoScroll = true;
    private RecyclerView.OnScrollListener rcvOnScrollListener = new RecyclerView.OnScrollListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity.1
        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            super.onScrollStateChanged(recyclerView, i);
            if (i == 1) {
                LogUtil.m338i("--拖拽--");
                ConversationRecordingActivity.this.startTimeCount();
            }
        }
    };
    private ConversationRecordIconView.onTouchChangeListener onTouchChangeListener = new ConversationRecordIconView.onTouchChangeListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity.2
        AnimationDrawable anim;

        @Override // com.aivox.app.view.ConversationRecordIconView.onTouchChangeListener
        public void onStart(boolean z) {
            ConversationRecordingActivity.this.mTtsManager.stopAll();
            ConversationRecordingActivity.this.mBinding.ivWave.setBackgroundResource(C1034R.drawable.wave_anim);
            AnimationDrawable animationDrawable = (AnimationDrawable) ConversationRecordingActivity.this.mBinding.ivWave.getBackground();
            this.anim = animationDrawable;
            animationDrawable.start();
            if (ConversationRecordingActivity.this.isRecording) {
                return;
            }
            ConversationRecordingActivity.this.isRecordBtnPressed = true;
            ConversationRecordingActivity.this.startTrans(z);
        }

        @Override // com.aivox.app.view.ConversationRecordIconView.onTouchChangeListener
        public void onEnd(boolean z, boolean z2) {
            AnimationDrawable animationDrawable = this.anim;
            if (animationDrawable != null) {
                animationDrawable.stop();
            }
            ConversationRecordingActivity.this.mBinding.ivWave.setBackgroundResource(C1034R.drawable.ic_audio_wave_frame_0);
            ConversationRecordingActivity.this.isRecordBtnPressed = false;
            if (z && ConversationRecordingActivity.this.isRightSpeaking) {
                return;
            }
            if (z || !ConversationRecordingActivity.this.isLeftSpeaking) {
                if (z2) {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.hint_speech_too_short));
                }
                ConversationRecordingActivity.this.isRecording = false;
                ConversationRecordingActivity.this.pauseAll();
            }
        }
    };
    private ICommonTransCallback transCallback = new C07293();
    private Transcribe mTranscribe = new Transcribe();
    private int lastED = 0;

    /* JADX INFO: renamed from: com.aivox.app.activity.ConversationRecordingActivity$3 */
    class C07293 implements ICommonTransCallback {
        @Override // com.aivox.common.speech2text.ICommonTransCallback
        public void onPrepare() {
        }

        C07293() {
        }

        @Override // com.aivox.common.speech2text.ICommonTransCallback
        public void onProgress(final String str, final boolean z, final String str2, int i, int i2, boolean z2) {
            LogUtil.m334d("onProgress: id = " + str2 + " text = " + str + " isLeftSpeaking = " + ConversationRecordingActivity.this.isLeftSpeaking);
            ConversationRecordingActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.ConversationRecordingActivity$3$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m92xe98d0f69(str2, z, str);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onProgress$2$com-aivox-app-activity-ConversationRecordingActivity$3 */
        /* synthetic */ void m92xe98d0f69(String str, boolean z, String str2) {
            if (ConversationRecordingActivity.this.transcribeList.isEmpty()) {
                ConversationRecordingActivity.this.mTranscribe.setBeginAt(ConversationRecordingActivity.this.sessionStartTime + "");
                ConversationRecordingActivity.this.mTranscribe.setAudioId(str);
                ConversationRecordingActivity.this.mTranscribe.setEndAt(System.currentTimeMillis() + "");
                ConversationRecordingActivity.this.mTranscribe.setId(ConversationRecordingActivity.this.curIndex);
                ConversationRecordingActivity.this.mTranscribe.setType(z ? "1" : "0");
                ConversationRecordingActivity.this.mTranscribe.setVar(str2);
                if (!z) {
                    ConversationRecordingActivity.this.mTranscribe.setOnebest(str2);
                    ConversationRecordingActivity.this.mTranscribe.setVar("");
                } else {
                    ConversationRecordingActivity.this.mTranscribe.setOnebest("");
                    ConversationRecordingActivity.this.mTranscribe.setVar(str2);
                }
                ConversationRecordingActivity.this.mTranscribe.setConversationLeft(ConversationRecordingActivity.this.curIsLeft);
                ConversationRecordingActivity.this.transcribeList.add(ConversationRecordingActivity.this.mTranscribe);
            } else {
                int size = ConversationRecordingActivity.this.transcribeList.size() - 1;
                if (((Transcribe) ConversationRecordingActivity.this.transcribeList.get(size)).getId() == ConversationRecordingActivity.this.curIndex) {
                    ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(size)).setEndAt(System.currentTimeMillis() + "");
                    ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(size)).setType(z ? "1" : "0");
                    if (!z) {
                        ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(size)).setOnebest(((Transcribe) ConversationRecordingActivity.this.transcribeList.get(size)).getOnebest() + str2);
                        ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(size)).setVar("");
                    } else {
                        ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(size)).setVar(str2);
                    }
                    ConversationRecordingActivity.this.mTranscribe.setAudioId(str);
                } else {
                    ConversationRecordingActivity.this.mTranscribe = new Transcribe();
                    ConversationRecordingActivity.this.mTranscribe.setBeginAt(ConversationRecordingActivity.this.sessionStartTime + "");
                    ConversationRecordingActivity.this.mTranscribe.setEndAt(System.currentTimeMillis() + "");
                    ConversationRecordingActivity.this.mTranscribe.setAudioId(str);
                    if (!z) {
                        ConversationRecordingActivity.this.mTranscribe.setOnebest(str2);
                        ConversationRecordingActivity.this.mTranscribe.setVar("");
                    } else {
                        ConversationRecordingActivity.this.mTranscribe.setVar(str2);
                        ConversationRecordingActivity.this.mTranscribe.setOnebest("");
                    }
                    ConversationRecordingActivity.this.mTranscribe.setId(ConversationRecordingActivity.this.curIndex);
                    ConversationRecordingActivity.this.mTranscribe.setType(z ? "1" : "0");
                    ConversationRecordingActivity.this.mTranscribe.setConversationLeft(ConversationRecordingActivity.this.curIsLeft);
                    ConversationRecordingActivity.this.transcribeList.add(ConversationRecordingActivity.this.mTranscribe);
                }
                try {
                    SeqTransModel.getInstance().start(ConversationRecordingActivity.this.lastED, ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(ConversationRecordingActivity.this.transcribeList.size() + (-1))).isConversationLeft() ? ConversationRecordingActivity.this.mMySideLang : ConversationRecordingActivity.this.mOtherSideLang, ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(ConversationRecordingActivity.this.transcribeList.size() + (-1))).isConversationLeft() ? ConversationRecordingActivity.this.mOtherSideLang : ConversationRecordingActivity.this.mMySideLang, z, ConversationRecordingActivity.this.curIndex, ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(ConversationRecordingActivity.this.transcribeList.size() - 1)).getOnebest() + ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(ConversationRecordingActivity.this.transcribeList.size() - 1)).getVar(), str, new SeqTransModel.ISeqTranCallback() { // from class: com.aivox.app.activity.ConversationRecordingActivity$3$$ExternalSyntheticLambda0
                        @Override // com.aivox.common.translate.SeqTransModel.ISeqTranCallback
                        public final void onComplete(boolean z2, int i, String str3, int i2, String str4) {
                            this.f$0.m91xe856bc8a(z2, i, str3, i2, str4);
                        }
                    });
                } catch (Exception e) {
                    BaseAppUtils.printErrorMsg(e);
                }
            }
            ConversationRecordingActivity.this.refreshList(false);
        }

        /* JADX INFO: renamed from: lambda$onProgress$1$com-aivox-app-activity-ConversationRecordingActivity$3 */
        /* synthetic */ void m91xe856bc8a(boolean z, int i, String str, int i2, String str2) {
            if (z) {
                ConversationRecordingActivity.this.hasTransDone = false;
            } else {
                ConversationRecordingActivity.this.hasTransDone = true;
                ConversationRecordingActivity.this.lastED = (int) (System.currentTimeMillis() - ConversationRecordingActivity.this.sessionStartTime);
            }
            for (int i3 = 0; i3 < ConversationRecordingActivity.this.transcribeList.size(); i3++) {
                if (i2 == ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(i3)).getId()) {
                    ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(i3)).setTranslate(str);
                }
            }
            if (ConversationRecordingActivity.this.mAutoSpeakEnable && !ConversationRecordingActivity.this.isRecording) {
                LogUtil.m337e("TTS", "onProgress: " + str);
                ConversationRecordingActivity conversationRecordingActivity = ConversationRecordingActivity.this;
                conversationRecordingActivity.mCurSpeakingIndex = conversationRecordingActivity.transcribeList.size() - 1;
                ConversationRecordingActivity.this.mTtsManager.speak(str, ConversationRecordingActivity.this.curIsLeft ? ConversationRecordingActivity.this.mOtherSideLang : ConversationRecordingActivity.this.mMySideLang, true);
            }
            ConversationRecordingActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.ConversationRecordingActivity$3$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m90xe72069ab();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onProgress$0$com-aivox-app-activity-ConversationRecordingActivity$3 */
        /* synthetic */ void m90xe72069ab() {
            if (!ConversationRecordingActivity.this.isRecordBtnPressed) {
                ConversationRecordingActivity.this.pauseAll();
            }
            ConversationRecordingActivity conversationRecordingActivity = ConversationRecordingActivity.this;
            conversationRecordingActivity.refreshList(conversationRecordingActivity.hasTransDone);
        }

        @Override // com.aivox.common.speech2text.ICommonTransCallback
        public void onComplete(String str) {
            LogUtil.m337e("TTS", "onComplete: " + ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(ConversationRecordingActivity.this.transcribeList.size() - 1)).getTranslate());
            if (ConversationRecordingActivity.this.mAutoSpeakEnable && !ConversationRecordingActivity.this.isRecording && ConversationRecordingActivity.this.hasTransDone) {
                ConversationRecordingActivity conversationRecordingActivity = ConversationRecordingActivity.this;
                conversationRecordingActivity.mCurSpeakingIndex = conversationRecordingActivity.transcribeList.size() - 1;
                ConversationRecordingActivity.this.mTtsManager.speak(((Transcribe) ConversationRecordingActivity.this.transcribeList.get(ConversationRecordingActivity.this.transcribeList.size() - 1)).getTranslate(), ((Transcribe) ConversationRecordingActivity.this.transcribeList.get(ConversationRecordingActivity.this.transcribeList.size() - 1)).isConversationLeft() ? ConversationRecordingActivity.this.mOtherSideLang : ConversationRecordingActivity.this.mMySideLang, true);
            }
            if (ConversationRecordingActivity.this.isRecordBtnPressed) {
                return;
            }
            ConversationRecordingActivity.this.pauseAll();
        }

        @Override // com.aivox.common.speech2text.ICommonTransCallback
        public void onError(final String str, boolean z) {
            if (z) {
                ConversationRecordingActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.ConversationRecordingActivity$3$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m89x721f4245(str);
                    }
                });
            }
        }

        /* JADX INFO: renamed from: lambda$onError$3$com-aivox-app-activity-ConversationRecordingActivity$3 */
        /* synthetic */ void m89x721f4245(String str) {
            ConversationRecordingActivity.this.refreshNoti(0, str, null);
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivityConversationRecordingBinding activityConversationRecordingBinding = (ActivityConversationRecordingBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_conversation_recording);
        this.mBinding = activityConversationRecordingBinding;
        activityConversationRecordingBinding.setClickListener(this);
        this.mManager = ConversationDbManager.getInstance(AppApplication.getIns().getDaoSession());
        this.mUid = (String) SPUtil.get(SPUtil.USER_ID, "0");
        DataHandle.getIns().setCurTransType(3);
        if (!DataHandle.getIns().isVip()) {
            this.mMaxCount = DataHandle.getIns().getFunctionBean().getBilingualCountLimit().intValue();
            long jLongValue = ((Long) SPUtil.getWithUid(SPUtil.CONVERSATION_TIME_STAMP, 0L)).longValue();
            if (jLongValue == 0) {
                SPUtil.putWithUid(SPUtil.CONVERSATION_TIME_STAMP, Long.valueOf(System.currentTimeMillis()));
                SPUtil.putWithUid(SPUtil.CONVERSATION_COUNT, 0);
            } else {
                if (System.currentTimeMillis() - jLongValue > 86400000) {
                    SPUtil.putWithUid(SPUtil.CONVERSATION_TIME_STAMP, Long.valueOf(System.currentTimeMillis()));
                    SPUtil.putWithUid(SPUtil.CONVERSATION_COUNT, 0);
                }
                this.mLastCount = ((Integer) SPUtil.getWithUid(SPUtil.CONVERSATION_COUNT, 0)).intValue();
            }
        }
        MicrosoftTtsManager microsoftTtsManager = new MicrosoftTtsManager();
        this.mTtsManager = microsoftTtsManager;
        microsoftTtsManager.setOnEventChangeListener(new MicrosoftTtsManager.OnEventChangeListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda0
            @Override // com.aivox.common.util.MicrosoftTtsManager.OnEventChangeListener
            public final void onEventChanged(int i) {
                this.f$0.m71xa8cf86b2(i);
            }
        });
        getWindow().setFlags(128, 128);
        Bundle extras = getIntent().getExtras();
        this.bundle = extras;
        if (extras != null) {
            this.mMySideLang = extras.getInt("from", MyEnum.TRANSLATE_LANGUAGE.ZH.type);
            this.mOtherSideLang = this.bundle.getInt("to", MyEnum.TRANSLATE_LANGUAGE.EN.type);
            this.mBinding.tvLanguageFrom.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mMySideLang).display);
            this.mBinding.tvLanguageTo.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mOtherSideLang).display);
            this.mBinding.tvLanguageFromFace.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mMySideLang).display);
            this.mBinding.tvLanguageToFace.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mOtherSideLang).display);
            this.mBinding.tvLangMySideS2s.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mMySideLang).display);
            this.mBinding.tvLangOtherSideS2s.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mOtherSideLang).display);
            this.mBinding.tvLangMySideF2f.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mMySideLang).display);
            this.mBinding.tvLangOtherSideF2f.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mOtherSideLang).display);
        }
        this.mBinding.tvNoticeTop.setText(MyEnum.TRANSLATE_LANGUAGE.getNotice(this.mOtherSideLang));
        this.mBinding.tvNoticeBtm.setText(MyEnum.TRANSLATE_LANGUAGE.getNotice(this.mMySideLang));
        this.mBinding.titleView.setOnBackListener(new View.OnClickListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m74xc2eb0551(view2);
            }
        });
        this.mBinding.titleView.setRightSwitchListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda14
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m75xdd0683f0(compoundButton, z);
            }
        });
        this.mBinding.titleView.setRightIconListener(new View.OnClickListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m76xf722028f(view2);
            }
        });
        this.mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        this.transcribeAdapter = new TranscribeConversationAdapter(this);
        this.mBinding.recyclerview.setAdapter(this.transcribeAdapter);
        this.transcribeAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda16
            @Override // com.aivox.base.databinding.OnItemClickListener
            public final void onItemClick(View view2, int i) {
                this.f$0.m77x113d812e(view2, i);
            }
        });
        this.mBinding.setIsFaceMode(Boolean.valueOf(this.isFaceMode));
        this.mBinding.rvBtm.setLayoutManager(new LinearLayoutManager(this));
        this.mBinding.rvTop.setLayoutManager(new LinearLayoutManager(this));
        this.mBtmAdapter = new TranscribeConversationTextAdapter(C0726R.layout.item_conversation_text, true);
        this.mTopAdapter = new TranscribeConversationTextAdapter(C0726R.layout.item_conversation_text, false);
        this.mBtmAdapter.bindToRecyclerView(this.mBinding.rvBtm);
        this.mTopAdapter.bindToRecyclerView(this.mBinding.rvTop);
        this.mBtmAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda17
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m78x2b58ffcd(baseQuickAdapter, view2, i);
            }
        });
        this.mTopAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda18
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m79x45747e6c(baseQuickAdapter, view2, i);
            }
        });
        this.mBinding.rvBtm.setOnTouchListener(new View.OnTouchListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda19
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f$0.m80x5f8ffd0b(view2, motionEvent);
            }
        });
        this.mBinding.rvTop.setOnTouchListener(new View.OnTouchListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda20
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f$0.m81x79ab7baa(view2, motionEvent);
            }
        });
        this.mBinding.recyclerview.addOnScrollListener(this.rcvOnScrollListener);
        if (this.recordStatus == 0) {
            this.mHandler = new RecordingHandler();
        }
        toOpenTheRecordPermission();
        DataHandle.getIns().setTranscribeWsCanClosed(false);
        this.mBinding.ivMicLeft.setOnTouchChangeListener(this.onTouchChangeListener);
        this.mBinding.ivMicRight.setOnTouchChangeListener(this.onTouchChangeListener);
        this.mBinding.rlBottom.setOnTouchListener(new View.OnTouchListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f$0.m72x1b229a20(view2, motionEvent);
            }
        });
        this.mBinding.rlTop.setOnTouchListener(new View.OnTouchListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f$0.m73x353e18bf(view2, motionEvent);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m71xa8cf86b2(final int i) {
        runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m70x8eb40813(i);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m70x8eb40813(int i) {
        if (i == 0) {
            if (!this.isFaceMode) {
                this.transcribeAdapter.startTtsAnim(this.mCurSpeakingIndex);
                return;
            } else {
                this.mTopAdapter.startTtsAnim(this.mCurSpeakingIndex);
                this.mBtmAdapter.startTtsAnim(this.mCurSpeakingIndex);
                return;
            }
        }
        if (i == 1 || i == 2) {
            this.mTopAdapter.stopTtsAnim();
            this.mBtmAdapter.stopTtsAnim();
            this.transcribeAdapter.stopTtsAnim();
        }
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m74xc2eb0551(View view2) {
        doBack();
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m75xdd0683f0(CompoundButton compoundButton, boolean z) {
        this.mAutoSpeakEnable = z;
        this.transcribeAdapter.setAutoSpeakMode(z);
        this.mBtmAdapter.setAutoSpeakMode(this.mAutoSpeakEnable);
        this.mTopAdapter.setAutoSpeakMode(this.mAutoSpeakEnable);
        this.mTtsManager.stopAll();
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m76xf722028f(View view2) {
        showModeSwitchPopup();
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m77x113d812e(View view2, int i) {
        if (this.isRecording) {
            return;
        }
        this.mCurSpeakingIndex = i;
        Transcribe transcribe = this.transcribeList.get(i);
        this.mTtsManager.speak(transcribe.getTranslate(), transcribe.isConversationLeft() ? this.mOtherSideLang : this.mMySideLang, false);
    }

    /* JADX INFO: renamed from: lambda$initView$6$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m78x2b58ffcd(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        if (this.isRecording) {
            return;
        }
        this.mCurSpeakingIndex = i;
        this.mTtsManager.speak(this.transcribeList.get(i).getTranslate(), this.mMySideLang, false);
    }

    /* JADX INFO: renamed from: lambda$initView$7$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m79x45747e6c(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        if (this.isRecording) {
            return;
        }
        this.mCurSpeakingIndex = i;
        this.mTtsManager.speak(this.transcribeList.get(i).getTranslate(), this.mOtherSideLang, false);
    }

    /* JADX INFO: renamed from: lambda$initView$8$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ boolean m80x5f8ffd0b(View view2, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 1) {
            return false;
        }
        startTimeCount();
        return false;
    }

    /* JADX INFO: renamed from: lambda$initView$9$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ boolean m81x79ab7baa(View view2, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 1) {
            return false;
        }
        startTimeCount();
        return false;
    }

    /* JADX INFO: renamed from: lambda$initView$10$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ boolean m72x1b229a20(View view2, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            if (this.isRecording) {
                return true;
            }
            this.mTtsManager.stopAll();
            this.isRecordBtnPressed = true;
            startTrans(true);
            this.downTime = System.currentTimeMillis();
            VibrateHelp.vSimple(view2.getContext(), 60);
        } else {
            if (motionEvent.getAction() != 1 || this.isRightSpeaking) {
                return true;
            }
            this.isRecordBtnPressed = false;
            this.isRecording = false;
            if (System.currentTimeMillis() - this.downTime < 200) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.hint_speech_too_short));
            }
            pauseAll();
        }
        return true;
    }

    /* JADX INFO: renamed from: lambda$initView$11$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ boolean m73x353e18bf(View view2, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            if (this.isRecording) {
                return true;
            }
            this.mTtsManager.stopAll();
            this.isRecordBtnPressed = true;
            startTrans(false);
            this.downTime = System.currentTimeMillis();
            VibrateHelp.vSimple(view2.getContext(), 60);
        } else {
            if (motionEvent.getAction() != 1 || this.isLeftSpeaking) {
                return true;
            }
            this.isRecordBtnPressed = false;
            this.isRecording = false;
            if (System.currentTimeMillis() - this.downTime < 200) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.hint_speech_too_short));
            }
            pauseAll();
        }
        return true;
    }

    private void showModeSwitchPopup() {
        View viewInflate = LayoutInflater.from(this.context).inflate(C0726R.layout.mode_switch_popup_layout, (ViewGroup) null);
        final PopupWindow popupWindow = new PopupWindow(viewInflate, -2, -2);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        TextView textView = (TextView) viewInflate.findViewById(C0726R.id.tv_face);
        TextView textView2 = (TextView) viewInflate.findViewById(C0726R.id.tv_side);
        TextView textView3 = (TextView) viewInflate.findViewById(C0726R.id.tv_history);
        Drawable drawable = AppCompatResources.getDrawable(this, this.isFaceMode ? C1034R.drawable.ic_talk_mode_face_highlight : C1034R.drawable.ic_talk_mode_face_normal);
        Drawable drawable2 = AppCompatResources.getDrawable(this, this.isFaceMode ? C1034R.drawable.ic_talk_mode_side_normal : C1034R.drawable.ic_talk_mode_side_highlight);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable, (Drawable) null);
        textView2.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable2, (Drawable) null);
        textView.setTextColor(ColorUtils.getColor(this.isFaceMode ? C0874R.color.txt_color_highlight : C0874R.color.txt_color_primary));
        textView2.setTextColor(ColorUtils.getColor(this.isFaceMode ? C0874R.color.txt_color_primary : C0874R.color.txt_color_highlight));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m84xee113b67(popupWindow, view2);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m85x82cba06(popupWindow, view2);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m86x224838a5(popupWindow, view2);
            }
        });
        popupWindow.showAsDropDown(this.mBinding.titleView, 0, 0, GravityCompat.END);
    }

    /* JADX INFO: renamed from: lambda$showModeSwitchPopup$12$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m84xee113b67(PopupWindow popupWindow, View view2) {
        this.isFaceMode = true;
        this.mBinding.setIsFaceMode(true);
        refreshList(false);
        boolean z = this.isLeftSpeaking;
        boolean z2 = this.isRightSpeaking;
        int i = this.leftTime;
        refreshMicStatus(z, z2, i < 15, 30 - i);
        popupWindow.dismiss();
    }

    /* JADX INFO: renamed from: lambda$showModeSwitchPopup$13$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m85x82cba06(PopupWindow popupWindow, View view2) {
        this.isFaceMode = false;
        this.mBinding.setIsFaceMode(false);
        refreshList(false);
        boolean z = this.isLeftSpeaking;
        boolean z2 = this.isRightSpeaking;
        int i = this.leftTime;
        refreshMicStatus(z, z2, i < 15, 30 - i);
        popupWindow.dismiss();
    }

    /* JADX INFO: renamed from: lambda$showModeSwitchPopup$14$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m86x224838a5(PopupWindow popupWindow, View view2) {
        ARouterUtils.startWithActivity(this, RecordAction.CONVERSATION_HISTORY);
        popupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.aivox.app.activity.ConversationRecordingActivity$4] */
    public void startTimeCount() {
        this.countDownTimer = new CountDownTimer(5000L, 500L) { // from class: com.aivox.app.activity.ConversationRecordingActivity.4
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                ConversationRecordingActivity.this.autoScroll = false;
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                ConversationRecordingActivity.this.autoScroll = true;
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshList(boolean z) {
        if (!DataHandle.getIns().isVip() && this.transcribeList.size() > 0) {
            int size = this.mLastCount + this.transcribeList.size();
            this.mCurCount = size;
            SPUtil.putWithUid(SPUtil.CONVERSATION_COUNT, Integer.valueOf(size));
        }
        if (!this.transcribeList.isEmpty()) {
            this.mBinding.llNoti.setVisibility(8);
            this.mBinding.tvNoticeTop.setVisibility(8);
            this.mBinding.tvNoticeBtm.setVisibility(8);
            if (z) {
                ConversationEntity conversationEntity = new ConversationEntity();
                conversationEntity.setContent(this.transcribeList.get(r0.size() - 1).getOnebest());
                conversationEntity.setTranslation(this.transcribeList.get(r0.size() - 1).getTranslate());
                conversationEntity.setMySide(this.transcribeList.get(r0.size() - 1).isConversationLeft());
                conversationEntity.setUid(this.mUid);
                this.mManager.insertOrReplace(conversationEntity);
            }
        }
        if (isActivityShow(ConversationRecordingActivity.class)) {
            if (this.isFaceMode) {
                updateBtmListData();
                return;
            }
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

    private void updateBtmListData() {
        this.mBtmAdapter.setNewData(this.transcribeList);
        this.mTopAdapter.setNewData(this.transcribeList);
        if (this.autoScroll) {
            this.mBinding.rvBtm.scrollToPosition(this.transcribeList.size() - 1);
            this.mBinding.rvTop.scrollToPosition(this.transcribeList.size() - 1);
        }
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        if (AntiShake.check(view2)) {
            return;
        }
        int id = view2.getId();
        if (id == this.mBinding.tvLangMySideS2s.getId() || id == this.mBinding.tvLangMySideF2f.getId()) {
            selectSideLang(true);
            return;
        }
        if (id == this.mBinding.tvLangOtherSideS2s.getId() || id == this.mBinding.tvLangOtherSideF2f.getId()) {
            selectSideLang(false);
        } else if (id == this.mBinding.ivSwitchLangS2s.getId() || id == this.mBinding.ivSwitchLangF2f.getId()) {
            switchLang();
        }
    }

    private class RecordingHandler extends Handler {
        private RecordingHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 1001) {
                return;
            }
            if (ConversationRecordingActivity.this.isRecording) {
                ConversationRecordingActivity.this.leftTime--;
                if (ConversationRecordingActivity.this.leftTime < 10) {
                    ConversationRecordingActivity conversationRecordingActivity = ConversationRecordingActivity.this;
                    conversationRecordingActivity.refreshMicStatus(conversationRecordingActivity.isLeftSpeaking, ConversationRecordingActivity.this.isRightSpeaking, true, ConversationRecordingActivity.this.leftTime);
                }
                if (ConversationRecordingActivity.this.leftTime <= 0) {
                    ConversationRecordingActivity.this.pauseAll();
                }
            }
            ConversationRecordingActivity.this.mHandler.sendEmptyMessageDelayed(1001, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTrans(boolean z) {
        if (this.mMySideLang == this.mOtherSideLang) {
            DialogUtils.showDialogWithDefBtn(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.same_lang_notice), null, null, true, true);
            return;
        }
        if (!DataHandle.getIns().isVip()) {
            int i = this.mCurCount;
            int i2 = this.mMaxCount;
            if (i < i2 && this.mLastCount < i2) {
                this.sessionStartTime = System.currentTimeMillis();
                this.mBinding.groupLangSetS2s.setVisibility(8);
                this.mBinding.llLangSetF2f.setVisibility(8);
                this.mBinding.llWave.setVisibility(0);
                this.mBinding.llLangSelected.setVisibility(0);
                if (z) {
                    startLeft();
                    return;
                } else {
                    startRight();
                    return;
                }
            }
            AppUtils.showVipSubDialog(this);
            return;
        }
        this.sessionStartTime = System.currentTimeMillis();
        this.mBinding.groupLangSetS2s.setVisibility(8);
        this.mBinding.llLangSetF2f.setVisibility(8);
        this.mBinding.llWave.setVisibility(0);
        this.mBinding.llLangSelected.setVisibility(0);
        if (z) {
            startLeft();
        } else {
            startRight();
        }
    }

    private void startLeft() {
        if (this.isRecordBtnPressed) {
            this.isRecording = true;
            this.mBinding.ivMicLeft.setClickable(false);
            this.mBinding.ivMicRight.setClickable(false);
            this.leftTime = 30;
            refreshMicStatus(true, false, false, 0);
            CommonTransManager commonTransManager = this.rightTransManager;
            if (commonTransManager != null && commonTransManager.getTranStatus() == MyEnum.TRANS_MODEL_STATUS.TRANSCRIBING.type) {
                this.rightTransManager.pauseAudio();
            }
            CommonTransManager commonTransManager2 = this.leftTransManager;
            if (commonTransManager2 != null) {
                if (commonTransManager2.getTranStatus() == MyEnum.TRANS_MODEL_STATUS.NOT_START.type) {
                    this.leftTransManager.startRecording(this.mRecorder);
                    this.leftTransManager.startTrans(false, this.mMySideLang, this.debugPath, this.transCallback);
                } else {
                    this.leftTransManager.resumeAudio();
                }
            }
            new Handler().postDelayed(new Runnable() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m87x57f46015();
                }
            }, 10L);
        }
    }

    /* JADX INFO: renamed from: lambda$startLeft$15$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m87x57f46015() {
        this.curIsLeft = true;
        this.curIndex++;
        this.isLeftSpeaking = true;
        this.isRightSpeaking = false;
        this.mBinding.ivMicLeft.setClickable(true);
        this.mBinding.ivMicRight.setClickable(true);
    }

    private void startRight() {
        if (this.isRecordBtnPressed) {
            this.isRecording = true;
            this.mBinding.ivMicRight.setClickable(false);
            this.mBinding.ivMicLeft.setClickable(false);
            this.leftTime = 30;
            refreshMicStatus(false, true, false, 0);
            CommonTransManager commonTransManager = this.leftTransManager;
            if (commonTransManager != null && commonTransManager.getTranStatus() == MyEnum.TRANS_MODEL_STATUS.TRANSCRIBING.type) {
                this.leftTransManager.pauseAudio();
            }
            CommonTransManager commonTransManager2 = this.rightTransManager;
            if (commonTransManager2 != null) {
                if (commonTransManager2.getTranStatus() == MyEnum.TRANS_MODEL_STATUS.NOT_START.type) {
                    this.rightTransManager.startRecording(this.mRecorder2);
                    this.rightTransManager.startTrans(false, this.mOtherSideLang, this.debugPath, this.transCallback);
                } else {
                    this.rightTransManager.resumeAudio();
                }
            }
            new Handler().postDelayed(new Runnable() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m88xe2d6d29f();
                }
            }, 10L);
        }
    }

    /* JADX INFO: renamed from: lambda$startRight$16$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m88xe2d6d29f() {
        this.curIsLeft = false;
        this.curIndex++;
        this.isRightSpeaking = true;
        this.isLeftSpeaking = false;
        this.mBinding.ivMicLeft.setClickable(true);
        this.mBinding.ivMicRight.setClickable(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshMicStatus(boolean z, boolean z2, boolean z3, int i) {
        AnimationDrawable animationDrawable = (AnimationDrawable) this.mBinding.ivWaveTop.getBackground();
        AnimationDrawable animationDrawable2 = (AnimationDrawable) this.mBinding.ivWaveBottom.getBackground();
        if (this.isFaceMode) {
            this.mBinding.ivWaveTop.setVisibility(z2 ? 0 : 8);
            this.mBinding.ivMicTopStart.setVisibility(z2 ? 8 : 0);
            this.mBinding.ivWaveBottom.setVisibility(z ? 0 : 8);
            this.mBinding.ivMicBottomStart.setVisibility(z ? 8 : 0);
            if (z) {
                animationDrawable.stop();
                animationDrawable2.start();
            } else {
                animationDrawable2.stop();
            }
            if (z2) {
                animationDrawable.start();
                animationDrawable2.stop();
                return;
            } else {
                animationDrawable.stop();
                return;
            }
        }
        this.mBinding.ivMicLeft.refresh(z, z3, i);
        this.mBinding.ivMicRight.refresh(z2, z3, i);
        if (z || z2) {
            return;
        }
        this.mBinding.ivWave.setBackgroundResource(C1034R.drawable.ic_audio_wave_frame_0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pauseAll() {
        CommonTransManager commonTransManager = this.leftTransManager;
        if (commonTransManager != null && commonTransManager.getTranStatus() == MyEnum.TRANS_MODEL_STATUS.TRANSCRIBING.type) {
            this.leftTransManager.pauseAudio();
        }
        CommonTransManager commonTransManager2 = this.rightTransManager;
        if (commonTransManager2 != null && commonTransManager2.getTranStatus() == MyEnum.TRANS_MODEL_STATUS.TRANSCRIBING.type) {
            this.rightTransManager.pauseAudio();
        }
        this.isLeftSpeaking = false;
        this.isRightSpeaking = false;
        this.isRecording = false;
        refreshMicStatus(false, false, false, 0);
    }

    private boolean isSupportToSpeechLanguage(int i, int i2) {
        return (i == MyEnum.TRANSLATE_LANGUAGE.ZH.type || i == MyEnum.TRANSLATE_LANGUAGE.EN.type || i == MyEnum.TRANSLATE_LANGUAGE.JA.type) && (i2 == MyEnum.TRANSLATE_LANGUAGE.ZH.type || i2 == MyEnum.TRANSLATE_LANGUAGE.EN.type || i2 == MyEnum.TRANSLATE_LANGUAGE.JA.type);
    }

    private void stopAll() {
        this.mTtsManager.stopAll();
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
        PermissionUtils.getIns(this, new C07315()).request("android.permission.RECORD_AUDIO");
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.ConversationRecordingActivity$5 */
    class C07315 implements PermissionCallback {
        C07315() {
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void granted(boolean z) {
            if (z) {
                LogUtil.m338i("已获得录音权限");
                ConversationRecordingActivity.this.getTransKeys();
            } else {
                DialogUtils.showDialogWithBtnIds(ConversationRecordingActivity.this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.no_microphone_permissions), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$5$$ExternalSyntheticLambda0
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        this.f$0.m93x46a64298(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$5$$ExternalSyntheticLambda1
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        BaseAppUtils.openSettingView(context);
                    }
                }, true, false, C0874R.string.cancel, C0874R.string.sure);
            }
        }

        /* JADX INFO: renamed from: lambda$granted$0$com-aivox-app-activity-ConversationRecordingActivity$5 */
        /* synthetic */ void m93x46a64298(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            ConversationRecordingActivity.this.finish();
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void requestError(Throwable th) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.no_microphone_permissions));
            BaseAppUtils.openSettingView(ConversationRecordingActivity.this.context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getTransKeys() {
        DialogUtils.showLoadingDialog(this.context);
        int i = this.mMySideLang;
        if (i == 99) {
            i = 9;
        }
        int i2 = this.mOtherSideLang;
        new AudioService(this).getTransKeys(i, i2 != 99 ? i2 : 9).subscribe(new Consumer() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m68xdfa2492a((TransKeyBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m69xf9bdc7c9((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getTransKeys$17$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m68xdfa2492a(TransKeyBean transKeyBean) throws Exception {
        DialogUtils.hideLoadingDialog();
        String strDecrypt = SerAESUtil.decrypt(transKeyBean.getFrom(), Constant.DECRYPT_KEY);
        String strDecrypt2 = SerAESUtil.decrypt(transKeyBean.getTo(), Constant.DECRYPT_KEY);
        if (!BaseStringUtil.isEmpty(strDecrypt)) {
            JSONObject jSONObject = new JSONObject(strDecrypt);
            if (jSONObject.has("appKey")) {
                SPUtil.put(SPUtil.ALI_TRANS_KEY + this.mMySideLang, jSONObject.getString("appKey"));
                SPUtil.put(SPUtil.ALI_TRANS_TOKEN + this.mMySideLang, jSONObject.getString(SPUtil.TOKEN));
            } else {
                SPUtil.put(SPUtil.TENCENT_TRANS_SK, jSONObject.getString("secretKey"));
                SPUtil.put(SPUtil.TENCENT_TRANS_SI, jSONObject.getString("secretId"));
                SPUtil.put(SPUtil.TENCENT_TRANS_ID, jSONObject.getString("appId"));
            }
        }
        if (!BaseStringUtil.isEmpty(strDecrypt2)) {
            JSONObject jSONObject2 = new JSONObject(strDecrypt2);
            if (jSONObject2.has("appKey")) {
                SPUtil.put(SPUtil.ALI_TRANS_KEY + this.mOtherSideLang, jSONObject2.getString("appKey"));
                SPUtil.put(SPUtil.ALI_TRANS_TOKEN + this.mOtherSideLang, jSONObject2.getString(SPUtil.TOKEN));
            } else {
                SPUtil.put(SPUtil.TENCENT_TRANS_SK, jSONObject2.getString("secretKey"));
                SPUtil.put(SPUtil.TENCENT_TRANS_SI, jSONObject2.getString("secretId"));
                SPUtil.put(SPUtil.TENCENT_TRANS_ID, jSONObject2.getString("appId"));
            }
        }
        startTrans();
    }

    /* JADX INFO: renamed from: lambda$getTransKeys$18$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m69xf9bdc7c9(Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        AppUtils.checkHttpCode(this.context);
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
                this.mHandler.removeMessages(1001);
                this.mHandler.sendEmptyMessage(1001);
                this.mFirst = false;
            }
        } catch (Exception e) {
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_METHOD, "recordStartError" + e.toString());
            LogUtil.m336e("录音异常：" + e.getLocalizedMessage());
            recordError();
            DialogUtils.showDialogWithDefBtnAndSingleListener(this, Integer.valueOf(C0874R.string.reminder), e.toString(), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda6
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m82x373030d9(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, false, false);
        }
    }

    /* JADX INFO: renamed from: lambda$recordStart$19$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m82x373030d9(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        doBack();
    }

    public void recordError() {
        LogUtil.m336e("--recordError--");
        setRecordStatus(0);
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void onEventMainThread(EventBean eventBean) {
        int from = eventBean.getFrom();
        if (from == 46) {
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "networkReconnect");
            this.isNoNet = false;
            refreshNoti();
        } else {
            if (from != 59) {
                return;
            }
            SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_MSG, "networkDisconnect");
            if (NetUtil.isConnected(this)) {
                return;
            }
            ToastUtil.showShort(Integer.valueOf(C0874R.string.trans_error_40089));
        }
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
        this.mHandler.removeCallbacksAndMessages(null);
        finishAndRemoveTask();
        finish();
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
        LogUtil.m338i("Recording_onResume--" + this.bundle);
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "ConversationRecordingActivity:onResume");
        BarUtils.setNavBarColor(this, getColor(C0874R.color.bg_color_popup_bar));
        super.onResume();
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
        BarUtils.setNavBarColor(this, getColor(C0874R.color.bg_color_secondary));
        super.onStop();
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        SaveLogHelper.getIns().writeLog(SaveLogHelper.LOG_TAG_LIFECYCLE, "ConversationRecordingActivity:onDestroy");
        super.onDestroy();
        SeqTransModel.getInstance().destroy();
        FileUtils.deleteFile(this.filePath);
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        SPUtil.put(SPUtil.RECORD_STATE, 0);
        this.mHandler.removeCallbacksAndMessages(null);
        this.transcribeList.clear();
        this.mDis.clear();
        this.mTtsManager.releaseAll();
    }

    private void refreshNoti() {
        if (this.showBreakNoti) {
            return;
        }
        if (this.isNoNet) {
            this.mBinding.llNoti.setVisibility(0);
            this.mBinding.llNoti.setBackgroundColor(getResources().getColor(C0874R.color.red3));
            this.mBinding.tvNoti.setText(getResources().getString(C0874R.string.weak_net_recording_tip));
            this.mBinding.tvNoti.setTextColor(getResources().getColor(C0874R.color.white));
            this.mBinding.llNoti.setOnClickListener(null);
            return;
        }
        this.mBinding.llNoti.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshNoti(int i, String str, View.OnClickListener onClickListener) {
        this.showBreakNoti = i == 0;
        if (i == 0) {
            return;
        }
        this.content = new SpannableString(str);
        this.mBinding.llNoti.setVisibility(i);
        this.mBinding.llNoti.setBackgroundColor(Color.parseColor("#FFECD5"));
        this.mBinding.tvNoti.setText(this.content);
        this.mBinding.tvNoti.setTextColor(getResources().getColor(C0874R.color.black_11));
        this.mBinding.llNoti.setOnClickListener(onClickListener);
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

    private void parseTranscribeResult(TextView textView, boolean z) {
        SpanUtils spanUtils = new SpanUtils();
        this.spanUtils = spanUtils;
        spanUtils.append("\n\n\n\n");
        for (Transcribe transcribe : this.transcribeList) {
            this.spanUtils.append(z == transcribe.isConversationLeft() ? transcribe.getOnebest() + transcribe.getVar() : transcribe.getTranslate());
            this.spanUtils.append("\n\n");
        }
        textView.setText(this.spanUtils.create());
        int lineCount = textView.getLineCount() * textView.getLineHeight();
        this.offset = lineCount;
        if (lineCount <= textView.getHeight() || !this.autoScroll) {
            return;
        }
        textView.scrollTo(0, this.offset - textView.getHeight());
    }

    private void selectSideLang(final boolean z) {
        LanguageUtils.showLangSelectView(this.context, z, new LanguageSelectView.LangSelectListener() { // from class: com.aivox.app.activity.ConversationRecordingActivity$$ExternalSyntheticLambda9
            @Override // com.aivox.common.view.LanguageSelectView.LangSelectListener
            public final void onLangSelected(MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
                this.f$0.m83xf7eb0acd(z, translate_language, translate_language2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$selectSideLang$20$com-aivox-app-activity-ConversationRecordingActivity */
    /* synthetic */ void m83xf7eb0acd(boolean z, MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
        if (z) {
            this.mMySideLang = translate_language.type;
        } else {
            this.mOtherSideLang = translate_language.type;
        }
        doUiUpdateAndInit();
    }

    private void switchLang() {
        int i = this.mMySideLang;
        this.mMySideLang = this.mOtherSideLang;
        this.mOtherSideLang = i;
        doUiUpdateAndInit();
    }

    private void doUiUpdateAndInit() {
        this.mBinding.tvLangMySideS2s.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mMySideLang).display);
        this.mBinding.tvLangOtherSideS2s.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mOtherSideLang).display);
        this.mBinding.tvLangMySideF2f.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mMySideLang).display);
        this.mBinding.tvLangOtherSideF2f.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mOtherSideLang).display);
        this.mBinding.tvLanguageFrom.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mMySideLang).display);
        this.mBinding.tvLanguageTo.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mOtherSideLang).display);
        this.mBinding.tvLanguageFromFace.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mMySideLang).display);
        this.mBinding.tvLanguageToFace.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(this.mOtherSideLang).display);
        this.mBinding.tvNoticeTop.setText(MyEnum.TRANSLATE_LANGUAGE.getNotice(this.mOtherSideLang));
        this.mBinding.tvNoticeBtm.setText(MyEnum.TRANSLATE_LANGUAGE.getNotice(this.mMySideLang));
        this.mBinding.tvNoticeTop.setVisibility(0);
        this.mBinding.tvNoticeBtm.setVisibility(0);
        this.isRecordInit = false;
        getTransKeys();
        this.mTranscribe = new Transcribe();
        this.transcribeList.clear();
        refreshList(false);
        LanguageUtils.setDefaultLang(this.context, 3, this.mMySideLang, this.mOtherSideLang);
    }
}
