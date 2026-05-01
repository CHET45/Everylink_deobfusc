package com.aivox.app.activity;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.net.Network;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityDeviceUpdateBinding;
import com.aivox.base.C0874R;
import com.aivox.base.common.GlassesCmd;
import com.aivox.base.http.HttpException;
import com.aivox.base.http.progress.ProgressRequestBody;
import com.aivox.base.http.progress.ProgressRequestListener;
import com.aivox.base.img.imageloader.GlideApp;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.besota.bessdk.BesSdkConstants;
import com.aivox.besota.bessdk.scan.BtHeleper;
import com.aivox.besota.bessdk.service.BesOTAConstants;
import com.aivox.besota.bessdk.service.BesOtaService;
import com.aivox.besota.bessdk.service.base.BesServiceConfig;
import com.aivox.besota.bessdk.service.base.BesServiceListener;
import com.aivox.besota.bessdk.utils.SPHelper;
import com.aivox.besota.sdk.device.HmDevice;
import com.aivox.besota.sdk.ota.OTADfuInfo;
import com.aivox.besota.sdk.ota.OTATask;
import com.aivox.besota.sdk.ota.RemoteOTAConfig;
import com.aivox.besota.sdk.utils.DeviceProtocol;
import com.aivox.besota.sdk.utils.OTAStatus;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.download.DownloadUtil;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.GlassState;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.WifiConnector;
import com.aivox.common_ui.antishake.AntiShake;
import com.aivox.jieliota.tool.bluetooth.BluetoothHelper;
import com.aivox.jieliota.tool.config.ConfigHelper;
import com.aivox.jieliota.tool.ota.OTAManager;
import com.jieli.jl_bt_ota.interfaces.BtEventCallback;
import com.jieli.jl_bt_ota.model.base.BaseError;
import com.jieli.jl_bt_ota.tool.UpgradeCbHelper;
import com.jieli.jl_bt_ota.util.BluetoothUtil;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class DeviceUpdateActivity extends BaseFragmentActivity implements BesServiceListener, OTATask.StatusListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int DOWNLOADING = 1;
    private static final int DOWNLOAD_ERROR = 3;
    private static final int DOWNLOAD_FINISH = 2;
    private static final int UPDATE_ERROR = 5;
    private static final int UPDATE_SUCCESS = 4;
    BesOtaService besOtaService;
    private final BtEventCallback bluetoothCallback;
    private final BluetoothHelper bluetoothHelper;
    private String btUrl;
    private DownloadUtil downloadUtil;
    private String fileName;
    private int glassCount;
    private boolean isJieLiDevice;
    private boolean isUpdateSuccess;
    private long lastProgress;
    private ActivityDeviceUpdateBinding mBinding;
    BluetoothDevice mDevice;
    private final OTAManager manager;
    OTATask otaTask;
    private WifiConnector wifiConnector;
    private String wifiFileName;
    private boolean mCanBack = false;
    private final GlassState glassState = new GlassState();
    private boolean isWifiConnected = false;
    private boolean updateBoth = false;
    private final PublishSubject<String> ssidSub = PublishSubject.create();
    private final PublishSubject<String> passwdSub = PublishSubject.create();
    private final CompositeDisposable mDis = new CompositeDisposable();
    private final Handler mHandler2 = new Handler(new C07442());

    /* JADX INFO: renamed from: $r8$lambda$pnZHaLsuWh0mdbgsX--fRG3ls6s, reason: not valid java name */
    public static /* synthetic */ Pair m2105$r8$lambda$pnZHaLsuWh0mdbgsXfRG3ls6s(Object obj, Object obj2) {
        return new Pair(obj, obj2);
    }

    public DeviceUpdateActivity() {
        BtEventCallback btEventCallback = new BtEventCallback() { // from class: com.aivox.app.activity.DeviceUpdateActivity.4
            @Override // com.jieli.jl_bt_ota.interfaces.BtEventCallback, com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
            public void onConnection(BluetoothDevice bluetoothDevice, int i) {
                LogUtil.m339i(DeviceUpdateActivity.this.TAG, "onConnection:" + i);
                if ((i == 2 || i == 0) && DeviceUpdateActivity.this.bluetoothHelper.getConnectedDevice() != null) {
                    DeviceUpdateActivity.this.bluetoothHelper.disconnectDevice(DeviceUpdateActivity.this.bluetoothHelper.getConnectedDevice());
                } else if (i == 1) {
                    DeviceUpdateActivity.this.startJieLiOTA();
                }
            }

            @Override // com.jieli.jl_bt_ota.interfaces.BtEventCallback, com.jieli.jl_bt_ota.interfaces.IBluetoothCallback
            public void onMandatoryUpgrade(BluetoothDevice bluetoothDevice) {
                super.onMandatoryUpgrade(bluetoothDevice);
            }
        };
        this.bluetoothCallback = btEventCallback;
        OTAManager oTAManager = new OTAManager(BaseAppUtils.getContext());
        this.manager = oTAManager;
        oTAManager.registerBluetoothCallback(btEventCallback);
        ConfigHelper.INSTANCE.getInstance().setBleWay(DataHandle.getIns().isHasConnectedBle(false));
        this.bluetoothHelper = BluetoothHelper.INSTANCE.getInstance();
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityDeviceUpdateBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_device_update);
        getWindow().addFlags(128);
        this.mBinding.titleView.setOnBackListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceUpdateActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2106lambda$initView$0$comaivoxappactivityDeviceUpdateActivity(view2);
            }
        });
        this.mBinding.btnFinish.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceUpdateActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2107lambda$initView$1$comaivoxappactivityDeviceUpdateActivity(view2);
            }
        });
        this.mBinding.tvNotiDetail.setText(Html.fromHtml(this.context.getString(C0874R.string.device_update_tips_detail), 0));
        this.downloadUtil = new DownloadUtil();
        FileUtils.deleteFile(FileUtils.getOTAFilePath(this));
        if (BleBtService.getInstance().isGlass()) {
            GlideApp.with((FragmentActivity) this).load(Integer.valueOf(C0874R.drawable.pic_device_echo_eye)).into(this.mBinding.ivIc);
            this.glassCount = getIntent().getIntExtra("updateCount", 0);
            startGlassOtaDownload();
            this.mDis.add(Observable.combineLatest(this.ssidSub, this.passwdSub, new BiFunction() { // from class: com.aivox.app.activity.DeviceUpdateActivity$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return DeviceUpdateActivity.m2105$r8$lambda$pnZHaLsuWh0mdbgsXfRG3ls6s((String) obj, (String) obj2);
                }
            }).filter(new Predicate() { // from class: com.aivox.app.activity.DeviceUpdateActivity$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Predicate
                public final boolean test(Object obj) {
                    return DeviceUpdateActivity.lambda$initView$2((Pair) obj);
                }
            }).take(1L).observeOn(Schedulers.m1898io()).flatMapCompletable(new Function() { // from class: com.aivox.app.activity.DeviceUpdateActivity$$ExternalSyntheticLambda6
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return this.f$0.m2109lambda$initView$4$comaivoxappactivityDeviceUpdateActivity((Pair) obj);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.aivox.app.activity.DeviceUpdateActivity$$ExternalSyntheticLambda7
                @Override // io.reactivex.functions.Action
                public final void run() throws Exception {
                    this.f$0.m2110lambda$initView$5$comaivoxappactivityDeviceUpdateActivity();
                }
            }, new Consumer() { // from class: com.aivox.app.activity.DeviceUpdateActivity$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2111lambda$initView$6$comaivoxappactivityDeviceUpdateActivity((Throwable) obj);
                }
            }));
            return;
        }
        this.isJieLiDevice = getIntent().getBooleanExtra("isJieLiDevice", false);
        startDownloadOtaFile(getIntent().getStringExtra("url"));
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-DeviceUpdateActivity, reason: not valid java name */
    /* synthetic */ void m2106lambda$initView$0$comaivoxappactivityDeviceUpdateActivity(View view2) {
        onBackPressed();
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-DeviceUpdateActivity, reason: not valid java name */
    /* synthetic */ void m2107lambda$initView$1$comaivoxappactivityDeviceUpdateActivity(View view2) {
        onBackPressed();
    }

    static /* synthetic */ boolean lambda$initView$2(Pair pair) throws Exception {
        return (TextUtils.isEmpty((CharSequence) pair.first) || TextUtils.isEmpty((CharSequence) pair.second)) ? false : true;
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-app-activity-DeviceUpdateActivity, reason: not valid java name */
    /* synthetic */ void m2108lambda$initView$3$comaivoxappactivityDeviceUpdateActivity(Pair pair) throws Exception {
        connectWifi((String) pair.first, (String) pair.second);
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-app-activity-DeviceUpdateActivity, reason: not valid java name */
    /* synthetic */ CompletableSource m2109lambda$initView$4$comaivoxappactivityDeviceUpdateActivity(final Pair pair) throws Exception {
        return Completable.fromAction(new Action() { // from class: com.aivox.app.activity.DeviceUpdateActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m2108lambda$initView$3$comaivoxappactivityDeviceUpdateActivity(pair);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-app-activity-DeviceUpdateActivity, reason: not valid java name */
    /* synthetic */ void m2110lambda$initView$5$comaivoxappactivityDeviceUpdateActivity() throws Exception {
        Log.d(this.TAG, "开始连接WiFi！");
    }

    /* JADX INFO: renamed from: lambda$initView$6$com-aivox-app-activity-DeviceUpdateActivity, reason: not valid java name */
    /* synthetic */ void m2111lambda$initView$6$comaivoxappactivityDeviceUpdateActivity(Throwable th) throws Exception {
        Log.e(this.TAG, "开始连接WiFi失败", th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startGlassOtaDownload() {
        this.btUrl = getIntent().getStringExtra("btUrl");
        String stringExtra = getIntent().getStringExtra("wifiUrl");
        int i = this.glassCount;
        if (i == 1) {
            BleBtService.getInstance().sendGlassCmd(GlassesCmd.START_OTA, (byte) 2, (byte) 0, (byte) 1);
            startDownloadOtaFile(this.btUrl);
        } else if (i == 2) {
            BleBtService.getInstance().sendGlassCmd(GlassesCmd.START_OTA, (byte) 2, (byte) 1, (byte) 0);
            startDownloadOtaFile(stringExtra, true);
        } else {
            if (i != 3) {
                return;
            }
            this.updateBoth = true;
            BleBtService.getInstance().sendGlassCmd(GlassesCmd.START_OTA, (byte) 2, (byte) 1, (byte) 1);
            startDownloadOtaFile(stringExtra, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOtaProgress(long j) {
        if (this.lastProgress != j) {
            this.lastProgress = j;
            runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceUpdateActivity$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m113xd4818c11();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$setOtaProgress$7$com-aivox-app-activity-DeviceUpdateActivity */
    /* synthetic */ void m113xd4818c11() {
        this.mBinding.pbProgress.setProgress(Math.toIntExact(this.lastProgress));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startDownloadOtaFile(String str) {
        startDownloadOtaFile(str, false);
    }

    private void startDownloadOtaFile(String str, final boolean z) {
        this.mCanBack = false;
        this.mBinding.btnFinish.setEnabled(false);
        String[] strArrSplit = str.split("/");
        if (z) {
            this.wifiFileName = strArrSplit[strArrSplit.length - 1];
        } else {
            this.fileName = strArrSplit[strArrSplit.length - 1];
        }
        if (BaseStringUtil.isEmpty(str)) {
            return;
        }
        this.downloadUtil.download(str, FileUtils.getOTAFilePath(this), z ? this.wifiFileName : this.fileName, new DownloadUtil.OnDownloadListener() { // from class: com.aivox.app.activity.DeviceUpdateActivity.1
            @Override // com.aivox.common.download.DownloadUtil.OnDownloadListener
            public void onDownloadSuccess(File file) {
                LogUtil.m335d(DeviceUpdateActivity.this.TAG, "onDownloadSuccess");
                if (z && DeviceUpdateActivity.this.updateBoth) {
                    LogUtil.m335d(DeviceUpdateActivity.this.TAG, "WiFi firmware downloaded, now downloading BT firmware...");
                    DeviceUpdateActivity deviceUpdateActivity = DeviceUpdateActivity.this;
                    deviceUpdateActivity.startDownloadOtaFile(deviceUpdateActivity.btUrl);
                } else {
                    LogUtil.m338i("当前线程：" + Thread.currentThread().getName());
                    Message message = new Message();
                    message.obj = file;
                    message.what = 2;
                    DeviceUpdateActivity.this.mHandler2.sendMessage(message);
                }
            }

            @Override // com.aivox.common.download.DownloadUtil.OnDownloadListener
            public void onDownloading(long j, long j2) {
                Message message = new Message();
                message.what = 1;
                message.arg1 = (int) ((j / j2) * 100.0f);
                DeviceUpdateActivity.this.mHandler2.sendMessage(message);
            }

            @Override // com.aivox.common.download.DownloadUtil.OnDownloadListener
            public void onDownloadFailed(Exception exc) {
                LogUtil.m334d(DeviceUpdateActivity.this.TAG + "onDownloadFailed:" + exc.toString());
                String localizedMessage = exc.getLocalizedMessage();
                Message message = new Message();
                message.what = 3;
                message.obj = localizedMessage;
                DeviceUpdateActivity.this.mHandler2.sendMessage(message);
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        WifiConnector wifiConnector = this.wifiConnector;
        if (wifiConnector != null) {
            wifiConnector.retryConnectionAfterEnablingWifi();
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(128);
        if (BleBtService.getInstance().isGlass()) {
            BleBtService.getInstance().sendGlassCmd(GlassesCmd.START_OTA, (byte) 2, (byte) 0, (byte) 0);
            BleBtService.getInstance().sendGlassCmd(GlassesCmd.WIFI_AP_CONTROL, 0);
        }
        WifiConnector wifiConnector = this.wifiConnector;
        if (wifiConnector != null) {
            wifiConnector.cleanup();
            this.wifiConnector = null;
        }
        this.manager.unregisterBluetoothCallback(this.bluetoothCallback);
        this.manager.release();
        this.mDis.clear();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mCanBack) {
            FileUtils.deleteFile(FileUtils.getOTAFilePath(this));
            super.onBackPressed();
        } else {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.cannot_exit_while_updating));
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.DeviceUpdateActivity$2 */
    class C07442 implements Handler.Callback {
        C07442() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                int i2 = message.arg1;
                if (DeviceUpdateActivity.this.glassCount == 3) {
                    DeviceUpdateActivity.this.setOtaProgress(i2 / 10);
                    return false;
                }
                if (DeviceUpdateActivity.this.glassCount != 2) {
                    return false;
                }
                DeviceUpdateActivity.this.setOtaProgress(i2 / 5);
                return false;
            }
            if (i == 2) {
                if (BleBtService.getInstance().isGlass()) {
                    if (DeviceUpdateActivity.this.glassCount <= 1) {
                        DeviceUpdateActivity.this.startConnect();
                        return false;
                    }
                    BleBtService.getInstance().sendGlassCmd(GlassesCmd.WIFI_AP_CONTROL, 1);
                    return false;
                }
                if (DeviceUpdateActivity.this.isJieLiDevice) {
                    DeviceUpdateActivity.this.startJieLiConnect();
                    return false;
                }
                DeviceUpdateActivity.this.startConnect();
                return false;
            }
            if (i == 3) {
                DeviceUpdateActivity.this.mCanBack = true;
                DeviceUpdateActivity.this.mBinding.btnFinish.setEnabled(true);
                DialogUtils.showDialogWithBtnIds(DeviceUpdateActivity.this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.device_ota_pack_download_fail), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.DeviceUpdateActivity$2$$ExternalSyntheticLambda0
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i3, int i4, EditText editText) {
                        this.f$0.m117xf89f9916(context, dialogBuilder, dialog, i3, i4, editText);
                    }
                }, true, true, C0874R.string.cancel, C0874R.string.retry);
                return false;
            }
            if (i != 4) {
                if (i != 5) {
                    return false;
                }
                if (DeviceUpdateActivity.this.otaTask != null) {
                    DeviceUpdateActivity.this.otaTask.stopDataTransfer();
                }
                DeviceUpdateActivity.this.otaTask = null;
                DialogUtils.showDialogWithBtnIds(DeviceUpdateActivity.this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.device_update_fail), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.DeviceUpdateActivity$2$$ExternalSyntheticLambda1
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i3, int i4, EditText editText) {
                        this.f$0.m118x6e19bf57(context, dialogBuilder, dialog, i3, i4, editText);
                    }
                }, false, false, C0874R.string.cancel, C0874R.string.i_know);
                return false;
            }
            DeviceUpdateActivity.this.mCanBack = true;
            DeviceUpdateActivity.this.mBinding.btnFinish.setEnabled(true);
            if (DeviceUpdateActivity.this.otaTask != null) {
                DeviceUpdateActivity.this.otaTask.stopDataTransfer();
            }
            DeviceUpdateActivity.this.otaTask = null;
            DeviceUpdateActivity.this.finish();
            return false;
        }

        /* JADX INFO: renamed from: lambda$handleMessage$0$com-aivox-app-activity-DeviceUpdateActivity$2 */
        /* synthetic */ void m117xf89f9916(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            FileUtils.deleteFile(FileUtils.getOTAFilePath(DeviceUpdateActivity.this));
            if (BleBtService.getInstance().isGlass()) {
                DeviceUpdateActivity.this.wifiConnector.cleanup();
                DeviceUpdateActivity.this.wifiConnector = null;
                DeviceUpdateActivity.this.startGlassOtaDownload();
            } else {
                DeviceUpdateActivity deviceUpdateActivity = DeviceUpdateActivity.this;
                deviceUpdateActivity.startDownloadOtaFile(deviceUpdateActivity.getIntent().getStringExtra("url"));
            }
        }

        /* JADX INFO: renamed from: lambda$handleMessage$1$com-aivox-app-activity-DeviceUpdateActivity$2 */
        /* synthetic */ void m118x6e19bf57(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            DeviceUpdateActivity.this.finish();
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void onEventMainThread(EventBean eventBean) {
        super.onEventMainThread(eventBean);
        if (eventBean.getFrom() == 403 && (eventBean.getT() instanceof GlassesCmd)) {
            int i = C07497.$SwitchMap$com$aivox$base$common$GlassesCmd[((GlassesCmd) eventBean.getT()).ordinal()];
            if (i == 1) {
                this.glassState.setIp(eventBean.getS1());
                return;
            }
            if (i == 2) {
                if (TextUtils.isEmpty(eventBean.getS1())) {
                    return;
                }
                this.ssidSub.onNext(eventBean.getS1());
                return;
            } else {
                if (i == 3 && !TextUtils.isEmpty(eventBean.getS1())) {
                    this.passwdSub.onNext(eventBean.getS1());
                    return;
                }
                return;
            }
        }
        eventBean.getFrom();
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.DeviceUpdateActivity$7 */
    static /* synthetic */ class C07497 {
        static final /* synthetic */ int[] $SwitchMap$com$aivox$base$common$GlassesCmd;

        static {
            int[] iArr = new int[GlassesCmd.values().length];
            $SwitchMap$com$aivox$base$common$GlassesCmd = iArr;
            try {
                iArr[GlassesCmd.REPORT_WIFI_STATUS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.REPORT_AP_SSID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aivox$base$common$GlassesCmd[GlassesCmd.REPORT_AP_PASSWORD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startConnect() {
        CommonServiceUtils.getInstance().stopService(this);
        EventBus.getDefault().post(new EventBean(301));
        LogUtil.m335d(this.TAG, "startConnect");
        HmDevice hmDevice = new HmDevice();
        if (BleBtService.getInstance().isGlass()) {
            hmDevice.setDeviceName(BleBtService.getInstance().getConnectedDeviceName());
        } else {
            hmDevice.setDeviceName("AI-801S");
        }
        hmDevice.setPreferredProtocol(DeviceProtocol.PROTOCOL_SPP);
        hmDevice.setDeviceMAC((String) SPUtil.get(SPUtil.CONNECTED_DEVICE_ADDRESS, ""));
        hmDevice.setRssi(1000);
        this.mDevice = BtHeleper.getBluetoothAdapter(this).getRemoteDevice(hmDevice.getPreferredProtocol() == DeviceProtocol.PROTOCOL_BLE ? hmDevice.getBleAddress() : hmDevice.getDeviceMAC());
        BesServiceConfig besServiceConfig = new BesServiceConfig();
        besServiceConfig.setDeviceProtocol(DeviceProtocol.PROTOCOL_SPP);
        besServiceConfig.setDevice(hmDevice);
        besServiceConfig.setUSER_FLAG(1);
        besServiceConfig.setServiceUUID(BesSdkConstants.BES_OTA_SERVICE_OTA_UUID);
        besServiceConfig.setTotaConnect(false);
        BesOtaService besOtaService = new BesOtaService(besServiceConfig, this, this);
        this.besOtaService = besOtaService;
        this.otaTask = besOtaService;
    }

    @Override // com.aivox.besota.bessdk.service.base.BesServiceListener
    public void onTotaConnectState(boolean z, HmDevice hmDevice) {
        LogUtil.m335d(this.TAG, "onTotaConnectState" + z);
    }

    @Override // com.aivox.besota.bessdk.service.base.BesServiceListener
    public void onErrorMessage(int i, HmDevice hmDevice) {
        LogUtil.m335d(this.TAG, "onErrorMessage" + i);
        Message message = new Message();
        message.what = 5;
        this.mHandler2.sendMessage(message);
    }

    @Override // com.aivox.besota.bessdk.service.base.BesServiceListener
    public void onStateChangedMessage(final int i, String str, HmDevice hmDevice) {
        runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceUpdateActivity.3
            @Override // java.lang.Runnable
            public void run() {
                int i2 = i;
                if (i2 != 666) {
                    if (i2 != 2312) {
                        return;
                    }
                    File file = new File(FileUtils.getAppPath(DeviceUpdateActivity.this, FileUtils.OTA), DeviceUpdateActivity.this.fileName);
                    if (!file.exists()) {
                        LogUtil.m335d(DeviceUpdateActivity.this.TAG, "otaFile.exists FALSE");
                    }
                    SPHelper.putPreference(DeviceUpdateActivity.this, BesOTAConstants.BES_OTA_IS_MULTIDEVICE_UPGRADE, "");
                    RemoteOTAConfig remoteOTAConfig = new RemoteOTAConfig();
                    remoteOTAConfig.setLocalPath(file.getAbsolutePath());
                    DeviceUpdateActivity.this.otaTask.setOtaConfig(remoteOTAConfig);
                    DeviceUpdateActivity.this.otaTask.startDataTransfer(new OTADfuInfo("001", 1), DeviceUpdateActivity.this);
                    return;
                }
                if (DeviceUpdateActivity.this.isUpdateSuccess) {
                    return;
                }
                File file2 = new File(FileUtils.getAppPath(DeviceUpdateActivity.this, FileUtils.OTA), DeviceUpdateActivity.this.fileName);
                if (!file2.exists()) {
                    LogUtil.m335d(DeviceUpdateActivity.this.TAG, "otaFile.exists FALSE");
                }
                SPHelper.putPreference(DeviceUpdateActivity.this, BesOTAConstants.BES_OTA_IS_MULTIDEVICE_UPGRADE, "");
                RemoteOTAConfig remoteOTAConfig2 = new RemoteOTAConfig();
                remoteOTAConfig2.setLocalPath(file2.getAbsolutePath());
                if (DeviceUpdateActivity.this.otaTask == null) {
                    Message message = new Message();
                    message.what = 5;
                    DeviceUpdateActivity.this.mHandler2.sendMessage(message);
                } else {
                    DeviceUpdateActivity.this.otaTask.setOtaConfig(remoteOTAConfig2);
                    DeviceUpdateActivity.this.otaTask.startDataTransfer(new OTADfuInfo("001", 1), DeviceUpdateActivity.this);
                }
            }
        });
    }

    @Override // com.aivox.besota.bessdk.service.base.BesServiceListener
    public void onSuccessMessage(int i, HmDevice hmDevice) {
        LogUtil.m335d(this.TAG, "onSuccessMessage" + i);
        this.isUpdateSuccess = true;
        Message message = new Message();
        message.what = 4;
        this.mHandler2.sendMessage(message);
    }

    @Override // com.aivox.besota.sdk.ota.OTATask.StatusListener
    public void onOTAStatusChanged(OTAStatus oTAStatus, HmDevice hmDevice) {
        LogUtil.m335d(this.TAG, "onOTAStatusChanged" + oTAStatus.getName() + oTAStatus.getValue());
    }

    @Override // com.aivox.besota.sdk.ota.OTATask.StatusListener
    public void onOTAProgressChanged(int i, HmDevice hmDevice) {
        if (this.updateBoth) {
            setOtaProgress((i / 2) + 50);
        } else {
            setOtaProgress(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startJieLiConnect() {
        BluetoothDevice remoteDevice = BluetoothUtil.getRemoteDevice((String) SPUtil.get(SPUtil.CONNECTED_DEVICE_ADDRESS, ""));
        if (this.bluetoothHelper.isDeviceConnected(remoteDevice)) {
            this.bluetoothHelper.disconnectDevice(remoteDevice);
        } else if (remoteDevice != null) {
            this.bluetoothHelper.connectDevice(remoteDevice);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startJieLiOTA() {
        File file = new File(FileUtils.getAppPath(this, FileUtils.OTA), this.fileName);
        if (!file.exists()) {
            LogUtil.m337e(this.TAG, "startJieLiOTA : update File not exist");
            Message message = new Message();
            message.what = 5;
            this.mHandler2.sendMessage(message);
            return;
        }
        if (this.bluetoothHelper.getConnectedDevice() == null) {
            LogUtil.m337e(this.TAG, "startJieLiOTA : no connected device.");
            Message message2 = new Message();
            message2.what = 5;
            this.mHandler2.sendMessage(message2);
            return;
        }
        if (this.manager.isOTA()) {
            LogUtil.m339i(this.TAG, "startJieLiOTA : OTA already start.");
        } else {
            this.manager.getBluetoothOption().setFirmwareFilePath(file.getAbsolutePath());
            this.manager.startOTA(new UpgradeCbHelper() { // from class: com.aivox.app.activity.DeviceUpdateActivity.5
                @Override // com.jieli.jl_bt_ota.tool.UpgradeCbHelper, com.jieli.jl_bt_ota.interfaces.IUpgradeCallback
                public void onNeedReconnect(String str, boolean z) {
                    LogUtil.m339i(DeviceUpdateActivity.this.TAG, "onNeedReconnect : " + z + "\t" + str);
                }

                @Override // com.jieli.jl_bt_ota.tool.UpgradeCbHelper, com.jieli.jl_bt_ota.interfaces.IUpgradeCallback
                public void onProgress(int i, float f) {
                    LogUtil.m339i(DeviceUpdateActivity.this.TAG, "onProgress : " + i + "\t" + f);
                    if (i == 1) {
                        DeviceUpdateActivity.this.mBinding.pbProgress.setProgress((int) f);
                    }
                }

                @Override // com.jieli.jl_bt_ota.tool.UpgradeCbHelper, com.jieli.jl_bt_ota.interfaces.IUpgradeCallback
                public void onStopOTA() {
                    DeviceUpdateActivity.this.isUpdateSuccess = true;
                    Message message3 = new Message();
                    message3.what = 4;
                    DeviceUpdateActivity.this.mHandler2.sendMessage(message3);
                    LogUtil.m337e(DeviceUpdateActivity.this.TAG, "onStopOTA");
                }

                @Override // com.jieli.jl_bt_ota.tool.UpgradeCbHelper, com.jieli.jl_bt_ota.interfaces.IUpgradeCallback
                public void onCancelOTA() {
                    LogUtil.m337e(DeviceUpdateActivity.this.TAG, "onCancelOTA");
                }

                @Override // com.jieli.jl_bt_ota.tool.UpgradeCbHelper, com.jieli.jl_bt_ota.interfaces.IUpgradeCallback
                public void onError(BaseError baseError) {
                    LogUtil.m337e(DeviceUpdateActivity.this.TAG, "onErrorOTA : " + baseError.getMessage());
                    Message message3 = new Message();
                    message3.what = 5;
                    DeviceUpdateActivity.this.mHandler2.sendMessage(message3);
                }
            });
        }
    }

    private synchronized void connectWifi(String str, String str2) {
        LogUtil.m335d(this.TAG, "connectWifi:" + str + " " + str2);
        if (AntiShake.check(this)) {
            return;
        }
        if (this.wifiConnector == null) {
            this.wifiConnector = new WifiConnector(this);
        }
        LogUtil.m335d(this.TAG, "wifiConnector create");
        this.wifiConnector.setWifiConnectionListener(new C07486());
        this.wifiConnector.connectToWifi(str, str2);
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.DeviceUpdateActivity$6 */
    class C07486 implements WifiConnector.WifiConnectionListener {
        C07486() {
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiConnecting(String str) {
            LogUtil.m335d(DeviceUpdateActivity.this.TAG, "WiFi开始连接");
            DeviceUpdateActivity.this.mDis.add(Completable.timer(30L, TimeUnit.SECONDS, Schedulers.m1898io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.aivox.app.activity.DeviceUpdateActivity$6$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Action
                public final void run() throws Exception {
                    this.f$0.m121x3f334fa3();
                }
            }));
        }

        /* JADX INFO: renamed from: lambda$onWifiConnecting$2$com-aivox-app-activity-DeviceUpdateActivity$6 */
        /* synthetic */ void m121x3f334fa3() throws Exception {
            if (DeviceUpdateActivity.this.isWifiConnected) {
                return;
            }
            LogUtil.m337e(DeviceUpdateActivity.this.TAG, "WiFi连接超时");
            DeviceUpdateActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.DeviceUpdateActivity$6$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m120xc9b92962();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onWifiConnecting$1$com-aivox-app-activity-DeviceUpdateActivity$6 */
        /* synthetic */ void m120xc9b92962() {
            DialogUtils.showDialogWithBtnIds(DeviceUpdateActivity.this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.wifi_connection_timeout), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.DeviceUpdateActivity$6$$ExternalSyntheticLambda1
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m119x543f0321(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, true, true, C0874R.string.cancel, C0874R.string.retry);
        }

        /* JADX INFO: renamed from: lambda$onWifiConnecting$0$com-aivox-app-activity-DeviceUpdateActivity$6 */
        /* synthetic */ void m119x543f0321(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            FileUtils.deleteFile(FileUtils.getOTAFilePath(DeviceUpdateActivity.this));
            if (BleBtService.getInstance().isGlass()) {
                DeviceUpdateActivity.this.wifiConnector.cleanup();
                DeviceUpdateActivity.this.wifiConnector = null;
                DeviceUpdateActivity.this.startGlassOtaDownload();
            } else {
                DeviceUpdateActivity deviceUpdateActivity = DeviceUpdateActivity.this;
                deviceUpdateActivity.startDownloadOtaFile(deviceUpdateActivity.getIntent().getStringExtra("url"));
            }
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiConnected(String str, Network network) {
            LogUtil.m335d(DeviceUpdateActivity.this.TAG, "WiFi已连接");
            DeviceUpdateActivity.this.isWifiConnected = true;
            DeviceUpdateActivity.this.uploadFirmware();
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiConnectionFailed(String str, String str2) {
            LogUtil.m335d(DeviceUpdateActivity.this.TAG, "WiFi连接失败");
            DeviceUpdateActivity.this.isWifiConnected = false;
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiDisconnected(String str, String str2) {
            LogUtil.m335d(DeviceUpdateActivity.this.TAG, "WiFi已断开:" + str2);
            DeviceUpdateActivity.this.isWifiConnected = false;
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiEnabledRequired() {
            LogUtil.m335d(DeviceUpdateActivity.this.TAG, "WiFi已启用");
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onPermissionsRequired(List<String> list) {
            LogUtil.m335d(DeviceUpdateActivity.this.TAG, "权限不足");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadFirmware() {
        String str = "http://" + this.glassState.getIp() + "/api/glass/ota";
        LogUtil.m335d(this.TAG, "start:uploadFirmware" + str);
        File file = new File(FileUtils.getAppPath(this, FileUtils.OTA), this.wifiFileName);
        if (file.exists()) {
            LogUtil.m335d(this.TAG, "file:" + file.getAbsolutePath() + "\t size:" + file.length());
            MultipartBody.Part partCreateFormData = MultipartBody.Part.createFormData("file", file.getName(), new ProgressRequestBody(RequestBody.create(MediaType.parse("application/x-gzip"), file), new ProgressRequestListener() { // from class: com.aivox.app.activity.DeviceUpdateActivity$$ExternalSyntheticLambda10
                @Override // com.aivox.base.http.progress.ProgressRequestListener
                public final void onRequestProgress(long j, long j2, boolean z) {
                    this.f$0.m115x19616107(j, j2, z);
                }
            }));
            LogUtil.m335d(this.TAG, "start upload:" + str);
            this.mDis.add(new AudioService(this).glassOtaApi(str, partCreateFormData).subscribe(new Consumer() { // from class: com.aivox.app.activity.DeviceUpdateActivity$$ExternalSyntheticLambda11
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m116xb4022388((String) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.DeviceUpdateActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m114xc8bbd724((Throwable) obj);
                }
            }));
        }
    }

    /* JADX INFO: renamed from: lambda$uploadFirmware$8$com-aivox-app-activity-DeviceUpdateActivity */
    /* synthetic */ void m115x19616107(long j, long j2, boolean z) {
        long j3;
        long j4;
        long j5 = 100;
        if (!z) {
            try {
                long j6 = (j * 100) / j2;
                if (j6 <= 100) {
                    j5 = j6;
                }
            } catch (Exception e) {
                LogUtil.m337e(this.TAG, "uploadFirmware error:" + e.getLocalizedMessage());
                return;
            }
        }
        if (this.updateBoth) {
            j3 = (j5 * 2) / 5;
            j4 = 10;
        } else {
            j3 = (j5 * 4) / 5;
            j4 = 20;
        }
        setOtaProgress(j3 + j4);
    }

    /* JADX INFO: renamed from: lambda$uploadFirmware$9$com-aivox-app-activity-DeviceUpdateActivity */
    /* synthetic */ void m116xb4022388(String str) throws Exception {
        BleBtService.getInstance().sendGlassCmd(GlassesCmd.WIFI_AP_CONTROL, 0);
        this.wifiConnector.cleanup();
        this.wifiConnector = null;
        if (this.glassCount == 3) {
            this.glassCount = 1;
            startConnect();
            return;
        }
        this.glassCount = 0;
        this.isUpdateSuccess = true;
        Message message = new Message();
        message.what = 4;
        this.mHandler2.sendMessage(message);
    }

    /* JADX INFO: renamed from: lambda$uploadFirmware$10$com-aivox-app-activity-DeviceUpdateActivity */
    /* synthetic */ void m114xc8bbd724(Throwable th) throws Exception {
        LogUtil.m339i(this.TAG, "glassApi error:" + th.getLocalizedMessage());
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        }
    }
}
