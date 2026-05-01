package com.aivox.account.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import androidx.databinding.DataBindingUtil;
import com.aivox.account.C0707R;
import com.aivox.account.databinding.ActivityCodeInputBinding;
import com.aivox.base.C0874R;
import com.aivox.base.app.AppManager;
import com.aivox.base.common.BaseDataHandle;
import com.aivox.base.common.Constant;
import com.aivox.base.http.HttpException;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
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
import com.aivox.common.util.CodeCountDownManager;
import com.blankj.utilcode.util.KeyboardUtils;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class CodeInputActivity extends BaseFragmentActivity implements CodeCountDownManager.CountDownListener {
    public static final int CODE_TYPE_DELETE_ACCOUNT = 4;
    public static final int CODE_TYPE_FORGET_PWD = 5;
    public static final int CODE_TYPE_REGISTER = 0;
    public static final int CODE_TYPE_RESET_EMAIL = 3;
    public static final int CODE_TYPE_RESET_PWD = 1;
    private String mAccount;
    private ActivityCodeInputBinding mBinding;
    private int mCodeInputType;
    private CodeCountDownManager mCountDownManager;
    private boolean mIsPhone;
    private String mPwd;

    static /* synthetic */ void lambda$sendValidationCode$0(Object obj) throws Exception {
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivityCodeInputBinding activityCodeInputBinding = (ActivityCodeInputBinding) DataBindingUtil.setContentView(this, C0707R.layout.activity_code_input);
        this.mBinding = activityCodeInputBinding;
        KeyboardUtils.showSoftInput(activityCodeInputBinding.etCode);
        this.mCountDownManager = CodeCountDownManager.getInstance();
        Bundle extras = getIntent().getExtras();
        String string = extras.getString("email");
        this.mAccount = string;
        if (BaseStringUtil.isEmpty(string)) {
            this.mIsPhone = true;
            this.mAccount = extras.getString(Constant.KEY_PHONE);
        } else {
            this.mIsPhone = false;
        }
        this.mPwd = extras.getString(Constant.KEY_PWD);
        this.mCodeInputType = extras.getInt(Constant.KEY_CODE_INPUT_TYPE);
        this.mBinding.tvSubMsg.setText(this.mAccount);
        this.mBinding.etCode.addTextChangedListener(new TextWatcher() { // from class: com.aivox.account.activity.CodeInputActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                CodeInputActivity.this.mBinding.tvFakeHint.setVisibility(TextUtils.isEmpty(editable) ? 0 : 4);
                if (editable.length() > 0) {
                    CodeInputActivity.this.mBinding.tvNotice.setVisibility(4);
                }
                if (editable.length() < 6) {
                    if (CodeInputActivity.this.mCountDownManager.isTicking()) {
                        CodeInputActivity.this.mBinding.btnNext.setEnabled(false);
                        return;
                    } else {
                        CodeInputActivity.this.mBinding.btnNext.setEnabled(true);
                        CodeInputActivity.this.mBinding.btnNext.setText(CodeInputActivity.this.getString(C0874R.string.resend));
                        return;
                    }
                }
                CodeInputActivity.this.mBinding.btnNext.setEnabled(true);
                CodeInputActivity.this.mBinding.btnNext.setText(CodeInputActivity.this.getString(C0874R.string.do_continue));
            }
        });
        this.mBinding.btnNext.setOnClickListener(new ViewOnClickListenerC07092());
        sendValidationCode();
    }

    /* JADX INFO: renamed from: com.aivox.account.activity.CodeInputActivity$2 */
    class ViewOnClickListenerC07092 implements View.OnClickListener {
        ViewOnClickListenerC07092() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            if (CodeInputActivity.this.mCountDownManager.isTicking() || CodeInputActivity.this.mBinding.etCode.getText().length() >= 6) {
                CodeInputActivity.this.verifyCode();
            } else if (CodeInputActivity.this.mIsPhone) {
                CodeInputActivity.this.sendValidationCode();
            } else {
                DialogUtils.showDialogWithBtnIds(CodeInputActivity.this.context, "", Integer.valueOf(C0874R.string.resend_code_notice), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.account.activity.CodeInputActivity$2$$ExternalSyntheticLambda0
                    @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                    public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                        this.f$0.m2027lambda$onClick$0$comaivoxaccountactivityCodeInputActivity$2(context, dialogBuilder, dialog, i, i2, editText);
                    }
                }, true, true, C0874R.string.cancel, C0874R.string.do_continue);
            }
        }

        /* JADX INFO: renamed from: lambda$onClick$0$com-aivox-account-activity-CodeInputActivity$2, reason: not valid java name */
        /* synthetic */ void m2027lambda$onClick$0$comaivoxaccountactivityCodeInputActivity$2(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            CodeInputActivity.this.sendValidationCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendValidationCode() {
        Observable<Object> observableSendSmsCode;
        this.mCountDownManager.registerAndStart(this);
        int i = this.mCodeInputType;
        int i2 = 1;
        if (i != 0) {
            if (i == 1) {
                i2 = 4;
            } else if (i != 3) {
                i2 = 5;
                if (i != 4) {
                    i2 = i != 5 ? 0 : 6;
                }
            } else {
                i2 = 2;
            }
        }
        if (this.mCountDownManager.isTicking()) {
            return;
        }
        AuthService authService = new AuthService(this);
        if (this.mIsPhone) {
            observableSendSmsCode = authService.sendPhoneSmsCode(this.mAccount, i2);
        } else {
            observableSendSmsCode = authService.sendSmsCode(this.mAccount, i2);
        }
        observableSendSmsCode.subscribe(new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                CodeInputActivity.lambda$sendValidationCode$0(obj);
            }
        }, new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m50xabc3ac9c((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$sendValidationCode$1$com-aivox-account-activity-CodeInputActivity */
    /* synthetic */ void m50xabc3ac9c(Throwable th) throws Exception {
        this.mCountDownManager.stopTicking();
        onFinish();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda5
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.sendValidationCode();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyCode() {
        this.mBinding.btnNext.startLoading();
        String string = this.mBinding.etCode.getText().toString();
        int i = this.mCodeInputType;
        if (i == 1) {
            new UserService(this).changePwd(this.mPwd, string).subscribe(new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda12
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2020lambda$verifyCode$3$comaivoxaccountactivityCodeInputActivity(obj);
                }
            }, new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda13
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2021lambda$verifyCode$4$comaivoxaccountactivityCodeInputActivity((Throwable) obj);
                }
            });
            return;
        }
        if (i == 3) {
            new UserService(this).changeEmail(this.mAccount, string).subscribe(new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda16
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2026lambda$verifyCode$9$comaivoxaccountactivityCodeInputActivity(obj);
                }
            }, new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda17
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m51xec1ba7b4((Throwable) obj);
                }
            });
            return;
        }
        if (i == 4) {
            new AuthService(this).logoff(string).subscribe(new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda18
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m52xa5933553(obj);
                }
            }, new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda19
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m53x5f0ac2f2((Throwable) obj);
                }
            });
        } else if (i == 5) {
            new UserService(this).forgetPwd(this.mPwd, string, this.mAccount).subscribe(new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda14
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2023lambda$verifyCode$6$comaivoxaccountactivityCodeInputActivity(obj);
                }
            }, new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda15
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2024lambda$verifyCode$7$comaivoxaccountactivityCodeInputActivity((Throwable) obj);
                }
            });
        } else {
            new AuthService(this).loginByCode(this.mAccount, this.mBinding.etCode.getText().toString()).subscribe(new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m54x18825091((LoginBean) obj);
                }
            }, new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m55xd1f9de30((Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$verifyCode$3$com-aivox-account-activity-CodeInputActivity, reason: not valid java name */
    /* synthetic */ void m2020lambda$verifyCode$3$comaivoxaccountactivityCodeInputActivity(Object obj) throws Exception {
        DialogUtils.showDialogWithDefBtnAndSingleListener(this.context, "", Integer.valueOf(C0874R.string.account_pwd_reset_success), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda0
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                AppUtils.logout(context);
            }
        }, false, false);
    }

    /* JADX INFO: renamed from: lambda$verifyCode$4$com-aivox-account-activity-CodeInputActivity, reason: not valid java name */
    /* synthetic */ void m2021lambda$verifyCode$4$comaivoxaccountactivityCodeInputActivity(Throwable th) throws Exception {
        this.mBinding.btnNext.stopLoading();
        this.mBinding.etCode.setText("");
        if (BaseDataHandle.getIns().getCode() == Constant.SeverErrorCode.VERIFICATION_CODE_WRONG.code) {
            this.mBinding.tvNotice.setVisibility(0);
        } else if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new CodeInputActivity$$ExternalSyntheticLambda3(this));
        }
    }

    /* JADX INFO: renamed from: lambda$verifyCode$6$com-aivox-account-activity-CodeInputActivity, reason: not valid java name */
    /* synthetic */ void m2023lambda$verifyCode$6$comaivoxaccountactivityCodeInputActivity(Object obj) throws Exception {
        DialogUtils.showDialogWithDefBtnAndSingleListener(this.context, "", Integer.valueOf(C0874R.string.account_pwd_reset_success), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda10
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m2022lambda$verifyCode$5$comaivoxaccountactivityCodeInputActivity(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, false, false);
    }

    /* JADX INFO: renamed from: lambda$verifyCode$5$com-aivox-account-activity-CodeInputActivity, reason: not valid java name */
    /* synthetic */ void m2022lambda$verifyCode$5$comaivoxaccountactivityCodeInputActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        loginByPwd();
    }

    /* JADX INFO: renamed from: lambda$verifyCode$7$com-aivox-account-activity-CodeInputActivity, reason: not valid java name */
    /* synthetic */ void m2024lambda$verifyCode$7$comaivoxaccountactivityCodeInputActivity(Throwable th) throws Exception {
        this.mBinding.btnNext.stopLoading();
        this.mBinding.etCode.setText("");
        if (BaseDataHandle.getIns().getCode() == Constant.SeverErrorCode.VERIFICATION_CODE_WRONG.code) {
            this.mBinding.tvNotice.setVisibility(0);
        } else if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new CodeInputActivity$$ExternalSyntheticLambda3(this));
        }
    }

    /* JADX INFO: renamed from: lambda$verifyCode$9$com-aivox-account-activity-CodeInputActivity, reason: not valid java name */
    /* synthetic */ void m2026lambda$verifyCode$9$comaivoxaccountactivityCodeInputActivity(Object obj) throws Exception {
        DialogUtils.showDialogWithDefBtnAndSingleListener(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.email_change_success), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda11
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                this.f$0.m2025lambda$verifyCode$8$comaivoxaccountactivityCodeInputActivity(context, dialogBuilder, dialog, i, i2, editText);
            }
        }, false, false);
    }

    /* JADX INFO: renamed from: lambda$verifyCode$8$com-aivox-account-activity-CodeInputActivity, reason: not valid java name */
    /* synthetic */ void m2025lambda$verifyCode$8$comaivoxaccountactivityCodeInputActivity(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        SPUtil.put(SPUtil.LOGIN_ACCOUNT, this.mAccount);
        ARouterUtils.startWithActivity(this, MainAction.MAIN);
        AppManager.getAppManager().finishAllActivityExcept(ARouterUtils.getClass(MainAction.MAIN));
    }

    /* JADX INFO: renamed from: lambda$verifyCode$10$com-aivox-account-activity-CodeInputActivity */
    /* synthetic */ void m51xec1ba7b4(Throwable th) throws Exception {
        this.mBinding.btnNext.stopLoading();
        this.mBinding.etCode.setText("");
        if (BaseDataHandle.getIns().getCode() == Constant.SeverErrorCode.VERIFICATION_CODE_WRONG.code) {
            this.mBinding.tvNotice.setVisibility(0);
        } else if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new CodeInputActivity$$ExternalSyntheticLambda3(this));
        }
    }

    /* JADX INFO: renamed from: lambda$verifyCode$11$com-aivox-account-activity-CodeInputActivity */
    /* synthetic */ void m52xa5933553(Object obj) throws Exception {
        AppUtils.logout(this);
    }

    /* JADX INFO: renamed from: lambda$verifyCode$12$com-aivox-account-activity-CodeInputActivity */
    /* synthetic */ void m53x5f0ac2f2(Throwable th) throws Exception {
        this.mBinding.btnNext.stopLoading();
        this.mBinding.etCode.setText("");
        if (BaseDataHandle.getIns().getCode() == Constant.SeverErrorCode.VERIFICATION_CODE_WRONG.code) {
            this.mBinding.tvNotice.setVisibility(0);
        } else if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new CodeInputActivity$$ExternalSyntheticLambda3(this));
        }
    }

    /* JADX INFO: renamed from: lambda$verifyCode$14$com-aivox-account-activity-CodeInputActivity */
    /* synthetic */ void m55xd1f9de30(Throwable th) throws Exception {
        this.mBinding.btnNext.stopLoading();
        this.mBinding.tvNotice.setTextColor(getColor(C0874R.color.txt_color_warning));
        if (BaseDataHandle.getIns().getCode() == Constant.SeverErrorCode.UNABLE_TO_REGISTER.code) {
            this.mBinding.tvNotice.setText(C0874R.string.account_code_set_unable);
        }
        if (BaseDataHandle.getIns().getCode() == Constant.SeverErrorCode.VERIFICATION_CODE_WRONG.code) {
            this.mBinding.tvNotice.setText(C0874R.string.account_code_set_notice);
        }
        this.mBinding.tvNotice.setVisibility(0);
        this.mBinding.etCode.setText("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loginByPwd() {
        Observable<LoginBean> observableLoginByPwd;
        AuthService authService = new AuthService(this);
        if (this.mIsPhone) {
            observableLoginByPwd = authService.loginByPwdPhone(this.mAccount, this.mPwd);
        } else {
            observableLoginByPwd = authService.loginByPwd(this.mAccount, this.mPwd);
        }
        observableLoginByPwd.subscribe(new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                this.f$0.m54x18825091((LoginBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m49x5bc6d146((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$loginByPwd$15$com-aivox-account-activity-CodeInputActivity */
    /* synthetic */ void m49x5bc6d146(Throwable th) throws Exception {
        this.mBinding.btnNext.stopLoading();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.account.activity.CodeInputActivity$$ExternalSyntheticLambda4
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.loginByPwd();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: saveInfoAndJump, reason: merged with bridge method [inline-methods] */
    public void m54x18825091(LoginBean loginBean) {
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

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mCountDownManager.unRegister(this);
    }

    @Override // com.aivox.common.util.CodeCountDownManager.CountDownListener
    public void onTick(long j) {
        if (this.mBinding.etCode.getText().length() < 6) {
            this.mBinding.btnNext.setText(String.format(getString(C0874R.string.account_resend_countdown), Long.valueOf(j)));
            this.mBinding.btnNext.setEnabled(false);
        }
    }

    @Override // com.aivox.common.util.CodeCountDownManager.CountDownListener
    public void onFinish() {
        if (this.mBinding.etCode.getText().length() < 6) {
            this.mBinding.btnNext.setText(getString(C0874R.string.resend));
            this.mBinding.btnNext.setEnabled(true);
        }
    }
}
