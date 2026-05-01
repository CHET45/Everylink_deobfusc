package com.aivox.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.aivox.app.C0726R;
import com.aivox.app.activity.RecordingActivity;
import com.aivox.app.adapter.HomeAudioListAdapter;
import com.aivox.app.databinding.FragmentHomeBinding;
import com.aivox.app.listener.FragmentFileActionListener;
import com.aivox.app.test.trans.AudioUploadModel;
import com.aivox.app.util.VersionUtil;
import com.aivox.base.C0874R;
import com.aivox.base.app.AppManager;
import com.aivox.base.common.Constant;
import com.aivox.base.common.GlassesCmd;
import com.aivox.base.common.MyEnum;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.http.HttpException;
import com.aivox.base.img.imageloader.GlideApp;
import com.aivox.base.permission.PermissionCallback;
import com.aivox.base.permission.PermissionUtils;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.router.action.RecordAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.JsonUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.NetUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseBindingFragment;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.http.AudioService;
import com.aivox.common.http.UserService;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.AudioNewAllListBean;
import com.aivox.common.model.AudioType;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.GlassOtaUpdateBean;
import com.aivox.common.model.LeftTimeBean;
import com.aivox.common.model.TranslateInfoBean;
import com.aivox.common.model.UserInfo;
import com.aivox.common.p003db.LocalFileEntityDao;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.p003db.entity.LocalFileEntity;
import com.aivox.common.p003db.maneger.LocalFileDbManager;
import com.aivox.common.parse.SendManager;
import com.aivox.common.statemachine.RecordingStateMachine;
import com.aivox.common.statemachine.SocketStateMachine;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.PcmToWavUtil;
import com.aivox.common.util.encrypt.SerAESUtil;
import com.aivox.common.util.whisper.RiffWaveHelperKt;
import com.aivox.common.view.RecordModeSelectDialog;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.HomeEnterView;
import com.aivox.common_ui.antishake.AntiShake;
import com.aivox.common_ui.update.UpdateBean;
import com.aivox.common_ui.update.UpdateManager;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.houbb.heaven.constant.FileOptionConst;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.google.android.material.appbar.AppBarLayout;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class HomeFragment extends BaseBindingFragment implements OnViewClickListener {
    private AudioService audioService;
    private String hardwareInfo;
    private HomeAudioListAdapter mAdapter;
    private FragmentHomeBinding mBinding;
    private FragmentFileActionListener mListener;
    private LocalFileDbManager mLocalFileDbManager;
    private UpdateBean mUpdateInfo;
    private UpdateManager mUpdateManager;
    private UserInfo mUserInfo;
    private final List<RecordModeSelectDialog.RecordModeBean> mModeList = new ArrayList();
    private final List<AudioInfoBean> mList = new ArrayList();
    private int mRetryTime = 0;
    private int mPage = 1;
    private boolean isVideoRecording = false;
    private boolean enableRtspClick = true;
    private final PublishSubject<Integer[]> deviceInfoSub = PublishSubject.create();
    private final PublishSubject<String> productModelSub = PublishSubject.create();
    private final PublishSubject<String> btVersionSub = PublishSubject.create();
    private final PublishSubject<String> wifiVersionSub = PublishSubject.create();
    private final CompositeDisposable mDis = new CompositeDisposable();

    /* JADX INFO: renamed from: $r8$lambda$pnZHaLsuWh0mdbgsX--fRG3ls6s, reason: not valid java name */
    public static /* synthetic */ Pair m2279$r8$lambda$pnZHaLsuWh0mdbgsXfRG3ls6s(Object obj, Object obj2) {
        return new Pair(obj, obj2);
    }

    static /* synthetic */ void lambda$checkLeftTime$22(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    static /* synthetic */ GlassesCmd lambda$onEventMainThread$0(GlassesCmd glassesCmd, Long l) throws Exception {
        return glassesCmd;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        Intent intent;
        if (AntiShake.check(this)) {
            return;
        }
        if (view2.getId() == C0726R.id.iv_home_enter_shorthand || view2.getId() == C0726R.id.cl_asr) {
            m2283lambda$checkLeftTime$26$comaivoxappfragmentHomeFragment(1);
            return;
        }
        if (view2.getId() == C0726R.id.iv_home_enter_translate || view2.getId() == C0726R.id.cl_translate) {
            m2283lambda$checkLeftTime$26$comaivoxappfragmentHomeFragment(2);
            return;
        }
        if (view2.getId() == C0726R.id.iv_home_enter_bilingual) {
            m2283lambda$checkLeftTime$26$comaivoxappfragmentHomeFragment(3);
            return;
        }
        if (view2.getId() == C0726R.id.iv_home_enter_device) {
            connectOrDisconnectDevice();
            return;
        }
        if (view2.getId() == C0726R.id.iv_search) {
            ARouterUtils.startWithContext(this.mContext, MainAction.SEARCH);
            return;
        }
        if (view2.getId() == C0726R.id.ll_device) {
            ARouterUtils.startWithActivity(this.mActivity, MainAction.DEVICE_CONNECTED);
            return;
        }
        if (view2.getId() == C0726R.id.cl_rtsp) {
            if (!this.enableRtspClick) {
                ToastUtil.showShort(getString(C0874R.string.operation_too_fast));
                return;
            }
            if (!((WifiManager) requireActivity().getSystemService("wifi")).isWifiEnabled()) {
                Toast.makeText(requireActivity(), C0874R.string.enable_wifi_hint, 1).show();
                if (Build.VERSION.SDK_INT >= 29) {
                    intent = new Intent("android.settings.panel.action.WIFI");
                } else {
                    intent = new Intent("android.settings.WIFI_SETTINGS");
                }
                intent.addFlags(268435456);
                requireActivity().startActivity(intent);
                return;
            }
            if (this.isVideoRecording) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.glasses_recording_in_progress));
                return;
            } else if (((Integer) SPUtil.get(SPUtil.GLASS_BATTERY, 0)).intValue() > 15) {
                ARouterUtils.startWithActivity(this.mActivity, MainAction.GLASS_PREVIEW);
                return;
            } else {
                DialogUtils.showDialogWithBtnIds(requireContext(), Integer.valueOf(C0874R.string.reminder), getString(C0874R.string.low_battery_warning), null, null, false, false, C0874R.string.sure, C0874R.string.sure);
                return;
            }
        }
        if (view2.getId() == C0726R.id.cl_manual) {
            ARouterUtils.startWithActivity(this.mActivity, MainAction.GLASS_MANUAL);
            return;
        }
        if (view2.getId() == C0726R.id.ll_photo_capture) {
            if (this.isVideoRecording) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.glasses_recording_in_progress));
                return;
            } else {
                BleBtService.getInstance().sendGlassCmd(GlassesCmd.SET_DEVICE_CONTROL, (byte) 8);
                ToastUtil.showShort(Integer.valueOf(C0874R.string.glasses_capture_import_hint));
                return;
            }
        }
        if (view2.getId() == C0726R.id.ll_video_recording) {
            if (this.isVideoRecording) {
                BleBtService.getInstance().sendGlassCmd(GlassesCmd.SET_DEVICE_CONTROL, (byte) 10);
                ToastUtil.showShort(Integer.valueOf(C0874R.string.glasses_stop_record_import_hint));
                return;
            } else {
                BleBtService.getInstance().sendGlassCmd(GlassesCmd.SET_DEVICE_CONTROL, (byte) 9);
                ToastUtil.showShort(Integer.valueOf(C0874R.string.glasses_record_import_hint));
                return;
            }
        }
        if (view2.getId() == C0726R.id.ll_image_recognition) {
            if (this.isVideoRecording) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.glasses_recording_in_progress));
            } else {
                EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_START_RECOGNITION));
            }
        }
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void onEventMainThread(EventBean eventBean) {
        int i;
        super.onEventMainThread(eventBean);
        int from = eventBean.getFrom();
        if (from == 50) {
            getAudioList(true);
            return;
        }
        if (from == 72) {
            getLocalListAndCheckBreak();
            return;
        }
        if (from != 306 && from != 308) {
            int i2 = 0;
            if (from == 402) {
                this.enableRtspClick = false;
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda40
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2297lambda$onEventMainThread$2$comaivoxappfragmentHomeFragment();
                    }
                }, 3000L);
                return;
            }
            if (from == 405) {
                this.hardwareInfo = eventBean.getS1();
                this.productModelSub.onNext(eventBean.getS1());
                return;
            }
            if (from == 415) {
                this.isVideoRecording = eventBean.getA() == 1;
                return;
            }
            if (from == 418) {
                this.deviceInfoSub.onNext(new Integer[]{Integer.valueOf(eventBean.getA()), Integer.valueOf(eventBean.getB()), Integer.valueOf(eventBean.getC())});
                return;
            }
            if (from == 450) {
                this.btVersionSub.onNext(eventBean.getS1());
                return;
            }
            if (from != 451) {
                switch (from) {
                    case 200:
                        LogUtil.m337e("TAG", "AUDIO_UPLOAD_START");
                        while (i2 < this.mList.size()) {
                            if (this.mList.get(i2).getId() == eventBean.getA()) {
                                this.mList.get(i2).setState(MyEnum.AUDIO_UPLOAD_STATE.UPLOADING.type);
                                this.mAdapter.notifyItemChanged(i2);
                                return;
                            }
                            i2++;
                        }
                        return;
                    case Constant.EVENT.AUDIO_UPLOAD_PROGRESS /* 201 */:
                        LogUtil.m337e("TAG", "AUDIO_UPLOAD_PROGRESS " + eventBean.getB());
                        while (i2 < this.mList.size()) {
                            if (this.mList.get(i2).getId() == eventBean.getA()) {
                                if (eventBean.getB() != 100) {
                                    this.mList.get(i2).setState(MyEnum.AUDIO_UPLOAD_STATE.UPLOADING.type);
                                }
                                this.mList.get(i2).setProgress(eventBean.getB());
                                return;
                            }
                            i2++;
                        }
                        return;
                    case Constant.EVENT.AUDIO_UPLOAD_COMPLETE /* 202 */:
                        LogUtil.m337e("TAG", "AUDIO_UPLOAD_COMPLETE");
                        while (i2 < this.mList.size()) {
                            if (this.mList.get(i2).getId() == eventBean.getA()) {
                                AudioInfoBean audioInfoBean = this.mList.get(i2);
                                audioInfoBean.setState(MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD_SUCCESS.type);
                                audioInfoBean.getAudioInfo().setAudioUrl(eventBean.getS1());
                                this.mAdapter.notifyItemChanged(i2);
                                LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession()).insertOrReplace(new LocalFileEntity(audioInfoBean));
                                return;
                            }
                            i2++;
                        }
                        return;
                    case Constant.EVENT.AUDIO_UPLOAD_FAILED /* 203 */:
                        LogUtil.m337e("TAG", "AUDIO_UPLOAD_FAILED");
                        while (true) {
                            if (i2 < this.mList.size()) {
                                if (this.mList.get(i2).getId() == eventBean.getA()) {
                                    this.mList.get(i2).setState(MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD_FAIL.type);
                                    this.mAdapter.notifyItemChanged(i2);
                                } else {
                                    i2++;
                                }
                            }
                        }
                        ToastUtil.showLong(eventBean.getS1());
                        return;
                    default:
                        switch (from) {
                            case Constant.EVENT.BLE_CONNECTED /* 300 */:
                            case 301:
                                if (BleBtService.getInstance().isGlass() && BleBtService.getInstance().getConnectionState() == 2 && CommonServiceUtils.getInstance().getRealConnectedDeviceName().equals(MyEnum.DEVICE_MODEL.HY_16.name)) {
                                    this.mBinding.tvHomeTitle.setVisibility(8);
                                    this.mBinding.ivDevice.setVisibility(0);
                                } else {
                                    this.mBinding.tvHomeTitle.setVisibility(0);
                                    this.mBinding.ivDevice.setVisibility(8);
                                }
                                if (BleBtService.getInstance().isGlass()) {
                                    this.mBinding.llUpdate.setVisibility(8);
                                    this.mDis.add(Observable.fromArray(GlassesCmd.GET_ANTI_SHAKE, GlassesCmd.GET_VERSION, GlassesCmd.GET_HARDWARE_INFO, GlassesCmd.GET_SUPPORTED_FEATURES, GlassesCmd.GET_DEVICE_INFO, GlassesCmd.GET_PRODUCT_MODEL).zipWith(Observable.interval(0L, 200L, TimeUnit.MILLISECONDS), new BiFunction() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda37
                                        @Override // io.reactivex.functions.BiFunction
                                        public final Object apply(Object obj, Object obj2) {
                                            return HomeFragment.lambda$onEventMainThread$0((GlassesCmd) obj, (Long) obj2);
                                        }
                                    }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda38
                                        @Override // io.reactivex.functions.Consumer
                                        public final void accept(Object obj) {
                                            BleBtService.getInstance().sendGlassCmd((GlassesCmd) obj);
                                        }
                                    }));
                                }
                                break;
                            case 302:
                                String s1 = eventBean.getS1();
                                if (s1.contains("cmdd05-")) {
                                    s1 = s1.replace("cmdd05-", "");
                                    try {
                                        int i3 = Integer.parseInt(s1);
                                        if (i3 >= 80) {
                                            i = C1034R.drawable.ic_glass_battery_100;
                                        } else if (i3 >= 60) {
                                            i = C1034R.drawable.ic_glass_battery_80;
                                        } else if (i3 >= 40) {
                                            i = C1034R.drawable.ic_glass_battery_60;
                                        } else if (i3 >= 20) {
                                            i = C1034R.drawable.ic_glass_battery_40;
                                        } else {
                                            i = C1034R.drawable.ic_glass_battery_20;
                                        }
                                        GlideApp.with(requireContext()).load(Integer.valueOf(i)).into(this.mBinding.ivBattery);
                                        break;
                                    } catch (Exception unused) {
                                    }
                                }
                                this.mBinding.tvBattery.setText(s1 + PunctuationConst.PERCENT);
                                return;
                            default:
                                return;
                        }
                        break;
                }
            } else {
                this.wifiVersionSub.onNext(eventBean.getS1());
                return;
            }
        }
        this.mBinding.svGlass.postDelayed(new Runnable() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda39
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateDeviceConnectedStatus();
            }
        }, 200L);
    }

    /* JADX INFO: renamed from: lambda$onEventMainThread$2$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2297lambda$onEventMainThread$2$comaivoxappfragmentHomeFragment() {
        this.enableRtspClick = true;
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        this.mBinding = FragmentHomeBinding.inflate(layoutInflater, viewGroup, false);
        this.mLocalFileDbManager = LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
        this.mUpdateManager = new UpdateManager(this.mActivity);
        this.mUserInfo = new SQLiteDataBaseManager(this.mActivity).getUserInfo();
        this.audioService = new AudioService(this.mActivity);
        if (SocketStateMachine.get().getStageNow() == SocketStateMachine.SocketStateCode.START) {
            SocketStateMachine.get().enter();
            RecordingStateMachine.get().enter();
        }
        this.mBinding.setClickListener(this);
        this.mBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.aivox.app.fragment.HomeFragment.1
            int scrollRange = -1;

            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (this.scrollRange == -1) {
                    this.scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (this.scrollRange + i <= 0 && HomeFragment.this.mBinding.llTitleEnter.getVisibility() == 4) {
                    HomeFragment.this.mBinding.llTitleEnter.setVisibility(0);
                } else if (i == 0) {
                    HomeFragment.this.mBinding.llTitleEnter.setVisibility(4);
                }
            }
        });
        this.mBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda4
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                this.f$0.m2290lambda$initBindingAndViews$3$comaivoxappfragmentHomeFragment();
            }
        });
        this.mBinding.hevShorthand.setClickListener(new HomeEnterView.ClickListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda16
            @Override // com.aivox.common_ui.HomeEnterView.ClickListener
            public final void onClick(int i) {
                this.f$0.m2283lambda$checkLeftTime$26$comaivoxappfragmentHomeFragment(i);
            }
        });
        this.mBinding.hevTranslate.setClickListener(new HomeEnterView.ClickListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda16
            @Override // com.aivox.common_ui.HomeEnterView.ClickListener
            public final void onClick(int i) {
                this.f$0.m2283lambda$checkLeftTime$26$comaivoxappfragmentHomeFragment(i);
            }
        });
        this.mBinding.hevBilingual.setClickListener(new HomeEnterView.ClickListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda16
            @Override // com.aivox.common_ui.HomeEnterView.ClickListener
            public final void onClick(int i) {
                this.f$0.m2283lambda$checkLeftTime$26$comaivoxappfragmentHomeFragment(i);
            }
        });
        this.mBinding.hevDevice.setClickListener(new HomeEnterView.ClickListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda17
            @Override // com.aivox.common_ui.HomeEnterView.ClickListener
            public final void onClick(int i) {
                this.f$0.m2291lambda$initBindingAndViews$4$comaivoxappfragmentHomeFragment(i);
            }
        });
        this.mBinding.hevDeviceConnect.setClickListener(new HomeEnterView.ClickListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda18
            @Override // com.aivox.common_ui.HomeEnterView.ClickListener
            public final void onClick(int i) {
                this.f$0.m2292lambda$initBindingAndViews$5$comaivoxappfragmentHomeFragment(i);
            }
        });
        this.mBinding.rvAudioList.setLayoutManager(new LinearLayoutManager(this.mContext));
        HomeAudioListAdapter homeAudioListAdapter = new HomeAudioListAdapter(C0726R.layout.item_audio_layout);
        this.mAdapter = homeAudioListAdapter;
        homeAudioListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda19
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m2293lambda$initBindingAndViews$6$comaivoxappfragmentHomeFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda20
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildLongClickListener
            public final boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                return this.f$0.m2294lambda$initBindingAndViews$7$comaivoxappfragmentHomeFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setNumChangeListener(new HomeAudioListAdapter.OnSelectNumChangedListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda21
            @Override // com.aivox.app.adapter.HomeAudioListAdapter.OnSelectNumChangedListener
            public final void onAudioSelectedChanged(List list) {
                this.f$0.m2295lambda$initBindingAndViews$8$comaivoxappfragmentHomeFragment(list);
            }
        });
        this.mAdapter.bindToRecyclerView(this.mBinding.rvAudioList);
        this.mAdapter.setEmptyView(C0726R.layout.home_empty_layout);
        this.mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda23
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
            public final void onLoadMoreRequested() {
                this.f$0.m2296lambda$initBindingAndViews$9$comaivoxappfragmentHomeFragment();
            }
        }, this.mBinding.rvAudioList);
        getTranslateInfo();
        reqLeftSpace();
        getLocalListAndCheckBreak();
        this.mDis.add(Observable.combineLatest(this.btVersionSub, this.wifiVersionSub, new BiFunction() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return HomeFragment.m2279$r8$lambda$pnZHaLsuWh0mdbgsXfRG3ls6s((String) obj, (String) obj2);
            }
        }).filter(new Predicate() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return HomeFragment.lambda$initBindingAndViews$10((Pair) obj);
            }
        }).observeOn(Schedulers.m1898io()).flatMapCompletable(new Function() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m261xfd2f0790((Pair) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Action
            public final void run() {
                Log.d("HomeFragment", "开始获取眼镜固件版本！");
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Log.e("HomeFragment", "获取眼镜固件版本失败", (Throwable) obj);
            }
        }));
        this.mDis.add(Observable.zip(this.deviceInfoSub, this.productModelSub, this.btVersionSub, this.wifiVersionSub, new Function4() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Function4
            public final Object apply(Object obj, Object obj2, Object obj3, Object obj4) {
                return HomeFragment.lambda$initBindingAndViews$15((Integer[]) obj, (String) obj2, (String) obj3, (String) obj4);
            }
        }).filter(new Predicate() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return HomeFragment.lambda$initBindingAndViews$16((Object[]) obj);
            }
        }).take(1L).observeOn(Schedulers.m1898io()).flatMapCompletable(new Function() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m263x731dfc96((Object[]) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.Action
            public final void run() {
                Log.d("HomeFragment", "开始同步眼镜信息！");
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Log.e("HomeFragment", "获取眼镜眼镜信息超时", (Throwable) obj);
            }
        }));
        return this.mBinding.getRoot();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$3$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2290lambda$initBindingAndViews$3$comaivoxappfragmentHomeFragment() {
        getAudioList(true);
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$4$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2291lambda$initBindingAndViews$4$comaivoxappfragmentHomeFragment(int i) {
        connectOrDisconnectDevice();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$5$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2292lambda$initBindingAndViews$5$comaivoxappfragmentHomeFragment(int i) {
        connectOrDisconnectDevice();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$6$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2293lambda$initBindingAndViews$6$comaivoxappfragmentHomeFragment(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        if (this.mAdapter.isAudioSelectMode() || !AntiShake.check(this)) {
            if (this.mAdapter.isAudioSelectMode()) {
                this.mAdapter.onItemSelected(i);
                return;
            }
            AudioInfoBean audioInfoBean = this.mList.get(i);
            if (audioInfoBean.getId() == DataHandle.getIns().getBreakAudioId()) {
                ToastUtil.showLong(Integer.valueOf(C0874R.string.break_audio_resuming));
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.AUDIO_ID, audioInfoBean.getId());
            bundle.putString(Constant.LOCAL_AUDIO_URL, audioInfoBean.getLocalPath());
            bundle.putString(Constant.LOCAL_AUDIO_NAME, audioInfoBean.getTitle());
            bundle.putInt(Constant.LOCAL_AUDIO_DURATION, audioInfoBean.getFileTime());
            bundle.putBoolean(Constant.IS_LOCAL_AUDIO, false);
            ARouterUtils.startWithActivity(this.mActivity, RecordAction.RECORD_INFO, bundle);
        }
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$7$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ boolean m2294lambda$initBindingAndViews$7$comaivoxappfragmentHomeFragment(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        this.mAdapter.onItemSelected(i);
        return true;
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$8$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2295lambda$initBindingAndViews$8$comaivoxappfragmentHomeFragment(List list) {
        FragmentFileActionListener fragmentFileActionListener = this.mListener;
        if (fragmentFileActionListener != null) {
            fragmentFileActionListener.audioSelectChanged(list);
        }
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$9$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2296lambda$initBindingAndViews$9$comaivoxappfragmentHomeFragment() {
        getAudioList(false);
    }

    static /* synthetic */ boolean lambda$initBindingAndViews$10(Pair pair) throws Exception {
        return (TextUtils.isEmpty((CharSequence) pair.first) || TextUtils.isEmpty((CharSequence) pair.second)) ? false : true;
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$11$com-aivox-app-fragment-HomeFragment */
    /* synthetic */ void m260xe987340f(Pair pair) throws Exception {
        parserGlassVersion((String) pair.first, (String) pair.second);
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$12$com-aivox-app-fragment-HomeFragment */
    /* synthetic */ CompletableSource m261xfd2f0790(final Pair pair) throws Exception {
        return Completable.fromAction(new Action() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda46
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m260xe987340f(pair);
            }
        });
    }

    static /* synthetic */ Object[] lambda$initBindingAndViews$15(Integer[] numArr, String str, String str2, String str3) throws Exception {
        return new Object[]{numArr, str, str2, str3};
    }

    static /* synthetic */ boolean lambda$initBindingAndViews$16(Object[] objArr) throws Exception {
        return (objArr[0] == null || TextUtils.isEmpty((String) objArr[1]) || TextUtils.isEmpty((String) objArr[2]) || TextUtils.isEmpty((String) objArr[3])) ? false : true;
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$17$com-aivox-app-fragment-HomeFragment */
    /* synthetic */ void m262x5f762915(Object[] objArr) throws Exception {
        glassSync((Integer[]) objArr[0], (String) objArr[1], (String) objArr[2], (String) objArr[3]);
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$18$com-aivox-app-fragment-HomeFragment */
    /* synthetic */ CompletableSource m263x731dfc96(final Object[] objArr) throws Exception {
        return Completable.fromAction(new Action() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda22
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m262x5f762915(objArr);
            }
        });
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (CollectionUtils.isEmpty(this.mList)) {
            getAudioList(true);
        }
        getLatestAudio();
        UpdateBean updateBean = this.mUpdateInfo;
        if (updateBean == null || updateBean.getMust().intValue() == 1) {
            reqUpdate();
        }
    }

    public void notifyItemChanged(AudioInfoBean audioInfoBean) {
        for (int i = 0; i < this.mList.size(); i++) {
            if (this.mList.get(i) != null && this.mList.get(i).getId() == audioInfoBean.getId()) {
                if (audioInfoBean.getTitle().isEmpty()) {
                    audioInfoBean.setTitle(this.mList.get(i).getTitle());
                }
                this.mList.set(i, audioInfoBean);
                LogUtil.m334d("notifyItemChanged" + audioInfoBean.getTitle() + audioInfoBean.getProgress() + audioInfoBean.getState());
                this.mAdapter.notifyItemChanged(i);
            }
        }
    }

    private void connectOrDisconnectDevice() {
        if (!BleBtService.getInstance().getBluetoothAdapter().isEnabled()) {
            DialogUtils.showDialogWithDefBtn(this.mContext, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.device_to_open_bt), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda49
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m259x64d9ec3b(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, true, false);
        } else {
            PermissionUtils.getIns(this.mActivity, new PermissionCallback() { // from class: com.aivox.app.fragment.HomeFragment.2
                @Override // com.aivox.base.permission.PermissionCallback
                public void granted(boolean z) {
                    String str;
                    if (z) {
                        FragmentActivity fragmentActivity = HomeFragment.this.mActivity;
                        if (DataHandle.getIns().hasConnectedBluetooth(false)) {
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
                    BaseAppUtils.openSettingView(HomeFragment.this.mContext);
                }
            }).request("android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_ADVERTISE", "android.permission.ACCESS_FINE_LOCATION");
        }
    }

    /* JADX INFO: renamed from: lambda$connectOrDisconnectDevice$21$com-aivox-app-fragment-HomeFragment */
    /* synthetic */ void m259x64d9ec3b(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        BaseAppUtils.jump2OpenBT(this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: checkLeftTime, reason: merged with bridge method [inline-methods] */
    public void m2283lambda$checkLeftTime$26$comaivoxappfragmentHomeFragment(final int i) {
        if (!NetUtil.isConnected(this.mActivity)) {
            DialogUtils.showDialogWithDefBtnAndSingleListener(this.mActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.socket_reconnect_fail), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda29
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    HomeFragment.lambda$checkLeftTime$22(context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, false, false);
        } else if (i == 3) {
            toRecord(i);
        } else {
            DialogUtils.showLoadingDialog(this.mActivity);
            this.mDis.add(new UserService(this.mActivity).getLeftTime().subscribe(new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda30
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2282lambda$checkLeftTime$25$comaivoxappfragmentHomeFragment(i, (LeftTimeBean) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda31
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2284lambda$checkLeftTime$27$comaivoxappfragmentHomeFragment(i, (Throwable) obj);
                }
            }));
        }
    }

    /* JADX INFO: renamed from: lambda$checkLeftTime$25$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2282lambda$checkLeftTime$25$comaivoxappfragmentHomeFragment(final int i, LeftTimeBean leftTimeBean) throws Exception {
        DialogUtils.hideLoadingDialog();
        SPUtil.put(SPUtil.LEFT_CURRENCY_TIME, Integer.valueOf(leftTimeBean.getGeneralTime()));
        SPUtil.put(SPUtil.IS_TRANS_TIME_OUT, Boolean.valueOf(leftTimeBean.getGeneralTime() <= 5));
        if (leftTimeBean.getGeneralTime() <= 0) {
            DialogUtils.showDialogWithBtnIds(this.mActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.trans_time_exhaust), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda35
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    this.f$0.m2280lambda$checkLeftTime$23$comaivoxappfragmentHomeFragment(i, context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, null, true, true, C0874R.string.only_recording_continue_use, C0874R.string.sure);
        } else if (leftTimeBean.getGeneralTime() <= 1800) {
            DialogUtils.showDialogWithBtnIds(this.mActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.trans_time_less_30_min), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda36
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    this.f$0.m2281lambda$checkLeftTime$24$comaivoxappfragmentHomeFragment(i, context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, null, true, true, C0874R.string.trans_continue_use, C0874R.string.sure);
        } else {
            toRecord(i);
        }
    }

    /* JADX INFO: renamed from: lambda$checkLeftTime$23$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2280lambda$checkLeftTime$23$comaivoxappfragmentHomeFragment(int i, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
        toRecord(i);
    }

    /* JADX INFO: renamed from: lambda$checkLeftTime$24$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2281lambda$checkLeftTime$24$comaivoxappfragmentHomeFragment(int i, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
        toRecord(i);
    }

    /* JADX INFO: renamed from: lambda$checkLeftTime$27$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2284lambda$checkLeftTime$27$comaivoxappfragmentHomeFragment(final int i, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda51
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2283lambda$checkLeftTime$26$comaivoxappfragmentHomeFragment(i);
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
        if (DataHandle.getIns().hasConnectedBluetooth(true) && !BleBtService.getInstance().isGlass() && BaseAppUtils.getMainTransType(i) != 3 && !CommonServiceUtils.getInstance().getConnectedDeviceName().equals(MyEnum.DEVICE_MODEL.TRANSLATER_T3.name)) {
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
                i5 = C1034R.drawable.ic_earphone_mode_up_translate;
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
            DialogUtils.showBottomSheetDialog(this.mActivity, new RecordModeSelectDialog(this.mActivity, this.mModeList, new RecordModeSelectDialog.ItemInteractionListener() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda47
                @Override // com.aivox.common.view.RecordModeSelectDialog.ItemInteractionListener
                public final void onSelected(int i10) {
                    this.f$0.m2304lambda$toRecord$28$comaivoxappfragmentHomeFragment(i10);
                }
            }));
            return;
        }
        this.mListener.click2Record(i);
    }

    /* JADX INFO: renamed from: lambda$toRecord$28$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2304lambda$toRecord$28$comaivoxappfragmentHomeFragment(int i) {
        FragmentFileActionListener fragmentFileActionListener = this.mListener;
        if (fragmentFileActionListener != null) {
            fragmentFileActionListener.click2Record(i);
        }
    }

    private void reqLeftSpace() {
        this.mDis.add(new UserService(this.mActivity).getLeftTime().subscribe(new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                HomeFragment.lambda$reqLeftSpace$29((LeftTimeBean) obj);
            }
        }, new HomeFragment$$ExternalSyntheticLambda11()));
    }

    static /* synthetic */ void lambda$reqLeftSpace$29(LeftTimeBean leftTimeBean) throws Exception {
        SPUtil.put(SPUtil.LEFT_CURRENCY_TIME, Integer.valueOf(leftTimeBean.getGeneralTime()));
        SPUtil.put(SPUtil.IS_TRANS_TIME_OUT, Boolean.valueOf(leftTimeBean.getGeneralTime() <= 5));
    }

    public void getLocalListAndCheckBreak() {
        try {
            for (LocalFileEntity localFileEntity : this.mLocalFileDbManager.queryLocalListByPage(0, 3, LocalFileEntityDao.Properties.Uid.m1944eq(this.mUserInfo.getUuid()), LocalFileEntityDao.Properties.Uid.m1944eq(this.mUserInfo.getUuid()))) {
                if (localFileEntity.getLocalPath().endsWith(AudioType.WAV.getType()) && localFileEntity.getIsBreak()) {
                    resumeBreakAudio(localFileEntity.convert2AudioInfo());
                    return;
                }
            }
        } catch (Exception e) {
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
            new Thread(new Runnable() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2302lambda$resumeBreakAudio$30$comaivoxappfragmentHomeFragment(localPath, audioInfoBean);
                }
            }).start();
            return;
        }
        if (!FileUtils.isFileExist(strReplace) || FileUtils.getFileSizeKb(strReplace) == 0) {
            return;
        }
        try {
            new File(localPath).createNewFile();
            new Thread(new Runnable() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda48
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2303lambda$resumeBreakAudio$31$comaivoxappfragmentHomeFragment(strReplace, localPath, audioInfoBean);
                }
            }).start();
        } catch (IOException e) {
            LogUtil.m336e("resumeBreakAudio:" + e.getLocalizedMessage());
        }
    }

    /* JADX INFO: renamed from: lambda$resumeBreakAudio$30$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2302lambda$resumeBreakAudio$30$comaivoxappfragmentHomeFragment(String str, AudioInfoBean audioInfoBean) {
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

    /* JADX INFO: renamed from: lambda$resumeBreakAudio$31$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2303lambda$resumeBreakAudio$31$comaivoxappfragmentHomeFragment(final String str, final String str2, final AudioInfoBean audioInfoBean) {
        PcmToWavUtil.convertPcmToWav(str, str2, new PcmToWavUtil.IPcmConvertCallback() { // from class: com.aivox.app.fragment.HomeFragment.3
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
                HomeFragment.this.uploadAndSyncRecord(audioInfoBean, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadAndSyncRecord(AudioInfoBean audioInfoBean, String str) {
        if (FileUtils.isFileExist(str)) {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda52
                @Override // java.lang.Runnable
                public final void run() {
                    ToastUtil.showLong(Integer.valueOf(C0874R.string.break_audio_resume_notice));
                }
            });
            this.mDis.add(this.audioService.saveRecordInfo(audioInfoBean.getId(), audioInfoBean.getTitle(), "", "", FileUtils.getAudioFileVoiceTime(str), FileUtils.getFileSizeKb(str), str, 0).subscribe(new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m264x9a20fd59((AudioInfoBean) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    HomeFragment.lambda$uploadAndSyncRecord$35((Throwable) obj);
                }
            }));
        } else {
            ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ToastUtil.showLong(Integer.valueOf(C0874R.string.toast_file_does_not_exist_or_deleted));
                }
            });
            DataHandle.getIns().setBreakAudioId(-1);
        }
    }

    /* JADX INFO: renamed from: lambda$uploadAndSyncRecord$33$com-aivox-app-fragment-HomeFragment */
    /* synthetic */ void m264x9a20fd59(AudioInfoBean audioInfoBean) throws Exception {
        LocalFileEntity localFileEntity = new LocalFileEntity(audioInfoBean);
        localFileEntity.setIsBreak(false);
        this.mLocalFileDbManager.insertOrReplace(localFileEntity);
        DataHandle.getIns().setBreakAudioId(-1);
        ToastUtil.showShort(Integer.valueOf(C0874R.string.break_audio_resume_success));
        this.mList.add(0, audioInfoBean);
        this.mAdapter.notifyItemInserted(0);
    }

    static /* synthetic */ void lambda$uploadAndSyncRecord$35(Throwable th) throws Exception {
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                ToastUtil.showLong(Integer.valueOf(C0874R.string.audio_sync_fail));
            }
        });
        DataHandle.getIns().setBreakAudioId(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceConnectedStatus() {
        FragmentHomeBinding fragmentHomeBinding;
        boolean zHasConnectedBluetooth = DataHandle.getIns().hasConnectedBluetooth(true);
        if (!zHasConnectedBluetooth) {
            BleBtService.getInstance().setGlass(false);
        }
        if (getActivity() == null || getActivity().isFinishing() || (fragmentHomeBinding = this.mBinding) == null) {
            return;
        }
        fragmentHomeBinding.clDefault.setVisibility(BleBtService.getInstance().isGlass() ? 8 : 0);
        this.mBinding.clDefault.setVisibility(BleBtService.getInstance().isGlass() ? 8 : 0);
        this.mBinding.clDefault.setVisibility(BleBtService.getInstance().isGlass() ? 8 : 0);
        this.mBinding.svGlass.setVisibility(BleBtService.getInstance().isGlass() ? 0 : 8);
        if (BleBtService.getInstance().isGlass()) {
            return;
        }
        this.mBinding.glEnter.setVisibility(zHasConnectedBluetooth ? 0 : 8);
        this.mBinding.hevDeviceConnect.setVisibility(zHasConnectedBluetooth ? 8 : 0);
        this.mBinding.ivHomeEnterShorthand.setVisibility(zHasConnectedBluetooth ? 0 : 4);
        this.mBinding.ivHomeEnterTranslate.setVisibility(zHasConnectedBluetooth ? 0 : 4);
        this.mBinding.ivHomeEnterBilingual.setVisibility(zHasConnectedBluetooth ? 0 : 4);
        this.mBinding.ivHomeEnterDevice.setImageResource(zHasConnectedBluetooth ? C1034R.drawable.ic_home_enter_device_connected : C1034R.drawable.ic_home_enter_device);
    }

    public void setInteractionListener(FragmentFileActionListener fragmentFileActionListener) {
        this.mListener = fragmentFileActionListener;
    }

    public void quitSelectMode() {
        this.mAdapter.quitAudioSelectMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reqUpdate() {
        this.mDis.add(this.audioService.checkUpdate().subscribe(new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda32
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2300lambda$reqUpdate$37$comaivoxappfragmentHomeFragment((UpdateBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda34
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2301lambda$reqUpdate$38$comaivoxappfragmentHomeFragment((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$reqUpdate$37$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2300lambda$reqUpdate$37$comaivoxappfragmentHomeFragment(UpdateBean updateBean) throws Exception {
        this.mUpdateInfo = updateBean;
        this.mUpdateManager.simpleCheck(updateBean);
        SPUtil.put(SPUtil.HAS_NEW_VER, Boolean.valueOf(this.mUpdateManager.isUpdate(updateBean.getNewVersion())));
    }

    /* JADX INFO: renamed from: lambda$reqUpdate$38$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2301lambda$reqUpdate$38$comaivoxappfragmentHomeFragment(Throwable th) throws Exception {
        LogUtil.m336e("update_e:" + th.getLocalizedMessage() + ";" + th.getClass().getName());
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda25
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.reqUpdate();
                }
            });
        }
    }

    private void getTranslateInfo() {
        this.mDis.add(this.audioService.getTranslateInfo().subscribe(new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda41
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataHandle.getIns().setTranslateInfo((TranslateInfoBean) JsonUtils.getIns().jsonStr2Obj(SerAESUtil.decrypt((String) obj, Constant.DECRYPT_KEY), TranslateInfoBean.class));
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda42
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2289lambda$getTranslateInfo$40$comaivoxappfragmentHomeFragment((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$getTranslateInfo$40$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2289lambda$getTranslateInfo$40$comaivoxappfragmentHomeFragment(Throwable th) throws Exception {
        LogUtil.m336e("getTranslateInfo:" + th.getLocalizedMessage());
        int i = this.mRetryTime;
        if (i < 3) {
            this.mRetryTime = i + 1;
            getTranslateInfo();
        }
    }

    private void parserGlassVersion(final String str, final String str2) {
        if (BleBtService.getInstance().isGlass()) {
            this.mDis.add(this.audioService.checkGlassOtaUpdate(this.hardwareInfo).subscribe(new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda43
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2298lambda$parserGlassVersion$41$comaivoxappfragmentHomeFragment(str2, str, (GlassOtaUpdateBean) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda45
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2299lambda$parserGlassVersion$42$comaivoxappfragmentHomeFragment((Throwable) obj);
                }
            }));
        }
    }

    /* JADX INFO: renamed from: lambda$parserGlassVersion$41$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2298lambda$parserGlassVersion$41$comaivoxappfragmentHomeFragment(String str, String str2, GlassOtaUpdateBean glassOtaUpdateBean) throws Exception {
        int i = VersionUtil.hasNewVersion(str, glassOtaUpdateBean.getWifiVersion()) ? 2 : 0;
        if (VersionUtil.hasNewVersion(str2, glassOtaUpdateBean.getBtVersion())) {
            i++;
        }
        this.mBinding.llUpdate.setVisibility(i <= 0 ? 8 : 0);
    }

    /* JADX INFO: renamed from: lambda$parserGlassVersion$42$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2299lambda$parserGlassVersion$42$comaivoxappfragmentHomeFragment(Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(requireContext());
        }
    }

    private void glassSync(Integer[] numArr, String str, String str2, String str3) {
        if (BleBtService.getInstance().isGlass()) {
            this.mDis.add(this.audioService.glassSync(numArr[0].intValue(), numArr[1].intValue(), numArr[2].intValue(), str, str2, str3).subscribe(new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda33
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    LogUtil.m334d("glassSync success");
                }
            }));
        }
    }

    private void getAudioList(final boolean z) {
        if (BleBtService.getInstance().isGlass()) {
            return;
        }
        if (z) {
            this.mPage = 1;
        } else {
            this.mPage++;
        }
        this.mDis.add(this.audioService.getAllAudioList(this.mPage, 20, "", 0).doFinally(new Action() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda26
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m2285lambda$getAudioList$44$comaivoxappfragmentHomeFragment();
            }
        }).subscribe(new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda27
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2286lambda$getAudioList$45$comaivoxappfragmentHomeFragment(z, (AudioNewAllListBean.DataBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda28
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2287lambda$getAudioList$46$comaivoxappfragmentHomeFragment((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$getAudioList$44$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2285lambda$getAudioList$44$comaivoxappfragmentHomeFragment() throws Exception {
        if (this.mBinding.refreshView.isRefreshing()) {
            this.mBinding.refreshView.setRefreshing(false);
        }
    }

    /* JADX INFO: renamed from: lambda$getAudioList$45$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2286lambda$getAudioList$45$comaivoxappfragmentHomeFragment(boolean z, AudioNewAllListBean.DataBean dataBean) throws Exception {
        if (z) {
            this.mList.clear();
        }
        if (dataBean.getDatas().isEmpty()) {
            this.mAdapter.setEnableLoadMore(false);
        } else {
            this.mList.addAll(dataBean.getDatas());
            this.mAdapter.setEnableLoadMore(this.mList.size() < dataBean.getTotal());
        }
        if (this.mPage == 1) {
            this.mAdapter.setNewData(this.mList);
        } else {
            this.mAdapter.notifyItemRangeChanged(this.mList.size() - dataBean.getDatas().size(), dataBean.getDatas().size());
        }
        this.mAdapter.loadMoreComplete();
    }

    /* JADX INFO: renamed from: lambda$getAudioList$46$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2287lambda$getAudioList$46$comaivoxappfragmentHomeFragment(Throwable th) throws Exception {
        this.mPage--;
        LogUtil.m336e("getAudioList:" + th.getLocalizedMessage());
    }

    private void getLatestAudio() {
        this.mDis.add(this.audioService.getAllAudioList(1, 1, "", 0).subscribe(new Consumer() { // from class: com.aivox.app.fragment.HomeFragment$$ExternalSyntheticLambda24
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2288lambda$getLatestAudio$47$comaivoxappfragmentHomeFragment((AudioNewAllListBean.DataBean) obj);
            }
        }, new HomeFragment$$ExternalSyntheticLambda11()));
    }

    /* JADX INFO: renamed from: lambda$getLatestAudio$47$com-aivox-app-fragment-HomeFragment, reason: not valid java name */
    /* synthetic */ void m2288lambda$getLatestAudio$47$comaivoxappfragmentHomeFragment(AudioNewAllListBean.DataBean dataBean) throws Exception {
        if (dataBean.getDatas().isEmpty()) {
            return;
        }
        AudioInfoBean audioInfoBean = dataBean.getDatas().get(0);
        if (audioInfoBean.getAudioUrl().isEmpty() && FileUtils.isFileExist(audioInfoBean.getLocalPath()) && audioInfoBean.getId() != DataHandle.getIns().getBreakAudioId()) {
            AudioUploadModel.getInstance().checkAndUpload(this.mContext, audioInfoBean, false, false);
        }
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void clearBinding() {
        this.mBinding = null;
        this.mDis.clear();
    }
}
