package com.aivox.app.activity;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import androidx.core.view.WindowCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import com.aivox.app.BuildConfig;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityMainBinding;
import com.aivox.app.fragment.FileFragment;
import com.aivox.app.fragment.GalleryFragment;
import com.aivox.app.fragment.HomeFragment;
import com.aivox.app.fragment.MainAiFragment;
import com.aivox.app.fragment.MainMeFragment;
import com.aivox.app.listener.FragmentFileActionListener;
import com.aivox.app.test.trans.AudioTransModel;
import com.aivox.app.test.trans.AudioUploadModel;
import com.aivox.app.view.AudioAndFolderActionPopup;
import com.aivox.base.C0874R;
import com.aivox.base.app.AppManager;
import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.common.Constant;
import com.aivox.base.common.GlassesCmd;
import com.aivox.base.common.MyEnum;
import com.aivox.base.http.HttpException;
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
import com.aivox.base.util.JsonUtils;
import com.aivox.base.util.LayoutUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.RegexUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.SaveLogHelper;
import com.aivox.base.util.ThreadPoolManager;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.ble.service.BleDeviceAutoLinkHelper;
import com.aivox.common.ble.service.BleServiceUtils;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.ble.service.SppBtService;
import com.aivox.common.ble.service.SppDeviceAutoLinkHelper;
import com.aivox.common.http.AudioService;
import com.aivox.common.http.UserService;
import com.aivox.common.model.AiConfigBean;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.DeviceBean;
import com.aivox.common.model.DouBaoConfigBean;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.OpenAiConfigBean;
import com.aivox.common.model.PresetLanguageBean;
import com.aivox.common.model.UserInfo;
import com.aivox.common.p003db.LocalFileEntityDao;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.p003db.entity.GlassImageEntity;
import com.aivox.common.p003db.entity.LocalFileEntity;
import com.aivox.common.p003db.maneger.GlassImageDbManager;
import com.aivox.common.p003db.maneger.LocalFileDbManager;
import com.aivox.common.parse.SendManager;
import com.aivox.common.socket.WebSocketManager;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.DeviceFileSyncManager;
import com.aivox.common.util.LanguageUtils;
import com.aivox.common.util.encrypt.SerAESUtil;
import com.aivox.common_ui.BottomEditDialogView;
import com.aivox.common_ui.DeviceActivateDialogView;
import com.aivox.common_ui.DeviceConnectDialogView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.github.houbb.heaven.constant.FileOptionConst;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends BaseFragmentActivity {
    private double clickTime;
    private long currentDate;
    private byte[] fileData;
    private byte fileHandle;
    private String filePath;
    private GlassImageDbManager glassImageDbManager;
    private BottomSheetDialog mActivateDialog;
    private DeviceActivateDialogView mActivateDialogView;
    private int mActivateRetryCount;
    private AudioAndFolderActionPopup mAudioItemPopup;
    private ActivityMainBinding mBinding;
    private BottomSheetDialog mConnectDialog;
    private DeviceConnectDialogView mConnectDialogView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private boolean mIsVisible;
    private String mLastDeviceAddress;
    private String mLastDeviceName;
    private LocalFileDbManager mLocalFileDbManager;
    private boolean mOnDisconnect;
    private SQLiteDataBaseManager manager;
    private boolean[] packageReceived;
    private RandomAccessFile raf;
    private String uid;
    private UserInfo userInfo;
    private final Handler mHandler = new Handler();
    private HomeFragment homeFragment;
    private FileFragment audioFragment;
    private MainAiFragment aiFragment;
    private GalleryFragment galleryFragment;
    private MainMeFragment meFragment;
    private final List<Fragment> fragmentList = Arrays.asList(this.homeFragment, this.audioFragment, this.aiFragment, this.galleryFragment, this.meFragment);
    private final CompositeDisposable mDis = new CompositeDisposable();
    private boolean blockAi = false;
    private boolean isGlassSync = false;
    private int retryCount = 0;
    private final ExecutorService fileExecutor = Executors.newSingleThreadExecutor();
    private long filePackageSize = 0;
    private final List<Integer> lostPackageNumbers = new ArrayList();
    private final AtomicInteger receivedPackageCount = new AtomicInteger(0);
    private final Object lock = new Object();
    private final SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYYMMDD2, Locale.getDefault());
    private final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
    private final BleBtService.GlassFileReceiver glassFileReceiver = new C07631();

    static /* synthetic */ void lambda$checkDeviceActivated$6(Throwable th) throws Exception {
    }

    static /* synthetic */ void lambda$doRenameFolder$24(Object obj) throws Exception {
    }

    static /* synthetic */ void lambda$getConfigInfo$12(Throwable th) throws Exception {
    }

    static /* synthetic */ void lambda$initView$1(View view2) {
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.MainActivity$1 */
    class C07631 implements BleBtService.GlassFileReceiver {
        C07631() {
        }

        @Override // com.aivox.common.ble.service.BleBtService.GlassFileReceiver
        public void onStart(final long j, final long j2, final long j3) {
            MainActivity.this.retryCount = 0;
            MainActivity.this.fileExecutor.execute(new Runnable() { // from class: com.aivox.app.activity.MainActivity$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2152lambda$onStart$0$comaivoxappactivityMainActivity$1(j, j2, j3);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onStart$0$com-aivox-app-activity-MainActivity$1, reason: not valid java name */
        /* synthetic */ void m2152lambda$onStart$0$comaivoxappactivityMainActivity$1(long j, long j2, long j3) {
            MainActivity.this.createFile(j, j2, j3);
        }

        @Override // com.aivox.common.ble.service.BleBtService.GlassFileReceiver
        public void onProgress(final long j, final long j2, final byte[] bArr) {
            MainActivity.this.fileExecutor.execute(new Runnable() { // from class: com.aivox.app.activity.MainActivity$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2151lambda$onProgress$1$comaivoxappactivityMainActivity$1(j, j2, bArr);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onProgress$1$com-aivox-app-activity-MainActivity$1, reason: not valid java name */
        /* synthetic */ void m2151lambda$onProgress$1$comaivoxappactivityMainActivity$1(long j, long j2, byte[] bArr) {
            if (MainActivity.this.raf != null) {
                MainActivity.this.receiveFileData(j, j2, bArr);
            }
        }

        /* JADX INFO: renamed from: lambda$onComplete$2$com-aivox-app-activity-MainActivity$1, reason: not valid java name */
        /* synthetic */ void m2150lambda$onComplete$2$comaivoxappactivityMainActivity$1() {
            MainActivity.this.checkFileLost();
        }

        @Override // com.aivox.common.ble.service.BleBtService.GlassFileReceiver
        public void onComplete() {
            MainActivity.this.fileExecutor.execute(new Runnable() { // from class: com.aivox.app.activity.MainActivity$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2150lambda$onComplete$2$comaivoxappactivityMainActivity$1();
                }
            });
        }

        @Override // com.aivox.common.ble.service.BleBtService.GlassFileReceiver
        public void onError(Exception exc) {
            LogUtil.m337e(MainActivity.this.TAG, "onError: " + exc.getLocalizedMessage());
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityMainBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_main);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        LayoutUtil.fitSystemInsets(this.mBinding.container, true);
        LayoutUtil.fitSystemInsets(this.mBinding.layoutFooter, false);
        if (BuildConfig.FLAVOR.toLowerCase().contains(ImagesContract.LOCAL)) {
            this.mBinding.tvLocalTag.setVisibility(0);
            this.mBinding.tvLocalTag.setText(String.format("v%s(%s)", BaseAppUtils.getVersionName(), BuildConfig.BUILD_TIME));
        }
        this.manager = new SQLiteDataBaseManager(this);
        this.mLocalFileDbManager = LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
        this.uid = (String) SPUtil.get(SPUtil.USER_ID, "0");
        this.userInfo = this.manager.getUserInfo();
        KeyboardUtils.registerSoftInputChangedListener(this, new KeyboardUtils.OnSoftInputChangedListener() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda31
            @Override // com.blankj.utilcode.util.KeyboardUtils.OnSoftInputChangedListener
            public final void onSoftInputChanged(int i) {
                this.f$0.m2140lambda$initView$0$comaivoxappactivityMainActivity(i);
            }
        });
        this.mBinding.rlCloseLayout.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda32
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MainActivity.lambda$initView$1(view2);
            }
        });
        this.mBinding.ivCloseX.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda33
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2141lambda$initView$2$comaivoxappactivityMainActivity(view2);
            }
        });
        this.glassImageDbManager = GlassImageDbManager.getInstance(AppApplication.getIns().getDaoSession());
        initSdkAndData();
        checkSppAvailable();
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2140lambda$initView$0$comaivoxappactivityMainActivity(int i) {
        this.mBinding.rgBottom.setVisibility(i > 400 ? 8 : 0);
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2141lambda$initView$2$comaivoxappactivityMainActivity(View view2) {
        doBack();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.blockAi = false;
        EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_BLOCK_AI, false));
        this.mIsVisible = true;
        this.userInfo = this.manager.getUserInfo();
        if (this.mBinding.rgBottom.getVisibility() != 0) {
            this.mBinding.rgBottom.setVisibility(0);
        }
        if (this.curNetStatus == 1 || this.curNetStatus == 2) {
            DeviceFileSyncManager.getInstance().startSync(this.context);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        this.blockAi = true;
        EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_BLOCK_AI, true));
        this.mIsVisible = false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Bundle extras = intent.getExtras();
        int i = extras != null ? extras.getInt(Constant.KEY_INDEX) : 0;
        initFragments(i);
        getConfigInfo();
        if (i == 0) {
            this.mBinding.footBarHome.setChecked(true);
            return;
        }
        if (i == 1) {
            this.mBinding.footBarAudio.setChecked(true);
            return;
        }
        if (i == 2) {
            this.mBinding.footBarChat.setChecked(true);
        } else if (i == 3) {
            this.mBinding.footBarFile.setChecked(true);
        } else {
            if (i != 4) {
                return;
            }
            this.mBinding.footBarMe.setChecked(true);
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.MainActivity$2 */
    class C07642 implements PermissionCallback {
        C07642() {
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void granted(boolean z) {
            if (z) {
                MainActivity.this.mLastDeviceAddress = (String) SPUtil.get(SPUtil.CONNECTED_DEVICE_ADDRESS, "");
                MainActivity.this.mLastDeviceName = (String) SPUtil.get(SPUtil.CONNECTED_DEVICE_NAME, "");
                if (BaseStringUtil.isEmpty(MainActivity.this.mLastDeviceAddress)) {
                    BleDeviceAutoLinkHelper.getInstance().startAutoLinkBle(MainActivity.this);
                    SppDeviceAutoLinkHelper.getInstance().startAutoLinkSpp(MainActivity.this);
                    new Handler().postDelayed(new Runnable() { // from class: com.aivox.app.activity.MainActivity$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m2153lambda$granted$0$comaivoxappactivityMainActivity$2();
                        }
                    }, 1000L);
                    return;
                } else {
                    BleDeviceAutoLinkHelper.getInstance().startAutoLinkBle(MainActivity.this);
                    SppDeviceAutoLinkHelper.getInstance().startAutoLinkSpp(MainActivity.this);
                    return;
                }
            }
            ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied_bluetooth));
        }

        /* JADX INFO: renamed from: lambda$granted$0$com-aivox-app-activity-MainActivity$2, reason: not valid java name */
        /* synthetic */ void m2153lambda$granted$0$comaivoxappactivityMainActivity$2() {
            boolean zBooleanValue;
            Exception e;
            BleDeviceAutoLinkHelper.getInstance().setConnectStatusChangeListener(new BleDeviceAutoLinkHelper.IConnectStatusChangListener() { // from class: com.aivox.app.activity.MainActivity.2.1
                @Override // com.aivox.common.ble.service.BleDeviceAutoLinkHelper.IConnectStatusChangListener
                public void onSuccess() {
                    LogUtil.m335d("BLE", "BLE 设备连接成功");
                }

                @Override // com.aivox.common.ble.service.BleDeviceAutoLinkHelper.IConnectStatusChangListener
                public void onFailed(String str) {
                    ToastUtil.showShort(str);
                    MainActivity.this.mConnectDialogView.onConnectedFailed();
                }
            });
            SppDeviceAutoLinkHelper.getInstance().setConnectStatusChangeListener(new SppDeviceAutoLinkHelper.IConnectStatusChangListener() { // from class: com.aivox.app.activity.MainActivity.2.2
                @Override // com.aivox.common.ble.service.SppDeviceAutoLinkHelper.IConnectStatusChangListener
                public void onSuccess() {
                    LogUtil.m335d("SPP", "SPP 设备连接成功");
                }

                @Override // com.aivox.common.ble.service.SppDeviceAutoLinkHelper.IConnectStatusChangListener
                public void onFailed(String str) {
                    ToastUtil.showShort(str);
                    MainActivity.this.mConnectDialogView.onConnectedFailed();
                }
            });
            for (BluetoothDevice bluetoothDevice : CommonServiceUtils.getInstance().getBondedDevices()) {
                LogUtil.m335d("DEVICE", "NAME : " + bluetoothDevice.getName());
                try {
                    zBooleanValue = ((Boolean) bluetoothDevice.getClass().getMethod("isConnected", new Class[0]).invoke(bluetoothDevice, new Object[0])).booleanValue();
                    try {
                        LogUtil.m335d("DEVICE", "CONNECTED : " + zBooleanValue);
                        for (ParcelUuid parcelUuid : bluetoothDevice.getUuids()) {
                            LogUtil.m335d("DEVICE", "UUID : " + parcelUuid.getUuid().toString().toUpperCase());
                        }
                    } catch (Exception e2) {
                        e = e2;
                        e.printStackTrace();
                    }
                } catch (Exception e3) {
                    zBooleanValue = false;
                    e = e3;
                }
                if (zBooleanValue && (CommonServiceUtils.getInstance().isContainUUid(bluetoothDevice.getUuids(), CommonServiceUtils.UUID1) || CommonServiceUtils.getInstance().isContainUUid(bluetoothDevice.getUuids(), CommonServiceUtils.UUID2) || CommonServiceUtils.getInstance().isContainUUid(bluetoothDevice.getUuids(), CommonServiceUtils.UUID3))) {
                    LogUtil.m335d("DEVICE", "SHOW DIALOG");
                    MainActivity.this.showConnectDialog(bluetoothDevice.getName(), bluetoothDevice.getAddress());
                    return;
                }
            }
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void requestError(Throwable th) {
            LogUtil.m336e("permission.e:" + th.getLocalizedMessage());
            ToastUtil.showLong(Integer.valueOf(C0874R.string.permission_denied_bluetooth));
            BaseAppUtils.openSettingView(MainActivity.this.context);
        }
    }

    private void checkSppAvailable() {
        if (DataHandle.getIns().hasConnectedBluetooth(false)) {
            return;
        }
        PermissionUtils.getIns(this, new C07642()).request("android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_ADVERTISE", "android.permission.BLUETOOTH_CONNECT", "android.permission.ACCESS_FINE_LOCATION");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showConnectDialog(String str, String str2) {
        LogUtil.m335d("DEVICE", "SHOW DIALOG:" + str + "\t Mac:" + str2);
        this.mConnectDialogView = new DeviceConnectDialogView(this.context, str, str2, new DeviceConnectDialogView.MyClickListener() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda0
            @Override // com.aivox.common_ui.DeviceConnectDialogView.MyClickListener
            public final void onBtnClick(String str3, String str4) {
                MainActivity.lambda$showConnectDialog$3(str3, str4);
            }
        });
        BottomSheetDialog bottomSheetDialog = this.mConnectDialog;
        if (bottomSheetDialog == null || !bottomSheetDialog.isShowing()) {
            this.mConnectDialog = DialogUtils.showBottomSheetDialog(this.context, this.mConnectDialogView);
        }
    }

    static /* synthetic */ void lambda$showConnectDialog$3(String str, String str2) {
        if (MyEnum.DEVICE_MODEL.isJieLiDevice(str) || str.equals(MyEnum.DEVICE_MODEL.AI_GLASSES_PAI_08.name) || str.equals(MyEnum.DEVICE_MODEL.HY_15.name) || str.equals(MyEnum.DEVICE_MODEL.HY_16.name)) {
            BleServiceUtils.getInstance().connectDevice(new DeviceBean(str, str2));
        } else {
            CommonServiceUtils.getInstance().connect(str2);
        }
    }

    private void parseData(String str) {
        if (str.startsWith(Constant.CmdDownBatteryLevel) && this.mIsVisible) {
            if (BaseStringUtil.isEmpty(this.mLastDeviceName)) {
                this.mLastDeviceName = CommonServiceUtils.getInstance().getConnectedDeviceName();
                this.mLastDeviceAddress = CommonServiceUtils.getInstance().getConnectedDeviceAddress();
            }
            this.mConnectDialogView = new DeviceConnectDialogView(this.context, this.mLastDeviceName, this.mLastDeviceAddress, null);
            String[] strArrSplit = str.split("-");
            if (strArrSplit.length >= 4) {
                this.mConnectDialogView.onConnectedSuccess(strArrSplit[1], strArrSplit[2], strArrSplit[3]);
            } else if (strArrSplit.length == 3) {
                this.mConnectDialogView.onConnectedSuccess(strArrSplit[1], strArrSplit[2], "");
            }
            BottomSheetDialog bottomSheetDialog = this.mActivateDialog;
            if (bottomSheetDialog == null || !bottomSheetDialog.isShowing()) {
                if (DataHandle.getIns().isHasConnectedBle(true) && BleBtService.getInstance().isGlass()) {
                    return;
                }
                this.mConnectDialog = DialogUtils.showBottomSheetDialog(this.context, this.mConnectDialogView);
            }
        }
    }

    private void checkDeviceActivated() {
        try {
            String strEncrypt = SerAESUtil.encrypt("smalink-" + this.userInfo.getUuid() + "-" + SppBtService.getInstance().getConnectedDeviceAddress(), Constant.MAC_DECRYPT_KEY);
            this.mLastDeviceName = CommonServiceUtils.getInstance().getConnectedDeviceName();
            this.mLastDeviceAddress = CommonServiceUtils.getInstance().getConnectedDeviceAddress();
            new AudioService(this.context).checkDeviceActivated(strEncrypt.trim()).subscribe(new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m152x97eb1895((Boolean) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    MainActivity.lambda$checkDeviceActivated$6((Throwable) obj);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showLong("Activate failed : " + e.getLocalizedMessage());
            disconnectWithDevice();
        }
    }

    /* JADX INFO: renamed from: lambda$checkDeviceActivated$5$com-aivox-app-activity-MainActivity */
    /* synthetic */ void m152x97eb1895(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            SendManager.getInstance().sendSppData(Constant.CmdUpEnterActive);
            this.mActivateDialogView = new DeviceActivateDialogView(this.context, this.mLastDeviceName, this.mLastDeviceAddress);
            BottomSheetDialog bottomSheetDialogShowBottomSheetDialog = DialogUtils.showBottomSheetDialog(this.context, this.mActivateDialogView);
            this.mActivateDialog = bottomSheetDialogShowBottomSheetDialog;
            bottomSheetDialogShowBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda25
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f$0.m151x84434514(dialogInterface);
                }
            });
            return;
        }
        SendManager.getInstance().sendSppData(Constant.CmdUpBatteryLevel);
    }

    /* JADX INFO: renamed from: lambda$checkDeviceActivated$4$com-aivox-app-activity-MainActivity */
    /* synthetic */ void m151x84434514(DialogInterface dialogInterface) {
        this.mActivateRetryCount = 0;
        if (this.mActivateDialogView.isActivateSuccess()) {
            SendManager.getInstance().sendSppData(Constant.CmdUpBatteryLevel);
        } else {
            DialogUtils.showDialogWithDefBtnAndSingleListener(this.context, "", Integer.valueOf(C0874R.string.device_activate_failed_notice), null, false, true);
            disconnectWithDevice();
        }
    }

    private void activateDevice() {
        try {
            new AudioService(this.context).activateDevice(SerAESUtil.encrypt("smalink-" + this.userInfo.getUuid() + "-" + SppBtService.getInstance().getConnectedDeviceAddress(), Constant.MAC_DECRYPT_KEY).trim()).doFinally(new Action() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda21
                @Override // io.reactivex.functions.Action
                public final void run() throws Exception {
                    this.f$0.m2131lambda$activateDevice$7$comaivoxappactivityMainActivity();
                }
            }).subscribe(new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda23
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2132lambda$activateDevice$8$comaivoxappactivityMainActivity((Boolean) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda24
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2133lambda$activateDevice$9$comaivoxappactivityMainActivity((Throwable) obj);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showLong("Activate failed : " + e.getLocalizedMessage());
            disconnectWithDevice();
        }
    }

    /* JADX INFO: renamed from: lambda$activateDevice$7$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2131lambda$activateDevice$7$comaivoxappactivityMainActivity() throws Exception {
        BottomSheetDialog bottomSheetDialog;
        if (this.mActivateRetryCount == 3 && (bottomSheetDialog = this.mActivateDialog) != null && bottomSheetDialog.isShowing()) {
            this.mActivateDialog.dismiss();
        }
    }

    /* JADX INFO: renamed from: lambda$activateDevice$8$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2132lambda$activateDevice$8$comaivoxappactivityMainActivity(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.success));
            SendManager.getInstance().sendSppData(Constant.CmdUpExitActive);
            this.mActivateDialogView.onActivateSuccess();
        } else {
            this.mActivateRetryCount++;
            this.mActivateDialogView.onActivateFailed();
        }
    }

    /* JADX INFO: renamed from: lambda$activateDevice$9$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2133lambda$activateDevice$9$comaivoxappactivityMainActivity(Throwable th) throws Exception {
        this.mActivateRetryCount++;
        this.mActivateDialogView.onActivateFailed();
    }

    private void disconnectWithDevice() {
        this.mOnDisconnect = true;
        SendManager.getInstance().sendSppData(Constant.CmdUpExitActive);
        DataHandle.getIns().setHasConnectedBle(false);
        DataHandle.getIns().setHasConnectedSpp(false);
        CommonServiceUtils.getInstance().stopService(this);
        ToastUtil.showShort(Integer.valueOf(C0874R.string.earphone_disconnected));
        SPUtil.put(SPUtil.CONNECTED_DEVICE_ADDRESS, "");
        SPUtil.put(SPUtil.CONNECTED_DEVICE_NAME, "");
        EventBus.getDefault().post(new EventBean(301));
    }

    private void initSdkAndData() {
        AppApplication.initSDK();
        SaveLogHelper.getIns().init(FileUtils.getLogFilePath(this));
        initFragments(0);
        getConfigInfo();
        new UserService(this.context).getUserInfo().subscribe(new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DataHandle.getIns().setHasSetPwd(((UserInfo) obj).getIsPassword() == 1);
            }
        }, new MainActivity$$ExternalSyntheticLambda14());
    }

    private void getConfigInfo() {
        new UserService(this.context).getPresetLanguage().subscribe(new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                MainActivity.lambda$getConfigInfo$11((PresetLanguageBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                MainActivity.lambda$getConfigInfo$12((Throwable) obj);
            }
        });
        if (BaseGlobalConfig.isMainland()) {
            new AudioService(this.context).getDouBaoConfig().subscribe(new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda17
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    MainActivity.lambda$getConfigInfo$13((DouBaoConfigBean) obj);
                }
            }, new MainActivity$$ExternalSyntheticLambda14());
        } else {
            new AudioService(this.context).getOpenAiConfig().subscribe(new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda18
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    MainActivity.lambda$getConfigInfo$14((OpenAiConfigBean) obj);
                }
            }, new MainActivity$$ExternalSyntheticLambda14());
        }
    }

    static /* synthetic */ void lambda$getConfigInfo$11(PresetLanguageBean presetLanguageBean) throws Exception {
        LanguageUtils.setDefaultLangToLocal(2, presetLanguageBean.getDefaultTranslateOrigin(), presetLanguageBean.getDefaultTranslateTarget());
        LanguageUtils.setDefaultLangToLocal(1, presetLanguageBean.getDefaultAsrLanguage(), -1);
    }

    static /* synthetic */ void lambda$getConfigInfo$13(DouBaoConfigBean douBaoConfigBean) throws Exception {
        AiConfigBean aiConfigBean = (AiConfigBean) JsonUtils.getIns().jsonStr2Obj(SerAESUtil.decrypt(douBaoConfigBean.getDoubaoKey(), Constant.DECRYPT_KEY), AiConfigBean.class);
        if (aiConfigBean == null || !BaseStringUtil.isNotEmpty(aiConfigBean.getApiKey())) {
            return;
        }
        DataHandle.getIns().setAiConfig(aiConfigBean);
    }

    static /* synthetic */ void lambda$getConfigInfo$14(OpenAiConfigBean openAiConfigBean) throws Exception {
        AiConfigBean aiConfigBean = (AiConfigBean) JsonUtils.getIns().jsonStr2Obj(SerAESUtil.decrypt(openAiConfigBean.getOpenApiKey(), Constant.DECRYPT_KEY), AiConfigBean.class);
        if (aiConfigBean == null || !BaseStringUtil.isNotEmpty(aiConfigBean.getApiKey())) {
            return;
        }
        DataHandle.getIns().setAiConfig(aiConfigBean);
    }

    private void initFragments(int i) {
        this.mFragmentManager = getSupportFragmentManager();
        this.mBinding.rgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda34
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup, int i2) {
                this.f$0.m2138lambda$initFragments$15$comaivoxappactivityMainActivity(radioGroup, i2);
            }
        });
        addFragmentToStack(i);
        AudioAndFolderActionPopup audioAndFolderActionPopupCreate = AudioAndFolderActionPopup.create(this.context, new AudioAndFolderActionPopup.OnPopupInteractionListener() { // from class: com.aivox.app.activity.MainActivity.3
            @Override // com.aivox.app.view.AudioAndFolderActionPopup.OnPopupInteractionListener
            public void doAudioRename(AudioInfoBean audioInfoBean) {
                MainActivity.this.showSaveDialog(audioInfoBean);
            }

            @Override // com.aivox.app.view.AudioAndFolderActionPopup.OnPopupInteractionListener
            public void doAudioDelete(List<AudioInfoBean> list) {
                MainActivity.this.showAudioDeleteDialog(list);
            }
        });
        this.mAudioItemPopup = audioAndFolderActionPopupCreate;
        audioAndFolderActionPopupCreate.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda35
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                this.f$0.m2139lambda$initFragments$16$comaivoxappactivityMainActivity();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initFragments$15$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2138lambda$initFragments$15$comaivoxappactivityMainActivity(RadioGroup radioGroup, int i) {
        int i2;
        if (i == C0726R.id.foot_bar_audio) {
            i2 = 1;
        } else if (i == C0726R.id.foot_bar_chat) {
            i2 = 2;
        } else if (i == C0726R.id.foot_bar_file) {
            i2 = 3;
        } else {
            i2 = i == C0726R.id.foot_bar_me ? 4 : 0;
        }
        this.mBinding.container.setBackgroundResource((i2 == 2 || i2 == 3) ? C0874R.color.bg_color_secondary : C0874R.color.bg_color_primary);
        addFragmentToStack(i2);
        if (BleBtService.getInstance().isGlass()) {
            EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_HOME_TAB, i2));
            BleBtService.getInstance().sendGlassCmd(GlassesCmd.SET_TIME);
        }
    }

    /* JADX INFO: renamed from: lambda$initFragments$16$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2139lambda$initFragments$16$comaivoxappactivityMainActivity() {
        this.mBinding.rlCloseLayout.setVisibility(8);
        HomeFragment homeFragment = this.homeFragment;
        if (homeFragment != null) {
            homeFragment.quitSelectMode();
        }
        FileFragment fileFragment = this.audioFragment;
        if (fileFragment != null) {
            fileFragment.quitSelectMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAudioDeleteDialog(final List<AudioInfoBean> list) {
        DialogUtils.showDeleteDialog(this, Integer.valueOf(C0874R.string.audio_delete), Integer.valueOf(C0874R.string.audio_delete_remind), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda28
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m154xa34e500f(list, context, dialogBuilder, dialog, i, i2, editText);
            }
        }, null, true, false, C0874R.string.delete, C0874R.string.cancel, 0);
    }

    /* JADX INFO: renamed from: lambda$showAudioDeleteDialog$17$com-aivox-app-activity-MainActivity */
    /* synthetic */ void m154xa34e500f(List list, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        m2148lambda$requestDelete$19$comaivoxappactivityMainActivity(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: requestDelete, reason: merged with bridge method [inline-methods] */
    public void m2148lambda$requestDelete$19$comaivoxappactivityMainActivity(final List<AudioInfoBean> list) {
        String strSubstring;
        LocalFileEntity localFileEntity;
        DialogUtils.showLoadingDialog(this, getResources().getString(C0874R.string.audio_delete_ing));
        final ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (AudioInfoBean audioInfoBean : list) {
            if (audioInfoBean.getIsTrans() != MyEnum.TRANS_STATE.ON_TRANS.type && audioInfoBean.getState() != MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD.type) {
                if (audioInfoBean.getId() != 0) {
                    sb.append(audioInfoBean.getId()).append(PunctuationConst.COMMA);
                }
                List<LocalFileEntity> listQueryLocalList = this.mLocalFileDbManager.queryLocalList(LocalFileEntityDao.Properties.Uid.m1944eq(this.uid), LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(audioInfoBean.getId())));
                if (listQueryLocalList.size() != 0 && (localFileEntity = listQueryLocalList.get(0)) != null && BaseStringUtil.isNotEmpty(localFileEntity.getLocalPath())) {
                    arrayList.add(localFileEntity.getLocalPath());
                    arrayList2.add(Integer.valueOf(localFileEntity.getVid()));
                }
            }
        }
        if (sb.length() > 0) {
            strSubstring = sb.substring(0, sb.length() - 1);
        } else {
            strSubstring = "";
        }
        new AudioService(this.context).deleteFile(strSubstring).subscribe(new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2147lambda$requestDelete$18$comaivoxappactivityMainActivity(arrayList, obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2149lambda$requestDelete$20$comaivoxappactivityMainActivity(list, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$requestDelete$18$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2147lambda$requestDelete$18$comaivoxappactivityMainActivity(List list, Object obj) throws Exception {
        DialogUtils.hideLoadingDialog();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!BaseStringUtil.isEmpty(str)) {
                FileUtils.localFileDelete(str);
                this.mLocalFileDbManager.deleteWhere(LocalFileEntityDao.Properties.LocalPath.m1944eq(str), LocalFileEntityDao.Properties.Uid.m1944eq(this.uid));
            }
        }
        ToastUtil.showShort(Integer.valueOf(C0874R.string.audio_delete_success));
        EventBus.getDefault().post(new EventBean(50));
    }

    /* JADX INFO: renamed from: lambda$requestDelete$20$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2149lambda$requestDelete$20$comaivoxappactivityMainActivity(final List list, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.context);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda26
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2148lambda$requestDelete$19$comaivoxappactivityMainActivity(list);
                }
            });
        }
    }

    private void addFragmentToStack(int i) {
        this.mFragmentTransaction = this.mFragmentManager.beginTransaction();
        if (i == 0 && this.homeFragment == null) {
            HomeFragment homeFragmentNewInstance = HomeFragment.newInstance();
            this.homeFragment = homeFragmentNewInstance;
            homeFragmentNewInstance.setInteractionListener(new FragmentFileActionListener() { // from class: com.aivox.app.activity.MainActivity.4
                @Override // com.aivox.app.listener.FragmentFileActionListener
                public void click2Record(int i2) {
                    MainActivity.this.enterRecord(i2);
                }

                @Override // com.aivox.app.listener.FragmentFileActionListener
                public void audioSelectChanged(List<AudioInfoBean> list) {
                    MainActivity.this.onAudioSelected(list);
                }
            });
            this.fragmentList.set(0, this.homeFragment);
        }
        if (i == 1 && this.audioFragment == null) {
            FileFragment fileFragmentNewInstance = FileFragment.newInstance();
            this.audioFragment = fileFragmentNewInstance;
            fileFragmentNewInstance.setInteractionListener(new FragmentFileActionListener() { // from class: com.aivox.app.activity.MainActivity.5
                @Override // com.aivox.app.listener.FragmentFileActionListener
                public void click2Record(int i2) {
                }

                @Override // com.aivox.app.listener.FragmentFileActionListener
                public void audioSelectChanged(List<AudioInfoBean> list) {
                    MainActivity.this.onAudioSelected(list);
                }
            });
            this.fragmentList.set(1, this.audioFragment);
        }
        if (i == 2 && this.aiFragment == null) {
            MainAiFragment mainAiFragmentNewInstance = MainAiFragment.newInstance();
            this.aiFragment = mainAiFragmentNewInstance;
            this.fragmentList.set(2, mainAiFragmentNewInstance);
        }
        if (i == 3 && this.galleryFragment == null) {
            GalleryFragment galleryFragmentNewInstance = GalleryFragment.newInstance();
            this.galleryFragment = galleryFragmentNewInstance;
            this.fragmentList.set(3, galleryFragmentNewInstance);
        }
        if (i == 4 && this.meFragment == null) {
            MainMeFragment mainMeFragmentNewInstance = MainMeFragment.newInstance();
            this.meFragment = mainMeFragmentNewInstance;
            this.fragmentList.set(4, mainMeFragmentNewInstance);
        }
        try {
            Fragment fragment = this.fragmentList.get(i);
            if (fragment != null && !fragment.isAdded()) {
                this.mFragmentTransaction.add(C0726R.id.fragment_container, fragment);
            }
            for (int i2 = 0; i2 < this.fragmentList.size(); i2++) {
                Fragment fragment2 = this.fragmentList.get(i2);
                if (i2 == i && fragment2.isAdded()) {
                    this.mFragmentTransaction.show(fragment2);
                } else if (fragment2 != null && fragment2.isAdded() && fragment2.isVisible()) {
                    this.mFragmentTransaction.hide(fragment2);
                }
                if (i2 == i) {
                    this.mFragmentTransaction.setMaxLifecycle(fragment2, Lifecycle.State.RESUMED);
                } else if (fragment2 != null) {
                    this.mFragmentTransaction.setMaxLifecycle(fragment2, Lifecycle.State.STARTED);
                }
            }
            this.mFragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAudioSelected(List<AudioInfoBean> list) {
        LogUtil.m337e("MainActivity", "infoList size = " + list.size());
        if (list.isEmpty()) {
            this.mAudioItemPopup.dismiss();
            return;
        }
        if (!this.mAudioItemPopup.isShowing()) {
            this.mAudioItemPopup.showAtLocation(this.mBinding.fragmentContainer, 80, 0, 0);
            this.mBinding.rlCloseLayout.setVisibility(0);
        }
        this.mAudioItemPopup.updateUiAndAudioData(list);
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mDis.clear();
        this.fileExecutor.shutdown();
        SaveLogHelper.getIns().destroy();
        if (CommonServiceUtils.getInstance().getConnectedDeviceName().equals(MyEnum.DEVICE_MODEL.DIGI_GIFT_BOX.name)) {
            SendManager.getInstance().sendSppData(Constant.CmdUpCloseWiFi);
        }
        BleDeviceAutoLinkHelper.getInstance().destroy();
        SppDeviceAutoLinkHelper.getInstance().destroy();
        CommonServiceUtils.getInstance().stopService(this);
        WebSocketManager.stopService(this);
        AudioUploadModel.getInstance().destroy();
        AudioTransModel.getInstance().destroy();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (doBack()) {
            super.onBackPressed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSaveDialog(final AudioInfoBean audioInfoBean) {
        if (audioInfoBean.getIsTrans() == MyEnum.TRANS_STATE.ON_TRANS.type || audioInfoBean.getState() == MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD.type) {
            return;
        }
        BottomEditDialogView bottomEditDialogView = new BottomEditDialogView(this.context, new BottomEditDialogView.OnBtnClickListener() { // from class: com.aivox.app.activity.MainActivity.6
            @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
            public void onLeftBtnClick() {
            }

            @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
            public void onSaveBtnClick(String str) {
                audioInfoBean.setTitle(str);
                MainActivity.this.m2145lambda$reqEditAudioInfo$32$comaivoxappactivityMainActivity(audioInfoBean);
            }
        });
        bottomEditDialogView.setDialogContent(getString(C0874R.string.record_info_dialog_file_rename), getString(C0874R.string.record_info_dialog_file_name), getString(C0874R.string.record_info_dialog_file_name_hint), audioInfoBean.getTitle(), "");
        DialogUtils.showBottomSheetDialog(this.context, bottomEditDialogView, C0874R.style.BottomSheetDialogWithEdit);
    }

    private void showFolderSaveDialog(final String str, final int i) {
        if (!DataHandle.getIns().isVip() && BaseStringUtil.isEmpty(str)) {
            AppUtils.showVipSubDialog(this.context);
            return;
        }
        BottomEditDialogView bottomEditDialogView = new BottomEditDialogView(this.context, new BottomEditDialogView.OnBtnClickListener() { // from class: com.aivox.app.activity.MainActivity.7
            @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
            public void onLeftBtnClick() {
            }

            @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
            public void onSaveBtnClick(String str2) {
                if (BaseStringUtil.isNotEmpty(str2)) {
                    if (BaseStringUtil.isEmpty(str)) {
                        MainActivity.this.m2134lambda$doCreateFolder$22$comaivoxappactivityMainActivity(str2);
                    } else {
                        MainActivity.this.m2136lambda$doRenameFolder$25$comaivoxappactivityMainActivity(str2, i);
                    }
                }
            }
        });
        bottomEditDialogView.setDialogContent(getString(BaseStringUtil.isEmpty(str) ? C0874R.string.new_folder : C0874R.string.rename_folder), getString(C0874R.string.folder_name), getString(C0874R.string.new_folder_hint), str, "");
        DialogUtils.showBottomSheetDialog(this.context, bottomEditDialogView, C0874R.style.BottomSheetDialogWithEdit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: doCreateFolder, reason: merged with bridge method [inline-methods] */
    public void m2134lambda$doCreateFolder$22$comaivoxappactivityMainActivity(final String str) {
        DialogUtils.showLoadingDialog(this.context);
        new AudioService(this.context).createFolder(str).subscribe(new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda19
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                EventBus.getDefault().post(new EventBean(500));
            }
        }, new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda20
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2135lambda$doCreateFolder$23$comaivoxappactivityMainActivity(str, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$doCreateFolder$23$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2135lambda$doCreateFolder$23$comaivoxappactivityMainActivity(final String str, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.context);
        } else {
            AppUtils.showError(this.context, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda30
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2134lambda$doCreateFolder$22$comaivoxappactivityMainActivity(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: doRenameFolder, reason: merged with bridge method [inline-methods] */
    public void m2136lambda$doRenameFolder$25$comaivoxappactivityMainActivity(final String str, final int i) {
        DialogUtils.showLoadingDialog(this.context);
        new AudioService(this.context).renameFolder(str, i).subscribe(new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                MainActivity.lambda$doRenameFolder$24(obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2137lambda$doRenameFolder$26$comaivoxappactivityMainActivity(str, i, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$doRenameFolder$26$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2137lambda$doRenameFolder$26$comaivoxappactivityMainActivity(final String str, final int i, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.context);
        } else {
            AppUtils.showError(this.context, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda11
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2136lambda$doRenameFolder$25$comaivoxappactivityMainActivity(str, i);
                }
            });
        }
    }

    private boolean doBack() {
        if (this.mAudioItemPopup.isShowing()) {
            this.mAudioItemPopup.dismiss();
            return false;
        }
        if (System.currentTimeMillis() - this.clickTime > 2000.0d) {
            this.clickTime = System.currentTimeMillis();
            ToastUtil.showTextToast(this, Integer.valueOf(C0874R.string.tap_and_quit));
            return false;
        }
        this.mHandler.removeCallbacksAndMessages(null);
        WebSocketManager.stopService(this);
        DataHandle.getIns().clear();
        System.gc();
        AppManager.getAppManager().finishAllActivity();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enterRecord(int i) {
        if ((i == 107 || i == 101) && !DataHandle.getIns().isPhoneState()) {
            ToastUtil.showLong(Integer.valueOf(C0874R.string.phone_mode_limit));
        } else {
            PermissionUtils.getIns(this, new C07708(i)).request("android.permission.RECORD_AUDIO", "android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_ADVERTISE", "android.permission.BLUETOOTH_CONNECT");
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.MainActivity$8 */
    class C07708 implements PermissionCallback {
        final /* synthetic */ int val$type;

        static /* synthetic */ void lambda$granted$1(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        }

        C07708(int i) {
            this.val$type = i;
        }

        /* JADX WARN: Removed duplicated region for block: B:7:0x002c A[PHI: r0 r1 r10
  0x002c: PHI (r0v10 int) = (r0v3 int), (r0v2 int) binds: [B:11:0x0042, B:6:0x002a] A[DONT_GENERATE, DONT_INLINE]
  0x002c: PHI (r1v14 int) = (r1v3 int), (r1v2 int) binds: [B:11:0x0042, B:6:0x002a] A[DONT_GENERATE, DONT_INLINE]
  0x002c: PHI (r10v23 int) = (r10v5 int), (r10v24 int) binds: [B:11:0x0042, B:6:0x002a] A[DONT_GENERATE, DONT_INLINE]] */
        @Override // com.aivox.base.permission.PermissionCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void granted(boolean r10) {
            /*
                Method dump skipped, instruction units count: 244
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aivox.app.activity.MainActivity.C07708.granted(boolean):void");
        }

        /* JADX INFO: renamed from: lambda$granted$0$com-aivox-app-activity-MainActivity$8, reason: not valid java name */
        /* synthetic */ void m2154lambda$granted$0$comaivoxappactivityMainActivity$8(int i, MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
            if (translate_language == MyEnum.TRANSLATE_LANGUAGE.YUE_HK) {
                translate_language = MyEnum.TRANSLATE_LANGUAGE.YUE;
            }
            if (translate_language2 == MyEnum.TRANSLATE_LANGUAGE.YUE_HK) {
                translate_language2 = MyEnum.TRANSLATE_LANGUAGE.YUE;
            }
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.TRANSCRIBE_TYPE, i);
            bundle.putInt("from", translate_language.type);
            bundle.putInt("to", translate_language2.type);
            ARouterUtils.startWithActivity(MainActivity.this, RecordAction.RECORD_ING, bundle);
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void requestError(Throwable th) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied_bluetooth));
            BaseAppUtils.openSettingView(MainActivity.this.context);
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void onEventMainThread(EventBean eventBean) {
        super.onEventMainThread(eventBean);
        int from = eventBean.getFrom();
        if (from == 46) {
            DeviceFileSyncManager.getInstance().startSync(this.context);
            return;
        }
        if (from == 320) {
            showConnectDialog(eventBean.getS1(), eventBean.getS2());
            return;
        }
        if (from == 307) {
            this.mOnDisconnect = false;
            checkSppAvailable();
            return;
        }
        if (from != 308) {
            switch (from) {
                case Constant.EVENT.BLE_CONNECTED /* 300 */:
                    this.mOnDisconnect = false;
                    BottomSheetDialog bottomSheetDialog = this.mConnectDialog;
                    if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                        this.mConnectDialog.dismiss();
                    }
                    checkDeviceActivated();
                    this.mBinding.footBarChat.postDelayed(new Runnable() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m2142lambda$onEventMainThread$27$comaivoxappactivityMainActivity();
                        }
                    }, 200L);
                    break;
                case 301:
                    break;
                case 302:
                    if (!this.mOnDisconnect) {
                        if (BleBtService.getInstance().isGlass()) {
                            boolean zBooleanValue = ((Boolean) SPUtil.get(SPUtil.GLASS_IS_CHARGING, false)).booleanValue();
                            if (((Integer) SPUtil.get(SPUtil.GLASS_BATTERY, 0)).intValue() == 15 && !zBooleanValue) {
                                DialogUtils.showDialogWithBtnIds(this, Integer.valueOf(C0874R.string.reminder), getString(C0874R.string.low_battery_warning), null, null, false, false, C0874R.string.sure, C0874R.string.sure);
                                break;
                            }
                        } else {
                            parseData(eventBean.getS1());
                            break;
                        }
                    }
                    break;
                default:
                    switch (from) {
                        case 313:
                            activateDevice();
                            break;
                        case 314:
                            DataHandle.getIns().setIsPhoneState(true);
                            break;
                        case 315:
                            DataHandle.getIns().setIsPhoneState(false);
                            break;
                        default:
                            switch (from) {
                                case 402:
                                    LogUtil.m335d(this.TAG, "关闭AP");
                                    BleBtService.getInstance().sendGlassCmd(GlassesCmd.WIFI_AP_CONTROL, 0);
                                    LogUtil.m335d(this.TAG, "关闭视频预览");
                                    this.mHandler.postDelayed(new Runnable() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda4
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            BleBtService.getInstance().sendGlassCmd(GlassesCmd.VIDEO_PREVIEW_CONTROL, 0);
                                        }
                                    }, 200L);
                                    break;
                                case 403:
                                    if (eventBean.getT() instanceof GlassesCmd) {
                                        int i = C07719.$SwitchMap$com$aivox$base$common$GlassesCmd[((GlassesCmd) eventBean.getT()).ordinal()];
                                        if (i == 1) {
                                            if (!this.blockAi && !this.isGlassSync && !this.mBinding.footBarChat.isChecked() && eventBean.getA() == 1) {
                                                LogUtil.m335d(this.TAG, "AI语音触发页面切换：" + eventBean.getA());
                                                DataHandle.getIns().setShouldHandleAiChat(true);
                                                this.mBinding.footBarChat.setChecked(true);
                                                break;
                                            }
                                        } else if (i == 2) {
                                            LogUtil.m335d(this.TAG, "拍照结束返回文件信息");
                                            if (eventBean.getA() == 1) {
                                                LogUtil.m335d(this.TAG, "WiFi传输" + eventBean.getS1());
                                            } else if (eventBean.getA() == 2) {
                                                this.fileHandle = (byte) eventBean.getB();
                                                BleBtService.getInstance().sendGlassCmd(GlassesCmd.START_GET_FILE, Byte.valueOf(this.fileHandle));
                                            }
                                            break;
                                        }
                                    }
                                    break;
                                case 404:
                                    BleBtService.getInstance().sendGlassCmdWithResponse((GlassesCmd) eventBean.getT());
                                    break;
                                default:
                                    switch (from) {
                                        case Constant.EVENT.BLE_GLASS_IS_SYNC /* 410 */:
                                            this.isGlassSync = eventBean.isTrue();
                                            break;
                                        case Constant.EVENT.BLE_GLASS_HIDE_TAB /* 411 */:
                                            this.mBinding.layoutFooter.setVisibility(eventBean.isTrue() ? 8 : 0);
                                            if (BuildConfig.FLAVOR.equals(ImagesContract.LOCAL)) {
                                                this.mBinding.tvLocalTag.setVisibility(eventBean.isTrue() ? 8 : 0);
                                            }
                                            break;
                                        case Constant.EVENT.BLE_GLASS_SWITCH_LANGUAGE /* 412 */:
                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda5
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    this.f$0.m2143lambda$onEventMainThread$29$comaivoxappactivityMainActivity();
                                                }
                                            }, 1000L);
                                            break;
                                        case Constant.EVENT.BLE_GLASS_START_RECOGNITION /* 413 */:
                                            DataHandle.getIns().setShouldHandleRecognition(true);
                                            if (!this.mBinding.footBarChat.isChecked()) {
                                                this.mBinding.footBarChat.setChecked(true);
                                                EventBus.getDefault().post(new EventBean(416));
                                            }
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
            return;
        }
        this.mOnDisconnect = true;
        BottomSheetDialog bottomSheetDialog2 = this.mConnectDialog;
        if (bottomSheetDialog2 != null && bottomSheetDialog2.isShowing()) {
            this.mConnectDialog.dismiss();
        }
        this.mLastDeviceName = "";
        DataHandle.getIns().setIsPhoneState(false);
        updateGalleryFragmentVisibility(false);
        BleBtService.getInstance().setGlassFileReceiver(null);
        if (this.mBinding.footBarHome.isChecked()) {
            return;
        }
        this.mBinding.footBarHome.setChecked(true);
    }

    /* JADX INFO: renamed from: lambda$onEventMainThread$27$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2142lambda$onEventMainThread$27$comaivoxappactivityMainActivity() {
        updateGalleryFragmentVisibility(BleBtService.getInstance().isGlass());
        if (BleBtService.getInstance().isGlass()) {
            BleBtService.getInstance().setGlassFileReceiver(this.glassFileReceiver);
            BleBtService.getInstance().sendGlassCmd(GlassesCmd.GET_BATTERY);
            BleBtService.getInstance().sendGlassCmd(GlassesCmd.SET_TIME);
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.MainActivity$9 */
    static /* synthetic */ class C07719 {
        static final /* synthetic */ int[] $SwitchMap$com$aivox$base$common$GlassesCmd;

        static {
            int[] iArr = new int[GlassesCmd.values().length];
            $SwitchMap$com$aivox$base$common$GlassesCmd = iArr;
            try {
                iArr[GlassesCmd.AI_MODE_VOICE_EVENT_TRIGGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.IMAGE_CAPTURE_FINISH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: renamed from: lambda$onEventMainThread$29$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2143lambda$onEventMainThread$29$comaivoxappactivityMainActivity() {
        if (this.context == null) {
            return;
        }
        System.exit(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createFile(long j, long j2, long j3) {
        this.currentDate = new Date().getTime();
        File file = new File(FileUtils.getAppPath(this, "glassImage"), this.sdf1.format(Long.valueOf(this.currentDate)) + ".jpg");
        this.filePath = file.getAbsolutePath();
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, FileOptionConst.READ_WRITE);
            this.raf = randomAccessFile;
            randomAccessFile.setLength(j);
            LogUtil.m335d(this.TAG, "创建文件:" + j);
            this.filePackageSize = j2;
            this.fileData = new byte[(int) j];
            this.packageReceived = new boolean[(int) j3];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void receiveFileData(long j, long j2, byte[] bArr) {
        boolean[] zArr;
        synchronized (this.lock) {
            if (this.fileData != null && (zArr = this.packageReceived) != null) {
                int i = (int) (j2 - 1);
                if (i >= 0 && i < zArr.length) {
                    if (!zArr[i]) {
                        zArr[i] = true;
                        this.receivedPackageCount.incrementAndGet();
                        long j3 = ((long) i) * this.filePackageSize;
                        if (bArr.length > 0) {
                            writeFileData(j3, bArr);
                        }
                        LogUtil.m335d(this.TAG, "接收文件数据:" + j2);
                    }
                    if (this.receivedPackageCount.get() == j) {
                        finishReceive(j);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkFileLost() {
        synchronized (this.lock) {
            if (this.packageReceived == null) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (true) {
                boolean[] zArr = this.packageReceived;
                if (i >= zArr.length) {
                    break;
                }
                if (!zArr[i]) {
                    arrayList.add(Integer.valueOf(i + 1));
                }
                i++;
            }
            if (arrayList.isEmpty()) {
                finishReceive(this.packageReceived.length);
            } else {
                LogUtil.m335d(this.TAG, "检测到丢失包数量: " + arrayList.size());
                requestRetransmission(arrayList);
            }
        }
    }

    private void requestRetransmission(final List<Integer> list) {
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m153x26bfefcc(list);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$requestRetransmission$30$com-aivox-app-activity-MainActivity */
    /* synthetic */ void m153x26bfefcc(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            synchronized (this.lock) {
                boolean[] zArr = this.packageReceived;
                if (zArr == null || !zArr[num.intValue() - 1]) {
                    BleBtService.getInstance().sendGlassCmd(GlassesCmd.RETRIEVE_FILE_DATA, num, Byte.valueOf(this.fileHandle));
                    try {
                        Thread.sleep(50L);
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        int i = this.retryCount;
        if (i < 2) {
            this.retryCount = i + 1;
            this.mHandler.postDelayed(new Runnable() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.checkFileLost();
                }
            }, 1000L);
        } else {
            this.retryCount = 0;
        }
    }

    private void finishReceive(long j) {
        LogUtil.m335d(this.TAG, "文件接收完成，开始后续处理: " + this.filePath);
        RandomAccessFile randomAccessFile = this.raf;
        if (randomAccessFile == null) {
            return;
        }
        try {
            try {
                randomAccessFile.close();
                if (this.mBinding.footBarChat.isChecked()) {
                    EventBus.getDefault().post(new EventBean(Constant.EVENT.BLE_GLASS_ASK_AI, this.filePath));
                }
                GlassImageEntity glassImageEntity = new GlassImageEntity();
                glassImageEntity.setUid(this.uid);
                glassImageEntity.setImagePath(this.filePath);
                File file = new File(this.filePath);
                if (file.exists()) {
                    glassImageEntity.setImageName(file.getName());
                }
                glassImageEntity.setCreateTime(this.sdf.format(Long.valueOf(this.currentDate)));
                this.glassImageDbManager.insert(glassImageEntity);
                EventBus.getDefault().post(new EventBean(406));
            } catch (IOException e) {
                LogUtil.m337e(this.TAG, "关闭文件失败:" + e.getLocalizedMessage());
            }
        } finally {
            this.filePackageSize = 0L;
            this.fileData = null;
            this.packageReceived = null;
            this.raf = null;
            this.receivedPackageCount.set(0);
        }
    }

    private synchronized void writeFileData(long j, byte[] bArr) {
        RandomAccessFile randomAccessFile = this.raf;
        if (randomAccessFile == null) {
            return;
        }
        try {
            randomAccessFile.seek(j);
            this.raf.write(bArr);
        } catch (IOException e) {
            LogUtil.m337e(this.TAG, "写入文件数据失败:" + e.getLocalizedMessage());
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        LogUtil.m338i("RecordListActivity_onActivityResult");
        if (i == 10188) {
            this.aiFragment.onActivityResult(i, i2, intent);
        }
    }

    private void updateGalleryFragmentVisibility(boolean z) {
        this.mBinding.footBarAudio.setVisibility(z ? 0 : 8);
        this.mBinding.footBarFile.setVisibility(z ? 0 : 8);
        if (z) {
            return;
        }
        if (this.mBinding.footBarAudio.isChecked() || this.mBinding.footBarFile.isChecked()) {
            this.mBinding.footBarHome.setChecked(true);
        }
    }

    public void showDot(int i) {
        if (i == 0) {
            this.mBinding.ivHasNews.setVisibility(0);
        } else if (i == 1) {
            this.mBinding.ivHasSysNotice.setVisibility(8);
        } else {
            if (i != 2) {
                return;
            }
            this.mBinding.ivHasMsgNotice.setVisibility(8);
        }
    }

    public void hideDot(int i) {
        if (i == 0) {
            this.mBinding.ivHasNews.setVisibility(8);
        } else if (i == 1) {
            this.mBinding.ivHasSysNotice.setVisibility(8);
        } else {
            if (i != 2) {
                return;
            }
            this.mBinding.ivHasMsgNotice.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: reqEditAudioInfo, reason: merged with bridge method [inline-methods] */
    public void m2145lambda$reqEditAudioInfo$32$comaivoxappactivityMainActivity(final AudioInfoBean audioInfoBean) {
        if (BaseStringUtil.isEmpty(audioInfoBean.getTitle())) {
            ToastUtil.showTextToast(this, Integer.valueOf(C0874R.string.record_info_dialog_meeting_name_hint));
        } else if (!RegexUtil.isFileName(audioInfoBean.getTitle())) {
            ToastUtil.showTextToast(this, Integer.valueOf(C0874R.string.record_info_dialog_meeting_subject_error));
        } else {
            this.mDis.add(new AudioService(this).editRecordInfo(audioInfoBean.getId(), audioInfoBean.getTitle()).subscribe(new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda22
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2144lambda$reqEditAudioInfo$31$comaivoxappactivityMainActivity(audioInfoBean, (AudioInfoBean) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda29
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2146lambda$reqEditAudioInfo$33$comaivoxappactivityMainActivity(audioInfoBean, (Throwable) obj);
                }
            }));
        }
    }

    /* JADX INFO: renamed from: lambda$reqEditAudioInfo$31$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2144lambda$reqEditAudioInfo$31$comaivoxappactivityMainActivity(AudioInfoBean audioInfoBean, AudioInfoBean audioInfoBean2) throws Exception {
        ToastUtil.showTextToast(this, getString(C0874R.string.save_success));
        this.mLocalFileDbManager.updateTitle(audioInfoBean2.getTitle(), LocalFileEntityDao.Properties.Uid.m1944eq(this.uid), LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(audioInfoBean2.getId())));
        audioInfoBean2.setTagGroupList(audioInfoBean.getTagGroupList());
        HomeFragment homeFragment = this.homeFragment;
        if (homeFragment != null) {
            homeFragment.notifyItemChanged(audioInfoBean2);
        }
        FileFragment fileFragment = this.audioFragment;
        if (fileFragment != null) {
            fileFragment.notifyItemChanged(audioInfoBean2);
        }
    }

    /* JADX INFO: renamed from: lambda$reqEditAudioInfo$33$com-aivox-app-activity-MainActivity, reason: not valid java name */
    /* synthetic */ void m2146lambda$reqEditAudioInfo$33$comaivoxappactivityMainActivity(final AudioInfoBean audioInfoBean, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.MainActivity$$ExternalSyntheticLambda27
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2145lambda$reqEditAudioInfo$32$comaivoxappactivityMainActivity(audioInfoBean);
                }
            });
        }
    }
}
