package com.aivox.app.activity;

import android.content.res.Configuration;
import android.net.Network;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityGlassPreviewBinding;
import com.aivox.app.util.VlcPlayer;
import com.aivox.base.C0874R;
import com.aivox.base.common.GlassesCmd;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.ThreadPoolManager;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.model.EventBean;
import com.aivox.common.util.WifiConnector;
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
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class GlassPreviewActivity extends BaseFragmentActivity {
    private ActivityGlassPreviewBinding mBinding;
    private VlcPlayer vlcPlayer;
    private WifiConnector wifiConnector;
    private boolean isWifiConnected = false;
    private final PublishSubject<String> ssidSub = PublishSubject.create();
    private final PublishSubject<String> passwdSub = PublishSubject.create();
    private final CompositeDisposable mDis = new CompositeDisposable();

    /* JADX INFO: renamed from: $r8$lambda$pnZHaLsuWh0mdbgsX--fRG3ls6s, reason: not valid java name */
    public static /* synthetic */ Pair m2124$r8$lambda$pnZHaLsuWh0mdbgsXfRG3ls6s(Object obj, Object obj2) {
        return new Pair(obj, obj2);
    }

    public GlassPreviewActivity() {
        this.setPortrait = false;
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.GlassPreviewActivity$2 */
    static /* synthetic */ class C07622 {
        static final /* synthetic */ int[] $SwitchMap$com$aivox$base$common$GlassesCmd;

        static {
            int[] iArr = new int[GlassesCmd.values().length];
            $SwitchMap$com$aivox$base$common$GlassesCmd = iArr;
            try {
                iArr[GlassesCmd.VIDEO_API.ordinal()] = 1;
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

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void onEventMainThread(EventBean eventBean) {
        if (eventBean.getT() instanceof GlassesCmd) {
            int i = C07622.$SwitchMap$com$aivox$base$common$GlassesCmd[((GlassesCmd) eventBean.getT()).ordinal()];
            if (i == 1) {
                if (TextUtils.isEmpty(eventBean.getS1())) {
                    return;
                }
                this.vlcPlayer.play(this.wifiConnector.currentTargetSsid, eventBean.getS1());
            } else if (i == 2) {
                if (TextUtils.isEmpty(eventBean.getS1())) {
                    return;
                }
                this.ssidSub.onNext(eventBean.getS1());
            } else if (i == 3 && !TextUtils.isEmpty(eventBean.getS1())) {
                this.passwdSub.onNext(eventBean.getS1());
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        WifiConnector wifiConnector = this.wifiConnector;
        if (wifiConnector != null) {
            wifiConnector.retryConnectionAfterEnablingWifi();
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        DialogUtils.hideLoadingDialog();
        this.mDis.clear();
        ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.aivox.app.activity.GlassPreviewActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2130lambda$onDestroy$0$comaivoxappactivityGlassPreviewActivity();
            }
        });
        stopRtsp();
        super.onDestroy();
    }

    /* JADX INFO: renamed from: lambda$onDestroy$0$com-aivox-app-activity-GlassPreviewActivity, reason: not valid java name */
    /* synthetic */ void m2130lambda$onDestroy$0$comaivoxappactivityGlassPreviewActivity() {
        WifiConnector wifiConnector = this.wifiConnector;
        if (wifiConnector != null) {
            wifiConnector.cleanup();
            this.wifiConnector = null;
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration.orientation == 2) {
            changeFullScreen(true);
        } else if (configuration.orientation == 1) {
            changeFullScreen(false);
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivityGlassPreviewBinding activityGlassPreviewBinding = (ActivityGlassPreviewBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_glass_preview);
        this.mBinding = activityGlassPreviewBinding;
        activityGlassPreviewBinding.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.GlassPreviewActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2125lambda$initView$1$comaivoxappactivityGlassPreviewActivity(view2);
            }
        });
        if (this.vlcPlayer == null) {
            this.vlcPlayer = new VlcPlayer(this, this.mBinding.f120sv);
        }
        DialogUtils.showLoadingDialog(this, "正在打开实时视频...", true);
        BleBtService.getInstance().sendGlassCmd(GlassesCmd.WIFI_AP_CONTROL, 1);
        changeFullScreen(true);
        this.wifiConnector = new WifiConnector(this);
        this.mDis.add(Observable.combineLatest(this.ssidSub, this.passwdSub, new BiFunction() { // from class: com.aivox.app.activity.GlassPreviewActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return GlassPreviewActivity.m2124$r8$lambda$pnZHaLsuWh0mdbgsXfRG3ls6s((String) obj, (String) obj2);
            }
        }).filter(new Predicate() { // from class: com.aivox.app.activity.GlassPreviewActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return GlassPreviewActivity.lambda$initView$2((Pair) obj);
            }
        }).observeOn(Schedulers.m1898io()).flatMapCompletable(new Function() { // from class: com.aivox.app.activity.GlassPreviewActivity$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m2127lambda$initView$4$comaivoxappactivityGlassPreviewActivity((Pair) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.aivox.app.activity.GlassPreviewActivity$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m2128lambda$initView$5$comaivoxappactivityGlassPreviewActivity();
            }
        }, new Consumer() { // from class: com.aivox.app.activity.GlassPreviewActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2129lambda$initView$6$comaivoxappactivityGlassPreviewActivity((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-GlassPreviewActivity, reason: not valid java name */
    /* synthetic */ void m2125lambda$initView$1$comaivoxappactivityGlassPreviewActivity(View view2) {
        finish();
    }

    static /* synthetic */ boolean lambda$initView$2(Pair pair) throws Exception {
        return (TextUtils.isEmpty((CharSequence) pair.first) || TextUtils.isEmpty((CharSequence) pair.second)) ? false : true;
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-app-activity-GlassPreviewActivity, reason: not valid java name */
    /* synthetic */ void m2126lambda$initView$3$comaivoxappactivityGlassPreviewActivity(Pair pair) throws Exception {
        connectWifi((String) pair.first, (String) pair.second);
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-app-activity-GlassPreviewActivity, reason: not valid java name */
    /* synthetic */ CompletableSource m2127lambda$initView$4$comaivoxappactivityGlassPreviewActivity(final Pair pair) throws Exception {
        return Completable.fromAction(new Action() { // from class: com.aivox.app.activity.GlassPreviewActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m2126lambda$initView$3$comaivoxappactivityGlassPreviewActivity(pair);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-app-activity-GlassPreviewActivity, reason: not valid java name */
    /* synthetic */ void m2128lambda$initView$5$comaivoxappactivityGlassPreviewActivity() throws Exception {
        Log.d(this.TAG, "开始连接WiFi！");
    }

    /* JADX INFO: renamed from: lambda$initView$6$com-aivox-app-activity-GlassPreviewActivity, reason: not valid java name */
    /* synthetic */ void m2129lambda$initView$6$comaivoxappactivityGlassPreviewActivity(Throwable th) throws Exception {
        Log.e(this.TAG, "开始连接WiFi失败", th);
    }

    private void connectWifi(String str, String str2) {
        LogUtil.m335d(this.TAG, "ssid：" + str + "\tpassword：" + str2);
        this.wifiConnector.setWifiConnectionListener(new C07611());
        LogUtil.m335d(this.TAG, "开始连接WiFi");
        this.wifiConnector.connectToWifi(str, str2);
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.GlassPreviewActivity$1 */
    class C07611 implements WifiConnector.WifiConnectionListener {
        C07611() {
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiConnecting(String str) {
            LogUtil.m337e(GlassPreviewActivity.this.TAG, "WiFi开始连接");
            GlassPreviewActivity.this.mDis.add(Completable.timer(30L, TimeUnit.SECONDS, Schedulers.m1898io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.aivox.app.activity.GlassPreviewActivity$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Action
                public final void run() throws Exception {
                    this.f$0.m148xff03e4c9();
                }
            }));
        }

        /* JADX INFO: renamed from: lambda$onWifiConnecting$0$com-aivox-app-activity-GlassPreviewActivity$1 */
        /* synthetic */ void m148xff03e4c9() throws Exception {
            if (GlassPreviewActivity.this.isWifiConnected) {
                return;
            }
            LogUtil.m337e(GlassPreviewActivity.this.TAG, "WiFi连接超时");
            DialogUtils.hideLoadingDialog();
            ToastUtil.showLong(GlassPreviewActivity.this.getString(C0874R.string.wifi_connection_timeout));
            GlassPreviewActivity.this.finish();
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiConnected(String str, Network network) {
            LogUtil.m335d(GlassPreviewActivity.this.TAG, "WiFi已连接");
            GlassPreviewActivity.this.isWifiConnected = true;
            BleBtService.getInstance().sendGlassCmd(GlassesCmd.VIDEO_PREVIEW_CONTROL, 1);
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiConnectionFailed(String str, String str2) {
            LogUtil.m335d(GlassPreviewActivity.this.TAG, "WiFi连接失败");
            GlassPreviewActivity.this.isWifiConnected = false;
            GlassPreviewActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.GlassPreviewActivity$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m149xc93c424d();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onWifiConnectionFailed$1$com-aivox-app-activity-GlassPreviewActivity$1 */
        /* synthetic */ void m149xc93c424d() {
            DialogUtils.hideLoadingDialog();
            ToastUtil.showLong(GlassPreviewActivity.this.getString(C0874R.string.wifi_connection_failed));
            GlassPreviewActivity.this.finish();
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiDisconnected(String str, String str2) {
            LogUtil.m335d(GlassPreviewActivity.this.TAG, "WiFi断开连接");
            GlassPreviewActivity.this.isWifiConnected = false;
            GlassPreviewActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.app.activity.GlassPreviewActivity$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m150xf30c768e();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onWifiDisconnected$2$com-aivox-app-activity-GlassPreviewActivity$1 */
        /* synthetic */ void m150xf30c768e() {
            DialogUtils.hideLoadingDialog();
            ToastUtil.showLong(GlassPreviewActivity.this.getString(C0874R.string.wifi_disconnected));
            GlassPreviewActivity.this.finish();
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onWifiEnabledRequired() {
            LogUtil.m335d(GlassPreviewActivity.this.TAG, "WiFi已启用");
        }

        @Override // com.aivox.common.util.WifiConnector.WifiConnectionListener
        public void onPermissionsRequired(List<String> list) {
            LogUtil.m335d(GlassPreviewActivity.this.TAG, "权限不足");
        }
    }

    private void stopRtsp() {
        VlcPlayer vlcPlayer = this.vlcPlayer;
        if (vlcPlayer != null) {
            if (vlcPlayer.isPlaying()) {
                this.vlcPlayer.stop();
            }
            this.vlcPlayer.release();
            this.vlcPlayer = null;
        }
        EventBus.getDefault().post(new EventBean(402, 0));
    }

    private void changeFullScreen(boolean z) {
        if (z) {
            getWindow().addFlags(1024);
        } else {
            getWindow().clearFlags(1024);
        }
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.mBinding.clParent);
        constraintSet.constrainWidth(C0726R.id.f114fl, 0);
        constraintSet.constrainHeight(C0726R.id.f114fl, 0);
        constraintSet.connect(C0726R.id.f114fl, 3, 0, 3, 0);
        constraintSet.connect(C0726R.id.f114fl, 4, 0, 4, 0);
        constraintSet.connect(C0726R.id.f114fl, 6, 0, 6, 0);
        constraintSet.connect(C0726R.id.f114fl, 7, 0, 7, 0);
        constraintSet.applyTo(this.mBinding.clParent);
    }
}
