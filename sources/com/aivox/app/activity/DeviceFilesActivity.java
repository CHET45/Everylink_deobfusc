package com.aivox.app.activity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.PopupWindow;
import androidx.constraintlayout.motion.widget.Key;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityDeviceFilesBinding;
import com.aivox.app.test.denoise.AudioDownloadUtil;
import com.aivox.app.util.SwipedSelectItemTouchCallBack;
import com.aivox.app.view.ItemDeletePopup;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.img.imageloader.FileSizeUtil;
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
import com.aivox.common.model.DeviceFileBean;
import com.aivox.common.model.EventBean;
import com.aivox.common.p003db.entity.DeviceFileEntity;
import com.aivox.common.p003db.maneger.DeviceFileDbManager;
import com.aivox.common.parse.SendManager;
import com.aivox.common_ui.C1034R;
import com.aivox.libOpus.utils.OpusDecoder;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public class DeviceFilesActivity extends BaseFragmentActivity {
    private FileAdapter mAdapter;
    private ActivityDeviceFilesBinding mBinding;
    private DeviceFileDbManager mDbManager;
    private String mDeviceWifiIp;
    private String mDeviceWifiPort;
    private String mDeviceWifiPwd;
    private AudioDownloadUtil mDownloadUtil;
    private ItemDeletePopup mItemDeletePop;
    private OpusDecoder mOpusDecoder;
    private String mUid;
    private final String TAG = "DeviceFilesActivity";
    private final String DATA_DIVIDER = "-";
    private final List<DeviceFileBean.FileList> mList = new ArrayList();
    private String mDeviceWifiName = "";
    private int mPage = 1;

    public interface ResponseCallback {
        void onFailure(String str);

        void onSuccess(String str);
    }

    static /* synthetic */ int access$810(DeviceFilesActivity deviceFilesActivity) {
        int i = deviceFilesActivity.mPage;
        deviceFilesActivity.mPage = i - 1;
        return i;
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityDeviceFilesBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_device_files);
        DialogUtils.showLoadingDialog(this.context);
        SendManager.getInstance().sendSppData(Constant.CmdUpOpenWiFi);
        this.mDbManager = DeviceFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
        this.mUid = (String) SPUtil.get(SPUtil.USER_ID, "0");
        this.mOpusDecoder = new OpusDecoder(16000, 1);
        ItemDeletePopup itemDeletePopupCreate = ItemDeletePopup.create(this.context, new ItemDeletePopup.OnDeleteListener() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda12
            @Override // com.aivox.app.view.ItemDeletePopup.OnDeleteListener
            public final void delete() {
                this.f$0.m2078lambda$initView$1$comaivoxappactivityDeviceFilesActivity();
            }
        });
        this.mItemDeletePop = itemDeletePopupCreate;
        itemDeletePopupCreate.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda13
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                this.f$0.m2080lambda$initView$2$comaivoxappactivityDeviceFilesActivity();
            }
        });
        this.mBinding.ivCloseX.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2081lambda$initView$3$comaivoxappactivityDeviceFilesActivity(view2);
            }
        });
        this.mDownloadUtil = new AudioDownloadUtil();
        this.mAdapter = new FileAdapter(C1034R.layout.device_file_item_layout, this.mList);
        this.mBinding.rvDeviceFiles.setLayoutManager(new LinearLayoutManager(this.context));
        this.mBinding.rvDeviceFiles.setAdapter(this.mAdapter);
        SwipedSelectItemTouchCallBack swipedSelectItemTouchCallBack = new SwipedSelectItemTouchCallBack();
        swipedSelectItemTouchCallBack.setOnItemTouchListener(new SwipedSelectItemTouchCallBack.OnItemSwipeListener() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda15
            @Override // com.aivox.app.util.SwipedSelectItemTouchCallBack.OnItemSwipeListener
            public final void onSwipeSelected(int i) {
                this.f$0.m2082lambda$initView$4$comaivoxappactivityDeviceFilesActivity(i);
            }
        });
        new ItemTouchHelper(swipedSelectItemTouchCallBack).attachToRecyclerView(this.mBinding.rvDeviceFiles);
        this.mBinding.titleView.setOnBackListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2083lambda$initView$5$comaivoxappactivityDeviceFilesActivity(view2);
            }
        });
        this.mBinding.titleView.setRightIconListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2084lambda$initView$6$comaivoxappactivityDeviceFilesActivity(view2);
            }
        });
        this.mBinding.btnConnect.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2085lambda$initView$7$comaivoxappactivityDeviceFilesActivity(view2);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m2086lambda$initView$8$comaivoxappactivityDeviceFilesActivity(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildLongClickListener
            public final boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                return this.f$0.m2087lambda$initView$9$comaivoxappactivityDeviceFilesActivity(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setEnableLoadMore(false);
        this.mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
            public final void onLoadMoreRequested() {
                this.f$0.m2079lambda$initView$10$comaivoxappactivityDeviceFilesActivity();
            }
        }, this.mBinding.rvDeviceFiles);
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-DeviceFilesActivity, reason: not valid java name */
    /* synthetic */ void m2078lambda$initView$1$comaivoxappactivityDeviceFilesActivity() {
        DialogUtils.showDeleteDialog(this.context, "", Integer.valueOf(C0874R.string.audio_delete_remind), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda10
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m2077lambda$initView$0$comaivoxappactivityDeviceFilesActivity(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, null, true, true, C0874R.string.delete, C0874R.string.cancel, 0);
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-DeviceFilesActivity, reason: not valid java name */
    /* synthetic */ void m2077lambda$initView$0$comaivoxappactivityDeviceFilesActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        if (BaseStringUtil.isEmpty(this.mAdapter.getSelectedFileName())) {
            this.mItemDeletePop.dismiss();
        } else {
            deleteDeviceFile(this.mAdapter.getSelectedFileName(), new C07321());
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.DeviceFilesActivity$1 */
    class C07321 implements ResponseCallback {
        C07321() {
        }

        @Override // com.aivox.app.activity.DeviceFilesActivity.ResponseCallback
        public void onSuccess(String str) {
            DeviceFilesActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceFilesActivity$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2090lambda$onSuccess$1$comaivoxappactivityDeviceFilesActivity$1();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSuccess$0$com-aivox-app-activity-DeviceFilesActivity$1, reason: not valid java name */
        /* synthetic */ boolean m2089lambda$onSuccess$0$comaivoxappactivityDeviceFilesActivity$1(DeviceFileBean.FileList fileList) {
            return fileList.getName().equals(DeviceFilesActivity.this.mAdapter.getSelectedFileName());
        }

        /* JADX INFO: renamed from: lambda$onSuccess$1$com-aivox-app-activity-DeviceFilesActivity$1, reason: not valid java name */
        /* synthetic */ void m2090lambda$onSuccess$1$comaivoxappactivityDeviceFilesActivity$1() {
            int iIndexOf = DeviceFilesActivity.this.mList.indexOf(DeviceFilesActivity.this.mList.stream().filter(new Predicate() { // from class: com.aivox.app.activity.DeviceFilesActivity$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.m2089lambda$onSuccess$0$comaivoxappactivityDeviceFilesActivity$1((DeviceFileBean.FileList) obj);
                }
            }).findFirst().orElse(null));
            DeviceFilesActivity.this.mList.remove(iIndexOf);
            DeviceFilesActivity.this.mAdapter.notifyItemRemoved(iIndexOf);
            DeviceFilesActivity.this.mItemDeletePop.dismiss();
        }

        /* JADX INFO: renamed from: lambda$onFailure$2$com-aivox-app-activity-DeviceFilesActivity$1, reason: not valid java name */
        /* synthetic */ void m2088lambda$onFailure$2$comaivoxappactivityDeviceFilesActivity$1(String str) {
            ToastUtil.showShort(DeviceFilesActivity.this.getString(C0874R.string.audio_delete_fail) + ":" + str);
        }

        @Override // com.aivox.app.activity.DeviceFilesActivity.ResponseCallback
        public void onFailure(final String str) {
            DeviceFilesActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceFilesActivity$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2088lambda$onFailure$2$comaivoxappactivityDeviceFilesActivity$1(str);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-app-activity-DeviceFilesActivity, reason: not valid java name */
    /* synthetic */ void m2080lambda$initView$2$comaivoxappactivityDeviceFilesActivity() {
        switchListMode(false, false);
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-app-activity-DeviceFilesActivity, reason: not valid java name */
    /* synthetic */ void m2081lambda$initView$3$comaivoxappactivityDeviceFilesActivity(View view2) {
        this.mItemDeletePop.dismiss();
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-app-activity-DeviceFilesActivity, reason: not valid java name */
    /* synthetic */ void m2082lambda$initView$4$comaivoxappactivityDeviceFilesActivity(int i) {
        switchListMode(true, true);
        this.mAdapter.selectItem(i);
        this.mItemDeletePop.show(this.mBinding.main, this.mBinding.main.getBottom());
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-app-activity-DeviceFilesActivity, reason: not valid java name */
    /* synthetic */ void m2083lambda$initView$5$comaivoxappactivityDeviceFilesActivity(View view2) {
        onBackPressed();
    }

    /* JADX INFO: renamed from: lambda$initView$6$com-aivox-app-activity-DeviceFilesActivity, reason: not valid java name */
    /* synthetic */ void m2084lambda$initView$6$comaivoxappactivityDeviceFilesActivity(View view2) {
        switchListMode(true, false);
        this.mItemDeletePop.show(this.mBinding.main, this.mBinding.main.getBottom());
    }

    /* JADX INFO: renamed from: lambda$initView$7$com-aivox-app-activity-DeviceFilesActivity, reason: not valid java name */
    /* synthetic */ void m2085lambda$initView$7$comaivoxappactivityDeviceFilesActivity(View view2) {
        DialogUtils.showLoadingDialog(this.context);
        startActivity(new Intent("android.settings.WIFI_SETTINGS"));
    }

    /* JADX INFO: renamed from: lambda$initView$8$com-aivox-app-activity-DeviceFilesActivity, reason: not valid java name */
    /* synthetic */ void m2086lambda$initView$8$comaivoxappactivityDeviceFilesActivity(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        if (view2.getId() == C1034R.id.iv_file_download) {
            downloadOpus(i);
        } else if (view2.getId() == C1034R.id.cl_file_content && this.mAdapter.getMode() == 1) {
            this.mAdapter.selectItem(i);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$9$com-aivox-app-activity-DeviceFilesActivity, reason: not valid java name */
    /* synthetic */ boolean m2087lambda$initView$9$comaivoxappactivityDeviceFilesActivity(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        switchListMode(true, true);
        this.mAdapter.selectItem(i);
        this.mItemDeletePop.show(this.mBinding.main, this.mBinding.main.getBottom());
        return true;
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.DeviceFilesActivity$2 */
    class C07332 implements ResponseCallback {
        C07332() {
        }

        @Override // com.aivox.app.activity.DeviceFilesActivity.ResponseCallback
        public void onSuccess(final String str) {
            DeviceFilesActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceFilesActivity$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2091lambda$onSuccess$0$comaivoxappactivityDeviceFilesActivity$2(str);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSuccess$0$com-aivox-app-activity-DeviceFilesActivity$2, reason: not valid java name */
        /* synthetic */ void m2091lambda$onSuccess$0$comaivoxappactivityDeviceFilesActivity$2(String str) {
            LogUtil.m335d("DeviceFilesActivity", str);
            DeviceFileBean deviceFileBean = (DeviceFileBean) JSONObject.parseObject(str, DeviceFileBean.class);
            if (deviceFileBean != null) {
                DeviceFilesActivity.this.mList.addAll(deviceFileBean.getFileList());
                LogUtil.m335d("DeviceFilesActivity", "DATA_SIZE:" + DeviceFilesActivity.this.mList.size());
                if (DeviceFilesActivity.this.mList.size() == deviceFileBean.getTotal().intValue()) {
                    DeviceFilesActivity.this.mAdapter.setEnableLoadMore(false);
                    ((DeviceFileBean.FileList) DeviceFilesActivity.this.mList.get(DeviceFilesActivity.this.mList.size() - 1)).setLast(true);
                }
                DeviceFilesActivity.this.mAdapter.notifyItemRangeChanged(DeviceFilesActivity.this.mList.size() - deviceFileBean.getFileList().size(), deviceFileBean.getFileList().size());
                DeviceFilesActivity.this.mAdapter.loadMoreComplete();
            }
        }

        @Override // com.aivox.app.activity.DeviceFilesActivity.ResponseCallback
        public void onFailure(String str) {
            DeviceFilesActivity.access$810(DeviceFilesActivity.this);
            LogUtil.m336e(str);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$10$com-aivox-app-activity-DeviceFilesActivity, reason: not valid java name */
    /* synthetic */ void m2079lambda$initView$10$comaivoxappactivityDeviceFilesActivity() {
        int i = this.mPage + 1;
        this.mPage = i;
        getDeviceFileList(i, 20, new C07332());
    }

    private void switchListMode(boolean z, boolean z2) {
        this.mBinding.titleView.setVisibility(z ? 4 : 0);
        this.mBinding.ivCloseX.setVisibility(z ? 0 : 4);
        this.mAdapter.switchMode(z ? 1 : 0, z2);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mItemDeletePop.isShowing()) {
            this.mItemDeletePop.dismiss();
            return;
        }
        if (CollectionUtils.isNotEmpty(this.mAdapter.getDownloadingItems())) {
            DialogUtils.showDialogWithDefBtn(this.context, "", Integer.valueOf(C0874R.string.quit_and_stop_download), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda7
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m109x76145f9e(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, true, true);
        } else {
            if (this.mDeviceWifiName.contains("WIFI_DIGI_GIFT")) {
                DialogUtils.showLoadingDialog(this.context, "", false);
                SendManager.getInstance().sendSppData(Constant.CmdUpCloseWiFi);
                this.mBinding.tvWifiNotice.postDelayed(new Runnable() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m110x3900c8fd();
                    }
                }, 5000L);
                return;
            }
            super.onBackPressed();
        }
    }

    /* JADX INFO: renamed from: lambda$onBackPressed$12$com-aivox-app-activity-DeviceFilesActivity */
    /* synthetic */ void m109x76145f9e(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        DialogUtils.showLoadingDialog(context, "", false);
        AudioDownloadUtil audioDownloadUtil = this.mDownloadUtil;
        if (audioDownloadUtil != null) {
            audioDownloadUtil.destroy();
        }
        SendManager.getInstance().sendSppData(Constant.CmdUpCloseWiFi);
        this.mBinding.tvWifiNotice.postDelayed(new Runnable() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m108xb327f63f();
            }
        }, 5000L);
    }

    /* JADX INFO: renamed from: lambda$onBackPressed$11$com-aivox-app-activity-DeviceFilesActivity */
    /* synthetic */ void m108xb327f63f() {
        DialogUtils.hideLoadingDialog();
        super.onBackPressed();
    }

    /* JADX INFO: renamed from: lambda$onBackPressed$13$com-aivox-app-activity-DeviceFilesActivity */
    /* synthetic */ void m110x3900c8fd() {
        DialogUtils.hideLoadingDialog();
        super.onBackPressed();
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void onEventMainThread(EventBean eventBean) {
        super.onEventMainThread(eventBean);
        int from = eventBean.getFrom();
        if (from == 45) {
            updateFilesList();
            return;
        }
        if (from == 301 || from == 308) {
            finish();
            return;
        }
        if (from != 351) {
            if (from != 352) {
                return;
            }
            ToastUtil.showLong(Integer.valueOf(C0874R.string.earphone_wifi_closing));
            return;
        }
        JSONObject object = JSONObject.parseObject(eventBean.getS1().split("-")[2]);
        if (object != null) {
            this.mDeviceWifiName = object.getString("wifi_name");
            this.mDeviceWifiPwd = object.getString("wifi_password");
            this.mDeviceWifiIp = object.getString("ip");
            this.mDeviceWifiPort = object.getString("port");
            this.mBinding.tvWifiName.setText(this.mDeviceWifiName);
            this.mBinding.tvWifiPwd.setText(this.mDeviceWifiPwd);
            this.mBinding.btnConnect.setVisibility(0);
        }
        DialogUtils.hideLoadingDialog();
    }

    private void updateFilesList() {
        LogUtil.m335d("DeviceFilesActivity", "SSID : " + getCurrentSSID());
        if (getCurrentSSID().equals(this.mDeviceWifiName) && this.mBinding.llWifiInfo.getVisibility() == 0) {
            this.mBinding.llWifiInfo.setVisibility(8);
            LogUtil.m335d("DeviceFilesActivity", "开始请求文件列表");
            getDeviceFileList(this.mPage, 20, new C07343());
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.DeviceFilesActivity$3 */
    class C07343 implements ResponseCallback {
        C07343() {
        }

        @Override // com.aivox.app.activity.DeviceFilesActivity.ResponseCallback
        public void onSuccess(final String str) {
            DeviceFilesActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceFilesActivity$3$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2093lambda$onSuccess$0$comaivoxappactivityDeviceFilesActivity$3(str);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSuccess$0$com-aivox-app-activity-DeviceFilesActivity$3, reason: not valid java name */
        /* synthetic */ void m2093lambda$onSuccess$0$comaivoxappactivityDeviceFilesActivity$3(String str) {
            LogUtil.m335d("DeviceFilesActivity", str);
            DialogUtils.hideLoadingDialog();
            DeviceFileBean deviceFileBean = (DeviceFileBean) JSONObject.parseObject(str, DeviceFileBean.class);
            if (deviceFileBean != null) {
                DeviceFilesActivity.this.mList.addAll(deviceFileBean.getFileList());
                LogUtil.m335d("DeviceFilesActivity", "DATA_SIZE:" + DeviceFilesActivity.this.mList.size());
                if (DeviceFilesActivity.this.mList.size() < deviceFileBean.getTotal().intValue()) {
                    DeviceFilesActivity.this.mAdapter.setEnableLoadMore(true);
                } else {
                    DeviceFilesActivity.this.mAdapter.setEnableLoadMore(false);
                    ((DeviceFileBean.FileList) DeviceFilesActivity.this.mList.get(DeviceFilesActivity.this.mList.size() - 1)).setLast(true);
                }
                DeviceFilesActivity.this.mBinding.rvDeviceFiles.setVisibility(0);
                DeviceFilesActivity.this.mBinding.titleView.setRightIconVisible(0);
                DeviceFilesActivity.this.mAdapter.notifyDataSetChanged();
            }
        }

        @Override // com.aivox.app.activity.DeviceFilesActivity.ResponseCallback
        public void onFailure(final String str) {
            LogUtil.m336e(str);
            DeviceFilesActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceFilesActivity$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2092lambda$onFailure$1$comaivoxappactivityDeviceFilesActivity$3(str);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onFailure$1$com-aivox-app-activity-DeviceFilesActivity$3, reason: not valid java name */
        /* synthetic */ void m2092lambda$onFailure$1$comaivoxappactivityDeviceFilesActivity$3(String str) {
            ToastUtil.showShort(DeviceFilesActivity.this.getString(C0874R.string.list_loading_failed) + ":" + str);
        }
    }

    private void downloadOpus(final int i) {
        this.mAdapter.updateItemDownloadStatus(this.mList.get(i), 0);
        if (this.mAdapter.getDownloadingItems().size() > 1) {
            return;
        }
        new Thread(new Runnable() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m107x6f23081(i);
            }
        }).start();
    }

    /* JADX INFO: renamed from: lambda$downloadOpus$14$com-aivox-app-activity-DeviceFilesActivity */
    /* synthetic */ void m107x6f23081(int i) {
        this.mDownloadUtil.start(this.context, "http://" + this.mDeviceWifiIp + ":" + this.mDeviceWifiPort + "/file_server?cmd=download&file_name=" + this.mList.get(i).getName(), new C07354(i));
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.DeviceFilesActivity$4 */
    class C07354 implements AudioDownloadUtil.IAudioDownloadListener {
        final /* synthetic */ int val$position;

        C07354(int i) {
            this.val$position = i;
        }

        @Override // com.aivox.app.test.denoise.AudioDownloadUtil.IAudioDownloadListener
        public void onPrepare(String str) {
            LogUtil.m339i("DeviceFilesActivity", "准备下载:" + str);
            DeviceFilesActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceFilesActivity$4$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.in_progress));
                }
            });
        }

        @Override // com.aivox.app.test.denoise.AudioDownloadUtil.IAudioDownloadListener
        public void onError(final String str) {
            LogUtil.m339i("DeviceFilesActivity", "下载异常:" + str);
            DeviceFilesActivity deviceFilesActivity = DeviceFilesActivity.this;
            final int i = this.val$position;
            deviceFilesActivity.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceFilesActivity$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2095lambda$onError$1$comaivoxappactivityDeviceFilesActivity$4(str, i);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onError$1$com-aivox-app-activity-DeviceFilesActivity$4, reason: not valid java name */
        /* synthetic */ void m2095lambda$onError$1$comaivoxappactivityDeviceFilesActivity$4(String str, int i) {
            ToastUtil.showShort(str);
            DeviceFilesActivity.this.mAdapter.updateItemDownloadStatus((DeviceFileBean.FileList) DeviceFilesActivity.this.mList.get(i), 2);
        }

        @Override // com.aivox.app.test.denoise.AudioDownloadUtil.IAudioDownloadListener
        public void onComplete(final File file) {
            LogUtil.m339i("DeviceFilesActivity", "下载完成:" + file.length());
            DeviceFilesActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceFilesActivity$4$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2094lambda$onComplete$2$comaivoxappactivityDeviceFilesActivity$4(file);
                }
            });
            DeviceFilesActivity.this.decodeOpusAndConvertToWav(file, this.val$position);
        }

        /* JADX INFO: renamed from: lambda$onComplete$2$com-aivox-app-activity-DeviceFilesActivity$4, reason: not valid java name */
        /* synthetic */ void m2094lambda$onComplete$2$comaivoxappactivityDeviceFilesActivity$4(File file) {
            ToastUtil.showLong(DeviceFilesActivity.this.getString(C0874R.string.success) + ":" + file.getAbsolutePath());
        }

        @Override // com.aivox.app.test.denoise.AudioDownloadUtil.IAudioDownloadListener
        public void onProgress(long j, long j2) {
            LogUtil.m339i("DeviceFilesActivity", "下载进度:" + j + "/" + j2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decodeOpusAndConvertToWav(final File file, final int i) {
        final File file2 = new File(file.getAbsolutePath().replace(".opus", ".pcm"));
        ThreadUtils.executeByIo(new ThreadUtils.SimpleTask<Boolean>() { // from class: com.aivox.app.activity.DeviceFilesActivity.5
            @Override // com.blankj.utilcode.util.ThreadUtils.Task
            public Boolean doInBackground() {
                FileOutputStream fileOutputStream;
                short[] sArr;
                byte[] bArr;
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        fileOutputStream = new FileOutputStream(file2);
                        try {
                            sArr = new short[5760];
                            bArr = new byte[4096];
                        } finally {
                        }
                    } finally {
                    }
                    while (true) {
                        int i2 = fileInputStream.read(bArr);
                        if (i2 > 0) {
                            byte[] bArr2 = new byte[i2];
                            System.arraycopy(bArr, 0, bArr2, 0, i2);
                            int iDecode = DeviceFilesActivity.this.mOpusDecoder.decode(bArr2, sArr);
                            if (iDecode <= 0) {
                                LogUtil.m337e("DeviceFilesActivity", "Opus decoding failed for a packet. Error code: " + iDecode);
                            } else {
                                byte[] bArr3 = new byte[iDecode * 2];
                                for (int i3 = 0; i3 < iDecode; i3++) {
                                    int i4 = i3 * 2;
                                    short s = sArr[i3];
                                    bArr3[i4] = (byte) (s & 255);
                                    bArr3[i4 + 1] = (byte) ((s >> 8) & 255);
                                }
                                fileOutputStream.write(bArr3);
                            }
                        } else {
                            fileOutputStream.close();
                            fileInputStream.close();
                            return true;
                        }
                    }
                } catch (IOException e) {
                    LogUtil.m337e("DeviceFilesActivity", "File IO or decoding error: " + e.getMessage());
                    return false;
                }
            }

            @Override // com.blankj.utilcode.util.ThreadUtils.Task
            public void onSuccess(Boolean bool) {
                if (bool.booleanValue()) {
                    LogUtil.m339i("DeviceFilesActivity", "解码完成:" + file2.getAbsolutePath());
                    FileUtils.deleteFile(file);
                    DeviceFilesActivity.this.convertPcmToWav(file2.getAbsolutePath(), i);
                    return;
                }
                LogUtil.m337e("DeviceFilesActivity", "解码失败");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void convertPcmToWav(String str, int i) {
        LogUtil.m339i("DeviceFilesActivity", "转换完成:" + str.replace(".pcm", PictureMimeType.WAV));
        FileUtils.deleteFile(str);
        final DeviceFileBean.FileList fileList = this.mList.get(i);
        DeviceFileEntity deviceFileEntity = new DeviceFileEntity();
        deviceFileEntity.setFileId(fileList.getId().intValue());
        deviceFileEntity.setName(this.mAdapter.formatFileName(fileList.getName()));
        deviceFileEntity.setFileSize(fileList.getSize().longValue());
        deviceFileEntity.setSynced(false);
        deviceFileEntity.setUid(this.mUid);
        deviceFileEntity.setLocalPath(str.replace(".pcm", PictureMimeType.WAV));
        this.mDbManager.insertOrReplace(deviceFileEntity);
        if (isFinishing() || isDestroyed()) {
            return;
        }
        deleteDeviceFile(fileList.getName(), new ResponseCallback() { // from class: com.aivox.app.activity.DeviceFilesActivity.6
            @Override // com.aivox.app.activity.DeviceFilesActivity.ResponseCallback
            public void onFailure(String str2) {
            }

            @Override // com.aivox.app.activity.DeviceFilesActivity.ResponseCallback
            public void onSuccess(String str2) {
                LogUtil.m337e("DeviceFilesActivity", str2);
            }
        });
        ThreadUtils.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceFilesActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m106xc4cd1d29(fileList);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$convertPcmToWav$15$com-aivox-app-activity-DeviceFilesActivity */
    /* synthetic */ void m106xc4cd1d29(DeviceFileBean.FileList fileList) {
        this.mAdapter.updateItemDownloadStatus(fileList, 1);
        if (CollectionUtils.isNotEmpty(this.mAdapter.getDownloadingItems())) {
            downloadOpus(this.mList.indexOf(this.mAdapter.getDownloadingItems().get(0)));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        updateFilesList();
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        OpusDecoder opusDecoder = this.mOpusDecoder;
        if (opusDecoder != null) {
            opusDecoder.release();
            this.mOpusDecoder = null;
        }
    }

    private String getCurrentSSID() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if (networkCapabilities != null && networkCapabilities.hasTransport(1)) {
            String ssid = ((WifiManager) getApplicationContext().getSystemService("wifi")).getConnectionInfo().getSSID();
            if (ssid.startsWith(PunctuationConst.DOUBLE_QUOTES) && ssid.endsWith(PunctuationConst.DOUBLE_QUOTES)) {
                ssid = ssid.substring(1, ssid.length() - 1);
            }
            return (ssid.equals("<unknown ssid>") || TextUtils.isEmpty(ssid)) ? "unknown ssid or empty" : ssid;
        }
        return "wifi is disable";
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class FileAdapter extends BaseQuickAdapter<DeviceFileBean.FileList, BaseViewHolder> {
        public static final int MODE_NORMAL = 0;
        public static final int MODE_SELECT = 1;
        private int mCurMode;
        private final List<DeviceFileBean.FileList> mDownloadingItems;
        private String mSelectedItemName;

        public FileAdapter(int i, List<DeviceFileBean.FileList> list) {
            super(i, list);
            this.mDownloadingItems = new ArrayList();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, DeviceFileBean.FileList fileList) {
            long j;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(baseViewHolder.itemView.findViewById(C1034R.id.iv_file_loading), Key.ROTATION, 0.0f, 360.0f);
            objectAnimatorOfFloat.setDuration(800L);
            objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
            objectAnimatorOfFloat.setRepeatCount(-1);
            try {
                j = Long.parseLong(fileList.getName().replace(".opus", "").split(PunctuationConst.UNDERLINE)[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                j = 0;
            }
            baseViewHolder.setText(C1034R.id.tv_file_name, formatFileName(fileList.getName()));
            baseViewHolder.setText(C1034R.id.tv_file_date, DateUtil.getDateFromTimestamp(j, DateUtil.DATE_IN_LIST_WITHOUT_YEAR));
            baseViewHolder.setText(C1034R.id.tv_file_size, FileSizeUtil.FormetFileSize(fileList.getSize().longValue()));
            baseViewHolder.addOnClickListener(C1034R.id.iv_file_download);
            baseViewHolder.addOnClickListener(C1034R.id.cl_file_content);
            baseViewHolder.addOnLongClickListener(C1034R.id.cl_file_content);
            if (this.mDownloadingItems.contains(fileList)) {
                baseViewHolder.setVisible(C1034R.id.iv_file_download, false);
                baseViewHolder.setVisible(C1034R.id.iv_file_loading, true);
                objectAnimatorOfFloat.start();
            } else {
                baseViewHolder.setVisible(C1034R.id.iv_file_download, this.mCurMode == 0);
                baseViewHolder.setVisible(C1034R.id.iv_file_loading, false);
                objectAnimatorOfFloat.pause();
            }
            if (fileList.getName().equals(this.mSelectedItemName)) {
                baseViewHolder.setBackgroundRes(C1034R.id.cl_file_content, C1034R.drawable.bg_purple_select_c14);
            } else {
                baseViewHolder.setBackgroundRes(C1034R.id.cl_file_content, C1034R.drawable.bg_purple_c14);
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) baseViewHolder.itemView.getLayoutParams();
            marginLayoutParams.topMargin = SizeUtils.dp2px(8.0f);
            marginLayoutParams.bottomMargin = SizeUtils.dp2px(fileList.isLast() ? 100.0f : 8.0f);
            baseViewHolder.itemView.setLayoutParams(marginLayoutParams);
        }

        public String formatFileName(String str) {
            try {
                return DateUtil.getDateFromTimestamp(Long.parseLong(str.replace(".opus", "").split(PunctuationConst.UNDERLINE)[1]), DateUtil.MM_DD_HH_MM) + " A1 Recording";
            } catch (Exception unused) {
                return str;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateItemDownloadStatus(DeviceFileBean.FileList fileList, int i) {
            int iIndexOf = this.mData.indexOf(fileList);
            if (i == 0 && !this.mDownloadingItems.contains(fileList)) {
                this.mDownloadingItems.add(fileList);
                notifyItemChanged(iIndexOf);
            } else if (i == 1) {
                this.mDownloadingItems.remove(fileList);
                this.mData.remove(iIndexOf);
                notifyItemRemoved(iIndexOf);
            } else {
                this.mDownloadingItems.remove(fileList);
                notifyItemChanged(iIndexOf);
            }
            LogUtil.m337e(TAG, "updateItemDownloadStatus:" + this.mDownloadingItems.size());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public List<DeviceFileBean.FileList> getDownloadingItems() {
            LogUtil.m337e(TAG, "getDownloadingItems:" + this.mDownloadingItems.size());
            return this.mDownloadingItems;
        }

        public void switchMode(int i, boolean z) {
            LogUtil.m336e("switchMode:" + i + ",bySwipeOrPress:" + z);
            this.mCurMode = i;
            if (i == 0) {
                this.mSelectedItemName = "";
            }
            notifyItemRangeChanged(0, this.mData.size());
        }

        public void selectItem(int i) {
            if (this.mDownloadingItems.contains(this.mData.get(i))) {
                return;
            }
            int iIndexOf = BaseStringUtil.isEmpty(this.mSelectedItemName) ? -1 : this.mData.indexOf(this.mData.stream().filter(new Predicate() { // from class: com.aivox.app.activity.DeviceFilesActivity$FileAdapter$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.m111xeff29a3b((DeviceFileBean.FileList) obj);
                }
            }).findFirst().orElse(null));
            this.mSelectedItemName = ((DeviceFileBean.FileList) this.mData.get(i)).getName();
            if (iIndexOf != -1) {
                notifyItemChanged(iIndexOf);
            }
            notifyItemChanged(i);
            LogUtil.m336e("SELECT ITEM : " + iIndexOf + " -> " + i);
        }

        /* JADX INFO: renamed from: lambda$selectItem$0$com-aivox-app-activity-DeviceFilesActivity$FileAdapter */
        /* synthetic */ boolean m111xeff29a3b(DeviceFileBean.FileList fileList) {
            return fileList.getName().equals(this.mSelectedItemName);
        }

        public int getMode() {
            return this.mCurMode;
        }

        public String getSelectedFileName() {
            return this.mSelectedItemName;
        }
    }

    public void getDeviceFileList(int i, int i2, final ResponseCallback responseCallback) {
        new OkHttpClient().newCall(new Request.Builder().url(String.format("http://" + this.mDeviceWifiIp + ":" + this.mDeviceWifiPort + "/file_server?cmd=filelist&page=%d&pagesize=%d", Integer.valueOf(i), Integer.valueOf(i2))).get().build()).enqueue(new Callback() { // from class: com.aivox.app.activity.DeviceFilesActivity.7
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                responseCallback.onFailure(iOException.getMessage());
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseCallback.onSuccess(response.body().string());
                } else {
                    responseCallback.onFailure("HTTP Error: " + response.code());
                }
            }
        });
    }

    public void deleteDeviceFile(String str, final ResponseCallback responseCallback) {
        new OkHttpClient().newCall(new Request.Builder().url(String.format("http://" + this.mDeviceWifiIp + ":" + this.mDeviceWifiPort + "/file_server?cmd=delete&file_name=%s", str)).get().build()).enqueue(new Callback() { // from class: com.aivox.app.activity.DeviceFilesActivity.8
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                responseCallback.onFailure(iOException.getMessage());
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseCallback.onSuccess(response.body().string());
                } else {
                    responseCallback.onFailure("HTTP Error: " + response.code());
                }
            }
        });
    }
}
