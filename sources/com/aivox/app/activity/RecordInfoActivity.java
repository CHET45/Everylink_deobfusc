package com.aivox.app.activity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.Key;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.app.C0726R;
import com.aivox.app.activity.RecordInfoActivity;
import com.aivox.app.adapter.TranscribeAdapter;
import com.aivox.app.databinding.ActivityRecordInfoBinding;
import com.aivox.app.fragment.RecordTranscribeFragment;
import com.aivox.app.test.denoise.AudioDownloadUtil;
import com.aivox.app.test.share.DownloadShareTxt;
import com.aivox.app.test.trans.AudioTransModel;
import com.aivox.app.util.wav.IOSWavManager2;
import com.aivox.app.view.AiSummaryOperateView;
import com.aivox.app.view.AudioInAppShareView;
import com.aivox.app.view.AudioOperateAIView;
import com.aivox.app.view.AudioOperateView;
import com.aivox.app.view.AudioShareView;
import com.aivox.app.view.AudioTxtLongClickPopup;
import com.aivox.app.view.ItemDeletePopup;
import com.aivox.base.C0874R;
import com.aivox.base.common.BaseDataHandle;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.http.HttpException;
import com.aivox.base.permission.PermissionCallback;
import com.aivox.base.permission.PermissionUtils;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AudioService;
import com.aivox.common.http.UserService;
import com.aivox.common.http.oss.CommonUploadManager;
import com.aivox.common.http.oss.OnUploadListener;
import com.aivox.common.model.AiChatBean;
import com.aivox.common.model.AiScenesBean;
import com.aivox.common.model.AudioContentBean;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.AudioMarkBean;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.ShareBean;
import com.aivox.common.model.ShareStatus;
import com.aivox.common.model.Transcribe;
import com.aivox.common.model.UserInfo;
import com.aivox.common.p003db.LocalFileEntityDao;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.p003db.maneger.LocalFileDbManager;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.GlideEngine;
import com.aivox.common.util.LanguageUtils;
import com.aivox.common.util.StringUtil;
import com.aivox.common.view.LanguageSelectView;
import com.aivox.common_ui.BottomEditDialogView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.FileScrollSelectView;
import com.aivox.common_ui.PowerfulEditText;
import com.aivox.common_ui.SpeakerNumSelectView;
import com.aivox.common_ui.antishake.AntiShake;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.piasy.rxandroidaudio.PlayConfig;
import com.github.piasy.rxandroidaudio.RxAudioPlayer;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.microsoft.azure.storage.queue.QueueConstants;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;

