package com.aivox.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.app.C0726R;
import com.aivox.app.activity.MainActivity;
import com.aivox.app.activity.RecordingActivity;
import com.aivox.app.adapter.AudioListAdapter;
import com.aivox.app.databinding.FragmentMainHomeBinding;
import com.aivox.app.listener.FragmentFileActionListener;
import com.aivox.app.test.trans.AudioUploadModel;
import com.aivox.base.C0874R;
import com.aivox.base.app.AppManager;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.http.HttpException;
import com.aivox.base.http.socket.ConnectStatus;
import com.aivox.base.permission.PermissionCallback;
import com.aivox.base.permission.PermissionUtils;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.router.action.RecordAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.JsonUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.MiscUtil;
import com.aivox.base.util.NetUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseBindingFragment;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.http.AudioService;
import com.aivox.common.http.UserService;
import com.aivox.common.http.oss.CommonUploadManager;
import com.aivox.common.http.oss.OnUploadListener;
import com.aivox.common.model.ActivityBean;
import com.aivox.common.model.AiTitleBean;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.AudioNewAllListBean;
import com.aivox.common.model.AudioType;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.FunctionRightsBean;
import com.aivox.common.model.LeftTimeBean;
import com.aivox.common.model.MultipleRecordItem;
import com.aivox.common.model.NoticeBean;
import com.aivox.common.model.TranslateInfoBean;
import com.aivox.common.model.UserInfo;
import com.aivox.common.p003db.LocalFileEntityDao;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.p003db.entity.LocalFileEntity;
import com.aivox.common.p003db.maneger.LocalFileDbManager;
import com.aivox.common.parse.SendManager;
import com.aivox.common.pay.PayHelper;
import com.aivox.common.socket.WebSocketHandler;
import com.aivox.common.statemachine.RecordingStateMachine;
import com.aivox.common.statemachine.SocketStateMachine;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.PcmToWavUtil;
import com.aivox.common.util.StringUtil;
import com.aivox.common.util.encrypt.SerAESUtil;
import com.aivox.common.util.whisper.RiffWaveHelperKt;
import com.aivox.common.view.RecordModeSelectDialog;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.LoadingButton;
import com.aivox.common_ui.antishake.AntiShake;
import com.aivox.common_ui.update.UpdateBean;
import com.aivox.common_ui.update.UpdateManager;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.houbb.heaven.constant.FileOptionConst;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class MainHomeFragment extends BaseBindingFragment implements OnViewClickListener {
    private AudioListAdapter mAdapter;
    private FragmentMainHomeBinding mBinding;
    private FragmentFileActionListener mListener;
    private LocalFileDbManager mLocalFileDbManager;
    private UpdateBean mUpdateInfo;
    private UpdateManager mUpdateManager;
    private SQLiteDataBaseManager manager;
    private UserInfo userInfo;
    private final List<MultipleRecordItem> mList = new ArrayList();
    private final CompositeDisposable mDis = new CompositeDisposable();
    private int mRetryTime = 0;
    private final List<RecordModeSelectDialog.RecordModeBean> mModeList = new ArrayList();

    static /* synthetic */ void lambda$toTimeCheck$26(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    public static MainHomeFragment newInstance() {
        return new MainHomeFragment();
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        FragmentMainHomeBinding fragmentMainHomeBindingInflate = FragmentMainHomeBinding.inflate(layoutInflater, viewGroup, false);
        this.mBinding = fragmentMainHomeBindingInflate;
        fragmentMainHomeBindingInflate.setClickListener(this);
        AudioListAdapter audioListAdapter = new AudioListAdapter(this.mActivity, this.mList);
        this.mAdapter = audioListAdapter;
        audioListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda22
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m275x8651ae2e(baseQuickAdapter, view2, i);
            }
        });
        this.mBinding.rvRecent.setLayoutManager(new LinearLayoutManager(this.mActivity));
        this.mBinding.rvRecent.setAdapter(this.mAdapter);
        this.mUpdateManager = new UpdateManager(this.mActivity);
        this.manager = new SQLiteDataBaseManager(this.mActivity);
        this.mLocalFileDbManager = LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
        this.userInfo = this.manager.getUserInfo();
        LogUtil.m336e("当前状态：" + SocketStateMachine.get().getStageNow());
        if (SocketStateMachine.get().getStageNow() == SocketStateMachine.SocketStateCode.START) {
            String randomString = MiscUtil.getRandomString(16);
            LogUtil.m339i("websocket", "uuid:" + randomString);
            SPUtil.put(SPUtil.SESSION_ID, randomString);
            SocketStateMachine.get().enter();
            RecordingStateMachine.get().enter();
        }
        getTranslateInfo();
        getLocalListAndCheckBreak();
        PayHelper.getInstance().checkBreakOrder(this.mActivity);
        return this.mBinding.getRoot();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$0$com-aivox-app-fragment-MainHomeFragment */
    /* synthetic */ void m275x8651ae2e(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        AudioInfoBean audioInfo = this.mList.get(i).getAudioInfo();
        if (audioInfo.getIsTrans() != 1) {
            ToastUtil.showLong(Integer.valueOf(C0874R.string.trans_notice));
            return;
        }
        if (audioInfo.getId() == DataHandle.getIns().getBreakAudioId()) {
            ToastUtil.showLong(Integer.valueOf(C0874R.string.break_audio_resuming));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.AUDIO_ID, audioInfo.getId());
        bundle.putString(Constant.LOCAL_AUDIO_URL, audioInfo.getLocalPath());
        bundle.putString(Constant.LOCAL_AUDIO_NAME, audioInfo.getTitle());
        bundle.putInt(Constant.LOCAL_AUDIO_DURATION, audioInfo.getFileTime());
        bundle.putBoolean(Constant.IS_LOCAL_AUDIO, false);
        ARouterUtils.startWithActivity(this.mActivity, RecordAction.RECORD_INFO, bundle);
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void clearBinding() {
        this.mBinding = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        LogUtil.m338i("MainHomeFragment onResume");
        this.userInfo = this.manager.getUserInfo();
        getConnectStatus();
        UpdateBean updateBean = this.mUpdateInfo;
        if (updateBean != null) {
            updateBean.getMust().intValue();
        }
        reqNewSysNotice();
        reqUserinfo();
        reqLeftSpace();
        reqRecentRecord();
    }

    private void getTranslateInfo() {
        new AudioService(this.mContext).getTranslateInfo().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataHandle.getIns().setTranslateInfo((TranslateInfoBean) JsonUtils.getIns().jsonStr2Obj(SerAESUtil.decrypt((String) obj, Constant.DECRYPT_KEY), TranslateInfoBean.class));
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m274x6dcb80e4((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getTranslateInfo$2$com-aivox-app-fragment-MainHomeFragment */
    /* synthetic */ void m274x6dcb80e4(Throwable th) throws Exception {
        th.printStackTrace();
        int i = this.mRetryTime;
        if (i < 3) {
            this.mRetryTime = i + 1;
            getTranslateInfo();
        }
    }

    private void getActivityInfo() {
        new AudioService(this.mContext).getActivityInfo().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda26
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2344lambda$getActivityInfo$7$comaivoxappfragmentMainHomeFragment((ActivityBean) obj);
            }
        }, new MainHomeFragment$$ExternalSyntheticLambda11());
    }

    /* JADX INFO: renamed from: lambda$getActivityInfo$7$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2344lambda$getActivityInfo$7$comaivoxappfragmentMainHomeFragment(final ActivityBean activityBean) throws Exception {
        if (activityBean.getShowType().intValue() == 0) {
            return;
        }
        if (((Integer) SPUtil.getWithUid(SPUtil.DO_NOT_SHOW_ACTIVITY_ID, 0)).intValue() == activityBean.getId().intValue()) {
            return;
        }
        int iIntValue = ((Integer) SPUtil.getWithUid(SPUtil.DO_NOT_SHOW_ACTIVITY_ID_24H, 0)).intValue();
        boolean z = System.currentTimeMillis() - ((Long) SPUtil.getWithUid(SPUtil.DO_NOT_SHOW_ACTIVITY_TIME_STAMP, 0L)).longValue() < 86400000;
        if (iIntValue == activityBean.getId().intValue() && z) {
            return;
        }
        if (BaseStringUtil.isEmpty(activityBean.getPictureUrl())) {
            SPUtil.putWithUid(SPUtil.DO_NOT_SHOW_ACTIVITY_ID_24H, activityBean.getId());
            SPUtil.putWithUid(SPUtil.DO_NOT_SHOW_ACTIVITY_TIME_STAMP, Long.valueOf(System.currentTimeMillis()));
            DialogUtils.showDialogWithBtnIdsWithCheckBox(this.mContext, Integer.valueOf(C0874R.string.reminder), activityBean.getTitle(), null, new DialogBuilder.DialogButtonClickWithCheckBoxListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda21
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickWithCheckBoxListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, CheckBox checkBox) {
                    this.f$0.m2342lambda$getActivityInfo$3$comaivoxappfragmentMainHomeFragment(activityBean, context, dialogBuilder, dialog, i, i2, checkBox);
                }
            }, activityBean.getNotifyOnly() == 0, true, C0874R.string.dismiss, C0874R.string.confirm);
            return;
        }
        final Dialog dialog = new Dialog(this.mContext, C0874R.style.SelfDefineDialogStyle);
        dialog.setContentView(C0726R.layout.dialog_activity_layout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        final ImageView imageView = (ImageView) dialog.findViewById(C0726R.id.iv_activity_pic);
        ImageView imageView2 = (ImageView) dialog.findViewById(C0726R.id.iv_close);
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(C0726R.id.ll_do_not_show);
        final AppCompatCheckBox appCompatCheckBox = (AppCompatCheckBox) dialog.findViewById(C0726R.id.cb_do_not_show);
        LoadingButton loadingButton = (LoadingButton) dialog.findViewById(C0726R.id.btn_title);
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = -1;
            layoutParams.height = -2;
            window.setAttributes(layoutParams);
        }
        loadingButton.setVisibility(BaseStringUtil.isEmpty(activityBean.getTitle()) ? 8 : 0);
        loadingButton.setText(activityBean.getTitle());
        Glide.with(this).asBitmap().load(activityBean.getPictureUrl()).into(new CustomTarget<Bitmap>() { // from class: com.aivox.app.fragment.MainHomeFragment.1
            @Override // com.bumptech.glide.request.target.Target
            public void onLoadCleared(Drawable drawable) {
            }

            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                int width = (int) ((imageView.getWidth() * bitmap.getHeight()) / bitmap.getWidth());
                ViewGroup.LayoutParams layoutParams2 = imageView.getLayoutParams();
                layoutParams2.height = width;
                imageView.setLayoutParams(layoutParams2);
                imageView.setImageBitmap(bitmap);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda23
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MainHomeFragment.lambda$getActivityInfo$4(appCompatCheckBox, activityBean, view2);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                dialog.dismiss();
            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda25
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2343lambda$getActivityInfo$6$comaivoxappfragmentMainHomeFragment(activityBean, dialog, view2);
            }
        };
        loadingButton.setOnClickListener(onClickListener);
        imageView.setOnClickListener(onClickListener);
        SPUtil.putWithUid(SPUtil.DO_NOT_SHOW_ACTIVITY_ID_24H, activityBean.getId());
        SPUtil.putWithUid(SPUtil.DO_NOT_SHOW_ACTIVITY_TIME_STAMP, Long.valueOf(System.currentTimeMillis()));
        dialog.show();
    }

    /* JADX INFO: renamed from: lambda$getActivityInfo$3$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2342lambda$getActivityInfo$3$comaivoxappfragmentMainHomeFragment(ActivityBean activityBean, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, CheckBox checkBox) {
        if (checkBox.isChecked()) {
            SPUtil.putWithUid(SPUtil.DO_NOT_SHOW_ACTIVITY_ID, activityBean.getId());
        }
        if (BaseStringUtil.isNotEmpty(activityBean.getUrl())) {
            BaseAppUtils.startActivityForWeb(getActivity(), activityBean.getUrl(), ARouterUtils.getClass(MainAction.WEB));
        } else {
            if (activityBean.getNotifyOnly() == 1) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString(Constant.KEY_TITLE, activityBean.getTitle());
            bundle.putString("data", activityBean.getText());
            ARouterUtils.startWithContext(this.mContext, MainAction.NOTICE_DETAIL, bundle);
        }
    }

    static /* synthetic */ void lambda$getActivityInfo$4(AppCompatCheckBox appCompatCheckBox, ActivityBean activityBean, View view2) {
        appCompatCheckBox.setChecked(!appCompatCheckBox.isChecked());
        SPUtil.putWithUid(SPUtil.DO_NOT_SHOW_ACTIVITY_ID, Integer.valueOf(appCompatCheckBox.isChecked() ? activityBean.getId().intValue() : 0));
    }

    /* JADX INFO: renamed from: lambda$getActivityInfo$6$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2343lambda$getActivityInfo$6$comaivoxappfragmentMainHomeFragment(ActivityBean activityBean, Dialog dialog, View view2) {
        if (BaseStringUtil.isNotEmpty(activityBean.getUrl())) {
            BaseAppUtils.startActivityForWeb(getActivity(), activityBean.getUrl(), ARouterUtils.getClass(MainAction.WEB));
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.KEY_TITLE, activityBean.getTitle());
            bundle.putString("data", activityBean.getText());
            ARouterUtils.startWithContext(this.mContext, MainAction.NOTICE_DETAIL, bundle);
        }
        dialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reqRecentRecord() {
        new AudioService(this.mActivity).getAllAudioList(1, 2, "", 0).subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m277xe735ae2b((AudioNewAllListBean.DataBean) obj);
            }
        }, new MainHomeFragment$$ExternalSyntheticLambda11());
    }

    /* JADX INFO: renamed from: lambda$reqRecentRecord$12$com-aivox-app-fragment-MainHomeFragment */
    /* synthetic */ void m277xe735ae2b(AudioNewAllListBean.DataBean dataBean) throws Exception {
        this.mList.clear();
        if (!dataBean.getDatas().isEmpty()) {
            this.mBinding.tvNoFiles.setVisibility(8);
            StringUtil.updateFileTime(this.mLocalFileDbManager, this.userInfo.getUuid(), dataBean.getDatas());
            StringUtil.updateFileLocalPath(this.mLocalFileDbManager, this.userInfo.getUuid(), dataBean.getDatas());
            final AudioInfoBean audioInfoBean = dataBean.getDatas().get(0);
            if ((audioInfoBean.getTitleStatus() == 1 && audioInfoBean.getTitle().isEmpty()) || audioInfoBean.getTitleStatus() == 2) {
                Observable.interval(1L, TimeUnit.SECONDS).flatMap(new Function() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda14
                    @Override // io.reactivex.functions.Function
                    public final Object apply(Object obj) {
                        return this.f$0.m2346lambda$reqRecentRecord$8$comaivoxappfragmentMainHomeFragment(audioInfoBean, (Long) obj);
                    }
                }).takeUntil((Predicate<? super R>) new Predicate() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda15
                    @Override // io.reactivex.functions.Predicate
                    public final boolean test(Object obj) {
                        return MainHomeFragment.lambda$reqRecentRecord$9((AiTitleBean) obj);
                    }
                }).subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda16
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        MainHomeFragment.lambda$reqRecentRecord$10(audioInfoBean, (AiTitleBean) obj);
                    }
                }, new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda17
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        MainHomeFragment.lambda$reqRecentRecord$11(audioInfoBean, (Throwable) obj);
                    }
                });
            }
            if (audioInfoBean.getAudioUrl().isEmpty() && FileUtils.isFileExist(audioInfoBean.getLocalPath()) && audioInfoBean.getId() != DataHandle.getIns().getBreakAudioId()) {
                AudioUploadModel.getInstance().checkAndUpload(this.mContext, audioInfoBean, false, false);
            }
            Iterator<AudioInfoBean> it = dataBean.getDatas().iterator();
            while (it.hasNext()) {
                this.mList.add(new MultipleRecordItem(it.next()));
            }
            this.mAdapter.notifyDataSetChanged();
            return;
        }
        this.mBinding.tvNoFiles.setVisibility(0);
    }

    /* JADX INFO: renamed from: lambda$reqRecentRecord$8$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ ObservableSource m2346lambda$reqRecentRecord$8$comaivoxappfragmentMainHomeFragment(AudioInfoBean audioInfoBean, Long l) throws Exception {
        return new AudioService(this.mContext).getAiTitleById(audioInfoBean.getId());
    }

    static /* synthetic */ boolean lambda$reqRecentRecord$9(AiTitleBean aiTitleBean) throws Exception {
        return aiTitleBean.getStatus().intValue() != 2;
    }

    static /* synthetic */ void lambda$reqRecentRecord$10(AudioInfoBean audioInfoBean, AiTitleBean aiTitleBean) throws Exception {
        if (aiTitleBean.getStatus().intValue() != 2) {
            EventBean eventBean = new EventBean(80);
            eventBean.setA(audioInfoBean.getId());
            eventBean.setT(aiTitleBean);
            EventBus.getDefault().post(eventBean);
        }
    }

    static /* synthetic */ void lambda$reqRecentRecord$11(AudioInfoBean audioInfoBean, Throwable th) throws Exception {
        AiTitleBean aiTitleBean = new AiTitleBean();
        aiTitleBean.setStatus(4);
        aiTitleBean.setTitle("");
        EventBean eventBean = new EventBean(80);
        eventBean.setA(audioInfoBean.getId());
        eventBean.setT(aiTitleBean);
        EventBus.getDefault().post(eventBean);
    }

    private void reqLeftSpace() {
        this.mDis.add(new UserService(this.mActivity).getLeftTime().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda30
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                MainHomeFragment.lambda$reqLeftSpace$13((LeftTimeBean) obj);
            }
        }, new MainHomeFragment$$ExternalSyntheticLambda11()));
    }

    static /* synthetic */ void lambda$reqLeftSpace$13(LeftTimeBean leftTimeBean) throws Exception {
        SPUtil.put(SPUtil.LEFT_CURRENCY_TIME, Integer.valueOf(leftTimeBean.getGeneralTime()));
        SPUtil.put(SPUtil.IS_TRANS_TIME_OUT, Boolean.valueOf(leftTimeBean.getGeneralTime() <= 5));
    }

    @Override // com.aivox.common.base.BaseBindingFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mDis.clear();
    }

    public void setInteractionListener(FragmentFileActionListener fragmentFileActionListener) {
        this.mListener = fragmentFileActionListener;
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void onEventMainThread(EventBean eventBean) {
        super.onEventMainThread(eventBean);
        LogUtil.m338i("HOME_FRAG_Main_event:" + eventBean.getFrom());
        int from = eventBean.getFrom();
        if (from == 72) {
            refreshNotice();
            getLocalListAndCheckBreak();
        }
        int i = 0;
        if (from == 80) {
            int a = eventBean.getA();
            AiTitleBean aiTitleBean = (AiTitleBean) eventBean.getT();
            while (i < this.mList.size()) {
                if (this.mList.get(i).getAudioInfo().getId() == a) {
                    this.mList.get(i).getAudioInfo().setTitleStatus(aiTitleBean.getStatus().intValue());
                    this.mList.get(i).getAudioInfo().setTitle(aiTitleBean.getTitle());
                    this.mAdapter.notifyItemChanged(i);
                    return;
                }
                i++;
            }
            return;
        }
        if (from == 306 || from == 308 || from == 300 || from == 301) {
            updateDeviceStatus();
            return;
        }
        switch (from) {
            case 200:
                LogUtil.m337e("TEMP", "AUDIO_UPLOAD_START");
                while (i < this.mList.size()) {
                    if (this.mList.get(i).getAudioInfo().getId() == eventBean.getA()) {
                        this.mList.get(i).getAudioInfo().setState(MyEnum.AUDIO_UPLOAD_STATE.UPLOADING.type);
                        this.mAdapter.notifyItemChanged(i);
                    } else {
                        i++;
                    }
                    break;
                }
                break;
            case Constant.EVENT.AUDIO_UPLOAD_PROGRESS /* 201 */:
                LogUtil.m337e("TEMP", "AUDIO_UPLOAD_PROGRESS " + eventBean.getB());
                while (i < this.mList.size()) {
                    if (this.mList.get(i).getAudioInfo().getId() == eventBean.getA()) {
                        if (eventBean.getB() != 100) {
                            this.mList.get(i).getAudioInfo().setState(MyEnum.AUDIO_UPLOAD_STATE.UPLOADING.type);
                        }
                        this.mList.get(i).getAudioInfo().setProgress(eventBean.getB());
                    } else {
                        i++;
                    }
                    break;
                }
                break;
            case Constant.EVENT.AUDIO_UPLOAD_COMPLETE /* 202 */:
                LogUtil.m337e("TEMP", "AUDIO_UPLOAD_COMPLETE");
                while (i < this.mList.size()) {
                    if (this.mList.get(i).getAudioInfo().getId() == eventBean.getA()) {
                        AudioInfoBean audioInfo = this.mList.get(i).getAudioInfo();
                        audioInfo.setState(MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD_SUCCESS.type);
                        audioInfo.getAudioInfo().setAudioUrl(eventBean.getS1());
                        this.mAdapter.notifyItemChanged(i);
                        LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession()).insertOrReplace(new LocalFileEntity(audioInfo));
                    } else {
                        i++;
                    }
                    break;
                }
                break;
            case Constant.EVENT.AUDIO_UPLOAD_FAILED /* 203 */:
                LogUtil.m337e("TEMP", "AUDIO_UPLOAD_FAILED");
                while (true) {
                    if (i < this.mList.size()) {
                        if (this.mList.get(i).getAudioInfo().getId() == eventBean.getA()) {
                            this.mList.get(i).getAudioInfo().setState(MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD_FAIL.type);
                            this.mAdapter.notifyItemChanged(i);
                        } else {
                            i++;
                        }
                    }
                }
                ToastUtil.showLong(eventBean.getS1());
                break;
            default:
                switch (from) {
                    case Constant.EVENT.AUDIO_TRANS_PROGRESS /* 211 */:
                        LogUtil.m337e("TEMP", "AUDIO_TRANS_PROGRESS " + eventBean.getB());
                        break;
                    case 212:
                        LogUtil.m337e("TEMP", "AUDIO_TRANS_COMPLETE");
                        for (int i2 = 0; i2 < this.mList.size(); i2++) {
                            if (this.mList.get(i2).getAudioInfo().getId() == eventBean.getA()) {
                                this.mList.get(i2).getAudioInfo().setIsTrans(MyEnum.TRANS_STATE.TRANSCRIBED.type);
                                this.mAdapter.notifyItemChanged(i2);
                                LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession()).updateTransStatus(1, LocalFileEntityDao.Properties.Uid.m1944eq(this.userInfo.getUuid()), LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(eventBean.getA())));
                            }
                            break;
                        }
                        break;
                    case Constant.EVENT.AUDIO_TRANS_FAILED /* 213 */:
                        LogUtil.m337e("TEMP", "AUDIO_TRANS_FAILED");
                        while (true) {
                            if (i < this.mList.size()) {
                                if (this.mList.get(i).getAudioInfo().getId() == eventBean.getA()) {
                                    this.mList.get(i).getAudioInfo().setIsTrans(MyEnum.TRANS_STATE.TRANS_FAIL.type);
                                    this.mAdapter.notifyItemChanged(i);
                                } else {
                                    i++;
                                }
                            }
                        }
                        ToastUtil.showLong(eventBean.getS1());
                        break;
                }
                break;
        }
    }

    private void updateDeviceStatus() {
        final boolean zHasConnectedBluetooth = DataHandle.getIns().hasConnectedBluetooth(false);
        LogUtil.m336e("CONNECT STATUS : " + zHasConnectedBluetooth);
        this.mBinding.tvDeviceConnected.setVisibility(zHasConnectedBluetooth ? 0 : 8);
        String str = (String) SPUtil.get(SPUtil.DEVICE_NOTICE_NAME, "");
        if (zHasConnectedBluetooth && !str.contains(CommonServiceUtils.getInstance().getConnectedDeviceName())) {
            ToastUtil.showLong(Integer.valueOf(C0874R.string.device_connected_msg));
            SPUtil.put(SPUtil.DEVICE_NOTICE_NAME, CommonServiceUtils.getInstance().getConnectedDeviceName() + "/" + str);
        }
        this.mBinding.llDevices.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda29
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m280x7ed33fac(zHasConnectedBluetooth, view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$updateDeviceStatus$14$com-aivox-app-fragment-MainHomeFragment */
    /* synthetic */ void m280x7ed33fac(final boolean z, View view2) {
        PermissionUtils.getIns(this.mActivity, new PermissionCallback() { // from class: com.aivox.app.fragment.MainHomeFragment.2
            @Override // com.aivox.base.permission.PermissionCallback
            public void granted(boolean z2) {
                String str;
                if (z2) {
                    FragmentActivity fragmentActivity = MainHomeFragment.this.mActivity;
                    if (z) {
                        str = MainAction.DEVICE_CONNECTED;
                    } else {
                        str = MainAction.DEVICE_SCAN;
                    }
                    ARouterUtils.startWithActivity(fragmentActivity, str);
                    return;
                }
                ToastUtil.showLong(Integer.valueOf(C0874R.string.permission_denied_bluetooth));
            }

            @Override // com.aivox.base.permission.PermissionCallback
            public void requestError(Throwable th) {
                LogUtil.m336e("permission.e:" + th.getLocalizedMessage());
                ToastUtil.showLong(Integer.valueOf(C0874R.string.permission_denied_bluetooth));
                BaseAppUtils.openSettingView(MainHomeFragment.this.mContext);
            }
        }).request("android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_ADVERTISE", "android.permission.ACCESS_FINE_LOCATION");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reqUserinfo() {
        this.mDis.add(new UserService(this.mActivity).getUserInfo().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda32
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2349lambda$reqUserinfo$15$comaivoxappfragmentMainHomeFragment((UserInfo) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda33
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2350lambda$reqUserinfo$16$comaivoxappfragmentMainHomeFragment((Throwable) obj);
            }
        }));
        this.mDis.add(new UserService(this.mActivity).getFunctionRights().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda34
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataHandle.getIns().setFunctionBean((FunctionRightsBean) obj);
            }
        }, new MainHomeFragment$$ExternalSyntheticLambda11()));
    }

    /* JADX INFO: renamed from: lambda$reqUserinfo$15$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2349lambda$reqUserinfo$15$comaivoxappfragmentMainHomeFragment(UserInfo userInfo) throws Exception {
        this.manager.insertUserInfo(userInfo);
        this.userInfo = userInfo;
        SPUtil.put(SPUtil.USER_AVATAR, userInfo.getAvatar());
    }

    /* JADX INFO: renamed from: lambda$reqUserinfo$16$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2350lambda$reqUserinfo$16$comaivoxappfragmentMainHomeFragment(Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda31
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.reqUserinfo();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reqUpdate() {
        this.mDis.add(new AudioService(this.mActivity).checkUpdate().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda27
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2347lambda$reqUpdate$18$comaivoxappfragmentMainHomeFragment((UpdateBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda28
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2348lambda$reqUpdate$19$comaivoxappfragmentMainHomeFragment((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$reqUpdate$18$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2347lambda$reqUpdate$18$comaivoxappfragmentMainHomeFragment(UpdateBean updateBean) throws Exception {
        this.mUpdateInfo = updateBean;
        this.mUpdateManager.simpleCheck(updateBean);
        SPUtil.put(SPUtil.HAS_NEW_VER, Boolean.valueOf(this.mUpdateManager.isUpdate(updateBean.getNewVersion())));
    }

    /* JADX INFO: renamed from: lambda$reqUpdate$19$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2348lambda$reqUpdate$19$comaivoxappfragmentMainHomeFragment(Throwable th) throws Exception {
        LogUtil.m336e("update_e:" + th.getLocalizedMessage() + ";" + th.getClass().getName());
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda7
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.reqUpdate();
                }
            });
        }
    }

    private void reqNewSysNotice() {
        this.mDis.add(new AudioService(this.mActivity).getAllNewNotice().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m276x604903ef((NoticeBean) obj);
            }
        }, new MainHomeFragment$$ExternalSyntheticLambda11()));
    }

    /* JADX INFO: renamed from: lambda$reqNewSysNotice$20$com-aivox-app-fragment-MainHomeFragment */
    /* synthetic */ void m276x604903ef(NoticeBean noticeBean) throws Exception {
        if (CollectionUtils.isNotEmpty(noticeBean.getSysNotifyList())) {
            if (((Integer) SPUtil.getWithUid(SPUtil.LATEST_SYS_NOTICE_ID, -1)).intValue() < noticeBean.getSysNotifyList().get(0).getId().intValue()) {
                this.mBinding.ivDotSys.setVisibility(0);
                ((MainActivity) this.mActivity).showDot(1);
            } else {
                this.mBinding.ivDotSys.setVisibility(4);
                ((MainActivity) this.mActivity).hideDot(1);
            }
        } else {
            this.mBinding.ivDotSys.setVisibility(4);
            ((MainActivity) this.mActivity).hideDot(1);
        }
        if (CollectionUtils.isNotEmpty(noticeBean.getMessageList().getRecords())) {
            if (((Integer) SPUtil.getWithUid(SPUtil.LATEST_MSG_NOTICE_ID, -1)).intValue() < noticeBean.getMessageList().getRecords().get(0).getId().intValue()) {
                this.mBinding.ivDotMsg.setVisibility(0);
                ((MainActivity) this.mActivity).showDot(2);
                return;
            } else {
                this.mBinding.ivDotMsg.setVisibility(4);
                ((MainActivity) this.mActivity).hideDot(2);
                return;
            }
        }
        this.mBinding.ivDotMsg.setVisibility(4);
        ((MainActivity) this.mActivity).hideDot(2);
    }

    private void refreshNotice() {
        String string;
        if (((Boolean) SPUtil.get(SPUtil.HAS_BREAK_SAVE_AUDIO, false)).booleanValue()) {
            this.mBinding.llNoti.setVisibility(0);
            if (((Boolean) SPUtil.get(SPUtil.AUDIO_BREAK_BECAUSE_SPP_BREAK, false)).booleanValue()) {
                SPUtil.put(SPUtil.AUDIO_BREAK_BECAUSE_SPP_BREAK, false);
                this.mBinding.tvNoti.setText(C0874R.string.noti_recording_break_by_spp);
            } else {
                TextView textView = this.mBinding.tvNoti;
                if (((Boolean) SPUtil.get(SPUtil.BREAK_AUDIO_TIMEOUT_TRANSLATING, false)).booleanValue()) {
                    string = getString(C0874R.string.noti_translating_break);
                } else {
                    string = getString(C0874R.string.noti_recording_break);
                }
                textView.setText(string);
            }
            this.mBinding.llNoti.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda35
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2345lambda$refreshNotice$21$comaivoxappfragmentMainHomeFragment(view2);
                }
            });
            return;
        }
        this.mBinding.llNoti.setVisibility(8);
    }

    /* JADX INFO: renamed from: lambda$refreshNotice$21$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2345lambda$refreshNotice$21$comaivoxappfragmentMainHomeFragment(View view2) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.AUDIO_ID, ((Integer) SPUtil.get(SPUtil.BREAK_SAVE_AUDIO_ID, -1)).intValue());
        bundle.putBoolean(Constant.IS_LOCAL_AUDIO, true);
        ARouterUtils.startWithActivity(this.mActivity, RecordAction.RECORD_INFO, bundle);
    }

    public void getLocalListAndCheckBreak() {
        try {
            for (LocalFileEntity localFileEntity : this.mLocalFileDbManager.queryLocalListByPage(0, 3, LocalFileEntityDao.Properties.Uid.m1944eq(this.userInfo.getUuid()), LocalFileEntityDao.Properties.Uid.m1944eq(this.userInfo.getUuid()))) {
                if (localFileEntity.getLocalPath().endsWith(AudioType.WAV.getType()) && localFileEntity.getIsBreak()) {
                    resumeBreakAudio(localFileEntity.convert2AudioInfo());
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            BaseAppUtils.printErrorMsg(e);
        }
    }

    private void resumeBreakAudio(final AudioInfoBean audioInfoBean) {
        if (RecordingStateMachine.get().getStageNow() != RecordingStateMachine.RecordingStateCode.IDLE || (AppManager.getAppManager().currentActivity() instanceof RecordingActivity) || audioInfoBean.getAudioInfo() == null) {
            return;
        }
        final String localPath = audioInfoBean.getAudioInfo().getLocalPath();
        if (BaseStringUtil.isEmpty(localPath)) {
            return;
        }
        DataHandle.getIns().setBreakAudioId(audioInfoBean.getId());
        final String strReplace = localPath.replace(AudioType.WAV.getType(), AudioType.PCM.getType());
        if (FileUtils.isFileExist(localPath) && !FileUtils.isFileExist(strReplace)) {
            new Thread(new Runnable() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m278xa00f54d6(localPath, audioInfoBean);
                }
            }).start();
            return;
        }
        if (!FileUtils.isFileExist(strReplace) || FileUtils.getFileSizeKb(strReplace) == 0) {
            return;
        }
        try {
            new File(localPath).createNewFile();
            new Thread(new Runnable() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m279x2caf7fd7(strReplace, localPath, audioInfoBean);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: lambda$resumeBreakAudio$22$com-aivox-app-fragment-MainHomeFragment */
    /* synthetic */ void m278xa00f54d6(String str, AudioInfoBean audioInfoBean) {
        File file = new File(str);
        if (RiffWaveHelperKt.isWavFileWithHeader(file)) {
            uploadAndSyncRecord(audioInfoBean, str);
            return;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, FileOptionConst.READ_WRITE);
            randomAccessFile.write(RiffWaveHelperKt.headerBytes((int) file.length()), 0, 44);
            randomAccessFile.close();
            uploadAndSyncRecord(audioInfoBean, str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: renamed from: lambda$resumeBreakAudio$23$com-aivox-app-fragment-MainHomeFragment */
    /* synthetic */ void m279x2caf7fd7(final String str, final String str2, final AudioInfoBean audioInfoBean) {
        PcmToWavUtil.convertPcmToWav(str, str2, new PcmToWavUtil.IPcmConvertCallback() { // from class: com.aivox.app.fragment.MainHomeFragment.3
            @Override // com.aivox.common.util.PcmToWavUtil.IPcmConvertCallback
            public void onError(String str3) {
            }

            @Override // com.aivox.common.util.PcmToWavUtil.IPcmConvertCallback
            public void onStart() {
            }

            @Override // com.aivox.common.util.PcmToWavUtil.IPcmConvertCallback
            public void onComplete() {
                if (FileUtils.getFileSize(str2) > FileUtils.getFileSize(str)) {
                    FileUtils.deleteFile(str);
                }
                MainHomeFragment.this.uploadAndSyncRecord(audioInfoBean, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadAndSyncRecord(AudioInfoBean audioInfoBean, String str) {
        if (FileUtils.isFileExist(str)) {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    ToastUtil.showLong(Integer.valueOf(C0874R.string.break_audio_resume_notice));
                }
            });
            CommonUploadManager.getInstance().startUpload(this.mContext, 0, str, this.userInfo.getUuid(), DateUtil.getCurDate(DateUtil.YYYYMMDD), new C08244(audioInfoBean, str), Constant.TYPE_AUDIO);
        } else {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    ToastUtil.showLong(Integer.valueOf(C0874R.string.toast_file_does_not_exist_or_deleted));
                }
            });
            DataHandle.getIns().setBreakAudioId(-1);
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.fragment.MainHomeFragment$4 */
    class C08244 implements OnUploadListener {
        final /* synthetic */ AudioInfoBean val$audioInfoBean;
        final /* synthetic */ String val$localPath;

        C08244(AudioInfoBean audioInfoBean, String str) {
            this.val$audioInfoBean = audioInfoBean;
            this.val$localPath = str;
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onProgress(int i, long j, long j2, int i2) {
            LogUtil.m337e("BREAK RESUME", i2 + "% " + j + "/" + j2);
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onSuccess(int i, String str, String str2, long j) {
            Observable<Object> observableSyncFile = new AudioService(MainHomeFragment.this.mActivity).syncFile(this.val$audioInfoBean.getId(), str2, FileUtils.getFileSizeKb(this.val$localPath));
            final AudioInfoBean audioInfoBean = this.val$audioInfoBean;
            final String str3 = this.val$localPath;
            observableSyncFile.subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$4$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2360lambda$onSuccess$1$comaivoxappfragmentMainHomeFragment$4(audioInfoBean, str3, obj);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSuccess$1$com-aivox-app-fragment-MainHomeFragment$4, reason: not valid java name */
        /* synthetic */ void m2360lambda$onSuccess$1$comaivoxappfragmentMainHomeFragment$4(AudioInfoBean audioInfoBean, String str, Object obj) throws Exception {
            new AudioService(MainHomeFragment.this.mActivity).saveRecordInfo(audioInfoBean.getId(), audioInfoBean.getTitle(), "", "", FileUtils.getAudioFileVoiceTime(str), FileUtils.getFileSizeKb(str), str, 0).subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$4$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj2) throws Exception {
                    this.f$0.m2359lambda$onSuccess$0$comaivoxappfragmentMainHomeFragment$4((AudioInfoBean) obj2);
                }
            }, new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$4$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj2) {
                    ((Throwable) obj2).printStackTrace();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSuccess$0$com-aivox-app-fragment-MainHomeFragment$4, reason: not valid java name */
        /* synthetic */ void m2359lambda$onSuccess$0$comaivoxappfragmentMainHomeFragment$4(AudioInfoBean audioInfoBean) throws Exception {
            LocalFileEntity localFileEntity = new LocalFileEntity(audioInfoBean);
            localFileEntity.setIsBreak(false);
            MainHomeFragment.this.mLocalFileDbManager.insertOrReplace(localFileEntity);
            DataHandle.getIns().setBreakAudioId(-1);
            MainHomeFragment.this.reqRecentRecord();
            ToastUtil.showShort(Integer.valueOf(C0874R.string.break_audio_resume_success));
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onFailure(int i) {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.MainHomeFragment$4$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ToastUtil.showLong(Integer.valueOf(C0874R.string.audio_sync_fail));
                }
            });
            DataHandle.getIns().setBreakAudioId(-1);
        }
    }

    private void getConnectStatus() {
        ConnectStatus status = WebSocketHandler.getInstance().getStatus();
        LogUtil.m338i("状态：" + status);
        if (status == null) {
            SPUtil.put(SPUtil.RECORD_STATE, 0);
        }
    }

    private void toRecordNotice(int i) {
        if (i == 3) {
            this.mListener.click2Record(i);
        } else {
            m2357lambda$toTimeCheck$32$comaivoxappfragmentMainHomeFragment(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: toTimeCheck, reason: merged with bridge method [inline-methods] */
    public void m2357lambda$toTimeCheck$32$comaivoxappfragmentMainHomeFragment(final int i) {
        if (!NetUtil.isConnected(this.mActivity)) {
            DialogUtils.showDialogWithDefBtnAndSingleListener(this.mActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.socket_reconnect_fail), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda3
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    MainHomeFragment.lambda$toTimeCheck$26(context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, false, false);
        } else {
            DialogUtils.showLoadingDialog(this.mActivity);
            this.mDis.add(new UserService(this.mActivity).getLeftTime().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2356lambda$toTimeCheck$31$comaivoxappfragmentMainHomeFragment(i, (LeftTimeBean) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2358lambda$toTimeCheck$33$comaivoxappfragmentMainHomeFragment(i, (Throwable) obj);
                }
            }));
        }
    }

    /* JADX INFO: renamed from: lambda$toTimeCheck$31$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2356lambda$toTimeCheck$31$comaivoxappfragmentMainHomeFragment(final int i, LeftTimeBean leftTimeBean) throws Exception {
        DialogUtils.hideLoadingDialog();
        SPUtil.put(SPUtil.LEFT_CURRENCY_TIME, Integer.valueOf(leftTimeBean.getGeneralTime()));
        SPUtil.put(SPUtil.IS_TRANS_TIME_OUT, Boolean.valueOf(leftTimeBean.getGeneralTime() <= 5));
        if (leftTimeBean.getGeneralTime() <= 0) {
            DialogUtils.showDialogWithBtnIds(this.mActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.trans_time_exhaust), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda36
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    this.f$0.m2352lambda$toTimeCheck$27$comaivoxappfragmentMainHomeFragment(i, context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda37
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    this.f$0.m2353lambda$toTimeCheck$28$comaivoxappfragmentMainHomeFragment(context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, true, true, C0874R.string.only_recording_continue_use, DataHandle.getIns().isVip() ? C0874R.string.to_recharge : C0874R.string.join_vip);
        } else if (leftTimeBean.getGeneralTime() <= 1800) {
            DialogUtils.showDialogWithBtnIds(this.mActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.trans_time_less_30_min), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda1
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    this.f$0.m2354lambda$toTimeCheck$29$comaivoxappfragmentMainHomeFragment(i, context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda2
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    this.f$0.m2355lambda$toTimeCheck$30$comaivoxappfragmentMainHomeFragment(context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, true, true, C0874R.string.trans_continue_use, DataHandle.getIns().isVip() ? C0874R.string.to_recharge : C0874R.string.join_vip);
        } else {
            toRecord(i);
        }
    }

    /* JADX INFO: renamed from: lambda$toTimeCheck$27$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2352lambda$toTimeCheck$27$comaivoxappfragmentMainHomeFragment(int i, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
        toRecord(i);
    }

    /* JADX INFO: renamed from: lambda$toTimeCheck$28$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2353lambda$toTimeCheck$28$comaivoxappfragmentMainHomeFragment(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        AppUtils.jumpToPurchase(this.mActivity, DataHandle.getIns().isVip());
    }

    /* JADX INFO: renamed from: lambda$toTimeCheck$29$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2354lambda$toTimeCheck$29$comaivoxappfragmentMainHomeFragment(int i, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
        toRecord(i);
    }

    /* JADX INFO: renamed from: lambda$toTimeCheck$30$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2355lambda$toTimeCheck$30$comaivoxappfragmentMainHomeFragment(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        AppUtils.jumpToPurchase(this.mActivity, DataHandle.getIns().isVip());
    }

    /* JADX INFO: renamed from: lambda$toTimeCheck$33$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2358lambda$toTimeCheck$33$comaivoxappfragmentMainHomeFragment(final int i, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda18
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2357lambda$toTimeCheck$32$comaivoxappfragmentMainHomeFragment(i);
                }
            });
        }
    }

    private void toRecord(int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        if (DataHandle.getIns().hasConnectedBluetooth(true)) {
            SendManager.getInstance().sendSppData(Constant.CmdUpCallState);
        }
        if (DataHandle.getIns().hasConnectedBluetooth(true) && BaseAppUtils.getMainTransType(i) != 3 && !CommonServiceUtils.getInstance().getConnectedDeviceName().equals(MyEnum.DEVICE_MODEL.TRANSLATER_T3.name)) {
            boolean ttsEnable = MyEnum.DEVICE_MODEL.getTtsEnable(CommonServiceUtils.getInstance().getConnectedDeviceName());
            this.mModeList.clear();
            List<RecordModeSelectDialog.RecordModeBean> list = this.mModeList;
            int i8 = i == 1 ? 101 : ttsEnable ? 107 : 104;
            if (i == 1) {
                i2 = C1034R.drawable.ic_earphone_mode_both;
            } else {
                i2 = C1034R.drawable.ic_earphone_mode_both_translate;
            }
            if (i == 1) {
                i3 = C0874R.string.record_mode_both;
            } else {
                i3 = C0874R.string.record_mode_both_translate;
            }
            String string = getString(i3);
            if (i == 1) {
                i4 = C0874R.string.record_mode_both_desc;
            } else {
                i4 = C0874R.string.record_mode_both_desc_translate;
            }
            list.add(new RecordModeSelectDialog.RecordModeBean(i8, i2, string, getString(i4)));
            List<RecordModeSelectDialog.RecordModeBean> list2 = this.mModeList;
            int i9 = i == 1 ? 1 : 2;
            if (i == 1) {
                i5 = C1034R.drawable.ic_earphone_mode_up;
            } else {
                i5 = C1034R.drawable.ic_earphone_mode_down;
            }
            if (i == 1) {
                i6 = C0874R.string.record_mode_from_up;
            } else {
                i6 = C0874R.string.record_mode_from_up_translate;
            }
            String string2 = getString(i6);
            if (i == 1) {
                i7 = C0874R.string.record_mode_up_desc;
            } else {
                i7 = C0874R.string.record_mode_up_desc_translate;
            }
            list2.add(new RecordModeSelectDialog.RecordModeBean(i9, i5, string2, getString(i7)));
            DialogUtils.showBottomSheetDialog(this.mActivity, new RecordModeSelectDialog(this.mActivity, this.mModeList, new RecordModeSelectDialog.ItemInteractionListener() { // from class: com.aivox.app.fragment.MainHomeFragment$$ExternalSyntheticLambda13
                @Override // com.aivox.common.view.RecordModeSelectDialog.ItemInteractionListener
                public final void onSelected(int i10) {
                    this.f$0.m2351lambda$toRecord$34$comaivoxappfragmentMainHomeFragment(i10);
                }
            }));
            return;
        }
        this.mListener.click2Record(i);
    }

    /* JADX INFO: renamed from: lambda$toRecord$34$com-aivox-app-fragment-MainHomeFragment, reason: not valid java name */
    /* synthetic */ void m2351lambda$toRecord$34$comaivoxappfragmentMainHomeFragment(int i) {
        FragmentFileActionListener fragmentFileActionListener = this.mListener;
        if (fragmentFileActionListener != null) {
            fragmentFileActionListener.click2Record(i);
        }
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        if (AntiShake.check(this)) {
            return;
        }
        int id = view2.getId();
        if (id == C0726R.id.ll_translate) {
            toRecordNotice(2);
            return;
        }
        if (id == C0726R.id.ll_transcribe) {
            toRecordNotice(1);
            return;
        }
        if (id == C0726R.id.ll_bilingual) {
            toRecordNotice(3);
            return;
        }
        if (id == C0726R.id.ll_devices) {
            PermissionUtils.getIns(this.mActivity, new C08255()).request("android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_ADVERTISE", "android.permission.ACCESS_FINE_LOCATION");
        } else if (id == C0726R.id.iv_notice) {
            ARouterUtils.startWithActivity(this.mActivity, MainAction.NOTICE_LIST);
        } else if (id == C0726R.id.iv_import) {
            ARouterUtils.startWithContext(this.mContext, RecordAction.RECORD_IMPORT);
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.fragment.MainHomeFragment$5 */
    class C08255 implements PermissionCallback {
        C08255() {
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void granted(boolean z) {
            if (z) {
                if (!BleBtService.getInstance().getBluetoothAdapter().isEnabled()) {
                    DialogUtils.showDialogWithDefBtn(MainHomeFragment.this.mContext, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.device_to_open_bt), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.MainHomeFragment$5$$ExternalSyntheticLambda0
                        @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                        public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                            this.f$0.m2361lambda$granted$0$comaivoxappfragmentMainHomeFragment$5(context, dialogBuilder, dialog, i, i2, editText);
                        }
                    }, true, false);
                    return;
                } else {
                    ARouterUtils.startWithActivity(MainHomeFragment.this.mActivity, MainAction.DEVICE_SCAN);
                    return;
                }
            }
            ToastUtil.showLong(Integer.valueOf(C0874R.string.permission_denied_bluetooth));
        }

        /* JADX INFO: renamed from: lambda$granted$0$com-aivox-app-fragment-MainHomeFragment$5, reason: not valid java name */
        /* synthetic */ void m2361lambda$granted$0$comaivoxappfragmentMainHomeFragment$5(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            BaseAppUtils.jump2OpenBT(MainHomeFragment.this.mContext);
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void requestError(Throwable th) {
            LogUtil.m336e("permission.e:" + th.getLocalizedMessage());
            ToastUtil.showLong(Integer.valueOf(C0874R.string.permission_denied_bluetooth));
            BaseAppUtils.openSettingView(MainHomeFragment.this.mContext);
        }
    }
}
