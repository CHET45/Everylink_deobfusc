package com.aivox.account.activity;

import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.aivox.account.C0707R;
import com.aivox.account.databinding.ActivitySmsCodeBinding;
import com.aivox.base.C0874R;
import com.aivox.base.app.AppManager;
import com.aivox.base.common.Constant;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.SPUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.ApiService;
import com.aivox.common.http.AuthService;
import com.aivox.common.http.RetrofitServiceManager;
import com.aivox.common.model.LoginBean;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.BaseUtil;
import com.aivox.common.util.CodeCountDownManager;
import com.blankj.utilcode.util.KeyboardUtils;
import com.example.gjylibrary.GjySerialnumberLayout;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class SmsCodeActivity extends BaseFragmentActivity implements CodeCountDownManager.CountDownListener {
    private ActivitySmsCodeBinding mBinding;
    private CodeCountDownManager mCountDown;
    private String mPhoneNumber;

    static /* synthetic */ void lambda$sendSmsCode$3(Object obj) throws Exception {
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivitySmsCodeBinding) DataBindingUtil.setContentView(this, C0707R.layout.activity_sms_code);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mPhoneNumber = extras.getString(Constant.KEY_PHONE);
        }
        this.mCountDown = CodeCountDownManager.getInstance();
        this.mBinding.tvPhoneNum.setText(getString(C0874R.string.login_loginbycode_title2, new Object[]{maskPhoneNumber(this.mPhoneNumber)}));
        this.mBinding.etCode.setOnInputListener(new GjySerialnumberLayout.OnInputListener() { // from class: com.aivox.account.activity.SmsCodeActivity$$ExternalSyntheticLambda5
            @Override // com.example.gjylibrary.GjySerialnumberLayout.OnInputListener
            public final void onSucess(String str) {
                this.f$0.m2048lambda$initView$1$comaivoxaccountactivitySmsCodeActivity(str);
            }
        });
        this.mBinding.etCode.postDelayed(new Runnable() { // from class: com.aivox.account.activity.SmsCodeActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                KeyboardUtils.showSoftInput();
            }
        }, 200L);
        this.mBinding.tvCountDown.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.activity.SmsCodeActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2049lambda$initView$2$comaivoxaccountactivitySmsCodeActivity(view2);
            }
        });
        sendSmsCode();
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-account-activity-SmsCodeActivity, reason: not valid java name */
    /* synthetic */ void m2048lambda$initView$1$comaivoxaccountactivitySmsCodeActivity(String str) {
        if (str.length() == 6) {
            DialogUtils.showLoadingDialog(this.context);
            new AuthService(this.context).loginBySmS(this.mPhoneNumber, str).doFinally(new Action() { // from class: com.aivox.account.activity.SmsCodeActivity$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Action
                public final void run() {
                    DialogUtils.hideLoadingDialog();
                }
            }).subscribe(new Consumer() { // from class: com.aivox.account.activity.SmsCodeActivity$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    this.f$0.saveInfoAndJump((LoginBean) obj);
                }
            }, new Consumer() { // from class: com.aivox.account.activity.SmsCodeActivity$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2047lambda$initView$0$comaivoxaccountactivitySmsCodeActivity((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-account-activity-SmsCodeActivity, reason: not valid java name */
    /* synthetic */ void m2047lambda$initView$0$comaivoxaccountactivitySmsCodeActivity(Throwable th) throws Exception {
        AppUtils.checkHttpCode(this.context);
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-account-activity-SmsCodeActivity, reason: not valid java name */
    /* synthetic */ void m2049lambda$initView$2$comaivoxaccountactivitySmsCodeActivity(View view2) {
        sendSmsCode();
    }

    private void sendSmsCode() {
        this.mCountDown.registerAndStart(this);
        if (this.mCountDown.isTicking()) {
            return;
        }
        new AuthService(this.context).sendPhoneSmsCode(this.mPhoneNumber, 1).subscribe(new Consumer() { // from class: com.aivox.account.activity.SmsCodeActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                SmsCodeActivity.lambda$sendSmsCode$3(obj);
            }
        }, new Consumer() { // from class: com.aivox.account.activity.SmsCodeActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2050lambda$sendSmsCode$4$comaivoxaccountactivitySmsCodeActivity((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$sendSmsCode$4$com-aivox-account-activity-SmsCodeActivity, reason: not valid java name */
    /* synthetic */ void m2050lambda$sendSmsCode$4$comaivoxaccountactivitySmsCodeActivity(Throwable th) throws Exception {
        AppUtils.checkHttpCode(this.context);
        this.mCountDown.stopTicking();
    }

    private String maskPhoneNumber(String str) {
        return (str == null || str.length() != 11) ? str : str.substring(0, 3) + "****" + str.substring(7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveInfoAndJump(LoginBean loginBean) {
        DialogUtils.hideLoadingDialog();
        new SQLiteDataBaseManager(this).insertUserInfo(loginBean.getUserinfo());
        SPUtil.put(SPUtil.TOKEN, loginBean.getToken());
        SPUtil.put(SPUtil.TOKEN_EXPIRE, loginBean.getTokenExpire());
        SPUtil.put(SPUtil.REFRESH_TOKEN, loginBean.getRefreshToken());
        SPUtil.put(SPUtil.USER_ID, loginBean.getUserinfo().getUuid());
        SPUtil.put(SPUtil.LOGIN_ACCOUNT, loginBean.getUserinfo().getPhone());
        SPUtil.put(SPUtil.NICKNAME, loginBean.getUserinfo().getNickname());
        BaseUtil.setApiService((ApiService) new RetrofitServiceManager().create(ApiService.class));
        ARouterUtils.startWithActivity(this, MainAction.MAIN);
        AppManager.getAppManager().finishAllActivityExcept(ARouterUtils.getClass(MainAction.MAIN));
    }

    @Override // com.aivox.common.util.CodeCountDownManager.CountDownListener
    public void onTick(long j) {
        this.mBinding.tvCountDown.setText(String.format(getString(C0874R.string.account_resend_countdown), Long.valueOf(j)));
        this.mBinding.tvCountDown.setTextColor(getResources().getColor(C0874R.color.txt_color_tertiary));
    }

    @Override // com.aivox.common.util.CodeCountDownManager.CountDownListener
    public void onFinish() {
        this.mBinding.tvCountDown.setText(C0874R.string.account_resend);
        this.mBinding.tvCountDown.setTextColor(getResources().getColor(C0874R.color.txt_color_highlight));
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mCountDown.unRegister(this);
    }
}
