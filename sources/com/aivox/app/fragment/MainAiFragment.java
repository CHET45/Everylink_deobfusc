package com.aivox.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.HomeAiChatAdapter;
import com.aivox.app.databinding.FragmentMainAiBinding;
import com.aivox.app.util.CalendarHelper;
import com.aivox.app.util.EmojiFilter;
import com.aivox.app.util.TencentAsrManager;
import com.aivox.app.util.TencentTtsManager;
import com.aivox.app.util.agent.BaseChatManager;
import com.aivox.app.util.agent.ChatCallback;
import com.aivox.app.util.agent.ChatEngineType;
import com.aivox.app.util.agent.ChatManagerFactory;
import com.aivox.app.util.agent.CozeManager;
import com.aivox.app.util.agent.GlassDelegate;
import com.aivox.app.util.agent.N8nManager;
import com.aivox.base.C0874R;
import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.common.Constant;
import com.aivox.base.common.GlassesCmd;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.language.MultiLanguageUtil;
import com.aivox.base.permission.PermissionCallback;
import com.aivox.base.permission.PermissionUtils;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ThreadPoolManager;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.C0958R;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseBindingFragment;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.p003db.entity.HomeAiChatEntity;
import com.aivox.common.p003db.maneger.HomeAiChatDbManager;
import com.aivox.common.util.GlideEngine;
import com.aivox.common.util.MicrosoftTtsManager;
import com.aivox.common.util.tts.TtsManager;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.antishake.AntiShake;
import com.alibaba.android.arouter.utils.TextUtils;
import com.azure.core.util.polling.implementation.PollingConstants;
import com.blankj.utilcode.util.KeyboardUtils;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.github.houbb.opencc4j.util.ZhTwConverterUtil;
import com.hjq.permissions.Permission;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import io.noties.markwon.Markwon;
import io.noties.markwon.SoftBreakAddsNewLinePlugin;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.greenrobot.eventbus.EventBus;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;

