package com.aivox.account.activity;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import com.aivox.account.C0707R;
import com.aivox.account.databinding.ActivityLoginOneKeyBinding;
import com.aivox.base.C0874R;
import com.aivox.base.app.AppManager;
import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.base.common.Constant;
import com.aivox.base.http.HttpException;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.AccountAction;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.SpanUtils;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.ApiService;
import com.aivox.common.http.AuthService;
import com.aivox.common.http.RetrofitServiceManager;
import com.aivox.common.model.LoginBean;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.BaseUtil;
import com.aivox.common_ui.PolicyDialogFragment;
import com.aivox.common_ui.antishake.AntiShake;
import com.blankj.utilcode.util.KeyboardUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class OneKeyLoginActivity extends BaseFragmentActivity {
    private static final int RC_SIGN_IN = 100;
    private ActivityLoginOneKeyBinding mBinding;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean mIsLoginInProgress;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityLoginOneKeyBinding) DataBindingUtil.setContentView(this, C0707R.layout.activity_login_one_key);
        this.mGoogleSignInClient = GoogleSignIn.getClient((Activity) this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(BaseGlobalConfig.getGoogleServiceClientId()).requestEmail().build());
        this.mBinding.ivGoogleLogin.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.activity.OneKeyLoginActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2031lambda$initView$0$comaivoxaccountactivityOneKeyLoginActivity(view2);
            }
        });
        if (BaseGlobalConfig.isMainland()) {
            this.mBinding.btnLoginPhone.setVisibility(0);
            this.mBinding.btnLoginEmail.setVisibility(0);
            this.mBinding.btnLoginPhone.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.activity.OneKeyLoginActivity$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2032lambda$initView$1$comaivoxaccountactivityOneKeyLoginActivity(view2);
                }
            });
            this.mBinding.btnLoginEmail.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.activity.OneKeyLoginActivity$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2033lambda$initView$2$comaivoxaccountactivityOneKeyLoginActivity(view2);
                }
            });
        } else {
            this.mBinding.btnLogin.setVisibility(0);
            this.mBinding.btnLogin.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.account.activity.OneKeyLoginActivity$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2034lambda$initView$3$comaivoxaccountactivityOneKeyLoginActivity(view2);
                }
            });
        }
        this.mBinding.cbEmail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.aivox.account.activity.OneKeyLoginActivity$$ExternalSyntheticLambda7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m2035lambda$initView$4$comaivoxaccountactivityOneKeyLoginActivity(compoundButton, z);
            }
        });
        if (BaseGlobalConfig.getPolicyVersion().equals(SPUtil.get(SPUtil.LAST_POLICY_VERSION, "0"))) {
            AppApplication.initSDK();
        } else {
            showPolicyPopup();
        }
        setPolicy();
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-account-activity-OneKeyLoginActivity, reason: not valid java name */
    /* synthetic */ void m2031lambda$initView$0$comaivoxaccountactivityOneKeyLoginActivity(View view2) {
        startActivityForResult(this.mGoogleSignInClient.getSignInIntent(), 100);
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-account-activity-OneKeyLoginActivity, reason: not valid java name */
    /* synthetic */ void m2032lambda$initView$1$comaivoxaccountactivityOneKeyLoginActivity(View view2) {
        ARouterUtils.startWithActivity(this, AccountAction.PHONE_INPUT);
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-account-activity-OneKeyLoginActivity, reason: not valid java name */
    /* synthetic */ void m2033lambda$initView$2$comaivoxaccountactivityOneKeyLoginActivity(View view2) {
        new Bundle().putInt(Constant.KEY_EMAIL_INPUT_TYPE, 0);
        ARouterUtils.startWithActivity(this, AccountAction.EMAIL_INPUT);
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-account-activity-OneKeyLoginActivity, reason: not valid java name */
    /* synthetic */ void m2034lambda$initView$3$comaivoxaccountactivityOneKeyLoginActivity(View view2) {
        new Bundle().putInt(Constant.KEY_EMAIL_INPUT_TYPE, 0);
        ARouterUtils.startWithActivity(this, AccountAction.EMAIL_INPUT);
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-account-activity-OneKeyLoginActivity, reason: not valid java name */
    /* synthetic */ void m2035lambda$initView$4$comaivoxaccountactivityOneKeyLoginActivity(CompoundButton compoundButton, boolean z) {
        this.mBinding.btnLogin.setEnabled(z);
        this.mBinding.btnLoginPhone.setEnabled(z);
        this.mBinding.btnLoginEmail.setEnabled(z);
    }

    private void showPolicyPopup() {
        PolicyDialogFragment policyDialogFragment = new PolicyDialogFragment();
        policyDialogFragment.setDialogListener(new PolicyDialogFragment.DialogListener() { // from class: com.aivox.account.activity.OneKeyLoginActivity.1
            @Override // com.aivox.common_ui.PolicyDialogFragment.DialogListener
            public void onPrivacyPolicyClick() {
                OneKeyLoginActivity oneKeyLoginActivity = OneKeyLoginActivity.this;
                BaseAppUtils.startActivityForWeb(oneKeyLoginActivity, oneKeyLoginActivity.getString(C0874R.string.h5_path_privacy), OneKeyLoginActivity.this.getString(C0874R.string.set_privacy_policy), ARouterUtils.getClass(MainAction.WEB));
            }

            @Override // com.aivox.common_ui.PolicyDialogFragment.DialogListener
            public void onServiceAgreementClick() {
                OneKeyLoginActivity oneKeyLoginActivity = OneKeyLoginActivity.this;
                BaseAppUtils.startActivityForWeb(oneKeyLoginActivity, oneKeyLoginActivity.getString(C0874R.string.h5_path_agreement), OneKeyLoginActivity.this.getString(C0874R.string.vip_terms_of_service), ARouterUtils.getClass(MainAction.WEB));
            }

            @Override // com.aivox.common_ui.PolicyDialogFragment.DialogListener
            public void onConfirm() {
                AppApplication.initSDK();
                SPUtil.put(SPUtil.LAST_POLICY_VERSION, BaseGlobalConfig.getPolicyVersion());
            }

            @Override // com.aivox.common_ui.PolicyDialogFragment.DialogListener
            public void onCancel() {
                OneKeyLoginActivity.this.finish();
            }
        });
        policyDialogFragment.show(getSupportFragmentManager(), "PolicyDialogFragment");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: startOneKeyLogin, reason: merged with bridge method [inline-methods] */
    public void m62x7e546529(final String str) {
        DialogUtils.showLoadingDialog(this);
        new AuthService(this).loginByGoogle(str).doFinally(new Action() { // from class: com.aivox.account.activity.OneKeyLoginActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m60xabf216b();
            }
        }).subscribe(new Consumer() { // from class: com.aivox.account.activity.OneKeyLoginActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m61x4489c34a((LoginBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.account.activity.OneKeyLoginActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m63xb81f0708(str, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$startOneKeyLogin$5$com-aivox-account-activity-OneKeyLoginActivity */
    /* synthetic */ void m60xabf216b() throws Exception {
        this.mIsLoginInProgress = false;
    }

    /* JADX INFO: renamed from: lambda$startOneKeyLogin$6$com-aivox-account-activity-OneKeyLoginActivity */
    /* synthetic */ void m61x4489c34a(LoginBean loginBean) throws Exception {
        DialogUtils.hideLoadingDialog();
        toLogin(loginBean);
    }

    /* JADX INFO: renamed from: lambda$startOneKeyLogin$8$com-aivox-account-activity-OneKeyLoginActivity */
    /* synthetic */ void m63xb81f0708(final String str, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.account.activity.OneKeyLoginActivity$$ExternalSyntheticLambda8
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m62x7e546529(str);
                }
            });
        }
    }

    private void toLogin(LoginBean loginBean) {
        new SQLiteDataBaseManager(this).insertUserInfo(loginBean.getUserinfo());
        SPUtil.put(SPUtil.TOKEN, loginBean.getToken());
        SPUtil.put(SPUtil.TOKEN_EXPIRE, loginBean.getTokenExpire());
        SPUtil.put(SPUtil.REFRESH_TOKEN, loginBean.getRefreshToken());
        SPUtil.put(SPUtil.USER_ID, loginBean.getUserinfo().getUuid());
        SPUtil.put(SPUtil.NICKNAME, loginBean.getUserinfo().getNickname());
        SPUtil.put(SPUtil.LOGIN_ACCOUNT, loginBean.getUserinfo().getEmail());
        saveIdentityInfo();
    }

    private void saveIdentityInfo() {
        BaseUtil.setApiService((ApiService) new RetrofitServiceManager().create(ApiService.class));
        ARouterUtils.startWithActivity(this, MainAction.MAIN);
        AppManager.getAppManager().finishAllActivityExcept(ARouterUtils.getClass(MainAction.MAIN));
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            m62x7e546529(task.getResult(ApiException.class).getIdToken());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void setPolicy() {
        ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.aivox.account.activity.OneKeyLoginActivity.2
            @Override // android.text.style.ClickableSpan
            public void onClick(View view2) {
                if (!AntiShake.check(this)) {
                    KeyboardUtils.hideSoftInput(view2);
                    OneKeyLoginActivity oneKeyLoginActivity = OneKeyLoginActivity.this;
                    BaseAppUtils.startActivityForWeb(oneKeyLoginActivity, oneKeyLoginActivity.getString(C0874R.string.h5_path_privacy), OneKeyLoginActivity.this.getString(C0874R.string.set_privacy_policy), ARouterUtils.getClass(MainAction.WEB));
                }
                ((TextView) view2).setHighlightColor(OneKeyLoginActivity.this.getColor(R.color.transparent));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(OneKeyLoginActivity.this.getColor(C0874R.color.txt_color_highlight));
                textPaint.setUnderlineText(false);
                textPaint.clearShadowLayer();
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() { // from class: com.aivox.account.activity.OneKeyLoginActivity.3
            @Override // android.text.style.ClickableSpan
            public void onClick(View view2) {
                if (!AntiShake.check(this)) {
                    KeyboardUtils.hideSoftInput(view2);
                    OneKeyLoginActivity oneKeyLoginActivity = OneKeyLoginActivity.this;
                    BaseAppUtils.startActivityForWeb(oneKeyLoginActivity, oneKeyLoginActivity.getString(C0874R.string.h5_path_agreement), OneKeyLoginActivity.this.getString(C0874R.string.vip_terms_of_service), ARouterUtils.getClass(MainAction.WEB));
                }
                ((TextView) view2).setHighlightColor(OneKeyLoginActivity.this.getColor(R.color.transparent));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(OneKeyLoginActivity.this.getColor(C0874R.color.txt_color_highlight));
                textPaint.setUnderlineText(false);
                textPaint.clearShadowLayer();
            }
        };
        ClickableSpan clickableSpan3 = new ClickableSpan() { // from class: com.aivox.account.activity.OneKeyLoginActivity.4
            @Override // android.text.style.ClickableSpan
            public void onClick(View view2) {
                if (!AntiShake.check(this)) {
                    KeyboardUtils.hideSoftInput(view2);
                    OneKeyLoginActivity oneKeyLoginActivity = OneKeyLoginActivity.this;
                    BaseAppUtils.startActivityForWeb(oneKeyLoginActivity, oneKeyLoginActivity.getString(C0874R.string.h5_path_kids), ARouterUtils.getClass(MainAction.WEB));
                }
                ((TextView) view2).setHighlightColor(OneKeyLoginActivity.this.getColor(R.color.transparent));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(OneKeyLoginActivity.this.getColor(C0874R.color.txt_color_highlight));
                textPaint.setUnderlineText(false);
                textPaint.clearShadowLayer();
            }
        };
        ClickableSpan clickableSpan4 = new ClickableSpan() { // from class: com.aivox.account.activity.OneKeyLoginActivity.5
            @Override // android.text.style.ClickableSpan
            public void onClick(View view2) {
                if (!AntiShake.check(this)) {
                    KeyboardUtils.hideSoftInput(view2);
                    OneKeyLoginActivity oneKeyLoginActivity = OneKeyLoginActivity.this;
                    BaseAppUtils.startActivityForWeb(oneKeyLoginActivity, oneKeyLoginActivity.getString(C0874R.string.h5_path_personal_info), ARouterUtils.getClass(MainAction.WEB));
                }
                ((TextView) view2).setHighlightColor(OneKeyLoginActivity.this.getColor(R.color.transparent));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(OneKeyLoginActivity.this.getColor(C0874R.color.txt_color_highlight));
                textPaint.setUnderlineText(false);
                textPaint.clearShadowLayer();
            }
        };
        this.mBinding.tvPolicy.setText(new SpanUtils().setFlag(17).append(getString(C0874R.string.account_privacy_policy)).append(getString(C0874R.string.set_notice_privacy)).setClickSpan(clickableSpan).append(getString(C0874R.string.set_notice_service)).setClickSpan(clickableSpan2).append(getString(C0874R.string.set_notice_kids_privacy)).setClickSpan(clickableSpan3).append(getString(C0874R.string.set_notice_personal_info_list)).setClickSpan(clickableSpan4).append(getString(C0874R.string.set_notice_third_info_list)).setClickSpan(new ClickableSpan() { // from class: com.aivox.account.activity.OneKeyLoginActivity.6
            @Override // android.text.style.ClickableSpan
            public void onClick(View view2) {
                if (!AntiShake.check(this)) {
                    KeyboardUtils.hideSoftInput(view2);
                    OneKeyLoginActivity oneKeyLoginActivity = OneKeyLoginActivity.this;
                    BaseAppUtils.startActivityForWeb(oneKeyLoginActivity, oneKeyLoginActivity.getString(C0874R.string.h5_path_sdk), ARouterUtils.getClass(MainAction.WEB));
                }
                ((TextView) view2).setHighlightColor(OneKeyLoginActivity.this.getColor(R.color.transparent));
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(OneKeyLoginActivity.this.getColor(C0874R.color.txt_color_highlight));
                textPaint.setUnderlineText(false);
                textPaint.clearShadowLayer();
            }
        }).create());
        this.mBinding.tvPolicy.setMovementMethod(LinkMovementMethod.getInstance());
        this.mBinding.tvPolicy.setHighlightColor(getResources().getColor(R.color.transparent));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 100 || this.mIsLoginInProgress) {
            return;
        }
        this.mIsLoginInProgress = true;
        handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(intent));
    }
}
