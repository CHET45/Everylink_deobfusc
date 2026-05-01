package com.aivox.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.DataBindingUtil;
import androidx.exifinterface.media.ExifInterface;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityDeviceConnectedBinding;
import com.aivox.app.util.VersionUtil;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.common.GlassesCmd;
import com.aivox.base.common.MyEnum;
import com.aivox.base.http.HttpException;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.ble.service.BleBtService;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.GlassOtaUpdateBean;
import com.aivox.common.model.OtaUpdateBean;
import com.aivox.common.parse.SendManager;
import com.aivox.common.util.AppUtils;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.EqScrollSelectView;
import com.github.houbb.heaven.constant.PunctuationConst;
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
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class DeviceConnectedActivity extends BaseFragmentActivity {
    private static final String bleDataDivider = "-";
    private int glassBattery;
    private String hardwareInfo;
    private boolean isJieLiDevice;
    private ActivityDeviceConnectedBinding mBinding;
    private boolean mByUserCheck;
    private boolean isQueryAntiShake = false;
    private final PublishSubject<String> btVersionSub = PublishSubject.create();
    private final PublishSubject<String> wifiVersionSub = PublishSubject.create();
    private final CompositeDisposable mDis = new CompositeDisposable();

    /* JADX INFO: renamed from: $r8$lambda$pnZHaLsuWh0mdbgsX--fRG3ls6s, reason: not valid java name */
    public static /* synthetic */ Pair m2067$r8$lambda$pnZHaLsuWh0mdbgsXfRG3ls6s(Object obj, Object obj2) {
        return new Pair(obj, obj2);
    }

    static /* synthetic */ GlassesCmd lambda$initView$16(GlassesCmd glassesCmd, Long l) throws Exception {
        return glassesCmd;
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        String stringExtra;
        ActivityDeviceConnectedBinding activityDeviceConnectedBinding = (ActivityDeviceConnectedBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_device_connected);
        this.mBinding = activityDeviceConnectedBinding;
        activityDeviceConnectedBinding.llInfo.setVisibility(BleBtService.getInstance().isGlass() ? 8 : 0);
        this.mBinding.llGlassCurrent.setVisibility(BleBtService.getInstance().isGlass() ? 0 : 8);
        this.mBinding.stvFiles.postDelayed(new Runnable() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                DeviceConnectedActivity.lambda$initView$0();
            }
        }, 500L);
        if (BaseStringUtil.isEmpty(getIntent().getStringExtra("deviceName"))) {
            stringExtra = CommonServiceUtils.getInstance().getConnectedDeviceName();
        } else {
            stringExtra = getIntent().getStringExtra("deviceName");
        }
        this.isJieLiDevice = MyEnum.DEVICE_MODEL.isJieLiDevice(stringExtra);
        if (getIntent().getBooleanExtra("isConnect", false)) {
            ToastUtil.showShort(getString(C0874R.string.earphone_device_connected));
        }
        if (MyEnum.DEVICE_MODEL.WATER3F_AI.name.equals(stringExtra) || MyEnum.DEVICE_MODEL.MARVO_AI_FUTURE.name.equals(stringExtra) || MyEnum.DEVICE_MODEL.AIVOX_R7.name.equals(stringExtra)) {
            this.mBinding.llNc.setVisibility(0);
            this.mBinding.llEq.setVisibility(0);
        }
        if (MyEnum.DEVICE_MODEL.TRANSLATER_T3.name.equals(stringExtra) || MyEnum.DEVICE_MODEL.DIGI_GIFT_BOX.name.equals(stringExtra) || MyEnum.BLE_DEVICE_MODEL.AI_GLASSES_PAI_08.name.equals(stringExtra) || MyEnum.BLE_DEVICE_MODEL.HY_15.name.equals(stringExtra) || MyEnum.BLE_DEVICE_MODEL.HY_16.name.equals(stringExtra)) {
            this.mBinding.epLeft.setVisibility(4);
            this.mBinding.epRight.setVisibility(4);
        }
        if (MyEnum.DEVICE_MODEL.HAPPYLEMON_HL3.name.equals(stringExtra)) {
            this.mBinding.epPack.setVisibility(8);
        }
        if (MyEnum.DEVICE_MODEL.DIGI_GIFT_BOX.name.equals(stringExtra)) {
            this.mBinding.stvFiles.setVisibility(0);
        }
        this.mBinding.ivEarphoneIcon.setImageResource(MyEnum.DEVICE_MODEL.getDeviceIcon(stringExtra));
        this.mBinding.stvEq.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2069lambda$initView$2$comaivoxappactivityDeviceConnectedActivity(view2);
            }
        });
        this.mBinding.tvNcNormal.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2070lambda$initView$3$comaivoxappactivityDeviceConnectedActivity(view2);
            }
        });
        this.mBinding.tvNcOpen.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2071lambda$initView$4$comaivoxappactivityDeviceConnectedActivity(view2);
            }
        });
        this.mBinding.tvNcTrans.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2072lambda$initView$5$comaivoxappactivityDeviceConnectedActivity(view2);
            }
        });
        this.mBinding.btnDisconnect.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2073lambda$initView$6$comaivoxappactivityDeviceConnectedActivity(view2);
            }
        });
        if (!BleBtService.getInstance().isGlass()) {
            this.mBinding.titleView.setTitle(stringExtra);
            this.mBinding.titleView.setRightName(getString(C0874R.string.soft_update_updatebtn));
            this.mBinding.titleView.setRightColor(getColor(C0874R.color.txt_color_tertiary));
            this.mBinding.titleView.setRightListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda12
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2074lambda$initView$7$comaivoxappactivityDeviceConnectedActivity(view2);
                }
            });
            this.mBinding.stvVersion.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda13
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2075lambda$initView$8$comaivoxappactivityDeviceConnectedActivity(view2);
                }
            });
            this.mBinding.stvFiles.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda14
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2076lambda$initView$9$comaivoxappactivityDeviceConnectedActivity(view2);
                }
            });
            return;
        }
        this.mBinding.llSettings.setVisibility(0);
        this.mBinding.stvAntiShake.enableSwitch();
        ((SwitchCompat) this.mBinding.stvAntiShake.findViewById(C1034R.id.switch_right)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda15
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m94xd5bee405(compoundButton, z);
            }
        });
        this.mDis.add(Observable.combineLatest(this.btVersionSub, this.wifiVersionSub, new BiFunction() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda24
            @Override // io.reactivex.functions.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return DeviceConnectedActivity.m2067$r8$lambda$pnZHaLsuWh0mdbgsXfRG3ls6s((String) obj, (String) obj2);
            }
        }).filter(new Predicate() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda25
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return DeviceConnectedActivity.lambda$initView$11((Pair) obj);
            }
        }).observeOn(Schedulers.m1898io()).flatMapCompletable(new Function() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m96x831ec9a2((Pair) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m97xbce96b81();
            }
        }, new Consumer() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m98xf6b40d60((Throwable) obj);
            }
        }));
        this.mDis.add(Observable.fromArray(GlassesCmd.GET_ANTI_SHAKE, GlassesCmd.GET_VERSION, GlassesCmd.GET_HARDWARE_INFO, GlassesCmd.GET_SUPPORTED_FEATURES).zipWith(Observable.interval(0L, 200L, TimeUnit.MILLISECONDS), new BiFunction() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return DeviceConnectedActivity.lambda$initView$16((GlassesCmd) obj, (Long) obj2);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                BleBtService.getInstance().sendGlassCmd((GlassesCmd) obj);
            }
        }));
    }

    static /* synthetic */ void lambda$initView$0() {
        if (BleBtService.getInstance().isGlass()) {
            BleBtService.getInstance().sendGlassCmd(GlassesCmd.GET_BATTERY);
        } else {
            SendManager.getInstance().sendSppData(Constant.CmdUpBatteryLevel);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-app-activity-DeviceConnectedActivity, reason: not valid java name */
    /* synthetic */ void m2069lambda$initView$2$comaivoxappactivityDeviceConnectedActivity(View view2) {
        DialogUtils.showBottomSheetDialog(this.context, new EqScrollSelectView(this.context, new EqScrollSelectView.DateSelectListener() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda0
            @Override // com.aivox.common_ui.EqScrollSelectView.DateSelectListener
            public final void onDateSelected(int i) {
                this.f$0.m2068lambda$initView$1$comaivoxappactivityDeviceConnectedActivity(i);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-DeviceConnectedActivity, reason: not valid java name */
    /* synthetic */ void m2068lambda$initView$1$comaivoxappactivityDeviceConnectedActivity(int i) {
        if (!BleBtService.getInstance().isGlass()) {
            SendManager.getInstance().sendSppData(Constant.CmdUpHead + MyEnum.DEVICE_EFFECT.getEffect(i, false).code);
        }
        this.mBinding.stvEq.setMsg(getString(MyEnum.DEVICE_EFFECT.getEffect(i, false).msgRes));
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-app-activity-DeviceConnectedActivity, reason: not valid java name */
    /* synthetic */ void m2070lambda$initView$3$comaivoxappactivityDeviceConnectedActivity(View view2) {
        switchNcMode(MyEnum.DEVICE_VOICE_MODE.NORMAL.index.intValue(), true);
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-app-activity-DeviceConnectedActivity, reason: not valid java name */
    /* synthetic */ void m2071lambda$initView$4$comaivoxappactivityDeviceConnectedActivity(View view2) {
        switchNcMode(MyEnum.DEVICE_VOICE_MODE.DENOISE.index.intValue(), true);
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-app-activity-DeviceConnectedActivity, reason: not valid java name */
    /* synthetic */ void m2072lambda$initView$5$comaivoxappactivityDeviceConnectedActivity(View view2) {
        switchNcMode(MyEnum.DEVICE_VOICE_MODE.AMBIENT.index.intValue(), true);
    }

    /* JADX INFO: renamed from: lambda$initView$6$com-aivox-app-activity-DeviceConnectedActivity, reason: not valid java name */
    /* synthetic */ void m2073lambda$initView$6$comaivoxappactivityDeviceConnectedActivity(View view2) {
        DataHandle.getIns().setHasConnectedBle(false);
        DataHandle.getIns().setHasConnectedSpp(false);
        CommonServiceUtils.getInstance().stopService(this);
        ToastUtil.showShort(Integer.valueOf(C0874R.string.earphone_disconnected));
        SPUtil.put(SPUtil.CONNECTED_DEVICE_ADDRESS, "");
        SPUtil.put(SPUtil.CONNECTED_DEVICE_NAME, "");
        EventBus.getDefault().post(new EventBean(301));
        if (BleBtService.getInstance().isGlass()) {
            BleBtService.getInstance().disconnect();
        }
        finish();
    }

    /* JADX INFO: renamed from: lambda$initView$7$com-aivox-app-activity-DeviceConnectedActivity, reason: not valid java name */
    /* synthetic */ void m2074lambda$initView$7$comaivoxappactivityDeviceConnectedActivity(View view2) {
        DialogUtils.showLoadingDialog(this.context);
        SendManager.getInstance().sendSppData(Constant.CmdUpEarphoneVersion);
        this.mByUserCheck = true;
    }

    /* JADX INFO: renamed from: lambda$initView$8$com-aivox-app-activity-DeviceConnectedActivity, reason: not valid java name */
    /* synthetic */ void m2075lambda$initView$8$comaivoxappactivityDeviceConnectedActivity(View view2) {
        DialogUtils.showLoadingDialog(this.context);
        SendManager.getInstance().sendSppData(Constant.CmdUpEarphoneVersion);
        this.mByUserCheck = true;
    }

    /* JADX INFO: renamed from: lambda$initView$9$com-aivox-app-activity-DeviceConnectedActivity, reason: not valid java name */
    /* synthetic */ void m2076lambda$initView$9$comaivoxappactivityDeviceConnectedActivity(View view2) {
        ARouterUtils.startWithContext(this.context, MainAction.DEVICE_FILES);
    }

    /* JADX INFO: renamed from: lambda$initView$10$com-aivox-app-activity-DeviceConnectedActivity */
    /* synthetic */ void m94xd5bee405(CompoundButton compoundButton, boolean z) {
        if (this.isQueryAntiShake) {
            return;
        }
        BleBtService.getInstance().sendGlassCmd(GlassesCmd.SET_ANTI_SHAKE, Integer.valueOf(z ? 1 : 0));
    }

    static /* synthetic */ boolean lambda$initView$11(Pair pair) throws Exception {
        return (TextUtils.isEmpty((CharSequence) pair.first) || TextUtils.isEmpty((CharSequence) pair.second)) ? false : true;
    }

    /* JADX INFO: renamed from: lambda$initView$12$com-aivox-app-activity-DeviceConnectedActivity */
    /* synthetic */ void m95x495427c3(Pair pair) throws Exception {
        parserGlassVersion((String) pair.first, (String) pair.second);
    }

    /* JADX INFO: renamed from: lambda$initView$13$com-aivox-app-activity-DeviceConnectedActivity */
    /* synthetic */ CompletableSource m96x831ec9a2(final Pair pair) throws Exception {
        return Completable.fromAction(new Action() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda22
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m95x495427c3(pair);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$14$com-aivox-app-activity-DeviceConnectedActivity */
    /* synthetic */ void m97xbce96b81() throws Exception {
        Log.d(this.TAG, "开始获取眼镜固件版本！");
    }

    /* JADX INFO: renamed from: lambda$initView$15$com-aivox-app-activity-DeviceConnectedActivity */
    /* synthetic */ void m98xf6b40d60(Throwable th) throws Exception {
        Log.e(this.TAG, "获取眼镜固件版本失败", th);
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void onEventMainThread(EventBean eventBean) {
        super.onEventMainThread(eventBean);
        int from = eventBean.getFrom();
        if (from == 305) {
            if (BleBtService.getInstance().isGlass()) {
                m101x8051256(eventBean.getS1());
                SendManager.getInstance().sendSppData(Constant.CmdUpRequestMode);
                return;
            }
            return;
        }
        if (from != 308) {
            if (from == 403) {
                if ((eventBean.getT() instanceof GlassesCmd) && eventBean.getT() == GlassesCmd.GET_ANTI_SHAKE) {
                    this.isQueryAntiShake = true;
                    this.mBinding.stvAntiShake.setChecked(eventBean.getA() == 1);
                    this.isQueryAntiShake = false;
                    return;
                }
                return;
            }
            if (from == 405) {
                this.hardwareInfo = eventBean.getS1();
                return;
            }
            if (from == 450) {
                this.btVersionSub.onNext(eventBean.getS1());
                this.mBinding.stvFirmware.setMsg(eventBean.getS1());
                return;
            }
            if (from != 451) {
                switch (from) {
                    case 302:
                        DialogUtils.hideLoadingDialog();
                        parseData(eventBean.getS1());
                        if (!BleBtService.getInstance().isGlass()) {
                            SendManager.getInstance().sendSppData(Constant.CmdUpEarphoneVersion);
                        }
                        break;
                    case 303:
                        parseEffect(eventBean.getS1());
                        break;
                }
                return;
            }
            this.wifiVersionSub.onNext(eventBean.getS1());
            this.mBinding.stvWifi.setMsg(eventBean.getS1());
            return;
        }
        finish();
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mDis.clear();
        super.onDestroy();
    }

    private void parseEffect(String str) {
        if (str.startsWith(Constant.CmdDownRequestMode)) {
            String[] strArrSplit = str.trim().split("-");
            if (strArrSplit.length >= 3) {
                int i = Integer.parseInt(strArrSplit[1].substring(0, 2));
                switchNcMode(MyEnum.DEVICE_VOICE_MODE.getVoiceMode(Integer.parseInt(strArrSplit[2].substring(0, 2)), true).index.intValue(), false);
                if (MyEnum.DEVICE_EFFECT.getEffect(i, true) != MyEnum.DEVICE_EFFECT.OFF) {
                    this.mBinding.stvEq.setMsg(getString(MyEnum.DEVICE_EFFECT.getEffect(i, true).msgRes));
                } else {
                    this.mBinding.stvEq.setMsg(getString(MyEnum.DEVICE_EFFECT.OFF.msgRes));
                }
            }
        }
    }

    private void parseData(String str) {
        LogUtil.m335d("blebtservice", "powers:" + str);
        if (str.startsWith(Constant.CmdDownBatteryLevel)) {
            String[] strArrSplit = str.split("-");
            if (strArrSplit.length >= 4) {
                this.mBinding.epLeft.setPower(strArrSplit[1], 1);
                this.mBinding.epRight.setPower(strArrSplit[2], 2);
                this.mBinding.epPack.setPower(strArrSplit[3], 3);
                this.mBinding.epPack.setVisibility(0);
                return;
            }
            if (strArrSplit.length == 3) {
                this.mBinding.epLeft.setPower(strArrSplit[1], 1);
                this.mBinding.epRight.setPower(strArrSplit[2], 2);
                this.mBinding.epPack.setVisibility(8);
            } else if (strArrSplit.length == 2) {
                this.mBinding.epPack.setPower(strArrSplit[1], 3);
                this.mBinding.epPack.setVisibility(0);
                if (BleBtService.getInstance().isGlass()) {
                    try {
                        this.glassBattery = Integer.parseInt(strArrSplit[1]);
                    } catch (NumberFormatException unused) {
                        this.glassBattery = 1;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: parseVersion, reason: merged with bridge method [inline-methods] */
    public void m101x8051256(final String str) {
        if (str.startsWith(Constant.CmdDownEarphoneVersion)) {
            String[] strArrSplit = str.split("-");
            if (strArrSplit.length >= 3) {
                String strTrim = strArrSplit[2].trim();
                if (BaseStringUtil.isNotEmpty(strTrim)) {
                    this.mBinding.stvVersion.setSubTitle(ExifInterface.GPS_MEASUREMENT_INTERRUPTED + strTrim);
                    this.mDis.add(new AudioService(this).checkOtaUpdate(strArrSplit[1], Double.parseDouble(strTrim.replace(PunctuationConst.COMMA, "."))).subscribe(new Consumer() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda16
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) throws Exception {
                            this.f$0.m100x109b292c((OtaUpdateBean) obj);
                        }
                    }, new Consumer() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda17
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) throws Exception {
                            this.f$0.m102x41cfb435(str, (Throwable) obj);
                        }
                    }));
                }
            }
        }
    }

    /* JADX INFO: renamed from: lambda$parseVersion$19$com-aivox-app-activity-DeviceConnectedActivity */
    /* synthetic */ void m100x109b292c(final OtaUpdateBean otaUpdateBean) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (otaUpdateBean.isUpdate()) {
            this.mBinding.titleView.setRightColor(getColor(C0874R.color.txt_color_warning));
            DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.device_has_new_version), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda18
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m99xd6d0874d(otaUpdateBean, context, dialogBuilder, dialog, i, i2, editText);
                }
            }, true, true, C0874R.string.cancel, C0874R.string.device_to_update);
        } else if (this.mByUserCheck) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.on_the_latest_version));
        }
    }

    /* JADX INFO: renamed from: lambda$parseVersion$18$com-aivox-app-activity-DeviceConnectedActivity */
    /* synthetic */ void m99xd6d0874d(OtaUpdateBean otaUpdateBean, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        Bundle bundle = new Bundle();
        bundle.putString("url", otaUpdateBean.getUrl());
        bundle.putBoolean("isJieLiDevice", this.isJieLiDevice);
        finish();
        ARouterUtils.startWithActivity(this, MainAction.DEVICE_UPDATE, bundle);
    }

    /* JADX INFO: renamed from: lambda$parseVersion$21$com-aivox-app-activity-DeviceConnectedActivity */
    /* synthetic */ void m102x41cfb435(final String str, Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda19
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m101x8051256(str);
                }
            });
        }
    }

    private void parserGlassVersion(final String str, final String str2) {
        if (BleBtService.getInstance().isGlass()) {
            this.mDis.add(new AudioService(this).checkGlassOtaUpdate(this.hardwareInfo).subscribe(new Consumer() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda20
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m104x6511ed89(str2, str, (GlassOtaUpdateBean) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda21
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m105x9edc8f68((Throwable) obj);
                }
            }));
        }
    }

    /* JADX INFO: renamed from: lambda$parserGlassVersion$23$com-aivox-app-activity-DeviceConnectedActivity */
    /* synthetic */ void m104x6511ed89(String str, String str2, final GlassOtaUpdateBean glassOtaUpdateBean) throws Exception {
        final int i;
        if (VersionUtil.hasNewVersion(str, glassOtaUpdateBean.getWifiVersion())) {
            this.mBinding.stvWifiNew.setMsg(glassOtaUpdateBean.getWifiVersion());
            i = 2;
        } else {
            this.mBinding.stvWifiNew.setVisibility(8);
            i = 0;
        }
        if (VersionUtil.hasNewVersion(str2, glassOtaUpdateBean.getBtVersion())) {
            i++;
            this.mBinding.stvFirmwareNew.setMsg(glassOtaUpdateBean.getBtVersion());
        } else {
            this.mBinding.stvFirmwareNew.setVisibility(8);
        }
        if (i > 0) {
            this.mBinding.llGlassNew.setVisibility(0);
            this.mBinding.tvAvailableVersion.setVisibility(0);
            this.mBinding.btnUpdate.setVisibility(0);
            this.mBinding.btnUpdate.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.DeviceConnectedActivity$$ExternalSyntheticLambda11
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m103x2b474baa(i, glassOtaUpdateBean, view2);
                }
            });
            return;
        }
        if (this.mByUserCheck) {
            ToastUtil.showShort(Integer.valueOf(C0874R.string.on_the_latest_version));
        }
    }

    /* JADX INFO: renamed from: lambda$parserGlassVersion$22$com-aivox-app-activity-DeviceConnectedActivity */
    /* synthetic */ void m103x2b474baa(int i, GlassOtaUpdateBean glassOtaUpdateBean, View view2) {
        Intent intent;
        if (!((WifiManager) getSystemService("wifi")).isWifiEnabled()) {
            Toast.makeText(this.context, C0874R.string.enable_wifi_hint, 1).show();
            if (Build.VERSION.SDK_INT >= 29) {
                intent = new Intent("android.settings.panel.action.WIFI");
            } else {
                intent = new Intent("android.settings.WIFI_SETTINGS");
            }
            intent.addFlags(268435456);
            startActivity(intent);
            return;
        }
        if (this.glassBattery < 50) {
            ToastUtil.showLong(Integer.valueOf(C0874R.string.ota_low_battery_prompt));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("updateCount", i);
        bundle.putString("btUrl", glassOtaUpdateBean.getBtUrl());
        bundle.putString("wifiUrl", glassOtaUpdateBean.getWifiUrl());
        finish();
        ARouterUtils.startWithActivity(this, MainAction.DEVICE_UPDATE, bundle);
    }

    /* JADX INFO: renamed from: lambda$parserGlassVersion$24$com-aivox-app-activity-DeviceConnectedActivity */
    /* synthetic */ void m105x9edc8f68(Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        }
    }

    private void switchNcMode(int i, boolean z) {
        if (z && !BleBtService.getInstance().isGlass()) {
            SendManager.getInstance().sendSppData(Constant.CmdUpHead + MyEnum.DEVICE_VOICE_MODE.getVoiceMode(i, false).code);
        }
        this.mBinding.tvNcNormal.setTextColor(this.context.getColor(i == MyEnum.DEVICE_VOICE_MODE.NORMAL.index.intValue() ? C0874R.color.txt_color_primary : C0874R.color.txt_color_tertiary));
        this.mBinding.tvNcOpen.setTextColor(this.context.getColor(i == MyEnum.DEVICE_VOICE_MODE.DENOISE.index.intValue() ? C0874R.color.txt_color_primary : C0874R.color.txt_color_tertiary));
        this.mBinding.tvNcTrans.setTextColor(this.context.getColor(i == MyEnum.DEVICE_VOICE_MODE.AMBIENT.index.intValue() ? C0874R.color.txt_color_primary : C0874R.color.txt_color_tertiary));
        this.mBinding.tvNcNormal.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, getResources().getDrawable(i == MyEnum.DEVICE_VOICE_MODE.NORMAL.index.intValue() ? C1034R.drawable.ic_nc_normal_selected : C1034R.drawable.ic_nc_normal), (Drawable) null, (Drawable) null);
        this.mBinding.tvNcOpen.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, getResources().getDrawable(i == MyEnum.DEVICE_VOICE_MODE.DENOISE.index.intValue() ? C1034R.drawable.ic_nc_denoise_selected : C1034R.drawable.ic_nc_denoise), (Drawable) null, (Drawable) null);
        this.mBinding.tvNcTrans.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, getResources().getDrawable(i == MyEnum.DEVICE_VOICE_MODE.AMBIENT.index.intValue() ? C1034R.drawable.ic_nc_ambient_selected : C1034R.drawable.ic_nc_ambient), (Drawable) null, (Drawable) null);
        this.mBinding.tvNcNormal.setBackgroundResource(i == MyEnum.DEVICE_VOICE_MODE.NORMAL.index.intValue() ? C1034R.drawable.bg_white_c8 : 0);
        this.mBinding.tvNcOpen.setBackgroundResource(i == MyEnum.DEVICE_VOICE_MODE.DENOISE.index.intValue() ? C1034R.drawable.bg_white_c8 : 0);
        this.mBinding.tvNcTrans.setBackgroundResource(i == MyEnum.DEVICE_VOICE_MODE.AMBIENT.index.intValue() ? C1034R.drawable.bg_white_c8 : 0);
    }
}
