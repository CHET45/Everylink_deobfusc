package com.aivox.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.GalleryAdapter;
import com.aivox.app.databinding.FragmentGallaryBinding;
import com.aivox.app.util.SyncManager;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.common.GlassesCmd;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.img.imageloader.GlideApp;
import com.aivox.base.img.imageloader.GlideRequests;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.DensityUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.GalleryUtils;
import com.aivox.base.util.LayoutUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseBindingFragment;
import com.aivox.common.ble.BleDataManager;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.model.DemoMultiEntity;
import com.aivox.common.model.DemoPicBean;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.Transcribe;
import com.aivox.common.p003db.entity.GlassImageEntity;
import com.aivox.common.p003db.maneger.GlassImageDbManager;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.GridItemDecoration;
import com.aivox.common_ui.antishake.AntiShake;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.houbb.heaven.constant.PunctuationConst;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class GalleryFragment extends BaseBindingFragment implements OnViewClickListener {
    private Disposable dis;
    private GalleryAdapter galleryAdapter;
    private GlassImageDbManager glassImageDbManager;
    private FragmentGallaryBinding mBinding;
    private SyncManager syncManager;
    private String uid;
    private final String TAG = getClass().getSimpleName();
    private final int SPAN_COUNT = 3;
    private final AtomicInteger processFileIndex = new AtomicInteger(0);
    private final List<DemoMultiEntity> mList = new ArrayList();
    private boolean enableSyncClick = true;
    private int fileCount = 0;
    private int galleryType = 0;
    private final PublishSubject<String> ssidSub = PublishSubject.create();
    private final PublishSubject<String> passwdSub = PublishSubject.create();
    private final CompositeDisposable mDis = new CompositeDisposable();

    /* JADX INFO: renamed from: $r8$lambda$pnZHaLsuWh0mdbgsX--fRG3ls6s, reason: not valid java name */
    public static /* synthetic */ Pair m2270$r8$lambda$pnZHaLsuWh0mdbgsXfRG3ls6s(Object obj, Object obj2) {
        return new Pair(obj, obj2);
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void onEventMainThread(EventBean eventBean) {
        super.onEventMainThread(eventBean);
        if (eventBean.getFrom() == 403 && (eventBean.getT() instanceof GlassesCmd)) {
            switch (C08082.$SwitchMap$com$aivox$base$common$GlassesCmd[((GlassesCmd) eventBean.getT()).ordinal()]) {
                case 1:
                    this.syncManager.setIp(eventBean.getS1());
                    break;
                case 2:
                    if (!TextUtils.isEmpty(eventBean.getS1())) {
                        this.ssidSub.onNext(eventBean.getS1());
                    }
                    break;
                case 3:
                    if (!TextUtils.isEmpty(eventBean.getS1())) {
                        this.passwdSub.onNext(eventBean.getS1());
                    }
                    break;
                case 4:
                    this.syncManager.setFileApi(eventBean.getS1());
                    break;
                case 5:
                    this.fileCount = eventBean.getA();
                    if (this.syncManager.getCurrentState() == SyncManager.SyncState.IDLE) {
                        updateUiForSyncState(SyncManager.SyncState.IDLE);
                    }
                    updateFileCountUI();
                    break;
                case 6:
                    if (eventBean.getA() == 1) {
                        LogUtil.m335d(this.TAG, "WiFi传输" + eventBean.getA());
                    } else if (eventBean.getA() == 2) {
                        BleBtService.getInstance().sendGlassCmd(GlassesCmd.START_GET_FILE, Byte.valueOf((byte) eventBean.getA()));
                    }
                    break;
                case 7:
                    LogUtil.m335d(this.TAG, "获取OTA信息：" + eventBean.getA() + "\t" + eventBean.getB());
                    break;
            }
            return;
        }
        int from = eventBean.getFrom();
        if (from == 301) {
            if (this.syncManager.isSyncing()) {
                this.syncManager.cancelSync();
            }
            this.fileCount = 0;
            updateFileCountUI();
            return;
        }
        if (from == 406) {
            updateImageList();
        } else {
            if (from != 409) {
                return;
            }
            if (this.fileCount != 0) {
                this.mBinding.tvFile.setText(getString(C0874R.string.importing) + " " + this.processFileIndex.get() + "/" + this.fileCount);
            }
            this.mBinding.cpi.setProgress(eventBean.getA());
            this.mBinding.tvProgress.setText(eventBean.getA() + PunctuationConst.PERCENT);
        }
    }

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        updateImageList();
        this.mBinding.getRoot().postDelayed(new Runnable() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                BleBtService.getInstance().sendGlassCmd(GlassesCmd.GET_FILE_COUNT);
            }
        }, 500L);
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        FragmentGallaryBinding fragmentGallaryBindingInflate = FragmentGallaryBinding.inflate(layoutInflater, viewGroup, false);
        this.mBinding = fragmentGallaryBindingInflate;
        LayoutUtil.fitSystemInsets(fragmentGallaryBindingInflate.glFunction, false);
        this.mBinding.setClickListener(this);
        this.uid = (String) SPUtil.get(SPUtil.USER_ID, "0");
        this.glassImageDbManager = GlassImageDbManager.getInstance(AppApplication.getIns().getDaoSession());
        this.syncManager = new SyncManager(requireContext(), this.uid, new C08071());
        this.galleryAdapter = new GalleryAdapter(this.mList);
        this.mBinding.rvGallery.setLayoutManager(new GridLayoutManager(this.mContext, 3));
        this.mBinding.rvGallery.addItemDecoration(new GridItemDecoration.Builder(this.mContext).color(C0874R.color.bg_color_secondary).size(DensityUtil.dp2px(requireContext(), 2.0f)).build());
        this.galleryAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda12
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.SpanSizeLookup
            public final int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                return this.f$0.m248xcf003e5b(gridLayoutManager, i);
            }
        });
        this.galleryAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda13
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m249x261e2f3a(baseQuickAdapter, view2, i);
            }
        });
        this.mBinding.rvGallery.setAdapter(this.galleryAdapter);
        this.dis = Observable.combineLatest(this.ssidSub, this.passwdSub, new BiFunction() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda14
            @Override // io.reactivex.functions.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return GalleryFragment.m2270$r8$lambda$pnZHaLsuWh0mdbgsXfRG3ls6s((String) obj, (String) obj2);
            }
        }).filter(new Predicate() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return GalleryFragment.lambda$initBindingAndViews$3((Pair) obj);
            }
        }).observeOn(Schedulers.m1898io()).flatMapCompletable(new Function() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m251x2b7801d7((Pair) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m252x8295f2b6();
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m253xd9b3e395((Throwable) obj);
            }
        });
        return this.mBinding.getRoot();
    }

    /* JADX INFO: renamed from: com.aivox.app.fragment.GalleryFragment$1 */
    class C08071 implements SyncManager.SyncListener {
        C08071() {
        }

        @Override // com.aivox.app.util.SyncManager.SyncListener
        public void onSyncStateChanged(SyncManager.SyncState syncState) {
            GalleryFragment.this.updateUiForSyncState(syncState);
            if (syncState == SyncManager.SyncState.IDLE) {
                GalleryFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.GalleryFragment$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        BleBtService.getInstance().sendGlassCmd(GlassesCmd.WIFI_AP_CONTROL, 0);
                    }
                });
            }
        }

        @Override // com.aivox.app.util.SyncManager.SyncListener
        public void onDownloadProgress(final int i) {
            GalleryFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.GalleryFragment$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m256x31259106(i);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onDownloadProgress$1$com-aivox-app-fragment-GalleryFragment$1 */
        /* synthetic */ void m256x31259106(int i) {
            GalleryFragment.this.mBinding.cpi.setProgress(i);
            GalleryFragment.this.mBinding.tvProgress.setText(i + PunctuationConst.PERCENT);
        }

        /* JADX INFO: renamed from: lambda$onSyncProgressText$2$com-aivox-app-fragment-GalleryFragment$1 */
        /* synthetic */ void m258xcb05b5c5(String str) {
            GalleryFragment.this.mBinding.tvFile.setText(str);
        }

        @Override // com.aivox.app.util.SyncManager.SyncListener
        public void onSyncProgressText(final String str) {
            GalleryFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.GalleryFragment$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m258xcb05b5c5(str);
                }
            });
        }

        @Override // com.aivox.app.util.SyncManager.SyncListener
        public void onSyncFinished(final String str, final boolean z) {
            GalleryFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.GalleryFragment$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2277lambda$onSyncFinished$3$comaivoxappfragmentGalleryFragment$1(str, z);
                }
            });
            GalleryFragment.this.updateUiForSyncState(SyncManager.SyncState.IDLE);
        }

        /* JADX INFO: renamed from: lambda$onSyncFinished$3$com-aivox-app-fragment-GalleryFragment$1, reason: not valid java name */
        /* synthetic */ void m2277lambda$onSyncFinished$3$comaivoxappfragmentGalleryFragment$1(String str, boolean z) {
            BleBtService.getInstance().sendGlassCmd(GlassesCmd.WIFI_AP_CONTROL, 0);
            ToastUtil.showShort(str);
            if (z) {
                GalleryFragment.this.updateImageList();
            }
        }

        @Override // com.aivox.app.util.SyncManager.SyncListener
        public void onFileCountUpdate(int i) {
            GalleryFragment.this.fileCount = i;
            GalleryFragment.this.updateFileCountUI();
        }

        /* JADX INFO: renamed from: lambda$onImageListUpdate$4$com-aivox-app-fragment-GalleryFragment$1 */
        /* synthetic */ void m257x470840ce() {
            GalleryFragment.this.updateImageList();
        }

        @Override // com.aivox.app.util.SyncManager.SyncListener
        public void onImageListUpdate() {
            GalleryFragment.this.requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.GalleryFragment$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m257x470840ce();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$1$com-aivox-app-fragment-GalleryFragment */
    /* synthetic */ int m248xcf003e5b(GridLayoutManager gridLayoutManager, int i) {
        return this.mList.get(i).getSpanSize();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$2$com-aivox-app-fragment-GalleryFragment */
    /* synthetic */ void m249x261e2f3a(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        int i2;
        if (this.galleryAdapter.isDeleteMode()) {
            this.galleryAdapter.selectItem(i);
            changeDeleteText();
            GlideRequests glideRequestsWith = GlideApp.with(this);
            if (this.galleryAdapter.getSelectedCount() == 0 || !this.galleryAdapter.isFavoriteAll()) {
                i2 = C1034R.drawable.ic_gallery_favorite;
            } else {
                i2 = C1034R.drawable.ic_gallery_favorited;
            }
            glideRequestsWith.load(Integer.valueOf(i2)).into(this.mBinding.ivFavorite);
            return;
        }
        int i3 = i;
        for (int i4 = 0; i4 < i && i4 < this.mList.size(); i4++) {
            if (this.mList.get(i4).getShowTypeId() == 0) {
                i3--;
            }
        }
        LogUtil.m335d(this.TAG, "position:" + i + "\t" + i3);
        Bundle bundle = new Bundle();
        bundle.putSerializable("transcribe", getImageList());
        bundle.putBoolean("showDelete", false);
        bundle.putBoolean("showIndicator", true);
        bundle.putBoolean("isGallery", true);
        bundle.putInt("pos", i3);
        ARouterUtils.startWithContext(this.mContext, MainAction.PHOTO_BROWSE, bundle);
    }

    static /* synthetic */ boolean lambda$initBindingAndViews$3(Pair pair) throws Exception {
        return (TextUtils.isEmpty((CharSequence) pair.first) || TextUtils.isEmpty((CharSequence) pair.second)) ? false : true;
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$4$com-aivox-app-fragment-GalleryFragment */
    /* synthetic */ void m250xd45a10f8(Pair pair) throws Exception {
        this.syncManager.setWifiInfo((String) pair.first, (String) pair.second);
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$5$com-aivox-app-fragment-GalleryFragment */
    /* synthetic */ CompletableSource m251x2b7801d7(final Pair pair) throws Exception {
        return Completable.fromAction(new Action() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m250xd45a10f8(pair);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$6$com-aivox-app-fragment-GalleryFragment */
    /* synthetic */ void m252x8295f2b6() throws Exception {
        Log.d(this.TAG, "开始连接WiFi！");
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$7$com-aivox-app-fragment-GalleryFragment */
    /* synthetic */ void m253xd9b3e395(Throwable th) throws Exception {
        Log.e(this.TAG, "开始连接WiFi失败", th);
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        int i;
        Intent intent;
        if (AntiShake.check(this)) {
            return;
        }
        if (view2.getId() == C0726R.id.tv_all) {
            this.galleryType = 0;
            changeGalleryType();
            return;
        }
        if (view2.getId() == C0726R.id.tv_photo) {
            this.galleryType = 1;
            changeGalleryType();
            return;
        }
        if (view2.getId() == C0726R.id.tv_video) {
            this.galleryType = 2;
            changeGalleryType();
            return;
        }
        if (view2.getId() == C0726R.id.tv_favorite) {
            this.galleryType = 3;
            changeGalleryType();
            return;
        }
        if (view2.getId() == C0726R.id.tv_sync) {
            if (!this.enableSyncClick) {
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
            this.enableSyncClick = false;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2275lambda$onViewClick$8$comaivoxappfragmentGalleryFragment();
                }
            }, 3000L);
            if (this.syncManager.isSyncing()) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.syncing));
                return;
            }
            this.mBinding.cpi.setProgress(0);
            this.mBinding.tvProgress.setText("");
            if (((Integer) SPUtil.get(SPUtil.GLASS_BATTERY, 0)).intValue() > 15) {
                this.syncManager.startSync();
                BleBtService.getInstance().sendGlassCmd(GlassesCmd.WIFI_AP_CONTROL, 1);
                return;
            } else {
                DialogUtils.showDialogWithBtnIds(requireContext(), Integer.valueOf(C0874R.string.reminder), getString(C0874R.string.low_battery_warning), null, null, false, false, C0874R.string.sure, C0874R.string.sure);
                return;
            }
        }
        if (view2.getId() == C0726R.id.iv_cancel) {
            if (!this.enableSyncClick) {
                ToastUtil.showShort(getString(C0874R.string.operation_too_fast));
                return;
            }
            this.enableSyncClick = false;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2276lambda$onViewClick$9$comaivoxappfragmentGalleryFragment();
                }
            }, 3000L);
            SyncManager syncManager = this.syncManager;
            if (syncManager != null) {
                syncManager.cancelSync();
                return;
            }
            return;
        }
        if (view2.getId() == C0726R.id.iv_settings) {
            this.galleryAdapter.reverseDeleteMode();
            refreshDeleteMode();
            return;
        }
        if (view2.getId() == C0726R.id.tv_select_all) {
            this.galleryAdapter.selectAllItem(!r12.isSelectedAll());
            changeDeleteText();
            return;
        }
        if (view2.getId() == C0726R.id.tv_complete) {
            this.galleryAdapter.reverseDeleteMode();
            this.galleryAdapter.selectAllItem(false);
            refreshDeleteMode();
            return;
        }
        if (view2.getId() == C0726R.id.ll_save) {
            DialogUtils.showLoadingDialog(requireContext());
            this.mDis.add(Observable.fromIterable(this.mList).filter(new Predicate() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Predicate
                public final boolean test(Object obj) {
                    return GalleryFragment.lambda$onViewClick$10((DemoMultiEntity) obj);
                }
            }).map(new Function() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return ((DemoMultiEntity) obj).getDemoPicBean();
                }
            }).flatMapCompletable(new Function() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return this.f$0.m2272lambda$onViewClick$12$comaivoxappfragmentGalleryFragment((DemoPicBean) obj);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Action
                public final void run() throws Exception {
                    this.f$0.m2273lambda$onViewClick$13$comaivoxappfragmentGalleryFragment();
                }
            }, new Consumer() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    GalleryFragment.lambda$onViewClick$14((Throwable) obj);
                }
            }));
            return;
        }
        if (view2.getId() == C0726R.id.ll_favorite) {
            boolean zIsFavoriteAll = this.galleryAdapter.isFavoriteAll();
            LogUtil.m335d(this.TAG, "isFavorite: " + zIsFavoriteAll);
            for (DemoMultiEntity demoMultiEntity : this.mList) {
                if (demoMultiEntity.getDemoPicBean() != null && demoMultiEntity.getDemoPicBean().isSelected()) {
                    demoMultiEntity.getDemoPicBean().setFavorite(zIsFavoriteAll);
                    this.glassImageDbManager.updateFavorite(demoMultiEntity.getDemoPicBean().getId(), !zIsFavoriteAll);
                }
            }
            GlideRequests glideRequestsWith = GlideApp.with(this);
            if (!zIsFavoriteAll) {
                i = C1034R.drawable.ic_gallery_favorited;
            } else {
                i = C1034R.drawable.ic_gallery_favorite;
            }
            glideRequestsWith.load(Integer.valueOf(i)).into(this.mBinding.ivFavorite);
            return;
        }
        if (view2.getId() != C0726R.id.ll_delete || this.galleryAdapter.getSelectedCount() <= 0) {
            return;
        }
        DialogUtils.showDeleteDialog(this.mContext, Integer.valueOf(C0874R.string.delete_files_title), Integer.valueOf(C0874R.string.confirm_delete_files_prompt), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda9
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                this.f$0.m2274lambda$onViewClick$15$comaivoxappfragmentGalleryFragment(context, dialogBuilder, dialog, i2, i3, editText);
            }
        }, null, true, false, C0874R.string.delete, C0874R.string.cancel, 0);
    }

    /* JADX INFO: renamed from: lambda$onViewClick$8$com-aivox-app-fragment-GalleryFragment, reason: not valid java name */
    /* synthetic */ void m2275lambda$onViewClick$8$comaivoxappfragmentGalleryFragment() {
        this.enableSyncClick = true;
    }

    /* JADX INFO: renamed from: lambda$onViewClick$9$com-aivox-app-fragment-GalleryFragment, reason: not valid java name */
    /* synthetic */ void m2276lambda$onViewClick$9$comaivoxappfragmentGalleryFragment() {
        this.enableSyncClick = true;
    }

    static /* synthetic */ boolean lambda$onViewClick$10(DemoMultiEntity demoMultiEntity) throws Exception {
        return demoMultiEntity.getDemoPicBean() != null && demoMultiEntity.getDemoPicBean().isSelected();
    }

    /* JADX INFO: renamed from: lambda$onViewClick$12$com-aivox-app-fragment-GalleryFragment, reason: not valid java name */
    /* synthetic */ CompletableSource m2272lambda$onViewClick$12$comaivoxappfragmentGalleryFragment(final DemoPicBean demoPicBean) throws Exception {
        return Completable.fromAction(new Action() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m2271lambda$onViewClick$11$comaivoxappfragmentGalleryFragment(demoPicBean);
            }
        }).subscribeOn(Schedulers.m1898io());
    }

    /* JADX INFO: renamed from: lambda$onViewClick$11$com-aivox-app-fragment-GalleryFragment, reason: not valid java name */
    /* synthetic */ void m2271lambda$onViewClick$11$comaivoxappfragmentGalleryFragment(DemoPicBean demoPicBean) throws Exception {
        File file = new File(demoPicBean.getUrl());
        if (demoPicBean.getUrl().toLowerCase().endsWith(".mp4")) {
            GalleryUtils.saveVideoToGallery(requireContext(), file, file.getName());
        } else {
            GalleryUtils.saveImageToGallery(requireContext(), file, file.getName());
        }
    }

    /* JADX INFO: renamed from: lambda$onViewClick$13$com-aivox-app-fragment-GalleryFragment, reason: not valid java name */
    /* synthetic */ void m2273lambda$onViewClick$13$comaivoxappfragmentGalleryFragment() throws Exception {
        ToastUtil.showShort(getString(C0874R.string.save_success));
        DialogUtils.hideLoadingDialog();
    }

    static /* synthetic */ void lambda$onViewClick$14(Throwable th) throws Exception {
        ToastUtil.showShort("保存失败: " + th.getMessage());
        DialogUtils.hideLoadingDialog();
    }

    /* JADX INFO: renamed from: lambda$onViewClick$15$com-aivox-app-fragment-GalleryFragment, reason: not valid java name */
    /* synthetic */ void m2274lambda$onViewClick$15$comaivoxappfragmentGalleryFragment(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        for (DemoMultiEntity demoMultiEntity : this.mList) {
            if (demoMultiEntity.getDemoPicBean() != null && demoMultiEntity.getDemoPicBean().isSelected()) {
                if (!TextUtils.isEmpty(demoMultiEntity.getDemoPicBean().getUrl()) && !demoMultiEntity.getDemoPicBean().getUrl().startsWith("http")) {
                    if (demoMultiEntity.getDemoPicBean().getUrl().endsWith(".mp4")) {
                        FileUtils.deleteFile(new File(demoMultiEntity.getDemoPicBean().getUrl().replace(".mp4", ".jpg")).getPath());
                    }
                    FileUtils.deleteFile(new File(demoMultiEntity.getDemoPicBean().getUrl()).getPath());
                }
                this.glassImageDbManager.delete(Long.valueOf(demoMultiEntity.getDemoPicBean().getId()));
            }
        }
        updateImageList();
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void clearBinding() {
        DialogUtils.hideLoadingDialog();
        this.mBinding = null;
        SyncManager syncManager = this.syncManager;
        if (syncManager != null) {
            syncManager.release();
            this.syncManager = null;
        }
        BleDataManager.getInstance().destroy();
        this.mDis.add(this.dis);
        this.mDis.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateImageList() {
        this.mList.clear();
        String createTime = null;
        for (GlassImageEntity glassImageEntity : this.glassImageDbManager.queryGallery(this.uid, this.galleryType)) {
            if (TextUtils.isEmpty(createTime) || !createTime.equals(glassImageEntity.getCreateTime())) {
                createTime = glassImageEntity.getCreateTime();
                this.mList.add(0, new DemoMultiEntity(0, 3, glassImageEntity.getCreateTime()));
            }
            this.mList.add(1, new DemoMultiEntity(1, 1, new DemoPicBean(Math.toIntExact(glassImageEntity.getId().longValue()), glassImageEntity.getImagePath(), glassImageEntity.getCreateTime(), glassImageEntity.getDuration(), glassImageEntity.getIsFavorite())));
        }
        this.galleryAdapter.notifyDataSetChanged();
    }

    private void refreshDeleteMode() {
        this.mBinding.tvSync.setVisibility(this.galleryAdapter.isDeleteMode() ? 8 : 0);
        this.mBinding.ivSettings.setVisibility(this.galleryAdapter.isDeleteMode() ? 8 : 0);
        this.mBinding.llTab.setVisibility(this.galleryAdapter.isDeleteMode() ? 4 : 0);
        this.mBinding.tvSelectAll.setVisibility(this.galleryAdapter.isDeleteMode() ? 0 : 8);
        this.mBinding.tvComplete.setVisibility(this.galleryAdapter.isDeleteMode() ? 0 : 8);
        EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_HIDE_TAB, this.galleryAdapter.isDeleteMode()));
        this.mBinding.glFunction.setVisibility(this.galleryAdapter.isDeleteMode() ? 0 : 8);
        changeDeleteText();
    }

    private void changeGalleryType() {
        int i;
        int i2;
        int i3;
        int i4;
        this.mBinding.tvAll.setTypeface(this.galleryType == 0 ? Typeface.defaultFromStyle(1) : Typeface.DEFAULT);
        TextView textView = this.mBinding.tvAll;
        if (this.galleryType == 0) {
            i = C1034R.drawable.bg_white_c10;
        } else {
            i = C1034R.drawable.bg_gray_c12;
        }
        textView.setBackgroundResource(i);
        this.mBinding.tvPhoto.setTypeface(this.galleryType == 1 ? Typeface.defaultFromStyle(1) : Typeface.DEFAULT);
        TextView textView2 = this.mBinding.tvPhoto;
        if (this.galleryType == 1) {
            i2 = C1034R.drawable.bg_white_c10;
        } else {
            i2 = C1034R.drawable.bg_gray_c12;
        }
        textView2.setBackgroundResource(i2);
        this.mBinding.tvVideo.setTypeface(this.galleryType == 2 ? Typeface.defaultFromStyle(1) : Typeface.DEFAULT);
        TextView textView3 = this.mBinding.tvVideo;
        if (this.galleryType == 2) {
            i3 = C1034R.drawable.bg_white_c10;
        } else {
            i3 = C1034R.drawable.bg_gray_c12;
        }
        textView3.setBackgroundResource(i3);
        this.mBinding.tvFavorite.setTypeface(this.galleryType == 3 ? Typeface.defaultFromStyle(1) : Typeface.DEFAULT);
        TextView textView4 = this.mBinding.tvFavorite;
        if (this.galleryType == 3) {
            i4 = C1034R.drawable.bg_white_c10;
        } else {
            i4 = C1034R.drawable.bg_gray_c12;
        }
        textView4.setBackgroundResource(i4);
        updateImageList();
    }

    private void changeDeleteText() {
        if (this.galleryAdapter.isDeleteMode()) {
            this.mBinding.tvSelectCount.setText(String.format(getString(C0874R.string.items_selected_count), Integer.valueOf(this.galleryAdapter.getSelectedCount())));
        }
    }

    private Transcribe getImageList() {
        Transcribe transcribe = new Transcribe();
        ArrayList arrayList = new ArrayList();
        for (DemoMultiEntity demoMultiEntity : this.mList) {
            if (demoMultiEntity.getShowTypeId() == 1) {
                arrayList.add(new Transcribe.TagImgBean(demoMultiEntity.getDemoPicBean().getId(), demoMultiEntity.getDemoPicBean().getUrl()));
            }
        }
        transcribe.setImageList(arrayList);
        return transcribe;
    }

    /* JADX INFO: renamed from: com.aivox.app.fragment.GalleryFragment$2 */
    static /* synthetic */ class C08082 {
        static final /* synthetic */ int[] $SwitchMap$com$aivox$app$util$SyncManager$SyncState;
        static final /* synthetic */ int[] $SwitchMap$com$aivox$base$common$GlassesCmd;

        static {
            int[] iArr = new int[SyncManager.SyncState.values().length];
            $SwitchMap$com$aivox$app$util$SyncManager$SyncState = iArr;
            try {
                iArr[SyncManager.SyncState.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aivox$app$util$SyncManager$SyncState[SyncManager.SyncState.CONNECTING_WIFI.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aivox$app$util$SyncManager$SyncState[SyncManager.SyncState.FETCHING_FILE_LIST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aivox$app$util$SyncManager$SyncState[SyncManager.SyncState.DOWNLOADING_FILES.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$aivox$app$util$SyncManager$SyncState[SyncManager.SyncState.DELETING_FILES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr2 = new int[GlassesCmd.values().length];
            $SwitchMap$com$aivox$base$common$GlassesCmd = iArr2;
            try {
                iArr2[GlassesCmd.REPORT_WIFI_STATUS.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.REPORT_AP_SSID.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.REPORT_AP_PASSWORD.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.REPORT_WIFI_API.ordinal()] = 4;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.FILE_COUNT_UPDATE.ordinal()] = 5;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.IMAGE_CAPTURE_FINISH.ordinal()] = 6;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.GET_OTA_INFO.ordinal()] = 7;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUiForSyncState(final SyncManager.SyncState syncState) {
        requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m255xd34387f5(syncState);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$updateUiForSyncState$16$com-aivox-app-fragment-GalleryFragment */
    /* synthetic */ void m255xd34387f5(SyncManager.SyncState syncState) {
        int i = C08082.$SwitchMap$com$aivox$app$util$SyncManager$SyncState[syncState.ordinal()];
        if (i == 1) {
            this.mBinding.rlSync.setVisibility(this.fileCount > 0 ? 0 : 8);
            this.mBinding.llSync.setVisibility(this.fileCount > 0 ? 0 : 8);
            this.mBinding.clSyncProgress.setVisibility(8);
            this.mBinding.cpi.setProgress(0);
            this.mBinding.tvProgress.setText("");
            this.mBinding.tvFile.setText(getString(C0874R.string.syncing));
            requireActivity().getWindow().clearFlags(128);
            return;
        }
        if (i == 2) {
            this.mBinding.rlSync.setVisibility(0);
            this.mBinding.llSync.setVisibility(8);
            this.mBinding.clSyncProgress.setVisibility(0);
            this.mBinding.tvFile.setText(getString(C0874R.string.connecting_wifi));
            requireActivity().getWindow().addFlags(128);
            return;
        }
        if (i == 3) {
            this.mBinding.rlSync.setVisibility(0);
            this.mBinding.llSync.setVisibility(8);
            this.mBinding.clSyncProgress.setVisibility(0);
            this.mBinding.tvFile.setText(getString(C0874R.string.get_file_list));
            return;
        }
        if (i == 4) {
            this.mBinding.rlSync.setVisibility(0);
            this.mBinding.llSync.setVisibility(8);
            this.mBinding.clSyncProgress.setVisibility(0);
            this.mBinding.tvFile.setText(getString(C0874R.string.importing));
            return;
        }
        if (i != 5) {
            return;
        }
        this.mBinding.rlSync.setVisibility(0);
        this.mBinding.llSync.setVisibility(8);
        this.mBinding.clSyncProgress.setVisibility(0);
        this.mBinding.tvFile.setText(getString(C0874R.string.clean_device_files));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFileCountUI() {
        requireActivity().runOnUiThread(new Runnable() { // from class: com.aivox.app.fragment.GalleryFragment$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m254xab89ac6e();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$updateFileCountUI$17$com-aivox-app-fragment-GalleryFragment */
    /* synthetic */ void m254xab89ac6e() {
        if (this.fileCount == 0) {
            this.mBinding.tvFileCount.setText("");
        } else {
            this.mBinding.tvFileCount.setText(String.format(getResources().getString(C0874R.string.files_pending_import), Integer.valueOf(this.fileCount)));
        }
    }
}