/* JADX INFO: loaded from: classes.dex */
public class RecordInfoActivity extends BaseFragmentActivity implements AudioTxtLongClickPopup.AudioTxtLongClickListener, TranscribeAdapter.OnCheckChangeListener, OnViewClickListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int HANDLER_NOTIFY = 1001;
    public static final int MODE_EDIT = 2;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_SEARCH = 3;
    public static final int MODE_SELECT = 1;
    private static final int REQUEST_CODE_EDIT = 101;
    private static final int REQUEST_CODE_MARK = 100;
    private static final int REQ_PROGRESS = 1002;
    private BottomSheetDialog audioOperateAIDialog;
    private int curVideoTransId;
    private int currentEditPos;
    private IOSWavManager2 iosWavManager2;
    private boolean isPlaying;
    private ItemDeletePopup mAiChatDeletePop;
    private Disposable mAiResultStatusDis;
    private boolean mAllowSave;
    private ObjectAnimator mAnim;
    private int mAudioBg;
    private int mAudioId;
    private AudioInfoBean mAudioInfoBean;
    private AudioTxtLongClickPopup mAudioTxtLongClickPop;
    private String mAudioUrl;
    private ActivityRecordInfoBinding mBinding;
    private boolean mDoSeparate;
    private boolean mFromUser;
    private boolean mIsDeleteAfterRead;
    private boolean mIsEncrypt;
    private boolean mIsLocal;
    private String mLastQuestion;
    private LocalFileDbManager mLocalFileDbManager;
    private int mMaxPicCount;
    private MediaPlayer mMediaPlayer;
    private boolean mPicInsertEnable;
    private boolean mReTranscribing;
    private AiScenesAdapter mScenesAdapter;
    private ShareBean mShareBean;
    private ShareStatus mShareStatus;
    private int mSpeakerNum;
    private int mTabIndex;
    private int mTotalTime;
    private Disposable mTransStatusDis;
    private int mValidPeriod;
    private SQLiteDataBaseManager manager;
    private BottomSheetDialog moreOperateDialog;
    private int nextStep;
    private String s2tType;
    private boolean toChangeVideo;
    private RecordTranscribeFragment transcribeFragment;
    private int transcribeProgress;
    private UserInfo userInfo;
    private MyEnum.AUDIO_PLAY_STATUS playStatus = MyEnum.AUDIO_PLAY_STATUS.IDLE;
    private final List<AiScenesBean> mList = new ArrayList();
    private final List<Float> speedList = new ArrayList();
    private final List<Integer> displayList = new ArrayList();
    private final List<Transcribe> checkList = new ArrayList();
    private final List<Transcribe> transcribeList = new ArrayList();
    private boolean hasTranslated = false;
    private boolean isFirst = true;
    private float mSpeed = 1.0f;
    private int mStartWaveTime = 0;
    private int mNoteMarkCount = 0;
    private int mCurPicCount = 0;
    private int currentSpeedIndex = 1;
    private int listMode = 0;
    private int mFromLang = -1;
    private int mAvatarStyle = 1;
    private final Handler mHandler = new HandlerC07791();

    static /* synthetic */ void lambda$addPicTag$66(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$checkBeforeUpload$37(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$checkBeforeUpload$38(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$click2ShowSelectTranslateView$52(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$editName$67(Object obj) throws Exception {
    }

    static /* synthetic */ void lambda$showAiOperateView$45(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ void lambda$showAiOperateView$47(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    @Override // com.aivox.app.view.AudioTxtLongClickPopup.AudioTxtLongClickListener
    public void longClick2TranslateSingle() {
    }

    static /* synthetic */ int access$5408(RecordInfoActivity recordInfoActivity) {
        int i = recordInfoActivity.mNoteMarkCount;
        recordInfoActivity.mNoteMarkCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$5410(RecordInfoActivity recordInfoActivity) {
        int i = recordInfoActivity.mNoteMarkCount;
        recordInfoActivity.mNoteMarkCount = i - 1;
        return i;
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.RecordInfoActivity$1 */
    class HandlerC07791 extends Handler {
        static /* synthetic */ void lambda$handleMessage$1(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        }

        static /* synthetic */ void lambda$handleMessage$2(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        }

        HandlerC07791() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 1) {
                LogUtil.m338i("播放缓冲");
                RecordInfoActivity.this.playStatus = MyEnum.AUDIO_PLAY_STATUS.PLAYING;
                RecordInfoActivity.this.refreshPlayBtn();
                DialogUtils.showLoadingDialog(RecordInfoActivity.this.context);
                return;
            }
            if (i == 2) {
                RecordInfoActivity.this.playStatus = MyEnum.AUDIO_PLAY_STATUS.PAUSED;
                RecordInfoActivity.this.refreshPlayBtn();
                return;
            }
            if (i == 3) {
                RecordInfoActivity.this.playStatus = MyEnum.AUDIO_PLAY_STATUS.PLAYING;
                RecordInfoActivity.this.refreshPlayBtn();
                if (RecordInfoActivity.this.mMediaPlayer != null) {
                    RecordInfoActivity.this.mMediaPlayer.seekTo(RecordInfoActivity.this.mStartWaveTime);
                    return;
                }
                return;
            }
            if (i == 4) {
                LogUtil.m338i("播放结束1");
                RecordInfoActivity.this.playStatus = MyEnum.AUDIO_PLAY_STATUS.IDLE;
                RecordInfoActivity.this.mHandler.removeMessages(1001);
                RecordInfoActivity.this.mMediaPlayer = null;
                if (RecordInfoActivity.this.toChangeVideo) {
                    RecordInfoActivity.this.toChangeVideo = false;
                } else {
                    RecordInfoActivity.this.mStartWaveTime = 0;
                }
                RecordInfoActivity.this.refreshPlayBtn();
                RecordInfoActivity.this.refreshSeekbar();
                return;
            }
            if (i == 5) {
                RecordInfoActivity.this.getMediaPlayer();
                RecordInfoActivity recordInfoActivity = RecordInfoActivity.this;
                recordInfoActivity.setSpeed(recordInfoActivity.mSpeed);
                try {
                    if (RecordInfoActivity.this.mMediaPlayer != null) {
                        RecordInfoActivity.this.mMediaPlayer.seekTo(RecordInfoActivity.this.mStartWaveTime);
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    BaseAppUtils.printErrorMsg(e);
                }
                RecordInfoActivity.this.mHandler.sendEmptyMessage(1001);
                DialogUtils.hideLoadingDialog();
                return;
            }
            if (i == 7) {
                RecordInfoActivity.this.mStartWaveTime = 0;
                RecordInfoActivity.this.playStatus = MyEnum.AUDIO_PLAY_STATUS.IDLE;
                RecordInfoActivity.this.refreshPlayBtn();
                RecordInfoActivity.this.refreshSeekbar();
                DialogUtils.hideLoadingDialog();
                if (BaseStringUtil.isEmpty(RecordInfoActivity.this.mAudioUrl)) {
                    DialogUtils.showDialogWithBtnIds(RecordInfoActivity.this.context, Integer.valueOf(C0874R.string.file_play_fail), Integer.valueOf(C0874R.string.toast_file_does_not_sync_or_deleted), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$1$$ExternalSyntheticLambda1
                        @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                        public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                            RecordInfoActivity.HandlerC07791.lambda$handleMessage$1(context, dialogBuilder, dialog, i2, i3, editText);
                        }
                    }, false, false, C0874R.string.sure, C0874R.string.sure);
                    return;
                } else {
                    DialogUtils.showDialogWithDefBtn(RecordInfoActivity.this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.file_play_fail), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$1$$ExternalSyntheticLambda2
                        @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                        public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                            RecordInfoActivity.HandlerC07791.lambda$handleMessage$2(context, dialogBuilder, dialog, i2, i3, editText);
                        }
                    }, false, false);
                    return;
                }
            }
            if (i == 1008) {
                int i2 = message.arg1;
                RecordInfoActivity.this.mHandler.removeMessages(1008);
                if (RecordInfoActivity.this.mAudioInfoBean == null || RecordInfoActivity.this.mAudioInfoBean.getIsTrans() != MyEnum.TRANS_STATE.ON_TRANS.type) {
                    return;
                }
                RecordInfoActivity recordInfoActivity2 = RecordInfoActivity.this;
                recordInfoActivity2.refreshBtmNotify(true, recordInfoActivity2.getString(recordInfoActivity2.mDoSeparate ? C0874R.string.record_info_on_separate : C0874R.string.record_info_on_transcribe));
                Message message2 = new Message();
                message2.what = 1008;
                message2.arg1 = i2;
                RecordInfoActivity.this.mHandler.sendMessageDelayed(message2, 4000L);
                return;
            }
            if (i != 1001) {
                if (i != 1002) {
                    return;
                }
                if (RecordInfoActivity.this.transcribeProgress != 100) {
                    if (RecordInfoActivity.this.mDoSeparate) {
                        RecordInfoActivity.this.reqSeparateProgress();
                        return;
                    }
                    return;
                }
                RecordInfoActivity.this.refreshBtmNotify(false, "");
                ToastUtil.showShort(Integer.valueOf(RecordInfoActivity.this.mDoSeparate ? C0874R.string.file_transcribe_separate_success : C0874R.string.file_transcribe_success));
                RecordInfoActivity.this.mAudioInfoBean.setIsTrans(MyEnum.TRANS_STATE.TRANSCRIBED.type);
                RxAudioPlayer.getInstance().stopPlay();
                RecordInfoActivity.this.isPlaying = false;
                RecordInfoActivity.this.mHandler.sendEmptyMessage(4);
                new Handler().postDelayed(new Runnable() { // from class: com.aivox.app.activity.RecordInfoActivity$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m211x44b64975();
                    }
                }, 300L);
                return;
            }
            try {
                if (RecordInfoActivity.this.mMediaPlayer == null || RecordInfoActivity.this.mMediaPlayer.getDuration() <= 0 || !RecordInfoActivity.this.isPlaying) {
                    return;
                }
                RecordInfoActivity.this.mHandler.sendEmptyMessageDelayed(1001, 1000L);
                if (RecordInfoActivity.this.playStatus == MyEnum.AUDIO_PLAY_STATUS.IDLE) {
                    return;
                }
                RecordInfoActivity recordInfoActivity3 = RecordInfoActivity.this;
                recordInfoActivity3.mStartWaveTime = recordInfoActivity3.mMediaPlayer.getCurrentPosition();
                RecordInfoActivity.this.refreshSeekbar();
            } catch (IllegalStateException e2) {
                e2.printStackTrace();
                BaseAppUtils.printErrorMsg(e2);
            }
        }

        /* JADX INFO: renamed from: lambda$handleMessage$0$com-aivox-app-activity-RecordInfoActivity$1 */
        /* synthetic */ void m211x44b64975() {
            RecordInfoActivity.this.mAvatarStyle = 1;
            RecordInfoActivity.this.transcribeFragment.setAvatarStyle(RecordInfoActivity.this.mAvatarStyle);
            RecordInfoActivity.this.mReTranscribing = false;
            RecordInfoActivity.this.transcribeFragment.setReTranscribingState(false);
            RecordInfoActivity.this.transcribeFragment.setTrans(RecordInfoActivity.this.mAudioInfoBean.getIsTrans());
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        BarUtils.setNavBarColor(this, getColor(C0874R.color.bg_color_popup_bar));
        ActivityRecordInfoBinding activityRecordInfoBinding = (ActivityRecordInfoBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_record_info);
        this.mBinding = activityRecordInfoBinding;
        activityRecordInfoBinding.setClickListener(this);
        ItemDeletePopup itemDeletePopupCreate = ItemDeletePopup.create(this.context, new ItemDeletePopup.OnDeleteListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda23
            @Override // com.aivox.app.view.ItemDeletePopup.OnDeleteListener
            public final void delete() {
                this.f$0.m2199lambda$initView$3$comaivoxappactivityRecordInfoActivity();
            }
        });
        this.mAiChatDeletePop = itemDeletePopupCreate;
        itemDeletePopupCreate.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda24
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                this.f$0.m2200lambda$initView$4$comaivoxappactivityRecordInfoActivity();
            }
        });
        Iterator it = Arrays.asList(Integer.valueOf(Constant.AI_TYPE_SUMMARY), Integer.valueOf(Constant.AI_TYPE_OVERVIEW)).iterator();
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            AiScenesBean aiScenesBean = new AiScenesBean();
            aiScenesBean.setScenesType(iIntValue);
            aiScenesBean.setScenesNameRes(getNameRes(iIntValue));
            aiScenesBean.setScenesIconRes(getIconRes(iIntValue));
            aiScenesBean.setScenesBgRes(getBgRes(iIntValue));
            aiScenesBean.setSelected(false);
            this.mList.add(aiScenesBean);
        }
        AiScenesAdapter aiScenesAdapter = new AiScenesAdapter(C0726R.layout.item_ai_scenes_layout, this.mList);
        this.mScenesAdapter = aiScenesAdapter;
        aiScenesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda25
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m2201lambda$initView$5$comaivoxappactivityRecordInfoActivity(baseQuickAdapter, view2, i);
            }
        });
        this.mBinding.rvScenes.setLayoutManager(new LinearLayoutManager(this.context, 0, false));
        this.mBinding.rvScenes.setAdapter(this.mScenesAdapter);
        this.mBinding.etAiTalk.addTextChangedListener(new TextWatcher() { // from class: com.aivox.app.activity.RecordInfoActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                RecordInfoActivity.this.mBinding.ivAiTalkSend.setVisibility(editable.toString().isEmpty() ? 4 : 0);
                RecordInfoActivity.this.mBinding.rvScenes.setVisibility(TextUtils.isEmpty(editable) ? 0 : 8);
            }
        });
        this.mBinding.etAiTalk.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda26
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return this.f$0.m2202lambda$initView$6$comaivoxappactivityRecordInfoActivity(textView, i, keyEvent);
            }
        });
        this.mBinding.etSearch.addTextChangedListener(new TextWatcher() { // from class: com.aivox.app.activity.RecordInfoActivity.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                LogUtil.m338i("search:" + ((Object) editable));
                if (RecordInfoActivity.this.transcribeFragment != null) {
                    RecordInfoActivity.this.transcribeFragment.refreshKeywords(editable.toString());
                }
            }
        });
        this.mBinding.etSearch.setOnRightClickListener(new PowerfulEditText.OnRightClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda27
            @Override // com.aivox.common_ui.PowerfulEditText.OnRightClickListener
            public final void onClick(EditText editText) {
                this.f$0.m2203lambda$initView$7$comaivoxappactivityRecordInfoActivity(editText);
            }
        });
        this.mBinding.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda28
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2204lambda$initView$8$comaivoxappactivityRecordInfoActivity(view2);
            }
        });
        this.mBinding.tvSave.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda29
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2197lambda$initView$11$comaivoxappactivityRecordInfoActivity(view2);
            }
        });
        this.manager = new SQLiteDataBaseManager(this);
        this.mLocalFileDbManager = LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
        this.userInfo = this.manager.getUserInfo();
        Bundle extras = getIntent().getExtras();
        this.mAudioId = extras.getInt(Constant.AUDIO_ID, 0);
        this.mAudioUrl = extras.getString(Constant.LOCAL_AUDIO_URL, "");
        this.mIsLocal = extras.getBoolean(Constant.IS_LOCAL_AUDIO);
        this.mAudioBg = extras.getInt(Constant.AUDIO_BG, -1);
        LogUtil.m338i("bg:" + this.mAudioBg);
        DialogUtils.showLoadingDialog(this, getString(C0874R.string.loading_ing) + "...");
        m185x79e369d0();
        if (((Boolean) SPUtil.get(SPUtil.HAS_BREAK_SAVE_AUDIO, false)).booleanValue() && ((Integer) SPUtil.get(SPUtil.BREAK_SAVE_AUDIO_ID, -1)).intValue() == this.mAudioId) {
            SPUtil.put(SPUtil.HAS_BREAK_SAVE_AUDIO, false);
            EventBus.getDefault().post(new EventBean(72, false));
        }
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2199lambda$initView$3$comaivoxappactivityRecordInfoActivity() {
        DialogUtils.showDeleteDialog(this.context, "", Integer.valueOf(C0874R.string.note_mark_delete_notice), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda38
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m2198lambda$initView$2$comaivoxappactivityRecordInfoActivity(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, null, true, true, C0874R.string.delete, C0874R.string.cancel, 0);
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2198lambda$initView$2$comaivoxappactivityRecordInfoActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        DialogUtils.showLoadingDialog(context);
        if (CollectionUtils.isEmpty(this.transcribeFragment.getSelectedChatId())) {
            DialogUtils.hideLoadingDialog();
            this.mAiChatDeletePop.dismiss();
        } else {
            new AudioService(context).deleteAiChatList(this.transcribeFragment.getSelectedChatId()).doFinally(new Action() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda80
                @Override // io.reactivex.functions.Action
                public final void run() throws Exception {
                    this.f$0.m2194lambda$initView$0$comaivoxappactivityRecordInfoActivity();
                }
            }).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda81
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2195lambda$initView$1$comaivoxappactivityRecordInfoActivity(obj);
                }
            }, new RecordInfoActivity$$ExternalSyntheticLambda15());
        }
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2194lambda$initView$0$comaivoxappactivityRecordInfoActivity() throws Exception {
        DialogUtils.hideLoadingDialog();
        this.mAiChatDeletePop.dismiss();
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2195lambda$initView$1$comaivoxappactivityRecordInfoActivity(Object obj) throws Exception {
        this.transcribeFragment.removeSelectChatId();
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2200lambda$initView$4$comaivoxappactivityRecordInfoActivity() {
        this.transcribeFragment.switchAiChatListMode(0);
        this.mBinding.rlEdit.setVisibility(8);
        this.mBinding.rlTop.setVisibility(0);
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2201lambda$initView$5$comaivoxappactivityRecordInfoActivity(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        doAiChat(this.mList.get(i).getScenesType(), false);
    }

    /* JADX INFO: renamed from: lambda$initView$6$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ boolean m2202lambda$initView$6$comaivoxappactivityRecordInfoActivity(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        doAiChat(Constant.AI_TYPE_TALK, false);
        return true;
    }

    /* JADX INFO: renamed from: lambda$initView$7$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2203lambda$initView$7$comaivoxappactivityRecordInfoActivity(EditText editText) {
        this.mBinding.etSearch.setText("");
        KeyboardUtils.hideSoftInput(this.mBinding.etSearch);
    }

    /* JADX INFO: renamed from: lambda$initView$8$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2204lambda$initView$8$comaivoxappactivityRecordInfoActivity(View view2) {
        if (this.mTabIndex == 1) {
            this.mBinding.rlEdit.setVisibility(8);
            this.mBinding.rlTop.setVisibility(0);
            if (this.mAiChatDeletePop.isShowing()) {
                this.mAiChatDeletePop.dismiss();
                return;
            }
            return;
        }
        if (this.mMediaPlayer != null) {
            audioStop();
        }
        changeListMode(0);
        this.mAudioTxtLongClickPop.dismiss();
    }

    /* JADX INFO: renamed from: lambda$initView$11$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2197lambda$initView$11$comaivoxappactivityRecordInfoActivity(View view2) {
        if (this.mMediaPlayer != null) {
            audioStop();
        }
        changeListMode(0);
        new Handler().postDelayed(new Runnable() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2196lambda$initView$10$comaivoxappactivityRecordInfoActivity();
            }
        }, 500L);
    }

    /* JADX INFO: renamed from: lambda$initView$10$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2196lambda$initView$10$comaivoxappactivityRecordInfoActivity() {
        List<AudioContentBean.DataBean> editData = this.transcribeFragment.getEditData();
        if (editData.isEmpty()) {
            return;
        }
        AudioContentBean audioContentBean = new AudioContentBean(this.mAudioInfoBean.getId(), editData);
        this.mAudioTxtLongClickPop.dismiss();
        new AudioService(this).batchEditContent(audioContentBean).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2205lambda$initView$9$comaivoxappactivityRecordInfoActivity((List) obj);
            }
        }, new RecordInfoActivity$$ExternalSyntheticLambda15());
    }

    /* JADX INFO: renamed from: lambda$initView$9$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2205lambda$initView$9$comaivoxappactivityRecordInfoActivity(List list) throws Exception {
        this.transcribeFragment.batchUpdate(list);
    }

    private int getNameRes(int i) {
        if (i == Constant.AI_TYPE_SUMMARY) {
            return C0874R.string.ai_summary_scenes_summary;
        }
        if (i == Constant.AI_TYPE_OVERVIEW) {
            return C0874R.string.ai_summary_scenes_overview;
        }
        if (i == Constant.AI_TYPE_MIND) {
            return C0874R.string.ai_summary_scenes_mind;
        }
        return C0874R.string.ai_summary_scenes_default;
    }

    private int getIconRes(int i) {
        if (i == Constant.AI_TYPE_SUMMARY) {
            return C1034R.drawable.ai_scenes_summarize;
        }
        if (i == Constant.AI_TYPE_OVERVIEW) {
            return C1034R.drawable.ai_scenes_overview;
        }
        if (i == Constant.AI_TYPE_MIND) {
            return C1034R.drawable.ai_scenes_mind;
        }
        return C1034R.drawable.ai_scenes_summarize;
    }

    private int getBgRes(int i) {
        if (i == Constant.AI_TYPE_SUMMARY) {
            return C1034R.drawable.bg_scenes_summarize;
        }
        if (i == Constant.AI_TYPE_OVERVIEW) {
            return C1034R.drawable.bg_scenes_overview;
        }
        if (i == Constant.AI_TYPE_MIND) {
            return C1034R.drawable.bg_scenes_mind;
        }
        return C1034R.drawable.bg_scenes_summarize;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAiChat(int i, boolean z) {
        LogUtil.m337e("OPEN AI", "doAiChat : " + i);
        if (this.mReTranscribing) {
            DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.record_info_action_in_progress), null, null, false, true, C0874R.string.dismiss, C0874R.string.confirm);
            return;
        }
        if (this.transcribeFragment.transIsEmpty()) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.trans_is_empty));
            return;
        }
        if (!z) {
            if (i == Constant.AI_TYPE_SUMMARY) {
                this.mLastQuestion = getString(C0874R.string.ai_summary_scenes_summary);
            } else if (i == Constant.AI_TYPE_OVERVIEW) {
                this.mLastQuestion = getString(C0874R.string.ai_summary_scenes_overview);
            } else if (i == Constant.AI_TYPE_MIND) {
                this.mLastQuestion = getString(C0874R.string.ai_summary_scenes_mind);
            } else {
                this.mLastQuestion = this.mBinding.etAiTalk.getText().toString().trim();
            }
        }
        if (BaseStringUtil.isEmpty(this.mLastQuestion)) {
            return;
        }
        this.mBinding.clAiSummary.setVisibility(4);
        AiChatBean.Records records = new AiChatBean.Records();
        records.setStatus(-1);
        records.setQuestion(this.mLastQuestion);
        records.setAnswer("");
        records.setType(Integer.valueOf(i));
        this.transcribeFragment.setSummaryContent(records);
        this.transcribeFragment.onGenerationStarted();
        new AudioService(this.context).generateByAi(this.mAudioId, i, this.mLastQuestion).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2189lambda$doAiChat$15$comaivoxappactivityRecordInfoActivity((Integer) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2191lambda$doAiChat$17$comaivoxappactivityRecordInfoActivity((Throwable) obj);
            }
        });
        this.mBinding.etAiTalk.setText("");
        KeyboardUtils.hideSoftInput(this);
    }

    /* JADX INFO: renamed from: lambda$doAiChat$15$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2189lambda$doAiChat$15$comaivoxappactivityRecordInfoActivity(final Integer num) throws Exception {
        this.mAiResultStatusDis = Observable.interval(1L, TimeUnit.SECONDS).flatMap(new Function() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda68
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m2187lambda$doAiChat$12$comaivoxappactivityRecordInfoActivity(num, (Long) obj);
            }
        }).takeUntil((Predicate<? super R>) new Predicate() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda69
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return RecordInfoActivity.lambda$doAiChat$13((AiChatBean.Records) obj);
            }
        }).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda70
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2188lambda$doAiChat$14$comaivoxappactivityRecordInfoActivity((AiChatBean.Records) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$doAiChat$12$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ ObservableSource m2187lambda$doAiChat$12$comaivoxappactivityRecordInfoActivity(Integer num, Long l) throws Exception {
        return new AudioService(this.context).getAiChatDetail(num.intValue());
    }

    static /* synthetic */ boolean lambda$doAiChat$13(AiChatBean.Records records) throws Exception {
        return records.getStatus().intValue() == 1;
    }

    /* JADX INFO: renamed from: lambda$doAiChat$14$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2188lambda$doAiChat$14$comaivoxappactivityRecordInfoActivity(AiChatBean.Records records) throws Exception {
        if (records.getStatus().intValue() == 1) {
            this.transcribeFragment.onAiGenerateFinish(records.getId().intValue(), records.getAnswer());
            this.mBinding.clAiSummary.setVisibility(0);
        }
    }

    /* JADX INFO: renamed from: lambda$doAiChat$17$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2191lambda$doAiChat$17$comaivoxappactivityRecordInfoActivity(Throwable th) throws Exception {
        if (BaseDataHandle.getIns().getCode() == Constant.SeverErrorCode.AUDIO_AI_SUMMARY_LIMIT.code) {
            this.transcribeFragment.onGenerationLimit();
            if (DataHandle.getIns().isVip()) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.ai_chat_limit_reached));
            } else {
                DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.get_vip_membership), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda43
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        this.f$0.m2190lambda$doAiChat$16$comaivoxappactivityRecordInfoActivity(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, true, true, C0874R.string.dismiss, C0874R.string.confirm);
            }
        } else {
            this.transcribeFragment.onGenerationInterrupt();
            AppUtils.checkHttpCode(this.context);
        }
        this.mBinding.clAiSummary.setVisibility(0);
    }

    /* JADX INFO: renamed from: lambda$doAiChat$16$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2190lambda$doAiChat$16$comaivoxappactivityRecordInfoActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        AppUtils.jumpToPurchase(this, false);
    }

    private void initSeekbar() {
        this.mBinding.tvRecordTimeTotal.setText(DateUtil.numberToTimeInDetail(this.mTotalTime));
        this.mBinding.sbProgress.setMax(this.mTotalTime * 1000);
        this.mBinding.sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.aivox.app.activity.RecordInfoActivity.4
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                RecordInfoActivity.this.mFromUser = z;
                RecordInfoActivity.this.mStartWaveTime = i;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
                LogUtil.m334d("--onStartTrackingTouch--");
                RecordInfoActivity.this.audioPause();
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                LogUtil.m334d("--onStopTrackingTouch--");
                if (RecordInfoActivity.this.mFromUser) {
                    RecordInfoActivity recordInfoActivity = RecordInfoActivity.this;
                    recordInfoActivity.onTransClick(recordInfoActivity.mStartWaveTime, false);
                }
            }
        });
    }

    private void initFragment() {
        if (this.mAudioInfoBean != null) {
            LogUtil.m338i("trans1:" + this.mAudioInfoBean.getIsTrans());
            RecordTranscribeFragment recordTranscribeFragment = this.transcribeFragment;
            if (recordTranscribeFragment == null) {
                RecordTranscribeFragment recordTranscribeFragment2 = RecordTranscribeFragment.getInstance(this.mAudioInfoBean, this.mAudioBg, this.mIsLocal);
                this.transcribeFragment = recordTranscribeFragment2;
                recordTranscribeFragment2.setInteractionListener(new C07905());
            } else {
                recordTranscribeFragment.setTrans(this.mAudioInfoBean.getIsTrans());
            }
            RecordTranscribeFragment recordTranscribeFragment3 = this.transcribeFragment;
            if (recordTranscribeFragment3 == null || recordTranscribeFragment3.isAdded()) {
                return;
            }
            FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransactionBeginTransaction.add(C0726R.id.fragment_container, this.transcribeFragment);
            fragmentTransactionBeginTransaction.show(this.transcribeFragment);
            fragmentTransactionBeginTransaction.commitAllowingStateLoss();
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.RecordInfoActivity$5 */
    class C07905 implements RecordTranscribeFragment.OnFragmentInteractionListener {
        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void refreshBar(int i) {
        }

        C07905() {
        }

        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void transClick(int i, boolean z) {
            RecordInfoActivity.this.onTransClick(i, z);
        }

        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void transClickBGED(int i, int i2, boolean z, boolean z2, boolean z3) {
            RecordInfoActivity.this.onTransClickBGED(i, i2, z, z2, z3);
        }

        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void showItemPop(Transcribe transcribe, int i, View view2) {
            RecordInfoActivity.this.currentEditPos = i;
            RecordInfoActivity.this.curVideoTransId = transcribe.getId();
            RecordInfoActivity.this.mCurPicCount = 0;
            boolean z = false;
            for (Transcribe transcribe2 : RecordInfoActivity.this.transcribeList) {
                RecordInfoActivity.this.mCurPicCount += transcribe2.getImageList().size();
                if (!BaseStringUtil.isEmpty(transcribe.getSpeaker())) {
                    z = true;
                }
            }
            RecordInfoActivity recordInfoActivity = RecordInfoActivity.this;
            recordInfoActivity.mAudioTxtLongClickPop = AudioTxtLongClickPopup.create(recordInfoActivity.context, false);
            RecordInfoActivity.this.mAudioTxtLongClickPop.show(i, RecordInfoActivity.this.mCurPicCount, RecordInfoActivity.this.mBinding.clMain, RecordInfoActivity.this.mBinding.clMain.getBottom());
            RecordInfoActivity.this.changeListMode(1);
            RecordInfoActivity.this.mAudioTxtLongClickPop.changeMultiSpeakerMode(z);
            RecordInfoActivity.this.mAudioTxtLongClickPop.hideItem();
            RecordInfoActivity.this.mAudioTxtLongClickPop.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.aivox.app.activity.RecordInfoActivity$5$$ExternalSyntheticLambda0
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    this.f$0.m2214lambda$showItemPop$0$comaivoxappactivityRecordInfoActivity$5();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$showItemPop$0$com-aivox-app-activity-RecordInfoActivity$5, reason: not valid java name */
        /* synthetic */ void m2214lambda$showItemPop$0$comaivoxappactivityRecordInfoActivity$5() {
            RecordInfoActivity.this.transcribeFragment.setAutoScroll(true);
        }

        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void stopAudioWhenScroll() {
            RecordInfoActivity.this.audioPause();
        }

        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void loadSuccessTranscribe(List<Transcribe> list, boolean z, boolean z2) {
            RecordInfoActivity.this.transcribeList.clear();
            RecordInfoActivity.this.transcribeList.addAll(list);
            if (z) {
                RecordInfoActivity.this.audioStop();
            }
            if (RecordInfoActivity.this.transcribeList.isEmpty()) {
                return;
            }
            RecordInfoActivity recordInfoActivity = RecordInfoActivity.this;
            recordInfoActivity.hasTranslated = BaseStringUtil.isNotEmpty(((Transcribe) recordInfoActivity.transcribeList.get(0)).getTranslate());
        }

        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void refreshAutoScroll() {
            if (RecordInfoActivity.this.mAudioTxtLongClickPop == null || !RecordInfoActivity.this.mAudioTxtLongClickPop.isShowing()) {
                RecordInfoActivity.this.transcribeFragment.setAutoScroll(true);
            }
        }

        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void updateCurId(int i) {
            RecordInfoActivity.this.curVideoTransId = i;
        }

        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void switchContent(int i) {
            RecordInfoActivity.this.mTabIndex = i;
            RecordInfoActivity.this.mBinding.clAiSummary.setVisibility(i == 1 ? 0 : 8);
            RecordInfoActivity.this.mBinding.clBottom.setVisibility(RecordInfoActivity.this.mTabIndex == 0 ? 0 : 8);
            RecordInfoActivity.this.mBinding.ivSearch.setVisibility(RecordInfoActivity.this.mTabIndex != 0 ? 8 : 0);
            ImageView imageView = RecordInfoActivity.this.mBinding.ivOperateAi;
            int unused = RecordInfoActivity.this.mTabIndex;
            imageView.setVisibility(8);
            RecordInfoActivity recordInfoActivity = RecordInfoActivity.this;
            BarUtils.setNavBarColor(recordInfoActivity, recordInfoActivity.getColor(recordInfoActivity.mTabIndex == 0 ? C0874R.color.bg_color_popup_bar : C0874R.color.bg_color_secondary));
        }

        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void showSummaryLayout(boolean z) {
            RecordInfoActivity.this.mBinding.clAiSummary.setVisibility(z ? 0 : 8);
        }

        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void doSummaryEdit(AiChatBean.Records records) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.KEY_IDS, records.getId().intValue());
            bundle.putString("data", records.getAnswer());
            ARouterUtils.startWithActivity(RecordInfoActivity.this, MainAction.AI_CHAT_EDIT, bundle, 101);
        }

        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void regenerateSummary(AiChatBean.Records records) {
            RecordInfoActivity.this.mLastQuestion = records.getQuestion();
            RecordInfoActivity.this.doAiChat(records.getType().intValue(), true);
        }

        @Override // com.aivox.app.fragment.RecordTranscribeFragment.OnFragmentInteractionListener
        public void quitSearchMode() {
            RecordInfoActivity.this.toSearch(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: syncAndDoNext, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void m209x69b3b3a2(final String str, final long j, final String str2) {
        if (this.mAudioInfoBean.getFileState() == 3) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(this.mAudioInfoBean.getId()));
            new AudioService(this.context).syncFileFromRecycleBin(arrayList).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda45
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m204x210e7408(str, j, obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda46
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m206xb9ef675f(str, j, str2, (Throwable) obj);
                }
            });
            return;
        }
        new AudioService(this).syncFile(this.mAudioInfoBean.getId(), str, FileUtils.getFileSizeKb(str2)).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda47
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m207x9f30d620(str, j, obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda48
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m210x4ef52263(j, str, str2, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$syncAndDoNext$18$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m204x210e7408(String str, long j, Object obj) throws Exception {
        EventBus.getDefault().post(new EventBean(50));
        this.mAudioInfoBean.getAudioInfo().setAudioUrl(str);
        this.mAudioInfoBean.setFileTime((int) j);
        this.mAudioInfoBean.setFileState(0);
        this.mTotalTime = this.mAudioInfoBean.getFileTime();
        doNextByStep();
    }

    /* JADX INFO: renamed from: lambda$syncAndDoNext$20$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m206xb9ef675f(final String str, final long j, final String str2, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda5
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m205x64fe2c9(str, j, str2);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$syncAndDoNext$21$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m207x9f30d620(String str, long j, Object obj) throws Exception {
        EventBus.getDefault().post(new EventBean(50));
        this.mAudioInfoBean.getAudioInfo().setAudioUrl(str);
        this.mAudioInfoBean.setFileTime((int) j);
        this.mTotalTime = this.mAudioInfoBean.getFileTime();
        doNextByStep();
    }

    /* JADX INFO: renamed from: lambda$syncAndDoNext$24$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m210x4ef52263(final long j, final String str, final String str2, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            if (BaseDataHandle.getIns().getCode() == Constant.SeverErrorCode.THE_AUDIO_ALREADY_SYNC.code) {
                new AudioService(this.context).recordDetails(this.mAudioId).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda77
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f$0.m208x847244e1(j, (AudioInfoBean) obj);
                    }
                });
                return;
            } else {
                AppUtils.checkHttpCode(this);
                return;
            }
        }
        AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda82
            @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
            public final void callback() {
                this.f$0.m209x69b3b3a2(str, j, str2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$syncAndDoNext$22$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m208x847244e1(long j, AudioInfoBean audioInfoBean) throws Exception {
        this.mAudioInfoBean = audioInfoBean;
        audioInfoBean.setFileTime((int) j);
        this.mTotalTime = this.mAudioInfoBean.getFileTime();
        doNextByStep();
    }

    private void doNextByStep() {
        int i = this.nextStep;
        if (i == 1) {
            reqTranscribe();
        } else if (i == 2) {
            click2ShowSelectTranslateView();
        } else if (i == 3) {
            showMoreOperateView();
        } else if (i == 5) {
            doAppShare();
        } else if (i == 6) {
            doAudioExport();
        }
        this.nextStep = 0;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (intent != null) {
            intent.putExtra("pos", this.currentEditPos);
        }
        if (i == 100 && i2 == -1) {
            this.transcribeFragment.updateListSummaryData(intent.getIntegerArrayListExtra(Constant.KEY_IDS), intent.getStringArrayListExtra("data"));
            this.mNoteMarkCount = intent.getIntegerArrayListExtra(Constant.KEY_IDS).size();
            updateCountStatus();
            return;
        }
        if (i == 101 && i2 == -1) {
            this.transcribeFragment.updateChatContent(intent.getIntExtra(Constant.KEY_IDS, -1), intent.getStringExtra("data"));
            return;
        }
        if (i != 188) {
            this.transcribeFragment.onActivityResult(i, i2, intent);
            return;
        }
        if (i2 == -1) {
            ArrayList<LocalMedia> arrayListObtainSelectorList = PictureSelector.obtainSelectorList(intent);
            for (int i3 = 0; i3 < arrayListObtainSelectorList.size(); i3++) {
                String availablePath = arrayListObtainSelectorList.get(i3).getAvailablePath();
                if (isFinishing() || isDestroyed()) {
                    return;
                }
                LogUtil.m336e("压缩成功后调用，返回压缩后的图片文件");
                LogUtil.m338i("file.size:" + FileUtils.getFileSize(availablePath));
                LogUtil.m338i("path:" + availablePath);
                uploadImg(availablePath, this.currentEditPos, this.curVideoTransId);
                changeListMode(0);
            }
        }
    }

    private void uploadImg(String str, int i, final int i2) {
        if (FileUtils.isFileExist(str)) {
            CommonUploadManager.getInstance().startUpload(this.context, i, str, this.userInfo.getUuid(), DateUtil.getCurDate(DateUtil.YYYYMMDD), new OnUploadListener() { // from class: com.aivox.app.activity.RecordInfoActivity.6
                @Override // com.aivox.common.http.oss.OnUploadListener
                public void onProgress(int i3, long j, long j2, int i4) {
                    LogUtil.m338i("上传中:" + i3 + "  " + i4 + "% " + j + "/" + j2);
                }

                @Override // com.aivox.common.http.oss.OnUploadListener
                public void onSuccess(int i3, String str2, String str3, long j) {
                    LogUtil.m338i("上传成功:" + i3 + "  " + str2 + "   " + str3);
                    RecordInfoActivity.this.postImgTag(i2, str3, BaseStringUtil.bToKb(j));
                }

                @Override // com.aivox.common.http.oss.OnUploadListener
                public void onFailure(int i3) {
                    LogUtil.m338i("上传失败：" + i3);
                }
            }, Constant.TYPE_AUDIO_IMG);
        } else {
            LogUtil.m338i("文件不存在");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postImgTag(final int i, final String str, int i2) {
        new AudioService(this).addImgTag(this.mAudioId, i, str, i2).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2206lambda$postImgTag$25$comaivoxappactivityRecordInfoActivity(i, str, (Integer) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$postImgTag$25$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2206lambda$postImgTag$25$comaivoxappactivityRecordInfoActivity(int i, String str, Integer num) throws Exception {
        this.transcribeFragment.refreshListAfterChangeImgTag(i, new Transcribe.TagImgBean(num.intValue(), str), true);
    }

    /* JADX INFO: renamed from: reqTranslateAll, reason: merged with bridge method [inline-methods] */
    public void m192x775f260f(final int i) {
        DialogUtils.showLoadingDialog(this, "");
        new AudioService(this).translateAll(this.mAudioInfoBean.getId(), i).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda71
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m191x921db74e(obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda72
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m193x5ca094d0(i, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqTranslateAll$26$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m191x921db74e(Object obj) throws Exception {
        DialogUtils.hideLoadingDialog();
        pollingTranslationStatus(this.mAudioInfoBean.getId());
        RxAudioPlayer.getInstance().stopPlay();
        this.isPlaying = false;
        refreshBtmNotify(true, getString(C0874R.string.record_info_on_translate));
    }

    /* JADX INFO: renamed from: lambda$reqTranslateAll$28$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m193x5ca094d0(final int i, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        LogUtil.m338i("translate_e:" + th.getLocalizedMessage());
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda22
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m192x775f260f(i);
                }
            });
        }
    }

    public void pollingTranslationStatus(final int i) {
        this.mTransStatusDis = Observable.interval(1L, TimeUnit.SECONDS).flatMap(new Function() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda32
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m179x6ddc19c8(i, (Long) obj);
            }
        }).takeUntil((Predicate<? super R>) new Predicate() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda34
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return RecordInfoActivity.lambda$pollingTranslationStatus$30((Integer) obj);
            }
        }).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda35
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m181xebfe7be0((Integer) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$pollingTranslationStatus$29$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ ObservableSource m179x6ddc19c8(int i, Long l) throws Exception {
        return new AudioService(this.context).getTransStatus(i);
    }

    static /* synthetic */ boolean lambda$pollingTranslationStatus$30(Integer num) throws Exception {
        return num.intValue() == 1;
    }

    /* JADX INFO: renamed from: lambda$pollingTranslationStatus$32$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m181xebfe7be0(Integer num) throws Exception {
        if (num.intValue() == 1) {
            RxAudioPlayer.getInstance().stopPlay();
            this.isPlaying = false;
            this.mHandler.sendEmptyMessage(4);
            m185x79e369d0();
            this.mHandler.postDelayed(new Runnable() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda78
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m180x6bd0d1f();
                }
            }, 1000L);
        }
    }

    /* JADX INFO: renamed from: lambda$pollingTranslationStatus$31$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m180x6bd0d1f() {
        refreshBtmNotify(false, "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: reqRecordDetails, reason: merged with bridge method [inline-methods] */
    public void m185x79e369d0() {
        if (this.userInfo == null) {
            this.userInfo = this.manager.getUserInfo();
        }
        new AudioService(this).recordDetails(this.mAudioId).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m184x94a1fb0f((AudioInfoBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m186x5f24d891((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqRecordDetails$34$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m184x94a1fb0f(AudioInfoBean audioInfoBean) throws Exception {
        StringUtil.updateFileTime(this.mLocalFileDbManager, this.userInfo.getUuid(), audioInfoBean);
        this.mAudioInfoBean = audioInfoBean;
        if (audioInfoBean.getAudioInfo() != null) {
            int iIntValue = audioInfoBean.getAudioInfo().getSource().intValue();
            this.mFromLang = iIntValue;
            this.s2tType = MyEnum.TRANSLATE_LANGUAGE.getLanguage(iIntValue).alias;
            this.mAvatarStyle = audioInfoBean.getAudioInfo().getSpeakerAvatarStyle().intValue();
        }
        this.mNoteMarkCount = audioInfoBean.getMarkCount();
        updateCountStatus();
        if (audioInfoBean.getAudioInfo() == null) {
            return;
        }
        this.mBinding.ivNoteMark.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m183xaf608c4e(view2);
            }
        });
        this.mTotalTime = audioInfoBean.isLocalAudio() ? (int) FileUtils.getAudioFileVoiceTime(audioInfoBean.getLocalPath()) : audioInfoBean.getFileTime();
        this.mAudioUrl = audioInfoBean.getAudioUrl();
        initSeekbar();
        if (FileUtils.isFileExist(audioInfoBean.getLocalPath())) {
            this.mAudioUrl = audioInfoBean.getLocalPath();
        }
        if (this.isFirst) {
            this.isFirst = false;
            this.mStartWaveTime = 0;
        }
        refreshTransformResult();
        if (audioInfoBean.getIsSeparate() == 1) {
            this.mDoSeparate = true;
            refreshBtmNotify(true, getString(C0874R.string.record_info_on_separate));
            Message message = new Message();
            message.what = 1008;
            message.arg1 = this.mAudioInfoBean.getId();
            this.mHandler.sendMessageDelayed(message, 4000L);
            this.transcribeProgress = 0;
            this.mHandler.sendEmptyMessageDelayed(1002, 4000L);
        }
    }

    /* JADX INFO: renamed from: lambda$reqRecordDetails$33$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m183xaf608c4e(View view2) {
        jumpToNotesMarkPage();
    }

    /* JADX INFO: renamed from: lambda$reqRecordDetails$36$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m186x5f24d891(Throwable th) throws Exception {
        LogUtil.m336e("reqRecordDetails error :" + th.getLocalizedMessage());
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda6
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m185x79e369d0();
                }
            });
        }
    }

    private void jumpToNotesMarkPage() {
        if (this.mNoteMarkCount == 0) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.KEY_FLAG, 0);
            ARouterUtils.startWithContext(this.context, MainAction.FUNCTION_GUIDE, bundle);
        } else {
            Bundle bundle2 = new Bundle();
            bundle2.putInt(Constant.KEY_IDS, this.mAudioId);
            ARouterUtils.startWithActivity(this, MainAction.NOTE_MARK_LIST, bundle2, 100);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCountStatus() {
        this.mBinding.tvNoteMark.setVisibility(this.mNoteMarkCount == 0 ? 8 : 0);
    }

    private void refreshTransformResult() {
        int isTrans = this.mAudioInfoBean.getIsTrans();
        initFragment();
        if (isTrans == 2) {
            DialogUtils.showLoadingDialog(this, getString(C0874R.string.file_transcribe_ing) + "...");
            this.mHandler.sendEmptyMessageDelayed(1002, 1000L);
        } else if (isTrans == 3) {
            ToastUtil.showTextToast(this, Integer.valueOf(C0874R.string.file_transcribe_fail));
        }
    }

    public void checkBeforeUpload() {
        if (FileUtils.isFileExist(this.mAudioInfoBean.getLocalPath())) {
            if (((Boolean) SPUtil.get(SPUtil.IS_CLOUD_FULL, false)).booleanValue()) {
                DialogUtils.showDialogWithBtnIds(this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.me_cloud_full_sysc_unable), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda52
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        RecordInfoActivity.lambda$checkBeforeUpload$37(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda53
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        RecordInfoActivity.lambda$checkBeforeUpload$38(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, false, true, C0874R.string.cancel, C0874R.string.me_to_clean_cloud);
                return;
            } else {
                m2208lambda$reqFileSize$64$comaivoxappactivityRecordInfoActivity(this.mAudioInfoBean.getLocalPath());
                return;
            }
        }
        ToastUtil.showTextToast(this, Integer.valueOf(C0874R.string.toast_file_does_not_sync_or_deleted));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reqTranscribe() {
        RxAudioPlayer.getInstance().stopPlay();
        this.isPlaying = false;
        if (this.mDoSeparate) {
            new AudioService(this).speechToText(this.mAudioInfoBean.getId(), this.s2tType, true, this.mSpeakerNum).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m189x37b56b6e(obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda11
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m190xeb54f004((Throwable) obj);
                }
            });
        } else {
            AudioTransModel.getInstance().startTrans(this.context, this.mAudioId, this.mFromLang);
        }
    }

    /* JADX INFO: renamed from: lambda$reqTranscribe$39$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m189x37b56b6e(Object obj) throws Exception {
        RecordTranscribeFragment recordTranscribeFragment = this.transcribeFragment;
        if (recordTranscribeFragment != null) {
            this.mReTranscribing = true;
            recordTranscribeFragment.setReTranscribingState(true);
        }
        refreshBtmNotify(true, getString(C0874R.string.record_info_on_separate));
        this.mNoteMarkCount = 0;
        updateCountStatus();
        this.mAudioInfoBean.setIsTrans(MyEnum.TRANS_STATE.ON_TRANS.type);
        Message message = new Message();
        message.what = 1008;
        message.arg1 = this.mAudioInfoBean.getId();
        this.mHandler.sendMessageDelayed(message, 4000L);
        this.transcribeProgress = 0;
        this.mHandler.sendEmptyMessageDelayed(1002, 4000L);
    }

    /* JADX INFO: renamed from: lambda$reqTranscribe$40$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m190xeb54f004(Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda66
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.reqTranscribe();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshBtmNotify(final boolean z, final String str) {
        runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m182x8b3f0bb5(str, z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$refreshBtmNotify$41$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m182x8b3f0bb5(String str, boolean z) {
        this.mBinding.tvMsg.setText(str);
        if (z) {
            this.mBinding.llBtmNotify.setVisibility(0);
            if (this.mAnim == null) {
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mBinding.ivLoading, Key.ROTATION, 0.0f, 360.0f);
                this.mAnim = objectAnimatorOfFloat;
                objectAnimatorOfFloat.setDuration(800L);
                this.mAnim.setInterpolator(new LinearInterpolator());
                this.mAnim.setRepeatCount(-1);
                this.mAnim.start();
                return;
            }
            return;
        }
        ObjectAnimator objectAnimator = this.mAnim;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mAnim = null;
        }
        this.mBinding.llBtmNotify.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reqSeparateProgress() {
        new AudioService(this).separateProgress(this.mAudioInfoBean.getId()).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda30
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m187x7eac5dab((Integer) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda31
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m188x63edcc6c((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqSeparateProgress$42$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m187x7eac5dab(Integer num) throws Exception {
        int iIntValue = num.intValue();
        this.transcribeProgress = iIntValue;
        if (iIntValue == 100) {
            refreshBtmNotify(false, "");
            EventBus.getDefault().post(new EventBean(50));
        }
        this.mHandler.sendEmptyMessageDelayed(1002, 4000L);
    }

    /* JADX INFO: renamed from: lambda$reqSeparateProgress$43$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m188x63edcc6c(Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (BaseDataHandle.getIns().getCode() == Constant.SeverErrorCode.ASR_SEPARATE_ERROR.code) {
            ToastUtil.showTextToast(this, Integer.valueOf(C0874R.string.server_error_code_3315));
            this.mAudioInfoBean.setIsTrans(MyEnum.TRANS_STATE.TRANS_FAIL.type);
            refreshBtmNotify(false, "");
        } else if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda39
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.reqSeparateProgress();
                }
            });
        }
    }

    private void showMoreOperateView() {
        if (isFinishing()) {
            return;
        }
        if (this.mTabIndex == 0) {
            List<Transcribe> list = this.transcribeList;
            AudioOperateView audioOperateView = new AudioOperateView(this, list == null || list.isEmpty());
            audioOperateView.setItemClickListener(new ViewOnClickListenerC07927());
            this.moreOperateDialog = DialogUtils.showBottomSheetDialog(this.context, audioOperateView);
            return;
        }
        String aiSummaryContent = this.transcribeFragment.getAiSummaryContent();
        if (aiSummaryContent.isEmpty()) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.no_ai_summary));
            return;
        }
        AiSummaryOperateView aiSummaryOperateView = new AiSummaryOperateView(this, aiSummaryContent);
        aiSummaryOperateView.setItemClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (AntiShake.check(this)) {
                    return;
                }
                if (view2.getId() == C0726R.id.tv_summary_select) {
                    RecordInfoActivity.this.transcribeFragment.switchAiChatListMode(1);
                    RecordInfoActivity.this.mBinding.rlEdit.setVisibility(0);
                    RecordInfoActivity.this.mBinding.tvEditTitle.setText("");
                    RecordInfoActivity.this.mBinding.rlTop.setVisibility(8);
                    RecordInfoActivity.this.mBinding.tvSave.setVisibility(4);
                    if (RecordInfoActivity.this.mAiChatDeletePop.isShowing()) {
                        return;
                    } else {
                        RecordInfoActivity.this.mAiChatDeletePop.show(RecordInfoActivity.this.mBinding.clMain, RecordInfoActivity.this.mBinding.clMain.getBottom());
                    }
                }
                if (RecordInfoActivity.this.moreOperateDialog == null || !RecordInfoActivity.this.moreOperateDialog.isShowing()) {
                    return;
                }
                RecordInfoActivity.this.moreOperateDialog.dismiss();
            }
        });
        this.moreOperateDialog = DialogUtils.showBottomSheetDialog(this.context, aiSummaryOperateView);
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.RecordInfoActivity$7 */
    class ViewOnClickListenerC07927 implements View.OnClickListener {
        ViewOnClickListenerC07927() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            int i;
            int i2;
            if (AntiShake.check(this)) {
                return;
            }
            int id = view2.getId();
            if (id == C0726R.id.tv_edit_file) {
                if (RecordInfoActivity.this.mAudioInfoBean.getIsTrans() == MyEnum.TRANS_STATE.TRANSCRIBED.type) {
                    RecordInfoActivity.this.transcribeFragment.selectFirst();
                }
            } else if (id == C0726R.id.tv_share_to_user) {
                RecordInfoActivity.this.click2AppShare();
            } else if (id == C0726R.id.tv_copy_link) {
                RecordInfoActivity.this.click2ShareAudioUrl();
            } else if (id == C0726R.id.tv_export_audio) {
                RecordInfoActivity.this.click2ExportAudio();
            } else if (id == C0726R.id.tv_export_file) {
                RecordInfoActivity.this.click2ShareTxt();
            } else if (id == C0726R.id.tv_delete) {
                if (RecordInfoActivity.this.mAudioInfoBean == null) {
                    return;
                }
                boolean zIsLocalAudio = RecordInfoActivity.this.mAudioInfoBean.isLocalAudio();
                final boolean z = !zIsLocalAudio;
                Context context = RecordInfoActivity.this.context;
                Integer numValueOf = Integer.valueOf(C0874R.string.reminder);
                if (!RecordInfoActivity.this.mIsLocal) {
                    i = C0874R.string.audio_delete_cloud_remind;
                } else if (!zIsLocalAudio) {
                    i = C0874R.string.audio_delete_remind;
                } else {
                    i = C0874R.string.audio_delete_remind_sync;
                }
                Integer numValueOf2 = Integer.valueOf(i);
                DialogBuilder.DialogButtonClickListener dialogButtonClickListener = new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$7$$ExternalSyntheticLambda0
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context2, DialogBuilder dialogBuilder, Dialog dialog, int i3, int i4, EditText editText) {
                        this.f$0.m2215lambda$onClick$0$comaivoxappactivityRecordInfoActivity$7(context2, dialogBuilder, dialog, i3, i4, editText);
                    }
                };
                DialogBuilder.DialogButtonClickListener dialogButtonClickListener2 = new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$7$$ExternalSyntheticLambda1
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context2, DialogBuilder dialogBuilder, Dialog dialog, int i3, int i4, EditText editText) {
                        this.f$0.m2216lambda$onClick$1$comaivoxappactivityRecordInfoActivity$7(z, context2, dialogBuilder, dialog, i3, i4, editText);
                    }
                };
                int i3 = !RecordInfoActivity.this.mIsLocal ? C0874R.string.delete : C0874R.string.continue_delete;
                if (RecordInfoActivity.this.mIsLocal) {
                    if (!zIsLocalAudio) {
                        i2 = C0874R.string.cancel;
                    } else {
                        i2 = C0874R.string.device_sync;
                    }
                } else if (DataHandle.getIns().isVip()) {
                    i2 = C0874R.string.cancel;
                } else {
                    i2 = C0874R.string.join_vip;
                }
                DialogUtils.showDeleteDialog(context, numValueOf, numValueOf2, dialogButtonClickListener, dialogButtonClickListener2, true, false, i3, i2, 0);
            }
            if (RecordInfoActivity.this.moreOperateDialog == null || !RecordInfoActivity.this.moreOperateDialog.isShowing()) {
                return;
            }
            RecordInfoActivity.this.moreOperateDialog.dismiss();
        }

        /* JADX INFO: renamed from: lambda$onClick$0$com-aivox-app-activity-RecordInfoActivity$7, reason: not valid java name */
        /* synthetic */ void m2215lambda$onClick$0$comaivoxappactivityRecordInfoActivity$7(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            if (RecordInfoActivity.this.mAudioInfoBean != null) {
                RecordInfoActivity recordInfoActivity = RecordInfoActivity.this;
                recordInfoActivity.m195x4dd6ce3b(recordInfoActivity.mAudioInfoBean.getId());
            }
        }

        /* JADX INFO: renamed from: lambda$onClick$1$com-aivox-app-activity-RecordInfoActivity$7, reason: not valid java name */
        /* synthetic */ void m2216lambda$onClick$1$comaivoxappactivityRecordInfoActivity$7(boolean z, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            if (RecordInfoActivity.this.mIsLocal) {
                if (z) {
                    return;
                }
                RecordInfoActivity.this.checkBeforeUpload();
            } else {
                if (DataHandle.getIns().isVip()) {
                    return;
                }
                AppUtils.jumpToPurchase(RecordInfoActivity.this, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void click2AppShare() {
        if (BaseStringUtil.isNotEmpty(this.mAudioInfoBean.getAudioUrl()) && this.mAudioInfoBean.getFileState() == 0) {
            doAppShare();
            return;
        }
        BottomSheetDialog bottomSheetDialog = this.moreOperateDialog;
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            this.moreOperateDialog.dismiss();
        }
        this.nextStep = 5;
        checkBeforeUpload();
    }

    private void doAppShare() {
        AudioInAppShareView audioInAppShareView = new AudioInAppShareView(this.context);
        audioInAppShareView.setMyOnClickListener(new AudioInAppShareView.MyOnClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda79
            @Override // com.aivox.app.view.AudioInAppShareView.MyOnClickListener
            public final void toShare(int i, String str) {
                this.f$0.m171x4cec906d(i, str);
            }
        });
        DialogUtils.showBottomSheetDialog(this.context, audioInAppShareView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void click2ExportAudio() {
        if (BaseStringUtil.isNotEmpty(this.mAudioInfoBean.getAudioUrl()) && this.mAudioInfoBean.getFileState() == 0) {
            doAudioExport();
            return;
        }
        BottomSheetDialog bottomSheetDialog = this.moreOperateDialog;
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            this.moreOperateDialog.dismiss();
        }
        this.nextStep = 6;
        checkBeforeUpload();
    }

    private void doAudioExport() {
        new Thread(new Runnable() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m168xc843fe1c();
            }
        }).start();
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.RecordInfoActivity$9 */
    class C07949 implements AudioDownloadUtil.IAudioDownloadListener {
        C07949() {
        }

        @Override // com.aivox.app.test.denoise.AudioDownloadUtil.IAudioDownloadListener
        public void onPrepare(String str) {
            LogUtil.m338i("准备下载:" + str);
            RecordInfoActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.RecordInfoActivity$9$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.in_progress));
                }
            });
        }

        @Override // com.aivox.app.test.denoise.AudioDownloadUtil.IAudioDownloadListener
        public void onError(String str) {
            ToastUtil.showShort(str);
        }

        /* JADX INFO: renamed from: lambda$onComplete$1$com-aivox-app-activity-RecordInfoActivity$9, reason: not valid java name */
        /* synthetic */ void m2217lambda$onComplete$1$comaivoxappactivityRecordInfoActivity$9(File file) {
            ToastUtil.showLong(RecordInfoActivity.this.getString(C0874R.string.success) + ":" + file.getAbsolutePath());
        }

        @Override // com.aivox.app.test.denoise.AudioDownloadUtil.IAudioDownloadListener
        public void onComplete(final File file) {
            RecordInfoActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.RecordInfoActivity$9$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2217lambda$onComplete$1$comaivoxappactivityRecordInfoActivity$9(file);
                }
            });
        }

        @Override // com.aivox.app.test.denoise.AudioDownloadUtil.IAudioDownloadListener
        public void onProgress(long j, long j2) {
            LogUtil.m338i("下载进度:" + j + "/" + j2);
        }
    }

    /* JADX INFO: renamed from: lambda$doAudioExport$44$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m168xc843fe1c() {
        new AudioDownloadUtil().start(this.context, this.mAudioInfoBean.getAudioUrl(), new C07949());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        this.userInfo = this.manager.getUserInfo();
        this.mMaxPicCount = DataHandle.getIns().getFunctionBean().getImageInsertLimit().intValue();
        this.mPicInsertEnable = DataHandle.getIns().getFunctionBean().getImageInsert().booleanValue();
    }

    private void showAiOperateView() {
        if (this.mAudioInfoBean == null) {
            return;
        }
        AudioOperateAIView audioOperateAIView = new AudioOperateAIView(this, this.mFromLang);
        audioOperateAIView.setItemClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m201xea3dceb2(view2);
            }
        });
        this.audioOperateAIDialog = DialogUtils.showBottomSheetDialog(this.context, audioOperateAIView);
    }

    /* JADX INFO: renamed from: lambda$showAiOperateView$50$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m201xea3dceb2(View view2) {
        if (AntiShake.check(this) || otherActionInProgress()) {
            return;
        }
        int id = view2.getId();
        if (id == C0726R.id.tv_translate) {
            if (!this.hasTranslated) {
                click2ShowSelectTranslateView();
            } else {
                DialogUtils.showDialogWithBtnIds(this, Integer.valueOf(C0874R.string.tips_re_translate), Integer.valueOf(C0874R.string.tips_retranslate_override), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda60
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        RecordInfoActivity.lambda$showAiOperateView$45(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda61
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        this.f$0.m198x86d9fdd9(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, true, true, C0874R.string.tips_cancel, C0874R.string.tips_continue);
            }
            this.audioOperateAIDialog.dismiss();
        } else if (id == C0726R.id.tv_transcribe) {
            if (this.mAudioInfoBean.getIsTrans() == MyEnum.TRANS_STATE.ON_TRANS.type) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.record_info_on_transcribe));
                return;
            }
            this.mDoSeparate = false;
            if (this.mAudioInfoBean.getIsTrans() != 1) {
                click2ShowTranscribeView();
            } else {
                DialogUtils.showDialogWithBtnIds(this, Integer.valueOf(C0874R.string.tips_re_transcribe), Integer.valueOf(C0874R.string.tips_retranscribe_override), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda62
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        RecordInfoActivity.lambda$showAiOperateView$47(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda63
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        this.f$0.m199x515cdb5b(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, true, true, C0874R.string.tips_cancel, C0874R.string.tips_continue);
            }
            this.audioOperateAIDialog.dismiss();
        } else if (id == C0726R.id.tv_ai_speaker) {
            DialogUtils.showBottomSheetDialog(this.context, new SpeakerNumSelectView(this.context, new SpeakerNumSelectView.OnNumSelectListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda64
                @Override // com.aivox.common_ui.SpeakerNumSelectView.OnNumSelectListener
                public final void onNumSelect(int i) {
                    this.f$0.m200x369e4a1c(i);
                }
            }));
        }
        this.audioOperateAIDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$showAiOperateView$46$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m198x86d9fdd9(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        click2ShowSelectTranslateView();
    }

    /* JADX INFO: renamed from: lambda$showAiOperateView$48$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m199x515cdb5b(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        click2ShowTranscribeView();
    }

    /* JADX INFO: renamed from: lambda$showAiOperateView$49$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m200x369e4a1c(int i) {
        if (this.mAudioInfoBean.getIsTrans() == MyEnum.TRANS_STATE.ON_TRANS.type) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.record_info_on_transcribe));
            return;
        }
        this.mDoSeparate = true;
        this.mSpeakerNum = i;
        click2ShowTranscribeView();
    }

    private boolean otherActionInProgress() {
        if (this.mBinding.llBtmNotify.getVisibility() != 0) {
            return false;
        }
        DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.record_info_action_in_progress), null, null, false, true, C0874R.string.cancel, C0874R.string.cancel);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSpeed(float f) {
        toPlayAudio();
        if (this.playStatus == MyEnum.AUDIO_PLAY_STATUS.IDLE) {
            setSpeedSuccess(f);
        } else if (this.mMediaPlayer != null) {
            this.mSpeed = f;
            setAudioSpeed(f);
            this.playStatus = MyEnum.AUDIO_PLAY_STATUS.PLAYING;
            refreshPlayBtn();
        }
    }

    private void setSpeedSuccess(final float f) {
        new Handler().postDelayed(new Runnable() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m197x83ca58b9(f);
            }
        }, 1000L);
    }

    /* JADX INFO: renamed from: lambda$setSpeedSuccess$51$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m197x83ca58b9(float f) {
        if (this.mMediaPlayer != null && this.playStatus != MyEnum.AUDIO_PLAY_STATUS.IDLE) {
            this.mSpeed = f;
            setAudioSpeed(f);
        } else {
            setSpeedSuccess(f);
        }
    }

    public void setAudioSpeed(float f) {
        MediaPlayer mediaPlayer;
        try {
            if (!this.isPlaying || (mediaPlayer = this.mMediaPlayer) == null || mediaPlayer.getDuration() <= 0) {
                return;
            }
            PlaybackParams playbackParams = this.mMediaPlayer.getPlaybackParams();
            playbackParams.setSpeed(f);
            this.mMediaPlayer.setPlaybackParams(playbackParams);
            LogUtil.m338i("speed:" + this.mMediaPlayer.getPlaybackParams().getSpeed());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        if (AntiShake.check(this)) {
            return;
        }
        int id = view2.getId();
        if (id == C0726R.id.iv_search) {
            toSearch(true);
            return;
        }
        if (id == C0726R.id.lly_back) {
            doBack();
            return;
        }
        if (id == C0726R.id.iv_bottom_more) {
            showMoreOperateView();
            return;
        }
        if (id == C0726R.id.iv_operate_ai) {
            showAiOperateView();
            return;
        }
        if (id == C0726R.id.tv_search_cancel) {
            toSearch(false);
            return;
        }
        if (id == C0726R.id.play_start_iv) {
            toPlayAudio();
            return;
        }
        if (id == C0726R.id.play_stop_iv) {
            audioPause();
        } else if (id == C0726R.id.iv_speed) {
            switchSpeed();
        } else if (id == C0726R.id.iv_ai_talk_send) {
            doAiChat(Constant.AI_TYPE_TALK, false);
        }
    }

    private void switchSpeed() {
        if (this.speedList.isEmpty()) {
            this.speedList.add(Float.valueOf(0.75f));
            this.speedList.add(Float.valueOf(1.0f));
            this.speedList.add(Float.valueOf(1.25f));
            this.speedList.add(Float.valueOf(1.75f));
            this.displayList.add(Integer.valueOf(C1034R.drawable.iv_play_speed_0_75));
            this.displayList.add(Integer.valueOf(C1034R.drawable.iv_play_speed_1_0));
            this.displayList.add(Integer.valueOf(C1034R.drawable.iv_play_speed_1_25));
            this.displayList.add(Integer.valueOf(C1034R.drawable.iv_play_speed_1_5));
        }
        int i = this.currentSpeedIndex;
        if (i == 3) {
            this.currentSpeedIndex = 0;
        } else {
            this.currentSpeedIndex = i + 1;
        }
        this.mBinding.ivSpeed.setImageResource(this.displayList.get(this.currentSpeedIndex).intValue());
        setSpeed(this.speedList.get(this.currentSpeedIndex).floatValue());
    }

    private void click2ShowSelectTranslateView() {
        if (this.mAudioInfoBean.getIsTrans() != 1) {
            DialogUtils.showDialogWithBtnIds(this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.file_trans_refuse_no_transcri), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda65
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    RecordInfoActivity.lambda$click2ShowSelectTranslateView$52(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda67
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m167x6c7c6bc9(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, true, true, C0874R.string.tips_cancel, C0874R.string.tips_continue);
        } else {
            showLangSelectDialog(2);
        }
    }

    /* JADX INFO: renamed from: lambda$click2ShowSelectTranslateView$53$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m167x6c7c6bc9(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        click2ShowTranscribeView();
    }

    private void changeAudioSource() {
        LogUtil.m334d("changeAudioSource");
        this.mAudioUrl = this.mAudioInfoBean.getAudioUrl();
        if (FileUtils.isFileExist(this.mAudioInfoBean.getLocalPath())) {
            this.mAudioUrl = this.mAudioInfoBean.getLocalPath();
        }
        audioStop();
        audioPlay();
    }

    public void toShowTransView() {
        click2ShowTranscribeView();
    }

    private void click2ShowTranscribeView() {
        if (this.mAudioInfoBean.getIsTrans() == MyEnum.TRANS_STATE.ON_TRANS.type) {
            if (this.mDoSeparate) {
                reqSeparateProgress();
                return;
            } else {
                AudioTransModel.getInstance().continueTrans(this.context, this.mAudioId);
                return;
            }
        }
        if (AppUtils.fileTransTimeCheck(this, this.mAudioInfoBean, DataHandle.getIns().isVip())) {
            if (this.mDoSeparate) {
                click2SpeechToText();
            } else {
                showLangSelectDialog(1);
            }
        }
    }

    private void showLangSelectDialog(final int i) {
        LanguageUtils.showLangSelectView(this.context, i, false, new LanguageSelectView.LangSelectListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda20
            @Override // com.aivox.common.view.LanguageSelectView.LangSelectListener
            public final void onLangSelected(MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
                this.f$0.m202x8f6feaf1(i, translate_language, translate_language2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$showLangSelectDialog$54$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m202x8f6feaf1(int i, MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
        if (translate_language == MyEnum.TRANSLATE_LANGUAGE.YUE_HK) {
            translate_language = MyEnum.TRANSLATE_LANGUAGE.YUE;
        }
        if (translate_language2 == MyEnum.TRANSLATE_LANGUAGE.YUE_HK) {
            translate_language2 = MyEnum.TRANSLATE_LANGUAGE.YUE;
        }
        if (i == 1) {
            this.s2tType = MyEnum.TRANSLATE_LANGUAGE.getLanguage(translate_language.type).alias;
            this.mFromLang = translate_language.type;
            click2SpeechToText();
            return;
        }
        m192x775f260f(translate_language2.type);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toSearch(boolean z) {
        if (z) {
            this.mBinding.rlTop.setVisibility(8);
            this.mBinding.clBottom.setVisibility(8);
            this.mBinding.clSearch.setVisibility(0);
            KeyboardUtils.showSoftInput(this.mBinding.etSearch);
            this.transcribeFragment.changeMode(3);
            return;
        }
        this.mBinding.rlTop.setVisibility(0);
        this.mBinding.clBottom.setVisibility(0);
        this.mBinding.rlEdit.setVisibility(8);
        this.mBinding.clSearch.setVisibility(8);
        KeyboardUtils.hideSoftInput(this.mBinding.etSearch);
        this.transcribeFragment.changeMode(0);
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void onEventMainThread(EventBean eventBean) {
        super.onEventMainThread(eventBean);
        if (this.mAudioInfoBean == null) {
            return;
        }
        int from = eventBean.getFrom();
        if (from == 75) {
            this.transcribeFragment.refreshListAfterChangeImgTagById(this.curVideoTransId, eventBean.getA());
            return;
        }
        if (from == 77) {
            AudioInfoBean audioInfo = eventBean.getAudioInfo();
            this.mAudioInfoBean = audioInfo;
            this.transcribeFragment.setAudioInfo(audioInfo);
            return;
        }
        if (from == 82) {
            int a = eventBean.getA();
            this.transcribeFragment.switchAiChatListMode(a);
            if (a == 0) {
                this.mBinding.rlEdit.setVisibility(8);
                this.mBinding.rlTop.setVisibility(0);
                return;
            }
            this.mBinding.rlEdit.setVisibility(0);
            this.mBinding.tvEditTitle.setText("");
            this.mBinding.rlTop.setVisibility(8);
            this.mBinding.tvSave.setVisibility(4);
            if (this.mAiChatDeletePop.isShowing()) {
                return;
            }
            this.mAiChatDeletePop.show(this.mBinding.clMain, this.mBinding.clMain.getBottom());
            return;
        }
        if (from != 83) {
            switch (from) {
                case Constant.EVENT.AUDIO_TRANS_START /* 210 */:
                    if (eventBean.getA() == this.mAudioInfoBean.getId()) {
                        LogUtil.m337e("TEMP", "AUDIO_TRANS_PROGRESS " + eventBean.getB());
                        this.mAudioInfoBean.setIsTrans(MyEnum.TRANS_STATE.ON_TRANS.type);
                        RecordTranscribeFragment recordTranscribeFragment = this.transcribeFragment;
                        if (recordTranscribeFragment != null) {
                            this.mReTranscribing = true;
                            recordTranscribeFragment.setReTranscribingState(true);
                        }
                        refreshBtmNotify(true, getString(C0874R.string.record_info_on_transcribe));
                        this.mNoteMarkCount = 0;
                        updateCountStatus();
                    }
                    break;
                case Constant.EVENT.AUDIO_TRANS_PROGRESS /* 211 */:
                    if (eventBean.getA() == this.mAudioInfoBean.getId()) {
                        int b = eventBean.getB();
                        this.transcribeProgress = b;
                        if (b == 100) {
                            refreshBtmNotify(false, "");
                        }
                    }
                    break;
                case 212:
                    if (eventBean.getA() == this.mAudioInfoBean.getId()) {
                        LogUtil.m337e("TEMP", "AUDIO_TRANS_COMPLETE");
                        this.mAudioInfoBean.setIsTrans(MyEnum.TRANS_STATE.TRANSCRIBED.type);
                        refreshBtmNotify(false, "");
                        LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession()).updateTransStatus(1, LocalFileEntityDao.Properties.Uid.m1944eq(this.userInfo.getUuid()), LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(eventBean.getA())));
                        ToastUtil.showShort(Integer.valueOf(C0874R.string.file_transcribe_success));
                        RxAudioPlayer.getInstance().stopPlay();
                        this.isPlaying = false;
                        this.mHandler.sendEmptyMessage(4);
                        new Handler().postDelayed(new Runnable() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda55
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m178x42baa03d();
                            }
                        }, 300L);
                    }
                    break;
                case Constant.EVENT.AUDIO_TRANS_FAILED /* 213 */:
                    if (eventBean.getA() == this.mAudioInfoBean.getId()) {
                        LogUtil.m337e("TEMP", "AUDIO_TRANS_FAILED");
                        this.mAudioInfoBean.setIsTrans(MyEnum.TRANS_STATE.TRANS_FAIL.type);
                        ToastUtil.showLong(eventBean.getS1());
                        DialogUtils.hideLoadingDialog();
                        refreshBtmNotify(false, "");
                    }
                    break;
            }
            return;
        }
        getAiChatStatus();
    }

    /* JADX INFO: renamed from: lambda$onEventMainThread$55$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m178x42baa03d() {
        this.mAvatarStyle = 1;
        this.transcribeFragment.setAvatarStyle(1);
        this.mReTranscribing = false;
        this.transcribeFragment.setReTranscribingState(false);
        this.transcribeFragment.setTrans(this.mAudioInfoBean.getIsTrans());
    }

    private void getAiChatStatus() {
        this.mAiResultStatusDis = Observable.interval(3L, TimeUnit.SECONDS).flatMap(new Function() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda74
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m169x7de4b7d4((Long) obj);
            }
        }).takeUntil((Predicate<? super R>) new Predicate() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda75
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return RecordInfoActivity.lambda$getAiChatStatus$57((AiChatBean.Records) obj);
            }
        }).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda76
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m170x48679556((AiChatBean.Records) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getAiChatStatus$56$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ ObservableSource m169x7de4b7d4(Long l) throws Exception {
        return new AudioService(this.context).getAiChatDetail(this.transcribeFragment.getLatestAiChatId());
    }

    static /* synthetic */ boolean lambda$getAiChatStatus$57(AiChatBean.Records records) throws Exception {
        return records.getStatus().intValue() == 1;
    }

    /* JADX INFO: renamed from: lambda$getAiChatStatus$58$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m170x48679556(AiChatBean.Records records) throws Exception {
        if (records.getStatus().intValue() == 1) {
            this.transcribeFragment.onAiGenerateFinish(records.getId().intValue(), records.getAnswer());
            this.mBinding.clAiSummary.setVisibility(0);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        doBack();
    }

    private void doBack() {
        if (this.listMode != 0) {
            changeListMode(0);
            return;
        }
        if (this.mBinding.rlTop.getVisibility() == 8 && this.mBinding.clSearch.getVisibility() == 0) {
            toSearch(false);
        } else {
            if (this.mAiChatDeletePop.isShowing()) {
                this.mAiChatDeletePop.dismiss();
                return;
            }
            this.mHandler.removeCallbacksAndMessages(null);
            setResult(-1);
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: requestDelete, reason: merged with bridge method [inline-methods] */
    public void m195x4dd6ce3b(final int i) {
        new AudioService(this).deleteFile(String.valueOf(i)).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda36
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m194x9a3749a5(obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda37
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m196x33183cfc(i, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$requestDelete$59$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m194x9a3749a5(Object obj) throws Exception {
        delSuccess();
    }

    /* JADX INFO: renamed from: lambda$requestDelete$61$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m196x33183cfc(final int i, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda42
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m195x4dd6ce3b(i);
                }
            });
        }
    }

    private void delSuccess() {
        String string;
        if (this.mIsLocal) {
            deleteLocalFile();
        } else {
            this.mLocalFileDbManager.updateAutoSyncStatus(false, LocalFileEntityDao.Properties.Uid.m1944eq(this.userInfo.getUuid()), LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(this.mAudioInfoBean.getId())));
            this.mLocalFileDbManager.updateAudioUrl("", LocalFileEntityDao.Properties.Uid.m1944eq(this.userInfo.getUuid()), LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(this.mAudioInfoBean.getId())));
        }
        if (DataHandle.getIns().isVip() && !this.mIsLocal) {
            string = getResources().getString(C0874R.string.audio_drop_success);
        } else {
            string = getResources().getString(C0874R.string.audio_delete_success);
        }
        ToastUtil.showTextToast(this, string);
        EventBus.getDefault().post(new EventBean(50));
        finish();
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        BarUtils.setNavBarColor(this, getColor(C0874R.color.bg_color_secondary));
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        Disposable disposable = this.mTransStatusDis;
        if (disposable != null && !disposable.isDisposed()) {
            this.mTransStatusDis.dispose();
        }
        Disposable disposable2 = this.mAiResultStatusDis;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.mAiResultStatusDis.dispose();
        }
        if (this.mMediaPlayer != null) {
            audioStop();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshPlayBtn() {
        if (this.playStatus == MyEnum.AUDIO_PLAY_STATUS.PLAYING) {
            this.mBinding.playStartIv.setVisibility(4);
            this.mBinding.playStopIv.setVisibility(0);
        } else {
            this.mBinding.playStartIv.setVisibility(0);
            this.mBinding.playStopIv.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshSeekbar() {
        try {
            if (this.mMediaPlayer == null || !this.isPlaying) {
                this.mBinding.sbProgress.setProgress(0);
            } else {
                this.mBinding.sbProgress.setProgress(this.mStartWaveTime);
            }
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
        if (this.mStartWaveTime == 0) {
            this.mBinding.tvRecordTimeCur.setText("00:00:00");
        } else {
            this.mBinding.tvRecordTimeCur.setText(DateUtil.numberToTimeInDetail(this.mStartWaveTime / 1000));
        }
        RecordTranscribeFragment recordTranscribeFragment = this.transcribeFragment;
        if (recordTranscribeFragment != null) {
            recordTranscribeFragment.setCurTime(this.mStartWaveTime);
        }
    }

    public void onTransClick(int i, boolean z) {
        LogUtil.m338i(String.format("--transClick--startWaveTime:%s,isConferenceSummaryClicked:%s", Integer.valueOf(i), Boolean.valueOf(z)));
        this.mStartWaveTime = i;
        try {
            MediaPlayer mediaPlayer = this.mMediaPlayer;
            if (mediaPlayer != null) {
                if (this.isPlaying) {
                    mediaPlayer.seekTo(i);
                } else {
                    audioResume();
                }
            } else {
                toPlayAudio();
            }
        } catch (Exception e) {
            e.printStackTrace();
            BaseAppUtils.printErrorMsg(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTransClickBGED(int i, int i2, boolean z, boolean z2, boolean z3) {
        LogUtil.m338i(String.format("--transClickBGED--startWaveTime:%s,isCurrent:%s,isReclick:%s,isConferenceSummaryClicked:%s", Integer.valueOf(i2), Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3)));
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            try {
                if (!this.isPlaying) {
                    if (z) {
                        this.mStartWaveTime = mediaPlayer.getCurrentPosition();
                    } else {
                        this.mStartWaveTime = i2;
                    }
                    audioResume();
                    return;
                }
                if (!z) {
                    this.mStartWaveTime = i2;
                    mediaPlayer.seekTo(i2);
                    return;
                } else if (z2) {
                    this.mStartWaveTime = mediaPlayer.getCurrentPosition();
                    audioResume();
                    return;
                } else {
                    audioPause();
                    return;
                }
            } catch (Exception e) {
                LogUtil.m334d("mMediaPlayer异常" + e);
                return;
            }
        }
        this.mStartWaveTime = i2;
        toPlayAudio();
    }

    private void click2SpeechToText() {
        if (this.mAudioInfoBean.isLocalAudio()) {
            this.nextStep = 1;
            upload2OSS();
        } else {
            reqTranscribe();
        }
    }

    public void click2ShareTxt() {
        if (this.mAudioInfoBean.getIsTrans() == MyEnum.TRANS_STATE.TRANSCRIBED.type) {
            DialogUtils.showBottomSheetDialog(this.context, new FileScrollSelectView(this.context, new FileScrollSelectView.DateSelectListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda83
                @Override // com.aivox.common_ui.FileScrollSelectView.DateSelectListener
                public final void onDateSelected(int i) {
                    this.f$0.m166x10183042(i);
                }
            }));
        } else {
            ToastUtil.showTextToast(this, Integer.valueOf(C0874R.string.file_download_refuse_2text));
        }
    }

    /* JADX INFO: renamed from: lambda$click2ShareTxt$62$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m166x10183042(int i) {
        refreshBtmNotify(true, getString(C0874R.string.in_exporting));
        new DownloadShareTxt().download(this, this.mAudioInfoBean, i, new DownloadShareTxt.IDownloadShareListener() { // from class: com.aivox.app.activity.RecordInfoActivity.10
            @Override // com.aivox.app.test.share.DownloadShareTxt.IDownloadShareListener
            public void onProgress(int i2) {
            }

            @Override // com.aivox.app.test.share.DownloadShareTxt.IDownloadShareListener
            public void onSuccess(File file) {
                LogUtil.m338i("下载完成:" + file.getAbsolutePath());
                RecordInfoActivity.this.refreshBtmNotify(false, "");
                FileUtils.shareFile(RecordInfoActivity.this, file.getAbsolutePath());
            }

            @Override // com.aivox.app.test.share.DownloadShareTxt.IDownloadShareListener
            public void onError(String str) {
                RecordInfoActivity.this.refreshBtmNotify(false, "");
                ToastUtil.showShort(str);
            }
        });
    }

    public void click2ShareAudioUrl() {
        this.mShareStatus = null;
        if (BaseStringUtil.isNotEmpty(this.mAudioInfoBean.getAudioUrl()) && this.mAudioInfoBean.getFileState() == 0) {
            showPreSharePop();
            return;
        }
        BottomSheetDialog bottomSheetDialog = this.moreOperateDialog;
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            this.moreOperateDialog.dismiss();
        }
        this.nextStep = 3;
        checkBeforeUpload();
    }

    private void deleteLocalFile() {
        FileUtils.localFileDelete(this.mAudioInfoBean.getLocalPath());
        this.mLocalFileDbManager.deleteWhere(LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(this.mAudioInfoBean.getId())), LocalFileEntityDao.Properties.Uid.m1944eq(this.userInfo.getUuid()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: reqFileSize, reason: merged with bridge method [inline-methods] */
    public void m2208lambda$reqFileSize$64$comaivoxappactivityRecordInfoActivity(final String str) {
        long fileSizeKb = FileUtils.getFileSizeKb(str);
        String filePathL = FileUtils.getFilePathL(str);
        String filePathF = FileUtils.getFilePathF(str);
        if (FileUtils.isFileExist(filePathL) && FileUtils.isFileExist(filePathF)) {
            fileSizeKb = fileSizeKb + FileUtils.getFileSizeKb(filePathL) + FileUtils.getFileSizeKb(filePathF);
        }
        new UserService(this).checkCloudSpace(fileSizeKb).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda33
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2207lambda$reqFileSize$63$comaivoxappactivityRecordInfoActivity((Boolean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda44
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2209lambda$reqFileSize$65$comaivoxappactivityRecordInfoActivity(str, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqFileSize$63$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2207lambda$reqFileSize$63$comaivoxappactivityRecordInfoActivity(Boolean bool) throws Exception {
        upload2OSS();
    }

    /* JADX INFO: renamed from: lambda$reqFileSize$65$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2209lambda$reqFileSize$65$comaivoxappactivityRecordInfoActivity(final String str, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda41
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2208lambda$reqFileSize$64$comaivoxappactivityRecordInfoActivity(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void upload2OSS() {
        String localPath = this.mAudioInfoBean.getLocalPath();
        if (FileUtils.isFileExist(localPath)) {
            LogUtil.m338i("filePath:" + localPath);
            refreshBtmNotify(true, getString(C0874R.string.in_progress));
            CommonUploadManager.getInstance().startUpload(this.context, 0, localPath, this.userInfo.getUuid(), DateUtil.getCurDate(DateUtil.YYYYMMDD), new C078111(localPath), Constant.TYPE_AUDIO);
            return;
        }
        LogUtil.m338i("文件不存在");
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.RecordInfoActivity$11 */
    class C078111 implements OnUploadListener {
        final /* synthetic */ String val$localPath;

        C078111(String str) {
            this.val$localPath = str;
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onProgress(int i, long j, long j2, int i2) {
            LogUtil.m338i("上传中:" + i + "  " + i2 + "% " + j + "/" + j2);
            RecordInfoActivity recordInfoActivity = RecordInfoActivity.this;
            recordInfoActivity.refreshBtmNotify(true, recordInfoActivity.getString(C0874R.string.in_progress));
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onSuccess(int i, String str, String str2, long j) {
            LogUtil.m338i("上传成功:" + i + "  " + str + "   " + str2);
            RecordInfoActivity.this.m209x69b3b3a2(str2, FileUtils.getAudioFileVoiceTime(this.val$localPath), this.val$localPath);
            RecordInfoActivity.this.refreshBtmNotify(false, "");
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onFailure(int i) {
            LogUtil.m338i("上传失败：" + i);
            RecordInfoActivity.this.refreshBtmNotify(false, "");
            RecordInfoActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.RecordInfoActivity$11$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2211lambda$onFailure$1$comaivoxappactivityRecordInfoActivity$11();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onFailure$1$com-aivox-app-activity-RecordInfoActivity$11, reason: not valid java name */
        /* synthetic */ void m2211lambda$onFailure$1$comaivoxappactivityRecordInfoActivity$11() {
            DialogUtils.showDialogWithDefBtnAndSingleListener(RecordInfoActivity.this, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.audio_sync_fail), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$11$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2210lambda$onFailure$0$comaivoxappactivityRecordInfoActivity$11(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, false, true);
        }

        /* JADX INFO: renamed from: lambda$onFailure$0$com-aivox-app-activity-RecordInfoActivity$11, reason: not valid java name */
        /* synthetic */ void m2210lambda$onFailure$0$comaivoxappactivityRecordInfoActivity$11(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            RecordInfoActivity.this.upload2OSS();
        }
    }

    private void toPlayAudio() {
        LogUtil.m334d("mAudioUrl:" + this.mAudioUrl);
        if (this.playStatus == MyEnum.AUDIO_PLAY_STATUS.IDLE) {
            audioPlay();
        } else {
            audioResume();
        }
    }

    private void audioPlay() {
        this.mMediaPlayer = null;
        LogUtil.m336e("mAudioUrl:" + this.mAudioUrl);
        if (this.iosWavManager2 == null) {
            this.iosWavManager2 = new IOSWavManager2();
        }
        this.iosWavManager2.handleByThread(this, this.mAudioUrl, new IOSWavManager2.IIOSWavCallback() { // from class: com.aivox.app.activity.RecordInfoActivity.12
            @Override // com.aivox.app.util.wav.IOSWavManager2.IIOSWavCallback
            public void onPrepare() {
                RecordInfoActivity recordInfoActivity = RecordInfoActivity.this;
                DialogUtils.showProgressDialog(recordInfoActivity, recordInfoActivity.getString(C0874R.string.ios_wav_running));
            }

            @Override // com.aivox.app.util.wav.IOSWavManager2.IIOSWavCallback
            public void onProgress(String str, long j, long j2) {
                DialogUtils.showProgressDialog(RecordInfoActivity.this, str + ": " + ((int) ((j / j2) * 100.0f)) + PunctuationConst.PERCENT);
            }

            @Override // com.aivox.app.util.wav.IOSWavManager2.IIOSWavCallback
            public void onError(String str) {
                DialogUtils.hideProgressDialog();
                RecordInfoActivity.this.rxAudioPlayerPlay(PlayConfig.url(str).looping(false).build());
            }

            @Override // com.aivox.app.util.wav.IOSWavManager2.IIOSWavCallback
            public void onComplete(File file) {
                DialogUtils.hideProgressDialog();
                RecordInfoActivity.this.rxAudioPlayerPlay(PlayConfig.file(file).looping(false).build());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rxAudioPlayerPlay(PlayConfig playConfig) {
        RxAudioPlayer.getInstance().play(playConfig).subscribeOn(Schedulers.m1898io()).subscribe(new Observer<Boolean>() { // from class: com.aivox.app.activity.RecordInfoActivity.13
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                LogUtil.m338i("onSubscribe:" + disposable.toString());
                RecordInfoActivity.this.mHandler.sendEmptyMessage(1);
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean bool) {
                LogUtil.m338i("onNext:" + bool);
                RecordInfoActivity.this.mHandler.sendEmptyMessage(5);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                LogUtil.m338i("onError:" + th.getLocalizedMessage());
                RecordInfoActivity.this.mHandler.sendEmptyMessage(7);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                LogUtil.m338i("onComplete:");
                RecordInfoActivity.this.mHandler.sendEmptyMessage(4);
            }
        });
        this.isPlaying = true;
    }

    public void audioPause() {
        if (RxAudioPlayer.getInstance().getMediaPlayer() == null || RxAudioPlayer.getInstance().getMediaPlayer().getDuration() <= 0) {
            return;
        }
        RxAudioPlayer.getInstance().pause();
        this.mHandler.sendEmptyMessage(2);
        this.isPlaying = false;
    }

    private void audioResume() {
        if (RxAudioPlayer.getInstance().getMediaPlayer() != null) {
            RxAudioPlayer.getInstance().resume();
            this.mHandler.sendEmptyMessage(3);
            this.isPlaying = true;
            this.mHandler.sendEmptyMessage(1001);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void audioStop() {
        if (RxAudioPlayer.getInstance().getMediaPlayer() == null || !this.isPlaying) {
            return;
        }
        RxAudioPlayer.getInstance().stopPlay();
        this.mHandler.sendEmptyMessage(4);
        this.isPlaying = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getMediaPlayer() {
        this.mMediaPlayer = RxAudioPlayer.getInstance().getMediaPlayer();
        LogUtil.m338i("getMediaPlayer==null:" + this.mMediaPlayer);
    }

    @Override // com.aivox.app.view.AudioTxtLongClickPopup.AudioTxtLongClickListener
    public void longClick2EditContent(int i) {
        this.currentEditPos = i;
        changeListMode(2);
    }

    @Override // com.aivox.app.view.AudioTxtLongClickPopup.AudioTxtLongClickListener
    public void longClick2EditMark(int i) {
        changeListMode(0);
        Transcribe transcribe = this.transcribeList.get(i);
        if (!DataHandle.getIns().isVip() && transcribe.getAudioMark() == null) {
            AppUtils.showVipSubDialog(this.context);
            return;
        }
        BottomEditDialogView bottomEditDialogView = new BottomEditDialogView(this.context, 3, 1000, transcribe.getAudioMark() != null, new C078414(transcribe));
        bottomEditDialogView.setDialogContent(getString(C0874R.string.dialog_edit_notes_title), getString(C0874R.string.dialog_edit_notes_msg), getString(C0874R.string.dialog_edit_notes_hint), transcribe.getAudioMark() == null ? "" : transcribe.getAudioMark().getContent(), getString(C0874R.string.delete));
        DialogUtils.showBottomSheetDialog(this.context, bottomEditDialogView, C0874R.style.BottomSheetDialogWithEdit);
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.RecordInfoActivity$14 */
    class C078414 implements BottomEditDialogView.OnBtnClickListener {
        final /* synthetic */ Transcribe val$transcribe;

        C078414(Transcribe transcribe) {
            this.val$transcribe = transcribe;
        }

        @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
        public void onLeftBtnClick() {
            Context context = RecordInfoActivity.this.context;
            Integer numValueOf = Integer.valueOf(C0874R.string.reminder);
            Integer numValueOf2 = Integer.valueOf(C0874R.string.note_mark_delete_notice);
            final Transcribe transcribe = this.val$transcribe;
            DialogUtils.showDeleteDialog(context, numValueOf, numValueOf2, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$14$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context2, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m213x94303e51(transcribe, context2, dialogBuilder, dialog, i, i2, editText);
                }
            }, null, true, true, C0874R.string.delete, C0874R.string.cancel, 0);
        }

        /* JADX INFO: renamed from: lambda$onLeftBtnClick$1$com-aivox-app-activity-RecordInfoActivity$14 */
        /* synthetic */ void m213x94303e51(final Transcribe transcribe, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            DialogUtils.showLoadingDialog(context);
            new AudioService(context).deleteNoteMark(Arrays.asList(transcribe.getAudioMark().getId())).doFinally(new RecordInfoActivity$14$$ExternalSyntheticLambda1()).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$14$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m212xdab8b0b2(transcribe, obj);
                }
            }, new RecordInfoActivity$14$$ExternalSyntheticLambda3());
        }

        /* JADX INFO: renamed from: lambda$onLeftBtnClick$0$com-aivox-app-activity-RecordInfoActivity$14 */
        /* synthetic */ void m212xdab8b0b2(Transcribe transcribe, Object obj) throws Exception {
            transcribe.setAudioMark(null);
            RecordInfoActivity.this.transcribeFragment.updateListData();
            RecordInfoActivity.access$5410(RecordInfoActivity.this);
            RecordInfoActivity.this.updateCountStatus();
        }

        @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
        public void onSaveBtnClick(final String str) {
            LogUtil.m336e(str);
            if (BaseStringUtil.isEmpty(str.trim())) {
                return;
            }
            DialogUtils.showLoadingDialog(RecordInfoActivity.this.context);
            final long j = Long.parseLong(this.val$transcribe.getBeginAt());
            if (this.val$transcribe.getAudioMark() != null) {
                Observable<Object> observableDoFinally = new AudioService(RecordInfoActivity.this.context).editNoteMark(RecordInfoActivity.this.mAudioId, this.val$transcribe.getAudioMark().getId().intValue(), str).doFinally(new RecordInfoActivity$14$$ExternalSyntheticLambda1());
                final Transcribe transcribe = this.val$transcribe;
                observableDoFinally.subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$14$$ExternalSyntheticLambda2
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f$0.m214xd5fdbdba(transcribe, str, j, obj);
                    }
                }, new RecordInfoActivity$14$$ExternalSyntheticLambda3());
            } else {
                Observable<Integer> observableDoFinally2 = new AudioService(RecordInfoActivity.this.context).addNoteMark(RecordInfoActivity.this.mAudioId, this.val$transcribe.getId(), j, str).doFinally(new RecordInfoActivity$14$$ExternalSyntheticLambda1());
                final Transcribe transcribe2 = this.val$transcribe;
                observableDoFinally2.subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$14$$ExternalSyntheticLambda4
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f$0.m215x8f754b59(str, j, transcribe2, (Integer) obj);
                    }
                }, new RecordInfoActivity$14$$ExternalSyntheticLambda3());
            }
        }

        /* JADX INFO: renamed from: lambda$onSaveBtnClick$2$com-aivox-app-activity-RecordInfoActivity$14 */
        /* synthetic */ void m214xd5fdbdba(Transcribe transcribe, String str, long j, Object obj) throws Exception {
            AudioMarkBean audioMarkBean = new AudioMarkBean();
            audioMarkBean.setId(transcribe.getAudioMark().getId());
            audioMarkBean.setContent(str);
            audioMarkBean.setMarkTime(Long.valueOf(j));
            transcribe.setAudioMark(audioMarkBean);
            RecordInfoActivity.this.transcribeFragment.updateListData();
        }

        /* JADX INFO: renamed from: lambda$onSaveBtnClick$3$com-aivox-app-activity-RecordInfoActivity$14 */
        /* synthetic */ void m215x8f754b59(String str, long j, Transcribe transcribe, Integer num) throws Exception {
            AudioMarkBean audioMarkBean = new AudioMarkBean();
            audioMarkBean.setId(num);
            audioMarkBean.setContent(str);
            audioMarkBean.setMarkTime(Long.valueOf(j));
            transcribe.setAudioMark(audioMarkBean);
            RecordInfoActivity.this.transcribeFragment.updateListData();
            RecordInfoActivity.access$5408(RecordInfoActivity.this);
            RecordInfoActivity.this.updateCountStatus();
        }
    }

    @Override // com.aivox.app.view.AudioTxtLongClickPopup.AudioTxtLongClickListener
    public void addPicTag(int i) {
        changeListMode(0);
        if (!this.mPicInsertEnable) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.unable_to_insert_pic));
            return;
        }
        LogUtil.m336e(this.mMaxPicCount + "/" + i);
        if (this.mMaxPicCount - i <= 0) {
            if (DataHandle.getIns().isVip()) {
                DialogUtils.showDialogWithBtnIds(this, Integer.valueOf(C0874R.string.reminder), getString(C0874R.string.img_out_limit_num_for_vip, new Object[]{Integer.valueOf(this.mMaxPicCount)}), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda57
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                        RecordInfoActivity.lambda$addPicTag$66(context, dialogBuilder, dialog, i2, i3, editText);
                    }
                }, false, false, C0874R.string.know_and_continue, C0874R.string.know_and_continue);
                return;
            } else {
                AppUtils.showVipSubDialog(this.context);
                return;
            }
        }
        PermissionUtils.getIns(this, new C078515(i)).request("android.permission.READ_MEDIA_IMAGES");
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.RecordInfoActivity$15 */
    class C078515 implements PermissionCallback {
        final /* synthetic */ int val$curPicCount;

        C078515(int i) {
            this.val$curPicCount = i;
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void granted(boolean z) {
            if (z) {
                PictureSelector.create((AppCompatActivity) RecordInfoActivity.this).openGallery(SelectMimeType.ofImage()).setMaxSelectNum(RecordInfoActivity.this.mMaxPicCount - this.val$curPicCount).setMinSelectNum(1).setImageSpanCount(4).isPreviewImage(true).isDisplayCamera(true).setImageEngine(GlideEngine.createGlideEngine()).setCompressEngine(new CompressFileEngine() { // from class: com.aivox.app.activity.RecordInfoActivity$15$$ExternalSyntheticLambda0
                    @Override // com.luck.picture.lib.engine.CompressFileEngine
                    public final void onStartCompress(Context context, ArrayList arrayList, OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
                        this.f$0.m2213lambda$granted$0$comaivoxappactivityRecordInfoActivity$15(context, arrayList, onKeyValueResultCallbackListener);
                    }
                }).setSelectionMode(2).isSelectZoomAnim(true).forResult(188);
            } else {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied));
            }
        }

        /* JADX INFO: renamed from: lambda$granted$0$com-aivox-app-activity-RecordInfoActivity$15, reason: not valid java name */
        /* synthetic */ void m2213lambda$granted$0$comaivoxappactivityRecordInfoActivity$15(Context context, ArrayList arrayList, final OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
            Luban.with(context).load(arrayList).ignoreBy(50).setCompressListener(new OnNewCompressListener() { // from class: com.aivox.app.activity.RecordInfoActivity.15.1
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
            BaseAppUtils.openSettingView(RecordInfoActivity.this.context);
        }
    }

    @Override // com.aivox.app.view.AudioTxtLongClickPopup.AudioTxtLongClickListener
    public void editName(final int i) {
        changeListMode(0);
        BottomEditDialogView bottomEditDialogView = new BottomEditDialogView(this.context, 1, 16, false, new C078616(i));
        bottomEditDialogView.setDialogContent(getString(C0874R.string.participants_edit_title), getString(C0874R.string.participants_edit_msg), getString(C0874R.string.participants_edit_hint), this.transcribeList.get(i).getSpeakerName(), "");
        bottomEditDialogView.setDialogType(2, this.mAvatarStyle, new BottomEditDialogView.ExtraInteractionListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda19
            @Override // com.aivox.common_ui.BottomEditDialogView.ExtraInteractionListener
            public final void onDataChange(Object obj) {
                this.f$0.m2193lambda$editName$69$comaivoxappactivityRecordInfoActivity(i, obj);
            }
        });
        DialogUtils.showBottomSheetDialog(this.context, bottomEditDialogView, C0874R.style.BottomSheetDialogWithEdit);
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.RecordInfoActivity$16 */
    class C078616 implements BottomEditDialogView.OnBtnClickListener {
        final /* synthetic */ int val$pos;

        static /* synthetic */ void lambda$onSaveBtnClick$0(Object obj) throws Exception {
        }

        @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
        public void onLeftBtnClick() {
        }

        C078616(int i) {
            this.val$pos = i;
        }

        @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
        public void onSaveBtnClick(String str) {
            String speaker = ((Transcribe) RecordInfoActivity.this.transcribeList.get(this.val$pos)).getSpeaker();
            ArrayList arrayList = new ArrayList();
            for (Transcribe transcribe : RecordInfoActivity.this.transcribeList) {
                if (transcribe.getSpeaker().equals(speaker)) {
                    transcribe.setSpeakerName(str);
                }
                arrayList.add(transcribe);
            }
            RecordInfoActivity.this.transcribeFragment.batchUpdate(arrayList);
            new AudioService(RecordInfoActivity.this.context).updateSpeakerName(((Transcribe) RecordInfoActivity.this.transcribeList.get(this.val$pos)).getSpeaker(), ((Transcribe) RecordInfoActivity.this.transcribeList.get(this.val$pos)).getAudioId(), str).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$16$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    RecordInfoActivity.C078616.lambda$onSaveBtnClick$0(obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$16$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m216x1c86301d((Throwable) obj);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSaveBtnClick$1$com-aivox-app-activity-RecordInfoActivity$16 */
        /* synthetic */ void m216x1c86301d(Throwable th) throws Exception {
            if (th instanceof HttpException) {
                AppUtils.checkHttpCode(RecordInfoActivity.this.context);
            }
        }
    }

    /* JADX INFO: renamed from: lambda$editName$69$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2193lambda$editName$69$comaivoxappactivityRecordInfoActivity(int i, Object obj) {
        Integer num = (Integer) obj;
        int iIntValue = num.intValue();
        this.mAvatarStyle = iIntValue;
        this.transcribeFragment.setAvatarStyle(iIntValue);
        new AudioService(this.context).changeSpeakerAvatar(this.transcribeList.get(i).getAudioId(), num.intValue()).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda58
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj2) throws Exception {
                RecordInfoActivity.lambda$editName$67(obj2);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda59
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj2) throws Exception {
                this.f$0.m2192lambda$editName$68$comaivoxappactivityRecordInfoActivity((Throwable) obj2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$editName$68$com-aivox-app-activity-RecordInfoActivity, reason: not valid java name */
    /* synthetic */ void m2192lambda$editName$68$comaivoxappactivityRecordInfoActivity(Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.context);
        }
    }

    @Override // com.aivox.app.view.AudioTxtLongClickPopup.AudioTxtLongClickListener
    public void longClick2Copy() {
        BaseStringUtil.putTextIntoClip(this, this.transcribeFragment.getTexts());
        changeListMode(0);
    }

    @Override // com.aivox.app.view.AudioTxtLongClickPopup.AudioTxtLongClickListener
    public void longClick2Share() {
        changeListMode(0);
    }

    @Override // com.aivox.app.view.AudioTxtLongClickPopup.AudioTxtLongClickListener
    public void longClick2Delete() {
        DialogUtils.showDeleteDialog(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.note_mark_delete_notice), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda40
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m177x1975472a(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, null, true, true, C0874R.string.delete, C0874R.string.cancel, 0);
    }

    /* JADX INFO: renamed from: lambda$longClick2Delete$71$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m177x1975472a(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        AudioTxtLongClickPopup audioTxtLongClickPopup = this.mAudioTxtLongClickPop;
        if (audioTxtLongClickPopup != null) {
            audioTxtLongClickPopup.dismiss();
        }
        final String ids = this.transcribeFragment.getIds(0);
        if (BaseStringUtil.isEmpty(ids)) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.select_at_least_one_segment));
            return;
        }
        DialogUtils.showLoadingDialog(context);
        new AudioService(context).batchDeleteContent(ids).doFinally(new RecordInfoActivity$$ExternalSyntheticLambda49()).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda73
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m176x3433d869(ids, obj);
            }
        }, new RecordInfoActivity$$ExternalSyntheticLambda15());
        changeListMode(0);
    }

    /* JADX INFO: renamed from: lambda$longClick2Delete$70$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m176x3433d869(String str, Object obj) throws Exception {
        for (Transcribe transcribe : this.transcribeList) {
            if (str.contains(String.valueOf(transcribe.getId())) && transcribe.getAudioMark() != null) {
                this.mNoteMarkCount--;
            }
        }
        updateCountStatus();
        this.transcribeFragment.batchDelete(str);
    }

    public void changeListMode(int i) {
        this.listMode = i;
        if (i == 0) {
            this.checkList.clear();
            this.mBinding.rlEdit.setVisibility(8);
            this.mBinding.rlTop.setVisibility(0);
            this.mBinding.sbProgress.setVisibility(0);
            this.mBinding.clBottom.setVisibility(0);
            this.mBinding.llTime.setVisibility(0);
            AudioTxtLongClickPopup audioTxtLongClickPopup = this.mAudioTxtLongClickPop;
            if (audioTxtLongClickPopup != null && audioTxtLongClickPopup.isShowing()) {
                this.mAudioTxtLongClickPop.dismiss();
            }
            KeyboardUtils.hideSoftInput(this);
        } else if (i == 1) {
            this.mBinding.rlEdit.setVisibility(0);
            this.mBinding.tvEditTitle.setText("");
            this.mBinding.rlTop.setVisibility(8);
            this.mBinding.sbProgress.setVisibility(8);
            this.mBinding.clBottom.setVisibility(8);
            this.mBinding.llTime.setVisibility(8);
        } else {
            this.checkList.clear();
            this.mBinding.rlEdit.setVisibility(0);
            this.mBinding.tvEditTitle.setText(C0874R.string.all_edit);
            this.mBinding.rlTop.setVisibility(8);
            this.mBinding.sbProgress.setVisibility(8);
            this.mBinding.clBottom.setVisibility(8);
            this.mBinding.llTime.setVisibility(8);
            this.mBinding.tvSave.setVisibility(0);
            AudioTxtLongClickPopup audioTxtLongClickPopup2 = this.mAudioTxtLongClickPop;
            if (audioTxtLongClickPopup2 != null && audioTxtLongClickPopup2.isShowing()) {
                this.mAudioTxtLongClickPop.dismiss();
            }
        }
        this.transcribeFragment.changeMode(i);
    }

    private void generateShareContent(int i) {
        String url = this.mShareBean.getUrl();
        LogUtil.m334d("分享url：" + url);
        String str = (SPUtil.get(SPUtil.NICKNAME, "") + " " + getString(C0874R.string.shared_a_link) + "\n") + url + "\n";
        if (!TextUtils.isEmpty(this.mShareBean.getPsw())) {
            str = str + getString(C0874R.string.access_code) + this.mShareBean.getPsw() + "\n";
        }
        if (this.mIsDeleteAfterRead) {
            str = str + getString(C0874R.string.read_and_burn) + "\n";
        }
        int i2 = this.mValidPeriod;
        int i3 = i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? 0 : 90 : 30 : 7 : 1;
        String str2 = str + getString(C0874R.string.expire_holder_1) + " " + i3 + " " + getString(C0874R.string.expire_holder_2);
        if (i == 0) {
            FileUtils.shareTxt(this, str2);
            return;
        }
        if (i == 4) {
            StringBuilder sb = new StringBuilder();
            sb.append(SPUtil.get(SPUtil.NICKNAME, "") + " " + getString(C0874R.string.shared_a_link) + "\n");
            sb.append(url + "\n");
            sb.append(getString(C0874R.string.copy_and_open_in_browser) + "\n");
            sb.append(getString(C0874R.string.expire_holder_1) + " " + i3 + " " + getString(C0874R.string.expire_holder_2) + "\n");
            sb.append(getString(C0874R.string.access_code) + this.mShareBean.getPsw());
            BaseStringUtil.putTextIntoClip(this, sb.toString());
        }
    }

    private void showPreSharePop() {
        AudioShareView audioShareView = new AudioShareView(this);
        audioShareView.setMyOnClickListener(new AudioShareView.MyOnClickListener() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda18
            @Override // com.aivox.app.view.AudioShareView.MyOnClickListener
            public final void toShare(int i, boolean z, boolean z2, int i2, boolean z3) {
                this.f$0.m203x1ec9bd3e(i, z, z2, i2, z3);
            }
        });
        DialogUtils.showBottomSheetDialog(this.context, audioShareView);
    }

    /* JADX INFO: renamed from: lambda$showPreSharePop$72$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m203x1ec9bd3e(int i, boolean z, boolean z2, int i2, boolean z3) {
        this.mIsEncrypt = z;
        this.mAllowSave = z3;
        this.mIsDeleteAfterRead = z2;
        this.mValidPeriod = i2;
        if (!this.mAudioInfoBean.isLocalAudio()) {
            m174xb473b8ef(i);
        } else {
            ToastUtil.showTextToast(this.context, Integer.valueOf(C0874R.string.file_share_refuse));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getSignForShare, reason: merged with bridge method [inline-methods] */
    public void m174xb473b8ef(final int i) {
        DialogUtils.showLoadingDialog(this.context);
        int i2 = this.mValidPeriod;
        new AudioService(this).getSignForShare(i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? 0 : 7776000 : 2592000 : QueueConstants.MAX_TIME_TO_LIVE_IN_SECONDS : 86400, this.mIsDeleteAfterRead ? 1 : 0, this.mIsEncrypt ? 1 : 0, this.mAudioInfoBean.getId(), this.mAllowSave ? 1 : 0).doFinally(new RecordInfoActivity$$ExternalSyntheticLambda49()).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda50
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m173xcf324a2e(i, (ShareBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda51
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m175x99b527b0(i, (Throwable) obj);
            }
        });
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /* JADX INFO: renamed from: lambda$getSignForShare$73$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m173xcf324a2e(int i, ShareBean shareBean) throws Exception {
        this.mShareBean = shareBean;
        ShareStatus shareStatus = new ShareStatus();
        this.mShareStatus = shareStatus;
        shareStatus.setPsw(this.mShareBean.getPsw());
        this.mShareStatus.setDueDate(this.mValidPeriod);
        this.mShareStatus.setHasPsw(this.mIsEncrypt ? 1 : 0);
        this.mShareStatus.setBurned(this.mIsDeleteAfterRead ? 1 : 0);
        generateShareContent(i);
    }

    /* JADX INFO: renamed from: lambda$getSignForShare$75$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m175x99b527b0(final int i, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda17
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m174xb473b8ef(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getSignForAppShare, reason: merged with bridge method [inline-methods] */
    public void m171x4cec906d(final int i, final String str) {
        DialogUtils.showLoadingDialog(this.context);
        new AudioService(this).getSignForAppShare(i != 0 ? i != 1 ? i != 2 ? i != 3 ? 0 : 7776000 : 2592000 : QueueConstants.MAX_TIME_TO_LIVE_IN_SECONDS : 86400, this.mAudioInfoBean.getId(), str).doFinally(new RecordInfoActivity$$ExternalSyntheticLambda49()).subscribe(new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda54
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.success));
            }
        }, new Consumer() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda56
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m172x322dff2e(i, str, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getSignForAppShare$78$com-aivox-app-activity-RecordInfoActivity */
    /* synthetic */ void m172x322dff2e(final int i, final String str, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.RecordInfoActivity$$ExternalSyntheticLambda7
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m171x4cec906d(i, str);
                }
            });
        }
    }

    private void changeMode(boolean z) {
        this.mAudioTxtLongClickPop.changeMode(z);
        this.mAudioTxtLongClickPop.hideItem();
    }

    @Override // com.aivox.app.adapter.TranscribeAdapter.OnCheckChangeListener
    public void checkChange(Transcribe transcribe, boolean z, int i) {
        if (!z) {
            for (int i2 = 0; i2 < this.checkList.size(); i2++) {
                if (this.checkList.get(i2).getId() == transcribe.getId()) {
                    this.checkList.remove(i2);
                }
            }
            if (this.checkList.size() == 0) {
                this.mBinding.tvCancel.performClick();
            }
        } else {
            this.checkList.add(transcribe);
        }
        if (this.mAudioTxtLongClickPop != null) {
            changeMode(this.checkList.size() > 1);
        }
        this.mBinding.tvSave.setVisibility(4);
    }

    private static class AiScenesAdapter extends BaseQuickAdapter<AiScenesBean, BaseViewHolder> {
        public AiScenesAdapter(int i, List<AiScenesBean> list) {
            super(i, list);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, AiScenesBean aiScenesBean) {
            baseViewHolder.setText(C0726R.id.tv_ai_scenes, aiScenesBean.getScenesNameRes());
            baseViewHolder.setImageResource(C0726R.id.iv_ai_scenes, aiScenesBean.getScenesIconRes());
            baseViewHolder.setBackgroundRes(C0726R.id.ll_ai_scenes, aiScenesBean.getScenesBgRes());
        }
    }
}