/* JADX INFO: loaded from: classes.dex */
public class MainAiFragment extends BaseBindingFragment implements OnViewClickListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ASR_AUTO_STOP_MS = 10000;
    private static final long LOCATION_TIMEOUT_MS = 15000;
    public static final int STOP_STATUS_COMPLETE = 0;
    public static final int STOP_STATUS_INTERRUPT = 1;
    public static final int STOP_STATUS_SHUTDOWN = 2;
    private HomeAiChatAdapter aiChatAdapter;
    private View anchorView;
    private TencentAsrManager asrManager;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    private AudioFocusRequest audioFocusRequest;
    private AudioManager audioManager;
    private BaseChatManager<GlassDelegate> chatManager;
    private boolean couldBreak;
    private Disposable disposable;
    private String globalLanguage;
    private HomeAiChatDbManager homeAiChatDbManager;
    private String imgData;
    private String imgPath;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private GlassDelegate.ImageCallback mActiveImageCallback;
    private FragmentMainAiBinding mBinding;
    private SoundPool mSoundPool;
    private SQLiteDataBaseManager manager;
    private PopupWindow pwModel;
    private String regenerateQuestion;
    private String streamResult;
    private TtsManager ttsManager;
    private String uid;
    private final String TAG = "MainAiFragment";
    private final CompositeDisposable mDis = new CompositeDisposable();
    private final List<HomeAiChatEntity> chatList = new ArrayList();
    private boolean isStop = true;
    private boolean isFirstLoad = true;
    private boolean isLoadText = false;
    private boolean blockAi = false;
    private boolean isAiTrigger = false;
    private boolean currentPage = true;
    private int zhType = 0;
    private int loadCount = 2;
    private int page = 1;
    private int streamIndex = 0;
    private final AtomicInteger mRequestCounter = new AtomicInteger(0);
    private final PublishSubject<Bitmap> bitmapSub = PublishSubject.create();
    private boolean isRegenerate = false;
    private boolean isTtsStart = false;
    private boolean mRunnableInTask = false;
    private final AtomicBoolean mIsTtsSpeaking = new AtomicBoolean(false);
    private int mTtsStopStatus = 0;
    private final Runnable mStopAsrRunnable = new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda30
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.stopRecognizeAndQuitAiMode();
        }
    };
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final HomeAiChatEntity mTempChatEntity = new HomeAiChatEntity();
    private final AtomicLong mCurrentSessionId = new AtomicLong(0);
    private final TencentAsrManager.AsrListener asrListener = new C08121();
    private int mWaitingSoundId = -1;
    private int mStreamId = -1;
    private final Runnable mPlayWaitingSoundRunnable = new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.playWaitingSound();
        }
    };
    private boolean isWaitingSoundPlaying = false;

    /* JADX INFO: renamed from: com.aivox.app.fragment.MainAiFragment$1 */
    class C08121 implements TencentAsrManager.AsrListener {
        C08121() {
        }

        @Override // com.aivox.app.util.TencentAsrManager.AsrListener
        public void onSliceSuccess(final String str) {
            if (MainAiFragment.this.requireActivity().isFinishing() || !MainAiFragment.this.isStop || TextUtils.isEmpty(str) || str.equals(MainAiFragment.this.mTempChatEntity.getQuestion())) {
                return;
            }
            MainAiFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2326lambda$onSliceSuccess$0$comaivoxappfragmentMainAiFragment$1(str);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSliceSuccess$0$com-aivox-app-fragment-MainAiFragment$1, reason: not valid java name */
        /* synthetic */ void m2326lambda$onSliceSuccess$0$comaivoxappfragmentMainAiFragment$1(String str) {
            MainAiFragment.this.forceCompleteLastMessage();
            MainAiFragment.this.showEmpty(false);
            MainAiFragment.this.mBinding.etQuestion.setText(MainAiFragment.this.convertChinese(str));
            if (MainAiFragment.this.mRunnableInTask) {
                MainAiFragment.this.mRunnableInTask = false;
                MainAiFragment.this.mHandler.removeCallbacks(MainAiFragment.this.mStopAsrRunnable);
            }
            if (MainAiFragment.this.mIsTtsSpeaking.get()) {
                MainAiFragment.this.stopTtsWithStatus(1);
            }
            MainAiFragment mainAiFragment = MainAiFragment.this;
            mainAiFragment.addQuestionToChatList(mainAiFragment.mBinding.etQuestion.getText().toString(), false, true);
        }

        @Override // com.aivox.app.util.TencentAsrManager.AsrListener
        public void onSegmentSuccess(final String str) {
            if (MainAiFragment.this.requireActivity().isFinishing() || !MainAiFragment.this.isStop) {
                return;
            }
            MainAiFragment.this.isAiTrigger = true;
            MainAiFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m272x73ab9c23(str);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSegmentSuccess$2$com-aivox-app-fragment-MainAiFragment$1 */
        /* synthetic */ void m272x73ab9c23(String str) {
            MainAiFragment.this.forceCompleteLastMessage();
            MainAiFragment.this.mBinding.etQuestion.setText(MainAiFragment.this.convertChinese(str));
            ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m271xe70b7122();
                }
            });
            KeyboardUtils.hideSoftInput(MainAiFragment.this.mBinding.etQuestion);
        }

        /* JADX INFO: renamed from: lambda$onSegmentSuccess$1$com-aivox-app-fragment-MainAiFragment$1 */
        /* synthetic */ void m271xe70b7122() {
            MainAiFragment.this.startChat();
        }

        @Override // com.aivox.app.util.TencentAsrManager.AsrListener
        public void onStateChanged(boolean z) {
            if (z) {
                return;
            }
            MainAiFragment mainAiFragment = MainAiFragment.this;
            mainAiFragment.showEmpty(mainAiFragment.chatList.isEmpty());
        }

        @Override // com.aivox.app.util.TencentAsrManager.AsrListener
        public void onError(String str) {
            LogUtil.m335d(MainAiFragment.this.TAG, "ASR Error:" + str);
            MainAiFragment.this.stopRecognizeAndQuitAiMode();
        }
    }

    public static MainAiFragment newInstance() {
        return new MainAiFragment();
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void onEventMainThread(final EventBean eventBean) {
        super.onEventMainThread(eventBean);
        if (eventBean.getT() instanceof GlassesCmd) {
            int i = C08209.$SwitchMap$com$aivox$base$common$GlassesCmd[((GlassesCmd) eventBean.getT()).ordinal()];
            if (i == 1 || i == 2) {
                LogUtil.m335d(this.TAG, "AI语音触发：" + eventBean.getA());
                if (this.isAiTrigger && !this.couldBreak) {
                    stopWaitingSound();
                    forceCompleteLastMessage();
                    showEmpty(false);
                    if (this.mRunnableInTask) {
                        this.mRunnableInTask = false;
                        this.mHandler.removeCallbacks(this.mStopAsrRunnable);
                    }
                    if (this.mIsTtsSpeaking.get()) {
                        stopTtsWithStatus(1);
                    }
                    this.blockAi = true;
                    BleBtService.getInstance().sendGlassCmd(GlassesCmd.AI_MODE_VOICE_PROMPT, 1);
                    startRecognize();
                    LogUtil.m335d(this.TAG, "handleAiChatEvent startRecognize");
                    postAsrStopRunnable();
                    return;
                }
                if (eventBean.getA() == 0) {
                    exitChat();
                }
                handleAiChatEvent(eventBean.getA());
                return;
            }
            return;
        }
        int from = eventBean.getFrom();
        if (from == 84) {
            this.aiChatAdapter.notifyDataSetChanged();
            return;
        }
        if (from != 308) {
            if (from == 400) {
                LogUtil.m335d(this.TAG, "文件接收完成后询问AI");
                String s1 = eventBean.getS1();
                this.imgPath = s1;
                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(s1);
                if (bitmapDecodeFile != null) {
                    this.bitmapSub.onNext(bitmapDecodeFile);
                } else {
                    LogUtil.m337e(this.TAG, "文件不存在");
                }
                DataHandle.getIns().setShouldHandleRecognition(false);
                return;
            }
            if (from == 414) {
                this.homeAiChatDbManager.delete(this.chatList.get(eventBean.getA()).getId());
                this.chatList.remove(eventBean.getA());
                this.aiChatAdapter.notifyItemRemoved(eventBean.getA());
                showEmpty(this.chatList.isEmpty());
                return;
            }
            if (from == 420) {
                PermissionUtils.getIns(this.mActivity, new PermissionCallback() { // from class: com.aivox.app.fragment.MainAiFragment.2
                    @Override // com.aivox.base.permission.PermissionCallback
                    public void granted(boolean z) {
                        if (z) {
                            new CalendarHelper().addEvent(MainAiFragment.this.requireContext(), eventBean.getS1(), "", Long.valueOf(eventBean.getD()), Long.valueOf(eventBean.getD() + 3600000));
                        } else {
                            ToastUtil.showLong(Integer.valueOf(C0874R.string.calendar_permission_denied_hint));
                        }
                    }

                    @Override // com.aivox.base.permission.PermissionCallback
                    public void requestError(Throwable th) {
                        LogUtil.m336e("permission.e:" + th.getLocalizedMessage());
                        ToastUtil.showLong(Integer.valueOf(C0874R.string.calendar_permission_denied_hint));
                        BaseAppUtils.openSettingView(MainAiFragment.this.mContext);
                    }
                }).request(PermissionUtils.CALENDAR);
                return;
            }
            if (from != 300 && from != 301) {
                if (from == 407) {
                    LogUtil.m335d(this.TAG, "切换页面：" + eventBean.getA());
                    boolean z = eventBean.getA() == 2;
                    this.currentPage = z;
                    if (z) {
                        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.initTencentAsr();
                            }
                        });
                        this.aiChatAdapter.changeLastPosition();
                        return;
                    } else {
                        exitChat();
                        return;
                    }
                }
                if (from == 408) {
                    this.blockAi = eventBean.isTrue();
                    return;
                }
                if (from == 416) {
                    LogUtil.m335d(this.TAG, "开始识别图像 BLE_GLASS_HOME_RECOGNITION");
                    setRecognitionQuestion();
                    startChat();
                    return;
                } else {
                    if (from != 417) {
                        return;
                    }
                    BleBtService.getInstance().sendGlassCmd(GlassesCmd.IMAGE_CAPTURE_START);
                    LogUtil.m335d(this.TAG, "开始识别图像 BLE_GLASS_START_RECOGNITION_TAKE_PHOTO");
                    return;
                }
            }
        }
        if (this.isAiTrigger) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.connection_lost_conversation_stopped));
        }
        LogUtil.m335d(this.TAG, "蓝牙断开连接 stop tts");
        stopTtsWithStatus(2);
    }

    /* JADX INFO: renamed from: com.aivox.app.fragment.MainAiFragment$9 */
    static /* synthetic */ class C08209 {
        static final /* synthetic */ int[] $SwitchMap$com$aivox$base$common$GlassesCmd;

        static {
            int[] iArr = new int[GlassesCmd.values().length];
            $SwitchMap$com$aivox$base$common$GlassesCmd = iArr;
            try {
                iArr[GlassesCmd.AI_MODE_VOICE_EVENT_TRIGGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.DEVICE_AI_MODE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        if (AntiShake.check(this)) {
            return;
        }
        if (view2.getId() == this.mBinding.ivSend.getId()) {
            if (this.isStop) {
                if (TextUtils.isEmpty(this.mBinding.etQuestion.getText())) {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.please_enter_your_question));
                    return;
                } else {
                    ThreadPoolManager.getInstance().execute(new MainAiFragment$$ExternalSyntheticLambda22(this));
                    return;
                }
            }
            return;
        }
        if (view2.getId() == this.mBinding.ivImg.getId()) {
            chooseImage();
            return;
        }
        if (view2.getId() == this.mBinding.ivUpload.getId()) {
            this.imgPath = null;
            this.mBinding.cvUpload.setVisibility(8);
        } else if (view2.getId() == this.mBinding.ivDelete.getId()) {
            if (this.chatList.isEmpty()) {
                return;
            }
            DialogUtils.showDeleteDialog(this.mContext, Integer.valueOf(C0874R.string.gentle_reminder), Integer.valueOf(C0874R.string.confirm_clear_chat_history), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda12
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2321lambda$onViewClick$3$comaivoxappfragmentMainAiFragment(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, null, true, false, C0874R.string.delete, C0874R.string.cancel, 0);
        } else if ((view2.getId() == this.mBinding.tvTitle.getId() || view2.getId() == this.mBinding.ivModel.getId()) && !BaseGlobalConfig.isMainland()) {
            showModelChoose(false);
        }
    }

    /* JADX INFO: renamed from: lambda$onViewClick$0$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2318lambda$onViewClick$0$comaivoxappfragmentMainAiFragment() throws Exception {
        this.homeAiChatDbManager.clear();
    }

    /* JADX INFO: renamed from: lambda$onViewClick$3$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2321lambda$onViewClick$3$comaivoxappfragmentMainAiFragment(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        this.mDis.add(Completable.fromAction(new Action() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda24
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m2318lambda$onViewClick$0$comaivoxappfragmentMainAiFragment();
            }
        }).subscribeOn(Schedulers.m1898io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda25
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m2319lambda$onViewClick$1$comaivoxappfragmentMainAiFragment();
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda26
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2320lambda$onViewClick$2$comaivoxappfragmentMainAiFragment((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$onViewClick$1$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2319lambda$onViewClick$1$comaivoxappfragmentMainAiFragment() throws Exception {
        this.chatList.clear();
        this.aiChatAdapter.notifyDataSetChanged();
        showEmpty(true);
    }

    /* JADX INFO: renamed from: lambda$onViewClick$2$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2320lambda$onViewClick$2$comaivoxappfragmentMainAiFragment(Throwable th) throws Exception {
        LogUtil.m337e(this.TAG, "AI history clear error:" + th.getLocalizedMessage());
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        FragmentMainAiBinding fragmentMainAiBindingInflate = FragmentMainAiBinding.inflate(layoutInflater, viewGroup, false);
        this.mBinding = fragmentMainAiBindingInflate;
        fragmentMainAiBindingInflate.setClickListener(this);
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m266x35d6353b();
            }
        });
        initDb();
        String string = MultiLanguageUtil.getInstance().getLanguageLocale().toString();
        this.globalLanguage = string;
        if (string.contains("zh_TW") || this.globalLanguage.contains("zh_HK")) {
            this.zhType = 1;
        } else if (this.globalLanguage.contains("zh_CN")) {
            this.zhType = 2;
        }
        if (this.audioManager == null) {
            this.audioManager = (AudioManager) requireContext().getSystemService("audio");
        }
        Markwon markwonBuild = Markwon.builder(requireContext()).usePlugin(SoftBreakAddsNewLinePlugin.create()).build();
        this.mBinding.ivModel.setVisibility(BaseGlobalConfig.isMainland() ? 8 : 0);
        this.aiChatAdapter = new HomeAiChatAdapter(requireContext(), C0726R.layout.item_home_ai_chat, markwonBuild);
        if (this.mBinding.rvAi.getItemAnimator() != null) {
            this.mBinding.rvAi.getItemAnimator().setChangeDuration(0L);
            this.mBinding.rvAi.setItemAnimator(null);
        }
        this.mBinding.rvAi.setAdapter(this.aiChatAdapter);
        this.aiChatAdapter.setNewData(this.chatList);
        this.mBinding.etQuestion.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda18
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return this.f$0.m267xfed72c7c(textView, i, keyEvent);
            }
        });
        this.mBinding.etQuestion.addTextChangedListener(new C08143());
        this.couldBreak = ((Boolean) SPUtil.get(SPUtil.GLASS_COULD_BREAK, true)).booleanValue();
        this.mBinding.scBreak.setChecked(this.couldBreak);
        this.mBinding.scBreak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda19
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m268xc7d823bd(compoundButton, z);
            }
        });
        initModelChoose();
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m270x59da123f();
            }
        });
        return this.mBinding.getRoot();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$4$com-aivox-app-fragment-MainAiFragment */
    /* synthetic */ void m266x35d6353b() {
        ChatManagerFactory.getManager(BaseGlobalConfig.isMainland() ? ChatEngineType.COZE : ChatEngineType.N8N);
        if (this.chatManager == null) {
            this.chatManager = ChatManagerFactory.getCurrent();
        }
        initWaitingSoundPool();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$5$com-aivox-app-fragment-MainAiFragment */
    /* synthetic */ boolean m267xfed72c7c(TextView textView, int i, KeyEvent keyEvent) {
        if (!AntiShake.check(this) && i == 4 && this.isStop) {
            if (TextUtils.isEmpty(textView.getText())) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.please_enter_your_question));
                return true;
            }
            ThreadPoolManager.getInstance().execute(new MainAiFragment$$ExternalSyntheticLambda22(this));
            KeyboardUtils.hideSoftInput(this.mBinding.etQuestion);
        }
        return true;
    }

    /* JADX INFO: renamed from: com.aivox.app.fragment.MainAiFragment$3 */
    class C08143 implements TextWatcher {
        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        C08143() {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            MainAiFragment.this.mBinding.etQuestion.post(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2327lambda$onTextChanged$0$comaivoxappfragmentMainAiFragment$3();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onTextChanged$0$com-aivox-app-fragment-MainAiFragment$3, reason: not valid java name */
        /* synthetic */ void m2327lambda$onTextChanged$0$comaivoxappfragmentMainAiFragment$3() {
            MainAiFragment.this.mBinding.etQuestion.setSelection(MainAiFragment.this.mBinding.etQuestion.getText().length());
        }
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$6$com-aivox-app-fragment-MainAiFragment */
    /* synthetic */ void m268xc7d823bd(CompoundButton compoundButton, boolean z) {
        this.couldBreak = z;
        SPUtil.put(SPUtil.GLASS_COULD_BREAK, Boolean.valueOf(z));
        ToastUtil.showShort(Integer.valueOf(this.couldBreak ? C0874R.string.broadcast_interruption_enabled : C0874R.string.broadcast_interruption_disabled));
        if (this.couldBreak && this.mIsTtsSpeaking.get()) {
            startRecognize();
        }
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$8$com-aivox-app-fragment-MainAiFragment */
    /* synthetic */ void m270x59da123f() {
        initTencentAsr();
        initAiAgent();
        loadAiChat();
        requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m269x90d91afe();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$7$com-aivox-app-fragment-MainAiFragment */
    /* synthetic */ void m269x90d91afe() {
        if (DataHandle.getIns().isShouldHandleAiChat()) {
            handleAiChatEvent(1);
            DataHandle.getIns().setShouldHandleAiChat(false);
        } else if (DataHandle.getIns().isShouldHandleRecognition()) {
            LogUtil.m335d(this.TAG, "开始识别图像");
            this.blockAi = true;
            showEmpty(false);
            setRecognitionQuestion();
            addQuestionToChatList(this.mBinding.etQuestion.getText().toString(), false, true);
            DataHandle.getIns().setShouldHandleRecognition(false);
            startChat();
        }
    }

    private void setRecognitionQuestion() {
        this.mBinding.etQuestion.setText(getString(C0874R.string.desc_captured_image));
    }

    private void handleAiChatEvent(int i) {
        if (i != 1) {
            if (i == 0) {
                LogUtil.m335d(this.TAG, "眼镜按键 stopTtsWithStatus");
                stopTtsWithStatus(2);
                return;
            }
            return;
        }
        if (this.blockAi) {
            return;
        }
        this.blockAi = true;
        this.isAiTrigger = true;
        BleBtService.getInstance().sendGlassCmd(GlassesCmd.AI_MODE_VOICE_PROMPT, 1);
        startRecognize();
        LogUtil.m335d(this.TAG, "handleAiChatEvent startRecognize");
        postAsrStopRunnable();
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void clearBinding() {
        LogUtil.m335d(this.TAG, "clearBinding");
        this.mBinding = null;
    }

    @Override // com.aivox.common.base.BaseBindingFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        stopTtsWithStatus(2);
        TtsManager ttsManager = this.ttsManager;
        if (ttsManager != null) {
            ttsManager.release();
            this.ttsManager = null;
        }
        stopRecognize();
        LocationManager locationManager = this.locationManager;
        if (locationManager != null) {
            locationManager.removeUpdates(this.locationListener);
        }
        this.chatManager.setDelegate(null);
        this.audioFocusChangeListener = null;
        this.audioFocusRequest = null;
        Disposable disposable = this.disposable;
        if (disposable != null) {
            disposable.dispose();
            this.disposable = null;
        }
        this.mDis.clear();
        releaseWaitingSoundPool();
        super.onDestroy();
    }

    private void initDb() {
        if (this.manager == null) {
            this.manager = new SQLiteDataBaseManager(requireContext());
        }
        this.uid = (String) SPUtil.get(SPUtil.USER_ID, "0");
        if (this.homeAiChatDbManager == null) {
            this.homeAiChatDbManager = HomeAiChatDbManager.getInstance(AppApplication.getIns().getDaoSession());
        }
        if (this.mCurrentSessionId.get() == 0) {
            this.mCurrentSessionId.set(System.currentTimeMillis());
            LogUtil.m335d(this.TAG, "初始化 SessionId: " + this.mCurrentSessionId.get());
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.fragment.MainAiFragment$4 */
    class C08154 implements PermissionCallback {
        C08154() {
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void granted(boolean z) {
            if (z) {
                PictureSelector.create(MainAiFragment.this.requireActivity()).openGallery(SelectMimeType.ofImage()).setMaxSelectNum(1).setMinSelectNum(1).setImageSpanCount(4).isPreviewImage(true).isDisplayCamera(true).setImageEngine(GlideEngine.createGlideEngine()).setCompressEngine(new CompressFileEngine() { // from class: com.aivox.app.fragment.MainAiFragment$4$$ExternalSyntheticLambda0
                    @Override // com.luck.picture.lib.engine.CompressFileEngine
                    public final void onStartCompress(Context context, ArrayList arrayList, OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
                        this.f$0.m2328lambda$granted$0$comaivoxappfragmentMainAiFragment$4(context, arrayList, onKeyValueResultCallbackListener);
                    }
                }).setSelectionMode(1).isSelectZoomAnim(true).forResult(Constant.CHOOSE_AI_IMAGE_REQ_CODE);
            } else {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied));
            }
        }

        /* JADX INFO: renamed from: lambda$granted$0$com-aivox-app-fragment-MainAiFragment$4, reason: not valid java name */
        /* synthetic */ void m2328lambda$granted$0$comaivoxappfragmentMainAiFragment$4(Context context, ArrayList arrayList, final OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
            Luban.with(context).load(arrayList).ignoreBy(50).setCompressListener(new OnNewCompressListener() { // from class: com.aivox.app.fragment.MainAiFragment.4.1
                @Override // top.zibin.luban.OnNewCompressListener
                public void onStart() {
                }

                @Override // top.zibin.luban.OnNewCompressListener
                public void onSuccess(String str, File file) {
                    OnKeyValueResultCallbackListener onKeyValueResultCallbackListener2 = onKeyValueResultCallbackListener;
                    if (onKeyValueResultCallbackListener2 != null) {
                        onKeyValueResultCallbackListener2.onCallback(str, file.getAbsolutePath());
                    }
                }

                @Override // top.zibin.luban.OnNewCompressListener
                public void onError(String str, Throwable th) {
                    OnKeyValueResultCallbackListener onKeyValueResultCallbackListener2 = onKeyValueResultCallbackListener;
                    if (onKeyValueResultCallbackListener2 != null) {
                        onKeyValueResultCallbackListener2.onCallback(str, null);
                    }
                }
            }).launch();
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void requestError(Throwable th) {
            LogUtil.m336e("permission.e:" + th.getLocalizedMessage());
            ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied));
            BaseAppUtils.openSettingView(MainAiFragment.this.requireContext());
        }
    }

    private void chooseImage() {
        PermissionUtils.getIns(requireActivity(), new C08154()).request("android.permission.READ_MEDIA_IMAGES");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTencentAsr() {
        if (this.asrManager == null) {
            this.asrManager = new TencentAsrManager(requireContext(), this.asrListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRecognize() {
        initTencentAsr();
        if (this.asrManager != null) {
            LogUtil.m335d(this.TAG, "开始识别");
            this.asrManager.startRecognize(this.globalLanguage);
        }
    }

    private void stopRecognize() {
        TencentAsrManager tencentAsrManager = this.asrManager;
        if (tencentAsrManager != null) {
            try {
                tencentAsrManager.stopRecognize();
            } catch (Exception e) {
                LogUtil.m337e(this.TAG, "停止 ASR 识别失败: " + e.getLocalizedMessage());
            }
            TencentAsrManager tencentAsrManager2 = this.asrManager;
            if (tencentAsrManager2 != null) {
                tencentAsrManager2.release();
            }
            this.asrManager = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopRecognizeAndQuitAiMode() {
        if (this.isAiTrigger) {
            BleBtService.getInstance().sendGlassCmd(GlassesCmd.AI_MODE_VOICE_PROMPT, 0);
            this.isAiTrigger = false;
            this.blockAi = false;
            stopRecognize();
            LogUtil.m335d(this.TAG, "stopRecognizeAndQuitAiMode stopTtsWithStatus");
            stopTtsWithStatus(2);
        }
    }

    private void initTts() {
        LogUtil.m335d(this.TAG, "initTts");
        if (this.audioManager == null && getContext() != null) {
            this.audioManager = (AudioManager) requireContext().getSystemService("audio");
        }
        this.audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda21
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public final void onAudioFocusChange(int i) {
                this.f$0.m2315lambda$initTts$10$comaivoxappfragmentMainAiFragment(i);
            }
        };
        this.audioFocusRequest = new AudioFocusRequest.Builder(2).setAudioAttributes(new AudioAttributes.Builder().setUsage(16).setContentType(1).build()).setOnAudioFocusChangeListener(this.audioFocusChangeListener).build();
        if (this.ttsManager == null) {
            LogUtil.m335d(this.TAG, "initTts: " + this.globalLanguage);
            if (this.globalLanguage.contains("zh_CN") || this.globalLanguage.startsWith("en")) {
                this.ttsManager = new TencentTtsManager();
            } else {
                this.ttsManager = new MicrosoftTtsManager();
            }
            this.ttsManager.init(this.globalLanguage);
            TtsManager ttsManager = this.ttsManager;
            if (ttsManager == null) {
                return;
            }
            ttsManager.setTtsListener(new TtsManager.TtsListener() { // from class: com.aivox.app.fragment.MainAiFragment.5
                @Override // com.aivox.common.util.tts.TtsManager.TtsListener
                public void onTtsStart() {
                    LogUtil.m337e(MainAiFragment.this.TAG, "onTtsStart");
                    if (MainAiFragment.this.ttsManager instanceof MicrosoftTtsManager) {
                        MainAiFragment.this.mHandler.removeCallbacks(MainAiFragment.this.mStopAsrRunnable);
                    }
                    MainAiFragment.this.isTtsStart = true;
                }

                @Override // com.aivox.common.util.tts.TtsManager.TtsListener
                public void onTtsStop() {
                    LogUtil.m337e(MainAiFragment.this.TAG, "onTtsStop: " + MainAiFragment.this.mTtsStopStatus);
                    MainAiFragment.this.mIsTtsSpeaking.set(false);
                    int i = MainAiFragment.this.mTtsStopStatus;
                    if (i == 0) {
                        if (!MainAiFragment.this.isTtsStart || !MainAiFragment.this.isAiTrigger || MainAiFragment.this.couldBreak) {
                            if (!MainAiFragment.this.isAiTrigger) {
                                MainAiFragment.this.blockAi = false;
                                MainAiFragment.this.stopTtsWithStatus(0);
                            }
                        } else {
                            MainAiFragment.this.startRecognize();
                        }
                        MainAiFragment.this.postAsrStopRunnable();
                    } else if (i == 2) {
                        MainAiFragment.this.stopRecognizeAndQuitAiMode();
                    }
                    MainAiFragment.this.mTtsStopStatus = 0;
                    MainAiFragment.this.isTtsStart = false;
                }

                @Override // com.aivox.common.util.tts.TtsManager.TtsListener
                public void onTtsError(String str) {
                    LogUtil.m337e(MainAiFragment.this.TAG, "TTS Error: " + str);
                    MainAiFragment.this.isTtsStart = false;
                    MainAiFragment.this.startRecognize();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$initTts$10$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2315lambda$initTts$10$comaivoxappfragmentMainAiFragment(int i) {
        if (i == -3) {
            LogUtil.m337e(this.TAG, "Audio focus lost transient (can duck), but we continue playback.");
            return;
        }
        if (i == -2 || i == -1) {
            LogUtil.m335d(this.TAG, "Audio focus lost. Stopping TTS.");
            if (getActivity() == null || getActivity().isFinishing()) {
                return;
            }
            LogUtil.m335d(this.TAG, "Audio focus lost. stopTtsWithStatus.");
            getActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2316lambda$initTts$9$comaivoxappfragmentMainAiFragment();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$initTts$9$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2316lambda$initTts$9$comaivoxappfragmentMainAiFragment() {
        stopTtsWithStatus(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTts(String str) {
        if (BleBtService.getInstance().isGlass() && this.currentPage) {
            if (this.ttsManager == null || this.audioManager == null) {
                LogUtil.m337e(this.TAG, "TTS Manager or AudioManager not initialized. Cannot start TTS.");
                if (getContext() == null) {
                    return;
                }
                initTts();
                if (this.ttsManager == null || this.audioManager == null) {
                    return;
                }
            }
            LogUtil.m335d(this.TAG, "Start tts & play, current language:" + this.globalLanguage);
            this.mTtsStopStatus = 0;
            String strTrim = EmojiFilter.filterEmoji(str).replace("*", "").trim();
            this.ttsManager.speak(strTrim, true);
            LogUtil.m337e(this.TAG, "ttsManager.speak: " + strTrim);
            this.mIsTtsSpeaking.set(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopTtsWithStatus(int i) {
        this.mTtsStopStatus = i;
        LogUtil.m337e(this.TAG, "stopTtsWithStatus: " + i);
        TtsManager ttsManager = this.ttsManager;
        if (ttsManager != null) {
            ttsManager.release();
            this.ttsManager = null;
            if (this.audioManager != null) {
                LogUtil.m335d(this.TAG, "abandonAudioFocus");
                AudioFocusRequest audioFocusRequest = this.audioFocusRequest;
                if (audioFocusRequest != null) {
                    this.audioManager.abandonAudioFocusRequest(audioFocusRequest);
                }
            }
        }
    }

    private void initAiAgent() {
        this.mDis.add(this.bitmapSub.subscribeOn(Schedulers.m1898io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda28
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2308lambda$initAiAgent$11$comaivoxappfragmentMainAiFragment((Bitmap) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda29
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2309lambda$initAiAgent$12$comaivoxappfragmentMainAiFragment((Throwable) obj);
            }
        }));
        this.chatManager.setDelegate(new GlassDelegate() { // from class: com.aivox.app.fragment.MainAiFragment.6
            @Override // com.aivox.app.util.agent.GlassDelegate
            public void glassCalendarEvent(String str, Long l) {
                LogUtil.m335d(MainAiFragment.this.TAG, "glassCalendarEvent: " + str + ", " + l);
                EventBean eventBean = new EventBean(420);
                eventBean.setS1(str);
                eventBean.setD(l.longValue());
                EventBus.getDefault().post(eventBean);
            }

            @Override // com.aivox.app.util.agent.GlassDelegate
            public void glassDeviceControl(int i) {
                LogUtil.m335d(MainAiFragment.this.TAG, "glassDeviceControl: " + i);
                if (i > 0) {
                    BleBtService.getInstance().sendGlassCmd(GlassesCmd.SET_DEVICE_CONTROL, Byte.valueOf((byte) i));
                }
            }

            @Override // com.aivox.app.util.agent.GlassDelegate
            public void glassGetLocation(GlassDelegate.LocationCallback locationCallback) {
                LogUtil.m335d(MainAiFragment.this.TAG, "glassGetLocation");
                MainAiFragment.this.requestLocation(locationCallback);
            }

            @Override // com.aivox.app.util.agent.GlassDelegate
            public void glassImageRecognition(GlassDelegate.ImageCallback imageCallback) {
                LogUtil.m335d(MainAiFragment.this.TAG, "glassImageRecognition");
                MainAiFragment.this.mActiveImageCallback = imageCallback;
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_START_RECOGNITION_TAKE_PHOTO));
            }

            @Override // com.aivox.app.util.agent.GlassDelegate
            public void glassTakePhoto() {
                LogUtil.m335d(MainAiFragment.this.TAG, "glassTakePhoto");
                BleBtService.getInstance().sendGlassCmd(GlassesCmd.SET_DEVICE_CONTROL, (byte) 8);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initAiAgent$11$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2308lambda$initAiAgent$11$comaivoxappfragmentMainAiFragment(Bitmap bitmap) throws Exception {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        Log.d(this.TAG, "Rx收到图片，准备回调给 AI 引擎");
        if (!this.chatList.isEmpty()) {
            this.chatList.get(r0.size() - 1).setImageUrl(this.imgPath);
            this.aiChatAdapter.notifyItemChanged(this.chatList.size() - 1);
            scrollToBottom();
        }
        GlassDelegate.ImageCallback imageCallback = this.mActiveImageCallback;
        if (imageCallback != null) {
            imageCallback.onImageCaptured(bitmap);
            this.mActiveImageCallback = null;
        }
    }

    /* JADX INFO: renamed from: lambda$initAiAgent$12$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2309lambda$initAiAgent$12$comaivoxappfragmentMainAiFragment(Throwable th) throws Exception {
        LogUtil.m337e(this.TAG, "图片流处理异常: " + th.getMessage());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addQuestionToChatList(final String str, final boolean z, final boolean z2) {
        requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m265x524ba847(z2, z, str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$addQuestionToChatList$13$com-aivox-app-fragment-MainAiFragment */
    /* synthetic */ void m265x524ba847(boolean z, boolean z2, String str) {
        HomeAiChatEntity homeAiChatEntity;
        showEmpty(false);
        if (this.chatList.isEmpty()) {
            homeAiChatEntity = null;
        } else {
            homeAiChatEntity = this.chatList.get(r0.size() - 1);
        }
        if (homeAiChatEntity != null && TextUtils.isEmpty(homeAiChatEntity.getAnswer()) && (z || (z2 && str.equals(homeAiChatEntity.getQuestion())))) {
            homeAiChatEntity.setQuestion(str);
            homeAiChatEntity.setImageUrl(this.imgPath);
            if (z2) {
                homeAiChatEntity.setAnswer(getString(C0874R.string.thinking));
            }
            this.aiChatAdapter.notifyItemChanged(this.chatList.size() - 1);
        } else {
            HomeAiChatEntity homeAiChatEntity2 = new HomeAiChatEntity();
            homeAiChatEntity2.setQuestion(str);
            homeAiChatEntity2.setImageUrl(this.imgPath);
            homeAiChatEntity2.setAnswer(z2 ? getString(C0874R.string.thinking) : "");
            this.chatList.add(homeAiChatEntity2);
            this.aiChatAdapter.notifyItemInserted(this.chatList.size() - 1);
        }
        scrollToBottom();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestLocation(final GlassDelegate.LocationCallback locationCallback) {
        if (ActivityCompat.checkSelfPermission(requireContext(), "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(requireContext(), Permission.ACCESS_COARSE_LOCATION) == 0) {
            LocationManager locationManager = (LocationManager) requireContext().getSystemService(PollingConstants.LOCATION_LOWER_CASE);
            this.locationManager = locationManager;
            LocationListener locationListener = this.locationListener;
            if (locationListener != null && locationManager != null) {
                locationManager.removeUpdates(locationListener);
            }
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            this.locationListener = new LocationListener() { // from class: com.aivox.app.fragment.MainAiFragment.7
                @Override // android.location.LocationListener
                public void onProviderDisabled(String str) {
                }

                @Override // android.location.LocationListener
                public void onProviderEnabled(String str) {
                }

                @Override // android.location.LocationListener
                public void onStatusChanged(String str, int i, Bundle bundle) {
                }

                @Override // android.location.LocationListener
                public void onLocationChanged(Location location) {
                    if (atomicBoolean.getAndSet(true)) {
                        return;
                    }
                    GlassDelegate.LocationCallback locationCallback2 = locationCallback;
                    if (locationCallback2 != null) {
                        locationCallback2.onLocationResult(location.getLatitude(), location.getLongitude());
                    }
                    if (MainAiFragment.this.locationManager != null) {
                        MainAiFragment.this.locationManager.removeUpdates(this);
                    }
                    LogUtil.m335d(MainAiFragment.this.TAG, "实时坐标: " + location.getLatitude() + ", " + location.getLongitude() + ", " + location.getProvider());
                }
            };
            Runnable runnable = new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2323lambda$requestLocation$14$comaivoxappfragmentMainAiFragment(atomicBoolean);
                }
            };
            this.mHandler.postDelayed(runnable, LOCATION_TIMEOUT_MS);
            try {
                this.locationManager.requestLocationUpdates(com.tencent.cloud.stream.tts.core.p032ws.Constant.NETWORK_KEY, 1000L, 1.0f, this.locationListener);
                this.mHandler.postDelayed(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2324lambda$requestLocation$15$comaivoxappfragmentMainAiFragment(atomicBoolean);
                    }
                }, 5000L);
            } catch (SecurityException e) {
                LogUtil.m337e(this.TAG, "requestLocationUpdates error:" + e.getLocalizedMessage());
                try {
                    this.locationManager.requestLocationUpdates("gps", 1000L, 1.0f, this.locationListener);
                } catch (SecurityException unused) {
                    this.mHandler.removeCallbacks(runnable);
                }
            }
        }
    }

    /* JADX INFO: renamed from: lambda$requestLocation$14$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2323lambda$requestLocation$14$comaivoxappfragmentMainAiFragment(AtomicBoolean atomicBoolean) {
        LocationManager locationManager;
        if (atomicBoolean.getAndSet(true)) {
            return;
        }
        LocationListener locationListener = this.locationListener;
        if (locationListener != null && (locationManager = this.locationManager) != null) {
            locationManager.removeUpdates(locationListener);
        }
        ToastUtil.showShort(Integer.valueOf(C0874R.string.location_failed));
    }

    /* JADX INFO: renamed from: lambda$requestLocation$15$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2324lambda$requestLocation$15$comaivoxappfragmentMainAiFragment(AtomicBoolean atomicBoolean) {
        if (atomicBoolean.get()) {
            return;
        }
        try {
            this.locationManager.requestLocationUpdates("gps", 1000L, 1.0f, this.locationListener);
        } catch (SecurityException e) {
            LogUtil.m337e(this.TAG, "requestLocationUpdates error:" + e.getLocalizedMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startChat() {
        LogUtil.m337e(this.TAG, "startChat");
        if (this.asrManager != null) {
            LogUtil.m335d(this.TAG, "startChat: 停止 ASR");
            stopRecognize();
        }
        if (this.mRunnableInTask) {
            this.mRunnableInTask = false;
            this.mHandler.removeCallbacks(this.mStopAsrRunnable);
        }
        this.mBinding.rvAi.removeCallbacks(new MainAiFragment$$ExternalSyntheticLambda0(this));
        this.isLoadText = false;
        this.isStop = false;
        this.streamIndex = 0;
        final String string = this.mBinding.etQuestion.getText().toString();
        addQuestionToChatList(string, true, false);
        List<HomeAiChatEntity> list = this.chatList;
        final HomeAiChatEntity homeAiChatEntity = list.get(list.size() - 1);
        clearInput();
        stopWaitingSound();
        playWaitingSound();
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2325lambda$startChat$16$comaivoxappfragmentMainAiFragment(string, homeAiChatEntity);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$startChat$16$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2325lambda$startChat$16$comaivoxappfragmentMainAiFragment(String str, HomeAiChatEntity homeAiChatEntity) {
        int iIncrementAndGet = this.mRequestCounter.incrementAndGet();
        this.chatManager.setSessionId(Long.valueOf(this.mCurrentSessionId.get()));
        this.chatManager.chat(str, new C08198(iIncrementAndGet, homeAiChatEntity));
    }

    /* JADX INFO: renamed from: com.aivox.app.fragment.MainAiFragment$8 */
    class C08198 implements ChatCallback {
        final /* synthetic */ HomeAiChatEntity val$currentChatEntity;
        final /* synthetic */ int val$currentRequestTag;

        C08198(int i, HomeAiChatEntity homeAiChatEntity) {
            this.val$currentRequestTag = i;
            this.val$currentChatEntity = homeAiChatEntity;
        }

        /* JADX INFO: renamed from: lambda$onSuccess$0$com-aivox-app-fragment-MainAiFragment$8, reason: not valid java name */
        /* synthetic */ void m2330lambda$onSuccess$0$comaivoxappfragmentMainAiFragment$8() {
            MainAiFragment.this.stopWaitingSound();
        }

        @Override // com.aivox.app.util.agent.ChatCallback
        public void onSuccess(final String str) {
            MainAiFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$8$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2330lambda$onSuccess$0$comaivoxappfragmentMainAiFragment$8();
                }
            });
            LogUtil.m335d(MainAiFragment.this.TAG, "onSuccess: " + str);
            if (this.val$currentRequestTag != MainAiFragment.this.mRequestCounter.get()) {
                LogUtil.m337e(MainAiFragment.this.TAG, "丢弃过时的请求响应");
                return;
            }
            if (!TextUtils.isEmpty(str)) {
                str = MainAiFragment.this.convertChinese(str);
                if (MainAiFragment.this.isStop) {
                    LogUtil.m337e(MainAiFragment.this.TAG, "用户取消AI生成");
                }
                MainAiFragment.this.streamResult = str;
                if (!MainAiFragment.this.isLoadText) {
                    MainAiFragment.this.isLoadText = true;
                    if (MainAiFragment.this.getActivity() == null || MainAiFragment.this.getActivity().isFinishing()) {
                        return;
                    } else {
                        MainAiFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$8$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m2331lambda$onSuccess$1$comaivoxappfragmentMainAiFragment$8();
                            }
                        });
                    }
                }
            }
            MainAiFragment.this.isStop = true;
            if (!MainAiFragment.this.chatList.isEmpty()) {
                this.val$currentChatEntity.setAnswer(str);
            }
            FragmentActivity fragmentActivityRequireActivity = MainAiFragment.this.requireActivity();
            final HomeAiChatEntity homeAiChatEntity = this.val$currentChatEntity;
            fragmentActivityRequireActivity.runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$8$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2333lambda$onSuccess$3$comaivoxappfragmentMainAiFragment$8(homeAiChatEntity, str);
                }
            });
            MainAiFragment.this.saveAiChat(this.val$currentChatEntity, str);
        }

        /* JADX INFO: renamed from: lambda$onSuccess$1$com-aivox-app-fragment-MainAiFragment$8, reason: not valid java name */
        /* synthetic */ void m2331lambda$onSuccess$1$comaivoxappfragmentMainAiFragment$8() {
            MainAiFragment.this.loadText();
            if (MainAiFragment.this.isAiTrigger && MainAiFragment.this.couldBreak) {
                MainAiFragment.this.startRecognize();
            }
        }

        /* JADX INFO: renamed from: lambda$onSuccess$3$com-aivox-app-fragment-MainAiFragment$8, reason: not valid java name */
        /* synthetic */ void m2333lambda$onSuccess$3$comaivoxappfragmentMainAiFragment$8(HomeAiChatEntity homeAiChatEntity, final String str) {
            if (!MainAiFragment.this.chatList.isEmpty()) {
                homeAiChatEntity.setAnswer(str);
                MainAiFragment.this.aiChatAdapter.updateLastItemText(str);
            }
            MainAiFragment.this.scrollToBottom();
            ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2332lambda$onSuccess$2$comaivoxappfragmentMainAiFragment$8(str);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSuccess$2$com-aivox-app-fragment-MainAiFragment$8, reason: not valid java name */
        /* synthetic */ void m2332lambda$onSuccess$2$comaivoxappfragmentMainAiFragment$8(String str) {
            MainAiFragment.this.startTts(str);
        }

        /* JADX INFO: renamed from: lambda$onError$4$com-aivox-app-fragment-MainAiFragment$8, reason: not valid java name */
        /* synthetic */ void m2329lambda$onError$4$comaivoxappfragmentMainAiFragment$8() {
            MainAiFragment.this.stopWaitingSound();
        }

        @Override // com.aivox.app.util.agent.ChatCallback
        public void onError(Throwable th) {
            MainAiFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$8$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2329lambda$onError$4$comaivoxappfragmentMainAiFragment$8();
                }
            });
            LogUtil.m337e(MainAiFragment.this.TAG, "AI异常：" + th.getLocalizedMessage());
            MainAiFragment.this.chatError(th.getLocalizedMessage());
        }
    }

    private void clearInput() {
        requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2307lambda$clearInput$17$comaivoxappfragmentMainAiFragment();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$clearInput$17$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2307lambda$clearInput$17$comaivoxappfragmentMainAiFragment() {
        this.mBinding.etQuestion.setText("");
        this.mBinding.cvUpload.setVisibility(8);
        this.imgData = null;
        this.imgPath = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadText() {
        if (this.isLoadText) {
            if (this.chatList.isEmpty() || TextUtils.isEmpty(this.streamResult)) {
                this.isLoadText = false;
                return;
            }
            if (this.streamIndex < this.streamResult.length()) {
                int i = this.streamIndex + 1;
                this.streamIndex = i;
                String strSubstring = this.streamResult.substring(0, Math.min(i, this.streamResult.length()));
                HomeAiChatAdapter homeAiChatAdapter = this.aiChatAdapter;
                if (homeAiChatAdapter != null) {
                    homeAiChatAdapter.updateLastItemText(strSubstring);
                }
                if (!this.chatList.isEmpty()) {
                    this.chatList.get(r2.size() - 1).setAnswer(strSubstring);
                }
                int i2 = this.loadCount + 1;
                this.loadCount = i2;
                if (i2 > 2) {
                    this.loadCount = 0;
                    scrollToBottom();
                }
                this.mBinding.rvAi.postDelayed(new MainAiFragment$$ExternalSyntheticLambda0(this), 20L);
                return;
            }
            this.isLoadText = false;
            scrollToBottom();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forceCompleteLastMessage() {
        this.mBinding.rvAi.removeCallbacks(new MainAiFragment$$ExternalSyntheticLambda0(this));
        if (!this.isLoadText || TextUtils.isEmpty(this.streamResult) || this.chatList.isEmpty()) {
            this.isLoadText = false;
            return;
        }
        this.isLoadText = false;
        HomeAiChatEntity homeAiChatEntity = this.chatList.get(r0.size() - 1);
        homeAiChatEntity.setAnswer(this.streamResult);
        HomeAiChatAdapter homeAiChatAdapter = this.aiChatAdapter;
        if (homeAiChatAdapter != null) {
            homeAiChatAdapter.updateLastItemText(this.streamResult);
        }
        this.streamIndex = 0;
        this.imgPath = null;
        this.homeAiChatDbManager.insert(homeAiChatEntity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void chatError(final String str) {
        this.isStop = true;
        this.isLoadText = false;
        this.mBinding.rvAi.removeCallbacks(new MainAiFragment$$ExternalSyntheticLambda0(this));
        if (!this.chatList.isEmpty()) {
            List<HomeAiChatEntity> list = this.chatList;
            list.get(list.size() - 1).setAnswer(str);
        }
        requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2306lambda$chatError$18$comaivoxappfragmentMainAiFragment(str);
            }
        });
        startRecognize();
    }

    /* JADX INFO: renamed from: lambda$chatError$18$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2306lambda$chatError$18$comaivoxappfragmentMainAiFragment(String str) {
        HomeAiChatAdapter homeAiChatAdapter = this.aiChatAdapter;
        if (homeAiChatAdapter != null) {
            homeAiChatAdapter.updateLastItemText(str);
        }
        scrollToBottom();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void scrollToBottom() {
        ((LinearLayoutManager) this.mBinding.rvAi.getLayoutManager()).scrollToPositionWithOffset(this.chatList.size() - 1, -100000);
    }

    private void loadAiChat() {
        if (TextUtils.isEmpty(this.uid)) {
            return;
        }
        final List<HomeAiChatEntity> listQueryAiChatList = this.homeAiChatDbManager.queryAiChatList(this.uid, this.page, 10);
        if (getActivity() != null || isAdded()) {
            requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2317lambda$loadAiChat$19$comaivoxappfragmentMainAiFragment(listQueryAiChatList);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$loadAiChat$19$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2317lambda$loadAiChat$19$comaivoxappfragmentMainAiFragment(List list) {
        if (list == null || list.isEmpty()) {
            if (this.isFirstLoad) {
                scrollToBottom();
                this.isFirstLoad = false;
                this.aiChatAdapter.changeLastPosition();
                return;
            }
            return;
        }
        if (this.page == 1) {
            this.chatList.clear();
        }
        this.chatList.addAll(list);
        this.aiChatAdapter.notifyDataSetChanged();
        this.page++;
        showEmpty(this.chatList.isEmpty());
        loadAiChat();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveAiChat(HomeAiChatEntity homeAiChatEntity, String str) {
        if (homeAiChatEntity == null || TextUtils.isEmpty(str)) {
            LogUtil.m337e(this.TAG, "saveAiChat: originalEntity or reply is null, skip saving.");
            return;
        }
        HomeAiChatEntity homeAiChatEntity2 = new HomeAiChatEntity();
        homeAiChatEntity2.setUid(this.uid);
        homeAiChatEntity2.setQuestion(homeAiChatEntity.getQuestion());
        homeAiChatEntity2.setImageUrl(homeAiChatEntity.getImageUrl());
        homeAiChatEntity2.setAnswer(str);
        homeAiChatEntity2.setType(1);
        homeAiChatEntity2.setStatus(1);
        homeAiChatEntity2.setTokenCount(0L);
        homeAiChatEntity2.setCreatedAt(DateUtil.getCurDate(DateUtil.YYYY_MM_DD_HH_MM_SS));
        try {
            this.homeAiChatDbManager.insert(homeAiChatEntity2);
            homeAiChatEntity.setId(homeAiChatEntity2.getId());
        } catch (Exception e) {
            LogUtil.m337e(this.TAG, "数据库保存失败: " + e.getMessage());
        }
        this.imgPath = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postAsrStopRunnable() {
        LogUtil.m337e(this.TAG, "postAsrStopRunnable");
        this.mHandler.removeCallbacks(this.mStopAsrRunnable);
        this.mHandler.postDelayed(this.mStopAsrRunnable, 10000L);
        this.mRunnableInTask = true;
    }

    private void exitChat() {
        stopWaitingSound();
        if (!this.isStop) {
            LogUtil.m335d(this.TAG, "页面切换，取消正在进行的AI对话");
            this.mRequestCounter.incrementAndGet();
            FragmentMainAiBinding fragmentMainAiBinding = this.mBinding;
            if (fragmentMainAiBinding != null && fragmentMainAiBinding.rvAi != null) {
                this.mBinding.rvAi.removeCallbacks(new MainAiFragment$$ExternalSyntheticLambda0(this));
            }
            this.isLoadText = false;
            this.isStop = true;
            if (!this.chatList.isEmpty()) {
                int size = this.chatList.size() - 1;
                this.chatList.remove(size);
                HomeAiChatAdapter homeAiChatAdapter = this.aiChatAdapter;
                if (homeAiChatAdapter != null) {
                    homeAiChatAdapter.notifyItemRemoved(size);
                }
                showEmpty(this.chatList.isEmpty());
            }
        }
        if (this.isAiTrigger) {
            stopRecognizeAndQuitAiMode();
        } else {
            stopTtsWithStatus(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEmpty(boolean z) {
        FragmentMainAiBinding fragmentMainAiBinding = this.mBinding;
        if (fragmentMainAiBinding == null) {
            return;
        }
        fragmentMainAiBinding.rvAi.setVisibility(z ? 8 : 0);
        this.mBinding.llEmpty.setVisibility(z ? 0 : 8);
    }

    private void initModelChoose() {
        if (this.chatManager instanceof CozeManager) {
            return;
        }
        View viewInflate = LayoutInflater.from(this.mContext).inflate(C0726R.layout.model_popup_layout, (ViewGroup) null);
        PopupWindow popupWindow = new PopupWindow(viewInflate, -2, -2);
        this.pwModel = popupWindow;
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.pwModel.setElevation(16.0f);
        this.pwModel.setOutsideTouchable(true);
        final ImageView imageView = (ImageView) viewInflate.findViewById(C0726R.id.iv_gemini);
        final ImageView imageView2 = (ImageView) viewInflate.findViewById(C0726R.id.iv_claude);
        final ImageView imageView3 = (ImageView) viewInflate.findViewById(C0726R.id.iv_gpt);
        final ImageView imageView4 = (ImageView) viewInflate.findViewById(C0726R.id.iv_grok);
        String string = SPUtil.get(SPUtil.GLASS_AI_MODEL, N8nManager.AIModel.GROK.name().toLowerCase()).toString();
        if (N8nManager.AIModel.GEMINI.name().toLowerCase().equals(string)) {
            imageView.setImageResource(C1034R.drawable.ic_ai_model_selected);
            ((N8nManager) this.chatManager).changeModel(N8nManager.AIModel.GEMINI);
        } else if (N8nManager.AIModel.CLAUDE.name().toLowerCase().equals(string)) {
            imageView2.setImageResource(C1034R.drawable.ic_ai_model_selected);
            ((N8nManager) this.chatManager).changeModel(N8nManager.AIModel.CLAUDE);
        } else if (N8nManager.AIModel.GROK.name().toLowerCase().equals(string)) {
            imageView4.setImageResource(C1034R.drawable.ic_ai_model_selected);
            ((N8nManager) this.chatManager).changeModel(N8nManager.AIModel.GROK);
        } else {
            imageView3.setImageResource(C1034R.drawable.ic_ai_model_selected);
            ((N8nManager) this.chatManager).changeModel(N8nManager.AIModel.GPT);
        }
        viewInflate.findViewById(C0726R.id.cl_gemini).setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2310lambda$initModelChoose$20$comaivoxappfragmentMainAiFragment(imageView, imageView2, imageView3, imageView4, view2);
            }
        });
        viewInflate.findViewById(C0726R.id.cl_claude).setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2311lambda$initModelChoose$21$comaivoxappfragmentMainAiFragment(imageView, imageView2, imageView3, imageView4, view2);
            }
        });
        viewInflate.findViewById(C0726R.id.cl_gpt).setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2312lambda$initModelChoose$22$comaivoxappfragmentMainAiFragment(imageView, imageView2, imageView3, imageView4, view2);
            }
        });
        viewInflate.findViewById(C0726R.id.cl_grok).setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2313lambda$initModelChoose$23$comaivoxappfragmentMainAiFragment(imageView, imageView2, imageView3, imageView4, view2);
            }
        });
        this.aiChatAdapter.setListener(new HomeAiChatAdapter.RegenerateClickListener() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda9
            @Override // com.aivox.app.adapter.HomeAiChatAdapter.RegenerateClickListener
            public final void regenerateClick(String str, View view2) {
                this.f$0.m2314lambda$initModelChoose$24$comaivoxappfragmentMainAiFragment(str, view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initModelChoose$20$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2310lambda$initModelChoose$20$comaivoxappfragmentMainAiFragment(ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, View view2) {
        if (this.chatManager instanceof N8nManager) {
            SPUtil.put(SPUtil.GLASS_AI_MODEL, N8nManager.AIModel.GEMINI.name().toLowerCase());
            ((N8nManager) this.chatManager).changeModel(N8nManager.AIModel.GEMINI);
            imageView.setImageResource(C1034R.drawable.ic_ai_model_selected);
            imageView2.setImageResource(0);
            imageView3.setImageResource(0);
            imageView4.setImageResource(0);
            this.pwModel.dismiss();
            if (this.isRegenerate) {
                this.mBinding.etQuestion.setText(convertChinese(this.regenerateQuestion));
                startChat();
            }
        }
    }

    /* JADX INFO: renamed from: lambda$initModelChoose$21$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2311lambda$initModelChoose$21$comaivoxappfragmentMainAiFragment(ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, View view2) {
        if (this.chatManager instanceof N8nManager) {
            SPUtil.put(SPUtil.GLASS_AI_MODEL, N8nManager.AIModel.CLAUDE.name().toLowerCase());
            ((N8nManager) this.chatManager).changeModel(N8nManager.AIModel.CLAUDE);
            imageView.setImageResource(0);
            imageView2.setImageResource(C1034R.drawable.ic_ai_model_selected);
            imageView3.setImageResource(0);
            imageView4.setImageResource(0);
            this.pwModel.dismiss();
            if (this.isRegenerate) {
                this.mBinding.etQuestion.setText(convertChinese(this.regenerateQuestion));
                startChat();
            }
        }
    }

    /* JADX INFO: renamed from: lambda$initModelChoose$22$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2312lambda$initModelChoose$22$comaivoxappfragmentMainAiFragment(ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, View view2) {
        if (this.chatManager instanceof N8nManager) {
            SPUtil.put(SPUtil.GLASS_AI_MODEL, N8nManager.AIModel.GPT.name().toLowerCase());
            ((N8nManager) this.chatManager).changeModel(N8nManager.AIModel.GPT);
            imageView.setImageResource(0);
            imageView2.setImageResource(0);
            imageView3.setImageResource(C1034R.drawable.ic_ai_model_selected);
            imageView4.setImageResource(0);
            this.pwModel.dismiss();
            if (this.isRegenerate) {
                this.mBinding.etQuestion.setText(convertChinese(this.regenerateQuestion));
                startChat();
            }
        }
    }

    /* JADX INFO: renamed from: lambda$initModelChoose$23$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2313lambda$initModelChoose$23$comaivoxappfragmentMainAiFragment(ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, View view2) {
        if (this.chatManager instanceof N8nManager) {
            SPUtil.put(SPUtil.GLASS_AI_MODEL, N8nManager.AIModel.GROK.name().toLowerCase());
            ((N8nManager) this.chatManager).changeModel(N8nManager.AIModel.GROK);
            imageView.setImageResource(0);
            imageView2.setImageResource(0);
            imageView3.setImageResource(0);
            imageView4.setImageResource(C1034R.drawable.ic_ai_model_selected);
            this.pwModel.dismiss();
            if (this.isRegenerate) {
                this.mBinding.etQuestion.setText(convertChinese(this.regenerateQuestion));
                startChat();
            }
        }
    }

    /* JADX INFO: renamed from: lambda$initModelChoose$24$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2314lambda$initModelChoose$24$comaivoxappfragmentMainAiFragment(String str, View view2) {
        this.regenerateQuestion = str;
        this.anchorView = view2;
        showModelChoose(true);
    }

    private void showModelChoose(boolean z) {
        PopupWindow popupWindow = this.pwModel;
        if (popupWindow == null) {
            return;
        }
        this.isRegenerate = z;
        View contentView = popupWindow.getContentView();
        contentView.findViewById(C0726R.id.tv_regenerate).setVisibility(this.isRegenerate ? 0 : 8);
        contentView.findViewById(C0726R.id.v_line).setVisibility(this.isRegenerate ? 0 : 8);
        if (this.isRegenerate) {
            contentView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            int measuredHeight = contentView.getMeasuredHeight();
            int[] iArr = new int[2];
            this.anchorView.getLocationOnScreen(iArr);
            int i = iArr[1];
            int height = this.anchorView.getHeight();
            this.pwModel.showAsDropDown(this.anchorView, 0, this.anchorView.getResources().getDisplayMetrics().heightPixels - (i + height) < measuredHeight ? -(height + measuredHeight) : 0);
            return;
        }
        this.pwModel.showAsDropDown(this.mBinding.tvTitle, 0, 0, 80);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String convertChinese(String str) {
        if (this.zhType == 1 && ZhConverterUtil.containsSimple(str)) {
            return ZhTwConverterUtil.toTraditional(str);
        }
        return (this.zhType == 2 && ZhTwConverterUtil.containsTraditional(str)) ? ZhConverterUtil.toSimple(str) : str;
    }

    private void initWaitingSoundPool() {
        if (this.mSoundPool == null) {
            SoundPool soundPoolBuild = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(16).setContentType(4).build()).build();
            this.mSoundPool = soundPoolBuild;
            this.mWaitingSoundId = soundPoolBuild.load(requireContext(), C0958R.raw.dengdengs, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playWaitingSound() {
        if (getActivity() == null || this.isStop || this.mWaitingSoundId == -1) {
            return;
        }
        String lowerCase = Build.MANUFACTURER.toLowerCase(Locale.ENGLISH);
        String lowerCase2 = Build.BRAND.toLowerCase(Locale.ENGLISH);
        if (lowerCase.contains("vivo") || lowerCase2.contains("iqoo")) {
            SoundPool soundPool = this.mSoundPool;
            if (soundPool != null && this.isWaitingSoundPlaying) {
                soundPool.release();
                this.mSoundPool = null;
                initWaitingSoundPool();
            }
            if (this.mSoundPool != null) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.aivox.app.fragment.MainAiFragment$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2322lambda$playWaitingSound$25$comaivoxappfragmentMainAiFragment();
                    }
                }, 100L);
                this.isWaitingSoundPlaying = true;
                this.mHandler.postDelayed(this.mPlayWaitingSoundRunnable, 1400L);
                return;
            }
            return;
        }
        SoundPool soundPool2 = this.mSoundPool;
        if (soundPool2 != null) {
            this.mStreamId = soundPool2.play(this.mWaitingSoundId, 3.0f, 3.0f, 1, 0, 1.0f);
            this.mHandler.postDelayed(this.mPlayWaitingSoundRunnable, 1400L);
        }
    }

    /* JADX INFO: renamed from: lambda$playWaitingSound$25$com-aivox-app-fragment-MainAiFragment, reason: not valid java name */
    /* synthetic */ void m2322lambda$playWaitingSound$25$comaivoxappfragmentMainAiFragment() {
        int i = this.mWaitingSoundId;
        if (i != -1) {
            this.mStreamId = this.mSoundPool.play(i, 1.0f, 1.0f, 10, 0, 1.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopWaitingSound() {
        int i;
        this.isWaitingSoundPlaying = false;
        this.mHandler.removeCallbacks(this.mPlayWaitingSoundRunnable);
        SoundPool soundPool = this.mSoundPool;
        if (soundPool == null || (i = this.mStreamId) == -1) {
            return;
        }
        soundPool.stop(i);
        this.mStreamId = -1;
    }

    private void releaseWaitingSoundPool() {
        stopWaitingSound();
        SoundPool soundPool = this.mSoundPool;
        if (soundPool != null) {
            soundPool.release();
            this.mSoundPool = null;
            this.mWaitingSoundId = -1;
        }
    }
}
