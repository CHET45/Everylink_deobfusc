package com.aivox.account.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.aivox.account.C0707R;
import com.aivox.account.databinding.ActivityPwdInputBinding;
import com.aivox.base.C0874R;
import com.aivox.base.app.AppManager;
import com.aivox.base.common.Constant;
import com.aivox.base.http.HttpException;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.AccountAction;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.RegexUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.ApiService;
import com.aivox.common.http.AuthService;
import com.aivox.common.http.RetrofitServiceManager;
import com.aivox.common.http.UserService;
import com.aivox.common.model.LoginBean;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.BaseUtil;
import com.aivox.common_ui.PwdForgetView;
import com.blankj.utilcode.util.KeyboardUtils;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class PwdInputActivity extends BaseFragmentActivity {
    public static final int PWD_TYPE_CREATE = 0;
    public static final int PWD_TYPE_FORGET = 3;
    public static final int PWD_TYPE_LOGIN = 1;
    public static final int PWD_TYPE_RESET = 2;
    private ActivityPwdInputBinding mBinding;
    private boolean mIsPhone;
    private LoginBean mLoginBean;
    private boolean mReceiveEmail;
    private int mPwdInputType = 0;
    private String mAccount = "";
    private String mPwd = "";
    private String mPwdConfirm = "";

    static /* synthetic */ void lambda$setReceiveEmailAds$3(Object obj) throws Exception {
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityPwdInputBinding) DataBindingUtil.setContentView(this, C0707R.layout.activity_pwd_input);
        Bundle extras = getIntent().getExtras();
        String string = extras.getString("email");
        this.mAccount = string;
        if (BaseStringUtil.isEmpty(string)) {
            this.mAccount = extras.getString(Constant.KEY_PHONE);
            this.mIsPhone = true;
        } else {
            this.mIsPhone = false;
        }
        this.mPwdInputType = extras.getInt(Constant.KEY_PWD_INPUT_TYPE);
        this.mReceiveEmail = extras.getBoolean(Constant.KEY_FLAG);
        if (this.mPwdInputType == 1) {
            this.mBinding.etPwdConfirm.setVisibility(8);
            this.mBinding.tvCodeLogin.setVisibility(0);
        } else {
            this.mBinding.titleView.setTitle(C0874R.string.resetpwd_title);
            this.mBinding.tvCodeLogin.setVisibility(8);
        }
        this.mBinding.tvCodeLogin.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.activity.PwdInputActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2040lambda$initView$0$comaivoxaccountactivityPwdInputActivity(view2);
            }
        });
        this.mBinding.etPwd.addTextChangedListener(new TextWatcher() { // from class: com.aivox.account.activity.PwdInputActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                PwdInputActivity.this.mPwd = editable.toString();
                PwdInputActivity.this.refreshBtnStatusAndNotice(true);
            }
        });
        this.mBinding.etPwdConfirm.addTextChangedListener(new TextWatcher() { // from class: com.aivox.account.activity.PwdInputActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                PwdInputActivity.this.mPwdConfirm = editable.toString();
                PwdInputActivity.this.refreshBtnStatusAndNotice(false);
            }
        });
        this.mBinding.btnLogin.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.activity.PwdInputActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2041lambda$initView$1$comaivoxaccountactivityPwdInputActivity(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-account-activity-PwdInputActivity, reason: not valid java name */
    /* synthetic */ void m2040lambda$initView$0$comaivoxaccountactivityPwdInputActivity(View view2) {
        Bundle bundle = new Bundle();
        if (this.mIsPhone) {
            bundle.putString(Constant.KEY_PHONE, this.mAccount);
            ARouterUtils.startWithContext(this.context, AccountAction.SMS_CODE_INPUT, bundle);
        } else {
            bundle.putInt(Constant.KEY_CODE_INPUT_TYPE, 0);
            bundle.putString("email", this.mAccount);
            ARouterUtils.startWithActivity(this, AccountAction.CODE_INPUT, bundle);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-account-activity-PwdInputActivity, reason: not valid java name */
    /* synthetic */ void m2041lambda$initView$1$comaivoxaccountactivityPwdInputActivity(View view2) {
        if (this.mPwdInputType == 1) {
            loginByPwd();
        } else {
            setPwd();
        }
    }

    private void setReceiveEmailAds() {
        new UserService(this).saveNotifyEmail(this.mAccount).doFinally(new Action() { // from class: com.aivox.account.activity.PwdInputActivity$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m64x65166774();
            }
        }).subscribe(new Consumer() { // from class: com.aivox.account.activity.PwdInputActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                PwdInputActivity.lambda$setReceiveEmailAds$3(obj);
            }
        }, new Consumer() { // from class: com.aivox.account.activity.PwdInputActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ((Throwable) obj).printStackTrace();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setReceiveEmailAds$2$com-aivox-account-activity-PwdInputActivity */
    /* synthetic */ void m64x65166774() throws Exception {
        saveInfoAndJump(this.mLoginBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshBtnStatusAndNotice(boolean z) {
        boolean zIsPwd = RegexUtil.isPwd(z ? this.mPwd : this.mPwdConfirm);
        boolean zEquals = this.mPwd.equals(this.mPwdConfirm);
        if (!z) {
            if (!zEquals) {
                updateUiInfo(-1, 2, C0874R.string.account_pwd_set_confirm_correct, C0874R.color.txt_color_warning, false);
                return;
            } else if (zIsPwd) {
                updateUiInfo(-1, 1, C0874R.string.account_pwd_set_match, C0874R.color.txt_color_primary, true);
                return;
            } else {
                updateUiInfo(-1, 0, C0874R.string.account_pwd_set_notice, C0874R.color.txt_color_primary, false);
                return;
            }
        }
        if (this.mPwdInputType == 1) {
            int i = zIsPwd ? -1 : C0874R.string.account_pwd_set_notice;
            updateUiInfo(zIsPwd ? 1 : 0, -1, i, C0874R.color.txt_color_primary, zIsPwd);
        } else {
            if (zEquals) {
                if (zIsPwd) {
                    updateUiInfo(1, 1, C0874R.string.account_pwd_set_match, C0874R.color.txt_color_primary, true);
                    return;
                } else {
                    updateUiInfo(0, 0, C0874R.string.account_pwd_set_notice, C0874R.color.txt_color_primary, false);
                    return;
                }
            }
            if (zIsPwd) {
                updateUiInfo(1, 2, C0874R.string.account_pwd_set_confirm_correct, C0874R.color.txt_color_warning, false);
            } else {
                updateUiInfo(0, 0, C0874R.string.account_pwd_set_notice, C0874R.color.txt_color_primary, false);
            }
        }
    }

    private void updateUiInfo(int i, int i2, int i3, int i4, boolean z) {
        if (i != -1) {
            this.mBinding.etPwd.changeStatus(i);
        }
        if (i2 != -1) {
            this.mBinding.etPwdConfirm.changeStatus(i2);
        }
        this.mBinding.tvNotice.setText(i3 == -1 ? "" : getString(i3));
        this.mBinding.tvNotice.setTextColor(getColor(i4));
        this.mBinding.btnLogin.setEnabled(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPwd() {
        KeyboardUtils.hideSoftInput(this);
        if (this.mPwdInputType == 0) {
            new UserService(this).createPwd(this.mPwd).subscribe(new Consumer() { // from class: com.aivox.account.activity.PwdInputActivity$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2044lambda$setPwd$4$comaivoxaccountactivityPwdInputActivity(obj);
                }
            }, new Consumer() { // from class: com.aivox.account.activity.PwdInputActivity$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2045lambda$setPwd$5$comaivoxaccountactivityPwdInputActivity((Throwable) obj);
                }
            });
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_PWD_INPUT_TYPE, this.mPwdInputType);
        bundle.putInt(Constant.KEY_CODE_INPUT_TYPE, this.mPwdInputType == 2 ? 1 : 5);
        if (this.mIsPhone) {
            bundle.putString(Constant.KEY_PHONE, this.mAccount);
        } else {
            bundle.putString("email", this.mAccount);
        }
        bundle.putString(Constant.KEY_PWD, this.mPwd);
        ARouterUtils.startWithContext(this.context, AccountAction.CODE_INPUT, bundle);
    }

    /* JADX INFO: renamed from: lambda$setPwd$4$com-aivox-account-activity-PwdInputActivity, reason: not valid java name */
    /* synthetic */ void m2044lambda$setPwd$4$comaivoxaccountactivityPwdInputActivity(Object obj) throws Exception {
        this.mBinding.btnLogin.stopLoading();
        ARouterUtils.startWithActivity(this, MainAction.MAIN);
        AppManager.getAppManager().finishAllActivityExcept(ARouterUtils.getClass(MainAction.MAIN));
    }

    /* JADX INFO: renamed from: lambda$setPwd$5$com-aivox-account-activity-PwdInputActivity, reason: not valid java name */
    /* synthetic */ void m2045lambda$setPwd$5$comaivoxaccountactivityPwdInputActivity(Throwable th) throws Exception {
        this.mBinding.btnLogin.stopLoading();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.account.activity.PwdInputActivity$$ExternalSyntheticLambda9
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.setPwd();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loginByPwd() {
        Observable<LoginBean> observableLoginByPwd;
        this.mBinding.btnLogin.startLoading();
        KeyboardUtils.hideSoftInput(this);
        AuthService authService = new AuthService(this);
        if (this.mIsPhone) {
            observableLoginByPwd = authService.loginByPwdPhone(this.mAccount, this.mPwd);
        } else {
            observableLoginByPwd = authService.loginByPwd(this.mAccount, this.mPwd);
        }
        observableLoginByPwd.subscribe(new Consumer() { // from class: com.aivox.account.activity.PwdInputActivity$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2042lambda$loginByPwd$6$comaivoxaccountactivityPwdInputActivity((LoginBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.account.activity.PwdInputActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2043lambda$loginByPwd$7$comaivoxaccountactivityPwdInputActivity((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$loginByPwd$6$com-aivox-account-activity-PwdInputActivity, reason: not valid java name */
    /* synthetic */ void m2042lambda$loginByPwd$6$comaivoxaccountactivityPwdInputActivity(LoginBean loginBean) throws Exception {
        if (this.mReceiveEmail) {
            this.mLoginBean = loginBean;
            SPUtil.put(SPUtil.TOKEN, loginBean.getToken());
            SPUtil.put(SPUtil.TOKEN_EXPIRE, loginBean.getTokenExpire());
            SPUtil.put(SPUtil.REFRESH_TOKEN, loginBean.getRefreshToken());
            setReceiveEmailAds();
            return;
        }
        saveInfoAndJump(loginBean);
    }

    /* JADX INFO: renamed from: lambda$loginByPwd$7$com-aivox-account-activity-PwdInputActivity, reason: not valid java name */
    /* synthetic */ void m2043lambda$loginByPwd$7$comaivoxaccountactivityPwdInputActivity(Throwable th) throws Exception {
        this.mBinding.btnLogin.stopLoading();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.account.activity.PwdInputActivity$$ExternalSyntheticLambda0
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.loginByPwd();
                }
            });
        }
    }

    private void showForgetDialog() {
        KeyboardUtils.hideSoftInput(this);
        DialogUtils.showBottomSheetDialog(this.context, new PwdForgetView(this.context, this.mAccount, new PwdForgetView.BtnTapListener() { // from class: com.aivox.account.activity.PwdInputActivity.3
            @Override // com.aivox.common_ui.PwdForgetView.BtnTapListener
            public void onSendTap() {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.KEY_PWD_INPUT_TYPE, 3);
                bundle.putString("email", PwdInputActivity.this.mAccount);
                ARouterUtils.startWithContext(PwdInputActivity.this.context, AccountAction.PWD_INPUT, bundle);
                PwdInputActivity.this.finish();
            }

            @Override // com.aivox.common_ui.PwdForgetView.BtnTapListener
            public void onRetryTap() {
                PwdInputActivity.this.mBinding.etPwd.setText("");
            }
        }));
    }

    private void saveInfoAndJump(LoginBean loginBean) {
        new SQLiteDataBaseManager(this).insertUserInfo(loginBean.getUserinfo());
        SPUtil.put(SPUtil.TOKEN, loginBean.getToken());
        SPUtil.put(SPUtil.TOKEN_EXPIRE, loginBean.getTokenExpire());
        SPUtil.put(SPUtil.REFRESH_TOKEN, loginBean.getRefreshToken());
        SPUtil.put(SPUtil.USER_ID, loginBean.getUserinfo().getUuid());
        SPUtil.put(SPUtil.LOGIN_ACCOUNT, this.mAccount);
        SPUtil.put(SPUtil.NICKNAME, loginBean.getUserinfo().getNickname());
        BaseUtil.setApiService((ApiService) new RetrofitServiceManager().create(ApiService.class));
        ARouterUtils.startWithActivity(this, MainAction.MAIN);
        AppManager.getAppManager().finishAllActivityExcept(ARouterUtils.getClass(MainAction.MAIN));
    }
}
